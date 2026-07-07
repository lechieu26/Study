# Chương 9: Quản lý bộ nhớ và hiệu năng

## 1. Stack và heap

Stack dùng cho biến cục bộ, cấp phát và thu hồi nhanh, kích thước giới hạn.

Heap dùng cho dữ liệu sống linh hoạt hơn, kích thước lớn hơn, nhưng quản lý phức tạp hơn.

```cpp
int x = 10;              // stack
int* p = new int(20);    // heap
delete p;
```

Trong C++ hiện đại, tránh `new/delete` trực tiếp nếu có thể.

## 2. RAII

RAII là Resource Acquisition Is Initialization. Ý tưởng: tài nguyên được gắn với đối tượng, khi đối tượng hết vòng đời thì destructor tự giải phóng tài nguyên.

Ví dụ:

- `std::vector` tự quản lý bộ nhớ.
- `std::string` tự quản lý chuỗi.
- `std::fstream` tự động đóng file.
- `std::unique_ptr` tự động delete.

RAII giúp code an toàn hơn khi có lỗi, return sớm hoặc exception.

## 3. Smart pointer

`std::unique_ptr`: một chủ sở hữu duy nhất.

```cpp
auto p = std::make_unique<int>(10);
```

`std::shared_ptr`: nhiều chủ cùng sở hữu, đối tượng bị hủy khi bộ đếm về 0.

```cpp
auto p = std::make_shared<int>(10);
```

`std::weak_ptr`: tham chiếu yếu, không tăng bộ đếm sở hữu, thường dùng để tránh vòng lặp shared_ptr.

## 4. Copy và move

Copy tạo bản sao. Move chuyển tài nguyên từ đối tượng này sang đối tượng khác.

```cpp
std::string a = "hello";
std::string b = std::move(a);
```

Sau move, `a` vẫn hợp lệ nhưng giá trị cụ thể không nên phụ thuộc.

## 5. Truyền tham số hiệu quả

- Kiểu nhỏ như `int`, `double`, `char`, `bool`: truyền theo giá trị.
- Đối tượng lớn như `std::string`, `std::vector`: truyền `const &` nếu chỉ đọc.
- Nếu hàm cần giữ bản sao, có thể nhận theo giá trị rồi move vào field.

## 6. Đo hiệu năng

Dùng cảm tính rất dễ sai. Nếu tối ưu, hãy đo.

Cần quan tâm:

- Độ phức tạp thuật toán.
- Số lần cấp phát bộ nhớ.
- Copy dữ liệu lớn.
- Cache locality.

## 7. Độ phức tạp Big O

Big O mô tả tốc độ tăng của thời gian/bộ nhớ theo kích thước đầu vào.

- `O(1)`: hằng số.
- `O(log n)`: logarit.
- `O(n)`: tuyến tính.
- `O(n log n)`: sắp xếp tốt thường gặp.
- `O(n^2)`: hai vòng lặp lồng nhau.

Tối ưu thuật toán thường quan trọng hơn tối ưu từng dòng nhỏ.

## 8. Lỗi thường gặp

- Dùng `shared_ptr` ở mọi nơi làm ownership không rõ.
- Copy vector/string lớn không cần thiết.
- Tối ưu sớm khi chưa có số liệu đo.
- Quên `virtual destructor` khi xóa object con qua con trỏ base.

---

# Ví dụ thực hành

# Ví dụ chương 9

## 1. Dùng unique_ptr

```cpp
#include <iostream>
#include <memory>

class Resource {
public:
    Resource() {
        std::cout << "Tạo resource\n";
    }

    ~Resource() {
        std::cout << "Hủy resource\n";
    }

    void use() const {
        std::cout << "Đang dùng resource\n";
    }
};

int main() {
    auto resource = std::make_unique<Resource>();
    resource->use();
    return 0;
}
```

Không cần gọi `delete`. Khi `resource` ra khỏi scope, destructor tự chạy.

## 2. Truyền vector bằng const reference

```cpp
#include <iostream>
#include <vector>

long long sumVector(const std::vector<int>& numbers) {
    long long sum = 0;
    for (int value : numbers) {
        sum += value;
    }
    return sum;
}

int main() {
    std::vector<int> numbers = {1, 2, 3, 4, 5};
    std::cout << sumVector(numbers) << "\n";
    return 0;
}
```

Nếu hàm nhận `std::vector<int> numbers`, toàn bộ vector bị copy.

## 3. Move vào class

```cpp
#include <iostream>
#include <string>
#include <utility>

class User {
public:
    explicit User(std::string name)
        : name(std::move(name)) {}

    void print() const {
        std::cout << name << "\n";
    }

private:
    std::string name;
};

int main() {
    std::string name = "An";
    User user(name);              // copy vào tham số, move vào field
    User user2(std::string("Bình")); // move từ temporary

    user.print();
    user2.print();
    return 0;
}
```

## 4. So sánh O(n) và O(n^2)

```cpp
#include <iostream>
#include <vector>

bool hasDuplicateSlow(const std::vector<int>& numbers) {
    for (std::size_t i = 0; i < numbers.size(); ++i) {
        for (std::size_t j = i + 1; j < numbers.size(); ++j) {
            if (numbers[i] == numbers[j]) {
                return true;
            }
        }
    }
    return false;
}

int main() {
    std::vector<int> numbers = {1, 2, 3, 4, 2};
    std::cout << hasDuplicateSlow(numbers) << "\n";
    return 0;
}
```

Sau chương STL, bạn có thể cải thiện bằng `std::unordered_set` để đạt trung bình `O(n)`.

## 5. Bài tập

1. Viết hàm nhận `const std::string&` và đếm số nguyên âm.
2. Dùng `unique_ptr` quản lý một object `Student`.
3. Viết hàm kiểm tra duplicate bằng `unordered_set`.
4. Tìm độ phức tạp của các đoạn code có vòng lặp lồng nhau.
