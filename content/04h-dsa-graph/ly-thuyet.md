# Graph (Đồ thị)

## 1. Khái niệm

**Graph** gồm tập đỉnh **V** (vertices) và tập cạnh **E** (edges) kết nối các đỉnh.

```
Undirected:        Directed:         Weighted:
  1 --- 2           1 → 2             1 --5-- 2
  |   / |           ↓   ↓             |      / |
  |  /  |           3 → 4             3    2   7
  3 --- 4                              \  /   |
                                        4 --1-- 5
```

**Phân loại:**
| Loại | Đặc điểm |
|------|----------|
| **Directed / Undirected** | Cạnh có hướng hay không |
| **Weighted / Unweighted** | Cạnh có trọng số hay không |
| **Cyclic / Acyclic** | Có vòng lặp hay không |
| **Connected / Disconnected** | Tất cả đỉnh liên thông hay không |
| **DAG** | Directed Acyclic Graph — đồ thị có hướng không chu trình |

## 2. Biểu diễn đồ thị

### Adjacency List (Phổ biến nhất)

```java
// Unweighted
Map<Integer, List<Integer>> graph = new HashMap<>();
graph.computeIfAbsent(1, k -> new ArrayList<>()).add(2);
graph.computeIfAbsent(2, k -> new ArrayList<>()).add(1);  // Undirected

// Weighted
Map<Integer, List<int[]>> wGraph = new HashMap<>();
wGraph.computeIfAbsent(1, k -> new ArrayList<>()).add(new int[]{2, 5});  // {neighbor, weight}
```

### Adjacency Matrix

```java
int[][] matrix = new int[n][n];
matrix[0][1] = 1;  // Cạnh 0→1
matrix[1][0] = 1;  // Undirected
```

| Cách | Bộ nhớ | Kiểm tra cạnh | Duyệt neighbors |
|------|--------|---------------|-----------------|
| Adjacency List | O(V + E) | O(degree) | O(degree) |
| Adjacency Matrix | O(V²) | O(1) | O(V) |

> Dùng **List** cho đồ thị sparse (ít cạnh), **Matrix** cho dense (nhiều cạnh).

## 3. BFS (Breadth-First Search)

Duyệt theo **chiều rộng**, dùng **Queue**. Tìm **đường đi ngắn nhất** trong đồ thị unweighted.

```java
public List<Integer> bfs(Map<Integer, List<Integer>> graph, int start) {
    List<Integer> result = new ArrayList<>();
    Set<Integer> visited = new HashSet<>();
    Queue<Integer> queue = new ArrayDeque<>();
    queue.offer(start);
    visited.add(start);

    while (!queue.isEmpty()) {
        int node = queue.poll();
        result.add(node);
        for (int neighbor : graph.getOrDefault(node, List.of())) {
            if (visited.add(neighbor)) queue.offer(neighbor);
        }
    }
    return result;
}

// Shortest path (unweighted)
public int shortestPath(Map<Integer, List<Integer>> graph, int start, int end) {
    if (start == end) return 0;
    Queue<int[]> queue = new ArrayDeque<>();
    Set<Integer> visited = new HashSet<>();
    queue.offer(new int[]{start, 0});
    visited.add(start);

    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        for (int neighbor : graph.getOrDefault(curr[0], List.of())) {
            if (neighbor == end) return curr[1] + 1;
            if (visited.add(neighbor)) queue.offer(new int[]{neighbor, curr[1] + 1});
        }
    }
    return -1;
}
```

## 4. DFS (Depth-First Search)

Duyệt theo **chiều sâu**, dùng **Stack** hoặc **đệ quy**. Phát hiện cycle, topological sort, connected components.

```java
// DFS Recursive
public void dfs(Map<Integer, List<Integer>> graph, int node,
                Set<Integer> visited, List<Integer> result) {
    visited.add(node);
    result.add(node);
    for (int neighbor : graph.getOrDefault(node, List.of())) {
        if (!visited.contains(neighbor)) {
            dfs(graph, neighbor, visited, result);
        }
    }
}

// DFS Iterative
public List<Integer> dfsIterative(Map<Integer, List<Integer>> graph, int start) {
    List<Integer> result = new ArrayList<>();
    Set<Integer> visited = new HashSet<>();
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(start);
    while (!stack.isEmpty()) {
        int node = stack.pop();
        if (visited.add(node)) {
            result.add(node);
            for (int n : graph.getOrDefault(node, List.of())) {
                if (!visited.contains(n)) stack.push(n);
            }
        }
    }
    return result;
}
```

### So sánh BFS vs DFS

| Tiêu chí | BFS | DFS |
|---------|-----|-----|
| Cấu trúc | Queue (FIFO) | Stack / Recursion |
| Đường ngắn nhất | Có (unweighted) | Không |
| Bộ nhớ | O(branching^depth) | O(depth) |
| Phát hiện cycle | Có | Có |
| Topological Sort | Kahn's algorithm | Postorder DFS |
| Khi nào dùng | Shortest path, level-order | Cycle detection, path finding, backtracking |

