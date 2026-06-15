# Java Spring Framework - Lý Thuyết Chi Tiết

## Giới thiệu

Spring Framework là nền tảng phát triển ứng dụng Java enterprise phổ biến nhất. **Spring Boot** đơn giản hóa cấu hình và triển khai, giúp tập trung vào business logic.

**Kiến trúc Spring Boot:**

| Thành phần | Vai trò |
|-----------|---------|
| Spring Core | IoC Container, Dependency Injection, Bean lifecycle |
| Spring MVC | Web framework (Controller, REST API) |
| Spring Data JPA | ORM, Repository abstraction |
| Spring Security | Authentication, Authorization |
| Spring AOP | Aspect-Oriented Programming (logging, caching, transactions) |
| Spring Boot Starter | Auto-configuration, embedded server |
| Spring Actuator | Health checks, metrics, monitoring |

**Nguyên tắc cốt lõi:**
- **Convention over Configuration** — Cấu hình mặc định hợp lý, chỉ override khi cần
- **Dependency Injection** — Tách biệt dependencies, dễ test
- **Aspect-Oriented Programming** — Cross-cutting concerns (logging, security, transaction)

---

## Phần 1: Spring Core

### 1.1 IoC (Inversion of Control) và DI (Dependency Injection)

**IoC Container** quản lý vòng đời và dependencies của các đối tượng (beans). Thay vì class tự tạo dependencies, container "inject" chúng vào.

**Tại sao cần DI?**
- **Loose coupling** — class không phụ thuộc vào implementation cụ thể
- **Testability** — dễ mock dependencies trong unit test
- **Flexibility** — swap implementation mà không sửa code client
- **Single Responsibility** — class không lo việc tạo dependencies

**3 loại DI:**

| Loại | Ưu điểm | Nhược điểm | Khi nào dùng |
|------|---------|-----------|-------------|
| Constructor Injection | Immutable, rõ ràng, dễ test, fail-fast | Dài nếu nhiều params | **Mặc định — luôn ưu tiên** |
| Setter Injection | Optional dependencies | Mutable, có thể thiếu | Dependencies tùy chọn |
| Field Injection | Ngắn gọn | Khó test, ẩn dependencies | **Không khuyến nghị** |

```java
// 1. Constructor Injection (Khuyến nghị nhất)
@Service
public class DonHangService {
    private final SanPhamRepository sanPhamRepo;
    private final ThanhToanService thanhToanService;

    // Spring tự inject khi chỉ có 1 constructor (không cần @Autowired)
    public DonHangService(SanPhamRepository sanPhamRepo,
                          ThanhToanService thanhToanService) {
        this.sanPhamRepo = sanPhamRepo;
        this.thanhToanService = thanhToanService;
    }
}

// 2. Setter Injection — dùng cho optional dependencies
@Service
public class BaoCaoService {
    private EmailService emailService;

    @Autowired(required = false)  // Optional
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}

// 3. Field Injection — TRÁNH dùng
@Service
public class NguoiDungService {
    @Autowired
    private NguoiDungRepository repo; // Khó test, ẩn dependencies, không immutable
}
```

> **Phỏng vấn:** Tại sao Constructor Injection tốt nhất? — (1) Dependencies rõ ràng, (2) Field `final` → immutable → thread-safe, (3) Fail-fast nếu thiếu dependency, (4) Dễ mock trong test.

### 1.2 Bean Scopes

| Scope | Mô tả | Lifecycle | Khi nào dùng |
|-------|-------|-----------|-------------|
| `singleton` | **Mặc định.** 1 instance duy nhất trong container | Từ khi tạo đến khi container shutdown | Service, Repository, Controller |
| `prototype` | Instance mới mỗi lần `getBean()` | Container chỉ tạo, KHÔNG quản lý destroy | Stateful object (giỏ hàng) |
| `request` | 1 instance / HTTP request | Bắt đầu → kết thúc request | Request-scoped data |
| `session` | 1 instance / HTTP session | Bắt đầu → kết thúc session | User session data |
| `application` | 1 instance / ServletContext | Từ deploy đến undeploy | App-wide shared data |

```java
@Component
@Scope("prototype")
public class GioHang {
    private List<SanPham> items = new ArrayList<>();
}

// Request scope
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestContext {
    private String requestId = UUID.randomUUID().toString();
}
```

