# 03. C++ STL Containers

STL gồm container, iterator, algorithm, function object. Khi phỏng vấn, điều quan trọng là biết chọn container theo thao tác chính.

## vector

Mảng động liên tiếp trong memory.

```cpp
#include <iostream>
#include <vector>
using namespace std;

int main() {
    vector<int> nums = {1, 2, 3};
    nums.push_back(4);

    for (int x : nums) {
        cout << x << " ";
    }
}
```

Đặc điểm:

- Truy cập index `O(1)`.
- Thêm/xóa cuối trung bình `O(1)`.
- Chèn/xóa giữa `O(n)`.
- Tốt cho cache vì memory liên tiếp.

## list

Danh sách liên kết đôi.

```cpp
#include <list>
#include <iostream>
using namespace std;

int main() {
    list<int> a = {1, 3, 4};
    auto it = next(a.begin());
    a.insert(it, 2); // 1 2 3 4
}
```

Đặc điểm:

- Chèn/xóa tại vị trí đã có iterator: `O(1)`.
- Không truy cập index trực tiếp.
- Tốn thêm memory cho pointer.
- Cache kém hơn vector.

## deque

Double-ended queue (hàng đợi hai đầu), thêm/xóa nhanh ở đầu và cuối.

```cpp
deque<int> q;
q.push_front(1);
q.push_back(2);
```

## stack, queue, priority_queue

Adapter container.

```cpp
stack<int> st;      // LIFO
queue<int> q;       // FIFO
priority_queue<int> pq; // max heap mặc định
```

## set, map

Thường cài đặt bằng balanced binary tree (cây nhị phân cân bằng).

```cpp
map<string, int> age;
age["An"] = 20;

set<int> s = {3, 1, 2}; // tự sắp xếp: 1 2 3
```

Đặc điểm:

- Tìm/chèn/xóa `O(log n)`.
- Phần tử được sắp xếp.
- `map`: key duy nhất.
- `multimap`: key có thể trùng.
- `set`: value duy nhất.
- `multiset`: value có thể trùng.

## unordered_map, unordered_set

Hash table, có từ C++11.

```cpp
#include <unordered_map>
#include <iostream>
using namespace std;

int main() {
    unordered_map<string, int> freq;
    freq["cpp"]++;
    freq["cpp"]++;
    cout << freq["cpp"]; // 2
}
```

Đặc điểm:

- Tìm/chèn/xóa trung bình `O(1)`, xấu nhất `O(n)`.
- Không sắp xếp.
- Cần hash function.
- Phù hợp để đếm tần suất, kiểm tra tồn tại nhanh.

## So sánh nhanh

| Container | Truy cập | Tìm kiếm | Chèn/xóa | Khi nên dùng |
|---|---:|---:|---:|---|
| `vector` | `O(1)` index | `O(n)` | cuối `O(1)`, giữa `O(n)` | danh sách hay duyệt, cần nhanh cache |
| `list` | `O(n)` | `O(n)` | `O(1)` nếu có iterator | chèn/xóa nhiều ở giữa |
| `map/set` | theo key | `O(log n)` | `O(log n)` | cần sắp xếp |
| `unordered_map/set` | theo key | avg `O(1)` | avg `O(1)` | tra cứu nhanh, không cần thứ tự |
| `queue/stack` | đầu/cuối | không phù hợp | `O(1)` | FIFO/LIFO |
| `priority_queue` | top | không phù hợp | `O(log n)` | lấy max/min nhanh |

## Algorithm hay dùng

```cpp
#include <algorithm>
#include <vector>
using namespace std;

vector<int> v = {4, 1, 3, 2};
sort(v.begin(), v.end());

auto it = find(v.begin(), v.end(), 3);

bool ok = binary_search(v.begin(), v.end(), 2);
```

Lưu ý: `binary_search` chỉ dùng khi range đã sắp xếp.

## Cách chọn container khi phỏng vấn

Khi được hỏi "nên dùng container nào", đừng trả lời bằng cảm tính. Hãy hỏi hoặc tự xác định:

- Có cần giữ thứ tự insert không?
- Có cần truy cập theo index không?
- Thao tác chính là tìm kiếm, chèn/xóa, hay duyệt tuần tự?
- Có cần dữ liệu luôn sorted không?
- Key có hash tốt không?
- Có cần iterator/reference không bị invalid sau khi insert/erase không?

Ví dụ trả lời:

- Cần duyệt nhiều, append cuối nhiều, truy cập index: `vector`.
- Cần queue hai đầu: `deque`.
- Cần sorted key và range query: `map`/`set`.
- Cần lookup nhanh, không cần sorted: `unordered_map`/`unordered_set`.
- Cần lấy phần tử lớn nhất/nhỏ nhất liên tục: `priority_queue`.

