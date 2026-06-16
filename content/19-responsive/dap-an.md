# Responsive Web Design - Đáp Án Bài Tập

## Bài 1: Responsive Card Grid

```html
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Product Grid</title>
    <style>
        :root {
            --gap: 16px;
            --card-radius: 12px;
        }

        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

        body {
            font-family: system-ui, sans-serif;
            background: #f5f5f5;
            padding: var(--gap);
        }

        .product-grid {
            display: grid;
            grid-template-columns: 1fr;
            gap: var(--gap);
            max-width: 1400px;
            margin: 0 auto;
        }

        .card {
            background: white;
            border-radius: var(--card-radius);
            overflow: hidden;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
        }

        .card img {
            width: 100%;
            height: 200px;
            object-fit: cover;
            display: block;
        }

        .card-body { padding: 16px; }

        .card-title {
            font-size: 1.1rem;
            margin-bottom: 8px;
        }

        .card-price {
            font-size: 1.25rem;
            font-weight: 700;
            color: #e74c3c;
            margin-bottom: 12px;
        }

        .card-btn {
            width: 100%;
            padding: 12px;
            background: #3498db;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 1rem;
            min-height: 44px;
        }

        /* Tablet: 2 cột */
        @media (min-width: 768px) {
            :root { --gap: 24px; }
            .product-grid { grid-template-columns: repeat(2, 1fr); }
        }

        /* Desktop: 3 cột */
        @media (min-width: 1024px) {
            :root { --gap: 32px; }
            .product-grid { grid-template-columns: repeat(3, 1fr); }
        }

        /* Large Desktop: 4 cột */
        @media (min-width: 1440px) {
            .product-grid { grid-template-columns: repeat(4, 1fr); }
        }
    </style>
</head>
<body>
    <div class="product-grid">
        <div class="card">
            <img src="https://via.placeholder.com/400x200" alt="Sản phẩm 1" loading="lazy" />
            <div class="card-body">
                <h3 class="card-title">Sản phẩm 1</h3>
                <p class="card-price">299.000 VND</p>
                <button class="card-btn">Mua ngay</button>
            </div>
        </div>
        <div class="card">
            <img src="https://via.placeholder.com/400x200" alt="Sản phẩm 2" loading="lazy" />
            <div class="card-body">
                <h3 class="card-title">Sản phẩm 2</h3>
                <p class="card-price">499.000 VND</p>
                <button class="card-btn">Mua ngay</button>
            </div>
        </div>
        <div class="card">
            <img src="https://via.placeholder.com/400x200" alt="Sản phẩm 3" loading="lazy" />
            <div class="card-body">
                <h3 class="card-title">Sản phẩm 3</h3>
                <p class="card-price">199.000 VND</p>
                <button class="card-btn">Mua ngay</button>
            </div>
        </div>
        <div class="card">
            <img src="https://via.placeholder.com/400x200" alt="Sản phẩm 4" loading="lazy" />
            <div class="card-body">
                <h3 class="card-title">Sản phẩm 4</h3>
                <p class="card-price">599.000 VND</p>
                <button class="card-btn">Mua ngay</button>
            </div>
        </div>
    </div>
</body>
</html>
```

**Giải thích:**
- Mobile-first: bắt đầu với 1 cột (`grid-template-columns: 1fr`)
- CSS Variables cho gap, thay đổi theo breakpoint
- `object-fit: cover` giữ hình ảnh không bị méo
- `min-height: 44px` cho button đảm bảo touch target trên mobile
- `loading="lazy"` giúp performance

---

## Bài 2: Mobile-First Landing Page

```css
/* === Base (Mobile) === */
:root {
    --spacing-sm: 16px;
    --spacing-md: 24px;
    --spacing-lg: 48px;
    --primary: #667eea;
    --text: #333;
}

*, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

body {
    font-family: system-ui, sans-serif;
    color: var(--text);
    line-height: 1.6;
}

/* Navigation - Mobile: hamburger */
.navbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px var(--spacing-sm);
    background: white;
    position: sticky;
    top: 0;
    z-index: 1000;
    box-shadow: 0 2px 4px rgba(0,0,0,0.05);
}

.nav-toggle {
    display: block;
    background: none;
    border: none;
    font-size: 1.5rem;
    min-width: 44px;
    min-height: 44px;
    cursor: pointer;
}

.nav-menu {
    display: none;
    list-style: none;
    position: absolute;
    top: 60px;
    left: 0;
    width: 100%;
    background: white;
    box-shadow: 0 4px 8px rgba(0,0,0,0.1);
}

.nav-menu.active { display: block; }

.nav-menu a {
    display: block;
    padding: 16px 24px;
    text-decoration: none;
    color: var(--text);
    min-height: 44px;
}

/* Hero */
.hero {
    padding: var(--spacing-lg) var(--spacing-sm);
    text-align: center;
    background: linear-gradient(135deg, #667eea, #764ba2);
    color: white;
}

.hero h1 {
    font-size: clamp(1.75rem, 5vw, 3.5rem);
    margin-bottom: var(--spacing-sm);
}

.hero p {
    font-size: clamp(1rem, 2vw, 1.25rem);
    margin-bottom: var(--spacing-md);
}

.cta-btn {
    display: inline-block;
    padding: 14px 32px;
    background: white;
    color: var(--primary);
    border-radius: 8px;
    text-decoration: none;
    font-weight: 600;
    min-height: 44px;
}

/* Features - 1 cột mobile */
.features {
    padding: var(--spacing-lg) var(--spacing-sm);
    display: grid;
    grid-template-columns: 1fr;
    gap: var(--spacing-md);
    max-width: 1200px;
    margin: 0 auto;
}

.feature-card {
    text-align: center;
    padding: var(--spacing-md);
    border-radius: 12px;
    background: #f8f9fa;
}

/* Footer - stacked */
.footer {
    background: #2c3e50;
    color: white;
    padding: var(--spacing-lg) var(--spacing-sm);
}

.footer-grid {
    display: grid;
    grid-template-columns: 1fr;
    gap: var(--spacing-md);
}

/* === Tablet (>= 768px) === */
@media (min-width: 768px) {
    :root {
        --spacing-sm: 24px;
        --spacing-md: 32px;
    }

    .features { grid-template-columns: repeat(2, 1fr); }
    .footer-grid { grid-template-columns: repeat(2, 1fr); }
}

/* === Desktop (>= 1024px) === */
@media (min-width: 1024px) {
    .nav-toggle { display: none; }

    .nav-menu {
        display: flex;
        position: static;
        box-shadow: none;
        gap: 8px;
    }

    .nav-menu a { padding: 8px 16px; }

    .features { grid-template-columns: repeat(3, 1fr); }
    .footer-grid { grid-template-columns: repeat(4, 1fr); }
}
```

