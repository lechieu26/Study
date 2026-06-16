# Quiz - Generics

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Generics trong Java dùng để:

- [x] Type safety tại compile-time, tránh casting, tái sử dụng code với nhiều types
- [ ] Chỉ cho Collections
- [ ] Tăng performance
- [ ] Thay thế inheritance

> **Giải thích:** Generics: parameterized types. Compile-time type checking. Eliminate casts. Code reuse. Ví dụ: List<String> thay vì List + (String) cast.

## Câu 2

[TYPE: SELECT_RESULT]

```java
class Box<T> {
    private T value;
    Box(T value) { this.value = value; }
    T get() { return value; }
}
Box<String> box = new Box<>("Hello");
System.out.println(box.get().toUpperCase());
```

- [x] HELLO
- [ ] Hello
- [ ] Lỗi biên dịch
- [ ] ClassCastException

> **Giải thích:** Box<String>: T = String. get() trả về String. toUpperCase() gọi trực tiếp, không cần cast. Type safety.

## Câu 3

[TYPE: FILL_BLANK]

`<T extends Number>` nghĩa là T phải là `___` hoặc subclass của Number.

- [x] Number
- [ ] Object
- [ ] Integer
- [ ] Comparable

> **Giải thích:** Upper bounded: T extends Number → T phải là Number, Integer, Double, Long, etc. Compile-time check. Cho phép gọi Number methods trên T.

## Câu 4

[TYPE: SELECT_RESULT]

```java
class Pair<A, B> {
    A first;
    B second;
    Pair(A a, B b) { first = a; second = b; }
}
Pair<String, Integer> pair = new Pair<>("Age", 25);
System.out.println(pair.first + ": " + pair.second);
```

- [x] Age: 25
- [ ] Lỗi biên dịch
- [ ] null: null
- [ ] Age: null

> **Giải thích:** Multiple type parameters: A=String, B=Integer. Diamond inference. first="Age", second=25.

## Câu 5

[TYPE: TRUE_FALSE]

Mệnh đề: "Type erasure xóa generic type information tại runtime, thay T bằng Object (hoặc bound)."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Erasure: List<String> → List (raw). T → Object (unbounded), T extends Number → Number. Runtime: không biết generic type. Backward compatibility.

## Câu 6

[TYPE: SELECT_RESULT]

```java
List<String> strings = new ArrayList<>();
List<Integer> ints = new ArrayList<>();
System.out.println(strings.getClass() == ints.getClass());
```

- [x] true
- [ ] false
- [ ] Lỗi biên dịch
- [ ] ClassCastException

> **Giải thích:** Type erasure: cả hai là ArrayList at runtime. getClass() == getClass() → true. Generic type info bị xóa.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

Wildcard `?` trong generics dùng khi:

- [x] Không biết/không quan tâm exact type, cho phép flexibility trong method parameters
- [ ] Thay thế T
- [ ] Chỉ cho Collections
- [ ] Performance

> **Giải thích:** `?`: unknown type. `? extends T`: upper bound (read). `? super T`: lower bound (write). PECS: Producer Extends, Consumer Super.

## Câu 8

[TYPE: SELECT_RESULT]

```java
static double sum(List<? extends Number> list) {
    double total = 0;
    for (Number n : list) total += n.doubleValue();
    return total;
}
System.out.println(sum(List.of(1, 2, 3)));
System.out.println(sum(List.of(1.5, 2.5)));
```

- [x] 6.0 và 4.0
- [ ] Lỗi biên dịch
- [ ] 6 và 4
- [ ] ClassCastException

> **Giải thích:** `? extends Number`: accepts List<Integer>, List<Double>, etc. sum([1,2,3])=6.0. sum([1.5,2.5])=4.0. Upper bounded wildcard.

## Câu 9

[TYPE: SELECT_RESULT]

```java
static <T> T firstOrDefault(List<T> list, T defaultValue) {
    return list.isEmpty() ? defaultValue : list.get(0);
}
String result = firstOrDefault(List.of("A", "B"), "X");
Integer num = firstOrDefault(List.of(), 42);
System.out.println(result + " " + num);
```

- [x] A 42
- [ ] X 42
- [ ] A null
- [ ] Lỗi biên dịch

> **Giải thích:** Generic method: <T> inferred. List not empty → "A". Empty list → defaultValue 42.

## Câu 10

[TYPE: FILL_BLANK]

PECS: Producer `___`, Consumer Super.

- [x] Extends
- [ ] Equals
- [ ] Excludes
- [ ] Encapsulates

> **Giải thích:** PECS (Josh Bloch): Read from → `? extends T` (covariant). Write to → `? super T` (contravariant). Ví dụ: Collections.copy(dest super, src extends).

## Câu 11

[TYPE: SELECT_RESULT]

```java
List<? super Integer> list = new ArrayList<Number>();
list.add(1);
list.add(2);
// list.add(1.5); // compile error - Double not Integer
Object first = list.get(0); // can only get Object
System.out.println(first);
```

- [x] 1
- [ ] Lỗi biên dịch
- [ ] null
- [ ] ClassCastException

> **Giải thích:** `? super Integer`: lower bound. Can add Integer (and subtypes). get() returns Object (type unknown). add safe, read restricted.

## Câu 12

[TYPE: TRUE_FALSE]

Mệnh đề: "Không thể tạo array của generic type: `new T[]` hoặc `new List<String>[]` sẽ compile error."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Generic array creation: compile error. Type erasure → runtime không biết T. Workaround: (T[]) new Object[size] (unsafe cast). Arrays are reified, generics are erased.

## Câu 13

[TYPE: SELECT_RESULT]

```java
static <T extends Comparable<T>> T max(T a, T b) {
    return a.compareTo(b) >= 0 ? a : b;
}
System.out.println(max(3, 7));
System.out.println(max("apple", "banana"));
```

- [x] 7 và banana
- [ ] 3 và apple
- [ ] Lỗi biên dịch
- [ ] 7 và apple

> **Giải thích:** T extends Comparable<T>: T phải comparable. max(3,7): 7. max("apple","banana"): "banana" > "apple" alphabetically.

## Câu 14

[TYPE: SELECT_RESULT]

```java
class Container<T> {
    private T item;
    void set(T item) { this.item = item; }
    T get() { return item; }
}
Container<Number> container = new Container<>();
container.set(42);        // Integer is-a Number
container.set(3.14);      // Double is-a Number
System.out.println(container.get());
```

- [x] 3.14
- [ ] 42
- [ ] Lỗi biên dịch
- [ ] null

> **Giải thích:** Container<Number>: accepts any Number subtype. set(42) → set(3.14) overwrites. get() → 3.14 (last set value).

## Câu 15

[TYPE: MULTIPLE_CHOICE]

`<T extends A & B & C>` nghĩa gì?

- [x] T phải implement/extend tất cả A, B, C (multiple bounds, A phải là class nếu có)
- [ ] T là A hoặc B hoặc C
- [ ] Chỉ extend A
- [ ] Lỗi cú pháp

> **Giải thích:** Multiple bounds: `&` separator. Class first (nếu có), rồi interfaces. Ví dụ: `<T extends Number & Comparable<T> & Serializable>`. T phải thỏa tất cả.

