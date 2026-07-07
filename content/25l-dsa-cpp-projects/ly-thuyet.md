# Chương 12: Dự án và bài tập tổng hợp

## 1. Mục tiêu

Sau khi học từng cấu trúc dữ liệu riêng lẻ, bạn cần luyện cách nhận diện bài toán.

Một bài DSA tốt thường không nói thẳng "hãy dùng stack" hay "hãy dùng quy hoạch động". Bạn phải nhìn dấu hiệu:

- Đoạn con liên tiếp: sliding window, prefix sum.
- Dữ liệu đã sắp xếp: binary search, two pointers.
- Cần min/max liên tục: heap.
- Quan hệ đỉnh-cạnh: đồ thị (graph).
- Bài toán con lặp lại: quy hoạch động (DP).
- Cần thử tất cả khả năng: quay lui (backtracking).
- Cần tìm nhanh đã xuất hiện: bảng băm (hash table).

## 2. Cách tự luyện

1. Giải brute force trước.
2. Viết độ phức tạp brute force.
3. Tìm điểm nghẽn (bottleneck).
4. Chọn cấu trúc dữ liệu/thuật toán để giảm điểm nghẽn.
5. Viết code sạch, tách hàm.
6. Tự tạo test biên.

## 3. Bộ bài tập nên làm

Mảng/chuỗi (Array/string):

- Two Sum.
- Maximum Subarray.
- Longest Substring Without Repeating Characters.
- Product of Array Except Self.

Ngăn xếp/hàng đợi (Stack/queue):

- Valid Parentheses.
- Min Stack.
- Daily Temperatures.
- Sliding Window Maximum.

Cây/đồ thị (Tree/graph):

- Maximum Depth of Binary Tree.
- Validate BST.
- Number of Islands.
- Shortest Path in Binary Matrix.

Quy hoạch động (DP):

- Climbing Stairs.
- House Robber.
- Coin Change.
- Longest Common Subsequence.

## 4. Tiêu chuẩn code DSA tốt

- Tên biến rõ nghĩa.
- Hàm nhỏ, mỗi hàm làm một việc.
- Không hard-code vô lý.
- Kiểm tra input rỗng.
- Dùng `long long` khi tổng có thể lớn.
- Comment ngắn cho ý tưởng khó, không comment từng dòng hiển nhiên.

---

# Ví dụ thực hành

# Ví dụ chương 12

## 1. Dự án nhỏ: bộ đếm tần suất từ trong file

Ý tưởng DSA:

- Dùng `unordered_map` để đếm tần suất.
- Dùng `vector` để sắp xếp kết quả.
- Dùng comparator để sắp xếp theo tần suất giảm dần.

```cpp
#include <algorithm>
#include <cctype>
#include <fstream>
#include <iostream>
#include <string>
#include <unordered_map>
#include <vector>

std::string normalize(std::string word) {
    std::string result;
    for (char ch : word) {
        if (std::isalnum(static_cast<unsigned char>(ch))) {
            result.push_back(static_cast<char>(std::tolower(static_cast<unsigned char>(ch))));
        }
    }
    return result;
}

int main() {
    std::ifstream in("input.txt");
    if (!in) {
        std::cout << "Khong mo duoc input.txt\n";
        return 1;
    }

    std::unordered_map<std::string, int> freq;
    std::string word;

    while (in >> word) {
        word = normalize(word);
        if (!word.empty()) {
            ++freq[word];
        }
    }

    std::vector<std::pair<std::string, int>> items(freq.begin(), freq.end());
    std::sort(items.begin(), items.end(), [](const auto& a, const auto& b) {
        if (a.second != b.second) {
            return a.second > b.second;
        }
        return a.first < b.first;
    });

    int limit = std::min(10, static_cast<int>(items.size()));
    for (int i = 0; i < limit; ++i) {
        std::cout << items[i].first << ": " << items[i].second << "\n";
    }

    return 0;
}
```

## 2. Dự án nhỏ: tìm đường ngắn nhất trong lưới

Ý tưởng DSA:

- Mỗi ô là một đỉnh.
- Đi 4 hướng là cạnh.
- Vì mỗi bước có chi phí 1, dùng BFS.

```cpp
#include <iostream>
#include <queue>
#include <utility>
#include <vector>

int shortestPathGrid(const std::vector<std::vector<int>>& grid) {
    int rows = static_cast<int>(grid.size());
    int cols = static_cast<int>(grid[0].size());

    if (grid[0][0] == 1 || grid[rows - 1][cols - 1] == 1) {
        return -1;
    }

    std::vector<std::vector<int>> dist(rows, std::vector<int>(cols, -1));
    std::queue<std::pair<int, int>> q;
    std::vector<int> dr = {-1, 1, 0, 0};
    std::vector<int> dc = {0, 0, -1, 1};

    dist[0][0] = 0;
    q.push({0, 0});

    while (!q.empty()) {
        auto [r, c] = q.front();
        q.pop();

        for (int k = 0; k < 4; ++k) {
            int nr = r + dr[k];
            int nc = c + dc[k];

            if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) {
                continue;
            }
            if (grid[nr][nc] == 1 || dist[nr][nc] != -1) {
                continue;
            }

            dist[nr][nc] = dist[r][c] + 1;
            q.push({nr, nc});
        }
    }

    return dist[rows - 1][cols - 1];
}

int main() {
    std::vector<std::vector<int>> grid = {
        {0, 0, 0},
        {1, 1, 0},
        {0, 0, 0}
    };

    std::cout << shortestPathGrid(grid) << "\n";
    return 0;
}
```

## 3. Dự án nhỏ: lịch xử lý task theo ưu tiên

Ý tưởng DSA:

- Dùng priority queue.
- Task có priority lớn hơn xử lý trước.
- Nếu priority bằng nhau, task đến trước xử lý trước.

```cpp
#include <iostream>
#include <queue>
#include <string>
#include <vector>

struct Task {
    std::string name;
    int priority;
    int order;
};

struct CompareTask {
    bool operator()(const Task& a, const Task& b) const {
        if (a.priority != b.priority) {
            return a.priority < b.priority;
        }
        return a.order > b.order;
    }
};

int main() {
    std::priority_queue<Task, std::vector<Task>, CompareTask> pq;
    int order = 0;

    pq.push({"hoc graph", 3, order++});
    pq.push({"sua bug", 5, order++});
    pq.push({"viet note", 3, order++});

    while (!pq.empty()) {
        Task task = pq.top();
        pq.pop();
        std::cout << task.name << "\n";
    }

    return 0;
}
```

## 4. Bài tập tổng hợp

1. Viết chương trình quản lý điểm thi: thêm điểm, thống kê tần suất, tìm top K điểm cao.
2. Viết công cụ đọc file text và tìm từ xuất hiện nhiều nhất.
3. Viết game tìm đường trong mê cung (maze) bằng BFS.
4. Viết bộ lập lịch task dùng hàng đợi ưu tiên (priority queue).
5. Viết chương trình gợi ý bài tập: mỗi bài có tag, độ khó, điểm; tìm theo tag bằng bảng băm (hash map).
