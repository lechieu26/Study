# Spring Core - Quiz

## Câu 1
[TYPE: MULTIPLE_CHOICE]
IoC (Inversion of Control) trong Spring có nghĩa là gì?

A. Developer tự tạo và quản lý objects
B. Framework quản lý vòng đời và dependencies của objects
C. Objects được tạo bởi database
D. Không sử dụng constructor

**Đáp án: B**
> IoC nghĩa là framework (Spring Container) chịu trách nhiệm tạo, quản lý và inject dependencies thay vì developer tự new objects.

## Câu 2
[TYPE: MULTIPLE_CHOICE]
Đâu là cách Dependency Injection KHÔNG được Spring hỗ trợ?

A. Constructor Injection
B. Setter Injection
C. Field Injection
D. Interface Injection

**Đáp án: D**
> Spring hỗ trợ Constructor, Setter, và Field Injection. Interface Injection không phải pattern của Spring.

## Câu 3
[TYPE: MULTIPLE_CHOICE]
Constructor Injection được khuyến nghị vì:

A. Code ngắn hơn Field Injection
B. Đảm bảo immutability và dependencies luôn available khi object ready
C. Không cần annotation
D. Nhanh hơn Setter Injection

**Đáp án: B**
> Constructor Injection đảm bảo: (1) dependencies final/immutable, (2) object luôn ở trạng thái hợp lệ, (3) dễ test không cần reflection.

## Câu 4
[TYPE: SELECT_RESULT]
```java
@Component
public class OrderService {
    @Autowired
    private PaymentService paymentService;
}
```
Đây là kiểu DI nào?

A. Constructor Injection
B. Setter Injection
C. Field Injection
D. Method Injection

**Đáp án: C**
> @Autowired trực tiếp trên field là Field Injection. Không khuyến nghị vì không thể tạo immutable field và khó test.

## Câu 5
[TYPE: TRUE_FALSE]
Khi class chỉ có 1 constructor, @Autowired có thể bỏ qua (Spring tự inject).

**Đáp án: TRUE**
> Từ Spring 4.3, nếu class chỉ có 1 constructor thì Spring tự động dùng nó cho DI mà không cần @Autowired.

## Câu 6
[TYPE: MULTIPLE_CHOICE]
Bean scope mặc định trong Spring là gì?

A. Prototype
B. Singleton
C. Request
D. Session

**Đáp án: B**
> Singleton là scope mặc định. Mỗi bean chỉ có 1 instance duy nhất trong ApplicationContext.

## Câu 7
[TYPE: MULTIPLE_CHOICE]
Với scope Prototype, Spring Container:

A. Tạo 1 instance duy nhất
B. Tạo instance MỚI mỗi lần request bean
C. Tạo instance mới cho mỗi HTTP request
D. Tạo instance mới cho mỗi HTTP session

**Đáp án: B**
> Prototype scope tạo instance mới mỗi lần getBean() hoặc inject. Spring KHÔNG quản lý lifecycle sau khi tạo.

## Câu 8
[TYPE: SELECT_RESULT]
```java
@Component @Scope("prototype")
public class Cart { private List<String> items = new ArrayList<>(); }

@Service
public class ShopService {
    @Autowired private Cart cart;  // ?
}
```
Vấn đề gì xảy ra?

A. ShopService có cart riêng mỗi lần inject
B. Tất cả ShopService instances dùng chung 1 Cart (vì ShopService là singleton)
C. NullPointerException
D. Compile error

**Đáp án: B**
> Singleton bean inject Prototype bean → chỉ inject 1 lần khi tạo singleton. Mọi request tới ShopService dùng chung cart đó.

## Câu 9
[TYPE: MULTIPLE_CHOICE]
Giải pháp cho Prototype-in-Singleton problem là:

A. Dùng @Lazy
B. Dùng ObjectProvider<Cart> hoặc @Lookup
C. Dùng @Primary
D. Dùng @Qualifier

**Đáp án: B**
> ObjectProvider<Cart> cho phép lấy instance MỚI mỗi lần gọi getObject(). @Lookup cũng hoạt động tương tự.

## Câu 10
[TYPE: TRUE_FALSE]
@PostConstruct chạy SAU khi tất cả dependencies đã được inject.

