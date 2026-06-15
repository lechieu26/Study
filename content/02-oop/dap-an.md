# Java OOP - Đáp Án

## Bài 1: Class và Object - Quản lý Sinh Viên

```java
import java.util.Objects;

public class SinhVien {
    private String maSV;
    private String hoTen;
    private double diemTB;

    public SinhVien() {
        this("Unknown", "Unknown", 0.0);
    }

    public SinhVien(String maSV, String hoTen, double diemTB) {
        setMaSV(maSV);
        setHoTen(hoTen);
        setDiemTB(diemTB);
    }

    public String getMaSV() { return maSV; }
    public String getHoTen() { return hoTen; }
    public double getDiemTB() { return diemTB; }

    public void setMaSV(String maSV) {
        if (maSV == null || maSV.isBlank()) throw new IllegalArgumentException("Mã SV không được rỗng");
        this.maSV = maSV;
    }

    public void setHoTen(String hoTen) {
        if (hoTen == null || hoTen.isBlank()) throw new IllegalArgumentException("Họ tên không được rỗng");
        this.hoTen = hoTen;
    }

    public void setDiemTB(double diemTB) {
        if (diemTB < 0 || diemTB > 10) throw new IllegalArgumentException("Điểm TB phải từ 0-10");
        this.diemTB = diemTB;
    }

    public String xepLoai() {
        if (diemTB >= 9) return "Xuất sắc";
        if (diemTB >= 8) return "Giỏi";
        if (diemTB >= 6.5) return "Khá";
        if (diemTB >= 5) return "Trung bình";
        return "Yếu";
    }

    @Override
    public String toString() {
        return "SinhVien{maSV='%s', hoTen='%s', diemTB=%.1f, xepLoai='%s'}".formatted(maSV, hoTen, diemTB, xepLoai());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SinhVien sv = (SinhVien) o;
        return Objects.equals(maSV, sv.maSV);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maSV);
    }

    public static void main(String[] args) {
        SinhVien sv1 = new SinhVien("SV001", "Nguyễn Văn An", 9.2);
        SinhVien sv2 = new SinhVien("SV002", "Trần Thị Bình", 7.5);
        SinhVien sv3 = new SinhVien("SV003", "Lê Văn Cường", 4.8);

        System.out.println(sv1); // SinhVien{maSV='SV001', ..., xepLoai='Xuất sắc'}
        System.out.println(sv2); // SinhVien{maSV='SV002', ..., xepLoai='Khá'}
        System.out.println(sv3); // SinhVien{maSV='SV003', ..., xepLoai='Yếu'}
    }
}
```

---

## Bài 2: Kế thừa - Hệ thống Nhân viên

```java
import java.util.List;

public abstract class NhanVien {
    protected String maNV;
    protected String hoTen;
    protected double luongCoBan;

    public NhanVien(String maNV, String hoTen, double luongCoBan) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.luongCoBan = luongCoBan;
    }

    public abstract double tinhLuong();

    @Override
    public String toString() {
        return "%s - %s - Lương: %,.0f".formatted(maNV, hoTen, tinhLuong());
    }
}

class NhanVienFullTime extends NhanVien {
    private double phuCap;

    public NhanVienFullTime(String maNV, String hoTen, double luongCoBan, double phuCap) {
        super(maNV, hoTen, luongCoBan);
        this.phuCap = phuCap;
    }

    @Override
    public double tinhLuong() {
        return luongCoBan + phuCap;
    }
}

class NhanVienPartTime extends NhanVien {
    private int soGio;
    private double donGia;

    public NhanVienPartTime(String maNV, String hoTen, double luongCoBan, int soGio, double donGia) {
        super(maNV, hoTen, luongCoBan);
        this.soGio = soGio;
        this.donGia = donGia;
    }

    @Override
    public double tinhLuong() {
        return soGio * donGia;
    }
}

class QuanLy extends NhanVienFullTime {
    private double thuongQuanLy;

    public QuanLy(String maNV, String hoTen, double luongCoBan, double phuCap, double thuongQuanLy) {
        super(maNV, hoTen, luongCoBan, phuCap);
        this.thuongQuanLy = thuongQuanLy;
    }

    @Override
    public double tinhLuong() {
        return super.tinhLuong() + thuongQuanLy;
    }

    public static void main(String[] args) {
        List<NhanVien> dsNV = List.of(
            new NhanVienFullTime("NV01", "An", 10_000_000, 2_000_000),
            new NhanVienPartTime("NV02", "Bình", 0, 80, 100_000),
            new QuanLy("NV03", "Cường", 15_000_000, 3_000_000, 5_000_000)
        );

        dsNV.forEach(System.out::println);
        double tongLuong = dsNV.stream().mapToDouble(NhanVien::tinhLuong).sum();
        System.out.println("Tổng lương: " + String.format("%,.0f", tongLuong));
    }
}
```

---

## Bài 3: Interface và Polymorphism - Hệ thống Hình học

