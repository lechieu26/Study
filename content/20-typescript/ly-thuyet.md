# TypeScript - Lý Thuyết Từ Cơ Bản Đến Nâng Cao

## Mục lục

1. [Giới thiệu về TypeScript](#1-gioi-thieu-ve-typescript)
2. [Cài đặt và Cấu hình](#2-cai-dat-va-cau-hinh)
3. [Basic Types](#3-basic-types)
4. [Interfaces và Type Aliases](#4-interfaces-va-type-aliases)
5. [Functions](#5-functions)
6. [Classes](#6-classes)
7. [Generics](#7-generics)
8. [Enums](#8-enums)
9. [Union và Intersection Types](#9-union-va-intersection-types)
10. [Type Guards và Narrowing](#10-type-guards-va-narrowing)
11. [Utility Types](#11-utility-types)
12. [Advanced Types](#12-advanced-types)
13. [Modules và Namespaces](#13-modules-va-namespaces)
14. [Declaration Files](#14-declaration-files)
15. [Best Practices](#15-best-practices)

---

## 1. Giới thiệu về TypeScript

### 1.1 TypeScript là gì?

TypeScript là **superset của JavaScript** do Microsoft phát triển. Nó thêm **hệ thống kiểu (type system)** vào JavaScript, giúp phát hiện lỗi tại thời điểm biên dịch (compile-time) thay vì khi chạy (runtime).

### 1.2 Tại sao dùng TypeScript?

| JavaScript | TypeScript |
|-----------|-----------|
| Kiểu động (dynamic) | Kiểu tĩnh (static) |
| Lỗi phát hiện lúc runtime | Lỗi phát hiện lúc compile |
| IntelliSense hạn chế | IntelliSense mạnh mẽ |
| Khó refactor dự án lớn | Refactor an toàn |
| Không có interface | Interface, Generics, Enums |

### 1.3 TypeScript Compiler

```
TypeScript (.ts) → tsc compiler → JavaScript (.js) → Trình duyệt/Node.js
```

---

## 2. Cài đặt và Cấu hình

### 2.1 Cài đặt

```bash
# Cài đặt global
npm install -g typescript

# Kiểm tra phiên bản
tsc --version

# Cài đặt trong project
npm install --save-dev typescript
```

### 2.2 tsconfig.json

```json
{
  "compilerOptions": {
    "target": "ES2020",
    "module": "ESNext",
    "lib": ["ES2020", "DOM"],
    "strict": true,
    "esModuleInterop": true,
    "skipLibCheck": true,
    "forceConsistentCasingInFileNames": true,
    "outDir": "./dist",
    "rootDir": "./src",
    "declaration": true,
    "sourceMap": true,
    "noUnusedLocals": true,
    "noUnusedParameters": true,
    "noImplicitReturns": true
  },
  "include": ["src/**/*"],
  "exclude": ["node_modules", "dist"]
}
```

---

## 3. Basic Types

### 3.1 Primitive Types

```typescript
// String
let name: string = "Nguyen Van A";
let greeting: string = `Xin chào ${name}`;

// Number
let age: number = 25;
let price: number = 99.99;
let hex: number = 0xff;

// Boolean
let isActive: boolean = true;

// Null và Undefined
let empty: null = null;
let notDefined: undefined = undefined;

// BigInt
let bigNumber: bigint = 100n;

// Symbol
let sym: symbol = Symbol("unique");
```

### 3.2 Array và Tuple

```typescript
// Array
let numbers: number[] = [1, 2, 3];
let names: Array<string> = ["An", "Bình", "Chi"];

// Tuple - mảng cố định kiểu và số lượng
let person: [string, number] = ["An", 25];
let rgb: [number, number, number] = [255, 128, 0];

// Readonly Array
let readonlyArr: readonly number[] = [1, 2, 3];
// readonlyArr.push(4); // Lỗi!
```

### 3.3 Any, Unknown, Never, Void

```typescript
// any - tắt kiểu (TRÁNH DÙNG)
let anything: any = "hello";
anything = 42;    // OK nhưng mất an toàn kiểu

// unknown - an toàn hơn any, phải kiểm tra kiểu trước khi dùng
let value: unknown = "hello";
if (typeof value === "string") {
    console.log(value.toUpperCase()); // OK sau khi kiểm tra
}

// void - hàm không trả về gì
function log(msg: string): void {
    console.log(msg);
}

// never - hàm không bao giờ kết thúc bình thường
function throwError(msg: string): never {
    throw new Error(msg);
}

function infiniteLoop(): never {
    while (true) {}
}
```

### 3.4 Object và Type Assertions

```typescript
// Object type
let user: { name: string; age: number } = {
    name: "An",
    age: 25
};

// Type Assertion (ép kiểu)
let someValue: unknown = "hello world";
let strLength: number = (someValue as string).length;
// Hoặc: let strLength = (<string>someValue).length;

// Non-null Assertion
let el: HTMLElement | null = document.getElementById("app");
el!.innerHTML = "Hello"; // ! khẳng định không null (cẩn thận!)
```

---

## 4. Interfaces và Type Aliases

### 4.1 Interface

```typescript
interface User {
    id: number;
    name: string;
    email: string;
    age?: number;                    // Optional
    readonly createdAt: Date;        // Chỉ đọc
}

// Kế thừa
interface Admin extends User {
    role: "admin" | "superadmin";
    permissions: string[];
}

// Implement
const admin: Admin = {
    id: 1,
    name: "An",
    email: "an@email.com",
    role: "admin",
    permissions: ["read", "write"],
    createdAt: new Date()
};
```

### 4.2 Type Alias

```typescript
type ID = string | number;

type Point = {
    x: number;
    y: number;
};

type Status = "active" | "inactive" | "pending";

type ApiResponse<T> = {
    data: T;
    status: number;
    message: string;
};

// Intersection (kết hợp)
type Employee = User & {
    department: string;
    salary: number;
};
```

### 4.3 Interface vs Type

| Interface | Type |
|-----------|------|
| Khai báo lại để merge | Không thể khai báo lại |
| extends để kế thừa | & để kết hợp |
| Tốt cho OOP patterns | Tốt cho union, tuple, mapped types |
| Dễ đọc hơn cho object shape | Linh hoạt hơn |

---

## 5. Functions

### 5.1 Function Types

```typescript
// Khai báo kiểu tham số và trả về
function add(a: number, b: number): number {
    return a + b;
}

// Arrow function
const multiply = (a: number, b: number): number => a * b;

// Optional và Default parameters
function greet(name: string, greeting: string = "Xin chào"): string {
    return `${greeting}, ${name}!`;
}

// Rest parameters
function sum(...numbers: number[]): number {
    return numbers.reduce((acc, n) => acc + n, 0);
}

// Function type
type MathFn = (a: number, b: number) => number;
const divide: MathFn = (a, b) => a / b;
```

### 5.2 Function Overloading

```typescript
function format(input: string): string;
function format(input: number): string;
function format(input: string | number): string {
    if (typeof input === "string") return input.trim();
    return input.toFixed(2);
}

format("  hello  "); // "hello"
format(3.14159);     // "3.14"
```

---

## 6. Classes

### 6.1 Class cơ bản

```typescript
class Animal {
    // Access modifiers
    public name: string;
    protected species: string;
    private _age: number;
    readonly id: number;

    constructor(name: string, species: string, age: number) {
        this.name = name;
        this.species = species;
        this._age = age;
        this.id = Math.random();
    }

    // Getter/Setter
    get age(): number { return this._age; }
    set age(value: number) {
        if (value < 0) throw new Error("Age must be positive");
        this._age = value;
    }

    // Method
    speak(): string {
        return `${this.name} says hello`;
    }
}

// Shorthand constructor
class Point {
    constructor(
        public x: number,
        public y: number
    ) {}
}
```

### 6.2 Kế thừa và Abstract

```typescript
abstract class Shape {
    abstract area(): number;
    abstract perimeter(): number;

    describe(): string {
        return `Area: ${this.area()}, Perimeter: ${this.perimeter()}`;
    }
}

class Circle extends Shape {
    constructor(private radius: number) {
        super();
    }

    area(): number { return Math.PI * this.radius ** 2; }
    perimeter(): number { return 2 * Math.PI * this.radius; }
}

class Rectangle extends Shape {
    constructor(
        private width: number,
        private height: number
    ) { super(); }

    area(): number { return this.width * this.height; }
    perimeter(): number { return 2 * (this.width + this.height); }
}
```

### 6.3 Interface với Class

```typescript
interface Serializable {
    serialize(): string;
}

interface Loggable {
    log(): void;
}

class UserModel implements Serializable, Loggable {
    constructor(public name: string, public email: string) {}

    serialize(): string {
        return JSON.stringify({ name: this.name, email: this.email });
    }

    log(): void {
        console.log(`User: ${this.name} (${this.email})`);
    }
}
```

---

## 7. Generics

### 7.1 Generic Functions

```typescript
function identity<T>(value: T): T {
    return value;
}

identity<string>("hello");  // kiểu string
identity(42);              // TypeScript tự suy ra kiểu number

// Nhiều generic
function pair<K, V>(key: K, value: V): [K, V] {
    return [key, value];
}
```

### 7.2 Generic Constraints

```typescript
interface HasLength {
    length: number;
}

function logLength<T extends HasLength>(item: T): T {
    console.log(item.length);
    return item;
}

logLength("hello");      // OK - string có length
logLength([1, 2, 3]);   // OK - array có length
// logLength(42);        // Lỗi - number không có length

// keyof constraint
function getProperty<T, K extends keyof T>(obj: T, key: K): T[K] {
    return obj[key];
}

const user = { name: "An", age: 25 };
getProperty(user, "name"); // OK, trả về string
// getProperty(user, "email"); // Lỗi! "email" không phải key của user
```

### 7.3 Generic Classes và Interfaces

```typescript
interface Repository<T> {
    findById(id: string): Promise<T>;
    findAll(): Promise<T[]>;
    save(item: T): Promise<T>;
    delete(id: string): Promise<void>;
}

class InMemoryRepo<T extends { id: string }> implements Repository<T> {
    private items: Map<string, T> = new Map();

    async findById(id: string): Promise<T> {
        const item = this.items.get(id);
        if (!item) throw new Error("Not found");
        return item;
    }

    async findAll(): Promise<T[]> {
        return Array.from(this.items.values());
    }

    async save(item: T): Promise<T> {
        this.items.set(item.id, item);
        return item;
    }

    async delete(id: string): Promise<void> {
        this.items.delete(id);
    }
}
```

---

## 8. Enums

```typescript
// Numeric Enum
enum Direction {
    Up = 0,
    Down = 1,
    Left = 2,
    Right = 3
}

// String Enum (KHUYÊN DÙNG)
enum Status {
    Active = "ACTIVE",
    Inactive = "INACTIVE",
    Pending = "PENDING"
}

// Const Enum (tối ưu performance - inline khi compile)
const enum Color {
    Red = "#ff0000",
    Green = "#00ff00",
    Blue = "#0000ff"
}

// Sử dụng
let dir: Direction = Direction.Up;
let status: Status = Status.Active;

// Thay thế enum bằng union type (tối ưu hơn)
type StatusType = "ACTIVE" | "INACTIVE" | "PENDING";
```

---

## 9. Union và Intersection Types

### 9.1 Union Types

```typescript
// Biến có thể là nhiều kiểu
type StringOrNumber = string | number;

function format(value: StringOrNumber): string {
    if (typeof value === "string") return value.toUpperCase();
    return value.toFixed(2);
}

// Literal Union
type Theme = "light" | "dark" | "system";
type HttpMethod = "GET" | "POST" | "PUT" | "DELETE";

// Discriminated Union
type Shape =
    | { kind: "circle"; radius: number }
    | { kind: "rectangle"; width: number; height: number }
    | { kind: "triangle"; base: number; height: number };

function area(shape: Shape): number {
    switch (shape.kind) {
        case "circle": return Math.PI * shape.radius ** 2;
        case "rectangle": return shape.width * shape.height;
        case "triangle": return 0.5 * shape.base * shape.height;
    }
}
```

### 9.2 Intersection Types

```typescript
type Timestamped = { createdAt: Date; updatedAt: Date };
type SoftDeletable = { deletedAt: Date | null };

type BaseEntity = Timestamped & SoftDeletable;

type Product = BaseEntity & {
    id: string;
    name: string;
    price: number;
};
```

---

## 10. Type Guards và Narrowing

```typescript
// typeof guard
function process(value: string | number) {
    if (typeof value === "string") {
        return value.toUpperCase(); // TypeScript biết đây là string
    }
    return value * 2;               // TypeScript biết đây là number
}

// instanceof guard
function handleError(error: Error | string) {
    if (error instanceof Error) {
        console.log(error.message);
    } else {
        console.log(error);
    }
}

// in guard
interface Dog { bark(): void; breed: string; }
interface Cat { meow(): void; color: string; }

function speak(animal: Dog | Cat) {
    if ("bark" in animal) {
        animal.bark();
    } else {
        animal.meow();
    }
}

// Custom Type Guard
function isString(value: unknown): value is string {
    return typeof value === "string";
}

function example(value: unknown) {
    if (isString(value)) {
        console.log(value.toUpperCase()); // OK - narrowed to string
    }
}
```

---

## 11. Utility Types

```typescript
interface User {
    id: number;
    name: string;
    email: string;
    age: number;
}

// Partial - tất cả thuộc tính thành optional
type UpdateUser = Partial<User>;
// { id?: number; name?: string; email?: string; age?: number }

// Required - tất cả thuộc tính thành bắt buộc
type RequiredUser = Required<User>;

// Readonly - tất cả thuộc tính chỉ đọc
type FrozenUser = Readonly<User>;

// Pick - chọn một số thuộc tính
type UserPreview = Pick<User, "id" | "name">;
// { id: number; name: string }

// Omit - bỏ một số thuộc tính
type CreateUser = Omit<User, "id">;
// { name: string; email: string; age: number }

// Record - tạo object type từ key và value type
type UserMap = Record<string, User>;

// Exclude - loại kiểu khỏi union
type NotString = Exclude<string | number | boolean, string>;
// number | boolean

// Extract - lấy kiểu từ union
type OnlyString = Extract<string | number | boolean, string>;
// string

// NonNullable - loại null và undefined
type SafeValue = NonNullable<string | null | undefined>;
// string

// ReturnType - lấy kiểu trả về của function
function getUser() { return { id: 1, name: "An" }; }
type UserReturn = ReturnType<typeof getUser>;
// { id: number; name: string }

// Parameters - lấy kiểu tham số của function
type GetUserParams = Parameters<typeof getUser>;
// []
```

---

## 12. Advanced Types

### 12.1 Mapped Types

```typescript
type Optional<T> = {
    [K in keyof T]?: T[K];
};

type ReadonlyAll<T> = {
    readonly [K in keyof T]: T[K];
};

// Mapped type với key remapping
type Getters<T> = {
    [K in keyof T as `get${Capitalize<string & K>}`]: () => T[K];
};

type UserGetters = Getters<User>;
// { getId: () => number; getName: () => string; ... }
```

### 12.2 Conditional Types

```typescript
type IsString<T> = T extends string ? true : false;

type A = IsString<"hello">; // true
type B = IsString<42>;       // false

// infer - suy ra kiểu bên trong
type ArrayElement<T> = T extends (infer U)[] ? U : never;
type Elem = ArrayElement<number[]>; // number

type Awaited<T> = T extends Promise<infer U> ? U : T;
type Result = Awaited<Promise<string>>; // string
```

### 12.3 Template Literal Types

```typescript
type EventName = "click" | "scroll" | "mousemove";
type Handler = `on${Capitalize<EventName>}`;
// "onClick" | "onScroll" | "onMousemove"

type CSSProperty = `${string}-${string}`;
// Bất kỳ string có chứa "-"
```

---

## 13. Modules và Namespaces

### 13.1 ES Modules

```typescript
// user.ts - Export
export interface User {
    id: number;
    name: string;
}

export function createUser(name: string): User {
    return { id: Date.now(), name };
}

export default class UserService {
    findAll(): User[] { return []; }
}

// app.ts - Import
import UserService, { User, createUser } from "./user";
import type { User as UserType } from "./user"; // Type-only import
```

### 13.2 Type-only Imports

```typescript
// Chỉ import kiểu, không import runtime code
import type { User } from "./user";

// Inline type import
import { type User, createUser } from "./user";
```

---

## 14. Declaration Files

### 14.1 .d.ts Files

```typescript
// types.d.ts - Khai báo kiểu cho thư viện JS không có TypeScript
declare module "my-library" {
    export function doSomething(input: string): number;
    export interface Config {
        debug: boolean;
        timeout: number;
    }
}

// Global declarations
declare global {
    interface Window {
        myApp: {
            version: string;
            init(): void;
        };
    }
}
```

---

## 15. Best Practices

1. **Bật `strict: true`** trong tsconfig.json
2. **Tránh `any`:** Dùng `unknown` khi không biết kiểu, rồi narrow
3. **Dùng `interface` cho object shapes**, `type` cho union/intersection
4. **Type-only imports:** Dùng `import type` khi chỉ cần kiểu
5. **Enum vs Union:** Ưu tiên union type (`"a" | "b"`) hơn enum
6. **Discriminated Unions:** Dùng cho state management phức tạp
7. **Utility Types:** Tận dụng Partial, Pick, Omit thay vì tạo type mới
8. **Generic constraints:** Luôn ràng buộc generic (`<T extends ...>`)
9. **Không cast vô tội vạ:** Tránh `as any` - nó phá vỡ type safety
10. **Readonly:** Dùng `readonly` và `Readonly<T>` cho immutable data

---

## Tổng kết

TypeScript tăng cường JavaScript với hệ thống kiểu mạnh mẽ:

1. **Type Safety:** Phát hiện lỗi sớm tại compile time
2. **IntelliSense:** Auto-complete và documentation tốt hơn
3. **Generics:** Code tái sử dụng và type-safe
4. **Utility Types:** Biến đổi kiểu dễ dàng (Partial, Pick, Omit, Record)
5. **Union/Intersection:** Kiểu linh hoạt và an toàn
6. **Narrowing:** Tự động thu hẹp kiểu qua type guards
7. **Advanced Types:** Mapped, Conditional, Template Literal types
