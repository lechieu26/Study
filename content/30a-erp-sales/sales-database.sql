-- =============================================
-- MODULE BÁN HÀNG (SALES) - DATABASE STRUCTURE & SAMPLE DATA
-- Yêu cầu: Chạy file erp-database.sql (30-erp-system) trước
-- Database: PostgreSQL
-- =============================================

-- =============================================
-- 1. BẢNG CUSTOMERS (Khách hàng)
-- =============================================
CREATE TABLE IF NOT EXISTS customers (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(200) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    address TEXT,
    tax_code VARCHAR(20),
    contact_person VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- 2. BẢNG QUOTATIONS (Báo giá)
-- =============================================
CREATE TABLE IF NOT EXISTS quotations (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,          -- QT-2024-0001
    customer_id BIGINT REFERENCES customers(id),
    quotation_date DATE NOT NULL,
    valid_until DATE,                           -- Hiệu lực đến ngày
    status VARCHAR(20) DEFAULT 'DRAFT',         -- DRAFT, SENT, ACCEPTED, REJECTED, EXPIRED
    total_amount DECIMAL(15,2) DEFAULT 0,
    note TEXT,
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS quotation_items (
    id BIGSERIAL PRIMARY KEY,
    quotation_id BIGINT REFERENCES quotations(id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES products(id),
    quantity INT NOT NULL,
    unit_price DECIMAL(15,2) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,              -- quantity × unit_price
    note TEXT
);

-- =============================================
-- 3. BẢNG SALES ORDERS (Đơn hàng)
-- =============================================
CREATE TABLE IF NOT EXISTS sales_orders (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,          -- SO-2024-0001
    customer_id BIGINT REFERENCES customers(id),
    quotation_id BIGINT REFERENCES quotations(id),
    order_date DATE NOT NULL,
    delivery_date DATE,
    status VARCHAR(20) DEFAULT 'DRAFT',
    -- DRAFT → CONFIRMED → DELIVERING → DELIVERED → INVOICED → COMPLETED
    total_amount DECIMAL(15,2) DEFAULT 0,
    note TEXT,
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS sales_order_items (
    id BIGSERIAL PRIMARY KEY,
    sales_order_id BIGINT REFERENCES sales_orders(id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES products(id),
    quantity INT NOT NULL,
    unit_price DECIMAL(15,2) NOT NULL,
    amount DECIMAL(15,2) NOT NULL
);

-- =============================================
-- 4. BẢNG INVOICES (Hóa đơn)
-- =============================================
CREATE TABLE IF NOT EXISTS invoices (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,          -- INV-2024-0001
    sales_order_id BIGINT REFERENCES sales_orders(id),
    customer_id BIGINT REFERENCES customers(id),
    invoice_date DATE NOT NULL,
    due_date DATE,                              -- Hạn thanh toán
    subtotal DECIMAL(15,2) DEFAULT 0,          -- Tổng trước thuế
    tax_rate DECIMAL(5,2) DEFAULT 10,          -- % VAT
    tax_amount DECIMAL(15,2) DEFAULT 0,
    total_amount DECIMAL(15,2) DEFAULT 0,      -- Tổng sau thuế
    paid_amount DECIMAL(15,2) DEFAULT 0,       -- Đã thanh toán
    status VARCHAR(20) DEFAULT 'DRAFT',        -- DRAFT, SENT, PARTIALLY_PAID, PAID, OVERDUE
    note TEXT,
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- 5. BẢNG PAYMENTS (Thanh toán)
-- =============================================
CREATE TABLE IF NOT EXISTS payments (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,          -- PAY-2024-0001
    invoice_id BIGINT REFERENCES invoices(id),
    payment_date DATE NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    payment_method VARCHAR(20),                -- CASH, BANK_TRANSFER, CHECK
    reference_no VARCHAR(50),                   -- Số tham chiếu GD
    note TEXT,
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- SAMPLE DATA - CUSTOMERS
-- =============================================
INSERT INTO customers (code, name, phone, email, address, tax_code) VALUES
('KH-001', 'Công ty TNHH Viễn Thông ABC', '028-3823-4567', 'abc@vienthong.com', '123 Nguyễn Huệ, Q.1, TP.HCM', '0312345678'),
('KH-002', 'Công ty CP Công Nghệ XYZ', '024-3976-5432', 'info@xyztech.com', '456 Trần Duy Hưng, Cầu Giấy, Hà Nội', '0109876543'),
('KH-003', 'Cửa hàng Di Động Minh Anh', '0901234567', 'minhanh@mobile.com', '789 Lê Lợi, Q.1, TP.HCM', '0311111111'),
('KH-004', 'Đại lý SIM Hoàng Long', '0912345678', 'hoanglong@sim.com', '321 Hai Bà Trưng, Q.3, TP.HCM', '0312222222'),
('KH-005', 'Siêu thị Điện Tử MegaStore', '028-3999-8888', 'mega@store.com', '100 Võ Văn Tần, Q.3, TP.HCM', '0313333333');

-- =============================================
-- SAMPLE DATA - QUOTATIONS
-- =============================================
INSERT INTO quotations (code, customer_id, quotation_date, valid_until, status, total_amount, created_by) VALUES
('QT-2024-0001', 1, '2024-06-01', '2024-06-15', 'ACCEPTED', 500000000, 'user1'),
('QT-2024-0002', 2, '2024-06-03', '2024-06-17', 'SENT', 825000000, 'user1'),
('QT-2024-0003', 3, '2024-06-05', '2024-06-19', 'DRAFT', 25000000, 'user2'),
('QT-2024-0004', 4, '2024-06-10', '2024-06-24', 'REJECTED', 100000000, 'user1');

INSERT INTO quotation_items (quotation_id, product_id, quantity, unit_price, amount) VALUES
-- QT-2024-0001: Công ty ABC mua 10.000 SIM 4G Mobifone
(1, 1, 10000, 50000, 500000000),
-- QT-2024-0002: Công ty XYZ mua SIM Viettel + eSIM
(2, 2, 10000, 55000, 550000000),
(2, 3, 1000, 150000, 150000000),
(2, 4, 250, 500000, 125000000),
-- QT-2024-0003: Cửa hàng Minh Anh mua SIM 4G
(3, 1, 500, 50000, 25000000),
-- QT-2024-0004: Đại lý Hoàng Long mua SIM số đẹp
(4, 4, 200, 500000, 100000000);

-- =============================================
-- SAMPLE DATA - SALES ORDERS
-- =============================================
INSERT INTO sales_orders (code, customer_id, quotation_id, order_date, delivery_date, status, total_amount, created_by) VALUES
('SO-2024-0001', 1, 1, '2024-06-05', '2024-06-10', 'COMPLETED', 500000000, 'user1'),
('SO-2024-0002', 2, NULL, '2024-06-08', '2024-06-15', 'DELIVERING', 275000000, 'user1'),
('SO-2024-0003', 5, NULL, '2024-06-12', '2024-06-18', 'CONFIRMED', 150000000, 'user2');

INSERT INTO sales_order_items (sales_order_id, product_id, quantity, unit_price, amount) VALUES
-- SO-0001: Từ báo giá QT-0001
(1, 1, 10000, 50000, 500000000),
-- SO-0002: Đơn trực tiếp
(2, 2, 5000, 55000, 275000000),
-- SO-0003: MegaStore mua eSIM
(3, 3, 1000, 150000, 150000000);

-- =============================================
-- SAMPLE DATA - INVOICES
-- =============================================
INSERT INTO invoices (code, sales_order_id, customer_id, invoice_date, due_date, subtotal, tax_rate, tax_amount, total_amount, paid_amount, status, created_by) VALUES
('INV-2024-0001', 1, 1, '2024-06-10', '2024-07-10', 500000000, 10, 50000000, 550000000, 550000000, 'PAID', 'user1'),
('INV-2024-0002', 2, 2, '2024-06-15', '2024-07-15', 275000000, 10, 27500000, 302500000, 150000000, 'PARTIALLY_PAID', 'user1');

-- =============================================
-- SAMPLE DATA - PAYMENTS
-- =============================================
INSERT INTO payments (code, invoice_id, payment_date, amount, payment_method, reference_no, created_by) VALUES
('PAY-2024-0001', 1, '2024-06-15', 300000000, 'BANK_TRANSFER', 'VCB-20240615-001', 'user1'),
('PAY-2024-0002', 1, '2024-06-25', 250000000, 'BANK_TRANSFER', 'VCB-20240625-002', 'user1'),
('PAY-2024-0003', 2, '2024-06-20', 150000000, 'BANK_TRANSFER', 'TCB-20240620-001', 'user1');

-- =============================================
-- CÂU QUERY MẪU
-- =============================================

-- 1. Xem danh sách đơn hàng kèm tên khách hàng
-- SELECT so.code, c.name AS customer, so.order_date, so.status, so.total_amount
-- FROM sales_orders so JOIN customers c ON so.customer_id = c.id;

-- 2. Xem công nợ khách hàng
-- SELECT c.code, c.name,
--        SUM(i.total_amount) AS total_invoice,
--        SUM(i.paid_amount) AS total_paid,
--        SUM(i.total_amount - i.paid_amount) AS debt
-- FROM invoices i JOIN customers c ON i.customer_id = c.id
-- GROUP BY c.id, c.code, c.name
-- HAVING SUM(i.total_amount - i.paid_amount) > 0;

-- 3. Xem chi tiết đơn hàng
-- SELECT so.code, p.name AS product, soi.quantity, soi.unit_price, soi.amount
-- FROM sales_order_items soi
-- JOIN sales_orders so ON soi.sales_order_id = so.id
-- JOIN products p ON soi.product_id = p.id
-- WHERE so.code = 'SO-2024-0001';
