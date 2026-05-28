# Java - Đáp Án Chi Tiết

## Bài 1: Kiểm tra Palindrome

### Cách 1: Sử dụng Java thuần (Two Pointers)
```java
public class Bai1_Cach1 {
    public static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome("A man, a plan, a canal: Panama")); // true
        System.out.println(isPalindrome("race a car")); // false
    }
}
```

### Cách 2: Sử dụng StringBuilder
```java
public class Bai1_Cach2 {
    public static boolean isPalindrome(String s) {
        String cleaned = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        return cleaned.equals(new StringBuilder(cleaned).reverse().toString());
    }
}
```

### Cách 3: Sử dụng Stream API
```java
public class Bai1_Cach3 {
    public static boolean isPalindrome(String s) {
        String cleaned = s.chars()
            .filter(Character::isLetterOrDigit)
            .map(Character::toLowerCase)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
        return cleaned.contentEquals(new StringBuilder(cleaned).reverse());
    }
}
```

---

## Bài 2: Majority Element

### Cách 1: HashMap đếm tần suất
```java
public class Bai2_Cach1 {
    public static int majorityElement(int[] nums) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int num : nums) {
            counts.merge(num, 1, Integer::sum);
            if (counts.get(num) > nums.length / 2) return num;
        }
        return -1;
    }
}
```

### Cách 2: Boyer-Moore Voting Algorithm (tối ưu O(1) bộ nhớ)
```java
public class Bai2_Cach2 {
    public static int majorityElement(int[] nums) {
        int candidate = nums[0], count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (count == 0) {
                candidate = nums[i];
                count = 1;
            } else if (nums[i] == candidate) {
                count++;
            } else {
                count--;
            }
        }
        return candidate;
    }
}
```

### Cách 3: Sắp xếp và lấy phần tử giữa
```java
public class Bai2_Cach3 {
    public static int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }
}
```

---

## Bài 3: Nhóm Anagram

### Cách 1: Sắp xếp ký tự làm key
```java
public class Bai3_Cach1 {
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
    }
}
```

### Cách 2: Đếm ký tự làm key (tối ưu hơn)
```java
public class Bai3_Cach2 {
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            int[] count = new int[26];
            for (char c : s.toCharArray()) count[c - 'a']++;
            String key = Arrays.toString(count);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
    }
}
```

### Cách 3: Stream API
```java
public class Bai3_Cach3 {
    public static List<List<String>> groupAnagrams(String[] strs) {
        return new ArrayList<>(
            Arrays.stream(strs)
                .collect(Collectors.groupingBy(s -> {
                    char[] chars = s.toCharArray();
                    Arrays.sort(chars);
                    return new String(chars);
                }))
                .values()
        );
    }
}
```

---

## Bài 4: Hệ Thống Quản Lý Sinh Viên

