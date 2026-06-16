# Quiz - Array (Mảng)

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Độ phức tạp thời gian truy cập phần tử tại index bất kỳ trong mảng tĩnh là gì?

- [x] O(1)
- [ ] O(n)
- [ ] O(log n)
- [ ] O(n²)

> **Giải thích:** Array lưu trữ liên tiếp trong bộ nhớ, truy cập bằng công thức: address = base + index × elementSize → O(1).

## Câu 2

[TYPE: SELECT_RESULT]

Cho mảng `arr = [3, 1, 4, 1, 5, 9]`. Kết quả của `arr[2] + arr[4]` là gì?

- [ ] 5
- [x] 9
- [ ] 6
- [ ] 14

> **Giải thích:** arr[2] = 4, arr[4] = 5. Tổng = 4 + 5 = 9.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

ArrayList trong Java khi đầy sẽ tăng kích thước bao nhiêu lần?

- [ ] 2x (gấp đôi)
- [x] ~1.5x
- [ ] 3x
- [ ] +10 phần tử

> **Giải thích:** Java ArrayList tăng kích thước khoảng 1.5 lần (newCapacity = oldCapacity + (oldCapacity >> 1)). C++ vector thường dùng 2x.

## Câu 4

[TYPE: TRUE_FALSE]

Mệnh đề: "Thêm phần tử vào cuối ArrayList luôn là O(1)."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Thêm cuối ArrayList là O(1) amortized. Khi cần resize (mảng đầy), phải copy toàn bộ → worst case O(n). Nhưng trung bình vẫn là O(1).

## Câu 5

[TYPE: SELECT_RESULT]

Cho đoạn code Kadane's Algorithm:
```java
int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
int maxSum = nums[0], currentSum = nums[0];
for (int i = 1; i < nums.length; i++) {
    currentSum = Math.max(nums[i], currentSum + nums[i]);
    maxSum = Math.max(maxSum, currentSum);
}
```
Giá trị `maxSum` cuối cùng là gì?

- [ ] 4
- [ ] 5
- [x] 6
- [ ] 7

> **Giải thích:** Subarray có tổng lớn nhất là [4, -1, 2, 1] = 6. Kadane's Algorithm tìm được kết quả này trong O(n).

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Kỹ thuật nào phù hợp nhất để tìm 2 số có tổng bằng target trong mảng **đã sắp xếp**?

- [ ] Brute Force O(n²)
- [ ] HashMap O(n)
- [x] Two Pointers O(n)
- [ ] Binary Search cho mỗi phần tử O(n log n)

> **Giải thích:** Với mảng đã sorted, Two Pointers (left + right) là tối ưu: O(n) time, O(1) space. HashMap cũng O(n) nhưng cần O(n) space.

## Câu 7

[TYPE: FILL_BLANK]

Kỹ thuật dùng mảng tiền xử lý để trả lời nhanh truy vấn tổng đoạn [l, r] được gọi là `___`.

- [x] Prefix Sum
- [ ] Sliding Window
- [ ] Two Pointers
- [ ] Kadane

> **Giải thích:** Prefix Sum xây dựng mảng tổng tích lũy, cho phép tính tổng bất kỳ đoạn [l, r] trong O(1) sau O(n) tiền xử lý.

## Câu 8

[TYPE: SELECT_RESULT]

Cho mảng `prefix = [0, 2, 6, 7, 10, 15]` (prefix sum của arr = [2, 4, 1, 3, 5]). Tổng đoạn arr[1..3] (index 1 đến 3) là bao nhiêu?

- [ ] 6
- [x] 8
- [ ] 10
- [ ] 7

> **Giải thích:** Tổng [1, 3] = prefix[4] - prefix[1] = 10 - 2 = 8 (tức 4 + 1 + 3 = 8).

## Câu 9

[TYPE: MULTIPLE_CHOICE]

Để xoay mảng sang phải k bước với O(1) extra space, kỹ thuật nào hiệu quả nhất?

- [ ] Dùng mảng tạm copy
- [x] Reverse 3 lần (toàn bộ, k đầu, phần còn lại)
- [ ] Dịch từng phần tử k lần
- [ ] Dùng Queue

> **Giải thích:** Reverse 3 lần: (1) reverse toàn bộ, (2) reverse k phần tử đầu, (3) reverse phần còn lại → O(n) time, O(1) space.

## Câu 10

[TYPE: TRUE_FALSE]

Mệnh đề: "Sliding Window chỉ áp dụng được khi cửa sổ có kích thước cố định."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Sliding Window có 2 loại: (1) Fixed-size window (kích thước k cố định) và (2) Variable-size window (thu/mở rộng cửa sổ theo điều kiện). Ví dụ: Minimum Size Subarray Sum dùng variable-size window.
