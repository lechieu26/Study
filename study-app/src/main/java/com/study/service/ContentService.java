package com.study.service;

import com.study.model.Exercise;
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
        topics.put("design-patterns", createTopic("design-patterns", "Design Patterns", "Các mẫu thiết kế phần mềm: Creational, Structural, Behavioral", "🏗️"));
        topics.put("dsa", createTopic("dsa", "Cấu Trúc Dữ Liệu & Giải Thuật", "DSA: Array, Linked List, Tree, Graph, DP, Sorting, Searching", "🧮"));
        topics.put("sql", createTopic("sql", "SQL (PostgreSQL)", "SQL nâng cao: JOIN, Window Functions, CTE, Stored Functions, Optimization", "🗄️"));
        topics.put("spring", createTopic("spring", "Java Spring", "Spring Framework: Core, Boot, Security, AOP, JPA, Testing", "🌱"));
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

        return topic;
    }

    private String getContentPath(String topicId, String filename) {
        return switch (topicId) {
            case "java" -> "01-java/" + filename;
            case "design-patterns" -> "02-design-patterns/" + filename;
            case "dsa" -> "03-dsa/" + filename;
            case "sql" -> "04-sql-postgresql/" + filename;
            case "spring" -> "05-java-spring/" + filename;
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
}