---

## Bài 3: Dashboard Responsive Layout

```css
.dashboard {
    display: grid;
    grid-template-areas:
        "header"
        "main";
    grid-template-rows: 60px 1fr;
    min-height: 100vh;
}

.dashboard__header {
    grid-area: header;
    background: #1a202c;
    color: white;
    display: flex;
    align-items: center;
    padding: 0 16px;
    position: sticky;
    top: 0;
    z-index: 100;
}

.dashboard__sidebar { display: none; }

.dashboard__main {
    grid-area: main;
    padding: 16px;
    padding-bottom: 76px;   /* Cho đủ bottom nav trên mobile */
}

.stat-cards {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 16px;
    margin-bottom: 24px;
}

/* Bottom nav chỉ trên mobile */
.bottom-nav {
    display: flex;
    justify-content: space-around;
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 60px;
    background: white;
    box-shadow: 0 -2px 8px rgba(0,0,0,0.1);
    z-index: 100;
    align-items: center;
}

.bottom-nav a {
    min-width: 44px;
    min-height: 44px;
    display: flex;
    align-items: center;
    justify-content: center;
}

/* Tablet: sidebar thu gọn */
@media (min-width: 768px) {
    .dashboard {
        grid-template-areas:
            "header header"
            "sidebar main";
        grid-template-columns: 60px 1fr;
    }

    .dashboard__sidebar {
        display: flex;
        flex-direction: column;
        align-items: center;
        grid-area: sidebar;
        background: #2d3748;
        padding-top: 16px;
    }

    .dashboard__sidebar .nav-text { display: none; }

    .dashboard__main { padding-bottom: 16px; }
    .bottom-nav { display: none; }
}

/* Desktop: sidebar đầy đủ */
@media (min-width: 1024px) {
    .dashboard {
        grid-template-columns: 250px 1fr;
    }

    .dashboard__sidebar {
        align-items: flex-start;
        padding: 16px;
    }

    .dashboard__sidebar .nav-text { display: inline; }

    .stat-cards {
        grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    }
}
```

---

## Bài 4: Responsive Image Gallery

```css
.gallery {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 8px;
    padding: 8px;
}

.gallery-item {
    position: relative;
    overflow: hidden;
    border-radius: 8px;
    aspect-ratio: 1 / 1;
}

.gallery-item.wide {
    grid-column: span 2;
    aspect-ratio: 2 / 1;
}

.gallery-item img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    display: block;
    transition: transform 0.3s ease;
}

.gallery-item:hover img {
    transform: scale(1.05);
}

.gallery-overlay {
    position: absolute;
    inset: 0;
    background: rgba(0, 0, 0, 0.5);
    color: white;
    display: flex;
    align-items: flex-end;
    padding: 16px;
    opacity: 0;
    transition: opacity 0.3s ease;
}

.gallery-item:hover .gallery-overlay {
    opacity: 1;
}

@media (min-width: 768px) {
    .gallery {
        grid-template-columns: repeat(3, 1fr);
        gap: 12px;
        padding: 12px;
    }
}

@media (min-width: 1024px) {
    .gallery {
        grid-template-columns: repeat(4, 1fr);
        gap: 16px;
        padding: 16px;
    }
}
```

```html
<div class="gallery">
    <div class="gallery-item wide">
        <picture>
            <source media="(min-width: 1024px)" srcset="photo1-large.jpg" />
            <source media="(min-width: 768px)" srcset="photo1-medium.jpg" />
            <img src="photo1-small.jpg" alt="Hình 1" loading="lazy" />
        </picture>
        <div class="gallery-overlay">
            <p>Hình 1 - Phong cảnh</p>
        </div>
    </div>
    <div class="gallery-item">
        <img src="photo2.jpg" alt="Hình 2" loading="lazy" />
        <div class="gallery-overlay">
            <p>Hình 2</p>
        </div>
    </div>
</div>
```
