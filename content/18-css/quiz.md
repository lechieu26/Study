# Quiz - CSS

## Câu 1

[TYPE: MULTIPLE_CHOICE]

CSS là viết tắt của gì?

- [ ] Computer Style Sheets
- [x] Cascading Style Sheets
- [ ] Colorful Style Sheets
- [ ] Creative Style Sheets

> **Giải thích:** CSS = Cascading Style Sheets. "Cascading" (thác đổ) mô tả cách các quy tắc CSS được áp dụng theo thứ tự ưu tiên (specificity, source order).

## Câu 2

[TYPE: SELECT_RESULT]

Với CSS sau, màu của văn bản là gì?

```css
p { color: red; }
p { color: blue; }
.text { color: green; }
```

```html
<p class="text">Hello</p>
```

- [ ] red
- [ ] blue
- [x] green
- [ ] black

> **Giải thích:** Class selector `.text` có specificity (0-1-0) cao hơn element selector `p` (0-0-1). Do đó `.text { color: green }` thắng, bất kể thứ tự khai báo.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

`box-sizing: border-box` có tác dụng gì?

- [ ] Thêm border cho element
- [ ] Xóa padding của element
- [x] Width/height bao gồm cả padding và border
- [ ] Chỉ áp dụng cho block elements

> **Giải thích:** Với `border-box`, width và height tính luôn padding và border. Ví dụ: `width: 300px` thì tổng chiều rộng là 300px (bao gồm content + padding + border). Với `content-box` (mặc định), 300px chỉ là content, padding và border cộng thêm.

## Câu 4

[TYPE: MULTIPLE_CHOICE]

Đâu là cách CĂN GIỮA một element cả ngang và dọc với Flexbox?

- [ ] `display: flex; align-items: center;`
- [ ] `display: flex; justify-content: center;`
- [x] `display: flex; justify-content: center; align-items: center;`
- [ ] `display: flex; text-align: center;`

> **Giải thích:** `justify-content: center` căn giữa theo trục chính (main axis, mặc định là ngang). `align-items: center` căn giữa theo trục phụ (cross axis, mặc định là dọc). Cần cả hai để căn giữa hoàn toàn.

## Câu 5

[TYPE: MULTIPLE_CHOICE]

Sự khác biệt giữa `display: none` và `visibility: hidden`?

- [x] `display: none` xóa khỏi layout, `visibility: hidden` vẫn chiếm không gian
- [ ] Không có sự khác biệt
- [ ] `visibility: hidden` xóa khỏi layout, `display: none` vẫn chiếm không gian
- [ ] `display: none` chỉ ẩn trên mobile

> **Giải thích:** `display: none` xóa element khỏi document flow, không chiếm không gian. `visibility: hidden` ẩn element nhưng VẪN giữ chỗ trong layout. `opacity: 0` cũng ẩn nhưng vẫn chiếm chỗ và có thể tương tác (click).

## Câu 6

[TYPE: MULTIPLE_CHOICE]

`position: sticky` hoạt động như thế nào?

- [ ] Giống `position: fixed`
- [ ] Giống `position: absolute`
- [x] Hoạt động như `relative` cho đến khi scroll đến ngưỡng, rồi chuyển thành `fixed`
- [ ] Chỉ hoạt động trong Flexbox

> **Giải thích:** `position: sticky` kết hợp relative và fixed. Element ở vị trí bình thường (relative) cho đến khi người dùng scroll đến ngưỡng (vd: `top: 0`), lúc đó no "dính" lại như fixed. Cần có `top/bottom/left/right` và parent không có `overflow: hidden`.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

CSS Specificity đúng nhất là gì?

- [ ] ID > Class > Inline style > Element
- [x] !important > Inline style > ID > Class > Element
- [ ] Class > ID > Element > Inline style
- [ ] Element > Class > ID > Inline style

