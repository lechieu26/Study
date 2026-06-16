# Quiz - Stream API

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Stream API trong Java 8 là gì?

- [x] API cho xử lý dữ liệu theo pipeline: source → intermediate ops → terminal op
- [ ] Thay thế Collections
- [ ] Chỉ cho file I/O
- [ ] Chỉ cho parallel processing

> **Giải thích:** Stream: functional-style operations trên sequences. Lazy evaluation (intermediate ops). Single-use. Không lưu trữ data. Source: Collection, array, I/O, generator.

## Câu 2

[TYPE: SELECT_RESULT]

```java
List<Integer> list = List.of(1, 2, 3, 4, 5);
List<Integer> result = list.stream()
    .filter(n -> n % 2 == 0)
    .collect(Collectors.toList());
System.out.println(result);
```

- [x] [2, 4]
- [ ] [1, 3, 5]
- [ ] [1, 2, 3, 4, 5]
- [ ] []

> **Giải thích:** filter: giữ elements thỏa predicate. n%2==0: even numbers. 2, 4 pass filter. collect(toList()) → [2, 4].

## Câu 3

[TYPE: FILL_BLANK]

Stream operation `map()` thuộc loại `___` operation (lazy, trả về Stream).

- [x] intermediate
- [ ] terminal
- [ ] source
- [ ] eager

> **Giải thích:** Intermediate: filter, map, sorted, distinct, limit, skip, flatMap, peek. Terminal: collect, forEach, reduce, count, findFirst, anyMatch. Lazy: chỉ chạy khi có terminal op.

## Câu 4

[TYPE: SELECT_RESULT]

```java
List<String> names = List.of("An", "Bình", "Cường", "Dung");
String result = names.stream()
    .map(String::toUpperCase)
    .collect(Collectors.joining(", "));
System.out.println(result);
```

- [x] AN, BÌNH, CƯỜNG, DUNG
- [ ] An, Bình, Cường, Dung
- [ ] [AN, BÌNH, CƯỜNG, DUNG]
- [ ] ANBÌNHCƯỜNGDUNG

> **Giải thích:** map(toUpperCase): transform each name. joining(", "): concatenate with separator. Result: single String.

## Câu 5

[TYPE: TRUE_FALSE]

Mệnh đề: "Stream chỉ có thể được consumed (sử dụng) một lần. Gọi terminal operation lần 2 sẽ throw IllegalStateException."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Stream single-use. Sau terminal op → stream closed. Tạo stream mới nếu cần reprocess. IllegalStateException: "stream has already been operated upon or closed".

## Câu 6

[TYPE: SELECT_RESULT]

```java
List<Integer> nums = List.of(3, 1, 4, 1, 5, 9, 2, 6);
List<Integer> result = nums.stream()
    .distinct()
    .sorted()
    .limit(4)
    .collect(Collectors.toList());
System.out.println(result);
```

- [x] [1, 2, 3, 4]
- [ ] [3, 1, 4, 5]
- [ ] [1, 1, 2, 3]
- [ ] [1, 2, 3, 4, 5, 6, 9]

> **Giải thích:** distinct: remove duplicates → {3,1,4,5,9,2,6}. sorted: [1,2,3,4,5,6,9]. limit(4): [1,2,3,4].

## Câu 7

[TYPE: SELECT_RESULT]

```java
int sum = IntStream.rangeClosed(1, 5).sum();
long count = IntStream.rangeClosed(1, 5).count();
OptionalInt max = IntStream.rangeClosed(1, 5).max();
System.out.println(sum + " " + count + " " + max.getAsInt());
```

- [x] 15 5 5
- [ ] 10 4 5
- [ ] 15 5 1
- [ ] 10 5 4

> **Giải thích:** rangeClosed(1,5): [1,2,3,4,5]. sum=15. count=5. max=5. IntStream: primitive specialization, no boxing.

## Câu 8

[TYPE: MULTIPLE_CHOICE]

flatMap vs map?

- [x] map: 1-to-1 transform; flatMap: 1-to-many transform rồi flatten thành single stream
- [ ] Giống nhau
- [ ] flatMap nhanh hơn
- [ ] map cho collections, flatMap cho primitives

> **Giải thích:** map: Stream<T> → Stream<R> (1:1). flatMap: Stream<T> → Stream<R> (1:N, flatten). Ví dụ: list of lists → single flat list.

## Câu 9

[TYPE: SELECT_RESULT]

```java
List<List<Integer>> nested = List.of(List.of(1, 2), List.of(3, 4), List.of(5));
List<Integer> flat = nested.stream()
    .flatMap(Collection::stream)
    .collect(Collectors.toList());
System.out.println(flat);
```

- [x] [1, 2, 3, 4, 5]
- [ ] [[1, 2], [3, 4], [5]]
- [ ] [1, 2]
- [ ] Lỗi biên dịch

> **Giải thích:** flatMap: mỗi inner list → stream, flatten tất cả thành 1 stream. [1,2] + [3,4] + [5] → [1,2,3,4,5].

## Câu 10

[TYPE: SELECT_RESULT]

```java
Optional<String> result = List.of("abc", "de", "fghi", "j")
    .stream()
    .filter(s -> s.length() > 2)
    .findFirst();
System.out.println(result.orElse("none"));
```

- [x] abc
- [ ] fghi
- [ ] none
- [ ] de

> **Giải thích:** filter(length > 2): "abc"(3) ✓, "fghi"(4) ✓. findFirst(): short-circuit, return first match. "abc".

## Câu 11

[TYPE: FILL_BLANK]

`Collectors.groupingBy()` trả về `Map<K, ___>`.

- [x] List<T>
- [ ] Set<T>
- [ ] T
- [ ] Stream<T>

> **Giải thích:** groupingBy(classifier): Map<K, List<T>>. Downstream collector: groupingBy(classifier, toSet()) → Map<K, Set<T>>. groupingBy(classifier, counting()) → Map<K, Long>.

## Câu 12

[TYPE: SELECT_RESULT]

```java
Map<Integer, List<String>> grouped = Stream.of("An", "Bình", "Cường", "Du", "Em")
    .collect(Collectors.groupingBy(String::length));
System.out.println(grouped.get(2));
```

- [x] [An, Du, Em]
- [ ] [Bình]
- [ ] [Cường]
- [ ] null

> **Giải thích:** groupingBy(length): 2 → [An, Du, Em]. 4 → [Bình]. 5 → [Cường]. get(2) → danh sách tên 2 ký tự.

## Câu 13

[TYPE: SELECT_RESULT]

```java
int result = Stream.of(1, 2, 3, 4, 5)
    .reduce(0, (a, b) -> a + b);
System.out.println(result);
```

