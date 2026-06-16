# Quiz - Greedy & Backtracking

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Greedy algorithm hoạt động như thế nào?

- [x] Luôn chọn lựa chọn tốt nhất tại mỗi bước (locally optimal)
- [ ] Thử tất cả khả năng rồi chọn tốt nhất
- [ ] Dùng đệ quy với memoization
- [ ] Chia bài toán thành 2 nửa

> **Giải thích:** Greedy chọn locally optimal mỗi bước, hy vọng đạt globally optimal. Không quay lui.

## Câu 2

[TYPE: TRUE_FALSE]

Mệnh đề: "Greedy luôn cho lời giải tối ưu cho mọi bài toán."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Greedy chỉ đúng khi bài toán có Greedy Choice Property. Ví dụ: Coin Change với coins=[1,5,11] amount=15, greedy cho 5 xu thay vì 3 xu tối ưu.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Backtracking khác Brute Force ở điểm nào?

- [ ] Backtracking nhanh hơn vì dùng DP
- [x] Backtracking cắt tỉa (pruning) nhánh không hợp lệ sớm
- [ ] Không có sự khác biệt
- [ ] Backtracking chỉ dùng cho đồ thị

> **Giải thích:** Backtracking = thử tất cả + quay lui khi phát hiện sai (pruning). Hiệu quả hơn brute force nhờ cắt tỉa.

## Câu 4

[TYPE: SELECT_RESULT]

Cho `nums = [1, 2, 3]`, tổng số subsets là bao nhiêu?

- [ ] 6
- [x] 8
- [ ] 7
- [ ] 3

> **Giải thích:** n phần tử có 2^n subsets. 2³ = 8: [], [1], [2], [3], [1,2], [1,3], [2,3], [1,2,3].

## Câu 5

[TYPE: SELECT_RESULT]

Cho `nums = [1, 2, 3]`, tổng số permutations là bao nhiêu?

- [ ] 3
- [x] 6
- [ ] 8
- [ ] 9

> **Giải thích:** n! permutations. 3! = 6: [1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], [3,2,1].

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Template backtracking gồm 3 bước chính nào?

- [ ] Sort → Search → Return
- [x] Choose → Explore → Unchoose
- [ ] Divide → Conquer → Merge
- [ ] Push → Process → Pop

> **Giải thích:** Backtracking: (1) Choose — chọn 1 lựa chọn, (2) Explore — đệ quy, (3) Unchoose — quay lui bỏ lựa chọn.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

Activity Selection problem dùng greedy strategy nào?

- [ ] Chọn hoạt động bắt đầu sớm nhất
- [x] Chọn hoạt động kết thúc sớm nhất
- [ ] Chọn hoạt động ngắn nhất
- [ ] Chọn ngẫu nhiên

> **Giải thích:** Sort theo end time, chọn hoạt động kết thúc sớm nhất → để lại nhiều thời gian nhất cho hoạt động sau.

## Câu 8

[TYPE: MULTIPLE_CHOICE]

N-Queens problem (n=8) thuộc loại bài toán nào?

- [ ] Greedy
- [x] Backtracking
- [ ] Dynamic Programming
- [ ] Divide and Conquer

> **Giải thích:** N-Queens cần thử đặt hậu từng hàng, quay lui khi không thể đặt. Backtracking với pruning kiểm tra cột + 2 đường chéo.
