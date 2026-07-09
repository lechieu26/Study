# 02. Namespace, Template, Exception Handling

## Namespace

`namespace` gom nhóm tên biến, hàm, class để tránh trùng tên.

```cpp
#include <iostream>
using namespace std;

namespace Math {
    int square(int x) {
        return x * x;
    }
}

namespace Text {
    string square(const string& s) {
        return "[" + s + "]";
    }
}

int main() {
    cout << Math::square(5) << "\n";     // 25
    cout << Text::square("hi") << "\n"; // [hi]
}
```

Khuyến nghị:

- Trong file `.cpp`, có thể dùng `using std::cout;`.
- Trong header `.h/.hpp`, tránh `using namespace std;` vì làm ô nhiễm namespace của file include.
- Namespace có thể lồng nhau:

```cpp
namespace app::model {
    class User {};
}
```

## Template

Template giúp viết code tổng quát cho nhiều kiểu dữ liệu. Compiler sinh code cụ thể khi template được dùng.

### Function template

```cpp
template <typename T>
T maxValue(T a, T b) {
    return (a > b) ? a : b;
}

int main() {
    cout << maxValue(3, 7) << "\n";
    cout << maxValue(2.5, 1.2) << "\n";
}
```

### Class template

```cpp
template <typename T>
class Box {
private:
    T value;

public:
    Box(T v) : value(v) {}
    T get() const { return value; }
};

int main() {
    Box<int> a(10);
    Box<string> b("hello");
}
```

### Template specialization

Tùy biến hành vi riêng cho một kiểu.

```cpp
template <typename T>
struct TypeName {
    static string name() { return "unknown"; }
};

template <>
struct TypeName<int> {
    static string name() { return "int"; }
};
```

## Exception Handling

Exception dùng để xử lý lỗi bất thường mà không làm trộn logic chính.

```cpp
#include <iostream>
#include <stdexcept>
using namespace std;

double divide(double a, double b) {
    if (b == 0) {
        throw invalid_argument("division by zero");
    }
    return a / b;
}

int main() {
    try {
        cout << divide(10, 0);
    } catch (const invalid_argument& e) {
        cout << "Invalid input: " << e.what();
    } catch (const exception& e) {
        cout << "Error: " << e.what();
    }
}
```

Ghi nhớ:

- `throw`: ném lỗi.
- `try`: khối code có thể phát sinh lỗi.
- `catch`: bắt và xử lý lỗi.
- Bắt exception bằng `const std::exception&` để tránh object slicing.
- Không nên throw string literal hoặc int trong code C++ hiện đại; nên throw class kế thừa `std::exception`.

## RAII và exception safety

RAII: Resource Acquisition Is Initialization (Thu nhận tài nguyên là khởi tạo). Tài nguyên được quản lý bởi object, destructor tự động giải phóng kể cả khi có exception.

```cpp
#include <fstream>
#include <string>
using namespace std;

void writeLog(const string& msg) {
    ofstream file("app.log"); // tự close khi ra khỏi scope
    if (!file) throw runtime_error("cannot open log file");
    file << msg;
}
```

Quy tắc thực tế:

- Dùng smart pointer thay vì `new/delete` thủ công.
- Destructor không nên throw exception.
- Khi có nhiều resource, để object quản lý từng resource riêng.

## Namespace chi tiết hơn

### Name lookup và qualified name

Khi gọi `foo()`, compiler sẽ tìm tên theo scope hiện tại, scope cha, namespace liên quan và một số rule như ADL. Khi dùng `A::foo()`, bạn chỉ rõ namespace/class nên tránh mơ hồ.

```cpp
namespace A {
    void log() {}
}

namespace B {
    void log() {}
}

int main() {
    A::log(); // rõ ràng
    B::log();
}
```

Trong project lớn, namespace giúp tránh trùng tên giữa module, thư viện hoặc team khác nhau.

### Anonymous namespace

Anonymous namespace giới hạn symbol trong file hiện tại, thường thay cho `static` file-scope trong C++.

```cpp
namespace {
    int helper() {
        return 42;
    }
}
```

`helper` chỉ dùng được trong file `.cpp` đó. Đây là cách giảm rò rỉ implementation detail ra linker.

### Namespace alias

Khi namespace quá dài, có thể tạo alias:

```cpp
namespace fs = std::filesystem;
```

Trong interview, nói thêm rằng alias nên đặt ở scope hẹp, tránh làm code khó đọc.

## Template chi tiết hơn

