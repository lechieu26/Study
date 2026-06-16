# Quiz - Module Mua Hàng (Procurement)

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Trong quy trình mua hàng, bước đầu tiên là gì?

- [ ] Tạo đơn đặt hàng (PO)
- [x] Tạo yêu cầu mua hàng (Purchase Request)
- [ ] Nhập hàng vào kho
- [ ] Thanh toán cho NCC

> **Giải thích:** Quy trình: Yêu cầu mua → Duyệt → Tạo PO → NCC xác nhận → Nhận hàng → Thanh toán. Bước đầu tiên là phòng ban tạo yêu cầu mua hàng.

## Câu 2

[TYPE: MULTIPLE_CHOICE]

Purchase Order khác Sales Order ở điểm nào?

- [ ] PO quản lý hàng ra, SO quản lý hàng vào
- [x] PO là đặt hàng từ NCC (hàng vào), SO là bán cho khách (hàng ra)
- [ ] PO dùng cho nội bộ, SO dùng cho bên ngoài
- [ ] Không có sự khác biệt

> **Giải thích:** PO (Purchase Order) đặt hàng từ nhà cung cấp → hàng VÀO kho, tiền RA. SO (Sales Order) bán hàng cho khách → hàng RA kho, tiền VÀO.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Khi nhập hàng, nếu NCC giao thiếu 10% thì trạng thái PO sẽ là gì?

- [ ] RECEIVED
- [x] PARTIALLY_RECEIVED
- [ ] CANCELLED
- [ ] COMPLETED

> **Giải thích:** Khi SL thực nhận < SL đặt → PO chuyển sang PARTIALLY_RECEIVED. Hệ thống cho phép nhập hàng nhiều lần cho đến khi nhận đủ.

## Câu 4

[TYPE: TRUE_FALSE]

Mệnh đề: "Có thể tạo PO trực tiếp mà không cần Yêu cầu mua hàng."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Trong thực tế, PO có thể tạo trực tiếp (không qua YCMH) hoặc tạo từ YCMH đã duyệt. YCMH là bước tùy chọn để kiểm soát tốt hơn.

## Câu 5

[TYPE: MULTIPLE_CHOICE]

`costPrice` trong bảng Products dùng để làm gì?

- [ ] Giá bán cho khách hàng
- [x] Giá gốc mua từ nhà cung cấp
- [ ] Giá sau khi giảm giá
- [ ] Phí vận chuyển

> **Giải thích:** `costPrice` (giá vốn) là giá mua vào từ NCC. `price` là giá bán ra cho khách. Lợi nhuận = price - costPrice.

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Khi nhập hàng thành công, bước nào xảy ra tự động?

- [ ] Gửi email cho NCC
- [ ] Tạo hóa đơn thanh toán
- [x] Tăng tồn kho trong module Inventory
- [ ] Tạo yêu cầu mua hàng mới

> **Giải thích:** Nhập hàng liên kết trực tiếp với module Kho. Khi xác nhận nhập, `inventoryService.increaseStock()` được gọi để tăng tồn kho tương ứng.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

Trong Spring Data JPA, method nào dùng để tìm kiếm theo nhiều field?

- [ ] `findByField()`
- [x] `findByCodeContainingOrNameContaining(search, search, pageable)`
- [ ] `searchAll(search)`
- [ ] `query(search)`

> **Giải thích:** Spring Data JPA hỗ trợ query derivation từ method name. `findByCodeContainingOrNameContaining` = WHERE code LIKE %search% OR name LIKE %search%.

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Net 30 trong thanh toán NCC có nghĩa là gì?

- [ ] Thanh toán 30% giá trị
- [ ] Thanh toán trong 30 phút
- [x] Thanh toán trong vòng 30 ngày sau khi nhận hàng
- [ ] Thanh toán tối thiểu 30 triệu

> **Giải thích:** Net 30 là điều khoản thanh toán: phải trả tiền trong vòng 30 ngày kể từ ngày nhận hóa đơn/hàng hóa. Tương tự: Net 60 = 60 ngày.
