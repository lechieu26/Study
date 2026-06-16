# Quiz - Linked List

## Câu 1

[TYPE: TRUE_FALSE]

Mệnh đề: "LinkedList có thời gian truy cập phần tử tại index bất kỳ là O(1)."

- [ ] Đúng
- [x] Sai

> **Giải thích:** LinkedList truy cập phần tử theo index là O(n) vì phải duyệt tuần tự. ArrayList mới có O(1) random access.

## Câu 2

[TYPE: MULTIPLE_CHOICE]

Ưu điểm chính của Linked List so với Array là gì?

- [ ] Truy cập ngẫu nhiên nhanh hơn
- [x] Thêm/xóa ở đầu là O(1)
- [ ] Ít tốn bộ nhớ hơn
- [ ] Cache-friendly hơn

> **Giải thích:** Linked List thêm/xóa ở đầu chỉ cần thay đổi pointer → O(1). Array phải dịch toàn bộ phần tử → O(n).

## Câu 3

[TYPE: SELECT_RESULT]

Cho linked list: `1 → 2 → 3 → 4 → 5`. Sau khi reverse, danh sách trở thành gì?

- [ ] `1 → 2 → 3 → 4 → 5`
- [ ] `2 → 1 → 4 → 3 → 5`
- [x] `5 → 4 → 3 → 2 → 1`
- [ ] `5 → 3 → 1 → 2 → 4`

> **Giải thích:** Reverse đảo ngược toàn bộ: node cuối thành head, mỗi pointer đổi hướng.

## Câu 4

[TYPE: MULTIPLE_CHOICE]

Thuật toán Floyd's Tortoise and Hare dùng để làm gì trên Linked List?

- [ ] Tìm phần tử lớn nhất
- [ ] Sắp xếp danh sách
- [x] Phát hiện cycle (vòng lặp)
- [ ] Đảo ngược danh sách

> **Giải thích:** Floyd's algorithm dùng 2 con trỏ: slow (1 bước) và fast (2 bước). Nếu có cycle, chúng sẽ gặp nhau. Time O(n), Space O(1).

## Câu 5

[TYPE: SELECT_RESULT]

Dùng slow/fast pointers, khi fast đến cuối list `1 → 2 → 3 → 4 → 5`, slow đang ở node nào?

- [ ] Node 1
- [ ] Node 2
- [x] Node 3
- [ ] Node 4

> **Giải thích:** slow đi 1 bước, fast đi 2 bước. Khi fast ở node 5 (cuối), slow ở node 3 (giữa).

## Câu 6

[TYPE: FILL_BLANK]

Kỹ thuật tạo node giả trước head để đơn giản hóa xử lý edge cases trong Linked List gọi là `___`.

- [x] Dummy Node (Sentinel)
- [ ] Anchor Node
- [ ] Ghost Node
- [ ] Buffer Node

> **Giải thích:** Dummy node (hay sentinel node) đặt trước head thực sự, giúp tránh kiểm tra đặc biệt khi xóa/thêm ở đầu list.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

Doubly Linked List so với Singly Linked List có ưu điểm gì?

- [ ] Truy cập nhanh hơn
- [x] Xóa node biết trước pointer là O(1)
- [ ] Ít tốn bộ nhớ hơn
- [ ] Thêm vào đầu nhanh hơn

> **Giải thích:** Doubly Linked List có pointer `prev`, nên khi biết node cần xóa, có thể truy cập node trước → O(1). Singly phải duyệt từ đầu tìm node trước → O(n).

## Câu 8

[TYPE: TRUE_FALSE]

Mệnh đề: "Trong Java, nên dùng LinkedList thay ArrayDeque để implement Stack và Queue."

- [ ] Đúng
- [x] Sai

> **Giải thích:** ArrayDeque nhanh hơn LinkedList cho Stack/Queue vì: (1) cache-friendly (mảng liên tiếp), (2) ít overhead bộ nhớ (không cần pointer), (3) Java docs khuyến nghị ArrayDeque.

## Câu 9

[TYPE: SELECT_RESULT]

Để xóa node thứ n từ cuối linked list trong **một lần duyệt**, cần dùng kỹ thuật gì?

- [ ] Duyệt ngược
- [x] Two Pointers cách nhau n bước
- [ ] Stack
- [ ] Đệ quy

> **Giải thích:** Fast pointer đi trước n+1 bước, sau đó cả fast và slow đi cùng tốc độ. Khi fast đến null, slow ở ngay trước node cần xóa.

## Câu 10

[TYPE: MULTIPLE_CHOICE]

Để kiểm tra Palindrome Linked List với O(1) space, các bước cần thực hiện là gì?

- [ ] Copy vào array rồi kiểm tra
- [ ] Dùng Stack
- [x] Tìm middle → Reverse nửa sau → So sánh hai nửa
- [ ] Dùng đệ quy

> **Giải thích:** (1) Slow/fast tìm middle, (2) Reverse nửa sau, (3) So sánh từ đầu với nửa reversed. Time O(n), Space O(1).