## Câu 16

[TYPE: SELECT_RESULT]

```java
static <T extends Number & Comparable<T>> T max(List<T> list) {
    return list.stream().max(Comparable::compareTo).orElseThrow();
}
List<Integer> ints = List.of(3, 1, 4, 1, 5);
System.out.println(max(ints));
```

- [x] 5
- [ ] 1
- [ ] 3
- [ ] Lỗi biên dịch

> **Giải thích:** Integer extends Number và implements Comparable<Integer>. max stream → 5. Multiple bounds satisfied.

## Câu 17

[TYPE: FILL_BLANK]

Raw type `List` (không có generic) compile nhưng tạo `___` warning.

- [x] unchecked
- [ ] deprecated
- [ ] type
- [ ] cast

> **Giải thích:** Raw types: backward compatibility. Warning: "unchecked or unsafe operations". @SuppressWarnings("unchecked") to suppress. Avoid raw types in new code.

## Câu 18

[TYPE: SELECT_RESULT]

```java
List rawList = new ArrayList();
rawList.add("Hello");
rawList.add(42);
rawList.add(true);
System.out.println(rawList.size());
```

- [x] 3
- [ ] Lỗi biên dịch
- [ ] ClassCastException
- [ ] 1

> **Giải thích:** Raw List: no type checking. Can add any Object. size=3. Nguy hiểm: no compile-time safety. Deprecated practice.

## Câu 19

[TYPE: SELECT_RESULT]

```java
interface Transformer<I, O> {
    O transform(I input);
}
Transformer<String, Integer> lengthOf = String::length;
System.out.println(lengthOf.transform("Hello"));
```

- [x] 5
- [ ] Hello
- [ ] Lỗi biên dịch
- [ ] null

> **Giải thích:** Generic interface: I=String, O=Integer. transform("Hello") → String.length() → 5. Method reference matches functional interface.

## Câu 20

[TYPE: TRUE_FALSE]

Mệnh đề: "List<Integer> KHÔNG phải subtype của List<Number>, dù Integer extends Number."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Generics are invariant. List<Integer> ≠ List<Number>. Reason: List<Number> could add Double, breaking List<Integer>. Dùng wildcard: List<? extends Number>.

## Câu 21

[TYPE: SELECT_RESULT]

```java
static void printAll(List<?> list) {
    for (Object item : list) {
        System.out.print(item + " ");
    }
}
printAll(List.of("A", "B"));
System.out.println();
printAll(List.of(1, 2, 3));
```

- [x] A B (newline) 1 2 3
- [ ] Lỗi biên dịch
- [ ] null
- [ ] ClassCastException

> **Giải thích:** `List<?>`: unbounded wildcard. Accepts any List. Read only as Object. Cannot add (except null). Maximum flexibility for reading.

## Câu 22

[TYPE: SELECT_RESULT]

```java
class Stack<E> {
    private Object[] elements;
    private int size;
    Stack(int capacity) { elements = new Object[capacity]; }
    void push(E item) { elements[size++] = item; }
    @SuppressWarnings("unchecked")
    E pop() { return (E) elements[--size]; }
}
Stack<String> stack = new Stack<>(10);
stack.push("A");
stack.push("B");
System.out.println(stack.pop() + " " + stack.pop());
```

- [x] B A
- [ ] A B
- [ ] Lỗi biên dịch
- [ ] ClassCastException

> **Giải thích:** Stack LIFO: push A, B. pop → B, pop → A. Object[] with unchecked cast — common pattern (erasure prevents new E[]).

## Câu 23

[TYPE: MULTIPLE_CHOICE]

Generic method vs Generic class?

- [x] Method: type param scoped to method; Class: type param scoped to entire class
- [ ] Giống nhau
- [ ] Method không thể generic
- [ ] Class không thể generic

> **Giải thích:** Generic method: `<T> T method(T param)`. Type inferred per call. Generic class: `class Box<T>`. Type fixed at instantiation. Method can be generic even in non-generic class.

## Câu 24

[TYPE: SELECT_RESULT]

```java
static <T> List<T> repeat(T item, int count) {
    List<T> list = new ArrayList<>();
    for (int i = 0; i < count; i++) list.add(item);
    return list;
}
List<String> result = repeat("X", 3);
System.out.println(result);
```

- [x] [X, X, X]
- [ ] [X]
- [ ] Lỗi biên dịch
- [ ] null

> **Giải thích:** Generic method: T inferred as String. Create list with 3 "X"s. Type-safe, no casting needed.

## Câu 25

[TYPE: FILL_BLANK]

`Class<T>` là generic version của `___` token cho type-safe reflection.

- [x] Class (type)
- [ ] Object
- [ ] String
- [ ] Method

> **Giải thích:** Class<T>: type token. String.class → Class<String>. newInstance() returns T. Dùng trong factory patterns, service locators, dependency injection.

## Câu 26

[TYPE: SELECT_RESULT]

```java
Map<String, List<Integer>> map = new HashMap<>();
map.put("evens", List.of(2, 4, 6));
map.put("odds", List.of(1, 3, 5));
List<Integer> evens = map.get("evens");
System.out.println(evens.get(1));
```

- [x] 4
- [ ] 2
- [ ] 6
- [ ] Lỗi biên dịch

> **Giải thích:** Nested generics: Map<String, List<Integer>>. get("evens") → List<Integer> [2,4,6]. get(1) → 4. No casting needed.

## Câu 27

[TYPE: SELECT_RESULT]

```java
interface Repository<T, ID> {
    T findById(ID id);
    void save(T entity);
}
class UserRepo implements Repository<String, Integer> {
    Map<Integer, String> store = new HashMap<>();
    public String findById(Integer id) { return store.get(id); }
    public void save(String entity) { store.put(store.size(), entity); }
}
UserRepo repo = new UserRepo();
repo.save("Alice");
System.out.println(repo.findById(0));
```

- [x] Alice
- [ ] null
- [ ] 0
- [ ] Lỗi biên dịch

> **Giải thích:** Generic interface implemented with concrete types: T=String, ID=Integer. save → key=0, value="Alice". findById(0) → "Alice".

## Câu 28

[TYPE: TRUE_FALSE]

Mệnh đề: "Diamond operator <> (Java 7+) cho phép compiler infer type arguments từ context."

- [x] Đúng
- [ ] Sai

> **Giải thích:** `List<String> list = new ArrayList<>()`: compiler infers String. Giảm verbosity. Var (Java 10): `var list = new ArrayList<String>()`.

## Câu 29

[TYPE: SELECT_RESULT]

```java
static <T> boolean contains(T[] array, T target) {
    for (T item : array) {
        if (item.equals(target)) return true;
    }
    return false;
}
String[] names = {"An", "Bình", "Cường"};
System.out.println(contains(names, "Bình"));
System.out.println(contains(names, "Dung"));
```

- [x] true và false
- [ ] true và true
- [ ] false và false
- [ ] Lỗi biên dịch

> **Giải thích:** Generic method with array. contains(["An","Bình","Cường"], "Bình") → true. "Dung" not found → false.

## Câu 30

[TYPE: MULTIPLE_CHOICE]

