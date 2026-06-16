# Module Nhân Sự - Bài Tập

## Bài 1: Quản lý Phòng ban & Nhân viên
**Độ khó: Trung bình**

Xây dựng CRUD phòng ban và nhân viên:

**Backend:**
1. Entity: `DepartmentEntity` (code, name, parentId), `EmployeeEntity` (đầy đủ thông tin)
2. API phòng ban: CRUD + tree structure (sơ đồ tổ chức)
3. API nhân viên: CRUD + phân trang + search + filter theo phòng ban
4. Mã NV tự sinh: NV-{thứ tự 3 chữ số}

**Frontend:**
5. Sơ đồ tổ chức (tree view) cho phòng ban
6. Danh sách nhân viên: filter phòng ban, search tên/mã
7. Form nhân viên: đầy đủ thông tin cá nhân + hợp đồng
8. Profile nhân viên: hiển thị chi tiết

**Đầu vào:** Thông tin phòng ban, nhân viên
**Đầu ra:** CRUD hoạt động, sơ đồ tổ chức hiển thị đúng

---

## Bài 2: Hệ thống Chấm công
**Độ khó: Trung bình**

Xây dựng chức năng chấm công:

**Backend:**
1. Entity: `AttendanceEntity` (employeeId, date, checkIn, checkOut, workHours, otHours, status)
2. API check-in: ghi nhận giờ vào (không cho check-in 2 lần/ngày)
3. API check-out: ghi nhận giờ ra, tự tính workHours và otHours (>8h)
4. API bảng công tháng: danh sách chấm công theo NV + tháng
5. API tổng hợp: ngày công, ngày phép, giờ OT

**Frontend:**
6. Nút Check-in / Check-out (hiển thị giờ hiện tại)
7. Bảng công tháng: lịch hiển thị trạng thái từng ngày (✓, P, S, OT)
8. Tổng kết: ngày làm, ngày phép, giờ OT
9. Dropdown chọn NV + tháng để xem

**Đầu vào:** Check-in/out
**Đầu ra:** Bảng chấm công, tổng hợp ngày công

---

## Bài 3: Tính lương tháng
**Độ khó: Khó**

Xây dựng chức năng tính lương:

**Backend:**
1. Entity: `PayslipEntity` đầy đủ các khoản
2. API tính lương 1 NV: nhận employeeId + month + year → tính đầy đủ
3. API tính lương toàn bộ NV: batch calculate cho cả công ty
4. Công thức: Lương thực tế = (baseSalary / 22) × workDays
5. OT = hourlyRate × 1.5 × otHours
6. BHXH 8%, BHYT 1.5%, BHTN 1% trên lương cơ bản
7. Thuế TNCN theo biểu lũy tiến (đơn giản hóa)
8. Status: DRAFT → CONFIRMED → PAID

**Frontend:**
9. Bảng lương tháng: danh sách NV + lương gộp + khấu trừ + thực nhận
10. Phiếu lương cá nhân (payslip): hiển thị chi tiết
11. Nút "Tính lương" → calculate batch
12. Nút "Duyệt" → CONFIRMED, "Thanh toán" → PAID
13. Tổng chi phí lương tháng (gửi sang module Kế toán)

**Đầu vào:** Tháng/năm tính lương
**Đầu ra:** Bảng lương, phiếu lương chi tiết, tổng chi phí

---

## Bài 4: Đánh giá KPI
**Độ khó: Trung bình**

Xây dựng hệ thống đánh giá KPI:

**Backend:**
1. Entity: `KpiEvaluationEntity` (employeeId, period, kpiName, target, actual, score)
2. API CRUD KPI: tạo, cập nhật, xem theo NV + kỳ
3. Tính achievement rate: actual / target × 100%
4. Tính điểm trung bình: avg(score) cho 1 NV trong 1 kỳ
5. Flow: DRAFT → SUBMITTED (NV nộp) → APPROVED (Manager duyệt)

**Frontend:**
6. Form đánh giá: danh sách KPI (dynamic rows), nhập target + actual
7. Bảng KPI cá nhân: KPI | Mục tiêu | Thực tế | % Đạt | Điểm
8. Dashboard KPI phòng ban: biểu đồ Radar so sánh NV
9. Progress bar cho từng KPI

**Đầu vào:** Danh sách KPI, mục tiêu, thực tế
**Đầu ra:** Bảng đánh giá KPI, điểm trung bình, biểu đồ

---

## Bài 5: Dashboard Nhân sự
**Độ khó: Trung bình**

Xây dựng trang tổng quan HR:

**Backend:**
1. API thống kê: tổng NV, NV mới tháng, NV nghỉ việc
2. API phân bổ: NV theo phòng ban, theo loại HĐ
3. API chấm công hôm nay: ai đã check-in, ai chưa

**Frontend:**
4. Card: Tổng NV, NV mới, Nghỉ việc, Đang chấm công hôm nay
5. Biểu đồ Pie: phân bổ NV theo phòng ban
6. Biểu đồ Bar: NV mới vs nghỉ việc theo tháng
7. Danh sách NV chưa check-in hôm nay (cảnh báo)

**Đầu vào:** Không
**Đầu ra:** Dashboard HR trực quan
