# Spring MVC & REST API - Lý Thuyết Chi Tiết

## Giới thiệu

**Spring MVC** (Model-View-Controller) là web framework của Spring cho phép xây dựng REST API và web applications. Với Spring Boot, MVC được auto-configure với embedded Tomcat server.

**Kiến trúc Spring MVC:**
```
Client → DispatcherServlet → HandlerMapping → Controller → Service → Repository
                                                    ↓
                          ViewResolver ← View ← Model (cho web pages)
                          HttpMessageConverter ← JSON (cho REST API)
```

---

## 1. REST Controller

### 1.1 @RestController vs @Controller

| Annotation | Trả về | Dùng cho |
|-----------|--------|----------|
| `@Controller` | View name (Thymeleaf, JSP) | Web pages (SSR) |
| `@RestController` | Object → JSON/XML | REST API |

```java
// @RestController = @Controller + @ResponseBody
@RestController
@RequestMapping("/api/san-pham")
public class SanPhamController {

    private final SanPhamService service;

    public SanPhamController(SanPhamService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<SanPhamDTO>> layTatCa(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ngayTao,desc") String[] sort) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        return ResponseEntity.ok(service.layTatCa(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SanPhamDTO> layTheoId(@PathVariable Long id) {
        return ResponseEntity.ok(service.layTheoId(id));
    }

    @PostMapping
    public ResponseEntity<SanPhamDTO> taoMoi(@Valid @RequestBody SanPhamRequest request) {
        SanPhamDTO created = service.taoMoi(request);
        URI location = URI.create("/api/san-pham/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SanPhamDTO> capNhat(
            @PathVariable Long id,
            @Valid @RequestBody SanPhamRequest request) {
        return ResponseEntity.ok(service.capNhat(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SanPhamDTO> capNhatMotPhan(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.capNhatMotPhan(id, updates));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void xoa(@PathVariable Long id) {
        service.xoa(id);
    }
}
```

### 1.2 HTTP Methods và REST Convention

| Method | URI | Action | Response Code | Idempotent |
|--------|-----|--------|--------------|-----------|
| GET | `/api/san-pham` | Lấy danh sách | 200 OK | Có |
| GET | `/api/san-pham/{id}` | Lấy theo ID | 200 OK / 404 | Có |
| POST | `/api/san-pham` | Tạo mới | 201 Created | Không |
| PUT | `/api/san-pham/{id}` | Cập nhật toàn bộ | 200 OK | Có |
| PATCH | `/api/san-pham/{id}` | Cập nhật 1 phần | 200 OK | Không |
| DELETE | `/api/san-pham/{id}` | Xóa | 204 No Content | Có |

---

## 2. Request Handling

### 2.1 Request Annotations

| Annotation | Source | Ví dụ |
|-----------|--------|-------|
| `@PathVariable` | URL path | `/users/{id}` → `@PathVariable Long id` |
| `@RequestParam` | Query string | `?page=0&size=10` → `@RequestParam int page` |
| `@RequestBody` | Request body | JSON → Object |
| `@RequestHeader` | HTTP header | `@RequestHeader("Authorization") String token` |
| `@CookieValue` | Cookie | `@CookieValue("sessionId") String sid` |
| `@ModelAttribute` | Form data | Form fields → Object |
| `@RequestPart` | Multipart | File upload |

### 2.2 @PathVariable

```java
@GetMapping("/phong-ban/{pbId}/nhan-vien/{nvId}")
public ResponseEntity<NhanVienDTO> layNhanVien(
        @PathVariable("pbId") Long phongBanId,
        @PathVariable("nvId") Long nhanVienId) {
    return ResponseEntity.ok(service.layTheoPhongBanVaId(phongBanId, nhanVienId));
}

// Optional PathVariable
@GetMapping({"/reports", "/reports/{year}"})
public ResponseEntity<List<Report>> getReports(
        @PathVariable(required = false) Integer year) {
    if (year == null) year = LocalDate.now().getYear();
    return ResponseEntity.ok(service.getReports(year));
}
```

### 2.3 @RequestParam

```java
@GetMapping("/tim-kiem")
public ResponseEntity<Page<SanPhamDTO>> timKiem(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) String danhMuc,
        @RequestParam(required = false) BigDecimal giaMin,
        @RequestParam(required = false) BigDecimal giaMax,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "ngayTao") String sortBy,
        @RequestParam(defaultValue = "desc") String sortDir) {
    
    Sort sort = sortDir.equalsIgnoreCase("asc") 
        ? Sort.by(sortBy).ascending() 
        : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(page, size, sort);
    
    return ResponseEntity.ok(service.timKiem(keyword, danhMuc, giaMin, giaMax, pageable));
}

// List params: ?ids=1,2,3 hoặc ?ids=1&ids=2&ids=3
@DeleteMapping("/batch")
public ResponseEntity<Void> xoaNhieu(@RequestParam List<Long> ids) {
    service.xoaNhieu(ids);
    return ResponseEntity.noContent().build();
}
```

