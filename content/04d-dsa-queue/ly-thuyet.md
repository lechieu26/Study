# Queue (Hàng đợi)

## 1. Khái niệm

**Queue** là cấu trúc dữ liệu hoạt động theo nguyên tắc **FIFO** (First In, First Out) — phần tử vào trước sẽ ra trước, giống như xếp hàng.

```
enqueue(A)  enqueue(B)  enqueue(C)  dequeue() → A
   ↓           ↓           ↓           ↑
 front       front       front       front
 +---+       +---+---+   +---+---+---+   +---+---+
 | A |       | A | B |   | A | B | C |   | B | C |
 +---+       +---+---+   +---+---+---+   +---+---+
  rear        rear         rear           rear
```

**Ứng dụng thực tế:**
- **BFS** (Breadth-First Search)
- **Task scheduling** (CPU, printer)
- **Message Queue** (RabbitMQ, Kafka)
- **Buffer** (IO buffer, keyboard buffer)
- **Level-order traversal** trên cây

## 2. Các loại Queue

| Loại | Đặc điểm | Ứng dụng |
|------|----------|----------|
| **Queue** | FIFO cơ bản | BFS, scheduling |
| **Deque** | Thêm/xóa cả hai đầu | Sliding window, cả Stack lẫn Queue |
| **PriorityQueue** | Phần tử ưu tiên cao nhất ra trước | Dijkstra, Top-K, task priority |
| **BlockingQueue** | Thread-safe, chờ khi rỗng/đầy | Producer-Consumer pattern |
| **Circular Queue** | Dùng mảng vòng, tối ưu bộ nhớ | Buffer cố định |

## 3. Implement trong Java

```java
// Queue — dùng ArrayDeque (nhanh nhất)
Queue<Integer> queue = new ArrayDeque<>();
queue.offer(1);    // Thêm cuối — O(1)
queue.offer(2);
queue.offer(3);
queue.peek();      // 1 — xem đầu, không xóa
queue.poll();      // 1 — lấy ra đầu
queue.isEmpty();   // false
queue.size();      // 2

// Deque (Double-Ended Queue) — cả Stack lẫn Queue
Deque<Integer> deque = new ArrayDeque<>();
deque.offerFirst(1);   // Thêm đầu
deque.offerLast(2);    // Thêm cuối
deque.peekFirst();     // Xem đầu
deque.peekLast();      // Xem cuối
deque.pollFirst();     // Lấy đầu
deque.pollLast();      // Lấy cuối

// PriorityQueue (Min-Heap mặc định)
PriorityQueue<Integer> pq = new PriorityQueue<>();
pq.offer(5); pq.offer(1); pq.offer(3);
pq.poll();  // 1 — nhỏ nhất
pq.poll();  // 3
pq.poll();  // 5

// PriorityQueue Max-Heap
PriorityQueue<Integer> maxPQ = new PriorityQueue<>(Collections.reverseOrder());
// Hoặc: new PriorityQueue<>((a, b) -> b - a);
```

> **offer vs add, poll vs remove, peek vs element:**
> - `offer/poll/peek` → trả về null/false khi rỗng
> - `add/remove/element` → throw exception khi rỗng
> - Khuyến nghị dùng `offer/poll/peek` để tránh exception

## 4. Tự implement Circular Queue

```java
public class CircularQueue {
    private int[] data;
    private int front, rear, size;

    public CircularQueue(int capacity) {
        data = new int[capacity];
        front = 0; rear = -1; size = 0;
    }

    public boolean enqueue(int val) {
        if (isFull()) return false;
        rear = (rear + 1) % data.length;  // Vòng tròn
        data[rear] = val;
        size++;
        return true;
    }

    public int dequeue() {
        if (isEmpty()) throw new RuntimeException("Queue empty");
        int val = data[front];
        front = (front + 1) % data.length;
        size--;
        return val;
    }

    public int peek() {
        if (isEmpty()) throw new RuntimeException("Queue empty");
        return data[front];
    }

    public boolean isEmpty() { return size == 0; }
    public boolean isFull() { return size == data.length; }
}
```

## 5. BFS với Queue

Queue là cấu trúc chính của thuật toán BFS (Breadth-First Search).

```java
// BFS trên đồ thị — tìm đường ngắn nhất (unweighted)
public int bfs(Map<Integer, List<Integer>> graph, int start, int end) {
    if (start == end) return 0;
    Queue<int[]> queue = new ArrayDeque<>();  // {node, distance}
    Set<Integer> visited = new HashSet<>();
    queue.offer(new int[]{start, 0});
    visited.add(start);

    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        int node = curr[0], dist = curr[1];
        for (int neighbor : graph.getOrDefault(node, List.of())) {
            if (neighbor == end) return dist + 1;
            if (visited.add(neighbor)) {
                queue.offer(new int[]{neighbor, dist + 1});
            }
        }
    }
    return -1;  // Không tìm thấy
}

// BFS Level-order — duyệt theo từng tầng
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    Queue<TreeNode> queue = new ArrayDeque<>();
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
```

## 6. Sliding Window Maximum với Deque

```java
// Monotonic Deque — tìm max trong mỗi cửa sổ kích thước k
public int[] maxSlidingWindow(int[] nums, int k) {
    Deque<Integer> deque = new ArrayDeque<>();  // Giảm dần
    int[] result = new int[nums.length - k + 1];

    for (int i = 0; i < nums.length; i++) {
        // Xóa phần tử ngoài cửa sổ
        while (!deque.isEmpty() && deque.peekFirst() <= i - k)
            deque.pollFirst();
        // Xóa phần tử nhỏ hơn
        while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i])
            deque.pollLast();
        deque.offerLast(i);
        if (i >= k - 1) result[i - k + 1] = nums[deque.peekFirst()];
    }
    return result;
}
```

## 7. So sánh các implementation

| Implementation | Đặc điểm | Time (enqueue/dequeue) |
|---------------|----------|----------------------|
| `ArrayDeque` | Nhanh nhất, circular array | O(1) amortized |
| `LinkedList` | Implement cả List + Deque | O(1) |
| `PriorityQueue` | Min-heap, sắp xếp tự động | O(log n) |
| `ArrayBlockingQueue` | Thread-safe, kích thước cố định | O(1) |
| `LinkedBlockingQueue` | Thread-safe, unbounded | O(1) |

## 8. Khi nào dùng Queue?

| Dùng Queue khi | Ví dụ |
|---------------|-------|
| BFS trên đồ thị/cây | Shortest path, level-order traversal |
| Xử lý task theo thứ tự | Task scheduler, print queue |
| Producer-Consumer | Message queue, event handling |
| Sliding Window | Monotonic Deque cho max/min |
| Cần FIFO ordering | Cache eviction, rate limiting |

> **Phỏng vấn thường hỏi:** Implement Queue using Stacks, Design Circular Queue, Sliding Window Maximum, Binary Tree Level Order, Rotting Oranges (BFS).
