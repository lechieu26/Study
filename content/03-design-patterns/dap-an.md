# Design Patterns - Đáp Án

## Bài 1: Singleton - Quản lý Cấu Hình

### Cách 1: Eager Initialization
```java
public class CauHinhEager {
    private static final CauHinhEager INSTANCE = new CauHinhEager();
    private final Properties props = new Properties();

    private CauHinhEager() {
        taiCauHinh();
    }

    public static CauHinhEager getInstance() { return INSTANCE; }

    private void taiCauHinh() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Không thể tải cấu hình", e);
        }
    }

    public String layGiaTri(String key) { return props.getProperty(key); }
    public void datGiaTri(String key, String value) { props.setProperty(key, value); }
    public void reload() { props.clear(); taiCauHinh(); }
}
// Ưu điểm: Đơn giản, thread-safe tự nhiên
// Nhược điểm: Tạo instance ngay cả khi chưa cần
```

### Cách 2: Double-Checked Locking
```java
public class CauHinhDCL {
    private static volatile CauHinhDCL instance;
    private final ConcurrentHashMap<String, String> props = new ConcurrentHashMap<>();

    private CauHinhDCL() { taiCauHinh(); }

    public static CauHinhDCL getInstance() {
        if (instance == null) {
            synchronized (CauHinhDCL.class) {
                if (instance == null) {
                    instance = new CauHinhDCL();
                }
            }
        }
        return instance;
    }

    private void taiCauHinh() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            Properties p = new Properties();
            if (is != null) p.load(is);
            p.forEach((k, v) -> props.put(k.toString(), v.toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String layGiaTri(String key) { return props.get(key); }
    public void datGiaTri(String key, String value) { props.put(key, value); }

    public synchronized void reload() {
        props.clear();
        taiCauHinh();
    }
}
// Ưu điểm: Lazy initialization, thread-safe
// Nhược điểm: Phức tạp, cần volatile
```

### Cách 3: Enum Singleton (Khuyến nghị)
```java
public enum CauHinhEnum {
    INSTANCE;

    private final ConcurrentHashMap<String, String> props = new ConcurrentHashMap<>();

    CauHinhEnum() { taiCauHinh(); }

    private void taiCauHinh() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            Properties p = new Properties();
            if (is != null) p.load(is);
            p.forEach((k, v) -> props.put(k.toString(), v.toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String layGiaTri(String key) { return props.get(key); }
    public void datGiaTri(String key, String value) { props.put(key, value); }
    public void reload() { props.clear(); taiCauHinh(); }
}
// Ưu điểm: Chống serialization attack, chống reflection, ngắn gọn
// Nhược điểm: Không thể extend, eager init
```

---

## Bài 2: Factory + Strategy - Hệ Thống Thanh Toán

