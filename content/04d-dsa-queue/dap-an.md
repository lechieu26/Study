# Queue - Đáp Án Chi Tiết

## Bài 1: Implement Queue bằng hai Stacks

### Cách 1: Lazy Transfer
```java
public class MyQueue {
    private Deque<Integer> pushStack = new ArrayDeque<>();
    private Deque<Integer> popStack = new ArrayDeque<>();

    public void push(int x) {
        pushStack.push(x);
    }

    public int pop() {
        if (popStack.isEmpty()) transfer();
        return popStack.pop();
    }

    public int peek() {
        if (popStack.isEmpty()) transfer();
        return popStack.peek();
    }

    public boolean empty() {
        return pushStack.isEmpty() && popStack.isEmpty();
    }

    private void transfer() {
        while (!pushStack.isEmpty()) popStack.push(pushStack.pop());
    }
}
// Amortized O(1) cho mỗi thao tác
// Chỉ transfer khi popStack rỗng
```

---

## Bài 2: Implement Stack bằng hai Queues

### Cách 1: Push O(n)
```java
public class MyStack {
    private Queue<Integer> queue = new ArrayDeque<>();

    public void push(int x) {
        int size = queue.size();
        queue.offer(x);
        for (int i = 0; i < size; i++) {
            queue.offer(queue.poll());  // Đưa tất cả phần tử cũ ra sau
        }
    }

    public int pop() { return queue.poll(); }
    public int top() { return queue.peek(); }
    public boolean empty() { return queue.isEmpty(); }
}
// push(1): [1]
// push(2): offer 2 → [1,2] → rotate → [2,1]
// push(3): offer 3 → [2,1,3] → rotate → [3,2,1]
```

---

## Bài 3: Thiết Kế Circular Queue

### Cách 1: Array với front/rear pointers
```java
public class MyCircularQueue {
    private int[] data;
    private int front, rear, size;

    public MyCircularQueue(int k) {
        data = new int[k]; front = 0; rear = -1; size = 0;
    }

    public boolean enQueue(int value) {
        if (isFull()) return false;
        rear = (rear + 1) % data.length;
        data[rear] = value;
        size++;
        return true;
    }

    public boolean deQueue() {
        if (isEmpty()) return false;
        front = (front + 1) % data.length;
        size--;
        return true;
    }

    public int Front() { return isEmpty() ? -1 : data[front]; }
    public int Rear() { return isEmpty() ? -1 : data[rear]; }
    public boolean isEmpty() { return size == 0; }
    public boolean isFull() { return size == data.length; }
}
```

---

## Bài 4: Rotting Oranges

### Cách 1: Multi-source BFS
```java
public class RottingOranges {
    public static int orangesRotting(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        Queue<int[]> queue = new ArrayDeque<>();
        int fresh = 0;

        // Thu thập tất cả cam thối ban đầu
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 2) queue.offer(new int[]{r, c});
                else if (grid[r][c] == 1) fresh++;
            }
        }

        if (fresh == 0) return 0;
        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
        int minutes = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            boolean rotted = false;
            for (int i = 0; i < size; i++) {
                int[] cell = queue.poll();
                for (int[] d : dirs) {
                    int nr = cell[0] + d[0], nc = cell[1] + d[1];
                    if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] == 1) {
                        grid[nr][nc] = 2;
                        queue.offer(new int[]{nr, nc});
                        fresh--;
                        rotted = true;
                    }
                }
            }
            if (rotted) minutes++;
        }
        return fresh == 0 ? minutes : -1;
    }
}
```

---

## Bài 5: Task Scheduler

### Cách 1: Greedy + Math
```java
public class TaskScheduler {
    public static int leastInterval(char[] tasks, int n) {
        int[] freq = new int[26];
        for (char t : tasks) freq[t - 'A']++;
        int maxFreq = 0, maxCount = 0;
        for (int f : freq) {
            if (f > maxFreq) { maxFreq = f; maxCount = 1; }
            else if (f == maxFreq) maxCount++;
        }
        // Công thức: (maxFreq - 1) * (n + 1) + maxCount
        int intervals = (maxFreq - 1) * (n + 1) + maxCount;
        return Math.max(intervals, tasks.length);
    }
}
// A=3, B=3, n=2: (3-1)*(2+1) + 2 = 8
// A_B_A_B_AB → A→B→idle→A→B→idle→A→B
```

---

## Bài 6: Sliding Window Maximum

### Cách 1: Monotonic Deque O(n)
```java
public class SlidingWindowMaximum {
    public static int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> deque = new ArrayDeque<>();
        int[] result = new int[nums.length - k + 1];

        for (int i = 0; i < nums.length; i++) {
            while (!deque.isEmpty() && deque.peekFirst() <= i - k)
                deque.pollFirst();
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i])
                deque.pollLast();
            deque.offerLast(i);
            if (i >= k - 1) result[i - k + 1] = nums[deque.peekFirst()];
        }
        return result;
    }
}
```
