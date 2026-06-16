# Quiz - Exception Handling

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Exception hierarchy trong Java: Throwable có những subclass nào?

- [x] Error và Exception
- [ ] RuntimeException và IOException
- [ ] CheckedException và UncheckedException
- [ ] Throwable không có subclass

> **Giải thích:** Throwable → Error (JVM errors, không nên catch) và Exception → RuntimeException (unchecked) + checked exceptions (IOException, SQLException...).

## Câu 2

[TYPE: SELECT_RESULT]

```java
try {
    int result = 10 / 0;
    System.out.println("Result: " + result);
} catch (ArithmeticException e) {
    System.out.println("Error: " + e.getMessage());
} finally {
    System.out.println("Done");
}
```

- [x] Error: / by zero và Done
- [ ] Result: 0 và Done
- [ ] Chỉ Done
- [ ] Error: / by zero

> **Giải thích:** 10/0 → ArithmeticException. Catch block in "Error: / by zero". Finally luôn chạy → "Done". Print sau exception trong try bị skip.

## Câu 3

[TYPE: FILL_BLANK]

Exception nào KHÔNG cần khai báo throws hoặc try-catch? Đó là `___` Exception.

- [x] Unchecked (RuntimeException)
- [ ] Checked
- [ ] IO
- [ ] SQL

> **Giải thích:** Unchecked (extends RuntimeException): NullPointerException, ArrayIndexOutOfBoundsException, ClassCastException... Compiler không bắt buộc handle.

## Câu 4

[TYPE: TRUE_FALSE]

Mệnh đề: "finally block luôn được thực thi, kể cả khi có return trong try hoặc catch."

- [x] Đúng
- [ ] Sai

> **Giải thích:** finally luôn chạy trừ System.exit() hoặc JVM crash. Return trong try → finally chạy trước khi return. finally return overwrite try return.

## Câu 5

[TYPE: SELECT_RESULT]

```java
public static int test() {
    try {
        return 1;
    } catch (Exception e) {
        return 2;
    } finally {
        return 3;
    }
}
System.out.println(test());
```

- [ ] 1
- [ ] 2
- [x] 3
- [ ] Lỗi biên dịch

> **Giải thích:** finally return (3) overwrite try return (1). KHÔNG nên return trong finally vì behavior khó đoán và nuốt exceptions.

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Đâu là Checked Exception?

- [x] IOException, SQLException, ClassNotFoundException
- [ ] NullPointerException, ArrayIndexOutOfBoundsException
- [ ] StackOverflowError, OutOfMemoryError
- [ ] ArithmeticException, ClassCastException

> **Giải thích:** Checked: compiler bắt buộc handle (try-catch hoặc throws). IOException, SQLException, FileNotFoundException, ClassNotFoundException.

## Câu 7

[TYPE: SELECT_RESULT]

```java
try {
    String s = null;
    System.out.println(s.length());
} catch (NullPointerException e) {
    System.out.print("NPE ");
} catch (Exception e) {
    System.out.print("Exception ");
} finally {
    System.out.print("Finally");
}
```

- [x] NPE Finally
- [ ] Exception Finally
- [ ] NPE Exception Finally
- [ ] Finally

> **Giải thích:** null.length() → NullPointerException. Catch NPE first (specific). Finally luôn chạy. Catch blocks kiểm tra từ trên xuống.

## Câu 8

[TYPE: SELECT_RESULT]

```java
public class CustomException extends Exception {
    private int code;
    public CustomException(String message, int code) {
        super(message);
        this.code = code;
    }
    public int getCode() { return code; }
}

try {
    throw new CustomException("Not Found", 404);
} catch (CustomException e) {
    System.out.println(e.getMessage() + " " + e.getCode());
}
```

- [x] Not Found 404
- [ ] CustomException: Not Found
- [ ] 404
- [ ] Lỗi biên dịch

> **Giải thích:** Custom exception extends Exception (checked). getMessage() từ super. getCode() custom field. throw → catch → print message và code.

## Câu 9

[TYPE: FILL_BLANK]

Từ Java 7, try-with-resources tự động close resources implement interface `___`.

- [x] AutoCloseable
- [ ] Closeable
- [ ] Disposable
- [ ] Releasable

> **Giải thích:** AutoCloseable: close() method. Closeable extends AutoCloseable (for I/O). Try-with-resources: tự động gọi close() khi exit try block.

## Câu 10

[TYPE: SELECT_RESULT]

```java
try (var br = new BufferedReader(new FileReader("nonexistent.txt"))) {
    System.out.println(br.readLine());
} catch (FileNotFoundException e) {
    System.out.println("File not found");
} catch (IOException e) {
    System.out.println("IO error");
}
```

- [x] File not found
- [ ] IO error
- [ ] null
- [ ] Lỗi biên dịch

> **Giải thích:** File không tồn tại → FileNotFoundException (subclass của IOException). Catch specific exception first. BufferedReader auto-closed.

## Câu 11

[TYPE: TRUE_FALSE]

Mệnh đề: "Multi-catch (catch(A | B e)) có thể bắt nhiều exception types trong 1 catch block."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Java 7+: `catch (IOException | SQLException e)`. Biến e implicitly final. Các types không được có quan hệ cha-con.

## Câu 12

[TYPE: SELECT_RESULT]

```java
try {
    try {
        throw new RuntimeException("inner");
    } finally {
        System.out.print("inner-finally ");
    }
} catch (RuntimeException e) {
    System.out.print(e.getMessage());
}
```

- [x] inner-finally inner
- [ ] inner inner-finally
- [ ] inner-finally
- [ ] inner

> **Giải thích:** Inner try throw → inner finally chạy ("inner-finally ") → exception propagate → outer catch ("inner"). Finally chạy trước propagation.

## Câu 13

[TYPE: MULTIPLE_CHOICE]

Khi nào nên tạo Custom Exception?

- [x] Khi cần exception có ý nghĩa cụ thể cho domain, chứa thêm thông tin
- [ ] Cho mọi error
- [ ] Không bao giờ nên tạo
- [ ] Chỉ khi checked exception

> **Giải thích:** Custom Exception khi: cần error code, extra data, specific exception type cho catch, domain-specific errors. Extend Exception (checked) hoặc RuntimeException (unchecked).

## Câu 14

[TYPE: SELECT_RESULT]

```java
public void method() throws IOException {
    throw new FileNotFoundException("file.txt");
}

try {
    method();
} catch (FileNotFoundException e) {
    System.out.println("FNF: " + e.getMessage());
} catch (IOException e) {
    System.out.println("IO: " + e.getMessage());
}
```

- [x] FNF: file.txt
- [ ] IO: file.txt
- [ ] FNF: file.txt và IO: file.txt
- [ ] Lỗi biên dịch

> **Giải thích:** FileNotFoundException extends IOException. Catch specific first → "FNF: file.txt". Catch order: specific → general.

## Câu 15

[TYPE: SELECT_RESULT]

