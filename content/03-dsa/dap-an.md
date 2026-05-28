# DSA - Đáp Án Chi Tiết

## Bài 1: Two Sum

### Cách 1: Brute Force O(n²)
```java
public class TwoSum_BruteForce {
    public static int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) return new int[]{i, j};
            }
        }
        return new int[]{};
    }
}
```

### Cách 2: HashMap O(n) - Tối ưu
```java
public class TwoSum_HashMap {
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }
}
```

### Cách 3: Sắp xếp + Two Pointers O(n log n)
```java
public class TwoSum_TwoPointers {
    public static int[] twoSum(int[] nums, int target) {
        int[][] indexed = new int[nums.length][2];
        for (int i = 0; i < nums.length; i++) {
            indexed[i] = new int[]{nums[i], i};
        }
        Arrays.sort(indexed, Comparator.comparingInt(a -> a[0]));

        int left = 0, right = nums.length - 1;
        while (left < right) {
            int sum = indexed[left][0] + indexed[right][0];
            if (sum == target) return new int[]{indexed[left][1], indexed[right][1]};
            else if (sum < target) left++;
            else right--;
        }
        return new int[]{};
    }
}
```

---

## Bài 2: Kiểm Tra Dấu Ngoặc

### Cách 1: Stack
```java
public class ValidParentheses_Stack {
    public static boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        Map<Character, Character> pairs = Map.of(')', '(', '}', '{', ']', '[');

        for (char c : s.toCharArray()) {
            if (pairs.containsValue(c)) {
                stack.push(c);
            } else if (pairs.containsKey(c)) {
                if (stack.isEmpty() || stack.pop() != pairs.get(c)) return false;
            }
        }
        return stack.isEmpty();
    }
}
```

### Cách 2: Thay thế chuỗi (không tối ưu nhưng dễ hiểu)
```java
public class ValidParentheses_Replace {
    public static boolean isValid(String s) {
        while (s.contains("()") || s.contains("{}") || s.contains("[]")) {
            s = s.replace("()", "").replace("{}", "").replace("[]", "");
        }
        return s.isEmpty();
    }
}
```

---

## Bài 3: Đảo Ngược Linked List

### Cách 1: Iterative
```java
public class ReverseLinkedList {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static ListNode reverseIterative(ListNode head) {
        ListNode prev = null, current = head;
        while (current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        return prev;
    }
}
```

### Cách 2: Recursive
```java
public class ReverseLinkedList_Recursive {
    public static ListNode reverseRecursive(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode newHead = reverseRecursive(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }
}
```

### Cách 3: Sử dụng Stack
```java
public class ReverseLinkedList_Stack {
    public static ListNode reverseWithStack(ListNode head) {
        if (head == null) return null;
        Deque<ListNode> stack = new ArrayDeque<>();
        ListNode current = head;
        while (current != null) {
            stack.push(current);
            current = current.next;
        }
        ListNode newHead = stack.pop();
        current = newHead;
        while (!stack.isEmpty()) {
            current.next = stack.pop();
            current = current.next;
        }
        current.next = null;
        return newHead;
    }
}
```

---

## Bài 4: Mê Cung - BFS

```java
public class MazeBFS {
    public static int shortestPath(int[][] maze) {
        int m = maze.length, n = maze[0].length;
        if (maze[0][0] == 1 || maze[m - 1][n - 1] == 1) return -1;

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 0, 1}); // row, col, distance
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0], col = cell[1], dist = cell[2];

            if (row == m - 1 && col == n - 1) return dist;

            for (int[] dir : dirs) {
                int nr = row + dir[0], nc = col + dir[1];
                if (nr >= 0 && nr < m && nc >= 0 && nc < n
                    && maze[nr][nc] == 0 && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    queue.add(new int[]{nr, nc, dist + 1});
                }
            }
        }
        return -1;
    }
}
```

---

## Bài 5: Bài Toán Ba Lô

### Cách 1: DP Bottom-up
```java
public class Knapsack_DP {
    public static int knapsack(int[] weights, int[] values, int W) {
        int n = weights.length;
        int[][] dp = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                dp[i][w] = dp[i - 1][w]; // Không chọn vật i
                if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(dp[i][w],
                        dp[i - 1][w - weights[i - 1]] + values[i - 1]); // Chọn vật i
                }
            }
        }
        return dp[n][W];
    }
}
```

### Cách 2: DP tối ưu bộ nhớ O(W)
```java
public class Knapsack_Optimized {
    public static int knapsack(int[] weights, int[] values, int W) {
        int[] dp = new int[W + 1];
        for (int i = 0; i < weights.length; i++) {
            for (int w = W; w >= weights[i]; w--) {
                dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
            }
        }
        return dp[W];
    }
}
```

### Cách 3: Đệ quy + Memoization
```java
public class Knapsack_Memo {
    public static int knapsack(int[] w, int[] v, int W) {
        int[][] memo = new int[w.length][W + 1];
        for (int[] row : memo) Arrays.fill(row, -1);
        return solve(w, v, w.length - 1, W, memo);
    }

    private static int solve(int[] w, int[] v, int i, int remaining, int[][] memo) {
        if (i < 0 || remaining <= 0) return 0;
        if (memo[i][remaining] != -1) return memo[i][remaining];
        int skip = solve(w, v, i - 1, remaining, memo);
        int take = w[i] <= remaining ? v[i] + solve(w, v, i - 1, remaining - w[i], memo) : 0;
        return memo[i][remaining] = Math.max(skip, take);
    }
}
```

---

## Bài 6: LCA - Tổ Tiên Chung Gần Nhất

```java
public class LCA {
    public static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) return root;
        return left != null ? left : right;
    }
}
```

---

## Bài 8: Longest Increasing Subsequence

### Cách 1: DP O(n²)
```java
public class LIS_DP {
    public static int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        int maxLen = 1;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }
}
```

### Cách 2: Binary Search O(n log n)
```java
public class LIS_BinarySearch {
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
```

---

## Bài 10: LRU Cache

```java
public class LRUCache {
    private static class Node {
        int key, value;
        Node prev, next;
        Node(int key, int value) { this.key = key; this.value = value; }
    }

    private final int capacity;
    private final Map<Integer, Node> map;
    private final Node head, tail; // Sentinel nodes

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) return -1;
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node node = map.get(key);
        if (node != null) {
            node.value = value;
            moveToHead(node);
        } else {
            node = new Node(key, value);
            map.put(key, node);
            addToHead(node);
            if (map.size() > capacity) {
                Node removed = removeTail();
                map.remove(removed.key);
            }
        }
    }

    private void addToHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }

    private Node removeTail() {
        Node node = tail.prev;
        removeNode(node);
        return node;
    }
}
```
