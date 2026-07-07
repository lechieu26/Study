# Chương 1: Độ phức tạp và tư duy DSA

## 1. DSA là gì?

DSA gồm hai phần:

- Data Structures: cách tổ chức dữ liệu, ví dụ array, linked list, stack, queue, tree, graph, hash table.
- Algorithms: cách xử lý dữ liệu, ví dụ sort, search, BFS, DFS, dynamic programming.

Học DSA không chỉ để giải bài online judge. Mục tiêu chính là biết chọn cách lưu và xử lý dữ liệu sao cho đúng, nhanh, rõ ràng.

## 2. Big O

Big O mô tả tốc độ tăng của thời gian chạy hoặc bộ nhớ khi kích thước input tăng.

Thứ tự thường gặp từ tốt đến nặng:

- `O(1)`: hằng số.
- `O(log n)`: logarit.
- `O(n)`: tuyến tính.
- `O(n log n)`: sắp xếp hiệu quả.
- `O(n^2)`: hai vòng lặp lồng nhau.
- `O(2^n)`: thử tất cả tập con.
- `O(n!)`: thử tất cả hoán vị.

Big O bỏ qua hằng số và chỉ giữ phần tăng nhanh nhất.

## 3. Ví dụ phân tích

```cpp
for (int i = 0; i < n; ++i) {
    std::cout << i << "\n";
}
```

Vòng lặp chạy `n` lần nên là `O(n)`.

```cpp
for (int i = 0; i < n; ++i) {
    for (int j = 0; j < n; ++j) {
        std::cout << i << " " << j << "\n";
    }
}
```

Hai vòng lặp lồng nhau, mỗi vòng `n` lần, tổng `n * n`, nên là `O(n^2)`.

## 4. Giới hạn input và chọn thuật toán

Quy tắc cảm tính:

- `n <= 20`: có thể nghĩ đến backtracking, bitmask, `O(2^n)`.
- `n <= 1,000`: `O(n^2)` có thể chấp nhận.
- `n <= 100,000`: cần `O(n log n)` hoặc `O(n)`.
- `n >= 1,000,000`: ưu tiên `O(n)`, cần cẩn thận bộ nhớ.

Đây chỉ là ước lượng. Thực tế còn phụ thuộc time limit, ngôn ngữ, hằng số, IO.

## 5. Cách đọc bài DSA

1. Xác định input, output.
2. Tìm ranh giới: `n` tối đa bao nhiêu, giá trị phần tử bao nhiêu.
3. Viết cách brute force trước để hiểu bài.
4. Tìm điểm lặp lại, tính chất sắp xếp, tính chất prefix, graph, tree, state.
5. Chọn cấu trúc dữ liệu giúp thao tác nhanh hơn.
6. Chứng minh ngắn gọn vì sao đúng.
7. Kiểm tra edge cases.

## 6. Edge cases hay gặp

- Mảng rỗng.
- Chỉ có 1 phần tử.
- Tất cả phần tử bằng nhau.
- Số âm, số 0.
- Gia trị rất lớn gây tràn `int`.
- Input đã sắp xếp tăng/giảm.
- Graph không liên thông.
- Tree rỗng.

## 7. Lỗi thường gặp

- Tối ưu khi chưa hiểu đúng bài.
- Không đọc giới hạn input.
- Dùng `int` cho tổng lớn, gây overflow.
- Quên tính bộ nhớ.
- Chỉ test case đẹp, không test biên.

---

# Ví dụ thực hành

# Ví dụ chương 1

## 1. O(n): tính tổng mảng

```cpp
#include <iostream>
#include <vector>

long long sumArray(const std::vector<int>& a) {
    long long sum = 0;
    for (int x : a) {
        sum += x;
    }
    return sum;
}

int main() {
    std::vector<int> a = {1, 2, 3, 4, 5};
    std::cout << sumArray(a) << "\n";
    return 0;
}
```

## 2. O(n^2): đếm cặp có tổng bằng target

```cpp
#include <iostream>
#include <vector>

int countPairsBruteForce(const std::vector<int>& a, int target) {
    int count = 0;
    for (int i = 0; i < static_cast<int>(a.size()); ++i) {
        for (int j = i + 1; j < static_cast<int>(a.size()); ++j) {
            if (a[i] + a[j] == target) {
                ++count;
            }
        }
    }
    return count;
}

int main() {
    std::vector<int> a = {1, 4, 2, 3, 5};
    std::cout << countPairsBruteForce(a, 6) << "\n";
    return 0;
}
```

## 3. O(log n): chia đôi khoảng tìm kiếm

```cpp
#include <iostream>

int countDivideSteps(int n) {
    int steps = 0;
    while (n > 1) {
        n /= 2;
        ++steps;
    }
    return steps;
}

int main() {
    std::cout << countDivideSteps(1024) << "\n";
    return 0;
}
```

## 4. Bài tập

1. Xác định Big O của một vòng lặp từ `i = 1`, mỗi lần `i *= 2`.
2. Viết hàm tìm max trong vector và phân tích độ phức tạp.
3. Viết brute force tìm 3 số có tổng bằng target, phân tích Big O.
4. Với `n = 100000`, bạn có nên dùng `O(n^2)` không? Vì sao?
