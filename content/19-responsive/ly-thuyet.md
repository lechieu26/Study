# Responsive Web Design - Lý Thuyết Từ Cơ Bản Đến Nâng Cao

## Mục lục

1. [Giới thiệu về Responsive Web Design](#1-giới-thiệu-về-responsive-web-design)
2. [Viewport và Meta Tag](#2-viewport-và-meta-tag)
3. [Media Queries](#3-media-queries)
4. [Fluid Layouts](#4-fluid-layouts)
5. [Responsive Images](#5-responsive-images)
6. [Mobile-First Approach](#6-mobile-first-approach)
7. [Responsive Typography](#7-responsive-typography)
8. [Flexbox Responsive Patterns](#8-flexbox-responsive-patterns)
9. [CSS Grid Responsive Patterns](#9-css-grid-responsive-patterns)
10. [Container Queries](#10-container-queries)
11. [Responsive Navigation Patterns](#11-responsive-navigation-patterns)
12. [Testing và Tools](#12-testing-và-tools)
13. [Best Practices](#13-best-practices)

---

## 1. Giới thiệu về Responsive Web Design

### 1.1 Responsive Web Design là gì?

Responsive Web Design (RWD) là phương pháp thiết kế web giúp trang web tự động điều chỉnh layout, hình ảnh và nội dung cho phù hợp với mọi kích thước màn hình - từ mobile nhỏ đến desktop lớn.

### 1.2 Tại sao cần Responsive?

- **60%+ traffic** từ mobile (2024)
- Google **Mobile-First Indexing** - ưu tiên trang mobile cho SEO
- Trải nghiệm người dùng tốt hơn trên mọi thiết bị
- Chỉ cần **1 codebase** cho mọi thiết bị

### 1.3 Ba trụ cột của RWD

1. **Fluid Grids** - Layout linh hoạt dùng đơn vị tương đối (%, fr, vw)
2. **Flexible Images** - Hình ảnh tự động co giãn
3. **Media Queries** - CSS điều kiện theo kích thước màn hình

---

## 2. Viewport và Meta Tag

### 2.1 Viewport Meta Tag

```html
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
```

**Các giá trị:**
- `width=device-width`: Chiều rộng = chiều rộng thiết bị
- `initial-scale=1.0`: Tỷ lệ zoom ban đầu
- `maximum-scale=1.0`: Giới hạn zoom tối đa (tránh dùng - xấu cho accessibility)
- `user-scalable=no`: Cấm zoom (KHÔNG NÊN dùng)

### 2.2 Viewport Units

```css
.hero {
    width: 100vw;     /* 100% chiều rộng viewport */
    height: 100vh;    /* 100% chiều cao viewport */
    min-height: 100svh; /* Small viewport height (tính cả address bar mobile) */
}

.sidebar {
    width: 25vw;      /* 25% chiều rộng viewport */
    min-width: 250px; /* Tối thiểu 250px */
}
```

---

## 3. Media Queries

### 3.1 Cú pháp cơ bản

```css
/* Màn hình nhỏ hơn 768px */
@media (max-width: 768px) {
    .container { padding: 10px; }
}

/* Màn hình lớn hơn 1024px */
@media (min-width: 1024px) {
    .container { max-width: 1200px; }
}

/* Khoảng giữa */
@media (min-width: 768px) and (max-width: 1024px) {
    .sidebar { display: none; }
}
```

### 3.2 Breakpoints phổ biến

```css
/* Mobile-First Breakpoints */
/* Mobile: 0 - 767px (mặc định, không cần media query) */

/* Tablet */
@media (min-width: 768px) {
    .grid { grid-template-columns: repeat(2, 1fr); }
}

/* Desktop */
@media (min-width: 1024px) {
    .grid { grid-template-columns: repeat(3, 1fr); }
}

/* Large Desktop */
@media (min-width: 1440px) {
    .container { max-width: 1400px; }
}
```

### 3.3 Media Features khác

```css
/* Orientation */
@media (orientation: landscape) {
    .hero { height: 80vh; }
}

@media (orientation: portrait) {
    .hero { height: 50vh; }
}

/* Hover capability */
@media (hover: hover) {
    .button:hover { background: blue; }
}

/* Prefers color scheme */
@media (prefers-color-scheme: dark) {
    :root {
        --bg: #1a1a2e;
        --text: #e0e0e0;
    }
}

/* Prefers reduced motion */
@media (prefers-reduced-motion: reduce) {
    * { animation: none !important; transition: none !important; }
}

/* Print */
@media print {
    nav, footer, .no-print { display: none; }
    body { font-size: 12pt; color: black; }
}

/* Resolution (cho retina displays) */
@media (min-resolution: 2dppx) {
    .logo { background-image: url('logo@2x.png'); }
}
```

---

## 4. Fluid Layouts

### 4.1 Container fluid

```css
.container {
    width: 100%;
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 16px;
}

/* Responsive padding */
.section {
    padding: clamp(16px, 5vw, 64px);
}
```

### 4.2 Fluid Grid với Percentages

```css
/* 12-column grid đơn giản */
.row {
    display: flex;
    flex-wrap: wrap;
    margin: 0 -8px;
}

.col { padding: 0 8px; }
.col-6 { width: 50%; }
.col-4 { width: 33.333%; }
.col-3 { width: 25%; }

@media (max-width: 768px) {
    .col-6, .col-4, .col-3 { width: 100%; }
}
```

### 4.3 Aspect Ratio

```css
/* Modern: aspect-ratio property */
.video-container {
    width: 100%;
    aspect-ratio: 16 / 9;
}

.square-card {
    aspect-ratio: 1 / 1;
}

/* Fallback cho trình duyệt cũ */
.video-wrapper {
    position: relative;
    padding-bottom: 56.25%; /* 9/16 = 56.25% */
    height: 0;
}

.video-wrapper iframe {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
}
```

---

## 5. Responsive Images

### 5.1 Fluid Images (cơ bản)

```css
img {
    max-width: 100%;
    height: auto;
    display: block;
}
```

### 5.2 Srcset và Sizes

```html
<img src="photo-800.jpg"
     srcset="photo-400.jpg 400w,
             photo-800.jpg 800w,
             photo-1200.jpg 1200w"
     sizes="(max-width: 600px) 100vw,
            (max-width: 1000px) 50vw,
            33vw"
     alt="Responsive photo" />
```

**Giải thích:**
- `srcset`: Danh sách ảnh và kích thước thực (400w = 400px rộng)
- `sizes`: Báo trình duyệt ảnh sẽ chiếm bao nhiêu viewport
- Trình duyệt tự chọn ảnh phù hợp nhất (dựa trên DPR và viewport)

### 5.3 Picture Element

```html
<picture>
    <!-- WebP cho trình duyệt hỗ trợ -->
    <source type="image/webp"
            srcset="photo.webp" />

    <!-- Hình khác nhau cho breakpoints khác nhau -->
    <source media="(min-width: 1024px)"
            srcset="photo-desktop.jpg" />
    <source media="(min-width: 768px)"
            srcset="photo-tablet.jpg" />

    <!-- Fallback -->
    <img src="photo-mobile.jpg" alt="Responsive" loading="lazy" />
</picture>
```

### 5.4 Background Images Responsive

```css
.hero {
    background-image: url('hero-mobile.jpg');
    background-size: cover;
    background-position: center;
}

@media (min-width: 768px) {
    .hero { background-image: url('hero-tablet.jpg'); }
}

@media (min-width: 1024px) {
    .hero { background-image: url('hero-desktop.jpg'); }
}

/* Hoặc dùng image-set */
.hero {
    background-image: image-set(
        url('hero.webp') type('image/webp'),
        url('hero.jpg') type('image/jpeg')
    );
}
```

---

## 6. Mobile-First Approach

### 6.1 Khái niệm

Viết CSS cho mobile trước (màn hình nhỏ nhất), sau đó dùng `min-width` media queries để thêm style cho màn hình lớn hơn.

### 6.2 Ví dụ Mobile-First

```css
/* === Mobile (mặc định) === */
.grid {
    display: grid;
    grid-template-columns: 1fr;
    gap: 16px;
    padding: 16px;
}

.card {
    padding: 16px;
    border-radius: 8px;
}

.sidebar { display: none; }

/* === Tablet (>= 768px) === */
@media (min-width: 768px) {
    .grid {
        grid-template-columns: repeat(2, 1fr);
        gap: 24px;
        padding: 24px;
    }

    .sidebar {
        display: block;
        width: 250px;
    }
}

/* === Desktop (>= 1024px) === */
@media (min-width: 1024px) {
    .grid {
        grid-template-columns: repeat(3, 1fr);
        gap: 32px;
    }

    .sidebar { width: 300px; }
}

/* === Large (>= 1440px) === */
@media (min-width: 1440px) {
    .container { max-width: 1400px; margin: 0 auto; }
    .grid { grid-template-columns: repeat(4, 1fr); }
}
```

### 6.3 Desktop-First vs Mobile-First

```css
/* Desktop-First (KHÔNG KHUYÊN DÙNG) - dùng max-width */
.grid { grid-template-columns: repeat(4, 1fr); }

@media (max-width: 1024px) {
    .grid { grid-template-columns: repeat(2, 1fr); }
}
@media (max-width: 768px) {
    .grid { grid-template-columns: 1fr; }
}

/* Mobile-First (KHUYÊN DÙNG) - dùng min-width */
.grid { grid-template-columns: 1fr; }

@media (min-width: 768px) {
    .grid { grid-template-columns: repeat(2, 1fr); }
}
@media (min-width: 1024px) {
    .grid { grid-template-columns: repeat(4, 1fr); }
}
```

---

## 7. Responsive Typography

### 7.1 Fluid Typography với clamp()

```css
html {
    font-size: 16px;
}

h1 {
    font-size: clamp(1.75rem, 4vw + 1rem, 3.5rem);
    line-height: 1.2;
}

h2 {
    font-size: clamp(1.5rem, 3vw + 0.5rem, 2.5rem);
}

p {
    font-size: clamp(0.9rem, 1vw + 0.5rem, 1.125rem);
    line-height: 1.6;
}
```

### 7.2 Modular Scale

```css
:root {
    --step-0: clamp(1rem, 0.5vw + 0.875rem, 1.125rem);
    --step-1: clamp(1.2rem, 0.8vw + 1rem, 1.5rem);
    --step-2: clamp(1.44rem, 1.2vw + 1.1rem, 2rem);
    --step-3: clamp(1.728rem, 1.8vw + 1.2rem, 2.667rem);
    --step-4: clamp(2.074rem, 2.5vw + 1.3rem, 3.556rem);
}

body { font-size: var(--step-0); }
h3 { font-size: var(--step-1); }
h2 { font-size: var(--step-2); }
h1 { font-size: var(--step-3); }
.display { font-size: var(--step-4); }
```

---

## 8. Flexbox Responsive Patterns

### 8.1 Auto-wrapping Cards

```css
.card-grid {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
}

.card {
    flex: 1 1 300px;     /* Grow, shrink, min 300px */
    max-width: 100%;
}
```

### 8.2 Sidebar Layout

```css
.layout {
    display: flex;
    flex-wrap: wrap;
    gap: 24px;
}

.sidebar {
    flex: 1 1 250px;     /* Min 250px */
    max-width: 300px;
}

.main-content {
    flex: 1 1 600px;     /* Min 600px, chiếm nhiều hơn */
}
```

### 8.3 Footer Stay Bottom

```css
body {
    display: flex;
    flex-direction: column;
    min-height: 100vh;
}

main { flex: 1; }  /* Đẩy footer xuống dưới */
```

---

## 9. CSS Grid Responsive Patterns

### 9.1 Auto-fill / Auto-fit

```css
/* auto-fill: tạo nhiều cột nhất có thể */
.grid-fill {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 20px;
}

/* auto-fit: giống auto-fill nhưng giãn các cột hiện có */
.grid-fit {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    gap: 20px;
}
```

### 9.2 Holy Grail Layout

```css
.page {
    display: grid;
    grid-template-areas:
        "header"
        "main"
        "footer";
    grid-template-rows: auto 1fr auto;
    min-height: 100vh;
}

@media (min-width: 768px) {
    .page {
        grid-template-areas:
            "header header header"
            "nav    main   aside"
            "footer footer footer";
        grid-template-columns: 200px 1fr 200px;
    }
}

.header { grid-area: header; }
.nav    { grid-area: nav; }
.main   { grid-area: main; }
.aside  { grid-area: aside; }
.footer { grid-area: footer; }
```

---

## 10. Container Queries

### 10.1 Container Queries (CSS mới)

```css
/* Định nghĩa container */
.card-wrapper {
    container-type: inline-size;
    container-name: card;
}

/* Style dựa trên kích thước container (không phải viewport) */
@container card (min-width: 400px) {
    .card {
        display: flex;
        flex-direction: row;
    }
    .card__image { width: 40%; }
    .card__body { width: 60%; }
}

@container card (max-width: 399px) {
    .card {
        display: block;
    }
    .card__image { width: 100%; }
}
```

### 10.2 Container Units

```css
.card-wrapper {
    container-type: inline-size;
}

.card__title {
    font-size: clamp(1rem, 5cqi, 2rem);  /* cqi = container query inline */
}
```

---

## 11. Responsive Navigation Patterns

### 11.1 Hamburger Menu (Mobile)

```css
.nav-toggle {
    display: none;
    background: none;
    border: none;
    font-size: 1.5rem;
    cursor: pointer;
}

.nav-menu {
    display: flex;
    gap: 16px;
    list-style: none;
}

@media (max-width: 768px) {
    .nav-toggle { display: block; }

    .nav-menu {
        display: none;
        flex-direction: column;
        position: absolute;
        top: 60px;
        left: 0;
        width: 100%;
        background: white;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .nav-menu.active { display: flex; }

    .nav-menu a {
        padding: 16px 24px;
        border-bottom: 1px solid #eee;
    }
}
```

### 11.2 Bottom Navigation (Mobile App Style)

```css
.bottom-nav {
    display: none;
}

@media (max-width: 768px) {
    .bottom-nav {
        display: flex;
        justify-content: space-around;
        align-items: center;
        position: fixed;
        bottom: 0;
        left: 0;
        width: 100%;
        height: 60px;
        background: white;
        box-shadow: 0 -2px 8px rgba(0, 0, 0, 0.1);
        z-index: 1000;
    }

    /* Thêm padding-bottom cho body để tránh bị che */
    body { padding-bottom: 60px; }
}
```

---

## 12. Testing và Tools

### 12.1 Chrome DevTools

1. **Device Toolbar** (Ctrl + Shift + M): Giả lập các thiết bị
2. **Responsive Mode**: Kéo thả để thay đổi kích thước
3. **Network throttling**: Giả lập mạng chậm (3G, 4G)
4. **Lighthouse**: Kiểm tra performance, accessibility, SEO

### 12.2 Testing Checklist

- [ ] iPhone SE (375px) - màn hình nhỏ nhất phổ biến
- [ ] iPhone 14 (390px) - mobile phổ biến
- [ ] iPad (768px) - tablet portrait
- [ ] iPad Landscape (1024px) - tablet landscape
- [ ] Laptop (1366px) - màn hình phổ biến nhất
- [ ] Desktop (1920px) - Full HD
- [ ] 4K (2560px) - màn hình lớn

---

## 13. Best Practices

### 13.1 Do's

1. **Mobile-First:** Viết CSS mobile trước, dùng `min-width` media queries
2. **Relative Units:** Dùng rem, em, %, vw thay vì px cố định
3. **Fluid Typography:** Dùng `clamp()` cho responsive font-size
4. **Flexible Images:** Luôn có `max-width: 100%` cho images
5. **Test trên thiết bị thật:** Emulator không chính xác 100%
6. **Content-First:** Thiết kế dựa trên nội dung, không phải thiết bị cụ thể
7. **Touch Targets:** Tối thiểu 44x44px cho nút bấm trên mobile
8. **Performance:** Lazy load images, optimize assets

### 13.2 Don'ts

1. **KHÔNG** dùng `max-scale=1` hoặc `user-scalable=no`
2. **KHÔNG** dùng width cố định (width: 960px)
3. **KHÔNG** ẩn nội dung quan trọng trên mobile
4. **KHÔNG** dùng quá nhiều breakpoints
5. **KHÔNG** chỉ test trên Chrome - test cả Safari, Firefox
6. **KHÔNG** quên `<meta viewport>` tag

---

## Tổng kết

Responsive Web Design là kỹ năng bắt buộc cho frontend developer:

1. **Viewport Meta Tag** là bước đầu tiên cho mỗi trang responsive
2. **Mobile-First** là phương pháp tiêu chuẩn
3. **Media Queries** điều kiện hóa CSS theo kích thước màn hình
4. **Flexbox + Grid** là công cụ layout responsive hiện đại
5. **clamp()** giúp typography responsive không cần media queries
6. **Container Queries** là tương lai của component-level responsive
7. **Testing** trên nhiều thiết bị là bắt buộc
