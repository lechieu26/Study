# Quiz - Java Spring

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Spring IoC Container quản lý gì?

- [ ] Chỉ database connections
- [x] Bean lifecycle, dependency injection, configuration
- [ ] Chỉ HTTP requests
- [ ] Chỉ security

> **Giải thích:** IoC (Inversion of Control) Container: tạo, cấu hình, quản lý beans. ApplicationContext là IoC container chính, xử lý DI, lifecycle, AOP.

## Câu 2

[TYPE: SELECT_RESULT]

Cho Spring component:

```java
@Service
public class UserService {
    private final UserRepository repo;

    @Autowired
    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public String getUser() {
        return repo.findName();
    }
}

@Repository
public class UserRepository {
    public String findName() { return "An"; }
}
```

Khi gọi `userService.getUser()`:

- [x] "An"
- [ ] NullPointerException
- [ ] Lỗi khởi tạo
- [ ] null

> **Giải thích:** Constructor injection: Spring tự động inject UserRepository vào UserService. getUser() → repo.findName() → "An".

## Câu 3

[TYPE: FILL_BLANK]

Annotation nào đánh dấu class là Spring Bean tổng quát? `@___`

- [x] Component
- [ ] Bean
- [ ] Service
- [ ] Autowired

> **Giải thích:** @Component: đánh dấu class là Spring bean. @Service, @Repository, @Controller là specializations của @Component.

## Câu 4

[TYPE: TRUE_FALSE]

Mệnh đề: "Spring Boot starter-web tự động cấu hình embedded Tomcat server."

- [x] Đúng
- [ ] Sai

> **Giải thích:** spring-boot-starter-web bao gồm embedded Tomcat. Không cần deploy WAR. Có thể thay bằng Jetty hoặc Undertow.

## Câu 5

[TYPE: SELECT_RESULT]

Cho REST controller:

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping("/{id}")
    public ResponseEntity<String> getUser(@PathVariable Long id) {
        if (id <= 0) return ResponseEntity.badRequest().body("Invalid ID");
        return ResponseEntity.ok("User " + id);
    }
}
```

GET `/api/users/5` trả về:

- [x] 200 OK, body: "User 5"
- [ ] 400 Bad Request
- [ ] 404 Not Found
- [ ] 500 Internal Server Error

> **Giải thích:** id=5 > 0 → ResponseEntity.ok("User 5"). Status 200, body "User 5". @PathVariable bind {id} từ URL.

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Sự khác biệt giữa @Controller và @RestController?

- [x] @RestController = @Controller + @ResponseBody (tự động serialize response body)
- [ ] Không có sự khác biệt
- [ ] @Controller chỉ cho REST API
- [ ] @RestController chỉ cho web pages

> **Giải thích:** @Controller: return view name (Thymeleaf, JSP). @RestController: return data trực tiếp (JSON/XML). @RestController = @Controller + @ResponseBody trên mọi method.

## Câu 7

[TYPE: SELECT_RESULT]

Cho configuration:

```java
@Configuration
public class AppConfig {
    @Bean
    public String greeting() {
        return "Hello Spring";
    }

    @Bean
    public String farewell() {
        return "Goodbye " + greeting();
    }
}
```

Khi inject bean "farewell":

- [x] "Goodbye Hello Spring"
- [ ] "Goodbye null"
- [ ] Lỗi circular dependency
- [ ] Lỗi biên dịch

> **Giải thích:** @Configuration với CGLIB proxy: gọi greeting() trong farewell() trả về cùng bean instance. farewell = "Goodbye " + "Hello Spring".

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Bean scope mặc định trong Spring là gì?

- [x] Singleton (1 instance per IoC container)
- [ ] Prototype (new instance mỗi lần inject)
- [ ] Request (1 per HTTP request)
- [ ] Session (1 per HTTP session)

> **Giải thích:** Default: Singleton. Prototype: new instance mỗi lần getBean(). Request/Session: web scope. Application: 1 per ServletContext.

## Câu 9

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
@Component
@Scope("prototype")
public class Counter {
    private int count = 0;
    public int increment() { return ++count; }
}

@Service
public class MyService {
    @Autowired private Counter counter1;
    @Autowired private Counter counter2;

    public String test() {
        counter1.increment();
        counter1.increment();
        return counter1.increment() + ":" + counter2.increment();
    }
}
```

Kết quả test():

- [ ] 3:1
- [x] 3:1
- [ ] 3:4
- [ ] 1:1

> **Giải thích:** Prototype scope: mỗi injection point nhận instance khác nhau. NHƯNG trong singleton MyService, counter1 và counter2 được inject 1 lần. counter1: 3, counter2: 1.

## Câu 10

[TYPE: TRUE_FALSE]

Mệnh đề: "Spring Boot auto-configuration dựa trên classpath dependencies và @ConditionalOn... annotations."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Auto-configuration: Spring Boot scan classpath, nếu có thư viện (vd: H2) → auto-config DataSource. @ConditionalOnClass, @ConditionalOnMissingBean kiểm soát.

## Câu 11

[TYPE: SELECT_RESULT]

Cho REST controller:

```java
@RestController
@RequestMapping("/api")
public class ProductController {
    @PostMapping("/products")
    public ResponseEntity<Product> create(@RequestBody @Valid Product product) {
        product.setId(1L);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}

public class Product {
    private Long id;
    @NotBlank private String name;
    @Min(0) private double price;
    // getters, setters
}
```

POST `/api/products` với body `{"name": "", "price": -5}`:

- [ ] 201 Created
- [x] 400 Bad Request (validation failed)
- [ ] 500 Internal Server Error
- [ ] 404 Not Found

> **Giải thích:** @Valid + @NotBlank: name="" → fail. @Min(0): price=-5 → fail. Spring trả 400 Bad Request với validation errors.

## Câu 12

[TYPE: FILL_BLANK]

Annotation nào dùng để inject dependency tự động trong Spring? `@___`

- [x] Autowired
- [ ] Inject
- [ ] Resource
- [ ] Wire

> **Giải thích:** @Autowired: inject by type. @Qualifier: chọn bean cụ thể khi có nhiều candidate. Constructor injection là best practice (không cần @Autowired từ Spring 4.3+).

## Câu 13

[TYPE: SELECT_RESULT]

Cho Spring Data JPA repository:

```java
@Entity
public class User {
    @Id @GeneratedValue private Long id;
    private String name;
    private String email;
    private int age;
}

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByAgeGreaterThan(int age);
    Optional<User> findByEmail(String email);
    List<User> findByNameContainingOrderByNameAsc(String keyword);
}
```

`findByAgeGreaterThan(25)` tương đương SQL nào?

- [x] SELECT * FROM user WHERE age > 25
- [ ] SELECT * FROM user WHERE age >= 25
- [ ] SELECT * FROM user WHERE age < 25
- [ ] SELECT * FROM user WHERE age = 25

> **Giải thích:** Spring Data query method: findBy + field + condition. GreaterThan → `>`. GreaterThanEqual → `>=`. LessThan → `<`.

