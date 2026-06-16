# Tree (Cây)

## 1. Khái niệm

**Tree** là cấu trúc dữ liệu phân cấp gồm các **node** kết nối theo quan hệ **cha-con**. Node trên cùng là **root**, các node không có con là **leaf**.

```
          1          ← root (gốc)
        /   \
       2     3       ← internal nodes
      / \   / \
     4   5 6   7     ← leaf nodes (lá)
```

**Thuật ngữ:**
- **Root:** Node gốc, không có cha
- **Leaf:** Node lá, không có con
- **Height:** Chiều cao = số cạnh từ root đến leaf xa nhất
- **Depth:** Độ sâu = số cạnh từ root đến node đó
- **Subtree:** Cây con = node + tất cả con cháu
- **Level:** Tầng, root ở level 0

## 2. Binary Tree (Cây nhị phân)

Mỗi node có **tối đa 2 con** (left, right).

```java
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}
```

### Các loại Binary Tree

| Loại | Đặc điểm |
|------|----------|
| **Full Binary Tree** | Mỗi node có 0 hoặc 2 con |
| **Complete Binary Tree** | Tất cả tầng đầy trừ tầng cuối (lấp từ trái) |
| **Perfect Binary Tree** | Tất cả tầng đầy đủ, leaf cùng level |
| **Balanced Binary Tree** | Chênh lệch chiều cao left/right ≤ 1 |
| **Degenerate (Skewed)** | Mỗi node chỉ có 1 con → giống linked list |

## 3. Duyệt cây (Tree Traversal)

```
       1
      / \
     2   3
    / \ / \
   4  5 6  7

Inorder  (LNR): 4, 2, 5, 1, 6, 3, 7  ← BST: cho kết quả sorted
Preorder (NLR): 1, 2, 4, 5, 3, 6, 7  ← Dùng để serialize/copy cây
Postorder(LRN): 4, 5, 2, 6, 7, 3, 1  ← Dùng khi cần xử lý con trước cha
Level-order:    1, 2, 3, 4, 5, 6, 7  ← BFS, duyệt theo tầng
```

### 3.1 Recursive Traversal

```java
// Inorder (Left → Node → Right)
public void inorder(TreeNode node, List<Integer> result) {
    if (node == null) return;
    inorder(node.left, result);
    result.add(node.val);
    inorder(node.right, result);
}

// Preorder (Node → Left → Right)
public void preorder(TreeNode node, List<Integer> result) {
    if (node == null) return;
    result.add(node.val);
    preorder(node.left, result);
    preorder(node.right, result);
}

// Postorder (Left → Right → Node)
public void postorder(TreeNode node, List<Integer> result) {
    if (node == null) return;
    postorder(node.left, result);
    postorder(node.right, result);
    result.add(node.val);
}
```

### 3.2 Iterative Traversal (dùng Stack)

```java
// Inorder Iterative
public List<Integer> inorderIterative(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode curr = root;
    while (curr != null || !stack.isEmpty()) {
        while (curr != null) { stack.push(curr); curr = curr.left; }
        curr = stack.pop();
        result.add(curr.val);
        curr = curr.right;
    }
    return result;
}
```

### 3.3 Level-order (BFS)

```java
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    Queue<TreeNode> queue = new ArrayDeque<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
        int size = queue.size();
        List<Integer> level = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            level.add(node.val);
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        result.add(level);
    }
    return result;
}
```

## 4. Binary Search Tree (BST)

**Quy tắc:** Left child < Parent < Right child

```
        8
       / \
      3   10
     / \    \
    1   6   14
       / \  /
      4  7 13
```

Inorder traversal → mảng sorted: `1, 3, 4, 6, 7, 8, 10, 13, 14`

### Các thao tác BST

