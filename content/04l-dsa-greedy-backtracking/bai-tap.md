# Greedy & Backtracking - Bài Tập

## Bài 1: Jump Game (Greedy)
**Độ khó: Trung bình**

Cho mảng `nums[i]` = số bước nhảy tối đa từ vị trí i. Kiểm tra có thể đến cuối mảng không.

**Đầu vào:** `nums = [2,3,1,1,4]` → **Đầu ra:** `true`
**Đầu vào:** `nums = [3,2,1,0,4]` → **Đầu ra:** `false`

---

## Bài 2: Subsets (Backtracking)
**Độ khó: Trung bình**

Liệt kê tất cả tập con của mảng số nguyên phân biệt.

**Đầu vào:** `nums = [1, 2, 3]`
**Đầu ra:** `[[], [1], [1,2], [1,2,3], [1,3], [2], [2,3], [3]]`

---

## Bài 3: Permutations (Backtracking)
**Độ khó: Trung bình**

Liệt kê tất cả hoán vị của mảng số nguyên phân biệt.

**Đầu vào:** `nums = [1, 2, 3]`
**Đầu ra:** `[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]`

---

## Bài 4: Combination Sum (Backtracking)
**Độ khó: Trung bình**

Tìm tất cả tổ hợp trong `candidates` có tổng bằng `target`. Mỗi số dùng nhiều lần.

**Đầu vào:** `candidates = [2, 3, 6, 7]`, `target = 7`
**Đầu ra:** `[[2,2,3], [7]]`

---

## Bài 5: Non-overlapping Intervals (Greedy)
**Độ khó: Trung bình**

Tìm số lượng intervals tối thiểu cần xóa để không còn overlap.

**Đầu vào:** `[[1,2],[2,3],[3,4],[1,3]]`
**Đầu ra:** `1` (xóa [1,3])

---

## Bài 6: Word Search (Backtracking)
**Độ khó: Trung bình**

Cho ma trận 2D chữ cái, tìm xem có thể tạo từ `word` bằng cách đi liền kề (ngang/dọc) không. Mỗi ô dùng 1 lần.

**Đầu vào:**
```
board = [["A","B","C","E"],
         ["S","F","C","S"],
         ["A","D","E","E"]]
word = "ABCCED"
```
**Đầu ra:** `true`

---

## Bài 7: N-Queens (Backtracking)
**Độ khó: Khó**

Đặt n quân hậu trên bàn cờ n×n sao cho không quân nào tấn công nhau. Tìm tất cả cách đặt.

**Đầu vào:** `n = 4`
**Đầu ra:** 2 cách đặt

---

## Bài 8: Gas Station (Greedy)
**Độ khó: Trung bình**

Có n trạm xăng trên đường vòng. Trạm i có gas[i] xăng, đi đến trạm tiếp theo tốn cost[i]. Tìm trạm xuất phát đi hết vòng.

**Đầu vào:** `gas = [1,2,3,4,5]`, `cost = [3,4,5,1,2]`
**Đầu ra:** `3`
