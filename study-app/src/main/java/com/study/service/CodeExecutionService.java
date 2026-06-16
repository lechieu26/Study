package com.study.service;

import com.study.model.CodeCheckResult;
import com.study.model.TestCaseResult;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CodeExecutionService {

    private static final long TIMEOUT_SECONDS = 10;
    private static final int MAX_OUTPUT_LENGTH = 5000;
    private static final Pattern TEST_RESULT_PATTERN =
            Pattern.compile("\\[TEST (\\d+)\\] (PASS|FAIL)(?:: Expected (.+?), Got (.+))?");

    public CodeCheckResult executeJavaCode(String code) {
        Path tempDir = null;
        try {
            tempDir = Files.createTempDirectory("java_exec_");
            String className = extractClassName(code);
            if (className == null) {
                return new CodeCheckResult(false, "", "Không tìm thấy class có phương thức main. Hãy đảm bảo code có: public class TenClass { public static void main(String[] args) { ... } }", 0, "Kiểm tra lại cấu trúc code.");
            }

            Path javaFile = tempDir.resolve(className + ".java");
            Files.writeString(javaFile, code);

            String compileOutput = runProcess(tempDir, "javac", className + ".java");
            if (compileOutput != null && compileOutput.contains("error")) {
                return new CodeCheckResult(false, "", formatCompileError(compileOutput), 0, "Code có lỗi biên dịch. Hãy kiểm tra lại cú pháp.");
            }

            String runOutput = runProcess(tempDir, "java", "-cp", tempDir.toString(), className);
            if (runOutput == null) {
                return new CodeCheckResult(false, "", "Chương trình chạy quá thời gian (" + TIMEOUT_SECONDS + " giây).", 30, "Code chạy quá lâu, có thể có vòng lặp vô hạn.");
            }

            String truncatedOutput = runOutput.length() > MAX_OUTPUT_LENGTH
                ? runOutput.substring(0, MAX_OUTPUT_LENGTH) + "\n... (đã cắt bớt)"
                : runOutput;

            List<TestCaseResult> testResults = parseTestResults(truncatedOutput);

            if (!testResults.isEmpty()) {
                int passed = (int) testResults.stream().filter(TestCaseResult::isPassed).count();
                int total = testResults.size();
                int score = total > 0 ? (passed * 100) / total : 0;
                String feedback = generateTestFeedback(passed, total);
                return new CodeCheckResult(true, truncatedOutput, "", score, feedback, testResults, passed, total);
            }

            int score = evaluateCode(code, truncatedOutput);
            String feedback = generateFeedback(score, code);
            return new CodeCheckResult(true, truncatedOutput, "", score, feedback);

        } catch (Exception e) {
            return new CodeCheckResult(false, "", "Lỗi hệ thống: " + e.getMessage(), 0, "Có lỗi xảy ra khi thực thi code.");
        } finally {
            if (tempDir != null) {
                deleteDirectory(tempDir);
            }
        }
    }

    private List<TestCaseResult> parseTestResults(String output) {
        List<TestCaseResult> results = new ArrayList<>();
        String[] lines = output.split("\n");
        for (String line : lines) {
            Matcher m = TEST_RESULT_PATTERN.matcher(line.trim());
            if (m.find()) {
                int testId = Integer.parseInt(m.group(1));
                boolean passed = "PASS".equals(m.group(2));
                String expected = m.group(3) != null ? m.group(3).trim() : "";
                String actual = m.group(4) != null ? m.group(4).trim() : "";
                results.add(new TestCaseResult(testId, passed, "", expected, actual));
            }
        }
        return results;
    }

    private String generateTestFeedback(int passed, int total) {
        if (passed == total) {
            return "Xuất sắc! Tất cả " + total + " test cases đều PASS! 🎉";
        } else if (passed > total / 2) {
            return "Khá tốt! " + passed + "/" + total + " test cases PASS. Hãy kiểm tra lại các test case còn lại.";
        } else if (passed > 0) {
            return "Cần cải thiện. Chỉ " + passed + "/" + total + " test cases PASS. Hãy xem lại logic.";
        } else {
            return "Chưa pass test nào. Hãy kiểm tra lại thuật toán và logic xử lý.";
        }
    }

    private String extractClassName(String code) {
        Matcher matcher = Pattern.compile("public\\s+class\\s+(\\w+)").matcher(code);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private String runProcess(Path workDir, String... command) {
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(workDir.toFile());
            pb.redirectErrorStream(true);

            Process process = pb.start();
            StringBuilder output = new StringBuilder();

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<String> future = executor.submit(() -> {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line).append("\n");
                    }
                }
                return output.toString();
            });

            try {
                future.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
                process.waitFor(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                process.destroyForcibly();
                return null;
            } finally {
                executor.shutdownNow();
            }

            return output.toString().trim();

        } catch (Exception e) {
            return "Lỗi: " + e.getMessage();
        }
    }

    private int evaluateCode(String code, String output) {
        int score = 0;

        if (!output.isEmpty() && !output.startsWith("Lỗi")) score += 40;

        if (code.contains("public static void main")) score += 10;
        if (code.contains("class ")) score += 10;

        if (!code.contains("// TODO") && !code.contains("// ...")) score += 10;

        if (output.split("\n").length > 1) score += 10;

        if (code.contains("try") || code.contains("catch")) score += 5;
        if (code.contains("@Override")) score += 5;
        if (code.contains("private ") || code.contains("final ")) score += 5;
        if (code.contains("stream()") || code.contains("->")) score += 5;

        return Math.min(score, 100);
    }

    private String generateFeedback(int score, String code) {
        StringBuilder fb = new StringBuilder();

        if (score >= 80) {
            fb.append("Xuất sắc! Code hoạt động tốt. ");
        } else if (score >= 60) {
            fb.append("Tốt! Code chạy được nhưng có thể cải thiện. ");
        } else if (score >= 40) {
            fb.append("Khá! Code cơ bản hoạt động. ");
        } else {
            fb.append("Cần cải thiện. Kiểm tra lại logic và cú pháp. ");
        }

        if (!code.contains("try") && !code.contains("catch")) {
            fb.append("Gợi ý: Thêm xử lý ngoại lệ (try-catch). ");
        }
        if (!code.contains("private")) {
            fb.append("Gợi ý: Sử dụng access modifier phù hợp. ");
        }

        return fb.toString().trim();
    }

    private String formatCompileError(String error) {
        return error.replaceAll("/tmp/java_exec_[^/]+/", "");
    }

    private void deleteDirectory(Path dir) {
        try {
            Files.walk(dir)
                .sorted(java.util.Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        } catch (IOException ignored) {}
    }
}
