# 01. C/C++ Object-Oriented Programming

Trong C++, lập trình hướng đối tượng gồm 4 trụ cột chính: Encapsulation, Abstraction, Inheritance, Polymorphism. C có `struct` và function pointer nên có thể mô phỏng OOP, nhưng C++ hỗ trợ trực tiếp bằng `class`, access modifier, constructor/destructor, virtual function.

## 1. Encapsulation - Đóng gói

Đóng gói là việc gom data và hành vi liên quan vào cùng một class, đồng thời che giấu data bằng `private`/`protected`, chỉ cho truy cập qua public method.

```cpp
#include <iostream>
#include <stdexcept>
using namespace std;

class BankAccount {
private:
    double balance;

public:
    BankAccount(double initial) : balance(initial) {}

    void deposit(double amount) {
        if (amount <= 0) throw invalid_argument("amount must be positive");
        balance += amount;
    }

    bool withdraw(double amount) {
        if (amount <= 0 || amount > balance) return false;
        balance -= amount;
        return true;
    }

    double getBalance() const {
        return balance;
    }
};

int main() {
    BankAccount acc(100);
    acc.deposit(50);
    cout << acc.getBalance(); // 150
}
```

Ý chính: bên ngoài không sửa trực tiếp `balance`, nên class có thể bảo vệ invariant: số tiền không bị rút quá mức.

## 2. Abstraction - Trừu tượng hóa

Trừu tượng hóa là chỉ đưa ra interface cần thiết, ẩn chi tiết cài đặt. Trong C++ thường dùng abstract class có pure virtual function.

```cpp
#include <iostream>
using namespace std;

class Shape {
public:
    virtual double area() const = 0; // pure virtual
    virtual ~Shape() = default;
};

class Rectangle : public Shape {
private:
    double w, h;

public:
    Rectangle(double width, double height) : w(width), h(height) {}
    double area() const override {
        return w * h;
    }
};

int main() {
    Shape* s = new Rectangle(3, 4);
    cout << s->area(); // 12
    delete s;
}
```

Người dùng chỉ cần biết `Shape` có hàm `area()`, không cần biết hình chữ nhật tính diện tích ra sao.

## 3. Inheritance - Kế thừa

Kế thừa cho phép class con tái sử dụng và mở rộng class cha.

```cpp
class Animal {
public:
    void eat() { cout << "Eating\n"; }
};

class Dog : public Animal {
public:
    void bark() { cout << "Woof\n"; }
};
```

### 5 loại kế thừa thường gặp

1. Single inheritance: một class con kế thừa một class cha.

```cpp
class Dog : public Animal {};
```

2. Multiple inheritance: một class con kế thừa nhiều class cha.

```cpp
class Printer {
public:
    void print() {}
};

class Scanner {
public:
    void scan() {}
};

class MultiFunctionMachine : public Printer, public Scanner {};
```

3. Multilevel inheritance: kế thừa nhiều tầng.

```cpp
class Animal {};
class Mammal : public Animal {};
class Dog : public Mammal {};
```

4. Hierarchical inheritance: nhiều class con cùng kế thừa một class cha.

```cpp
class Cat : public Animal {};
class Bird : public Animal {};
```

5. Hybrid inheritance: kết hợp nhiều dạng, thường gặp diamond problem (vấn đề kim cương).

```cpp
class A {};
class B : virtual public A {};
class C : virtual public A {};
class D : public B, public C {}; // virtual inheritance tránh có 2 bản A
```

## 4. Polymorphism - Đa hình

Đa hình nghĩa là cùng một interface/tên hàm nhưng có nhiều hình thái hành vi. Nó thường xuất hiện khi có hệ thống class liên quan bằng kế thừa.

### Compile-time polymorphism - Đa hình lúc biên dịch

Gồm function overloading, operator overloading, template.

```cpp
#include <iostream>
using namespace std;

int add(int a, int b) { return a + b; }
double add(double a, double b) { return a + b; }

class Point {
public:
    int x, y;
    Point(int x, int y) : x(x), y(y) {}

    Point operator+(const Point& other) const {
        return Point(x + other.x, y + other.y);
    }
};
```

Hàm nào được gọi đã được quyết định tại compile time.

### Runtime polymorphism - Đa hình lúc chạy

Dùng `virtual` và `override`. Hàm được gọi phụ thuộc vào object thật sự tại runtime.

