-- =============================================
-- MODULE MUA HÀNG (PROCUREMENT) - DATABASE STRUCTURE & SAMPLE DATA
-- Yêu cầu: Chạy file erp-database.sql (30-erp-system) trước
-- Database: PostgreSQL
-- =============================================

-- =============================================
-- 1. BẢNG SUPPLIERS (Nhà cung cấp)
-- =============================================
CREATE TABLE IF NOT EXISTS suppliers (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(200) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    address TEXT,
    tax_code VARCHAR(20),
    contact_person VARCHAR(100),
    payment_term VARCHAR(20) DEFAULT 'NET30',  -- COD, NET30, NET60
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- 2. BẢNG PURCHASE REQUESTS (Yêu cầu mua hàng)
-- =============================================
CREATE TABLE IF NOT EXISTS purchase_requests (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,          -- PR-2024-0001
    requested_by BIGINT REFERENCES users(id),
    department VARCHAR(50),
    request_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',       -- PENDING, APPROVED, REJECTED
    reason TEXT,
    approved_by BIGINT REFERENCES users(id),
    approved_date DATE,
    note TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS purchase_request_items (
    id BIGSERIAL PRIMARY KEY,
    request_id BIGINT REFERENCES purchase_requests(id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES products(id),
    quantity INT NOT NULL,
    note TEXT
);

-- =============================================
-- 3. BẢNG PURCHASE ORDERS (Đơn đặt hàng)
-- =============================================
CREATE TABLE IF NOT EXISTS purchase_orders (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,          -- PO-2024-0001
    supplier_id BIGINT REFERENCES suppliers(id),
    request_id BIGINT REFERENCES purchase_requests(id),
    order_date DATE NOT NULL,
    expected_date DATE,
    status VARCHAR(20) DEFAULT 'DRAFT',
    -- DRAFT → SENT → CONFIRMED → RECEIVED / PARTIALLY_RECEIVED → COMPLETED
    total_amount DECIMAL(15,2) DEFAULT 0,
    paid_amount DECIMAL(15,2) DEFAULT 0,
    note TEXT,
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS purchase_order_items (
    id BIGSERIAL PRIMARY KEY,
    purchase_order_id BIGINT REFERENCES purchase_orders(id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES products(id),
    quantity INT NOT NULL,
    unit_price DECIMAL(15,2) NOT NULL,
    received_quantity INT DEFAULT 0,
    amount DECIMAL(15,2) NOT NULL
);

-- =============================================
-- 4. BẢNG GOODS RECEIPTS (Nhập hàng)
-- =============================================
CREATE TABLE IF NOT EXISTS goods_receipts (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,          -- GR-2024-0001
    purchase_order_id BIGINT REFERENCES purchase_orders(id),
    warehouse_id BIGINT,
    receipt_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'DRAFT',        -- DRAFT, COMPLETED
    note TEXT,
    received_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS goods_receipt_items (
    id BIGSERIAL PRIMARY KEY,
    receipt_id BIGINT REFERENCES goods_receipts(id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES products(id),
    ordered_quantity INT,
    received_quantity INT NOT NULL,
    note TEXT
);

-- =============================================
-- SAMPLE DATA - SUPPLIERS
-- =============================================
INSERT INTO suppliers (code, name, phone, email, address, tax_code, contact_person, payment_term) VALUES
('NCC-001', 'Công ty SIM Tech Vietnam', '028-3800-1111', 'sales@simtech.vn', '50 Nguyễn Thị Minh Khai, Q.1, TP.HCM', '0314567890', 'Nguyễn Thanh Tùng', 'NET30'),
('NCC-002', 'Nhà máy Chip Điện Tử HN', '024-3700-2222', 'order@chiphn.com', '100 Phạm Hùng, Nam Từ Liêm, Hà Nội', '0105678901', 'Trần Minh Đức', 'NET60'),
('NCC-003', 'Công ty Bao Bì Xanh', '028-3600-3333', 'info@baobixanh.com', '200 Quốc Lộ 1A, Bình Tân, TP.HCM', '0316789012', 'Lê Thị Mai', 'COD'),
('NCC-004', 'Đại lý Nhựa Phương Nam', '0918765432', 'phuongnam@nhua.com', '88 Lý Thường Kiệt, Q.10, TP.HCM', '0317890123', 'Phạm Văn Hùng', 'NET30');

-- =============================================
-- SAMPLE DATA - PURCHASE REQUESTS
-- =============================================
INSERT INTO purchase_requests (code, requested_by, department, request_date, status, reason, approved_by, approved_date) VALUES
('PR-2024-0001', 4, 'Kho vận', '2024-06-01', 'APPROVED', 'Tồn kho chip eSIM sắp hết, cần bổ sung gấp', 2, '2024-06-02'),
('PR-2024-0002', 5, 'Sản xuất', '2024-06-05', 'APPROVED', 'Cần NVL cho đợt sản xuất SIM tháng 7', 2, '2024-06-06'),
('PR-2024-0003', 4, 'Kho vận', '2024-06-10', 'PENDING', 'Bổ sung bao bì đóng gói', NULL, NULL),
('PR-2024-0004', 6, 'Kinh doanh', '2024-06-12', 'REJECTED', 'Mua thêm SIM số đẹp', 3, '2024-06-13');

INSERT INTO purchase_request_items (request_id, product_id, quantity, note) VALUES
(1, 5, 20000, 'Chip eSIM nhập khẩu - loại A'),
(2, 5, 50000, 'Chip cho đợt SX tháng 7'),
(2, 6, 50000, 'Phôi nhựa SIM'),
(2, 7, 50000, 'Bao bì SIM'),
(3, 7, 30000, 'Bao bì loại mới'),
(4, 4, 500, '');

-- =============================================
-- SAMPLE DATA - PURCHASE ORDERS
-- =============================================
INSERT INTO purchase_orders (code, supplier_id, request_id, order_date, expected_date, status, total_amount, paid_amount, created_by) VALUES
('PO-2024-0001', 1, 1, '2024-06-03', '2024-06-10', 'COMPLETED', 600000000, 600000000, 'user1'),
('PO-2024-0002', 2, 2, '2024-06-07', '2024-06-20', 'RECEIVED', 1500000000, 750000000, 'user1'),
('PO-2024-0003', 3, 2, '2024-06-07', '2024-06-14', 'CONFIRMED', 100000000, 0, 'user2');

INSERT INTO purchase_order_items (purchase_order_id, product_id, quantity, unit_price, received_quantity, amount) VALUES
-- PO-0001: Mua 20.000 chip eSIM từ SIM Tech
(1, 5, 20000, 30000, 20000, 600000000),
-- PO-0002: Mua chip + phôi từ Chip HN
(2, 5, 50000, 30000, 50000, 1500000000),
-- PO-0003: Mua bao bì
(3, 7, 50000, 2000, 0, 100000000);

-- =============================================
-- SAMPLE DATA - GOODS RECEIPTS
-- =============================================
INSERT INTO goods_receipts (code, purchase_order_id, warehouse_id, receipt_date, status, received_by) VALUES
('GR-2024-0001', 1, 1, '2024-06-10', 'COMPLETED', 'user4'),
('GR-2024-0002', 2, 1, '2024-06-18', 'COMPLETED', 'user4');

INSERT INTO goods_receipt_items (receipt_id, product_id, ordered_quantity, received_quantity, note) VALUES
(1, 5, 20000, 20000, 'Nhận đủ, chất lượng OK'),
(2, 5, 50000, 50000, 'Nhận đủ');

-- =============================================
-- CÂU QUERY MẪU
-- =============================================

-- 1. Xem công nợ phải trả NCC
-- SELECT s.code, s.name,
--        SUM(po.total_amount) AS total_po,
--        SUM(po.paid_amount) AS total_paid,
--        SUM(po.total_amount - po.paid_amount) AS debt
-- FROM purchase_orders po JOIN suppliers s ON po.supplier_id = s.id
-- WHERE po.status IN ('RECEIVED', 'PARTIALLY_RECEIVED', 'COMPLETED')
-- GROUP BY s.id, s.code, s.name
-- HAVING SUM(po.total_amount - po.paid_amount) > 0;

-- 2. Xem YCMH chờ duyệt
-- SELECT pr.code, u.full_name AS requester, pr.department,
--        pr.request_date, pr.reason
-- FROM purchase_requests pr JOIN users u ON pr.requested_by = u.id
-- WHERE pr.status = 'PENDING';
