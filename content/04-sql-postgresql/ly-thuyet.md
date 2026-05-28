# SQL (PostgreSQL) - Lý Thuyết Chi Tiết

## Phần 1: Cơ Bản SQL

### 1.1 Kiểu dữ liệu PostgreSQL
| Kiểu | Mô tả | Ví dụ |
|------|-------|-------|
| INTEGER / INT | Số nguyên 4 bytes | 42 |
| BIGINT | Số nguyên 8 bytes | 9223372036854775807 |
| SERIAL | Số nguyên tự tăng | 1, 2, 3... |
| NUMERIC(p,s) | Số chính xác | NUMERIC(10,2) → 12345678.90 |
| REAL / FLOAT4 | Số thực 4 bytes | 3.14 |
| VARCHAR(n) | Chuỗi biến đổi | VARCHAR(255) |
| TEXT | Chuỗi không giới hạn | 'Nội dung dài...' |
| BOOLEAN | Đúng/Sai | TRUE, FALSE |
| DATE | Ngày | '2024-01-15' |
| TIMESTAMP | Ngày giờ | '2024-01-15 10:30:00' |
| TIMESTAMPTZ | Ngày giờ + timezone | '2024-01-15 10:30:00+07' |
| UUID | ID duy nhất toàn cầu | gen_random_uuid() |
| JSONB | JSON nhị phân | '{"key": "value"}' |
| ARRAY | Mảng | '{1,2,3}' hoặc ARRAY[1,2,3] |

### 1.2 DDL - Data Definition Language

```sql
-- Tạo bảng
CREATE TABLE nhan_vien (
    id SERIAL PRIMARY KEY,
    ho_ten VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE,
    phong_ban_id INTEGER REFERENCES phong_ban(id),
    luong NUMERIC(12, 2) DEFAULT 0 CHECK (luong >= 0),
    ngay_vao_lam DATE NOT NULL DEFAULT CURRENT_DATE,
    trang_thai BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- Thêm/Xóa cột
ALTER TABLE nhan_vien ADD COLUMN so_dien_thoai VARCHAR(15);
ALTER TABLE nhan_vien DROP COLUMN so_dien_thoai;
ALTER TABLE nhan_vien ALTER COLUMN ho_ten TYPE VARCHAR(200);

-- Index
CREATE INDEX idx_nhan_vien_phong_ban ON nhan_vien(phong_ban_id);
CREATE UNIQUE INDEX idx_nhan_vien_email ON nhan_vien(email);
CREATE INDEX idx_nhan_vien_ten_gin ON nhan_vien USING gin(ho_ten gin_trgm_ops);
```

### 1.3 DML - Data Manipulation Language

```sql
-- INSERT
INSERT INTO nhan_vien (ho_ten, email, phong_ban_id, luong)
VALUES ('Nguyễn Văn An', 'an@email.com', 1, 15000000);

-- INSERT nhiều dòng
INSERT INTO nhan_vien (ho_ten, email, phong_ban_id, luong) VALUES
    ('Trần Thị Bình', 'binh@email.com', 2, 12000000),
    ('Lê Văn Cường', 'cuong@email.com', 1, 18000000);

-- INSERT ... ON CONFLICT (UPSERT)
INSERT INTO nhan_vien (email, ho_ten, luong)
VALUES ('an@email.com', 'Nguyễn Văn An', 16000000)
ON CONFLICT (email) DO UPDATE SET luong = EXCLUDED.luong;

-- UPDATE
UPDATE nhan_vien SET luong = luong * 1.1 WHERE phong_ban_id = 1;

-- DELETE
DELETE FROM nhan_vien WHERE trang_thai = FALSE;
```

### 1.4 Truy Vấn SELECT

```sql
-- SELECT cơ bản
SELECT ho_ten, luong FROM nhan_vien
WHERE phong_ban_id = 1 AND luong > 10000000
ORDER BY luong DESC
LIMIT 10 OFFSET 0;

-- DISTINCT
SELECT DISTINCT phong_ban_id FROM nhan_vien;

-- LIKE và Pattern Matching
SELECT * FROM nhan_vien WHERE ho_ten LIKE 'Nguyễn%';
SELECT * FROM nhan_vien WHERE ho_ten ILIKE '%văn%'; -- Không phân biệt hoa/thường
SELECT * FROM nhan_vien WHERE ho_ten ~ '^[NT]'; -- Regex

-- IN, BETWEEN, IS NULL
SELECT * FROM nhan_vien WHERE phong_ban_id IN (1, 2, 3);
SELECT * FROM nhan_vien WHERE luong BETWEEN 10000000 AND 20000000;
SELECT * FROM nhan_vien WHERE email IS NOT NULL;

-- CASE WHEN
SELECT ho_ten, luong,
    CASE
        WHEN luong >= 20000000 THEN 'Cao'
        WHEN luong >= 10000000 THEN 'Trung bình'
        ELSE 'Thấp'
    END AS muc_luong
FROM nhan_vien;
```

