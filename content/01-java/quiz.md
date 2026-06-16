# Quiz - Java

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Đâu là kiểu dữ liệu nguyên thủy (primitive type) trong Java?

- [ ] String
- [x] int
- [ ] Integer
- [ ] ArrayList

> **Giải thích:** `int` là kiểu nguyên thủy. `String`, `Integer`, `ArrayList` đều là kiểu tham chiếu (reference type). `Integer` là wrapper class của `int`.

## Câu 2

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
String s1 = "Hello";
String s2 = "Hello";
System.out.println(s1 == s2);
System.out.println(s1.equals(s2));
```

- [ ] false, false
- [ ] false, true
- [x] true, true
- [ ] true, false

> **Giải thích:** Cả hai biến `s1` và `s2` đều trỏ đến cùng một đối tượng trong String Pool, nên `==` trả về `true`. Phương thức `equals()` so sánh nội dung nên cũng trả về `true`.

## Câu 3

[TYPE: FILL_BLANK]

Để khai báo một hằng số trong Java, ta sử dụng từ khóa `___` trước kiểu dữ liệu.

- [ ] static
- [ ] const
- [x] final
- [ ] immutable

> **Giải thích:** Trong Java, từ khóa `final` được sử dụng để khai báo hằng số. Giá trị của biến `final` không thể thay đổi sau khi được gán.

## Câu 4

[TYPE: TRUE_FALSE]

Mệnh đề: "Trong Java, một class có thể kế thừa (extends) nhiều class cùng lúc."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Java chỉ hỗ trợ đơn kế thừa (single inheritance) cho class. Một class chỉ có thể extends một class duy nhất. Tuy nhiên, một class có thể implements nhiều interface.

## Câu 5

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
int[] arr = {1, 2, 3, 4, 5};
System.out.println(arr.length);
```

- [ ] 4
- [x] 5
- [ ] Lỗi biên dịch
- [ ] 0

> **Giải thích:** Mảng `arr` có 5 phần tử nên `arr.length` trả về 5. Lưu ý `length` là thuộc tính (field) của mảng, không phải phương thức.

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Phương thức nào được gọi khi tạo một đối tượng mới bằng từ khóa `new`?

- [ ] main()
- [x] Constructor
- [ ] finalize()
- [ ] init()

> **Giải thích:** Constructor là phương thức đặc biệt được gọi tự động khi tạo đối tượng mới. Constructor có cùng tên với class và không có kiểu trả về.

## Câu 7

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
List<Integer> list = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5));
Collections.sort(list);
System.out.println(list);
```

- [ ] [3, 1, 4, 1, 5]
- [x] [1, 1, 3, 4, 5]
- [ ] [5, 4, 3, 1, 1]
- [ ] Lỗi biên dịch

> **Giải thích:** `Collections.sort()` sắp xếp list theo thứ tự tăng dần mặc định. Kết quả là [1, 1, 3, 4, 5].

## Câu 8

[TYPE: FILL_BLANK]

Để xử lý ngoại lệ trong Java, ta đặt code có thể gây lỗi trong khối `___`.

- [x] try
- [ ] catch
- [ ] throw
- [ ] finally

> **Giải thích:** Khối `try` chứa code có thể phát sinh ngoại lệ. Khối `catch` xử lý ngoại lệ. `finally` chạy sau cùng dù có lỗi hay không. `throw` dùng để ném ngoại lệ.

## Câu 9

[TYPE: TRUE_FALSE]

Mệnh đề: "Interface trong Java có thể chứa phương thức có thân hàm (method body) kể từ Java 8."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Từ Java 8, interface có thể chứa `default methods` và `static methods` với thân hàm. Từ Java 9, interface còn có thể chứa `private methods`.

## Câu 10

[TYPE: MULTIPLE_CHOICE]

Từ khóa nào dùng để ngăn một class bị kế thừa?

- [ ] static
- [ ] abstract
- [x] final
- [ ] private

> **Giải thích:** Từ khóa `final` trước class ngăn class đó bị kế thừa. Ví dụ: `final class MyClass {}`. Lớp `String` trong Java là một ví dụ điển hình của final class.

## Câu 11

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
Map<String, Integer> map = new HashMap<>();
map.put("a", 1);
map.put("b", 2);
map.put("a", 3);
System.out.println(map.size());
System.out.println(map.get("a"));
```

- [ ] 3 và 1
- [ ] 3 và 3
- [x] 2 và 3
- [ ] 2 và 1

> **Giải thích:** HashMap không cho phép key trùng. Khi `put("a", 3)`, giá trị cũ (1) bị ghi đè bằng 3. Size = 2 vì chỉ có 2 key ("a", "b").

## Câu 12

[TYPE: FILL_BLANK]

Trong Java, từ khóa `___` được sử dụng để tham chiếu đến đối tượng hiện tại của class.

- [ ] super
- [x] this
- [ ] self
- [ ] me

> **Giải thích:** `this` tham chiếu đến đối tượng hiện tại. Thường dùng để phân biệt biến instance với tham số cùng tên, hoặc gọi constructor khác trong cùng class.

## Câu 13

[TYPE: TRUE_FALSE]

Mệnh đề: "ArrayList trong Java có thể chứa các phần tử kiểu nguyên thủy (primitive type) trực tiếp."

- [ ] Đúng
- [x] Sai

