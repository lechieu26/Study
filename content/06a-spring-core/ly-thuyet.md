# Spring Core - Lý Thuyết Chi Tiết

## Giới thiệu

Spring Core là nền tảng của toàn bộ Spring Framework. Nó cung cấp **IoC Container** (Inversion of Control) và **Dependency Injection** — hai cơ chế giúp quản lý vòng đời đối tượng và kết nối các thành phần trong ứng dụng một cách linh hoạt, dễ test và dễ mở rộng.

**Tại sao cần Spring Core?**
- Quản lý đối tượng tập trung (không cần `new` thủ công)
- Loose coupling giữa các thành phần
- Dễ dàng thay đổi implementation mà không sửa code client
- Hỗ trợ testing tốt (mock dependencies)
- Lifecycle management tự động

---

## 1. IoC (Inversion of Control)

### 1.1 Khái niệm

**IoC** đảo ngược quyền kiểm soát việc tạo và quản lý đối tượng. Thay vì class tự tạo dependencies, **container** sẽ tạo và inject chúng.

**Không có IoC (tight coupling):**
```java
public class DonHangService {
    // Class tự tạo dependency → tight coupling
    private SanPhamRepository repo = new SanPhamRepositoryImpl();
    private EmailService emailService = new SmtpEmailService();
    
    // Vấn đề:
    // 1. Không thể thay đổi implementation mà không sửa code
    // 2. Không thể mock trong test
    // 3. DonHangService phải biết cách tạo dependencies
}
```

**Có IoC (loose coupling):**
```java
public class DonHangService {
    // Dependencies được inject từ bên ngoài
    private final SanPhamRepository repo;
    private final EmailService emailService;
    
    public DonHangService(SanPhamRepository repo, EmailService emailService) {
        this.repo = repo;
        this.emailService = emailService;
    }
    // DonHangService không cần biết implementation cụ thể
}
```

### 1.2 IoC Container

Spring cung cấp 2 loại IoC Container:

| Container | Interface | Đặc điểm |
|-----------|-----------|-----------|
| **BeanFactory** | `BeanFactory` | Lazy initialization, lightweight, cơ bản |
| **ApplicationContext** | `ApplicationContext` | Eager initialization, hỗ trợ AOP, events, i18n, **luôn dùng cái này** |

```java
// Tạo ApplicationContext
// 1. Annotation-based (phổ biến nhất)
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

// 2. XML-based (legacy)
ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

// 3. Spring Boot (tự động)
@SpringBootApplication
public class MyApp {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MyApp.class, args);
    }
}
```

### 1.3 ApplicationContext vs BeanFactory

| Tính năng | BeanFactory | ApplicationContext |
|-----------|-------------|-------------------|
| Bean instantiation | Lazy (khi gọi getBean) | Eager (khi container start) |
| AOP support | Không | Có |
| Event publication | Không | Có |
| Internationalization | Không | Có |
| Environment abstraction | Không | Có |
| BeanPostProcessor | Manual register | Auto-detect |

> **Quy tắc:** Luôn dùng `ApplicationContext` trừ khi memory cực kỳ hạn chế (embedded systems).

---

## 2. Dependency Injection (DI)

### 2.1 Các loại DI

| Loại | Ưu điểm | Nhược điểm | Khi nào dùng |
|------|---------|-----------|-------------|
| **Constructor Injection** | Immutable, rõ ràng, fail-fast, dễ test | Verbose nếu nhiều params | **Mặc định — luôn ưu tiên** |
| **Setter Injection** | Optional dependencies, flexible | Mutable, có thể null | Dependencies tùy chọn |
| **Field Injection** | Code ngắn gọn | Khó test, ẩn dependencies, không immutable | **Không khuyến nghị** |

### 2.2 Constructor Injection (Khuyến nghị)

```java
@Service
public class DonHangService {
    private final SanPhamRepository sanPhamRepo;
    private final ThanhToanService thanhToanService;
    private final EmailService emailService;

    // Spring tự inject khi chỉ có 1 constructor (không cần @Autowired)
    public DonHangService(SanPhamRepository sanPhamRepo,
                          ThanhToanService thanhToanService,
                          EmailService emailService) {
        this.sanPhamRepo = sanPhamRepo;
        this.thanhToanService = thanhToanService;
        this.emailService = emailService;
    }

    public DonHang taoDonHang(DonHangRequest request) {
        SanPham sp = sanPhamRepo.findById(request.getSanPhamId())
            .orElseThrow(() -> new NotFoundException("Sản phẩm không tồn tại"));
        thanhToanService.xuLyThanhToan(request.getThanhToan());
        emailService.guiXacNhan(request.getEmail());
        return luuDonHang(sp, request);
    }
}
```

