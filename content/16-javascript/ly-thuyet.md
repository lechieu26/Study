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

### 1.1 JavaScript la gi?

JavaScript (JS) la ngon ngu lap trinh **dong** (dynamic), **da mo hinh** (multi-paradigm) - ho tro **lap trinh huong doi tuong**, **lap trinh ham**, va **lap trinh huong su kien**. Day la ngon ngu duy nhat chay **native** tren moi trinh duyet web.

### 1.2 Lich su phat trien

| Nam | Su kien |
|-----|---------|
| 1995 | Brendan Eich tao ra JavaScript trong 10 ngay tai Netscape |
| 1997 | ECMAScript 1 - phien ban chuan hoa dau tien |
| 2009 | ECMAScript 5 (ES5) - strict mode, JSON, Array methods |
| 2009 | Node.js ra doi - JavaScript chay tren server |
| 2015 | ECMAScript 6 (ES6/ES2015) - let/const, arrow function, class, Promise, module |
| 2016 | ES2016 - Array.includes(), exponentiation operator |
| 2017 | ES2017 - async/await, Object.entries/values |
| 2020 | ES2020 - Optional chaining, Nullish coalescing, BigInt |
| 2022 | ES2022 - Top-level await, .at(), Object.hasOwn() |
| 2023 | ES2023 - Array findLast/findLastIndex, toSorted/toReversed/toSpliced |

### 1.3 Tai sao hoc JavaScript?

**1. Ngon ngu cua Web:**
JavaScript la ngon ngu duy nhat chay tren trinh duyet. Moi trang web tuong tac deu can JavaScript.

**2. Full-Stack Development:**
Voi Node.js, JavaScript co the lam ca frontend lan backend.

**3. He sinh thai khong lo:**
- **Frontend:** React, Vue, Angular, Svelte
- **Backend:** Node.js, Express, NestJS, Deno, Bun
- **Mobile:** React Native, Ionic, NativeScript
- **Desktop:** Electron (VS Code, Discord, Slack)
- **Database:** MongoDB (JavaScript-based query)

**4. Thi truong viec lam rong lon:**
JavaScript lien tuc la ngon ngu pho bien nhat tren Stack Overflow Survey.

### 1.4 JavaScript Engine

Moi trinh duyet co mot **JavaScript Engine** de thuc thi code:

| Trinh duyet | Engine |
|-------------|--------|
| Chrome, Edge, Opera | V8 |
| Firefox | SpiderMonkey |
| Safari | JavaScriptCore (Nitro) |
| Node.js | V8 |

**Qua trinh thuc thi:**
```
Source Code -> Parser -> AST (Abstract Syntax Tree) -> Interpreter -> Bytecode
                                                           |
                                                    JIT Compiler -> Machine Code (toi uu)
```

### 1.5 JavaScript vs Java

| Tieu chi | JavaScript | Java |
|----------|-----------|------|
| Kieu du lieu | Dong (Dynamic typing) | Tinh (Static typing) |
| Chay tren | Trinh duyet + Node.js | JVM |
| OOP | Prototype-based | Class-based |
| Bien dich | Interpreted / JIT | Compiled to bytecode |
| Su dung chinh | Web development | Enterprise, Android |

---

## 2. Cài đặt và Môi trường Phát triển

### 2.1 Chay JavaScript tren trinh duyet

**Cach 1: Console cua trinh duyet**
Mo Developer Tools (F12) -> Tab Console -> Nhap code truc tiep.

**Cach 2: Nhung vao file HTML**
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
        console.log("Hello tu inline script!");
    </script>

    <!-- External script -->
    <script src="app.js"></script>
</body>
</html>
```

### 2.2 Cai dat Node.js

Node.js cho phep chay JavaScript ngoai trinh duyet:

```bash
# Kiem tra phien ban
node --version    # v20.x.x
npm --version     # 10.x.x

# Chay file JavaScript
node app.js

# Chay REPL (tuong tac)
node
> console.log("Hello")
Hello
```

### 2.3 IDE khuyen dung

**Visual Studio Code** la lua chon pho bien nhat:
- Extension: ESLint, Prettier, JavaScript (ES6) Code Snippets
- Debugger tich hop
- IntelliSense / Autocomplete

### 2.4 Vi tri dat the `<script>`

```html
<!-- Cuoi body (khuyen dung) - DOM da load xong -->
<body>
    <div id="app"></div>
    <script src="app.js"></script>
</body>

<!-- Hoac dung defer/async trong head -->
<head>
    <!-- defer: tai song song, chay sau khi DOM parse xong -->
    <script src="app.js" defer></script>

    <!-- async: tai song song, chay ngay khi tai xong -->
    <script src="analytics.js" async></script>
</head>
```

**So sanh defer vs async:**

| | defer | async |
|---|-------|-------|
| Tai | Song song voi HTML parsing | Song song voi HTML parsing |
| Chay | Sau khi DOM parse xong | Ngay khi file tai xong |
| Thu tu | Dam bao thu tu | Khong dam bao thu tu |
| Dung cho | App logic chinh | Analytics, ads |

---

## 3. Biến và Kiểu dữ liệu

### 3.1 Khai bao bien

JavaScript co 3 cach khai bao bien:

```javascript
// var - function-scoped, co hoisting (ES5)
var name = "JavaScript";

// let - block-scoped, khong hoisting (ES6+) - KHUYEN DUNG
let age = 28;
age = 29; // co the gan lai

// const - block-scoped, khong the gan lai (ES6+) - KHUYEN DUNG
const PI = 3.14159;
// PI = 3.14; // TypeError: Assignment to constant variable
```

**So sanh var, let, const:**

| Dac diem | var | let | const |
|----------|-----|-----|-------|
| Scope | Function | Block | Block |
| Hoisting | Co (gia tri undefined) | Co (TDZ) | Co (TDZ) |
| Gan lai | Co | Co | Khong |
| Khai bao lai | Co | Khong | Khong |
| Su dung | Tranh dung | Bien thay doi | Mac dinh nen dung |

**Temporal Dead Zone (TDZ):**
```javascript
console.log(a); // undefined (var duoc hoisted)
var a = 1;

console.log(b); // ReferenceError: Cannot access 'b' before initialization
let b = 2;
```

### 3.2 Kieu du lieu (Data Types)

JavaScript co **8 kieu du lieu** chia thanh 2 nhom:

**Primitive Types (Kieu nguyen thuy - truyen theo gia tri):**

```javascript
// 1. Number - so nguyen va so thuc
let integer = 42;
let float = 3.14;
let negative = -10;
let infinity = Infinity;
let notANumber = NaN;

// 2. String - chuoi ky tu
let single = 'Hello';
let double = "World";
let template = `Hello ${single}`; // Template literal (ES6)

// 3. Boolean
let isTrue = true;
let isFalse = false;

// 4. undefined - bien da khai bao nhung chua gan gia tri
let x;
console.log(x); // undefined

// 5. null - gia tri rong co chu dich
let empty = null;

// 6. BigInt - so nguyen lon (ES2020)
let bigNum = 9007199254740991n;
let anotherBig = BigInt("9007199254740992");

// 7. Symbol - gia tri duy nhat (ES6)
let sym1 = Symbol("id");
let sym2 = Symbol("id");
console.log(sym1 === sym2); // false - moi Symbol la duy nhat
```

**Reference Types (Kieu tham chieu - truyen theo tham chieu):**

```javascript
// 8. Object - bao gom: Object, Array, Function, Date, RegExp, Map, Set,...
let person = { name: "An", age: 25 };
let numbers = [1, 2, 3];
let greet = function() { return "Hi"; };
let today = new Date();
```

### 3.3 Kiem tra kieu du lieu

```javascript
typeof 42;           // "number"
typeof "hello";      // "string"
typeof true;         // "boolean"
typeof undefined;    // "undefined"
typeof null;         // "object"  !! Bug lich su cua JS
typeof {};           // "object"
typeof [];           // "object"  - Array la object
typeof function(){}; // "function"
typeof Symbol();     // "symbol"
typeof 42n;          // "bigint"

// Kiem tra Array
Array.isArray([1, 2]); // true
Array.isArray({});     // false

// Kiem tra null
let val = null;
val === null; // true
```

### 3.4 Chuyen doi kieu (Type Conversion)

**Chuyen doi tu dong (Implicit Coercion):**
```javascript
// String + Number -> String (noi chuoi)
"5" + 3;        // "53"
"5" + true;     // "5true"

// Cac phep toan khac -> Number
"5" - 3;        // 2
"5" * 2;        // 10
"5" / 2;        // 2.5
true + 1;       // 2
false + 1;      // 1

// So sanh
"5" == 5;       // true  (co coercion)
"5" === 5;      // false (khong coercion - SO SANH NGHIEM NGAT)
```

**Chuyen doi thu cong (Explicit Conversion):**
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
Boolean([]);       // true  !! Mang rong la truthy
Boolean({});       // true  !! Object rong la truthy
!!value;           // Cach ngan gon !! (double NOT)
```

**Truthy va Falsy:**
```javascript
// Falsy values (chi co 8 gia tri):
false, 0, -0, 0n, "", null, undefined, NaN

// Tat ca cac gia tri khac deu la truthy, ke ca:
// [], {}, "0", "false", function(){}, new Date()
```

---

## 4. Toán tử

### 4.1 Toan tu so hoc (Arithmetic)

```javascript
let a = 10, b = 3;
a + b;    // 13 - Cong
a - b;    // 7  - Tru
a * b;    // 30 - Nhan
a / b;    // 3.3333 - Chia
a % b;    // 1  - Chia lay du (Modulo)
a ** b;   // 1000 - Luy thua (ES2016)

// Toan tu tang/giam
let x = 5;
x++;   // Post-increment: tra ve 5, roi tang len 6
++x;   // Pre-increment: tang len 7, roi tra ve 7
x--;   // Post-decrement
--x;   // Pre-decrement
```

### 4.2 Toan tu gan (Assignment)

