# CSS - Dap An Bai Tap

## Bai 1: Card Component

```html
<div class="card">
    <img src="https://via.placeholder.com/400x200" alt="San pham" class="card__image" />
    <div class="card__body">
        <h3 class="card__title">Ten san pham</h3>
        <p class="card__desc">Mo ta ngan ve san pham, tinh nang noi bat.</p>
        <div class="card__footer">
            <span class="card__price">299.000 VND</span>
            <button class="card__btn">Mua ngay</button>
        </div>
    </div>
</div>
```

```css
:root {
    --card-bg: #ffffff;
    --card-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    --card-shadow-hover: 0 8px 24px rgba(0, 0, 0, 0.15);
    --card-radius: 12px;
    --primary: #3498db;
    --primary-hover: #2980b9;
    --text: #333333;
    --text-light: #666666;
}

*, *::before, *::after { box-sizing: border-box; }

.card {
    background: var(--card-bg);
    border-radius: var(--card-radius);
    box-shadow: var(--card-shadow);
    overflow: hidden;
    max-width: 360px;
    transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.card:hover {
    transform: translateY(-4px);
    box-shadow: var(--card-shadow-hover);
}

.card__image {
    width: 100%;
    height: 200px;
    object-fit: cover;
    display: block;
}

.card__body { padding: 20px; }

.card__title {
    font-size: 1.25rem;
    color: var(--text);
    margin-bottom: 8px;
}

.card__desc {
    color: var(--text-light);
    font-size: 0.9rem;
    line-height: 1.5;
    margin-bottom: 16px;
}

.card__footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.card__price {
    font-size: 1.2rem;
    font-weight: 700;
    color: var(--primary);
}

.card__btn {
    background: var(--primary);
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 6px;
    cursor: pointer;
    font-size: 0.9rem;
    transition: background 0.3s ease;
}

.card__btn:hover {
    background: var(--primary-hover);
}
```

**Giai thich:**
- BEM naming convention cho de bao tri
- CSS Variables de thay doi theme de dang
- transition tren card va button cho smooth hover effect
- object-fit: cover de hinh anh khong bi meo

---

## Bai 2: Flexbox Navigation Bar

```html
<nav class="navbar">
    <a href="/" class="navbar__logo">MyBrand</a>
    <ul class="navbar__menu">
        <li><a href="/" class="navbar__link navbar__link--active">Trang chu</a></li>
        <li><a href="/about" class="navbar__link">Gioi thieu</a></li>
        <li><a href="/services" class="navbar__link">Dich vu</a></li>
        <li><a href="/blog" class="navbar__link">Blog</a></li>
        <li><a href="/contact" class="navbar__link">Lien he</a></li>
    </ul>
</nav>
```

```css
:root {
    --nav-bg: #2c3e50;
    --nav-text: #ecf0f1;
    --nav-hover: #3498db;
    --nav-active: #e74c3c;
}

.navbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: var(--nav-bg);
    padding: 0 24px;
    position: sticky;
    top: 0;
    z-index: 1000;
}

.navbar__logo {
    color: white;
    font-size: 1.5rem;
    font-weight: bold;
    text-decoration: none;
}

.navbar__menu {
    display: flex;
    list-style: none;
    gap: 8px;
    margin: 0;
    padding: 0;
}

.navbar__link {
    color: var(--nav-text);
    text-decoration: none;
    padding: 16px 12px;
    display: block;
    position: relative;
    transition: color 0.3s ease;
}

.navbar__link::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 50%;
    width: 0;
    height: 3px;
    background: var(--nav-hover);
    transition: width 0.3s ease, left 0.3s ease;
}

.navbar__link:hover { color: var(--nav-hover); }
.navbar__link:hover::after { width: 100%; left: 0; }

.navbar__link--active { color: var(--nav-active); }
.navbar__link--active::after {
    width: 100%;
    left: 0;
    background: var(--nav-active);
}

@media (max-width: 768px) {
    .navbar { flex-direction: column; padding: 12px; }
    .navbar__menu { flex-direction: column; width: 100%; text-align: center; }
}
```

---

## Bai 3: CSS Grid Dashboard

```css
.dashboard {
    display: grid;
    grid-template-areas:
        "header  header"
        "sidebar main"
        "footer  footer";
    grid-template-columns: 250px 1fr;
    grid-template-rows: 64px 1fr 48px;
    min-height: 100vh;
    gap: 0;
}

.dashboard__header {
    grid-area: header;
    background: #2c3e50;
    color: white;
    display: flex;
    align-items: center;
    padding: 0 24px;
}

.dashboard__sidebar {
    grid-area: sidebar;
    background: #34495e;
    color: white;
    padding: 16px;
}

.dashboard__main {
    grid-area: main;
    padding: 24px;
    background: #ecf0f1;
}

.dashboard__footer {
    grid-area: footer;
    background: #2c3e50;
    color: white;
    display: flex;
    align-items: center;
    justify-content: center;
}

/* Stat cards trong main */
.stat-cards {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 20px;
}

.stat-card {
    background: white;
    padding: 24px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

@media (max-width: 768px) {
    .dashboard {
        grid-template-areas:
            "header"
            "sidebar"
            "main"
            "footer";
        grid-template-columns: 1fr;
        grid-template-rows: 64px auto 1fr 48px;
    }
}
```

