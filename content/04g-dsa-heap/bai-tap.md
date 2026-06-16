# Heap - Bài Tập

## Bài 1: Kth Largest Element in Array
**Độ khó: Trung bình**

Tìm phần tử lớn thứ k trong mảng (không cần sorted).

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | nums=[3,2,1,5,6,4], k=2 | 5 |
| 2 | nums=[3,2,3,1,2,4,5,5,6], k=4 | 4 |
| 3 | nums=[1], k=1 | 1 |
| 4 | nums=[7,6,5,4,3,2,1], k=5 | 3 |
| 5 | nums=[2,1], k=2 | 1 |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(findKthLargest(new int[]{3,2,1,5,6,4}, 2)), "5");
        check(2, String.valueOf(findKthLargest(new int[]{3,2,3,1,2,4,5,5,6}, 4)), "4");
        check(3, String.valueOf(findKthLargest(new int[]{1}, 1)), "1");
        check(4, String.valueOf(findKthLargest(new int[]{7,6,5,4,3,2,1}, 5)), "3");
        check(5, String.valueOf(findKthLargest(new int[]{2,1}, 2)), "1");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int findKthLargest(int[] nums, int k) {
        // Code here ...
        return 0;
    }
}
```

---

## Bài 2: Top K Frequent Elements
**Độ khó: Trung bình**

Tìm k phần tử xuất hiện nhiều nhất.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | nums=[1,1,1,2,2,3], k=2 | [1, 2] |
| 2 | nums=[1], k=1 | [1] |
| 3 | nums=[4,4,4,1,1,2], k=1 | [4] |
| 4 | nums=[1,2], k=2 | size=2 |
| 5 | nums=[3,3,3,3], k=1 | [3] |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int[] r1 = topKFrequent(new int[]{1,1,1,2,2,3}, 2);
        Arrays.sort(r1);
        check(1, Arrays.toString(r1), "[1, 2]");
        check(2, Arrays.toString(topKFrequent(new int[]{1}, 1)), "[1]");
        check(3, Arrays.toString(topKFrequent(new int[]{4,4,4,1,1,2}, 1)), "[4]");
        check(4, String.valueOf(topKFrequent(new int[]{1,2}, 2).length), "2");
        check(5, Arrays.toString(topKFrequent(new int[]{3,3,3,3}, 1)), "[3]");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int[] topKFrequent(int[] nums, int k) {
        // Code here ...
        return new int[]{};
    }
}
```

---

## Bài 3: Merge K Sorted Lists
**Độ khó: Khó**

Gộp k linked list đã sắp xếp thành 1 list sorted.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [[1,4,5],[1,3,4],[2,6]] | 1->1->2->3->4->4->5->6 |
| 2 | [] | null |
| 3 | [[]] | null |
| 4 | [[1],[2],[3]] | 1->2->3 |
| 5 | [[1,2],[3,4]] | 1->2->3->4 |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        ListNode[] lists1 = {buildList(new int[]{1,4,5}), buildList(new int[]{1,3,4}), buildList(new int[]{2,6})};
        check(1, listToString(mergeKLists(lists1)), "1->1->2->3->4->4->5->6");

        check(2, listToString(mergeKLists(new ListNode[]{})), "null");
        check(3, listToString(mergeKLists(new ListNode[]{null})), "null");

        ListNode[] lists4 = {buildList(new int[]{1}), buildList(new int[]{2}), buildList(new int[]{3})};
        check(4, listToString(mergeKLists(lists4)), "1->2->3");

        ListNode[] lists5 = {buildList(new int[]{1,2}), buildList(new int[]{3,4})};
        check(5, listToString(mergeKLists(lists5)), "1->2->3->4");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static ListNode buildList(int[] vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : vals) { cur.next = new ListNode(v); cur = cur.next; }
        return dummy.next;
    }

    static String listToString(ListNode head) {
        if (head == null) return "null";
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) sb.append("->");
            head = head.next;
        }
        return sb.toString();
    }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        // Code here ...
        return null;
    }
}
```

---

## Bài 4: Find Median from Data Stream
**Độ khó: Khó**

Thiết kế cấu trúc dữ liệu hỗ trợ `addNum` và `findMedian`.

**Gợi ý:** Dùng 2 heap (max-heap cho nửa nhỏ, min-heap cho nửa lớn).

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | add(1),add(2),median | 1.5 |
| 2 | add(1),add(2),add(3),median | 2.0 |
| 3 | add(5),median | 5.0 |
| 4 | add(1),add(1),median | 1.0 |
| 5 | add(3),add(1),add(2),median | 2.0 |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    static PriorityQueue<Integer> maxHeap;
    static PriorityQueue<Integer> minHeap;

    public static void main(String[] args) {
        // Test 1
        reset(); addNum(1); addNum(2);
        check(1, String.valueOf(findMedian()), "1.5");

        // Test 2
        reset(); addNum(1); addNum(2); addNum(3);
        check(2, String.valueOf(findMedian()), "2.0");

        // Test 3
        reset(); addNum(5);
        check(3, String.valueOf(findMedian()), "5.0");

        // Test 4
        reset(); addNum(1); addNum(1);
        check(4, String.valueOf(findMedian()), "1.0");

        // Test 5
        reset(); addNum(3); addNum(1); addNum(2);
        check(5, String.valueOf(findMedian()), "2.0");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void reset() {
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        minHeap = new PriorityQueue<>();
    }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static void addNum(int num) {
        // Code here ...
    }

    public static double findMedian() {
        // Code here ...
        return 0.0;
    }
}
```

