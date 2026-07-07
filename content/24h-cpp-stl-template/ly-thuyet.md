# Chương 8: STL, template và lambda

## 1. STL là gì?

STL là Standard Template Library, gồm container, iterator và algorithm.

Ba nhóm quan trọng:

- Container: `vector`, `array`, `list`, `deque`, `map`, `set`, `unordered_map`.
- Iterator: đối tượng giúp duyệt container.
- Algorithm: `sort`, `find`, `count`, `lower_bound`, `accumulate`.

## 2. `std::vector`

`vector` là mảng động, kích thước có thể thay đổi.

```cpp
std::vector<int> numbers;
numbers.push_back(10);
numbers.push_back(20);
```

Dùng `vector` thay mảng động trong đa số trường hợp.

## 3. `std::map` và `std::unordered_map`

`map` lưu key-value, key được sắp xếp.

`unordered_map` lưu key-value bằng hash table, thường nhanh hơn khi không cần thứ tự.

```cpp
std::unordered_map<std::string, int> age;
age["An"] = 20;
```

## 4. `std::set`

`set` lưu các giá trị không trùng, có thứ tự. `unordered_set` không đảm bảo thứ tự.

## 5. Algorithm

Dùng algorithm chuẩn giúp code ngắn và ít lỗi hơn.

```cpp
std::sort(numbers.begin(), numbers.end());
```

Tìm phần tử:

```cpp
auto it = std::find(numbers.begin(), numbers.end(), 5);
```

## 6. Lambda

Lambda là hàm nhỏ viết tại chỗ.

```cpp
auto isEven = [](int x) {
    return x % 2 == 0;
};
```

Dùng trong sort:

```cpp
std::sort(students.begin(), students.end(), [](const Student& a, const Student& b) {
    return a.gpa > b.gpa;
});
```

## 7. Template

Template giúp viết code tổng quát cho nhiều kiểu dữ liệu.

```cpp
template <typename T>
T maxValue(T a, T b) {
    return a > b ? a : b;
}
```

Compiler sẽ sinh phiên bản phù hợp khi bạn gọi hàm.

## 8. Lỗi thường gặp

- Dùng iterator sau khi container bị thay đổi làm iterator mất hiệu lực.
- Dùng `map` khi `unordered_map` phù hợp hơn, hoặc ngược lại.
- Viết lại sort/find thủ công khi STL đã có.
- Template lôi dài khó đọc; chỉ template khi có nhu cầu tổng quát thật.

---

# Ví dụ thực hành

# Ví dụ chương 8

## 1. Sắp xếp vector

```cpp
#include <algorithm>
#include <iostream>
#include <vector>

int main() {
    std::vector<int> numbers = {5, 1, 9, 2, 7};

    std::sort(numbers.begin(), numbers.end());

    for (int value : numbers) {
        std::cout << value << " ";
    }
    std::cout << "\n";

    return 0;
}
```

## 2. Đếm tần suất từ

```cpp
#include <iostream>
#include <string>
#include <unordered_map>
#include <vector>

int main() {
    std::vector<std::string> words = {"c", "cpp", "c", "code", "cpp", "cpp"};
    std::unordered_map<std::string, int> frequency;

    for (const std::string& word : words) {
        ++frequency[word];
    }

    for (const auto& pair : frequency) {
        std::cout << pair.first << ": " << pair.second << "\n";
    }

    return 0;
}
```

## 3. Sắp xếp struct bằng lambda

```cpp
#include <algorithm>
#include <iostream>
#include <string>
#include <vector>

struct Student {
    std::string name;
    double gpa;
};

int main() {
    std::vector<Student> students = {
        {"An", 8.5},
        {"Bình", 7.2},
        {"Chi", 9.1}
    };

    std::sort(students.begin(), students.end(), [](const Student& a, const Student& b) {
        return a.gpa > b.gpa;
    });

    for (const Student& student : students) {
        std::cout << student.name << " " << student.gpa << "\n";
    }

    return 0;
}
```

## 4. Hàm template

```cpp
#include <iostream>

template <typename T>
T maxValue(T a, T b) {
    return a > b ? a : b;
}

int main() {
    std::cout << maxValue(3, 7) << "\n";
    std::cout << maxValue(2.5, 1.8) << "\n";
    return 0;
}
```

## 5. Bài tập

1. Nhập n số, sắp xếp tăng dần và giảm dần.
2. Đếm tần suất ký tự trong một chuỗi bằng `unordered_map`.
3. Quản lý danh sách sinh viên, sắp xếp theo gpa giảm dần.
4. Viết template `minValue`.
5. Dùng `std::count_if` đếm số chẵn trong vector.