> **Giải thích:** ArrayList chỉ chứa đối tượng (Object), không chứa primitive trực tiếp. Khi viết `ArrayList<Integer>`, Java sử dụng autoboxing để chuyển `int` thành `Integer`.

## Câu 14

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
String str = "Java Programming";
System.out.println(str.substring(5, 11));
```

- [ ] Program
- [x] Progra
- [ ] aProg
- [ ] rogra

> **Giải thích:** `substring(5, 11)` lấy ký tự từ index 5 đến 10 (không bao gồm 11). "Java Programming" có index: J(0)a(1)v(2)a(3) (4)P(5)r(6)o(7)g(8)r(9)a(10)m(11). Kết quả: "Progra".

## Câu 15

[TYPE: MULTIPLE_CHOICE]

Stream API trong Java được giới thiệu từ phiên bản nào?

- [ ] Java 6
- [ ] Java 7
- [x] Java 8
- [ ] Java 11

> **Giải thích:** Stream API được giới thiệu trong Java 8 cùng với Lambda Expressions và Functional Interfaces.

## Câu 16

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
int x = 10;
int y = 3;
System.out.println(x / y);
System.out.println(x % y);
```

- [ ] 3.33 và 1
- [x] 3 và 1
- [ ] 3 và 0
- [ ] 3.33 và 0

> **Giải thích:** Phép chia hai số nguyên (`int / int`) cho kết quả nguyên (bỏ phần thập phân): 10/3 = 3. Phép chia lấy dư: 10%3 = 1.

## Câu 17

[TYPE: MULTIPLE_CHOICE]

Lớp nào là lớp cha (superclass) của tất cả các class trong Java?

- [ ] Class
- [x] Object
- [ ] Main
- [ ] Base

> **Giải thích:** `java.lang.Object` là lớp cha mặc định của tất cả các class trong Java. Mọi class đều kế thừa từ Object dù không khai báo `extends Object`.

## Câu 18

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
String s1 = new String("Hello");
String s2 = new String("Hello");
System.out.println(s1 == s2);
System.out.println(s1.equals(s2));
```

- [ ] true, true
- [x] false, true
- [ ] false, false
- [ ] true, false

> **Giải thích:** `new String()` tạo đối tượng mới trên heap, nên `s1` và `s2` là hai đối tượng khác nhau → `==` là `false`. `equals()` so sánh nội dung → `true`.

## Câu 19

[TYPE: FILL_BLANK]

Để chuyển kiểu từ `String` sang `int` trong Java, ta dùng phương thức `Integer.___("123")`.

- [ ] toInt
- [x] parseInt
- [ ] valueOf
- [ ] convert

> **Giải thích:** `Integer.parseInt("123")` chuyển String thành int primitive. `Integer.valueOf("123")` trả về Integer object. Cả hai đều parse nhưng trả về kiểu khác nhau.

## Câu 20

[TYPE: TRUE_FALSE]

Mệnh đề: "Biến `static` thuộc về đối tượng, mỗi đối tượng có một bản sao riêng."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Biến `static` thuộc về class, không thuộc về đối tượng. Tất cả các đối tượng của class chia sẻ chung một biến static.

## Câu 21

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
int a = 5;
int b = a++;
System.out.println("a=" + a + ", b=" + b);
```

- [ ] a=5, b=5
- [x] a=6, b=5
- [ ] a=6, b=6
- [ ] a=5, b=6

> **Giải thích:** `a++` là post-increment: trả về giá trị hiện tại (5) rồi mới tăng. Nên `b = 5`, sau đó `a` trở thành 6.

## Câu 22

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
int a = 5;
int b = ++a;
System.out.println("a=" + a + ", b=" + b);
```

- [ ] a=5, b=5
- [ ] a=6, b=5
- [x] a=6, b=6
- [ ] a=5, b=6

> **Giải thích:** `++a` là pre-increment: tăng giá trị trước (a=6) rồi mới trả về. Nên cả `a` và `b` đều bằng 6.

## Câu 23

[TYPE: MULTIPLE_CHOICE]

Access modifier nào cho phép truy cập từ mọi nơi?

- [ ] private
- [ ] protected
- [x] public
- [ ] default (package-private)

> **Giải thích:** `public` cho phép truy cập không giới hạn từ bất kỳ class, package nào. `private` chỉ trong cùng class, `protected` trong cùng package và subclass, `default` chỉ trong cùng package.

## Câu 24

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
StringBuilder sb = new StringBuilder("Hello");
sb.append(" World");
sb.insert(5, ",");
System.out.println(sb);
```

- [ ] Hello World
- [x] Hello, World
- [ ] Hello,World
- [ ] ,Hello World

> **Giải thích:** `append(" World")` → "Hello World". `insert(5, ",")` chèn dấu phẩy tại vị trí index 5 → "Hello, World".

## Câu 25

[TYPE: TRUE_FALSE]

Mệnh đề: "Trong Java, `switch` statement có thể dùng với kiểu `String` kể từ Java 7."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Từ Java 7, `switch` hỗ trợ `String`. Trước đó chỉ hỗ trợ `byte`, `short`, `char`, `int` và các wrapper tương ứng, cùng `enum`.

