# Cấu Trúc Dữ Liệu & Giải Thuật (DSA)

## Phần 1: Phân Tích Độ Phức Tạp

### 1.1 Big-O Notation

Big-O mô tả **giới hạn trên** của tốc độ tăng thời gian chạy theo kích thước input. Dùng để so sánh hiệu quả thuật toán.

| Ký hiệu | Tên | Mô tả | Ví dụ |
|---------|-----|-------|-------|
| O(1) | Hằng số | Không phụ thuộc input | Truy cập array[i], HashMap.get() |
| O(log n) | Logarithm | Giảm nửa mỗi bước | Binary Search, cây cân bằng |
| O(n) | Tuyến tính | Duyệt 1 lần | Tìm max, sum, linear search |
| O(n log n) | Linearithmic | Sắp xếp hiệu quả | Merge Sort, Quick Sort (TB) |
| O(n²) | Bình phương | 2 vòng lặp lồng nhau | Bubble Sort, Selection Sort |
| O(n³) | Lập phương | 3 vòng lặp lồng | Nhân ma trận naive |
| O(2^n) | Mũ | Mỗi phần tử có 2 lựa chọn | Subset enumeration, recursion không memo |
| O(n!) | Giai thừa | Hoán vị | Traveling Salesman (brute force) |

**Quy tắc tính Big-O:**
1. **Bỏ hằng số:** O(3n) → O(n), O(n/2) → O(n)
2. **Giữ số hạng lớn nhất:** O(n² + n) → O(n²), O(n + log n) → O(n)
3. **Phép cộng:** Tuần tự → cộng → giữ lớn nhất. `for O(n) + for O(m)` = O(n + m)
4. **Phép nhân:** Lồng nhau → nhân. `for O(n) { for O(m) }` = O(n × m)

**So sánh thực tế (n = 1,000,000):**

| O(log n) | O(n) | O(n log n) | O(n²) |
|---------|------|-----------|------|
| ~20 operations | 1,000,000 | ~20,000,000 | 1,000,000,000,000 |
| Tức thì | Nhanh | Chấp nhận | Không khả thi |

> **Phỏng vấn:** Phân biệt Best/Average/Worst case. Ví dụ Quick Sort: Best O(n log n), Average O(n log n), Worst O(n²) khi pivot luôn là min/max.

### 1.2 Space Complexity (Độ phức tạp bộ nhớ)

Đo lượng bộ nhớ thêm mà thuật toán sử dụng (ngoài input).

| Thuật toán | Time | Space | Ghi chú |
|-----------|------|-------|---------|
| Binary Search (iterative) | O(log n) | O(1) | In-place |
| Binary Search (recursive) | O(log n) | O(log n) | Call stack |
| Merge Sort | O(n log n) | O(n) | Cần mảng tạm |
| Quick Sort | O(n log n) | O(log n) | In-place, stack đệ quy |
| BFS | O(V + E) | O(V) | Queue + visited set |
| DFS (recursive) | O(V + E) | O(V) | Call stack + visited |

---

## Phần 2: Cấu Trúc Dữ Liệu

### 2.1 Array và Dynamic Array (ArrayList)

**Array (Mảng tĩnh):**
- Kích thước cố định, vùng nhớ liên tiếp
- Truy cập O(1) bằng index
- Thêm/xóa O(n) vì phải dịch phần tử

**ArrayList (Mảng động):**
- Tự tăng kích thước (default: 1.5x khi đầy)
- `add()` amortized O(1), worst case O(n) khi resize
- `add(index)` O(n), `remove(index)` O(n), `get(index)` O(1)

```java
// Kỹ thuật Two Pointers trên array
// Bài toán: Tìm 2 số trong mảng sorted có tổng bằng target
public int[] twoSum(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    while (left < right) {
        int sum = arr[left] + arr[right];
        if (sum == target) return new int[]{left, right};
        else if (sum < target) left++;
        else right--;
    }
    return new int[]{-1, -1};
}

// Sliding Window — Tổng lớn nhất của k phần tử liên tiếp
public int maxSumSubarray(int[] arr, int k) {
    int windowSum = 0;
    for (int i = 0; i < k; i++) windowSum += arr[i];

    int maxSum = windowSum;
    for (int i = k; i < arr.length; i++) {
        windowSum += arr[i] - arr[i - k];  // Trượt cửa sổ
        maxSum = Math.max(maxSum, windowSum);
    }
    return maxSum;
}
```

