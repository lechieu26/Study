# Hash Table (Bảng băm)

## 1. Khái niệm

**Hash Table** là cấu trúc dữ liệu lưu trữ cặp **key-value**, cho phép truy cập, thêm, xóa trong **O(1) trung bình** nhờ **hash function**.

```
Key "apple"  → hashCode() → 97 → index = 97 % 16 = 1
Key "banana" → hashCode() → 93 → index = 93 % 16 = 13

Buckets:
[0]  → null
[1]  → ("apple", 5) → null
[2]  → null
...
[13] → ("banana", 3) → null
```

**Cơ chế hoạt động:**
1. Tính `hashCode()` của key
2. `index = hash & (capacity - 1)` — xác định bucket (dùng bitwise AND thay cho modulo)
3. Lưu entry vào bucket tại index
4. Nếu **collision** (hai key cùng index) → xử lý bằng chaining hoặc open addressing

## 2. Xử lý Collision

### Separate Chaining (Java HashMap)
Mỗi bucket là một linked list (hoặc tree khi nhiều collision).

```
Bucket[3]:  ("cat", 1) → ("car", 2) → null
```

**Java 8+:** Khi bucket có >8 entries → chuyển từ LinkedList sang **Red-Black Tree** → O(log n) thay vì O(n).

### Open Addressing
Tìm slot trống tiếp theo trong mảng (Linear Probing, Quadratic Probing, Double Hashing).

## 3. HashMap trong Java

```java
Map<String, Integer> map = new HashMap<>();

// Thao tác cơ bản
map.put("apple", 5);          // O(1) — thêm/cập nhật
map.get("apple");              // 5 — O(1)
map.getOrDefault("grape", 0);  // 0 — trả về default nếu không có
map.containsKey("apple");      // true — O(1)
map.containsValue(5);          // true — O(n) ⚠️
map.remove("apple");           // O(1)
map.size();                    // Số cặp key-value

// Duyệt
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}
for (String key : map.keySet()) { ... }
for (int value : map.values()) { ... }

// Phương thức hữu ích
map.merge("apple", 1, Integer::sum);           // Cộng thêm 1
map.computeIfAbsent("key", k -> new ArrayList<>());  // Tạo nếu chưa có
map.putIfAbsent("key", value);                 // Chỉ put nếu chưa có
```

## 4. Độ phức tạp

| Thao tác | Average | Worst |
|----------|---------|-------|
| `put(key, value)` | O(1) | O(n) hoặc O(log n)* |
| `get(key)` | O(1) | O(n) hoặc O(log n)* |
| `remove(key)` | O(1) | O(n) hoặc O(log n)* |
| `containsKey(key)` | O(1) | O(n) hoặc O(log n)* |
| `containsValue(val)` | O(n) | O(n) |

> (*) Java 8+ chuyển bucket sang TreeNode khi >8 entries.

## 5. HashSet

**HashSet** = HashMap mà chỉ lưu key (value là dummy object).

```java
Set<Integer> set = new HashSet<>();
set.add(1);           // O(1)
set.add(2);
set.contains(1);      // true — O(1)
set.remove(1);        // O(1)
set.size();

// Kiểm tra phần tử trùng lặp
public boolean containsDuplicate(int[] nums) {
    Set<Integer> seen = new HashSet<>();
    for (int num : nums) {
        if (!seen.add(num)) return true;  // add() return false nếu đã tồn tại
    }
    return false;
}
```

## 6. Các kỹ thuật quan trọng

### 6.1 Counting Pattern (Đếm tần suất)

```java
// Đếm tần suất ký tự
Map<Character, Integer> freq = new HashMap<>();
for (char c : text.toCharArray()) {
    freq.merge(c, 1, Integer::sum);
}

// Hoặc dùng getOrDefault
for (char c : text.toCharArray()) {
    freq.put(c, freq.getOrDefault(c, 0) + 1);
}
```

### 6.2 Two Sum (HashMap)

```java
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement)) {
            return new int[]{map.get(complement), i};
        }
        map.put(nums[i], i);
    }
    return new int[]{};
}
// O(n) time, O(n) space
```

### 6.3 Group Anagrams

```java
public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> map = new HashMap<>();
    for (String s : strs) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        String key = new String(chars);  // Sorted string làm key
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
    }
    return new ArrayList<>(map.values());
}
// ["eat","tea","tan","ate","nat","bat"]
// → [["eat","tea","ate"], ["tan","nat"], ["bat"]]
```

### 6.4 Longest Consecutive Sequence

```java
// Tìm dãy số liên tiếp dài nhất — O(n)
public int longestConsecutive(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for (int n : nums) set.add(n);
    int longest = 0;

    for (int num : set) {
        if (!set.contains(num - 1)) {  // Chỉ bắt đầu từ đầu dãy
            int length = 1;
            while (set.contains(num + length)) length++;
            longest = Math.max(longest, length);
        }
    }
    return longest;
}
// [100, 4, 200, 1, 3, 2] → longest = 4 (dãy 1,2,3,4)
```

## 7. Các biến thể HashMap

| Class | Thứ tự | Đặc điểm |
|-------|--------|----------|
| `HashMap` | Không đảm bảo | Nhanh nhất |
| `LinkedHashMap` | Theo thứ tự insert | Dùng cho LRU Cache |
| `TreeMap` | Theo thứ tự key (sorted) | O(log n), dùng Red-Black Tree |
| `ConcurrentHashMap` | Không đảm bảo | Thread-safe, segment locking |

```java
// LinkedHashMap — giữ thứ tự insert
Map<String, Integer> linked = new LinkedHashMap<>();
linked.put("c", 3); linked.put("a", 1); linked.put("b", 2);
// Duyệt: c→a→b (theo thứ tự insert)

// TreeMap — sorted by key
Map<String, Integer> tree = new TreeMap<>();
tree.put("c", 3); tree.put("a", 1); tree.put("b", 2);
// Duyệt: a→b→c (sorted)
tree.firstKey();     // "a"
tree.lastKey();      // "c"
tree.floorKey("b");  // "b" — key lớn nhất ≤ "b"
```

## 8. hashCode() và equals()

```java
// Quy tắc quan trọng khi dùng object làm key:
// 1. Nếu override equals(), PHẢI override hashCode()
// 2. a.equals(b) == true → a.hashCode() == b.hashCode()
// 3. Ngược lại không bắt buộc (collision là bình thường)

public class Point {
    int x, y;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point p)) return false;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
```

## 9. Khi nào dùng Hash Table?

| Dùng khi | Không nên dùng khi |
|---------|-------------------|
| Cần lookup O(1) theo key | Cần dữ liệu sorted → TreeMap |
| Đếm tần suất | Cần thứ tự insert → LinkedHashMap |
| Kiểm tra tồn tại (HashSet) | Dữ liệu rất nhỏ (overhead HashMap) |
| Two Sum, Group by key | Cần range query → TreeMap |
| Cache key-value | Multi-thread → ConcurrentHashMap |

> **Phỏng vấn thường hỏi:** Two Sum, Group Anagrams, Longest Consecutive Sequence, LRU Cache, Contains Duplicate, Valid Anagram, Top K Frequent Elements.
