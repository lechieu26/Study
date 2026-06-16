# Greedy & Backtracking

---

# Phần 1: Greedy (Tham lam)

## 1. Khái niệm

**Greedy** luôn chọn **lựa chọn tốt nhất tại mỗi bước** (locally optimal), hy vọng đạt lời giải tối ưu toàn cục (globally optimal).

**Điều kiện để Greedy đúng:**
1. **Greedy Choice Property:** Chọn tốt nhất tại mỗi bước dẫn đến lời giải tối ưu
2. **Optimal Substructure:** Bài toán con cũng có cấu trúc tối ưu

> **Chú ý:** Greedy KHÔNG phải lúc nào cũng đúng! Cần chứng minh hoặc phản ví dụ.

## 2. Ví dụ kinh điển

### Activity Selection (Chọn hoạt động)

```java
// Chọn nhiều hoạt động nhất không trùng thời gian
// Greedy: sắp xếp theo thời gian kết thúc, chọn sớm nhất
public int maxActivities(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[1] - b[1]);  // Sort by end time
    int count = 1, end = intervals[0][1];
    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] >= end) {
            count++;
            end = intervals[i][1];
        }
    }
    return count;
}
```

### Jump Game

```java
// Kiểm tra có thể nhảy đến cuối mảng không
public boolean canJump(int[] nums) {
    int maxReach = 0;
    for (int i = 0; i < nums.length; i++) {
        if (i > maxReach) return false;
        maxReach = Math.max(maxReach, i + nums[i]);
    }
    return true;
}

// Jump Game II — Ít bước nhất
public int jump(int[] nums) {
    int jumps = 0, currEnd = 0, farthest = 0;
    for (int i = 0; i < nums.length - 1; i++) {
        farthest = Math.max(farthest, i + nums[i]);
        if (i == currEnd) {
            jumps++;
            currEnd = farthest;
        }
    }
    return jumps;
}
```

### Fractional Knapsack

```java
// Khác 0/1 Knapsack: có thể lấy một phần item
// Greedy: chọn item có value/weight cao nhất
public double fractionalKnapsack(int[] w, int[] v, int capacity) {
    int n = w.length;
    double[][] ratio = new double[n][2];
    for (int i = 0; i < n; i++) ratio[i] = new double[]{(double) v[i] / w[i], i};
    Arrays.sort(ratio, (a, b) -> Double.compare(b[0], a[0]));

    double totalValue = 0;
    for (double[] r : ratio) {
        int idx = (int) r[1];
        if (capacity >= w[idx]) {
            totalValue += v[idx];
            capacity -= w[idx];
        } else {
            totalValue += r[0] * capacity;
            break;
        }
    }
    return totalValue;
}
```

### Interval Scheduling

```java
// Minimum number of intervals to remove to make non-overlapping
public int eraseOverlapIntervals(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
    int count = 0, end = Integer.MIN_VALUE;
    for (int[] interval : intervals) {
        if (interval[0] >= end) end = interval[1];
        else count++;  // Overlap → remove
    }
    return count;
}

// Minimum meeting rooms
public int minMeetingRooms(int[][] intervals) {
    int[] starts = new int[intervals.length];
    int[] ends = new int[intervals.length];
    for (int i = 0; i < intervals.length; i++) {
        starts[i] = intervals[i][0];
        ends[i] = intervals[i][1];
    }
    Arrays.sort(starts); Arrays.sort(ends);
    int rooms = 0, endPtr = 0;
    for (int start : starts) {
        if (start < ends[endPtr]) rooms++;
        else endPtr++;
    }
    return rooms;
}
```

## 3. Greedy vs DP

| Greedy | DP |
|--------|-----|
| Chọn tốt nhất tại mỗi bước | Xét tất cả lựa chọn |
| Nhanh hơn (thường O(n log n)) | Chậm hơn (O(n²) hoặc hơn) |
| Không phải lúc nào cũng đúng | Luôn đúng nếu đúng recurrence |
| Khó chứng minh đúng | Dễ chứng minh hơn |

---

# Phần 2: Backtracking (Quay lui)

## 1. Khái niệm

**Backtracking** thử tất cả khả năng, **quay lui** khi phát hiện hướng đi sai (pruning).

```
Quy trình:
1. CHOOSE: Chọn 1 lựa chọn
2. EXPLORE: Đệ quy với lựa chọn đó
3. UNCHOOSE: Quay lui, bỏ lựa chọn
```

## 2. Template Backtracking