- [x] 15
- [ ] 0
- [ ] 5
- [ ] 120

> **Giải thích:** reduce(identity=0, accumulator=sum): 0+1+2+3+4+5 = 15. reduce: combine elements thành single value.

## Câu 14

[TYPE: TRUE_FALSE]

Mệnh đề: "peek() dùng cho debugging, nó là intermediate operation không thay đổi stream elements."

- [x] Đúng
- [ ] Sai

> **Giải thích:** peek: intermediate, nhận Consumer. Dùng cho logging/debugging. Không modify elements (though side effects possible). Lazy: chỉ chạy khi terminal op triggered.

## Câu 15

[TYPE: SELECT_RESULT]

```java
boolean allPositive = List.of(1, 2, 3, 4, 5).stream().allMatch(n -> n > 0);
boolean anyNegative = List.of(1, -2, 3).stream().anyMatch(n -> n < 0);
boolean noneZero = List.of(1, 2, 3).stream().noneMatch(n -> n == 0);
System.out.println(allPositive + " " + anyNegative + " " + noneZero);
```

- [x] true true true
- [ ] true false true
- [ ] false true true
- [ ] true true false

> **Giải thích:** allMatch: all > 0 ✓. anyMatch: any < 0 → -2 ✓. noneMatch: none == 0 ✓. Short-circuit operations.

## Câu 16

[TYPE: SELECT_RESULT]

```java
String result = Stream.of("Hello", "World", "Java")
    .collect(Collectors.joining(" | ", "[", "]"));
System.out.println(result);
```

- [x] [Hello | World | Java]
- [ ] Hello | World | Java
- [ ] [Hello, World, Java]
- [ ] Hello|World|Java

> **Giải thích:** joining(delimiter, prefix, suffix): "[" + "Hello | World | Java" + "]". 3-arg joining.

## Câu 17

[TYPE: MULTIPLE_CHOICE]

Stream.generate() vs Stream.iterate()?

- [x] generate: Supplier, no relation between elements; iterate: seed + UnaryOperator, each depends on previous
- [ ] Giống nhau
- [ ] generate finite, iterate infinite
- [ ] Cả hai finite

> **Giải thích:** generate(supplier): independent elements. iterate(seed, f): f(seed), f(f(seed))... Both infinite unless limited. Java 9: iterate(seed, predicate, f) for bounded.

## Câu 18

[TYPE: SELECT_RESULT]

```java
List<Integer> result = Stream.iterate(1, n -> n * 2)
    .limit(6)
    .collect(Collectors.toList());
System.out.println(result);
```

- [x] [1, 2, 4, 8, 16, 32]
- [ ] [2, 4, 8, 16, 32, 64]
- [ ] [1, 2, 3, 4, 5, 6]
- [ ] Infinite loop

> **Giải thích:** iterate(1, n*2): 1, 2, 4, 8, 16, 32... limit(6): take 6 elements. Powers of 2.

## Câu 19

[TYPE: SELECT_RESULT]

```java
Map<Boolean, List<Integer>> partitioned = Stream.of(1, 2, 3, 4, 5, 6)
    .collect(Collectors.partitioningBy(n -> n % 2 == 0));
System.out.println(partitioned.get(true));
System.out.println(partitioned.get(false));
```

- [x] [2, 4, 6] và [1, 3, 5]
- [ ] [1, 3, 5] và [2, 4, 6]
- [ ] [2, 4, 6] và null
- [ ] Lỗi biên dịch

> **Giải thích:** partitioningBy: chia thành 2 groups. true: even [2,4,6]. false: odd [1,3,5]. Luôn có cả 2 keys.

## Câu 20

[TYPE: FILL_BLANK]

`stream.collect(Collectors.toUnmodifiableList())` trả về list `___`.

- [x] không thể thay đổi (immutable)
- [ ] có thể thay đổi
- [ ] sorted
- [ ] null

> **Giải thích:** toUnmodifiableList (Java 10): immutable list. add/remove → UnsupportedOperationException. toUnmodifiableSet(), toUnmodifiableMap() tương tự.

## Câu 21

[TYPE: SELECT_RESULT]

```java
OptionalDouble avg = IntStream.of(10, 20, 30, 40, 50).average();
System.out.println(avg.getAsDouble());
IntSummaryStatistics stats = IntStream.of(10, 20, 30, 40, 50).summaryStatistics();
System.out.println(stats.getMin() + " " + stats.getMax() + " " + stats.getSum());
```

- [x] 30.0 và 10 50 150
- [ ] 30 và 10 50 150
- [ ] 150.0 và 10 50 150
- [ ] Lỗi

> **Giải thích:** average(): (10+20+30+40+50)/5 = 30.0. summaryStatistics: min=10, max=50, sum=150, count=5. One-pass statistics.

## Câu 22

[TYPE: SELECT_RESULT]

```java
List<String> result = Stream.of("banana", "apple", "cherry")
    .sorted(Comparator.comparing(String::length).thenComparing(Comparator.naturalOrder()))
    .collect(Collectors.toList());
System.out.println(result);
```

- [x] [apple, banana, cherry]
- [ ] [banana, apple, cherry]
- [ ] [cherry, banana, apple]
- [ ] [apple, cherry, banana]

> **Giải thích:** Sort by length: apple(5), banana(6), cherry(6). Same length → natural order: banana < cherry. Result: [apple, banana, cherry].

## Câu 23

[TYPE: MULTIPLE_CHOICE]

Khi nào dùng parallel stream?

- [x] Large data sets, CPU-intensive operations, independent elements, no shared mutable state
- [ ] Luôn luôn
- [ ] Small collections
- [ ] I/O operations

> **Giải thích:** Parallel: useful khi data lớn, operations CPU-bound, elements independent. Overhead cho small data. Avoid: shared state, I/O (thread contention), ordered operations.

## Câu 24

[TYPE: SELECT_RESULT]

```java
long count = Stream.of("a", "bb", "ccc", "dd", "e")
    .filter(s -> s.length() > 1)
    .count();
System.out.println(count);
```

- [x] 3
- [ ] 2
- [ ] 5
- [ ] 1

> **Giải thích:** filter(length > 1): "bb"(2), "ccc"(3), "dd"(2) pass. "a"(1), "e"(1) fail. count = 3.

## Câu 25

[TYPE: SELECT_RESULT]

```java
Map<String, Integer> map = Stream.of("apple", "banana", "cherry")
    .collect(Collectors.toMap(
        Function.identity(),
        String::length
    ));
System.out.println(map);
```

- [x] {apple=5, banana=6, cherry=6}
- [ ] {5=apple, 6=cherry}
- [ ] Lỗi duplicate key
- [ ] {apple=5, banana=6}

