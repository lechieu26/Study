# 06. C/C++ Compilation Process and C++ Standards

## Quá trình biên dịch C/C++

Từ source code đến executable (file thực thi) thường qua 4 bước:

1. Preprocessing (Tiền xử lý)
2. Compilation (Biên dịch)
3. Assembly (Dịch mã máy)
4. Linking (Liên kết)

## 1. Preprocessing

Xử lý directive (chỉ thị) bắt đầu bằng `#`:

- `#include`
- `#define`
- `#if`, `#ifdef`, `#ifndef`

```cpp
#include <iostream>
#define PI 3.14
```

Lệnh xem output preprocess:

```powershell
g++ -E main.cpp -o main.i
```

## 2. Compilation

Biên dịch code C++ đã preprocess thành assembly.

```powershell
g++ -S main.i -o main.s
```

Compiler kiểm tra syntax (cú pháp), type (kiểu dữ liệu), template instantiation (khởi tạo template), optimization (tối ưu hóa).

## 3. Assembly

Biên dịch assembly thành object file `.o`/`.obj`.

```powershell
g++ -c main.s -o main.o
```

## 4. Linking

Nối các object file và library (thư viện) thành executable.

```powershell
g++ main.o helper.o -o app
```

Lỗi hay gặp:

- Compile error: sai syntax/type.
- Linker error: khai báo có nhưng không có định nghĩa, thiếu library.
- Runtime error: chương trình chạy mới lỗi.

Ví dụ linker error:

```cpp
// header
void foo();

int main() {
    foo(); // compile được, link lỗi nếu không có định nghĩa foo()
}
```

## Header guard

Tránh include trùng lặp.

```cpp
#ifndef USER_H
#define USER_H

class User {};

#endif
```

Hoặc:

```cpp
#pragma once
```

## C++11

C++11 là mốc rất lớn, đưa C++ vào phong cách hiện đại.

Tính năng trong danh sách ôn tập:

- `auto`: compiler suy diễn kiểu.
- `nullptr`: con trỏ null an toàn hơn `NULL`.
- Lambda.
- Smart pointer: `std::unique_ptr`, `std::shared_ptr`, `std::weak_ptr`.
- Move semantics: `T&&`, `std::move`.
- Range-based for (vòng lặp for dựa trên khoảng).
- `enum class`.
- `thread`, `mutex`.
- Unordered containers: `unordered_map`, `unordered_set`, `unordered_multimap`, `unordered_multiset`.

```cpp
auto x = 10;
int* p = nullptr;

auto add = [](int a, int b) {
    return a + b;
};
```

Smart pointer:

```cpp
auto p = std::make_shared<int>(42);
```

Lưu ý: `make_unique` chính thức có từ C++14, nhưng `unique_ptr` có từ C++11.

## C++14

C++14 là bản cải tiến nhỏ, làm C++11 dễ dùng hơn.

- Generic lambda: `auto` trong tham số lambda.
- `std::make_unique`.
- Return type deduction (suy diễn kiểu trả về) cho function.
- Variable templates.

```cpp
auto square = [](auto x) {
    return x * x;
};

auto p = std::make_unique<int>(10);
```

## C++17

C++17 thêm nhiều tính năng rất hay dùng trong code hiện đại.

- Structured bindings (ràng buộc cấu trúc).
- `if constexpr`.
- `std::optional`, `std::variant`, `std::any`.
- `std::filesystem`.
- Inline variables.
- `string_view`.

```cpp
std::map<std::string, int> ages = {{"An", 20}};

for (const auto& [name, age] : ages) {
    std::cout << name << ": " << age << "\n";
}
```

`if constexpr`:

```cpp
template <typename T>
void printTypeInfo(T value) {
    if constexpr (std::is_integral_v<T>) {
        std::cout << "integer\n";
    } else {
        std::cout << "not integer\n";
    }
}
```

## C++20

C++20 là bước nhảy lớn tiếp theo.

- Concepts.
- Ranges.
- Coroutines.
- Modules.
- `std::span`.
- `std::format` (tùy compiler/library hỗ trợ khác nhau).
- Three-way comparison (so sánh 3 chiều) `<=>`.
- `std::jthread`, semaphore, latch, barrier.

Concept:

```cpp
#include <concepts>

template <std::integral T>
T add(T a, T b) {
    return a + b;
}
```

Ranges:

```cpp
#include <ranges>
#include <vector>

std::vector<int> v = {1, 2, 3, 4, 5};
for (int x : v | std::views::filter([](int n) { return n % 2 == 0; })) {
    std::cout << x << " ";
}
```

## Bảng so sánh nhanh

| Standard | Điểm nổi bật |
|---|---|
| C++11 | `auto`, lambda, `nullptr`, smart pointer, move, thread, unordered containers |
| C++14 | generic lambda, `make_unique`, cải tiến constexpr/return deduction |
| C++17 | structured binding, optional/variant/any, filesystem, if constexpr |
| C++20 | concepts, ranges, modules, coroutine, span, jthread, semaphore |