> **Kỹ thuật phổ biến trên array:** Two Pointers, Sliding Window, Prefix Sum, Kadane's Algorithm.

### 2.2 Linked List

| Thao tác | Array | Singly Linked | Doubly Linked |
|----------|-------|---------------|---------------|
| Truy cập index | O(1) | O(n) | O(n) |
| Thêm đầu | O(n) | O(1) | O(1) |
| Thêm cuối | O(1)* | O(n) hoặc O(1)** | O(1) |
| Xóa đầu | O(n) | O(1) | O(1) |
| Xóa node biết vị trí | O(n) | O(n)*** | O(1) |
| Bộ nhớ thêm | Không | next pointer | next + prev pointer |

> (*) Amortized, (**) Nếu giữ tail pointer, (***) Cần duyệt tìm node trước

```java
// Reverse Linked List — bài kinh điển
public ListNode reverseList(ListNode head) {
    ListNode prev = null, curr = head;
    while (curr != null) {
        ListNode next = curr.next;
        curr.next = prev;    // Đảo chiều
        prev = curr;
        curr = next;
    }
    return prev;
}

// Phát hiện Cycle — Floyd's Tortoise and Hare
public boolean hasCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;       // Bước 1
        fast = fast.next.next;  // Bước 2
        if (slow == fast) return true;
    }
    return false;
}

// Tìm middle — slow/fast pointer
public ListNode findMiddle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    return slow;  // Node giữa
}
```

### 2.3 Stack (Ngăn xếp — LIFO)

**Ứng dụng:** Kiểm tra ngoặc hợp lệ, undo/redo, DFS, parse expression, function call stack.

```java
// Java: Dùng ArrayDeque thay vì Stack class (legacy, synchronized)
Deque<Integer> stack = new ArrayDeque<>();
stack.push(1);          // Thêm đỉnh
stack.peek();           // Xem đỉnh (không xóa)
stack.pop();            // Lấy ra đỉnh

// Bài toán kinh điển: Valid Parentheses
public boolean isValid(String s) {
    Deque<Character> stack = new ArrayDeque<>();
    Map<Character, Character> pairs = Map.of(')', '(', ']', '[', '}', '{');

    for (char c : s.toCharArray()) {
        if (pairs.containsValue(c)) {
            stack.push(c);
        } else if (pairs.containsKey(c)) {
            if (stack.isEmpty() || stack.pop() != pairs.get(c)) return false;
        }
    }
    return stack.isEmpty();
}

// Monotonic Stack — Tìm next greater element
public int[] nextGreaterElement(int[] nums) {
    int[] result = new int[nums.length];
    Arrays.fill(result, -1);
    Deque<Integer> stack = new ArrayDeque<>();  // Lưu index

    for (int i = 0; i < nums.length; i++) {
        while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
            result[stack.pop()] = nums[i];
        }
        stack.push(i);
    }
    return result;
}
```

### 2.4 Queue (Hàng đợi — FIFO)

**Ứng dụng:** BFS, task scheduling, buffer, message queue.

| Implementation | Đặc điểm |
|---------------|----------|
| `ArrayDeque` | Nhanh nhất, O(1) amortized, dùng circular array |
| `LinkedList` | Cũng implement Queue, overhead pointer |
| `PriorityQueue` | Min-heap, O(log n) add/poll |
| `BlockingQueue` | Thread-safe, dùng trong producer-consumer |

```java
Queue<Integer> queue = new ArrayDeque<>();
queue.offer(1);    // Thêm cuối (không throw exception)
queue.peek();      // Xem đầu
queue.poll();      // Lấy đầu

// PriorityQueue — tự động sắp xếp
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);  // Min by [1]
pq.offer(new int[]{0, 5});
pq.offer(new int[]{1, 2});
pq.poll();  // [1, 2] — nhỏ nhất

// Deque — cả Stack lẫn Queue
Deque<Integer> deque = new ArrayDeque<>();
deque.offerFirst(1);   // Thêm đầu
deque.offerLast(2);    // Thêm cuối
deque.pollFirst();     // Lấy đầu
deque.pollLast();      // Lấy cuối
```

### 2.5 Hash Table (HashMap)

**Cơ chế hoạt động:**
1. Tính `hashCode()` của key
2. `index = hash & (capacity - 1)` — xác định bucket
3. Nếu collision → Java 8+: LinkedList (≤8 nodes) → Red-Black Tree (>8 nodes)

