# Tree - Bài Tập

## Bài 1: Chiều Cao Cây Nhị Phân
**Độ khó: Dễ**

Tìm chiều cao (max depth) của cây nhị phân.

```
    3
   / \
  9  20
    /  \
   15   7
```
**Đầu ra:** `3`

---

## Bài 2: Invert Binary Tree
**Độ khó: Dễ**

Đảo ngược (mirror) cây nhị phân — hoán đổi left và right tại mỗi node.

```
     4              4
   /   \    →     /   \
  2     7        7     2
 / \   / \      / \   / \
1   3 6   9    9   6 3   1
```

---

## Bài 3: Validate BST
**Độ khó: Trung bình**

Kiểm tra cây nhị phân có phải BST hợp lệ không (left < node < right tại mọi node).

```
    5
   / \
  1   4       → false (4 < 5 nhưng 4 ở right subtree)
     / \        Thực ra 3, 6 hợp lệ nhưng 4 không
    3   6
```

---

## Bài 4: Tổ Tiên Chung Gần Nhất (LCA)
**Độ khó: Trung bình**

Cho cây nhị phân và hai node p, q, tìm Lowest Common Ancestor.

```
        3
       / \
      5   1
     / \ / \
    6  2 0  8
      / \
     7   4
```
**LCA(5, 1) = 3**, **LCA(5, 4) = 5**

---

## Bài 5: Binary Tree Level Order Traversal
**Độ khó: Trung bình**

Duyệt cây theo từng tầng, trả về danh sách các tầng.

```
    3
   / \
  9  20
    /  \
   15   7
```
**Đầu ra:** `[[3], [9, 20], [15, 7]]`

---

## Bài 6: Kth Smallest Element in BST
**Độ khó: Trung bình**

Tìm phần tử nhỏ thứ k trong BST.

```
        5
       / \
      3   6
     / \
    2   4
   /
  1
```
**k = 3** → **Đầu ra:** `3` (sorted: 1, 2, **3**, 4, 5, 6)

---

## Bài 7: Serialize and Deserialize Binary Tree
**Độ khó: Khó**

Thiết kế thuật toán chuyển cây nhị phân thành chuỗi (serialize) và từ chuỗi khôi phục lại cây (deserialize).

---

## Bài 8: Binary Tree Maximum Path Sum
**Độ khó: Khó**

Tìm tổng đường đi lớn nhất trong cây nhị phân. Đường đi có thể bắt đầu và kết thúc tại bất kỳ node nào.

```
   -10
   / \
  9  20
    /  \
   15   7
```
**Đầu ra:** `42` (đường đi: 15 → 20 → 7)
