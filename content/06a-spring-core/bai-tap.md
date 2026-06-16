# Spring Core - Bài Tập

## Bài 1: Dependency Injection Cơ Bản
**Độ khó: Dễ**

Tạo ứng dụng quản lý thông báo (Notification System):
1. Tạo interface `ThongBaoService` với method `gui(String nguoiNhan, String noiDung)`.
2. Implement 3 class: `EmailThongBao`, `SmsThongBao`, `PushThongBao`.
3. Tạo `ThongBaoManager` sử dụng Constructor Injection nhận `List<ThongBaoService>`.
4. Method `guiTatCa()` gửi thông báo qua tất cả kênh.
5. Viết unit test cho `ThongBaoManager` với mocked services.

---

## Bài 2: Bean Scopes và Lifecycle
**Độ khó: Trung bình**

1. Tạo `GioHang` bean với scope `prototype` chứa danh sách sản phẩm.
2. Tạo `MuaHangService` (singleton) inject `ObjectProvider<GioHang>`.
3. Mỗi lần gọi `taoGioMoi()` phải trả về instance mới.
4. Thêm `@PostConstruct` log khi GioHang được tạo, `@PreDestroy` log khi bị hủy.
5. Viết test chứng minh mỗi lần lấy GioHang là instance khác nhau.

---

## Bài 3: Configuration và Profiles
**Độ khó: Trung bình**

1. Tạo `@ConfigurationProperties` class `AppConfig` bind từ `app.*` properties.
2. Cấu hình 3 profiles: `dev` (H2, debug logging), `staging` (PostgreSQL, info), `prod` (PostgreSQL, warn).
3. Tạo `DataSourceConfig` với `@Profile` tạo DataSource khác nhau cho mỗi profile.
4. Tạo `FeatureToggle` bean conditional (`@ConditionalOnProperty`) bật/tắt tính năng.
5. Viết test kiểm tra properties được bind đúng cho mỗi profile.

---

## Bài 4: Spring Events
**Độ khó: Trung bình - Khó**

Xây dựng hệ thống xử lý đơn hàng dựa trên events:
1. Tạo events: `DonHangTaoEvent`, `DonHangThanhToanEvent`, `DonHangHuyEvent`.
2. Service `DonHangService` publish events khi trạng thái thay đổi.
3. Listeners: `EmailListener` gửi email, `KhoListener` cập nhật tồn kho, `ThongKeListener` ghi thống kê.
4. `EmailListener` chỉ gửi SAU khi transaction commit (`@TransactionalEventListener`).
5. `ThongKeListener` xử lý async (`@Async`).
6. Viết integration test kiểm tra tất cả listeners được gọi đúng.

---

## Bài 5: Custom BeanPostProcessor
**Độ khó: Khó**

1. Tạo annotation `@Encrypted` đánh dấu field cần mã hóa khi lưu.
2. Tạo `EncryptionBeanPostProcessor` tìm beans có field `@Encrypted`.
3. Wrap bean bằng proxy: trước khi gọi setter → mã hóa giá trị, trước khi gọi getter → giải mã.
4. Tạo `UserService` với field `@Encrypted private String password`.
5. Viết test chứng minh password được mã hóa/giải mã tự động.
