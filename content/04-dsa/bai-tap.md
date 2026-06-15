# Cấu Trúc Dữ Liệu và Giải Thuật - Bài Tập

## Bài 1: Two Sum
**Độ khó: Trung bình**

Cho một mảng số nguyên `nums` và một số nguyên `target`, tìm hai chỉ số sao cho tổng hai phần tử bằng `target`. Mỗi phần tử chỉ được dùng một lần.

**Đầu vào:** `nums = [2, 7, 11, 15]`, `target = 9`
**Đầu ra:** `[0, 1]` (vì nums[0] + nums[1] = 2 + 7 = 9)

---

## Bài 2: Kiểm Tra Dấu Ngoặc Hợp Lệ
**Độ khó: Trung bình**

Cho một chuỗi chỉ chứa các ký tự `(){}[]`, kiểm tra xem chuỗi có hợp lệ không.
- Mỗi ngoặc mở phải có ngoặc đóng tương ứng.
- Ngoặc phải đóng đúng thứ tự.

**Đầu vào:** `"({[]})"`  → **Đầu ra:** `true`
**Đầu vào:** `"([)]"` → **Đầu ra:** `false`

---

## Bài 3: Đảo Ngược Linked List
**Độ khó: Trung bình**

Đảo ngược một singly linked list.

**Đầu vào:** `1 → 2 → 3 → 4 → 5`
**Đầu ra:** `5 → 4 → 3 → 2 → 1`

Yêu cầu: Implement cả hai cách iterative và recursive.

---

## Bài 4: Tìm Đường Đi Ngắn Nhất (BFS)
**Độ khó: Trung bình - Khó**

Cho một mê cung dạng ma trận 2D (0 = đường đi, 1 = tường), tìm đường đi ngắn nhất từ góc trên trái (0,0) đến góc dưới phải (m-1, n-1).

**Đầu vào:**
```
[0, 0, 0, 0]
[1, 1, 0, 1]
[0, 0, 0, 0]
[0, 1, 1, 0]
```
**Đầu ra:** `7` (độ dài đường đi ngắn nhất)

---

## Bài 5: Bài Toán Ba Lô (Knapsack) - Quy Hoạch Động
**Độ khó: Khó**

Cho n vật phẩm, mỗi vật có trọng lượng `w[i]` và giá trị `v[i]`. Tìm tập hợp vật phẩm có tổng giá trị lớn nhất mà tổng trọng lượng không vượt quá `W`.

**Đầu vào:**
- Trọng lượng: `[2, 3, 4, 5]`
- Giá trị: `[3, 4, 5, 6]`
- Sức chứa W = 8

**Đầu ra:** Giá trị tối đa = `10` (chọn vật 2 và 4: 4 + 6 = 10, trọng lượng 3 + 5 = 8)

---

## Bài 6: Cây Nhị Phân - Tổ Tiên Chung Gần Nhất (LCA)
**Độ khó: Khó**

Cho cây nhị phân và hai node p, q, tìm tổ tiên chung gần nhất (Lowest Common Ancestor).

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

## Bài 7: Sắp Xếp Mảng Gần Như Đã Sắp (K-Sorted Array)
**Độ khó: Trung bình - Khó**

Cho một mảng mà mỗi phần tử cách vị trí đúng của nó tối đa k vị trí. Sắp xếp mảng hiệu quả.

**Đầu vào:** `arr = [6, 5, 3, 2, 8, 10, 9]`, `k = 3`
**Đầu ra:** `[2, 3, 5, 6, 8, 9, 10]`

**Gợi ý:** Sử dụng Min-Heap kích thước k+1.

---

## Bài 8: Longest Increasing Subsequence (LIS)
**Độ khó: Khó**

Tìm độ dài dãy con tăng dài nhất trong mảng.

**Đầu vào:** `[10, 9, 2, 5, 3, 7, 101, 18]`
**Đầu ra:** `4` (dãy con: [2, 3, 7, 101] hoặc [2, 5, 7, 101])

Yêu cầu: Implement cả DP O(n²) và Binary Search O(n log n).

---

## Bài 9: Đếm Số Đảo (Number of Islands)
**Độ khó: Trung bình - Khó**

Cho ma trận 2D gồm '1' (đất) và '0' (nước), đếm số đảo. Một đảo được tạo bởi các ô '1' kề nhau (ngang/dọc).

**Đầu vào:**
```
1 1 0 0 0
1 1 0 0 0
0 0 1 0 0
0 0 0 1 1
```
**Đầu ra:** `3`

---

## Bài 10: Thiết Kế LRU Cache
**Độ khó: Rất Khó**

Thiết kế cấu trúc dữ liệu Least Recently Used (LRU) Cache với:
1. `get(key)` - Trả về giá trị nếu tồn tại, -1 nếu không. Đánh dấu là vừa được dùng.
2. `put(key, value)` - Thêm hoặc cập nhật. Nếu đầy, loại bỏ phần tử ít được dùng nhất.
3. Cả hai thao tác phải O(1).

**Gợi ý:** Kết hợp HashMap và Doubly Linked List.