Bounded wildcard: `? extends T` vs `? super T`?

- [x] extends: read-only (get T), covariant; super: write-only (add T), contravariant
- [ ] Giống nhau
- [ ] extends for write
- [ ] super for read

> **Giải thích:** `? extends T`: producer, safe to read T. `? super T`: consumer, safe to add T. PECS principle. Combines with generic methods for flexibility.

## Câu 31

[TYPE: SELECT_RESULT]

```java
static <T extends Comparable<? super T>> void sort(List<T> list) {
    list.sort(null); // natural order
}
List<Integer> nums = new ArrayList<>(List.of(3, 1, 2));
sort(nums);
System.out.println(nums);
```

- [x] [1, 2, 3]
- [ ] [3, 1, 2]
- [ ] Lỗi biên dịch
- [ ] [3, 2, 1]

> **Giải thích:** `Comparable<? super T>`: T hoặc parent implements Comparable. Integer implements Comparable<Integer>. sort natural order → [1,2,3]. Collections.sort signature.

## Câu 32

[TYPE: SELECT_RESULT]

```java
Optional<String> opt = Optional.of("Hello");
Optional<Integer> len = opt.map(String::length);
System.out.println(len.get());
```

- [x] 5
- [ ] Hello
- [ ] null
- [ ] Lỗi biên dịch

> **Giải thích:** Optional<String>.map(String::length) → Optional<Integer>. "Hello".length() = 5. Generic transformation.

## Câu 33

[TYPE: FILL_BLANK]

`Collections.emptyList()` trả về empty, immutable `___<T>`.

- [x] List
- [ ] Set
- [ ] Map
- [ ] Collection

> **Giải thích:** `Collections.<String>emptyList()`: type-safe empty list. Immutable (UnsupportedOperationException on add). Generic type inference from context.

## Câu 34

[TYPE: SELECT_RESULT]

```java
@FunctionalInterface
interface Converter<F, T> {
    T convert(F from);
}
Converter<String, Integer> toInt = Integer::parseInt;
Converter<Integer, String> toStr = String::valueOf;
int num = toInt.convert("42");
String str = toStr.convert(num);
System.out.println(str.getClass().getSimpleName() + ": " + str);
```

- [x] String: 42
- [ ] Integer: 42
- [ ] Lỗi biên dịch
- [ ] null

> **Giải thích:** Generic functional interface: F→T. toInt: String→Integer. toStr: Integer→String. Chain: "42"→42→"42". Type-safe conversions.

## Câu 35

[TYPE: SELECT_RESULT]

```java
class TypeRef<T> {
    // Captures generic type at subclass creation
}
TypeRef<List<String>> ref = new TypeRef<List<String>>() {};
System.out.println(ref.getClass().getGenericSuperclass());
```

- [x] TypeRef<java.util.List<java.lang.String>> (hoặc tương tự)
- [ ] TypeRef
- [ ] Object
- [ ] null

> **Giải thích:** Super type token: anonymous subclass preserves generic info. getGenericSuperclass() → ParameterizedType with List<String>. Workaround for type erasure.

## Câu 36

[TYPE: MULTIPLE_CHOICE]

Recursive type bound: `<T extends Comparable<T>>` nghĩa gì?

- [x] T phải comparable với chính nó (self-referencing bound)
- [ ] T extends Object
- [ ] T là recursive data structure
- [ ] Lỗi cú pháp

> **Giải thích:** Self-bounded: Integer implements Comparable<Integer>. Ensures compareTo(T) takes same type. Common for max, min, sort. Curiously Recurring Template Pattern (CRTP).

## Câu 37

[TYPE: TRUE_FALSE]

Mệnh đề: "Generics chỉ hoạt động với reference types, KHÔNG dùng được với primitives (int, double, etc.)."

- [x] Đúng
- [ ] Sai

> **Giải thích:** No `List<int>`. Must use `List<Integer>` (autoboxing). Type erasure → Object, primitives not Object. Valhalla project (future): primitive generics.

## Câu 38

[TYPE: SELECT_RESULT]

```java
static <K, V> Map<V, K> invertMap(Map<K, V> original) {
    Map<V, K> inverted = new HashMap<>();
    original.forEach((k, v) -> inverted.put(v, k));
    return inverted;
}
Map<String, Integer> map = Map.of("A", 1, "B", 2);
Map<Integer, String> inverted = invertMap(map);
System.out.println(inverted.get(1));
```

- [x] A
- [ ] 1
- [ ] B
- [ ] null

> **Giải thích:** Generic method with 2 type params: K=String, V=Integer. Invert: {1="A", 2="B"}. get(1) → "A".

## Câu 39

[TYPE: SELECT_RESULT]

```java
class Wrapper<T> {
    T value;
    Wrapper(T v) { value = v; }

    <R> Wrapper<R> map(Function<T, R> mapper) {
        return new Wrapper<>(mapper.apply(value));
    }
}
Wrapper<String> w = new Wrapper<>("hello");
Wrapper<Integer> len = w.map(String::length);
System.out.println(len.value);
```

- [x] 5
- [ ] hello
- [ ] Lỗi biên dịch
- [ ] null

> **Giải thích:** Generic class + generic method. map: T→R transformation. Wrapper<String> → Wrapper<Integer>. Functor pattern.

## Câu 40

[TYPE: SELECT_RESULT]

```java
static <T> T[] toArray(List<T> list, IntFunction<T[]> generator) {
    return list.toArray(generator.apply(0));
}
String[] arr = toArray(List.of("A", "B", "C"), String[]::new);
System.out.println(Arrays.toString(arr));
```

- [x] [A, B, C]
- [ ] Lỗi biên dịch
- [ ] ClassCastException
- [ ] []

> **Giải thích:** Generic array creation workaround. IntFunction<T[]> generates typed array. String[]::new → (size) -> new String[size]. toArray fills it.

## Câu 41

[TYPE: FILL_BLANK]

`@SafeVarargs` annotation suppresses `___` warnings cho generic varargs methods.

- [x] heap pollution (unchecked)
- [ ] deprecated
- [ ] null
- [ ] type

> **Giải thích:** Generic varargs: potential heap pollution. @SafeVarargs: promise method is safe. Only on final/static/private methods. Ví dụ: Collections.addAll, Arrays.asList.

## Câu 42

[TYPE: SELECT_RESULT]

```java
@SafeVarargs
static <T> List<T> listOf(T... items) {
    return List.of(items);
}
List<String> list = listOf("A", "B", "C");
System.out.println(list);
```

- [x] [A, B, C]
- [ ] Lỗi biên dịch
- [ ] Heap pollution
- [ ] null

> **Giải thích:** Generic varargs safe with @SafeVarargs. No heap pollution in this case. Return immutable list of items.

## Câu 43

[TYPE: SELECT_RESULT]

```java
record Pair<A, B>(A first, B second) {
    <C> Pair<C, B> mapFirst(Function<A, C> mapper) {
        return new Pair<>(mapper.apply(first), second);
    }
}
Pair<String, Integer> p = new Pair<>("hello", 42);
Pair<Integer, Integer> mapped = p.mapFirst(String::length);
System.out.println(mapped.first() + " " + mapped.second());
```

