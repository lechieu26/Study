# Quiz - Java OOP

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Đâu là 4 tính chất cơ bản của lập trình hướng đối tượng (OOP)?

- [ ] Abstraction, Polymorphism, Concurrency, Encapsulation
- [x] Encapsulation, Inheritance, Polymorphism, Abstraction
- [ ] Encapsulation, Inheritance, Generics, Abstraction
- [ ] Encapsulation, Composition, Polymorphism, Abstraction

> **Giải thích:** 4 tính chất cơ bản của OOP: Đóng gói (Encapsulation), Kế thừa (Inheritance), Đa hình (Polymorphism), Trừu tượng (Abstraction).

## Câu 2

[TYPE: SELECT_RESULT]

Cho đoạn code sau, kết quả in ra là gì?

```java
class Animal {
    void speak() { System.out.println("..."); }
}
class Dog extends Animal {
    void speak() { System.out.println("Gâu gâu"); }
}
Animal a = new Dog();
a.speak();
```

- [ ] ...
- [x] Gâu gâu
- [ ] Lỗi biên dịch
- [ ] Lỗi runtime

> **Giải thích:** Đây là runtime polymorphism (dynamic binding). Biến `a` kiểu `Animal` nhưng trỏ đến đối tượng `Dog`. Khi gọi `speak()`, JVM gọi method của `Dog`.

## Câu 3

[TYPE: FILL_BLANK]

Tính chất OOP nào giúp ẩn chi tiết triển khai và chỉ cung cấp interface cho bên ngoài sử dụng? Đó là tính `___`.

- [ ] Inheritance
- [ ] Polymorphism
- [x] Encapsulation
- [ ] Composition

> **Giải thích:** Encapsulation (Đóng gói) ẩn dữ liệu bên trong class bằng access modifier (private) và cung cấp getter/setter để truy cập.

## Câu 4

[TYPE: TRUE_FALSE]

Mệnh đề: "Abstract class có thể có constructor."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Abstract class CÓ THỂ có constructor. Constructor này được gọi khi subclass tạo đối tượng. Tuy nhiên, không thể tạo instance trực tiếp từ abstract class.

## Câu 5

[TYPE: SELECT_RESULT]

Cho đoạn code sau:

```java
class Parent {
    int x = 10;
    int getX() { return x; }
}
class Child extends Parent {
    int x = 20;
    int getX() { return x; }
}
Parent p = new Child();
System.out.println(p.x);
System.out.println(p.getX());
```

- [ ] 20 và 20
- [x] 10 và 20
- [ ] 10 và 10
- [ ] 20 và 10

> **Giải thích:** Field access (`p.x`) dùng compile-time type → `Parent.x = 10`. Method call (`p.getX()`) dùng runtime type → `Child.getX()` trả về 20. Fields không bị override.

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Trong SOLID, chữ S (Single Responsibility Principle) có nghĩa là gì?

- [x] Mỗi class chỉ nên có một lý do để thay đổi
- [ ] Mỗi class chỉ có một method
- [ ] Mỗi class chỉ có một field
- [ ] Mỗi package chỉ có một class

> **Giải thích:** SRP: Một class chỉ nên chịu trách nhiệm cho một chức năng duy nhất. Nếu class có nhiều lý do thay đổi, nên tách thành nhiều class.

## Câu 7

[TYPE: SELECT_RESULT]

Cho đoạn code sau:

```java
interface Flyable {
    default void fly() { System.out.println("Flying"); }
}
interface Swimmable {
    default void fly() { System.out.println("Swimming fly"); }
}
class Duck implements Flyable, Swimmable {
    // ???
}
```

Điều gì xảy ra khi compile?

- [ ] In ra "Flying"
- [ ] In ra "Swimming fly"
- [x] Lỗi biên dịch vì conflict default method
- [ ] Tự động chọn method của interface đầu tiên

> **Giải thích:** Khi hai interface có cùng default method, class implement cả hai phải override method đó để resolve conflict. Nếu không, compiler báo lỗi.

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Sự khác biệt chính giữa abstract class và interface?

- [ ] Abstract class có thể có field, interface thì không
- [ ] Interface có thể có method body, abstract class thì không
- [x] Abstract class cho phép đơn kế thừa, interface cho phép đa kế thừa
- [ ] Không có sự khác biệt từ Java 8

> **Giải thích:** Class chỉ extends 1 abstract class nhưng implements nhiều interface. Abstract class có constructor và state, interface thì không (field trong interface mặc định `public static final`).

## Câu 9

[TYPE: SELECT_RESULT]

Cho đoạn code sau:

```java
class A {
    A() { System.out.print("A "); }
}
class B extends A {
    B() { System.out.print("B "); }
}
class C extends B {
    C() { System.out.print("C "); }
}
new C();
```

- [x] A B C
- [ ] C B A
- [ ] C
- [ ] A C

> **Giải thích:** Constructor chạy từ class cha → class con. `new C()` → gọi constructor A (cha nhất) → B → C. Mỗi constructor ngầm gọi `super()` ở dòng đầu.

## Câu 10

[TYPE: TRUE_FALSE]

Mệnh đề: "Method overriding cho phép subclass thay đổi access modifier thành restrictive hơn."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Khi override, access modifier chỉ có thể bằng hoặc rộng hơn (ví dụ: protected → public). KHÔNG được restrictive hơn (ví dụ: public → private).

## Câu 11

[TYPE: SELECT_RESULT]

Cho đoạn code sau:

```java
class Shape {
    double area() { return 0; }
}
class Circle extends Shape {
    double r;
    Circle(double r) { this.r = r; }
    double area() { return Math.PI * r * r; }
}
Shape s = new Circle(5);
System.out.printf("%.2f", s.area());
```

- [ ] 0.00
- [x] 78.54
- [ ] Lỗi biên dịch
- [ ] 25.00

> **Giải thích:** Polymorphism: `s` là kiểu `Shape` nhưng đối tượng thực tế là `Circle`. Gọi `area()` → `Circle.area()` = π × 5² ≈ 78.54.

## Câu 12

[TYPE: MULTIPLE_CHOICE]

Trong SOLID, chữ O (Open/Closed Principle) có nghĩa là gì?

- [ ] Mở để sửa đổi, đóng để mở rộng
- [x] Mở để mở rộng, đóng để sửa đổi
- [ ] Mở tất cả class cho public
- [ ] Đóng tất cả field bằng private

> **Giải thích:** OCP: Entities nên mở cho mở rộng (extension) nhưng đóng cho sửa đổi (modification). Thêm tính năng mới bằng cách thêm code, không sửa code cũ.

## Câu 13

[TYPE: SELECT_RESULT]

Cho đoạn code sau:

```java
class Base {
    static void greet() { System.out.println("Base"); }
}
class Derived extends Base {
    static void greet() { System.out.println("Derived"); }
}
Base b = new Derived();
b.greet();
```