> **Giải thích:** toMap(keyMapper, valueMapper): key=string, value=length. Tất cả keys unique → OK. Nếu duplicate key → IllegalStateException (trừ khi có merge function).

## Câu 26

[TYPE: TRUE_FALSE]

Mệnh đề: "Stream operations lazy: intermediate ops không thực thi cho đến khi terminal operation được gọi."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Lazy evaluation: filter().map().sorted() chỉ tạo pipeline. Chỉ khi collect/forEach/count chạy → pipeline thực thi. Optimization: short-circuit.

## Câu 27

[TYPE: SELECT_RESULT]

```java
String result = Stream.of("Hello", null, "World", null, "Java")
    .filter(Objects::nonNull)
    .collect(Collectors.joining(" "));
System.out.println(result);
```

- [x] Hello World Java
- [ ] Hello null World null Java
- [ ] NullPointerException
- [ ] Hello World

> **Giải thích:** filter(Objects::nonNull): remove nulls. Remaining: "Hello", "World", "Java". joining(" ") → "Hello World Java".

## Câu 28

[TYPE: SELECT_RESULT]

```java
List<String> words = List.of("hello", "world", "hello", "java", "world");
Map<String, Long> freq = words.stream()
    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
System.out.println(freq.get("hello") + " " + freq.get("java"));
```

- [x] 2 1
- [ ] 1 1
- [ ] 2 2
- [ ] 3 1

> **Giải thích:** groupingBy + counting: frequency map. hello=2, world=2, java=1.

## Câu 29

[TYPE: MULTIPLE_CHOICE]

Optional best practices?

- [x] Dùng cho return types, tránh Optional.get() trực tiếp, dùng orElse/orElseGet/orElseThrow
- [ ] Dùng cho mọi field
- [ ] Optional.get() luôn safe
- [ ] Dùng Optional cho method parameters

> **Giải thích:** Optional: return type only. Không dùng cho fields, parameters, collections. orElse(default), orElseGet(supplier), orElseThrow(), ifPresent(consumer).

## Câu 30

[TYPE: SELECT_RESULT]

```java
Optional<String> opt = Optional.of("Hello");
String result = opt.map(s -> s + " World")
                   .filter(s -> s.contains("World"))
                   .orElse("Empty");
System.out.println(result);
```

- [x] Hello World
- [ ] Hello
- [ ] Empty
- [ ] null

> **Giải thích:** map: "Hello" → "Hello World". filter: contains "World" ✓. orElse: has value → "Hello World".

## Câu 31

[TYPE: FILL_BLANK]

`Collectors.___()` tạo downstream collector tính giá trị trung bình.

- [x] averagingInt/averagingDouble
- [ ] average
- [ ] mean
- [ ] avg

> **Giải thích:** Collectors.averagingInt(mapper), averagingDouble, averagingLong. groupingBy(dept, averagingInt(Employee::salary)) → average salary by dept.

## Câu 32

[TYPE: SELECT_RESULT]

```java
List<Integer> result = Stream.of(5, 3, 8, 1, 9, 2)
    .sorted()
    .skip(2)
    .limit(3)
    .collect(Collectors.toList());
System.out.println(result);
```

- [x] [3, 5, 8]
- [ ] [1, 2, 3]
- [ ] [8, 9]
- [ ] [5, 8, 1]

> **Giải thích:** sorted: [1,2,3,5,8,9]. skip(2): [3,5,8,9]. limit(3): [3,5,8].

## Câu 33

[TYPE: SELECT_RESULT]

```java
record Person(String name, int age) {}
List<Person> people = List.of(
    new Person("An", 25), new Person("Bình", 30),
    new Person("Cường", 25), new Person("Dung", 35));
Map<Integer, List<String>> namesByAge = people.stream()
    .collect(Collectors.groupingBy(Person::age,
        Collectors.mapping(Person::name, Collectors.toList())));
System.out.println(namesByAge.get(25));
```

- [x] [An, Cường]
- [ ] [An]
- [ ] [25, 25]
- [ ] Lỗi biên dịch

> **Giải thích:** groupingBy(age, mapping(name, toList())): group by age, collect names. age=25 → [An, Cường]. Downstream collector chain.

## Câu 34

[TYPE: SELECT_RESULT]

```java
double result = DoubleStream.of(1.5, 2.5, 3.5, 4.5)
    .map(d -> d * 2)
    .sum();
System.out.println(result);
```

- [x] 24.0
- [ ] 12.0
- [ ] 48.0
- [ ] 6.0

> **Giải thích:** map(d*2): [3.0, 5.0, 7.0, 9.0]. sum: 3+5+7+9 = 24.0. DoubleStream: primitive specialization, no boxing.

## Câu 35

[TYPE: MULTIPLE_CHOICE]

Stream.of() vs Arrays.stream() vs Collection.stream()?

- [x] Stream.of: varargs; Arrays.stream: array (supports range); Collection.stream: from collection
- [ ] Tất cả giống nhau
- [ ] Stream.of chỉ cho primitives
- [ ] Collection.stream là lazy nhất

> **Giải thích:** Stream.of(1,2,3), Arrays.stream(arr, from, to), list.stream(). Arrays.stream: primitive arrays → IntStream/LongStream/DoubleStream.

## Câu 36

[TYPE: SELECT_RESULT]

```java
String longest = Stream.of("a", "bbb", "cc", "dddd", "ee")
    .max(Comparator.comparingInt(String::length))
    .orElse("");
System.out.println(longest);
```

- [x] dddd
- [ ] a
- [ ] ee
- [ ] bbb

> **Giải thích:** max by length: "dddd"(4) > "bbb"(3) > "cc"(2) = "ee"(2) > "a"(1). max = "dddd".

## Câu 37

[TYPE: SELECT_RESULT]

```java
int[] arr = {1, 2, 3, 4, 5};
int sum = Arrays.stream(arr).filter(n -> n > 2).sum();
System.out.println(sum);
```

- [x] 12
- [ ] 15
- [ ] 9
- [ ] 3

> **Giải thích:** filter(>2): 3, 4, 5. sum: 3+4+5 = 12. Arrays.stream(int[]) → IntStream.

## Câu 38

[TYPE: TRUE_FALSE]

Mệnh đề: "Stream.concat() nối 2 streams thành 1 stream."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Stream.concat(s1, s2): nối s1 và s2. Lazy. Kết quả: elements of s1 followed by elements of s2.

## Câu 39

[TYPE: SELECT_RESULT]

```java
List<String> result = Stream.of("a,b", "c,d", "e")
    .flatMap(s -> Arrays.stream(s.split(",")))
    .collect(Collectors.toList());
System.out.println(result);
```

