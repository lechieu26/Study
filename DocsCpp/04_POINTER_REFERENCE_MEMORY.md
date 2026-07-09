# 04. Pointer, Reference, Memory Management

## Memory layout cơ bản

Một chương trình C/C++ thường có các vùng:

- Text/code: mã lệnh chương trình.
- Data/BSS: biến global/static.
- Stack: biến local, tham số hàm, tự động hủy khi ra khỏi scope.
- Heap: cấp phát động bằng `new/malloc`, phải quản lý vòng đời.

## Pointer

Pointer (con trỏ) lưu địa chỉ của biến/object.

```cpp
#include <iostream>
using namespace std;

int main() {
    int x = 10;
    int* p = &x;

    cout << p << "\n";  // địa chỉ
    cout << *p << "\n"; // giá trị tại địa chỉ: 10

    *p = 20;
    cout << x; // 20
}
```

Ký hiệu:

- `&x`: lấy địa chỉ của `x`.
- `*p`: dereference (giải tham chiếu), lấy/sửa giá trị tại địa chỉ p trỏ tới.
- `nullptr`: con trỏ rỗng, có từ C++11, thay cho `NULL`.

## Pointer pitfalls

```cpp
int* p = nullptr;
// cout << *p; // lỗi runtime
```

Lỗi thường gặp:

- Dangling pointer (con trỏ lửng lơ): trỏ tới memory đã bị hủy.
- Memory leak (rò rỉ bộ nhớ): cấp phát mà không giải phóng.
- Double delete: xóa cùng vùng nhớ hai lần.
- Out of bounds: truy cập vượt giới hạn mảng.

## Reference

Reference (tham chiếu) là alias (bí danh) của một biến, phải khởi tạo ngay và không đổi sang object khác.

```cpp
int x = 10;
int& ref = x;
ref = 30;
cout << x; // 30
```

Dùng reference để truyền tham số tránh copy.

```cpp
void update(int& x) {
    x += 10;
}

void print(const string& s) {
    cout << s;
}
```

So sánh pointer và reference:

| Tiêu chí | Pointer | Reference |
|---|---|---|
| Có thể null | Có | Không nên/cơ bản là không |
| Có thể đổi sang object khác | Có | Không |
| Cú pháp truy cập | `*p`, `p->x` | như biến thường |
| Phù hợp | optional object, array, ownership raw API | alias bắt buộc tồn tại |

## Dynamic allocation

C style:

```cpp
int* p = (int*)malloc(sizeof(int));
*p = 10;
free(p);
```

C++ style:

```cpp
int* p = new int(10);
delete p;

int* arr = new int[5];
delete[] arr;
```

Trong C++ hiện đại, hạn chế `new/delete` trực tiếp. Ưu tiên `std::vector`, `std::string`, smart pointer.

## Smart pointer

### unique_ptr

Sở hữu độc quyền. Không copy được, chỉ move được.

```cpp
#include <memory>
using namespace std;

unique_ptr<int> p = make_unique<int>(10);
unique_ptr<int> q = move(p); // p không còn sở hữu
```

Dùng khi một object có đúng một owner.

### shared_ptr

Nhiều owner cùng sở hữu object. Object bị hủy khi reference count (số lượng tham chiếu) về 0.

```cpp
shared_ptr<int> a = make_shared<int>(100);
shared_ptr<int> b = a;
cout << a.use_count(); // 2
```

Dùng khi thực sự cần chia sẻ ownership.

### weak_ptr

Quan sát object được quản lý bởi `shared_ptr` nhưng không tăng reference count. Dùng để tránh circular reference (tham chiếu vòng).

```cpp
weak_ptr<int> w;
{
    auto s = make_shared<int>(42);
    w = s;

    if (auto locked = w.lock()) {
        cout << *locked;
    }
}
// s đã hủy, w.expired() == true
```

## Deep copy và shallow copy

Shallow copy (sao chép nông): copy địa chỉ, hai object trỏ tới cùng vùng nhớ. Dễ lỗi double delete.

```cpp
class BadArray {
public:
    int* data;
    BadArray(int n) { data = new int[n]; }
    ~BadArray() { delete[] data; }
    // copy mặc định sẽ copy pointer: nguy hiểm
};
```

Deep copy (sao chép sâu): copy cả vùng dữ liệu mới.

```cpp
class GoodArray {
private:
    int size;
    int* data;

public:
    GoodArray(int n) : size(n), data(new int[n]{}) {}

    GoodArray(const GoodArray& other)
        : size(other.size), data(new int[other.size]) {
        for (int i = 0; i < size; ++i) data[i] = other.data[i];
    }

    GoodArray& operator=(const GoodArray& other) {
        if (this == &other) return *this;

        int* newData = new int[other.size];
        for (int i = 0; i < other.size; ++i) newData[i] = other.data[i];

        delete[] data;
        data = newData;
        size = other.size;
        return *this;
    }

    ~GoodArray() {
        delete[] data;
    }
};
```

Rule of 3/5/0:

- Nếu class tự quản lý resource, cần destructor, copy constructor, copy assignment.
- C++11 thêm move constructor, move assignment thành Rule of 5.
- Tốt nhất là Rule of 0: dùng `vector`, `string`, smart pointer để không tự viết destructor/copy.

## Stack và heap chi tiết hơn