- [x] 5 42
- [ ] hello 42
- [ ] Lỗi biên dịch
- [ ] null

> **Giải thích:** Generic record + generic method. mapFirst: A→C, keep B. "hello"→5. Result: Pair(5, 42).

## Câu 44

[TYPE: MULTIPLE_CHOICE]

Type witness: `Collections.<String>emptyList()` dùng khi:

- [x] Compiler không thể infer type, explicit type argument cần thiết
- [ ] Luôn luôn
- [ ] Performance
- [ ] Chỉ cho Collections

> **Giải thích:** Type witness: explicit type arg. `Collections.<String>emptyList()`. Usually unnecessary (inference). Needed in ambiguous contexts hoặc pre-Java 8.

## Câu 45

[TYPE: SELECT_RESULT]

```java
static <T> boolean allMatch(List<T> list, Predicate<T> predicate) {
    for (T item : list) {
        if (!predicate.test(item)) return false;
    }
    return true;
}
System.out.println(allMatch(List.of(2, 4, 6), n -> n % 2 == 0));
System.out.println(allMatch(List.of(2, 3, 6), n -> n % 2 == 0));
```

- [x] true và false
- [ ] true và true
- [ ] false và false
- [ ] Lỗi biên dịch

> **Giải thích:** Generic allMatch: check all elements. [2,4,6] all even → true. [2,3,6] 3 is odd → false.

## Câu 46

[TYPE: TRUE_FALSE]

Mệnh đề: "instanceof không thể check generic type: `obj instanceof List<String>` là compile error."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Erasure: runtime không biết generic type. `obj instanceof List<?>` OK. `obj instanceof List<String>` compile error. Pattern matching (Java 16+): limited support.

## Câu 47

[TYPE: SELECT_RESULT]

```java
static <E> Set<E> union(Set<? extends E> s1, Set<? extends E> s2) {
    Set<E> result = new HashSet<>(s1);
    result.addAll(s2);
    return result;
}
Set<Integer> ints = Set.of(1, 2, 3);
Set<Double> doubles = Set.of(1.5, 2.5);
Set<Number> combined = union(ints, doubles);
System.out.println(combined.size());
```

- [x] 5
- [ ] 3
- [ ] 2
- [ ] Lỗi biên dịch

> **Giải thích:** E=Number. s1: Set<Integer> (extends Number). s2: Set<Double> (extends Number). Union: {1,2,3,1.5,2.5} = 5 elements.

## Câu 48

[TYPE: SELECT_RESULT]

```java
class Registry<T> {
    private Map<String, T> items = new HashMap<>();
    void register(String name, T item) { items.put(name, item); }
    Optional<T> get(String name) { return Optional.ofNullable(items.get(name)); }
}
Registry<Integer> reg = new Registry<>();
reg.register("port", 8080);
reg.register("timeout", 30);
System.out.println(reg.get("port").orElse(0));
System.out.println(reg.get("missing").orElse(-1));
```

- [x] 8080 và -1
- [ ] 8080 và null
- [ ] 0 và -1
- [ ] Lỗi biên dịch

> **Giải thích:** Generic registry. get("port") → Optional[8080] → 8080. get("missing") → Optional.empty → -1 (default).

## Câu 49

[TYPE: FILL_BLANK]

Generic method syntax: return type trước method name, type params trong `___`.

- [x] angle brackets <> (dấu ngoặc nhọn)
- [ ] parentheses ()
- [ ] square brackets []
- [ ] curly braces {}

> **Giải thích:** `<T> T method(T param)`: <T> trước return type. Declared once, used throughout method. Inferred at call site.

## Câu 50

[TYPE: SELECT_RESULT]

```java
interface Mapper<A, B> {
    B map(A input);

    default <C> Mapper<A, C> andThen(Mapper<B, C> after) {
        return input -> after.map(this.map(input));
    }
}
Mapper<String, Integer> length = String::length;
Mapper<Integer, Boolean> isEven = n -> n % 2 == 0;
Mapper<String, Boolean> isEvenLength = length.andThen(isEven);
System.out.println(isEvenLength.map("Hi"));
System.out.println(isEvenLength.map("Hey"));
```

- [x] true và false
- [ ] false và true
- [ ] Lỗi biên dịch
- [ ] null

> **Giải thích:** Function composition with generics. "Hi"→2→true (even). "Hey"→3→false (odd). andThen chains mappers. Type-safe pipeline.

## Câu 51

[TYPE: SELECT_RESULT]

```java
class TypeSafePrinter {
    static <T> void printClass(T obj) {
        System.out.println(obj.getClass().getSimpleName());
    }
}
TypeSafePrinter.printClass("Hello");
TypeSafePrinter.printClass(42);
TypeSafePrinter.printClass(3.14);
```

- [x] String, Integer, Double
- [ ] Object, Object, Object
- [ ] T, T, T
- [ ] Lỗi biên dịch

> **Giải thích:** getClass(): runtime type (not erased T). "Hello"→String. 42→Integer (autobox). 3.14→Double. Runtime type preserved.

## Câu 52

[TYPE: MULTIPLE_CHOICE]

Khi nào dùng generic class vs generic method?

- [x] Class: khi type dùng xuyên suốt class; Method: khi type chỉ liên quan đến method cụ thể
- [ ] Luôn dùng class
- [ ] Luôn dùng method
- [ ] Không có quy tắc

> **Giải thích:** Class generic: Container<T>, Repository<T, ID>. Method generic: utility methods (swap, max, toArray). Method generic more flexible, less coupling.

## Câu 53

[TYPE: SELECT_RESULT]

```java
static <T> void copy(List<? super T> dest, List<? extends T> src) {
    for (T item : src) {
        dest.add(item);
    }
}
List<Number> dest = new ArrayList<>();
List<Integer> src = List.of(1, 2, 3);
copy(dest, src);
System.out.println(dest);
```

- [x] [1, 2, 3]
- [ ] []
- [ ] Lỗi biên dịch
- [ ] ClassCastException

> **Giải thích:** PECS: src (extends, producer), dest (super, consumer). Read Integer from src, write to Number list. T=Integer. dest = [1,2,3].

## Câu 54

[TYPE: TRUE_FALSE]

Mệnh đề: "Generic type information CÓ thể lấy tại runtime qua reflection cho fields và method signatures."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Field.getGenericType(), Method.getGenericReturnType() → ParameterizedType. Field `List<String>` → ParameterizedType{List, [String]}. Erasure cho local variables, preserved cho declarations.

## Câu 55

[TYPE: SELECT_RESULT]

```java
class Result<T> {
    private final T value;
    private final String error;
    private Result(T value, String error) {
        this.value = value;
        this.error = error;
    }
    static <T> Result<T> success(T value) { return new Result<>(value, null); }
    static <T> Result<T> failure(String error) { return new Result<>(null, error); }
    boolean isSuccess() { return error == null; }
    T getValue() { return value; }
    String getError() { return error; }
}
Result<Integer> r1 = Result.success(42);
Result<Integer> r2 = Result.failure("Not found");
System.out.println(r1.isSuccess() + " " + r1.getValue());
System.out.println(r2.isSuccess() + " " + r2.getError());
```

