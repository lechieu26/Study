# Quiz - Searching

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Độ phức tạp của Binary Search trên mảng sorted là gì?

- [ ] O(n)
- [x] O(log n)
- [ ] O(n²)
- [ ] O(1)

> **Giải thích:** Binary Search chia đôi không gian tìm kiếm mỗi bước → O(log n). Yêu cầu mảng sorted.

## Câu 2

[TYPE: SELECT_RESULT]

Cho mảng sorted `[2, 5, 8, 12, 16, 23, 38]`. Binary search target = 23 cần bao nhiêu bước so sánh?

- [ ] 1
- [x] 2
- [ ] 3
- [ ] 4

> **Giải thích:** Bước 1: mid=12 (12<23 → right half). Bước 2: mid=23 (found!).

## Câu 3

[TYPE: TRUE_FALSE]

Mệnh đề: "Binary Search chỉ áp dụng được cho mảng sorted."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Binary Search áp dụng được cho bất kỳ bài toán có tính monotonic. Ví dụ: Binary Search on Answer, Search in Rotated Array.

## Câu 4

[TYPE: MULTIPLE_CHOICE]

Tại sao dùng `mid = left + (right - left) / 2` thay vì `mid = (left + right) / 2`?

- [ ] Nhanh hơn
- [x] Tránh integer overflow khi left + right lớn
- [ ] Kết quả chính xác hơn
- [ ] Không có sự khác biệt

> **Giải thích:** `left + right` có thể vượt quá Integer.MAX_VALUE. `left + (right - left) / 2` tránh overflow.

## Câu 5

[TYPE: FILL_BLANK]

Kỹ thuật dùng Binary Search để tìm giá trị nhỏ nhất/lớn nhất thỏa điều kiện gọi là `___`.

- [x] Binary Search on Answer
- [ ] Lower Bound
- [ ] Two Pointers
- [ ] Parametric Search

> **Giải thích:** Binary Search on Answer: nếu f(x) đúng thì f(x+1) cũng đúng (monotonic) → binary search trên không gian đáp án.

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Để tìm trong mảng sorted bị xoay (rotated), dùng kỹ thuật gì?

- [ ] Linear Search
- [x] Modified Binary Search (xác định nửa nào sorted)
- [ ] Two Pointers
- [ ] Hashing

> **Giải thích:** Xác định nửa nào vẫn sorted, kiểm tra target nằm trong nửa đó không → chọn nửa tiếp. O(log n).

## Câu 7

[TYPE: SELECT_RESULT]

`Arrays.binarySearch(new int[]{1,3,5,7,9}, 4)` trả về gì?

- [ ] -1
- [ ] 2
- [x] -3
- [ ] -2

> **Giải thích:** Không tìm thấy → trả về -(insertion point) - 1. 4 sẽ chèn tại index 2 → return -(2)-1 = -3.

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Lower Bound tìm gì?

- [x] Vị trí đầu tiên ≥ target
- [ ] Vị trí đầu tiên > target
- [ ] Vị trí cuối cùng ≤ target
- [ ] Vị trí chính xác của target

> **Giải thích:** Lower bound: vị trí đầu tiên mà arr[i] >= target. Upper bound: vị trí đầu tiên mà arr[i] > target.
