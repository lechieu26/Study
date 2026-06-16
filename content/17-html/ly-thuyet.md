# HTML - Lý Thuyết Từ Cơ Bản Đến Nâng Cao

## Mục lục

1. [Giới thiệu về HTML](#1-giới-thiệu-về-html)
2. [Cấu trúc tài liệu HTML](#2-cấu-trúc-tài-liệu-html)
3. [Thẻ văn bản (Text Elements)](#3-thẻ-văn-bản-text-elements)
4. [Liên kết và Hình ảnh](#4-liên-kết-và-hình-ảnh)
5. [Danh sách (Lists)](#5-danh-sách-lists)
6. [Bảng (Tables)](#6-bảng-tables)
7. [Biểu mẫu (Forms)](#7-biểu-mẫu-forms)
8. [Semantic HTML](#8-semantic-html)
9. [Multimedia](#9-multimedia)
10. [Meta Tags và SEO](#10-meta-tags-và-seo)
11. [HTML5 APIs](#11-html5-apis)
12. [Accessibility (ARIA)](#12-accessibility-aria)
13. [Best Practices](#13-best-practices)

---

## 1. Giới thiệu về HTML

### 1.1 HTML là gì?

HTML (HyperText Markup Language) là **ngôn ngữ đánh dấu** dùng để tạo cấu trúc nội dung trang web. HTML không phải là ngôn ngữ lập trình - nó chỉ định nghĩa **cấu trúc** và **ngữ nghĩa** của nội dung.

### 1.2 Lịch sử phát triển

| Năm | Sự kiện |
|-----|---------|
| 1991 | Tim Berners-Lee tạo ra HTML tại CERN |
| 1995 | HTML 2.0 - phiên bản chuẩn đầu tiên |
| 1997 | HTML 3.2 - thêm tables, applets |
| 1999 | HTML 4.01 - CSS support, scripting |
| 2000 | XHTML 1.0 - HTML theo quy tắc XML |
| 2014 | HTML5 - multimedia, semantic, APIs |
| 2017 | HTML 5.2 - Payment Request API, dialog element |
| 2021 | HTML Living Standard - WHATWG duy trì liên tục |

### 1.3 Công cụ cần thiết

- **Trình soạn thảo:** VS Code, Sublime Text, WebStorm
- **Trình duyệt:** Chrome DevTools (F12), Firefox Developer Tools
- **Extension hữu ích:** Live Server, Emmet, Prettier, HTML CSS Support

---

## 2. Cấu trúc tài liệu HTML

### 2.1 Bộ khung cơ bản

```html
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tiêu đề trang</title>
</head>
<body>
    <h1>Xin chào!</h1>
    <p>Đây là trang web đầu tiên.</p>
</body>
</html>
```

**Giải thích:**
- `<!DOCTYPE html>`: Khai báo HTML5
- `<html lang="vi">`: Phần tử gốc, thuộc tính `lang` giúp trình duyệt và screen reader
- `<head>`: Chứa metadata (không hiển thị trên trang)
- `<meta charset="UTF-8">`: Mã hóa ký tự Unicode
- `<meta name="viewport">`: Thiết lập viewport cho mobile
- `<body>`: Nội dung hiển thị trên trang

### 2.2 Phần tử (Element) và Thẻ (Tag)

```html
<!-- Cú pháp cơ bản -->
<tagname attribute="value">Nội dung</tagname>

<!-- Thẻ tự đóng (self-closing) -->
<img src="image.jpg" alt="Mô tả" />
<br />
<hr />
<input type="text" />

<!-- Thẻ lồng nhau (nesting) -->
<div>
    <p>Đoạn văn trong <strong>thẻ div</strong></p>
</div>
```

### 2.3 Thuộc tính (Attributes)

```html
<!-- Thuộc tính toàn cục (Global Attributes) -->
<div id="unique-id"           <!-- ID duy nhất -->
     class="ten-lop"          <!-- Tên class CSS -->
     style="color: red;"      <!-- CSS inline -->
     title="Tooltip text"     <!-- Tooltip khi hover -->
     data-info="custom"       <!-- Thuộc tính tự định nghĩa -->
     hidden                   <!-- Ẩn phần tử -->
     tabindex="0"             <!-- Thứ tự tab -->
     contenteditable="true"   <!-- Cho phép chỉnh sửa -->
     draggable="true">        <!-- Cho phép kéo thả -->
</div>
```

### 2.4 Comment

```html
<!-- Đây là comment đơn dòng -->

<!--
    Đây là comment
    nhiều dòng
-->
```

---

## 3. Thẻ văn bản (Text Elements)

### 3.1 Heading (Tiêu đề)

```html
<h1>Heading 1 - Tiêu đề chính (1 lần duy nhất mỗi trang)</h1>
<h2>Heading 2 - Tiêu đề phụ</h2>
<h3>Heading 3</h3>
<h4>Heading 4</h4>
<h5>Heading 5</h5>
<h6>Heading 6 - Nhỏ nhất</h6>
```

**Lưu ý:** Chỉ dùng **một `<h1>`** mỗi trang để SEO tốt. Thứ tự heading phải liên tục (h1 → h2 → h3, không nhảy từ h1 → h4).

### 3.2 Đoạn văn và Ngắt dòng

```html
<p>Đây là một đoạn văn bản. Trình duyệt tự động xuống dòng khi hết chiều rộng.</p>
<p>Đây là đoạn văn thứ hai.</p>

<!-- Ngắt dòng trong đoạn văn -->
<p>Dòng thứ nhất<br />Dòng thứ hai</p>

<!-- Đường kẻ ngang -->
<hr />
```

### 3.3 Định dạng văn bản

```html
<strong>In đậm - quan trọng về ngữ nghĩa</strong>
<b>In đậm - chỉ về hình thức</b>

<em>In nghiêng - nhấn mạnh</em>
<i>In nghiêng - chỉ về hình thức</i>

<mark>Đánh dấu nổi bật</mark>
<del>Gạch ngang (đã xóa)</del>
<ins>Gạch chân (thêm mới)</ins>
<sub>Chỉ số dưới: H<sub>2</sub>O</sub>
<sup>Chỉ số trên: x<sup>2</sup></sup>

<small>Chữ nhỏ hơn</small>
<code>Inline code</code>
<kbd>Phím tắt: <kbd>Ctrl</kbd> + <kbd>C</kbd></kbd>
<abbr title="HyperText Markup Language">HTML</abbr>

<blockquote cite="https://example.com">
    Đây là trích dẫn block-level.
</blockquote>

<q>Đây là trích dẫn inline.</q>

<pre>
    Văn bản
    giữ nguyên    định dạng
    và khoảng trắng
</pre>
```

---

## 4. Liên kết và Hình ảnh

### 4.1 Liên kết (Anchor)

```html
<!-- Liên kết cơ bản -->
<a href="https://google.com">Truy cập Google</a>

<!-- Mở tab mới -->
<a href="https://google.com" target="_blank" rel="noopener noreferrer">
    Mở tab mới
</a>

<!-- Liên kết nội bộ (anchor) -->
<a href="#section-2">Đi đến Section 2</a>
<h2 id="section-2">Section 2</h2>

<!-- Liên kết email và điện thoại -->
<a href="mailto:info@example.com">Gửi email</a>
<a href="tel:+84123456789">Gọi điện</a>

<!-- Liên kết tải file -->
<a href="report.pdf" download="bao-cao.pdf">Tải PDF</a>
```

### 4.2 Hình ảnh

```html
<!-- Hình ảnh cơ bản -->
<img src="photo.jpg" alt="Mô tả hình ảnh" width="600" height="400" />

<!-- Hình ảnh responsive với srcset -->
<img src="photo-800.jpg"
     srcset="photo-400.jpg 400w,
             photo-800.jpg 800w,
             photo-1200.jpg 1200w"
     sizes="(max-width: 600px) 400px,
            (max-width: 1000px) 800px,
            1200px"
     alt="Responsive image" />

<!-- Picture element - nhiều nguồn -->
<picture>
    <source media="(min-width: 1024px)" srcset="desktop.jpg" />
    <source media="(min-width: 768px)" srcset="tablet.jpg" />
    <img src="mobile.jpg" alt="Responsive picture" />
</picture>

<!-- Figure với caption -->
<figure>
    <img src="chart.png" alt="Biểu đồ doanh thu" />
    <figcaption>Hình 1: Biểu đồ doanh thu Q1/2024</figcaption>
</figure>
```

---

## 5. Danh sách (Lists)

### 5.1 Các loại danh sách

```html
<!-- Danh sách không thứ tự -->
<ul>
    <li>Mục 1</li>
    <li>Mục 2</li>
    <li>Mục 3</li>
</ul>

<!-- Danh sách có thứ tự -->
<ol type="1" start="1">
    <li>Bước 1</li>
    <li>Bước 2</li>
    <li>Bước 3</li>
</ol>

<!-- Danh sách định nghĩa -->
<dl>
    <dt>HTML</dt>
    <dd>Ngôn ngữ đánh dấu siêu văn bản</dd>
    <dt>CSS</dt>
    <dd>Ngôn ngữ định kiểu</dd>
</dl>

<!-- Danh sách lồng nhau -->
<ul>
    <li>Frontend
        <ul>
            <li>HTML</li>
            <li>CSS</li>
            <li>JavaScript</li>
        </ul>
    </li>
    <li>Backend
        <ul>
            <li>Java</li>
            <li>Python</li>
        </ul>
    </li>
</ul>
```

---

## 6. Bảng (Tables)

### 6.1 Cấu trúc bảng

```html
<table>
    <caption>Bảng điểm sinh viên</caption>
    <thead>
        <tr>
            <th scope="col">STT</th>
            <th scope="col">Họ tên</th>
            <th scope="col">Điểm</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>1</td>
            <td>Nguyễn Văn A</td>
            <td>8.5</td>
        </tr>
        <tr>
            <td>2</td>
            <td>Trần Thị B</td>
            <td>9.0</td>
        </tr>
    </tbody>
    <tfoot>
        <tr>
            <td colspan="2">Trung bình</td>
            <td>8.75</td>
        </tr>
    </tfoot>
</table>
```

### 6.2 Gộp ô (Merge Cells)

```html
<table>
    <tr>
        <th rowspan="2">Tên</th>
        <th colspan="2">Điểm</th>
    </tr>
    <tr>
        <th>Giữa kỳ</th>
        <th>Cuối kỳ</th>
    </tr>
    <tr>
        <td>An</td>
        <td>8.0</td>
        <td>9.0</td>
    </tr>
</table>
```

---

## 7. Biểu mẫu (Forms)

### 7.1 Cấu trúc Form

```html
<form action="/submit" method="POST" enctype="multipart/form-data">
    <!-- Text Input -->
    <label for="username">Tên đăng nhập:</label>
    <input type="text" id="username" name="username"
           placeholder="Nhập tên..." required minlength="3" maxlength="20" />

    <!-- Email -->
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required />

    <!-- Password -->
    <label for="password">Mật khẩu:</label>
    <input type="password" id="password" name="password"
           required minlength="8" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" />

    <!-- Number -->
    <label for="age">Tuổi:</label>
    <input type="number" id="age" name="age" min="1" max="120" step="1" />

    <!-- Date -->
    <input type="date" name="birthday" min="1900-01-01" max="2024-12-31" />

    <!-- Range -->
    <input type="range" name="volume" min="0" max="100" value="50" />

    <!-- Color -->
    <input type="color" name="theme" value="#ff0000" />

    <!-- File Upload -->
    <input type="file" name="avatar" accept="image/*" multiple />

    <!-- Textarea -->
    <label for="bio">Giới thiệu:</label>
    <textarea id="bio" name="bio" rows="4" cols="50" maxlength="500"></textarea>

    <!-- Select -->
    <label for="city">Thành phố:</label>
    <select id="city" name="city">
        <optgroup label="Miền Bắc">
            <option value="hn">Hà Nội</option>
            <option value="hp">Hải Phòng</option>
        </optgroup>
        <optgroup label="Miền Nam">
            <option value="hcm" selected>TP. Hồ Chí Minh</option>
            <option value="dn">Đà Nẵng</option>
        </optgroup>
    </select>

    <!-- Datalist -->
    <input list="languages" name="language" />
    <datalist id="languages">
        <option value="JavaScript" />
        <option value="Python" />
        <option value="Java" />
    </datalist>

    <!-- Radio -->
    <fieldset>
        <legend>Giới tính:</legend>
        <input type="radio" id="male" name="gender" value="male" />
        <label for="male">Nam</label>
        <input type="radio" id="female" name="gender" value="female" />
        <label for="female">Nữ</label>
    </fieldset>

    <!-- Checkbox -->
    <input type="checkbox" id="agree" name="agree" required />
    <label for="agree">Đồng ý điều khoản</label>

    <!-- Hidden -->
    <input type="hidden" name="csrf_token" value="abc123" />

    <!-- Submit -->
    <button type="submit">Gửi</button>
    <button type="reset">Xóa form</button>
</form>
```

### 7.2 Validation Attributes

```html
<input type="text" required />                    <!-- Bắt buộc -->
<input type="text" minlength="3" maxlength="50" /> <!-- Độ dài -->
<input type="number" min="0" max="100" />         <!-- Khoảng giá trị -->
<input type="text" pattern="[A-Za-z]{3,}" />      <!-- Regex pattern -->
<input type="email" />                             <!-- Tự động validate email -->
<input type="url" />                               <!-- Tự động validate URL -->
```

---

## 8. Semantic HTML

### 8.1 Tại sao cần Semantic HTML?

- **SEO tốt hơn:** Search engine hiểu nội dung
- **Accessibility:** Screen reader đọc đúng
- **Code dễ đọc:** Developer hiểu cấu trúc
- **Bảo trì dễ hơn:** Phân biệt rõ các thành phần

### 8.2 Các thẻ Semantic

```html
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <title>Semantic HTML</title>
</head>
<body>
    <header>
        <nav>
            <ul>
                <li><a href="/">Trang chủ</a></li>
                <li><a href="/about">Giới thiệu</a></li>
                <li><a href="/contact">Liên hệ</a></li>
            </ul>
        </nav>
    </header>

    <main>
        <article>
            <header>
                <h1>Tiêu đề bài viết</h1>
                <time datetime="2024-01-15">15/01/2024</time>
            </header>

            <section>
                <h2>Phần 1</h2>
                <p>Nội dung phần 1...</p>
            </section>

            <section>
                <h2>Phần 2</h2>
                <p>Nội dung phần 2...</p>
                <figure>
                    <img src="chart.png" alt="Biểu đồ" />
                    <figcaption>Hình minh họa</figcaption>
                </figure>
            </section>

            <footer>
                <p>Tác giả: Nguyễn Văn A</p>
            </footer>
        </article>

        <aside>
            <h3>Bài viết liên quan</h3>
            <ul>
                <li><a href="#">Bài 1</a></li>
                <li><a href="#">Bài 2</a></li>
            </ul>
        </aside>
    </main>

    <footer>
        <p>&copy; 2024 My Website</p>
        <address>
            Liên hệ: <a href="mailto:info@example.com">info@example.com</a>
        </address>
    </footer>
</body>
</html>
```

### 8.3 So sánh Semantic vs Non-Semantic

```html
<!-- SAI - Non-semantic -->
<div class="header">
    <div class="nav">...</div>
</div>
<div class="main">
    <div class="article">...</div>
    <div class="sidebar">...</div>
</div>
<div class="footer">...</div>

<!-- ĐÚNG - Semantic -->
<header>
    <nav>...</nav>
</header>
<main>
    <article>...</article>
    <aside>...</aside>
</main>
<footer>...</footer>
```

---

## 9. Multimedia

### 9.1 Video

```html
<video width="640" height="360" controls autoplay muted loop poster="thumb.jpg">
    <source src="video.mp4" type="video/mp4" />
    <source src="video.webm" type="video/webm" />
    <track src="subtitles_vi.vtt" kind="subtitles" srclang="vi" label="Tiếng Việt" />
    Trình duyệt không hỗ trợ video.
</video>
```

### 9.2 Audio

```html
<audio controls>
    <source src="audio.mp3" type="audio/mpeg" />
    <source src="audio.ogg" type="audio/ogg" />
    Trình duyệt không hỗ trợ audio.
</audio>
```

### 9.3 Nhúng nội dung (Embed)

```html
<!-- iframe -->
<iframe src="https://www.youtube.com/embed/VIDEO_ID"
        width="560" height="315"
        title="YouTube video"
        allowfullscreen
        loading="lazy">
</iframe>

<!-- embed và object -->
<embed src="file.pdf" type="application/pdf" width="600" height="400" />
```

---

## 10. Meta Tags và SEO

### 10.1 Essential Meta Tags

```html
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="Mô tả trang web (150-160 ký tự)" />
    <meta name="keywords" content="html, css, web development" />
    <meta name="author" content="Tên tác giả" />
    <meta name="robots" content="index, follow" />

    <!-- Open Graph (Facebook, LinkedIn) -->
    <meta property="og:title" content="Tiêu đề" />
    <meta property="og:description" content="Mô tả" />
    <meta property="og:image" content="https://example.com/image.jpg" />
    <meta property="og:url" content="https://example.com" />
    <meta property="og:type" content="website" />

    <!-- Twitter Card -->
    <meta name="twitter:card" content="summary_large_image" />
    <meta name="twitter:title" content="Tiêu đề" />
    <meta name="twitter:description" content="Mô tả" />
    <meta name="twitter:image" content="https://example.com/image.jpg" />

    <!-- Favicon -->
    <link rel="icon" type="image/x-icon" href="/favicon.ico" />
    <link rel="apple-touch-icon" href="/apple-touch-icon.png" />

    <!-- Canonical URL -->
    <link rel="canonical" href="https://example.com/page" />

    <title>Tiêu đề trang | Tên website</title>
</head>
```

---

## 11. HTML5 APIs

### 11.1 Local Storage và Session Storage

```html
<script>
// Local Storage - lưu trữ lâu dài
localStorage.setItem('theme', 'dark');
const theme = localStorage.getItem('theme');
localStorage.removeItem('theme');
localStorage.clear();

// Session Storage - mất khi đóng tab
sessionStorage.setItem('token', 'abc123');
</script>
```

### 11.2 Geolocation API

```html
<script>
navigator.geolocation.getCurrentPosition(
    (position) => {
        console.log('Lat:', position.coords.latitude);
        console.log('Lng:', position.coords.longitude);
    },
    (error) => {
        console.error('Lỗi:', error.message);
    }
);
</script>
```

### 11.3 Drag and Drop

```html
<div draggable="true"
     ondragstart="event.dataTransfer.setData('text', event.target.id)"
     id="drag-item">
    Kéo tới đây
</div>

<div ondrop="drop(event)" ondragover="event.preventDefault()" id="drop-zone">
    Thả vào đây
</div>

<script>
function drop(event) {
    event.preventDefault();
    const id = event.dataTransfer.getData('text');
    event.target.appendChild(document.getElementById(id));
}
</script>
```

### 11.4 Canvas

```html
<canvas id="myCanvas" width="400" height="300"></canvas>
<script>
const canvas = document.getElementById('myCanvas');
const ctx = canvas.getContext('2d');

ctx.fillStyle = '#3498db';
ctx.fillRect(50, 50, 200, 100);

ctx.strokeStyle = '#e74c3c';
ctx.beginPath();
ctx.arc(300, 150, 50, 0, Math.PI * 2);
ctx.stroke();
</script>
```

---

## 12. Accessibility (ARIA)

### 12.1 ARIA Roles và Attributes

```html
<!-- ARIA Roles -->
<div role="navigation">...</div>
<div role="main">...</div>
<div role="alert">Có lỗi xảy ra!</div>
<div role="dialog" aria-modal="true">...</div>

<!-- ARIA Labels -->
<button aria-label="Đóng">X</button>
<input aria-labelledby="label-id" />
<div aria-describedby="help-text">...</div>

<!-- ARIA States -->
<button aria-expanded="false" aria-controls="menu-id">Menu</button>
<div id="menu-id" aria-hidden="true">...</div>
<input aria-invalid="true" aria-errormessage="error-id" />

<!-- Live Regions -->
<div aria-live="polite" aria-atomic="true">
    Nội dung thay đổi sẽ được đọc bởi screen reader
</div>
```

### 12.2 Best Practices Accessibility

```html
<!-- Đúng: Label cho input -->
<label for="email">Email:</label>
<input type="email" id="email" name="email" />

<!-- Đúng: Alt text có ý nghĩa -->
<img src="logo.png" alt="Logo công ty ABC" />
<img src="decoration.png" alt="" />  <!-- Hình trang trí: alt rỗng -->

<!-- Đúng: Skip navigation -->
<a href="#main-content" class="skip-link">Bỏ qua menu</a>
<nav>...</nav>
<main id="main-content">...</main>

<!-- Đúng: Focus management -->
<button tabindex="0">Có thể tab đến</button>
<div tabindex="-1">Chỉ focus bằng JS</div>
```

---

## 13. Best Practices

### 13.1 Quy tắc chung

1. **Luôn khai báo DOCTYPE:** `<!DOCTYPE html>`
2. **Dùng Semantic HTML:** Sử dụng thẻ có ngữ nghĩa thay vì `<div>` cho mọi thứ
3. **Validate HTML:** Dùng W3C Validator (validator.w3.org)
4. **Alt text cho hình ảnh:** Mỗi `<img>` phải có thuộc tính `alt`
5. **Label cho form input:** Mỗi input phải có `<label>` tương ứng
6. **UTF-8 encoding:** Luôn khai báo `<meta charset="UTF-8">`
7. **Viewport meta:** Cần thiết cho responsive design
8. **Indent nhất quán:** 2 hoặc 4 spaces
9. **Thuộc tính lowercase:** `<div class="name">` thay vì `<DIV CLASS="name">`
10. **Đóng thẻ đúng cách:** Tránh thẻ mở mà không đóng

### 13.2 Performance

```html
<!-- Lazy loading hình ảnh -->
<img src="photo.jpg" alt="..." loading="lazy" />

<!-- Preload tài nguyên quan trọng -->
<link rel="preload" href="font.woff2" as="font" type="font/woff2" crossorigin />
<link rel="preload" href="style.css" as="style" />

<!-- Defer và async cho script -->
<script src="analytics.js" async></script>  <!-- Tải song song, chạy ngay -->
<script src="app.js" defer></script>         <!-- Tải song song, chạy sau DOM -->

<!-- DNS Prefetch -->
<link rel="dns-prefetch" href="//api.example.com" />
<link rel="preconnect" href="https://fonts.googleapis.com" />
```

---

## 31. Tổng kết

HTML là nền tảng của mọi trang web. Những điểm chính cần nhớ:

1. **Cấu trúc:** Mỗi trang HTML có DOCTYPE, html, head, body
2. **Semantic:** Sử dụng thẻ có ngữ nghĩa (header, nav, main, article, section, footer)
3. **Forms:** Nhiều loại input và validation có sẵn
4. **Multimedia:** Hỗ trợ video, audio, canvas native
5. **Accessibility:** ARIA roles và attributes giúp người khuyết tật
6. **SEO:** Meta tags, semantic HTML, và cấu trúc tốt
7. **Performance:** Lazy loading, preload, defer/async
8. **HTML5 APIs:** Local Storage, Geolocation, Drag & Drop, Canvas

HTML kết hợp với CSS (trình bày) và JavaScript (tương tác) tạo thành bộ ba cốt lõi của web development.
