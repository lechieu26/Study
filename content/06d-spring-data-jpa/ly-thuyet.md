# Spring Data JPA - Lý Thuyết Chi Tiết

## Giới thiệu

**Spring Data JPA** cung cấp abstraction layer trên JPA (Java Persistence API), giúp giảm boilerplate code cho data access. Với repository interfaces, Spring tự generate implementation cho các CRUD operations và custom queries.

**Stack:**
```
Application Code
    ↓
Spring Data JPA (Repository abstraction)
    ↓
JPA (Jakarta Persistence API - specification)
    ↓
Hibernate (JPA implementation)
    ↓
JDBC
    ↓
Database (PostgreSQL, MySQL, H2...)
```

---

## 1. Entity Mapping

### 1.1 Basic Entity

```java
@Entity
@Table(name = "nhan_vien", indexes = {
    @Index(name = "idx_nv_email", columnList = "email", unique = true),
    @Index(name = "idx_nv_phong_ban", columnList = "phong_ban_id")
})
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ho_ten", nullable = false, length = 100)
    private String hoTen;

    @Column(unique = true, nullable = false, length = 150)
    private String email;

    @Column(precision = 12, scale = 2)
    private BigDecimal luong;

    @Column(name = "ngay_vao_lam")
    private LocalDate ngayVaoLam;

    @Column(name = "trang_thai")
    private Boolean trangThai = true;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ChucVu chucVu;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors, Getters & Setters
}
```

### 1.2 ID Generation Strategies

| Strategy | Mô tả | DB hỗ trợ | Khi nào dùng |
|----------|-------|-----------|-------------|
| `IDENTITY` | Auto-increment column | MySQL, PostgreSQL, H2 | Đơn giản, mặc định |
| `SEQUENCE` | Database sequence | PostgreSQL, Oracle | Performance cao (batch insert) |
| `TABLE` | Bảng riêng lưu sequence | Tất cả | Cross-database portable |
| `UUID` | UUID generated | Tất cả | Distributed systems |
| `AUTO` | JPA tự chọn | Tùy DB | Không khuyến nghị (không predict) |

```java
// SEQUENCE - tốt cho PostgreSQL
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nv_seq")
@SequenceGenerator(name = "nv_seq", sequenceName = "nhan_vien_id_seq", allocationSize = 50)
private Long id;

// UUID
@Id
@GeneratedValue(strategy = GenerationType.UUID)
private UUID id;
```

### 1.3 Column Types

```java
@Entity
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String ten;

    @Column(columnDefinition = "TEXT")  // Large text
    private String moTa;

    @Column(precision = 12, scale = 2)  // DECIMAL(12,2)
    private BigDecimal gia;

    @Lob  // BLOB/CLOB
    private byte[] hinhAnh;

    @Column(name = "metadata", columnDefinition = "jsonb")  // PostgreSQL JSON
    private String metadata;

    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayTao;

    // Java 8 Date/Time API (không cần @Temporal)
    private LocalDate ngayBatDau;
    private LocalDateTime thoiGianTao;
    private Instant lastModified;
}
```

---

## 2. Relationships

### 2.1 @ManyToOne và @OneToMany

```java
// Many-to-One (nhiều nhân viên thuộc 1 phòng ban)
@Entity
public class NhanVien {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hoTen;

    @ManyToOne(fetch = FetchType.LAZY)  // LAZY = không load phòng ban khi load nhân viên
    @JoinColumn(name = "phong_ban_id", nullable = false)
    private PhongBan phongBan;
}

// One-to-Many (1 phòng ban có nhiều nhân viên)
@Entity
public class PhongBan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ten;

    @OneToMany(mappedBy = "phongBan",      // Tên field ở entity con
               cascade = CascadeType.ALL,   // Cascade operations
               orphanRemoval = true)         // Xóa entity con khi remove khỏi list
    private List<NhanVien> nhanViens = new ArrayList<>();

    // Helper methods (quan trọng!)
    public void themNhanVien(NhanVien nv) {
        nhanViens.add(nv);
        nv.setPhongBan(this);
    }

    public void xoaNhanVien(NhanVien nv) {
        nhanViens.remove(nv);
        nv.setPhongBan(null);
    }
}
```

