# Spring AOP - Lý Thuyết Chi Tiết

## Giới thiệu

**AOP (Aspect-Oriented Programming)** tách các **cross-cutting concerns** — logic cắt ngang nhiều module (logging, security, caching, transaction) — ra khỏi business logic chính. Thay vì copy-paste code logging vào mọi method, viết 1 lần trong Aspect và áp dụng cho tất cả.

**Không có AOP:**
```java
public class OrderService {
    public Order createOrder(OrderRequest request) {
        long start = System.currentTimeMillis();         // Logging
        log.info("→ createOrder called");                // Logging
        checkPermission();                                // Security
        Order order = processOrder(request);              // Business logic
        log.info("← createOrder: {}ms", elapsed);        // Logging
        return order;
    }
}
// Mỗi method đều copy-paste logging, security, transaction...
```

**Có AOP:**
```java
public class OrderService {
    public Order createOrder(OrderRequest request) {
        return processOrder(request);  // Chỉ business logic
    }
}
// Logging, security, transaction được xử lý bởi Aspects riêng biệt
```

---

## 1. Thuật ngữ AOP

| Thuật ngữ | Mô tả | Ví dụ |
|----------|-------|-------|
| **Aspect** | Module chứa cross-cutting logic | `LoggingAspect`, `SecurityAspect` |
| **Join Point** | Điểm trong code có thể áp dụng aspect | Method execution, field access |
| **Pointcut** | Expression chọn các join points | `execution(* com.study.service.*.*(..))` |
| **Advice** | Code thực thi tại join point | `@Before`, `@After`, `@Around` |
| **Target** | Object được apply aspect | `OrderService` instance |
| **Proxy** | Object bọc quanh target (thêm aspect logic) | CGLIB/JDK Dynamic Proxy |
| **Weaving** | Quá trình kết hợp aspect vào target | Compile-time, runtime (Spring = runtime) |

---

## 2. Advice Types

### 2.1 @Before

```java
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // Chạy TRƯỚC method target
    @Before("execution(* com.study.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String method = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        
        log.info("→ {}.{}({})", className, method, 
            Arrays.stream(args).map(Object::toString).collect(Collectors.joining(", ")));
    }
}
```

### 2.2 @AfterReturning

```java
// Chạy SAU method target return thành công
@AfterReturning(pointcut = "execution(* com.study.service.*.*(..))", returning = "result")
public void logAfterReturn(JoinPoint joinPoint, Object result) {
    log.info("← {}.{} → {}", 
        joinPoint.getTarget().getClass().getSimpleName(),
        joinPoint.getSignature().getName(),
        result);
}
```

### 2.3 @AfterThrowing

```java
// Chạy khi method target throw exception
@AfterThrowing(pointcut = "execution(* com.study.service.*.*(..))", throwing = "ex")
public void logException(JoinPoint joinPoint, Exception ex) {
    log.error("✗ {}.{} threw {}: {}",
        joinPoint.getTarget().getClass().getSimpleName(),
        joinPoint.getSignature().getName(),
        ex.getClass().getSimpleName(),
        ex.getMessage());
}
```

### 2.4 @After (Finally)

```java
// Chạy SAU method (cả success và exception) — tương tự finally
@After("execution(* com.study.service.*.*(..))")
public void logAfter(JoinPoint joinPoint) {
    log.debug("⤓ {}.{} completed", 
        joinPoint.getTarget().getClass().getSimpleName(),
        joinPoint.getSignature().getName());
}
```

### 2.5 @Around (Mạnh nhất)

```java
// Bọc QUANH method — kiểm soát hoàn toàn
@Around("execution(* com.study.service.*.*(..))")
public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    String className = joinPoint.getTarget().getClass().getSimpleName();
    String method = joinPoint.getSignature().getName();
    long start = System.currentTimeMillis();

    try {
        log.info("→ {}.{}", className, method);
        Object result = joinPoint.proceed();  // GỌI METHOD GỐC
        long elapsed = System.currentTimeMillis() - start;
        log.info("← {}.{} → {}ms", className, method, elapsed);
        return result;
    } catch (Exception ex) {
        long elapsed = System.currentTimeMillis() - start;
        log.error("✗ {}.{} failed after {}ms: {}", className, method, elapsed, ex.getMessage());
        throw ex;  // Re-throw để caller xử lý
    }
}
```