### 2.4 @RequestBody

```java
@PostMapping
public ResponseEntity<NhanVienDTO> taoMoi(
        @Valid @RequestBody NhanVienRequest request) {
    // JSON body → NhanVienRequest object
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(service.taoMoi(request));
}

// DTO Pattern
public record NhanVienRequest(
    @NotBlank(message = "Họ tên không được trống")
    @Size(min = 2, max = 100, message = "Họ tên 2-100 ký tự")
    String hoTen,

    @NotBlank @Email(message = "Email không hợp lệ")
    String email,

    @NotNull @Positive(message = "Lương phải > 0")
    BigDecimal luong,

    @NotNull
    Long phongBanId
) {}
```

### 2.5 File Upload

```java
@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<FileResponse> upload(
        @RequestPart("file") MultipartFile file,
        @RequestPart(value = "metadata", required = false) FileMetadata metadata) {
    
    if (file.isEmpty()) {
        throw new BadRequestException("File trống");
    }
    if (file.getSize() > 10 * 1024 * 1024) { // 10MB
        throw new BadRequestException("File quá lớn (max 10MB)");
    }
    
    String savedPath = fileService.save(file);
    return ResponseEntity.ok(new FileResponse(savedPath, file.getOriginalFilename()));
}

// Config
spring:
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 20MB
```

---

## 3. Response Handling

### 3.1 ResponseEntity

```java
@GetMapping("/{id}")
public ResponseEntity<SanPhamDTO> layTheoId(@PathVariable Long id) {
    SanPhamDTO sp = service.layTheoId(id);
    
    return ResponseEntity
        .ok()                                    // 200
        .header("X-Custom-Header", "value")     // Custom header
        .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))  // Cache
        .body(sp);
}

@PostMapping
public ResponseEntity<SanPhamDTO> taoMoi(@Valid @RequestBody SanPhamRequest request) {
    SanPhamDTO created = service.taoMoi(request);
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(created.getId())
        .toUri();
    
    return ResponseEntity
        .created(location)  // 201 + Location header
        .body(created);
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> xoa(@PathVariable Long id) {
    service.xoa(id);
    return ResponseEntity.noContent().build();  // 204
}
```

### 3.2 Response DTO Pattern

```java
// Generic wrapper cho API response
public record ApiResponse<T>(
    boolean success,
    String message,
    T data,
    LocalDateTime timestamp
) {
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, "Thành công", data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, LocalDateTime.now());
    }
}

// Sử dụng
@GetMapping("/{id}")
public ResponseEntity<ApiResponse<SanPhamDTO>> layTheoId(@PathVariable Long id) {
    return ResponseEntity.ok(ApiResponse.ok(service.layTheoId(id)));
}
```

### 3.3 Pagination Response

```java
@GetMapping
public ResponseEntity<PageResponse<SanPhamDTO>> layTatCa(
        @PageableDefault(size = 10, sort = "ngayTao", direction = Sort.Direction.DESC)
        Pageable pageable) {
    Page<SanPhamDTO> page = service.layTatCa(pageable);
    return ResponseEntity.ok(PageResponse.of(page));
}

// Custom Page Response
public record PageResponse<T>(
    List<T> content,
    int page,
    int size,
    long totalElements,
    int totalPages,
    boolean last
) {
    public static <T> PageResponse<T> of(Page<T> page) {
        return new PageResponse<>(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isLast()
        );
    }
}
```

---

## 4. Validation

### 4.1 Bean Validation Annotations

| Annotation | Mô tả | Ví dụ |
|-----------|-------|-------|
| `@NotNull` | Không null | `@NotNull Long id` |
| `@NotBlank` | Không null/empty/whitespace | `@NotBlank String ten` |
| `@NotEmpty` | Không null/empty | `@NotEmpty List<String> items` |
| `@Size` | Kích thước min/max | `@Size(min=2, max=100) String ten` |
| `@Min` / `@Max` | Giá trị số min/max | `@Min(0) int soLuong` |
| `@Positive` | > 0 | `@Positive BigDecimal gia` |
| `@Email` | Email hợp lệ | `@Email String email` |
| `@Pattern` | Regex pattern | `@Pattern(regexp="^\\d{10}$") String sdt` |
| `@Past` / `@Future` | Ngày trong quá khứ/tương lai | `@Past LocalDate ngaySinh` |
| `@Valid` | Validate nested object | `@Valid Address diaChi` |