```javascript
let x = 10;
x += 5;    // x = x + 5   -> 15
x -= 3;    // x = x - 3   -> 12
x *= 2;    // x = x * 2   -> 24
x /= 4;    // x = x / 4   -> 6
x %= 4;    // x = x % 4   -> 2
x **= 3;   // x = x ** 3  -> 8

// Logical assignment (ES2021)
x ||= 5;   // x = x || 5  (gan neu x la falsy)
x &&= 10;  // x = x && 10 (gan neu x la truthy)
x ??= 7;   // x = x ?? 7  (gan neu x la null/undefined)
```

### 4.3 Toan tu so sanh (Comparison)

```javascript
5 == "5";     // true  - So sanh LONG (co type coercion)
5 === "5";    // false - So sanh NGHIEM NGAT (khong coercion) -> NEN DUNG
5 != "5";     // false
5 !== "5";    // true  -> NEN DUNG

10 > 5;       // true
10 >= 10;     // true
5 < 10;       // true
5 <= 5;       // true

// So sanh dac biet
null == undefined;  // true
null === undefined; // false
NaN == NaN;         // false !! NaN khong bang chinh no
Number.isNaN(NaN);  // true - cach kiem tra dung
```

### 4.4 Toan tu logic (Logical)

```javascript
// AND (&&) - tra ve gia tri falsy dau tien, hoac gia tri cuoi
true && true;     // true
true && false;    // false
"hello" && 42;    // 42
0 && "hello";     // 0

// OR (||) - tra ve gia tri truthy dau tien, hoac gia tri cuoi
false || true;    // true
0 || "default";   // "default"
"" || "fallback"; // "fallback"

// NOT (!)
!true;   // false
!0;      // true
!!"hello"; // true (double NOT = chuyen sang boolean)

// Nullish Coalescing (??) - chi kiem tra null/undefined (ES2020)
null ?? "default";      // "default"
undefined ?? "default"; // "default"
0 ?? "default";         // 0  !! Khac voi || (0 la falsy nhung khong phai null/undefined)
"" ?? "default";        // "" !! Chuoi rong khong phai null/undefined
```

### 4.5 Toan tu 3 ngoi (Ternary)

```javascript
let age = 20;
let status = age >= 18 ? "Nguoi lon" : "Tre em";
// Tuong duong:
// if (age >= 18) status = "Nguoi lon";
// else status = "Tre em";

// Co the long nhau (nhung nen tranh de code ro rang)
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
        city: "Ha Noi"
    }
};

// Khong co optional chaining - de gap loi
// user.contact.phone; // TypeError: Cannot read properties of undefined

// Voi optional chaining
user.contact?.phone;       // undefined (khong loi)
user.getInfo?.();          // undefined (goi method an toan)
user.hobbies?.[0];         // undefined (truy cap array an toan)

// Ket hop voi Nullish Coalescing
let city = user.address?.city ?? "Khong ro";  // "Ha Noi"
let phone = user.contact?.phone ?? "N/A";     // "N/A"
```

---

## 5. Câu điều kiện

### 5.1 if...else

```javascript
let score = 85;

if (score >= 90) {
    console.log("Xuat sac");
} else if (score >= 80) {
    console.log("Gioi");
} else if (score >= 70) {
    console.log("Kha");
} else if (score >= 60) {
    console.log("Trung binh");
} else {
    console.log("Yeu");
}
// Output: "Gioi"
```

### 5.2 switch...case

```javascript
let day = new Date().getDay(); // 0-6

switch (day) {
    case 0:
        console.log("Chu nhat");
        break;
    case 1:
        console.log("Thu Hai");
        break;
    case 2:
        console.log("Thu Ba");
        break;
    case 3:
        console.log("Thu Tu");
        break;
    case 4:
        console.log("Thu Nam");
        break;
    case 5:
        console.log("Thu Sau");
        break;
    case 6:
        console.log("Thu Bay");
        break;
    default:
        console.log("Khong hop le");
}

// Nhom nhieu case (fall-through)
switch (day) {
    case 1: case 2: case 3: case 4: case 5:
        console.log("Ngay lam viec");
        break;
    case 0: case 6:
        console.log("Cuoi tuan");
        break;
}
```

### 5.3 Cac ky thuat dieu kien ngan gon

```javascript
// Short-circuit evaluation
let name = userName || "Khach";           // Fallback cho falsy
let name2 = userName ?? "Khach";          // Fallback cho null/undefined

// Guard clause (tra ve som)
function divide(a, b) {
    if (b === 0) return "Khong the chia cho 0";
    return a / b;
}

// Object lookup thay the switch
const dayName = {
    0: "Chu nhat", 1: "Thu Hai", 2: "Thu Ba",
    3: "Thu Tu", 4: "Thu Nam", 5: "Thu Sau", 6: "Thu Bay"
};
console.log(dayName[new Date().getDay()]);
```

---

## 6. Vòng lặp

### 6.1 for

```javascript
// for co ban
for (let i = 0; i < 5; i++) {
    console.log(i); // 0, 1, 2, 3, 4
}

// Duyet mang voi chi so
const fruits = ["Tao", "Cam", "Chuoi"];
for (let i = 0; i < fruits.length; i++) {
    console.log(`${i}: ${fruits[i]}`);
}
```

### 6.2 while va do...while

```javascript
// while - kiem tra dieu kien truoc
let count = 0;
while (count < 3) {
    console.log(count); // 0, 1, 2
    count++;
}

// do...while - thuc hien it nhat 1 lan
let num = 10;
do {
    console.log(num); // 10 (chay 1 lan du dieu kien sai)
    num++;
} while (num < 5);
```

### 6.3 for...of (ES6)

Duyet qua **gia tri** cua iterable (Array, String, Map, Set,...):

```javascript
const colors = ["Do", "Xanh", "Vang"];
for (const color of colors) {
    console.log(color); // "Do", "Xanh", "Vang"
}

// Duyet String
for (const char of "Hello") {
    console.log(char); // "H", "e", "l", "l", "o"
}

// Voi destructuring
const entries = [["name", "An"], ["age", 25]];
for (const [key, value] of entries) {
    console.log(`${key}: ${value}`);
}
```

### 6.4 for...in

Duyet qua **key** (thuoc tinh) cua object:

```javascript
const person = { name: "An", age: 25, city: "HCM" };
for (const key in person) {
    console.log(`${key}: ${person[key]}`);
}
// "name: An", "age: 25", "city: HCM"

// LUU Y: Khong nen dung for...in voi Array vi no duyet ca prototype properties
// va thu tu khong dam bao. Dung for...of hoac forEach cho Array.
```

### 6.5 break va continue

```javascript
// break - thoat khoi vong lap
for (let i = 0; i < 10; i++) {
    if (i === 5) break;
    console.log(i); // 0, 1, 2, 3, 4
}

// continue - bo qua lan lap hien tai
for (let i = 0; i < 5; i++) {
    if (i === 2) continue;
    console.log(i); // 0, 1, 3, 4
}

// Label (nhan) cho vong lap long nhau
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
// Khai bao ham - duoc hoisted (co the goi truoc khi khai bao)
function greet(name) {
    return `Xin chao, ${name}!`;
}

console.log(greet("An")); // "Xin chao, An!"
```

### 7.2 Function Expression

```javascript
// Bieu thuc ham - KHONG duoc hoisted
const greet = function(name) {
    return `Xin chao, ${name}!`;
};

// Named function expression (huu ich cho debugging)
const factorial = function fact(n) {
    return n <= 1 ? 1 : n * fact(n - 1);
};
```

### 7.3 Arrow Function (ES6)

```javascript
// Cu phap ngan gon
const add = (a, b) => a + b;
const square = x => x * x;          // 1 tham so: bo ngoac
const sayHi = () => "Hi!";          // 0 tham so: bat buoc co ()
const getObj = () => ({ key: "value" }); // Tra ve object: boc trong ()

// Arrow function nhieu dong
const calculate = (a, b) => {
    const sum = a + b;
    const product = a * b;
    return { sum, product };
};

// KHAC BIET QUAN TRONG voi regular function:
// 1. Khong co 'this' rieng (ke thua tu scope cha)
// 2. Khong co 'arguments' object
// 3. Khong the dung lam constructor (new)
// 4. Khong co prototype
```

### 7.4 Tham so mac dinh (Default Parameters)

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

// Ket hop voi tham so binh thuong
function log(level, ...messages) {
    messages.forEach(msg => console.log(`[${level}] ${msg}`));
}
log("INFO", "Server started", "Port 3000");
```

### 7.6 IIFE (Immediately Invoked Function Expression)

```javascript
// Ham tu goi - chay ngay khi dinh nghia
(function() {
    let secret = "private";
    console.log("IIFE chay ngay!");
})();

// Arrow IIFE
(() => {
    console.log("Arrow IIFE");
})();

// IIFE voi tham so
((name) => {
    console.log(`Hello ${name}`);
})("JavaScript");
```

### 7.7 Callback Function

```javascript
// Ham truyen nhu tham so cho ham khac
function processArray(arr, callback) {
    const result = [];
    for (const item of arr) {
        result.push(callback(item));
    }
    return result;
}

const doubled = processArray([1, 2, 3], x => x * 2);
// [2, 4, 6]

// Callback trong thuc te
setTimeout(() => console.log("Sau 1 giay"), 1000);

document.getElementById("btn").addEventListener("click", function(event) {
    console.log("Button duoc click!");
});
```

### 7.8 Higher-Order Functions

```javascript
// Ham nhan ham khac lam tham so HOAC tra ve mot ham
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
// Pure function: cung input -> cung output, khong side effects
function add(a, b) {
    return a + b; // Luon tra ve ket qua giong nhau voi cung a, b
}

// Impure function: co side effect (thay doi bien ben ngoai)
let total = 0;
function addToTotal(value) {
    total += value; // Side effect: thay doi bien ben ngoai
    return total;
}
```

---

## 8. Mảng (Arrays)

### 8.1 Tao mang

```javascript
// Cach 1: Array literal (khuyen dung)
const fruits = ["Tao", "Cam", "Chuoi"];

