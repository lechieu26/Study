# Quiz - TypeScript

## Câu 1

[TYPE: MULTIPLE_CHOICE]

TypeScript là gì?

- [ ] Một ngôn ngữ lập trình hoàn toàn mới
- [x] Một superset của JavaScript thêm hệ thống kiểu tĩnh
- [ ] Một framework JavaScript
- [ ] Một trình duyệt web

> **Giải thích:** TypeScript là superset của JavaScript - mọi code JavaScript hợp lệ đều là TypeScript hợp lệ. TypeScript thêm type system, interfaces, generics, ... và compile thành JavaScript để chạy.

## Câu 2

[TYPE: SELECT_RESULT]

Đoạn code TypeScript sau có lỗi gì?

```typescript
let name: string = "An";
name = 42;
```

- [ ] Không có lỗi
- [x] Lỗi: không thể gán number vào biến kiểu string
- [ ] Lỗi: biến `name` đã tồn tại
- [ ] Lỗi: thiếu dấu chấm phẩy

> **Giải thích:** Biến `name` được khai báo kiểu `string`. Gán `42` (number) vi phạm type safety. Đây là lỗi compile-time mà TypeScript phát hiện, JavaScript sẽ không báo lỗi này.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Sự khác biệt giữa `interface` và `type` trong TypeScript?

- [ ] Không có sự khác biệt
- [ ] `type` không thể dùng cho objects
- [x] `interface` có thể merge declarations và extends, `type` hỗ trợ union và intersection
- [ ] `interface` nhanh hơn `type`

> **Giải thích:** `interface` hỗ trợ declaration merging (khai báo lại để thêm thuộc tính) và `extends`. `type` hỗ trợ union (`|`), intersection (`&`), mapped types, conditional types. Cho object shapes, cả hai đều được; convention là dùng `interface` cho objects, `type` cho union/utility.

## Câu 4

[TYPE: MULTIPLE_CHOICE]

`unknown` khác `any` như thế nào?

- [ ] Giống nhau
- [ ] `unknown` nhanh hơn `any`
- [x] `unknown` an toàn hơn - phải kiểm tra kiểu trước khi sử dụng
- [ ] `any` an toàn hơn `unknown`

> **Giải thích:** `any` cho phép làm bất kỳ gì mà không kiểm tra kiểu (tắt type checking). `unknown` cũng nhận mọi giá trị nhưng BẮT BUỘC phải narrow (kiểm tra kiểu) trước khi sử dụng. `unknown` là lựa chọn an toàn khi không biết kiểu.

## Câu 5

[TYPE: SELECT_RESULT]

Kiểu trả về của hàm sau là gì?

```typescript
function getValue(key: string): string | undefined {
    const map: Record<string, string> = { a: "1" };
    return map[key];
}
```

- [ ] `string`
- [x] `string | undefined`
- [ ] `any`
- [ ] `unknown`

> **Giải thích:** Hàm khai báo trả về `string | undefined`. Vì `map[key]` có thể không tồn tại (trả về `undefined`), kiểu union `string | undefined` là chính xác. Người gọi phải kiểm tra `undefined` trước khi sử dụng.

## Câu 6

[TYPE: MULTIPLE_CHOICE]

`Partial<User>` làm gì?

- [ ] Xóa hết thuộc tính của User
- [x] Biến tất cả thuộc tính của User thành optional (?)
- [ ] Biến tất cả thuộc tính thành readonly
- [ ] Chỉ lấy một phần của User

> **Giải thích:** `Partial<T>` là utility type biến tất cả thuộc tính thành optional. `Partial<{name: string; age: number}>` = `{name?: string; age?: number}`. Rất hữu ích cho update operations (chỉ gửi các trường cần thay đổi).

## Câu 7

[TYPE: MULTIPLE_CHOICE]

Generic constraint `<T extends HasLength>` có ý nghĩa gì?

- [ ] T phải bằng HasLength
- [x] T phải có tất cả thuộc tính của HasLength (và có thể có thêm)
- [ ] T là con của HasLength
- [ ] T không được có thuộc tính của HasLength

> **Giải thích:** `T extends HasLength` nghĩa là T phải **ít nhất** có các thuộc tính của HasLength. Nếu `HasLength = { length: number }`, thì T phải có `length: number` (string, array, ... đều OK). T có thể có thêm thuộc tính khác.

## Câu 8

[TYPE: SELECT_RESULT]

Kiểu của `result` là gì?

```typescript
type ApiResponse<T> =
    | { ok: true; data: T }
    | { ok: false; error: string };

function handle(res: ApiResponse<number>) {
    if (res.ok) {
        const result = res.data;
    }
}
```

- [x] `number`
- [ ] `number | string`
- [ ] `unknown`
- [ ] `T`

> **Giải thích:** Sau khi kiểm tra `res.ok === true` (discriminated union narrowing), TypeScript biết `res` là `{ ok: true; data: number }`. Do đó `res.data` có kiểu `number` (generic T được thay bằng number).

## Câu 9

[TYPE: MULTIPLE_CHOICE]

Tại sao nên tránh dùng `enum` trong TypeScript hiện đại?

- [ ] Enum đã bị deprecated
- [ ] Enum không hoạt động
- [x] Union type (`"a" | "b"`) nhẹ hơn, không tạo runtime code thừa, và tree-shakeable
- [ ] Enum không type-safe

> **Giải thích:** `enum` tạo ra JavaScript runtime code (object). Union type (`type Status = "active" | "inactive"`) chỉ tồn tại tại compile-time, không tạo runtime code, nhẹ hơn cho bundle size. `const enum` là ở giữa: inline giá trị nhưng có hạn chế với declaration files.

## Câu 10

[TYPE: MULTIPLE_CHOICE]

`keyof` operator trả về gì?

- [ ] Giá trị của tất cả key
- [x] Union type của tất cả tên thuộc tính (keys) của một type
- [ ] Số lượng keys
- [ ] Mảng các keys

> **Giải thích:** `keyof T` trả về union của các key names. Với `type User = { id: number; name: string }`, `keyof User` = `"id" | "name"`. Đây là compile-time type, không phải runtime value. Kết hợp với generics (`K extends keyof T`) để tạo type-safe property access.

## Câu 11

[TYPE: MULTIPLE_CHOICE]

`as const` có tác dụng gì?

- [ ] Biến đổi thành class
- [ ] Giống như `Object.freeze()`
- [x] Ép TypeScript suy ra kiểu literal hẹp nhất (readonly và literal types)
- [ ] Chỉ dùng với string

> **Giải thích:** `as const` (const assertion) khiến TypeScript suy ra kiểu hẹp nhất. `const x = [1, 2]` có kiểu `number[]`, nhưng `const x = [1, 2] as const` có kiểu `readonly [1, 2]`. Tương tự, `{ a: "hello" } as const` có kiểu `{ readonly a: "hello" }` thay vì `{ a: string }`.

## Câu 12

[TYPE: MULTIPLE_CHOICE]

Custom type guard `value is string` có tác dụng gì?

- [ ] Ép value thành string
- [ ] Kiểm tra value lúc runtime
- [x] Báo TypeScript rằng khi hàm trả về true, value có kiểu string (narrowing)
- [ ] Tương đương với typeof

> **Giải thích:** `function isString(v: unknown): v is string` là type predicate. Khi hàm trả về `true`, TypeScript tự động narrow kiểu của tham số thành `string` trong block `if (isString(v))`. Đây là cách tạo custom narrowing logic mà TypeScript có thể hiểu.
