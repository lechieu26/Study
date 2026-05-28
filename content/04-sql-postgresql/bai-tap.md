# SQL (PostgreSQL) - Bài Tập

## Chuẩn bị dữ liệu
Sử dụng các bảng sau cho bài tập:

```sql
CREATE TABLE phong_ban (
    id SERIAL PRIMARY KEY,
    ten_phong_ban VARCHAR(100) NOT NULL,
    truong_phong_id INTEGER
);

CREATE TABLE nhan_vien (
    id SERIAL PRIMARY KEY,
    ho_ten VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE,
    phong_ban_id INTEGER REFERENCES phong_ban(id),
    quan_ly_id INTEGER REFERENCES nhan_vien(id),
    luong NUMERIC(12, 2) NOT NULL,
    ngay_vao_lam DATE NOT NULL,
    trang_thai BOOLEAN DEFAULT TRUE
);

CREATE TABLE du_an (
    id SERIAL PRIMARY KEY,
    ten_du_an VARCHAR(200) NOT NULL,
    ngay_bat_dau DATE,
    ngay_ket_thuc DATE,
    ngan_sach NUMERIC(15, 2)
);

CREATE TABLE phan_cong (
    nhan_vien_id INTEGER REFERENCES nhan_vien(id),
    du_an_id INTEGER REFERENCES du_an(id),
    vai_tro VARCHAR(50),
    so_gio INTEGER,
    PRIMARY KEY (nhan_vien_id, du_an_id)
);
```

---

## Bài 1: Truy Vấn Cơ Bản và JOIN
**Độ khó: Trung bình**

1. Liệt kê tất cả nhân viên cùng tên phòng ban, sắp xếp theo lương giảm dần.
2. Tìm nhân viên không thuộc phòng ban nào.
3. Tìm phòng ban không có nhân viên nào.
4. Liệt kê nhân viên và tên quản lý trực tiếp (self-join).
5. Tìm nhân viên có lương cao hơn quản lý của mình.

---

## Bài 2: Hàm Tập Hợp và GROUP BY
**Độ khó: Trung bình**

1. Đếm số nhân viên và tính lương trung bình mỗi phòng ban.
2. Tìm phòng ban có tổng lương cao nhất.
3. Liệt kê các phòng ban có hơn 5 nhân viên.
4. Tính tỷ lệ phần trăm lương mỗi phòng ban so với tổng lương công ty.
5. Tìm nhân viên có lương cao nhất mỗi phòng ban (không dùng Window Function).

---

## Bài 3: Subquery Nâng Cao
**Độ khó: Trung bình - Khó**

1. Tìm nhân viên có lương cao hơn lương trung bình phòng ban của họ.
2. Tìm phòng ban có lương trung bình cao hơn lương trung bình toàn công ty.
3. Liệt kê nhân viên tham gia nhiều hơn 2 dự án.
4. Tìm dự án có tổng số giờ làm việc nhiều nhất.
5. Tìm nhân viên không tham gia dự án nào (sử dụng EXISTS).

---

## Bài 4: Window Functions
**Độ khó: Khó**

1. Xếp hạng lương nhân viên trong mỗi phòng ban (RANK, DENSE_RANK).
2. Tính tổng lương tích lũy theo ngày vào làm.
3. So sánh lương mỗi nhân viên với lương trung bình phòng ban (dùng Window).
4. Tìm top 3 nhân viên lương cao nhất mỗi phòng ban.
5. Tính lương trung bình trượt (moving average) 3 nhân viên liên tiếp theo ngày vào làm.

---

## Bài 5: CTE và Recursive Query
**Độ khó: Khó**

1. Viết CTE tính số nhân viên, lương TB, lương max mỗi phòng ban, sau đó tìm phòng ban có lương max cao nhất.
2. Dùng Recursive CTE để hiển thị cây tổ chức (ai quản lý ai) với thụt lề.
3. Tính tổng lương của mỗi quản lý và tất cả nhân viên cấp dưới (gián tiếp).
4. Viết CTE để tìm "chuỗi quản lý" từ mỗi nhân viên đến CEO.

---

## Bài 6: Stored Functions và Trigger
**Độ khó: Khó**

1. Viết function `thong_ke_phong_ban(p_id INTEGER)` trả về TABLE với thông tin chi tiết.
2. Viết function tính thưởng: < 1 năm = 0, 1-3 năm = 1 tháng lương, > 3 năm = 2 tháng.
3. Tạo trigger tự động cập nhật `truong_phong_id` khi nhân viên có lương cao nhất thay đổi.
4. Tạo trigger audit log cho bảng nhân viên (INSERT, UPDATE, DELETE).

---

## Bài 7: Tối Ưu Hóa Truy Vấn
**Độ khó: Rất Khó**

1. Cho truy vấn chậm, dùng EXPLAIN ANALYZE phân tích và tối ưu bằng index.
2. Viết lại subquery tương quan thành JOIN để cải thiện hiệu năng.
3. So sánh hiệu năng giữa: subquery IN vs EXISTS vs JOIN.
4. Thiết kế index strategy cho hệ thống có lượng đọc lớn với nhiều filter kết hợp.
