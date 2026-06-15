# Collection Framework trong Java

## Mục lục

1. [Giới thiệu về Collection Framework](#1-giới-thiệu-về-collection-framework)
2. [Interface Collection](#2-interface-collection)
3. [List Interface](#3-list-interface)
4. [Set Interface](#4-set-interface)
5. [Queue Interface](#5-queue-interface)
6. [Map Interface](#6-map-interface)
7. [Iterators](#7-iterators)
8. [Comparable và Comparator](#8-comparable-và-comparator)
9. [Collections Utility Class](#9-collections-utility-class)
10. [Java 8+ Features trong Collection](#10-java-8-features-trong-collection)
11. [Performance Considerations](#11-performance-considerations)
12. [Best Practices](#12-best-practices)

---

## 1. Giới thiệu về Collection Framework

### 1.1. Collection Framework là gì?

**Collection Framework** là một kiến trúc thống nhất để biểu diễn và thao tác với các nhóm đối tượng (collections) trong Java. Nó cung cấp:

- **Interfaces**: Các kiểu dữ liệu trừu tượng đại diện cho collections
- **Implementations**: Các lớp triển khai cụ thể của interfaces
- **Algorithms**: Các phương thức thực hiện tính toán hữu ích trên collections

```java
// Ví dụ cơ bản về Collection
import java.util.*;

public class CollectionDemo {
    public static void main(String[] args) {
        // List - danh sách có thứ tự, cho phép trùng lặp
        List<String> names = new ArrayList<>();
        names.add("Java");
        names.add("Python");
        names.add("Java"); // cho phép trùng lặp
        System.out.println("List: " + names); // [Java, Python, Java]

        // Set - tập hợp không trùng lặp
        Set<String> uniqueNames = new HashSet<>(names);
        System.out.println("Set: " + uniqueNames); // [Java, Python]

        // Map - cặp key-value
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Java", 95);
        scores.put("Python", 88);
        System.out.println("Map: " + scores); // {Java=95, Python=88}
    }
}
```

### 1.2. Lợi ích của Collection Framework

| Lợi ích | Mô tả |
|---------|--------|
| **Giảm effort lập trình** | Cung cấp sẵn cấu trúc dữ liệu và thuật toán |
| **Tăng chất lượng code** | Implementations được tối ưu hóa và test kỹ |
| **Interoperability** | Các collections có thể trao đổi dữ liệu dễ dàng |
| **Giảm effort học** | API thống nhất cho các collections khác nhau |
| **Tái sử dụng** | Code linh hoạt, dễ mở rộng |

### 1.3. Kiến trúc Collection Framework

```
                    Iterable (interface)
                        |
                    Collection (interface)
                   /        |        \
                List       Set      Queue
               /  |  \    / | \      |   \
        ArrayList  |  Vector  |  |  PriorityQueue  ArrayDeque
              LinkedList   HashSet  |
                          LinkedHashSet
                              TreeSet

                    Map (interface)
                   /    |     \      \
            HashMap  TreeMap  LinkedHashMap  Hashtable
```

---

## 2. Interface Collection

### 2.1. Các phương thức cơ bản của Collection

| Phương thức | Mô tả |
|-------------|--------|
| `add(E e)` | Thêm phần tử vào collection |
| `remove(Object o)` | Xóa phần tử khỏi collection |
| `contains(Object o)` | Kiểm tra phần tử có tồn tại không |
| `size()` | Trả về số lượng phần tử |
| `isEmpty()` | Kiểm tra collection rỗng |
| `clear()` | Xóa tất cả phần tử |
| `toArray()` | Chuyển đổi sang mảng |
| `iterator()` | Trả về iterator để duyệt |
| `addAll(Collection c)` | Thêm tất cả phần tử từ collection khác |
| `removeAll(Collection c)` | Xóa tất cả phần tử có trong collection khác |
| `retainAll(Collection c)` | Giữ lại các phần tử có trong collection khác |

### 2.2. Ví dụ sử dụng Collection

```java
import java.util.*;

public class CollectionMethodsDemo {
    public static void main(String[] args) {
        Collection<String> fruits = new ArrayList<>();
        
        // Thêm phần tử
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        System.out.println("Fruits: " + fruits); // [Apple, Banana, Cherry]
        
        // Kiểm tra
        System.out.println("Contains Apple? " + fruits.contains("Apple")); // true
        System.out.println("Size: " + fruits.size()); // 3
        System.out.println("Is empty? " + fruits.isEmpty()); // false
        
        // Xóa phần tử
        fruits.remove("Banana");
        System.out.println("After remove: " + fruits); // [Apple, Cherry]
        
        // addAll
        Collection<String> moreFruits = Arrays.asList("Mango", "Grape");
        fruits.addAll(moreFruits);
        System.out.println("After addAll: " + fruits); // [Apple, Cherry, Mango, Grape]
        
        // Chuyển sang mảng
        String[] arr = fruits.toArray(new String[0]);
        System.out.println("Array: " + Arrays.toString(arr));
        
        // Clear
        fruits.clear();
        System.out.println("After clear: " + fruits); // []
    }
}
```

---

## 3. List Interface

**List** là collection có thứ tự (ordered), cho phép trùng lặp phần tử, và hỗ trợ truy cập bằng index.

### 3.1. ArrayList

**ArrayList** sử dụng mảng động (dynamic array) để lưu trữ phần tử.

**Đặc điểm:**
- Truy cập nhanh O(1) theo index
- Thêm/xóa ở giữa chậm O(n)
- Không thread-safe
- Cho phép null

```java
import java.util.ArrayList;
import java.util.List;

public class ArrayListDemo {
    public static void main(String[] args) {
        // Khởi tạo
        List<String> list = new ArrayList<>();         // empty
        List<String> list2 = new ArrayList<>(100);     // initial capacity
        List<String> list3 = new ArrayList<>(list);    // copy constructor

        // Thêm phần tử
        list.add("Java");           // thêm vào cuối
        list.add(0, "Python");      // thêm vào vị trí index 0
        list.addAll(Arrays.asList("C++", "Go"));

        // Truy cập
        String first = list.get(0);         // Python
        int index = list.indexOf("Java");   // 1

        // Cập nhật
        list.set(0, "JavaScript");  // thay thế phần tử tại index 0

        // Xóa
        list.remove(0);             // xóa theo index
        list.remove("Go");          // xóa theo giá trị

        // Duyệt
        for (String item : list) {
            System.out.println(item);
        }

        // SubList
        List<String> subList = list.subList(0, 2);
        System.out.println("SubList: " + subList);
    }
}
```

**Cơ chế tự động mở rộng (Auto Resize):**
```java
// ArrayList bắt đầu với capacity mặc định = 10
// Khi đầy, nó sẽ tạo mảng mới với size = oldCapacity + (oldCapacity >> 1)
// Tức là tăng 50% mỗi lần (10 -> 15 -> 22 -> 33 ...)
```

### 3.2. LinkedList

**LinkedList** sử dụng danh sách liên kết đôi (doubly-linked list).

**Đặc điểm:**
- Thêm/xóa đầu/cuối nhanh O(1)
- Truy cập theo index chậm O(n)
- Implements cả List và Deque interface
- Tốn nhiều bộ nhớ hơn ArrayList (lưu thêm prev/next pointers)

```java
import java.util.LinkedList;
import java.util.List;

public class LinkedListDemo {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();

        // Thêm phần tử
        list.add("B");
        list.addFirst("A");     // thêm đầu
        list.addLast("C");      // thêm cuối
        list.add(1, "A.5");     // thêm vào giữa
        System.out.println(list); // [A, A.5, B, C]

        // Truy cập
        String first = list.getFirst();  // A
        String last = list.getLast();    // C
        String peek = list.peek();      // A (không xóa)

        // Xóa
        list.removeFirst();     // xóa đầu
        list.removeLast();      // xóa cuối
        System.out.println(list); // [A.5, B]

        // Sử dụng như Stack
        list.push("Top");       // thêm đầu (stack push)
        String pop = list.pop(); // xóa đầu (stack pop)

        // Sử dụng như Queue
        list.offer("End");      // thêm cuối (queue offer)
        String poll = list.poll(); // xóa đầu (queue poll)
    }
}
```

### 3.3. Vector

**Vector** tương tự ArrayList nhưng **thread-safe** (synchronized).

**Đặc điểm:**
- Thread-safe (mọi phương thức đều synchronized)
- Chậm hơn ArrayList do overhead đồng bộ hóa
- Tăng gấp đôi capacity khi đầy (ArrayList tăng 50%)
- Legacy class, ít dùng trong code mới

```java
import java.util.Vector;

public class VectorDemo {
    public static void main(String[] args) {
        Vector<Integer> vector = new Vector<>();       // capacity = 10
        Vector<Integer> v2 = new Vector<>(20);         // capacity = 20
        Vector<Integer> v3 = new Vector<>(20, 5);      // capacity=20, increment=5

        vector.add(1);
        vector.add(2);
        vector.add(3);

        // Capacity vs Size
        System.out.println("Size: " + vector.size());           // 3
        System.out.println("Capacity: " + vector.capacity());   // 10

        // Các phương thức đặc biệt
        vector.addElement(4);       // tương đương add()
        vector.elementAt(0);        // tương đương get()
        vector.firstElement();      // phần tử đầu
        vector.lastElement();       // phần tử cuối
    }
}
```

### 3.4. Stack

**Stack** kế thừa từ Vector, triển khai cấu trúc LIFO (Last In First Out).

```java
import java.util.Stack;

public class StackDemo {
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();

        // Push - thêm phần tử lên đỉnh
        stack.push("First");
        stack.push("Second");
        stack.push("Third");
        System.out.println("Stack: " + stack); // [First, Second, Third]

        // Peek - xem phần tử đỉnh (không xóa)
        System.out.println("Peek: " + stack.peek()); // Third

        // Pop - lấy và xóa phần tử đỉnh
        String top = stack.pop();
        System.out.println("Pop: " + top);     // Third
        System.out.println("Stack: " + stack); // [First, Second]

        // Search - tìm vị trí (1-based, từ đỉnh)
        int pos = stack.search("First");
        System.out.println("Position of First: " + pos); // 2

        // Empty
        System.out.println("Empty? " + stack.empty()); // false
    }
}
```

> **Lưu ý:** Trong thực tế, nên dùng `Deque` (ArrayDeque) thay vì `Stack` vì Stack kế thừa Vector (thread-safe không cần thiết).

### 3.5. So sánh ArrayList vs LinkedList vs Vector

| Đặc điểm | ArrayList | LinkedList | Vector |
|-----------|-----------|------------|--------|
| **Cấu trúc** | Dynamic Array | Doubly Linked List | Dynamic Array |
| **Truy cập index** | O(1) | O(n) | O(1) |
| **Thêm/xóa đầu** | O(n) | O(1) | O(n) |
| **Thêm/xóa cuối** | O(1) amortized | O(1) | O(1) amortized |
| **Thêm/xóa giữa** | O(n) | O(1)* | O(n) |
| **Thread-safe** | Không | Không | Có |
| **Memory** | Ít hơn | Nhiều hơn (pointers) | Ít hơn |
| **Resize** | +50% | Không cần | +100% |

> *O(1) cho thêm/xóa tại vị trí iterator đang trỏ, O(n) nếu phải tìm vị trí trước.

---

## 4. Set Interface

**Set** là collection **không cho phép phần tử trùng lặp**.

### 4.1. HashSet

**HashSet** sử dụng **HashMap** bên trong để lưu trữ phần tử.

**Đặc điểm:**
- Không đảm bảo thứ tự
- Cho phép 1 phần tử null
- O(1) cho add, remove, contains
- Phụ thuộc vào `hashCode()` và `equals()`

```java
import java.util.HashSet;
import java.util.Set;

public class HashSetDemo {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        
        // Thêm phần tử
        set.add("Java");
        set.add("Python");
        set.add("Java");    // không thêm được (trùng)
        set.add(null);      // OK - cho phép 1 null
        
        System.out.println("Set: " + set);      // thứ tự không đảm bảo
        System.out.println("Size: " + set.size()); // 3 (Java, Python, null)
        
        // Kiểm tra tồn tại
        System.out.println("Contains Java? " + set.contains("Java")); // true
        
        // Xóa
        set.remove("Python");
        
        // Phép toán trên tập hợp
        Set<String> set1 = new HashSet<>(Arrays.asList("A", "B", "C"));
        Set<String> set2 = new HashSet<>(Arrays.asList("B", "C", "D"));
        
        // Union (Hợp)
        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);
        System.out.println("Union: " + union); // [A, B, C, D]
        
        // Intersection (Giao)
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        System.out.println("Intersection: " + intersection); // [B, C]
        
        // Difference (Hiệu)
        Set<String> difference = new HashSet<>(set1);
        difference.removeAll(set2);
        System.out.println("Difference: " + difference); // [A]
    }
}
```

### 4.2. LinkedHashSet

**LinkedHashSet** kế thừa HashSet nhưng duy trì **thứ tự chèn** bằng linked list.

```java
import java.util.LinkedHashSet;
import java.util.Set;

public class LinkedHashSetDemo {
    public static void main(String[] args) {
        Set<String> set = new LinkedHashSet<>();
        
        set.add("Cherry");
        set.add("Apple");
        set.add("Banana");
        set.add("Apple"); // không thêm (trùng)
        
        // Duy trì thứ tự chèn
        System.out.println(set); // [Cherry, Apple, Banana]
        
        // Duyệt - luôn theo thứ tự chèn
        for (String fruit : set) {
            System.out.println(fruit);
        }
        // Cherry
        // Apple
        // Banana
    }
}
```

### 4.3. TreeSet

**TreeSet** sử dụng **Red-Black Tree** (cây đỏ-đen), tự động **sắp xếp** phần tử.

**Đặc điểm:**
- Phần tử luôn được sắp xếp (natural ordering hoặc Comparator)
- Không cho phép null
- O(log n) cho add, remove, contains
- Implements NavigableSet interface

```java
import java.util.TreeSet;
import java.util.NavigableSet;

public class TreeSetDemo {
    public static void main(String[] args) {
        TreeSet<Integer> set = new TreeSet<>();
        
        set.add(5);
        set.add(1);
        set.add(3);
        set.add(2);
        set.add(4);
        
        // Tự động sắp xếp
        System.out.println("TreeSet: " + set); // [1, 2, 3, 4, 5]
        
        // NavigableSet methods
        System.out.println("First: " + set.first());       // 1
        System.out.println("Last: " + set.last());         // 5
        System.out.println("Lower(3): " + set.lower(3));   // 2 (< 3)
        System.out.println("Higher(3): " + set.higher(3)); // 4 (> 3)
        System.out.println("Floor(3): " + set.floor(3));   // 3 (<= 3)
        System.out.println("Ceiling(3): " + set.ceiling(3)); // 3 (>= 3)
        
        // SubSet
        NavigableSet<Integer> sub = set.subSet(2, true, 4, true);
        System.out.println("SubSet [2,4]: " + sub); // [2, 3, 4]
        
        // HeadSet và TailSet
        System.out.println("HeadSet(3): " + set.headSet(3));   // [1, 2]
        System.out.println("TailSet(3): " + set.tailSet(3));   // [3, 4, 5]
        
        // Sắp xếp ngược
        System.out.println("Descending: " + set.descendingSet()); // [5, 4, 3, 2, 1]
        
        // TreeSet với custom Comparator
        TreeSet<String> nameSet = new TreeSet<>((a, b) -> b.compareTo(a)); // sắp xếp giảm
        nameSet.add("Apple");
        nameSet.add("Cherry");
        nameSet.add("Banana");
        System.out.println("Custom sort: " + nameSet); // [Cherry, Banana, Apple]
    }
}
```

### 4.4. So sánh HashSet vs LinkedHashSet vs TreeSet

| Đặc điểm | HashSet | LinkedHashSet | TreeSet |
|-----------|---------|---------------|---------|
| **Cấu trúc** | Hash Table | Hash Table + Linked List | Red-Black Tree |
| **Thứ tự** | Không đảm bảo | Thứ tự chèn | Sắp xếp (sorted) |
| **Null** | Cho phép 1 null | Cho phép 1 null | Không cho phép |
| **Performance** | O(1) | O(1) | O(log n) |
| **Use case** | Cần tốc độ, không cần thứ tự | Cần tốc độ + thứ tự chèn | Cần sắp xếp |

---

## 5. Queue Interface

**Queue** (Hàng đợi) theo nguyên tắc **FIFO** (First In First Out).

| Phương thức | Throws Exception | Trả về null/false |
|-------------|-----------------|-------------------|
| Thêm | `add(e)` | `offer(e)` |
| Xóa | `remove()` | `poll()` |
| Xem | `element()` | `peek()` |

### 5.1. PriorityQueue

**PriorityQueue** sắp xếp phần tử theo thứ tự ưu tiên (natural ordering hoặc Comparator).

```java
import java.util.PriorityQueue;
import java.util.Comparator;

public class PriorityQueueDemo {
    public static void main(String[] args) {
        // Min-Heap (mặc định)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.add(5);
        minHeap.add(1);
        minHeap.add(3);
        minHeap.add(2);
        
        System.out.println("Peek: " + minHeap.peek()); // 1 (nhỏ nhất)
        
        // Poll lần lượt
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.poll() + " "); // 1 2 3 5
        }
        System.out.println();
        
        // Max-Heap
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        maxHeap.add(5);
        maxHeap.add(1);
        maxHeap.add(3);
        
        System.out.println("Max Peek: " + maxHeap.peek()); // 5
        
        // PriorityQueue với Object
        PriorityQueue<Task> taskQueue = new PriorityQueue<>(
            Comparator.comparingInt(Task::getPriority)
        );
        taskQueue.add(new Task("Low task", 3));
        taskQueue.add(new Task("High task", 1));
        taskQueue.add(new Task("Medium task", 2));
        
        while (!taskQueue.isEmpty()) {
            System.out.println(taskQueue.poll()); // High -> Medium -> Low
        }
    }
}

class Task {
    private String name;
    private int priority;
    
    public Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }
    
    public int getPriority() { return priority; }
    
    @Override
    public String toString() {
        return name + " (priority=" + priority + ")";
    }
}
```

### 5.2. ArrayDeque

**ArrayDeque** là triển khai Deque dựa trên mảng vòng (circular array). Nhanh hơn Stack và LinkedList.

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class ArrayDequeDemo {
    public static void main(String[] args) {
        Deque<String> deque = new ArrayDeque<>();
        
        // Sử dụng như Queue (FIFO)
        deque.offer("First");
        deque.offer("Second");
        deque.offer("Third");
        System.out.println("Queue poll: " + deque.poll()); // First
        
        // Sử dụng như Stack (LIFO)
        Deque<String> stack = new ArrayDeque<>();
        stack.push("Bottom");
        stack.push("Middle");
        stack.push("Top");
        System.out.println("Stack pop: " + stack.pop()); // Top
        
        // Double-ended operations
        Deque<Integer> dq = new ArrayDeque<>();
        dq.addFirst(1);     // [1]
        dq.addLast(3);      // [1, 3]
        dq.addFirst(0);     // [0, 1, 3]
        dq.addLast(4);      // [0, 1, 3, 4]
        
        System.out.println("First: " + dq.peekFirst());  // 0
        System.out.println("Last: " + dq.peekLast());    // 4
        System.out.println("Deque: " + dq);              // [0, 1, 3, 4]
    }
}
```

### 5.3. LinkedList as Queue

```java
import java.util.LinkedList;
import java.util.Queue;

public class LinkedListQueueDemo {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();
        
        // Enqueue
        queue.offer("Customer 1");
        queue.offer("Customer 2");
        queue.offer("Customer 3");
        
        System.out.println("Queue: " + queue);
        
        // Dequeue
        while (!queue.isEmpty()) {
            System.out.println("Serving: " + queue.poll());
        }
        // Serving: Customer 1
        // Serving: Customer 2
        // Serving: Customer 3
    }
}
```

---

## 6. Map Interface

**Map** lưu trữ dữ liệu dạng cặp **key-value**. Key không được trùng lặp.

### 6.1. HashMap

**HashMap** sử dụng hash table, cho phép null key và null value.

```java
import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        
        // Put - thêm cặp key-value
        map.put("Java", 95);
        map.put("Python", 88);
        map.put("JavaScript", 92);
        map.put(null, 0);        // null key OK
        map.put("Go", null);     // null value OK
        
        // Get - lấy value theo key
        int javaScore = map.get("Java");     // 95
        Integer unknown = map.get("Rust");   // null (không tồn tại)
        
        // getOrDefault
        int rustScore = map.getOrDefault("Rust", -1); // -1
        
        // putIfAbsent - chỉ thêm nếu key chưa tồn tại
        map.putIfAbsent("Java", 100);  // không thay đổi (Java đã tồn tại)
        
        // Kiểm tra
        System.out.println("Contains key 'Java'? " + map.containsKey("Java"));     // true
        System.out.println("Contains value 95? " + map.containsValue(95));          // true
        
        // Duyệt Map
        // Cách 1: entrySet
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        
        // Cách 2: keySet
        for (String key : map.keySet()) {
            System.out.println(key + " = " + map.get(key));
        }
        
        // Cách 3: forEach (Java 8+)
        map.forEach((key, value) -> System.out.println(key + " -> " + value));
        
        // Xóa
        map.remove("Go");           // xóa theo key
        map.remove("Java", 100);    // xóa nếu key=Java VÀ value=100
        
        // compute, merge (Java 8+)
        map.compute("Python", (k, v) -> v + 10);     // Python = 98
        map.merge("Rust", 80, Integer::sum);          // Rust = 80 (mới)
        map.merge("Python", 10, Integer::sum);        // Python = 108 (cộng dồn)
    }
}
```

### 6.2. LinkedHashMap

**LinkedHashMap** duy trì **thứ tự chèn** hoặc **thứ tự truy cập**.

```java
import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapDemo {
    public static void main(String[] args) {
        // Thứ tự chèn (mặc định)
        Map<String, Integer> insertionOrder = new LinkedHashMap<>();
        insertionOrder.put("C", 3);
        insertionOrder.put("A", 1);
        insertionOrder.put("B", 2);
        System.out.println(insertionOrder); // {C=3, A=1, B=2}
        
        // Thứ tự truy cập (accessOrder = true)
        Map<String, Integer> accessOrder = new LinkedHashMap<>(16, 0.75f, true);
        accessOrder.put("A", 1);
        accessOrder.put("B", 2);
        accessOrder.put("C", 3);
        
        accessOrder.get("A"); // truy cập A -> A chuyển về cuối
        System.out.println(accessOrder); // {B=2, C=3, A=1}
        
        // Ứng dụng: LRU Cache
        Map<String, String> lruCache = new LinkedHashMap<>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > 3; // giữ tối đa 3 phần tử
            }
        };
        
        lruCache.put("1", "one");
        lruCache.put("2", "two");
        lruCache.put("3", "three");
        lruCache.put("4", "four"); // "1" bị xóa (eldest)
        System.out.println("LRU Cache: " + lruCache); // {2=two, 3=three, 4=four}
    }
}
```

### 6.3. TreeMap

**TreeMap** sử dụng Red-Black Tree, key luôn được **sắp xếp**.

```java
import java.util.TreeMap;
import java.util.NavigableMap;

public class TreeMapDemo {
    public static void main(String[] args) {
        TreeMap<String, Integer> map = new TreeMap<>();
        
        map.put("Banana", 2);
        map.put("Apple", 5);
        map.put("Cherry", 3);
        map.put("Date", 1);
        
        // Tự động sắp xếp theo key
        System.out.println(map); // {Apple=5, Banana=2, Cherry=3, Date=1}
        
        // NavigableMap methods
        System.out.println("First key: " + map.firstKey());          // Apple
        System.out.println("Last key: " + map.lastKey());            // Date
        System.out.println("Lower(Cherry): " + map.lowerKey("Cherry"));   // Banana
        System.out.println("Higher(Cherry): " + map.higherKey("Cherry")); // Date
        
        // SubMap
        NavigableMap<String, Integer> sub = map.subMap("B", true, "D", true);
        System.out.println("SubMap [B,D]: " + sub); // {Banana=2, Cherry=3, Date=1}
        
        // HeadMap và TailMap
        System.out.println("HeadMap(Cherry): " + map.headMap("Cherry")); // {Apple=5, Banana=2}
        System.out.println("TailMap(Cherry): " + map.tailMap("Cherry")); // {Cherry=3, Date=1}
    }
}
```

### 6.4. Hashtable

**Hashtable** là legacy class, thread-safe, **không cho phép null** key hoặc value.

```java
import java.util.Hashtable;

public class HashtableDemo {
    public static void main(String[] args) {
        Hashtable<String, Integer> table = new Hashtable<>();
        
        table.put("A", 1);
        table.put("B", 2);
        table.put("C", 3);
        
        // Không cho phép null
        // table.put(null, 4);    // NullPointerException
        // table.put("D", null);  // NullPointerException
        
        System.out.println(table);
    }
}
```

### 6.5. ConcurrentHashMap

**ConcurrentHashMap** là thread-safe map hiệu suất cao, sử dụng segment locking.

```java
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);
        
        // Thread-safe operations
        map.putIfAbsent("D", 4);
        map.computeIfAbsent("E", k -> k.length());
        map.computeIfPresent("A", (k, v) -> v + 10);
        
        // Không cho phép null key hoặc value
        // map.put(null, 5);     // NullPointerException
        // map.put("F", null);   // NullPointerException
        
        // Atomic operations
        map.replace("B", 2, 20);  // thay thế nếu value hiện tại = 2
        
        // forEach parallel (Java 8+)
        map.forEach(2, (key, value) -> {
            System.out.println(Thread.currentThread().getName() + ": " + key + "=" + value);
        });
        
        System.out.println(map);
    }
}
```

### 6.6. So sánh các Map implementations

| Đặc điểm | HashMap | LinkedHashMap | TreeMap | Hashtable | ConcurrentHashMap |
|-----------|---------|---------------|---------|-----------|-------------------|
| **Thứ tự** | Không | Chèn/Truy cập | Sorted | Không | Không |
| **Null key** | 1 null | 1 null | Không | Không | Không |
| **Null value** | Nhiều | Nhiều | Nhiều | Không | Không |
| **Thread-safe** | Không | Không | Không | Có | Có |
| **Performance** | O(1) | O(1) | O(log n) | O(1) | O(1) |
| **Use case** | General | Cache/LRU | Sorted data | Legacy | Concurrent |

---

## 7. Iterators

### 7.1. Iterator

**Iterator** cho phép duyệt qua collection theo một hướng (forward).

```java
import java.util.*;

public class IteratorDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E"));
        
        // Lấy Iterator
        Iterator<String> it = list.iterator();
        
        // Duyệt
        while (it.hasNext()) {
            String element = it.next();
            System.out.print(element + " ");
        }
        System.out.println(); // A B C D E
        
        // Xóa phần tử an toàn trong khi duyệt
        Iterator<String> it2 = list.iterator();
        while (it2.hasNext()) {
            String element = it2.next();
            if (element.equals("C")) {
                it2.remove(); // Xóa an toàn
            }
        }
        System.out.println("After remove: " + list); // [A, B, D, E]
        
        // KHÔNG được modify collection trực tiếp khi đang duyệt
        // for (String s : list) {
        //     list.remove(s); // ConcurrentModificationException!
        // }
    }
}
```

### 7.2. ListIterator

**ListIterator** mở rộng Iterator, cho phép duyệt **hai chiều** và modify.

```java
import java.util.*;

public class ListIteratorDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        
        // ListIterator
        ListIterator<String> lit = list.listIterator();
        
        // Duyệt xuôi
        System.out.print("Forward: ");
        while (lit.hasNext()) {
            int index = lit.nextIndex();
            String element = lit.next();
            System.out.print("[" + index + "]" + element + " ");
        }
        System.out.println(); // [0]A [1]B [2]C [3]D
        
        // Duyệt ngược
        System.out.print("Backward: ");
        while (lit.hasPrevious()) {
            String element = lit.previous();
            System.out.print(element + " ");
        }
        System.out.println(); // D C B A
        
        // Thêm và thay thế
        lit = list.listIterator(1); // bắt đầu từ index 1
        lit.next();                 // di chuyển qua "B"
        lit.set("B_modified");      // thay "B" bằng "B_modified"
        lit.add("B2");              // thêm "B2" sau "B_modified"
        
        System.out.println("Modified: " + list); // [A, B_modified, B2, C, D]
    }
}
```

### 7.3. For-each loop

**Enhanced for loop** (for-each) là cú pháp đơn giản hóa việc duyệt collection.

```java
import java.util.*;

