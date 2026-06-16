# Array - Bài Tập

## Bài 1: Two Sum
**Độ khó: Dễ**

Cho một mảng số nguyên `nums` và một số nguyên `target`, tìm hai chỉ số sao cho tổng hai phần tử bằng `target`. Mỗi phần tử chỉ được dùng một lần.

**Đầu vào:** `nums = [2, 7, 11, 15]`, `target = 9`
**Đầu ra:** `[0, 1]` (vì nums[0] + nums[1] = 2 + 7 = 9)

**Đầu vào:** `nums = [3, 2, 4]`, `target = 6`
**Đầu ra:** `[1, 2]`

---

## Bài 2: Maximum Subarray (Kadane)
**Độ khó: Trung bình**

Tìm subarray liên tiếp có tổng lớn nhất.

**Đầu vào:** `nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]`
**Đầu ra:** `6` (subarray [4, -1, 2, 1])

**Đầu vào:** `nums = [1]`
**Đầu ra:** `1`

---

## Bài 3: Merge Intervals
**Độ khó: Trung bình**

Cho danh sách các khoảng (intervals), gộp tất cả các khoảng chồng lấp.

**Đầu vào:** `intervals = [[1,3],[2,6],[8,10],[15,18]]`
**Đầu ra:** `[[1,6],[8,10],[15,18]]`

---

## Bài 4: Product of Array Except Self
**Độ khó: Trung bình**

Cho mảng `nums`, trả về mảng `output` sao cho `output[i]` bằng tích tất cả phần tử trừ `nums[i]`. **Không dùng phép chia**, time O(n).

**Đầu vào:** `nums = [1, 2, 3, 4]`
**Đầu ra:** `[24, 12, 8, 6]`

---

## Bài 5: Sliding Window Maximum
**Độ khó: Khó**

Cho mảng `nums` và số `k`, tìm giá trị lớn nhất trong mỗi cửa sổ trượt kích thước k.

**Đầu vào:** `nums = [1,3,-1,-3,5,3,6,7]`, `k = 3`
**Đầu ra:** `[3,3,5,5,6,7]`

**Giải thích:**
```
Cửa sổ [1,3,-1]   → max = 3
Cửa sổ [3,-1,-3]  → max = 3
Cửa sổ [-1,-3,5]  → max = 5
Cửa sổ [-3,5,3]   → max = 5
Cửa sổ [5,3,6]    → max = 6
Cửa sổ [3,6,7]    → max = 7
```

**Gợi ý:** Dùng Deque (Monotonic Queue) để đạt O(n).

---

## Bài 6: Trapping Rain Water
**Độ khó: Khó**

Cho mảng `height` biểu diễn chiều cao các cột, tính lượng nước mưa có thể chứa.

**Đầu vào:** `height = [0,1,0,2,1,0,1,3,2,1,2,1]`
**Đầu ra:** `6`

```
       █
   █   ██ █
 █ ██ ████ █
```

**Gợi ý:** Two Pointers hoặc Prefix Max arrays.

---

## Bài 7: Xoay Mảng (Rotate Array)
**Độ khó: Trung bình**

Xoay mảng sang phải k bước. Yêu cầu: O(1) extra space.

**Đầu vào:** `nums = [1,2,3,4,5,6,7]`, `k = 3`
**Đầu ra:** `[5,6,7,1,2,3,4]`

**Gợi ý:** Dùng kỹ thuật đảo ngược 3 lần.

---

## Bài 8: Subarray Sum Equals K
**Độ khó: Trung bình**

Đếm số lượng subarray liên tiếp có tổng bằng k.

**Đầu vào:** `nums = [1, 1, 1]`, `k = 2`
**Đầu ra:** `2` (subarray [1,1] bắt đầu từ index 0 và 1)

**Đầu vào:** `nums = [1, 2, 3]`, `k = 3`
**Đầu ra:** `2` ([1,2] và [3])

**Gợi ý:** Dùng Prefix Sum + HashMap.