---

## Phần 2: JOIN và Subquery

### 2.1 Các loại JOIN
```sql
-- INNER JOIN: Chỉ lấy dòng khớp ở cả hai bảng
SELECT nv.ho_ten, pb.ten_phong_ban
FROM nhan_vien nv
INNER JOIN phong_ban pb ON nv.phong_ban_id = pb.id;

-- LEFT JOIN: Lấy tất cả từ bảng trái + dòng khớp bảng phải
SELECT pb.ten_phong_ban, COUNT(nv.id) AS so_nhan_vien
FROM phong_ban pb
LEFT JOIN nhan_vien nv ON pb.id = nv.phong_ban_id
GROUP BY pb.ten_phong_ban;

-- RIGHT JOIN: Ngược lại LEFT JOIN
-- FULL OUTER JOIN: Lấy tất cả từ cả hai bảng
-- CROSS JOIN: Tích Cartesian

-- SELF JOIN: Tìm nhân viên cùng phòng ban
SELECT a.ho_ten, b.ho_ten AS dong_nghiep
FROM nhan_vien a
JOIN nhan_vien b ON a.phong_ban_id = b.phong_ban_id AND a.id < b.id;
```

### 2.2 Subquery
```sql
-- Subquery trong WHERE
SELECT * FROM nhan_vien
WHERE luong > (SELECT AVG(luong) FROM nhan_vien);

-- Subquery trong FROM (Derived Table)
SELECT phong_ban, avg_luong FROM (
    SELECT phong_ban_id AS phong_ban, AVG(luong) AS avg_luong
    FROM nhan_vien GROUP BY phong_ban_id
) sub WHERE avg_luong > 15000000;

-- EXISTS
SELECT * FROM phong_ban pb
WHERE EXISTS (
    SELECT 1 FROM nhan_vien nv
    WHERE nv.phong_ban_id = pb.id AND nv.luong > 20000000
);

-- ANY / ALL
SELECT * FROM nhan_vien
WHERE luong > ALL (SELECT luong FROM nhan_vien WHERE phong_ban_id = 2);
```

---

## Phần 3: Hàm Tập Hợp và GROUP BY

```sql
-- Hàm tập hợp
SELECT
    COUNT(*) AS tong_nv,
    COUNT(DISTINCT phong_ban_id) AS so_phong_ban,
    SUM(luong) AS tong_luong,
    AVG(luong) AS luong_tb,
    MIN(luong) AS luong_min,
    MAX(luong) AS luong_max,
    ROUND(AVG(luong), 2) AS luong_tb_lam_tron
FROM nhan_vien;

-- GROUP BY + HAVING
SELECT phong_ban_id, COUNT(*) AS so_nv, AVG(luong) AS luong_tb
FROM nhan_vien
GROUP BY phong_ban_id
HAVING COUNT(*) >= 3 AND AVG(luong) > 10000000
ORDER BY luong_tb DESC;

-- GROUPING SETS, ROLLUP, CUBE
SELECT phong_ban_id, EXTRACT(YEAR FROM ngay_vao_lam) AS nam, COUNT(*)
FROM nhan_vien
GROUP BY ROLLUP (phong_ban_id, EXTRACT(YEAR FROM ngay_vao_lam));
```

---

## Phần 4: Window Functions

```sql
-- ROW_NUMBER, RANK, DENSE_RANK
SELECT ho_ten, phong_ban_id, luong,
    ROW_NUMBER() OVER (PARTITION BY phong_ban_id ORDER BY luong DESC) AS stt,
    RANK() OVER (ORDER BY luong DESC) AS xep_hang,
    DENSE_RANK() OVER (ORDER BY luong DESC) AS xep_hang_lien_tuc
FROM nhan_vien;

-- LAG, LEAD: So sánh với dòng trước/sau
SELECT ho_ten, luong,
    LAG(luong) OVER (ORDER BY luong) AS luong_truoc,
    LEAD(luong) OVER (ORDER BY luong) AS luong_sau,
    luong - LAG(luong) OVER (ORDER BY luong) AS chenh_lech
FROM nhan_vien;

-- Tổng tích lũy (Running Total)
SELECT ho_ten, luong,
    SUM(luong) OVER (ORDER BY ngay_vao_lam) AS tong_tich_luy,
    AVG(luong) OVER (ORDER BY ngay_vao_lam ROWS BETWEEN 2 PRECEDING AND CURRENT ROW) AS ma_3
FROM nhan_vien;

-- NTILE: Chia thành nhóm
SELECT ho_ten, luong,
    NTILE(4) OVER (ORDER BY luong DESC) AS nhom_luong
FROM nhan_vien;

-- FIRST_VALUE, LAST_VALUE
SELECT ho_ten, phong_ban_id, luong,
    FIRST_VALUE(ho_ten) OVER (PARTITION BY phong_ban_id ORDER BY luong DESC) AS luong_cao_nhat
FROM nhan_vien;
```

