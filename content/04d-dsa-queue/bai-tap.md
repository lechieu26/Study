# Queue - Bài Tập

## Bài 1: Implement Queue bằng hai Stacks
**Độ khó: Dễ**

Implement Queue (FIFO) chỉ dùng hai Stack. Hỗ trợ: `push(x)`, `pop()`, `peek()`, `empty()`.

**Ví dụ:**
```
MyQueue queue = new MyQueue();
queue.push(1);
queue.push(2);
queue.peek();   // 1
queue.pop();    // 1
queue.empty();  // false
```

---

## Bài 2: Implement Stack bằng hai Queues
**Độ khó: Dễ**

Implement Stack (LIFO) chỉ dùng hai Queue.

---

## Bài 3: Thiết Kế Circular Queue
**Độ khó: Trung bình**

Thiết kế circular queue với kích thước cố định k. Hỗ trợ: `enQueue(value)`, `deQueue()`, `Front()`, `Rear()`, `isEmpty()`, `isFull()`.

---

## Bài 4: Rotting Oranges
**Độ khó: Trung bình**

Cho ma trận 2D: 0 = ô trống, 1 = cam tươi, 2 = cam thối. Mỗi phút, cam thối lan sang các cam tươi kề (4 hướng). Tìm số phút để tất cả cam đều thối, hoặc -1 nếu không thể.

**Đầu vào:**
```
[[2,1,1],
 [1,1,0],
 [0,1,1]]
```
**Đầu ra:** `4`

**Gợi ý:** Multi-source BFS — bắt đầu từ tất cả cam thối cùng lúc.

---

## Bài 5: Task Scheduler
**Độ khó: Trung bình**

Cho mảng tasks và cooldown `n`. Cùng một task phải cách nhau ít nhất n intervals. Tìm số intervals tối thiểu để hoàn thành tất cả.

**Đầu vào:** `tasks = ['A','A','A','B','B','B']`, `n = 2`
**Đầu ra:** `8` (A → B → idle → A → B → idle → A → B)

---

## Bài 6: Sliding Window Maximum
**Độ khó: Khó**

Cho mảng `nums` và số `k`, tìm giá trị lớn nhất trong mỗi cửa sổ trượt kích thước k.

**Đầu vào:** `nums = [1,3,-1,-3,5,3,6,7]`, `k = 3`
**Đầu ra:** `[3,3,5,5,6,7]`

**Gợi ý:** Dùng Monotonic Deque.
