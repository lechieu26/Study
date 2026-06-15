# Quiz - Java OOP

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Tính chất nào của OOP cho phép ẩn dữ liệu nội bộ, chỉ expose qua phương thức công khai?

- [ ] Inheritance
- [x] Encapsulation
- [ ] Polymorphism
- [ ] Abstraction

> **Giải thích:** Encapsulation (Đóng gói) là tính chất ẩn dữ liệu bên trong class bằng `private`, chỉ cho phép truy cập qua getter/setter.

## Câu 2

[TYPE: MULTIPLE_CHOICE]

Từ khóa nào dùng để kế thừa class trong Java?

- [ ] implements
- [x] extends
- [ ] inherits
- [ ] super

> **Giải thích:** `extends` dùng để kế thừa class. `implements` dùng cho interface. `super` truy cập class cha nhưng không phải từ khóa kế thừa.

## Câu 3

[TYPE: SELECT_RESULT]

Đoạn code sau in ra gì?

```java
class Animal {
    void sound() { System.out.println("..."); }
}
class Dog extends Animal {
    @Override
    void sound() { System.out.println("Gau gau"); }
}
Animal a = new Dog();
a.sound();
```

- [ ] `...`
- [x] `Gau gau`
- [ ] Compile error
- [ ] Runtime error

> **Giải thích:** Runtime Polymorphism — biến `a` kiểu Animal nhưng object thực tế là Dog, nên JVM gọi `Dog.sound()`.

## Câu 4

[TYPE: MULTIPLE_CHOICE]

Access modifier nào cho phép truy cập từ subclass ở package khác?

- [ ] private
- [ ] default (package-private)
- [x] protected
- [ ] Tất cả đều không được

> **Giải thích:** `protected` cho phép truy cập từ cùng package VÀ subclass (kể cả khác package). `default` chỉ cho cùng package.

## Câu 5

[TYPE: MULTIPLE_CHOICE]

Khi override `equals()`, bắt buộc phải override method nào?

- [ ] toString()
- [ ] clone()
- [x] hashCode()
- [ ] finalize()

> **Giải thích:** Quy tắc bắt buộc: nếu `a.equals(b) == true` thì `a.hashCode() == b.hashCode()`. HashMap/HashSet phụ thuộc vào quy tắc này.

## Câu 6

[TYPE: SELECT_RESULT]

Đoạn code nào đúng khi tạo abstract class?

```java
// A
abstract class Shape {
    abstract double area();
    void print() { System.out.println("Shape"); }
}

// B
abstract class Shape {
    abstract double area() { return 0; }
}

// C
class Shape {
    abstract double area();
}
```

- [x] A
- [ ] B
- [ ] C
- [ ] Cả A và B

> **Giải thích:** A đúng — abstract class có thể chứa cả abstract method (không body) và concrete method. B sai — abstract method không có body. C sai — class chứa abstract method phải khai báo abstract.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

So sánh nào sau đây là đúng giữa Abstract Class và Interface?

- [ ] Interface có thể có constructor
- [x] Một class có thể implement nhiều Interface
- [ ] Abstract class chỉ chứa abstract methods
- [ ] Interface có thể có instance fields

> **Giải thích:** Java chỉ hỗ trợ single inheritance cho class, nhưng một class có thể implement nhiều interface. Interface không có constructor, chỉ có `public static final` fields.

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Nguyên tắc SOLID nào nói rằng "Mỗi class chỉ nên có một lý do để thay đổi"?

- [x] Single Responsibility Principle
- [ ] Open/Closed Principle
- [ ] Liskov Substitution Principle
- [ ] Dependency Inversion Principle

> **Giải thích:** SRP (Single Responsibility Principle) — mỗi class chỉ đảm nhận một trách nhiệm, một lý do để thay đổi.

## Câu 9

[TYPE: MULTIPLE_CHOICE]

Composition khác Aggregation ở điểm nào?

- [x] Composition: object con phụ thuộc vòng đời object cha
- [ ] Aggregation: object con phụ thuộc vòng đời object cha
- [ ] Composition và Aggregation hoàn toàn giống nhau
- [ ] Aggregation là quan hệ mạnh hơn Composition

> **Giải thích:** Composition là quan hệ "has-a" mạnh — object con không tồn tại nếu cha bị hủy (Car-Engine). Aggregation là quan hệ yếu — con tồn tại độc lập (Classroom-Student).

## Câu 10

[TYPE: SELECT_RESULT]

Đoạn code sau minh họa pattern nào?

```java
class Pizza {
    private String size;
    private boolean cheese;
    private boolean pepperoni;

    private Pizza(Builder b) {
        this.size = b.size;
        this.cheese = b.cheese;
        this.pepperoni = b.pepperoni;
    }

    static class Builder {
        private String size;
        private boolean cheese;
        private boolean pepperoni;

        Builder(String size) { this.size = size; }
        Builder cheese(boolean v) { this.cheese = v; return this; }
        Builder pepperoni(boolean v) { this.pepperoni = v; return this; }
        Pizza build() { return new Pizza(this); }
    }
}
```

- [ ] Singleton
- [ ] Factory
- [x] Builder
- [ ] Observer

> **Giải thích:** Builder Pattern — tạo object phức tạp từng bước qua method chaining. Constructor private, chỉ Builder tạo được object.
