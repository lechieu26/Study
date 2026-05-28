# Java - Lý Thuyết Từ Cơ Bản Đến Nâng Cao

## Phần 1: Java Cơ Bản

### 1.1 Giới thiệu Java
- Java là ngôn ngữ lập trình hướng đối tượng, đa nền tảng (Write Once, Run Anywhere).
- JVM (Java Virtual Machine): Máy ảo Java chịu trách nhiệm thực thi bytecode.
- JDK (Java Development Kit): Bộ công cụ phát triển bao gồm JRE + compiler + tools.
- JRE (Java Runtime Environment): Môi trường chạy chương trình Java.

### 1.2 Kiểu dữ liệu
**Kiểu nguyên thủy (Primitive Types):**
| Kiểu | Kích thước | Phạm vi |
|------|-----------|---------|
| byte | 1 byte | -128 đến 127 |
| short | 2 bytes | -32,768 đến 32,767 |
| int | 4 bytes | -2^31 đến 2^31-1 |
| long | 8 bytes | -2^63 đến 2^63-1 |
| float | 4 bytes | ±3.4e38 (7 chữ số có nghĩa) |
| double | 8 bytes | ±1.7e308 (15 chữ số có nghĩa) |
| char | 2 bytes | Unicode (0 đến 65,535) |
| boolean | 1 bit | true hoặc false |

**Kiểu tham chiếu (Reference Types):** String, Array, Class, Interface, Enum.

### 1.3 Toán tử
- **Số học:** `+`, `-`, `*`, `/`, `%`
- **So sánh:** `==`, `!=`, `>`, `<`, `>=`, `<=`
- **Logic:** `&&`, `||`, `!`
- **Bit:** `&`, `|`, `^`, `~`, `<<`, `>>`, `>>>`
- **Gán:** `=`, `+=`, `-=`, `*=`, `/=`, `%=`
- **Tam nguyên:** `điều_kiện ? giá_trị_đúng : giá_trị_sai`

### 1.4 Cấu trúc điều khiển
```java
// if-else
if (điều_kiện) {
    // thực thi nếu đúng
} else if (điều_kiện_khác) {
    // thực thi nếu điều kiện khác đúng
} else {
    // thực thi nếu tất cả sai
}

// switch-case (Java 14+ hỗ trợ switch expression)
switch (biến) {
    case giá_trị_1 -> System.out.println("Giá trị 1");
    case giá_trị_2 -> System.out.println("Giá trị 2");
    default -> System.out.println("Mặc định");
}

// Vòng lặp
for (int i = 0; i < n; i++) { }
for (Type item : collection) { }  // for-each
while (điều_kiện) { }
do { } while (điều_kiện);
```

### 1.5 Mảng (Array)
```java
// Khai báo và khởi tạo
int[] arr = new int[5];
int[] arr2 = {1, 2, 3, 4, 5};
int[][] matrix = new int[3][4]; // Mảng 2 chiều

// Duyệt mảng
Arrays.sort(arr);           // Sắp xếp
Arrays.binarySearch(arr, 3); // Tìm kiếm nhị phân
Arrays.copyOf(arr, newLen);  // Sao chép
Arrays.fill(arr, value);    // Điền giá trị
```

### 1.6 Xử lý chuỗi (String)
```java
String s = "Xin chào";
s.length();           // Độ dài
s.charAt(0);          // Ký tự tại vị trí
s.substring(0, 3);    // Cắt chuỗi
s.indexOf("chào");    // Vị trí xuất hiện
s.contains("chào");   // Kiểm tra chứa
s.replace("a", "b");  // Thay thế
s.split(" ");          // Tách chuỗi
s.trim();              // Xóa khoảng trắng
s.toUpperCase();       // Chuyển hoa
s.toLowerCase();       // Chuyển thường

// StringBuilder - hiệu quả khi nối chuỗi nhiều lần
StringBuilder sb = new StringBuilder();
sb.append("Hello");
sb.insert(0, "World");
sb.reverse();
sb.toString();
```

