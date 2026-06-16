# Quiz - HTML

## Câu 1

[TYPE: MULTIPLE_CHOICE]

HTML là viết tắt của gì?

- [ ] Hyper Transfer Markup Language
- [x] HyperText Markup Language
- [ ] High Tech Modern Language
- [ ] HyperText Machine Language

> **Giải thích:** HTML = HyperText Markup Language (Ngôn ngữ Đánh dấu Siêu văn bản). Đây là ngôn ngữ đánh dấu dùng để tạo cấu trúc nội dung trang web.

## Câu 2

[TYPE: MULTIPLE_CHOICE]

Thẻ HTML nào dùng để tạo tiêu đề lớn nhất?

- [x] `<h1>`
- [ ] `<heading>`
- [ ] `<head>`
- [ ] `<title>`

> **Giải thích:** `<h1>` là thẻ heading lớn nhất (h1 → h6). `<head>` chứa metadata, `<title>` là tiêu đề tab trình duyệt, `<heading>` không tồn tại trong HTML.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Thuộc tính nào bắt buộc phải có trong thẻ `<img>`?

- [ ] `src`
- [ ] `alt`
- [x] Cả `src` và `alt`
- [ ] `width` và `height`

> **Giải thích:** Theo chuẩn HTML, `<img>` cần cả `src` (đường dẫn ảnh) và `alt` (mô tả thay thế khi ảnh không tải được, quan trọng cho accessibility và SEO).

## Câu 4

[TYPE: MULTIPLE_CHOICE]

Thẻ nào là thẻ semantic HTML?

- [ ] `<div>`
- [ ] `<span>`
- [x] `<article>`
- [ ] `<b>`

> **Giải thích:** `<article>` là thẻ semantic - nó truyền đạt ý nghĩa (nội dung độc lập). `<div>` và `<span>` là thẻ non-semantic (chỉ là container). `<b>` chỉ là định dạng hình thức.

## Câu 5

[TYPE: MULTIPLE_CHOICE]

Đoạn code nào tạo liên kết mở tab mới AN TOÀN?

- [ ] `<a href="url" target="_blank">Link</a>`
- [x] `<a href="url" target="_blank" rel="noopener noreferrer">Link</a>`
- [ ] `<a href="url" new-tab="true">Link</a>`
- [ ] `<link href="url" target="_blank" />`

> **Giải thích:** Khi dùng `target="_blank"`, cần thêm `rel="noopener noreferrer"` để ngăn trang mới truy cập `window.opener` (lỗ hổng bảo mật). `<link>` dùng trong `<head>` cho stylesheet, không phải hyperlink.

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Thẻ `<meta name="viewport" content="width=device-width, initial-scale=1.0">` có tác dụng gì?

- [ ] Tăng tốc độ tải trang
- [ ] Thêm SEO cho trang
- [x] Điều chỉnh viewport cho responsive trên mobile
- [ ] Thay đổi charset của trang

> **Giải thích:** Viewport meta tag bảo trình duyệt mobile điều chỉnh chiều rộng trang bằng chiều rộng thiết bị và scale ban đầu là 1.0. Không có thẻ này, trang sẽ hiển thị như trên desktop và bị thu nhỏ trên mobile.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

Sự khác biệt giữa `<strong>` và `<b>` là gì?

- [ ] Không có sự khác biệt
- [ ] `<strong>` in đậm hơn `<b>`
- [x] `<strong>` có ý nghĩa ngữ nghĩa (quan trọng), `<b>` chỉ định dạng hình thức
- [ ] `<b>` đã bị loại bỏ trong HTML5

> **Giải thích:** `<strong>` mang ý nghĩa ngữ nghĩa - nội dung quan trọng, screen reader sẽ nhấn mạnh khi đọc. `<b>` chỉ làm in đậm về hình thức (visual), không mang ý nghĩa gì cho máy tính/screen reader.

## Câu 8

[TYPE: SELECT_RESULT]

Đoạn code HTML sau sẽ render như thế nào?

```html
<ol start="3" reversed>
    <li>Mục A</li>
    <li>Mục B</li>
    <li>Mục C</li>
</ol>
```

- [ ] 3. Mục A, 4. Mục B, 5. Mục C
- [ ] 1. Mục A, 2. Mục B, 3. Mục C
- [x] 3. Mục A, 2. Mục B, 1. Mục C
- [ ] C. Mục A, B. Mục B, A. Mục C