## Câu 14

[TYPE: MULTIPLE_CHOICE]

@Transactional annotation trong Spring làm gì?

- [x] Quản lý transaction: auto begin, commit, rollback on exception
- [ ] Chỉ lock database table
- [ ] Chỉ dùng cho read operations
- [ ] Thay thế SQL transactions

> **Giải thích:** @Transactional: Spring tạo proxy, begin transaction trước method, commit sau, rollback nếu RuntimeException. Có thể set isolation, propagation, readOnly.

## Câu 15

[TYPE: SELECT_RESULT]

Cho đoạn code AOP:

```java
@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* com.example.service.*.*(..))")
    public void logBefore(JoinPoint jp) {
        System.out.print("Before:" + jp.getSignature().getName() + " ");
    }

    @After("execution(* com.example.service.*.*(..))")
    public void logAfter(JoinPoint jp) {
        System.out.print("After:" + jp.getSignature().getName());
    }
}

@Service
public class OrderService {
    public String process() { return "Done"; }
}
```

Khi gọi `orderService.process()`, console output:

- [x] Before:process After:process
- [ ] After:process Before:process
- [ ] Before:process
- [ ] Không in gì

> **Giải thích:** @Before: chạy trước method. @After: chạy sau method (luôn, kể cả exception). Thứ tự: Before → method → After.

## Câu 16

[TYPE: TRUE_FALSE]

Mệnh đề: "application.properties và application.yml có thể dùng thay thế nhau trong Spring Boot."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Spring Boot hỗ trợ cả .properties và .yml. .yml có hierarchical structure. Nếu cả hai tồn tại, .properties ưu tiên hơn.

## Câu 17

[TYPE: SELECT_RESULT]

Cho application.yml:

```yaml
app:
  name: MyApp
  max-users: 100
  features:
    - auth
    - logging
```

```java
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private String name;
    private int maxUsers;
    private List<String> features;
    // getters, setters
}
```

`appConfig.getMaxUsers()`:

- [x] 100
- [ ] 0
- [ ] null
- [ ] Lỗi binding

> **Giải thích:** @ConfigurationProperties bind YAML properties. max-users → maxUsers (relaxed binding). features list cũng được bind.

## Câu 18

[TYPE: MULTIPLE_CHOICE]

Spring Security filter chain thực hiện theo thứ tự nào?

- [x] SecurityContextPersistenceFilter → Authentication → Authorization → ExceptionTranslation
- [ ] Authorization → Authentication → Filter
- [ ] Ngẫu nhiên
- [ ] Chỉ có 1 filter

> **Giải thích:** Filter chain: restore SecurityContext → authenticate (UsernamePasswordAuth) → authorize (access control) → handle exceptions. Mỗi filter có trách nhiệm riêng.

## Câu 19

[TYPE: SELECT_RESULT]

Cho ExceptionHandler:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegal(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception e) {
        return ResponseEntity.status(500).body("Server Error");
    }
}
```

Khi controller throw `new IllegalArgumentException("bad input")`:

- [x] 400 Bad Request, body: "Error: bad input"
- [ ] 500 Internal Server Error
- [ ] Exception propagate lên
- [ ] 404 Not Found

> **Giải thích:** @ExceptionHandler(IllegalArgumentException.class) bắt specific exception. Trả 400 + message. @RestControllerAdvice áp dụng cho tất cả controllers.

## Câu 20

[TYPE: FILL_BLANK]

Annotation nào đánh dấu method sẽ chạy sau khi bean được khởi tạo và inject xong? `@___`

- [x] PostConstruct
- [ ] PreDestroy
- [ ] Init
- [ ] AfterInit

> **Giải thích:** @PostConstruct: chạy sau constructor + DI hoàn tất. @PreDestroy: chạy trước khi bean bị hủy. Lifecycle callbacks.

## Câu 21

[TYPE: SELECT_RESULT]

Cho Spring profiles:

```yaml
# application.yml
spring:
  profiles:
    active: dev

---
spring:
  config:
    activate:
      on-profile: dev
server:
  port: 8080

---
spring:
  config:
    activate:
      on-profile: prod
server:
  port: 80
```

App chạy trên port nào?

- [x] 8080
- [ ] 80
- [ ] 8443
- [ ] Lỗi cấu hình

> **Giải thích:** active profile = dev → dùng config dev → port 8080. Profiles cho phép config khác nhau cho các môi trường.

## Câu 22

[TYPE: MULTIPLE_CHOICE]

@Qualifier dùng khi nào?

- [x] Khi có nhiều bean cùng type, chỉ định bean cụ thể để inject
- [ ] Khi không có bean nào
- [ ] Thay thế @Autowired
- [ ] Chỉ dùng với @Configuration

> **Giải thích:** Khi có 2+ bean cùng interface (vd: EmailService, SMSService implements NotificationService), @Qualifier("emailService") chọn bean cụ thể.

## Câu 23

[TYPE: SELECT_RESULT]

Cho Spring Data JPA:

```java
@Entity
public class Order {
    @Id @GeneratedValue private Long id;
    private String status;
    private double amount;
    @ManyToOne private Customer customer;
}

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId AND o.amount > :minAmount")
    List<Order> findExpensiveOrders(@Param("customerId") Long customerId,
                                    @Param("minAmount") double minAmount);
}
```

`findExpensiveOrders(1L, 100.0)` tương đương:

- [x] SELECT * FROM order WHERE customer_id = 1 AND amount > 100.0
- [ ] SELECT * FROM order WHERE customer_id = 1 OR amount > 100.0
- [ ] SELECT * FROM order WHERE amount > 100.0
- [ ] Lỗi cú pháp JPQL

> **Giải thích:** @Query dùng JPQL. :customerId và :minAmount bind qua @Param. AND condition: cả 2 điều kiện phải thỏa.

## Câu 24

[TYPE: TRUE_FALSE]

Mệnh đề: "Spring Boot DevTools cung cấp automatic restart khi code thay đổi."

- [x] Đúng
- [ ] Sai

> **Giải thích:** spring-boot-devtools: auto restart, livereload, disable caching. Chỉ active trong development. Excluded trong production JAR.

## Câu 25

[TYPE: SELECT_RESULT]

Cho interceptor:

```java
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) {
        String token = req.getHeader("Authorization");
        if (token == null) {
            resp.setStatus(401);
            return false;
        }
        return true;
    }
}
```

Request không có header Authorization:

- [x] 401 Unauthorized, request không đến controller
- [ ] 200 OK
- [ ] 403 Forbidden
- [ ] 500 Internal Server Error

> **Giải thích:** preHandle return false → chain dừng, không gọi controller. Status 401. preHandle return true → tiếp tục xử lý.

## Câu 26

[TYPE: MULTIPLE_CHOICE]

Spring Boot Actuator cung cấp gì?

- [x] Health check, metrics, info, env endpoints cho monitoring
- [ ] Chỉ logging
- [ ] Chỉ database management
- [ ] Chỉ security

> **Giải thích:** Actuator: /actuator/health, /actuator/metrics, /actuator/info, /actuator/env. Production-ready features: monitoring, management.

## Câu 27

[TYPE: SELECT_RESULT]

Cho scheduled task:

```java
@Component
public class ScheduledTasks {
    private int count = 0;

