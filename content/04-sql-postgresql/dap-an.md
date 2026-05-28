# SQL (PostgreSQL) - Đáp Án

## Bài 1: Truy Vấn Cơ Bản và JOIN

### Câu 1: Nhân viên + Phòng ban
```sql
SELECT nv.ho_ten, nv.luong, pb.ten_phong_ban
FROM nhan_vien nv
INNER JOIN phong_ban pb ON nv.phong_ban_id = pb.id
ORDER BY nv.luong DESC;
```

### Câu 2: Nhân viên không có phòng ban
```sql
-- Cách 1: IS NULL
SELECT * FROM nhan_vien WHERE phong_ban_id IS NULL;

-- Cách 2: NOT EXISTS
SELECT * FROM nhan_vien nv
WHERE NOT EXISTS (
    SELECT 1 FROM phong_ban pb WHERE pb.id = nv.phong_ban_id
);
```

### Câu 3: Phòng ban trống
```sql
-- Cách 1: LEFT JOIN
SELECT pb.*
FROM phong_ban pb
LEFT JOIN nhan_vien nv ON pb.id = nv.phong_ban_id
WHERE nv.id IS NULL;

-- Cách 2: NOT IN
SELECT * FROM phong_ban
WHERE id NOT IN (SELECT DISTINCT phong_ban_id FROM nhan_vien WHERE phong_ban_id IS NOT NULL);

-- Cách 3: NOT EXISTS
SELECT * FROM phong_ban pb
WHERE NOT EXISTS (SELECT 1 FROM nhan_vien nv WHERE nv.phong_ban_id = pb.id);
```

### Câu 4: Nhân viên + Quản lý (Self-Join)
```sql
SELECT
    nv.ho_ten AS nhan_vien,
    ql.ho_ten AS quan_ly
FROM nhan_vien nv
LEFT JOIN nhan_vien ql ON nv.quan_ly_id = ql.id
ORDER BY ql.ho_ten, nv.ho_ten;
```

### Câu 5: Nhân viên lương > quản lý
```sql
SELECT nv.ho_ten, nv.luong AS luong_nv, ql.ho_ten AS quan_ly, ql.luong AS luong_ql
FROM nhan_vien nv
JOIN nhan_vien ql ON nv.quan_ly_id = ql.id
WHERE nv.luong > ql.luong;
```

---

## Bài 2: Hàm Tập Hợp

### Câu 1: Thống kê phòng ban
```sql
SELECT
    pb.ten_phong_ban,
    COUNT(nv.id) AS so_nhan_vien,
    ROUND(AVG(nv.luong), 2) AS luong_trung_binh
FROM phong_ban pb
LEFT JOIN nhan_vien nv ON pb.id = nv.phong_ban_id
GROUP BY pb.ten_phong_ban
ORDER BY so_nhan_vien DESC;
```

### Câu 2: Phòng ban tổng lương cao nhất
```sql
-- Cách 1: ORDER BY + LIMIT
SELECT pb.ten_phong_ban, SUM(nv.luong) AS tong_luong
FROM nhan_vien nv
JOIN phong_ban pb ON nv.phong_ban_id = pb.id
GROUP BY pb.ten_phong_ban
ORDER BY tong_luong DESC LIMIT 1;

-- Cách 2: Subquery
SELECT pb.ten_phong_ban, SUM(nv.luong) AS tong_luong
FROM nhan_vien nv
JOIN phong_ban pb ON nv.phong_ban_id = pb.id
GROUP BY pb.ten_phong_ban
HAVING SUM(nv.luong) = (
    SELECT MAX(tl) FROM (
        SELECT SUM(luong) AS tl FROM nhan_vien GROUP BY phong_ban_id
    ) sub
);
```

### Câu 4: Tỷ lệ lương
```sql
SELECT
    pb.ten_phong_ban,
    SUM(nv.luong) AS tong_luong,
    ROUND(SUM(nv.luong) * 100.0 / (SELECT SUM(luong) FROM nhan_vien), 2) AS ty_le_phan_tram
FROM nhan_vien nv
JOIN phong_ban pb ON nv.phong_ban_id = pb.id
GROUP BY pb.ten_phong_ban
ORDER BY ty_le_phan_tram DESC;
```

### Câu 5: Lương cao nhất mỗi phòng ban (không dùng Window)
```sql
SELECT nv.ho_ten, nv.luong, pb.ten_phong_ban
FROM nhan_vien nv
JOIN phong_ban pb ON nv.phong_ban_id = pb.id
WHERE nv.luong = (
    SELECT MAX(luong) FROM nhan_vien WHERE phong_ban_id = nv.phong_ban_id
);
```