Stack thường dùng cho biến local có lifetime rõ ràng theo scope. Heap dùng cho object cần sống lâu hơn scope tạo ra, kích thước lớn, hoặc lifetime động.

```cpp
void f() {
    int x = 10;              // stack
    auto p = make_unique<int>(20); // heap, quản lý bởi unique_ptr
}
```

Điểm phỏng vấn quan trọng: stack/heap là cách nói phổ biến, nhưng chuẩn C++ nói nhiều hơn về storage duration:

- Automatic storage duration: biến local tự hủy khi ra khỏi scope.
- Static storage duration: global/static sống đến hết chương trình.
- Dynamic storage duration: cấp phát bằng `new`, `malloc`, allocator.
- Thread storage duration: `thread_local`.

## Pointer arithmetic

Pointer có thể cộng/trừ theo kích thước kiểu dữ liệu nó trỏ tới.

```cpp
int a[] = {10, 20, 30};
int* p = a;

cout << *p;       // 10
cout << *(p + 1); // 20
```

`p + 1` không tăng địa chỉ thêm 1 byte, mà tăng thêm `sizeof(int)` byte.

Cẩn thận: pointer arithmetic chỉ hợp lệ trong cùng một mảng hoặc ngay sau phần tử cuối. Vượt ra ngoài là undefined behavior.

## const với pointer

Đây là câu hỏi rất hay bị hỏi:

```cpp
const int* p1;       // pointer tới const int: không sửa *p1 qua p1
int* const p2 = &x;  // const pointer: p2 không trỏ sang chỗ khác
const int* const p3 = &x; // cả pointer và value đều const qua p3
```

Cách đọc: đọc từ phải sang trái.

```cpp
int x = 10;
int y = 20;

const int* p = &x;
p = &y;      // được
// *p = 30;  // không được

int* const q = &x;
*q = 30;     // được
// q = &y;   // không được
```

## Ownership: ai sở hữu memory?

Khi thấy pointer, hãy hỏi: pointer này có sở hữu object không?

| Loại | Ý nghĩa |
|---|---|
| Raw pointer `T*` | Có thể chỉ quan sát hoặc API C cũ, không rõ ownership |
| Reference `T&` | Alias bắt buộc tồn tại, không sở hữu |
| `unique_ptr<T>` | Sở hữu độc quyền |
| `shared_ptr<T>` | Sở hữu chia sẻ |
| `weak_ptr<T>` | Quan sát object do `shared_ptr` quản lý |

Trong code hiện đại, raw pointer thường nên dùng cho non-owning pointer. Ownership nên thể hiện bằng smart pointer.

## Move semantics

Move semantics giúp chuyển tài nguyên thay vì copy sâu tốn kém.

```cpp
class Buffer {
private:
    size_t size;
    int* data;

public:
    Buffer(size_t n) : size(n), data(new int[n]{}) {}

    Buffer(Buffer&& other) noexcept
        : size(other.size), data(other.data) {
        other.size = 0;
        other.data = nullptr;
    }

    ~Buffer() {
        delete[] data;
    }
};
```

Sau move, object nguồn vẫn phải ở trạng thái hợp lệ, nhưng giá trị cụ thể thường không nên phụ thuộc. Với `unique_ptr`, sau move pointer nguồn thường thành `nullptr`.

```cpp
auto p = make_unique<int>(10);
auto q = move(p);
// p không còn sở hữu object
```

## new/delete vs malloc/free

| Tiêu chí | `new/delete` | `malloc/free` |
|---|---|---|
| Ngôn ngữ | C++ | C |
| Constructor/destructor | Có gọi | Không gọi |
| Kiểu trả về | đúng type | `void*` |
| Khi lỗi | throw `bad_alloc` | trả `NULL` |

Không được trộn:

```cpp
int* p = new int(10);
// free(p); // sai
delete p;
```

```cpp
int* q = (int*)malloc(sizeof(int));
// delete q; // sai
free(q);
```

## Undefined behavior liên quan memory

Undefined behavior nghĩa là chuẩn C++ không quy định chuyện gì xảy ra. Chương trình có thể chạy đúng, crash, hoặc sai âm thầm.

Ví dụ:

```cpp
int* p;
cout << *p; // p chưa khởi tạo
```

```cpp
int a[3] = {1, 2, 3};
cout << a[5]; // out of bounds
```

```cpp
int* p = new int(5);
delete p;
cout << *p; // use-after-free
```

Trong interview, hãy nói rõ: không phải "luôn crash", mà là undefined behavior nên không thể dự đoán.

## Câu hỏi bẫy thường gặp

1. Reference có thể null không?

Theo thiết kế, reference phải alias một object hợp lệ. Có thể tạo reference null bằng cách dereference null pointer, nhưng đó là undefined behavior.

2. `delete nullptr` có an toàn không?

Có. `delete nullptr;` không làm gì.

3. Vì sao nên dùng `make_shared`/`make_unique`?

Ngắn gọn, exception-safe hơn, tránh viết `new` trực tiếp. `make_shared` còn có thể cấp phát control block và object trong một allocation.

4. Circular reference với `shared_ptr` là gì?

Hai object giữ `shared_ptr` tới nhau làm reference count không về 0. Dùng `weak_ptr` cho liên kết ngược hoặc quan hệ không sở hữu.
