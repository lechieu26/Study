# Quiz - Reflection

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Reflection trong Java là gì?

- [x] API cho phép inspect và modify classes, methods, fields tại runtime
- [ ] Design pattern
- [ ] Compile-time feature
- [ ] Chỉ cho testing

> **Giải thích:** Reflection (java.lang.reflect): runtime introspection. Xem class structure, gọi methods, access fields, tạo instances. Dùng trong frameworks (Spring, Hibernate, JUnit).

## Câu 2

[TYPE: SELECT_RESULT]

```java
Class<?> clazz = String.class;
System.out.println(clazz.getName());
System.out.println(clazz.getSimpleName());
```

- [x] java.lang.String và String
- [ ] String và java.lang.String
- [ ] String và String
- [ ] Lỗi biên dịch

> **Giải thích:** getName(): fully qualified name. getSimpleName(): class name without package. Class<?> object chứa metadata của class.

## Câu 3

[TYPE: FILL_BLANK]

Có 3 cách lấy Class object: `ClassName.class`, `object.getClass()`, và `Class.___("fullName")`.

- [x] forName
- [ ] get
- [ ] load
- [ ] find

> **Giải thích:** Class.forName("java.lang.String"): load class by name. Throws ClassNotFoundException nếu không tìm thấy. Dùng cho dynamic class loading.

## Câu 4

[TYPE: SELECT_RESULT]

```java
class Person {
    private String name;
    public int age;
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
Field[] publicFields = Person.class.getFields();
Field[] allFields = Person.class.getDeclaredFields();
System.out.println(publicFields.length + " " + allFields.length);
```

- [x] 1 2
- [ ] 2 2
- [ ] 0 2
- [ ] 2 0

> **Giải thích:** getFields(): chỉ public fields (age). getDeclaredFields(): tất cả fields kể cả private (name, age). getFields() bao gồm inherited; getDeclaredFields() chỉ class hiện tại.

## Câu 5

[TYPE: TRUE_FALSE]

Mệnh đề: "Reflection có thể truy cập private fields và methods bằng setAccessible(true)."

- [x] Đúng
- [ ] Sai

> **Giải thích:** setAccessible(true): bỏ qua access control. Dangerous nhưng cần cho frameworks. Java 9+ module system có thể restrict. SecurityManager có thể block.

## Câu 6

[TYPE: SELECT_RESULT]

```java
class Secret {
    private String password = "hidden";
}
Secret obj = new Secret();
Field field = Secret.class.getDeclaredField("password");
field.setAccessible(true);
System.out.println(field.get(obj));
field.set(obj, "newPassword");
System.out.println(field.get(obj));
```

- [x] hidden và newPassword
- [ ] IllegalAccessException
- [ ] null và newPassword
- [ ] Lỗi biên dịch

> **Giải thích:** setAccessible(true) → bypass private. field.get(obj) → "hidden". field.set(obj, "newPassword") → modify. field.get(obj) → "newPassword".

## Câu 7

[TYPE: SELECT_RESULT]

```java
Method[] methods = String.class.getMethods();
System.out.println(methods.length > 50);

Method method = String.class.getMethod("substring", int.class, int.class);
String result = (String) method.invoke("Hello World", 0, 5);
System.out.println(result);
```

- [x] true và Hello
- [ ] false và Hello
- [ ] true và Hello World
- [ ] Lỗi NoSuchMethodException

> **Giải thích:** getMethods(): tất cả public methods (including inherited). String có > 50 methods. getMethod + invoke: gọi method via reflection. substring(0,5) → "Hello".

## Câu 8

[TYPE: MULTIPLE_CHOICE]

getMethod() vs getDeclaredMethod()?

- [x] getMethod: public methods (including inherited); getDeclaredMethod: all methods in this class (including private)
- [ ] Giống nhau
- [ ] getMethod cho private
- [ ] getDeclaredMethod cho inherited

> **Giải thích:** getMethod("name", paramTypes): chỉ public, bao gồm inherited. getDeclaredMethod: tất cả access levels, chỉ class hiện tại. Tương tự cho Fields, Constructors.

## Câu 9

[TYPE: SELECT_RESULT]

```java
class Calculator {
    public int add(int a, int b) { return a + b; }
    private int multiply(int a, int b) { return a * b; }
}
Calculator calc = new Calculator();
Method method = Calculator.class.getDeclaredMethod("multiply", int.class, int.class);
method.setAccessible(true);
int result = (int) method.invoke(calc, 3, 4);
System.out.println(result);
```

- [x] 12
- [ ] IllegalAccessException
- [ ] 7
- [ ] NoSuchMethodException

> **Giải thích:** getDeclaredMethod("multiply"): tìm private method. setAccessible(true): bypass access check. invoke(calc, 3, 4) → 3*4 = 12.

## Câu 10

[TYPE: FILL_BLANK]

`Constructor.___()` tạo new instance qua reflection constructor.

- [x] newInstance
- [ ] create
- [ ] build
- [ ] instantiate

> **Giải thích:** Constructor.newInstance(args...): tạo object mới. Class.getDeclaredConstructor(paramTypes).newInstance(args). Throws InstantiationException, IllegalAccessException.

## Câu 11

[TYPE: SELECT_RESULT]

```java
class Animal {
    private String name;
    private Animal() { this.name = "Unknown"; }
    public String getName() { return name; }
}
Constructor<Animal> ctor = Animal.class.getDeclaredConstructor();
ctor.setAccessible(true);
Animal animal = ctor.newInstance();
System.out.println(animal.getName());
```

- [x] Unknown
- [ ] null
- [ ] IllegalAccessException
- [ ] InstantiationException

> **Giải thích:** Private constructor accessed via reflection. setAccessible(true) bypass. newInstance() call constructor → name="Unknown".

## Câu 12

[TYPE: TRUE_FALSE]

Mệnh đề: "Reflection chậm hơn direct method calls do overhead của access checks và JIT optimization bypass."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Reflection: access check, method resolution, boxing/unboxing, no JIT inline optimization. 10-100x chậm hơn. Cache Method/Field objects để giảm overhead.

## Câu 13

[TYPE: SELECT_RESULT]

```java
class Service {
    @Deprecated
    public void oldMethod() {}

    public void newMethod() {}
}
Method[] methods = Service.class.getDeclaredMethods();
for (Method m : methods) {
    if (m.isAnnotationPresent(Deprecated.class)) {
        System.out.println(m.getName() + " is deprecated");
    }
}
```

- [x] oldMethod is deprecated
- [ ] Không in gì
- [ ] newMethod is deprecated
- [ ] oldMethod is deprecated và newMethod is deprecated

> **Giải thích:** isAnnotationPresent(Deprecated.class): check annotation. oldMethod có @Deprecated → true. newMethod không có → false.

## Câu 14

[TYPE: SELECT_RESULT]

```java
Class<?> clazz = ArrayList.class;
System.out.println(clazz.getSuperclass().getSimpleName());
Class<?>[] interfaces = clazz.getInterfaces();
boolean implementsList = false;
for (Class<?> i : interfaces) {
    if (i == List.class) implementsList = true;
}
System.out.println(implementsList);
```

