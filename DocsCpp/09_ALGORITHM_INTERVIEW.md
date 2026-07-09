# 09. Algorithm Interview Questions

## Bài 1. Fibonacci bằng đệ quy và vòng lặp

### Đệ quy đơn giản

```cpp
int fib(int n) {
    if (n <= 1) return n;
    return fib(n - 1) + fib(n - 2);
}
```

Độ phức tạp: `O(2^n)`, vì tính lại nhiều giá trị cũ.

### Tối ưu đệ quy bằng memoization (lưu trữ kết quả trung gian)

Dùng mảng/vector lưu kết quả đã tính.

```cpp
#include <vector>
using namespace std;

long long fibMemo(int n, vector<long long>& memo) {
    if (n <= 1) return n;
    if (memo[n] != -1) return memo[n];

    memo[n] = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
    return memo[n];
}

long long fibonacci(int n) {
    vector<long long> memo(n + 1, -1);
    return fibMemo(n, memo);
}
```

Độ phức tạp: `O(n)` time, `O(n)` memory.

### Vòng lặp

```cpp
long long fibLoop(int n) {
    if (n <= 1) return n;

    long long prev = 0;
    long long cur = 1;

    for (int i = 2; i <= n; ++i) {
        long long next = prev + cur;
        prev = cur;
        cur = next;
    }

    return cur;
}
```

Độ phức tạp: `O(n)` time, `O(1)` memory.

## Bài 2. Tìm nhanh một số trong dãy nhập từ bàn phím

Nếu cần tìm nhiều lần, lưu vào hash table (`unordered_set`) để query (truy vấn) trung bình `O(1)`.

```cpp
#include <iostream>
#include <unordered_set>
#include <vector>
using namespace std;

int main() {
    int n;
    cin >> n;

    unordered_set<int> index;
    for (int i = 0; i < n; ++i) {
        int x;
        cin >> x;
        index.insert(x);
    }

    int target;
    cin >> target;

    if (index.count(target)) {
        cout << "Found\n";
    } else {
        cout << "Not found\n";
    }
}
```

Nếu cần lưu vị trí của mỗi số:

```cpp
unordered_map<int, vector<int>> positions;

for (int i = 0; i < n; ++i) {
    int x;
    cin >> x;
    positions[x].push_back(i);
}
```

## Bài 3. Tìm substring trong string

Cách dễ hiểu: `string::find`.

```cpp
string s = "hello world";
string sub = "world";

size_t pos = s.find(sub);
if (pos != string::npos) {
    cout << pos; // 6
}
```

Nếu phỏng vấn hỏi thuật toán nhanh, có thể dùng KMP `O(n + m)` hoặc Rabin-Karp dùng hashing.

### Rabin-Karp ý tưởng hash

```cpp
#include <iostream>
#include <string>
using namespace std;

int findSubstring(const string& text, const string& pattern) {
    int n = text.size();
    int m = pattern.size();
    if (m == 0) return 0;
    if (m > n) return -1;

    const long long base = 256;
    const long long mod = 1000000007;

    long long patternHash = 0;
    long long windowHash = 0;
    long long power = 1;

    for (int i = 0; i < m; ++i) {
        patternHash = (patternHash * base + pattern[i]) % mod;
        windowHash = (windowHash * base + text[i]) % mod;
        if (i < m - 1) power = (power * base) % mod;
    }

    for (int i = 0; i <= n - m; ++i) {
        if (patternHash == windowHash && text.substr(i, m) == pattern) {
            return i;
        }

        if (i < n - m) {
            windowHash = (windowHash - text[i] * power % mod + mod) % mod;
            windowHash = (windowHash * base + text[i + m]) % mod;
        }
    }

    return -1;
}
```

Cần so sánh lại string khi hash bằng nhau vì có thể xảy ra collision (xung đột hash).

## Bài 4. Linked list một chiều bị cycle, tìm node bắt đầu cycle

Dùng Floyd cycle detection (phát hiện chu kỳ Floyd): slow đi 1 bước, fast đi 2 bước.