```java
import java.util.*;

public interface HinhHoc {
    double tinhDienTich();
    double tinhChuVi();
    String tenHinh();
}

class HinhTron implements HinhHoc {
    private double banKinh;
    public HinhTron(double banKinh) { this.banKinh = banKinh; }

    @Override public double tinhDienTich() { return Math.PI * banKinh * banKinh; }
    @Override public double tinhChuVi() { return 2 * Math.PI * banKinh; }
    @Override public String tenHinh() { return "Hình Tròn (r=" + banKinh + ")"; }
}

class HinhChuNhat implements HinhHoc {
    private double dai, rong;
    public HinhChuNhat(double dai, double rong) { this.dai = dai; this.rong = rong; }

    @Override public double tinhDienTich() { return dai * rong; }
    @Override public double tinhChuVi() { return 2 * (dai + rong); }
    @Override public String tenHinh() { return "Hình Chữ Nhật (" + dai + "x" + rong + ")"; }
}

class HinhTamGiac implements HinhHoc {
    private double a, b, c;
    public HinhTamGiac(double a, double b, double c) { this.a = a; this.b = b; this.c = c; }

    @Override
    public double tinhDienTich() {
        double p = tinhChuVi() / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    @Override public double tinhChuVi() { return a + b + c; }
    @Override public String tenHinh() { return "Hình Tam Giác (" + a + ", " + b + ", " + c + ")"; }

    public static void main(String[] args) {
        List<HinhHoc> ds = new ArrayList<>(List.of(
            new HinhTron(5),
            new HinhChuNhat(4, 6),
            new HinhTamGiac(3, 4, 5)
        ));

        ds.forEach(h -> System.out.printf("%s | S=%.2f | C=%.2f%n", h.tenHinh(), h.tinhDienTich(), h.tinhChuVi()));

        ds.sort((a1, b1) -> Double.compare(b1.tinhDienTich(), a1.tinhDienTich()));
        System.out.println("\nSắp xếp theo diện tích giảm dần:");
        ds.forEach(h -> System.out.printf("%s | S=%.2f%n", h.tenHinh(), h.tinhDienTich()));
    }
}
```

---

## Bài 4: Encapsulation - Tài khoản Ngân hàng

```java
import java.util.*;
import java.time.LocalDateTime;

public class TaiKhoanNganHang {
    private final String soTaiKhoan;
    private final String chuTaiKhoan;
    private double soDu;
    private final List<String> lichSuGiaoDich = new ArrayList<>();

    public TaiKhoanNganHang(String soTaiKhoan, String chuTaiKhoan, double soDuBanDau) {
        this.soTaiKhoan = soTaiKhoan;
        this.chuTaiKhoan = chuTaiKhoan;
        this.soDu = soDuBanDau;
        ghiLog("Mở tài khoản với số dư: " + soDuBanDau);
    }

    public void nopTien(double soTien) {
        if (soTien <= 0) throw new IllegalArgumentException("Số tiền phải > 0");
        soDu += soTien;
        ghiLog("Nộp: +" + soTien + " → Số dư: " + soDu);
    }

    public void rutTien(double soTien) {
        if (soTien <= 0) throw new IllegalArgumentException("Số tiền phải > 0");
        if (soTien > soDu) throw new IllegalArgumentException("Không đủ số dư");
        soDu -= soTien;
        ghiLog("Rút: -" + soTien + " → Số dư: " + soDu);
    }

    public void chuyenTien(TaiKhoanNganHang nguoiNhan, double soTien) {
        rutTien(soTien);
        nguoiNhan.nopTien(soTien);
        ghiLog("Chuyển " + soTien + " đến TK " + nguoiNhan.soTaiKhoan);
    }

    private void ghiLog(String noiDung) {
        lichSuGiaoDich.add("[" + LocalDateTime.now() + "] " + noiDung);
    }

    public double getSoDu() { return soDu; }
    public String getSoTaiKhoan() { return soTaiKhoan; }

    public List<String> getLichSu() {
        return Collections.unmodifiableList(lichSuGiaoDich);
    }

    public static void main(String[] args) {
        TaiKhoanNganHang tk1 = new TaiKhoanNganHang("TK001", "An", 5_000_000);
        TaiKhoanNganHang tk2 = new TaiKhoanNganHang("TK002", "Bình", 3_000_000);

        tk1.nopTien(2_000_000);
        tk1.chuyenTien(tk2, 1_000_000);

        System.out.println("TK1 số dư: " + tk1.getSoDu()); // 6,000,000
        System.out.println("TK2 số dư: " + tk2.getSoDu()); // 4,000,000

        System.out.println("\nLịch sử TK1:");
        tk1.getLichSu().forEach(System.out::println);
    }
}
```

---

## Bài 5: Design Pattern - Builder cho HTTP Request

```java
import java.util.*;

public class HttpRequest {
    private final String url;
    private final String method;
    private final Map<String, String> headers;
    private final String body;
    private final int timeout;
    private final int retryCount;

    private HttpRequest(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.headers = Map.copyOf(builder.headers);
        this.body = builder.body;
        this.timeout = builder.timeout;
        this.retryCount = builder.retryCount;
    }

    public void execute() {
        System.out.println("=== HTTP Request ===");
        System.out.println(method + " " + url);
        headers.forEach((k, v) -> System.out.println(k + ": " + v));
        if (body != null) System.out.println("Body: " + body);
        System.out.println("Timeout: " + timeout + "ms | Retries: " + retryCount);
    }

    static class Builder {
        private final String url;
        private String method = "GET";
        private final Map<String, String> headers = new HashMap<>();
        private String body;
        private int timeout = 30_000;
        private int retryCount = 0;

        Builder(String url) { this.url = url; }

        Builder method(String method) { this.method = method; return this; }
        Builder header(String key, String value) { headers.put(key, value); return this; }
        Builder body(String body) { this.body = body; return this; }
        Builder timeout(int ms) { this.timeout = ms; return this; }
        Builder retryCount(int count) { this.retryCount = count; return this; }

        HttpRequest build() {
            if (url == null || url.isBlank()) throw new IllegalArgumentException("URL required");
            if (timeout <= 0) throw new IllegalArgumentException("Timeout must be > 0");
            return new HttpRequest(this);
        }
    }

    public static void main(String[] args) {
        HttpRequest request = new HttpRequest.Builder("https://api.example.com/users")
                .method("POST")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer token123")
                .body("{\"name\":\"An\"}")
                .timeout(5000)
                .retryCount(3)
                .build();

        request.execute();
    }
}
```
