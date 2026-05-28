# Design Patterns - Lý Thuyết Chi Tiết

## Giới thiệu
Design Patterns (Mẫu thiết kế) là các giải pháp tổng quát cho những vấn đề phổ biến trong thiết kế phần mềm. Được phân loại thành 3 nhóm chính:

---

## Phần 1: Creational Patterns (Mẫu Khởi Tạo)

### 1.1 Singleton
**Mục đích:** Đảm bảo một class chỉ có duy nhất một instance và cung cấp điểm truy cập toàn cục.

**Khi nào dùng:** Database connection pool, Logger, Configuration, Cache.

```java
// Thread-safe Singleton với Double-Checked Locking
public class DatabaseConnection {
    private static volatile DatabaseConnection instance;

    private DatabaseConnection() { }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
}

// Cách tốt nhất: Enum Singleton
public enum AppConfig {
    INSTANCE;
    private String dbUrl;
    public String getDbUrl() { return dbUrl; }
}
```

### 1.2 Factory Method
**Mục đích:** Định nghĩa interface để tạo đối tượng, nhưng để lớp con quyết định class nào được tạo.

```java
public interface ThongBao {
    void gui(String noiDung);
}

public class EmailThongBao implements ThongBao {
    public void gui(String noiDung) { System.out.println("Email: " + noiDung); }
}

public class SMSThongBao implements ThongBao {
    public void gui(String noiDung) { System.out.println("SMS: " + noiDung); }
}

public class ThongBaoFactory {
    public static ThongBao taoThongBao(String loai) {
        return switch (loai.toUpperCase()) {
            case "EMAIL" -> new EmailThongBao();
            case "SMS" -> new SMSThongBao();
            default -> throw new IllegalArgumentException("Loại không hợp lệ: " + loai);
        };
    }
}
```

### 1.3 Abstract Factory
**Mục đích:** Cung cấp interface để tạo nhóm các đối tượng liên quan mà không cần chỉ rõ class cụ thể.

```java
// Ví dụ: Factory cho UI components theo platform
public interface Button { void render(); }
public interface TextField { void render(); }

public interface UIFactory {
    Button createButton();
    TextField createTextField();
}

public class WindowsUIFactory implements UIFactory {
    public Button createButton() { return new WindowsButton(); }
    public TextField createTextField() { return new WindowsTextField(); }
}

public class MacUIFactory implements UIFactory {
    public Button createButton() { return new MacButton(); }
    public TextField createTextField() { return new MacTextField(); }
}
```

### 1.4 Builder
**Mục đích:** Tách biệt quá trình xây dựng đối tượng phức tạp khỏi biểu diễn của nó.

```java
public class NguoiDung {
    private final String ten;
    private final String email;
    private final int tuoi;
    private final String diaChi;
    private final String soDienThoai;

    private NguoiDung(Builder builder) {
        this.ten = builder.ten;
        this.email = builder.email;
        this.tuoi = builder.tuoi;
        this.diaChi = builder.diaChi;
        this.soDienThoai = builder.soDienThoai;
    }

    public static class Builder {
        private final String ten;    // Bắt buộc
        private final String email;  // Bắt buộc
        private int tuoi;
        private String diaChi;
        private String soDienThoai;

        public Builder(String ten, String email) {
            this.ten = ten;
            this.email = email;
        }

        public Builder tuoi(int tuoi) { this.tuoi = tuoi; return this; }
        public Builder diaChi(String diaChi) { this.diaChi = diaChi; return this; }
        public Builder soDienThoai(String sdt) { this.soDienThoai = sdt; return this; }

        public NguoiDung build() { return new NguoiDung(this); }
    }
}

// Sử dụng
NguoiDung nd = new NguoiDung.Builder("An", "an@email.com")
    .tuoi(25)
    .diaChi("Hà Nội")
    .build();
```

### 1.5 Prototype
**Mục đích:** Tạo đối tượng mới bằng cách sao chép (clone) từ một đối tượng mẫu.

```java
public abstract class HinhHoc implements Cloneable {
    protected String mauSac;

    @Override
    public HinhHoc clone() {
        try {
            return (HinhHoc) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
```

---

## Phần 2: Structural Patterns (Mẫu Cấu Trúc)

### 2.1 Adapter
**Mục đích:** Chuyển đổi interface của một class thành interface khác mà client mong đợi.

