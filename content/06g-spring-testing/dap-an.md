# Spring Testing - Đáp Án

## Bài 1: Unit Test Service Layer

```java
@ExtendWith(MockitoExtension.class)
class NhanVienServiceTest {
    @Mock private NhanVienRepository repo;
    @Mock private PhongBanRepository pbRepo;
    @InjectMocks private NhanVienService service;

    @Test
    void layTheoId_tonTai_traVeDTO() {
        NhanVien nv = new NhanVien(1L, "An", "an@email.com", BigDecimal.valueOf(15000000));
        when(repo.findById(1L)).thenReturn(Optional.of(nv));

        NhanVienDTO result = service.layTheoId(1L);

        assertThat(result).isNotNull();
        assertThat(result.getHoTen()).isEqualTo("An");
        verify(repo).findById(1L);
    }

    @Test
    void layTheoId_khongTonTai_throwException() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.layTheoId(99L))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void taoMoi_emailTrung_throwException() {
        NhanVienRequest req = new NhanVienRequest("An", "an@email.com", BigDecimal.TEN, 1L);
        when(repo.existsByEmail("an@email.com")).thenReturn(true);

        assertThatThrownBy(() -> service.taoMoi(req))
            .isInstanceOf(DuplicateResourceException.class);
        verify(repo, never()).save(any());
    }

    @Test
    void taoMoi_thanhCong_captureSavedEntity() {
        NhanVienRequest req = new NhanVienRequest("An", "an@email.com", BigDecimal.valueOf(15000000), 1L);
        PhongBan pb = new PhongBan(1L, "IT");
        when(repo.existsByEmail("an@email.com")).thenReturn(false);
        when(pbRepo.findById(1L)).thenReturn(Optional.of(pb));
        when(repo.save(any())).thenAnswer(inv -> { inv.getArgument(0, NhanVien.class).setId(1L); return inv.getArgument(0); });

        service.taoMoi(req);

        ArgumentCaptor<NhanVien> captor = ArgumentCaptor.forClass(NhanVien.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getHoTen()).isEqualTo("An");
        assertThat(captor.getValue().getPhongBan()).isEqualTo(pb);
    }

    @Test
    void xoa_khongTonTai_throwException() {
        when(repo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.xoa(99L))
            .isInstanceOf(ResourceNotFoundException.class);
        verify(repo, never()).deleteById(any());
    }
}
```

## Bài 2: Integration Test với MockMvc

```java
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class NhanVienControllerIT {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper mapper;
    @Autowired private NhanVienRepository repo;

    @BeforeEach
    void setUp() {
        repo.deleteAll();
        repo.save(new NhanVien(null, "An", "an@email.com", BigDecimal.valueOf(15000000)));
    }

    @Test
    void layTatCa_traVe200() throws Exception {
        mockMvc.perform(get("/api/nhan-vien").param("page", "0").param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(1)))
            .andExpect(jsonPath("$.content[0].hoTen").value("An"));
    }

    @Test
    void taoMoi_valid_traVe201() throws Exception {
        String json = mapper.writeValueAsString(
            new NhanVienRequest("Binh", "binh@email.com", BigDecimal.valueOf(20000000), 1L));

        mockMvc.perform(post("/api/nhan-vien")
                .contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.hoTen").value("Binh"));
    }

    @Test
    void taoMoi_invalid_traVe400() throws Exception {
        mockMvc.perform(post("/api/nhan-vien")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"hoTen\":\"\",\"email\":\"invalid\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.fieldErrors").exists());
    }

    @Test
    void xoa_tonTai_traVe204() throws Exception {
        Long id = repo.findAll().get(0).getId();
        mockMvc.perform(delete("/api/nhan-vien/{id}", id))
            .andExpect(status().isNoContent());
        assertThat(repo.existsById(id)).isFalse();
    }
}
```

## Bài 4: Security Testing

```java
@WebMvcTest(NhanVienController.class)
@Import(SecurityConfig.class)
class SecurityTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private NhanVienService service;

    @Test
    void noAuth_returns401() throws Exception {
        mockMvc.perform(get("/api/nhan-vien"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void userRole_getAllowed() throws Exception {
        when(service.layTatCa(any())).thenReturn(Page.empty());
        mockMvc.perform(get("/api/nhan-vien"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void userRole_deleteForbidden() throws Exception {
        mockMvc.perform(delete("/api/nhan-vien/1"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminRole_deleteAllowed() throws Exception {
        doNothing().when(service).xoa(1L);
        mockMvc.perform(delete("/api/nhan-vien/1"))
            .andExpect(status().isNoContent());
    }
}
```