---

## Bài 5: Sắp Xếp Mảng K-Sorted
**Độ khó: Trung bình**

Cho mảng mà mỗi phần tử cách vị trí đúng tối đa k. Sắp xếp hiệu quả.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | arr=[6,5,3,2,8,10,9], k=3 | [2, 3, 5, 6, 8, 9, 10] |
| 2 | arr=[2,1], k=1 | [1, 2] |
| 3 | arr=[1], k=0 | [1] |
| 4 | arr=[3,1,2,4], k=2 | [1, 2, 3, 4] |
| 5 | arr=[10,9,8,7,4,70,60,50], k=4 | [4, 7, 8, 9, 10, 50, 60, 70] |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int[] a1 = {6,5,3,2,8,10,9}; sortKSorted(a1, 3);
        check(1, Arrays.toString(a1), "[2, 3, 5, 6, 8, 9, 10]");

        int[] a2 = {2,1}; sortKSorted(a2, 1);
        check(2, Arrays.toString(a2), "[1, 2]");

        int[] a3 = {1}; sortKSorted(a3, 0);
        check(3, Arrays.toString(a3), "[1]");

        int[] a4 = {3,1,2,4}; sortKSorted(a4, 2);
        check(4, Arrays.toString(a4), "[1, 2, 3, 4]");

        int[] a5 = {10,9,8,7,4,70,60,50}; sortKSorted(a5, 4);
        check(5, Arrays.toString(a5), "[4, 7, 8, 9, 10, 50, 60, 70]");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static void sortKSorted(int[] arr, int k) {
        // Code here ...
    }
}
```

---

## Bài 6: Last Stone Weight
**Độ khó: Dễ**

Mỗi lượt chọn 2 viên đá nặng nhất. Nếu khác nhau, viên lớn hơn giảm đi. Tìm trọng lượng viên cuối.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [2,7,4,1,8,1] | 1 |
| 2 | [1] | 1 |
| 3 | [2,2] | 0 |
| 4 | [3,7,2] | 2 |
| 5 | [1,3,5,7] | 0 |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(lastStoneWeight(new int[]{2,7,4,1,8,1})), "1");
        check(2, String.valueOf(lastStoneWeight(new int[]{1})), "1");
        check(3, String.valueOf(lastStoneWeight(new int[]{2,2})), "0");
        check(4, String.valueOf(lastStoneWeight(new int[]{3,7,2})), "2");
        check(5, String.valueOf(lastStoneWeight(new int[]{1,3,5,7})), "0");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int lastStoneWeight(int[] stones) {
        // Code here ...
        return 0;
    }
}
```
