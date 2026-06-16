# Greedy & Backtracking - Đáp Án Chi Tiết

## Bài 1: Jump Game

### Cách 1: Greedy — Max Reach O(n)
```java
public class JumpGame {
    public static boolean canJump(int[] nums) {
        int maxReach = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > maxReach) return false;
            maxReach = Math.max(maxReach, i + nums[i]);
        }
        return true;
    }
}
```

---

## Bài 2: Subsets

### Cách 1: Backtracking
```java
public class Subsets {
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), nums, 0);
        return result;
    }

    private static void backtrack(List<List<Integer>> result, List<Integer> curr, int[] nums, int start) {
        result.add(new ArrayList<>(curr));
        for (int i = start; i < nums.length; i++) {
            curr.add(nums[i]);
            backtrack(result, curr, nums, i + 1);
            curr.remove(curr.size() - 1);
        }
    }
}
```

### Cách 2: Bit Manipulation
```java
public class Subsets_Bit {
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;
        for (int mask = 0; mask < (1 << n); mask++) {
            List<Integer> subset = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) subset.add(nums[i]);
            }
            result.add(subset);
        }
        return result;
    }
}
```

---

## Bài 3: Permutations

### Cách 1: Backtracking
```java
public class Permutations {
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        backtrack(result, new ArrayList<>(), nums, used);
        return result;
    }

    private static void backtrack(List<List<Integer>> result, List<Integer> curr,
                                   int[] nums, boolean[] used) {
        if (curr.size() == nums.length) {
            result.add(new ArrayList<>(curr));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;
            used[i] = true;
            curr.add(nums[i]);
            backtrack(result, curr, nums, used);
            curr.remove(curr.size() - 1);
            used[i] = false;
        }
    }
}
```

---

## Bài 4: Combination Sum

### Cách 1: Backtracking
```java
public class CombinationSum {
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        backtrack(result, new ArrayList<>(), candidates, target, 0);
        return result;
    }

    private static void backtrack(List<List<Integer>> result, List<Integer> curr,
                                   int[] candidates, int remain, int start) {
        if (remain == 0) { result.add(new ArrayList<>(curr)); return; }
        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > remain) break;  // Pruning
            curr.add(candidates[i]);
            backtrack(result, curr, candidates, remain - candidates[i], i);  // i, không phải i+1
            curr.remove(curr.size() - 1);
        }
    }
}
```

---

## Bài 5: Non-overlapping Intervals

### Cách 1: Greedy — Sort by end time O(n log n)
```java
public class NonOverlapping {
    public static int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
        int count = 0, end = Integer.MIN_VALUE;
        for (int[] interval : intervals) {
            if (interval[0] >= end) end = interval[1];
            else count++;
        }
        return count;
    }
}
```

---

## Bài 6: Word Search

### Cách 1: Backtracking + DFS
```java
public class WordSearch {
    public static boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (dfs(board, word, i, j, 0)) return true;
            }
        }
        return false;
    }

    private static boolean dfs(char[][] board, String word, int r, int c, int idx) {
        if (idx == word.length()) return true;
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length
            || board[r][c] != word.charAt(idx)) return false;

        char temp = board[r][c];
        board[r][c] = '#';  // Đánh dấu đã dùng
        boolean found = dfs(board, word, r+1, c, idx+1) || dfs(board, word, r-1, c, idx+1)
                      || dfs(board, word, r, c+1, idx+1) || dfs(board, word, r, c-1, idx+1);
        board[r][c] = temp;  // Backtrack
        return found;
    }
}
```

---

## Bài 7: N-Queens

### Cách 1: Backtracking Row by Row
```java
public class NQueens {
    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];
        for (char[] row : board) Arrays.fill(row, '.');
        solve(result, board, 0, n);
        return result;
    }

    private static void solve(List<List<String>> result, char[][] board, int row, int n) {
        if (row == n) {
            List<String> snapshot = new ArrayList<>();
            for (char[] r : board) snapshot.add(new String(r));
            result.add(snapshot);
            return;
        }
        for (int col = 0; col < n; col++) {
            if (isSafe(board, row, col, n)) {
                board[row][col] = 'Q';
                solve(result, board, row + 1, n);
                board[row][col] = '.';
            }
        }
    }

    private static boolean isSafe(char[][] board, int row, int col, int n) {
        for (int i = 0; i < row; i++) if (board[i][col] == 'Q') return false;
        for (int i = row-1, j = col-1; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 'Q') return false;
        for (int i = row-1, j = col+1; i >= 0 && j < n; i--, j++)
            if (board[i][j] == 'Q') return false;
        return true;
    }
}
```

---

## Bài 8: Gas Station

### Cách 1: Greedy O(n)
```java
public class GasStation {
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        int totalGas = 0, totalCost = 0, tank = 0, start = 0;
        for (int i = 0; i < gas.length; i++) {
            totalGas += gas[i]; totalCost += cost[i];
            tank += gas[i] - cost[i];
            if (tank < 0) {
                start = i + 1;  // Reset: trạm hiện tại và trước đều không đi được
                tank = 0;
            }
        }
        return totalGas >= totalCost ? start : -1;
    }
}
```
