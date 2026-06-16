# Spring Boot - Đáp Án

## Bài 1: Khởi Tạo Project Spring Boot

### Cách 1: Cấu hình đầy đủ

```yaml
# application.yml
server:
  port: 9090
  servlet:
    context-path: /api

spring:
  application:
    name: study-boot-app

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
  endpoint:
    health:
      show-details: always
  info:
    env:
      enabled: true

info:
  app:
    name: Study Boot App
    version: 1.0.0
    author: Student
```

```java
@RestController
public class InfoController {
    @Value("${info.app.name}") private String name;
    @Value("${info.app.version}") private String version;

    @GetMapping("/info")
    public Map<String, String> getInfo() {
        return Map.of("name", name, "version", version, "timestamp", Instant.now().toString());
    }
}

@Component
public class DiskSpaceHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        File disk = new File("/");
        long freeSpace = disk.getFreeSpace();
        long totalSpace = disk.getTotalSpace();
        double freePercent = (double) freeSpace / totalSpace * 100;

        if (freePercent < 10) {
            return Health.down()
                .withDetail("freeSpace", freeSpace / (1024*1024) + "MB")
                .withDetail("freePercent", String.format("%.1f%%", freePercent))
                .build();
        }
        return Health.up()
            .withDetail("freeSpace", freeSpace / (1024*1024) + "MB")
            .withDetail("freePercent", String.format("%.1f%%", freePercent))
            .build();
    }
}
```

## Bài 2: Configuration Properties

### Cách 1: Type-safe Configuration

```java
@ConfigurationProperties(prefix = "app")
@Validated
public class AppProperties {
    @NotBlank private String name;
    private String version = "1.0.0";
    @Valid private Contact contact = new Contact();
    private Map<String, Boolean> features = new HashMap<>();

    public static class Contact {
        @Email private String email;
        private String phone;
        // Getters & Setters
    }
    // Getters & Setters
}

@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppConfig {}

@RestController
public class ConfigController {
    private final AppProperties props;

    public ConfigController(AppProperties props) { this.props = props; }

    @GetMapping("/config")
    public AppProperties getConfig() { return props; }
}
```
