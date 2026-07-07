# Chương 4: Ngăn xếp, hàng đợi và deque

## 1. Ngăn xếp (Stack)

Stack hoạt động theo LIFO: last in, first out. Phần tử vào sau sẽ ra trước.

Thao tác:

- `push`: thêm.
- `pop`: xóa phần tử trên cùng.
- `top`: xem phần tử trên cùng.
- `empty`: kiểm tra rỗng.

Ứng dụng:

- Kiểm tra ngoặc.
- Undo/redo.
- DFS.
- Chuyển đổi biểu thức.
- Gọi hàm/đệ quy.

## 2. Hàng đợi (Queue)

Queue hoạt động theo FIFO: first in, first out.

Thao tác:

- `push`: thêm vào cuối.
- `pop`: lấy ra đầu.
- `front`: xem đầu hàng đợi.

Ứng dụng:

- BFS.
- Hàng đợi xử lý công việc.
- Mô phỏng.

## 3. Deque

Deque là double-ended queue, cho thêm/xóa ở cả hai đầu.

Thao tác:

- `push_front`, `push_back`.
- `pop_front`, `pop_back`.
- `front`, `back`.

Deque hay dùng trong sliding window maximum.

## 4. Monotonic stack/queue

Monotonic stack giữ các phần tử theo thứ tự tăng/giảm để tìm next greater element (phần tử lớn hơn tiếp theo).

Monotonic queue giữ ứng viên max/min trong sliding window.

## 5. Lỗi thường gặp

- Gọi `top`, `front`, `pop` khi container rỗng.
- Quên pop trong BFS làm vòng lặp vô hạn.
- Dùng stack thay queue trong bài cần BFS theo layer.
- Monotonic queue sai khi quên xóa index hết hạn.

---

# Ví dụ thực hành

# Ví dụ chương 4

## 1. Kiểm tra ngoặc hợp lệ

```cpp
#include <iostream>
#include <stack>
#include <string>

bool isValid(const std::string& s) {
    std::stack<char> st;

    for (char ch : s) {
        if (ch == '(' || ch == '[' || ch == '{') {
            st.push(ch);
        } else {
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
    std::cout << isValid("([]{})") << "\n";
    std::cout << isValid("([)]") << "\n";
    return 0;
}
```

## 2. Hàng đợi từ hai ngăn xếp

```cpp
#include <iostream>
#include <stack>

class MyQueue {
public:
    void push(int x) {
        in.push(x);
    }

    int front() {
        moveIfNeeded();
        return out.top();
    }

    void pop() {
        moveIfNeeded();
        out.pop();
    }

    bool empty() const {
        return in.empty() && out.empty();
    }

private:
    std::stack<int> in;
    std::stack<int> out;

    void moveIfNeeded() {
        if (!out.empty()) {
            return;
        }
        while (!in.empty()) {
            out.push(in.top());
            in.pop();
        }
    }
};

int main() {
    MyQueue q;
    q.push(1);
    q.push(2);
    std::cout << q.front() << "\n";
    q.pop();
    std::cout << q.front() << "\n";
    return 0;
}
```

## 3. Sliding window maximum bằng deque

```cpp
#include <deque>
#include <iostream>
#include <vector>

std::vector<int> maxSlidingWindow(const std::vector<int>& a, int k) {
    std::vector<int> result;
    std::deque<int> dq; // lưu index, giá trị giảm dần

    for (int i = 0; i < static_cast<int>(a.size()); ++i) {
        while (!dq.empty() && dq.front() <= i - k) {
            dq.pop_front();
        }

        while (!dq.empty() && a[dq.back()] <= a[i]) {
            dq.pop_back();
        }

        dq.push_back(i);

        if (i >= k - 1) {
            result.push_back(a[dq.front()]);
        }
    }

    return result;
}

int main() {
    std::vector<int> a = {1, 3, -1, -3, 5, 3, 6, 7};
    for (int x : maxSlidingWindow(a, 3)) {
        std::cout << x << " ";
    }
    std::cout << "\n";
    return 0;
}
```

## 4. Bài tập

1. Cài đặt ngăn xếp bằng vector.
2. Cài đặt hàng đợi bằng mảng vòng (circular array).
3. Tìm next greater element (phần tử lớn hơn tiếp theo) cho mỗi phần tử.
4. Dùng hàng đợi trong BFS theo từng cấp độ (level).