### 2.2 @ManyToMany

```java
// Many-to-Many qua bảng trung gian
@Entity
public class SinhVien {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hoTen;

    @ManyToMany
    @JoinTable(
        name = "sinh_vien_khoa_hoc",        // Tên bảng trung gian
        joinColumns = @JoinColumn(name = "sinh_vien_id"),
        inverseJoinColumns = @JoinColumn(name = "khoa_hoc_id")
    )
    private Set<KhoaHoc> khoaHocs = new HashSet<>();
}

@Entity
public class KhoaHoc {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ten;

    @ManyToMany(mappedBy = "khoaHocs")
    private Set<SinhVien> sinhViens = new HashSet<>();
}
```

### 2.3 @ManyToMany với extra columns (Bảng trung gian có attribute)

```java
// Khi bảng trung gian có thêm cột (ngày đăng ký, điểm...)
// → Tạo Entity riêng cho bảng trung gian

@Entity
@Table(name = "dang_ky")
public class DangKy {
    @EmbeddedId
    private DangKyId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("sinhVienId")
    @JoinColumn(name = "sinh_vien_id")
    private SinhVien sinhVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("khoaHocId")
    @JoinColumn(name = "khoa_hoc_id")
    private KhoaHoc khoaHoc;

    private LocalDate ngayDangKy;
    private Double diemSo;

    @Enumerated(EnumType.STRING)
    private TrangThaiDangKy trangThai;
}

@Embeddable
public class DangKyId implements Serializable {
    private Long sinhVienId;
    private Long khoaHocId;
    // equals(), hashCode()
}
```

### 2.4 @OneToOne

```java
@Entity
public class NguoiDung {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @OneToOne(mappedBy = "nguoiDung", cascade = CascadeType.ALL, 
              fetch = FetchType.LAZY, optional = false)
    private HoSo hoSo;
}

@Entity
public class HoSo {
    @Id
    private Long id;  // Share PK with NguoiDung

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId  // Dùng chung ID với NguoiDung
    @JoinColumn(name = "id")
    private NguoiDung nguoiDung;

    private String diaChi;
    private String soDienThoai;
}
```

### 2.5 Fetch Types

| Type | Behavior | Default cho | Nên dùng |
|------|----------|-------------|----------|
| `LAZY` | Load khi truy cập field | `@OneToMany`, `@ManyToMany` | **Luôn ưu tiên** |
| `EAGER` | Load cùng lúc entity cha | `@ManyToOne`, `@OneToOne` | **Tránh** (gây N+1) |

```java
// LUÔN đặt LAZY cho @ManyToOne
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "phong_ban_id")
private PhongBan phongBan;

// Khi cần load → dùng JOIN FETCH trong query
@Query("SELECT n FROM NhanVien n JOIN FETCH n.phongBan WHERE n.id = :id")
Optional<NhanVien> findByIdWithPhongBan(@Param("id") Long id);
```

### 2.6 Cascade Types

| Cascade | Mô tả | Khi nào dùng |
|---------|-------|-------------|
| `PERSIST` | Save parent → save children | Parent owns children |
| `MERGE` | Update parent → update children | Parent owns children |
| `REMOVE` | Delete parent → delete children | Children meaningless without parent |
| `ALL` | Tất cả operations | Parent completely owns children |
| `DETACH` | Detach parent → detach children | Ít dùng |

> **Cẩn thận:** KHÔNG cascade từ Many side (ManyToOne). Chỉ cascade từ One side (OneToMany).

---

## 3. Repository

### 3.1 Repository Hierarchy

```
Repository (marker interface)
    ↓
CrudRepository (CRUD operations)
    ↓
ListCrudRepository (return List instead of Iterable)
    ↓
PagingAndSortingRepository (+ paging, sorting)
    ↓
JpaRepository (+ flush, batch, deleteInBatch, JPA specifics)
```

### 3.2 JpaRepository Methods