- [x] true 42 và false Not found
- [ ] Lỗi biên dịch
- [ ] true null và false null
- [ ] true 42 và true Not found

> **Giải thích:** Generic Result type: Either pattern. success(42) → isSuccess=true, value=42. failure("Not found") → isSuccess=false, error="Not found".

## Câu 56

[TYPE: SELECT_RESULT]

```java
sealed interface Shape<T extends Number> permits Circle, Rectangle {}
record Circle<T extends Number>(T radius) implements Shape<T> {}
record Rectangle<T extends Number>(T width, T height) implements Shape<T> {}

Shape<Double> shape = new Circle<>(3.14);
if (shape instanceof Circle<Double> c) {
    System.out.println("Circle: " + c.radius());
}
```

- [x] Circle: 3.14
- [ ] Lỗi biên dịch
- [ ] null
- [ ] ClassCastException

> **Giải thích:** Sealed interface + generics + pattern matching. Circle<Double> match → radius=3.14. Type-safe deconstruction.

## Câu 57

[TYPE: FILL_BLANK]

`Comparable<T>` generic interface: `compareTo(T other)` so sánh `___` type.

- [x] same (cùng)
- [ ] any
- [ ] Object
- [ ] Comparable

> **Giải thích:** Comparable<T>: compareTo(T). Same type comparison. Integer implements Comparable<Integer>. Pre-generics: compareTo(Object) → casting needed.

## Câu 58

[TYPE: SELECT_RESULT]

```java
class Cache<K, V> {
    private final Map<K, V> store = new LinkedHashMap<>();
    private final int maxSize;
    Cache(int maxSize) { this.maxSize = maxSize; }
    void put(K key, V value) {
        store.put(key, value);
        if (store.size() > maxSize) {
            K oldest = store.keySet().iterator().next();
            store.remove(oldest);
        }
    }
    V get(K key) { return store.get(key); }
    int size() { return store.size(); }
}
Cache<String, Integer> cache = new Cache<>(2);
cache.put("a", 1);
cache.put("b", 2);
cache.put("c", 3);
System.out.println(cache.get("a"));
System.out.println(cache.size());
```

- [x] null và 2
- [ ] 1 và 3
- [ ] 1 và 2
- [ ] null và 3

> **Giải thích:** LRU-like cache, maxSize=2. Put a, b → [a,b]. Put c → evict oldest (a) → [b,c]. get("a") → null (evicted). size=2.

## Câu 59

[TYPE: SELECT_RESULT]

```java
static <T> Predicate<T> not(Predicate<T> predicate) {
    return predicate.negate();
}
List<String> result = List.of("", "hello", "", "world").stream()
    .filter(not(String::isEmpty))
    .collect(Collectors.toList());
System.out.println(result);
```

- [x] [hello, world]
- [ ] [, , ]
- [ ] [hello, , world]
- [ ] Lỗi biên dịch

> **Giải thích:** Generic not: negate predicate. not(isEmpty) → isNotEmpty. Filter non-empty strings. [hello, world]. Java 11: Predicate.not() built-in.

## Câu 60

[TYPE: MULTIPLE_CHOICE]

Heap pollution là gì?

- [x] Variable of parameterized type refers to object not of that type (unchecked operations)
- [ ] Memory leak
- [ ] Stack overflow
- [ ] Thread safety issue

> **Giải thích:** Heap pollution: `List<String> list = (List) rawList;` rawList có Integer → ClassCastException later. Caused by raw types, unchecked casts, generic varargs.

## Câu 61

[TYPE: SELECT_RESULT]

```java
interface Builder<T> {
    T build();
}
class StringBuilderWrapper implements Builder<String> {
    private StringBuilder sb = new StringBuilder();
    StringBuilderWrapper add(String s) { sb.append(s); return this; }
    public String build() { return sb.toString(); }
}
String result = new StringBuilderWrapper().add("Hello").add(" ").add("World").build();
System.out.println(result);
```

- [x] Hello World
- [ ] HelloWorld
- [ ] Lỗi biên dịch
- [ ] null

> **Giải thích:** Generic Builder interface, concrete implementation. Fluent API: add → add → add → build. Result: "Hello World".

## Câu 62

[TYPE: SELECT_RESULT]

```java
static <T> Optional<T> firstMatch(List<T> list, Predicate<T> predicate) {
    for (T item : list) {
        if (predicate.test(item)) return Optional.of(item);
    }
    return Optional.empty();
}
Optional<String> result = firstMatch(
    List.of("an", "binh", "cuong"), s -> s.length() > 3);
System.out.println(result.orElse("none"));
```

- [x] binh
- [ ] cuong
- [ ] none
- [ ] an

> **Giải thích:** firstMatch: first element passing predicate. "an"(2)✗, "binh"(4)✓ → return. Generic, type-safe.

## Câu 63

[TYPE: TRUE_FALSE]

Mệnh đề: "Generic exceptions (class MyException<T> extends Exception) KHÔNG được phép trong Java."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Cannot: `class MyException<T> extends Exception`. Reason: catch clause cannot use generic type (erasure). Can throw type parameter: `<T extends Exception> void method() throws T`.

## Câu 64

[TYPE: SELECT_RESULT]

```java
static <X extends Exception> void doThrow(Exception e) throws X {
    @SuppressWarnings("unchecked")
    X ex = (X) e;
    throw ex;
}
try {
    doThrow(new RuntimeException("test"));
} catch (Exception e) {
    System.out.println(e.getMessage());
}
```

- [x] test
- [ ] Lỗi biên dịch
- [ ] ClassCastException
- [ ] null

> **Giải thích:** Generic exception: type parameter in throws. Unchecked cast due to erasure. RuntimeException caught. Message: "test".

## Câu 65

[TYPE: SELECT_RESULT]

```java
class EventBus<E> {
    private final List<Consumer<E>> listeners = new ArrayList<>();
    void subscribe(Consumer<E> listener) { listeners.add(listener); }
    void publish(E event) { listeners.forEach(l -> l.accept(event)); }
}
EventBus<String> bus = new EventBus<>();
List<String> received = new ArrayList<>();
bus.subscribe(received::add);
bus.subscribe(s -> received.add(s.toUpperCase()));
bus.publish("hello");
System.out.println(received);
```

- [x] [hello, HELLO]
- [ ] [hello]
- [ ] [HELLO]
- [ ] Lỗi biên dịch

> **Giải thích:** Generic EventBus<String>. 2 subscribers: add as-is + add uppercase. publish("hello") → [hello, HELLO]. Observer pattern with generics.

## Câu 66

[TYPE: FILL_BLANK]

`Supplier<T>` functional interface: method `get()` trả về object kiểu `___`.

- [x] T
- [ ] Object
- [ ] Void
- [ ] Boolean

> **Giải thích:** Supplier<T>.get(): lazy creation/computation. Supplier<String> s = () -> "Hello". Dùng trong lazy init, factory, Optional.orElseGet.

## Câu 67

[TYPE: SELECT_RESULT]