public class ForEachDemo {
    public static void main(String[] args) {
        List<String> languages = Arrays.asList("Java", "Python", "Go", "Rust");
        
        // For-each loop (dùng Iterator bên dưới)
        for (String lang : languages) {
            System.out.println(lang);
        }
        
        // Với Map
        Map<String, Integer> scores = Map.of("Java", 95, "Python", 88, "Go", 90);
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        
        // forEach method (Java 8+)
        languages.forEach(System.out::println);
        languages.forEach(lang -> System.out.println("Language: " + lang));
        
        // Với Array
        int[] numbers = {1, 2, 3, 4, 5};
        for (int num : numbers) {
            System.out.print(num + " ");
        }
    }
}
```

---

## 8. Comparable và Comparator

### 8.1. Comparable Interface

**Comparable** định nghĩa **thứ tự tự nhiên** (natural ordering) của đối tượng. Implement `compareTo()`.

```java
public class Student implements Comparable<Student> {
    private String name;
    private int age;
    private double gpa;
    
    public Student(String name, int age, double gpa) {
        this.name = name;
        this.age = age;
        this.gpa = gpa;
    }
    
    // Sắp xếp theo GPA giảm dần (natural ordering)
    @Override
    public int compareTo(Student other) {
        return Double.compare(other.gpa, this.gpa); // giảm dần
    }
    
