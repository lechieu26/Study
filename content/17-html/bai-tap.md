# HTML - Bài Tập

## Bài 1: Tạo trang cá nhân (Personal Page)
**Độ khó: Dễ**

Tạo một trang HTML hoàn chỉnh giới thiệu bản thân với các yêu cầu:

1. Sử dụng đúng cấu trúc HTML5 (DOCTYPE, html, head, body)
2. Chứa tiêu đề `<h1>` với tên bạn
3. Một ảnh đại diện (có thể dùng placeholder image)
4. Đoạn văn giới thiệu bản thân
5. Danh sách kỹ năng (unordered list)
6. Bảng học vấn (table với cột: Trường, Ngành, Năm)
7. Link đến mạng xã hội (ít nhất 3 link)
8. Meta tags cơ bản (charset, viewport, description)

---

## Bài 2: Biểu mẫu đăng ký
**Độ khó: Trung bình**

Tạo form đăng ký tài khoản với các trường:

1. Họ và tên (text, bắt buộc, tối thiểu 2 ký tự)
2. Email (email, bắt buộc)
3. Mật khẩu (password, bắt buộc, tối thiểu 8 ký tự)
4. Xác nhận mật khẩu
5. Ngày sinh (date)
6. Giới tính (radio buttons)
7. Thành phố (select với optgroup Miền Bắc/Nam/Trung)
8. Sở thích (checkbox - nhiều lựa chọn)
9. Giới thiệu bản thân (textarea)
10. Upload ảnh đại diện (file, chỉ chấp nhận image)
11. Đồng ý điều khoản (checkbox bắt buộc)
12. Nút Đăng ký và Xóa form

Yêu cầu: Sử dụng `<fieldset>`, `<legend>`, `<label>`, HTML5 validation attributes.

---

## Bài 3: Semantic Blog Layout
**Độ khó: Trung bình**

Tạo layout blog hoàn chỉnh sử dụng **chỉ HTML semantic** (không CSS):

1. **Header:** Logo, navigation với 5 menu items
2. **Main content:** 3 bài viết (article), mỗi bài có:
   - Tiêu đề, ngày đăng (`<time>`), tác giả
   - Nội dung văn bản, 1 hình ảnh với `<figure>` và `<figcaption>`
   - Tags/categories
3. **Sidebar (aside):** Danh sách bài viết mới nhất, form tìm kiếm
4. **Footer:** Copyright, link chính sách, địa chỉ (`<address>`)

Yêu cầu: Không sử dụng `<div>` khi có thể dùng semantic tag. Sử dụng `<details>` và `<summary>` cho phần FAQ.

---

## Bài 4: Bảng giá sản phẩm
**Độ khó: Trung bình**

Tạo bảng so sánh giá 3 gói dịch vụ (Basic, Pro, Enterprise):

1. Bảng có header row và header column
2. Sử dụng `colspan` và `rowspan` để gộp ô hợp lý
3. Các dòng gồm: Tên gói, Giá, Dung lượng, Số người dùng, Hỗ trợ, Tính năng 1-5
4. Dùng `<caption>` cho tiêu đề bảng
5. Dùng `<thead>`, `<tbody>`, `<tfoot>` đúng cách
6. Dòng cuối (tfoot) chứa nút "Chọn gói" cho mỗi cột

---

## Bài 5: Trang Multimedia
**Độ khó: Khó**

Tạo trang giới thiệu sản phẩm sử dụng đầy đủ multimedia:

1. Video giới thiệu sản phẩm (`<video>` với poster, controls, nhiều source)
2. Audio feedback khách hàng (`<audio>`)
3. Nhúng YouTube video (`<iframe>`)
4. Canvas vẽ logo đơn giản (hình tròn + chữ)
5. Gallery hình ảnh sử dụng `<picture>` với srcset cho responsive
6. Form liên hệ với đầy đủ các trường (text, email, tel, textarea, select)
7. Sử dụng `<details>` cho FAQ section
8. Accessibility: ARIA labels, alt text, skip navigation link

Yêu cầu: Trang phải validate qua W3C Validator. Tối ưu hóa với lazy loading và preload.
