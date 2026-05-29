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

- [ ] Progra
- [x] Progra
- [ ] Programming
- [ ] aProg

**Đáp án: 0**

> **Giải thích:** `substring(5, 11)` lấy ký tự từ index 5 đến 10 (không bao gồm 11). "Java Programming" có index: J(0) a(1) v(2) a(3) (4)P(5)r(6)o(7)g(8)r(9)a(10)m(11)... Kết quả: "Progra".

## Câu 15

[TYPE: MULTIPLE_CHOICE]

Stream API trong Java được giới thiệu từ phiên bản nào?

- [ ] Java 6
- [ ] Java 7
- [x] Java 8
- [ ] Java 11

> **Giải thích:** Stream API được giới thiệu trong Java 8 cùng với Lambda Expressions, giúp xử lý collections theo phong cách functional programming.