// Cach 2: Array constructor
const numbers = new Array(1, 2, 3);
const empty = new Array(5); // Tao mang co length = 5 (khong co phan tu)

// Cach 3: Array.from()
const chars = Array.from("Hello"); // ["H", "e", "l", "l", "o"]
const range = Array.from({ length: 5 }, (_, i) => i + 1); // [1, 2, 3, 4, 5]

// Cach 4: Array.of()
const arr = Array.of(1, 2, 3); // [1, 2, 3]
```

### 8.2 Truy cap va Sua doi

```javascript
const arr = ["a", "b", "c", "d", "e"];

arr[0];          // "a" - phan tu dau
arr[arr.length - 1]; // "e" - phan tu cuoi
arr.at(-1);      // "e" - phan tu cuoi (ES2022)
arr.at(-2);      // "d" - phan tu ke cuoi

arr[1] = "B";    // Sua phan tu: ["a", "B", "c", "d", "e"]
```

### 8.3 Cac phuong thuc bien doi (Mutating Methods)

```javascript
const arr = [1, 2, 3];

// Them/Xoa o cuoi
arr.push(4);       // [1, 2, 3, 4] - tra ve length moi
arr.pop();         // [1, 2, 3] - tra ve phan tu bi xoa (4)

// Them/Xoa o dau
arr.unshift(0);    // [0, 1, 2, 3] - tra ve length moi
arr.shift();       // [1, 2, 3] - tra ve phan tu bi xoa (0)

// splice(start, deleteCount, ...items)
arr.splice(1, 1);        // [1, 3] - xoa 1 phan tu tai index 1
arr.splice(1, 0, 2);     // [1, 2, 3] - chen 2 tai index 1
arr.splice(1, 1, "a", "b"); // [1, "a", "b", 3] - thay the va chen

// Sap xep
[3, 1, 2].sort();             // [1, 2, 3] - sap xep mac dinh (theo string)
[10, 2, 30].sort();           // [10, 2, 30] !! Sai vi so sanh theo string
[10, 2, 30].sort((a, b) => a - b); // [2, 10, 30] - sap xep so tang dan
[10, 2, 30].sort((a, b) => b - a); // [30, 10, 2] - sap xep so giam dan

// Dao nguoc
[1, 2, 3].reverse(); // [3, 2, 1]

// fill(value, start, end)
[1, 2, 3, 4].fill(0, 1, 3); // [1, 0, 0, 4]
```

### 8.4 Cac phuong thuc khong bien doi (Non-Mutating Methods)

```javascript
const arr = [1, 2, 3, 4, 5];

// concat
arr.concat([6, 7]); // [1, 2, 3, 4, 5, 6, 7]

// slice(start, end) - lay mang con
arr.slice(1, 3);    // [2, 3] (khong bao gom index 3)
arr.slice(-2);      // [4, 5] (2 phan tu cuoi)

// includes
arr.includes(3);    // true

// indexOf / lastIndexOf
arr.indexOf(3);     // 2
arr.lastIndexOf(3); // 2

// join
arr.join("-");      // "1-2-3-4-5"

// flat (ES2019)
[1, [2, [3, [4]]]].flat();     // [1, 2, [3, [4]]] - lam phang 1 cap
[1, [2, [3, [4]]]].flat(Infinity); // [1, 2, 3, 4] - lam phang hoan toan

// Phuong thuc moi khong bien doi (ES2023)
arr.toSorted((a, b) => b - a);   // [5, 4, 3, 2, 1] - arr khong doi
arr.toReversed();                  // [5, 4, 3, 2, 1] - arr khong doi
arr.toSpliced(1, 2, 10, 20);     // [1, 10, 20, 4, 5] - arr khong doi
arr.with(2, 99);                   // [1, 2, 99, 4, 5] - arr khong doi
```

### 8.5 Iteration Methods (Phuong thuc lap)

```javascript
const numbers = [1, 2, 3, 4, 5];

// forEach - lap qua tung phan tu (khong tra ve gi)
numbers.forEach((value, index) => {
    console.log(`${index}: ${value}`);
});

// map - bien doi moi phan tu -> mang moi
const doubled = numbers.map(n => n * 2);
// [2, 4, 6, 8, 10]

// filter - loc phan tu thoa dieu kien -> mang moi
const evens = numbers.filter(n => n % 2 === 0);
// [2, 4]

// reduce - gom mang thanh 1 gia tri
const sum = numbers.reduce((acc, curr) => acc + curr, 0);
// 15

// find - tim phan tu dau tien thoa dieu kien
const found = numbers.find(n => n > 3);
// 4

// findIndex - tim vi tri phan tu dau tien thoa dieu kien
const index = numbers.findIndex(n => n > 3);
// 3

// findLast / findLastIndex (ES2023)
[1, 2, 3, 4, 3].findLast(n => n > 2);      // 3
[1, 2, 3, 4, 3].findLastIndex(n => n > 2);  // 4

// some - co it nhat 1 phan tu thoa dieu kien?
numbers.some(n => n > 4);  // true

// every - tat ca phan tu thoa dieu kien?
numbers.every(n => n > 0); // true

// flatMap - map + flat (1 cap)
[1, 2, 3].flatMap(n => [n, n * 2]);
// [1, 2, 2, 4, 3, 6]
```

### 8.6 Vi du thuc te ket hop cac Array methods

```javascript
const students = [
    { name: "An", score: 85, class: "A" },
    { name: "Binh", score: 72, class: "B" },
    { name: "Cuong", score: 95, class: "A" },
    { name: "Dung", score: 60, class: "B" },
    { name: "Em", score: 88, class: "A" }
];

// Tim sinh vien gioi nhat lop A
const bestInA = students
    .filter(s => s.class === "A")
    .sort((a, b) => b.score - a.score)
    [0];
// { name: "Cuong", score: 95, class: "A" }

// Tinh diem trung binh
const avgScore = students.reduce((sum, s) => sum + s.score, 0) / students.length;
// 80

// Nhom theo lop
const byClass = students.reduce((groups, s) => {
    (groups[s.class] ||= []).push(s);
    return groups;
}, {});
// { A: [...], B: [...] }

// Chuyen doi thanh object { name: score }
const scoreMap = Object.fromEntries(
    students.map(s => [s.name, s.score])
);
// { An: 85, Binh: 72, Cuong: 95, Dung: 60, Em: 88 }
```

---

## 9. Object

### 9.1 Tao Object

```javascript
// Cach 1: Object literal (pho bien nhat)
const person = {
    name: "An",
    age: 25,
    "favorite color": "blue", // Key co dau cach -> dung ngoac kep
    greet() {
        return `Xin chao, toi la ${this.name}`;
    }
};

// Cach 2: Constructor function
function Person(name, age) {
    this.name = name;
    this.age = age;
}
const person2 = new Person("Binh", 30);

// Cach 3: Object.create()
const proto = { greet() { return "Hi"; } };
const obj = Object.create(proto);

// Cach 4: Class (ES6) - xem phan Class
```

### 9.2 Truy cap va Sua doi

```javascript
const person = { name: "An", age: 25 };

// Dot notation
person.name;          // "An"
person.age = 26;      // Sua doi

// Bracket notation (dung khi key la bien hoac co ky tu dac biet)
person["name"];        // "An"
person["favorite color"]; // Truy cap key co dau cach

// Computed property names
const key = "email";
person[key] = "an@mail.com";

// Xoa thuoc tinh
delete person.age;
```

### 9.3 Object Methods (Phuong thuc tinh)

```javascript
const person = { name: "An", age: 25, city: "HCM" };

// Lay danh sach keys
Object.keys(person);    // ["name", "age", "city"]

// Lay danh sach values
Object.values(person);  // ["An", 25, "HCM"]

// Lay danh sach [key, value] pairs
Object.entries(person); // [["name","An"], ["age",25], ["city","HCM"]]

// Tao object tu entries
Object.fromEntries([["a", 1], ["b", 2]]); // { a: 1, b: 2 }

// Copy / Merge objects (shallow copy)
const copy = Object.assign({}, person);
const merged = Object.assign({}, person, { email: "an@mail.com" });

// Spread operator (ES6) - cach khuyen dung
const copy2 = { ...person };
const merged2 = { ...person, email: "an@mail.com" };

// Dong bang object
Object.freeze(person);       // Khong the them/sua/xoa (shallow)
Object.isFrozen(person);     // true

Object.seal(person);         // Co the sua, khong the them/xoa
Object.isSealed(person);     // true

// Kiem tra thuoc tinh
"name" in person;                    // true
person.hasOwnProperty("name");       // true
Object.hasOwn(person, "name");       // true (ES2022 - khuyen dung)
```

### 9.4 Destructuring Object

```javascript
const person = { name: "An", age: 25, city: "HCM", country: "VN" };

// Destructuring co ban
const { name, age } = person;
console.log(name, age); // "An" 25

// Doi ten bien
const { name: fullName, age: years } = person;

// Gia tri mac dinh
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

### 9.5 Property Shorthand va Computed Properties

```javascript
const name = "An";
const age = 25;

// Property shorthand
const person = { name, age }; // { name: "An", age: 25 }

// Method shorthand
const obj = {
    greet() { return "Hi"; },       // Thay vi greet: function() {}
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

### 10.1 Tao String

```javascript
const s1 = 'Single quotes';
const s2 = "Double quotes";
const s3 = `Template literal ${s1}`; // Co the nhung bieu thuc

// Multi-line string
const multiLine = `Dong 1
Dong 2
Dong 3`;
```

### 10.2 Cac phuong thuc String quan trong

```javascript
const str = "Hello, JavaScript World!";

// Tim kiem
str.indexOf("JavaScript");     // 7
str.lastIndexOf("o");          // 19
str.includes("Java");          // true
str.startsWith("Hello");       // true
str.endsWith("!");             // true
str.search(/java/i);           // 7 (regex, khong phan biet hoa thuong)

// Truy cap
str.charAt(0);                 // "H"
str[0];                        // "H"
str.at(-1);                    // "!" (ES2022)
str.charCodeAt(0);             // 72 (ma ASCII)

