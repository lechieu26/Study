# JavaScript - Lý Thuyết Từ Cơ Bản Đến Nâng Cao

## Mục lục

1. [Giới thiệu về JavaScript](#1-giới-thiệu-về-javascript)
2. [Cài đặt và Môi trường Phát triển](#2-cài-đặt-và-môi-trường-phát-triển)
3. [Biến và Kiểu dữ liệu](#3-biến-và-kiểu-dữ-liệu)
4. [Toán tử](#4-toán-tử)
5. [Câu điều kiện](#5-câu-điều-kiện)
6. [Vòng lặp](#6-vòng-lặp)
7. [Hàm (Functions)](#7-hàm-functions)
8. [Mảng (Arrays)](#8-mảng-arrays)
9. [Object](#9-object)
10. [String Methods](#10-string-methods)
11. [Scope và Hoisting](#11-scope-và-hoisting)
12. [Closure](#12-closure)
13. [this Keyword](#13-this-keyword)
14. [Prototype và Kế thừa](#14-prototype-và-kế-thừa)
15. [ES6+ Features](#15-es6-features)
16. [Destructuring và Spread/Rest](#16-destructuring-và-spreadrest)
17. [Promise và Async/Await](#17-promise-và-asyncawait)
18. [Error Handling](#18-error-handling)
19. [DOM Manipulation](#19-dom-manipulation)
20. [Event Handling](#20-event-handling)
21. [Fetch API và AJAX](#21-fetch-api-và-ajax)
22. [Web Storage API](#22-web-storage-api)
23. [Regular Expressions](#23-regular-expressions)
24. [Module System](#24-module-system)
25. [Class (ES6)](#25-class-es6)
26. [Iterator và Generator](#26-iterator-và-generator)
27. [Symbol, Map, Set, WeakMap, WeakSet](#27-symbol-map-set-weakmap-weakset)
28. [Proxy và Reflect](#28-proxy-và-reflect)
29. [Web APIs](#29-web-apis)
30. [Best Practices và Design Patterns](#30-best-practices-và-design-patterns)
31. [Tổng kết](#31-tổng-kết)

---

## 1. Giới thiệu về JavaScript

### 1.1 JavaScript là gì?

JavaScript (JS) là ngôn ngữ lập trình **động** (dynamic), **đa mô hình** (multi-paradigm) - hỗ trợ **lập trình hướng đối tượng**, **lập trình hàm**, và **lập trình hướng sự kiện**. Đây là ngôn ngữ duy nhất chạy **native** trên mọi trình duyệt web.

### 1.2 Lịch sử phát triển

| Năm | Sự kiện |
|-----|---------|
| 1995 | Brendan Eich tạo ra JavaScript trong 10 ngày tại Netscape |
| 1997 | ECMAScript 1 - phiên bản chuẩn hóa đầu tiên |
| 2009 | ECMAScript 5 (ES5) - strict mode, JSON, Array methods |
| 2009 | Node.js ra đời - JavaScript chạy trên server |
| 2015 | ECMAScript 6 (ES6/ES2015) - let/const, arrow function, class, Promise, module |
| 2016 | ES2016 - Array.includes(), exponentiation operator |
| 2017 | ES2017 - async/await, Object.entries/values |
| 2020 | ES2020 - Optional chaining, Nullish coalescing, BigInt |
| 2022 | ES2022 - Top-level await, .at(), Object.hasOwn() |
| 2023 | ES2023 - Array findLast/findLastIndex, toSorted/toReversed/toSpliced |

### 1.3 Tại sao học JavaScript?

**1. Ngôn ngữ của Web:**
JavaScript là ngôn ngữ duy nhất chạy trên trình duyệt. Mọi trang web tương tác đều cần JavaScript.

**2. Full-Stack Development:**
Với Node.js, JavaScript có thể làm cả frontend lẫn backend.

**3. Hệ sinh thái khổng lồ:**
- **Frontend:** React, Vue, Angular, Svelte
- **Backend:** Node.js, Express, NestJS, Deno, Bun
- **Mobile:** React Native, Ionic, NativeScript
- **Desktop:** Electron (VS Code, Discord, Slack)
- **Database:** MongoDB (JavaScript-based query)

**4. Thị trường việc làm rộng lớn:**
JavaScript liên tục là ngôn ngữ phổ biến nhất trên Stack Overflow Survey.

### 1.4 JavaScript Engine

Mọi trình duyệt có một **JavaScript Engine** để thực thi code:

| Trình duyệt | Engine |
|-------------|--------|
| Chrome, Edge, Opera | V8 |
| Firefox | SpiderMonkey |
| Safari | JavaScriptCore (Nitro) |
| Node.js | V8 |

**Quá trình thực thi:**
```
Source Code -> Parser -> AST (Abstract Syntax Tree) -> Interpreter -> Bytecode
                                                           |
                                                    JIT Compiler -> Machine Code (tối ưu)
```

### 1.5 JavaScript vs Java

| Tiêu chí | JavaScript | Java |
|----------|-----------|------|
| Kiểu dữ liệu | Động (Dynamic typing) | Tĩnh (Static typing) |
| Chạy trên | Trình duyệt + Node.js | JVM |
| OOP | Prototype-based | Class-based |
| Biên dịch | Interpreted / JIT | Compiled to bytecode |
| Sử dụng chính | Web development | Enterprise, Android |

---

## 2. Cài đặt và Môi trường Phát triển

### 2.1 Chạy JavaScript trên trình duyệt

**Cách 1: Console của trình duyệt**
Mở Developer Tools (F12) -> Tab Console -> Nhập code trực tiếp.

**Cách 2: Nhúng vào file HTML**
```html
<!DOCTYPE html>
<html>
<head>
    <title>JavaScript Demo</title>
</head>
<body>
    <h1>Hello JavaScript</h1>

    <!-- Inline script -->
    <script>
        console.log("Hello từ inline script!");
    </script>

    <!-- External script -->
    <script src="app.js"></script>
</body>
</html>
```

### 2.2 Cài đặt Node.js

Node.js cho phép chạy JavaScript ngoài trình duyệt:

```bash
# Kiểm tra phiên bản
node --version    # v20.x.x
npm --version     # 10.x.x

# Chạy file JavaScript
node app.js

# Chạy REPL (tương tác)
node
> console.log("Hello")
Hello
```

### 2.3 IDE khuyên dùng

**Visual Studio Code** là lựa chọn phổ biến nhất:
- Extension: ESLint, Prettier, JavaScript (ES6) Code Snippets
- Debugger tích hợp
- IntelliSense / Autocomplete

### 2.4 Vị trí đặt thẻ `<script>`

```html
<!-- Cuối body (khuyên dùng) - DOM đã load xong -->
<body>
    <div id="app"></div>
    <script src="app.js"></script>
</body>

<!-- Hoặc dùng defer/async trong head -->
<head>
    <!-- defer: tải song song, chạy sau khi DOM parse xong -->
    <script src="app.js" defer></script>

    <!-- async: tải song song, chạy ngay khi tải xong -->
    <script src="analytics.js" async></script>
</head>
```

**So sánh defer vs async:**

| | defer | async |
|---|-------|-------|
| Tải | Song song với HTML parsing | Song song với HTML parsing |
| Chạy | Sau khi DOM parse xong | Ngay khi file tải xong |
| Thứ tự | Đảm bảo thứ tự | Không đảm bảo thứ tự |
| Dùng cho | App logic chính | Analytics, ads |

---

## 3. Biến và Kiểu dữ liệu

### 3.1 Khai báo biến

JavaScript có 3 cách khai báo biến:

```javascript
// var - function-scoped, có hoisting (ES5)
var name = "JavaScript";

// let - block-scoped, không hoisting (ES6+) - KHUYÊN DÙNG
let age = 28;
age = 29; // có thể gán lại

// const - block-scoped, không thể gán lại (ES6+) - KHUYÊN DÙNG
const PI = 3.14159;
// PI = 3.14; // TypeError: Assignment to constant variable
```

**So sánh var, let, const:**

| Đặc điểm | var | let | const |
|----------|-----|-----|-------|
| Scope | Function | Block | Block |
| Hoisting | Có (giá trị undefined) | Có (TDZ) | Có (TDZ) |
| Gán lại | Có | Có | Không |
| Khai báo lại | Có | Không | Không |
| Sử dụng | Tránh dùng | Biến thay đổi | Mặc định nên dùng |

**Temporal Dead Zone (TDZ):**
```javascript
console.log(a); // undefined (var được hoisted)
var a = 1;

console.log(b); // ReferenceError: Cannot access 'b' before initialization
let b = 2;
```

### 3.2 Kiểu dữ liệu (Data Types)

JavaScript có **8 kiểu dữ liệu** chia thành 2 nhóm:

**Primitive Types (Kiểu nguyên thủy - truyền theo giá trị):**

```javascript
// 1. Number - số nguyên và số thực
let integer = 42;
let float = 3.14;
let negative = -10;
let infinity = Infinity;
let notANumber = NaN;

// 2. String - chuỗi ký tự
let single = 'Hello';
let double = "World";
let template = `Hello ${single}`; // Template literal (ES6)

// 3. Boolean
let isTrue = true;
let isFalse = false;

// 4. undefined - biến đã khai báo nhưng chưa gán giá trị
let x;
console.log(x); // undefined

// 5. null - giá trị rỗng có chủ đích
let empty = null;

// 6. BigInt - số nguyên lớn (ES2020)
let bigNum = 9007199254740991n;
let anotherBig = BigInt("9007199254740992");

// 7. Symbol - giá trị duy nhất (ES6)
let sym1 = Symbol("id");
let sym2 = Symbol("id");
console.log(sym1 === sym2); // false - mỗi Symbol là duy nhất
```

**Reference Types (Kiểu tham chiếu - truyền theo tham chiếu):**

```javascript
// 8. Object - bao gồm: Object, Array, Function, Date, RegExp, Map, Set,...
let person = { name: "An", age: 25 };
let numbers = [1, 2, 3];
let greet = function() { return "Hi"; };
let today = new Date();
```

### 3.3 Kiểm tra kiểu dữ liệu

```javascript
typeof 42;           // "number"
typeof "hello";      // "string"
typeof true;         // "boolean"
typeof undefined;    // "undefined"
typeof null;         // "object"  !! Bug lịch sử của JS
typeof {};           // "object"
typeof [];           // "object"  - Array là object
typeof function(){}; // "function"
typeof Symbol();     // "symbol"
typeof 42n;          // "bigint"

// Kiểm tra Array
Array.isArray([1, 2]); // true
Array.isArray({});     // false

// Kiểm tra null
let val = null;
val === null; // true
```

### 3.4 Chuyển đổi kiểu (Type Conversion)

**Chuyển đổi tự động (Implicit Coercion):**
```javascript
// String + Number -> String (nối chuỗi)
"5" + 3;        // "53"
"5" + true;     // "5true"

// Các phép toán khác -> Number
"5" - 3;        // 2
"5" * 2;        // 10
"5" / 2;        // 2.5
true + 1;       // 2
false + 1;      // 1

// So sánh
"5" == 5;       // true  (có coercion)
"5" === 5;      // false (không coercion - SO SÁNH NGHIÊM NGẶT)
```

**Chuyển đổi thủ công (Explicit Conversion):**
```javascript
// Sang Number
Number("123");     // 123
Number("abc");     // NaN
Number(true);      // 1
Number(null);      // 0
Number(undefined); // NaN
parseInt("42px");  // 42
parseFloat("3.14abc"); // 3.14
+"123";            // 123 (unary plus)

// Sang String
String(123);       // "123"
String(true);      // "true"
String(null);      // "null"
(123).toString();  // "123"
`${123}`;          // "123"

// Sang Boolean
Boolean(0);        // false
Boolean("");       // false
Boolean(null);     // false
Boolean(undefined); // false
Boolean(NaN);      // false
Boolean("hello");  // true
Boolean(42);       // true
Boolean([]);       // true  !! Mảng rỗng là truthy
Boolean({});       // true  !! Object rỗng là truthy
!!value;           // Cách ngắn gọn !! (double NOT)
```

**Truthy và Falsy:**
```javascript
// Falsy values (chỉ có 8 giá trị):
false, 0, -0, 0n, "", null, undefined, NaN

// Tất cả các giá trị khác đều là truthy, kể cả:
// [], {}, "0", "false", function(){}, new Date()
```

---

## 4. Toán tử

### 4.1 Toán tử số học (Arithmetic)

```javascript
let a = 10, b = 3;
a + b;    // 13 - Cộng
a - b;    // 7  - Trừ
a * b;    // 30 - Nhân
a / b;    // 3.3333 - Chia
a % b;    // 1  - Chia lấy dư (Modulo)
a ** b;   // 1000 - Lũy thừa (ES2016)

// Toán tử tăng/giảm
let x = 5;
x++;   // Post-increment: trả về 5, rồi tăng lên 6
++x;   // Pre-increment: tăng lên 7, rồi trả về 7
x--;   // Post-decrement
--x;   // Pre-decrement
```

### 4.2 Toán tử gán (Assignment)

```javascript
let x = 10;
x += 5;    // x = x + 5   -> 15
x -= 3;    // x = x - 3   -> 12
x *= 2;    // x = x * 2   -> 24
x /= 4;    // x = x / 4   -> 6
x %= 4;    // x = x % 4   -> 2
x **= 3;   // x = x ** 3  -> 8

// Logical assignment (ES2021)
x ||= 5;   // x = x || 5  (gán nếu x là falsy)
x &&= 10;  // x = x && 10 (gán nếu x là truthy)
x ??= 7;   // x = x ?? 7  (gán nếu x là null/undefined)
```

### 4.3 Toán tử so sánh (Comparison)

```javascript
5 == "5";     // true  - So sánh LỎNG (có type coercion)
5 === "5";    // false - So sánh NGHIÊM NGẶT (không coercion) -> NÊN DÙNG
5 != "5";     // false
5 !== "5";    // true  -> NÊN DÙNG

10 > 5;       // true
10 >= 10;     // true
5 < 10;       // true
5 <= 5;       // true

// So sánh đặc biệt
null == undefined;  // true
null === undefined; // false
NaN == NaN;         // false !! NaN không bằng chính nó
Number.isNaN(NaN);  // true - cách kiểm tra đúng
```

### 4.4 Toán tử logic (Logical)

```javascript
// AND (&&) - trả về giá trị falsy đầu tiên, hoặc giá trị cuối
true && true;     // true
true && false;    // false
"hello" && 42;    // 42
0 && "hello";     // 0

// OR (||) - trả về giá trị truthy đầu tiên, hoặc giá trị cuối
false || true;    // true
0 || "default";   // "default"
"" || "fallback"; // "fallback"

// NOT (!)
!true;   // false
!0;      // true
!!"hello"; // true (double NOT = chuyển sang boolean)

// Nullish Coalescing (??) - chỉ kiểm tra null/undefined (ES2020)
null ?? "default";      // "default"
undefined ?? "default"; // "default"
0 ?? "default";         // 0  !! Khác với || (0 là falsy nhưng không phải null/undefined)
"" ?? "default";        // "" !! Chuỗi rỗng không phải null/undefined
```

### 4.5 Toán tử 3 ngôi (Ternary)

```javascript
let age = 20;
let status = age >= 18 ? "Người lớn" : "Trẻ em";
// Tương đương:
// if (age >= 18) status = "Người lớn";
// else status = "Trẻ em";

// Có thể lồng nhau (nhưng nên tránh để code rõ ràng)
let grade = score >= 90 ? "A"
          : score >= 80 ? "B"
          : score >= 70 ? "C"
          : "F";
```

### 4.6 Optional Chaining (?.) (ES2020)

```javascript
let user = {
    name: "An",
    address: {
        city: "Hà Nội"
    }
};

// Không có optional chaining - dễ gặp lỗi
// user.contact.phone; // TypeError: Cannot read properties of undefined

// Với optional chaining
user.contact?.phone;       // undefined (không lỗi)
user.getInfo?.();          // undefined (gọi method an toàn)
user.hobbies?.[0];         // undefined (truy cập array an toàn)

// Kết hợp với Nullish Coalescing
let city = user.address?.city ?? "Không rõ";  // "Hà Nội"
let phone = user.contact?.phone ?? "N/A";     // "N/A"
```

---

## 5. Câu điều kiện

### 5.1 if...else

```javascript
let score = 85;

if (score >= 90) {
    console.log("Xuất sắc");
} else if (score >= 80) {
    console.log("Giỏi");
} else if (score >= 70) {
    console.log("Khá");
} else if (score >= 60) {
    console.log("Trung bình");
} else {
    console.log("Yếu");
}
// Output: "Giỏi"
```

### 5.2 switch...case

```javascript
let day = new Date().getDay(); // 0-6

switch (day) {
    case 0:
        console.log("Chủ nhật");
        break;
    case 1:
        console.log("Thứ Hai");
        break;
    case 2:
        console.log("Thứ Ba");
        break;
    case 3:
        console.log("Thứ Tư");
        break;
    case 4:
        console.log("Thứ Năm");
        break;
    case 5:
        console.log("Thứ Sáu");
        break;
    case 6:
        console.log("Thứ Bảy");
        break;
    default:
        console.log("Không hợp lệ");
}

// Nhóm nhiều case (fall-through)
switch (day) {
    case 1: case 2: case 3: case 4: case 5:
        console.log("Ngày làm việc");
        break;
    case 0: case 6:
        console.log("Cuối tuần");
        break;
}
```

### 5.3 Các kỹ thuật điều kiện ngắn gọn

```javascript
// Short-circuit evaluation
let name = userName || "Khách";           // Fallback cho falsy
let name2 = userName ?? "Khách";          // Fallback cho null/undefined

// Guard clause (trả về sớm)
function divide(a, b) {
    if (b === 0) return "Không thể chia cho 0";
    return a / b;
}

// Object lookup thay thế switch
const dayName = {
    0: "Chủ nhật", 1: "Thứ Hai", 2: "Thứ Ba",
    3: "Thứ Tư", 4: "Thứ Năm", 5: "Thứ Sáu", 6: "Thứ Bảy"
};
console.log(dayName[new Date().getDay()]);
```

---

## 6. Vòng lặp

### 6.1 for

```javascript
// for cơ bản
for (let i = 0; i < 5; i++) {
    console.log(i); // 0, 1, 2, 3, 4
}

// Duyệt mảng với chỉ số
const fruits = ["Táo", "Cam", "Chuối"];
for (let i = 0; i < fruits.length; i++) {
    console.log(`${i}: ${fruits[i]}`);
}
```

### 6.2 while và do...while

```javascript
// while - kiểm tra điều kiện trước
let count = 0;
while (count < 3) {
    console.log(count); // 0, 1, 2
    count++;
}

// do...while - thực hiện ít nhất 1 lần
let num = 10;
do {
    console.log(num); // 10 (chạy 1 lần dù điều kiện sai)
    num++;
} while (num < 5);
```

### 6.3 for...of (ES6)

Duyệt qua **giá trị** của iterable (Array, String, Map, Set,...):

```javascript
const colors = ["Đỏ", "Xanh", "Vàng"];
for (const color of colors) {
    console.log(color); // "Đỏ", "Xanh", "Vàng"
}

// Duyệt String
for (const char of "Hello") {
    console.log(char); // "H", "e", "l", "l", "o"
}

// Với destructuring
const entries = [["name", "An"], ["age", 25]];
for (const [key, value] of entries) {
    console.log(`${key}: ${value}`);
}
```

### 6.4 for...in

Duyệt qua **key** (thuộc tính) của object:

```javascript
const person = { name: "An", age: 25, city: "HCM" };
for (const key in person) {
    console.log(`${key}: ${person[key]}`);
}
// "name: An", "age: 25", "city: HCM"

// LƯU Ý: Không nên dùng for...in với Array vì nó duyệt cả prototype properties
// và thứ tự không đảm bảo. Dùng for...of hoặc forEach cho Array.
```

### 6.5 break và continue

```javascript
// break - thoát khỏi vòng lặp
for (let i = 0; i < 10; i++) {
    if (i === 5) break;
    console.log(i); // 0, 1, 2, 3, 4
}

// continue - bỏ qua lần lặp hiện tại
for (let i = 0; i < 5; i++) {
    if (i === 2) continue;
    console.log(i); // 0, 1, 3, 4
}

// Label (nhãn) cho vòng lặp lồng nhau
outer: for (let i = 0; i < 3; i++) {
    for (let j = 0; j < 3; j++) {
        if (i === 1 && j === 1) break outer;
        console.log(i, j);
    }
}
// 0 0, 0 1, 0 2, 1 0
```

---

## 7. Hàm (Functions)

### 7.1 Function Declaration

```javascript
// Khai báo hàm - được hoisted (có thể gọi trước khi khai báo)
function greet(name) {
    return `Xin chào, ${name}!`;
}

console.log(greet("An")); // "Xin chào, An!"
```

### 7.2 Function Expression

```javascript
// Biểu thức hàm - KHÔNG được hoisted
const greet = function(name) {
    return `Xin chào, ${name}!`;
};

// Named function expression (hữu ích cho debugging)
const factorial = function fact(n) {
    return n <= 1 ? 1 : n * fact(n - 1);
};
```

### 7.3 Arrow Function (ES6)

```javascript
// Cú pháp ngắn gọn
const add = (a, b) => a + b;
const square = x => x * x;          // 1 tham số: bỏ ngoặc
const sayHi = () => "Hi!";          // 0 tham số: bắt buộc có ()
const getObj = () => ({ key: "value" }); // Trả về object: bọc trong ()

// Arrow function nhiều dòng
const calculate = (a, b) => {
    const sum = a + b;
    const product = a * b;
    return { sum, product };
};

// KHÁC BIỆT QUAN TRỌNG với regular function:
// 1. Không có 'this' riêng (kế thừa từ scope cha)
// 2. Không có 'arguments' object
// 3. Không thể dùng làm constructor (new)
// 4. Không có prototype
```

### 7.4 Tham số mặc định (Default Parameters)

```javascript
function createUser(name, role = "user", active = true) {
    return { name, role, active };
}

createUser("An");              // { name: "An", role: "user", active: true }
createUser("An", "admin");     // { name: "An", role: "admin", active: true }
createUser("An", undefined, false); // { name: "An", role: "user", active: false }
```

### 7.5 Rest Parameters

```javascript
function sum(...numbers) {
    return numbers.reduce((total, n) => total + n, 0);
}
sum(1, 2, 3);     // 6
sum(1, 2, 3, 4, 5); // 15

// Kết hợp với tham số bình thường
function log(level, ...messages) {
    messages.forEach(msg => console.log(`[${level}] ${msg}`));
}
log("INFO", "Server started", "Port 3000");
```

### 7.6 IIFE (Immediately Invoked Function Expression)

```javascript
// Hàm tự gọi - chạy ngay khi định nghĩa
(function() {
    let secret = "private";
    console.log("IIFE chạy ngay!");
})();

// Arrow IIFE
(() => {
    console.log("Arrow IIFE");
})();

// IIFE với tham số
((name) => {
    console.log(`Hello ${name}`);
})("JavaScript");
```

### 7.7 Callback Function

```javascript
// Hàm truyền như tham số cho hàm khác
function processArray(arr, callback) {
    const result = [];
    for (const item of arr) {
        result.push(callback(item));
    }
    return result;
}

const doubled = processArray([1, 2, 3], x => x * 2);
// [2, 4, 6]

// Callback trong thực tế
setTimeout(() => console.log("Sau 1 giây"), 1000);

document.getElementById("btn").addEventListener("click", function(event) {
    console.log("Button được click!");
});
```

### 7.8 Higher-Order Functions

```javascript
// Hàm nhận hàm khác làm tham số HOẶC trả về một hàm
function multiplier(factor) {
    return function(number) {
        return number * factor;
    };
}

const double = multiplier(2);
const triple = multiplier(3);

double(5);  // 10
triple(5);  // 15
```

### 7.9 Pure Functions

```javascript
// Pure function: cùng input -> cùng output, không side effects
function add(a, b) {
    return a + b; // Luôn trả về kết quả giống nhau với cùng a, b
}

// Impure function: có side effect (thay đổi biến bên ngoài)
let total = 0;
function addToTotal(value) {
    total += value; // Side effect: thay đổi biến bên ngoài
    return total;
}
```

---

## 8. Mảng (Arrays)

### 8.1 Tạo mảng

```javascript
// Cách 1: Array literal (khuyên dùng)
const fruits = ["Táo", "Cam", "Chuối"];

// Cách 2: Array constructor
const numbers = new Array(1, 2, 3);
const empty = new Array(5); // Tạo mảng có length = 5 (không có phần tử)

// Cách 3: Array.from()
const chars = Array.from("Hello"); // ["H", "e", "l", "l", "o"]
const range = Array.from({ length: 5 }, (_, i) => i + 1); // [1, 2, 3, 4, 5]

// Cách 4: Array.of()
const arr = Array.of(1, 2, 3); // [1, 2, 3]
```

### 8.2 Truy cập và Sửa đổi

```javascript
const arr = ["a", "b", "c", "d", "e"];

arr[0];          // "a" - phần tử đầu
arr[arr.length - 1]; // "e" - phần tử cuối
arr.at(-1);      // "e" - phần tử cuối (ES2022)
arr.at(-2);      // "d" - phần tử kế cuối

arr[1] = "B";    // Sửa phần tử: ["a", "B", "c", "d", "e"]
```

### 8.3 Các phương thức biến đổi (Mutating Methods)

```javascript
const arr = [1, 2, 3];

// Thêm/Xóa ở cuối
arr.push(4);       // [1, 2, 3, 4] - trả về length mới
arr.pop();         // [1, 2, 3] - trả về phần tử bị xóa (4)

// Thêm/Xóa ở đầu
arr.unshift(0);    // [0, 1, 2, 3] - trả về length mới
arr.shift();       // [1, 2, 3] - trả về phần tử bị xóa (0)

// splice(start, deleteCount, ...items)
arr.splice(1, 1);        // [1, 3] - xóa 1 phần tử tại index 1
arr.splice(1, 0, 2);     // [1, 2, 3] - chèn 2 tại index 1
arr.splice(1, 1, "a", "b"); // [1, "a", "b", 3] - thay thế và chèn

// Sắp xếp
[3, 1, 2].sort();             // [1, 2, 3] - sắp xếp mặc định (theo string)
[10, 2, 30].sort();           // [10, 2, 30] !! Sai vì so sánh theo string
[10, 2, 30].sort((a, b) => a - b); // [2, 10, 30] - sắp xếp số tăng dần
[10, 2, 30].sort((a, b) => b - a); // [30, 10, 2] - sắp xếp số giảm dần

// Đảo ngược
[1, 2, 3].reverse(); // [3, 2, 1]

// fill(value, start, end)
[1, 2, 3, 4].fill(0, 1, 3); // [1, 0, 0, 4]
```

### 8.4 Các phương thức không biến đổi (Non-Mutating Methods)

```javascript
const arr = [1, 2, 3, 4, 5];

// concat
arr.concat([6, 7]); // [1, 2, 3, 4, 5, 6, 7]

// slice(start, end) - lấy mảng con
arr.slice(1, 3);    // [2, 3] (không bao gồm index 3)
arr.slice(-2);      // [4, 5] (2 phần tử cuối)

// includes
arr.includes(3);    // true

// indexOf / lastIndexOf
arr.indexOf(3);     // 2
arr.lastIndexOf(3); // 2

// join
arr.join("-");      // "1-2-3-4-5"

// flat (ES2019)
[1, [2, [3, [4]]]].flat();     // [1, 2, [3, [4]]] - làm phẳng 1 cấp
[1, [2, [3, [4]]]].flat(Infinity); // [1, 2, 3, 4] - làm phẳng hoàn toàn

// Phương thức mới không biến đổi (ES2023)
arr.toSorted((a, b) => b - a);   // [5, 4, 3, 2, 1] - arr không đổi
arr.toReversed();                  // [5, 4, 3, 2, 1] - arr không đổi
arr.toSpliced(1, 2, 10, 20);     // [1, 10, 20, 4, 5] - arr không đổi
arr.with(2, 99);                   // [1, 2, 99, 4, 5] - arr không đổi
```

### 8.5 Iteration Methods (Phương thức lặp)

```javascript
const numbers = [1, 2, 3, 4, 5];

// forEach - lặp qua từng phần tử (không trả về gì)
numbers.forEach((value, index) => {
    console.log(`${index}: ${value}`);
});

// map - biến đổi mỗi phần tử -> mảng mới
const doubled = numbers.map(n => n * 2);
// [2, 4, 6, 8, 10]

// filter - lọc phần tử thỏa điều kiện -> mảng mới
const evens = numbers.filter(n => n % 2 === 0);
// [2, 4]

// reduce - gom mảng thành 1 giá trị
const sum = numbers.reduce((acc, curr) => acc + curr, 0);
// 15

// find - tìm phần tử đầu tiên thỏa điều kiện
const found = numbers.find(n => n > 3);
// 4

// findIndex - tìm vị trí phần tử đầu tiên thỏa điều kiện
const index = numbers.findIndex(n => n > 3);
// 3

// findLast / findLastIndex (ES2023)
[1, 2, 3, 4, 3].findLast(n => n > 2);      // 3
[1, 2, 3, 4, 3].findLastIndex(n => n > 2);  // 4

// some - có ít nhất 1 phần tử thỏa điều kiện?
numbers.some(n => n > 4);  // true

// every - tất cả phần tử thỏa điều kiện?
numbers.every(n => n > 0); // true

// flatMap - map + flat (1 cấp)
[1, 2, 3].flatMap(n => [n, n * 2]);
// [1, 2, 2, 4, 3, 6]
```

### 8.6 Ví dụ thực tế kết hợp các Array methods

```javascript
const students = [
    { name: "An", score: 85, class: "A" },
    { name: "Bình", score: 72, class: "B" },
    { name: "Cường", score: 95, class: "A" },
    { name: "Dũng", score: 60, class: "B" },
    { name: "Em", score: 88, class: "A" }
];

// Tìm sinh viên giỏi nhất lớp A
const bestInA = students
    .filter(s => s.class === "A")
    .sort((a, b) => b.score - a.score)
    [0];
// { name: "Cường", score: 95, class: "A" }

// Tính điểm trung bình
const avgScore = students.reduce((sum, s) => sum + s.score, 0) / students.length;
// 80

// Nhóm theo lớp
const byClass = students.reduce((groups, s) => {
    (groups[s.class] ||= []).push(s);
    return groups;
}, {});
// { A: [...], B: [...] }

// Chuyển đổi thành object { name: score }
const scoreMap = Object.fromEntries(
    students.map(s => [s.name, s.score])
);
// { An: 85, Bình: 72, Cường: 95, Dũng: 60, Em: 88 }
```

---

## 9. Object

### 9.1 Tạo Object

```javascript
// Cách 1: Object literal (phổ biến nhất)
const person = {
    name: "An",
    age: 25,
    "favorite color": "blue", // Key có dấu cách -> dùng ngoặc kép
    greet() {
        return `Xin chào, tôi là ${this.name}`;
    }
};

// Cách 2: Constructor function
function Person(name, age) {
    this.name = name;
    this.age = age;
}
const person2 = new Person("Bình", 30);

// Cách 3: Object.create()
const proto = { greet() { return "Hi"; } };
const obj = Object.create(proto);

// Cách 4: Class (ES6) - xem phần Class
```

### 9.2 Truy cập và Sửa đổi

```javascript
const person = { name: "An", age: 25 };

// Dot notation
person.name;          // "An"
person.age = 26;      // Sửa đổi

// Bracket notation (dùng khi key là biến hoặc có ký tự đặc biệt)
person["name"];        // "An"
person["favorite color"]; // Truy cập key có dấu cách

// Computed property names
const key = "email";
person[key] = "an@mail.com";

// Xóa thuộc tính
delete person.age;
```

### 9.3 Object Methods (Phương thức tĩnh)

```javascript
const person = { name: "An", age: 25, city: "HCM" };

// Lấy danh sách keys
Object.keys(person);    // ["name", "age", "city"]

// Lấy danh sách values
Object.values(person);  // ["An", 25, "HCM"]

// Lấy danh sách [key, value] pairs
Object.entries(person); // [["name","An"], ["age",25], ["city","HCM"]]

// Tạo object từ entries
Object.fromEntries([["a", 1], ["b", 2]]); // { a: 1, b: 2 }

// Copy / Merge objects (shallow copy)
const copy = Object.assign({}, person);
const merged = Object.assign({}, person, { email: "an@mail.com" });

// Spread operator (ES6) - cách khuyên dùng
const copy2 = { ...person };
const merged2 = { ...person, email: "an@mail.com" };

// Đóng băng object
Object.freeze(person);       // Không thể thêm/sửa/xoa (shallow)
Object.isFrozen(person);     // true

Object.seal(person);         // Có thể sửa, không thể thêm/xóa
Object.isSealed(person);     // true

// Kiểm tra thuộc tính
"name" in person;                    // true
person.hasOwnProperty("name");       // true
Object.hasOwn(person, "name");       // true (ES2022 - khuyên dùng)
```

### 9.4 Destructuring Object

```javascript
const person = { name: "An", age: 25, city: "HCM", country: "VN" };

// Destructuring cơ bản
const { name, age } = person;
console.log(name, age); // "An" 25

// Đổi tên biến
const { name: fullName, age: years } = person;

// Giá trị mặc định
const { name, phone = "N/A" } = person;

// Rest pattern
const { name, ...rest } = person;
// rest = { age: 25, city: "HCM", country: "VN" }

// Nested destructuring
const user = {
    id: 1,
    info: { name: "An", address: { city: "HCM" } }
};
const { info: { name, address: { city } } } = user;
```

### 9.5 Property Shorthand và Computed Properties

```javascript
const name = "An";
const age = 25;

// Property shorthand
const person = { name, age }; // { name: "An", age: 25 }

// Method shorthand
const obj = {
    greet() { return "Hi"; },       // Thay vì greet: function() {}
    get fullName() { return "An"; }, // Getter
    set fullName(val) { /* ... */ }  // Setter
};

// Computed property names
const prop = "name";
const obj2 = {
    [prop]: "An",              // { name: "An" }
    [`get${prop}`]: () => "An" // { getName: () => "An" }
};
```

---

## 10. String Methods

### 10.1 Tạo String

```javascript
const s1 = 'Single quotes';
const s2 = "Double quotes";
const s3 = `Template literal ${s1}`; // Có thể nhúng biểu thức

// Multi-line string
const multiLine = `Dong 1
Dong 2
Dong 3`;
```

### 10.2 Các phương thức String quan trọng

```javascript
const str = "Hello, JavaScript World!";

// Tìm kiếm
str.indexOf("JavaScript");     // 7
str.lastIndexOf("o");          // 19
str.includes("Java");          // true
str.startsWith("Hello");       // true
str.endsWith("!");             // true
str.search(/java/i);           // 7 (regex, không phân biệt hoa thường)

// Truy cập
str.charAt(0);                 // "H"
str[0];                        // "H"
str.at(-1);                    // "!" (ES2022)
str.charCodeAt(0);             // 72 (mã ASCII)

// Cắt chuỗi
str.slice(7, 17);              // "JavaScript"
str.slice(-6);                 // "orld!"
str.substring(7, 17);          // "JavaScript"

// Thay thế
str.replace("World", "VN");     // "Hello, JavaScript VN!" (thay 1 lần)
str.replaceAll("l", "L");       // "HeLLo, JavaScript WorLd!"
str.replace(/[aeiou]/g, "*");   // "H*ll*, J*v*Scr*pt W*rld!" (regex)

// Chuyển đổi
str.toUpperCase();              // "HELLO, JAVASCRIPT WORLD!"
str.toLowerCase();              // "hello, javascript world!"

// Cắt khoảng trắng
"  Hello  ".trim();            // "Hello"
"  Hello  ".trimStart();       // "Hello  "
"  Hello  ".trimEnd();         // "  Hello"

// Tách và nối
"a-b-c".split("-");            // ["a", "b", "c"]
["a", "b", "c"].join("-");     // "a-b-c"

// Lặp lại
"Ha".repeat(3);                // "HaHaHa"

// Đệm độ dài
"Hello".padStart(10, "*");     // "*****Hello"
"Hello".padEnd(10, "-");       // "Hello-----"
```

### 10.3 Template Literals (ES6)

```javascript
const name = "An";
const age = 25;

// Nhúng biểu thức
const msg = `${name} là ${age} tuổi, năm sau là ${age + 1} tuổi`;

// Multi-line
const html = `
<div class="card">
    <h2>${name}</h2>
    <p>Age: ${age}</p>
</div>
`;

// Tagged template literals
function highlight(strings, ...values) {
    return strings.reduce((result, str, i) => {
        const value = values[i] ? `<b>${values[i]}</b>` : "";
        return result + str + value;
    }, "");
}
const output = highlight`Xin chào ${name}, bạn ${age} tuổi`;
// "Xin chào <b>An</b>, bạn <b>25</b> tuổi"
```


## 11. Scope và Hoisting

### 11.1 Các loại Scope

```javascript
// 1. Global Scope - truy cập được ở mọi nơi
var globalVar = "global";
let globalLet = "global";

// 2. Function Scope - chỉ truy cập trong hàm
function myFunc() {
    var localVar = "local"; // Chỉ tồn tại trong hàm
    console.log(globalVar);  // OK - truy cập được global
}
// console.log(localVar); // ReferenceError

// 3. Block Scope - chỉ truy cập trong block {}
if (true) {
    let blockLet = "block";
    const blockConst = "block";
    var blockVar = "NOT block scoped!"; // var không có block scope!
}
// console.log(blockLet);   // ReferenceError
// console.log(blockConst); // ReferenceError
console.log(blockVar);      // "NOT block scoped!" - var thoát khỏi block

// 4. Module Scope (ES6 Modules)
// Mỗi file module có scope riêng, không ở global
```

### 11.2 Hoisting

JavaScript "nâng" các khai báo lên đầu scope trước khi thực thi:

```javascript
// --- var hoisting ---
console.log(x); // undefined (khai báo được hoisted, không phải giá trị)
var x = 5;
// Tương đương:
// var x;
// console.log(x); // undefined
// x = 5;

// --- Function declaration hoisting ---
sayHi(); // "Hi!" - gọi trước khi khai báo được!
function sayHi() {
    console.log("Hi!");
}

// --- let/const - Temporal Dead Zone (TDZ) ---
// console.log(y); // ReferenceError: Cannot access 'y' before initialization
let y = 10;

// --- Function expression KHÔNG được hoisted ---
// greet(); // TypeError: greet is not a function
var greet = function() {
    console.log("Hello");
};
```

### 11.3 Scope Chain

```javascript
const global = "I am global";

function outer() {
    const outerVar = "I am outer";

    function inner() {
        const innerVar = "I am inner";
        console.log(innerVar);  // OK - scope hiện tại
        console.log(outerVar);  // OK - scope cha
        console.log(global);    // OK - global scope
    }

    inner();
    // console.log(innerVar); // ReferenceError - không truy cập được scope con
}

outer();
```

---

## 12. Closure

### 12.1 Closure là gì?

Closure là một hàm có thể **nhớ và truy cập** các biến từ scope bên ngoài (lexical scope) ngay cả khi hàm đó được thực thi ở nơi khác.

```javascript
function createCounter() {
    let count = 0; // Biến này được "đóng lại" (enclosed) trong closure

    return {
        increment() { return ++count; },
        decrement() { return --count; },
        getCount() { return count; }
    };
}

const counter = createCounter();
counter.increment(); // 1
counter.increment(); // 2
counter.decrement(); // 1
counter.getCount();  // 1

// `count` vẫn tồn tại và được cập nhật dù createCounter() đã chạy xong
// Không thể truy cập count trực tiếp từ bên ngoài -> ENCAPSULATION
```

### 12.2 Ứng dụng của Closure

```javascript
// 1. Data Privacy / Encapsulation
function createBankAccount(initialBalance) {
    let balance = initialBalance;

    return {
        deposit(amount) {
            if (amount > 0) balance += amount;
            return balance;
        },
        withdraw(amount) {
            if (amount > 0 && amount <= balance) balance -= amount;
            return balance;
        },
        getBalance() { return balance; }
    };
}

const account = createBankAccount(1000);
account.deposit(500);    // 1500
account.withdraw(200);   // 1300
// account.balance;      // undefined - không truy cập trực tiếp được!

// 2. Function Factory
function createMultiplier(multiplier) {
    return function(number) {
        return number * multiplier;
    };
}
const double = createMultiplier(2);
const triple = createMultiplier(3);
double(5); // 10
triple(5); // 15

// 3. Memoization (Cache kết quả tính toán)
function memoize(fn) {
    const cache = {};
    return function(...args) {
        const key = JSON.stringify(args);
        if (cache[key] !== undefined) {
            console.log("Từ cache!");
            return cache[key];
        }
        const result = fn(...args);
        cache[key] = result;
        return result;
    };
}

const memoFib = memoize(function fib(n) {
    if (n <= 1) return n;
    return memoFib(n - 1) + memoFib(n - 2);
});
memoFib(40); // Rất nhanh nhờ cache

// 4. Debounce
function debounce(fn, delay) {
    let timeoutId;
    return function(...args) {
        clearTimeout(timeoutId);
        timeoutId = setTimeout(() => fn.apply(this, args), delay);
    };
}

const handleSearch = debounce((query) => {
    console.log("Searching:", query);
}, 300);
```

### 12.3 Bẫy thường gặp với Closure trong Loop

```javascript
// SAI - tất cả đều in ra 3 vì var là function-scoped
for (var i = 0; i < 3; i++) {
    setTimeout(() => console.log(i), 100);
}
// Output: 3, 3, 3

// ĐÚNG - Cách 1: Dùng let (block-scoped)
for (let i = 0; i < 3; i++) {
    setTimeout(() => console.log(i), 100);
}
// Output: 0, 1, 2

// ĐÚNG - Cách 2: Dùng IIFE tạo scope mới
for (var i = 0; i < 3; i++) {
    ((j) => {
        setTimeout(() => console.log(j), 100);
    })(i);
}
// Output: 0, 1, 2
```

---

## 13. this Keyword

### 13.1 this trong các ngữ cảnh khác nhau

```javascript
// 1. Global context
console.log(this); // window (browser) hoặc global (Node.js)
// Trong strict mode: undefined

// 2. Object method - this trỏ đến object sở hữu method
const person = {
    name: "An",
    greet() {
        console.log(this.name); // "An"
    }
};
person.greet();

// 3. Regular function - this phụ thuộc vào cách gọi
function showThis() {
    console.log(this);
}
showThis();        // window (non-strict) / undefined (strict)
person.greet();    // person object

// 4. Arrow function - this kế thừa từ scope cha (lexical this)
const obj = {
    name: "An",
    greet: () => {
        console.log(this.name); // undefined! this là global, không phải obj
    },
    delayGreet() {
        setTimeout(() => {
            console.log(this.name); // "An" - arrow function kế thừa this từ delayGreet
        }, 100);
    }
};

// 5. Constructor - this trỏ đến object mới tạo
function User(name) {
    this.name = name;
}
const user = new User("An"); // this = {} mới

// 6. Event handler - this trỏ đến element nhận sự kiện
button.addEventListener("click", function() {
    console.log(this); // <button> element
});
button.addEventListener("click", () => {
    console.log(this); // window! Arrow function không có this riêng
});
```

### 13.2 call(), apply(), bind()

```javascript
function greet(greeting, punctuation) {
    return `${greeting}, ${this.name}${punctuation}`;
}

const person = { name: "An" };

// call - gọi hàm với this chỉ định, tham số riêng lẻ
greet.call(person, "Xin chào", "!"); // "Xin chào, An!"

// apply - giống call nhưng tham số là mảng
greet.apply(person, ["Xin chào", "!"]); // "Xin chào, An!"

// bind - tạo hàm mới với this cố định (không gọi ngay)
const greetAn = greet.bind(person, "Hello");
greetAn("!");     // "Hello, An!"
greetAn("...");   // "Hello, An..."
```

---

## 14. Prototype và Kế thừa

### 14.1 Prototype Chain

Mỗi object trong JavaScript có một **[[Prototype]]** (prototype ẩn) trỏ đến một object khác. Khi truy cập thuộc tính không tồn tại, JS tìm theo chuỗi prototype.

```javascript
const animal = {
    eat() { return "eating"; }
};

const dog = Object.create(animal);
dog.bark = function() { return "woof!"; };

dog.bark(); // "woof!" - tìm thấy trên dog
dog.eat();  // "eating" - không có trên dog -> tìm lên prototype (animal)

// Prototype chain: dog -> animal -> Object.prototype -> null
```

### 14.2 Constructor Function và Prototype

```javascript
function Person(name, age) {
    this.name = name;
    this.age = age;
}

// Method được chia sẻ qua prototype (tiết kiệm bộ nhớ)
Person.prototype.greet = function() {
    return `Xin chào, tôi là ${this.name}`;
};

Person.prototype.getAge = function() {
    return this.age;
};

const an = new Person("An", 25);
const binh = new Person("Bình", 30);

an.greet();  // "Xin chào, tôi là An"
binh.greet(); // "Xin chào, tôi là Bình"

// Cả hai dùng chung method
an.greet === binh.greet; // true
```

### 14.3 Kế thừa qua Prototype

```javascript
function Animal(name) {
    this.name = name;
}
Animal.prototype.speak = function() {
    return `${this.name} makes a sound`;
};

function Dog(name, breed) {
    Animal.call(this, name); // Gọi constructor cha
    this.breed = breed;
}

// Thiết lập kế thừa
Dog.prototype = Object.create(Animal.prototype);
Dog.prototype.constructor = Dog;

Dog.prototype.bark = function() {
    return `${this.name} barks!`;
};

const rex = new Dog("Rex", "Husky");
rex.speak(); // "Rex makes a sound" (kế thừa từ Animal)
rex.bark();  // "Rex barks!" (riêng của Dog)
rex.instanceof Dog;    // true
rex.instanceof Animal; // true
```

---

## 15. ES6+ Features

### 15.1 let và const

```javascript
// Block scoping
{
    let x = 1;
    const y = 2;
}
// x, y không truy cập được ở đây

// const với object/array - vẫn có thể thay đổi nội dung
const arr = [1, 2, 3];
arr.push(4);     // OK - thay đổi nội dung
// arr = [5, 6]; // Error - không thể gán lại

const obj = { name: "An" };
obj.age = 25;    // OK - thêm thuộc tính
// obj = {};     // Error - không thể gán lại
```

### 15.2 Arrow Functions

```javascript
// Xem chi tiết tại phần 7.3
const add = (a, b) => a + b;
const greet = name => `Hello ${name}`;
const getObj = () => ({ key: "value" });
```

### 15.3 Template Literals

```javascript
const name = "An";
const msg = `Hello ${name}, 2 + 3 = ${2 + 3}`;
const multiline = `
    Line 1
    Line 2
`;
```

### 15.4 Enhanced Object Literals

```javascript
const name = "An";
const age = 25;

const person = {
    name,           // Property shorthand
    age,
    greet() {       // Method shorthand
        return `Hi ${this.name}`;
    },
    ["key" + 1]: "value1" // Computed property
};
```

### 15.5 for...of Loop

```javascript
for (const item of [1, 2, 3]) console.log(item);
for (const char of "Hello") console.log(char);
for (const [key, val] of new Map([["a", 1]])) console.log(key, val);
```

### 15.6 Promise (xem chi tiết phần 17)

### 15.7 Default Parameters, Rest/Spread (xem các phần trước)

### 15.8 Symbol

```javascript
const id = Symbol("id");
const obj = { [id]: 123, name: "An" };
obj[id]; // 123

// Symbol không hiển thị trong for...in hoặc Object.keys()
Object.keys(obj);                  // ["name"]
Object.getOwnPropertySymbols(obj); // [Symbol(id)]
```

### 15.9 Các tính năng ES2020+

```javascript
// Optional Chaining (?.) - ES2020
user?.address?.city;

// Nullish Coalescing (??) - ES2020
value ?? "default";

// Logical Assignment (||=, &&=, ??=) - ES2021
x ||= 5;

// structuredClone - Deep copy (ES2022)
const original = { a: 1, nested: { b: 2 } };
const deepCopy = structuredClone(original);
deepCopy.nested.b = 99;
console.log(original.nested.b); // 2 (không bị ảnh hưởng)

// Object.groupBy (ES2024)
const people = [
    { name: "An", age: 25 },
    { name: "Bình", age: 30 },
    { name: "Cường", age: 25 }
];
const grouped = Object.groupBy(people, p => p.age);
// { 25: [{name:"An",...}, {name:"Cường",...}], 30: [{name:"Bình",...}] }
```

---

## 16. Destructuring và Spread/Rest

### 16.1 Array Destructuring

```javascript
const colors = ["đỏ", "xanh", "vàng", "tím"];

// Cơ bản
const [first, second] = colors;   // "đỏ", "xanh"

// Bỏ qua phần tử
const [, , third] = colors;       // "vàng"

// Giá trị mặc định
const [a, b, c, d, e = "trắng"] = colors;

// Rest pattern
const [head, ...tail] = colors;   // "đỏ", ["xanh", "vàng", "tím"]

// Swap biến
let x = 1, y = 2;
[x, y] = [y, x];  // x = 2, y = 1
```

### 16.2 Object Destructuring

```javascript
const person = { name: "An", age: 25, city: "HCM" };

const { name, age } = person;
const { name: fullName } = person;    // Đổi tên
const { phone = "N/A" } = person;     // Giá trị mặc định
const { name, ...rest } = person;     // Rest

// Nested
const { address: { city } } = { address: { city: "HN" } };

// Trong tham số hàm
function greet({ name, age }) {
    return `${name}, ${age} tuổi`;
}
greet(person);
```

### 16.3 Spread Operator (...)

```javascript
// Spread Array
const arr1 = [1, 2, 3];
const arr2 = [4, 5, 6];
const combined = [...arr1, ...arr2];    // [1, 2, 3, 4, 5, 6]
const copy = [...arr1];                  // Shallow copy

// Spread Object
const defaults = { theme: "light", lang: "vi" };
const userPrefs = { theme: "dark" };
const config = { ...defaults, ...userPrefs };
// { theme: "dark", lang: "vi" } - userPrefs ghi đè defaults

// Spread vào tham số hàm
const nums = [1, 2, 3];
Math.max(...nums); // 3
```

---

## 17. Promise và Async/Await

### 17.1 Callback Hell

```javascript
// Vấn đề: callback lồng nhiều cấp -> khó đọc, khó bảo trì
getData(function(a) {
    getMoreData(a, function(b) {
        getEvenMoreData(b, function(c) {
            getFinalData(c, function(d) {
                console.log(d);
            });
        });
    });
});
```

### 17.2 Promise

Promise đại diện cho một giá trị **có thể có trong tương lai**. Có 3 trạng thái:
- **Pending**: Đang chờ xử lý
- **Fulfilled**: Thành công
- **Rejected**: Thất bại

```javascript
// Tạo Promise
const promise = new Promise((resolve, reject) => {
    const success = true;
    setTimeout(() => {
        if (success) {
            resolve("Thành công!"); // Chuyển sang fulfilled
        } else {
            reject("Thất bại!");    // Chuyển sang rejected
        }
    }, 1000);
});

// Sử dụng Promise
promise
    .then(result => {
        console.log(result);  // "Thành công!"
        return "Bước tiếp theo";
    })
    .then(next => {
        console.log(next);    // "Bước tiếp theo"
    })
    .catch(error => {
        console.error(error); // Xử lý lỗi
    })
    .finally(() => {
        console.log("Luôn chạy!"); // Chạy bất kể thành công hay thất bại
    });
```

### 17.3 Promise Chaining

```javascript
function fetchUser(id) {
    return fetch(`/api/users/${id}`)
        .then(res => res.json());
}

function fetchPosts(userId) {
    return fetch(`/api/users/${userId}/posts`)
        .then(res => res.json());
}

// Chain promises
fetchUser(1)
    .then(user => {
        console.log(user.name);
        return fetchPosts(user.id);
    })
    .then(posts => {
        console.log(`Có ${posts.length} bài viết`);
    })
    .catch(error => {
        console.error("Lỗi:", error);
    });
```

### 17.4 Promise Static Methods

```javascript
// Promise.all - chờ TẤT CẢ hoàn thành (hoặc 1 lỗi -> reject ngay)
const results = await Promise.all([
    fetch("/api/users"),
    fetch("/api/posts"),
    fetch("/api/comments")
]);
// [Response, Response, Response]

// Promise.allSettled - chờ TẤT CẢ hoàn thành (không quan tâm lỗi)
const results2 = await Promise.allSettled([
    Promise.resolve("OK"),
    Promise.reject("Lỗi"),
    Promise.resolve("OK 2")
]);
// [
//   { status: "fulfilled", value: "OK" },
//   { status: "rejected", reason: "Lỗi" },
//   { status: "fulfilled", value: "OK 2" }
// ]

// Promise.race - trả về kết quả CỦA PROMISE NHANH NHẤT
const fast = await Promise.race([
    fetch("/api/server1"),
    fetch("/api/server2")
]);

// Promise.any - trả về kết quả CỦA PROMISE THÀNH CÔNG ĐẦU TIÊN
const firstSuccess = await Promise.any([
    fetch("/api/slow"),
    fetch("/api/fast"),
    fetch("/api/fail")
]);

// Promise.resolve / reject - tạo promise đã giải quyết
const resolved = Promise.resolve(42);
const rejected = Promise.reject("Error");
```

### 17.5 Async/Await (ES2017)

Cách viết bất đồng bộ **giống đồng bộ**, dựa trên Promise:

```javascript
// async function luôn trả về Promise
async function fetchUserData(id) {
    try {
        const response = await fetch(`/api/users/${id}`);

        if (!response.ok) {
            throw new Error(`HTTP Error: ${response.status}`);
        }

        const user = await response.json();
        const posts = await fetch(`/api/users/${user.id}/posts`);
        const postsData = await posts.json();

        return { user, posts: postsData };
    } catch (error) {
        console.error("Lỗi:", error.message);
        throw error; // Re-throw để caller xử lý
    }
}

// Gọi async function
fetchUserData(1)
    .then(data => console.log(data))
    .catch(err => console.error(err));

// Hoặc dùng await (trong async function khác)
async function main() {
    const data = await fetchUserData(1);
    console.log(data);
}
```

### 17.6 Xử lý song song với async/await

```javascript
// SAI - tuần tự (chậm)
async function sequential() {
    const user = await fetchUser();   // Chờ 1s
    const posts = await fetchPosts(); // Chờ thêm 1s
    // Tổng: 2s
}

// ĐÚNG - song song (nhanh)
async function parallel() {
    const [user, posts] = await Promise.all([
        fetchUser(),   // Bắt đầu ngay
        fetchPosts()   // Bắt đầu ngay
    ]);
    // Tổng: ~1s (song song)
}

// Sử dụng for...of với await (tuần tự có chủ đích)
async function processItems(items) {
    for (const item of items) {
        await processItem(item); // Xử lý từng cái một
    }
}

// Lưu ý: forEach KHÔNG hoạt động với async/await
// items.forEach(async (item) => { await process(item); }); // SAI!
```

### 17.7 Top-Level Await (ES2022)

```javascript
// Trong ES Modules, có thể dùng await ở cấp cao nhất
// file: config.mjs
const response = await fetch("/api/config");
const config = await response.json();
export default config;
```

---

## 18. Error Handling

### 18.1 try...catch...finally

```javascript
try {
    // Code có thể gây lỗi
    const data = JSON.parse("invalid json");
} catch (error) {
    // Xử lý lỗi
    console.error("Lỗi:", error.message);
    console.error("Loại:", error.name);    // SyntaxError
    console.error("Stack:", error.stack);   // Stack trace
} finally {
    // Luôn chạy, bất kể có lỗi hay không
    console.log("Hoàn tất");
}
```

### 18.2 Các loại Error

```javascript
// SyntaxError - lỗi cú pháp
// eval("var a = ;");

// ReferenceError - biến chưa được khai báo
// console.log(undeclaredVar);

// TypeError - sai kiểu dữ liệu
// null.toString();
// (5).toUpperCase();

// RangeError - giá trị ngoài phạm vi
// new Array(-1);

// URIError - sai định dạng URI
// decodeURI("%");
```

### 18.3 Custom Error

```javascript
class ValidationError extends Error {
    constructor(message, field) {
        super(message);
        this.name = "ValidationError";
        this.field = field;
    }
}

class NotFoundError extends Error {
    constructor(resource, id) {
        super(`${resource} với id ${id} không tồn tại`);
        this.name = "NotFoundError";
        this.resource = resource;
        this.id = id;
    }
}

function validateAge(age) {
    if (typeof age !== "number") {
        throw new ValidationError("Age phải la số", "age");
    }
    if (age < 0 || age > 150) {
        throw new ValidationError("Age phải từ 0 đến 150", "age");
    }
}

try {
    validateAge("abc");
} catch (error) {
    if (error instanceof ValidationError) {
        console.log(`Lỗi validation trường ${error.field}: ${error.message}`);
    } else {
        throw error; // Re-throw nếu không phải lỗi đã biết
    }
}
```

### 18.4 Error Handling với Async/Await

```javascript
// Cách 1: try/catch
async function fetchData() {
    try {
        const response = await fetch("/api/data");
        if (!response.ok) throw new Error(`HTTP ${response.status}`);
        return await response.json();
    } catch (error) {
        console.error("Fetch failed:", error);
        return null; // Giá trị mặc định
    }
}

// Cách 2: Wrapper function
async function safeAsync(asyncFn) {
    try {
        const data = await asyncFn();
        return [data, null];
    } catch (error) {
        return [null, error];
    }
}

const [data, error] = await safeAsync(() => fetch("/api/data").then(r => r.json()));
if (error) console.error("Lỗi:", error);
```

---

## 19. DOM Manipulation

### 19.1 Chọn phần tử (Selecting Elements)

```javascript
// Theo ID
const header = document.getElementById("header");

// Theo CSS selector (trả về phần tử đầu tiên)
const firstBtn = document.querySelector(".btn");
const nav = document.querySelector("nav > ul");

// Theo CSS selector (trả về tất cả - NodeList)
const allBtns = document.querySelectorAll(".btn");
// Chuyển sang Array
const btnsArray = [...document.querySelectorAll(".btn")];

// Theo class name (trả về HTMLCollection - live)
const items = document.getElementsByClassName("item");

// Theo tag name
const paragraphs = document.getElementsByTagName("p");
```

### 19.2 Thay đổi nội dung

```javascript
const el = document.querySelector("#content");

// Text content (chỉ text, an toàn với XSS)
el.textContent = "Hello World";

// Inner HTML (parse HTML - CẨN THẬN XSS!)
el.innerHTML = "<strong>Bold text</strong>";

// Outer HTML (thay thế cả element)
el.outerHTML = "<div id='new'>New element</div>";
```

### 19.3 Thay đổi Style và Class

```javascript
const el = document.querySelector(".box");

// Inline style
el.style.backgroundColor = "red";
el.style.fontSize = "20px";
el.style.display = "none";

// ClassList API (khuyên dùng)
el.classList.add("active");           // Thêm class
el.classList.remove("hidden");        // Xóa class
el.classList.toggle("visible");       // Toggle class
el.classList.contains("active");      // Kiểm tra có class
el.classList.replace("old", "new");   // Thay thế class

// className (thay thế toàn bộ class)
el.className = "box active";
```

### 19.4 Thuộc tính (Attributes)

```javascript
const link = document.querySelector("a");

link.getAttribute("href");              // Lấy giá trị
link.setAttribute("href", "https://example.com"); // Đặt giá trị
link.removeAttribute("target");         // Xóa
link.hasAttribute("rel");               // Kiểm tra

// Data attributes
// <div data-user-id="123" data-role="admin">
const el = document.querySelector("div");
el.dataset.userId;       // "123"
el.dataset.role;         // "admin"
el.dataset.newProp = "value"; // Thêm data-new-prop
```

### 19.5 Tạo và Thêm phần tử

```javascript
// Tạo element
const div = document.createElement("div");
div.className = "card";
div.textContent = "New Card";

// Thêm vào DOM
document.body.appendChild(div);              // Thêm vào cuối
document.body.prepend(div);                  // Thêm vào đầu
parent.insertBefore(div, referenceNode);     // Chèn trước một node

// insertAdjacentHTML - chèn HTML tại vị trí cụ thể
element.insertAdjacentHTML("beforebegin", "<p>Trước element</p>");
element.insertAdjacentHTML("afterbegin", "<p>Đầu element</p>");
element.insertAdjacentHTML("beforeend", "<p>Cuối element</p>");
element.insertAdjacentHTML("afterend", "<p>Sau element</p>");

// Xóa element
element.remove();                             // Xóa chính nó
parent.removeChild(child);                    // Xóa element con

// Clone element
const clone = element.cloneNode(true);        // Deep clone (cả children)
const shallow = element.cloneNode(false);     // Shallow clone
```

### 19.6 Document Fragment

```javascript
// Tối ưu: gom nhiều thao tác DOM thành 1 lần
const fragment = document.createDocumentFragment();

for (let i = 0; i < 1000; i++) {
    const li = document.createElement("li");
    li.textContent = `Item ${i}`;
    fragment.appendChild(li); // Thêm vào fragment (không reflow)
}

document.querySelector("ul").appendChild(fragment); // 1 lần reflow duy nhất
```

---

## 20. Event Handling

### 20.1 addEventListener

```javascript
const button = document.querySelector("#myBtn");

// Thêm event listener
button.addEventListener("click", function(event) {
    console.log("Clicked!", event.target);
});

// Arrow function
button.addEventListener("click", (e) => {
    console.log("Clicked!", e.target);
});

// Named function (dễ gỡ bỏ)
function handleClick(e) {
    console.log("Clicked!");
}
button.addEventListener("click", handleClick);
button.removeEventListener("click", handleClick); // Gỡ bỏ

// Options
button.addEventListener("click", handler, {
    once: true,      // Chỉ chạy 1 lần rồi tự động gỡ bỏ
    capture: true,   // Bắt sự kiện ở pha capturing
    passive: true    // Handler sẽ không gọi preventDefault()
});
```

### 20.2 Event Object

```javascript
element.addEventListener("click", function(event) {
    event.target;          // Element được click trực tiếp
    event.currentTarget;   // Element đang xử lý sự kiện (có listener)
    event.type;            // "click"
    event.timeStamp;       // Thời điểm sự kiện xảy ra
    event.clientX;         // Tọa độ X (viewport)
    event.clientY;         // Tọa độ Y (viewport)
    event.pageX;           // Tọa độ X (trang)
    event.pageY;           // Tọa độ Y (trang)

    event.preventDefault();   // Ngăn hành vi mặc định (vd: submit form)
    event.stopPropagation();  // Ngăn sự kiện lan truyền (bubbling)
});
```

### 20.3 Event Bubbling và Capturing

```javascript
// Event Bubbling (mặc định): event lan từ element con lên element cha
// Click vào <button> trong <div>: button -> div -> body -> html -> document

// Event Capturing: ngược lại, từ ngoài vào trong
// document -> html -> body -> div -> button

document.querySelector(".parent").addEventListener("click", () => {
    console.log("Parent clicked (bubbling)");
}); // Mặc định: bubbling

document.querySelector(".parent").addEventListener("click", () => {
    console.log("Parent clicked (capturing)");
}, true); // true = capturing phase

// Ngăn lan truyền
child.addEventListener("click", (e) => {
    e.stopPropagation(); // Không lan lên parent
});
```

### 20.4 Event Delegation

Thay vì gán event cho từng element con, gán cho element cha:

```javascript
// KHÔNG TỐT: gán cho mỗi li
document.querySelectorAll("li").forEach(li => {
    li.addEventListener("click", handleClick);
});

// TỐT HƠN: Event Delegation
document.querySelector("ul").addEventListener("click", function(e) {
    const li = e.target.closest("li");
    if (li && this.contains(li)) {
        console.log("Clicked:", li.textContent);
    }
});
// Ưu điểm:
// - Ít listener hơn (tiết kiệm bộ nhớ)
// - Tự động hoạt động với element động (thêm sau)
// - Dễ quản lý
```

### 20.5 Các sự kiện phổ biến

```javascript
// Mouse events
element.addEventListener("click", handler);      // Click
element.addEventListener("dblclick", handler);    // Double click
element.addEventListener("mouseenter", handler);  // Hover vào (không bubble)
element.addEventListener("mouseleave", handler);  // Hover ra (không bubble)
element.addEventListener("mousemove", handler);   // Di chuyển chuột
element.addEventListener("contextmenu", handler); // Click phải

// Keyboard events
document.addEventListener("keydown", (e) => {
    console.log(e.key);     // "Enter", "a", "Escape",...
    console.log(e.code);    // "Enter", "KeyA", "Escape",...
    console.log(e.ctrlKey); // true nếu giữ Ctrl
    console.log(e.shiftKey);
    console.log(e.altKey);
});

// Form events
form.addEventListener("submit", (e) => {
    e.preventDefault(); // Ngăn submit mặc định
    const formData = new FormData(form);
});
input.addEventListener("input", handler);   // Mọi thay đổi
input.addEventListener("change", handler);  // Mất focus sau thay đổi
input.addEventListener("focus", handler);   // Focus vào
input.addEventListener("blur", handler);    // Mất focus

// Document events
document.addEventListener("DOMContentLoaded", handler); // DOM ready
window.addEventListener("load", handler);                // Tất cả tải xong
window.addEventListener("resize", handler);              // Resize cửa sổ
window.addEventListener("scroll", handler);              // Scroll
```

---

## 21. Fetch API và AJAX

### 21.1 Fetch cơ bản

```javascript
// GET request
const response = await fetch("https://api.example.com/users");
const data = await response.json();

// Kiểm tra response
if (!response.ok) {
    throw new Error(`HTTP Error: ${response.status}`);
}

// POST request
const newUser = await fetch("https://api.example.com/users", {
    method: "POST",
    headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer token123"
    },
    body: JSON.stringify({
        name: "An",
        email: "an@mail.com"
    })
});

// PUT request
await fetch(`/api/users/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(updatedData)
});

// DELETE request
await fetch(`/api/users/${id}`, {
    method: "DELETE"
});
```

### 21.2 Xử lý các loại response

```javascript
const res = await fetch(url);

res.json();    // Parse JSON
res.text();    // Plain text
res.blob();    // Binary (image, file)
res.formData(); // Form data
res.arrayBuffer(); // Raw binary

// Response properties
res.ok;        // true nếu status 200-299
res.status;    // 200, 404, 500,...
res.statusText; // "OK", "Not Found",...
res.headers;   // Headers object
res.url;       // URL cuối cùng (sau redirect)
```

### 21.3 AbortController - Hủy request

```javascript
const controller = new AbortController();

// Bắt đầu fetch với signal
fetch("/api/data", { signal: controller.signal })
    .then(res => res.json())
    .then(data => console.log(data))
    .catch(err => {
        if (err.name === "AbortError") {
            console.log("Request bị hủy");
        }
    });

// Hủy request sau 5 giây
setTimeout(() => controller.abort(), 5000);

// Hoặc dùng AbortSignal.timeout (mới hơn)
fetch("/api/data", { signal: AbortSignal.timeout(5000) });
```

### 21.4 Ví dụ thực tế: Fetch Wrapper

```javascript
class ApiClient {
    constructor(baseURL) {
        this.baseURL = baseURL;
    }

    async request(endpoint, options = {}) {
        const url = `${this.baseURL}${endpoint}`;
        const config = {
            headers: {
                "Content-Type": "application/json",
                ...options.headers
            },
            ...options
        };

        if (config.body && typeof config.body === "object") {
            config.body = JSON.stringify(config.body);
        }

        const response = await fetch(url, config);

        if (!response.ok) {
            const error = await response.json().catch(() => ({}));
            throw new Error(error.message || `HTTP ${response.status}`);
        }

        return response.json();
    }

    get(endpoint) { return this.request(endpoint); }
    post(endpoint, body) { return this.request(endpoint, { method: "POST", body }); }
    put(endpoint, body) { return this.request(endpoint, { method: "PUT", body }); }
    delete(endpoint) { return this.request(endpoint, { method: "DELETE" }); }
}

const api = new ApiClient("https://api.example.com");
const users = await api.get("/users");
const newUser = await api.post("/users", { name: "An" });
```


## 22. Web Storage API

### 22.1 localStorage

Dữ liệu lưu trữ **vĩnh viễn** (cho đến khi xóa thủ công):

```javascript
// Lưu
localStorage.setItem("name", "An");
localStorage.setItem("user", JSON.stringify({ name: "An", age: 25 }));

// Đọc
const name = localStorage.getItem("name"); // "An"
const user = JSON.parse(localStorage.getItem("user"));

// Xóa
localStorage.removeItem("name");   // Xóa 1 item
localStorage.clear();               // Xóa tất cả

// Kiểm tra
localStorage.length;                // Số lượng items
localStorage.key(0);                // Key tại index 0
```

### 22.2 sessionStorage

Giống localStorage nhưng **chỉ tồn tại trong phiên làm việc** (đóng tab = mất):

```javascript
sessionStorage.setItem("token", "abc123");
const token = sessionStorage.getItem("token");
sessionStorage.removeItem("token");
```

### 22.3 So sánh

| Đặc điểm | localStorage | sessionStorage | Cookie |
|----------|-------------|----------------|--------|
| Dung lượng | ~5-10 MB | ~5-10 MB | ~4 KB |
| Hết hạn | Không | Đóng tab | Có thể set |
| Gửi lên server | Không | Không | Tự động gửi |
| Truy cập | Client-side | Client-side | Client + Server |

### 22.4 Storage Wrapper với Type Safety

```javascript
const storage = {
    get(key, defaultValue = null) {
        try {
            const item = localStorage.getItem(key);
            return item ? JSON.parse(item) : defaultValue;
        } catch {
            return defaultValue;
        }
    },

    set(key, value) {
        localStorage.setItem(key, JSON.stringify(value));
    },

    remove(key) {
        localStorage.removeItem(key);
    },

    has(key) {
        return localStorage.getItem(key) !== null;
    }
};

storage.set("settings", { theme: "dark", lang: "vi" });
const settings = storage.get("settings", { theme: "light" });
```

---

## 23. Regular Expressions

### 23.1 Tạo Regex

```javascript
// Cách 1: Literal
const regex1 = /pattern/flags;

// Cách 2: Constructor (khi pattern là biến)
const regex2 = new RegExp("pattern", "flags");
```

### 23.2 Flags

| Flag | Mô tả |
|------|-------|
| `g` | Global - tìm tất cả, không dừng lại ở kết quả đầu |
| `i` | Case-insensitive - không phân biệt hoa thường |
| `m` | Multiline - ^ và $ match đầu/cuối mỗi dòng |
| `s` | Dotall - dấu . match cả ký tự xuống dòng |
| `u` | Unicode - hỗ trợ Unicode đầy đủ |
| `d` | hasIndices - trả về chỉ số vị trí của match |

### 23.3 Các ký tự đặc biệt

```javascript
// Meta characters
.      // Bất kỳ ký tự nào (trừ newline)
\d     // Chữ số [0-9]
\D     // Không phải chữ số
\w     // Word character [a-zA-Z0-9_]
\W     // Không phải word character
\s     // Khoảng trắng (space, tab, newline)
\S     // Không phải khoảng trắng
\b     // Biên giới từ (word boundary)

// Quantifiers (số lượng)
*      // 0 hoặc nhiều lần
+      // 1 hoặc nhiều lần
?      // 0 hoặc 1 lần
{n}    // Chính xác n lần
{n,}   // Ít nhất n lần
{n,m}  // Từ n đến m lần

// Anchors
^      // Đầu chuỗi
$      // Cuối chuỗi

// Groups
(abc)    // Capture group
(?:abc)  // Non-capture group
(?<name>abc) // Named capture group
a|b      // Hoặc a hoặc b

// Character class
[abc]    // a, b, hoặc c
[^abc]   // Không phải a, b, c
[a-z]    // a đến z
[A-Z0-9] // A-Z hoặc 0-9
```

### 23.4 Phương thức Regex

```javascript
const str = "Hello World 123";

// test() - kiểm tra có match không
/\d+/.test(str);          // true

// exec() - lấy thông tin chi tiết về match
 /(\d+)/.exec(str);        // ["123", "123", index: 12]

// String methods với regex
str.match(/\d+/g);        // ["123"]
str.matchAll(/(\w+)/g);   // Iterator của tất cả matches
str.search(/world/i);     // 6 (vị trí tìm thấy)
str.replace(/world/i, "JS"); // "Hello JS 123"
str.split(/\s+/);         // ["Hello", "World", "123"]
```

### 23.5 Ví dụ thực tế

```javascript
// Validate email
const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
emailRegex.test("an@mail.com"); // true

// Validate số điện thoại VN
const phoneRegex = /^(0|\+84)(3|5|7|8|9)\d{8}$/;
phoneRegex.test("0912345678"); // true

// Trích xuất số từ chuỗi
"Gia: 100.000d, Giam: 20.000d".match(/[\d.]+/g);
// ["100.000", "20.000"]

// Thay thế nhiều khoảng trắng
"Hello    World   JS".replace(/\s+/g, " ");
// "Hello World JS"

// Named groups
const dateStr = "2024-01-15";
const { year, month, day } = dateStr.match(
    /(?<year>\d{4})-(?<month>\d{2})-(?<day>\d{2})/
).groups;
// year: "2024", month: "01", day: "15"

// Password validation (ít nhất 8 ký tự, có hoa, thường, số, ký tự đặc biệt)
const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
```

---

## 24. Module System

### 24.1 ES Modules (ESM) - Chuẩn hiện đại

```javascript
// --- math.js ---
// Named exports
export const PI = 3.14159;
export function add(a, b) { return a + b; }
export function subtract(a, b) { return a - b; }

// Default export (mỗi file chỉ có 1)
export default class Calculator {
    add(a, b) { return a + b; }
}

// --- app.js ---
// Named imports
import { add, subtract, PI } from "./math.js";

// Đổi tên khi import
import { add as sum } from "./math.js";

// Default import (đặt tên tùy ý)
import Calculator from "./math.js";
import Calc from "./math.js"; // Cũng được

// Import tất cả
import * as math from "./math.js";
math.add(1, 2);
math.PI;

// Dynamic import (lazy loading)
const module = await import("./heavy-module.js");
module.doSomething();

// Hoặc với then
import("./heavy-module.js")
    .then(module => module.doSomething());
```

### 24.2 CommonJS (CJS) - Dùng trong Node.js

```javascript
// --- math.js ---
const PI = 3.14159;
function add(a, b) { return a + b; }

module.exports = { PI, add };
// hoặc
exports.PI = PI;
exports.add = add;

// --- app.js ---
const { PI, add } = require("./math");
const math = require("./math");
```

### 24.3 So sánh ESM và CJS

| Đặc điểm | ESM | CJS |
|----------|-----|-----|
| Cú pháp | import/export | require/module.exports |
| Loading | Bất đồng bộ | Đồng bộ |
| Phân tích | Tĩnh (static) | Động (runtime) |
| Tree-shaking | Có | Không |
| Browser | Hỗ trợ native | Cần bundler |
| Node.js | .mjs hoặc "type":"module" | Mặc định |

### 24.4 Sử dụng ESM trong HTML

```html
<script type="module" src="app.js"></script>
<script type="module">
    import { greet } from "./utils.js";
    greet("An");
</script>
```

---

## 25. Class (ES6)

### 25.1 Khai báo Class

```javascript
class Person {
    // Constructor
    constructor(name, age) {
        this.name = name;
        this.age = age;
    }

    // Instance method
    greet() {
        return `Xin chào, tôi là ${this.name}, ${this.age} tuổi`;
    }

    // Getter
    get info() {
        return `${this.name} (${this.age})`;
    }

    // Setter
    set info(value) {
        const [name, age] = value.split(",");
        this.name = name.trim();
        this.age = parseInt(age);
    }

    // Static method (gọi qua Class, không qua instance)
    static create(name, age) {
        return new Person(name, age);
    }

    // Static property
    static species = "Homo Sapiens";

    // Private field (ES2022)
    #id = Math.random();

    // Private method
    #generateId() {
        return Math.random().toString(36).substr(2, 9);
    }

    getId() {
        return this.#id;
    }
}

const an = new Person("An", 25);
an.greet();              // "Xin chào, tôi là An, 25 tuổi"
an.info;                 // "An (25)" - Getter
an.info = "Bình, 30";   // Setter
Person.species;          // "Homo Sapiens"
Person.create("C", 28); // Static method

// an.#id;              // SyntaxError - không truy cập private từ bên ngoài
```

### 25.2 Kế thừa (Inheritance)

```javascript
class Animal {
    constructor(name) {
        this.name = name;
    }

    speak() {
        return `${this.name} makes a sound`;
    }
}

class Dog extends Animal {
    constructor(name, breed) {
        super(name); // Gọi constructor cha (BẮT BUỘC trước khi dùng this)
        this.breed = breed;
    }

    // Override method cha
    speak() {
        return `${this.name} barks`;
    }

    // Gọi method cha
    speakLoud() {
        return super.speak().toUpperCase();
    }

    fetch(item) {
        return `${this.name} fetches ${item}`;
    }
}

const rex = new Dog("Rex", "Husky");
rex.speak();        // "Rex barks" (override)
rex.speakLoud();    // "REX MAKES A SOUND" (super)
rex.fetch("ball");  // "Rex fetches ball"

rex.instanceof Dog;    // true
rex.instanceof Animal; // true
```

### 25.3 Abstraction với Class

```javascript
// JavaScript không có abstract class chính thức
// Nhưng có thể mô phỏng:
class Shape {
    constructor(color) {
        if (new.target === Shape) {
            throw new Error("Không thể tạo instance Shape trực tiếp");
        }
        this.color = color;
    }

    // "Abstract" method
    area() {
        throw new Error("Phải implement method area()");
    }

    describe() {
        return `${this.color} shape với diện tích ${this.area()}`;
    }
}

class Circle extends Shape {
    constructor(color, radius) {
        super(color);
        this.radius = radius;
    }

    area() {
        return Math.PI * this.radius ** 2;
    }
}

const circle = new Circle("đỏ", 5);
circle.describe(); // "đỏ shape với diện tích 78.53..."
// new Shape("xanh"); // Error!
```

### 25.4 Mixins (đa kế thừa giới hạn)

```javascript
// JavaScript chỉ hỗ trợ đơn kế thừa, dùng Mixin để thêm chức năng
const Serializable = (Base) => class extends Base {
    serialize() {
        return JSON.stringify(this);
    }

    static deserialize(json) {
        return Object.assign(new this(), JSON.parse(json));
    }
};

const Validatable = (Base) => class extends Base {
    validate() {
        for (const [key, value] of Object.entries(this)) {
            if (value === null || value === undefined) {
                throw new Error(`${key} is required`);
            }
        }
        return true;
    }
};

class User extends Serializable(Validatable(class {})) {
    constructor(name, email) {
        super();
        this.name = name;
        this.email = email;
    }
}

const user = new User("An", "an@mail.com");
user.validate();           // true
const json = user.serialize(); // '{"name":"An","email":"an@mail.com"}'
```

---

## 26. Iterator và Generator

### 26.1 Iterator Protocol

Một object là **iterable** khi có method `[Symbol.iterator]()` trả về một **iterator** (có method `next()`):

```javascript
// Custom iterable
class Range {
    constructor(start, end) {
        this.start = start;
        this.end = end;
    }

    [Symbol.iterator]() {
        let current = this.start;
        const end = this.end;
        return {
            next() {
                if (current <= end) {
                    return { value: current++, done: false };
                }
                return { done: true };
            }
        };
    }
}

const range = new Range(1, 5);
for (const num of range) {
    console.log(num); // 1, 2, 3, 4, 5
}
[...range]; // [1, 2, 3, 4, 5]
```

### 26.2 Generator Function

Generator là hàm đặc biệt có thể **tạm dừng** và **tiếp tục** thực thi:

```javascript
function* numberGenerator() {
    yield 1;
    yield 2;
    yield 3;
}

const gen = numberGenerator();
gen.next(); // { value: 1, done: false }
gen.next(); // { value: 2, done: false }
gen.next(); // { value: 3, done: false }
gen.next(); // { value: undefined, done: true }

// Dùng trong for...of
for (const num of numberGenerator()) {
    console.log(num); // 1, 2, 3
}

// Spread
[...numberGenerator()]; // [1, 2, 3]
```

### 26.3 Ứng dụng của Generator

```javascript
// 1. Dãy số vô hạn
function* fibonacci() {
    let a = 0, b = 1;
    while (true) {
        yield a;
        [a, b] = [b, a + b];
    }
}

const fib = fibonacci();
fib.next().value; // 0
fib.next().value; // 1
fib.next().value; // 1
fib.next().value; // 2
fib.next().value; // 3

// Lấy N phần tử đầu
function take(generator, n) {
    const result = [];
    for (const value of generator) {
        result.push(value);
        if (result.length === n) break;
    }
    return result;
}
take(fibonacci(), 10); // [0, 1, 1, 2, 3, 5, 8, 13, 21, 34]

// 2. ID Generator
function* idGenerator(prefix = "ID") {
    let id = 1;
    while (true) {
        yield `${prefix}-${String(id++).padStart(4, "0")}`;
    }
}
const userIdGen = idGenerator("USR");
userIdGen.next().value; // "USR-0001"
userIdGen.next().value; // "USR-0002"

// 3. Async-like flow (trước khi có async/await)
function* fetchFlow() {
    const user = yield fetch("/api/user");
    const posts = yield fetch(`/api/posts?userId=${user.id}`);
    return posts;
}
```

---

## 27. Symbol, Map, Set, WeakMap, WeakSet

### 27.1 Symbol

```javascript
// Symbol là kiểu nguyên thủy duy nhất và bất biến
const sym1 = Symbol("description");
const sym2 = Symbol("description");
sym1 === sym2; // false - mỗi Symbol là duy nhất

// Sử dụng làm key của object (không bị xung đột tên)
const ID = Symbol("id");
const user = {
    [ID]: 123,
    name: "An"
};
user[ID]; // 123

// Well-known Symbols
Symbol.iterator;     // Định nghĩa iterable
Symbol.toPrimitive;  // Chuyển đổi kiểu
Symbol.hasInstance;   // Tùy chỉnh instanceof

// Global Symbol Registry
const globalSym = Symbol.for("app.id");
const same = Symbol.for("app.id");
globalSym === same; // true
Symbol.keyFor(globalSym); // "app.id"
```

### 27.2 Map

Map lưu trữ cặp **key-value** với key có thể là **bất kỳ kiểu gì**:

```javascript
const map = new Map();

// Set / Get
map.set("name", "An");
map.set(42, "so");
map.set(true, "bool");

const objKey = { id: 1 };
map.set(objKey, "object lam key");

map.get("name");    // "An"
map.get(42);        // "so"
map.get(objKey);    // "object lam key"

// Size và kiểm tra
map.size;           // 4
map.has("name");    // true

// Xóa
map.delete(42);
map.clear();        // Xóa tất cả

// Khởi tạo từ array
const map2 = new Map([
    ["a", 1],
    ["b", 2],
    ["c", 3]
]);

// Lặp
for (const [key, value] of map2) {
    console.log(key, value);
}
map2.forEach((value, key) => console.log(key, value));

// Chuyển đổi
[...map2.keys()];     // ["a", "b", "c"]
[...map2.values()];   // [1, 2, 3]
[...map2.entries()];  // [["a",1], ["b",2], ["c",3]]
Object.fromEntries(map2); // { a: 1, b: 2, c: 3 }
```

### 27.3 Set

Set lưu trữ **tập hợp giá trị duy nhất** (không trùng lặp):

```javascript
const set = new Set([1, 2, 3, 3, 2, 1]);
// Set { 1, 2, 3 } - tự động loại bỏ trùng

set.add(4);
set.has(3);    // true
set.delete(2);
set.size;      // 3

// Lặp
for (const value of set) console.log(value);

// Chuyển sang Array
[...set]; // [1, 3, 4]

// Ứng dụng: loại bỏ trùng lặp trong array
const unique = [...new Set([1, 1, 2, 2, 3])]; // [1, 2, 3]

// Các phép toán tập hợp
const setA = new Set([1, 2, 3, 4]);
const setB = new Set([3, 4, 5, 6]);

// Hợp (Union)
const union = new Set([...setA, ...setB]); // {1,2,3,4,5,6}

// Giao (Intersection)
const intersection = new Set([...setA].filter(x => setB.has(x))); // {3,4}

// Hiệu (Difference)
const difference = new Set([...setA].filter(x => !setB.has(x))); // {1,2}

// ES2025+ (proposal) - Set methods
// setA.union(setB);
// setA.intersection(setB);
// setA.difference(setB);
```

### 27.4 WeakMap và WeakSet

**Weak** references - key (WeakMap) hoặc value (WeakSet) có thể bị garbage collected:

```javascript
// WeakMap - key phải là object
const weakMap = new WeakMap();
let obj = { name: "An" };
weakMap.set(obj, "metadata");
weakMap.get(obj); // "metadata"

obj = null; // Object có thể bị garbage collected
// weakMap tự động xóa entry

// Ứng dụng: lưu metadata cho object mà không cần memory leak
const cache = new WeakMap();
function processObj(obj) {
    if (cache.has(obj)) return cache.get(obj);
    const result = /* tinh toan nang */ obj;
    cache.set(obj, result);
    return result;
}

// WeakSet - value phải là object
const weakSet = new WeakSet();
const visited = new WeakSet();

function trackVisit(user) {
    visited.add(user);
}

function hasVisited(user) {
    return visited.has(user);
}
```

---

## 28. Proxy và Reflect

### 28.1 Proxy

Proxy cho phép **chặn và tùy chỉnh** các thao tác trên object:

```javascript
const handler = {
    // Chặn đọc thuộc tính
    get(target, property, receiver) {
        console.log(`Đang đọc ${property}`);
        return property in target ? target[property] : `Không có ${property}`;
    },

    // Chặn ghi thuộc tính
    set(target, property, value, receiver) {
        if (property === "age" && (typeof value !== "number" || value < 0)) {
            throw new TypeError("Age phải là số dương");
        }
        target[property] = value;
        return true;
    },

    // Chặn delete
    deleteProperty(target, property) {
        if (property === "id") {
            throw new Error("Không thể xóa id");
        }
        delete target[property];
        return true;
    },

    // Chặn kiểm tra "in"
    has(target, property) {
        return property in target;
    }
};

const user = new Proxy({ id: 1, name: "An", age: 25 }, handler);

user.name;       // Log: "Đang đọc name", trả về "An"
user.email;      // Log: "Đang đọc email", trả về "Không có email"
user.age = 30;   // OK
// user.age = -5; // TypeError
// delete user.id; // Error
```

### 28.2 Ứng dụng Proxy

```javascript
// 1. Validation
function createValidatedObject(schema) {
    return new Proxy({}, {
        set(target, prop, value) {
            if (schema[prop]) {
                const { type, required, min, max } = schema[prop];
                if (type && typeof value !== type) {
                    throw new TypeError(`${prop} phải là ${type}`);
                }
                if (min !== undefined && value < min) {
                    throw new RangeError(`${prop} phải >= ${min}`);
                }
                if (max !== undefined && value > max) {
                    throw new RangeError(`${prop} phải <= ${max}`);
                }
            }
            target[prop] = value;
            return true;
        }
    });
}

const user = createValidatedObject({
    name: { type: "string" },
    age: { type: "number", min: 0, max: 150 }
});

user.name = "An";   // OK
user.age = 25;       // OK
// user.age = -1;    // RangeError

// 2. Observable (theo dõi thay đổi)
function observable(target, callback) {
    return new Proxy(target, {
        set(obj, prop, value) {
            const oldValue = obj[prop];
            obj[prop] = value;
            callback(prop, value, oldValue);
            return true;
        }
    });
}

const state = observable({ count: 0 }, (prop, newVal, oldVal) => {
    console.log(`${prop} thay đổi: ${oldVal} -> ${newVal}`);
});
state.count = 1; // "count thay đổi: 0 -> 1"
```

### 28.3 Reflect

Reflect cung cấp các phương thức tương ứng với các trap của Proxy:

```javascript
const obj = { name: "An", age: 25 };

Reflect.get(obj, "name");           // "An"
Reflect.set(obj, "age", 26);        // true
Reflect.has(obj, "name");           // true
Reflect.deleteProperty(obj, "age"); // true
Reflect.ownKeys(obj);               // ["name"]

// Dùng trong Proxy handler để đảm bảo hành vi mặc định
const handler = {
    get(target, prop, receiver) {
        console.log(`Truy cập ${prop}`);
        return Reflect.get(target, prop, receiver);
    }
};
```

---

## 29. Web APIs

### 29.1 Timer APIs

```javascript
// setTimeout - thực thi 1 lần sau delay
const timeoutId = setTimeout(() => {
    console.log("Sau 2 giây");
}, 2000);
clearTimeout(timeoutId); // Hủy

// setInterval - thực thi lặp lại
const intervalId = setInterval(() => {
    console.log("Mỗi 1 giây");
}, 1000);
clearInterval(intervalId); // Dừng lại

// requestAnimationFrame - tối ưu cho animation (60fps)
function animate() {
    // Cập nhật animation
    element.style.left = `${position}px`;
    position += 1;

    if (position < 300) {
        requestAnimationFrame(animate);
    }
}
requestAnimationFrame(animate);
```

### 29.2 Date API

```javascript
// Tạo Date
const now = new Date();
const specific = new Date(2024, 0, 15); // Tháng bắt đầu từ 0!
const fromString = new Date("2024-01-15T10:30:00");
const fromTimestamp = new Date(1705312200000);

// Lấy thông tin
now.getFullYear();    // 2024
now.getMonth();       // 0-11 (0 = tháng 1!)
now.getDate();        // 1-31
now.getDay();         // 0-6 (0 = Chủ nhật)
now.getHours();       // 0-23
now.getMinutes();     // 0-59
now.getSeconds();     // 0-59
now.getTime();        // Timestamp (ms từ 1/1/1970)

// Đặt giá trị
now.setFullYear(2025);
now.setMonth(5);      // Tháng 6

// Tính toán
const diff = date2 - date1; // Hiệu số ms
const days = diff / (1000 * 60 * 60 * 24); // Chuyển sang ngày

// Format
now.toLocaleDateString("vi-VN"); // "15/01/2024"
now.toLocaleTimeString("vi-VN"); // "10:30:00"
now.toISOString();               // "2024-01-15T03:30:00.000Z"

// Intl.DateTimeFormat (tùy chỉnh format)
new Intl.DateTimeFormat("vi-VN", {
    weekday: "long",
    year: "numeric",
    month: "long",
    day: "numeric"
}).format(now);
// "Thứ Hai, 15 tháng 1, 2024"
```

### 29.3 JSON

```javascript
// Parse (String -> Object)
const obj = JSON.parse('{"name":"An","age":25}');

// Stringify (Object -> String)
JSON.stringify(obj);                    // '{"name":"An","age":25}'
JSON.stringify(obj, null, 2);          // Formatted với 2 space indent
JSON.stringify(obj, ["name"]);         // '{"name":"An"}' - chỉ giữ "name"
JSON.stringify(obj, (key, value) => {  // Replacer function
    if (key === "age") return undefined; // Bỏ qua "age"
    return value;
});

// Deep clone (đơn giản nhưng có giới hạn)
const clone = JSON.parse(JSON.stringify(original));
// Không hoạt động với: Date, Function, undefined, Map, Set, RegExp
// Dùng structuredClone() để deep clone đầy đủ hơn
```

### 29.4 Console API

```javascript
console.log("Thông tin");
console.warn("Cảnh báo");
console.error("Lỗi");
console.info("Info");

console.table([{name:"An",age:25}, {name:"Bình",age:30}]); // Bảng đẹp
console.group("Nhóm");
console.log("Bên trong nhóm");
console.groupEnd();

console.time("myTimer");
// Code cần đo thời gian
console.timeEnd("myTimer"); // "myTimer: 123.456ms"

console.assert(1 === 2, "1 không bằng 2"); // Chỉ hiện khi điều kiện sai
console.count("myCounter");  // "myCounter: 1"
console.count("myCounter");  // "myCounter: 2"
console.dir(object);         // Hiển thị object dạng tree
```

### 29.5 Event Loop và Call Stack

JavaScript là **single-threaded** nhưng xử lý bất đồng bộ nhờ **Event Loop**:

```
┌─────────────────────┐
│     Call Stack       │ <- Thực thi code đồng bộ
├─────────────────────┤
│  Web APIs / Node     │ <- setTimeout, fetch, DOM events
├─────────────────────┤
│  Microtask Queue     │ <- Promise.then, queueMicrotask (ƯU TIÊN CAO)
├─────────────────────┤
│  Macrotask Queue     │ <- setTimeout, setInterval, I/O
└─────────────────────┘

Event Loop:
1. Thực thi code trong Call Stack
2. Khi Call Stack rỗng -> kiểm tra Microtask Queue (xử lý HẾT)
3. Lấy 1 task từ Macrotask Queue -> Call Stack
4. Lặp lại bước 2-3
```

```javascript
console.log("1");                          // Đồng bộ -> Call Stack

setTimeout(() => console.log("2"), 0);     // Macrotask

Promise.resolve().then(() => console.log("3")); // Microtask

console.log("4");                          // Đồng bộ -> Call Stack

// Output: 1, 4, 3, 2
// Giải thích:
// 1. "1" - đồng bộ, chạy ngay
// 2. setTimeout -> Macrotask Queue
// 3. Promise.then -> Microtask Queue
// 4. "4" - đồng bộ, chạy ngay
// 5. Call Stack rỗng -> xử lý Microtask: "3"
// 6. Xử lý Macrotask: "2"
```

---

## 30. Best Practices và Design Patterns

### 30.1 Coding Best Practices

```javascript
// 1. Luôn dùng const, chỉ dùng let khi cần gán lại
const MAX_ITEMS = 100;
let currentCount = 0;

// 2. Dùng === thay vì ==
if (value === null) { }

// 3. Dùng template literals
const msg = `Hello ${name}, bạn có ${count} tin nhắn`;

// 4. Dùng destructuring
const { name, age } = user;
const [first, ...rest] = items;

// 5. Optional chaining và nullish coalescing
const city = user?.address?.city ?? "Unknown";

// 6. Arrow function cho callback ngắn
const doubled = numbers.map(n => n * 2);

// 7. Tránh side effects - ưu tiên pure functions
// Xấu:
function addToCart(cart, item) {
    cart.push(item); // Biến đổi tham số!
}
// Tốt:
function addToCart(cart, item) {
    return [...cart, item]; // Trả về mảng mới
}

// 8. Early return (Guard Clause)
function getDiscount(user) {
    if (!user) return 0;
    if (!user.isPremium) return 5;
    if (user.yearsActive > 5) return 25;
    return 15;
}

// 9. Error handling đầy đủ
async function fetchData(url) {
    try {
        const res = await fetch(url);
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        return await res.json();
    } catch (error) {
        console.error(`Fetch ${url} failed:`, error);
        return null;
    }
}

// 10. Đặt tên có ý nghĩa
// Xấu: const d = new Date();
// Tốt: const currentDate = new Date();
// Xấu: function proc(a, b) {}
// Tốt: function calculateTotal(price, tax) {}
```

### 30.2 Design Patterns trong JavaScript

**1. Module Pattern:**
```javascript
const CartModule = (() => {
    let items = [];

    return {
        add(item) { items.push(item); },
        remove(id) { items = items.filter(i => i.id !== id); },
        getItems() { return [...items]; },
        getTotal() { return items.reduce((sum, i) => sum + i.price, 0); }
    };
})();
```

**2. Observer Pattern:**
```javascript
class EventEmitter {
    #listeners = {};

    on(event, callback) {
        (this.#listeners[event] ||= []).push(callback);
        return () => this.off(event, callback);
    }

    off(event, callback) {
        this.#listeners[event] = this.#listeners[event]?.filter(cb => cb !== callback);
    }

    emit(event, ...args) {
        this.#listeners[event]?.forEach(cb => cb(...args));
    }
}

const emitter = new EventEmitter();
const unsub = emitter.on("userLogin", (user) => console.log(`${user} logged in`));
emitter.emit("userLogin", "An"); // "An logged in"
unsub(); // Gỡ đăng ký
```

**3. Singleton Pattern:**
```javascript
class Database {
    static #instance = null;

    constructor() {
        if (Database.#instance) {
            return Database.#instance;
        }
        this.connection = "Connected";
        Database.#instance = this;
    }

    static getInstance() {
        if (!Database.#instance) {
            Database.#instance = new Database();
        }
        return Database.#instance;
    }
}

const db1 = Database.getInstance();
const db2 = Database.getInstance();
db1 === db2; // true
```

**4. Factory Pattern:**
```javascript
class UserFactory {
    static create(type, data) {
        switch (type) {
            case "admin":
                return new AdminUser(data);
            case "editor":
                return new EditorUser(data);
            default:
                return new BasicUser(data);
        }
    }
}

const admin = UserFactory.create("admin", { name: "An" });
```

**5. Decorator Pattern:**
```javascript
function withLogging(fn) {
    return function(...args) {
        console.log(`Gọi ${fn.name} với args:`, args);
        const result = fn.apply(this, args);
        console.log(`Kết quả:`, result);
        return result;
    };
}

function add(a, b) { return a + b; }
const loggedAdd = withLogging(add);
loggedAdd(2, 3);
// "Gọi add với args: [2, 3]"
// "Kết quả: 5"
```

### 30.3 Clean Code Tips

```javascript
// 1. Hàm chỉ làm 1 việc
// Xấu:
function processUser(user) {
    // validate + save + send email + log
}
// Tốt:
function validateUser(user) { /* ... */ }
function saveUser(user) { /* ... */ }
function sendWelcomeEmail(user) { /* ... */ }

// 2. Tránh "magic numbers"
// Xấu: if (status === 3) { }
// Tốt:
const STATUS = { ACTIVE: 1, INACTIVE: 2, BANNED: 3 };
if (status === STATUS.BANNED) { }

// 3. Dùng Object.freeze cho constants
const CONFIG = Object.freeze({
    API_URL: "https://api.example.com",
    MAX_RETRIES: 3,
    TIMEOUT: 5000
});

// 4. Chain methods khi phù hợp
const result = users
    .filter(u => u.active)
    .map(u => u.name)
    .sort()
    .join(", ");
```

---

## 31. Tổng kết

### 31.1 Lộ trình học JavaScript

```
1. Cơ bản:
   Biến, Kiểu dữ liệu, Toán tử, Điều kiện, Vòng lặp, Hàm

2. Trung cấp:
   Array Methods, Object, Scope, Closure, this, Prototype
   ES6+ (let/const, arrow, destructuring, spread, template literals)

3. Nâng cao:
   Promise, Async/Await, Class, Module, Iterator/Generator
   Proxy/Reflect, Symbol, Map/Set

4. DOM & Browser:
   DOM Manipulation, Event Handling, Fetch API, Web Storage

5. Patterns & Best Practices:
   Design Patterns, Clean Code, Error Handling, Performance

6. Framework (bước tiếp theo):
   React, Vue, Angular, Svelte (Frontend)
   Node.js, Express, NestJS (Backend)
```

### 31.2 Tài nguyên học thêm

| Tài nguyên | Link |
|------------|------|
| MDN Web Docs | developer.mozilla.org |
| JavaScript.info | javascript.info |
| Eloquent JavaScript | eloquentjavascript.net |
| You Don't Know JS | github.com/getify/You-Dont-Know-JS |
| ES6+ Cheatsheet | es6-features.org |
| Node.js Docs | nodejs.org/docs |

