# Quiz - Queue

## Câu 1

[TYPE: FILL_BLANK]

Cấu trúc dữ liệu FIFO (First In, First Out) được gọi là `___`.

- [x] Queue
- [ ] Stack
- [ ] Deque
- [ ] PriorityQueue

> **Giải thích:** Queue: FIFO. Enqueue (thêm cuối) và Dequeue (lấy đầu).

## Câu 2

[TYPE: SELECT_RESULT]

Cho Queue ban đầu rỗng, thực hiện: enqueue(A), enqueue(B), enqueue(C), dequeue(), enqueue(D). Queue hiện tại là gì?

- [ ] [A, B, D]
- [x] [B, C, D]
- [ ] [B, C]
- [ ] [A, C, D]

> **Giải thích:** enqueue(A)→[A], enqueue(B)→[A,B], enqueue(C)→[A,B,C], dequeue()→[B,C], enqueue(D)→[B,C,D].

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Cấu trúc dữ liệu nào phù hợp nhất để implement BFS?

- [ ] Stack
- [x] Queue
- [ ] Tree
- [ ] HashMap

> **Giải thích:** BFS dùng Queue (FIFO): duyệt theo chiều rộng, xử lý node gần nhất trước. DFS dùng Stack.

## Câu 4

[TYPE: MULTIPLE_CHOICE]

`PriorityQueue` trong Java mặc định là loại nào?

- [x] Min-Heap (phần tử nhỏ nhất ra trước)
- [ ] Max-Heap (phần tử lớn nhất ra trước)
- [ ] FIFO Queue
- [ ] LIFO Stack

> **Giải thích:** Java PriorityQueue mặc định là min-heap. Để dùng max-heap: `new PriorityQueue<>(Collections.reverseOrder())`.

## Câu 5

[TYPE: MULTIPLE_CHOICE]

Deque (Double-Ended Queue) khác Queue thường ở điểm nào?

- [ ] Deque chỉ thêm ở đầu
- [ ] Deque chỉ xóa ở cuối
- [x] Deque thêm/xóa được ở cả hai đầu
- [ ] Deque tự động sắp xếp

> **Giải thích:** Deque hỗ trợ offerFirst, offerLast, pollFirst, pollLast — thao tác ở cả hai đầu. Có thể dùng như cả Stack lẫn Queue.

## Câu 6

[TYPE: TRUE_FALSE]

Mệnh đề: "ArrayDeque nhanh hơn LinkedList khi implement Queue."

- [x] Đúng
- [ ] Sai

> **Giải thích:** ArrayDeque dùng circular array → cache-friendly, ít overhead. LinkedList dùng pointer → overhead bộ nhớ, cache miss nhiều hơn.

## Câu 7

[TYPE: SELECT_RESULT]

Cho PriorityQueue (min-heap): offer(5), offer(1), offer(3), offer(2). Thực hiện poll() 2 lần, kết quả lần lượt là gì?

- [ ] 5 rồi 3
- [x] 1 rồi 2
- [ ] 5 rồi 1
- [ ] 1 rồi 3

> **Giải thích:** Min-heap poll ra phần tử nhỏ nhất: poll()→1, poll()→2.

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Trong bài toán Rotting Oranges (cam thối lan sang cam tươi), nên dùng thuật toán nào?

- [ ] DFS
- [x] Multi-source BFS
- [ ] Dynamic Programming
- [ ] Binary Search

> **Giải thích:** Multi-source BFS bắt đầu từ tất cả cam thối đồng thời, mỗi level BFS = 1 phút. Tìm thời gian ngắn nhất.