```java
// Interface cũ
public class OldPaymentSystem {
    public void processPayment(String cardNumber, double amount) { }
}

// Interface mới client cần
public interface PaymentProcessor {
    void pay(PaymentRequest request);
}

// Adapter
public class PaymentAdapter implements PaymentProcessor {
    private final OldPaymentSystem oldSystem;

    public PaymentAdapter(OldPaymentSystem oldSystem) {
        this.oldSystem = oldSystem;
    }

    @Override
    public void pay(PaymentRequest request) {
        oldSystem.processPayment(request.getCardNumber(), request.getAmount());
    }
}
```

### 2.2 Decorator
**Mục đích:** Thêm chức năng mới cho đối tượng mà không thay đổi cấu trúc.

```java
public interface DoUong {
    String moTa();
    double gia();
}

public class CaPhe implements DoUong {
    public String moTa() { return "Cà phê đen"; }
    public double gia() { return 25000; }
}

public abstract class DoUongDecorator implements DoUong {
    protected final DoUong doUong;
    public DoUongDecorator(DoUong doUong) { this.doUong = doUong; }
}

public class ThemSua extends DoUongDecorator {
    public ThemSua(DoUong doUong) { super(doUong); }
    public String moTa() { return doUong.moTa() + ", thêm sữa"; }
    public double gia() { return doUong.gia() + 5000; }
}

public class ThemDuong extends DoUongDecorator {
    public ThemDuong(DoUong doUong) { super(doUong); }
    public String moTa() { return doUong.moTa() + ", thêm đường"; }
    public double gia() { return doUong.gia() + 3000; }
}

// Sử dụng
DoUong order = new ThemDuong(new ThemSua(new CaPhe()));
// "Cà phê đen, thêm sữa, thêm đường" - 33000đ
```

### 2.3 Proxy
**Mục đích:** Cung cấp đối tượng đại diện để kiểm soát truy cập đến đối tượng gốc.

**Các loại:**
- **Virtual Proxy:** Lazy initialization (tải ảnh, kết nối DB).
- **Protection Proxy:** Kiểm tra quyền truy cập.
- **Caching Proxy:** Lưu cache kết quả.

```java
public interface DuLieuService {
    List<String> layDuLieu();
}

public class CachingProxy implements DuLieuService {
    private final DuLieuService service;
    private List<String> cache;
    private long lastFetch;
    private static final long CACHE_TTL = 60000; // 1 phút

    public CachingProxy(DuLieuService service) { this.service = service; }

    @Override
    public List<String> layDuLieu() {
        if (cache == null || System.currentTimeMillis() - lastFetch > CACHE_TTL) {
            cache = service.layDuLieu();
            lastFetch = System.currentTimeMillis();
        }
        return cache;
    }
}
```

### 2.4 Facade
**Mục đích:** Cung cấp giao diện đơn giản cho một hệ thống con phức tạp.

```java
public class DatHangFacade {
    private final KhoHangService khoHang;
    private final ThanhToanService thanhToan;
    private final VanChuyenService vanChuyen;
    private final ThongBaoService thongBao;

    public KetQuaDatHang datHang(DonHang donHang) {
        if (!khoHang.kiemTraTonKho(donHang)) throw new HetHangException();
        thanhToan.xuLyThanhToan(donHang);
        khoHang.truHang(donHang);
        String maVanDon = vanChuyen.taoVanDon(donHang);
        thongBao.guiXacNhan(donHang);
        return new KetQuaDatHang(maVanDon);
    }
}
```

---

## Phần 3: Behavioral Patterns (Mẫu Hành Vi)

### 3.1 Observer
**Mục đích:** Định nghĩa quan hệ một-nhiều giữa các đối tượng, khi một đối tượng thay đổi trạng thái, tất cả phụ thuộc được thông báo.

```java
public interface NguoiQuanSat {
    void capNhat(String suKien, Object duLieu);
}

public class KenhTinTuc {
    private final List<NguoiQuanSat> nguoiDangKy = new ArrayList<>();

    public void dangKy(NguoiQuanSat nqs) { nguoiDangKy.add(nqs); }
    public void huyDangKy(NguoiQuanSat nqs) { nguoiDangKy.remove(nqs); }

    public void thongBao(String suKien, Object duLieu) {
        nguoiDangKy.forEach(nqs -> nqs.capNhat(suKien, duLieu));
    }

    public void dangBaiViet(String tieuDe) {
        thongBao("BAI_VIET_MOI", tieuDe);
    }
}
```