// Cat chuoi
str.slice(7, 17);              // "JavaScript"
str.slice(-6);                 // "orld!"
str.substring(7, 17);          // "JavaScript"

// Thay the
str.replace("World", "VN");     // "Hello, JavaScript VN!" (thay 1 lan)
str.replaceAll("l", "L");       // "HeLLo, JavaScript WorLd!"
str.replace(/[aeiou]/g, "*");   // "H*ll*, J*v*Scr*pt W*rld!" (regex)

// Chuyen doi
str.toUpperCase();              // "HELLO, JAVASCRIPT WORLD!"
str.toLowerCase();              // "hello, javascript world!"

// Cat khoang trang
"  Hello  ".trim();            // "Hello"
"  Hello  ".trimStart();       // "Hello  "
"  Hello  ".trimEnd();         // "  Hello"

// Tach va noi
"a-b-c".split("-");            // ["a", "b", "c"]
["a", "b", "c"].join("-");     // "a-b-c"

// Lap lai
"Ha".repeat(3);                // "HaHaHa"

// Dem do dai
"Hello".padStart(10, "*");     // "*****Hello"
"Hello".padEnd(10, "-");       // "Hello-----"
```

### 10.3 Template Literals (ES6)

```javascript
const name = "An";
const age = 25;

// Nhung bieu thuc
const msg = `${name} la ${age} tuoi, nam sau la ${age + 1} tuoi`;

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
const output = highlight`Xin chao ${name}, ban ${age} tuoi`;
// "Xin chao <b>An</b>, ban <b>25</b> tuoi"
```

---

## 11. Scope và Hoisting

### 11.1 Cac loai Scope

```javascript
// 1. Global Scope - truy cap duoc o moi noi
var globalVar = "global";
let globalLet = "global";

// 2. Function Scope - chi truy cap trong ham
function myFunc() {
    var localVar = "local"; // Chi ton tai trong ham
    console.log(globalVar);  // OK - truy cap duoc global
}
// console.log(localVar); // ReferenceError

// 3. Block Scope - chi truy cap trong block {}
if (true) {
    let blockLet = "block";
    const blockConst = "block";
    var blockVar = "NOT block scoped!"; // var khong co block scope!
}
// console.log(blockLet);   // ReferenceError
// console.log(blockConst); // ReferenceError
console.log(blockVar);      // "NOT block scoped!" - var thoat khoi block

// 4. Module Scope (ES6 Modules)
// Moi file module co scope rieng, khong o global
```

### 11.2 Hoisting

JavaScript "nang" cac khai bao len dau scope truoc khi thuc thi:

```javascript
// --- var hoisting ---
console.log(x); // undefined (khai bao duoc hoisted, khong phai gia tri)
var x = 5;
// Tuong duong:
// var x;
// console.log(x); // undefined
// x = 5;

// --- Function declaration hoisting ---
sayHi(); // "Hi!" - goi truoc khi khai bao duoc!
function sayHi() {
    console.log("Hi!");
}

// --- let/const - Temporal Dead Zone (TDZ) ---
// console.log(y); // ReferenceError: Cannot access 'y' before initialization
let y = 10;

// --- Function expression KHONG duoc hoisted ---
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
        console.log(innerVar);  // OK - scope hien tai
        console.log(outerVar);  // OK - scope cha
        console.log(global);    // OK - global scope
    }

    inner();
    // console.log(innerVar); // ReferenceError - khong truy cap duoc scope con
}

