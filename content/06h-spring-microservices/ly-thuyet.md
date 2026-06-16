# Spring Microservices - Lý Thuyết Chi Tiết

## Giới thiệu

**Microservices** là kiến trúc chia ứng dụng thành các service nhỏ, độc lập, mỗi service có database riêng và giao tiếp qua network (REST, messaging). **Spring Cloud** cung cấp bộ công cụ xây dựng microservices: service discovery, configuration, circuit breaker, API gateway.

**Monolith vs Microservices:**

| Tiêu chí | Monolith | Microservices |
|----------|----------|--------------|
| Deployment | 1 artifact | Nhiều services riêng biệt |
| Scaling | Scale toàn bộ | Scale từng service |
| Database | 1 shared DB | DB per service |
| Team | 1 team lớn | Nhiều team nhỏ |
| Failure | 1 lỗi = toàn bộ down | 1 service lỗi ≠ toàn bộ down |
| Complexity | Code đơn giản, deploy khó scale | Code phức tạp hơn, scale linh hoạt |

**Kiến trúc điển hình:**
```
Client → API Gateway → Service A → Database A
                     → Service B → Database B
                     → Service C → Database C
              ↕
         Config Server
         Service Discovery (Eureka)
         Circuit Breaker (Resilience4j)
```

---

## 1. Service Communication

### 1.1 RestTemplate (Legacy)

```java
@Service
public class OrderService {
    private final RestTemplate restTemplate;

    public OrderService(RestTemplateBuilder builder) {
        this.restTemplate = builder
            .rootUri("http://product-service")
            .setConnectTimeout(Duration.ofSeconds(5))
            .setReadTimeout(Duration.ofSeconds(10))
            .build();
    }

    public ProductDTO getProduct(Long id) {
        return restTemplate.getForObject("/api/products/{id}", ProductDTO.class, id);
    }

    public ProductDTO createProduct(ProductRequest request) {
        return restTemplate.postForObject("/api/products", request, ProductDTO.class);
    }
}
```

### 1.2 WebClient (Reactive — Khuyến nghị)

```java
@Service
public class OrderService {
    private final WebClient webClient;

    public OrderService(WebClient.Builder builder) {
        this.webClient = builder
            .baseUrl("http://product-service")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .filter(ExchangeFilterFunctions.basicAuthentication("user", "pass"))
            .build();
    }

    public Mono<ProductDTO> getProduct(Long id) {
        return webClient.get()
            .uri("/api/products/{id}", id)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response ->
                Mono.error(new ProductNotFoundException("Product not found: " + id)))
            .onStatus(HttpStatusCode::is5xxServerError, response ->
                Mono.error(new ServiceException("Product service unavailable")))
            .bodyToMono(ProductDTO.class);
    }

    // Blocking call (khi cần dùng trong non-reactive code)
    public ProductDTO getProductSync(Long id) {
        return getProduct(id).block(Duration.ofSeconds(10));
    }

    public Flux<ProductDTO> getAllProducts() {
        return webClient.get()
            .uri("/api/products")
            .retrieve()
            .bodyToFlux(ProductDTO.class);
    }
}
```

### 1.3 OpenFeign (Declarative REST Client)

```java
// 1. Enable Feign
@SpringBootApplication
@EnableFeignClients
public class OrderApplication {}

// 2. Declare Feign Client
@FeignClient(
    name = "product-service",
    url = "${services.product.url:http://localhost:8081}",
    fallback = ProductClientFallback.class
)
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    ProductDTO getProduct(@PathVariable Long id);

    @GetMapping("/api/products")
    List<ProductDTO> getAllProducts();

    @PostMapping("/api/products")
    ProductDTO createProduct(@RequestBody ProductRequest request);

    @GetMapping("/api/products/{id}/stock")
    StockDTO checkStock(@PathVariable Long id, @RequestParam int quantity);
}

// 3. Fallback (khi service down)
@Component
public class ProductClientFallback implements ProductClient {
    
    @Override
    public ProductDTO getProduct(Long id) {
        return new ProductDTO(id, "Unknown Product", BigDecimal.ZERO);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return Collections.emptyList();
    }

    @Override
    public ProductDTO createProduct(ProductRequest request) {
        throw new ServiceUnavailableException("Product service unavailable");
    }

    @Override
    public StockDTO checkStock(Long id, int quantity) {
        return new StockDTO(id, 0, false);
    }
}

// 4. Sử dụng trong service
@Service
public class OrderService {
    private final ProductClient productClient;

    public Order createOrder(OrderRequest request) {
        ProductDTO product = productClient.getProduct(request.getProductId());
        StockDTO stock = productClient.checkStock(request.getProductId(), request.getQuantity());
        
        if (!stock.isAvailable()) {
            throw new InsufficientStockException("Hết hàng");
        }
        // Tạo đơn hàng...
    }
}
```