    @Scheduled(fixedRate = 1000)
    public void task() {
        count++;
    }

    public int getCount() { return count; }
}
```

Sau 5 giây, `getCount()` trả về khoảng:

- [x] 5
- [ ] 1
- [ ] 1000
- [ ] 0

> **Giải thích:** @Scheduled(fixedRate=1000): chạy mỗi 1000ms = 1 giây. Sau 5 giây → khoảng 5 lần. Cần @EnableScheduling trên @Configuration.

## Câu 28

[TYPE: FILL_BLANK]

File cấu hình mặc định của Spring Boot là `application.___`.

- [x] properties hoặc yml
- [ ] xml
- [ ] json
- [ ] cfg

> **Giải thích:** Spring Boot: application.properties hoặc application.yml (YAML). Đặt trong src/main/resources/. Có thể override bằng environment variables, command line args.

## Câu 29

[TYPE: SELECT_RESULT]

Cho caching:

```java
@Service
public class ProductService {
    private int callCount = 0;

    @Cacheable("products")
    public String findById(Long id) {
        callCount++;
        return "Product " + id;
    }

    public int getCallCount() { return callCount; }
}
```

```java
productService.findById(1L); // lần 1
productService.findById(1L); // lần 2
productService.findById(2L); // lần 3
```

`getCallCount()`:

- [x] 2
- [ ] 3
- [ ] 1
- [ ] 0

> **Giải thích:** @Cacheable: lần 1 id=1 → call method, cache. Lần 2 id=1 → cache hit, không call. Lần 3 id=2 → call method, cache. Total calls = 2.

## Câu 30

[TYPE: MULTIPLE_CHOICE]

Thứ tự ưu tiên cấu hình Spring Boot (cao đến thấp)?

- [x] Command line args > Environment variables > application.properties
- [ ] application.properties > Command line > Environment
- [ ] Environment > application.properties > Command line
- [ ] Tất cả ngang nhau

> **Giải thích:** Command line args (--server.port=9090) > OS env vars > application-{profile}.properties > application.properties. Higher source overrides lower.

## Câu 31

[TYPE: SELECT_RESULT]

Cho event handling:

```java
public class UserCreatedEvent extends ApplicationEvent {
    private final String username;
    public UserCreatedEvent(Object source, String username) {
        super(source);
        this.username = username;
    }
    public String getUsername() { return username; }
}

@Component
public class UserEventListener {
    @EventListener
    public void handle(UserCreatedEvent event) {
        System.out.println("User created: " + event.getUsername());
    }
}

@Service
public class UserService {
    @Autowired private ApplicationEventPublisher publisher;
    public void createUser(String name) {
        publisher.publishEvent(new UserCreatedEvent(this, name));
    }
}
```

`userService.createUser("An")`:

- [x] In ra "User created: An"
- [ ] Không in gì
- [ ] NullPointerException
- [ ] Lỗi biên dịch

> **Giải thích:** ApplicationEventPublisher publish event → @EventListener bắt event → in "User created: An". Spring event-driven architecture.

## Câu 32

[TYPE: TRUE_FALSE]

Mệnh đề: "@RequestParam bind query parameter, @PathVariable bind URL path variable."

- [x] Đúng
- [ ] Sai

> **Giải thích:** @RequestParam: `/api/users?name=An` → name="An". @PathVariable: `/api/users/5` → id=5. @RequestBody: bind JSON body.

## Câu 33

[TYPE: SELECT_RESULT]

Cho JPA relationships:

```java
@Entity
public class Author {
    @Id @GeneratedValue private Long id;
    private String name;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();
}

@Entity
public class Book {
    @Id @GeneratedValue private Long id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}
```

`cascade = CascadeType.ALL` nghĩa là:

- [x] Khi persist/remove Author, tự động persist/remove Books
- [ ] Chỉ persist
- [ ] Chỉ remove
- [ ] Không có tác dụng

> **Giải thích:** CascadeType.ALL = PERSIST + MERGE + REMOVE + REFRESH + DETACH. Save author → tự động save books. Delete author → tự động delete books.

## Câu 34

[TYPE: MULTIPLE_CHOICE]

Spring Security @PreAuthorize dùng để làm gì?

- [x] Kiểm tra quyền trước khi method được gọi (method-level security)
- [ ] Kiểm tra sau khi method chạy
- [ ] Encrypt data
- [ ] Tạo user

> **Giải thích:** @PreAuthorize("hasRole('ADMIN')"): kiểm tra role trước method. @PostAuthorize: kiểm tra sau. Cần @EnableMethodSecurity.

## Câu 35

[TYPE: SELECT_RESULT]

Cho REST controller với pagination:

```java
@GetMapping("/users")
public Page<User> getUsers(Pageable pageable) {
    return userRepository.findAll(pageable);
}
```

GET `/users?page=0&size=10&sort=name,asc` trả về:

- [x] 10 users đầu tiên, sắp xếp theo name tăng dần
- [ ] Tất cả users
- [ ] 10 users cuối
- [ ] Lỗi cú pháp

> **Giải thích:** Spring Data Pageable: page=0 (trang đầu), size=10 (10 items), sort=name,asc. Auto-bind từ query params.

## Câu 36

[TYPE: FILL_BLANK]

Annotation nào đánh dấu phương thức trả về bean trong @Configuration class? `@___`

- [x] Bean
- [ ] Component
- [ ] Service
- [ ] Autowired

> **Giải thích:** @Bean trên method trong @Configuration class: Spring gọi method, đăng ký return value là bean. Method name = bean name (default).

## Câu 37

[TYPE: SELECT_RESULT]

Cho async method:

```java
@Service
public class EmailService {
    @Async
    public CompletableFuture<String> sendEmail(String to) {
        // simulate delay
        try { Thread.sleep(1000); } catch (Exception e) {}
        return CompletableFuture.completedFuture("Sent to " + to);
    }
}
```

`emailService.sendEmail("an@mail.com")` trả về ngay:

- [x] CompletableFuture (chưa complete), method chạy trên thread khác
- [ ] "Sent to an@mail.com" (đợi 1 giây)
- [ ] null
- [ ] Exception

> **Giải thích:** @Async: method chạy trên thread pool riêng. Return CompletableFuture ngay lập tức. Cần @EnableAsync.

## Câu 38

[TYPE: MULTIPLE_CHOICE]

@Transactional propagation REQUIRED (default) nghĩa là gì?

- [x] Tham gia transaction hiện tại nếu có, nếu không thì tạo mới
- [ ] Luôn tạo transaction mới
- [ ] Chạy mà không có transaction
- [ ] Suspend transaction hiện tại

> **Giải thích:** REQUIRED (default): join existing hoặc create new. REQUIRES_NEW: luôn tạo mới, suspend existing. NOT_SUPPORTED: chạy không transaction.

## Câu 39

[TYPE: SELECT_RESULT]

Cho validator:

```java
public class User {
    @NotBlank(message = "Name required")
    private String name;

