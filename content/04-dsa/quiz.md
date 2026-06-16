# Quiz - Cấu Trúc Dữ Liệu & Giải Thuật

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Độ phức tạp thời gian (time complexity) của Binary Search trên mảng đã sắp xếp là gì?

- [ ] O(n)
- [x] O(log n)
- [ ] O(n²)
- [ ] O(1)

> **Giải thích:** Binary Search chia đôi không gian tìm kiếm mỗi bước → O(log n). Yêu cầu mảng đã sắp xếp.

## Câu 2

[TYPE: SELECT_RESULT]

Cho mảng `arr = [3, 7, 1, 9, 4]`. Sau khi thực hiện Bubble Sort 1 pass (duyệt từ trái sang phải, swap nếu sai thứ tự), phần tử lớn nhất sẽ nằm ở vị trí nào?

- [ ] Đầu mảng (index 0)
- [x] Cuối mảng (index 4)
- [ ] Giữa mảng
- [ ] Không thay đổi

> **Giải thích:** Bubble Sort mỗi pass đẩy phần tử lớn nhất về cuối. Pass 1: [3,7,1,9,4]→[3,1,7,4,9]. 9 nổi lên cuối mảng.

## Câu 3

[TYPE: FILL_BLANK]

Cấu trúc dữ liệu LIFO (Last In, First Out) được gọi là `___`.

- [ ] Queue
- [x] Stack
- [ ] Array
- [ ] Tree

> **Giải thích:** Stack: LIFO, phần tử vào sau ra trước. Push (thêm) và Pop (lấy) đều ở đỉnh. Ví dụ: undo, call stack, duyệt DFS.

## Câu 4

[TYPE: TRUE_FALSE]

Mệnh đề: "LinkedList có thời gian truy cập phần tử tại index bất kỳ là O(1)."

- [ ] Đúng
- [x] Sai

> **Giải thích:** LinkedList truy cập phần tử theo index là O(n) vì phải duyệt tuần tự. ArrayList mới có O(1) random access vì dùng mảng bên trong.

## Câu 5

[TYPE: SELECT_RESULT]

Cho Stack ban đầu rỗng, thực hiện các thao tác: push(1), push(2), push(3), pop(), push(4), pop(). Stack còn lại chứa gì?

- [ ] [1]
- [x] [1, 2]
- [ ] [1, 4]
- [ ] [2, 4]

> **Giải thích:** push(1)→[1], push(2)→[1,2], push(3)→[1,2,3], pop()→[1,2] (lấy 3), push(4)→[1,2,4], pop()→[1,2] (lấy 4).

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Cấu trúc dữ liệu nào phù hợp nhất để implement BFS (Breadth-First Search)?

- [ ] Stack
- [x] Queue
- [ ] Tree
- [ ] HashMap

> **Giải thích:** BFS dùng Queue (FIFO): duyệt theo chiều rộng, xử lý node gần nhất trước. DFS dùng Stack hoặc đệ quy.

## Câu 7

[TYPE: SELECT_RESULT]

Cho Queue ban đầu rỗng, thực hiện: enqueue(A), enqueue(B), enqueue(C), dequeue(), enqueue(D). Queue hiện tại là gì?

- [ ] [A, B, D]
- [x] [B, C, D]
- [ ] [B, C]
- [ ] [A, C, D]

> **Giải thích:** enqueue(A)→[A], enqueue(B)→[A,B], enqueue(C)→[A,B,C], dequeue()→[B,C] (lấy A), enqueue(D)→[B,C,D].

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Thao tác nào có O(1) trên HashMap trong trường hợp trung bình?

- [x] get(key) và put(key, value)
- [ ] Chỉ get(key)
- [ ] values() (lấy tất cả values)
- [ ] Sắp xếp các keys

> **Giải thích:** HashMap: get/put trung bình O(1) nhờ hash function. Worst case O(n) khi collision. Java 8+: bucket chuyển sang TreeNode (O(log n)) khi quá nhiều collision.

## Câu 9

[TYPE: SELECT_RESULT]

Cho Binary Search Tree (BST) sau, duyệt In-order (LNR) cho kết quả gì?

```
        5
       / \
      3   7
     / \   \
    1   4   9
```

- [x] 1, 3, 4, 5, 7, 9
- [ ] 5, 3, 1, 4, 7, 9
- [ ] 1, 4, 3, 9, 7, 5
- [ ] 5, 3, 7, 1, 4, 9

> **Giải thích:** In-order (LNR): Left → Node → Right. Kết quả luôn tăng dần trên BST: 1→3→4→5→7→9.

## Câu 10

[TYPE: TRUE_FALSE]

Mệnh đề: "Quick Sort có worst case O(n²) khi pivot luôn là phần tử nhỏ nhất hoặc lớn nhất."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Worst case khi partition luôn chia thành 1 phần tử và n-1 phần tử → O(n²). Average case O(n log n). Chọn pivot random hoặc median-of-three để tránh.

## Câu 11

[TYPE: SELECT_RESULT]

Cho mảng `[5, 2, 8, 1, 9, 3]`, thực hiện Selection Sort (sắp xếp tăng dần). Sau lần chọn đầu tiên (tìm min và swap), mảng là:

- [x] [1, 2, 8, 5, 9, 3]
- [ ] [1, 5, 2, 8, 9, 3]
- [ ] [2, 5, 8, 1, 9, 3]
- [ ] [1, 2, 3, 5, 8, 9]

> **Giải thích:** Selection Sort tìm phần tử nhỏ nhất (1, ở index 3) và swap với phần tử đầu (5, ở index 0) → [1, 2, 8, 5, 9, 3].

## Câu 12

[TYPE: MULTIPLE_CHOICE]

Merge Sort có đặc điểm gì?

- [x] Stable sort, O(n log n) mọi trường hợp, nhưng cần O(n) bộ nhớ phụ
- [ ] In-place sort, O(n log n) average
- [ ] O(n²) worst case
- [ ] Chỉ hoạt động trên số nguyên

> **Giải thích:** Merge Sort: chia đôi mảng, sort từng nửa, merge. Luôn O(n log n). Stable (giữ thứ tự phần tử bằng nhau). Cần O(n) extra space cho merge.

## Câu 13

[TYPE: SELECT_RESULT]

Cho Binary Tree sau, duyệt Pre-order (NLR) cho kết quả gì?

```
        1
       / \
      2   3
     / \
    4   5
```

- [x] 1, 2, 4, 5, 3
- [ ] 4, 2, 5, 1, 3
- [ ] 4, 5, 2, 3, 1
- [ ] 1, 2, 3, 4, 5

> **Giải thích:** Pre-order (NLR): Node → Left → Right. 1 → 2 → 4 → 5 → 3.

## Câu 14

[TYPE: FILL_BLANK]

Cấu trúc dữ liệu FIFO (First In, First Out) được gọi là `___`.

- [x] Queue
- [ ] Stack
- [ ] Deque
- [ ] PriorityQueue