```java
public interface NhanVienRepository extends JpaRepository<NhanVien, Long> {
    // Kế thừa sẵn:
    // save(entity), saveAll(entities)
    // findById(id), findAll(), findAll(Pageable), findAll(Sort)
    // existsById(id), count()
    // deleteById(id), delete(entity), deleteAll()
    // flush(), saveAndFlush(entity)
}
```

### 3.3 Derived Query Methods

Spring tự generate SQL từ tên method:

```java
public interface NhanVienRepository extends JpaRepository<NhanVien, Long> {

    // ===== Tìm kiếm =====
    List<NhanVien> findByHoTen(String hoTen);
    List<NhanVien> findByHoTenContainingIgnoreCase(String keyword);
    List<NhanVien> findByEmailEndingWith(String domain);
    Optional<NhanVien> findByEmail(String email);
    
    // ===== So sánh =====
    List<NhanVien> findByLuongGreaterThan(BigDecimal min);
    List<NhanVien> findByLuongBetween(BigDecimal min, BigDecimal max);
    List<NhanVien> findByNgayVaoLamAfter(LocalDate date);
    
    // ===== Logic =====
    List<NhanVien> findByTrangThaiTrueAndPhongBanId(Long pbId);
    List<NhanVien> findByChucVuInAndLuongGreaterThan(List<ChucVu> chucVus, BigDecimal minLuong);
    
    // ===== Sắp xếp & giới hạn =====
    List<NhanVien> findTop5ByOrderByLuongDesc();
    List<NhanVien> findByPhongBanIdOrderByHoTenAsc(Long pbId);
    
    // ===== Kiểm tra & đếm =====
    boolean existsByEmail(String email);
    long countByPhongBanId(Long phongBanId);
    
    // ===== Xóa =====
    void deleteByTrangThaiFalse();
    
    // ===== Pagination =====
    Page<NhanVien> findByPhongBanId(Long pbId, Pageable pageable);
    Slice<NhanVien> findByChucVu(ChucVu chucVu, Pageable pageable);
}
```

**Derived Query Keywords:**

| Keyword | SQL | Ví dụ method |
|---------|-----|-------------|
| `findBy` | `SELECT ... WHERE` | `findByEmail(email)` |
| `countBy` | `SELECT COUNT(*)` | `countByPhongBanId(id)` |
| `existsBy` | `SELECT EXISTS` | `existsByEmail(email)` |
| `deleteBy` | `DELETE ... WHERE` | `deleteByTrangThai(false)` |
| `Containing` | `LIKE %...%` | `findByHoTenContaining("An")` |
| `StartingWith` | `LIKE ...%` | `findByEmailStartingWith("a")` |
| `EndingWith` | `LIKE %...` | `findByEmailEndingWith("@gmail.com")` |
| `Between` | `BETWEEN` | `findByLuongBetween(min, max)` |
| `LessThan` | `<` | `findByLuongLessThan(1000)` |
| `GreaterThanEqual` | `>=` | `findByLuongGreaterThanEqual(5000)` |
| `In` | `IN (...)` | `findByChucVuIn(list)` |
| `IsNull` | `IS NULL` | `findByPhongBanIsNull()` |
| `True/False` | `= true/false` | `findByTrangThaiTrue()` |
| `OrderBy` | `ORDER BY` | `findAllOrderByLuongDesc()` |
| `IgnoreCase` | `LOWER(...)` | `findByHoTenIgnoreCase("an")` |

### 3.4 @Query (JPQL & Native)

