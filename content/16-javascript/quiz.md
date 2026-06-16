# Quiz - JavaScript

## Cau 1

[TYPE: MULTIPLE_CHOICE]

JavaScript la gi?

- [ ] Mot phien ban rut gon cua Java
- [x] Mot ngon ngu lap trinh dong, da mo hinh chay tren trinh duyet va server
- [ ] Mot framework CSS
- [ ] Mot he quan tri co so du lieu

> **Giai thich:** JavaScript la ngon ngu lap trinh dong (dynamic), da mo hinh (multi-paradigm), chay native tren trinh duyet va co the chay tren server voi Node.js. JavaScript khong lien quan gi den Java.

## Cau 2

[TYPE: MULTIPLE_CHOICE]

Dau ra cua doan code sau la gi?

```javascript
console.log(typeof null);
```

- [ ] "null"
- [x] "object"
- [ ] "undefined"
- [ ] "boolean"

> **Giai thich:** Day la mot bug lich su cua JavaScript tu phien ban dau tien. `typeof null` tra ve "object" thay vi "null". De kiem tra null, dung `value === null`.

## Cau 3

[TYPE: MULTIPLE_CHOICE]

Su khac biet chinh giua `let` va `var` la gi?

- [ ] `let` nhanh hon `var`
- [ ] `var` khong the gan lai gia tri
- [x] `let` co block scope, `var` co function scope
- [ ] `let` chi dung duoc trong ham

> **Giai thich:** `let` va `const` co block scope (chi ton tai trong `{}`), trong khi `var` co function scope. `var` con duoc hoisted voi gia tri undefined, con `let` nam trong Temporal Dead Zone (TDZ).

## Cau 4

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

```javascript
console.log(1 + "2" + 3);
```

- [ ] 6
- [ ] "6"
- [x] "123"
- [ ] 123

> **Giai thich:** Khi gap toan tu `+` voi mot string, JavaScript se chuyen doi sang string va noi chuoi. `1 + "2"` = `"12"` (number + string = string), roi `"12" + 3` = `"123"`.

## Cau 5

[TYPE: TRUE_FALSE]

Menh de: "Arrow function co `this` rieng cua no."

- [ ] Dung
- [x] Sai

> **Giai thich:** Arrow function KHONG co `this` rieng. No ke thua `this` tu scope cha (lexical this). Day la mot trong nhung khac biet quan trong nhat giua arrow function va regular function.

## Cau 6

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

```javascript
console.log([] == false);
console.log([] === false);
```

- [ ] true, true
- [x] true, false
- [ ] false, false
- [ ] false, true

> **Giai thich:** Voi `==` (so sanh long), `[]` duoc chuyen thanh `""` roi thanh `0`, `false` cung thanh `0`, nen `0 == 0` la `true`. Voi `===` (so sanh nghiem ngat), khong co type coercion, nen `[]` (object) !== `false` (boolean).

## Cau 7

[TYPE: MULTIPLE_CHOICE]

Phuong thuc nao KHONG lam thay doi mang goc?

- [ ] `push()`
- [ ] `splice()`
- [x] `map()`
- [ ] `sort()`

> **Giai thich:** `map()` tra ve mot mang moi ma khong thay doi mang goc. `push()`, `splice()`, `sort()` deu la mutating methods - chung thay doi mang goc truc tiep.

## Cau 8

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

```javascript
const arr = [1, 2, 3];
const result = arr.reduce((acc, curr) => acc + curr, 10);
console.log(result);
```

- [ ] 6
- [x] 16
- [ ] 10
- [ ] NaN

> **Giai thich:** `reduce` bat dau voi gia tri khoi tao la `10`, roi cong lan luot: `10 + 1 = 11`, `11 + 2 = 13`, `13 + 3 = 16`.

## Cau 9

[TYPE: MULTIPLE_CHOICE]

Closure trong JavaScript la gi?

- [ ] Mot cach de dong trinh duyet
- [ ] Mot loai vong lap dac biet
- [x] Mot ham co the truy cap bien tu scope ben ngoai ngay ca khi ham cha da thuc thi xong
- [ ] Mot ky thuat de xoa bien

