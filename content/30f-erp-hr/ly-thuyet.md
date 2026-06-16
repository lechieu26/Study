# Module Nhân Sự (HR - Human Resources)

## Mục lục

1. [Tổng quan quản lý nhân sự](#1-tổng-quan-quản-lý-nhân-sự)
2. [Hồ sơ nhân viên](#2-hồ-sơ-nhân-viên)
3. [Chấm công](#3-chấm-công)
4. [Tính lương](#4-tính-lương)
5. [Đánh giá KPI](#5-đánh-giá-kpi)
6. [Thiết kế Database](#6-thiết-kế-database)
7. [Backend - API nhân sự](#7-backend---api-nhân-sự)
8. [Frontend - Giao diện nhân sự](#8-frontend---giao-diện-nhân-sự)

---

> **📥 Tải file SQL:** [hr-database.sql](/api/download/erp-hr/hr-database.sql) — Cấu trúc bảng và dữ liệu mẫu cho module Nhân sự (departments, employees, attendance, payslips, kpi_evaluations). Yêu cầu chạy file `erp-database.sql` từ module Tổng Quan trước.

---

## 1. Tổng quan quản lý nhân sự

### 1.1 HR module trong ERP

Module HR quản lý toàn bộ thông tin nhân sự: từ hồ sơ nhân viên, chấm công, tính lương đến đánh giá hiệu suất. Module này kết nối với Kế toán để ghi nhận chi phí lương.

### 1.2 Các nghiệp vụ chính

| Nghiệp vụ | Mô tả |
|---|---|
| **Hồ sơ nhân viên** | Thông tin cá nhân, hợp đồng, phòng ban, chức vụ |
| **Chấm công** | Giờ đến, giờ về, nghỉ phép, OT (làm thêm) |
| **Tính lương** | Lương cơ bản + phụ cấp + OT - khấu trừ = thực nhận |
| **Đánh giá KPI** | Chỉ tiêu hiệu suất, đánh giá định kỳ |

### 1.3 Ví dụ tính lương

```
Nhân viên: Nguyễn Văn A
Phòng ban: Kỹ thuật
Chức vụ: Senior Developer

Lương tháng 06/2024:
  Lương cơ bản:        20.000.000₫
  Phụ cấp ăn trưa:     1.000.000₫
  Phụ cấp xăng xe:       500.000₫
  OT (20 giờ):         2.500.000₫  (125.000₫/giờ × 150% × 20h ÷ 1.5)
  ───────────────────────────────
  Tổng thu nhập:       24.000.000₫

  BHXH (8%):           -1.600.000₫
  BHYT (1.5%):           -300.000₫
  BHTN (1%):             -200.000₫
  Thuế TNCN:           -1.150.000₫
  ───────────────────────────────
  Thực nhận:           20.750.000₫
```

---

## 2. Hồ sơ nhân viên

### 2.1 Thông tin nhân viên

```
Mã NV:        NV-001
Họ tên:       Nguyễn Văn A
Ngày sinh:    15/03/1995
CCCD:         012345678901
SĐT:          0901234567
Email:        nva@company.com
Địa chỉ:     123 Nguyễn Huệ, Q.1, TP.HCM

Phòng ban:    Kỹ thuật (IT)
Chức vụ:      Senior Developer
Ngày vào:     01/01/2022
Loại HĐ:     Không xác định thời hạn
Lương CB:     20.000.000₫
Tài khoản NH: 1234567890 - Vietcombank
```

### 2.2 Phòng ban & Tổ chức

```
Công ty ABC
├── Ban Giám đốc
├── Phòng Kỹ thuật (IT)
│   ├── Team Backend
│   └── Team Frontend
├── Phòng Kinh doanh (Sales)
├── Phòng Kế toán
├── Phòng Nhân sự (HR)
└── Phòng Kho vận
```

---

## 3. Chấm công

### 3.1 Loại ngày công

| Loại | Mã | Hệ số |
|---|---|---|
| Ngày thường | WORK | 1.0 |
| Nghỉ phép (có lương) | LEAVE_PAID | 1.0 |
| Nghỉ không lương | LEAVE_UNPAID | 0.0 |
| Nghỉ ốm | SICK_LEAVE | 0.75 |
| Làm thêm ngày thường | OT_NORMAL | 1.5 |
| Làm thêm cuối tuần | OT_WEEKEND | 2.0 |
| Làm thêm lễ | OT_HOLIDAY | 3.0 |

### 3.2 Bảng chấm công tháng

```
NV-001 | Nguyễn Văn A | Tháng 06/2024

Ngày | T2 | T3 | T4 | T5 | T6 | T7 | CN |
W1   | ✓  | ✓  | ✓  | ✓  | ✓  | -  | -  |
W2   | ✓  | ✓  | P  | ✓  | ✓  | OT | -  |
W3   | ✓  | ✓  | ✓  | ✓  | ✓  | -  | -  |
W4   | ✓  | ✓  | ✓  | S  | ✓  | -  | -  |

✓: Đi làm | P: Phép | S: Ốm | OT: Làm thêm | -: Nghỉ

Tổng kết: 20 ngày làm, 1 ngày phép, 1 ngày ốm, 1 ngày OT
```

### 3.3 API chấm công

```java
@Service
@RequiredArgsConstructor
public class AttendanceService {

    public void checkIn(Long employeeId) {
        AttendanceEntity att = AttendanceEntity.builder()
            .employeeId(employeeId)
            .date(LocalDate.now())
            .checkIn(LocalTime.now())
            .status("PRESENT")
            .build();
        attendanceRepo.save(att);
    }

    public void checkOut(Long employeeId) {
        AttendanceEntity att = attendanceRepo
            .findByEmployeeIdAndDate(employeeId, LocalDate.now())
            .orElseThrow();
        att.setCheckOut(LocalTime.now());

        // Tính giờ làm
        Duration duration = Duration.between(att.getCheckIn(), att.getCheckOut());
        att.setWorkHours(duration.toHours());

        // OT nếu > 8 giờ
        if (duration.toHours() > 8) {
            att.setOtHours(duration.toHours() - 8);
        }
        attendanceRepo.save(att);
    }
}
```

---

## 4. Tính lương

### 4.1 Công thức tính lương

```
Tổng thu nhập = Lương cơ bản
              + Phụ cấp (ăn trưa, xăng xe, điện thoại...)
              + OT (giờ OT × lương giờ × hệ số OT)
              + Thưởng

Khấu trừ = BHXH (8%) + BHYT (1.5%) + BHTN (1%)
         + Thuế TNCN
         + Khấu trừ khác (tạm ứng, phạt...)

Thực nhận = Tổng thu nhập - Khấu trừ
```

### 4.2 Tính thuế TNCN (đơn giản hóa)

```
Thu nhập chịu thuế = Tổng thu nhập - BHXH - Giảm trừ bản thân (11tr)
                   - Giảm trừ người phụ thuộc (4.4tr/người)

Biểu thuế lũy tiến:
| Bậc | Thu nhập chịu thuế    | Thuế suất |
|-----|-----------------------|-----------|
| 1   | Đến 5 triệu          | 5%        |
| 2   | 5 - 10 triệu         | 10%       |
| 3   | 10 - 18 triệu        | 15%       |
| 4   | 18 - 32 triệu        | 20%       |
| 5   | 32 - 52 triệu        | 25%       |
```

### 4.3 Service tính lương

```java
@Service
@RequiredArgsConstructor
public class PayrollService {

    public PayslipDTO calculatePayslip(Long employeeId, int month, int year) {
        EmployeeEntity emp = employeeRepo.findById(employeeId).orElseThrow();

        // 1. Tính ngày công
        AttendanceSummary summary = attendanceService.getSummary(employeeId, month, year);
        int workDays = summary.getWorkDays();
        int standardDays = 22; // Ngày công chuẩn
        double otHours = summary.getTotalOtHours();

        // 2. Tính lương
        BigDecimal baseSalary = emp.getBaseSalary();
        BigDecimal dailyRate = baseSalary.divide(BigDecimal.valueOf(standardDays), 2, RoundingMode.HALF_UP);
        BigDecimal hourlyRate = dailyRate.divide(BigDecimal.valueOf(8), 2, RoundingMode.HALF_UP);

        BigDecimal actualSalary = dailyRate.multiply(BigDecimal.valueOf(workDays));
        BigDecimal allowance = emp.getAllowance();
        BigDecimal otPay = hourlyRate.multiply(BigDecimal.valueOf(1.5))
            .multiply(BigDecimal.valueOf(otHours));

        BigDecimal grossPay = actualSalary.add(allowance).add(otPay);

        // 3. Khấu trừ
        BigDecimal socialInsurance = baseSalary.multiply(new BigDecimal("0.08"));
        BigDecimal healthInsurance = baseSalary.multiply(new BigDecimal("0.015"));
        BigDecimal unemploymentInsurance = baseSalary.multiply(new BigDecimal("0.01"));
        BigDecimal totalInsurance = socialInsurance.add(healthInsurance).add(unemploymentInsurance);

        BigDecimal tax = calculateTax(grossPay, totalInsurance, emp.getDependents());

        BigDecimal totalDeduction = totalInsurance.add(tax);
        BigDecimal netPay = grossPay.subtract(totalDeduction);

        return PayslipDTO.builder()
            .employeeId(employeeId)
            .employeeName(emp.getFullName())
            .month(month).year(year)
            .workDays(workDays).otHours(otHours)
            .baseSalary(baseSalary)
            .actualSalary(actualSalary)
            .allowance(allowance)
            .otPay(otPay)
            .grossPay(grossPay)
            .socialInsurance(socialInsurance)
            .healthInsurance(healthInsurance)
            .unemploymentInsurance(unemploymentInsurance)
            .tax(tax)
            .totalDeduction(totalDeduction)
            .netPay(netPay)
            .build();
    }
}
```

---

## 5. Đánh giá KPI

### 5.1 KPI là gì?

**KPI (Key Performance Indicator)** - Chỉ số đánh giá hiệu suất công việc. Mỗi nhân viên có các KPI riêng theo phòng ban và chức vụ.

### 5.2 Ví dụ KPI

**Nhân viên Sales:**
| KPI | Mục tiêu | Thực tế | Đạt % |
|---|---|---|---|
| Doanh số bán hàng | 500.000.000₫ | 450.000.000₫ | 90% |
| Số khách hàng mới | 10 KH | 12 KH | 120% |
| Tỷ lệ chuyển đổi | 30% | 25% | 83% |

**Nhân viên IT:**
| KPI | Mục tiêu | Thực tế | Đạt % |
|---|---|---|---|
| Số task hoàn thành | 20 tasks | 22 tasks | 110% |
| Bug fix time | < 4 giờ | 3.5 giờ | 114% |
| Code review pass rate | 90% | 95% | 106% |

### 5.3 Cấu trúc đánh giá

```java
@Entity
@Table(name = "kpi_evaluations")
public class KpiEvaluationEntity extends BaseEntity {
    private Long employeeId;
    private String period;           // "2024-Q2" hoặc "2024-06"
    private String kpiName;
    private String targetValue;
    private String actualValue;
    private BigDecimal achievementRate; // % đạt được
    private Integer score;             // Điểm (1-5)
    private String managerComment;
    private String status;             // DRAFT, SUBMITTED, APPROVED
}
```

---

## 6. Thiết kế Database

```sql
-- Phòng ban
CREATE TABLE departments (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    parent_id BIGINT REFERENCES departments(id),
    manager_id BIGINT
);

-- Nhân viên
CREATE TABLE employees (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,       -- NV-001
    full_name VARCHAR(100) NOT NULL,
    date_of_birth DATE,
    gender VARCHAR(10),
    id_number VARCHAR(20),                   -- CCCD
    phone VARCHAR(20),
    email VARCHAR(100),
    address TEXT,
    department_id BIGINT REFERENCES departments(id),
    position VARCHAR(50),
    hire_date DATE,
    contract_type VARCHAR(20),               -- PERMANENT, FIXED_TERM, PROBATION
    base_salary DECIMAL(15,2),
    allowance DECIMAL(15,2) DEFAULT 0,
    bank_account VARCHAR(30),
    bank_name VARCHAR(50),
    dependents INT DEFAULT 0,                -- Số người phụ thuộc
    status VARCHAR(20) DEFAULT 'ACTIVE',
    user_id BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Chấm công
CREATE TABLE attendance (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT REFERENCES employees(id),
    date DATE NOT NULL,
    check_in TIME,
    check_out TIME,
    work_hours DECIMAL(4,2) DEFAULT 0,
    ot_hours DECIMAL(4,2) DEFAULT 0,
    status VARCHAR(20) DEFAULT 'PRESENT',    -- PRESENT, LEAVE_PAID, LEAVE_UNPAID, SICK, OT
    note TEXT,
    UNIQUE(employee_id, date)
);

-- Bảng lương
CREATE TABLE payslips (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT REFERENCES employees(id),
    month INT NOT NULL,
    year INT NOT NULL,
    work_days INT,
    ot_hours DECIMAL(5,2),
    base_salary DECIMAL(15,2),
    actual_salary DECIMAL(15,2),
    allowance DECIMAL(15,2),
    ot_pay DECIMAL(15,2),
    gross_pay DECIMAL(15,2),
    social_insurance DECIMAL(15,2),
    health_insurance DECIMAL(15,2),
    unemployment_insurance DECIMAL(15,2),
    tax DECIMAL(15,2),
    total_deduction DECIMAL(15,2),
    net_pay DECIMAL(15,2),
    status VARCHAR(20) DEFAULT 'DRAFT',      -- DRAFT, CONFIRMED, PAID
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(employee_id, month, year)
);

-- KPI
CREATE TABLE kpi_evaluations (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT REFERENCES employees(id),
    period VARCHAR(20) NOT NULL,
    kpi_name VARCHAR(100) NOT NULL,
    target_value VARCHAR(50),
    actual_value VARCHAR(50),
    achievement_rate DECIMAL(5,2),
    score INT CHECK (score BETWEEN 1 AND 5),
    manager_comment TEXT,
    status VARCHAR(20) DEFAULT 'DRAFT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

## 7. Backend - API nhân sự

### 7.1 API Endpoints

| Method | URL | Mô tả |
|---|---|---|
| GET | `/api/hr/departments` | Danh sách phòng ban |
| GET | `/api/hr/employees` | Danh sách nhân viên |
| POST | `/api/hr/employees` | Thêm nhân viên |
| PUT | `/api/hr/employees/{id}` | Cập nhật NV |
| POST | `/api/hr/attendance/check-in` | Chấm công vào |
| POST | `/api/hr/attendance/check-out` | Chấm công ra |
| GET | `/api/hr/attendance/monthly/{employeeId}` | Bảng công tháng |
| POST | `/api/hr/payroll/calculate` | Tính lương tháng |
| GET | `/api/hr/payroll/{month}/{year}` | Bảng lương tháng |
| GET | `/api/hr/payslip/{employeeId}/{month}/{year}` | Phiếu lương cá nhân |
| GET | `/api/hr/kpi/{employeeId}` | KPI nhân viên |
| POST | `/api/hr/kpi` | Tạo/cập nhật KPI |

---

## 8. Frontend - Giao diện nhân sự

### 8.1 Cấu trúc trang

```
src/pages/HR/
├── EmployeeList.jsx         ← Danh sách NV
├── EmployeeForm.jsx         ← Form thông tin NV
├── EmployeeProfile.jsx      ← Hồ sơ chi tiết NV
├── DepartmentTree.jsx       ← Sơ đồ tổ chức
├── AttendanceBoard.jsx      ← Bảng chấm công
├── AttendanceCalendar.jsx   ← Lịch chấm công cá nhân
├── PayrollList.jsx          ← Bảng lương tháng
├── PayslipDetail.jsx        ← Phiếu lương cá nhân
├── KpiDashboard.jsx         ← Dashboard KPI
├── KpiEvaluationForm.jsx    ← Form đánh giá KPI
└── HRDashboard.jsx          ← Tổng quan nhân sự
```

### 8.2 Phiếu lương Component

```jsx
function PayslipDetail({ employeeId, month, year }) {
  const [payslip, setPayslip] = useState(null);

  useEffect(() => {
    api.get(`/hr/payslip/${employeeId}/${month}/${year}`)
      .then(r => setPayslip(r.data));
  }, [employeeId, month, year]);

  if (!payslip) return <div>Loading...</div>;

  return (
    <div className="payslip">
      <h2>📄 Phiếu lương tháng {month}/{year}</h2>
      <h3>{payslip.employeeName} ({payslip.employeeCode})</h3>

      <table className="payslip-table">
        <thead><tr><th>Khoản mục</th><th>Số tiền</th></tr></thead>
        <tbody>
          <tr className="section-header"><td colSpan="2">THU NHẬP</td></tr>
          <tr><td>Lương thực tế ({payslip.workDays} ngày)</td>
              <td>{payslip.actualSalary.toLocaleString()}₫</td></tr>
          <tr><td>Phụ cấp</td>
              <td>{payslip.allowance.toLocaleString()}₫</td></tr>
          <tr><td>Lương OT ({payslip.otHours}h)</td>
              <td>{payslip.otPay.toLocaleString()}₫</td></tr>
          <tr className="subtotal"><td><strong>Tổng thu nhập</strong></td>
              <td><strong>{payslip.grossPay.toLocaleString()}₫</strong></td></tr>

          <tr className="section-header"><td colSpan="2">KHẤU TRỪ</td></tr>
          <tr><td>BHXH (8%)</td>
              <td className="negative">-{payslip.socialInsurance.toLocaleString()}₫</td></tr>
          <tr><td>BHYT (1.5%)</td>
              <td className="negative">-{payslip.healthInsurance.toLocaleString()}₫</td></tr>
          <tr><td>BHTN (1%)</td>
              <td className="negative">-{payslip.unemploymentInsurance.toLocaleString()}₫</td></tr>
          <tr><td>Thuế TNCN</td>
              <td className="negative">-{payslip.tax.toLocaleString()}₫</td></tr>
          <tr className="subtotal"><td><strong>Tổng khấu trừ</strong></td>
              <td className="negative"><strong>-{payslip.totalDeduction.toLocaleString()}₫</strong></td></tr>

          <tr className="total"><td><strong>THỰC NHẬN</strong></td>
              <td className="positive"><strong>{payslip.netPay.toLocaleString()}₫</strong></td></tr>
        </tbody>
      </table>
    </div>
  );
}
```

---

> **Hoàn thành!** Bạn đã nắm được tổng quan tất cả 6 module của hệ thống ERP. Hãy bắt đầu xây dựng từ module **Tổng quan** → **Sales** → các module tiếp theo.