**Đáp án: TRUE**
> Lifecycle: Constructor → DI → @PostConstruct → Bean ready.

## Câu 11
[TYPE: MULTIPLE_CHOICE]
Thứ tự lifecycle của Spring Bean:

A. Constructor → @PostConstruct → DI → Ready
B. DI → Constructor → @PostConstruct → Ready
C. Constructor → DI → @PostConstruct → Ready
D. @PostConstruct → Constructor → DI → Ready

**Đáp án: C**
> Đúng: Instantiation (Constructor) → Populate Properties (DI) → @PostConstruct → Bean Ready.

## Câu 12
[TYPE: MULTIPLE_CHOICE]
@PreDestroy được gọi khi nào?

A. Khi ApplicationContext đóng (shutdown)
B. Khi bean hết scope
C. Mỗi request xử lý xong
D. Cả A và B (tùy scope)

**Đáp án: D**
> Với singleton: khi context shutdown. Với request/session scope: khi request/session kết thúc. Prototype: KHÔNG được gọi (Spring không quản lý lifecycle prototype sau khi tạo).

## Câu 13
[TYPE: SELECT_RESULT]
```java
@Configuration
public class AppConfig {
    @Bean
    public DataSource dataSource() { return new HikariDataSource(); }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource()); // Gọi lại dataSource()
    }
}
```
Kết quả?

A. 2 DataSource instances được tạo
B. 1 DataSource instance (CGLIB proxy intercept method call)
C. NullPointerException
D. StackOverflowError

**Đáp án: B**
> @Configuration class được wrap bởi CGLIB proxy. Khi gọi lại dataSource(), proxy trả về bean singleton đã tạo thay vì tạo mới.

## Câu 14
[TYPE: MULTIPLE_CHOICE]
Sự khác biệt giữa @Component và @Bean:

A. Không khác nhau
B. @Component trên class, @Bean trên method trong @Configuration
C. @Bean chỉ dùng cho external libraries
D. @Component không cần component scan

**Đáp án: B**
> @Component annotate class (auto-detected by component scan). @Bean annotate method trong @Configuration (manually define beans, thường cho 3rd party).

## Câu 15
[TYPE: TRUE_FALSE]
@Service, @Repository, @Controller đều là specialization của @Component.

**Đáp án: TRUE**
> Đều meta-annotated với @Component nên đều được component scan detect. Mỗi cái thêm semantic riêng (vd: @Repository thêm exception translation).

## Câu 16
[TYPE: MULTIPLE_CHOICE]
Khi có 2 beans cùng type, Spring sẽ:

A. Tự chọn bean đầu tiên
B. Throw NoUniqueBeanDefinitionException
C. Ignore cả 2
D. Tạo proxy wrapper

**Đáp án: B**
> Ambiguity error. Giải quyết bằng: @Primary, @Qualifier, hoặc tên biến khớp bean name.

## Câu 17
[TYPE: MULTIPLE_CHOICE]
@Primary dùng để:

A. Đánh dấu bean được ưu tiên khi có nhiều beans cùng type
B. Đánh dấu bean quan trọng nhất
C. Đánh dấu bean khởi tạo đầu tiên
D. Tăng priority của bean

**Đáp án: A**
> @Primary cho biết bean nào được chọn mặc định khi inject by type gặp ambiguity.

## Câu 18
[TYPE: SELECT_RESULT]
```java
@Component
public class EmailService implements NotificationService {}

@Component
@Primary
public class SmsService implements NotificationService {}

@Service
public class AlertService {
    @Autowired private NotificationService service; // ?
}
```
AlertService nhận bean nào?

A. EmailService
B. SmsService
C. Throw exception
D. null

**Đáp án: B**
> SmsService có @Primary nên được ưu tiên khi inject NotificationService.

## Câu 19
[TYPE: MULTIPLE_CHOICE]
@Profile("dev") nghĩa là:

A. Bean chỉ active khi profile "dev" active
B. Bean luôn active
C. Bean active ở mọi profile trừ "dev"
D. Bean cần property dev=true

**Đáp án: A**
> Bean chỉ được đăng ký vào context khi profile "dev" active (spring.profiles.active=dev).

## Câu 20
[TYPE: TRUE_FALSE]
@ConditionalOnMissingBean cho phép user override auto-configured beans.