## Câu 26

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
double d = 0.1 + 0.2;
System.out.println(d == 0.3);
System.out.println(d);
```

- [ ] true và 0.3
- [x] false và 0.30000000000000004
- [ ] false và 0.3
- [ ] true và 0.30000000000000004

> **Giải thích:** Số thực dấu phẩy động (floating-point) có sai số biểu diễn. `0.1 + 0.2` không chính xác bằng `0.3` mà xấp xỉ `0.30000000000000004`.

## Câu 27

[TYPE: MULTIPLE_CHOICE]

Kiểu dữ liệu nào có kích thước 64 bit trong Java?

- [ ] int
- [ ] float
- [x] long
- [ ] short

> **Giải thích:** `long` chiếm 64 bit (8 bytes). `int` = 32 bit, `short` = 16 bit, `float` = 32 bit. `double` cũng là 64 bit.

## Câu 28

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
String[] arr = {"A", "B", "C", "D"};
System.out.println(arr[arr.length - 1]);
```

- [ ] A
- [ ] C
- [x] D
- [ ] ArrayIndexOutOfBoundsException

> **Giải thích:** `arr.length` = 4, nên `arr[4-1]` = `arr[3]` = "D". Index bắt đầu từ 0, nên phần tử cuối cùng có index = length - 1.

## Câu 29

[TYPE: FILL_BLANK]

Từ khóa `___` được dùng để khai báo một phương thức trừu tượng (không có thân hàm) trong class.

- [ ] virtual
- [x] abstract
- [ ] interface
- [ ] void

> **Giải thích:** `abstract` dùng để khai báo phương thức trừu tượng. Class chứa phương thức abstract cũng phải được khai báo là `abstract`.

## Câu 30

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
try {
    int result = 10 / 0;
    System.out.println("Result: " + result);
} catch (ArithmeticException e) {
    System.out.println("Error!");
} finally {
    System.out.println("Done");
}
```

- [ ] Result: 0 và Done
- [x] Error! và Done
- [ ] Chỉ Error!
- [ ] Chỉ Done

> **Giải thích:** Chia cho 0 ném `ArithmeticException`, catch xử lý in "Error!". `finally` luôn chạy nên in "Done".

## Câu 31

[TYPE: MULTIPLE_CHOICE]

Phương thức `equals()` mặc định trong class `Object` so sánh gì?

- [x] Tham chiếu (reference) của hai đối tượng
- [ ] Nội dung (content) của hai đối tượng
- [ ] Hashcode của hai đối tượng
- [ ] Kiểu dữ liệu của hai đối tượng

> **Giải thích:** Mặc định, `Object.equals()` so sánh tham chiếu (giống `==`). Các class như `String`, `Integer` override `equals()` để so sánh nội dung.

## Câu 32

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
int[] a = {1, 2, 3};
int[] b = a;
b[0] = 10;
System.out.println(a[0]);
```

- [x] 10
- [ ] 1
- [ ] Lỗi biên dịch
- [ ] 0

> **Giải thích:** Mảng là kiểu tham chiếu. `b = a` không tạo bản sao mà chỉ copy tham chiếu. Cả `a` và `b` trỏ đến cùng mảng → thay đổi qua `b` ảnh hưởng `a`.

## Câu 33

[TYPE: TRUE_FALSE]

Mệnh đề: "Phương thức `main()` trong Java phải luôn có access modifier là `public`."

- [x] Đúng
- [ ] Sai

> **Giải thích:** JVM yêu cầu `main()` phải là `public static void main(String[] args)`. Nếu không khai báo `public`, JVM không thể gọi phương thức từ bên ngoài class.

## Câu 34

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
for (int i = 0; i < 5; i++) {
    if (i == 3) break;
    System.out.print(i + " ");
}
```

- [x] 0 1 2
- [ ] 0 1 2 3
- [ ] 0 1 2 3 4
- [ ] 0 1 2 4

> **Giải thích:** Vòng lặp chạy từ i=0 đến i=4. Khi i=3, `break` dừng vòng lặp ngay lập tức. Kết quả in ra: 0 1 2.

## Câu 35

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
for (int i = 0; i < 5; i++) {
    if (i == 3) continue;
    System.out.print(i + " ");
}
```

- [ ] 0 1 2
- [ ] 0 1 2 3 4
- [x] 0 1 2 4
- [ ] 3

> **Giải thích:** Khi i=3, `continue` bỏ qua phần còn lại của vòng lặp hiện tại và chuyển sang lần lặp tiếp theo. Nên 3 bị bỏ qua, kết quả: 0 1 2 4.

## Câu 36

[TYPE: MULTIPLE_CHOICE]

Giá trị mặc định của một biến `boolean` instance trong Java là gì?

- [x] false
- [ ] true
- [ ] null
- [ ] 0

> **Giải thích:** Biến instance kiểu `boolean` có giá trị mặc định là `false`. Biến `int` mặc định là 0, biến object mặc định là `null`.

## Câu 37

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
String s = null;
System.out.println(s + " world");
```

- [ ] NullPointerException
- [x] null world
- [ ] world
- [ ] Lỗi biên dịch

> **Giải thích:** Trong phép nối chuỗi (+), `null` được chuyển thành String "null". Kết quả là "null world". Chỉ gọi method trên null mới ném NullPointerException.

## Câu 38

[TYPE: FILL_BLANK]

Từ khóa `___` dùng để kiểm tra xem một đối tượng có phải là instance của một class/interface hay không.

- [ ] typeof
- [x] instanceof
- [ ] isType
- [ ] classOf

> **Giải thích:** `instanceof` trả về `true` nếu đối tượng là instance của class/interface đó hoặc subclass của nó. Ví dụ: `"Hello" instanceof String` → `true`.

## Câu 39

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
char c = 'A';
int n = c;
System.out.println(n);
```

