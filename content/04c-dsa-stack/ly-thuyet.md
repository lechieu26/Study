# Stack (Ngăn xếp)

## 1. Khái niệm

**Stack** là cấu trúc dữ liệu hoạt động theo nguyên tắc **LIFO** (Last In, First Out) — phần tử vào sau sẽ ra trước.

```
        push(3)     push(7)     pop() → 7
          ↓           ↓           ↑
        +---+       +---+       +---+
top →   | 3 |  →    | 7 |  →    | 3 |   ← top
        +---+       +---+       +---+
                    | 3 |
                    +---+
```

**Ứng dụng thực tế:**
- **Undo/Redo** trong text editor
- **Call Stack** — quản lý lời gọi hàm
- **Browser Back/Forward**
- **Kiểm tra ngoặc** hợp lệ
- **DFS** (Depth-First Search)
- **Parse expression** (infix → postfix)
- **Monotonic Stack** — tìm next greater/smaller element

## 2. Các thao tác cơ bản

| Thao tác | Mô tả | Time |
|----------|--------|------|
| `push(x)` | Thêm x vào đỉnh | O(1) |
| `pop()` | Lấy và xóa phần tử đỉnh | O(1) |
| `peek()/top()` | Xem phần tử đỉnh (không xóa) | O(1) |
| `isEmpty()` | Kiểm tra rỗng | O(1) |
| `size()` | Số phần tử | O(1) |

## 3. Implement trong Java

```java
// ✅ Khuyến nghị: Dùng ArrayDeque thay vì Stack class
Deque<Integer> stack = new ArrayDeque<>();
stack.push(10);        // Thêm đỉnh
stack.push(20);
stack.push(30);
stack.peek();          // 30 — xem đỉnh, không xóa
stack.pop();           // 30 — lấy ra đỉnh
stack.isEmpty();       // false
stack.size();          // 2

// ❌ Không nên dùng java.util.Stack (legacy, synchronized, extends Vector)
Stack<Integer> legacyStack = new Stack<>();  // Cũ, chậm hơn
```

> **Tại sao ArrayDeque tốt hơn Stack?**
> - `Stack` extends `Vector` → synchronized → overhead không cần thiết
> - `ArrayDeque` dùng circular array → cache-friendly, nhanh hơn
> - `ArrayDeque` implement `Deque` → linh hoạt hơn

## 4. Tự implement Stack

```java
// Stack dùng Array
public class ArrayStack {
    private int[] data;
    private int top;

    public ArrayStack(int capacity) {
        data = new int[capacity];
        top = -1;
    }

    public void push(int val) {
        if (top == data.length - 1) throw new RuntimeException("Stack overflow");
        data[++top] = val;
    }

    public int pop() {
        if (isEmpty()) throw new RuntimeException("Stack underflow");
        return data[top--];
    }

    public int peek() {
        if (isEmpty()) throw new RuntimeException("Stack empty");
        return data[top];
    }

    public boolean isEmpty() { return top == -1; }
    public int size() { return top + 1; }
}
```

## 5. Các bài toán kinh điển

### 5.1 Valid Parentheses (Kiểm tra ngoặc hợp lệ)

```java
public boolean isValid(String s) {
    Deque<Character> stack = new ArrayDeque<>();
    Map<Character, Character> pairs = Map.of(')', '(', ']', '[', '}', '{');

    for (char c : s.toCharArray()) {
        if (pairs.containsValue(c)) {
            stack.push(c);  // Ngoặc mở → push
        } else if (pairs.containsKey(c)) {
            if (stack.isEmpty() || stack.pop() != pairs.get(c))
                return false;  // Không khớp hoặc stack rỗng
        }
    }
    return stack.isEmpty();  // Stack phải rỗng
}
// "({[]})" → true
// "([)]"  → false
// "(("    → false (stack không rỗng)
```

### 5.2 Monotonic Stack (Ngăn xếp đơn điệu)

Giữ stack luôn tăng hoặc giảm → giải quyết bài toán **next greater/smaller element** trong O(n).

```java
// Next Greater Element: Với mỗi phần tử, tìm phần tử lớn hơn gần nhất bên phải
public int[] nextGreaterElement(int[] nums) {
    int[] result = new int[nums.length];
    Arrays.fill(result, -1);
    Deque<Integer> stack = new ArrayDeque<>();  // Lưu index, giảm dần

    for (int i = 0; i < nums.length; i++) {
        // Pop tất cả phần tử nhỏ hơn nums[i]
        while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
            result[stack.pop()] = nums[i];
        }
        stack.push(i);
    }
    return result;
}
// nums = [2, 1, 2, 4, 3]
// result= [4, 2, 4,-1,-1]
```

```java
// Daily Temperatures: Tìm số ngày phải đợi để có nhiệt độ cao hơn
public int[] dailyTemperatures(int[] temperatures) {
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
// temps  = [73, 74, 75, 71, 69, 72, 76, 73]
// result = [ 1,  1,  4,  2,  1,  1,  0,  0]
```

### 5.3 Evaluate Reverse Polish Notation (Hậu tố)

```java
public int evalRPN(String[] tokens) {
    Deque<Integer> stack = new ArrayDeque<>();
    Set<String> ops = Set.of("+", "-", "*", "/");

    for (String token : tokens) {
        if (ops.contains(token)) {
            int b = stack.pop(), a = stack.pop();
            switch (token) {
                case "+" -> stack.push(a + b);
                case "-" -> stack.push(a - b);
                case "*" -> stack.push(a * b);
                case "/" -> stack.push(a / b);
            }
        } else {
            stack.push(Integer.parseInt(token));
        }
    }
    return stack.pop();
}
// ["2","1","+","3","*"] = (2+1)*3 = 9
```

### 5.4 Min Stack — Stack hỗ trợ getMin() O(1)

```java
public class MinStack {
    private Deque<Integer> stack = new ArrayDeque<>();
    private Deque<Integer> minStack = new ArrayDeque<>();

    public void push(int val) {
        stack.push(val);
        // minStack giữ min hiện tại
        if (minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        }
    }

    public void pop() {
        int val = stack.pop();
        if (val == minStack.peek()) minStack.pop();
    }

    public int top() { return stack.peek(); }
    public int getMin() { return minStack.peek(); }
}
```

### 5.5 Ứng dụng Stack trong DFS

```java
// DFS dùng Stack (iterative) thay vì đệ quy
public List<Integer> dfsIterative(Map<Integer, List<Integer>> graph, int start) {
    List<Integer> result = new ArrayList<>();
    Set<Integer> visited = new HashSet<>();
    Deque<Integer> stack = new ArrayDeque<>();

    stack.push(start);
    while (!stack.isEmpty()) {
        int node = stack.pop();
        if (visited.add(node)) {
            result.add(node);
            for (int neighbor : graph.getOrDefault(node, List.of())) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                }
            }
        }
    }
    return result;
}
```

## 6. Khi nào dùng Stack?

| Dùng Stack khi | Ví dụ |
|---------------|-------|
| Xử lý cặp ngoặc/matching | Valid Parentheses, HTML tag matching |
| Đánh giá biểu thức | Infix → Postfix, Calculator |
| DFS (iterative) | Duyệt đồ thị, duyệt cây |
| Undo/Redo | Text editor, browser history |
| Tìm next greater/smaller | Monotonic Stack problems |
| Quản lý trạng thái | Function call stack, backtracking |

> **Phỏng vấn thường hỏi:** Valid Parentheses, Min Stack, Daily Temperatures, Evaluate RPN, Largest Rectangle in Histogram, Trapping Rain Water (stack approach).