```cpp
struct Node {
    int value;
    Node* next;
};

Node* detectCycleStart(Node* head) {
    Node* slow = head;
    Node* fast = head;

    while (fast != nullptr && fast->next != nullptr) {
        slow = slow->next;
        fast = fast->next->next;

        if (slow == fast) {
            Node* p = head;
            while (p != slow) {
                p = p->next;
                slow = slow->next;
            }
            return p;
        }
    }

    return nullptr;
}
```

Giải thích ngắn:

- Nếu có cycle, slow và fast sẽ gặp nhau trong cycle.
- Đưa một con trỏ về head, giữ con trỏ kia tại điểm gặp.
- Cho cả hai đi từng bước, điểm gặp tiếp theo là node bắt đầu cycle.

Độ phức tạp: `O(n)` time, `O(1)` memory.

## Checklist trả lời phỏng vấn

- Fibonacci: đệ quy thuần chậm `O(2^n)`, memo/loop `O(n)`.
- Tìm số nhanh: hash table, `unordered_set`/`unordered_map`.
- Tìm substring: built-in `find`, KMP, Rabin-Karp.
- Linked list cycle: Floyd slow-fast pointer.

## Cách trình bày lời giải trong phỏng vấn

Một format trả lời tốt:

1. Nhắc lại bài toán và constraint.
2. Đưa brute force trước nếu tự nhiên.
3. Phân tích bottleneck.
4. Đề xuất tối ưu bằng data structure/algorithm.
5. Nêu độ phức tạp time/space.
6. Nói edge cases.
7. Code sạch, đặt tên rõ.

Ví dụ với bài tìm số:

- Nếu chỉ query một lần, linear scan `O(n)` có thể đủ.
- Nếu query nhiều lần, preprocess vào `unordered_set` tốn `O(n)` memory, mỗi query average `O(1)`.

Điểm cộng là bạn không nhảy ngay vào hash table khi chưa biết số lượng query.

## Big O cần nắm chắc

| Độ phức tạp | Ý nghĩa | Ví dụ |
|---|---|---|
| `O(1)` | hằng số | truy cập `vector[i]` |
| `O(log n)` | chia đôi mỗi bước | binary search |
| `O(n)` | duyệt một lần | tìm max |
| `O(n log n)` | sort hiệu quả | merge sort, heap sort |
| `O(n^2)` | hai vòng lặp | so sánh mọi cặp |
| `O(2^n)` | thử mọi subset | backtracking subset |

Khi phân tích, bỏ hằng số và lower-order term: `O(2n + 10)` thành `O(n)`.

## Bài 5. Two Sum

Cho mảng `nums` và target, tìm hai index sao cho tổng bằng target.

### Brute force

Hai vòng lặp:

```cpp
for (int i = 0; i < n; ++i) {
    for (int j = i + 1; j < n; ++j) {
        if (nums[i] + nums[j] == target) {
            return {i, j};
        }
    }
}
```

Time `O(n^2)`, space `O(1)`.

### Hash map

```cpp
vector<int> twoSum(const vector<int>& nums, int target) {
    unordered_map<int, int> seen; // value -> index

    for (int i = 0; i < (int)nums.size(); ++i) {
        int need = target - nums[i];
        if (seen.count(need)) {
            return {seen[need], i};
        }
        seen[nums[i]] = i;
    }

    return {};
}
```

Time average `O(n)`, space `O(n)`.

Edge cases:

- Có số âm.
- Có duplicate.
- Không có answer.
- Target overflow nếu số rất lớn, có thể dùng `long long`.

## Bài 6. Binary Search

Binary search dùng khi search space có tính đơn điệu.

```cpp
int binarySearch(const vector<int>& a, int target) {
    int left = 0;
    int right = (int)a.size() - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;

        if (a[mid] == target) return mid;
        if (a[mid] < target) left = mid + 1;
        else right = mid - 1;
    }

    return -1;
}
```

Tại sao không viết `(left + right) / 2`? Vì `left + right` có thể overflow với index lớn.

