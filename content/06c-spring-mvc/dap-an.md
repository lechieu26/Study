# Spring MVC & REST API - Đáp Án

## Bài 1: REST API CRUD - Quản Lý Sản Phẩm

### Controller
```java
@RestController
@RequestMapping("/api/san-pham")
public class SanPhamController {
    private final SanPhamService service;

    public SanPhamController(SanPhamService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<Page<SanPhamDTO>> timKiem(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String danhMuc,
            @RequestParam(required = false) BigDecimal giaMin,
            @RequestParam(required = false) BigDecimal giaMax,
            @PageableDefault(size = 10, sort = "ngayTao", direction = Sort.Direction.DESC) Pageable pageable) {
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

### Service
```java
@Service
@Transactional
public class SanPhamService {
    private final SanPhamRepository repo;

    public SanPhamService(SanPhamRepository repo) { this.repo = repo; }

    @Transactional(readOnly = true)
    public Page<SanPhamDTO> timKiem(String keyword, String danhMuc,
                                     BigDecimal giaMin, BigDecimal giaMax, Pageable pageable) {
        return repo.timKiem(keyword, danhMuc, giaMin, giaMax, pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public SanPhamDTO layTheoId(Long id) {
        return repo.findById(id).map(this::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm", "id", id));
    }

    public SanPhamDTO taoMoi(SanPhamRequest request) {
        SanPham sp = new SanPham();
        sp.setTen(request.ten());
        sp.setMoTa(request.moTa());
        sp.setGia(request.gia());
        sp.setSoLuong(request.soLuong());
        sp.setDanhMuc(request.danhMuc());
        sp.setNgayTao(LocalDateTime.now());
        return toDTO(repo.save(sp));
    }

    public SanPhamDTO capNhat(Long id, SanPhamRequest request) {
        SanPham sp = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm", "id", id));
        sp.setTen(request.ten());
        sp.setMoTa(request.moTa());
        sp.setGia(request.gia());
        sp.setSoLuong(request.soLuong());
        sp.setDanhMuc(request.danhMuc());
        return toDTO(repo.save(sp));
    }

    public void xoa(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Sản phẩm", "id", id);
        repo.deleteById(id);
    }

    private SanPhamDTO toDTO(SanPham sp) {
        return new SanPhamDTO(sp.getId(), sp.getTen(), sp.getMoTa(), sp.getGia(), sp.getSoLuong(), sp.getDanhMuc());
    }
}
```

### Exception Handler
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex) {
        return new ErrorResponse(404, ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
            .forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
        return new ErrorResponse(400, "Validation failed", errors, LocalDateTime.now());
    }
}
```