```java
public interface NhanVienRepository extends JpaRepository<NhanVien, Long> {

    // ===== JPQL (query trên Entity, không phải table) =====
    @Query("SELECT n FROM NhanVien n WHERE n.luong > :minLuong AND n.phongBan.id = :pbId")
    List<NhanVien> timTheoLuongVaPhongBan(@Param("minLuong") BigDecimal minLuong,
                                           @Param("pbId") Long phongBanId);

    // JOIN FETCH — giải quyết N+1 problem
    @Query("SELECT n FROM NhanVien n JOIN FETCH n.phongBan WHERE n.trangThai = true")
    List<NhanVien> findAllActiveWithPhongBan();

    // DTO Projection
    @Query("SELECT new com.study.dto.NhanVienSummary(n.id, n.hoTen, n.email, pb.ten) " +
           "FROM NhanVien n JOIN n.phongBan pb WHERE pb.id = :pbId")
    List<NhanVienSummary> findSummaryByPhongBan(@Param("pbId") Long phongBanId);

    // ===== Native Query (SQL thuần) =====
    @Query(value = """
        SELECT * FROM nhan_vien 
        WHERE EXTRACT(YEAR FROM ngay_vao_lam) = :nam
        AND luong > (SELECT AVG(luong) FROM nhan_vien)
        """, nativeQuery = true)
    List<NhanVien> timTheoNamVaLuongTrenTrungBinh(@Param("nam") int nam);

    // ===== @Modifying — UPDATE/DELETE =====
    @Modifying(clearAutomatically = true)
    @Query("UPDATE NhanVien n SET n.luong = n.luong * :heSo WHERE n.phongBan.id = :pbId")
    int tangLuongPhongBan(@Param("pbId") Long phongBanId, @Param("heSo") BigDecimal heSo);

    @Modifying
    @Query("DELETE FROM NhanVien n WHERE n.trangThai = false AND n.updatedAt < :cutoff")
    int xoaNhanVienKhongHoatDong(@Param("cutoff") LocalDateTime cutoff);
}
```

### 3.5 Projections

```java
// 1. Interface-based Projection (Spring generates proxy)
public interface NhanVienSummaryProjection {
    Long getId();
    String getHoTen();
    String getEmail();
    
    @Value("#{target.hoTen + ' (' + target.email + ')')}")  // SpEL
    String getDisplayName();
}

// Sử dụng
List<NhanVienSummaryProjection> findByPhongBanId(Long pbId);

// 2. Class-based Projection (DTO)
public record NhanVienSummary(Long id, String hoTen, String email, String phongBanTen) {}

@Query("SELECT new com.study.dto.NhanVienSummary(n.id, n.hoTen, n.email, pb.ten) " +
       "FROM NhanVien n JOIN n.phongBan pb")
List<NhanVienSummary> findAllSummaries();

// 3. Dynamic Projection
<T> List<T> findByPhongBanId(Long pbId, Class<T> type);
// Gọi: repo.findByPhongBanId(1L, NhanVienSummaryProjection.class);
```

---

## 4. Pagination và Sorting

### 4.1 Pageable

```java
// Controller
@GetMapping
public ResponseEntity<Page<NhanVienDTO>> layTatCa(
        @PageableDefault(size = 10, sort = "ngayTao", direction = Sort.Direction.DESC)
        Pageable pageable) {
    return ResponseEntity.ok(service.layTatCa(pageable));
}

// Service
@Transactional(readOnly = true)
public Page<NhanVienDTO> layTatCa(Pageable pageable) {
    return repo.findAll(pageable).map(mapper::toDTO);
}

// Custom Pageable
Pageable pageable = PageRequest.of(0, 10, Sort.by("luong").descending().and(Sort.by("hoTen")));
```

### 4.2 Page vs Slice

| Type | Count query | Khi nào dùng |
|------|------------|-------------|
| `Page<T>` | Có (`SELECT COUNT(*)`) | Hiển thị tổng pages, navigation |
| `Slice<T>` | Không | Infinite scroll, "Load more" |
| `List<T>` | Không | Không cần pagination metadata |

```java
// Slice — không count query (performant hơn cho large datasets)
Slice<NhanVien> findByPhongBanId(Long pbId, Pageable pageable);
```

---

## 5. Specifications (Dynamic Queries)

