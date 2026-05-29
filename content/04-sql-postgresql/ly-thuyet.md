# SQL (PostgreSQL) - Lý Thuyết Chi Tiết

## Giới thiệu

SQL (Structured Query Language) là ngôn ngữ chuẩn để quản lý và truy vấn cơ sở dữ liệu quan hệ. PostgreSQL là hệ quản trị CSDL mã nguồn mở mạnh mẽ nhất, hỗ trợ đầy đủ SQL chuẩn và nhiều tính năng nâng cao.

**Thứ tự thực thi của câu lệnh SELECT (quan trọng!):**

| Thứ tự | Clause | Mô tả |
|--------|--------|-------|
| 1 | `FROM` / `JOIN` | Xác định bảng nguồn, thực hiện join |
| 2 | `WHERE` | Lọc dòng trước khi nhóm |
| 3 | `GROUP BY` | Nhóm các dòng |
| 4 | `HAVING` | Lọc nhóm sau khi nhóm |
| 5 | `SELECT` | Chọn cột, tính toán expression |
| 6 | `DISTINCT` | Loại bỏ trùng lặp |
| 7 | `ORDER BY` | Sắp xếp kết quả |
| 8 | `LIMIT` / `OFFSET` | Giới hạn số dòng trả về |

> **Phỏng vấn thường hỏi:** Tại sao không thể dùng alias trong WHERE? — Vì WHERE thực thi **trước** SELECT. Alias chỉ khả dụng từ ORDER BY trở đi.

---

## Phần 1: Cơ Bản SQL

### 1.1 Kiểu dữ liệu PostgreSQL

| Kiểu | Mô tả | Ví dụ | Khi nào dùng |
|------|-------|-------|-------------|
| `INTEGER` / `INT` | Số nguyên 4 bytes | 42 | Đa số trường hợp |
| `BIGINT` | Số nguyên 8 bytes | 9223372036854775807 | ID lớn, timestamp |
| `SERIAL` / `BIGSERIAL` | Số tự tăng | 1, 2, 3... | Primary key (legacy) |
| `IDENTITY` | Số tự tăng (SQL chuẩn) | `GENERATED ALWAYS AS IDENTITY` | PK (khuyên dùng) |
| `NUMERIC(p,s)` | Số chính xác | NUMERIC(10,2) → 12345678.90 | Tiền tệ, tài chính |
| `REAL` / `DOUBLE PRECISION` | Số thực xấp xỉ | 3.14 | Khoa học, thống kê |
| `VARCHAR(n)` | Chuỗi giới hạn | VARCHAR(255) | Tên, email |
| `TEXT` | Chuỗi không giới hạn | 'Nội dung dài...' | Mô tả, nội dung |
| `BOOLEAN` | Đúng/Sai | TRUE, FALSE | Cờ trạng thái |
| `DATE` | Ngày | '2024-01-15' | Ngày sinh, ngày tạo |
| `TIMESTAMP` | Ngày giờ | '2024-01-15 10:30:00' | Thời điểm cục bộ |
| `TIMESTAMPTZ` | Ngày giờ + timezone | '2024-01-15 10:30:00+07' | **Luôn dùng cho production** |
| `UUID` | ID duy nhất toàn cầu | `gen_random_uuid()` | Distributed systems |
| `JSONB` | JSON nhị phân, indexable | '{"key": "value"}' | Schema linh hoạt |
| `ARRAY` | Mảng | ARRAY[1,2,3] | Tags, multi-value |
| `INET` / `CIDR` | Địa chỉ IP / mạng | '192.168.1.0/24' | Networking |

> **Best Practice:** Dùng `TIMESTAMPTZ` thay vì `TIMESTAMP` cho mọi trường thời gian. Dùng `TEXT` thay vì `VARCHAR` nếu không cần giới hạn cụ thể (PostgreSQL không có khác biệt hiệu năng). Dùng `NUMERIC` cho tiền tệ, **không bao giờ** dùng `FLOAT/DOUBLE`.

### 1.2 DDL - Data Definition Language

