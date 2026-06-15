# Java OOP - Lập Trình Hướng Đối Tượng

## 1. Giới thiệu về Lập trình Hướng đối tượng (OOP)

### 1.1. Lập trình hướng đối tượng là gì?

**Lập trình hướng đối tượng (Object-Oriented Programming - OOP)** là một mô hình lập trình dựa trên khái niệm **"đối tượng"** — các thực thể chứa **dữ liệu** (thuộc tính/fields) và **hành vi** (phương thức/methods).

Thay vì viết chương trình như một chuỗi lệnh tuần tự (procedural), OOP tổ chức code thành các đối tượng tương tác với nhau, mô phỏng thế giới thực.

**So sánh Procedural vs OOP:**

| Tiêu chí | Procedural | OOP |
|----------|-----------|-----|
| Đơn vị cơ bản | Hàm (function) | Đối tượng (object) |
| Dữ liệu | Tách biệt, truyền qua tham số | Đóng gói trong object |
| Tái sử dụng | Copy-paste hoặc gọi hàm | Kế thừa, composition |
| Mở rộng | Sửa code hiện có | Thêm class mới, override |
| Ví dụ ngôn ngữ | C, Pascal | Java, C++, Python, C# |

**Ví dụ minh họa:**
```java
// Procedural style — dữ liệu và hàm tách biệt
String studentName = "An";
int studentAge = 20;
double studentGpa = 3.5;

void printStudent(String name, int age, double gpa) {
    System.out.println(name + " - " + age + " tuổi - GPA: " + gpa);
}

// OOP style — dữ liệu và hành vi gói chung trong object
class Student {
    String name;
    int age;
    double gpa;

    void print() {
        System.out.println(name + " - " + age + " tuổi - GPA: " + gpa);
    }
}

Student student = new Student();
student.name = "An";
student.age = 20;
student.gpa = 3.5;
student.print(); // "An - 20 tuổi - GPA: 3.5"
```

### 1.2. 4 tính chất cơ bản của OOP

Java OOP xây dựng trên **4 trụ cột** (Four Pillars):

| Tính chất | Ý nghĩa | Từ khóa liên quan |
|-----------|---------|-------------------|
| **Encapsulation** (Đóng gói) | Ẩn dữ liệu, chỉ expose qua phương thức | `private`, getters/setters |
| **Inheritance** (Kế thừa) | Class con thừa hưởng thuộc tính/hành vi từ class cha | `extends`, `super` |
| **Polymorphism** (Đa hình) | Cùng phương thức, hành vi khác nhau tùy đối tượng | `@Override`, overloading |
| **Abstraction** (Trừu tượng) | Ẩn chi tiết triển khai, chỉ hiện giao diện cần thiết | `abstract`, `interface` |

```
                    ┌─────────────────┐
                    │   ABSTRACTION   │  ← Ẩn chi tiết, lộ interface
                    └────────┬────────┘
                             │
              ┌──────────────┼──────────────┐
              │              │              │
     ┌────────┴───────┐ ┌───┴────────┐ ┌──┴──────────────┐
     │ ENCAPSULATION  │ │INHERITANCE │ │  POLYMORPHISM   │
     │ Đóng gói data  │ │ Tái sử dụng│ │ Đa hình hành vi │
     └────────────────┘ └────────────┘ └─────────────────┘
```

> **Phỏng vấn thường hỏi:** Hãy giải thích 4 tính chất OOP và cho ví dụ thực tế. — Trả lời bằng cách liên hệ thế giới thực: Xe hơi **đóng gói** động cơ bên trong, người lái chỉ dùng vô lăng (interface). Xe điện **kế thừa** từ xe hơi nhưng có thêm pin. Cùng phương thức `khởiĐộng()` nhưng xe xăng và xe điện hoạt động **đa hình** khác nhau. **Trừu tượng** — bạn chỉ cần biết nhấn ga để tăng tốc, không cần biết cơ chế phun xăng.

---

## 2. Class và Object

### 2.1. Class (Lớp)

**Class** là **bản thiết kế** (blueprint) mô tả cấu trúc và hành vi chung cho một nhóm đối tượng. Class định nghĩa:
- **Fields** (thuộc tính): dữ liệu mà đối tượng lưu trữ
- **Methods** (phương thức): hành vi mà đối tượng có thể thực hiện
- **Constructors**: cách khởi tạo đối tượng

```java
// Định nghĩa class — bản thiết kế
public class Dog {
    // Fields (thuộc tính)
    String name;
    String breed;
    int age;

    // Constructor — cách tạo đối tượng
    public Dog(String name, String breed, int age) {
        this.name = name;
        this.breed = breed;
        this.age = age;
    }

    // Method (hành vi)
    public void bark() {
        System.out.println(name + " says: Gâu gâu!");
    }

    public String info() {
        return name + " (" + breed + ") - " + age + " tuổi";
    }
}
```

### 2.2. Object (Đối tượng)

**Object** là **thể hiện cụ thể** (instance) của một class. Mỗi object có bộ dữ liệu riêng nhưng dùng chung bản thiết kế.

```java
// Tạo object từ class Dog
Dog dog1 = new Dog("Buddy", "Golden Retriever", 3);
Dog dog2 = new Dog("Max", "Poodle", 5);

dog1.bark();    // "Buddy says: Gâu gâu!"
dog2.bark();    // "Max says: Gâu gâu!"

System.out.println(dog1.info()); // "Buddy (Golden Retriever) - 3 tuổi"
System.out.println(dog2.info()); // "Max (Poodle) - 5 tuổi"

// dog1 và dog2 là 2 object khác nhau (khác reference)
System.out.println(dog1 == dog2); // false
```

**Bộ nhớ khi tạo Object:**
```
Stack                    Heap
┌──────────┐        ┌──────────────────┐
│ dog1 ─────────────▶│ Dog Object       │
│          │        │ name = "Buddy"   │
│          │        │ breed = "Golden" │
│          │        │ age = 3          │
├──────────┤        └──────────────────┘
│ dog2 ─────────────▶┌──────────────────┐
│          │        │ Dog Object       │
│          │        │ name = "Max"     │
│          │        │ breed = "Poodle" │
│          │        │ age = 5          │
└──────────┘        └──────────────────┘
```

> **Quan trọng:** Biến `dog1`, `dog2` trên Stack chỉ chứa **reference** (địa chỉ) trỏ đến object thực sự trên Heap. Khi gán `Dog dog3 = dog1;` → `dog3` và `dog1` cùng trỏ đến 1 object.

---

## 3. Các tính chất của OOP

### 3.1. Encapsulation (Đóng gói)

**Đóng gói** là cơ chế **ẩn dữ liệu nội bộ** của object, chỉ cho phép truy cập thông qua các phương thức công khai (getters/setters). Điều này bảo vệ dữ liệu khỏi bị thay đổi trực tiếp từ bên ngoài.

```java
// ❌ Không đóng gói — ai cũng sửa được trực tiếp
class BankAccount {
    public double balance;  // public → nguy hiểm!
}

BankAccount acc = new BankAccount();
acc.balance = -1000; // Ai cũng set được số âm → lỗi logic

// ✅ Đóng gói — kiểm soát truy cập qua method
class BankAccount {
    private double balance; // private → chỉ class này truy cập

    public BankAccount(double initialBalance) {
        if (initialBalance < 0) throw new IllegalArgumentException("Số dư không thể âm");
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Số tiền phải > 0");
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Số tiền phải > 0");
        if (amount > balance) throw new IllegalArgumentException("Không đủ số dư");
        balance -= amount;
    }
}

BankAccount acc = new BankAccount(1000);
// acc.balance = -1000;   // ❌ Compile error — private
acc.deposit(500);          // ✅ balance = 1500
acc.withdraw(200);         // ✅ balance = 1300
acc.withdraw(2000);        // ❌ Exception: Không đủ số dư
```