```java
Exception original = new Exception("original");
try {
    throw original;
} catch (Exception e) {
    RuntimeException wrapper = new RuntimeException("wrapped", e);
    throw wrapper;
}
// Caller catches RuntimeException re
// re.getCause()
```

`re.getCause()` trả về:

- [x] Exception "original"
- [ ] null
- [ ] RuntimeException "wrapped"
- [ ] Lỗi

> **Giải thích:** Exception chaining: new RuntimeException("wrapped", cause). getCause() trả về original exception. Giữ root cause information.

## Câu 16

[TYPE: FILL_BLANK]

Lệnh `throw` dùng để `___` exception, còn `throws` khai báo method có thể `___` exception.

- [x] ném (throw) / ném ra (throw)
- [ ] bắt / ném
- [ ] tạo / xóa
- [ ] log / ignore

> **Giải thích:** throw: ném exception instance. throws: khai báo trên method signature. `throw new Exception()` vs `void method() throws Exception`.

## Câu 17

[TYPE: SELECT_RESULT]

```java
public static String getValue() {
    String result = "default";
    try {
        result = "try";
        return result;
    } finally {
        result = "finally";
    }
}
System.out.println(getValue());
```

- [x] try
- [ ] finally
- [ ] default
- [ ] null

> **Giải thích:** return result trong try: giá trị "try" được lưu. finally thay đổi biến result nhưng return value đã được capture. Trả về "try".

## Câu 18

[TYPE: MULTIPLE_CHOICE]

Error vs Exception: khi nào nên catch Error?

- [x] Hầu như không bao giờ. Error là JVM-level problems (OutOfMemoryError, StackOverflowError)
- [ ] Luôn catch Error
- [ ] Catch Error thường xuyên hơn Exception
- [ ] Error và Exception giống nhau

> **Giải thích:** Error: unrecoverable JVM problems. OutOfMemoryError, StackOverflowError, NoClassDefFoundError. Catch chỉ trong rare cases (cleanup, logging before exit).

## Câu 19

[TYPE: SELECT_RESULT]

```java
List<String> list = List.of("a", "b", "c");
try {
    list.add("d");
} catch (UnsupportedOperationException e) {
    System.out.print("Immutable ");
}
try {
    String s = list.get(5);
} catch (IndexOutOfBoundsException e) {
    System.out.print("OutOfBounds");
}
```

- [x] Immutable OutOfBounds
- [ ] Chỉ Immutable
- [ ] Chỉ OutOfBounds
- [ ] Không in gì

> **Giải thích:** List.of() immutable → add → UnsupportedOperationException. get(5) index > size → IndexOutOfBoundsException. Cả hai caught.

## Câu 20

[TYPE: TRUE_FALSE]

Mệnh đề: "Suppressed exceptions được lưu trữ khi try-with-resources có exception cả trong try block và close()."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Try throws exception A. close() throws exception B. A là primary, B added as suppressed: `e.getSuppressed()`. Không mất exception info.

## Câu 21

[TYPE: SELECT_RESULT]

```java
class MyResource implements AutoCloseable {
    String name;
    MyResource(String n) { this.name = n; System.out.print("Open:" + n + " "); }
    public void close() { System.out.print("Close:" + name + " "); }
}
try (MyResource a = new MyResource("A"); MyResource b = new MyResource("B")) {
    System.out.print("Use ");
}
```

- [x] Open:A Open:B Use Close:B Close:A
- [ ] Open:A Open:B Use Close:A Close:B
- [ ] Open:A Open:B Close:A Close:B Use
- [ ] Open:A Open:B Use

> **Giải thích:** Resources close in REVERSE order. Open: A→B. Use. Close: B→A (LIFO, giống stack).

## Câu 22

[TYPE: SELECT_RESULT]

```java
try {
    int[] arr = new int[3];
    arr[5] = 10;
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Index: " + e.getMessage());
}
```

- [x] Index: Index 5 out of bounds for length 3
- [ ] Index: 5
- [ ] NullPointerException
- [ ] Lỗi biên dịch

> **Giải thích:** arr[5]: array length=3, index 5 → ArrayIndexOutOfBoundsException. Java 14+ message: "Index 5 out of bounds for length 3".

## Câu 23

[TYPE: MULTIPLE_CHOICE]

Best practice xử lý exception?

- [x] Catch specific exceptions, don't swallow exceptions, use meaningful messages, clean up resources
- [ ] Catch Exception cho mọi trường hợp
- [ ] Bỏ qua exceptions
- [ ] Luôn dùng RuntimeException

> **Giải thích:** Best practices: specific catch, meaningful message, log exception, cleanup in finally/try-with-resources, don't use exceptions for flow control.

## Câu 24

[TYPE: SELECT_RESULT]

```java
public void process(String input) {
    Objects.requireNonNull(input, "Input must not be null");
    System.out.println(input.toUpperCase());
}
process(null);
```

- [x] NullPointerException: Input must not be null
- [ ] NULL
- [ ] Lỗi biên dịch
- [ ] IllegalArgumentException

> **Giải thích:** Objects.requireNonNull: throw NullPointerException với message nếu argument null. Fail-fast validation. Clean error message.

## Câu 25

[TYPE: FILL_BLANK]

Annotation `@___` trong JUnit 5 kiểm tra method ném exception mong đợi.

- [ ] ThrowsException
- [x] Test (dùng assertThrows())
- [ ] ExpectedException
- [ ] Catch

> **Giải thích:** JUnit 5: `assertThrows(IllegalArgumentException.class, () -> method())`. Không dùng @Test(expected=...) như JUnit 4.

## Câu 26

[TYPE: SELECT_RESULT]

```java
try {
    throw new Exception("first");
} catch (Exception e) {
    throw new RuntimeException("second");
} finally {
    System.out.println("finally");
}
```

- [x] In "finally", rồi throw RuntimeException "second"
- [ ] In "finally" rồi throw Exception "first"
- [ ] Chỉ in "finally"
- [ ] Throw Exception "first"

> **Giải thích:** Try throws "first" → catch throws "second" → finally runs (in "finally") → RuntimeException "second" propagate. Catch re-throws.

## Câu 27

[TYPE: SELECT_RESULT]

```java
class Parent {
    void method() throws IOException {}
}
class Child extends Parent {
    @Override
    void method() throws FileNotFoundException {} // FileNotFoundException extends IOException
}
```

Đoạn code có hợp lệ không?

- [x] Hợp lệ. Override method có thể throws narrower exception hoặc không throws
- [ ] Không hợp lệ. Override phải throws cùng exception
- [ ] Lỗi biên dịch
- [ ] Lỗi runtime

> **Giải thích:** Override rule: throws narrower (subclass) hoặc không throws. Không được throws broader/unrelated checked exception. FileNotFoundException ⊂ IOException → OK.

## Câu 28

[TYPE: MULTIPLE_CHOICE]

StackOverflowError xảy ra khi nào?

- [x] Đệ quy quá sâu, call stack vượt giới hạn
- [ ] Heap memory đầy
- [ ] Array quá lớn
- [ ] Deadlock

