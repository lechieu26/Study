# Dynamic Programming - Bài Tập

## Bài 1: Climbing Stairs
**Độ khó: Dễ**

Có n bậc thang. Mỗi lần bước 1 hoặc 2 bậc. Đếm số cách leo lên đỉnh.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | n=2 | 2 |
| 2 | n=3 | 3 |
| 3 | n=1 | 1 |
| 4 | n=5 | 8 |
| 5 | n=10 | 89 |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(climbStairs(2)), "2");
        check(2, String.valueOf(climbStairs(3)), "3");
        check(3, String.valueOf(climbStairs(1)), "1");
        check(4, String.valueOf(climbStairs(5)), "8");
        check(5, String.valueOf(climbStairs(10)), "89");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int climbStairs(int n) {
        // Code here ...
        return 0;
    }
}
```

---

## Bài 2: House Robber
**Độ khó: Trung bình**

Mỗi nhà có tiền. Không được cướp 2 nhà liên tiếp. Tìm tổng tiền max.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [1,2,3,1] | 4 |
| 2 | [2,7,9,3,1] | 12 |
| 3 | [0] | 0 |
| 4 | [2,1] | 2 |
| 5 | [1,3,1,3,100] | 103 |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(rob(new int[]{1,2,3,1})), "4");
        check(2, String.valueOf(rob(new int[]{2,7,9,3,1})), "12");
        check(3, String.valueOf(rob(new int[]{0})), "0");
        check(4, String.valueOf(rob(new int[]{2,1})), "2");
        check(5, String.valueOf(rob(new int[]{1,3,1,3,100})), "103");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int rob(int[] nums) {
        // Code here ...
        return 0;
    }
}
```

---

## Bài 3: Coin Change
**Độ khó: Trung bình**

Cho mảng mệnh giá xu, tìm số xu ít nhất để tạo amount. Trả về -1 nếu không thể.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | coins=[1,5,11], amount=15 | 3 |
| 2 | coins=[2], amount=3 | -1 |
| 3 | coins=[1], amount=0 | 0 |
| 4 | coins=[1,2,5], amount=11 | 3 |
| 5 | coins=[186,419,83,408], amount=6249 | 20 |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(coinChange(new int[]{1,5,11}, 15)), "3");
        check(2, String.valueOf(coinChange(new int[]{2}, 3)), "-1");
        check(3, String.valueOf(coinChange(new int[]{1}, 0)), "0");
        check(4, String.valueOf(coinChange(new int[]{1,2,5}, 11)), "3");
        check(5, String.valueOf(coinChange(new int[]{186,419,83,408}, 6249)), "20");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int coinChange(int[] coins, int amount) {
        // Code here ...
        return -1;
    }
}
```

---

## Bài 4: Longest Increasing Subsequence
**Độ khó: Trung bình**

Tìm dãy con tăng dài nhất (LIS).

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [10,9,2,5,3,7,101,18] | 4 |
| 2 | [0,1,0,3,2,3] | 4 |
| 3 | [7,7,7,7] | 1 |
| 4 | [1] | 1 |
| 5 | [1,3,6,7,9,4,10,5,6] | 6 |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(lengthOfLIS(new int[]{10,9,2,5,3,7,101,18})), "4");
        check(2, String.valueOf(lengthOfLIS(new int[]{0,1,0,3,2,3})), "4");
        check(3, String.valueOf(lengthOfLIS(new int[]{7,7,7,7})), "1");
        check(4, String.valueOf(lengthOfLIS(new int[]{1})), "1");
        check(5, String.valueOf(lengthOfLIS(new int[]{1,3,6,7,9,4,10,5,6})), "6");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int lengthOfLIS(int[] nums) {
        // Code here ...
        return 0;
    }
}
```

---

## Bài 5: 0/1 Knapsack
**Độ khó: Trung bình**

