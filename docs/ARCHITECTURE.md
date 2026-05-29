# Kiến Trúc Hệ Thống — Study Platform

## Tổng quan

Study Platform là ứng dụng web **monolithic** sử dụng kiến trúc **MVC (Model-View-Controller)** truyền thống của Spring Boot. Ứng dụng chia thành 3 layer rõ ràng:

```
┌─────────────────────────────────────────────────────────┐
│                      CLIENT (Browser)                    │
│  ┌─────────┐  ┌──────────┐  ┌──────────┐  ┌─────────┐ │
│  │ HTML/CSS │  │CodeMirror│  │Highlight │  │  app.js  │ │
│  │Thymeleaf│  │  Editor  │  │   .js    │  │  Logic   │ │
│  └────┬────┘  └────┬─────┘  └──────────┘  └────┬────┘ │
│       │             │                           │       │
└───────┼─────────────┼───────────────────────────┼───────┘
        │ Page Load   │ POST /api/check           │ localStorage
        │ (HTML)      │ (JSON)                    │ (save/load)
        ▼             ▼                           ▼
┌─────────────────────────────────────────────────────────┐
│                   BACKEND (Spring Boot)                   │
│                                                          │
│  ┌─────────────── Controller Layer ───────────────────┐ │
│  │ StudyController          │ ApiController            │ │
│  │ (Thymeleaf pages)        │ (REST API - JSON)        │ │
│  │ GET /, /topic/{id},      │ POST /api/check          │ │
│  │ /topic/{id}/theory,      │   → routes by topicId    │ │
│  │ /topic/{id}/exercise/{n} │                          │ │
│  └──────────┬───────────────┴──────────┬───────────────┘ │
│             │                          │                  │
│  ┌──────────▼──────── Service Layer ───▼───────────────┐ │
│  │ ContentService     │ CodeExecutionSvc │ SqlExecSvc   │ │
│  │ • Load Markdown    │ • javac compile  │ • H2 DB      │ │
│  │ • Parse exercises  │ • java run       │ • Execute SQL│ │
│  │ • Render HTML      │ • Score output   │ • Format     │ │
│  │ • Parse solutions  │ • Timeout 10s    │ • ROLLBACK   │ │
│  └──────────┬─────────┴────────┬─────────┴──────┬──────┘ │
│             │                  │                │         │
│  ┌──────────▼─── Model Layer ──▼────────────────▼──────┐ │
│  │ Topic │ Exercise │ Solution │ CodeCheckRequest/Result│ │
│  └─────────────────────────────────────────────────────┘ │
│                                                          │
└──────────────────────────────────────────────────────────┘
        │
        ▼
┌─────────────────────────────────────────────────────────┐
│                    DATA LAYER                            │
│  ┌──────────────┐  ┌───────────────┐  ┌──────────────┐ │
│  │ Markdown Files│  │ JDK Compiler  │  │ H2 In-Memory │ │
│  │ (content/)    │  │ (javac/java)  │  │  Database    │ │
│  │ • ly-thuyet   │  │ • Temp files  │  │ • 4 tables   │ │
│  │ • bai-tap     │  │ • Process API │  │ • PG mode    │ │
│  │ • dap-an      │  │ • Sandbox     │  │ • Sample data│ │
│  └──────────────┘  └───────────────┘  └──────────────┘ │
└─────────────────────────────────────────────────────────┘
```

---

## Frontend (Client-side)

### Công nghệ

| File | Vai trò | Chi tiết |
|------|---------|----------|
| `templates/index.html` | Trang chủ | Topic grid, progress badges, hero stats |
| `templates/topic.html` | Chi tiết chủ đề | Exercise list với difficulty badges |
| `templates/theory.html` | Trang lý thuyết | Rendered markdown với Highlight.js |
| `templates/exercise.html` | Code editor | CodeMirror editor, results panel, solutions |
| `static/css/style.css` | Styling | CSS Variables, dark theme, responsive |
| `static/js/app.js` | Client logic | Editor init, AJAX, localStorage, sidebar |

