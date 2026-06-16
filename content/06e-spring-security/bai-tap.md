# Spring Security - Bài Tập

## Bài 1: JWT Authentication Cơ Bản
**Độ khó: Trung bình**

Xây dựng hệ thống xác thực JWT:
1. Tạo `POST /api/auth/register` - đăng ký (username, email, password).
2. Tạo `POST /api/auth/login` - trả về JWT token.
3. Password encode bằng BCrypt.
4. JWT token chứa: username, roles, expiration.
5. Middleware `JwtAuthenticationFilter` xác thực token trên mọi request.
6. Endpoint `GET /api/me` trả về thông tin user hiện tại.

---

## Bài 2: Role-Based Access Control (RBAC)
**Độ khó: Trung bình**

1. Roles: ADMIN, MANAGER, USER.
2. Cấu hình SecurityFilterChain: GET → USER, POST/PUT → MANAGER, DELETE → ADMIN.
3. Method-level security: `@PreAuthorize("hasRole('ADMIN')")` cho admin endpoints.
4. Custom expression: `@PreAuthorize("#userId == authentication.principal.id")` user chỉ sửa info của mình.
5. Role hierarchy: ADMIN > MANAGER > USER.
6. Test: tạo users với roles khác nhau, verify access control.

---

## Bài 3: OAuth2 Social Login
**Độ khó: Khó**

1. Cấu hình OAuth2 login với Google và GitHub.
2. Custom `OAuth2UserService` lưu user mới vào database.
3. Sau login thành công → generate JWT token riêng (không phụ thuộc OAuth2 session).
4. Link account: user đăng nhập bằng Google → link với account email/password.
5. Endpoint `GET /api/auth/providers` liệt kê providers user đã link.

---

## Bài 4: Refresh Token và Logout
**Độ khó: Trung bình - Khó**

1. Access token (15 phút) + Refresh token (7 ngày).
2. `POST /api/auth/refresh` - lấy access token mới từ refresh token.
3. Lưu refresh tokens trong database (userId, token, expiresAt, revoked).
4. `POST /api/auth/logout` - revoke refresh token.
5. `POST /api/auth/logout-all` - revoke tất cả refresh tokens của user.
6. Cron job xóa expired refresh tokens hàng ngày.

---

## Bài 5: Rate Limiting và Security Headers
**Độ khó: Trung bình**

1. Rate limit: 100 requests/phút cho /api/**, 5 requests/phút cho /api/auth/login.
2. Khi exceed → return 429 Too Many Requests.
3. Cấu hình Security Headers: HSTS, X-Content-Type-Options, X-Frame-Options.
4. CORS: cho phép origins cụ thể, credentials=true.
5. Implement account lockout: 5 login thất bại → lock 30 phút.
