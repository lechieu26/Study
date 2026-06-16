# Spring Testing - Lý Thuyết Chi Tiết

## Giới thiệu

Testing trong Spring Boot bao gồm nhiều tầng: **Unit Test** (test từng class riêng lẻ), **Integration Test** (test toàn bộ flow với Spring context), và **End-to-End Test** (test từ HTTP request đến database).

**Test Pyramid:**
```
        /  E2E Tests  \         ← Ít nhất, chậm nhất, đắt nhất
       / Integration   \        ← Trung bình
      /  Unit Tests     \       ← Nhiều nhất, nhanh nhất, rẻ nhất
```

**Spring Boot Test Starters:**
- JUnit 5 (Jupiter)
- Mockito (mocking)
- AssertJ (fluent assertions)
- MockMvc (test controllers)
- @DataJpaTest (test repositories)
- @WebMvcTest (test web layer only)
- TestRestTemplate / WebTestClient

---

## 1. Unit Test với Mockito

### 1.1 Setup cơ bản

```java
@ExtendWith(MockitoExtension.class)  // Không load Spring context → nhanh
class NhanVienServiceTest {

    @Mock  // Tạo mock object
    private NhanVienRepository repo;

    @Mock
    private PhongBanRepository phongBanRepo;

    @InjectMocks  // Inject mocks vào service
    private NhanVienService service;

    @Test
    void layTheoId_tonTai_traVeDTO() {
        // Arrange
        NhanVien nv = new NhanVien(1L, "An", "an@email.com", BigDecimal.valueOf(15000000));
        when(repo.findById(1L)).thenReturn(Optional.of(nv));

        // Act
        NhanVienDTO result = service.layTheoId(1L);

        // Assert
        assertNotNull(result);
        assertEquals("An", result.getHoTen());
        assertEquals("an@email.com", result.getEmail());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    void layTheoId_khongTonTai_throwException() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.layTheoId(99L));
        verify(repo).findById(99L);
    }
}
```

### 1.2 Mockito Cheat Sheet

```java
// ===== Stubbing (Giả lập behavior) =====
when(mock.method(args)).thenReturn(value);           // Return giá trị
when(mock.method(args)).thenThrow(new Exception());  // Throw exception
when(mock.method(args)).thenAnswer(invocation -> {   // Custom logic
    Long id = invocation.getArgument(0);
    return new Entity(id);
});
doNothing().when(mock).voidMethod(args);             // Void method
doThrow(new Exception()).when(mock).voidMethod();    // Void throw

// ===== Argument Matchers =====
when(repo.findById(any())).thenReturn(Optional.of(entity));
when(repo.findByEmail(anyString())).thenReturn(Optional.empty());
when(repo.save(any(NhanVien.class))).thenAnswer(inv -> {
    NhanVien nv = inv.getArgument(0);
    nv.setId(1L);
    return nv;
});
when(repo.findByLuongBetween(eq(BigDecimal.ZERO), any())).thenReturn(List.of());

// ===== Verification =====
verify(mock).method(args);                           // Gọi đúng 1 lần
verify(mock, times(2)).method(args);                 // Gọi đúng 2 lần
verify(mock, never()).method(args);                  // Không bao giờ gọi
verify(mock, atLeastOnce()).method(args);            // Ít nhất 1 lần
verify(mock, atMost(3)).method(args);               // Tối đa 3 lần
verifyNoMoreInteractions(mock);                      // Không có interaction nào khác

// ===== ArgumentCaptor =====
ArgumentCaptor<NhanVien> captor = ArgumentCaptor.forClass(NhanVien.class);
verify(repo).save(captor.capture());
NhanVien saved = captor.getValue();
assertEquals("An", saved.getHoTen());
```

### 1.3 Test Service đầy đủ