### Thymeleaf Templates

Thymeleaf là **server-side template engine** — HTML được render trên server trước khi gửi cho client:

```html
<!-- Server render dynamic data vào HTML -->
<h3 th:text="${topic.name}">Topic Name</h3>
<div th:utext="${topic.theoryHtml}">Theory content</div>

<!-- Conditional rendering -->
<div th:text="${topic.id == 'sql'} ? 'SQL' : 'Java'">Java</div>
```

**Tại sao Thymeleaf thay vì React/Vue?**
- Đơn giản, ít dependencies
- SEO-friendly (server-side rendering)
- Phù hợp với content-heavy app
- Không cần build step riêng cho frontend

### CodeMirror Editor

```javascript
// Editor khởi tạo theo ngôn ngữ
function initEditor(lang) {
    var editorMode = (lang === 'sql') ? 'text/x-sql' : 'text/x-java';
    editor = CodeMirror.fromTextArea(textarea, {
        mode: editorMode,
        theme: 'dracula',
        lineNumbers: true,
        // ...
    });
}
```

### Client-side Data Flow

```
User action → app.js handler → AJAX/localStorage → UI update

Cụ thể:
1. Code editor: User gõ code → CodeMirror captures → Auto-save localStorage
2. Check code:  Click "Kiểm tra" → POST /api/check → Hiển thị kết quả
3. View solution: Click "Xem đáp án" → Toggle solution panel
4. Progress: Hoàn thành bài → localStorage flag → Badge update
```

### CSS Architecture

```css
/* CSS Variables cho theming */
:root {
    --primary: #4f46e5;
    --bg: #0f172a;
    --bg-card: #1e293b;
    --text: #e2e8f0;
    /* ... */
}

/* Naming convention: BEM-like */
.topic-card { }
.topic-card .topic-icon { }
.topic-card .topic-stats { }

/* Responsive breakpoints */
@media (max-width: 768px) { /* Tablet */ }
@media (max-width: 480px) { /* Mobile */ }
```

---

## Backend (Server-side)

### Controller Layer

#### StudyController (Page Routes)

| Route | Method | View | Mô tả |
|-------|--------|------|-------|
| `/` | GET | `index.html` | Trang chủ — topic grid |
| `/topic/{id}` | GET | `topic.html` | Chi tiết chủ đề — exercise list |
| `/topic/{id}/theory` | GET | `theory.html` | Lý thuyết (rendered markdown) |
| `/topic/{id}/exercise/{n}` | GET | `exercise.html` | Code editor + bài tập |

#### ApiController (REST API)

| Route | Method | Request | Response | Mô tả |
|-------|--------|---------|----------|-------|
| `/api/check` | POST | `CodeCheckRequest` | `CodeCheckResult` | Chạy code và chấm điểm |

**Routing logic:**
```java
if ("sql".equals(request.getTopicId())) {
    result = sqlExecutionService.executeSql(request.getCode());
} else {
    result = codeExecutionService.executeJavaCode(request.getCode());
}
```

### Service Layer

#### ContentService

Chịu trách nhiệm load và parse nội dung giáo dục:

```
Startup (@PostConstruct)
  │
  ├─ Load 5 topics (java, design-patterns, dsa, sql, spring)
  │   │
  │   ├─ Mỗi topic load 3 files:
  │   │   ├── ly-thuyet.md → parse Markdown → HTML (CommonMark + GFM Tables)
  │   │   ├── bai-tap.md   → parse exercises (regex: "## Bài N: title")
  │   │   └── dap-an.md    → parse solutions (regex: "### Cách N: approach")
  │   │
  │   └─ Cache in memory (Map<String, Topic>)
  │
  └─ Content sources (ưu tiên):
      1. File system: ../content/ (dev mode)
      2. Classpath: content/ (packaged JAR)
```