| Thao tác | Average | Worst (nhiều collision) |
|----------|---------|----------------------|
| `put()` | O(1) | O(n) hoặc O(log n)* |
| `get()` | O(1) | O(n) hoặc O(log n)* |
| `remove()` | O(1) | O(n) hoặc O(log n)* |
| `containsKey()` | O(1) | O(n) hoặc O(log n)* |

> (*) Java 8+ chuyển bucket sang Tree khi >8 entries → O(log n) thay vì O(n).

```java
// Counting Pattern — đếm tần suất
Map<Character, Integer> freq = new HashMap<>();
for (char c : text.toCharArray()) {
    freq.merge(c, 1, Integer::sum);  // Gọn hơn getOrDefault
}

// Two Sum (HashMap) — O(n) time, O(n) space
public int[] twoSum(int[] nums, int target) {
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

// Group Anagrams
public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> map = new HashMap<>();
    for (String s : strs) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        String key = new String(chars);
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
    }
    return new ArrayList<>(map.values());
}
```

### 2.6 Tree (Cây)

**Binary Tree — Cây nhị phân:**
```
       1          Inorder  (LNR): 4, 2, 5, 1, 6, 3, 7
      / \         Preorder (NLR): 1, 2, 4, 5, 3, 6, 7
     2   3        Postorder(LRN): 4, 5, 2, 6, 7, 3, 1
    / \ / \       Level-order:    1, 2, 3, 4, 5, 6, 7
   4  5 6  7
```

**Binary Search Tree (BST):**
- Left child < Parent < Right child
- Inorder traversal → mảng sorted
- Search/Insert/Delete: O(h) — h = chiều cao

| Loại BST | Search | Insert | Delete | Ghi chú |
|----------|--------|--------|--------|---------|
| BST thường | O(n) worst | O(n) worst | O(n) worst | Degenerate khi sorted input |
| AVL Tree | O(log n) | O(log n) | O(log n) | Strictly balanced (diff ≤ 1) |
| Red-Black Tree | O(log n) | O(log n) | O(log n) | Loosely balanced, ít rotation hơn AVL |

```java
// Inorder Traversal (Recursive)
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    inorder(root, result);
    return result;
}

private void inorder(TreeNode node, List<Integer> result) {
    if (node == null) return;
    inorder(node.left, result);
    result.add(node.val);
    inorder(node.right, result);
}

// Inorder Traversal (Iterative — dùng Stack)
public List<Integer> inorderIterative(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode curr = root;

    while (curr != null || !stack.isEmpty()) {
        while (curr != null) {
            stack.push(curr);
            curr = curr.left;
        }
        curr = stack.pop();
        result.add(curr.val);
        curr = curr.right;
    }
    return result;
}

// Level Order Traversal — BFS trên cây
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        int size = queue.size();
        List<Integer> level = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            level.add(node.val);
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        result.add(level);
    }
    return result;
}

// Validate BST
public boolean isValidBST(TreeNode root) {
    return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
}

private boolean validate(TreeNode node, long min, long max) {
    if (node == null) return true;
    if (node.val <= min || node.val >= max) return false;
    return validate(node.left, min, node.val) &&
           validate(node.right, node.val, max);
}
```

### 2.7 Heap (Đống)

Min-Heap: parent ≤ children. Max-Heap: parent ≥ children.

**Ứng dụng:** Priority Queue, Top-K problems, Heap Sort, Median finding.

| Thao tác | Time |
|----------|------|
| `peek()` (min/max) | O(1) |
| `offer()` (insert) | O(log n) |
| `poll()` (extract min/max) | O(log n) |
| `heapify` (build heap) | O(n) |

```java
// Top K Frequent Elements
public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    for (int n : nums) freq.merge(n, 1, Integer::sum);

    // Min-heap theo frequency
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    for (var entry : freq.entrySet()) {
        pq.offer(new int[]{entry.getKey(), entry.getValue()});
        if (pq.size() > k) pq.poll();  // Giữ k phần tử nhiều nhất
    }

    return pq.stream().mapToInt(a -> a[0]).toArray();
}
```

### 2.8 Graph (Đồ thị)

**Cách biểu diễn:**

| Cách | Bộ nhớ | Kiểm tra cạnh | Duyệt neighbors | Phù hợp |
|------|--------|---------------|-----------------|---------|
| Ma trận kề | O(V²) | O(1) | O(V) | Đồ thị dense |
| Danh sách kề | O(V + E) | O(degree) | O(degree) | Đồ thị sparse (phổ biến) |

