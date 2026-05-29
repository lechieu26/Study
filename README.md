# Study Platform

**Nền tảng ôn tập lập trình** tương tác với lý thuyết chi tiết, code editor trực tiếp trên trình duyệt, và chấm điểm tự động.

> Live demo: Deploy bằng Docker hoặc các nền tảng cloud — xem [DEPLOY.md](DEPLOY.md)

---

## Tính năng

| Tính năng | Mô tả |
|-----------|-------|
| **Lý thuyết tương tác** | Nội dung Markdown → HTML với syntax highlighting, bảng responsive, blockquote styled |
| **Code Editor** | CodeMirror editor với syntax highlighting, auto-indent, line numbers |
| **Chạy code Java** | Biên dịch và chạy Java trực tiếp trên server (`javac` + `java`) |
| **Chạy SQL** | Thực thi SQL trên H2 database (PostgreSQL mode) với dữ liệu mẫu |
| **Chấm điểm tự động** | Đánh giá code dựa trên output, cấu trúc, kỹ thuật sử dụng |
| **Nhiều cách giải** | Mỗi bài tập có nhiều đáp án với approach khác nhau |
| **Lưu tiến trình** | Code và trạng thái bài tập lưu tự động trong localStorage |
| **Dark Theme** | Giao diện tối, thân thiện với mắt |
| **Responsive** | Hỗ trợ desktop, tablet, và mobile |

## Chủ đề ôn tập

| # | Chủ đề | Nội dung |
|---|--------|----------|
| 1 | **Java** | OOP, Collections, Stream API, Generics, Multi-threading, Memory Model |
| 2 | **Design Patterns** | 16 patterns: Creational, Structural, Behavioral với ví dụ thực tế |
| 3 | **DSA** | Array, LinkedList, Tree, Graph, DP, Sorting, Searching, Backtracking |
| 4 | **SQL (PostgreSQL)** | JOIN, Window Functions, CTE, Triggers, Indexes, Query Optimization |
| 5 | **Java Spring** | Spring Core, Boot, Security, AOP, JPA, Testing, REST API |

---

## Tech Stack

### Backend

| Công nghệ | Phiên bản | Vai trò |
|-----------|----------|---------|
| **Java** | 17 | Ngôn ngữ chính |
| **Spring Boot** | 3.2.5 | Web framework, dependency injection, embedded Tomcat |
| **Thymeleaf** | 3.x | Server-side template engine (HTML rendering) |
| **CommonMark** | 0.21.0 | Markdown parser → HTML (với GFM Tables extension) |
| **H2 Database** | 2.x | In-memory SQL database cho bài tập SQL (PostgreSQL mode) |
| **Maven** | 3.x | Build tool, dependency management |

### Frontend

| Công nghệ | Phiên bản | Vai trò |
|-----------|----------|---------|
| **HTML5 / CSS3** | — | Cấu trúc và giao diện |
| **JavaScript** | ES6 | Logic client-side, AJAX calls |
| **CodeMirror** | 5.65.16 | Code editor (Java + SQL modes) |
| **Highlight.js** | 11.9.0 | Syntax highlighting cho code blocks trong lý thuyết |

### DevOps

| Công nghệ | Vai trò |
|-----------|---------|
| **Docker** | Container hóa ứng dụng (multi-stage build) |
| **GitHub Actions** | CI/CD pipeline (build, test, Docker verify) |
| **Render / Railway** | Cloud deployment |

---

## Cấu trúc Project

