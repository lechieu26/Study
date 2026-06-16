# Dynamic Programming - Đáp Án Chi Tiết

## Bài 1: Climbing Stairs

### Cách 1: Bottom-Up O(n)
```java
public class ClimbingStairs {
    public static int climbStairs(int n) {
        if (n <= 2) return n;
        int prev2 = 1, prev1 = 2;
        for (int i = 3; i <= n; i++) {
            int curr = prev1 + prev2;
            prev2 = prev1; prev1 = curr;
        }
        return prev1;
    }
}
// dp[i] = dp[i-1] + dp[i-2], giống Fibonacci
```

---

## Bài 2: House Robber

### Cách 1: DP O(n) time, O(1) space
```java
public class HouseRobber {
    public static int rob(int[] nums) {
        int prev2 = 0, prev1 = 0;
        for (int num : nums) {
            int curr = Math.max(prev1, prev2 + num);
            prev2 = prev1; prev1 = curr;
        }
        return prev1;
    }
}
// dp[i] = max(dp[i-1], dp[i-2] + nums[i])
// Chọn hoặc không chọn nhà i
```

---

## Bài 3: Coin Change

### Cách 1: Bottom-Up DP O(amount × coins)
```java
public class CoinChange {
    public static int coinChange(int[] coins, int amount) {
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
}
// dp[i] = min(dp[i - coin] + 1) cho mỗi coin
```

---

## Bài 4: LIS

### Cách 1: DP O(n²)
```java
public class LIS {
    public static int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int maxLen = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }
}
```

### Cách 2: Patience Sorting O(n log n)
```java
public class LIS_Optimal {
    public static int lengthOfLIS(int[] nums) {
        List<Integer> tails = new ArrayList<>();
        for (int num : nums) {
            int pos = Collections.binarySearch(tails, num);
            if (pos < 0) pos = -(pos + 1);
            if (pos == tails.size()) tails.add(num);
            else tails.set(pos, num);
        }
        return tails.size();
    }
}
// tails[i] = giá trị nhỏ nhất của đuôi LIS độ dài i+1
```

---

## Bài 5: 0/1 Knapsack

### Cách 1: 2D DP O(n × W)
```java
public class Knapsack {
    public static int knapsack(int[] w, int[] v, int W) {
        int n = w.length;
        int[][] dp = new int[n + 1][W + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= W; j++) {
                dp[i][j] = dp[i - 1][j];
                if (w[i - 1] <= j) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - w[i - 1]] + v[i - 1]);
                }
            }
        }
        return dp[n][W];
    }
}
```

### Cách 2: 1D DP (tối ưu space) O(n × W)
```java
public class Knapsack_1D {
    public static int knapsack(int[] w, int[] v, int W) {
        int[] dp = new int[W + 1];
        for (int i = 0; i < w.length; i++) {
            for (int j = W; j >= w[i]; j--) {  // Duyệt ngược!
                dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
            }
        }
        return dp[W];
    }
}
```

---

## Bài 6: LCS

### Cách 1: 2D DP O(m × n)
```java
public class LCS {
    public static int longestCommonSubsequence(String text1, String text2) {
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
}
```

---

## Bài 7: Edit Distance

### Cách 1: 2D DP O(m × n)
```java
public class EditDistance {
    public static int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],
                                    Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp[m][n];
    }
}
```

---

## Bài 8: Word Break

### Cách 1: DP O(n² × k)
```java
public class WordBreak {
    public static boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }
}
// dp[i] = true nếu s[0..i-1] có thể tách được
```
