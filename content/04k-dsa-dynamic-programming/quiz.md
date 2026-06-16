# Quiz - Dynamic Programming

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Hai điều kiện cần để áp dụng Dynamic Programming là gì?

- [x] Optimal Substructure + Overlapping Subproblems
- [ ] Sorted input + Monotonic function
- [ ] Greedy choice + Optimal Substructure
- [ ] Divide and Conquer + Merge

> **Giải thích:** DP yêu cầu: (1) Optimal Substructure — lời giải tối ưu chứa lời giải tối ưu bài toán con, (2) Overlapping Subproblems — cùng bài toán con tính nhiều lần.

## Câu 2

[TYPE: MULTIPLE_CHOICE]

Top-Down DP khác Bottom-Up DP ở điểm nào?

- [x] Top-Down dùng đệ quy + memoization, Bottom-Up dùng vòng lặp + tabulation
- [ ] Top-Down nhanh hơn
- [ ] Bottom-Up dùng đệ quy
- [ ] Không có sự khác biệt

> **Giải thích:** Top-Down: đệ quy từ bài toán lớn xuống nhỏ, cache kết quả. Bottom-Up: xây bảng từ base case lên.

## Câu 3

[TYPE: SELECT_RESULT]

Climbing Stairs n=5 có bao nhiêu cách? (mỗi bước 1 hoặc 2 bậc)

- [ ] 5
- [ ] 7
- [x] 8
- [ ] 13

> **Giải thích:** dp = [1, 1, 2, 3, 5, 8]. dp[5] = dp[4] + dp[3] = 5 + 3 = 8. Fibonacci pattern.

## Câu 4

[TYPE: SELECT_RESULT]

Coin Change: coins = [1, 5, 11], amount = 15. Số xu ít nhất?

- [ ] 1
- [x] 3
- [ ] 5
- [ ] 15

> **Giải thích:** 15 = 5 + 5 + 5 = 3 xu. Greedy chọn 11 → 11 + 1 + 1 + 1 + 1 = 5 xu (sai). DP cho đáp án tối ưu.

## Câu 5

[TYPE: TRUE_FALSE]

Mệnh đề: "Bài toán Coin Change có thể giải bằng Greedy (luôn chọn xu lớn nhất)."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Greedy không luôn cho kết quả tối ưu. Ví dụ: coins=[1,5,11], amount=15. Greedy: 11+1+1+1+1=5 xu. DP: 5+5+5=3 xu.

## Câu 6

[TYPE: FILL_BLANK]

Công thức recurrence cho House Robber: `dp[i] = max(dp[i-1], dp[i-2] + ___)`.

- [x] nums[i]
- [ ] dp[i-3]
- [ ] nums[i-1]
- [ ] 0

> **Giải thích:** dp[i] = max(không cướp nhà i: dp[i-1], cướp nhà i: dp[i-2] + nums[i]).

## Câu 7

[TYPE: MULTIPLE_CHOICE]

LIS (Longest Increasing Subsequence) có thể giải trong O(n log n) bằng kỹ thuật nào?

- [ ] Merge Sort
- [x] Patience Sorting (Binary Search trên mảng tails)
- [ ] Two Pointers
- [ ] BFS

> **Giải thích:** Duy trì mảng tails: tails[i] = giá trị nhỏ nhất kết thúc LIS độ dài i+1. Binary search vị trí insert → O(n log n).

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Edit Distance giữa "kitten" và "sitting" là bao nhiêu?

- [ ] 2
- [x] 3
- [ ] 4
- [ ] 5

> **Giải thích:** kitten → sitten (replace k→s) → sittin (replace e→i) → sitting (insert g) = 3 thao tác.
