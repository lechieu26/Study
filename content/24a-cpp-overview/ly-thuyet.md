# Chương 1: Tổng quan C/C++ và môi trường

## 1. C và C++ là gì?

C là ngôn ngữ lập trình hệ thống, gần với bộ nhớ, tốc độ nhanh, cú pháp gọn. C thường được dùng để viết hệ điều hành, driver, embedded, thư viện cần hiệu năng cao.

C++ được phát triển từ C, thêm lập trình hướng đối tượng, template, STL, RAII, generic programming và nhiều công cụ giúp viết chương trình lớn tốt hơn.

Nói ngắn gọn:

- C giúp bạn hiểu máy tính chạy chương trình như thế nào.
- C++ giúp bạn xây dựng phần mềm lớn hơn, an toàn và có cấu trúc hơn.

## 2. Chương trình C/C++ được chạy như thế nào?

Quá trình cơ bản:

1. Viết mã nguồn trong file `.c`, `.cpp`, `.h`, `.hpp`.
2. Compiler biên dịch mã nguồn thành mã máy.
3. Linker ghép các file object và thư viện.
4. Tạo file chạy được như `.exe` trên Windows.

Ví dụ:

```bash
g++ main.cpp -o main
```

`g++` là compiler C++. `main.cpp` là file nguồn. `-o main` đặt tên file kết quả.

## 3. Hàm `main`

Mỗi chương trình C/C++ chạy từ hàm `main`.

```cpp
int main() {
    return 0;
}
```

`return 0` thường có nghĩa chương trình kết thúc thành công.

## 4. Header và thư viện

Trong C++:

```cpp
#include <iostream>
```

Dòng này nói compiler rằng chương trình cần dùng thư viện nhập xuất.

Trong C:

```c
#include <stdio.h>
```

## 5. Namespace trong C++

`std` là namespace chứa nhiều thành phần của thư viện chuẩn C++ như `cout`, `cin`, `string`, `vector`.

Nên viết rõ:

```cpp
std::cout << "Hello";
```

Tránh lạm dụng:

```cpp
using namespace std;
```

Khi mới học có thể dùng cho ngắn, nhưng khi viết code lớn nên viết `std::`.

## 6. Lỗi compiler và lỗi runtime

Lỗi compiler là lỗi bị phát hiện khi biên dịch, ví dụ thiếu dấu `;`, sai tên biến.

Lỗi runtime là lỗi xảy ra khi chương trình đang chạy, ví dụ chia cho 0, truy cập mảng vượt giới hạn, con trỏ null.

## 7. Thói quen nên có từ đầu

- Đặt tên biến có nghĩa.
- Biên dịch với cảnh báo: `-Wall -Wextra -pedantic`.
- Tách bài toán thành từng hàm nhỏ.
- Đọc thông báo lỗi từ trên xuống dưới.
- Không bỏ qua warning.

---

# Ví dụ thực hành

# Ví dụ chương 1

## 1. Hello world C++

```cpp
#include <iostream>

int main() {
    std::cout << "Hello, C++!\n";
    return 0;
}
```

Biên dịch:

```bash
g++ -std=c++17 -Wall -Wextra -pedantic hello.cpp -o hello
```

## 2. Hello world C

```c
#include <stdio.h>

int main(void) {
    printf("Hello, C!\n");
    return 0;
}
```

Biên dịch:

```bash
gcc -Wall -Wextra -pedantic hello.c -o hello
```

## 3. Lỗi thiếu dấu chấm phẩy

```cpp
#include <iostream>

int main() {
    std::cout << "Sai cú pháp\n"
    return 0;
}
```

Compiler sẽ báo lỗi gần dòng `return 0`. Lỗi thật sự nằm ở dòng trước đó: thiếu `;`.

## 4. Bài tập

1. In tên của bạn ra màn hình.
2. In 3 dòng: tên, tuổi, mục tiêu học C/C++.
3. Cố tình xóa một dấu `;`, biên dịch lại và đọc thông báo lỗi.