```cpp
#include <iostream>
using namespace std;

class Animal {
public:
    virtual void speak() const {
        cout << "Animal sound\n";
    }

    virtual ~Animal() = default;
};

class Dog : public Animal {
public:
    void speak() const override {
        cout << "Woof\n";
    }
};

class Cat : public Animal {
public:
    void speak() const override {
        cout << "Meow\n";
    }
};

void makeSound(const Animal& animal) {
    animal.speak();
}

int main() {
    Dog d;
    Cat c;
    makeSound(d); // Woof
    makeSound(c); // Meow
}
```

Ghi nhớ: nếu class có virtual function, destructor của base class nên là `virtual`.

## Virtual Function

- `virtual`: cho phép override ở class con và gọi đúng hàm theo kiểu object thật.
- `override`: báo compiler kiểm tra hàm con có override đúng signature không.
- Pure virtual `= 0`: biến class thành abstract class.

```cpp
class Base {
public:
    virtual void run() = 0;
    virtual ~Base() = default;
};
```

## static, final, friend

### static

Trong class, `static` member thuộc về class, không thuộc từng object.

```cpp
class Counter {
private:
    static int count;

public:
    Counter() { ++count; }
    static int getCount() { return count; }
};

int Counter::count = 0;
```

Trong function, local `static` giữ giá trị qua các lần gọi.

```cpp
int nextId() {
    static int id = 0;
    return ++id;
}
```

### final

Chặn class không cho kế thừa, hoặc chặn virtual function không cho override tiếp.

```cpp
class Logger final {};

class Base {
public:
    virtual void run() final {}
};
```

### friend

Cho một function/class khác truy cập private/protected member. Dùng ít vì làm giảm đóng gói.

```cpp
class Box {
private:
    int secret = 42;

    friend void printSecret(const Box& b);
};

void printSecret(const Box& b) {
    cout << b.secret;
}
```

## SOLID

1. Single Responsibility: một class nên có một lý do để thay đổi.
2. Open/Closed: mở rộng được, hạn chế sửa code cũ.
3. Liskov Substitution: class con thay thế class cha mà không làm sai hành vi.
4. Interface Segregation: tách interface nhỏ, tránh bắt class implement hàm không dùng.
5. Dependency Inversion: phụ thuộc vào abstraction, không phụ thuộc trực tiếp implementation.

Ví dụ DIP:

```cpp
class IMessageSender {
public:
    virtual void send(const string& msg) = 0;
    virtual ~IMessageSender() = default;
};

class EmailSender : public IMessageSender {
public:
    void send(const string& msg) override {
        cout << "Email: " << msg << "\n";
    }
};

class Notification {
private:
    IMessageSender& sender;

public:
    Notification(IMessageSender& s) : sender(s) {}
    void notify(const string& msg) {
        sender.send(msg);
    }
};
```

## Hiểu sâu khi đi phỏng vấn

### Vì sao C++ cần virtual destructor?

Khi xóa object con thông qua con trỏ base, nếu destructor của base không `virtual` thì hành vi có thể không đúng: destructor của class con có thể không được gọi, dẫn đến leak resource.

```cpp
class Base {
public:
    virtual ~Base() = default;
};

class FileReader : public Base {
private:
    FILE* f;

public:
    FileReader(const char* path) {
        f = fopen(path, "r");
    }

    ~FileReader() override {
        if (f) fclose(f);
    }
};

Base* p = new FileReader("data.txt");
delete p; // an toàn vì Base::~Base là virtual
```

Câu trả lời interview ngắn: Nếu class được dùng đa hình, đặc biệt có virtual function, base destructor nên là virtual để xóa object qua base pointer không mất phần destructor của derived class.

### Overloading, overriding, hiding khác nhau thế nào?

| Khái niệm | Xảy ra khi | Quyết định lúc nào | Ví dụ |
|---|---|---|---|
| Overloading | Cùng tên hàm, khác tham số trong cùng scope | Compile time | `print(int)`, `print(string)` |
| Overriding | Derived class định nghĩa lại virtual function cùng signature | Runtime | `Dog::speak()` override `Animal::speak()` |
| Hiding | Derived class có hàm cùng tên làm ẩn overload ở base | Compile time | `Derived::run(double)` ẩn `Base::run(int)` |

Ví dụ hiding dễ bị hỏi:

```cpp
class Base {
public:
    void print(int) {}
    void print(double) {}
};

class Derived : public Base {
public:
    void print(string) {}
};

Derived d;
// d.print(10); // lỗi: Base::print bị ẩn bởi Derived::print(string)
```

Muốn đưa overload của base vào derived:

```cpp
class Derived : public Base {
public:
    using Base::print;
    void print(string) {}
};
```

### Object slicing

