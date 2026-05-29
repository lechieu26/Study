# Java - Lý Thuyết Từ Cơ Bản Đến Nâng Cao

## Phần 1: Java Cơ Bản

### 1.1 Giới thiệu Java

Java là ngôn ngữ lập trình hướng đối tượng, đa nền tảng, được thiết kế với triết lý **"Write Once, Run Anywhere"** (WORA).

**Kiến trúc Java Platform:**

| Thành phần | Mô tả | Vai trò |
|-----------|-------|---------|
| JDK | Java Development Kit | Bộ công cụ phát triển: JRE + compiler (`javac`) + debugger + tools |
| JRE | Java Runtime Environment | Môi trường chạy: JVM + thư viện chuẩn |
| JVM | Java Virtual Machine | Máy ảo thực thi bytecode, quản lý bộ nhớ (GC) |
| JIT | Just-In-Time Compiler | Biên dịch bytecode → mã máy khi chạy, tối ưu hiệu năng |

**Quy trình biên dịch và thực thi:**
```
Source (.java) → javac → Bytecode (.class) → JVM (JIT) → Mã máy → CPU
```

> **Phỏng vấn thường hỏi:** Sự khác biệt giữa JDK, JRE và JVM? Tại sao Java là "platform independent"? — Bytecode chạy trên JVM, mà JVM có phiên bản cho mỗi hệ điều hành.

**Đặc điểm nổi bật của Java:**
- **Strongly Typed:** Mọi biến phải khai báo kiểu, giúp phát hiện lỗi ngay lúc biên dịch.
- **Automatic Memory Management:** Garbage Collector tự động thu hồi bộ nhớ.
- **Multi-threaded:** Hỗ trợ đa luồng từ tầng ngôn ngữ (keyword `synchronized`, `volatile`).
- **Rich Standard Library:** Hàng nghìn class/interface cho I/O, networking, collections, concurrency.
- **Backward Compatible:** Code Java 8 vẫn chạy trên JVM 17+.

### 1.2 Kiểu dữ liệu

**Kiểu nguyên thủy (Primitive Types):**

| Kiểu | Kích thước | Giá trị mặc định | Phạm vi | Wrapper Class |
|------|-----------|------------------|---------|--------------|
| `byte` | 1 byte | 0 | -128 đến 127 | `Byte` |
| `short` | 2 bytes | 0 | -32,768 đến 32,767 | `Short` |
| `int` | 4 bytes | 0 | -2^31 đến 2^31-1 (~2.1 tỷ) | `Integer` |
| `long` | 8 bytes | 0L | -2^63 đến 2^63-1 | `Long` |
| `float` | 4 bytes | 0.0f | ±3.4e38 (7 chữ số có nghĩa) | `Float` |
| `double` | 8 bytes | 0.0d | ±1.7e308 (15 chữ số có nghĩa) | `Double` |
| `char` | 2 bytes | '\u0000' | Unicode 0 đến 65,535 | `Character` |
| `boolean` | ~1 byte | false | `true` hoặc `false` | `Boolean` |

> **Lưu ý quan trọng:** Autoboxing/Unboxing tự động chuyển giữa primitive ↔ wrapper. Nhưng cẩn thận với `Integer` cache: `Integer.valueOf(127) == Integer.valueOf(127)` là `true`, nhưng `Integer.valueOf(128) == Integer.valueOf(128)` là `false` (so sánh reference). **Luôn dùng `.equals()` cho wrapper types.**

**Kiểu tham chiếu (Reference Types):**
- **String** — Immutable, lưu trong String Pool
- **Array** — Kích thước cố định, truy cập O(1)
- **Class/Object** — Mọi class đều kế thừa từ `Object`
- **Interface** — Hợp đồng (contract) cho behavior
- **Enum** — Tập hợp hằng số có tên

**String Immutability — Tại sao quan trọng?**
```java
String s1 = "Hello";    // String Pool
String s2 = "Hello";    // Cùng reference trong Pool
String s3 = new String("Hello"); // Heap riêng

s1 == s2;       // true  (cùng reference trong Pool)
s1 == s3;       // false (khác reference)
s1.equals(s3);  // true  (cùng giá trị)
```

