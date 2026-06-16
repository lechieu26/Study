# Spring Boot - Lý Thuyết Chi Tiết

## Giới thiệu

**Spring Boot** đơn giản hóa việc phát triển ứng dụng Spring bằng cách cung cấp auto-configuration, embedded server, và convention-over-configuration. Không cần XML config phức tạp — chỉ cần thêm dependencies và Spring Boot tự cấu hình mọi thứ.

**Nguyên tắc cốt lõi:**
- **Opinionated defaults** — Cấu hình mặc định hợp lý, chỉ override khi cần
- **Auto-configuration** — Tự động cấu hình dựa trên classpath
- **Standalone** — Embedded server, không cần deploy WAR
- **Production-ready** — Health checks, metrics, monitoring tích hợp sẵn

---

## 1. @SpringBootApplication

### 1.1 Cấu trúc

```java
@SpringBootApplication  // = @Configuration + @EnableAutoConfiguration + @ComponentScan
public class StudyApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);
    }
}
```

`@SpringBootApplication` là tổ hợp của:

| Annotation | Vai trò |
|-----------|---------|
| `@Configuration` | Class này chứa bean definitions |
| `@EnableAutoConfiguration` | Bật auto-configuration dựa trên classpath |
| `@ComponentScan` | Scan beans trong package hiện tại và sub-packages |

### 1.2 SpringApplication Lifecycle

```
1. Create SpringApplication instance
2. Determine ApplicationType (SERVLET, REACTIVE, NONE)
3. Load ApplicationContextInitializers
4. Load ApplicationListeners
5. Determine main application class
6. Prepare Environment (load properties, profiles)
7. Create ApplicationContext
8. Refresh context (scan, instantiate, wire beans)
9. Call ApplicationRunner / CommandLineRunner
10. Application ready!
```

```java
@SpringBootApplication
public class MyApp {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MyApp.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setAdditionalProfiles("dev");
        app.run(args);
    }
}
```

---

## 2. Auto-Configuration

### 2.1 Cơ chế hoạt động

Spring Boot scan `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` để tìm auto-configuration classes.

```java
// Ví dụ: DataSourceAutoConfiguration
@AutoConfiguration
@ConditionalOnClass(DataSource.class)  // Chỉ khi có DataSource trong classpath
@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean  // Không tạo nếu user đã define bean
    public DataSource dataSource(DataSourceProperties properties) {
        return DataSourceBuilder.create()
            .url(properties.getUrl())
            .username(properties.getUsername())
            .password(properties.getPassword())
            .build();
    }
}
```

**Quy tắc Auto-Configuration:**
1. Chỉ active khi class cần thiết có trong classpath (`@ConditionalOnClass`)
2. User-defined beans luôn ưu tiên (`@ConditionalOnMissingBean`)
3. Có thể disable: `@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)`

### 2.2 Xem auto-configuration nào đang active

```yaml
# application.yml
debug: true  # In ra report khi startup
```

```
# Hoặc command line:
java -jar app.jar --debug
```

Output:
```
=========================
AUTO-CONFIGURATION REPORT
=========================
Positive matches: (active)
   DataSourceAutoConfiguration matched
Negative matches: (inactive)
   RedisAutoConfiguration - @ConditionalOnClass không tìm thấy RedisClient
```

### 2.3 Tắt Auto-Configuration

```java
@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class,
    SecurityAutoConfiguration.class
})
public class MyApp {}

// Hoặc trong application.yml:
spring:
  autoconfigure:
    exclude:
      - org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
```

---

## 3. Starters

### 3.1 Starter Dependencies

Starters là bộ dependency được đóng gói sẵn, tự động kéo theo các thư viện liên quan.

| Starter | Bao gồm | Dùng khi |
|---------|---------|----------|
| `spring-boot-starter-web` | Tomcat, Spring MVC, Jackson | REST API, Web app |
| `spring-boot-starter-data-jpa` | Hibernate, HikariCP, Spring Data JPA | Database access |
| `spring-boot-starter-security` | Spring Security, BCrypt | Authentication/Authorization |
| `spring-boot-starter-validation` | Hibernate Validator | Bean validation |
| `spring-boot-starter-test` | JUnit 5, Mockito, AssertJ, MockMvc | Testing |
| `spring-boot-starter-actuator` | Health, Metrics, Info | Monitoring |
| `spring-boot-starter-cache` | Spring Cache abstraction | Caching |
| `spring-boot-starter-mail` | JavaMail | Email sending |
| `spring-boot-starter-websocket` | WebSocket support | Real-time communication |
| `spring-boot-starter-thymeleaf` | Thymeleaf template engine | Server-side rendering |

