# Spring Data JPA - Bài Tập

## Bài 1: Entity Mapping và Relationships
**Độ khó: Trung bình**

Thiết kế hệ thống quản lý trường học:
1. Entity `SinhVien`: id, maSV, hoTen, email, ngaySinh, lop.
2. Entity `KhoaHoc`: id, ten, moTa, soTinChi, giangVien.
3. Quan hệ Many-to-Many qua bảng `DangKy` (có thêm cột: ngayDangKy, diemSo, trangThai).
4. Entity `GiangVien`: id, hoTen, email, boMon → OneToMany với `KhoaHoc`.
5. Sử dụng `@MappedSuperclass` cho BaseEntity (id, createdAt, updatedAt).
6. Cấu hình Auditing tự động fill createdAt/updatedAt.

---

## Bài 2: Repository và Derived Queries
**Độ khó: Trung bình**

Từ bài 1, tạo các repository methods:
1. `findByHoTenContainingIgnoreCase(String keyword)` - tìm sinh viên theo tên.
2. `findByLopAndDiemTrungBinhGreaterThan(String lop, Double diem)` - SV giỏi.
3. `findByNgaySinhBetween(LocalDate from, LocalDate to)` - SV theo năm sinh.
4. `countByKhoaHocId(Long khoaHocId)` - đếm SV đăng ký khóa học.
5. `findTop5ByOrderByDiemTrungBinhDesc()` - top 5 SV điểm cao nhất.
6. Pagination: `Page<SinhVien> findByLop(String lop, Pageable pageable)`.

---

## Bài 3: Custom Queries (JPQL và Native)
**Độ khó: Trung bình - Khó**

1. JPQL: Lấy danh sách SV cùng điểm trung bình các khóa học đã đăng ký.
2. JPQL JOIN FETCH: Load SinhVien kèm tất cả KhoaHoc (giải quyết N+1).
3. Native Query: Thống kê số SV theo từng lớp.
4. @Modifying: Cập nhật trạng thái `DangKy` hàng loạt theo điều kiện.
5. Projection: Interface-based projection chỉ lấy (hoTen, email, lop).
6. Dynamic Projection: Cùng method trả về DTO khác nhau tùy request.

---

## Bài 4: Specifications - Tìm Kiếm Động
**Độ khó: Khó**

1. Tạo `SinhVienSpec` với các Specification: `coTen()`, `thuocLop()`, `diemTrongKhoang()`, `dangHoatDong()`.
2. Combine specifications từ filter params: keyword, lop, diemMin, diemMax, trangThai.
3. Tạo `SinhVienFilter` DTO chứa tất cả filter params.
4. Controller endpoint `GET /api/sinh-vien/tim-kiem` sử dụng Specification.
5. Hỗ trợ pagination + sorting cùng với Specification.

---

## Bài 5: Performance Optimization
**Độ khó: Khó**

1. Giải quyết N+1 problem: load SinhVien + KhoaHoc + GiangVien trong 1 query.
2. `@EntityGraph` cho findAll() load kèm relationships.
3. Batch insert 1000 SinhVien sử dụng `saveAll()` + flush thủ công.
4. Implement Soft Delete: @SQLDelete + @SQLRestriction.
5. Second-level cache cho entity ít thay đổi (KhoaHoc).
6. So sánh performance: N+1 vs JOIN FETCH vs @BatchSize (log SQL count).
