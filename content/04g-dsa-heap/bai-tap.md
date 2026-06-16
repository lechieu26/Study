# Heap - Bài Tập

## Bài 1: Kth Largest Element in Array
**Độ khó: Trung bình**

Tìm phần tử lớn thứ k trong mảng (không cần sorted).

**Đầu vào:** `nums = [3,2,1,5,6,4]`, `k = 2`
**Đầu ra:** `5`

---

## Bài 2: Top K Frequent Elements
**Độ khó: Trung bình**

Tìm k phần tử xuất hiện nhiều nhất.

**Đầu vào:** `nums = [1,1,1,2,2,3]`, `k = 2`
**Đầu ra:** `[1, 2]`

---

## Bài 3: Merge K Sorted Lists
**Độ khó: Khó**

Gộp k linked list đã sắp xếp thành 1 list sorted.

**Đầu vào:** `lists = [[1,4,5],[1,3,4],[2,6]]`
**Đầu ra:** `[1,1,2,3,4,4,5,6]`

---

## Bài 4: Find Median from Data Stream
**Độ khó: Khó**

Thiết kế cấu trúc dữ liệu hỗ trợ:
- `addNum(int num)` — thêm số
- `findMedian()` — trả về median hiện tại

**Ví dụ:**
```
addNum(1), addNum(2) → median = 1.5
addNum(3) → median = 2.0
```

**Gợi ý:** Dùng 2 heap (max-heap cho nửa nhỏ, min-heap cho nửa lớn).

---

## Bài 5: Sắp Xếp Mảng K-Sorted
**Độ khó: Trung bình**

Cho mảng mà mỗi phần tử cách vị trí đúng tối đa k. Sắp xếp hiệu quả.

**Đầu vào:** `arr = [6, 5, 3, 2, 8, 10, 9]`, `k = 3`
**Đầu ra:** `[2, 3, 5, 6, 8, 9, 10]`

---

## Bài 6: Last Stone Weight
**Độ khó: Dễ**

Mỗi lượt chọn 2 viên đá nặng nhất. Nếu khác nhau, viên lớn hơn giảm đi. Tìm trọng lượng viên đá cuối cùng.

**Đầu vào:** `stones = [2,7,4,1,8,1]`
**Đầu ra:** `1`