> **Cẩn thận:** Inject prototype bean vào singleton → prototype sẽ "đóng băng" (chỉ tạo 1 lần). Giải pháp: dùng `ObjectProvider<T>` hoặc `@Lookup`.

### 1.3 Bean Lifecycle

```
Constructor → @PostConstruct → afterPropertiesSet() → Custom init → Ready
    ...sử dụng bean...
@PreDestroy → destroy() → Custom destroy → GC
```

```java
@Component
public class CacheManager {

    @PostConstruct  // Chạy sau khi inject xong
    public void khởiTạo() {
        System.out.println("Cache đã khởi tạo");
        loadInitialData();
    }

    @PreDestroy  // Chạy trước khi container shutdown
    public void donDep() {
        System.out.println("Dọn dẹp cache");
        flushToDatabase();
    }
}
```

### 1.4 Configuration và Profiles

```java
// @Configuration — khai báo beans thủ công
@Configuration
public class AppConfig {

    @Bean
    @ConditionalOnProperty(name = "cache.enabled", havingValue = "true")
    public CacheManager cacheManager() {
        return new RedisCacheManager();
    }

    @Bean
    @Profile("dev")  // Chỉ active ở profile dev
    public DataSource devDataSource() {
        return new H2DataSource();
    }

    @Bean
    @Profile("prod")
    public DataSource prodDataSource() {
        return new PostgresDataSource();
    }
}
```

```yaml
# application.yml — cấu hình chính
spring:
  profiles:
    active: dev
  datasource:
    url: jdbc:postgresql://localhost:5432/mydb
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}

# application-dev.yml — override cho dev
spring:
  datasource:
    url: jdbc:h2:mem:testdb

# Sử dụng: java -jar app.jar --spring.profiles.active=prod
```

### 1.5 Spring Annotations tổng hợp

| Annotation | Vị trí | Mô tả |
|-----------|--------|-------|
| `@Component` | Class | Đánh dấu bean chung |
| `@Service` | Class | Bean tầng service (business logic) |
| `@Repository` | Class | Bean tầng data access (exception translation) |
| `@Controller` | Class | Bean tầng web (trả về View) |
| `@RestController` | Class | `@Controller` + `@ResponseBody` (trả về JSON) |
| `@Configuration` | Class | Chứa `@Bean` methods |
| `@Autowired` | Field/Constructor/Setter | Inject dependency |
| `@Qualifier` | Field/Parameter | Chọn bean cụ thể khi có nhiều candidate |
| `@Value` | Field | Inject giá trị từ properties |
| `@Primary` | Class/Method | Bean ưu tiên khi có nhiều candidate |
| `@Lazy` | Class | Khởi tạo lazy (khi dùng mới tạo) |

---

## Phần 2: Spring Web (MVC + REST API)

### 2.1 REST Controller

```java
@RestController
@RequestMapping("/api/nhan-vien")
@RequiredArgsConstructor
public class NhanVienController {

    private final NhanVienService service;

    // GET /api/nhan-vien?page=0&size=10
    @GetMapping
    public ResponseEntity<Page<NhanVienDTO>> layTatCa(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.layTatCa(page, size));
    }

    // GET /api/nhan-vien/1
    @GetMapping("/{id}")
    public ResponseEntity<NhanVienDTO> layTheoId(@PathVariable Long id) {
        return service.layTheoId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/nhan-vien
    @PostMapping
    public ResponseEntity<NhanVienDTO> taoMoi(@Valid @RequestBody NhanVienRequest request) {
        NhanVienDTO created = service.taoMoi(request);
        URI location = URI.create("/api/nhan-vien/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    // PUT /api/nhan-vien/1
    @PutMapping("/{id}")
    public ResponseEntity<NhanVienDTO> capNhat(
            @PathVariable Long id,
            @Valid @RequestBody NhanVienRequest request) {
        return ResponseEntity.ok(service.capNhat(id, request));
    }

    // DELETE /api/nhan-vien/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoa(@PathVariable Long id) {
        service.xoa(id);
        return ResponseEntity.noContent().build();
    }
}
```

**HTTP Methods và REST Convention:**

