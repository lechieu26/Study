# Design Patterns - Lý Thuyết Chi Tiết

## Giới thiệu

Design Patterns (Mẫu thiết kế) là các giải pháp đã được chứng minh cho những vấn đề phổ biến trong thiết kế phần mềm. Được hệ thống hóa bởi **Gang of Four (GoF)** trong cuốn sách kinh điển năm 1994.

**Tại sao cần Design Patterns?**
- Cung cấp **ngôn ngữ chung** giữa các developer ("Dùng Strategy Pattern ở đây" — ai cũng hiểu)
- **Giải pháp đã kiểm chứng** — không cần phát minh lại bánh xe
- **Code linh hoạt**, dễ mở rộng, dễ bảo trì
- Tuân thủ các nguyên tắc SOLID

**Phân loại 3 nhóm chính:**

| Nhóm | Mục đích | Patterns |
|------|---------|---------|
| **Creational** (Khởi tạo) | Cách tạo đối tượng linh hoạt | Singleton, Factory, Abstract Factory, Builder, Prototype |
| **Structural** (Cấu trúc) | Cách kết hợp class/object | Adapter, Decorator, Proxy, Facade, Composite, Bridge, Flyweight |
| **Behavioral** (Hành vi) | Cách giao tiếp giữa objects | Observer, Strategy, Template Method, Command, Chain of Responsibility, State, Iterator |

---

## Phần 1: Creational Patterns (Mẫu Khởi Tạo)

### 1.1 Singleton

**Mục đích:** Đảm bảo một class chỉ có duy nhất một instance và cung cấp điểm truy cập toàn cục.

**Khi nào dùng:** Database connection pool, Logger, Configuration, Cache manager, Thread pool.

**Vấn đề giải quyết:** Nhiều nơi trong code cần truy cập cùng một resource (ví dụ: DB connection), tạo nhiều instance gây lãng phí hoặc xung đột.

```java
// Cách 1: Thread-safe Singleton với Double-Checked Locking
public class DatabaseConnection {
    private static volatile DatabaseConnection instance;

    private DatabaseConnection() {
        // private constructor — ngăn new từ bên ngoài
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {                        // Check 1 (không lock)
            synchronized (DatabaseConnection.class) {
                if (instance == null) {                // Check 2 (trong lock)
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
}

// Cách 2: Enum Singleton (Recommended by Joshua Bloch — Effective Java)
// Thread-safe, serialization-safe, reflection-safe
public enum AppConfig {
    INSTANCE;

    private String dbUrl;
    private int maxConnections;

    public String getDbUrl() { return dbUrl; }
    public void setDbUrl(String url) { this.dbUrl = url; }
}

// Cách 3: Bill Pugh Singleton (Inner Static Class — lazy + thread-safe)
public class Registry {
    private Registry() {}

    private static class Holder {
        private static final Registry INSTANCE = new Registry();
    }

    public static Registry getInstance() {
        return Holder.INSTANCE;
    }
}
```

> **Phỏng vấn:** Tại sao cần `volatile`? — Ngăn JVM reorder instructions. Không có `volatile`, thread khác có thể thấy object chưa khởi tạo xong. Enum Singleton là cách tốt nhất vì JVM đảm bảo thread-safety và chống Reflection attack.

> **Nhược điểm:** Khó test (global state), vi phạm Single Responsibility, ẩn dependencies. Trong Spring, dùng `@Scope("singleton")` thay vì tự implement.

### 1.2 Factory Method

**Mục đích:** Định nghĩa interface để tạo đối tượng, nhưng để lớp con quyết định class nào được tạo. **Tách logic tạo object khỏi logic sử dụng.**

**Khi nào dùng:** Không biết trước kiểu object cần tạo lúc compile-time (phụ thuộc vào input/config runtime).