```java
// Adjacency List
Map<Integer, List<Integer>> graph = new HashMap<>();

// Thêm cạnh (undirected)
void addEdge(int u, int v) {
    graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
    graph.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
}
```

---

## Phần 3: Thuật Toán

### 3.1 Sắp xếp (Sorting)

| Thuật toán | Best | Average | Worst | Space | Ổn định | Khi nào dùng |
|-----------|------|---------|-------|-------|---------|-------------|
| Bubble Sort | O(n) | O(n²) | O(n²) | O(1) | Có | Giáo dục, dữ liệu gần sorted |
| Selection Sort | O(n²) | O(n²) | O(n²) | O(1) | Không | Khi số lần swap quan trọng |
| Insertion Sort | O(n) | O(n²) | O(n²) | O(1) | Có | Dữ liệu nhỏ hoặc gần sorted |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) | Có | Cần stable sort, linked list |
| Quick Sort | O(n log n) | O(n log n) | O(n²) | O(log n) | Không | Đa số trường hợp (nhanh nhất thực tế) |
| Heap Sort | O(n log n) | O(n log n) | O(n log n) | O(1) | Không | Cần guaranteed O(n log n), O(1) space |
| Counting Sort | O(n + k) | O(n + k) | O(n + k) | O(k) | Có | Số nguyên nhỏ, range hẹp |
| Radix Sort | O(d × n) | O(d × n) | O(d × n) | O(n + k) | Có | Số nguyên/chuỗi, d chữ số |

> **Thực tế:** `Arrays.sort()` dùng Dual-Pivot Quick Sort cho primitives, TimSort (hybrid Merge+Insertion) cho Objects. `Collections.sort()` dùng TimSort.

**Merge Sort:**
```java
public static void mergeSort(int[] arr, int left, int right) {
    if (left < right) {
        int mid = left + (right - left) / 2;  // Tránh overflow
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }
}

private static void merge(int[] arr, int left, int mid, int right) {
    int[] temp = new int[right - left + 1];
    int i = left, j = mid + 1, k = 0;
    while (i <= mid && j <= right) {
        temp[k++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];
    }
    while (i <= mid) temp[k++] = arr[i++];
    while (j <= right) temp[k++] = arr[j++];
    System.arraycopy(temp, 0, arr, left, temp.length);
}
```

**Quick Sort:**
```java
public static void quickSort(int[] arr, int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        quickSort(arr, low, pi - 1);
        quickSort(arr, pi + 1, high);
    }
}

private static int partition(int[] arr, int low, int high) {
    int pivot = arr[high];  // Chọn pivot cuối (có thể random để tránh worst case)
    int i = low - 1;
    for (int j = low; j < high; j++) {
        if (arr[j] < pivot) {
            i++;
            int temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
        }
    }
    int temp = arr[i + 1]; arr[i + 1] = arr[high]; arr[high] = temp;
    return i + 1;
}
```

### 3.2 Tìm kiếm (Searching)

**Binary Search — Kỹ thuật quan trọng nhất:**

```java
// Standard Binary Search
public static int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;  // Tránh integer overflow
        if (arr[mid] == target) return mid;
        else if (arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}

// Binary Search biến thể: Tìm vị trí chèn (lower bound)
public static int lowerBound(int[] arr, int target) {
    int left = 0, right = arr.length;
    while (left < right) {
        int mid = left + (right - left) / 2;
        if (arr[mid] < target) left = mid + 1;
        else right = mid;
    }
    return left;  // Vị trí đầu tiên >= target
}

// Binary Search on Answer — Tìm giá trị thỏa mãn điều kiện
// Ví dụ: Tìm căn bậc 2 của n (integer)
public int mySqrt(int x) {
    long left = 0, right = x;
    while (left <= right) {
        long mid = left + (right - left) / 2;
        if (mid * mid <= x) left = mid + 1;
        else right = mid - 1;
    }
    return (int) right;
}
```

> **Binary Search áp dụng rộng:** Không chỉ cho mảng sorted. Bất kỳ bài toán nào có tính chất monotonic (nếu f(x) đúng thì f(x+1) cũng đúng) đều có thể binary search.

### 3.3 Duyệt đồ thị

**BFS (Breadth-First Search) — Duyệt theo chiều rộng:**
- Dùng Queue (FIFO)
- Tìm **đường đi ngắn nhất** trong đồ thị không trọng số
- Time: O(V + E), Space: O(V)