> **Giải thích:** StackOverflowError: mỗi method call = 1 stack frame. Recursion không có base case → stack frames vượt limit. Error, không phải Exception.

## Câu 29

[TYPE: SELECT_RESULT]

```java
Optional<String> opt = Optional.ofNullable(null);
try {
    String value = opt.orElseThrow(() -> new IllegalStateException("Empty"));
} catch (IllegalStateException e) {
    System.out.println(e.getMessage());
}
```

- [x] Empty
- [ ] null
- [ ] NullPointerException
- [ ] Lỗi biên dịch

> **Giải thích:** Optional.ofNullable(null) → empty Optional. orElseThrow → throw IllegalStateException("Empty"). Catch → print "Empty".

## Câu 30

[TYPE: TRUE_FALSE]

Mệnh đề: "ClassCastException là unchecked exception."

- [x] Đúng
- [ ] Sai

> **Giải thích:** ClassCastException extends RuntimeException → unchecked. Xảy ra khi cast object sang type không tương thích. Ví dụ: `(String) new Object()`.

## Câu 31

[TYPE: SELECT_RESULT]

```java
String str = "abc";
try {
    int num = Integer.parseInt(str);
} catch (NumberFormatException e) {
    System.out.print("NFE ");
}
try {
    int num = Integer.parseInt("123");
    System.out.print(num);
} catch (NumberFormatException e) {
    System.out.print("NFE2");
}
```

- [x] NFE 123
- [ ] NFE NFE2
- [ ] 123
- [ ] NFE

> **Giải thích:** "abc" → NumberFormatException → "NFE ". "123" → parse thành công → 123. Hai try-catch blocks độc lập.

## Câu 32

[TYPE: SELECT_RESULT]

```java
class BusinessException extends RuntimeException {
    private final String errorCode;
    public BusinessException(String code, String msg) {
        super(msg);
        this.errorCode = code;
    }
    public String getErrorCode() { return errorCode; }
}

class InsufficientFundsException extends BusinessException {
    private final double balance;
    public InsufficientFundsException(double balance) {
        super("INSUFFICIENT_FUNDS", "Balance: " + balance);
        this.balance = balance;
    }
    public double getBalance() { return balance; }
}

try {
    throw new InsufficientFundsException(50.0);
} catch (BusinessException e) {
    System.out.println(e.getErrorCode() + ": " + e.getMessage());
}
```

- [x] INSUFFICIENT_FUNDS: Balance: 50.0
- [ ] Lỗi biên dịch
- [ ] BusinessException
- [ ] NullPointerException

> **Giải thích:** Exception hierarchy: InsufficientFundsException extends BusinessException. Caught by BusinessException catch. Polymorphism: getErrorCode() works.

## Câu 33

[TYPE: MULTIPLE_CHOICE]

try-with-resources vs try-finally cho resource management?

- [x] try-with-resources cleaner, handles close() exceptions, supports multiple resources
- [ ] try-finally tốt hơn
- [ ] Giống nhau
- [ ] try-with-resources chậm hơn

> **Giải thích:** try-with-resources (Java 7): automatic close, suppressed exceptions, less boilerplate. try-finally: manual close, close exception có thể mask original.

## Câu 34

[TYPE: SELECT_RESULT]

```java
interface Validator {
    void validate(String input) throws IllegalArgumentException;
}

Validator notEmpty = input -> {
    if (input == null || input.isEmpty())
        throw new IllegalArgumentException("Input is empty");
};

try {
    notEmpty.validate("");
} catch (IllegalArgumentException e) {
    System.out.println(e.getMessage());
}
```

- [x] Input is empty
- [ ] Lỗi biên dịch
- [ ] NullPointerException
- [ ] Không in gì

> **Giải thích:** Lambda implement Validator. "" isEmpty() → true → throw IllegalArgumentException. Catch → print message.

## Câu 35

[TYPE: FILL_BLANK]

Method `e.___()` in ra stack trace của exception ra System.err.

- [x] printStackTrace
- [ ] printError
- [ ] showTrace
- [ ] dumpStack

> **Giải thích:** e.printStackTrace(): in exception type, message, stack trace. Default ra System.err. Dùng logger.error("msg", e) thay vì printStackTrace() trong production.

## Câu 36

[TYPE: SELECT_RESULT]

```java
try {
    System.out.print("A ");
    if (true) throw new Exception("test");
    System.out.print("B ");
} catch (Exception e) {
    System.out.print("C ");
    throw e;
} finally {
    System.out.print("D ");
}
```

- [x] A C D (rồi exception propagate)
- [ ] A B C D
- [ ] A C
- [ ] A D

> **Giải thích:** A printed. Exception thrown → skip B. Catch: C printed, re-throw. Finally: D printed (luôn chạy). Exception propagate tiếp.

## Câu 37

[TYPE: TRUE_FALSE]

Mệnh đề: "Checked exceptions buộc caller phải handle, giúp API contracts rõ ràng."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Checked exceptions: compiler enforce handling. API contract: method khai báo throws = "có thể fail cách này". Caller phải xử lý. Controversial: verbose code.

## Câu 38

[TYPE: SELECT_RESULT]

```java
public void readFile() {
    try {
        throw new IOException("read error");
    } catch (IOException e) {
        throw new UncheckedIOException(e);
    }
}

try {
    readFile();
} catch (UncheckedIOException e) {
    System.out.println(e.getCause().getMessage());
}
```

- [x] read error
- [ ] UncheckedIOException
- [ ] Lỗi biên dịch (readFile không throws IOException)
- [ ] null

> **Giải thích:** Wrap checked IOException trong unchecked UncheckedIOException. getCause() → original IOException. getMessage() → "read error". Pattern: checked → unchecked.

## Câu 39

[TYPE: SELECT_RESULT]

```java
record Result<T>(T value, String error) {
    static <T> Result<T> success(T value) { return new Result<>(value, null); }
    static <T> Result<T> failure(String error) { return new Result<>(null, error); }
    boolean isSuccess() { return error == null; }
}

Result<Integer> divide(int a, int b) {
    if (b == 0) return Result.failure("Division by zero");
    return Result.success(a / b);
}

Result<Integer> r = divide(10, 0);
System.out.println(r.isSuccess() + ": " + r.error());
```

- [x] false: Division by zero
- [ ] true: null
- [ ] ArithmeticException
- [ ] Lỗi biên dịch

> **Giải thích:** Result pattern: alternative to exceptions. b=0 → failure result. isSuccess()=false (error != null). error="Division by zero".

## Câu 40

[TYPE: MULTIPLE_CHOICE]

Đâu là anti-pattern trong exception handling?

- [x] Catch Exception rồi không làm gì (swallow), dùng exceptions cho flow control
- [ ] Catch specific exceptions
- [ ] Log và re-throw
- [ ] Dùng custom exceptions

> **Giải thích:** Anti-patterns: empty catch block, catch(Exception e){}, throw trong normal flow, log and throw (double logging), generic catch everywhere.