```java
// Product interface
public interface ThongBao {
    void gui(String noiDung);
}

// Concrete Products
public class EmailThongBao implements ThongBao {
    public void gui(String noiDung) {
        System.out.println("Email: " + noiDung);
    }
}

public class SMSThongBao implements ThongBao {
    public void gui(String noiDung) {
        System.out.println("SMS: " + noiDung);
    }
}

public class PushThongBao implements ThongBao {
    public void gui(String noiDung) {
        System.out.println("Push Notification: " + noiDung);
    }
}

// Factory — tập trung logic tạo object
public class ThongBaoFactory {
    public static ThongBao taoThongBao(String loai) {
        return switch (loai.toUpperCase()) {
            case "EMAIL" -> new EmailThongBao();
            case "SMS"   -> new SMSThongBao();
            case "PUSH"  -> new PushThongBao();
            default -> throw new IllegalArgumentException("Loại không hợp lệ: " + loai);
        };
    }
}

// Sử dụng — client không cần biết class cụ thể
ThongBao tb = ThongBaoFactory.taoThongBao("EMAIL");
tb.gui("Đơn hàng đã được xác nhận");
```

> **Ưu điểm:** Open/Closed Principle — thêm loại thông báo mới chỉ cần thêm class + case trong factory, không sửa code client.

### 1.3 Abstract Factory

**Mục đích:** Cung cấp interface để tạo **nhóm** các đối tượng liên quan mà không cần chỉ rõ class cụ thể.

**Khi nào dùng:** Hệ thống cần hỗ trợ nhiều "families" của products (ví dụ: UI cho Windows/Mac, DB driver cho MySQL/PostgreSQL).

```java
// Abstract Products
public interface Button { void render(); void onClick(Runnable action); }
public interface TextField { void render(); String getValue(); }
public interface Checkbox { void render(); boolean isChecked(); }

// Abstract Factory
public interface UIFactory {
    Button createButton();
    TextField createTextField();
    Checkbox createCheckbox();
}

// Windows Family
public class WindowsUIFactory implements UIFactory {
    public Button createButton() { return new WindowsButton(); }
    public TextField createTextField() { return new WindowsTextField(); }
    public Checkbox createCheckbox() { return new WindowsCheckbox(); }
}

// Mac Family
public class MacUIFactory implements UIFactory {
    public Button createButton() { return new MacButton(); }
    public TextField createTextField() { return new MacTextField(); }
    public Checkbox createCheckbox() { return new MacCheckbox(); }
}

// Client code — không phụ thuộc vào platform cụ thể
public class Application {
    private final UIFactory factory;

    public Application(UIFactory factory) {
        this.factory = factory;
    }

    public void createUI() {
        Button btn = factory.createButton();
        TextField txt = factory.createTextField();
        btn.render();
        txt.render();
    }
}
```

> **So sánh Factory Method vs Abstract Factory:** Factory Method tạo **1 product**, Abstract Factory tạo **nhóm products liên quan**. Abstract Factory thường dùng Factory Method bên trong.

### 1.4 Builder

**Mục đích:** Tách biệt quá trình xây dựng đối tượng phức tạp khỏi biểu diễn của nó. Đặc biệt hữu ích khi object có nhiều tham số tùy chọn.

**Khi nào dùng:** Object có 4+ parameters, nhiều optional fields, cần immutable objects. Thay thế cho Telescoping Constructor anti-pattern.