```java
// SinhVien.java
public class SinhVien {
    private String maSV;
    private String hoTen;
    private List<Double> diem;

    public SinhVien(String maSV, String hoTen, List<Double> diem) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.diem = new ArrayList<>(diem);
    }

    public double diemTrungBinh() {
        return diem.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }

    // Getters, setters, toString...
    public String getMaSV() { return maSV; }
    public String getHoTen() { return hoTen; }
    public List<Double> getDiem() { return diem; }

    @Override
    public String toString() {
        return String.format("SV[%s, %s, TB=%.2f]", maSV, hoTen, diemTrungBinh());
    }
}

// DanhSachQuanLy.java
public interface DanhSachQuanLy<T> {
    void them(T item);
    boolean xoa(String id);
    Optional<T> timKiem(String keyword);
    List<T> sapXep(Comparator<T> comparator);
}

// DanhSachSinhVien.java
public class DanhSachSinhVien implements DanhSachQuanLy<SinhVien> {
    private final List<SinhVien> danhSach = new ArrayList<>();

    @Override
    public void them(SinhVien sv) {
        if (danhSach.stream().anyMatch(s -> s.getMaSV().equals(sv.getMaSV()))) {
            throw new IllegalArgumentException("Mã SV đã tồn tại: " + sv.getMaSV());
        }
        danhSach.add(sv);
    }

    @Override
    public boolean xoa(String maSV) {
        return danhSach.removeIf(sv -> sv.getMaSV().equals(maSV));
    }

    @Override
    public Optional<SinhVien> timKiem(String keyword) {
        return danhSach.stream()
            .filter(sv -> sv.getMaSV().equalsIgnoreCase(keyword)
                       || sv.getHoTen().toLowerCase().contains(keyword.toLowerCase()))
            .findFirst();
    }

    public List<SinhVien> timKiemTatCa(String keyword) {
        return danhSach.stream()
            .filter(sv -> sv.getMaSV().equalsIgnoreCase(keyword)
                       || sv.getHoTen().toLowerCase().contains(keyword.toLowerCase()))
            .collect(Collectors.toList());
    }

    @Override
    public List<SinhVien> sapXep(Comparator<SinhVien> comparator) {
        return danhSach.stream().sorted(comparator).collect(Collectors.toList());
    }

    public List<SinhVien> locDat() {
        return danhSach.stream()
            .filter(sv -> sv.diemTrungBinh() >= 5.0)
            .collect(Collectors.toList());
    }

    public List<SinhVien> locKhongDat() {
        return danhSach.stream()
            .filter(sv -> sv.diemTrungBinh() < 5.0)
            .collect(Collectors.toList());
    }

    public DoubleSummaryStatistics thongKe() {
        return danhSach.stream()
            .mapToDouble(SinhVien::diemTrungBinh)
            .summaryStatistics();
    }

    public Map<String, List<SinhVien>> nhomTheoXepLoai() {
        return danhSach.stream().collect(Collectors.groupingBy(sv -> {
            double tb = sv.diemTrungBinh();
            if (tb >= 8.5) return "Giỏi";
            if (tb >= 7.0) return "Khá";
            if (tb >= 5.0) return "Trung bình";
            return "Yếu";
        }));
    }

    public static void main(String[] args) {
        DanhSachSinhVien ds = new DanhSachSinhVien();
        ds.them(new SinhVien("SV001", "Nguyễn Văn An", List.of(8.5, 7.0, 9.0)));
        ds.them(new SinhVien("SV002", "Trần Thị Bình", List.of(6.0, 5.5, 4.0)));
        ds.them(new SinhVien("SV003", "Lê Văn Cường", List.of(9.0, 8.5, 9.5)));

        System.out.println("=== Sắp xếp theo điểm TB giảm dần ===");
        ds.sapXep(Comparator.comparing(SinhVien::diemTrungBinh).reversed())
          .forEach(System.out::println);

        System.out.println("\n=== Thống kê ===");
        DoubleSummaryStatistics stats = ds.thongKe();
        System.out.printf("Cao nhất: %.2f, Thấp nhất: %.2f, TB lớp: %.2f%n",
            stats.getMax(), stats.getMin(), stats.getAverage());

        System.out.println("\n=== Nhóm theo xếp loại ===");
        ds.nhomTheoXepLoai().forEach((loai, svList) ->
            System.out.println(loai + ": " + svList));
    }
}
```

---

## Bài 5: Thread-Safe Bounded Queue

```java
import java.util.LinkedList;
import java.util.concurrent.locks.*;

public class BoundedQueue<T> {
    private final LinkedList<T> queue = new LinkedList<>();
    private final int capacity;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public BoundedQueue(int capacity) {
        this.capacity = capacity;
    }

    public void enqueue(T item) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                notFull.await();
            }
            queue.addLast(item);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T dequeue() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await();
            }
            T item = queue.removeFirst();
            notFull.signal();
            return item;
        } finally {
            lock.unlock();
        }
    }

    public T peek() {
        lock.lock();
        try {
            return queue.peek();
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }

    // Test Producer-Consumer
    public static void main(String[] args) {
        BoundedQueue<Integer> bq = new BoundedQueue<>(5);

        // Producers
        for (int i = 0; i < 3; i++) {
            final int producerId = i;
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        int value = producerId * 100 + j;
                        bq.enqueue(value);
                        System.out.printf("Producer %d: thêm %d (size=%d)%n",
                            producerId, value, bq.size());
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }).start();
        }

        // Consumers
        for (int i = 0; i < 2; i++) {
            final int consumerId = i;
            new Thread(() -> {
                for (int j = 0; j < 15; j++) {
                    try {
                        int value = bq.dequeue();
                        System.out.printf("Consumer %d: lấy %d (size=%d)%n",
                            consumerId, value, bq.size());
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }).start();
        }
    }
}
```