### 3.2. Inheritance (Kế thừa)

**Kế thừa** cho phép class con (**subclass**) thừa hưởng các thuộc tính và phương thức từ class cha (**superclass**), giúp tái sử dụng code và tạo hệ thống phân cấp.

```java
// Class cha (superclass)
class Animal {
    String name;
    int age;

    Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    void eat() {
        System.out.println(name + " đang ăn...");
    }

    void sleep() {
        System.out.println(name + " đang ngủ...");
    }
}

// Class con (subclass) kế thừa từ Animal
class Cat extends Animal {
    String color;

    Cat(String name, int age, String color) {
        super(name, age); // Gọi constructor class cha
        this.color = color;
    }

    void meow() {
        System.out.println(name + " says: Meo meo!");
    }
}

Cat cat = new Cat("Mimi", 2, "Trắng");
cat.eat();   // Kế thừa từ Animal: "Mimi đang ăn..."
cat.sleep(); // Kế thừa từ Animal: "Mimi đang ngủ..."
cat.meow();  // Riêng của Cat: "Mimi says: Meo meo!"
```

### 3.3. Polymorphism (Đa hình)

**Đa hình** cho phép cùng một phương thức có **hành vi khác nhau** tùy thuộc vào đối tượng thực thi. Có 2 dạng:

```java
// --- Compile-time Polymorphism (Overloading) ---
class Calculator {
    int add(int a, int b) { return a + b; }
    double add(double a, double b) { return a + b; }
    int add(int a, int b, int c) { return a + b + c; }
}

Calculator calc = new Calculator();
calc.add(1, 2);       // gọi add(int, int) → 3
calc.add(1.5, 2.5);   // gọi add(double, double) → 4.0
calc.add(1, 2, 3);    // gọi add(int, int, int) → 6

// --- Runtime Polymorphism (Overriding) ---
class Shape {
    double area() {
        return 0;
    }
}

class Circle extends Shape {
    double radius;
    Circle(double radius) { this.radius = radius; }

    @Override
    double area() { return Math.PI * radius * radius; }
}

class Rectangle extends Shape {
    double width, height;
    Rectangle(double w, double h) { width = w; height = h; }

    @Override
    double area() { return width * height; }
}

// Cùng kiểu Shape, nhưng hành vi area() khác nhau
Shape s1 = new Circle(5);
Shape s2 = new Rectangle(4, 6);
System.out.println(s1.area()); // 78.54 — Circle.area()
System.out.println(s2.area()); // 24.0 — Rectangle.area()
```

### 3.4. Abstraction (Trừu tượng)

**Trừu tượng** là quá trình **ẩn chi tiết triển khai**, chỉ hiển thị giao diện (interface) cần thiết cho người dùng.

```java
// Abstract class — định nghĩa "hợp đồng" cho các payment method
abstract class Payment {
    protected double amount;

    Payment(double amount) {
        this.amount = amount;
    }

    // Abstract method — bắt buộc subclass phải implement
    abstract boolean processPayment();

    // Concrete method — dùng chung cho tất cả
    void printReceipt() {
        System.out.println("Thanh toán: " + amount + " VND");
    }
}

class CreditCardPayment extends Payment {
    String cardNumber;

    CreditCardPayment(double amount, String cardNumber) {
        super(amount);
        this.cardNumber = cardNumber;
    }

    @Override
    boolean processPayment() {
        System.out.println("Xử lý thẻ tín dụng: " + cardNumber);
        return true; // giả lập thành công
    }
}

class MomoPayment extends Payment {
    String phoneNumber;

    MomoPayment(double amount, String phoneNumber) {
        super(amount);
        this.phoneNumber = phoneNumber;
    }

    @Override
    boolean processPayment() {
        System.out.println("Xử lý MoMo: " + phoneNumber);
        return true;
    }
}

// Sử dụng — không cần biết chi tiết bên trong
Payment payment = new MomoPayment(50000, "0901234567");
payment.processPayment(); // "Xử lý MoMo: 0901234567"
payment.printReceipt();   // "Thanh toán: 50000.0 VND"
```

---

## 4. Class và Object chi tiết

### 4.1. Constructor

**Constructor** là phương thức đặc biệt được gọi khi tạo object bằng `new`. Constructor có **cùng tên với class** và **không có return type**.

```java
class Student {
    String name;
    int age;
    double gpa;

    // 1. Default Constructor — không tham số
    Student() {
        this.name = "Unknown";
        this.age = 0;
        this.gpa = 0.0;
    }

    // 2. Parameterized Constructor — có tham số
    Student(String name, int age) {
        this.name = name;
        this.age = age;
        this.gpa = 0.0;
    }

    // 3. Full Constructor
    Student(String name, int age, double gpa) {
        this.name = name;
        this.age = age;
        this.gpa = gpa;
    }

    // 4. Copy Constructor — sao chép từ object khác
    Student(Student other) {
        this.name = other.name;
        this.age = other.age;
        this.gpa = other.gpa;
    }
}

// Sử dụng các constructor
Student s1 = new Student();                    // Default: Unknown, 0, 0.0
Student s2 = new Student("An", 20);            // Parameterized
Student s3 = new Student("Bình", 21, 3.8);     // Full
Student s4 = new Student(s3);                   // Copy: Bình, 21, 3.8
```

**Constructor Chaining** — Constructor gọi constructor khác bằng `this()`:
```java
class Employee {
    String name;
    String department;
    double salary;

    Employee() {
        this("Unknown", "General", 0); // gọi constructor 3 tham số
    }

    Employee(String name) {
        this(name, "General", 0);
    }

    Employee(String name, String department, double salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
    }
}
```

> **Lưu ý:** `this()` phải là dòng lệnh **đầu tiên** trong constructor. Nếu class không định nghĩa constructor nào, Java tự tạo **default constructor** (không tham số, body rỗng). Nhưng nếu bạn đã định nghĩa bất kỳ constructor nào, default constructor **không tự động sinh ra**.

### 4.2. Access Modifiers

**Access Modifiers** kiểm soát phạm vi truy cập của class, field, method, constructor.

| Modifier | Cùng class | Cùng package | Subclass (khác package) | Khác package |
|----------|-----------|-------------|------------------------|-------------|
| `public` | ✅ | ✅ | ✅ | ✅ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| (default/package-private) | ✅ | ✅ | ❌ | ❌ |
| `private` | ✅ | ❌ | ❌ | ❌ |

```java
package com.example.model;

public class Person {
    public String name;         // Truy cập từ mọi nơi
    protected int age;          // Cùng package + subclass
    String address;             // default — chỉ cùng package
    private String ssn;         // Chỉ trong class Person

    public Person(String name, int age, String address, String ssn) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.ssn = ssn;
    }
}

// Cùng package
package com.example.model;
class PersonHelper {
    void test() {
        Person p = new Person("An", 20, "HN", "123");
        System.out.println(p.name);     // ✅ public
        System.out.println(p.age);      // ✅ protected — cùng package
        System.out.println(p.address);  // ✅ default — cùng package
        // System.out.println(p.ssn);   // ❌ private
    }
}

// Khác package, có kế thừa
package com.example.other;
class Student extends Person {
    void test() {
        System.out.println(this.name);     // ✅ public
        System.out.println(this.age);      // ✅ protected — subclass
        // System.out.println(this.address); // ❌ default — khác package
        // System.out.println(this.ssn);     // ❌ private
    }
}
```

> **Best Practice:** Mặc định dùng `private` cho fields, `public` cho methods cần expose. Dùng `protected` cho những gì subclass cần truy cập. Tránh dùng `public` fields — thay bằng getter/setter.

### 4.3. Getters và Setters

