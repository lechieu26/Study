-- =============================================
-- MODULE NHÂN SỰ (HR) - DATABASE STRUCTURE & SAMPLE DATA
-- Yêu cầu: Chạy file erp-database.sql (30-erp-system) trước
-- Database: PostgreSQL
-- =============================================

-- =============================================
-- 1. BẢNG DEPARTMENTS (Phòng ban)
-- =============================================
CREATE TABLE IF NOT EXISTS departments (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    parent_id BIGINT REFERENCES departments(id),
    manager_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- 2. BẢNG EMPLOYEES (Nhân viên)
-- =============================================
CREATE TABLE IF NOT EXISTS employees (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,          -- NV-001
    full_name VARCHAR(100) NOT NULL,
    date_of_birth DATE,
    gender VARCHAR(10),
    id_number VARCHAR(20),                      -- CCCD
    phone VARCHAR(20),
    email VARCHAR(100),
    address TEXT,
    department_id BIGINT REFERENCES departments(id),
    position VARCHAR(50),
    hire_date DATE,
    contract_type VARCHAR(20),                  -- PERMANENT, FIXED_TERM, PROBATION
    base_salary DECIMAL(15,2),
    allowance DECIMAL(15,2) DEFAULT 0,
    bank_account VARCHAR(30),
    bank_name VARCHAR(50),
    dependents INT DEFAULT 0,
    status VARCHAR(20) DEFAULT 'ACTIVE',       -- ACTIVE, INACTIVE, RESIGNED
    user_id BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- 3. BẢNG ATTENDANCE (Chấm công)
-- =============================================
CREATE TABLE IF NOT EXISTS attendance (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT REFERENCES employees(id),
    date DATE NOT NULL,
    check_in TIME,
    check_out TIME,
    work_hours DECIMAL(4,2) DEFAULT 0,
    ot_hours DECIMAL(4,2) DEFAULT 0,
    status VARCHAR(20) DEFAULT 'PRESENT',      -- PRESENT, LEAVE_PAID, LEAVE_UNPAID, SICK, OT
    note TEXT,
    UNIQUE(employee_id, date)
);

-- =============================================
-- 4. BẢNG PAYSLIPS (Bảng lương)
-- =============================================
CREATE TABLE IF NOT EXISTS payslips (
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
    status VARCHAR(20) DEFAULT 'DRAFT',        -- DRAFT, CONFIRMED, PAID
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(employee_id, month, year)
);

-- =============================================
-- 5. BẢNG KPI
-- =============================================
CREATE TABLE IF NOT EXISTS kpi_evaluations (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT REFERENCES employees(id),
    period VARCHAR(20) NOT NULL,               -- '2024-Q2' hoặc '2024-06'
    kpi_name VARCHAR(100) NOT NULL,
    target_value VARCHAR(50),
    actual_value VARCHAR(50),
    achievement_rate DECIMAL(5,2),
    score INT CHECK (score BETWEEN 1 AND 5),
    manager_comment TEXT,
    status VARCHAR(20) DEFAULT 'DRAFT',        -- DRAFT, SUBMITTED, APPROVED
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- SAMPLE DATA - DEPARTMENTS
-- =============================================
INSERT INTO departments (code, name, parent_id) VALUES
('BGD', 'Ban Giám đốc', NULL),
('IT', 'Phòng Kỹ thuật', NULL),
('SALES', 'Phòng Kinh doanh', NULL),
('HR', 'Phòng Nhân sự', NULL),
('ACCT', 'Phòng Kế toán', NULL),
('WH', 'Phòng Kho vận', NULL),
('IT-BE', 'Team Backend', 2),
('IT-FE', 'Team Frontend', 2);

-- =============================================
-- SAMPLE DATA - EMPLOYEES
-- =============================================
INSERT INTO employees (code, full_name, date_of_birth, gender, id_number, phone, email, address, department_id, position, hire_date, contract_type, base_salary, allowance, bank_account, bank_name, dependents, user_id) VALUES
('NV-001', 'Nguyễn Văn Minh', '1985-03-15', 'Nam', '012345678901', '0901111111', 'minh@erp.com', '100 Nguyễn Huệ, Q.1, TP.HCM', 1, 'Giám đốc', '2020-01-01', 'PERMANENT', 50000000, 5000000, '1234567890', 'Vietcombank', 2, 2),
('NV-002', 'Trần Thị Hoa', '1988-07-20', 'Nữ', '012345678902', '0902222222', 'hoa@erp.com', '200 Lê Lợi, Q.1, TP.HCM', 3, 'Trưởng phòng KD', '2020-06-01', 'PERMANENT', 35000000, 3000000, '2345678901', 'Techcombank', 1, 3),
('NV-003', 'Lê Hoàng Nam', '1995-11-10', 'Nam', '012345678903', '0903333333', 'nam@erp.com', '300 Trần Hưng Đạo, Q.5, TP.HCM', 7, 'Senior Developer', '2022-01-15', 'PERMANENT', 25000000, 2000000, '3456789012', 'Vietcombank', 0, 4),
('NV-004', 'Phạm Thị Lan', '1996-04-25', 'Nữ', '012345678904', '0904444444', 'lan@erp.com', '400 Võ Văn Tần, Q.3, TP.HCM', 8, 'Frontend Developer', '2022-06-01', 'PERMANENT', 20000000, 1500000, '4567890123', 'MB Bank', 0, 5),
('NV-005', 'Võ Minh Tuấn', '1993-08-05', 'Nam', '012345678905', '0905555555', 'tuan@erp.com', '500 Điện Biên Phủ, Q.3, TP.HCM', 6, 'Quản lý kho', '2021-03-01', 'PERMANENT', 18000000, 1500000, '5678901234', 'Agribank', 1, 6),
('NV-006', 'Hoàng Thị Mai', '1997-12-30', 'Nữ', '012345678906', '0906666666', 'mai@erp.com', '600 Hai Bà Trưng, Q.1, TP.HCM', 5, 'Kế toán viên', '2023-01-10', 'FIXED_TERM', 15000000, 1000000, '6789012345', 'BIDV', 0, NULL),
('NV-007', 'Đỗ Văn Hùng', '1999-02-14', 'Nam', '012345678907', '0907777777', 'hung@erp.com', '700 Nguyễn Trãi, Q.5, TP.HCM', 3, 'Nhân viên KD', '2023-06-01', 'FIXED_TERM', 12000000, 1000000, '7890123456', 'Sacombank', 0, NULL),
('NV-008', 'Lý Thị Ngọc', '2000-06-18', 'Nữ', '012345678908', '0908888888', 'ngoc@erp.com', '800 Phan Xích Long, Phú Nhuận, TP.HCM', 4, 'Nhân viên HR', '2024-01-15', 'PROBATION', 10000000, 500000, '8901234567', 'TPBank', 0, NULL);

-- =============================================
-- SAMPLE DATA - ATTENDANCE (Tháng 06/2024 - tuần 1)
-- =============================================
INSERT INTO attendance (employee_id, date, check_in, check_out, work_hours, ot_hours, status) VALUES
-- NV-003 (Lê Hoàng Nam) - Tuần 1 tháng 6
(3, '2024-06-03', '08:30', '17:30', 8.0, 0, 'PRESENT'),
(3, '2024-06-04', '08:15', '17:30', 8.25, 0.25, 'PRESENT'),
(3, '2024-06-05', '08:30', '17:30', 8.0, 0, 'PRESENT'),
(3, '2024-06-06', '08:30', '19:00', 9.5, 1.5, 'PRESENT'),
(3, '2024-06-07', '08:30', '17:30', 8.0, 0, 'PRESENT'),
-- Tuần 2
(3, '2024-06-10', '08:30', '17:30', 8.0, 0, 'PRESENT'),
(3, '2024-06-11', '08:30', '17:30', 8.0, 0, 'PRESENT'),
(3, '2024-06-12', NULL, NULL, 0, 0, 'LEAVE_PAID'),
(3, '2024-06-13', '08:30', '17:30', 8.0, 0, 'PRESENT'),
(3, '2024-06-14', '08:30', '17:30', 8.0, 0, 'PRESENT'),
(3, '2024-06-15', '09:00', '17:00', 8.0, 0, 'OT'),
-- Tuần 3
(3, '2024-06-17', '08:30', '17:30', 8.0, 0, 'PRESENT'),
(3, '2024-06-18', '08:30', '17:30', 8.0, 0, 'PRESENT'),
(3, '2024-06-19', '08:30', '20:00', 10.5, 2.5, 'PRESENT'),
(3, '2024-06-20', '08:30', '17:30', 8.0, 0, 'PRESENT'),
(3, '2024-06-21', '08:30', '17:30', 8.0, 0, 'PRESENT'),
-- Tuần 4
(3, '2024-06-24', '08:30', '17:30', 8.0, 0, 'PRESENT'),
(3, '2024-06-25', '08:30', '17:30', 8.0, 0, 'PRESENT'),
(3, '2024-06-26', '08:30', '17:30', 8.0, 0, 'PRESENT'),
(3, '2024-06-27', NULL, NULL, 0, 0, 'SICK'),
(3, '2024-06-28', '08:30', '17:30', 8.0, 0, 'PRESENT'),

-- NV-004 (Phạm Thị Lan) - Một số ngày mẫu
(4, '2024-06-03', '09:00', '18:00', 8.0, 0, 'PRESENT'),
(4, '2024-06-04', '08:45', '17:45', 8.0, 0, 'PRESENT'),
(4, '2024-06-05', '09:00', '18:30', 8.5, 0.5, 'PRESENT'),
(4, '2024-06-06', '09:00', '18:00', 8.0, 0, 'PRESENT'),
(4, '2024-06-07', '09:00', '18:00', 8.0, 0, 'PRESENT');

-- =============================================
-- SAMPLE DATA - PAYSLIPS (Tháng 06/2024)
-- =============================================
INSERT INTO payslips (employee_id, month, year, work_days, ot_hours, base_salary, actual_salary, allowance, ot_pay, gross_pay, social_insurance, health_insurance, unemployment_insurance, tax, total_deduction, net_pay, status) VALUES
(1, 6, 2024, 22, 0, 50000000, 50000000, 5000000, 0, 55000000, 4000000, 750000, 500000, 5950000, 11200000, 43800000, 'PAID'),
(2, 6, 2024, 22, 0, 35000000, 35000000, 3000000, 0, 38000000, 2800000, 525000, 350000, 2925000, 6600000, 31400000, 'PAID'),
(3, 6, 2024, 19, 4.25, 25000000, 21590909, 2000000, 604688, 24195597, 2000000, 375000, 250000, 1150000, 3775000, 20420597, 'PAID'),
(4, 6, 2024, 22, 0.5, 20000000, 20000000, 1500000, 56818, 21556818, 1600000, 300000, 200000, 445682, 2545682, 19011136, 'PAID'),
(5, 6, 2024, 22, 0, 18000000, 18000000, 1500000, 0, 19500000, 1440000, 270000, 180000, 0, 1890000, 17610000, 'PAID'),
(6, 6, 2024, 22, 0, 15000000, 15000000, 1000000, 0, 16000000, 1200000, 225000, 150000, 0, 1575000, 14425000, 'CONFIRMED'),
(7, 6, 2024, 22, 0, 12000000, 12000000, 1000000, 0, 13000000, 960000, 180000, 120000, 0, 1260000, 11740000, 'CONFIRMED'),
(8, 6, 2024, 22, 0, 10000000, 10000000, 500000, 0, 10500000, 800000, 150000, 100000, 0, 1050000, 9450000, 'DRAFT');

-- =============================================
-- SAMPLE DATA - KPI (Quý 2/2024)
-- =============================================
INSERT INTO kpi_evaluations (employee_id, period, kpi_name, target_value, actual_value, achievement_rate, score, manager_comment, status) VALUES
-- NV-002 (Trưởng phòng KD)
(2, '2024-Q2', 'Doanh số bán hàng', '2000000000', '1850000000', 92.5, 4, 'Gần đạt mục tiêu, cần đẩy mạnh Q3', 'APPROVED'),
(2, '2024-Q2', 'Số khách hàng mới', '15', '18', 120.0, 5, 'Vượt chỉ tiêu, làm tốt', 'APPROVED'),
(2, '2024-Q2', 'Tỷ lệ giữ chân KH', '90', '88', 97.8, 4, 'Cần cải thiện CSKH', 'APPROVED'),
-- NV-003 (Senior Dev)
(3, '2024-Q2', 'Số task hoàn thành', '60', '65', 108.3, 5, 'Năng suất tốt', 'APPROVED'),
(3, '2024-Q2', 'Bug fix time (giờ)', '4', '3.2', 125.0, 5, 'Fix bug nhanh', 'APPROVED'),
(3, '2024-Q2', 'Code review pass rate', '90', '95', 105.6, 5, 'Chất lượng code cao', 'APPROVED'),
-- NV-007 (NV Kinh doanh)
(7, '2024-Q2', 'Doanh số cá nhân', '500000000', '420000000', 84.0, 3, 'Cần cố gắng hơn', 'APPROVED'),
(7, '2024-Q2', 'Số cuộc gọi/tuần', '50', '45', 90.0, 4, 'Khá ổn', 'APPROVED');

-- =============================================
-- CÂU QUERY MẪU
-- =============================================

-- 1. Bảng lương tháng 6/2024
-- SELECT e.code, e.full_name, d.name AS department, e.position,
--        p.gross_pay, p.total_deduction, p.net_pay, p.status
-- FROM payslips p
-- JOIN employees e ON p.employee_id = e.id
-- JOIN departments d ON e.department_id = d.id
-- WHERE p.month = 6 AND p.year = 2024
-- ORDER BY e.code;

-- 2. Tổng hợp chấm công NV-003 tháng 6
-- SELECT
--   COUNT(*) FILTER (WHERE status = 'PRESENT') AS work_days,
--   COUNT(*) FILTER (WHERE status = 'LEAVE_PAID') AS leave_paid,
--   COUNT(*) FILTER (WHERE status = 'SICK') AS sick_days,
--   COUNT(*) FILTER (WHERE status = 'OT') AS ot_days,
--   SUM(ot_hours) AS total_ot_hours
-- FROM attendance
-- WHERE employee_id = 3 AND EXTRACT(MONTH FROM date) = 6 AND EXTRACT(YEAR FROM date) = 2024;

-- 3. KPI trung bình theo nhân viên
-- SELECT e.code, e.full_name,
--        AVG(k.achievement_rate) AS avg_achievement,
--        AVG(k.score) AS avg_score
-- FROM kpi_evaluations k
-- JOIN employees e ON k.employee_id = e.id
-- WHERE k.period = '2024-Q2' AND k.status = 'APPROVED'
-- GROUP BY e.id, e.code, e.full_name
-- ORDER BY avg_score DESC;

-- 4. Tổng chi phí lương
-- SELECT SUM(gross_pay) AS total_gross,
--        SUM(total_deduction) AS total_deduction,
--        SUM(net_pay) AS total_net
-- FROM payslips
-- WHERE month = 6 AND year = 2024;