```sql
-- Tạo bảng với đầy đủ constraints
CREATE TABLE nhan_vien (
    id SERIAL PRIMARY KEY,
    ho_ten VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phong_ban_id INTEGER REFERENCES phong_ban(id) ON DELETE SET NULL,
    quan_ly_id INTEGER REFERENCES nhan_vien(id),
    luong NUMERIC(12, 2) DEFAULT 0 CHECK (luong >= 0),
    ngay_vao_lam DATE NOT NULL DEFAULT CURRENT_DATE,
    trang_thai BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- Constraints giải thích
-- PRIMARY KEY = UNIQUE + NOT NULL, tự tạo index
-- FOREIGN KEY (REFERENCES) = tham chiếu bảng khác
-- ON DELETE SET NULL / CASCADE / RESTRICT = hành vi khi xóa parent
-- CHECK = kiểm tra điều kiện
-- UNIQUE = không trùng giá trị (cho phép NULL)
-- NOT NULL = bắt buộc có giá trị

-- Thêm/Xóa/Sửa cột
ALTER TABLE nhan_vien ADD COLUMN so_dien_thoai VARCHAR(15);
ALTER TABLE nhan_vien DROP COLUMN so_dien_thoai;
ALTER TABLE nhan_vien ALTER COLUMN ho_ten TYPE VARCHAR(200);
ALTER TABLE nhan_vien ALTER COLUMN email SET NOT NULL;
ALTER TABLE nhan_vien ADD CONSTRAINT chk_luong CHECK (luong >= 0);

-- Index — tăng tốc truy vấn
CREATE INDEX idx_nv_phong_ban ON nhan_vien(phong_ban_id);
CREATE UNIQUE INDEX idx_nv_email ON nhan_vien(email);
CREATE INDEX idx_nv_ten_gin ON nhan_vien USING gin(ho_ten gin_trgm_ops); -- Full-text search
CREATE INDEX idx_nv_created ON nhan_vien(created_at DESC); -- Sorted index
```

> **Quy tắc đặt tên:** Bảng: snake_case số ít (`nhan_vien`). Cột: snake_case (`ho_ten`). Index: `idx_<bảng>_<cột>`. FK: `fk_<bảng>_<bảng_ref>`.

### 1.3 DML - Data Manipulation Language

```sql
-- INSERT — thêm dữ liệu
INSERT INTO nhan_vien (ho_ten, email, phong_ban_id, luong)
VALUES ('Nguyễn Văn An', 'an@email.com', 1, 15000000);

-- INSERT nhiều dòng — hiệu quả hơn INSERT từng dòng
INSERT INTO nhan_vien (ho_ten, email, phong_ban_id, luong) VALUES
    ('Trần Thị Bình', 'binh@email.com', 2, 12000000),
    ('Lê Văn Cường', 'cuong@email.com', 1, 18000000);

-- INSERT ... ON CONFLICT (UPSERT) — INSERT hoặc UPDATE nếu conflict
INSERT INTO nhan_vien (email, ho_ten, luong)
VALUES ('an@email.com', 'Nguyễn Văn An', 16000000)
ON CONFLICT (email) DO UPDATE
    SET luong = EXCLUDED.luong,
        updated_at = NOW();
-- EXCLUDED = row đang cố INSERT

-- INSERT ... RETURNING — lấy lại dữ liệu vừa thêm
INSERT INTO nhan_vien (ho_ten, email, luong)
VALUES ('Test', 'test@email.com', 10000000)
RETURNING id, ho_ten, created_at;

-- UPDATE — cập nhật dữ liệu
UPDATE nhan_vien
SET luong = luong * 1.1,
    updated_at = NOW()
WHERE phong_ban_id = 1;

-- UPDATE với FROM (join update)
UPDATE nhan_vien nv
SET luong = luong * 1.15
FROM phong_ban pb
WHERE nv.phong_ban_id = pb.id AND pb.ten_phong_ban = 'Kỹ thuật';

-- DELETE — xóa dữ liệu
DELETE FROM nhan_vien WHERE trang_thai = FALSE;
-- Cẩn thận: DELETE không có WHERE sẽ xóa TOÀN BỘ bảng!

-- TRUNCATE — xóa toàn bộ (nhanh hơn DELETE, reset sequence)
TRUNCATE TABLE log_lich_su RESTART IDENTITY CASCADE;
```

### 1.4 Truy Vấn SELECT