```java
// Specification builder cho tìm kiếm động
public class NhanVienSpec {

    public static Specification<NhanVien> coTen(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) return null;
            return cb.like(cb.lower(root.get("hoTen")), "%" + keyword.toLowerCase() + "%");
        };
    }

    public static Specification<NhanVien> thuocPhongBan(Long phongBanId) {
        return (root, query, cb) -> {
            if (phongBanId == null) return null;
            return cb.equal(root.get("phongBan").get("id"), phongBanId);
        };
    }

    public static Specification<NhanVien> luongTrongKhoang(BigDecimal min, BigDecimal max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min != null && max != null) return cb.between(root.get("luong"), min, max);
            if (min != null) return cb.greaterThanOrEqualTo(root.get("luong"), min);
            return cb.lessThanOrEqualTo(root.get("luong"), max);
        };
    }

    public static Specification<NhanVien> dangHoatDong() {
        return (root, query, cb) -> cb.isTrue(root.get("trangThai"));
    }
}

// Repository implements JpaSpecificationExecutor
public interface NhanVienRepository extends JpaRepository<NhanVien, Long>,
                                            JpaSpecificationExecutor<NhanVien> {}

// Service — combine specifications
@Transactional(readOnly = true)
public Page<NhanVienDTO> timKiem(NhanVienFilter filter, Pageable pageable) {
    Specification<NhanVien> spec = Specification
        .where(NhanVienSpec.coTen(filter.getKeyword()))
        .and(NhanVienSpec.thuocPhongBan(filter.getPhongBanId()))
        .and(NhanVienSpec.luongTrongKhoang(filter.getLuongMin(), filter.getLuongMax()))
        .and(NhanVienSpec.dangHoatDong());
    
    return repo.findAll(spec, pageable).map(mapper::toDTO);
}
```

---

## 6. Auditing

```java
@Configuration
@EnableJpaAuditing
public class JpaConfig {
    
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext()
            .getAuthentication())
            .map(Authentication::getName);
    }
}

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;
}

// Entity kế thừa
@Entity
public class NhanVien extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // ...
}
```

---

## 7. Transaction Management

### 7.1 @Transactional

```java
@Service
public class ChuyenKhoanService {

    @Transactional  // Nếu có exception → rollback toàn bộ
    public void chuyenTien(Long fromId, Long toId, BigDecimal soTien) {
        TaiKhoan from = taiKhoanRepo.findById(fromId).orElseThrow();
        TaiKhoan to = taiKhoanRepo.findById(toId).orElseThrow();

        if (from.getSoDu().compareTo(soTien) < 0) {
            throw new InsufficientFundsException("Số dư không đủ");
        }

        from.setSoDu(from.getSoDu().subtract(soTien));
        to.setSoDu(to.getSoDu().add(soTien));

        taiKhoanRepo.save(from);
        taiKhoanRepo.save(to);
        // Nếu save(to) fail → cả 2 đều rollback
    }

    @Transactional(readOnly = true)  // Tối ưu cho read-only (no dirty checking)
    public TaiKhoanDTO layThongTin(Long id) {
        return taiKhoanRepo.findById(id).map(this::toDTO).orElseThrow();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)  // Transaction riêng
    public void ghiLog(String action) {
        // Luôn commit, kể cả khi transaction cha rollback
        logRepo.save(new LogEntry(action));
    }
}
```

### 7.2 Transaction Propagation

| Propagation | Mô tả |
|-------------|-------|
| `REQUIRED` | **Mặc định.** Dùng transaction hiện tại, tạo mới nếu chưa có |
| `REQUIRES_NEW` | Luôn tạo transaction MỚI (suspend transaction cũ) |
| `SUPPORTS` | Dùng transaction nếu có, không tạo mới |
| `MANDATORY` | PHẢI có transaction, throw exception nếu không có |
| `NOT_SUPPORTED` | Chạy KHÔNG có transaction |
| `NEVER` | Throw exception nếu CÓ transaction |
| `NESTED` | Nested transaction (savepoint) |

### 7.3 Rollback Rules

```java
@Transactional(
    rollbackFor = {BusinessException.class, IOException.class},  // Rollback cho checked exceptions
    noRollbackFor = {EmailSendException.class}  // Không rollback cho exception này
)
public void processOrder(Order order) throws IOException {
    // Mặc định: rollback cho RuntimeException & Error
    // Checked exception: KHÔNG rollback (phải khai báo rollbackFor)
}
```

