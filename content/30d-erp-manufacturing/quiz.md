# Quiz - Module Sản Xuất (Manufacturing)

## Câu 1

[TYPE: MULTIPLE_CHOICE]

BOM (Bill of Materials) dùng để làm gì?

- [ ] Liệt kê nhân viên tham gia sản xuất
- [x] Liệt kê nguyên vật liệu cần thiết để sản xuất 1 đơn vị thành phẩm
- [ ] Báo cáo kết quả sản xuất
- [ ] Lập kế hoạch bán hàng

> **Giải thích:** BOM (Bill of Materials) = Công thức sản xuất. VD: 1 SIM = 1 chip + 1 phôi + 1 bao bì. BOM giúp tính NVL cần cho SX và giá vốn sản phẩm.

## Câu 2

[TYPE: MULTIPLE_CHOICE]

Khi bắt đầu sản xuất (Start), hệ thống tự động thực hiện gì?

- [ ] Tạo đơn đặt hàng mua NVL
- [x] Xuất nguyên vật liệu từ kho theo BOM
- [ ] Nhập thành phẩm vào kho
- [ ] Gửi hóa đơn cho khách hàng

> **Giải thích:** Start SX → xuất NVL từ kho (decreaseStock) theo BOM × SL sản xuất. Nhập thành phẩm xảy ra khi Complete. Mua NVL thuộc module Procurement.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

BOM: 1 SIM cần 1 chip (30.000₫), 1 phôi (5.000₫), 1 bao bì (2.000₫). Giá vốn SX 1.000 SIM là bao nhiêu?

- [ ] 37.000₫
- [ ] 30.000.000₫
- [x] 37.000.000₫
- [ ] 370.000.000₫

> **Giải thích:** Giá vốn 1 SIM = 30.000 + 5.000 + 2.000 = 37.000₫. Giá vốn 1.000 SIM = 37.000 × 1.000 = 37.000.000₫.

## Câu 4

[TYPE: TRUE_FALSE]

Mệnh đề: "Số lượng thành phẩm thực tế (actualQuantity) luôn bằng số lượng kế hoạch (plannedQuantity)."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Trong thực tế, actualQuantity có thể < plannedQuantity do phế phẩm, hư hỏng trong quá trình SX. Hiệu suất = actual/planned × 100%.

## Câu 5

[TYPE: MULTIPLE_CHOICE]

Trình tự trạng thái đúng của Work Order là:

- [ ] DRAFT → IN_PROGRESS → CONFIRMED → COMPLETED
- [x] DRAFT → CONFIRMED → IN_PROGRESS → COMPLETED
- [ ] CONFIRMED → DRAFT → IN_PROGRESS → COMPLETED
- [ ] IN_PROGRESS → CONFIRMED → COMPLETED → DRAFT

> **Giải thích:** DRAFT (tạo mới) → CONFIRMED (kiểm tra NVL đủ) → IN_PROGRESS (đang SX, đã xuất NVL) → COMPLETED (xong, nhập TP).

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Nếu cần SX 100.000 SIM (BOM: 1 chip/SIM) mà kho chỉ có 80.000 chip, cần làm gì?

- [ ] Sản xuất 80.000 SIM và bỏ 20.000
- [ ] Sản xuất 100.000 SIM dù thiếu chip
- [x] Tạo yêu cầu mua hàng bổ sung 20.000 chip trước khi SX
- [ ] Chuyển chip từ kho khác mà không cần kiểm tra

> **Giải thích:** Module Manufacturing tích hợp với Procurement: khi kiểm tra NVL phát hiện thiếu → tạo YCMH bổ sung → sau khi nhập đủ NVL mới bắt đầu SX.
