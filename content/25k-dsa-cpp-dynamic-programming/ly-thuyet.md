# Chương 11: Quy hoạch động

## 1. Quy hoạch động (DP) là gì?

Quy hoạch động/Dynamic programming dùng khi bài toán có:

- Overlapping subproblems: các bài toán con bị lặp lại.
- Optimal substructure: đáp án bài toán lớn cấu thành từ đáp án bài toán nhỏ hơn.

Quy hoạch động lưu kết quả bài toán con để không phải tính lại.

## 2. Memoization và tabulation

Memoization: đệ quy kết hợp với bộ nhớ đệm (cache).

Tabulation: tính từ bài toán nhỏ lên bảng bằng vòng lặp.

## 3. Cách thiết kế bài toán quy hoạch động

1. Xác định trạng thái (state): `dp[i]` hoặc `dp[i][j]` nghĩa là gì?
2. Xác định công thức chuyển trạng thái (transition): tính trạng thái hiện tại từ trạng thái nào?
3. Xác định trường hợp cơ sở (base case).
4. Xác định thứ tự tính toán.
5. Lấy đáp án từ trạng thái nào?

## 4. Quy hoạch động 1 chiều

Ví dụ dãy Fibonacci:

```text
dp[i] = dp[i - 1] + dp[i - 2]
```

## 5. Quy hoạch động 2 chiều

Ví dụ LCS, knapsack, grid path (đường đi trên lưới).

`dp[i][j]` thường đại diện cho đáp án khi xét `i` phần tử đầu và tham số `j`.

## 6. Lỗi thường gặp

- Trạng thái không rõ nghĩa.
- Sai trường hợp cơ sở (base case).
- Dùng chỉ số (index) âm.
- Quên chia lấy dư (modulo) nếu bài yêu cầu.
- Cấp phát mảng quy hoạch động quá lớn.

---

# Ví dụ thực hành

# Ví dụ chương 11

## 1. Fibonacci tabulation

```cpp
#include <iostream>
#include <vector>

long long fibonacci(int n) {
    if (n <= 1) {
        return n;
    }

    std::vector<long long> dp(n + 1, 0);
    dp[0] = 0;
    dp[1] = 1;

    for (int i = 2; i <= n; ++i) {
        dp[i] = dp[i - 1] + dp[i - 2];
    }

    return dp[n];
}

int main() {
    std::cout << fibonacci(10) << "\n";
    return 0;
}
```

## 2. Climbing stairs (Leo cầu thang)

```cpp
#include <iostream>
#include <vector>

int countWays(int n) {
    std::vector<int> dp(n + 1, 0);
    dp[0] = 1;

    for (int i = 1; i <= n; ++i) {
        dp[i] += dp[i - 1];
        if (i >= 2) {
            dp[i] += dp[i - 2];
        }
    }

    return dp[n];
}

int main() {
    std::cout << countWays(5) << "\n";
    return 0;
}
```

## 3. Bài toán cái túi 0/1 (0/1 Knapsack)

```cpp
#include <algorithm>
#include <iostream>
#include <vector>

int knapsack(const std::vector<int>& weight, const std::vector<int>& value, int capacity) {
    int n = static_cast<int>(weight.size());
    std::vector<std::vector<int>> dp(n + 1, std::vector<int>(capacity + 1, 0));

    for (int i = 1; i <= n; ++i) {
        for (int cap = 0; cap <= capacity; ++cap) {
            dp[i][cap] = dp[i - 1][cap];
            if (cap >= weight[i - 1]) {
                dp[i][cap] = std::max(dp[i][cap], dp[i - 1][cap - weight[i - 1]] + value[i - 1]);
            }
        }
    }

    return dp[n][capacity];
}

int main() {
    std::vector<int> weight = {2, 3, 4};
    std::vector<int> value = {4, 5, 10};
    std::cout << knapsack(weight, value, 5) << "\n";
    return 0;
}
```

## 4. Dãy con tăng dài nhất O(n^2) (Longest Increasing Subsequence)

```cpp
#include <algorithm>
#include <iostream>
#include <vector>

int lis(const std::vector<int>& a) {
    if (a.empty()) {
        return 0;
    }

    std::vector<int> dp(a.size(), 1);
    int best = 1;

    for (int i = 0; i < static_cast<int>(a.size()); ++i) {
        for (int j = 0; j < i; ++j) {
            if (a[j] < a[i]) {
                dp[i] = std::max(dp[i], dp[j] + 1);
            }
        }
        best = std::max(best, dp[i]);
    }

    return best;
}

int main() {
    std::vector<int> a = {10, 9, 2, 5, 3, 7, 101, 18};
    std::cout << lis(a) << "\n";
    return 0;
}
```

## 5. Bài tập

1. Tính Fibonacci tối ưu bộ nhớ `O(1)`.
2. Đếm số đường đi trong lưới (grid) m x n chỉ được đi sang phải/xuống dưới.
3. Đổi tiền (Coin change): số đồng xu ít nhất để tạo thành số tiền (amount).
4. Dãy con chung dài nhất (Longest common subsequence) của 2 chuỗi.
