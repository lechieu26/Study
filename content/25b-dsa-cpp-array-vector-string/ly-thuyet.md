# Chương 2: Mảng, vector và chuỗi

## 1. Mảng và vector

Mảng lưu các phần tử liên tiếp trong bộ nhớ. Truy cập theo chỉ số là `O(1)`.

Trong C++, nên ưu tiên `std::vector` vì kích thước linh hoạt và an toàn hơn mảng động thủ công.

```cpp
std::vector<int> a = {1, 2, 3};
a.push_back(4);
```

## 2. Độ phức tạp thao tác vector

- Truy cập `a[i]`: `O(1)`.
- Thêm cuối `push_back`: trung bình `O(1)`.
- Chèn/xóa ở giữa: `O(n)` vì phải dịch phần tử.
- Duyệt toàn bộ: `O(n)`.

## 3. Prefix sum

Prefix sum giúp tính tổng đoạn nhanh.

Nếu `prefix[i]` là tổng `a[0]` đến `a[i - 1]`, tổng đoạn `[l, r]` là:

```text
prefix[r + 1] - prefix[l]
```

Tiền xử lý `O(n)`, mỗi query `O(1)`.

## 4. Two pointers

Two pointers dùng hai con trỏ/chỉ số di chuyển trên mảng. Hay dùng khi mảng đã sắp xếp hoặc cần xử lý đoạn liên tục.

Ví dụ: tìm hai số có tổng bằng target trong mảng đã sắp xếp.

## 5. Sliding window

Sliding window là cửa sổ trượt trên mảng/chuỗi. Phù hợp với bài toán đoạn con liên tiếp.

Có hai dạng:

- Cửa sổ kích thước cố định.
- Cửa sổ kích thước thay đổi theo điều kiện.

## 6. Chuỗi

`std::string` hỗ trợ truy cập ký tự, nối chuỗi, lấy độ dài, cắt chuỗi.

```cpp
std::string s = "hello";
std::cout << s[0];
```

Chú ý: `s.size()` trả về `std::size_t`, là kiểu không âm.

## 7. Lỗi thường gặp

- Truy cập vượt biên.
- Dùng `int` cho tổng lớn.
- Quên trường hợp chuỗi rỗng.
- Sliding window sai khi có số âm.
- Two pointers trên mảng chưa sắp xếp trong khi thuật toán cần sắp xếp.

---

# Ví dụ thực hành

# Ví dụ chương 2

## 1. Prefix sum tính tổng đoạn

```cpp
#include <iostream>
#include <vector>

std::vector<long long> buildPrefixSum(const std::vector<int>& a) {
    std::vector<long long> prefix(a.size() + 1, 0);
    for (std::size_t i = 0; i < a.size(); ++i) {
        prefix[i + 1] = prefix[i] + a[i];
    }
    return prefix;
}

long long rangeSum(const std::vector<long long>& prefix, int left, int right) {
    return prefix[right + 1] - prefix[left];
}

int main() {
    std::vector<int> a = {2, 4, 1, 7, 3};
    auto prefix = buildPrefixSum(a);
    std::cout << rangeSum(prefix, 1, 3) << "\n"; // 4 + 1 + 7
    return 0;
}
```

## 2. Two pointers: hai số có tổng bằng target

```cpp
#include <iostream>
#include <vector>

bool hasTwoSumSorted(const std::vector<int>& a, int target) {
    int left = 0;
    int right = static_cast<int>(a.size()) - 1;

    while (left < right) {
        int sum = a[left] + a[right];
        if (sum == target) {
            return true;
        }
        if (sum < target) {
            ++left;
        } else {
            --right;
        }
    }

    return false;
}

int main() {
    std::vector<int> a = {1, 2, 4, 7, 11};
    std::cout << hasTwoSumSorted(a, 9) << "\n";
    return 0;
}
```

## 3. Sliding window: tổng lớn nhất của k phần tử liên tiếp

```cpp
#include <iostream>
#include <vector>

long long maxSumWindow(const std::vector<int>& a, int k) {
    if (k <= 0 || k > static_cast<int>(a.size())) {
        return 0;
    }

    long long windowSum = 0;
    for (int i = 0; i < k; ++i) {
        windowSum += a[i];
    }

    long long best = windowSum;
    for (int right = k; right < static_cast<int>(a.size()); ++right) {
        windowSum += a[right];
        windowSum -= a[right - k];
        best = std::max(best, windowSum);
    }

    return best;
}

int main() {
    std::vector<int> a = {2, 1, 5, 1, 3, 2};
    std::cout << maxSumWindow(a, 3) << "\n";
    return 0;
}
```

## 4. Đảo ngược từ trong chuỗi

```cpp
#include <algorithm>
#include <iostream>
#include <sstream>
#include <string>
#include <vector>

int main() {
    std::string text = "hoc DSA bang C++";
    std::stringstream ss(text);
    std::vector<std::string> words;
    std::string word;

    while (ss >> word) {
        words.push_back(word);
    }

    std::reverse(words.begin(), words.end());

    for (const auto& w : words) {
        std::cout << w << " ";
    }
    std::cout << "\n";
    return 0;
}
```

## 5. Bài tập

1. Dùng prefix sum trả lời nhiều query tổng đoạn.
2. Tìm độ dài chuỗi con không có ký tự lặp.
3. Kiểm tra chuỗi palindrome bằng two pointers.
4. Tìm tổng nhỏ nhất của k phần tử liên tiếp.
