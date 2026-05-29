# Hướng Dẫn Đóng Góp

Cảm ơn bạn đã quan tâm đến việc đóng góp cho Study Platform!

---

## Quick Start cho Contributors

```bash
# 1. Fork repo trên GitHub

# 2. Clone về local
git clone https://github.com/<your-username>/Study.git
cd Study/study-app

# 3. Tạo branch
git checkout -b feature/ten-tinh-nang

# 4. Chạy app để test
mvn spring-boot:run
# Mở http://localhost:8080

# 5. Build & test
mvn clean package

# 6. Commit & push
git add -A
git commit -m "feat: mô tả ngắn gọn"
git push origin feature/ten-tinh-nang

# 7. Tạo Pull Request trên GitHub
```

---

## Cấu trúc Project

Xem [ARCHITECTURE.md](docs/ARCHITECTURE.md) để hiểu chi tiết kiến trúc.

**Tóm tắt:**

| Muốn sửa... | Vào đâu |
|-------------|---------|
| Nội dung lý thuyết | `content/XX-topic/ly-thuyet.md` |
| Bài tập mới | `content/XX-topic/bai-tap.md` + `dap-an.md` |
| Giao diện (CSS) | `study-app/src/main/resources/static/css/style.css` |
| Client logic (JS) | `study-app/src/main/resources/static/js/app.js` |
| HTML templates | `study-app/src/main/resources/templates/` |
| Backend logic | `study-app/src/main/java/com/study/` |
| CI/CD | `.github/workflows/ci.yml` |
| Docker | `Dockerfile` |

---

## Thêm Nội Dung Mới

### Thêm lý thuyết

Chỉnh sửa `content/XX-topic/ly-thuyet.md`:

```markdown
## Heading (Level 2)

### Sub-heading (Level 3)

Text bình thường với **bold** và `inline code`.

| Cột 1 | Cột 2 | Cột 3 |
|-------|-------|-------|
| Data  | Data  | Data  |

> **Lưu ý:** Blockquote cho tips/warnings

```java
// Code block với syntax highlight
public class Example {
    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
```​
```

### Thêm bài tập

Thêm vào `content/XX-topic/bai-tap.md`:

```markdown
## Bài N: Tên Bài Tập

**Độ khó: Trung bình**

Mô tả bài tập...

**Yêu cầu:**
- Yêu cầu 1
- Yêu cầu 2

**Ví dụ:**
- Input: ...
- Output: ...
```

Thêm đáp án vào `content/XX-topic/dap-an.md`:

```markdown
## Bài N: Tên Bài Tập

### Cách 1: Tên Approach

```java
// Code đáp án
```​

### Cách 2: Tên Approach Khác

```java
// Code đáp án khác
```​
```

### Thêm chủ đề mới

1. Tạo folder: `content/06-ten-chu-de/`
2. Tạo 3 files: `ly-thuyet.md`, `bai-tap.md`, `dap-an.md`
3. Đăng ký topic trong `ContentService.java`:

```java
// Trong method loadTopics()
topics.put("ten-id", createTopic("ten-id", "Tên Hiển Thị", "Mô tả", "🎯"));

// Trong method getContentPath()
case "ten-id" -> "06-ten-chu-de/" + filename;
```

---

## Conventions

### Git Commit Messages

Sử dụng [Conventional Commits](https://www.conventionalcommits.org/):

```
<type>: <description>

Ví dụ:
feat: thêm chủ đề Docker vào danh sách ôn tập
fix: sửa lỗi hiển thị table trên mobile
docs: cập nhật README với hướng dẫn deploy
style: cải thiện responsive cho exercise page
content: bổ sung bài tập Java Collections
refactor: tách ContentService thành nhiều class
test: thêm unit test cho SqlExecutionService
ci: thêm step kiểm tra Docker build
```

### Code Style

**Java:**
- Indentation: 4 spaces
- Naming: camelCase (methods, variables), PascalCase (classes)
- Constructor injection (không dùng `@Autowired` trên field)
- Final fields cho dependencies

**CSS:**
- CSS Variables cho theming (`:root { --primary: ... }`)
- BEM-like naming (`.component-name .child-element`)
- Mobile-first responsive

**JavaScript:**
- `var` (ES5 compatible cho tương thích rộng)
- `function` declarations (không arrow functions top-level)
- IIFE cho scope isolation

### Content Style

- Tiếng Việt cho tất cả nội dung giáo dục
- Code comments bằng tiếng Việt khi giải thích concept
- Mỗi section có ví dụ code thực tế
- Tables cho so sánh, blockquotes cho tips/warnings

---

## Yêu cầu kỹ thuật

- **Java 17+** (JDK)
- **Maven 3.6+**
- **Docker** (optional, cho container testing)

### Build commands

```bash
# Build (skip tests)
cd study-app && mvn clean package -DskipTests

# Build + Test
cd study-app && mvn clean package

# Run tests only
cd study-app && mvn test

# Run locally
cd study-app && mvn spring-boot:run

# Docker
docker build -t study-platform .
docker run -p 8080:8080 study-platform
```

---

## Pull Request Guidelines

1. **Branch naming:** `feature/`, `fix/`, `docs/`, `content/`
2. **Mô tả rõ ràng** thay đổi trong PR description
3. **CI phải pass** (build + test)
4. **Test thủ công** trên browser trước khi submit
5. **Ảnh chụp màn hình** cho UI changes