- [x] [a, b, c, d, e]
- [ ] [a,b, c,d, e]
- [ ] [[a, b], [c, d], [e]]
- [ ] [a, b]

> **Giải thích:** flatMap: split mỗi string, flatten. "a,b"→[a,b], "c,d"→[c,d], "e"→[e]. Flatten: [a,b,c,d,e].

## Câu 40

[TYPE: SELECT_RESULT]

```java
Map<String, Optional<Person>> oldestByDept = List.of(
    new Person("An", 25), new Person("Bình", 35),
    new Person("Cường", 30), new Person("Dung", 40))
    .stream()
    .collect(Collectors.groupingBy(
        p -> p.age() > 30 ? "Senior" : "Junior",
        Collectors.maxBy(Comparator.comparingInt(Person::age))
    ));
System.out.println(oldestByDept.get("Senior").get().name());
```

record Person(String name, int age) {}

- [x] Dung
- [ ] Bình
- [ ] An
- [ ] Cường

> **Giải thích:** Senior: Bình(35), Dung(40). maxBy(age): Dung(40). Junior: An(25), Cường(30). maxBy: Cường(30). get("Senior") = Dung.

## Câu 41

[TYPE: FILL_BLANK]

`Collectors.___()` tính tổng giá trị qua một mapper function.

- [x] summingInt/summingDouble
- [ ] sum
- [ ] total
- [ ] adding

> **Giải thích:** summingInt(mapper): sum mapped values. groupingBy(dept, summingInt(salary)) → total salary by dept. summingDouble, summingLong tương tự.

## Câu 42

[TYPE: SELECT_RESULT]

```java
Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
stream.forEach(System.out::print);
stream.forEach(System.out::print); // line 2
```

- [ ] 1234512345
- [x] 12345 rồi IllegalStateException
- [ ] 12345
- [ ] Lỗi biên dịch

> **Giải thích:** Stream single-use. First forEach: consume stream → 12345. Second forEach: stream already closed → IllegalStateException.

## Câu 43

[TYPE: SELECT_RESULT]

```java
List<Integer> result = Stream.of(1, 2, 3, 4, 5)
    .takeWhile(n -> n < 4)
    .collect(Collectors.toList());
System.out.println(result);
```

- [x] [1, 2, 3]
- [ ] [1, 2, 3, 4, 5]
- [ ] [4, 5]
- [ ] []

> **Giải thích:** takeWhile (Java 9): take elements while predicate true. 1<4✓, 2<4✓, 3<4✓, 4<4✗ → stop. [1,2,3].

## Câu 44

[TYPE: SELECT_RESULT]

```java
List<Integer> result = Stream.of(1, 2, 3, 4, 5)
    .dropWhile(n -> n < 4)
    .collect(Collectors.toList());
System.out.println(result);
```

- [x] [4, 5]
- [ ] [1, 2, 3]
- [ ] [1, 2, 3, 4, 5]
- [ ] []

> **Giải thích:** dropWhile (Java 9): drop elements while predicate true. Drop 1,2,3 → keep 4,5. Complement of takeWhile.

## Câu 45

[TYPE: MULTIPLE_CHOICE]

Collector.teeing() (Java 12) dùng để:

- [x] Apply 2 collectors đồng thời, rồi merge kết quả
- [ ] Tạo 2 streams
- [ ] Split stream
- [ ] Parallel processing

> **Giải thích:** teeing(collector1, collector2, merger): process stream 2 ways simultaneously. Ví dụ: tính min và max cùng lúc.

## Câu 46

[TYPE: SELECT_RESULT]

```java
var result = Stream.of(1, 2, 3, 4, 5)
    .collect(Collectors.teeing(
        Collectors.summingInt(Integer::intValue),
        Collectors.counting(),
        (sum, count) -> "Sum=" + sum + " Count=" + count
    ));
System.out.println(result);
```

- [x] Sum=15 Count=5
- [ ] Sum=15
- [ ] Count=5
- [ ] Lỗi biên dịch

> **Giải thích:** teeing: collector1=sum(15), collector2=count(5). Merger: combine results. "Sum=15 Count=5".

## Câu 47

[TYPE: SELECT_RESULT]

```java
Optional<String> result = Stream.<String>empty()
    .reduce((a, b) -> a + b);
System.out.println(result.isPresent());

String result2 = Stream.<String>empty()
    .reduce("", (a, b) -> a + b);
System.out.println(result2.isEmpty());
```

- [x] false và true
- [ ] true và false
- [ ] false và false
- [ ] NullPointerException

> **Giải thích:** reduce without identity on empty stream → empty Optional (isPresent=false). reduce with identity="" on empty → identity ("") → isEmpty=true.

## Câu 48

[TYPE: FILL_BLANK]

`Stream.___()` tạo stream từ nullable value, trả về empty stream nếu null.

- [x] ofNullable
- [ ] of
- [ ] nullable
- [ ] fromNullable

> **Giải thích:** Stream.ofNullable(value) (Java 9): null → empty stream. non-null → stream of 1 element. Stream.of(null) → NullPointerException.

## Câu 49

[TYPE: SELECT_RESULT]

```java
record Employee(String name, String dept, int salary) {}
List<Employee> employees = List.of(
    new Employee("An", "IT", 1000),
    new Employee("Bình", "HR", 1200),
    new Employee("Cường", "IT", 1500),
    new Employee("Dung", "HR", 900));
Map<String, Double> avgSalary = employees.stream()
    .collect(Collectors.groupingBy(Employee::dept,
        Collectors.averagingInt(Employee::salary)));
System.out.println(avgSalary.get("IT"));
```

- [x] 1250.0
- [ ] 2500.0
- [ ] 1000.0
- [ ] 1500.0

> **Giải thích:** IT: An(1000) + Cường(1500) = 2500/2 = 1250.0. groupingBy + averagingInt = average salary by department.

## Câu 50

[TYPE: SELECT_RESULT]

```java
String result = IntStream.rangeClosed(1, 5)
    .mapToObj(i -> "Item" + i)
    .collect(Collectors.joining(", "));
System.out.println(result);
```

- [x] Item1, Item2, Item3, Item4, Item5
- [ ] 1, 2, 3, 4, 5
- [ ] Item1Item2Item3Item4Item5
- [ ] [Item1, Item2, Item3, Item4, Item5]

> **Giải thích:** IntStream → mapToObj → Stream<String>. joining(", ") → single string with separator.

## Câu 51

[TYPE: TRUE_FALSE]

Mệnh đề: "forEachOrdered() duy trì encounter order ngay cả với parallel streams."