```java
void backtrack(List<List<Integer>> result, List<Integer> current, int[] nums, int start) {
    if (isComplete(current)) {
        result.add(new ArrayList<>(current));  // Deep copy!
        return;
    }
    for (int i = start; i < nums.length; i++) {
        // Pruning: bỏ qua nếu không hợp lệ
        if (!isValid(i)) continue;

        current.add(nums[i]);           // CHOOSE
        backtrack(result, current, nums, i + 1);  // EXPLORE
        current.remove(current.size() - 1);       // UNCHOOSE
    }
}
```

## 3. Ví dụ kinh điển

### Subsets (Tập con)

```java
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), nums, 0);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> current, int[] nums, int start) {
    result.add(new ArrayList<>(current));  // Mỗi trạng thái là 1 subset
    for (int i = start; i < nums.length; i++) {
        current.add(nums[i]);
        backtrack(result, current, nums, i + 1);
        current.remove(current.size() - 1);
    }
}
// nums = [1,2,3] → [[], [1], [1,2], [1,2,3], [1,3], [2], [2,3], [3]]
```

### Permutations (Hoán vị)

```java
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    boolean[] used = new boolean[nums.length];
    backtrackPermute(result, new ArrayList<>(), nums, used);
    return result;
}

private void backtrackPermute(List<List<Integer>> result, List<Integer> current,
                               int[] nums, boolean[] used) {
    if (current.size() == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        used[i] = true;
        current.add(nums[i]);
        backtrackPermute(result, current, nums, used);
        current.remove(current.size() - 1);
        used[i] = false;
    }
}
```

### Combinations (Tổ hợp)

```java
// C(n, k) — chọn k phần tử từ 1..n
public List<List<Integer>> combine(int n, int k) {
    List<List<Integer>> result = new ArrayList<>();
    backtrackCombine(result, new ArrayList<>(), n, k, 1);
    return result;
}

private void backtrackCombine(List<List<Integer>> result, List<Integer> current,
                               int n, int k, int start) {
    if (current.size() == k) {
        result.add(new ArrayList<>(current));
        return;
    }
    for (int i = start; i <= n - (k - current.size()) + 1; i++) {  // Pruning
        current.add(i);
        backtrackCombine(result, current, n, k, i + 1);
        current.remove(current.size() - 1);
    }
}
```

### N-Queens

```java
public List<List<String>> solveNQueens(int n) {
    List<List<String>> result = new ArrayList<>();
    char[][] board = new char[n][n];
    for (char[] row : board) Arrays.fill(row, '.');
    solve(result, board, 0, n);
    return result;
}

private void solve(List<List<String>> result, char[][] board, int row, int n) {
    if (row == n) {
        List<String> snapshot = new ArrayList<>();
        for (char[] r : board) snapshot.add(new String(r));
        result.add(snapshot);
        return;
    }
    for (int col = 0; col < n; col++) {
        if (isQueenSafe(board, row, col, n)) {
            board[row][col] = 'Q';
            solve(result, board, row + 1, n);
            board[row][col] = '.';
        }
    }
}

private boolean isQueenSafe(char[][] board, int row, int col, int n) {
    for (int i = 0; i < row; i++) if (board[i][col] == 'Q') return false;
    for (int i = row-1, j = col-1; i >= 0 && j >= 0; i--, j--)
        if (board[i][j] == 'Q') return false;
    for (int i = row-1, j = col+1; i >= 0 && j < n; i--, j++)
        if (board[i][j] == 'Q') return false;
    return true;
}
```

### Sudoku Solver

```java
public void solveSudoku(char[][] board) {
    solve(board);
}

private boolean solve(char[][] board) {
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            if (board[i][j] == '.') {
                for (char c = '1'; c <= '9'; c++) {
                    if (isValid(board, i, j, c)) {
                        board[i][j] = c;
                        if (solve(board)) return true;
                        board[i][j] = '.';
                    }
                }
                return false;  // Không số nào hợp lệ → quay lui
            }
        }
    }
    return true;  // Đã điền hết
}
```

## 4. Khi nào dùng Greedy vs Backtracking?

| Greedy | Backtracking |
|--------|-------------|
| Tối ưu hóa (min/max) | Liệt kê tất cả lời giải |
| Activity Selection, Intervals | Subsets, Permutations, Combinations |
| Huffman Coding | N-Queens, Sudoku |
| Jump Game | Word Search, Path finding |
| Có thể chứng minh greedy đúng | Khi cần thử tất cả khả năng |

> **Phỏng vấn thường hỏi:** 
> - Greedy: Jump Game, Meeting Rooms, Task Scheduler, Gas Station
> - Backtracking: Subsets, Permutations, Combination Sum, N-Queens, Word Search, Palindrome Partitioning
