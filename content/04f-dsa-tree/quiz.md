# Quiz - Tree

## Câu 1

[TYPE: SELECT_RESULT]

Cho BST sau, duyệt In-order (LNR) cho kết quả gì?

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

> **Giải thích:** In-order (LNR): Left → Node → Right. Kết quả luôn tăng dần trên BST.

## Câu 2

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

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Chiều cao (height) của cây nhị phân có n node trong worst case là bao nhiêu?

- [ ] O(1)
- [ ] O(log n)
- [x] O(n)
- [ ] O(n²)

> **Giải thích:** Worst case khi cây suy biến (skewed) — mỗi node chỉ có 1 con → chiều cao = n. Balanced tree có chiều cao O(log n).

## Câu 4

[TYPE: MULTIPLE_CHOICE]

AVL Tree đảm bảo điều gì tại mỗi node?

- [ ] Left subtree luôn nhỏ hơn right subtree
- [x] Chênh lệch chiều cao left/right ≤ 1
- [ ] Mỗi node có đúng 2 con
- [ ] Chiều cao luôn = log n

> **Giải thích:** AVL Tree là BST tự cân bằng: |height(left) - height(right)| ≤ 1 tại mọi node. Dùng rotation để duy trì.

## Câu 5

[TYPE: FILL_BLANK]

Duyệt cây theo thứ tự Left → Node → Right gọi là `___` traversal.

- [x] Inorder
- [ ] Preorder
- [ ] Postorder
- [ ] Level-order

> **Giải thích:** Inorder (LNR): Left → Node → Right. Trên BST, cho kết quả sorted.

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Để duyệt cây theo từng tầng (level-order), dùng cấu trúc dữ liệu nào?

- [ ] Stack
- [x] Queue
- [ ] HashMap
- [ ] PriorityQueue

> **Giải thích:** Level-order = BFS trên cây. Dùng Queue để duyệt từng tầng từ trái sang phải.

## Câu 7

[TYPE: TRUE_FALSE]

Mệnh đề: "Inorder traversal trên BST luôn cho kết quả tăng dần."

- [x] Đúng
- [ ] Sai

> **Giải thích:** BST: left < node < right. Inorder (L→N→R) duyệt left trước → node → right → kết quả tăng dần.

## Câu 8

[TYPE: SELECT_RESULT]

Cho cây:
```
        3
       / \
      5   1
     / \
    6   2
```
LCA(6, 2) là node nào?

- [ ] 3
- [x] 5
- [ ] 6
- [ ] 2

> **Giải thích:** Node 6 và 2 đều là con của node 5 → LCA = 5. Node 5 là tổ tiên chung gần nhất.