- [x] Base
- [ ] Derived
- [ ] Lỗi biên dịch
- [ ] Lỗi runtime

> **Giải thích:** Static method không bị override mà bị hiding. `b.greet()` gọi dựa trên compile-time type → `Base.greet()`. Static binding xảy ra tại compile-time.

## Câu 14

[TYPE: FILL_BLANK]

Nguyên tắc `___` trong SOLID nói rằng: "Subtype phải có thể thay thế được cho base type mà không làm sai chương trình."

- [x] Liskov Substitution
- [ ] Interface Segregation
- [ ] Dependency Inversion
- [ ] Single Responsibility

> **Giải thích:** LSP (Liskov Substitution Principle): Nếu S là subtype của T, thì mọi chỗ dùng T đều có thể thay bằng S mà chương trình vẫn đúng.

## Câu 15

[TYPE: TRUE_FALSE]

Mệnh đề: "Trong Java, một abstract class có thể implement interface."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Abstract class hoàn toàn có thể implement interface. Nó không bắt buộc phải implement tất cả method → các method chưa implement để lại cho concrete subclass.

## Câu 16

[TYPE: SELECT_RESULT]

Cho đoạn code sau:

```java
class Singleton {
    private static Singleton instance;
    private Singleton() {}
    public static Singleton getInstance() {
        if (instance == null) instance = new Singleton();
        return instance;
    }
}
Singleton s1 = Singleton.getInstance();
Singleton s2 = Singleton.getInstance();
System.out.println(s1 == s2);
```

- [x] true
- [ ] false
- [ ] Lỗi biên dịch
- [ ] NullPointerException

> **Giải thích:** Singleton pattern đảm bảo chỉ có 1 instance. Lần gọi đầu tạo instance mới, lần gọi sau trả về instance đã tạo. Nên `s1 == s2` → true.

## Câu 17

[TYPE: MULTIPLE_CHOICE]

Composition khác Inheritance ở điểm nào?

- [ ] Composition dùng extends, Inheritance dùng has-a
- [x] Composition là quan hệ "has-a", Inheritance là quan hệ "is-a"
- [ ] Composition chậm hơn Inheritance
- [ ] Inheritance linh hoạt hơn Composition

> **Giải thích:** Inheritance: Dog IS-A Animal. Composition: Car HAS-A Engine. "Favor composition over inheritance" vì composition linh hoạt hơn, giảm coupling.

## Câu 18

[TYPE: SELECT_RESULT]

Cho đoạn code sau:

```java
abstract class Vehicle {
    abstract void start();
    void stop() { System.out.println("Stopped"); }
}
class Car extends Vehicle {
    void start() { System.out.println("Car started"); }
}
Vehicle v = new Car();
v.start();
v.stop();
```

- [ ] Lỗi biên dịch
- [x] Car started và Stopped
- [ ] Chỉ Car started
- [ ] Chỉ Stopped

> **Giải thích:** `Car` implement `start()` abstract → "Car started". `stop()` là concrete method kế thừa từ `Vehicle` → "Stopped".

## Câu 19

[TYPE: TRUE_FALSE]

Mệnh đề: "Phương thức private có thể bị override bởi subclass."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Phương thức `private` không thể bị override vì subclass không nhìn thấy. Nếu subclass tạo method cùng tên, đó là method mới, không phải override.

## Câu 20

[TYPE: SELECT_RESULT]

Cho đoạn code sau:

```java
class Calculator {
    int add(int a, int b) { return a + b; }
    double add(double a, double b) { return a + b; }
    int add(int a, int b, int c) { return a + b + c; }
}
Calculator calc = new Calculator();
System.out.println(calc.add(1, 2));
System.out.println(calc.add(1.5, 2.5));
System.out.println(calc.add(1, 2, 3));
```

- [x] 3, 4.0, 6
- [ ] Lỗi biên dịch (trùng tên method)
- [ ] 3, 4, 6
- [ ] 3.0, 4.0, 6.0

> **Giải thích:** Đây là method overloading: cùng tên nhưng khác tham số. Compiler chọn method dựa trên tham số truyền vào. int+int=int, double+double=double.

## Câu 21

[TYPE: MULTIPLE_CHOICE]

Trong SOLID, Interface Segregation Principle (ISP) nói gì?

- [ ] Mỗi interface chỉ có một method
- [x] Client không nên bị ép phụ thuộc vào method mà nó không dùng
- [ ] Interface nên càng lớn càng tốt
- [ ] Chỉ dùng một interface cho toàn bộ ứng dụng

> **Giải thích:** ISP: Nên tách interface lớn thành nhiều interface nhỏ, chuyên biệt. Class chỉ implement interface mà nó thực sự cần.

## Câu 22

[TYPE: SELECT_RESULT]

Cho đoạn code sau:

```java
class Employee {
    private String name;
    private double salary;
    
    Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }
    
    String getName() { return name; }
    double getSalary() { return salary; }
    void setSalary(double salary) {
        if (salary > 0) this.salary = salary;
    }
}
Employee e = new Employee("An", 1000);
e.setSalary(-500);
System.out.println(e.getSalary());
```

- [x] 1000.0
- [ ] -500.0
- [ ] 0.0
- [ ] Lỗi runtime

> **Giải thích:** Encapsulation: `setSalary()` kiểm tra dữ liệu đầu vào. Giá trị -500 không thỏa điều kiện `> 0` nên salary giữ nguyên 1000.

## Câu 23

[TYPE: FILL_BLANK]

Nguyên tắc Dependency Inversion Principle (DIP) nói: "Module cấp cao không nên phụ thuộc vào module cấp thấp. Cả hai nên phụ thuộc vào `___`."

- [ ] concrete class
- [x] abstraction (interface/abstract class)
- [ ] static method
- [ ] singleton

> **Giải thích:** DIP: Phụ thuộc vào abstraction, không phụ thuộc vào implementation. Ví dụ: Service phụ thuộc vào Repository interface, không phụ thuộc vào JdbcRepository.

## Câu 24

[TYPE: SELECT_RESULT]

Cho đoạn code sau:

```java
interface Printable {
    void print();
}
class Document implements Printable {
    String content;
    Document(String content) { this.content = content; }
    public void print() { System.out.println(content); }
}
Printable p = new Document("Hello OOP");
p.print();
```

- [x] Hello OOP
- [ ] Lỗi biên dịch
- [ ] null
- [ ] Printable@hashcode

> **Giải thích:** Interface reference (`Printable p`) trỏ đến object `Document`. Gọi `print()` → runtime polymorphism → `Document.print()` → in "Hello OOP".

## Câu 25

[TYPE: TRUE_FALSE]

Mệnh đề: "Trong Java, constructor có thể được kế thừa bởi subclass."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Constructor KHÔNG được kế thừa. Subclass phải tự định nghĩa constructor. Tuy nhiên, constructor subclass có thể gọi constructor cha bằng `super()`.