```sql
-- SELECT cơ bản
SELECT ho_ten, luong
FROM nhan_vien
WHERE phong_ban_id = 1 AND luong > 10000000
ORDER BY luong DESC
LIMIT 10 OFFSET 0;

-- DISTINCT — loại trùng
SELECT DISTINCT phong_ban_id FROM nhan_vien;
SELECT DISTINCT ON (phong_ban_id) ho_ten, phong_ban_id, luong
FROM nhan_vien ORDER BY phong_ban_id, luong DESC;
-- DISTINCT ON: lấy 1 dòng cho mỗi giá trị phong_ban_id (đặc biệt PostgreSQL)

-- LIKE, ILIKE, và Pattern Matching
SELECT * FROM nhan_vien WHERE ho_ten LIKE 'Nguyễn%';    -- Bắt đầu bằng
SELECT * FROM nhan_vien WHERE ho_ten LIKE '%Văn%';       -- Chứa
SELECT * FROM nhan_vien WHERE ho_ten ILIKE '%văn%';      -- Case-insensitive
SELECT * FROM nhan_vien WHERE ho_ten ~ '^[NT]';          -- Regex
SELECT * FROM nhan_vien WHERE ho_ten SIMILAR TO '(Nguyễn|Trần)%'; -- SQL regex

-- IN, BETWEEN, IS NULL
SELECT * FROM nhan_vien WHERE phong_ban_id IN (1, 2, 3);
SELECT * FROM nhan_vien WHERE phong_ban_id NOT IN (4, 5);
SELECT * FROM nhan_vien WHERE luong BETWEEN 10000000 AND 20000000;
SELECT * FROM nhan_vien WHERE email IS NOT NULL;
SELECT * FROM nhan_vien WHERE quan_ly_id IS NULL; -- Tìm trưởng phòng

-- CASE WHEN — Biểu thức điều kiện
SELECT ho_ten, luong,
    CASE
        WHEN luong >= 20000000 THEN 'Cao'
        WHEN luong >= 15000000 THEN 'Khá'
        WHEN luong >= 10000000 THEN 'Trung bình'
        ELSE 'Thấp'
    END AS muc_luong
FROM nhan_vien;

-- COALESCE — giá trị mặc định cho NULL
SELECT ho_ten, COALESCE(email, 'Chưa có email') AS email
FROM nhan_vien;

-- NULLIF — trả về NULL nếu 2 giá trị bằng nhau (tránh chia cho 0)
SELECT ho_ten, luong / NULLIF(so_gio_lam, 0) AS luong_gio
FROM nhan_vien;

-- Hàm xử lý chuỗi
SELECT
    UPPER(ho_ten),                           -- CHỮ HOA
    LOWER(ho_ten),                           -- chữ thường
    INITCAP(ho_ten),                         -- Viết Hoa Đầu Từ
    LENGTH(ho_ten),                          -- Độ dài
    TRIM(ho_ten),                            -- Xóa khoảng trắng
    SUBSTRING(ho_ten FROM 1 FOR 3),          -- Cắt chuỗi
    REPLACE(ho_ten, 'Văn', 'Thị'),          -- Thay thế
    CONCAT(ho_ten, ' - ', email),            -- Nối chuỗi
    LEFT(ho_ten, 5),                         -- 5 ký tự đầu
    RIGHT(ho_ten, 5),                        -- 5 ký tự cuối
    SPLIT_PART(email, '@', 2)               -- Tách chuỗi
FROM nhan_vien;

-- Hàm xử lý ngày tháng
SELECT
    CURRENT_DATE,                            -- Ngày hiện tại
    CURRENT_TIMESTAMP,                       -- Thời điểm hiện tại
    NOW(),                                   -- Giống CURRENT_TIMESTAMP
    EXTRACT(YEAR FROM ngay_vao_lam),         -- Lấy năm
    EXTRACT(MONTH FROM ngay_vao_lam),        -- Lấy tháng
    DATE_TRUNC('month', ngay_vao_lam),       -- Cắt theo tháng
    AGE(NOW(), ngay_vao_lam),                -- Thời gian làm việc
    ngay_vao_lam + INTERVAL '1 year',        -- Cộng 1 năm
    DATE_PART('dow', ngay_vao_lam)           -- Ngày trong tuần (0=CN)
FROM nhan_vien;
```

---

## Phần 2: JOIN và Subquery

### 2.1 Các loại JOIN

**Minh họa JOIN:**
```
INNER JOIN:  Chỉ phần giao (A ∩ B)
LEFT JOIN:   Tất cả A + phần giao B
RIGHT JOIN:  Phần giao A + tất cả B
FULL OUTER:  Tất cả A + tất cả B (A ∪ B)
CROSS JOIN:  Tích Cartesian (mọi cặp)
```