    @Override
    public String toString() {
        return name + "(GPA=" + gpa + ")";
    }
    
    // Getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getGpa() { return gpa; }
}

// Sử dụng
List<Student> students = new ArrayList<>();
students.add(new Student("Alice", 20, 3.8));
students.add(new Student("Bob", 22, 3.5));
students.add(new Student("Charlie", 21, 3.9));

Collections.sort(students); // sử dụng compareTo
System.out.println(students); // [Charlie(GPA=3.9), Alice(GPA=3.8), Bob(GPA=3.5)]
```

### 8.2. Comparator Interface

**Comparator** cho phép định nghĩa **nhiều cách sắp xếp** khác nhau bên ngoài class.

```java
import java.util.*;

public class ComparatorDemo {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
            new Student("Alice", 20, 3.8),
            new Student("Bob", 22, 3.5),
            new Student("Charlie", 21, 3.9),
            new Student("David", 20, 3.8)
        );
        
        // Comparator sắp xếp theo tên
        Comparator<Student> byName = Comparator.comparing(Student::getName);
        students.sort(byName);
        System.out.println("By name: " + students);
        
        // Sắp xếp theo tuổi
        Comparator<Student> byAge = Comparator.comparingInt(Student::getAge);
        students.sort(byAge);
        System.out.println("By age: " + students);
        
        // Sắp xếp theo GPA giảm dần
        Comparator<Student> byGpaDesc = Comparator.comparingDouble(Student::getGpa).reversed();
        students.sort(byGpaDesc);
        System.out.println("By GPA desc: " + students);
        
        // Sắp xếp nhiều tiêu chí: GPA giảm -> Tên tăng
        Comparator<Student> multiSort = Comparator
            .comparingDouble(Student::getGpa).reversed()
            .thenComparing(Student::getName);
        students.sort(multiSort);
        System.out.println("Multi sort: " + students);
        
        // Xử lý null
        Comparator<Student> nullSafe = Comparator.nullsLast(byName);
    }
}
```

### 8.3. So sánh Comparable vs Comparator

| Đặc điểm | Comparable | Comparator |
|-----------|-----------|------------|
| **Package** | `java.lang` | `java.util` |
| **Method** | `compareTo(T o)` | `compare(T o1, T o2)` |
| **Số cách sắp xếp** | 1 (natural ordering) | Nhiều |
| **Modify class** | Cần modify class gốc | Không cần |
| **Use case** | Thứ tự mặc định | Sắp xếp linh hoạt |
| **Java 8+ support** | Không | `comparing()`, `thenComparing()` |

---

## 9. Collections Utility Class

### 9.1. Sorting

```java
import java.util.*;

