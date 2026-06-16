# Spring Security - Lý Thuyết Chi Tiết

## Giới thiệu

**Spring Security** là framework bảo mật toàn diện cho ứng dụng Java. Nó cung cấp authentication (xác thực), authorization (phân quyền), và protection against common attacks (CSRF, XSS, session fixation).

**Kiến trúc Security Filter Chain:**
```
HTTP Request
    ↓
SecurityFilterChain (ordered filters)
    ↓ CorsFilter
    ↓ CsrfFilter
    ↓ AuthenticationFilter (UsernamePassword / JWT / OAuth2)
    ↓ AuthorizationFilter
    ↓
Controller (nếu pass tất cả filters)
```

---

## 1. Security Configuration

### 1.1 SecurityFilterChain (Spring Security 6.x)

```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity  // Bật @PreAuthorize, @Secured
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter,
                         CustomUserDetailsService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Tắt CSRF cho REST API (stateless)
            .csrf(csrf -> csrf.disable())
            
            // Stateless session (không lưu session server-side)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // CORS configuration
            .cors(cors -> cors.configurationSource(corsConfigSource()))
            
            // Authorization rules
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/actuator/health").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                
                // Role-based access
                .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                
                // Mọi request khác phải authenticated
                .anyRequest().authenticated()
            )
            
            // Exception handling
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(unauthorizedHandler())  // 401
                .accessDeniedHandler(accessDeniedHandler())       // 403
            )
            
            // Thêm JWT filter trước UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);  // Strength 12 (2^12 rounds)
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    private CorsConfigurationSource corsConfigSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000", "https://myapp.com"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }
}
```

### 1.2 Request Matching Order

```java
// Quy tắc: CỤ THỂ trước → CHUNG sau
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/api/admin/users/{id}/roles").hasRole("SUPER_ADMIN")  // Cụ thể nhất
    .requestMatchers("/api/admin/**").hasRole("ADMIN")                      // Rộng hơn
    .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("USER", "ADMIN") // Rộng hơn nữa
    .anyRequest().authenticated()                                           // Catch-all
)
```

---

## 2. Authentication

### 2.1 UserDetailsService

```java
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final NguoiDungRepository nguoiDungRepo;

    public CustomUserDetailsService(NguoiDungRepository nguoiDungRepo) {
        this.nguoiDungRepo = nguoiDungRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NguoiDung nd = nguoiDungRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(
                "Không tìm thấy user: " + username));

        return User.builder()
            .username(nd.getUsername())
            .password(nd.getPassword())  // Đã mã hóa BCrypt
            .roles(nd.getRoles().stream()
                .map(Role::getName)
                .toArray(String[]::new))
            .accountLocked(!nd.isActive())
            .build();
    }
}
```

### 2.2 Authentication Flow

```
Client gửi credentials (username + password)
    ↓
AuthenticationFilter nhận request
    ↓
Tạo UsernamePasswordAuthenticationToken (chưa authenticated)
    ↓
AuthenticationManager.authenticate()
    ↓
DaoAuthenticationProvider
    ↓
UserDetailsService.loadUserByUsername()
    ↓
PasswordEncoder.matches(rawPassword, encodedPassword)
    ↓
Nếu match → tạo Authentication object (authenticated = true)
    ↓
Set vào SecurityContextHolder
    ↓
Controller có thể truy cập user info
```

### 2.3 Authentication Controller

```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;
    private final NguoiDungService nguoiDungService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        if (nguoiDungService.existsByUsername(request.username())) {
            throw new DuplicateResourceException("Username đã tồn tại");
        }

        NguoiDung nd = new NguoiDung();
        nd.setUsername(request.username());
        nd.setEmail(request.email());
        nd.setPassword(passwordEncoder.encode(request.password()));
        nd.setRoles(Set.of(Role.USER));
        nguoiDungService.save(nd);

        String token = tokenProvider.generateToken(nd.getUsername(), nd.getRoleNames());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new AuthResponse(token, "Bearer", nd.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);
        
        return ResponseEntity.ok(new AuthResponse(token, "Bearer", request.username()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestHeader("Authorization") String authHeader) {
        String oldToken = authHeader.replace("Bearer ", "");
        String newToken = tokenProvider.refreshToken(oldToken);
        String username = tokenProvider.getUsernameFromToken(newToken);
        return ResponseEntity.ok(new AuthResponse(newToken, "Bearer", username));
    }
}
```

---

## 3. JWT (JSON Web Token)

### 3.1 JWT Structure

```
Header.Payload.Signature

Header: {"alg": "HS512", "typ": "JWT"}
Payload: {"sub": "user123", "roles": ["ADMIN"], "exp": 1700000000, "iat": 1699900000}
Signature: HMACSHA512(base64(header) + "." + base64(payload), secret)
```

### 3.2 JWT Token Provider

