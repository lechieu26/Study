# Dynamic Programming - Bài Tập

## Bài 1: Climbing Stairs
**Độ khó: Dễ**

Có n bậc thang. Mỗi lần bước 1 hoặc 2 bậc. Đếm số cách leo lên đỉnh.

**Đầu vào:** `n = 3`
**Đầu ra:** `3` (1+1+1, 1+2, 2+1)

---

## Bài 2: House Robber
**Độ khó: Trung bình**

Mỗi nhà có tiền. Không được cướp 2 nhà liên tiếp. Tìm tổng tiền max.

**Đầu vào:** `nums = [2, 7, 9, 3, 1]`
**Đầu ra:** `12` (2 + 9 + 1)

---

## Bài 3: Coin Change
**Độ khó: Trung bình**

Cho mảng mệnh giá xu, tìm số xu ít nhất để tạo amount. Mỗi xu dùng nhiều lần.

**Đầu vào:** `coins = [1, 5, 11]`, `amount = 15`
**Đầu ra:** `3` (5 + 5 + 5)

---

## Bài 4: Longest Increasing Subsequence
**Độ khó: Trung bình**

Tìm dãy con tăng dài nhất (LIS).

**Đầu vào:** `nums = [10, 9, 2, 5, 3, 7, 101, 18]`
**Đầu ra:** `4` (2, 3, 7, 101)

---

## Bài 5: 0/1 Knapsack
**Độ khó: Trung bình**

Có n items (weight, value) và túi sức chứa W. Chọn items tối đa giá trị, không vượt sức chứa. Mỗi item chọn tối đa 1 lần.

**Đầu vào:** `weights = [2, 3, 4, 5]`, `values = [3, 4, 5, 6]`, `W = 8`
**Đầu ra:** `10` (items 0 + 2: weight 2+4=6, value 3+5+... ?)

---

## Bài 6: Longest Common Subsequence
**Độ khó: Trung bình**

Tìm dãy con chung dài nhất của 2 chuỗi.

**Đầu vào:** `text1 = "abcde"`, `text2 = "ace"`
**Đầu ra:** `3` ("ace")

---

## Bài 7: Edit Distance
**Độ khó: Khó**

Tìm số thao tác tối thiểu (insert, delete, replace) để biến word1 → word2.

**Đầu vào:** `word1 = "intention"`, `word2 = "execution"`
**Đầu ra:** `5`

---

## Bài 8: Word Break
**Độ khó: Trung bình**

Kiểm tra chuỗi s có thể tách thành các từ trong dictionary không.

**Đầu vào:** `s = "leetcode"`, `wordDict = ["leet", "code"]`
**Đầu ra:** `true`

**Đầu vào:** `s = "catsandog"`, `wordDict = ["cats", "dog", "sand", "and", "cat"]`
**Đầu ra:** `false`