**Tại sao Constructor Injection tốt nhất?**
1. **Immutable**: Field `final` → thread-safe, không bị thay đổi sau khi tạo
2. **Fail-fast**: Thiếu dependency → lỗi ngay khi container start (không phải runtime)
3. **Rõ ràng**: Nhìn constructor biết ngay class cần gì
4. **Testable**: Dễ mock trong unit test

```java
// Unit test dễ dàng với Constructor Injection
@Test
void testTaoDonHang() {
    // Mock dependencies
    SanPhamRepository mockRepo = mock(SanPhamRepository.class);
    ThanhToanService mockThanhToan = mock(ThanhToanService.class);
    EmailService mockEmail = mock(EmailService.class);
    
    // Inject mocks qua constructor
    DonHangService service = new DonHangService(mockRepo, mockThanhToan, mockEmail);
    
    when(mockRepo.findById(1L)).thenReturn(Optional.of(new SanPham()));
    // Test...
}
```

### 2.3 Setter Injection

```java
@Service
public class BaoCaoService {
    private final BaoCaoRepository repo;
    private EmailService emailService;      // Optional
    private CacheService cacheService;       // Optional

    // Required dependency qua constructor
    public BaoCaoService(BaoCaoRepository repo) {
        this.repo = repo;
    }

    // Optional dependency qua setter
    @Autowired(required = false)
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Autowired(required = false)
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    public BaoCao taoBaoCao() {
        BaoCao bc = repo.generate();
        if (emailService != null) {
            emailService.guiBaoCao(bc);
        }
        if (cacheService != null) {
            cacheService.cache(bc);
        }
        return bc;
    }
}
```

### 2.4 Field Injection (TRÁNH dùng)

```java
@Service
public class NguoiDungService {
    @Autowired
    private NguoiDungRepository repo;  // Không final, ẩn dependency
    
    @Autowired
    private PasswordEncoder encoder;   // Không thể test mà không dùng reflection
}
// Vấn đề: Không thể tạo instance mà không có Spring container
// → Không unit test được bằng `new NguoiDungService(mockRepo, mockEncoder)`
```

### 2.5 Xử lý nhiều Bean cùng type

Khi có nhiều implementation cùng interface:

```java
public interface ThongBaoService {
    void gui(String message);
}

@Service("emailThongBao")
public class EmailThongBaoService implements ThongBaoService {
    public void gui(String message) { /* Gửi email */ }
}

@Service("smsThongBao") 
public class SmsThongBaoService implements ThongBaoService {
    public void gui(String message) { /* Gửi SMS */ }
}

@Primary  // Bean mặc định khi không chỉ định
@Service("pushThongBao")
public class PushThongBaoService implements ThongBaoService {
    public void gui(String message) { /* Push notification */ }
}
```

**Cách chọn bean cụ thể:**

```java
@Service
public class DonHangService {
    private final ThongBaoService thongBaoService;

    // Cách 1: @Qualifier — chỉ định bean name
    public DonHangService(@Qualifier("emailThongBao") ThongBaoService thongBaoService) {
        this.thongBaoService = thongBaoService;
    }
}

// Cách 2: @Primary — bean mặc định (đã khai báo ở PushThongBaoService)
@Service
public class UserService {
    private final ThongBaoService thongBaoService; // → PushThongBaoService (vì @Primary)
    
    public UserService(ThongBaoService thongBaoService) {
        this.thongBaoService = thongBaoService;
    }
}

// Cách 3: Inject tất cả implementations
@Service
public class ThongBaoManager {
    private final List<ThongBaoService> allServices;     // Inject tất cả
    private final Map<String, ThongBaoService> serviceMap; // Bean name → instance
    
    public ThongBaoManager(List<ThongBaoService> allServices,
                           Map<String, ThongBaoService> serviceMap) {
        this.allServices = allServices;
        this.serviceMap = serviceMap;
    }
    
    public void guiTatCa(String msg) {
        allServices.forEach(s -> s.gui(msg));
    }
}
```

---

## 3. Bean Scopes

### 3.1 Các loại scope

