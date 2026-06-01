# Quiz - Cấu Trúc Dữ Liệu & Giải Thuật

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Độ phức tạp thời gian trung bình của Binary Search là gì?

- [ ] O(n)
- [x] O(log n)
- [ ] O(n log n)
- [ ] O(1)

> **Giải thích:** Binary Search chia đôi không gian tìm kiếm sau mỗi bước, nên độ phức tạp là O(log n). Yêu cầu mảng đã được sắp xếp.

## Câu 2

[TYPE: SELECT_RESULT]

Cho mảng `[3, 6, 2, 8, 1]`. Sau khi thực hiện một lần partition của QuickSort (pivot = phần tử cuối = 1), mảng sẽ trở thành?

```
Mảng ban đầu: [3, 6, 2, 8, 1]
Pivot = 1 (phần tử cuối)
```

- [x] [1, 6, 2, 8, 3]
- [ ] [1, 2, 3, 6, 8]
- [ ] [3, 6, 2, 8, 1]
- [ ] [1, 3, 2, 6, 8]

> **Giải thích:** Partition đặt pivot (1) vào đúng vị trí. Vì 1 nhỏ nhất, nó được đặt ở đầu mảng. Các phần tử khác ở bên phải (chưa sắp xếp).

## Câu 3

[TYPE: FILL_BLANK]

Cấu trúc dữ liệu `___` hoạt động theo nguyên tắc LIFO (Last In, First Out).

- [ ] Queue
- [x] Stack
- [ ] LinkedList
- [ ] Tree

> **Giải thích:** Stack hoạt động theo LIFO — phần tử được thêm vào cuối cùng sẽ được lấy ra đầu tiên. Queue hoạt động theo FIFO (First In, First Out).

## Câu 4

[TYPE: TRUE_FALSE]

Mệnh đề: "Linked List có thời gian truy cập phần tử theo index là O(1)."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Linked List có thời gian truy cập O(n) vì phải duyệt từ đầu. Array/ArrayList mới có O(1) cho truy cập theo index.

## Câu 5

[TYPE: MULTIPLE_CHOICE]

Thuật toán sắp xếp nào có độ phức tạp thời gian trường hợp xấu nhất là O(n log n)?

- [ ] Quick Sort
- [x] Merge Sort
- [ ] Bubble Sort
- [ ] Selection Sort

> **Giải thích:** Merge Sort luôn có O(n log n) trong mọi trường hợp. Quick Sort có worst case O(n²). Bubble Sort và Selection Sort đều O(n²).

## Câu 6

[TYPE: SELECT_RESULT]

Cho Binary Search Tree với các giá trị được chèn theo thứ tự: 5, 3, 7, 1, 4. Duyệt Inorder cho kết quả gì?

```
     5
    / \
   3   7
  / \
 1   4
```

- [ ] 5, 3, 1, 4, 7
- [ ] 5, 3, 7, 1, 4
- [x] 1, 3, 4, 5, 7
- [ ] 1, 4, 3, 7, 5

> **Giải thích:** Inorder traversal (Left → Root → Right) của BST luôn cho kết quả sắp xếp tăng dần: 1, 3, 4, 5, 7.

## Câu 7

[TYPE: FILL_BLANK]

Cấu trúc dữ liệu `___` sử dụng hàm băm (hash function) để ánh xạ key sang value với thời gian trung bình O(1).

- [x] HashMap
- [ ] TreeMap
- [ ] LinkedList
- [ ] Array

> **Giải thích:** HashMap sử dụng hash function để tính index trong mảng nội bộ, cho phép truy cập, thêm, xóa với O(1) trung bình.

## Câu 8

[TYPE: TRUE_FALSE]

Mệnh đề: "Trong đồ thị có hướng, BFS (Breadth-First Search) luôn tìm được đường đi ngắn nhất."

- [x] Đúng
- [ ] Sai

> **Giải thích:** BFS duyệt theo tầng (level by level), nên luôn tìm được đường đi ngắn nhất (tính theo số cạnh) trong đồ thị không trọng số.

## Câu 9

[TYPE: MULTIPLE_CHOICE]

Cấu trúc dữ liệu nào phù hợp nhất để implement Priority Queue?

- [ ] Array
- [ ] Linked List
- [x] Heap
- [ ] Stack

> **Giải thích:** Heap (Min-Heap hoặc Max-Heap) là cấu trúc tối ưu cho Priority Queue với insert O(log n) và extract-min/max O(log n).

## Câu 10

[TYPE: SELECT_RESULT]

Cho bài toán Dynamic Programming: Fibonacci(5) = ?

```
F(0) = 0
F(1) = 1
F(n) = F(n-1) + F(n-2)
```

- [ ] 3
- [x] 5
- [ ] 8
- [ ] 13

> **Giải thích:** F(0)=0, F(1)=1, F(2)=1, F(3)=2, F(4)=3, F(5)=5. Dynamic Programming giúp tránh tính lại các subproblem.
