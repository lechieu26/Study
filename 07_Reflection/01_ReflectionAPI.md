# Reflection API trong Java

## Mục lục

1. [Giới thiệu về Reflection](#1-giới-thiệu-về-reflection)
2. [Class Object](#2-class-object)
3. [Constructors](#3-constructors)
4. [Fields](#4-fields)
5. [Methods](#5-methods)
6. [Annotations](#6-annotations)
7. [Arrays và Reflection](#7-arrays-và-reflection)
8. [Proxy và Dynamic Proxy](#8-proxy-và-dynamic-proxy)
9. [Performance Considerations](#9-performance-considerations)
10. [Security Considerations](#10-security-considerations)
11. [Use Cases](#11-use-cases)

---

## 1. Giới thiệu về Reflection

**Reflection** là khả năng của chương trình Java kiểm tra (inspect) và thay đổi (modify) cấu trúc và hành vi của class tại **runtime**. Nó cho phép:
- Xem thông tin class, method, field, constructor tại runtime
- Tạo instance của class mà không biết tên class lúc compile
- Gọi method, truy cập field kể cả private
- Tạo proxy động

```java
import java.lang.reflect.*;

public class ReflectionIntroDemo {
    public static void main(String[] args) throws Exception {
        // Lấy thông tin class tại runtime
        Class<?> clazz = String.class;
        
        System.out.println("Class name: " + clazz.getName());
        System.out.println("Simple name: " + clazz.getSimpleName());
        System.out.println("Package: " + clazz.getPackageName());
        System.out.println("Is interface: " + clazz.isInterface());
        System.out.println("Superclass: " + clazz.getSuperclass().getName());
        
        // Liệt kê tất cả methods
        Method[] methods = clazz.getDeclaredMethods();
        System.out.println("Number of methods: " + methods.length);
    }
}
```

### Khi nào sử dụng Reflection?

| Use Case | Ví dụ |
|----------|-------|
| **Framework development** | Spring IoC, Hibernate ORM |
| **Testing** | JUnit test runner, Mockito |
| **Serialization** | Jackson, Gson |
| **Plugin systems** | Loading classes dynamically |
| **Code analysis tools** | IDE auto-completion, linting |
| **Dependency Injection** | Inject dependencies at runtime |

---

## 2. Class Object

### Lấy Class Object

```java
public class GetClassDemo {
    public static void main(String[] args) throws ClassNotFoundException {
        // Cách 1: .class literal
        Class<String> clazz1 = String.class;
        
        // Cách 2: getClass() từ instance
        String str = "Hello";
        Class<?> clazz2 = str.getClass();
        
        // Cách 3: Class.forName() - dynamic loading
        Class<?> clazz3 = Class.forName("java.util.ArrayList");
        
        // Cách 4: Cho primitive types
        Class<Integer> intClass = int.class;        // primitive
        Class<Integer> intWrap = Integer.class;     // wrapper
        Class<Integer> intType = Integer.TYPE;      // == int.class
        
        // Kiểm tra
        System.out.println(clazz1 == clazz2); // true - cùng Class object
        System.out.println(int.class == Integer.TYPE); // true
        System.out.println(int.class == Integer.class); // false
    }
}
```

### Các phương thức quan trọng của Class

```java
import java.lang.reflect.*;
import java.util.Arrays;

public class ClassMethodsDemo {
    public static void main(String[] args) {
        Class<?> clazz = ArrayList.class;
        
        // Tên
        System.out.println("Name: " + clazz.getName());             // java.util.ArrayList
        System.out.println("Simple: " + clazz.getSimpleName());     // ArrayList
        System.out.println("Canonical: " + clazz.getCanonicalName()); // java.util.ArrayList
        
        // Hierarchy
        System.out.println("Superclass: " + clazz.getSuperclass());
        Class<?>[] interfaces = clazz.getInterfaces();
        System.out.println("Interfaces: " + Arrays.toString(interfaces));
        
        // Modifiers
        int mod = clazz.getModifiers();
        System.out.println("Is public: " + Modifier.isPublic(mod));
        System.out.println("Is abstract: " + Modifier.isAbstract(mod));
        System.out.println("Is final: " + Modifier.isFinal(mod));
        
        // Type checks
        System.out.println("Is array: " + clazz.isArray());
        System.out.println("Is enum: " + clazz.isEnum());
        System.out.println("Is interface: " + clazz.isInterface());
        System.out.println("Is annotation: " + clazz.isAnnotation());
        
        // Type parameters (Generics)
        TypeVariable<?>[] typeParams = clazz.getTypeParameters();
        System.out.println("Type params: " + Arrays.toString(typeParams)); // [E]
    }
}
```

---

## 3. Constructors

### Lấy thông tin Constructors

```java
import java.lang.reflect.*;

class Person {
    private String name;
    private int age;
    
    public Person() { this.name = "Unknown"; this.age = 0; }
    public Person(String name) { this.name = name; this.age = 0; }
    private Person(String name, int age) { this.name = name; this.age = age; }
    
    @Override
    public String toString() { return "Person{name='" + name + "', age=" + age + "}"; }
}

public class ConstructorDemo {
    public static void main(String[] args) throws Exception {
        Class<Person> clazz = Person.class;
        
        // Lấy tất cả public constructors
        Constructor<?>[] publicCtors = clazz.getConstructors();
        System.out.println("Public constructors: " + publicCtors.length);
        
        // Lấy TẤT CẢ constructors (bao gồm private)
        Constructor<?>[] allCtors = clazz.getDeclaredConstructors();
        for (Constructor<?> ctor : allCtors) {
            System.out.println("  " + ctor);
            System.out.println("    Params: " + Arrays.toString(ctor.getParameterTypes()));
            System.out.println("    Modifiers: " + Modifier.toString(ctor.getModifiers()));
        }
        
        // Lấy constructor cụ thể theo parameter types
        Constructor<Person> noArgCtor = clazz.getConstructor(); // public no-arg
        Constructor<Person> oneArgCtor = clazz.getConstructor(String.class);
        Constructor<Person> privateCtor = clazz.getDeclaredConstructor(String.class, int.class);
    }
}
```

### Tạo instance từ Constructor

```java
public class CreateInstanceDemo {
    public static void main(String[] args) throws Exception {
        Class<Person> clazz = Person.class;
        
        // Cách 1: No-arg constructor
        Person p1 = clazz.getConstructor().newInstance();
        System.out.println(p1); // Person{name='Unknown', age=0}
        
        // Cách 2: Constructor có tham số
        Constructor<Person> ctor = clazz.getConstructor(String.class);
        Person p2 = ctor.newInstance("Alice");
        System.out.println(p2); // Person{name='Alice', age=0}
        
        // Cách 3: Private constructor (cần setAccessible)
        Constructor<Person> privateCtor = clazz.getDeclaredConstructor(String.class, int.class);
        privateCtor.setAccessible(true); // bypass access check
        Person p3 = privateCtor.newInstance("Bob", 30);
        System.out.println(p3); // Person{name='Bob', age=30}
        
        // Cách 4: Dynamic - tạo instance từ class name (String)
        Class<?> dynamicClass = Class.forName("java.util.ArrayList");
        Object list = dynamicClass.getConstructor().newInstance();
        System.out.println(list.getClass().getSimpleName()); // ArrayList
    }
}
```

---

## 4. Fields

### Lấy thông tin Fields

```java
import java.lang.reflect.*;

class Employee {
    public String name;
    protected int age;
    private double salary;
    private static int count = 0;
    public final String company = "Tech Corp";
}

public class FieldInfoDemo {
    public static void main(String[] args) {
        Class<Employee> clazz = Employee.class;
        
        // Public fields (bao gồm inherited)
        Field[] publicFields = clazz.getFields();
        System.out.println("Public fields: " + publicFields.length);
        
        // Tất cả fields (chỉ declared, không inherited)
        Field[] allFields = clazz.getDeclaredFields();
        for (Field field : allFields) {
            System.out.printf("  %s %s %s%n",
                Modifier.toString(field.getModifiers()),
                field.getType().getSimpleName(),
                field.getName()
            );
        }
        // Output:
        // public String name
        // protected int age
        // private double salary
        // private static int count
        // public final String company
    }
}
```

### Đọc và ghi Fields

```java
public class FieldAccessDemo {
    public static void main(String[] args) throws Exception {
        Employee emp = new Employee();
        Class<Employee> clazz = Employee.class;
        
        // Đọc public field
        Field nameField = clazz.getField("name");
        nameField.set(emp, "Alice");
        String name = (String) nameField.get(emp);
        System.out.println("Name: " + name); // Alice
        
        // Đọc/ghi private field
        Field salaryField = clazz.getDeclaredField("salary");
        salaryField.setAccessible(true); // bypass private
        salaryField.set(emp, 5000.0);
        double salary = salaryField.getDouble(emp);
        System.out.println("Salary: " + salary); // 5000.0
        
        // Đọc/ghi static field
        Field countField = clazz.getDeclaredField("count");
        countField.setAccessible(true);
        countField.set(null, 42); // null vì là static
        int count = countField.getInt(null);
        System.out.println("Count: " + count); // 42
        
        // Đọc final field (CÓ THỂ ghi nhưng KHÔNG NÊN)
        Field companyField = clazz.getField("company");
        System.out.println("Company: " + companyField.get(emp)); // Tech Corp
        // Ghi final field (hack - không đảm bảo hoạt động từ Java 12+)
        // companyField.setAccessible(true);
        // companyField.set(emp, "New Corp");
    }
}
```

---

## 5. Methods

### Lấy thông tin Methods

```java
import java.lang.reflect.*;

class Calculator {
    public int add(int a, int b) { return a + b; }
    public double divide(double a, double b) throws ArithmeticException {
        if (b == 0) throw new ArithmeticException("Division by zero");
        return a / b;
    }
    private String format(int number) { return String.format("%,d", number); }
    public static int multiply(int a, int b) { return a * b; }
}

public class MethodInfoDemo {
    public static void main(String[] args) {
        Class<Calculator> clazz = Calculator.class;
        
        // Tất cả public methods (bao gồm inherited từ Object)
        Method[] publicMethods = clazz.getMethods();
        
        // Chỉ declared methods (không inherited)
        Method[] declaredMethods = clazz.getDeclaredMethods();
        
        for (Method method : declaredMethods) {
            System.out.println("Method: " + method.getName());
            System.out.println("  Return type: " + method.getReturnType().getSimpleName());
            System.out.println("  Parameters: " + Arrays.toString(method.getParameterTypes()));
            System.out.println("  Exceptions: " + Arrays.toString(method.getExceptionTypes()));
            System.out.println("  Modifiers: " + Modifier.toString(method.getModifiers()));
            System.out.println();
        }
    }
}
```

### Invoke Methods

```java
public class MethodInvokeDemo {
    public static void main(String[] args) throws Exception {
        Calculator calc = new Calculator();
        Class<Calculator> clazz = Calculator.class;
        
        // Invoke public method
        Method addMethod = clazz.getMethod("add", int.class, int.class);
        int result = (int) addMethod.invoke(calc, 3, 5);
        System.out.println("3 + 5 = " + result); // 8
        
        // Invoke method with different return type
        Method divideMethod = clazz.getMethod("divide", double.class, double.class);
        double divResult = (double) divideMethod.invoke(calc, 10.0, 3.0);
        System.out.println("10 / 3 = " + divResult); // 3.333...
        
        // Invoke private method
        Method formatMethod = clazz.getDeclaredMethod("format", int.class);
        formatMethod.setAccessible(true);
        String formatted = (String) formatMethod.invoke(calc, 1000000);
        System.out.println("Formatted: " + formatted); // 1,000,000
        
        // Invoke static method
        Method multiplyMethod = clazz.getMethod("multiply", int.class, int.class);
        int product = (int) multiplyMethod.invoke(null, 4, 7); // null cho static
        System.out.println("4 * 7 = " + product); // 28
        
        // Invoke method by name (dynamic dispatch)
        String methodName = "add"; // có thể đến từ config, input...
        Method dynamicMethod = clazz.getMethod(methodName, int.class, int.class);
        int dynResult = (int) dynamicMethod.invoke(calc, 10, 20);
        System.out.println("Dynamic: " + dynResult); // 30
    }
}
```

---

## 6. Annotations

### Lấy thông tin Annotations

```java
import java.lang.annotation.*;
import java.lang.reflect.*;

// Định nghĩa custom annotation
@Retention(RetentionPolicy.RUNTIME)  // Available at runtime
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@interface MyAnnotation {
    String value() default "";
    int priority() default 0;
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Test {
    String description() default "";
}

@MyAnnotation(value = "Service class", priority = 1)
class UserService {
    @MyAnnotation("user name field")
    private String userName;
    
    @Test(description = "Test creating user")
    public void createUser() { }
    
    @Test(description = "Test deleting user")
    @Deprecated
    public void deleteUser() { }
}

public class AnnotationDemo {
    public static void main(String[] args) {
        Class<UserService> clazz = UserService.class;
        
        // Class-level annotations
        if (clazz.isAnnotationPresent(MyAnnotation.class)) {
            MyAnnotation ann = clazz.getAnnotation(MyAnnotation.class);
            System.out.println("Class annotation value: " + ann.value());
            System.out.println("Class annotation priority: " + ann.priority());
        }
        
        // Method-level annotations
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                Test test = method.getAnnotation(Test.class);
                System.out.println("Test method: " + method.getName());
                System.out.println("  Description: " + test.description());
            }
            
            // All annotations on method
            Annotation[] annotations = method.getAnnotations();
            System.out.println("  All annotations: " + Arrays.toString(annotations));
        }
        
        // Field-level annotations
        for (Field field : clazz.getDeclaredFields()) {
            MyAnnotation ann = field.getAnnotation(MyAnnotation.class);
            if (ann != null) {
                System.out.println("Field " + field.getName() + ": " + ann.value());
            }
        }
    }
}
```

### Custom Annotations

```java
import java.lang.annotation.*;
import java.lang.reflect.*;

// Annotation cho validation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotNull {
    String message() default "Field must not be null";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Range {
    int min() default 0;
    int max() default Integer.MAX_VALUE;
    String message() default "Value out of range";
}

class User {
    @NotNull(message = "Name is required")
    private String name;
    
    @Range(min = 18, max = 100, message = "Age must be between 18 and 100")
    private int age;
    
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

// Validator sử dụng Reflection
class Validator {
    public static List<String> validate(Object obj) throws IllegalAccessException {
        List<String> errors = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(obj);
            
            // Check @NotNull
            if (field.isAnnotationPresent(NotNull.class)) {
                if (value == null) {
                    NotNull ann = field.getAnnotation(NotNull.class);
                    errors.add(ann.message());
                }
            }
            
            // Check @Range
            if (field.isAnnotationPresent(Range.class)) {
                Range ann = field.getAnnotation(Range.class);
                if (value instanceof Integer) {
                    int intVal = (Integer) value;
                    if (intVal < ann.min() || intVal > ann.max()) {
                        errors.add(ann.message());
                    }
                }
            }
        }
        return errors;
    }
}

// Sử dụng
public class CustomAnnotationDemo {
    public static void main(String[] args) throws Exception {
        User validUser = new User("Alice", 25);
        User invalidUser = new User(null, 15);
        
        System.out.println("Valid: " + Validator.validate(validUser));     // []
        System.out.println("Invalid: " + Validator.validate(invalidUser)); // [Name is required, Age must be...]
    }
}
```

---

## 7. Arrays và Reflection

### Tạo mảng động

```java
import java.lang.reflect.Array;

public class ArrayReflectionDemo {
    public static void main(String[] args) {
        // Tạo mảng bằng reflection
        int[] intArr = (int[]) Array.newInstance(int.class, 5);
        String[] strArr = (String[]) Array.newInstance(String.class, 3);
        
        // Set values
        Array.set(intArr, 0, 10);
        Array.set(intArr, 1, 20);
        Array.set(intArr, 2, 30);
        
        Array.set(strArr, 0, "Hello");
        Array.set(strArr, 1, "World");
        
        // Get values
        System.out.println("intArr[0]: " + Array.get(intArr, 0)); // 10
        System.out.println("strArr[1]: " + Array.get(strArr, 1)); // World
        
        // Length
        System.out.println("Length: " + Array.getLength(intArr)); // 5
        
        // Multi-dimensional
        int[][] matrix = (int[][]) Array.newInstance(int.class, 3, 4);
        Array.set(matrix[0], 0, 99);
        System.out.println("matrix[0][0]: " + matrix[0][0]); // 99
    }
}
```

### Thao tác với mảng

```java
public class ArrayManipulationDemo {
    // Generic array resizer sử dụng reflection
    @SuppressWarnings("unchecked")
    public static <T> T resizeArray(T array, int newSize) {
        Class<?> componentType = array.getClass().getComponentType();
        int oldSize = Array.getLength(array);
        
        Object newArray = Array.newInstance(componentType, newSize);
        System.arraycopy(array, 0, newArray, 0, Math.min(oldSize, newSize));
        
        return (T) newArray;
    }
    
    public static void main(String[] args) {
        int[] original = {1, 2, 3, 4, 5};
        int[] resized = resizeArray(original, 10);
        System.out.println(Arrays.toString(resized)); // [1, 2, 3, 4, 5, 0, 0, 0, 0, 0]
        
        String[] strArr = {"A", "B", "C"};
        String[] resizedStr = resizeArray(strArr, 5);
        System.out.println(Arrays.toString(resizedStr)); // [A, B, C, null, null]
        
        // Kiểm tra component type
        Class<?> type = original.getClass().getComponentType();
        System.out.println("Component type: " + type); // int
        System.out.println("Is array: " + original.getClass().isArray()); // true
    }
}
```

---

## 8. Proxy và Dynamic Proxy

### Static Proxy

```java
// Interface
interface UserRepository {
    void save(String user);
    String find(int id);
}

// Real implementation
class UserRepositoryImpl implements UserRepository {
    @Override
    public void save(String user) {
        System.out.println("Saving user: " + user);
    }
    
    @Override
    public String find(int id) {
        return "User_" + id;
    }
}

// Static Proxy - phải code thủ công cho mỗi method
class LoggingProxy implements UserRepository {
    private final UserRepository target;
    
    public LoggingProxy(UserRepository target) {
        this.target = target;
    }
    
    @Override
    public void save(String user) {
        System.out.println("[LOG] Before save");
        target.save(user);
        System.out.println("[LOG] After save");
    }
    
    @Override
    public String find(int id) {
        System.out.println("[LOG] Before find");
        String result = target.find(id);
        System.out.println("[LOG] After find");
        return result;
    }
}
```

### Dynamic Proxy

```java
import java.lang.reflect.*;

// Dynamic Proxy - tự động cho TẤT CẢ methods
class LoggingHandler implements InvocationHandler {
    private final Object target;
    
    public LoggingHandler(Object target) {
        this.target = target;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("[LOG] Calling: " + method.getName() + 
                           " with args: " + Arrays.toString(args));
        
        try {
            Object result = method.invoke(target, args);
            long elapsed = System.currentTimeMillis() - start;
            System.out.println("[LOG] " + method.getName() + " returned: " + result +
                             " (took " + elapsed + "ms)");
            return result;
        } catch (InvocationTargetException e) {
            System.out.println("[LOG] " + method.getName() + " threw: " + e.getCause());
            throw e.getCause();
        }
    }
}

public class DynamicProxyDemo {
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(T target, Class<T> interfaceType) {
        return (T) Proxy.newProxyInstance(
            interfaceType.getClassLoader(),
            new Class<?>[]{interfaceType},
            new LoggingHandler(target)
        );
    }
    
    public static void main(String[] args) {
        // Tạo real object
        UserRepository realRepo = new UserRepositoryImpl();
        
        // Tạo proxy
        UserRepository proxyRepo = createProxy(realRepo, UserRepository.class);
        
        // Sử dụng proxy - tự động log
        proxyRepo.save("Alice");
        // [LOG] Calling: save with args: [Alice]
        // Saving user: Alice
        // [LOG] save returned: null (took 0ms)
        
        String user = proxyRepo.find(1);
        // [LOG] Calling: find with args: [1]
        // [LOG] find returned: User_1 (took 0ms)
        
        System.out.println("Found: " + user); // User_1
    }
}
```

**Ứng dụng Dynamic Proxy:**

```java
// Transaction proxy
class TransactionHandler implements InvocationHandler {
    private final Object target;
    
    public TransactionHandler(Object target) { this.target = target; }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("BEGIN TRANSACTION");
        try {
            Object result = method.invoke(target, args);
            System.out.println("COMMIT");
            return result;
        } catch (Exception e) {
            System.out.println("ROLLBACK");
            throw e;
        }
    }
}

// Caching proxy
class CachingHandler implements InvocationHandler {
    private final Object target;
    private final Map<String, Object> cache = new HashMap<>();
    
    public CachingHandler(Object target) { this.target = target; }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String key = method.getName() + Arrays.toString(args);
        
        if (cache.containsKey(key)) {
            System.out.println("[CACHE HIT] " + key);
            return cache.get(key);
        }
        
        Object result = method.invoke(target, args);
        cache.put(key, result);
        System.out.println("[CACHE MISS] " + key);
        return result;
    }
}
```

---

## 9. Performance Considerations

### Reflection chậm hơn normal code

```java
public class PerformanceDemo {
    public static void main(String[] args) throws Exception {
        Calculator calc = new Calculator();
        Method addMethod = Calculator.class.getMethod("add", int.class, int.class);
        
        int iterations = 10_000_000;
        
        // Direct call
        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            calc.add(1, 2);
        }
        long directTime = System.nanoTime() - start;
        
        // Reflection call
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            addMethod.invoke(calc, 1, 2);
        }
        long reflectTime = System.nanoTime() - start;
        
        System.out.printf("Direct: %d ms%n", directTime / 1_000_000);
        System.out.printf("Reflection: %d ms%n", reflectTime / 1_000_000);
        System.out.printf("Reflection is ~%.1fx slower%n", (double) reflectTime / directTime);
    }
}
```

### Caching để cải thiện performance

```java
import java.lang.reflect.*;
import java.util.concurrent.ConcurrentHashMap;

public class ReflectionCache {
    // Cache Method objects để tránh lookup lặp lại
    private static final ConcurrentHashMap<String, Method> methodCache = new ConcurrentHashMap<>();
    
    public static Method getMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
        String key = clazz.getName() + "#" + name + Arrays.toString(paramTypes);
        
        return methodCache.computeIfAbsent(key, k -> {
            try {
                Method method = clazz.getDeclaredMethod(name, paramTypes);
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }
    
    // Sử dụng MethodHandle (Java 7+) - nhanh hơn reflection
    // import java.lang.invoke.*;
    // MethodHandles.Lookup lookup = MethodHandles.lookup();
    // MethodHandle mh = lookup.findVirtual(Calculator.class, "add",
    //     MethodType.methodType(int.class, int.class, int.class));
    // int result = (int) mh.invoke(calc, 3, 5);
}
```

---

## 10. Security Considerations

### Security Manager

```java
public class SecurityDemo {
    public static void main(String[] args) {
        // setAccessible(true) có thể bị chặn bởi SecurityManager
        // Trong môi trường production, cần cẩn thận
        
        try {
            Field field = SecureClass.class.getDeclaredField("secret");
            field.setAccessible(true); // Có thể throw SecurityException
            // ...
        } catch (SecurityException e) {
            System.out.println("Access denied by SecurityManager");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
```

### Access Control

```java
// Module system (Java 9+) hạn chế reflection
// Cần --add-opens nếu truy cập internal packages
// java --add-opens java.base/java.lang=ALL-UNNAMED MyApp

// Best practices:
// 1. Chỉ dùng setAccessible khi thực sự cần thiết
// 2. Kiểm tra permission trước khi truy cập
// 3. Log mọi reflection access vào private members
// 4. Sử dụng MethodHandle thay vì reflection khi có thể (nhanh hơn, an toàn hơn)
```

---

## 11. Use Cases

### Frameworks (Spring, Hibernate)

```java
// Spring-like Dependency Injection bằng Reflection
class SimpleContainer {
    private final Map<Class<?>, Object> instances = new HashMap<>();
    
    public <T> void register(Class<T> type) throws Exception {
        Constructor<T> ctor = type.getDeclaredConstructor();
        T instance = ctor.newInstance();
        
        // Inject dependencies vào @Autowired fields
        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                Object dependency = instances.get(field.getType());
                if (dependency != null) {
                    field.set(instance, dependency);
                }
            }
        }
        
        instances.put(type, instance);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> type) {
        return (T) instances.get(type);
    }
}
```

### Testing frameworks (JUnit)

```java
// JUnit-like test runner bằng Reflection
class SimpleTestRunner {
    public static void run(Class<?> testClass) throws Exception {
        Object testInstance = testClass.getDeclaredConstructor().newInstance();
        
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                System.out.print("Running " + method.getName() + "... ");
                try {
                    method.invoke(testInstance);
                    System.out.println("PASSED");
                } catch (InvocationTargetException e) {
                    System.out.println("FAILED: " + e.getCause().getMessage());
                }
            }
        }
    }
}
```

### Serialization/Deserialization

```java
// Simple JSON-like serialization
class SimpleSerializer {
    public static String serialize(Object obj) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder("{");
        Field[] fields = obj.getClass().getDeclaredFields();
        
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            sb.append("\"").append(fields[i].getName()).append("\": ");
            
            Object value = fields[i].get(obj);
            if (value instanceof String) {
                sb.append("\"").append(value).append("\"");
            } else {
                sb.append(value);
            }
            
            if (i < fields.length - 1) sb.append(", ");
        }
        
        return sb.append("}").toString();
    }
}
```

### Dependency Injection

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Autowired {}

class ServiceA {
    public String hello() { return "Hello from ServiceA"; }
}

class ServiceB {
    @Autowired
    private ServiceA serviceA;
    
    public String greet() {
        return serviceA.hello() + "!";
    }
}

// DI Container thực hiện injection bằng reflection
// Quét @Autowired fields -> tìm instance phù hợp -> inject
```

---

> **Tóm tắt:** Reflection API là công cụ mạnh mẽ cho phép kiểm tra và thay đổi cấu trúc chương trình tại runtime. Nó là nền tảng của nhiều framework quan trọng (Spring, Hibernate, JUnit). Tuy nhiên, cần cân nhắc về performance, security, và chỉ sử dụng khi thực sự cần thiết.
