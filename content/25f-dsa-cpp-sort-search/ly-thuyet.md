# Chương 6: Sắp xếp và tìm kiếm

## 1. Sắp xếp (Sorting)

Sắp xếp giúp dữ liệu có thứ tự, từ đó nhiều bài toán trở nên dễ hơn: two pointers, binary search, loại trùng, nhóm nhóm (grouping).

Trong C++, nên dùng `std::sort` cho đa số trường hợp.

```cpp
std::sort(a.begin(), a.end());
```

Độ phức tạp trung bình và xấu nhất của `std::sort` là `O(n log n)`.

## 2. Bộ so sánh (Comparator)

Comparator quy định thứ tự sắp xếp.

```cpp
std::sort(a.begin(), a.end(), [](int x, int y) {
    return x > y;
});
```

Comparator phải tạo thứ tự hợp lệ (strict weak ordering). Không nên dùng `<=` trong comparator.

## 3. Tìm kiếm nhị phân (Binary search)

Binary search dùng khi không gian tìm kiếm có tính đơn điệu.

Không chỉ tìm trong mảng, binary search còn có thể tìm đáp án nhỏ nhất/lớn nhất thỏa mãn điều kiện.

## 4. Lower bound và upper bound

Trong mảng đã sắp xếp:

- `lower_bound`: vị trí đầu tiên `>= x`.
- `upper_bound`: vị trí đầu tiên `> x`.

Dùng để đếm số lần xuất hiện:

```text
count(x) = upper_bound(x) - lower_bound(x)
```

## 5. Lỗi thường gặp

- Binary search bị tràn số khi tính `mid = (left + right) / 2`.
- Vòng lặp vô hạn do cập nhật biến sai.
- Sắp xếp xong làm mất thứ tự gốc mà bài yêu cầu giữ.
- Comparator sai quy tắc.

---

# Ví dụ thực hành

# Ví dụ chương 6

## 1. Sắp xếp struct theo nhiều tiêu chí

```cpp
#include <algorithm>
#include <iostream>
#include <string>
#include <vector>

struct Student {
    std::string name;
    int score;
};

int main() {
    std::vector<Student> students = {
        {"An", 8},
        {"Binh", 9},
        {"Chi", 9},
        {"Dung", 7}
    };

    std::sort(students.begin(), students.end(), [](const Student& a, const Student& b) {
        if (a.score != b.score) {
            return a.score > b.score;
        }
        return a.name < b.name;
    });

    for (const auto& s : students) {
        std::cout << s.name << " " << s.score << "\n";
    }

    return 0;
}
```

## 2. Tìm kiếm nhị phân tự viết

```cpp
#include <iostream>
#include <vector>

int binarySearch(const std::vector<int>& a, int target) {
    int left = 0;
    int right = static_cast<int>(a.size()) - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;

        if (a[mid] == target) {
            return mid;
        }
        if (a[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }

    return -1;
}

int main() {
    std::vector<int> a = {1, 3, 5, 7, 9};
    std::cout << binarySearch(a, 7) << "\n";
    return 0;
}
```

## 3. Lower bound đếm tần suất

```cpp
#include <algorithm>
#include <iostream>
#include <vector>

int countOccurrences(const std::vector<int>& a, int x) {
    auto left = std::lower_bound(a.begin(), a.end(), x);
    auto right = std::upper_bound(a.begin(), a.end(), x);
    return static_cast<int>(right - left);
}

int main() {
    std::vector<int> a = {1, 2, 2, 2, 3, 4};
    std::cout << countOccurrences(a, 2) << "\n";
    return 0;
}
```

## 4. Tìm kiếm nhị phân trên đáp án

```cpp
#include <algorithm>
#include <iostream>
#include <vector>

bool canMakeAtLeast(const std::vector<int>& lengths, int pieceLength, int need) {
    int count = 0;
    for (int len : lengths) {
        count += len / pieceLength;
    }
    return count >= need;
}

int maxPieceLength(const std::vector<int>& lengths, int need) {
    int left = 1;
    int right = *std::max_element(lengths.begin(), lengths.end());
    int answer = 0;

    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (canMakeAtLeast(lengths, mid, need)) {
            answer = mid;
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }

    return answer;
}

int main() {
    std::vector<int> lengths = {8, 5, 10};
    std::cout << maxPieceLength(lengths, 5) << "\n";
    return 0;
}
```

## 5. Bài tập

1. Sắp xếp danh sách sinh viên theo điểm giảm, tên tăng.
2. Đếm số phần tử trong đoạn `[L, R]` của mảng đã sắp xếp.
3. Tìm căn bậc hai nguyên của n bằng tìm kiếm nhị phân.
4. Tìm tốc độ nhỏ nhất để ăn hết chuỗi công việc trong T giờ.