**Getters** và **Setters** là các phương thức công khai cho phép đọc/ghi giá trị private fields một cách kiểm soát.

```java
class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        setName(name);       // Dùng setter để validate ngay trong constructor
        setPrice(price);
        setQuantity(quantity);
    }

    // --- Getters ---
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    // Getter tính toán (derived/computed property)
    public double getTotalValue() {
        return price * quantity;
    }

    // --- Setters với validation ---
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Tên sản phẩm không được rỗng");
        }
        this.name = name.trim();
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Giá phải >= 0");
        }
        this.price = price;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Số lượng phải >= 0");
        }
        this.quantity = quantity;
    }
}

Product p = new Product("iPhone 15", 25_000_000, 10);
System.out.println(p.getTotalValue()); // 250,000,000

p.setPrice(-100); // ❌ IllegalArgumentException: Giá phải >= 0
```

### 4.4. toString(), equals(), hashCode()

Mọi class Java đều kế thừa từ `Object`, nên có 3 method quan trọng cần override:

```java
import java.util.Objects;

class Student {
    private String id;
    private String name;
    private double gpa;

    Student(String id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    // 1. toString() — biểu diễn chuỗi của object
    @Override
    public String toString() {
        return "Student{id='%s', name='%s', gpa=%.2f}".formatted(id, name, gpa);
    }

    // 2. equals() — so sánh giá trị (không phải reference)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;                    // Cùng reference
        if (obj == null || getClass() != obj.getClass()) return false; // Khác kiểu
        Student student = (Student) obj;
        return Objects.equals(id, student.id);           // So sánh theo id
    }

    // 3. hashCode() — PHẢI override cùng equals()
    // Quy tắc: nếu a.equals(b) == true thì a.hashCode() == b.hashCode()
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

Student s1 = new Student("SV001", "An", 3.5);
Student s2 = new Student("SV001", "An", 3.5);
Student s3 = new Student("SV002", "Bình", 3.8);

System.out.println(s1);                   // "Student{id='SV001', name='An', gpa=3.50}"
System.out.println(s1.equals(s2));        // true  — cùng id
System.out.println(s1.equals(s3));        // false — khác id
System.out.println(s1 == s2);            // false — khác reference trên Heap

// hashCode quan trọng cho HashMap, HashSet
Set<Student> students = new HashSet<>();
students.add(s1);
students.add(s2); // Không thêm vì equals + hashCode giống s1
System.out.println(students.size()); // 1
```

> **Quy tắc bắt buộc:** Khi override `equals()` → **luôn override `hashCode()`**. Nếu không, HashMap/HashSet sẽ hoạt động sai — 2 object `equals()` nhưng khác `hashCode()` sẽ nằm ở 2 bucket khác nhau.

---

## 5. Kế thừa (Inheritance)

### 5.1. extends keyword

Từ khóa `extends` cho phép một class kế thừa từ class khác:

```java
class Vehicle {
    String brand;
    int year;
    double speed;

    Vehicle(String brand, int year) {
        this.brand = brand;
        this.year = year;
        this.speed = 0;
    }

    void accelerate(double amount) {
        speed += amount;
        System.out.println(brand + " tăng tốc lên " + speed + " km/h");
    }

    void brake() {
        speed = Math.max(0, speed - 20);
        System.out.println(brand + " giảm tốc còn " + speed + " km/h");
    }

    String info() {
        return brand + " (" + year + ")";
    }
}

class Car extends Vehicle {
    int seats;

    Car(String brand, int year, int seats) {
        super(brand, year);  // Gọi constructor Vehicle
        this.seats = seats;
    }

    void honk() {
        System.out.println(brand + ": Bíp bíp!");
    }
}

class ElectricCar extends Car {
    int batteryCapacity; // kWh

    ElectricCar(String brand, int year, int seats, int batteryCapacity) {
        super(brand, year, seats);
        this.batteryCapacity = batteryCapacity;
    }

    void charge() {
        System.out.println(brand + " đang sạc pin " + batteryCapacity + " kWh...");
    }
}

// Hệ thống phân cấp: Vehicle → Car → ElectricCar
ElectricCar tesla = new ElectricCar("Tesla Model 3", 2024, 5, 75);
tesla.accelerate(100);  // Kế thừa từ Vehicle
tesla.honk();           // Kế thừa từ Car
tesla.charge();         // Riêng của ElectricCar
System.out.println(tesla.info()); // "Tesla Model 3 (2024)"
```

### 5.2. super keyword

`super` dùng để tham chiếu đến class cha:

```java
class Animal {
    String name;
    String sound;

    Animal(String name, String sound) {
        this.name = name;
        this.sound = sound;
    }

    void makeSound() {
        System.out.println(name + ": " + sound);
    }

    String describe() {
        return "Tôi là " + name;
    }
}

class Dog extends Animal {
    String breed;

    Dog(String name, String breed) {
        super(name, "Gâu gâu");   // 1. super() — gọi constructor cha
        this.breed = breed;
    }

    @Override
    void makeSound() {
        super.makeSound();          // 2. super.method() — gọi method cha
        System.out.println("*vẫy đuôi mạnh*");
    }

    @Override
    String describe() {
        return super.describe() + ", giống " + breed; // 3. Kết hợp với cha
    }
}

Dog dog = new Dog("Buddy", "Golden Retriever");
dog.makeSound();
// Output:
// Buddy: Gâu gâu
// *vẫy đuôi mạnh*

System.out.println(dog.describe());
// "Tôi là Buddy, giống Golden Retriever"
```

> **Lưu ý:** `super()` phải là lệnh **đầu tiên** trong constructor. Nếu không gọi `super()` tường minh, Java tự thêm `super()` (constructor không tham số) — nếu class cha không có constructor không tham số → **compile error**.

### 5.3. Single Inheritance

Java chỉ hỗ trợ **đơn kế thừa** (single inheritance) — mỗi class chỉ extends được 1 class.

```java
// ✅ Single inheritance — cho phép
class A { }
class B extends A { }
class C extends B { }  // Chuỗi kế thừa: C → B → A → Object

// ❌ Multiple inheritance — KHÔNG cho phép
// class D extends A, B { }  // Compile error!

// ✅ Giải pháp: dùng Interface cho "đa kế thừa"
interface Flyable {
    void fly();
}

interface Swimmable {
    void swim();
}

class Duck extends Animal implements Flyable, Swimmable {
    Duck(String name) {
        super(name, "Quạc quạc");
    }

    @Override
    public void fly() {
        System.out.println(name + " đang bay...");
    }

    @Override
    public void swim() {
        System.out.println(name + " đang bơi...");
    }
}

Duck duck = new Duck("Donald");
duck.makeSound(); // "Donald: Quạc quạc"
duck.fly();       // "Donald đang bay..."
duck.swim();      // "Donald đang bơi..."
```

> **Tại sao Java không hỗ trợ đa kế thừa class?** — Tránh **Diamond Problem**: Nếu class D extends cả B và C, mà cả B, C đều override method từ A → D gọi method nào? Java giải quyết bằng Interface (cho phép implement nhiều interface).

### 5.4. Method Overriding

**Method Overriding** là khi subclass cung cấp triển khai mới cho method đã có ở superclass.

```java
class Logger {
    void log(String message) {
        System.out.println("[LOG] " + message);
    }
}

class TimestampLogger extends Logger {
    @Override  // Annotation — compiler kiểm tra method cha tồn tại
    void log(String message) {
        String timestamp = java.time.LocalDateTime.now().toString();
        System.out.println("[" + timestamp + "] " + message);
    }
}

class FileLogger extends Logger {
    @Override
    void log(String message) {
        // Ghi ra file thay vì console
        System.out.println("[FILE] " + message);
        // Giả lập: writeToFile(message);
    }
}
```

**Quy tắc Override:**