> **Giai thich:** Closure la mot ham co the "nho" va truy cap cac bien tu lexical scope (scope ben ngoai) ngay ca khi ham ben ngoai da return. Day la mot khai niem co ban va quan trong trong JavaScript.

## Cau 10

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

```javascript
for (var i = 0; i < 3; i++) {
    setTimeout(() => console.log(i), 0);
}
```

- [ ] 0, 1, 2
- [x] 3, 3, 3
- [ ] undefined, undefined, undefined
- [ ] 0, 0, 0

> **Giai thich:** `var` co function scope, nen chi co 1 bien `i` duy nhat. Khi cac callback cua `setTimeout` chay (sau khi vong lap ket thuc), `i` da bang 3. De fix, dung `let` (block scope) thay cho `var`.

## Cau 11

[TYPE: MULTIPLE_CHOICE]

`===` khac gi voi `==` trong JavaScript?

- [ ] `===` nhanh hon `==`
- [ ] Khong co su khac biet
- [x] `===` so sanh ca gia tri va kieu du lieu (khong co type coercion)
- [ ] `===` chi dung cho string

> **Giai thich:** `===` (strict equality) so sanh ca gia tri va kieu du lieu ma khong chuyen doi kieu. `==` (loose equality) se tu dong chuyen doi kieu truoc khi so sanh. Vi du: `"5" == 5` la `true`, nhung `"5" === 5` la `false`.

## Cau 12

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

```javascript
const obj = { a: 1, b: 2, c: 3 };
const { a, ...rest } = obj;
console.log(rest);
```

- [ ] { a: 1, b: 2, c: 3 }
- [ ] { a: 1 }
- [x] { b: 2, c: 3 }
- [ ] [2, 3]

> **Giai thich:** Rest pattern (`...rest`) trong destructuring thu thap cac thuoc tinh con lai vao mot object moi. `a` da duoc lay rieng, nen `rest` chua `{ b: 2, c: 3 }`.

## Cau 13

[TYPE: MULTIPLE_CHOICE]

Promise co bao nhieu trang thai?

- [ ] 2: Resolved va Rejected
- [x] 3: Pending, Fulfilled, Rejected
- [ ] 4: Pending, Loading, Fulfilled, Rejected
- [ ] 1: Completed

> **Giai thich:** Promise co 3 trang thai: **Pending** (dang cho), **Fulfilled** (thanh cong - resolved), va **Rejected** (that bai). Khi da chuyen sang fulfilled hoac rejected, Promise khong the thay doi trang thai nua (settled).

## Cau 14

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

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

> **Giai thich:** Event Loop: (1) Dong bo: "1", "4" chay truoc. (2) Microtask (Promise.then): "3" chay tiep. (3) Macrotask (setTimeout): "2" chay cuoi. Microtask luon duoc xu ly truoc Macrotask.

## Cau 15

[TYPE: MULTIPLE_CHOICE]

Cach nao dung de tao ban sao sau (deep copy) cua mot object?

- [ ] `Object.assign({}, obj)`
- [ ] `{ ...obj }`
- [x] `structuredClone(obj)`
- [ ] `obj.clone()`

> **Giai thich:** `Object.assign` va spread operator (`...`) chi tao shallow copy (nested objects van la tham chieu). `structuredClone()` (ES2022) tao deep copy thuc su. `JSON.parse(JSON.stringify(obj))` cung la deep copy nhung co gioi han (khong ho tro Date, Function, Map, Set).

## Cau 16

[TYPE: TRUE_FALSE]

Menh de: "Mang rong `[]` va object rong `{}` deu la falsy values."

- [ ] Dung
- [x] Sai

> **Giai thich:** `[]` va `{}` deu la **truthy** values! Chi co 8 gia tri falsy trong JS: `false`, `0`, `-0`, `0n`, `""`, `null`, `undefined`, `NaN`. Tat ca object (ke ca mang rong va object rong) deu la truthy.

## Cau 17

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