## Câu 41

[TYPE: SELECT_RESULT]

```java
public class ExceptionChaining {
    public void low() throws Exception {
        throw new Exception("low-level error");
    }
    public void mid() {
        try { low(); }
        catch (Exception e) {
            throw new RuntimeException("mid-level error", e);
        }
    }
    public void high() {
        try { mid(); }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause().getMessage());
        }
    }
}
new ExceptionChaining().high();
```

- [x] mid-level error và low-level error
- [ ] low-level error và null
- [ ] mid-level error và null
- [ ] Lỗi biên dịch

> **Giải thích:** Exception chaining: low → mid wraps trong RuntimeException → high catches. getMessage="mid-level error". getCause().getMessage="low-level error".

## Câu 42

[TYPE: FILL_BLANK]

Java assertion `assert condition : message` throw `___Error` khi condition false.

- [x] Assertion
- [ ] Runtime
- [ ] Illegal
- [ ] Assert

> **Giải thích:** assert false → AssertionError. Cần -ea flag để enable. Dùng cho developer checks, không cho production input validation.

## Câu 43

[TYPE: SELECT_RESULT]

```java
try {
    throw new IllegalStateException("state");
} catch (IllegalArgumentException | IllegalStateException e) {
    System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
}
```

- [x] IllegalStateException: state
- [ ] IllegalArgumentException: state
- [ ] Lỗi biên dịch (multi-catch)
- [ ] Lỗi runtime

> **Giải thích:** Multi-catch: IllegalArgumentException | IllegalStateException. Thrown IllegalStateException → caught. getClass().getSimpleName() → "IllegalStateException".

## Câu 44

[TYPE: SELECT_RESULT]

```java
Stream.of("1", "2", "abc", "4").forEach(s -> {
    try {
        System.out.print(Integer.parseInt(s) + " ");
    } catch (NumberFormatException e) {
        System.out.print("X ");
    }
});
```

- [x] 1 2 X 4
- [ ] 1 2
- [ ] NumberFormatException
- [ ] 1 2 4

> **Giải thích:** "1"→1, "2"→2, "abc"→NFE→X, "4"→4. Try-catch inside lambda handles exception per element.

## Câu 45

[TYPE: MULTIPLE_CHOICE]

IllegalArgumentException vs IllegalStateException?

- [x] IllegalArgumentException: argument không hợp lệ; IllegalStateException: object ở trạng thái không hợp lệ
- [ ] Giống nhau
- [ ] IllegalStateException cho bad arguments
- [ ] IllegalArgumentException cho bad state

> **Giải thích:** IAE: method nhận arg sai (null, negative). ISE: object chưa sẵn sàng (connection closed, not initialized). Cả hai là unchecked.

## Câu 46

[TYPE: SELECT_RESULT]

```java
class ResourceManager implements AutoCloseable {
    boolean closed = false;
    void use() {
        if (closed) throw new IllegalStateException("Already closed");
        System.out.print("Using ");
    }
    public void close() {
        closed = true;
        System.out.print("Closed ");
    }
}
try (ResourceManager rm = new ResourceManager()) {
    rm.use();
    rm.use();
}
```

- [x] Using Using Closed
- [ ] Using Closed
- [ ] Using Using
- [ ] IllegalStateException

> **Giải thích:** use() 2 lần → "Using Using". Exit try → auto close → "Closed". closed=true nhưng không gọi use() nữa.

## Câu 47

[TYPE: TRUE_FALSE]

Mệnh đề: "NullPointerException từ Java 14 có helpful message chỉ rõ biến nào null."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Java 14 JEP 358: "Cannot invoke String.length() because the return value of method() is null". Helpful NullPointerExceptions. Enable: `-XX:+ShowCodeDetailsInExceptionMessages`.

## Câu 48

[TYPE: SELECT_RESULT]

```java
public interface Service {
    String process() throws ServiceException;
}
public class ServiceImpl implements Service {
    public String process() { // NO throws clause
        return "OK";
    }
}
```

Code có hợp lệ?

- [x] Hợp lệ. Implementation có thể không throws (narrower)
- [ ] Lỗi biên dịch
- [ ] Lỗi runtime
- [ ] Implementation phải throws ServiceException

> **Giải thích:** Override/implement: có thể throws narrower hoặc no throws. Implementation không throws → hợp lệ. Interface contract cho phép nhưng không bắt buộc.

## Câu 49

[TYPE: SELECT_RESULT]

```java
RuntimeException e1 = new RuntimeException("main");
RuntimeException e2 = new RuntimeException("suppressed1");
RuntimeException e3 = new RuntimeException("suppressed2");
e1.addSuppressed(e2);
e1.addSuppressed(e3);
System.out.println(e1.getSuppressed().length);
```

- [x] 2
- [ ] 0
- [ ] 1
- [ ] 3

> **Giải thích:** addSuppressed: thêm suppressed exceptions. getSuppressed() → array[2]: e2, e3. Dùng trong try-with-resources khi close() cũng throw.

## Câu 50

[TYPE: MULTIPLE_CHOICE]

Defensive programming với Preconditions?

- [x] Validate inputs đầu method: null checks, range checks, state checks
- [ ] Catch mọi exception
- [ ] Không validate gì
- [ ] Chỉ validate output

> **Giải thích:** Preconditions: Objects.requireNonNull(), if (x < 0) throw new IAE(), Guava Preconditions.checkArgument(). Fail-fast, clear error messages.

## Câu 51

[TYPE: SELECT_RESULT]

```java
public void riskyMethod() {
    try {
        throw new Exception("oops");
    } catch (Exception e) {
        System.out.print("caught ");
        return;
    } finally {
        System.out.print("finally ");
    }
}
riskyMethod();
```

- [x] caught finally
- [ ] caught
- [ ] finally
- [ ] caught finally oops

> **Giải thích:** catch: "caught ". return statement encountered. finally runs before return: "finally ". Then method returns.

## Câu 52

[TYPE: FILL_BLANK]

Exception type cho khi index vượt giới hạn mảng là `___`.

- [x] ArrayIndexOutOfBoundsException
- [ ] IndexOutOfBoundsException
- [ ] OutOfBoundsException
- [ ] ArrayException

> **Giải thích:** ArrayIndexOutOfBoundsException extends IndexOutOfBoundsException. Cụ thể cho arrays. List dùng IndexOutOfBoundsException. String dùng StringIndexOutOfBoundsException.

## Câu 53

[TYPE: SELECT_RESULT]

```java
Map<String, String> map = new HashMap<>();
map.put("key", "value");
try {
    String result = map.get("missing");
    System.out.println(result.toUpperCase());
} catch (NullPointerException e) {
    System.out.println("Key not found");
}
```

- [x] Key not found
- [ ] null
- [ ] VALUE
- [ ] Lỗi biên dịch

> **Giải thích:** map.get("missing") → null. null.toUpperCase() → NullPointerException → catch → "Key not found". Better: check null or use getOrDefault().