- [x] AbstractList và true
- [ ] Object và false
- [ ] AbstractCollection và true
- [ ] ArrayList và true

> **Giải thích:** ArrayList extends AbstractList. getSuperclass() → AbstractList. getInterfaces(): trực tiếp implemented. ArrayList implements List → true.

## Câu 15

[TYPE: MULTIPLE_CHOICE]

Annotation Retention policies?

- [x] SOURCE (compile-time only), CLASS (in bytecode, not runtime), RUNTIME (available via reflection)
- [ ] Chỉ RUNTIME
- [ ] SOURCE và RUNTIME
- [ ] CLASS only

> **Giải thích:** @Retention(RetentionPolicy.RUNTIME): available via reflection. SOURCE: compiler chỉ (Override, SuppressWarnings). CLASS: default, in .class file nhưng not reflection.

## Câu 16

[TYPE: SELECT_RESULT]

```java
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface TestCase {
    String value();
    int priority() default 0;
}

class Tests {
    @TestCase(value = "login test", priority = 1)
    public void testLogin() {}

    @TestCase("signup test")
    public void testSignup() {}
}

Method m = Tests.class.getMethod("testLogin");
TestCase tc = m.getAnnotation(TestCase.class);
System.out.println(tc.value() + " P" + tc.priority());
```

- [x] login test P1
- [ ] login test P0
- [ ] null
- [ ] Lỗi biên dịch

> **Giải thích:** @TestCase(value="login test", priority=1). getAnnotation: retrieve annotation. value()="login test", priority()=1.

## Câu 17

[TYPE: FILL_BLANK]

`Method.getReturnType()` trả về `___` object đại diện cho return type.

- [x] Class
- [ ] Type
- [ ] String
- [ ] Object

> **Giải thích:** getReturnType(): Class<?> of return type. getParameterTypes(): Class<?>[] of params. getModifiers(): int (Modifier.isPublic, isStatic, etc).

## Câu 18

[TYPE: SELECT_RESULT]

```java
class Demo {
    public static String greet(String name) { return "Hi " + name; }
}
Method method = Demo.class.getMethod("greet", String.class);
System.out.println(Modifier.isStatic(method.getModifiers()));
System.out.println(method.getReturnType().getSimpleName());
System.out.println(method.getParameterCount());
```

- [x] true, String, 1
- [ ] false, String, 1
- [ ] true, void, 0
- [ ] true, String, 0

> **Giải thích:** Modifier.isStatic: true (static method). returnType: String. parameterCount: 1 (String name).

## Câu 19

[TYPE: SELECT_RESULT]

```java
Object obj = "Hello";
Class<?> clazz = obj.getClass();
System.out.println(clazz == String.class);
System.out.println(clazz.isInstance("World"));
System.out.println(clazz.isInstance(42));
```

- [x] true, true, false
- [ ] true, true, true
- [ ] false, true, false
- [ ] true, false, false

> **Giải thích:** getClass() == String.class: true (same Class object). isInstance("World"): String instance ✓. isInstance(42): Integer, not String ✗.

## Câu 20

[TYPE: TRUE_FALSE]

Mệnh đề: "Proxy.newProxyInstance() tạo dynamic proxy implementing interfaces tại runtime."

- [x] Đúng
- [ ] Sai

> **Giải thích:** java.lang.reflect.Proxy: tạo proxy object implement 1+ interfaces. InvocationHandler xử lý method calls. Dùng trong AOP, RPC, lazy loading.

## Câu 21

[TYPE: SELECT_RESULT]

```java
interface Greeter { String greet(String name); }

Greeter proxy = (Greeter) Proxy.newProxyInstance(
    Greeter.class.getClassLoader(),
    new Class[]{Greeter.class},
    (proxyObj, method, args) -> {
        if (method.getName().equals("greet")) {
            return "Hello, " + args[0] + "!";
        }
        return null;
    }
);
System.out.println(proxy.greet("An"));
```

- [x] Hello, An!
- [ ] null
- [ ] Lỗi runtime
- [ ] Lỗi biên dịch

> **Giải thích:** Dynamic proxy: implement Greeter interface. InvocationHandler: intercept greet() → "Hello, An!". No concrete class needed.

## Câu 22

[TYPE: SELECT_RESULT]

```java
Class<?> intClass = int.class;
Class<?> integerClass = Integer.class;
System.out.println(intClass == integerClass);
System.out.println(intClass.isPrimitive());
System.out.println(integerClass.isPrimitive());
```

- [x] false, true, false
- [ ] true, true, true
- [ ] false, false, false
- [ ] true, false, false

> **Giải thích:** int.class ≠ Integer.class: different Class objects. int.class.isPrimitive()=true. Integer.class.isPrimitive()=false (wrapper class).

## Câu 23

[TYPE: MULTIPLE_CHOICE]

Reflection use cases trong frameworks?

- [x] Dependency injection (Spring), ORM mapping (Hibernate), testing (JUnit), serialization
- [ ] Chỉ testing
- [ ] Chỉ Spring
- [ ] Không dùng trong production

> **Giải thích:** Spring: @Autowired injection. Hibernate: entity → table mapping. JUnit: @Test discovery. Jackson: JSON serialization. Annotation processing.

## Câu 24

[TYPE: SELECT_RESULT]

```java
class Container<T> {
    private T value;
    Container(T v) { this.value = v; }
}
Container<String> c = new Container<>("Hello");
Field field = Container.class.getDeclaredField("value");
field.setAccessible(true);
Type genericType = field.getGenericType();
System.out.println(field.getType().getSimpleName());
System.out.println(genericType instanceof TypeVariable);
```

- [x] Object và true
- [ ] String và false
- [ ] Object và false
- [ ] T và true

> **Giải thích:** Type erasure: T → Object at runtime. getType()=Object. getGenericType()=TypeVariable (T). Runtime: generic info partially available through reflection.

## Câu 25

[TYPE: FILL_BLANK]

`Class.isAssignableFrom(other)` kiểm tra `___` relationship giữa classes.

- [x] is-a (superclass/interface)
- [ ] has-a
- [ ] equals
- [ ] contains

> **Giải thích:** Number.class.isAssignableFrom(Integer.class) → true (Integer IS-A Number). Kiểm tra class/interface hierarchy. Useful cho type checking at runtime.

## Câu 26

[TYPE: SELECT_RESULT]

```java
class Base { }
class Child extends Base { }
class GrandChild extends Child { }

System.out.println(Base.class.isAssignableFrom(GrandChild.class));
System.out.println(GrandChild.class.isAssignableFrom(Base.class));
System.out.println(Base.class.isAssignableFrom(String.class));
```

- [x] true, false, false
- [ ] true, true, true
- [ ] false, true, false
- [ ] true, false, true

> **Giải thích:** Base.isAssignableFrom(GrandChild): GrandChild IS-A Base ✓. GrandChild.isAssignableFrom(Base): Base không IS-A GrandChild ✗. String not related ✗.

## Câu 27

[TYPE: SELECT_RESULT]

```java
int[] arr = {1, 2, 3};
Class<?> clazz = arr.getClass();
System.out.println(clazz.isArray());
System.out.println(clazz.getComponentType().getSimpleName());
System.out.println(Array.getLength(arr));
System.out.println(Array.getInt(arr, 1));
```

