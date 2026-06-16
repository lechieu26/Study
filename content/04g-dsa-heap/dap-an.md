# Heap - Đáp Án Chi Tiết

## Bài 1: Kth Largest Element

### Cách 1: Min-Heap kích thước k — O(n log k)
```java
public class KthLargest {
    public static int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int num : nums) {
            pq.offer(num);
            if (pq.size() > k) pq.poll();
        }
        return pq.peek();
    }
}
```

### Cách 2: QuickSelect — O(n) average
```java
public class KthLargest_QuickSelect {
    public static int findKthLargest(int[] nums, int k) {
        int target = nums.length - k;
        return quickSelect(nums, 0, nums.length - 1, target);
    }

    private static int quickSelect(int[] nums, int lo, int hi, int target) {
        int pivot = nums[hi], i = lo;
        for (int j = lo; j < hi; j++) {
            if (nums[j] <= pivot) { swap(nums, i, j); i++; }
        }
        swap(nums, i, hi);
        if (i == target) return nums[i];
        if (i < target) return quickSelect(nums, i + 1, hi, target);
        return quickSelect(nums, lo, i - 1, target);
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}
```

---

## Bài 2: Top K Frequent

### Cách 1: HashMap + Min-Heap O(n log k)
```java
public class TopKFrequent {
    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int n : nums) freq.merge(n, 1, Integer::sum);
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        for (var e : freq.entrySet()) {
            pq.offer(new int[]{e.getKey(), e.getValue()});
            if (pq.size() > k) pq.poll();
        }
        return pq.stream().mapToInt(a -> a[0]).toArray();
    }
}
```

---

## Bài 3: Merge K Sorted Lists

### Cách 1: PriorityQueue O(N log k)
```java
public class MergeKLists {
    public static ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
        for (ListNode n : lists) if (n != null) pq.offer(n);
        ListNode dummy = new ListNode(0), curr = dummy;
        while (!pq.isEmpty()) {
            ListNode min = pq.poll();
            curr.next = min; curr = curr.next;
            if (min.next != null) pq.offer(min.next);
        }
        return dummy.next;
    }
}
```

---

## Bài 4: Find Median

### Cách 1: Two Heaps
```java
public class MedianFinder {
    private PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    private PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    public void addNum(int num) {
        maxHeap.offer(num);
        minHeap.offer(maxHeap.poll());
        if (minHeap.size() > maxHeap.size()) maxHeap.offer(minHeap.poll());
    }

    public double findMedian() {
        if (maxHeap.size() > minHeap.size()) return maxHeap.peek();
        return (maxHeap.peek() + minHeap.peek()) / 2.0;
    }
}
```

---

## Bài 5: K-Sorted Array

### Cách 1: Min-Heap kích thước k+1 — O(n log k)
```java
public class KSortedArray {
    public static void sortKSorted(int[] arr, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            pq.offer(arr[i]);
            if (pq.size() > k + 1) arr[index++] = pq.poll();
        }
        while (!pq.isEmpty()) arr[index++] = pq.poll();
    }
}
```

---

## Bài 6: Last Stone Weight

### Cách 1: Max-Heap
```java
public class LastStoneWeight {
    public static int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (int s : stones) pq.offer(s);
        while (pq.size() > 1) {
            int first = pq.poll(), second = pq.poll();
            if (first != second) pq.offer(first - second);
        }
        return pq.isEmpty() ? 0 : pq.peek();
    }
}
```