## Iterator invalidation

Đây là phần rất hay bị hỏi vì liên quan bug runtime.

| Container | Insert có thể làm invalid iterator? | Erase làm invalid gì? |
|---|---|---|
| `vector` | Có, nếu reallocate thì mọi iterator/reference invalid | Iterator từ điểm xóa trở về sau invalid |
| `deque` | Có thể invalid nhiều iterator | Iterator tới phần tử bị xóa invalid |
| `list` | Không invalid iterator khác | Chỉ iterator tới phần tử bị xóa invalid |
| `map/set` | Không invalid iterator khác | Chỉ iterator tới phần tử bị xóa invalid |
| `unordered_map/set` | Rehash làm invalid iterator | Chỉ phần tử bị xóa invalid, nhưng rehash là ngoại lệ |

Ví dụ lỗi với `vector`:

```cpp
vector<int> v = {1, 2, 3};
auto it = v.begin();
v.push_back(4); // nếu vector reallocate, it có thể invalid
cout << *it;    // nguy hiểm
```

Nếu biết trước số lượng phần tử, dùng `reserve`:

```cpp
vector<int> v;
v.reserve(1000);
```

`reserve` cấp capacity trước, giảm số lần reallocation.

## vector: size vs capacity

- `size()`: số phần tử hiện có.
- `capacity()`: số phần tử có thể chứa trước khi phải cấp phát lại.

```cpp
vector<int> v;
v.reserve(10);
cout << v.size();     // 0
cout << v.capacity(); // ít nhất 10
```

`resize(10)` khác `reserve(10)`: `resize` thật sự tạo 10 phần tử, còn `reserve` chỉ chuẩn bị bộ nhớ.

## map vs unordered_map

`unordered_map` thường nhanh hơn cho lookup trung bình, nhưng không luôn là lựa chọn tốt nhất.

| Tiêu chí | `map` | `unordered_map` |
|---|---|---|
| Cấu trúc | balanced tree | hash table |
| Thứ tự key | sorted | không đảm bảo |
| Lookup | `O(log n)` | average `O(1)`, worst `O(n)` |
| Range query | tốt | không phù hợp |
| Custom key | cần comparator | cần hash + equality |

Ví dụ range query cần `map`:

```cpp
map<int, string> users;
auto it = users.lower_bound(100);
```

`lower_bound` tìm key đầu tiên không nhỏ hơn target.

## priority_queue: min heap

Mặc định `priority_queue<int>` là max heap. Muốn min heap:

```cpp
priority_queue<int, vector<int>, greater<int>> pq;
pq.push(3);
pq.push(1);
cout << pq.top(); // 1
```

Với struct:

```cpp
struct Job {
    int priority;
    string name;
};

struct CompareJob {
    bool operator()(const Job& a, const Job& b) const {
        return a.priority > b.priority; // priority nhỏ hơn đứng trước
    }
};

priority_queue<Job, vector<Job>, CompareJob> jobs;
```

## erase khi đang duyệt

Với `vector`, cách an toàn:

```cpp
for (auto it = v.begin(); it != v.end(); ) {
    if (*it % 2 == 0) {
        it = v.erase(it);
    } else {
        ++it;
    }
}
```

Với C++20, có thể dùng:

```cpp
erase_if(v, [](int x) { return x % 2 == 0; });
```

## Emplace vs push

`push_back` nhận object đã tạo, `emplace_back` xây object trực tiếp trong container.

```cpp
vector<pair<int, string>> v;
v.push_back({1, "one"});
v.emplace_back(2, "two");
```

Trong nhiều trường hợp compiler tối ưu tốt, nhưng hiểu khác biệt giúp trả lời câu hỏi hiệu năng.

## Câu hỏi bẫy thường gặp

1. `unordered_map` có luôn `O(1)` không?

Không. Trung bình `O(1)`, xấu nhất `O(n)` nếu nhiều collision hoặc hash kém.

2. Vì sao `list` chèn/xóa giữa `O(1)` nhưng không phải lúc nào cũng nhanh?

Vì phải có iterator tới vị trí đó. Nếu phải tìm từ đầu thì vẫn `O(n)`. Ngoài ra list cache locality kém.

3. `vector` có phù hợp mặc định không?

Có, trong C++ hiện đại `vector` thường là lựa chọn mặc định tốt vì memory liên tiếp, cache tốt, API đơn giản. Chỉ đổi container khi thao tác chính yêu cầu cấu trúc khác.