- [x] true, int, 3, 2
- [ ] true, Integer, 3, 2
- [ ] false, int, 3, 2
- [ ] Lỗi biên dịch

> **Giải thích:** isArray(): true for array types. getComponentType(): element type (int). Array.getLength: array length via reflection. Array.getInt: element at index.

## Câu 28

[TYPE: MULTIPLE_CHOICE]

Nhược điểm của Reflection?

- [x] Performance overhead, breaks encapsulation, no compile-time safety, security restrictions
- [ ] Không có nhược điểm
- [ ] Chỉ chậm
- [ ] Chỉ security

> **Giải thích:** Performance: slower than direct calls. Security: bypasses access control. Type safety: no compile-time checks → runtime errors. Maintenance: brittle (string-based).

## Câu 29

[TYPE: SELECT_RESULT]

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column { String name(); }

class User {
    @Column(name = "user_name") private String name;
    @Column(name = "user_email") private String email;
    private int age;
}

int annotatedFields = 0;
for (Field f : User.class.getDeclaredFields()) {
    if (f.isAnnotationPresent(Column.class)) {
        annotatedFields++;
    }
}
System.out.println(annotatedFields);
```

- [x] 2
- [ ] 3
- [ ] 1
- [ ] 0

> **Giải thích:** name có @Column ✓, email có @Column ✓, age không có @Column ✗. annotatedFields = 2.

## Câu 30

[TYPE: TRUE_FALSE]

Mệnh đề: "Java 9 module system có thể restrict reflection access bằng --add-opens."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Module system: packages phải open cho reflection. --add-opens module/package=target. InaccessibleObjectException nếu không open. Security improvement.

## Câu 31

[TYPE: SELECT_RESULT]

```java
enum Color { RED, GREEN, BLUE }

Class<Color> clazz = Color.class;
System.out.println(clazz.isEnum());
Color[] values = clazz.getEnumConstants();
System.out.println(values.length);
System.out.println(values[1]);
```

- [x] true, 3, GREEN
- [ ] true, 3, RED
- [ ] false, 3, GREEN
- [ ] true, 4, GREEN

> **Giải thích:** isEnum(): true for enum types. getEnumConstants(): array of enum values in declaration order. [RED, GREEN, BLUE]. values[1]=GREEN.

## Câu 32

[TYPE: SELECT_RESULT]

```java
class Utility {
    public void process() {}
    public void process(String s) {}
    public void process(String s, int n) {}
}
Method[] methods = Utility.class.getDeclaredMethods();
int processCount = 0;
for (Method m : methods) {
    if (m.getName().equals("process")) processCount++;
}
System.out.println(processCount);
```

- [x] 3
- [ ] 1
- [ ] 2
- [ ] 0

> **Giải thích:** 3 overloaded methods named "process". getDeclaredMethods returns all. Filter by name → 3.

## Câu 33

[TYPE: FILL_BLANK]

`Modifier.isPublic(modifiers)` kiểm tra method/field có modifier `___`.

- [x] public
- [ ] private
- [ ] static
- [ ] final

> **Giải thích:** Modifier: isPublic, isPrivate, isProtected, isStatic, isFinal, isAbstract, isSynchronized, isTransient, isVolatile. getModifiers() → int bitmask.

## Câu 34

[TYPE: SELECT_RESULT]

```java
class Config {
    public static final String VERSION = "1.0";
    private static int counter = 0;
}
Field versionField = Config.class.getDeclaredField("VERSION");
Field counterField = Config.class.getDeclaredField("counter");
int vMods = versionField.getModifiers();
int cMods = counterField.getModifiers();
System.out.println(Modifier.isStatic(vMods) && Modifier.isFinal(vMods));
System.out.println(Modifier.isPrivate(cMods) && Modifier.isStatic(cMods));
```

- [x] true và true
- [ ] true và false
- [ ] false và true
- [ ] false và false

> **Giải thích:** VERSION: public static final → isStatic=true, isFinal=true. counter: private static → isPrivate=true, isStatic=true.

## Câu 35

[TYPE: MULTIPLE_CHOICE]

MethodHandle (java.lang.invoke) vs Reflection?

- [x] MethodHandle: faster (JIT optimized), type-safe, no access check per call; Reflection: more flexible, slower
- [ ] Giống nhau
- [ ] Reflection nhanh hơn
- [ ] MethodHandle deprecated

> **Giải thích:** MethodHandle: access check 1 lần (at lookup), JIT can inline. Reflection: access check mỗi lần invoke. MethodHandle: Java 7+, lower-level API.

## Câu 36

[TYPE: SELECT_RESULT]

```java
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Roles.class)
@interface Role { String value(); }

@Retention(RetentionPolicy.RUNTIME)
@interface Roles { Role[] value(); }

@Role("ADMIN")
@Role("USER")
class AdminUser {}

Role[] roles = AdminUser.class.getAnnotationsByType(Role.class);
System.out.println(roles.length);
for (Role r : roles) System.out.print(r.value() + " ");
```

- [x] 2 và ADMIN USER
- [ ] 1 và ADMIN
- [ ] 0
- [ ] Lỗi biên dịch

> **Giải thích:** @Repeatable: multiple annotations same type. getAnnotationsByType: return array. 2 roles: ADMIN, USER.

## Câu 37

[TYPE: SELECT_RESULT]

```java
interface Printable {
    default void print() { System.out.println("Printable"); }
}
class Document implements Printable {}

Method method = Printable.class.getMethod("print");
System.out.println(method.isDefault());

Method[] methods = Document.class.getMethods();
boolean found = false;
for (Method m : methods) {
    if (m.getName().equals("print")) found = true;
}
System.out.println(found);
```

- [x] true và true
- [ ] false và true
- [ ] true và false
- [ ] false và false

> **Giải thích:** isDefault(): true for interface default methods. getMethods on Document: includes inherited default method print().

## Câu 38

[TYPE: TRUE_FALSE]

Mệnh đề: "Class.newInstance() deprecated từ Java 9, nên dùng Constructor.newInstance() thay thế."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Class.newInstance(): wraps exceptions, chỉ no-arg constructor. Constructor.newInstance(): proper exception handling, any constructor, setAccessible support.

## Câu 39

[TYPE: SELECT_RESULT]

```java
class Builder {
    private String name = "";
    public Builder setName(String n) { this.name = n; return this; }
    public String build() { return "Built: " + name; }
}