| Quy tắc | Giải thích |
|---------|-----------|
| Cùng tên, tham số | Method signature phải giống hệt class cha |
| Return type | Giống hoặc là **covariant** (subtype) của return type cha |
| Access modifier | Bằng hoặc **rộng hơn** cha (cha `protected` → con `protected` hoặc `public`) |
| Exception | Không throw exception **rộng hơn** cha (checked exception) |
| `static` method | Không override được — chỉ **hide** (method resolution theo class, không theo object) |
| `final` method | Không override được |
| `private` method | Không override được (không thấy từ subclass) |

```java
class Parent {
    protected Number calculate() {
        return 42;
    }
}

class Child extends Parent {
    @Override
    public Integer calculate() {  // ✅ Covariant return (Integer extends Number)
        return 100;               // ✅ public rộng hơn protected
    }
}
```

---

## 6. Đa hình (Polymorphism)

### 6.1. Compile-time Polymorphism (Overloading)

**Method Overloading** — cùng tên method nhưng khác **danh sách tham số** (số lượng, kiểu, hoặc thứ tự). Compiler quyết định gọi method nào tại **compile time**.

```java
class StringUtils {

    // Overload 1: nối 2 chuỗi
    static String join(String a, String b) {
        return a + b;
    }

    // Overload 2: nối 2 chuỗi với separator
    static String join(String a, String b, String separator) {
        return a + separator + b;
    }

    // Overload 3: nối mảng chuỗi
    static String join(String[] parts, String separator) {
        return String.join(separator, parts);
    }

    // Overload 4: khác kiểu tham số
    static String join(int a, int b) {
        return a + "" + b;
    }
}

// Compiler chọn method dựa trên tham số
StringUtils.join("Hello", "World");              // Overload 1 → "HelloWorld"
StringUtils.join("Hello", "World", " ");         // Overload 2 → "Hello World"
StringUtils.join(new String[]{"A","B","C"}, "-"); // Overload 3 → "A-B-C"
StringUtils.join(1, 2);                          // Overload 4 → "12"
```

> **Lưu ý:** Overloading **không phụ thuộc** vào return type. Hai method cùng tên, cùng tham số nhưng khác return type → **compile error**.

**Constructor Overloading** — cũng là một dạng compile-time polymorphism:
```java
class Color {
    int r, g, b;
    double alpha;

    Color() { this(0, 0, 0, 1.0); }                  // Đen
    Color(int r, int g, int b) { this(r, g, b, 1.0); } // Không alpha
    Color(int r, int g, int b, double alpha) {         // Đầy đủ
        this.r = r; this.g = g; this.b = b; this.alpha = alpha;
    }
    Color(String hex) {                                // Từ hex string
        this.r = Integer.parseInt(hex.substring(1, 3), 16);
        this.g = Integer.parseInt(hex.substring(3, 5), 16);
        this.b = Integer.parseInt(hex.substring(5, 7), 16);
        this.alpha = 1.0;
    }
}

Color c1 = new Color();                    // Đen
Color c2 = new Color(255, 0, 0);           // Đỏ
Color c3 = new Color(255, 0, 0, 0.5);      // Đỏ trong suốt
Color c4 = new Color("#FF5733");           // Từ hex
```

### 6.2. Runtime Polymorphism (Overriding)

**Runtime Polymorphism** xảy ra khi JVM quyết định gọi method nào dựa trên **kiểu thực tế** của object tại **runtime** (Dynamic Method Dispatch).

```java
abstract class Notification {
    String recipient;

    Notification(String recipient) {
        this.recipient = recipient;
    }

    abstract void send(String message);

    void log(String message) {
        System.out.println("→ Gửi đến " + recipient + ": " + message);
    }
}

class EmailNotification extends Notification {
    EmailNotification(String email) { super(email); }

    @Override
    void send(String message) {
        System.out.println("📧 Email đến " + recipient);
        log(message);
    }
}

class SmsNotification extends Notification {
    SmsNotification(String phone) { super(phone); }

    @Override
    void send(String message) {
        System.out.println("📱 SMS đến " + recipient);
        log(message);
    }
}

class PushNotification extends Notification {
    PushNotification(String deviceId) { super(deviceId); }

    @Override
    void send(String message) {
        System.out.println("🔔 Push đến device " + recipient);
        log(message);
    }
}

// --- Sức mạnh của Runtime Polymorphism ---
// Code xử lý chung, không cần biết kiểu cụ thể
class NotificationService {
    void sendAll(List<Notification> notifications, String message) {
        for (Notification n : notifications) {
            n.send(message); // JVM gọi đúng method của object thực tế
        }
    }
}

List<Notification> list = List.of(
    new EmailNotification("an@gmail.com"),
    new SmsNotification("0901234567"),
    new PushNotification("device-abc-123")
);

new NotificationService().sendAll(list, "Đơn hàng đã xác nhận!");
// Output:
// 📧 Email đến an@gmail.com
// → Gửi đến an@gmail.com: Đơn hàng đã xác nhận!
// 📱 SMS đến 0901234567
// → Gửi đến 0901234567: Đơn hàng đã xác nhận!
// 🔔 Push đến device device-abc-123
// → Gửi đến device-abc-123: Đơn hàng đã xác nhận!
```

**Upcasting và Downcasting:**
```java
// Upcasting — tự động, an toàn
Animal animal = new Dog("Rex", "Husky"); // Dog → Animal (implicit)
animal.makeSound(); // Gọi Dog.makeSound() — runtime polymorphism

// Downcasting — cần cast tường minh, có thể lỗi
if (animal instanceof Dog dog) {   // Java 16+ pattern matching
    dog.makeSound();               // Truy cập method riêng của Dog
    System.out.println(dog.breed); // "Husky"
}

// ❌ Downcasting sai → ClassCastException
// Cat cat = (Cat) animal; // Runtime error! animal thực chất là Dog
```

---

## 7. Trừu tượng (Abstraction)

### 7.1. Abstract Class

**Abstract class** là class **không thể tạo object trực tiếp**, dùng làm class cha chứa cả method trừu tượng (chưa có body) và method cụ thể (có body).

```java
abstract class Database {
    protected String connectionUrl;

    Database(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    // Abstract methods — subclass PHẢI implement
    abstract void connect();
    abstract void disconnect();
    abstract List<Map<String, Object>> executeQuery(String sql);

    // Concrete method — dùng chung
    void printConnectionInfo() {
        System.out.println("Database URL: " + connectionUrl);
    }

    // Template Method pattern — định nghĩa flow, subclass tùy chỉnh bước
    void executeAndPrint(String sql) {
        connect();
        List<Map<String, Object>> results = executeQuery(sql);
        results.forEach(System.out::println);
        disconnect();
    }
}

class MySQLDatabase extends Database {
    MySQLDatabase(String url) { super(url); }

    @Override
    void connect() {
        System.out.println("Kết nối MySQL tại " + connectionUrl);
    }

    @Override
    void disconnect() {
        System.out.println("Ngắt kết nối MySQL");
    }

    @Override
    List<Map<String, Object>> executeQuery(String sql) {
        System.out.println("MySQL thực thi: " + sql);
        return List.of(Map.of("result", "MySQL data"));
    }
}

class PostgreSQLDatabase extends Database {
    PostgreSQLDatabase(String url) { super(url); }

    @Override
    void connect() {
        System.out.println("Kết nối PostgreSQL tại " + connectionUrl);
    }

    @Override
    void disconnect() {
        System.out.println("Ngắt kết nối PostgreSQL");
    }

    @Override
    List<Map<String, Object>> executeQuery(String sql) {
        System.out.println("PostgreSQL thực thi: " + sql);
        return List.of(Map.of("result", "PostgreSQL data"));
    }
}

// Sử dụng — code không phụ thuộc DB cụ thể
Database db = new PostgreSQLDatabase("jdbc:postgresql://localhost:5432/mydb");
db.executeAndPrint("SELECT * FROM users");
```

