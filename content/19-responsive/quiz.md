# Quiz - Responsive Web Design

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Meta tag viewport nào là CHUẨN cho responsive web?

- [ ] `<meta name="viewport" content="width=1024">`
- [x] `<meta name="viewport" content="width=device-width, initial-scale=1.0">`
- [ ] `<meta name="viewport" content="width=device-width, user-scalable=no">`
- [ ] `<meta name="responsive" content="true">`

> **Giải thích:** `width=device-width` đặt chiều rộng viewport bằng chiều rộng thiết bị, `initial-scale=1.0` đặt tỷ lệ zoom ban đầu. Không nên dùng `user-scalable=no` vì nó cấm zoom, xấu cho accessibility.

## Câu 2

[TYPE: MULTIPLE_CHOICE]

Mobile-First approach sử dụng media query nào?

- [ ] `@media (max-width: 768px)`
- [x] `@media (min-width: 768px)`
- [ ] `@media (width: 768px)`
- [ ] `@media screen`

> **Giải thích:** Mobile-First viết CSS cho mobile trước (không cần media query), rồi dùng `min-width` để THÊM style cho màn hình LỚN HƠN. Desktop-First dùng `max-width`. Mobile-First được khuyến dùng vì code gọn hơn và performance tốt hơn trên mobile.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Thuộc tính CSS nào giúp hình ảnh tự động co giãn theo container?

- [ ] `width: 100%`
- [ ] `max-width: auto`
- [x] `max-width: 100%; height: auto;`
- [ ] `object-fit: contain`

> **Giải thích:** `max-width: 100%` đảm bảo hình không lớn hơn container. `height: auto` giữ tỷ lệ gốc. `width: 100%` sẽ kéo giãn hình lớn hơn kích thước gốc. `object-fit` dùng cho hình trong container có kích thước cố định.

## Câu 4

[TYPE: SELECT_RESULT]

Với CSS Mobile-First sau, trên màn hình 900px rộng, grid có bao nhiêu cột?

```css
.grid { grid-template-columns: 1fr; }

@media (min-width: 768px) {
    .grid { grid-template-columns: repeat(2, 1fr); }
}

@media (min-width: 1024px) {
    .grid { grid-template-columns: repeat(3, 1fr); }
}
```

- [ ] 1 cột
- [x] 2 cột
- [ ] 3 cột
- [ ] 4 cột

> **Giải thích:** 900px > 768px nên media query `min-width: 768px` được kích hoạt (2 cột). Nhưng 900px < 1024px nên media query thứ 2 KHÔNG được kích hoạt. Kết quả: 2 cột.

## Câu 5

[TYPE: MULTIPLE_CHOICE]

`clamp(1rem, 2.5vw, 2rem)` có ý nghĩa gì?

- [ ] Font luôn là 2.5vw
- [ ] Font là 1rem trên mobile, 2rem trên desktop
- [x] Font responsive theo viewport, tối thiểu 1rem, tối đa 2rem
- [ ] Font tăng từ 1rem đến 2rem rồi dừng

> **Giải thích:** `clamp(min, preferred, max)` trả về giá trị preferred (2.5vw) nhưng không dưới min (1rem) và không quá max (2rem). Đây là cách tạo responsive typography không cần media queries.

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Sự khác biệt giữa `auto-fill` và `auto-fit` trong CSS Grid?

- [ ] Không có sự khác biệt
- [x] `auto-fill` giữ các track rỗng, `auto-fit` co các track hiện có để lấp đầy không gian
- [ ] `auto-fill` chỉ dùng với minmax
- [ ] `auto-fit` nhanh hơn `auto-fill`

> **Giải thích:** Khi số items ít hơn số cột có thể: `auto-fill` tạo các track rỗng (có không gian thừa), `auto-fit` collapse các track rỗng và giãn các item để lấp đầy không gian. Trên thực tế, sự khác biệt chỉ rõ khi có ít items.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