- [ ] A
- [x] 65
- [ ] Lỗi biên dịch
- [ ] 0

> **Giải thích:** `char` trong Java là kiểu số 16 bit (Unicode). Gán `char` cho `int` là widening conversion (tự động). 'A' có mã Unicode = 65.

## Câu 40

[TYPE: TRUE_FALSE]

Mệnh đề: "Trong Java, biến local (biến cục bộ trong method) được tự động khởi tạo giá trị mặc định."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Chỉ biến instance và biến static mới được tự động khởi tạo. Biến local phải được gán giá trị trước khi sử dụng, nếu không sẽ gây lỗi biên dịch.

## Câu 41

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
int x = 5;
System.out.println(x > 3 ? "Lớn" : "Nhỏ");
```

- [x] Lớn
- [ ] Nhỏ
- [ ] true
- [ ] 5

> **Giải thích:** Toán tử ba ngôi (ternary): `condition ? valueIfTrue : valueIfFalse`. `5 > 3` là `true` nên trả về "Lớn".

## Câu 42

[TYPE: MULTIPLE_CHOICE]

Phương thức nào dùng để lấy độ dài của một String trong Java?

- [ ] size()
- [x] length()
- [ ] count()
- [ ] getLength()

> **Giải thích:** `String.length()` trả về số ký tự. Lưu ý: mảng dùng `length` (thuộc tính, không có ngoặc), ArrayList dùng `size()`.

## Câu 43

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
Integer a = 127;
Integer b = 127;
Integer c = 128;
Integer d = 128;
System.out.println(a == b);
System.out.println(c == d);
```

- [ ] true, true
- [x] true, false
- [ ] false, false
- [ ] false, true

> **Giải thích:** Java cache các Integer từ -128 đến 127. Nên `a == b` là `true` (cùng object). Giá trị 128 nằm ngoài cache, tạo object mới → `c == d` là `false`.

## Câu 44

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
String s = "Hello World";
System.out.println(s.indexOf("World"));
System.out.println(s.indexOf("Java"));
```

- [x] 6 và -1
- [ ] 6 và 0
- [ ] 5 và -1
- [ ] 7 và -1

> **Giải thích:** `indexOf()` trả về vị trí đầu tiên tìm thấy substring, hoặc -1 nếu không tìm thấy. "World" bắt đầu tại index 6, "Java" không tồn tại → -1.

## Câu 45

[TYPE: MULTIPLE_CHOICE]

Đâu KHÔNG phải là từ khóa hợp lệ trong Java?

- [ ] synchronized
- [ ] volatile
- [x] goto (sử dụng được)
- [ ] transient

> **Giải thích:** `goto` là từ khóa dành riêng (reserved keyword) nhưng KHÔNG được sử dụng trong Java. Nó tồn tại để ngăn lập trình viên dùng làm tên biến.

## Câu 46

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
int[] arr = new int[3];
System.out.println(arr[0]);
System.out.println(arr[1]);
```

- [x] 0 và 0
- [ ] null và null
- [ ] Lỗi biên dịch
- [ ] ArrayIndexOutOfBoundsException

> **Giải thích:** Mảng `int` được tự động khởi tạo với giá trị mặc định `0`. Mảng object thì mặc định `null`, boolean mặc định `false`.

## Câu 47

[TYPE: TRUE_FALSE]

Mệnh đề: "Phương thức `static` có thể truy cập trực tiếp biến instance (non-static) của class."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Phương thức `static` thuộc về class, không có tham chiếu `this`. Không thể truy cập trực tiếp biến instance. Phải tạo đối tượng trước.

## Câu 48

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
String s1 = "abc";
String s2 = "ab" + "c";
System.out.println(s1 == s2);
```

- [x] true
- [ ] false
- [ ] Lỗi biên dịch
- [ ] NullPointerException

> **Giải thích:** Compiler tối ưu hóa nối chuỗi hằng thời gian biên dịch. `"ab" + "c"` được compile thành `"abc"` → cùng tham chiếu trong String Pool.

## Câu 49

[TYPE: MULTIPLE_CHOICE]

Khi nào garbage collector trong Java chạy?

- [ ] Khi gọi `System.gc()` thì chắc chắn chạy ngay
- [ ] Khi chương trình kết thúc
- [x] Khi JVM quyết định, không thể đảm bảo thời điểm chính xác
- [ ] Mỗi 60 giây

> **Giải thích:** Garbage collector do JVM quản lý. `System.gc()` chỉ là gợi ý, không đảm bảo GC sẽ chạy ngay. JVM tự quyết định dựa trên tình trạng bộ nhớ.

## Câu 50

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
System.out.println(1 + 2 + "3");
System.out.println("1" + 2 + 3);
```

- [ ] 54 và 54
- [x] 33 và 123
- [ ] 123 và 123
- [ ] 6 và 6

> **Giải thích:** `1 + 2 + "3"`: int + int = 3, rồi 3 + "3" = "33". `"1" + 2 + 3`: String + int = "12", rồi "12" + 3 = "123". Thứ tự tính từ trái sang phải.