| Loại JOIN | Kết quả | Khi nào dùng |
|-----------|---------|-------------|
| `INNER JOIN` | Chỉ dòng khớp cả hai bảng | Lấy dữ liệu có quan hệ |
| `LEFT JOIN` | Tất cả từ trái + khớp phải (NULL nếu không khớp) | Đếm bao gồm cả "không có" |
| `RIGHT JOIN` | Ngược LEFT JOIN | Hiếm dùng, đổi thứ tự bảng |
| `FULL OUTER JOIN` | Tất cả từ cả hai bảng | So sánh 2 dataset |
| `CROSS JOIN` | Tích Cartesian (mỗi cặp) | Tạo tổ hợp |
| `SELF JOIN` | Bảng join với chính nó | Quan hệ cha-con, cùng bảng |

```sql
-- INNER JOIN: Nhân viên và phòng ban
SELECT nv.ho_ten, pb.ten_phong_ban, nv.luong
FROM nhan_vien nv
INNER JOIN phong_ban pb ON nv.phong_ban_id = pb.id;

-- LEFT JOIN: Đếm nhân viên mỗi phòng ban (bao gồm phòng 0 nhân viên)
SELECT pb.ten_phong_ban, COUNT(nv.id) AS so_nhan_vien
FROM phong_ban pb
LEFT JOIN nhan_vien nv ON pb.id = nv.phong_ban_id
GROUP BY pb.ten_phong_ban
ORDER BY so_nhan_vien DESC;

-- LEFT JOIN + IS NULL: Tìm phòng ban KHÔNG có nhân viên
SELECT pb.ten_phong_ban
FROM phong_ban pb
LEFT JOIN nhan_vien nv ON pb.id = nv.phong_ban_id
WHERE nv.id IS NULL;

-- SELF JOIN: Nhân viên và quản lý
SELECT nv.ho_ten AS nhan_vien, ql.ho_ten AS quan_ly
FROM nhan_vien nv
LEFT JOIN nhan_vien ql ON nv.quan_ly_id = ql.id;

-- SELF JOIN: Tìm cặp đồng nghiệp cùng phòng
SELECT a.ho_ten, b.ho_ten AS dong_nghiep
FROM nhan_vien a
JOIN nhan_vien b ON a.phong_ban_id = b.phong_ban_id AND a.id < b.id;

-- Multi-table JOIN
SELECT nv.ho_ten, pb.ten_phong_ban, da.ten_du_an, pc.vai_tro
FROM nhan_vien nv
JOIN phong_ban pb ON nv.phong_ban_id = pb.id
JOIN phan_cong pc ON nv.id = pc.nhan_vien_id
JOIN du_an da ON pc.du_an_id = da.id;
```

> **Performance tip:** JOIN trên cột đã có index sẽ nhanh hơn nhiều. `INNER JOIN` thường nhanh hơn `LEFT JOIN` vì optimizer có nhiều lựa chọn hơn.

### 2.2 Subquery

```sql
-- Subquery trong WHERE — Scalar subquery
SELECT * FROM nhan_vien
WHERE luong > (SELECT AVG(luong) FROM nhan_vien);

-- Subquery trong WHERE — IN
SELECT * FROM nhan_vien
WHERE phong_ban_id IN (
    SELECT id FROM phong_ban WHERE ten_phong_ban LIKE 'K%'
);

-- Subquery trong FROM (Derived Table)
SELECT phong_ban, avg_luong
FROM (
    SELECT phong_ban_id AS phong_ban, AVG(luong) AS avg_luong
    FROM nhan_vien
    GROUP BY phong_ban_id
) sub
WHERE avg_luong > 15000000;

-- EXISTS — hiệu quả hơn IN cho dataset lớn
SELECT pb.ten_phong_ban
FROM phong_ban pb
WHERE EXISTS (
    SELECT 1 FROM nhan_vien nv
    WHERE nv.phong_ban_id = pb.id AND nv.luong > 20000000
);

-- NOT EXISTS — Tìm phòng ban không có nhân viên lương cao
SELECT pb.ten_phong_ban
FROM phong_ban pb
WHERE NOT EXISTS (
    SELECT 1 FROM nhan_vien nv
    WHERE nv.phong_ban_id = pb.id AND nv.luong > 20000000
);

-- Correlated Subquery — subquery phụ thuộc vào outer query
SELECT nv.ho_ten, nv.luong, nv.phong_ban_id
FROM nhan_vien nv
WHERE nv.luong = (
    SELECT MAX(luong) FROM nhan_vien WHERE phong_ban_id = nv.phong_ban_id
);

-- ANY / ALL
SELECT * FROM nhan_vien
WHERE luong > ALL (SELECT luong FROM nhan_vien WHERE phong_ban_id = 2);
-- Lương cao hơn TẤT CẢ nhân viên phòng 2

SELECT * FROM nhan_vien
WHERE luong > ANY (SELECT luong FROM nhan_vien WHERE phong_ban_id = 2);
-- Lương cao hơn BẤT KỲ nhân viên nào phòng 2
```