> **Phỏng vấn:** Tại sao String là immutable? — (1) Thread-safe, (2) Hash code được cache, (3) Security (URL, DB connection không bị thay đổi), (4) String Pool tiết kiệm bộ nhớ.

### 1.3 Toán tử

| Nhóm | Toán tử | Ghi chú |
|------|---------|---------|
| Số học | `+`, `-`, `*`, `/`, `%` | `/` giữa 2 int → int (cắt phần thập phân) |
| So sánh | `==`, `!=`, `>`, `<`, `>=`, `<=` | `==` so sánh reference cho Object, dùng `.equals()` cho giá trị |
| Logic | `&&`, `\|\|`, `!` | Short-circuit: `&&` dừng khi gặp false, `\|\|` dừng khi gặp true |
| Bit | `&`, `\|`, `^`, `~`, `<<`, `>>`, `>>>` | `>>>` unsigned right shift (không giữ dấu) |
| Gán | `=`, `+=`, `-=`, `*=`, `/=`, `%=` | `x += 1` tương đương `x = (type)(x + 1)` (có cast ngầm) |
| Tam nguyên | `điều_kiện ? giá_trị_đúng : giá_trị_sai` | Biểu thức, không phải statement |

### 1.4 Cấu trúc điều khiển

```java
// if-else — Kiểm tra điều kiện
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

// Switch expression trả về giá trị (Java 14+)
int days = switch (month) {
    case 2 -> isLeapYear ? 29 : 28;
    case 4, 6, 9, 11 -> 30;
    default -> 31;
};

// Vòng lặp
for (int i = 0; i < n; i++) { }       // for truyền thống
for (Type item : collection) { }        // for-each (iterable)
while (điều_kiện) { }                   // kiểm tra trước
do { } while (điều_kiện);              // thực thi ít nhất 1 lần

// break, continue
// break — thoát vòng lặp hiện tại
// continue — bỏ qua lần lặp hiện tại, chuyển sang lần tiếp
// break label — thoát vòng lặp được đặt tên (labeled break)
```

### 1.5 Mảng (Array)

```java
// Khai báo và khởi tạo
int[] arr = new int[5];                   // mặc định = 0
int[] arr2 = {1, 2, 3, 4, 5};           // khởi tạo trực tiếp
int[][] matrix = new int[3][4];          // Mảng 2 chiều
String[] names = new String[3];          // mặc định = null

// Thao tác phổ biến (java.util.Arrays)
Arrays.sort(arr);                        // Sắp xếp O(n log n)
Arrays.binarySearch(arr, 3);             // Tìm kiếm nhị phân (mảng phải sorted)
Arrays.copyOf(arr, newLen);              // Sao chép với kích thước mới
Arrays.copyOfRange(arr, from, to);       // Sao chép một đoạn
Arrays.fill(arr, value);                 // Điền giá trị
Arrays.equals(arr1, arr2);              // So sánh 2 mảng
Arrays.deepEquals(matrix1, matrix2);     // So sánh mảng đa chiều
Arrays.stream(arr).sum();               // Tính tổng qua Stream
```

> **Best Practice:** Khi cần mảng kích thước động → dùng `ArrayList`. Mảng chỉ nên dùng khi biết trước kích thước hoặc cần hiệu năng tối đa.

### 1.6 Xử lý chuỗi (String)

```java
// Các phương thức String thường dùng
String s = "Xin chào Java";
s.length();              // 14 — Độ dài
s.charAt(0);             // 'X' — Ký tự tại vị trí
s.substring(0, 3);       // "Xin" — Cắt chuỗi [from, to)
s.indexOf("chào");       // 4 — Vị trí xuất hiện đầu tiên (-1 nếu không có)
s.lastIndexOf("a");      // 13 — Vị trí xuất hiện cuối
s.contains("chào");      // true — Kiểm tra chứa
s.startsWith("Xin");     // true
s.endsWith("Java");      // true
s.replace("a", "o");     // "Xin choo Jovo" — Thay thế tất cả
s.replaceAll("\\s+", " "); // Thay thế bằng regex
s.split(" ");             // ["Xin", "chào", "Java"]
s.trim();                 // Xóa khoảng trắng đầu cuối
s.strip();               // (Java 11+) Xóa whitespace Unicode
s.toUpperCase();          // "XIN CHÀO JAVA"
s.toLowerCase();          // "xin chào java"
s.isEmpty();             // false — Kiểm tra rỗng
s.isBlank();             // (Java 11+) false — Kiểm tra rỗng hoặc chỉ whitespace

// StringBuilder — Hiệu quả khi nối chuỗi nhiều lần
StringBuilder sb = new StringBuilder();
sb.append("Hello").append(" ").append("World");
sb.insert(5, ",");       // "Hello, World"
sb.delete(5, 6);         // "Hello World"
sb.reverse();            // "dlroW olleH"
String result = sb.toString();

// String.format & formatted (Java 15+)
String msg = String.format("Tên: %s, Tuổi: %d, Điểm: %.2f", "An", 20, 8.5);
String msg2 = "Tên: %s, Tuổi: %d".formatted("An", 20); // Java 15+
```