## Câu 54

[TYPE: SELECT_RESULT]

```java
@FunctionalInterface
interface CheckedFunction<T, R> {
    R apply(T t) throws Exception;
}

static <T, R> Function<T, R> wrap(CheckedFunction<T, R> fn) {
    return t -> {
        try { return fn.apply(t); }
        catch (Exception e) { throw new RuntimeException(e); }
    };
}

List<String> result = List.of("1", "2", "abc").stream()
    .map(wrap(s -> Integer.parseInt(s)))
    .map(Object::toString)
    .collect(Collectors.toList());
```

Khi gặp "abc":

- [x] RuntimeException wrapping NumberFormatException
- [ ] [1, 2, abc]
- [ ] NumberFormatException (checked)
- [ ] Lỗi biên dịch

> **Giải thích:** wrap: convert checked → unchecked. "abc" → parseInt fails → wrap catches → throw RuntimeException. Useful pattern for streams with checked exceptions.

## Câu 55

[TYPE: MULTIPLE_CHOICE]

Khi nào dùng Checked vs Unchecked Exceptions?

- [x] Checked: recoverable errors caller cần handle; Unchecked: programming errors, unrecoverable
- [ ] Luôn dùng checked
- [ ] Luôn dùng unchecked
- [ ] Không có rule

> **Giải thích:** Checked: file not found (recoverable, retry). Unchecked: null pointer (bug, fix code). Modern trend: prefer unchecked (Spring, Kotlin).

## Câu 56

[TYPE: SELECT_RESULT]

```java
public int divide(int a, int b) {
    if (b == 0) throw new ArithmeticException("Cannot divide by zero");
    return a / b;
}
try {
    System.out.println(divide(10, 2));
    System.out.println(divide(10, 0));
    System.out.println(divide(20, 4));
} catch (ArithmeticException e) {
    System.out.println(e.getMessage());
}
```

- [x] 5, Cannot divide by zero
- [ ] 5, Cannot divide by zero, 5
- [ ] 5, 0, 5
- [ ] Cannot divide by zero

> **Giải thích:** divide(10,2)=5 → print. divide(10,0) → throw → skip divide(20,4). Catch → print message. Third call never executes.

## Câu 57

[TYPE: TRUE_FALSE]

Mệnh đề: "InterruptedException là checked exception thường gặp khi thread bị interrupt."

- [x] Đúng
- [ ] Sai

> **Giải thích:** InterruptedException: checked. Thread.sleep(), wait(), join() throws InterruptedException. Handle: restore interrupt status hoặc propagate.

## Câu 58

[TYPE: SELECT_RESULT]

```java
class A extends Exception {}
class B extends A {}

try {
    throw new B();
} catch (A e) {
    System.out.print("A ");
} catch (B e) {
    System.out.print("B ");
}
```

- [ ] A B
- [x] Lỗi biên dịch
- [ ] A
- [ ] B

> **Giải thích:** Lỗi biên dịch: catch(A) trước catch(B) nhưng B extends A → B unreachable. Compiler error: "exception B has already been caught". Phải đặt specific (B) trước general (A).

## Câu 59

[TYPE: SELECT_RESULT]

```java
String[] arr = {"hello", null, "world"};
for (String s : arr) {
    try {
        System.out.print(s.length() + " ");
    } catch (NullPointerException e) {
        System.out.print("null ");
    }
}
```

- [x] 5 null 5
- [ ] 5 0 5
- [ ] NullPointerException
- [ ] 5 5

> **Giải thích:** "hello".length()=5. null.length() → NPE → catch → "null". "world".length()=5. Loop continues after each catch.

## Câu 60

[TYPE: FILL_BLANK]

`UnsupportedOperationException` thường xảy ra khi gọi method trên `___` collection.

- [x] unmodifiable/immutable
- [ ] empty
- [ ] synchronized
- [ ] sorted

> **Giải thích:** Collections.unmodifiableList(), List.of() → immutable. add/remove/set → UnsupportedOperationException. Design: read-only view.

## Câu 61

[TYPE: SELECT_RESULT]

```java
public void validate(int age) {
    if (age < 0 || age > 150) {
        throw new IllegalArgumentException("Age must be between 0 and 150, got: " + age);
    }
}
try {
    validate(-5);
} catch (IllegalArgumentException e) {
    System.out.println(e.getMessage());
}
```

- [x] Age must be between 0 and 150, got: -5
- [ ] -5
- [ ] Lỗi biên dịch
- [ ] Không in gì

> **Giải thích:** age=-5 < 0 → throw IAE với descriptive message. Best practice: include actual value in error message.

## Câu 62

[TYPE: MULTIPLE_CHOICE]

Logger vs System.err cho exception handling?

- [x] Logger: structured, configurable, supports levels/appenders; System.err: primitive, no formatting
- [ ] System.err tốt hơn
- [ ] Giống nhau
- [ ] Logger chậm hơn

> **Giải thích:** Logger (SLF4J, Log4j): `logger.error("Failed", exception)`. Levels: ERROR, WARN, INFO. Appenders: file, console, ELK. Production: luôn dùng logger.

## Câu 63

[TYPE: SELECT_RESULT]

```java
Optional<String> opt = Optional.empty();
String value = opt.orElseGet(() -> "default");
System.out.print(value + " ");
String value2 = opt.orElse("fallback");
System.out.print(value2);
```

- [x] default fallback
- [ ] null null
- [ ] NoSuchElementException
- [ ] default default

> **Giải thích:** Empty Optional. orElseGet(supplier): lazy evaluation → "default". orElse(value): eager → "fallback". Cả hai tránh exception.

## Câu 64

[TYPE: SELECT_RESULT]

```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    throw new RuntimeException("async error");
});
try {
    future.get();
} catch (ExecutionException e) {
    System.out.println(e.getCause().getMessage());
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
}
```

- [x] async error
- [ ] RuntimeException
- [ ] ExecutionException
- [ ] Lỗi biên dịch

> **Giải thích:** CompletableFuture async task throws → get() wraps in ExecutionException. getCause() → original RuntimeException. getMessage() → "async error".

## Câu 65

[TYPE: TRUE_FALSE]

Mệnh đề: "NoSuchElementException là unchecked exception thrown bởi Iterator.next() khi không còn phần tử."

- [x] Đúng
- [ ] Sai

> **Giải thích:** NoSuchElementException extends RuntimeException. Iterator.next() khi hasNext()=false. Cũng thrown bởi Optional.get() khi empty, Scanner.next() khi no input.

## Câu 66

[TYPE: SELECT_RESULT]

```java
class MyException extends Exception {
    public MyException(String msg, Throwable cause) { super(msg, cause); }
}

try {
    try {
        int[] arr = {};
        int x = arr[0];
    } catch (ArrayIndexOutOfBoundsException e) {
        throw new MyException("Array access failed", e);
    }
} catch (MyException e) {
    System.out.println(e.getMessage());
    System.out.println(e.getCause().getClass().getSimpleName());
}
```