> **EXISTS vs IN:** `EXISTS` dừng ngay khi tìm thấy 1 dòng khớp → nhanh hơn cho subquery trả về nhiều dòng. `IN` phải load toàn bộ subquery result vào bộ nhớ.

---

## Phần 3: Hàm Tập Hợp và GROUP BY

```sql
-- Các hàm tập hợp (Aggregate Functions)
SELECT
    COUNT(*)                        AS tong_nv,           -- Đếm tất cả dòng
    COUNT(DISTINCT phong_ban_id)    AS so_phong_ban,      -- Đếm giá trị unique
    COUNT(email)                    AS co_email,          -- Đếm NOT NULL
    SUM(luong)                      AS tong_luong,
    AVG(luong)                      AS luong_tb,
    MIN(luong)                      AS luong_min,
    MAX(luong)                      AS luong_max,
    ROUND(AVG(luong), 0)            AS luong_tb_lam_tron,
    PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY luong) AS median_luong, -- Trung vị
    STRING_AGG(ho_ten, ', ' ORDER BY ho_ten) AS ds_ten    -- Nối chuỗi
FROM nhan_vien
WHERE trang_thai = TRUE;

-- GROUP BY + HAVING
SELECT phong_ban_id,
    COUNT(*) AS so_nv,
    ROUND(AVG(luong), 0) AS luong_tb,
    MIN(luong) AS luong_thap_nhat,
    MAX(luong) AS luong_cao_nhat
FROM nhan_vien
WHERE trang_thai = TRUE
GROUP BY phong_ban_id
HAVING COUNT(*) >= 2 AND AVG(luong) > 10000000
ORDER BY luong_tb DESC;

-- GROUP BY nhiều cột
SELECT phong_ban_id,
    EXTRACT(YEAR FROM ngay_vao_lam) AS nam,
    COUNT(*) AS so_nv,
    SUM(luong) AS tong_luong
FROM nhan_vien
GROUP BY phong_ban_id, EXTRACT(YEAR FROM ngay_vao_lam)
ORDER BY phong_ban_id, nam;

-- ROLLUP — Tổng phụ theo cấp bậc
SELECT
    COALESCE(pb.ten_phong_ban, '== TỔNG CỘNG ==') AS phong_ban,
    COUNT(*) AS so_nv,
    SUM(luong) AS tong_luong
FROM nhan_vien nv
JOIN phong_ban pb ON nv.phong_ban_id = pb.id
GROUP BY ROLLUP (pb.ten_phong_ban)
ORDER BY pb.ten_phong_ban NULLS LAST;
-- ROLLUP tạo thêm dòng tổng cộng (grand total)
```

> **WHERE vs HAVING:** `WHERE` lọc dòng **trước** GROUP BY (không thể dùng aggregate). `HAVING` lọc nhóm **sau** GROUP BY (dùng được aggregate).

---

## Phần 4: Window Functions (Hàm Cửa Sổ)

Window Functions tính toán trên một "cửa sổ" các dòng liên quan đến dòng hiện tại, **mà không gộp dòng** (khác GROUP BY).

**Cú pháp:** `function() OVER (PARTITION BY ... ORDER BY ... ROWS/RANGE ...)`

| Hàm | Mô tả | Ví dụ sử dụng |
|-----|-------|--------------|
| `ROW_NUMBER()` | Số thứ tự 1, 2, 3... (không trùng) | Đánh STT, phân trang |
| `RANK()` | Xếp hạng (trùng → nhảy số) | 1, 2, 2, 4 |
| `DENSE_RANK()` | Xếp hạng (trùng → không nhảy) | 1, 2, 2, 3 |
| `NTILE(n)` | Chia thành n nhóm đều | Chia quartile |
| `LAG(col, n)` | Giá trị n dòng trước | So sánh tháng trước |
| `LEAD(col, n)` | Giá trị n dòng sau | Dự đoán tiếp theo |
| `FIRST_VALUE()` | Giá trị đầu tiên trong window | Top 1 mỗi nhóm |
| `LAST_VALUE()` | Giá trị cuối cùng | Cần RANGE UNBOUNDED |
| `SUM() OVER` | Tổng tích lũy | Running total |
| `AVG() OVER` | Trung bình trượt | Moving average |

