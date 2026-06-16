# Responsive Web Design - Bai Tap

## Bai 1: Responsive Card Grid
**Do kho: De**

Tao mot trang hien thi danh sach san pham dung grid responsive:

1. Tren mobile (< 768px): 1 cot
2. Tren tablet (768px - 1023px): 2 cot
3. Tren desktop (>= 1024px): 3 cot
4. Tren large desktop (>= 1440px): 4 cot
5. Moi card co hinh anh, ten, gia, nut mua
6. Hinh anh trong card phai fluid (max-width: 100%)
7. Gap giua cac cards tang dan theo breakpoint (16px -> 24px -> 32px)

---

## Bai 2: Mobile-First Landing Page
**Do kho: Trung binh**

Thiet ke landing page hoan chinh theo Mobile-First:

1. **Hero section:** Full-width background image, tieu de lon (clamp), nut CTA
2. **Features:** 3 cards (1 cot mobile -> 3 cot desktop)
3. **Navigation:** Hamburger menu tren mobile, inline menu tren desktop
4. **Testimonials:** Slider 1 item tren mobile, 3 items tren desktop
5. **Footer:** Stacked tren mobile, multi-column tren desktop
6. Su dung clamp() cho typography
7. Su dung CSS Variables cho spacing responsive
8. Touch targets toi thieu 44x44px tren mobile

---

## Bai 3: Dashboard Responsive Layout
**Do kho: Trung binh**

Tao admin dashboard responsive:

1. **Desktop:** Sidebar (250px) + Main content
2. **Tablet:** Sidebar thu gon (icon only, 60px) + Main content
3. **Mobile:** Khong co sidebar, them bottom navigation
4. Main content co stat cards (dung auto-fill minmax)
5. Bieu do/bang chiem full width tren mobile
6. Su dung CSS Grid template areas thay doi theo breakpoint
7. Sticky header tren tat ca thiet bi

---

## Bai 4: Responsive Image Gallery
**Do kho: Trung binh**

Tao gallery hinh anh responsive:

1. Su dung `<picture>` va `srcset` cho responsive images
2. Layout masonry-style voi CSS Grid
3. Tren mobile: 2 cot
4. Tren tablet: 3 cot
5. Tren desktop: 4 cot
6. Mot so hinh lon chiem 2 cot (span)
7. Lazy loading cho tat ca hinh anh
8. Hover overlay voi thong tin hinh anh

---

## Bai 5: Responsive Email Template
**Do kho: Kho**

Tao email template responsive (gioi han: chi dung table layout va inline CSS):

1. Header voi logo (can giua)
2. Hero image full-width
3. 2-column content tren desktop, stack tren mobile
4. Button CTA lon, de bam tren mobile
5. Footer voi social links
6. Max-width 600px (chuan email)
7. Font fallback an toan (Arial, Helvetica, sans-serif)
8. Chi dung inline CSS va `<table>` (email clients khong ho tro Flexbox/Grid)
9. Test tren Gmail, Outlook (dung media queries co dieu kien)
