# HTML - Ly Thuyet Tu Co Ban Den Nang Cao

## Muc luc

1. [Gioi thieu ve HTML](#1-gioi-thieu-ve-html)
2. [Cau truc tai lieu HTML](#2-cau-truc-tai-lieu-html)
3. [The van ban (Text Elements)](#3-the-van-ban-text-elements)
4. [Lien ket va Hinh anh](#4-lien-ket-va-hinh-anh)
5. [Danh sach (Lists)](#5-danh-sach-lists)
6. [Bang (Tables)](#6-bang-tables)
7. [Bieu mau (Forms)](#7-bieu-mau-forms)
8. [Semantic HTML](#8-semantic-html)
9. [Multimedia](#9-multimedia)
10. [Meta Tags va SEO](#10-meta-tags-va-seo)
11. [HTML5 APIs](#11-html5-apis)
12. [Accessibility (ARIA)](#12-accessibility-aria)
13. [Best Practices](#13-best-practices)

---

## 1. Gioi thieu ve HTML

### 1.1 HTML la gi?

HTML (HyperText Markup Language) la **ngon ngu danh dau** dung de tao cau truc noi dung trang web. HTML khong phai la ngon ngu lap trinh - no chi dinh nghia **cau truc** va **ngu nghia** cua noi dung.

### 1.2 Lich su phat trien

| Nam | Su kien |
|-----|---------|
| 1991 | Tim Berners-Lee tao ra HTML tai CERN |
| 1995 | HTML 2.0 - phien ban chuan dau tien |
| 1997 | HTML 3.2 - them tables, applets |
| 1999 | HTML 4.01 - CSS support, scripting |
| 2000 | XHTML 1.0 - HTML theo quy tac XML |
| 2014 | HTML5 - multimedia, semantic, APIs |
| 2017 | HTML 5.2 - Payment Request API, dialog element |
| 2021 | HTML Living Standard - WHATWG duy tri lien tuc |

### 1.3 Cong cu can thiet

- **Trinh soan thao:** VS Code, Sublime Text, WebStorm
- **Trinh duyet:** Chrome DevTools (F12), Firefox Developer Tools
- **Extension huu ich:** Live Server, Emmet, Prettier, HTML CSS Support

---

## 2. Cau truc tai lieu HTML

### 2.1 Bo khung co ban

```html
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tieu de trang</title>
</head>
<body>
    <h1>Xin chao!</h1>
    <p>Day la trang web dau tien.</p>
</body>
</html>
```

**Giai thich:**
- `<!DOCTYPE html>`: Khai bao HTML5
- `<html lang="vi">`: Phan tu goc, thuoc tinh `lang` giup trinh duyet va screen reader
- `<head>`: Chua metadata (khong hien thi tren trang)
- `<meta charset="UTF-8">`: Ma hoa ky tu Unicode
- `<meta name="viewport">`: Thiet lap viewport cho mobile
- `<body>`: Noi dung hien thi tren trang

### 2.2 Phan tu (Element) va The (Tag)

```html
<!-- Cu phap co ban -->
<tagname attribute="value">Noi dung</tagname>

<!-- The tu dong (self-closing) -->
<img src="image.jpg" alt="Mo ta" />
<br />
<hr />
<input type="text" />

<!-- The long nhau (nesting) -->
<div>
    <p>Doan van trong <strong>the div</strong></p>
</div>
```

### 2.3 Thuoc tinh (Attributes)

```html
<!-- Thuoc tinh toan cuc (Global Attributes) -->
<div id="unique-id"           <!-- ID duy nhat -->
     class="ten-lop"          <!-- Ten class CSS -->
     style="color: red;"      <!-- CSS inline -->
     title="Tooltip text"     <!-- Tooltip khi hover -->
     data-info="custom"       <!-- Thuoc tinh tu dinh nghia -->
     hidden                   <!-- An phan tu -->
     tabindex="0"             <!-- Thu tu tab -->
     contenteditable="true"   <!-- Cho phep chinh sua -->
     draggable="true">        <!-- Cho phep keo tha -->
</div>
```

### 2.4 Comment

```html
<!-- Day la comment don dong -->

<!--
    Day la comment
    nhieu dong
-->
```

---

## 3. The van ban (Text Elements)

### 3.1 Heading (Tieu de)

```html
<h1>Heading 1 - Tieu de chinh (1 lan duy nhat moi trang)</h1>
<h2>Heading 2 - Tieu de phu</h2>
<h3>Heading 3</h3>
<h4>Heading 4</h4>
<h5>Heading 5</h5>
<h6>Heading 6 - Nho nhat</h6>
```

**Luu y:** Chi dung **mot `<h1>`** moi trang de SEO tot. Thu tu heading phai lien tuc (h1 → h2 → h3, khong nhay tu h1 → h4).

### 3.2 Doan van va Ngat dong

```html
<p>Day la mot doan van ban. Trinh duyet tu dong xuong dong khi het chieu rong.</p>
<p>Day la doan van thu hai.</p>

<!-- Ngat dong trong doan van -->
<p>Dong thu nhat<br />Dong thu hai</p>

<!-- Duong ke ngang -->
<hr />
```

### 3.3 Dinh dang van ban

```html
<strong>In dam - quan trong ve ngu nghia</strong>
<b>In dam - chi ve hinh thuc</b>

<em>In nghieng - nhan manh</em>
<i>In nghieng - chi ve hinh thuc</i>

<mark>Danh dau noi bat</mark>
<del>Gach ngang (da xoa)</del>
<ins>Gach chan (them moi)</ins>
<sub>Chi so duoi: H<sub>2</sub>O</sub>
<sup>Chi so tren: x<sup>2</sup></sup>

<small>Chu nho hon</small>
<code>Inline code</code>
<kbd>Phim tat: <kbd>Ctrl</kbd> + <kbd>C</kbd></kbd>
<abbr title="HyperText Markup Language">HTML</abbr>

<blockquote cite="https://example.com">
    Day la trich dan block-level.
</blockquote>

<q>Day la trich dan inline.</q>

<pre>
    Van ban
    giu nguyen    dinh dang
    va khoang trang
</pre>
```

---

## 4. Lien ket va Hinh anh

### 4.1 Lien ket (Anchor)

```html
<!-- Lien ket co ban -->
<a href="https://google.com">Truy cap Google</a>

<!-- Mo tab moi -->
<a href="https://google.com" target="_blank" rel="noopener noreferrer">
    Mo tab moi
</a>

<!-- Lien ket noi bo (anchor) -->
<a href="#section-2">Di den Section 2</a>
<h2 id="section-2">Section 2</h2>

<!-- Lien ket email va dien thoai -->
<a href="mailto:info@example.com">Gui email</a>
<a href="tel:+84123456789">Goi dien</a>

<!-- Lien ket tai file -->
<a href="report.pdf" download="bao-cao.pdf">Tai PDF</a>
```

### 4.2 Hinh anh

```html
<!-- Hinh anh co ban -->
<img src="photo.jpg" alt="Mo ta hinh anh" width="600" height="400" />

<!-- Hinh anh responsive voi srcset -->
<img src="photo-800.jpg"
     srcset="photo-400.jpg 400w,
             photo-800.jpg 800w,
             photo-1200.jpg 1200w"
     sizes="(max-width: 600px) 400px,
            (max-width: 1000px) 800px,
            1200px"
     alt="Responsive image" />

<!-- Picture element - nhieu nguon -->
<picture>
    <source media="(min-width: 1024px)" srcset="desktop.jpg" />
    <source media="(min-width: 768px)" srcset="tablet.jpg" />
    <img src="mobile.jpg" alt="Responsive picture" />
</picture>

<!-- Figure voi caption -->
<figure>
    <img src="chart.png" alt="Bieu do doanh thu" />
    <figcaption>Hinh 1: Bieu do doanh thu Q1/2024</figcaption>
</figure>
```

---

## 5. Danh sach (Lists)

### 5.1 Cac loai danh sach

```html
<!-- Danh sach khong thu tu -->
<ul>
    <li>Muc 1</li>
    <li>Muc 2</li>
    <li>Muc 3</li>
</ul>

<!-- Danh sach co thu tu -->
<ol type="1" start="1">
    <li>Buoc 1</li>
    <li>Buoc 2</li>
    <li>Buoc 3</li>
</ol>

<!-- Danh sach dinh nghia -->
<dl>
    <dt>HTML</dt>
    <dd>Ngon ngu danh dau sieu van ban</dd>
    <dt>CSS</dt>
    <dd>Ngon ngu dinh kieu</dd>
</dl>

<!-- Danh sach long nhau -->
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

## 6. Bang (Tables)

### 6.1 Cau truc bang

```html
<table>
    <caption>Bang diem sinh vien</caption>
    <thead>
        <tr>
            <th scope="col">STT</th>
            <th scope="col">Ho ten</th>
            <th scope="col">Diem</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>1</td>
            <td>Nguyen Van A</td>
            <td>8.5</td>
        </tr>
        <tr>
            <td>2</td>
            <td>Tran Thi B</td>
            <td>9.0</td>
        </tr>
    </tbody>
    <tfoot>
        <tr>
            <td colspan="2">Trung binh</td>
            <td>8.75</td>
        </tr>
    </tfoot>
</table>
```

### 6.2 Gop o (Merge Cells)

```html
<table>
    <tr>
        <th rowspan="2">Ten</th>
        <th colspan="2">Diem</th>
    </tr>
    <tr>
        <th>Giua ky</th>
        <th>Cuoi ky</th>
    </tr>
    <tr>
        <td>An</td>
        <td>8.0</td>
        <td>9.0</td>
    </tr>
</table>
```

---

## 7. Bieu mau (Forms)

### 7.1 Cau truc Form

```html
<form action="/submit" method="POST" enctype="multipart/form-data">
    <!-- Text Input -->
    <label for="username">Ten dang nhap:</label>
    <input type="text" id="username" name="username"
           placeholder="Nhap ten..." required minlength="3" maxlength="20" />

    <!-- Email -->
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required />

    <!-- Password -->
    <label for="password">Mat khau:</label>
    <input type="password" id="password" name="password"
           required minlength="8" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" />

    <!-- Number -->
    <label for="age">Tuoi:</label>
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
    <label for="bio">Gioi thieu:</label>
    <textarea id="bio" name="bio" rows="4" cols="50" maxlength="500"></textarea>

    <!-- Select -->
    <label for="city">Thanh pho:</label>
    <select id="city" name="city">
        <optgroup label="Mien Bac">
            <option value="hn">Ha Noi</option>
            <option value="hp">Hai Phong</option>
        </optgroup>
        <optgroup label="Mien Nam">
            <option value="hcm" selected>TP. Ho Chi Minh</option>
            <option value="dn">Da Nang</option>
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
        <legend>Gioi tinh:</legend>
        <input type="radio" id="male" name="gender" value="male" />
        <label for="male">Nam</label>
        <input type="radio" id="female" name="gender" value="female" />
        <label for="female">Nu</label>
    </fieldset>

    <!-- Checkbox -->
    <input type="checkbox" id="agree" name="agree" required />
    <label for="agree">Dong y dieu khoan</label>

    <!-- Hidden -->
    <input type="hidden" name="csrf_token" value="abc123" />

    <!-- Submit -->
    <button type="submit">Gui</button>
    <button type="reset">Xoa form</button>
</form>
```

### 7.2 Validation Attributes

```html
<input type="text" required />                    <!-- Bat buoc -->
<input type="text" minlength="3" maxlength="50" /> <!-- Do dai -->
<input type="number" min="0" max="100" />         <!-- Khoang gia tri -->
<input type="text" pattern="[A-Za-z]{3,}" />      <!-- Regex pattern -->
<input type="email" />                             <!-- Tu dong validate email -->
<input type="url" />                               <!-- Tu dong validate URL -->
```

---

## 8. Semantic HTML

### 8.1 Tai sao can Semantic HTML?

- **SEO tot hon:** Search engine hieu noi dung
- **Accessibility:** Screen reader doc dung
- **Code de doc:** Developer hieu cau truc
- **Bao tri de hon:** Phan biet ro cac thanh phan

### 8.2 Cac the Semantic

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
                <li><a href="/">Trang chu</a></li>
                <li><a href="/about">Gioi thieu</a></li>
                <li><a href="/contact">Lien he</a></li>
            </ul>
        </nav>
    </header>

    <main>
        <article>
            <header>
                <h1>Tieu de bai viet</h1>
                <time datetime="2024-01-15">15/01/2024</time>
            </header>

            <section>
                <h2>Phan 1</h2>
                <p>Noi dung phan 1...</p>
            </section>

            <section>
                <h2>Phan 2</h2>
                <p>Noi dung phan 2...</p>
                <figure>
                    <img src="chart.png" alt="Bieu do" />
                    <figcaption>Hinh minh hoa</figcaption>
                </figure>
            </section>

            <footer>
                <p>Tac gia: Nguyen Van A</p>
            </footer>
        </article>

        <aside>
            <h3>Bai viet lien quan</h3>
            <ul>
                <li><a href="#">Bai 1</a></li>
                <li><a href="#">Bai 2</a></li>
            </ul>
        </aside>
    </main>

    <footer>
        <p>&copy; 2024 My Website</p>
        <address>
            Lien he: <a href="mailto:info@example.com">info@example.com</a>
        </address>
    </footer>
</body>
</html>
```

### 8.3 So sanh Semantic vs Non-Semantic

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

<!-- DUNG - Semantic -->
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
    <track src="subtitles_vi.vtt" kind="subtitles" srclang="vi" label="Tieng Viet" />
    Trinh duyet khong ho tro video.
</video>
```

### 9.2 Audio

```html
<audio controls>
    <source src="audio.mp3" type="audio/mpeg" />
    <source src="audio.ogg" type="audio/ogg" />
    Trinh duyet khong ho tro audio.
</audio>
```

### 9.3 Nhung noi dung (Embed)

```html
<!-- iframe -->
<iframe src="https://www.youtube.com/embed/VIDEO_ID"
        width="560" height="315"
        title="YouTube video"
        allowfullscreen
        loading="lazy">
</iframe>

<!-- embed va object -->
<embed src="file.pdf" type="application/pdf" width="600" height="400" />
```

---

## 10. Meta Tags va SEO

### 10.1 Essential Meta Tags

```html
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="Mo ta trang web (150-160 ky tu)" />
    <meta name="keywords" content="html, css, web development" />
    <meta name="author" content="Ten tac gia" />
    <meta name="robots" content="index, follow" />

    <!-- Open Graph (Facebook, LinkedIn) -->
    <meta property="og:title" content="Tieu de" />
    <meta property="og:description" content="Mo ta" />
    <meta property="og:image" content="https://example.com/image.jpg" />
    <meta property="og:url" content="https://example.com" />
    <meta property="og:type" content="website" />

    <!-- Twitter Card -->
    <meta name="twitter:card" content="summary_large_image" />
    <meta name="twitter:title" content="Tieu de" />
    <meta name="twitter:description" content="Mo ta" />
    <meta name="twitter:image" content="https://example.com/image.jpg" />

    <!-- Favicon -->
    <link rel="icon" type="image/x-icon" href="/favicon.ico" />
    <link rel="apple-touch-icon" href="/apple-touch-icon.png" />

    <!-- Canonical URL -->
    <link rel="canonical" href="https://example.com/page" />

    <title>Tieu de trang | Ten website</title>
</head>
```

---

## 11. HTML5 APIs

### 11.1 Local Storage va Session Storage

```html
<script>
// Local Storage - luu tru lau dai
localStorage.setItem('theme', 'dark');
const theme = localStorage.getItem('theme');
localStorage.removeItem('theme');
localStorage.clear();

// Session Storage - mat khi dong tab
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
        console.error('Loi:', error.message);
    }
);
</script>
```

### 11.3 Drag and Drop

```html
<div draggable="true"
     ondragstart="event.dataTransfer.setData('text', event.target.id)"
     id="drag-item">
    Keo toi day
</div>

<div ondrop="drop(event)" ondragover="event.preventDefault()" id="drop-zone">
    Tha vao day
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

### 12.1 ARIA Roles va Attributes

```html
<!-- ARIA Roles -->
<div role="navigation">...</div>
<div role="main">...</div>
<div role="alert">Co loi xay ra!</div>
<div role="dialog" aria-modal="true">...</div>

<!-- ARIA Labels -->
<button aria-label="Dong">X</button>
<input aria-labelledby="label-id" />
<div aria-describedby="help-text">...</div>

<!-- ARIA States -->
<button aria-expanded="false" aria-controls="menu-id">Menu</button>
<div id="menu-id" aria-hidden="true">...</div>
<input aria-invalid="true" aria-errormessage="error-id" />

<!-- Live Regions -->
<div aria-live="polite" aria-atomic="true">
    Noi dung thay doi se duoc doc boi screen reader
</div>
```

### 12.2 Best Practices Accessibility

```html
<!-- Dung: Label cho input -->
<label for="email">Email:</label>
<input type="email" id="email" name="email" />

<!-- Dung: Alt text co y nghia -->
<img src="logo.png" alt="Logo cong ty ABC" />
<img src="decoration.png" alt="" />  <!-- Hinh trang tri: alt rong -->

<!-- Dung: Skip navigation -->
<a href="#main-content" class="skip-link">Bo qua menu</a>
<nav>...</nav>
<main id="main-content">...</main>

<!-- Dung: Focus management -->
<button tabindex="0">Co the tab den</button>
<div tabindex="-1">Chi focus bang JS</div>
```

---

## 13. Best Practices

### 13.1 Quy tac chung

1. **Luon khai bao DOCTYPE:** `<!DOCTYPE html>`
2. **Dung Semantic HTML:** Su dung the co ngu nghia thay vi `<div>` cho moi thu
3. **Validate HTML:** Dung W3C Validator (validator.w3.org)
4. **Alt text cho hinh anh:** Moi `<img>` phai co thuoc tinh `alt`
5. **Label cho form input:** Moi input phai co `<label>` tuong ung
6. **UTF-8 encoding:** Luon khai bao `<meta charset="UTF-8">`
7. **Viewport meta:** Can thiet cho responsive design
8. **Indent nhat quan:** 2 hoac 4 spaces
9. **Thuoc tinh lowercase:** `<div class="name">` thay vi `<DIV CLASS="name">`
10. **Dong the dung cach:** Tranh the mo ma khong dong

### 13.2 Performance

```html
<!-- Lazy loading hinh anh -->
<img src="photo.jpg" alt="..." loading="lazy" />

<!-- Preload tai nguyen quan trong -->
<link rel="preload" href="font.woff2" as="font" type="font/woff2" crossorigin />
<link rel="preload" href="style.css" as="style" />

<!-- Defer va async cho script -->
<script src="analytics.js" async></script>  <!-- Tai song song, chay ngay -->
<script src="app.js" defer></script>         <!-- Tai song song, chay sau DOM -->

<!-- DNS Prefetch -->
<link rel="dns-prefetch" href="//api.example.com" />
<link rel="preconnect" href="https://fonts.googleapis.com" />
```

---

## 31. Tong ket

HTML la nen tang cua moi trang web. Nhung diem chinh can nho:

1. **Cau truc:** Moi trang HTML co DOCTYPE, html, head, body
2. **Semantic:** Su dung the co ngu nghia (header, nav, main, article, section, footer)
3. **Forms:** Nhieu loai input va validation co san
4. **Multimedia:** Ho tro video, audio, canvas native
5. **Accessibility:** ARIA roles va attributes giup nguoi khuyet tat
6. **SEO:** Meta tags, semantic HTML, va cau truc tot
7. **Performance:** Lazy loading, preload, defer/async
8. **HTML5 APIs:** Local Storage, Geolocation, Drag & Drop, Canvas

HTML ket hop voi CSS (trinh bay) va JavaScript (tuong tac) tao thanh bo ba cot loi cua web development.