---

## Phần 5: CTE và Recursive Query

```sql
-- Common Table Expression (CTE)
WITH nv_luong_cao AS (
    SELECT * FROM nhan_vien WHERE luong > 15000000
),
thong_ke_pb AS (
    SELECT phong_ban_id, COUNT(*) AS so_nv
    FROM nv_luong_cao
    GROUP BY phong_ban_id
)
SELECT pb.ten_phong_ban, tk.so_nv
FROM thong_ke_pb tk
JOIN phong_ban pb ON tk.phong_ban_id = pb.id;

-- Recursive CTE: Cây tổ chức
WITH RECURSIVE cay_to_chuc AS (
    -- Base case: Giám đốc (không có quản lý)
    SELECT id, ho_ten, quan_ly_id, 1 AS cap
    FROM nhan_vien WHERE quan_ly_id IS NULL

    UNION ALL

    -- Recursive: Nhân viên cấp dưới
    SELECT nv.id, nv.ho_ten, nv.quan_ly_id, ct.cap + 1
    FROM nhan_vien nv
    JOIN cay_to_chuc ct ON nv.quan_ly_id = ct.id
)
SELECT * FROM cay_to_chuc ORDER BY cap, ho_ten;
```

---

## Phần 6: PostgreSQL Nâng Cao

### 6.1 JSONB
```sql
-- Tạo bảng với JSONB
CREATE TABLE san_pham (
    id SERIAL PRIMARY KEY,
    ten VARCHAR(200),
    thuoc_tinh JSONB DEFAULT '{}'::jsonb
);

-- Truy vấn JSONB
SELECT ten, thuoc_tinh->>'mau_sac' AS mau
FROM san_pham
WHERE thuoc_tinh->>'loai' = 'Điện tử';

SELECT * FROM san_pham
WHERE thuoc_tinh @> '{"hang": "Samsung"}'::jsonb;

-- Cập nhật JSONB
UPDATE san_pham
SET thuoc_tinh = thuoc_tinh || '{"bao_hanh": "12 tháng"}'::jsonb
WHERE id = 1;
```

### 6.2 Transaction và Isolation Level
```sql
-- Transaction cơ bản
BEGIN;
UPDATE tai_khoan SET so_du = so_du - 1000000 WHERE id = 1;
UPDATE tai_khoan SET so_du = so_du + 1000000 WHERE id = 2;
COMMIT; -- hoặc ROLLBACK;

-- Isolation Levels
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;   -- Mặc định PostgreSQL
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
```

### 6.3 Stored Functions
```sql
CREATE OR REPLACE FUNCTION tang_luong(
    p_phong_ban_id INTEGER,
    p_phan_tram NUMERIC
) RETURNS INTEGER AS $$
DECLARE
    so_nv_cap_nhat INTEGER;
BEGIN
    UPDATE nhan_vien
    SET luong = luong * (1 + p_phan_tram / 100)
    WHERE phong_ban_id = p_phong_ban_id;

    GET DIAGNOSTICS so_nv_cap_nhat = ROW_COUNT;
    RETURN so_nv_cap_nhat;
END;
$$ LANGUAGE plpgsql;

-- Gọi function
SELECT tang_luong(1, 10); -- Tăng 10% cho phòng ban 1
```

### 6.4 Trigger
```sql
CREATE OR REPLACE FUNCTION ghi_log_luong()
RETURNS TRIGGER AS $$
BEGIN
    IF OLD.luong <> NEW.luong THEN
        INSERT INTO log_luong (nhan_vien_id, luong_cu, luong_moi, ngay_thay_doi)
        VALUES (NEW.id, OLD.luong, NEW.luong, NOW());
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_ghi_log_luong
AFTER UPDATE ON nhan_vien
FOR EACH ROW EXECUTE FUNCTION ghi_log_luong();
```

### 6.5 Performance và Index
```sql
-- Xem query plan
EXPLAIN ANALYZE
SELECT * FROM nhan_vien WHERE phong_ban_id = 1 AND luong > 10000000;

-- Partial Index
CREATE INDEX idx_nv_active ON nhan_vien(phong_ban_id) WHERE trang_thai = TRUE;

-- Expression Index
CREATE INDEX idx_nv_ten_lower ON nhan_vien(LOWER(ho_ten));

-- Composite Index
CREATE INDEX idx_nv_pb_luong ON nhan_vien(phong_ban_id, luong DESC);

-- Covering Index (INCLUDE)
CREATE INDEX idx_nv_cover ON nhan_vien(phong_ban_id) INCLUDE (ho_ten, luong);
```