## Câu 26

[TYPE: SELECT_RESULT]

Cho đoạn code sau:

```java
class A {
    void display() { System.out.println("A"); }
}
class B extends A {
    void display() { System.out.println("B"); }
}
class C extends B {
    void display() { System.out.println("C"); }
}
A obj = new C();
obj.display();
```

- [ ] A
- [ ] B
- [x] C
- [ ] A B C

> **Giải thích:** Dynamic dispatch: Object thực tế là `C`, nên `display()` của `C` được gọi. Java tìm method từ class thực tế (C) lên trên.

## Câu 27

[TYPE: MULTIPLE_CHOICE]

Khi nào nên dùng abstract class thay vì interface?

- [x] Khi cần chia sẻ code (method body) và state (fields) giữa các related class
- [ ] Khi cần đa kế thừa
- [ ] Khi không có method nào cần implement
- [ ] Khi tất cả method đều là static

> **Giải thích:** Dùng abstract class khi: có shared code, có state (fields), các class liên quan chặt chẽ. Dùng interface khi: cần đa kế thừa, các class không liên quan nhưng cùng behavior.

## Câu 28

[TYPE: SELECT_RESULT]

Cho đoạn code sau:

```java
class Outer {
    private int x = 10;
    class Inner {
        void show() { System.out.println(x); }
    }
}
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner();
inner.show();
```

- [x] 10
- [ ] Lỗi biên dịch (x là private)
- [ ] 0
- [ ] null

> **Giải thích:** Inner class (non-static) có thể truy cập tất cả members của outer class, kể cả `private`. Inner class giữ reference ngầm đến outer object.

## Câu 29

[TYPE: TRUE_FALSE]

Mệnh đề: "Covariant return type cho phép override method trả về subtype của kiểu trả về gốc."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Từ Java 5, method override có thể trả về subtype. Ví dụ: nếu parent trả về `Animal`, child có thể trả về `Dog` (subtype của Animal).

## Câu 30

[TYPE: SELECT_RESULT]

Cho đoạn code sau:

```java
interface Logger {
    void log(String msg);
}
class ConsoleLogger implements Logger {
    public void log(String msg) { System.out.println("[LOG] " + msg); }
}
class Service {
    private Logger logger;
    Service(Logger logger) { this.logger = logger; }
    void process() { logger.log("Processing"); }
}
Service s = new Service(new ConsoleLogger());
s.process();
```

- [x] [LOG] Processing
- [ ] Processing
- [ ] Lỗi biên dịch
- [ ] null

> **Giải thích:** Dependency Injection: `Service` phụ thuộc vào `Logger` interface, nhận `ConsoleLogger` qua constructor. Gọi `process()` → `ConsoleLogger.log()`.

## Câu 31

[TYPE: MULTIPLE_CHOICE]

Method overloading khác method overriding ở điểm nào?

- [ ] Overloading xảy ra giữa parent-child, overriding trong cùng class
- [x] Overloading cùng class khác tham số, overriding giữa parent-child cùng signature
- [ ] Overloading dùng từ khóa @Override
- [ ] Không có sự khác biệt

> **Giải thích:** Overloading: cùng tên, khác tham số, cùng class (compile-time polymorphism). Overriding: cùng tên + signature, khác class (parent-child, runtime polymorphism).

## Câu 32

[TYPE: SELECT_RESULT]

Cho đoạn code sau:

```java
class Animal {
    String type = "Animal";
    String getType() { return type; }
}
class Cat extends Animal {
    String type = "Cat";
    String getType() { return type; }
}
Animal a = new Cat();
System.out.println(a.type + " - " + a.getType());
```

- [ ] Cat - Cat
- [x] Animal - Cat
- [ ] Animal - Animal
- [ ] Cat - Animal

> **Giải thích:** `a.type` → compile-time type (Animal) → "Animal". `a.getType()` → runtime type (Cat) → "Cat". Fields dùng static binding, methods dùng dynamic binding.

## Câu 33

[TYPE: TRUE_FALSE]

Mệnh đề: "final method không thể bị override nhưng có thể bị overload."

- [x] Đúng
- [ ] Sai

> **Giải thích:** `final` ngăn override trong subclass. Nhưng vẫn có thể overload (tạo method cùng tên khác tham số) trong cùng class hoặc subclass.

## Câu 34

[TYPE: SELECT_RESULT]

Cho đoạn code sau:

```java
enum Season {
    SPRING("Ấm"), SUMMER("Nóng"), AUTUMN("Mát"), WINTER("Lạnh");
    
    private String desc;
    Season(String desc) { this.desc = desc; }
    String getDesc() { return desc; }
}
System.out.println(Season.SUMMER.getDesc());
System.out.println(Season.values().length);
```

- [x] Nóng và 4
- [ ] SUMMER và 4
- [ ] Nóng và SUMMER
- [ ] Lỗi biên dịch

> **Giải thích:** Enum có constructor private, field và method. `SUMMER.getDesc()` → "Nóng". `values()` trả về mảng tất cả giá trị → length = 4.

## Câu 35

[TYPE: MULTIPLE_CHOICE]

Đâu là ví dụ đúng về Polymorphism?

- [ ] Tạo nhiều instance từ cùng class
- [x] Biến kiểu cha trỏ đến đối tượng kiểu con và gọi method theo runtime type
- [ ] Khai báo biến private
- [ ] Dùng static method

> **Giải thích:** Polymorphism: cùng một lời gọi method cho kết quả khác nhau tùy đối tượng thực tế. `Animal a = new Dog(); a.speak();` → gọi `Dog.speak()`.

## Câu 36

[TYPE: SELECT_RESULT]

Cho đoạn code sau:

```java
class Person implements Cloneable {
    String name;
    Person(String name) { this.name = name; }
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
Person p1 = new Person("An");
Person p2 = (Person) p1.clone();
p2.name = "Bình";
System.out.println(p1.name + " - " + p2.name);
```

- [x] An - Bình
- [ ] Bình - Bình
- [ ] An - An
- [ ] Lỗi runtime

> **Giải thích:** `clone()` tạo shallow copy. Thay đổi `p2.name` gán reference mới (String immutable) nên `p1.name` không đổi. Nhưng nếu field là mutable object, cần deep copy.

## Câu 37

[TYPE: FILL_BLANK]

Trong Java, từ khóa `___` được dùng trước class declaration để ngăn tạo instance trực tiếp và buộc phải kế thừa.

- [x] abstract
- [ ] final
- [ ] static
- [ ] sealed

> **Giải thích:** `abstract class` không thể tạo instance bằng `new`. Phải tạo concrete subclass implements tất cả abstract methods.

## Câu 38

[TYPE: SELECT_RESULT]

Cho đoạn code sau:

```java
interface Drawable {
    void draw();
}
Drawable d = () -> System.out.println("Drawing circle");
d.draw();
```

- [x] Drawing circle
- [ ] Lỗi biên dịch
- [ ] null
- [ ] Lỗi runtime

> **Giải thích:** Lambda expression implement functional interface (interface có 1 abstract method). `() -> ...` implement `draw()`. Đây là anonymous implementation.

## Câu 39

[TYPE: MULTIPLE_CHOICE]

Tight coupling (phụ thuộc chặt) gây ra vấn đề gì?

- [x] Khó thay đổi, test và maintain
- [ ] Tăng hiệu suất
- [ ] Giảm bộ nhớ sử dụng
- [ ] Tăng tính bảo mật

> **Giải thích:** Tight coupling: class A phụ thuộc trực tiếp vào implementation của class B. Thay đổi B → phải sửa A. Loose coupling (qua interface) giúp dễ maintain, test, thay đổi.

## Câu 40

[TYPE: SELECT_RESULT]

Cho đoạn code sau:

```java
class Parent {
    Parent() {
        System.out.print("P ");
        display();
    }
    void display() { System.out.print("Parent "); }
}
class Child extends Parent {
    int x = 10;
    void display() { System.out.print("x=" + x + " "); }
}
new Child();
```

- [ ] P Parent
- [x] P x=0
- [ ] P x=10
- [ ] Lỗi runtime

> **Giải thích:** Trong constructor Parent, `display()` bị override bởi Child. Nhưng `x` chưa được khởi tạo (vẫn là 0 mặc định) vì constructor cha chạy trước khi field con được gán.

## Câu 41

[TYPE: TRUE_FALSE]

Mệnh đề: "Interface có thể chứa private method kể từ Java 9."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Java 9 cho phép private method trong interface. Dùng để tái sử dụng code giữa các default methods mà không expose ra ngoài.

## Câu 42

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
class Box<T> {
    private T item;
    void set(T item) { this.item = item; }
    T get() { return item; }
}
Box<String> box = new Box<>();
box.set("Hello");
String s = box.get();
System.out.println(s.length());
```

- [x] 5
- [ ] Lỗi biên dịch
- [ ] Hello
- [ ] ClassCastException

> **Giải thích:** Generic class `Box<T>` với T=String. `set("Hello")` lưu String, `get()` trả về String → `s.length()` = 5. Type-safe tại compile-time.

## Câu 43

[TYPE: MULTIPLE_CHOICE]

Đâu là nhược điểm của Inheritance so với Composition?

- [ ] Inheritance chậm hơn
- [x] Inheritance tạo tight coupling giữa parent và child class
- [ ] Inheritance không hỗ trợ polymorphism
- [ ] Inheritance không thể có shared code

> **Giải thích:** Inheritance: subclass phụ thuộc chặt vào superclass. Thay đổi superclass có thể break subclass. Composition linh hoạt hơn: dễ thay đổi, swap behavior tại runtime.

## Câu 44

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
sealed interface Shape permits Circle, Rectangle {}
record Circle(double r) implements Shape {}
record Rectangle(double w, double h) implements Shape {}

Shape s = new Circle(5);
String result = switch(s) {
    case Circle c -> "Circle r=" + c.r();
    case Rectangle r -> "Rect " + r.w() + "x" + r.h();
};
System.out.println(result);
```

- [x] Circle r=5.0
- [ ] Lỗi biên dịch
- [ ] Rect 5.0x5.0
- [ ] null

> **Giải thích:** Sealed interface + pattern matching switch (Java 17+). `s` là `Circle(5)` → match case `Circle c` → in "Circle r=5.0". Switch exhaustive vì sealed.

## Câu 45

[TYPE: TRUE_FALSE]

Mệnh đề: "Static method có thể bị override trong Java."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Static method không bị override mà bị hiding (ẩn). Override chỉ áp dụng cho instance method. Static method resolve dựa trên compile-time type.

## Câu 46

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
class Animal {
    protected void eat() { System.out.println("Animal eats"); }
}
class Dog extends Animal {
    public void eat() { System.out.println("Dog eats"); }
}
```

Code trên có compile thành công không?

- [x] Có, vì access modifier rộng hơn (protected → public) khi override
- [ ] Không, vì không được thay đổi access modifier
- [ ] Không, vì thiếu @Override
- [ ] Có, nhưng sẽ lỗi runtime

> **Giải thích:** Khi override, access modifier phải bằng hoặc rộng hơn. `protected → public` hợp lệ. `@Override` là optional annotation.

## Câu 47

[TYPE: MULTIPLE_CHOICE]

Đâu là cách đúng để implement Encapsulation?

- [ ] Dùng public fields và public methods
- [x] Dùng private fields với public getter/setter
- [ ] Dùng protected fields không có methods
- [ ] Dùng static fields

> **Giải thích:** Encapsulation: ẩn dữ liệu (private fields), kiểm soát truy cập qua getter/setter. Setter có thể validate dữ liệu trước khi gán.

## Câu 48

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
class Base {
    void print(int x) { System.out.println("int: " + x); }
    void print(String s) { System.out.println("String: " + s); }
}
Base b = new Base();
b.print(42);
b.print("Hello");
b.print((Object) "Test");
```

- [ ] int: 42, String: Hello, String: Test
- [x] int: 42, String: Hello, Lỗi biên dịch
- [ ] int: 42, String: Hello, Object: Test
- [ ] Lỗi biên dịch

> **Giải thích:** Không có `print(Object)` method. Cast `"Test"` sang `Object` → không có method phù hợp → lỗi biên dịch. Overloading resolve tại compile-time dựa trên declared type.

## Câu 49

[TYPE: TRUE_FALSE]

Mệnh đề: "Trong Java, tất cả các class đều ngầm kế thừa từ class Object."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Mọi class trong Java (trừ `Object` itself) đều kế thừa từ `java.lang.Object`. Điều này cho phép mọi đối tượng có `toString()`, `equals()`, `hashCode()`.

## Câu 50

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
interface Validator<T> {
    boolean validate(T value);
}
Validator<String> notEmpty = s -> s != null && !s.isEmpty();
System.out.println(notEmpty.validate("Hello"));
System.out.println(notEmpty.validate(""));
System.out.println(notEmpty.validate(null));
```

- [x] true, false, false
- [ ] true, true, false
- [ ] true, false, NullPointerException
- [ ] Lỗi biên dịch

> **Giải thích:** Lambda implement generic interface. "Hello" → non-null và non-empty → true. "" → empty → false. null → `s != null` → false (short-circuit, không gọi `isEmpty()`).

## Câu 51

[TYPE: MULTIPLE_CHOICE]

Đâu là ví dụ đúng về Abstract class?

- [ ] Class không có field
- [ ] Class chỉ có static method
- [x] Class có ít nhất một abstract method (hoặc được khai báo abstract)
- [ ] Class chỉ có constructor

> **Giải thích:** Abstract class có thể: có hoặc không có abstract method, có constructor, field, concrete method. Nếu có abstract method → class phải khai báo abstract.

## Câu 52

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
class Counter {
    private int count = 0;
    void increment() { count++; }
    int getCount() { return count; }
}
Counter c1 = new Counter();
Counter c2 = c1;
c1.increment();
c2.increment();
System.out.println(c1.getCount());
```

