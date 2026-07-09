# 05. Struct, Padding, Keywords

## struct trong C và C++

Trong C, `struct` chủ yếu gồm data. Trong C++, `struct` có thể có method, constructor, inheritance như `class`.

Khác biệt mặc định trong C++:

- `struct`: member mặc định là `public`.
- `class`: member mặc định là `private`.

```cpp
struct Point {
    int x;
    int y;

    void move(int dx, int dy) {
        x += dx;
        y += dy;
    }
};
```

## Tính sizeof struct và padding

Compiler chèn byte trống giữa các member để đảm bảo alignment (căn chỉnh bộ nhớ). Alignment giúp CPU đọc dữ liệu hiệu quả hơn.

Ví dụ:

```cpp
#include <iostream>
using namespace std;

struct A {
    char c;   // 1 byte
    int i;    // 4 bytes
};

struct B {
    int i;    // 4 bytes
    char c;   // 1 byte
};

int main() {
    cout << sizeof(A) << "\n";
    cout << sizeof(B) << "\n";
}
```

Trên nhiều hệ 64-bit:

- `sizeof(A)` thường là 8: `char` 1 byte + 3 padding + `int` 4 byte.
- `sizeof(B)` thường là 8: `int` 4 byte + `char` 1 byte + 3 tail padding để size là bội số của alignment lớn nhất.

Ví dụ tối ưu thứ tự member:

```cpp
struct Bad {
    char a;
    double b;
    int c;
}; // thường 24 bytes

struct Good {
    double b;
    int c;
    char a;
}; // thường 16 bytes
```

Quy tắc nhanh:

- Member có alignment thường bằng kích thước kiểu dữ liệu, tùy thuộc ABI/compiler.
- Địa chỉ mỗi member phải nằm ở offset phù hợp alignment.
- Size của struct thường là bội số của alignment lớn nhất trong struct.
- Sắp xếp member từ lớn đến nhỏ có thể giảm padding.

## Packing

Packing ép compiler bỏ/giảm padding, thường dùng khi đọc binary protocol/file format. Cần cẩn thận vì truy cập misaligned (lệch alignment) có thể chậm hoặc lỗi trên một số kiến trúc.

```cpp
#pragma pack(push, 1)
struct PackedHeader {
    char type;
    int length;
};
#pragma pack(pop)
```

## static keyword

1. Local static: biến sống đến hết chương trình, chỉ khởi tạo một lần.

```cpp
int nextId() {
    static int id = 0;
    return ++id;
}
```

2. Class static member: thuộc class, dùng chung cho mọi object.

```cpp
class User {
public:
    static int count;
    User() { ++count; }
};

int User::count = 0;
```

3. File-scope static trong C/C++: giới hạn linkage (liên kết) trong file hiện tại.

```cpp
static int helperValue = 10;
```

## final keyword

```cpp
class Base {
public:
    virtual void run() final {}
};

class Service final {};
```

- `final` trên method: class con không override tiếp.
- `final` trên class: không cho kế thừa.

## friend keyword

```cpp
class Secret {
private:
    int value = 7;

    friend class Inspector;
};

class Inspector {
public:
    int read(const Secret& s) {
        return s.value;
    }
};
```

Dùng khi cần operator overload hoặc class helper truy cập nội bộ. Không nên lạm dụng.

## register keyword

`register` là gợi ý cũ cho compiler đặt biến vào CPU register (thanh ghi CPU).

```cpp
register int i = 0;
```

Trong C++ hiện đại:

- Compiler tự tối ưu tốt hơn.
- `register` không còn giá trị thực tế đáng kể.
- C++17 loại bỏ ý nghĩa sử dụng thông thường của `register`, nên tránh dùng trong code mới.

## Cách tính padding từng bước

Ví dụ:

```cpp
struct Example {
    char a;   // alignment 1
    int b;    // alignment 4
    short c;  // alignment 2
};
```

Giả sử `char = 1`, `short = 2`, `int = 4`, alignment lớn nhất là 4.

Layout thường là:

| Member | Offset | Size | Ghi chú |
|---|---:|---:|---|
| `a` | 0 | 1 | đặt ngay đầu |
| padding | 1-3 | 3 | để `b` bắt đầu tại offset chia hết cho 4 |
| `b` | 4 | 4 | offset 4 hợp lệ |
| `c` | 8 | 2 | offset 8 chia hết cho 2 |
| tail padding | 10-11 | 2 | tổng size phải chia hết cho alignment lớn nhất 4 |