| Scope | Mô tả | Lifecycle | Khi nào dùng |
|-------|--------|-----------|-------------|
| `singleton` | **Mặc định.** 1 instance duy nhất | Container start → shutdown | Service, Repository, Controller |
| `prototype` | Instance mới mỗi lần getBean/inject | Container chỉ tạo, KHÔNG quản lý destroy | Stateful objects |
| `request` | 1 instance / HTTP request | Request start → end | Request-scoped data |
| `session` | 1 instance / HTTP session | Session start → end | User session data |
| `application` | 1 instance / ServletContext | Deploy → undeploy | App-wide shared state |
| `websocket` | 1 instance / WebSocket session | Connect → disconnect | WebSocket state |

### 3.2 Singleton Scope (Mặc định)

```java
@Service  // Mặc định là singleton
public class SanPhamService {
    private final SanPhamRepository repo;
    
    // Chỉ 1 instance được tạo cho toàn bộ container
    // CẢNH BÁO: KHÔNG được có mutable state!
    // private List<SanPham> cache = new ArrayList<>(); // ← NGUY HIỂM (race condition)
    
    public SanPhamService(SanPhamRepository repo) {
        this.repo = repo;
    }
}
```

### 3.3 Prototype Scope

```java
@Component
@Scope("prototype")
public class GioHang {
    private List<SanPham> items = new ArrayList<>();
    
    public void them(SanPham sp) { items.add(sp); }
    public List<SanPham> getItems() { return Collections.unmodifiableList(items); }
    public BigDecimal tongTien() {
        return items.stream().map(SanPham::getGia).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
```

### 3.4 Vấn đề Inject Prototype vào Singleton

```java
@Service  // Singleton
public class MuaHangService {
    // BUG: GioHang là prototype nhưng chỉ inject 1 lần vào singleton!
    // Mọi user sẽ dùng chung 1 giỏ hàng
    @Autowired
    private GioHang gioHang;  // ← CHỈ TẠO 1 LẦN!
}
```

**Giải pháp 1: ObjectProvider (Khuyến nghị)**
```java
@Service
public class MuaHangService {
    private final ObjectProvider<GioHang> gioHangProvider;
    
    public MuaHangService(ObjectProvider<GioHang> gioHangProvider) {
        this.gioHangProvider = gioHangProvider;
    }
    
    public GioHang taoGioHangMoi() {
        return gioHangProvider.getObject(); // Mỗi lần gọi → instance mới
    }
}
```

**Giải pháp 2: @Lookup**
```java
@Service
public abstract class MuaHangService {
    
    @Lookup
    public abstract GioHang taoGioHangMoi(); // Spring override method này
    
    public void muaHang(DonHangRequest request) {
        GioHang gh = taoGioHangMoi(); // Instance mới mỗi lần
        // ...
    }
}
```

**Giải pháp 3: Scoped Proxy**
```java
@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GioHang {
    // Spring tạo proxy, mỗi lần gọi method → delegate tới instance mới
}
```

---

## 4. Bean Lifecycle

### 4.1 Vòng đời đầy đủ

```
1. Instantiation (new bean)
   ↓
2. Populate Properties (inject dependencies)
   ↓
3. BeanNameAware.setBeanName()
   ↓
4. BeanFactoryAware.setBeanFactory()
   ↓
5. ApplicationContextAware.setApplicationContext()
   ↓
6. BeanPostProcessor.postProcessBeforeInitialization()
   ↓
7. @PostConstruct
   ↓
8. InitializingBean.afterPropertiesSet()
   ↓
9. Custom init-method
   ↓
10. BeanPostProcessor.postProcessAfterInitialization()
   ↓
=== BEAN READY (sử dụng) ===
   ↓
11. @PreDestroy
   ↓
12. DisposableBean.destroy()
   ↓
13. Custom destroy-method
   ↓
14. GC
```

### 4.2 Lifecycle Callbacks

```java
@Component
public class CacheManager {

    private Map<String, Object> cache;

    @PostConstruct  // Chạy SAU khi inject xong — dùng để init resources
    public void khoiTao() {
        cache = new ConcurrentHashMap<>();
        loadDataFromDB();
        System.out.println("Cache đã khởi tạo với " + cache.size() + " entries");
    }

    @PreDestroy  // Chạy TRƯỚC khi container shutdown — dùng để cleanup
    public void donDep() {
        flushToDatabase();
        cache.clear();
        System.out.println("Cache đã dọn dẹp");
    }

    private void loadDataFromDB() { /* ... */ }
    private void flushToDatabase() { /* ... */ }
}
```

