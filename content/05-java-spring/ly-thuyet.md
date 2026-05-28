# Java Spring Framework - Lý Thuyết Chi Tiết

## Phần 1: Spring Core

### 1.1 IoC (Inversion of Control) và DI (Dependency Injection)

**IoC Container** quản lý vòng đời và dependencies của các đối tượng (beans).

**Các loại DI:**
```java
// 1. Constructor Injection (Khuyến nghị)
@Service
public class DonHangService {
    private final SanPhamRepository sanPhamRepo;
    private final ThanhToanService thanhToanService;

    public DonHangService(SanPhamRepository sanPhamRepo,
                          ThanhToanService thanhToanService) {
        this.sanPhamRepo = sanPhamRepo;
        this.thanhToanService = thanhToanService;
    }
}

// 2. Setter Injection
@Service
public class BaoCaoService {
    private EmailService emailService;

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}

// 3. Field Injection (Không khuyến nghị)
@Service
public class NguoiDungService {
    @Autowired
    private NguoiDungRepository repo; // Khó test, ẩn dependencies
}
```

### 1.2 Bean Scopes
| Scope | Mô tả |
|-------|-------|
| singleton | Mặc định. 1 instance duy nhất trong container |
| prototype | Tạo instance mới mỗi lần request |
| request | 1 instance cho mỗi HTTP request (Web) |
| session | 1 instance cho mỗi HTTP session (Web) |
| application | 1 instance cho mỗi ServletContext |

```java
@Component
@Scope("prototype")
public class GioHang {
    private List<SanPham> items = new ArrayList<>();
}
```

### 1.3 Bean Lifecycle
```
Constructor → @PostConstruct → afterPropertiesSet() → init-method
    → Sử dụng →
@PreDestroy → destroy() → destroy-method
```

```java
@Component
public class CacheManager {
    @PostConstruct
    public void init() {
        System.out.println("Khởi tạo cache...");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("Dọn dẹp cache...");
    }
}
```

### 1.4 Annotation quan trọng
| Annotation | Mô tả |
|-----------|-------|
| @Component | Đánh dấu class là Spring bean |
| @Service | Tầng business logic |
| @Repository | Tầng data access |
| @Controller | Tầng web (trả về view) |
| @RestController | Tầng REST API (= @Controller + @ResponseBody) |
| @Configuration | Class cấu hình (thay XML) |
| @Bean | Khai báo bean trong @Configuration |
| @Autowired | Tự động inject dependency |
| @Qualifier | Chỉ định bean cụ thể khi có nhiều implementation |
| @Value | Inject giá trị từ properties |
| @Profile | Kích hoạt bean theo profile (dev, prod) |
| @Conditional | Kích hoạt bean theo điều kiện |

---

## Phần 2: Spring Boot

### 2.1 Khởi tạo dự án
```java
@SpringBootApplication // = @Configuration + @EnableAutoConfiguration + @ComponentScan
public class StudyApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);
    }
}
```

### 2.2 Cấu hình application.properties / application.yml
```yaml
# application.yml
server:
  port: 8080
  servlet:
    context-path: /api

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/study_db
    username: postgres
    password: secret
  jpa:
    hibernate:
      ddl-auto: update  # create, create-drop, validate, none
    show-sql: true
    properties:
      hibernate:
        format_sql: true
  profiles:
    active: dev

# Custom properties
app:
  jwt:
    secret: mySecretKey
    expiration: 86400000
```

### 2.3 REST Controller
```java
@RestController
@RequestMapping("/api/nhan-vien")
public class NhanVienController {

    private final NhanVienService service;

    public NhanVienController(NhanVienService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<NhanVienDTO>> layTatCa(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.layTatCa(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NhanVienDTO> layTheoId(@PathVariable Long id) {
        return service.layTheoId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NhanVienDTO> taoMoi(
            @Valid @RequestBody NhanVienRequest request) {
        NhanVienDTO created = service.taoMoi(request);
        URI location = URI.create("/api/nhan-vien/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NhanVienDTO> capNhat(
            @PathVariable Long id,
            @Valid @RequestBody NhanVienRequest request) {
        return ResponseEntity.ok(service.capNhat(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoa(@PathVariable Long id) {
        service.xoa(id);
        return ResponseEntity.noContent().build();
    }
}
```

### 2.4 Service Layer
```java
@Service
@Transactional
public class NhanVienService {

    private final NhanVienRepository repo;

    public NhanVienService(NhanVienRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<NhanVienDTO> layTatCa(int page, int size) {
        return repo.findAll(PageRequest.of(page, size))
            .map(this::toDTO)
            .getContent();
    }

    public NhanVienDTO taoMoi(NhanVienRequest request) {
        NhanVien nv = new NhanVien();
        nv.setHoTen(request.getHoTen());
        nv.setEmail(request.getEmail());
        nv.setLuong(request.getLuong());
        return toDTO(repo.save(nv));
    }

    private NhanVienDTO toDTO(NhanVien nv) {
        return new NhanVienDTO(nv.getId(), nv.getHoTen(), nv.getEmail(), nv.getLuong());
    }
}
```

