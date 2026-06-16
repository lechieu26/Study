# Quiz - Collection Framework

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Collection Framework hierarchy: Interface nào là root?

- [ ] List
- [x] Collection (và Map riêng biệt)
- [ ] Set
- [ ] Iterable

> **Giải thích:** Iterable → Collection → List, Set, Queue. Map là hierarchy riêng biệt. Collection cung cấp add, remove, contains, size, iterator.

## Câu 2

[TYPE: SELECT_RESULT]

```java
List<String> list = new ArrayList<>(List.of("A", "B", "C"));
list.add("D");
list.add(1, "X");
System.out.println(list);
```

- [x] [A, X, B, C, D]
- [ ] [A, B, C, D, X]
- [ ] [X, A, B, C, D]
- [ ] [A, B, X, C, D]

> **Giải thích:** add("D") → [A,B,C,D]. add(1,"X") → insert X tại index 1, shift phần còn lại → [A,X,B,C,D].

## Câu 3

[TYPE: FILL_BLANK]

Collection nào không cho phép phần tử trùng lặp? Đó là `___`.

- [x] Set
- [ ] List
- [ ] Queue
- [ ] Deque

> **Giải thích:** Set: no duplicates. HashSet (O(1), unordered), TreeSet (O(log n), sorted), LinkedHashSet (O(1), insertion order).

## Câu 4

[TYPE: TRUE_FALSE]

Mệnh đề: "ArrayList có random access O(1) nhưng insert/remove ở giữa O(n)."

- [x] Đúng
- [ ] Sai

> **Giải thích:** ArrayList dùng mảng bên trong: get(index) O(1). Insert/remove ở giữa cần shift elements → O(n). Add ở cuối amortized O(1).

## Câu 5

[TYPE: SELECT_RESULT]

```java
Set<String> set = new HashSet<>();
set.add("B");
set.add("A");
set.add("C");
set.add("A");
System.out.println(set.size());
```

- [ ] 4
- [x] 3
- [ ] 2
- [ ] Lỗi runtime

> **Giải thích:** HashSet không cho trùng. "A" add lần 2 → bị bỏ qua. Size = 3: {"A", "B", "C"} (thứ tự không đảm bảo).

## Câu 6

[TYPE: SELECT_RESULT]

```java
Map<String, Integer> map = new HashMap<>();
map.put("a", 1);
map.put("b", 2);
map.put("a", 3);
System.out.println(map.get("a") + " " + map.size());
```

- [x] 3 2
- [ ] 1 3
- [ ] 1 2
- [ ] 3 3

> **Giải thích:** put("a", 3) overwrite value cũ (1). map = {"a":3, "b":2}. get("a") = 3, size = 2.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

Sự khác biệt giữa HashMap và TreeMap?

- [x] HashMap: O(1) unordered; TreeMap: O(log n) sorted by key
- [ ] HashMap sorted, TreeMap unordered
- [ ] Không có sự khác biệt
- [ ] TreeMap nhanh hơn HashMap

> **Giải thích:** HashMap: hash table, O(1) average. TreeMap: Red-Black Tree, O(log n), keys sorted (natural order hoặc Comparator).

## Câu 8

[TYPE: SELECT_RESULT]

```java
Queue<Integer> queue = new LinkedList<>();
queue.offer(1);
queue.offer(2);
queue.offer(3);
System.out.print(queue.poll() + " ");
System.out.print(queue.peek() + " ");
System.out.print(queue.size());
```

- [x] 1 2 2
- [ ] 3 2 2
- [ ] 1 1 2
- [ ] 3 3 2

> **Giải thích:** offer: add. poll: remove+return head (1). peek: return head without remove (2). size: 2 (2 và 3 còn lại).

## Câu 9

[TYPE: FILL_BLANK]

LinkedHashMap khác HashMap ở chỗ nào? LinkedHashMap duy trì `___` order.

- [x] insertion
- [ ] natural
- [ ] random
- [ ] alphabetical

> **Giải thích:** LinkedHashMap: maintains insertion order (hoặc access order nếu config). HashMap: no ordering guarantee. TreeMap: sorted order.

## Câu 10

[TYPE: SELECT_RESULT]

```java
List<Integer> list = new ArrayList<>(List.of(3, 1, 4, 1, 5));
Collections.sort(list);
System.out.println(list);
int idx = Collections.binarySearch(list, 4);
System.out.println(idx);
```

- [x] [1, 1, 3, 4, 5] và 3
- [ ] [3, 1, 4, 1, 5] và 2
- [ ] [1, 1, 3, 4, 5] và 2
- [ ] [5, 4, 3, 1, 1] và 0

> **Giải thích:** sort → [1,1,3,4,5]. binarySearch(4) → index 3. binarySearch yêu cầu list đã sorted.

## Câu 11

[TYPE: TRUE_FALSE]

Mệnh đề: "ConcurrentHashMap cho phép nhiều thread đọc/ghi đồng thời mà không cần synchronized."

- [x] Đúng
- [ ] Sai

> **Giải thích:** ConcurrentHashMap: segment-level locking (Java 7) hoặc node-level CAS (Java 8+). Thread-safe mà không lock toàn bộ map. Nhanh hơn Collections.synchronizedMap.

## Câu 12

[TYPE: SELECT_RESULT]

```java
Map<String, Integer> map = new HashMap<>();
map.put("x", 10);
map.put("y", 20);
map.putIfAbsent("x", 30);
map.putIfAbsent("z", 30);
System.out.println(map);
```

- [x] {x=10, y=20, z=30}
- [ ] {x=30, y=20, z=30}
- [ ] {x=10, y=20}
- [ ] {x=30, y=20}

> **Giải thích:** putIfAbsent("x", 30): x đã có → không overwrite. putIfAbsent("z", 30): z chưa có → thêm mới.

## Câu 13

[TYPE: SELECT_RESULT]

```java
Deque<String> deque = new ArrayDeque<>();
deque.offerFirst("A");
deque.offerLast("B");
deque.offerFirst("C");
System.out.print(deque.pollFirst() + " ");
System.out.print(deque.pollLast() + " ");
System.out.print(deque.peek());
```

- [x] C B A
- [ ] A B C
- [ ] C A B
- [ ] B A C

> **Giải thích:** offerFirst("A")→[A]. offerLast("B")→[A,B]. offerFirst("C")→[C,A,B]. pollFirst→C. pollLast→B. peek→A (first element).

## Câu 14

[TYPE: MULTIPLE_CHOICE]

