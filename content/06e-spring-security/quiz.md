# Spring Security - Quiz

## Câu 1
[TYPE: MULTIPLE_CHOICE]
Spring Security mặc định bảo vệ tất cả endpoints bằng:

A. API Key
B. HTTP Basic Authentication
C. JWT
D. OAuth2

**Đáp án: B**
> Khi thêm spring-boot-starter-security, TẤT CẢ endpoints yêu cầu HTTP Basic auth với user "user" và generated password.

## Câu 2
[TYPE: TRUE_FALSE]
SecurityFilterChain thay thế WebSecurityConfigurerAdapter (deprecated từ Spring Security 5.7).

**Đáp án: TRUE**
> DSL mới dùng @Bean SecurityFilterChain method thay vì extend WebSecurityConfigurerAdapter.

## Câu 3
[TYPE: MULTIPLE_CHOICE]
Thứ tự authorizeHttpRequests matchers quan trọng vì:

A. Không quan trọng
B. Spring kiểm tra từ trên xuống, match đầu tiên được áp dụng
C. Spring kiểm tra từ dưới lên
D. Chỉ matcher cuối cùng có hiệu lực

**Đáp án: B**
> Specific patterns phải đặt TRƯỚC general patterns. `/api/admin/**` trước `/**`.

## Câu 4
[TYPE: SELECT_RESULT]
```java
http.authorizeHttpRequests(auth -> auth
    .requestMatchers("/api/public/**").permitAll()
    .requestMatchers("/api/admin/**").hasRole("ADMIN")
    .anyRequest().authenticated());
```
User role USER truy cập `/api/admin/users` → kết quả?

A. 200 OK
B. 401 Unauthorized
C. 403 Forbidden
D. 404 Not Found

**Đáp án: C**
> User đã authenticated nhưng không có role ADMIN → 403 Forbidden.

## Câu 5
[TYPE: MULTIPLE_CHOICE]
JWT token gồm 3 phần:

A. Username, Password, Role
B. Header, Payload, Signature
C. Access, Refresh, Session
D. Key, Value, Expiry

**Đáp án: B**
> JWT = Header (algorithm) + Payload (claims) + Signature (verification). Base64 encoded, separated by dots.

## Câu 6
[TYPE: MULTIPLE_CHOICE]
BCryptPasswordEncoder với strength=12 nghĩa là:

A. Password tối đa 12 ký tự
B. 2^12 = 4096 rounds hashing (cost factor)
C. Salt dài 12 bytes
D. Hash output 12 bytes

**Đáp án: B**
> Strength = log2(rounds). Strength 12 = 4096 rounds. Cao hơn = chậm hơn = an toàn hơn.

## Câu 7
[TYPE: TRUE_FALSE]
Stateless session (SessionCreationPolicy.STATELESS) nghĩa là server không lưu session - mọi request phải tự xác thực (vd: JWT).

**Đáp án: TRUE**
> STATELESS = không HttpSession. Mỗi request phải carry token/credentials.

## Câu 8
[TYPE: MULTIPLE_CHOICE]
@PreAuthorize("hasRole('ADMIN')") kiểm tra:

A. Trước khi method thực thi
B. Sau khi method thực thi
C. Trong quá trình thực thi
D. Khi throw exception

**Đáp án: A**
> @PreAuthorize = kiểm tra TRƯỚC method call. @PostAuthorize = kiểm tra SAU (có access return value).

## Câu 9
[TYPE: SELECT_RESULT]
```java
@PreAuthorize("#userId == authentication.principal.id")
public UserDTO updateUser(Long userId, UserRequest req) { ... }
```
Biểu thức này kiểm tra gì?

A. User có role ADMIN
B. userId param phải bằng ID của user đang đăng nhập (chỉ sửa info của chính mình)
C. User đã authenticated
D. Request có JWT token

**Đáp án: B**
> SpEL so sánh method param userId với authenticated user's ID → user chỉ update chính mình.

## Câu 10
[TYPE: MULTIPLE_CHOICE]
CSRF protection nên:

A. Luôn enable
B. Disable cho REST API (stateless), enable cho web forms (stateful)
C. Luôn disable
D. Chỉ enable cho GET requests

**Đáp án: B**
> REST API dùng JWT/tokens không cần CSRF. Web forms dùng session cần CSRF protection.

## Câu 11
[TYPE: MULTIPLE_CHOICE]
OncePerRequestFilter đảm bảo:

A. Filter chỉ chạy 1 lần per request (không lặp lại khi forward/include)
B. Filter chạy mỗi giây
C. Chỉ accept 1 request đồng thời
D. Filter tự destroy sau 1 request

**Đáp án: A**
> Tránh duplicate filter execution trong trường hợp request dispatch (forward, include, error).

## Câu 12
[TYPE: TRUE_FALSE]
Role hierarchy ADMIN > MANAGER > USER nghĩa là ADMIN có tất cả quyền của MANAGER và USER.

**Đáp án: TRUE**
> RoleHierarchy bean cho phép role thừa kế. ADMIN kế thừa permissions của MANAGER, MANAGER kế thừa USER.

## Câu 13
[TYPE: MULTIPLE_CHOICE]
OAuth2 Authorization Code Flow phù hợp cho:

A. Mobile apps
B. Server-side web applications
C. Public SPAs
D. IoT devices

**Đáp án: B**
> Authorization Code Flow: server-side apps (có client_secret). SPAs dùng PKCE variant.

## Câu 14
[TYPE: MULTIPLE_CHOICE]
@WithMockUser(roles = "ADMIN") trong test:

A. Tạo user thật trong database
B. Mock SecurityContext với user có role ADMIN
C. Login bằng HTTP Basic
D. Generate JWT token

**Đáp án: B**
> @WithMockUser populate SecurityContext với mock Authentication. Không cần real user/token.

## Câu 15
[TYPE: SELECT_RESULT]
```java
http.exceptionHandling(ex -> ex
    .authenticationEntryPoint((req, res, e) -> res.sendError(401))
    .accessDeniedHandler((req, res, e) -> res.sendError(403)));
```
Unauthenticated user truy cập protected endpoint → status?

A. 200
B. 401
C. 403
D. 500

**Đáp án: B**
> authenticationEntryPoint xử lý khi CHƯA authenticate → 401. accessDeniedHandler xử lý khi ĐÃ authenticate nhưng thiếu quyền → 403.