### 4.2 Custom Validator

```java
// 1. Custom annotation
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {
    String message() default "Email đã tồn tại";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

// 2. Validator implementation
@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;

    public UniqueEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) return true;  // @NotNull xử lý null
        return !userRepository.existsByEmail(email);
    }
}

// 3. Sử dụng
public record RegisterRequest(
    @NotBlank String username,
    @UniqueEmail String email,
    @Size(min = 8) String password
) {}
```

### 4.3 Validation Groups

```java
// Định nghĩa groups
public interface OnCreate {}
public interface OnUpdate {}

// DTO với groups
public class SanPhamRequest {
    @Null(groups = OnCreate.class)  // Không truyền ID khi tạo
    @NotNull(groups = OnUpdate.class)  // Phải có ID khi update
    private Long id;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    private String ten;

    @NotNull(groups = OnCreate.class)
    private BigDecimal gia;
}

// Controller sử dụng
@PostMapping
public ResponseEntity<SanPhamDTO> taoMoi(
        @Validated(OnCreate.class) @RequestBody SanPhamRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.taoMoi(request));
}

@PutMapping("/{id}")
public ResponseEntity<SanPhamDTO> capNhat(
        @PathVariable Long id,
        @Validated(OnUpdate.class) @RequestBody SanPhamRequest request) {
    return ResponseEntity.ok(service.capNhat(id, request));
}
```

---

## 5. Exception Handling

### 5.1 @RestControllerAdvice

```java
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex, WebRequest request) {
        log.warn("Resource not found: {}", ex.getMessage());
        return ErrorResponse.builder()
            .status(404)
            .error("Not Found")
            .message(ex.getMessage())
            .path(((ServletWebRequest) request).getRequest().getRequestURI())
            .timestamp(LocalDateTime.now())
            .build();
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicate(DuplicateResourceException ex) {
        return new ErrorResponse(409, "Conflict", ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
            .forEach(e -> fieldErrors.put(e.getField(), e.getDefaultMessage()));
        
        return ErrorResponse.builder()
            .status(400)
            .error("Validation Failed")
            .message("Dữ liệu đầu vào không hợp lệ")
            .fieldErrors(fieldErrors)
            .timestamp(LocalDateTime.now())
            .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = ex.getConstraintViolations().stream()
            .collect(Collectors.toMap(
                v -> v.getPropertyPath().toString(),
                ConstraintViolation::getMessage
            ));
        return new ErrorResponse(400, "Validation Failed", errors, LocalDateTime.now());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadJson(HttpMessageNotReadableException ex) {
        return new ErrorResponse(400, "Invalid JSON", "Request body không đúng format JSON", LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleAll(Exception ex) {
        log.error("Unhandled exception", ex);
        return new ErrorResponse(500, "Internal Server Error", "Lỗi hệ thống", LocalDateTime.now());
    }
}
```

### 5.2 Custom Exceptions

```java
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s không tìm thấy với %s = '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}

public class BusinessException extends RuntimeException {
    private final String errorCode;
    
    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
```

---

## 6. Content Negotiation

### 6.1 JSON & XML

```java
@GetMapping(value = "/{id}", produces = {
    MediaType.APPLICATION_JSON_VALUE,
    MediaType.APPLICATION_XML_VALUE
})
public ResponseEntity<SanPhamDTO> layTheoId(
        @PathVariable Long id,
        @RequestHeader(value = "Accept", defaultValue = "application/json") String accept) {
    return ResponseEntity.ok(service.layTheoId(id));
}
```

### 6.2 Jackson Configuration

```java
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}

// DTO với Jackson annotations
public class NhanVienDTO {
    private Long id;
    
    @JsonProperty("full_name")  // Tên khác trong JSON
    private String hoTen;
    
    @JsonIgnore  // Không serialize
    private String password;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime ngayTao;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> roles;
}
```

---

## 7. Interceptors và Filters

### 7.1 HandlerInterceptor