```java
// Search — O(h), h = chiều cao
public TreeNode search(TreeNode root, int val) {
    if (root == null || root.val == val) return root;
    if (val < root.val) return search(root.left, val);
    return search(root.right, val);
}

// Insert — O(h)
public TreeNode insert(TreeNode root, int val) {
    if (root == null) return new TreeNode(val);
    if (val < root.val) root.left = insert(root.left, val);
    else if (val > root.val) root.right = insert(root.right, val);
    return root;
}

// Delete — O(h)
public TreeNode delete(TreeNode root, int val) {
    if (root == null) return null;
    if (val < root.val) root.left = delete(root.left, val);
    else if (val > root.val) root.right = delete(root.right, val);
    else {
        if (root.left == null) return root.right;
        if (root.right == null) return root.left;
        // Node có 2 con: thay bằng successor (nhỏ nhất cây phải)
        TreeNode successor = findMin(root.right);
        root.val = successor.val;
        root.right = delete(root.right, successor.val);
    }
    return root;
}

private TreeNode findMin(TreeNode node) {
    while (node.left != null) node = node.left;
    return node;
}

// Validate BST
public boolean isValidBST(TreeNode root) {
    return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
}

private boolean validate(TreeNode node, long min, long max) {
    if (node == null) return true;
    if (node.val <= min || node.val >= max) return false;
    return validate(node.left, min, node.val) &&
           validate(node.right, node.val, max);
}
```

### Độ phức tạp BST

| Loại BST | Search | Insert | Delete |
|----------|--------|--------|--------|
| BST thường | O(n) worst | O(n) worst | O(n) worst |
| AVL Tree | O(log n) | O(log n) | O(log n) |
| Red-Black Tree | O(log n) | O(log n) | O(log n) |

> BST thường bị degenerate (suy biến thành linked list) khi insert dữ liệu sorted → worst case O(n).

## 5. Cây cân bằng (Balanced Trees)

### AVL Tree
- Chênh lệch chiều cao left/right ≤ 1 tại **mọi** node
- Cần rotation khi mất cân bằng sau insert/delete
- Strictly balanced → search nhanh hơn Red-Black

### Red-Black Tree
- Mỗi node có màu Red hoặc Black, tuân theo 5 quy tắc
- Loosely balanced → ít rotation hơn AVL khi insert/delete
- Java `TreeMap`, `TreeSet` dùng Red-Black Tree

## 6. Các bài toán kinh điển

### Chiều cao cây
```java
public int maxDepth(TreeNode root) {
    if (root == null) return 0;
    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
}
```

### Kiểm tra cây cân bằng
```java
public boolean isBalanced(TreeNode root) {
    return height(root) != -1;
}

private int height(TreeNode node) {
    if (node == null) return 0;
    int left = height(node.left);
    int right = height(node.right);
    if (left == -1 || right == -1 || Math.abs(left - right) > 1) return -1;
    return 1 + Math.max(left, right);
}
```

### Lowest Common Ancestor (LCA)
```java
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q) return root;
    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    if (left != null && right != null) return root;
    return left != null ? left : right;
}
```

### Đường kính cây (Diameter)
```java
private int diameter = 0;

public int diameterOfBinaryTree(TreeNode root) {
    depth(root);
    return diameter;
}

private int depth(TreeNode node) {
    if (node == null) return 0;
    int left = depth(node.left);
    int right = depth(node.right);
    diameter = Math.max(diameter, left + right);
    return 1 + Math.max(left, right);
}
```

### Serialize / Deserialize Binary Tree
```java
// Preorder serialization
public String serialize(TreeNode root) {
    if (root == null) return "null";
    return root.val + "," + serialize(root.left) + "," + serialize(root.right);
}

public TreeNode deserialize(String data) {
    Queue<String> queue = new ArrayDeque<>(Arrays.asList(data.split(",")));
    return buildTree(queue);
}

private TreeNode buildTree(Queue<String> queue) {
    String val = queue.poll();
    if ("null".equals(val)) return null;
    TreeNode node = new TreeNode(Integer.parseInt(val));
    node.left = buildTree(queue);
    node.right = buildTree(queue);
    return node;
}
```

## 7. Khi nào dùng Tree?

| Dùng khi | Loại cây |
|---------|---------|
| Dữ liệu phân cấp (file system, DOM) | General Tree |
| Tìm kiếm sorted O(log n) | BST / AVL / Red-Black |
| Priority Queue | Heap (Complete Binary Tree) |
| Autocomplete, spell check | Trie |
| Range query | Segment Tree, BIT |
| Database indexing | B-Tree, B+ Tree |

> **Phỏng vấn thường hỏi:** Max Depth, Validate BST, LCA, Level Order, Invert Tree, Diameter, Serialize/Deserialize, Path Sum.
