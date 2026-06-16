# Spring AOP - Bài Tập

## Bài 1: Logging Aspect
**Độ khó: Dễ - Trung bình**

1. Tạo `LoggingAspect` log method entry/exit cho tất cả service layer.
2. Log: class name, method name, arguments, return value, execution time.
3. Nếu method throw exception → log error với exception message.
4. Chỉ log cho methods trong package `com.study.service`.
5. Test: gọi service methods và verify log output.

---

## Bài 2: Custom Annotation - @MeasureTime
**Độ khó: Trung bình**

1. Tạo annotation `@MeasureTime` với attribute `warnThresholdMs` (default 1000ms).
2. Aspect đo execution time mỗi method có `@MeasureTime`.
3. Nếu vượt threshold → log WARN, ngược lại → log INFO.
4. Ghi metrics vào MeterRegistry (Micrometer).
5. Áp dụng cho các service methods quan trọng và test.

---

## Bài 3: Rate Limiting Aspect
**Độ khó: Trung bình - Khó**

1. Annotation `@RateLimit(requests=10, periodSeconds=60)`.
2. Aspect kiểm tra rate limit per-user (lấy user từ SecurityContext).
3. Nếu exceed → throw `RateLimitExceededException` (HTTP 429).
4. Sử dụng ConcurrentHashMap để lưu request counts.
5. Áp dụng cho API endpoints nhạy cảm (login, register, send-email).
6. Viết test simulate nhiều requests liên tiếp.

---

## Bài 4: Retry Aspect
**Độ khó: Khó**

1. Annotation `@Retry(maxAttempts=3, delay=1000, backoffMultiplier=2)`.
2. Aspect retry method khi throw specified exceptions.
3. Exponential backoff: delay * multiplier^attempt (1s, 2s, 4s).
4. Attribute `retryOn` chỉ định exceptions nào trigger retry.
5. Log mỗi retry attempt.
6. Test với service gọi API external (mock để throw exception).

---

## Bài 5: Audit Trail Aspect
**Độ khó: Khó**

1. Annotation `@Auditable(action="CREATE", entity="User")`.
2. Aspect ghi audit log: who (user), what (action), when (timestamp), entity, old/new values.
3. Lưu audit logs vào database (AuditLog entity).
4. Chỉ ghi khi method thành công (dùng `@AfterReturning`).
5. Cho phương thức update: so sánh old vs new value và ghi diff.
6. Endpoint `GET /api/audit-logs` query audit history.
