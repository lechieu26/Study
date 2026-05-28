# Cấu Trúc Dữ Liệu và Giải Thuật (DSA) - Lý Thuyết

## Phần 1: Độ Phức Tạp Thuật Toán

### 1.1 Big-O Notation
| Ký hiệu | Tên gọi | Ví dụ |
|----------|---------|-------|
| O(1) | Hằng số | Truy cập mảng theo index |
| O(log n) | Logarit | Tìm kiếm nhị phân |
| O(n) | Tuyến tính | Duyệt mảng |
| O(n log n) | Log-tuyến tính | Merge Sort, Quick Sort |
| O(n²) | Bậc hai | Bubble Sort, Selection Sort |
| O(2ⁿ) | Mũ | Bài toán tập con |
| O(n!) | Giai thừa | Bài toán hoán vị |

### 1.2 Quy tắc tính Big-O
- Bỏ hằng số: O(2n) → O(n)
- Lấy hạng lớn nhất: O(n² + n) → O(n²)
- Nhân khi vòng lặp lồng nhau: O(n) × O(n) = O(n²)
- Cộng khi tuần tự: O(n) + O(m) = O(n + m)

---

## Phần 2: Cấu Trúc Dữ Liệu

### 2.1 Mảng (Array) và Danh Sách Liên Kết (Linked List)

**Mảng:**
- Truy cập ngẫu nhiên O(1)
- Thêm/xóa O(n) (phải dịch chuyển)
- Kích thước cố định (hoặc dynamic array)

**Linked List:**
```
[data|next] → [data|next] → [data|next] → null
     ^head
```
- Truy cập O(n)
- Thêm/xóa đầu O(1), giữa O(n)
- Không cần bộ nhớ liên tục

**Doubly Linked List:**
```
null ← [prev|data|next] ⇄ [prev|data|next] ⇄ [prev|data|next] → null
```

```java
public class DanhSachLienKet<T> {
    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; }
    }

    private Node<T> head;
    private int size;

    public void themDau(T data) {
        Node<T> node = new Node<>(data);
        node.next = head;
        head = node;
        size++;
    }

    public void themCuoi(T data) {
        Node<T> node = new Node<>(data);
        if (head == null) { head = node; }
        else {
            Node<T> current = head;
            while (current.next != null) current = current.next;
            current.next = node;
        }
        size++;
    }

    public T xoaDau() {
        if (head == null) throw new NoSuchElementException();
        T data = head.data;
        head = head.next;
        size--;
        return data;
    }
}
```

### 2.2 Stack (Ngăn xếp) và Queue (Hàng đợi)

**Stack (LIFO - Last In First Out):**
```
| top |  ← push/pop
|  3  |
|  2  |
|  1  |
+-----+
```
- Ứng dụng: Undo, kiểm tra dấu ngoặc, DFS, đánh giá biểu thức.

**Queue (FIFO - First In First Out):**
```
front → [1] → [2] → [3] ← rear (enqueue)
 (dequeue)
```
- Ứng dụng: BFS, task scheduling, message queue.

**Priority Queue (Hàng đợi ưu tiên):**
- Phần tử có độ ưu tiên cao nhất được lấy ra trước.
- Thường implement bằng Heap.
- enqueue O(log n), dequeue O(log n).

### 2.3 Hash Table (Bảng băm)
```
Index: 0  → null
       1  → [Key1, Value1] → [Key5, Value5]  (chaining)
       2  → [Key2, Value2]
       3  → null
       4  → [Key4, Value4]
```

**Xử lý va chạm (Collision):**
1. **Chaining:** Mỗi bucket là một linked list.
2. **Open Addressing:** Linear probing, Quadratic probing, Double hashing.

**Load Factor:** λ = n/m (số phần tử / số bucket)
- Khi λ > 0.75 → rehash (tăng kích thước gấp đôi)

### 2.4 Cây (Tree)

**Cây nhị phân tìm kiếm (BST):**
```
        8
       / \
      3   10
     / \    \
    1   6    14
       / \   /
      4   7 13
```
- Tìm kiếm, thêm, xóa: O(h) - h là chiều cao
- Trường hợp xấu nhất (cây lệch): O(n)
- Cây cân bằng (AVL, Red-Black): O(log n)

**Duyệt cây:**
- **In-order (LNR):** 1, 3, 4, 6, 7, 8, 10, 13, 14 (sắp xếp)
- **Pre-order (NLR):** 8, 3, 1, 6, 4, 7, 10, 14, 13
- **Post-order (LRN):** 1, 4, 7, 6, 3, 13, 14, 10, 8
- **Level-order (BFS):** 8, 3, 10, 1, 6, 14, 4, 7, 13

### 2.5 Heap (Đống)
- **Min-Heap:** Node cha ≤ node con (gốc là nhỏ nhất).
- **Max-Heap:** Node cha ≥ node con (gốc là lớn nhất).
- Implement bằng mảng: cha = (i-1)/2, con trái = 2i+1, con phải = 2i+2.
- Insert: O(log n), Delete min/max: O(log n), Peek: O(1).

### 2.6 Đồ thị (Graph)
**Biểu diễn:**
1. **Ma trận kề (Adjacency Matrix):** O(V²) bộ nhớ, kiểm tra cạnh O(1).
2. **Danh sách kề (Adjacency List):** O(V + E) bộ nhớ, hiệu quả hơn cho đồ thị thưa.