## Câu 51

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
byte b = 127;
b++;
System.out.println(b);
```

- [ ] 128
- [x] -128
- [ ] Lỗi biên dịch
- [ ] 0

> **Giải thích:** `byte` có range từ -128 đến 127. Khi tăng 127 thêm 1, xảy ra overflow (tràn số) và quay vòng thành -128.

## Câu 52

[TYPE: MULTIPLE_CHOICE]

Phương thức `hashCode()` trả về kiểu dữ liệu gì?

- [x] int
- [ ] long
- [ ] String
- [ ] Object

> **Giải thích:** `hashCode()` trả về `int` (32-bit). Nó được dùng bởi các cấu trúc hash-based như HashMap, HashSet để xác định bucket lưu trữ.

## Câu 53

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
String s = "Hello";
s.toUpperCase();
System.out.println(s);
```

- [x] Hello
- [ ] HELLO
- [ ] hello
- [ ] Lỗi biên dịch

> **Giải thích:** String trong Java là immutable (bất biến). `toUpperCase()` trả về String MỚI, không thay đổi String gốc. Phải gán lại: `s = s.toUpperCase()`.

## Câu 54

[TYPE: TRUE_FALSE]

Mệnh đề: "Một enum trong Java có thể chứa constructor, method và field."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Enum trong Java mạnh hơn nhiều ngôn ngữ khác. Có thể chứa constructor (private), method, field, thậm chí implement interface.

## Câu 55

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
int x = 0;
while (x < 5) {
    x += 2;
}
System.out.println(x);
```

- [ ] 4
- [ ] 5
- [x] 6
- [ ] 8

> **Giải thích:** x: 0→2→4→6. Khi x=4 < 5 → x=6. Khi x=6, điều kiện 6 < 5 là false → thoát vòng lặp. In ra 6.

## Câu 56

[TYPE: MULTIPLE_CHOICE]

Đâu là cách khai báo mảng hợp lệ trong Java?

- [ ] int arr[] = new int[];
- [x] int[] arr = new int[5];
- [ ] int arr = new int[5];
- [ ] int[5] arr = new int[];

> **Giải thích:** Khai báo mảng cần chỉ định kích thước trong `new int[5]`. Có thể viết `int[] arr` hoặc `int arr[]`, nhưng kích thước phải ở vế phải.

## Câu 57

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
List<String> list = List.of("A", "B", "C");
list.add("D");
System.out.println(list);
```

- [ ] [A, B, C, D]
- [ ] [A, B, C]
- [x] UnsupportedOperationException
- [ ] Lỗi biên dịch

> **Giải thích:** `List.of()` (Java 9+) tạo immutable list. Gọi `add()` trên immutable list ném `UnsupportedOperationException`.

## Câu 58

[TYPE: FILL_BLANK]

Để so sánh hai chuỗi mà không phân biệt chữ hoa/thường, ta dùng phương thức `___`.

- [ ] equals()
- [x] equalsIgnoreCase()
- [ ] compareTo()
- [ ] matches()

> **Giải thích:** `equalsIgnoreCase()` so sánh nội dung String mà bỏ qua chữ hoa/thường. Ví dụ: `"Hello".equalsIgnoreCase("hello")` → `true`.

## Câu 59

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
Object obj = "Hello";
if (obj instanceof String s) {
    System.out.println(s.length());
}
```

- [x] 5
- [ ] Hello
- [ ] Lỗi biên dịch
- [ ] ClassCastException

> **Giải thích:** Pattern matching for instanceof (Java 16+). Nếu `obj` là String, tự động ép kiểu vào biến `s`. "Hello" có 5 ký tự.

## Câu 60

[TYPE: TRUE_FALSE]

Mệnh đề: "Java hỗ trợ đa kế thừa (multiple inheritance) thông qua interface."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Tuy class chỉ extend được 1 class, nhưng có thể implement nhiều interface → đạt được đa kế thừa kiểu (type) thông qua interface.

## Câu 61

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
var list = new ArrayList<String>();
list.add("Java");
list.add("Python");
System.out.println(list.getClass().getSimpleName());
```

- [x] ArrayList
- [ ] List
- [ ] Object
- [ ] Lỗi biên dịch

> **Giải thích:** `var` (Java 10+) suy luận kiểu. Ở đây kiểu là `ArrayList<String>`. `getClass().getSimpleName()` trả về tên class đơn giản.

## Câu 62

[TYPE: MULTIPLE_CHOICE]

Trong Java, kiểu nào có thể lưu trữ giá trị `null`?

- [ ] int
- [ ] boolean
- [x] Integer
- [ ] double

> **Giải thích:** Chỉ kiểu tham chiếu (reference type) mới lưu được `null`. `Integer` là wrapper class (reference type). Các kiểu primitive (`int`, `boolean`, `double`) không lưu được `null`.