> **Performance:** Nối chuỗi trong vòng lặp bằng `+` tạo nhiều object String → dùng `StringBuilder`. Trong môi trường đa luồng → dùng `StringBuffer` (thread-safe nhưng chậm hơn).

---

## Phần 2: Lập Trình Hướng Đối Tượng (OOP)

### 2.1 Bốn tính chất OOP

**Đóng gói (Encapsulation):**

Ẩn dữ liệu bên trong đối tượng, kiểm soát truy cập qua getter/setter.

| Access Modifier | Cùng class | Cùng package | Subclass khác package | Khác package |
|----------------|-----------|-------------|---------------------|-------------|
| `private` | Yes | No | No | No |
| `default` (no modifier) | Yes | Yes | No | No |
| `protected` | Yes | Yes | Yes | No |
| `public` | Yes | Yes | Yes | Yes |

```java
public class NhanVien {
    private String ten;           // Chỉ class này truy cập
    private double luong;

    public String getTen() { return ten; }
    public void setLuong(double luong) {
        if (luong > 0) this.luong = luong;  // Validation trong setter
    }
}
```

> **Phỏng vấn:** Tại sao cần encapsulation? — (1) Kiểm soát dữ liệu (validation), (2) Ẩn implementation detail, (3) Dễ thay đổi nội bộ mà không ảnh hưởng bên ngoài, (4) Immutability.

**Kế thừa (Inheritance):**

Lớp con thừa hưởng thuộc tính và phương thức từ lớp cha. Java chỉ hỗ trợ **đơn kế thừa** cho class, nhưng cho phép implement **nhiều interface**.

```java
public class NhanVienFullTime extends NhanVien {
    private double thuong;

    @Override
    public double tinhLuong() {
        return super.getLuong() + thuong;  // super gọi method/field của cha
    }
}
```

> **Lưu ý:** Kế thừa tạo coupling chặt. Ưu tiên **Composition over Inheritance** (GoF principle). Chỉ dùng kế thừa khi có quan hệ "IS-A" thực sự.

**Đa hình (Polymorphism):**

| Loại | Tên khác | Thời điểm | Cơ chế |
|------|---------|-----------|--------|
| Compile-time | Overloading (nạp chồng) | Biên dịch | Cùng tên, khác tham số |
| Runtime | Overriding (ghi đè) | Thực thi | Lớp con ghi đè method lớp cha |

```java
// Overloading — cùng tên, khác tham số
public int tinhTong(int a, int b) { return a + b; }
public double tinhTong(double a, double b) { return a + b; }
public int tinhTong(int a, int b, int c) { return a + b + c; }

// Overriding + Runtime Polymorphism
NhanVien nv = new NhanVienFullTime();  // Upcasting
nv.tinhLuong();  // Gọi phương thức của NhanVienFullTime (dynamic dispatch)
```

> **Phỏng vấn:** Overloading vs Overriding? — Overloading: compile-time, cùng class, khác signature. Overriding: runtime, class cha-con, cùng signature, annotation `@Override`.

**Trừu tượng (Abstraction):**

```java
// Abstract class — có thể chứa cả abstract và concrete methods
public abstract class HinhHoc {
    protected String mauSac;                    // Có state
    abstract double tinhDienTich();             // Phải implement
    abstract double tinhChuVi();               // Phải implement
    public void inThongTin() {                 // Có thể có implementation
        System.out.printf("Diện tích: %.2f%n", tinhDienTich());
    }
}

// Interface — contract thuần túy (từ Java 8 có default methods)
public interface CoTheTinh {
    double tinhDienTich();                      // abstract (mặc định)
    double tinhChuVi();
    default String moTa() { return "Hình"; }   // default method (Java 8+)
    static double PI = 3.14159;                 // public static final
}
```