---

## 8. N+1 Problem và Solutions

### 8.1 Vấn đề N+1

```java
// LAZY fetch → N+1 queries khi iterate
List<NhanVien> nvs = nhanVienRepo.findAll(); // 1 query
for (NhanVien nv : nvs) {
    System.out.println(nv.getPhongBan().getTen()); // N queries (mỗi nhân viên 1 query)
}
// Tổng: 1 + N queries
```

### 8.2 Giải pháp

```java
// 1. JOIN FETCH (Tốt nhất cho single collection)
@Query("SELECT n FROM NhanVien n JOIN FETCH n.phongBan")
List<NhanVien> findAllWithPhongBan();

// 2. @EntityGraph
@EntityGraph(attributePaths = {"phongBan", "phanCongs"})
List<NhanVien> findByTrangThaiTrue();

// 3. @BatchSize (Hibernate specific)
@Entity
public class PhongBan {
    @OneToMany(mappedBy = "phongBan")
    @BatchSize(size = 20)  // Load 20 collections trong 1 query
    private List<NhanVien> nhanViens;
}

// 4. Global batch fetch size (application.yml)
spring:
  jpa:
    properties:
      hibernate:
        default_batch_fetch_size: 20
```

---

## 9. Soft Delete

```java
@Entity
@SQLDelete(sql = "UPDATE nhan_vien SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")  // Hibernate 6.x
public class NhanVien {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private boolean deleted = false;
    
    // repo.deleteById(id) → UPDATE SET deleted = true
    // repo.findAll() → SELECT ... WHERE deleted = false
}
```

---

## 10. Database Migration

### 10.1 Flyway

```sql
-- V1__create_tables.sql
CREATE TABLE phong_ban (
    id BIGSERIAL PRIMARY KEY,
    ten VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE nhan_vien (
    id BIGSERIAL PRIMARY KEY,
    ho_ten VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    luong DECIMAL(12,2),
    phong_ban_id BIGINT REFERENCES phong_ban(id),
    created_at TIMESTAMP DEFAULT NOW()
);

-- V2__add_trang_thai.sql
ALTER TABLE nhan_vien ADD COLUMN trang_thai BOOLEAN DEFAULT TRUE;
```

```yaml
spring:
  flyway:
    enabled: true
    locations: classpath:db/migration
    baseline-on-migrate: true
  jpa:
    hibernate:
      ddl-auto: validate  # Chỉ validate schema, không auto-generate
```

---

## 11. Performance Tips

| Tip | Mô tả |
|-----|-------|
| `FetchType.LAZY` mặc định | Tránh load data không cần thiết |
| JOIN FETCH khi cần | Giải quyết N+1 |
| `@Transactional(readOnly = true)` | Tối ưu read queries |
| Pagination | Không load toàn bộ table |
| Projections/DTO | Chỉ SELECT cột cần thiết |
| Batch operations | `saveAll()` thay vì loop `save()` |
| Index trên DB | Tạo index cho columns thường WHERE/JOIN |
| Second-level cache | Hibernate cache cho read-heavy data |
| `@DynamicUpdate` | Chỉ UPDATE columns thay đổi |

---

## 12. Best Practices

| Chủ đề | Nên | Không nên |
|--------|-----|----------|
| Fetch | LAZY mặc định, JOIN FETCH khi cần | EAGER everywhere |
| Entity | Tách Entity và DTO | Expose entity qua API |
| Transaction | `@Transactional` ở service layer | Transaction ở controller/repository |
| Query | Derived queries cho simple, @Query cho complex | Native query khi không cần thiết |
| Cascade | Chỉ cascade từ parent → child | Cascade từ ManyToOne side |
| Migration | Flyway/Liquibase | `ddl-auto: update` ở production |
| N+1 | JOIN FETCH, @EntityGraph, @BatchSize | Ignore N+1 |
| ID | `IDENTITY` hoặc `SEQUENCE` | `TABLE` (chậm) |
