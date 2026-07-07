# Chương 7: Lập trình hướng đối tượng trong C++

## 1. Class là gì?

Class là bản thiết kế cho đối tượng. Đối tượng có dữ liệu và hành vi.

```cpp
class BankAccount {
private:
    double balance;

public:
    void deposit(double amount);
    double getBalance() const;
};
```

## 2. Đóng gói

Đóng gói là che giấu dữ liệu bên trong và chỉ cho phép truy cập qua hàm công khai.

```cpp
private:
    double balance;
```

Nếu cho sửa trực tiếp `balance`, code bên ngoài có thể gán giá trị âm. Dùng method giúp kiểm soát quy tắc.

## 3. Constructor

Constructor được gọi khi tạo đối tượng.

```cpp
class Student {
public:
    Student(std::string name, int age)
        : name(name), age(age) {}

private:
    std::string name;
    int age;
};
```

Danh sách khởi tạo sau dấu `:` là cách nên dùng để khởi tạo field.

## 4. `const` method

Method không thay đổi trạng thái đối tượng nên đánh dấu `const`.

```cpp
double getBalance() const {
    return balance;
}
```

## 5. Kế thừa

Kế thừa cho phép class con tái sử dụng và mở rộng class cha.

```cpp
class Animal {
public:
    void eat();
};

class Dog : public Animal {
public:
    void bark();
};
```

Không nên lạm dụng kế thừa. Nếu quan hệ không phải "is-a", hãy cân nhắc composition.

## 6. Đa hình và virtual

Đa hình cho phép gọi hàm đúng theo kiểu đối tượng thật tại runtime.

```cpp
class Shape {
public:
    virtual double area() const = 0;
    virtual ~Shape() = default;
};
```

Class có hàm virtual nên có destructor virtual.

## 7. Composition

Composition là class này chứa class khác.

```cpp
class Engine {};

class Car {
private:
    Engine engine;
};
```

Trong thực tế, composition thường dễ bảo trì hơn kế thừa.

## 8. Lỗi thường gặp

- Cho field public quá nhiều.
- Quên destructor virtual trong base class có virtual method.
- Dùng kế thừa khi composition phù hợp hơn.
- Method getter/setter quá máy móc mà không bảo vệ quy tắc dữ liệu.

---

# Ví dụ thực hành

# Ví dụ chương 7

## 1. Class tài khoản ngân hàng

```cpp
#include <iostream>
#include <string>

class BankAccount {
public:
    BankAccount(std::string owner, double initialBalance)
        : owner(owner), balance(initialBalance) {}

    void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    bool withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    double getBalance() const {
        return balance;
    }

    void print() const {
        std::cout << owner << ": " << balance << "\n";
    }

private:
    std::string owner;
    double balance;
};

int main() {
    BankAccount account("An", 1000);
    account.deposit(500);
    account.withdraw(200);
    account.print();
    return 0;
}
```

## 2. Đa hình với Shape

```cpp
#include <cmath>
#include <iostream>
#include <memory>
#include <vector>

class Shape {
public:
    virtual double area() const = 0;
    virtual ~Shape() = default;
};

class Circle : public Shape {
public:
    explicit Circle(double radius) : radius(radius) {}

    double area() const override {
        return 3.1415926535 * radius * radius;
    }

private:
    double radius;
};

class Rectangle : public Shape {
public:
    Rectangle(double width, double height) : width(width), height(height) {}

    double area() const override {
        return width * height;
    }

private:
    double width;
    double height;
};

int main() {
    std::vector<std::unique_ptr<Shape>> shapes;
    shapes.push_back(std::make_unique<Circle>(2.0));
    shapes.push_back(std::make_unique<Rectangle>(3.0, 4.0));

    for (const auto& shape : shapes) {
        std::cout << shape->area() << "\n";
    }

    return 0;
}
```

## 3. Bài tập

1. Viết class `Student` có name, age, gpa và hàm xếp loại.
2. Viết class `Fraction` hỗ trợ cộng, trừ, nhân, chia phân số.
3. Tạo base class `Employee`, class con `FullTimeEmployee`, `PartTimeEmployee`, mỗi class có cách tính lương riêng.
4. Viết class `TodoList` dùng `std::vector<std::string>` để thêm, xóa, in công việc.