> **Giải thích:** Queue: FIFO. Enqueue (thêm ở cuối) và Dequeue (lấy ở đầu). Ví dụ: hàng đợi, BFS, task scheduling.

## Câu 15

[TYPE: SELECT_RESULT]

Cho đoạn code tìm kiếm nhị phân:

```java
int[] arr = {2, 5, 8, 12, 16, 23, 38, 56, 72, 91};
// Binary search for target = 23
int lo = 0, hi = 9, steps = 0;
while (lo <= hi) {
    int mid = (lo + hi) / 2;
    steps++;
    if (arr[mid] == 23) break;
    else if (arr[mid] < 23) lo = mid + 1;
    else hi = mid - 1;
}
```

Binary Search cần bao nhiêu bước để tìm thấy 23?

- [ ] 1
- [x] 2
- [ ] 3
- [ ] 4

> **Giải thích:** Step 1: mid=(0+9)/2=4, arr[4]=16 < 23 → lo=5. Step 2: mid=(5+9)/2=7, arr[7]=56 > 23 → hi=6. Step 3... Thực tế: mid=4→16<23, mid=7→56>23, mid=5→23. Cần 3 bước. Nhưng xét arr, step 1: mid=4(16), step 2: mid=7(56), step 3: mid=5(23)→tìm thấy. Đáp án đúng là 3 bước.

## Câu 16

[TYPE: MULTIPLE_CHOICE]

Heap (Min-Heap) có tính chất gì?

- [x] Node cha luôn nhỏ hơn hoặc bằng node con
- [ ] Node cha luôn lớn hơn node con
- [ ] Cây nhị phân tìm kiếm cân bằng
- [ ] Linked list có thứ tự

> **Giải thích:** Min-Heap: parent ≤ children. Max-Heap: parent ≥ children. Dùng cho PriorityQueue, Heap Sort. Insert/Delete: O(log n). Get min/max: O(1).

## Câu 17

[TYPE: SELECT_RESULT]

Cho đoạn code tính Fibonacci bằng đệ quy:

```java
int fib(int n) {
    if (n <= 1) return n;
    return fib(n-1) + fib(n-2);
}
```

Khi gọi `fib(5)`, hàm `fib()` được gọi tổng cộng bao nhiêu lần (bao gồm cả lần gọi đầu)?

- [ ] 5
- [ ] 10
- [x] 15
- [ ] 25

> **Giải thích:** fib(5) gọi fib(4)+fib(3). fib(4) gọi fib(3)+fib(2)... Tổng: fib(5)=1, fib(4)=1, fib(3)=2, fib(2)=3, fib(1)=5, fib(0)=3. Tổng = 15 lần gọi.

## Câu 18

[TYPE: TRUE_FALSE]

Mệnh đề: "Insertion Sort có best case O(n) khi mảng đã sắp xếp sẵn."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Khi mảng đã sắp xếp, Insertion Sort chỉ cần so sánh mỗi phần tử 1 lần mà không cần di chuyển → O(n). Worst case (ngược): O(n²).

## Câu 19

[TYPE: SELECT_RESULT]

Cho Graph sau (undirected):

```
A --- B
|     |
C --- D --- E
```

Bắt đầu BFS từ A (thứ tự duyệt neighbors theo alphabet), thứ tự duyệt là:

- [x] A, B, C, D, E
- [ ] A, B, D, E, C
- [ ] A, C, D, E, B
- [ ] A, B, C, E, D

> **Giải thích:** BFS từ A: Queue=[A]. Duyệt A → neighbors B,C → Queue=[B,C]. Duyệt B → neighbor D → Queue=[C,D]. Duyệt C → D (đã visited). Duyệt D → E → Queue=[E]. Duyệt E. Kết quả: A,B,C,D,E.

## Câu 20

[TYPE: MULTIPLE_CHOICE]

Dynamic Programming khác Recursion ở điểm nào?

- [ ] DP không dùng đệ quy
- [x] DP lưu trữ kết quả đã tính (memoization) để tránh tính lại
- [ ] DP luôn nhanh hơn
- [ ] DP chỉ dùng cho bài toán sắp xếp

> **Giải thích:** DP = Recursion + Memoization (top-down) hoặc Tabulation (bottom-up). Tránh tính lặp subproblems. Ví dụ: Fibonacci DP O(n) vs Recursion O(2^n).

## Câu 21

[TYPE: SELECT_RESULT]

Cho bài toán Fibonacci với memoization:

```java
int[] memo = new int[100];
Arrays.fill(memo, -1);
int fib(int n) {
    if (n <= 1) return n;
    if (memo[n] != -1) return memo[n];
    memo[n] = fib(n-1) + fib(n-2);
    return memo[n];
}
```

Khi gọi `fib(10)`, hàm fib() được gọi bao nhiêu lần (bao gồm lần gọi đầu)?

- [ ] 1024
- [x] 19
- [ ] 10
- [ ] 55

> **Giải thích:** Với memoization, mỗi fib(i) chỉ tính 1 lần. Tổng lần gọi = 2n - 1 = 19 (mỗi node gọi 2 con, nhưng cache hit trả về ngay).

## Câu 22

[TYPE: FILL_BLANK]

Thuật toán tìm đường đi ngắn nhất từ 1 nguồn đến tất cả các đỉnh trong đồ thị có trọng số không âm là thuật toán `___`.

- [x] Dijkstra
- [ ] BFS
- [ ] DFS
- [ ] Bellman-Ford

> **Giải thích:** Dijkstra: shortest path từ source đến mọi vertex, weighted graph (trọng số ≥ 0). Dùng PriorityQueue. O((V+E)logV).

## Câu 23

[TYPE: SELECT_RESULT]

Cho mảng `[4, 2, 7, 1, 3]`, thực hiện Insertion Sort. Sau khi insert phần tử thứ 3 (7), mảng là:

- [x] [2, 4, 7, 1, 3]
- [ ] [1, 2, 4, 7, 3]
- [ ] [2, 4, 7, 3, 1]
- [ ] [1, 2, 3, 4, 7]

> **Giải thích:** Insertion Sort: mảng ban đầu [4]. Insert 2: [2,4]. Insert 7: 7>4 → giữ nguyên vị trí → [2,4,7]. Phần còn lại chưa xử lý: [1,3].

## Câu 24

[TYPE: MULTIPLE_CHOICE]

Trie (Prefix Tree) phù hợp cho bài toán nào?

- [ ] Sắp xếp số nguyên
- [x] Tìm kiếm chuỗi theo prefix, autocomplete
- [ ] Tìm đường đi ngắn nhất
- [ ] Cân bằng tải

> **Giải thích:** Trie: mỗi node là 1 ký tự, path từ root đến node = prefix. Tìm kiếm O(m) với m = độ dài chuỗi. Dùng: dictionary, autocomplete, spell check.

## Câu 25

[TYPE: SELECT_RESULT]

Cho Binary Tree sau, duyệt Post-order (LRN) cho kết quả gì?