// Using reflection to chain methods
Object obj = Builder.class.getDeclaredConstructor().newInstance();
Method setName = Builder.class.getMethod("setName", String.class);
Object result = setName.invoke(obj, "Test");
Method build = Builder.class.getMethod("build");
System.out.println(build.invoke(result));
```

- [x] Built: Test
- [ ] Built:
- [ ] null
- [ ] Lỗi runtime

> **Giải thích:** newInstance → Builder. setName.invoke → returns Builder (method chaining). build.invoke → "Built: Test". Reflection-based method chaining.

## Câu 40

[TYPE: SELECT_RESULT]

```java
Field[] fields = Integer.class.getDeclaredFields();
int staticFields = 0;
for (Field f : fields) {
    if (Modifier.isStatic(f.getModifiers())) staticFields++;
}
System.out.println(staticFields > 0);
System.out.println(Integer.class.getSuperclass().getSimpleName());
```

- [x] true và Number
- [ ] false và Object
- [ ] true và Object
- [ ] false và Number

> **Giải thích:** Integer has static fields (MIN_VALUE, MAX_VALUE, etc). staticFields > 0 → true. Integer extends Number → getSuperclass() = Number.

## Câu 41

[TYPE: MULTIPLE_CHOICE]

VarHandle (Java 9) dùng cho gì?

- [x] Type-safe, atomic access to fields/array elements, thay thế Unsafe operations
- [ ] Thay thế reflection
- [ ] Chỉ cho volatile
- [ ] Database access

> **Giải thích:** VarHandle: get, set, compareAndSet, getAndAdd. atomic operations. Type-safe. Replacement for sun.misc.Unsafe field access. java.lang.invoke.VarHandle.

## Câu 42

[TYPE: SELECT_RESULT]

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Table { String value(); }

@Table("users")
class User {
    private Long id;
    private String name;
}

Table table = User.class.getAnnotation(Table.class);
System.out.println(table != null ? table.value() : "no table");
System.out.println(User.class.getDeclaredFields().length);
```

- [x] users và 2
- [ ] no table và 2
- [ ] users và 0
- [ ] Lỗi runtime

> **Giải thích:** @Table("users") on class. getAnnotation → Table annotation. table.value() = "users". 2 declared fields: id, name.

## Câu 43

[TYPE: FILL_BLANK]

`ParameterizedType.getActualTypeArguments()` trả về `___` of generic type.

- [x] Type[] (actual type arguments)
- [ ] Class[]
- [ ] String[]
- [ ] Object[]

> **Giải thích:** Truy cập generic type info tại runtime qua reflection. Field: getGenericType() → ParameterizedType. getActualTypeArguments() → Type[]. Ví dụ: List<String> → [String.class].

## Câu 44

[TYPE: SELECT_RESULT]

```java
class Container {
    private List<String> items;
}
Field field = Container.class.getDeclaredField("items");
Type type = field.getGenericType();
if (type instanceof ParameterizedType pt) {
    Type[] typeArgs = pt.getActualTypeArguments();
    System.out.println(((Class<?>) typeArgs[0]).getSimpleName());
}
```

- [x] String
- [ ] List
- [ ] Object
- [ ] Lỗi ClassCastException

> **Giải thích:** Field generic type: List<String>. ParameterizedType: raw=List, args=[String]. Type erasure không xóa field generic info.

## Câu 45

[TYPE: SELECT_RESULT]

```java
class Singleton {
    private static Singleton instance = new Singleton();
    private Singleton() {}
    public static Singleton getInstance() { return instance; }
}
// Break singleton via reflection
Constructor<Singleton> ctor = Singleton.class.getDeclaredConstructor();
ctor.setAccessible(true);
Singleton s1 = Singleton.getInstance();
Singleton s2 = ctor.newInstance();
System.out.println(s1 == s2);
```

- [x] false
- [ ] true
- [ ] IllegalAccessException
- [ ] Lỗi biên dịch

> **Giải thích:** Reflection bypasses private constructor. s2 = new instance ≠ s1. Singleton broken! Enum-based singleton prevents this.

## Câu 46

[TYPE: TRUE_FALSE]

Mệnh đề: "Annotation processor (javax.annotation.processing) chạy tại compile-time, không phải runtime."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Annotation processing: compile-time code generation/validation. @Retention(SOURCE/CLASS). Ví dụ: Lombok, MapStruct, Dagger. Khác reflection (runtime).

## Câu 47

[TYPE: SELECT_RESULT]

```java
interface Service {
    String execute(String input);
}

InvocationHandler handler = (proxy, method, args) -> {
    System.out.print("Before ");
    String result = "Processed: " + args[0];
    System.out.print("After ");
    return result;
};
Service service = (Service) Proxy.newProxyInstance(
    Service.class.getClassLoader(), new Class[]{Service.class}, handler);
System.out.print(service.execute("test"));
```

- [x] Before After Processed: test
- [ ] Processed: test
- [ ] Before After
- [ ] Lỗi runtime

> **Giải thích:** Proxy intercepts execute(). handler: print "Before ", create result, print "After ", return result. Print return value. AOP-like pattern.

## Câu 48

[TYPE: SELECT_RESULT]

```java
Method method = String.class.getMethod("valueOf", int.class);
System.out.println(method.invoke(null, 42));
System.out.println(Modifier.isStatic(method.getModifiers()));
```

- [x] 42 và true
- [ ] 42 và false
- [ ] null và true
- [ ] Lỗi NullPointerException

> **Giải thích:** String.valueOf(int): static method. invoke(null, 42): null for static methods (no instance needed). isStatic=true.

## Câu 49

[TYPE: MULTIPLE_CHOICE]

Custom annotation @Retention(RUNTIME) + @Target(METHOD) dùng cho:

- [x] Runtime method-level metadata, readable via reflection
- [ ] Compile-time only
- [ ] Class-level metadata
- [ ] Field-level metadata

> **Giải thích:** RUNTIME: available via reflection. METHOD: chỉ applicable trên methods. Ví dụ: @Test, @Transactional, @GetMapping. Target types: TYPE, FIELD, METHOD, PARAMETER, etc.

## Câu 50

[TYPE: SELECT_RESULT]

```java
class Shape {
    protected void draw() { System.out.println("Shape"); }
}
class Circle extends Shape {
    @Override
    protected void draw() { System.out.println("Circle"); }
}

Shape shape = new Circle();
Method method = Shape.class.getDeclaredMethod("draw");
method.setAccessible(true);
method.invoke(shape);
```

- [x] Circle
- [ ] Shape
- [ ] Lỗi IllegalAccessException
- [ ] Lỗi biên dịch

> **Giải thích:** Even though Method from Shape.class, invoke on Circle instance → polymorphism → Circle.draw() called. Reflection respects virtual dispatch.

## Câu 51

[TYPE: SELECT_RESULT]

```java
Object arr = Array.newInstance(String.class, 3);
Array.set(arr, 0, "Hello");
Array.set(arr, 1, "World");
Array.set(arr, 2, "Java");
System.out.println(Array.get(arr, 1));
System.out.println(Array.getLength(arr));
```

- [x] World và 3
- [ ] Hello và 3
- [ ] Java và 3
- [ ] Lỗi runtime

> **Giải thích:** Array.newInstance: tạo array via reflection. Array.set/get: access elements. Array.getLength: length. Generic array creation.

## Câu 52

[TYPE: FILL_BLANK]

`Class.___()` trả về tất cả declared inner/nested classes.

- [x] getDeclaredClasses
- [ ] getInnerClasses
- [ ] getNestedClasses
- [ ] getClasses

> **Giải thích:** getDeclaredClasses(): all inner/nested classes (including private). getClasses(): only public inner classes (including inherited).

## Câu 53

[TYPE: SELECT_RESULT]