Object slicing xảy ra khi copy object con vào biến kiểu base bằng value. Phần riêng của derived bị cắt mất.

```cpp
class Animal {
public:
    virtual void speak() const { cout << "Animal\n"; }
};

class Dog : public Animal {
public:
    void speak() const override { cout << "Dog\n"; }
};

Dog dog;
Animal a = dog; // slicing
a.speak();      // Animal, không còn phần Dog
```

Cách tránh: truyền bằng reference hoặc pointer.

```cpp
void call(const Animal& a) {
    a.speak();
}
```

### Access modifier và inheritance mode

Trong C++, `public`, `protected`, `private` không chỉ áp dụng cho member mà còn áp dụng cho kiểu kế thừa.

| Kế thừa | Public member của base thành | Protected member của base thành |
|---|---|---|
| `public` | public | protected |
| `protected` | protected | protected |
| `private` | private | private |

Thực tế:

- `public inheritance`: quan hệ "is-a", ví dụ `Dog` là `Animal`.
- `private inheritance`: dùng lại implementation, nhưng không muốn expose interface base. Trong code hiện đại thường ưu tiên composition.

### Composition vs inheritance

Một câu hỏi rất hay: khi nào dùng kế thừa, khi nào dùng composition?

- Dùng inheritance khi derived thật sự là một dạng của base và cần đa hình.
- Dùng composition khi class chỉ "có" một thành phần khác.

```cpp
class Engine {
public:
    void start() {}
};

class Car {
private:
    Engine engine; // Car has an Engine

public:
    void start() {
        engine.start();
    }
};
```

Nếu chỉ muốn tái sử dụng code, composition thường an toàn hơn inheritance vì giảm coupling và tránh phá vỡ invariant của base class.

## SOLID chi tiết hơn

### S - Single Responsibility Principle

Một class nên có một nhóm trách nhiệm liên quan chặt chẽ và một lý do chính để thay đổi.

Ví dụ xấu:

```cpp
class Report {
public:
    string buildContent();
    void saveToFile(const string& path);
    void sendEmail(const string& to);
};
```

Class này thay đổi khi format report đổi, cách lưu file đổi, hoặc cách gửi mail đổi. Tách ra sẽ dễ test hơn:

```cpp
class ReportBuilder {};
class ReportRepository {};
class EmailService {};
```

### O - Open/Closed Principle

Code nên mở cho mở rộng nhưng đóng với sửa đổi. Thường đạt được bằng polymorphism hoặc strategy.

Nếu mỗi lần thêm loại giảm giá lại sửa `if/else`, dễ gây regression. Thay vào đó tách interface:

```cpp
class DiscountPolicy {
public:
    virtual double apply(double price) const = 0;
    virtual ~DiscountPolicy() = default;
};
```

### L - Liskov Substitution Principle

Class con phải thay thế được class cha mà không làm người dùng base class bất ngờ.

Ví dụ kinh điển: `Square` kế thừa `Rectangle` thường sai nếu `Rectangle` cho set width/height độc lập. Vì `Square` không giữ được hành vi kỳ vọng của `Rectangle`.

### I - Interface Segregation Principle

Không ép class implement method không dùng.

```cpp
class Printer {
public:
    virtual void print() = 0;
};

class Scanner {
public:
    virtual void scan() = 0;
};
```

Tốt hơn một interface lớn kiểu `Machine` gồm cả `print`, `scan`, `fax` trong khi nhiều máy không hỗ trợ fax.

### D - Dependency Inversion Principle

Module cấp cao không nên phụ thuộc trực tiếp module cấp thấp. Cả hai nên phụ thuộc abstraction.

Điểm phỏng vấn cần nhấn mạnh: DIP không có nghĩa là mọi thứ đều phải virtual/interface. Chỉ tạo abstraction khi có nhiều implementation, cần test/mock, hoặc dependency có khả năng thay đổi.

## Câu hỏi bẫy thường gặp

1. Constructor có virtual được không?

Không. Virtual dispatch phụ thuộc object đã được xây dựng, trong constructor phần derived chưa hoàn chỉnh.

2. Gọi virtual function trong constructor có sao không?

Không nên. Khi base constructor chạy, virtual call không dispatch xuống derived như bạn kỳ vọng.

3. Abstract class có constructor không?

Có. Constructor của abstract class dùng để khởi tạo phần base khi object derived được tạo.

4. Interface trong C++ là gì?

C++ không có keyword `interface` như Java/C#. Thường dùng abstract class chỉ chứa pure virtual functions và virtual destructor.
