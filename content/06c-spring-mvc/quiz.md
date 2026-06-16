# Spring MVC & REST - Quiz

## Câu 1
[TYPE: MULTIPLE_CHOICE]
Sự khác biệt giữa @Controller và @RestController:

A. Không khác nhau
B. @RestController = @Controller + @ResponseBody (tất cả methods trả JSON)
C. @Controller xử lý REST, @RestController xử lý HTML
D. @RestController không thể dùng @RequestMapping

**Đáp án: B**
> @RestController auto-applies @ResponseBody cho tất cả methods → trả response body trực tiếp (JSON/XML).

## Câu 2
[TYPE: MULTIPLE_CHOICE]
HTTP method nào dùng cho "tạo mới resource":

A. GET
B. PUT
C. POST
D. PATCH

**Đáp án: C**
> POST /api/users → tạo mới. PUT = replace toàn bộ. PATCH = update 1 phần.

## Câu 3
[TYPE: TRUE_FALSE]
PUT là idempotent (gọi nhiều lần cho cùng kết quả), POST thì không.

**Đáp án: TRUE**
> PUT replace resource → kết quả giống nhau. POST tạo mới → mỗi lần gọi tạo thêm resource.

## Câu 4
[TYPE: SELECT_RESULT]
```java
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) { ... }
```
Request `GET /users/5` → giá trị `id` là?

A. null
B. "5" (String)
C. 5 (Long)
D. Exception

**Đáp án: C**
> Spring tự convert "5" thành Long 5 cho @PathVariable.

## Câu 5
[TYPE: MULTIPLE_CHOICE]
@RequestParam vs @PathVariable:

A. Cả hai lấy từ URL path
B. @PathVariable lấy từ URI template, @RequestParam lấy từ query string
C. Cả hai lấy từ request body
D. Không khác nhau

**Đáp án: B**
> @PathVariable: `/users/{id}` (path segment). @RequestParam: `/users?name=abc` (query parameter).

## Câu 6
[TYPE: MULTIPLE_CHOICE]
Khi validation fail với @Valid, Spring trả về status code nào mặc định?

A. 401
B. 403
C. 400
D. 422

**Đáp án: C**
> 400 Bad Request là default. Có thể customize trong @ExceptionHandler hoặc @ControllerAdvice.

## Câu 7
[TYPE: SELECT_RESULT]
```java
@PostMapping("/users")
public ResponseEntity<User> create(@Valid @RequestBody UserRequest req) {
    User user = service.create(req);
    return ResponseEntity.status(HttpStatus.CREATED).body(user);
}
```
Response status code cho request thành công?

A. 200
B. 201
C. 204
D. 302

**Đáp án: B**
> HttpStatus.CREATED = 201. Best practice cho POST tạo resource mới.

## Câu 8
[TYPE: TRUE_FALSE]
@RestControllerAdvice xử lý exceptions globally cho tất cả controllers.

**Đáp án: TRUE**
> @RestControllerAdvice = @ControllerAdvice + @ResponseBody. Centralized exception handling.

## Câu 9
[TYPE: MULTIPLE_CHOICE]
Content Negotiation trong Spring MVC dùng để:

A. Negotiate network connection
B. Trả response dạng JSON hoặc XML tùy theo Accept header
C. Encrypt response
D. Compress response

**Đáp án: B**
> Accept: application/json → trả JSON. Accept: application/xml → trả XML (cần jackson-dataformat-xml).

## Câu 10
[TYPE: MULTIPLE_CHOICE]
@PageableDefault(size = 20, sort = "name") dùng để:

A. Giới hạn kết quả trả về
B. Set default pagination parameters nếu client không truyền
C. Bắt buộc client truyền pagination
D. Cache kết quả

**Đáp án: B**
> Default page size=20, sort by name. Client có thể override bằng ?page=0&size=10&sort=date.

## Câu 11
[TYPE: MULTIPLE_CHOICE]
HandlerInterceptor có 3 methods:

A. preHandle, postHandle, afterCompletion
B. before, after, around
C. enter, exit, error
D. init, handle, destroy

**Đáp án: A**
> preHandle (trước controller) → controller → postHandle (sau controller) → view render → afterCompletion (cleanup).

## Câu 12
[TYPE: SELECT_RESULT]
```java
@GetMapping("/files/{fileName:.+}")
public Resource download(@PathVariable String fileName) { ... }
```
`:.+` trong path variable nghĩa là gì?

A. Regex match 1 hoặc nhiều ký tự (bao gồm dấu chấm)
B. Default value
C. Optional parameter
D. Encode URL

**Đáp án: A**
> Mặc định Spring truncate sau dấu chấm cuối. `:.+` giữ lại extension (vd: "report.pdf").

## Câu 13
[TYPE: MULTIPLE_CHOICE]
CORS (Cross-Origin Resource Sharing) cần cấu hình khi:

A. Frontend và backend cùng domain
B. Frontend (localhost:3000) gọi backend (localhost:8080) - khác port
C. Chỉ khi dùng HTTPS
D. Chỉ khi dùng cookie

**Đáp án: B**
> Khác origin (scheme, host, hoặc port) → browser block request. Cần CORS headers cho phép.

## Câu 14
[TYPE: TRUE_FALSE]
ResponseEntity cho phép customize status code, headers, và body trong response.

**Đáp án: TRUE**
> ResponseEntity.status(201).header("X-Custom", "val").body(data) → full control over response.

## Câu 15
[TYPE: MULTIPLE_CHOICE]
@RequestPart dùng cho:

A. Multipart file upload
B. JSON body
C. Path variable
D. Cookie

**Đáp án: A**
> @RequestPart("file") MultipartFile file - dùng trong multipart/form-data requests.
