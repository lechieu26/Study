# TypeScript - Bài Tập

## Bài 1: Type-safe Collection
**Độ khó: Trung bình**

Tạo một class `TypedCollection<T>` với các tính năng:

1. `add(item: T)` - thêm phần tử
2. `remove(index: number)` - xóa theo index
3. `get(index: number): T` - lấy theo index
4. `find(predicate: (item: T) => boolean): T | undefined`
5. `filter(predicate: (item: T) => boolean): T[]`
6. `map<U>(fn: (item: T) => U): TypedCollection<U>` - trả về collection mới
7. `toArray(): readonly T[]` - trả về mảng readonly
8. Implement `[Symbol.iterator]` để có thể dùng for...of
9. Thuộc tính `size` (getter)

**Test:**
```typescript
const nums = new TypedCollection<number>();
nums.add(1); nums.add(2); nums.add(3);
nums.filter(n => n > 1);   // [2, 3]
nums.map(n => n * 2);      // TypedCollection<number> [2, 4, 6]
for (const n of nums) { }  // Iterable
```

---

## Bài 2: API Response Handler
**Độ khó: Trung bình**

Thiết kế type system cho API client:

1. Định nghĩa `ApiResponse<T>` với 2 trạng thái: Success và Error (discriminated union)
2. Tạo type `HttpMethod = "GET" | "POST" | "PUT" | "DELETE"`
3. Định nghĩa `RequestConfig<T>` với: url, method, body (chỉ có khi POST/PUT), headers
4. Tạo hàm `fetchData<T>(config: RequestConfig<T>): Promise<ApiResponse<T>>`
5. Tạo utility type `ExtractData<R>` lấy kiểu data từ ApiResponse
6. Tạo type-safe error handler dùng type guards

**Yêu cầu:** Body type phải tự động là `never` khi method là GET hoặc DELETE.

---

## Bài 3: Event Emitter Generic
**Độ khó: Trung bình**

Tạo type-safe EventEmitter:

1. Nhận một type map `Events` định nghĩa tên event và kiểu payload
2. `on<K extends keyof Events>(event: K, listener: (payload: Events[K]) => void)`
3. `off<K extends keyof Events>(event: K, listener: ...)`
4. `emit<K extends keyof Events>(event: K, payload: Events[K])`
5. `once<K extends keyof Events>(event: K, listener: ...)`

**Test:**
```typescript
interface AppEvents {
    login: { userId: string; timestamp: Date };
    logout: { userId: string };
    error: { code: number; message: string };
}

const emitter = new EventEmitter<AppEvents>();
emitter.on("login", (payload) => {
    // payload được infer là { userId: string; timestamp: Date }
    console.log(payload.userId);
});
emitter.emit("login", { userId: "123", timestamp: new Date() }); // OK
// emitter.emit("login", { wrong: true }); // Lỗi compile!
```

---

## Bài 4: Builder Pattern Generic
**Độ khó: Khó**

Tạo type-safe Builder pattern:

1. `QueryBuilder<T>` nhận kiểu entity
2. `.select<K extends keyof T>(...fields: K[])` - trả về Builder với chỉ các field được chọn
3. `.where(field: keyof T, op: "=" | ">" | "<" | "like", value: any)` - thêm điều kiện
4. `.orderBy(field: keyof T, direction: "ASC" | "DESC")` - sắp xếp
5. `.limit(n: number)` và `.offset(n: number)` - phân trang
6. `.build()` - trả về object mô tả query

**Test:**
```typescript
interface Product {
    id: number;
    name: string;
    price: number;
    category: string;
}

const query = new QueryBuilder<Product>()
    .select("id", "name", "price")
    .where("price", ">", 100)
    .where("category", "=", "electronics")
    .orderBy("price", "DESC")
    .limit(10)
    .build();
```

---

## Bài 5: State Machine với Types
**Độ khó: Khó**

Implement type-safe finite state machine:

1. Định nghĩa các trạng thái và transitions bằng types
2. Máy chỉ cho phép chuyển trạng thái hợp lệ (compile-time check)
3. Mỗi trạng thái có thể có dữ liệu kèm theo (context)

**Ví dụ: Order state machine:**
- `draft` -> `pending` (action: submit)
- `pending` -> `confirmed` | `cancelled` (action: confirm | cancel)
- `confirmed` -> `shipped` (action: ship)
- `shipped` -> `delivered` (action: deliver)
- `cancelled` là trạng thái cuối (không chuyển tiếp được)

Yêu cầu: Nếu gọi `machine.transition("cancelled", "confirm")` phải báo lỗi TẠI COMPILE TIME, không phải runtime.
