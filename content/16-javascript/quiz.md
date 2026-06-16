# Quiz - JavaScript

## Câu 1

[TYPE: MULTIPLE_CHOICE]

JavaScript là gì?

- [ ] Một phiên bản rút gọn của Java
- [x] Một ngôn ngữ lập trình động, đa mô hình chạy trên trình duyệt và server
- [ ] Một framework CSS
- [ ] Một hệ quản trị cơ sở dữ liệu

> **Giải thích:** JavaScript là ngôn ngữ lập trình động (dynamic), đa mô hình (multi-paradigm), chạy native trên trình duyệt và có thể chạy trên server với Node.js. JavaScript không liên quan gì đến Java.

## Câu 2

[TYPE: MULTIPLE_CHOICE]

Đầu ra của đoạn code sau là gì?

```javascript
console.log(typeof null);
```

- [ ] "null"
- [x] "object"
- [ ] "undefined"
- [ ] "boolean"

> **Giải thích:** Đây là một bug lịch sử của JavaScript từ phiên bản đầu tiên. `typeof null` trả về "object" thay vì "null". Để kiểm tra null, dùng `value === null`.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Sự khác biệt chính giữa `let` và `var` là gì?

- [ ] `let` nhanh hơn `var`
- [ ] `var` không thể gán lại giá trị
- [x] `let` có block scope, `var` có function scope
- [ ] `let` chỉ dùng được trong hàm

> **Giải thích:** `let` và `const` có block scope (chỉ tồn tại trong `{}`), trong khi `var` có function scope. `var` còn được hoisted với giá trị undefined, còn `let` nằm trong Temporal Dead Zone (TDZ).

## Câu 4

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
console.log(1 + "2" + 3);
```

- [ ] 6
- [ ] "6"
- [x] "123"
- [ ] 123

> **Giải thích:** Khi gặp toán tử `+` với một string, JavaScript sẽ chuyển đổi sang string và nối chuỗi. `1 + "2"` = `"12"` (number + string = string), rồi `"12" + 3` = `"123"`.

## Câu 5

[TYPE: TRUE_FALSE]

Mệnh đề: "Arrow function có `this` riêng của nó."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Arrow function KHÔNG có `this` riêng. Nó kế thừa `this` từ scope cha (lexical this). Đây là một trong những khác biệt quan trọng nhất giữa arrow function và regular function.

## Câu 6

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
console.log([] == false);
console.log([] === false);
```

- [ ] true, true
- [x] true, false
- [ ] false, false
- [ ] false, true

> **Giải thích:** Với `==` (so sánh lỏng), `[]` được chuyển thành `""` rồi thành `0`, `false` cũng thành `0`, nên `0 == 0` là `true`. Với `===` (so sánh nghiêm ngặt), không có type coercion, nên `[]` (object) !== `false` (boolean).

## Câu 7

[TYPE: MULTIPLE_CHOICE]

Phương thức nào KHÔNG làm thay đổi mảng gốc?

- [ ] `push()`
- [ ] `splice()`
- [x] `map()`
- [ ] `sort()`

> **Giải thích:** `map()` trả về một mảng mới mà không thay đổi mảng gốc. `push()`, `splice()`, `sort()` đều là mutating methods - chúng thay đổi mảng gốc trực tiếp.

## Câu 8

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
const arr = [1, 2, 3];
const result = arr.reduce((acc, curr) => acc + curr, 10);
console.log(result);
```

- [ ] 6
- [x] 16
- [ ] 10
- [ ] NaN

> **Giải thích:** `reduce` bắt đầu với giá trị khởi tạo là `10`, rồi cộng lần lượt: `10 + 1 = 11`, `11 + 2 = 13`, `13 + 3 = 16`.

## Câu 9

[TYPE: MULTIPLE_CHOICE]

Closure trong JavaScript là gì?

- [ ] Một cách để đóng trình duyệt
- [ ] Một loại vòng lặp đặc biệt
- [x] Một hàm có thể truy cập biến từ scope bên ngoài ngay cả khi hàm cha đã thực thi xong
- [ ] Một kỹ thuật để xóa biến

> **Giải thích:** Closure là một hàm có thể "nhớ" và truy cập các biến từ lexical scope (scope bên ngoài) ngay cả khi hàm bên ngoài đã return. Đây là một khái niệm cơ bản và quan trọng trong JavaScript.

## Câu 10

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
for (var i = 0; i < 3; i++) {
    setTimeout(() => console.log(i), 0);
}
```