### 2.5 JPA Repository
```java
public interface NhanVienRepository extends JpaRepository<NhanVien, Long> {

    // Derived Query Methods
    List<NhanVien> findByPhongBanId(Long phongBanId);
    List<NhanVien> findByHoTenContainingIgnoreCase(String ten);
    Optional<NhanVien> findByEmail(String email);
    List<NhanVien> findByLuongBetween(BigDecimal min, BigDecimal max);
    List<NhanVien> findByTrangThaiTrueOrderByLuongDesc();

    // JPQL
    @Query("SELECT n FROM NhanVien n WHERE n.luong > :luong AND n.phongBan.id = :pbId")
    List<NhanVien> timTheoLuongVaPhongBan(@Param("luong") BigDecimal luong,
                                           @Param("pbId") Long phongBanId);

    // Native Query
    @Query(value = "SELECT * FROM nhan_vien WHERE EXTRACT(YEAR FROM ngay_vao_lam) = :nam",
           nativeQuery = true)
    List<NhanVien> timTheoNamVaoLam(@Param("nam") int nam);

    // Modifying
    @Modifying
    @Query("UPDATE NhanVien n SET n.luong = n.luong * :heSo WHERE n.phongBan.id = :pbId")
    int tangLuongPhongBan(@Param("pbId") Long phongBanId, @Param("heSo") BigDecimal heSo);
}
```

### 2.6 Entity
```java
@Entity
@Table(name = "nhan_vien")
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ho_ten", nullable = false, length = 100)
    private String hoTen;

    @Column(unique = true)
    private String email;

    @Column(precision = 12, scale = 2)
    private BigDecimal luong;

    @Column(name = "ngay_vao_lam")
    private LocalDate ngayVaoLam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phong_ban_id")
    private PhongBan phongBan;

    @OneToMany(mappedBy = "nhanVien", cascade = CascadeType.ALL)
    private List<PhanCong> phanCongs = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // Getters & Setters...
}
```

### 2.7 Validation
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
    private BigDecimal luong;
}
```

### 2.8 Exception Handling
```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e ->
            errors.put(e.getField(), e.getDefaultMessage()));
        ErrorResponse error = new ErrorResponse(400, "Dữ liệu không hợp lệ", errors);
        return ResponseEntity.badRequest().body(error);
    }
}
```

---

## Phần 3: Spring Security

### 3.1 Cấu hình Security
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

### 3.2 JWT Authentication
```java
@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
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
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
```

### 3.3 UserDetailsService
```java
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final NguoiDungRepository nguoiDungRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NguoiDung nd = nguoiDungRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy: " + username));

        return User.builder()
            .username(nd.getUsername())
            .password(nd.getPassword())
            .roles(nd.getVaiTro().toArray(new String[0]))
            .build();
    }
}
```

---

## Phần 4: Spring AOP

### 4.1 Aspect-Oriented Programming
```java
@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    // Pointcut
    @Pointcut("execution(* com.study.service.*.*(..))")
    public void serviceLayer() {}

    // Before advice
    @Before("serviceLayer()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Gọi: {}.{}()",
            joinPoint.getTarget().getClass().getSimpleName(),
            joinPoint.getSignature().getName());
    }

    // Around advice - đo thời gian
    @Around("serviceLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsed = System.currentTimeMillis() - start;
        log.info("{}.{} hoàn thành trong {}ms",
            joinPoint.getTarget().getClass().getSimpleName(),
            joinPoint.getSignature().getName(), elapsed);
        return result;
    }

    // After throwing
    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        log.error("Lỗi tại {}: {}", joinPoint.getSignature().getName(), ex.getMessage());
    }
}
```

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
        NhanVienRequest request = new NhanVienRequest("An", "an@email.com", BigDecimal.valueOf(15000000));
        NhanVien saved = new NhanVien(1L, "An", "an@email.com", BigDecimal.valueOf(15000000));

        when(repo.save(any(NhanVien.class))).thenReturn(saved);

        NhanVienDTO result = service.taoMoi(request);

        assertNotNull(result);
        assertEquals("An", result.getHoTen());
        verify(repo, times(1)).save(any(NhanVien.class));
    }
}
```

### 5.2 Integration Test
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class NhanVienControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void layTatCa_traVeDanhSach() throws Exception {
        mockMvc.perform(get("/api/nhan-vien"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    void taoMoi_duLieuKhongHopLe_traVe400() throws Exception {
        String json = "{\"hoTen\": \"\", \"email\": \"invalid\"}";
        mockMvc.perform(post("/api/nhan-vien")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
    }
}
```
