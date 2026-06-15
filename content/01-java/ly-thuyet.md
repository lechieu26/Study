# Java Core - Lý Thuyết Từ Cơ Bản Đến Nâng Cao

## Mục lục

1. [Giới thiệu về Java](#1-giới-thiệu-về-java)
2. [Cài đặt và môi trường phát triển](#2-cài-đặt-và-môi-trường-phát-triển)
3. [Các thành phần cơ bản của Java](#3-các-thành-phần-cơ-bản-của-java)
4. [Các kiểu dữ liệu trong Java](#4-các-kiểu-dữ-liệu-trong-java)
5. [Biến và Hằng trong Java](#5-biến-và-hằng-trong-java)
6. [Toán tử trong Java](#6-toán-tử-trong-java)
7. [Câu điều kiện](#7-câu-điều-kiện)
8. [Vòng lặp](#8-vòng-lặp)
9. [Mảng (Arrays)](#9-mảng-arrays)
10. [Chuỗi (String)](#10-chuỗi-string)
11. [Phương thức (Methods)](#11-phương-thức-methods)
12. [Nhập xuất dữ liệu](#12-nhập-xuất-dữ-liệu)
13. [Best Practices](#13-best-practices)
14. [Bài tập thực hành](#14-bài-tập-thực-hành)
15. [Object trong Java](#15-object-trong-java)
    - [15.1 Object là gì](#151-object-là-gì)
    - [15.2 Cách tạo Object](#152-cách-tạo-object)
    - [15.3 Cấu trúc của Object trong bộ nhớ](#153-cấu-trúc-của-object-trong-bộ-nhớ)
    - [15.4 Vòng đời của Object](#154-vòng-đời-của-object)
    - [15.5 Các phương thức của Object class](#155-các-phương-thức-của-object-class)
    - [15.6 So sánh Object (== vs equals)](#156-so-sánh-object--vs-equals)
    - [15.7 Garbage Collection (GC) trong Java](#157-garbage-collection-gc-trong-java)
        - [15.7.1 Garbage Collection là gì](#1571-garbage-collection-là-gì)
        - [15.7.2 Cấu trúc Heap Memory](#1572-cấu-trúc-heap-memory)
        - [15.7.3 Quá trình Garbage Collection](#1573-quá-trình-garbage-collection)
        - [15.7.4 Các thuật toán Garbage Collection](#1574-các-thuật-toán-garbage-collection)
        - [15.7.5 Các loại GC Algorithms trong Java](#1575-các-loại-gc-algorithms-trong-java)
        - [15.7.6 Khi nào Object bị Garbage Collected](#1576-khi-nào-object-bị-garbage-collected)
        - [15.7.7 System.gc() - Có nên sử dụng](#1577-systemgc---có-nên-sử-dụng)
        - [15.7.8 Memory Leaks trong Java](#1578-memory-leaks-trong-java)
        - [15.7.9 GC Logs và Monitoring](#1579-gc-logs-và-monitoring)
        - [15.7.10 Best Practices với GC](#15710-best-practices-với-gc)
    - [15.8 So sánh Stack và Heap](#158-so-sánh-stack-và-heap)
16. [Wrapper Classes - Boxing và Unboxing](#16-wrapper-classes---boxing-và-unboxing)
17. [Tổng kết](#17-tổng-kết)

---

## 1. Giới thiệu về Java

### 1.1 Java là gì?

Java là ngôn ngữ lập trình **hướng đối tượng** (Object-Oriented), **đa nền tảng** (cross-platform), được phát triển bởi **James Gosling** tại Sun Microsystems vào năm **1995**. Hiện tại Java thuộc sở hữu của **Oracle Corporation**.

Java được thiết kế với triết lý cốt lõi: **"Write Once, Run Anywhere"** (WORA) — viết một lần, chạy mọi nơi. Điều này có nghĩa là code Java được biên dịch thành **bytecode**, và bytecode này có thể chạy trên bất kỳ hệ điều hành nào có cài đặt **Java Virtual Machine (JVM)**.

### 1.2 Lịch sử phát triển

| Năm | Sự kiện |
|-----|---------|
| 1991 | James Gosling bắt đầu dự án "Oak" tại Sun Microsystems |
| 1995 | Phát hành Java 1.0, đổi tên từ Oak thành Java |
| 2004 | Java 5.0 — Generics, Enum, Annotations, Autoboxing |
| 2006 | Java trở thành mã nguồn mở (OpenJDK) |
| 2010 | Oracle mua Sun Microsystems |
| 2014 | Java 8 — Lambda, Stream API, Optional, Date/Time API |
| 2017 | Java 9 — Module System, phát hành theo chu kỳ 6 tháng |
| 2021 | Java 17 — LTS (Long Term Support), Sealed Classes, Pattern Matching |
| 2023 | Java 21 — LTS, Virtual Threads, Pattern Matching for switch |

### 1.3 Đặc điểm nổi bật của Java

**1. Đơn giản (Simple):**
Java loại bỏ nhiều tính năng phức tạp của C++ như con trỏ (pointer), đa kế thừa class, operator overloading.

**2. Hướng đối tượng (Object-Oriented):**
Mọi thứ trong Java đều là đối tượng (ngoại trừ kiểu nguyên thủy). Java hỗ trợ đầy đủ 4 tính chất OOP: Đóng gói, Kế thừa, Đa hình, Trừu tượng.

**3. Đa nền tảng (Platform Independent):**
```
Source Code (.java) → Compiler (javac) → Bytecode (.class) → JVM → Mã máy
```
Bytecode là mã trung gian, không phụ thuộc vào hệ điều hành. JVM trên mỗi nền tảng sẽ dịch bytecode thành mã máy tương ứng.

**4. An toàn (Secure):**
- Không có con trỏ → không thể truy cập trực tiếp vào bộ nhớ
- Bytecode Verifier kiểm tra code trước khi thực thi
- Security Manager kiểm soát quyền truy cập

**5. Mạnh mẽ (Robust):**
- Quản lý bộ nhớ tự động (Garbage Collection)
- Xử lý ngoại lệ (Exception Handling) mạnh mẽ
- Kiểm tra kiểu dữ liệu nghiêm ngặt (Strongly Typed)

**6. Đa luồng (Multi-threaded):**
Java hỗ trợ lập trình đa luồng từ tầng ngôn ngữ với `Thread`, `Runnable`, `synchronized`, `volatile`.

**7. Hiệu suất cao:**
Nhờ **JIT Compiler** (Just-In-Time), bytecode được biên dịch thành mã máy native khi chạy, giúp tăng hiệu suất đáng kể.

### 1.4 Kiến trúc Java Platform

```
┌─────────────────────────────────────┐
│           JDK (Java Development Kit) │
│  ┌─────────────────────────────────┐ │
│  │    JRE (Java Runtime Environment)│ │
│  │  ┌─────────────────────────────┐ │ │
│  │  │   JVM (Java Virtual Machine) │ │ │
│  │  │  ┌───────────────────────┐  │ │ │
│  │  │  │  JIT Compiler         │  │ │ │
│  │  │  │  Class Loader         │  │ │ │
│  │  │  │  Garbage Collector    │  │ │ │
│  │  │  │  Execution Engine     │  │ │ │
│  │  │  └───────────────────────┘  │ │ │
│  │  └─────────────────────────────┘ │ │
│  │  + Thư viện chuẩn (rt.jar)       │ │
│  └─────────────────────────────────┘ │
│  + javac (Compiler)                   │
│  + javadoc, jar, jdb (Tools)          │
└─────────────────────────────────────┘
```

| Thành phần | Mô tả | Vai trò |
|-----------|-------|---------|
| **JDK** | Java Development Kit | Bộ công cụ phát triển: JRE + compiler (`javac`) + debugger + tools |
| **JRE** | Java Runtime Environment | Môi trường chạy: JVM + thư viện chuẩn |
| **JVM** | Java Virtual Machine | Máy ảo thực thi bytecode, quản lý bộ nhớ (GC) |
| **JIT** | Just-In-Time Compiler | Biên dịch bytecode → mã máy khi chạy, tối ưu hiệu năng |

### 1.5 Quy trình biên dịch và thực thi

```
┌──────────┐     javac      ┌──────────┐      JVM       ┌──────────┐
│ Hello.java│ ──────────────→│Hello.class│ ──────────────→│  Kết quả  │
│(Source Code)│  (Biên dịch)  │(Bytecode) │  (Thực thi)   │ (Output)  │
└──────────┘                └──────────┘                └──────────┘
```

**Demo: Chương trình Java đầu tiên**

```java
// File: HelloWorld.java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Xin chào, Java!");
    }
}
```

```bash
# Biên dịch: tạo file HelloWorld.class (bytecode)
javac HelloWorld.java

# Thực thi: JVM đọc bytecode và chạy
java HelloWorld
# Output: Xin chào, Java!
```

> **Phỏng vấn thường hỏi:** Sự khác biệt giữa JDK, JRE và JVM?
> - **JDK** = JRE + Công cụ phát triển (javac, javadoc, jar...)
> - **JRE** = JVM + Thư viện chuẩn
> - **JVM** = Máy ảo thực thi bytecode
> Java là "platform independent" vì bytecode chạy trên JVM, mà JVM có phiên bản cho mỗi hệ điều hành.

### 1.6 Các phiên bản Java quan trọng

| Phiên bản | Tính năng chính | Ghi chú |
|-----------|----------------|---------|
| Java 5 | Generics, Enum, Annotations, Autoboxing, for-each | Bước ngoặt lớn |
| Java 8 | Lambda, Stream API, Optional, Date/Time API, default methods | **LTS**, phổ biến nhất |
| Java 11 | var, String methods mới, HTTP Client | **LTS** |
| Java 17 | Sealed Classes, Pattern Matching, Records | **LTS**, khuyên dùng |
| Java 21 | Virtual Threads, Pattern Matching for switch, Sequenced Collections | **LTS** mới nhất |

> **LTS (Long Term Support)** là các phiên bản được hỗ trợ dài hạn (nhiều năm), khuyên dùng cho production.

---

## 2. Cài đặt và môi trường phát triển

### 2.1 Cài đặt JDK

**Trên Windows:**
1. Tải JDK từ [Oracle](https://www.oracle.com/java/technologies/downloads/) hoặc [Adoptium](https://adoptium.net/)
2. Chạy installer, chọn thư mục cài đặt (ví dụ: `C:\Program Files\Java\jdk-17`)
3. Thiết lập biến môi trường:

```
# Thêm vào System Variables:
JAVA_HOME = C:\Program Files\Java\jdk-17

# Thêm vào PATH:
%JAVA_HOME%\bin
```

**Trên macOS:**
```bash
# Dùng Homebrew
brew install openjdk@17

# Hoặc dùng SDKMAN (khuyên dùng - quản lý nhiều phiên bản)
curl -s "https://get.sdkman.io" | bash
sdk install java 17.0.8-tem
```

**Trên Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install openjdk-17-jdk

# Kiểm tra
java -version
javac -version
```

### 2.2 Kiểm tra cài đặt

```bash
# Kiểm tra phiên bản Java Runtime
java -version
# Output: openjdk version "17.0.8" 2023-07-18

# Kiểm tra phiên bản Java Compiler
javac -version
# Output: javac 17.0.8

# Kiểm tra JAVA_HOME
echo $JAVA_HOME
# Output: /usr/lib/jvm/java-17-openjdk-amd64
```

### 2.3 IDE phổ biến

| IDE | Ưu điểm | Nhược điểm | Phù hợp |
|-----|---------|-----------|---------|
| **IntelliJ IDEA** | Thông minh, autocomplete mạnh, refactoring tốt | Tốn RAM, bản Ultimate có phí | Professional, Spring Boot |
| **Eclipse** | Miễn phí, plugin phong phú, cộng đồng lớn | Giao diện cũ, chậm hơn IntelliJ | Enterprise, Java EE |
| **VS Code** | Nhẹ, Extension Pack for Java, tích hợp Git | Không mạnh bằng IDE chuyên dụng | Beginner, lightweight |
| **NetBeans** | Miễn phí, tích hợp Maven/Gradle | Ít plugin, ít phổ biến | Beginner |

### 2.4 Build Tools

| Tool | File cấu hình | Lệnh build | Đặc điểm |
|------|--------------|-----------|----------|
| **Maven** | `pom.xml` | `mvn clean package` | Convention over configuration, phổ biến nhất |
| **Gradle** | `build.gradle` | `gradle build` | Linh hoạt, nhanh hơn Maven, dùng Groovy/Kotlin DSL |

**Ví dụ Maven pom.xml cơ bản:**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>my-app</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>
</project>
```

### 2.5 Cấu trúc dự án Java chuẩn

```
my-project/
├── src/
│   ├── main/
│   │   ├── java/                  # Source code chính
│   │   │   └── com/example/
│   │   │       └── App.java
│   │   └── resources/             # Tài nguyên (config, file tĩnh)
│   └── test/
│       ├── java/                  # Unit tests
│       │   └── com/example/
│       │       └── AppTest.java
│       └── resources/             # Test resources
├── pom.xml                        # Maven config
├── .gitignore
└── README.md
```

---

## 3. Các thành phần cơ bản của Java

### 3.1 Cấu trúc một chương trình Java

```java
// 1. Khai báo package (tùy chọn, thường có trong dự án thực tế)
package com.example.myapp;

// 2. Import các thư viện cần dùng
import java.util.Scanner;
import java.util.ArrayList;

// 3. Khai báo class (tên class PHẢI trùng với tên file)
public class MyFirstProgram {

    // 4. Phương thức main - điểm bắt đầu chương trình
    public static void main(String[] args) {
        // 5. Các câu lệnh thực thi
        System.out.println("Hello, Java!");
    }
}
```

**Giải thích từng thành phần:**

| Thành phần | Ý nghĩa |
|-----------|---------|
| `package` | Nhóm các class liên quan, tránh trùng tên, tổ chức code |
| `import` | Nạp các class từ package khác để sử dụng |
| `public` | Access modifier - cho phép truy cập từ mọi nơi |
| `class` | Từ khóa khai báo một lớp |
| `static` | Phương thức thuộc về class, không cần tạo object |
| `void` | Phương thức không trả về giá trị |
| `main` | Tên phương thức đặc biệt - điểm bắt đầu chương trình |
| `String[] args` | Tham số dòng lệnh (command-line arguments) |

### 3.2 Package

Package là cơ chế **nhóm các class** liên quan lại với nhau, giống như thư mục trong hệ thống file.

```java
// Khai báo package
package com.example.util;

// Quy ước đặt tên: tên miền đảo ngược
// com.example.myapp → thư mục: com/example/myapp/
```

**Các package quan trọng trong Java:**

| Package | Mô tả |
|---------|-------|
| `java.lang` | Tự động import: String, Math, System, Object, Thread |
| `java.util` | Collections, Date, Scanner, Random |
| `java.io` | File I/O: File, InputStream, OutputStream |
| `java.nio` | New I/O: Path, Files, Channel, Buffer |
| `java.net` | Networking: URL, Socket, HttpClient |
| `java.sql` | Database: Connection, Statement, ResultSet |
| `java.time` | Date/Time API (Java 8+): LocalDate, LocalTime |

### 3.3 Comments (Ghi chú)

```java
// 1. Comment một dòng
// Đây là comment một dòng

// 2. Comment nhiều dòng
/*
 * Đây là comment
 * nhiều dòng
 */

// 3. Javadoc comment - tạo tài liệu API
/**
 * Tính tổng hai số nguyên.
 *
 * @param a số thứ nhất
 * @param b số thứ hai
 * @return tổng của a và b
 * @throws IllegalArgumentException nếu tham số không hợp lệ
 */
public int tinhTong(int a, int b) {
    return a + b;
}
```

### 3.4 Naming Conventions (Quy ước đặt tên)

| Loại | Quy ước | Ví dụ |
|------|---------|-------|
| **Class** | PascalCase | `SinhVien`, `BankAccount` |
| **Interface** | PascalCase | `Comparable`, `Serializable` |
| **Method** | camelCase | `tinhDienTich()`, `getName()` |
| **Variable** | camelCase | `hoTen`, `soLuong`, `firstName` |
| **Constant** | UPPER_SNAKE_CASE | `MAX_SIZE`, `PI`, `DEFAULT_VALUE` |
| **Package** | lowercase | `com.example.myapp` |
| **Enum** | PascalCase (tên), UPPER_SNAKE_CASE (giá trị) | `enum Color { RED, GREEN }` |

### 3.5 Từ khóa (Keywords) trong Java

Java có **67 từ khóa** được đặt trước, không thể dùng làm tên biến, tên class hay tên method:

```
abstract  assert    boolean   break     byte      case
catch     char      class     const*    continue  default
do        double    else      enum      extends   final
finally   float     for       goto*     if        implements
import    instanceof int      interface long      native
new       package   private   protected public    return
short     static    strictfp  super     switch    synchronized
this      throw     throws    transient try       void
volatile  while     var**     yield**   record**  sealed**
```

> `*` là từ khóa dự trữ (chưa dùng). `**` là từ khóa ngữ cảnh (context keyword, từ Java 10+).

---

## 4. Các kiểu dữ liệu trong Java

Java có hai nhóm kiểu dữ liệu chính: **Primitive Types** (kiểu nguyên thủy) và **Reference Types** (kiểu tham chiếu).

### 4.1 Kiểu nguyên thủy (Primitive Types)

Java có **8 kiểu nguyên thủy**, được lưu trực tiếp trên **Stack**:

| Kiểu | Kích thước | Giá trị mặc định | Phạm vi | Wrapper Class |
|------|-----------|------------------|---------|--------------|
| `byte` | 1 byte (8 bit) | 0 | -128 đến 127 | `Byte` |
| `short` | 2 bytes (16 bit) | 0 | -32,768 đến 32,767 | `Short` |
| `int` | 4 bytes (32 bit) | 0 | -2^31 đến 2^31-1 (~±2.1 tỷ) | `Integer` |
| `long` | 8 bytes (64 bit) | 0L | -2^63 đến 2^63-1 | `Long` |
| `float` | 4 bytes (32 bit) | 0.0f | ±3.4×10^38 (7 chữ số có nghĩa) | `Float` |
| `double` | 8 bytes (64 bit) | 0.0d | ±1.7×10^308 (15 chữ số có nghĩa) | `Double` |
| `char` | 2 bytes (16 bit) | '\u0000' | 0 đến 65,535 (Unicode) | `Character` |
| `boolean` | ~1 byte | false | `true` hoặc `false` | `Boolean` |

**Demo các kiểu nguyên thủy:**

```java
public class PrimitiveDemo {
    public static void main(String[] args) {
        // Kiểu số nguyên
        byte tuoi = 25;                    // -128 đến 127
        short soDiem = 30000;              // số nhỏ, tiết kiệm bộ nhớ
        int danSo = 1_000_000;             // dấu _ giúp đọc dễ hơn (Java 7+)
        long khoangCach = 149_600_000L;    // suffix L cho long

        // Kiểu số thực
        float diemTrungBinh = 8.5f;        // suffix f cho float
        double pi = 3.14159265358979;      // mặc định là double

        // Kiểu ký tự
        char kyTu = 'A';                   // ký tự đơn
        char unicode = '\u0041';           // Unicode của 'A'

        // Kiểu logic
        boolean laHocSinh = true;

        // In giá trị
        System.out.println("Tuổi: " + tuoi);
        System.out.println("Dân số: " + danSo);
        System.out.println("Pi: " + pi);
        System.out.println("Ký tự: " + kyTu);
        System.out.println("Là học sinh: " + laHocSinh);
    }
}
```

**Biểu diễn số với các hệ cơ số:**

```java
int thapPhan = 100;          // Hệ 10 (decimal)
int nhiPhan = 0b1100100;     // Hệ 2 (binary) - prefix 0b
int batPhan = 0144;          // Hệ 8 (octal) - prefix 0
int thapLucPhan = 0x64;      // Hệ 16 (hexadecimal) - prefix 0x

System.out.println(thapPhan == nhiPhan);      // true - đều là 100
System.out.println(thapPhan == batPhan);      // true
System.out.println(thapPhan == thapLucPhan);  // true
```

### 4.2 Ép kiểu (Type Casting)

**Ép kiểu ngầm (Implicit/Widening Casting) — tự động, không mất dữ liệu:**

```
byte → short → int → long → float → double
         char ↗
```

```java
int soNguyen = 100;
double soThuc = soNguyen;    // Tự động: int → double
System.out.println(soThuc);  // 100.0

byte b = 42;
int i = b;                   // Tự động: byte → int
```

**Ép kiểu tường minh (Explicit/Narrowing Casting) — thủ công, có thể mất dữ liệu:**

```java
double pi = 3.14159;
int soNguyen = (int) pi;     // Ép kiểu: double → int (mất phần thập phân)
System.out.println(soNguyen); // 3

long soLon = 300;
byte b = (byte) soLon;       // Nguy hiểm! 300 > 127 → overflow
System.out.println(b);       // 44 (do tràn số)

// Cẩn thận với phép chia số nguyên
int a = 7, c = 2;
System.out.println(a / c);           // 3 (chia nguyên)
System.out.println((double) a / c);  // 3.5 (ép kiểu trước khi chia)
```

### 4.3 Kiểu tham chiếu (Reference Types)

Kiểu tham chiếu lưu **địa chỉ** (reference) trỏ đến object trên **Heap**:

| Kiểu | Mô tả | Ví dụ |
|------|-------|-------|
| **String** | Chuỗi ký tự, immutable | `String s = "Hello";` |
| **Array** | Mảng, kích thước cố định | `int[] arr = new int[5];` |
| **Class** | Đối tượng từ class | `Scanner sc = new Scanner(System.in);` |
| **Interface** | Tham chiếu qua interface | `List<String> list = new ArrayList<>();` |
| **Enum** | Tập hợp hằng số có tên | `Color c = Color.RED;` |

```java
// Reference type lưu địa chỉ, không lưu giá trị trực tiếp
String s1 = "Hello";          // s1 lưu địa chỉ trỏ đến "Hello" trong String Pool
String s2 = s1;               // s2 cũng trỏ đến cùng "Hello"
String s3 = new String("Hello"); // s3 trỏ đến object mới trên Heap

System.out.println(s1 == s2);      // true (cùng reference)
System.out.println(s1 == s3);      // false (khác reference)
System.out.println(s1.equals(s3)); // true (cùng giá trị)
```

### 4.4 So sánh Primitive vs Reference

| Đặc điểm | Primitive | Reference |
|-----------|-----------|-----------|
| Lưu trữ | Giá trị trực tiếp | Địa chỉ (reference) đến object |
| Vùng nhớ | Stack | Stack (reference) + Heap (object) |
| Giá trị mặc định | 0, 0.0, false, '\u0000' | `null` |
| So sánh (`==`) | So sánh giá trị | So sánh địa chỉ (reference) |
| Null | Không thể null | Có thể null |
| Kích thước | Cố định (1-8 bytes) | Phụ thuộc object |

---

## 5. Biến và Hằng trong Java

### 5.1 Biến (Variable)

Biến là **vùng nhớ** có tên, dùng để lưu trữ dữ liệu. Mỗi biến có **kiểu dữ liệu** xác định.

**Cú pháp khai báo:**
```java
kiểuDữLiệu tênBiến;                    // Khai báo
kiểuDữLiệu tênBiến = giáTrị;           // Khai báo + khởi tạo
var tênBiến = giáTrị;                    // Java 10+ (type inference)
```

### 5.2 Các loại biến

**1. Biến cục bộ (Local Variable):**
- Khai báo bên trong method, constructor, hoặc block `{}`
- **Phải khởi tạo** trước khi sử dụng (không có giá trị mặc định)
- Chỉ tồn tại trong phạm vi khai báo

```java
public void tinhToan() {
    int x = 10;              // Biến cục bộ
    String message = "Hello"; // Biến cục bộ

    if (x > 5) {
        int y = 20;          // Biến cục bộ trong block if
        System.out.println(y);
    }
    // System.out.println(y); // Lỗi! y không tồn tại ngoài block if
}
```

**2. Biến instance (Instance Variable / Field):**
- Khai báo trong class, bên ngoài method
- Mỗi object có bản sao riêng
- Có **giá trị mặc định** (0, null, false...)

```java
public class SinhVien {
    String hoTen;                // Biến instance, mặc định = null
    int tuoi;                    // Biến instance, mặc định = 0
    double diemTrungBinh;        // Biến instance, mặc định = 0.0
    boolean dangHocTap = true;   // Biến instance, khởi tạo = true

    public void hienThi() {
        // Biến instance có thể truy cập trong mọi method của class
        System.out.println(hoTen + " - " + tuoi + " tuổi");
    }
}

// Sử dụng:
SinhVien sv1 = new SinhVien();
sv1.hoTen = "Nguyễn Văn A";
sv1.tuoi = 20;

SinhVien sv2 = new SinhVien();
sv2.hoTen = "Trần Thị B";     // sv2 có hoTen riêng, không ảnh hưởng sv1
```

**3. Biến static (Class Variable):**
- Khai báo với từ khóa `static`
- **Chia sẻ** giữa tất cả object của class
- Có thể truy cập qua **tên class** mà không cần tạo object

```java
public class SinhVien {
    static int soLuong = 0;    // Biến static - chung cho tất cả object
    String hoTen;              // Biến instance - riêng cho mỗi object

    public SinhVien(String hoTen) {
        this.hoTen = hoTen;
        soLuong++;             // Mỗi lần tạo SinhVien, soLuong tăng 1
    }
}

// Sử dụng:
SinhVien sv1 = new SinhVien("An");    // soLuong = 1
SinhVien sv2 = new SinhVien("Bình");  // soLuong = 2
System.out.println(SinhVien.soLuong); // 2 (truy cập qua tên class)
```

### 5.3 So sánh các loại biến

| Đặc điểm | Local | Instance | Static |
|-----------|-------|----------|--------|
| Vị trí khai báo | Trong method/block | Trong class, ngoài method | Trong class, có `static` |
| Vùng nhớ | Stack | Heap (trong object) | Method Area (Metaspace) |
| Giá trị mặc định | Không có (phải khởi tạo) | Có (0, null, false) | Có (0, null, false) |
| Phạm vi | Trong method/block | Trong class | Toàn bộ class, truy cập qua tên class |
| Vòng đời | Khi method thực thi | Khi object tồn tại | Khi class được load đến khi unload |

### 5.4 Hằng (Constant)

Hằng là biến **không thể thay đổi giá trị** sau khi khởi tạo, sử dụng từ khóa `final`.

```java
public class Constants {
    // Hằng số - thường dùng static final + UPPER_SNAKE_CASE
    public static final double PI = 3.14159265358979;
    public static final int MAX_STUDENTS = 50;
    public static final String SCHOOL_NAME = "Đại học Bách Khoa";

    public static void main(String[] args) {
        // Sử dụng hằng số
        double dienTich = PI * 5 * 5;
        System.out.println("Diện tích: " + dienTich);

        // PI = 3.14; // Lỗi biên dịch! Không thể thay đổi giá trị hằng

        // Hằng cục bộ
        final int MAX_RETRY = 3;
        // MAX_RETRY = 5; // Lỗi!
    }
}
```

**Blank final variable (Java cho phép gán hằng 1 lần):**

```java
public class Config {
    final String databaseUrl;   // Blank final - chưa khởi tạo

    public Config(String url) {
        this.databaseUrl = url; // Gán 1 lần trong constructor — OK
        // this.databaseUrl = "other"; // Lỗi! Đã gán rồi
    }
}
```

### 5.5 var - Type Inference (Java 10+)

```java
// Thay vì viết kiểu dữ liệu dài dòng:
Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();

// Có thể dùng var:
var map = new HashMap<String, List<Integer>>();
var list = List.of(1, 2, 3);              // List<Integer>
var message = "Hello";                     // String
var scanner = new Scanner(System.in);      // Scanner

// Hạn chế: var chỉ dùng cho biến cục bộ
// Không dùng cho: field, parameter, return type
```

---

## 6. Toán tử trong Java

### 6.1 Toán tử số học (Arithmetic Operators)

| Toán tử | Ý nghĩa | Ví dụ | Kết quả |
|---------|---------|-------|---------|
| `+` | Cộng | `5 + 3` | `8` |
| `-` | Trừ | `5 - 3` | `2` |
| `*` | Nhân | `5 * 3` | `15` |
| `/` | Chia | `7 / 2` | `3` (chia nguyên) |
| `%` | Chia lấy dư | `7 % 2` | `1` |
| `++` | Tăng 1 | `i++` hoặc `++i` | |
| `--` | Giảm 1 | `i--` hoặc `--i` | |

```java
public class ArithmeticDemo {
    public static void main(String[] args) {
        // Chia nguyên vs chia thực
        System.out.println(7 / 2);          // 3 (int / int = int)
        System.out.println(7.0 / 2);        // 3.5 (double / int = double)
        System.out.println(7 / 2.0);        // 3.5
        System.out.println((double) 7 / 2); // 3.5

        // Modulo
        System.out.println(10 % 3);  // 1 (10 = 3*3 + 1)
        System.out.println(-10 % 3); // -1 (dấu theo số bị chia)

        // Prefix vs Postfix
        int a = 5;
        System.out.println(a++); // 5 (dùng giá trị cũ, rồi tăng)
        System.out.println(a);   // 6 (đã tăng)

        int b = 5;
        System.out.println(++b); // 6 (tăng trước, rồi dùng)
        System.out.println(b);   // 6
    }
}
```

### 6.2 Toán tử so sánh (Comparison Operators)

| Toán tử | Ý nghĩa | Ví dụ | Kết quả |
|---------|---------|-------|---------|
| `==` | Bằng | `5 == 5` | `true` |
| `!=` | Khác | `5 != 3` | `true` |
| `>` | Lớn hơn | `5 > 3` | `true` |
| `<` | Nhỏ hơn | `5 < 3` | `false` |
| `>=` | Lớn hơn hoặc bằng | `5 >= 5` | `true` |
| `<=` | Nhỏ hơn hoặc bằng | `5 <= 3` | `false` |

```java
// Chú ý: == so sánh reference cho object
String s1 = "Hello";
String s2 = "Hello";
String s3 = new String("Hello");

System.out.println(s1 == s2);      // true (cùng reference trong String Pool)
System.out.println(s1 == s3);      // false (khác reference)
System.out.println(s1.equals(s3)); // true (cùng giá trị)

// Luôn dùng .equals() để so sánh giá trị String và Object
```

### 6.3 Toán tử logic (Logical Operators)

| Toán tử | Ý nghĩa | Short-circuit? |
|---------|---------|---------------|
| `&&` | AND (Và) | Có — dừng nếu vế trái `false` |
| `\|\|` | OR (Hoặc) | Có — dừng nếu vế trái `true` |
| `!` | NOT (Phủ định) | — |

```java
public class LogicalDemo {
    public static void main(String[] args) {
        int x = 10;

        // Short-circuit: vế phải KHÔNG được đánh giá nếu không cần
        // Ví dụ: tránh NullPointerException
        String name = null;
        if (name != null && name.length() > 0) {
            // An toàn! name.length() chỉ gọi khi name != null
        }

        // Ứng dụng: kiểm tra phạm vi
        int tuoi = 25;
        boolean laThanhNien = tuoi >= 18 && tuoi <= 60;  // true

        // Ứng dụng: kiểm tra nhiều điều kiện
        boolean coVe = true;
        boolean laVIP = false;
        boolean duocVao = coVe || laVIP;  // true (chỉ cần 1 điều kiện đúng)
    }
}
```

### 6.4 Toán tử bit (Bitwise Operators)

| Toán tử | Ý nghĩa | Ví dụ (a=5=0101, b=3=0011) | Kết quả |
|---------|---------|---------------------------|---------|
| `&` | AND bit | `5 & 3` = `0101 & 0011` | `1` (0001) |
| `\|` | OR bit | `5 \| 3` = `0101 \| 0011` | `7` (0111) |
| `^` | XOR bit | `5 ^ 3` = `0101 ^ 0011` | `6` (0110) |
| `~` | NOT bit (đảo) | `~5` = `~0101` | `-6` |
| `<<` | Dịch trái | `5 << 1` = `0101 << 1` | `10` (1010) |
| `>>` | Dịch phải (giữ dấu) | `-8 >> 2` | `-2` |
| `>>>` | Dịch phải (unsigned) | `-8 >>> 2` | `1073741822` |

```java
// Ứng dụng: kiểm tra số chẵn/lẻ nhanh bằng bit AND
int n = 7;
if ((n & 1) == 0) {
    System.out.println("Chẵn");
} else {
    System.out.println("Lẻ");   // 7 là lẻ
}

// Ứng dụng: nhân/chia cho 2 nhanh bằng dịch bit
int x = 10;
System.out.println(x << 1); // 20 (nhân 2)
System.out.println(x >> 1); // 5  (chia 2)
System.out.println(x << 3); // 80 (nhân 8 = 2^3)

// Ứng dụng: swap hai số không dùng biến tạm
int a = 5, b = 3;
a = a ^ b; // a = 6
b = a ^ b; // b = 5
a = a ^ b; // a = 3
System.out.println("a=" + a + ", b=" + b); // a=3, b=5
```

### 6.5 Toán tử gán (Assignment Operators)

| Toán tử | Tương đương | Ghi chú |
|---------|------------|---------|
| `=` | `x = 5` | Gán giá trị |
| `+=` | `x = (type)(x + 5)` | Cộng và gán, có cast ngầm |
| `-=` | `x = (type)(x - 5)` | Trừ và gán |
| `*=` | `x = (type)(x * 5)` | Nhân và gán |
| `/=` | `x = (type)(x / 5)` | Chia và gán |
| `%=` | `x = (type)(x % 5)` | Chia dư và gán |
| `&=` | `x = x & 5` | AND bit và gán |
| `\|=` | `x = x \| 5` | OR bit và gán |
| `^=` | `x = x ^ 5` | XOR bit và gán |
| `<<=` | `x = x << 5` | Dịch trái và gán |
| `>>=` | `x = x >> 5` | Dịch phải và gán |

```java
// Cast ngầm trong toán tử gán kết hợp
byte b = 10;
// b = b + 5;   // Lỗi! b + 5 = int, không thể gán cho byte
b += 5;         // OK! tương đương b = (byte)(b + 5)
```

### 6.6 Toán tử điều kiện (Ternary Operator)

```java
// Cú pháp: điều_kiện ? giá_trị_đúng : giá_trị_sai
int tuoi = 20;
String loai = tuoi >= 18 ? "Người lớn" : "Trẻ em";
System.out.println(loai); // "Người lớn"

// Lồng nhau (nên tránh vì khó đọc)
int diem = 75;
String xepLoai = diem >= 90 ? "Xuất sắc"
               : diem >= 70 ? "Khá"
               : diem >= 50 ? "Trung bình"
               : "Yếu";
System.out.println(xepLoai); // "Khá"

// Ternary trả về giá trị, có thể dùng trong biểu thức
double giaTien = 100_000;
double thanhToan = giaTien * (laVIP ? 0.9 : 1.0); // Giảm 10% cho VIP
```

### 6.7 Toán tử instanceof

```java
Object obj = "Hello";

if (obj instanceof String) {
    String s = (String) obj; // Cần cast truyền thống
    System.out.println(s.length());
}

// Java 16+: Pattern Matching for instanceof
if (obj instanceof String s) {
    System.out.println(s.length()); // Tự động cast, không cần cast thủ công
}

// Kết hợp với điều kiện
if (obj instanceof String s && s.length() > 3) {
    System.out.println("Chuỗi dài hơn 3: " + s);
}
```

### 6.8 Thứ tự ưu tiên toán tử

Từ cao đến thấp:

| Ưu tiên | Toán tử | Mô tả |
|---------|---------|-------|
| 1 | `()` `[]` `.` | Ngoặc, truy cập mảng, truy cập thành viên |
| 2 | `++` `--` `!` `~` `(type)` | Unary (đơn ngôi) |
| 3 | `*` `/` `%` | Nhân, chia, dư |
| 4 | `+` `-` | Cộng, trừ |
| 5 | `<<` `>>` `>>>` | Dịch bit |
| 6 | `<` `<=` `>` `>=` `instanceof` | So sánh |
| 7 | `==` `!=` | Bằng, khác |
| 8 | `&` | AND bit |
| 9 | `^` | XOR bit |
| 10 | `\|` | OR bit |
| 11 | `&&` | AND logic |
| 12 | `\|\|` | OR logic |
| 13 | `? :` | Ternary |
| 14 | `=` `+=` `-=` ... | Gán |

> **Mẹo:** Khi không chắc thứ tự ưu tiên, hãy **dùng ngoặc `()`** để làm rõ ý định.

---

## 7. Câu điều kiện

### 7.1 if - else

```java
public class IfElseDemo {
    public static void main(String[] args) {
        int diem = 75;

        // if đơn giản
        if (diem >= 50) {
            System.out.println("Đậu");
        }

        // if-else
        if (diem >= 50) {
            System.out.println("Đậu");
        } else {
            System.out.println("Rớt");
        }

        // if-else if-else (nhiều nhánh)
        if (diem >= 90) {
            System.out.println("Xuất sắc");
        } else if (diem >= 80) {
            System.out.println("Giỏi");
        } else if (diem >= 70) {
            System.out.println("Khá");
        } else if (diem >= 50) {
            System.out.println("Trung bình");
        } else {
            System.out.println("Yếu");
        }
        // Output: Khá
    }
}
```

### 7.2 switch-case

**Switch truyền thống (trước Java 14):**

```java
int ngay = 3;
String tenNgay;

switch (ngay) {
    case 1:
        tenNgay = "Chủ nhật";
        break;                    // break để tránh fall-through
    case 2:
        tenNgay = "Thứ Hai";
        break;
    case 3:
        tenNgay = "Thứ Ba";
        break;
    case 4:
        tenNgay = "Thứ Tư";
        break;
    case 5:
        tenNgay = "Thứ Năm";
        break;
    case 6:
        tenNgay = "Thứ Sáu";
        break;
    case 7:
        tenNgay = "Thứ Bảy";
        break;
    default:
        tenNgay = "Không hợp lệ";
        break;
}
System.out.println(tenNgay);      // Thứ Ba
```

**Switch expression (Java 14+) — cú pháp mới, không cần break:**

```java
// Arrow syntax → (không cần break, không fall-through)
String tenNgay = switch (ngay) {
    case 1 -> "Chủ nhật";
    case 2 -> "Thứ Hai";
    case 3 -> "Thứ Ba";
    case 4 -> "Thứ Tư";
    case 5 -> "Thứ Năm";
    case 6 -> "Thứ Sáu";
    case 7 -> "Thứ Bảy";
    default -> "Không hợp lệ";
};

// Nhiều giá trị trong 1 case
int soNgay = switch (thang) {
    case 2 -> namNhuan ? 29 : 28;
    case 4, 6, 9, 11 -> 30;
    default -> 31;
};

// Block code với yield (trả về giá trị từ block)
String xepLoai = switch (diem / 10) {
    case 10, 9 -> "Xuất sắc";
    case 8 -> "Giỏi";
    case 7 -> "Khá";
    case 5, 6 -> {
        System.out.println("Cần cố gắng thêm");
        yield "Trung bình";       // yield trả về giá trị từ block
    }
    default -> {
        System.out.println("Cần học lại");
        yield "Yếu";
    }
};
```

**Switch hỗ trợ các kiểu dữ liệu:**
- `byte`, `short`, `int`, `char`
- `String` (từ Java 7)
- `enum`
- Wrapper classes: `Byte`, `Short`, `Integer`, `Character`

```java
// Switch với String
String command = "start";
switch (command) {
    case "start" -> System.out.println("Bắt đầu");
    case "stop"  -> System.out.println("Dừng");
    case "pause" -> System.out.println("Tạm dừng");
    default      -> System.out.println("Lệnh không hợp lệ");
}

// Switch với Enum
enum MuaGiai { XUAN, HA, THU, DONG }

MuaGiai mua = MuaGiai.HA;
String moTa = switch (mua) {
    case XUAN -> "Hoa nở, thời tiết ấm áp";
    case HA   -> "Nắng nóng, nghỉ hè";
    case THU  -> "Lá vàng, mát mẻ";
    case DONG -> "Lạnh, Giáng sinh";
};
```

### 7.3 Pattern Matching for switch (Java 21)

```java
// Switch với kiểm tra kiểu (Pattern Matching)
Object obj = 42;

String ketQua = switch (obj) {
    case Integer i when i > 0 -> "Số nguyên dương: " + i;
    case Integer i            -> "Số nguyên: " + i;
    case String s             -> "Chuỗi: " + s;
    case Double d             -> "Số thực: " + d;
    case null                 -> "Giá trị null";
    default                   -> "Kiểu khác: " + obj.getClass().getSimpleName();
};
System.out.println(ketQua); // "Số nguyên dương: 42"
```

---

## 8. Vòng lặp

### 8.1 Vòng lặp for

```java
// for truyền thống
// Cú pháp: for (khởi_tạo; điều_kiện; cập_nhật) { thân_vòng_lặp }
for (int i = 0; i < 5; i++) {
    System.out.print(i + " "); // 0 1 2 3 4
}

// Đếm ngược
for (int i = 10; i >= 1; i--) {
    System.out.print(i + " "); // 10 9 8 7 6 5 4 3 2 1
}

// Nhiều biến trong for
for (int i = 0, j = 10; i < j; i++, j--) {
    System.out.println("i=" + i + ", j=" + j);
}
// Output: i=0,j=10  i=1,j=9  i=2,j=8  i=3,j=7  i=4,j=6

// Vòng lặp vô hạn
for (;;) {
    // Chạy mãi cho đến khi break
    break;
}
```

### 8.2 Vòng lặp for-each (Enhanced for)

```java
// Dùng cho Array và các đối tượng implement Iterable
int[] scores = {90, 85, 70, 95, 80};

// for-each — đọc từng phần tử, không cần index
for (int score : scores) {
    System.out.print(score + " "); // 90 85 70 95 80
}

// Với Collection
List<String> names = List.of("An", "Bình", "Cường");
for (String name : names) {
    System.out.println("Xin chào " + name);
}

// Với Map (dùng entrySet)
Map<String, Integer> diem = Map.of("An", 9, "Bình", 8, "Cường", 7);
for (Map.Entry<String, Integer> entry : diem.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}
```

> **Lưu ý:** for-each **không thể** thay đổi phần tử mảng, không có index, không thể duyệt ngược. Khi cần những tính năng này, dùng for truyền thống.

### 8.3 Vòng lặp while

```java
// while — kiểm tra điều kiện TRƯỚC khi thực thi
// Có thể thực thi 0 lần
int count = 0;
while (count < 5) {
    System.out.print(count + " "); // 0 1 2 3 4
    count++;
}

// Ứng dụng: đọc input cho đến khi gặp "exit"
Scanner sc = new Scanner(System.in);
String input = "";
while (!input.equals("exit")) {
    System.out.print("Nhập lệnh: ");
    input = sc.nextLine();
    System.out.println("Bạn đã nhập: " + input);
}
```

### 8.4 Vòng lặp do-while

```java
// do-while — thực thi ÍT NHẤT 1 LẦN, rồi kiểm tra điều kiện
int count = 10;
do {
    System.out.println("count = " + count); // In ra 1 lần dù count = 10 > 5
    count++;
} while (count < 5);

// Ứng dụng: menu chương trình
Scanner sc = new Scanner(System.in);
int luaChon;
do {
    System.out.println("=== MENU ===");
    System.out.println("1. Xem danh sách");
    System.out.println("2. Thêm mới");
    System.out.println("3. Xóa");
    System.out.println("0. Thoát");
    System.out.print("Chọn: ");
    luaChon = sc.nextInt();

    switch (luaChon) {
        case 1 -> System.out.println("Đang xem danh sách...");
        case 2 -> System.out.println("Đang thêm mới...");
        case 3 -> System.out.println("Đang xóa...");
        case 0 -> System.out.println("Tạm biệt!");
        default -> System.out.println("Lựa chọn không hợp lệ!");
    }
} while (luaChon != 0);
```

### 8.5 break, continue và label

```java
// break — thoát khỏi vòng lặp hiện tại
for (int i = 0; i < 10; i++) {
    if (i == 5) break;        // Dừng khi i = 5
    System.out.print(i + " "); // 0 1 2 3 4
}

// continue — bỏ qua lần lặp hiện tại, tiếp tục lần kế
for (int i = 0; i < 10; i++) {
    if (i % 2 == 0) continue; // Bỏ qua số chẵn
    System.out.print(i + " "); // 1 3 5 7 9
}

// Labeled break — thoát khỏi vòng lặp NGOÀI
outer:
for (int i = 0; i < 5; i++) {
    for (int j = 0; j < 5; j++) {
        if (i * j > 6) {
            System.out.println("Thoát tại i=" + i + ", j=" + j);
            break outer;       // Thoát cả 2 vòng lặp
        }
    }
}

// Labeled continue — quay lại vòng lặp NGOÀI
outer:
for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 3; j++) {
        if (j == 1) continue outer; // Bỏ qua j=1,2, quay lại vòng ngoài
        System.out.println("i=" + i + ", j=" + j);
    }
}
// Output: i=0,j=0  i=1,j=0  i=2,j=0
```

### 8.6 So sánh các vòng lặp

| Đặc điểm | for | for-each | while | do-while |
|-----------|-----|----------|-------|----------|
| Số lần lặp | Biết trước | Toàn bộ collection | Không biết trước | Không biết trước |
| Kiểm tra | Trước | — | Trước | Sau |
| Lần thực thi tối thiểu | 0 | 0 (nếu rỗng) | 0 | **1** |
| Truy cập index | Có | Không | Có (thủ công) | Có (thủ công) |
| Phù hợp | Duyệt mảng, đếm | Collection, Array | Đọc input, chờ | Menu, retry |

---

## 9. Mảng (Arrays)

### 9.1 Khai báo và khởi tạo

```java
// Cách 1: Khai báo rồi khởi tạo
int[] numbers;                      // Khai báo (khuyên dùng)
int numbers2[];                     // Cú pháp C-style (không khuyên)
numbers = new int[5];               // Khởi tạo với kích thước 5 (mặc định = 0)

// Cách 2: Khai báo + khởi tạo giá trị
int[] scores = {90, 85, 70, 95, 80};

// Cách 3: Khởi tạo với new + giá trị
int[] values = new int[]{1, 2, 3, 4, 5};

// Mảng các kiểu khác
String[] names = {"An", "Bình", "Cường"};
double[] prices = new double[10];     // Mặc định = 0.0
boolean[] flags = new boolean[3];     // Mặc định = false
Object[] objects = new Object[5];     // Mặc định = null
```

### 9.2 Truy cập và duyệt mảng

```java
int[] arr = {10, 20, 30, 40, 50};

// Truy cập qua index (0-based)
System.out.println(arr[0]);    // 10 (phần tử đầu)
System.out.println(arr[4]);    // 50 (phần tử cuối)
System.out.println(arr.length);// 5 (kích thước mảng)

// Thay đổi giá trị
arr[2] = 99;
System.out.println(arr[2]);    // 99

// Duyệt bằng for
for (int i = 0; i < arr.length; i++) {
    System.out.print(arr[i] + " ");
}

// Duyệt bằng for-each
for (int value : arr) {
    System.out.print(value + " ");
}

// Duyệt bằng Arrays.toString()
System.out.println(Arrays.toString(arr)); // [10, 20, 99, 40, 50]

// ArrayIndexOutOfBoundsException
// arr[5]; // Lỗi! Index 5 vượt quá mảng kích thước 5 (0-4)
```

### 9.3 Mảng 2 chiều (Multidimensional Array)

```java
// Khai báo và khởi tạo mảng 2 chiều
int[][] matrix = new int[3][4];       // 3 hàng, 4 cột (mặc định = 0)

// Khởi tạo với giá trị
int[][] bangDiem = {
    {9, 8, 7},                         // Hàng 0
    {6, 5, 8},                         // Hàng 1
    {7, 9, 10}                         // Hàng 2
};

// Truy cập: [hàng][cột]
System.out.println(bangDiem[0][0]);    // 9
System.out.println(bangDiem[2][2]);    // 10

// Duyệt mảng 2 chiều
for (int i = 0; i < bangDiem.length; i++) {
    for (int j = 0; j < bangDiem[i].length; j++) {
        System.out.printf("%3d", bangDiem[i][j]);
    }
    System.out.println();
}

// Mảng "răng cưa" (Jagged Array) — mỗi hàng có kích thước khác nhau
int[][] jagged = new int[3][];
jagged[0] = new int[]{1, 2};           // 2 phần tử
jagged[1] = new int[]{3, 4, 5};        // 3 phần tử
jagged[2] = new int[]{6};              // 1 phần tử

// In mảng 2 chiều
System.out.println(Arrays.deepToString(bangDiem));
// [[9, 8, 7], [6, 5, 8], [7, 9, 10]]
```

### 9.4 Các thao tác phổ biến với java.util.Arrays

```java
import java.util.Arrays;

int[] arr = {5, 2, 8, 1, 9, 3};

// Sắp xếp
Arrays.sort(arr);                         // [1, 2, 3, 5, 8, 9]

// Sắp xếp một phần: [fromIndex, toIndex)
int[] arr2 = {5, 2, 8, 1, 9, 3};
Arrays.sort(arr2, 1, 4);                  // [5, 1, 2, 8, 9, 3]

// Tìm kiếm nhị phân (mảng PHẢI đã sắp xếp)
int index = Arrays.binarySearch(arr, 5);  // 3

// Sao chép
int[] copy1 = Arrays.copyOf(arr, 3);     // [1, 2, 3] (3 phần tử đầu)
int[] copy2 = Arrays.copyOf(arr, 8);     // [1, 2, 3, 5, 8, 9, 0, 0] (mở rộng)
int[] copy3 = Arrays.copyOfRange(arr, 2, 5); // [3, 5, 8]

// Điền giá trị
int[] filled = new int[5];
Arrays.fill(filled, 7);                  // [7, 7, 7, 7, 7]

// So sánh hai mảng
int[] a = {1, 2, 3};
int[] b = {1, 2, 3};
System.out.println(a == b);              // false (khác reference)
System.out.println(Arrays.equals(a, b)); // true (cùng giá trị)

// Tính tổng qua Stream
int sum = Arrays.stream(arr).sum();      // 28
double avg = Arrays.stream(arr).average().orElse(0); // 4.67
int max = Arrays.stream(arr).max().orElse(0);        // 9

// Chuyển mảng thành List
List<Integer> list = Arrays.stream(arr).boxed().toList();
```

### 9.5 Demo: Các bài toán phổ biến với mảng

```java
public class ArrayDemo {
    // Tìm phần tử lớn nhất
    public static int timMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) max = arr[i];
        }
        return max;
    }

    // Đảo ngược mảng
    public static void daoNguoc(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }

    // Kiểm tra mảng có sắp xếp tăng dần
    public static boolean laSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) return false;
        }
        return true;
    }

    // Xóa phần tử trùng lặp (mảng đã sắp xếp)
    public static int[] xoaTrung(int[] arr) {
        return Arrays.stream(arr).distinct().toArray();
    }

    public static void main(String[] args) {
        int[] arr = {5, 2, 8, 1, 9, 3, 2, 5};

        System.out.println("Max: " + timMax(arr));           // 9
        System.out.println("Sorted: " + laSorted(arr));     // false

        daoNguoc(arr);
        System.out.println(Arrays.toString(arr));            // [5, 2, 3, 9, 1, 8, 2, 5]

        int[] unique = xoaTrung(arr);
        System.out.println(Arrays.toString(unique));         // [5, 2, 3, 9, 1, 8]
    }
}
```

---

## 10. Chuỗi (String)

### 10.1 Khởi tạo String

```java
// Cách 1: String literal — lưu trong String Pool
String s1 = "Hello";
String s2 = "Hello";
System.out.println(s1 == s2);       // true (cùng reference trong Pool)

// Cách 2: new — tạo object mới trên Heap
String s3 = new String("Hello");
System.out.println(s1 == s3);       // false (khác reference)
System.out.println(s1.equals(s3));  // true (cùng giá trị)

// String là IMMUTABLE (bất biến) — mọi thao tác tạo String mới
String greeting = "Hello";
greeting = greeting + " World";     // Tạo String MỚI "Hello World"
                                    // String cũ "Hello" vẫn trong Pool
```

### 10.2 String Pool

```
┌─────────────── Heap Memory ───────────────┐
│                                            │
│  ┌──────── String Pool ────────┐           │
│  │  "Hello" ← s1, s2 trỏ vào  │           │
│  │  "World"                     │           │
│  │  "Java"                      │           │
│  └──────────────────────────────┘           │
│                                            │
│  ┌── new String("Hello") ──┐               │
│  │  s3 trỏ vào đây         │               │
│  └──────────────────────────┘               │
└────────────────────────────────────────────┘
```

```java
// intern() — đưa String vào Pool
String s3 = new String("Hello");
String s4 = s3.intern();          // Trả về reference từ Pool
System.out.println(s1 == s4);     // true

// Tại sao String là immutable?
// 1. Thread-safe: nhiều thread đọc cùng 1 String an toàn
// 2. Hashcode cache: tính 1 lần, dùng nhiều lần (hiệu quả cho HashMap key)
// 3. Security: URL, file path không bị thay đổi ngoài ý muốn
// 4. String Pool: tiết kiệm bộ nhớ vì chia sẻ reference
```

### 10.3 Các phương thức String quan trọng

```java
String s = "Xin chào Java Programming";

// --- Thông tin chuỗi ---
s.length();                   // 27 — Độ dài
s.isEmpty();                  // false — Kiểm tra rỗng ("")
s.isBlank();                  // false — (Java 11+) Kiểm tra rỗng hoặc chỉ whitespace

// --- Truy cập ký tự ---
s.charAt(0);                  // 'X' — Ký tự tại vị trí
s.codePointAt(0);             // 88 — Mã Unicode của ký tự

// --- Tìm kiếm ---
s.indexOf("chào");            // 4 — Vị trí đầu tiên (-1 nếu không có)
s.lastIndexOf("a");           // 24 — Vị trí cuối cùng
s.contains("Java");           // true — Kiểm tra chứa
s.startsWith("Xin");          // true — Bắt đầu bằng
s.endsWith("ing");            // true — Kết thúc bằng

// --- Cắt chuỗi ---
s.substring(4);               // "chào Java Programming" — Từ index 4 đến hết
s.substring(4, 8);            // "chào" — Từ index 4 đến 7 [from, to)

// --- Biến đổi ---
s.toUpperCase();              // "XIN CHÀO JAVA PROGRAMMING"
s.toLowerCase();              // "xin chào java programming"
s.trim();                     // Xóa khoảng trắng đầu cuối
s.strip();                    // (Java 11+) Xóa whitespace Unicode
s.stripLeading();             // Xóa whitespace đầu
s.stripTrailing();            // Xóa whitespace cuối

// --- Thay thế ---
s.replace('a', 'o');          // "Xin choo Jovo Progromming"
s.replace("Java", "Python");  // "Xin chào Python Programming"
s.replaceAll("\\s+", "-");    // "Xin-chào-Java-Programming" (regex)
s.replaceFirst("\\s", "-");   // "Xin-chào Java Programming" (regex, chỉ lần đầu)

// --- Tách & Nối ---
String csv = "An,Bình,Cường,Dũng";
String[] parts = csv.split(",");      // ["An", "Bình", "Cường", "Dũng"]
String joined = String.join(" - ", parts); // "An - Bình - Cường - Dũng"

// --- So sánh ---
"abc".equals("abc");            // true (so sánh giá trị)
"abc".equalsIgnoreCase("ABC");  // true (không phân biệt hoa/thường)
"abc".compareTo("abd");         // -1 (so sánh từ điển: a < b)

// --- Format ---
String msg = String.format("Tên: %s, Tuổi: %d, Điểm: %.2f", "An", 20, 8.5);
// "Tên: An, Tuổi: 20, Điểm: 8.50"

String msg2 = "Tên: %s, Tuổi: %d".formatted("An", 20); // Java 15+

// --- Java 11+ Methods ---
"  ".isBlank();               // true
" Hello ".strip();            // "Hello"
"Ha\n".repeat(3);             // "Ha\nHa\nHa\n"
"A\nB\nC".lines().toList();   // [A, B, C]
```

### 10.4 StringBuilder và StringBuffer

```java
// StringBuilder — hiệu quả khi nối chuỗi nhiều lần (NOT thread-safe)
StringBuilder sb = new StringBuilder();
sb.append("Hello");
sb.append(" ");
sb.append("World");
sb.insert(5, ",");           // "Hello, World"
sb.delete(5, 6);             // "Hello World"
sb.replace(6, 11, "Java");  // "Hello Java"
sb.reverse();                // "avaJ olleH"
String result = sb.toString();
sb.length();                 // Độ dài hiện tại
sb.capacity();               // Dung lượng bộ đệm

// Performance: Nối chuỗi trong vòng lặp
// ❌ BAD — tạo nhiều String object
String s = "";
for (int i = 0; i < 10000; i++) {
    s += i;  // Mỗi lần tạo String mới → O(n²) overall
}

// ✅ GOOD — StringBuilder
StringBuilder sb2 = new StringBuilder();
for (int i = 0; i < 10000; i++) {
    sb2.append(i);  // Thay đổi tại chỗ → O(n) overall
}
String result2 = sb2.toString();
```

| Đặc điểm | String | StringBuilder | StringBuffer |
|-----------|--------|---------------|-------------|
| Mutable | Không (immutable) | Có (mutable) | Có (mutable) |
| Thread-safe | Có (immutable) | **Không** | **Có** (synchronized) |
| Hiệu suất | Chậm khi nối nhiều | **Nhanh nhất** | Chậm hơn StringBuilder |
| Khi nào dùng | Ít thao tác | Nối chuỗi nhiều (single-thread) | Nối chuỗi nhiều (multi-thread) |

### 10.5 Text Block (Java 15+)

```java
// Chuỗi nhiều dòng — trước Java 15
String json = "{\n" +
    "  \"name\": \"An\",\n" +
    "  \"age\": 20\n" +
    "}";

// Text Block — Java 15+
String jsonBlock = """
    {
      "name": "An",
      "age": 20
    }
    """;

// Text Block với HTML
String html = """
    <html>
        <body>
            <h1>Xin chào</h1>
        </body>
    </html>
    """;

// Text Block với SQL
String sql = """
    SELECT s.name, s.score
    FROM students s
    WHERE s.score > 8.0
    ORDER BY s.score DESC
    """;
```

---

## 11. Phương thức (Methods)

### 11.1 Cú pháp khai báo

```java
// [access_modifier] [static] [final] returnType methodName(parameterList) [throws ExceptionList] {
//     // Thân phương thức
//     return giáTrị; // nếu returnType không phải void
// }

public class MethodDemo {

    // Phương thức không trả về giá trị (void)
    public void xinChao(String ten) {
        System.out.println("Xin chào, " + ten + "!");
    }

    // Phương thức trả về giá trị
    public int tinhTong(int a, int b) {
        return a + b;
    }

    // Phương thức static — gọi mà không cần tạo object
    public static double tinhDienTichHinhTron(double banKinh) {
        return Math.PI * banKinh * banKinh;
    }

    // Phương thức với nhiều tham số
    public String taoThongTin(String ten, int tuoi, String diaChi) {
        return String.format("Tên: %s, Tuổi: %d, Địa chỉ: %s", ten, tuoi, diaChi);
    }

    public static void main(String[] args) {
        MethodDemo demo = new MethodDemo();

        demo.xinChao("An");                     // Xin chào, An!
        int tong = demo.tinhTong(3, 5);         // 8

        // Gọi static method qua tên class
        double dt = MethodDemo.tinhDienTichHinhTron(5);
        System.out.println("Diện tích: " + dt); // 78.54
    }
}
```

### 11.2 Truyền tham số: Pass by Value

> **Quan trọng:** Java luôn truyền tham số theo **giá trị** (pass by value).
> - Với **primitive**: sao chép giá trị → thay đổi trong method không ảnh hưởng ngoài.
> - Với **reference**: sao chép **reference** (địa chỉ) → method có thể thay đổi **nội dung** object, nhưng không thể thay đổi reference gốc.

```java
public class PassByValueDemo {

    // Primitive: thay đổi KHÔNG ảnh hưởng ngoài
    static void thayDoiSo(int x) {
        x = 100;    // Chỉ thay đổi bản sao
    }

    // Reference: thay đổi NỘI DUNG object — ảnh hưởng ngoài
    static void thayDoiList(List<String> list) {
        list.add("New Item");   // Thay đổi nội dung object → thấy từ bên ngoài
    }

    // Reference: thay đổi REFERENCE — KHÔNG ảnh hưởng ngoài
    static void ganMoiList(List<String> list) {
        list = new ArrayList<>();  // Chỉ thay đổi bản sao reference
        list.add("Item in new list");
    }

    public static void main(String[] args) {
        int num = 5;
        thayDoiSo(num);
        System.out.println(num);       // 5 (không đổi)

        List<String> myList = new ArrayList<>(List.of("A", "B"));
        thayDoiList(myList);
        System.out.println(myList);    // [A, B, New Item] (đã thay đổi)

        ganMoiList(myList);
        System.out.println(myList);    // [A, B, New Item] (không đổi reference gốc)
    }
}
```

### 11.3 Method Overloading (Nạp chồng)

Cùng tên phương thức, **khác danh sách tham số** (số lượng, kiểu, thứ tự).

```java
public class Calculator {
    // Overload: cùng tên, khác tham số
    public int tinhTong(int a, int b) {
        return a + b;
    }

    public int tinhTong(int a, int b, int c) {
        return a + b + c;
    }

    public double tinhTong(double a, double b) {
        return a + b;
    }

    public String tinhTong(String a, String b) {
        return a + b;  // Nối chuỗi
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        System.out.println(calc.tinhTong(1, 2));          // 3 (int)
        System.out.println(calc.tinhTong(1, 2, 3));       // 6 (int, 3 params)
        System.out.println(calc.tinhTong(1.5, 2.5));      // 4.0 (double)
        System.out.println(calc.tinhTong("Hello", " World")); // "Hello World"
    }
}
```

> **Lưu ý:** Overloading phân biệt qua **tham số**, KHÔNG phải return type. Hai method chỉ khác return type sẽ gây lỗi biên dịch.

### 11.4 Varargs (Variable Arguments)

```java
// Varargs — số lượng tham số thay đổi
public int tinhTong(int... numbers) {
    int sum = 0;
    for (int n : numbers) {
        sum += n;
    }
    return sum;
}

// Sử dụng
tinhTong();           // 0
tinhTong(1);          // 1
tinhTong(1, 2, 3);    // 6
tinhTong(1, 2, 3, 4, 5); // 15

// Varargs phải là THAM SỐ CUỐI CÙNG
public void inThongTin(String label, int... values) {
    System.out.print(label + ": ");
    for (int v : values) System.out.print(v + " ");
    System.out.println();
}

inThongTin("Điểm", 8, 9, 7);  // Điểm: 8 9 7
```

### 11.5 Đệ quy (Recursion)

```java
public class RecursionDemo {

    // Tính giai thừa: n! = n * (n-1)!
    public static long giaiThua(int n) {
        if (n <= 1) return 1;         // Base case
        return n * giaiThua(n - 1);   // Recursive case
    }

    // Fibonacci: F(n) = F(n-1) + F(n-2)
    public static int fibonacci(int n) {
        if (n <= 0) return 0;         // Base case
        if (n == 1) return 1;         // Base case
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    // Tìm kiếm nhị phân đệ quy
    public static int binarySearch(int[] arr, int target, int left, int right) {
        if (left > right) return -1;  // Không tìm thấy
        int mid = left + (right - left) / 2;
        if (arr[mid] == target) return mid;
        if (arr[mid] < target) return binarySearch(arr, target, mid + 1, right);
        return binarySearch(arr, target, left, mid - 1);
    }

    public static void main(String[] args) {
        System.out.println(giaiThua(5));     // 120
        System.out.println(fibonacci(10));    // 55
    }
}
```

> **Lưu ý:** Đệ quy luôn cần **base case** (điều kiện dừng) để tránh StackOverflowError. Đệ quy sâu nên cân nhắc dùng vòng lặp thay thế.

---

## 12. Nhập xuất dữ liệu

### 12.1 Xuất dữ liệu (Output)

```java
// System.out — OutputStream chuẩn
System.out.print("Hello");        // In không xuống dòng
System.out.println("Hello");      // In và xuống dòng
System.out.println();             // Xuống dòng

// printf — In có định dạng (format string)
System.out.printf("Tên: %s, Tuổi: %d%n", "An", 20);
System.out.printf("Điểm: %.2f%n", 8.567);    // 8.57 (2 chữ số thập phân)
System.out.printf("Hex: %x, Oct: %o%n", 255, 255); // ff, 377

// Các format specifier phổ biến
// %d — số nguyên (int, long)
// %f — số thực (float, double)
// %s — chuỗi (String)
// %c — ký tự (char)
// %b — boolean
// %n — xuống dòng (platform-independent)
// %% — ký tự %
// %-10s — căn trái, rộng 10 ký tự
// %10d — căn phải, rộng 10 ký tự
// %05d — đệm số 0, rộng 5 ký tự

System.out.printf("%-10s | %5d | %8.2f%n", "An", 20, 8.5);
System.out.printf("%-10s | %5d | %8.2f%n", "Bình", 22, 9.2);
// Output:
// An         |    20 |     8.50
// Bình       |    22 |     9.20
```

### 12.2 Nhập dữ liệu với Scanner

```java
import java.util.Scanner;

public class InputDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Nhập chuỗi
        System.out.print("Nhập tên: ");
        String ten = scanner.nextLine();          // Đọc cả dòng

        // Nhập số nguyên
        System.out.print("Nhập tuổi: ");
        int tuoi = scanner.nextInt();             // Đọc int
        scanner.nextLine();                        // Xóa ký tự xuống dòng còn sót

        // Nhập số thực
        System.out.print("Nhập điểm: ");
        double diem = scanner.nextDouble();

        // Nhập boolean
        System.out.print("Là sinh viên (true/false): ");
        boolean laSV = scanner.nextBoolean();

        System.out.printf("Tên: %s, Tuổi: %d, Điểm: %.1f, SV: %b%n",
            ten, tuoi, diem, laSV);

        scanner.close();  // Đóng Scanner khi xong
    }
}
```

> **Lỗi phổ biến:** Sau khi gọi `nextInt()`, `nextDouble()`, v.v., ký tự `\n` vẫn còn trong buffer. Cần gọi `scanner.nextLine()` để xóa trước khi đọc chuỗi tiếp theo.

### 12.3 Kiểm tra input an toàn

```java
Scanner scanner = new Scanner(System.in);

// Kiểm tra trước khi đọc
System.out.print("Nhập số: ");
if (scanner.hasNextInt()) {
    int so = scanner.nextInt();
    System.out.println("Bạn đã nhập: " + so);
} else {
    System.out.println("Đó không phải là số nguyên!");
    scanner.next(); // Bỏ qua input không hợp lệ
}

// Vòng lặp nhập cho đến khi hợp lệ
int so;
while (true) {
    System.out.print("Nhập số nguyên dương: ");
    if (scanner.hasNextInt()) {
        so = scanner.nextInt();
        if (so > 0) break;
        System.out.println("Số phải dương!");
    } else {
        System.out.println("Vui lòng nhập số nguyên!");
        scanner.next();
    }
}
```

### 12.4 Đọc/Ghi file

```java
import java.io.*;
import java.nio.file.*;
import java.util.List;

public class FileIODemo {

    // --- NIO (Java 7+) — Khuyên dùng ---

    // Đọc toàn bộ file thành String
    public static String docFile(String duongDan) throws IOException {
        return Files.readString(Path.of(duongDan));
    }

    // Đọc file thành danh sách dòng
    public static List<String> docTungDong(String duongDan) throws IOException {
        return Files.readAllLines(Path.of(duongDan));
    }

    // Ghi file (tạo mới hoặc ghi đè)
    public static void ghiFile(String duongDan, String noiDung) throws IOException {
        Files.writeString(Path.of(duongDan), noiDung);
    }

    // Ghi thêm vào cuối file
    public static void ghiThem(String duongDan, String noiDung) throws IOException {
        Files.writeString(Path.of(duongDan), noiDung,
            StandardOpenOption.APPEND, StandardOpenOption.CREATE);
    }

    // --- Đọc file lớn với Stream (không load toàn bộ vào RAM) ---
    public static long demDongChuaJava(String duongDan) throws IOException {
        try (var stream = Files.lines(Path.of(duongDan))) {
            return stream.filter(line -> line.contains("Java")).count();
        }
    }

    // --- I/O truyền thống với try-with-resources ---
    public static void docVoiBufferedReader(String duongDan) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(duongDan))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } // reader tự động close
    }

    public static void main(String[] args) throws IOException {
        // Ghi file
        ghiFile("test.txt", "Dòng 1\nDòng 2\nDòng 3");

        // Đọc file
        String content = docFile("test.txt");
        System.out.println(content);

        // Đọc từng dòng
        List<String> lines = docTungDong("test.txt");
        lines.forEach(System.out::println);

        // Ghi thêm
        ghiThem("test.txt", "\nDòng 4");
    }
}
```

### 12.5 Nhập xuất với Console

```java
// Console — thay thế Scanner cho ứng dụng console (hỗ trợ đọc mật khẩu)
Console console = System.console();
if (console != null) {
    String username = console.readLine("Username: ");
    char[] password = console.readPassword("Password: "); // Không hiển thị
    System.out.println("Đăng nhập: " + username);
    Arrays.fill(password, ' '); // Xóa mật khẩu khỏi bộ nhớ
}
```

---

## 13. Best Practices

### 13.1 Đặt tên và code style

```java
// ✅ GOOD — Tên có ý nghĩa, rõ ràng
int soLuongSinhVien = 50;
double tongDiem = 0.0;
boolean daDangNhap = false;

// ❌ BAD — Tên không rõ ràng
int x = 50;
double td = 0.0;
boolean flag = false;

// ✅ GOOD — Method tên là động từ, mô tả hành động
public double tinhDiemTrungBinh(List<Double> dsDiem) { ... }
public boolean kiemTraHopLe(String input) { ... }

// ❌ BAD — Method tên không rõ ràng
public double calc(List<Double> d) { ... }
public boolean check(String s) { ... }
```

### 13.2 Nguyên tắc viết code sạch

**1. KISS — Keep It Simple, Stupid**
```java
// ❌ Phức tạp hóa
public boolean isEven(int n) {
    if (n % 2 == 0) {
        return true;
    } else {
        return false;
    }
}

// ✅ Đơn giản
public boolean isEven(int n) {
    return n % 2 == 0;
}
```

**2. DRY — Don't Repeat Yourself**
```java
// ❌ Code lặp lại
public void inThongTinSV1() {
    System.out.println("Tên: An");
    System.out.println("Tuổi: 20");
}
public void inThongTinSV2() {
    System.out.println("Tên: Bình");
    System.out.println("Tuổi: 22");
}

// ✅ Tái sử dụng
public void inThongTin(String ten, int tuoi) {
    System.out.printf("Tên: %s, Tuổi: %d%n", ten, tuoi);
}
```

**3. Sử dụng hằng số thay vì magic numbers**
```java
// ❌ Magic number
if (tuoi >= 18) { ... }
double giamGia = giaTien * 0.1;

// ✅ Hằng số có tên
static final int TUOI_THANH_NIEN = 18;
static final double TY_LE_GIAM_GIA = 0.1;

if (tuoi >= TUOI_THANH_NIEN) { ... }
double giamGia = giaTien * TY_LE_GIAM_GIA;
```

### 13.3 Xử lý lỗi đúng cách

```java
// ✅ Bắt exception cụ thể
try {
    int result = Integer.parseInt(input);
} catch (NumberFormatException e) {
    System.out.println("Input không phải số: " + e.getMessage());
}

// ❌ Bắt exception chung chung
try {
    // code
} catch (Exception e) {
    // Không biết lỗi gì
}

// ✅ Dùng try-with-resources cho resource management
try (Scanner scanner = new Scanner(new File("data.txt"))) {
    while (scanner.hasNextLine()) {
        System.out.println(scanner.nextLine());
    }
} catch (FileNotFoundException e) {
    System.err.println("File không tồn tại: " + e.getMessage());
}

// ❌ Không bắt exception rồi bỏ qua (swallow exception)
try {
    // code
} catch (IOException e) {
    // Im lặng — KHÔNG BAO GIỜ làm thế này!
}
```

### 13.4 Sử dụng Collections đúng cách

```java
// ✅ Khai báo bằng interface type
List<String> names = new ArrayList<>();
Map<String, Integer> scores = new HashMap<>();
Set<String> uniqueNames = new HashSet<>();

// ❌ Khai báo bằng implementation type
ArrayList<String> names = new ArrayList<>();

// ✅ Dùng List.of(), Map.of() cho immutable collections
List<String> colors = List.of("Red", "Green", "Blue");
Map<String, Integer> ages = Map.of("An", 20, "Bình", 22);

// ✅ Dùng Optional thay vì trả về null
public Optional<String> timTenTheoId(int id) {
    // ...
    return Optional.ofNullable(result);
}
```

### 13.5 Performance tips

```java
// ✅ StringBuilder cho nối chuỗi trong vòng lặp
StringBuilder sb = new StringBuilder();
for (String item : items) sb.append(item).append(", ");

// ✅ Dùng == cho enum, không dùng equals
if (color == Color.RED) { ... }

// ✅ Tránh tạo object không cần thiết
// BAD: new Integer(5) → deprecated
// GOOD: Integer.valueOf(5) → dùng cache

// ✅ Đóng resource đúng cách
try (var stream = Files.lines(path)) {
    // sử dụng stream
} // tự động đóng
```

---

## 14. Bài tập thực hành

### Bài 1: Máy tính đơn giản (Cơ bản)

Viết chương trình nhận 2 số và phép toán (+, -, *, /) từ người dùng, thực hiện phép tính và in kết quả.

```java
import java.util.Scanner;

public class MayTinh {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhập số thứ nhất: ");
        double a = sc.nextDouble();

        System.out.print("Nhập phép toán (+, -, *, /): ");
        char phepToan = sc.next().charAt(0);

        System.out.print("Nhập số thứ hai: ");
        double b = sc.nextDouble();

        double ketQua = switch (phepToan) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if (b == 0) {
                    System.out.println("Lỗi: Không thể chia cho 0!");
                    yield Double.NaN;
                }
                yield a / b;
            }
            default -> {
                System.out.println("Phép toán không hợp lệ!");
                yield Double.NaN;
            }
        };

        if (!Double.isNaN(ketQua)) {
            System.out.printf("%.2f %c %.2f = %.2f%n", a, phepToan, b, ketQua);
        }
        sc.close();
    }
}
```

### Bài 2: Kiểm tra số nguyên tố (Mảng + Vòng lặp)

```java
public class SoNguyenTo {
    public static boolean laSoNguyenTo(int n) {
        if (n < 2) return false;
        if (n < 4) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;

        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }

    // Sàng Eratosthenes — tìm tất cả số nguyên tố đến N
    public static boolean[] sangEratosthenes(int n) {
        boolean[] isPrime = new boolean[n + 1];
        java.util.Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;

        for (int i = 2; i * i <= n; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= n; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        return isPrime;
    }

    public static void main(String[] args) {
        // Kiểm tra từng số
        System.out.println(laSoNguyenTo(17));  // true
        System.out.println(laSoNguyenTo(20));  // false

        // Tìm tất cả số nguyên tố đến 50
        boolean[] isPrime = sangEratosthenes(50);
        System.out.print("Số nguyên tố đến 50: ");
        for (int i = 2; i <= 50; i++) {
            if (isPrime[i]) System.out.print(i + " ");
        }
        // 2 3 5 7 11 13 17 19 23 29 31 37 41 43 47
    }
}
```

### Bài 3: Quản lý danh sách sinh viên (Tổng hợp)

```java
import java.util.*;

public class QuanLySinhVien {
    static List<String> danhSach = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int luaChon;
        do {
            System.out.println("\n=== QUẢN LÝ SINH VIÊN ===");
            System.out.println("1. Thêm sinh viên");
            System.out.println("2. Xem danh sách");
            System.out.println("3. Tìm kiếm");
            System.out.println("4. Xóa sinh viên");
            System.out.println("5. Sắp xếp");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            luaChon = sc.nextInt();
            sc.nextLine();

            switch (luaChon) {
                case 1 -> themSinhVien();
                case 2 -> xemDanhSach();
                case 3 -> timKiem();
                case 4 -> xoaSinhVien();
                case 5 -> sapXep();
                case 0 -> System.out.println("Tạm biệt!");
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (luaChon != 0);
    }

    static void themSinhVien() {
        System.out.print("Nhập tên: ");
        String ten = sc.nextLine();
        danhSach.add(ten);
        System.out.println("Đã thêm: " + ten);
    }

    static void xemDanhSach() {
        if (danhSach.isEmpty()) {
            System.out.println("Danh sách trống!");
            return;
        }
        for (int i = 0; i < danhSach.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, danhSach.get(i));
        }
    }

    static void timKiem() {
        System.out.print("Nhập từ khóa: ");
        String keyword = sc.nextLine().toLowerCase();
        List<String> ketQua = danhSach.stream()
            .filter(s -> s.toLowerCase().contains(keyword))
            .toList();

        if (ketQua.isEmpty()) {
            System.out.println("Không tìm thấy!");
        } else {
            ketQua.forEach(System.out::println);
        }
    }

    static void xoaSinhVien() {
        xemDanhSach();
        System.out.print("Nhập số thứ tự cần xóa: ");
        int stt = sc.nextInt();
        if (stt >= 1 && stt <= danhSach.size()) {
            String removed = danhSach.remove(stt - 1);
            System.out.println("Đã xóa: " + removed);
        } else {
            System.out.println("Số thứ tự không hợp lệ!");
        }
    }

    static void sapXep() {
        Collections.sort(danhSach);
        System.out.println("Đã sắp xếp theo thứ tự A-Z");
        xemDanhSach();
    }
}
```

### Bài 4: Xử lý chuỗi (String)

```java
public class XuLyChuoi {
    // Đếm số từ trong chuỗi
    public static int demTu(String s) {
        if (s == null || s.isBlank()) return 0;
        return s.trim().split("\\s+").length;
    }

    // Đảo ngược từng từ
    public static String daoNguocTungTu(String s) {
        String[] words = s.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(new StringBuilder(word).reverse()).append(" ");
        }
        return result.toString().trim();
    }

    // Kiểm tra chuỗi palindrome
    public static boolean laPalindrome(String s) {
        String cleaned = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        return cleaned.equals(new StringBuilder(cleaned).reverse().toString());
    }

    // Đếm tần suất ký tự
    public static Map<Character, Integer> demTanSuat(String s) {
        Map<Character, Integer> freq = new LinkedHashMap<>();
        for (char c : s.toLowerCase().toCharArray()) {
            if (Character.isLetter(c)) {
                freq.merge(c, 1, Integer::sum);
            }
        }
        return freq;
    }

    public static void main(String[] args) {
        System.out.println(demTu("Xin chào Java"));           // 3
        System.out.println(daoNguocTungTu("Hello World"));     // "olleH dlroW"
        System.out.println(laPalindrome("A man a plan a canal Panama")); // true
        System.out.println(demTanSuat("Hello Java"));
        // {h=1, e=1, l=2, o=1, j=1, a=2, v=1}
    }
}
```

---

## 15. Object trong Java

### 15.1 Object là gì

Trong Java, **Object** (đối tượng) là một thực thể (instance) của một **Class** (lớp). Object chứa:
- **State** (trạng thái): các giá trị của biến instance (fields/attributes)
- **Behavior** (hành vi): các phương thức (methods) mà object có thể thực hiện
- **Identity** (danh tính): mỗi object có địa chỉ duy nhất trong bộ nhớ

```java
// Class là bản thiết kế (blueprint)
public class SinhVien {
    // State (thuộc tính)
    String hoTen;
    int tuoi;
    double diem;

    // Behavior (hành vi)
    public void hocBai() {
        System.out.println(hoTen + " đang học bài");
    }

    public String xepLoai() {
        if (diem >= 8) return "Giỏi";
        if (diem >= 6.5) return "Khá";
        if (diem >= 5) return "Trung bình";
        return "Yếu";
    }
}

// Object là thực thể cụ thể từ bản thiết kế
SinhVien sv1 = new SinhVien(); // sv1 là object, SinhVien là class
sv1.hoTen = "Nguyễn Văn An";
sv1.tuoi = 20;
sv1.diem = 8.5;
sv1.hocBai();                  // "Nguyễn Văn An đang học bài"
```

### 15.2 Cách tạo Object

Có **5 cách** tạo object trong Java:

```java
// Cách 1: new (phổ biến nhất)
SinhVien sv1 = new SinhVien();

// Cách 2: Class.forName().newInstance() — dùng Reflection (deprecated)
SinhVien sv2 = (SinhVien) Class.forName("SinhVien").getDeclaredConstructor().newInstance();

// Cách 3: clone() — sao chép object (class phải implement Cloneable)
SinhVien sv3 = (SinhVien) sv1.clone();

// Cách 4: Deserialization — đọc object từ stream
ObjectInputStream in = new ObjectInputStream(new FileInputStream("sv.dat"));
SinhVien sv4 = (SinhVien) in.readObject();

// Cách 5: Factory method — dùng method trả về object
SinhVien sv5 = SinhVien.taoMoi("Trần B", 22, 7.5);
```

**Constructor (Hàm khởi tạo):**

```java
public class SinhVien {
    String hoTen;
    int tuoi;
    double diem;

    // Constructor mặc định (no-arg)
    public SinhVien() {
        this.hoTen = "Chưa có tên";
        this.tuoi = 0;
        this.diem = 0.0;
    }

    // Constructor có tham số
    public SinhVien(String hoTen, int tuoi, double diem) {
        this.hoTen = hoTen;   // this phân biệt field và parameter
        this.tuoi = tuoi;
        this.diem = diem;
    }

    // Constructor gọi constructor khác (constructor chaining)
    public SinhVien(String hoTen) {
        this(hoTen, 18, 0.0);  // Gọi constructor 3 tham số
    }

    // Copy constructor
    public SinhVien(SinhVien other) {
        this.hoTen = other.hoTen;
        this.tuoi = other.tuoi;
        this.diem = other.diem;
    }
}

// Sử dụng
SinhVien sv1 = new SinhVien();                       // Constructor mặc định
SinhVien sv2 = new SinhVien("An", 20, 8.5);         // Constructor 3 tham số
SinhVien sv3 = new SinhVien("Bình");                 // Constructor 1 tham số
SinhVien sv4 = new SinhVien(sv2);                    // Copy constructor
```

### 15.3 Cấu trúc của Object trong bộ nhớ

Khi tạo object bằng `new`, JVM cấp phát bộ nhớ trên **Heap** với cấu trúc:

```
┌─────────────────────────────────────────┐
│              Object Header               │
│  ┌──────────────────────────────────────┐│
│  │ Mark Word (8 bytes trên 64-bit)      ││
│  │  - hashCode                          ││
│  │  - GC age (4 bit)                    ││
│  │  - lock state                        ││
│  │  - biased locking info               ││
│  ├──────────────────────────────────────┤│
│  │ Class Pointer (4-8 bytes)            ││
│  │  - trỏ đến metadata của class        ││
│  ├──────────────────────────────────────┤│
│  │ Array Length (4 bytes, chỉ cho array) ││
│  └──────────────────────────────────────┘│
├─────────────────────────────────────────┤
│              Instance Data               │
│  - hoTen: reference → String "An"       │
│  - tuoi: 20 (int, 4 bytes)             │
│  - diem: 8.5 (double, 8 bytes)         │
├─────────────────────────────────────────┤
│              Padding                     │
│  - Căn chỉnh đến bội số 8 bytes        │
└─────────────────────────────────────────┘
```

| Phần | Kích thước | Mô tả |
|------|-----------|-------|
| **Mark Word** | 8 bytes (64-bit) | HashCode, trạng thái GC, thông tin lock |
| **Class Pointer** | 4 bytes (compressed) | Trỏ đến metadata class trong Metaspace |
| **Instance Data** | Tùy thuộc fields | Giá trị các biến instance |
| **Padding** | 0-7 bytes | Căn chỉnh cho bội số 8 bytes |

### 15.4 Vòng đời của Object

```
  1. Created         2. In Use          3. Invisible       4. Unreachable      5. Collected
  ┌─────────┐     ┌─────────────┐    ┌─────────────┐    ┌──────────────┐    ┌───────────┐
  │ new SV() │ ──→ │ sv.hocBai() │ ──→│ Ra khỏi scope│ ──→│ Không còn ref│ ──→│ GC thu hồi│
  └─────────┘     └─────────────┘    └─────────────┘    └──────────────┘    └───────────┘
```

```java
public void demo() {
    // 1. Created: Object được tạo trên Heap
    SinhVien sv = new SinhVien("An", 20, 8.5);

    // 2. In Use: Object đang được sử dụng
    sv.hocBai();
    System.out.println(sv.xepLoai());

    // 3. Khi method kết thúc, biến sv (reference) bị xóa khỏi Stack
    // 4. Object trên Heap không còn reference → eligible for GC
    // 5. GC sẽ thu hồi bộ nhớ khi cần
}
```

### 15.5 Các phương thức của Object class

Mọi class trong Java đều **kế thừa từ `java.lang.Object`**. Object class cung cấp 11 phương thức:

| Phương thức | Mô tả | Thường override? |
|-------------|-------|-----------------|
| `toString()` | Biểu diễn chuỗi của object | **Có** |
| `equals(Object)` | So sánh nội dung 2 object | **Có** |
| `hashCode()` | Mã hash cho object | **Có** (khi override equals) |
| `getClass()` | Trả về Class object tại runtime | Không |
| `clone()` | Tạo bản sao object | Tùy |
| `finalize()` | Gọi trước khi GC thu hồi (deprecated) | Không |
| `wait()` | Thread chờ trên object | Không |
| `notify()` | Đánh thức 1 thread đang chờ | Không |
| `notifyAll()` | Đánh thức tất cả thread đang chờ | Không |

```java
public class SinhVien {
    private String maSV;
    private String hoTen;
    private double diem;

    // Override toString() — mô tả object dễ đọc
    @Override
    public String toString() {
        return String.format("SinhVien{maSV='%s', hoTen='%s', diem=%.1f}",
            maSV, hoTen, diem);
    }

    // Override equals() — so sánh nội dung (theo maSV)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                        // Cùng reference
        if (o == null || getClass() != o.getClass()) return false; // Khác class
        SinhVien sv = (SinhVien) o;
        return Objects.equals(maSV, sv.maSV);              // So sánh theo maSV
    }

    // Override hashCode() — PHẢI override khi override equals
    @Override
    public int hashCode() {
        return Objects.hash(maSV);
    }
}

// Sử dụng
SinhVien sv1 = new SinhVien("SV001", "An", 8.5);
SinhVien sv2 = new SinhVien("SV001", "An", 8.5);

System.out.println(sv1);              // SinhVien{maSV='SV001', hoTen='An', diem=8.5}
System.out.println(sv1.equals(sv2));  // true (cùng maSV)
System.out.println(sv1 == sv2);       // false (khác reference)
```

### 15.6 So sánh Object (== vs equals)

| Tiêu chí | `==` | `.equals()` |
|----------|------|-------------|
| So sánh | **Reference** (địa chỉ bộ nhớ) | **Nội dung** (giá trị) |
| Primitive | So sánh giá trị | Không áp dụng |
| Object | Cùng object trên Heap? | Giá trị logic bằng nhau? |
| Null-safe | Có (`null == null` → true) | Không (`null.equals()` → NPE) |

```java
// == cho primitive — so sánh giá trị
int a = 5, b = 5;
System.out.println(a == b);          // true

// == cho object — so sánh reference (ĐỊA CHỈ)
String s1 = new String("Hello");
String s2 = new String("Hello");
System.out.println(s1 == s2);        // false (2 object khác nhau)
System.out.println(s1.equals(s2));   // true (cùng nội dung)

// String Pool — trường hợp đặc biệt
String s3 = "Hello";
String s4 = "Hello";
System.out.println(s3 == s4);        // true (cùng reference trong Pool)

// Null-safe comparison
Objects.equals(null, null);            // true
Objects.equals("abc", null);           // false
Objects.equals(null, "abc");           // false (không NPE)
```

### 15.7 Garbage Collection (GC) trong Java

#### 15.7.1 Garbage Collection là gì

**Garbage Collection (GC)** là cơ chế **tự động thu hồi bộ nhớ** trong Java. GC tìm và xóa các object không còn được tham chiếu (reference) trên Heap, giải phóng bộ nhớ cho ứng dụng.

**Đặc điểm chính:**
- **Tự động**: Lập trình viên không cần (và không nên) quản lý bộ nhớ thủ công
- **Non-deterministic**: Không thể biết chính xác khi nào GC chạy
- **Stop-the-world**: Hầu hết GC phải tạm dừng ứng dụng (pause) khi thu gom
- **Chỉ hoạt động trên Heap**: Stack được quản lý tự động theo LIFO

```java
public void demo() {
    // Object được tạo trên Heap
    SinhVien sv = new SinhVien("An");  // [1] Object A trên Heap

    sv = new SinhVien("Bình");         // [2] Object B trên Heap
    // Object A không còn reference → eligible for GC

    sv = null;                          // [3] Object B cũng mất reference
    // Cả Object A và B đều eligible for GC

    // GC có thể thu hồi bất kỳ lúc nào, ta không kiểm soát được
}
```

#### 15.7.2 Cấu trúc Heap Memory

JVM chia Heap thành các vùng (generations) để tối ưu GC:

```
┌───────────────────── Heap Memory ─────────────────────┐
│                                                        │
│  ┌──────────── Young Generation (~1/3) ──────────────┐ │
│  │                                                    │ │
│  │  ┌─────────┐  ┌───────────┐  ┌───────────┐       │ │
│  │  │  Eden    │  │Survivor S0│  │Survivor S1│       │ │
│  │  │(New objs)│  │ (From)    │  │ (To)      │       │ │
│  │  │  ~80%    │  │  ~10%     │  │  ~10%     │       │ │
│  │  └─────────┘  └───────────┘  └───────────┘       │ │
│  └────────────────────────────────────────────────────┘ │
│                                                        │
│  ┌────────── Old Generation (Tenured) (~2/3) ────────┐ │
│  │                                                    │ │
│  │  Object sống lâu (sau nhiều lần GC ở Young Gen)   │ │
│  │                                                    │ │
│  └────────────────────────────────────────────────────┘ │
│                                                        │
│  ┌─────────── Metaspace (ngoài Heap) ────────────────┐ │
│  │  Class metadata, method bytecode, constant pool    │ │
│  │  (Thay thế PermGen từ Java 8)                     │ │
│  └────────────────────────────────────────────────────┘ │
└────────────────────────────────────────────────────────┘
```

| Vùng | Chứa gì | GC Type |
|------|---------|---------|
| **Eden** | Object mới được tạo | Minor GC |
| **Survivor S0, S1** | Object sống sót qua Minor GC | Minor GC |
| **Old Generation** | Object sống lâu (vượt qua ngưỡng age) | Major GC |
| **Metaspace** | Class metadata (ngoài Heap) | Full GC |

#### 15.7.3 Quá trình Garbage Collection

**Minor GC (Young Generation GC):**

```
Bước 1: Object mới tạo → Eden
┌──────────────┐
│ Eden: A B C D │  ← tất cả object mới ở đây
│ S0: (trống)   │
│ S1: (trống)   │
└──────────────┘

Bước 2: Eden đầy → Minor GC
- Đánh dấu object còn sống (reachable)
- Copy object sống sang S0
- Xóa toàn bộ Eden

┌──────────────┐
│ Eden: (trống) │  ← đã xóa
│ S0: A C       │  ← object sống (age=1)
│ S1: (trống)   │
└──────────────┘
  (B, D bị GC vì không còn reference)

Bước 3: Eden đầy lần nữa → Minor GC
- Copy Eden sống + S0 sống → S1
- Xóa Eden + S0

┌──────────────┐
│ Eden: (trống) │
│ S0: (trống)   │
│ S1: A E F     │  ← A(age=2), E,F(age=1)
└──────────────┘
  (C bị GC, E F là object mới từ Eden)

Bước 4: Object đạt ngưỡng age → Promote lên Old Gen
- Default threshold: 15 (tuỳ GC)
- Object A nếu sống đủ lâu → chuyển sang Old Gen
```

**Major GC (Old Generation GC):**
- Xảy ra khi Old Generation sắp đầy
- Chậm hơn Minor GC nhiều lần (vì quét vùng nhớ lớn)
- Thường gây **stop-the-world pause** dài hơn

**Full GC:**
- Thu gom **cả Young + Old + Metaspace**
- Chậm nhất, gây pause dài nhất
- Nên tránh Full GC trong production

#### 15.7.4 Các thuật toán Garbage Collection

**1. Mark and Sweep:**
```
Bước 1: Mark — đánh dấu tất cả object reachable từ GC Roots
  GC Roots: stack variables, static fields, JNI references

                   ┌──┐    ┌──┐    ┌──┐
  GC Root ────────→│ A│───→│ B│───→│ C│  (reachable, đánh dấu ✓)
                   └──┘    └──┘    └──┘
                   ┌──┐    ┌──┐
                   │ D│    │ E│  (unreachable, không đánh dấu ✗)
                   └──┘    └──┘

Bước 2: Sweep — xóa tất cả object KHÔNG được đánh dấu
  → D, E bị xóa, giải phóng bộ nhớ
```

**2. Mark-Sweep-Compact:**
```
Sau Mark-Sweep, bộ nhớ bị phân mảnh (fragmentation):
  [A][_][B][_][_][C][_]

Compact: dồn object sống về một đầu
  [A][B][C][_][_][_][_]
  → Giải quyết phân mảnh, nhưng tốn thời gian di chuyển
```

**3. Copying (dùng trong Young Gen):**
```
Chia bộ nhớ thành 2 vùng: From-space và To-space
- Copy object sống từ From → To
- Xóa toàn bộ From
- Đổi vai trò From ↔ To

Ưu điểm: không phân mảnh, nhanh
Nhược điểm: lãng phí 50% bộ nhớ (nhưng Young Gen nhỏ nên OK)
```

**4. Generational Collection (kết hợp):**
- Young Gen: dùng Copying (nhanh, object sống ngắn)
- Old Gen: dùng Mark-Sweep-Compact (ít chạy, object sống lâu)

#### 15.7.5 Các loại GC Algorithms trong Java

| GC | JVM Flag | Đặc điểm | Phù hợp |
|----|----------|----------|---------|
| **Serial GC** | `-XX:+UseSerialGC` | Single-thread, stop-the-world | Client app, Heap nhỏ (<100MB) |
| **Parallel GC** | `-XX:+UseParallelGC` | Multi-thread, throughput cao | Server, batch processing |
| **CMS** | `-XX:+UseConcMarkSweepGC` | Concurrent mark-sweep, low pause | Web app (deprecated Java 14) |
| **G1 GC** | `-XX:+UseG1GC` | Region-based, balanced | **Default (Java 9+)**, đa số app |
| **ZGC** | `-XX:+UseZGC` | Ultra-low latency (<10ms pause) | Real-time, Heap lớn (TB-level) |
| **Shenandoah** | `-XX:+UseShenandoahGC` | Concurrent compaction | Tương tự ZGC, OpenJDK |

**Serial GC:**
```
Application threads:  ████████████░░░░░████████████
Serial GC:                        ████
                               (stop-the-world, single thread)
```

**Parallel GC:**
```
Application threads:  ████████████░░░████████████
GC Thread 1:                      ██
GC Thread 2:                      ██
GC Thread 3:                      ██
GC Thread 4:                      ██
                               (stop-the-world, multi-thread → nhanh hơn)
```

**G1 GC (Garbage-First):**
```
Heap chia thành nhiều Region (~2048 regions):
┌───┬───┬───┬───┬───┬───┬───┬───┐
│ E │ E │ S │ O │ O │ E │ H │ O │  E=Eden, S=Survivor, O=Old, H=Humongous
├───┼───┼───┼───┼───┼───┼───┼───┤
│ O │ E │ O │   │ O │ S │ O │ E │
└───┴───┴───┴───┴───┴───┴───┴───┘

- Thu gom region có nhiều garbage nhất trước (Garbage-First)
- Có thể thiết lập target pause time: -XX:MaxGCPauseMillis=200
- Mixed GC: thu gom cả Young + một số Old region
```

**ZGC (Z Garbage Collector):**
```
Application threads:  ████████████████████████████████████
ZGC (concurrent):     ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░

- Hầu hết công việc GC diễn ra CONCURRENT (đồng thời) với application
- Pause time < 10ms bất kể heap size (1MB → 16TB)
- Dùng colored pointers và load barriers
```

#### 15.7.6 Khi nào Object bị Garbage Collected

Object trở thành **eligible for GC** khi **không còn strong reference** nào trỏ đến nó:

```java
// Trường hợp 1: Gán null
SinhVien sv = new SinhVien("An");
sv = null;  // Object "An" eligible for GC

// Trường hợp 2: Gán reference mới
SinhVien sv = new SinhVien("An");    // Object A
sv = new SinhVien("Bình");           // Object A eligible for GC

// Trường hợp 3: Ra khỏi scope
public void method() {
    SinhVien sv = new SinhVien("An");
    // ... sử dụng sv
} // sv ra khỏi scope → Object eligible for GC

// Trường hợp 4: Island of Isolation (đảo cô lập)
class Node {
    Node next;
}
Node a = new Node();
Node b = new Node();
a.next = b;
b.next = a;  // a và b tham chiếu chéo
a = null;
b = null;
// Cả 2 object vẫn tham chiếu nhau, nhưng KHÔNG reachable từ GC Root
// → Eligible for GC (GC đủ thông minh để phát hiện island of isolation)
```

**Các loại Reference trong Java:**

| Loại | Cú pháp | GC thu hồi khi | Dùng cho |
|------|---------|---------------|---------|
| **Strong** | `Object o = new Object()` | Không thu hồi khi còn ref | Mặc định, đa số trường hợp |
| **Weak** | `WeakReference<Object>` | Ngay khi GC chạy | Cache, listeners |
| **Soft** | `SoftReference<Object>` | Khi sắp OutOfMemory | Memory-sensitive cache |
| **Phantom** | `PhantomReference<Object>` | Đã bị GC, trước khi deallocate | Resource cleanup |

```java
import java.lang.ref.*;

// Strong reference
Object strong = new Object();  // Không bị GC khi còn strong ref

// Weak reference
WeakReference<Object> weak = new WeakReference<>(new Object());
Object obj = weak.get();       // Lấy object (có thể null nếu đã bị GC)
// Khi không còn strong ref, GC sẽ thu hồi ngay lập tức

// Soft reference
SoftReference<byte[]> cache = new SoftReference<>(new byte[1024 * 1024]);
byte[] data = cache.get();     // null nếu JVM thiếu memory và đã GC
// Chỉ bị GC khi JVM sắp OutOfMemoryError

// WeakHashMap — Map tự động xóa entry khi key không còn strong ref
WeakHashMap<Object, String> map = new WeakHashMap<>();
Object key = new Object();
map.put(key, "value");
key = null;                     // key bị GC → entry tự động xóa khỏi map
```

#### 15.7.7 System.gc() - Có nên sử dụng

```java
// System.gc() chỉ là "gợi ý" cho JVM chạy GC
// JVM có thể IGNORE hoàn toàn
System.gc();                    // Gợi ý GC
Runtime.getRuntime().gc();      // Tương đương

// Tại sao KHÔNG nên dùng System.gc():
// 1. Non-deterministic: JVM có thể bỏ qua
// 2. Performance hit: Full GC gây stop-the-world pause dài
// 3. JVM tự biết khi nào nên GC (hiệu quả hơn)
// 4. Phá vỡ GC ergonomics (tự tối ưu của JVM)
```

> **Kết luận:** Hầu như **KHÔNG BAO GIỜ** nên gọi `System.gc()` trong production code. Chỉ dùng trong testing/benchmarking.

#### 15.7.8 Memory Leaks trong Java

Mặc dù Java có GC, **memory leak vẫn xảy ra** khi object không cần dùng nhưng vẫn có reference.

**1. Collection không được xóa:**
```java
// ❌ Memory Leak: list giữ reference, object không bị GC
static List<Object> cache = new ArrayList<>();

public void process() {
    for (int i = 0; i < 1_000_000; i++) {
        cache.add(new byte[1024]); // Không bao giờ xóa → Heap đầy dần
    }
}

// ✅ Fix: Giới hạn kích thước hoặc dọn dẹp
public void process() {
    for (int i = 0; i < 1_000_000; i++) {
        cache.add(new byte[1024]);
        if (cache.size() > 1000) cache.clear(); // Dọn dẹp khi quá lớn
    }
}
```

**2. Resource không đóng:**
```java
// ❌ Memory Leak: Connection, Stream không đóng
public void readFile() throws IOException {
    FileInputStream fis = new FileInputStream("data.txt");
    // Quên fis.close() → resource leak
}

// ✅ Fix: try-with-resources
public void readFile() throws IOException {
    try (FileInputStream fis = new FileInputStream("data.txt")) {
        // sử dụng fis
    } // tự động close
}
```

**3. Inner class giữ reference đến outer class:**
```java
// ❌ Non-static inner class giữ reference ngầm đến outer
public class Outer {
    private byte[] largeData = new byte[10_000_000]; // 10MB

    class Inner {
        // Inner giữ reference đến Outer → Outer không bị GC
        public void doSomething() { }
    }
}

// ✅ Fix: dùng static inner class
public class Outer {
    private byte[] largeData = new byte[10_000_000];

    static class Inner {
        // Không giữ reference đến Outer
        public void doSomething() { }
    }
}
```

**4. Listeners/Callbacks không unregister:**
```java
// ❌ Register listener nhưng quên unregister
button.addActionListener(e -> handleClick()); // Listener giữ reference đến object

// ✅ Fix: Unregister khi không cần
ActionListener listener = e -> handleClick();
button.addActionListener(listener);
// Khi không cần:
button.removeActionListener(listener);
```

**5. ThreadLocal không clean up:**
```java
// ❌ ThreadLocal trong thread pool → memory leak
static ThreadLocal<byte[]> data = new ThreadLocal<>();
data.set(new byte[1_000_000]);
// Thread pool reuse thread → data không bị GC

// ✅ Fix: luôn remove khi xong
try {
    data.set(new byte[1_000_000]);
    // sử dụng
} finally {
    data.remove(); // LUÔN remove trong finally
}
```

#### 15.7.9 GC Logs và Monitoring

**Bật GC Logs:**
```bash
# Java 9+ (Unified JVM Logging)
java -Xlog:gc*:file=gc.log:time,uptime,level,tags -jar app.jar

# Các option hữu ích
-Xlog:gc:gc.log                     # Basic GC info
-Xlog:gc*:gc.log                    # Chi tiết GC
-Xlog:gc+heap=debug:gc.log          # Thêm thông tin Heap
-Xlog:gc*:gc.log:time,uptime:filecount=5,filesize=10m  # Xoay log
```

**Đọc GC Logs:**
```
[2024-01-15T10:30:15.123+0700][0.456s] GC(0) Pause Young (Normal) (G1 Evacuation Pause)
[2024-01-15T10:30:15.123+0700][0.456s] GC(0)   Eden: 24M → 0M
[2024-01-15T10:30:15.123+0700][0.456s] GC(0)   Survivor: 0M → 3M
[2024-01-15T10:30:15.123+0700][0.456s] GC(0)   Old: 0M → 15M
[2024-01-15T10:30:15.123+0700][0.456s] GC(0)   Heap: 24M → 18M (256M)
[2024-01-15T10:30:15.123+0700][0.456s] GC(0) Pause: 5.2ms
```

**Monitoring Tools:**

| Tool | Loại | Mô tả |
|------|------|-------|
| **jstat** | CLI | Thống kê GC realtime (`jstat -gc <pid> 1000`) |
| **jmap** | CLI | Heap dump (`jmap -dump:format=b,file=heap.hprof <pid>`) |
| **jvisualvm** | GUI | Monitor GC, CPU, Memory, Threads |
| **JConsole** | GUI | JMX-based monitoring |
| **GCViewer** | Tool | Phân tích GC logs |
| **Eclipse MAT** | Tool | Phân tích heap dump, tìm memory leak |

```bash
# jstat — xem GC statistics theo thời gian thực
jstat -gc <pid> 1000   # Cập nhật mỗi 1 giây

# jmap — tạo heap dump
jmap -dump:format=b,file=heap.hprof <pid>

# jcmd — xem thông tin GC
jcmd <pid> GC.heap_info
jcmd <pid> GC.run        # Trigger GC (chỉ dùng cho debugging)
```

#### 15.7.10 Best Practices với GC

**1. Tuning JVM Memory:**
```bash
# Thiết lập kích thước Heap
-Xms512m              # Initial heap size
-Xmx2g                # Maximum heap size
-XX:NewRatio=2         # Old:Young = 2:1

# Ví dụ cho web application
java -Xms1g -Xmx2g -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -jar app.jar

# Ví dụ cho batch processing (throughput ưu tiên)
java -Xms4g -Xmx4g -XX:+UseParallelGC -jar batch.jar

# Ví dụ cho low-latency application
java -Xms4g -Xmx4g -XX:+UseZGC -jar realtime.jar
```

**2. Coding Practices:**
```java
// ✅ Sử dụng object pools cho object tốn kém tạo mới
// Ví dụ: Connection Pool, Thread Pool

// ✅ Giảm object allocation
// BAD: tạo nhiều object tạm
for (int i = 0; i < 1_000_000; i++) {
    String s = new String("value_" + i);
}

// GOOD: dùng StringBuilder
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1_000_000; i++) {
    sb.setLength(0);
    sb.append("value_").append(i);
}

// ✅ Dùng primitive thay vì wrapper khi có thể
int count = 0;           // GOOD: 4 bytes trên stack
// Integer count = 0;    // BAD: object trên Heap, Autoboxing overhead

// ✅ Đóng resource đúng cách (try-with-resources)
try (var reader = new BufferedReader(new FileReader("file.txt"))) {
    // ...
}

// ✅ Set collection initial capacity nếu biết trước kích thước
List<String> list = new ArrayList<>(1000); // Tránh resize nhiều lần
Map<String, Integer> map = new HashMap<>(1000, 0.75f);

// ✅ Tránh String concatenation trong vòng lặp
// ✅ Dùng WeakHashMap cho cache nếu phù hợp
// ✅ Remove listeners khi không cần
// ✅ ThreadLocal.remove() trong thread pool
```

**3. GC Tuning Checklist:**
1. Đo trước, tune sau — profile application trước khi thay đổi GC
2. Bắt đầu từ default (G1 GC) — chỉ thay đổi khi có vấn đề rõ ràng
3. Set `-Xms` = `-Xmx` để tránh heap resize
4. Monitor: GC pause time, throughput, heap usage
5. Tránh Full GC — nguyên nhân thường là: Heap quá nhỏ, Memory leak, quá nhiều long-lived objects

### 15.8 So sánh Stack và Heap

| Đặc điểm | Stack | Heap |
|-----------|-------|------|
| **Lưu trữ** | Local variables, method calls, references | Objects, arrays, instance variables |
| **Quản lý** | Tự động (LIFO), giải phóng khi method kết thúc | Garbage Collector quản lý |
| **Kích thước** | Nhỏ (~512KB-1MB mỗi thread) | Lớn (có thể nhiều GB) |
| **Tốc độ** | Rất nhanh (LIFO, không cần GC) | Chậm hơn (cần GC, allocation phức tạp) |
| **Thread** | Mỗi thread có Stack riêng | Chia sẻ giữa tất cả threads |
| **Lỗi** | StackOverflowError (đệ quy sâu) | OutOfMemoryError (Heap đầy) |
| **Phân mảnh** | Không (LIFO) | Có (cần compaction) |

```java
public class StackVsHeap {
    // Static field → Method Area (Metaspace)
    static int counter = 0;

    public static void main(String[] args) {
        // args → Stack (reference) → Heap (String[] object)
        int x = 10;                    // x → Stack (primitive value)
        String name = "Hello";         // name → Stack (reference)
                                       // "Hello" → Heap (String Pool)

        SinhVien sv = new SinhVien();  // sv → Stack (reference)
                                       // SinhVien object → Heap
        process(x, sv);
    }

    static void process(int num, SinhVien sv) {
        // num → Stack (copy of x)
        // sv → Stack (copy of reference, cùng trỏ đến object trên Heap)
        int local = num * 2;           // local → Stack

        // Khi method process() kết thúc:
        // - num, sv (reference), local bị xóa khỏi Stack
        // - Object SinhVien trên Heap vẫn tồn tại (nếu còn reference khác)
    }
}
```

**Minh họa bộ nhớ:**
```
┌─────── Stack (main thread) ───────┐    ┌────────── Heap ──────────┐
│                                    │    │                          │
│  ┌── process() frame ──────────┐  │    │  ┌──── SinhVien ─────┐  │
│  │ num = 10                     │  │    │  │ hoTen: ref → ─────┼──┤
│  │ sv = ref ──────────────────────────────→│ tuoi: 0            │  │
│  │ local = 20                   │  │    │  │ diem: 0.0          │  │
│  └──────────────────────────────┘  │    │  └────────────────────┘  │
│                                    │    │                          │
│  ┌── main() frame ─────────────┐  │    │  ┌── String Pool ─────┐  │
│  │ args = ref → ────────────────────────→  │ "Hello"             │  │
│  │ x = 10                       │  │    │  └────────────────────┘  │
│  │ name = ref → ─────────────────────────→                        │
│  │ sv = ref → ──────────────────────────→ (cùng SinhVien object)  │
│  └──────────────────────────────┘  │    │                          │
└────────────────────────────────────┘    └──────────────────────────┘
```

---

## 16. Wrapper Classes - Boxing và Unboxing

### 16.1 Wrapper Classes là gì

Mỗi kiểu nguyên thủy (primitive) có một **Wrapper Class** tương ứng — cho phép primitive hoạt động như object.

| Primitive | Wrapper Class | Kích thước Object |
|-----------|--------------|------------------|
| `byte` | `Byte` | ~16 bytes |
| `short` | `Short` | ~16 bytes |
| `int` | `Integer` | ~16 bytes |
| `long` | `Long` | ~24 bytes |
| `float` | `Float` | ~16 bytes |
| `double` | `Double` | ~24 bytes |
| `char` | `Character` | ~16 bytes |
| `boolean` | `Boolean` | ~16 bytes |

### 16.2 Tại sao cần Wrapper Classes?

```java
// 1. Collections không chấp nhận primitive
List<int> list;           // ❌ Lỗi biên dịch!
List<Integer> list;       // ✅ OK

// 2. Cho phép giá trị null (primitive không thể null)
Integer age = null;        // OK — biểu diễn "không có giá trị"
// int age = null;         // ❌ Lỗi!

// 3. Cung cấp utility methods
Integer.parseInt("123");          // String → int
Integer.valueOf("123");           // String → Integer
Integer.MAX_VALUE;                // 2147483647
Integer.MIN_VALUE;                // -2147483648
Integer.toBinaryString(10);      // "1010"
Integer.toHexString(255);        // "ff"

// 4. Generics yêu cầu Object type
public <T> T findMax(List<T> list) { ... }
```

### 16.3 Boxing và Unboxing

**Boxing** (primitive → Wrapper): gói primitive vào object.
**Unboxing** (Wrapper → primitive): lấy primitive từ object.

```java
// Manual Boxing (trước Java 5)
Integer num = Integer.valueOf(42);     // Boxing thủ công

// Manual Unboxing
int value = num.intValue();            // Unboxing thủ công

// Autoboxing (Java 5+) — tự động chuyển đổi
Integer num2 = 42;                     // Autoboxing: int → Integer
int value2 = num2;                     // Auto-unboxing: Integer → int

// Autoboxing trong collection
List<Integer> numbers = new ArrayList<>();
numbers.add(10);                       // Autoboxing: int 10 → Integer.valueOf(10)
int first = numbers.get(0);           // Auto-unboxing: Integer → int

// Autoboxing trong phép toán
Integer a = 10;
Integer b = 20;
int sum = a + b;                       // Auto-unboxing cả a và b, rồi cộng
```

### 16.4 Integer Cache (Caching Pool)

```java
// Java cache Integer từ -128 đến 127
Integer a = 127;
Integer b = 127;
System.out.println(a == b);      // true ← cùng object trong cache!

Integer c = 128;
Integer d = 128;
System.out.println(c == d);      // false ← KHÁC object (ngoài cache)!
System.out.println(c.equals(d)); // true ← cùng giá trị

// Tại sao?
// Integer.valueOf(127) → trả về object từ cache
// Integer.valueOf(128) → tạo object MỚI trên Heap
```

> **Quy tắc vàng:** **LUÔN dùng `.equals()`** để so sánh Wrapper objects, **KHÔNG dùng `==`**.

**Các kiểu có cache:**

| Wrapper | Cache range |
|---------|------------|
| `Byte` | -128 → 127 (toàn bộ) |
| `Short` | -128 → 127 |
| `Integer` | -128 → 127 (mở rộng bằng `-XX:AutoBoxCacheMax=N`) |
| `Long` | -128 → 127 |
| `Character` | 0 → 127 |
| `Boolean` | `TRUE`, `FALSE` (chỉ 2 instance) |
| `Float`, `Double` | **Không cache** |

### 16.5 Pitfalls (Lỗi thường gặp)

**1. NullPointerException khi unboxing null:**
```java
Integer num = null;
int value = num;       // NullPointerException! (auto-unboxing null)

// ✅ Fix: kiểm tra null trước
int value = (num != null) ? num : 0;
// Hoặc dùng Optional
int value = Optional.ofNullable(num).orElse(0);
```

**2. Performance penalty trong vòng lặp:**
```java
// ❌ BAD — autoboxing tạo hàng triệu Integer object
Long sum = 0L;
for (long i = 0; i < 1_000_000; i++) {
    sum += i;   // Auto-unbox → cộng → auto-box → tạo Long mới
}

// ✅ GOOD — dùng primitive
long sum = 0L;
for (long i = 0; i < 1_000_000; i++) {
    sum += i;   // Primitive operation, không tạo object
}
```

**3. == vs equals() cho Wrapper:**
```java
// Primitive: == so sánh giá trị → an toàn
int a = 1000, b = 1000;
System.out.println(a == b);          // true ← so sánh giá trị

// Wrapper: == so sánh reference → NGUY HIỂM
Integer x = 1000, y = 1000;
System.out.println(x == y);          // false ← so sánh reference!
System.out.println(x.equals(y));     // true ← so sánh giá trị
```

### 16.6 Conversion Methods

```java
// String → Primitive
int num1 = Integer.parseInt("123");           // "123" → 123
double num2 = Double.parseDouble("3.14");     // "3.14" → 3.14
boolean flag = Boolean.parseBoolean("true");  // "true" → true

// String → Wrapper
Integer num3 = Integer.valueOf("123");        // "123" → Integer(123)

// Primitive/Wrapper → String
String s1 = String.valueOf(123);              // 123 → "123"
String s2 = Integer.toString(123);            // 123 → "123"
String s3 = "" + 123;                         // Concatenation (ít hiệu quả)

// Giữa các kiểu số
int intVal = 42;
long longVal = intVal;                         // Widening: int → long
byte byteVal = (byte) intVal;                // Narrowing: int → byte
double doubleVal = intVal;                    // Widening: int → double

// Wrapper methods hữu ích
Integer.MAX_VALUE;           // 2147483647
Integer.MIN_VALUE;           // -2147483648
Integer.BYTES;               // 4
Integer.SIZE;                // 32 (bits)
Integer.toBinaryString(10);  // "1010"
Integer.toOctalString(10);   // "12"
Integer.toHexString(255);    // "ff"
Integer.compare(1, 2);       // -1 (1 < 2)
Integer.sum(3, 4);           // 7
Integer.max(3, 4);           // 4
Integer.min(3, 4);           // 3
```

### 16.7 Demo tổng hợp

```java
import java.util.*;
import java.util.stream.*;

public class WrapperDemo {
    public static void main(String[] args) {
        // 1. Autoboxing trong Stream
        int[] primitiveArr = {5, 2, 8, 1, 9};
        List<Integer> boxedList = Arrays.stream(primitiveArr)
            .boxed()                          // int → Integer
            .sorted(Comparator.reverseOrder()) // Sắp xếp giảm dần
            .toList();
        System.out.println(boxedList);        // [9, 8, 5, 2, 1]

        // 2. Null-safe operations
        Integer score = getScore("An");        // Có thể null
        int safeScore = Optional.ofNullable(score).orElse(0);
        System.out.println("Score: " + safeScore);

        // 3. Parsing với error handling
        String input = "abc";
        try {
            int num = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Không phải số: " + input);
        }

        // 4. Type checking
        Number num = 42;  // Autoboxing: int → Integer, Integer extends Number
        if (num instanceof Integer i) {
            System.out.println("Integer: " + i);
        }

        // 5. Character utility
        char c = 'A';
        System.out.println(Character.isLetter(c));     // true
        System.out.println(Character.isDigit(c));      // false
        System.out.println(Character.isUpperCase(c));   // true
        System.out.println(Character.toLowerCase(c));   // 'a'
    }

    static Integer getScore(String name) {
        Map<String, Integer> scores = Map.of("An", 85, "Bình", 90);
        return scores.get(name);  // null nếu không tìm thấy
    }
}
```

---

## 17. Tổng kết

### 17.1 Bản đồ kiến thức Java Core

```
Java Core
├── 1. Nền tảng
│   ├── JDK / JRE / JVM
│   ├── Biên dịch & Thực thi (javac → bytecode → JVM)
│   └── Platform Independence
│
├── 2. Cú pháp cơ bản
│   ├── Kiểu dữ liệu (Primitive & Reference)
│   ├── Biến & Hằng (local, instance, static, final)
│   ├── Toán tử (Arithmetic, Comparison, Logical, Bitwise)
│   ├── Câu điều kiện (if-else, switch)
│   └── Vòng lặp (for, while, do-while, for-each)
│
├── 3. Kiểu dữ liệu phức tạp
│   ├── Mảng (Array) — kích thước cố định, truy cập O(1)
│   ├── Chuỗi (String) — immutable, String Pool
│   ├── StringBuilder / StringBuffer — mutable string
│   └── Wrapper Classes — Boxing / Unboxing
│
├── 4. Phương thức & Cấu trúc
│   ├── Khai báo & Gọi method
│   ├── Pass by Value
│   ├── Overloading
│   ├── Varargs
│   └── Đệ quy (Recursion)
│
├── 5. Nhập xuất (I/O)
│   ├── Scanner — đọc input
│   ├── System.out — xuất output
│   ├── File I/O (NIO)
│   └── Format (printf, String.format)
│
├── 6. Object & Bộ nhớ
│   ├── Object lifecycle (Create → Use → GC)
│   ├── Constructor & this keyword
│   ├── equals() / hashCode() / toString()
│   ├── == vs .equals()
│   ├── Stack vs Heap
│   └── Garbage Collection
│       ├── Generational GC (Young, Old Gen)
│       ├── GC Algorithms (Serial, Parallel, G1, ZGC)
│       ├── Memory Leaks
│       └── GC Tuning & Monitoring
│
└── 7. Best Practices
    ├── Naming Conventions
    ├── KISS, DRY
    ├── Exception Handling
    ├── Resource Management (try-with-resources)
    └── Performance Tips
```

### 17.2 Checklist ôn tập

| Chủ đề | Câu hỏi kiểm tra | Trả lời được? |
|--------|------------------|--------------|
| JDK/JRE/JVM | Phân biệt JDK, JRE, JVM? | ☐ |
| Kiểu dữ liệu | 8 kiểu primitive và kích thước? | ☐ |
| String | Tại sao String là immutable? String Pool hoạt động thế nào? | ☐ |
| == vs equals | Khi nào dùng == và khi nào dùng equals()? | ☐ |
| Pass by Value | Java truyền tham số bằng gì? Reference type thì sao? | ☐ |
| Array vs ArrayList | Khi nào dùng Array, khi nào dùng ArrayList? | ☐ |
| StringBuilder | Tại sao cần StringBuilder khi đã có String? | ☐ |
| Wrapper Classes | Integer cache hoạt động thế nào? | ☐ |
| Boxing/Unboxing | Autoboxing gây vấn đề performance khi nào? | ☐ |
| GC | Object eligible for GC khi nào? | ☐ |
| Stack vs Heap | Primitive lưu ở đâu? Object lưu ở đâu? | ☐ |
| Memory Leak | Java có memory leak không? Cho ví dụ? | ☐ |
| GC Algorithms | G1 GC khác ZGC thế nào? | ☐ |
| Switch Expression | Switch Java 14+ có gì mới? | ☐ |
| var | var dùng ở đâu, không dùng ở đâu? | ☐ |

### 17.3 Tài liệu tham khảo

| Tài liệu | Link | Mô tả |
|----------|------|-------|
| Oracle Java Docs | [docs.oracle.com/javase](https://docs.oracle.com/javase/) | Tài liệu chính thức |
| Java Language Spec | [JLS](https://docs.oracle.com/javase/specs/) | Đặc tả ngôn ngữ |
| Effective Java | Joshua Bloch | 90 best practices |
| Java Concurrency in Practice | Brian Goetz | Đa luồng & đồng bộ |
| Baeldung | [baeldung.com](https://www.baeldung.com/) | Tutorial chi tiết |

### 17.4 Lộ trình học tiếp

Sau khi nắm vững Java Core, tiếp tục với:

1. **OOP nâng cao**: Abstract Class, Interface, Encapsulation, Inheritance, Polymorphism
2. **Collections Framework**: List, Set, Map, Queue, Comparable, Comparator
3. **Generics**: Type parameters, Bounded types, Wildcards, PECS
4. **Lambda & Stream API**: Functional programming trong Java
5. **Exception Handling**: Checked/Unchecked, Custom exceptions
6. **Multi-threading**: Thread, Runnable, ExecutorService, CompletableFuture
7. **Java I/O & NIO**: File handling, Serialization
8. **Java Records, Sealed Classes, Pattern Matching** (Java 17+)
9. **Design Patterns**: Singleton, Factory, Observer, Strategy...
10. **Spring Framework**: Spring Boot, Spring MVC, Spring Security