Có n items (weight, value) và túi sức chứa W. Chọn items tối đa giá trị.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | w=[2,3,4,5], v=[3,4,5,6], W=8 | 10 |
| 2 | w=[1,2,3], v=[6,10,12], W=5 | 22 |
| 3 | w=[10], v=[100], W=5 | 0 |
| 4 | w=[1], v=[1], W=1 | 1 |
| 5 | w=[1,1,1], v=[10,20,30], W=2 | 50 |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(knapsack(new int[]{2,3,4,5}, new int[]{3,4,5,6}, 8)), "10");
        check(2, String.valueOf(knapsack(new int[]{1,2,3}, new int[]{6,10,12}, 5)), "22");
        check(3, String.valueOf(knapsack(new int[]{10}, new int[]{100}, 5)), "0");
        check(4, String.valueOf(knapsack(new int[]{1}, new int[]{1}, 1)), "1");
        check(5, String.valueOf(knapsack(new int[]{1,1,1}, new int[]{10,20,30}, 2)), "50");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int knapsack(int[] weights, int[] values, int W) {
        // Code here ...
        return 0;
    }
}
```

---

## Bài 6: Longest Common Subsequence
**Độ khó: Trung bình**

Tìm dãy con chung dài nhất của 2 chuỗi.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | text1="abcde", text2="ace" | 3 |
| 2 | text1="abc", text2="abc" | 3 |
| 3 | text1="abc", text2="def" | 0 |
| 4 | text1="a", text2="a" | 1 |
| 5 | text1="bl", text2="yby" | 1 |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(longestCommonSubsequence("abcde", "ace")), "3");
        check(2, String.valueOf(longestCommonSubsequence("abc", "abc")), "3");
        check(3, String.valueOf(longestCommonSubsequence("abc", "def")), "0");
        check(4, String.valueOf(longestCommonSubsequence("a", "a")), "1");
        check(5, String.valueOf(longestCommonSubsequence("bl", "yby")), "1");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int longestCommonSubsequence(String text1, String text2) {
        // Code here ...
        return 0;
    }
}
```

---

## Bài 7: Edit Distance
**Độ khó: Khó**

Tìm số thao tác tối thiểu (insert, delete, replace) để biến word1 → word2.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | word1="horse", word2="ros" | 3 |
| 2 | word1="intention", word2="execution" | 5 |
| 3 | word1="", word2="abc" | 3 |
| 4 | word1="abc", word2="" | 3 |
| 5 | word1="abc", word2="abc" | 0 |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(minDistance("horse", "ros")), "3");
        check(2, String.valueOf(minDistance("intention", "execution")), "5");
        check(3, String.valueOf(minDistance("", "abc")), "3");
        check(4, String.valueOf(minDistance("abc", "")), "3");
        check(5, String.valueOf(minDistance("abc", "abc")), "0");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int minDistance(String word1, String word2) {
        // Code here ...
        return 0;
    }
}
```

---

## Bài 8: Word Break
**Độ khó: Trung bình**

Kiểm tra chuỗi s có thể tách thành các từ trong dictionary không.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | s="leetcode", dict=["leet","code"] | true |
| 2 | s="applepenapple", dict=["apple","pen"] | true |
| 3 | s="catsandog", dict=["cats","dog","sand","and","cat"] | false |
| 4 | s="a", dict=["a"] | true |
| 5 | s="ab", dict=["a","b"] | true |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(wordBreak("leetcode", Arrays.asList("leet","code"))), "true");
        check(2, String.valueOf(wordBreak("applepenapple", Arrays.asList("apple","pen"))), "true");
        check(3, String.valueOf(wordBreak("catsandog", Arrays.asList("cats","dog","sand","and","cat"))), "false");
        check(4, String.valueOf(wordBreak("a", Arrays.asList("a"))), "true");
        check(5, String.valueOf(wordBreak("ab", Arrays.asList("a","b"))), "true");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static boolean wordBreak(String s, List<String> wordDict) {
        // Code here ...
        return false;
    }
}
```
