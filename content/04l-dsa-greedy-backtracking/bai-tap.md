# Greedy & Backtracking - Bài Tập

## Bài 1: Jump Game (Greedy)
**Độ khó: Trung bình**

Cho mảng `nums[i]` = số bước nhảy tối đa từ vị trí i. Kiểm tra có thể đến cuối mảng không.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [2,3,1,1,4] | true |
| 2 | [3,2,1,0,4] | false |
| 3 | [0] | true |
| 4 | [2,0,0] | true |
| 5 | [1,1,1,1] | true |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(canJump(new int[]{2,3,1,1,4})), "true");
        check(2, String.valueOf(canJump(new int[]{3,2,1,0,4})), "false");
        check(3, String.valueOf(canJump(new int[]{0})), "true");
        check(4, String.valueOf(canJump(new int[]{2,0,0})), "true");
        check(5, String.valueOf(canJump(new int[]{1,1,1,1})), "true");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static boolean canJump(int[] nums) {
        // Code here ...
        return false;
    }
}
```

---

## Bài 2: Subsets (Backtracking)
**Độ khó: Trung bình**

Liệt kê tất cả tập con của mảng số nguyên phân biệt.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [1,2,3] | 8 subsets |
| 2 | [0] | 2 subsets |
| 3 | [1,2] | 4 subsets |
| 4 | [] | 1 subset |
| 5 | [1,2,3,4] | 16 subsets |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(subsets(new int[]{1,2,3}).size()), "8");
        check(2, String.valueOf(subsets(new int[]{0}).size()), "2");
        check(3, String.valueOf(subsets(new int[]{1,2}).size()), "4");
        check(4, String.valueOf(subsets(new int[]{}).size()), "1");
        check(5, String.valueOf(subsets(new int[]{1,2,3,4}).size()), "16");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static List<List<Integer>> subsets(int[] nums) {
        // Code here ...
        return new ArrayList<>();
    }
}
```

---

## Bài 3: Permutations (Backtracking)
**Độ khó: Trung bình**

Liệt kê tất cả hoán vị của mảng số nguyên phân biệt.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [1,2,3] | 6 permutations |
| 2 | [0,1] | 2 permutations |
| 3 | [1] | 1 permutation |
| 4 | [1,2] | 2 permutations |
| 5 | [1,2,3,4] | 24 permutations |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(permute(new int[]{1,2,3}).size()), "6");
        check(2, String.valueOf(permute(new int[]{0,1}).size()), "2");
        check(3, String.valueOf(permute(new int[]{1}).size()), "1");
        check(4, String.valueOf(permute(new int[]{1,2}).size()), "2");
        check(5, String.valueOf(permute(new int[]{1,2,3,4}).size()), "24");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static List<List<Integer>> permute(int[] nums) {
        // Code here ...
        return new ArrayList<>();
    }
}
```

---

## Bài 4: Combination Sum (Backtracking)
**Độ khó: Trung bình**

Tìm tất cả tổ hợp có tổng bằng target. Mỗi số dùng nhiều lần.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | candidates=[2,3,6,7], target=7 | 2 combinations |
| 2 | candidates=[2,3,5], target=8 | 3 combinations |
| 3 | candidates=[2], target=1 | 0 combinations |
| 4 | candidates=[1], target=1 | 1 combination |
| 5 | candidates=[1], target=2 | 1 combination |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(combinationSum(new int[]{2,3,6,7}, 7).size()), "2");
        check(2, String.valueOf(combinationSum(new int[]{2,3,5}, 8).size()), "3");
        check(3, String.valueOf(combinationSum(new int[]{2}, 1).size()), "0");
        check(4, String.valueOf(combinationSum(new int[]{1}, 1).size()), "1");
        check(5, String.valueOf(combinationSum(new int[]{1}, 2).size()), "1");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        // Code here ...
        return new ArrayList<>();
    }
}
```

---

## Bài 5: Non-overlapping Intervals (Greedy)
**Độ khó: Trung bình**