public class CollectionsSortDemo {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(5, 2, 8, 1, 9, 3));
        
        // Sort tăng dần
        Collections.sort(numbers);
        System.out.println("Sorted: " + numbers); // [1, 2, 3, 5, 8, 9]
        
        // Sort giảm dần
        Collections.sort(numbers, Collections.reverseOrder());
        System.out.println("Reversed: " + numbers); // [9, 8, 5, 3, 2, 1]
        
        // Reverse (đảo ngược)
        Collections.reverse(numbers);
        System.out.println("Reversed: " + numbers);
        
        // Shuffle (xáo trộn)
        Collections.shuffle(numbers);
        System.out.println("Shuffled: " + numbers);
        
        // Swap
        Collections.swap(numbers, 0, numbers.size() - 1);
        
        // Rotate
        List<Integer> rotateList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        Collections.rotate(rotateList, 2); // xoay phải 2 vị trí
        System.out.println("Rotated: " + rotateList); // [4, 5, 1, 2, 3]
    }
}
```

### 9.2. Searching

```java
import java.util.*;

public class CollectionsSearchDemo {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 9, 11));
        
        // Binary Search (list phải sorted)
        int index = Collections.binarySearch(numbers, 7);
        System.out.println("Index of 7: " + index); // 3
        
        int notFound = Collections.binarySearch(numbers, 6);
        System.out.println("Index of 6: " + notFound); // negative (insertion point)
        
        // Min/Max
        System.out.println("Min: " + Collections.min(numbers)); // 1
        System.out.println("Max: " + Collections.max(numbers)); // 11
        
        // Frequency
        List<String> words = Arrays.asList("hello", "world", "hello", "java", "hello");
        int freq = Collections.frequency(words, "hello");
        System.out.println("Frequency of 'hello': " + freq); // 3
        
        // Disjoint (kiểm tra 2 collection không có phần tử chung)
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(4, 5, 6);
        System.out.println("Disjoint? " + Collections.disjoint(list1, list2)); // true
    }
}
```

### 9.3. Synchronizing

```java
import java.util.*;