```java
static <T> T createDefault(Supplier<T> supplier) {
    return supplier.get();
}
String s = createDefault(() -> "Hello");
List<String> list = createDefault(ArrayList::new);
System.out.println(s + " " + list.getClass().getSimpleName());
```

- [x] Hello ArrayList
- [ ] null ArrayList
- [ ] Hello null
- [ ] Lỗi biên dịch

> **Giải thích:** Supplier: lazy creation. () -> "Hello" → String. ArrayList::new → new ArrayList(). Generic factory pattern.

## Câu 68

[TYPE: SELECT_RESULT]

```java
class Tuple<A, B, C> {
    final A first;
    final B second;
    final C third;
    Tuple(A a, B b, C c) { first = a; second = b; third = c; }
}
Tuple<String, Integer, Boolean> t = new Tuple<>("test", 42, true);
System.out.println(t.first + " " + t.second + " " + t.third);
```

- [x] test 42 true
- [ ] Lỗi biên dịch
- [ ] null null null
- [ ] test null true

> **Giải thích:** Triple generic: 3 type parameters. A=String, B=Integer, C=Boolean. All fields accessible. Type-safe tuple.

## Câu 69

[TYPE: MULTIPLE_CHOICE]

Covariant return types và generics?

- [x] Override method có thể return subtype (covariant), nhưng generic parameters are invariant
- [ ] Generics covariant
- [ ] Không liên quan
- [ ] Generics contravariant

> **Giải thích:** Method return: covariant (List → ArrayList OK). Generic params: invariant (List<Number> ≠ List<Integer>). Wildcards cho variance: extends (covariant), super (contravariant).

## Câu 70

[TYPE: SELECT_RESULT]

```java
interface Transform<T> extends Function<T, T> {
    // UnaryOperator-like
}
Transform<String> upper = String::toUpperCase;
Transform<String> trim = String::trim;
Transform<String> combined = s -> trim.apply(upper.apply(s));
System.out.println(combined.apply("  hello  "));
```

- [x] HELLO
- [ ]   HELLO  
- [ ] hello
- [ ]   hello  

> **Giải thích:** upper("  hello  ") → "  HELLO  ". trim("  HELLO  ") → "HELLO". Chain: upper then trim.

## Câu 71

[TYPE: TRUE_FALSE]

Mệnh đề: "Static fields không thể dùng class type parameter: `static T field` là compile error."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Static context: no instance → no type parameter. `class Box<T> { static T value; }` → error. Static methods CAN declare own type params: `static <T> T method()`.

## Câu 72

[TYPE: SELECT_RESULT]

```java
class Store<T> {
    // static T instance; // would be compile error
    static <T> Store<T> empty() { return new Store<>(); }
}
Store<String> s1 = Store.empty();
Store<Integer> s2 = Store.empty();
System.out.println(s1.getClass() == s2.getClass());
```

- [x] true
- [ ] false
- [ ] Lỗi biên dịch
- [ ] ClassCastException

> **Giải thích:** Static method with own <T>. Erasure: both are Store at runtime. Same class object. Generic static factory method.

## Câu 73

[TYPE: SELECT_RESULT]

```java
static <T> List<T> filter(List<T> list, Predicate<? super T> predicate) {
    return list.stream().filter(predicate).collect(Collectors.toList());
}
Predicate<Number> isPositive = n -> n.doubleValue() > 0;
List<Integer> result = filter(List.of(-1, 2, -3, 4), isPositive);
System.out.println(result);
```

- [x] [2, 4]
- [ ] [-1, 2, -3, 4]
- [ ] Lỗi biên dịch
- [ ] []

> **Giải thích:** `Predicate<? super T>`: accepts Predicate<Number> for List<Integer> (Number super Integer). PECS: consumer (predicate tests T). Filter positive: [2, 4].

## Câu 74

[TYPE: SELECT_RESULT]

```java
class LazyValue<T> {
    private Supplier<T> supplier;
    private T value;
    private boolean computed;
    LazyValue(Supplier<T> supplier) { this.supplier = supplier; }
    T get() {
        if (!computed) {
            value = supplier.get();
            computed = true;
        }
        return value;
    }
}
LazyValue<String> lazy = new LazyValue<>(() -> {
    System.out.print("Computing ");
    return "Result";
});
System.out.print(lazy.get() + " ");
System.out.print(lazy.get());
```

- [x] Computing Result Result
- [ ] Computing Result Computing Result
- [ ] Result Result
- [ ] Computing Computing Result Result

> **Giải thích:** Lazy evaluation: computed only once. First get: "Computing " + "Result ". Second get: cached → "Result". No recomputation.

## Câu 75

[TYPE: FILL_BLANK]

`BiFunction<T, U, R>` nhận 2 params kiểu T, U và trả về `___`.

- [x] R
- [ ] T
- [ ] U
- [ ] void

> **Giải thích:** BiFunction<T, U, R>: apply(T, U) → R. Ví dụ: BiFunction<String, Integer, String> repeat = String::repeat. Two-param function.

## Câu 76

[TYPE: SELECT_RESULT]

```java
static <T, R> List<R> mapList(List<T> list, Function<T, R> mapper) {
    return list.stream().map(mapper).collect(Collectors.toList());
}
List<String> names = List.of("An", "Bình", "Cường");
List<Integer> lengths = mapList(names, String::length);
System.out.println(lengths);
```

- [x] [2, 4, 5]
- [ ] [An, Bình, Cường]
- [ ] Lỗi biên dịch
- [ ] null

> **Giải thích:** Generic mapList: T=String, R=Integer. String::length maps each name. "An"→2, "Bình"→4, "Cường"→5.

## Câu 77

[TYPE: SELECT_RESULT]

```java
interface Validator<T> {
    boolean validate(T value);
    default Validator<T> and(Validator<T> other) {
        return value -> this.validate(value) && other.validate(value);
    }
    default Validator<T> or(Validator<T> other) {
        return value -> this.validate(value) || other.validate(value);
    }
}
Validator<String> notEmpty = s -> !s.isEmpty();
Validator<String> shortEnough = s -> s.length() <= 10;
Validator<String> combined = notEmpty.and(shortEnough);
System.out.println(combined.validate("Hello"));
System.out.println(combined.validate(""));
System.out.println(combined.validate("This is a very long string"));
```

- [x] true, false, false
- [ ] true, true, true
- [ ] true, false, true
- [ ] false, false, false

> **Giải thích:** Combined: notEmpty AND shortEnough. "Hello": !empty ✓ && ≤10 ✓ → true. "": empty ✗ → false. Long string: ≤10 ✗ → false.

## Câu 78

[TYPE: MULTIPLE_CHOICE]

`Enum<E extends Enum<E>>` pattern nghĩa gì?

- [x] Self-bounded type: mỗi enum type E tham chiếu đến chính nó, đảm bảo type safety
- [ ] Enum extends Object
- [ ] Syntax error
- [ ] Recursive data

> **Giải thích:** `enum Color extends Enum<Color>`. E = Color. compareTo(Color), valueOf returns Color. Self-referencing bound ensures type safety.

## Câu 79

[TYPE: SELECT_RESULT]