```javascript
const person = { name: "An" };
Object.freeze(person);
person.name = "Binh";
person.age = 25;
console.log(person);
```

- [ ] { name: "Binh", age: 25 }
- [x] { name: "An" }
- [ ] Error
- [ ] { name: "Binh" }

> **Giai thich:** `Object.freeze()` ngan khong cho thay doi, them, hoac xoa thuoc tinh cua object. Cac phep gan bi bo qua im lang (hoac throw error trong strict mode). Luu y: freeze chi hoat dong o cap dau tien (shallow freeze).

## Cau 18

[TYPE: MULTIPLE_CHOICE]

`async/await` duoc xay dung dua tren co che nao?

- [ ] Callback
- [x] Promise
- [ ] Observable
- [ ] Generator

> **Giai thich:** `async/await` la syntactic sugar (cu phap gon) dua tren Promise. Mot `async` function luon tra ve Promise. `await` tam dung thuc thi cho den khi Promise duoc resolve hoac reject.

## Cau 19

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

```javascript
const arr = [1, 2, 3, 4, 5];
const result = arr.filter(n => n > 2).map(n => n * 10);
console.log(result);
```

- [ ] [10, 20, 30, 40, 50]
- [ ] [3, 4, 5]
- [x] [30, 40, 50]
- [ ] [1, 2, 30, 40, 50]

> **Giai thich:** `filter(n => n > 2)` loc ra `[3, 4, 5]`, sau do `map(n => n * 10)` nhan moi phan tu voi 10, tra ve `[30, 40, 50]`.

## Cau 20

[TYPE: MULTIPLE_CHOICE]

Event Delegation la gi?

- [ ] Xoa event listener sau khi su dung
- [ ] Truyen event tu server xuong client
- [x] Gan event listener cho element cha de xu ly event cua cac element con
- [ ] Tao nhieu event listener cho moi element

> **Giai thich:** Event Delegation la ky thuat gan 1 event listener cho element cha thay vi nhieu listener cho tung element con. No tan dung Event Bubbling - khi event xay ra tren element con, no se "bubble" (noi len) den element cha.

## Cau 21

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

```javascript
console.log(typeof typeof 42);
```

- [ ] "number"
- [x] "string"
- [ ] "typeof"
- [ ] undefined

> **Giai thich:** `typeof 42` tra ve chuoi `"number"`. Sau do `typeof "number"` tra ve `"string"`. Ket qua cua `typeof` luon la mot string.

## Cau 22

[TYPE: MULTIPLE_CHOICE]

Cau nao dung ve `const` trong JavaScript?

- [ ] Gia tri cua bien `const` khong bao gio thay doi duoc
- [x] Bien `const` khong the gan lai (re-assign), nhung noi dung cua object/array van co the thay doi
- [ ] `const` giong het `let` nhung nhanh hon
- [ ] `const` chi dung cho so va string

> **Giai thich:** `const` ngan viec gan lai (re-assign) bien, nhung khong ngan viec thay doi noi dung cua object/array. Vi du: `const arr = [1]; arr.push(2);` la hop le, nhung `arr = [3]` se loi.

## Cau 23

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

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

> **Giai thich:** Khi dung object lam key, no duoc chuyen thanh string `"[object Object]"`. Ca `b` va `c` deu thanh cung mot key `"[object Object]"`, nen `a[c] = 456` ghi de len `a[b] = 123`. Do do `a[b]` tra ve `456`.

## Cau 24

[TYPE: MULTIPLE_CHOICE]

Cach nao de kiem tra mot bien la Array?

- [ ] `typeof arr === "array"`
- [ ] `arr.type === "Array"`
- [x] `Array.isArray(arr)`
- [ ] `arr typeof Array`

> **Giai thich:** `typeof []` tra ve `"object"` (khong phai "array"), nen khong dung `typeof` de kiem tra. `Array.isArray()` la cach chuan va dang tin cay nhat. Co the dung `arr instanceof Array` nhung no co van de voi cross-frame/iframe.

## Cau 25

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

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