    @Email(message = "Invalid email")
    private String email;

    @Min(value = 18, message = "Must be 18+")
    private int age;
}

@PostMapping("/users")
public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
    if (result.hasErrors()) {
        return ResponseEntity.badRequest().body(result.getAllErrors());
    }
    return ResponseEntity.ok(user);
}
```

Body: `{"name": "An", "email": "invalid", "age": 15}`:

- [x] 400 Bad Request với 2 errors (email, age)
- [ ] 200 OK
- [ ] 400 với 1 error
- [ ] 500 Internal Server Error

> **Giải thích:** name="An" ✓. email="invalid" (không hợp lệ) ✗. age=15 < 18 ✗. BindingResult chứa 2 errors.

## Câu 40

[TYPE: TRUE_FALSE]

Mệnh đề: "Spring Boot có thể tạo executable JAR (fat JAR) chứa embedded server."

- [x] Đúng
- [ ] Sai

> **Giải thích:** `mvn package` tạo fat JAR chứa app + dependencies + embedded Tomcat. Chạy: `java -jar app.jar`. Không cần external server.

## Câu 41

[TYPE: SELECT_RESULT]

Cho Spring MVC:

```java
@Controller
public class PageController {
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Welcome");
        return "index";
    }
}
```

GET `/home` trả về:

- [x] Render template "index.html" với message="Welcome"
- [ ] String "index"
- [ ] 404 Not Found
- [ ] JSON {"message": "Welcome"}

> **Giải thích:** @Controller (không phải @RestController): return "index" → resolve view template (index.html). Model data available trong template.

## Câu 42

[TYPE: MULTIPLE_CHOICE]

Spring Data JPA query method naming convention: `findByNameAndAge` tạo query gì?

- [x] WHERE name = ? AND age = ?
- [ ] WHERE name = ? OR age = ?
- [ ] WHERE name LIKE ? AND age > ?
- [ ] Lỗi tên method

> **Giải thích:** findBy: WHERE. And: AND. Or: OR. GreaterThan: >. LessThan: <. Like: LIKE. OrderBy: ORDER BY. Between: BETWEEN.

## Câu 43

[TYPE: SELECT_RESULT]

Cho conditional bean:

```java
@Configuration
public class DataSourceConfig {
    @Bean
    @ConditionalOnProperty(name = "app.db", havingValue = "mysql")
    public DataSource mysqlDataSource() {
        return new MysqlDataSource();
    }

    @Bean
    @ConditionalOnProperty(name = "app.db", havingValue = "h2")
    public DataSource h2DataSource() {
        return new H2DataSource();
    }
}
// application.properties: app.db=h2
```

Bean nào được tạo?

- [x] h2DataSource
- [ ] mysqlDataSource
- [ ] Cả hai
- [ ] Không có bean nào

> **Giải thích:** @ConditionalOnProperty: tạo bean khi property match. app.db=h2 → h2DataSource. mysqlDataSource không được tạo.

## Câu 44

[TYPE: FILL_BLANK]

Annotation nào chạy method trước mỗi test trong JUnit 5? `@___`

- [x] BeforeEach
- [ ] Before
- [ ] Setup
- [ ] Init

> **Giải thích:** JUnit 5: @BeforeEach (trước mỗi test), @BeforeAll (trước tất cả), @AfterEach (sau mỗi test), @AfterAll (sau tất cả).

## Câu 45

[TYPE: SELECT_RESULT]

Cho Spring Boot test:

```java
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired MockMvc mockMvc;

    @Test
    void shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/users"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(3));
    }
}
```

Test này kiểm tra gì?

- [x] GET /api/users trả 200 OK và response JSON có 3 phần tử
- [ ] POST /api/users trả 201
- [ ] GET /api/users trả 404
- [ ] Lỗi biên dịch

> **Giải thích:** MockMvc: test controller không cần start server. perform(get(...)) → expect status 200 + JSON array length = 3.

## Câu 46

[TYPE: MULTIPLE_CHOICE]

Spring AOP Pointcut expression `execution(* com.example.service.*.*(..))` match gì?

- [x] Mọi method trong mọi class thuộc package com.example.service
- [ ] Chỉ public methods
- [ ] Chỉ void methods
- [ ] Mọi method trong toàn bộ project

> **Giải thích:** `*`: any return type. `com.example.service.*`: any class in package. `.*(..)`: any method with any args. Không match subpackages.

## Câu 47

[TYPE: SELECT_RESULT]

Cho Spring Security config:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/public/**").permitAll()
            .requestMatchers("/api/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
        );
        return http.build();
    }
}
```

User không authenticated gọi GET `/api/public/info`:

- [x] 200 OK (cho phép)
- [ ] 401 Unauthorized
- [ ] 403 Forbidden
- [ ] 302 Redirect to login

> **Giải thích:** `/api/public/**` → permitAll() → ai cũng truy cập được. `/api/admin/**` cần ADMIN role. Còn lại cần authenticated.

## Câu 48

[TYPE: TRUE_FALSE]

Mệnh đề: "Spring Boot Starter là bộ dependencies được đóng gói sẵn."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Starter: spring-boot-starter-web (web + Tomcat + JSON), spring-boot-starter-data-jpa (JPA + Hibernate + Spring Data). Đơn giản hóa dependency management.

## Câu 49

[TYPE: SELECT_RESULT]

Cho ResponseEntity:

```java
@GetMapping("/download")
public ResponseEntity<byte[]> download() {
    byte[] data = "Hello".getBytes();
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file.txt")
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(data);
}
```

Response header Content-Disposition:

- [x] attachment; filename=file.txt
- [ ] inline; filename=file.txt
- [ ] Không có header
- [ ] application/octet-stream

> **Giải thích:** Content-Disposition: attachment → browser download file thay vì display. filename=file.txt. Content-Type: application/octet-stream cho binary data.

## Câu 50

[TYPE: MULTIPLE_CHOICE]

@Component, @Service, @Repository, @Controller khác nhau như thế nào?

- [x] Tất cả tạo Spring bean, nhưng mang semantic khác nhau; @Repository thêm exception translation
- [ ] Hoàn toàn giống nhau
- [ ] @Service có transaction support
- [ ] @Controller chỉ cho REST

> **Giải thích:** @Component: generic. @Service: business logic. @Repository: data access + exception translation (DataAccessException). @Controller: web layer. Semantic clarity.

## Câu 51

[TYPE: SELECT_RESULT]

Cho Spring Data custom query:

```java
@Modifying
@Transactional
@Query("UPDATE User u SET u.active = false WHERE u.lastLogin < :date")
int deactivateInactiveUsers(@Param("date") LocalDate date);
```

Method trả về gì?

- [x] Số rows bị ảnh hưởng (int)
- [ ] List<User>
- [ ] void
- [ ] Boolean