| Method | URI | Mô tả | Response Code |
|--------|-----|-------|--------------|
| GET | `/api/nhan-vien` | Lấy danh sách | 200 OK |
| GET | `/api/nhan-vien/{id}` | Lấy theo ID | 200 OK / 404 Not Found |
| POST | `/api/nhan-vien` | Tạo mới | 201 Created |
| PUT | `/api/nhan-vien/{id}` | Cập nhật toàn bộ | 200 OK |
| PATCH | `/api/nhan-vien/{id}` | Cập nhật 1 phần | 200 OK |
| DELETE | `/api/nhan-vien/{id}` | Xóa | 204 No Content |

### 2.2 Request/Response Annotations

| Annotation | Mô tả | Ví dụ |
|-----------|-------|-------|
| `@PathVariable` | Tham số trong URL path | `/users/{id}` → `@PathVariable Long id` |
| `@RequestParam` | Query parameter | `?page=0&size=10` → `@RequestParam int page` |
| `@RequestBody` | JSON body → Object | `{"name": "An"}` → `@RequestBody UserDto dto` |
| `@RequestHeader` | HTTP header | `@RequestHeader("Authorization") String token` |
| `@CookieValue` | Cookie value | `@CookieValue("sessionId") String sid` |
| `@Valid` | Kích hoạt validation | `@Valid @RequestBody UserDto dto` |
| `@ResponseStatus` | HTTP status code | `@ResponseStatus(HttpStatus.CREATED)` |

### 2.3 Service Layer

```java
@Service
@RequiredArgsConstructor
public class NhanVienService {

    private final NhanVienRepository repo;

    @Transactional(readOnly = true)  // Tối ưu cho query chỉ đọc
    public Page<NhanVienDTO> layTatCa(int page, int size) {
        return repo.findAll(PageRequest.of(page, size))
            .map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public Optional<NhanVienDTO> layTheoId(Long id) {
        return repo.findById(id).map(this::toDTO);
    }

    @Transactional
    public NhanVienDTO taoMoi(NhanVienRequest request) {
        if (repo.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email đã tồn tại: " + request.getEmail());
        }
        NhanVien nv = new NhanVien();
        nv.setHoTen(request.getHoTen());
        nv.setEmail(request.getEmail());
        nv.setLuong(request.getLuong());
        return toDTO(repo.save(nv));
    }

    @Transactional
    public NhanVienDTO capNhat(Long id, NhanVienRequest request) {
        NhanVien nv = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Nhân viên", "id", id));
        nv.setHoTen(request.getHoTen());
        nv.setEmail(request.getEmail());
        nv.setLuong(request.getLuong());
        return toDTO(repo.save(nv));
    }

    @Transactional
    public void xoa(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Nhân viên", "id", id);
        }
        repo.deleteById(id);
    }

    private NhanVienDTO toDTO(NhanVien nv) {
        return new NhanVienDTO(nv.getId(), nv.getHoTen(), nv.getEmail(), nv.getLuong());
    }
}
```

> **@Transactional:** Nếu có lỗi (RuntimeException), tự rollback. `readOnly = true` cho phép DB tối ưu (không cần dirty checking). Đặt ở service layer, không đặt ở repository hoặc controller.

### 2.4 JPA Repository

```java
public interface NhanVienRepository extends JpaRepository<NhanVien, Long> {

    // ===== Derived Query Methods — Spring tự tạo SQL từ tên method =====
    List<NhanVien> findByPhongBanId(Long phongBanId);
    List<NhanVien> findByHoTenContainingIgnoreCase(String ten);
    Optional<NhanVien> findByEmail(String email);
    boolean existsByEmail(String email);
    List<NhanVien> findByLuongBetween(BigDecimal min, BigDecimal max);
    List<NhanVien> findByTrangThaiTrueOrderByLuongDesc();

    // ===== JPQL — Query trên Entity (không phải table) =====
    @Query("SELECT n FROM NhanVien n WHERE n.luong > :luong AND n.phongBan.id = :pbId")
    List<NhanVien> timTheoLuongVaPhongBan(@Param("luong") BigDecimal luong,
                                           @Param("pbId") Long phongBanId);

    // JPQL với JOIN FETCH — tránh N+1 problem
    @Query("SELECT n FROM NhanVien n JOIN FETCH n.phongBan WHERE n.trangThai = true")
    List<NhanVien> findAllWithPhongBan();

    // ===== Native Query — SQL thuần =====
    @Query(value = "SELECT * FROM nhan_vien WHERE EXTRACT(YEAR FROM ngay_vao_lam) = :nam",
           nativeQuery = true)
    List<NhanVien> timTheoNamVaoLam(@Param("nam") int nam);

    // ===== @Modifying — UPDATE/DELETE =====
    @Modifying
    @Query("UPDATE NhanVien n SET n.luong = n.luong * :heSo WHERE n.phongBan.id = :pbId")
    int tangLuongPhongBan(@Param("pbId") Long phongBanId, @Param("heSo") BigDecimal heSo);

    // ===== Pagination và Sorting =====
    Page<NhanVien> findByPhongBanId(Long phongBanId, Pageable pageable);
    List<NhanVien> findTop5ByOrderByLuongDesc();
}
```