> **Giai thich:** JavaScript tu dong them dau cham phay (ASI - Automatic Semicolon Insertion) sau `return` vi dong tiep theo bat dau tren dong moi. Ham thuc su la `return;` nen tra ve `undefined`. De fix: `return {` phai cung dong voi `return`.

## Cau 26

[TYPE: MULTIPLE_CHOICE]

Nullish Coalescing Operator `??` khac gi voi `||`?

- [ ] Khong co su khac biet
- [ ] `??` chi dung cho so
- [x] `??` chi kiem tra `null` va `undefined`, con `||` kiem tra tat ca falsy values
- [ ] `??` la phien ban cu cua `||`

> **Giai thich:** `||` tra ve gia tri ben phai neu ben trai la falsy (0, "", false, null, undefined, NaN). `??` chi tra ve gia tri ben phai khi ben trai la `null` hoac `undefined`. Vi du: `0 || 5` = `5`, nhung `0 ?? 5` = `0`.

## Cau 27

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

```javascript
const obj = { a: 1, b: 2 };
const clone = { ...obj, b: 3, c: 4 };
console.log(clone);
```

- [ ] { a: 1, b: 2 }
- [ ] { a: 1, b: 2, c: 4 }
- [x] { a: 1, b: 3, c: 4 }
- [ ] Error

> **Giai thich:** Spread operator (`...obj`) trai cac thuoc tinh cua obj, sau do `b: 3` ghi de gia tri cu cua `b`, va `c: 4` duoc them moi. Thu tu quan trong: thuoc tinh sau se ghi de thuoc tinh truoc neu cung key.

## Cau 28

[TYPE: TRUE_FALSE]

Menh de: "`NaN === NaN` tra ve `true`."

- [ ] Dung
- [x] Sai

> **Giai thich:** `NaN` la gia tri duy nhat trong JavaScript khong bang chinh no! `NaN === NaN` tra ve `false`. De kiem tra NaN, dung `Number.isNaN(value)` hoac `Object.is(value, NaN)`.

## Cau 29

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

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

> **Giai thich:** Primitive values (number, string, boolean,...) duoc truyen theo gia tri (pass by value). Khi `y = x`, gia tri 10 duoc copy sang `y`. Thay doi `x` thanh 20 khong anh huong den `y`.

## Cau 30

[TYPE: MULTIPLE_CHOICE]

Phuong thuc `addEventListener` co uu diem gi so voi `onclick`?

- [ ] Nhanh hon
- [ ] De viet hon
- [x] Co the gan nhieu handler cho cung mot event tren cung element
- [ ] Ho tro nhieu trinh duyet hon

> **Giai thich:** `addEventListener` cho phep them nhieu handler cho cung mot event, ho tro capturing phase, va co tuy chon nhu `once`, `passive`. `onclick` (DOM property) chi cho phep 1 handler - handler moi se ghi de handler cu.

## Cau 31

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

```javascript
console.log(0.1 + 0.2 === 0.3);
```

- [ ] true
- [x] false
- [ ] Error
- [ ] undefined

> **Giai thich:** Do IEEE 754 floating-point arithmetic, `0.1 + 0.2` = `0.30000000000000004`, khong chinh xac bang `0.3`. De so sanh so thap phan, dung `Math.abs(0.1 + 0.2 - 0.3) < Number.EPSILON` hoac lam viec voi so nguyen (nhan 100).

## Cau 32

[TYPE: MULTIPLE_CHOICE]

Generator function duoc khai bao nhu the nao?

- [ ] `function gen() {}`
- [x] `function* gen() {}`
- [ ] `async function gen() {}`
- [ ] `generator function gen() {}`

> **Giai thich:** Generator function duoc khai bao bang `function*` (co dau `*`). Ben trong co the dung tu khoa `yield` de tam dung va tiep tuc thuc thi. Generator tra ve mot iterator khi goi.

## Cau 33

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

```javascript
const arr = [1, 2, 3];
arr[10] = 11;
console.log(arr.length);
```

- [ ] 3
- [ ] 4
- [x] 11
- [ ] 10

