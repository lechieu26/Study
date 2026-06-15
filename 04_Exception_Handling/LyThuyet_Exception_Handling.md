# Exception Handling trong Java

## Mục lục

1. [Giới thiệu về Exception Handling](#1-giới-thiệu-về-exception-handling)
2. [Các loại Exception](#2-các-loại-exception)
3. [Cơ chế xử lý Exception](#3-cơ-chế-xử-lý-exception)
4. [Throws và Throw](#4-throws-và-throw)
5. [Custom Exceptions](#5-custom-exceptions)
6. [Exception Best Practices](#6-exception-best-practices)
7. [Common Exceptions](#7-common-exceptions)

---

## 1. Giới thiệu về Exception Handling

### 1.1. Exception là gì?

**Exception** (ngoại lệ) là một sự kiện bất thường xảy ra trong quá trình thực thi chương trình, làm gián đoạn luồng thực thi bình thường. Exception là một đối tượng (object) chứa thông tin về lỗi.

```java
public class ExceptionIntroDemo {
    public static void main(String[] args) {
        // Ví dụ exception xảy ra
        int[] numbers = {1, 2, 3};
        System.out.println(numbers[5]); // ArrayIndexOutOfBoundsException!
        
        // Dòng này KHÔNG được thực thi nếu không xử lý exception
        System.out.println("This line won't execute");
    }
}
```

### 1.2. Tại sao cần Exception Handling?

| Lý do | Giải thích |
|-------|-----------|
| **Tách logic xử lý lỗi** | Code business logic và code xử lý lỗi được tách biệt |
| **Propagating errors** | Exception có thể được truyền lên call stack |
| **Grouping errors** | Phân nhóm và phân biệt các loại lỗi |
| **Bảo vệ chương trình** | Chương trình không crash đột ngột |
| **Clean up resources** | Đảm bảo tài nguyên được giải phóng đúng cách |

```java
// Không có exception handling - code lộn xộn
public int readFile(String path) {
    // Mở file
    // Nếu file không tồn tại → return -1
    // Đọc dữ liệu
    // Nếu format sai → return -2
    // Đóng file
    // Nếu lỗi đóng file → return -3
    return data;
}

// Với exception handling - code rõ ràng
public int readFile(String path) throws IOException {
    try (FileReader reader = new FileReader(path)) {
        return parseData(reader);
    }
    // Tài nguyên tự động được đóng, exception tự động propagate
}
```

### 1.3. Kiến trúc Exception trong Java

```
                    Object
                      |
                  Throwable
                  /       \
            Exception      Error
            /      \         |
    Checked    RuntimeException   OutOfMemoryError
    Exception  (Unchecked)        StackOverflowError
       |            |
  IOException   NullPointerException
  SQLException  ArrayIndexOutOfBoundsException
  ...           IllegalArgumentException
                ClassCastException
                ...
```

**Phân cấp Exception:**

| Class | Mô tả |
|-------|--------|
| **Throwable** | Lớp gốc cho tất cả exceptions và errors |
| **Exception** | Lỗi có thể recover, chương trình nên xử lý |
| **RuntimeException** | Exception không bắt buộc handle (unchecked) |
| **Error** | Lỗi nghiêm trọng, thường không thể recover (JVM-level) |

---

## 2. Các loại Exception

### 2.1. Checked Exceptions

**Checked Exception** là exception mà compiler **bắt buộc** phải xử lý (try-catch hoặc throws). Thường liên quan đến các tác vụ I/O, network, database.

```java
import java.io.*;

public class CheckedExceptionDemo {
    // Cách 1: Dùng try-catch
    public void readFile() {
        try {
            FileReader reader = new FileReader("file.txt");
            BufferedReader br = new BufferedReader(reader);
            String line = br.readLine();
            System.out.println(line);
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("File không tồn tại: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Lỗi đọc file: " + e.getMessage());
        }
    }
    
    // Cách 2: Dùng throws (delegate cho caller xử lý)
    public void readFile2() throws IOException {
        FileReader reader = new FileReader("file.txt");
        BufferedReader br = new BufferedReader(reader);
        String line = br.readLine();
        br.close();
    }
}
```

**Các Checked Exception phổ biến:**
- `IOException` - lỗi I/O
- `FileNotFoundException` - file không tồn tại
- `SQLException` - lỗi database
- `ClassNotFoundException` - class không tìm thấy
- `InterruptedException` - thread bị interrupt
- `ParseException` - lỗi parse dữ liệu

### 2.2. Unchecked Exceptions (Runtime Exceptions)

**Unchecked Exception** (Runtime Exception) là exception **không bắt buộc** phải xử lý. Thường do lỗi logic lập trình.

```java
public class UncheckedExceptionDemo {
    public static void main(String[] args) {
        // NullPointerException
        String str = null;
        // str.length(); // NPE!
        
        // ArrayIndexOutOfBoundsException
        int[] arr = {1, 2, 3};
        // int x = arr[10]; // AIOOBE!
        
        // ArithmeticException
        // int result = 10 / 0; // AE!
        
        // NumberFormatException
        // int num = Integer.parseInt("abc"); // NFE!
        
        // ClassCastException
        Object obj = "Hello";
        // Integer num = (Integer) obj; // CCE!
        
        // IllegalArgumentException
        // Thread.sleep(-1); // IAE!
    }
}
```

**Các Unchecked Exception phổ biến:**
- `NullPointerException` - truy cập null reference
- `ArrayIndexOutOfBoundsException` - index ngoài phạm vi mảng
- `ArithmeticException` - phép toán bất hợp lệ (chia cho 0)
- `ClassCastException` - ép kiểu không hợp lệ
- `IllegalArgumentException` - tham số không hợp lệ
- `NumberFormatException` - format số không hợp lệ
- `ConcurrentModificationException` - modify collection khi đang duyệt
- `UnsupportedOperationException` - phương thức không được hỗ trợ

### 2.3. Errors

**Error** là vấn đề nghiêm trọng ở mức JVM, **không nên catch** trong code bình thường.

```java
public class ErrorDemo {
    // StackOverflowError - đệ quy vô hạn
    public static void infiniteRecursion() {
        infiniteRecursion(); // StackOverflowError
    }
    
    // OutOfMemoryError - hết bộ nhớ
    public static void outOfMemory() {
        List<byte[]> list = new ArrayList<>();
        while (true) {
            list.add(new byte[1024 * 1024]); // Allocate 1MB mỗi lần
        }
    }
    
    public static void main(String[] args) {
        try {
            infiniteRecursion();
        } catch (StackOverflowError e) {
            // Có thể catch nhưng KHÔNG NÊN
            System.out.println("Stack overflow detected!");
        }
    }
}
```

**Các Error phổ biến:**
- `StackOverflowError` - stack bị tràn (đệ quy quá sâu)
- `OutOfMemoryError` - hết bộ nhớ heap
- `NoClassDefFoundError` - class không tìm thấy tại runtime
- `VirtualMachineError` - lỗi JVM

---

## 3. Cơ chế xử lý Exception

### 3.1. Try-Catch-Finally

```java
public class TryCatchFinallyDemo {
    public static void main(String[] args) {
        FileReader reader = null;
        try {
            // Code có thể throw exception
            reader = new FileReader("data.txt");
            int data = reader.read();
            System.out.println("Data: " + (char) data);
            
        } catch (FileNotFoundException e) {
            // Xử lý FileNotFoundException
            System.out.println("File not found: " + e.getMessage());
            
        } catch (IOException e) {
            // Xử lý IOException (cha của FileNotFoundException)
            System.out.println("IO Error: " + e.getMessage());
            
        } finally {
            // LUÔN được thực thi (dù có exception hay không)
            System.out.println("Finally block executed");
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing reader");
            }
        }
    }
}
```

**Quy tắc Try-Catch:**
1. Catch exception con trước, exception cha sau
2. `finally` luôn thực thi (trừ khi `System.exit()` hoặc JVM crash)
3. Có thể có nhiều catch blocks
4. `finally` là optional

```java
// Thứ tự catch blocks
try {
    // code
} catch (FileNotFoundException e) {    // Cụ thể nhất (con)
    // handle
} catch (IOException e) {              // Tổng quát hơn (cha)
    // handle
} catch (Exception e) {                // Tổng quát nhất
    // handle
}
// KHÔNG được đặt Exception trước IOException vì sẽ unreachable code
```

### 3.2. Multi-catch (Java 7+)

```java
public class MultiCatchDemo {
    public static void main(String[] args) {
        try {
            // Code có thể throw nhiều loại exception
            String input = "abc";
            int number = Integer.parseInt(input);
            int[] arr = new int[number];
            arr[10] = 100;
            
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // Catch nhiều exception cùng lúc
            System.out.println("Error: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage());
        }
        
        // Lưu ý: Các exception trong multi-catch KHÔNG được có quan hệ kế thừa
        // catch (IOException | FileNotFoundException e) // Compile error!
        // Vì FileNotFoundException extends IOException
    }
}
```

### 3.3. Try-with-resources (Java 7+)

**Try-with-resources** tự động đóng tài nguyên implement `AutoCloseable` hoặc `Closeable`.

```java
import java.io.*;

public class TryWithResourcesDemo {
    public static void main(String[] args) {
        // Cách cũ (trước Java 7)
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("file.txt"));
            String line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try { reader.close(); } catch (IOException e) { }
            }
        }
        
        // Cách mới (Java 7+) - tự động close
        try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // br tự động được close sau khi try block kết thúc
        
        // Nhiều resources
        try (
            FileInputStream fis = new FileInputStream("input.txt");
            FileOutputStream fos = new FileOutputStream("output.txt");
            BufferedInputStream bis = new BufferedInputStream(fis)
        ) {
            int data;
            while ((data = bis.read()) != -1) {
                fos.write(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Java 9+: Có thể dùng effectively final variable
        BufferedReader br2 = new BufferedReader(new FileReader("file.txt"));
        try (br2) { // Java 9+
            System.out.println(br2.readLine());
        }
    }
    
    // Custom AutoCloseable
    static class MyResource implements AutoCloseable {
        public MyResource() {
            System.out.println("Resource opened");
        }
        
        public void doWork() {
            System.out.println("Working...");
        }
        
        @Override
        public void close() {
            System.out.println("Resource closed automatically");
        }
    }
    
    public void useCustomResource() {
        try (MyResource resource = new MyResource()) {
            resource.doWork();
        }
        // Output:
        // Resource opened
        // Working...
        // Resource closed automatically
    }
}
```

---

## 4. Throws và Throw

### 4.1. throw keyword

**`throw`** dùng để **ném** (tạo) một exception tại thời điểm cụ thể.

```java
public class ThrowDemo {
    
    // Throw để validate input
    public static void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative: " + age);
        }
        if (age > 150) {
            throw new IllegalArgumentException("Age too large: " + age);
        }
        System.out.println("Age set to: " + age);
    }
    
    // Throw custom exception
    public static double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a / b;
    }
    
    // Throw checked exception
    public static void connectToDatabase(String url) throws SQLException {
        if (url == null || url.isEmpty()) {
            throw new SQLException("Database URL cannot be null or empty");
        }
        // connect logic...
    }
    
    // Re-throw exception (chaining)
    public static void processData(String data) {
        try {
            int value = Integer.parseInt(data);
        } catch (NumberFormatException e) {
            // Wrap exception với thông tin bổ sung
            throw new RuntimeException("Failed to process data: " + data, e);
        }
    }
    
    public static void main(String[] args) {
        try {
            setAge(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        try {
            processData("abc");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Caused by: " + e.getCause());
        }
    }
}
```

### 4.2. throws keyword

**`throws`** khai báo rằng phương thức **có thể ném** exception, yêu cầu caller phải xử lý.

```java
import java.io.*;

public class ThrowsDemo {
    
    // throws một exception
    public static String readFirstLine(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        return reader.readLine();
    }
    
    // throws nhiều exceptions
    public static void riskyMethod() throws IOException, InterruptedException {
        // Method có thể throw IOException hoặc InterruptedException
        Thread.sleep(1000);
        FileReader reader = new FileReader("file.txt");
    }
    
    // Propagation chain
    public static void method1() throws IOException {
        method2(); // propagate IOException lên
    }
    
    public static void method2() throws IOException {
        method3(); // propagate IOException lên
    }
    
    public static void method3() throws IOException {
        throw new IOException("Something went wrong in method3");
    }
    
    public static void main(String[] args) {
        // Caller phải handle hoặc propagate tiếp
        try {
            String line = readFirstLine("data.txt");
            System.out.println(line);
        } catch (IOException e) {
            System.out.println("Cannot read file: " + e.getMessage());
        }
        
        // Hoặc main cũng throws
        // public static void main(String[] args) throws IOException { ... }
    }
}
```

**So sánh throw vs throws:**

| Đặc điểm | throw | throws |
|-----------|-------|--------|
| **Vị trí** | Trong method body | Trong method signature |
| **Mục đích** | Ném exception cụ thể | Khai báo exception có thể xảy ra |
| **Số lượng** | Ném 1 exception mỗi lần | Khai báo nhiều exceptions |
| **Theo sau** | Object (instance of Throwable) | Class names |

---

## 5. Custom Exceptions

### 5.1. Tạo custom exception

```java
// Custom Checked Exception
public class InsufficientBalanceException extends Exception {
    private double balance;
    private double amount;
    
    public InsufficientBalanceException(double balance, double amount) {
        super(String.format(
            "Insufficient balance. Current: %.2f, Requested: %.2f, Deficit: %.2f",
            balance, amount, amount - balance
        ));
        this.balance = balance;
        this.amount = amount;
    }
    
    public double getBalance() { return balance; }
    public double getAmount() { return amount; }
    public double getDeficit() { return amount - balance; }
}

// Custom Unchecked Exception
public class InvalidEmailException extends RuntimeException {
    private String email;
    
    public InvalidEmailException(String email) {
        super("Invalid email format: " + email);
        this.email = email;
    }
    
    public InvalidEmailException(String email, Throwable cause) {
        super("Invalid email format: " + email, cause);
        this.email = email;
    }
    
    public String getEmail() { return email; }
}

// Sử dụng Custom Exception
public class BankAccount {
    private String owner;
    private double balance;
    
    public BankAccount(String owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }
    
    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (amount > balance) {
            throw new InsufficientBalanceException(balance, amount);
        }
        balance -= amount;
        System.out.printf("Withdrawn %.2f. Remaining: %.2f%n", amount, balance);
    }
    
    public static void main(String[] args) {
        BankAccount account = new BankAccount("Alice", 1000);
        
        try {
            account.withdraw(500);   // OK
            account.withdraw(800);   // Exception!
        } catch (InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Deficit: " + e.getDeficit());
        }
    }
}
```

### 5.2. Khi nào nên tạo custom exception?

**NÊN tạo custom exception khi:**
- Cần thông tin lỗi cụ thể cho domain (VD: `OrderNotFoundException`)
- Các exception standard không truyền đủ ý nghĩa
- Cần phân biệt các loại lỗi business logic
- Cần thêm dữ liệu kèm theo exception (error code, context)

**KHÔNG NÊN tạo custom exception khi:**
- Có thể dùng exception standard phù hợp
- Exception không cung cấp thêm thông tin hữu ích
- Chỉ để đổi tên exception có sẵn

```java
// Exception hierarchy cho một ứng dụng
public class AppException extends RuntimeException { ... }
    public class ValidationException extends AppException { ... }
        public class InvalidInputException extends ValidationException { ... }
    public class BusinessException extends AppException { ... }
        public class OrderNotFoundException extends BusinessException { ... }
        public class PaymentFailedException extends BusinessException { ... }
    public class InfrastructureException extends AppException { ... }
        public class DatabaseConnectionException extends InfrastructureException { ... }
```

---

## 6. Exception Best Practices

### 6.1. Don't swallow exceptions

```java
// ❌ TUYỆT ĐỐI KHÔNG LÀM
try {
    riskyOperation();
} catch (Exception e) {
    // Không làm gì - exception bị "nuốt"
}

// ❌ Chỉ print - không đủ thông tin
try {
    riskyOperation();
} catch (Exception e) {
    e.printStackTrace(); // Chỉ in ra console, dễ bị miss
}

// ✅ Log hoặc rethrow
try {
    riskyOperation();
} catch (Exception e) {
    logger.error("Failed to perform risky operation", e);
    throw new ServiceException("Operation failed", e);
}
```

### 6.2. Log appropriately

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingDemo {
    private static final Logger logger = LoggerFactory.getLogger(LoggingDemo.class);
    
    public void process(String data) {
        try {
            // business logic
            parseAndSave(data);
        } catch (ValidationException e) {
            // WARN - lỗi từ input user, có thể recover
            logger.warn("Invalid input data: {}", data, e);
        } catch (DatabaseException e) {
            // ERROR - lỗi hệ thống, cần chú ý
            logger.error("Database error while processing data: {}", data, e);
            throw e; // rethrow nếu caller cần biết
        }
    }
}
```

### 6.3. Use specific exceptions

```java
// ❌ Quá generic
public void processOrder(Order order) throws Exception {
    // ...
}

// ❌ Catch quá rộng
try {
    processOrder(order);
} catch (Exception e) { // Bắt tất cả, kể cả NPE, AIOOBE...
    handleError(e);
}

// ✅ Specific exception
public void processOrder(Order order) throws OrderValidationException, PaymentException {
    if (!order.isValid()) {
        throw new OrderValidationException(order.getId(), "Invalid order");
    }
    processPayment(order.getPayment());
}

// ✅ Catch specific
try {
    processOrder(order);
} catch (OrderValidationException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
} catch (PaymentException e) {
    notifyAdmin(e);
    return ResponseEntity.status(503).body("Payment service unavailable");
}
```

### 6.4. Clean up resources

```java
// ✅ Try-with-resources (recommended)
public List<String> readLines(String path) throws IOException {
    List<String> lines = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
    }
    return lines;
}

// ✅ Nhiều resources - đóng theo thứ tự ngược
public void copyFile(String src, String dest) throws IOException {
    try (
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dest)
    ) {
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
    }
}
```

### 6.5. Document exceptions

```java
/**
 * Transfers money between two accounts.
 *
 * @param from   source account ID
 * @param to     destination account ID
 * @param amount amount to transfer (must be positive)
 * @throws AccountNotFoundException if either account does not exist
 * @throws InsufficientBalanceException if source account has insufficient funds
 * @throws IllegalArgumentException if amount is not positive
 * @throws TransferException if the transfer fails due to system error
 */
public void transfer(String from, String to, double amount)
        throws AccountNotFoundException, InsufficientBalanceException, TransferException {
    if (amount <= 0) {
        throw new IllegalArgumentException("Amount must be positive: " + amount);
    }
    // implementation...
}
```

---

## 7. Common Exceptions

### 7.1. NullPointerException

Xảy ra khi truy cập method/field trên đối tượng `null`.

```java
public class NPEDemo {
    public static void main(String[] args) {
        // Các trường hợp gây NPE
        String str = null;
        // str.length();           // NPE
        // str.toUpperCase();      // NPE
        
        List<String> list = null;
        // list.add("item");       // NPE
        
        Map<String, String> map = new HashMap<>();
        String value = map.get("key"); // value = null
        // value.length();         // NPE
        
        // Cách phòng tránh
        // 1. Null check
        if (str != null) {
            System.out.println(str.length());
        }
        
        // 2. Optional (Java 8+)
        Optional<String> optStr = Optional.ofNullable(str);
        optStr.ifPresent(s -> System.out.println(s.length()));
        String safe = optStr.orElse("default");
        
        // 3. Objects.requireNonNull
        // Objects.requireNonNull(str, "str must not be null");
        
        // 4. Null-safe methods
        System.out.println(Objects.toString(str, "N/A"));
        
        // Java 14+: Helpful NullPointerExceptions
        // Trước Java 14: "NullPointerException"
        // Java 14+: "Cannot invoke String.length() because str is null"
    }
}
```

### 7.2. ArrayIndexOutOfBoundsException

Xảy ra khi truy cập index ngoài phạm vi mảng.

```java
public class AIOOBEDemo {
    public static void main(String[] args) {
        int[] arr = {10, 20, 30, 40, 50};
        
        // Lỗi phổ biến
        // arr[5];     // index 5 không tồn tại (0-4)
        // arr[-1];    // index âm
        
        // Phòng tránh
        int index = 3;
        if (index >= 0 && index < arr.length) {
            System.out.println(arr[index]); // 40
        }
        
        // Lỗi trong vòng lặp
        // for (int i = 0; i <= arr.length; i++) { // <= thay vì <
        //     System.out.println(arr[i]); // AIOOBE khi i = arr.length
        // }
        
        // Đúng
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        
        // Tốt nhất: for-each
        for (int num : arr) {
            System.out.println(num);
        }
    }
}
```

### 7.3. ArithmeticException

Xảy ra với phép toán bất hợp lệ (phổ biến nhất: chia cho 0 với số nguyên).

```java
public class ArithmeticExceptionDemo {
    public static void main(String[] args) {
        // Chia số nguyên cho 0
        try {
            int result = 10 / 0; // ArithmeticException
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage()); // / by zero
        }
        
        // Lưu ý: Chia số thực cho 0 KHÔNG throw exception
        double result1 = 10.0 / 0;   // Infinity
        double result2 = -10.0 / 0;  // -Infinity
        double result3 = 0.0 / 0;    // NaN
        
        System.out.println(result1);  // Infinity
        System.out.println(result2);  // -Infinity
        System.out.println(result3);  // NaN
        
        // Phòng tránh
        int divisor = 0;
        if (divisor != 0) {
            int result = 100 / divisor;
        } else {
            System.out.println("Cannot divide by zero");
        }
    }
}
```

### 7.4. ClassCastException

Xảy ra khi ép kiểu không hợp lệ.

```java
public class ClassCastExceptionDemo {
    public static void main(String[] args) {
        // Ép kiểu không tương thích
        Object obj = "Hello";
        try {
            Integer num = (Integer) obj; // ClassCastException
        } catch (ClassCastException e) {
            System.out.println("Cannot cast String to Integer");
        }
        
        // Phòng tránh: instanceof check
        if (obj instanceof Integer) {
            Integer num = (Integer) obj;
        } else if (obj instanceof String) {
            String str = (String) obj;
            System.out.println("It's a String: " + str);
        }
        
        // Java 16+: Pattern matching instanceof
        if (obj instanceof String str) {
            System.out.println("Length: " + str.length());
        }
        
        // Collection generic type erasure
        List rawList = new ArrayList();
        rawList.add("Hello");
        rawList.add(123);
        
        // Nguy hiểm - không có compile-time check
        for (Object item : rawList) {
            // String s = (String) item; // CCE khi gặp Integer!
            if (item instanceof String s) {
                System.out.println("String: " + s);
            }
        }
    }
}
```

### 7.5. IOException

Xảy ra khi thao tác I/O thất bại.

```java
import java.io.*;
import java.nio.file.*;

public class IOExceptionDemo {
    public static void main(String[] args) {
        // FileNotFoundException (subclass of IOException)
        try {
            FileReader reader = new FileReader("nonexistent.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        
        // IOException khi đọc/ghi
        try {
            Path path = Paths.get("data.txt");
            Files.write(path, "Hello".getBytes());
            String content = new String(Files.readAllBytes(path));
            System.out.println("Content: " + content);
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        }
        
        // Best practice: try-with-resources
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            writer.write("Line 1\n");
            writer.write("Line 2\n");
        } catch (IOException e) {
            System.err.println("Write error: " + e.getMessage());
        }
    }
}
```

### 7.6. SQLException

Xảy ra khi thao tác database thất bại.

```java
import java.sql.*;

public class SQLExceptionDemo {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydb";
        
        try (Connection conn = DriverManager.getConnection(url, "user", "pass");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            
            stmt.setInt(1, 1);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }
            
        } catch (SQLException e) {
            // SQLException cung cấp thêm thông tin
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
            
            // Chained exceptions
            SQLException next = e.getNextException();
            while (next != null) {
                System.out.println("Next: " + next.getMessage());
                next = next.getNextException();
            }
        }
    }
    
    // Best practice: wrap SQLException in custom exception
    public User findUser(int id) throws DataAccessException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapToUser(rs);
            }
            throw new UserNotFoundException(id);
            
        } catch (SQLException e) {
            throw new DataAccessException("Failed to find user: " + id, e);
        }
    }
}
```

---

> **Tóm tắt:** Exception Handling là cơ chế quan trọng giúp xây dựng ứng dụng Java robust và reliable. Hiểu rõ phân cấp exception, sử dụng try-with-resources, tạo custom exception hợp lý, và tuân thủ best practices giúp code dễ debug và maintain.
