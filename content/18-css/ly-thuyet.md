# CSS - Ly Thuyet Tu Co Ban Den Nang Cao

## Muc luc

1. [Gioi thieu ve CSS](#1-gioi-thieu-ve-css)
2. [Cach them CSS vao HTML](#2-cach-them-css-vao-html)
3. [Selectors](#3-selectors)
4. [Specificity va Cascade](#4-specificity-va-cascade)
5. [Box Model](#5-box-model)
6. [Typography](#6-typography)
7. [Colors va Backgrounds](#7-colors-va-backgrounds)
8. [Display va Visibility](#8-display-va-visibility)
9. [Positioning](#9-positioning)
10. [Flexbox](#10-flexbox)
11. [CSS Grid](#11-css-grid)
12. [Transitions va Animations](#12-transitions-va-animations)
13. [Pseudo-classes va Pseudo-elements](#13-pseudo-classes-va-pseudo-elements)
14. [CSS Variables (Custom Properties)](#14-css-variables-custom-properties)
15. [CSS Functions](#15-css-functions)
16. [Best Practices](#16-best-practices)

---

## 1. Gioi thieu ve CSS

### 1.1 CSS la gi?

CSS (Cascading Style Sheets) la ngon ngu dinh kieu dung de mo ta cach trinh bay cua tai lieu HTML. CSS tach biet noi dung (HTML) va trinh bay (CSS), giup trang web dep hon va de bao tri hon.

### 1.2 Lich su phat trien

| Nam | Su kien |
|-----|---------|
| 1996 | CSS1 - font, color, text, margin, border |
| 1998 | CSS2 - positioning, z-index, media types |
| 2011 | CSS3 - modules: Flexbox, Grid, Animations, Variables |
| 2017 | CSS Grid Layout duoc ho tro rong rai |
| 2022 | Container Queries, :has() pseudo-class |
| 2024 | Nesting, @scope, Scroll-driven Animations |

---

## 2. Cach them CSS vao HTML

### 2.1 Ba cach them CSS

```html
<!-- 1. Inline CSS - truc tiep tren element -->
<p style="color: red; font-size: 16px;">Van ban do</p>

<!-- 2. Internal CSS - trong the <style> -->
<head>
    <style>
        p { color: blue; }
        .highlight { background: yellow; }
    </style>
</head>

<!-- 3. External CSS - file rieng (KHUYEN DUNG) -->
<head>
    <link rel="stylesheet" href="styles.css" />
</head>
```

**Thu tu uu tien:** Inline > Internal > External (nhung phu thuoc Specificity)

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
a[href^="https"] { color: green; }     /* Bat dau bang "https" */
a[href$=".pdf"] { color: red; }        /* Ket thuc bang ".pdf" */
a[href*="google"] { font-weight: bold; } /* Chua "google" */
```

### 3.2 Combinator Selectors

```css
/* Descendant (con chau) */
div p { color: blue; }           /* Moi <p> ben trong <div> */

/* Child (con truc tiep) */
div > p { color: red; }          /* Chi <p> la con truc tiep cua <div> */

/* Adjacent Sibling (anh em ke) */
h2 + p { margin-top: 0; }       /* <p> ngay sau <h2> */

/* General Sibling (anh em chung) */
h2 ~ p { color: gray; }         /* Moi <p> sau <h2> cung cap */
```

### 3.3 Grouping

```css
h1, h2, h3, h4, h5, h6 {
    font-family: 'Arial', sans-serif;
    line-height: 1.4;
}
```

---

## 4. Specificity va Cascade

### 4.1 Specificity (Do uu tien)

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

/* !important - tranh dung */
p { color: purple !important; }
```

### 4.2 Cascade Order

Khi cung specificity, quy tac sau se ghi de quy tac truoc:

```css
p { color: red; }
p { color: blue; }  /* -> Ket qua: blue (khai bao sau thang) */
```

### 4.3 Inheritance

```css
/* Mot so thuoc tinh ke thua tu parent */
body {
    font-family: Arial, sans-serif;  /* Ke thua */
    color: #333;                     /* Ke thua */
    border: 1px solid red;           /* KHONG ke thua */
}

/* Ep ke thua */
.child {
    border: inherit;  /* Ep ke thua border tu parent */
}
```

---

## 5. Box Model

### 5.1 Mo hinh hop

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

/* Voi content-box (mac dinh):
   Tong chieu rong = 300 + 20*2 + 2*2 + 10*2 = 364px */

/* Voi border-box (khuyen dung):
   Tong chieu rong = 300 + 10*2 = 320px (padding + border nam trong 300px) */

*, *::before, *::after {
    box-sizing: border-box;  /* LUON SU DUNG */
}
```

### 5.2 Margin Collapsing

```css
/* Margin tren-duoi cua 2 block elements se gop lai (khong cong) */
.box1 { margin-bottom: 30px; }
.box2 { margin-top: 20px; }
/* Khoang cach thuc te: 30px (lay gia tri lon hon), KHONG PHAI 50px */

/* Cach tranh margin collapsing: */
.parent {
    overflow: hidden;      /* Hoac */
    display: flow-root;    /* Hoac */
    padding-top: 1px;      /* Hoac */
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
    letter-spacing: 0.5px; /* Khoang cach chu */
    word-spacing: 2px;
    text-align: center;    /* left, right, center, justify */
    text-decoration: none; /* underline, overline, line-through */
    text-transform: uppercase; /* lowercase, capitalize, none */
    text-indent: 2em;      /* Thut dong dau tien */
}
```

### 6.2 Don vi do

```css
.units {
    /* Absolute Units */
    width: 300px;          /* Pixels */
    font-size: 12pt;       /* Points (in an) */

    /* Relative Units */
    font-size: 1.5em;      /* Tuong doi voi font-size parent */
    font-size: 1.5rem;     /* Tuong doi voi font-size root (html) */
    width: 50%;            /* Tuong doi voi parent */
    width: 50vw;           /* 50% viewport width */
    height: 100vh;         /* 100% viewport height */
    width: 50vmin;         /* 50% cua chieu nho hon (vw hoac vh) */
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

## 7. Colors va Backgrounds

### 7.1 Color Values

```css
.colors {
    color: red;                        /* Named color */
    color: #ff6347;                    /* Hex */
    color: #f634;                      /* Hex voi alpha */
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

## 8. Display va Visibility

### 8.1 Display

```css
/* Block: chiem toan bo chieu rong, xuong dong */
div, p, h1, section { display: block; }

/* Inline: chi chiem vua noi dung, khong xuong dong */
span, a, strong { display: inline; }

/* Inline-block: inline nhung co the set width/height */
.badge { display: inline-block; width: 100px; height: 30px; }

/* None: an hoan toan, khong chiem khong gian */
.hidden { display: none; }

/* Flex va Grid */
.flex-container { display: flex; }
.grid-container { display: grid; }
```

### 8.2 Visibility va Opacity

```css
/* visibility: hidden - AN nhung VAN chiem khong gian */
.invisible { visibility: hidden; }

/* opacity: 0 - trong suot nhung van chiem khong gian va tuong tac duoc */
.transparent { opacity: 0; }

/* display: none - AN va KHONG chiem khong gian */
.gone { display: none; }
```

---

## 9. Positioning

### 9.1 Position Values

```css
/* Static (mac dinh) - theo document flow */
.static { position: static; }

/* Relative - dich chuyen tuong doi voi vi tri goc */
.relative {
    position: relative;
    top: 10px;
    left: 20px;
    /* Van chiem khong gian tai vi tri goc */
}

/* Absolute - thoat khoi flow, dinh vi theo ancestor gan nhat co position != static */
.absolute {
    position: absolute;
    top: 0;
    right: 0;
    /* Khong chiem khong gian trong flow */
}

/* Fixed - dinh vi theo viewport, khong di chuyen khi scroll */
.fixed-header {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    z-index: 1000;
}

/* Sticky - ket hop relative va fixed */
.sticky-nav {
    position: sticky;
    top: 0;                /* Dinh tai top khi scroll qua */
    z-index: 100;
}
```

### 9.2 Z-index

```css
/* Chi hoat dong voi position != static */
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

    /* Huong chinh */
    flex-direction: row;          /* row | row-reverse | column | column-reverse */

    /* Wrap */
    flex-wrap: wrap;              /* nowrap | wrap | wrap-reverse */

    /* Shorthand */
    flex-flow: row wrap;

    /* Canh chinh tren truc chinh (main axis) */
    justify-content: center;
    /* flex-start | flex-end | center | space-between | space-around | space-evenly */

    /* Canh chinh tren truc phu (cross axis) */
    align-items: center;
    /* flex-start | flex-end | center | stretch | baseline */

    /* Canh chinh cac dong (khi wrap) */
    align-content: space-between;

    gap: 16px;                    /* Khoang cach giua items */
    row-gap: 10px;
    column-gap: 20px;
}
```

### 10.2 Flex Items

```css
.flex-item {
    flex-grow: 1;     /* Ty le gian ra (0 = khong gian) */
    flex-shrink: 0;   /* Ty le co lai (0 = khong co) */
    flex-basis: 200px; /* Kich thuoc ban dau */

    /* Shorthand */
    flex: 1 0 200px;  /* grow shrink basis */
    flex: 1;          /* = flex: 1 1 0% */

    /* Tu canh chinh tren cross axis */
    align-self: flex-end;

    order: -1;        /* Thu tu hien thi (mac dinh: 0) */
}
```

### 10.3 Flexbox Patterns

```css
/* Can giua hoan hao */
.center-both {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
}

/* Navbar: logo trai, menu phai */
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

    /* Dinh nghia cot */
    grid-template-columns: 200px 1fr 200px;        /* 3 cot */
    grid-template-columns: repeat(3, 1fr);          /* 3 cot bang nhau */
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); /* Responsive */

    /* Dinh nghia dong */
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

/* Hoac dung line numbers */
.item {
    grid-column: 1 / 3;     /* Tu cot 1 den cot 3 */
    grid-row: 1 / 2;        /* Tu dong 1 den dong 2 */

    /* Shorthand */
    grid-column: span 2;    /* Chiem 2 cot */
    grid-row: span 3;       /* Chiem 3 dong */
}
```

### 11.3 Grid vs Flexbox

| Tinh nang | Flexbox | Grid |
|-----------|---------|------|
| Chieu | 1 chieu (row HOAC column) | 2 chieu (row VA column) |
| Use case | Navigation, toolbar, cards | Page layout, dashboard |
| Content-based | Kich thuoc theo noi dung | Kich thuoc theo grid |
| Overlap | Kho | De (grid-area chong nhau) |

---

## 12. Transitions va Animations

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
/* Dinh nghia keyframes */
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

/* Ap dung animation */
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
    transform: translateX(50px);       /* Di chuyen */
    transform: translateY(-20px);
    transform: translate(50px, -20px);
    transform: rotate(45deg);          /* Xoay */
    transform: scale(1.5);             /* Phong to */
    transform: scale(0.8);             /* Thu nho */
    transform: skewX(10deg);           /* Nghieng */
    transform: matrix(1, 0, 0, 1, 0, 0); /* Ket hop tat ca */

    /* Nhieu transform */
    transform: translate(-50%, -50%) rotate(45deg) scale(1.2);

    /* Goc xoay */
    transform-origin: center center;   /* top left, 50% 50%, etc */
}
```

---

## 13. Pseudo-classes va Pseudo-elements

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
li:nth-child(2n) { background: #f5f5f5; }     /* Chan */
li:nth-child(2n+1) { background: white; }      /* Le */
li:nth-child(3) { color: red; }                /* Thu 3 */
p:first-of-type { font-size: 1.2em; }
:root { --primary: #3498db; }

/* Phu dinh */
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
/* Them noi dung truoc/sau */
.required::before {
    content: "* ";
    color: red;
}

blockquote::before {
    content: "\201C";      /* Dau ngoac kep mo */
    font-size: 2em;
}

/* Dinh dang dong dau */
p::first-line { font-weight: bold; }
p::first-letter { font-size: 2em; float: left; }

/* Dinh dang selection */
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
    /* Khai bao bien */
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
    font-size: var(--font-size-base, 14px);  /* Gia tri mac dinh: 14px */
}

/* Override trong scope */
.dark-theme {
    --primary-color: #1a1a2e;
    --secondary-color: #16213e;
}

/* Tinh toan voi calc() */
.container {
    width: calc(100% - var(--spacing-lg) * 2);
    padding: var(--spacing-md);
}
```

---

## 15. CSS Functions

```css
.functions {
    /* calc() - Tinh toan */
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

### 16.1 Quy tac chung

1. **Mobile-first:** Viet CSS cho mobile truoc, dung media queries cho man hinh lon
2. **BEM Naming:** `.block__element--modifier` (vd: `.card__title--large`)
3. **Tranh ID cho styling:** Dung class thay vi ID
4. **Tranh !important:** Chi dung khi that su can thiet
5. **CSS Variables:** Dung cho colors, spacing, fonts de de bao tri
6. **border-box:** Luon set `box-sizing: border-box` cho tat ca elements
7. **Shorthand:** Dung shorthand properties khi co the
8. **Responsive units:** Dung rem, em, %, vw/vh thay vi px co dinh
9. **Logical Properties:** Dung `margin-inline`, `padding-block` cho i18n

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

## Tong ket

CSS la cong cu dinh kieu manh me cho web:

1. **Selectors:** Nhieu cach chon elements (class, ID, attribute, combinator)
2. **Box Model:** Moi element la mot hop voi content, padding, border, margin
3. **Layout:** Flexbox (1 chieu) va Grid (2 chieu)
4. **Responsive:** Media queries, relative units, clamp()
5. **Animations:** Transitions cho tuong tac, @keyframes cho animation phuc tap
6. **Variables:** Custom properties giup tai su dung va bao tri
7. **Specificity:** Hieu quy tac cascade de tranh xung dot
