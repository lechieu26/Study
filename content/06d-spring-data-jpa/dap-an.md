# Spring Data JPA - Đáp Án

## Bài 1: Entity Mapping và Relationships

### BaseEntity
```java
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
```

### Entities
```java
@Entity
@Table(name = "sinh_vien")
public class SinhVien extends BaseEntity {
    @Column(unique = true, nullable = false) private String maSV;
    @Column(nullable = false) private String hoTen;
    @Column(unique = true) private String email;
    private LocalDate ngaySinh;
    private String lop;
    private Double diemTrungBinh;

    @OneToMany(mappedBy = "sinhVien", cascade = CascadeType.ALL)
    private Set<DangKy> dangKys = new HashSet<>();
}

@Entity
@Table(name = "khoa_hoc")
public class KhoaHoc extends BaseEntity {
    @Column(nullable = false) private String ten;
    private String moTa;
    private Integer soTinChi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giang_vien_id")
    private GiangVien giangVien;

    @OneToMany(mappedBy = "khoaHoc")
    private Set<DangKy> dangKys = new HashSet<>();
}

@Entity
@Table(name = "giang_vien")
public class GiangVien extends BaseEntity {
    private String hoTen;
    @Column(unique = true) private String email;
    private String boMon;

    @OneToMany(mappedBy = "giangVien")
    private List<KhoaHoc> khoaHocs = new ArrayList<>();
}

@Entity
@Table(name = "dang_ky")
public class DangKy {
    @EmbeddedId private DangKyId id = new DangKyId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("sinhVienId")
    private SinhVien sinhVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("khoaHocId")
    private KhoaHoc khoaHoc;

    private LocalDate ngayDangKy = LocalDate.now();
    private Double diemSo;

    @Enumerated(EnumType.STRING)
    private TrangThai trangThai = TrangThai.DANG_HOC;
}

@Embeddable
public class DangKyId implements Serializable {
    private Long sinhVienId;
    private Long khoaHocId;
    // equals() & hashCode()
}
```

## Bài 4: Specifications - Tìm Kiếm Động

```java
public class SinhVienSpec {
    public static Specification<SinhVien> coTen(String keyword) {
        return (root, query, cb) -> keyword == null ? null :
            cb.like(cb.lower(root.get("hoTen")), "%" + keyword.toLowerCase() + "%");
    }

    public static Specification<SinhVien> thuocLop(String lop) {
        return (root, query, cb) -> lop == null ? null :
            cb.equal(root.get("lop"), lop);
    }

    public static Specification<SinhVien> diemTrongKhoang(Double min, Double max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min != null && max != null) return cb.between(root.get("diemTrungBinh"), min, max);
            if (min != null) return cb.ge(root.get("diemTrungBinh"), min);
            return cb.le(root.get("diemTrungBinh"), max);
        };
    }
}

// Service
public Page<SinhVienDTO> timKiem(SinhVienFilter filter, Pageable pageable) {
    Specification<SinhVien> spec = Specification
        .where(SinhVienSpec.coTen(filter.getKeyword()))
        .and(SinhVienSpec.thuocLop(filter.getLop()))
        .and(SinhVienSpec.diemTrongKhoang(filter.getDiemMin(), filter.getDiemMax()));
    return repo.findAll(spec, pageable).map(this::toDTO);
}
```
