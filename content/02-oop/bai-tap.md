# Java OOP - Bài Tập

## Bài 1: Class và Object - Quản lý Sinh Viên
**Độ khó: Dễ**

Tạo class `SinhVien` với các yêu cầu:
1. Fields: `maSV` (String), `hoTen` (String), `diemTB` (double).
2. Constructor đầy đủ tham số + constructor không tham số.
3. Getters/Setters với validation (điểm TB từ 0-10, mã SV không rỗng).
4. Override `toString()`, `equals()` (so sánh theo maSV), `hashCode()`.
5. Method `xepLoai()` trả về: Xuất sắc (>=9), Giỏi (>=8), Khá (>=6.5), Trung bình (>=5), Yếu (<5).

**Test:** Tạo 3 sinh viên, in thông tin và xếp loại.

```java
// Template
public class SinhVien {
    // TODO: Implement
}
```

---

## Bài 2: Kế thừa - Hệ thống Nhân viên
**Độ khó: Trung bình**

Xây dựng hệ thống phân cấp nhân viên:
1. Abstract class `NhanVien` với fields: `maNV`, `hoTen`, `luongCoBan`. Abstract method `tinhLuong()`.
2. Class `NhanVienFullTime extends NhanVien`: lương = lương cơ bản + phụ cấp.
3. Class `NhanVienPartTime extends NhanVien`: lương = số giờ × đơn giá/giờ.
4. Class `QuanLy extends NhanVienFullTime`: lương = lương fulltime + thưởng quản lý.

**Yêu cầu:** Tạo danh sách các NhanVien, dùng polymorphism để tính tổng lương.

```java
// Template
public abstract class NhanVien {
    // TODO: Implement
    public abstract double tinhLuong();
}
```

---

## Bài 3: Interface và Polymorphism - Hệ thống Hình học
**Độ khó: Trung bình**

1. Tạo interface `HinhHoc` với methods: `tinhDienTich()`, `tinhChuVi()`, `tenHinh()`.
2. Implement: `HinhTron`, `HinhChuNhat`, `HinhTamGiac`.
3. Tạo method `inThongTin(List<HinhHoc> danhSach)` in tên, diện tích, chu vi của từng hình.
4. Sắp xếp danh sách theo diện tích giảm dần.

```java
// Template
public interface HinhHoc {
    double tinhDienTich();
    double tinhChuVi();
    String tenHinh();
}
```

---

## Bài 4: Encapsulation - Tài khoản Ngân hàng
**Độ khó: Trung bình**

Tạo class `TaiKhoanNganHang` với đóng gói chặt chẽ:
1. Private fields: `soTaiKhoan`, `chuTaiKhoan`, `soDu`, `lichSuGiaoDich` (List).
2. Methods: `nopTien(double)`, `rutTien(double)`, `chuyenTien(TaiKhoanNganHang, double)`.
3. Validation: số tiền > 0, rút tiền không quá số dư.
4. Mỗi giao dịch tự động ghi vào lịch sử.
5. `getLichSu()` trả về bản sao (defensive copy).

```java
// Template
public class TaiKhoanNganHang {
    // TODO: Implement với encapsulation
}
```

---

## Bài 5: Design Pattern - Builder cho HTTP Request
**Độ khó: Khó**

Implement Builder Pattern cho class `HttpRequest`:
1. Required: `url`, `method` (GET/POST/PUT/DELETE).
2. Optional: `headers` (Map), `body` (String), `timeout` (int), `retryCount` (int).
3. Validation trong `build()`: url không rỗng, timeout > 0.
4. Method `execute()` in ra thông tin request.

```java
// Template
public class HttpRequest {
    // TODO: Implement với Builder Pattern
}
```
