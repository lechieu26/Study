# Dự Án ERP - Bài Tập Khởi Tạo Dự Án

## Bài 1: Khởi tạo Backend Spring Boot
**Độ khó: Trung bình**

Khởi tạo project Spring Boot cho hệ thống ERP với đầy đủ cấu hình:

1. Tạo project từ Spring Initializr với các dependencies: Spring Web, Spring Data JPA, PostgreSQL, Security, Lombok, Validation
2. Cấu hình `application.yml` kết nối PostgreSQL database `erp_db`
3. Tạo `BaseEntity` abstract class với các field: id, createdAt, updatedAt, createdBy
4. Tạo `ApiResponse<T>` wrapper class cho response chuẩn
5. Tạo `GlobalExceptionHandler` xử lý exception tập trung
6. Cấu hình CORS cho phép frontend (port 3000) gọi API

**Đầu vào:** Không
**Đầu ra:** Project Spring Boot chạy được trên port 8080, trả về JSON response chuẩn

---

## Bài 2: Khởi tạo Frontend React
**Độ khó: Trung bình**

Khởi tạo project React cho hệ thống ERP:

1. Tạo React app và cài đặt: react-router-dom, axios, antd (UI library)
2. Tạo component `MainLayout` với Sidebar menu và Header
3. Sidebar hiển thị menu: Dashboard, Bán hàng, Mua hàng, Kho, Sản xuất, Kế toán, Nhân sự
4. Cấu hình React Router với các route cho từng module
5. Tạo `api.js` service với axios, cấu hình baseURL và interceptor JWT
6. Tạo trang Dashboard với thống kê giả lập (4 card: Doanh thu, Đơn hàng, Khách hàng, Sản phẩm)

**Đầu vào:** Không
**Đầu ra:** Ứng dụng React chạy trên port 3000, có layout với sidebar, router hoạt động, dashboard hiển thị 4 card thống kê

---

## Bài 3: Thiết kế Database Schema
**Độ khó: Trung bình**

Thiết kế cơ sở dữ liệu PostgreSQL cho hệ thống ERP:

1. Tạo bảng `users` với các field: id, username, password, full_name, email, role, active
2. Tạo bảng `customers` với: id, code, name, phone, email, address, tax_code
3. Tạo bảng `suppliers` với cấu trúc tương tự customers
4. Tạo bảng `products` với: id, code, name, category, unit, price, cost_price, min_stock
5. Tạo bảng `warehouses` với: id, code, name, address, manager_id (FK → users)
6. Viết JPA Entity cho mỗi bảng, sử dụng Lombok annotation

**Đầu vào:** Không
**Đầu ra:** 5 Entity classes ánh xạ đúng cấu trúc bảng, có relationship giữa Warehouse và User

---

## Bài 4: Authentication - Đăng nhập / Đăng ký
**Độ khó: Trung bình - Khó**

Xây dựng hệ thống xác thực JWT:

**Backend:**
1. Tạo `AuthController` với 2 endpoint: POST `/api/auth/login`, POST `/api/auth/register`
2. Tạo `JwtUtils` class: generateToken, validateToken, getUsernameFromToken
3. Cấu hình Spring Security: cho phép `/api/auth/**` không cần token, các API khác phải authenticate
4. Tạo `JwtAuthFilter` extends OncePerRequestFilter

**Frontend:**
5. Tạo trang Login với form: username, password
6. Gọi API login, lưu JWT token vào localStorage
7. Tạo ProtectedRoute component: redirect về /login nếu chưa đăng nhập
8. Hiển thị tên user trên Header, nút Logout

**Đầu vào:** Username và password
**Đầu ra:** Đăng nhập thành công → redirect Dashboard, Token được gửi kèm mọi API request

---

## Bài 5: CRUD Sản phẩm (Product)
**Độ khó: Trung bình**

Xây dựng chức năng quản lý sản phẩm hoàn chỉnh (CRUD đầy đủ):

**Backend:**
1. Tạo `ProductEntity`, `ProductDTO`, `ProductRepository`, `ProductService`, `ProductController`
2. API endpoints: GET `/api/products` (phân trang, tìm kiếm), GET `/api/products/{id}`, POST, PUT, DELETE
3. Validate: code và name bắt buộc, price >= 0, code không trùng

**Frontend:**
4. Tạo trang danh sách sản phẩm với DataTable: phân trang, tìm kiếm theo tên/mã
5. Nút "Thêm mới" → Modal form thêm sản phẩm
6. Nút "Sửa" trên mỗi dòng → Modal form cập nhật
7. Nút "Xóa" với confirm dialog
8. Hiển thị thông báo thành công/thất bại (toast notification)

**Đầu vào:** Thông tin sản phẩm (mã, tên, giá, đơn vị, mô tả)
**Đầu ra:** Danh sách sản phẩm hiển thị đúng, CRUD hoạt động hoàn chỉnh, phân trang và tìm kiếm hoạt động