> **Giải thích:** @Modifying: cho UPDATE/DELETE query. Return int = số rows affected. @Transactional bắt buộc cho modifying queries.

## Câu 52

[TYPE: FILL_BLANK]

Để enable Spring Boot auto-configuration, đặt annotation nào trên main class? `@___`

- [x] SpringBootApplication
- [ ] EnableAutoConfig
- [ ] AutoConfiguration
- [ ] SpringApp

> **Giải thích:** @SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan. Đặt trên main class.

## Câu 53

[TYPE: SELECT_RESULT]

Cho custom exception:

```java
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String msg) { super(msg); }
}

@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) {
    return userRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
}
```

GET `/users/999` (không tồn tại):

- [x] 404 Not Found
- [ ] 500 Internal Server Error
- [ ] 200 OK với null
- [ ] 400 Bad Request

> **Giải thích:** findById(999) → empty Optional → orElseThrow → ResourceNotFoundException. @ResponseStatus(NOT_FOUND) → 404.

## Câu 54

[TYPE: MULTIPLE_CHOICE]

JPA FetchType.LAZY nghĩa là gì?

- [x] Dữ liệu liên quan chỉ load khi truy cập (lazy loading)
- [ ] Load tất cả dữ liệu ngay
- [ ] Không load dữ liệu
- [ ] Load async

> **Giải thích:** LAZY: load khi access property lần đầu. EAGER: load ngay khi query entity. @OneToMany default LAZY. @ManyToOne default EAGER.

## Câu 55

[TYPE: SELECT_RESULT]

Cho Spring Boot test slice:

```java
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired MockMvc mockMvc;
    @MockBean UserService userService;

    @Test
    void test() throws Exception {
        when(userService.findById(1L)).thenReturn(new User(1L, "An"));
        mockMvc.perform(get("/api/users/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("An"));
    }
}
```

@WebMvcTest khác @SpringBootTest:

- [x] @WebMvcTest chỉ load web layer, nhẹ hơn; @SpringBootTest load full context
- [ ] Không có sự khác biệt
- [ ] @WebMvcTest load full context
- [ ] @SpringBootTest chỉ load web layer

> **Giải thích:** @WebMvcTest: chỉ load controllers, filters, converters. Mock dependencies với @MockBean. Nhanh hơn @SpringBootTest.

## Câu 56

[TYPE: TRUE_FALSE]

Mệnh đề: "Spring Boot hỗ trợ Flyway/Liquibase cho database migration."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Spring Boot auto-detect Flyway/Liquibase trên classpath. Tự động chạy migrations khi start. Flyway: SQL scripts. Liquibase: XML/YAML/JSON.

## Câu 57

[TYPE: SELECT_RESULT]

Cho Spring Data specification:

```java
public static Specification<Product> hasPriceGreaterThan(double price) {
    return (root, query, cb) -> cb.greaterThan(root.get("price"), price);
}
public static Specification<Product> hasCategory(String category) {
    return (root, query, cb) -> cb.equal(root.get("category"), category);
}

List<Product> results = productRepo.findAll(
    hasPriceGreaterThan(100).and(hasCategory("Electronics"))
);
```

Tương đương SQL:

- [x] WHERE price > 100 AND category = 'Electronics'
- [ ] WHERE price > 100 OR category = 'Electronics'
- [ ] WHERE price >= 100 AND category = 'Electronics'
- [ ] Lỗi cú pháp

> **Giải thích:** Specification: type-safe, composable query criteria. `.and()` = AND. `.or()` = OR. Dùng CriteriaBuilder.

## Câu 58

[TYPE: MULTIPLE_CHOICE]

RestTemplate vs WebClient trong Spring?

- [x] RestTemplate: synchronous (blocking), WebClient: async (non-blocking, reactive)
- [ ] Không có sự khác biệt
- [ ] RestTemplate nhanh hơn
- [ ] WebClient chỉ cho GET

> **Giải thích:** RestTemplate: blocking I/O, deprecated từ Spring 5 (maintenance mode). WebClient: non-blocking, reactive, hỗ trợ streaming. Nên dùng WebClient.

## Câu 59

[TYPE: SELECT_RESULT]

Cho Spring cache eviction:

```java
@Service
public class ProductService {
    @Cacheable("products")
    public Product findById(Long id) { return productRepo.findById(id).orElse(null); }

    @CacheEvict("products")
    public void deleteById(Long id) { productRepo.deleteById(id); }

    @CacheEvict(value = "products", allEntries = true)
    public void clearCache() {}
}
```

Gọi `clearCache()` làm gì?

- [x] Xóa tất cả entries trong cache "products"
- [ ] Xóa 1 entry
- [ ] Không làm gì
- [ ] Xóa tất cả caches

> **Giải thích:** @CacheEvict(allEntries=true): xóa toàn bộ cache "products". Không có allEntries: chỉ evict entry matching method params.

## Câu 60

[TYPE: FILL_BLANK]

Trong Spring, `___` pattern cho phép intercept method calls trên bean để thêm cross-cutting concerns.

- [x] AOP (Aspect-Oriented Programming)
- [ ] MVC
- [ ] IoC
- [ ] DI

> **Giải thích:** AOP: logging, security, transaction → Aspects. Cross-cutting concerns tách khỏi business logic. Spring dùng proxy-based AOP.

## Câu 61

[TYPE: SELECT_RESULT]

Cho filter:

```java
@Component
@Order(1)
public class LogFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        System.out.print("Before ");
        chain.doFilter(req, resp);
        System.out.print("After ");
    }
}
```

Request flow:

- [x] Before → Controller xử lý → After
- [ ] Controller → Before → After
- [ ] After → Before → Controller
- [ ] Before → After → Controller

> **Giải thích:** Filter: trước chain.doFilter() → code "Before". chain.doFilter() → request đến controller. Sau controller → code "After".

## Câu 62

[TYPE: MULTIPLE_CHOICE]

Spring Boot Starters nào cung cấp sẵn embedded database cho testing?

- [x] spring-boot-starter-test cùng H2 dependency
- [ ] spring-boot-starter-web
- [ ] spring-boot-starter-security
- [ ] spring-boot-starter-actuator

> **Giải thích:** H2: in-memory database, thường dùng cho test. Thêm `com.h2database:h2` dependency, Spring Boot auto-config DataSource.

## Câu 63

[TYPE: SELECT_RESULT]

Cho @Value injection:

```java
@Component
public class ApiClient {
    @Value("${api.base-url:https://default.api.com}")
    private String baseUrl;

    @Value("${api.timeout:5000}")
    private int timeout;
}
// application.properties: api.base-url=https://my.api.com
// (api.timeout không được set)
```

baseUrl và timeout:

- [x] baseUrl: "https://my.api.com", timeout: 5000
- [ ] baseUrl: "https://default.api.com", timeout: 5000
- [ ] baseUrl: "https://my.api.com", timeout: 0
- [ ] Lỗi khởi tạo

> **Giải thích:** @Value("${prop:default}"): dùng default nếu property không set. api.base-url set → dùng giá trị. api.timeout không set → dùng default 5000.

