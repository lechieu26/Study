# Generic trong Java

## Mục lục

1. [Giới thiệu về Generic](#1-giới-thiệu-về-generic)
2. [Cú pháp và cách sử dụng cơ bản](#2-cú-pháp-và-cách-sử-dụng-cơ-bản)
3. [Generic Methods](#3-generic-methods)
4. [Type Parameters](#4-type-parameters)
5. [Bounded Type Parameters](#5-bounded-type-parameters)
6. [Generic và Inheritance](#6-generic-và-inheritance)
7. [Type Erasure](#7-type-erasure)
8. [Best Practices](#8-best-practices)

---

## 1. Giới thiệu về Generic

### 1.1. Khái niệm Generic

**Generic** (Java 5+) cho phép class, interface, method làm việc với **nhiều kiểu dữ liệu khác nhau** mà vẫn đảm bảo **type safety** tại compile time.

```java
// Không có Generic (Java < 5) - KHÔNG an toàn
List list = new ArrayList();
list.add("Hello");
list.add(123);           // Không báo lỗi compile time!
String s = (String) list.get(1); // ClassCastException tại runtime!

// Có Generic (Java 5+) - AN TOÀN
List<String> list = new ArrayList<>();
list.add("Hello");
// list.add(123);        // Compile error! Type safety
String s = list.get(0);  // Không cần cast
```

### 1.2. Lợi ích của Generic

| Lợi ích | Mô tả |
|---------|--------|
| **Type Safety** | Phát hiện lỗi kiểu tại compile time thay vì runtime |
| **Loại bỏ casting** | Không cần ép kiểu thủ công |
| **Code reuse** | Viết một lần, dùng cho nhiều kiểu |
| **Readability** | Code rõ ràng hơn về kiểu dữ liệu |
| **Performance** | Tránh boxing/unboxing không cần thiết |

```java
// Không generic - cần cast, có thể lỗi runtime
Object obj = list.get(0);
String str = (String) obj; // Nguy hiểm!

// Có generic - compiler đảm bảo type safe
String str = list.get(0); // An toàn, không cần cast
```

---

## 2. Cú pháp và cách sử dụng cơ bản

### 2.1. Khai báo Generic Class

```java
// Generic class với 1 type parameter
public class Box<T> {
    private T content;
    
    public Box() {}
    
    public Box(T content) {
        this.content = content;
    }
    
    public T getContent() {
        return content;
    }
    
    public void setContent(T content) {
        this.content = content;
    }
    
    @Override
    public String toString() {
        return "Box[" + content + "]";
    }
}
```

### 2.2. Sử dụng Generic Class

```java
public class GenericUsageDemo {
    public static void main(String[] args) {
        // Box chứa String
        Box<String> stringBox = new Box<>("Hello");
        String str = stringBox.getContent(); // Không cần cast
        System.out.println(str.toUpperCase()); // HELLO
        
        // Box chứa Integer
        Box<Integer> intBox = new Box<>(42);
        int num = intBox.getContent(); // Auto-unboxing
        System.out.println(num * 2); // 84
        
        // Box chứa custom object
        Box<List<String>> listBox = new Box<>(Arrays.asList("A", "B", "C"));
        List<String> items = listBox.getContent();
        System.out.println(items); // [A, B, C]
        
        // Diamond operator (Java 7+) - compiler suy luận type
        Box<Double> doubleBox = new Box<>(3.14); // type inference
    }
}
```

### 2.3. Multiple Type Parameters

```java
// Generic class với nhiều type parameters
public class Pair<K, V> {
    private K key;
    private V value;
    
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
    
    public K getKey() { return key; }
    public V getValue() { return value; }
    
    @Override
    public String toString() {
        return "(" + key + ", " + value + ")";
    }
}

// Triple - 3 type parameters
public class Triple<A, B, C> {
    private final A first;
    private final B second;
    private final C third;
    
    public Triple(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }
    
    public A getFirst() { return first; }
    public B getSecond() { return second; }
    public C getThird() { return third; }
}

// Sử dụng
public class MultiParamDemo {
    public static void main(String[] args) {
        Pair<String, Integer> nameAge = new Pair<>("Alice", 25);
        System.out.println(nameAge.getKey() + " is " + nameAge.getValue());
        
        Pair<Integer, List<String>> idNames = new Pair<>(1, Arrays.asList("A", "B"));
        
        Triple<String, Integer, Boolean> record = new Triple<>("Bob", 30, true);
        System.out.println(record.getFirst() + ", " + record.getSecond());
    }
}
```

---

## 3. Generic Methods

### 3.1. Khai báo Generic Method

Generic method có thể xuất hiện trong cả generic class lẫn non-generic class.

```java
public class GenericMethodDemo {
    
    // Generic method - type parameter khai báo trước return type
    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
    
    // Generic method trả về generic type
    public static <T> T getFirst(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    // Generic method với nhiều type parameters
    public static <K, V> Map<K, V> createMap(K key, V value) {
        Map<K, V> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
    
    // Generic method với bounded type
    public static <T extends Comparable<T>> T findMax(List<T> list) {
        if (list.isEmpty()) throw new IllegalArgumentException("Empty list");
        T max = list.get(0);
        for (T item : list) {
            if (item.compareTo(max) > 0) {
                max = item;
            }
        }
        return max;
    }
    
    public static void main(String[] args) {
        // Type inference - compiler suy luận type
        Integer[] intArr = {1, 2, 3, 4, 5};
        String[] strArr = {"Hello", "World"};
        
        printArray(intArr);  // <Integer> được suy luận
        printArray(strArr);  // <String> được suy luận
        
        // Explicit type specification (ít dùng)
        GenericMethodDemo.<Integer>printArray(intArr);
        
        // Sử dụng
        String first = getFirst(Arrays.asList("A", "B", "C"));
        System.out.println("First: " + first); // A
        
        Map<String, Integer> map = createMap("age", 25);
        System.out.println(map); // {age=25}
        
        int max = findMax(Arrays.asList(3, 1, 4, 1, 5, 9));
        System.out.println("Max: " + max); // 9
    }
}
```

### 3.2. Sử dụng Generic Method

```java
public class UtilityMethods {
    
    // Swap elements in array
    public static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    // Convert array to list
    public static <T> List<T> arrayToList(T[] array) {
        List<T> list = new ArrayList<>();
        Collections.addAll(list, array);
        return list;
    }
    
    // Filter list by predicate
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }
    
    // Transform list
    public static <T, R> List<R> transform(List<T> list, Function<T, R> mapper) {
        List<R> result = new ArrayList<>();
        for (T item : list) {
            result.add(mapper.apply(item));
        }
        return result;
    }
    
    public static void main(String[] args) {
        // swap
        String[] arr = {"A", "B", "C"};
        swap(arr, 0, 2);
        System.out.println(Arrays.toString(arr)); // [C, B, A]
        
        // filter
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> evens = filter(numbers, n -> n % 2 == 0);
        System.out.println(evens); // [2, 4, 6]
        
        // transform
        List<String> names = Arrays.asList("alice", "bob");
        List<String> upper = transform(names, String::toUpperCase);
        System.out.println(upper); // [ALICE, BOB]
        
        List<Integer> lengths = transform(names, String::length);
        System.out.println(lengths); // [5, 3]
    }
}
```

---

## 4. Type Parameters

### 4.1. T - Type

Tham số kiểu chung nhất, đại diện cho bất kỳ kiểu nào.

```java
public class Container<T> {
    private T value;
    public T get() { return value; }
    public void set(T value) { this.value = value; }
}
```

### 4.2. E - Element

Thường dùng cho **phần tử** trong collections.

```java
public interface CustomList<E> {
    void add(E element);
    E get(int index);
    int size();
}

public class SimpleList<E> implements CustomList<E> {
    private Object[] elements = new Object[10];
    private int size = 0;
    
    @Override
    public void add(E element) {
        elements[size++] = element;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        return (E) elements[index];
    }
    
    @Override
    public int size() { return size; }
}
```

### 4.3. K - Key

Thường dùng cho **khóa** (key) trong Map.

```java
public interface Cache<K, V> {
    V get(K key);
    void put(K key, V value);
    void remove(K key);
}
```

### 4.4. V - Value

Thường dùng cho **giá trị** (value) trong Map.

```java
public class SimpleCache<K, V> implements Cache<K, V> {
    private final Map<K, V> store = new HashMap<>();
    
    @Override
    public V get(K key) { return store.get(key); }
    
    @Override
    public void put(K key, V value) { store.put(key, value); }
    
    @Override
    public void remove(K key) { store.remove(key); }
}
```

### 4.5. N - Number

Thường dùng khi type bị giới hạn là **số**.

```java
public class MathBox<N extends Number> {
    private N value;
    
    public MathBox(N value) { this.value = value; }
    
    public double doubleValue() { return value.doubleValue(); }
    public int intValue() { return value.intValue(); }
    
    public boolean isGreaterThan(MathBox<? extends Number> other) {
        return this.doubleValue() > other.doubleValue();
    }
}

// Sử dụng
MathBox<Integer> intBox = new MathBox<>(10);
MathBox<Double> doubleBox = new MathBox<>(3.14);
System.out.println(intBox.isGreaterThan(doubleBox)); // true
```

### 4.6. ? - Wildcard

**Wildcard** (`?`) đại diện cho kiểu **không xác định** (unknown type).

```java
public class WildcardDemo {
    // Unbounded wildcard: ? - bất kỳ type nào
    public static void printList(List<?> list) {
        for (Object item : list) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        List<Integer> ints = Arrays.asList(1, 2, 3);
        List<String> strs = Arrays.asList("A", "B", "C");
        
        printList(ints);  // OK
        printList(strs);  // OK
        
        // List<?> chỉ có thể đọc (Object), KHÔNG thể add
        List<?> unknown = new ArrayList<>(ints);
        Object obj = unknown.get(0);  // OK - đọc được
        // unknown.add(4);            // Compile error! Không biết type để add
        unknown.add(null);            // Chỉ null được add
    }
}
```

---

## 5. Bounded Type Parameters

### 5.1. Upper Bounded Wildcards

**`<? extends Type>`** - chấp nhận Type và **các class con** của Type. Chỉ đọc (producer).

```java
public class UpperBoundDemo {
    // Chấp nhận List<Number>, List<Integer>, List<Double>, etc.
    public static double sum(List<? extends Number> list) {
        double total = 0;
        for (Number num : list) {
            total += num.doubleValue();
        }
        return total;
    }
    
    // Upper bound trên type parameter
    public static <T extends Comparable<T>> T findMin(List<T> list) {
        T min = list.get(0);
        for (T item : list) {
            if (item.compareTo(min) < 0) {
                min = item;
            }
        }
        return min;
    }
    
    // Multiple bounds: T phải extend A VÀ implement B, C
    public static <T extends Number & Comparable<T>> T findMax(List<T> list) {
        return Collections.max(list);
    }
    
    public static void main(String[] args) {
        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5);
        List<Double> doubles = Arrays.asList(1.1, 2.2, 3.3);
        
        System.out.println("Sum ints: " + sum(ints));       // 15.0
        System.out.println("Sum doubles: " + sum(doubles)); // 6.6
        
        System.out.println("Min: " + findMin(ints));    // 1
        System.out.println("Max: " + findMax(ints));    // 5
        
        // KHÔNG thể add vào ? extends
        List<? extends Number> numbers = ints;
        // numbers.add(10);  // Compile error!
        Number n = numbers.get(0); // OK - đọc được
    }
}
```

### 5.2. Lower Bounded Wildcards

**`<? super Type>`** - chấp nhận Type và **các class cha** của Type. Chỉ ghi (consumer).

```java
public class LowerBoundDemo {
    // Chấp nhận List<Integer>, List<Number>, List<Object>
    public static void addNumbers(List<? super Integer> list) {
        list.add(1);
        list.add(2);
        list.add(3);
        // Có thể add Integer vào (Integer IS-A Number IS-A Object)
    }
    
    // PECS: Producer Extends, Consumer Super
    public static <T> void copy(List<? extends T> src, List<? super T> dest) {
        for (T item : src) {
            dest.add(item);
        }
    }
    
    public static void main(String[] args) {
        List<Number> numberList = new ArrayList<>();
        addNumbers(numberList); // OK - Number super Integer
        System.out.println(numberList); // [1, 2, 3]
        
        List<Object> objectList = new ArrayList<>();
        addNumbers(objectList); // OK - Object super Integer
        
        // List<Double> doubleList = new ArrayList<>();
        // addNumbers(doubleList); // Compile error! Double không super Integer
        
        // Copy demo (PECS pattern)
        List<Integer> source = Arrays.asList(1, 2, 3);
        List<Number> destination = new ArrayList<>();
        copy(source, destination);
        System.out.println(destination); // [1, 2, 3]
    }
}
```

**PECS Rule (Producer Extends, Consumer Super):**

```java
// Producer - cung cấp dữ liệu → extends (đọc)
public static double sum(List<? extends Number> producer) {
    double total = 0;
    for (Number n : producer) { // READ from producer
        total += n.doubleValue();
    }
    return total;
}

// Consumer - nhận dữ liệu → super (ghi)
public static void fill(List<? super Integer> consumer, int value) {
    consumer.add(value); // WRITE to consumer
}

// Cả hai
public static <T> void copy(
    List<? extends T> src,    // Producer: đọc từ src
    List<? super T> dest      // Consumer: ghi vào dest
) {
    for (T item : src) {
        dest.add(item);
    }
}
```

---

## 6. Generic và Inheritance

### 6.1. Generic Class Inheritance

```java
// Base generic class
public class Container<T> {
    protected T value;
    
    public Container(T value) { this.value = value; }
    public T getValue() { return value; }
}

// Kế thừa giữ nguyên type parameter
public class NamedContainer<T> extends Container<T> {
    private String name;
    
    public NamedContainer(String name, T value) {
        super(value);
        this.name = name;
    }
    
    public String getName() { return name; }
}

// Kế thừa với type cụ thể
public class StringContainer extends Container<String> {
    public StringContainer(String value) {
        super(value);
    }
    
    public int length() { return value.length(); }
}

// Kế thừa thêm type parameter mới
public class PairContainer<T, U> extends Container<T> {
    private U extra;
    
    public PairContainer(T value, U extra) {
        super(value);
        this.extra = extra;
    }
}

// LƯU Ý: Generic types KHÔNG có subtype relationship!
// List<Integer> IS NOT a subtype of List<Number>
public class InheritanceDemo {
    public static void main(String[] args) {
        // Object là cha của String
        Object obj = "Hello"; // OK: String IS-A Object
        
        // NHƯNG List<Object> KHÔNG phải cha của List<String>
        // List<Object> list = new ArrayList<String>(); // Compile error!
        
        // Phải dùng wildcard
        List<? extends Object> list = new ArrayList<String>(); // OK
        
        // Generic class inheritance vẫn hoạt động bình thường
        NamedContainer<Integer> named = new NamedContainer<>("count", 42);
        Container<Integer> base = named; // OK: NamedContainer IS-A Container
    }
}
```

### 6.2. Generic Method Inheritance

```java
interface Transformer<T, R> {
    R transform(T input);
}

// Implement generic interface
class StringToIntTransformer implements Transformer<String, Integer> {
    @Override
    public Integer transform(String input) {
        return input.length();
    }
}

// Implement giữ generic
class IdentityTransformer<T> implements Transformer<T, T> {
    @Override
    public T transform(T input) {
        return input;
    }
}

// Override generic method
abstract class AbstractProcessor<T> {
    public abstract T process(T input);
    
    public List<T> processAll(List<T> inputs) {
        List<T> results = new ArrayList<>();
        for (T input : inputs) {
            results.add(process(input));
        }
        return results;
    }
}

class UpperCaseProcessor extends AbstractProcessor<String> {
    @Override
    public String process(String input) {
        return input.toUpperCase();
    }
}
```

---

## 7. Type Erasure

**Type Erasure** là cơ chế compiler xóa thông tin generic tại compile time. Bytecode không chứa generic types.

```java
// Source code
public class Box<T> {
    private T content;
    public T get() { return content; }
    public void set(T content) { this.content = content; }
}

// Sau type erasure (bytecode thực tế)
public class Box {
    private Object content;  // T → Object
    public Object get() { return content; }
    public void set(Object content) { this.content = content; }
}

// Bounded type erasure
public class NumberBox<T extends Number> {
    private T value;
}
// Sau erasure: T → Number (upper bound)
public class NumberBox {
    private Number value;
}
```

### Bridge Methods

Compiler tạo **bridge methods** để đảm bảo polymorphism hoạt động đúng sau type erasure.

```java
interface Comparable<T> {
    int compareTo(T o);
}

class MyString implements Comparable<MyString> {
    @Override
    public int compareTo(MyString o) { // method chính
        return 0;
    }
    
    // Compiler tự sinh bridge method:
    // public int compareTo(Object o) {   // bridge
    //     return compareTo((MyString) o);
    // }
}
```

### Hạn chế của Type Erasure

```java
public class TypeErasureLimitations {
    public static void main(String[] args) {
        // 1. Không thể tạo instance của type parameter
        // T obj = new T(); // Compile error!
        
        // 2. Không thể tạo array của generic type
        // T[] arr = new T[10]; // Compile error!
        // List<String>[] arr = new List<String>[10]; // Compile error!
        
        // 3. Không thể dùng instanceof với generic type
        List<String> list = new ArrayList<>();
        // if (list instanceof List<String>) {} // Compile error!
        if (list instanceof List<?>) {} // OK - unbounded wildcard
        
        // 4. Không thể overload dựa trên generic type
        // void process(List<String> list) {}
        // void process(List<Integer> list) {} // Compile error! Same erasure
        
        // 5. Không thể catch/throw generic exception type
        // class GenericException<T> extends Exception {} // Compile error!
        
        // 6. Static field không thể dùng class type parameter
        // class Container<T> {
        //     static T field; // Compile error!
        // }
        
        // Workaround cho hạn chế 1: Class token
        // public <T> T create(Class<T> clazz) throws Exception {
        //     return clazz.getDeclaredConstructor().newInstance();
        // }
    }
}
```

---

## 8. Best Practices

### 1. Luôn sử dụng generic thay vì raw types

```java
// ❌ Raw type - KHÔNG an toàn
List list = new ArrayList();
list.add("Hello");
list.add(123); // Không báo lỗi!
String s = (String) list.get(1); // Runtime ClassCastException!

// ✅ Generic - type safe
List<String> list = new ArrayList<>();
list.add("Hello");
// list.add(123); // Compile error!
String s = list.get(0); // No cast needed
```

### 2. Sử dụng PECS (Producer Extends, Consumer Super)

```java
// ✅ PECS principle
public static <T> void copy(List<? extends T> src, List<? super T> dest) {
    for (T item : src) {
        dest.add(item);
    }
}

// ✅ Readable API
public static double sum(List<? extends Number> numbers) { ... }
public static void addIntegers(List<? super Integer> target) { ... }
```

### 3. Ưu tiên generic methods hơn wildcard khi type liên quan

```java
// ❌ Wildcard - không thể liên kết types
public static void swap(List<?> list, int i, int j) {
    // list.set(i, list.get(j)); // Compile error! Không biết type
}

// ✅ Generic method - type T liên kết input/output
public static <T> void swap(List<T> list, int i, int j) {
    T temp = list.get(i);
    list.set(i, list.get(j));
    list.set(j, temp);
}
```

### 4. Tránh raw type warnings

```java
// ❌ Raw types - warnings
Map map = new HashMap();
List list = new ArrayList();

// ✅ Diamond operator
Map<String, Integer> map = new HashMap<>();
List<String> list = new ArrayList<>();

// Nếu thực sự cần unknown type
List<?> unknown = getList();
Map<?, ?> unknownMap = getMap();
```

### 5. Type token pattern cho runtime type info

```java
// Vì type erasure xóa type info, dùng Class<T> để giữ lại
public class TypeSafeContainer {
    private final Map<Class<?>, Object> map = new HashMap<>();
    
    public <T> void put(Class<T> type, T instance) {
        map.put(type, instance);
    }
    
    public <T> T get(Class<T> type) {
        return type.cast(map.get(type));
    }
}

// Sử dụng
TypeSafeContainer container = new TypeSafeContainer();
container.put(String.class, "Hello");
container.put(Integer.class, 42);

String str = container.get(String.class);   // "Hello" - type safe
Integer num = container.get(Integer.class); // 42 - type safe
```

### 6. Không dùng generic type cho static members

```java
public class GenericClass<T> {
    // ❌ Không thể dùng T cho static
    // private static T instance; // Compile error
    // public static T create() { } // Compile error
    
    // ✅ Static method riêng type parameter
    public static <E> E getDefault(Class<E> clazz) throws Exception {
        return clazz.getDeclaredConstructor().newInstance();
    }
}
```

### 7. Sử dụng @SuppressWarnings("unchecked") có kiểm soát

```java
// ✅ Suppress ở phạm vi nhỏ nhất + comment giải thích
public <T> T[] toArray(List<T> list, Class<T> type) {
    @SuppressWarnings("unchecked") // Safe: Array.newInstance trả về đúng type
    T[] array = (T[]) Array.newInstance(type, list.size());
    return list.toArray(array);
}

// ❌ Suppress ở phạm vi lớn
@SuppressWarnings("unchecked") // Tệ - ẩn TẤT CẢ warnings!
public class BadExample { ... }
```

### Tổng hợp: Khi nào dùng gì?

| Tình huống | Cú pháp |
|-----------|---------|
| Class/interface làm việc với nhiều types | `class Box<T>` |
| Method độc lập với type | `<T> T method(T param)` |
| Đọc data (Producer) | `<? extends T>` |
| Ghi data (Consumer) | `<? super T>` |
| Không biết type cụ thể | `<?>` |
| Type phải là subtype | `<T extends Base>` |
| Multiple bounds | `<T extends A & B & C>` |
| Type token (runtime) | `Class<T>` parameter |

---

> **Tóm tắt:** Generic là tính năng quan trọng giúp code Java an toàn về type, tái sử dụng được, và dễ đọc. Hiểu rõ type erasure, PECS principle, và các hạn chế giúp sử dụng Generic hiệu quả trong mọi tình huống.