public class CollectionsSyncDemo {
    public static void main(String[] args) {
        // Tạo synchronized collections
        List<String> syncList = Collections.synchronizedList(new ArrayList<>());
        Set<String> syncSet = Collections.synchronizedSet(new HashSet<>());
        Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());
        
        // Thread-safe operations
        syncList.add("A");
        syncList.add("B");
        
        // LƯU Ý: Khi duyệt phải synchronized manually
        synchronized (syncList) {
            for (String item : syncList) {
                System.out.println(item);
            }
        }
    }
}
```

### 9.4. Unmodifiable Collections

```java
import java.util.*;

public class UnmodifiableDemo {
    public static void main(String[] args) {
        List<String> mutableList = new ArrayList<>(Arrays.asList("A", "B", "C"));
        
        // Tạo unmodifiable view
        List<String> immutableList = Collections.unmodifiableList(mutableList);
        // immutableList.add("D"); // UnsupportedOperationException!
        
        // Java 9+: Immutable factory methods
        List<String> list = List.of("A", "B", "C");
        Set<String> set = Set.of("A", "B", "C");
        Map<String, Integer> map = Map.of("A", 1, "B", 2);
        
        // Java 10+: copyOf
        List<String> copy = List.copyOf(mutableList);
        
        // Empty collections
        List<String> emptyList = Collections.emptyList();
        Set<String> emptySet = Collections.emptySet();
        Map<String, String> emptyMap = Collections.emptyMap();
        
        // Singleton
        List<String> single = Collections.singletonList("Only");
        Set<String> singleSet = Collections.singleton("Only");
    }
}
```

---

## 10. Java 8+ Features trong Collection

### 10.1. Stream API

```java
import java.util.*;
import java.util.stream.*;