```java
@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration:86400000}")  // 24 hours
    private long jwtExpiration;

    @Value("${app.jwt.refresh-expiration:604800000}")  // 7 days
    private long refreshExpiration;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return generateToken(userDetails.getUsername(), 
            userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList());
    }

    public String generateToken(String username, List<String> roles) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
            .setSubject(username)
            .claim("roles", roles)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    public String refreshToken(String token) {
        Claims claims = extractClaims(token);
        Date now = new Date();
        Date expiry = new Date(now.getTime() + refreshExpiration);

        return Jwts.builder()
            .setSubject(claims.getSubject())
            .claim("roles", claims.get("roles"))
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    public String getUsernameFromToken(String token) {
        return extractClaims(token).getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        return extractClaims(token).get("roles", List.class);
    }

    public boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new AuthException("Token đã hết hạn");
        } catch (MalformedJwtException e) {
            throw new AuthException("Token không đúng format");
        } catch (UnsupportedJwtException e) {
            throw new AuthException("Token không được hỗ trợ");
        } catch (IllegalArgumentException e) {
            throw new AuthException("Token trống");
        }
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
}
```

### 3.3 JWT Authentication Filter

```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider,
                                   UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) 
            throws ServletException, IOException {

        String token = extractToken(request);

        if (token != null && tokenProvider.validateToken(token)) {
            String username = tokenProvider.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/") || path.startsWith("/actuator/");
    }
}
```

---

## 4. Authorization (Phân quyền)

### 4.1 Method-Level Security

```java
@Service
public class NhanVienService {

    @PreAuthorize("hasRole('ADMIN')")
    public void xoaNhanVien(Long id) {
        repo.deleteById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public NhanVienDTO capNhat(Long id, NhanVienRequest request) {
        // Chỉ ADMIN hoặc MANAGER mới được update
    }

    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public NhanVienDTO layTheoId(Long id) {
        // User chỉ xem được info của chính mình, ADMIN xem tất cả
    }

    @PreAuthorize("@securityService.isOwner(#id, authentication.name)")
    public void capNhatProfile(Long id, ProfileRequest request) {
        // Delegate logic phức tạp cho SecurityService
    }

    @PostAuthorize("returnObject.email == authentication.name or hasRole('ADMIN')")
    public NhanVienDTO layChiTiet(Long id) {
        // Kiểm tra SAU khi method chạy xong
        return repo.findById(id).map(mapper::toDTO).orElseThrow();
    }

    @PreFilter("filterObject.createdBy == authentication.name")
    public void xoaNhieu(List<NhanVien> nhanViens) {
        // Filter input list — chỉ giữ items thuộc current user
    }

    @PostFilter("filterObject.public == true or filterObject.createdBy == authentication.name")
    public List<Document> layTatCa() {
        // Filter output list
    }
}
```

### 4.2 Custom Security Service

```java
@Service("securityService")
public class SecurityService {

    private final NhanVienRepository nhanVienRepo;

    public boolean isOwner(Long resourceId, String username) {
        return nhanVienRepo.findById(resourceId)
            .map(nv -> nv.getCreatedBy().equals(username))
            .orElse(false);
    }

    public boolean isInSamePhongBan(Long targetId, String username) {
        // Kiểm tra cùng phòng ban
        return nhanVienRepo.areInSamePhongBan(targetId, username);
    }
}
```

### 4.3 Role Hierarchy

```java
@Bean
public RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
    hierarchy.setHierarchy("""
        ROLE_SUPER_ADMIN > ROLE_ADMIN
        ROLE_ADMIN > ROLE_MANAGER
        ROLE_MANAGER > ROLE_USER
        """);
    return hierarchy;
}
// SUPER_ADMIN có tất cả quyền của ADMIN, MANAGER, USER
```

---

## 5. Password Encoding

### 5.1 BCrypt

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
    // Strength 12: 2^12 = 4096 hashing rounds
    // Tăng strength = chậm hơn nhưng an toàn hơn (brute-force protection)
}

// Sử dụng
String encoded = passwordEncoder.encode("myPassword123");
// → $2a$12$LQv3c1yqBo9SkvXS7QTJp.pZV4RAqW.bRXCUJw0UXkFzA./4K5C3S

boolean matches = passwordEncoder.matches("myPassword123", encoded);
// → true
```

### 5.2 Delegating Password Encoder

```java
@Bean
public PasswordEncoder passwordEncoder() {
    // Hỗ trợ nhiều algorithm (migration)
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    // Passwords stored as: {bcrypt}$2a$10$...
    //                       {noop}plaintext
    //                       {argon2}$argon2id$...
}
```

---

## 6. OAuth2 / Social Login

### 6.1 OAuth2 Login Configuration

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: ${GOOGLE_CLIENT_ID}
            client-secret: ${GOOGLE_CLIENT_SECRET}
            scope: openid, profile, email
          github:
            client-id: ${GITHUB_CLIENT_ID}
            client-secret: ${GITHUB_CLIENT_SECRET}
            scope: user:email
```