## Translation unit là gì?

Một translation unit là kết quả sau khi preprocessor xử lý một file `.cpp` cùng toàn bộ header được `#include` vào nó.

Ví dụ `main.cpp` include `user.h`, `user.h` include `address.h`. Sau preprocessing, compiler nhìn thấy một file lớn gồm cả ba phần. Đó là lý do:

- Header càng nặng, compile càng lâu.
- Thay đổi header có thể khiến nhiều `.cpp` phải compile lại.
- Template thường đặt trong header vì compiler cần thấy definition khi instantiate.

## Declaration vs definition

Declaration nói với compiler "thứ này tồn tại". Definition cung cấp implementation hoặc storage thật.

```cpp
// declaration
int add(int a, int b);

// definition
int add(int a, int b) {
    return a + b;
}
```

Với biến global:

```cpp
extern int count; // declaration
int count = 0;    // definition
```

Nếu có nhiều definition cho cùng một symbol trong nhiều translation unit, có thể vi phạm One Definition Rule.

## One Definition Rule (ODR)

ODR nói rằng một entity như function/class/variable thường chỉ được có một definition hợp lệ trong program, trừ một số ngoại lệ như inline function/template.

Ví dụ lỗi:

```cpp
// helper.h
int add(int a, int b) {
    return a + b;
}
```

Nếu header này include vào nhiều `.cpp`, linker có thể báo multiple definition. Cách sửa:

```cpp
inline int add(int a, int b) {
    return a + b;
}
```

Hoặc chỉ declaration trong header, definition trong một `.cpp`.

## Static library vs dynamic library

| Loại | Windows | Linux | Ý nghĩa |
|---|---|---|---|
| Static library | `.lib` | `.a` | code được copy vào executable lúc link |
| Dynamic/shared library | `.dll` | `.so` | load lúc chạy hoặc lúc start process |

Static link dễ deploy hơn nhưng binary lớn. Dynamic link giúp chia sẻ library và update độc lập, nhưng phải quản lý version/path.

## Name mangling và `extern "C"`

C++ hỗ trợ function overloading nên compiler mã hóa tên hàm kèm kiểu tham số trong symbol, gọi là name mangling.

```cpp
int add(int, int);
double add(double, double);
```

Hai hàm này cần symbol khác nhau. Khi muốn export API cho C hoặc dùng từ C, dùng `extern "C"` để tắt C++ name mangling.

```cpp
extern "C" int add(int a, int b);
```

## Lỗi compile, link, runtime phân biệt thế nào?

### Compile error

Compiler chưa tạo được object file do sai syntax/type/template.

```cpp
int x = "hello"; // type error
```

### Linker error

Mỗi file compile riêng thành công, nhưng khi nối lại thiếu symbol hoặc trùng symbol.

```cpp
void run();

int main() {
    run(); // nếu không có definition run thì link lỗi
}
```

### Runtime error

Build thành công, chạy mới lỗi.

```cpp
int* p = nullptr;
cout << *p; // runtime crash/undefined behavior
```

## Build modes: debug vs release

Debug build thường:

- Ít tối ưu hoặc không tối ưu.
- Có debug symbols.
- Dễ debug từng dòng.

Release build thường:

- Bật optimization.
- Có thể inline/reorder code.
- Bug undefined behavior có thể biểu hiện khác debug.

Câu trả lời hay: Nếu bug chỉ xảy ra ở release, nghi ngờ UB, race condition, uninitialized memory, hoặc code phụ thuộc timing.

## C++ standard nên chọn thế nào?

Trong dự án mới, nếu toolchain ổn, C++17 thường là baseline tốt vì có `optional`, `variant`, structured binding, filesystem. C++20 đáng dùng nếu compiler/library hỗ trợ ổn và team cần concepts/ranges/coroutines.

Khi phỏng vấn, không cần thuộc mọi feature. Nên nắm:

- C++11: modern C++ bắt đầu: move, lambda, smart pointer, thread.
- C++14: làm C++11 dễ dùng hơn.
- C++17: tiện ích thực chiến.
- C++20: constraint/ranges/concurrency hiện đại.

## Câu hỏi bẫy thường gặp

1. `#include` khác linking thế nào?

`#include` là preprocessing: copy text header vào translation unit. Linking là nối object files/libraries sau compile.

2. Vì sao thiếu include có thể vẫn compile trên máy này nhưng fail máy khác?

Do transitive include: một header khác tình cờ include hộ. Không nên phụ thuộc vào đó; dùng gì include nấy.

3. `#pragma once` có thay thế header guard hoàn toàn không?

Hầu hết compiler hỗ trợ tốt, ngắn gọn. Header guard là chuẩn portable truyền thống.