public class StreamCollectionDemo {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
        
        // Filter và collect
        List<String> longNames = names.stream()
            .filter(name -> name.length() > 3)
            .collect(Collectors.toList());
        System.out.println("Long names: " + longNames); // [Alice, Charlie, David]
        
        // Map (transform)
        List<String> upperNames = names.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        System.out.println("Upper: " + upperNames);
        
        // Reduce
        int totalLength = names.stream()
            .mapToInt(String::length)
            .sum();
        System.out.println("Total length: " + totalLength);
        
        // Group by
        Map<Integer, List<String>> byLength = names.stream()
            .collect(Collectors.groupingBy(String::length));
        System.out.println("By length: " + byLength);
    }
}
```

### 10.2. Lambda Expressions

```java
import java.util.*;

public class LambdaCollectionDemo {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(5, 2, 8, 1, 9));
        
        // Sort với Lambda
        numbers.sort((a, b) -> a - b);
        System.out.println("Sorted: " + numbers);
        
        // removeIf
        numbers.removeIf(n -> n > 5);
        System.out.println("After removeIf: " + numbers); // [2, 1]
        
        // replaceAll
        numbers.replaceAll(n -> n * 10);
        System.out.println("After replaceAll: " + numbers); // [20, 10]
        
        // Map forEach
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.forEach((k, v) -> System.out.println(k + "=" + v));
        
        // Map compute
        map.compute("A", (k, v) -> v + 10);  // A = 11
        map.computeIfAbsent("C", k -> 3);    // C = 3
        map.replaceAll((k, v) -> v * 2);
    }
}
```

### 10.3. Default Methods

```java
import java.util.*;