### 4.3 BeanPostProcessor

```java
@Component
public class LoggingBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean.getClass().isAnnotationPresent(Service.class)) {
            System.out.println("Before init: " + beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        // Có thể wrap bean bằng proxy ở đây (AOP làm điều này)
        return bean;
    }
}
```

### 4.4 Aware Interfaces

```java
@Component
public class AppInfoBean implements ApplicationContextAware, BeanNameAware {
    
    private ApplicationContext context;
    private String beanName;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.context = context; // Truy cập container
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name; // Biết tên bean của mình
    }
    
    public int demBeans() {
        return context.getBeanDefinitionCount();
    }
}
```

---

## 5. Configuration

### 5.1 @Configuration và @Bean

```java
@Configuration  // Đánh dấu class chứa bean definitions
public class AppConfig {

    @Bean  // Method trả về object được quản lý bởi container
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Bean
    @ConditionalOnProperty(name = "cache.enabled", havingValue = "true")
    public CacheManager cacheManager() {
        return new CaffeineCacheManager("products", "users");
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public ConnectionPool connectionPool() {
        return new HikariConnectionPool();
    }
}
```

### 5.2 @Configuration với CGLIB Proxy

```java
@Configuration  // CGLIB proxy: đảm bảo singleton semantics
public class DataConfig {

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        // Gọi dataSource() ở đây KHÔNG tạo instance mới
        // CGLIB proxy intercept → trả về cùng bean singleton
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public TransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource()); // Cùng instance
    }
}
```

> **Lưu ý:** Nếu dùng `@Component` thay `@Configuration`, mỗi lần gọi `dataSource()` sẽ tạo instance MỚI (không có CGLIB proxy). Đây gọi là "lite mode" — TRÁNH dùng khi beans phụ thuộc nhau.

### 5.3 @ComponentScan

```java
@Configuration
@ComponentScan(
    basePackages = "com.study",
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ANNOTATION,
        classes = Controller.class  // Không scan controller ở đây
    )
)
public class ServiceConfig {}

// @SpringBootApplication đã bao gồm @ComponentScan cho package hiện tại
```

### 5.4 @Import và Modular Configuration

```java
@Configuration
@Import({SecurityConfig.class, CacheConfig.class, SchedulingConfig.class})
public class AppConfig {
    // Import các config khác vào
}
```

---

## 6. Profiles

### 6.1 Định nghĩa Profile

```java
@Configuration
@Profile("dev")  // Chỉ active khi profile = dev
public class DevConfig {
    
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("schema.sql")
            .build();
    }
}

@Configuration
@Profile("prod")
public class ProdConfig {
    
    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:postgresql://prod-db:5432/app");
        ds.setMaximumPoolSize(20);
        return ds;
    }
}

@Configuration
@Profile("!prod")  // Active khi KHÔNG phải prod
public class NonProdConfig {
    @Bean
    public DebugService debugService() {
        return new DebugService();
    }
}
```

### 6.2 Kích hoạt Profile

```yaml
# application.yml
spring:
  profiles:
    active: dev

# Hoặc qua command line:
# java -jar app.jar --spring.profiles.active=prod

# Hoặc qua environment variable:
# SPRING_PROFILES_ACTIVE=prod

# Hoặc qua system property:
# -Dspring.profiles.active=prod
```

### 6.3 Profile-specific Properties

```
src/main/resources/
├── application.yml           # Cấu hình chung
├── application-dev.yml       # Override cho dev
├── application-prod.yml      # Override cho prod
└── application-test.yml      # Override cho test
```

```yaml
# application.yml (chung)
server:
  port: 8080
app:
  name: StudyApp

# application-dev.yml
spring:
  datasource:
    url: jdbc:h2:mem:devdb
  jpa:
    show-sql: true

# application-prod.yml
spring:
  datasource:
    url: jdbc:postgresql://prod-host:5432/proddb
    hikari:
      maximum-pool-size: 20
  jpa:
    show-sql: false
```

---

## 7. Properties và @Value

### 7.1 Inject giá trị từ properties

