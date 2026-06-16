# Spring Microservices - Quiz

## Câu 1
[TYPE: MULTIPLE_CHOICE]
Microservices architecture ưu điểm:

A. Deploy, scale, develop từng service độc lập
B. Code đơn giản hơn monolith
C. Không cần network
D. Không cần database

**Đáp án: A**
> Microservices: independent deployment, scaling, tech stack per service. Trade-off: complexity tăng.

## Câu 2
[TYPE: MULTIPLE_CHOICE]
OpenFeign so với RestTemplate:

A. Giống nhau
B. Feign: declarative (interface + annotations), RestTemplate: imperative (manual code)
C. RestTemplate tốt hơn
D. Feign không support error handling

**Đáp án: B**
> Feign = khai báo interface → Spring generate HTTP client. RestTemplate = manual xây request/response.

## Câu 3
[TYPE: TRUE_FALSE]
Circuit Breaker pattern ngăn cascade failure khi downstream service down.

**Đáp án: TRUE**
> Circuit opens → stop sending requests → fallback response → prevent cascade failure + allow recovery.

## Câu 4
[TYPE: MULTIPLE_CHOICE]
Circuit Breaker states:

A. Open, Closed
B. Closed, Open, Half-Open
C. Active, Inactive
D. Running, Stopped, Paused

**Đáp án: B**
> CLOSED (normal) → failure threshold exceeded → OPEN (reject all) → wait → HALF_OPEN (test few) → CLOSED/OPEN.

## Câu 5
[TYPE: SELECT_RESULT]
```yaml
resilience4j:
  circuitbreaker:
    instances:
      myService:
        failure-rate-threshold: 50
        sliding-window-size: 10
```
Khi nào circuit opens?

A. 1 failure
B. 5+ failures trong 10 requests gần nhất (50%)
C. 10 failures liên tiếp
D. Sau 50 giây

**Đáp án: B**
> 50% failure rate trong sliding window 10 calls = 5+ failures → circuit OPEN.

## Câu 6
[TYPE: MULTIPLE_CHOICE]
API Gateway chức năng chính:

A. Database management
B. Single entry point: routing, auth, rate limiting, load balancing
C. Code compilation
D. File storage

**Đáp án: B**
> Gateway = reverse proxy + cross-cutting: authentication, rate limiting, routing, monitoring.

## Câu 7
[TYPE: MULTIPLE_CHOICE]
Service Discovery (Eureka) giải quyết vấn đề:

A. Database replication
B. Services tìm nhau bằng logical name thay vì hardcode IP/port
C. Code versioning
D. Logging

**Đáp án: B**
> Services register tên → clients lookup bằng name → load balancer chọn instance. Dynamic scaling.

## Câu 8
[TYPE: TRUE_FALSE]
Event-driven communication (Kafka/RabbitMQ) là asynchronous - producer không đợi consumer xử lý.

**Đáp án: TRUE**
> Publish event → message broker → consumers process independently. Loose coupling, resilience.

## Câu 9
[TYPE: MULTIPLE_CHOICE]
Saga pattern dùng cho:

A. Authentication
B. Distributed transactions across multiple services
C. Logging
D. Caching

**Đáp án: B**
> Saga = sequence of local transactions. Mỗi step thành công → next step. Failure → compensating transactions.

## Câu 10
[TYPE: MULTIPLE_CHOICE]
Distributed tracing (Zipkin/Jaeger) giúp:

A. Encrypt messages
B. Trace request path across multiple services (với trace ID)
C. Load balance
D. Auto-scale

**Đáp án: B**
> Trace ID propagate qua services → visualize request flow, identify bottlenecks, debug failures.

## Câu 11
[TYPE: SELECT_RESULT]
```java
@FeignClient(name = "user-service", fallback = UserClientFallback.class)
public interface UserClient {
    @GetMapping("/api/users/{id}")
    UserDTO getUser(@PathVariable Long id);
}
```
fallback class được gọi khi?

A. Luôn luôn
B. Khi user-service down hoặc timeout (circuit open)
C. Khi user không tồn tại
D. Khi request thành công

**Đáp án: B**
> Fallback activate khi: service unreachable, timeout, circuit breaker open, hoặc exception.

## Câu 12
[TYPE: MULTIPLE_CHOICE]
Database per service pattern nghĩa là:

A. 1 database cho tất cả services
B. Mỗi service có database riêng, không share database
C. Không cần database
D. Database replicated

**Đáp án: B**
> Mỗi service owns database riêng → loose coupling, independent scaling, technology freedom.

## Câu 13
[TYPE: TRUE_FALSE]
Spring Cloud Config Server centralize configuration cho tất cả services (từ Git repo hoặc Vault).

**Đáp án: TRUE**
> Config Server: 1 nơi quản lý config cho tất cả services. Hot refresh không cần restart.

## Câu 14
[TYPE: MULTIPLE_CHOICE]
CQRS pattern tách:

A. Frontend và Backend
B. Command (write) và Query (read) thành separate models/databases
C. Services và databases
D. Tests và production

**Đáp án: B**
> Command side: write operations (event store). Query side: read-optimized projections. Scale independently.

## Câu 15
[TYPE: MULTIPLE_CHOICE]
Bulkhead pattern:

A. Isolate failures - resource pool riêng cho mỗi downstream call
B. Retry failed requests
C. Cache responses
D. Compress data

**Đáp án: A**
> Bulkhead = isolate thread pools/connections. Nếu 1 downstream slow → chỉ affect pool của nó, không ảnh hưởng toàn bộ.