```java
@Configuration
public class OAuth2SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService()))
                .successHandler(oAuth2SuccessHandler())
                .failureHandler(oAuth2FailureHandler())
            );
        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserService() {
        return new CustomOAuth2UserService();
    }
}
```

### 6.2 Custom OAuth2 User Service

```java
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final NguoiDungRepository nguoiDungRepo;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);
        String provider = request.getClientRegistration().getRegistrationId();
        String email = oAuth2User.getAttribute("email");

        NguoiDung nd = nguoiDungRepo.findByEmail(email)
            .orElseGet(() -> createNewUser(oAuth2User, provider));

        return new CustomOAuth2User(nd, oAuth2User.getAttributes());
    }

    private NguoiDung createNewUser(OAuth2User oAuth2User, String provider) {
        NguoiDung nd = new NguoiDung();
        nd.setEmail(oAuth2User.getAttribute("email"));
        nd.setHoTen(oAuth2User.getAttribute("name"));
        nd.setProvider(provider);
        nd.setRoles(Set.of(Role.USER));
        return nguoiDungRepo.save(nd);
    }
}
```

---

## 7. CSRF Protection

```java
// REST API (stateless) → Tắt CSRF
.csrf(csrf -> csrf.disable())

// Web app (stateful, có form) → Giữ CSRF
.csrf(csrf -> csrf
    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
    .ignoringRequestMatchers("/api/webhook/**")  // Webhook không cần CSRF
)
```

```html
<!-- Thymeleaf tự thêm CSRF token vào form -->
<form th:action="@{/submit}" method="post">
    <!-- th:action tự thêm hidden input _csrf -->
    <input type="text" name="data"/>
    <button type="submit">Submit</button>
</form>
```

---

## 8. Security Context

```java
// Lấy thông tin user hiện tại
@RestController
public class UserController {

    @GetMapping("/api/me")
    public ResponseEntity<UserInfo> getCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails) {
        // Cách 1: @AuthenticationPrincipal
        return ResponseEntity.ok(new UserInfo(userDetails.getUsername()));
    }

    @GetMapping("/api/profile")
    public ResponseEntity<UserInfo> getProfile() {
        // Cách 2: SecurityContextHolder
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
        return ResponseEntity.ok(new UserInfo(username, roles));
    }
}
```

---

## 9. Security Headers

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .headers(headers -> headers
            .contentSecurityPolicy(csp -> csp
                .policyDirectives("default-src 'self'; script-src 'self'"))
            .frameOptions(frame -> frame.deny())
            .httpStrictTransportSecurity(hsts -> hsts
                .includeSubDomains(true)
                .maxAgeInSeconds(31536000))
            .xssProtection(xss -> xss.headerValue(
                XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
        );
    return http.build();
}
```

---

## 10. Rate Limiting

```java
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) 
            throws ServletException, IOException {

        String clientIp = request.getRemoteAddr();
        Bucket bucket = buckets.computeIfAbsent(clientIp, this::createBucket);

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("{\"error\": \"Rate limit exceeded\"}");
        }
    }

    private Bucket createBucket(String key) {
        Bandwidth limit = Bandwidth.classic(100, Refill.intervally(100, Duration.ofMinutes(1)));
        return Bucket.builder().addLimit(limit).build();
    }
}
```

---

## 11. Token Blacklist (Logout)

```java
@Service
public class TokenBlacklistService {

    private final Set<String> blacklist = ConcurrentHashMap.newKeySet();
    // Thực tế: dùng Redis với TTL = token expiration

    public void blacklist(String token) {
        blacklist.add(token);
    }

    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
}

// Trong JwtFilter: kiểm tra blacklist
if (token != null && !blacklistService.isBlacklisted(token) 
    && tokenProvider.validateToken(token)) {
    // Set authentication...
}

// Logout endpoint
@PostMapping("/api/auth/logout")
public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
    String token = authHeader.replace("Bearer ", "");
    tokenBlacklistService.blacklist(token);
    return ResponseEntity.ok().build();
}
```

---

## 12. Best Practices

| Chủ đề | Nên | Không nên |
|--------|-----|----------|
| Password | BCrypt (strength 12+), never log | Plain text, MD5, SHA-1 |
| JWT | Short expiration, refresh token | Long-lived tokens, store in localStorage |
| CORS | Explicit origins | `allowedOrigins("*")` ở production |
| Authorization | Method-level + URL-level | Chỉ URL-level |
| Secrets | Env variables, Vault | Hard-code trong source |
| HTTPS | Always in production | HTTP cho API endpoints |
| Session | Stateless cho REST API | Session-based cho pure REST |
| Error messages | Generic ("Invalid credentials") | Specific ("User not found" vs "Wrong password") |
| Rate limiting | Per-IP, per-user limits | Unlimited requests |
| Token storage | HttpOnly cookie hoặc memory | localStorage (XSS risk) |
