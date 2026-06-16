# TypeScript - Dap An Bai Tap

## Bai 1: Type-safe Collection

```typescript
class TypedCollection<T> implements Iterable<T> {
    private items: T[] = [];

    get size(): number {
        return this.items.length;
    }

    add(item: T): void {
        this.items.push(item);
    }

    remove(index: number): void {
        if (index < 0 || index >= this.items.length) {
            throw new RangeError(`Index ${index} out of bounds`);
        }
        this.items.splice(index, 1);
    }

    get(index: number): T {
        if (index < 0 || index >= this.items.length) {
            throw new RangeError(`Index ${index} out of bounds`);
        }
        return this.items[index];
    }

    find(predicate: (item: T) => boolean): T | undefined {
        return this.items.find(predicate);
    }

    filter(predicate: (item: T) => boolean): T[] {
        return this.items.filter(predicate);
    }

    map<U>(fn: (item: T) => U): TypedCollection<U> {
        const result = new TypedCollection<U>();
        for (const item of this.items) {
            result.add(fn(item));
        }
        return result;
    }

    toArray(): readonly T[] {
        return Object.freeze([...this.items]);
    }

    [Symbol.iterator](): Iterator<T> {
        let index = 0;
        const items = this.items;
        return {
            next(): IteratorResult<T> {
                if (index < items.length) {
                    return { value: items[index++], done: false };
                }
                return { value: undefined, done: true };
            }
        };
    }
}

// Test
const nums = new TypedCollection<number>();
nums.add(1);
nums.add(2);
nums.add(3);

console.log(nums.size);              // 3
console.log(nums.filter(n => n > 1)); // [2, 3]

const doubled = nums.map(n => n * 2);
console.log(doubled.toArray());       // [2, 4, 6]

for (const n of nums) {
    console.log(n); // 1, 2, 3
}
```

**Giai thich:**
- Generic `<T>` cho phep collection lam viec voi bat ky kieu nao
- `map<U>` tra ve `TypedCollection<U>` - kieu moi co the khac kieu goc
- `toArray()` tra ve `readonly T[]` de ngan viec thay doi tu ben ngoai
- `[Symbol.iterator]` implement Iterable protocol cho for...of

---

## Bai 2: API Response Handler

```typescript
// Discriminated Union cho Response
type ApiResponse<T> =
    | { success: true; data: T; status: number }
    | { success: false; error: string; status: number };

type HttpMethod = "GET" | "POST" | "PUT" | "DELETE";

// Conditional type: body chi khi POST/PUT
type RequestConfig<T> = {
    url: string;
    headers?: Record<string, string>;
} & (
    | { method: "GET" | "DELETE" }
    | { method: "POST" | "PUT"; body: T }
);

// Utility type lay data tu response
type ExtractData<R> = R extends ApiResponse<infer T>
    ? T extends { success: true } ? T : never
    : never;

// Type guard
function isSuccess<T>(response: ApiResponse<T>): response is ApiResponse<T> & { success: true } {
    return response.success;
}

// Fetch function
async function fetchData<T>(config: RequestConfig<T>): Promise<ApiResponse<T>> {
    try {
        const options: RequestInit = {
            method: config.method,
            headers: config.headers,
        };

        if ("body" in config) {
            options.body = JSON.stringify(config.body);
        }

        const res = await fetch(config.url, options);
        const data = await res.json();

        if (res.ok) {
            return { success: true, data: data as T, status: res.status };
        }
        return { success: false, error: data.message || "Error", status: res.status };
    } catch (err) {
        return { success: false, error: String(err), status: 0 };
    }
}

// Su dung
interface User { id: number; name: string; }

async function example() {
    // GET - khong co body
    const getResult = await fetchData<User>({
        method: "GET",
        url: "/api/users/1"
    });

    if (isSuccess(getResult)) {
        console.log(getResult.data.name); // Type-safe
    } else {
        console.error(getResult.error);    // Type-safe
    }

    // POST - co body
    const postResult = await fetchData<User>({
        method: "POST",
        url: "/api/users",
        body: { id: 0, name: "New User" } // Body bat buoc
    });

    // Loi compile: GET khong co body
    // fetchData<User>({ method: "GET", url: "/", body: {} });
}
```

---

## Bai 3: Event Emitter Generic

```typescript
class EventEmitter<Events extends Record<string, unknown>> {
    private listeners = new Map<keyof Events, Set<Function>>();

    on<K extends keyof Events>(
        event: K,
        listener: (payload: Events[K]) => void
    ): void {
        if (!this.listeners.has(event)) {
            this.listeners.set(event, new Set());
        }
        this.listeners.get(event)!.add(listener);
    }

    off<K extends keyof Events>(
        event: K,
        listener: (payload: Events[K]) => void
    ): void {
        this.listeners.get(event)?.delete(listener);
    }

    emit<K extends keyof Events>(event: K, payload: Events[K]): void {
        const handlers = this.listeners.get(event);
        if (handlers) {
            handlers.forEach(fn => fn(payload));
        }
    }

    once<K extends keyof Events>(
        event: K,
        listener: (payload: Events[K]) => void
    ): void {
        const wrapper = (payload: Events[K]) => {
            listener(payload);
            this.off(event, wrapper);
        };
        this.on(event, wrapper);
    }
}

// Test
interface AppEvents {
    login: { userId: string; timestamp: Date };
    logout: { userId: string };
    error: { code: number; message: string };
}

const emitter = new EventEmitter<AppEvents>();

emitter.on("login", (payload) => {
    // payload tu dong la { userId: string; timestamp: Date }
    console.log(`User ${payload.userId} logged in at ${payload.timestamp}`);
});

emitter.on("error", (payload) => {
    // payload tu dong la { code: number; message: string }
    console.error(`Error ${payload.code}: ${payload.message}`);
});

emitter.emit("login", { userId: "123", timestamp: new Date() }); // OK
// emitter.emit("login", { wrong: true }); // Compile error!
// emitter.emit("unknown", {}); // Compile error! "unknown" khong phai key
```

