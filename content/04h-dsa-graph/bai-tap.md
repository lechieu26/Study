# Graph - Bài Tập

## Bài 1: Đếm Số Đảo (Number of Islands)
**Độ khó: Trung bình**

Cho ma trận 2D gồm '1' (đất) và '0' (nước), đếm số đảo.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [["1","1","0","0","0"],["1","1","0","0","0"],["0","0","1","0","0"],["0","0","0","1","1"]] | 3 |
| 2 | [["1","1","1"],["0","1","0"],["1","1","1"]] | 1 |
| 3 | [["0","0"],["0","0"]] | 0 |
| 4 | [["1"]] | 1 |
| 5 | [["1","0","1"],["0","0","0"],["1","0","1"]] | 4 |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(numIslands(new char[][]{
            {'1','1','0','0','0'},{'1','1','0','0','0'},
            {'0','0','1','0','0'},{'0','0','0','1','1'}})), "3");

        check(2, String.valueOf(numIslands(new char[][]{
            {'1','1','1'},{'0','1','0'},{'1','1','1'}})), "1");

        check(3, String.valueOf(numIslands(new char[][]{
            {'0','0'},{'0','0'}})), "0");

        check(4, String.valueOf(numIslands(new char[][]{{'1'}})), "1");

        check(5, String.valueOf(numIslands(new char[][]{
            {'1','0','1'},{'0','0','0'},{'1','0','1'}})), "4");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int numIslands(char[][] grid) {
        // Code here ...
        return 0;
    }
}
```

---

## Bài 2: Clone Graph
**Độ khó: Trung bình**

Cho một node trong undirected graph, tạo deep copy toàn bộ graph.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [[2,4],[1,3],[2,4],[1,3]] | cloned correctly |
| 2 | [[]] | 1 node |
| 3 | [] | null |
| 4 | [[2],[1]] | 2 nodes |
| 5 | [[2,3],[1,3],[1,2]] | 3 nodes |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    static class Node {
        public int val;
        public List<Node> neighbors;
        public Node(int val) { this.val = val; this.neighbors = new ArrayList<>(); }
    }

    public static void main(String[] args) {
        // Test 1: 4-node graph
        Node n1 = new Node(1); Node n2 = new Node(2); Node n3 = new Node(3); Node n4 = new Node(4);
        n1.neighbors.addAll(Arrays.asList(n2,n4)); n2.neighbors.addAll(Arrays.asList(n1,n3));
        n3.neighbors.addAll(Arrays.asList(n2,n4)); n4.neighbors.addAll(Arrays.asList(n1,n3));
        Node c1 = cloneGraph(n1);
        check(1, String.valueOf(c1 != n1 && c1.val == 1 && c1.neighbors.size() == 2), "true");

        // Test 2: single node
        Node s = new Node(1);
        Node cs = cloneGraph(s);
        check(2, String.valueOf(cs != s && cs.val == 1), "true");

        // Test 3: null
        check(3, String.valueOf(cloneGraph(null) == null), "true");

        // Test 4: 2 nodes
        Node a = new Node(1); Node b = new Node(2);
        a.neighbors.add(b); b.neighbors.add(a);
        Node ca = cloneGraph(a);
        check(4, String.valueOf(ca != a && ca.neighbors.size() == 1), "true");

        // Test 5: 3 nodes
        Node x = new Node(1); Node y = new Node(2); Node z = new Node(3);
        x.neighbors.addAll(Arrays.asList(y,z)); y.neighbors.addAll(Arrays.asList(x,z)); z.neighbors.addAll(Arrays.asList(x,y));
        Node cx = cloneGraph(x);
        check(5, String.valueOf(cx != x && cx.neighbors.size() == 2), "true");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static Node cloneGraph(Node node) {
        // Code here ...
        return null;
    }
}
```

---

## Bài 3: Course Schedule (Topological Sort)
**Độ khó: Trung bình**