## Câu 64

[TYPE: TRUE_FALSE]

Mệnh đề: "Spring Data JPA tự động implement interface methods dựa trên method name convention."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Spring Data JPA: `findByNameAndAge(String name, int age)` → tự động tạo query `WHERE name=? AND age=?`. Không cần viết implementation.

## Câu 65

[TYPE: SELECT_RESULT]

Cho transaction rollback:

```java
@Service
public class PaymentService {
    @Autowired private OrderRepository orderRepo;
    @Autowired private PaymentRepository paymentRepo;

    @Transactional
    public void processPayment(Long orderId, double amount) {
        Order order = orderRepo.findById(orderId).orElseThrow();
        order.setStatus("PAID");
        orderRepo.save(order);

        Payment payment = new Payment(orderId, amount);
        paymentRepo.save(payment);

        if (amount > 10000) {
            throw new RuntimeException("Amount exceeds limit");
        }
    }
}
```

`processPayment(1L, 15000)`:

- [x] Cả order update và payment đều bị rollback
- [ ] Order update thành công, payment rollback
- [ ] Cả hai đều commit
- [ ] Chỉ payment rollback

> **Giải thích:** @Transactional: RuntimeException → rollback toàn bộ transaction. Cả orderRepo.save() và paymentRepo.save() đều bị rollback.

## Câu 66

[TYPE: MULTIPLE_CHOICE]

Spring WebFlux dùng cho loại ứng dụng nào?

- [x] Reactive, non-blocking applications (high concurrency, streaming)
- [ ] Chỉ REST API
- [ ] Chỉ batch processing
- [ ] Thay thế hoàn toàn Spring MVC

> **Giải thích:** WebFlux: reactive stack, non-blocking I/O. Dùng Mono/Flux (Project Reactor). Phù hợp: high concurrency, streaming, microservices. Không thay thế MVC.

## Câu 67

[TYPE: SELECT_RESULT]

Cho custom converter:

```java
@Component
public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        return LocalDate.parse(source, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
```

Input "15/03/2024":

- [x] LocalDate 2024-03-15
- [ ] LocalDate 2024-15-03
- [ ] Lỗi parse
- [ ] null

> **Giải thích:** Pattern "dd/MM/yyyy": 15=day, 03=month, 2024=year. Spring auto-register Converter → tự động convert trong @RequestParam, @PathVariable.

## Câu 68

[TYPE: FILL_BLANK]

Annotation nào cho phép run test với Spring context? `@___`

- [x] SpringBootTest
- [ ] Test
- [ ] RunWith
- [ ] ContextConfiguration

> **Giải thích:** @SpringBootTest: load full application context. Dùng cho integration tests. Test slices: @WebMvcTest, @DataJpaTest, @WebFluxTest.

## Câu 69

[TYPE: SELECT_RESULT]

Cho OpenAPI/Swagger config:

```java
@RestController
@Tag(name = "Users", description = "User management API")
public class UserController {
    @Operation(summary = "Get user by ID")
    @ApiResponse(responseCode = "200", description = "Found the user")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) { ... }
}
```

Swagger UI hiển thị:

- [x] Tag "Users", operation "Get user by ID" với 2 response codes
- [ ] Không hiển thị gì
- [ ] Chỉ API path
- [ ] Lỗi cấu hình

> **Giải thích:** springdoc-openapi tự động generate Swagger UI từ annotations. @Tag: nhóm API. @Operation: mô tả. @ApiResponse: document response codes.

## Câu 70

[TYPE: MULTIPLE_CHOICE]

Spring Boot Health Check endpoint mặc định là gì?

- [x] /actuator/health
- [ ] /health
- [ ] /status
- [ ] /api/health

> **Giải thích:** Spring Boot Actuator: /actuator/health trả {"status": "UP"} hoặc {"status": "DOWN"}. Có thể add custom health indicators.

## Câu 71

[TYPE: SELECT_RESULT]

Cho message source (i18n):

```java
// messages.properties: greeting=Hello
// messages_vi.properties: greeting=Xin chào

@Autowired MessageSource messageSource;

String msg = messageSource.getMessage("greeting", null, new Locale("vi"));
```

msg:

- [x] "Xin chào"
- [ ] "Hello"
- [ ] null
- [ ] Lỗi

> **Giải thích:** MessageSource resolve messages theo locale. Locale("vi") → messages_vi.properties → "Xin chào". Fallback: messages.properties.

## Câu 72

[TYPE: TRUE_FALSE]

Mệnh đề: "Constructor injection là phương pháp DI được khuyến nghị trong Spring."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Constructor injection: immutable, testable, fail-fast (thiếu dependency → compile error). Field injection: khó test, hidden dependencies. Setter injection: optional dependencies.

## Câu 73

[TYPE: SELECT_RESULT]

Cho CORS configuration:

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("https://frontend.com")
                .allowedMethods("GET", "POST");
    }
}
```

Request từ `https://other.com` đến `/api/users`:

- [x] CORS error (origin không được phép)
- [ ] 200 OK
- [ ] 404 Not Found
- [ ] 500 Error

> **Giải thích:** CORS: chỉ allow https://frontend.com. Request từ other.com bị browser block (CORS policy). Methods chỉ GET, POST.

## Câu 74

[TYPE: MULTIPLE_CHOICE]

@Retryable annotation trong spring-retry dùng để:

- [x] Tự động retry method khi gặp exception, với configurable max attempts
- [ ] Cache kết quả
- [ ] Async execution
- [ ] Transaction management

> **Giải thích:** @Retryable: retry on exception. maxAttempts, backoff (delay), include/exclude exceptions. Cần @EnableRetry. spring-retry library.

## Câu 75

[TYPE: SELECT_RESULT]

Cho Spring Data projection:

```java
public interface UserSummary {
    String getName();
    String getEmail();
}

public interface UserRepository extends JpaRepository<User, Long> {
    List<UserSummary> findByAgeGreaterThan(int age);
}
```

Query trả về:

- [x] List chứa objects chỉ có name và email (not full User entity)
- [ ] List<User> đầy đủ
- [ ] Lỗi biên dịch
- [ ] null

> **Giải thích:** Interface-based projection: Spring Data tạo proxy, chỉ SELECT name, email. Giảm data transfer. Closed projection (chỉ methods trong interface).

## Câu 76

[TYPE: FILL_BLANK]

Annotation nào chạy 1 lần trước tất cả test methods trong JUnit 5 class? `@___`

- [x] BeforeAll
- [ ] BeforeEach
- [ ] Setup
- [ ] Init

> **Giải thích:** @BeforeAll: static method, chạy 1 lần trước tất cả tests. @BeforeEach: chạy trước mỗi test. JUnit 5 lifecycle.

## Câu 77

[TYPE: SELECT_RESULT]

Cho environment-specific beans:

```java
@Configuration
public class StorageConfig {
    @Bean
    @Profile("dev")
    public StorageService localStorage() {
        return new LocalStorageService();
    }

    @Bean
    @Profile("prod")
    public StorageService s3Storage() {
        return new S3StorageService();
    }
}
// active profile: prod
```