```java
public class NguoiDung {
    private final String ten;           // Bắt buộc
    private final String email;         // Bắt buộc
    private final int tuoi;
    private final String diaChi;
    private final String soDienThoai;
    private final List<String> vaiTro;

    private NguoiDung(Builder builder) {
        this.ten = builder.ten;
        this.email = builder.email;
        this.tuoi = builder.tuoi;
        this.diaChi = builder.diaChi;
        this.soDienThoai = builder.soDienThoai;
        this.vaiTro = List.copyOf(builder.vaiTro); // Immutable copy
    }

    // Getters (no setters → immutable)
    public String getTen() { return ten; }
    public String getEmail() { return email; }

    public static class Builder {
        private final String ten;       // Bắt buộc
        private final String email;     // Bắt buộc
        private int tuoi;
        private String diaChi;
        private String soDienThoai;
        private List<String> vaiTro = new ArrayList<>();

        public Builder(String ten, String email) {
            this.ten = Objects.requireNonNull(ten, "Tên không được null");
            this.email = Objects.requireNonNull(email, "Email không được null");
        }

        public Builder tuoi(int tuoi) { this.tuoi = tuoi; return this; }
        public Builder diaChi(String diaChi) { this.diaChi = diaChi; return this; }
        public Builder soDienThoai(String sdt) { this.soDienThoai = sdt; return this; }
        public Builder themVaiTro(String vaiTro) { this.vaiTro.add(vaiTro); return this; }

        public NguoiDung build() {
            // Validation trước khi tạo
            if (tuoi < 0 || tuoi > 150) throw new IllegalStateException("Tuổi không hợp lệ");
            return new NguoiDung(this);
        }
    }
}

// Sử dụng — fluent API, dễ đọc
NguoiDung nd = new NguoiDung.Builder("An", "an@email.com")
    .tuoi(25)
    .diaChi("Hà Nội")
    .themVaiTro("ADMIN")
    .themVaiTro("USER")
    .build();
```

> **Thực tế:** Lombok `@Builder` tự động generate Builder code. Trong Spring, `HttpSecurity`, `RestTemplate.builder()` đều dùng Builder pattern.

### 1.5 Prototype

**Mục đích:** Tạo đối tượng mới bằng cách sao chép (clone) từ một đối tượng mẫu. Hữu ích khi tạo object từ scratch tốn kém (ví dụ: object từ database, complex initialization).

```java
public abstract class HinhHoc implements Cloneable {
    protected String mauSac;
    protected double x, y;

    public abstract double tinhDienTich();

    @Override
    public HinhHoc clone() {
        try {
            return (HinhHoc) super.clone();  // Shallow copy
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

// Prototype Registry — quản lý các prototype
public class HinhHocRegistry {
    private final Map<String, HinhHoc> prototypes = new HashMap<>();

    public void dangKy(String key, HinhHoc prototype) {
        prototypes.put(key, prototype);
    }

    public HinhHoc tao(String key) {
        HinhHoc prototype = prototypes.get(key);
        if (prototype == null) throw new IllegalArgumentException("Không có prototype: " + key);
        return prototype.clone();
    }
}
```

> **Lưu ý:** `super.clone()` chỉ tạo **shallow copy**. Nếu object chứa reference types (List, Map), cần override clone để deep copy.

---

## Phần 2: Structural Patterns (Mẫu Cấu Trúc)

### 2.1 Adapter

**Mục đích:** Chuyển đổi interface của một class thành interface khác mà client mong đợi. Giúp các class không tương thích làm việc cùng nhau.

**Khi nào dùng:** Tích hợp hệ thống cũ (legacy), sử dụng thư viện bên thứ ba có interface khác.

```java
// Interface cũ (3rd party hoặc legacy)
public class OldPaymentSystem {
    public void processPayment(String cardNumber, double amount) {
        // logic cũ
    }
}

// Interface mới client cần
public interface PaymentProcessor {
    void pay(PaymentRequest request);
}

// Adapter — cầu nối giữa cũ và mới
public class PaymentAdapter implements PaymentProcessor {
    private final OldPaymentSystem oldSystem;

    public PaymentAdapter(OldPaymentSystem oldSystem) {
        this.oldSystem = oldSystem;
    }

    @Override
    public void pay(PaymentRequest request) {
        // Chuyển đổi từ interface mới sang interface cũ
        oldSystem.processPayment(request.getCardNumber(), request.getAmount());
    }
}
```

> **Thực tế trong Java:** `InputStreamReader` adapt `InputStream` thành `Reader`, `Arrays.asList()` adapt array thành List.

### 2.2 Decorator

