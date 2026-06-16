# Spring AOP - Đáp Án

## Bài 1: Logging Aspect

```java
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.study.service.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String method = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        long start = System.currentTimeMillis();

        log.info("→ {}.{}({})", className, method, Arrays.toString(args));

        try {
            Object result = joinPoint.proceed();
            long elapsed = System.currentTimeMillis() - start;
            log.info("← {}.{} → {} [{}ms]", className, method, result, elapsed);
            return result;
        } catch (Exception e) {
            long elapsed = System.currentTimeMillis() - start;
            log.error("✗ {}.{} threw {} [{}ms]: {}", className, method,
                e.getClass().getSimpleName(), elapsed, e.getMessage());
            throw e;
        }
    }
}
```

## Bài 2: Custom Annotation - @MeasureTime

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MeasureTime {
    long warnThresholdMs() default 1000;
}

@Aspect
@Component
@Slf4j
public class MeasureTimeAspect {
    private final MeterRegistry registry;

    public MeasureTimeAspect(MeterRegistry registry) { this.registry = registry; }

    @Around("@annotation(measureTime)")
    public Object measure(ProceedingJoinPoint pjp, MeasureTime measureTime) throws Throwable {
        String methodName = pjp.getSignature().toShortString();
        long start = System.nanoTime();

        try {
            Object result = pjp.proceed();
            long elapsedMs = (System.nanoTime() - start) / 1_000_000;

            Timer.builder("method.execution.time")
                .tag("method", methodName)
                .register(registry)
                .record(elapsedMs, TimeUnit.MILLISECONDS);

            if (elapsedMs > measureTime.warnThresholdMs()) {
                log.warn("⚠ {} took {}ms (threshold: {}ms)", methodName, elapsedMs, measureTime.warnThresholdMs());
            } else {
                log.info("⏱ {} took {}ms", methodName, elapsedMs);
            }
            return result;
        } catch (Exception e) {
            long elapsedMs = (System.nanoTime() - start) / 1_000_000;
            log.error("⏱ {} failed after {}ms", methodName, elapsedMs);
            throw e;
        }
    }
}
```

## Bài 3: Rate Limiting Aspect

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    int requests() default 10;
    int periodSeconds() default 60;
}

@Aspect
@Component
public class RateLimitAspect {
    private final Map<String, List<Long>> requestLog = new ConcurrentHashMap<>();

    @Around("@annotation(rateLimit)")
    public Object checkRate(ProceedingJoinPoint pjp, RateLimit rateLimit) throws Throwable {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        String key = user + ":" + pjp.getSignature().toShortString();
        long now = System.currentTimeMillis();
        long windowStart = now - (rateLimit.periodSeconds() * 1000L);

        List<Long> timestamps = requestLog.computeIfAbsent(key, k -> new CopyOnWriteArrayList<>());
        timestamps.removeIf(t -> t < windowStart);

        if (timestamps.size() >= rateLimit.requests()) {
            throw new RateLimitExceededException("Rate limit exceeded: " + rateLimit.requests()
                + " requests per " + rateLimit.periodSeconds() + "s");
        }

        timestamps.add(now);
        return pjp.proceed();
    }
}
```

## Bài 4: Retry Aspect

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Retry {
    int maxAttempts() default 3;
    long delay() default 1000;
    int backoffMultiplier() default 2;
    Class<? extends Exception>[] retryOn() default {RuntimeException.class};
}

@Aspect
@Component
@Slf4j
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object retryMethod(ProceedingJoinPoint pjp, Retry retry) throws Throwable {
        int attempts = 0;
        long delay = retry.delay();
        Exception lastEx = null;

        while (attempts < retry.maxAttempts()) {
            try {
                return pjp.proceed();
            } catch (Exception e) {
                boolean shouldRetry = Arrays.stream(retry.retryOn()).anyMatch(c -> c.isInstance(e));
                if (!shouldRetry) throw e;

                lastEx = e;
                attempts++;
                if (attempts >= retry.maxAttempts()) break;

                log.warn("Retry {}/{} for {} after {}ms: {}",
                    attempts, retry.maxAttempts(), pjp.getSignature().toShortString(), delay, e.getMessage());
                Thread.sleep(delay);
                delay *= retry.backoffMultiplier();
            }
        }
        throw lastEx;
    }
}
```
