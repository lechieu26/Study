-- =============================================
-- MODULE SẢN XUẤT (MANUFACTURING) - DATABASE STRUCTURE & SAMPLE DATA
-- Yêu cầu: Chạy file erp-database.sql (30-erp-system) trước
-- Database: PostgreSQL
-- =============================================

-- =============================================
-- 1. BẢNG BOMS (Công thức sản xuất)
-- =============================================
CREATE TABLE IF NOT EXISTS boms (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,          -- BOM-001
    name VARCHAR(100),
    product_id BIGINT REFERENCES products(id), -- Thành phẩm
    version INT DEFAULT 1,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS bom_items (
    id BIGSERIAL PRIMARY KEY,
    bom_id BIGINT REFERENCES boms(id) ON DELETE CASCADE,
    material_id BIGINT REFERENCES products(id),-- Nguyên vật liệu
    quantity INT NOT NULL,                      -- SL NVL cho 1 TP
    unit VARCHAR(20)
);

-- =============================================
-- 2. BẢNG WORK ORDERS (Lệnh sản xuất)
-- =============================================
CREATE TABLE IF NOT EXISTS work_orders (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,          -- WO-2024-0001
    bom_id BIGINT REFERENCES boms(id),
    product_id BIGINT REFERENCES products(id),
    warehouse_id BIGINT,
    planned_quantity INT NOT NULL,
    actual_quantity INT DEFAULT 0,
    planned_start DATE,
    planned_end DATE,
    actual_start DATE,
    completed_date DATE,
    status VARCHAR(20) DEFAULT 'DRAFT',
    -- DRAFT → CONFIRMED → IN_PROGRESS → COMPLETED / CANCELLED
    note TEXT,
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- SAMPLE DATA - BOMS
-- =============================================
INSERT INTO boms (code, name, product_id, version) VALUES
('BOM-001', 'Công thức SIM 4G Mobifone', 1, 1),
('BOM-002', 'Công thức SIM 4G Viettel', 2, 1),
('BOM-003', 'Công thức eSIM Quốc tế', 3, 1);

INSERT INTO bom_items (bom_id, material_id, quantity, unit) VALUES
-- BOM-001: SIM 4G Mobifone = 1 Chip + 1 Phôi + 1 Bao bì + 1 Nhãn
(1, 5, 1, 'cái'),     -- Chip eSIM
(1, 6, 1, 'cái'),     -- Phôi nhựa SIM
(1, 7, 1, 'cái'),     -- Bao bì
(1, 8, 1, 'tờ'),      -- Nhãn mã vạch
-- BOM-002: SIM 4G Viettel (tương tự)
(2, 5, 1, 'cái'),
(2, 6, 1, 'cái'),
(2, 7, 1, 'cái'),
(2, 8, 1, 'tờ'),
-- BOM-003: eSIM (chỉ cần Chip + Bao bì)
(3, 5, 1, 'cái'),
(3, 7, 1, 'cái');

-- =============================================
-- SAMPLE DATA - WORK ORDERS
-- =============================================
INSERT INTO work_orders (code, bom_id, product_id, warehouse_id, planned_quantity, actual_quantity, planned_start, planned_end, actual_start, completed_date, status, created_by) VALUES
('WO-2024-0001', 1, 1, 2, 10000, 9800, '2024-06-01', '2024-06-07', '2024-06-01', '2024-06-06', 'COMPLETED', 'user1'),
('WO-2024-0002', 2, 2, 2, 5000, 5000, '2024-06-08', '2024-06-12', '2024-06-08', '2024-06-11', 'COMPLETED', 'user1'),
('WO-2024-0003', 1, 1, 1, 20000, 0, '2024-06-15', '2024-06-25', '2024-06-15', NULL, 'IN_PROGRESS', 'user2'),
('WO-2024-0004', 3, 3, 2, 5000, 0, '2024-07-01', '2024-07-05', NULL, NULL, 'CONFIRMED', 'user1'),
('WO-2024-0005', 1, 1, 1, 50000, 0, '2024-07-10', '2024-07-25', NULL, NULL, 'DRAFT', 'user2');

-- =============================================
-- CÂU QUERY MẪU
-- =============================================

-- 1. Xem BOM chi tiết (NVL cần cho 1 thành phẩm)
-- SELECT b.code AS bom, p_tp.name AS thanh_pham,
--        p_nvl.name AS nguyen_vat_lieu, bi.quantity, p_nvl.cost_price,
--        (bi.quantity * p_nvl.cost_price) AS material_cost
-- FROM bom_items bi
-- JOIN boms b ON bi.bom_id = b.id
-- JOIN products p_tp ON b.product_id = p_tp.id
-- JOIN products p_nvl ON bi.material_id = p_nvl.id
-- WHERE b.code = 'BOM-001';

-- 2. Tính NVL cần cho 1 lệnh SX
-- SELECT wo.code, p_nvl.name, bi.quantity * wo.planned_quantity AS required_qty
-- FROM work_orders wo
-- JOIN boms b ON wo.bom_id = b.id
-- JOIN bom_items bi ON bi.bom_id = b.id
-- JOIN products p_nvl ON bi.material_id = p_nvl.id
-- WHERE wo.code = 'WO-2024-0003';

-- 3. Hiệu suất sản xuất
-- SELECT code, product_id, planned_quantity, actual_quantity,
--        ROUND(actual_quantity::DECIMAL / planned_quantity * 100, 1) AS efficiency_pct
-- FROM work_orders
-- WHERE status = 'COMPLETED';

-- 4. Giá vốn SX 1 thành phẩm
-- SELECT b.code, p.name AS product,
--        SUM(bi.quantity * nvl.cost_price) AS unit_production_cost
-- FROM bom_items bi
-- JOIN boms b ON bi.bom_id = b.id
-- JOIN products p ON b.product_id = p.id
-- JOIN products nvl ON bi.material_id = nvl.id
-- GROUP BY b.id, b.code, p.name;