- [ ] 1
- [x] 2
- [ ] 0
- [ ] Lỗi biên dịch

> **Giải thích:** `c2 = c1` → cả hai trỏ đến cùng đối tượng Counter. Gọi increment() qua c1 hoặc c2 đều thay đổi cùng object. count: 0 → 1 → 2.

## Câu 53

[TYPE: FILL_BLANK]

Pattern nào cho phép tạo đối tượng mà không cần chỉ rõ class cụ thể? Đó là `___` Pattern.

- [x] Factory
- [ ] Singleton
- [ ] Observer
- [ ] Builder

> **Giải thích:** Factory Pattern: dùng factory method để tạo object mà client không cần biết class cụ thể. Ví dụ: `ShapeFactory.create("circle")` trả về Circle object.

## Câu 54

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
class Immutable {
    private final String name;
    private final List<String> items;
    
    Immutable(String name, List<String> items) {
        this.name = name;
        this.items = new ArrayList<>(items);  // defensive copy
    }
    String getName() { return name; }
    List<String> getItems() { return Collections.unmodifiableList(items); }
}
Immutable obj = new Immutable("Test", List.of("A", "B"));
obj.getItems().add("C");  // dòng này
```

- [ ] Thêm "C" thành công
- [x] UnsupportedOperationException
- [ ] Lỗi biên dịch
- [ ] NullPointerException

> **Giải thích:** `getItems()` trả về unmodifiable view → gọi `add()` ném exception. Đây là cách implement immutable class đúng: defensive copy + unmodifiable return.

## Câu 55

[TYPE: TRUE_FALSE]

Mệnh đề: "Overriding method có thể throw checked exception mới mà parent method không khai báo."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Override method chỉ có thể throw subclass of checked exception hoặc unchecked exception. KHÔNG được throw checked exception mới hoặc broader exception.

## Câu 56

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
interface A {
    default void hello() { System.out.println("A"); }
}
interface B extends A {
    default void hello() { System.out.println("B"); }
}
class C implements A, B {}
new C().hello();
```

- [ ] A
- [x] B
- [ ] Lỗi biên dịch
- [ ] A B

> **Giải thích:** Khi B extends A và cả hai có default method `hello()`, B's method "thắng" vì cụ thể hơn (most specific interface). C kế thừa method từ B.

## Câu 57

[TYPE: MULTIPLE_CHOICE]

Đâu là đặc điểm của Immutable class?

- [ ] Tất cả field đều public
- [x] Class final, field private final, không có setter, defensive copy
- [ ] Class abstract
- [ ] Chỉ có static method

> **Giải thích:** Immutable class: `final class`, `private final` fields, no setters, defensive copy trong constructor và getter. Ví dụ: `String`, `Integer`.

## Câu 58

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
class A {
    void method(Object o) { System.out.println("Object"); }
    void method(String s) { System.out.println("String"); }
}
A a = new A();
a.method(null);
```

- [ ] Object
- [x] String
- [ ] Lỗi biên dịch (ambiguous)
- [ ] NullPointerException

> **Giải thích:** Khi `null` phù hợp nhiều overloaded method, compiler chọn most specific type. `String` cụ thể hơn `Object` nên gọi `method(String)`.

## Câu 59

[TYPE: TRUE_FALSE]

Mệnh đề: "Trong Java, enum có thể implement interface."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Enum có thể implement interface. Mỗi enum constant có thể có implementation riêng. Ví dụ: `enum Operation implements Calculator`.

## Câu 60

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
class Builder {
    private String name;
    private int age;
    
    Builder name(String n) { this.name = n; return this; }
    Builder age(int a) { this.age = a; return this; }
    String build() { return name + ":" + age; }
}
String result = new Builder().name("An").age(25).build();
System.out.println(result);
```

- [x] An:25
- [ ] Lỗi biên dịch
- [ ] null:0
- [ ] Builder@hashcode

> **Giải thích:** Builder pattern: mỗi method trả về `this` cho phép method chaining. `.name("An")` → `.age(25)` → `.build()` trả về "An:25".

## Câu 61

[TYPE: MULTIPLE_CHOICE]

Khi nào cần override `equals()` và `hashCode()` cùng lúc?

- [ ] Luôn luôn
- [x] Khi dùng object làm key trong HashMap hoặc phần tử trong HashSet
- [ ] Khi class có method static
- [ ] Khi class implement Serializable

> **Giải thích:** Contract: nếu `a.equals(b)` → `a.hashCode() == b.hashCode()`. HashMap/HashSet dùng hashCode để tìm bucket, equals để xác nhận. Không override cả hai → sai kết quả.

## Câu 62

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
class Person {
    String name;
    Person(String name) { this.name = name; }
    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person p)) return false;
        return name.equals(p.name);
    }
    public int hashCode() { return name.hashCode(); }
}
Set<Person> set = new HashSet<>();
set.add(new Person("An"));
set.add(new Person("An"));
System.out.println(set.size());
```

- [x] 1
- [ ] 2
- [ ] 0
- [ ] Lỗi biên dịch

> **Giải thích:** Override `equals()` và `hashCode()` dựa trên `name`. Hai Person("An") bằng nhau → HashSet chỉ giữ 1. Nếu không override → size = 2 (khác object).

## Câu 63

[TYPE: TRUE_FALSE]

Mệnh đề: "Anonymous inner class có thể extend một class VÀ implement interface cùng lúc."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Anonymous inner class chỉ có thể extend 1 class HOẶC implement 1 interface, không thể cả hai. Nó được tạo bằng `new ClassName() {}` hoặc `new InterfaceName() {}`.

## Câu 64

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
abstract class Template {
    final void execute() {
        step1();
        step2();
        step3();
    }
    abstract void step1();
    abstract void step2();
    void step3() { System.out.print("Done"); }
}
class MyProcess extends Template {
    void step1() { System.out.print("Init "); }
    void step2() { System.out.print("Run "); }
}
new MyProcess().execute();
```

- [x] Init Run Done
- [ ] Done
- [ ] Lỗi biên dịch
- [ ] Init Run

> **Giải thích:** Template Method pattern: `execute()` là template (final), gọi các step theo thứ tự. `step1()` và `step2()` do subclass implement, `step3()` dùng default.

## Câu 65

[TYPE: MULTIPLE_CHOICE]

Đâu là điểm khác biệt giữa `protected` và `default` (package-private)?

