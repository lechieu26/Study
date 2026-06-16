# Queue - Bài Tập

## Bài 1: Implement Queue bằng hai Stacks
**Độ khó: Dễ**

Implement Queue (FIFO) chỉ dùng hai Stack. Hỗ trợ: `push(x)`, `pop()`, `peek()`, `empty()`.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | push(1),push(2),peek | 1 |
| 2 | push(1),push(2),pop | 1 |
| 3 | push(1),push(2),pop,peek | 2 |
| 4 | push(1),pop,empty | true |
| 5 | empty (initial) | true |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    static Stack<Integer> s1 = new Stack<>();
    static Stack<Integer> s2 = new Stack<>();

    public static void main(String[] args) {
        // Test 1
        reset(); qPush(1); qPush(2);
        check(1, String.valueOf(qPeek()), "1");

        // Test 2
        reset(); qPush(1); qPush(2);
        check(2, String.valueOf(qPop()), "1");

        // Test 3
        reset(); qPush(1); qPush(2); qPop();
        check(3, String.valueOf(qPeek()), "2");

        // Test 4
        reset(); qPush(1); qPop();
        check(4, String.valueOf(qEmpty()), "true");

        // Test 5
        reset();
        check(5, String.valueOf(qEmpty()), "true");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void reset() { s1.clear(); s2.clear(); }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static void qPush(int x) {
        // Code here ...
    }

    public static int qPop() {
        // Code here ...
        return -1;
    }

    public static int qPeek() {
        // Code here ...
        return -1;
    }

    public static boolean qEmpty() {
        // Code here ...
        return true;
    }
}
```

---

## Bài 2: Implement Stack bằng hai Queues
**Độ khó: Dễ**

Implement Stack (LIFO) chỉ dùng hai Queue.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | push(1),push(2),top | 2 |
| 2 | push(1),push(2),pop | 2 |
| 3 | push(1),push(2),pop,top | 1 |
| 4 | push(1),pop,empty | true |
| 5 | empty (initial) | true |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    static Queue<Integer> q1 = new LinkedList<>();
    static Queue<Integer> q2 = new LinkedList<>();

    public static void main(String[] args) {
        reset(); sPush(1); sPush(2);
        check(1, String.valueOf(sTop()), "2");

        reset(); sPush(1); sPush(2);
        check(2, String.valueOf(sPop()), "2");

        reset(); sPush(1); sPush(2); sPop();
        check(3, String.valueOf(sTop()), "1");

        reset(); sPush(1); sPop();
        check(4, String.valueOf(sEmpty()), "true");

        reset();
        check(5, String.valueOf(sEmpty()), "true");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void reset() { q1.clear(); q2.clear(); }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static void sPush(int x) {
        // Code here ...
    }

    public static int sPop() {
        // Code here ...
        return -1;
    }

    public static int sTop() {
        // Code here ...
        return -1;
    }

    public static boolean sEmpty() {
        // Code here ...
        return true;
    }
}
```

---

## Bài 3: Thiết Kế Circular Queue
**Độ khó: Trung bình**

Thiết kế circular queue với kích thước cố định k.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | enQueue(1),enQueue(2),front | 1 |
| 2 | enQueue(1),enQueue(2),rear | 2 |
| 3 | enQueue(1),enQueue(2),deQueue,front | 2 |
| 4 | size=2,enQueue(1),enQueue(2),enQueue(3) | false |
| 5 | isEmpty (initial) | true |

### 🧪 Main Demo
```java
public class Main {

    static int[] queue;
    static int front, rear, size, capacity;

    public static void main(String[] args) {
        // Test 1
        init(3); enQueue(1); enQueue(2);
        check(1, String.valueOf(front()), "1");

        // Test 2
        init(3); enQueue(1); enQueue(2);
        check(2, String.valueOf(rear()), "2");

        // Test 3
        init(3); enQueue(1); enQueue(2); deQueue();
        check(3, String.valueOf(front()), "2");

        // Test 4
        init(2); enQueue(1); enQueue(2);
        check(4, String.valueOf(enQueue(3)), "false");

        // Test 5
        init(3);
        check(5, String.valueOf(isEmpty()), "true");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void init(int k) {
        queue = new int[k]; front = 0; rear = -1; size = 0; capacity = k;
    }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static boolean enQueue(int value) {
        // Code here ...
        return false;
    }

    public static boolean deQueue() {
        // Code here ...
        return false;
    }

    public static int front() {
        // Code here ...
        return -1;
    }

    public static int rear() {
        // Code here ...
        return -1;
    }

    public static boolean isEmpty() {
        // Code here ...
        return true;
    }

    public static boolean isFull() {
        // Code here ...
        return false;
    }
}
```