### 7.2. Interface

**Interface** là một "hợp đồng" (contract) hoàn toàn trừu tượng — định nghĩa **những gì** object có thể làm, không quan tâm **làm như thế nào**.

```java
// Interface định nghĩa khả năng (capability)
interface Sortable<T> {
    int compareTo(T other);
}

interface Printable {
    void print();

    // Default method (Java 8+) — có thể có body
    default void printWithBorder() {
        System.out.println("========================");
        print();
        System.out.println("========================");
    }
}

interface Exportable {
    String toJson();
    String toCsv();

    // Static method trong interface
    static void exportAll(List<Exportable> items, String format) {
        for (Exportable item : items) {
            switch (format) {
                case "json" -> System.out.println(item.toJson());
                case "csv" -> System.out.println(item.toCsv());
            }
        }
    }
}

// Class implement nhiều interface
class Employee implements Printable, Exportable, Sortable<Employee> {
    String name;
    double salary;

    Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    @Override
    public void print() {
        System.out.println("Employee: " + name + " - Lương: " + salary);
    }

    @Override
    public String toJson() {
        return "{\"name\":\"%s\",\"salary\":%.0f}".formatted(name, salary);
    }

    @Override
    public String toCsv() {
        return name + "," + salary;
    }

    @Override
    public int compareTo(Employee other) {
        return Double.compare(this.salary, other.salary);
    }
}

Employee emp = new Employee("An", 15_000_000);
emp.print();             // "Employee: An - Lương: 1.5E7"
emp.printWithBorder();   // Dùng default method
System.out.println(emp.toJson()); // {"name":"An","salary":15000000}
```

**Interface Evolution (Java 8+):**
```java
interface PaymentGateway {
    // Abstract method — bắt buộc implement
    boolean charge(double amount);

    // Default method — có sẵn, có thể override
    default void refund(double amount) {
        System.out.println("Hoàn tiền: " + amount);
    }

    // Static method — gọi qua interface name
    static PaymentGateway createDefault() {
        return amount -> {
            System.out.println("Default charge: " + amount);
            return true;
        };
    }

    // Private method (Java 9+) — helper cho default methods
    private void logTransaction(String type, double amount) {
        System.out.println("[TX] " + type + ": " + amount);
    }
}
```

### 7.3. So sánh Abstract Class và Interface

| Tiêu chí | Abstract Class | Interface |
|----------|---------------|-----------|
| Từ khóa | `abstract class` | `interface` |
| Kế thừa | `extends` (chỉ 1 class) | `implements` (nhiều interface) |
| Constructor | ✅ Có | ❌ Không |
| Fields | Có mọi loại (bao gồm non-final) | Chỉ `public static final` (constants) |
| Methods | Abstract + concrete | Abstract + `default` + `static` + `private` (Java 9+) |
| Access modifiers | Mọi loại | Methods mặc định `public` |
| Khi nào dùng | Có **trạng thái chung** (fields) + logic chung | Định nghĩa **khả năng** (behavior contract) |
| Ví dụ | `Animal` (có name, age) | `Flyable`, `Serializable`, `Comparable` |

**Quy tắc chọn:**
```
1. Cần chia sẻ STATE (fields non-static/non-final) giữa các subclass?
   → Abstract Class

2. Cần "đa kế thừa" — một class có nhiều khả năng?
   → Interface

3. Cần cả hai?
   → Abstract class cho state + Implement interfaces cho capabilities
```

**Ví dụ kết hợp cả hai:**
```java
// Interface cho khả năng
interface Drawable {
    void draw();
}

interface Resizable {
    void resize(double factor);
}

// Abstract class cho state chung
abstract class UIComponent implements Drawable {
    protected int x, y;
    protected int width, height;
    protected boolean visible = true;

    UIComponent(int x, int y, int width, int height) {
        this.x = x; this.y = y;
        this.width = width; this.height = height;
    }

    abstract void onClick();

    void hide() { visible = false; }
    void show() { visible = true; }
}

// Concrete class kết hợp abstract class + interface
class Button extends UIComponent implements Resizable {
    String label;

    Button(int x, int y, int w, int h, String label) {
        super(x, y, w, h);
        this.label = label;
    }

    @Override
    public void draw() {
        if (visible) {
            System.out.println("Vẽ Button [" + label + "] tại (" + x + "," + y + ")");
        }
    }

    @Override
    void onClick() {
        System.out.println("Button [" + label + "] được click!");
    }

    @Override
    public void resize(double factor) {
        width = (int)(width * factor);
        height = (int)(height * factor);
        System.out.println("Resize Button → " + width + "x" + height);
    }
}
```

---

## 8. Đóng gói (Encapsulation)

### 8.1. Access Modifiers chi tiết

Ngoài 4 access modifier cơ bản (`public`, `protected`, default, `private`), Java còn có các modifier khác ảnh hưởng đến thiết kế class:

```java
public class EncapsulationExample {

    // --- Các loại modifier cho fields ---
    private int mutableField;           // Thay đổi được qua setter
    private final String immutableField; // Chỉ set 1 lần (trong constructor)
    private static int instanceCount;   // Chia sẻ giữa tất cả instances

    // --- static final = CONSTANT ---
    public static final double PI = 3.14159265358979;
    public static final String APP_NAME = "MyApp";

    public EncapsulationExample(String immutableField) {
        this.immutableField = immutableField;
        instanceCount++;
    }

    // --- Ví dụ Immutable Class ---
}

// Immutable class — không thể thay đổi sau khi tạo
final class Money {
    private final double amount;
    private final String currency;

    Money(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    // Chỉ có getters, KHÔNG có setters
    public double getAmount() { return amount; }
    public String getCurrency() { return currency; }

    // Các operation trả về object MỚI, không modify object hiện tại
    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Khác loại tiền!");
        }
        return new Money(this.amount + other.amount, this.currency);
    }

    public Money multiply(double factor) {
        return new Money(this.amount * factor, this.currency);
    }

    @Override
    public String toString() {
        return amount + " " + currency;
    }
}

Money price = new Money(100, "USD");
Money tax = new Money(10, "USD");
Money total = price.add(tax);  // Object mới: 110 USD
// price vẫn là 100 USD — immutable!
```

> **Tại sao Immutable quan trọng?**
> - **Thread-safe** tự nhiên — không cần synchronize
> - Dùng làm key trong HashMap an toàn
> - Dễ debug — state không thay đổi bất ngờ
> - Ví dụ: `String`, `Integer`, `LocalDate` đều là immutable

### 8.2. Data Hiding

**Data Hiding** là nguyên tắc ẩn cấu trúc dữ liệu nội bộ, chỉ expose hành vi:

```java
class ShoppingCart {
    // ẩn cấu trúc dữ liệu — bên ngoài không biết dùng List hay Map
    private final List<CartItem> items = new ArrayList<>();

    // Public interface — chỉ hành vi, không lộ cấu trúc
    public void addItem(String productName, double price, int quantity) {
        // Logic kiểm tra, gộp item nếu trùng...
        for (CartItem item : items) {
            if (item.productName.equals(productName)) {
                item.quantity += quantity;
                return;
            }
        }
        items.add(new CartItem(productName, price, quantity));
    }

    public void removeItem(String productName) {
        items.removeIf(item -> item.productName.equals(productName));
    }

    public double getTotal() {
        return items.stream()
                .mapToDouble(item -> item.price * item.quantity)
                .sum();
    }

    public int getItemCount() {
        return items.size();
    }

    // Trả về bản sao (defensive copy) — không lộ List gốc
    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    // Inner class — cũng ẩn nếu không cần expose
    static class CartItem {
        String productName;
        double price;
        int quantity;

        CartItem(String productName, double price, int quantity) {
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
        }
    }
}

ShoppingCart cart = new ShoppingCart();
cart.addItem("iPhone 15", 25_000_000, 1);
cart.addItem("Ốp lưng", 200_000, 2);
cart.addItem("iPhone 15", 25_000_000, 1); // Gộp: quantity = 2

System.out.println("Số sản phẩm: " + cart.getItemCount()); // 2
System.out.println("Tổng: " + cart.getTotal()); // 50,400,000

// Không thể sửa trực tiếp list
// cart.getItems().add(new CartItem(...)); // ❌ UnsupportedOperationException
```

