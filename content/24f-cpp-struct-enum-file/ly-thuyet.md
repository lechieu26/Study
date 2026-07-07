# Chương 6: Struct, enum và file

## 1. Struct

`struct` gom nhiều trường dữ liệu liên quan thành một kiểu mới.

```cpp
struct Student {
    std::string name;
    int age;
    double gpa;
};
```

Dùng struct khi một đối tượng có nhiều thông tin đi kèm nhau.

## 2. Truy cập thành viên

```cpp
Student s;
s.name = "An";
s.age = 20;
s.gpa = 8.5;
```

Nếu có con trỏ tới struct, dùng `->`:

```cpp
Student* p = &s;
p->age = 21;
```

## 3. Truyền struct vào hàm

Nếu struct nhỏ, truyền tham trị cũng được. Nếu struct lớn, nên dùng `const &`.

```cpp
void printStudent(const Student& s) {
    std::cout << s.name << "\n";
}
```

## 4. Enum

`enum` tạo tập giá trị có tên.

```cpp
enum class Role {
    Admin,
    User,
    Guest
};
```

Nên dùng `enum class` thay vì `enum` cũ vì an toàn hơn, tránh trùng tên.

## 5. Đọc ghi file trong C++

Thư viện:

```cpp
#include <fstream>
```

Ghi file:

```cpp
std::ofstream out("data.txt");
out << "Hello\n";
```

Đọc file:

```cpp
std::ifstream in("data.txt");
std::string line;
std::getline(in, line);
```

Luôn kiểm tra mở file thành công:

```cpp
if (!in) {
    std::cout << "Không mở được file\n";
}
```

## 6. Lỗi thường gặp

- Quên include `<fstream>`.
- Không kiểm tra file có mở được không.
- Ghi dữ liệu không có dấu phân tách, làm khó đọc lại.
- Dùng `enum` cũ khi `enum class` phù hợp hơn.

---

# Ví dụ thực hành

# Ví dụ chương 6

## 1. Quản lý sinh viên bằng struct

```cpp
#include <iostream>
#include <string>
#include <vector>

struct Student {
    std::string name;
    int age;
    double gpa;
};

void printStudent(const Student& student) {
    std::cout << student.name << " - " << student.age << " - " << student.gpa << "\n";
}

int main() {
    std::vector<Student> students = {
        {"An", 20, 8.5},
        {"Bình", 21, 7.8},
        {"Chi", 19, 9.1}
    };

    for (const Student& student : students) {
        printStudent(student);
    }

    return 0;
}
```

## 2. Enum class

```cpp
#include <iostream>

enum class OrderStatus {
    Pending,
    Paid,
    Shipped,
    Cancelled
};

void printStatus(OrderStatus status) {
    switch (status) {
    case OrderStatus::Pending:
        std::cout << "Đang chờ\n";
        break;
    case OrderStatus::Paid:
        std::cout << "Đã thanh toán\n";
        break;
    case OrderStatus::Shipped:
        std::cout << "Đã giao\n";
        break;
    case OrderStatus::Cancelled:
        std::cout << "Đã hủy\n";
        break;
    }
}

int main() {
    printStatus(OrderStatus::Paid);
    return 0;
}
```

## 3. Ghi và đọc file

```cpp
#include <fstream>
#include <iostream>
#include <string>

int main() {
    {
        std::ofstream out("notes.txt");
        if (!out) {
            std::cout << "Không tạo được file\n";
            return 1;
        }
        out << "Dòng 1\n";
        out << "Dòng 2\n";
    }

    std::ifstream in("notes.txt");
    if (!in) {
        std::cout << "Không mở được file\n";
        return 1;
    }

    std::string line;
    while (std::getline(in, line)) {
        std::cout << line << "\n";
    }

    return 0;
}
```

## 4. Bài tập

1. Tạo struct `Book` gồm title, author, year. In danh sách sách.
2. Đọc danh sách số nguyên từ file, tính tổng.
3. Ghi danh sách sinh viên ra file CSV đơn giản.
4. Tạo enum class `Direction` gồm Up, Down, Left, Right và viết hàm in ra hướng.