---

## Bai 4: Builder Pattern Generic

```typescript
interface WhereClause<T> {
    field: keyof T;
    op: "=" | ">" | "<" | "like";
    value: T[keyof T];
}

interface OrderClause<T> {
    field: keyof T;
    direction: "ASC" | "DESC";
}

interface QueryResult<T, K extends keyof T = keyof T> {
    selectedFields: K[];
    conditions: WhereClause<T>[];
    ordering: OrderClause<T>[];
    limitValue: number | null;
    offsetValue: number | null;
}

class QueryBuilder<T, K extends keyof T = keyof T> {
    private _select: K[] = [];
    private _where: WhereClause<T>[] = [];
    private _orderBy: OrderClause<T>[] = [];
    private _limit: number | null = null;
    private _offset: number | null = null;

    select<F extends keyof T>(...fields: F[]): QueryBuilder<T, F> {
        const builder = new QueryBuilder<T, F>();
        builder._select = fields;
        builder._where = this._where as WhereClause<T>[];
        builder._orderBy = this._orderBy as OrderClause<T>[];
        builder._limit = this._limit;
        builder._offset = this._offset;
        return builder;
    }

    where<F extends keyof T>(
        field: F,
        op: "=" | ">" | "<" | "like",
        value: T[F]
    ): this {
        this._where.push({ field, op, value: value as T[keyof T] });
        return this;
    }

    orderBy(field: keyof T, direction: "ASC" | "DESC"): this {
        this._orderBy.push({ field, direction });
        return this;
    }

    limit(n: number): this {
        this._limit = n;
        return this;
    }

    offset(n: number): this {
        this._offset = n;
        return this;
    }

    build(): QueryResult<T, K> {
        return {
            selectedFields: this._select,
            conditions: this._where,
            ordering: this._orderBy,
            limitValue: this._limit,
            offsetValue: this._offset
        };
    }
}

// Test
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

console.log(query);
// { selectedFields: ["id","name","price"], conditions: [...], ordering: [...], ... }
```

---

## Bai 5: State Machine voi Types

```typescript
// Dinh nghia trang thai va transitions
interface OrderStates {
    draft: { orderId: string };
    pending: { orderId: string; submittedAt: Date };
    confirmed: { orderId: string; confirmedAt: Date };
    cancelled: { orderId: string; reason: string };
    shipped: { orderId: string; trackingId: string };
    delivered: { orderId: string; deliveredAt: Date };
}

// Dinh nghia transitions hop le
interface OrderTransitions {
    draft: { submit: "pending" };
    pending: { confirm: "confirmed"; cancel: "cancelled" };
    confirmed: { ship: "shipped" };
    shipped: { deliver: "delivered" };
    cancelled: {};   // Khong co transition nao
    delivered: {};   // Khong co transition nao
}

// Generic State Machine
type StateData<
    States extends Record<string, unknown>,
    S extends keyof States
> = {
    state: S;
    context: States[S];
};

class StateMachine<
    States extends Record<string, unknown>,
    Transitions extends Record<keyof States, Record<string, keyof States>>
> {
    private current: StateData<States, keyof States>;

    constructor(
        initialState: keyof States,
        initialContext: States[keyof States]
    ) {
        this.current = {
            state: initialState,
            context: initialContext
        };
    }

    getState(): keyof States {
        return this.current.state;
    }

    getContext(): States[keyof States] {
        return this.current.context;
    }

    transition<
        S extends keyof Transitions,
        A extends keyof Transitions[S]
    >(
        _fromState: S,
        action: A,
        newContext: States[Transitions[S][A] & keyof States]
    ): void {
        if (this.current.state !== _fromState) {
            throw new Error(
                `Expected state "${String(_fromState)}", ` +
                `but current is "${String(this.current.state)}"`
            );
        }
        const nextState = this.getNextState(_fromState, action);
        this.current = { state: nextState, context: newContext };
    }

    private getNextState<
        S extends keyof Transitions,
        A extends keyof Transitions[S]
    >(state: S, _action: A): Transitions[S][A] & keyof States {
        return undefined as unknown as Transitions[S][A] & keyof States;
    }
}

// Su dung
const orderMachine = new StateMachine<OrderStates, OrderTransitions>(
    "draft",
    { orderId: "ORD-001" }
);

// Chuyen trang thai hop le - OK
orderMachine.transition("draft", "submit", {
    orderId: "ORD-001",
    submittedAt: new Date()
});

// Loi COMPILE TIME:
// orderMachine.transition("cancelled", "confirm", { ... });
// "confirm" khong ton tai trong OrderTransitions["cancelled"]
```

**Giai thich:**
- Discriminated union `OrderStates` dinh nghia data cho moi trang thai
- `OrderTransitions` map trang thai -> action -> trang thai moi
- TypeScript en force tai compile time: chi cho phep cac transitions da dinh nghia
- Goi `transition("cancelled", "confirm")` se bao loi vi "confirm" khong phai key cua `OrderTransitions["cancelled"]` (la `{}`)