```java
class Outer {
    private class Inner {}
    public static class StaticNested {}
}
Class<?>[] declared = Outer.class.getDeclaredClasses();
System.out.println(declared.length);
for (Class<?> c : declared) {
    System.out.print(c.getSimpleName() + " ");
}
```

- [x] 2 và Inner StaticNested
- [ ] 1 và StaticNested
- [ ] 1 và Inner
- [ ] 0

> **Giải thích:** getDeclaredClasses: tất cả inner/nested. Inner (private inner class) + StaticNested (static nested class) = 2.

## Câu 54

[TYPE: SELECT_RESULT]

```java
interface Validator<T> {
    boolean validate(T value);
}

@SuppressWarnings("unchecked")
<T> Validator<T> createValidator(Class<T> type) {
    return (Validator<T>) Proxy.newProxyInstance(
        type.getClassLoader(),
        new Class[]{Validator.class},
        (proxy, method, args) -> {
            if (method.getName().equals("validate")) {
                return args[0] != null;
            }
            return null;
        });
}
Validator<String> validator = createValidator(String.class);
System.out.println(validator.validate("hello"));
System.out.println(validator.validate(null));
```

- [x] true và false
- [ ] true và true
- [ ] false và false
- [ ] Lỗi ClassCastException

> **Giải thích:** Dynamic proxy: validate returns args[0] != null. "hello" ≠ null → true. null == null → false. Generic proxy creation.

## Câu 55

[TYPE: MULTIPLE_CHOICE]

ClassLoader hierarchy trong Java?

- [x] Bootstrap → Platform (Extension) → Application (System) → Custom
- [ ] Chỉ 1 ClassLoader
- [ ] Application → Bootstrap
- [ ] Custom → Bootstrap

> **Giải thích:** Bootstrap: java.lang, java.util (native). Platform: java.sql, javax. Application: classpath classes. Parent delegation model: child asks parent first.

## Câu 56

[TYPE: TRUE_FALSE]

Mệnh đề: "Reflection có thể bypass final field restriction để modify final fields."

- [x] Đúng
- [ ] Sai

> **Giải thích:** field.setAccessible(true) + field.set(obj, newValue): có thể modify non-static final fields. Static final: khó hơn (JVM optimization). Java 12+ thêm restrictions.

## Câu 57

[TYPE: SELECT_RESULT]

```java
class Immutable {
    private final String value;
    Immutable(String v) { this.value = v; }
    String getValue() { return value; }
}
Immutable obj = new Immutable("original");
Field field = Immutable.class.getDeclaredField("value");
field.setAccessible(true);
field.set(obj, "modified");
System.out.println(obj.getValue());
```

- [x] modified
- [ ] original
- [ ] IllegalAccessException
- [ ] Lỗi biên dịch

> **Giải thích:** Reflection bypasses final. field.set modifies even final field. getValue() returns modified value. Breaks immutability! Dangerous practice.

## Câu 58

[TYPE: SELECT_RESULT]

```java
class Mapper {
    static <T> T fromMap(Map<String, Object> map, Class<T> clazz) throws Exception {
        T obj = clazz.getDeclaredConstructor().newInstance();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (map.containsKey(field.getName())) {
                field.set(obj, map.get(field.getName()));
            }
        }
        return obj;
    }
}
class User { String name; int age; }
Map<String, Object> data = Map.of("name", "An", "age", 25);
User user = Mapper.fromMap(data, User.class);
System.out.println(user.name + " " + user.age);
```

- [x] An 25
- [ ] null 0
- [ ] Lỗi runtime
- [ ] Lỗi biên dịch

> **Giải thích:** Reflection-based mapping: iterate fields, set values from map. ORM/JSON mapping pattern. name="An", age=25. Simple object mapper.

## Câu 59

[TYPE: SELECT_RESULT]

```java
Method[] methods = Object.class.getMethods();
List<String> names = new ArrayList<>();
for (Method m : methods) names.add(m.getName());
Collections.sort(names);
System.out.println(names.contains("equals"));
System.out.println(names.contains("toString"));
System.out.println(names.contains("hashCode"));
```

- [x] true, true, true
- [ ] false, false, false
- [ ] true, true, false
- [ ] true, false, true

> **Giải thích:** Object methods: equals, hashCode, toString, getClass, wait, notify, notifyAll, clone, finalize. All 3 checked → true.

## Câu 60

[TYPE: FILL_BLANK]

`Method.invoke(object, args...)` gọi method trên `___` với arguments.

- [x] object (instance)
- [ ] class
- [ ] null
- [ ] proxy

> **Giải thích:** invoke(target, args): target = instance for instance methods, null for static methods. args: method arguments. Returns Object (return value).

## Câu 61

[TYPE: SELECT_RESULT]

```java
class Plugin {
    public String name() { return "Default"; }
    public int version() { return 1; }
}
Plugin p = new Plugin();
for (Method m : Plugin.class.getDeclaredMethods()) {
    if (m.getParameterCount() == 0 && m.getReturnType() != void.class) {
        System.out.print(m.getName() + "=" + m.invoke(p) + " ");
    }
}
```

- [x] name=Default version=1 (hoặc ngược thứ tự)
- [ ] Chỉ name=Default
- [ ] Không in gì
- [ ] Lỗi

> **Giải thích:** Filter: no params, non-void return. name() → "Default", version() → 1. Both match. Order not guaranteed.

## Câu 62

[TYPE: SELECT_RESULT]

```java
record Point(int x, int y) {}
RecordComponent[] components = Point.class.getRecordComponents();
System.out.println(components.length);
for (RecordComponent rc : components) {
    System.out.print(rc.getName() + ":" + rc.getType().getSimpleName() + " ");
}
```

- [x] 2 và x:int y:int
- [ ] 0
- [ ] 2 và x:Integer y:Integer
- [ ] Lỗi biên dịch

> **Giải thích:** Java 16: getRecordComponents() for record types. Point has 2 components: x(int), y(int). Record-specific reflection API.

## Câu 63

[TYPE: MULTIPLE_CHOICE]

ServiceLoader (java.util.ServiceLoader) là gì?

- [x] Service discovery mechanism: load implementations of interface via META-INF/services
- [ ] Class loader
- [ ] Reflection API
- [ ] Serialization

> **Giải thích:** ServiceLoader: SPI (Service Provider Interface). META-INF/services/interface.name → list implementations. Module system: provides/uses. Plugin architecture.

## Câu 64

[TYPE: TRUE_FALSE]

Mệnh đề: "getMethod() throws NoSuchMethodException nếu method không tồn tại."

- [x] Đúng
- [ ] Sai

> **Giải thích:** getMethod("nonExistent"): NoSuchMethodException (checked). getDeclaredMethod tương tự. Phải handle hoặc declare throws.

## Câu 65

[TYPE: SELECT_RESULT]

```java
@Retention(RetentionPolicy.RUNTIME)
@interface Validate {
    int min() default 0;
    int max() default Integer.MAX_VALUE;
}

class Form {
    @Validate(min = 1, max = 100) private String name;
    @Validate(min = 18) private int age;
}

for (Field f : Form.class.getDeclaredFields()) {
    Validate v = f.getAnnotation(Validate.class);
    if (v != null) {
        System.out.printf("%s: min=%d max=%d%n", f.getName(), v.min(), v.max());
    }
}
```