**Content loading priority:**
```java
// 1. Tìm file trên filesystem trước (cho development)
Path filePath = Path.of(System.getProperty("content.dir", "../content"), relativePath);
if (Files.exists(filePath)) return Files.readString(filePath);

// 2. Fallback: classpath (cho production JAR)
Resource resource = new ClassPathResource("content/" + relativePath);
if (resource.exists()) return new String(resource.getInputStream().readAllBytes());
```

#### CodeExecutionService

Chạy code Java trên server (⚠️ security concern — chỉ dùng cho trusted environment):

```
Input: String code
  │
  ├─ 1. Extract class name (regex: "public class (\w+)")
  ├─ 2. Tạo temp directory
  ├─ 3. Ghi file .java
  ├─ 4. Chạy javac (compile) → timeout 10s
  │      └─ Nếu lỗi → return compile error
  ├─ 5. Chạy java (execute) → timeout 10s
  │      └─ Nếu timeout → return "vòng lặp vô hạn"
  ├─ 6. Evaluate score (0-100) dựa trên:
  │      ├─ Output có kết quả (+40)
  │      ├─ Có main method (+10)
  │      ├─ Có class definition (+10)
  │      ├─ Không có TODO/... (+10)
  │      ├─ Output nhiều dòng (+10)
  │      ├─ Error handling (+5)
  │      ├─ OOP practices (+5)
  │      └─ Modern Java features (+5)
  ├─ 7. Generate feedback message
  └─ 8. Cleanup temp directory
```

#### SqlExecutionService

Thực thi SQL trên H2 in-memory database:

```
Khởi tạo (lazy, synchronized):
  ├─ Tạo H2 connection (PostgreSQL mode)
  ├─ Tạo 4 bảng:
  │   ├── phong_ban (5 phòng ban)
  │   ├── nhan_vien (15 nhân viên)
  │   ├── du_an (4 dự án)
  │   └── phan_cong (assignments)
  └─ Flag initialized = true

Execution flow:
  Input: String sql
  │
  ├─ 1. Parse multi-statement (split by ";", respect string literals)
  ├─ 2. BEGIN TRANSACTION
  ├─ 3. Execute mỗi statement:
  │      ├─ SELECT → executeQuery() → format ASCII table
  │      └─ DML    → executeUpdate() → "N rows affected"
  ├─ 4. ROLLBACK (luôn rollback để bảo vệ dữ liệu)
  ├─ 5. Calculate score dựa trên complexity:
  │      ├─ JOIN (+15), GROUP BY (+10)
  │      ├─ HAVING (+10), Window Functions (+20)
  │      ├─ Subquery (+15), CTE (+15)
  │      └─ CASE WHEN (+10)
  └─ 6. Return formatted result + score
```

### Model Layer

```
Topic
  ├─ id: String (java, design-patterns, dsa, sql, spring)
  ├─ name: String (tên hiển thị)
  ├─ description: String
  ├─ icon: String (emoji)
  ├─ theoryHtml: String (rendered HTML)
  └─ exercises: List<Exercise>

Exercise
  ├─ id: int
  ├─ title: String
  ├─ difficulty: String (Trung bình, Khó, Rất Khó)
  ├─ descriptionHtml: String
  └─ solutions: List<Solution>

Solution
  ├─ approach: String (tên cách giải)
  └─ codeHtml: String (rendered code HTML)

CodeCheckRequest
  ├─ code: String
  ├─ topicId: String
  └─ exerciseId: int

CodeCheckResult
  ├─ success: boolean
  ├─ output: String
  ├─ error: String
  ├─ score: int (0-100)
  └─ feedback: String
```

---

## Content Layer

### Markdown Format

Nội dung giáo dục lưu dưới dạng Markdown thuần (`.md`), tách biệt khỏi application code:

```
content/
├── 01-java/
│   ├── ly-thuyet.md        # Lý thuyết: headings, code blocks, tables, blockquotes
│   ├── bai-tap.md          # Bài tập: pattern "## Bài N: Title\n**Độ khó: X**"
│   └── dap-an.md           # Đáp án: pattern "## Bài N\n### Cách M: Approach"
```

**Tại sao Markdown thay vì Database?**
- Version control (Git diff cho content changes)
- Dễ chỉnh sửa bằng bất kỳ text editor
- Không cần database setup
- Portable — copy files là xong
- Dễ contribute (PRs cho content)

### Exercise Parsing

```
Input: bai-tap.md

## Bài 1: Reverse LinkedList                 ← Exercise start
**Độ khó: Trung bình**                       ← Difficulty
                                              ← Description (markdown → HTML)
Cho một linked list đơn, đảo ngược nó...

## Bài 2: LRU Cache                          ← Next exercise
**Độ khó: Khó**
...
```

```
Input: dap-an.md

## Bài 1: Reverse LinkedList                 ← Match exercise ID
### Cách 1: Iterative                        ← Solution approach
```java                                       ← Code block
public ListNode reverse(ListNode head) {...}
```                                           ← End block
### Cách 2: Recursive                        ← Another approach
...
```

---

## Design Decisions

### Tại sao Spring Boot + Thymeleaf (không phải React/Vue)?

| Tiêu chí | Spring + Thymeleaf | Spring + React/Vue |
|---------|-------------------|-------------------|
| Complexity | Thấp (1 project) | Cao (2 projects, CORS, proxy) |
| Build | 1 command (`mvn package`) | 2 builds (npm + mvn) |
| SEO | Server-rendered | Cần SSR/prerender |
| Deployment | 1 JAR file | 2 services hoặc static hosting |
| Learning curve | Thấp | Cao (thêm React/Vue ecosystem) |
| Phù hợp cho | Content app, CRUD app | SPA, real-time app |

**Kết luận:** Với ứng dụng chủ yếu hiển thị content + editor đơn giản, Thymeleaf là lựa chọn pragmatic.

### Tại sao H2 thay vì PostgreSQL cho SQL exercises?

- **Zero setup:** Không cần install hay config database
- **Portable:** Chạy trên mọi OS
- **PostgreSQL mode:** `MODE=PostgreSQL` hỗ trợ phần lớn cú pháp PG
- **In-memory:** Nhanh, dữ liệu reset mỗi lần khởi động
- **Safe:** Mỗi query ROLLBACK → không ảnh hưởng dữ liệu mẫu

### Tại sao Markdown thay vì Database cho content?

- **Git-friendly:** Track changes, diff, blame, PRs
- **Portable:** Không phụ thuộc infra
- **Developer-friendly:** Edit bằng VS Code, IntelliJ, bất kỳ editor
- **Separation of concerns:** Content team có thể contribute mà không touch code

---

## Security Considerations

| Risk | Mitigation | Severity |
|------|-----------|----------|
| Code injection (Java) | Timeout 10s, temp directory, output truncation | **Cao** — cần sandbox trong production |
| SQL injection | ROLLBACK sau mỗi execution, max 100 rows | Trung bình |
| File system access | Temp dir isolation, cleanup after execution | Trung bình |
| DoS (heavy code) | Timeout, process kill | Trung bình |

> **⚠️ Production:** Nếu deploy public, cần thêm: Docker container isolation cho code execution, rate limiting, authentication. Hiện tại app phù hợp cho **trusted environment** (local, nội bộ).

---

## CI/CD Pipeline

```
Push/PR → GitHub Actions
  │
  ├─ Job: Build & Test
  │   ├── Checkout
  │   ├── Setup JDK 17
  │   ├── mvn clean package -DskipTests
  │   ├── mvn test
  │   └── Upload JAR artifact (only on main)
  │
  └─ Job: Docker Build (only on main push)
      ├── Build Docker image
      ├── Start container
      ├── Health check (curl localhost:8080)
      └── Stop container
```