---

## Bài 6: Stream API Nâng Cao

```java
import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;

public class StreamNangCao {
    record NhanVien(int id, String ten, String phongBan, double luong, LocalDate ngayVaoLam) {}

    public static void main(String[] args) {
        List<NhanVien> nvList = List.of(
            new NhanVien(1, "An", "IT", 15_000_000, LocalDate.of(2018, 3, 1)),
            new NhanVien(2, "Bình", "IT", 22_000_000, LocalDate.of(2015, 6, 15)),
            new NhanVien(3, "Cường", "HR", 12_000_000, LocalDate.of(2020, 1, 10)),
            new NhanVien(4, "Dũng", "HR", 8_000_000, LocalDate.of(2022, 7, 1)),
            new NhanVien(5, "Em", "Sales", 25_000_000, LocalDate.of(2016, 11, 20)),
            new NhanVien(6, "Phúc", "Sales", 18_000_000, LocalDate.of(2019, 4, 5))
        );

        // 1. Nhân viên có lương cao nhất mỗi phòng ban
        Map<String, Optional<NhanVien>> maxLuong = nvList.stream()
            .collect(Collectors.groupingBy(NhanVien::phongBan,
                Collectors.maxBy(Comparator.comparing(NhanVien::luong))));
        System.out.println("1. Lương cao nhất mỗi PB: " + maxLuong);

        // 2. Tổng lương theo phòng ban
        Map<String, Double> tongLuong = nvList.stream()
            .collect(Collectors.groupingBy(NhanVien::phongBan,
                Collectors.summingDouble(NhanVien::luong)));
        System.out.println("2. Tổng lương: " + tongLuong);

        // 3. Nhóm theo khoảng lương
        Map<String, List<NhanVien>> nhomLuong = nvList.stream()
            .collect(Collectors.groupingBy(nv -> {
                if (nv.luong() < 10_000_000) return "Dưới 10 triệu";
                if (nv.luong() <= 20_000_000) return "10-20 triệu";
                return "Trên 20 triệu";
            }));
        System.out.println("3. Nhóm lương: " + nhomLuong);

        // 4. Phòng ban có lương trung bình cao nhất
        Optional<Map.Entry<String, Double>> pbLuongCaoNhat = nvList.stream()
            .collect(Collectors.groupingBy(NhanVien::phongBan,
                Collectors.averagingDouble(NhanVien::luong)))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue());
        System.out.println("4. PB lương TB cao nhất: " + pbLuongCaoNhat);

        // 5. Top 3 thâm niên lâu nhất
        List<NhanVien> top3ThamNien = nvList.stream()
            .sorted(Comparator.comparing(NhanVien::ngayVaoLam))
            .limit(3)
            .collect(Collectors.toList());
        System.out.println("5. Top 3 thâm niên: " + top3ThamNien);

        // 6. Báo cáo
        String baoCao = nvList.stream()
            .collect(Collectors.groupingBy(NhanVien::phongBan))
            .entrySet().stream()
            .map(e -> {
                String tenNVs = e.getValue().stream()
                    .map(NhanVien::ten).collect(Collectors.joining(", "));
                double tong = e.getValue().stream()
                    .mapToDouble(NhanVien::luong).sum();
                return String.format("%s: [%s] - Tổng lương: %,.0f", e.getKey(), tenNVs, tong);
            })
            .collect(Collectors.joining("\n"));
        System.out.println("6. Báo cáo:\n" + baoCao);
    }
}
```

---

## Bài 7: Hệ Thống Ngân Hàng (Tóm tắt)