- [x] name: min=1 max=100 và age: min=18 max=2147483647
- [ ] Chỉ name
- [ ] Không in gì
- [ ] Lỗi runtime

> **Giải thích:** name: min=1, max=100 (explicit). age: min=18, max=Integer.MAX_VALUE (default). Annotation values accessible via reflection.

## Câu 66

[TYPE: SELECT_RESULT]

```java
interface Logger {
    void log(String msg);
    void error(String msg);
}

Logger logger = (Logger) Proxy.newProxyInstance(
    Logger.class.getClassLoader(),
    new Class[]{Logger.class},
    (proxy, method, args) -> {
        System.out.printf("[%s] %s%n", method.getName().toUpperCase(), args[0]);
        return null;
    });
logger.log("hello");
logger.error("oops");
```

- [x] [LOG] hello và [ERROR] oops
- [ ] hello và oops
- [ ] Lỗi runtime
- [ ] [log] hello và [error] oops

> **Giải thích:** Proxy intercepts both methods. method.getName().toUpperCase(): LOG, ERROR. args[0]: message. Dynamic logging proxy.

## Câu 67

[TYPE: SELECT_RESULT]

```java
class Generic<T extends Number> {
    T value;
}
TypeVariable<?>[] typeParams = Generic.class.getTypeParameters();
System.out.println(typeParams[0].getName());
Type[] bounds = typeParams[0].getBounds();
System.out.println(((Class<?>) bounds[0]).getSimpleName());
```

- [x] T và Number
- [ ] T và Object
- [ ] Number và T
- [ ] Lỗi

> **Giải thích:** getTypeParameters(): [T]. getName()="T". getBounds(): [Number] (upper bound). Type erasure: T → Number at bytecode level.

## Câu 68

[TYPE: FILL_BLANK]

`AccessibleObject.___()` bypass Java access control checks.

- [x] setAccessible(true)
- [ ] unlock()
- [ ] open()
- [ ] permit()

> **Giải thích:** setAccessible(true): bypass private/protected checks. Field, Method, Constructor extend AccessibleObject. Security risk. canAccess(obj) checks accessibility.

## Câu 69

[TYPE: SELECT_RESULT]

```java
class EventBus {
    void dispatch(Object target) throws Exception {
        for (Method m : target.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(Subscribe.class) && m.getParameterCount() == 0) {
                m.setAccessible(true);
                m.invoke(target);
            }
        }
    }
}

@Retention(RetentionPolicy.RUNTIME)
@interface Subscribe {}

class Handler {
    @Subscribe void onEvent() { System.out.print("handled "); }
    void other() { System.out.print("other "); }
    @Subscribe void onOther() { System.out.print("other-event "); }
}

new EventBus().dispatch(new Handler());
```

- [x] handled other-event (hoặc other-event handled)
- [ ] handled other other-event
- [ ] handled
- [ ] Lỗi runtime

> **Giải thích:** dispatch: find methods with @Subscribe + 0 params. onEvent ✓, other ✗ (no @Subscribe), onOther ✓. Two methods invoked.

## Câu 70

[TYPE: SELECT_RESULT]

```java
class BeanInfo {
    static Map<String, String> describe(Object obj) throws Exception {
        Map<String, String> map = new TreeMap<>();
        for (Method m : obj.getClass().getMethods()) {
            if (m.getName().startsWith("get") && m.getParameterCount() == 0
                    && !m.getName().equals("getClass")) {
                String prop = m.getName().substring(3);
                Object value = m.invoke(obj);
                map.put(prop, String.valueOf(value));
            }
        }
        return map;
    }
}
class User {
    private String name = "An";
    private int age = 25;
    public String getName() { return name; }
    public int getAge() { return age; }
}
System.out.println(BeanInfo.describe(new User()));
```

- [x] {Age=25, Name=An}
- [ ] {name=An, age=25}
- [ ] {}
- [ ] Lỗi

> **Giải thích:** JavaBeans convention: getXxx() → property Xxx. Introspect via reflection: getName → "Name"="An", getAge → "Age"="25". TreeMap sorted by key.

## Câu 71

[TYPE: MULTIPLE_CHOICE]

Sealed classes và reflection?

- [x] getPermittedSubclasses() (Java 17) trả về danh sách permitted subclasses
- [ ] Sealed classes block reflection
- [ ] Không liên quan
- [ ] Sealed chỉ compile-time

> **Giải thích:** Class.getPermittedSubclasses(): return Class<?>[] of permitted subclasses. isSealed(): check if sealed. Runtime information about class hierarchy.

## Câu 72

[TYPE: SELECT_RESULT]

```java
Method method = String.class.getMethod("charAt", int.class);
Class<?>[] paramTypes = method.getParameterTypes();
Class<?> returnType = method.getReturnType();
Class<?>[] exTypes = method.getExceptionTypes();
System.out.println(paramTypes[0].getSimpleName());
System.out.println(returnType.getSimpleName());
System.out.println(exTypes.length);
```

- [x] int, char, 0
- [ ] Integer, Character, 0
- [ ] int, char, 1
- [ ] int, Character, 0

> **Giải thích:** charAt(int): param=int.class, return=char.class. No declared checked exceptions. Primitive types reflected correctly.

## Câu 73

[TYPE: TRUE_FALSE]

Mệnh đề: "Class.forName() triggers class initialization (static blocks run)."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Class.forName("name"): load + link + initialize. Static initializers run. Class.forName("name", false, classLoader): load without initialize. ClassLoader.loadClass: no init.

## Câu 74

[TYPE: SELECT_RESULT]

```java
class Config {
    static { System.out.print("Init "); }
    public static String value = "Hello";
}
Class<?> clazz = Class.forName("Config");
System.out.print(clazz.getSimpleName());
```

Giả sử Config trong default package:

- [x] Init Config
- [ ] Config Init
- [ ] Config
- [ ] Init

> **Giải thích:** Class.forName triggers initialization → static block runs "Init ". Then getSimpleName() → "Config". Init before access.

## Câu 75

[TYPE: SELECT_RESULT]

```java
interface Transformer<I, O> {
    O transform(I input);
}

@SuppressWarnings("unchecked")
<I, O> Transformer<I, O> chain(Transformer<I, ?>... transformers) {
    return input -> {
        Object current = input;
        for (Transformer t : transformers) {
            current = t.transform(current);
        }
        return (O) current;
    };
}
Transformer<String, Integer> pipeline = chain(
    (Transformer<String, String>) String::trim,
    (Transformer<String, Integer>) Integer::parseInt
);
System.out.println(pipeline.transform("  42  "));
```

- [x] 42
- [ ] "  42  "
- [ ] Lỗi ClassCastException
- [ ] Lỗi biên dịch

> **Giải thích:** Chain: trim → "42" → parseInt → 42. Type-unsafe but works at runtime. Reflection-style dynamic pipeline.

## Câu 76

[TYPE: SELECT_RESULT]

```java
class MyClass {
    public MyClass() {}
    public MyClass(String s) {}
    private MyClass(int n) {}
}
Constructor<?>[] publicCtors = MyClass.class.getConstructors();
Constructor<?>[] allCtors = MyClass.class.getDeclaredConstructors();
System.out.println(publicCtors.length + " " + allCtors.length);
```