> **Giải thích:** Thứ tự ưu tiên: `!important` (cao nhất) > Inline style (thuộc tính style="") > ID selector (#id) > Class/Attribute/Pseudo-class (.class, [attr], :hover) > Element/Pseudo-element (p, ::before). Khi cùng specificity, khai báo SAU sẽ thắng.

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Khi nào nên dùng CSS Grid thay vì Flexbox?

- [ ] Khi cần layout 1 chiều
- [x] Khi cần layout 2 chiều (dòng và cột cùng lúc)
- [ ] Khi cần animation
- [ ] Grid đã thay thế hoàn toàn Flexbox

> **Giải thích:** CSS Grid lý tưởng cho layout 2 chiều (định nghĩa cả dòng và cột). Flexbox tốt cho layout 1 chiều (row HOẶC column). Trong thực tế, chúng thường được kết hợp: Grid cho page layout, Flexbox cho component layout.

## Câu 9

[TYPE: SELECT_RESULT]

Giá trị của `--size` trong element `.child` là gì?

```css
:root { --size: 16px; }
.parent { --size: 20px; }
.child { font-size: var(--size); }
```

```html
<div class="parent">
    <div class="child">Text</div>
</div>
```

- [ ] 16px
- [x] 20px
- [ ] 0px
- [ ] inherit

> **Giải thích:** CSS Variables kế thừa theo DOM tree. `.child` nằm trong `.parent`, nên `--size` được kế thừa từ `.parent` (20px) thay vì `:root` (16px). Giá trị gần nhất trong scope chain sẽ được sử dụng.

## Câu 10

[TYPE: MULTIPLE_CHOICE]

`clamp(14px, 2.5vw, 22px)` có ý nghĩa gì?

- [ ] Font size luôn là 2.5vw
- [ ] Font size là 14px hoặc 22px
- [x] Font size là 2.5vw nhưng tối thiểu 14px và tối đa 22px
- [ ] Font size tăng từ 14px đến 22px rồi dừng

> **Giải thích:** `clamp(min, preferred, max)` trả về giá trị preferred (2.5vw) nhưng đảm bảo không nhỏ hơn min (14px) và không lớn hơn max (22px). Đây là cách tuyệt vời để tạo responsive typography mà không cần media queries.

## Câu 11

[TYPE: MULTIPLE_CHOICE]

Pseudo-element nào dùng để thêm nội dung TRƯỚC một element?

- [ ] `:before`
- [x] `::before`
- [ ] `:first-child`
- [ ] `::first-line`

> **Giải thích:** `::before` (2 dấu hai chấm) là pseudo-element thêm nội dung trước element. CSS3 quy định pseudo-elements dùng `::` (2 dấu) để phân biệt với pseudo-classes dùng `:` (1 dấu). Cần có `content` property để hoạt động.

## Câu 12

[TYPE: MULTIPLE_CHOICE]

Tại sao nên dùng `rem` thay vì `px` cho font-size?

- [ ] `rem` nhanh hơn `px`
- [ ] `rem` đẹp hơn `px`
- [x] `rem` tương đối với root font-size, giúp accessibility tốt hơn (người dùng có thể thay đổi font-size trình duyệt)
- [ ] `px` đã bị loại bỏ trong CSS3

> **Giải thích:** `rem` (root em) tương đối với font-size của `<html>`. Khi người dùng thay đổi font-size mặc định của trình duyệt (ví dụ người kém mắt tăng lên 20px), tất cả nội dung dùng `rem` sẽ tự động điều chỉnh. `px` cố định nên không phản hồi thay đổi này.

## Câu 13

[TYPE: TRUE_FALSE]

Margin của hai block elements kề nhau sẽ cộng lại (ví dụ: margin-bottom 20px + margin-top 30px = 50px).

- [ ] True
- [x] False

> **Giải thích:** Đây là Margin Collapsing. Margin dọc (top/bottom) của hai block elements kề nhau sẽ gộp lại, lấy giá trị LỚN HƠN. Với margin-bottom 20px + margin-top 30px, khoảng cách thực tế là 30px (không phải 50px). Margin ngang (left/right) KHÔNG collapse.

## Câu 14

[TYPE: MULTIPLE_CHOICE]

`z-index` chỉ hoạt động khi element có thuộc tính nào?

- [x] `position` khác `static` (hoặc trong flex/grid container)
- [ ] `display: block`
- [ ] `overflow: hidden`
- [ ] `z-index` luôn hoạt động

> **Giải thích:** `z-index` chỉ có tác dụng trên elements có `position: relative/absolute/fixed/sticky` hoặc là flex/grid items. Trên element `position: static` (mặc định), `z-index` bị bỏ qua.

## Câu 15

[TYPE: MULTIPLE_CHOICE]

Cách tốt nhất để ẩn nội dung nhưng vẫn cho screen reader đọc được?

- [ ] `display: none`
- [ ] `visibility: hidden`
- [ ] `opacity: 0`
- [x] `position: absolute; width: 1px; height: 1px; clip: rect(0,0,0,0); overflow: hidden;`

> **Giải thích:** `display: none` và `visibility: hidden` đều ẩn khỏi screen reader. `opacity: 0` vẫn có thể bị click. Kỹ thuật "visually hidden" (hoặc sr-only) dùng position absolute với clip để ẩn thị giác nhưng vẫn được screen reader đọc. Đây là pattern phổ biến trong accessibility.
