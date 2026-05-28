# Java Spring - Bài Tập

## Bài 1: REST API CRUD - Quản Lý Sản Phẩm
**Độ khó: Trung bình**

Xây dựng REST API quản lý sản phẩm với Spring Boot:
1. Entity `SanPham`: id, ten, moTa, gia, soLuong, danhMuc, ngayTao.
2. Các endpoint: GET /api/san-pham, GET /{id}, POST, PUT /{id}, DELETE /{id}.
3. Hỗ trợ phân trang và sắp xếp: `?page=0&size=10&sort=gia,desc`.
4. Tìm kiếm: `?keyword=abc&danhMuc=DienTu&giaMin=100&giaMax=500`.
5. Validation đầy đủ cho request body.
6. Exception handling trả về response chuẩn.

---

## Bài 2: Spring Security - Xác Thực JWT
**Độ khó: Khó**

Implement hệ thống xác thực và phân quyền:
1. Đăng ký: POST /api/auth/register (username, password, email).
2. Đăng nhập: POST /api/auth/login → trả về JWT token.
3. Phân quyền: ADMIN có full quyền, USER chỉ GET.
4. Refresh token khi token sắp hết hạn.
5. Logout (đưa token vào blacklist).
6. Mã hóa password với BCrypt.

---

## Bài 3: Spring AOP - Logging và Caching
**Độ khó: Trung bình - Khó**

1. Tạo `@LogExecutionTime` annotation: Tự động log thời gian thực thi của method.
2. Tạo `@CacheResult` annotation: Cache kết quả của method trong một khoảng thời gian.
3. Tạo `@RateLimit` annotation: Giới hạn số lần gọi method trong 1 phút.
4. Implement các Aspect tương ứng.
5. Viết unit test cho từng aspect.

---

## Bài 4: Spring Data JPA - Quan Hệ Phức Tạp
**Độ khó: Khó**

Thiết kế hệ thống quản lý khóa học:
1. Entities: KhoaHoc, GiangVien, SinhVien, DangKy (bảng trung gian).
2. Quan hệ: KhoaHoc (N-1) GiangVien, SinhVien (N-N) KhoaHoc qua DangKy.
3. DangKy có: ngayDangKy, diemSo, trangThai.
4. Custom queries:
   - Top 5 khóa học nhiều sinh viên nhất.
   - Giảng viên có điểm trung bình sinh viên cao nhất.
   - Sinh viên đăng ký nhiều khóa nhất với tổng điểm.
5. Sử dụng Specification cho tìm kiếm động.

---

## Bài 5: Microservice - Hệ Thống Đặt Hàng
**Độ khó: Rất Khó**

Thiết kế và implement 2 service:
1. **Product Service:** CRUD sản phẩm, kiểm tra tồn kho.
2. **Order Service:** Tạo đơn hàng, gọi Product Service kiểm tra.
3. Giao tiếp qua REST (WebClient hoặc OpenFeign).
4. Circuit Breaker (Resilience4j): Xử lý khi Product Service down.
5. Centralized Configuration (Spring Cloud Config hoặc Environment Variables).
6. API Gateway pattern.

---

## Bài 6: Spring Boot Testing
**Độ khó: Trung bình**

Viết test đầy đủ cho bài 1 (CRUD Sản Phẩm):
1. Unit test cho Service layer (Mockito).
2. Repository test với @DataJpaTest và H2.
3. Integration test với MockMvc.
4. Test cho validation và exception handling.
5. Đạt code coverage >= 80%.

---

## Bài 7: WebSocket Chat Application
**Độ khó: Khó**

Xây dựng ứng dụng chat real-time:
1. WebSocket endpoint cho gửi/nhận tin nhắn.
2. Phòng chat: tạo phòng, tham gia, rời phòng.
3. Thông báo khi user join/leave.
4. Lưu lịch sử chat vào database.
5. Hiển thị danh sách user online.
6. Xác thực user qua JWT token.
