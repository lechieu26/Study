# Chương 2: Cú pháp cơ bản

## 1. Biến

Biến là vùng nhớ có tên, dùng để lưu dữ liệu.

```cpp
int age = 20;
double salary = 1500.5;
char grade = 'A';
bool passed = true;
```

Đặt tên biến nên rõ nghĩa:

```cpp
int numberOfStudents = 35;
```

Không nên:

```cpp
int n = 35; // nếu không rõ n là gì
```

## 2. Kiểu dữ liệu cơ bản

- `int`: số nguyên.
- `long long`: số nguyên lớn.
- `float`: số thực độ chính xác đơn.
- `double`: số thực độ chính xác kép, hay dùng hơn `float`.
- `char`: một ký tự.
- `bool`: đúng/sai.
- `std::string`: chuỗi trong C++.

## 3. Hằng số

Dùng `const` khi giá trị không nên thay đổi.

```cpp
const double PI = 3.1415926535;
```

Lỗi thường gặp: dùng biến thường cho giá trị có ý nghĩa cố định, làm code khó hiểu và dễ sửa nhầm.

## 4. Nhập xuất trong C++

```cpp
std::cin >> age;
std::cout << "Tuổi: " << age << "\n";
```

`>>` đọc dữ liệu từ bàn phím. `<<` đưa dữ liệu ra màn hình.

Khi đọc chuỗi có khoảng trắng, dùng `std::getline`.

## 5. Toán tử

Toán tử số học:

- `+`, `-`, `*`, `/`, `%`.

Toán tử so sánh:

- `==`, `!=`, `<`, `>`, `<=`, `>=`.

Toán tử logic:

- `&&`: và.
- `||`: hoặc.
- `!`: phủ định.

## 6. Ép kiểu

Trong C++, nên dùng `static_cast`.

```cpp
int a = 5;
int b = 2;
double result = static_cast<double>(a) / b;
```

Nếu không ép kiểu, `5 / 2` cho kết quả `2`, không phải `2.5`, vì cả hai là số nguyên.

## 7. Lỗi thường gặp

- Dùng `=` thay vì `==` khi so sánh.
- Chia số nguyên nhưng mong kết quả số thực.
- Đọc `std::getline` ngay sau `std::cin >>` mà không xử lý ký tự xuống dòng còn lại.
- Không khởi tạo biến trước khi dùng.

---

# Ví dụ thực hành

# Ví dụ chương 2

## 1. Tính diện tích hình tròn

```cpp
#include <iostream>

int main() {
    const double PI = 3.1415926535;
    double radius;

    std::cout << "Nhập bán kính: ";
    std::cin >> radius;

    double area = PI * radius * radius;
    std::cout << "Diện tích = " << area << "\n";
    return 0;
}
```

## 2. Chia số nguyên và số thực

```cpp
#include <iostream>

int main() {
    int a = 5;
    int b = 2;

    std::cout << "Chia nguyên: " << a / b << "\n";
    std::cout << "Chia thực: " << static_cast<double>(a) / b << "\n";
    return 0;
}
```

## 3. Nhập chuỗi có khoảng trắng

```cpp
#include <iostream>
#include <limits>
#include <string>

int main() {
    int age;
    std::string fullName;

    std::cout << "Nhập tuổi: ";
    std::cin >> age;

    std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');

    std::cout << "Nhập họ tên: ";
    std::getline(std::cin, fullName);

    std::cout << fullName << " - " << age << " tuổi\n";
    return 0;
}
```

## 4. Bài tập

1. Nhập chiều dài và chiều rộng, tính chu vi và diện tích hình chữ nhật.
2. Nhập điểm toán, lý, hóa, tính điểm trung bình.
3. Nhập số giây, đổi sang giờ, phút, giây.