Khi nào nên dùng LinkedList thay vì ArrayList?

- [ ] Luôn luôn
- [x] Khi thường xuyên insert/remove ở đầu/giữa; ít random access
- [ ] Khi cần random access nhanh
- [ ] Khi cần sorted order

> **Giải thích:** LinkedList: insert/remove O(1) nếu có iterator. Random access O(n). ArrayList: random access O(1), insert/remove giữa O(n). ArrayList thường tốt hơn nhờ cache locality.

## Câu 15

[TYPE: SELECT_RESULT]

```java
TreeSet<Integer> set = new TreeSet<>();
set.add(5);
set.add(2);
set.add(8);
set.add(1);
System.out.println(set.first() + " " + set.last());
System.out.println(set.headSet(5));
```

- [x] 1 8 và [1, 2]
- [ ] 5 1 và [5, 2]
- [ ] 1 8 và [1, 2, 5]
- [ ] 2 8 và [1, 2]

> **Giải thích:** TreeSet sorted: {1,2,5,8}. first()=1, last()=8. headSet(5): elements < 5 → {1, 2}.

## Câu 16

[TYPE: SELECT_RESULT]

```java
Map<String, List<Integer>> map = new HashMap<>();
map.computeIfAbsent("scores", k -> new ArrayList<>()).add(85);
map.computeIfAbsent("scores", k -> new ArrayList<>()).add(90);
System.out.println(map.get("scores"));
```

- [x] [85, 90]
- [ ] [90]
- [ ] [85]
- [ ] null

> **Giải thích:** computeIfAbsent: tạo list nếu chưa có, trả về list hiện tại nếu đã có. Lần 1: tạo list, add 85. Lần 2: list đã có, add 90. → [85, 90].

## Câu 17

[TYPE: FILL_BLANK]

PriorityQueue trong Java implement `___` data structure.

- [x] Min-Heap
- [ ] Stack
- [ ] Linked List
- [ ] Red-Black Tree

> **Giải thích:** PriorityQueue: min-heap (default). poll() trả về phần tử nhỏ nhất. Có thể dùng Comparator cho max-heap: `new PriorityQueue<>(Comparator.reverseOrder())`.

## Câu 18

[TYPE: SELECT_RESULT]

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();
pq.offer(30);
pq.offer(10);
pq.offer(20);
StringBuilder sb = new StringBuilder();
while (!pq.isEmpty()) sb.append(pq.poll()).append(" ");
System.out.println(sb.toString().trim());
```

- [x] 10 20 30
- [ ] 30 20 10
- [ ] 30 10 20
- [ ] 10 30 20

> **Giải thích:** PriorityQueue (min-heap): poll luôn lấy min. 10 → 20 → 30. Sắp xếp tự nhiên.

## Câu 19

[TYPE: TRUE_FALSE]

Mệnh đề: "Iterator.remove() an toàn khi duyệt collection, trong khi collection.remove() trong for-each gây ConcurrentModificationException."

- [x] Đúng
- [ ] Sai

> **Giải thích:** For-each loop dùng Iterator ngầm. Gọi collection.remove() trong loop → ConcurrentModificationException. Dùng iterator.remove() hoặc removeIf().

## Câu 20

[TYPE: SELECT_RESULT]

```java
List<String> list = new ArrayList<>(List.of("a", "b", "c", "d"));
list.removeIf(s -> s.equals("b") || s.equals("d"));
System.out.println(list);
```

- [x] [a, c]
- [ ] [b, d]
- [ ] [a, b, c, d]
- [ ] ConcurrentModificationException

> **Giải thích:** removeIf: safe removal during iteration. Predicate match "b" và "d" → remove. Còn lại: [a, c].

## Câu 21

[TYPE: SELECT_RESULT]

```java
Map<String, Integer> map = Map.of("a", 1, "b", 2, "c", 3);
map.put("d", 4);
```

- [ ] map = {a=1, b=2, c=3, d=4}
- [x] UnsupportedOperationException
- [ ] map = {d=4}
- [ ] Lỗi biên dịch

> **Giải thích:** Map.of() tạo unmodifiable map. put() → UnsupportedOperationException. Tương tự: List.of(), Set.of().

## Câu 22

[TYPE: MULTIPLE_CHOICE]

EnumSet và EnumMap có đặc điểm gì?

- [x] Tối ưu cho enum keys/elements: nhanh hơn HashSet/HashMap, dùng bit vector
- [ ] Chậm hơn HashMap
- [ ] Chỉ dùng cho String
- [ ] Deprecated

> **Giải thích:** EnumSet: internally bit vector, rất nhanh và compact. EnumMap: internally array indexed by enum ordinal. Nhanh hơn HashMap cho enum keys.

## Câu 23

[TYPE: SELECT_RESULT]

```java
NavigableMap<Integer, String> map = new TreeMap<>();
map.put(1, "A");
map.put(3, "C");
map.put(5, "E");
map.put(7, "G");
System.out.println(map.floorEntry(4));
System.out.println(map.ceilingEntry(4));
```

- [x] 3=C và 5=E
- [ ] 4=null và 4=null
- [ ] 3=C và 4=null
- [ ] null và 5=E

> **Giải thích:** floorEntry(4): entry ≤ 4 → 3=C. ceilingEntry(4): entry ≥ 4 → 5=E. NavigableMap: floor, ceiling, lower, higher.

## Câu 24

[TYPE: FILL_BLANK]

Method `Collections.unmodifiableList(list)` trả về wrapper `___` không cho phép modification.

- [x] unmodifiable/read-only
- [ ] synchronized
- [ ] sorted
- [ ] concurrent

> **Giải thích:** Collections.unmodifiableList/Set/Map: read-only wrapper. add/remove/set → UnsupportedOperationException. View-only, không copy data.

## Câu 25

[TYPE: SELECT_RESULT]

```java
List<Integer> original = new ArrayList<>(List.of(1, 2, 3));
List<Integer> copy = new ArrayList<>(original);
copy.add(4);
System.out.println(original);
System.out.println(copy);
```

- [x] [1, 2, 3] và [1, 2, 3, 4]
- [ ] [1, 2, 3, 4] và [1, 2, 3, 4]
- [ ] [1, 2, 3] và [1, 2, 3]
- [ ] Lỗi runtime

> **Giải thích:** new ArrayList<>(original): shallow copy, tạo list mới. Add vào copy không ảnh hưởng original. Integer immutable nên safe.

## Câu 26

[TYPE: SELECT_RESULT]

```java
Map<String, Integer> map = new HashMap<>();
map.put("a", 1);
map.put("b", 2);
map.merge("a", 10, Integer::sum);
map.merge("c", 10, Integer::sum);
System.out.println(map);
```

- [x] {a=11, b=2, c=10}
- [ ] {a=10, b=2, c=10}
- [ ] {a=1, b=2, c=10}
- [ ] Lỗi runtime

> **Giải thích:** merge("a", 10, sum): a exists → 1+10=11. merge("c", 10, sum): c absent → put c=10. merge: powerful map operation.

## Câu 27

[TYPE: TRUE_FALSE]

Mệnh đề: "WeakHashMap cho phép garbage collector thu hồi key khi không còn strong reference."

- [x] Đúng
- [ ] Sai

> **Giải thích:** WeakHashMap: keys wrapped in WeakReference. Khi key không có strong ref → GC thu hồi → entry tự xóa. Dùng cho cache.

## Câu 28

[TYPE: SELECT_RESULT]

```java
List<String> list = List.of("banana", "apple", "cherry");
List<String> sorted = list.stream().sorted().collect(Collectors.toList());
System.out.println(sorted);
```

- [x] [apple, banana, cherry]
- [ ] [banana, apple, cherry]
- [ ] [cherry, banana, apple]
- [ ] Lỗi runtime

> **Giải thích:** sorted(): natural ordering (String → alphabetical). apple < banana < cherry.

## Câu 29

[TYPE: MULTIPLE_CHOICE]

Đâu là sự khác biệt giữa fail-fast và fail-safe iterators?

- [x] Fail-fast: throw ConcurrentModificationException khi modify; fail-safe: work on copy
- [ ] Fail-fast an toàn hơn
- [ ] Không có sự khác biệt
- [ ] Fail-safe nhanh hơn

> **Giải thích:** Fail-fast: ArrayList, HashMap → ConcurrentModificationException. Fail-safe: ConcurrentHashMap, CopyOnWriteArrayList → iterate copy.

## Câu 30

[TYPE: SELECT_RESULT]

```java
Set<Integer> a = new HashSet<>(Set.of(1, 2, 3, 4));
Set<Integer> b = new HashSet<>(Set.of(3, 4, 5, 6));
Set<Integer> union = new HashSet<>(a);
union.addAll(b);
Set<Integer> intersection = new HashSet<>(a);
intersection.retainAll(b);
System.out.println("Union: " + union.size());
System.out.println("Intersection: " + intersection);
```

- [x] Union: 6, Intersection: [3, 4]
- [ ] Union: 8, Intersection: [3, 4]
- [ ] Union: 6, Intersection: [1, 2, 5, 6]
- [ ] Union: 4, Intersection: [3, 4]

> **Giải thích:** Union = {1,2,3,4,5,6} size=6. Intersection: retainAll giữ phần tử chung = {3,4}.

## Câu 31

[TYPE: SELECT_RESULT]

```java
Map<String, Integer> map = new HashMap<>();
map.put("a", 1);
map.put("b", 2);
map.put("c", 3);
int sum = 0;
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    sum += entry.getValue();
}
System.out.println(sum);
```

- [x] 6
- [ ] 3
- [ ] 0
- [ ] Lỗi runtime

> **Giải thích:** Duyệt entrySet(): 1+2+3=6. entrySet() cho cả key và value. keySet() cho keys. values() cho values.

## Câu 32

[TYPE: FILL_BLANK]

Interface nào cho phép objects so sánh với nhau bằng method `compareTo()`? `___`

- [x] Comparable
- [ ] Comparator
- [ ] Sortable
- [ ] Ordered

> **Giải thích:** Comparable<T>: class implement compareTo(T). Natural ordering. Comparator<T>: external, compare(T, T). Dùng cho custom sort.

## Câu 33

[TYPE: SELECT_RESULT]

```java
class Student implements Comparable<Student> {
    String name; int score;
    Student(String n, int s) { name = n; score = s; }
    public int compareTo(Student o) { return Integer.compare(o.score, this.score); }
    public String toString() { return name + ":" + score; }
}
List<Student> list = new ArrayList<>(List.of(
    new Student("An", 80), new Student("Bình", 95), new Student("Cường", 70)));
