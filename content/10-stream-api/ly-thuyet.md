# Stream API trong Java

## Mục lục

1. [Giới thiệu về Stream API](#1-giới-thiệu-về-stream-api)
2. [Tạo Stream](#2-tạo-stream)
3. [Các loại Operations](#3-các-loại-operations)
4. [Collectors](#4-collectors)
5. [Parallel Streams](#5-parallel-streams)
6. [Primitive Streams](#6-primitive-streams)
7. [Optional Class](#7-optional-class)
8. [Best Practices](#8-best-practices)

---

## 1. Giới thiệu về Stream API

**Stream API** (Java 8+) cho phép xử lý dữ liệu theo kiểu **functional programming** — khai báo *những gì muốn làm* thay vì *làm như thế nào*.

### Đặc điểm chính của Stream

| Đặc điểm | Mô tả |
|-----------|--------|
| **Không lưu trữ dữ liệu** | Stream không phải collection, không chứa data |
| **Không modify nguồn** | Không thay đổi collection gốc |
| **Lazy evaluation** | Intermediate operations chỉ chạy khi có terminal operation |
| **Có thể infinite** | Stream có thể vô hạn (generate, iterate) |
| **Sử dụng 1 lần** | Không thể tái sử dụng sau khi consumed |
| **Pipeline** | Chuỗi operations tạo thành pipeline |

```java
import java.util.*;
import java.util.stream.*;

public class StreamIntroDemo {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve", "Alice");
        
        // Imperative approach (truyền thống)
        List<String> result1 = new ArrayList<>();
        for (String name : names) {
            if (name.length() > 3) {
                result1.add(name.toUpperCase());
            }
        }
        Collections.sort(result1);
        
        // Declarative approach (Stream API)
        List<String> result2 = names.stream()
            .filter(name -> name.length() > 3)  // lọc
            .map(String::toUpperCase)            // biến đổi
            .distinct()                          // loại trùng
            .sorted()                            // sắp xếp
            .collect(Collectors.toList());       // thu thập kết quả
        
        System.out.println(result2); // [ALICE, CHARLIE, DAVID]
    }
}
```

---

## 2. Tạo Stream

### Từ Collection

```java
List<String> list = Arrays.asList("a", "b", "c");
Stream<String> stream = list.stream();          // Sequential stream
Stream<String> parallel = list.parallelStream(); // Parallel stream

Set<Integer> set = new HashSet<>(Arrays.asList(1, 2, 3));
Stream<Integer> setStream = set.stream();
```

### Từ Array

```java
String[] arr = {"Java", "Python", "Go"};
Stream<String> arrStream = Arrays.stream(arr);
Stream<String> arrStream2 = Stream.of("Java", "Python", "Go");

// Partial array
Stream<String> partial = Arrays.stream(arr, 0, 2); // [Java, Python]

// Primitive arrays
int[] nums = {1, 2, 3, 4, 5};
IntStream intStream = Arrays.stream(nums);
```

### Từ các phương thức static

```java
// Stream.of
Stream<String> of = Stream.of("one", "two", "three");

// Stream.empty
Stream<String> empty = Stream.empty();

// Stream.generate (infinite)
Stream<Double> randoms = Stream.generate(Math::random); // vô hạn!
Stream<String> constants = Stream.generate(() -> "Hello");

// Stream.iterate (infinite)
Stream<Integer> naturals = Stream.iterate(0, n -> n + 1); // 0, 1, 2, 3, ...

// Stream.iterate với predicate (Java 9+)
Stream<Integer> limited = Stream.iterate(0, n -> n < 100, n -> n + 10);
// 0, 10, 20, 30, ..., 90

// Stream.concat
Stream<String> s1 = Stream.of("a", "b");
Stream<String> s2 = Stream.of("c", "d");
Stream<String> concat = Stream.concat(s1, s2); // a, b, c, d

// Stream.builder
Stream<String> built = Stream.<String>builder()
    .add("one")
    .add("two")
    .add("three")
    .build();

// Từ String
IntStream chars = "Hello".chars(); // IntStream of char values

// Từ File (Java 8+)
// Stream<String> lines = Files.lines(Paths.get("file.txt"));

// Từ Pattern
// Stream<String> words = Pattern.compile("\\s+").splitAsStream("Hello World Java");
```

---

## 3. Các loại Operations

### 3.1. Intermediate Operations (Lazy)

Intermediate operations trả về Stream mới và chỉ thực thi khi có terminal operation.

#### filter()

Lọc phần tử theo điều kiện (Predicate).

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Lọc số chẵn
List<Integer> evens = numbers.stream()
    .filter(n -> n % 2 == 0)
    .collect(Collectors.toList());
System.out.println(evens); // [2, 4, 6, 8, 10]

// Lọc nhiều điều kiện
List<Integer> result = numbers.stream()
    .filter(n -> n > 3)
    .filter(n -> n < 8)
    .collect(Collectors.toList());
System.out.println(result); // [4, 5, 6, 7]
```

#### map()

Biến đổi mỗi phần tử sang dạng khác.

```java
List<String> names = Arrays.asList("alice", "bob", "charlie");

// String -> uppercase
List<String> upper = names.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());
System.out.println(upper); // [ALICE, BOB, CHARLIE]

// String -> length (Integer)
List<Integer> lengths = names.stream()
    .map(String::length)
    .collect(Collectors.toList());
System.out.println(lengths); // [5, 3, 7]

// Object transformation
List<Person> people = getPersonList();
List<String> emails = people.stream()
    .map(Person::getEmail)
    .collect(Collectors.toList());
```

#### flatMap()

"Phẳng hóa" - chuyển mỗi phần tử thành Stream rồi nối lại thành một Stream duy nhất.

```java
// Flatten list of lists
List<List<Integer>> nested = Arrays.asList(
    Arrays.asList(1, 2, 3),
    Arrays.asList(4, 5, 6),
    Arrays.asList(7, 8, 9)
);

List<Integer> flat = nested.stream()
    .flatMap(Collection::stream)
    .collect(Collectors.toList());
System.out.println(flat); // [1, 2, 3, 4, 5, 6, 7, 8, 9]

// Flatten String words
List<String> sentences = Arrays.asList("Hello World", "Java Stream");
List<String> words = sentences.stream()
    .flatMap(s -> Arrays.stream(s.split(" ")))
    .collect(Collectors.toList());
System.out.println(words); // [Hello, World, Java, Stream]

// flatMap vs map
// map:     Stream<String[]>  -> Mỗi câu thành array
// flatMap: Stream<String>    -> Phẳng thành từng từ
```

#### distinct()

Loại bỏ phần tử trùng lặp (dùng `equals()`).

```java
List<Integer> nums = Arrays.asList(1, 2, 2, 3, 3, 3, 4);
List<Integer> unique = nums.stream()
    .distinct()
    .collect(Collectors.toList());
System.out.println(unique); // [1, 2, 3, 4]
```

#### sorted()

Sắp xếp phần tử.

```java
List<String> names = Arrays.asList("Charlie", "Alice", "Bob");

// Natural order
List<String> sorted = names.stream()
    .sorted()
    .collect(Collectors.toList());
System.out.println(sorted); // [Alice, Bob, Charlie]

// Custom comparator
List<String> byLength = names.stream()
    .sorted(Comparator.comparingInt(String::length))
    .collect(Collectors.toList());
System.out.println(byLength); // [Bob, Alice, Charlie]

// Reversed
List<String> reversed = names.stream()
    .sorted(Comparator.reverseOrder())
    .collect(Collectors.toList());
```

#### limit() và skip()

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Lấy 5 phần tử đầu
List<Integer> first5 = numbers.stream()
    .limit(5)
    .collect(Collectors.toList());
System.out.println(first5); // [1, 2, 3, 4, 5]

// Bỏ qua 3 phần tử đầu
List<Integer> skip3 = numbers.stream()
    .skip(3)
    .collect(Collectors.toList());
System.out.println(skip3); // [4, 5, 6, 7, 8, 9, 10]

// Pagination: page 2, size 3
int page = 2, size = 3;
List<Integer> page2 = numbers.stream()
    .skip((long)(page - 1) * size)
    .limit(size)
    .collect(Collectors.toList());
System.out.println(page2); // [4, 5, 6]
```

#### peek()

Thực hiện action trên mỗi phần tử mà không thay đổi stream (debug).

```java
List<String> result = Stream.of("one", "two", "three", "four")
    .filter(s -> s.length() > 3)
    .peek(s -> System.out.println("Filtered: " + s))  // debug
    .map(String::toUpperCase)
    .peek(s -> System.out.println("Mapped: " + s))    // debug
    .collect(Collectors.toList());
// Filtered: three
// Mapped: THREE
// Filtered: four
// Mapped: FOUR
```

### 3.2. Terminal Operations (Eager)

Terminal operations kích hoạt xử lý pipeline và trả về kết quả (không phải Stream).

#### forEach()

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

// forEach - không đảm bảo thứ tự trong parallel stream
names.stream().forEach(System.out::println);

// forEachOrdered - đảm bảo thứ tự
names.parallelStream().forEachOrdered(System.out::println);
```

#### collect()

Thu thập kết quả vào collection hoặc giá trị đơn.

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Alice");

// To List
List<String> list = names.stream().collect(Collectors.toList());

// To Set (loại trùng)
Set<String> set = names.stream().collect(Collectors.toSet());

// To specific collection
TreeSet<String> treeSet = names.stream()
    .collect(Collectors.toCollection(TreeSet::new));

// Joining
String joined = names.stream()
    .collect(Collectors.joining(", ", "[", "]"));
System.out.println(joined); // [Alice, Bob, Charlie, Alice]
```

#### reduce()

Kết hợp tất cả phần tử thành một giá trị duy nhất.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// Tổng
int sum = numbers.stream()
    .reduce(0, Integer::sum);
System.out.println("Sum: " + sum); // 15

// Tích
int product = numbers.stream()
    .reduce(1, (a, b) -> a * b);
System.out.println("Product: " + product); // 120

// Max (trả về Optional)
Optional<Integer> max = numbers.stream()
    .reduce(Integer::max);
max.ifPresent(m -> System.out.println("Max: " + m)); // 5

// Concatenate strings
String concat = Stream.of("Hello", " ", "World")
    .reduce("", String::concat);
System.out.println(concat); // Hello World
```

#### count(), min(), max()

```java
List<Integer> numbers = Arrays.asList(5, 2, 8, 1, 9, 3);

long count = numbers.stream().filter(n -> n > 3).count();
System.out.println("Count > 3: " + count); // 3

Optional<Integer> min = numbers.stream().min(Integer::compareTo);
Optional<Integer> max = numbers.stream().max(Integer::compareTo);
System.out.println("Min: " + min.orElse(0)); // 1
System.out.println("Max: " + max.orElse(0)); // 9
```

#### anyMatch(), allMatch(), noneMatch()

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

boolean anyEven = numbers.stream().anyMatch(n -> n % 2 == 0);
System.out.println("Any even? " + anyEven); // true

boolean allPositive = numbers.stream().allMatch(n -> n > 0);
System.out.println("All positive? " + allPositive); // true

boolean noneNegative = numbers.stream().noneMatch(n -> n < 0);
System.out.println("None negative? " + noneNegative); // true
```

#### findFirst() và findAny()

```java
List<String> names = Arrays.asList("Alice", "Bob", "Anna", "Charlie");

// findFirst - phần tử đầu tiên thỏa mãn
Optional<String> first = names.stream()
    .filter(n -> n.startsWith("A"))
    .findFirst();
System.out.println(first.orElse("Not found")); // Alice

// findAny - bất kỳ phần tử nào (hữu ích với parallel)
Optional<String> any = names.parallelStream()
    .filter(n -> n.startsWith("A"))
    .findAny();
System.out.println(any.orElse("Not found")); // Alice hoặc Anna
```

#### toArray()

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

// Sang Object[]
Object[] objArr = names.stream().toArray();

// Sang String[]
String[] strArr = names.stream().toArray(String[]::new);
System.out.println(Arrays.toString(strArr)); // [Alice, Bob, Charlie]

// Sang Integer[]
Integer[] numArr = Stream.of(1, 2, 3, 4, 5).toArray(Integer[]::new);
```

---

## 4. Collectors

### 4.1. Collectors.toList()

```java
List<String> list = stream.collect(Collectors.toList());

// Java 16+: toList() trực tiếp (unmodifiable)
List<String> list2 = stream.toList();
```

### 4.2. Collectors.toSet()

```java
Set<String> set = names.stream()
    .map(String::toLowerCase)
    .collect(Collectors.toSet());
```

### 4.3. Collectors.toMap()

```java
List<Person> people = Arrays.asList(
    new Person("Alice", 25),
    new Person("Bob", 30),
    new Person("Charlie", 28)
);

// Đơn giản: name -> age
Map<String, Integer> nameToAge = people.stream()
    .collect(Collectors.toMap(Person::getName, Person::getAge));

// Xử lý key trùng (merge function)
Map<Integer, String> ageToName = people.stream()
    .collect(Collectors.toMap(
        Person::getAge,
        Person::getName,
        (existing, replacement) -> existing + ", " + replacement // merge
    ));

// Chỉ định Map implementation
TreeMap<String, Integer> sortedMap = people.stream()
    .collect(Collectors.toMap(
        Person::getName,
        Person::getAge,
        (a, b) -> a,
        TreeMap::new
    ));
```

### 4.4. Collectors.joining()

```java
List<String> words = Arrays.asList("Java", "is", "awesome");

String joined1 = words.stream().collect(Collectors.joining());
System.out.println(joined1); // Javaisawesome

String joined2 = words.stream().collect(Collectors.joining(" "));
System.out.println(joined2); // Java is awesome

String joined3 = words.stream().collect(Collectors.joining(", ", "[", "]"));
System.out.println(joined3); // [Java, is, awesome]
```

### 4.5. Collectors.groupingBy()

```java
List<Person> people = Arrays.asList(
    new Person("Alice", 25, "Engineering"),
    new Person("Bob", 30, "Engineering"),
    new Person("Charlie", 28, "Marketing"),
    new Person("David", 35, "Marketing"),
    new Person("Eve", 22, "Sales")
);

// Group by department
Map<String, List<Person>> byDept = people.stream()
    .collect(Collectors.groupingBy(Person::getDepartment));
// {Engineering=[Alice, Bob], Marketing=[Charlie, David], Sales=[Eve]}

// Group by + count
Map<String, Long> deptCount = people.stream()
    .collect(Collectors.groupingBy(Person::getDepartment, Collectors.counting()));
// {Engineering=2, Marketing=2, Sales=1}

// Group by + average age
Map<String, Double> avgAge = people.stream()
    .collect(Collectors.groupingBy(
        Person::getDepartment,
        Collectors.averagingInt(Person::getAge)
    ));

// Multi-level grouping
Map<String, Map<Integer, List<Person>>> nested = people.stream()
    .collect(Collectors.groupingBy(
        Person::getDepartment,
        Collectors.groupingBy(p -> p.getAge() >= 30 ? 30 : 20)
    ));
```

### 4.6. Collectors.partitioningBy()

Chia thành 2 nhóm: true/false.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

Map<Boolean, List<Integer>> partition = numbers.stream()
    .collect(Collectors.partitioningBy(n -> n % 2 == 0));
System.out.println("Even: " + partition.get(true));  // [2, 4, 6, 8, 10]
System.out.println("Odd: " + partition.get(false));  // [1, 3, 5, 7, 9]

// Partition with downstream collector
Map<Boolean, Long> countPartition = numbers.stream()
    .collect(Collectors.partitioningBy(n -> n > 5, Collectors.counting()));
System.out.println(countPartition); // {false=5, true=5}
```

### 4.7. Collectors.summarizingInt/Long/Double

```java
List<Person> people = getPersonList();

IntSummaryStatistics stats = people.stream()
    .collect(Collectors.summarizingInt(Person::getAge));

System.out.println("Count: " + stats.getCount());
System.out.println("Sum: " + stats.getSum());
System.out.println("Min: " + stats.getMin());
System.out.println("Max: " + stats.getMax());
System.out.println("Average: " + stats.getAverage());
```

---

## 5. Parallel Streams

### 5.1. Khi nào sử dụng parallel stream?

```java
import java.util.stream.*;

public class ParallelStreamDemo {
    public static void main(String[] args) {
        List<Integer> numbers = IntStream.rangeClosed(1, 1_000_000)
            .boxed().collect(Collectors.toList());
        
        // Sequential
        long start = System.currentTimeMillis();
        long sum1 = numbers.stream()
            .mapToLong(n -> (long) n * n)
            .sum();
        long seqTime = System.currentTimeMillis() - start;
        
        // Parallel
        start = System.currentTimeMillis();
        long sum2 = numbers.parallelStream()
            .mapToLong(n -> (long) n * n)
            .sum();
        long parTime = System.currentTimeMillis() - start;
        
        System.out.println("Sequential: " + seqTime + "ms");
        System.out.println("Parallel: " + parTime + "ms");
    }
}
```

**Nên dùng parallel stream khi:**
- Dữ liệu lớn (>10,000 phần tử)
- Operations CPU-intensive (tính toán nặng)
- Nguồn dữ liệu splitable tốt (ArrayList, arrays)
- Không có shared mutable state

**KHÔNG nên dùng khi:**
- Dữ liệu nhỏ (overhead > benefit)
- Operations I/O-bound
- Cần đảm bảo thứ tự
- Có side effects hoặc shared state
- Nguồn dữ liệu khó split (LinkedList)

### 5.2. Lưu ý khi sử dụng parallel stream

```java
// ❌ NGUY HIỂM - shared mutable state
List<Integer> result = new ArrayList<>(); // NOT thread-safe!
numbers.parallelStream()
    .filter(n -> n > 50)
    .forEach(result::add); // Race condition!

// ✅ AN TOÀN - collect
List<Integer> result = numbers.parallelStream()
    .filter(n -> n > 50)
    .collect(Collectors.toList());

// ❌ Thứ tự không đảm bảo với forEach
numbers.parallelStream().forEach(System.out::println); // random order

// ✅ Đảm bảo thứ tự
numbers.parallelStream().forEachOrdered(System.out::println);

// Chuyển đổi sequential <-> parallel
Stream<Integer> seq = numbers.stream();
Stream<Integer> par = seq.parallel();     // chuyển sang parallel
Stream<Integer> seq2 = par.sequential();  // chuyển lại sequential
```

---

## 6. Primitive Streams

### 6.1. IntStream

```java
import java.util.stream.IntStream;

public class IntStreamDemo {
    public static void main(String[] args) {
        // Tạo IntStream
        IntStream range = IntStream.range(1, 10);          // 1..9
        IntStream rangeClosed = IntStream.rangeClosed(1, 10); // 1..10
        IntStream of = IntStream.of(1, 3, 5, 7, 9);
        
        // Từ array
        int[] arr = {1, 2, 3, 4, 5};
        IntStream arrStream = Arrays.stream(arr);
        
        // Operations
        int sum = IntStream.rangeClosed(1, 100).sum();
        System.out.println("Sum 1-100: " + sum); // 5050
        
        OptionalInt max = IntStream.of(3, 1, 4, 1, 5).max();
        OptionalDouble avg = IntStream.of(1, 2, 3, 4, 5).average();
        
        System.out.println("Max: " + max.orElse(0));        // 5
        System.out.println("Average: " + avg.orElse(0));    // 3.0
        
        // Convert to object stream
        Stream<Integer> boxed = IntStream.of(1, 2, 3).boxed();
        List<Integer> list = IntStream.of(1, 2, 3).boxed().collect(Collectors.toList());
        
        // mapToObj
        List<String> strings = IntStream.rangeClosed(1, 5)
            .mapToObj(i -> "Item " + i)
            .collect(Collectors.toList());
        System.out.println(strings); // [Item 1, Item 2, Item 3, Item 4, Item 5]
    }
}
```

### 6.2. LongStream

```java
import java.util.stream.LongStream;

public class LongStreamDemo {
    public static void main(String[] args) {
        // Factorial
        long factorial = LongStream.rangeClosed(1, 20)
            .reduce(1, (a, b) -> a * b);
        System.out.println("20! = " + factorial);
        
        // Sum of large range
        long sum = LongStream.rangeClosed(1, 1_000_000_000L).sum();
        System.out.println("Sum: " + sum);
    }
}
```

### 6.3. DoubleStream

```java
import java.util.stream.DoubleStream;

public class DoubleStreamDemo {
    public static void main(String[] args) {
        // Random doubles
        DoubleStream randoms = DoubleStream.generate(Math::random).limit(5);
        randoms.forEach(d -> System.out.printf("%.4f%n", d));
        
        // Statistics
        DoubleSummaryStatistics stats = DoubleStream.of(1.5, 2.3, 3.7, 4.1, 5.9)
            .summaryStatistics();
        System.out.println("Count: " + stats.getCount());
        System.out.println("Sum: " + stats.getSum());
        System.out.println("Average: " + stats.getAverage());
        System.out.println("Min: " + stats.getMin());
        System.out.println("Max: " + stats.getMax());
    }
}
```

---

## 7. Optional Class

**Optional** (Java 8+) là container có thể chứa hoặc không chứa giá trị non-null. Giúp tránh `NullPointerException`.

### 7.1. Tạo Optional

```java
import java.util.Optional;

public class OptionalCreateDemo {
    public static void main(String[] args) {
        // Tạo Optional có giá trị
        Optional<String> opt1 = Optional.of("Hello");
        
        // Tạo Optional rỗng
        Optional<String> opt2 = Optional.empty();
        
        // Tạo Optional từ nullable value
        String value = null;
        Optional<String> opt3 = Optional.ofNullable(value);    // empty
        Optional<String> opt4 = Optional.ofNullable("World");  // has value
        
        // ⚠️ Optional.of(null) sẽ throw NullPointerException!
        // Optional<String> bad = Optional.of(null); // NPE!
    }
}
```

### 7.2. Các phương thức của Optional

```java
import java.util.Optional;

public class OptionalMethodsDemo {
    public static void main(String[] args) {
        Optional<String> opt = Optional.of("Java");
        Optional<String> empty = Optional.empty();
        
        // isPresent / isEmpty (Java 11+)
        System.out.println(opt.isPresent());   // true
        System.out.println(empty.isEmpty());   // true (Java 11+)
        
        // get() - lấy giá trị (throws NoSuchElementException nếu empty)
        String val = opt.get(); // "Java"
        // String bad = empty.get(); // NoSuchElementException!
        
        // ifPresent - thực hiện action nếu có giá trị
        opt.ifPresent(v -> System.out.println("Value: " + v)); // Value: Java
        empty.ifPresent(v -> System.out.println("Won't print"));
        
        // ifPresentOrElse (Java 9+)
        opt.ifPresentOrElse(
            v -> System.out.println("Got: " + v),
            () -> System.out.println("Empty!")
        );
        
        // orElse - giá trị mặc định
        String result1 = opt.orElse("default");    // "Java"
        String result2 = empty.orElse("default");  // "default"
        
        // orElseGet - lazy default (chỉ tính khi cần)
        String result3 = empty.orElseGet(() -> computeDefault());
        
        // orElseThrow - throw exception nếu empty
        String result4 = opt.orElseThrow();  // "Java"
        // empty.orElseThrow(); // NoSuchElementException!
        String result5 = opt.orElseThrow(() -> new RuntimeException("Not found"));
        
        // or (Java 9+) - trả về Optional khác nếu empty
        Optional<String> result6 = empty.or(() -> Optional.of("fallback"));
        System.out.println(result6.get()); // "fallback"
        
        // map - transform giá trị
        Optional<Integer> length = opt.map(String::length);
        System.out.println(length.get()); // 4
        
        // flatMap - khi function trả về Optional
        Optional<Optional<String>> nested = opt.map(v -> Optional.of(v.toUpperCase()));
        Optional<String> flat = opt.flatMap(v -> Optional.of(v.toUpperCase()));
        System.out.println(flat.get()); // "JAVA"
        
        // filter - lọc giá trị
        Optional<String> filtered = opt.filter(v -> v.length() > 3);
        System.out.println(filtered.isPresent()); // true
        
        Optional<String> filteredOut = opt.filter(v -> v.length() > 10);
        System.out.println(filteredOut.isPresent()); // false
        
        // stream (Java 9+)
        opt.stream().forEach(System.out::println); // Java
    }
    
    private static String computeDefault() {
        System.out.println("Computing default...");
        return "computed";
    }
}
```

### 7.3. Best Practices với Optional

```java
// ✅ Dùng Optional làm return type
public Optional<User> findUserById(int id) {
    User user = database.find(id);
    return Optional.ofNullable(user);
}

// ✅ Chain operations
String city = findUserById(1)
    .flatMap(User::getAddress)
    .map(Address::getCity)
    .orElse("Unknown");

// ❌ KHÔNG dùng Optional làm parameter
// public void process(Optional<String> name) { ... }
// ✅ Dùng overloading hoặc null check thay thế
public void process(String name) {
    Objects.requireNonNull(name);
}

// ❌ KHÔNG dùng Optional cho field
// private Optional<String> name; 
// ✅ Dùng nullable field
private String name; // null = không có giá trị

// ❌ KHÔNG dùng Optional.get() mà không check
// opt.get(); // Nguy hiểm!
// ✅ Dùng orElse, orElseThrow, ifPresent
opt.orElse("default");
opt.orElseThrow(() -> new NotFoundException("Not found"));

// ❌ KHÔNG dùng isPresent + get
if (opt.isPresent()) {
    String val = opt.get();
    process(val);
}
// ✅ Dùng ifPresent hoặc map
opt.ifPresent(this::process);
opt.map(this::transform).orElse(defaultValue);
```

---

## 8. Best Practices

### 1. Ưu tiên method references khi có thể

```java
// ✅ Method reference - ngắn gọn, rõ ràng
names.stream().map(String::toUpperCase).forEach(System.out::println);

// ❌ Lambda dài dòng khi có thể dùng method ref
names.stream().map(s -> s.toUpperCase()).forEach(s -> System.out.println(s));
```

### 2. Tránh side effects trong stream operations

```java
// ❌ Side effect trong map/filter
List<String> results = new ArrayList<>();
names.stream()
    .filter(n -> {
        System.out.println("Checking: " + n); // side effect!
        return n.length() > 3;
    })
    .forEach(results::add); // mutable state!

// ✅ Pure functions
List<String> results = names.stream()
    .filter(n -> n.length() > 3)
    .collect(Collectors.toList());
```

### 3. Sử dụng primitive streams cho performance

```java
// ❌ Autoboxing overhead
int sum = numbers.stream()
    .map(n -> n * 2)           // Integer -> Integer (autoboxing)
    .reduce(0, Integer::sum);

// ✅ Primitive stream
int sum = numbers.stream()
    .mapToInt(Integer::intValue)  // IntStream - no boxing
    .map(n -> n * 2)
    .sum();
```

### 4. Tránh stream lồng nhau quá nhiều

```java
// ❌ Khó đọc
result = items.stream()
    .flatMap(item -> item.getSubItems().stream()
        .filter(sub -> sub.getType().equals("A"))
        .map(sub -> sub.getValue()))
    .collect(Collectors.toList());

// ✅ Tách thành method riêng
result = items.stream()
    .flatMap(this::extractTypeAValues)
    .collect(Collectors.toList());

private Stream<String> extractTypeAValues(Item item) {
    return item.getSubItems().stream()
        .filter(sub -> sub.getType().equals("A"))
        .map(SubItem::getValue);
}
```

### 5. Sử dụng collect thay vì reduce cho mutable reduction

```java
// ❌ reduce với mutable object
StringBuilder sb = names.stream()
    .reduce(new StringBuilder(), 
            (builder, name) -> builder.append(name), 
            StringBuilder::append); // Không thread-safe!

// ✅ collect
String result = names.stream()
    .collect(Collectors.joining(", "));
```

### 6. Stream chỉ dùng một lần

```java
// ❌ Tái sử dụng stream
Stream<String> stream = names.stream().filter(n -> n.length() > 3);
stream.forEach(System.out::println);
stream.count(); // IllegalStateException: stream has already been operated upon

// ✅ Tạo stream mới mỗi lần
Supplier<Stream<String>> streamSupplier = () -> names.stream().filter(n -> n.length() > 3);
streamSupplier.get().forEach(System.out::println);
long count = streamSupplier.get().count();
```

### 7. Đóng stream khi đọc từ I/O resources

```java
// ✅ Try-with-resources cho I/O streams
try (Stream<String> lines = Files.lines(Paths.get("file.txt"))) {
    lines.filter(line -> line.contains("error"))
         .forEach(System.out::println);
}
// Stream tự động close sau try block
```

---

> **Tóm tắt:** Stream API là công cụ mạnh mẽ cho xử lý dữ liệu functional trong Java. Hiểu rõ sự khác biệt giữa intermediate/terminal operations, sử dụng Collectors phù hợp, và tuân thủ best practices giúp viết code ngắn gọn, hiệu quả và dễ đọc.
