# CSS - Lý Thuyết Từ Cơ Bản Đến Nâng Cao

## Mục lục

1. [Giới thiệu về CSS](#1-giới thiệu-về-css)
2. [Cách thêm CSS vào HTML](#2-cách-thêm-css-vào-html)
3. [Selectors](#3-selectors)
4. [Specificity và Cascade](#4-specificity-và-cascade)
5. [Box Model](#5-box-model)
6. [Typography](#6-typography)
7. [Colors và Backgrounds](#7-colors-và-backgrounds)
8. [Display và Visibility](#8-display-và-visibility)
9. [Positioning](#9-positioning)
10. [Flexbox](#10-flexbox)
11. [CSS Grid](#11-css-grid)
12. [Transitions và Animations](#12-transitions-và-animations)
13. [Pseudo-classes và Pseudo-elements](#13-pseudo-classes-và-pseudo-elements)
14. [CSS Variables (Custom Properties)](#14-css-variables-custom-properties)
15. [CSS Functions](#15-css-functions)
16. [Best Practices](#16-best-practices)

---

## 1. Giới thiệu về CSS

### 1.1 CSS là gì?

CSS (Cascading Style Sheets) là ngôn ngữ định kiểu dùng để mô tả cách trình bày của tài liệu HTML. CSS tách biệt nội dung (HTML) và trình bày (CSS), giúp trang web đẹp hơn và dễ bảo trì hơn.

### 1.2 Lịch sử phát triển

| Năm | Sự kiện |
|-----|---------|
| 1996 | CSS1 - font, color, text, margin, border |
| 1998 | CSS2 - positioning, z-index, media types |
| 2011 | CSS3 - modules: Flexbox, Grid, Animations, Variables |
| 2017 | CSS Grid Layout được hỗ trợ rộng rãi |
| 2022 | Container Queries, :has() pseudo-class |
| 2024 | Nesting, @scope, Scroll-driven Animations |

---

## 2. Cách thêm CSS vào HTML

### 2.1 Ba cách thêm CSS

```html
<!-- 1. Inline CSS - trực tiếp trên element -->
<p style="color: red; font-size: 16px;">Văn bản đỏ</p>

<!-- 2. Internal CSS - trong thẻ <style> -->
<head>
    <style>
        p { color: blue; }
        .highlight { background: yellow; }
    </style>
</head>

<!-- 3. External CSS - file riêng (KHUYÊN DÙNG) -->
<head>
    <link rel="stylesheet" href="styles.css" />
</head>
```

**Thứ tự ưu tiên:** Inline > Internal > External (nhưng phụ thuộc Specificity)

---

## 3. Selectors

### 3.1 Basic Selectors

```css
/* Universal Selector */
* { margin: 0; padding: 0; box-sizing: border-box; }

/* Type/Element Selector */
p { color: #333; }
h1 { font-size: 2rem; }

/* Class Selector */
.btn { padding: 10px 20px; }
.btn-primary { background: #3498db; }

/* ID Selector */
#header { background: #2c3e50; }

/* Attribute Selector */
input[type="text"] { border: 1px solid #ccc; }
a[href^="https"] { color: green; }     /* Bắt đầu bằng "https" */
a[href$=".pdf"] { color: red; }        /* Kết thúc bằng ".pdf" */
a[href*="google"] { font-weight: bold; } /* Chứa "google" */
```

### 3.2 Combinator Selectors

```css
/* Descendant (con cháu) */
div p { color: blue; }           /* Mọi <p> bên trong <div> */

/* Child (con trực tiếp) */
div > p { color: red; }          /* Chỉ <p> là con trực tiếp của <div> */

/* Adjacent Sibling (anh em kề) */
h2 + p { margin-top: 0; }       /* <p> ngay sau <h2> */

/* General Sibling (anh em chung) */
h2 ~ p { color: gray; }         /* Mọi <p> sau <h2> cùng cấp */
```

### 3.3 Grouping

```css
h1, h2, h3, h4, h5, h6 {
    font-family: 'Arial', sans-serif;
    line-height: 1.4;
}
```

---

## 4. Specificity và Cascade

### 4.1 Specificity (Độ ưu tiên)

```
!important    > Inline style > ID > Class/Attribute/Pseudo-class > Element/Pseudo-element
(10000)         (1000)        (100)  (10)                          (1)
```

```css
/* Specificity: 0-0-1 (1 element) */
p { color: black; }

/* Specificity: 0-1-0 (1 class) */
.text { color: blue; }

/* Specificity: 1-0-0 (1 ID) */
#intro { color: red; }

/* Specificity: 1-1-1 */
#intro .text p { color: green; }

/* !important - tránh dùng */
p { color: purple !important; }
```

### 4.2 Cascade Order

Khi cùng specificity, quy tắc sau sẽ ghi đè quy tắc trước:

```css
p { color: red; }
p { color: blue; }  /* -> Kết quả: blue (khai báo sau thắng) */
```

### 4.3 Inheritance

```css
/* Một số thuộc tính kế thừa từ parent */
body {
    font-family: Arial, sans-serif;  /* Kế thừa */
    color: #333;                     /* Kế thừa */
    border: 1px solid red;           /* KHÔNG kế thừa */
}

/* Ép kế thừa */
.child {
    border: inherit;  /* Ép kế thừa border từ parent */
}
```

---

## 5. Box Model

### 5.1 Mô hình hộp

```
+------------------------------------------+
|              margin                       |
|  +------------------------------------+  |
|  |           border                   |  |
|  |  +------------------------------+  |  |
|  |  |         padding              |  |  |
|  |  |  +------------------------+  |  |  |
|  |  |  |       content          |  |  |  |
|  |  |  |    (width x height)    |  |  |  |
|  |  |  +------------------------+  |  |  |
|  |  +------------------------------+  |  |
|  +------------------------------------+  |
+------------------------------------------+
```

```css
.box {
    width: 300px;
    height: 200px;
    padding: 20px;
    border: 2px solid #333;
    margin: 10px;
}

/* Với content-box (mặc định):
   Tổng chiều rộng = 300 + 20*2 + 2*2 + 10*2 = 364px */

/* Với border-box (khuyến dùng):
   Tổng chiều rộng = 300 + 10*2 = 320px (padding + border nằm trong 300px) */

*, *::before, *::after {
    box-sizing: border-box;  /* LUÔN SỬ DỤNG */
}
```

### 5.2 Margin Collapsing

```css
/* Margin trên-dưới của 2 block elements sẽ gộp lại (không cộng) */
.box1 { margin-bottom: 30px; }
.box2 { margin-top: 20px; }
/* Khoảng cách thực tế: 30px (lấy giá trị lớn hơn), KHÔNG PHẢI 50px */

/* Cách tránh margin collapsing: */
.parent {
    overflow: hidden;      /* Hoặc */
    display: flow-root;    /* Hoặc */
    padding-top: 1px;      /* Hoặc */
    border-top: 1px solid transparent;
}
```

---

## 6. Typography

### 6.1 Font Properties

```css
body {
    font-family: 'Roboto', 'Segoe UI', Arial, sans-serif;
    font-size: 16px;       /* Base font size */
    font-weight: 400;      /* 100-900, normal=400, bold=700 */
    font-style: italic;    /* normal, italic, oblique */
    line-height: 1.6;      /* 1.4-1.8 cho body text */
    letter-spacing: 0.5px; /* Khoảng cách chữ */
    word-spacing: 2px;
    text-align: center;    /* left, right, center, justify */
    text-decoration: none; /* underline, overline, line-through */
    text-transform: uppercase; /* lowercase, capitalize, none */
    text-indent: 2em;      /* Thụt dòng đầu tiên */
}
```

### 6.2 Đơn vị đo

```css
.units {
    /* Absolute Units */
    width: 300px;          /* Pixels */
    font-size: 12pt;       /* Points (in ấn) */

    /* Relative Units */
    font-size: 1.5em;      /* Tương đối với font-size parent */
    font-size: 1.5rem;     /* Tương đối với font-size root (html) */
    width: 50%;            /* Tương đối với parent */
    width: 50vw;           /* 50% viewport width */
    height: 100vh;         /* 100% viewport height */
    width: 50vmin;         /* 50% của chiều nhỏ hơn (vw hoặc vh) */
    font-size: clamp(14px, 2vw, 22px);  /* Min, preferred, max */
}
```

### 6.3 Google Fonts

```html
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
```

```css
body { font-family: 'Roboto', sans-serif; }
```

---

## 7. Colors và Backgrounds

### 7.1 Color Values

```css
.colors {
    color: red;                        /* Named color */
    color: #ff6347;                    /* Hex */
    color: #f634;                      /* Hex với alpha */
    color: rgb(255, 99, 71);           /* RGB */
    color: rgba(255, 99, 71, 0.5);     /* RGBA (alpha: 0-1) */
    color: hsl(9, 100%, 64%);          /* HSL (Hue, Saturation, Lightness) */
    color: hsla(9, 100%, 64%, 0.5);    /* HSLA */
}
```

### 7.2 Background

```css
.background {
    background-color: #f5f5f5;
    background-image: url('bg.jpg');
    background-repeat: no-repeat;       /* repeat, repeat-x, repeat-y */
    background-position: center center;
    background-size: cover;             /* contain, 100% 100% */
    background-attachment: fixed;       /* scroll, local */

    /* Shorthand */
    background: #f5f5f5 url('bg.jpg') no-repeat center/cover;

    /* Gradient */
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    background: radial-gradient(circle, #fff 0%, #000 100%);
    background: conic-gradient(red, yellow, green, blue, red);
}
```

---

## 8. Display và Visibility

### 8.1 Display

```css
/* Block: chiếm toàn bộ chiều rộng, xuống dòng */
div, p, h1, section { display: block; }

/* Inline: chỉ chiếm vừa nội dung, không xuống dòng */
span, a, strong { display: inline; }

/* Inline-block: inline nhưng có thể set width/height */
.badge { display: inline-block; width: 100px; height: 30px; }

/* None: ẩn hoàn toàn, không chiếm không gian */
.hidden { display: none; }

/* Flex và Grid */
.flex-container { display: flex; }
.grid-container { display: grid; }
```

### 8.2 Visibility và Opacity

```css
/* visibility: hidden - ẨN nhưng VẪN chiếm không gian */
.invisible { visibility: hidden; }

/* opacity: 0 - trong suốt nhưng vẫn chiếm không gian và tương tác được */
.transparent { opacity: 0; }

/* display: none - ẨN và KHÔNG chiếm không gian */
.gone { display: none; }
```

---

## 9. Positioning

### 9.1 Position Values

```css
/* Static (mặc định) - theo document flow */
.static { position: static; }

/* Relative - dịch chuyển tương đối với vị trí gốc */
.relative {
    position: relative;
    top: 10px;
    left: 20px;
    /* Vẫn chiếm không gian tại vị trí gốc */
}

/* Absolute - thoát khỏi flow, định vị theo ancestor gần nhất có position != static */
.absolute {
    position: absolute;
    top: 0;
    right: 0;
    /* Không chiếm không gian trong flow */
}

/* Fixed - định vị theo viewport, không di chuyển khi scroll */
.fixed-header {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    z-index: 1000;
}

/* Sticky - kết hợp relative và fixed */
.sticky-nav {
    position: sticky;
    top: 0;                /* Dính tại top khi scroll qua */
    z-index: 100;
}
```

### 9.2 Z-index

```css
/* Chỉ hoạt động với position != static */
.behind { z-index: 1; }
.front { z-index: 10; }
.overlay { z-index: 100; }
.modal { z-index: 1000; }
```

---

## 10. Flexbox

### 10.1 Flex Container

```css
.flex-container {
    display: flex;

    /* Hướng chính */
    flex-direction: row;          /* row | row-reverse | column | column-reverse */

    /* Wrap */
    flex-wrap: wrap;              /* nowrap | wrap | wrap-reverse */

    /* Shorthand */
    flex-flow: row wrap;

    /* Canh chỉnh trên trục chính (main axis) */
    justify-content: center;
    /* flex-start | flex-end | center | space-between | space-around | space-evenly */

    /* Canh chỉnh trên trục phụ (cross axis) */
    align-items: center;
    /* flex-start | flex-end | center | stretch | baseline */

    /* Canh chỉnh các dòng (khi wrap) */
    align-content: space-between;

    gap: 16px;                    /* Khoảng cách giữa items */
    row-gap: 10px;
    column-gap: 20px;
}
```

### 10.2 Flex Items

```css
.flex-item {
    flex-grow: 1;     /* Tỷ lệ giãn ra (0 = không giãn) */
    flex-shrink: 0;   /* Tỷ lệ co lại (0 = không co) */
    flex-basis: 200px; /* Kích thước ban đầu */

    /* Shorthand */
    flex: 1 0 200px;  /* grow shrink basis */
    flex: 1;          /* = flex: 1 1 0% */

    /* Tự canh chỉnh trên cross axis */
    align-self: flex-end;

    order: -1;        /* Thứ tự hiển thị (mặc định: 0) */
}
```

### 10.3 Flexbox Patterns

```css
/* Căn giữa hoàn hảo */
.center-both {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
}

/* Navbar: logo trái, menu phải */
.navbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

/* Holy Grail Layout */
.layout {
    display: flex;
    min-height: 100vh;
}
.sidebar { flex: 0 0 250px; }
.main-content { flex: 1; }
```

---

## 11. CSS Grid

### 11.1 Grid Container

```css
.grid-container {
    display: grid;

    /* Định nghĩa cột */
    grid-template-columns: 200px 1fr 200px;        /* 3 cột */
    grid-template-columns: repeat(3, 1fr);          /* 3 cột bằng nhau */
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); /* Responsive */

    /* Định nghĩa dòng */
    grid-template-rows: 80px 1fr 60px;

    /* Gap */
    gap: 20px;
    row-gap: 10px;
    column-gap: 20px;

    /* Template Areas */
    grid-template-areas:
        "header header header"
        "sidebar main aside"
        "footer footer footer";
}
```

### 11.2 Grid Items

```css
.header { grid-area: header; }
.sidebar { grid-area: sidebar; }
.main { grid-area: main; }
.aside { grid-area: aside; }
.footer { grid-area: footer; }

/* Hoặc dùng line numbers */
.item {
    grid-column: 1 / 3;     /* Từ cột 1 đến cột 3 */
    grid-row: 1 / 2;        /* Từ dòng 1 đến dòng 2 */

    /* Shorthand */
    grid-column: span 2;    /* Chiếm 2 cột */
    grid-row: span 3;       /* Chiếm 3 dòng */
}
```

### 11.3 Grid vs Flexbox

| Tính năng | Flexbox | Grid |
|-----------|---------|------|
| Chiều | 1 chiều (row HOẶC column) | 2 chiều (row VÀ column) |
| Use case | Navigation, toolbar, cards | Page layout, dashboard |
| Content-based | Kích thước theo nội dung | Kích thước theo grid |
| Overlap | Khó | Dễ (grid-area chồng nhau) |

---

## 12. Transitions và Animations

### 12.1 Transitions

```css
.button {
    background: #3498db;
    color: white;
    padding: 12px 24px;
    border: none;
    border-radius: 4px;
    cursor: pointer;

    /* Transition */
    transition: background 0.3s ease, transform 0.2s ease;
    /* transition: property duration timing-function delay; */
    /* timing: ease | linear | ease-in | ease-out | ease-in-out | cubic-bezier() */
}

.button:hover {
    background: #2980b9;
    transform: translateY(-2px);
}
```

### 12.2 Animations

```css
/* Định nghĩa keyframes */
@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(30px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

@keyframes pulse {
    0%   { transform: scale(1); }
    50%  { transform: scale(1.05); }
    100% { transform: scale(1); }
}

/* Áp dụng animation */
.card {
    animation: fadeInUp 0.6s ease-out forwards;
    /* animation: name duration timing-function delay iteration-count direction fill-mode; */
}

.icon:hover {
    animation: pulse 1s ease infinite;
}
```

### 12.3 Transform

```css
.transform {
    transform: translateX(50px);       /* Di chuyển */
    transform: translateY(-20px);
    transform: translate(50px, -20px);
    transform: rotate(45deg);          /* Xoay */
    transform: scale(1.5);             /* Phóng to */
    transform: scale(0.8);             /* Thu nhỏ */
    transform: skewX(10deg);           /* Nghiêng */
    transform: matrix(1, 0, 0, 1, 0, 0); /* Kết hợp tất cả */

    /* Nhiều transform */
    transform: translate(-50%, -50%) rotate(45deg) scale(1.2);

    /* Góc xoay */
    transform-origin: center center;   /* top left, 50% 50%, etc */
}
```

---

## 13. Pseudo-classes và Pseudo-elements

### 13.1 Pseudo-classes

```css
/* State */
a:hover { color: red; }
a:active { color: darkred; }
a:visited { color: purple; }
input:focus { outline: 2px solid blue; }
input:disabled { opacity: 0.5; }
input:checked { accent-color: green; }

/* Structural */
li:first-child { font-weight: bold; }
li:last-child { border-bottom: none; }
li:nth-child(2n) { background: #f5f5f5; }     /* Chẵn */
li:nth-child(2n+1) { background: white; }      /* Lẻ */
li:nth-child(3) { color: red; }                /* Thứ 3 */
p:first-of-type { font-size: 1.2em; }
:root { --primary: #3498db; }

/* Phủ định */
p:not(.special) { color: gray; }
li:not(:last-child) { border-bottom: 1px solid #eee; }

/* Form */
input:valid { border-color: green; }
input:invalid { border-color: red; }
input:required { border-left: 3px solid red; }
input:placeholder-shown { border-color: gray; }

/* Modern */
.container:has(> .error) { border-color: red; }
```

### 13.2 Pseudo-elements

```css
/* Thêm nội dung trước/sau */
.required::before {
    content: "* ";
    color: red;
}

blockquote::before {
    content: "\201C";      /* Dấu ngoặc kép mở */
    font-size: 2em;
}

/* Định dạng dòng đầu */
p::first-line { font-weight: bold; }
p::first-letter { font-size: 2em; float: left; }

/* Định dạng selection */
::selection {
    background: #3498db;
    color: white;
}

/* Placeholder */
input::placeholder {
    color: #999;
    font-style: italic;
}

/* Scrollbar (Webkit) */
::-webkit-scrollbar { width: 8px; }
::-webkit-scrollbar-thumb { background: #888; border-radius: 4px; }
```

---

## 14. CSS Variables (Custom Properties)

```css
:root {
    /* Khai báo biến */
    --primary-color: #3498db;
    --secondary-color: #2ecc71;
    --font-size-base: 16px;
    --spacing-sm: 8px;
    --spacing-md: 16px;
    --spacing-lg: 32px;
    --border-radius: 8px;
    --shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.card {
    background: var(--primary-color);
    padding: var(--spacing-md);
    border-radius: var(--border-radius);
    box-shadow: var(--shadow);
    font-size: var(--font-size-base, 14px);  /* Giá trị mặc định: 14px */
}

/* Override trong scope */
.dark-theme {
    --primary-color: #1a1a2e;
    --secondary-color: #16213e;
}

/* Tính toán với calc() */
.container {
    width: calc(100% - var(--spacing-lg) * 2);
    padding: var(--spacing-md);
}
```

---

## 15. CSS Functions

```css
.functions {
    /* calc() - Tính toán */
    width: calc(100% - 60px);
    font-size: calc(14px + 0.5vw);

    /* min(), max(), clamp() */
    width: min(90%, 1200px);
    font-size: max(16px, 1.2vw);
    font-size: clamp(14px, 2.5vw, 22px);  /* min, preferred, max */

    /* var() - CSS Variables */
    color: var(--text-color, #333);

    /* url() */
    background-image: url('image.png');

    /* Color functions */
    color: rgb(255, 0, 0);
    color: hsl(120, 100%, 50%);
    color: color-mix(in srgb, red 50%, blue 50%);

    /* Filter */
    filter: blur(5px);
    filter: brightness(1.2);
    filter: grayscale(100%);
    filter: drop-shadow(2px 2px 4px rgba(0,0,0,0.3));
}
```

---

## 16. Best Practices

### 16.1 Quy tắc chung

1. **Mobile-first:** Viết CSS cho mobile trước, dùng media queries cho màn hình lớn
2. **BEM Naming:** `.block__element--modifier` (vd: `.card__title--large`)
3. **Tránh ID cho styling:** Dùng class thay vì ID
4. **Tránh !important:** Chỉ dùng khi thật sự cần thiết
5. **CSS Variables:** Dùng cho colors, spacing, fonts để dễ bảo trì
6. **border-box:** Luôn set `box-sizing: border-box` cho tất cả elements
7. **Shorthand:** Dùng shorthand properties khi có thể
8. **Responsive units:** Dùng rem, em, %, vw/vh thay vì px cố định
9. **Logical Properties:** Dùng `margin-inline`, `padding-block` cho i18n

### 16.2 CSS Reset/Normalize

```css
/* Modern CSS Reset */
*, *::before, *::after {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
}

html {
    font-size: 16px;
    -webkit-text-size-adjust: 100%;
}

body {
    min-height: 100vh;
    line-height: 1.6;
    font-family: system-ui, -apple-system, 'Segoe UI', Roboto, sans-serif;
}

img, picture, video, canvas, svg {
    display: block;
    max-width: 100%;
}

input, button, textarea, select {
    font: inherit;
}
```

---

## Tổng kết

CSS là công cụ định kiểu mạnh mẽ cho web:

1. **Selectors:** Nhiều cách chọn elements (class, ID, attribute, combinator)
2. **Box Model:** Mỗi element là một hộp với content, padding, border, margin
3. **Layout:** Flexbox (1 chiều) và Grid (2 chiều)
4. **Responsive:** Media queries, relative units, clamp()
5. **Animations:** Transitions cho tương tác, @keyframes cho animation phức tạp
6. **Variables:** Custom properties giúp tái sử dụng và bảo trì
7. **Specificity:** Hiểu quy tắc cascade để tránh xung đột