```
Study/
├── .github/
│   └── workflows/
│       └── ci.yml                    # GitHub Actions CI pipeline
│
├── content/                          # [Content Layer] Nội dung giáo dục (Markdown)
│   ├── 01-java/
│   │   ├── ly-thuyet.md             # Lý thuyết Java chi tiết
│   │   ├── bai-tap.md               # 10 bài tập (Trung bình → Rất Khó)
│   │   └── dap-an.md                # Đáp án nhiều cách giải
│   ├── 02-design-patterns/          # Design Patterns theory + exercises
│   ├── 03-dsa/                      # Data Structures & Algorithms
│   ├── 04-sql-postgresql/           # SQL + PostgreSQL
│   └── 05-java-spring/             # Spring Framework
│
├── study-app/                        # [Application] Spring Boot application
│   ├── pom.xml                      # Maven dependencies & build config
│   └── src/
│       ├── main/
│       │   ├── java/com/study/      # ── Backend (Java) ──
│       │   │   ├── StudyApplication.java          # Entry point
│       │   │   ├── controller/
│       │   │   │   ├── StudyController.java       # Page routes (Thymeleaf)
│       │   │   │   └── ApiController.java         # REST API (/api/check)
│       │   │   ├── service/
│       │   │   │   ├── ContentService.java        # Markdown → HTML, exercises parser
│       │   │   │   ├── CodeExecutionService.java  # Java code compiler + runner
│       │   │   │   └── SqlExecutionService.java   # SQL executor (H2 database)
│       │   │   └── model/
│       │   │       ├── Topic.java                 # Chủ đề ôn tập
│       │   │       ├── Exercise.java              # Bài tập
│       │   │       ├── Solution.java              # Đáp án
│       │   │       ├── CodeCheckRequest.java      # Request DTO
│       │   │       └── CodeCheckResult.java       # Response DTO
│       │   │
│       │   └── resources/           # ── Frontend (Web) ──
│       │       ├── application.properties         # Server config
│       │       ├── static/
│       │       │   ├── css/
│       │       │   │   └── style.css              # Toàn bộ styling (dark theme)
│       │       │   └── js/
│       │       │       └── app.js                 # Client logic, editor, AJAX
│       │       └── templates/
│       │           ├── index.html                 # Trang chủ (topic grid)
│       │           ├── topic.html                 # Chi tiết chủ đề (exercise list)
│       │           ├── theory.html                # Trang lý thuyết
│       │           └── exercise.html              # Code editor + bài tập
│       │
│       └── test/                    # ── Tests ──
│           └── java/com/study/
│               └── StudyApplicationTests.java     # Spring context test
│
├── docs/                            # Tài liệu dự án
│   └── ARCHITECTURE.md              # Kiến trúc chi tiết
│
├── Dockerfile                       # Multi-stage Docker build
├── render.yaml                      # Render.com deployment config
├── DEPLOY.md                        # Hướng dẫn deploy chi tiết
├── CONTRIBUTING.md                  # Hướng dẫn đóng góp
└── README.md                        # Tài liệu chính (file này)
```

**Phân tách Frontend / Backend:**

| Layer | Vị trí | Công nghệ | Vai trò |
|-------|--------|-----------|---------|
| **Backend** | `src/main/java/com/study/` | Java, Spring Boot | Controller, Service, Model, API |
| **Frontend** | `src/main/resources/static/` + `templates/` | HTML, CSS, JS, Thymeleaf | UI, Editor, Styling |
| **Content** | `content/` | Markdown | Nội dung giáo dục (tách biệt khỏi code) |

---

## Quick Start

### Yêu cầu

- **Java 17+** (JDK, không phải JRE — cần `javac` cho code execution)
- **Maven 3.6+**

### Chạy local

```bash
# Clone
git clone https://github.com/lechieu26/Study.git
cd Study/study-app

# Build & Run
mvn spring-boot:run
```

Mở trình duyệt: **http://localhost:8080**

### Build JAR

```bash
cd study-app
mvn clean package -DskipTests
java -jar target/study-roadmap-1.0.0.jar
```

### Chạy bằng Docker

```bash
docker build -t study-platform .
docker run -p 8080:8080 study-platform
```

---

## Cách hoạt động

### 1. Content Pipeline

```
Markdown files (content/)
    │
    ▼
ContentService.java (parse + render)
    │  ├─ CommonMark parser (GFM Tables extension)
    │  ├─ Exercise parser (regex-based)
    │  └─ Solution parser
    ▼
HTML (Thymeleaf templates)
    │
    ▼
Browser (Highlight.js syntax highlighting)
```

### 2. Code Execution Flow

```
User viết code trong CodeMirror editor
    │
    ▼
JavaScript gửi POST /api/check
    { code: "...", topicId: "java|sql", exerciseId: 1 }
    │
    ▼
ApiController routes theo topicId:
    ├─ topicId = "sql"  → SqlExecutionService (H2 database)
    └─ topicId != "sql" → CodeExecutionService (javac + java)
    │
    ▼
CodeCheckResult { success, output, error, score, feedback }
    │
    ▼
JavaScript hiển thị kết quả + điểm + phản hồi
```

### 3. SQL Execution

```
SqlExecutionService:
    ├─ H2 in-memory database (PostgreSQL compatibility mode)
    ├─ 4 bảng mẫu: phong_ban, nhan_vien, du_an, phan_cong
    ├─ 15 nhân viên, 5 phòng ban, 4 dự án
    ├─ Mỗi query chạy trong transaction → ROLLBACK (dữ liệu không thay đổi)
    └─ Timeout: 10 giây, max 100 rows
```

---

## Tài liệu

| Tài liệu | Nội dung |
|-----------|----------|
| [ARCHITECTURE.md](docs/ARCHITECTURE.md) | Kiến trúc hệ thống chi tiết, data flow, design decisions |
| [CONTRIBUTING.md](CONTRIBUTING.md) | Hướng dẫn đóng góp, coding conventions, PR process |
| [DEPLOY.md](DEPLOY.md) | Hướng dẫn deploy (Render, Railway, Fly.io, Cloud Run, Docker) |

---

## License

MIT
