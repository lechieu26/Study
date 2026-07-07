# Chương 7: Bảng băm, set và map

## 1. Bảng băm (Hash table)

Bảng băm lưu key-value và cho thao tác tìm/chèn/xóa trung bình `O(1)`.

Trong C++:

- `std::unordered_map<Key, Value>`.
- `std::unordered_set<Key>`.

## 2. Map và set có thứ tự

`std::map` và `std::set` được cài đặt bằng cây cân bằng, thao tác `O(log n)` và duyệt theo thứ tự tăng dần.

Dùng `map/set` khi cần thứ tự. Dùng `unordered_map/unordered_set` khi cần nhanh trung bình và không cần thứ tự.

## 3. Đếm tần suất

Mẫu rất hay gặp:

```cpp
std::unordered_map<int, int> freq;
for (int x : a) {
    ++freq[x];
}
```

## 4. Phần tử trùng lặp và sự tồn tại (Membership)

`unordered_set` giúp kiểm tra một giá trị đã xuất hiện chưa.

```cpp
if (seen.count(x)) {
    // đã gặp x
}
```

## 5. Lỗi thường gặp

- Tưởng `unordered_map` luôn `O(1)`, thực tế worst-case có thể xấu.
- Dùng `operator[]` chỉ để kiểm tra key, vô tình tạo key mới.
- Quên include `<unordered_map>` hoặc `<map>`.
- Dùng key là custom struct nhưng không cung cấp hash/comparator.

---

# Ví dụ thực hành

# Ví dụ chương 7

## 1. Two sum bằng unordered_map

```cpp
#include <iostream>
#include <unordered_map>
#include <vector>

std::pair<int, int> twoSum(const std::vector<int>& a, int target) {
    std::unordered_map<int, int> indexOf;

    for (int i = 0; i < static_cast<int>(a.size()); ++i) {
        int need = target - a[i];
        if (indexOf.count(need)) {
            return {indexOf[need], i};
        }
        indexOf[a[i]] = i;
    }

    return {-1, -1};
}

int main() {
    std::vector<int> a = {2, 7, 11, 15};
    auto [i, j] = twoSum(a, 9);
    std::cout << i << " " << j << "\n";
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
    std::vector<std::string> words = {"dsa", "cpp", "dsa", "map"};
    std::unordered_map<std::string, int> freq;

    for (const auto& word : words) {
        ++freq[word];
    }

    for (const auto& [word, count] : freq) {
        std::cout << word << ": " << count << "\n";
    }

    return 0;
}
```

## 3. Kiểm tra trùng lặp (Duplicate)

```cpp
#include <iostream>
#include <unordered_set>
#include <vector>

bool hasDuplicate(const std::vector<int>& a) {
    std::unordered_set<int> seen;

    for (int x : a) {
        if (seen.count(x)) {
            return true;
        }
        seen.insert(x);
    }

    return false;
}

int main() {
    std::vector<int> a = {1, 2, 3, 2};
    std::cout << hasDuplicate(a) << "\n";
    return 0;
}
```

## 4. Dùng map để in theo thứ tự key

```cpp
#include <iostream>
#include <map>
#include <string>

int main() {
    std::map<std::string, int> score;
    score["Binh"] = 8;
    score["An"] = 9;
    score["Chi"] = 7;

    for (const auto& [name, value] : score) {
        std::cout << name << " " << value << "\n";
    }

    return 0;
}
```

## 5. Bài tập

1. Tìm phần tử xuất hiện nhiều nhất.
2. Kiểm tra hai chuỗi có phải là anagram (biến thể đảo chữ) của nhau không.
3. Đếm số cặp có tổng bằng target.
4. In các giá trị không trùng theo thứ tự tăng dần.