`sizeof(Example)` thường là 12.

Nếu đổi thứ tự:

```cpp
struct Better {
    int b;
    short c;
    char a;
};
```

Layout thường là `4 + 2 + 1 + 1 tail padding = 8`.

Khi trả lời phỏng vấn, nhớ nói "thường là" vì kích thước/alignment phụ thuộc kiến trúc, ABI và compiler.

## Alignment là gì?

Alignment là yêu cầu địa chỉ của object phải chia hết cho một giá trị nhất định. Ví dụ `int` alignment 4 nghĩa là địa chỉ của `int` nên chia hết cho 4.

CPU thường đọc dữ liệu aligned nhanh hơn. Một số kiến trúc có thể xử lý misaligned chậm, hoặc thậm chí lỗi.

C++ có thể kiểm tra:

```cpp
cout << alignof(int) << "\n";
cout << alignof(double) << "\n";
cout << alignof(Example) << "\n";
```

## `struct` C vs C++ chi tiết hơn

Trong C:

```c
struct Point {
    int x;
    int y;
};

struct Point p;
```

Trong C++, có thể viết:

```cpp
Point p;
```

Trong C++ `struct` có constructor, destructor, method, operator overload, inheritance giống `class`. Khác biệt chính với `class` là default access:

- `struct`: public member và public inheritance mặc định.
- `class`: private member và private inheritance mặc định.

Vì vậy convention thường là:

- Dùng `struct` cho data object đơn giản, aggregate, DTO.
- Dùng `class` khi muốn che giấu invariant bằng private member.

## Aggregate initialization

Aggregate là kiểu có thể khởi tạo trực tiếp bằng danh sách giá trị.

```cpp
struct User {
    int id;
    string name;
};

User u{1, "An"};
```

C++20 có designated initializer:

```cpp
User u{.id = 1, .name = "An"};
```

Điểm hay: rõ field nào nhận giá trị nào. Điểm cần nhớ: thứ tự designated initializer vẫn phải theo thứ tự member trong chuẩn C++20.

## `static` chi tiết hơn

### Static local và thread-safe initialization

Từ C++11, khởi tạo local static là thread-safe.

```cpp
Logger& getLogger() {
    static Logger logger;
    return logger;
}
```

Đây là nền tảng của Meyers Singleton.

### Static data member trong class

Trước C++17, static data member thường cần definition ngoài class:

```cpp
class Counter {
public:
    static int count;
};

int Counter::count = 0;
```

C++17 có inline variable:

```cpp
class Counter {
public:
    inline static int count = 0;
};
```

## `friend` dùng đúng chỗ

`friend` không phá encapsulation nếu dùng có chủ đích. Ví dụ operator `<<` cần truy cập private để in object:

```cpp
class Point {
private:
    int x;
    int y;

public:
    Point(int x, int y) : x(x), y(y) {}

    friend ostream& operator<<(ostream& os, const Point& p);
};

ostream& operator<<(ostream& os, const Point& p) {
    return os << "(" << p.x << ", " << p.y << ")";
}
```

Nhưng nếu nhiều class/function được khai báo friend, đó có thể là dấu hiệu thiết kế đang phụ thuộc quá nhiều vào internal state.

## `final` dùng khi nào?

`final` hữu ích khi:

- Bạn muốn khóa API inheritance vì class không được thiết kế để kế thừa.
- Bạn muốn ngăn override một hàm quan trọng.
- Có thể giúp compiler tối ưu devirtualization trong một số trường hợp.

Nhưng không nên rải `final` khắp nơi nếu codebase cần extension qua inheritance.

## Câu hỏi bẫy thường gặp

1. `sizeof(struct)` có bằng tổng size member không?

Không nhất thiết, vì padding và tail padding.

2. Empty struct/class size bằng bao nhiêu trong C++?

Thường là 1 byte để mỗi object có địa chỉ riêng biệt.

```cpp
struct Empty {};
cout << sizeof(Empty); // thường 1
```

3. `#pragma pack(1)` có nên dùng để tối ưu memory không?

Không nên dùng bừa. Nó có thể làm access chậm/misaligned. Chủ yếu dùng khi cần match binary layout với file/network protocol.