### Template là compile-time mechanism

Template không phải "generic runtime" như một số ngôn ngữ khác. Compiler sinh ra phiên bản cụ thể khi template được dùng.

```cpp
maxValue<int>(1, 2);
maxValue<double>(1.5, 2.5);
```

Về mặt ý tưởng, compiler có thể tạo hai hàm khác nhau: một cho `int`, một cho `double`. Vì vậy template rất nhanh ở runtime nhưng có thể làm tăng thời gian compile và kích thước binary.

### Template type deduction

Compiler suy luận kiểu từ argument:

```cpp
template <typename T>
void print(const T& value) {}

print(10);       // T = int
print("hello");  // T = char[6] hoặc const char* tùy signature
```

Cần cẩn thận với reference, const và array decay. Đây là lý do nhiều code hiện đại dùng `auto`, `decltype`, forwarding reference.

### Non-type template parameter

Template không chỉ nhận type, còn có thể nhận value compile-time.

```cpp
template <typename T, int Size>
class Array {
private:
    T data[Size];
};

Array<int, 10> a;
```

Điểm hay: `Size` biết tại compile time, có thể tối ưu tốt. Điểm hạn chế: `Array<int, 10>` và `Array<int, 20>` là hai kiểu khác nhau.

### Template specialization vs overload

Specialization dùng khi muốn hành vi riêng cho type cụ thể. Nhưng với function template, overload thường dễ đọc hơn specialization.

```cpp
template <typename T>
void printValue(const T& value) {
    cout << value;
}

void printValue(const char* value) {
    cout << "\"" << value << "\"";
}
```

### SFINAE và concepts

SFINAE là rule cũ: nếu substitution template thất bại trong một số ngữ cảnh, compiler bỏ overload đó thay vì báo lỗi ngay.

C++20 concepts giúp diễn đạt constraint rõ hơn:

```cpp
#include <concepts>

template <std::integral T>
T twice(T x) {
    return x * 2;
}
```

Câu trả lời interview: Trước C++20 hay dùng SFINAE/`enable_if`; từ C++20 concepts rõ ràng hơn và báo lỗi dễ hiểu hơn.

## Exception handling chi tiết hơn

### Exception propagation và stack unwinding

Khi `throw`, chương trình nhảy ra khỏi scope hiện tại để tìm `catch` phù hợp. Trong quá trình đó, destructor của object local đã tạo sẽ được gọi. Quá trình này gọi là stack unwinding.

```cpp
struct Guard {
    ~Guard() { cout << "cleanup\n"; }
};

void f() {
    Guard g;
    throw runtime_error("fail");
} // g vẫn được hủy khi exception bay ra
```

Đây là lý do RAII cực kỳ quan trọng trong C++.

### Exception safety guarantees

Khi phỏng vấn senior hơn, người ta có thể hỏi exception safety:

| Guarantee | Ý nghĩa |
|---|---|
| No-throw | Hàm cam kết không throw |
| Strong guarantee | Nếu lỗi, state quay về như trước khi gọi |
| Basic guarantee | Nếu lỗi, object vẫn hợp lệ, không leak |
| No guarantee | Có thể hỏng state/leak |

Ví dụ assignment operator tốt thường tạo resource mới trước, sau đó mới thay resource cũ để đạt strong guarantee.

### Khi nào không dùng exception?

Không phải lỗi nào cũng nên throw:

- Input validation bình thường có thể trả `bool`, `optional`, error code.
- Code realtime/embedded có thể hạn chế exception vì chi phí và rule hệ thống.
- Destructor không nên throw vì nếu đang stack unwinding mà destructor throw tiếp, chương trình có thể `std::terminate`.

### Custom exception

```cpp
class ConfigError : public std::runtime_error {
public:
    explicit ConfigError(const string& msg)
        : runtime_error(msg) {}
};
```

Nên kế thừa từ `std::exception` hoặc các class con như `runtime_error`, `logic_error` để catch thống nhất.

## Câu hỏi bẫy thường gặp

1. Vì sao không `catch (exception e)`?

Vì copy by value gây object slicing. Nên `catch (const std::exception& e)`.

2. Header có nên `using namespace std;` không?

Không nên, vì mọi file include header đó bị kéo `std` vào global lookup, dễ conflict.

3. Template code thường để trong header vì sao?

Compiler cần thấy định nghĩa template tại nơi instantiate. Nếu chỉ có declaration trong header và definition trong `.cpp`, linker có thể không tìm thấy instantiation phù hợp.