Cho n khóa học và danh sách điều kiện tiên quyết. Kiểm tra có thể hoàn thành tất cả không.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | n=2, [[1,0]] | true |
| 2 | n=2, [[1,0],[0,1]] | false |
| 3 | n=4, [[1,0],[2,0],[3,1],[3,2]] | true |
| 4 | n=1, [] | true |
| 5 | n=3, [[0,1],[1,2],[2,0]] | false |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(canFinish(2, new int[][]{{1,0}})), "true");
        check(2, String.valueOf(canFinish(2, new int[][]{{1,0},{0,1}})), "false");
        check(3, String.valueOf(canFinish(4, new int[][]{{1,0},{2,0},{3,1},{3,2}})), "true");
        check(4, String.valueOf(canFinish(1, new int[][]{})), "true");
        check(5, String.valueOf(canFinish(3, new int[][]{{0,1},{1,2},{2,0}})), "false");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        // Code here ...
        return false;
    }
}
```

---

## Bài 4: Đường Đi Ngắn Nhất trong Mê Cung (BFS)
**Độ khó: Trung bình**

Cho mê cung ma trận 2D (0=đường, 1=tường), tìm đường ngắn nhất từ (0,0) đến (m-1,n-1). Trả về -1 nếu không thể.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [[0,0,0,0],[1,1,0,1],[0,0,0,0],[0,1,1,0]] | 7 |
| 2 | [[0,1],[0,0]] | 3 |
| 3 | [[0]] | 1 |
| 4 | [[0,1],[1,0]] | -1 |
| 5 | [[0,0],[0,0]] | 3 |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(shortestPath(new int[][]{{0,0,0,0},{1,1,0,1},{0,0,0,0},{0,1,1,0}})), "7");
        check(2, String.valueOf(shortestPath(new int[][]{{0,1},{0,0}})), "3");
        check(3, String.valueOf(shortestPath(new int[][]{{0}})), "1");
        check(4, String.valueOf(shortestPath(new int[][]{{0,1},{1,0}})), "-1");
        check(5, String.valueOf(shortestPath(new int[][]{{0,0},{0,0}})), "3");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int shortestPath(int[][] grid) {
        // Code here ...
        return -1;
    }
}
```

---

## Bài 5: Network Delay Time (Dijkstra)
**Độ khó: Trung bình**

Cho đồ thị có hướng có trọng số, gửi tín hiệu từ node k. Tìm thời gian để tất cả node nhận được.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | times=[[2,1,1],[2,3,1],[3,4,1]], n=4, k=2 | 2 |
| 2 | times=[[1,2,1]], n=2, k=1 | 1 |
| 3 | times=[[1,2,1]], n=2, k=2 | -1 |
| 4 | times=[[1,2,1],[2,3,2],[1,3,4]], n=3, k=1 | 3 |
| 5 | times=[[1,2,1],[2,1,1]], n=2, k=1 | 1 |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(networkDelayTime(new int[][]{{2,1,1},{2,3,1},{3,4,1}}, 4, 2)), "2");
        check(2, String.valueOf(networkDelayTime(new int[][]{{1,2,1}}, 2, 1)), "1");
        check(3, String.valueOf(networkDelayTime(new int[][]{{1,2,1}}, 2, 2)), "-1");
        check(4, String.valueOf(networkDelayTime(new int[][]{{1,2,1},{2,3,2},{1,3,4}}, 3, 1)), "3");
        check(5, String.valueOf(networkDelayTime(new int[][]{{1,2,1},{2,1,1}}, 2, 1)), "1");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int networkDelayTime(int[][] times, int n, int k) {
        // Code here ...
        return -1;
    }
}
```

---

## Bài 6: Word Ladder
**Độ khó: Khó**

Tìm chuỗi biến đổi ngắn nhất (mỗi bước đổi 1 ký tự).

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | begin="hit", end="cog", words=["hot","dot","dog","lot","log","cog"] | 5 |
| 2 | begin="hit", end="cog", words=["hot","dot","dog","lot","log"] | 0 |
| 3 | begin="a", end="c", words=["a","b","c"] | 2 |
| 4 | begin="hot", end="dog", words=["hot","dog"] | 0 |
| 5 | begin="abc", end="def", words=["abd","abe","abf","aef","def"] | 4 |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(ladderLength("hit", "cog", Arrays.asList("hot","dot","dog","lot","log","cog"))), "5");
        check(2, String.valueOf(ladderLength("hit", "cog", Arrays.asList("hot","dot","dog","lot","log"))), "0");
        check(3, String.valueOf(ladderLength("a", "c", Arrays.asList("a","b","c"))), "2");
        check(4, String.valueOf(ladderLength("hot", "dog", Arrays.asList("hot","dog"))), "0");
        check(5, String.valueOf(ladderLength("abc", "def", Arrays.asList("abd","abe","abf","aef","def"))), "4");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // Code here ...
        return 0;
    }
}
```