**Derived Query Keywords:**

| Keyword | SQL | Ví dụ |
|---------|-----|-------|
| `findBy` | `SELECT ... WHERE` | `findByEmail(email)` |
| `countBy` | `SELECT COUNT(*) WHERE` | `countByPhongBanId(id)` |
| `existsBy` | `SELECT EXISTS(...)` | `existsByEmail(email)` |
| `deleteBy` | `DELETE ... WHERE` | `deleteByTrangThai(false)` |
| `Containing` | `LIKE %...%` | `findByHoTenContaining("An")` |
| `StartingWith` | `LIKE ...%` | `findByEmailStartingWith("an")` |
| `Between` | `BETWEEN ... AND` | `findByLuongBetween(min, max)` |
| `LessThan/GreaterThan` | `< / >` | `findByLuongGreaterThan(1000)` |
| `OrderBy` | `ORDER BY` | `findByIdOrderByLuongDesc()` |
| `True/False` | `= TRUE/FALSE` | `findByTrangThaiTrue()` |

### 2.5 Entity

```java
@Entity
@Table(name = "nhan_vien",
       indexes = {
           @Index(name = "idx_nv_email", columnList = "email", unique = true),
           @Index(name = "idx_nv_phong_ban", columnList = "phong_ban_id")
       })
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ho_ten", nullable = false, length = 100)
    private String hoTen;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(precision = 12, scale = 2)
    private BigDecimal luong;

    @Column(name = "ngay_vao_lam")
    private LocalDate ngayVaoLam;

    private Boolean trangThai = true;

    // Quan hệ Many-to-One
    @ManyToOne(fetch = FetchType.LAZY)  // LAZY = chỉ load khi truy cập
    @JoinColumn(name = "phong_ban_id")
    private PhongBan phongBan;

    // Quan hệ One-to-Many
    @OneToMany(mappedBy = "nhanVien", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhanCong> phanCongs = new ArrayList<>();

    // Audit fields
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // Getters & Setters...
}
```

**JPA Relationship Annotations:**

| Annotation | Quan hệ | Ví dụ |
|-----------|---------|-------|
| `@ManyToOne` | Nhiều-một | Nhân viên → Phòng ban |
| `@OneToMany` | Một-nhiều | Phòng ban → Nhân viên |
| `@OneToOne` | Một-một | Nhân viên → Hồ sơ |
| `@ManyToMany` | Nhiều-nhiều | Nhân viên ↔ Dự án |

> **N+1 Problem:** `@ManyToOne(fetch = FetchType.EAGER)` gây query cho mỗi entity liên quan. **Luôn dùng LAZY** + `JOIN FETCH` trong JPQL khi cần.

### 2.6 Validation

