# Linked List - Bài Tập

## Bài 1: Đảo Ngược Linked List
**Độ khó: Dễ**

Đảo ngược một singly linked list.

**Đầu vào:** `1 → 2 → 3 → 4 → 5`
**Đầu ra:** `5 → 4 → 3 → 2 → 1`

Yêu cầu: Implement cả hai cách iterative và recursive.

---

## Bài 2: Phát Hiện Cycle
**Độ khó: Dễ**

Kiểm tra xem linked list có chứa vòng lặp (cycle) hay không.

**Đầu vào:** `3 → 2 → 0 → -4 → (trỏ về 2)`
**Đầu ra:** `true`

**Gợi ý:** Floyd's Tortoise and Hare algorithm.

---

## Bài 3: Merge Two Sorted Lists
**Độ khó: Dễ**

Gộp hai linked list đã sắp xếp thành một linked list mới cũng đã sắp xếp.

**Đầu vào:** `l1 = 1 → 2 → 4`, `l2 = 1 → 3 → 4`
**Đầu ra:** `1 → 1 → 2 → 3 → 4 → 4`

---

## Bài 4: Xóa Node Cách Cuối N Bước
**Độ khó: Trung bình**

Xóa node thứ n từ cuối linked list. Yêu cầu: duyệt chỉ 1 lần (one pass).

**Đầu vào:** `1 → 2 → 3 → 4 → 5`, `n = 2`
**Đầu ra:** `1 → 2 → 3 → 5`

---

## Bài 5: Palindrome Linked List
**Độ khó: Trung bình**

Kiểm tra linked list có phải palindrome (đọc xuôi ngược giống nhau) không. Yêu cầu O(n) time, O(1) space.

**Đầu vào:** `1 → 2 → 2 → 1`
**Đầu ra:** `true`

**Đầu vào:** `1 → 2 → 3`
**Đầu ra:** `false`

**Gợi ý:** Tìm middle, reverse nửa sau, so sánh hai nửa.

---

## Bài 6: Reorder List
**Độ khó: Trung bình**

Cho linked list L₀ → L₁ → ... → Lₙ₋₁ → Lₙ, sắp xếp lại thành L₀ → Lₙ → L₁ → Lₙ₋₁ → L₂ → Lₙ₋₂ → ...

**Đầu vào:** `1 → 2 → 3 → 4 → 5`
**Đầu ra:** `1 → 5 → 2 → 4 → 3`

**Gợi ý:** (1) Tìm middle, (2) Reverse nửa sau, (3) Merge xen kẽ.

---

## Bài 7: Copy List with Random Pointer
**Độ khó: Trung bình**

Cho linked list với mỗi node có thêm pointer `random` trỏ đến node bất kỳ hoặc null. Tạo deep copy của list.

```
Node 1 → Node 2 → Node 3
  ↓random   ↓random   ↓random
Node 3    Node 1     null
```

**Gợi ý:** HashMap hoặc kỹ thuật interleave.

---

## Bài 8: Reverse Nodes in k-Group
**Độ khó: Khó**

Đảo ngược linked list theo từng nhóm k node. Nếu số node còn lại ít hơn k, giữ nguyên.

**Đầu vào:** `1 → 2 → 3 → 4 → 5`, `k = 2`
**Đầu ra:** `2 → 1 → 4 → 3 → 5`

**Đầu vào:** `1 → 2 → 3 → 4 → 5`, `k = 3`
**Đầu ra:** `3 → 2 → 1 → 4 → 5`
