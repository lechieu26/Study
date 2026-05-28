# Design Patterns - Bài Tập

## Bài 1: Singleton - Quản lý Cấu Hình Ứng Dụng
**Độ khó: Trung bình**

Implement class `CauHinhUngDung` sử dụng Singleton pattern:
1. Đọc cấu hình từ file properties.
2. Cung cấp phương thức `layGiaTri(String key)` và `datGiaTri(String key, String value)`.
3. Hỗ trợ reload cấu hình mà không cần restart ứng dụng.
4. Đảm bảo thread-safe.

**Yêu cầu thêm:** So sánh 3 cách implement Singleton (eager, lazy double-checked, enum) và giải thích ưu nhược điểm.

---

## Bài 2: Factory + Strategy - Hệ Thống Thanh Toán
**Độ khó: Khó**

Xây dựng hệ thống thanh toán đa phương thức:
1. Tạo interface `ThanhToan` với phương thức `xuLy(double soTien)`.
2. Implement các strategy: `TheNoiDia`, `TheQuocTe`, `ViDienTu`, `ChuyenKhoan`.
3. Mỗi phương thức có phí xử lý khác nhau (% hoặc cố định).
4. Sử dụng Factory để tạo strategy phù hợp dựa trên input.
5. Thêm decorator `GhiLogThanhToan` để log mỗi giao dịch.

**Đầu vào:** Loại thanh toán + số tiền
**Đầu ra:** Chi tiết giao dịch bao gồm phí và số tiền thực nhận

---

## Bài 3: Observer - Hệ Thống Theo Dõi Giá Cổ Phiếu
**Độ khó: Trung bình - Khó**

Xây dựng hệ thống theo dõi giá cổ phiếu:
1. `SanGiaoDich` (Subject) quản lý danh sách cổ phiếu và giá hiện tại.
2. `NhaDauTu` (Observer) nhận thông báo khi giá thay đổi.
3. Mỗi nhà đầu tư có thể đăng ký theo dõi nhiều mã cổ phiếu.
4. Hỗ trợ cảnh báo khi giá vượt ngưỡng (trên/dưới mức đặt trước).
5. Implement `LichSuGia` observer để lưu lịch sử biến động.

---

## Bài 4: Builder - Tạo Truy Vấn SQL Động
**Độ khó: Khó**

Xây dựng `SQLQueryBuilder` sử dụng Builder pattern:
1. Hỗ trợ: SELECT, FROM, WHERE, JOIN, GROUP BY, HAVING, ORDER BY, LIMIT.
2. WHERE hỗ trợ: AND, OR, IN, BETWEEN, LIKE, IS NULL.
3. JOIN hỗ trợ: INNER, LEFT, RIGHT, FULL.
4. Validation: kiểm tra truy vấn hợp lệ trước khi build.
5. Hỗ trợ tham số hóa (parameterized query) để tránh SQL injection.

**Ví dụ sử dụng:**
```java
String sql = new SQLQueryBuilder()
    .select("nv.ten", "pb.tenPhongBan", "AVG(nv.luong)")
    .from("nhan_vien nv")
    .join(JoinType.INNER, "phong_ban pb", "nv.phong_ban_id = pb.id")
    .where("nv.tuoi > ?", 25)
    .and("pb.tenPhongBan IN (?, ?)", "IT", "HR")
    .groupBy("pb.tenPhongBan")
    .having("AVG(nv.luong) > ?", 15000000)
    .orderBy("AVG(nv.luong) DESC")
    .limit(10)
    .build();
```

---

## Bài 5: Decorator + Chain of Responsibility - Xử Lý Request HTTP
**Độ khó: Rất Khó**

Xây dựng middleware pipeline cho HTTP request:
1. Tạo interface `Middleware` với phương thức `xuLy(Request req, Response res)`.
2. Implement các middleware:
   - `XacThucMiddleware`: Kiểm tra token JWT.
   - `PhanQuyenMiddleware`: Kiểm tra role người dùng.
   - `RateLimitMiddleware`: Giới hạn số request/phút.
   - `LogMiddleware`: Ghi log request/response.
   - `CacheMiddleware`: Cache response cho GET request.
3. Sử dụng Chain of Responsibility để kết nối các middleware.
4. Sử dụng Decorator để thêm tính năng (compression, encryption).
5. Có thể cấu hình pipeline linh hoạt cho từng endpoint.

---

## Bài 6: Template Method + Strategy - Xuất Báo Cáo
**Độ khó: Trung bình**

Xây dựng hệ thống xuất báo cáo đa định dạng:
1. Template: Thu thập dữ liệu → Xử lý → Định dạng → Xuất file.
2. Strategy cho định dạng: PDF, Excel, CSV, HTML.
3. Dữ liệu báo cáo: Danh sách nhân viên với lương, phòng ban.
4. Tổng kết: Tổng lương, trung bình, min, max theo phòng ban.

---

## Bài 7: Command + Memento - Text Editor với Undo/Redo
**Độ khó: Khó**

Implement text editor đơn giản:
1. Các lệnh: Gõ chữ, Xóa, Copy, Paste, Tìm-Thay thế.
2. Hỗ trợ Undo/Redo không giới hạn.
3. Lưu snapshot (Memento) tại các mốc quan trọng.
4. Macro: Ghi lại chuỗi lệnh và phát lại.
