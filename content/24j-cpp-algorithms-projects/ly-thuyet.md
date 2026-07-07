# Chương 10: Thuật toán, cấu trúc dữ liệu và dự án

## 1. Vì sao cần thuật toán?

Thuật toán là cách giải bài toán. Cùng một bài toán, cách giải khác nhau có thể lệch nhau rất lớn về tốc độ và bộ nhớ.

Người học C/C++ nên nắm:

- Tìm kiếm tuyến tính và nhị phân.
- Sắp xếp cơ bản và dùng `std::sort`.
- Stack, queue, linked list, tree, graph ở mức ý tưởng.
- Recursion và dynamic programming cơ bản.

## 2. Tìm kiếm tuyến tính

Duyệt từng phần tử đến khi thấy.

Độ phức tạp: `O(n)`.

Phù hợp khi dữ liệu nhỏ hoặc chưa sắp xếp.

## 3. Tìm kiếm nhị phân

Dùng cho dữ liệu đã sắp xếp.

Mỗi bước loại bỏ nửa khoảng tìm kiếm.

Độ phức tạp: `O(log n)`.

## 4. Stack

Stack hoạt động LIFO: vào sau ra trước.

Ứng dụng:

- Kiểm tra ngoặc đúng.
- Undo/redo.
- DFS.
- Gọi hàm trong runtime.

## 5. Queue

Queue hoạt động FIFO: vào trước ra trước.

Ứng dụng:

- Hàng đợi xử lý công việc.
- BFS.
- Mô phỏng hệ thống.

## 6. Linked list

Danh sách liên kết gồm các node, mỗi node trỏ tới node tiếp theo.

Ưu điểm: chèn/xóa khi đã có node có thể nhanh.

Nhược điểm: truy cập theo chỉ số chậm, tốn bộ nhớ cho con trỏ, kém cache hơn vector.

Trong C++ thực tế, `std::vector` thường là lựa chọn đầu tiên trước khi nghĩ đến linked list.

## 7. Tree và graph

Tree là cấu trúc phân cấp, ví dụ file system, DOM, cây tìm kiếm.

Graph gồm đỉnh và cạnh, dùng cho mạng xã hội, bản đồ, phụ thuộc công việc.

## 8. Dự án tổng hợp nên làm

Sau khi học xong, hãy làm các dự án nhỏ:

- Quản lý sinh viên bằng file.
- Todo list console.
- Quản lý thư viện sách.
- Game đoán số.
- Máy tính biểu thức đơn giản.
- Hệ thống đăng nhập giả lập.

Mỗi dự án nên có:

- Menu rõ ràng.
- Struct/class cho dữ liệu.
- Hàm tách nhỏ.
- Lưu/nạp file nếu phù hợp.
- Xử lý input sai.

---

# Ví dụ thực hành

# Ví dụ chương 10

## 1. Tìm kiếm nhị phân

```cpp
#include <iostream>
#include <vector>

int binarySearch(const std::vector<int>& numbers, int target) {
    int left = 0;
    int right = static_cast<int>(numbers.size()) - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;

        if (numbers[mid] == target) {
            return mid;
        }

        if (numbers[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }

    return -1;
}

int main() {
    std::vector<int> numbers = {1, 3, 5, 7, 9};
    std::cout << binarySearch(numbers, 7) << "\n";
    return 0;
}
```

## 2. Kiểm tra ngoặc bằng stack

```cpp
#include <iostream>
#include <stack>
#include <string>

bool isValidParentheses(const std::string& text) {
    std::stack<char> st;

    for (char ch : text) {
        if (ch == '(' || ch == '[' || ch == '{') {
            st.push(ch);
        } else if (ch == ')' || ch == ']' || ch == '}') {
            if (st.empty()) {
                return false;
            }

            char open = st.top();
            st.pop();

            if ((open == '(' && ch != ')') ||
                (open == '[' && ch != ']') ||
                (open == '{' && ch != '}')) {
                return false;
            }
        }
    }

    return st.empty();
}

int main() {
    std::cout << isValidParentheses("({[]})") << "\n";
    std::cout << isValidParentheses("([)]") << "\n";
    return 0;
}
```

## 3. BFS đơn giản trên graph

```cpp
#include <iostream>
#include <queue>
#include <vector>

int main() {
    std::vector<std::vector<int>> graph = {
        {1, 2},
        {0, 3},
        {0, 3},
        {1, 2}
    };

    std::vector<bool> visited(graph.size(), false);
    std::queue<int> q;

    visited[0] = true;
    q.push(0);

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

    std::cout << "\n";
    return 0;
}
```

## 4. Khung dự án Todo list console

```cpp
#include <iostream>
#include <string>
#include <vector>

class TodoList {
public:
    void add(std::string task) {
        tasks.push_back(std::move(task));
    }

    bool remove(int index) {
        if (index < 0 || index >= static_cast<int>(tasks.size())) {
            return false;
        }
        tasks.erase(tasks.begin() + index);
        return true;
    }

    void print() const {
        if (tasks.empty()) {
            std::cout << "Chưa có công việc\n";
            return;
        }

        for (std::size_t i = 0; i < tasks.size(); ++i) {
            std::cout << i << ". " << tasks[i] << "\n";
        }
    }

private:
    std::vector<std::string> tasks;
};

int main() {
    TodoList todo;
    todo.add("Học vector");
    todo.add("Viết bài tập file");
    todo.print();
    todo.remove(0);
    todo.print();
    return 0;
}
```

## 5. Bài tập tổng hợp

1. Hoàn thiện Todo list có menu thêm, xóa, sửa, tìm kiếm.
2. Thêm tính năng lưu và đọc Todo list từ file.
3. Viết chương trình quản lý sinh viên: thêm, xóa, sửa, sắp xếp theo điểm, tìm theo tên.
4. Viết game đoán số có giới hạn lượt chơi và thống kê kết quả.
5. Viết chương trình đọc file văn bản và in 10 từ xuất hiện nhiều nhất.
