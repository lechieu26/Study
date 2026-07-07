# Chương 9: Heap và hàng đợi ưu tiên (priority_queue)

## 1. Heap là gì?

Heap là cây nhị phân gần đầy đủ, thỏa mãn tính chất ưu tiên.

Max heap: node cha >= node con.

Min heap: node cha <= node con.

Trong C++, `std::priority_queue` mặc định là max heap.

## 2. Thao tác

- Lấy phần tử ưu tiên nhất: `O(1)`.
- Thêm phần tử: `O(log n)`.
- Xóa phần tử ưu tiên nhất: `O(log n)`.

## 3. Khi nào dùng heap?

- Cần lấy min/max liên tục.
- Top K phần tử.
- Trộn k danh sách đã sắp xếp (merge k sorted lists).
- Thuật toán đường đi ngắn nhất Dijkstra.
- Lập lịch (Scheduling).

## 4. Min heap trong C++

```cpp
std::priority_queue<int, std::vector<int>, std::greater<int>> pq;
```

## 5. Lỗi thường gặp

- Quên include `<queue>`.
- Nhầm lẫn giữa max heap và min heap.
- Cung cấp custom comparator sai quy tắc.
- Dùng heap khi cần tìm/xóa phần tử bất kỳ, vì heap không hỗ trợ nhanh thao tác đó.

---

# Ví dụ thực hành

# Ví dụ chương 9

## 1. Max heap mặc định

```cpp
#include <iostream>
#include <queue>

int main() {
    std::priority_queue<int> pq;
    pq.push(5);
    pq.push(1);
    pq.push(9);

    while (!pq.empty()) {
        std::cout << pq.top() << " ";
        pq.pop();
    }
    std::cout << "\n";
    return 0;
}
```

## 2. Min heap

```cpp
#include <functional>
#include <iostream>
#include <queue>
#include <vector>

int main() {
    std::priority_queue<int, std::vector<int>, std::greater<int>> pq;
    pq.push(5);
    pq.push(1);
    pq.push(9);

    while (!pq.empty()) {
        std::cout << pq.top() << " ";
        pq.pop();
    }
    std::cout << "\n";
    return 0;
}
```

## 3. Top K lớn nhất (Top K largest)

```cpp
#include <functional>
#include <iostream>
#include <queue>
#include <vector>

std::vector<int> topKLargest(const std::vector<int>& a, int k) {
    std::priority_queue<int, std::vector<int>, std::greater<int>> heap;

    for (int x : a) {
        heap.push(x);
        if (static_cast<int>(heap.size()) > k) {
            heap.pop();
        }
    }

    std::vector<int> result;
    while (!heap.empty()) {
        result.push_back(heap.top());
        heap.pop();
    }
    return result;
}

int main() {
    std::vector<int> a = {5, 1, 9, 2, 7};
    for (int x : topKLargest(a, 3)) {
        std::cout << x << " ";
    }
    std::cout << "\n";
    return 0;
}
```

## 4. Sắp xếp công việc theo độ ưu tiên

```cpp
#include <iostream>
#include <queue>
#include <string>
#include <vector>

struct Job {
    std::string name;
    int priority;
};

struct CompareJob {
    bool operator()(const Job& a, const Job& b) const {
        return a.priority < b.priority; // priority lớn hơn được xử lý trước
    }
};

int main() {
    std::priority_queue<Job, std::vector<Job>, CompareJob> jobs;
    jobs.push({"build", 2});
    jobs.push({"fix bug", 5});
    jobs.push({"write docs", 1});

    while (!jobs.empty()) {
        std::cout << jobs.top().name << "\n";
        jobs.pop();
    }

    return 0;
}
```

## 5. Bài tập

1. Tìm K phần tử nhỏ nhất.
2. Nối dây với chi phí nhỏ nhất: mỗi lần nối 2 đoạn nhỏ nhất.
3. Tìm trung vị (median) của luồng số (stream).
4. Trộn k mảng đã sắp xếp.