**Đáp án: TRUE**
> Nếu user đã define bean cùng type → auto-configuration bean có @ConditionalOnMissingBean sẽ KHÔNG được tạo.

## Câu 21
[TYPE: MULTIPLE_CHOICE]
BeanPostProcessor dùng để:

A. Tạo beans
B. Modify beans SAU khi instantiation và TRƯỚC/SAU initialization
C. Xóa beans
D. Validate properties

**Đáp án: B**
> BeanPostProcessor có 2 hooks: postProcessBeforeInitialization (trước @PostConstruct) và postProcessAfterInitialization (sau).

## Câu 22
[TYPE: MULTIPLE_CHOICE]
ApplicationContext so với BeanFactory:

A. Hoàn toàn giống nhau
B. ApplicationContext extends BeanFactory, thêm: events, i18n, environment, lifecycle
C. BeanFactory mạnh hơn ApplicationContext
D. BeanFactory hỗ trợ annotation, ApplicationContext không

**Đáp án: B**
> ApplicationContext = BeanFactory + AOP integration + Event publishing + MessageSource + Environment abstraction.

## Câu 23
[TYPE: SELECT_RESULT]
```java
@Value("${app.name:DefaultApp}")
private String appName;
```
Nếu property app.name KHÔNG tồn tại, giá trị appName là?

A. null
B. ""
C. "DefaultApp"
D. Throw exception

**Đáp án: C**
> Syntax `${property:default}` — giá trị sau dấu `:` là default khi property không tồn tại.

## Câu 24
[TYPE: MULTIPLE_CHOICE]
Spring Events hoạt động theo pattern nào?

A. Observer/Publisher-Subscriber
B. Factory
C. Singleton
D. Strategy

**Đáp án: A**
> Spring Events dùng Publisher-Subscriber pattern. ApplicationEventPublisher publish, @EventListener subscribe.

## Câu 25
[TYPE: TRUE_FALSE]
@TransactionalEventListener chỉ xử lý event SAU khi transaction commit thành công (mặc định).

**Đáp án: TRUE**
> Mặc định phase = AFTER_COMMIT. Có thể thay đổi: BEFORE_COMMIT, AFTER_ROLLBACK, AFTER_COMPLETION.

## Câu 26
[TYPE: MULTIPLE_CHOICE]
SpEL (Spring Expression Language) dùng syntax nào?

A. `${expression}`
B. `#{expression}`
C. `@{expression}`
D. `&{expression}`

**Đáp án: B**
> `#{}` là SpEL syntax. `${}` là property placeholder. Ví dụ: `#{T(java.lang.Math).random()}`

## Câu 27
[TYPE: MULTIPLE_CHOICE]
@Lazy annotation làm gì?

A. Bean được tạo khi lần đầu được truy cập (không phải khi context startup)
B. Bean được tạo sau 5 giây
C. Bean có priority thấp
D. Bean không bao giờ được garbage collected

**Đáp án: A**
> @Lazy delay initialization cho đến khi bean lần đầu được request/inject.

## Câu 28
[TYPE: SELECT_RESULT]
```java
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestContext { }
```
proxyMode = TARGET_CLASS nghĩa là?

A. Tạo JDK dynamic proxy
B. Tạo CGLIB proxy (subclass) để inject vào singleton beans
C. Không tạo proxy
D. Tạo interface proxy

**Đáp án: B**
> TARGET_CLASS tạo CGLIB proxy. Proxy được inject vào singleton, và delegate sang instance thật thuộc scope request hiện tại.

## Câu 29
[TYPE: TRUE_FALSE]
Spring Boot auto-configuration có thể bị disable bằng `@SpringBootApplication(exclude = ...)`.

**Đáp án: TRUE**
> Ví dụ: `@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)` tắt auto-config cho DataSource.

## Câu 30
[TYPE: MULTIPLE_CHOICE]
@DependsOn("beanA") trên beanB nghĩa là:

A. beanB extends beanA
B. beanA phải được khởi tạo TRƯỚC beanB
C. beanB inject beanA
D. beanA và beanB cùng scope

**Đáp án: B**
> @DependsOn chỉ định thứ tự khởi tạo. BeanA phải ready trước khi Spring tạo beanB.