```java
class Converter {
    @SuppressWarnings("unchecked")
    static <T> T convert(Object obj, Class<T> type) {
        if (type.isInstance(obj)) return type.cast(obj);
        throw new ClassCastException("Cannot convert");
    }
}
String s = Converter.convert("Hello", String.class);
System.out.println(s);
try {
    Integer i = Converter.convert("Hello", Integer.class);
} catch (ClassCastException e) {
    System.out.println(e.getMessage());
}
```

- [x] Hello và Cannot convert
- [ ] Hello và null
- [ ] Lỗi biên dịch
- [ ] ClassCastException

> **Giải thích:** Type-safe cast with Class<T>. "Hello" is String → OK. "Hello" is not Integer → ClassCastException. Type token pattern.

## Câu 80

[TYPE: TRUE_FALSE]

Mệnh đề: "Bridge methods được compiler tạo để đảm bảo polymorphism hoạt động với generic erasure."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Bridge method: compiler-generated. `class StringBox extends Box<String> { String get() }` → bridge: `Object get() { return get(); }`. Ensures override works after erasure.

## Câu 81

[TYPE: SELECT_RESULT]

```java
interface Pipeline<I, O> {
    O process(I input);

    default <N> Pipeline<I, N> then(Pipeline<O, N> next) {
        return input -> next.process(this.process(input));
    }
}
Pipeline<String, String> trim = String::trim;
Pipeline<String, Integer> length = String::length;
Pipeline<String, Boolean> isLong = s -> s.length() > 5;
Pipeline<String, Integer> pipeline = trim.then(length);
System.out.println(pipeline.process("  Hello World  "));
```

- [x] 11
- [ ] 15
- [ ] 5
- [ ] Lỗi biên dịch

> **Giải thích:** Pipeline composition. trim("  Hello World  ") → "Hello World" (11 chars). length → 11. Generic pipeline chaining.

## Câu 82

[TYPE: SELECT_RESULT]

```java
class TypedMap {
    private Map<Class<?>, Object> map = new HashMap<>();
    <T> void put(Class<T> type, T value) { map.put(type, value); }
    <T> T get(Class<T> type) { return type.cast(map.get(type)); }
}
TypedMap tm = new TypedMap();
tm.put(String.class, "Hello");
tm.put(Integer.class, 42);
System.out.println(tm.get(String.class) + " " + tm.get(Integer.class));
```

- [x] Hello 42
- [ ] Lỗi biên dịch
- [ ] null null
- [ ] ClassCastException

> **Giải thích:** Typesafe heterogeneous container (Effective Java). Class<T> as key, T as value. type.cast ensures safety. Different types in same map.

## Câu 83

[TYPE: SELECT_RESULT]

```java
static <T> Comparator<T> comparing(Function<T, ? extends Comparable> keyExtractor) {
    return (a, b) -> keyExtractor.apply(a).compareTo(keyExtractor.apply(b));
}
List<String> names = new ArrayList<>(List.of("Cường", "An", "Bình"));
names.sort(comparing(String::length));
System.out.println(names);
```

- [x] [An, Bình, Cường]
- [ ] [Cường, Bình, An]
- [ ] [An, Cường, Bình]
- [ ] Lỗi biên dịch

> **Giải thích:** Generic comparing: extract key, compare. Sort by length: An(2), Bình(4), Cường(5). Ascending. Comparator.comparing pattern.

## Câu 84

[TYPE: FILL_BLANK]

`Consumer<T>` functional interface: method `accept(T t)` có return type `___`.

- [x] void
- [ ] T
- [ ] boolean
- [ ] Object

> **Giải thích:** Consumer<T>.accept(T): consume value, no return. forEach(Consumer). BiConsumer<T,U>: accept(T, U). Side-effect operations.

## Câu 85

[TYPE: SELECT_RESULT]

```java
static <T> T reduce(List<T> list, T identity, BinaryOperator<T> op) {
    T result = identity;
    for (T item : list) result = op.apply(result, item);
    return result;
}
int sum = reduce(List.of(1, 2, 3, 4), 0, Integer::sum);
String concat = reduce(List.of("A", "B", "C"), "", String::concat);
System.out.println(sum + " " + concat);
```

- [x] 10 ABC
- [ ] 0 ABC
- [ ] 10
- [ ] Lỗi biên dịch

> **Giải thích:** Generic reduce: fold list. sum: 0+1+2+3+4=10. concat: ""+"A"+"B"+"C"="ABC". BinaryOperator<T> extends BiFunction<T,T,T>.

## Câu 86

[TYPE: SELECT_RESULT]

```java
class ImmutableList<E> {
    private final List<E> items;
    private ImmutableList(List<E> items) { this.items = List.copyOf(items); }

    static <E> ImmutableList<E> of(E... elements) {
        return new ImmutableList<>(Arrays.asList(elements));
    }
    E get(int index) { return items.get(index); }
    int size() { return items.size(); }
}
ImmutableList<String> list = ImmutableList.of("X", "Y", "Z");
System.out.println(list.get(1) + " " + list.size());
```

- [x] Y 3
- [ ] X 3
- [ ] Z 3
- [ ] Lỗi biên dịch

> **Giải thích:** Generic immutable list. of("X","Y","Z"). get(1)="Y". size=3. copyOf ensures immutability.

## Câu 87

[TYPE: MULTIPLE_CHOICE]

`Class<T>` bounded type token pattern dùng cho:

- [x] Type-safe factory, service locator, heterogeneous container (map with different value types)
- [ ] Chỉ reflection
- [ ] Chỉ serialization
- [ ] Performance

> **Giải thích:** Class<T> as type token: ensures compile-time safety. put(String.class, "value"), get(String.class) → String. No casting needed. Effective Java Item 33.

## Câu 88

[TYPE: TRUE_FALSE]

Mệnh đề: "var (Java 10) sử dụng type inference nhưng KHÔNG phải generic — compiler infers concrete type."

- [x] Đúng
- [ ] Sai

> **Giải thích:** `var list = new ArrayList<String>()`: infers ArrayList<String>. var: local variable type inference. Not a generic feature, compile-time deduction.

## Câu 89

[TYPE: SELECT_RESULT]

```java
interface Monad<T> {
    <R> Monad<R> flatMap(Function<T, Monad<R>> mapper);
    T get();
}
class Just<T> implements Monad<T> {
    private final T value;
    Just(T v) { value = v; }
    public <R> Monad<R> flatMap(Function<T, Monad<R>> mapper) { return mapper.apply(value); }
    public T get() { return value; }
}
Monad<Integer> result = new Just<>("Hello")
    .flatMap(s -> new Just<>(s.length()))
    .flatMap(n -> new Just<>(n * 2));
System.out.println(result.get());
```

- [x] 10
- [ ] 5
- [ ] Hello
- [ ] Lỗi biên dịch

> **Giải thích:** Monad with generics. "Hello" → length 5 → *2 = 10. flatMap chains transformations. Generic type changes at each step.

## Câu 90

[TYPE: SELECT_RESULT]