---

## 2. Circuit Breaker (Resilience4j)

### 2.1 Khái niệm

Circuit Breaker bảo vệ service khi dependency bị lỗi, tránh cascade failure.

**3 trạng thái:**
```
CLOSED (bình thường)
   → Khi failure rate > threshold
OPEN (ngắt mạch — reject requests ngay lập tức)
   → Sau wait duration
HALF_OPEN (thử lại một số requests)
   → Nếu success → CLOSED
   → Nếu fail → OPEN
```

### 2.2 Configuration

```yaml
# application.yml
resilience4j:
  circuitbreaker:
    instances:
      productService:
        sliding-window-size: 10           # Số requests để tính failure rate
        failure-rate-threshold: 50         # 50% failure → OPEN
        wait-duration-in-open-state: 30s   # Đợi 30s rồi HALF_OPEN
        permitted-number-of-calls-in-half-open-state: 3
        slow-call-duration-threshold: 5s   # > 5s = slow call
        slow-call-rate-threshold: 80       # 80% slow → OPEN
  retry:
    instances:
      productService:
        max-attempts: 3
        wait-duration: 2s
        retry-exceptions:
          - java.io.IOException
          - java.util.concurrent.TimeoutException
  timelimiter:
    instances:
      productService:
        timeout-duration: 10s
```

### 2.3 Sử dụng

```java
@Service
public class OrderService {
    private final ProductClient productClient;

    @CircuitBreaker(name = "productService", fallbackMethod = "getProductFallback")
    @Retry(name = "productService")
    @TimeLimiter(name = "productService")
    public CompletableFuture<ProductDTO> getProduct(Long id) {
        return CompletableFuture.supplyAsync(() -> productClient.getProduct(id));
    }

    // Fallback method (phải cùng return type + thêm Exception param)
    public CompletableFuture<ProductDTO> getProductFallback(Long id, Exception ex) {
        log.warn("Circuit breaker activated for product {}: {}", id, ex.getMessage());
        return CompletableFuture.completedFuture(
            new ProductDTO(id, "Service unavailable", BigDecimal.ZERO));
    }
}
```

### 2.4 Programmatic Usage

```java
@Service
public class ResilientProductService {

    private final CircuitBreakerRegistry cbRegistry;
    private final RetryRegistry retryRegistry;
    private final ProductClient productClient;

    public ProductDTO getProduct(Long id) {
        CircuitBreaker cb = cbRegistry.circuitBreaker("productService");
        Retry retry = retryRegistry.retry("productService");

        Supplier<ProductDTO> supplier = () -> productClient.getProduct(id);
        
        Supplier<ProductDTO> decorated = Decorators.ofSupplier(supplier)
            .withCircuitBreaker(cb)
            .withRetry(retry)
            .withFallback(List.of(CallNotPermittedException.class),
                e -> new ProductDTO(id, "Fallback", BigDecimal.ZERO))
            .decorate();

        return decorated.get();
    }
}
```

---

## 3. API Gateway

### 3.1 Spring Cloud Gateway

```yaml
# gateway-service application.yml
server:
  port: 8080

spring:
  cloud:
    gateway:
      routes:
        - id: product-service
          uri: http://localhost:8081
          predicates:
            - Path=/api/products/**
          filters:
            - StripPrefix=0
            - AddRequestHeader=X-Gateway, true
            - CircuitBreaker=name=productCB,fallbackUri=forward:/fallback/products

        - id: order-service
          uri: http://localhost:8082
          predicates:
            - Path=/api/orders/**
          filters:
            - StripPrefix=0
            - RateLimit=10,60  # 10 requests per 60 seconds

        - id: user-service
          uri: http://localhost:8083
          predicates:
            - Path=/api/users/**
            - Method=GET,POST,PUT,DELETE
          filters:
            - StripPrefix=0
```

### 3.2 Custom Gateway Filter

