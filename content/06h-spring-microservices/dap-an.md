# Spring Microservices - Đáp Án

## Bài 1: Service-to-Service Communication

### Product Service
```java
// product-service (port 8081)
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<StockDTO> checkStock(@PathVariable Long id, @RequestParam int qty) {
        boolean available = service.checkStock(id, qty);
        return ResponseEntity.ok(new StockDTO(id, service.getStock(id), available));
    }
}
```

### Order Service - Feign Client
```java
// order-service (port 8082)
@FeignClient(name = "product-service", url = "${services.product.url}",
             fallback = ProductClientFallback.class)
public interface ProductClient {
    @GetMapping("/api/products/{id}")
    ProductDTO getProduct(@PathVariable Long id);

    @GetMapping("/api/products/{id}/stock")
    StockDTO checkStock(@PathVariable Long id, @RequestParam int qty);
}

@Component
public class ProductClientFallback implements ProductClient {
    @Override
    public ProductDTO getProduct(Long id) {
        return new ProductDTO(id, "Unknown", BigDecimal.ZERO);
    }

    @Override
    public StockDTO checkStock(Long id, int qty) {
        return new StockDTO(id, 0, false);
    }
}

@Service
public class OrderService {
    private final ProductClient productClient;
    private final OrderRepository orderRepo;

    public OrderDTO createOrder(OrderRequest request) {
        StockDTO stock = productClient.checkStock(request.productId(), request.quantity());
        if (!stock.available()) {
            throw new InsufficientStockException("Not enough stock");
        }
        ProductDTO product = productClient.getProduct(request.productId());
        Order order = new Order(request.productId(), request.quantity(),
            product.price().multiply(BigDecimal.valueOf(request.quantity())));
        return toDTO(orderRepo.save(order));
    }
}
```

## Bài 2: Circuit Breaker

```yaml
# order-service application.yml
resilience4j:
  circuitbreaker:
    instances:
      productService:
        sliding-window-size: 10
        failure-rate-threshold: 50
        wait-duration-in-open-state: 30s
        permitted-number-of-calls-in-half-open-state: 3
  retry:
    instances:
      productService:
        max-attempts: 3
        wait-duration: 2s
```

```java
@Service
public class ResilientOrderService {
    private final ProductClient productClient;

    @CircuitBreaker(name = "productService", fallbackMethod = "fallback")
    @Retry(name = "productService")
    public ProductDTO getProduct(Long id) {
        return productClient.getProduct(id);
    }

    public ProductDTO fallback(Long id, Exception ex) {
        log.warn("Circuit breaker fallback for product {}: {}", id, ex.getMessage());
        return new ProductDTO(id, "Service unavailable", BigDecimal.ZERO);
    }
}
```

## Bài 3: API Gateway

```yaml
# gateway application.yml
spring:
  cloud:
    gateway:
      routes:
        - id: product-service
          uri: http://localhost:8081
          predicates:
            - Path=/api/products/**
        - id: order-service
          uri: http://localhost:8082
          predicates:
            - Path=/api/orders/**
```

```java
@Component
public class AuthGatewayFilter implements GlobalFilter, Ordered {
    private final JwtTokenProvider jwtProvider;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (path.startsWith("/api/auth/")) return chain.filter(exchange);

        String token = extractToken(exchange.getRequest());
        if (token == null || !jwtProvider.validate(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String username = jwtProvider.getUsername(token);
        ServerHttpRequest modified = exchange.getRequest().mutate()
            .header("X-User-Name", username).build();
        return chain.filter(exchange.mutate().request(modified).build());
    }

    @Override
    public int getOrder() { return -1; }
}
```

## Bài 5: Docker Compose

```yaml
version: '3.8'
services:
  product-service:
    build: ./product-service
    ports: ["8081:8081"]
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://product-db:5432/products
    depends_on: [product-db]

  order-service:
    build: ./order-service
    ports: ["8082:8082"]
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://order-db:5432/orders
      SERVICES_PRODUCT_URL: http://product-service:8081
    depends_on: [order-db, product-service]

  gateway:
    build: ./gateway
    ports: ["8080:8080"]
    depends_on: [product-service, order-service]

  product-db:
    image: postgres:15
    environment: { POSTGRES_DB: products, POSTGRES_PASSWORD: secret }

  order-db:
    image: postgres:15
    environment: { POSTGRES_DB: orders, POSTGRES_PASSWORD: secret }

  zipkin:
    image: openzipkin/zipkin
    ports: ["9411:9411"]
```
