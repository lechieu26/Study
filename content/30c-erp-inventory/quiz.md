# Quiz - Module Kho (Inventory)

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Bảng `stock` có constraint UNIQUE trên cặp (warehouse_id, product_id) nhằm mục đích gì?

- [ ] Tăng tốc truy vấn
- [x] Đảm bảo mỗi sản phẩm chỉ có 1 record tồn kho tại mỗi kho
- [ ] Ngăn chặn xóa dữ liệu
- [ ] Tự động tạo index

> **Giải thích:** UNIQUE(warehouse_id, product_id) đảm bảo mỗi cặp kho-sản phẩm chỉ có 1 record. Khi nhập/xuất, ta UPDATE quantity thay vì INSERT record mới.

## Câu 2

[TYPE: MULTIPLE_CHOICE]

Khi chuyển kho, điều gì xảy ra nếu tồn kho nguồn không đủ?

- [ ] Hệ thống tự động tạo yêu cầu mua hàng
- [ ] Tồn kho chuyển sang âm
- [x] Throw exception, không cho phép chuyển
- [ ] Chuyển số lượng tồn hiện có

> **Giải thích:** Hệ thống validate trước khi chuyển: nếu stock < quantity yêu cầu → throw BusinessException. Không cho phép tồn kho âm.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Stock Transaction loại ADJUST được tạo từ nghiệp vụ nào?

- [ ] Mua hàng
- [ ] Bán hàng
- [x] Kiểm kê
- [ ] Chuyển kho

> **Giải thích:** ADJUST = điều chỉnh tồn kho, phát sinh từ kiểm kê khi SL thực tế ≠ SL hệ thống. IN = nhập (mua hàng), OUT = xuất (bán hàng), TRANSFER = chuyển kho.

## Câu 4

[TYPE: TRUE_FALSE]

Mệnh đề: "Khi kiểm kê phát hiện SL thực tế > SL hệ thống, cần tạo phiếu xuất kho để điều chỉnh."

- [ ] Đúng
- [x] Sai

> **Giải thích:** SL thực > SL hệ thống → hệ thống cần TĂNG (nhập bổ sung) để khớp. Ngược lại, SL thực < SL hệ thống mới cần giảm. Kiểm kê tự xử lý +/- dựa trên difference.

## Câu 5

[TYPE: MULTIPLE_CHOICE]

"Running balance" trong thẻ kho (Stock Card) là gì?

- [ ] Số dư tài khoản ngân hàng
- [ ] Tổng số lượng nhập trong kỳ
- [x] Số lượng tồn kho sau mỗi giao dịch (cumulative sum)
- [ ] Chênh lệch giữa nhập và xuất

> **Giải thích:** Running balance = SL tồn kho tại từng thời điểm, tính bằng tổng tích lũy (cumulative sum) của tất cả giao dịch từ đầu. Giúp theo dõi biến động tồn kho theo thời gian.

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Trong Spring Boot, annotation `@Transactional` trên method `confirmTransfer` đảm bảo điều gì?

- [ ] Method chạy nhanh hơn
- [ ] Method chỉ đọc dữ liệu
- [x] Tất cả thao tác DB hoặc thành công hết, hoặc rollback hết
- [ ] Method thread-safe

> **Giải thích:** `@Transactional` đảm bảo atomicity: giảm kho A + tăng kho B phải thành công cả hai. Nếu bước nào fail → rollback toàn bộ, không có tình trạng giảm A mà không tăng B.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

Sản phẩm có `min_stock = 5000`, `quantity = 3000`. Trạng thái tồn kho là gì?

- [ ] Đủ hàng (OK)
- [x] Sắp hết (LOW)
- [ ] Hết hàng (OUT_OF_STOCK)
- [ ] Không xác định

> **Giải thích:** quantity (3000) > 0 nhưng ≤ min_stock (5000) → trạng thái LOW (Sắp hết). Cần tạo yêu cầu mua hàng bổ sung.

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Tại sao chuyển kho cần thực hiện trong 1 transaction duy nhất?

- [ ] Để chạy nhanh hơn
- [ ] Để tiết kiệm bộ nhớ
- [x] Để đảm bảo tồn kho nhất quán (không xảy ra giảm A mà không tăng B)
- [ ] Vì Spring Boot bắt buộc

> **Giải thích:** Nếu giảm kho A thành công nhưng tăng kho B fail → hàng "mất" trong hệ thống. Transaction đảm bảo cả hai bước phải thành công, nếu fail thì rollback cả hai.