**Mục đích:** Thêm chức năng mới cho đối tượng **động** (runtime) mà không thay đổi cấu trúc class gốc. Tuân thủ Open/Closed Principle.

**Khi nào dùng:** Cần nhiều tổ hợp chức năng khác nhau, kế thừa quá nhiều subclass.

```java
// Component interface
public interface DoUong {
    String moTa();
    double gia();
}

// Concrete Component
public class CaPhe implements DoUong {
    public String moTa() { return "Cà phê đen"; }
    public double gia() { return 25000; }
}

public class Tra implements DoUong {
    public String moTa() { return "Trà"; }
    public double gia() { return 20000; }
}

// Base Decorator
public abstract class DoUongDecorator implements DoUong {
    protected final DoUong doUong;
    public DoUongDecorator(DoUong doUong) { this.doUong = doUong; }
}

// Concrete Decorators
public class ThemSua extends DoUongDecorator {
    public ThemSua(DoUong doUong) { super(doUong); }
    public String moTa() { return doUong.moTa() + " + Sữa"; }
    public double gia() { return doUong.gia() + 5000; }
}

public class ThemDuong extends DoUongDecorator {
    public ThemDuong(DoUong doUong) { super(doUong); }
    public String moTa() { return doUong.moTa() + " + Đường"; }
    public double gia() { return doUong.gia() + 3000; }
}

public class ThemDa extends DoUongDecorator {
    public ThemDa(DoUong doUong) { super(doUong); }
    public String moTa() { return doUong.moTa() + " + Đá"; }
    public double gia() { return doUong.gia() + 2000; }
}

// Sử dụng — combo linh hoạt, không cần class cho mỗi tổ hợp
DoUong order = new ThemDa(new ThemDuong(new ThemSua(new CaPhe())));
// "Cà phê đen + Sữa + Đường + Đá" — 35,000đ
```

> **Thực tế trong Java:** `BufferedInputStream(FileInputStream(...))`, `Collections.synchronizedList()`, `Collections.unmodifiableList()` đều là Decorator.

### 2.3 Proxy

**Mục đích:** Cung cấp đối tượng đại diện để kiểm soát truy cập đến đối tượng gốc.

| Loại Proxy | Mục đích | Ví dụ |
|-----------|---------|-------|
| Virtual Proxy | Lazy initialization, hoãn tạo object nặng | Load ảnh khi cần |
| Protection Proxy | Kiểm tra quyền truy cập | Security check |
| Caching Proxy | Lưu cache kết quả | API response cache |
| Logging Proxy | Ghi log trước/sau method call | Audit trail |
| Remote Proxy | Đại diện cho object ở server khác | RMI, gRPC |

```java
public interface DuLieuService {
    List<String> layDuLieu();
}

// Caching + Logging Proxy
public class SmartProxy implements DuLieuService {
    private final DuLieuService service;
    private List<String> cache;
    private long lastFetch;
    private static final long CACHE_TTL = 60_000; // 1 phút

    public SmartProxy(DuLieuService service) { this.service = service; }

    @Override
    public List<String> layDuLieu() {
        if (cache == null || System.currentTimeMillis() - lastFetch > CACHE_TTL) {
            System.out.println("[Proxy] Cache miss, gọi service thật...");
            cache = service.layDuLieu();
            lastFetch = System.currentTimeMillis();
        } else {
            System.out.println("[Proxy] Trả về từ cache");
        }
        return Collections.unmodifiableList(cache);
    }
}
```

> **Thực tế:** Spring AOP dùng Dynamic Proxy (JDK Proxy hoặc CGLIB) cho `@Transactional`, `@Cacheable`, `@Async`.

### 2.4 Facade

**Mục đích:** Cung cấp giao diện đơn giản cho một hệ thống con phức tạp. Client chỉ cần gọi 1 method thay vì phải gọi nhiều service.

