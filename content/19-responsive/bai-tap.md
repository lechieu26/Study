# Responsive Web Design - Bài Tập

## Bài 1: Responsive Card Grid
**Độ khó: Dễ**

Tạo một trang hiển thị danh sách sản phẩm dùng grid responsive:

1. Trên mobile (< 768px): 1 cột
2. Trên tablet (768px - 1023px): 2 cột
3. Trên desktop (>= 1024px): 3 cột
4. Trên large desktop (>= 1440px): 4 cột
5. Mỗi card có hình ảnh, tên, giá, nút mua
6. Hình ảnh trong card phải fluid (max-width: 100%)
7. Gap giữa các cards tăng dần theo breakpoint (16px -> 24px -> 32px)

---

## Bài 2: Mobile-First Landing Page
**Độ khó: Trung bình**

Thiết kế landing page hoàn chỉnh theo Mobile-First:

1. **Hero section:** Full-width background image, tiêu đề lớn (clamp), nút CTA
2. **Features:** 3 cards (1 cột mobile -> 3 cột desktop)
3. **Navigation:** Hamburger menu trên mobile, inline menu trên desktop
4. **Testimonials:** Slider 1 item trên mobile, 3 items trên desktop
5. **Footer:** Stacked trên mobile, multi-column trên desktop
6. Sử dụng clamp() cho typography
7. Sử dụng CSS Variables cho spacing responsive
8. Touch targets tối thiểu 44x44px trên mobile

---

## Bài 3: Dashboard Responsive Layout
**Độ khó: Trung bình**

Tạo admin dashboard responsive:

1. **Desktop:** Sidebar (250px) + Main content
2. **Tablet:** Sidebar thu gọn (icon only, 60px) + Main content
3. **Mobile:** Không có sidebar, thêm bottom navigation
4. Main content có stat cards (dùng auto-fill minmax)
5. Biểu đồ/bảng chiếm full width trên mobile
6. Sử dụng CSS Grid template areas thay đổi theo breakpoint
7. Sticky header trên tất cả thiết bị

---

## Bài 4: Responsive Image Gallery
**Độ khó: Trung bình**

Tạo gallery hình ảnh responsive:

1. Sử dụng `<picture>` và `srcset` cho responsive images
2. Layout masonry-style với CSS Grid
3. Trên mobile: 2 cột
4. Trên tablet: 3 cột
5. Trên desktop: 4 cột
6. Một số hình lớn chiếm 2 cột (span)
7. Lazy loading cho tất cả hình ảnh
8. Hover overlay với thông tin hình ảnh

---

## Bài 5: Responsive Email Template
**Độ khó: Khó**

Tạo email template responsive (giới hạn: chỉ dùng table layout và inline CSS):

1. Header với logo (căn giữa)
2. Hero image full-width
3. 2-column content trên desktop, stack trên mobile
4. Button CTA lớn, dễ bấm trên mobile
5. Footer với social links
6. Max-width 600px (chuẩn email)
7. Font fallback an toàn (Arial, Helvetica, sans-serif)
8. Chỉ dùng inline CSS và `<table>` (email clients không hỗ trợ Flexbox/Grid)
9. Test trên Gmail, Outlook (dùng media queries có điều kiện)