**Defensive Copy** — bảo vệ dữ liệu mutable:
```java
class DateRange {
    private final Date startDate;
    private final Date endDate;

    DateRange(Date start, Date end) {
        // Defensive copy — tránh caller sửa Date gốc
        this.startDate = new Date(start.getTime());
        this.endDate = new Date(end.getTime());
    }

    public Date getStartDate() {
        // Trả về bản sao — không lộ reference nội bộ
        return new Date(startDate.getTime());
    }

    public Date getEndDate() {
        return new Date(endDate.getTime());
    }
}
```

> **Best Practice:** Dùng `java.time.LocalDate` (immutable) thay vì `java.util.Date` (mutable) để tránh phải defensive copy.

---

## 9. Các khái niệm nâng cao

### 9.1. Composition (Hợp thành)

**Composition** là quan hệ "**has-a**" mạnh — object con **không tồn tại** nếu object cha bị hủy. Object cha tạo và quản lý vòng đời object con.

```java
// Engine không tồn tại độc lập ngoài Car
class Engine {
    private String type;
    private int horsepower;

    Engine(String type, int horsepower) {
        this.type = type;
        this.horsepower = horsepower;
    }

    void start() {
        System.out.println(type + " engine (" + horsepower + "HP) started!");
    }

    void stop() {
        System.out.println(type + " engine stopped.");
    }
}

class Car {
    private String brand;
    private final Engine engine; // Composition — Car TẠO và SỞ HỮU Engine

    Car(String brand, String engineType, int hp) {
        this.brand = brand;
        this.engine = new Engine(engineType, hp); // Tạo bên trong Car
    }

    void start() {
        System.out.println(brand + " đang khởi động...");
        engine.start(); // Delegate cho Engine
    }

    void stop() {
        engine.stop();
        System.out.println(brand + " đã tắt máy.");
    }
    // Khi Car bị GC → Engine cũng bị GC (không ai giữ reference)
}

Car car = new Car("Toyota", "V6", 300);
car.start();
// Toyota đang khởi động...
// V6 engine (300HP) started!
```

### 9.2. Aggregation (Tập hợp)

**Aggregation** là quan hệ "**has-a**" yếu — object con **có thể tồn tại** độc lập ngoài object cha.

```java
class Student {
    private String name;

    Student(String name) { this.name = name; }

    String getName() { return name; }
}

class Classroom {
    private String roomName;
    private List<Student> students; // Aggregation — Student tồn tại độc lập

    Classroom(String roomName) {
        this.roomName = roomName;
        this.students = new ArrayList<>();
    }

    void addStudent(Student student) {  // Nhận từ bên ngoài, không tạo
        students.add(student);
    }

    void removeStudent(Student student) {
        students.remove(student);
    }

    void printRoster() {
        System.out.println("Lớp " + roomName + ":");
        students.forEach(s -> System.out.println("  - " + s.getName()));
    }
}

// Student tồn tại trước và sau Classroom
Student an = new Student("An");
Student binh = new Student("Bình");

Classroom mathClass = new Classroom("Toán A1");
mathClass.addStudent(an);
mathClass.addStudent(binh);
mathClass.printRoster();

// Xóa lớp → Student vẫn tồn tại
mathClass = null;
System.out.println(an.getName()); // "An" — vẫn sống
```

### 9.3. Association (Liên kết)

**Association** là quan hệ **"uses-a"** hoặc **"knows-a"** — hai object liên kết nhưng **không sở hữu** nhau. Có thể là 1-1, 1-N, hoặc N-N.

```java
class Doctor {
    private String name;
    private List<Patient> patients = new ArrayList<>();

    Doctor(String name) { this.name = name; }

    void addPatient(Patient patient) {
        patients.add(patient);
        patient.addDoctor(this); // Liên kết hai chiều
    }

    String getName() { return name; }
}

class Patient {
    private String name;
    private List<Doctor> doctors = new ArrayList<>();

    Patient(String name) { this.name = name; }

    void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    String getName() { return name; }

    void printDoctors() {
        System.out.println(name + " khám bởi:");
        doctors.forEach(d -> System.out.println("  - BS. " + d.getName()));
    }
}

// Association N-N — Doctor ↔ Patient
Doctor dr1 = new Doctor("Nguyễn Văn A");
Doctor dr2 = new Doctor("Trần Thị B");
Patient p1 = new Patient("An");

dr1.addPatient(p1); // An khám BS. Nguyễn Văn A
dr2.addPatient(p1); // An khám BS. Trần Thị B

p1.printDoctors();
// An khám bởi:
//   - BS. Nguyễn Văn A
//   - BS. Trần Thị B
```

### 9.4. Dependency (Phụ thuộc)

**Dependency** là quan hệ yếu nhất — một class **sử dụng** class khác trong method (tham số, biến cục bộ, return type) nhưng **không lưu** reference.

```java
class EmailService {
    void sendEmail(String to, String subject, String body) {
        System.out.println("Email gửi đến " + to + ": " + subject);
    }
}

class OrderService {
    // Dependency — dùng EmailService nhưng không lưu làm field
    void placeOrder(String product, String customerEmail) {
        System.out.println("Đặt hàng: " + product);

        // Tạo và sử dụng EmailService chỉ trong method
        EmailService emailService = new EmailService();
        emailService.sendEmail(customerEmail, "Xác nhận đơn hàng",
            "Đơn hàng " + product + " đã được đặt thành công!");
    }
}
```

**Tổng hợp các mối quan hệ:**

| Quan hệ | Sở hữu | Vòng đời | Ví dụ | Code hint |
|---------|-------|----------|-------|-----------|
| **Composition** | Mạnh | Con phụ thuộc cha | Car-Engine | `new` bên trong constructor |
| **Aggregation** | Yếu | Con độc lập | Classroom-Student | Nhận object qua method |
| **Association** | Không | Độc lập | Doctor-Patient | Reference hai chiều |
| **Dependency** | Không | Tạm thời | OrderService-EmailService | Dùng trong method rồi bỏ |

```
Composition:   Car ◆───── Engine         (filled diamond)
Aggregation:   Classroom ◇───── Student   (empty diamond)
Association:   Doctor ────── Patient       (plain line)
Dependency:    OrderService - - - > Email  (dashed arrow)
```

---

## 10. Design Patterns cơ bản

### 10.1. Singleton Pattern

**Mục đích:** Đảm bảo class chỉ có **duy nhất một instance** trong toàn bộ ứng dụng.

**Khi nào dùng:** Database connection pool, Logger, Configuration, Cache.