Collections.sort(list);
System.out.println(list);
```

- [x] [Bình:95, An:80, Cường:70]
- [ ] [Cường:70, An:80, Bình:95]
- [ ] [An:80, Bình:95, Cường:70]
- [ ] Lỗi biên dịch

> **Giải thích:** compareTo: o.score - this.score → descending. Bình(95) > An(80) > Cường(70).

## Câu 34

[TYPE: SELECT_RESULT]

```java
Map<Integer, String> map = new LinkedHashMap<>();
map.put(3, "C");
map.put(1, "A");
map.put(2, "B");
System.out.println(map.keySet());
```

- [x] [3, 1, 2]
- [ ] [1, 2, 3]
- [ ] [2, 1, 3]
- [ ] Không đảm bảo thứ tự

> **Giải thích:** LinkedHashMap giữ insertion order. Insert 3→1→2 → keySet = [3, 1, 2]. HashMap không đảm bảo thứ tự.

## Câu 35

[TYPE: MULTIPLE_CHOICE]

ArrayList vs Vector?

- [x] ArrayList không synchronized (nhanh hơn), Vector synchronized (thread-safe nhưng chậm)
- [ ] Vector nhanh hơn ArrayList
- [ ] Không có sự khác biệt
- [ ] ArrayList thread-safe

> **Giải thích:** Vector: legacy, synchronized → chậm. ArrayList: không synchronized → nhanh. Dùng Collections.synchronizedList() hoặc CopyOnWriteArrayList cho thread-safety.

## Câu 36

[TYPE: SELECT_RESULT]

```java
List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5));
List<Integer> sub = list.subList(1, 4);
sub.set(0, 20);
System.out.println(list);
System.out.println(sub);
```

- [x] [1, 20, 3, 4, 5] và [20, 3, 4]
- [ ] [1, 2, 3, 4, 5] và [20, 3, 4]
- [ ] [1, 20, 3, 4, 5] và [2, 3, 4]
- [ ] Lỗi runtime

> **Giải thích:** subList: view (không copy). sub = [2,3,4] (index 1-3). sub.set(0,20) → thay đổi list[1] = 20. Cả hai reflect thay đổi.

## Câu 37

[TYPE: TRUE_FALSE]

Mệnh đề: "IdentityHashMap dùng == thay vì equals() để so sánh keys."

- [x] Đúng
- [ ] Sai

> **Giải thích:** IdentityHashMap: dùng reference equality (==) thay vì Object.equals(). Hai objects equals() nhưng != → hai entries khác nhau.

## Câu 38

[TYPE: SELECT_RESULT]

```java
Map<String, Integer> map = new HashMap<>();
map.put("a", 1);
map.put("b", 2);
map.replaceAll((k, v) -> v * 10);
System.out.println(map);
```

- [x] {a=10, b=20}
- [ ] {a=1, b=2}
- [ ] {a=10, b=2}
- [ ] Lỗi biên dịch

> **Giải thích:** replaceAll: apply function to all entries. v*10 cho mọi value. a: 1→10, b: 2→20.

## Câu 39

[TYPE: SELECT_RESULT]

```java
Set<String> set = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
set.add("Apple");
set.add("banana");
set.add("apple");
System.out.println(set.size() + " " + set);
```

- [x] 2 [Apple, banana]
- [ ] 3 [Apple, apple, banana]
- [ ] 2 [apple, banana]
- [ ] Lỗi biên dịch

> **Giải thích:** CASE_INSENSITIVE_ORDER: "Apple" == "apple" → add("apple") bị bỏ qua. Size=2. TreeSet sorted case-insensitive.

## Câu 40

[TYPE: MULTIPLE_CHOICE]

Collections.synchronizedMap vs ConcurrentHashMap?

- [x] synchronizedMap lock toàn bộ map; ConcurrentHashMap lock từng segment/node → concurrent reads
- [ ] synchronizedMap nhanh hơn
- [ ] Không có sự khác biệt
- [ ] ConcurrentHashMap không thread-safe

> **Giải thích:** synchronizedMap: một thread truy cập tại một thời điểm. ConcurrentHashMap: multiple readers concurrent, writers lock riêng phần → throughput cao hơn.

## Câu 41

[TYPE: SELECT_RESULT]

```java
List<String> list = new CopyOnWriteArrayList<>(List.of("A", "B", "C"));
for (String s : list) {
    if (s.equals("B")) list.remove(s);
}
System.out.println(list);
```

- [x] [A, C]
- [ ] ConcurrentModificationException
- [ ] [A, B, C]
- [ ] [A]

> **Giải thích:** CopyOnWriteArrayList: iterate on snapshot. Modification tạo copy mới. Không throw ConcurrentModificationException. "B" removed thành công.

## Câu 42

[TYPE: FILL_BLANK]

Method `Collections.___()` trả về empty immutable list.

- [x] emptyList
- [ ] newList
- [ ] blankList
- [ ] voidList

> **Giải thích:** Collections.emptyList(), emptySet(), emptyMap(): return immutable empty collections. Tốt hơn return null. Cũng có List.of() từ Java 9.

## Câu 43

[TYPE: SELECT_RESULT]

```java
Map<String, Integer> map = new HashMap<>();
map.put("x", 5);
Integer old = map.getOrDefault("y", 0);
map.put("y", old + 1);
System.out.println(map);
```

- [x] {x=5, y=1}
- [ ] {x=5, y=0}
- [ ] {x=5}
- [ ] NullPointerException

> **Giải thích:** getOrDefault("y", 0): y không có → trả 0. put("y", 0+1=1). map = {x=5, y=1}.

## Câu 44

[TYPE: SELECT_RESULT]

```java
List<Integer> nums = Arrays.asList(1, 2, 3);
nums.set(0, 10);
System.out.print(nums + " ");
nums.add(4);
```

- [x] [10, 2, 3] rồi UnsupportedOperationException
- [ ] [10, 2, 3, 4]
- [ ] [1, 2, 3] rồi UnsupportedOperationException
- [ ] Lỗi biên dịch

> **Giải thích:** Arrays.asList: fixed-size list backed by array. set() OK (modify element). add() → UnsupportedOperationException (cannot resize).

## Câu 45

[TYPE: MULTIPLE_CHOICE]

Khi nào nên dùng ArrayDeque thay vì LinkedList cho Stack/Queue operations?

- [x] Hầu hết mọi trường hợp vì ArrayDeque nhanh hơn (cache-friendly, no node allocation)
- [ ] Khi cần null elements
- [ ] Khi cần random access
- [ ] LinkedList luôn tốt hơn

> **Giải thích:** ArrayDeque: circular array, cache-friendly, no overhead of node objects. Nhanh hơn LinkedList cho stack/queue. Nhược điểm: không chấp nhận null.

## Câu 46

[TYPE: SELECT_RESULT]

```java
Map<String, Integer> scores = new HashMap<>();
scores.put("An", 80);
scores.put("Bình", 90);
scores.put("Cường", 85);
Optional<Map.Entry<String, Integer>> max = scores.entrySet().stream()
    .max(Map.Entry.comparingByValue());