### 2.2 Interface vs Abstract Class

| Đặc điểm | Interface | Abstract Class |
|-----------|-----------|---------------|
| Đa kế thừa | Có (`implements` nhiều) | Không (`extends` 1) |
| Constructor | Không | Có |
| Biến | `public static final` | Mọi loại (instance, static) |
| Phương thức | `abstract`, `default`, `static`, `private` | Mọi loại |
| State (field) | Không có instance field | Có |
| Khi nào dùng | Định nghĩa hành vi chung cho các class không liên quan | Chia sẻ code + state giữa các lớp liên quan |
| Ví dụ | `Comparable`, `Serializable`, `Runnable` | `AbstractList`, `HttpServlet` |

> **Quy tắc thực hành:** "Program to an interface, not an implementation" — Khai báo biến bằng interface type, khởi tạo bằng class cụ thể: `List<String> list = new ArrayList<>();`

---

## Phần 3: Java Collections Framework

### 3.1 Cấu trúc Collections

```
Collection (Interface)
├── List (Interface) — Có thứ tự, cho phép trùng lặp
│   ├── ArrayList   — Mảng động, truy cập nhanh O(1), thêm/xóa giữa O(n)
│   ├── LinkedList  — Danh sách liên kết đôi, thêm/xóa O(1), truy cập O(n)
│   └── Vector      — Thread-safe ArrayList (legacy, ít dùng)
├── Set (Interface) — Không trùng lặp
│   ├── HashSet     — Không thứ tự, O(1) add/remove/contains
│   ├── LinkedHashSet — Giữ thứ tự thêm vào, O(1)
│   └── TreeSet     — Sắp xếp tự nhiên, O(log n), dùng Red-Black Tree
└── Queue (Interface) — FIFO
    ├── PriorityQueue — Hàng đợi ưu tiên (Heap), O(log n)
    ├── ArrayDeque    — Deque hiệu quả (stack + queue), O(1) amortized
    └── LinkedList    — Cũng implement Deque

Map (Interface) — Key-Value pairs, không thuộc Collection
├── HashMap       — O(1) get/put, không thứ tự, cho phép 1 null key
├── LinkedHashMap — Giữ thứ tự thêm vào (hoặc access order)
├── TreeMap       — Sắp xếp theo key, O(log n), dùng Red-Black Tree
├── Hashtable     — Thread-safe (legacy, dùng ConcurrentHashMap thay thế)
└── ConcurrentHashMap — Thread-safe hiện đại, lock striping
```

### 3.2 So sánh chi tiết các Collection

| Thao tác | ArrayList | LinkedList | HashSet | TreeSet | HashMap | TreeMap |
|----------|-----------|------------|---------|---------|---------|---------|
| `get(i)` / `get(key)` | O(1) | O(n) | — | — | O(1) | O(log n) |
| `add` / `put` | O(1)* | O(1) | O(1) | O(log n) | O(1) | O(log n) |
| `remove` | O(n) | O(1)** | O(1) | O(log n) | O(1) | O(log n) |
| `contains` / `containsKey` | O(n) | O(n) | O(1) | O(log n) | O(1) | O(log n) |
| Thứ tự | Insertion | Insertion | Không | Sorted | Không | Sorted |
| Thread-safe | Không | Không | Không | Không | Không | Không |

> (*) Amortized O(1), worst case O(n) khi resize.
> (**) O(1) nếu có reference đến node, O(n) nếu tìm kiếm trước.

> **Khi nào dùng gì?**
> - Đọc nhiều, index → `ArrayList`
> - Thêm/xóa đầu/cuối nhiều → `LinkedList` hoặc `ArrayDeque`
> - Kiểm tra tồn tại, loại trùng → `HashSet`
> - Cần sắp xếp → `TreeSet` / `TreeMap`
> - Key-Value lookup → `HashMap`

### 3.3 Comparable vs Comparator