- [x] Array access failed và ArrayIndexOutOfBoundsException
- [ ] Array access failed và MyException
- [ ] Lỗi biên dịch
- [ ] ArrayIndexOutOfBoundsException

> **Giải thích:** arr[0] on empty array → AIOOBE. Wrap in MyException. Outer catch: getMessage()="Array access failed", getCause() type = ArrayIndexOutOfBoundsException.

## Câu 67

[TYPE: SELECT_RESULT]

```java
enum ErrorCode { NOT_FOUND, FORBIDDEN, INTERNAL }

class ApiException extends RuntimeException {
    private final ErrorCode code;
    public ApiException(ErrorCode code, String msg) {
        super(msg);
        this.code = code;
    }
    public ErrorCode getCode() { return code; }
}

try {
    throw new ApiException(ErrorCode.NOT_FOUND, "User 123");
} catch (ApiException e) {
    System.out.println(e.getCode() + ": " + e.getMessage());
}
```

- [x] NOT_FOUND: User 123
- [ ] ApiException: User 123
- [ ] Lỗi biên dịch
- [ ] INTERNAL: User 123

> **Giải thích:** Custom exception với enum error code. Structured error handling. getCode()=NOT_FOUND, getMessage()="User 123".

## Câu 68

[TYPE: FILL_BLANK]

Khi override method, exception trong `___` clause chỉ có thể narrower hoặc bỏ trống.

- [x] throws
- [ ] catch
- [ ] try
- [ ] finally

> **Giải thích:** Override: throws clause ≤ parent's throws. Không thể add new checked exceptions. Có thể: narrower, subset, or no throws.

## Câu 69

[TYPE: SELECT_RESULT]

```java
try {
    System.out.print("1 ");
    try {
        System.out.print("2 ");
        throw new Exception();
    } catch (RuntimeException e) {
        System.out.print("3 ");
    } finally {
        System.out.print("4 ");
    }
    System.out.print("5 ");
} catch (Exception e) {
    System.out.print("6 ");
} finally {
    System.out.print("7");
}
```

- [x] 1 2 4 6 7
- [ ] 1 2 3 4 5 6 7
- [ ] 1 2 4 7
- [ ] 1 2 3 4 6 7

> **Giải thích:** 1, 2 printed. throw Exception. Inner catch(RuntimeException) doesn't match. Inner finally: 4. Exception propagates to outer. Outer catch: 6. Outer finally: 7.

## Câu 70

[TYPE: MULTIPLE_CHOICE]

Rethrowing exceptions - best practice?

- [x] Wrap original exception as cause, add context information
- [ ] Throw new exception without cause
- [ ] Catch and ignore
- [ ] Always throw RuntimeException

> **Giải thích:** Rethrow: `throw new ServiceException("Processing failed for order " + id, originalException)`. Preserve stack trace, add context.

## Câu 71

[TYPE: SELECT_RESULT]

```java
public static void process(List<String> items) {
    for (int i = 0; i < items.size(); i++) {
        try {
            System.out.print(items.get(i).toUpperCase() + " ");
        } catch (NullPointerException e) {
            System.out.print("[null] ");
        }
    }
}
process(Arrays.asList("hi", null, "bye"));
```

- [x] HI [null] BYE
- [ ] HI
- [ ] NullPointerException
- [ ] HI BYE

> **Giải thích:** "hi"→"HI". null.toUpperCase()→NPE→"[null]". "bye"→"BYE". Loop continues, try-catch per iteration.

## Câu 72

[TYPE: TRUE_FALSE]

Mệnh đề: "Exception.getStackTrace() trả về mảng StackTraceElement chứa thông tin dòng, file, method."

- [x] Đúng
- [ ] Sai

> **Giải thích:** StackTraceElement: getClassName(), getMethodName(), getFileName(), getLineNumber(). getStackTrace() → array of StackTraceElements. Debugging info.

## Câu 73

[TYPE: SELECT_RESULT]

```java
Function<String, Integer> safeParse = s -> {
    try {
        return Integer.parseInt(s);
    } catch (NumberFormatException e) {
        return -1;
    }
};
List<Integer> result = Stream.of("1", "abc", "3")
    .map(safeParse)
    .collect(Collectors.toList());
System.out.println(result);
```

- [x] [1, -1, 3]
- [ ] [1, 3]
- [ ] NumberFormatException
- [ ] [1, null, 3]

> **Giải thích:** safeParse: catch NFE → return -1. "1"→1, "abc"→-1, "3"→3. Safe wrapper for stream operations.

## Câu 74

[TYPE: SELECT_RESULT]

```java
class Transaction implements AutoCloseable {
    boolean committed = false;
    void commit() { committed = true; System.out.print("commit "); }
    public void close() {
        if (!committed) System.out.print("rollback ");
        else System.out.print("close ");
    }
}
try (Transaction tx = new Transaction()) {
    // ... some operations
    throw new RuntimeException("error");
    // tx.commit(); // never reached
}
catch (RuntimeException e) { System.out.print("caught"); }
```

- [x] rollback caught
- [ ] commit close caught
- [ ] close caught
- [ ] caught rollback

> **Giải thích:** Exception before commit → close() called (auto-close) → committed=false → "rollback ". Then catch → "caught". Transaction pattern.

## Câu 75

[TYPE: MULTIPLE_CHOICE]

Đâu là cách handle exceptions trong Spring Boot?

- [x] @ExceptionHandler, @ControllerAdvice, ResponseStatusException, @ResponseStatus
- [ ] Chỉ try-catch
- [ ] Chỉ @ExceptionHandler
- [ ] Không handle, Spring tự xử lý

> **Giải thích:** Spring: @ExceptionHandler (per controller), @ControllerAdvice/@RestControllerAdvice (global), @ResponseStatus (on exception class), ResponseStatusException (inline).

## Câu 76

[TYPE: SELECT_RESULT]

```java
var lock = new ReentrantLock();
try {
    lock.lock();
    // critical section
    throw new RuntimeException("error in critical section");
} finally {
    lock.unlock();
    System.out.println("Lock released");
}
```

- [x] Lock released, then RuntimeException propagates
- [ ] Lock never released
- [ ] Deadlock
- [ ] Lỗi biên dịch

> **Giải thích:** finally ensures lock.unlock() even on exception. "Lock released" printed. Exception propagates. Best practice: always unlock in finally.

## Câu 77

[TYPE: FILL_BLANK]

`ConcurrentModificationException` xảy ra khi `___` collection trong khi đang iterate.

- [x] modify (thêm/xóa)
- [ ] read
- [ ] sort
- [ ] copy

> **Giải thích:** Fail-fast iterator: detect structural modification during iteration → ConcurrentModificationException. Fix: Iterator.remove(), removeIf(), CopyOnWriteArrayList.

## Câu 78

[TYPE: SELECT_RESULT]

