# Quiz - Graph

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Cách biểu diễn đồ thị nào phù hợp nhất cho đồ thị sparse (ít cạnh)?

- [ ] Adjacency Matrix
- [x] Adjacency List
- [ ] Edge List
- [ ] Incidence Matrix

> **Giải thích:** Adjacency List: O(V+E) bộ nhớ, hiệu quả cho sparse graph. Matrix cần O(V²) dù ít cạnh.

## Câu 2

[TYPE: MULTIPLE_CHOICE]

BFS tìm được đường đi ngắn nhất trong loại đồ thị nào?

- [x] Đồ thị không trọng số (unweighted)
- [ ] Đồ thị có trọng số dương
- [ ] Đồ thị có trọng số âm
- [ ] Tất cả đồ thị

> **Giải thích:** BFS tìm shortest path cho unweighted graph. Weighted → Dijkstra. Negative weights → Bellman-Ford.

## Câu 3

[TYPE: FILL_BLANK]

Thuật toán tìm đường ngắn nhất trong đồ thị có trọng số không âm là `___`.

- [x] Dijkstra
- [ ] BFS
- [ ] DFS
- [ ] Bellman-Ford

> **Giải thích:** Dijkstra dùng PriorityQueue, greedy chọn đỉnh gần nhất. O((V+E) log V). Yêu cầu trọng số ≥ 0.

## Câu 4

[TYPE: MULTIPLE_CHOICE]

Topological Sort áp dụng cho loại đồ thị nào?

- [ ] Undirected Graph
- [ ] Cyclic Graph
- [x] DAG (Directed Acyclic Graph)
- [ ] Complete Graph

> **Giải thích:** Topological Sort chỉ tồn tại trên DAG — đồ thị có hướng không chu trình. Dùng Kahn's (BFS) hoặc DFS.

## Câu 5

[TYPE: MULTIPLE_CHOICE]

Thuật toán nào phát hiện cycle trong undirected graph hiệu quả nhất?

- [ ] BFS
- [ ] DFS
- [x] Union-Find
- [ ] Dijkstra

> **Giải thích:** Union-Find: gần O(1) amortized cho mỗi cạnh (với path compression + union by rank). Tổng O(E × α(V)).

## Câu 6

[TYPE: TRUE_FALSE]

Mệnh đề: "DFS có thể tìm đường đi ngắn nhất trong đồ thị unweighted."

- [ ] Đúng
- [x] Sai

> **Giải thích:** DFS tìm đường đi nhưng không đảm bảo ngắn nhất. BFS mới đảm bảo shortest path cho unweighted graph.

## Câu 7

[TYPE: SELECT_RESULT]

Cho đồ thị: 0→1, 0→2, 1→3, 2→3. Topological sort cho kết quả nào?

- [x] [0, 1, 2, 3] hoặc [0, 2, 1, 3]
- [ ] [3, 1, 2, 0]
- [ ] [0, 3, 1, 2]
- [ ] [1, 2, 0, 3]

> **Giải thích:** Node 0 phải đứng trước 1, 2. Node 1, 2 phải đứng trước 3. Cả [0,1,2,3] và [0,2,1,3] đều hợp lệ.

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Độ phức tạp thời gian của BFS và DFS trên đồ thị là gì?

- [ ] O(V)
- [ ] O(E)
- [x] O(V + E)
- [ ] O(V × E)

> **Giải thích:** BFS/DFS thăm mỗi đỉnh 1 lần O(V) và duyệt mỗi cạnh 1 lần O(E) → tổng O(V + E).