---

## Bài 3: Subquery

### Câu 1: Lương > TB phòng ban
```sql
SELECT nv.ho_ten, nv.luong, pb.ten_phong_ban, tb.avg_luong
FROM nhan_vien nv
JOIN phong_ban pb ON nv.phong_ban_id = pb.id
JOIN (
    SELECT phong_ban_id, AVG(luong) AS avg_luong
    FROM nhan_vien GROUP BY phong_ban_id
) tb ON nv.phong_ban_id = tb.phong_ban_id
WHERE nv.luong > tb.avg_luong;
```

### Câu 3: Nhân viên > 2 dự án
```sql
SELECT nv.ho_ten, COUNT(pc.du_an_id) AS so_du_an
FROM nhan_vien nv
JOIN phan_cong pc ON nv.id = pc.nhan_vien_id
GROUP BY nv.id, nv.ho_ten
HAVING COUNT(pc.du_an_id) > 2;
```

### Câu 5: Không tham gia dự án
```sql
SELECT * FROM nhan_vien nv
WHERE NOT EXISTS (
    SELECT 1 FROM phan_cong pc WHERE pc.nhan_vien_id = nv.id
);
```

---

## Bài 4: Window Functions

### Câu 1: Xếp hạng lương
```sql
SELECT
    ho_ten, phong_ban_id, luong,
    RANK() OVER (PARTITION BY phong_ban_id ORDER BY luong DESC) AS rank,
    DENSE_RANK() OVER (PARTITION BY phong_ban_id ORDER BY luong DESC) AS dense_rank
FROM nhan_vien;
```

### Câu 2: Tổng tích lũy
```sql
SELECT
    ho_ten, ngay_vao_lam, luong,
    SUM(luong) OVER (ORDER BY ngay_vao_lam) AS tong_tich_luy
FROM nhan_vien;
```

### Câu 3: So sánh với TB phòng ban
```sql
SELECT
    ho_ten, phong_ban_id, luong,
    ROUND(AVG(luong) OVER (PARTITION BY phong_ban_id), 2) AS luong_tb_pb,
    ROUND(luong - AVG(luong) OVER (PARTITION BY phong_ban_id), 2) AS chenh_lech,
    CASE
        WHEN luong > AVG(luong) OVER (PARTITION BY phong_ban_id) THEN 'Cao hơn TB'
        WHEN luong < AVG(luong) OVER (PARTITION BY phong_ban_id) THEN 'Thấp hơn TB'
        ELSE 'Bằng TB'
    END AS danh_gia
FROM nhan_vien;
```

### Câu 4: Top 3 mỗi phòng ban
```sql
SELECT * FROM (
    SELECT
        ho_ten, phong_ban_id, luong,
        ROW_NUMBER() OVER (PARTITION BY phong_ban_id ORDER BY luong DESC) AS rn
    FROM nhan_vien
) ranked WHERE rn <= 3;
```

### Câu 5: Moving Average
```sql
SELECT
    ho_ten, ngay_vao_lam, luong,
    ROUND(AVG(luong) OVER (
        ORDER BY ngay_vao_lam
        ROWS BETWEEN 1 PRECEDING AND 1 FOLLOWING
    ), 2) AS moving_avg_3
FROM nhan_vien;
```

---

## Bài 5: CTE và Recursive

### Câu 2: Cây tổ chức
```sql
WITH RECURSIVE cay AS (
    SELECT id, ho_ten, quan_ly_id, 0 AS cap, ho_ten::TEXT AS duong_dan
    FROM nhan_vien WHERE quan_ly_id IS NULL

    UNION ALL

    SELECT nv.id, nv.ho_ten, nv.quan_ly_id, c.cap + 1,
           c.duong_dan || ' → ' || nv.ho_ten
    FROM nhan_vien nv
    JOIN cay c ON nv.quan_ly_id = c.id
)
SELECT
    REPEAT('  ', cap) || ho_ten AS co_cau,
    cap AS cap_bac,
    duong_dan
FROM cay ORDER BY duong_dan;
```