```java
public class DatHangFacade {
    private final KhoHangService khoHang;
    private final ThanhToanService thanhToan;
    private final VanChuyenService vanChuyen;
    private final ThongBaoService thongBao;

    public DatHangFacade(KhoHangService khoHang, ThanhToanService thanhToan,
                         VanChuyenService vanChuyen, ThongBaoService thongBao) {
        this.khoHang = khoHang;
        this.thanhToan = thanhToan;
        this.vanChuyen = vanChuyen;
        this.thongBao = thongBao;
    }

    // Client chỉ cần gọi 1 method này
    public KetQuaDatHang datHang(DonHang donHang) {
        // Bước 1: Kiểm tra tồn kho
        if (!khoHang.kiemTraTonKho(donHang)) {
            throw new HetHangException("Sản phẩm hết hàng");
        }
        // Bước 2: Xử lý thanh toán
        thanhToan.xuLyThanhToan(donHang);
        // Bước 3: Trừ hàng trong kho
        khoHang.truHang(donHang);
        // Bước 4: Tạo vận đơn
        String maVanDon = vanChuyen.taoVanDon(donHang);
        // Bước 5: Gửi thông báo
        thongBao.guiXacNhan(donHang);

        return new KetQuaDatHang(maVanDon);
    }
}
```

> **Thực tế:** `JdbcTemplate` trong Spring là Facade cho JDBC (ẩn Connection, Statement, ResultSet). `RestTemplate` là Facade cho HTTP client.

### 2.5 Composite (Bonus)

**Mục đích:** Tổ chức objects thành cấu trúc cây, cho phép client xử lý object đơn lẻ và nhóm objects cùng cách.

```java
public interface ThanhPhanMenu {
    String getTen();
    double getGia();
    void hienThi(String indent);
}

public class MonAn implements ThanhPhanMenu {
    private final String ten;
    private final double gia;

    public MonAn(String ten, double gia) { this.ten = ten; this.gia = gia; }
    public String getTen() { return ten; }
    public double getGia() { return gia; }
    public void hienThi(String indent) {
        System.out.printf("%s%s - %,.0fđ%n", indent, ten, gia);
    }
}

public class NhomMenu implements ThanhPhanMenu {
    private final String ten;
    private final List<ThanhPhanMenu> items = new ArrayList<>();

    public NhomMenu(String ten) { this.ten = ten; }
    public void them(ThanhPhanMenu item) { items.add(item); }
    public String getTen() { return ten; }
    public double getGia() { return items.stream().mapToDouble(ThanhPhanMenu::getGia).sum(); }
    public void hienThi(String indent) {
        System.out.println(indent + "📁 " + ten);
        items.forEach(item -> item.hienThi(indent + "  "));
    }
}
```

---

## Phần 3: Behavioral Patterns (Mẫu Hành Vi)

### 3.1 Observer

**Mục đích:** Định nghĩa quan hệ **một-nhiều** giữa các đối tượng. Khi một đối tượng thay đổi trạng thái, tất cả phụ thuộc được thông báo tự động.

**Khi nào dùng:** Event systems, pub/sub messaging, UI state management, real-time notifications.

```java
// Observer interface
public interface NguoiQuanSat {
    void capNhat(String suKien, Object duLieu);
}

// Subject (Observable)
public class KenhTinTuc {
    private final List<NguoiQuanSat> nguoiDangKy = new CopyOnWriteArrayList<>();

    public void dangKy(NguoiQuanSat nqs) { nguoiDangKy.add(nqs); }
    public void huyDangKy(NguoiQuanSat nqs) { nguoiDangKy.remove(nqs); }

    private void thongBao(String suKien, Object duLieu) {
        nguoiDangKy.forEach(nqs -> nqs.capNhat(suKien, duLieu));
    }

    public void dangBaiViet(String tieuDe) {
        thongBao("BAI_VIET_MOI", tieuDe);
    }

    public void capNhatTinNong(String tinTuc) {
        thongBao("TIN_NONG", tinTuc);
    }
}

// Concrete Observers
public class NguoiDocEmail implements NguoiQuanSat {
    private final String email;
    public NguoiDocEmail(String email) { this.email = email; }

    @Override
    public void capNhat(String suKien, Object duLieu) {
        System.out.printf("📧 Gửi email đến %s: [%s] %s%n", email, suKien, duLieu);
    }
}

// Sử dụng
KenhTinTuc kenh = new KenhTinTuc();
kenh.dangKy(new NguoiDocEmail("an@email.com"));
kenh.dangKy(new NguoiDocEmail("binh@email.com"));
kenh.dangBaiViet("Java 22 ra mắt!");
// → 2 email được gửi tự động
```