- [x] 2 3
- [ ] 3 3
- [ ] 1 3
- [ ] 2 2

> **Giải thích:** getConstructors: public only → 2 (no-arg, String). getDeclaredConstructors: all → 3 (no-arg, String, int/private).

## Câu 77

[TYPE: FILL_BLANK]

`class.___()` trả về ClassLoader đã load class.

- [x] getClassLoader
- [ ] loader
- [ ] loadedBy
- [ ] classLoader

> **Giải thích:** getClassLoader(): return ClassLoader. null for bootstrap ClassLoader (java.lang classes). Application classes: AppClassLoader.

## Câu 78

[TYPE: SELECT_RESULT]

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@interface Feature { String value(); }

@Feature("logging")
class Base {}
class Child extends Base {}

Feature f = Child.class.getAnnotation(Feature.class);
System.out.println(f != null ? f.value() : "none");
```

- [x] logging
- [ ] none
- [ ] Lỗi runtime
- [ ] Lỗi biên dịch

> **Giải thích:** @Inherited: annotation inherited by subclasses (only for TYPE target). Child inherits @Feature from Base. f.value() = "logging".

## Câu 79

[TYPE: MULTIPLE_CHOICE]

Reflection alternatives trong modern Java?

- [x] MethodHandles, VarHandles, Records, Sealed Classes, Pattern Matching
- [ ] Không có alternatives
- [ ] Chỉ MethodHandles
- [ ] Chỉ annotations

> **Giải thích:** MethodHandle: fast method calls. VarHandle: field access. Records: structured data. Sealed: type hierarchy. Pattern matching: type checking. Less need for raw reflection.

## Câu 80

[TYPE: SELECT_RESULT]

```java
class TypeToken<T> {
    Type getType() {
        return ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];
    }
}
TypeToken<List<String>> token = new TypeToken<List<String>>() {};
Type type = token.getType();
System.out.println(type.getTypeName());
```

- [x] java.util.List<java.lang.String>
- [ ] java.util.List
- [ ] java.lang.String
- [ ] Object

> **Giải thích:** Super type token trick: anonymous subclass preserves generic type info. getGenericSuperclass → ParameterizedType. Actual type: List<String>. Used in Gson, Jackson.

## Câu 81

[TYPE: SELECT_RESULT]

```java
interface Cacheable { }
class UserService implements Cacheable { }
class OrderService implements Cacheable { }
class ProductService { }

List<Class<?>> classes = List.of(UserService.class, OrderService.class, ProductService.class);
long count = classes.stream()
    .filter(Cacheable.class::isAssignableFrom)
    .count();
System.out.println(count);
```

- [x] 2
- [ ] 3
- [ ] 1
- [ ] 0

> **Giải thích:** isAssignableFrom: UserService implements Cacheable ✓, OrderService implements Cacheable ✓, ProductService ✗. count = 2.

## Câu 82

[TYPE: TRUE_FALSE]

Mệnh đề: "Method.getAnnotatedReturnType() (Java 8) trả về AnnotatedType cho return type annotations."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Java 8: type annotations (@NonNull String). getAnnotatedReturnType(): AnnotatedType with annotations on return type. Extended annotation support.

## Câu 83

[TYPE: SELECT_RESULT]

```java
class Calculator {
    public int add(int a, int b) { return a + b; }
    public int subtract(int a, int b) { return a - b; }
}

Calculator calc = new Calculator();
Map<String, Method> ops = new HashMap<>();
for (Method m : Calculator.class.getMethods()) {
    if (m.getDeclaringClass() == Calculator.class) {
        ops.put(m.getName(), m);
    }
}
System.out.println((int) ops.get("add").invoke(calc, 5, 3));
System.out.println((int) ops.get("subtract").invoke(calc, 10, 4));
```

- [x] 8 và 6
- [ ] 5 và 10
- [ ] Lỗi NullPointerException
- [ ] Lỗi runtime

> **Giải thích:** Method dispatch via reflection map. add(5,3)=8. subtract(10,4)=6. getDeclaringClass: filter out Object methods.

## Câu 84

[TYPE: SELECT_RESULT]

```java
class Parent { public void method() {} }
class Child extends Parent { @Override public void method() {} }

Method parentMethod = Parent.class.getMethod("method");
Method childMethod = Child.class.getMethod("method");
System.out.println(parentMethod.getDeclaringClass().getSimpleName());
System.out.println(childMethod.getDeclaringClass().getSimpleName());
System.out.println(parentMethod.equals(childMethod));
```

- [x] Parent, Child, false
- [ ] Parent, Parent, true
- [ ] Child, Child, true
- [ ] Parent, Child, true

> **Giải thích:** getDeclaringClass: class that declares the method. Parent's method declared in Parent. Child overrides → declared in Child. Different Method objects.

## Câu 85

[TYPE: FILL_BLANK]

`Proxy.newProxyInstance()` cần 3 tham số: ClassLoader, `___` array, InvocationHandler.

- [x] Class<?> (interfaces)
- [ ] Method
- [ ] String
- [ ] Object

> **Giải thích:** newProxyInstance(classLoader, interfaces[], handler): tạo proxy implement interfaces. ClassLoader: load proxy class. interfaces: target interfaces. handler: method dispatcher.

## Câu 86

[TYPE: SELECT_RESULT]

```java
class FieldCopier {
    static void copy(Object from, Object to) throws Exception {
        for (Field f : from.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            Field target = to.getClass().getDeclaredField(f.getName());
            target.setAccessible(true);
            target.set(to, f.get(from));
        }
    }
}
class A { String name = "An"; int value = 42; }
class B { String name; int value; }
B b = new B();
FieldCopier.copy(new A(), b);
System.out.println(b.name + " " + b.value);
```

- [x] An 42
- [ ] null 0
- [ ] Lỗi NoSuchFieldException
- [ ] Lỗi runtime

> **Giải thích:** Reflection field copy: iterate A's fields, find matching B fields, copy values. name="An", value=42. Simple bean copier.

## Câu 87

[TYPE: SELECT_RESULT]

```java
String lambdaClassName = ((Runnable) () -> {}).getClass().getName();
System.out.println(lambdaClassName.contains("Lambda"));
System.out.println(((Runnable) () -> {}).getClass().isSynthetic());
```

- [x] true và true
- [ ] false và false
- [ ] true và false
- [ ] false và true

> **Giải thích:** Lambda: compiler generates synthetic class. Name contains "$$Lambda". isSynthetic()=true. Lambda classes are runtime-generated.

## Câu 88

[TYPE: MULTIPLE_CHOICE]

Khi nào KHÔNG nên dùng reflection?

- [x] Khi có type-safe alternative, performance-critical code, simple object creation
- [ ] Không bao giờ dùng
- [ ] Luôn dùng
- [ ] Chỉ khi testing

> **Giải thích:** Avoid: khi có direct API, performance-critical paths, simple factory patterns. Use: frameworks (DI, ORM), testing (access private), dynamic loading, serialization.

## Câu 89

[TYPE: SELECT_RESULT]

```java
Method method = String.class.getMethod("length");
Parameter[] params = method.getParameters();
System.out.println(params.length);
System.out.println(method.getReturnType() == int.class);
```

- [x] 0 và true
- [ ] 1 và true
- [ ] 0 và false
- [ ] 1 và false

> **Giải thích:** String.length(): no parameters → params.length=0. Return type: int (primitive) → int.class == int.class → true.

## Câu 90

[TYPE: TRUE_FALSE]

Mệnh đề: "Reflection API methods that return arrays return empty arrays (not null) when there are no matching elements."

- [x] Đúng
- [ ] Sai

> **Giải thích:** getMethods(), getDeclaredFields(), etc: return empty array (length 0) if none found. Never null. getAnnotation() returns null if not present.

## Câu 91

[TYPE: SELECT_RESULT]

```java
class JsonSerializer {
    static String toJson(Object obj) throws Exception {
        StringBuilder sb = new StringBuilder("{");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            Object value = fields[i].get(obj);
            sb.append("\"").append(fields[i].getName()).append("\":");
            if (value instanceof String) sb.append("\"").append(value).append("\"");
            else sb.append(value);
            if (i < fields.length - 1) sb.append(",");
        }
        return sb.append("}").toString();
    }
}
class Item { String name = "Phone"; int price = 999; }
System.out.println(JsonSerializer.toJson(new Item()));
```

- [x] {"name":"Phone","price":999}
- [ ] {name:Phone, price:999}
- [ ] Lỗi runtime
- [ ] null

> **Giải thích:** Reflection-based JSON serializer: iterate fields, format as JSON. String → quoted, others → raw. Simplified Jackson/Gson concept.

## Câu 92

[TYPE: SELECT_RESULT]

```java
interface MathOp { int apply(int a, int b); }

