# Quiz - TypeScript

## Cau 1

[TYPE: MULTIPLE_CHOICE]

TypeScript la gi?

- [ ] Mot ngon ngu lap trinh hoan toan moi
- [x] Mot superset cua JavaScript them he thong kieu tinh
- [ ] Mot framework JavaScript
- [ ] Mot trinh duyet web

> **Giai thich:** TypeScript la superset cua JavaScript - moi code JavaScript hop le deu la TypeScript hop le. TypeScript them type system, interfaces, generics, ... va compile thanh JavaScript de chay.

## Cau 2

[TYPE: SELECT_RESULT]

Doan code TypeScript sau co loi gi?

```typescript
let name: string = "An";
name = 42;
```

- [ ] Khong co loi
- [x] Loi: khong the gan number vao bien kieu string
- [ ] Loi: bien `name` da ton tai
- [ ] Loi: thieu dau cham phay

> **Giai thich:** Bien `name` duoc khai bao kieu `string`. Gan `42` (number) vi pham type safety. Day la loi compile-time ma TypeScript phat hien, JavaScript se khong bao loi nay.

## Cau 3

[TYPE: MULTIPLE_CHOICE]

Su khac biet giua `interface` va `type` trong TypeScript?

- [ ] Khong co su khac biet
- [ ] `type` khong the dung cho objects
- [x] `interface` co the merge declarations va extends, `type` ho tro union va intersection
- [ ] `interface` nhanh hon `type`

> **Giai thich:** `interface` ho tro declaration merging (khai bao lai de them thuoc tinh) va `extends`. `type` ho tro union (`|`), intersection (`&`), mapped types, conditional types. Cho object shapes, ca hai deu duoc; convention la dung `interface` cho objects, `type` cho union/utility.

## Cau 4

[TYPE: MULTIPLE_CHOICE]

`unknown` khac `any` nhu the nao?

- [ ] Giong nhau
- [ ] `unknown` nhanh hon `any`
- [x] `unknown` an toan hon - phai kiem tra kieu truoc khi su dung
- [ ] `any` an toan hon `unknown`

> **Giai thich:** `any` cho phep lam bat ky gi ma khong kiem tra kieu (tat type checking). `unknown` cung nhan moi gia tri nhung BAT BUOC phai narrow (kiem tra kieu) truoc khi su dung. `unknown` la lua chon an toan khi khong biet kieu.

## Cau 5

[TYPE: SELECT_RESULT]

Kieu tra ve cua ham sau la gi?

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

> **Giai thich:** Ham khai bao tra ve `string | undefined`. Vi `map[key]` co the khong ton tai (tra ve `undefined`), kieu union `string | undefined` la chinh xac. Nguoi goi phai kiem tra `undefined` truoc khi su dung.

## Cau 6

[TYPE: MULTIPLE_CHOICE]

`Partial<User>` lam gi?

- [ ] Xoa het thuoc tinh cua User
- [x] Bien tat ca thuoc tinh cua User thanh optional (?)
- [ ] Bien tat ca thuoc tinh thanh readonly
- [ ] Chi lay mot phan cua User

> **Giai thich:** `Partial<T>` la utility type bien tat ca thuoc tinh thanh optional. `Partial<{name: string; age: number}>` = `{name?: string; age?: number}`. Rat huu ich cho update operations (chi gui cac truong can thay doi).

## Cau 7

[TYPE: MULTIPLE_CHOICE]

Generic constraint `<T extends HasLength>` co y nghia gi?

- [ ] T phai bang HasLength
- [x] T phai co tat ca thuoc tinh cua HasLength (va co the co them)
- [ ] T la con cua HasLength
- [ ] T khong duoc co thuoc tinh cua HasLength

> **Giai thich:** `T extends HasLength` nghia la T phai **it nhat** co cac thuoc tinh cua HasLength. Neu `HasLength = { length: number }`, thi T phai co `length: number` (string, array, ... deu OK). T co the co them thuoc tinh khac.

## Cau 8

[TYPE: SELECT_RESULT]

Kieu cua `result` la gi?

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

> **Giai thich:** Sau khi kiem tra `res.ok === true` (discriminated union narrowing), TypeScript biet `res` la `{ ok: true; data: number }`. Do do `res.data` co kieu `number` (generic T duoc thay bang number).

## Cau 9

[TYPE: MULTIPLE_CHOICE]

Tai sao nen tranh dung `enum` trong TypeScript hien dai?

- [ ] Enum da bi deprecated
- [ ] Enum khong hoat dong
- [x] Union type (`"a" | "b"`) nhe hon, khong tao runtime code thua, va tree-shakeable
- [ ] Enum khong type-safe

> **Giai thich:** `enum` tao ra JavaScript runtime code (object). Union type (`type Status = "active" | "inactive"`) chi ton tai tai compile-time, khong tao runtime code, nhe hon cho bundle size. `const enum` la giua: inline gia tri nhung co han che voi declaration files.

## Cau 10

[TYPE: MULTIPLE_CHOICE]

`keyof` operator tra ve gi?

- [ ] Gia tri cua tat ca key
- [x] Union type cua tat ca ten thuoc tinh (keys) cua mot type
- [ ] So luong keys
- [ ] Mang cac keys

> **Giai thich:** `keyof T` tra ve union cua cac key names. Voi `type User = { id: number; name: string }`, `keyof User` = `"id" | "name"`. Day la compile-time type, khong phai runtime value. Ket hop voi generics (`K extends keyof T`) de tao type-safe property access.

## Cau 11

[TYPE: MULTIPLE_CHOICE]

`as const` co tac dung gi?

- [ ] Bien doi thanh class
- [ ] Giong nhu `Object.freeze()`
- [x] Ep TypeScript suy ra kieu literal hep nhat (readonly va literal types)
- [ ] Chi dung voi string

> **Giai thich:** `as const` (const assertion) khien TypeScript suy ra kieu hep nhat. `const x = [1, 2]` co kieu `number[]`, nhung `const x = [1, 2] as const` co kieu `readonly [1, 2]`. Tuong tu, `{ a: "hello" } as const` co kieu `{ readonly a: "hello" }` thay vi `{ a: string }`.

## Cau 12

[TYPE: MULTIPLE_CHOICE]

Custom type guard `value is string` co tac dung gi?

- [ ] Ep value thanh string
- [ ] Kiem tra value luc runtime
- [x] Bao TypeScript rằng khi ham tra ve true, value co kieu string (narrowing)
- [ ] Tuong duong voi typeof

> **Giai thich:** `function isString(v: unknown): v is string` la type predicate. Khi ham tra ve `true`, TypeScript tu dong narrow kieu cua tham so thanh `string` trong block `if (isString(v))`. Day la cach tao custom narrowing logic ma TypeScript co the hieu.