### 3.2 pom.xml điển hình

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.0</version>
</parent>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 4. Configuration Properties

### 4.1 application.yml

```yaml
server:
  port: 8080
  servlet:
    context-path: /api

spring:
  application:
    name: study-app
  datasource:
    url: jdbc:postgresql://localhost:5432/studydb
    username: ${DB_USER:postgres}
    password: ${DB_PASSWORD:password}
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      connection-timeout: 30000
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
    properties:
      hibernate:
        format_sql: true
        default_batch_fetch_size: 20
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: Asia/Ho_Chi_Minh
    serialization:
      write-dates-as-timestamps: false

logging:
  level:
    root: INFO
    com.study: DEBUG
    org.hibernate.SQL: DEBUG
  file:
    name: logs/app.log
```

### 4.2 Property binding rules

| YAML key | Environment variable | System property |
|----------|---------------------|----------------|
| `server.port` | `SERVER_PORT` | `-Dserver.port=8080` |
| `spring.datasource.url` | `SPRING_DATASOURCE_URL` | `-Dspring.datasource.url=...` |
| `app.jwt-secret` | `APP_JWT_SECRET` | `-Dapp.jwt-secret=...` |

**Thứ tự ưu tiên (cao → thấp):**
1. Command line arguments (`--server.port=9090`)
2. System properties (`-Dserver.port=9090`)
3. OS environment variables (`SERVER_PORT=9090`)
4. Profile-specific properties (`application-prod.yml`)
5. Application properties (`application.yml`)
6. Default values trong code

### 4.3 Custom Properties với Validation

```java
@ConfigurationProperties(prefix = "app.mail")
@Validated
public class MailProperties {

    @NotBlank(message = "SMTP host là bắt buộc")
    private String host;

    @Min(1) @Max(65535)
    private int port = 587;

    @Email
    private String from;

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration timeout = Duration.ofSeconds(10);

    private boolean enabled = true;

    // Getters & Setters
}
```

---

## 5. Embedded Server

### 5.1 Server configuration

```yaml
server:
  port: 8080
  ssl:
    enabled: true
    key-store: classpath:keystore.p12
    key-store-password: ${SSL_PASSWORD}
    key-store-type: PKCS12
  compression:
    enabled: true
    min-response-size: 1024
    mime-types: application/json,text/html,text/css
  tomcat:
    max-threads: 200
    accept-count: 100
    connection-timeout: 20000
  error:
    include-message: always
    include-binding-errors: always
```

### 5.2 Thay đổi Embedded Server

```xml
<!-- Loại bỏ Tomcat, dùng Jetty -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jetty</artifactId>
</dependency>
```

### 5.3 Programmatic Server Config

```java
@Component
public class ServerCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
    
    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.setPort(9090);
        factory.addConnectorCustomizers(connector -> {
            connector.setProperty("maxThreads", "300");
            connector.setProperty("acceptCount", "150");
        });
    }
}
```

---

## 6. Spring Boot Actuator

### 6.1 Endpoints

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,env,loggers,mappings
      base-path: /management
  endpoint:
    health:
      show-details: when_authorized
    shutdown:
      enabled: true  # POST /management/shutdown
  info:
    env:
      enabled: true
```

**Endpoints chính:**

| Endpoint | Mô tả |
|----------|-------|
| `/actuator/health` | Trạng thái ứng dụng (UP/DOWN) |
| `/actuator/info` | Thông tin ứng dụng |
| `/actuator/metrics` | Metrics (JVM, HTTP, custom) |
| `/actuator/env` | Environment properties |
| `/actuator/loggers` | Xem/thay đổi log level runtime |
| `/actuator/mappings` | Tất cả request mappings |
| `/actuator/beans` | Tất cả beans |
| `/actuator/threaddump` | Thread dump |

### 6.2 Custom Health Indicator

```java
@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    public DatabaseHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Health health() {
        try (Connection conn = dataSource.getConnection()) {
            if (conn.isValid(3)) {
                return Health.up()
                    .withDetail("database", "PostgreSQL")
                    .withDetail("status", "Connected")
                    .build();
            }
        } catch (SQLException e) {
            return Health.down()
                .withDetail("error", e.getMessage())
                .build();
        }
        return Health.down().build();
    }
}
```

### 6.3 Custom Metrics

```java
@Service
public class DonHangService {

