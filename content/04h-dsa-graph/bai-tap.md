# Graph - Bài Tập

## Bài 1: Đếm Số Đảo (Number of Islands)
**Độ khó: Trung bình**

Cho ma trận 2D gồm '1' (đất) và '0' (nước), đếm số đảo. Một đảo được tạo bởi các ô '1' kề nhau (ngang/dọc).

**Đầu vào:**
```
1 1 0 0 0
1 1 0 0 0
0 0 1 0 0
0 0 0 1 1
```
**Đầu ra:** `3`

---

## Bài 2: Clone Graph
**Độ khó: Trung bình**

Cho một node trong undirected graph, tạo deep copy toàn bộ graph.

---

## Bài 3: Course Schedule (Topological Sort)
**Độ khó: Trung bình**

Cho n khóa học (0 đến n-1) và danh sách điều kiện tiên quyết. Kiểm tra có thể hoàn thành tất cả khóa học không.

**Đầu vào:** `numCourses = 4`, `prerequisites = [[1,0],[2,0],[3,1],[3,2]]`
**Đầu ra:** `true` (thứ tự: 0→1→2→3)

**Đầu vào:** `numCourses = 2`, `prerequisites = [[1,0],[0,1]]`
**Đầu ra:** `false` (cycle)

---

## Bài 4: Đường Đi Ngắn Nhất trong Mê Cung (BFS)
**Độ khó: Trung bình**

Cho mê cung ma trận 2D (0=đường, 1=tường), tìm đường ngắn nhất từ (0,0) đến (m-1,n-1).

**Đầu vào:**
```
[0, 0, 0, 0]
[1, 1, 0, 1]
[0, 0, 0, 0]
[0, 1, 1, 0]
```
**Đầu ra:** `7`

---

## Bài 5: Network Delay Time (Dijkstra)
**Độ khó: Trung bình**

Cho đồ thị có hướng có trọng số, gửi tín hiệu từ node k. Tìm thời gian để tất cả node nhận được tín hiệu.

**Đầu vào:** `times = [[2,1,1],[2,3,1],[3,4,1]]`, `n = 4`, `k = 2`
**Đầu ra:** `2`

---

## Bài 6: Word Ladder
**Độ khó: Khó**

Cho từ bắt đầu, từ kết thúc, và danh sách từ. Tìm chuỗi biến đổi ngắn nhất (mỗi bước đổi 1 ký tự).

**Đầu vào:** `beginWord = "hit"`, `endWord = "cog"`, `wordList = ["hot","dot","dog","lot","log","cog"]`
**Đầu ra:** `5` (hit → hot → dot → dog → cog)