- [x] Đúng
- [ ] Sai

> **Giải thích:** forEach: parallel → order không đảm bảo. forEachOrdered: duy trì order nhưng có thể giảm parallel performance. Dùng khi order quan trọng.

## Câu 52

[TYPE: SELECT_RESULT]

```java
Map<String, List<String>> grouped = Stream.of(
    "apple", "avocado", "banana", "blueberry", "cherry")
    .collect(Collectors.groupingBy(s -> s.substring(0, 1)));
System.out.println(grouped.get("a").size());
System.out.println(grouped.get("b").size());
```

- [x] 2 và 2
- [ ] 1 và 1
- [ ] 2 và 1
- [ ] 3 và 2

> **Giải thích:** Group by first letter. a: [apple, avocado]=2. b: [banana, blueberry]=2. c: [cherry]=1.

## Câu 53

[TYPE: SELECT_RESULT]

```java
List<Integer> result = Stream.of(1, 2, 2, 3, 3, 3, 4)
    .distinct()
    .collect(Collectors.toList());
System.out.println(result);
```

- [x] [1, 2, 3, 4]
- [ ] [1, 2, 3, 3, 4]
- [ ] [3]
- [ ] [1, 2, 2, 3, 3, 3, 4]

> **Giải thích:** distinct: remove duplicates (uses equals/hashCode). [1,2,3,4]. Maintains encounter order.

## Câu 54

[TYPE: SELECT_RESULT]

```java
Optional<Integer> min = Stream.of(5, 3, 8, 1, 9)
    .min(Comparator.naturalOrder());
Optional<Integer> max = Stream.of(5, 3, 8, 1, 9)
    .max(Comparator.naturalOrder());
System.out.println(min.get() + " " + max.get());
```

- [x] 1 9
- [ ] 9 1
- [ ] 5 5
- [ ] 1 1

> **Giải thích:** min: 1 (smallest). max: 9 (largest). naturalOrder: ascending. Both return Optional.

## Câu 55

[TYPE: MULTIPLE_CHOICE]

Stream.toList() (Java 16) vs Collectors.toList()?

- [x] toList(): unmodifiable list; Collectors.toList(): modifiable ArrayList
- [ ] Giống nhau
- [ ] Collectors.toList() unmodifiable
- [ ] toList() deprecated

> **Giải thích:** stream.toList() (Java 16): unmodifiable, null-tolerant. Collectors.toList(): modifiable ArrayList. Collectors.toUnmodifiableList(): unmodifiable, no null.

## Câu 56

[TYPE: SELECT_RESULT]

```java
int[] arr = {1, 2, 3};
IntStream stream = Arrays.stream(arr);
int sum = stream.map(n -> n * n).sum();
System.out.println(sum);
```

- [x] 14
- [ ] 6
- [ ] 9
- [ ] 36

> **Giải thích:** map(n*n): [1, 4, 9]. sum: 1+4+9 = 14. Sum of squares.

## Câu 57

[TYPE: SELECT_RESULT]

```java
Map<String, Long> wordCount = Stream.of(
    "the cat sat on the mat the cat".split(" "))
    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
System.out.println(wordCount.get("the") + " " + wordCount.get("cat"));
```

- [x] 3 2
- [ ] 2 1
- [ ] 3 1
- [ ] 2 2

> **Giải thích:** Word count: the=3, cat=2, sat=1, on=1, mat=1. groupingBy + counting = word frequency.

## Câu 58

[TYPE: SELECT_RESULT]

```java
List<String> result = Stream.of("abc", "de", "fghi")
    .flatMapToInt(String::chars)
    .mapToObj(c -> String.valueOf((char) c))
    .collect(Collectors.toList());
System.out.println(result.size());
```

- [x] 9
- [ ] 3
- [ ] 7
- [ ] Lỗi biên dịch

> **Giải thích:** flatMapToInt(chars): "abc"→3 ints, "de"→2 ints, "fghi"→4 ints. Total: 9 chars. Each char as String.

## Câu 59

[TYPE: FILL_BLANK]

`Collectors.collectingAndThen(collector, finisher)` apply `___` function sau khi collect xong.

- [x] finisher (transformation)
- [ ] mapper
- [ ] filter
- [ ] reducer

> **Giải thích:** collectingAndThen: wrap collector + transform result. Ví dụ: collectingAndThen(toList(), Collections::unmodifiableList) → collect rồi make unmodifiable.

## Câu 60

[TYPE: SELECT_RESULT]

```java
var result = List.of(1, 2, 3, 4, 5).stream()
    .collect(Collectors.collectingAndThen(
        Collectors.toList(),
        list -> {
            Collections.reverse(list);
            return list;
        }
    ));
System.out.println(result);
```

- [x] [5, 4, 3, 2, 1]
- [ ] [1, 2, 3, 4, 5]
- [ ] Lỗi biên dịch
- [ ] []

> **Giải thích:** collectingAndThen: collect to list, then reverse. Finisher transforms result. [1,2,3,4,5] → reversed → [5,4,3,2,1].

## Câu 61

[TYPE: SELECT_RESULT]

```java
Optional<String> result = Stream.of("banana", "apple", "cherry")
    .reduce((a, b) -> a.length() >= b.length() ? a : b);
System.out.println(result.get());
```

- [x] banana
- [ ] cherry
- [ ] apple
- [ ] Lỗi

> **Giải thích:** reduce: compare pairs. banana(6) vs apple(5) → banana. banana(6) vs cherry(6) → banana (>=). Result: "banana".

## Câu 62

[TYPE: TRUE_FALSE]

Mệnh đề: "Collectors.toMap() throw IllegalStateException khi có duplicate keys mà không có merge function."

- [x] Đúng
- [ ] Sai

> **Giải thích:** toMap(key, value): duplicate key → ISE. toMap(key, value, mergeFunction): handle duplicates. Ví dụ: (v1, v2) -> v1 (keep first).

## Câu 63

[TYPE: SELECT_RESULT]

```java
Map<String, Integer> result = Stream.of("a", "bb", "a", "ccc", "bb")
    .collect(Collectors.toMap(
        Function.identity(),
        String::length,
        (v1, v2) -> v1 + v2
    ));
System.out.println(result);
```

- [x] {a=2, bb=4, ccc=3}
- [ ] {a=1, bb=2, ccc=3}
- [ ] IllegalStateException
- [ ] {a=1, bb=2, ccc=3, a=1, bb=2}

> **Giải thích:** toMap with merge: duplicate "a" → 1+1=2. duplicate "bb" → 2+2=4. "ccc" → 3. Merge function sums values.

## Câu 64

[TYPE: SELECT_RESULT]

