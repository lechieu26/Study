# Quiz - Java Spring

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Annotation nào đánh dấu một class là Spring Bean được quản lý bởi IoC Container?

- [ ] @Bean
- [x] @Component
- [ ] @Autowired
- [ ] @Configuration

> **Giải thích:** `@Component` đánh dấu class là Spring Bean. `@Service`, `@Repository`, `@Controller` đều là specialization của `@Component`.

## Câu 2

[TYPE: SELECT_RESULT]

Đoạn code sau sử dụng kiểu Dependency Injection nào?

```java
@Service
public class UserService {
    private final UserRepository repo;
    
    public UserService(UserRepository repo) {
        this.repo = repo;
    }
}
```

- [ ] Field Injection
- [x] Constructor Injection
- [ ] Setter Injection
- [ ] Interface Injection

> **Giải thích:** Constructor Injection truyền dependency qua constructor. Đây là cách được khuyến nghị vì đảm bảo immutability và dễ test.

## Câu 3

[TYPE: FILL_BLANK]

Annotation `___` trong Spring Boot được sử dụng để đánh dấu phương thức xử lý HTTP GET request.

- [ ] @PostMapping
- [x] @GetMapping
- [ ] @RequestMapping
- [ ] @ResponseBody

> **Giải thích:** `@GetMapping` là shortcut cho `@RequestMapping(method = RequestMethod.GET)`. Dùng để map HTTP GET request đến method handler.

## Câu 4

[TYPE: TRUE_FALSE]

Mệnh đề: "Mặc định, Spring Bean có scope là Prototype — mỗi lần inject sẽ tạo một instance mới."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Mặc định scope của Spring Bean là Singleton — chỉ có một instance duy nhất trong ApplicationContext. Prototype scope mới tạo instance mới mỗi lần.

## Câu 5

[TYPE: MULTIPLE_CHOICE]

Spring AOP (Aspect-Oriented Programming) chủ yếu được sử dụng cho mục đích gì?

- [ ] Quản lý database
- [x] Cross-cutting concerns (logging, security, transaction)
- [ ] Tạo REST API
- [ ] Quản lý dependency

> **Giải thích:** AOP tách biệt cross-cutting concerns (logging, security, transaction management) khỏi business logic chính.

## Câu 6

[TYPE: SELECT_RESULT]

Annotation nào dùng để validate request body trong Spring Boot?

```java
@PostMapping("/users")
public User createUser(_____ @RequestBody UserDTO dto) {
    return userService.create(dto);
}
```

- [x] @Valid
- [ ] @Validated
- [ ] @NotNull
- [ ] @RequestParam

> **Giải thích:** `@Valid` trigger Bean Validation trên request body. Kết hợp với các annotation như `@NotNull`, `@Size`, `@Email` trên DTO fields.

## Câu 7

[TYPE: FILL_BLANK]

File `___` là file cấu hình chính của ứng dụng Spring Boot.

- [ ] web.xml
- [x] application.properties
- [ ] pom.xml
- [ ] beans.xml

> **Giải thích:** `application.properties` (hoặc `application.yml`) là file cấu hình chính, chứa các thiết lập như server port, database URL, logging level.

## Câu 8

[TYPE: TRUE_FALSE]

Mệnh đề: "Spring Boot có thể tự động cấu hình DataSource nếu có dependency JDBC/JPA và thông tin kết nối trong application.properties."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Spring Boot Auto-Configuration tự động cấu hình beans dựa trên classpath và properties. Đây là tính năng cốt lõi của Spring Boot.

## Câu 9

[TYPE: MULTIPLE_CHOICE]

Annotation nào đảm bảo một phương thức chạy trong transaction?

- [ ] @Async
- [x] @Transactional
- [ ] @Cacheable
- [ ] @Secured

> **Giải thích:** `@Transactional` quản lý transaction tự động: commit nếu thành công, rollback nếu có exception (RuntimeException mặc định).

## Câu 10

[TYPE: SELECT_RESULT]

Trong Spring Security, filter chain xử lý request theo thứ tự nào?

```
Request → ? → Controller → Response
```

- [ ] Controller → Filter → Interceptor
- [x] Filter → Interceptor → Controller
- [ ] Interceptor → Filter → Controller
- [ ] Controller → Interceptor → Filter

> **Giải thích:** Request đi qua Filter chain trước, sau đó qua HandlerInterceptor, cuối cùng đến Controller. Spring Security sử dụng Filter chain để xác thực và phân quyền.