```java
@Service
public class AppService {

    @Value("${app.name}")  // Inject từ application.yml
    private String appName;

    @Value("${app.version:1.0.0}")  // Giá trị mặc định nếu không có
    private String version;

    @Value("${app.features.enabled:true}")
    private boolean featuresEnabled;

    @Value("#{${app.limits}}")  // SpEL: parse Map
    private Map<String, Integer> limits;

    @Value("${app.admin.emails}")  // Comma-separated → List
    private List<String> adminEmails;
}
```

### 7.2 @ConfigurationProperties (Type-safe)

```java
@ConfigurationProperties(prefix = "app")
@Validated
public class AppProperties {

    @NotBlank
    private String name;

    @Min(1) @Max(65535)
    private int port = 8080;

    @Valid
    private Security security = new Security();

    @Valid
    private Database database = new Database();

    // Getters & Setters

    public static class Security {
        private String jwtSecret;
        private long tokenExpiration = 86400000;
        private List<String> allowedOrigins = new ArrayList<>();
        // Getters & Setters
    }

    public static class Database {
        private int maxPoolSize = 10;
        private Duration connectionTimeout = Duration.ofSeconds(30);
        // Getters & Setters
    }
}
```

```yaml
# application.yml
app:
  name: StudyApp
  port: 8080
  security:
    jwt-secret: ${JWT_SECRET}
    token-expiration: 86400000
    allowed-origins:
      - http://localhost:3000
      - https://app.study.com
  database:
    max-pool-size: 10
    connection-timeout: 30s
```

```java
@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppConfig {
    // AppProperties sẽ được tạo và inject tự động
}

// Sử dụng
@Service
public class AuthService {
    private final AppProperties props;
    
    public AuthService(AppProperties props) {
        this.props = props;
    }
    
    public String generateToken() {
        // Type-safe access
        String secret = props.getSecurity().getJwtSecret();
        long exp = props.getSecurity().getTokenExpiration();
        // ...
    }
}
```

---

## 8. Spring Events

### 8.1 Event System

```java
// 1. Định nghĩa Event
public class DonHangDaXacNhanEvent {
    private final Long donHangId;
    private final String email;
    private final BigDecimal tongTien;

    public DonHangDaXacNhanEvent(Long donHangId, String email, BigDecimal tongTien) {
        this.donHangId = donHangId;
        this.email = email;
        this.tongTien = tongTien;
    }
    // Getters...
}

// 2. Publish Event
@Service
public class DonHangService {
    private final ApplicationEventPublisher eventPublisher;

    public DonHangService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public DonHang xacNhanDonHang(Long id) {
        DonHang dh = repo.findById(id).orElseThrow();
        dh.setTrangThai(TrangThai.DA_XAC_NHAN);
        repo.save(dh);
        
        // Publish event — các listener sẽ xử lý phần còn lại
        eventPublisher.publishEvent(
            new DonHangDaXacNhanEvent(dh.getId(), dh.getEmail(), dh.getTongTien()));
        return dh;
    }
}

// 3. Listen Event
@Component
public class EmailEventListener {

    @EventListener
    public void guiEmailXacNhan(DonHangDaXacNhanEvent event) {
        // Gửi email xác nhận
        System.out.println("Gửi email tới: " + event.getEmail());
    }
}

@Component
public class InventoryEventListener {

    @EventListener
    @Async  // Xử lý bất đồng bộ
    public void giamTonKho(DonHangDaXacNhanEvent event) {
        // Giảm tồn kho
    }
}

// 4. Conditional Listener
@Component
public class VIPListener {

    @EventListener(condition = "#event.tongTien > 10000000")
    public void xuLyDonVIP(DonHangDaXacNhanEvent event) {
        // Chỉ xử lý đơn hàng VIP (> 10 triệu)
    }
}
```

### 8.2 @TransactionalEventListener

```java
@Component
public class AfterCommitListener {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sauKhiCommit(DonHangDaXacNhanEvent event) {
        // Chỉ chạy sau khi transaction commit thành công
        // → Tránh gửi email khi transaction bị rollback
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void sauKhiRollback(DonHangDaXacNhanEvent event) {
        // Xử lý khi transaction bị rollback
    }
}
```

---

## 9. Stereotype Annotations