```java
public List<Integer> bfs(Map<Integer, List<Integer>> graph, int start) {
    List<Integer> result = new ArrayList<>();
    Set<Integer> visited = new HashSet<>();
    Queue<Integer> queue = new LinkedList<>();

    queue.offer(start);
    visited.add(start);

    while (!queue.isEmpty()) {
        int node = queue.poll();
        result.add(node);
        for (int neighbor : graph.getOrDefault(node, List.of())) {
            if (visited.add(neighbor)) {
                queue.offer(neighbor);
            }
        }
    }
    return result;
}

// BFS — Shortest path (unweighted graph)
public int shortestPath(Map<Integer, List<Integer>> graph, int start, int end) {
    if (start == end) return 0;
    Queue<int[]> queue = new LinkedList<>();  // {node, distance}
    Set<Integer> visited = new HashSet<>();
    queue.offer(new int[]{start, 0});
    visited.add(start);

    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        for (int neighbor : graph.getOrDefault(curr[0], List.of())) {
            if (neighbor == end) return curr[1] + 1;
            if (visited.add(neighbor)) {
                queue.offer(new int[]{neighbor, curr[1] + 1});
            }
        }
    }
    return -1;  // Không tìm thấy
}
```

**DFS (Depth-First Search) — Duyệt theo chiều sâu:**
- Dùng Stack (hoặc đệ quy)
- Phát hiện cycle, topological sort, connected components
- Time: O(V + E), Space: O(V)

```java
public List<Integer> dfs(Map<Integer, List<Integer>> graph, int start) {
    List<Integer> result = new ArrayList<>();
    Set<Integer> visited = new HashSet<>();
    dfsHelper(graph, start, visited, result);
    return result;
}

private void dfsHelper(Map<Integer, List<Integer>> graph, int node,
                       Set<Integer> visited, List<Integer> result) {
    visited.add(node);
    result.add(node);
    for (int neighbor : graph.getOrDefault(node, List.of())) {
        if (!visited.contains(neighbor)) {
            dfsHelper(graph, neighbor, visited, result);
        }
    }
}
```

**So sánh BFS vs DFS:**

| Tiêu chí | BFS | DFS |
|---------|-----|-----|
| Cấu trúc | Queue (FIFO) | Stack/Recursion (LIFO) |
| Đường ngắn nhất | Có (unweighted) | Không |
| Bộ nhớ | O(branching^depth) — có thể lớn | O(depth) — thường nhỏ hơn |
| Duyệt level | Dễ dàng | Khó |
| Phát hiện cycle | Có | Có |
| Topological Sort | Kahn's algorithm | Postorder DFS |
| Khi nào dùng | Tìm đường ngắn nhất, nearest neighbor | Phát hiện cycle, path finding, backtracking |

### 3.4 Dijkstra's Algorithm

Tìm đường đi ngắn nhất từ 1 đỉnh đến tất cả đỉnh khác (đồ thị có trọng số **không âm**).

```java
public Map<Integer, Integer> dijkstra(Map<Integer, List<int[]>> graph, int start) {
    Map<Integer, Integer> dist = new HashMap<>();
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    pq.offer(new int[]{start, 0});

    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int node = curr[0], d = curr[1];
        if (dist.containsKey(node)) continue;  // Đã visited
        dist.put(node, d);

        for (int[] edge : graph.getOrDefault(node, List.of())) {
            int neighbor = edge[0], weight = edge[1];
            if (!dist.containsKey(neighbor)) {
                pq.offer(new int[]{neighbor, d + weight});
            }
        }
    }
    return dist;
}
```

> Time: O((V + E) log V) với PriorityQueue.

### 3.5 Quy hoạch động (Dynamic Programming)

**Nhận biết bài DP:**
1. **Overlapping Subproblems:** Bài toán lặp lại các bài toán con
2. **Optimal Substructure:** Lời giải tối ưu chứa lời giải tối ưu của bài toán con

**Hai cách tiếp cận:**
- **Top-down (Memoization):** Đệ quy + cache → dễ nghĩ, dễ code
- **Bottom-up (Tabulation):** Bảng từ nhỏ đến lớn → hiệu quả hơn (không có overhead đệ quy)

```java
// Fibonacci — 3 cách DP
// Top-down: Memoization
public int fibMemo(int n, Map<Integer, Integer> memo) {
    if (n <= 1) return n;
    if (memo.containsKey(n)) return memo.get(n);
    int result = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
    memo.put(n, result);
    return result;
}

// Bottom-up: Tabulation
public int fibTab(int n) {
    if (n <= 1) return n;
    int[] dp = new int[n + 1];
    dp[0] = 0; dp[1] = 1;
    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2];
    }
    return dp[n];
}

// Tối ưu Space O(1)
public int fibOptimal(int n) {
    if (n <= 1) return n;
    int a = 0, b = 1;
    for (int i = 2; i <= n; i++) {
        int temp = a + b;
        a = b;
        b = temp;
    }
    return b;
}
```

