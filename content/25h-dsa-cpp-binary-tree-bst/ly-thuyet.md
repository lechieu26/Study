# Chương 8: Cây và Cây tìm kiếm nhị phân

## 1. Cây (Tree) là gì?

Cây là cấu trúc phân cấp gồm node và cạnh. Node trên cùng là root (gốc). Node không có con là leaf (lá).

Cây nhị phân là cây mà mỗi node có tối đa 2 con: left (trái) và right (phải).

## 2. Duyệt cây (Traversal)

Các cách duyệt cây nhị phân:

- Preorder (tiền thứ tự): root, left, right.
- Inorder (trung thứ tự): left, root, right.
- Postorder (hậu thứ tự): left, right, root.
- Level order (duyệt theo mức): duyệt theo từng tầng bằng hàng đợi (queue).

## 3. Cây tìm kiếm nhị phân (Binary Search Tree)

BST là cây nhị phân có tính chất:

- Mỗi node bên trái nhỏ hơn node hiện tại.
- Mỗi node bên phải lớn hơn node hiện tại.

Duyệt inorder của BST cho dãy tăng dần.

## 4. Độ phức tạp BST

Nếu cây cân bằng:

- Tìm, chèn, xóa: `O(log n)`.

Nếu cây lệch như danh sách liên kết (linked list):

- Tìm, chèn, xóa: `O(n)`.

Trong thực tế, `std::set` và `std::map` dùng cây cân bằng.

## 5. Lỗi thường gặp

- Quên xử lý cây rỗng.
- Đệ quy sâu gây tràn ngăn xếp (stack overflow) với cây quá lệch.
- Xóa node BST sai khi node có hai con.
- Nhầm thứ tự preorder/inorder/postorder.

---

# Ví dụ thực hành

# Ví dụ chương 8

## 1. Node cây nhị phân và DFS

```cpp
#include <iostream>

struct TreeNode {
    int val;
    TreeNode* left;
    TreeNode* right;

    explicit TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
};

void inorder(TreeNode* root) {
    if (root == nullptr) {
        return;
    }
    inorder(root->left);
    std::cout << root->val << " ";
    inorder(root->right);
}

void freeTree(TreeNode* root) {
    if (root == nullptr) {
        return;
    }
    freeTree(root->left);
    freeTree(root->right);
    delete root;
}

int main() {
    TreeNode* root = new TreeNode(2);
    root->left = new TreeNode(1);
    root->right = new TreeNode(3);

    inorder(root);
    std::cout << "\n";
    freeTree(root);
    return 0;
}
```

## 2. Chèn vào BST

```cpp
TreeNode* insert(TreeNode* root, int value) {
    if (root == nullptr) {
        return new TreeNode(value);
    }

    if (value < root->val) {
        root->left = insert(root->left, value);
    } else if (value > root->val) {
        root->right = insert(root->right, value);
    }

    return root;
}
```

## 3. Tìm kiếm trong BST

```cpp
bool search(TreeNode* root, int value) {
    while (root != nullptr) {
        if (root->val == value) {
            return true;
        }
        if (value < root->val) {
            root = root->left;
        } else {
            root = root->right;
        }
    }
    return false;
}
```

## 4. Duyệt theo mức (Level order traversal)

```cpp
#include <iostream>
#include <queue>

void levelOrder(TreeNode* root) {
    if (root == nullptr) {
        return;
    }

    std::queue<TreeNode*> q;
    q.push(root);

    while (!q.empty()) {
        TreeNode* node = q.front();
        q.pop();
        std::cout << node->val << " ";

        if (node->left != nullptr) {
            q.push(node->left);
        }
        if (node->right != nullptr) {
            q.push(node->right);
        }
    }
}
```

## 5. Chiều cao cây

```cpp
int height(TreeNode* root) {
    if (root == nullptr) {
        return 0;
    }
    return 1 + std::max(height(root->left), height(root->right));
}
```

## 6. Bài tập

1. Đếm số node của cây.
2. Tính tổng giá trị các node.
3. Kiểm tra cây có phải là BST hợp lệ không.
4. Tìm giá trị nhỏ nhất/lớn nhất trong BST.