Map<String, MathOp> ops = Map.of(
    "add", (a, b) -> a + b,
    "mul", (a, b) -> a * b
);
Method applyMethod = MathOp.class.getMethod("apply", int.class, int.class);
int result = (int) applyMethod.invoke(ops.get("add"), 3, 4);
System.out.println(result);
```

- [x] 7
- [ ] 12
- [ ] Lỗi
- [ ] 0

> **Giải thích:** Get lambda from map. invoke via reflection: apply(3, 4) = 3+4 = 7. Reflection works on lambda instances.

## Câu 93

[TYPE: SELECT_RESULT]

```java
class TypeChecker {
    static String describe(Object obj) {
        Class<?> c = obj.getClass();
        if (c.isRecord()) return "Record";
        if (c.isEnum()) return "Enum";
        if (c.isArray()) return "Array";
        if (c.isInterface()) return "Interface";
        return "Class";
    }
}
System.out.print(TypeChecker.describe(new int[0]) + " ");
System.out.print(TypeChecker.describe("Hello") + " ");
enum E { A } System.out.print(TypeChecker.describe(E.A));
```

- [x] Array Class Enum
- [ ] Class Class Enum
- [ ] Array String Enum
- [ ] Array Class Class

> **Giải thích:** int[].getClass().isArray()=true. String → Class (not record/enum/array/interface). E.A → isEnum()=true.

## Câu 94

[TYPE: FILL_BLANK]

`Class.___()` kiểm tra class có phải là sealed class không (Java 17).

- [x] isSealed
- [ ] sealed
- [ ] isRestricted
- [ ] isClosed

> **Giải thích:** isSealed() (Java 17): true for sealed classes/interfaces. getPermittedSubclasses(): list permitted subclasses. Sealed class reflection support.

## Câu 95

[TYPE: SELECT_RESULT]

```java
@Retention(RetentionPolicy.RUNTIME)
@interface Priority { int value(); }

class TaskRunner {
    @Priority(3) public void taskA() { System.out.print("A "); }
    @Priority(1) public void taskB() { System.out.print("B "); }
    @Priority(2) public void taskC() { System.out.print("C "); }
}
TaskRunner runner = new TaskRunner();
List<Method> methods = Arrays.stream(TaskRunner.class.getDeclaredMethods())
    .filter(m -> m.isAnnotationPresent(Priority.class))
    .sorted(Comparator.comparingInt(m -> m.getAnnotation(Priority.class).value()))
    .collect(Collectors.toList());
for (Method m : methods) m.invoke(runner);
```

- [x] B C A
- [ ] A B C
- [ ] C B A
- [ ] A C B

> **Giải thích:** Sort by @Priority value: B(1) < C(2) < A(3). Execute in order: B, C, A. Annotation-driven execution order.

## Câu 96

[TYPE: SELECT_RESULT]

```java
class Resource implements AutoCloseable {
    String name;
    Resource(String n) { this.name = n; }
    public void close() { System.out.print("close:" + name + " "); }
}
Constructor<Resource> ctor = Resource.class.getDeclaredConstructor(String.class);
try (Resource r = ctor.newInstance("test")) {
    System.out.print("use ");
}
```

- [x] use close:test
- [ ] close:test use
- [ ] use
- [ ] Lỗi

> **Giải thích:** Constructor.newInstance("test"): create Resource. try-with-resources: auto-close. "use " → close → "close:test ".

## Câu 97

[TYPE: TRUE_FALSE]

Mệnh đề: "Java records có canonical constructor accessible via getRecordComponents() + reflection."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Record: getRecordComponents() → components. getDeclaredConstructor(component types) → canonical constructor. Record reflection: structured access to components.

## Câu 98

[TYPE: SELECT_RESULT]

```java
class AnnotationScanner {
    static List<String> findAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getDeclaredMethods())
            .filter(m -> m.isAnnotationPresent(annotation))
            .map(Method::getName)
            .sorted()
            .collect(Collectors.toList());
    }
}

class MyService {
    @Deprecated public void old1() {}
    @Deprecated public void old2() {}
    public void current() {}
}
System.out.println(AnnotationScanner.findAnnotatedMethods(MyService.class, Deprecated.class));
```

- [x] [old1, old2]
- [ ] [current, old1, old2]
- [ ] [old1]
- [ ] []

> **Giải thích:** Find methods with @Deprecated: old1, old2. current has no annotation. Sorted: [old1, old2].

## Câu 99

[TYPE: SELECT_RESULT]

```java
Method equalsMethod = Object.class.getMethod("equals", Object.class);
System.out.println(equalsMethod.getDeclaringClass().getSimpleName());
Method toStringMethod = String.class.getMethod("toString");
System.out.println(toStringMethod.getDeclaringClass().getSimpleName());
```

- [x] Object và String
- [ ] Object và Object
- [ ] String và String
- [ ] Object và CharSequence

> **Giải thích:** equals defined in Object → declaringClass=Object. String overrides toString → declaringClass=String (most specific declaration).

## Câu 100

[TYPE: MULTIPLE_CHOICE]

Best practices cho Reflection?

- [x] Cache Method/Field objects, handle exceptions properly, minimize use, consider alternatives
- [ ] Dùng mọi nơi
- [ ] Không cần cache
- [ ] Ignore exceptions

> **Giải thích:** Cache: getMethod() expensive. Handle: NoSuchMethodException, IllegalAccessException, InvocationTargetException. Minimize: use when necessary. Prefer type-safe APIs.