```java
@ExtendWith(MockitoExtension.class)
class NhanVienServiceTest {

    @Mock private NhanVienRepository repo;
    @Mock private PhongBanRepository phongBanRepo;
    @InjectMocks private NhanVienService service;

    private NhanVien sampleNhanVien;
    private PhongBan samplePhongBan;
    private NhanVienRequest sampleRequest;

    @BeforeEach
    void setUp() {
        samplePhongBan = new PhongBan(1L, "IT");
        sampleNhanVien = new NhanVien(1L, "An", "an@email.com", 
            BigDecimal.valueOf(15000000));
        sampleNhanVien.setPhongBan(samplePhongBan);
        sampleRequest = new NhanVienRequest("An", "an@email.com", 
            BigDecimal.valueOf(15000000), 1L);
    }

    @Test
    void taoMoi_thanhCong() {
        when(repo.existsByEmail("an@email.com")).thenReturn(false);
        when(phongBanRepo.findById(1L)).thenReturn(Optional.of(samplePhongBan));
        when(repo.save(any(NhanVien.class))).thenReturn(sampleNhanVien);

        NhanVienDTO result = service.taoMoi(sampleRequest);

        assertAll(
            () -> assertNotNull(result),
            () -> assertEquals("An", result.getHoTen()),
            () -> assertEquals("an@email.com", result.getEmail())
        );
        verify(repo).save(any(NhanVien.class));
    }

    @Test
    void taoMoi_emailTrung_throwException() {
        when(repo.existsByEmail("an@email.com")).thenReturn(true);

        DuplicateResourceException ex = assertThrows(
            DuplicateResourceException.class,
            () -> service.taoMoi(sampleRequest)
        );
        assertTrue(ex.getMessage().contains("Email đã tồn tại"));
        verify(repo, never()).save(any());
    }

    @Test
    void taoMoi_phongBanKhongTonTai_throwException() {
        when(repo.existsByEmail("an@email.com")).thenReturn(false);
        when(phongBanRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.taoMoi(sampleRequest));
    }

    @Test
    void xoa_tonTai_xoaThanhCong() {
        when(repo.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> service.xoa(1L));
        verify(repo).deleteById(1L);
    }

    @Test
    void xoa_khongTonTai_throwException() {
        when(repo.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.xoa(99L));
        verify(repo, never()).deleteById(any());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void taoMoi_hoTenTrong_throwException(String hoTen) {
        NhanVienRequest request = new NhanVienRequest(hoTen, "test@email.com", 
            BigDecimal.valueOf(10000000), 1L);
        // Validation test...
    }
}
```

---

## 2. Integration Test