```java
public class NhanVienRequest {

    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = 2, max = 100, message = "Họ tên từ 2-100 ký tự")
    private String hoTen;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    private String email;

    @NotNull(message = "Lương không được null")
    @DecimalMin(value = "0", message = "Lương phải >= 0")
    @DecimalMax(value = "999999999", message = "Lương quá lớn")
    private BigDecimal luong;

    @Past(message = "Ngày vào làm phải là quá khứ")
    private LocalDate ngayVaoLam;

    @Pattern(regexp = "^\\d{10,11}$", message = "SĐT phải 10-11 chữ số")
    private String soDienThoai;
}

// Custom Validator
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "Email đã tồn tại";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

**Validation Annotations phổ biến:**

| Annotation | Mô tả |
|-----------|-------|
| `@NotNull` | Không null |
| `@NotBlank` | Không null, không rỗng, không chỉ whitespace |
| `@NotEmpty` | Không null, không rỗng (cho String, Collection) |
| `@Size(min, max)` | Độ dài/kích thước |
| `@Min` / `@Max` | Giá trị tối thiểu/tối đa (số nguyên) |
| `@DecimalMin` / `@DecimalMax` | Giá trị tối thiểu/tối đa (decimal) |
| `@Email` | Định dạng email |
| `@Pattern(regexp)` | Regex pattern |
| `@Past` / `@Future` | Ngày quá khứ/tương lai |
| `@Positive` / `@Negative` | Số dương/âm |

### 2.7 Exception Handling

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex) {
        return new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicate(DuplicateResourceException ex) {
        return new ErrorResponse(409, ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e ->
            errors.put(e.getField(), e.getDefaultMessage()));
        return new ErrorResponse(400, "Dữ liệu không hợp lệ", errors, LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleAll(Exception ex) {
        return new ErrorResponse(500, "Lỗi hệ thống", LocalDateTime.now());
    }
}
```

> **Thứ tự xử lý:** Exception cụ thể trước → Exception chung sau. `@RestControllerAdvice` áp dụng cho mọi controller.

---

## Phần 3: Spring Security

### 3.1 Cấu hình Security

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Tắt CSRF cho REST API
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);  // Strength 12
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
```

**Security Filter Chain flow:**
```
Request → CORS → CSRF → Authentication Filter → Authorization → Controller
                          ↓
                   JWT Filter (custom)
                   → Extract token
                   → Validate token
                   → Set SecurityContext
```

### 3.2 JWT Authentication

```java
@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration:86400000}")  // Default 24h
    private long jwtExpiration;

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
            .claim("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList())
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret)
            .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new AuthException("Token đã hết hạn");
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthException("Token không hợp lệ");
        }
    }
}
```

### 3.3 JWT Filter

```java
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain chain) throws ServletException, IOException {
        String token = extractToken(request);

        if (token != null && tokenProvider.validateToken(token)) {
            String username = tokenProvider.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
```

### 3.4 UserDetailsService

```java
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final NguoiDungRepository nguoiDungRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NguoiDung nd = nguoiDungRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy: " + username));

        return User.builder()
            .username(nd.getUsername())
            .password(nd.getPassword())  // Đã hash bằng BCrypt
            .roles(nd.getVaiTro().toArray(new String[0]))
            .accountLocked(!nd.isActive())
            .build();
    }
}
```

---

## Phần 4: Spring AOP

### 4.1 Aspect-Oriented Programming

AOP tách các **cross-cutting concerns** (logging, security, caching, transaction) khỏi business logic.

| Thuật ngữ | Mô tả |
|----------|-------|
| **Aspect** | Module chứa cross-cutting logic (class với `@Aspect`) |
| **Join Point** | Điểm trong code có thể áp dụng aspect (method call) |
| **Pointcut** | Expression xác định join points nào được chọn |
| **Advice** | Code thực thi tại join point (Before, After, Around) |
| **Weaving** | Quá trình kết hợp aspect vào code chính |

```java
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // Pointcut — chọn tất cả method trong service layer
    @Pointcut("execution(* com.study.service.*.*(..))")
    public void serviceLayer() {}

    // Before — chạy trước method
    @Before("serviceLayer()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("→ {}.{}()",
            joinPoint.getTarget().getClass().getSimpleName(),
            joinPoint.getSignature().getName());
    }

    // Around — bọc quanh method (mạnh nhất)
    @Around("serviceLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();  // Gọi method gốc
            long elapsed = System.currentTimeMillis() - start;
            log.info("← {}.{} → {}ms",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(), elapsed);
            return result;
        } catch (Exception ex) {
            log.error("✗ {}.{} failed: {}",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(), ex.getMessage());
            throw ex;
        }
    }

    // AfterThrowing — chạy khi có exception
    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        log.error("Exception tại {}: {}",
            joinPoint.getSignature().getName(), ex.getMessage());
    }
}
```

**Pointcut expressions phổ biến:**

| Expression | Mô tả |
|-----------|-------|
| `execution(* com.study.service.*.*(..))` | Mọi method trong package service |
| `execution(public * *(..))` | Mọi public method |
| `@annotation(Cacheable)` | Method có annotation @Cacheable |
| `within(com.study.controller..*)` | Mọi class trong controller package |
| `args(Long, ..)` | Method có param đầu tiên là Long |

---

## Phần 5: Testing

### 5.1 Unit Test với Mockito

```java
@ExtendWith(MockitoExtension.class)
class NhanVienServiceTest {