```java
@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
                            Object handler) {
        request.setAttribute("startTime", System.currentTimeMillis());
        log.info("→ {} {}", request.getMethod(), request.getRequestURI());
        return true;  // true = tiếp tục, false = dừng
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                          Object handler, ModelAndView modelAndView) {
        // Sau khi controller xử lý, trước khi render view
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                               Object handler, Exception ex) {
        long start = (Long) request.getAttribute("startTime");
        long elapsed = System.currentTimeMillis() - start;
        log.info("← {} {} → {}ms [{}]", 
            request.getMethod(), request.getRequestURI(), elapsed, response.getStatus());
    }
}

// Đăng ký interceptor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    private final RequestLoggingInterceptor loggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor)
            .addPathPatterns("/api/**")
            .excludePathPatterns("/api/auth/**");
    }
}
```

### 7.2 Filter

```java
@Component
@Order(1)
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        chain.doFilter(request, response);
    }
}
```

---

## 8. CORS Configuration

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("http://localhost:3000", "https://myapp.com")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
            .allowedHeaders("*")
            .exposedHeaders("X-Total-Count", "X-Page-Number")
            .allowCredentials(true)
            .maxAge(3600);  // Preflight cache 1 hour
    }
}
```

---

## 9. Async Controller

```java
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @GetMapping("/generate")
    public CompletableFuture<ResponseEntity<ReportDTO>> generateReport(
            @RequestParam String type) {
        return reportService.generateAsync(type)
            .thenApply(ResponseEntity::ok);
    }

    // Streaming response
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> streamEvents() {
        return Flux.interval(Duration.ofSeconds(1))
            .map(seq -> ServerSentEvent.<String>builder()
                .id(String.valueOf(seq))
                .event("message")
                .data("Event " + seq)
                .build());
    }
}
```

---

## 10. Service Layer Pattern

```java
@Service
@Transactional
public class NhanVienService {

    private final NhanVienRepository repo;
    private final PhongBanRepository phongBanRepo;
    private final NhanVienMapper mapper;

    public NhanVienService(NhanVienRepository repo, 
                           PhongBanRepository phongBanRepo,
                           NhanVienMapper mapper) {
        this.repo = repo;
        this.phongBanRepo = phongBanRepo;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public Page<NhanVienDTO> layTatCa(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    public NhanVienDTO layTheoId(Long id) {
        return repo.findById(id)
            .map(mapper::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Nhân viên", "id", id));
    }

    public NhanVienDTO taoMoi(NhanVienRequest request) {
        if (repo.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email đã tồn tại: " + request.email());
        }
        PhongBan pb = phongBanRepo.findById(request.phongBanId())
            .orElseThrow(() -> new ResourceNotFoundException("Phòng ban", "id", request.phongBanId()));

        NhanVien nv = mapper.toEntity(request);
        nv.setPhongBan(pb);
        return mapper.toDTO(repo.save(nv));
    }

    public NhanVienDTO capNhat(Long id, NhanVienRequest request) {
        NhanVien nv = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Nhân viên", "id", id));
        mapper.updateEntity(request, nv);
        return mapper.toDTO(repo.save(nv));
    }

    public void xoa(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Nhân viên", "id", id);
        }
        repo.deleteById(id);
    }
}
```

---

## 11. DTO Mapping

```java
// MapStruct (code generation - nhanh nhất)
@Mapper(componentModel = "spring")
public interface NhanVienMapper {
    
    NhanVienDTO toDTO(NhanVien entity);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ngayTao", expression = "java(java.time.LocalDateTime.now())")
    NhanVien toEntity(NhanVienRequest request);
    
    @Mapping(target = "id", ignore = true)
    void updateEntity(NhanVienRequest request, @MappingTarget NhanVien entity);
}

// Manual mapping (khi không dùng MapStruct)
@Component
public class NhanVienMapper {
    
    public NhanVienDTO toDTO(NhanVien entity) {
        return new NhanVienDTO(
            entity.getId(),
            entity.getHoTen(),
            entity.getEmail(),
            entity.getLuong(),
            entity.getPhongBan() != null ? entity.getPhongBan().getTen() : null
        );
    }
}
```

---

## 12. Best Practices

| Chủ đề | Nên | Không nên |
|--------|-----|----------|
| Controller | Thin controller, chỉ gọi service | Business logic trong controller |
| Response | `ResponseEntity` với status code chuẩn | Return Object trực tiếp |
| DTO | Tách Request/Response DTO | Expose Entity qua API |
| Validation | `@Valid` + Bean Validation | Validate thủ công |
| Exception | `@RestControllerAdvice` tập trung | try-catch trong controller |
| URL | lowercase, kebab-case, noun plural | camelCase, verb in URL |
| Pagination | `Pageable` + `Page<T>` | Return toàn bộ data |
| CORS | Cấu hình explicit origins | `allowedOrigins("*")` ở prod |