System.out.println(max.get().getKey());
```

- [x] Bình
- [ ] An
- [ ] Cường
- [ ] 90

> **Giải thích:** max by value: Bình(90) > Cường(85) > An(80). max.get().getKey() → "Bình".

## Câu 47

[TYPE: TRUE_FALSE]

Mệnh đề: "TreeMap keys phải implement Comparable hoặc TreeMap phải nhận Comparator."

- [x] Đúng
- [ ] Sai

> **Giải thích:** TreeMap cần so sánh keys để sắp xếp. Keys implement Comparable (natural ordering) hoặc cung cấp Comparator khi tạo TreeMap.

## Câu 48

[TYPE: SELECT_RESULT]

```java
List<String> list = new ArrayList<>(List.of("A", "B", "C", "D", "E"));
Collections.rotate(list, 2);
System.out.println(list);
```

- [x] [D, E, A, B, C]
- [ ] [C, D, E, A, B]
- [ ] [A, B, C, D, E]
- [ ] [E, D, C, B, A]

> **Giải thích:** Collections.rotate(list, 2): shift phải 2 vị trí. D,E đến đầu, A,B,C shift sang phải.

## Câu 49

[TYPE: SELECT_RESULT]

```java
Map<String, Long> freq = Stream.of("a", "b", "a", "c", "b", "a")
    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
