# Chương 5: Đệ quy và quay lui

## 1. Đệ quy là gì?

Đệ quy là kỹ thuật hàm gọi lại chính nó.

Một hàm đệ quy cần có:

- Base case: điều kiện dừng.
- Recursive case: lời gọi đệ quy tiến dần đến base case.

## 2. Stack gọi hàm

Mỗi lời gọi hàm tạo một frame trên call stack. Nếu đệ quy quá sâu, chương trình có thể bị tràn ngăn xếp (stack overflow).

## 3. Quay lui (Backtracking)

Quay lui thử từng lựa chọn, đi tiếp, nếu không phù hợp thì quay lại (quay lui) và thử lựa chọn khác.

Mẫu tư duy:

```text
chọn
gọi đệ quy
bỏ chọn
```

## 4. Khi nào dùng quay lui?

- Sinh tập con.
- Sinh hoán vị.
- Tổ hợp.
- Sudoku, N-Queens.
- Tìm đường đi trong mê cung.

Thường có độ phức tạp lớn, nên cần nhánh cận (pruning/cắt nhánh).

## 5. Nhánh cận (Pruning)

Nhánh cận là bỏ qua các nhánh chắc chắn không thể tạo đáp án tốt.

Ví dụ: trong bài tổng target, nếu tổng hiện tại đã vượt target và tất cả số dương, có thể dừng nhánh đó.

## 6. Lỗi thường gặp

- Quên base case.
- Quên hoàn tác (undo) lựa chọn.
- Dùng biến toàn cục (global) nhưng không đặt lại (reset).
- Copy vector quá nhiều làm chậm.
- Đệ quy quá sâu.

---

# Ví dụ thực hành

# Ví dụ chương 5

## 1. Đệ quy tính giai thừa

```cpp
#include <iostream>

long long factorial(int n) {
    if (n <= 1) {
        return 1;
    }
    return n * factorial(n - 1);
}

int main() {
    std::cout << factorial(5) << "\n";
    return 0;
}
```

## 2. Sinh tất cả tập con

```cpp
#include <iostream>
#include <vector>

void generateSubsets(int index, const std::vector<int>& a, std::vector<int>& current) {
    if (index == static_cast<int>(a.size())) {
        std::cout << "{ ";
        for (int x : current) {
            std::cout << x << " ";
        }
        std::cout << "}\n";
        return;
    }

    generateSubsets(index + 1, a, current);

    current.push_back(a[index]);
    generateSubsets(index + 1, a, current);
    current.pop_back();
}

int main() {
    std::vector<int> a = {1, 2, 3};
    std::vector<int> current;
    generateSubsets(0, a, current);
    return 0;
}
```

## 3. Sinh hoán vị

```cpp
#include <iostream>
#include <vector>

void permute(const std::vector<int>& a, std::vector<bool>& used, std::vector<int>& current) {
    if (current.size() == a.size()) {
        for (int x : current) {
            std::cout << x << " ";
        }
        std::cout << "\n";
        return;
    }

    for (int i = 0; i < static_cast<int>(a.size()); ++i) {
        if (used[i]) {
            continue;
        }

        used[i] = true;
        current.push_back(a[i]);
        permute(a, used, current);
        current.pop_back();
        used[i] = false;
    }
}

int main() {
    std::vector<int> a = {1, 2, 3};
    std::vector<bool> used(a.size(), false);
    std::vector<int> current;
    permute(a, used, current);
    return 0;
}
```

## 4. N-Queens đếm số cách đặt quân hậu

```cpp
#include <iostream>
#include <vector>

int solveNQueens(int row, int n, std::vector<bool>& col, std::vector<bool>& diag1, std::vector<bool>& diag2) {
    if (row == n) {
        return 1;
    }

    int ways = 0;
    for (int c = 0; c < n; ++c) {
        int d1 = row - c + n - 1;
        int d2 = row + c;

        if (col[c] || diag1[d1] || diag2[d2]) {
            continue;
        }

        col[c] = diag1[d1] = diag2[d2] = true;
        ways += solveNQueens(row + 1, n, col, diag1, diag2);
        col[c] = diag1[d1] = diag2[d2] = false;
    }

    return ways;
}

int main() {
    int n = 8;
    std::vector<bool> col(n, false);
    std::vector<bool> diag1(2 * n - 1, false);
    std::vector<bool> diag2(2 * n - 1, false);
    std::cout << solveNQueens(0, n, col, diag1, diag2) << "\n";
    return 0;
}
```

## 5. Bài tập

1. Sinh chuỗi nhị phân độ dài n.
2. Sinh tổ hợp chập k của n.
3. Tìm tất cả cách chọn số có tổng bằng target.
4. Giải mê cung bằng quay lui.