> **Thực tế:** Spring `ApplicationEventPublisher`, JavaScript DOM events, RxJava/Project Reactor, Kafka consumers đều dựa trên Observer pattern.

### 3.2 Strategy

**Mục đích:** Định nghĩa nhóm thuật toán, đóng gói mỗi thuật toán thành class riêng và cho phép **hoán đổi** chúng tại runtime.

**Khi nào dùng:** Có nhiều cách thực hiện cùng một hành động, cần thay đổi algorithm tại runtime, loại bỏ `if-else` / `switch` dài.

```java
// Strategy interface
public interface ChienLuocGiamGia {
    double tinhGia(double giaGoc);
    String moTa();
}

// Concrete Strategies
public class KhongGiamGia implements ChienLuocGiamGia {
    public double tinhGia(double giaGoc) { return giaGoc; }
    public String moTa() { return "Không giảm giá"; }
}

public class GiamGiaPhanTram implements ChienLuocGiamGia {
    private final double phanTram;
    public GiamGiaPhanTram(double phanTram) { this.phanTram = phanTram; }
    public double tinhGia(double giaGoc) { return giaGoc * (1 - phanTram / 100); }
    public String moTa() { return "Giảm " + phanTram + "%"; }
}

public class GiamGiaCoDinh implements ChienLuocGiamGia {
    private final double soTien;
    public GiamGiaCoDinh(double soTien) { this.soTien = soTien; }
    public double tinhGia(double giaGoc) { return Math.max(0, giaGoc - soTien); }
    public String moTa() { return "Giảm " + soTien + "đ"; }
}

public class MuaNTangM implements ChienLuocGiamGia {
    private final int mua, tang;
    public MuaNTangM(int mua, int tang) { this.mua = mua; this.tang = tang; }
    public double tinhGia(double giaGoc) { return giaGoc * mua / (mua + tang); }
    public String moTa() { return "Mua " + mua + " tặng " + tang; }
}

// Context
public class DonHang {
    private ChienLuocGiamGia chieuLuoc = new KhongGiamGia();

    public void setChienLuoc(ChienLuocGiamGia cl) { this.chieuLuoc = cl; }

    public double tinhTongTien(double giaGoc) {
        return chieuLuoc.tinhGia(giaGoc);
    }
}
```

> **Phỏng vấn:** Strategy vs State? — Strategy: client chọn algorithm. State: object tự thay đổi behavior khi trạng thái thay đổi. Strategy không giữ reference đến context, State thường có.

### 3.3 Template Method

**Mục đích:** Định nghĩa **khung** thuật toán trong method cha, cho phép lớp con override các bước cụ thể mà không thay đổi cấu trúc tổng thể.

**Khi nào dùng:** Nhiều class có algorithm tương tự, chỉ khác một vài bước.