System.out.println(freq);
```

- [x] {a=3, b=2, c=1}
- [ ] {a=1, b=1, c=1}
- [ ] {a=3}
- [ ] Lỗi biên dịch

> **Giải thích:** groupingBy + counting: frequency map. a xuất hiện 3 lần, b 2 lần, c 1 lần.

## Câu 50

[TYPE: MULTIPLE_CHOICE]

Hashtable vs HashMap?

- [x] Hashtable: legacy, synchronized, no null key/value; HashMap: modern, unsynchronized, allows null
- [ ] Hashtable nhanh hơn
- [ ] HashMap không cho phép null
- [ ] Không có sự khác biệt

> **Giải thích:** Hashtable: legacy, thread-safe (synchronized), no null. HashMap: modern, faster, not thread-safe, allows 1 null key, many null values.

## Câu 51

[TYPE: SELECT_RESULT]

```java
List<Integer> list = new ArrayList<>(List.of(5, 3, 8, 1, 9));
list.sort(Comparator.reverseOrder());
System.out.println(list.get(0) + " " + list.get(list.size()-1));
```

- [x] 9 1
- [ ] 1 9
- [ ] 5 9
- [ ] 9 5

> **Giải thích:** Comparator.reverseOrder(): descending. [9,8,5,3,1]. get(0)=9, get(4)=1.

## Câu 52

[TYPE: FILL_BLANK]

`List.copyOf(collection)` trả về `___` copy.

- [x] unmodifiable (immutable)
- [ ] mutable
- [ ] synchronized
- [ ] deep

> **Giải thích:** List.copyOf(): tạo unmodifiable list mới. add/remove → UnsupportedOperationException. Java 10+. Tương tự Set.copyOf(), Map.copyOf().

## Câu 53

[TYPE: SELECT_RESULT]

```java
Map<String, Integer> map = new HashMap<>();
map.put("a", 5);
map.compute("a", (k, v) -> v == null ? 1 : v + 10);
map.compute("b", (k, v) -> v == null ? 1 : v + 10);
System.out.println(map);
```

- [x] {a=15, b=1}
- [ ] {a=10, b=1}
- [ ] {a=5, b=null}
- [ ] Lỗi runtime

> **Giải thích:** compute("a"): v=5 ≠ null → 5+10=15. compute("b"): v=null → 1. compute: tính value mới từ key và current value.

## Câu 54

[TYPE: SELECT_RESULT]

```java
Set<List<Integer>> set = new HashSet<>();
List<Integer> list1 = new ArrayList<>(List.of(1, 2));
List<Integer> list2 = new ArrayList<>(List.of(1, 2));
set.add(list1);
set.add(list2);
System.out.println(set.size());
list1.add(3);
set.add(list1);
System.out.println(set.size());
```

- [x] 1 rồi 2
- [ ] 2 rồi 3
- [ ] 1 rồi 1
- [ ] 2 rồi 2

> **Giải thích:** list1.equals(list2) → true (same content). Set size=1. Sau list1.add(3): hashCode thay đổi! set.add(list1) lại → size=2 (old hash position + new). Danger: mutable objects as hash keys.

## Câu 55

[TYPE: MULTIPLE_CHOICE]

Spliterator trong Java 8 dùng để làm gì?

- [x] Parallel traversal/splitting of data sources (parallel stream)
- [ ] Split strings
- [ ] Remove duplicates
- [ ] Sort collection

> **Giải thích:** Spliterator: split + iterator. trySplit() chia thành 2 parts cho parallel processing. tryAdvance() consume elements. Dùng trong parallel streams.

## Câu 56

[TYPE: SELECT_RESULT]

```java
List<String> list = new ArrayList<>(List.of("a", "b", "c", "d"));
ListIterator<String> it = list.listIterator(list.size());
StringBuilder sb = new StringBuilder();
while (it.hasPrevious()) sb.append(it.previous());
System.out.println(sb);
```

- [x] dcba
- [ ] abcd
- [ ] Lỗi runtime
- [ ] dcb

> **Giải thích:** ListIterator(list.size()): start ở cuối. hasPrevious()/previous(): duyệt ngược. d→c→b→a = "dcba".

## Câu 57

[TYPE: TRUE_FALSE]

Mệnh đề: "HashMap cho phép 1 null key và nhiều null values."

- [x] Đúng
- [ ] Sai

> **Giải thích:** HashMap: 1 null key OK (hash=0, bucket 0). Multiple null values OK. TreeMap: null key → NullPointerException. ConcurrentHashMap: no null key/value.

## Câu 58

[TYPE: SELECT_RESULT]

```java
Map<String, Integer> map = Map.ofEntries(
    Map.entry("a", 1),
    Map.entry("b", 2),
    Map.entry("c", 3)
);
System.out.println(map.containsKey("b") + " " + map.containsValue(5));
```

- [x] true false
- [ ] true true
- [ ] false false
- [ ] false true

> **Giải thích:** containsKey("b") → true. containsValue(5) → false (only 1,2,3). Map.ofEntries: unmodifiable map (Java 9+).

## Câu 59

[TYPE: SELECT_RESULT]

```java
List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5));
Collections.swap(list, 0, 4);
Collections.reverse(list.subList(1, 4));
System.out.println(list);
```

- [x] [5, 4, 3, 2, 1]
- [ ] [5, 2, 3, 4, 1]
- [ ] [1, 4, 3, 2, 5]
- [ ] [5, 3, 2, 4, 1]

> **Giải thích:** swap(0,4): [5,2,3,4,1]. reverse subList(1,4)=[2,3,4]→[4,3,2]. list = [5,4,3,2,1].

## Câu 60

[TYPE: MULTIPLE_CHOICE]

Sự khác biệt giữa Comparable và Comparator?

- [x] Comparable: natural ordering trong class (compareTo); Comparator: external, custom ordering (compare)
- [ ] Comparable nhanh hơn
- [ ] Comparator dùng trong class
- [ ] Không có sự khác biệt

> **Giải thích:** Comparable: `class X implements Comparable<X>`, compareTo. Comparator: `Comparator<X> comp = ...`, compare(a,b). Comparator linh hoạt hơn, nhiều sort criteria.

## Câu 61

[TYPE: SELECT_RESULT]

```java
List<String> list = new ArrayList<>(List.of("C", "A", "B"));
list.sort(Comparator.comparing(String::toLowerCase));
System.out.println(list);
```

- [x] [A, B, C]
- [ ] [C, A, B]
- [ ] [a, b, c]
- [ ] Lỗi biên dịch

> **Giải thích:** Comparator.comparing(String::toLowerCase): sort by lowercase value. A < B < C. Original case preserved.

## Câu 62

[TYPE: SELECT_RESULT]

```java
Map<String, List<String>> grouped = Stream.of("An", "Anh", "Bình", "Bảo", "Cường")
    .collect(Collectors.groupingBy(s -> s.substring(0, 1)));
