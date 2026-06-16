# Spring MVC & REST API - Bài Tập

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

## Bài 2: Validation Nâng Cao
**Độ khó: Trung bình**

1. Tạo custom annotation `@UniqueEmail` validate email chưa tồn tại trong DB.
2. Tạo `@StrongPassword` validate: >=8 ký tự, có chữ hoa, chữ thường, số, ký tự đặc biệt.
3. Implement Validation Groups: `OnCreate` và `OnUpdate` (ID bắt buộc khi update).
4. Cross-field validation: `ngayKetThuc` phải sau `ngayBatDau`.
5. Trả về error response chi tiết với field name và message.

---

## Bài 3: File Upload/Download API
**Độ khó: Trung bình - Khó**

1. Endpoint `POST /api/files/upload` nhận multipart file (max 10MB).
2. Validate file type (chỉ cho phép: jpg, png, pdf, docx).
3. Lưu file vào thư mục cấu hình trong application.yml.
4. Endpoint `GET /api/files/{filename}` download file.
5. Endpoint `GET /api/files` liệt kê tất cả files đã upload.
6. Xử lý các exception: file quá lớn, type không hợp lệ, file không tồn tại.

---

## Bài 4: API Versioning và HATEOAS
**Độ khó: Khó**

1. Implement 3 cách versioning: URI (/v1/users), Header (X-API-Version), Content-Type.
2. Tạo HATEOAS links cho resource: self, collection, related.
3. Implement pagination links: first, prev, next, last.
4. Tạo Interceptor log request/response time.
5. Custom `HandlerMethodArgumentResolver` parse complex filter params.

---

## Bài 5: WebSocket Chat Application
**Độ khó: Khó**

1. WebSocket endpoint `/ws/chat` cho gửi/nhận tin nhắn real-time.
2. STOMP protocol: subscribe `/topic/messages`, send to `/app/chat`.
3. Phòng chat: tạo phòng, tham gia (`/topic/room/{roomId}`), rời phòng.
4. Thông báo khi user join/leave room.
5. Lưu lịch sử chat vào database.
6. REST endpoint `GET /api/rooms/{id}/messages` lấy lịch sử (có pagination).