```sql
-- ROW_NUMBER, RANK, DENSE_RANK
SELECT ho_ten, phong_ban_id, luong,
    ROW_NUMBER() OVER (PARTITION BY phong_ban_id ORDER BY luong DESC) AS stt,
    RANK() OVER (ORDER BY luong DESC) AS xep_hang,
    DENSE_RANK() OVER (ORDER BY luong DESC) AS xep_hang_lien_tuc
FROM nhan_vien;

-- Top N mỗi nhóm (dùng ROW_NUMBER + subquery/CTE)
-- Ví dụ: Top 2 nhân viên lương cao nhất mỗi phòng ban
SELECT * FROM (
    SELECT ho_ten, phong_ban_id, luong,
        ROW_NUMBER() OVER (PARTITION BY phong_ban_id ORDER BY luong DESC) AS rn
    FROM nhan_vien
) sub WHERE rn <= 2;

-- LAG, LEAD: So sánh với dòng trước/sau
SELECT ho_ten, luong,
    LAG(luong, 1, 0) OVER (ORDER BY luong) AS luong_truoc,
    LEAD(luong, 1, 0) OVER (ORDER BY luong) AS luong_sau,
    luong - LAG(luong) OVER (ORDER BY luong) AS chenh_lech
FROM nhan_vien;

-- Tổng tích lũy (Running Total)
SELECT ho_ten, luong,
    SUM(luong) OVER (ORDER BY ngay_vao_lam) AS tong_tich_luy,
    SUM(luong) OVER () AS tong_tat_ca,
    ROUND(luong * 100.0 / SUM(luong) OVER (), 2) AS phan_tram_luong
FROM nhan_vien;

-- Moving Average (Trung bình trượt 3 dòng)
SELECT ho_ten, ngay_vao_lam, luong,
    ROUND(AVG(luong) OVER (
        ORDER BY ngay_vao_lam
        ROWS BETWEEN 2 PRECEDING AND CURRENT ROW
    ), 0) AS ma_3
FROM nhan_vien;

-- NTILE: Chia thành nhóm
SELECT ho_ten, luong,
    NTILE(4) OVER (ORDER BY luong DESC) AS nhom_luong
    -- 1 = Top 25%, 2 = 25-50%, 3 = 50-75%, 4 = Bottom 25%
FROM nhan_vien;

-- FIRST_VALUE: Người lương cao nhất mỗi phòng
SELECT ho_ten, phong_ban_id, luong,
    FIRST_VALUE(ho_ten) OVER (
        PARTITION BY phong_ban_id ORDER BY luong DESC
    ) AS nguoi_luong_cao_nhat
FROM nhan_vien;
```

> **Window Frame:** Mặc định `RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW`. Dùng `ROWS BETWEEN ... AND ...` để kiểm soát chính xác. `LAST_VALUE` cần `ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING` để hoạt động đúng.

---

## Phần 5: CTE và Recursive Query

### 5.1 CTE (Common Table Expression)

CTE giúp viết query phức tạp **dễ đọc** hơn, chia thành từng bước logic.

```sql
-- CTE cơ bản — dễ đọc hơn subquery lồng
WITH nv_luong_cao AS (
    SELECT * FROM nhan_vien WHERE luong > 15000000
),
thong_ke_pb AS (
    SELECT phong_ban_id, COUNT(*) AS so_nv, AVG(luong) AS luong_tb
    FROM nv_luong_cao
    GROUP BY phong_ban_id
)
SELECT pb.ten_phong_ban, tk.so_nv, ROUND(tk.luong_tb, 0) AS luong_tb
FROM thong_ke_pb tk
JOIN phong_ban pb ON tk.phong_ban_id = pb.id
ORDER BY tk.so_nv DESC;

-- CTE với INSERT/UPDATE/DELETE
WITH nv_nghi_viec AS (
    DELETE FROM nhan_vien
    WHERE trang_thai = FALSE
    RETURNING *
)
INSERT INTO nhan_vien_da_nghi SELECT * FROM nv_nghi_viec;
```

### 5.2 Recursive CTE

Dùng cho dữ liệu dạng cây (hierarchy): tổ chức nhân sự, danh mục sản phẩm, menu.