public class DefaultMethodsDemo {
    public static void main(String[] args) {
        // Iterable.forEach
        List<String> list = Arrays.asList("A", "B", "C");
        list.forEach(System.out::println);
        
        // Collection.removeIf
        List<Integer> nums = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        nums.removeIf(n -> n % 2 == 0);
        System.out.println(nums); // [1, 3, 5]
        
        // List.sort
        List<String> names = new ArrayList<>(Arrays.asList("Charlie", "Alice", "Bob"));
        names.sort(Comparator.naturalOrder());
        System.out.println(names); // [Alice, Bob, Charlie]
        
        // Map.getOrDefault, putIfAbsent, replace, merge
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        
        int val = map.getOrDefault("B", 0);    // 0
        map.putIfAbsent("B", 2);               // B = 2
        map.replace("A", 1, 10);               // A = 10
        map.merge("A", 5, Integer::sum);       // A = 15
    }
}
```

---

## 11. Performance Considerations

### 11.1. Time Complexity

| Operation | ArrayList | LinkedList | HashSet | TreeSet | HashMap | TreeMap |
|-----------|-----------|------------|---------|---------|---------|---------|
| **Add** | O(1)* | O(1) | O(1) | O(log n) | O(1) | O(log n) |
| **Remove** | O(n) | O(1)** | O(1) | O(log n) | O(1) | O(log n) |
| **Get/Contains** | O(1) | O(n) | O(1) | O(log n) | O(1) | O(log n) |
| **Search** | O(n) | O(n) | O(1) | O(log n) | O(1) | O(log n) |

> *Amortized O(1) - có thể O(n) khi resize
> **O(1) nếu đã có reference tới node

### 11.2. Space Complexity

| Collection | Space Overhead |
|------------|---------------|
| **ArrayList** | Array + empty slots (~25-50% waste) |
| **LinkedList** | 2 pointers per node (prev + next) |
| **HashSet/HashMap** | Array of buckets + linked list/tree nodes |
| **TreeSet/TreeMap** | 3 pointers per node (left, right, parent) + color bit |

### 11.3. Khi nào sử dụng Collection nào?

```java
// Cần truy cập nhanh theo index? → ArrayList
List<String> randomAccess = new ArrayList<>();

