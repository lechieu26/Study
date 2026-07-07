package com.study.service;

import com.study.model.Exercise;
import com.study.model.QuizQuestion;
import com.study.model.Solution;
import com.study.model.Topic;
import com.study.model.TopicGroup;
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
        topics.put("dsa-array", createTopic("dsa-array", "Array (Mảng)", "Mảng: Two Pointers, Sliding Window, Prefix Sum, Kadane's Algorithm", "📦"));
        topics.put("dsa-linked-list", createTopic("dsa-linked-list", "Linked List", "Danh sách liên kết: Singly, Doubly, Fast & Slow Pointers, Cycle Detection", "🔗"));
        topics.put("dsa-stack", createTopic("dsa-stack", "Stack (Ngăn xếp)", "Stack: LIFO, Monotonic Stack, Valid Parentheses, Expression Evaluation", "📚"));
        topics.put("dsa-queue", createTopic("dsa-queue", "Queue (Hàng đợi)", "Queue: FIFO, Deque, PriorityQueue, BFS, Sliding Window", "📋"));
        topics.put("dsa-hash", createTopic("dsa-hash", "Hash Table", "Bảng băm: HashMap, HashSet, Collision, Two Sum, LRU Cache", "#️⃣"));
        topics.put("dsa-tree", createTopic("dsa-tree", "Tree (Cây)", "Cây: Binary Tree, BST, Traversal, AVL, Red-Black Tree", "🌳"));
        topics.put("dsa-heap", createTopic("dsa-heap", "Heap & Priority Queue", "Đống: Min/Max Heap, Top-K, Merge K Lists, Median Finder", "⛰️"));
        topics.put("dsa-graph", createTopic("dsa-graph", "Graph (Đồ thị)", "Đồ thị: BFS, DFS, Dijkstra, Topological Sort, Union-Find", "🕸️"));
        topics.put("dsa-sort", createTopic("dsa-sort", "Sorting", "Sắp xếp: Bubble, Merge, Quick, Heap, Counting, TimSort", "🔢"));
        topics.put("dsa-search", createTopic("dsa-search", "Searching", "Tìm kiếm: Binary Search, Search on Answer, Rotated Array", "🔎"));
        topics.put("dsa-dp", createTopic("dsa-dp", "Dynamic Programming", "Quy hoạch động: Fibonacci, Knapsack, LIS, LCS, Edit Distance", "🧩"));
        topics.put("dsa-greedy-backtracking", createTopic("dsa-greedy-backtracking", "Greedy & Backtracking", "Tham lam & Quay lui: Activity Selection, Subsets, Permutations, N-Queens", "♟️"));
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
        topics.put("javascript", createTopic("javascript", "JavaScript", "JavaScript từ Cơ bản đến Nâng cao: ES6+, DOM, Async/Await, Closure, Prototype, Patterns", "🟨"));
        topics.put("react", createTopic("react", "React", "React từ Basic đến Advanced: JSX, Components, Hooks, Redux, Router, SSR, Testing", "⚛️"));
        topics.put("html", createTopic("html", "HTML", "HTML5 toàn tập: Semantic, Forms, Multimedia, Accessibility", "🏷️"));
        topics.put("css", createTopic("css", "CSS", "CSS từ cơ bản đến nâng cao: Selectors, Layout, Animations, Variables", "🎨"));
        topics.put("responsive", createTopic("responsive", "Responsive Design", "Responsive Web Design: Mobile-First, Media Queries, Flexible Layouts", "📱"));
        topics.put("typescript", createTopic("typescript", "TypeScript", "TypeScript: Types, Interfaces, Generics, Decorators, Modules", "💙"));
        topics.put("git", createTopic("git", "Git & Version Control", "Git: Basics, Branching, Merging, Collaboration, GitHub", "🔀"));
        topics.put("cicd", createTopic("cicd", "CI/CD & DevOps", "CI/CD: GitHub Actions, Jenkins, Docker, Deployment, Monitoring", "⚙️"));
        topics.put("docker", createTopic("docker", "Docker", "Docker: Containers, Images, Compose, Orchestration, Best Practices", "🐳"));

        // C/C++ Course
        topics.put("cpp-overview", createTopic("cpp-overview", "C/C++ - Tổng Quan & Môi Trường", "C/C++ là gì, cài môi trường, biên dịch và chạy chương trình đầu tiên", "💻"));
        topics.put("cpp-basic-syntax", createTopic("cpp-basic-syntax", "C/C++ - Cú Pháp Cơ Bản", "Biến, kiểu dữ liệu, nhập xuất, toán tử và cách viết chương trình C/C++ cơ bản", "⌨️"));
        topics.put("cpp-control-flow", createTopic("cpp-control-flow", "C/C++ - Điều Kiện & Vòng Lặp", "if, switch, for, while, do-while và tư duy điều khiển luồng chương trình", "🔁"));
        topics.put("cpp-functions-scope", createTopic("cpp-functions-scope", "C/C++ - Hàm & Phạm Vi", "Hàm, tham số, giá trị trả về, overload, scope và tổ chức code", "🧰"));
        topics.put("cpp-arrays-strings-pointers", createTopic("cpp-arrays-strings-pointers", "C/C++ - Mảng, Chuỗi & Con Trỏ", "Mảng, chuỗi C/C++, con trỏ, tham chiếu và cấp phát động", "🧵"));
        topics.put("cpp-struct-enum-file", createTopic("cpp-struct-enum-file", "C/C++ - Struct, Enum & File", "struct, enum, tổ chức dữ liệu và đọc ghi file", "📄"));
        topics.put("cpp-oop", createTopic("cpp-oop", "C++ OOP", "Class, object, constructor, encapsulation, inheritance, polymorphism trong C++", "🏛️"));
        topics.put("cpp-stl-template", createTopic("cpp-stl-template", "C++ STL & Template", "vector, string, map, set, algorithm, template và lambda", "🧩"));
        topics.put("cpp-memory-performance", createTopic("cpp-memory-performance", "C++ Bộ Nhớ & Hiệu Năng", "RAII, smart pointer, move semantics, tối ưu và tránh lỗi bộ nhớ", "⚡"));
        topics.put("cpp-algorithms-projects", createTopic("cpp-algorithms-projects", "C/C++ Thuật Toán & Dự Án", "Thuật toán, cấu trúc dữ liệu cơ bản và bài tập tổng hợp bằng C/C++", "🚧"));

        // DSA with C++ Course
        topics.put("dsa-cpp-complexity", createTopic("dsa-cpp-complexity", "DSA C++ - Độ Phức Tạp", "Big O, tư duy tối ưu và cách đọc phân tích bài toán", "📈"));
        topics.put("dsa-cpp-array-vector-string", createTopic("dsa-cpp-array-vector-string", "DSA C++ - Mảng, Vector, Chuỗi", "Mảng, vector, string, two pointers, sliding window và prefix sum", "📦"));
        topics.put("dsa-cpp-linked-list", createTopic("dsa-cpp-linked-list", "DSA C++ - Danh Sách Liên Kết", "Linked list, thao tác chèn xóa tìm kiếm và kỹ thuật con trỏ", "🔗"));
        topics.put("dsa-cpp-stack-queue-deque", createTopic("dsa-cpp-stack-queue-deque", "DSA C++ - Stack, Queue, Deque", "Ngăn xếp, hàng đợi, deque và các ứng dụng thường gặp", "📚"));
        topics.put("dsa-cpp-recursion-backtracking", createTopic("dsa-cpp-recursion-backtracking", "DSA C++ - Đệ Quy & Quay Lui", "Đệ quy, backtracking, sinh cấu hình và xử lý trạng thái", "♟️"));
        topics.put("dsa-cpp-sort-search", createTopic("dsa-cpp-sort-search", "DSA C++ - Sắp Xếp & Tìm Kiếm", "Các thuật toán sắp xếp, tìm kiếm nhị phân và binary search on answer", "🔎"));
        topics.put("dsa-cpp-hash-set-map", createTopic("dsa-cpp-hash-set-map", "DSA C++ - Hash, Set, Map", "Bảng băm, unordered_map, map, set và bài toán đếm tần suất", "#️⃣"));
        topics.put("dsa-cpp-binary-tree-bst", createTopic("dsa-cpp-binary-tree-bst", "DSA C++ - Cây Nhị Phân & BST", "Binary tree, binary search tree, DFS và các thao tác trên cây", "🌳"));
        topics.put("dsa-cpp-heap-priority-queue", createTopic("dsa-cpp-heap-priority-queue", "DSA C++ - Heap & Priority Queue", "Heap, priority_queue, top K và xử lý phần tử ưu tiên", "⛰️"));
        topics.put("dsa-cpp-graph", createTopic("dsa-cpp-graph", "DSA C++ - Đồ Thị", "Biểu diễn đồ thị, BFS, DFS và đường đi ngắn nhất cơ bản", "🕸️"));
        topics.put("dsa-cpp-dynamic-programming", createTopic("dsa-cpp-dynamic-programming", "DSA C++ - Quy Hoạch Động", "DP 1 chiều, 2 chiều, knapsack, LIS và tối ưu trạng thái", "🧠"));
        topics.put("dsa-cpp-projects", createTopic("dsa-cpp-projects", "DSA C++ - Dự Án & Tổng Hợp", "Bài tập lớn, khung dự án luyện DSA và tổng ôn thuật toán", "🧪"));

        // ERP Personal Project
        topics.put("erp-system", createTopic("erp-system", "Dự Án ERP - Tổng Quan", "Kiến trúc hệ thống ERP, Tech Stack, Database Design, Project Setup", "🏢"));
        topics.put("erp-sales", createTopic("erp-sales", "ERP - Quản Lý Bán Hàng", "Báo giá, Đơn hàng, Hóa đơn, Thanh toán, Công nợ khách hàng", "💰"));
        topics.put("erp-procurement", createTopic("erp-procurement", "ERP - Quản Lý Mua Hàng", "Yêu cầu mua, PO, Nhập hàng, Công nợ nhà cung cấp", "🛒"));
        topics.put("erp-inventory", createTopic("erp-inventory", "ERP - Quản Lý Kho", "Nhập kho, Xuất kho, Chuyển kho, Kiểm kê, Tồn kho realtime", "🏭"));
        topics.put("erp-manufacturing", createTopic("erp-manufacturing", "ERP - Quản Lý Sản Xuất", "BOM, Kế hoạch sản xuất, Lệnh sản xuất, Giá thành", "⚙️"));
        topics.put("erp-accounting", createTopic("erp-accounting", "ERP - Kế Toán Tài Chính", "Sổ cái, Thu chi, Công nợ, Báo cáo tài chính", "📒"));
        topics.put("erp-hr", createTopic("erp-hr", "ERP - Nhân Sự", "Hồ sơ nhân viên, Chấm công, Tính lương, KPI", "👥"));
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
            case "dsa-array" -> "04a-dsa-array/" + filename;
            case "dsa-linked-list" -> "04b-dsa-linked-list/" + filename;
            case "dsa-stack" -> "04c-dsa-stack/" + filename;
            case "dsa-queue" -> "04d-dsa-queue/" + filename;
            case "dsa-hash" -> "04e-dsa-hash/" + filename;
            case "dsa-tree" -> "04f-dsa-tree/" + filename;
            case "dsa-heap" -> "04g-dsa-heap/" + filename;
            case "dsa-graph" -> "04h-dsa-graph/" + filename;
            case "dsa-sort" -> "04i-dsa-sort/" + filename;
            case "dsa-search" -> "04j-dsa-search/" + filename;
            case "dsa-dp" -> "04k-dsa-dynamic-programming/" + filename;
            case "dsa-greedy-backtracking" -> "04l-dsa-greedy-backtracking/" + filename;
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
            case "javascript" -> "16-javascript/" + filename;
            case "react" -> "15-react/" + filename;
            case "html" -> "17-html/" + filename;
            case "css" -> "18-css/" + filename;
            case "responsive" -> "19-responsive/" + filename;
            case "typescript" -> "20-typescript/" + filename;
            case "git" -> "21-git/" + filename;
            case "cicd" -> "22-cicd/" + filename;
            case "docker" -> "23-docker/" + filename;
            case "cpp-overview" -> "24a-cpp-overview/" + filename;
            case "cpp-basic-syntax" -> "24b-cpp-basic-syntax/" + filename;
            case "cpp-control-flow" -> "24c-cpp-control-flow/" + filename;
            case "cpp-functions-scope" -> "24d-cpp-functions-scope/" + filename;
            case "cpp-arrays-strings-pointers" -> "24e-cpp-arrays-strings-pointers/" + filename;
            case "cpp-struct-enum-file" -> "24f-cpp-struct-enum-file/" + filename;
            case "cpp-oop" -> "24g-cpp-oop/" + filename;
            case "cpp-stl-template" -> "24h-cpp-stl-template/" + filename;
            case "cpp-memory-performance" -> "24i-cpp-memory-performance/" + filename;
            case "cpp-algorithms-projects" -> "24j-cpp-algorithms-projects/" + filename;
            case "dsa-cpp-complexity" -> "25a-dsa-cpp-complexity/" + filename;
            case "dsa-cpp-array-vector-string" -> "25b-dsa-cpp-array-vector-string/" + filename;
            case "dsa-cpp-linked-list" -> "25c-dsa-cpp-linked-list/" + filename;
            case "dsa-cpp-stack-queue-deque" -> "25d-dsa-cpp-stack-queue-deque/" + filename;
            case "dsa-cpp-recursion-backtracking" -> "25e-dsa-cpp-recursion-backtracking/" + filename;
            case "dsa-cpp-sort-search" -> "25f-dsa-cpp-sort-search/" + filename;
            case "dsa-cpp-hash-set-map" -> "25g-dsa-cpp-hash-set-map/" + filename;
            case "dsa-cpp-binary-tree-bst" -> "25h-dsa-cpp-binary-tree-bst/" + filename;
            case "dsa-cpp-heap-priority-queue" -> "25i-dsa-cpp-heap-priority-queue/" + filename;
            case "dsa-cpp-graph" -> "25j-dsa-cpp-graph/" + filename;
            case "dsa-cpp-dynamic-programming" -> "25k-dsa-cpp-dynamic-programming/" + filename;
            case "dsa-cpp-projects" -> "25l-dsa-cpp-projects/" + filename;
            case "erp-system" -> "30-erp-system/" + filename;
            case "erp-sales" -> "30a-erp-sales/" + filename;
            case "erp-procurement" -> "30b-erp-procurement/" + filename;
            case "erp-inventory" -> "30c-erp-inventory/" + filename;
            case "erp-manufacturing" -> "30d-erp-manufacturing/" + filename;
            case "erp-accounting" -> "30e-erp-accounting/" + filename;
            case "erp-hr" -> "30f-erp-hr/" + filename;
            default -> throw new IllegalArgumentException("Unknown topic: " + topicId);
        };
    }

    private String loadMarkdownContent(String relativePath) {
        for (Path contentRoot : getContentRootCandidates()) {
            Path filePath = contentRoot.resolve(relativePath).normalize();
            try {
                if (Files.exists(filePath)) {
                    return Files.readString(filePath, StandardCharsets.UTF_8);
                }
            } catch (IOException e) {
                // try next location
            }
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

    private List<Path> getContentRootCandidates() {
        String configuredContentDir = System.getProperty("content.dir");
        Path workingDir = Path.of(System.getProperty("user.dir"));
        List<Path> candidates = new ArrayList<>();

        if (configuredContentDir != null && !configuredContentDir.isBlank()) {
            candidates.add(Path.of(configuredContentDir));
        }

        candidates.add(workingDir.resolve("content"));
        candidates.add(workingDir.resolve("../content"));
        candidates.add(workingDir.resolve("study-app/../content"));
        candidates.add(Path.of("content"));
        candidates.add(Path.of("../content"));

        return candidates.stream()
                .map(Path::toAbsolutePath)
                .map(Path::normalize)
                .distinct()
                .toList();
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

            String boilerplate = extractBoilerplateCode(section);
            String descSection = section;
            if (boilerplate != null) {
                descSection = section.replaceAll("(?s)###\\s*🧪\\s*Main Demo.*", "").trim();
            }

            String descHtml = renderMarkdown(descSection);
            List<Solution> solutions = parseSolutions(solutionSections.getOrDefault(exerciseNum, ""));

            exercises.add(new Exercise(exerciseNum, title, difficulty, descHtml, solutions, boilerplate));
        }

        return exercises;
    }

    private String extractBoilerplateCode(String section) {
        Matcher m = Pattern.compile("###\\s*🧪\\s*Main Demo\\s*\\n```java\\n(.*?)```", Pattern.DOTALL).matcher(section);
        if (m.find()) {
            return m.group(1).trim();
        }
        return null;
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

    public List<TopicGroup> getTopicGroups() {
        List<TopicGroup> groups = new ArrayList<>();

        groups.add(new TopicGroup("DSA", "🧮", filterTopics(
                "dsa", "dsa-array", "dsa-linked-list", "dsa-stack", "dsa-queue",
                "dsa-hash", "dsa-tree", "dsa-heap", "dsa-graph",
                "dsa-sort", "dsa-search", "dsa-dp", "dsa-greedy-backtracking"
        )));

        groups.add(new TopicGroup("Learn Java", "☕", filterTopics(
                "java", "oop", "collection-framework", "generic",
                "exception-handling", "io", "stream-api",
                "reflection", "concurrency", "jdbc-connection-pool",
                "design-patterns"
        )));

        groups.add(new TopicGroup("Java Spring", "🍃", filterTopics(
                "spring-core", "spring-boot", "spring-mvc",
                "spring-data-jpa", "spring-security", "spring-aop",
                "spring-testing", "spring-microservices"
        )));

        groups.add(new TopicGroup("Front-end", "🌐", filterTopics(
                "html", "css", "responsive", "typescript", "javascript", "react"
        )));

        groups.add(new TopicGroup("DevOps & Tools", "🛠️", filterTopics(
                "git", "cicd", "docker"
        )));

        groups.add(new TopicGroup("C/C++", "💻", filterTopics(
                "cpp-overview", "cpp-basic-syntax", "cpp-control-flow",
                "cpp-functions-scope", "cpp-arrays-strings-pointers",
                "cpp-struct-enum-file", "cpp-oop", "cpp-stl-template",
                "cpp-memory-performance", "cpp-algorithms-projects"
        )));

        groups.add(new TopicGroup("DSA C++", "🧠", filterTopics(
                "dsa-cpp-complexity", "dsa-cpp-array-vector-string",
                "dsa-cpp-linked-list", "dsa-cpp-stack-queue-deque",
                "dsa-cpp-recursion-backtracking", "dsa-cpp-sort-search",
                "dsa-cpp-hash-set-map", "dsa-cpp-binary-tree-bst",
                "dsa-cpp-heap-priority-queue", "dsa-cpp-graph",
                "dsa-cpp-dynamic-programming", "dsa-cpp-projects"
        )));

        groups.add(new TopicGroup("SQL", "🗄️", filterTopics("sql")));

        groups.add(new TopicGroup("Dự án cá nhân", "📊", filterTopics(
                "erp-system", "erp-sales", "erp-procurement",
                "erp-inventory", "erp-manufacturing",
                "erp-accounting", "erp-hr"
        )));

        return groups;
    }

    private List<Topic> filterTopics(String... ids) {
        List<Topic> result = new ArrayList<>();
        for (String id : ids) {
            Topic t = topics.get(id);
            if (t != null) {
                result.add(t);
            }
        }
        return result;
    }

    public String loadFile(String topicId, String filename) {
        String relativePath = getContentPath(topicId, filename);
        return loadMarkdownContent(relativePath);
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