**Thứ tự thực thi Advice:**
```
@Around (before proceed)
    @Before
        Target Method
    @AfterReturning / @AfterThrowing
    @After
@Around (after proceed)
```

---

## 3. Pointcut Expressions

### 3.1 execution()

```java
// Cú pháp: execution(modifiers? return-type declaring-type.method-name(params) throws?)

// Mọi method trong service package
@Pointcut("execution(* com.study.service.*.*(..))")
public void serviceLayer() {}

// Mọi public method
@Pointcut("execution(public * *(..))")
public void publicMethods() {}

// Method trả về void
@Pointcut("execution(void com.study.service.*.*(..))")
public void voidMethods() {}

// Method bắt đầu bằng "save" hoặc "update"
@Pointcut("execution(* com.study.service.*.save*(..)) || execution(* com.study.service.*.update*(..))")
public void writeOperations() {}

// Method có đúng 1 param kiểu Long
@Pointcut("execution(* com.study.service.*.*(Long))")
public void methodsWithLongParam() {}

// Method có ít nhất 1 param (bất kỳ type)
@Pointcut("execution(* com.study.service.*.*(*, ..))")
public void methodsWithAtLeastOneParam() {}
```

### 3.2 within()

```java
// Mọi method trong 1 class cụ thể
@Pointcut("within(com.study.service.OrderService)")
public void inOrderService() {}

// Mọi method trong package (bao gồm sub-packages)
@Pointcut("within(com.study.service..*)")
public void inServicePackage() {}
```

### 3.3 @annotation()

```java
// Method có annotation cụ thể
@Pointcut("@annotation(com.study.annotation.Loggable)")
public void loggableMethods() {}

@Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
public void transactionalMethods() {}
```

### 3.4 args()

```java
// Method có param đầu tiên kiểu Long
@Pointcut("args(Long, ..)")
public void firstArgLong() {}

// Kết hợp: bind argument
@Before("execution(* com.study.service.*.*(..)) && args(id, ..)")
public void logWithId(JoinPoint joinPoint, Long id) {
    log.info("Processing id={}", id);
}
```

### 3.5 Combine Pointcuts

```java
@Aspect
@Component
public class SecurityAspect {

    @Pointcut("execution(* com.study.service.*.*(..))")
    private void serviceLayer() {}

    @Pointcut("execution(* com.study.controller.*.*(..))")
    private void controllerLayer() {}

    @Pointcut("@annotation(com.study.annotation.RequiresAuth)")
    private void requiresAuth() {}

    // Kết hợp: service OR controller có @RequiresAuth
    @Before("(serviceLayer() || controllerLayer()) && requiresAuth()")
    public void checkAuth(JoinPoint joinPoint) {
        // Kiểm tra authentication
    }
}
```

---

## 4. Custom Annotations với AOP

### 4.1 @LogExecutionTime

```java
// 1. Định nghĩa annotation
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecutionTime {
    String value() default "";  // Mô tả tùy chọn
}

// 2. Aspect xử lý
@Aspect
@Component
@Slf4j
public class ExecutionTimeAspect {

    @Around("@annotation(logTime)")
    public Object measureTime(ProceedingJoinPoint joinPoint, LogExecutionTime logTime) throws Throwable {
        long start = System.nanoTime();
        
        try {
            Object result = joinPoint.proceed();
            long elapsed = (System.nanoTime() - start) / 1_000_000;
            
            String desc = logTime.value().isEmpty() 
                ? joinPoint.getSignature().toShortString() 
                : logTime.value();
            log.info("⏱ {} took {}ms", desc, elapsed);
            
            return result;
        } catch (Exception e) {
            long elapsed = (System.nanoTime() - start) / 1_000_000;
            log.error("⏱ {} failed after {}ms", joinPoint.getSignature().toShortString(), elapsed);
            throw e;
        }
    }
}

// 3. Sử dụng
@Service
public class ReportService {
    
    @LogExecutionTime("Generate monthly report")
    public Report generateMonthlyReport(int month) {
        // Tự động log thời gian
    }
}
```

### 4.2 @RateLimit

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    int requests() default 100;
    int period() default 60;  // seconds
}