```java
LongStream.range(0, 5)
    .mapToObj(i -> "Item" + i)
    .forEach(System.out::println);
```

Dòng đầu tiên in:

- [x] Item0
- [ ] Item1
- [ ] Item5
- [ ] 0

> **Giải thích:** range(0, 5): [0,1,2,3,4] (exclusive end). mapToObj: 0→"Item0". First println: "Item0".

## Câu 65

[TYPE: SELECT_RESULT]

```java
record Product(String name, double price) {}
List<Product> products = List.of(
    new Product("A", 10), new Product("B", 20),
    new Product("C", 15), new Product("D", 5));
DoubleSummaryStatistics stats = products.stream()
    .mapToDouble(Product::price)
    .summaryStatistics();
System.out.println(stats.getAverage() + " " + stats.getCount());
```

- [x] 12.5 4
- [ ] 50.0 4
- [ ] 12.5 50
- [ ] 10.0 4

> **Giải thích:** prices: [10, 20, 15, 5]. average: 50/4 = 12.5. count: 4. summaryStatistics: min, max, sum, average, count.

## Câu 66

[TYPE: MULTIPLE_CHOICE]

Stream.mapMulti() (Java 16) thay thế gì?

- [x] Thay thế flatMap trong cases đơn giản, push-based thay vì tạo intermediate stream
- [ ] Thay thế map
- [ ] Thay thế filter
- [ ] Thay thế reduce

> **Giải thích:** mapMulti: Consumer-based, push 0..N elements. Lightweight hơn flatMap (no intermediate stream creation). Ví dụ: conditional emission.

## Câu 67

[TYPE: SELECT_RESULT]

```java
List<Integer> result = Stream.of(1, 2, 3, 4, 5)
    .<Integer>mapMulti((n, consumer) -> {
        if (n % 2 == 0) {
            consumer.accept(n);
            consumer.accept(n * 10);
        }
    })
    .collect(Collectors.toList());
System.out.println(result);
```

- [x] [2, 20, 4, 40]
- [ ] [1, 2, 3, 4, 5]
- [ ] [2, 4]
- [ ] [20, 40]

> **Giải thích:** mapMulti: even numbers → emit number and number×10. 2→[2,20], 4→[4,40]. Odd numbers → emit nothing.

## Câu 68

[TYPE: SELECT_RESULT]

```java
Stream<String> stream = Stream.of("a", "b", "c", "d", "e");
var result = stream.collect(Collectors.toMap(
    Function.identity(),
    s -> s.toUpperCase(),
    (v1, v2) -> v1,
    LinkedHashMap::new
));
System.out.println(result);
```

- [x] {a=A, b=B, c=C, d=D, e=E} (insertion order)
- [ ] Unordered map
- [ ] {A=a, B=b, C=c, D=d, E=e}
- [ ] Lỗi biên dịch

> **Giải thích:** toMap 4-arg: key, value, merge, mapFactory. LinkedHashMap preserves insertion order. Keys lowercase, values uppercase.

## Câu 69

[TYPE: SELECT_RESULT]

```java
String result = Stream.of("Hello World", "Java Stream", "API")
    .flatMap(s -> Arrays.stream(s.split(" ")))
    .filter(w -> w.length() <= 4)
    .sorted()
    .collect(Collectors.joining("-"));
System.out.println(result);
```

- [x] API-Java
- [ ] API-Java-Hello-World
- [ ] Java-API
- [ ] API

> **Giải thích:** Split: [Hello, World, Java, Stream, API]. Filter(≤4): Java(4), API(3). Sorted: API, Java. Joined: "API-Java".

## Câu 70

[TYPE: FILL_BLANK]

`IntStream.___()` chuyển IntStream thành Stream<Integer> (boxing).

- [x] boxed
- [ ] toObject
- [ ] wrap
- [ ] asStream

> **Giải thích:** boxed(): IntStream → Stream<Integer>. Cần khi dùng collect(Collectors.toList()). mapToObj: IntStream → Stream<R>.

## Câu 71

[TYPE: SELECT_RESULT]

```java
List<String> names = List.of("An", "Bình", "Cường");
Optional<String> reduced = names.stream()
    .reduce((a, b) -> a + ", " + b);
System.out.println(reduced.orElse(""));
```

- [x] An, Bình, Cường
- [ ] AnBìnhCường
- [ ] An
- [ ] Cường

> **Giải thích:** reduce(accumulator): "An" + ", Bình" → "An, Bình" + ", Cường" → "An, Bình, Cường". Similar to joining but manual.

## Câu 72

[TYPE: SELECT_RESULT]

```java
long count = Stream.iterate(1, n -> n <= 100, n -> n + 1)
    .filter(n -> n % 3 == 0 && n % 5 == 0)
    .count();
System.out.println(count);
```

- [x] 6
- [ ] 15
- [ ] 33
- [ ] 20

> **Giải thích:** iterate(1, ≤100, +1): 1 to 100. Filter divisible by 3 AND 5 (FizzBuzz): 15, 30, 45, 60, 75, 90 = 6 numbers.

## Câu 73

[TYPE: TRUE_FALSE]

Mệnh đề: "Stream.sorted() mà không có Comparator yêu cầu elements implement Comparable."

- [x] Đúng
- [ ] Sai

> **Giải thích:** sorted(): natural order → elements phải implement Comparable. sorted(comparator): custom order, không cần Comparable. ClassCastException nếu không Comparable.

## Câu 74

[TYPE: SELECT_RESULT]

```java
Map<Boolean, Long> result = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    .collect(Collectors.partitioningBy(n -> n % 2 == 0, Collectors.counting()));
System.out.println(result);
```

- [x] {false=5, true=5}
- [ ] {true=5}
- [ ] {false=5}
- [ ] {true=5, false=5}

> **Giải thích:** partitioningBy + counting: count per partition. Even(true): 2,4,6,8,10 = 5. Odd(false): 1,3,5,7,9 = 5.

## Câu 75

[TYPE: SELECT_RESULT]

```java
List<Integer> result = Stream.of(1, 2, 3)
    .flatMap(n -> Stream.of(n, n * 10))
    .collect(Collectors.toList());
System.out.println(result);
```

- [x] [1, 10, 2, 20, 3, 30]
- [ ] [1, 2, 3, 10, 20, 30]
- [ ] [10, 20, 30]
- [ ] [[1, 10], [2, 20], [3, 30]]

> **Giải thích:** flatMap: 1→[1,10], 2→[2,20], 3→[3,30]. Flatten: [1,10,2,20,3,30]. Each element maps to 2 elements.

## Câu 76

[TYPE: SELECT_RESULT]

