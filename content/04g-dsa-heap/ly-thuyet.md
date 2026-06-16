# Heap (Đống) & Priority Queue

## 1. Khái niệm

**Heap** là cây nhị phân hoàn chỉnh (complete binary tree) thỏa mãn tính chất heap:
- **Min-Heap:** Parent ≤ Children → root là giá trị nhỏ nhất
- **Max-Heap:** Parent ≥ Children → root là giá trị lớn nhất

```
Min-Heap:          Max-Heap:
     1                  9
    / \                / \
   3   5              7   6
  / \                / \
 7   4              3   5

Biểu diễn bằng mảng (0-indexed):
[1, 3, 5, 7, 4]    [9, 7, 6, 3, 5]
Parent(i) = (i-1)/2
Left(i)   = 2*i + 1
Right(i)  = 2*i + 2
```

## 2. Các thao tác

| Thao tác | Time | Mô tả |
|----------|------|--------|
| `peek()` | O(1) | Xem phần tử min/max (root) |
| `offer()` / insert | O(log n) | Thêm phần tử, sift up |
| `poll()` / extract | O(log n) | Lấy ra min/max, sift down |
| `heapify` (build) | O(n) | Xây heap từ mảng |

### Sift Up (Bubble Up)
Khi thêm phần tử mới ở cuối, so sánh với parent và swap nếu vi phạm.

### Sift Down (Bubble Down)
Khi lấy root ra, đưa phần tử cuối lên root, so sánh với children và swap xuống.

```java
// Tự implement Min-Heap
public class MinHeap {
    private int[] data;
    private int size;

    public MinHeap(int capacity) {
        data = new int[capacity];
        size = 0;
    }

    public void offer(int val) {
        data[size] = val;
        siftUp(size);
        size++;
    }

    public int poll() {
        int min = data[0];
        data[0] = data[--size];
        siftDown(0);
        return min;
    }

    public int peek() { return data[0]; }

    private void siftUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (data[i] >= data[parent]) break;
            swap(i, parent);
            i = parent;
        }
    }

    private void siftDown(int i) {
        while (2 * i + 1 < size) {
            int child = 2 * i + 1;
            if (child + 1 < size && data[child + 1] < data[child]) child++;
            if (data[i] <= data[child]) break;
            swap(i, child);
            i = child;
        }
    }

    private void swap(int i, int j) {
        int temp = data[i]; data[i] = data[j]; data[j] = temp;
    }
}
```

## 3. PriorityQueue trong Java

```java
// Min-Heap (mặc định)
PriorityQueue<Integer> minPQ = new PriorityQueue<>();
minPQ.offer(5); minPQ.offer(1); minPQ.offer(3);
minPQ.poll();  // 1 — nhỏ nhất
minPQ.peek();  // 3

// Max-Heap
PriorityQueue<Integer> maxPQ = new PriorityQueue<>(Collections.reverseOrder());
maxPQ.offer(5); maxPQ.offer(1); maxPQ.offer(3);
maxPQ.poll();  // 5 — lớn nhất

// Custom comparator
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
pq.offer(new int[]{0, 5});  // {id, priority}
pq.offer(new int[]{1, 2});
pq.poll();  // {1, 2} — nhỏ nhất theo [1]
```

## 4. Các bài toán kinh điển

### 4.1 Top K Frequent Elements

```java
public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    for (int n : nums) freq.merge(n, 1, Integer::sum);

    // Min-heap theo frequency, giữ k phần tử lớn nhất
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    for (var entry : freq.entrySet()) {
        pq.offer(new int[]{entry.getKey(), entry.getValue()});
        if (pq.size() > k) pq.poll();
    }
    return pq.stream().mapToInt(a -> a[0]).toArray();
}
```

### 4.2 Kth Largest Element

```java
// Min-heap kích thước k → root = kth largest
public int findKthLargest(int[] nums, int k) {
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    for (int num : nums) {
        pq.offer(num);
        if (pq.size() > k) pq.poll();
    }
    return pq.peek();
}
// Time: O(n log k)
```

### 4.3 Merge K Sorted Lists

```java
public ListNode mergeKLists(ListNode[] lists) {
    PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
    for (ListNode node : lists) {
        if (node != null) pq.offer(node);
    }

    ListNode dummy = new ListNode(0), curr = dummy;
    while (!pq.isEmpty()) {
        ListNode min = pq.poll();
        curr.next = min;
        curr = curr.next;
        if (min.next != null) pq.offer(min.next);
    }
    return dummy.next;
}
// Time: O(N log k), N = tổng số node, k = số list
```

### 4.4 Find Median from Data Stream

```java
public class MedianFinder {
    private PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    private PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    public void addNum(int num) {
        maxHeap.offer(num);
        minHeap.offer(maxHeap.poll());
        if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    public double findMedian() {
        if (maxHeap.size() > minHeap.size()) return maxHeap.peek();
        return (maxHeap.peek() + minHeap.peek()) / 2.0;
    }
}
// maxHeap chứa nửa nhỏ, minHeap chứa nửa lớn
// Median = maxHeap.peek() hoặc trung bình 2 đỉnh
```

### 4.5 Heap Sort

```java
public static void heapSort(int[] arr) {
    int n = arr.length;
    // Build max-heap: O(n)
    for (int i = n / 2 - 1; i >= 0; i--) siftDown(arr, n, i);
    // Extract max lần lượt: O(n log n)
    for (int i = n - 1; i > 0; i--) {
        int temp = arr[0]; arr[0] = arr[i]; arr[i] = temp;
        siftDown(arr, i, 0);
    }
}

private static void siftDown(int[] arr, int n, int i) {
    while (2 * i + 1 < n) {
        int child = 2 * i + 1;
        if (child + 1 < n && arr[child + 1] > arr[child]) child++;
        if (arr[i] >= arr[child]) break;
        int temp = arr[i]; arr[i] = arr[child]; arr[child] = temp;
        i = child;
    }
}
```

## 5. K-Sorted Array (Mảng gần sorted)

```java
// Sắp xếp mảng mà mỗi phần tử cách vị trí đúng tối đa k
public static void sortKSorted(int[] arr, int k) {
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    int index = 0;
    for (int i = 0; i < arr.length; i++) {
        pq.offer(arr[i]);
        if (pq.size() > k + 1) {
            arr[index++] = pq.poll();
        }
    }
    while (!pq.isEmpty()) arr[index++] = pq.poll();
}
// Time: O(n log k)
```

## 6. Khi nào dùng Heap?

| Dùng khi | Ví dụ |
|---------|-------|
| Top-K problems | Top K frequent, Kth largest |
| Priority scheduling | Task scheduler, process queue |
| Merge K sorted collections | Merge K sorted lists/arrays |
| Median maintenance | Running median |
| Dijkstra's Algorithm | Shortest path with weights |
| Huffman Coding | Compression |

> **Phỏng vấn thường hỏi:** Kth Largest Element, Top K Frequent, Merge K Sorted Lists, Find Median, Task Scheduler, Meeting Rooms II.