### 3.2 Strategy
**Mục đích:** Định nghĩa nhóm thuật toán, đóng gói mỗi thuật toán và cho phép hoán đổi chúng.

```java
public interface ChienLuocGiamGia {
    double tinhGia(double giaGoc);
}

public class GiamGiaPhanTram implements ChienLuocGiamGia {
    private final double phanTram;
    public GiamGiaPhanTram(double phanTram) { this.phanTram = phanTram; }
    public double tinhGia(double giaGoc) { return giaGoc * (1 - phanTram / 100); }
}

public class GiamGiaCoDinh implements ChienLuocGiamGia {
    private final double soTien;
    public GiamGiaCoDinh(double soTien) { this.soTien = soTien; }
    public double tinhGia(double giaGoc) { return Math.max(0, giaGoc - soTien); }
}

public class DonHang {
    private ChienLuocGiamGia chieuLuoc;
    public void setChienLuoc(ChienLuocGiamGia cl) { this.chieuLuoc = cl; }
    public double tinhTongTien(double giaGoc) {
        return chieuLuoc != null ? chieuLuoc.tinhGia(giaGoc) : giaGoc;
    }
}
```

### 3.3 Template Method
**Mục đích:** Định nghĩa khung thuật toán trong method cha, cho phép lớp con override các bước cụ thể.

```java
public abstract class BaoCaoTemplate {
    // Template method
    public final String taoBaoCao() {
        StringBuilder sb = new StringBuilder();
        sb.append(taoTieuDe());
        sb.append(taoNoiDung());
        sb.append(taoChanTrang());
        return sb.toString();
    }

    protected abstract String taoTieuDe();
    protected abstract String taoNoiDung();

    protected String taoChanTrang() {
        return "\n--- Báo cáo được tạo: " + LocalDate.now() + " ---";
    }
}
```

### 3.4 Command
**Mục đích:** Đóng gói yêu cầu thành đối tượng, cho phép undo/redo và logging.

```java
public interface LenhSoanThao {
    void thucThi();
    void hoaTac(); // Undo
}

public class LenhVietChu implements LenhSoanThao {
    private final VanBan vanBan;
    private final String noiDung;

    public LenhVietChu(VanBan vanBan, String noiDung) {
        this.vanBan = vanBan;
        this.noiDung = noiDung;
    }

    public void thucThi() { vanBan.them(noiDung); }
    public void hoaTac() { vanBan.xoa(noiDung.length()); }
}

public class LichSuLenh {
    private final Deque<LenhSoanThao> lichSu = new ArrayDeque<>();

    public void thucThi(LenhSoanThao lenh) {
        lenh.thucThi();
        lichSu.push(lenh);
    }

    public void undo() {
        if (!lichSu.isEmpty()) lichSu.pop().hoaTac();
    }
}
```

### 3.5 Chain of Responsibility
**Mục đích:** Cho phép truyền yêu cầu qua chuỗi các handler. Mỗi handler quyết định xử lý hoặc chuyển tiếp.

```java
public abstract class XuLyYeuCau {
    protected XuLyYeuCau tiepTheo;

    public XuLyYeuCau datTiepTheo(XuLyYeuCau handler) {
        this.tiepTheo = handler;
        return handler;
    }

    public abstract boolean xuLy(YeuCau yeuCau);

    protected boolean chuyenTiep(YeuCau yeuCau) {
        return tiepTheo != null && tiepTheo.xuLy(yeuCau);
    }
}

public class XacThucHandler extends XuLyYeuCau {
    public boolean xuLy(YeuCau yeuCau) {
        if (!yeuCau.hasToken()) {
            System.out.println("Chưa xác thực!");
            return false;
        }
        return chuyenTiep(yeuCau);
    }
}
```

---

## Tổng kết: Khi nào dùng Pattern nào?

| Pattern | Khi nào dùng |
|---------|-------------|
| Singleton | Cần đúng 1 instance (DB pool, Config) |
| Factory | Tạo đối tượng dựa trên điều kiện runtime |
| Builder | Đối tượng có nhiều tham số tùy chọn |
| Observer | Event system, thông báo thay đổi |
| Strategy | Cần thay đổi thuật toán runtime |
| Decorator | Thêm chức năng linh hoạt |
| Adapter | Tích hợp hệ thống cũ với interface mới |
| Facade | Đơn giản hóa hệ thống con phức tạp |
| Template Method | Thuật toán có bước cố định + bước tùy biến |
| Command | Undo/redo, job queue, logging |