outer();
```

---

## 12. Closure

### 12.1 Closure la gi?

Closure la mot ham co the **nho va truy cap** cac bien tu scope ben ngoai (lexical scope) ngay ca khi ham do duoc thuc thi o noi khac.

```javascript
function createCounter() {
    let count = 0; // Bien nay duoc "dong lai" (enclosed) trong closure

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

// `count` van ton tai va duoc cap nhat du createCounter() da chay xong
// Khong the truy cap count truc tiep tu ben ngoai -> ENCAPSULATION
```

### 12.2 Ung dung cua Closure

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
// account.balance;      // undefined - khong truy cap truc tiep duoc!

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

// 3. Memoization (Cache ket qua tinh toan)
function memoize(fn) {
    const cache = {};
    return function(...args) {
        const key = JSON.stringify(args);
        if (cache[key] !== undefined) {
            console.log("Tu cache!");
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
memoFib(40); // Rat nhanh nho cache

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

### 12.3 Bay thuong gap voi Closure trong Loop

```javascript
// SAI - tat ca deu in ra 3 vi var la function-scoped
for (var i = 0; i < 3; i++) {
    setTimeout(() => console.log(i), 100);
}
// Output: 3, 3, 3

// DUNG - Cach 1: Dung let (block-scoped)
for (let i = 0; i < 3; i++) {
    setTimeout(() => console.log(i), 100);
}
// Output: 0, 1, 2

// DUNG - Cach 2: Dung IIFE tao scope moi
for (var i = 0; i < 3; i++) {
    ((j) => {
        setTimeout(() => console.log(j), 100);
    })(i);
}
// Output: 0, 1, 2
```

---

## 13. this Keyword

### 13.1 this trong cac ngu canh khac nhau

```javascript
// 1. Global context
console.log(this); // window (browser) hoac global (Node.js)
// Trong strict mode: undefined

// 2. Object method - this tro den object so huu method
const person = {
    name: "An",
    greet() {
        console.log(this.name); // "An"
    }
};
person.greet();

// 3. Regular function - this phu thuoc vao cach goi
function showThis() {
    console.log(this);
}
showThis();        // window (non-strict) / undefined (strict)
person.greet();    // person object

// 4. Arrow function - this ke thua tu scope cha (lexical this)
const obj = {
    name: "An",
    greet: () => {
        console.log(this.name); // undefined! this la global, khong phai obj
    },
    delayGreet() {
        setTimeout(() => {
            console.log(this.name); // "An" - arrow function ke thua this tu delayGreet
        }, 100);
    }
};

// 5. Constructor - this tro den object moi tao
function User(name) {
    this.name = name;
}
const user = new User("An"); // this = {} moi

// 6. Event handler - this tro den element nhan su kien
button.addEventListener("click", function() {
    console.log(this); // <button> element
});
button.addEventListener("click", () => {
    console.log(this); // window! Arrow function khong co this rieng
});
```

### 13.2 call(), apply(), bind()

```javascript
function greet(greeting, punctuation) {
    return `${greeting}, ${this.name}${punctuation}`;
}

const person = { name: "An" };

// call - goi ham voi this chi dinh, tham so rieng le
greet.call(person, "Xin chao", "!"); // "Xin chao, An!"

// apply - giong call nhung tham so la mang
greet.apply(person, ["Xin chao", "!"]); // "Xin chao, An!"

// bind - tao ham moi voi this co dinh (khong goi ngay)
const greetAn = greet.bind(person, "Hello");
greetAn("!");     // "Hello, An!"
greetAn("...");   // "Hello, An..."
```

---

## 14. Prototype và Kế thừa

### 14.1 Prototype Chain

Moi object trong JavaScript co mot **[[Prototype]]** (prototype an) tro den mot object khac. Khi truy cap thuoc tinh khong ton tai, JS tim theo chuoi prototype.

```javascript
const animal = {
    eat() { return "eating"; }
};

const dog = Object.create(animal);
dog.bark = function() { return "woof!"; };

dog.bark(); // "woof!" - tim thay tren dog
dog.eat();  // "eating" - khong co tren dog -> tim len prototype (animal)

// Prototype chain: dog -> animal -> Object.prototype -> null
```

### 14.2 Constructor Function va Prototype

```javascript
function Person(name, age) {
    this.name = name;
    this.age = age;
}

// Method duoc chia se qua prototype (tiet kiem bo nho)
Person.prototype.greet = function() {
    return `Xin chao, toi la ${this.name}`;
};

Person.prototype.getAge = function() {
    return this.age;
};

const an = new Person("An", 25);
const binh = new Person("Binh", 30);

an.greet();  // "Xin chao, toi la An"
binh.greet(); // "Xin chao, toi la Binh"

// Ca hai dung chung method
an.greet === binh.greet; // true
```

### 14.3 Ke thua qua Prototype

```javascript
function Animal(name) {
    this.name = name;
}
Animal.prototype.speak = function() {
    return `${this.name} makes a sound`;
};

function Dog(name, breed) {
    Animal.call(this, name); // Goi constructor cha
    this.breed = breed;
}

// Thiet lap ke thua
Dog.prototype = Object.create(Animal.prototype);
Dog.prototype.constructor = Dog;

Dog.prototype.bark = function() {
    return `${this.name} barks!`;
};

const rex = new Dog("Rex", "Husky");
rex.speak(); // "Rex makes a sound" (ke thua tu Animal)
rex.bark();  // "Rex barks!" (rieng cua Dog)
rex instanceof Dog;    // true
rex instanceof Animal; // true
```

---

## 15. ES6+ Features

### 15.1 let va const

```javascript
// Block scoping
{
    let x = 1;
    const y = 2;
}
// x, y khong truy cap duoc o day

// const voi object/array - van co the thay doi noi dung
const arr = [1, 2, 3];
arr.push(4);     // OK - thay doi noi dung
// arr = [5, 6]; // Error - khong the gan lai

const obj = { name: "An" };
obj.age = 25;    // OK - them thuoc tinh
// obj = {};     // Error - khong the gan lai
```

### 15.2 Arrow Functions

```javascript
// Xem chi tiet tai phan 7.3
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

### 15.6 Promise (xem chi tiet phan 17)

### 15.7 Default Parameters, Rest/Spread (xem cac phan truoc)

### 15.8 Symbol

```javascript
const id = Symbol("id");
const obj = { [id]: 123, name: "An" };
obj[id]; // 123

// Symbol khong hien thi trong for...in hoac Object.keys()
Object.keys(obj);                  // ["name"]
Object.getOwnPropertySymbols(obj); // [Symbol(id)]
```

### 15.9 Cac tinh nang ES2020+

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
console.log(original.nested.b); // 2 (khong bi anh huong)

// Object.groupBy (ES2024)
const people = [
    { name: "An", age: 25 },
    { name: "Binh", age: 30 },
    { name: "Cuong", age: 25 }
];
const grouped = Object.groupBy(people, p => p.age);
// { 25: [{name:"An",...}, {name:"Cuong",...}], 30: [{name:"Binh",...}] }
```

---

## 16. Destructuring và Spread/Rest

### 16.1 Array Destructuring

```javascript
const colors = ["do", "xanh", "vang", "tim"];

// Co ban
const [first, second] = colors;   // "do", "xanh"

// Bo qua phan tu
const [, , third] = colors;       // "vang"

// Gia tri mac dinh
const [a, b, c, d, e = "trang"] = colors;

// Rest pattern
const [head, ...tail] = colors;   // "do", ["xanh", "vang", "tim"]

// Swap bien
let x = 1, y = 2;
[x, y] = [y, x];  // x = 2, y = 1
```

### 16.2 Object Destructuring

```javascript
const person = { name: "An", age: 25, city: "HCM" };

const { name, age } = person;
const { name: fullName } = person;    // Doi ten
const { phone = "N/A" } = person;     // Gia tri mac dinh
const { name, ...rest } = person;     // Rest

// Nested
const { address: { city } } = { address: { city: "HN" } };

// Trong tham so ham
function greet({ name, age }) {
    return `${name}, ${age} tuoi`;
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
// { theme: "dark", lang: "vi" } - userPrefs ghi de defaults

// Spread vao tham so ham
const nums = [1, 2, 3];
Math.max(...nums); // 3
```

---

## 17. Promise và Async/Await

### 17.1 Callback Hell

```javascript
// Van de: callback long nhieu cap -> kho doc, kho bao tri
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

Promise dai dien cho mot gia tri **co the co trong tuong lai**. Co 3 trang thai:
- **Pending**: Dang cho xu ly
- **Fulfilled**: Thanh cong
- **Rejected**: That bai

```javascript
// Tao Promise
const promise = new Promise((resolve, reject) => {
    const success = true;
    setTimeout(() => {
        if (success) {
            resolve("Thanh cong!"); // Chuyen sang fulfilled
        } else {
            reject("That bai!");    // Chuyen sang rejected
        }
    }, 1000);
});

// Su dung Promise
promise
    .then(result => {
        console.log(result);  // "Thanh cong!"
        return "Buoc tiep theo";
    })
    .then(next => {
        console.log(next);    // "Buoc tiep theo"
    })
    .catch(error => {
        console.error(error); // Xu ly loi
    })
    .finally(() => {
        console.log("Luon chay!"); // Chay bat ke thanh cong hay that bai
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
        console.log(`Co ${posts.length} bai viet`);
    })
    .catch(error => {
        console.error("Loi:", error);
    });
```

### 17.4 Promise Static Methods

```javascript
// Promise.all - cho TAT CA hoan thanh (hoac 1 loi -> reject ngay)
const results = await Promise.all([
    fetch("/api/users"),
    fetch("/api/posts"),
    fetch("/api/comments")
]);
// [Response, Response, Response]

// Promise.allSettled - cho TAT CA hoan thanh (khong quan tam loi)
const results2 = await Promise.allSettled([
    Promise.resolve("OK"),
    Promise.reject("Loi"),
    Promise.resolve("OK 2")
]);
// [
//   { status: "fulfilled", value: "OK" },
//   { status: "rejected", reason: "Loi" },
//   { status: "fulfilled", value: "OK 2" }
// ]

// Promise.race - tra ve ket qua CUA PROMISE NHANH NHAT
const fast = await Promise.race([
    fetch("/api/server1"),
    fetch("/api/server2")
]);

// Promise.any - tra ve ket qua CUA PROMISE THANH CONG DAU TIEN
const firstSuccess = await Promise.any([
    fetch("/api/slow"),
    fetch("/api/fast"),
    fetch("/api/fail")
]);

// Promise.resolve / reject - tao promise da giai quyet
const resolved = Promise.resolve(42);
const rejected = Promise.reject("Error");
```

### 17.5 Async/Await (ES2017)

Cach viet bat dong bo **giong dong bo**, dua tren Promise:

```javascript
// async function luon tra ve Promise
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
        console.error("Loi:", error.message);
        throw error; // Re-throw de caller xu ly
    }
}

// Goi async function
fetchUserData(1)
    .then(data => console.log(data))
    .catch(err => console.error(err));

// Hoac dung await (trong async function khac)
async function main() {
    const data = await fetchUserData(1);
    console.log(data);
}
```

### 17.6 Xu ly song song voi async/await

```javascript
// SAI - tuan tu (cham)
async function sequential() {
    const user = await fetchUser();   // Cho 1s
    const posts = await fetchPosts(); // Cho them 1s
    // Tong: 2s
}

// DUNG - song song (nhanh)
async function parallel() {
    const [user, posts] = await Promise.all([
        fetchUser(),   // Bat dau ngay
        fetchPosts()   // Bat dau ngay
    ]);
    // Tong: ~1s (song song)
}

// Su dung for...of voi await (tuan tu co chu dich)
async function processItems(items) {
    for (const item of items) {
        await processItem(item); // Xu ly tung cai mot
    }
}

// Luu y: forEach KHONG hoat dong voi async/await
// items.forEach(async (item) => { await process(item); }); // SAI!
```

### 17.7 Top-Level Await (ES2022)

```javascript
// Trong ES Modules, co the dung await o cap cao nhat
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
    // Code co the gay loi
    const data = JSON.parse("invalid json");
} catch (error) {
    // Xu ly loi
    console.error("Loi:", error.message);
    console.error("Loai:", error.name);    // SyntaxError
    console.error("Stack:", error.stack);   // Stack trace
} finally {
    // Luon chay, bat ke co loi hay khong
    console.log("Hoan tat");
}
```

### 18.2 Cac loai Error

```javascript
// SyntaxError - loi cu phap
// eval("var a = ;");

// ReferenceError - bien chua duoc khai bao
// console.log(undeclaredVar);

// TypeError - sai kieu du lieu
// null.toString();
// (5).toUpperCase();

// RangeError - gia tri ngoai pham vi
// new Array(-1);

// URIError - sai dinh dang URI
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
        super(`${resource} voi id ${id} khong ton tai`);
        this.name = "NotFoundError";
        this.resource = resource;
        this.id = id;
    }
}

function validateAge(age) {
    if (typeof age !== "number") {
        throw new ValidationError("Age phai la so", "age");
    }
    if (age < 0 || age > 150) {
        throw new ValidationError("Age phai tu 0 den 150", "age");
    }
}

try {
    validateAge("abc");
} catch (error) {
    if (error instanceof ValidationError) {
        console.log(`Loi validation truong ${error.field}: ${error.message}`);
    } else {
        throw error; // Re-throw neu khong phai loi da biet
    }
}
```

### 18.4 Error Handling voi Async/Await

```javascript
// Cach 1: try/catch
async function fetchData() {
    try {
        const response = await fetch("/api/data");
        if (!response.ok) throw new Error(`HTTP ${response.status}`);
        return await response.json();
    } catch (error) {
        console.error("Fetch failed:", error);
        return null; // Gia tri mac dinh
    }
}

// Cach 2: Wrapper function
async function safeAsync(asyncFn) {
    try {
        const data = await asyncFn();
        return [data, null];
    } catch (error) {
        return [null, error];
    }
}

const [data, error] = await safeAsync(() => fetch("/api/data").then(r => r.json()));
if (error) console.error("Loi:", error);
```

---

## 19. DOM Manipulation

### 19.1 Chon phan tu (Selecting Elements)

```javascript
// Theo ID
const header = document.getElementById("header");

// Theo CSS selector (tra ve phan tu dau tien)
const firstBtn = document.querySelector(".btn");
const nav = document.querySelector("nav > ul");

// Theo CSS selector (tra ve tat ca - NodeList)
const allBtns = document.querySelectorAll(".btn");
// Chuyen sang Array
const btnsArray = [...document.querySelectorAll(".btn")];

// Theo class name (tra ve HTMLCollection - live)
const items = document.getElementsByClassName("item");

// Theo tag name
const paragraphs = document.getElementsByTagName("p");
```

### 19.2 Thay doi noi dung

```javascript
const el = document.querySelector("#content");

// Text content (chi text, an toan voi XSS)
el.textContent = "Hello World";

// Inner HTML (parse HTML - CAN THAN XSS!)
el.innerHTML = "<strong>Bold text</strong>";

// Outer HTML (thay the ca element)
el.outerHTML = "<div id='new'>New element</div>";
```

### 19.3 Thay doi Style va Class

```javascript
const el = document.querySelector(".box");

// Inline style
el.style.backgroundColor = "red";
el.style.fontSize = "20px";
el.style.display = "none";

// ClassList API (khuyen dung)
el.classList.add("active");           // Them class
el.classList.remove("hidden");        // Xoa class
el.classList.toggle("visible");       // Toggle class
el.classList.contains("active");      // Kiem tra co class
el.classList.replace("old", "new");   // Thay the class

// className (thay the toan bo class)
el.className = "box active";
```

### 19.4 Thuoc tinh (Attributes)

```javascript
const link = document.querySelector("a");

link.getAttribute("href");              // Lay gia tri
link.setAttribute("href", "https://example.com"); // Dat gia tri
link.removeAttribute("target");         // Xoa
link.hasAttribute("rel");               // Kiem tra

// Data attributes
// <div data-user-id="123" data-role="admin">
const el = document.querySelector("div");
el.dataset.userId;       // "123"
el.dataset.role;         // "admin"
el.dataset.newProp = "value"; // Them data-new-prop
```

### 19.5 Tao va Them phan tu

```javascript
// Tao element
const div = document.createElement("div");
div.className = "card";
div.textContent = "New Card";

// Them vao DOM
document.body.appendChild(div);              // Them vao cuoi
document.body.prepend(div);                  // Them vao dau
parent.insertBefore(div, referenceNode);     // Chen truoc mot node

// insertAdjacentHTML - chen HTML tai vi tri cu the
element.insertAdjacentHTML("beforebegin", "<p>Truoc element</p>");
element.insertAdjacentHTML("afterbegin", "<p>Dau element</p>");
element.insertAdjacentHTML("beforeend", "<p>Cuoi element</p>");
element.insertAdjacentHTML("afterend", "<p>Sau element</p>");

// Xoa element
element.remove();                             // Xoa chinh no
parent.removeChild(child);                    // Xoa element con

// Clone element
const clone = element.cloneNode(true);        // Deep clone (ca children)
const shallow = element.cloneNode(false);     // Shallow clone
```

### 19.6 Document Fragment

```javascript
// Toi uu: gom nhieu thao tac DOM thanh 1 lan
const fragment = document.createDocumentFragment();

for (let i = 0; i < 1000; i++) {
    const li = document.createElement("li");
    li.textContent = `Item ${i}`;
    fragment.appendChild(li); // Them vao fragment (khong reflow)
}

document.querySelector("ul").appendChild(fragment); // 1 lan reflow duy nhat
```

---

## 20. Event Handling

### 20.1 addEventListener

```javascript
const button = document.querySelector("#myBtn");

// Them event listener
button.addEventListener("click", function(event) {
    console.log("Clicked!", event.target);
});

// Arrow function
button.addEventListener("click", (e) => {
    console.log("Clicked!", e.target);
});

// Named function (de go bo)
function handleClick(e) {
    console.log("Clicked!");
}
button.addEventListener("click", handleClick);
button.removeEventListener("click", handleClick); // Go bo

// Options
button.addEventListener("click", handler, {
    once: true,      // Chi chay 1 lan roi tu dong go bo
    capture: true,   // Bat su kien o pha capturing
    passive: true    // Handler se khong goi preventDefault()
});
```

### 20.2 Event Object

```javascript
element.addEventListener("click", function(event) {
    event.target;          // Element duoc click truc tiep
    event.currentTarget;   // Element dang xu ly su kien (co listener)
    event.type;            // "click"
    event.timeStamp;       // Thoi diem su kien xay ra
    event.clientX;         // Toa do X (viewport)
    event.clientY;         // Toa do Y (viewport)
    event.pageX;           // Toa do X (trang)
    event.pageY;           // Toa do Y (trang)

    event.preventDefault();   // Ngan hanh vi mac dinh (vd: submit form)
    event.stopPropagation();  // Ngan su kien lan truyen (bubbling)
});
```

### 20.3 Event Bubbling va Capturing

```javascript
// Event Bubbling (mac dinh): event lan tu element con len element cha
// Click vao <button> trong <div>: button -> div -> body -> html -> document

// Event Capturing: nguoc lai, tu ngoai vao trong
// document -> html -> body -> div -> button

document.querySelector(".parent").addEventListener("click", () => {
    console.log("Parent clicked (bubbling)");
}); // Mac dinh: bubbling

document.querySelector(".parent").addEventListener("click", () => {
    console.log("Parent clicked (capturing)");
}, true); // true = capturing phase

// Ngan lan truyen
child.addEventListener("click", (e) => {
    e.stopPropagation(); // Khong lan len parent
});
```

### 20.4 Event Delegation

Thay vi gan event cho tung element con, gan cho element cha:

```javascript
// KHONG TOT: gan cho moi li
document.querySelectorAll("li").forEach(li => {
    li.addEventListener("click", handleClick);
});

// TOT HON: Event Delegation
document.querySelector("ul").addEventListener("click", function(e) {
    const li = e.target.closest("li");
    if (li && this.contains(li)) {
        console.log("Clicked:", li.textContent);
    }
});
// Uu diem:
// - It listener hon (tiet kiem bo nho)
// - Tu dong hoat dong voi element dong (them sau)
// - De quan ly
```

### 20.5 Cac su kien pho bien

```javascript
// Mouse events
element.addEventListener("click", handler);      // Click
element.addEventListener("dblclick", handler);    // Double click
element.addEventListener("mouseenter", handler);  // Hover vao (khong bubble)
element.addEventListener("mouseleave", handler);  // Hover ra (khong bubble)
element.addEventListener("mousemove", handler);   // Di chuyen chuot
element.addEventListener("contextmenu", handler); // Click phai

// Keyboard events
document.addEventListener("keydown", (e) => {
    console.log(e.key);     // "Enter", "a", "Escape",...
    console.log(e.code);    // "Enter", "KeyA", "Escape",...
    console.log(e.ctrlKey); // true neu giu Ctrl
    console.log(e.shiftKey);
    console.log(e.altKey);
});

// Form events
form.addEventListener("submit", (e) => {
    e.preventDefault(); // Ngan submit mac dinh
    const formData = new FormData(form);
});
input.addEventListener("input", handler);   // Moi thay doi
input.addEventListener("change", handler);  // Mat focus sau thay doi
input.addEventListener("focus", handler);   // Focus vao
input.addEventListener("blur", handler);    // Mat focus

// Document events
document.addEventListener("DOMContentLoaded", handler); // DOM ready
window.addEventListener("load", handler);                // Tat ca tai xong
window.addEventListener("resize", handler);              // Resize cua so
window.addEventListener("scroll", handler);              // Scroll
```

---

## 21. Fetch API và AJAX

### 21.1 Fetch co ban

```javascript
// GET request
const response = await fetch("https://api.example.com/users");
const data = await response.json();

// Kiem tra response
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

### 21.2 Xu ly cac loai response

```javascript
const res = await fetch(url);

res.json();    // Parse JSON
res.text();    // Plain text
res.blob();    // Binary (image, file)
res.formData(); // Form data
res.arrayBuffer(); // Raw binary

// Response properties
res.ok;        // true neu status 200-299
res.status;    // 200, 404, 500,...
res.statusText; // "OK", "Not Found",...
res.headers;   // Headers object
res.url;       // URL cuoi cung (sau redirect)
```

### 21.3 AbortController - Huy request

```javascript
const controller = new AbortController();

// Bat dau fetch voi signal
fetch("/api/data", { signal: controller.signal })
    .then(res => res.json())
    .then(data => console.log(data))
    .catch(err => {
        if (err.name === "AbortError") {
            console.log("Request bi huy");
        }
    });

// Huy request sau 5 giay
setTimeout(() => controller.abort(), 5000);

// Hoac dung AbortSignal.timeout (moi hon)
fetch("/api/data", { signal: AbortSignal.timeout(5000) });
```

### 21.4 Vi du thuc te: Fetch Wrapper

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

---

## 22. Web Storage API

### 22.1 localStorage

Du lieu luu tru **vinh vien** (cho den khi xoa thu cong):

```javascript
// Luu
localStorage.setItem("name", "An");
localStorage.setItem("user", JSON.stringify({ name: "An", age: 25 }));

// Doc
const name = localStorage.getItem("name"); // "An"
const user = JSON.parse(localStorage.getItem("user"));

// Xoa
localStorage.removeItem("name");   // Xoa 1 item
localStorage.clear();               // Xoa tat ca

// Kiem tra
localStorage.length;                // So luong items
localStorage.key(0);                // Key tai index 0
```

### 22.2 sessionStorage

Giong localStorage nhung **chi ton tai trong phien lam viec** (dong tab = mat):

```javascript
sessionStorage.setItem("token", "abc123");
const token = sessionStorage.getItem("token");
sessionStorage.removeItem("token");
```

### 22.3 So sanh

| Dac diem | localStorage | sessionStorage | Cookie |
|----------|-------------|----------------|--------|
| Dung luong | ~5-10 MB | ~5-10 MB | ~4 KB |
| Het han | Khong | Dong tab | Co the set |
| Gui len server | Khong | Khong | Tu dong gui |
| Truy cap | Client-side | Client-side | Client + Server |

### 22.4 Storage Wrapper voi Type Safety

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

### 23.1 Tao Regex

```javascript
// Cach 1: Literal
const regex1 = /pattern/flags;

// Cach 2: Constructor (khi pattern la bien)
const regex2 = new RegExp("pattern", "flags");
```

### 23.2 Flags

| Flag | Mo ta |
|------|-------|
| `g` | Global - tim tat ca, khong dung lai o ket qua dau |
| `i` | Case-insensitive - khong phan biet hoa thuong |
| `m` | Multiline - ^ va $ match dau/cuoi moi dong |
| `s` | Dotall - dau . match ca ky tu xuong dong |
| `u` | Unicode - ho tro Unicode day du |
| `d` | hasIndices - tra ve chi so vi tri cua match |

### 23.3 Cac ky tu dac biet

```javascript
// Meta characters
.      // Bat ky ky tu nao (tru newline)
\d     // Chu so [0-9]
\D     // Khong phai chu so
\w     // Word character [a-zA-Z0-9_]
\W     // Khong phai word character
\s     // Khoang trang (space, tab, newline)
\S     // Khong phai khoang trang
\b     // Bien gioi tu (word boundary)

// Quantifiers (so luong)
*      // 0 hoac nhieu lan
+      // 1 hoac nhieu lan
?      // 0 hoac 1 lan
{n}    // Chinh xac n lan
{n,}   // It nhat n lan
{n,m}  // Tu n den m lan

// Anchors
^      // Dau chuoi
$      // Cuoi chuoi

// Groups
(abc)    // Capture group
(?:abc)  // Non-capture group
(?<name>abc) // Named capture group
a|b      // Hoac a hoac b

// Character class
[abc]    // a, b, hoac c
[^abc]   // Khong phai a, b, c
[a-z]    // a den z
[A-Z0-9] // A-Z hoac 0-9
```

### 23.4 Phuong thuc Regex

```javascript
const str = "Hello World 123";

// test() - kiem tra co match khong
/\d+/.test(str);          // true

// exec() - lay thong tin chi tiet ve match
/(\d+)/.exec(str);        // ["123", "123", index: 12]

// String methods voi regex
str.match(/\d+/g);        // ["123"]
str.matchAll(/(\w+)/g);   // Iterator cua tat ca matches
str.search(/world/i);     // 6 (vi tri tim thay)
str.replace(/world/i, "JS"); // "Hello JS 123"
str.split(/\s+/);         // ["Hello", "World", "123"]
```

### 23.5 Vi du thuc te

```javascript
// Validate email
const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
emailRegex.test("an@mail.com"); // true

// Validate so dien thoai VN
const phoneRegex = /^(0|\+84)(3|5|7|8|9)\d{8}$/;
phoneRegex.test("0912345678"); // true

// Trich xuat so tu chuoi
"Gia: 100.000d, Giam: 20.000d".match(/[\d.]+/g);
// ["100.000", "20.000"]

// Thay the nhieu khoang trang
"Hello    World   JS".replace(/\s+/g, " ");
// "Hello World JS"

// Named groups
const dateStr = "2024-01-15";
const { year, month, day } = dateStr.match(
    /(?<year>\d{4})-(?<month>\d{2})-(?<day>\d{2})/
).groups;
// year: "2024", month: "01", day: "15"

// Password validation (it nhat 8 ky tu, co hoa, thuong, so, ky tu dac biet)
const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
```

---

## 24. Module System

### 24.1 ES Modules (ESM) - Chuan hien dai

```javascript
// --- math.js ---
// Named exports
export const PI = 3.14159;
export function add(a, b) { return a + b; }
export function subtract(a, b) { return a - b; }

// Default export (moi file chi co 1)
export default class Calculator {
    add(a, b) { return a + b; }
}

// --- app.js ---
// Named imports
import { add, subtract, PI } from "./math.js";

// Doi ten khi import
import { add as sum } from "./math.js";

// Default import (dat ten tuy y)
import Calculator from "./math.js";
import Calc from "./math.js"; // Cung duoc

// Import tat ca
import * as math from "./math.js";
math.add(1, 2);
math.PI;

// Dynamic import (lazy loading)
const module = await import("./heavy-module.js");
module.doSomething();

// Hoac voi then
import("./heavy-module.js")
    .then(module => module.doSomething());
```

### 24.2 CommonJS (CJS) - Dung trong Node.js

```javascript
// --- math.js ---
const PI = 3.14159;
function add(a, b) { return a + b; }

module.exports = { PI, add };
// hoac
exports.PI = PI;
exports.add = add;

// --- app.js ---
const { PI, add } = require("./math");
const math = require("./math");
```

### 24.3 So sanh ESM va CJS

| Dac diem | ESM | CJS |
|----------|-----|-----|
| Cu phap | import/export | require/module.exports |
| Loading | Bat dong bo | Dong bo |
| Phan tich | Tinh (static) | Dong (runtime) |
| Tree-shaking | Co | Khong |
| Browser | Ho tro native | Can bundler |
| Node.js | .mjs hoac "type":"module" | Mac dinh |

### 24.4 Su dung ESM trong HTML

```html
<script type="module" src="app.js"></script>
<script type="module">
    import { greet } from "./utils.js";
    greet("An");
</script>
```

---

## 25. Class (ES6)

### 25.1 Khai bao Class

```javascript
class Person {
    // Constructor
    constructor(name, age) {
        this.name = name;
        this.age = age;
    }

    // Instance method
    greet() {
        return `Xin chao, toi la ${this.name}, ${this.age} tuoi`;
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

    // Static method (goi qua Class, khong qua instance)
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
an.greet();              // "Xin chao, toi la An, 25 tuoi"
an.info;                 // "An (25)" - Getter
an.info = "Binh, 30";   // Setter
Person.species;          // "Homo Sapiens"
Person.create("C", 28); // Static method

// an.#id;              // SyntaxError - khong truy cap private tu ben ngoai
```

### 25.2 Ke thua (Inheritance)

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
        super(name); // Goi constructor cha (BAT BUOC truoc khi dung this)
        this.breed = breed;
    }

    // Override method cha
    speak() {
        return `${this.name} barks`;
    }

    // Goi method cha
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

rex instanceof Dog;    // true
rex instanceof Animal; // true
```

### 25.3 Abstraction voi Class

```javascript
// JavaScript khong co abstract class chinh thuc
// Nhung co the mo phong:
class Shape {
    constructor(color) {
        if (new.target === Shape) {
            throw new Error("Khong the tao instance Shape truc tiep");
        }
        this.color = color;
    }

    // "Abstract" method
    area() {
        throw new Error("Phai implement method area()");
    }

    describe() {
        return `${this.color} shape voi dien tich ${this.area()}`;
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

const circle = new Circle("do", 5);
circle.describe(); // "do shape voi dien tich 78.53..."
// new Shape("xanh"); // Error!
```

### 25.4 Mixins (da ke thua gioi han)

```javascript
// JavaScript chi ho tro don ke thua, dung Mixin de them chuc nang
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

Mot object la **iterable** khi co method `[Symbol.iterator]()` tra ve mot **iterator** (co method `next()`):

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

Generator la ham dac biet co the **tam dung** va **tiep tuc** thuc thi:

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

// Dung trong for...of
for (const num of numberGenerator()) {
    console.log(num); // 1, 2, 3
}

// Spread
[...numberGenerator()]; // [1, 2, 3]
```

### 26.3 Ung dung cua Generator

```javascript
// 1. Day so vo han
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

// Lay N phan tu dau
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

// 3. Async-like flow (truoc khi co async/await)
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
// Symbol la kieu nguyen thuy duy nhat va bat bien
const sym1 = Symbol("description");
const sym2 = Symbol("description");
sym1 === sym2; // false - moi Symbol la duy nhat

// Su dung lam key cua object (khong bi xung dot ten)
const ID = Symbol("id");
const user = {
    [ID]: 123,
    name: "An"
};
user[ID]; // 123

// Well-known Symbols
Symbol.iterator;     // Dinh nghia iterable
Symbol.toPrimitive;  // Chuyen doi kieu
Symbol.hasInstance;   // Tuy chinh instanceof

// Global Symbol Registry
const globalSym = Symbol.for("app.id");
const same = Symbol.for("app.id");
globalSym === same; // true
Symbol.keyFor(globalSym); // "app.id"
```

### 27.2 Map

Map luu tru cap **key-value** voi key co the la **bat ky kieu gi**:

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

// Size va kiem tra
map.size;           // 4
map.has("name");    // true

// Xoa
map.delete(42);
map.clear();        // Xoa tat ca

// Khoi tao tu array
const map2 = new Map([
    ["a", 1],
    ["b", 2],
    ["c", 3]
]);

// Lap
for (const [key, value] of map2) {
    console.log(key, value);
}
map2.forEach((value, key) => console.log(key, value));

// Chuyen doi
[...map2.keys()];     // ["a", "b", "c"]
[...map2.values()];   // [1, 2, 3]
[...map2.entries()];  // [["a",1], ["b",2], ["c",3]]
Object.fromEntries(map2); // { a: 1, b: 2, c: 3 }
```

### 27.3 Set

Set luu tru **tap hop gia tri duy nhat** (khong trung lap):

```javascript
const set = new Set([1, 2, 3, 3, 2, 1]);
// Set { 1, 2, 3 } - tu dong loai bo trung

set.add(4);
set.has(3);    // true
set.delete(2);
set.size;      // 3

// Lap
for (const value of set) console.log(value);

// Chuyen sang Array
[...set]; // [1, 3, 4]

// Ung dung: loai bo trung lap trong array
const unique = [...new Set([1, 1, 2, 2, 3])]; // [1, 2, 3]

// Cac phep toan tap hop
const setA = new Set([1, 2, 3, 4]);
const setB = new Set([3, 4, 5, 6]);

// Hop (Union)
const union = new Set([...setA, ...setB]); // {1,2,3,4,5,6}

// Giao (Intersection)
const intersection = new Set([...setA].filter(x => setB.has(x))); // {3,4}

// Hieu (Difference)
const difference = new Set([...setA].filter(x => !setB.has(x))); // {1,2}

// ES2025+ (proposal) - Set methods
// setA.union(setB);
// setA.intersection(setB);
// setA.difference(setB);
```

### 27.4 WeakMap va WeakSet

**Weak** references - key (WeakMap) hoac value (WeakSet) co the bi garbage collected:

```javascript
// WeakMap - key phai la object
const weakMap = new WeakMap();
let obj = { name: "An" };
weakMap.set(obj, "metadata");
weakMap.get(obj); // "metadata"

obj = null; // Object co the bi garbage collected
// weakMap tu dong xoa entry

// Ung dung: luu metadata cho object ma khong can memory leak
const cache = new WeakMap();
function processObj(obj) {
    if (cache.has(obj)) return cache.get(obj);
    const result = /* tinh toan nang */ obj;
    cache.set(obj, result);
    return result;
}

// WeakSet - value phai la object
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

Proxy cho phep **chan va tuy chinh** cac thao tac tren object:

```javascript
const handler = {
    // Chan doc thuoc tinh
    get(target, property, receiver) {
        console.log(`Dang doc ${property}`);
        return property in target ? target[property] : `Khong co ${property}`;
    },

    // Chan ghi thuoc tinh
    set(target, property, value, receiver) {
        if (property === "age" && (typeof value !== "number" || value < 0)) {
            throw new TypeError("Age phai la so duong");
        }
        target[property] = value;
        return true;
    },

    // Chan delete
    deleteProperty(target, property) {
        if (property === "id") {
            throw new Error("Khong the xoa id");
        }
        delete target[property];
        return true;
    },

    // Chan kiem tra "in"
    has(target, property) {
        return property in target;
    }
};

const user = new Proxy({ id: 1, name: "An", age: 25 }, handler);

user.name;       // Log: "Dang doc name", tra ve "An"
user.email;      // Log: "Dang doc email", tra ve "Khong co email"
user.age = 30;   // OK
// user.age = -5; // TypeError
// delete user.id; // Error
```

### 28.2 Ung dung Proxy

```javascript
// 1. Validation
function createValidatedObject(schema) {
    return new Proxy({}, {
        set(target, prop, value) {
            if (schema[prop]) {
                const { type, required, min, max } = schema[prop];
                if (type && typeof value !== type) {
                    throw new TypeError(`${prop} phai la ${type}`);
                }
                if (min !== undefined && value < min) {
                    throw new RangeError(`${prop} phai >= ${min}`);
                }
                if (max !== undefined && value > max) {
                    throw new RangeError(`${prop} phai <= ${max}`);
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

// 2. Observable (theo doi thay doi)
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
    console.log(`${prop} thay doi: ${oldVal} -> ${newVal}`);
});
state.count = 1; // "count thay doi: 0 -> 1"
```

### 28.3 Reflect

Reflect cung cap cac phuong thuc tuong ung voi cac trap cua Proxy:

```javascript
const obj = { name: "An", age: 25 };

Reflect.get(obj, "name");           // "An"
Reflect.set(obj, "age", 26);        // true
Reflect.has(obj, "name");           // true
Reflect.deleteProperty(obj, "age"); // true
Reflect.ownKeys(obj);               // ["name"]

// Dung trong Proxy handler de dam bao hanh vi mac dinh
const handler = {
    get(target, prop, receiver) {
        console.log(`Truy cap ${prop}`);
        return Reflect.get(target, prop, receiver);
    }
};
```

---

## 29. Web APIs

### 29.1 Timer APIs

```javascript
// setTimeout - thuc thi 1 lan sau delay
const timeoutId = setTimeout(() => {
    console.log("Sau 2 giay");
}, 2000);
clearTimeout(timeoutId); // Huy

// setInterval - thuc thi lap lai
const intervalId = setInterval(() => {
    console.log("Moi 1 giay");
}, 1000);
clearInterval(intervalId); // Dung lai

// requestAnimationFrame - toi uu cho animation (60fps)
function animate() {
    // Cap nhat animation
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
// Tao Date
const now = new Date();
const specific = new Date(2024, 0, 15); // Thang bat dau tu 0!
const fromString = new Date("2024-01-15T10:30:00");
const fromTimestamp = new Date(1705312200000);

// Lay thong tin
now.getFullYear();    // 2024
now.getMonth();       // 0-11 (0 = thang 1!)
now.getDate();        // 1-31
now.getDay();         // 0-6 (0 = Chu nhat)
now.getHours();       // 0-23
now.getMinutes();     // 0-59
now.getSeconds();     // 0-59
now.getTime();        // Timestamp (ms tu 1/1/1970)

// Dat gia tri
now.setFullYear(2025);
now.setMonth(5);      // Thang 6

// Tinh toan
const diff = date2 - date1; // Hieu so ms
const days = diff / (1000 * 60 * 60 * 24); // Chuyen sang ngay

// Format
now.toLocaleDateString("vi-VN"); // "15/01/2024"
now.toLocaleTimeString("vi-VN"); // "10:30:00"
now.toISOString();               // "2024-01-15T03:30:00.000Z"

// Intl.DateTimeFormat (tuy chinh format)
new Intl.DateTimeFormat("vi-VN", {
    weekday: "long",
    year: "numeric",
    month: "long",
    day: "numeric"
}).format(now);
// "Thu Hai, 15 thang 1, 2024"
```

### 29.3 JSON

```javascript
// Parse (String -> Object)
const obj = JSON.parse('{"name":"An","age":25}');

// Stringify (Object -> String)
JSON.stringify(obj);                    // '{"name":"An","age":25}'
JSON.stringify(obj, null, 2);          // Formatted voi 2 space indent
JSON.stringify(obj, ["name"]);         // '{"name":"An"}' - chi giu "name"
JSON.stringify(obj, (key, value) => {  // Replacer function
    if (key === "age") return undefined; // Bo qua "age"
    return value;
});

// Deep clone (don gian nhung co gioi han)
const clone = JSON.parse(JSON.stringify(original));
// Khong hoat dong voi: Date, Function, undefined, Map, Set, RegExp
// Dung structuredClone() de deep clone day du hon
```

### 29.4 Console API

```javascript
console.log("Thong tin");
console.warn("Canh bao");
console.error("Loi");
console.info("Info");

console.table([{name:"An",age:25}, {name:"Binh",age:30}]); // Bang dep
console.group("Nhom");
console.log("Ben trong nhom");
console.groupEnd();

console.time("myTimer");
// Code can do thoi gian
console.timeEnd("myTimer"); // "myTimer: 123.456ms"

console.assert(1 === 2, "1 khong bang 2"); // Chi hien khi dieu kien sai
console.count("myCounter");  // "myCounter: 1"
console.count("myCounter");  // "myCounter: 2"
console.dir(object);         // Hien thi object dang tree
```

### 29.5 Event Loop va Call Stack

JavaScript la **single-threaded** nhung xu ly bat dong bo nho **Event Loop**:

```
┌─────────────────────┐
│     Call Stack       │ <- Thuc thi code dong bo
├─────────────────────┤
│  Web APIs / Node     │ <- setTimeout, fetch, DOM events
├─────────────────────┤
│  Microtask Queue     │ <- Promise.then, queueMicrotask (UU TIEN CAO)
├─────────────────────┤
│  Macrotask Queue     │ <- setTimeout, setInterval, I/O
└─────────────────────┘

Event Loop:
1. Thuc thi code trong Call Stack
2. Khi Call Stack rong -> kiem tra Microtask Queue (xu ly HET)
3. Lay 1 task tu Macrotask Queue -> Call Stack
4. Lap lai buoc 2-3
```

```javascript
console.log("1");                          // Dong bo -> Call Stack

setTimeout(() => console.log("2"), 0);     // Macrotask

Promise.resolve().then(() => console.log("3")); // Microtask

console.log("4");                          // Dong bo -> Call Stack

// Output: 1, 4, 3, 2
// Giai thich:
// 1. "1" - dong bo, chay ngay
// 2. setTimeout -> Macrotask Queue
// 3. Promise.then -> Microtask Queue
// 4. "4" - dong bo, chay ngay
// 5. Call Stack rong -> xu ly Microtask: "3"
// 6. Xu ly Macrotask: "2"
```

---

## 30. Best Practices và Design Patterns

### 30.1 Coding Best Practices

```javascript
// 1. Luon dung const, chi dung let khi can gan lai
const MAX_ITEMS = 100;
let currentCount = 0;

// 2. Dung === thay vi ==
if (value === null) { }

// 3. Dung template literals
const msg = `Hello ${name}, ban co ${count} tin nhan`;

// 4. Dung destructuring
const { name, age } = user;
const [first, ...rest] = items;

// 5. Optional chaining va nullish coalescing
const city = user?.address?.city ?? "Unknown";

// 6. Arrow function cho callback ngan
const doubled = numbers.map(n => n * 2);

// 7. Tranh side effects - uu tien pure functions
// Xau:
function addToCart(cart, item) {
    cart.push(item); // Bien doi tham so!
}
// Tot:
function addToCart(cart, item) {
    return [...cart, item]; // Tra ve mang moi
}

// 8. Early return (Guard Clause)
function getDiscount(user) {
    if (!user) return 0;
    if (!user.isPremium) return 5;
    if (user.yearsActive > 5) return 25;
    return 15;
}

// 9. Error handling day du
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

// 10. Dat ten co y nghia
// Xau: const d = new Date();
// Tot: const currentDate = new Date();
// Xau: function proc(a, b) {}
// Tot: function calculateTotal(price, tax) {}
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
unsub(); // Go dang ky
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
        console.log(`Goi ${fn.name} voi args:`, args);
        const result = fn.apply(this, args);
        console.log(`Ket qua:`, result);
        return result;
    };
}

function add(a, b) { return a + b; }
const loggedAdd = withLogging(add);
loggedAdd(2, 3);
// "Goi add voi args: [2, 3]"
// "Ket qua: 5"
```

### 30.3 Clean Code Tips

```javascript
// 1. Ham chi lam 1 viec
// Xau:
function processUser(user) {
    // validate + save + send email + log
}
// Tot:
function validateUser(user) { /* ... */ }
function saveUser(user) { /* ... */ }
function sendWelcomeEmail(user) { /* ... */ }

// 2. Tranh "magic numbers"
// Xau: if (status === 3) { }
// Tot:
const STATUS = { ACTIVE: 1, INACTIVE: 2, BANNED: 3 };
if (status === STATUS.BANNED) { }

// 3. Dung Object.freeze cho constants
const CONFIG = Object.freeze({
    API_URL: "https://api.example.com",
    MAX_RETRIES: 3,
    TIMEOUT: 5000
});

// 4. Chain methods khi phu hop
const result = users
    .filter(u => u.active)
    .map(u => u.name)
    .sort()
    .join(", ");
```

---

## 31. Tổng kết

### 31.1 Lo trinh hoc JavaScript

```
1. Co ban:
   Bien, Kieu du lieu, Toan tu, Dieu kien, Vong lap, Ham

2. Trung cap:
   Array Methods, Object, Scope, Closure, this, Prototype
   ES6+ (let/const, arrow, destructuring, spread, template literals)

3. Nang cao:
   Promise, Async/Await, Class, Module, Iterator/Generator
   Proxy/Reflect, Symbol, Map/Set

4. DOM & Browser:
   DOM Manipulation, Event Handling, Fetch API, Web Storage

5. Patterns & Best Practices:
   Design Patterns, Clean Code, Error Handling, Performance

6. Framework (buoc tiep theo):
   React, Vue, Angular, Svelte (Frontend)
   Node.js, Express, NestJS (Backend)
```

### 31.2 Tai nguyen hoc them

| Tai nguyen | Link |
|------------|------|
| MDN Web Docs | developer.mozilla.org |
| JavaScript.info | javascript.info |
| Eloquent JavaScript | eloquentjavascript.net |
| You Don't Know JS | github.com/getify/You-Dont-Know-JS |
| ES6+ Cheatsheet | es6-features.org |
| Node.js Docs | nodejs.org/docs |