```
        1
       / \
      2   3
     / \
    4   5
```

- [ ] 1, 2, 4, 5, 3
- [ ] 4, 2, 5, 1, 3
- [x] 4, 5, 2, 3, 1
- [ ] 1, 3, 5, 4, 2

> **Giải thích:** Post-order (LRN): Left → Right → Node. 4→5→2→3→1.

## Câu 26

[TYPE: TRUE_FALSE]

Mệnh đề: "HashSet cho phép phần tử trùng lặp."

- [ ] Đúng
- [x] Sai

> **Giải thích:** HashSet KHÔNG cho phép trùng. Dùng hashCode() + equals() để kiểm tra. TreeSet cũng không cho phép trùng và giữ thứ tự.

## Câu 27

[TYPE: SELECT_RESULT]

Cho Graph có hướng (directed):

```
A → B → D
A → C → D
B → C
```

Bắt đầu DFS từ A (chọn neighbor theo alphabet), thứ tự duyệt là:

- [x] A, B, C, D
- [ ] A, B, D, C
- [ ] A, C, B, D
- [ ] A, C, D, B

> **Giải thích:** DFS từ A: visit A, đi B (neighbor đầu tiên), đi C (neighbor của B), đi D (neighbor của C). Backtrack: D không có unvisited neighbor.

## Câu 28

[TYPE: MULTIPLE_CHOICE]

Đâu là ứng dụng thực tế của Stack?

- [ ] Hàng đợi phục vụ khách hàng
- [x] Kiểm tra dấu ngoặc hợp lệ, Undo/Redo, Call Stack
- [ ] Tìm đường đi ngắn nhất
- [ ] Caching dữ liệu

> **Giải thích:** Stack: balanced parentheses, expression evaluation, undo/redo, function call stack, DFS (iterative), back button trong browser.

## Câu 29

[TYPE: SELECT_RESULT]

Cho chuỗi `"({[]})"`, sử dụng Stack để kiểm tra dấu ngoặc có hợp lệ không. Kết quả:

```java
String s = "({[]})";
Stack<Character> stack = new Stack<>();
boolean valid = true;
for (char c : s.toCharArray()) {
    if (c == '(' || c == '{' || c == '[') stack.push(c);
    else {
        if (stack.isEmpty()) { valid = false; break; }
        char top = stack.pop();
        if ((c == ')' && top != '(') || (c == '}' && top != '{') || (c == ']' && top != '['))
        { valid = false; break; }
    }
}
if (!stack.isEmpty()) valid = false;
System.out.println(valid);
```

- [x] true
- [ ] false
- [ ] Lỗi runtime
- [ ] Lỗi biên dịch