**Các dạng DP phổ biến:**

| Dạng bài | Ví dụ | Recurrence |
|---------|-------|-----------|
| 1D DP | Climbing Stairs, House Robber | dp[i] = f(dp[i-1], dp[i-2]) |
| 2D DP | Unique Paths, Edit Distance | dp[i][j] = f(dp[i-1][j], dp[i][j-1]) |
| Knapsack | 0/1 Knapsack, Coin Change | dp[i][w] = max(dp[i-1][w], dp[i-1][w-wi] + vi) |
| Subsequence | LCS, LIS | dp[i][j] phụ thuộc match/không match |
| String | Edit Distance, Palindrome | dp[i][j] = f(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]) |

```java
// Longest Common Subsequence (LCS)
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

// Coin Change — Minimum coins to make amount
public int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, amount + 1);
    dp[0] = 0;

    for (int i = 1; i <= amount; i++) {
        for (int coin : coins) {
            if (coin <= i) {
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
    }
    return dp[amount] > amount ? -1 : dp[amount];
}
```

### 3.6 Thuật toán tham lam (Greedy)

Chọn phương án **tốt nhất tại mỗi bước** mà không xem xét toàn cục. Nhanh hơn DP nhưng không phải lúc nào cũng đúng.

**Khi nào Greedy đúng?** Cần chứng minh **Greedy Choice Property** (lựa chọn cục bộ tối ưu → toàn cục tối ưu).

```java
// Interval Scheduling — Chọn nhiều hoạt động nhất không trùng
public int maxActivities(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[1] - b[1]);  // Sort theo end time
    int count = 1, end = intervals[0][1];

    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] >= end) {  // Không trùng
            count++;
            end = intervals[i][1];
        }
    }
    return count;
}
```

### 3.7 Quay lui (Backtracking)

Thử tất cả khả năng, **quay lui khi gặp ngõ cụt**. Template chung:

```java
void backtrack(state, choices) {
    if (isGoal(state)) {
        results.add(copy(state));
        return;
    }
    for (choice : choices) {
        if (isValid(choice)) {
            make(choice);           // Chọn
            backtrack(newState, remainingChoices);
            undo(choice);           // Bỏ chọn (backtrack)
        }
    }
}
```

```java
// N-Queens
public List<List<String>> solveNQueens(int n) {
    List<List<String>> results = new ArrayList<>();
    int[] queens = new int[n];
    Arrays.fill(queens, -1);
    solve(0, n, queens, results, new HashSet<>(), new HashSet<>(), new HashSet<>());
    return results;
}

private void solve(int row, int n, int[] queens, List<List<String>> results,
                   Set<Integer> cols, Set<Integer> diag1, Set<Integer> diag2) {
    if (row == n) {
        results.add(buildBoard(queens, n));
        return;
    }
    for (int col = 0; col < n; col++) {
        if (cols.contains(col) || diag1.contains(row - col) || diag2.contains(row + col)) {
            continue;
        }
        queens[row] = col;
        cols.add(col); diag1.add(row - col); diag2.add(row + col);
        solve(row + 1, n, queens, results, cols, diag1, diag2);
        cols.remove(col); diag1.remove(row - col); diag2.remove(row + col);
    }
}
```

---

## Tổng kết: Chọn Cấu Trúc Dữ Liệu

| Yêu cầu | CTDL phù hợp | Lý do |
|---------|-------------|-------|
| Truy cập nhanh theo index | Array / ArrayList | O(1) random access |
| Thêm/xóa đầu/cuối nhanh | LinkedList / ArrayDeque | O(1) |
| Kiểm tra tồn tại nhanh | HashSet | O(1) average |
| Key-Value lookup | HashMap | O(1) average |
| Dữ liệu sắp xếp | TreeSet / TreeMap | O(log n), tự sắp xếp |
| LIFO (undo, DFS) | ArrayDeque (as Stack) | O(1) push/pop |
| FIFO (BFS, scheduling) | ArrayDeque (as Queue) | O(1) offer/poll |
| Top-K, priority | PriorityQueue (Heap) | O(log n) insert/extract |
| Chuỗi con, pattern matching | Trie | O(m) search, m = length |