- [ ] 0, 1, 2
- [x] 3, 3, 3
- [ ] undefined, undefined, undefined
- [ ] 0, 0, 0

> **Giải thích:** `var` có function scope, nên chỉ có 1 biến `i` duy nhất. Khi các callback của `setTimeout` chạy (sau khi vòng lặp kết thúc), `i` đã bằng 3. Để fix, dùng `let` (block scope) thay cho `var`.

## Câu 11

[TYPE: MULTIPLE_CHOICE]

`===` khác gì với `==` trong JavaScript?

- [ ] `===` nhanh hơn `==`
- [ ] Không có sự khác biệt
- [x] `===` so sánh cả giá trị và kiểu dữ liệu (không có type coercion)
- [ ] `===` chỉ dùng cho string

> **Giải thích:** `===` (strict equality) so sánh cả giá trị và kiểu dữ liệu mà không chuyển đổi kiểu. `==` (loose equality) sẽ tự động chuyển đổi kiểu trước khi so sánh. Ví dụ: `"5" == 5` là `true`, nhưng `"5" === 5` là `false`.

## Câu 12

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
const obj = { a: 1, b: 2, c: 3 };
const { a, ...rest } = obj;
console.log(rest);
```

- [ ] { a: 1, b: 2, c: 3 }
- [ ] { a: 1 }
- [x] { b: 2, c: 3 }
- [ ] [2, 3]

> **Giải thích:** Rest pattern (`...rest`) trong destructuring thu thập các thuộc tính còn lại vào một object mới. `a` đã được lấy riêng, nên `rest` chứa `{ b: 2, c: 3 }`.

## Câu 13

[TYPE: MULTIPLE_CHOICE]

Promise có bao nhiêu trạng thái?

- [ ] 2: Resolved và Rejected
- [x] 3: Pending, Fulfilled, Rejected
- [ ] 4: Pending, Loading, Fulfilled, Rejected
- [ ] 1: Completed

> **Giải thích:** Promise có 3 trạng thái: **Pending** (đang chờ), **Fulfilled** (thành công - resolved), và **Rejected** (thất bại). Khi đã chuyển sang fulfilled hoặc rejected, Promise không thể thay đổi trạng thái nữa (settled).

## Câu 14

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
console.log("1");
setTimeout(() => console.log("2"), 0);
Promise.resolve().then(() => console.log("3"));
console.log("4");
```

- [ ] 1, 2, 3, 4
- [ ] 1, 4, 2, 3
- [x] 1, 4, 3, 2
- [ ] 1, 3, 4, 2

> **Giải thích:** Event Loop: (1) Đồng bộ: "1", "4" chạy trước. (2) Microtask (Promise.then): "3" chạy tiếp. (3) Macrotask (setTimeout): "2" chạy cuối. Microtask luôn được xử lý trước Macrotask.

## Câu 15

[TYPE: MULTIPLE_CHOICE]

Cách nào dùng để tạo bản sao sâu (deep copy) của một object?

- [ ] `Object.assign({}, obj)`
- [ ] `{ ...obj }`
- [x] `structuredClone(obj)`
- [ ] `obj.clone()`

> **Giải thích:** `Object.assign` và spread operator (`...`) chỉ tạo shallow copy (nested objects vẫn là tham chiếu). `structuredClone()` (ES2022) tạo deep copy thực sự. `JSON.parse(JSON.stringify(obj))` cũng là deep copy nhưng có giới hạn (không hỗ trợ Date, Function, Map, Set).