> **Giai thich:** Khi gan gia tri tai index 10, JavaScript tu dong tang `length` thanh 11. Cac vi tri tu index 3 den 9 la "empty slots" (holes) - chung ton tai nhung khong co gia tri.

## Cau 34

[TYPE: MULTIPLE_CHOICE]

`Map` khac gi so voi Object thong thuong?

- [ ] Map nhanh hon Object
- [ ] Map chi chua string values
- [x] Map cho phep key la bat ky kieu du lieu nao, khong chi string/symbol
- [ ] Map la immutable

> **Giai thich:** Khac voi Object (key chi co the la string hoac symbol), Map cho phep key la bat ky kieu nao (object, function, number,...). Map cung dam bao thu tu chen va co property `size` truc tiep.

## Cau 35

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

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
- [x] undefined roi ReferenceError
- [ ] ReferenceError ngay dong dau

> **Giai thich:** `var a` duoc hoisted len dau ham voi gia tri `undefined`, nen `console.log(a)` in `undefined`. `let b` cung duoc hoisted nhung nam trong Temporal Dead Zone (TDZ), nen truy cap truoc khi khai bao gay `ReferenceError`.

## Cau 36

[TYPE: MULTIPLE_CHOICE]

Cach nao huy mot Fetch request dang chay?

- [ ] `fetch.cancel()`
- [ ] `request.abort()`
- [x] Su dung `AbortController` va truyen `signal` vao fetch options
- [ ] Khong the huy Fetch request

> **Giai thich:** Dung `const controller = new AbortController()`, truyen `{ signal: controller.signal }` vao fetch, roi goi `controller.abort()` de huy. Fetch se throw mot `AbortError`.

## Cau 37

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

```javascript
const set = new Set([1, 2, 3, 3, 2, 1]);
console.log(set.size);
```

- [ ] 6
- [x] 3
- [ ] 1
- [ ] Error

> **Giai thich:** `Set` chi luu tru cac gia tri duy nhat (khong trung lap). Tu mang `[1, 2, 3, 3, 2, 1]`, cac gia tri trung se bi loai bo, con lai `{1, 2, 3}` voi `size = 3`.

## Cau 38

[TYPE: TRUE_FALSE]

Menh de: "`forEach()` co the dung `break` hoac `return` de dung vong lap."

- [ ] Dung
- [x] Sai

> **Giai thich:** `forEach` KHONG the dung `break` de thoat vong lap. `return` trong forEach chi thoat khoi callback hien tai (tuong tu `continue`), khong dung ham forEach. De dung som, dung `for...of` voi `break`, hoac dung `some()`/`every()`.

## Cau 39

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

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

> **Giai thich:** Arrow function khong co `this` rieng, no ke thua tu scope cha. O day scope cha la global/module scope, khong phai object `user`. `this.name` la `undefined`. De fix, dung regular function: `greet() { return \`Hello ${this.name}\`; }`.

## Cau 40

[TYPE: MULTIPLE_CHOICE]

`Symbol` dung de lam gi?

- [ ] Tao icon cho web
- [ ] Ma hoa du lieu
- [x] Tao gia tri duy nhat (unique) dung lam property key
- [ ] Khai bao hang so

> **Giai thich:** `Symbol()` tao mot gia tri primitive duy nhat va bat bien. Chinh yeu duoc dung lam property key de tranh xung dot ten, va cung duoc dung cho cac well-known Symbols (Symbol.iterator, Symbol.toPrimitive,...).

## Cau 41

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

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

> **Giai thich:** Unary plus (`+`) chuyen doi sang Number: `+""` = `0`, `+true` = `1`, `+null` = `0`, `+undefined` = `NaN`. Luu y: `null` chuyen thanh `0` nhung `undefined` chuyen thanh `NaN`.

## Cau 42

[TYPE: MULTIPLE_CHOICE]

`WeakMap` khac gi so voi `Map`?

- [ ] WeakMap nhanh hon Map
- [x] Key cua WeakMap phai la object va co the bi garbage collected
- [ ] WeakMap co the iterate (duyet)
- [ ] WeakMap luu tru nhieu du lieu hon