```java
// Cách 1: Thread-safe với Double-Checked Locking
class AppConfig {
    private static volatile AppConfig instance;

    private String dbUrl;
    private int maxConnections;

    private AppConfig() {
        // Load config từ file hoặc environment
        this.dbUrl = "jdbc:mysql://localhost:3306/mydb";
        this.maxConnections = 10;
    }

    public static AppConfig getInstance() {
        if (instance == null) {                         // Check 1 — nhanh, không lock
            synchronized (AppConfig.class) {
                if (instance == null) {                 // Check 2 — trong lock
                    instance = new AppConfig();
                }
            }
        }
        return instance;
    }

    public String getDbUrl() { return dbUrl; }
    public int getMaxConnections() { return maxConnections; }
}

// Sử dụng
AppConfig config1 = AppConfig.getInstance();
AppConfig config2 = AppConfig.getInstance();
System.out.println(config1 == config2);         // true — cùng một instance
System.out.println(config1.getDbUrl());         // "jdbc:mysql://localhost:3306/mydb"

// Cách 2: Enum Singleton (Recommended — Joshua Bloch, Effective Java)
// Thread-safe, serialization-safe, reflection-safe
enum Logger {
    INSTANCE;

    void info(String message) {
        System.out.println("[INFO] " + message);
    }

    void error(String message) {
        System.out.println("[ERROR] " + message);
    }
}

Logger.INSTANCE.info("App started"); // [INFO] App started
```

### 10.2. Factory Pattern

**Mục đích:** Tạo object mà **không expose logic khởi tạo**, client chỉ cần biết "tôi muốn gì" chứ không cần biết "tạo như thế nào".

```java
// Các loại notification
interface Notification {
    void send(String message);
}

class EmailNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("📧 Email: " + message);
    }
}

class SmsNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("📱 SMS: " + message);
    }
}

class PushNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("🔔 Push: " + message);
    }
}

// Factory — quyết định tạo loại nào
class NotificationFactory {
    // Simple Factory Method
    static Notification create(String type) {
        return switch (type.toLowerCase()) {
            case "email" -> new EmailNotification();
            case "sms"   -> new SmsNotification();
            case "push"  -> new PushNotification();
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }
}

// Client code — không cần biết constructor cụ thể
Notification n1 = NotificationFactory.create("email");
Notification n2 = NotificationFactory.create("sms");
n1.send("Xin chào!"); // 📧 Email: Xin chào!
n2.send("OTP: 1234");  // 📱 SMS: OTP: 1234

// Lợi ích: Thêm loại mới (SlackNotification) chỉ cần:
// 1. Tạo class SlackNotification implements Notification
// 2. Thêm case "slack" trong Factory
// → Client code KHÔNG thay đổi
```

### 10.3. Builder Pattern

**Mục đích:** Tạo object phức tạp từng bước, tránh constructor quá nhiều tham số (telescoping constructor anti-pattern).

```java
class HttpRequest {
    // Required fields
    private final String url;
    private final String method;

    // Optional fields
    private final Map<String, String> headers;
    private final String body;
    private final int timeout;
    private final boolean followRedirects;

    // Private constructor — chỉ Builder được tạo
    private HttpRequest(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.headers = builder.headers;
        this.body = builder.body;
        this.timeout = builder.timeout;
        this.followRedirects = builder.followRedirects;
    }

    // Getters...
    public String getUrl() { return url; }
    public String getMethod() { return method; }

    @Override
    public String toString() {
        return method + " " + url + " | timeout=" + timeout
            + " | headers=" + headers + " | body=" + body;
    }

    // --- Static inner Builder class ---
    static class Builder {
        private final String url;
        private String method = "GET";
        private Map<String, String> headers = new HashMap<>();
        private String body;
        private int timeout = 30_000; // default 30s
        private boolean followRedirects = true;

        Builder(String url) {
            this.url = url;
        }

        Builder method(String method) {
            this.method = method;
            return this; // return this → method chaining
        }

        Builder header(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        Builder body(String body) {
            this.body = body;
            return this;
        }

        Builder timeout(int ms) {
            this.timeout = ms;
            return this;
        }

        Builder followRedirects(boolean follow) {
            this.followRedirects = follow;
            return this;
        }

        HttpRequest build() {
            // Validation
            if (url == null || url.isBlank()) {
                throw new IllegalArgumentException("URL is required");
            }
            return new HttpRequest(this);
        }
    }
}

// Sử dụng Builder — dễ đọc, dễ hiểu
HttpRequest getRequest = new HttpRequest.Builder("https://api.example.com/users")
        .method("GET")
        .header("Accept", "application/json")
        .header("Authorization", "Bearer token123")
        .timeout(5000)
        .build();

HttpRequest postRequest = new HttpRequest.Builder("https://api.example.com/users")
        .method("POST")
        .header("Content-Type", "application/json")
        .body("{\"name\":\"An\",\"email\":\"an@example.com\"}")
        .timeout(10_000)
        .followRedirects(false)
        .build();

System.out.println(getRequest);
// GET https://api.example.com/users | timeout=5000 | headers={...}
System.out.println(postRequest);
// POST https://api.example.com/users | timeout=10000 | body={...}
```

> **Khi nào dùng Builder?** Constructor có **>4 tham số**, đặc biệt khi nhiều tham số là optional. Thay vì `new Pizza(12, true, false, true, false, "thin", null)` → `new Pizza.Builder(12).cheese(true).crust("thin").build()`.

---

## 11. Best Practices trong OOP

### 11.1. SOLID Principles — SOLID là gì?

**SOLID** là 5 nguyên tắc thiết kế OOP giúp code **dễ bảo trì, dễ mở rộng, dễ test**.

#### S — Single Responsibility Principle (SRP)

> **Mỗi class chỉ nên có MỘT lý do để thay đổi** — tức là chỉ đảm nhận một trách nhiệm.

```java
// ❌ Vi phạm SRP — UserService làm quá nhiều việc
class UserService {
    void createUser(String name) { /* tạo user */ }
    void sendEmail(String email) { /* gửi email */ }
    String generateReport() { return "report"; } // Tại sao UserService tạo report?
    void saveToFile(String data) { /* ghi file */ }
}

// ✅ Tuân thủ SRP — mỗi class 1 trách nhiệm
class UserService {
    void createUser(String name) { /* tạo user */ }
}

class EmailService {
    void sendEmail(String email, String content) { /* gửi email */ }
}

class ReportGenerator {
    String generateReport(List<User> users) { return "report"; }
}

class FileExporter {
    void saveToFile(String data, String path) { /* ghi file */ }
}
```

#### O — Open/Closed Principle (OCP)

> **Open for extension, Closed for modification** — Mở rộng tính năng bằng cách **thêm code mới**, không sửa code cũ.

```java
// ❌ Vi phạm OCP — thêm hình mới phải sửa AreaCalculator
class AreaCalculator {
    double calculate(Object shape) {
        if (shape instanceof Circle c) {
            return Math.PI * c.radius * c.radius;
        } else if (shape instanceof Rectangle r) {
            return r.width * r.height;
        }
        // Thêm Triangle? → phải sửa method này
        return 0;
    }
}

// ✅ Tuân thủ OCP — thêm hình mới chỉ cần tạo class mới
interface Shape {
    double area();
}

class Circle implements Shape {
    double radius;
    Circle(double r) { radius = r; }
    @Override
    public double area() { return Math.PI * radius * radius; }
}

class Rectangle implements Shape {
    double width, height;
    Rectangle(double w, double h) { width = w; height = h; }
    @Override
    public double area() { return width * height; }
}

// Thêm Triangle → tạo class mới, KHÔNG sửa code cũ
class Triangle implements Shape {
    double base, height;
    Triangle(double b, double h) { base = b; height = h; }
    @Override
    public double area() { return 0.5 * base * height; }
}

class AreaCalculator {
    double totalArea(List<Shape> shapes) {
        return shapes.stream().mapToDouble(Shape::area).sum();
    }
}
```

#### L — Liskov Substitution Principle (LSP)

> **Subclass phải thay thế được superclass** mà không làm hỏng chương trình.

