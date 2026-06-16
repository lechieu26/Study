# Quiz - Heap & Priority Queue

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Min-Heap đảm bảo điều gì?

- [x] Parent ≤ Children tại mọi node
- [ ] Parent ≥ Children tại mọi node
- [ ] Left child < Right child
- [ ] Cây luôn cân bằng hoàn hảo

> **Giải thích:** Min-Heap: parent ≤ children → root là giá trị nhỏ nhất. Max-Heap thì ngược lại.

## Câu 2

[TYPE: SELECT_RESULT]

Cho Min-Heap: offer(5), offer(3), offer(7), offer(1). Phần tử peek() là gì?

- [x] 1
- [ ] 3
- [ ] 5
- [ ] 7

> **Giải thích:** Min-Heap: root luôn là nhỏ nhất. Sau khi thêm 5, 3, 7, 1 → root = 1.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Độ phức tạp của thao tác `offer()` trên Heap là gì?

- [ ] O(1)
- [x] O(log n)
- [ ] O(n)
- [ ] O(n log n)

> **Giải thích:** offer() thêm ở cuối rồi sift up → tối đa log n lần swap (chiều cao cây).

## Câu 4

[TYPE: TRUE_FALSE]

Mệnh đề: "Build heap từ mảng n phần tử có độ phức tạp O(n log n)."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Heapify (build heap) bottom-up chỉ cần O(n), không phải O(n log n). Chứng minh bằng tổng geometric series.

## Câu 5

[TYPE: MULTIPLE_CHOICE]

Để tìm phần tử lớn thứ k trong mảng, dùng heap nào hiệu quả nhất?

- [x] Min-Heap kích thước k
- [ ] Max-Heap kích thước k
- [ ] Min-Heap kích thước n
- [ ] Max-Heap kích thước n

> **Giải thích:** Min-Heap kích thước k: root = phần tử nhỏ nhất trong k phần tử lớn nhất = kth largest. O(n log k).

## Câu 6

[TYPE: MULTIPLE_CHOICE]

PriorityQueue trong Java mặc định là gì?

- [x] Min-Heap
- [ ] Max-Heap
- [ ] FIFO Queue
- [ ] Sorted Array

> **Giải thích:** Java PriorityQueue mặc định là min-heap. Dùng `Collections.reverseOrder()` hoặc custom comparator cho max-heap.

## Câu 7

[TYPE: SELECT_RESULT]

Heap Sort có độ phức tạp thời gian và bộ nhớ bao nhiêu?

- [ ] O(n log n) time, O(n) space
- [x] O(n log n) time, O(1) space
- [ ] O(n²) time, O(1) space
- [ ] O(n) time, O(n) space

> **Giải thích:** Heap Sort: build heap O(n) + extract n lần O(log n) = O(n log n). In-place → O(1) extra space.

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Bài toán Find Median from Data Stream dùng cấu trúc gì?

- [ ] Một Min-Heap
- [ ] Một Max-Heap
- [x] Hai heap: Max-Heap (nửa nhỏ) + Min-Heap (nửa lớn)
- [ ] Sorted Array

> **Giải thích:** Max-heap chứa nửa nhỏ, min-heap chứa nửa lớn. Median = top max-heap hoặc trung bình 2 tops. addNum O(log n), findMedian O(1).
