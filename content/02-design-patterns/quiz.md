# Quiz - Design Patterns

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Singleton Pattern thuộc nhóm nào?

- [x] Creational
- [ ] Structural
- [ ] Behavioral
- [ ] Concurrency

> **Giải thích:** Singleton thuộc nhóm Creational Pattern vì nó liên quan đến cách tạo đối tượng — đảm bảo chỉ có duy nhất một instance của class.

## Câu 2

[TYPE: SELECT_RESULT]

Đoạn code sau minh họa pattern nào?

```java
public class Logger {
    private static Logger instance;
    private Logger() {}
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
}
```

- [ ] Factory Method
- [x] Singleton
- [ ] Builder
- [ ] Prototype

> **Giải thích:** Đây là Singleton Pattern: constructor private, biến static instance, phương thức static `getInstance()` trả về instance duy nhất.

## Câu 3

[TYPE: FILL_BLANK]

Pattern `___` cho phép tạo đối tượng phức tạp theo từng bước, tách quá trình khởi tạo ra khỏi biểu diễn.

- [ ] Factory
- [x] Builder
- [ ] Prototype
- [ ] Abstract Factory

> **Giải thích:** Builder Pattern tách quá trình xây dựng đối tượng phức tạp khỏi biểu diễn, cho phép tạo từng bước với các phương thức chain (fluent API).

## Câu 4

[TYPE: TRUE_FALSE]

Mệnh đề: "Observer Pattern là một Structural Pattern."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Observer Pattern thuộc nhóm Behavioral Pattern, không phải Structural. Nó định nghĩa quan hệ one-to-many giữa các đối tượng.

## Câu 5

[TYPE: MULTIPLE_CHOICE]

Pattern nào giúp thêm chức năng mới cho đối tượng mà không thay đổi cấu trúc class gốc?

- [ ] Adapter
- [x] Decorator
- [ ] Proxy
- [ ] Facade

> **Giải thích:** Decorator Pattern wrap đối tượng gốc và thêm hành vi mới. Ví dụ điển hình: Java I/O Streams (BufferedInputStream wraps FileInputStream).

## Câu 6

[TYPE: SELECT_RESULT]

Đoạn code sau minh họa pattern nào?

```java
interface Shape { void draw(); }
class Circle implements Shape { public void draw() { System.out.println("Circle"); } }
class Rectangle implements Shape { public void draw() { System.out.println("Rectangle"); } }
class ShapeFactory {
    public Shape createShape(String type) {
        return switch (type) {
            case "circle" -> new Circle();
            case "rectangle" -> new Rectangle();
            default -> throw new IllegalArgumentException();
        };
    }
}
```

- [x] Factory Method
- [ ] Abstract Factory
- [ ] Builder
- [ ] Strategy

> **Giải thích:** Đây là Factory Method Pattern: sử dụng một phương thức factory để tạo đối tượng thay vì gọi constructor trực tiếp.

## Câu 7

[TYPE: FILL_BLANK]

Pattern `___` cung cấp một interface đơn giản cho một hệ thống con phức tạp.

- [ ] Adapter
- [ ] Bridge
- [x] Facade
- [ ] Proxy

> **Giải thích:** Facade Pattern ẩn đi sự phức tạp của hệ thống con (subsystem) và cung cấp một interface đơn giản, dễ sử dụng cho client.

## Câu 8

[TYPE: TRUE_FALSE]

Mệnh đề: "Strategy Pattern và State Pattern có cấu trúc class diagram gần giống nhau."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Cả hai đều sử dụng composition và interface/abstract class tương tự. Khác biệt ở intent: Strategy cho phép chọn thuật toán, State thay đổi hành vi theo trạng thái.

## Câu 9

[TYPE: MULTIPLE_CHOICE]

Adapter Pattern chuyển đổi interface của class hiện có thành interface mà client mong đợi. Adapter thuộc nhóm nào?

- [ ] Creational
- [x] Structural
- [ ] Behavioral
- [ ] Architectural

> **Giải thích:** Adapter là Structural Pattern vì nó liên quan đến cấu trúc class/object — kết nối hai interface không tương thích.

## Câu 10

[TYPE: SELECT_RESULT]

Đoạn code sau minh họa pattern nào?

```java
interface SortStrategy { void sort(int[] arr); }
class BubbleSort implements SortStrategy {
    public void sort(int[] arr) { /* bubble sort */ }
}
class QuickSort implements SortStrategy {
    public void sort(int[] arr) { /* quick sort */ }
}
class Sorter {
    private SortStrategy strategy;
    public void setStrategy(SortStrategy s) { this.strategy = s; }
    public void sort(int[] arr) { strategy.sort(arr); }
}
```

- [ ] Template Method
- [ ] Observer
- [x] Strategy
- [ ] Command

> **Giải thích:** Strategy Pattern cho phép chọn thuật toán (strategy) tại runtime. Client có thể thay đổi strategy thông qua setter.
