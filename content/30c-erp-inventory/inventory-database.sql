-- =============================================
-- MODULE KHO (INVENTORY) - DATABASE STRUCTURE & SAMPLE DATA
-- Yêu cầu: Chạy file erp-database.sql (30-erp-system) trước
-- Database: PostgreSQL
-- =============================================

-- =============================================
-- 1. BẢNG WAREHOUSES (Kho hàng)
-- =============================================
CREATE TABLE IF NOT EXISTS warehouses (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    address TEXT,
    manager_id BIGINT REFERENCES users(id),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- 2. BẢNG STOCK (Tồn kho hiện tại)
-- =============================================
CREATE TABLE IF NOT EXISTS stock (
    id BIGSERIAL PRIMARY KEY,
    warehouse_id BIGINT REFERENCES warehouses(id),
    product_id BIGINT REFERENCES products(id),
    quantity INT NOT NULL DEFAULT 0,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(warehouse_id, product_id)
);

-- =============================================
-- 3. BẢNG STOCK TRANSACTIONS (Lịch sử nhập xuất)
-- =============================================
CREATE TABLE IF NOT EXISTS stock_transactions (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20),
    type VARCHAR(20) NOT NULL,                 -- IN, OUT, TRANSFER, ADJUST
    warehouse_id BIGINT REFERENCES warehouses(id),
    product_id BIGINT REFERENCES products(id),
    quantity INT NOT NULL,                      -- Dương: nhập, Âm: xuất
    reference_type VARCHAR(20),                -- PURCHASE, SALES, PRODUCTION, MANUAL
    reference_id BIGINT,
    note TEXT,
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- 4. BẢNG STOCK TRANSFERS (Chuyển kho)
-- =============================================
CREATE TABLE IF NOT EXISTS stock_transfers (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,          -- ST-2024-0001
    from_warehouse_id BIGINT REFERENCES warehouses(id),
    to_warehouse_id BIGINT REFERENCES warehouses(id),
    transfer_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'DRAFT',        -- DRAFT, IN_TRANSIT, COMPLETED
    note TEXT,
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS stock_transfer_items (
    id BIGSERIAL PRIMARY KEY,
    transfer_id BIGINT REFERENCES stock_transfers(id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES products(id),
    quantity INT NOT NULL
);

-- =============================================
-- 5. BẢNG INVENTORY CHECKS (Kiểm kê)
-- =============================================
CREATE TABLE IF NOT EXISTS inventory_checks (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,          -- IC-2024-0001
    warehouse_id BIGINT REFERENCES warehouses(id),
    check_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'DRAFT',        -- DRAFT, COMPLETED
    note TEXT,
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS inventory_check_items (
    id BIGSERIAL PRIMARY KEY,
    check_id BIGINT REFERENCES inventory_checks(id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES products(id),
    system_quantity INT,
    actual_quantity INT,
    difference INT,                             -- actual - system
    note TEXT
);

-- =============================================
-- SAMPLE DATA - WAREHOUSES
-- =============================================
INSERT INTO warehouses (code, name, address, manager_id) VALUES
('KHO-HN', 'Kho Hà Nội', '50 Phạm Hùng, Nam Từ Liêm, Hà Nội', 2),
('KHO-HCM', 'Kho Hồ Chí Minh', '100 Quốc Lộ 1A, Bình Tân, TP.HCM', 3),
('KHO-DN', 'Kho Đà Nẵng', '200 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 2);

-- =============================================
-- SAMPLE DATA - STOCK (Tồn kho hiện tại)
-- =============================================
INSERT INTO stock (warehouse_id, product_id, quantity) VALUES
-- Kho Hà Nội
(1, 1, 15000),    -- SIM 4G Mobifone: 15.000
(1, 2, 8000),     -- SIM 4G Viettel: 8.000
(1, 3, 3000),     -- eSIM Quốc tế: 3.000
(1, 5, 25000),    -- Chip eSIM: 25.000
(1, 6, 12000),    -- Phôi nhựa: 12.000
(1, 7, 18000),    -- Bao bì: 18.000
-- Kho HCM
(2, 1, 20000),    -- SIM 4G Mobifone: 20.000
(2, 2, 12000),    -- SIM 4G Viettel: 12.000
(2, 3, 5000),     -- eSIM: 5.000
(2, 4, 800),      -- SIM Số đẹp: 800
(2, 5, 30000),    -- Chip eSIM: 30.000
(2, 6, 20000),    -- Phôi nhựa: 20.000
(2, 7, 25000),    -- Bao bì: 25.000
-- Kho Đà Nẵng
(3, 1, 5000),     -- SIM 4G Mobifone: 5.000
(3, 2, 3000),     -- SIM 4G Viettel: 3.000
(3, 3, 1000);     -- eSIM: 1.000

-- =============================================
-- SAMPLE DATA - STOCK TRANSACTIONS
-- =============================================
INSERT INTO stock_transactions (code, type, warehouse_id, product_id, quantity, reference_type, reference_id, note, created_by, created_at) VALUES
-- Nhập hàng từ PO
('TXN-001', 'IN', 1, 5, 20000, 'PURCHASE', 1, 'Nhập từ PO-2024-0001', 'user4', '2024-06-10 09:00:00'),
('TXN-002', 'IN', 1, 5, 50000, 'PURCHASE', 2, 'Nhập từ PO-2024-0002', 'user4', '2024-06-18 10:30:00'),
-- Xuất hàng bán
('TXN-003', 'OUT', 2, 1, -10000, 'SALES', 1, 'Xuất cho SO-2024-0001', 'user5', '2024-06-10 14:00:00'),
('TXN-004', 'OUT', 2, 2, -5000, 'SALES', 2, 'Xuất cho SO-2024-0002', 'user5', '2024-06-15 11:00:00'),
-- Chuyển kho
('TXN-005', 'TRANSFER', 2, 1, -3000, 'MANUAL', NULL, 'Chuyển SIM đến kho HN', 'user4', '2024-06-12 08:00:00'),
('TXN-006', 'TRANSFER', 1, 1, 3000, 'MANUAL', NULL, 'Nhận SIM từ kho HCM', 'user4', '2024-06-12 08:00:00'),
-- Kiểm kê điều chỉnh
('TXN-007', 'ADJUST', 1, 7, -200, 'MANUAL', NULL, 'Kiểm kê: thiếu 200 bao bì', 'user4', '2024-06-20 16:00:00');

-- =============================================
-- SAMPLE DATA - STOCK TRANSFERS
-- =============================================
INSERT INTO stock_transfers (code, from_warehouse_id, to_warehouse_id, transfer_date, status, note, created_by) VALUES
('ST-2024-0001', 2, 1, '2024-06-12', 'COMPLETED', 'Chuyển SIM từ HCM ra HN', 'user4'),
('ST-2024-0002', 1, 3, '2024-06-15', 'COMPLETED', 'Bổ sung hàng cho kho Đà Nẵng', 'user4');

INSERT INTO stock_transfer_items (transfer_id, product_id, quantity) VALUES
(1, 1, 3000),
(2, 1, 2000),
(2, 2, 1000);

-- =============================================
-- SAMPLE DATA - INVENTORY CHECKS
-- =============================================
INSERT INTO inventory_checks (code, warehouse_id, check_date, status, created_by) VALUES
('IC-2024-0001', 1, '2024-06-20', 'COMPLETED', 'user4');

INSERT INTO inventory_check_items (check_id, product_id, system_quantity, actual_quantity, difference, note) VALUES
(1, 1, 15000, 15000, 0, 'Đúng'),
(1, 5, 25200, 25000, -200, 'Thiếu 200 - có thể hư hỏng'),
(1, 7, 18200, 18000, -200, 'Thiếu 200');

-- =============================================
-- CÂU QUERY MẪU
-- =============================================

-- 1. Tồn kho tổng hợp
-- SELECT p.code, p.name, w.name AS warehouse, s.quantity, p.min_stock,
--        CASE WHEN s.quantity <= 0 THEN 'HẾT HÀNG'
--             WHEN s.quantity <= p.min_stock THEN 'SẮP HẾT'
--             ELSE 'ĐỦ'
--        END AS status
-- FROM stock s
-- JOIN products p ON s.product_id = p.id
-- JOIN warehouses w ON s.warehouse_id = w.id
-- ORDER BY s.quantity ASC;

-- 2. Thẻ kho (Stock Card) - lịch sử 1 SP tại 1 kho
-- SELECT t.created_at, t.type, t.reference_type,
--        CASE WHEN t.quantity > 0 THEN t.quantity ELSE 0 END AS qty_in,
--        CASE WHEN t.quantity < 0 THEN ABS(t.quantity) ELSE 0 END AS qty_out,
--        SUM(t.quantity) OVER (ORDER BY t.created_at) AS running_balance,
--        t.note
-- FROM stock_transactions t
-- WHERE t.warehouse_id = 1 AND t.product_id = 5
-- ORDER BY t.created_at;

-- 3. SP dưới mức tồn tối thiểu
-- SELECT p.code, p.name, s.quantity, p.min_stock
-- FROM stock s JOIN products p ON s.product_id = p.id
-- WHERE s.quantity <= p.min_stock AND p.active = true;