- [ ] protected chỉ truy cập trong cùng class
- [x] protected cho phép truy cập từ subclass ở package khác, default thì không
- [ ] Không có sự khác biệt
- [ ] default cho phép truy cập từ mọi nơi

> **Giải thích:** `protected`: cùng package + subclass (kể cả khác package). `default` (không ghi modifier): chỉ cùng package. protected rộng hơn default.

## Câu 66

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
class Animal {
    void makeSound() { System.out.print("..."); }
}
class Cat extends Animal {
    void makeSound() { System.out.print("Meow"); }
    void purr() { System.out.print(" Purr"); }
}
Animal a = new Cat();
a.makeSound();
// a.purr();  // compile error
((Cat) a).purr();
```

- [x] Meow Purr
- [ ] ... Purr
- [ ] Meow
- [ ] ClassCastException

> **Giải thích:** `a.makeSound()` → dynamic dispatch → "Meow". `a.purr()` lỗi vì `Animal` không có `purr()`. Cast `(Cat) a` → truy cập được `purr()` → " Purr".

## Câu 67

[TYPE: FILL_BLANK]

Trong OOP, khi class con cung cấp implementation cụ thể cho method đã khai báo trong class cha, gọi là `___`.

- [x] Method Overriding
- [ ] Method Overloading
- [ ] Method Hiding
- [ ] Method Chaining

> **Giải thích:** Overriding: subclass cung cấp implementation mới cho method của superclass. Cùng tên, cùng tham số, cùng hoặc rộng hơn access modifier.

## Câu 68

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
class Vehicle {
    String type;
    Vehicle(String type) { this.type = type; }
    public String toString() { return "Vehicle:" + type; }
}
class Car extends Vehicle {
    String model;
    Car(String type, String model) {
        super(type);
        this.model = model;
    }
    public String toString() { return super.toString() + ":" + model; }
}
System.out.println(new Car("Sedan", "Toyota"));
```

- [x] Vehicle:Sedan:Toyota
- [ ] Car@hashcode
- [ ] Sedan:Toyota
- [ ] Vehicle:Sedan

> **Giải thích:** `println` gọi `toString()`. `Car.toString()` gọi `super.toString()` → "Vehicle:Sedan", rồi nối ":Toyota" → "Vehicle:Sedan:Toyota".

## Câu 69

[TYPE: TRUE_FALSE]

Mệnh đề: "Trong Java, abstract method có thể có body (thân hàm)."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Abstract method KHÔNG có body, chỉ có signature kết thúc bằng `;`. Ví dụ: `abstract void draw();`. Subclass phải implement (cung cấp body).

## Câu 70

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
interface Comparable<T> {
    int compareTo(T other);
}
class Student implements Comparable<Student> {
    String name;
    double gpa;
    Student(String name, double gpa) { this.name = name; this.gpa = gpa; }
    public int compareTo(Student other) { return Double.compare(this.gpa, other.gpa); }
}
Student s1 = new Student("An", 3.5);
Student s2 = new Student("Bình", 3.8);
System.out.println(s1.compareTo(s2) < 0 ? "An thấp hơn" : "An cao hơn");
```

- [x] An thấp hơn
- [ ] An cao hơn
- [ ] Lỗi biên dịch
- [ ] 0

> **Giải thích:** `Double.compare(3.5, 3.8)` trả về số âm (3.5 < 3.8). Nên `compareTo() < 0` → true → "An thấp hơn".

## Câu 71

[TYPE: MULTIPLE_CHOICE]

Đâu là Creational Design Pattern?

- [x] Builder, Factory, Singleton, Prototype
- [ ] Observer, Strategy, Template
- [ ] Adapter, Decorator, Proxy
- [ ] MVC, MVVM, MVP

> **Giải thích:** Creational patterns liên quan đến việc tạo object. Builder, Factory, Abstract Factory, Singleton, Prototype là Creational. Observer, Strategy là Behavioral. Adapter, Decorator là Structural.

## Câu 72

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
class A {
    int x;
    A(int x) { this.x = x; }
    public boolean equals(Object o) {
        if (!(o instanceof A a)) return false;
        return this.x == a.x;
    }
}
A a1 = new A(5);
A a2 = new A(5);
A a3 = a1;
System.out.println(a1 == a2);
System.out.println(a1.equals(a2));
System.out.println(a1 == a3);
```

- [ ] false, false, true
- [x] false, true, true
- [ ] true, true, true
- [ ] false, true, false

> **Giải thích:** `a1 == a2` → false (khác object). `a1.equals(a2)` → true (cùng x=5). `a1 == a3` → true (cùng tham chiếu).

## Câu 73

[TYPE: TRUE_FALSE]

Mệnh đề: "Interface field mặc định là public static final."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Field trong interface luôn là `public static final` dù không khai báo. Ví dụ: `int MAX = 100;` trong interface tương đương `public static final int MAX = 100;`.

## Câu 74

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
class Decorator {
    interface Coffee {
        double cost();
        String description();
    }
    
    static class SimpleCoffee implements Coffee {
        public double cost() { return 1.0; }
        public String description() { return "Simple"; }
    }
    
    static class MilkDecorator implements Coffee {
        Coffee coffee;
        MilkDecorator(Coffee c) { this.coffee = c; }
        public double cost() { return coffee.cost() + 0.5; }
        public String description() { return coffee.description() + " + Milk"; }
    }
}
var coffee = new Decorator.MilkDecorator(new Decorator.SimpleCoffee());
System.out.println(coffee.description() + " = $" + coffee.cost());
```

- [x] Simple + Milk = $1.5
- [ ] Milk = $0.5
- [ ] Simple = $1.0
- [ ] Lỗi biên dịch

> **Giải thích:** Decorator pattern: MilkDecorator wrap SimpleCoffee. `cost()` = 1.0 + 0.5 = 1.5. `description()` = "Simple" + " + Milk".

## Câu 75

[TYPE: MULTIPLE_CHOICE]

Khi override `equals()`, điều gì KHÔNG cần đảm bảo?

- [ ] Reflexive: a.equals(a) == true
- [ ] Symmetric: a.equals(b) == b.equals(a)
- [ ] Transitive: nếu a.equals(b) && b.equals(c) → a.equals(c)
- [x] a.equals(b) → a.hashCode() != b.hashCode()

> **Giải thích:** Contract: nếu `a.equals(b)` → `hashCode()` phải bằng nhau (KHÔNG phải khác nhau). Ngược lại, hashCode bằng không đảm bảo equals (hash collision).

## Câu 76

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
class Strategy {
    interface SortStrategy { void sort(int[] arr); }
    
    static class BubbleSort implements SortStrategy {
        public void sort(int[] arr) { System.out.println("Bubble sort"); }
    }
    static class QuickSort implements SortStrategy {
        public void sort(int[] arr) { System.out.println("Quick sort"); }
    }
}
Strategy.SortStrategy strategy = new Strategy.QuickSort();
strategy.sort(new int[]{3, 1, 2});
strategy = new Strategy.BubbleSort();
strategy.sort(new int[]{3, 1, 2});
```