```java
// Comparable — sắp xếp tự nhiên (trong class, chỉ 1 cách)
public class SinhVien implements Comparable<SinhVien> {
    private String ten;
    private double diem;

    @Override
    public int compareTo(SinhVien other) {
        return Double.compare(this.diem, other.diem);  // Sắp xếp theo điểm
    }
}

// Comparator — sắp xếp tùy chỉnh (bên ngoài, nhiều cách)
// Sắp xếp theo điểm giảm dần
Collections.sort(list, Comparator.comparing(SinhVien::getDiem).reversed());

// Sắp xếp theo tên, nếu trùng thì theo điểm giảm dần
list.sort(Comparator.comparing(SinhVien::getTen)
                    .thenComparing(SinhVien::getDiem, Comparator.reverseOrder()));

// Xử lý null
list.sort(Comparator.nullsLast(Comparator.comparing(SinhVien::getTen)));
```

### 3.4 equals() và hashCode()

> **Contract quan trọng:** Nếu override `equals()`, **PHẢI** override `hashCode()`. Hai object `equals()` → cùng `hashCode()`. Vi phạm sẽ gây lỗi với HashMap, HashSet.

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SinhVien sv = (SinhVien) o;
    return Objects.equals(maSV, sv.maSV);
}

@Override
public int hashCode() {
    return Objects.hash(maSV);
}
```

---

## Phần 4: Java Nâng Cao

### 4.1 Generics

```java
// Generic class — tái sử dụng cho nhiều kiểu dữ liệu
public class Hop<T> {
    private T giaTri;
    public T layGiaTri() { return giaTri; }
    public void datGiaTri(T giaTri) { this.giaTri = giaTri; }
}

// Generic method
public <T extends Comparable<T>> T timMax(T a, T b) {
    return a.compareTo(b) > 0 ? a : b;
}

// Bounded type — giới hạn kiểu
public <T extends Number & Comparable<T>> double tinhTong(List<T> list) {
    return list.stream().mapToDouble(Number::doubleValue).sum();
}

// Wildcard — linh hoạt hơn
public void inDanhSach(List<?> list) { }           // Unbounded — đọc (read-only)
public void tinhTong(List<? extends Number> list) { } // Upper bound — đọc Number
public void themPhanTu(List<? super Integer> list) { } // Lower bound — ghi Integer
```

> **PECS Principle:** Producer Extends, Consumer Super.
> - `? extends T` — Khi chỉ **đọc** (produce) dữ liệu kiểu T
> - `? super T` — Khi chỉ **ghi** (consume) dữ liệu kiểu T
> - **Type Erasure:** Generic bị xóa lúc runtime → `List<String>` và `List<Integer>` cùng là `List` lúc runtime.

### 4.2 Lambda và Functional Interface

```java
// Functional Interface — interface chỉ có 1 abstract method
@FunctionalInterface
public interface PhepTinh {
    double tinh(double a, double b);
}

// Lambda expression — cú pháp ngắn gọn cho anonymous function
PhepTinh cong = (a, b) -> a + b;
PhepTinh nhan = (a, b) -> a * b;

// Built-in Functional Interfaces (java.util.function)
Predicate<String> isEmpty = String::isEmpty;          // T → boolean
Function<String, Integer> toLength = String::length;   // T → R
Consumer<String> print = System.out::println;          // T → void
Supplier<String> hello = () -> "Xin chào";            // () → T
BiFunction<Integer, Integer, Integer> sum = Integer::sum;
UnaryOperator<String> toUpper = String::toUpperCase;   // T → T
BinaryOperator<Integer> max = Integer::max;            // (T,T) → T

// Method Reference — 4 loại
// 1. Static method:      Integer::parseInt
// 2. Instance method:    String::toUpperCase
// 3. Object method:      System.out::println
// 4. Constructor:        ArrayList::new
```

### 4.3 Stream API

Stream API cung cấp cách xử lý dữ liệu **declarative** (khai báo), hỗ trợ parallel processing.

```java
List<String> names = List.of("An", "Bình", "Cường", "Dũng", "An");

// ===== Intermediate Operations (trả về Stream, lazy) =====
names.stream()
    .filter(s -> s.length() > 2)      // Lọc theo điều kiện
    .map(String::toUpperCase)          // Biến đổi phần tử
    .flatMap(s -> Arrays.stream(s.split(""))) // Phẳng hóa
    .sorted()                          // Sắp xếp (natural order)
    .distinct()                        // Loại bỏ trùng
    .limit(3)                          // Giới hạn số phần tử
    .skip(1)                           // Bỏ qua n phần tử đầu
    .peek(System.out::println);        // Debug (không nên dùng cho side-effects)