Tìm số lượng intervals tối thiểu cần xóa để không còn overlap.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [[1,2],[2,3],[3,4],[1,3]] | 1 |
| 2 | [[1,2],[1,2],[1,2]] | 2 |
| 3 | [[1,2],[2,3]] | 0 |
| 4 | [[1,100],[11,22],[1,11],[2,12]] | 2 |
| 5 | [[0,2],[1,3],[2,4],[3,5],[4,6]] | 2 |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(eraseOverlapIntervals(new int[][]{{1,2},{2,3},{3,4},{1,3}})), "1");
        check(2, String.valueOf(eraseOverlapIntervals(new int[][]{{1,2},{1,2},{1,2}})), "2");
        check(3, String.valueOf(eraseOverlapIntervals(new int[][]{{1,2},{2,3}})), "0");
        check(4, String.valueOf(eraseOverlapIntervals(new int[][]{{1,100},{11,22},{1,11},{2,12}})), "2");
        check(5, String.valueOf(eraseOverlapIntervals(new int[][]{{0,2},{1,3},{2,4},{3,5},{4,6}})), "2");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int eraseOverlapIntervals(int[][] intervals) {
        // Code here ...
        return 0;
    }
}
```

---

## Bài 6: Word Search (Backtracking)
**Độ khó: Trung bình**

Tìm xem có thể tạo từ `word` trên board bằng cách đi liền kề không.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | board=[["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word="ABCCED" | true |
| 2 | board=[["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word="SEE" | true |
| 3 | board=[["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word="ABCB" | false |
| 4 | board=[["A"]], word="A" | true |
| 5 | board=[["A","B"],["C","D"]], word="ABDC" | true |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        char[][] board = {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        check(1, String.valueOf(exist(board, "ABCCED")), "true");
        check(2, String.valueOf(exist(new char[][]{{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}}, "SEE")), "true");
        check(3, String.valueOf(exist(new char[][]{{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}}, "ABCB")), "false");
        check(4, String.valueOf(exist(new char[][]{{'A'}}, "A")), "true");
        check(5, String.valueOf(exist(new char[][]{{'A','B'},{'C','D'}}, "ABDC")), "true");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static boolean exist(char[][] board, String word) {
        // Code here ...
        return false;
    }
}
```

---

## Bài 7: N-Queens (Backtracking)
**Độ khó: Khó**

Đặt n quân hậu trên bàn cờ n×n sao cho không quân nào tấn công nhau.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | n=4 | 2 |
| 2 | n=1 | 1 |
| 3 | n=5 | 10 |
| 4 | n=6 | 4 |
| 5 | n=8 | 92 |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(solveNQueens(4).size()), "2");
        check(2, String.valueOf(solveNQueens(1).size()), "1");
        check(3, String.valueOf(solveNQueens(5).size()), "10");
        check(4, String.valueOf(solveNQueens(6).size()), "4");
        check(5, String.valueOf(solveNQueens(8).size()), "92");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static List<List<String>> solveNQueens(int n) {
        // Code here ...
        return new ArrayList<>();
    }
}
```

---

## Bài 8: Gas Station (Greedy)
**Độ khó: Trung bình**

Có n trạm xăng trên đường vòng. Trạm i có gas[i] xăng, đi đến trạm tiếp theo tốn cost[i]. Tìm trạm xuất phát, -1 nếu không thể.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | gas=[1,2,3,4,5], cost=[3,4,5,1,2] | 3 |
| 2 | gas=[2,3,4], cost=[3,4,3] | -1 |
| 3 | gas=[5,1,2,3,4], cost=[4,4,1,5,1] | 4 |
| 4 | gas=[3,3,4], cost=[3,4,4] | -1 |
| 5 | gas=[5,8,2,8], cost=[6,5,6,6] | 3 |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(canCompleteCircuit(new int[]{1,2,3,4,5}, new int[]{3,4,5,1,2})), "3");
        check(2, String.valueOf(canCompleteCircuit(new int[]{2,3,4}, new int[]{3,4,3})), "-1");
        check(3, String.valueOf(canCompleteCircuit(new int[]{5,1,2,3,4}, new int[]{4,4,1,5,1})), "4");
        check(4, String.valueOf(canCompleteCircuit(new int[]{3,3,4}, new int[]{3,4,4})), "-1");
        check(5, String.valueOf(canCompleteCircuit(new int[]{5,8,2,8}, new int[]{6,5,6,6})), "3");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int canCompleteCircuit(int[] gas, int[] cost) {
        // Code here ...
        return -1;
    }
}
```
