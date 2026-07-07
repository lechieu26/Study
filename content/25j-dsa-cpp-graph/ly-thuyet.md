# Chương 10: Đồ thị

## 1. Đồ thị là gì?

Graph/Đồ thị gồm:

- Vertex/Đỉnh.
- Edge/Cạnh nối giữa các đỉnh.

Đồ thị có thể:

- Có hướng hoặc vô hướng.
- Có trọng số hoặc không có trọng số.
- Liên thông hoặc không liên thông.

## 2. Biểu diễn đồ thị

Danh sách kề (Adjacency list):

```cpp
std::vector<std::vector<int>> graph(n);
```

Phù hợp khi đồ thị thưa, tiết kiệm bộ nhớ.

Ma trận kề (Adjacency matrix):

```cpp
std::vector<std::vector<int>> matrix(n, std::vector<int>(n));
```

Phù hợp khi đồ thị dày hoặc cần kiểm tra cạnh `O(1)`, nhưng tốn `O(n^2)` bộ nhớ.

## 3. Tìm kiếm theo chiều rộng (BFS)

BFS duyệt theo từng lớp bằng hàng đợi (queue).

Ứng dụng:

- Tìm đường đi ngắn nhất trên đồ thị không trọng số.
- Kiểm tra tính liên thông.
- Duyệt theo mức (level).

## 4. Tìm kiếm theo chiều sâu (DFS)

DFS đi sâu vào một nhánh trước.

Ứng dụng:

- Kiểm tra thành phần liên thông (connected component).
- Phát hiện chu trình (cycle detection).
- Sắp xếp topo (topological sort).
- Duyệt cây/đồ thị.

## 5. Thuật toán Dijkstra

Dijkstra tìm đường đi ngắn nhất từ một đỉnh trong đồ thị có trọng số không âm.

Dùng hàng đợi ưu tiên để đạt độ phức tạp `O((V + E) log V)`.

## 6. Lỗi thường gặp

- Quên đánh dấu đã thăm (visited).
- Đồ thị không liên thông nhưng chỉ DFS từ đỉnh 0.
- Dùng Dijkstra cho cạnh có trọng số âm.
- Nhầm đồ thị có hướng và vô hướng khi thêm cạnh.

---

# Ví dụ thực hành

# Ví dụ chương 10

## 1. BFS trên đồ thị vô hướng

```cpp
#include <iostream>
#include <queue>
#include <vector>

void bfs(const std::vector<std::vector<int>>& graph, int start) {
    std::vector<bool> visited(graph.size(), false);
    std::queue<int> q;

    visited[start] = true;
    q.push(start);

    while (!q.empty()) {
        int u = q.front();
        q.pop();
        std::cout << u << " ";

        for (int v : graph[u]) {
            if (!visited[v]) {
                visited[v] = true;
                q.push(v);
            }
        }
    }
}

int main() {
    std::vector<std::vector<int>> graph(5);
    graph[0] = {1, 2};
    graph[1] = {0, 3};
    graph[2] = {0, 4};
    graph[3] = {1};
    graph[4] = {2};

    bfs(graph, 0);
    std::cout << "\n";
    return 0;
}
```

## 2. DFS đệ quy

```cpp
#include <iostream>
#include <vector>

void dfs(int u, const std::vector<std::vector<int>>& graph, std::vector<bool>& visited) {
    visited[u] = true;
    std::cout << u << " ";

    for (int v : graph[u]) {
        if (!visited[v]) {
            dfs(v, graph, visited);
        }
    }
}

int main() {
    std::vector<std::vector<int>> graph = {
        {1, 2},
        {0, 3},
        {0},
        {1}
    };

    std::vector<bool> visited(graph.size(), false);
    dfs(0, graph, visited);
    std::cout << "\n";
    return 0;
}
```

## 3. Đếm số thành phần liên thông

```cpp
#include <iostream>
#include <vector>

void markComponent(int u, const std::vector<std::vector<int>>& graph, std::vector<bool>& visited) {
    visited[u] = true;
    for (int v : graph[u]) {
        if (!visited[v]) {
            markComponent(v, graph, visited);
        }
    }
}

int countComponents(const std::vector<std::vector<int>>& graph) {
    std::vector<bool> visited(graph.size(), false);
    int count = 0;

    for (int i = 0; i < static_cast<int>(graph.size()); ++i) {
        if (!visited[i]) {
            ++count;
            markComponent(i, graph, visited);
        }
    }

    return count;
}

int main() {
    std::vector<std::vector<int>> graph = {
        {1},
        {0},
        {3},
        {2},
        {}
    };

    std::cout << countComponents(graph) << "\n";
    return 0;
}
```

## 4. Thuật toán Dijkstra

```cpp
#include <functional>
#include <iostream>
#include <limits>
#include <queue>
#include <utility>
#include <vector>

std::vector<long long> dijkstra(const std::vector<std::vector<std::pair<int, int>>>& graph, int start) {
    const long long INF = std::numeric_limits<long long>::max() / 4;
    std::vector<long long> dist(graph.size(), INF);
    std::priority_queue<
        std::pair<long long, int>,
        std::vector<std::pair<long long, int>>,
        std::greater<std::pair<long long, int>>
    > pq;

    dist[start] = 0;
    pq.push({0, start});

    while (!pq.empty()) {
        auto [currentDist, u] = pq.top();
        pq.pop();

        if (currentDist != dist[u]) {
            continue;
        }

        for (auto [v, weight] : graph[u]) {
            if (dist[v] > dist[u] + weight) {
                dist[v] = dist[u] + weight;
                pq.push({dist[v], v});
            }
        }
    }

    return dist;
}

int main() {
    std::vector<std::vector<std::pair<int, int>>> graph(3);
    graph[0].push_back({1, 4});
    graph[0].push_back({2, 1});
    graph[2].push_back({1, 2});

    auto dist = dijkstra(graph, 0);
    std::cout << dist[1] << "\n";
    return 0;
}
```

## 5. Bài tập

1. Kiểm tra đồ thị vô hướng có liên thông không.
2. Tìm số cạnh ít nhất từ s đến t trong đồ thị không trọng số.
3. Kiểm tra đồ thị có chứa chu trình không.
4. Cài đặt sắp xếp topo (topological sort) cho DAG.
