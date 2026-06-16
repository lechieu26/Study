# Stack - Bài Tập

## Bài 1: Kiểm Tra Dấu Ngoặc Hợp Lệ
**Độ khó: Dễ**

Cho một chuỗi chỉ chứa các ký tự `(){}[]`, kiểm tra xem chuỗi có hợp lệ không.

**Ví dụ:**
```
Input:  "({[]})" → Output: true
Input:  "([)]"  → Output: false
```

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | "({[]})" | true |
| 2 | "([)]" | false |
| 3 | "()" | true |
| 4 | "" | true |
| 5 | "{[" | false |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(isValid("({[]})")), "true");
        check(2, String.valueOf(isValid("([)]")), "false");
        check(3, String.valueOf(isValid("()")), "true");
        check(4, String.valueOf(isValid("")), "true");
        check(5, String.valueOf(isValid("{[")), "false");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static boolean isValid(String s) {
        // Code here ...
        return false;
    }
}
```

---

## Bài 2: Min Stack
**Độ khó: Trung bình**

Thiết kế stack hỗ trợ `push`, `pop`, `top`, và `getMin` — tất cả đều O(1).

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | push(-2),push(0),push(-3),getMin | -3 |
| 2 | push(-2),push(0),push(-3),pop,top | 0 |
| 3 | push(-2),push(0),push(-3),pop,getMin | -2 |
| 4 | push(1),push(2),top | 2 |
| 5 | push(5),push(3),push(7),getMin | 3 |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    static int[] stackArr = new int[1000];
    static int[] minArr = new int[1000];
    static int top = -1;

    public static void main(String[] args) {
        // Test 1: push(-2),push(0),push(-3),getMin => -3
        top = -1;
        push(-2); push(0); push(-3);
        check(1, String.valueOf(getMin()), "-3");

        // Test 2: pop, top => 0
        pop();
        check(2, String.valueOf(top()), "0");

        // Test 3: getMin => -2
        check(3, String.valueOf(getMin()), "-2");

        // Test 4
        top = -1;
        push(1); push(2);
        check(4, String.valueOf(top()), "2");

        // Test 5
        top = -1;
        push(5); push(3); push(7);
        check(5, String.valueOf(getMin()), "3");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static void push(int val) {
        // Code here ...
    }

    public static void pop() {
        // Code here ...
    }

    public static int top() {
        // Code here ...
        return 0;
    }

    public static int getMin() {
        // Code here ...
        return 0;
    }
}
```

---

## Bài 3: Daily Temperatures
**Độ khó: Trung bình**

Cho mảng nhiệt độ, trả về mảng kết quả: `result[i]` = số ngày phải đợi để nhiệt độ cao hơn.

**Gợi ý:** Monotonic Stack (stack giảm dần).

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [73,74,75,71,69,72,76,73] | [1, 1, 4, 2, 1, 1, 0, 0] |
| 2 | [30,40,50,60] | [1, 1, 1, 0] |
| 3 | [30,60,90] | [1, 1, 0] |
| 4 | [90,80,70] | [0, 0, 0] |
| 5 | [55] | [0] |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, Arrays.toString(dailyTemperatures(new int[]{73,74,75,71,69,72,76,73})), "[1, 1, 4, 2, 1, 1, 0, 0]");
        check(2, Arrays.toString(dailyTemperatures(new int[]{30,40,50,60})), "[1, 1, 1, 0]");
        check(3, Arrays.toString(dailyTemperatures(new int[]{30,60,90})), "[1, 1, 0]");
        check(4, Arrays.toString(dailyTemperatures(new int[]{90,80,70})), "[0, 0, 0]");
        check(5, Arrays.toString(dailyTemperatures(new int[]{55})), "[0]");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int[] dailyTemperatures(int[] temperatures) {
        // Code here ...
        return new int[]{};
    }
}
```

---

## Bài 4: Evaluate Reverse Polish Notation
**Độ khó: Trung bình**

Tính giá trị biểu thức hậu tố (Reverse Polish Notation).

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | ["2","1","+","3","*"] | 9 |
| 2 | ["4","13","5","/","+"] | 6 |
| 3 | ["10","6","9","3","+","-11","*","/","*","17","+","5","+"] | 22 |
| 4 | ["3","4","+"] | 7 |
| 5 | ["5","1","2","+","4","*","+","3","-"] | 14 |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(evalRPN(new String[]{"2","1","+","3","*"})), "9");
        check(2, String.valueOf(evalRPN(new String[]{"4","13","5","/","+"})), "6");
        check(3, String.valueOf(evalRPN(new String[]{"10","6","9","3","+","-11","*","/","*","17","+","5","+"})), "22");
        check(4, String.valueOf(evalRPN(new String[]{"3","4","+"})), "7");
        check(5, String.valueOf(evalRPN(new String[]{"5","1","2","+","4","*","+","3","-"})), "14");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int evalRPN(String[] tokens) {
        // Code here ...
        return 0;
    }
}
```

---

## Bài 5: Largest Rectangle in Histogram
**Độ khó: Khó**

Cho mảng `heights` biểu diễn chiều cao các cột, tìm diện tích hình chữ nhật lớn nhất.

**Gợi ý:** Dùng Monotonic Stack.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [2,1,5,6,2,3] | 10 |
| 2 | [2,4] | 4 |
| 3 | [1] | 1 |
| 4 | [2,1,2] | 3 |
| 5 | [1,1,1,1] | 4 |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(largestRectangleArea(new int[]{2,1,5,6,2,3})), "10");
        check(2, String.valueOf(largestRectangleArea(new int[]{2,4})), "4");
        check(3, String.valueOf(largestRectangleArea(new int[]{1})), "1");
        check(4, String.valueOf(largestRectangleArea(new int[]{2,1,2})), "3");
        check(5, String.valueOf(largestRectangleArea(new int[]{1,1,1,1})), "4");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int largestRectangleArea(int[] heights) {
        // Code here ...
        return 0;
    }
}
```

---

## Bài 6: Basic Calculator
**Độ khó: Khó**

Tính giá trị biểu thức toán học chứa `+`, `-`, `(`, `)` và khoảng trắng.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | "(1+(4+5+2)-3)+(6+8)" | 23 |
| 2 | "1 + 1" | 2 |
| 3 | " 2-1 + 2 " | 3 |
| 4 | "-(3+4)+5" | -2 |
| 5 | "10 + (3 - 1)" | 12 |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(calculate("(1+(4+5+2)-3)+(6+8)")), "23");
        check(2, String.valueOf(calculate("1 + 1")), "2");
        check(3, String.valueOf(calculate(" 2-1 + 2 ")), "3");
        check(4, String.valueOf(calculate("-(3+4)+5")), "-2");
        check(5, String.valueOf(calculate("10 + (3 - 1)")), "12");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int calculate(String s) {
        // Code here ...
        return 0;
    }
}
```