```sql
-- Recursive CTE: Cây tổ chức nhân sự
WITH RECURSIVE cay_to_chuc AS (
    -- Base case: Giám đốc (không có quản lý)
    SELECT id, ho_ten, quan_ly_id, 1 AS cap,
           ho_ten::TEXT AS duong_dan
    FROM nhan_vien
    WHERE quan_ly_id IS NULL

    UNION ALL

    -- Recursive: Nhân viên cấp dưới
    SELECT nv.id, nv.ho_ten, nv.quan_ly_id, ct.cap + 1,
           ct.duong_dan || ' → ' || nv.ho_ten
    FROM nhan_vien nv
    JOIN cay_to_chuc ct ON nv.quan_ly_id = ct.id
)
SELECT
    REPEAT('  ', cap - 1) || ho_ten AS to_chuc,
    cap,
    duong_dan
FROM cay_to_chuc
ORDER BY duong_dan;

-- Recursive: Dãy số Fibonacci
WITH RECURSIVE fib AS (
    SELECT 1 AS n, 0::BIGINT AS fib_n, 1::BIGINT AS fib_next
    UNION ALL
    SELECT n + 1, fib_next, fib_n + fib_next FROM fib WHERE n < 20
)
SELECT n, fib_n FROM fib;
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
SELECT ten,
    thuoc_tinh->>'mau_sac' AS mau,         -- Lấy text value
    thuoc_tinh->'kich_thuoc' AS kich_thuoc, -- Lấy JSON value
    thuoc_tinh#>>'{dia_chi, thanh_pho}' AS tp -- Nested path
FROM san_pham
WHERE thuoc_tinh->>'loai' = 'Điện tử';

-- Kiểm tra chứa (@>)
SELECT * FROM san_pham
WHERE thuoc_tinh @> '{"hang": "Samsung"}'::jsonb;

-- Kiểm tra key tồn tại (?)
SELECT * FROM san_pham
WHERE thuoc_tinh ? 'bao_hanh';

-- Cập nhật JSONB
UPDATE san_pham
SET thuoc_tinh = thuoc_tinh || '{"bao_hanh": "12 tháng"}'::jsonb
WHERE id = 1;

-- Xóa key từ JSONB
UPDATE san_pham
SET thuoc_tinh = thuoc_tinh - 'bao_hanh'
WHERE id = 1;

-- Index cho JSONB (GIN)
CREATE INDEX idx_sp_thuoc_tinh ON san_pham USING gin(thuoc_tinh);
```

### 6.2 Transaction và Isolation Level

| Isolation Level | Dirty Read | Non-Repeatable Read | Phantom Read | Serialization Anomaly |
|----------------|-----------|-------------------|-------------|---------------------|
| Read Uncommitted* | Có | Có | Có | Có |
| Read Committed (mặc định PG) | Không | Có | Có | Có |
| Repeatable Read | Không | Không | Không** | Có |
| Serializable | Không | Không | Không | Không |

> (*) PostgreSQL xử lý Read Uncommitted như Read Committed.
> (**) PostgreSQL ngăn phantom read ở Repeatable Read (khác SQL chuẩn).

```sql
-- Transaction cơ bản
BEGIN;
    UPDATE tai_khoan SET so_du = so_du - 1000000 WHERE id = 1;
    UPDATE tai_khoan SET so_du = so_du + 1000000 WHERE id = 2;
    -- Nếu có lỗi: ROLLBACK;
COMMIT;

-- SAVEPOINT — checkpoint trong transaction
BEGIN;
    INSERT INTO don_hang VALUES (1, 'Sản phẩm A', 100000);
    SAVEPOINT sp1;
    INSERT INTO don_hang VALUES (2, 'Sản phẩm B', 200000);
    ROLLBACK TO sp1;  -- Chỉ rollback lệnh sau SAVEPOINT
COMMIT;  -- Sản phẩm A vẫn được commit

-- Isolation Levels
BEGIN ISOLATION LEVEL SERIALIZABLE;
    -- Đảm bảo hoàn toàn tuần tự
COMMIT;
```

### 6.3 Stored Functions

```sql
CREATE OR REPLACE FUNCTION tang_luong(
    p_phong_ban_id INTEGER,
    p_phan_tram NUMERIC
) RETURNS TABLE(ho_ten VARCHAR, luong_cu NUMERIC, luong_moi NUMERIC) AS $$
BEGIN
    RETURN QUERY
    UPDATE nhan_vien
    SET luong = luong * (1 + p_phan_tram / 100)
    WHERE phong_ban_id = p_phong_ban_id
    RETURNING nhan_vien.ho_ten, luong / (1 + p_phan_tram / 100), nhan_vien.luong;
END;
$$ LANGUAGE plpgsql;

-- Gọi function
SELECT * FROM tang_luong(1, 10);
```

### 6.4 Trigger