```java
// Strategy Interface
public interface ThanhToan {
    KetQuaThanhToan xuLy(double soTien);
    String tenPhuongThuc();
}

public record KetQuaThanhToan(String phuongThuc, double soTienGoc,
                               double phi, double soTienThucNhan, String trangThai) {
    @Override
    public String toString() {
        return String.format(
            "Phương thức: %s | Gốc: %,.0f | Phí: %,.0f | Thực nhận: %,.0f | %s",
            phuongThuc, soTienGoc, phi, soTienThucNhan, trangThai);
    }
}

// Concrete Strategies
public class TheNoiDia implements ThanhToan {
    public String tenPhuongThuc() { return "Thẻ Nội Địa"; }
    public KetQuaThanhToan xuLy(double soTien) {
        double phi = soTien * 0.01; // 1%
        return new KetQuaThanhToan(tenPhuongThuc(), soTien, phi, soTien - phi, "THÀNH CÔNG");
    }
}

public class TheQuocTe implements ThanhToan {
    public String tenPhuongThuc() { return "Thẻ Quốc Tế"; }
    public KetQuaThanhToan xuLy(double soTien) {
        double phi = soTien * 0.03 + 50000; // 3% + 50k
        return new KetQuaThanhToan(tenPhuongThuc(), soTien, phi, soTien - phi, "THÀNH CÔNG");
    }
}

public class ViDienTu implements ThanhToan {
    public String tenPhuongThuc() { return "Ví Điện Tử"; }
    public KetQuaThanhToan xuLy(double soTien) {
        double phi = Math.min(soTien * 0.015, 100000); // 1.5%, tối đa 100k
        return new KetQuaThanhToan(tenPhuongThuc(), soTien, phi, soTien - phi, "THÀNH CÔNG");
    }
}

// Factory
public class ThanhToanFactory {
    public static ThanhToan tao(String loai) {
        return switch (loai.toUpperCase()) {
            case "NOI_DIA" -> new TheNoiDia();
            case "QUOC_TE" -> new TheQuocTe();
            case "VI_DIEN_TU" -> new ViDienTu();
            default -> throw new IllegalArgumentException("Không hỗ trợ: " + loai);
        };
    }
}

// Decorator - Ghi Log
public class GhiLogThanhToan implements ThanhToan {
    private final ThanhToan thanhToan;

    public GhiLogThanhToan(ThanhToan thanhToan) { this.thanhToan = thanhToan; }

    public String tenPhuongThuc() { return thanhToan.tenPhuongThuc(); }

    public KetQuaThanhToan xuLy(double soTien) {
        System.out.printf("[LOG] Bắt đầu thanh toán %,.0f qua %s%n", soTien, tenPhuongThuc());
        long start = System.currentTimeMillis();
        KetQuaThanhToan kq = thanhToan.xuLy(soTien);
        long elapsed = System.currentTimeMillis() - start;
        System.out.printf("[LOG] Kết quả: %s (thời gian: %dms)%n", kq.trangThai(), elapsed);
        return kq;
    }
}

// Main
public class Main {
    public static void main(String[] args) {
        ThanhToan tt = new GhiLogThanhToan(ThanhToanFactory.tao("NOI_DIA"));
        System.out.println(tt.xuLy(1_000_000));

        tt = new GhiLogThanhToan(ThanhToanFactory.tao("QUOC_TE"));
        System.out.println(tt.xuLy(5_000_000));
    }
}
```

---

## Bài 3: Observer - Theo Dõi Cổ Phiếu

```java
public interface NguoiQuanSat {
    void capNhat(String maCK, double giaCu, double giaMoi);
}

public class CoPhieu {
    private final String maCK;
    private double gia;
    private final List<NguoiQuanSat> dsDangKy = new CopyOnWriteArrayList<>();

    public CoPhieu(String maCK, double gia) {
        this.maCK = maCK;
        this.gia = gia;
    }

    public void dangKy(NguoiQuanSat nqs) { dsDangKy.add(nqs); }
    public void huyDangKy(NguoiQuanSat nqs) { dsDangKy.remove(nqs); }

    public void capNhatGia(double giaMoi) {
        double giaCu = this.gia;
        this.gia = giaMoi;
        dsDangKy.forEach(nqs -> nqs.capNhat(maCK, giaCu, giaMoi));
    }

    public String getMaCK() { return maCK; }
    public double getGia() { return gia; }
}

public class NhaDauTu implements NguoiQuanSat {
    private final String ten;
    private final Map<String, double[]> canhBao = new HashMap<>(); // [min, max]

    public NhaDauTu(String ten) { this.ten = ten; }

    public void datCanhBao(String maCK, double min, double max) {
        canhBao.put(maCK, new double[]{min, max});
    }

    @Override
    public void capNhat(String maCK, double giaCu, double giaMoi) {
        double phanTram = (giaMoi - giaCu) / giaCu * 100;
        System.out.printf("[%s] %s: %,.0f → %,.0f (%+.2f%%)%n",
            ten, maCK, giaCu, giaMoi, phanTram);

        double[] nguong = canhBao.get(maCK);
        if (nguong != null) {
            if (giaMoi <= nguong[0])
                System.out.printf("  ⚠ CẢNH BÁO: %s dưới ngưỡng %,.0f!%n", maCK, nguong[0]);
            if (giaMoi >= nguong[1])
                System.out.printf("  ⚠ CẢNH BÁO: %s vượt ngưỡng %,.0f!%n", maCK, nguong[1]);
        }
    }
}

public class LichSuGia implements NguoiQuanSat {
    private final Map<String, List<double[]>> lichSu = new HashMap<>(); // [timestamp, gia]

    @Override
    public void capNhat(String maCK, double giaCu, double giaMoi) {
        lichSu.computeIfAbsent(maCK, k -> new ArrayList<>())
              .add(new double[]{System.currentTimeMillis(), giaMoi});
    }

    public void inLichSu(String maCK) {
        System.out.println("Lịch sử giá " + maCK + ":");
        lichSu.getOrDefault(maCK, List.of()).forEach(entry ->
            System.out.printf("  [%tT] %,.0f%n", (long) entry[0], entry[1]));
    }
}
```