```java
var map = Stream.of("apple", "banana", "avocado", "blueberry", "cherry")
    .collect(Collectors.groupingBy(
        s -> s.charAt(0),
        Collectors.joining(", ")
    ));
System.out.println(map.get('a'));
System.out.println(map.get('b'));
```

- [x] apple, avocado và banana, blueberry
- [ ] [apple, avocado] và [banana, blueberry]
- [ ] apple và banana
- [ ] Lỗi biên dịch

> **Giải thích:** groupingBy(first char, joining). 'a' group: "apple, avocado". 'b' group: "banana, blueberry". Downstream joining produces String.

## Câu 77

[TYPE: MULTIPLE_CHOICE]

Khi nào peek() hữu ích?

- [x] Debugging pipeline, logging intermediate results mà không thay đổi stream
- [ ] Transform elements
- [ ] Filter elements
- [ ] Thay thế map

> **Giải thích:** peek: side-effect operation. Debug: `stream.peek(System.out::println).filter(...)`. Không dùng cho business logic. May not execute nếu short-circuit.

## Câu 78

[TYPE: SELECT_RESULT]

```java
Stream<String> stream = Stream.of("one", "two", "three", "four", "five");
Map<Integer, Set<String>> result = stream.collect(
    Collectors.groupingBy(String::length, Collectors.toSet()));
System.out.println(result.get(3).size());
System.out.println(result.get(4).size());
```

- [x] 2 và 1
- [ ] 1 và 2
- [ ] 3 và 2
- [ ] 2 và 2

> **Giải thích:** Length 3: {one, two} = 2. Length 4: {four, five} = 2. Wait: "five" has 4 chars. So length 3: {one, two}=2. Length 4: {four, five}=2. Length 5: {three}=1. get(4).size()=2.

## Câu 79

[TYPE: SELECT_RESULT]

```java
int result = Stream.of("1", "2", "3", "4", "5")
    .mapToInt(Integer::parseInt)
    .reduce(1, (a, b) -> a * b);
System.out.println(result);
```

- [x] 120
- [ ] 15
- [ ] 5
- [ ] 1

> **Giải thích:** mapToInt: ["1"..."5"] → [1,2,3,4,5]. reduce(1, multiply): 1*1*2*3*4*5 = 120. Factorial of 5.

## Câu 80

[TYPE: FILL_BLANK]

`Collectors.___()` tạo collector đếm số elements.

- [x] counting
- [ ] count
- [ ] size
- [ ] total

> **Giải thích:** Collectors.counting(): downstream collector cho groupingBy. groupingBy(dept, counting()) → Map<String, Long> count per group.

## Câu 81

[TYPE: SELECT_RESULT]

```java
List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
int sumParallel = numbers.parallelStream()
    .filter(n -> n % 2 == 0)
    .mapToInt(Integer::intValue)
    .sum();
System.out.println(sumParallel);
```

- [x] 30
- [ ] 25
- [ ] 55
- [ ] Random (parallel issue)

> **Giải thích:** Even: 2+4+6+8+10 = 30. parallelStream: same result as sequential for stateless operations. filter+sum are safe for parallel.

## Câu 82

[TYPE: SELECT_RESULT]

```java
String result = Stream.of("Java", "is", "awesome")
    .collect(Collectors.joining(" ", "<<", ">>"));
System.out.println(result);
```

- [x] <<Java is awesome>>
- [ ] Java is awesome
- [ ] <<Java>> <<is>> <<awesome>>
- [ ] [Java, is, awesome]

> **Giải thích:** joining(delimiter, prefix, suffix): "<<" + "Java is awesome" + ">>". Prefix/suffix wrap entire result.

## Câu 83

[TYPE: TRUE_FALSE]

Mệnh đề: "findAny() có thể trả về bất kỳ element nào, đặc biệt hữu ích với parallel streams."

- [x] Đúng
- [ ] Sai

> **Giải thích:** findAny: non-deterministic, fastest match. Parallel: trả về first available từ any thread. findFirst: đảm bảo first encounter. Sequential: cả hai thường giống.

## Câu 84

[TYPE: SELECT_RESULT]

```java
Map<Integer, Optional<String>> longest = Stream.of("hi", "hello", "hey", "howdy", "hola")
    .collect(Collectors.groupingBy(
        String::length,
        Collectors.reducing((a, b) -> a.compareTo(b) > 0 ? a : b)
    ));
System.out.println(longest.get(5).get());
```

- [x] howdy
- [ ] hello
- [ ] hola
- [ ] hey

> **Giải thích:** Length 5: hello, howdy. reducing(max by natural order): howdy > hello. get(5) = Optional[howdy].

## Câu 85

[TYPE: SELECT_RESULT]

```java
int[] indices = IntStream.range(0, 10)
    .filter(i -> i % 3 == 0)
    .toArray();
System.out.println(Arrays.toString(indices));
```