### Lower bound tự viết

Tìm vị trí đầu tiên `a[i] >= target`.

```cpp
int lowerBound(const vector<int>& a, int target) {
    int left = 0;
    int right = a.size(); // nửa mở [left, right)

    while (left < right) {
        int mid = left + (right - left) / 2;
        if (a[mid] < target) {
            left = mid + 1;
        } else {
            right = mid;
        }
    }

    return left;
}
```

Nắm template nửa mở `[left, right)` sẽ giảm lỗi off-by-one.

## Bài 7. Sliding Window

Dùng cho bài toán subarray/substring liên tiếp.

Ví dụ: độ dài substring dài nhất không có ký tự lặp.

```cpp
int lengthOfLongestSubstring(const string& s) {
    vector<int> last(256, -1);
    int left = 0;
    int best = 0;

    for (int right = 0; right < (int)s.size(); ++right) {
        unsigned char ch = s[right];
        if (last[ch] >= left) {
            left = last[ch] + 1;
        }
        last[ch] = right;
        best = max(best, right - left + 1);
    }

    return best;
}
```

Time `O(n)`, space `O(1)` nếu charset cố định.

Khi nào dùng sliding window:

- Bài có "contiguous subarray/substring".
- Có thể mở rộng `right` và co `left`.
- State trong window cập nhật được nhanh.

## Bài 8. Reverse linked list

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

Giải thích:

- `prev` là phần list đã đảo.
- `cur` là node đang xử lý.
- Lưu `next` trước khi đổi `cur->next`, nếu không sẽ mất phần còn lại.

Time `O(n)`, space `O(1)`.

## Bài 9. BFS và DFS

### BFS

BFS duyệt theo tầng, thường dùng tìm shortest path trên graph không trọng số.

```cpp
vector<int> bfs(int start, const vector<vector<int>>& graph) {
    vector<int> dist(graph.size(), -1);
    queue<int> q;

    dist[start] = 0;
    q.push(start);

    while (!q.empty()) {
        int u = q.front();
        q.pop();

        for (int v : graph[u]) {
            if (dist[v] == -1) {
                dist[v] = dist[u] + 1;
                q.push(v);
            }
        }
    }

    return dist;
}
```

### DFS

DFS đi sâu trước, thường dùng connected components, cycle detection, topological sort.

```cpp
void dfs(int u, const vector<vector<int>>& graph, vector<bool>& visited) {
    visited[u] = true;
    for (int v : graph[u]) {
        if (!visited[v]) {
            dfs(v, graph, visited);
        }
    }
}
```

Với graph rất sâu, recursive DFS có thể stack overflow; dùng iterative DFS bằng stack.

## Bài 10. Dynamic Programming cơ bản

DP dùng khi bài có:

- Optimal substructure: lời giải bài lớn xây từ bài nhỏ.
- Overlapping subproblems: các bài nhỏ bị tính lặp lại.

Ví dụ climbing stairs:

```cpp
int climbStairs(int n) {
    if (n <= 2) return n;

    int a = 1;
    int b = 2;

    for (int i = 3; i <= n; ++i) {
        int c = a + b;
        a = b;
        b = c;
    }

    return b;
}
```

State: `dp[i]` là số cách lên bậc `i`.

Transition: `dp[i] = dp[i - 1] + dp[i - 2]`.

Tối ưu memory vì chỉ cần hai state trước.

## Checklist edge cases

- Input rỗng.
- Một phần tử.
- Duplicate.
- Số âm/số 0.
- Overflow.
- Index đầu/cuối.
- Không tìm thấy answer.
- Graph disconnected.
- Linked list không có cycle hoặc cycle bắt đầu tại head.

## Cách luyện hiệu quả

- Với mỗi bài, tự viết brute force trước.
- Tự nói to độ phức tạp.
- Tìm pattern: hash, two pointers, sliding window, binary search, BFS/DFS, DP.
- Sau khi AC, viết lại lời giải trong 5-7 dòng để nhớ insight.