// ===== Terminal Operations (kích hoạt pipeline) =====
list.stream().collect(Collectors.toList());         // Thu thập thành List
list.stream().forEach(System.out::println);         // Duyệt
list.stream().count();                              // Đếm
list.stream().findFirst();                          // Optional<T>
list.stream().anyMatch(s -> s.startsWith("A"));     // boolean
list.stream().allMatch(s -> s.length() > 0);        // boolean
list.stream().reduce("", String::concat);           // Gộp thành 1 giá trị

// ===== Collectors nâng cao =====
// Nhóm theo độ dài chuỗi
Map<Integer, List<String>> grouped =
    names.stream().collect(Collectors.groupingBy(String::length));

// Nhóm và đếm
Map<String, Long> counted =
    names.stream().collect(Collectors.groupingBy(s -> s, Collectors.counting()));

// Phân vùng (true/false)
Map<Boolean, List<String>> partitioned =
    names.stream().collect(Collectors.partitioningBy(s -> s.length() > 2));

// Nối chuỗi
String joined = names.stream().collect(Collectors.joining(", ", "[", "]"));

// Thống kê
DoubleSummaryStatistics stats =
    numbers.stream().collect(Collectors.summarizingDouble(x -> x));
// stats.getCount(), stats.getSum(), stats.getAverage(), stats.getMin(), stats.getMax()

// toMap — cẩn thận duplicate key
Map<String, Integer> nameLength = names.stream()
    .collect(Collectors.toMap(s -> s, String::length, (v1, v2) -> v1)); // merge function
```

> **Best Practice:** Stream là **lazy** — chỉ thực thi khi gặp terminal operation. **Không reuse** stream (IllegalStateException). Dùng `parallelStream()` cẩn thận — chỉ hiệu quả với dataset lớn và operations CPU-intensive.

### 4.4 Optional

`Optional<T>` tránh NullPointerException, ép buộc xử lý trường hợp null.

```java
Optional<String> opt = Optional.ofNullable(value);

// Kiểm tra và lấy giá trị
opt.isPresent();                                // Có giá trị?
opt.isEmpty();                                  // Java 11+ — Trống?
opt.ifPresent(System.out::println);             // Thực thi nếu có
opt.ifPresentOrElse(                            // Java 9+
    val -> process(val),
    () -> handleEmpty()
);

// Lấy giá trị với fallback
opt.orElse("Mặc định");                        // Trả về default
opt.orElseGet(() -> tinhGiaTriMacDinh());       // Lazy default
opt.orElseThrow(() -> new NotFoundException()); // Throw exception
opt.or(() -> Optional.of("Backup"));            // Java 9+ — Optional khác

// Biến đổi
opt.map(String::toUpperCase);                   // Optional<String>
opt.flatMap(this::timKiem);                     // Tránh Optional<Optional<T>>
opt.filter(s -> s.length() > 5);                // Lọc
opt.stream();                                   // Java 9+ — Stream 0 hoặc 1 phần tử
```

> **Anti-pattern:** Không dùng `opt.get()` mà không kiểm tra (throw NoSuchElementException). Không dùng Optional cho field, parameter, hoặc collection — chỉ dùng cho **return type**.

### 4.5 Xử lý ngoại lệ (Exception Handling)

**Cây phân cấp Exception:**
```
Throwable
├── Error (Lỗi hệ thống — KHÔNG bắt)
│   ├── OutOfMemoryError
│   ├── StackOverflowError
│   └── VirtualMachineError
└── Exception
    ├── Checked Exception (PHẢI xử lý — khai báo throws hoặc try-catch)
    │   ├── IOException, FileNotFoundException
    │   ├── SQLException
    │   ├── ClassNotFoundException
    │   └── InterruptedException
    └── RuntimeException (Unchecked — không bắt buộc)
        ├── NullPointerException
        ├── ArrayIndexOutOfBoundsException
        ├── IllegalArgumentException
        ├── IllegalStateException
        ├── NumberFormatException
        └── ConcurrentModificationException
