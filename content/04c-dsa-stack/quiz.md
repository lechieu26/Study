# Quiz - Stack

## Câu 1

[TYPE: FILL_BLANK]

Cấu trúc dữ liệu LIFO (Last In, First Out) được gọi là `___`.

- [ ] Queue
- [x] Stack
- [ ] Array
- [ ] Tree

> **Giải thích:** Stack: LIFO, phần tử vào sau ra trước. Push (thêm) và Pop (lấy) đều ở đỉnh.

## Câu 2

[TYPE: SELECT_RESULT]

Cho Stack ban đầu rỗng, thực hiện: push(1), push(2), push(3), pop(), push(4), pop(). Stack còn lại chứa gì?

- [ ] [1]
- [x] [1, 2]
- [ ] [1, 4]
- [ ] [2, 4]

> **Giải thích:** push(1)→[1], push(2)→[1,2], push(3)→[1,2,3], pop()→[1,2], push(4)→[1,2,4], pop()→[1,2].

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Trong Java, nên dùng class nào để implement Stack?

- [ ] `java.util.Stack`
- [x] `ArrayDeque` (dùng như Stack)
- [ ] `LinkedList`
- [ ] `ArrayList`

> **Giải thích:** `ArrayDeque` nhanh hơn `Stack` class (legacy, synchronized). Java docs khuyến nghị dùng `ArrayDeque` cho cả Stack và Queue.

## Câu 4

[TYPE: MULTIPLE_CHOICE]

Monotonic Stack (ngăn xếp đơn điệu) dùng để giải quyết bài toán nào?

- [ ] Tìm phần tử lớn nhất trong mảng
- [x] Tìm next greater element cho mỗi phần tử
- [ ] Sắp xếp mảng
- [ ] Tìm phần tử trung vị

> **Giải thích:** Monotonic Stack giữ stack luôn tăng/giảm dần, giúp tìm next greater/smaller element trong O(n).

## Câu 5

[TYPE: SELECT_RESULT]

Cho biểu thức hậu tố (RPN): `["3", "4", "+", "2", "*"]`. Kết quả là gì?

- [ ] 10
- [x] 14
- [ ] 9
- [ ] 11

> **Giải thích:** 3 + 4 = 7, 7 * 2 = 14. Stack: push 3, push 4, pop 4 và 3 → push 7, push 2, pop 2 và 7 → push 14.

## Câu 6

[TYPE: TRUE_FALSE]

Mệnh đề: "Mọi thao tác trên Stack (push, pop, peek) đều có độ phức tạp O(1)."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Tất cả thao tác chính trên Stack đều O(1) vì chỉ thao tác ở đỉnh. Push/Pop trên ArrayDeque là O(1) amortized.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

Ứng dụng nào KHÔNG phù hợp với Stack?

- [ ] Kiểm tra ngoặc hợp lệ
- [ ] Undo/Redo
- [x] BFS (Breadth-First Search)
- [ ] DFS (Depth-First Search)

> **Giải thích:** BFS dùng Queue (FIFO), không dùng Stack. DFS dùng Stack hoặc đệ quy (implicit stack).

## Câu 8

[TYPE: SELECT_RESULT]

Cho chuỗi `"([{}])"`, kiểm tra ngoặc hợp lệ bằng Stack. Stack sau khi duyệt hết chuỗi chứa gì?

- [x] Rỗng (chuỗi hợp lệ)
- [ ] `[(`
- [ ] `[{`
- [ ] `)]}`

> **Giải thích:** Mỗi ngoặc mở push, gặp ngoặc đóng khớp thì pop. Cuối cùng stack rỗng → chuỗi hợp lệ.