StorageService bean là:

- [x] S3StorageService
- [ ] LocalStorageService
- [ ] Cả hai
- [ ] Không có bean

> **Giải thích:** @Profile: bean chỉ active cho profile cụ thể. Active profile = prod → s3Storage bean. localStorage không được tạo.

## Câu 78

[TYPE: MULTIPLE_CHOICE]

Spring Boot Actuator endpoint /actuator/metrics cung cấp gì?

- [x] JVM metrics, HTTP request metrics, database metrics, custom metrics
- [ ] Chỉ CPU usage
- [ ] Chỉ memory usage
- [ ] Chỉ request count

> **Giải thích:** Metrics: jvm.memory.used, http.server.requests, jdbc.connections.active, process.cpu.usage. Tích hợp Prometheus, Graphite, InfluxDB.

## Câu 79

[TYPE: SELECT_RESULT]

Cho Spring Data auditing:

```java
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id @GeneratedValue private Long id;
    private String name;
    @CreatedDate private LocalDateTime createdAt;
    @LastModifiedDate private LocalDateTime updatedAt;
}
```

Khi update user name:

- [x] updatedAt tự động thay đổi thành thời gian hiện tại
- [ ] updatedAt giữ nguyên
- [ ] Cả createdAt và updatedAt thay đổi
- [ ] Lỗi

> **Giải thích:** @LastModifiedDate: Spring Data auto update khi entity modified. @CreatedDate: set 1 lần khi create. Cần @EnableJpaAuditing.

## Câu 80

[TYPE: TRUE_FALSE]

Mệnh đề: "Spring Boot property `server.port=0` sẽ chọn random available port."

- [x] Đúng
- [ ] Sai

> **Giải thích:** server.port=0: Spring Boot tìm port trống random. Useful cho testing. Lấy actual port: @LocalServerPort hoặc Environment.

## Câu 81

[TYPE: SELECT_RESULT]

Cho DTO mapping:

```java
record UserDTO(String name, String email) {}

@GetMapping("/users/{id}")
public UserDTO getUser(@PathVariable Long id) {
    User user = userRepo.findById(id).orElseThrow();
    return new UserDTO(user.getName(), user.getEmail());
}
```

DTO pattern giúp gì?

- [x] Tách entity khỏi API response, kiểm soát data exposed, tránh lazy loading issues
- [ ] Tăng tốc database
- [ ] Thay thế entity
- [ ] Không có lợi ích

> **Giải thích:** DTO: không expose sensitive fields (password), avoid circular references, avoid lazy loading outside transaction, clean API contract.

## Câu 82

[TYPE: MULTIPLE_CHOICE]

@EnableCaching activation trong Spring Boot cần gì?

- [x] Annotation @EnableCaching + cache manager bean (hoặc auto-config)
- [ ] Chỉ @Cacheable
- [ ] Chỉ application.properties
- [ ] Không cần gì thêm

> **Giải thích:** @EnableCaching trên @Configuration class. Spring Boot auto-config CacheManager nếu có cache provider (EhCache, Redis, Caffeine). Default: ConcurrentMapCacheManager.

## Câu 83

[TYPE: SELECT_RESULT]

Cho Spring Boot validation:

```java
@ConfigurationProperties(prefix = "mail")
@Validated
public class MailProperties {
    @NotEmpty private String host;
    @Min(1) @Max(65535) private int port;
    @Email private String from;
    // getters, setters
}
// application.yml:
// mail:
//   host: smtp.gmail.com
//   port: 587
//   from: noreply@gmail.com
```

App khởi động thành công?

- [x] Có (tất cả validation pass)
- [ ] Không (host empty)
- [ ] Không (port out of range)
- [ ] Không (email invalid)

> **Giải thích:** host="smtp.gmail.com" (not empty) ✓. port=587 (1-65535) ✓. from="noreply@gmail.com" (valid email) ✓. All pass.

## Câu 84

[TYPE: FILL_BLANK]

Spring Data JPA interface cơ bản nhất cung cấp CRUD operations là `___Repository`.

- [x] Crud
- [ ] Jpa
- [ ] Paging
- [ ] Base

> **Giải thích:** CrudRepository: save, findById, findAll, delete. JpaRepository extends PagingAndSortingRepository extends CrudRepository. JpaRepository thêm flush, batch operations.

## Câu 85

[TYPE: SELECT_RESULT]

Cho circuit breaker:

```java
@Service
public class PaymentService {
    @CircuitBreaker(name = "payment", fallbackMethod = "fallback")
    public String process(String orderId) {
        return externalPaymentApi.charge(orderId);
    }

    public String fallback(String orderId, Exception e) {
        return "Payment temporarily unavailable";
    }
}
```

Khi externalPaymentApi liên tục fail:

- [x] Circuit breaker mở, trả "Payment temporarily unavailable" mà không gọi API
- [ ] Retry vô hạn
- [ ] Throw exception
- [ ] Return null

> **Giải thích:** Circuit Breaker: closed (normal) → open (after threshold failures, short-circuit) → half-open (test). Fallback method xử lý gracefully.

## Câu 86

[TYPE: MULTIPLE_CHOICE]

Trong Spring Boot, `@ConditionalOnMissingBean` nghĩa là gì?

- [x] Tạo bean chỉ khi không có bean cùng type đã tồn tại
- [ ] Tạo bean khi có bean khác
- [ ] Xóa bean
- [ ] Luôn tạo bean

> **Giải thích:** @ConditionalOnMissingBean: auto-config tạo default bean nếu user chưa define. User define custom bean → auto-config skip. Cho phép override.

## Câu 87

[TYPE: SELECT_RESULT]

Cho ResponseEntity with headers:

```java
@GetMapping("/api/data")
public ResponseEntity<Map<String, Object>> getData() {
    Map<String, Object> body = Map.of("key", "value", "count", 42);
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-Custom-Header", "MyValue");
    return new ResponseEntity<>(body, headers, HttpStatus.OK);
}
```

Response:

- [x] Status 200, body: {"key":"value","count":42}, header X-Custom-Header: MyValue
- [ ] Status 200, body only
- [ ] Status 204 No Content
- [ ] Lỗi cú pháp

> **Giải thích:** ResponseEntity: full control over status, headers, body. Custom header X-Custom-Header set. Body serialized to JSON.

## Câu 88

[TYPE: TRUE_FALSE]

Mệnh đề: "Spring Data JPA Query by Example (QBE) cho phép tìm kiếm dynamic mà không cần viết query."

- [x] Đúng
- [ ] Sai

> **Giải thích:** QBE: tạo probe entity với values muốn match. `Example.of(probe)` → findAll(example). Dynamic query không cần @Query. Limited to equality/like matching.

## Câu 89

[TYPE: SELECT_RESULT]

Cho custom annotation:

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    int maxRequests() default 10;
    int windowSeconds() default 60;
}

