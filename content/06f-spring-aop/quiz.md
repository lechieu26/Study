# Spring AOP - Quiz

## Câu 1
[TYPE: MULTIPLE_CHOICE]
AOP viết tắt của:

A. Application Oriented Programming
B. Aspect Oriented Programming
C. Abstract Object Pattern
D. Advanced Operation Protocol

**Đáp án: B**
> AOP = Aspect Oriented Programming. Tách biệt cross-cutting concerns (logging, security, transactions).

## Câu 2
[TYPE: MULTIPLE_CHOICE]
Cross-cutting concern là:

A. Concerns chỉ ảnh hưởng 1 class
B. Concerns cắt ngang nhiều modules (logging, security, transaction, caching)
C. Bug trong code
D. Database concerns

**Đáp án: B**
> Cross-cutting = logic ảnh hưởng nhiều layers/modules. AOP tách nó ra khỏi business logic.

## Câu 3
[TYPE: TRUE_FALSE]
@Around advice có thể thay đổi arguments, return value, và quyết định có gọi target method hay không.

**Đáp án: TRUE**
> @Around wraps target method. proceed() gọi target. Có thể skip proceed(), modify args, modify result.

## Câu 4
[TYPE: MULTIPLE_CHOICE]
Thứ tự advice types khi không có exception:

A. @Before → Target → @AfterReturning → @After
B. @Before → @After → Target → @AfterReturning
C. Target → @Before → @After
D. @After → @Before → Target

**Đáp án: A**
> @Before → Target method → @AfterReturning (nếu thành công) → @After (luôn chạy, như finally).

## Câu 5
[TYPE: SELECT_RESULT]
```java
@Around("execution(* com.study.service.*.*(..))")
public Object log(ProceedingJoinPoint pjp) throws Throwable {
    System.out.println("Before");
    Object result = pjp.proceed();
    System.out.println("After");
    return result;
}
```
Nếu target method throw exception, "After" có được in?

A. Có
B. Không (exception propagate trước println)
C. Tùy exception type
D. In "After" rồi mới throw

**Đáp án: B**
> proceed() throws → code sau proceed() không chạy. Cần try-finally để đảm bảo "After" luôn in.

## Câu 6
[TYPE: MULTIPLE_CHOICE]
Pointcut expression `execution(* com.study.service.*.*(..))` match:

A. Tất cả methods trong com.study.service package (không sub-packages)
B. Tất cả methods trong com.study.service và sub-packages
C. Chỉ public methods
D. Chỉ void methods

**Đáp án: A**
> `*.*(..)` = any class, any method, any params. Chỉ 1 level (không sub-packages). Dùng `..` cho sub-packages.

## Câu 7
[TYPE: TRUE_FALSE]
`execution(* com.study.service..*.*(..))` (2 dấu chấm) match cả sub-packages.

**Đáp án: TRUE**
> `..` trong package = 0 hoặc nhiều sub-packages. Match: service.UserService, service.admin.AdminService.

## Câu 8
[TYPE: MULTIPLE_CHOICE]
Spring AOP mặc định dùng proxy nào cho class KHÔNG implement interface?

A. JDK Dynamic Proxy
B. CGLIB (subclass proxy)
C. AspectJ weaving
D. Javassist

**Đáp án: B**
> Không interface → CGLIB tạo subclass. Có interface → JDK Dynamic Proxy (Spring Boot 3 mặc định CGLIB cho tất cả).

## Câu 9
[TYPE: MULTIPLE_CHOICE]
Self-invocation problem trong AOP:

A. Proxy thất bại
B. Gọi method trong cùng class KHÔNG đi qua proxy → advice không apply
C. Stack overflow
D. Deadlock

**Đáp án: B**
> `this.methodB()` bypass proxy. Chỉ external calls đi qua proxy → advice apply. Fix: inject self hoặc separate class.

## Câu 10
[TYPE: SELECT_RESULT]
```java
@Aspect @Component
public class LogAspect {
    @Before("@annotation(com.study.annotation.Loggable)")
    public void log(JoinPoint jp) { ... }
}
```
Advice này apply cho methods nào?

A. Tất cả methods
B. Chỉ methods có annotation @Loggable
C. Tất cả methods trong LogAspect
D. Methods có return type Loggable

**Đáp án: B**
> `@annotation(...)` pointcut match methods annotated với specified annotation.

## Câu 11
[TYPE: MULTIPLE_CHOICE]
@Order(1) trên Aspect nghĩa là:

A. Chạy đầu tiên (số nhỏ = priority cao)
B. Chạy sau cùng
C. Chỉ chạy 1 lần
D. Priority thấp nhất

**Đáp án: A**
> @Order(1) higher priority than @Order(2). Nhỏ hơn = chạy before trước, after sau.

## Câu 12
[TYPE: TRUE_FALSE]
@Transactional, @Cacheable, @Async trong Spring đều implement bằng AOP proxy.

**Đáp án: TRUE**
> Spring dùng AOP proxy intercept method calls để apply transaction/cache/async behavior.

## Câu 13
[TYPE: MULTIPLE_CHOICE]
@AfterThrowing advice:

A. Prevent exception propagation
B. Log exception nhưng exception vẫn propagate bình thường
C. Retry method
D. Convert checked to unchecked exception

**Đáp án: B**
> @AfterThrowing chỉ observe exception, KHÔNG catch/prevent nó. Exception vẫn propagate to caller.

## Câu 14
[TYPE: MULTIPLE_CHOICE]
Pointcut composition `@Pointcut("serviceLayer() && !excludeGetters()")` nghĩa là:

A. Match serviceLayer OR excludeGetters
B. Match serviceLayer AND NOT excludeGetters
C. Match chỉ excludeGetters
D. Match tất cả

**Đáp án: B**
> `&&` = AND, `||` = OR, `!` = NOT. Match methods in service layer trừ getters.

## Câu 15
[TYPE: SELECT_RESULT]
```java
@Around("@annotation(retry)")
public Object doRetry(ProceedingJoinPoint pjp, Retry retry) throws Throwable {
    int max = retry.maxAttempts();
    // ...
}
```
Parameter `Retry retry` lấy giá trị từ đâu?

A. Spring DI inject
B. Annotation trên target method (binding annotation attributes)
C. Method argument
D. Configuration file

**Đáp án: B**
> `@annotation(retry)` binds annotation instance to parameter. Có thể access annotation attributes.
