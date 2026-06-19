-- =============================================
-- MODULE KẾ TOÁN (ACCOUNTING) - DATABASE STRUCTURE & SAMPLE DATA
-- Yêu cầu: Chạy file erp-database.sql (30-erp-system) trước
-- Database: PostgreSQL
-- =============================================

-- =============================================
-- 1. BẢNG ACCOUNTS (Hệ thống tài khoản kế toán)
-- =============================================
CREATE TABLE IF NOT EXISTS accounts (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    type VARCHAR(20) NOT NULL,                 -- ASSET, LIABILITY, EQUITY, REVENUE, EXPENSE
    parent_code VARCHAR(10),
    level INT DEFAULT 1,
    active BOOLEAN DEFAULT TRUE
);

-- =============================================
-- 2. BẢNG JOURNAL ENTRIES (Sổ cái - Bút toán)
-- =============================================
CREATE TABLE IF NOT EXISTS journal_entries (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,          -- JE-2024-0001
    entry_date DATE NOT NULL,
    description TEXT,
    reference_type VARCHAR(20),                -- SALES, PURCHASE, PAYMENT, RECEIPT, SALARY, MANUAL
    reference_id BIGINT,
    status VARCHAR(20) DEFAULT 'DRAFT',        -- DRAFT, POSTED
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS journal_entry_lines (
    id BIGSERIAL PRIMARY KEY,
    entry_id BIGINT REFERENCES journal_entries(id) ON DELETE CASCADE,
    account_code VARCHAR(10) REFERENCES accounts(code),
    description TEXT,
    debit_amount DECIMAL(15,2) DEFAULT 0,
    credit_amount DECIMAL(15,2) DEFAULT 0
);

-- =============================================
-- 3. BẢNG RECEIPT VOUCHERS (Phiếu thu)
-- =============================================
CREATE TABLE IF NOT EXISTS receipt_vouchers (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,          -- PT-2024-0001
    voucher_date DATE NOT NULL,
    customer_id BIGINT,
    amount DECIMAL(15,2) NOT NULL,
    payment_method VARCHAR(20),                -- CASH, BANK_TRANSFER
    description TEXT,
    invoice_id BIGINT,
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- 4. BẢNG PAYMENT VOUCHERS (Phiếu chi)
-- =============================================
CREATE TABLE IF NOT EXISTS payment_vouchers (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,          -- PC-2024-0001
    voucher_date DATE NOT NULL,
    supplier_id BIGINT,
    amount DECIMAL(15,2) NOT NULL,
    payment_method VARCHAR(20),
    description TEXT,
    purchase_order_id BIGINT,
    category VARCHAR(50),                       -- SUPPLIER, SALARY, RENT, UTILITY, OTHER
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- SAMPLE DATA - HỆ THỐNG TÀI KHOẢN (Theo TT200)
-- =============================================
INSERT INTO accounts (code, name, type, parent_code, level) VALUES
-- Loại 1: Tài sản
('1', 'TÀI SẢN', 'ASSET', NULL, 0),
('111', 'Tiền mặt', 'ASSET', '1', 1),
('112', 'Tiền gửi ngân hàng', 'ASSET', '1', 1),
('131', 'Phải thu khách hàng', 'ASSET', '1', 1),
('152', 'Nguyên vật liệu', 'ASSET', '1', 1),
('155', 'Thành phẩm', 'ASSET', '1', 1),
('156', 'Hàng hóa', 'ASSET', '1', 1),
('211', 'Tài sản cố định', 'ASSET', '1', 1),
-- Loại 2: Nợ phải trả
('3', 'NỢ PHẢI TRẢ', 'LIABILITY', NULL, 0),
('331', 'Phải trả nhà cung cấp', 'LIABILITY', '3', 1),
('334', 'Phải trả người lao động', 'LIABILITY', '3', 1),
('3331', 'Thuế GTGT phải nộp', 'LIABILITY', '3', 2),
('3334', 'Thuế TNCN phải nộp', 'LIABILITY', '3', 2),
('338', 'Phải trả khác (BHXH, BHYT)', 'LIABILITY', '3', 1),
-- Loại 4: Vốn chủ sở hữu
('4', 'VỐN CHỦ SỞ HỮU', 'EQUITY', NULL, 0),
('411', 'Vốn đầu tư của chủ sở hữu', 'EQUITY', '4', 1),
('421', 'Lợi nhuận chưa phân phối', 'EQUITY', '4', 1),
-- Loại 5: Doanh thu
('5', 'DOANH THU', 'REVENUE', NULL, 0),
('511', 'Doanh thu bán hàng', 'REVENUE', '5', 1),
('515', 'Doanh thu tài chính', 'REVENUE', '5', 1),
-- Loại 6: Chi phí
('6', 'CHI PHÍ', 'EXPENSE', NULL, 0),
('621', 'Chi phí NVL trực tiếp', 'EXPENSE', '6', 1),
('622', 'Chi phí nhân công trực tiếp', 'EXPENSE', '6', 1),
('632', 'Giá vốn hàng bán', 'EXPENSE', '6', 1),
('641', 'Chi phí bán hàng', 'EXPENSE', '6', 1),
('642', 'Chi phí quản lý doanh nghiệp', 'EXPENSE', '6', 1);

-- =============================================
-- SAMPLE DATA - JOURNAL ENTRIES (Bút toán)
-- =============================================
INSERT INTO journal_entries (code, entry_date, description, reference_type, status, created_by) VALUES
('JE-2024-0001', '2024-06-10', 'Bán hàng - HĐ INV-2024-0001 (Công ty ABC)', 'SALES', 'POSTED', 'user1'),
('JE-2024-0002', '2024-06-15', 'Thu tiền KH - Công ty ABC (đợt 1)', 'RECEIPT', 'POSTED', 'user1'),
('JE-2024-0003', '2024-06-25', 'Thu tiền KH - Công ty ABC (đợt 2)', 'RECEIPT', 'POSTED', 'user1'),
('JE-2024-0004', '2024-06-10', 'Nhập hàng - PO-2024-0001 (SIM Tech)', 'PURCHASE', 'POSTED', 'user2'),
('JE-2024-0005', '2024-06-15', 'Thanh toán NCC - SIM Tech', 'PAYMENT', 'POSTED', 'user2'),
('JE-2024-0006', '2024-06-30', 'Chi phí lương tháng 06/2024', 'SALARY', 'POSTED', 'user1'),
('JE-2024-0007', '2024-06-30', 'Chi phí thuê văn phòng tháng 06', 'MANUAL', 'POSTED', 'user1');

INSERT INTO journal_entry_lines (entry_id, account_code, description, debit_amount, credit_amount) VALUES
-- JE-0001: Bán hàng 550tr (gồm VAT 10%)
(1, '131', 'Phải thu KH - Công ty ABC', 550000000, 0),
(1, '511', 'Doanh thu bán SIM', 0, 500000000),
(1, '3331', 'Thuế GTGT 10%', 0, 50000000),
-- JE-0002: Thu tiền đợt 1 (300tr qua ngân hàng)
(2, '112', 'Nhận CK từ ABC - VCB', 300000000, 0),
(2, '131', 'Giảm phải thu ABC', 0, 300000000),
-- JE-0003: Thu tiền đợt 2 (250tr)
(3, '112', 'Nhận CK từ ABC - VCB', 250000000, 0),
(3, '131', 'Giảm phải thu ABC', 0, 250000000),
-- JE-0004: Nhập hàng NVL 600tr
(4, '152', 'Nhập chip eSIM từ SIM Tech', 600000000, 0),
(4, '331', 'Nợ NCC SIM Tech', 0, 600000000),
-- JE-0005: Thanh toán NCC 600tr
(5, '331', 'Trả nợ SIM Tech', 600000000, 0),
(5, '112', 'Chi CK cho SIM Tech', 0, 600000000),
-- JE-0006: Chi phí lương 200tr
(6, '642', 'Chi phí lương tháng 6', 200000000, 0),
(6, '334', 'Phải trả NV tháng 6', 0, 179000000),
(6, '338', 'BHXH, BHYT, BHTN', 0, 21000000),
-- JE-0007: Tiền thuê VP 30tr
(7, '642', 'Thuê VP tháng 6', 30000000, 0),
(7, '112', 'Chi CK tiền thuê', 0, 30000000);

-- =============================================
-- SAMPLE DATA - PHIẾU THU / PHIẾU CHI
-- =============================================
INSERT INTO receipt_vouchers (code, voucher_date, customer_id, amount, payment_method, description, created_by) VALUES
('PT-2024-0001', '2024-06-15', 1, 300000000, 'BANK_TRANSFER', 'Thu tiền ABC đợt 1', 'user1'),
('PT-2024-0002', '2024-06-25', 1, 250000000, 'BANK_TRANSFER', 'Thu tiền ABC đợt 2', 'user1'),
('PT-2024-0003', '2024-06-20', 2, 150000000, 'BANK_TRANSFER', 'Thu tiền XYZ đợt 1', 'user1');

INSERT INTO payment_vouchers (code, voucher_date, supplier_id, amount, payment_method, description, category, created_by) VALUES
('PC-2024-0001', '2024-06-15', 1, 600000000, 'BANK_TRANSFER', 'Thanh toán SIM Tech - PO-0001', 'SUPPLIER', 'user2'),
('PC-2024-0002', '2024-06-30', NULL, 200000000, 'BANK_TRANSFER', 'Chi lương tháng 06/2024', 'SALARY', 'user1'),
('PC-2024-0003', '2024-06-30', NULL, 30000000, 'BANK_TRANSFER', 'Thuê VP tháng 06', 'RENT', 'user1');

-- =============================================
-- CÂU QUERY MẪU
-- =============================================

-- 1. Báo cáo Lãi lỗ tháng 06/2024
-- SELECT
--   (SELECT COALESCE(SUM(credit_amount),0) FROM journal_entry_lines jl
--    JOIN journal_entries je ON jl.entry_id = je.id
--    WHERE jl.account_code = '511' AND je.status = 'POSTED'
--    AND je.entry_date BETWEEN '2024-06-01' AND '2024-06-30') AS revenue,
--   (SELECT COALESCE(SUM(debit_amount),0) FROM journal_entry_lines jl
--    JOIN journal_entries je ON jl.entry_id = je.id
--    WHERE jl.account_code LIKE '6%' AND je.status = 'POSTED'
--    AND je.entry_date BETWEEN '2024-06-01' AND '2024-06-30') AS total_expense;

-- 2. Số dư tài khoản
-- SELECT a.code, a.name,
--        COALESCE(SUM(jl.debit_amount),0) AS total_debit,
--        COALESCE(SUM(jl.credit_amount),0) AS total_credit,
--        COALESCE(SUM(jl.debit_amount),0) - COALESCE(SUM(jl.credit_amount),0) AS balance
-- FROM accounts a
-- LEFT JOIN journal_entry_lines jl ON a.code = jl.account_code
-- LEFT JOIN journal_entries je ON jl.entry_id = je.id AND je.status = 'POSTED'
-- WHERE a.level > 0
-- GROUP BY a.code, a.name
-- ORDER BY a.code;
