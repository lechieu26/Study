# Chương 4: Hàm và phạm vi

## 1. Hàm là gì?

Hàm là khối lệnh có tên, nhận đầu vào, xử lý và có thể trả về kết quả.

```cpp
int add(int a, int b) {
    return a + b;
}
```

Lợi ích:

- Tái sử dụng code.
- Chia chương trình lớn thành phần nhỏ.
- Dễ test và debug.
- Đặt tên hàm giúp code tự giải thích.

## 2. Khai báo và định nghĩa hàm

Định nghĩa hàm:

```cpp
int square(int x) {
    return x * x;
}
```

Khai báo trước, định nghĩa sau:

```cpp
int square(int x);

int main() {
    return square(5);
}

int square(int x) {
    return x * x;
}
```

## 3. Truyền tham trị

Mặc định C++ truyền đối số theo giá trị. Hàm nhận bản sao.

```cpp
void change(int x) {
    x = 100;
}
```

Biến bên ngoài không bị thay đổi.

## 4. Truyền tham chiếu

Dùng `&` để hàm thay đổi biến gốc.

```cpp
void increase(int& x) {
    ++x;
}
```

Dùng `const &` khi muốn tránh copy dữ liệu lớn nhưng không cho sửa.

```cpp
void printName(const std::string& name) {
    std::cout << name << "\n";
}
```

## 5. Giá trị trả về

Hàm có thể trả về một giá trị bằng `return`.

```cpp
bool isEven(int n) {
    return n % 2 == 0;
}
```

Hàm `void` không trả về giá trị.

## 6. Phạm vi biến

Biến khai báo trong block `{}` chỉ sống trong block đó.

```cpp
if (true) {
    int x = 10;
}
// x không tồn tại ở đây
```

Biến toàn cục tồn tại trong cả chương trình nhưng nên hạn chế, vì làm code khó kiểm soát.

## 7. Đệ quy

Hàm đệ quy là hàm gọi lại chính nó.

Cần có:

- Điều kiện dừng.
- Bước tiến gần đến điều kiện dừng.

```cpp
int factorial(int n) {
    if (n <= 1) {
        return 1;
    }
    return n * factorial(n - 1);
}
```

## 8. Lỗi thường gặp

- Quên `return` trong hàm có kiểu trả về.
- Truyền tham trị nhưng mong biến gốc thay đổi.
- Đệ quy không có điều kiện dừng.
- Đặt hàm quá dài, làm quá nhiều việc.

---

# Ví dụ thực hành

# Ví dụ chương 4

## 1. Tách logic thành hàm

```cpp
#include <iostream>

bool isPrime(int n) {
    if (n < 2) {
        return false;
    }

    for (int i = 2; i * i <= n; ++i) {
        if (n % i == 0) {
            return false;
        }
    }

    return true;
}

int main() {
    int n;
    std::cin >> n;

    if (isPrime(n)) {
        std::cout << "Là số nguyên tố\n";
    } else {
        std::cout << "Không phải số nguyên tố\n";
    }

    return 0;
}
```

## 2. Swap bằng tham chiếu

```cpp
#include <iostream>

void swapValues(int& a, int& b) {
    int temp = a;
    a = b;
    b = temp;
}

int main() {
    int x = 3;
    int y = 7;

    swapValues(x, y);
    std::cout << x << " " << y << "\n";
    return 0;
}
```

## 3. Đệ quy Fibonacci

```cpp
#include <iostream>

long long fibonacci(int n) {
    if (n <= 1) {
        return n;
    }
    return fibonacci(n - 1) + fibonacci(n - 2);
}

int main() {
    std::cout << fibonacci(10) << "\n";
    return 0;
}
```

Ghi chú: cách đệ quy trên dễ hiểu nhưng chậm với `n` lớn. Sau này nên dùng vòng lặp hoặc dynamic programming.

## 4. Bài tập

1. Viết hàm tính tổng các chữ số của một số nguyên.
2. Viết hàm kiểm tra năm nhuận.
3. Viết hàm tìm ước chung lớn nhất bằng thuật toán Euclid.
4. Viết hàm đệ quy tính `a^b` với `b >= 0`.
