package com.study.service;

import com.study.model.Exercise;
import com.study.model.QuizQuestion;
import com.study.model.Solution;
import com.study.model.Topic;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ContentService {

    private final Map<String, Topic> topics = new LinkedHashMap<>();
    private final Parser mdParser = Parser.builder()
            .extensions(List.of(TablesExtension.create()))
            .build();
    private final HtmlRenderer htmlRenderer = HtmlRenderer.builder()
            .extensions(List.of(TablesExtension.create()))
            .build();

    @PostConstruct
    public void init() {
        loadTopics();
    }

    private void loadTopics() {
        topics.put("java", createTopic("java", "Java", "Java từ Cơ bản đến Nâng cao: OOP, Collections, Stream API, Multi-threading", "☕"));
        topics.put("oop", createTopic("oop", "Java OOP", "Lập trình Hướng đối tượng: Encapsulation, Inheritance, Polymorphism, Abstraction, SOLID", "🧱"));
        topics.put("design-patterns", createTopic("design-patterns", "Design Patterns", "Các mẫu thiết kế phần mềm: Creational, Structural, Behavioral", "🏗️"));
        topics.put("dsa", createTopic("dsa", "Cấu Trúc Dữ Liệu & Giải Thuật", "DSA: Array, Linked List, Tree, Graph, DP, Sorting, Searching", "🧮"));
        topics.put("sql", createTopic("sql", "SQL (PostgreSQL)", "SQL nâng cao: JOIN, Window Functions, CTE, Stored Functions, Optimization", "🗄️"));
        topics.put("spring-core", createTopic("spring-core", "Spring Core", "IoC, DI, Bean Lifecycle, Configuration, Profiles", "💚"));
        topics.put("spring-boot", createTopic("spring-boot", "Spring Boot", "Auto-configuration, Starters, Actuator, DevTools", "🚀"));
        topics.put("spring-mvc", createTopic("spring-mvc", "Spring MVC & REST", "Controllers, REST API, Validation, Exception Handling", "🌐"));
        topics.put("spring-data-jpa", createTopic("spring-data-jpa", "Spring Data JPA", "ORM, Repository Pattern, Queries, Transactions", "💾"));
        topics.put("spring-security", createTopic("spring-security", "Spring Security", "Authentication, Authorization, JWT, OAuth2", "🔐"));
        topics.put("spring-aop", createTopic("spring-aop", "Spring AOP", "Aspects, Pointcuts, Cross-cutting Concerns", "🎯"));
        topics.put("spring-testing", createTopic("spring-testing", "Spring Testing", "Unit & Integration Tests, MockMvc, TestContainers", "✅"));
        topics.put("spring-microservices", createTopic("spring-microservices", "Spring Microservices", "Distributed Systems, Service Discovery, Circuit Breaker", "🔗"));
        topics.put("collection-framework", createTopic("collection-framework", "Collection Framework", "List, Set, Queue, Map: ArrayList, LinkedList, HashMap, TreeMap, ConcurrentHashMap", "📦"));
        topics.put("exception-handling", createTopic("exception-handling", "Exception Handling", "Xử lý ngoại lệ: Try-Catch-Finally, Custom Exception, Best Practices", "⚠️"));
        topics.put("io", createTopic("io", "Java IO", "Input/Output: Byte Streams, Character Streams, NIO, Serialization", "📂"));
        topics.put("stream-api", createTopic("stream-api", "Stream API", "Functional Programming: filter, map, reduce, Collectors, Parallel Streams", "🌊"));
        topics.put("reflection", createTopic("reflection", "Reflection API", "Runtime Inspection: Class, Method, Field, Dynamic Proxy, Annotations", "🔍"));
        topics.put("concurrency", createTopic("concurrency", "Concurrency", "Multi-threading: synchronized, Locks, Executor, CompletableFuture", "⚡"));
        topics.put("generic", createTopic("generic", "Generic", "Type Safety: Generic Class, Method, Bounded Types, Wildcards, PECS", "🔤"));
        topics.put("jdbc-connection-pool", createTopic("jdbc-connection-pool", "JDBC & Connection Pool", "Database Connection: HikariCP, Pool Sizing, Spring Boot Integration", "🗃️"));
        topics.put("react", createTopic("react", "React", "React từ Basic đến Advanced: JSX, Components, Hooks, Redux, Router, SSR, Testing", "⚛️"));
    }

    private Topic createTopic(String id, String name, String description, String icon) {
        Topic topic = new Topic(id, name, description, icon);

        String theoryPath = getContentPath(id, "ly-thuyet.md");
        String exercisePath = getContentPath(id, "bai-tap.md");
        String solutionPath = getContentPath(id, "dap-an.md");

        topic.setTheoryHtml(loadAndRenderMarkdown(theoryPath));
        topic.setExercises(parseExercises(
            loadMarkdownContent(exercisePath),
            loadMarkdownContent(solutionPath)
        ));

        String quizPath = getContentPath(id, "quiz.md");
        topic.setQuizQuestions(parseQuizQuestions(loadMarkdownContent(quizPath)));

        return topic;
    }

    private String getContentPath(String topicId, String filename) {
        return switch (topicId) {
            case "java" -> "01-java/" + filename;
            case "oop" -> "02-oop/" + filename;
            case "design-patterns" -> "03-design-patterns/" + filename;
            case "dsa" -> "04-dsa/" + filename;
            case "sql" -> "05-sql-postgresql/" + filename;
            case "spring-core" -> "06a-spring-core/" + filename;
            case "spring-boot" -> "06b-spring-boot/" + filename;
            case "spring-mvc" -> "06c-spring-mvc/" + filename;
            case "spring-data-jpa" -> "06d-spring-data-jpa/" + filename;
            case "spring-security" -> "06e-spring-security/" + filename;
            case "spring-aop" -> "06f-spring-aop/" + filename;
            case "spring-testing" -> "06g-spring-testing/" + filename;
            case "spring-microservices" -> "06h-spring-microservices/" + filename;
            case "collection-framework" -> "07-collection-framework/" + filename;
            case "exception-handling" -> "08-exception-handling/" + filename;
            case "io" -> "09-io/" + filename;
            case "stream-api" -> "10-stream-api/" + filename;
            case "reflection" -> "11-reflection/" + filename;
            case "concurrency" -> "12-concurrency/" + filename;
            case "generic" -> "13-generic/" + filename;
            case "jdbc-connection-pool" -> "14-jdbc-connection-pool/" + filename;
            case "react" -> "15-react/" + filename;
            default -> throw new IllegalArgumentException("Unknown topic: " + topicId);
        };
    }

    private String loadMarkdownContent(String relativePath) {
        Path filePath = Path.of(System.getProperty("content.dir", "../content"), relativePath);
        try {
            if (Files.exists(filePath)) {
                return Files.readString(filePath, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            // fall through
        }

        try {
            Resource resource = new ClassPathResource("content/" + relativePath);
            if (resource.exists()) {
                try (InputStream is = resource.getInputStream()) {
                    return new String(is.readAllBytes(), StandardCharsets.UTF_8);
                }
            }
        } catch (IOException e) {
            // fall through
        }
        return "";
    }

    private String loadAndRenderMarkdown(String relativePath) {
        String md = loadMarkdownContent(relativePath);
        if (md.isEmpty()) return "<p>Nội dung đang được cập nhật...</p>";
        Node document = mdParser.parse(md);
        return htmlRenderer.render(document);
    }

    private List<Exercise> parseExercises(String exerciseMd, String solutionMd) {
        List<Exercise> exercises = new ArrayList<>();
        if (exerciseMd.isEmpty()) return exercises;

        String[] sections = exerciseMd.split("(?=## Bài \\d+)");
        Map<Integer, String> solutionSections = parseSolutionSections(solutionMd);

        for (String section : sections) {
            section = section.trim();
            if (!section.startsWith("## Bài")) continue;

            Matcher titleMatcher = Pattern.compile("## Bài (\\d+):\\s*(.+)").matcher(section);
            if (!titleMatcher.find()) continue;

            int exerciseNum = Integer.parseInt(titleMatcher.group(1));
            String title = titleMatcher.group(2).trim();

            Matcher diffMatcher = Pattern.compile("\\*\\*Độ khó:\\s*(.+?)\\*\\*").matcher(section);
            String difficulty = diffMatcher.find() ? diffMatcher.group(1).trim() : "Trung bình";

            String descHtml = renderMarkdown(section);
            List<Solution> solutions = parseSolutions(solutionSections.getOrDefault(exerciseNum, ""));

            exercises.add(new Exercise(exerciseNum, title, difficulty, descHtml, solutions));
        }

        return exercises;
    }

    private Map<Integer, String> parseSolutionSections(String solutionMd) {
        Map<Integer, String> sections = new HashMap<>();
        if (solutionMd.isEmpty()) return sections;

        String[] parts = solutionMd.split("(?=## Bài \\d+)");
        for (String part : parts) {
            part = part.trim();
            Matcher m = Pattern.compile("## Bài (\\d+)").matcher(part);
            if (m.find()) {
                sections.put(Integer.parseInt(m.group(1)), part);
            }
        }
        return sections;
    }

    private List<Solution> parseSolutions(String solutionSection) {
        List<Solution> solutions = new ArrayList<>();
        if (solutionSection.isEmpty()) {
            solutions.add(new Solution("Chưa có đáp án", "<p>Đáp án đang được cập nhật...</p>"));
            return solutions;
        }

        String[] approaches = solutionSection.split("(?=### Cách \\d+)");
        for (String approach : approaches) {
            approach = approach.trim();
            if (!approach.startsWith("### Cách")) continue;

            Matcher m = Pattern.compile("### Cách \\d+:\\s*(.+)").matcher(approach);
            String approachName = m.find() ? m.group(1).trim() : "Cách giải";
            solutions.add(new Solution(approachName, renderMarkdown(approach)));
        }

        if (solutions.isEmpty()) {
            solutions.add(new Solution("Đáp án", renderMarkdown(solutionSection)));
        }

        return solutions;
    }

    private String renderMarkdown(String md) {
        Node document = mdParser.parse(md);
        return htmlRenderer.render(document);
    }

    public List<Topic> getAllTopics() {
        return new ArrayList<>(topics.values());
    }

    public Optional<Topic> getTopicById(String id) {
        return Optional.ofNullable(topics.get(id));
    }

    public Optional<Exercise> getExercise(String topicId, int exerciseId) {
        return getTopicById(topicId)
            .flatMap(topic -> topic.getExercises().stream()
                .filter(e -> e.getId() == exerciseId)
                .findFirst());
    }

    private List<QuizQuestion> parseQuizQuestions(String quizMd) {
        List<QuizQuestion> questions = new ArrayList<>();
        if (quizMd.isEmpty()) return questions;

        String[] sections = quizMd.split("(?=## Câu \\d+)");
        for (String section : sections) {
            section = section.trim();
            if (!section.startsWith("## Câu")) continue;

            Matcher numMatcher = Pattern.compile("## Câu (\\d+)").matcher(section);
            if (!numMatcher.find()) continue;
            int questionNum = Integer.parseInt(numMatcher.group(1));

            QuizQuestion.QuestionType type = parseQuestionType(section);

            String questionText = extractQuestionText(section);
            String codeSnippet = extractCodeSnippet(section);
            List<String> options = extractOptions(section);
            int correctAnswer = extractCorrectAnswer(section);
            String explanation = extractExplanation(section);

            String questionHtml = renderMarkdown(questionText);

            questions.add(new QuizQuestion(
                questionNum, type, questionText, questionHtml,
                codeSnippet, options, correctAnswer, explanation
            ));
        }
        return questions;
    }

    private QuizQuestion.QuestionType parseQuestionType(String section) {
        if (section.contains("[TYPE: FILL_BLANK]")) return QuizQuestion.QuestionType.FILL_BLANK;
        if (section.contains("[TYPE: SELECT_RESULT]")) return QuizQuestion.QuestionType.SELECT_RESULT;
        if (section.contains("[TYPE: TRUE_FALSE]")) return QuizQuestion.QuestionType.TRUE_FALSE;
        return QuizQuestion.QuestionType.MULTIPLE_CHOICE;
    }

    private String extractQuestionText(String section) {
        Matcher m = Pattern.compile("## Câu \\d+[^\\n]*\\n(.*?)(?=\\n```|\\n- \\[|\\n\\*\\*Đáp án|$)",
                Pattern.DOTALL).matcher(section);
        if (m.find()) {
            String text = m.group(1).trim();
            text = text.replaceAll("\\[TYPE: [A-Z_]+]", "").trim();
            return text;
        }
        return "";
    }

    private String extractCodeSnippet(String section) {
        Matcher m = Pattern.compile("```[a-z]*\\n(.*?)```", Pattern.DOTALL).matcher(section);
        if (m.find()) {
            return m.group(1).trim();
        }
        return null;
    }

    private List<String> extractOptions(String section) {
        List<String> options = new ArrayList<>();
        Matcher m = Pattern.compile("- \\[([ x])\\]\\s+(.+)").matcher(section);
        while (m.find()) {
            options.add(m.group(2).trim());
        }
        return options;
    }

    private int extractCorrectAnswer(String section) {
        Matcher explicitAnswer = Pattern.compile("\\*\\*Đáp án:\\s*(\\d+)\\*\\*").matcher(section);
        if (explicitAnswer.find()) {
            return Integer.parseInt(explicitAnswer.group(1));
        }
        int index = 0;
        Matcher m = Pattern.compile("- \\[([ x])\\]\\s+(.+)").matcher(section);
        while (m.find()) {
            if ("x".equals(m.group(1))) {
                return index;
            }
            index++;
        }
        return 0;
    }

    private String extractExplanation(String section) {
        Matcher m = Pattern.compile("> \\*\\*Giải thích:\\*\\*\\s*(.+)", Pattern.DOTALL).matcher(section);
        if (m.find()) {
            return m.group(1).trim();
        }
        Matcher m2 = Pattern.compile("> Giải thích:\\s*(.+)", Pattern.DOTALL).matcher(section);
        if (m2.find()) {
            return m2.group(1).trim();
        }
        return "";
    }
}