```

```java
// try-with-resources (Java 7+) — tự động close resources
try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
    String line = reader.readLine();
} catch (FileNotFoundException e) {
    System.err.println("Không tìm thấy tệp: " + e.getMessage());
} catch (IOException e) {
    System.err.println("Lỗi đọc tệp: " + e.getMessage());
} finally {
    System.out.println("Luôn thực thi");
}

// Multi-catch (Java 7+)
try {
    // code
} catch (IOException | SQLException e) {
    log.error("Lỗi: {}", e.getMessage());
}

// Custom Exception
public class KhongDuSoDuException extends Exception {
    private final double soDu;
    private final double soTien;

    public KhongDuSoDuException(double soDu, double soTien) {
        super("Số dư " + soDu + " không đủ để rút " + soTien);
        this.soDu = soDu;
        this.soTien = soTien;
    }

    public double getSoDu() { return soDu; }
    public double getSoTien() { return soTien; }
}
```

> **Best Practice:** (1) Bắt exception cụ thể, không bắt `Exception` chung. (2) Không dùng exception cho control flow. (3) Log exception ở nơi xử lý, không re-throw mà không thêm context. (4) Resource luôn dùng try-with-resources.

### 4.6 Multi-threading và Concurrency

```java
// Cách 1: Extends Thread (ít dùng — vì Java đơn kế thừa)
class MyThread extends Thread {
    @Override
    public void run() { System.out.println("Thread đang chạy"); }
}

// Cách 2: Implements Runnable (khuyên dùng)
Runnable task = () -> System.out.println("Task đang chạy");
new Thread(task).start();

// Cách 3: ExecutorService (khuyến khích nhất)
ExecutorService executor = Executors.newFixedThreadPool(4);
Future<Integer> future = executor.submit(() -> {
    Thread.sleep(1000);
    return 42;
});
int result = future.get(); // Blocking — chờ kết quả
executor.shutdown();       // Đừng quên shutdown!

// Cách 4: CompletableFuture (Java 8+ — non-blocking, chainable)
CompletableFuture.supplyAsync(() -> layDuLieu())
    .thenApply(data -> xuLy(data))          // Biến đổi
    .thenAccept(result -> hienThi(result))  // Tiêu thụ
    .thenRun(() -> log("Done"))             // Chạy tiếp
    .exceptionally(ex -> { log(ex); return null; });

// CompletableFuture kết hợp nhiều tasks
CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "Hello");
CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> "World");
f1.thenCombine(f2, (a, b) -> a + " " + b);  // "Hello World"

// Thread Safety
// synchronized — đơn giản nhưng coarse-grained
public synchronized void rutTien(double soTien) {
    if (soDu >= soTien) soDu -= soTien;
}

// ReentrantLock — linh hoạt hơn (tryLock, timed lock, fairness)
private final ReentrantLock lock = new ReentrantLock();
public void rutTien(double soTien) {
    lock.lock();
    try {
        if (soDu >= soTien) soDu -= soTien;
    } finally {
        lock.unlock();  // LUÔN unlock trong finally
    }
}

// Volatile — đảm bảo visibility giữa các thread
private volatile boolean running = true;
```

> **Concurrent Collections:** `ConcurrentHashMap`, `CopyOnWriteArrayList`, `BlockingQueue`, `ConcurrentLinkedQueue` — thread-safe mà không cần synchronized thủ công.

### 4.7 Java I/O và NIO

```java
// Đọc/ghi file với NIO (Java 7+)
Path path = Path.of("data.txt");
String content = Files.readString(path);         // Đọc toàn bộ
Files.writeString(path, "Nội dung mới");         // Ghi đè
List<String> lines = Files.readAllLines(path);   // Đọc từng dòng
Files.write(path, lines, StandardOpenOption.APPEND); // Ghi thêm

// Stream file lớn — không load toàn bộ vào RAM
try (Stream<String> stream = Files.lines(path)) {
    long count = stream.filter(line -> line.contains("Java"))
                       .count();
}

// Walk directory
try (Stream<Path> paths = Files.walk(Path.of("src"))) {
    paths.filter(p -> p.toString().endsWith(".java"))
         .forEach(System.out::println);
}