- [x] [0, 3, 6, 9]
- [ ] [3, 6, 9]
- [ ] [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
- [ ] [0, 3, 6]

> **Giải thích:** range(0, 10): [0..9]. filter(i%3==0): 0, 3, 6, 9. toArray(): int[]. Note: 0 % 3 == 0.

## Câu 86

[TYPE: SELECT_RESULT]

```java
Map<String, Integer> scores = Map.of("An", 85, "Bình", 92, "Cường", 78);
Optional<Map.Entry<String, Integer>> top = scores.entrySet().stream()
    .max(Map.Entry.comparingByValue());
System.out.println(top.get().getKey());
```

- [x] Bình
- [ ] An
- [ ] Cường
- [ ] 92

> **Giải thích:** max by value: Bình(92) > An(85) > Cường(78). getKey() → "Bình". Entry stream operations.

## Câu 87

[TYPE: MULTIPLE_CHOICE]

Stream.generate(Math::random).limit(5) tạo gì?

- [x] 5 random doubles từ 0.0 đến 1.0
- [ ] Infinite stream
- [ ] 5 integers
- [ ] Lỗi

> **Giải thích:** generate(supplier): infinite stream. Math.random() → [0.0, 1.0). limit(5): take 5 elements. Each call to random → different value.

## Câu 88

[TYPE: SELECT_RESULT]

```java
var result = Stream.of("a", "b", "c")
    .map(s -> s + s)
    .peek(s -> System.out.print(s + " "))
    .count();
```

Java 9+ output:

- [x] 3 (không in gì vì count() optimized)
- [ ] aa bb cc 3
- [ ] 3 với aa bb cc
- [ ] Lỗi

> **Giải thích:** Java 9+: count() có thể skip intermediate ops nếu biết stream size. Stream.of → known size → skip map/peek. count = 3. Optimization caveat.

## Câu 89

[TYPE: SELECT_RESULT]

```java
List<String> result = Stream.of("banana", "apple", "cherry", "date")
    .sorted(Comparator.comparingInt(String::length).reversed())
    .collect(Collectors.toList());
System.out.println(result);
```

- [x] [banana, cherry, apple, date]
- [ ] [date, apple, banana, cherry]
- [ ] [cherry, banana, apple, date]
- [ ] [apple, banana, cherry, date]

> **Giải thích:** Sort by length reversed (descending): banana(6), cherry(6), apple(5), date(4). Same length → encounter order preserved.

## Câu 90

[TYPE: FILL_BLANK]

`Collectors.___()` tạo collector trả về min element theo Comparator.

- [x] minBy
- [ ] min
- [ ] minimum
- [ ] smallest

> **Giải thích:** minBy(comparator): Optional<T>. maxBy(comparator). Dùng làm downstream collector: groupingBy(dept, minBy(comparingInt(salary))).

## Câu 91

[TYPE: SELECT_RESULT]

```java
List<String> words = List.of("hello", "world");
List<String> result = words.stream()
    .flatMap(w -> Stream.of(w.split("")))
    .distinct()
    .sorted()
    .collect(Collectors.toList());
System.out.println(result);
```

- [x] [d, e, h, l, o, r, w]
- [ ] [h, e, l, l, o, w, o, r, l, d]
- [ ] [hello, world]
- [ ] [d, e, h, l, l, l, o, o, r, w]

> **Giải thích:** Split each word to chars. flatMap → all chars. distinct: remove duplicates. sorted: alphabetical. 7 unique chars.

## Câu 92

[TYPE: SELECT_RESULT]

```java
record Order(String customer, List<String> items) {}
List<Order> orders = List.of(
    new Order("An", List.of("Phone", "Case")),
    new Order("Bình", List.of("Laptop")),
    new Order("An", List.of("Charger")));
Map<String, List<String>> allItems = orders.stream()
    .collect(Collectors.groupingBy(Order::customer,
        Collectors.flatMapping(o -> o.items().stream(), Collectors.toList())));
System.out.println(allItems.get("An"));
```

- [x] [Phone, Case, Charger]
- [ ] [[Phone, Case], [Charger]]
- [ ] [Phone, Case]
- [ ] Lỗi biên dịch

> **Giải thích:** groupingBy(customer, flatMapping). An: [Phone, Case] + [Charger] → flat → [Phone, Case, Charger]. Java 9 flatMapping collector.

## Câu 93

[TYPE: SELECT_RESULT]

```java
var result = IntStream.rangeClosed(1, 10)
    .asDoubleStream()
    .map(d -> 1.0 / d)
    .sum();
System.out.printf("%.2f%n", result);
```

- [x] 2.93
- [ ] 10.00
- [ ] 1.00
- [ ] 5.50

> **Giải thích:** Harmonic series: 1/1 + 1/2 + 1/3 + ... + 1/10 ≈ 2.9289... ≈ 2.93. asDoubleStream: IntStream → DoubleStream.

## Câu 94

[TYPE: TRUE_FALSE]

Mệnh đề: "Collectors.reducing() là general-purpose reduction collector tương tự Stream.reduce()."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Collectors.reducing: dùng làm downstream collector. reducing(identity, mapper, op). Tương tự reduce nhưng dùng trong collect pipeline.

## Câu 95

[TYPE: SELECT_RESULT]

```java
var result = Stream.of(
    Map.entry("a", 1), Map.entry("b", 2), Map.entry("a", 3))
    .collect(Collectors.toMap(
        Map.Entry::getKey,
        Map.Entry::getValue,
        Integer::sum));
System.out.println(result);
```

- [x] {a=4, b=2}
- [ ] {a=1, b=2}
- [ ] {a=3, b=2}
- [ ] IllegalStateException

> **Giải thích:** toMap with merge(sum): key "a" → 1+3=4. key "b" → 2. Merge function handles duplicate keys.

## Câu 96

[TYPE: SELECT_RESULT]

```java
var result = Stream.of(1, 2, 3, 4, 5)
    .collect(Collectors.filtering(n -> n > 2,
        Collectors.mapping(n -> n * 2, Collectors.toList())));
System.out.println(result);
```

- [x] [6, 8, 10]
- [ ] [2, 4, 6, 8, 10]
- [ ] [3, 4, 5]
- [ ] [6, 8]

> **Giải thích:** filtering(>2) → 3, 4, 5. mapping(*2) → 6, 8, 10. Collector composition (Java 9). Different from stream.filter().map().

## Câu 97

[TYPE: SELECT_RESULT]

```java
Optional<String> result = Optional.of("Hello")
    .flatMap(s -> s.isEmpty() ? Optional.empty() : Optional.of(s.toUpperCase()))
    .filter(s -> s.startsWith("H"));
System.out.println(result.orElse("none"));
```

- [x] HELLO
- [ ] Hello
- [ ] none
- [ ] null

> **Giải thích:** flatMap: "Hello" not empty → Optional[HELLO]. filter: starts with "H" ✓. orElse: has value → "HELLO".

## Câu 98

[TYPE: SELECT_RESULT]

```java
Map<String, Map<Integer, List<String>>> nested = Stream.of(
    "An", "Bình", "An Khanh", "Ba", "Cường")
    .collect(Collectors.groupingBy(
        s -> s.substring(0, 1),
        Collectors.groupingBy(String::length)));
System.out.println(nested.get("A").get(2));
```

- [x] [An]
- [ ] [An, An Khanh]
- [ ] null
- [ ] [An Khanh]

> **Giải thích:** Nested grouping: first by first letter, then by length. "A" group: An(2), An Khanh(8). get("A").get(2) → [An].

## Câu 99

[TYPE: MULTIPLE_CHOICE]

Stream performance: sequential vs parallel?

- [x] Parallel: overhead of splitting/merging; beneficial for large data, CPU-intensive ops
- [ ] Parallel luôn nhanh hơn
- [ ] Sequential luôn nhanh hơn
- [ ] Không có sự khác biệt

> **Giải thích:** Parallel overhead: thread management, splitting, merging. Small data → sequential faster. Large data + CPU ops → parallel faster. Benchmark always.

## Câu 100

[TYPE: FILL_BLANK]

`Stream.___()` tạo stream chứa đúng 1 non-null element hoặc empty stream nếu null (Java 9+).

- [x] ofNullable
- [ ] of
- [ ] nullable
- [ ] single

> **Giải thích:** ofNullable(null) → empty stream. ofNullable("hello") → Stream.of("hello"). Useful trong flatMap khi giá trị có thể null.