---

## Bài 4: Rotting Oranges
**Độ khó: Trung bình**

Cho ma trận 2D: 0=trống, 1=cam tươi, 2=cam thối. Mỗi phút cam thối lan sang cam tươi kề. Tìm số phút để tất cả cam thối, hoặc -1.

**Gợi ý:** Multi-source BFS.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [[2,1,1],[1,1,0],[0,1,1]] | 4 |
| 2 | [[2,1,1],[0,1,1],[1,0,1]] | -1 |
| 3 | [[0,2]] | 0 |
| 4 | [[2,1],[1,1]] | 2 |
| 5 | [[0]] | 0 |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(orangesRotting(new int[][]{{2,1,1},{1,1,0},{0,1,1}})), "4");
        check(2, String.valueOf(orangesRotting(new int[][]{{2,1,1},{0,1,1},{1,0,1}})), "-1");
        check(3, String.valueOf(orangesRotting(new int[][]{{0,2}})), "0");
        check(4, String.valueOf(orangesRotting(new int[][]{{2,1},{1,1}})), "2");
        check(5, String.valueOf(orangesRotting(new int[][]{{0}})), "0");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int orangesRotting(int[][] grid) {
        // Code here ...
        return -1;
    }
}
```

---

## Bài 5: Task Scheduler
**Độ khó: Trung bình**

Cho mảng tasks và cooldown `n`. Cùng một task phải cách nhau ít nhất n intervals. Tìm số intervals tối thiểu.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | tasks=[A,A,A,B,B,B], n=2 | 8 |
| 2 | tasks=[A,A,A,B,B,B], n=0 | 6 |
| 3 | tasks=[A,A,A,A,B,B,B,C,C], n=2 | 10 |
| 4 | tasks=[A], n=5 | 1 |
| 5 | tasks=[A,B,C,D], n=2 | 4 |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(leastInterval(new char[]{'A','A','A','B','B','B'}, 2)), "8");
        check(2, String.valueOf(leastInterval(new char[]{'A','A','A','B','B','B'}, 0)), "6");
        check(3, String.valueOf(leastInterval(new char[]{'A','A','A','A','B','B','B','C','C'}, 2)), "10");
        check(4, String.valueOf(leastInterval(new char[]{'A'}, 5)), "1");
        check(5, String.valueOf(leastInterval(new char[]{'A','B','C','D'}, 2)), "4");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int leastInterval(char[] tasks, int n) {
        // Code here ...
        return 0;
    }
}
```

---

## Bài 6: Sliding Window Maximum
**Độ khó: Khó**

Cho mảng `nums` và số `k`, tìm giá trị lớn nhất trong mỗi cửa sổ trượt kích thước k.

**Gợi ý:** Dùng Monotonic Deque.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | nums=[1,3,-1,-3,5,3,6,7], k=3 | [3, 3, 5, 5, 6, 7] |
| 2 | nums=[1], k=1 | [1] |
| 3 | nums=[9,11], k=2 | [11] |
| 4 | nums=[4,-2], k=2 | [4] |
| 5 | nums=[1,2,3,4,5], k=3 | [3, 4, 5] |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, Arrays.toString(maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3)), "[3, 3, 5, 5, 6, 7]");
        check(2, Arrays.toString(maxSlidingWindow(new int[]{1}, 1)), "[1]");
        check(3, Arrays.toString(maxSlidingWindow(new int[]{9,11}, 2)), "[11]");
        check(4, Arrays.toString(maxSlidingWindow(new int[]{4,-2}, 2)), "[4]");
        check(5, Arrays.toString(maxSlidingWindow(new int[]{1,2,3,4,5}, 3)), "[3, 4, 5]");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {
        // Code here ...
        return new int[]{};
    }
}
```