### Câu 3: Tổng lương cấp dưới
```sql
WITH RECURSIVE nhan_vien_cay AS (
    SELECT id, ho_ten, quan_ly_id, luong, id AS root_id
    FROM nhan_vien

    UNION ALL

    SELECT nv.id, nv.ho_ten, nv.quan_ly_id, nv.luong, nc.root_id
    FROM nhan_vien nv
    JOIN nhan_vien_cay nc ON nv.quan_ly_id = nc.id
    WHERE nv.id != nc.root_id
)
SELECT
    nv.ho_ten AS quan_ly,
    nv.luong AS luong_ca_nhan,
    COALESCE(sub.tong_luong_cap_duoi, 0) AS tong_luong_cap_duoi,
    nv.luong + COALESCE(sub.tong_luong_cap_duoi, 0) AS tong_tat_ca
FROM nhan_vien nv
LEFT JOIN (
    SELECT root_id, SUM(luong) AS tong_luong_cap_duoi
    FROM nhan_vien_cay
    WHERE id != root_id
    GROUP BY root_id
) sub ON nv.id = sub.root_id
WHERE EXISTS (SELECT 1 FROM nhan_vien WHERE quan_ly_id = nv.id)
ORDER BY tong_tat_ca DESC;
```

---

## Bài 6: Stored Functions

### Câu 1: Thống kê phòng ban
```sql
CREATE OR REPLACE FUNCTION thong_ke_phong_ban(p_id INTEGER)
RETURNS TABLE (
    ten_phong_ban VARCHAR,
    so_nhan_vien BIGINT,
    luong_trung_binh NUMERIC,
    luong_cao_nhat NUMERIC,
    luong_thap_nhat NUMERIC,
    tong_luong NUMERIC
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        pb.ten_phong_ban,
        COUNT(nv.id),
        ROUND(AVG(nv.luong), 2),
        MAX(nv.luong),
        MIN(nv.luong),
        SUM(nv.luong)
    FROM phong_ban pb
    LEFT JOIN nhan_vien nv ON pb.id = nv.phong_ban_id
    WHERE pb.id = p_id
    GROUP BY pb.ten_phong_ban;
END;
$$ LANGUAGE plpgsql;

-- Sử dụng
SELECT * FROM thong_ke_phong_ban(1);
```

### Câu 2: Tính thưởng
```sql
CREATE OR REPLACE FUNCTION tinh_thuong(p_nhan_vien_id INTEGER)
RETURNS NUMERIC AS $$
DECLARE
    v_luong NUMERIC;
    v_so_nam NUMERIC;
BEGIN
    SELECT luong, EXTRACT(YEAR FROM AGE(CURRENT_DATE, ngay_vao_lam))
    INTO v_luong, v_so_nam
    FROM nhan_vien WHERE id = p_nhan_vien_id;

    IF v_luong IS NULL THEN
        RAISE EXCEPTION 'Nhân viên % không tồn tại', p_nhan_vien_id;
    END IF;

    RETURN CASE
        WHEN v_so_nam < 1 THEN 0
        WHEN v_so_nam <= 3 THEN v_luong
        ELSE v_luong * 2
    END;
END;
$$ LANGUAGE plpgsql;
```

### Câu 4: Audit Trigger
```sql
CREATE TABLE audit_log (
    id SERIAL PRIMARY KEY,
    bang_ten VARCHAR(50),
    hanh_dong VARCHAR(10),
    du_lieu_cu JSONB,
    du_lieu_moi JSONB,
    nguoi_thuc_hien VARCHAR(100) DEFAULT current_user,
    thoi_gian TIMESTAMPTZ DEFAULT NOW()
);

CREATE OR REPLACE FUNCTION audit_nhan_vien()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'DELETE' THEN
        INSERT INTO audit_log (bang_ten, hanh_dong, du_lieu_cu)
        VALUES ('nhan_vien', 'DELETE', row_to_json(OLD)::jsonb);
        RETURN OLD;
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO audit_log (bang_ten, hanh_dong, du_lieu_cu, du_lieu_moi)
        VALUES ('nhan_vien', 'UPDATE', row_to_json(OLD)::jsonb, row_to_json(NEW)::jsonb);
        RETURN NEW;
    ELSIF TG_OP = 'INSERT' THEN
        INSERT INTO audit_log (bang_ten, hanh_dong, du_lieu_moi)
        VALUES ('nhan_vien', 'INSERT', row_to_json(NEW)::jsonb);
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_audit_nhan_vien
AFTER INSERT OR UPDATE OR DELETE ON nhan_vien
FOR EACH ROW EXECUTE FUNCTION audit_nhan_vien();
```