@Aspect
@Component
public class RateLimitAspect {
    @Around("@annotation(rateLimit)")
    public Object limit(ProceedingJoinPoint pjp, RateLimit rateLimit) throws Throwable {
        // check rate limit using rateLimit.maxRequests() and rateLimit.windowSeconds()
        return pjp.proceed();
    }
}
```

Pattern này gọi là:

- [x] Custom annotation + AOP cho cross-cutting concern
- [ ] Decorator pattern
- [ ] Factory pattern
- [ ] Strategy pattern

> **Giải thích:** Custom annotation + @Aspect: declarative programming. AOP intercept methods với @RateLimit, apply rate limiting logic. Clean separation of concerns.

## Câu 90

[TYPE: MULTIPLE_CHOICE]

Spring Data Repository method `findTop3ByOrderByPriceDesc` trả về gì?

- [x] 3 records có price cao nhất
- [ ] Tất cả records sorted by price
- [ ] 1 record
- [ ] Lỗi tên method

> **Giải thích:** findTop3: LIMIT 3. ByOrderByPriceDesc: ORDER BY price DESC. Kết quả: top 3 giá cao nhất. findFirst = findTop1.

## Câu 91

[TYPE: SELECT_RESULT]

Cho Spring HATEOAS:

```java
@GetMapping("/users/{id}")
public EntityModel<User> getUser(@PathVariable Long id) {
    User user = userService.findById(id);
    return EntityModel.of(user,
        linkTo(methodOn(UserController.class).getUser(id)).withSelfRel(),
        linkTo(methodOn(UserController.class).getUsers()).withRel("users"));
}
```

Response bao gồm:

- [x] User data + _links (self, users) cho navigation
- [ ] Chỉ User data
- [ ] Chỉ links
- [ ] Lỗi biên dịch

> **Giải thích:** HATEOAS: Hypermedia As The Engine Of Application State. Response chứa data + links cho client navigate API. RESTful Level 3.

## Câu 92

[TYPE: FILL_BLANK]

Annotation nào dùng để test JPA repository layer mà không load full context? `@___`

- [x] DataJpaTest
- [ ] SpringBootTest
- [ ] WebMvcTest
- [ ] RepositoryTest

> **Giải thích:** @DataJpaTest: chỉ load JPA components (repositories, EntityManager, TestEntityManager). Dùng in-memory DB. Nhanh hơn @SpringBootTest.

## Câu 93

[TYPE: SELECT_RESULT]

Cho Spring Boot graceful shutdown:

```yaml
server:
  shutdown: graceful
spring:
  lifecycle:
    timeout-per-shutdown-phase: 30s
```

Khi SIGTERM:

- [x] Ngừng nhận request mới, đợi requests đang xử lý hoàn tất (tối đa 30s), rồi shutdown
- [ ] Shutdown ngay lập tức
- [ ] Restart
- [ ] Ignore signal

> **Giải thích:** Graceful shutdown: stop accepting new requests, wait for in-flight requests. timeout: max wait time. Default: immediate shutdown.

## Câu 94

[TYPE: MULTIPLE_CHOICE]

N+1 Query Problem trong JPA là gì?

- [x] 1 query lấy N entities + N queries lấy associations = N+1 queries tổng cộng
- [ ] Query trả về N+1 results
- [ ] Chỉ 1 query chậm
- [ ] Lỗi SQL syntax

> **Giải thích:** N+1: SELECT all orders (1 query). Mỗi order load customer (N queries). Fix: JOIN FETCH, @EntityGraph, batch fetching. Performance killer.

## Câu 95

[TYPE: SELECT_RESULT]

Cho Spring Batch:

```java
@Bean
public Job importJob(Step step1, Step step2) {
    return new JobBuilder("importJob", jobRepository)
        .start(step1)
        .next(step2)
        .build();
}
```

Job execution flow:

- [x] step1 → step2 (sequential)
- [ ] step1 và step2 parallel
- [ ] Chỉ step1
- [ ] Random order

> **Giải thích:** `.start(step1).next(step2)`: sequential execution. Step = Reader → Processor → Writer. Spring Batch cho batch/ETL processing.

## Câu 96

[TYPE: TRUE_FALSE]

Mệnh đề: "Spring Boot auto-configure DataSource khi thấy spring-boot-starter-data-jpa và database driver trên classpath."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Auto-config: detect JDBC driver → create DataSource, EntityManagerFactory, TransactionManager. Chỉ cần database URL trong application.properties.

## Câu 97

[TYPE: SELECT_RESULT]

Cho multipart upload:

```java
@PostMapping("/upload")
public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
    if (file.isEmpty()) return ResponseEntity.badRequest().body("No file");
    String filename = file.getOriginalFilename();
    long size = file.getSize();
    return ResponseEntity.ok("Uploaded: " + filename + " (" + size + " bytes)");
}
```

Upload file "report.pdf" (1024 bytes):

- [x] 200 OK, "Uploaded: report.pdf (1024 bytes)"
- [ ] 400 Bad Request
- [ ] 500 Error
- [ ] 413 Payload Too Large

> **Giải thích:** MultipartFile: Spring handles multipart/form-data. getOriginalFilename() → "report.pdf". getSize() → 1024. Max size configurable.

## Câu 98

[TYPE: MULTIPLE_CHOICE]

Spring Boot property binding hỗ trợ relaxed binding. Nghĩa là?

- [x] app.max-users, app.maxUsers, APP_MAX_USERS đều bind vào cùng property
- [ ] Chỉ exact match
- [ ] Chỉ camelCase
- [ ] Chỉ kebab-case

> **Giải thích:** Relaxed binding: my-property = myProperty = MY_PROPERTY. application.properties dùng kebab-case. Environment variables dùng UPPER_SNAKE_CASE.

## Câu 99

[TYPE: SELECT_RESULT]

Cho Spring Data JPA custom repository:

```java
public interface CustomUserRepo {
    List<User> findActiveUsersWithOrders();
}

public class CustomUserRepoImpl implements CustomUserRepo {
    @PersistenceContext private EntityManager em;

    public List<User> findActiveUsersWithOrders() {
        return em.createQuery(
            "SELECT DISTINCT u FROM User u JOIN FETCH u.orders WHERE u.active = true", User.class)
            .getResultList();
    }
}

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepo {}
```

JOIN FETCH giải quyết vấn đề gì?

- [x] N+1 query problem (load orders cùng lúc với users trong 1 query)
- [ ] Pagination
- [ ] Sorting
- [ ] Validation

> **Giải thích:** JOIN FETCH: eager load associations trong cùng query. Tránh N+1: thay vì 1+N queries, chỉ cần 1 query. Custom repo cho complex queries.

## Câu 100

[TYPE: FILL_BLANK]

Trong Spring Boot, annotation `@___` cho phép schedule method chạy theo cron expression.

- [x] Scheduled
- [ ] Cron
- [ ] Timer
- [ ] Job

> **Giải thích:** @Scheduled(cron = "0 0 * * * *"): chạy mỗi giờ. @Scheduled(fixedRate = 1000): mỗi 1 giây. Cần @EnableScheduling.