| Annotation | Layer | Mô tả | Tính năng đặc biệt |
|-----------|-------|-------|-------------------|
| `@Component` | Chung | Bean tổng quát | Không có thêm |
| `@Service` | Business | Service layer | Semantic marker |
| `@Repository` | Data | Data access layer | **Exception translation** (SQL → Spring exceptions) |
| `@Controller` | Web | Web controller (trả View) | Request mapping |
| `@RestController` | Web | REST API | `@Controller` + `@ResponseBody` |
| `@Configuration` | Config | Bean definitions | CGLIB proxy cho singleton semantics |

```java
// @Repository tự động translate SQL exceptions
@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public User findByEmail(String email) {
        try {
            // JDBC query...
        } catch (SQLException e) {
            // Spring tự động convert → DataAccessException
            // Không cần catch SQLException ở service layer
        }
    }
}
```

---

## 10. Conditional Beans

### 10.1 @Conditional annotations

```java
@Configuration
public class ConditionalConfig {

    @Bean
    @ConditionalOnProperty(name = "cache.type", havingValue = "redis")
    public CacheManager redisCacheManager() {
        return new RedisCacheManager();
    }

    @Bean
    @ConditionalOnProperty(name = "cache.type", havingValue = "caffeine", matchIfMissing = true)
    public CacheManager caffeineCacheManager() {
        return new CaffeineCacheManager();
    }

    @Bean
    @ConditionalOnMissingBean(EmailService.class)
    public EmailService defaultEmailService() {
        return new ConsoleEmailService(); // Fallback
    }

    @Bean
    @ConditionalOnClass(name = "com.redis.RedisClient")
    public RedisTemplate<String, Object> redisTemplate() {
        return new RedisTemplate<>();
    }

    @Bean
    @ConditionalOnBean(DataSource.class)
    public JdbcTemplate jdbcTemplate(DataSource ds) {
        return new JdbcTemplate(ds);
    }
}
```

| Annotation | Điều kiện |
|-----------|-----------|
| `@ConditionalOnProperty` | Property có giá trị cụ thể |
| `@ConditionalOnMissingBean` | Bean chưa tồn tại |
| `@ConditionalOnBean` | Bean đã tồn tại |
| `@ConditionalOnClass` | Class có trong classpath |
| `@ConditionalOnMissingClass` | Class KHÔNG có trong classpath |
| `@ConditionalOnExpression` | SpEL expression = true |

---

## 11. SpEL (Spring Expression Language)

```java
@Component
public class SpELDemo {

    @Value("#{systemProperties['java.version']}")
    private String javaVersion;

    @Value("#{T(java.lang.Math).random() * 100}")
    private double randomNumber;

    @Value("#{${app.feature.enabled} ? 'ON' : 'OFF'}")
    private String featureStatus;

    @Value("#{@myService.getConfig()}")  // Gọi method của bean khác
    private String config;

    @Value("#{${app.list}.?[#this > 10]}")  // Filter list
    private List<Integer> filteredList;
}
```

---

## 12. Best Practices

| Chủ đề | Nên | Không nên |
|--------|-----|----------|
| DI | Constructor injection, final fields | Field injection |
| Bean Scope | Singleton cho stateless services | Singleton cho stateful objects |
| Configuration | `@ConfigurationProperties` type-safe | `@Value` cho complex config |
| Profiles | Tách config theo profile | Hard-code environment values |
| Events | Decouple qua events | Direct method calls cho cross-cutting |
| Naming | Descriptive bean names | Generic names (service1, service2) |
| Lifecycle | `@PostConstruct` cho init | Constructor logic nặng |
| Conditional | `@ConditionalOn...` cho optional beans | if-else trong code |

---

## 13. Phỏng vấn thường gặp

1. **IoC là gì? DI là gì?** — IoC đảo ngược quyền kiểm soát (container quản lý object), DI là cách hiện thực IoC (inject dependencies).

2. **Singleton vs Prototype?** — Singleton: 1 instance, stateless services. Prototype: instance mới mỗi lần request, stateful objects.

3. **@Component vs @Bean?** — @Component: class-level, auto-detected qua scanning. @Bean: method-level trong @Configuration, dùng khi không sửa được source code.

4. **Constructor vs Field Injection?** — Constructor: immutable, testable, fail-fast. Field: ẩn dependencies, khó test, mutable.

5. **BeanFactory vs ApplicationContext?** — ApplicationContext extends BeanFactory, thêm AOP, events, i18n. Luôn dùng ApplicationContext.

6. **Circular dependency xử lý thế nào?** — Redesign code (tách responsibility). Nếu không tránh được: dùng setter injection hoặc @Lazy.
