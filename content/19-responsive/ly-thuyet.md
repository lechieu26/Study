# Responsive Web Design - Ly Thuyet Tu Co Ban Den Nang Cao

## Muc luc

1. [Gioi thieu ve Responsive Web Design](#1-gioi-thieu-ve-responsive-web-design)
2. [Viewport va Meta Tag](#2-viewport-va-meta-tag)
3. [Media Queries](#3-media-queries)
4. [Fluid Layouts](#4-fluid-layouts)
5. [Responsive Images](#5-responsive-images)
6. [Mobile-First Approach](#6-mobile-first-approach)
7. [Responsive Typography](#7-responsive-typography)
8. [Flexbox Responsive Patterns](#8-flexbox-responsive-patterns)
9. [CSS Grid Responsive Patterns](#9-css-grid-responsive-patterns)
10. [Container Queries](#10-container-queries)
11. [Responsive Navigation Patterns](#11-responsive-navigation-patterns)
12. [Testing va Tools](#12-testing-va-tools)
13. [Best Practices](#13-best-practices)

---

## 1. Gioi thieu ve Responsive Web Design

### 1.1 Responsive Web Design la gi?

Responsive Web Design (RWD) la phuong phap thiet ke web giup trang web tu dong dieu chinh layout, hinh anh va noi dung cho phu hop voi moi kich thuoc man hinh - tu mobile nho den desktop lon.

### 1.2 Tai sao can Responsive?

- **60%+ traffic** tu mobile (2024)
- Google **Mobile-First Indexing** - uu tien trang mobile cho SEO
- Trai nghiem nguoi dung tot hon tren moi thiet bi
- Chi can **1 codebase** cho moi thiet bi

### 1.3 Ba tru cot cua RWD

1. **Fluid Grids** - Layout linh hoat dung don vi tuong doi (%, fr, vw)
2. **Flexible Images** - Hinh anh tu dong co gian
3. **Media Queries** - CSS dieu kien theo kich thuoc man hinh

---

## 2. Viewport va Meta Tag

### 2.1 Viewport Meta Tag

```html
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
```

**Cac gia tri:**
- `width=device-width`: Chieu rong = chieu rong thiet bi
- `initial-scale=1.0`: Ty le zoom ban dau
- `maximum-scale=1.0`: Gioi han zoom toi da (tranh dung - xau cho accessibility)
- `user-scalable=no`: Cam zoom (KHONG NEN dung)

### 2.2 Viewport Units

```css
.hero {
    width: 100vw;     /* 100% chieu rong viewport */
    height: 100vh;    /* 100% chieu cao viewport */
    min-height: 100svh; /* Small viewport height (tinh ca address bar mobile) */
}

.sidebar {
    width: 25vw;      /* 25% chieu rong viewport */
    min-width: 250px; /* Toi thieu 250px */
}
```

---

## 3. Media Queries

### 3.1 Cu phap co ban

```css
/* Man hinh nho hon 768px */
@media (max-width: 768px) {
    .container { padding: 10px; }
}

/* Man hinh lon hon 1024px */
@media (min-width: 1024px) {
    .container { max-width: 1200px; }
}

/* Khoang giua */
@media (min-width: 768px) and (max-width: 1024px) {
    .sidebar { display: none; }
}
```

### 3.2 Breakpoints pho bien

```css
/* Mobile-First Breakpoints */
/* Mobile: 0 - 767px (mac dinh, khong can media query) */

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

### 3.3 Media Features khac

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

### 4.2 Fluid Grid voi Percentages

```css
/* 12-column grid don gian */
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

/* Fallback cho trinh duyet cu */
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

### 5.1 Fluid Images (co ban)

```css
img {
    max-width: 100%;
    height: auto;
    display: block;
}
```

### 5.2 Srcset va Sizes

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
- `srcset`: Danh sach anh va kich thuoc thuc (400w = 400px rong)
- `sizes`: Bao trinh duyet anh se chiem bao nhieu viewport
- Trinh duyet tu chon anh phu hop nhat (dua tren DPR va viewport)

### 5.3 Picture Element

```html
<picture>
    <!-- WebP cho trinh duyet ho tro -->
    <source type="image/webp"
            srcset="photo.webp" />

    <!-- Hinh khac nhau cho breakpoints khac nhau -->
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

/* Hoac dung image-set */
.hero {
    background-image: image-set(
        url('hero.webp') type('image/webp'),
        url('hero.jpg') type('image/jpeg')
    );
}
```

---

## 6. Mobile-First Approach

### 6.1 Khai niem

Viet CSS cho mobile truoc (man hinh nho nhat), sau do dung `min-width` media queries de them style cho man hinh lon hon.

### 6.2 Vi du Mobile-First

```css
/* === Mobile (mac dinh) === */
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
/* Desktop-First (KHONG KHUYEN DUNG) - dung max-width */
.grid { grid-template-columns: repeat(4, 1fr); }

@media (max-width: 1024px) {
    .grid { grid-template-columns: repeat(2, 1fr); }
}
@media (max-width: 768px) {
    .grid { grid-template-columns: 1fr; }
}

/* Mobile-First (KHUYEN DUNG) - dung min-width */
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

### 7.1 Fluid Typography voi clamp()

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
    flex: 1 1 600px;     /* Min 600px, chiem nhieu hon */
}
```

### 8.3 Footer Stay Bottom

```css
body {
    display: flex;
    flex-direction: column;
    min-height: 100vh;
}

main { flex: 1; }  /* Day footer xuong duoi */
```

---

## 9. CSS Grid Responsive Patterns

### 9.1 Auto-fill / Auto-fit

```css
/* auto-fill: tao nhieu cot nhat co the */
.grid-fill {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 20px;
}

/* auto-fit: giong auto-fill nhung gian cac cot hien co */
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

### 10.1 Container Queries (CSS moi)

```css
/* Dinh nghia container */
.card-wrapper {
    container-type: inline-size;
    container-name: card;
}

/* Style dua tren kich thuoc container (khong phai viewport) */
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

    /* Them padding-bottom cho body de tranh bi che */
    body { padding-bottom: 60px; }
}
```

---

## 12. Testing va Tools

### 12.1 Chrome DevTools

1. **Device Toolbar** (Ctrl + Shift + M): Gia lap cac thiet bi
2. **Responsive Mode**: Keo tha de thay doi kich thuoc
3. **Network throttling**: Gia lap mang cham (3G, 4G)
4. **Lighthouse**: Kiem tra performance, accessibility, SEO

### 12.2 Testing Checklist

- [ ] iPhone SE (375px) - man hinh nho nhat pho bien
- [ ] iPhone 14 (390px) - mobile pho bien
- [ ] iPad (768px) - tablet portrait
- [ ] iPad Landscape (1024px) - tablet landscape
- [ ] Laptop (1366px) - man hinh pho bien nhat
- [ ] Desktop (1920px) - Full HD
- [ ] 4K (2560px) - man hinh lon

---

## 13. Best Practices

### 13.1 Do's

1. **Mobile-First:** Viet CSS mobile truoc, dung `min-width` media queries
2. **Relative Units:** Dung rem, em, %, vw thay vi px co dinh
3. **Fluid Typography:** Dung `clamp()` cho responsive font-size
4. **Flexible Images:** Luon co `max-width: 100%` cho images
5. **Test tren thiet bi that:** Emulator khong chinh xac 100%
6. **Content-First:** Thiet ke dua tren noi dung, khong phai thiet bi cu the
7. **Touch Targets:** Toi thieu 44x44px cho nut bam tren mobile
8. **Performance:** Lazy load images, optimize assets

### 13.2 Don'ts

1. **KHONG** dung `max-scale=1` hoac `user-scalable=no`
2. **KHONG** dung width co dinh (width: 960px)
3. **KHONG** an noi dung quan trong tren mobile
4. **KHONG** dung qua nhieu breakpoints
5. **KHONG** chi test tren Chrome - test ca Safari, Firefox
6. **KHONG** quen `<meta viewport>` tag

---

## Tong ket

Responsive Web Design la ky nang bat buoc cho frontend developer:

1. **Viewport Meta Tag** la buoc dau tien cho moi trang responsive
2. **Mobile-First** la phuong phap tieu chuan
3. **Media Queries** dieu kien hoa CSS theo kich thuoc man hinh
4. **Flexbox + Grid** la cong cu layout responsive hien dai
5. **clamp()** giup typography responsive khong can media queries
6. **Container Queries** la tuong lai cua component-level responsive
7. **Testing** tren nhieu thiet bi la bat buoc