    @Mock
    private NhanVienRepository repo;

    @InjectMocks
    private NhanVienService service;

    @Test
    void taoMoi_thanhCong() {
        // Arrange
        NhanVienRequest request = new NhanVienRequest("An", "an@email.com", BigDecimal.valueOf(15000000));
        NhanVien saved = new NhanVien(1L, "An", "an@email.com", BigDecimal.valueOf(15000000));

        when(repo.existsByEmail("an@email.com")).thenReturn(false);
        when(repo.save(any(NhanVien.class))).thenReturn(saved);

        // Act
        NhanVienDTO result = service.taoMoi(request);

        // Assert
        assertNotNull(result);
        assertEquals("An", result.getHoTen());
        assertEquals(1L, result.getId());
        verify(repo, times(1)).save(any(NhanVien.class));
        verify(repo).existsByEmail("an@email.com");
    }

    @Test
    void taoMoi_emailTrung_throwException() {
        NhanVienRequest request = new NhanVienRequest("An", "an@email.com", BigDecimal.valueOf(15000000));
        when(repo.existsByEmail("an@email.com")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> service.taoMoi(request));
        verify(repo, never()).save(any());
    }
}
```

### 5.2 Integration Test

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional  // Rollback sau mỗi test
class NhanVienControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void layTatCa_traVeDanhSach() throws Exception {
        mockMvc.perform(get("/api/nhan-vien")
                .param("page", "0")
                .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(greaterThan(0))))
            .andExpect(jsonPath("$.content[0].hoTen", notNullValue()));
    }

    @Test
    void taoMoi_thanhCong() throws Exception {
        NhanVienRequest request = new NhanVienRequest("Test", "test@email.com", BigDecimal.valueOf(15000000));

        mockMvc.perform(post("/api/nhan-vien")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.hoTen").value("Test"))
            .andExpect(jsonPath("$.email").value("test@email.com"));
    }

    @Test
    void taoMoi_duLieuKhongHopLe_traVe400() throws Exception {
        String json = "{\"hoTen\": \"\", \"email\": \"invalid\"}";

        mockMvc.perform(post("/api/nhan-vien")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors").exists());
    }
}
```

> **Unit Test vs Integration Test:**
> - **Unit Test** (`@ExtendWith(MockitoExtension.class)`): Test 1 class, mock dependencies → nhanh
> - **Integration Test** (`@SpringBootTest`): Load Spring context, test toàn bộ flow → chậm hơn nhưng thực tế hơn

---

## Phần 6: Best Practices Tổng Hợp

| Chủ đề | Nên | Không nên |
|--------|-----|----------|
| DI | Constructor injection, final fields | Field injection, mutable dependencies |
| Transaction | `@Transactional` ở service, `readOnly = true` cho query | Transaction ở controller hoặc repository |
| Fetch Strategy | `FetchType.LAZY` mặc định, `JOIN FETCH` khi cần | `FetchType.EAGER` (gây N+1) |
| DTO | Tách DTO và Entity, dùng DTO cho API response | Expose Entity trực tiếp qua API |
| Exception | `@RestControllerAdvice` tập trung, exception cụ thể | try-catch khắp nơi, return null |
| Validation | `@Valid` + Bean Validation annotations | Validate thủ công trong controller |
| Naming | camelCase method, kebab-case URL, snake_case DB | URL viết hoa, mix style |
| Security | BCrypt password, JWT stateless, HTTPS | Lưu password plaintext |
| Logging | SLF4J + parameterized logging `log.info("msg {}", val)` | `System.out.println`, string concatenation |
| Testing | Mock dependencies, test edge cases, AAA pattern | Test chỉ happy path |