```sql
-- Function cho trigger
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

-- Tạo trigger
CREATE TRIGGER trg_ghi_log_luong
AFTER UPDATE ON nhan_vien
FOR EACH ROW
EXECUTE FUNCTION ghi_log_luong();

-- Auto-update updated_at
CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_timestamp
BEFORE UPDATE ON nhan_vien
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();
```

### 6.5 Performance và Index

**Loại Index:**

| Loại | Khi nào dùng | Ví dụ |
|------|-------------|-------|
| B-Tree (mặc định) | `=`, `<`, `>`, `BETWEEN`, `ORDER BY` | `CREATE INDEX idx ON t(col)` |
| Hash | Chỉ `=` (nhanh hơn B-Tree cho `=`) | `CREATE INDEX idx ON t USING hash(col)` |
| GIN | Full-text search, JSONB, Array | `USING gin(col)` |
| GiST | Geometry, range, nearest-neighbor | `USING gist(col)` |
| BRIN | Dữ liệu tự nhiên có thứ tự (timestamp) | `USING brin(created_at)` |

```sql
-- Xem query plan — LUÔN dùng EXPLAIN ANALYZE trước khi tối ưu
EXPLAIN ANALYZE
SELECT * FROM nhan_vien WHERE phong_ban_id = 1 AND luong > 10000000;

-- Partial Index — chỉ index dòng thỏa điều kiện
CREATE INDEX idx_nv_active ON nhan_vien(phong_ban_id) WHERE trang_thai = TRUE;

-- Expression Index — index trên biểu thức
CREATE INDEX idx_nv_ten_lower ON nhan_vien(LOWER(ho_ten));

-- Composite Index — nhiều cột (thứ tự cột quan trọng!)
CREATE INDEX idx_nv_pb_luong ON nhan_vien(phong_ban_id, luong DESC);
-- Query WHERE phong_ban_id = 1 → dùng index
-- Query WHERE luong > 1000000 → KHÔNG dùng index (cột đầu phải có trong WHERE)

-- Covering Index (INCLUDE) — tránh table lookup
CREATE INDEX idx_nv_pb_cover ON nhan_vien(phong_ban_id) INCLUDE (ho_ten, luong);
-- Query SELECT ho_ten, luong WHERE phong_ban_id = 1 → Index-Only Scan

-- Kiểm tra index usage
SELECT indexname, idx_scan, idx_tup_read
FROM pg_stat_user_indexes WHERE schemaname = 'public';

-- Tìm index không dùng
SELECT indexname FROM pg_stat_user_indexes WHERE idx_scan = 0;
```

> **Nguyên tắc tối ưu:**
> 1. **Đo trước khi tối ưu** — dùng `EXPLAIN ANALYZE`
> 2. **Index cột trong WHERE, JOIN, ORDER BY** — nhưng không index quá nhiều (chậm INSERT/UPDATE)
> 3. **Composite index** — cột equality trước, cột range sau
> 4. **LIMIT sớm** — giới hạn dữ liệu trả về
> 5. **Tránh SELECT *** — chỉ lấy cột cần thiết

---

## Phần 7: Các Pattern SQL Phổ Biến

### 7.1 Pagination (Phân trang)

```sql
-- Offset-based (đơn giản nhưng chậm với offset lớn)
SELECT * FROM nhan_vien
ORDER BY id LIMIT 10 OFFSET 20;

-- Keyset pagination (nhanh hơn cho dataset lớn)
SELECT * FROM nhan_vien
WHERE id > 20  -- ID cuối của trang trước
ORDER BY id LIMIT 10;
```

### 7.2 Upsert và Merge

```sql
-- UPSERT: Insert hoặc Update
INSERT INTO nhan_vien (email, ho_ten, luong)
VALUES ('an@email.com', 'An', 15000000)
ON CONFLICT (email)
DO UPDATE SET
    luong = GREATEST(nhan_vien.luong, EXCLUDED.luong),
    updated_at = NOW();

-- DO NOTHING: Bỏ qua nếu conflict
INSERT INTO nhan_vien (email, ho_ten, luong)
VALUES ('an@email.com', 'An', 15000000)
ON CONFLICT (email) DO NOTHING;
```

### 7.3 Gap and Island

```sql
-- Tìm khoảng trống trong dãy ID
WITH all_ids AS (
    SELECT generate_series(1, (SELECT MAX(id) FROM nhan_vien)) AS id
)
SELECT a.id AS missing_id
FROM all_ids a
LEFT JOIN nhan_vien nv ON a.id = nv.id
WHERE nv.id IS NULL;
```