```java
@Component
public class AuthGatewayFilter implements GlobalFilter, Ordered {

    private final JwtTokenProvider tokenProvider;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        
        // Skip auth for public endpoints
        if (path.startsWith("/api/auth/") || path.startsWith("/actuator/")) {
            return chain.filter(exchange);
        }

        String token = extractToken(exchange.getRequest());
        if (token == null || !tokenProvider.validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Add user info to headers for downstream services
        String username = tokenProvider.getUsernameFromToken(token);
        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
            .header("X-User-Name", username)
            .build();

        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    @Override
    public int getOrder() {
        return -1;  // Execute first
    }
}
```

---

## 4. Service Discovery (Eureka)

### 4.1 Eureka Server

```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

```yaml
# eureka-server application.yml
server:
  port: 8761
eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

### 4.2 Eureka Client (Service Registration)

```yaml
# product-service application.yml
spring:
  application:
    name: product-service
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
  instance:
    prefer-ip-address: true
```

### 4.3 Client-side Load Balancing

```java
// Với Spring Cloud LoadBalancer, dùng service name thay vì URL
@FeignClient(name = "product-service")  // Eureka tự resolve hostname
public interface ProductClient {
    @GetMapping("/api/products/{id}")
    ProductDTO getProduct(@PathVariable Long id);
}

// WebClient với load balancing
@Bean
@LoadBalanced
public WebClient.Builder webClientBuilder() {
    return WebClient.builder();
}

// Sử dụng: webClient.get().uri("http://product-service/api/products/1")
```

---

## 5. Centralized Configuration

### 5.1 Spring Cloud Config Server

```java
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
```

```yaml
# config-server application.yml
server:
  port: 8888
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/org/config-repo
          search-paths: '{application}'
```

### 5.2 Config Client

```yaml
# product-service bootstrap.yml
spring:
  application:
    name: product-service
  config:
    import: optional:configserver:http://localhost:8888
```

### 5.3 Environment Variables (Simpler Alternative)

```yaml
# Dùng environment variables thay Config Server (cho small systems)
spring:
  datasource:
    url: ${DB_URL:jdbc:postgresql://localhost:5432/products}
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:secret}
app:
  jwt:
    secret: ${JWT_SECRET}
  services:
    order-url: ${ORDER_SERVICE_URL:http://localhost:8082}
```

---

## 6. Distributed Tracing

### 6.1 Micrometer Tracing (Spring Boot 3.x)

```yaml
# application.yml
management:
  tracing:
    sampling:
      probability: 1.0  # Trace 100% requests (giảm ở production)
  zipkin:
    tracing:
      endpoint: http://localhost:9411/api/v2/spans

logging:
  pattern:
    level: "%5p [${spring.application.name:},%X{traceId:-},%X{spanId:-}]"
```

```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-brave</artifactId>
</dependency>
<dependency>
    <groupId>io.zipkin.reporter2</groupId>
    <artifactId>zipkin-reporter-brave</artifactId>
</dependency>
```

### 6.2 Propagation Header

```
Request → API Gateway → Product Service → Order Service
          traceId=abc    traceId=abc       traceId=abc
          spanId=1       spanId=2          spanId=3
```

Trace ID propagation tự động qua HTTP headers (`traceparent`, `b3`).

---

## 7. Event-Driven Architecture

### 7.1 Spring Cloud Stream + Kafka/RabbitMQ

```java
// Producer
@Service
public class OrderService {
    private final StreamBridge streamBridge;

    public Order createOrder(OrderRequest request) {
        Order order = processOrder(request);
        
        // Publish event
        OrderCreatedEvent event = new OrderCreatedEvent(order.getId(), order.getProductId(), order.getQuantity());
        streamBridge.send("order-created", event);
        
        return order;
    }
}

// Consumer
@Component
public class InventoryConsumer {

    @Bean
    public Consumer<OrderCreatedEvent> orderCreated() {
        return event -> {
            // Xử lý event: giảm tồn kho
            inventoryService.decreaseStock(event.getProductId(), event.getQuantity());
        };
    }
}
```

```yaml
spring:
  cloud:
    stream:
      bindings:
        order-created:
          destination: order-events
          content-type: application/json
        orderCreated-in-0:
          destination: order-events
          group: inventory-service
      kafka:
        binder:
          brokers: localhost:9092
```

### 7.2 Saga Pattern (Distributed Transactions)

