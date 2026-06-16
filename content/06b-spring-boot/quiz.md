# Spring Boot - Quiz

## Câu 1
[TYPE: MULTIPLE_CHOICE]
@SpringBootApplication là tổ hợp của các annotations nào?

A. @Component + @Autowired + @Bean
B. @Configuration + @EnableAutoConfiguration + @ComponentScan
C. @Service + @Repository + @Controller
D. @SpringApplication + @AutoConfig + @Scan

**Đáp án: B**
> @SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan.

## Câu 2
[TYPE: TRUE_FALSE]
Spring Boot auto-configuration chỉ tạo bean khi user CHƯA define bean cùng type (@ConditionalOnMissingBean).

**Đáp án: TRUE**
> User-defined beans luôn ưu tiên hơn auto-configured beans.

## Câu 3
[TYPE: MULTIPLE_CHOICE]
Thứ tự ưu tiên property sources (cao nhất → thấp nhất):

A. application.yml → env variables → command line args
B. Command line args → env variables → application.yml
C. Env variables → command line args → application.yml
D. application.yml → command line args → env variables

**Đáp án: B**
> Command line args > System properties > Env variables > Profile-specific props > application.yml.

## Câu 4
[TYPE: SELECT_RESULT]
```yaml
server:
  port: ${PORT:8080}
```
Nếu environment variable PORT=9090, server chạy port nào?

A. 8080
B. 9090
C. Lỗi
D. Random port

**Đáp án: B**
> `${PORT:8080}` dùng env variable PORT nếu có (9090), fallback 8080 nếu không có.

## Câu 5
[TYPE: MULTIPLE_CHOICE]
Spring Boot Actuator endpoint `/actuator/health` trả về gì?

A. Danh sách beans
B. Trạng thái ứng dụng (UP/DOWN) cùng chi tiết health indicators
C. HTTP request metrics
D. Log files

**Đáp án: B**
> Health endpoint tổng hợp kết quả từ các HealthIndicator beans (DB, disk, custom...).

## Câu 6
[TYPE: MULTIPLE_CHOICE]
spring-boot-devtools cung cấp tính năng gì?

A. Auto-restart khi code thay đổi
B. LiveReload cho browser
C. Tắt template caching
D. Tất cả đáp án trên

**Đáp án: D**
> DevTools cung cấp: auto-restart, LiveReload, property defaults (tắt cache), remote debugging.

## Câu 7
[TYPE: TRUE_FALSE]
@ConfigurationProperties type-safe hơn @Value vì map toàn bộ prefix vào POJO.

**Đáp án: TRUE**
> @ConfigurationProperties bind properties vào class với validation, type-safety, IDE auto-complete.

## Câu 8
[TYPE: MULTIPLE_CHOICE]
Spring Boot mặc định dùng embedded server nào?

A. Jetty
B. Undertow
C. Tomcat
D. Netty

**Đáp án: C**
> spring-boot-starter-web mặc định include Tomcat. Có thể exclude và thay bằng Jetty/Undertow.

## Câu 9
[TYPE: MULTIPLE_CHOICE]
Để xem auto-configuration nào đang active, dùng:

A. `--verbose`
B. `--debug`
C. `--trace`
D. `--info`

**Đáp án: B**
> `java -jar app.jar --debug` hoặc `debug=true` in auto-configuration report.

## Câu 10
[TYPE: SELECT_RESULT]
```java
@Component
public class StartupRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        System.out.println("App started!");
    }
}
```
Method run() được gọi khi nào?

A. Trước context refresh
B. Sau khi ApplicationContext ready (beans loaded)
C. Mỗi HTTP request
D. Khi shutdown

**Đáp án: B**
> CommandLineRunner.run() được gọi SAU khi Spring context hoàn toàn ready.

## Câu 11
[TYPE: MULTIPLE_CHOICE]
Graceful shutdown trong Spring Boot nghĩa là:

A. Tắt ngay lập tức
B. Đợi requests đang xử lý hoàn thành rồi mới shutdown
C. Restart thay vì shutdown
D. Backup data trước khi tắt

**Đáp án: B**
> `server.shutdown=graceful` đợi in-flight requests hoàn thành (trong timeout) trước khi tắt.

## Câu 12
[TYPE: TRUE_FALSE]
ApplicationRunner nhận ApplicationArguments (parsed), CommandLineRunner nhận raw String[].

**Đáp án: TRUE**
> ApplicationRunner.run(ApplicationArguments args) vs CommandLineRunner.run(String... args).

## Câu 13
[TYPE: MULTIPLE_CHOICE]
spring.profiles.active=prod kích hoạt file config nào?

A. application.yml
B. application-prod.yml
C. Cả A và B (B override A)
D. Chỉ B

**Đáp án: C**
> application.yml luôn load. application-prod.yml load thêm và override các properties trùng.

## Câu 14
[TYPE: MULTIPLE_CHOICE]
Embedded server có thể thay đổi bằng cách:

A. Exclude Tomcat starter, thêm Jetty starter
B. Cấu hình trong application.yml
C. Dùng @Bean WebServerFactory
D. Cả A và C

**Đáp án: D**
> Cách 1: exclude spring-boot-starter-tomcat, add spring-boot-starter-jetty. Cách 2: Define WebServerFactoryCustomizer bean.

## Câu 15
[TYPE: SELECT_RESULT]
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
```
Endpoint nào KHÔNG accessible qua HTTP?

A. /actuator/health
B. /actuator/info
C. /actuator/beans
D. /actuator/metrics

**Đáp án: C**
> Chỉ health, info, metrics được expose. /actuator/beans không nằm trong include list.
