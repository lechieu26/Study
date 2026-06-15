# Java Spring - Đáp Án

## Bài 1: REST API CRUD - Quản Lý Sản Phẩm

### Entity
```java
@Entity
@Table(name = "san_pham")
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String ten;

    @Column(columnDefinition = "TEXT")
    private String moTa;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal gia;

    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;

    @Column(name = "danh_muc", length = 50)
    private String danhMuc;

    @Column(name = "ngay_tao")
    @CreatedDate
    private LocalDateTime ngayTao;

    // Getters & Setters
}
```

### Repository
```java
public interface SanPhamRepository extends JpaRepository<SanPham, Long>,
                                           JpaSpecificationExecutor<SanPham> {

    List<SanPham> findByDanhMuc(String danhMuc);

    @Query("SELECT sp FROM SanPham sp WHERE " +
           "(:keyword IS NULL OR LOWER(sp.ten) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:danhMuc IS NULL OR sp.danhMuc = :danhMuc) AND " +
           "(:giaMin IS NULL OR sp.gia >= :giaMin) AND " +
           "(:giaMax IS NULL OR sp.gia <= :giaMax)")
    Page<SanPham> timKiem(@Param("keyword") String keyword,
                          @Param("danhMuc") String danhMuc,
                          @Param("giaMin") BigDecimal giaMin,
                          @Param("giaMax") BigDecimal giaMax,
                          Pageable pageable);
}
```

### Service
```java
@Service
@Transactional
public class SanPhamService {
    private final SanPhamRepository repo;

    public SanPhamService(SanPhamRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public Page<SanPhamDTO> timKiem(String keyword, String danhMuc,
                                     BigDecimal giaMin, BigDecimal giaMax,
                                     Pageable pageable) {
        return repo.timKiem(keyword, danhMuc, giaMin, giaMax, pageable)
                   .map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public SanPhamDTO layTheoId(Long id) {
        return repo.findById(id)
            .map(this::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại: " + id));
    }

    public SanPhamDTO taoMoi(SanPhamRequest request) {
        SanPham sp = new SanPham();
        sp.setTen(request.getTen());
        sp.setMoTa(request.getMoTa());
        sp.setGia(request.getGia());
        sp.setSoLuong(request.getSoLuong());
        sp.setDanhMuc(request.getDanhMuc());
        sp.setNgayTao(LocalDateTime.now());
        return toDTO(repo.save(sp));
    }

    public SanPhamDTO capNhat(Long id, SanPhamRequest request) {
        SanPham sp = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại: " + id));
        sp.setTen(request.getTen());
        sp.setMoTa(request.getMoTa());
        sp.setGia(request.getGia());
        sp.setSoLuong(request.getSoLuong());
        sp.setDanhMuc(request.getDanhMuc());
        return toDTO(repo.save(sp));
    }

    public void xoa(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Sản phẩm không tồn tại: " + id);
        }
        repo.deleteById(id);
    }

    private SanPhamDTO toDTO(SanPham sp) {
        return new SanPhamDTO(sp.getId(), sp.getTen(), sp.getMoTa(),
                              sp.getGia(), sp.getSoLuong(), sp.getDanhMuc());
    }
}
```

### Controller
```java
@RestController
@RequestMapping("/api/san-pham")
public class SanPhamController {
    private final SanPhamService service;

    public SanPhamController(SanPhamService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<SanPhamDTO>> timKiem(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String danhMuc,
            @RequestParam(required = false) BigDecimal giaMin,
            @RequestParam(required = false) BigDecimal giaMax,
            @PageableDefault(size = 10, sort = "ngayTao", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(service.timKiem(keyword, danhMuc, giaMin, giaMax, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SanPhamDTO> layTheoId(@PathVariable Long id) {
        return ResponseEntity.ok(service.layTheoId(id));
    }

    @PostMapping
    public ResponseEntity<SanPhamDTO> taoMoi(@Valid @RequestBody SanPhamRequest request) {
        SanPhamDTO created = service.taoMoi(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SanPhamDTO> capNhat(@PathVariable Long id,
                                               @Valid @RequestBody SanPhamRequest request) {
        return ResponseEntity.ok(service.capNhat(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoa(@PathVariable Long id) {
        service.xoa(id);
        return ResponseEntity.noContent().build();
    }
}
```

### Request DTO với Validation
```java
public class SanPhamRequest {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 200, message = "Tên tối đa 200 ký tự")
    private String ten;

    private String moTa;

    @NotNull(message = "Giá không được null")
    @DecimalMin(value = "0.01", message = "Giá phải lớn hơn 0")
    private BigDecimal gia;

    @NotNull(message = "Số lượng không được null")
    @Min(value = 0, message = "Số lượng phải >= 0")
    private Integer soLuong;

    @NotBlank(message = "Danh mục không được để trống")
    private String danhMuc;

    // Getters & Setters
}
```

### Exception Handler
```java
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) { super(message); }
}

public record ErrorResponse(int status, String message, Object errors, LocalDateTime timestamp) {
    public ErrorResponse(int status, String message) {
        this(status, message, null, LocalDateTime.now());
    }
    public ErrorResponse(int status, String message, Object errors) {
        this(status, message, errors, LocalDateTime.now());
    }
}

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(404).body(new ErrorResponse(404, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
          .forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
        return ResponseEntity.badRequest().body(new ErrorResponse(400, "Dữ liệu không hợp lệ", errors));
    }
}
```

---

## Bài 2: JWT Authentication (Tóm tắt cấu trúc)

```java
// AuthController.java
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtProvider;
    private final NguoiDungService nguoiDungService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        nguoiDungService.dangKy(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of("message", "Đăng ký thành công"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String token = jwtProvider.generateToken(auth);
        return ResponseEntity.ok(Map.of("token", token, "type", "Bearer"));
    }
}

// JwtAuthFilter.java
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain chain) throws ServletException, IOException {
        String token = extractToken(request);
        if (token != null && jwtProvider.validateToken(token)) {
            String username = jwtProvider.getUsernameFromToken(token);
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

---

## Bài 3: AOP Annotations

```java
// Custom Annotation
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecutionTime {
    String value() default "";
}

// Aspect
@Aspect
@Component
public class LogExecutionTimeAspect {
    private static final Logger log = LoggerFactory.getLogger(LogExecutionTimeAspect.class);

    @Around("@annotation(logAnnotation)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint,
                                    LogExecutionTime logAnnotation) throws Throwable {
        long start = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();
        try {
            Object result = joinPoint.proceed();
            long elapsed = System.currentTimeMillis() - start;
            log.info("[{}] {} hoàn thành trong {}ms",
                logAnnotation.value().isEmpty() ? "TIMER" : logAnnotation.value(),
                methodName, elapsed);
            return result;
        } catch (Throwable ex) {
            long elapsed = System.currentTimeMillis() - start;
            log.error("[{}] {} lỗi sau {}ms: {}",
                logAnnotation.value(), methodName, elapsed, ex.getMessage());
            throw ex;
        }
    }
}

// Sử dụng
@Service
public class SanPhamService {
    @LogExecutionTime("SanPham.timKiem")
    public Page<SanPhamDTO> timKiem(...) { ... }
}
```

---

## Bài 4-7: Xem đáp án đầy đủ trong ứng dụng web

> Các bài có mức độ phức tạp cao hơn có đáp án chi tiết trong ứng dụng web. Truy cập phần "Java Spring" trên giao diện web để xem và thực hành.