// Serialization — lưu object vào file
public class SinhVien implements Serializable {
    private static final long serialVersionUID = 1L;  // Version control
    private String ten;
    private transient String matKhau;  // Không serialize trường này
}
```

### 4.8 Java Records (Java 16+)

Records tự động tạo constructor, getters, `equals()`, `hashCode()`, `toString()`.

```java
public record Diem(double x, double y) {
    // Compact constructor — validation
    public Diem {
        if (x < 0 || y < 0) throw new IllegalArgumentException("Tọa độ âm");
    }

    // Custom method
    public double khoangCach(Diem other) {
        return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
    }
}

// Sử dụng
Diem p1 = new Diem(3, 4);
p1.x();  // 3.0 (accessor, không phải getX())
p1.y();  // 4.0
```

> Record là `final`, không thể kế thừa. Tất cả field là `final`. Thích hợp cho DTO, Value Object, immutable data.

### 4.9 Sealed Classes (Java 17)

Giới hạn class nào được phép kế thừa — kiểm soát cây class hierarchy.

```java
public sealed class HinhHoc permits HinhTron, HinhVuong, HinhChuNhat {
    abstract double tinhDienTich();
}

public final class HinhTron extends HinhHoc {
    double banKinh;
    double tinhDienTich() { return Math.PI * banKinh * banKinh; }
}

public final class HinhVuong extends HinhHoc {
    double canh;
    double tinhDienTich() { return canh * canh; }
}

public non-sealed class HinhChuNhat extends HinhHoc {
    // non-sealed cho phép bất kỳ class kế thừa tiếp
    double dai, rong;
    double tinhDienTich() { return dai * rong; }
}
```

### 4.10 Pattern Matching (Java 16+)

```java
// instanceof pattern matching — kết hợp kiểm tra kiểu và cast
if (obj instanceof String s && s.length() > 5) {
    System.out.println(s.toUpperCase());
}

// Switch pattern matching (Java 21)
String moTa = switch (hinhHoc) {
    case HinhTron h   -> "Hình tròn bán kính " + h.getBanKinh();
    case HinhVuong h  -> "Hình vuông cạnh " + h.getCanh();
    case null         -> "Null";
    default           -> "Không xác định";
};

// Guarded patterns
String loai = switch (obj) {
    case Integer i when i > 0 -> "Số dương";
    case Integer i            -> "Số không dương";
    case String s             -> "Chuỗi: " + s;
    default                   -> "Khác";
};
```

---

## Phần 5: Các Chủ Đề Quan Trọng Khác

### 5.1 Garbage Collection (GC)

JVM tự động thu hồi object không còn reference.

| GC Algorithm | Đặc điểm | Phù hợp |
|-------------|----------|---------|
| Serial GC | Single-thread, stop-the-world | Ứng dụng nhỏ, client |
| Parallel GC | Multi-thread, throughput cao | Server, batch processing |
| G1 GC (default Java 9+) | Region-based, low latency | Đa số ứng dụng |
| ZGC (Java 15+) | Ultra-low latency (<10ms pause) | Ứng dụng realtime |

> **Phỏng vấn:** Object eligible for GC khi nào? — Khi không còn reference nào trỏ tới. GC chạy tự động, không thể ép buộc (`System.gc()` chỉ là gợi ý).

### 5.2 Memory Model

| Vùng nhớ | Chứa | Đặc điểm |
|---------|------|----------|
| Stack | Local variables, method calls | Mỗi thread 1 stack, LIFO, tự giải phóng |
| Heap | Objects, arrays | Chia sẻ giữa threads, GC quản lý |
| Method Area | Class metadata, static vars | Thuộc Heap (Metaspace từ Java 8) |
| String Pool | String literals | Phần đặc biệt của Heap |

### 5.3 Design Principles (SOLID)

| Nguyên tắc | Tên | Mô tả |
|-----------|-----|-------|
| **S** | Single Responsibility | Mỗi class chỉ có 1 lý do để thay đổi |
| **O** | Open/Closed | Mở để mở rộng, đóng để sửa đổi |
| **L** | Liskov Substitution | Subclass thay thế được cho parent class |
| **I** | Interface Segregation | Interface nhỏ, chuyên biệt hơn interface lớn |
| **D** | Dependency Inversion | Depend vào abstraction, không phải implementation |