> **Giai thich:** WeakMap chi chap nhan object lam key va giu "weak reference" - key co the bi garbage collected khi khong con tham chieu nao khac. WeakMap khong co `size`, khong the iterate, va khong co method `keys()`/`values()`/`entries()`.

## Cau 43

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

```javascript
const [a, , b, ...rest] = [1, 2, 3, 4, 5];
console.log(a, b, rest);
```

- [ ] 1, 2, [3, 4, 5]
- [ ] 1, 3, [4, 5]
- [x] 1, 3, [4, 5]
- [ ] 1, 2, [4, 5]

> **Giai thich:** Destructuring: `a = 1`, bo qua index 1 (gia tri 2), `b = 3`, `...rest` = `[4, 5]`. Dau phay trong bo qua phan tu tai vi tri do.

## Cau 44

[TYPE: MULTIPLE_CHOICE]

`Proxy` trong JavaScript dung de lam gi?

- [ ] Ket noi voi proxy server
- [ ] Bao mat du lieu
- [x] Chan va tuy chinh cac thao tac tren object (get, set, delete,...)
- [ ] Tao ban sao cua object

> **Giai thich:** Proxy cho phep dinh nghia cac "trap" (bo chan) cho cac thao tac co ban tren object nhu doc thuoc tinh (get), ghi thuoc tinh (set), xoa (deleteProperty), kiem tra (has), v.v.

## Cau 45

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

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

> **Giai thich:** `async` function luon tra ve mot Promise. Khi `return 42`, no tuong duong `return Promise.resolve(42)`. `console.log` hien thi Promise object. De lay gia tri 42, can dung `await test()` hoac `.then(val => ...)`.

## Cau 46

[TYPE: TRUE_FALSE]

Menh de: "`Object.keys()` tra ve ca cac thuoc tinh ke thua tu prototype."

- [ ] Dung
- [x] Sai

> **Giai thich:** `Object.keys()` chi tra ve cac own enumerable string properties cua object, KHONG bao gom thuoc tinh ke thua tu prototype. De lay ca thuoc tinh ke thua, dung `for...in` loop.

## Cau 47

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

```javascript
const nums = [10, 5, 20, 1, 100];
console.log(nums.sort());
```

- [ ] [1, 5, 10, 20, 100]
- [x] [1, 10, 100, 20, 5]
- [ ] [100, 20, 10, 5, 1]
- [ ] Error

> **Giai thich:** `sort()` mac dinh sap xep theo thu tu tu dien (string). So duoc chuyen sang string roi so sanh: "1" < "10" < "100" < "20" < "5". De sap xep so dung: `nums.sort((a, b) => a - b)`.

## Cau 48

[TYPE: MULTIPLE_CHOICE]

Optional Chaining (`?.`) hoat dong nhu the nao?

- [ ] Throw error neu thuoc tinh khong ton tai
- [x] Tra ve `undefined` thay vi throw error khi truy cap thuoc tinh cua null/undefined
- [ ] Tao thuoc tinh moi neu chua ton tai
- [ ] Chi dung duoc voi object, khong dung voi array

> **Giai thich:** Optional Chaining (`?.`) kiem tra xem gia tri truoc no co phai null/undefined khong. Neu co, tra ve `undefined` ngay ma khong tiep tuc truy cap. Co the dung voi properties (`?.`), methods (`?.()`), va array index (`?.[]`).

## Cau 49

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

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

> **Giai thich:** Generator function tam dung tai moi `yield`. Lan `next()` dau tra ve `{ value: 1, done: false }`, lan thu hai tra ve `{ value: 2, done: false }`. Moi lan goi `next()`, generator tiep tuc tu vi tri yield truoc do.

## Cau 50

[TYPE: SELECT_RESULT]

Dau ra cua doan code sau la gi?

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
- [ ] Error vi promise2 reject

> **Giai thich:** `Promise.allSettled()` cho tat ca promises hoan thanh (khong quan tam thanh cong hay that bai). Ket qua co 3 phan tu. `results[1]` la `{ status: "rejected", reason: "B" }` vi `promise2` bi reject.
