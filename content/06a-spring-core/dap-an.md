# Spring Core - Đáp Án

## Bài 1: Dependency Injection Cơ Bản

### Cách 1: Constructor Injection với List

```java
// Interface
public interface ThongBaoService {
    void gui(String nguoiNhan, String noiDung);
    String getKenhGui();
}

// Implementations
@Service
public class EmailThongBao implements ThongBaoService {
    @Override
    public void gui(String nguoiNhan, String noiDung) {
        System.out.printf("[EMAIL] Gửi tới %s: %s%n", nguoiNhan, noiDung);
    }
    @Override
    public String getKenhGui() { return "EMAIL"; }
}

@Service
public class SmsThongBao implements ThongBaoService {
    @Override
    public void gui(String nguoiNhan, String noiDung) {
        System.out.printf("[SMS] Gửi tới %s: %s%n", nguoiNhan, noiDung);
    }
    @Override
    public String getKenhGui() { return "SMS"; }
}

@Service
public class PushThongBao implements ThongBaoService {
    @Override
    public void gui(String nguoiNhan, String noiDung) {
        System.out.printf("[PUSH] Gửi tới %s: %s%n", nguoiNhan, noiDung);
    }
    @Override
    public String getKenhGui() { return "PUSH"; }
}

// Manager
@Service
public class ThongBaoManager {
    private final List<ThongBaoService> services;

    public ThongBaoManager(List<ThongBaoService> services) {
        this.services = services;
    }

    public void guiTatCa(String nguoiNhan, String noiDung) {
        services.forEach(s -> s.gui(nguoiNhan, noiDung));
    }

    public void guiTheoKenh(String kenh, String nguoiNhan, String noiDung) {
        services.stream()
            .filter(s -> s.getKenhGui().equalsIgnoreCase(kenh))
            .findFirst()
            .ifPresent(s -> s.gui(nguoiNhan, noiDung));
    }
}

// Test
@ExtendWith(MockitoExtension.class)
class ThongBaoManagerTest {
    @Mock private ThongBaoService emailService;
    @Mock private ThongBaoService smsService;

    @Test
    void guiTatCa_guiQuaTatCaKenh() {
        ThongBaoManager manager = new ThongBaoManager(List.of(emailService, smsService));
        manager.guiTatCa("An", "Xin chào");
        verify(emailService).gui("An", "Xin chào");
        verify(smsService).gui("An", "Xin chào");
    }
}
```

## Bài 2: Bean Scopes và Lifecycle

### Cách 1: ObjectProvider

```java
@Component
@Scope("prototype")
public class GioHang {
    private final String id = UUID.randomUUID().toString();
    private final List<String> items = new ArrayList<>();

    @PostConstruct
    public void init() {
        System.out.println("GioHang created: " + id);
    }

    public void them(String item) { items.add(item); }
    public List<String> getItems() { return Collections.unmodifiableList(items); }
    public String getId() { return id; }
}

@Service
public class MuaHangService {
    private final ObjectProvider<GioHang> gioHangProvider;

    public MuaHangService(ObjectProvider<GioHang> gioHangProvider) {
        this.gioHangProvider = gioHangProvider;
    }

    public GioHang taoGioMoi() {
        return gioHangProvider.getObject();
    }
}

// Test
@SpringBootTest
class MuaHangServiceTest {
    @Autowired private MuaHangService service;

    @Test
    void taoGioMoi_moiLanTraVeInstanceMoi() {
        GioHang gh1 = service.taoGioMoi();
        GioHang gh2 = service.taoGioMoi();
        assertNotEquals(gh1.getId(), gh2.getId());
    }
}
```