---

## Phần 2: Lập Trình Hướng Đối Tượng (OOP)

### 2.1 Bốn tính chất OOP

**Đóng gói (Encapsulation):**
- Ẩn dữ liệu bên trong đối tượng, chỉ cho phép truy cập qua getter/setter.
- Access modifiers: `private`, `default`, `protected`, `public`.

```java
public class NhanVien {
    private String ten;
    private double luong;

    public String getTen() { return ten; }
    public void setLuong(double luong) {
        if (luong > 0) this.luong = luong;
    }
}
```

**Kế thừa (Inheritance):**
- Lớp con thừa hưởng thuộc tính và phương thức từ lớp cha.
- Java chỉ hỗ trợ đơn kế thừa (single inheritance) cho class.

```java
public class NhanVienFullTime extends NhanVien {
    private double thuong;

    @Override
    public double tinhLuong() {
        return super.getLuong() + thuong;
    }
}
```

**Đa hình (Polymorphism):**
- **Compile-time (Overloading):** Cùng tên phương thức, khác tham số.
- **Runtime (Overriding):** Lớp con ghi đè phương thức lớp cha.

```java
// Overloading
public int tinhTong(int a, int b) { return a + b; }
public double tinhTong(double a, double b) { return a + b; }

// Overriding + Polymorphism
NhanVien nv = new NhanVienFullTime(); // Upcasting
nv.tinhLuong(); // Gọi phương thức của NhanVienFullTime
```

**Trừu tượng (Abstraction):**
- Abstract class: Lớp chứa phương thức trừu tượng, không thể tạo instance.
- Interface: Định nghĩa hành vi (contract) mà class phải implement.

```java
public abstract class HinhHoc {
    abstract double tinhDienTich();
    abstract double tinhChuVi();
}

public interface CoTheTinh {
    double tinhDienTich();
    double tinhChuVi();
}
```

### 2.2 Interface vs Abstract Class
| Đặc điểm | Interface | Abstract Class |
|-----------|-----------|---------------|
| Đa kế thừa | Có (implements nhiều) | Không (extends 1) |
| Constructor | Không | Có |
| Biến | public static final | Mọi loại |
| Phương thức | abstract, default, static | Mọi loại |
| Khi nào dùng | Định nghĩa hành vi chung | Chia sẻ code giữa các lớp liên quan |

---

## Phần 3: Java Collections Framework

### 3.1 Cấu trúc Collections
```
Collection (Interface)
├── List (Interface)
│   ├── ArrayList   - Mảng động, truy cập nhanh O(1)
│   ├── LinkedList  - Danh sách liên kết, thêm/xóa nhanh O(1)
│   └── Vector      - Thread-safe ArrayList (ít dùng)
├── Set (Interface)
│   ├── HashSet     - Không thứ tự, O(1) add/remove/contains
│   ├── LinkedHashSet - Giữ thứ tự thêm vào
│   └── TreeSet     - Sắp xếp tự nhiên, O(log n)
└── Queue (Interface)
    ├── PriorityQueue - Hàng đợi ưu tiên
    ├── ArrayDeque    - Deque hiệu quả
    └── LinkedList    - Cũng implement Queue

Map (Interface) - Không thuộc Collection
├── HashMap     - O(1) get/put, không thứ tự
├── LinkedHashMap - Giữ thứ tự thêm vào
├── TreeMap     - Sắp xếp theo key, O(log n)
├── Hashtable   - Thread-safe (ít dùng)
└── ConcurrentHashMap - Thread-safe hiện đại
```

### 3.2 So sánh ArrayList vs LinkedList
| Thao tác | ArrayList | LinkedList |
|----------|-----------|------------|
| get(index) | O(1) | O(n) |
| add(cuối) | O(1) amortized | O(1) |
| add(đầu/giữa) | O(n) | O(1) |
| remove | O(n) | O(1) |
| Bộ nhớ | Ít hơn | Nhiều hơn (node + pointer) |

