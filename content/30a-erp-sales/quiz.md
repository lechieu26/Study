# Quiz - Module Bán Hàng (Sales)

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Trong quy trình bán hàng ERP, bước nào đến sau Báo giá (Quotation)?

- [ ] Hóa đơn (Invoice)
- [x] Đơn hàng (Sales Order)
- [ ] Thanh toán (Payment)
- [ ] Giao hàng (Delivery)

> **Giải thích:** Quy trình: Báo giá → Đơn hàng → Giao hàng → Hóa đơn → Thanh toán. Khi khách đồng ý báo giá, chuyển thành đơn hàng chính thức.

## Câu 2

[TYPE: MULTIPLE_CHOICE]

Trạng thái nào KHÔNG hợp lệ để chuyển từ DRAFT?

- [ ] CONFIRMED
- [ ] CANCELLED
- [x] DELIVERED
- [ ] Cả CONFIRMED và CANCELLED đều hợp lệ

> **Giải thích:** Từ DRAFT chỉ có thể chuyển sang CONFIRMED (xác nhận) hoặc CANCELLED (hủy). Không thể nhảy sang DELIVERED vì phải qua các bước trung gian.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Công nợ khách hàng được tính bằng công thức nào?

- [ ] Tổng đơn hàng - Tổng thanh toán
- [x] Tổng hóa đơn - Tổng thanh toán
- [ ] Tổng báo giá - Tổng thanh toán
- [ ] Tổng đơn hàng - Tổng hóa đơn

> **Giải thích:** Công nợ = Tổng tiền trên hóa đơn (Invoice) - Tổng tiền đã thanh toán (Payment). Hóa đơn là chứng từ pháp lý ghi nhận nợ, không phải đơn hàng hay báo giá.

## Câu 4

[TYPE: MULTIPLE_CHOICE]

Khi tạo hóa đơn VAT, thuế VAT 10% được tính trên:

- [x] Tổng tiền hàng (trước thuế)
- [ ] Tổng tiền hàng (sau thuế)
- [ ] Giá từng sản phẩm riêng lẻ
- [ ] Số lượng hàng

> **Giải thích:** VAT = Tổng tiền hàng × 10%. Nếu tiền hàng = 500.000.000₫ thì VAT = 50.000.000₫, tổng hóa đơn = 550.000.000₫.

## Câu 5

[TYPE: TRUE_FALSE]

Mệnh đề: "Một hóa đơn chỉ có thể thanh toán một lần duy nhất."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Hóa đơn có thể thanh toán nhiều lần (partial payment). VD: HĐ 500 triệu → trả lần 1: 300 triệu (PARTIALLY_PAID) → trả lần 2: 200 triệu (PAID).

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Trong Spring Boot, annotation nào dùng để validate field không được để trống?

- [ ] @Required
- [x] @NotBlank
- [ ] @NonNull
- [ ] @Mandatory

> **Giải thích:** `@NotBlank` kiểm tra string không null, không rỗng, và không toàn khoảng trắng. `@NotNull` chỉ kiểm tra không null. `@NotEmpty` kiểm tra không null và không rỗng nhưng cho phép khoảng trắng.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

Pattern nào phù hợp để convert Entity sang DTO trong Spring Boot?

- [ ] Gọi constructor trực tiếp trong Controller
- [ ] Sử dụng reflection
- [x] Tạo mapper method trong Service hoặc sử dụng MapStruct
- [ ] Trả Entity trực tiếp, không cần DTO

> **Giải thích:** Best practice là dùng mapper (thủ công hoặc MapStruct) trong Service layer. Controller không nên biết về Entity. Trả Entity trực tiếp có nguy cơ lộ data nhạy cảm và gây LazyInitializationException.

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Trong React, cách nào đúng để thêm item vào mảng trong state?

- [ ] `state.items.push(newItem)`
- [x] `setItems([...items, newItem])`
- [ ] `items.push(newItem); setItems(items)`
- [ ] `setItems(items.push(newItem))`

> **Giải thích:** React state phải immutable. `setItems([...items, newItem])` tạo mảng mới. `push()` thay đổi mảng cũ (mutate), React không detect được thay đổi → không re-render.

## Câu 9

[TYPE: MULTIPLE_CHOICE]

Khi nào đơn hàng được kiểm tra tồn kho?

- [ ] Khi tạo đơn hàng (DRAFT)
- [ ] Khi xác nhận đơn (CONFIRMED)
- [x] Khi bắt đầu giao hàng (DELIVERING)
- [ ] Khi tạo hóa đơn (INVOICED)

> **Giải thích:** Kiểm tra tồn kho khi chuyển sang DELIVERING vì lúc đó mới thực sự xuất hàng. Kiểm tra quá sớm (DRAFT) có thể sai vì tồn kho thay đổi liên tục.

## Câu 10

[TYPE: MULTIPLE_CHOICE]

Mã báo giá QT-2024-0015 nghĩa là gì?

- [ ] Báo giá ngày 15 năm 2024
- [ ] Báo giá của khách hàng số 15
- [x] Báo giá thứ 15 trong năm 2024
- [ ] Báo giá tháng 1 năm 2024, lần 5

> **Giải thích:** Format: QT-{năm}-{số thứ tự}. QT-2024-0015 = Quotation năm 2024, số thứ tự 15. Mã được tự sinh tăng dần.