System.out.println(grouped.get("A").size());
System.out.println(grouped.get("B").size());
```

- [x] 2 và 2
- [ ] 2 và 1
- [ ] 1 và 2
- [ ] 3 và 2

> **Giải thích:** Group by first char. A: [An, Anh]=2. B: [Bình, Bảo]=2. C: [Cường]=1.

## Câu 63

[TYPE: TRUE_FALSE]

Mệnh đề: "CopyOnWriteArrayList tạo copy mới mỗi lần write, phù hợp khi read >> write."

- [x] Đúng
- [ ] Sai

> **Giải thích:** CopyOnWriteArrayList: mỗi add/remove tạo mảng mới. Read lock-free. Write expensive. Tốt cho: event listeners, configuration lists.

## Câu 64

[TYPE: SELECT_RESULT]

```java
Deque<Integer> stack = new ArrayDeque<>();
stack.push(1);
stack.push(2);
stack.push(3);
System.out.println(stack); // ArrayDeque prints head first
System.out.println(stack.pop());
```

- [x] [3, 2, 1] và 3
- [ ] [1, 2, 3] và 1
- [ ] [3, 2, 1] và 1
- [ ] [1, 2, 3] và 3

> **Giải thích:** push = addFirst. Stack: 3 ở đỉnh. toString prints head first → [3,2,1]. pop = removeFirst → 3.

## Câu 65

[TYPE: SELECT_RESULT]

```java
Map<String, Integer> map = new HashMap<>();
map.put("a", 1);
map.put("b", 2);
map.forEach((k, v) -> System.out.print(k + "=" + v + " "));
```

- [x] Prints a=1 b=2 (hoặc b=2 a=1, thứ tự không đảm bảo)
- [ ] Lỗi biên dịch
- [ ] Không in gì
- [ ] Lỗi runtime

> **Giải thích:** Map.forEach(BiConsumer): iterate key-value pairs. HashMap không đảm bảo thứ tự. In "a=1 b=2" hoặc "b=2 a=1".

## Câu 66

[TYPE: FILL_BLANK]

Method `Collections.frequency(collection, element)` trả về số lần `___` xuất hiện trong collection.

- [x] element
- [ ] collection
- [ ] null
- [ ] first

> **Giải thích:** Collections.frequency: đếm số lần element xuất hiện. frequency(List.of(1,2,1,3,1), 1) → 3.

## Câu 67

[TYPE: SELECT_RESULT]

```java
SortedSet<Integer> set = new TreeSet<>(Set.of(5, 1, 8, 3, 9));
System.out.println(set.subSet(3, 8));
System.out.println(set.tailSet(5));
```

- [x] [3, 5] và [5, 8, 9]
- [ ] [3, 5, 8] và [5, 8, 9]
- [ ] [3, 5] và [8, 9]
- [ ] Lỗi runtime

> **Giải thích:** TreeSet: {1,3,5,8,9}. subSet(3,8): [3, fromInclusive) to [8, toExclusive) → {3,5}. tailSet(5): >= 5 → {5,8,9}.

## Câu 68

[TYPE: SELECT_RESULT]

```java
record Person(String name, int age) {}
List<Person> people = List.of(
    new Person("An", 25),
    new Person("Bình", 30),
    new Person("Cường", 25));
Map<Integer, List<Person>> byAge = people.stream()
    .collect(Collectors.groupingBy(Person::age));
System.out.println(byAge.get(25).size());
```

- [x] 2
- [ ] 1
- [ ] 3
- [ ] 0

> **Giải thích:** groupingBy age: 25 → [An, Cường] (2 people). 30 → [Bình] (1 person). get(25).size() = 2.

## Câu 69

[TYPE: MULTIPLE_CHOICE]

`Collections.checkedList(list, type)` dùng để:

- [x] Tạo type-safe view, throw ClassCastException nếu thêm sai type tại runtime
- [ ] Sort list
- [ ] Copy list
- [ ] Sync list

> **Giải thích:** checkedList: runtime type checking. Prevent heap pollution (raw types adding wrong type). `Collections.checkedList(list, String.class)`.

## Câu 70

[TYPE: SELECT_RESULT]

```java
BitSet bits = new BitSet(8);
bits.set(1);
bits.set(3);
bits.set(5);
System.out.println(bits.cardinality());
bits.flip(3);
System.out.println(bits.get(3));
```

- [x] 3 và false
- [ ] 3 và true
- [ ] 8 và false
- [ ] Lỗi biên dịch

> **Giải thích:** set(1,3,5): 3 bits set. cardinality()=3. flip(3): toggle bit 3 → off. get(3)=false.

## Câu 71

[TYPE: TRUE_FALSE]

Mệnh đề: "HashMap resize (rehash) khi số elements vượt quá capacity × load factor."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Default: capacity=16, loadFactor=0.75. Resize tại 16×0.75=12 elements. Resize doubles capacity. Rehash: recompute bucket cho tất cả entries.

## Câu 72

[TYPE: SELECT_RESULT]

```java
List<Integer> list = List.of(1, 2, 3, 4, 5);
int sum = list.stream().reduce(0, Integer::sum);
System.out.println(sum);
```

- [x] 15
- [ ] 0
- [ ] 5
- [ ] 120

> **Giải thích:** reduce(0, Integer::sum): 0+1+2+3+4+5 = 15. Identity=0, accumulator=sum.

## Câu 73

[TYPE: SELECT_RESULT]

```java
Map<String, String> map = new HashMap<>();
map.put("key", "old");
map.replace("key", "old", "new");
map.replace("key2", "val", "new2");
System.out.println(map);
```

- [x] {key=new}
- [ ] {key=old, key2=new2}
- [ ] {key=old}
- [ ] {key=new, key2=new2}

> **Giải thích:** replace(key, oldValue, newValue): chỉ replace nếu current value == oldValue. "key": old→new ✓. "key2": không tồn tại → no-op.

## Câu 74

[TYPE: MULTIPLE_CHOICE]

NavigableSet thêm gì so với SortedSet?

- [x] floor, ceiling, lower, higher, descendingSet, subSet with inclusive flags
- [ ] Không thêm gì
- [ ] Add/remove methods
- [ ] Parallel iteration

> **Giải thích:** NavigableSet: floor(e)≤e, ceiling(e)≥e, lower(e)<e, higher(e)>e, pollFirst, pollLast, descendingSet. TreeSet implements NavigableSet.

## Câu 75

[TYPE: SELECT_RESULT]

```java
Map<String, Integer> map1 = Map.of("a", 1, "b", 2);
Map<String, Integer> map2 = Map.of("b", 3, "c", 4);
Map<String, Integer> merged = new HashMap<>(map1);
map2.forEach((k, v) -> merged.merge(k, v, Integer::sum));
System.out.println(merged);
```

- [x] {a=1, b=5, c=4}
- [ ] {a=1, b=3, c=4}
- [ ] {a=1, b=2, c=4}
- [ ] Lỗi biên dịch

> **Giải thích:** merged = {a=1, b=2}. merge b: 2+3=5. merge c: absent → 4. Result: {a=1, b=5, c=4}.

## Câu 76

[TYPE: FILL_BLANK]

`Collections.singletonList(element)` trả về immutable list chứa `___` phần tử.

- [x] 1 (đúng 1)
- [ ] 0
- [ ] Nhiều
- [ ] null

> **Giải thích:** singletonList: immutable list chứa đúng 1 phần tử. singletonSet, singletonMap tương tự. Lightweight, no array allocation.

## Câu 77

[TYPE: SELECT_RESULT]

```java
List<String> list = new ArrayList<>(List.of("Apple", "Banana", "Cherry"));
String result = String.join(", ", list);
System.out.println(result);
```

- [x] Apple, Banana, Cherry
- [ ] AppleBananaCherry
- [ ] [Apple, Banana, Cherry]
- [ ] Apple,Banana,Cherry

> **Giải thích:** String.join(delimiter, iterable): nối elements với ", " separator. String join = clean, efficient concatenation.

## Câu 78

[TYPE: SELECT_RESULT]

```java
Optional<String> opt = Optional.of("Hello");
String result = opt.filter(s -> s.length() > 3)
                   .map(String::toUpperCase)
                   .orElse("DEFAULT");