- [ ] Bubble sort và Quick sort
- [x] Quick sort và Bubble sort
- [ ] Quick sort và Quick sort
- [ ] Lỗi biên dịch

> **Giải thích:** Strategy pattern: thay đổi thuật toán tại runtime. Lần 1 dùng QuickSort → "Quick sort". Gán lại BubbleSort → "Bubble sort". Interface reference cho phép swap.

## Câu 77

[TYPE: FILL_BLANK]

Quan hệ "HAS-A" giữa các class trong OOP được gọi là `___`.

- [ ] Inheritance
- [x] Composition
- [ ] Polymorphism
- [ ] Abstraction

> **Giải thích:** Composition: "has-a". Car HAS-A Engine. Đối tượng chứa đối tượng khác như thành phần. Linh hoạt hơn Inheritance ("is-a").

## Câu 78

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
class Outer {
    static int x = 5;
    static class StaticNested {
        void show() { System.out.println(x); }
    }
}
Outer.StaticNested nested = new Outer.StaticNested();
nested.show();
```

- [x] 5
- [ ] Lỗi biên dịch
- [ ] 0
- [ ] NullPointerException

> **Giải thích:** Static nested class có thể truy cập static members của outer class. Tạo instance không cần outer instance: `new Outer.StaticNested()`.

## Câu 79

[TYPE: TRUE_FALSE]

Mệnh đề: "Trong Java, một class abstract có thể kế thừa từ class concrete (non-abstract)."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Abstract class có thể extends concrete class. Ví dụ: `abstract class MyList extends ArrayList {}`. Không có ràng buộc parent phải abstract.

## Câu 80

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
class Base {
    void display() throws Exception {
        System.out.println("Base");
    }
}
class Derived extends Base {
    void display() throws RuntimeException {
        System.out.println("Derived");
    }
}
Base b = new Derived();
b.display();
```

- [ ] Base
- [x] Derived (nhưng cần try-catch vì Base khai báo throws Exception)
- [ ] Lỗi biên dịch
- [ ] RuntimeException

> **Giải thích:** Override method có thể throw narrower exception. RuntimeException là unchecked nhưng cũng là subclass của Exception. Caller phải handle Exception vì declared type là Base.

## Câu 81

[TYPE: MULTIPLE_CHOICE]

Đâu là ví dụ về Abstraction?

- [ ] Tất cả fields đều private
- [x] Interface chỉ khai báo "what" không khai báo "how"
- [ ] Class có nhiều method
- [ ] Dùng static import

> **Giải thích:** Abstraction: ẩn chi tiết phức tạp, chỉ show interface cần thiết. Ví dụ: `List.add()` → user biết "thêm phần tử" mà không cần biết internal implementation.

## Câu 82

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
class Payment {
    void pay(double amount) {
        System.out.println("Pay: " + amount);
    }
}
class CreditCardPayment extends Payment {
    void pay(double amount) {
        System.out.println("Credit card: " + amount);
    }
}
class PayPalPayment extends Payment {
    void pay(double amount) {
        System.out.println("PayPal: " + amount);
    }
}
Payment[] payments = { new CreditCardPayment(), new PayPalPayment() };
for (Payment p : payments) p.pay(100);
```

- [ ] Pay: 100 và Pay: 100
- [x] Credit card: 100.0 và PayPal: 100.0
- [ ] Credit card: 100 và PayPal: 100
- [ ] Lỗi biên dịch

> **Giải thích:** Polymorphism: mảng Payment chứa các subclass. `p.pay(100)` gọi method của runtime type. double → hiển thị 100.0.

## Câu 83

[TYPE: TRUE_FALSE]

Mệnh đề: "Java cho phép override static method bằng instance method."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Không thể override static method bằng instance method hoặc ngược lại. Nếu cố làm → lỗi biên dịch. Static và instance method có binding khác nhau.

## Câu 84

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
interface Observer {
    void update(String event);
}
class EventBus {
    List<Observer> observers = new ArrayList<>();
    void subscribe(Observer o) { observers.add(o); }
    void publish(String event) {
        for (Observer o : observers) o.update(event);
    }
}
EventBus bus = new EventBus();
bus.subscribe(e -> System.out.print("A:" + e + " "));
bus.subscribe(e -> System.out.print("B:" + e));
bus.publish("click");
```

- [x] A:click B:click
- [ ] B:click A:click
- [ ] A:click
- [ ] Lỗi biên dịch

> **Giải thích:** Observer pattern: EventBus notify tất cả observers. Thứ tự subscribe → thứ tự notify. Observer A in trước, Observer B in sau.

## Câu 85

[TYPE: MULTIPLE_CHOICE]

Đâu là đặc điểm của method `final`?

- [x] Không thể bị override bởi subclass
- [ ] Không thể bị overload
- [ ] Không thể có tham số
- [ ] Chỉ có thể là static

> **Giải thích:** `final` method không thể bị override (ghi đè) trong subclass. Nhưng vẫn có thể bị overload (nạp chồng) và có tham số bình thường.

## Câu 86

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
class Node {
    int value;
    Node next;
    Node(int value) { this.value = value; }
    Node add(int v) { 
        this.next = new Node(v); 
        return this.next; 
    }
}
Node head = new Node(1);
head.add(2).add(3);
System.out.print(head.value + " ");
System.out.print(head.next.value + " ");
System.out.print(head.next.next.value);
```

- [x] 1 2 3
- [ ] 1 3 2
- [ ] NullPointerException
- [ ] 3 2 1

> **Giải thích:** Method chaining: `head.add(2)` trả về node(2), `.add(3)` thêm vào node(2). Linked list: 1 → 2 → 3.

## Câu 87

[TYPE: TRUE_FALSE]

Mệnh đề: "Trong Java, constructor chaining cho phép constructor gọi constructor khác trong cùng class bằng `this()`."

- [x] Đúng
- [ ] Sai

> **Giải thích:** `this()` gọi constructor khác trong cùng class. Phải là dòng đầu tiên trong constructor. Ví dụ: `Person() { this("Unknown", 0); }`.

## Câu 88

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
class Shape {
    void draw() { System.out.println("Shape"); }
}
class Circle extends Shape {
    void draw() { System.out.println("Circle"); }
}
class Square extends Shape {
    void draw() { System.out.println("Square"); }
}
List<Shape> shapes = List.of(new Circle(), new Square(), new Circle());
shapes.forEach(Shape::draw);
```

- [x] Circle, Square, Circle
- [ ] Shape, Shape, Shape
- [ ] Circle, Circle, Square
- [ ] Lỗi biên dịch

