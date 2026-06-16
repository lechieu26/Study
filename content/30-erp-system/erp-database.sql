-- =============================================
-- HỆ THỐNG ERP - DATABASE STRUCTURE & SAMPLE DATA
-- Module: Tổng quan - Bảng dùng chung (Master Tables)
-- Database: PostgreSQL
-- =============================================

-- Xóa bảng cũ nếu tồn tại (theo thứ tự phụ thuộc)
DROP TABLE IF EXISTS kpi_evaluations CASCADE;
DROP TABLE IF EXISTS payslips CASCADE;
DROP TABLE IF EXISTS attendance CASCADE;
DROP TABLE IF EXISTS employees CASCADE;
DROP TABLE IF EXISTS departments CASCADE;
DROP TABLE IF EXISTS journal_entry_lines CASCADE;
DROP TABLE IF EXISTS journal_entries CASCADE;
DROP TABLE IF EXISTS accounts CASCADE;
DROP TABLE IF EXISTS payment_vouchers CASCADE;
DROP TABLE IF EXISTS receipt_vouchers CASCADE;
DROP TABLE IF EXISTS inventory_check_items CASCADE;
DROP TABLE IF EXISTS inventory_checks CASCADE;
DROP TABLE IF EXISTS stock_transfer_items CASCADE;
DROP TABLE IF EXISTS stock_transfers CASCADE;
DROP TABLE IF EXISTS stock_transactions CASCADE;
DROP TABLE IF EXISTS stock CASCADE;
DROP TABLE IF EXISTS warehouses CASCADE;
DROP TABLE IF EXISTS bom_items CASCADE;
DROP TABLE IF EXISTS boms CASCADE;
DROP TABLE IF EXISTS work_orders CASCADE;
DROP TABLE IF EXISTS goods_receipt_items CASCADE;
DROP TABLE IF EXISTS goods_receipts CASCADE;
DROP TABLE IF EXISTS purchase_order_items CASCADE;
DROP TABLE IF EXISTS purchase_orders CASCADE;
DROP TABLE IF EXISTS purchase_request_items CASCADE;
DROP TABLE IF EXISTS purchase_requests CASCADE;
DROP TABLE IF EXISTS suppliers CASCADE;
DROP TABLE IF EXISTS payments CASCADE;
DROP TABLE IF EXISTS invoices CASCADE;
DROP TABLE IF EXISTS sales_order_items CASCADE;
DROP TABLE IF EXISTS sales_orders CASCADE;
DROP TABLE IF EXISTS quotation_items CASCADE;
DROP TABLE IF EXISTS quotations CASCADE;
DROP TABLE IF EXISTS customers CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- =============================================
-- 1. BẢNG USERS (Tài khoản người dùng)
-- =============================================
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    role VARCHAR(20) DEFAULT 'USER',          -- ADMIN, MANAGER, USER
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- 2. BẢNG CATEGORIES (Danh mục sản phẩm)
-- =============================================
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- 3. BẢNG PRODUCTS (Sản phẩm / Hàng hóa)
-- =============================================
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(200) NOT NULL,
    category_id BIGINT REFERENCES categories(id),
    unit VARCHAR(20) DEFAULT 'cái',           -- Đơn vị tính
    price DECIMAL(15,2) DEFAULT 0,            -- Giá bán
    cost_price DECIMAL(15,2) DEFAULT 0,       -- Giá vốn / giá mua
    min_stock INT DEFAULT 0,                   -- Mức tồn kho tối thiểu
    description TEXT,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- SAMPLE DATA - USERS
-- =============================================
INSERT INTO users (username, password, full_name, email, role) VALUES
('admin', '$2a$10$abcdefghijklmnopqrstuv', 'Administrator', 'admin@erp.com', 'ADMIN'),
('manager1', '$2a$10$abcdefghijklmnopqrstuv', 'Nguyễn Văn Minh', 'minh@erp.com', 'MANAGER'),
('manager2', '$2a$10$abcdefghijklmnopqrstuv', 'Trần Thị Hoa', 'hoa@erp.com', 'MANAGER'),
('user1', '$2a$10$abcdefghijklmnopqrstuv', 'Lê Hoàng Nam', 'nam@erp.com', 'USER'),
('user2', '$2a$10$abcdefghijklmnopqrstuv', 'Phạm Thị Lan', 'lan@erp.com', 'USER'),
('user3', '$2a$10$abcdefghijklmnopqrstuv', 'Võ Minh Tuấn', 'tuan@erp.com', 'USER');

-- =============================================
-- SAMPLE DATA - CATEGORIES
-- =============================================
INSERT INTO categories (code, name, description) VALUES
('DT', 'Điện tử', 'Linh kiện và sản phẩm điện tử'),
('SIM', 'SIM & eSIM', 'Thẻ SIM và eSIM các loại'),
('NVL', 'Nguyên vật liệu', 'Nguyên vật liệu sản xuất'),
('PKN', 'Phụ kiện', 'Phụ kiện đi kèm sản phẩm'),
('BB', 'Bao bì', 'Bao bì đóng gói');

-- =============================================
-- SAMPLE DATA - PRODUCTS
-- =============================================
INSERT INTO products (code, name, category_id, unit, price, cost_price, min_stock, description) VALUES
-- Thành phẩm
('SP-001', 'SIM 4G Mobifone', 2, 'cái', 50000, 37000, 5000, 'Thẻ SIM 4G Mobifone đã kích hoạt'),
('SP-002', 'SIM 4G Viettel', 2, 'cái', 55000, 38000, 5000, 'Thẻ SIM 4G Viettel đã kích hoạt'),
('SP-003', 'eSIM Quốc tế', 2, 'cái', 150000, 80000, 2000, 'eSIM dùng data quốc tế'),
('SP-004', 'SIM Số đẹp', 2, 'cái', 500000, 200000, 500, 'SIM có số đẹp dễ nhớ'),

-- Nguyên vật liệu
('NVL-001', 'Chip eSIM', 3, 'cái', 0, 30000, 10000, 'Chip eSIM nhập khẩu'),
('NVL-002', 'Phôi nhựa SIM', 3, 'cái', 0, 5000, 15000, 'Phôi nhựa để in SIM'),
('NVL-003', 'Bao bì SIM', 5, 'cái', 0, 2000, 20000, 'Bao bì đóng gói SIM'),
('NVL-004', 'Nhãn in mã vạch', 5, 'tờ', 0, 500, 30000, 'Nhãn dán thông tin SIM'),

-- Phụ kiện
('PK-001', 'Kim chọc SIM', 4, 'cái', 5000, 1000, 3000, 'Kim lấy SIM ra khỏi điện thoại'),
('PK-002', 'Adapter SIM', 4, 'bộ', 10000, 3000, 2000, 'Bộ adapter Nano → Micro → Standard');

-- =============================================
-- HƯỚNG DẪN SỬ DỤNG
-- =============================================
-- 1. Mở pgAdmin hoặc psql
-- 2. Tạo database: CREATE DATABASE erp_system;
-- 3. Kết nối vào database erp_system
-- 4. Chạy toàn bộ file SQL này
-- 5. Kiểm tra: SELECT * FROM products;
--
-- File này chứa các bảng dùng chung (master tables).
-- Các module khác sẽ có file SQL riêng với bảng nghiệp vụ.
-- Chạy file này TRƯỚC, sau đó chạy file SQL của từng module.