---

## Bài 4: Builder - SQL Query Builder

```java
public class SQLQueryBuilder {
    private final List<String> columns = new ArrayList<>();
    private String table;
    private final List<String> joins = new ArrayList<>();
    private final List<String> conditions = new ArrayList<>();
    private final List<Object> parameters = new ArrayList<>();
    private final List<String> groupByColumns = new ArrayList<>();
    private String havingClause;
    private String orderByClause;
    private Integer limitValue;

    public SQLQueryBuilder select(String... cols) {
        columns.addAll(Arrays.asList(cols));
        return this;
    }

    public SQLQueryBuilder from(String table) {
        this.table = table;
        return this;
    }

    public enum JoinType { INNER, LEFT, RIGHT, FULL }

    public SQLQueryBuilder join(JoinType type, String table, String on) {
        joins.add(type.name() + " JOIN " + table + " ON " + on);
        return this;
    }

    public SQLQueryBuilder where(String condition, Object... params) {
        conditions.add(condition);
        parameters.addAll(Arrays.asList(params));
        return this;
    }

    public SQLQueryBuilder and(String condition, Object... params) {
        conditions.add("AND " + condition);
        parameters.addAll(Arrays.asList(params));
        return this;
    }

    public SQLQueryBuilder or(String condition, Object... params) {
        conditions.add("OR " + condition);
        parameters.addAll(Arrays.asList(params));
        return this;
    }

    public SQLQueryBuilder groupBy(String... cols) {
        groupByColumns.addAll(Arrays.asList(cols));
        return this;
    }

    public SQLQueryBuilder having(String condition, Object... params) {
        this.havingClause = condition;
        parameters.addAll(Arrays.asList(params));
        return this;
    }

    public SQLQueryBuilder orderBy(String clause) {
        this.orderByClause = clause;
        return this;
    }

    public SQLQueryBuilder limit(int limit) {
        this.limitValue = limit;
        return this;
    }

    public String build() {
        validate();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(String.join(", ", columns));
        sql.append(" FROM ").append(table);
        joins.forEach(j -> sql.append(" ").append(j));
        if (!conditions.isEmpty()) {
            sql.append(" WHERE ").append(conditions.get(0));
            for (int i = 1; i < conditions.size(); i++) {
                sql.append(" ").append(conditions.get(i));
            }
        }
        if (!groupByColumns.isEmpty())
            sql.append(" GROUP BY ").append(String.join(", ", groupByColumns));
        if (havingClause != null)
            sql.append(" HAVING ").append(havingClause);
        if (orderByClause != null)
            sql.append(" ORDER BY ").append(orderByClause);
        if (limitValue != null)
            sql.append(" LIMIT ").append(limitValue);
        return sql.toString();
    }

    public List<Object> getParameters() { return Collections.unmodifiableList(parameters); }

    private void validate() {
        if (columns.isEmpty()) throw new IllegalStateException("SELECT không được rỗng");
        if (table == null) throw new IllegalStateException("FROM không được rỗng");
    }

    public static void main(String[] args) {
        SQLQueryBuilder qb = new SQLQueryBuilder();
        String sql = qb.select("nv.ten", "pb.tenPhongBan", "AVG(nv.luong)")
            .from("nhan_vien nv")
            .join(JoinType.INNER, "phong_ban pb", "nv.phong_ban_id = pb.id")
            .where("nv.tuoi > ?", 25)
            .and("pb.tenPhongBan IN (?, ?)", "IT", "HR")
            .groupBy("pb.tenPhongBan")
            .having("AVG(nv.luong) > ?", 15000000)
            .orderBy("AVG(nv.luong) DESC")
            .limit(10)
            .build();

        System.out.println("SQL: " + sql);
        System.out.println("Params: " + qb.getParameters());
    }
}
```

---

## Bài 5-7: Xem đáp án đầy đủ trong ứng dụng web

> Các bài 5, 6, 7 có đáp án đầy đủ trong ứng dụng web. Truy cập phần "Design Patterns" trên giao diện web để xem chi tiết.
