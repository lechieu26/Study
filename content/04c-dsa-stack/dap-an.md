# Stack - Đáp Án Chi Tiết

## Bài 1: Kiểm Tra Dấu Ngoặc Hợp Lệ

### Cách 1: Stack
```java
public class ValidParentheses {
    public static boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        Map<Character, Character> pairs = Map.of(')', '(', '}', '{', ']', '[');
        for (char c : s.toCharArray()) {
            if (pairs.containsValue(c)) {
                stack.push(c);
            } else if (pairs.containsKey(c)) {
                if (stack.isEmpty() || stack.pop() != pairs.get(c)) return false;
            }
        }
        return stack.isEmpty();
    }
}
```

### Cách 2: Thay thế chuỗi (không tối ưu)
```java
public class ValidParentheses_Replace {
    public static boolean isValid(String s) {
        while (s.contains("()") || s.contains("{}") || s.contains("[]")) {
            s = s.replace("()", "").replace("{}", "").replace("[]", "");
        }
        return s.isEmpty();
    }
}
// Time: O(n²) — mỗi lần replace duyệt chuỗi
```

---

## Bài 2: Min Stack

### Cách 1: Hai Stack
```java
public class MinStack {
    private Deque<Integer> stack = new ArrayDeque<>();
    private Deque<Integer> minStack = new ArrayDeque<>();

    public void push(int val) {
        stack.push(val);
        if (minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        }
    }

    public void pop() {
        if (stack.pop().equals(minStack.peek())) minStack.pop();
    }

    public int top() { return stack.peek(); }
    public int getMin() { return minStack.peek(); }
}
```

### Cách 2: Lưu pair (value, currentMin)
```java
public class MinStack_Pair {
    private Deque<int[]> stack = new ArrayDeque<>();

    public void push(int val) {
        int min = stack.isEmpty() ? val : Math.min(val, stack.peek()[1]);
        stack.push(new int[]{val, min});
    }

    public void pop() { stack.pop(); }
    public int top() { return stack.peek()[0]; }
    public int getMin() { return stack.peek()[1]; }
}
```

---

## Bài 3: Daily Temperatures

### Cách 1: Monotonic Stack O(n)
```java
public class DailyTemperatures {
    public static int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
                int prevDay = stack.pop();
                result[prevDay] = i - prevDay;
            }
            stack.push(i);
        }
        return result;
    }
}
// Stack giữ index giảm dần theo nhiệt độ
// Khi gặp ngày nóng hơn, pop ra và tính khoảng cách
```

---

## Bài 4: Evaluate Reverse Polish Notation

### Cách 1: Stack
```java
public class EvalRPN {
    public static int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (String token : tokens) {
            switch (token) {
                case "+": { int b = stack.pop(), a = stack.pop(); stack.push(a + b); break; }
                case "-": { int b = stack.pop(), a = stack.pop(); stack.push(a - b); break; }
                case "*": { int b = stack.pop(), a = stack.pop(); stack.push(a * b); break; }
                case "/": { int b = stack.pop(), a = stack.pop(); stack.push(a / b); break; }
                default: stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }
}
```

---

## Bài 5: Largest Rectangle in Histogram

### Cách 1: Monotonic Stack O(n)
```java
public class LargestRectangle {
    public static int largestRectangleArea(int[] heights) {
        int n = heights.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int maxArea = 0;

        for (int i = 0; i <= n; i++) {
            int currHeight = (i == n) ? 0 : heights[i];
            while (!stack.isEmpty() && heights[stack.peek()] > currHeight) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            stack.push(i);
        }
        return maxArea;
    }
}
// Stack tăng dần. Khi gặp cột thấp hơn, pop ra và tính diện tích
// width = khoảng cách từ stack.peek() đến i
```

---

## Bài 6: Basic Calculator

### Cách 1: Stack xử lý dấu ngoặc
```java
public class BasicCalculator {
    public static int calculate(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        int result = 0, num = 0, sign = 1;

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            } else if (c == '+') {
                result += sign * num;
                num = 0;
                sign = 1;
            } else if (c == '-') {
                result += sign * num;
                num = 0;
                sign = -1;
            } else if (c == '(') {
                stack.push(result);
                stack.push(sign);
                result = 0;
                sign = 1;
            } else if (c == ')') {
                result += sign * num;
                num = 0;
                result *= stack.pop();  // sign trước ngoặc
                result += stack.pop();  // result trước ngoặc
            }
        }
        return result + sign * num;
    }
}
```
