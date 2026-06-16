# Quiz - Module Nhân Sự (HR)

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Lương thực nhận (Net Pay) được tính bằng công thức nào?

- [ ] Lương cơ bản - Bảo hiểm
- [ ] Lương cơ bản + Phụ cấp
- [x] Tổng thu nhập (Gross) - Tổng khấu trừ (Bảo hiểm + Thuế)
- [ ] Lương cơ bản - Thuế TNCN

> **Giải thích:** Net Pay = Gross Pay - Total Deductions. Gross = lương thực tế + phụ cấp + OT. Khấu trừ = BHXH + BHYT + BHTN + Thuế TNCN.

## Câu 2

[TYPE: MULTIPLE_CHOICE]

BHXH (Bảo hiểm xã hội) được tính trên:

- [x] Lương cơ bản
- [ ] Tổng thu nhập (Gross Pay)
- [ ] Lương thực nhận (Net Pay)
- [ ] Lương cơ bản + Phụ cấp

> **Giải thích:** BHXH 8% tính trên lương cơ bản (base salary), không tính OT hay phụ cấp. Tương tự BHYT 1.5% và BHTN 1% cũng trên lương cơ bản.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Khi nhân viên làm thêm giờ ngày thường, lương OT được tính theo hệ số nào?

- [ ] 100% (1.0)
- [x] 150% (1.5)
- [ ] 200% (2.0)
- [ ] 300% (3.0)

> **Giải thích:** Theo luật lao động VN: OT ngày thường = 150%, OT cuối tuần = 200%, OT ngày lễ = 300%.

## Câu 4

[TYPE: TRUE_FALSE]

Mệnh đề: "Một nhân viên có thể check-in nhiều lần trong cùng một ngày."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Hệ thống validate: mỗi nhân viên chỉ có 1 record attendance/ngày (UNIQUE constraint). Nếu đã check-in → throw BusinessException.

## Câu 5

[TYPE: MULTIPLE_CHOICE]

KPI (Key Performance Indicator) dùng để đánh giá điều gì?

- [ ] Mức lương nhân viên
- [ ] Số ngày nghỉ phép
- [x] Hiệu suất và kết quả công việc
- [ ] Thâm niên làm việc

> **Giải thích:** KPI = Chỉ số đánh giá hiệu suất công việc. VD: Doanh số bán hàng, số task hoàn thành, tỷ lệ lỗi... Achievement = actual/target × 100%.

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Giảm trừ gia cảnh bản thân (2024) là bao nhiêu khi tính thuế TNCN?

- [ ] 4.400.000₫/tháng
- [x] 11.000.000₫/tháng
- [ ] 15.000.000₫/tháng
- [ ] 20.000.000₫/tháng

> **Giải thích:** Giảm trừ bản thân = 11 triệu/tháng. Giảm trừ mỗi người phụ thuộc = 4.4 triệu/tháng. Thu nhập chịu thuế = Thu nhập - BH - Giảm trừ.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

Trong React, để gọi 2 API song song, cách nào hiệu quả nhất?

- [ ] Gọi API 1, đợi xong rồi gọi API 2
- [x] `Promise.all([api.get('/a'), api.get('/b')])`
- [ ] Gọi cả 2 mà không await
- [ ] Sử dụng setInterval

> **Giải thích:** `Promise.all` gọi nhiều API đồng thời (parallel) và đợi tất cả hoàn thành. Nhanh hơn gọi tuần tự (sequential) vì không cần đợi API 1 xong mới gọi API 2.

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Ngày công chuẩn (standard days) thường là bao nhiêu ngày/tháng?

- [ ] 20 ngày
- [x] 22 ngày
- [ ] 26 ngày
- [ ] 30 ngày

> **Giải thích:** Ngày công chuẩn = 22 ngày/tháng (thứ 2 → thứ 6, trung bình 4.4 tuần). Lương ngày = Lương tháng / 22. Lương giờ = Lương ngày / 8.