```java
// Custom Exceptions
public class SoDuKhongDuException extends Exception {
    public SoDuKhongDuException(double soDu, double soTien) {
        super(String.format("Số dư %.0f không đủ để rút %.0f", soDu, soTien));
    }
}

public class TaiKhoanKhongTonTaiException extends Exception {
    public TaiKhoanKhongTonTaiException(String soTK) {
        super("Tài khoản không tồn tại: " + soTK);
    }
}

// GiaoDich.java
public record GiaoDich(LocalDateTime thoiGian, String loai, double soTien, double soDuConLai) {
    @Override
    public String toString() {
        return String.format("[%s] %s: %,.0f | Số dư: %,.0f",
            thoiGian.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
            loai, soTien, soDuConLai);
    }
}

// TaiKhoanNganHang.java
public class TaiKhoanNganHang {
    private final String soTaiKhoan;
    private final String chuTaiKhoan;
    private double soDu;
    private final List<GiaoDich> lichSu = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();

    public TaiKhoanNganHang(String soTaiKhoan, String chuTaiKhoan, double soDuBanDau) {
        this.soTaiKhoan = soTaiKhoan;
        this.chuTaiKhoan = chuTaiKhoan;
        this.soDu = soDuBanDau;
    }

    public void guiTien(double soTien) {
        lock.lock();
        try {
            if (soTien <= 0) throw new IllegalArgumentException("Số tiền phải > 0");
            soDu += soTien;
            lichSu.add(new GiaoDich(LocalDateTime.now(), "GỬI", soTien, soDu));
        } finally {
            lock.unlock();
        }
    }

    public void rutTien(double soTien) throws SoDuKhongDuException {
        lock.lock();
        try {
            if (soTien <= 0) throw new IllegalArgumentException("Số tiền phải > 0");
            if (soDu < soTien) throw new SoDuKhongDuException(soDu, soTien);
            soDu -= soTien;
            lichSu.add(new GiaoDich(LocalDateTime.now(), "RÚT", soTien, soDu));
        } finally {
            lock.unlock();
        }
    }

    public ReentrantLock getLock() { return lock; }
    public String getSoTaiKhoan() { return soTaiKhoan; }
    public double getSoDu() { return soDu; }
    public void setSoDu(double soDu) { this.soDu = soDu; }
    public List<GiaoDich> getLichSu() { return lichSu; }
}

// NganHang.java
public class NganHang {
    private final Map<String, TaiKhoanNganHang> danhSachTK = new ConcurrentHashMap<>();

    public void taoTaiKhoan(String soTK, String chuTK, double soDu) {
        danhSachTK.put(soTK, new TaiKhoanNganHang(soTK, chuTK, soDu));
    }

    public void chuyenKhoan(String tuTK, String denTK, double soTien)
            throws TaiKhoanKhongTonTaiException, SoDuKhongDuException {
        TaiKhoanNganHang nguoi = Optional.ofNullable(danhSachTK.get(tuTK))
            .orElseThrow(() -> new TaiKhoanKhongTonTaiException(tuTK));
        TaiKhoanNganHang nhan = Optional.ofNullable(danhSachTK.get(denTK))
            .orElseThrow(() -> new TaiKhoanKhongTonTaiException(denTK));

        // Tránh deadlock bằng cách lock theo thứ tự soTK
        TaiKhoanNganHang first = tuTK.compareTo(denTK) < 0 ? nguoi : nhan;
        TaiKhoanNganHang second = tuTK.compareTo(denTK) < 0 ? nhan : nguoi;

        first.getLock().lock();
        try {
            second.getLock().lock();
            try {
                nguoi.rutTien(soTien);
                nhan.guiTien(soTien);
            } finally {
                second.getLock().unlock();
            }
        } finally {
            first.getLock().unlock();
        }
    }
}
```

---

## Bài 8-10: Xem code đầy đủ trong ứng dụng web

> Các bài 8, 9, 10 có đáp án đầy đủ trong ứng dụng web. Truy cập phần "Java" trên giao diện web để xem chi tiết và thực hành.