@Aspect
@Component
public class RateLimitAspect {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Around("@annotation(rateLimit)")
    public Object checkRateLimit(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        String key = joinPoint.getSignature().toString();
        
        Bucket bucket = buckets.computeIfAbsent(key, k -> 
            Bucket.builder()
                .addLimit(Bandwidth.classic(rateLimit.requests(), 
                    Refill.intervally(rateLimit.requests(), Duration.ofSeconds(rateLimit.period()))))
                .build()
        );

        if (!bucket.tryConsume(1)) {
            throw new RateLimitExceededException("Rate limit exceeded for: " + key);
        }

        return joinPoint.proceed();
    }
}

// Sử dụng
@RateLimit(requests = 10, period = 60)
public void sendEmail(String to, String content) { }
```

### 4.3 @Cacheable (Custom)

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheResult {
    String key() default "";
    int ttlSeconds() default 300;  // 5 minutes
}

@Aspect
@Component
public class CacheAspect {

    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();

    @Around("@annotation(cacheResult)")
    public Object cacheMethod(ProceedingJoinPoint joinPoint, CacheResult cacheResult) throws Throwable {
        String cacheKey = buildKey(joinPoint, cacheResult);
        
        CacheEntry entry = cache.get(cacheKey);
        if (entry != null && !entry.isExpired()) {
            return entry.getValue();
        }

        Object result = joinPoint.proceed();
        cache.put(cacheKey, new CacheEntry(result, cacheResult.ttlSeconds()));
        return result;
    }

    private String buildKey(ProceedingJoinPoint joinPoint, CacheResult cacheResult) {
        if (!cacheResult.key().isEmpty()) return cacheResult.key();
        return joinPoint.getSignature().toString() + Arrays.toString(joinPoint.getArgs());
    }
}
```

### 4.4 @Retry

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Retry {
    int maxAttempts() default 3;
    long delayMs() default 1000;
    Class<? extends Exception>[] retryOn() default {RuntimeException.class};
}

@Aspect
@Component
@Slf4j
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object retryMethod(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        int attempts = 0;
        Exception lastException = null;

        while (attempts < retry.maxAttempts()) {
            try {
                return joinPoint.proceed();
            } catch (Exception e) {
                lastException = e;
                boolean shouldRetry = Arrays.stream(retry.retryOn())
                    .anyMatch(clazz -> clazz.isInstance(e));
                
                if (!shouldRetry || attempts >= retry.maxAttempts() - 1) throw e;
                
                attempts++;
                log.warn("Retry {}/{} for {}: {}", attempts, retry.maxAttempts(),
                    joinPoint.getSignature().toShortString(), e.getMessage());
                Thread.sleep(retry.delayMs() * attempts);  // Exponential backoff
            }
        }
        throw lastException;
    }
}

// Sử dụng
@Retry(maxAttempts = 3, delayMs = 2000, retryOn = {TimeoutException.class, IOException.class})
public String callExternalApi(String url) {
    return restTemplate.getForObject(url, String.class);
}
```

---

## 5. Spring AOP Proxy Mechanism

### 5.1 JDK Dynamic Proxy vs CGLIB

| Feature | JDK Dynamic Proxy | CGLIB |
|---------|-------------------|-------|
| Cần interface | Có (bắt buộc) | Không |
| Cơ chế | Implements interface | Subclass target class |
| Performance | Nhanh hơn cho interface | Nhanh hơn cho class |
| Spring Boot default | Không | **Có (mặc định)** |
| Final class/method | Hỗ trợ | **Không** (không subclass được) |

### 5.2 Self-Invocation Problem

```java
@Service
public class OrderService {

    @Transactional
    public void processOrder(Order order) {
        // Gọi method khác TRONG CÙNG CLASS → KHÔNG qua proxy → AOP KHÔNG hoạt động!
        this.sendNotification(order);  // ← @Async sẽ KHÔNG work!
    }

    @Async
    public void sendNotification(Order order) {
        // Không chạy async vì self-invocation
    }
}
```

**Giải pháp:**

```java
// Cách 1: Inject chính mình (qua proxy)
@Service
public class OrderService {
    @Lazy
    private final OrderService self;  // Proxy instance

    public OrderService(@Lazy OrderService self) {
        this.self = self;
    }

    @Transactional
    public void processOrder(Order order) {
        self.sendNotification(order);  // Gọi qua proxy → AOP hoạt động
    }

    @Async
    public void sendNotification(Order order) { }
}

