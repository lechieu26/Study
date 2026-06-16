# Spring Microservices - Bài Tập

## Bài 1: Service-to-Service Communication
**Độ khó: Trung bình**

Xây dựng 2 services giao tiếp với nhau:
1. `product-service` (port 8081): CRUD sản phẩm.
2. `order-service` (port 8082): tạo đơn hàng, gọi product-service kiểm tra tồn kho.
3. Sử dụng OpenFeign: `ProductClient` interface khai báo endpoints product-service.
4. Implement Fallback khi product-service không available.
5. Test: tắt product-service → verify fallback hoạt động.

---

## Bài 2: Circuit Breaker với Resilience4j
**Độ khó: Trung bình - Khó**

1. Cấu hình Circuit Breaker cho order-service khi gọi product-service.
2. Sliding window: 10 requests, failure rate threshold 50%.
3. Wait duration in open state: 30 seconds.
4. Fallback method trả về response mặc định khi circuit open.
5. Thêm Retry: 3 lần, delay 2s giữa các lần.
6. Test: simulate 6/10 failures → verify circuit opens → verify auto-recovery.

---

## Bài 3: API Gateway
**Độ khó: Trung bình - Khó**

1. Tạo Spring Cloud Gateway (port 8080) route requests tới product/order services.
2. Routes: /api/products/** → product-service, /api/orders/** → order-service.
3. Global filter: log tất cả requests (method, path, response time).
4. Auth filter: verify JWT token, reject unauthorized requests.
5. Rate limiting: 100 requests/phút per IP.
6. Test toàn bộ flow thông qua gateway.

---

## Bài 4: Event-Driven với Kafka/RabbitMQ
**Độ khó: Khó**

1. Khi đơn hàng được tạo → publish `OrderCreatedEvent`.
2. `inventory-service` subscribe event → giảm tồn kho.
3. `notification-service` subscribe event → gửi email xác nhận.
4. Nếu tồn kho không đủ → publish `OrderRejectedEvent`.
5. Implement Dead Letter Queue cho failed messages.
6. Test: create order → verify cả 2 consumers xử lý đúng.

---

## Bài 5: Distributed Tracing và Monitoring
**Độ khó: Khó**

1. Cấu hình Micrometer Tracing cho tất cả services.
2. Trace ID propagation qua HTTP calls giữa services.
3. Centralized logging: format log với traceId, spanId.
4. Setup Actuator health checks cho mỗi service.
5. Docker Compose: run tất cả services + PostgreSQL + Zipkin.
6. Test: gọi API qua gateway → trace request across 3 services.
