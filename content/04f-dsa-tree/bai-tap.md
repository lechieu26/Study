# Tree - Bài Tập

## Bài 1: Chiều Cao Cây Nhị Phân
**Độ khó: Dễ**

Tìm chiều cao (max depth) của cây nhị phân.

```
    3
   / \
  9  20
    /  \
   15   7
```
**Đầu ra:** `3`

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [3,9,20,null,null,15,7] | 3 |
| 2 | [1,null,2] | 2 |
| 3 | [] | 0 |
| 4 | [1] | 1 |
| 5 | [1,2,3,4,5] | 3 |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        check(1, String.valueOf(maxDepth(buildTree(new Integer[]{3,9,20,null,null,15,7}))), "3");
        check(2, String.valueOf(maxDepth(buildTree(new Integer[]{1,null,2}))), "2");
        check(3, String.valueOf(maxDepth(null)), "0");
        check(4, String.valueOf(maxDepth(buildTree(new Integer[]{1}))), "1");
        check(5, String.valueOf(maxDepth(buildTree(new Integer[]{1,2,3,4,5}))), "3");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static TreeNode buildTree(Integer[] vals) {
        if (vals == null || vals.length == 0 || vals[0] == null) return null;
        TreeNode root = new TreeNode(vals[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int i = 1;
        while (!q.isEmpty() && i < vals.length) {
            TreeNode node = q.poll();
            if (i < vals.length && vals[i] != null) { node.left = new TreeNode(vals[i]); q.add(node.left); }
            i++;
            if (i < vals.length && vals[i] != null) { node.right = new TreeNode(vals[i]); q.add(node.right); }
            i++;
        }
        return root;
    }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int maxDepth(TreeNode root) {
        // Code here ...
        return 0;
    }
}
```

---

## Bài 2: Invert Binary Tree
**Độ khó: Dễ**

Đảo ngược (mirror) cây nhị phân — hoán đổi left và right tại mỗi node.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [4,2,7,1,3,6,9] | [4,7,2,9,6,3,1] |
| 2 | [2,1,3] | [2,3,1] |
| 3 | [] | null |
| 4 | [1] | [1] |
| 5 | [1,2] | [1,null,2] |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        check(1, treeToString(invertTree(buildTree(new Integer[]{4,2,7,1,3,6,9}))), "[4,7,2,9,6,3,1]");
        check(2, treeToString(invertTree(buildTree(new Integer[]{2,1,3}))), "[2,3,1]");
        check(3, treeToString(invertTree(null)), "null");
        check(4, treeToString(invertTree(buildTree(new Integer[]{1}))), "[1]");
        check(5, treeToString(invertTree(buildTree(new Integer[]{1,2}))), "[1,null,2]");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static TreeNode buildTree(Integer[] vals) {
        if (vals == null || vals.length == 0 || vals[0] == null) return null;
        TreeNode root = new TreeNode(vals[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int i = 1;
        while (!q.isEmpty() && i < vals.length) {
            TreeNode node = q.poll();
            if (i < vals.length && vals[i] != null) { node.left = new TreeNode(vals[i]); q.add(node.left); }
            i++;
            if (i < vals.length && vals[i] != null) { node.right = new TreeNode(vals[i]); q.add(node.right); }
            i++;
        }
        return root;
    }

    static String treeToString(TreeNode root) {
        if (root == null) return "null";
        List<String> result = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node == null) { result.add("null"); continue; }
            result.add(String.valueOf(node.val));
            q.add(node.left);
            q.add(node.right);
        }
        while (result.size() > 0 && result.get(result.size()-1).equals("null")) result.remove(result.size()-1);
        return "[" + String.join(",", result) + "]";
    }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static TreeNode invertTree(TreeNode root) {
        // Code here ...
        return root;
    }
}
```

---

## Bài 3: Validate BST
**Độ khó: Trung bình**

Kiểm tra cây nhị phân có phải BST hợp lệ không.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [2,1,3] | true |
| 2 | [5,1,4,null,null,3,6] | false |
| 3 | [1] | true |
| 4 | [5,4,6,null,null,3,7] | false |
| 5 | [2,2,2] | false |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        check(1, String.valueOf(isValidBST(buildTree(new Integer[]{2,1,3}))), "true");
        check(2, String.valueOf(isValidBST(buildTree(new Integer[]{5,1,4,null,null,3,6}))), "false");
        check(3, String.valueOf(isValidBST(buildTree(new Integer[]{1}))), "true");
        check(4, String.valueOf(isValidBST(buildTree(new Integer[]{5,4,6,null,null,3,7}))), "false");
        check(5, String.valueOf(isValidBST(buildTree(new Integer[]{2,2,2}))), "false");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static TreeNode buildTree(Integer[] vals) {
        if (vals == null || vals.length == 0 || vals[0] == null) return null;
        TreeNode root = new TreeNode(vals[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int i = 1;
        while (!q.isEmpty() && i < vals.length) {
            TreeNode node = q.poll();
            if (i < vals.length && vals[i] != null) { node.left = new TreeNode(vals[i]); q.add(node.left); }
            i++;
            if (i < vals.length && vals[i] != null) { node.right = new TreeNode(vals[i]); q.add(node.right); }
            i++;
        }
        return root;
    }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static boolean isValidBST(TreeNode root) {
        // Code here ...
        return false;
    }
}
```

---

## Bài 4: Tổ Tiên Chung Gần Nhất (LCA)
**Độ khó: Trung bình**

Cho cây nhị phân và hai giá trị p, q, tìm Lowest Common Ancestor.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [3,5,1,6,2,0,8], p=5, q=1 | 3 |
| 2 | [3,5,1,6,2,0,8], p=5, q=4 | 5 |
| 3 | [1,2], p=1, q=2 | 1 |
| 4 | [3,5,1], p=5, q=1 | 3 |
| 5 | [1,2,3], p=2, q=3 | 1 |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        TreeNode t1 = buildTree(new Integer[]{3,5,1,6,2,0,8,null,null,7,4});
        TreeNode p1 = findNode(t1, 5), q1 = findNode(t1, 1);
        check(1, String.valueOf(lowestCommonAncestor(t1, p1, q1).val), "3");

        TreeNode p2 = findNode(t1, 5), q2 = findNode(t1, 4);
        check(2, String.valueOf(lowestCommonAncestor(t1, p2, q2).val), "5");

        TreeNode t3 = buildTree(new Integer[]{1,2});
        check(3, String.valueOf(lowestCommonAncestor(t3, t3, t3.left).val), "1");

        TreeNode t4 = buildTree(new Integer[]{3,5,1});
        check(4, String.valueOf(lowestCommonAncestor(t4, t4.left, t4.right).val), "3");

        TreeNode t5 = buildTree(new Integer[]{1,2,3});
        check(5, String.valueOf(lowestCommonAncestor(t5, t5.left, t5.right).val), "1");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static TreeNode buildTree(Integer[] vals) {
        if (vals == null || vals.length == 0 || vals[0] == null) return null;
        TreeNode root = new TreeNode(vals[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int i = 1;
        while (!q.isEmpty() && i < vals.length) {
            TreeNode node = q.poll();
            if (i < vals.length && vals[i] != null) { node.left = new TreeNode(vals[i]); q.add(node.left); }
            i++;
            if (i < vals.length && vals[i] != null) { node.right = new TreeNode(vals[i]); q.add(node.right); }
            i++;
        }
        return root;
    }

    static TreeNode findNode(TreeNode root, int val) {
        if (root == null) return null;
        if (root.val == val) return root;
        TreeNode left = findNode(root.left, val);
        return left != null ? left : findNode(root.right, val);
    }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // Code here ...
        return null;
    }
}
```

---

## Bài 5: Binary Tree Level Order Traversal
**Độ khó: Trung bình**

Duyệt cây theo từng tầng, trả về danh sách các tầng.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [3,9,20,null,null,15,7] | [[3],[9,20],[15,7]] |
| 2 | [1] | [[1]] |
| 3 | [] | [] |
| 4 | [1,2,3,4,5] | [[1],[2,3],[4,5]] |
| 5 | [1,null,2,null,3] | [[1],[2],[3]] |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        check(1, levelOrder(buildTree(new Integer[]{3,9,20,null,null,15,7})).toString(), "[[3], [9, 20], [15, 7]]");
        check(2, levelOrder(buildTree(new Integer[]{1})).toString(), "[[1]]");
        check(3, levelOrder(null).toString(), "[]");
        check(4, levelOrder(buildTree(new Integer[]{1,2,3,4,5})).toString(), "[[1], [2, 3], [4, 5]]");
        check(5, levelOrder(buildTree(new Integer[]{1,null,2,null,3})).toString(), "[[1], [2], [3]]");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static TreeNode buildTree(Integer[] vals) {
        if (vals == null || vals.length == 0 || vals[0] == null) return null;
        TreeNode root = new TreeNode(vals[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int i = 1;
        while (!q.isEmpty() && i < vals.length) {
            TreeNode node = q.poll();
            if (i < vals.length && vals[i] != null) { node.left = new TreeNode(vals[i]); q.add(node.left); }
            i++;
            if (i < vals.length && vals[i] != null) { node.right = new TreeNode(vals[i]); q.add(node.right); }
            i++;
        }
        return root;
    }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static List<List<Integer>> levelOrder(TreeNode root) {
        // Code here ...
        return new ArrayList<>();
    }
}
```

---

## Bài 6: Kth Smallest Element in BST
**Độ khó: Trung bình**

Tìm phần tử nhỏ thứ k trong BST.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [3,1,4,null,2], k=1 | 1 |
| 2 | [5,3,6,2,4,null,null,1], k=3 | 3 |
| 3 | [1], k=1 | 1 |
| 4 | [2,1,3], k=2 | 2 |
| 5 | [5,3,6,2,4,null,null,1], k=6 | 6 |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        check(1, String.valueOf(kthSmallest(buildTree(new Integer[]{3,1,4,null,2}), 1)), "1");
        check(2, String.valueOf(kthSmallest(buildTree(new Integer[]{5,3,6,2,4,null,null,1}), 3)), "3");
        check(3, String.valueOf(kthSmallest(buildTree(new Integer[]{1}), 1)), "1");
        check(4, String.valueOf(kthSmallest(buildTree(new Integer[]{2,1,3}), 2)), "2");
        check(5, String.valueOf(kthSmallest(buildTree(new Integer[]{5,3,6,2,4,null,null,1}), 6)), "6");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static TreeNode buildTree(Integer[] vals) {
        if (vals == null || vals.length == 0 || vals[0] == null) return null;
        TreeNode root = new TreeNode(vals[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int i = 1;
        while (!q.isEmpty() && i < vals.length) {
            TreeNode node = q.poll();
            if (i < vals.length && vals[i] != null) { node.left = new TreeNode(vals[i]); q.add(node.left); }
            i++;
            if (i < vals.length && vals[i] != null) { node.right = new TreeNode(vals[i]); q.add(node.right); }
            i++;
        }
        return root;
    }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int kthSmallest(TreeNode root, int k) {
        // Code here ...
        return 0;
    }
}
```

---

## Bài 7: Serialize and Deserialize Binary Tree
**Độ khó: Khó**

Chuyển cây nhị phân thành chuỗi (serialize) và từ chuỗi khôi phục lại cây (deserialize).

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [1,2,3,null,null,4,5] | [1,2,3,null,null,4,5] |
| 2 | [] | null |
| 3 | [1] | [1] |
| 4 | [1,2] | [1,2] |
| 5 | [1,null,2,null,3] | [1,null,2,null,3] |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        TreeNode t1 = buildTree(new Integer[]{1,2,3,null,null,4,5});
        check(1, treeToString(deserialize(serialize(t1))), treeToString(t1));

        check(2, serialize(null).contains("null") || serialize(null).equals("[]") ? "ok" : "ok", "ok");

        TreeNode t3 = buildTree(new Integer[]{1});
        check(3, treeToString(deserialize(serialize(t3))), "[1]");

        TreeNode t4 = buildTree(new Integer[]{1,2});
        check(4, treeToString(deserialize(serialize(t4))), "[1,2]");

        TreeNode t5 = buildTree(new Integer[]{1,null,2,null,3});
        check(5, treeToString(deserialize(serialize(t5))), "[1,null,2,null,3]");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static TreeNode buildTree(Integer[] vals) {
        if (vals == null || vals.length == 0 || vals[0] == null) return null;
        TreeNode root = new TreeNode(vals[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int i = 1;
        while (!q.isEmpty() && i < vals.length) {
            TreeNode node = q.poll();
            if (i < vals.length && vals[i] != null) { node.left = new TreeNode(vals[i]); q.add(node.left); }
            i++;
            if (i < vals.length && vals[i] != null) { node.right = new TreeNode(vals[i]); q.add(node.right); }
            i++;
        }
        return root;
    }

    static String treeToString(TreeNode root) {
        if (root == null) return "null";
        List<String> result = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node == null) { result.add("null"); continue; }
            result.add(String.valueOf(node.val));
            q.add(node.left); q.add(node.right);
        }
        while (result.size() > 0 && result.get(result.size()-1).equals("null")) result.remove(result.size()-1);
        return "[" + String.join(",", result) + "]";
    }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static String serialize(TreeNode root) {
        // Code here ...
        return "";
    }

    public static TreeNode deserialize(String data) {
        // Code here ...
        return null;
    }
}
```

---

## Bài 8: Binary Tree Maximum Path Sum
**Độ khó: Khó**

Tìm tổng đường đi lớn nhất trong cây nhị phân.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [1,2,3] | 6 |
| 2 | [-10,9,20,null,null,15,7] | 42 |
| 3 | [-3] | -3 |
| 4 | [2,-1] | 2 |
| 5 | [1,-2,3] | 4 |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        check(1, String.valueOf(maxPathSum(buildTree(new Integer[]{1,2,3}))), "6");
        check(2, String.valueOf(maxPathSum(buildTree(new Integer[]{-10,9,20,null,null,15,7}))), "42");
        check(3, String.valueOf(maxPathSum(buildTree(new Integer[]{-3}))), "-3");
        check(4, String.valueOf(maxPathSum(buildTree(new Integer[]{2,-1}))), "2");
        check(5, String.valueOf(maxPathSum(buildTree(new Integer[]{1,-2,3}))), "4");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static TreeNode buildTree(Integer[] vals) {
        if (vals == null || vals.length == 0 || vals[0] == null) return null;
        TreeNode root = new TreeNode(vals[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int i = 1;
        while (!q.isEmpty() && i < vals.length) {
            TreeNode node = q.poll();
            if (i < vals.length && vals[i] != null) { node.left = new TreeNode(vals[i]); q.add(node.left); }
            i++;
            if (i < vals.length && vals[i] != null) { node.right = new TreeNode(vals[i]); q.add(node.right); }
            i++;
        }
        return root;
    }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int maxPathSum(TreeNode root) {
        // Code here ...
        return 0;
    }
}
```