// Cách 2: Tách thành service riêng (KHUYẾN NGHỊ)
@Service
public class NotificationService {
    @Async
    public void sendNotification(Order order) { }
}
```

---

## 6. Built-in Spring AOP Features

### 6.1 @Transactional (AOP-based)

```java
// @Transactional hoạt động qua AOP proxy
@Service
public class BankService {
    
    @Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.READ_COMMITTED,
        timeout = 30,
        rollbackFor = BusinessException.class
    )
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        // AOP proxy wrap method này trong transaction
    }
}
```

### 6.2 @Cacheable (Spring Cache)

```java
@Service
public class ProductService {

    @Cacheable(value = "products", key = "#id")
    public Product findById(Long id) {
        // Kết quả được cache, lần gọi sau không hit DB
        return repo.findById(id).orElseThrow();
    }

    @CachePut(value = "products", key = "#product.id")
    public Product update(Product product) {
        // Update cache sau khi save
        return repo.save(product);
    }

    @CacheEvict(value = "products", key = "#id")
    public void delete(Long id) {
        // Xóa khỏi cache
        repo.deleteById(id);
    }

    @CacheEvict(value = "products", allEntries = true)
    public void clearCache() {
        // Xóa toàn bộ cache
    }
}
```

### 6.3 @Async

```java
@Configuration
@EnableAsync
public class AsyncConfig {
    
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.setThreadNamePrefix("async-");
        return executor;
    }
}

@Service
public class EmailService {

    @Async
    public CompletableFuture<Void> sendEmail(String to, String subject, String body) {
        // Chạy trong thread riêng
        mailSender.send(createMessage(to, subject, body));
        return CompletableFuture.completedFuture(null);
    }
}
```

### 6.4 @Scheduled

```java
@Configuration
@EnableScheduling
public class SchedulerConfig {}

@Component
@Slf4j
public class ScheduledTasks {

    @Scheduled(fixedRate = 60000)  // Mỗi 60 giây
    public void cleanExpiredTokens() {
        tokenService.removeExpired();
    }

    @Scheduled(cron = "0 0 2 * * ?")  // 2:00 AM mỗi ngày
    public void generateDailyReport() {
        reportService.generateDaily();
    }

    @Scheduled(fixedDelay = 30000, initialDelay = 5000)  // Delay 30s sau khi xong, bắt đầu sau 5s
    public void syncData() {
        syncService.sync();
    }
}
```

---

## 7. Aspect Ordering

```java
@Aspect
@Component
@Order(1)  // Thực thi đầu tiên
public class SecurityAspect {
    @Before("execution(* com.study.service.*.*(..))")
    public void checkSecurity() { }
}

@Aspect
@Component
@Order(2)  // Thực thi sau SecurityAspect
public class LoggingAspect {
    @Before("execution(* com.study.service.*.*(..))")
    public void logMethod() { }
}

// Execution order: Security @Before → Logging @Before → Target Method
//                  Logging @After → Security @After
```

---

## 8. Real-world Use Cases

| Use Case | Advice Type | Mô tả |
|----------|-------------|-------|
| Performance monitoring | `@Around` | Đo thời gian thực thi |
| Logging | `@Before` + `@AfterReturning` | Log input/output |
| Security | `@Before` | Kiểm tra quyền trước khi execute |
| Caching | `@Around` | Return cached value nếu có |
| Retry | `@Around` | Retry khi gặp exception |
| Rate limiting | `@Around` | Giới hạn số lần gọi |
| Auditing | `@AfterReturning` | Ghi lại actions |
| Exception translation | `@AfterThrowing` | Convert exception types |
| Transaction | `@Around` | Begin/commit/rollback transaction |
| Validation | `@Before` | Validate input trước khi xử lý |

---

## 9. Best Practices

| Chủ đề | Nên | Không nên |
|--------|-----|----------|
| Scope | Pointcut cụ thể, nhắm đúng target | Pointcut quá rộng (`execution(* *.*(..))`) |
| Advice | `@Around` khi cần control flow, `@Before` cho simple | `@Around` cho mọi thứ |
| Performance | Lightweight logic trong aspects | Heavy computation trong aspect |
| Testing | Test aspects riêng biệt | Skip testing aspects |
| Self-invocation | Tách service, inject self proxy | Ignore self-invocation issue |
| Order | Explicit `@Order` khi có nhiều aspects | Rely on default ordering |
| Annotation | Custom annotation cho reusability | Inline pointcut everywhere |
| Exception | Re-throw exceptions, log details | Swallow exceptions silently |
