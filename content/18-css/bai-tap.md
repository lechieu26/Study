# CSS - Bài Tập

## Bài 1: Card Component
**Độ khó: Dễ**

Tạo một card component với CSS thuần (không dùng framework):

1. Card có bo góc (border-radius), bóng đổ (box-shadow)
2. Hình ảnh trên cùng, chiếm toàn bộ chiều rộng
3. Phần nội dung gồm: tiêu đề, mô tả, giá, nút "Mua ngay"
4. Hover effect: card nâng lên (translateY) và bóng đổ lớn hơn
5. Nút có transition màu khi hover
6. Sử dụng CSS Variables cho màu sắc

---

## Bài 2: Flexbox Navigation Bar
**Độ khó: Dễ**

Tạo thanh navigation responsive với Flexbox:

1. Logo bên trái, menu items bên phải
2. Menu items cách đều nhau
3. Active item có underline và màu khác
4. Hover effect cho mỗi menu item
5. Sticky navigation (dính ở trên khi cuộn)
6. Trên mobile: menu items xếp dọc (column)

---

## Bài 3: CSS Grid Dashboard
**Độ khó: Trung bình**

Tạo dashboard layout sử dụng CSS Grid:

1. Header chiếm toàn bộ chiều rộng
2. Sidebar bên trái (250px cố định)
3. Main content chiếm phần còn lại
4. Footer chiếm toàn bộ chiều rộng
5. Main content có 3 stat cards xếp theo grid (auto-fill, minmax)
6. Sử dụng grid-template-areas
7. Responsive: Trên mobile, sidebar chuyển thành full-width trên main content

---

## Bài 4: Animation Loading Spinner
**Độ khó: Trung bình**

Tạo 3 loại loading spinner chỉ bằng CSS (không JavaScript):

1. **Spinner tròn:** Vòng tròn quay với border và animation rotate
2. **Dots bouncing:** 3 chấm nhảy lên xuống lệch pha (animation-delay)
3. **Progress bar:** Thanh tiến trình chạy từ trái sang phải và lặp lại
4. Sử dụng @keyframes cho mỗi loại
5. Mỗi spinner có thể tùy chỉnh kích thước và màu qua CSS Variables

---

## Bài 5: Responsive Pricing Table
**Độ khó: Khó**

Tạo bảng giá 3 gói (Basic, Pro, Enterprise) với CSS nâng cao:

1. 3 cards xếp ngang, gói "Pro" nổi bật hơn (scale lớn hơn, màu khác, badge "Popular")
2. Hover effect cho mỗi card (scale, shadow)
3. Sử dụng CSS Grid hoặc Flexbox
4. Danh sách tính năng với icon check/cross (pseudo-element ::before)
5. Nút CTA (Call to Action) với gradient background và hover transition
6. Responsive: 3 cột -> 1 cột trên mobile
7. Dark mode toggle sử dụng CSS Variables (thay đổi :root variables)
8. Sử dụng clamp() cho font-size responsive
9. Smooth scroll khi click nút