```java
record ValidationError(String field, String message) {}

class ValidationException extends RuntimeException {
    private final List<ValidationError> errors;
    ValidationException(List<ValidationError> errors) {
        super("Validation failed");
        this.errors = errors;
    }
    List<ValidationError> getErrors() { return errors; }
}

try {
    throw new ValidationException(List.of(
        new ValidationError("name", "required"),
        new ValidationError("email", "invalid")
    ));
} catch (ValidationException e) {
    System.out.println(e.getErrors().size());
}
```

- [x] 2
- [ ] 0
- [ ] ValidationException
- [ ] Lỗi biên dịch

> **Giải thích:** Custom exception chứa list of validation errors. getErrors().size() = 2. Pattern: aggregate multiple validation errors.

## Câu 79

[TYPE: TRUE_FALSE]

Mệnh đề: "OutOfMemoryError có thể được caught nhưng recovery thường không khả thi."

- [x] Đúng
- [ ] Sai

> **Giải thích:** OOM extends Error extends Throwable → technically catchable. Nhưng system state unreliable after OOM. Catch chỉ để cleanup/logging. JVM usually needs restart.

## Câu 80

[TYPE: SELECT_RESULT]

```java
class ChainedBuilder {
    private String name;
    ChainedBuilder name(String n) {
        if (n == null || n.isBlank()) throw new IllegalArgumentException("Name cannot be blank");
        this.name = n;
        return this;
    }
    String build() {
        if (name == null) throw new IllegalStateException("Name not set");
        return "Built: " + name;
    }
}
try {
    new ChainedBuilder().build();
} catch (IllegalStateException e) {
    System.out.println(e.getMessage());
}
```

- [x] Name not set
- [ ] Name cannot be blank
- [ ] Built: null
- [ ] NullPointerException

> **Giải thích:** name() never called → name=null. build() checks → throw ISE "Name not set". Builder validation with meaningful exceptions.

## Câu 81

[TYPE: SELECT_RESULT]

```java
try {
    Class.forName("com.nonexistent.MyClass");
} catch (ClassNotFoundException e) {
    System.out.println("Class not found: " + e.getMessage());
}
```

- [x] Class not found: com.nonexistent.MyClass
- [ ] Lỗi biên dịch
- [ ] NullPointerException
- [ ] Lỗi runtime khác

> **Giải thích:** Class.forName: checked ClassNotFoundException nếu class không tồn tại. getMessage() → class name. Common khi dùng reflection.

## Câu 82

[TYPE: MULTIPLE_CHOICE]

Khi nào nên dùng Optional thay vì throw exception?

- [x] Khi absence of value là normal case, không phải error
- [ ] Luôn dùng Optional
- [ ] Không bao giờ dùng Optional
- [ ] Chỉ cho return types

> **Giải thích:** Optional: method có thể return null legitimately (findById). Exception: unexpected failure. Optional tránh NPE, communicate intent. Không dùng cho fields/parameters.

## Câu 83

[TYPE: SELECT_RESULT]

```java
try {
    int[] arr = new int[Integer.MAX_VALUE];
} catch (OutOfMemoryError e) {
    System.out.println("OOM caught");
}
System.out.println("Program continues");
```

- [x] OOM caught rồi Program continues
- [ ] Chỉ OOM caught
- [ ] Program crash
- [ ] Lỗi biên dịch

> **Giải thích:** Array quá lớn → OutOfMemoryError. Caught → "OOM caught". Program continues (nhưng state có thể unreliable). Catching Error = risky.

## Câu 84

[TYPE: FILL_BLANK]

`StackOverflowError` xảy ra khi `___` quá sâu.

- [x] đệ quy (recursion)
- [ ] loop
- [ ] exception throwing
- [ ] thread creation

> **Giải thích:** Mỗi method call = 1 stack frame. Đệ quy vô hạn → stack overflow. Kích thước stack: `-Xss` JVM option (default ~512KB-1MB).

## Câu 85

[TYPE: SELECT_RESULT]

```java
class ConnectionPool implements AutoCloseable {
    List<String> connections = new ArrayList<>();
    void add(String conn) { connections.add(conn); }
    public void close() {
        connections.forEach(c -> System.out.print("Close:" + c + " "));
        connections.clear();
    }
}
try (ConnectionPool pool = new ConnectionPool()) {
    pool.add("DB1");
    pool.add("DB2");
}
```

- [x] Close:DB1 Close:DB2
- [ ] Không in gì
- [ ] Close:DB2 Close:DB1
- [ ] Lỗi

> **Giải thích:** Exit try → auto-close. close() iterates connections → "Close:DB1 Close:DB2". Connections cleared. Resource cleanup pattern.

## Câu 86

[TYPE: SELECT_RESULT]

```java
try {
    throw new RuntimeException("A");
} catch (Exception e) {
    System.out.print(e.getMessage() + " ");
    throw new RuntimeException("B");
} finally {
    System.out.print("finally ");
}
```

Caller catches RuntimeException:

- [x] A finally, then RuntimeException "B"
- [ ] A B finally
- [ ] A finally
- [ ] finally B

> **Giải thích:** Catch prints "A ". Re-throw "B". Finally runs ("finally "). RuntimeException "B" propagates. Note: "A" exception is lost.

## Câu 87

[TYPE: MULTIPLE_CHOICE]

ExceptionUtils (Apache Commons) hoặc Throwables (Guava) cung cấp gì?

- [x] getRootCause, getStackTrace as string, rethrow checked as unchecked
- [ ] Tạo exceptions
- [ ] Ignore exceptions
- [ ] Logging

> **Giải thích:** ExceptionUtils.getRootCause(e), getStackTrace(e) → String, rethrow(e) wrap checked → unchecked. Utilities giảm boilerplate.

## Câu 88

[TYPE: SELECT_RESULT]

```java
record Either<L, R>(L left, R right) {
    boolean isRight() { return right != null; }
    static <L, R> Either<L, R> left(L value) { return new Either<>(value, null); }
    static <L, R> Either<L, R> right(R value) { return new Either<>(null, value); }
}
Either<String, Integer> parse(String s) {
    try { return Either.right(Integer.parseInt(s)); }
    catch (NumberFormatException e) { return Either.left("Invalid: " + s); }
}
Either<String, Integer> result = parse("abc");
System.out.println(result.isRight() + ": " + result.left());
```

- [x] false: Invalid: abc
- [ ] true: null
- [ ] false: null
- [ ] NumberFormatException

> **Giải thích:** Either pattern: Right = success, Left = error. "abc" parse fails → left("Invalid: abc"). isRight=false. Functional error handling.

## Câu 89

[TYPE: TRUE_FALSE]

Mệnh đề: "Trong Java, constructor có thể throw checked exceptions."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Constructor có thể throws bất kỳ exception nào. `new MyClass()` có thể throw checked exception nếu constructor khai báo throws. Caller phải handle.

## Câu 90

[TYPE: SELECT_RESULT]