> **Giải thích:** `start="3"` bắt đầu từ số 3, `reversed` đảo ngược thứ tự đếm. Nên kết quả là 3, 2, 1.

## Câu 9

[TYPE: MULTIPLE_CHOICE]

Input type nào KHÔNG tồn tại trong HTML5?

- [ ] `<input type="email">`
- [ ] `<input type="date">`
- [x] `<input type="timestamp">`
- [ ] `<input type="range">`

> **Giải thích:** HTML5 có các input type: text, email, password, number, date, time, datetime-local, range, color, tel, url, search, file, ... Nhưng KHÔNG có `type="timestamp"`. Để nhập ngày giờ, dùng `type="datetime-local"`.

## Câu 10

[TYPE: MULTIPLE_CHOICE]

Cách đúng nhất để nhóm các trường form liên quan?

- [ ] `<div class="group">`
- [ ] `<section>`
- [x] `<fieldset>` với `<legend>`
- [ ] `<group>`

> **Giải thích:** `<fieldset>` và `<legend>` là thẻ chuẩn để nhóm các form controls liên quan. `<fieldset>` tạo viền bao quanh, `<legend>` là tiêu đề của nhóm. Đây là cách tốt nhất cho accessibility.

## Câu 11

[TYPE: MULTIPLE_CHOICE]

Thẻ nào dùng để nhúng video YouTube vào trang web?

- [ ] `<video src="youtube-url">`
- [ ] `<embed src="youtube-url">`
- [x] `<iframe src="youtube-embed-url">`
- [ ] `<object data="youtube-url">`

> **Giải thích:** YouTube cung cấp embed URL dạng `https://www.youtube.com/embed/VIDEO_ID` để nhúng qua `<iframe>`. Thẻ `<video>` chỉ dùng cho file video trực tiếp, không phải URL streaming.

## Câu 12

[TYPE: TRUE_FALSE]

`localStorage` và `sessionStorage` đều mất dữ liệu khi đóng trình duyệt.

- [ ] True
- [x] False

> **Giải thích:** `sessionStorage` mất khi đóng tab/cửa sổ. Nhưng `localStorage` lưu trữ VĨNH VIỄN cho đến khi bị xóa bằng code hoặc user tự xóa. Dù đóng trình duyệt, `localStorage` vẫn còn.

## Câu 13

[TYPE: MULTIPLE_CHOICE]

Thuộc tính `loading="lazy"` trên thẻ `<img>` có tác dụng gì?

- [ ] Giảm kích thước hình ảnh
- [ ] Làm mờ hình ảnh khi tải
- [x] Chỉ tải hình ảnh khi nó xuất hiện gần viewport
- [ ] Tải hình ảnh từ server khác

> **Giải thích:** `loading="lazy"` là native lazy loading - trình duyệt chỉ bắt đầu tải hình ảnh khi người dùng cuộn gần đến vị trí của nó. Giúp tăng tốc độ tải trang ban đầu vì không tải tất cả hình cùng lúc.

## Câu 14

[TYPE: MULTIPLE_CHOICE]

Đâu là cách đúng nhất để thêm alt text cho hình ảnh trang trí (không mang nội dung)?

- [ ] Không thêm thuộc tính `alt`
- [ ] `alt="hình trang trí"`
- [x] `alt=""` (alt rỗng)
- [ ] `alt="image"`

> **Giải thích:** Với hình ảnh trang trí (không mang thông tin), dùng `alt=""` (chuỗi rỗng). Screen reader sẽ bỏ qua hình này. Nếu không có `alt` thì screen reader sẽ đọc tên file, gây khó chịu. `alt="hình trang trí"` không cung cấp thông tin hữu ích.

## Câu 15

[TYPE: MULTIPLE_CHOICE]

Sự khác biệt giữa `defer` và `async` trong thẻ `<script>` là gì?

- [ ] Không có sự khác biệt
- [ ] `defer` tải nhanh hơn `async`
- [x] `defer` chạy sau khi DOM parsed xong, `async` chạy ngay khi tải xong
- [ ] `async` chỉ dùng cho module scripts

> **Giải thích:** Cả hai đều tải script song song với HTML parsing. Nhưng `defer` đảm bảo script chạy THEO THỨ TỰ và SAU KHI DOM parsed xong. `async` chạy NGAY khi tải xong (không đảm bảo thứ tự). Dùng `defer` cho app scripts, `async` cho analytics/ads.