```java
// Adjacency List
public class DoThi {
    private final Map<Integer, List<Integer>> danhSachKe = new HashMap<>();

    public void themDinh(int dinh) {
        danhSachKe.putIfAbsent(dinh, new ArrayList<>());
    }

    public void themCanh(int tu, int den) {
        danhSachKe.computeIfAbsent(tu, k -> new ArrayList<>()).add(den);
        danhSachKe.computeIfAbsent(den, k -> new ArrayList<>()).add(tu); // Vô hướng
    }
}
```

---

## Phần 3: Thuật Toán

### 3.1 Sắp xếp (Sorting)

| Thuật toán | Tốt nhất | Trung bình | Xấu nhất | Bộ nhớ | Ổn định |
|-----------|---------|----------|---------|--------|---------|
| Bubble Sort | O(n) | O(n²) | O(n²) | O(1) | Có |
| Selection Sort | O(n²) | O(n²) | O(n²) | O(1) | Không |
| Insertion Sort | O(n) | O(n²) | O(n²) | O(1) | Có |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) | Có |
| Quick Sort | O(n log n) | O(n log n) | O(n²) | O(log n) | Không |
| Heap Sort | O(n log n) | O(n log n) | O(n log n) | O(1) | Không |

**Merge Sort:**
```java
public static void mergeSort(int[] arr, int left, int right) {
    if (left < right) {
        int mid = left + (right - left) / 2;
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
    int pivot = arr[high];
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

**Tìm kiếm nhị phân (Binary Search):**
```java
public static int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (arr[mid] == target) return mid;
        else if (arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1; // Không tìm thấy
}
```

### 3.3 Duyệt đồ thị

**BFS (Breadth-First Search):**
```java
public List<Integer> bfs(int start) {
    List<Integer> result = new ArrayList<>();
    Set<Integer> visited = new HashSet<>();
    Queue<Integer> queue = new LinkedList<>();
    queue.add(start);
    visited.add(start);
    while (!queue.isEmpty()) {
        int node = queue.poll();
        result.add(node);
        for (int neighbor : danhSachKe.getOrDefault(node, List.of())) {
            if (visited.add(neighbor)) {
                queue.add(neighbor);
            }
        }
    }
    return result;
}
```

**DFS (Depth-First Search):**
```java
public List<Integer> dfs(int start) {
    List<Integer> result = new ArrayList<>();
    Set<Integer> visited = new HashSet<>();
    dfsHelper(start, visited, result);
    return result;
}

private void dfsHelper(int node, Set<Integer> visited, List<Integer> result) {
    visited.add(node);
    result.add(node);
    for (int neighbor : danhSachKe.getOrDefault(node, List.of())) {
        if (!visited.contains(neighbor)) {
            dfsHelper(neighbor, visited, result);
        }
    }
}
```

### 3.4 Quy hoạch động (Dynamic Programming)

**Nguyên tắc:**
1. **Overlapping Subproblems:** Bài toán có các bài toán con trùng lặp.
2. **Optimal Substructure:** Lời giải tối ưu chứa lời giải tối ưu của bài toán con.

**Cách tiếp cận:**
- **Top-down (Memoization):** Đệ quy + cache.
- **Bottom-up (Tabulation):** Bảng từ nhỏ đến lớn.

```java
// Fibonacci - Memoization
public int fibMemo(int n, Map<Integer, Integer> memo) {
    if (n <= 1) return n;
    if (memo.containsKey(n)) return memo.get(n);
    int result = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
    memo.put(n, result);
    return result;
}

// Fibonacci - Tabulation
public int fibTab(int n) {
    if (n <= 1) return n;
    int[] dp = new int[n + 1];
    dp[0] = 0; dp[1] = 1;
    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2];
    }
    return dp[n];
}

// Fibonacci - Tối ưu bộ nhớ O(1)
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

### 3.5 Thuật toán tham lam (Greedy)
- Chọn phương án tốt nhất tại mỗi bước mà không xem xét toàn cục.
- Không phải lúc nào cũng cho kết quả tối ưu, nhưng nhanh hơn DP.
- Ví dụ: Bài toán đổi tiền (khi mệnh giá đặc biệt), Interval scheduling, Huffman coding.

### 3.6 Quay lui (Backtracking)
- Thử tất cả khả năng, quay lui khi gặp ngõ cụt.
- Ứng dụng: N-Queens, Sudoku solver, Tổ hợp, Hoán vị.

```java
// N-Queens
public List<List<String>> solveNQueens(int n) {
    List<List<String>> results = new ArrayList<>();
    int[] queens = new int[n]; // queens[row] = col
    Arrays.fill(queens, -1);
    solve(queens, 0, n, results);
    return results;
}

private void solve(int[] queens, int row, int n, List<List<String>> results) {
    if (row == n) {
        results.add(buildBoard(queens, n));
        return;
    }
    for (int col = 0; col < n; col++) {
        if (isValid(queens, row, col)) {
            queens[row] = col;
            solve(queens, row + 1, n, results);
            queens[row] = -1;
        }
    }
}

private boolean isValid(int[] queens, int row, int col) {
    for (int i = 0; i < row; i++) {
        if (queens[i] == col || Math.abs(queens[i] - col) == row - i) return false;
    }
    return true;
}
```