### 2.1 @SpringBootTest

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional  // Rollback sau mỗi test → database clean
class NhanVienControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NhanVienRepository repo;

    @BeforeEach
    void setUp() {
        repo.deleteAll();
        repo.save(new NhanVien(null, "An", "an@email.com", BigDecimal.valueOf(15000000)));
        repo.save(new NhanVien(null, "Binh", "binh@email.com", BigDecimal.valueOf(20000000)));
    }

    @Test
    void layTatCa_traVeDanhSach() throws Exception {
        mockMvc.perform(get("/api/nhan-vien")
                .param("page", "0")
                .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(2)))
            .andExpect(jsonPath("$.content[0].hoTen").exists())
            .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    void layTheoId_tonTai_traVe200() throws Exception {
        NhanVien nv = repo.findAll().get(0);

        mockMvc.perform(get("/api/nhan-vien/{id}", nv.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.hoTen").value(nv.getHoTen()))
            .andExpect(jsonPath("$.email").value(nv.getEmail()));
    }

    @Test
    void layTheoId_khongTonTai_traVe404() throws Exception {
        mockMvc.perform(get("/api/nhan-vien/{id}", 99999))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void taoMoi_duLieuHopLe_traVe201() throws Exception {
        NhanVienRequest request = new NhanVienRequest(
            "Cuong", "cuong@email.com", BigDecimal.valueOf(18000000), 1L);

        mockMvc.perform(post("/api/nhan-vien")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.hoTen").value("Cuong"))
            .andExpect(jsonPath("$.email").value("cuong@email.com"))
            .andExpect(jsonPath("$.id").exists());

        assertEquals(3, repo.count());
    }

    @Test
    void taoMoi_emailTrung_traVe409() throws Exception {
        NhanVienRequest request = new NhanVienRequest(
            "An2", "an@email.com", BigDecimal.valueOf(18000000), 1L);

        mockMvc.perform(post("/api/nhan-vien")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isConflict());
    }

    @Test
    void taoMoi_duLieuKhongHopLe_traVe400() throws Exception {
        String json = """
            {"hoTen": "", "email": "invalid", "luong": -1}
            """;

        mockMvc.perform(post("/api/nhan-vien")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.fieldErrors.hoTen").exists())
            .andExpect(jsonPath("$.fieldErrors.email").exists());
    }

    @Test
    void xoa_tonTai_traVe204() throws Exception {
        NhanVien nv = repo.findAll().get(0);

        mockMvc.perform(delete("/api/nhan-vien/{id}", nv.getId()))
            .andExpect(status().isNoContent());

        assertFalse(repo.existsById(nv.getId()));
    }
}
```

### 2.2 @WebMvcTest (Controller Layer Only)

```java
@WebMvcTest(NhanVienController.class)  // Chỉ load web layer, mock service
class NhanVienControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean  // Mock Spring bean (inject vào controller)
    private NhanVienService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void layTatCa_traVe200() throws Exception {
        Page<NhanVienDTO> page = new PageImpl<>(List.of(
            new NhanVienDTO(1L, "An", "an@email.com", BigDecimal.valueOf(15000000))
        ));
        when(service.layTatCa(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/nhan-vien"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].hoTen").value("An"));
    }

    @Test
    void taoMoi_validationFail_traVe400() throws Exception {
        String json = """
            {"hoTen": "", "email": "not-email"}
            """;

        mockMvc.perform(post("/api/nhan-vien")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
        
        verify(service, never()).taoMoi(any());
    }
}
```

### 2.3 @DataJpaTest (Repository Layer Only)

```java
@DataJpaTest  // Chỉ load JPA components + embedded DB (H2)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // Dùng DB thật
class NhanVienRepositoryTest {

    @Autowired
    private NhanVienRepository repo;

    @Autowired
    private TestEntityManager em;

    @Test
    void findByEmail_tonTai_traVeNhanVien() {
        NhanVien nv = new NhanVien(null, "An", "an@email.com", BigDecimal.valueOf(15000000));
        em.persistAndFlush(nv);

        Optional<NhanVien> found = repo.findByEmail("an@email.com");

        assertTrue(found.isPresent());
        assertEquals("An", found.get().getHoTen());
    }

    @Test
    void findByEmail_khongTonTai_traVeEmpty() {
        Optional<NhanVien> found = repo.findByEmail("noexist@email.com");
        assertTrue(found.isEmpty());
    }

    @Test
    void findByLuongBetween_traVeDanhSach() {
        em.persistAndFlush(new NhanVien(null, "A", "a@e.com", BigDecimal.valueOf(10000000)));
        em.persistAndFlush(new NhanVien(null, "B", "b@e.com", BigDecimal.valueOf(20000000)));
        em.persistAndFlush(new NhanVien(null, "C", "c@e.com", BigDecimal.valueOf(30000000)));

        List<NhanVien> result = repo.findByLuongBetween(
            BigDecimal.valueOf(15000000), BigDecimal.valueOf(25000000));

        assertEquals(1, result.size());
        assertEquals("B", result.get(0).getHoTen());
    }

    @Test
    void existsByEmail_tonTai_traVeTrue() {
        em.persistAndFlush(new NhanVien(null, "An", "an@email.com", BigDecimal.valueOf(15000000)));

        assertTrue(repo.existsByEmail("an@email.com"));
        assertFalse(repo.existsByEmail("noexist@email.com"));
    }
}
```

---

## 3. MockMvc Chi Tiết

### 3.1 Request builders

```java
// GET với params
mockMvc.perform(get("/api/nhan-vien")
    .param("page", "0")
    .param("size", "10")
    .param("sort", "luong,desc")
    .header("Authorization", "Bearer " + token)
    .accept(MediaType.APPLICATION_JSON))

// POST với body
mockMvc.perform(post("/api/nhan-vien")
    .contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(request))
    .header("X-Request-Id", "test-123"))

// PUT
mockMvc.perform(put("/api/nhan-vien/{id}", 1L)
    .contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(request)))

// DELETE
mockMvc.perform(delete("/api/nhan-vien/{id}", 1L))

// PATCH
mockMvc.perform(patch("/api/nhan-vien/{id}", 1L)
    .contentType(MediaType.APPLICATION_JSON)
    .content("{\"luong\": 20000000}"))

// Multipart (file upload)
mockMvc.perform(multipart("/api/files/upload")
    .file(new MockMultipartFile("file", "test.pdf", "application/pdf", content))
    .param("description", "Test file"))
```

### 3.2 Response assertions

```java
mockMvc.perform(get("/api/nhan-vien"))
    // Status
    .andExpect(status().isOk())              // 200
    .andExpect(status().isCreated())          // 201
    .andExpect(status().isBadRequest())       // 400
    .andExpect(status().isNotFound())         // 404
    
    // Headers
    .andExpect(header().string("Content-Type", "application/json"))
    .andExpect(header().exists("X-Total-Count"))
    
    // JSON body (JsonPath)
    .andExpect(jsonPath("$.content", hasSize(2)))
    .andExpect(jsonPath("$.content[0].hoTen").value("An"))
    .andExpect(jsonPath("$.content[0].luong").value(15000000))
    .andExpect(jsonPath("$.totalElements").value(2))
    .andExpect(jsonPath("$.content[*].email", 
        containsInAnyOrder("an@email.com", "binh@email.com")))
    
    // Negation
    .andExpect(jsonPath("$.content[0].password").doesNotExist())
    
    // Print for debugging
    .andDo(print());
```

---

## 4. Test Configuration

### 4.1 Test Properties

```yaml
# src/test/resources/application-test.yml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
  profiles:
    active: test

app:
  jwt:
    secret: dGVzdC1zZWNyZXQta2V5LWZvci11bml0LXRlc3Rpbmc=
    expiration: 3600000
```

### 4.2 Test Fixtures

```java
// Test data builder
public class TestData {
    
    public static NhanVien nhanVien() {
        return nhanVien("An", "an@email.com");
    }
    
    public static NhanVien nhanVien(String hoTen, String email) {
        NhanVien nv = new NhanVien();
        nv.setHoTen(hoTen);
        nv.setEmail(email);
        nv.setLuong(BigDecimal.valueOf(15000000));
        nv.setTrangThai(true);
        return nv;
    }
    
    public static NhanVienRequest nhanVienRequest() {
        return new NhanVienRequest("An", "an@email.com", 
            BigDecimal.valueOf(15000000), 1L);
    }
}
```

### 4.3 @TestConfiguration

```java
@TestConfiguration
public class TestSecurityConfig {
    
    @Bean
    public SecurityFilterChain testFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}

// Sử dụng trong test
@SpringBootTest
@Import(TestSecurityConfig.class)  // Override security cho test
class SecuredControllerIT { }
```

---

## 5. Testing Security

```java
@WebMvcTest(NhanVienController.class)
@Import(SecurityConfig.class)
class NhanVienControllerSecurityTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private NhanVienService service;

    @Test
    void layTatCa_khongToken_traVe401() throws Exception {
        mockMvc.perform(get("/api/nhan-vien"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void layTatCa_coQuyen_traVe200() throws Exception {
        when(service.layTatCa(any())).thenReturn(Page.empty());
        
        mockMvc.perform(get("/api/nhan-vien"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void taoMoi_khongDuQuyen_traVe403() throws Exception {
        mockMvc.perform(post("/api/nhan-vien")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void taoMoi_admin_traVe201() throws Exception {
        when(service.taoMoi(any())).thenReturn(new NhanVienDTO(1L, "An", "an@e.com", null));
        
        mockMvc.perform(post("/api/nhan-vien")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"hoTen\":\"An\",\"email\":\"an@e.com\",\"luong\":15000000,\"phongBanId\":1}"))
            .andExpect(status().isCreated());
    }
}
```

---

## 6. TestContainers

```java
@SpringBootTest
@Testcontainers
class NhanVienRepositoryContainerTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private NhanVienRepository repo;

    @Test
    void testWithRealPostgres() {
        NhanVien nv = new NhanVien(null, "An", "an@email.com", BigDecimal.valueOf(15000000));
        NhanVien saved = repo.save(nv);

        assertNotNull(saved.getId());
        assertTrue(repo.existsByEmail("an@email.com"));
    }
}
```

---

## 7. AssertJ (Fluent Assertions)

```java
import static org.assertj.core.api.Assertions.*;

@Test
void testAssertJ() {
    NhanVienDTO nv = service.layTheoId(1L);

    // Object assertions
    assertThat(nv).isNotNull();
    assertThat(nv.getHoTen()).isEqualTo("An");
    assertThat(nv.getEmail()).contains("@").endsWith(".com");
    assertThat(nv.getLuong()).isGreaterThan(BigDecimal.ZERO);

    // Collection assertions
    List<NhanVienDTO> list = service.layTatCa();
    assertThat(list)
        .isNotEmpty()
        .hasSize(5)
        .extracting(NhanVienDTO::getHoTen)
        .contains("An", "Binh")
        .doesNotContain("Unknown");

    // Exception assertions
    assertThatThrownBy(() -> service.layTheoId(99L))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessageContaining("không tìm thấy");

    assertThatCode(() -> service.xoa(1L)).doesNotThrowAnyException();
}
```

---

## 8. Test Coverage

```xml
<!-- pom.xml: JaCoCo plugin -->
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution>
            <goals><goal>prepare-agent</goal></goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals><goal>report</goal></goals>
        </execution>
    </executions>
    <configuration>
        <rules>
            <rule>
                <element>BUNDLE</element>
                <limits>
                    <limit>
                        <counter>LINE</counter>
                        <value>COVEREDRATIO</value>
                        <minimum>0.80</minimum>
                    </limit>
                </limits>
            </rule>
        </rules>
    </configuration>
</plugin>
```

---

## 9. Best Practices

| Chủ đề | Nên | Không nên |
|--------|-----|----------|
| Pattern | AAA (Arrange-Act-Assert) | Logic phức tạp trong test |
| Naming | `methodName_scenario_expectedResult` | Tên chung chung |
| Scope | Unit test cho logic, Integration cho flow | Integration test cho mọi thứ |
| Data | Independent test data, @BeforeEach | Shared mutable state |
| Speed | `@WebMvcTest`/`@DataJpaTest` khi có thể | `@SpringBootTest` cho mọi test |
| Coverage | >= 80% lines, focus critical paths | 100% coverage bằng mọi giá |
| Assertions | Specific assertions, test edge cases | Chỉ test happy path |
| Mock | Mock external dependencies | Mock everything |
| Database | `@Transactional` (rollback), H2 for unit | Shared test database |
| CI | Run tests in CI, fail on regression | Skip tests |
