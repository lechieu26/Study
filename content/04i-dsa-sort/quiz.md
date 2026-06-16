# Quiz - Sorting

## Câu 1

[TYPE: SELECT_RESULT]

Cho mảng `arr = [3, 7, 1, 9, 4]`. Sau Bubble Sort 1 pass, phần tử lớn nhất ở vị trí nào?

- [ ] Đầu mảng (index 0)
- [x] Cuối mảng (index 4)
- [ ] Giữa mảng
- [ ] Không thay đổi

> **Giải thích:** Bubble Sort mỗi pass đẩy phần tử lớn nhất về cuối. Pass 1: [3,1,7,4,9].

## Câu 2

[TYPE: TRUE_FALSE]

Mệnh đề: "Quick Sort có worst case O(n²) khi pivot luôn là phần tử nhỏ/lớn nhất."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Worst case khi partition luôn chia 1 và n-1 → O(n²). Random pivot hoặc median-of-three giảm xác suất.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Merge Sort có đặc điểm gì?

- [x] Stable sort, O(n log n) mọi trường hợp, cần O(n) space
- [ ] In-place sort, O(n log n) average
- [ ] O(n²) worst case
- [ ] Chỉ cho số nguyên

> **Giải thích:** Merge Sort luôn O(n log n), stable, cần O(n) extra space cho merge.

## Câu 4

[TYPE: SELECT_RESULT]

`Arrays.sort()` trong Java dùng thuật toán gì cho int[]?

- [ ] Merge Sort
- [x] Dual-Pivot Quick Sort
- [ ] Heap Sort
- [ ] Radix Sort

> **Giải thích:** Java dùng Dual-Pivot QuickSort cho primitives, TimSort (hybrid Merge+Insertion) cho Objects.

## Câu 5

[TYPE: SELECT_RESULT]

Cho mảng `[5, 2, 8, 1, 9, 3]`, Selection Sort lần 1 (tìm min, swap) cho kết quả:

- [x] [1, 2, 8, 5, 9, 3]
- [ ] [1, 5, 2, 8, 9, 3]
- [ ] [2, 5, 8, 1, 9, 3]
- [ ] [1, 2, 3, 5, 8, 9]

> **Giải thích:** Tìm min = 1 (index 3), swap với arr[0] = 5 → [1, 2, 8, 5, 9, 3].

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Thuật toán sắp xếp nào có O(n log n) guaranteed VÀ O(1) extra space?

- [ ] Merge Sort
- [ ] Quick Sort
- [x] Heap Sort
- [ ] Counting Sort

> **Giải thích:** Heap Sort: O(n log n) mọi trường hợp, in-place O(1). Merge Sort cần O(n), Quick Sort worst O(n²).

## Câu 7

[TYPE: FILL_BLANK]

Thuật toán sắp xếp giữ nguyên thứ tự tương đối của phần tử bằng nhau gọi là `___` sort.

- [x] Stable
- [ ] In-place
- [ ] Adaptive
- [ ] External

> **Giải thích:** Stable sort: phần tử bằng nhau giữ thứ tự ban đầu. Merge Sort, Insertion Sort, TimSort là stable. Quick Sort, Heap Sort không stable.

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Khi nào dùng Counting Sort?

- [ ] Dữ liệu rất lớn, kiểu chuỗi
- [x] Số nguyên với range nhỏ (0..k), k không quá lớn
- [ ] Linked list
- [ ] Dữ liệu gần sorted

> **Giải thích:** Counting Sort: O(n+k) nhưng cần O(k) space. Hiệu quả khi k (range) nhỏ so với n.