### 3.3 Comparable vs Comparator
```java
// Comparable - sắp xếp tự nhiên (trong class)
public class SinhVien implements Comparable<SinhVien> {
    @Override
    public int compareTo(SinhVien other) {
        return this.ten.compareTo(other.ten);
    }
}

// Comparator - sắp xếp tùy chỉnh (bên ngoài class)
Collections.sort(list, (a, b) -> Double.compare(a.getDiem(), b.getDiem()));
Collections.sort(list, Comparator.comparing(SinhVien::getDiem).reversed());
```

---

## Phần 4: Java Nâng Cao

### 4.1 Generics
```java
// Generic class
public class Hop<T> {
    private T giaTri;
    public T layGiaTri() { return giaTri; }
    public void datGiaTri(T giaTri) { this.giaTri = giaTri; }
}

// Bounded type
public <T extends Comparable<T>> T timMax(T a, T b) {
    return a.compareTo(b) > 0 ? a : b;
}

// Wildcard
public void inDanhSach(List<?> list) { }           // Unbounded
public void tinhTong(List<? extends Number> list) { } // Upper bound
public void themPhanTu(List<? super Integer> list) { } // Lower bound
```

### 4.2 Lambda và Functional Interface
```java
// Functional Interface
@FunctionalInterface
public interface PhepTinh {
    double tinh(double a, double b);
}

// Lambda expression
PhepTinh cong = (a, b) -> a + b;
PhepTinh nhan = (a, b) -> a * b;

// Built-in Functional Interfaces
Predicate<String> isEmpty = s -> s.isEmpty();
Function<String, Integer> toLength = String::length;
Consumer<String> print = System.out::println;
Supplier<String> hello = () -> "Xin chào";
BiFunction<Integer, Integer, Integer> sum = Integer::sum;
```

### 4.3 Stream API
```java
List<String> names = List.of("An", "Bình", "Cường", "Dũng", "An");

// Các thao tác trung gian (Intermediate)
names.stream()
    .filter(s -> s.length() > 2)      // Lọc
    .map(String::toUpperCase)          // Biến đổi
    .sorted()                          // Sắp xếp
    .distinct()                        // Loại bỏ trùng
    .limit(3)                          // Giới hạn
    .skip(1)                           // Bỏ qua
    .peek(System.out::println)         // Debug

// Các thao tác kết thúc (Terminal)
    .collect(Collectors.toList());     // Thu thập
    .forEach(System.out::println);     // Duyệt
    .count();                          // Đếm
    .findFirst();                      // Phần tử đầu
    .anyMatch(s -> s.startsWith("A")); // Kiểm tra
    .reduce("", String::concat);       // Gộp

// Collectors nâng cao
Map<Integer, List<String>> grouped =
    names.stream().collect(Collectors.groupingBy(String::length));

String joined = names.stream().collect(Collectors.joining(", "));

DoubleSummaryStatistics stats =
    numbers.stream().collect(Collectors.summarizingDouble(x -> x));
```

### 4.4 Optional
```java
Optional<String> opt = Optional.ofNullable(value);
opt.isPresent();                      // Kiểm tra có giá trị
opt.ifPresent(System.out::println);   // Thực thi nếu có
opt.orElse("Mặc định");              // Giá trị mặc định
opt.orElseThrow(() -> new RuntimeException("Không tìm thấy"));
opt.map(String::toUpperCase);         // Biến đổi
opt.flatMap(this::timKiem);           // Biến đổi Optional
opt.filter(s -> s.length() > 5);      // Lọc
```

