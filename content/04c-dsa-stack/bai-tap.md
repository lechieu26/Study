# Stack - Bài Tập

## Bài 1: Kiểm Tra Dấu Ngoặc Hợp Lệ
**Độ khó: Dễ**

Cho một chuỗi chỉ chứa các ký tự `(){}[]`, kiểm tra xem chuỗi có hợp lệ không.

**Đầu vào:** `"({[]})"` → **Đầu ra:** `true`
**Đầu vào:** `"([)]"` → **Đầu ra:** `false`

---

## Bài 2: Min Stack
**Độ khó: Trung bình**

Thiết kế stack hỗ trợ `push`, `pop`, `top`, và `getMin` — tất cả đều O(1).

**Ví dụ:**
```
MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin();   // -3
minStack.pop();
minStack.top();      // 0
minStack.getMin();   // -2
```

---

## Bài 3: Daily Temperatures
**Độ khó: Trung bình**

Cho mảng nhiệt độ hàng ngày, trả về mảng kết quả: `result[i]` = số ngày phải đợi để nhiệt độ cao hơn `temperatures[i]`. Nếu không có ngày nào cao hơn, `result[i] = 0`.

**Đầu vào:** `temperatures = [73,74,75,71,69,72,76,73]`
**Đầu ra:** `[1,1,4,2,1,1,0,0]`

**Gợi ý:** Monotonic Stack (stack giảm dần).

---

## Bài 4: Evaluate Reverse Polish Notation
**Độ khó: Trung bình**

Tính giá trị biểu thức hậu tố (Reverse Polish Notation).

**Đầu vào:** `tokens = ["2","1","+","3","*"]`
**Đầu ra:** `9` → (2 + 1) * 3 = 9

**Đầu vào:** `tokens = ["4","13","5","/","+"]`
**Đầu ra:** `6` → 4 + (13 / 5) = 6

---

## Bài 5: Largest Rectangle in Histogram
**Độ khó: Khó**

Cho mảng `heights` biểu diễn chiều cao các cột trong biểu đồ, tìm diện tích hình chữ nhật lớn nhất.

**Đầu vào:** `heights = [2,1,5,6,2,3]`
**Đầu ra:** `10` (hình chữ nhật cao 5, rộng 2: cột index 2 và 3)

**Gợi ý:** Dùng Monotonic Stack để tìm left/right boundary cho mỗi cột.

---

## Bài 6: Basic Calculator
**Độ khó: Khó**

Tính giá trị biểu thức toán học chứa `+`, `-`, `(`, `)` và khoảng trắng.

**Đầu vào:** `"(1+(4+5+2)-3)+(6+8)"`
**Đầu ra:** `23`

**Gợi ý:** Dùng Stack để xử lý dấu ngoặc.