System.out.println(result);
```

- [x] HELLO
- [ ] Hello
- [ ] DEFAULT
- [ ] null

> **Giải thích:** filter: "Hello".length()=5 > 3 ✓ → Optional[Hello]. map: toUpperCase → Optional[HELLO]. orElse: has value → "HELLO".

## Câu 79

[TYPE: MULTIPLE_CHOICE]

Đâu là thread-safe collections trong java.util.concurrent?

- [x] ConcurrentHashMap, CopyOnWriteArrayList, ConcurrentLinkedQueue, BlockingQueue
- [ ] ArrayList, HashMap
- [ ] TreeMap, HashSet
- [ ] Không có collection nào thread-safe

> **Giải thích:** java.util.concurrent: ConcurrentHashMap, ConcurrentSkipListMap, CopyOnWriteArrayList, ConcurrentLinkedQueue, LinkedBlockingQueue, ArrayBlockingQueue.

## Câu 80

[TYPE: SELECT_RESULT]

```java
List<Integer> list = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
Collections.shuffle(list, new Random(42));
System.out.println(list.subList(0, 3));
```

Kết quả phụ thuộc vào:

- [x] Random seed (42) - luôn cho cùng kết quả với cùng seed
- [ ] Thời gian chạy
- [ ] JVM version
- [ ] Luôn [1, 2, 3]

> **Giải thích:** Random(42): deterministic pseudo-random. Cùng seed → cùng shuffle sequence. Reproducible for testing.

## Câu 81

[TYPE: SELECT_RESULT]

```java
Map<String, Integer> map = new HashMap<>();
map.put("a", 1);
map.put("b", null);
System.out.println(map.getOrDefault("b", 99));
System.out.println(map.getOrDefault("c", 99));
```

- [x] null và 99
- [ ] 99 và 99
- [ ] null và null
- [ ] 1 và 99

> **Giải thích:** getOrDefault: key exists → return value (even null). "b" exists → null. "c" absent → default 99.

## Câu 82

[TYPE: TRUE_FALSE]

Mệnh đề: "Arrays.asList() trả về java.util.ArrayList."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Arrays.asList trả về java.util.Arrays$ArrayList (inner class), KHÔNG phải java.util.ArrayList. Fixed size, backed by array. Cannot add/remove.

## Câu 83

[TYPE: SELECT_RESULT]

```java
TreeMap<String, Integer> map = new TreeMap<>();
map.put("charlie", 3);
map.put("alpha", 1);
map.put("bravo", 2);
System.out.println(map.firstKey() + " " + map.lastKey());
```

- [x] alpha charlie
- [ ] charlie alpha
- [ ] alpha bravo
- [ ] bravo charlie

> **Giải thích:** TreeMap sorted by key. Alphabetical: alpha < bravo < charlie. firstKey()=alpha, lastKey()=charlie.

## Câu 84

[TYPE: SELECT_RESULT]

```java
List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5));
Iterator<Integer> it = list.iterator();
while (it.hasNext()) {
    int val = it.next();
    if (val % 2 == 0) it.remove();
}
System.out.println(list);
```

- [x] [1, 3, 5]
- [ ] [2, 4]
- [ ] ConcurrentModificationException
- [ ] [1, 2, 3, 4, 5]

> **Giải thích:** Iterator.remove(): safe removal during iteration. Remove even numbers (2, 4). Còn lại: [1, 3, 5].

## Câu 85

[TYPE: MULTIPLE_CHOICE]

`Map.of()` (Java 9) có giới hạn gì?

- [x] Tối đa 10 entries, no null keys/values, unmodifiable
- [ ] Không giới hạn
- [ ] Cho phép null
- [ ] Modifiable

> **Giải thích:** Map.of(): tối đa 10 key-value pairs. No null keys/values. Unmodifiable. Dùng Map.ofEntries() cho > 10 entries.

## Câu 86

[TYPE: SELECT_RESULT]

```java
List<String> names = List.of("An", "Bình", "Cường", "An", "Dung");
long distinct = names.stream().distinct().count();
System.out.println(distinct);
```

- [x] 4
- [ ] 5
- [ ] 3
- [ ] 2

> **Giải thích:** distinct(): remove duplicates. "An" appears twice → keep once. Distinct: An, Bình, Cường, Dung = 4.

## Câu 87

[TYPE: SELECT_RESULT]

```java
Map<String, Integer> map = new HashMap<>();
map.put("a", 1);
map.put("b", 2);
map.put("c", 3);
map.values().removeIf(v -> v > 1);
System.out.println(map);
```

- [x] {a=1}
- [ ] {a=1, b=2, c=3}
- [ ] UnsupportedOperationException
- [ ] {}

> **Giải thích:** map.values() là live view. removeIf removes from map too. Values > 1: b(2), c(3) removed. Còn lại: {a=1}.

## Câu 88

[TYPE: FILL_BLANK]

Java 9 thêm factory method `___` để tạo immutable collections.

- [x] List.of() / Set.of() / Map.of()
- [ ] Collections.of()
- [ ] ImmutableList.of()
- [ ] Array.of()

> **Giải thích:** Java 9: List.of(), Set.of(), Map.of(), Map.ofEntries(). Immutable, null-intolerant, compact.

## Câu 89

[TYPE: SELECT_RESULT]

```java
List<String> list = new ArrayList<>(List.of("banana", "apple", "cherry", "date"));
list.sort(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()));
System.out.println(list);
```

- [x] [date, apple, banana, cherry]
- [ ] [apple, banana, cherry, date]
- [ ] [date, cherry, banana, apple]
- [ ] [banana, apple, cherry, date]

> **Giải thích:** Sort by length first: date(4), apple(5), banana(6), cherry(6). Same length → natural order: banana < cherry. Result: [date, apple, banana, cherry].

## Câu 90

[TYPE: SELECT_RESULT]

```java
Map<String, Integer> map = new HashMap<>();
map.put("a", 10);
map.put("b", 20);
map.put("c", 30);
int total = map.values().stream().mapToInt(Integer::intValue).sum();
System.out.println(total);
```

- [x] 60
- [ ] 30
- [ ] 0
- [ ] Lỗi biên dịch

> **Giải thích:** values(): [10, 20, 30]. stream + mapToInt + sum = 10+20+30 = 60.

## Câu 91

[TYPE: TRUE_FALSE]

Mệnh đề: "Stack class trong Java được coi là legacy, nên dùng ArrayDeque thay thế."

- [x] Đúng
- [ ] Sai

> **Giải thích:** java.util.Stack extends Vector (synchronized, slow). ArrayDeque: faster, recommended. Deque interface: push/pop/peek.

## Câu 92

[TYPE: SELECT_RESULT]

```java
Set<String> set1 = new HashSet<>(Set.of("A", "B", "C"));
Set<String> set2 = new HashSet<>(Set.of("B", "C", "D"));
set1.removeAll(set2);
System.out.println(set1);
```

- [x] [A]
- [ ] [D]
- [ ] [B, C]
- [ ] [A, D]

> **Giải thích:** removeAll: xóa tất cả elements có trong set2. {A,B,C} - {B,C,D} = {A}. Difference operation.

## Câu 93

[TYPE: SELECT_RESULT]

```java
List<String> list = new ArrayList<>(List.of("a", "b", "c"));
list.replaceAll(String::toUpperCase);
System.out.println(list);
```

- [x] [A, B, C]
- [ ] [a, b, c]
- [ ] [a, B, c]
- [ ] Lỗi biên dịch

> **Giải thích:** replaceAll(UnaryOperator): transform each element in-place. toUpperCase cho mỗi element.

## Câu 94

[TYPE: MULTIPLE_CHOICE]

Đâu là implementation tốt nhất cho single-threaded Stack?

- [x] ArrayDeque
- [ ] Stack
- [ ] LinkedList
- [ ] Vector

> **Giải thích:** ArrayDeque: no synchronization overhead, array-based (cache-friendly), no node allocation. Stack/Vector: synchronized → chậm. LinkedList: node allocation overhead.

## Câu 95

[TYPE: SELECT_RESULT]

```java
Map<Integer, String> map = new TreeMap<>(Comparator.reverseOrder());
map.put(1, "A");
map.put(3, "C");
map.put(2, "B");
System.out.println(new ArrayList<>(map.values()));
```

- [x] [C, B, A]
- [ ] [A, B, C]
- [ ] [A, C, B]
- [ ] Lỗi biên dịch

> **Giải thích:** TreeMap(reverseOrder): keys sorted descending. 3→C, 2→B, 1→A. values() → [C, B, A].

## Câu 96

[TYPE: SELECT_RESULT]

```java
List<Integer> list = List.of(1, 2, 3, 4, 5);
Map<Boolean, List<Integer>> partitioned = list.stream()
    .collect(Collectors.partitioningBy(n -> n % 2 == 0));
