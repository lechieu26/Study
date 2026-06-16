# Spring Boot - Bài Tập

## Bài 1: Khởi Tạo Project Spring Boot
**Độ khó: Dễ**

1. Tạo project Spring Boot với dependencies: Web, DevTools, Actuator, Validation.
2. Cấu hình `application.yml` với server port 9090, context-path `/api`.
3. Tạo endpoint `GET /info` trả về thông tin app (tên, version, author).
4. Cấu hình Actuator expose endpoints: health, info, metrics.
5. Tạo custom `HealthIndicator` kiểm tra disk space.
6. Chạy ứng dụng và kiểm tra: `http://localhost:9090/api/info`, `http://localhost:9090/api/actuator/health`.

---

## Bài 2: Configuration Properties
**Độ khó: Trung bình**

1. Tạo `@ConfigurationProperties` class `AppProperties` với prefix `app`.
2. Properties: name, version, contact.email, contact.phone, features (Map<String, Boolean>).
3. Validate: name @NotBlank, email @Email.
4. Tạo 3 profiles (dev, staging, prod) với cấu hình khác nhau.
5. Tạo REST endpoint `GET /config` trả về config hiện tại.
6. Test: chạy với `--spring.profiles.active=dev` và verify kết quả.

---

## Bài 3: Custom Auto-Configuration
**Độ khó: Khó**

1. Tạo library cung cấp `NotificationAutoConfiguration`.
2. Auto-configure `NotificationService` khi có property `app.notification.enabled=true`.
3. Cung cấp `EmailNotification` nếu `spring-boot-starter-mail` trong classpath.
4. Cung cấp `SlackNotification` nếu property `app.notification.slack.webhook` có giá trị.
5. User có thể override bằng cách define bean `NotificationService` riêng.
6. Viết test cho từng conditional case.

---

## Bài 4: Custom Metrics và Monitoring
**Độ khó: Trung bình**

1. Tạo REST API quản lý tasks (CRUD).
2. Thêm custom metrics: `task.created.total` (Counter), `task.processing.time` (Timer), `task.active.count` (Gauge).
3. Tạo custom `HealthIndicator` kiểm tra số tasks quá hạn.
4. Cấu hình Prometheus endpoint export metrics.
5. Tạo `ApplicationRunner` warm up cache khi startup.

---

## Bài 5: Embedded Server và Graceful Shutdown
**Độ khó: Trung bình - Khó**

1. Cấu hình embedded Tomcat: max-threads 100, connection-timeout 20s, SSL enabled.
2. Implement graceful shutdown: đợi requests hoàn thành trước khi tắt.
3. Tạo long-running endpoint (simulate 5s processing).
4. Test: gửi request, gửi SIGTERM, verify request hoàn thành trước shutdown.
5. Tạo `CommandLineRunner` seed data khi startup.
