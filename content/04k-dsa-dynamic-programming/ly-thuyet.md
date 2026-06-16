# Dynamic Programming (Quy hoạch động)

## 1. Khái niệm

**Dynamic Programming (DP)** giải bài toán bằng cách chia thành các **bài toán con** (subproblems), lưu kết quả để **tránh tính lại** (memoization).

**Hai điều kiện để dùng DP:**
1. **Optimal Substructure:** Lời giải tối ưu chứa lời giải tối ưu của bài toán con
2. **Overlapping Subproblems:** Cùng bài toán con được tính nhiều lần

**Ví dụ: Fibonacci**
```
                    fib(5)
                   /      \
              fib(4)      fib(3)     ← fib(3) tính 2 lần!
             /    \       /    \
         fib(3)  fib(2) fib(2) fib(1)
        /    \
    fib(2)  fib(1)
```

## 2. Hai cách tiếp cận

### Top-Down (Memoization) — Đệ quy + cache

```java
// Fibonacci — Top-Down
public int fib(int n, int[] memo) {
    if (n <= 1) return n;
    if (memo[n] != 0) return memo[n];  // Đã tính rồi
    memo[n] = fib(n - 1, memo) + fib(n - 2, memo);
    return memo[n];
}
// Time: O(n), Space: O(n)
```

### Bottom-Up (Tabulation) — Xây từ nhỏ lên

```java
// Fibonacci — Bottom-Up
public int fib(int n) {
    if (n <= 1) return n;
    int[] dp = new int[n + 1];
    dp[0] = 0; dp[1] = 1;
    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2];
    }
    return dp[n];
}

// Tối ưu space — chỉ cần 2 biến
public int fib(int n) {
    if (n <= 1) return n;
    int prev2 = 0, prev1 = 1;
    for (int i = 2; i <= n; i++) {
        int curr = prev1 + prev2;
        prev2 = prev1; prev1 = curr;
    }
    return prev1;
}
```

## 3. Các dạng DP phổ biến

### 3.1 1D DP

```java
// Climbing Stairs — Có bao nhiêu cách leo n bậc (mỗi bước 1 hoặc 2)?
// dp[i] = dp[i-1] + dp[i-2]
public int climbStairs(int n) {
    if (n <= 2) return n;
    int prev2 = 1, prev1 = 2;
    for (int i = 3; i <= n; i++) {
        int curr = prev1 + prev2;
        prev2 = prev1; prev1 = curr;
    }
    return prev1;
}

// House Robber — Không cướp 2 nhà liên tiếp, max tiền
// dp[i] = max(dp[i-1], dp[i-2] + nums[i])
public int rob(int[] nums) {
    int prev2 = 0, prev1 = 0;
    for (int num : nums) {
        int curr = Math.max(prev1, prev2 + num);
        prev2 = prev1; prev1 = curr;
    }
    return prev1;
}
```

### 3.2 2D DP

```java
// Unique Paths — m×n grid, chỉ đi phải/xuống, đếm số đường
// dp[i][j] = dp[i-1][j] + dp[i][j-1]
public int uniquePaths(int m, int n) {
    int[][] dp = new int[m][n];
    for (int i = 0; i < m; i++) dp[i][0] = 1;
    for (int j = 0; j < n; j++) dp[0][j] = 1;
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
        }
    }
    return dp[m - 1][n - 1];
}
```

### 3.3 Knapsack (Bài toán cái túi)

```java
// 0/1 Knapsack: Chọn items tối đa value, không vượt capacity
public int knapsack(int[] weights, int[] values, int capacity) {
    int n = weights.length;
    int[][] dp = new int[n + 1][capacity + 1];
    for (int i = 1; i <= n; i++) {
        for (int w = 0; w <= capacity; w++) {
            dp[i][w] = dp[i - 1][w];  // Không chọn item i
            if (weights[i - 1] <= w) {
                dp[i][w] = Math.max(dp[i][w],
                    dp[i - 1][w - weights[i - 1]] + values[i - 1]);  // Chọn item i
            }
        }
    }
    return dp[n][capacity];
}

// Coin Change — Ít xu nhất để tạo amount
// Unbounded knapsack (mỗi xu dùng nhiều lần)
public int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, amount + 1);
    dp[0] = 0;
    for (int i = 1; i <= amount; i++) {
        for (int coin : coins) {
            if (coin <= i) dp[i] = Math.min(dp[i], dp[i - coin] + 1);
        }
    }
    return dp[amount] > amount ? -1 : dp[amount];
}
```

### 3.4 Longest Subsequence

```java
// Longest Common Subsequence (LCS)
// dp[i][j] = LCS length of text1[0..i-1] and text2[0..j-1]
public int longestCommonSubsequence(String text1, String text2) {
    int m = text1.length(), n = text2.length();
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                dp[i][j] = dp[i - 1][j - 1] + 1;
            } else {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
    }
    return dp[m][n];
}

// Longest Increasing Subsequence (LIS)
// dp[i] = LIS ending at index i
public int lengthOfLIS(int[] nums) {
    int[] dp = new int[nums.length];
    Arrays.fill(dp, 1);
    int maxLen = 1;
    for (int i = 1; i < nums.length; i++) {
        for (int j = 0; j < i; j++) {
            if (nums[j] < nums[i]) dp[i] = Math.max(dp[i], dp[j] + 1);
        }
        maxLen = Math.max(maxLen, dp[i]);
    }
    return maxLen;
}
// Time: O(n²). Tối ưu O(n log n) với Patience Sorting
```

### 3.5 String DP

```java
// Edit Distance (Levenshtein) — Số thao tác tối thiểu biến s1 → s2
public int minDistance(String word1, String word2) {
    int m = word1.length(), n = word2.length();
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 0; i <= m; i++) dp[i][0] = i;
    for (int j = 0; j <= n; j++) dp[0][j] = j;

    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                dp[i][j] = dp[i - 1][j - 1];
            } else {
                dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],     // Replace
                                Math.min(dp[i - 1][j],          // Delete
                                         dp[i][j - 1]));        // Insert
            }
        }
    }
    return dp[m][n];
}
```

## 4. Quy trình giải bài DP

```
1. Xác định STATE: dp[i] hoặc dp[i][j] đại diện cho gì?
2. Xác định TRANSITION: dp[i] tính từ dp[...] nào?
3. Xác định BASE CASE: dp[0], dp[1], ...?
4. Xác định ANSWER: dp[n]? dp[m][n]? max(dp[i])?
5. Tối ưu SPACE nếu cần (chỉ phụ thuộc hàng trước → 1D)
```

## 5. Khi nào dùng DP?

| Dấu hiệu | Ví dụ |
|----------|-------|
| "Đếm số cách..." | Climbing Stairs, Unique Paths |
| "Tìm min/max..." | Coin Change, House Robber |
| "Có thể hay không..." | Word Break, Partition Equal |
| "Dãy con dài nhất..." | LIS, LCS |
| Bài toán tối ưu có cấu trúc con | Knapsack, Edit Distance |

> **Phỏng vấn thường hỏi:** Climbing Stairs, House Robber, Coin Change, LIS, LCS, Knapsack, Edit Distance, Word Break, Decode Ways.