    private final Counter donHangCounter;
    private final Timer orderProcessingTimer;

    public DonHangService(MeterRegistry registry) {
        this.donHangCounter = Counter.builder("donhang.created")
            .description("Số đơn hàng đã tạo")
            .tag("type", "online")
            .register(registry);
        this.orderProcessingTimer = Timer.builder("donhang.processing.time")
            .description("Thời gian xử lý đơn hàng")
            .register(registry);
    }

    public DonHang taoDonHang(DonHangRequest request) {
        return orderProcessingTimer.record(() -> {
            DonHang dh = processOrder(request);
            donHangCounter.increment();
            return dh;
        });
    }
}
```

---

## 7. DevTools

### 7.1 Cấu hình

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

**Tính năng:**
- **Auto-restart**: Restart ứng dụng khi file thay đổi
- **LiveReload**: Refresh browser tự động
- **Property defaults**: Tắt template caching trong dev
- **Remote debugging**: Debug ứng dụng remote

```yaml
spring:
  devtools:
    restart:
      enabled: true
      exclude: static/**,public/**  # Không restart khi thay đổi static files
    livereload:
      enabled: true
```

---

## 8. Logging

### 8.1 Cấu hình Logging

```yaml
logging:
  level:
    root: INFO
    com.study: DEBUG
    org.springframework.web: INFO
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE  # Log SQL params
  pattern:
    console: "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
  file:
    name: logs/app.log
    max-size: 10MB
    max-history: 30
  logback:
    rollingpolicy:
      max-file-size: 10MB
      total-size-cap: 1GB
```

### 8.2 Sử dụng Logger

```java
@Service
@Slf4j  // Lombok - tạo private static final Logger log
public class UserService {

    public User findById(Long id) {
        log.debug("Tìm user với id={}", id);  // Parameterized (không string concat)
        
        try {
            User user = repo.findById(id).orElseThrow();
            log.info("Đã tìm thấy user: {}", user.getEmail());
            return user;
        } catch (Exception e) {
            log.error("Lỗi khi tìm user id={}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
```

---

## 9. Error Handling

### 9.1 Custom Error Page

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
            .forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
        
        ErrorResponse error = new ErrorResponse(400, "Validation failed", errors, LocalDateTime.now());
        return ResponseEntity.badRequest().body(error);
    }
}
```

### 9.2 Error Response DTO

```java
public record ErrorResponse(
    int status,
    String message,
    Map<String, String> errors,
    LocalDateTime timestamp
) {
    public ErrorResponse(int status, String message, LocalDateTime timestamp) {
        this(status, message, null, timestamp);
    }
}
```

---

## 10. ApplicationRunner và CommandLineRunner

```java
@Component
@Order(1)  // Thứ tự chạy
public class DatabaseInitRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        // Chạy sau khi context ready
        // args.getOptionValues("mode") → lấy command line args
        System.out.println("Database initialized");
    }
}

@Component
@Order(2)
public class CacheWarmupRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        // Warm up cache
        System.out.println("Cache warmed up");
    }
}
```

---

## 11. Packaging và Deployment

### 11.1 Build JAR

```bash
# Maven
mvn clean package -DskipTests
java -jar target/app.jar --spring.profiles.active=prod

# Gradle
./gradlew bootJar
java -jar build/libs/app.jar
```

### 11.2 Dockerfile

```dockerfile
# Multi-stage build
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve
COPY src src
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 11.3 Graceful Shutdown

```yaml
server:
  shutdown: graceful  # Đợi requests hoàn thành trước khi shutdown

spring:
  lifecycle:
    timeout-per-shutdown-phase: 30s
```

---

## 12. Best Practices

| Chủ đề | Nên | Không nên |
|--------|-----|----------|
| Properties | `@ConfigurationProperties` type-safe | Quá nhiều `@Value` |
| Profiles | Tách config theo môi trường | Hard-code values |
| Starters | Dùng starter phù hợp | Add từng dependency thủ công |
| Actuator | Enable health + metrics | Expose tất cả endpoints ở prod |
| Logging | SLF4J + parameterized | `System.out.println` |
| Error | `@ControllerAdvice` tập trung | try-catch khắp nơi |
| Packaging | Multi-stage Docker, layered JAR | Fat JAR không optimize |
| Secrets | Environment variables, Vault | Hard-code trong yml |