## 5. Dijkstra's Algorithm

Tìm đường đi ngắn nhất từ 1 đỉnh đến tất cả đỉnh khác (đồ thị có trọng số **không âm**).

```java
public Map<Integer, Integer> dijkstra(Map<Integer, List<int[]>> graph, int start) {
    Map<Integer, Integer> dist = new HashMap<>();
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    pq.offer(new int[]{start, 0});

    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int node = curr[0], d = curr[1];
        if (dist.containsKey(node)) continue;
        dist.put(node, d);

        for (int[] edge : graph.getOrDefault(node, List.of())) {
            if (!dist.containsKey(edge[0])) {
                pq.offer(new int[]{edge[0], d + edge[1]});
            }
        }
    }
    return dist;
}
// Time: O((V + E) log V) với PriorityQueue
```

## 6. Topological Sort

Sắp xếp các đỉnh trong DAG sao cho với mọi cạnh u→v, u đứng trước v.

```java
// Kahn's Algorithm (BFS) — dùng in-degree
public List<Integer> topologicalSort(int n, int[][] edges) {
    Map<Integer, List<Integer>> graph = new HashMap<>();
    int[] inDegree = new int[n];

    for (int[] e : edges) {
        graph.computeIfAbsent(e[0], k -> new ArrayList<>()).add(e[1]);
        inDegree[e[1]]++;
    }

    Queue<Integer> queue = new ArrayDeque<>();
    for (int i = 0; i < n; i++) {
        if (inDegree[i] == 0) queue.offer(i);
    }

    List<Integer> order = new ArrayList<>();
    while (!queue.isEmpty()) {
        int node = queue.poll();
        order.add(node);
        for (int neighbor : graph.getOrDefault(node, List.of())) {
            if (--inDegree[neighbor] == 0) queue.offer(neighbor);
        }
    }
    return order.size() == n ? order : List.of();  // Rỗng nếu có cycle
}
```

## 7. Phát hiện Cycle

```java
// Undirected graph — Union-Find
public boolean hasCycleUndirected(int n, int[][] edges) {
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;

    for (int[] e : edges) {
        int rootA = find(parent, e[0]);
        int rootB = find(parent, e[1]);
        if (rootA == rootB) return true;
        parent[rootA] = rootB;
    }
    return false;
}

private int find(int[] parent, int x) {
    while (parent[x] != x) { parent[x] = parent[parent[x]]; x = parent[x]; }
    return x;
}

// Directed graph — DFS với 3 trạng thái
// 0 = chưa thăm, 1 = đang thăm (in stack), 2 = đã xong
public boolean hasCycleDirected(Map<Integer, List<Integer>> graph, int n) {
    int[] state = new int[n];
    for (int i = 0; i < n; i++) {
        if (state[i] == 0 && dfsHasCycle(graph, i, state)) return true;
    }
    return false;
}

private boolean dfsHasCycle(Map<Integer, List<Integer>> graph, int node, int[] state) {
    state[node] = 1;
    for (int neighbor : graph.getOrDefault(node, List.of())) {
        if (state[neighbor] == 1) return true;
        if (state[neighbor] == 0 && dfsHasCycle(graph, neighbor, state)) return true;
    }
    state[node] = 2;
    return false;
}
```

## 8. Union-Find (Disjoint Set)

```java
public class UnionFind {
    private int[] parent, rank;

    public UnionFind(int n) {
        parent = new int[n]; rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    public int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);  // Path compression
        return parent[x];
    }

    public boolean union(int a, int b) {
        int ra = find(a), rb = find(b);
        if (ra == rb) return false;
        if (rank[ra] < rank[rb]) parent[ra] = rb;
        else if (rank[ra] > rank[rb]) parent[rb] = ra;
        else { parent[rb] = ra; rank[ra]++; }
        return true;
    }
}
```

## 9. Khi nào dùng Graph?

| Bài toán | Thuật toán |
|---------|-----------|
| Đường ngắn nhất (unweighted) | BFS |
| Đường ngắn nhất (weighted, non-negative) | Dijkstra |
| Đường ngắn nhất (negative weights) | Bellman-Ford |
| Đường ngắn nhất (all pairs) | Floyd-Warshall |
| Phát hiện cycle | DFS / Union-Find |
| Topological Sort | Kahn's (BFS) / DFS |
| Connected Components | DFS / BFS / Union-Find |
| Minimum Spanning Tree | Kruskal / Prim |

> **Phỏng vấn thường hỏi:** Number of Islands, Clone Graph, Course Schedule, Pacific Atlantic Water Flow, Word Ladder, Network Delay Time.
