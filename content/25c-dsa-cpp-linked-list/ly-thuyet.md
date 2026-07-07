# Chương 3: Danh sách liên kết

## 1. Danh sách liên kết là gì?

Danh sách liên kết là danh sách gồm các node. Mỗi node chứa dữ liệu và con trỏ đến node tiếp theo.

```cpp
struct Node {
    int data;
    Node* next;
};
```

Khác với vector, các node không cần nằm liên tiếp trong bộ nhớ.

## 2. Ưu và nhược điểm

Ưu điểm:

- Chèn/xóa đầu danh sách `O(1)`.
- Chèn/xóa sau một node đã biết `O(1)`.
- Kích thước linh hoạt.

Nhược điểm:

- Truy cập phần tử thứ `i` là `O(n)`.
- Tốn thêm bộ nhớ cho con trỏ.
- Dễ lỗi bộ nhớ nếu quản lý thủ công.
- Cache locality kém hơn vector.

## 3. Khi nào dùng?

Dùng danh sách liên kết khi bài toán tập trung vào chèn/xóa node nhiều và bạn đã có vị trí node.

Trong lập trình thực tế, `std::vector` thường là lựa chọn mặc định. Danh sách liên kết quan trọng để hiểu con trỏ, node, và cấu trúc dữ liệu như stack, queue, graph.

## 4. Các thao tác cơ bản

- Thêm đầu.
- Thêm cuối.
- Tìm giá trị.
- Xóa giá trị đầu tiên.
- Đảo ngược danh sách.
- Giải phóng bộ nhớ.

## 5. Dummy node

Dummy node là node giả dùng để đơn giản hóa thao tác với head, đặc biệt khi xóa node đầu.

```cpp
Node dummy(0);
dummy.next = head;
```

## 6. Lỗi thường gặp

- Mất head khi thêm/xóa.
- Quên cập nhật `next`.
- Quên delete node.
- Dùng node sau khi đã delete.
- Vòng lặp vô hạn do list bị nối vòng.

---

# Ví dụ thực hành

# Ví dụ chương 3

## 1. Danh sách liên kết đơn cơ bản

```cpp
#include <iostream>

struct Node {
    int data;
    Node* next;

    explicit Node(int value) : data(value), next(nullptr) {}
};

void pushFront(Node*& head, int value) {
    Node* node = new Node(value);
    node->next = head;
    head = node;
}

void printList(Node* head) {
    for (Node* cur = head; cur != nullptr; cur = cur->next) {
        std::cout << cur->data << " ";
    }
    std::cout << "\n";
}

void freeList(Node*& head) {
    while (head != nullptr) {
        Node* next = head->next;
        delete head;
        head = next;
    }
}

int main() {
    Node* head = nullptr;
    pushFront(head, 3);
    pushFront(head, 2);
    pushFront(head, 1);
    printList(head);
    freeList(head);
    return 0;
}
```

## 2. Tìm kiếm trong danh sách liên kết

```cpp
bool contains(Node* head, int value) {
    for (Node* cur = head; cur != nullptr; cur = cur->next) {
        if (cur->data == value) {
            return true;
        }
    }
    return false;
}
```

## 3. Xóa giá trị đầu tiên

```cpp
bool removeFirst(Node*& head, int value) {
    Node dummy(0);
    dummy.next = head;
    Node* prev = &dummy;

    while (prev->next != nullptr) {
        if (prev->next->data == value) {
            Node* removed = prev->next;
            prev->next = removed->next;
            delete removed;
            head = dummy.next;
            return true;
        }
        prev = prev->next;
    }

    head = dummy.next;
    return false;
}
```

## 4. Đảo ngược danh sách liên kết

```cpp
Node* reverseList(Node* head) {
    Node* prev = nullptr;
    Node* cur = head;

    while (cur != nullptr) {
        Node* next = cur->next;
        cur->next = prev;
        prev = cur;
        cur = next;
    }

    return prev;
}
```

## 5. Bài tập

1. Viết hàm thêm cuối danh sách liên kết.
2. Đếm số node trong danh sách liên kết.
3. Tìm node ở giữa danh sách bằng slow/fast pointer.
4. Kiểm tra danh sách liên kết có chu trình (cycle) bằng thuật toán Floyd.