System.out.println(partitioned);
```

- [x] {false=[1, 3, 5], true=[2, 4]}
- [ ] {true=[1, 2, 3, 4, 5]}
- [ ] {false=[1, 3, 5]}
- [ ] Lỗi biên dịch

> **Giải thích:** partitioningBy: chia thành 2 groups (true/false). Even: [2,4]. Odd: [1,3,5].

## Câu 97

[TYPE: TRUE_FALSE]

Mệnh đề: "Java Collections Framework sử dụng generics để đảm bảo type safety tại compile time."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Generics: `List<String>` chỉ chấp nhận String. Compile-time type checking. Tránh ClassCastException tại runtime. Type erasure tại runtime.

## Câu 98

[TYPE: SELECT_RESULT]

```java
List<String> list = new ArrayList<>(List.of("x", "y", "z"));
Spliterator<String> sp = list.spliterator();
sp.tryAdvance(System.out::print);
sp.tryAdvance(System.out::print);
sp.forEachRemaining(System.out::print);
```

- [x] xyz
- [ ] xy
- [ ] z
- [ ] xyzxyz

> **Giải thích:** tryAdvance: consume 1 element. First: x. Second: y. forEachRemaining: z. Total: xyz.

## Câu 99

[TYPE: SELECT_RESULT]

```java
Map<String, Integer> map = new HashMap<>();
map.put("a", 1);
map.put("b", 2);
Map<String, Integer> unmod = Collections.unmodifiableMap(map);
map.put("c", 3);
System.out.println(unmod.size());
```

- [x] 3
- [ ] 2
- [ ] UnsupportedOperationException
- [ ] Lỗi biên dịch

> **Giải thích:** unmodifiableMap: wrapper view, NOT copy. Modifying original map → reflected in unmodifiable view. size = 3. unmod.put() → exception, nhưng map.put() OK.

## Câu 100

[TYPE: FILL_BLANK]

`Collections.disjoint(c1, c2)` trả về true khi 2 collections không có phần tử `___`.

- [x] chung (common)
- [ ] null
- [ ] trùng
- [ ] sorted

> **Giải thích:** disjoint: true nếu intersection rỗng. disjoint([1,2], [3,4]) → true. disjoint([1,2], [2,3]) → false (share 2).
