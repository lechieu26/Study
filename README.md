# 📚 Lộ Trình Ôn Tập Lập Trình

Hệ thống ôn tập lập trình toàn diện với ứng dụng web tương tác, bao gồm lý thuyết chi tiết, bài tập thực hành và đáp án nhiều cách giải.

## 📋 Chủ Đề Ôn Tập

| # | Chủ đề | Mô tả |
|---|--------|-------|
| 1 | **Java** | Cơ bản đến Nâng cao: OOP, Collections, Stream API, Generics, Multi-threading |
| 2 | **Design Patterns** | Creational, Structural, Behavioral patterns với ví dụ thực tế |
| 3 | **DSA** | Cấu trúc dữ liệu & Giải thuật: Array, LinkedList, Tree, Graph, DP |
| 4 | **SQL (PostgreSQL)** | JOIN, Window Functions, CTE, Stored Functions, Optimization |
| 5 | **Java Spring** | Spring Core, Boot, Security, AOP, JPA, Testing |

## 📁 Cấu Trúc Thư Mục

```
Study/
├── README.md
├── content/                          # Nội dung ôn tập (Markdown)
│   ├── 01-java/
│   │   ├── ly-thuyet.md             # Lý thuyết Java chi tiết
│   │   ├── bai-tap.md               # 10 bài tập (Trung bình → Rất Khó)
│   │   └── dap-an.md                # Đáp án nhiều cách giải
│   ├── 02-design-patterns/
│   │   ├── ly-thuyet.md
│   │   ├── bai-tap.md
│   │   └── dap-an.md
│   ├── 03-dsa/
│   │   ├── ly-thuyet.md
│   │   ├── bai-tap.md
│   │   └── dap-an.md
│   ├── 04-sql-postgresql/
│   │   ├── ly-thuyet.md
│   │   ├── bai-tap.md
│   │   └── dap-an.md
│   └── 05-java-spring/
│       ├── ly-thuyet.md
│       ├── bai-tap.md
│       └── dap-an.md
└── study-app/                        # Ứng dụng Web Spring Boot
    ├── pom.xml
    └── src/
        ├── main/
        │   ├── java/com/study/
        │   │   ├── StudyApplication.java
        │   │   ├── controller/       # Web & API controllers
        │   │   ├── model/            # Data models
        │   │   └── service/          # Business logic
        │   └── resources/
        │       ├── application.properties
        │       ├── templates/        # Thymeleaf templates
        │       └── static/           # CSS, JS
        └── test/
```

## 🚀 Hướng Dẫn Chạy Ứng Dụng

### Yêu cầu
- **Java 17+** (JDK)
- **Maven 3.6+**

### Cách chạy

```bash
# Clone repository
git clone https://github.com/lechieu26/Study.git
cd Study/study-app

# Build và chạy
mvn spring-boot:run
```

Ứng dụng sẽ chạy tại: **http://localhost:8080**

### Build JAR

```bash
cd study-app
mvn clean package -DskipTests
java -jar target/study-roadmap-1.0.0.jar
```

## 🌐 Tính Năng Ứng Dụng Web

1. **Trang chủ**: Hiển thị 5 danh mục ôn tập với số lượng bài tập.
2. **Lý thuyết**: Nội dung lý thuyết chi tiết, có highlight code syntax.
3. **Bài tập**: Danh sách bài tập theo độ khó (Trung bình → Rất Khó).
4. **Code Editor**: Khung soạn thảo code với CodeMirror (syntax highlighting, auto-indent).
5. **Nút Check**: Biên dịch và chạy code Java, chấm điểm tự động, hiển thị kết quả.
6. **Xem Đáp Án**: Hiển thị nhiều cách giải khác nhau cho mỗi bài.
7. **Lưu tiến trình**: Code được lưu tự động trong localStorage.
8. **Giao diện tiếng Việt**: Toàn bộ nội dung hiển thị bằng tiếng Việt.
9. **Dark Theme**: Giao diện tối, thân thiện với mắt.
10. **Responsive**: Hỗ trợ desktop và mobile.

## 🛠️ Công Nghệ Sử Dụng

- **Backend**: Java 17, Spring Boot 3.2, Thymeleaf
- **Frontend**: HTML5, CSS3, JavaScript, CodeMirror, Highlight.js
- **Markdown**: CommonMark (parse markdown → HTML)
- **Code Execution**: Java Compiler (javac + java runtime)