```java
public abstract class BaoCaoTemplate {
    // Template method — final để subclass không override toàn bộ flow
    public final String taoBaoCao() {
        StringBuilder sb = new StringBuilder();
        sb.append(taoTieuDe());
        sb.append("\n");
        sb.append(taoNoiDung());
        sb.append("\n");
        if (canChanTrang()) {           // Hook method — subclass có thể override
            sb.append(taoChanTrang());
        }
        return sb.toString();
    }

    // Abstract steps — subclass PHẢI implement
    protected abstract String taoTieuDe();
    protected abstract String taoNoiDung();

    // Hook method — có default, subclass CÓ THỂ override
    protected boolean canChanTrang() { return true; }

    protected String taoChanTrang() {
        return "\n--- Báo cáo tạo lúc: " + LocalDateTime.now() + " ---";
    }
}

public class BaoCaoDoanhThu extends BaoCaoTemplate {
    protected String taoTieuDe() { return "=== BÁO CÁO DOANH THU ==="; }
    protected String taoNoiDung() { return "Tổng doanh thu: 1,500,000,000đ"; }
}

public class BaoCaoNhanSu extends BaoCaoTemplate {
    protected String taoTieuDe() { return "=== BÁO CÁO NHÂN SỰ ==="; }
    protected String taoNoiDung() { return "Tổng nhân viên: 150 người"; }
    protected boolean canChanTrang() { return false; }  // Không cần chân trang
}
```

> **Thực tế:** `HttpServlet.service()` gọi `doGet()`/`doPost()`, Spring `JdbcTemplate.execute()`, JUnit `@BeforeEach`/`@Test`/`@AfterEach`.

### 3.4 Command

**Mục đích:** Đóng gói yêu cầu thành đối tượng, cho phép **undo/redo**, queuing, logging.

**Khi nào dùng:** Text editor (undo/redo), transaction systems, job schedulers, macro recording.

```java
public interface LenhSoanThao {
    void thucThi();
    void hoaTac();  // Undo
    String moTa();
}

public class LenhVietChu implements LenhSoanThao {
    private final VanBan vanBan;
    private final String noiDung;
    private final int viTri;

    public LenhVietChu(VanBan vanBan, String noiDung) {
        this.vanBan = vanBan;
        this.noiDung = noiDung;
        this.viTri = vanBan.getLength();
    }

    public void thucThi() { vanBan.them(noiDung); }
    public void hoaTac() { vanBan.xoa(viTri, noiDung.length()); }
    public String moTa() { return "Viết: \"" + noiDung + "\""; }
}

// Invoker — quản lý lịch sử lệnh
public class LichSuLenh {
    private final Deque<LenhSoanThao> undoStack = new ArrayDeque<>();
    private final Deque<LenhSoanThao> redoStack = new ArrayDeque<>();

    public void thucThi(LenhSoanThao lenh) {
        lenh.thucThi();
        undoStack.push(lenh);
        redoStack.clear();  // Xóa redo khi có action mới
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            LenhSoanThao lenh = undoStack.pop();
            lenh.hoaTac();
            redoStack.push(lenh);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            LenhSoanThao lenh = redoStack.pop();
            lenh.thucThi();
            undoStack.push(lenh);
        }
    }
}
```

### 3.5 Chain of Responsibility

**Mục đích:** Cho phép truyền yêu cầu qua chuỗi các handler. Mỗi handler quyết định **xử lý hoặc chuyển tiếp**.

**Khi nào dùng:** Middleware pipeline (Spring Security filters), validation chains, logging levels.

```java
public abstract class XuLyYeuCau {
    protected XuLyYeuCau tiepTheo;

    public XuLyYeuCau datTiepTheo(XuLyYeuCau handler) {
        this.tiepTheo = handler;
        return handler;  // Cho phép chaining
    }

    public abstract boolean xuLy(YeuCau yeuCau);

    protected boolean chuyenTiep(YeuCau yeuCau) {
        return tiepTheo != null && tiepTheo.xuLy(yeuCau);
    }
}

// Handlers
public class XacThucHandler extends XuLyYeuCau {
    public boolean xuLy(YeuCau yeuCau) {
        if (!yeuCau.hasToken()) {
            System.out.println("❌ Chưa xác thực!");
            return false;
        }
        System.out.println("✓ Xác thực OK");
        return chuyenTiep(yeuCau);
    }
}

public class PhanQuyenHandler extends XuLyYeuCau {
    public boolean xuLy(YeuCau yeuCau) {
        if (!yeuCau.hasPermission("ADMIN")) {
            System.out.println("❌ Không có quyền!");
            return false;
        }
        System.out.println("✓ Phân quyền OK");
        return chuyenTiep(yeuCau);
    }
}

public class RateLimitHandler extends XuLyYeuCau {
    public boolean xuLy(YeuCau yeuCau) {
        if (isRateLimited(yeuCau)) {
            System.out.println("❌ Quá nhiều request!");
            return false;
        }
        System.out.println("✓ Rate limit OK");
        return chuyenTiep(yeuCau);
    }
}

// Thiết lập chain
XuLyYeuCau chain = new XacThucHandler();
chain.datTiepTheo(new PhanQuyenHandler())
     .datTiepTheo(new RateLimitHandler());

chain.xuLy(request); // Đi qua từng handler
```

