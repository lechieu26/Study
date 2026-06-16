# TypeScript - Bai Tap

## Bài 1: Type-safe Collection
**Độ khó: Trung bình**

Tao mot class `TypedCollection<T>` voi cac tinh nang:

1. `add(item: T)` - them phan tu
2. `remove(index: number)` - xoa theo index
3. `get(index: number): T` - lay theo index
4. `find(predicate: (item: T) => boolean): T | undefined`
5. `filter(predicate: (item: T) => boolean): T[]`
6. `map<U>(fn: (item: T) => U): TypedCollection<U>` - tra ve collection moi
7. `toArray(): readonly T[]` - tra ve mang readonly
8. Implement `[Symbol.iterator]` de co the dung for...of
9. Thuoc tinh `size` (getter)

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

Thiet ke type system cho API client:

1. Dinh nghia `ApiResponse<T>` voi 2 trang thai: Success va Error (discriminated union)
2. Tao type `HttpMethod = "GET" | "POST" | "PUT" | "DELETE"`
3. Dinh nghia `RequestConfig<T>` voi: url, method, body (chi co khi POST/PUT), headers
4. Tao ham `fetchData<T>(config: RequestConfig<T>): Promise<ApiResponse<T>>`
5. Tao utility type `ExtractData<R>` lay kieu data tu ApiResponse
6. Tao type-safe error handler dung type guards

**Yeu cau:** Body type phai tu dong la `never` khi method la GET hoac DELETE.

---

## Bài 3: Event Emitter Generic
**Độ khó: Trung bình**

Tao type-safe EventEmitter:

1. Nhan mot type map `Events` dinh nghia ten event va kieu payload
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
    // payload duoc infer la { userId: string; timestamp: Date }
    console.log(payload.userId);
});
emitter.emit("login", { userId: "123", timestamp: new Date() }); // OK
// emitter.emit("login", { wrong: true }); // Loi compile!
```

---

## Bài 4: Builder Pattern Generic
**Độ khó: Khó**

Tao type-safe Builder pattern:

1. `QueryBuilder<T>` nhan kieu entity
2. `.select<K extends keyof T>(...fields: K[])` - tra ve Builder voi chi cac field duoc chon
3. `.where(field: keyof T, op: "=" | ">" | "<" | "like", value: any)` - them dieu kien
4. `.orderBy(field: keyof T, direction: "ASC" | "DESC")` - sap xep
5. `.limit(n: number)` va `.offset(n: number)` - phan trang
6. `.build()` - tra ve object mo ta query

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

## Bài 5: State Machine voi Types
**Độ khó: Khó**

Implement type-safe finite state machine:

1. Dinh nghia cac trang thai va transitions bang types
2. May chi cho phep chuyen trang thai hop le (compile-time check)
3. Moi trang thai co the co du lieu kem theo (context)

**Vi du: Order state machine:**
- `draft` -> `pending` (action: submit)
- `pending` -> `confirmed` | `cancelled` (action: confirm | cancel)
- `confirmed` -> `shipped` (action: ship)
- `shipped` -> `delivered` (action: deliver)
- `cancelled` la trang thai cuoi (khong chuyen tiep duoc)

Yeu cau: Neu goi `machine.transition("cancelled", "confirm")` phai bao loi TAI COMPILE TIME, khong phai runtime.