```java
// Orchestration-based Saga
@Service
public class OrderSagaOrchestrator {

    public Order createOrder(OrderRequest request) {
        Order order = null;
        try {
            // Step 1: Reserve inventory
            inventoryClient.reserve(request.getProductId(), request.getQuantity());
            
            // Step 2: Process payment
            paymentClient.charge(request.getUserId(), request.getTotalAmount());
            
            // Step 3: Create order
            order = orderRepository.save(new Order(request));
            
            // Step 4: Confirm inventory
            inventoryClient.confirm(request.getProductId(), request.getQuantity());
            
            return order;
        } catch (PaymentFailedException e) {
            // Compensating transaction: release inventory
            inventoryClient.release(request.getProductId(), request.getQuantity());
            throw new OrderFailedException("Payment failed", e);
        } catch (Exception e) {
            // Compensating transactions for all previous steps
            compensate(order, request);
            throw new OrderFailedException("Order creation failed", e);
        }
    }

    private void compensate(Order order, OrderRequest request) {
        inventoryClient.release(request.getProductId(), request.getQuantity());
        if (order != null) {
            paymentClient.refund(request.getUserId(), request.getTotalAmount());
            orderRepository.delete(order);
        }
    }
}
```

---

## 8. Docker & Kubernetes Deployment

### 8.1 Docker Compose

```yaml
version: '3.8'
services:
  eureka-server:
    build: ./eureka-server
    ports:
      - "8761:8761"

  config-server:
    build: ./config-server
    ports:
      - "8888:8888"
    depends_on:
      - eureka-server

  product-service:
    build: ./product-service
    ports:
      - "8081:8081"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
      - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/
      - SPRING_DATASOURCE_URL=jdbc:postgresql://product-db:5432/products
    depends_on:
      - eureka-server
      - product-db

  product-db:
    image: postgres:15
    environment:
      POSTGRES_DB: products
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: secret
    volumes:
      - product-data:/var/lib/postgresql/data

  api-gateway:
    build: ./api-gateway
    ports:
      - "8080:8080"
    depends_on:
      - eureka-server
      - product-service
      - order-service

volumes:
  product-data:
```

### 8.2 Kubernetes Deployment

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: product-service
spec:
  replicas: 3
  selector:
    matchLabels:
      app: product-service
  template:
    metadata:
      labels:
        app: product-service
    spec:
      containers:
        - name: product-service
          image: myregistry/product-service:latest
          ports:
            - containerPort: 8081
          env:
            - name: SPRING_PROFILES_ACTIVE
              value: "k8s"
            - name: DB_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: db-credentials
                  key: password
          readinessProbe:
            httpGet:
              path: /actuator/health/readiness
              port: 8081
            initialDelaySeconds: 30
          livenessProbe:
            httpGet:
              path: /actuator/health/liveness
              port: 8081
            initialDelaySeconds: 60
---
apiVersion: v1
kind: Service
metadata:
  name: product-service
spec:
  selector:
    app: product-service
  ports:
    - port: 8081
      targetPort: 8081
  type: ClusterIP
```

---

## 9. Design Patterns cho Microservices

| Pattern | Mô tả | Khi nào dùng |
|---------|-------|-------------|
| **API Gateway** | Single entry point, routing, auth | Mọi microservice system |
| **Circuit Breaker** | Ngắt mạch khi service lỗi | Gọi external services |
| **Saga** | Distributed transactions | Multi-service business flow |
| **CQRS** | Tách read/write models | High-traffic read/write |
| **Event Sourcing** | Lưu events thay vì state | Audit trail, undo/redo |
| **Strangler Fig** | Migrate monolith → microservices | Legacy modernization |
| **Sidecar** | Cross-cutting in separate process | Service mesh (Istio) |
| **Database per Service** | Mỗi service có DB riêng | Data isolation |
| **Bulkhead** | Isolate resources per service | Prevent resource exhaustion |

---

## 10. Best Practices

| Chủ đề | Nên | Không nên |
|--------|-----|----------|
| Communication | Async (events) cho non-critical, Sync (REST/gRPC) cho critical | Sync cho mọi thứ |
| Database | DB per service, eventual consistency | Shared database |
| Circuit Breaker | Fallback cho mọi external call | Call external mà không có protection |
| Configuration | Centralized config, env variables | Hard-code config |
| Monitoring | Distributed tracing, centralized logging | Log riêng lẻ mỗi service |
| Deployment | Containerized, CI/CD | Manual deployment |
| API | Versioning, backward compatible | Breaking changes |
| Security | mTLS giữa services, JWT cho external | No auth giữa services |
| Testing | Contract tests, integration tests | Chỉ unit tests |
| Size | Small, focused services | Too many tiny services |