// Thêm/xóa đầu/cuối thường xuyên? → LinkedList hoặc ArrayDeque
Deque<String> queue = new ArrayDeque<>();

// Không trùng lặp, không cần thứ tự? → HashSet
Set<String> unique = new HashSet<>();

// Không trùng lặp, cần sắp xếp? → TreeSet
Set<String> sorted = new TreeSet<>();

// Key-value lookup nhanh? → HashMap
Map<String, Object> lookup = new HashMap<>();

// Key-value cần sắp xếp? → TreeMap
Map<String, Object> sortedMap = new TreeMap<>();

// Thread-safe map? → ConcurrentHashMap
Map<String, Object> concurrent = new ConcurrentHashMap<>();

// FIFO queue? → ArrayDeque
Queue<String> fifo = new ArrayDeque<>();

// Priority queue? → PriorityQueue
Queue<Task> priority = new PriorityQueue<>();
```

---

## 12. Best Practices

### 1. Luôn khai báo bằng Interface

```java
// ✅ Tốt - linh hoạt, dễ thay đổi implementation
List<String> list = new ArrayList<>();
Map<String, Integer> map = new HashMap<>();
Set<String> set = new HashSet<>();

// ❌ Xấu - phụ thuộc vào implementation cụ thể
ArrayList<String> list = new ArrayList<>();
```

### 2. Chỉ định initial capacity khi biết trước size

```java
// ✅ Tránh resize nhiều lần
List<String> list = new ArrayList<>(1000);
Map<String, Integer> map = new HashMap<>(100);

// ❌ Không chỉ định → resize nhiều lần khi add nhiều phần tử
List<String> list = new ArrayList<>();
```

### 3. Sử dụng immutable collections khi có thể

```java
// ✅ An toàn, tránh modification ngoài ý muốn
List<String> constants = List.of("A", "B", "C");
Map<String, Integer> config = Map.of("timeout", 30, "retries", 3);

// Hoặc Collections.unmodifiableList()
List<String> readOnly = Collections.unmodifiableList(mutableList);
```

### 4. Ưu tiên isEmpty() thay vì size() == 0

```java
// ✅ Rõ ràng hơn, và có thể O(1) cho một số implementations
if (collection.isEmpty()) { ... }

// ❌ Không rõ ý nghĩa
if (collection.size() == 0) { ... }
```

### 5. Sử dụng diamond operator (Java 7+)

```java
// ✅ Gọn gàng
Map<String, List<Integer>> map = new HashMap<>();

// ❌ Redundant
Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
```

### 6. Tránh ConcurrentModificationException

```java
// ✅ Sử dụng Iterator.remove()
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    if (it.next().startsWith("A")) {
        it.remove();
    }
}

// ✅ Hoặc removeIf (Java 8+)
list.removeIf(s -> s.startsWith("A"));

// ❌ Xóa trực tiếp khi đang duyệt for-each
for (String s : list) {
    if (s.startsWith("A")) {
        list.remove(s); // ConcurrentModificationException!
    }
}
```

### 7. Override hashCode() khi override equals()

```java
public class Person {
    private String name;
    private int age;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
```

### 8. Sử dụng EnumSet và EnumMap cho Enum

```java
enum Day { MON, TUE, WED, THU, FRI, SAT, SUN }

// ✅ EnumSet - hiệu suất cao nhất cho enum
Set<Day> weekdays = EnumSet.range(Day.MON, Day.FRI);
Set<Day> weekend = EnumSet.of(Day.SAT, Day.SUN);

// ✅ EnumMap - map với enum key
Map<Day, String> schedule = new EnumMap<>(Day.class);
schedule.put(Day.MON, "Meeting");
```

---

> **Tóm tắt:** Collection Framework là nền tảng quan trọng trong Java. Hiểu rõ đặc điểm của từng implementation giúp bạn chọn đúng collection cho từng bài toán, tối ưu cả hiệu suất và khả năng bảo trì code.
