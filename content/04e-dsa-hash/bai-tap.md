# Hash Table - Bài Tập

## Bài 1: Valid Anagram
**Độ khó: Dễ**

Kiểm tra hai chuỗi có phải anagram (cùng chữ cái, khác thứ tự) không.

**Đầu vào:** `s = "anagram"`, `t = "nagaram"` → **Đầu ra:** `true`
**Đầu vào:** `s = "rat"`, `t = "car"` → **Đầu ra:** `false`

---

## Bài 2: Group Anagrams
**Độ khó: Trung bình**

Nhóm các chuỗi anagram lại với nhau.

**Đầu vào:** `["eat","tea","tan","ate","nat","bat"]`
**Đầu ra:** `[["eat","tea","ate"],["tan","nat"],["bat"]]`

---

## Bài 3: Longest Consecutive Sequence
**Độ khó: Trung bình**

Tìm độ dài dãy số liên tiếp dài nhất trong mảng unsorted. Yêu cầu O(n).

**Đầu vào:** `nums = [100, 4, 200, 1, 3, 2]`
**Đầu ra:** `4` (dãy 1, 2, 3, 4)

---

## Bài 4: Top K Frequent Elements
**Độ khó: Trung bình**

Cho mảng `nums` và số `k`, tìm k phần tử xuất hiện nhiều nhất.

**Đầu vào:** `nums = [1,1,1,2,2,3]`, `k = 2`
**Đầu ra:** `[1, 2]`

**Gợi ý:** HashMap đếm tần suất + PriorityQueue hoặc Bucket Sort.

---

## Bài 5: Thiết Kế LRU Cache
**Độ khó: Khó**

Thiết kế Least Recently Used (LRU) Cache:
1. `get(key)` — trả về value nếu tồn tại, -1 nếu không. Đánh dấu vừa dùng.
2. `put(key, value)` — thêm/cập nhật. Nếu đầy, loại bỏ ít dùng nhất.
3. Cả hai thao tác O(1).

**Gợi ý:** HashMap + Doubly Linked List.

---

## Bài 6: Subarray Sum Equals K
**Độ khó: Trung bình**

Đếm số lượng subarray liên tiếp có tổng bằng k.

**Đầu vào:** `nums = [1, 1, 1]`, `k = 2`
**Đầu ra:** `2`

**Gợi ý:** Prefix Sum + HashMap.