---

## Bai 4: Animation Loading Spinner

```css
/* === 1. Spinner tron === */
:root {
    --spinner-size: 40px;
    --spinner-color: #3498db;
    --dot-color: #e74c3c;
    --bar-color: #2ecc71;
}

.spinner-circle {
    width: var(--spinner-size);
    height: var(--spinner-size);
    border: 4px solid #eee;
    border-top-color: var(--spinner-color);
    border-radius: 50%;
    animation: spin 0.8s linear infinite;
}

@keyframes spin {
    to { transform: rotate(360deg); }
}

/* === 2. Dots bouncing === */
.dots {
    display: flex;
    gap: 8px;
}

.dot {
    width: 12px;
    height: 12px;
    border-radius: 50%;
    background: var(--dot-color);
    animation: bounce 0.6s ease-in-out infinite alternate;
}

.dot:nth-child(2) { animation-delay: 0.2s; }
.dot:nth-child(3) { animation-delay: 0.4s; }

@keyframes bounce {
    from { transform: translateY(0); }
    to { transform: translateY(-16px); }
}

/* === 3. Progress bar === */
.progress-bar {
    width: 200px;
    height: 4px;
    background: #eee;
    border-radius: 2px;
    overflow: hidden;
}

.progress-bar__fill {
    height: 100%;
    width: 40%;
    background: var(--bar-color);
    border-radius: 2px;
    animation: progress 1.5s ease-in-out infinite;
}

@keyframes progress {
    0%   { transform: translateX(-100%); }
    100% { transform: translateX(350%); }
}
```

---

## Bai 5: Responsive Pricing Table

```css
:root {
    --bg: #f5f7fa;
    --card-bg: #ffffff;
    --text: #333;
    --text-light: #666;
    --primary: #667eea;
    --primary-dark: #5a67d8;
    --accent: #f6ad55;
    --check: #48bb78;
    --cross: #fc8181;
    --shadow: 0 4px 6px rgba(0, 0, 0, 0.07);
}

.dark-theme {
    --bg: #1a202c;
    --card-bg: #2d3748;
    --text: #e2e8f0;
    --text-light: #a0aec0;
    --shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
}

body {
    background: var(--bg);
    color: var(--text);
    font-family: system-ui, sans-serif;
}

.pricing {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 24px;
    max-width: 1100px;
    margin: 0 auto;
    padding: 48px 16px;
    align-items: start;
}

.pricing-card {
    background: var(--card-bg);
    border-radius: 16px;
    padding: 32px;
    box-shadow: var(--shadow);
    text-align: center;
    transition: transform 0.3s ease, box-shadow 0.3s ease;
    position: relative;
}

.pricing-card:hover {
    transform: scale(1.03);
    box-shadow: 0 12px 24px rgba(0, 0, 0, 0.12);
}

.pricing-card--featured {
    transform: scale(1.05);
    border: 2px solid var(--primary);
}

.pricing-card--featured:hover {
    transform: scale(1.08);
}

.badge {
    position: absolute;
    top: -12px;
    left: 50%;
    transform: translateX(-50%);
    background: var(--accent);
    color: #fff;
    padding: 4px 16px;
    border-radius: 20px;
    font-size: 0.8rem;
    font-weight: 600;
}

.pricing-card__price {
    font-size: clamp(1.8rem, 3vw, 2.5rem);
    font-weight: 700;
    color: var(--primary);
    margin: 16px 0;
}

.feature-list {
    list-style: none;
    padding: 0;
    margin: 24px 0;
    text-align: left;
}

.feature-list li {
    padding: 8px 0;
    padding-left: 28px;
    position: relative;
    color: var(--text-light);
}

.feature-list li.included::before {
    content: "\2713";
    position: absolute;
    left: 0;
    color: var(--check);
    font-weight: bold;
}

.feature-list li.excluded::before {
    content: "\2717";
    position: absolute;
    left: 0;
    color: var(--cross);
}

.cta-btn {
    display: inline-block;
    width: 100%;
    padding: 14px;
    border: none;
    border-radius: 8px;
    font-size: 1rem;
    font-weight: 600;
    color: white;
    background: linear-gradient(135deg, var(--primary), var(--primary-dark));
    cursor: pointer;
    transition: opacity 0.3s ease, transform 0.2s ease;
}

.cta-btn:hover {
    opacity: 0.9;
    transform: translateY(-2px);
}

@media (max-width: 768px) {
    .pricing {
        grid-template-columns: 1fr;
        max-width: 400px;
    }
    .pricing-card--featured {
        transform: scale(1);
        order: -1;
    }
}
```
