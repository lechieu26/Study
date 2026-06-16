# Graph - Đáp Án Chi Tiết

## Bài 1: Number of Islands

### Cách 1: DFS O(m × n)
```java
public class NumberOfIslands {
    public static int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    dfs(grid, i, j);
                    count++;
                }
            }
        }
        return count;
    }

    private static void dfs(char[][] grid, int r, int c) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c] != '1') return;
        grid[r][c] = '0';  // Đánh dấu đã thăm
        dfs(grid, r + 1, c); dfs(grid, r - 1, c);
        dfs(grid, r, c + 1); dfs(grid, r, c - 1);
    }
}
```

### Cách 2: BFS
```java
public class NumberOfIslands_BFS {
    public static int numIslands(char[][] grid) {
        int count = 0;
        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    Queue<int[]> queue = new ArrayDeque<>();
                    queue.offer(new int[]{i, j});
                    grid[i][j] = '0';
                    while (!queue.isEmpty()) {
                        int[] cell = queue.poll();
                        for (int[] d : dirs) {
                            int nr = cell[0] + d[0], nc = cell[1] + d[1];
                            if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[0].length && grid[nr][nc] == '1') {
                                grid[nr][nc] = '0';
                                queue.offer(new int[]{nr, nc});
                            }
                        }
                    }
                    count++;
                }
            }
        }
        return count;
    }
}
```

---

## Bài 2: Clone Graph

### Cách 1: BFS + HashMap
```java
public class CloneGraph {
    public static Node cloneGraph(Node node) {
        if (node == null) return null;
        Map<Node, Node> map = new HashMap<>();
        Queue<Node> queue = new ArrayDeque<>();
        map.put(node, new Node(node.val));
        queue.offer(node);

        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            for (Node neighbor : curr.neighbors) {
                if (!map.containsKey(neighbor)) {
                    map.put(neighbor, new Node(neighbor.val));
                    queue.offer(neighbor);
                }
                map.get(curr).neighbors.add(map.get(neighbor));
            }
        }
        return map.get(node);
    }
}
```

---

## Bài 3: Course Schedule

### Cách 1: Kahn's Algorithm (BFS Topological Sort)
```java
public class CourseSchedule {
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        int[] inDegree = new int[numCourses];
        for (int[] p : prerequisites) {
            graph.computeIfAbsent(p[1], k -> new ArrayList<>()).add(p[0]);
            inDegree[p[0]]++;
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) queue.offer(i);
        }

        int count = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            count++;
            for (int next : graph.getOrDefault(course, List.of())) {
                if (--inDegree[next] == 0) queue.offer(next);
            }
        }
        return count == numCourses;
    }
}
```

---

## Bài 4: Mê Cung BFS

### Cách 1: BFS
```java
public class MazeBFS {
    public static int shortestPath(int[][] maze) {
        int m = maze.length, n = maze[0].length;
        if (maze[0][0] == 1 || maze[m-1][n-1] == 1) return -1;

        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, 0, 1});
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int r = cell[0], c = cell[1], dist = cell[2];
            if (r == m-1 && c == n-1) return dist;
            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < m && nc >= 0 && nc < n && !visited[nr][nc] && maze[nr][nc] == 0) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc, dist + 1});
                }
            }
        }
        return -1;
    }
}
```

---

## Bài 5: Network Delay Time

### Cách 1: Dijkstra O((V+E) log V)
```java
public class NetworkDelay {
    public static int networkDelayTime(int[][] times, int n, int k) {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] t : times) {
            graph.computeIfAbsent(t[0], x -> new ArrayList<>()).add(new int[]{t[1], t[2]});
        }

        Map<Integer, Integer> dist = new HashMap<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{k, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            if (dist.containsKey(curr[0])) continue;
            dist.put(curr[0], curr[1]);
            for (int[] edge : graph.getOrDefault(curr[0], List.of())) {
                if (!dist.containsKey(edge[0])) pq.offer(new int[]{edge[0], curr[1] + edge[1]});
            }
        }
        if (dist.size() != n) return -1;
        return Collections.max(dist.values());
    }
}
```

---

## Bài 6: Word Ladder

### Cách 1: BFS O(M² × N)
```java
public class WordLadder {
    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) return 0;

        Queue<String> queue = new ArrayDeque<>();
        queue.offer(beginWord);
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);
        int level = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String word = queue.poll();
                char[] chars = word.toCharArray();
                for (int j = 0; j < chars.length; j++) {
                    char original = chars[j];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) continue;
                        chars[j] = c;
                        String next = new String(chars);
                        if (next.equals(endWord)) return level + 1;
                        if (wordSet.contains(next) && visited.add(next)) queue.offer(next);
                    }
                    chars[j] = original;
                }
            }
            level++;
        }
        return 0;
    }
}
```