## Câu 16

[TYPE: TRUE_FALSE]

Mệnh đề: "Mảng rỗng `[]` và object rỗng `{}` đều là falsy values."

- [ ] Đúng
- [x] Sai

> **Giải thích:** `[]` và `{}` đều là **truthy** values! Chỉ có 8 giá trị falsy trong JS: `false`, `0`, `-0`, `0n`, `""`, `null`, `undefined`, `NaN`. Tất cả object (kể cả mảng rỗng và object rỗng) đều là truthy.

## Câu 17

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
const person = { name: "An" };
Object.freeze(person);
person.name = "Bình";
person.age = 25;
console.log(person);
```

- [ ] { name: "Bình", age: 25 }
- [x] { name: "An" }
- [ ] Error
- [ ] { name: "Bình" }

> **Giải thích:** `Object.freeze()` ngăn không cho thay đổi, thêm, hoặc xóa thuộc tính của object. Các phép gán bị bỏ qua im lặng (hoặc throw error trong strict mode). Lưu ý: freeze chỉ hoạt động ở cấp đầu tiên (shallow freeze).

## Câu 18

[TYPE: MULTIPLE_CHOICE]

`async/await` được xây dựng dựa trên cơ chế nào?

- [ ] Callback
- [x] Promise
- [ ] Observable
- [ ] Generator

> **Giải thích:** `async/await` là syntactic sugar (cú pháp gọn) dựa trên Promise. Một `async` function luôn trả về Promise. `await` tạm dừng thực thi cho đến khi Promise được resolve hoặc reject.

## Câu 19

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
const arr = [1, 2, 3, 4, 5];
const result = arr.filter(n => n > 2).map(n => n * 10);
console.log(result);
```

- [ ] [10, 20, 30, 40, 50]
- [ ] [3, 4, 5]
- [x] [30, 40, 50]
- [ ] [1, 2, 30, 40, 50]

> **Giải thích:** `filter(n => n > 2)` lọc ra `[3, 4, 5]`, sau đó `map(n => n * 10)` nhân mỗi phần tử với 10, trả về `[30, 40, 50]`.

## Câu 20

[TYPE: MULTIPLE_CHOICE]

Event Delegation là gì?

- [ ] Xóa event listener sau khi sử dụng
- [ ] Truyền event từ server xuống client
- [x] Gán event listener cho element cha để xử lý event của các element con
- [ ] Tạo nhiều event listener cho mỗi element

> **Giải thích:** Event Delegation là kỹ thuật gán 1 event listener cho element cha thay vì nhiều listener cho từng element con. Nó tận dụng Event Bubbling - khi event xảy ra trên element con, nó sẽ "bubble" (nổi lên) đến element cha.

## Câu 21

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
console.log(typeof typeof 42);
```

- [ ] "number"
- [x] "string"
- [ ] "typeof"
- [ ] undefined

> **Giải thích:** `typeof 42` trả về chuỗi `"number"`. Sau đó `typeof "number"` trả về `"string"`. Kết quả của `typeof` luôn là một string.

## Câu 22

[TYPE: MULTIPLE_CHOICE]

Câu nào đúng về `const` trong JavaScript?

- [ ] Giá trị của biến `const` không bao giờ thay đổi được
- [x] Biến `const` không thể gán lại (re-assign), nhưng nội dung của object/array vẫn có thể thay đổi
- [ ] `const` giống hệt `let` nhưng nhanh hơn
- [ ] `const` chỉ dùng cho số và string

> **Giải thích:** `const` ngăn việc gán lại (re-assign) biến, nhưng không ngăn việc thay đổi nội dung của object/array. Ví dụ: `const arr = [1]; arr.push(2);` là hợp lệ, nhưng `arr = [3]` sẽ lỗi.

## Câu 23

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
const a = {};
const b = { key: "b" };
const c = { key: "c" };

a[b] = 123;
a[c] = 456;

console.log(a[b]);
```