## Câu 63

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
int[] a = {1, 2, 3};
System.out.println(Arrays.toString(a));
System.out.println(a);
```

- [ ] [1, 2, 3] và [1, 2, 3]
- [x] [1, 2, 3] và [I@...] (hashcode)
- [ ] Lỗi biên dịch
- [ ] {1, 2, 3} và {1, 2, 3}

> **Giải thích:** `Arrays.toString(a)` trả về dạng đọc được "[1, 2, 3]". In mảng trực tiếp (`System.out.println(a)`) in ra kiểu + hashcode như `[I@15db9742`.

## Câu 64

[TYPE: FILL_BLANK]

Trong Java, từ khóa `___` dùng để gọi constructor của class cha.

- [ ] this
- [x] super
- [ ] parent
- [ ] base

> **Giải thích:** `super()` gọi constructor của class cha. Phải đặt ở dòng đầu tiên trong constructor. `super.method()` gọi phương thức của class cha.

## Câu 65

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
int result = 0;
for (int i = 1; i <= 5; i++) {
    result += i;
}
System.out.println(result);
```

- [ ] 5
- [ ] 10
- [x] 15
- [ ] 20

> **Giải thích:** Tính tổng 1+2+3+4+5 = 15. Vòng lặp cộng dồn giá trị i từ 1 đến 5 vào biến result.

## Câu 66

[TYPE: TRUE_FALSE]

Mệnh đề: "Trong Java, `finally` block luôn được thực thi ngay cả khi có `return` trong `try` block."

- [x] Đúng
- [ ] Sai

> **Giải thích:** `finally` luôn chạy dù `try` hoặc `catch` có `return`, `break`, hay `continue`. Ngoại trừ khi gọi `System.exit()` hoặc JVM crash.

## Câu 67

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
String s = "ABCDE";
System.out.println(s.charAt(2));
System.out.println(s.length());
```

- [ ] B và 5
- [x] C và 5
- [ ] C và 4
- [ ] B và 4

> **Giải thích:** `charAt(2)` lấy ký tự tại index 2 (A=0, B=1, C=2) → 'C'. `length()` trả về 5 (5 ký tự).

## Câu 68

[TYPE: MULTIPLE_CHOICE]

Phương thức nào dùng để so sánh thứ tự tự nhiên giữa hai chuỗi?

- [ ] equals()
- [ ] ==
- [x] compareTo()
- [ ] compare()

> **Giải thích:** `compareTo()` so sánh theo thứ tự từ điển. Trả về: < 0 nếu nhỏ hơn, 0 nếu bằng, > 0 nếu lớn hơn. `"A".compareTo("B")` → số âm.

## Câu 69

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
int x = 10;
int y = 20;
int z = (x > y) ? x : y;
System.out.println(z);
```

- [ ] 10
- [x] 20
- [ ] true
- [ ] false

> **Giải thích:** `10 > 20` là `false` → toán tử ternary trả về giá trị sau dấu `:` là `y = 20`.

## Câu 70

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
Set<String> set = new HashSet<>();
set.add("A");
set.add("B");
set.add("A");
System.out.println(set.size());
```

- [ ] 3
- [x] 2
- [ ] 1
- [ ] Lỗi biên dịch

> **Giải thích:** `HashSet` không cho phép phần tử trùng. Thêm "A" lần hai bị bỏ qua. Size = 2 (chỉ có "A" và "B").

## Câu 71

[TYPE: TRUE_FALSE]

Mệnh đề: "Trong Java, class `String` là `final` nên không thể kế thừa."

- [x] Đúng
- [ ] Sai

> **Giải thích:** `String` được khai báo `public final class String`. Từ khóa `final` ngăn bất kỳ class nào kế thừa String, đảm bảo tính immutable và bảo mật.

## Câu 72

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
int[][] matrix = {{1, 2}, {3, 4}, {5, 6}};
System.out.println(matrix.length);
System.out.println(matrix[0].length);
```

- [x] 3 và 2
- [ ] 2 và 3
- [ ] 6 và 2
- [ ] 3 và 3

> **Giải thích:** `matrix.length` = 3 (3 hàng). `matrix[0].length` = 2 (mỗi hàng có 2 phần tử). Mảng 2 chiều là mảng của mảng.

## Câu 73

[TYPE: MULTIPLE_CHOICE]

Lệnh nào dùng để ném (throw) một ngoại lệ tùy chỉnh?

- [ ] throws new Exception()
- [x] throw new Exception()
- [ ] raise new Exception()
- [ ] exception new Exception()

> **Giải thích:** `throw` ném một exception. `throws` khai báo exception mà method có thể ném. `throw` dùng với instance, `throws` dùng trong method signature.

## Câu 74

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
int i = 0;
do {
    System.out.print(i + " ");
    i++;
} while (i < 3);
```

- [x] 0 1 2
- [ ] 1 2 3
- [ ] 0 1 2 3
- [ ] Không in gì

> **Giải thích:** `do-while` thực hiện ít nhất 1 lần. In 0, tăng i=1. In 1, tăng i=2. In 2, tăng i=3. Kiểm tra 3 < 3 = false → dừng.

## Câu 75

[TYPE: FILL_BLANK]

Trong Java, phương thức `___()` của class Thread dùng để bắt đầu chạy một thread mới.

- [ ] run
- [x] start
- [ ] execute
- [ ] begin

> **Giải thích:** `start()` tạo thread mới và gọi `run()`. Nếu gọi `run()` trực tiếp, code chạy trên thread hiện tại, KHÔNG tạo thread mới.

## Câu 76

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
Optional<String> opt = Optional.of("Hello");
System.out.println(opt.isPresent());
System.out.println(opt.get());
```

- [x] true và Hello
- [ ] false và null
- [ ] true và Optional[Hello]
- [ ] Lỗi biên dịch

> **Giải thích:** `Optional.of("Hello")` tạo Optional chứa giá trị. `isPresent()` → true. `get()` trả về giá trị bên trong → "Hello".

## Câu 77

[TYPE: MULTIPLE_CHOICE]

Kiểu dữ liệu `char` trong Java chiếm bao nhiêu byte?

- [ ] 1 byte
- [x] 2 bytes
- [ ] 4 bytes
- [ ] 8 bytes

> **Giải thích:** `char` trong Java dùng Unicode (UTF-16), chiếm 2 bytes (16 bits). Khác với C/C++ nơi `char` chiếm 1 byte.

## Câu 78

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
long count = nums.stream()
                 .filter(n -> n % 2 == 0)
                 .count();
System.out.println(count);
```

- [ ] 5
- [ ] 3
- [x] 2
- [ ] 0

> **Giải thích:** `filter(n -> n % 2 == 0)` giữ lại số chẵn (2, 4). `count()` đếm → 2.

## Câu 79

[TYPE: TRUE_FALSE]

Mệnh đề: "Trong Java, method overloading yêu cầu các phương thức phải có kiểu trả về khác nhau."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Method overloading yêu cầu khác nhau về danh sách tham số (số lượng, kiểu, hoặc thứ tự). Kiểu trả về KHÔNG được dùng để phân biệt overloading.

## Câu 80

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
Map<String, Integer> map = new HashMap<>();
map.put("x", 1);
System.out.println(map.getOrDefault("x", 0));
System.out.println(map.getOrDefault("y", 0));
```

- [x] 1 và 0
- [ ] 1 và null
- [ ] 1 và 1
- [ ] 0 và 0

> **Giải thích:** `getOrDefault(key, defaultValue)`: trả về value nếu key tồn tại, ngược lại trả defaultValue. "x" có → 1, "y" không có → 0.

## Câu 81

[TYPE: MULTIPLE_CHOICE]

Đâu là functional interface trong Java?

- [ ] Interface có nhiều abstract method
- [x] Interface có đúng một abstract method
- [ ] Interface không có method nào
- [ ] Interface có method static

> **Giải thích:** Functional interface có chính xác 1 abstract method. Có thể có default/static methods. Thường dùng annotation `@FunctionalInterface`. Ví dụ: `Runnable`, `Comparator`.

## Câu 82

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
String[] arr = {"Java", "Python", "Go"};
String result = String.join(" - ", arr);
System.out.println(result);
```

- [x] Java - Python - Go
- [ ] Java-Python-Go
- [ ] Java Python Go
- [ ] [Java, Python, Go]

> **Giải thích:** `String.join(delimiter, elements)` nối các phần tử bằng delimiter. Kết quả: "Java - Python - Go".

## Câu 83

[TYPE: FILL_BLANK]

Annotation `___` được dùng để đánh dấu một phương thức đang ghi đè phương thức của class cha.

- [x] @Override
- [ ] @Overload
- [ ] @Inherited
- [ ] @Replace

> **Giải thích:** `@Override` kiểm tra tại compile-time rằng phương thức đang override. Nếu signature không khớp, compiler báo lỗi.

## Câu 84

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
String s = "Hello World Hello";
System.out.println(s.replace("Hello", "Hi"));
System.out.println(s);
```

- [x] Hi World Hi và Hello World Hello
- [ ] Hi World Hello và Hi World Hello
- [ ] Hi World Hi và Hi World Hi
- [ ] Hello World Hello và Hello World Hello

> **Giải thích:** `replace()` thay thế TẤT CẢ các lần xuất hiện → "Hi World Hi". Nhưng String immutable nên `s` gốc không đổi.

## Câu 85

[TYPE: TRUE_FALSE]

Mệnh đề: "Trong Java, `==` so sánh giá trị cho kiểu primitive và tham chiếu cho kiểu reference."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Với primitive (`int`, `double`...), `==` so sánh giá trị. Với reference type (`String`, `Object`...), `==` so sánh tham chiếu (địa chỉ bộ nhớ).

## Câu 86

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
record Point(int x, int y) {}
Point p = new Point(3, 4);
System.out.println(p.x());
System.out.println(p);
```

- [x] 3 và Point[x=3, y=4]
- [ ] 3 và (3, 4)
- [ ] Lỗi biên dịch (Java cũ)
- [ ] 3 và Point@hashcode

> **Giải thích:** `record` (Java 14+) tự động tạo constructor, getter (tên trùng field), `toString()`, `equals()`, `hashCode()`. `p.x()` → 3, `toString()` → "Point[x=3, y=4]".

## Câu 87

[TYPE: MULTIPLE_CHOICE]

Khi nào nên dùng `StringBuilder` thay vì `String`?

- [ ] Khi chuỗi ngắn
- [x] Khi nối nhiều chuỗi trong vòng lặp
- [ ] Khi cần thread-safe
- [ ] Khi so sánh chuỗi

> **Giải thích:** `String` immutable, mỗi lần nối tạo object mới → chậm trong vòng lặp. `StringBuilder` mutable, nối chuỗi hiệu quả hơn. `StringBuffer` là thread-safe version.

## Câu 88

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
List<String> list = new ArrayList<>(List.of("C", "A", "B"));
Collections.sort(list);
System.out.println(list);
Collections.reverse(list);
System.out.println(list);
```

- [ ] [C, A, B] và [B, A, C]
- [x] [A, B, C] và [C, B, A]
- [ ] [A, B, C] và [A, B, C]
- [ ] [C, B, A] và [A, B, C]

> **Giải thích:** `sort()` → [A, B, C]. Sau đó `reverse()` đảo ngược → [C, B, A].

## Câu 89

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
int x = 5;
switch (x) {
    case 5:
        System.out.print("Five ");
    case 6:
        System.out.print("Six ");
    default:
        System.out.print("Default");
}
```

- [ ] Five
- [x] Five Six Default
- [ ] Five Default
- [ ] Five Six

> **Giải thích:** Không có `break` → xảy ra fall-through. Khi match case 5, tiếp tục chạy case 6 và default. Cần `break` để ngăn fall-through.

## Câu 90

[TYPE: MULTIPLE_CHOICE]

Đâu là cách tạo thread hợp lệ trong Java?

- [ ] Chỉ extends Thread
- [ ] Chỉ implements Runnable
- [x] Cả extends Thread và implements Runnable đều hợp lệ
- [ ] Chỉ dùng ExecutorService

> **Giải thích:** Có nhiều cách: extends `Thread`, implements `Runnable`, implements `Callable`, dùng `ExecutorService`, hoặc lambda expression.

## Câu 91

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
String s = "";
System.out.println(s.isEmpty());
System.out.println(s.length());
System.out.println(s == null);
```

- [x] true, 0, false
- [ ] false, 0, false
- [ ] true, 0, true
- [ ] NullPointerException

> **Giải thích:** `""` là chuỗi rỗng (không phải null). `isEmpty()` → true, `length()` → 0, `s == null` → false.

## Câu 92

[TYPE: TRUE_FALSE]

Mệnh đề: "Trong Java, `float` có độ chính xác cao hơn `double`."

- [ ] Đúng
- [x] Sai

> **Giải thích:** `double` (64 bit) có độ chính xác cao hơn `float` (32 bit). `double` có ~15-16 chữ số thập phân, `float` chỉ ~6-7.

## Câu 93

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
int[] arr = {5, 3, 8, 1, 9};
Arrays.sort(arr);
int idx = Arrays.binarySearch(arr, 8);
System.out.println(idx);
```

- [ ] 2
- [x] 3
- [ ] 4
- [ ] -1

> **Giải thích:** Sau sort: [1, 3, 5, 8, 9]. `binarySearch` tìm 8 tại index 3. Lưu ý: mảng PHẢI được sắp xếp trước khi dùng binarySearch.

## Câu 94

[TYPE: MULTIPLE_CHOICE]

Đâu KHÔNG phải là một loại inner class trong Java?

- [ ] Static nested class
- [ ] Member inner class
- [ ] Anonymous inner class
- [x] Abstract inner class

> **Giải thích:** 4 loại inner class: Static nested class, Member (non-static) inner class, Local inner class, Anonymous inner class. "Abstract inner class" không phải phân loại chuẩn.

## Câu 95

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
Predicate<Integer> isEven = n -> n % 2 == 0;
System.out.println(isEven.test(4));
System.out.println(isEven.test(7));
```

- [x] true và false
- [ ] false và true
- [ ] true và true
- [ ] Lỗi biên dịch

> **Giải thích:** `Predicate<T>` là functional interface với method `test(T t)` trả về boolean. 4 % 2 == 0 → true, 7 % 2 == 0 → false.

## Câu 96

[TYPE: FILL_BLANK]

Để khai báo biến chỉ đọc được từ cùng class, ta dùng access modifier `___`.

- [x] private
- [ ] protected
- [ ] public
- [ ] final

> **Giải thích:** `private` giới hạn truy cập chỉ trong cùng class. Không class nào khác (kể cả subclass) có thể truy cập trực tiếp.

## Câu 97

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
Map<String, List<Integer>> map = new HashMap<>();
map.computeIfAbsent("key", k -> new ArrayList<>()).add(1);
map.computeIfAbsent("key", k -> new ArrayList<>()).add(2);
System.out.println(map.get("key"));
```

- [x] [1, 2]
- [ ] [2]
- [ ] [1]
- [ ] null

> **Giải thích:** Lần 1: "key" chưa có → tạo ArrayList mới, add 1. Lần 2: "key" đã có → dùng ArrayList cũ, add 2. Kết quả: [1, 2].

## Câu 98

[TYPE: TRUE_FALSE]

Mệnh đề: "Sealed class (Java 17) cho phép giới hạn các class được phép kế thừa nó."

- [x] Đúng
- [ ] Sai

> **Giải thích:** `sealed class Shape permits Circle, Rectangle` chỉ cho phép Circle và Rectangle kế thừa. Các class khác không được phép extends Shape.

## Câu 99

[TYPE: SELECT_RESULT]

Đoạn code sau in ra kết quả gì?

```java
List<Integer> list = List.of(1, 2, 3, 4, 5);
int sum = list.stream()
              .reduce(0, Integer::sum);
System.out.println(sum);
```

- [ ] 0
- [x] 15
- [ ] 5
- [ ] Lỗi biên dịch

> **Giải thích:** `reduce(0, Integer::sum)` cộng dồn từ giá trị khởi tạo 0: 0+1+2+3+4+5 = 15.

## Câu 100

[TYPE: MULTIPLE_CHOICE]

Text block trong Java (""") được giới thiệu từ phiên bản nào?

- [ ] Java 11
- [ ] Java 12
- [x] Java 13 (preview), Java 15 (chính thức)
- [ ] Java 17

> **Giải thích:** Text blocks (""") xuất hiện preview từ Java 13, chính thức từ Java 15. Cho phép viết multi-line string dễ dàng hơn.