```java
static <T> Stream<T> interleave(Stream<T> a, Stream<T> b) {
    Iterator<T> itA = a.iterator();
    Iterator<T> itB = b.iterator();
    List<T> result = new ArrayList<>();
    while (itA.hasNext() || itB.hasNext()) {
        if (itA.hasNext()) result.add(itA.next());
        if (itB.hasNext()) result.add(itB.next());
    }
    return result.stream();
}
List<Integer> result = interleave(
    Stream.of(1, 3, 5), Stream.of(2, 4, 6))
    .collect(Collectors.toList());
System.out.println(result);
```

- [x] [1, 2, 3, 4, 5, 6]
- [ ] [1, 3, 5, 2, 4, 6]
- [ ] [2, 4, 6, 1, 3, 5]
- [ ] Lỗi biên dịch

> **Giải thích:** Interleave: alternate elements. 1, 2, 3, 4, 5, 6. Generic method works with any type.

## Câu 91

[TYPE: SELECT_RESULT]

```java
class Lazy<T> {
    private final Supplier<T> supplier;
    private volatile T value;
    Lazy(Supplier<T> s) { supplier = s; }
    T get() {
        if (value == null) {
            synchronized (this) {
                if (value == null) value = supplier.get();
            }
        }
        return value;
    }
}
Lazy<List<String>> lazy = new Lazy<>(() -> List.of("A", "B"));
System.out.println(lazy.get().size());
```

- [x] 2
- [ ] 0
- [ ] null
- [ ] Lỗi

> **Giải thích:** Thread-safe lazy initialization with generics. Double-checked locking. First get: create list. size=2. Generic Lazy<T>.

## Câu 92

[TYPE: FILL_BLANK]

`Function<T, R>` : T là `___` type, R là return type.

- [x] input (parameter)
- [ ] output
- [ ] result
- [ ] void

> **Giải thích:** Function<T, R>.apply(T): T → R. Ví dụ: Function<String, Integer> = String::length. Compose: f.andThen(g), f.compose(g).

## Câu 93

[TYPE: SELECT_RESULT]

```java
interface Repository<T, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    T save(T entity);
}
class InMemoryRepo<T> implements Repository<T, Integer> {
    private Map<Integer, T> store = new HashMap<>();
    private int nextId = 0;
    public Optional<T> findById(Integer id) { return Optional.ofNullable(store.get(id)); }
    public List<T> findAll() { return new ArrayList<>(store.values()); }
    public T save(T entity) { store.put(nextId++, entity); return entity; }
}
InMemoryRepo<String> repo = new InMemoryRepo<>();
repo.save("First");
repo.save("Second");
System.out.println(repo.findAll().size());
System.out.println(repo.findById(0).orElse("none"));
```

- [x] 2 và First
- [ ] 0 và none
- [ ] 2 và Second
- [ ] 1 và First

> **Giải thích:** Generic repository pattern. save: id 0→"First", id 1→"Second". findAll: 2 items. findById(0): "First".

## Câu 94

[TYPE: SELECT_RESULT]

```java
static <T> Map<T, Integer> frequency(List<T> list) {
    Map<T, Integer> freq = new LinkedHashMap<>();
    for (T item : list) {
        freq.merge(item, 1, Integer::sum);
    }
    return freq;
}
System.out.println(frequency(List.of("a", "b", "a", "c", "b", "a")));
```

- [x] {a=3, b=2, c=1}
- [ ] {a=1, b=1, c=1}
- [ ] {3, 2, 1}
- [ ] Lỗi biên dịch

> **Giải thích:** Generic frequency counter. merge: increment count. a=3, b=2, c=1. LinkedHashMap preserves insertion order.

## Câu 95

[TYPE: MULTIPLE_CHOICE]

Intersection types trong generics: `<T extends Serializable & Comparable<T>>`?

- [x] T phải implement cả Serializable và Comparable<T> đồng thời
- [ ] T implement Serializable hoặc Comparable
- [ ] Lỗi cú pháp
- [ ] T chỉ Serializable

> **Giải thích:** Intersection: ALL bounds must be satisfied. `&` = AND. Class bound first (if any), then interfaces. Common: `<T extends Object & Comparable<? super T>>`.

## Câu 96

[TYPE: SELECT_RESULT]

```java
record KeyValue<K, V>(K key, V value) {
    static <K, V> KeyValue<K, V> of(K key, V value) {
        return new KeyValue<>(key, value);
    }
}
var kv = KeyValue.of("name", 42);
System.out.println(kv.key() + "=" + kv.value());
System.out.println(kv.key().getClass().getSimpleName());
System.out.println(kv.value().getClass().getSimpleName());
```

- [x] name=42, String, Integer
- [ ] name=42, Object, Object
- [ ] Lỗi biên dịch
- [ ] null

> **Giải thích:** Generic record with factory method. Type inference: K=String, V=Integer. Runtime types preserved despite erasure.

## Câu 97

[TYPE: TRUE_FALSE]

Mệnh đề: "Java generics KHÔNG hỗ trợ primitive type specialization (ví dụ: List<int> thay vì List<Integer>)."

- [x] Đúng
- [ ] Sai

> **Giải thích:** No primitive generics. Must use wrappers. Performance: autoboxing overhead. Project Valhalla (future): may add primitive specialization. IntStream/LongStream: manual specializations.

## Câu 98

[TYPE: SELECT_RESULT]

```java
static <A, B, C> Function<A, C> compose(Function<A, B> f, Function<B, C> g) {
    return a -> g.apply(f.apply(a));
}
Function<String, Integer> length = String::length;
Function<Integer, String> toStr = i -> "Length: " + i;
Function<String, String> composed = compose(length, toStr);
System.out.println(composed.apply("Hello"));
```

- [x] Length: 5
- [ ] 5
- [ ] Hello
- [ ] Lỗi biên dịch

> **Giải thích:** Generic composition: A→B→C. String→Integer→String. "Hello"→5→"Length: 5". Three type parameters in single method.

## Câu 99

[TYPE: SELECT_RESULT]

```java
class Either<L, R> {
    private final L left;
    private final R right;
    private Either(L l, R r) { left = l; right = r; }
    static <L, R> Either<L, R> left(L value) { return new Either<>(value, null); }
    static <L, R> Either<L, R> right(R value) { return new Either<>(null, value); }
    boolean isRight() { return right != null; }
    L getLeft() { return left; }
    R getRight() { return right; }
}
Either<String, Integer> success = Either.right(42);
Either<String, Integer> failure = Either.left("Error");
System.out.println(success.isRight() + " " + success.getRight());
System.out.println(failure.isRight() + " " + failure.getLeft());
```

- [x] true 42 và false Error
- [ ] true null và false null
- [ ] Lỗi biên dịch
- [ ] false 42 và true Error

> **Giải thích:** Either monad with generics. L=error type, R=success type. right(42): success. left("Error"): failure. Functional error handling.

## Câu 100

[TYPE: MULTIPLE_CHOICE]

Best practices cho Generics?

- [x] Prefer generics over raw types, use bounded wildcards (PECS), favor generic methods, avoid unchecked casts
- [ ] Dùng raw types
- [ ] Avoid generics
- [ ] Cast mọi thứ

> **Giải thích:** Effective Java: use generics cho type safety. PECS cho flexibility. Bounded type parameters cho constraints. Avoid raw types. @SuppressWarnings("unchecked") chỉ khi chắc chắn safe.