> **Thực tế:** Java Servlet Filters, Spring Security Filter Chain, Java Logger hierarchy đều dùng Chain of Responsibility.

### 3.6 State Pattern (Bonus)

**Mục đích:** Cho phép object thay đổi behavior khi internal state thay đổi.

```java
public interface TrangThaiDonHang {
    void tiepTheo(DonHang donHang);
    void huy(DonHang donHang);
    String getTen();
}

public class ChoXacNhan implements TrangThaiDonHang {
    public void tiepTheo(DonHang dh) { dh.setTrangThai(new DangGiao()); }
    public void huy(DonHang dh) { dh.setTrangThai(new DaHuy()); }
    public String getTen() { return "Chờ xác nhận"; }
}

public class DangGiao implements TrangThaiDonHang {
    public void tiepTheo(DonHang dh) { dh.setTrangThai(new DaGiao()); }
    public void huy(DonHang dh) { throw new IllegalStateException("Không thể hủy đơn đang giao"); }
    public String getTen() { return "Đang giao"; }
}
```

---

## Tổng kết: Khi nào dùng Pattern nào?

| Pattern | Khi nào dùng | Ví dụ thực tế |
|---------|-------------|--------------|
| **Singleton** | Cần đúng 1 instance | DB pool, Config, Logger |
| **Factory Method** | Tạo object dựa trên runtime condition | `Calendar.getInstance()`, `NumberFormat` |
| **Abstract Factory** | Tạo nhóm products liên quan | UI toolkit, DB driver family |
| **Builder** | Object có nhiều tham số tùy chọn | `StringBuilder`, `HttpRequest.newBuilder()` |
| **Prototype** | Clone object nhanh hơn tạo mới | Game object spawning, config templates |
| **Adapter** | Tích hợp interface không tương thích | `InputStreamReader`, legacy system wrapper |
| **Decorator** | Thêm chức năng linh hoạt, combinable | `BufferedInputStream`, Spring `@Cacheable` |
| **Proxy** | Kiểm soát truy cập, caching, lazy loading | Spring AOP, `@Transactional` |
| **Facade** | Đơn giản hóa hệ thống phức tạp | `JdbcTemplate`, `RestTemplate` |
| **Composite** | Cấu trúc cây (part-whole) | File system, UI component tree, menu |
| **Observer** | Event/notification system | Spring Events, DOM events, message queue |
| **Strategy** | Thay đổi algorithm tại runtime | Sorting comparator, payment methods |
| **Template Method** | Algorithm có bước cố định + bước tùy biến | `HttpServlet.doGet()`, test lifecycle |
| **Command** | Undo/redo, queuing, logging | Text editor, transaction, job scheduler |
| **Chain of Responsibility** | Pipeline xử lý tuần tự | Security filters, validation chain |
| **State** | Object thay đổi behavior theo trạng thái | Đơn hàng, TCP connection, vending machine |

> **Lời khuyên:** Đừng áp dụng pattern vì "nghe hay" — chỉ dùng khi có vấn đề cụ thể cần giải quyết. Over-engineering với patterns còn tệ hơn không dùng.