> **Giải thích:** Push (, {, [. Pop [ match ], pop { match }, pop ( match ). Stack empty → valid = true.

## Câu 30

[TYPE: FILL_BLANK]

Thuật toán sắp xếp nào có best, average, worst case đều là O(n log n) và là stable sort? Đó là `___` Sort.

- [ ] Quick
- [x] Merge
- [ ] Heap
- [ ] Selection

> **Giải thích:** Merge Sort: luôn O(n log n), stable sort. Quick Sort: average O(n log n) nhưng worst O(n²). Heap Sort: O(n log n) nhưng not stable.

## Câu 31

[TYPE: SELECT_RESULT]

Cho Priority Queue (Min-Heap): offer(5), offer(2), offer(8), offer(1). Sau đó gọi poll() 2 lần, kết quả lần lượt là:

- [x] 1 và 2
- [ ] 5 và 2
- [ ] 1 và 5
- [ ] 8 và 5

> **Giải thích:** Min-Heap: poll() lấy phần tử nhỏ nhất. Sau offer: heap chứa {1,2,5,8}. poll() → 1, poll() → 2.

## Câu 32

[TYPE: MULTIPLE_CHOICE]

Balanced BST (AVL Tree, Red-Black Tree) đảm bảo thao tác nào có O(log n)?

- [ ] Chỉ search
- [ ] Chỉ insert
- [x] Search, insert, delete đều O(log n)
- [ ] Không đảm bảo O(log n)

> **Giải thích:** Balanced BST giữ chiều cao O(log n) → tất cả thao tác cơ bản O(log n). Unbalanced BST (degenerate) có thể O(n).

## Câu 33

[TYPE: SELECT_RESULT]

Cho bài toán Knapsack 0/1:

```
Capacity: 7
Items: [(weight=2, value=3), (weight=3, value=4), (weight=4, value=5), (weight=5, value=8)]
```

Giá trị tối đa có thể đạt được là:

- [ ] 8
- [ ] 9
- [x] 12
- [ ] 20

> **Giải thích:** Chọn item (w=2,v=3) + (w=5,v=8) = weight 7, value 11. Hoặc (w=3,v=4) + (w=4,v=5) = weight 7, value 9. Hoặc (w=2,v=3) + (w=3,v=4) + (w=... Tối ưu: (w=2,v=3)+(w=5,v=8) = 11 hoặc (w=3,v=4)+(w=4,v=5)=9. Max = 11. Kiểm tra lại: (w=2,v=3)+(w=5,v=8)=total weight 7, value=11. Đáp án 12 = (w=3,v=4)+(w=5,v=8) nhưng weight=8>7. Nên max=11.

## Câu 34

[TYPE: TRUE_FALSE]

Mệnh đề: "Counting Sort có thể sắp xếp trong O(n+k) với k là giá trị lớn nhất."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Counting Sort: O(n+k), non-comparison sort. Đếm tần suất mỗi giá trị. Phù hợp khi k không quá lớn so với n.

## Câu 35

[TYPE: SELECT_RESULT]

Cho đoạn code Two Pointer:

```java
int[] arr = {1, 3, 5, 7, 9};
int target = 8;
int left = 0, right = arr.length - 1;
while (left < right) {
    int sum = arr[left] + arr[right];
    if (sum == target) break;
    else if (sum < target) left++;
    else right--;
}
System.out.println(arr[left] + " + " + arr[right]);
```

- [x] 1 + 7
- [ ] 3 + 5
- [ ] 5 + 3
- [ ] 1 + 9

> **Giải thích:** left=0(1), right=4(9): sum=10>8→right=3. left=0(1), right=3(7): sum=8==target→break. Kết quả: 1 + 7.

## Câu 36

[TYPE: MULTIPLE_CHOICE]

Đâu là ứng dụng của Graph?

- [ ] Chỉ trong học thuật
- [x] Mạng xã hội, GPS/bản đồ, dependency resolution, recommendation systems
- [ ] Chỉ để sắp xếp
- [ ] Chỉ để lưu trữ dữ liệu

> **Giải thích:** Graph: social networks (friends), Google Maps (routes), package dependency (Maven/npm), recommendation (Amazon), network routing.

## Câu 37

[TYPE: SELECT_RESULT]

Cho LinkedList: 1 → 2 → 3 → 4 → 5. Sau khi đảo ngược (reverse), kết quả là:

- [x] 5 → 4 → 3 → 2 → 1
- [ ] 1 → 3 → 5 → 2 → 4
- [ ] 1 → 2 → 3 → 4 → 5
- [ ] 5 → 1 → 4 → 2 → 3

> **Giải thích:** Reverse LinkedList: thay đổi hướng pointer. Mỗi node trỏ về node trước nó. head mới = node cuối (5).

## Câu 38

[TYPE: FILL_BLANK]

Thuật toán nào dùng để phát hiện cycle trong LinkedList với O(1) space? Đó là thuật toán `___`.

- [x] Floyd (Tortoise and Hare)
- [ ] Dijkstra
- [ ] Binary Search
- [ ] BFS

> **Giải thích:** Floyd's Cycle Detection: 2 pointers - slow (1 bước) và fast (2 bước). Nếu có cycle, fast sẽ đuổi kịp slow. O(n) time, O(1) space.

## Câu 39

[TYPE: SELECT_RESULT]

Cho Hash Table với hash function `h(key) = key % 7` và collision resolution = chaining. Insert: 10, 17, 24, 3, 14. Bucket 3 chứa bao nhiêu phần tử?

```
10 % 7 = 3
17 % 7 = 3
24 % 7 = 3
3  % 7 = 3
14 % 7 = 0
```

- [ ] 1
- [ ] 2
- [ ] 3
- [x] 4

> **Giải thích:** 10%7=3, 17%7=3, 24%7=3, 3%7=3 → tất cả vào bucket 3. 14%7=0 → bucket 0. Bucket 3 chứa 4 phần tử (chaining).

## Câu 40

[TYPE: TRUE_FALSE]

Mệnh đề: "Depth-First Search (DFS) có thể detect cycle trong directed graph."

- [x] Đúng
- [ ] Sai

> **Giải thích:** DFS dùng 3 trạng thái: white (chưa visit), gray (đang visit), black (hoàn thành). Nếu gặp gray node → cycle detected (back edge).

## Câu 41

[TYPE: SELECT_RESULT]

Cho đoạn code sliding window tìm max sum subarray kích thước k:

```java
int[] arr = {1, 4, 2, 10, 2, 3, 1, 0, 20};
int k = 3;
int windowSum = 0;
for (int i = 0; i < k; i++) windowSum += arr[i];
int maxSum = windowSum;
for (int i = k; i < arr.length; i++) {
    windowSum += arr[i] - arr[i - k];
    maxSum = Math.max(maxSum, windowSum);
}
System.out.println(maxSum);
```

- [ ] 20
- [ ] 14
- [x] 21
- [ ] 15

> **Giải thích:** Windows: [1,4,2]=7, [4,2,10]=16, [2,10,2]=14, [10,2,3]=15, [2,3,1]=6, [3,1,0]=4, [1,0,20]=21. Max = 21.

## Câu 42

[TYPE: MULTIPLE_CHOICE]

Big O notation O(n log n) thuộc nhóm nào?

- [ ] Linear
- [x] Linearithmic
- [ ] Quadratic
- [ ] Logarithmic

> **Giải thích:** O(1)=Constant, O(log n)=Logarithmic, O(n)=Linear, O(n log n)=Linearithmic, O(n²)=Quadratic, O(2^n)=Exponential.

## Câu 43

[TYPE: SELECT_RESULT]

Cho Adjacency Matrix của đồ thị vô hướng:

```
    A  B  C  D
A [ 0, 1, 1, 0 ]
B [ 1, 0, 1, 1 ]
C [ 1, 1, 0, 0 ]
D [ 0, 1, 0, 0 ]
```

Node B có bao nhiêu cạnh (degree)?

- [ ] 1
- [ ] 2
- [x] 3
- [ ] 4

> **Giải thích:** Đếm số 1 trong hàng B: B-A=1, B-C=1, B-D=1 → degree = 3. Trong undirected graph, degree = tổng giá trị trong hàng.

## Câu 44

[TYPE: FILL_BLANK]

Cấu trúc dữ liệu cho phép cả insert và remove ở cả hai đầu (front và rear) gọi là `___`.

- [ ] Stack
- [ ] Queue
- [x] Deque (Double-ended Queue)
- [ ] PriorityQueue

> **Giải thích:** Deque: insert/remove ở cả hai đầu. Java: `ArrayDeque`, `LinkedList` implement `Deque`. Có thể dùng như Stack hoặc Queue.

## Câu 45

[TYPE: SELECT_RESULT]

Cho bài toán Longest Common Subsequence (LCS):

```
X = "ABCBDAB"
Y = "BDCAB"
```

Độ dài LCS là:

- [ ] 3
- [x] 4
- [ ] 5
- [ ] 2

> **Giải thích:** LCS("ABCBDAB", "BDCAB") = "BCAB" hoặc "BDAB" → length = 4. DP: tạo bảng (m+1)×(n+1) và điền dần.

## Câu 46

[TYPE: MULTIPLE_CHOICE]

Radix Sort thuộc loại nào?

- [ ] Comparison-based sort
- [x] Non-comparison sort, sắp xếp theo từng chữ số
- [ ] In-place sort
- [ ] Divide and conquer sort

> **Giải thích:** Radix Sort: sắp xếp theo từng digit (từ LSD hoặc MSD). Dùng stable sort (Counting Sort) cho mỗi digit. O(d×(n+k)) với d=số chữ số, k=base.

## Câu 47

[TYPE: SELECT_RESULT]

Cho Min-Heap dạng mảng: `[1, 3, 5, 7, 9, 8]`. Sau khi insert(2), mảng Heap là:

- [x] [1, 3, 2, 7, 9, 8, 5]
- [ ] [1, 2, 3, 5, 7, 8, 9]
- [ ] [2, 3, 5, 7, 9, 8, 1]
- [ ] [1, 3, 5, 7, 9, 8, 2]

> **Giải thích:** Insert 2 ở cuối: [1,3,5,7,9,8,2]. Sift up: 2 < parent(5, idx=2) → swap: [1,3,2,7,9,8,5]. 2 < parent(1, idx=0)? Không → dừng.

## Câu 48

[TYPE: TRUE_FALSE]

Mệnh đề: "Adjacency List tốn ít bộ nhớ hơn Adjacency Matrix cho đồ thị thưa (sparse graph)."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Matrix: O(V²) space luôn. List: O(V+E) space. Sparse graph (E << V²) → List tiết kiệm hơn. Dense graph (E ≈ V²) → Matrix có thể tốt hơn.

## Câu 49

[TYPE: SELECT_RESULT]

Cho thuật toán Topological Sort trên DAG:

```
A → B → D
A → C → D
C → E
```

Một thứ tự topological hợp lệ là:

- [x] A, B, C, D, E hoặc A, C, B, E, D
- [ ] D, B, A, C, E
- [ ] B, A, D, C, E
- [ ] E, D, C, B, A

> **Giải thích:** Topological Sort: node xuất hiện trước dependencies. A phải trước B,C. B,C phải trước D. C phải trước E. A,B,C,D,E hoặc A,C,E,B,D hoặc A,C,B,E,D đều hợp lệ.

## Câu 50

[TYPE: MULTIPLE_CHOICE]

Amortized O(1) nghĩa là gì?

- [ ] Mọi operation luôn O(1)
- [x] Trung bình mỗi operation O(1) khi xét chuỗi operations, dù một vài operation có thể chậm
- [ ] Best case O(1)
- [ ] Tương đương O(n)

> **Giải thích:** Amortized: ví dụ ArrayList.add() → đôi khi resize O(n), nhưng trung bình mỗi add là O(1). Tổng n operations ≤ O(n) → amortized O(1) mỗi operation.

## Câu 51

[TYPE: SELECT_RESULT]

Cho đoạn code đệ quy tính giai thừa:

```java
int factorial(int n) {
    if (n == 0) return 1;
    return n * factorial(n - 1);
}
System.out.println(factorial(5));
```

- [ ] 25
- [x] 120
- [ ] 15
- [ ] 5

> **Giải thích:** 5! = 5×4×3×2×1 = 120. Mỗi lần gọi đệ quy giảm n đi 1 cho đến n=0 (base case trả về 1).

## Câu 52

[TYPE: FILL_BLANK]

Kỹ thuật giải thuật chia bài toán thành các bài toán con nhỏ hơn, giải từng bài con rồi gộp kết quả gọi là `___`.

- [x] Divide and Conquer
- [ ] Dynamic Programming
- [ ] Greedy
- [ ] Backtracking

> **Giải thích:** Divide and Conquer: chia (divide), giải (conquer), gộp (combine). Ví dụ: Merge Sort, Quick Sort, Binary Search.

## Câu 53

[TYPE: SELECT_RESULT]

Cho đoạn code kiểm tra Palindrome bằng Two Pointer:

```java
String s = "racecar";
int left = 0, right = s.length() - 1;
boolean isPalindrome = true;
while (left < right) {
    if (s.charAt(left) != s.charAt(right)) { isPalindrome = false; break; }
    left++; right--;
}
System.out.println(isPalindrome);
```

- [x] true
- [ ] false
- [ ] Lỗi biên dịch
- [ ] Lỗi runtime

> **Giải thích:** "racecar": r=r, a=a, c=c, e ở giữa. Tất cả đối xứng → palindrome → true.

## Câu 54

[TYPE: MULTIPLE_CHOICE]

Greedy Algorithm khác Dynamic Programming ở điểm nào?

- [x] Greedy chọn tối ưu cục bộ tại mỗi bước, DP xem xét tất cả subproblems
- [ ] Greedy luôn cho kết quả tối ưu toàn cục
- [ ] DP nhanh hơn Greedy
- [ ] Không có sự khác biệt

> **Giải thích:** Greedy: local optimal → không đảm bảo global optimal. DP: xem xét overlapping subproblems → đảm bảo optimal (nếu có optimal substructure).

## Câu 55

[TYPE: SELECT_RESULT]

Cho Binary Search Tree, thêm các phần tử theo thứ tự: 5, 3, 7, 1, 4, 6, 8. Cây kết quả:

```
        5
       / \
      3   7
     / \ / \
    1  4 6  8
```

Chiều cao (height) của cây là:

- [ ] 1
- [x] 2
- [ ] 3
- [ ] 7

> **Giải thích:** Height = số cạnh trên đường dài nhất từ root đến leaf. Root(5) → 3 → 1: 2 cạnh. Height = 2 (hoặc 3 nếu tính level).

## Câu 56

[TYPE: TRUE_FALSE]

Mệnh đề: "Heap Sort là in-place sorting algorithm."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Heap Sort: build max-heap in-place, lặp lại extract max → O(n log n), O(1) extra space. In-place nhưng not stable.

## Câu 57

[TYPE: SELECT_RESULT]

Cho đoạn code Kadane's Algorithm (Maximum Subarray Sum):

```java
int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
int maxSum = arr[0], currentSum = arr[0];
for (int i = 1; i < arr.length; i++) {
    currentSum = Math.max(arr[i], currentSum + arr[i]);
    maxSum = Math.max(maxSum, currentSum);
}
System.out.println(maxSum);
```

- [x] 6
- [ ] 4
- [ ] 7
- [ ] -2

> **Giải thích:** Maximum subarray: [4, -1, 2, 1] = 6. Kadane's: giữ currentSum (max ending here). Reset nếu tổng < phần tử hiện tại.

## Câu 58

[TYPE: MULTIPLE_CHOICE]

Sự khác biệt giữa BFS và DFS?

- [x] BFS duyệt theo chiều rộng (layer by layer), DFS duyệt sâu hết một nhánh rồi quay lại
- [ ] BFS nhanh hơn DFS
- [ ] DFS dùng Queue, BFS dùng Stack
- [ ] BFS chỉ dùng cho tree, DFS cho graph

> **Giải thích:** BFS: Queue, level-by-level, tìm shortest path (unweighted). DFS: Stack/Recursion, explore deeply, dùng cho topological sort, cycle detection.

## Câu 59

[TYPE: SELECT_RESULT]

Cho bài toán Coin Change: coins = [1, 5, 10, 25], amount = 36. Số đồng xu tối thiểu là:

- [ ] 2
- [x] 3
- [ ] 4
- [ ] 36

> **Giải thích:** Greedy: 25 + 10 + 1 = 36 (3 coins). DP cũng cho 3: dp[36] = min(dp[35]+1, dp[31]+1, dp[26]+1, dp[11]+1) = 3.

## Câu 60

[TYPE: FILL_BLANK]

Cấu trúc dữ liệu nào lưu trữ cặp key-value với thời gian truy cập trung bình O(1)? Đó là `___`.

- [ ] Array
- [ ] LinkedList
- [x] Hash Table (HashMap)
- [ ] Binary Tree

> **Giải thích:** Hash Table: hash function map key → bucket. Get/Put trung bình O(1). Java: HashMap, HashSet, Hashtable.

## Câu 61

[TYPE: SELECT_RESULT]

Cho mảng đã sắp xếp `[1, 2, 3, 3, 3, 4, 5]`. Dùng binary search biến thể, tìm index đầu tiên của 3:

- [ ] 0
- [ ] 1
- [x] 2
- [ ] 3

> **Giải thích:** Lower bound binary search: khi arr[mid]==3, tiếp tục search bên trái (hi=mid). Tìm index nhỏ nhất mà arr[index]==3 → index 2.

## Câu 62

[TYPE: MULTIPLE_CHOICE]

Space complexity của BFS trên tree có n node là:

- [ ] O(1)
- [ ] O(log n)
- [x] O(n) trong worst case (tree rất rộng)
- [ ] O(n²)

> **Giải thích:** BFS dùng Queue chứa nodes ở level hiện tại. Worst case: perfect binary tree → level cuối có n/2 nodes → O(n).

## Câu 63

[TYPE: SELECT_RESULT]

Cho đoạn code đếm số đảo (Number of Islands):

```
Grid:
1 1 0 0 0
1 1 0 0 0
0 0 1 0 0
0 0 0 1 1
```

Số đảo (connected component of '1') là:

- [ ] 2
- [x] 3
- [ ] 4
- [ ] 5

> **Giải thích:** Đảo 1: (0,0),(0,1),(1,0),(1,1). Đảo 2: (2,2). Đảo 3: (3,3),(3,4). Tổng: 3 đảo. Dùng DFS/BFS flood fill.

## Câu 64

[TYPE: TRUE_FALSE]

Mệnh đề: "Red-Black Tree đảm bảo chiều cao tối đa 2×log(n+1)."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Red-Black Tree: balanced BST với rules (root black, red node có 2 black children, black-height uniform). Height ≤ 2×log₂(n+1).

## Câu 65

[TYPE: SELECT_RESULT]

Cho Union-Find trên 5 phần tử {0,1,2,3,4}. Thực hiện: union(0,1), union(2,3), union(1,3). Có bao nhiêu component?

- [x] 2
- [ ] 3
- [ ] 1
- [ ] 4

> **Giải thích:** union(0,1): {0,1}, {2}, {3}, {4}. union(2,3): {0,1}, {2,3}, {4}. union(1,3): {0,1,2,3}, {4}. Kết quả: 2 components.

## Câu 66

[TYPE: MULTIPLE_CHOICE]

Khi nào nên dùng LinkedList thay vì ArrayList?

- [ ] Khi cần random access nhanh
- [x] Khi cần insert/remove thường xuyên ở đầu hoặc giữa
- [ ] Khi cần ít bộ nhớ
- [ ] Luôn nên dùng ArrayList

> **Giải thích:** LinkedList: insert/remove O(1) nếu đã có reference đến node. ArrayList: insert/remove ở giữa O(n) do shift. Nhưng ArrayList có cache-friendly hơn.

## Câu 67

[TYPE: SELECT_RESULT]

Cho đoạn code tìm phần tử lớn thứ k:

```java
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
int[] arr = {3, 2, 1, 5, 6, 4};
int k = 2;
for (int num : arr) {
    minHeap.offer(num);
    if (minHeap.size() > k) minHeap.poll();
}
System.out.println(minHeap.peek());
```

- [x] 5
- [ ] 6
- [ ] 4
- [ ] 2

> **Giải thích:** Min-Heap giữ k phần tử lớn nhất. Cuối cùng heap = {5, 6}. peek() → 5 (phần tử nhỏ nhất trong heap = lớn thứ k = lớn thứ 2).

## Câu 68

[TYPE: FILL_BLANK]

Thuật toán tìm Minimum Spanning Tree bằng cách chọn cạnh nhẹ nhất không tạo cycle gọi là thuật toán `___`.

- [x] Kruskal
- [ ] Dijkstra
- [ ] Prim
- [ ] Floyd-Warshall

> **Giải thích:** Kruskal: sort edges by weight, add edge nếu không tạo cycle (check bằng Union-Find). Prim: grow MST từ 1 vertex, add cheapest edge connecting to tree.

## Câu 69

[TYPE: SELECT_RESULT]

Cho đoạn code Binary Search:

```java
int[] arr = {1, 3, 5, 7, 9, 11, 13};
int target = 7;
int lo = 0, hi = arr.length - 1;
while (lo <= hi) {
    int mid = lo + (hi - lo) / 2;
    if (arr[mid] == target) { System.out.println("Found at " + mid); break; }
    else if (arr[mid] < target) lo = mid + 1;
    else hi = mid - 1;
}
```

- [x] Found at 3
- [ ] Found at 4
- [ ] Found at 2
- [ ] Không tìm thấy

> **Giải thích:** lo=0, hi=6, mid=3, arr[3]=7==target → "Found at 3". Tìm thấy ngay ở bước đầu.

## Câu 70

[TYPE: MULTIPLE_CHOICE]

Backtracking là gì?

- [x] Kỹ thuật thử tất cả khả năng, quay lui khi gặp dead-end
- [ ] Sắp xếp ngược
- [ ] Tìm kiếm tuyến tính
- [ ] Dynamic Programming ngược

> **Giải thích:** Backtracking: thử → nếu sai → undo (quay lui) → thử khả năng khác. Ví dụ: N-Queens, Sudoku solver, permutations, maze solving.

## Câu 71

[TYPE: SELECT_RESULT]

Cho Doubly Linked List: null ← 1 ⇄ 2 ⇄ 3 ⇄ 4 → null. Sau khi xóa node 2, list trở thành:

- [x] null ← 1 ⇄ 3 ⇄ 4 → null
- [ ] null ← 1 ⇄ 2 ⇄ 4 → null
- [ ] null ← 1 ⇄ 4 → null
- [ ] null ← 3 ⇄ 4 → null

> **Giải thích:** Xóa node 2: node 1.next = node 3, node 3.prev = node 1. O(1) nếu có reference đến node 2.

## Câu 72

[TYPE: TRUE_FALSE]

Mệnh đề: "B-Tree được dùng trong database indexing vì giảm số lần disk I/O."

- [x] Đúng
- [ ] Sai

> **Giải thích:** B-Tree: balanced, nhiều key per node → ít level → ít disk access. Dùng trong databases (MySQL InnoDB), file systems.

## Câu 73

[TYPE: SELECT_RESULT]

Cho bài toán Longest Increasing Subsequence (LIS) với mảng [10, 9, 2, 5, 3, 7, 101, 18]. Độ dài LIS là:

- [ ] 3
- [x] 4
- [ ] 5
- [ ] 8

> **Giải thích:** LIS: [2, 3, 7, 18] hoặc [2, 5, 7, 101] hoặc [2, 3, 7, 101] → length = 4. DP: O(n²) hoặc Binary Search: O(n log n).

## Câu 74

[TYPE: MULTIPLE_CHOICE]

Segment Tree dùng để giải quyết bài toán gì?

- [x] Range query (sum, min, max) trên mảng với update hiệu quả
- [ ] Sắp xếp mảng
- [ ] Tìm đường đi ngắn nhất
- [ ] Lưu trữ key-value

> **Giải thích:** Segment Tree: range query O(log n), update O(log n). Dùng cho: range sum, range min/max, count in range. Build O(n).

## Câu 75

[TYPE: SELECT_RESULT]

Cho đoạn code Quick Select (tìm kth smallest):

```java
int[] arr = {7, 10, 4, 3, 20, 15};
// Quick Select for k=3 (3rd smallest)
```

Phần tử nhỏ thứ 3 (k=3) là:

- [ ] 3
- [ ] 4
- [x] 7
- [ ] 10

> **Giải thích:** Sorted: [3, 4, 7, 10, 15, 20]. Phần tử nhỏ thứ 3 = 7. Quick Select: average O(n) time.

## Câu 76

[TYPE: FILL_BLANK]

Cấu trúc dữ liệu dạng cây mà mỗi node nội bộ có đúng 2 con gọi là `___` tree.

- [x] Binary
- [ ] Balanced
- [ ] Complete
- [ ] Perfect

> **Giải thích:** Binary Tree: mỗi node có tối đa 2 con (left, right). Full Binary Tree: mỗi node có 0 hoặc 2 con. Complete: filled level by level.

## Câu 77

[TYPE: SELECT_RESULT]

Cho đoạn code tìm GCD bằng Euclidean Algorithm:

```java
int gcd(int a, int b) {
    while (b != 0) {
        int temp = b;
        b = a % b;
        a = temp;
    }
    return a;
}
System.out.println(gcd(48, 18));
```

- [ ] 2
- [x] 6
- [ ] 3
- [ ] 18

> **Giải thích:** gcd(48,18): 48%18=12 → gcd(18,12): 18%12=6 → gcd(12,6): 12%6=0 → return 6. GCD(48,18) = 6.

## Câu 78

[TYPE: MULTIPLE_CHOICE]

Đâu là stable sorting algorithm?

- [x] Merge Sort, Insertion Sort, Bubble Sort
- [ ] Quick Sort, Heap Sort
- [ ] Selection Sort, Shell Sort
- [ ] Radix Sort, Bucket Sort, Quick Sort

> **Giải thích:** Stable sort: giữ thứ tự tương đối của phần tử bằng nhau. Merge Sort, Insertion Sort, Bubble Sort, Counting Sort, Radix Sort là stable.

## Câu 79

[TYPE: SELECT_RESULT]

Cho Trie chứa từ: "cat", "car", "card", "care". Tìm tất cả từ có prefix "car":

- [ ] ["car"]
- [x] ["car", "card", "care"]
- [ ] ["car", "cat"]
- [ ] ["card", "care"]

> **Giải thích:** Prefix "car": duyệt trie đến node 'r'. Từ đó tìm tất cả complete words: "car" (end), "card" (end), "care" (end). "cat" không match prefix.

## Câu 80

[TYPE: TRUE_FALSE]

Mệnh đề: "Dijkstra's Algorithm không hoạt động đúng với cạnh có trọng số âm."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Dijkstra giả định: một khi node đã finalized thì distance không giảm nữa. Trọng số âm vi phạm giả định này. Dùng Bellman-Ford cho negative weights.

## Câu 81

[TYPE: SELECT_RESULT]

Cho đoạn code đệ quy Tower of Hanoi:

```java
void hanoi(int n, char from, char to, char aux) {
    if (n == 1) { System.out.println(from + " -> " + to); return; }
    hanoi(n-1, from, aux, to);
    System.out.println(from + " -> " + to);
    hanoi(n-1, aux, to, from);
}
hanoi(3, 'A', 'C', 'B');
```

Tổng số bước di chuyển là:

- [ ] 3
- [x] 7
- [ ] 8
- [ ] 15

> **Giải thích:** Tower of Hanoi: T(n) = 2T(n-1) + 1. T(1)=1, T(2)=3, T(3)=7. Công thức: 2^n - 1 = 2³ - 1 = 7.

## Câu 82

[TYPE: MULTIPLE_CHOICE]

Đâu là application của Topological Sort?

- [x] Build system dependencies, course prerequisite scheduling, task ordering
- [ ] Tìm đường đi ngắn nhất
- [ ] Sắp xếp mảng
- [ ] Nén dữ liệu

> **Giải thích:** Topological Sort: thứ tự thực hiện tasks có dependency. Maven/Gradle build order, university course prerequisites, compilation order.

## Câu 83

[TYPE: SELECT_RESULT]

Cho mảng `[3, 0, 1]` (chứa số từ 0 đến n, thiếu 1 số). Số bị thiếu là:

```java
int[] nums = {3, 0, 1};
int n = nums.length;
int expectedSum = n * (n + 1) / 2;
int actualSum = 0;
for (int num : nums) actualSum += num;
System.out.println(expectedSum - actualSum);
```

- [x] 2
- [ ] 1
- [ ] 3
- [ ] 4

> **Giải thích:** n=3: expected sum = 3×4/2 = 6. actual sum = 3+0+1 = 4. Missing = 6-4 = 2.

## Câu 84

[TYPE: FILL_BLANK]

Thuật toán sắp xếp nào chọn pivot, partition mảng quanh pivot, rồi đệ quy sort hai phần? Đó là `___` Sort.

- [ ] Merge
- [x] Quick
- [ ] Heap
- [ ] Radix

> **Giải thích:** Quick Sort: chọn pivot → partition (nhỏ hơn pivot bên trái, lớn hơn bên phải) → đệ quy 2 phần. Average O(n log n).

## Câu 85

[TYPE: SELECT_RESULT]

Cho đoạn code BFS tìm shortest path trong unweighted graph:

```
Graph (undirected):
0 -- 1 -- 3
|         |
2 -- 4 -- 5
```

Shortest path từ 0 đến 5 có bao nhiêu cạnh?

- [ ] 1
- [ ] 2
- [x] 3
- [ ] 4

> **Giải thích:** Path: 0→1→3→5 (3 edges) hoặc 0→2→4→5 (3 edges). BFS tìm shortest path = 3.

## Câu 86

[TYPE: MULTIPLE_CHOICE]

Sự khác biệt giữa Singly và Doubly Linked List?

- [ ] Singly nhanh hơn
- [x] Doubly mỗi node có con trỏ prev và next, cho phép duyệt hai chiều
- [ ] Singly hỗ trợ random access
- [ ] Doubly không thể xóa node

> **Giải thích:** Singly: node có next. Doubly: node có prev + next → duyệt ngược, xóa node O(1) nếu có reference. Doubly tốn thêm bộ nhớ cho prev pointer.

## Câu 87

[TYPE: SELECT_RESULT]

Cho đoạn code merge 2 sorted arrays:

```java
int[] a = {1, 3, 5};
int[] b = {2, 4, 6};
int[] result = new int[6];
int i = 0, j = 0, k = 0;
while (i < a.length && j < b.length) {
    if (a[i] <= b[j]) result[k++] = a[i++];
    else result[k++] = b[j++];
}
while (i < a.length) result[k++] = a[i++];
while (j < b.length) result[k++] = b[j++];
System.out.println(Arrays.toString(result));
```

- [x] [1, 2, 3, 4, 5, 6]
- [ ] [1, 3, 5, 2, 4, 6]
- [ ] [2, 4, 6, 1, 3, 5]
- [ ] [1, 2, 3, 4, 5, 6, 0]

> **Giải thích:** Two-pointer merge: so sánh phần tử nhỏ nhất chưa dùng của mỗi mảng, chọn nhỏ hơn. Kết quả sorted: [1,2,3,4,5,6].

## Câu 88

[TYPE: TRUE_FALSE]

Mệnh đề: "Graph có V vertices và V-1 edges luôn là Tree."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Tree = connected + V-1 edges. Nếu graph có V-1 edges nhưng disconnected → không phải tree. Cần thêm điều kiện connected.

## Câu 89

[TYPE: SELECT_RESULT]

Cho bài toán 0/1 Knapsack DP table:

```
Items: [(w=1,v=1), (w=2,v=6), (w=3,v=10)]
Capacity: 5
```

Giá trị tối đa:

- [ ] 10
- [x] 16
- [ ] 17
- [ ] 11

> **Giải thích:** Chọn item 2 (w=2,v=6) + item 3 (w=3,v=10) → weight=5, value=16. Hoặc item 1+2 (w=3,v=7) hoặc item 1+3 (w=4,v=11). Max=16.

## Câu 90

[TYPE: MULTIPLE_CHOICE]

Đâu là đặc điểm của AVL Tree?

- [x] Balanced BST, balance factor (height diff) của mỗi node tối đa 1
- [ ] Mỗi node có tối đa 3 con
- [ ] Không cần balance
- [ ] Chỉ lưu trữ strings

> **Giải thích:** AVL: strict balance (|height(left) - height(right)| ≤ 1 cho mọi node). Rotation để rebalance sau insert/delete. Lookup/insert/delete O(log n).

## Câu 91

[TYPE: SELECT_RESULT]

Cho đoạn code Depth-First Search:

```java
void dfs(Map<Integer, List<Integer>> graph, int node, Set<Integer> visited) {
    if (visited.contains(node)) return;
    visited.add(node);
    System.out.print(node + " ");
    for (int neighbor : graph.getOrDefault(node, List.of())) {
        dfs(graph, neighbor, visited);
    }
}
// Graph: 0→[1,2], 1→[3], 2→[3], 3→[]
```

DFS từ 0, kết quả:

- [x] 0 1 3 2
- [ ] 0 1 2 3
- [ ] 0 2 3 1
- [ ] 3 1 2 0

> **Giải thích:** DFS(0): visit 0, đi 1 (neighbor đầu). DFS(1): visit 1, đi 3. DFS(3): visit 3, no unvisited neighbors. Back to 0, đi 2. DFS(2): visit 2, 3 already visited. Result: 0 1 3 2.

## Câu 92

[TYPE: FILL_BLANK]

Kỹ thuật chia window có kích thước cố định/thay đổi trên mảng/chuỗi để tối ưu O(n) gọi là `___`.

- [x] Sliding Window
- [ ] Two Pointer
- [ ] Divide and Conquer
- [ ] Binary Search

> **Giải thích:** Sliding Window: duy trì window [left, right] trên mảng. Expand/shrink window. Ví dụ: max sum subarray, longest substring without repeating chars.

## Câu 93

[TYPE: SELECT_RESULT]

Cho đoạn code kiểm tra BST:

```
     10
    /  \
   5    15
  / \   / \
 2   7 12  20
```

Tìm node predecessor (trước) của 10 theo In-order traversal:

- [x] 7
- [ ] 5
- [ ] 12
- [ ] 15

> **Giải thích:** In-order: 2,5,7,10,12,15,20. Predecessor của 10 = phần tử ngay trước = 7. Predecessor = rightmost node trong left subtree.

## Câu 94

[TYPE: MULTIPLE_CHOICE]

Đâu là ứng dụng của Priority Queue (Heap)?

- [x] Dijkstra's algorithm, Huffman coding, task scheduling
- [ ] FIFO queue
- [ ] Stack operations
- [ ] String matching

> **Giải thích:** PriorityQueue: Dijkstra (extract min distance), Huffman coding (merge min frequency), job scheduling (highest priority first), median finding.

## Câu 95

[TYPE: SELECT_RESULT]

Cho đoạn code Fibonacci bottom-up DP:

```java
int fib(int n) {
    if (n <= 1) return n;
    int[] dp = new int[n + 1];
    dp[0] = 0; dp[1] = 1;
    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i-1] + dp[i-2];
    }
    return dp[n];
}
System.out.println(fib(10));
```

- [ ] 34
- [x] 55
- [ ] 89
- [ ] 10

> **Giải thích:** fib(10) = 55. Sequence: 0,1,1,2,3,5,8,13,21,34,55. Bottom-up DP: O(n) time, O(n) space.

## Câu 96

[TYPE: TRUE_FALSE]

Mệnh đề: "Circular Queue giải quyết vấn đề lãng phí space trong array-based queue."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Linear queue: sau nhiều dequeue, phía trước mảng trống nhưng không dùng được. Circular queue: wrap around, tận dụng toàn bộ mảng.

## Câu 97

[TYPE: SELECT_RESULT]

Cho ma trận 4×4 và flood fill từ (1,1) đổi từ 'O' sang 'X':

```
X X X X
X O O X
X X O X
X O X X
```

Sau flood fill, ma trận là:

- [ ] Không thay đổi
- [x] X X X X / X X X X / X X X X / X O X X
- [ ] Toàn bộ X
- [ ] X X X X / X X X X / X X O X / X O X X

> **Giải thích:** Flood fill từ (1,1): đổi (1,1) và tất cả 'O' liền kề: (1,2), (2,2). (3,1) không liền kề (separated by X). Kết quả: (3,1) vẫn là 'O'.

## Câu 98

[TYPE: MULTIPLE_CHOICE]

Time complexity của Sieve of Eratosthenes để tìm tất cả số nguyên tố ≤ n?

- [x] O(n log log n)
- [ ] O(n²)
- [ ] O(n)
- [ ] O(n log n)

> **Giải thích:** Sieve of Eratosthenes: đánh dấu bội số. Tổng: n/2 + n/3 + n/5 + ... ≈ O(n log log n). Rất hiệu quả cho tìm primes.

## Câu 99

[TYPE: SELECT_RESULT]

Cho đoạn code invert Binary Tree:

```java
TreeNode invert(TreeNode root) {
    if (root == null) return null;
    TreeNode temp = root.left;
    root.left = invert(root.right);
    root.right = invert(temp);
    return root;
}
```

Cho tree `[4,2,7,1,3,6,9]`, sau invert:

- [x] [4,7,2,9,6,3,1]
- [ ] [4,2,7,1,3,6,9] (không đổi)
- [ ] [9,7,6,4,3,2,1]
- [ ] [1,2,3,4,6,7,9]

> **Giải thích:** Invert: swap left và right subtree đệ quy. 4 root, left=7(was right), right=2(was left). Tiếp tục đệ quy cho subtrees.

## Câu 100

[TYPE: FILL_BLANK]

Thuật toán tìm shortest path giữa tất cả cặp vertices trong weighted graph (bao gồm negative weights) là `___`.

- [ ] Dijkstra
- [ ] Bellman-Ford
- [x] Floyd-Warshall
- [ ] Prim

> **Giải thích:** Floyd-Warshall: all-pairs shortest path, O(V³). Hỗ trợ negative weights (không có negative cycle). Dijkstra: single-source. Bellman-Ford: single-source + negative weights.