> **Giải thích:** `Shape::draw` là method reference. forEach gọi `draw()` trên mỗi element. Polymorphism → gọi method của runtime type lần lượt.

## Câu 89

[TYPE: MULTIPLE_CHOICE]

Tại sao nên dùng interface thay vì concrete class khi khai báo kiểu biến?

- [x] Dễ thay đổi implementation, giảm coupling
- [ ] Chạy nhanh hơn
- [ ] Tiết kiệm bộ nhớ
- [ ] Bắt buộc theo Java specification

> **Giải thích:** `List<String> list = new ArrayList<>()` thay vì `ArrayList<String> list = ...`. Dễ đổi sang LinkedList, code khác chỉ phụ thuộc vào List interface.

## Câu 90

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
class Wrapper {
    private int value;
    Wrapper(int v) { this.value = v; }
    Wrapper plus(Wrapper other) {
        return new Wrapper(this.value + other.value);
    }
    int getValue() { return value; }
}
Wrapper a = new Wrapper(3);
Wrapper b = new Wrapper(7);
System.out.println(a.plus(b).getValue());
System.out.println(a.getValue());
```

- [x] 10 và 3
- [ ] 10 và 10
- [ ] 7 và 3
- [ ] Lỗi biên dịch

> **Giải thích:** `plus()` trả về Wrapper MỚI (immutable style). `a.plus(b)` → new Wrapper(10) → getValue() = 10. `a` không đổi → getValue() = 3.

## Câu 91

[TYPE: FILL_BLANK]

Design pattern nào đảm bảo một class chỉ có duy nhất một instance? Đó là `___` pattern.

- [ ] Factory
- [x] Singleton
- [ ] Builder
- [ ] Prototype

> **Giải thích:** Singleton: private constructor + static getInstance() đảm bảo chỉ 1 instance. Dùng cho: database connection, configuration, logging.

## Câu 92

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
interface Transformer<T, R> {
    R transform(T input);
}
Transformer<String, Integer> toLength = String::length;
System.out.println(toLength.transform("Hello"));
System.out.println(toLength.transform("OOP Design"));
```

- [x] 5 và 10
- [ ] Hello và OOP Design
- [ ] Lỗi biên dịch
- [ ] 0 và 0

> **Giải thích:** Generic functional interface. `String::length` là method reference → `"Hello".length()` = 5, `"OOP Design".length()` = 10.

## Câu 93

[TYPE: TRUE_FALSE]

Mệnh đề: "Aggregation và Composition đều là dạng association, nhưng Composition có lifecycle dependency."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Aggregation: "has-a" nhẹ (Department has Students → students tồn tại độc lập). Composition: "has-a" mạnh (House has Rooms → rooms không tồn tại khi house bị destroy).

## Câu 94

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
class A {
    { System.out.print("A-init "); }
    static { System.out.print("A-static "); }
    A() { System.out.print("A-constructor "); }
}
class B extends A {
    { System.out.print("B-init "); }
    static { System.out.print("B-static "); }
    B() { System.out.print("B-constructor "); }
}
new B();
```

- [x] A-static B-static A-init A-constructor B-init B-constructor
- [ ] A-constructor B-constructor A-init B-init
- [ ] B-static A-static B-init A-init
- [ ] A-static A-init A-constructor B-static B-init B-constructor

> **Giải thích:** Thứ tự: static blocks (cha → con) → instance init blocks + constructor (cha → con). Static chạy 1 lần khi class load.

## Câu 95

[TYPE: MULTIPLE_CHOICE]

Đâu là Behavioral Design Pattern?

- [ ] Factory, Builder, Prototype
- [ ] Adapter, Decorator, Facade
- [x] Observer, Strategy, Template Method
- [ ] Singleton, Bridge, Composite

> **Giải thích:** Behavioral patterns: Observer, Strategy, Template Method, Command, Iterator, State, Visitor, Chain of Responsibility, Mediator, Memento.

## Câu 96

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
class Product {
    String name;
    double price;
    Product(String name, double price) { this.name = name; this.price = price; }
}
List<Product> products = List.of(
    new Product("A", 30), new Product("B", 10), new Product("C", 20)
);
products.stream()
    .sorted((p1, p2) -> Double.compare(p1.price, p2.price))
    .forEach(p -> System.out.print(p.name + " "));
```

- [x] B C A
- [ ] A B C
- [ ] A C B
- [ ] C B A

> **Giải thích:** Sort theo price tăng dần: B(10) < C(20) < A(30). Lambda implement Comparator → dùng trong sorted(). In: B C A.

## Câu 97

[TYPE: TRUE_FALSE]

Mệnh đề: "Diamond problem xảy ra khi một class kế thừa hai class có cùng method."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Diamond problem: class D extends B, C; B và C cùng có method m(). D gọi m() → ambiguous. Java tránh bằng đơn kế thừa class, nhưng có thể xảy ra với default methods trong interface.

## Câu 98

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
record Student(String name, int age) implements Comparable<Student> {
    public int compareTo(Student other) {
        return Integer.compare(this.age, other.age);
    }
}
List<Student> list = new ArrayList<>(List.of(
    new Student("An", 22), new Student("Bình", 20), new Student("Cường", 21)
));
Collections.sort(list);
System.out.println(list.get(0).name());
```

- [ ] An
- [x] Bình
- [ ] Cường
- [ ] Lỗi biên dịch

> **Giải thích:** Sort theo age tăng dần. Bình(20) < Cường(21) < An(22). `list.get(0)` → phần tử nhỏ nhất → Bình.

## Câu 99

[TYPE: MULTIPLE_CHOICE]

Tại sao "Favor composition over inheritance" là best practice?

- [ ] Composition nhanh hơn
- [ ] Inheritance đã deprecated
- [x] Composition linh hoạt hơn, giảm coupling, dễ test và maintain
- [ ] Composition bắt buộc trong Java 17+

> **Giải thích:** Composition cho phép thay đổi behavior tại runtime (Strategy pattern), dễ mock trong unit test, giảm phụ thuộc giữa các class.

## Câu 100

[TYPE: SELECT_RESULT]

Cho đoạn code:

```java
interface Repository<T> {
    void save(T entity);
    T findById(int id);
}
class UserRepo implements Repository<String> {
    private Map<Integer, String> db = new HashMap<>();
    public void save(String entity) { db.put(db.size() + 1, entity); }
    public String findById(int id) { return db.getOrDefault(id, "Not found"); }
}
UserRepo repo = new UserRepo();
repo.save("An");
repo.save("Bình");
System.out.println(repo.findById(1));
System.out.println(repo.findById(3));
```

- [x] An và Not found
- [ ] Bình và Not found
- [ ] An và null
- [ ] An và Bình

> **Giải thích:** `save("An")` → key=1, `save("Bình")` → key=2. `findById(1)` → "An". `findById(3)` → key 3 không có → "Not found" (getOrDefault).