Kích thước tối thiểu cho touch target trên mobile là bao nhiêu (theo WCAG)?

- [ ] 24x24px
- [ ] 32x32px
- [x] 44x44px
- [ ] 64x64px

> **Giải thích:** Theo Apple HIG và WCAG 2.1, touch target tối thiểu là 44x44 CSS pixels. Google khuyên 48x48dp. Đây đảm bảo người dùng có thể bấm chính xác trên màn hình cảm ứng mà không bị nhầm.

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Container Queries khác Media Queries ở điểm nào?

- [ ] Container Queries nhanh hơn
- [x] Container Queries dựa trên kích thước parent container, Media Queries dựa trên viewport
- [ ] Media Queries đã bị thay thế bởi Container Queries
- [ ] Container Queries chỉ dùng cho images

> **Giải thích:** Media Queries phản hồi theo kích thước **viewport** (màn hình). Container Queries phản hồi theo kích thước **parent container**. Điều này rất hữu ích cho component-level responsive - một component có thể tự điều chỉnh dù ở bất kỳ vị trí nào trên trang.

## Câu 9

[TYPE: MULTIPLE_CHOICE]

Cách nào tốt nhất để phục vụ hình ảnh khác nhau cho mobile và desktop?

- [ ] Dùng JavaScript để đổi src
- [ ] Dùng CSS media queries với background-image
- [x] Dùng thẻ `<picture>` với `<source>` và media queries
- [ ] Dùng một hình lớn cho tất cả

> **Giải thích:** Thẻ `<picture>` với `<source>` cho phép trình duyệt chọn hình phù hợp TRƯỚC KHI tải. CSS background chỉ tải sau khi render. JavaScript đổi src sẽ tải hình sai trước rồi mới đổi. `<picture>` là giải pháp chuẩn cho art direction responsive images.

## Câu 10

[TYPE: TRUE_FALSE]

Media query `@media (hover: hover)` dùng để phát hiện thiết bị có hỗ trợ hover (chuột) hay không.

- [x] True
- [ ] False

> **Giải thích:** `@media (hover: hover)` kiểm tra thiết bị có primary input hỗ trợ hover không. Desktop với chuột = hover: hover. Mobile cảm ứng = hover: none. Rất hữu ích để chỉ áp dụng hover effects trên desktop, tránh UX xấu trên mobile.

## Câu 11

[TYPE: MULTIPLE_CHOICE]

Đơn vị `svh` (small viewport height) khác `vh` như thế nào?

- [ ] `svh` nhỏ hơn `vh`
- [x] `svh` tính viewport KHI thanh địa chỉ trình duyệt mobile hiển thị (viewport nhỏ nhất), `vh` có thể thay đổi
- [ ] `svh` là đơn vị cũ, `vh` là đơn vị mới
- [ ] Không có sự khác biệt

> **Giải thích:** Trên mobile, thanh địa chỉ trình duyệt có thể ẩn/hiện khi scroll, làm thay đổi chiều cao viewport. `vh` có thể không ổn định. `svh` (small viewport height) luôn tính theo viewport nhỏ nhất (khi address bar hiện). `lvh` (large) tính khi address bar ẩn. `dvh` (dynamic) thay đổi theo trạng thái hiện tại.

## Câu 12

[TYPE: MULTIPLE_CHOICE]

Khi nào nên dùng `aspect-ratio` trong responsive design?

- [ ] Chỉ dùng cho video
- [ ] Chỉ dùng trên desktop
- [x] Khi cần giữ tỷ lệ chiều rộng/cao cố định cho element responsive
- [ ] Thay thế cho width và height

> **Giải thích:** `aspect-ratio` giữ tỷ lệ khi element thay đổi kích thước. Ví dụ: `aspect-ratio: 16/9` cho video container, `aspect-ratio: 1/1` cho hình vuông. Element sẽ tự tính chiều cao dựa trên chiều rộng và tỷ lệ, rất hữu ích cho responsive layout.