```java
// ❌ Vi phạm LSP — Square không thể thay thế Rectangle
class Rectangle {
    protected int width, height;
    void setWidth(int w) { width = w; }
    void setHeight(int h) { height = h; }
    int area() { return width * height; }
}

class Square extends Rectangle {
    @Override
    void setWidth(int w) { width = w; height = w; } // Phá vỡ hành vi Rectangle!
    @Override
    void setHeight(int h) { width = h; height = h; }
}

// Test thất bại:
Rectangle r = new Square();
r.setWidth(5);
r.setHeight(3);
assert r.area() == 15; // ❌ FAIL! area() = 9 vì Square đặt cả 2 chiều = 3

// ✅ Tuân thủ LSP — dùng interface/abstract chung
interface Shape {
    int area();
}

class Rectangle implements Shape {
    private int width, height;
    Rectangle(int w, int h) { width = w; height = h; }
    @Override
    public int area() { return width * height; }
}

class Square implements Shape {
    private int side;
    Square(int s) { side = s; }
    @Override
    public int area() { return side * side; }
}
```

#### I — Interface Segregation Principle (ISP)

> **Không bắt buộc implement interface không dùng đến** — chia nhỏ interface béo thành nhiều interface gọn.

```java
// ❌ Vi phạm ISP — interface quá lớn
interface Worker {
    void work();
    void eat();
    void sleep();
    void attendMeeting();
    void writeReport();
}

// Robot bắt buộc implement eat(), sleep()? → vô lý!
class Robot implements Worker {
    @Override public void work() { /* ... */ }
    @Override public void eat() { /* không ăn */ }        // ❌ Vô nghĩa
    @Override public void sleep() { /* không ngủ */ }     // ❌ Vô nghĩa
    @Override public void attendMeeting() { /* ... */ }
    @Override public void writeReport() { /* ... */ }
}

// ✅ Tuân thủ ISP — tách thành interface nhỏ
interface Workable {
    void work();
}

interface Feedable {
    void eat();
    void sleep();
}

interface Reportable {
    void writeReport();
}

class HumanWorker implements Workable, Feedable, Reportable {
    @Override public void work() { System.out.println("Đang làm việc..."); }
    @Override public void eat() { System.out.println("Đang ăn trưa..."); }
    @Override public void sleep() { System.out.println("Đang ngủ..."); }
    @Override public void writeReport() { System.out.println("Viết báo cáo..."); }
}

class RobotWorker implements Workable, Reportable {
    @Override public void work() { System.out.println("Robot đang xử lý..."); }
    @Override public void writeReport() { System.out.println("Robot xuất report..."); }
    // Không cần eat(), sleep() → gọn gàng!
}
```

#### D — Dependency Inversion Principle (DIP)

> **Module cấp cao không phụ thuộc module cấp thấp** — cả hai phụ thuộc vào abstraction (interface).

```java
// ❌ Vi phạm DIP — OrderService phụ thuộc trực tiếp MySQLDatabase
class MySQLDatabase {
    void save(String data) {
        System.out.println("MySQL: save " + data);
    }
}

class OrderService {
    private MySQLDatabase db = new MySQLDatabase(); // Phụ thuộc cụ thể!

    void createOrder(String order) {
        db.save(order);
    }
    // Muốn đổi sang PostgreSQL? → Phải SỬA OrderService
}

// ✅ Tuân thủ DIP — phụ thuộc abstraction
interface DatabaseRepository {
    void save(String data);
    String findById(String id);
}

class MySQLRepository implements DatabaseRepository {
    @Override
    public void save(String data) { System.out.println("MySQL: save " + data); }
    @Override
    public String findById(String id) { return "MySQL data"; }
}

class PostgreSQLRepository implements DatabaseRepository {
    @Override
    public void save(String data) { System.out.println("PostgreSQL: save " + data); }
    @Override
    public String findById(String id) { return "PostgreSQL data"; }
}

class OrderService {
    private final DatabaseRepository repository; // Phụ thuộc INTERFACE

    // Dependency Injection qua constructor
    OrderService(DatabaseRepository repository) {
        this.repository = repository;
    }

    void createOrder(String order) {
        repository.save(order);
    }
}

// Dễ dàng đổi implementation — KHÔNG sửa OrderService
OrderService service1 = new OrderService(new MySQLRepository());
OrderService service2 = new OrderService(new PostgreSQLRepository());
service1.createOrder("Order #1"); // MySQL: save Order #1
service2.createOrder("Order #2"); // PostgreSQL: save Order #2
```

### 11.2. DRY, KISS, YAGNI

#### DRY — Don't Repeat Yourself

> **Không lặp lại logic** — nếu cùng logic xuất hiện 2+ nơi, tách thành method/class riêng.

```java
// ❌ Vi phạm DRY — logic validate lặp ở nhiều nơi
class UserService {
    void createUser(String email) {
        if (email == null || !email.contains("@") || email.length() > 255) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }
        // tạo user...
    }

    void updateEmail(String email) {
        if (email == null || !email.contains("@") || email.length() > 255) {
            throw new IllegalArgumentException("Email không hợp lệ"); // Lặp!
        }
        // cập nhật...
    }
}

// ✅ Tuân thủ DRY — tách logic chung
class EmailValidator {
    static void validate(String email) {
        if (email == null || !email.contains("@") || email.length() > 255) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }
    }
}

class UserService {
    void createUser(String email) {
        EmailValidator.validate(email);
        // tạo user...
    }

    void updateEmail(String email) {
        EmailValidator.validate(email);
        // cập nhật...
    }
}
```

#### KISS — Keep It Simple, Stupid

> **Giữ đơn giản** — giải pháp đơn giản nhất thường là tốt nhất.

```java
// ❌ Vi phạm KISS — over-engineering
class StringHelper {
    static boolean isPalindrome(String s) {
        char[] chars = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        Queue<Character> queue = new LinkedList<>();
        for (char c : chars) { stack.push(c); queue.offer(c); }
        while (!stack.isEmpty()) {
            if (!stack.pop().equals(queue.poll())) return false;
        }
        return true;
    }
}

// ✅ Tuân thủ KISS — đơn giản, dễ hiểu
class StringHelper {
    static boolean isPalindrome(String s) {
        return s.equals(new StringBuilder(s).reverse().toString());
    }
}
```

#### YAGNI — You Aren't Gonna Need It

> **Không code tính năng chưa cần** — chỉ implement khi thực sự có yêu cầu.

```java
// ❌ Vi phạm YAGNI — code sẵn những thứ chưa ai cần
class User {
    String name;
    String email;
    String facebookId;     // "Biết đâu sau này cần"
    String twitterHandle;  // "Phòng khi tích hợp Twitter"
    String linkedinUrl;    // "Lỡ khách hàng yêu cầu"
    byte[] avatar;         // "Chắc sẽ cần upload avatar"
    Map<String, Object> metadata; // "Để linh hoạt"

    // 15 methods cho các tính năng chưa ai dùng...
}

// ✅ Tuân thủ YAGNI — chỉ có những gì cần NGAY BÂY GIỜ
class User {
    String name;
    String email;

    User(String name, String email) {
        this.name = name;
        this.email = email;
    }
    // Thêm field/method khi có requirement thực tế
}
```

**Tổng hợp:**

| Nguyên tắc | Ý nghĩa | Dấu hiệu vi phạm |
|-----------|---------|-------------------|
| **DRY** | Không lặp logic | Copy-paste code giữa các method/class |
| **KISS** | Giữ đơn giản | Code phức tạp hơn bài toán yêu cầu |
| **YAGNI** | Không code trước | Tạo feature/class mà chưa ai yêu cầu |

> **Phỏng vấn:** Hãy giải thích SOLID và cho ví dụ. — Đây là câu hỏi kinh điển. Hãy nắm vững cả 5 nguyên tắc, mỗi cái kèm 1 ví dụ vi phạm và 1 ví dụ tuân thủ. SOLID + DRY/KISS/YAGNI là nền tảng để viết code chất lượng trong thực tế.