### 4.5 Xử lý ngoại lệ (Exception Handling)
```
Throwable
├── Error (Lỗi hệ thống - không bắt)
│   ├── OutOfMemoryError
│   └── StackOverflowError
└── Exception
    ├── Checked Exception (phải xử lý)
    │   ├── IOException
    │   ├── SQLException
    │   └── ClassNotFoundException
    └── RuntimeException (Unchecked - không bắt buộc)
        ├── NullPointerException
        ├── ArrayIndexOutOfBoundsException
        ├── IllegalArgumentException
        └── NumberFormatException
```

```java
// try-with-resources (Java 7+)
try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
    String line = reader.readLine();
} catch (FileNotFoundException e) {
    System.err.println("Không tìm thấy tệp: " + e.getMessage());
} catch (IOException e) {
    System.err.println("Lỗi đọc tệp: " + e.getMessage());
} finally {
    System.out.println("Luôn thực thi");
}

// Custom Exception
public class KhongDuSoDuException extends Exception {
    public KhongDuSoDuException(double soDu, double soTien) {
        super("Số dư " + soDu + " không đủ để rút " + soTien);
    }
}
```

### 4.6 Multi-threading
```java
// Cách 1: Extends Thread
class MyThread extends Thread {
    @Override
    public void run() { System.out.println("Thread đang chạy"); }
}

// Cách 2: Implements Runnable
Runnable task = () -> System.out.println("Task đang chạy");
new Thread(task).start();

// ExecutorService (khuyến khích)
ExecutorService executor = Executors.newFixedThreadPool(4);
Future<Integer> future = executor.submit(() -> {
    Thread.sleep(1000);
    return 42;
});
int result = future.get(); // Blocking

// CompletableFuture (Java 8+)
CompletableFuture.supplyAsync(() -> layDuLieu())
    .thenApply(data -> xuLy(data))
    .thenAccept(result -> hienThi(result))
    .exceptionally(ex -> { log(ex); return null; });

// Synchronized
public synchronized void rutTien(double soTien) {
    if (soDu >= soTien) soDu -= soTien;
}

// ReentrantLock
private final ReentrantLock lock = new ReentrantLock();
public void rutTien(double soTien) {
    lock.lock();
    try {
        if (soDu >= soTien) soDu -= soTien;
    } finally {
        lock.unlock();
    }
}
```

### 4.7 Java I/O và NIO
```java
// Đọc/ghi file với NIO (Java 7+)
Path path = Path.of("data.txt");
String content = Files.readString(path);
Files.writeString(path, "Nội dung mới");
List<String> lines = Files.readAllLines(path);
Files.write(path, lines);

// Stream file lớn
try (Stream<String> stream = Files.lines(path)) {
    stream.filter(line -> line.contains("Java"))
          .forEach(System.out::println);
}

// Serialization
public class SinhVien implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient String matKhau; // Không serialize
}
```

### 4.8 Java Records (Java 16+)
```java
public record Diem(double x, double y) {
    // Compact constructor
    public Diem {
        if (x < 0 || y < 0) throw new IllegalArgumentException("Tọa độ âm");
    }

    public double khoangCach(Diem other) {
        return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
    }
}
```

### 4.9 Sealed Classes (Java 17)
```java
public sealed class HinhHoc permits HinhTron, HinhVuong, HinhChuNhat {
    abstract double tinhDienTich();
}

public final class HinhTron extends HinhHoc { }
public final class HinhVuong extends HinhHoc { }
public non-sealed class HinhChuNhat extends HinhHoc { }
```

### 4.10 Pattern Matching (Java 16+)
```java
// instanceof pattern matching
if (obj instanceof String s && s.length() > 5) {
    System.out.println(s.toUpperCase());
}

// Switch pattern matching (Java 21)
String moTa = switch (hinhHoc) {
    case HinhTron h -> "Hình tròn bán kính " + h.getBanKinh();
    case HinhVuong h -> "Hình vuông cạnh " + h.getCanh();
    case null -> "Null";
    default -> "Không xác định";
};
```