- [ ] 123
- [x] 456
- [ ] undefined
- [ ] Error

> **Giải thích:** Khi dùng object làm key, nó được chuyển thành string `"[object Object]"`. Cả `b` và `c` đều thành cùng một key `"[object Object]"`, nên `a[c] = 456` ghi đè lên `a[b] = 123`. Do đó `a[b]` trả về `456`.

## Câu 24

[TYPE: MULTIPLE_CHOICE]

Cách nào để kiểm tra một biến là Array?

- [ ] `typeof arr === "array"`
- [ ] `arr.type === "Array"`
- [x] `Array.isArray(arr)`
- [ ] `arr typeof Array`

> **Giải thích:** `typeof []` trả về `"object"` (không phải "array"), nên không dùng `typeof` để kiểm tra. `Array.isArray()` là cách chuẩn và đáng tin cậy nhất. Có thể dùng `arr instanceof Array` nhưng nó có vấn đề với cross-frame/iframe.

## Câu 25

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
function foo() {
    return
    {
        name: "An"
    };
}
console.log(foo());
```

- [ ] { name: "An" }
- [x] undefined
- [ ] SyntaxError
- [ ] null

> **Giải thích:** JavaScript tự động thêm dấu chấm phẩy (ASI - Automatic Semicolon Insertion) sau `return` vì dòng tiếp theo bắt đầu trên dòng mới. Hàm thực sự là `return;` nên trả về `undefined`. Để fix: `return {` phải cùng dòng với `return`.

## Câu 26

[TYPE: MULTIPLE_CHOICE]

Nullish Coalescing Operator `??` khác gì với `||`?

- [ ] Không có sự khác biệt
- [ ] `??` chỉ dùng cho số
- [x] `??` chỉ kiểm tra `null` và `undefined`, còn `||` kiểm tra tất cả falsy values
- [ ] `??` là phiên bản cũ của `||`

> **Giải thích:** `||` trả về giá trị bên phải nếu bên trái là falsy (0, "", false, null, undefined, NaN). `??` chỉ trả về giá trị bên phải khi bên trái là `null` hoặc `undefined`. Ví dụ: `0 || 5` = `5`, nhưng `0 ?? 5` = `0`.

## Câu 27

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
const obj = { a: 1, b: 2 };
const clone = { ...obj, b: 3, c: 4 };
console.log(clone);
```

- [ ] { a: 1, b: 2 }
- [ ] { a: 1, b: 2, c: 4 }
- [x] { a: 1, b: 3, c: 4 }
- [ ] Error

> **Giải thích:** Spread operator (`...obj`) trải các thuộc tính của obj, sau đó `b: 3` ghi đè giá trị cũ của `b`, và `c: 4` được thêm mới. Thứ tự quan trọng: thuộc tính sau sẽ ghi đè thuộc tính trước nếu cùng key.

## Câu 28

[TYPE: TRUE_FALSE]

Mệnh đề: "`NaN === NaN` trả về `true`."

- [ ] Đúng
- [x] Sai

> **Giải thích:** `NaN` là giá trị duy nhất trong JavaScript không bằng chính nó! `NaN === NaN` trả về `false`. Để kiểm tra NaN, dùng `Number.isNaN(value)` hoặc `Object.is(value, NaN)`.

## Câu 29

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
let x = 10;
let y = x;
x = 20;
console.log(y);
```

- [x] 10
- [ ] 20
- [ ] undefined
- [ ] Error

> **Giải thích:** Primitive values (number, string, boolean,...) được truyền theo giá trị (pass by value). Khi `y = x`, giá trị 10 được copy sang `y`. Thay đổi `x` thành 20 không ảnh hưởng đến `y`.

## Câu 30

[TYPE: MULTIPLE_CHOICE]

Phương thức `addEventListener` có ưu điểm gì so với `onclick`?

- [ ] Nhanh hơn
- [ ] Dễ viết hơn
- [x] Có thể gán nhiều handler cho cùng một event trên cùng element
- [ ] Hỗ trợ nhiều trình duyệt hơn

> **Giải thích:** `addEventListener` cho phép thêm nhiều handler cho cùng một event, hỗ trợ capturing phase, và có tùy chọn như `once`, `passive`. `onclick` (DOM property) chỉ cho phép 1 handler - handler mới sẽ ghi đè handler cũ.

## Câu 31

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
console.log(0.1 + 0.2 === 0.3);
```

- [ ] true
- [x] false
- [ ] Error
- [ ] undefined

> **Giải thích:** Do IEEE 754 floating-point arithmetic, `0.1 + 0.2` = `0.30000000000000004`, không chính xác bằng `0.3`. Để so sánh số thập phân, dùng `Math.abs(0.1 + 0.2 - 0.3) < Number.EPSILON` hoặc làm việc với số nguyên (nhân 100).

## Câu 32

[TYPE: MULTIPLE_CHOICE]

Generator function được khai báo như thế nào?

- [ ] `function gen() {}`
- [x] `function* gen() {}`
- [ ] `async function gen() {}`
- [ ] `generator function gen() {}`

> **Giải thích:** Generator function được khai báo bằng `function*` (có dấu `*`). Bên trong có thể dùng từ khóa `yield` để tạm dừng và tiếp tục thực thi. Generator trả về một iterator khi gọi.

## Câu 33

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
const arr = [1, 2, 3];
arr[10] = 11;
console.log(arr.length);
```

- [ ] 3
- [ ] 4
- [x] 11
- [ ] 10

> **Giải thích:** Khi gán giá trị tại index 10, JavaScript tự động tăng `length` thành 11. Các vị trí từ index 3 đến 9 là "empty slots" (holes) - chúng tồn tại nhưng không có giá trị.

## Câu 34

[TYPE: MULTIPLE_CHOICE]

`Map` khác gì so với Object thông thường?

- [ ] Map nhanh hơn Object
- [ ] Map chỉ chứa string values
- [x] Map cho phép key là bất kỳ kiểu dữ liệu nào, không chỉ string/symbol
- [ ] Map là immutable

> **Giải thích:** Khác với Object (key chỉ có thể là string hoặc symbol), Map cho phép key là bất kỳ kiểu nào (object, function, number,...). Map cũng đảm bảo thứ tự chèn và có property `size` trực tiếp.

## Câu 35

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
function test() {
    console.log(a);
    console.log(b);
    var a = 1;
    let b = 2;
}
test();
```

- [ ] 1, 2
- [ ] undefined, undefined
- [x] undefined rồi ReferenceError
- [ ] ReferenceError ngay dòng đầu

> **Giải thích:** `var a` được hoisted lên đầu hàm với giá trị `undefined`, nên `console.log(a)` in `undefined`. `let b` cũng được hoisted nhưng nằm trong Temporal Dead Zone (TDZ), nên truy cập trước khi khai báo gây `ReferenceError`.

## Câu 36

[TYPE: MULTIPLE_CHOICE]

Cách nào hủy một Fetch request đang chạy?

- [ ] `fetch.cancel()`
- [ ] `request.abort()`
- [x] Sử dụng `AbortController` và truyền `signal` vào fetch options
- [ ] Không thể hủy Fetch request

> **Giải thích:** Dùng `const controller = new AbortController()`, truyền `{ signal: controller.signal }` vào fetch, rồi gọi `controller.abort()` để hủy. Fetch sẽ throw một `AbortError`.

## Câu 37

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
const set = new Set([1, 2, 3, 3, 2, 1]);
console.log(set.size);
```

- [ ] 6
- [x] 3
- [ ] 1
- [ ] Error

> **Giải thích:** `Set` chỉ lưu trữ các giá trị duy nhất (không trùng lặp). Từ mảng `[1, 2, 3, 3, 2, 1]`, các giá trị trùng sẽ bị loại bỏ, còn lại `{1, 2, 3}` với `size = 3`.

## Câu 38

[TYPE: TRUE_FALSE]

Mệnh đề: "`forEach()` có thể dùng `break` hoặc `return` để dừng vòng lặp."

- [ ] Đúng
- [x] Sai

> **Giải thích:** `forEach` KHÔNG thể dùng `break` để thoát vòng lặp. `return` trong forEach chỉ thoát khỏi callback hiện tại (tương tự `continue`), không dừng hàm forEach. Để dừng sớm, dùng `for...of` với `break`, hoặc dùng `some()`/`every()`.

## Câu 39

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
const user = {
    name: "An",
    greet: () => {
        return `Hello ${this.name}`;
    }
};
console.log(user.greet());
```

- [ ] "Hello An"
- [x] "Hello undefined"
- [ ] Error
- [ ] "Hello"

> **Giải thích:** Arrow function không có `this` riêng, nó kế thừa từ scope cha. Ở đây scope cha là global/module scope, không phải object `user`. `this.name` là `undefined`. Để fix, dùng regular function: `greet() { return \`Hello ${this.name}\`; }`.

## Câu 40

[TYPE: MULTIPLE_CHOICE]

`Symbol` dùng để làm gì?

- [ ] Tạo icon cho web
- [ ] Mã hóa dữ liệu
- [x] Tạo giá trị duy nhất (unique) dùng làm property key
- [ ] Khai báo hằng số

> **Giải thích:** `Symbol()` tạo một giá trị primitive duy nhất và bất biến. Chủ yếu được dùng làm property key để tránh xung đột tên, và cũng được dùng cho các well-known Symbols (Symbol.iterator, Symbol.toPrimitive,...).

## Câu 41

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
console.log(+"");
console.log(+true);
console.log(+null);
console.log(+undefined);
```

- [ ] 0, 1, 0, 0
- [x] 0, 1, 0, NaN
- [ ] NaN, NaN, NaN, NaN
- [ ] "", true, null, undefined

> **Giải thích:** Unary plus (`+`) chuyển đổi sang Number: `+""` = `0`, `+true` = `1`, `+null` = `0`, `+undefined` = `NaN`. Lưu ý: `null` chuyển thành `0` nhưng `undefined` chuyển thành `NaN`.

## Câu 42

[TYPE: MULTIPLE_CHOICE]

`WeakMap` khác gì so với `Map`?

- [ ] WeakMap nhanh hơn Map
- [x] Key của WeakMap phải là object và có thể bị garbage collected
- [ ] WeakMap có thể iterate (duyệt)
- [ ] WeakMap lưu trữ nhiều dữ liệu hơn

> **Giải thích:** WeakMap chỉ chấp nhận object làm key và giữ "weak reference" - key có thể bị garbage collected khi không còn tham chiếu nào khác. WeakMap không có `size`, không thể iterate, và không có method `keys()`/`values()`/`entries()`.

## Câu 43

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
const [a, , b, ...rest] = [1, 2, 3, 4, 5];
console.log(a, b, rest);
```

- [ ] 1, 2, [3, 4, 5]
- [ ] 1, 3, [4, 5]
- [x] 1, 3, [4, 5]
- [ ] 1, 2, [4, 5]

> **Giải thích:** Destructuring: `a = 1`, bỏ qua index 1 (giá trị 2), `b = 3`, `...rest` = `[4, 5]`. Dấu phẩy trống bỏ qua phần tử tại vị trí đó.

## Câu 44

[TYPE: MULTIPLE_CHOICE]

`Proxy` trong JavaScript dùng để làm gì?

- [ ] Kết nối với proxy server
- [ ] Bảo mật dữ liệu
- [x] Chặn và tùy chỉnh các thao tác trên object (get, set, delete,...)
- [ ] Tạo bản sao của object

> **Giải thích:** Proxy cho phép định nghĩa các "trap" (bộ chặn) cho các thao tác cơ bản trên object như đọc thuộc tính (get), ghi thuộc tính (set), xóa (deleteProperty), kiểm tra (has), v.v.

## Câu 45

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
async function test() {
    return 42;
}
console.log(test());
```

- [ ] 42
- [x] Promise {<fulfilled>: 42}
- [ ] undefined
- [ ] Error

> **Giải thích:** `async` function luôn trả về một Promise. Khi `return 42`, nó tương đương `return Promise.resolve(42)`. `console.log` hiển thị Promise object. Để lấy giá trị 42, cần dùng `await test()` hoặc `.then(val => ...)`.

## Câu 46

[TYPE: TRUE_FALSE]

Mệnh đề: "`Object.keys()` trả về cả các thuộc tính kế thừa từ prototype."

- [ ] Đúng
- [x] Sai

> **Giải thích:** `Object.keys()` chỉ trả về các own enumerable string properties của object, KHÔNG bao gồm thuộc tính kế thừa từ prototype. Để lấy cả thuộc tính kế thừa, dùng `for...in` loop.

## Câu 47

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
const nums = [10, 5, 20, 1, 100];
console.log(nums.sort());
```

- [ ] [1, 5, 10, 20, 100]
- [x] [1, 10, 100, 20, 5]
- [ ] [100, 20, 10, 5, 1]
- [ ] Error

> **Giải thích:** `sort()` mặc định sắp xếp theo thứ tự từ điển (string). Số được chuyển sang string rồi so sánh: "1" < "10" < "100" < "20" < "5". Để sắp xếp số đúng: `nums.sort((a, b) => a - b)`.

## Câu 48

[TYPE: MULTIPLE_CHOICE]

Optional Chaining (`?.`) hoạt động như thế nào?

- [ ] Throw error nếu thuộc tính không tồn tại
- [x] Trả về `undefined` thay vì throw error khi truy cập thuộc tính của null/undefined
- [ ] Tạo thuộc tính mới nếu chưa tồn tại
- [ ] Chỉ dùng được với object, không dùng với array

> **Giải thích:** Optional Chaining (`?.`) kiểm tra xem giá trị trước nó có phải null/undefined không. Nếu có, trả về `undefined` ngay mà không tiếp tục truy cập. Có thể dùng với properties (`?.`), methods (`?.()`), và array index (`?.[]`).

## Câu 49

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
function* gen() {
    yield 1;
    yield 2;
    yield 3;
}
const g = gen();
console.log(g.next().value);
console.log(g.next().value);
```

- [ ] 1, 1
- [x] 1, 2
- [ ] undefined, undefined
- [ ] Error

> **Giải thích:** Generator function tạm dừng tại mỗi `yield`. Lần `next()` đầu trả về `{ value: 1, done: false }`, lần thứ hai trả về `{ value: 2, done: false }`. Mỗi lần gọi `next()`, generator tiếp tục từ vị trí yield trước đó.

## Câu 50

[TYPE: SELECT_RESULT]

Đầu ra của đoạn code sau là gì?

```javascript
const promise1 = Promise.resolve("A");
const promise2 = Promise.reject("B");
const promise3 = Promise.resolve("C");

Promise.allSettled([promise1, promise2, promise3])
    .then(results => {
        console.log(results.length);
        console.log(results[1].status);
    });
```

- [ ] 2, "fulfilled"
- [ ] 3, "fulfilled"
- [x] 3, "rejected"
- [ ] Error vì promise2 reject

> **Giải thích:** `Promise.allSettled()` chờ tất cả promises hoàn thành (không quan trọng thành công hay thất bại). Kết quả có 3 phần tử. `results[1]` là `{ status: "rejected", reason: "B" }` vì `promise2` bị reject.