```java
public static <T> T retry(Supplier<T> action, int maxAttempts) {
    Exception lastException = null;
    for (int i = 0; i < maxAttempts; i++) {
        try {
            return action.get();
        } catch (Exception e) {
            lastException = e;
            System.out.print("Retry " + (i+1) + " ");
        }
    }
    throw new RuntimeException("Failed after " + maxAttempts + " attempts", lastException);
}
// action fails 2 times then succeeds on 3rd
int[] count = {0};
String result = retry(() -> {
    if (++count[0] < 3) throw new RuntimeException("fail");
    return "success";
}, 5);
System.out.print(result);
```

- [x] Retry 1 Retry 2 success
- [ ] Retry 1 Retry 2 Retry 3 Retry 4 Retry 5 (then exception)
- [ ] success
- [ ] Retry 1 success

> **Giải thích:** Attempt 1: fail → "Retry 1". Attempt 2: fail → "Retry 2". Attempt 3: count=3 → return "success". Total: Retry 1 Retry 2 success.

## Câu 91

[TYPE: SELECT_RESULT]

```java
Object obj = "Hello";
try {
    Integer num = (Integer) obj;
} catch (ClassCastException e) {
    System.out.println("Cannot cast String to Integer");
}
```

- [x] Cannot cast String to Integer
- [ ] 0
- [ ] null
- [ ] Lỗi biên dịch

> **Giải thích:** String cannot be cast to Integer → ClassCastException. Unchecked exception. Better: use instanceof before casting.

## Câu 92

[TYPE: FILL_BLANK]

`Thread.sleep()` throws `___` exception.

- [x] InterruptedException
- [ ] SleepException
- [ ] TimeoutException
- [ ] RuntimeException

> **Giải thích:** Thread.sleep(millis): checked InterruptedException. Thread bị interrupt during sleep → exception. Handle: Thread.currentThread().interrupt() (restore status).

## Câu 93

[TYPE: SELECT_RESULT]

```java
try {
    throw new IOException("io error");
} catch (Exception e) {
    if (e instanceof IOException ioe) {
        System.out.println("IO: " + ioe.getMessage());
    }
}
```

- [x] IO: io error
- [ ] Lỗi biên dịch
- [ ] Exception
- [ ] null

> **Giải thích:** Java 16 pattern matching: `e instanceof IOException ioe` → bind to variable ioe. getMessage() → "io error".

## Câu 94

[TYPE: MULTIPLE_CHOICE]

Global Exception Handler trong Java application (non-Spring)?

- [x] Thread.setDefaultUncaughtExceptionHandler(handler)
- [ ] Không thể
- [ ] try-catch trong main
- [ ] System.setExceptionHandler

> **Giải thích:** Thread.setDefaultUncaughtExceptionHandler: catch any unhandled exception in any thread. Useful for logging, alerting before app dies.

## Câu 95

[TYPE: SELECT_RESULT]

```java
String s = "Hello";
switch (s) {
    case "Hello" -> System.out.print("Hi ");
    case "World" -> System.out.print("Earth ");
    default -> System.out.print("Unknown ");
}
try {
    s = null;
    switch (s) {
        case "Hello" -> System.out.print("Hi");
        default -> System.out.print("Default");
    }
} catch (NullPointerException e) {
    System.out.print("NPE");
}
```

- [x] Hi NPE
- [ ] Hi Default
- [ ] Hi Hi
- [ ] NPE

> **Giải thích:** First switch: "Hello" → "Hi ". Second switch: null → NullPointerException (switch on null String). Caught → "NPE".

## Câu 96

[TYPE: SELECT_RESULT]

```java
record ApiResponse<T>(T data, String error, int status) {
    static <T> ApiResponse<T> ok(T data) { return new ApiResponse<>(data, null, 200); }
    static <T> ApiResponse<T> error(String msg, int status) { return new ApiResponse<>(null, msg, status); }
}

ApiResponse<String> call(boolean success) {
    if (success) return ApiResponse.ok("data");
    return ApiResponse.error("failed", 500);
}

ApiResponse<String> resp = call(false);
System.out.println(resp.status() + ": " + resp.error());
```

- [x] 500: failed
- [ ] 200: data
- [ ] 200: null
- [ ] Exception

> **Giải thích:** success=false → ApiResponse.error("failed", 500). status()=500, error()="failed". Error as value (no exception thrown).

## Câu 97

[TYPE: TRUE_FALSE]

Mệnh đề: "Throwable có thể được thrown và caught, nhưng best practice chỉ catch Exception (không Error)."

- [x] Đúng
- [ ] Sai

> **Giải thích:** catch(Throwable) catches cả Error + Exception. Nhưng Error = JVM problems → không nên catch. Catch Exception (hoặc specific subclasses).

## Câu 98

[TYPE: SELECT_RESULT]

```java
public void processItems(List<String> items) {
    List<String> errors = new ArrayList<>();
    for (String item : items) {
        try {
            Integer.parseInt(item);
        } catch (NumberFormatException e) {
            errors.add("Invalid: " + item);
        }
    }
    if (!errors.isEmpty()) {
        throw new RuntimeException("Errors: " + errors);
    }
}
try {
    processItems(List.of("1", "abc", "2", "xyz"));
} catch (RuntimeException e) {
    System.out.println(e.getMessage());
}
```

- [x] Errors: [Invalid: abc, Invalid: xyz]
- [ ] Invalid: abc
- [ ] Không throw
- [ ] Invalid: xyz

> **Giải thích:** Process all items, collect errors. "abc" → error. "xyz" → error. After loop: 2 errors → throw with all errors. Aggregate error pattern.

## Câu 99

[TYPE: SELECT_RESULT]

```java
class CircuitBreaker {
    int failCount = 0;
    int threshold = 3;
    String call(boolean willFail) {
        if (failCount >= threshold) throw new RuntimeException("Circuit OPEN");
        try {
            if (willFail) throw new RuntimeException("Service fail");
            failCount = 0;
            return "OK";
        } catch (RuntimeException e) {
            failCount++;
            throw e;
        }
    }
}
CircuitBreaker cb = new CircuitBreaker();
try { cb.call(true); } catch (Exception e) {}
try { cb.call(true); } catch (Exception e) {}
try { cb.call(true); } catch (Exception e) {}
try { cb.call(false); } catch (RuntimeException e) {
    System.out.println(e.getMessage());
}
```

- [x] Circuit OPEN
- [ ] OK
- [ ] Service fail
- [ ] Lỗi biên dịch

> **Giải thích:** 3 failures → failCount=3 ≥ threshold. 4th call (even success): check at start → "Circuit OPEN". Circuit breaker pattern with exceptions.

## Câu 100

[TYPE: MULTIPLE_CHOICE]

Đâu là best practice cho exception messages?

- [x] Include context (what happened, what input caused it, what was expected)
- [ ] Empty message
- [ ] Chỉ exception class name
- [ ] Stack trace trong message

> **Giải thích:** Good: "User not found with id=42". Bad: "error". Include: what, where, context. Don't: expose sensitive data, include stack trace in message.
