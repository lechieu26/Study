# Quiz - SQL (PostgreSQL)

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Câu lệnh SQL nào dùng để lấy dữ liệu từ bảng?

- [ ] INSERT
- [x] SELECT
- [ ] UPDATE
- [ ] DELETE

> **Giải thích:** `SELECT` là câu lệnh DQL (Data Query Language) dùng để truy vấn và lấy dữ liệu từ bảng.

## Câu 2

[TYPE: SELECT_RESULT]

Cho bảng `nhan_vien` có 15 dòng. Câu lệnh sau trả về bao nhiêu dòng?

```sql
SELECT COUNT(*) FROM nhan_vien WHERE luong > 20000000;
```

- [ ] 15
- [ ] 0
- [x] 5
- [ ] Lỗi cú pháp

> **Giải thích:** `COUNT(*)` đếm số dòng thỏa điều kiện `luong > 20000000`. Trong dữ liệu mẫu, có 5 nhân viên có lương trên 20 triệu.

## Câu 3

[TYPE: FILL_BLANK]

Mệnh đề `___` dùng để lọc kết quả sau khi GROUP BY.

- [ ] WHERE
- [x] HAVING
- [ ] ORDER BY
- [ ] LIMIT

> **Giải thích:** `HAVING` lọc sau khi nhóm (GROUP BY), còn `WHERE` lọc trước khi nhóm. HAVING thường dùng với aggregate functions (COUNT, SUM, AVG...).

## Câu 4

[TYPE: TRUE_FALSE]

Mệnh đề: "INNER JOIN trả về tất cả các dòng từ cả hai bảng, kể cả khi không có dòng khớp."

- [ ] Đúng
- [x] Sai

> **Giải thích:** INNER JOIN chỉ trả về các dòng có giá trị khớp ở cả hai bảng. LEFT JOIN mới trả về tất cả dòng từ bảng bên trái kể cả không khớp.

## Câu 5

[TYPE: MULTIPLE_CHOICE]

Window Function nào dùng để đánh số thứ tự cho mỗi dòng trong partition?

- [x] ROW_NUMBER()
- [ ] COUNT()
- [ ] SUM()
- [ ] GROUP BY

> **Giải thích:** `ROW_NUMBER()` gán số thứ tự liên tục (1, 2, 3...) cho mỗi dòng trong partition. Sử dụng với `OVER(PARTITION BY ... ORDER BY ...)`.

## Câu 6

[TYPE: SELECT_RESULT]

Câu lệnh SQL sau cho kết quả gì?

```sql
SELECT COALESCE(NULL, NULL, 'Hello', 'World');
```

- [ ] NULL
- [x] Hello
- [ ] World
- [ ] Lỗi cú pháp

> **Giải thích:** `COALESCE` trả về giá trị đầu tiên không phải NULL trong danh sách tham số. Ở đây, 'Hello' là giá trị không NULL đầu tiên.

## Câu 7

[TYPE: FILL_BLANK]

Để tạo một truy vấn con tạm thời có thể tái sử dụng trong câu lệnh SQL, ta sử dụng `___` (Common Table Expression).

- [x] WITH
- [ ] TEMP
- [ ] VIEW
- [ ] DECLARE

> **Giải thích:** `WITH` (CTE - Common Table Expression) tạo bảng tạm thời trong phạm vi câu truy vấn, giúp code dễ đọc và tái sử dụng.

## Câu 8

[TYPE: TRUE_FALSE]

Mệnh đề: "INDEX giúp tăng tốc câu lệnh SELECT nhưng có thể làm chậm INSERT và UPDATE."

- [x] Đúng
- [ ] Sai

> **Giải thích:** INDEX tạo cấu trúc dữ liệu phụ giúp tìm kiếm nhanh. Nhưng khi INSERT/UPDATE, index cũng cần được cập nhật, tạo overhead.

## Câu 9

[TYPE: MULTIPLE_CHOICE]

Sự khác biệt chính giữa RANK() và DENSE_RANK()?

- [ ] RANK() bắt đầu từ 0, DENSE_RANK() bắt đầu từ 1
- [x] RANK() bỏ qua thứ hạng khi có giá trị trùng, DENSE_RANK() không bỏ qua
- [ ] Không có sự khác biệt
- [ ] DENSE_RANK() chỉ dùng được với số

> **Giải thích:** Ví dụ: nếu 2 người cùng hạng 1, RANK() tiếp theo là 3 (bỏ qua 2), DENSE_RANK() tiếp theo là 2 (liên tục).

## Câu 10

[TYPE: SELECT_RESULT]

Câu lệnh SQL sau cho kết quả gì?

```sql
SELECT 
    CASE 
        WHEN 10 > 5 THEN 'Lớn hơn'
        WHEN 10 = 5 THEN 'Bằng nhau'
        ELSE 'Nhỏ hơn'
    END AS ket_qua;
```

- [x] Lớn hơn
- [ ] Bằng nhau
- [ ] Nhỏ hơn
- [ ] NULL

> **Giải thích:** CASE WHEN kiểm tra điều kiện theo thứ tự. Điều kiện đầu tiên `10 > 5` đúng nên trả về 'Lớn hơn'.
