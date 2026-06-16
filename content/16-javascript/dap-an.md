# JavaScript - Đáp Án Bài Tập

## Bài 1: Array Utilities

```javascript
// myFilter - lọc phần tử thỏa điều kiện
function myFilter(arr, callback) {
    const result = [];
    for (let i = 0; i < arr.length; i++) {
        if (callback(arr[i], i, arr)) {
            result.push(arr[i]);
        }
    }
    return result;
}

// myMap - biến đổi mỗi phần tử
function myMap(arr, callback) {
    const result = [];
    for (let i = 0; i < arr.length; i++) {
        result.push(callback(arr[i], i, arr));
    }
    return result;
}

// myReduce - gom mảng thành 1 giá trị
function myReduce(arr, callback, initialValue) {
    let accumulator = initialValue;
    let startIndex = 0;

    if (accumulator === undefined) {
        if (arr.length === 0) {
            throw new TypeError("Reduce of empty array with no initial value");
        }
        accumulator = arr[0];
        startIndex = 1;
    }

    for (let i = startIndex; i < arr.length; i++) {
        accumulator = callback(accumulator, arr[i], i, arr);
    }
    return accumulator;
}

// myFlat - làm phẳng mảng lồng nhau
function myFlat(arr, depth = 1) {
    const result = [];
    for (const item of arr) {
        if (Array.isArray(item) && depth > 0) {
            result.push(...myFlat(item, depth - 1));
        } else {
            result.push(item);
        }
    }
    return result;
}

// Test
const numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

console.log(myFilter(numbers, n => n % 2 === 0));
// [2, 4, 6, 8, 10]

console.log(myMap(numbers, n => n * 3));
// [3, 6, 9, 12, 15, 18, 21, 24, 27, 30]

console.log(myReduce(numbers, (acc, n) => acc + n, 0));
// 55

console.log(myFlat([1, [2, [3, [4]]]], 2));
// [1, 2, 3, [4]]

console.log(myFlat([1, [2, [3, [4]]]], Infinity));
// [1, 2, 3, 4]
```

**Giải thích:**
- `myFilter`: Duyệt mảng, gọi callback cho từng phần tử, thêm vào kết quả nếu callback trả về `true`.
- `myMap`: Duyệt mảng, gọi callback cho từng phần tử, thêm kết quả callback vào mảng mới.
- `myReduce`: Sử dụng accumulator, gọi callback với (accumulator, currentValue) cho từng phần tử. Xử lý trường hợp không có initialValue.
- `myFlat`: Đệ quy - nếu phần tử là array và depth > 0, gọi đệ quy với depth - 1. Nếu không, thêm trực tiếp vào kết quả.

---

## Bài 2: Closure - Counter Factory

```javascript
function createCounter(options = {}) {
    const {
        min = -Infinity,
        max = Infinity,
        step = 1,
        initialValue = 0
    } = options;

    let value = Math.min(Math.max(initialValue, min), max);
    const history = [value];
    const undoStack = [];

    function clamp(val) {
        return Math.min(Math.max(val, min), max);
    }

    function saveAndUpdate(newValue) {
        undoStack.push(value);
        value = clamp(newValue);
        history.push(value);
        return value;
    }

    return {
        increment() {
            return saveAndUpdate(value + step);
        },

        decrement() {
            return saveAndUpdate(value - step);
        },

        reset() {
            return saveAndUpdate(initialValue);
        },

        getValue() {
            return value;
        },

        getHistory() {
            return [...history];
        },

        undo() {
            if (undoStack.length === 0) return value;
            value = undoStack.pop();
            history.push(value);
            return value;
        }
    };
}

// Test
const counter = createCounter({ min: 0, max: 10, step: 2, initialValue: 4 });
console.log(counter.getValue());   // 4
console.log(counter.increment());  // 6
console.log(counter.increment());  // 8
console.log(counter.increment());  // 10
console.log(counter.increment());  // 10 (max)
console.log(counter.decrement());  // 8
console.log(counter.undo());       // 10
console.log(counter.getHistory()); // [4, 6, 8, 10, 10, 8, 10]
console.log(counter.reset());      // 4
```

**Giải thích:**
- Closure giữ các biến `value`, `history`, `undoStack` private - không truy cập được từ bên ngoài.
- `clamp()` đảm bảo giá trị luôn nằm trong khoảng [min, max].
- `saveAndUpdate()` lưu giá trị cũ vào undoStack trước khi cập nhật.
- `undo()` lấy giá trị từ undoStack và thêm vào history.

---

## Bài 3: Deep Clone

```javascript
function deepClone(obj, seen = new WeakMap()) {
    // Xử lý primitive và null
    if (obj === null || typeof obj !== "object") {
        return obj;
    }

    // Xử lý circular reference
    if (seen.has(obj)) {
        return seen.get(obj);
    }

    // Xử lý Date
    if (obj instanceof Date) {
        return new Date(obj.getTime());
    }

    // Xử lý RegExp
    if (obj instanceof RegExp) {
        return new RegExp(obj.source, obj.flags);
    }

    // Xử lý Map
    if (obj instanceof Map) {
        const mapClone = new Map();
        seen.set(obj, mapClone);
        for (const [key, value] of obj) {
            mapClone.set(deepClone(key, seen), deepClone(value, seen));
        }
        return mapClone;
    }

    // Xử lý Set
    if (obj instanceof Set) {
        const setClone = new Set();
        seen.set(obj, setClone);
        for (const value of obj) {
            setClone.add(deepClone(value, seen));
        }
        return setClone;
    }

    // Xử lý Array
    if (Array.isArray(obj)) {
        const arrClone = [];
        seen.set(obj, arrClone);
        for (let i = 0; i < obj.length; i++) {
            arrClone[i] = deepClone(obj[i], seen);
        }
        return arrClone;
    }

    // Xử lý Object
    const objClone = Object.create(Object.getPrototypeOf(obj));
    seen.set(obj, objClone);

    for (const key of Reflect.ownKeys(obj)) {
        const descriptor = Object.getOwnPropertyDescriptor(obj, key);
        if (descriptor.value !== undefined) {
            descriptor.value = deepClone(descriptor.value, seen);
        }
        Object.defineProperty(objClone, key, descriptor);
    }

    return objClone;
}

// Test
const original = {
    name: "An",
    scores: [90, 85, 95],
    info: { age: 25, address: { city: "HCM" } },
    createdAt: new Date("2024-01-15"),
    pattern: /test/gi,
    tags: new Set(["js", "web"]),
    meta: new Map([["key", "value"]])
};

// Test circular reference
original.self = original;

const cloned = deepClone(original);

cloned.info.address.city = "HN";
console.log(original.info.address.city); // "HCM"

cloned.scores.push(100);
console.log(original.scores.length); // 3

console.log(cloned.createdAt instanceof Date); // true
console.log(cloned.createdAt.getTime() === original.createdAt.getTime()); // true

console.log(cloned.pattern instanceof RegExp); // true
console.log(cloned.tags instanceof Set); // true
console.log(cloned.meta instanceof Map); // true

console.log(cloned.self === cloned); // true (circular ref được xử lý)
console.log(cloned.self !== original); // true
```

**Giải thích:**
- Sử dụng `WeakMap` (`seen`) để theo dõi các object đã clone, ngăn vòng lặp vô hạn khi gặp circular reference.
- Kiểm tra từng kiểu dữ liệu và xử lý phù hợp: Date -> copy timestamp, RegExp -> copy source và flags.
- Sử dụng `Reflect.ownKeys()` để lấy tất cả keys (kể cả Symbol).
- `Object.create(Object.getPrototypeOf(obj))` giữ nguyên prototype chain.

---

## Bài 4: Promise Pool

```javascript
async function promisePool(tasks, poolSize) {
    const results = new Array(tasks.length);
    let currentIndex = 0;

    async function runNext() {
        while (currentIndex < tasks.length) {
            const index = currentIndex++;
            try {
                const value = await tasks[index]();
                results[index] = { status: "fulfilled", value };
            } catch (reason) {
                results[index] = { status: "rejected", reason };
            }
        }
    }

    // Tạo poolSize workers chạy đồng thời
    const workers = Array.from(
        { length: Math.min(poolSize, tasks.length) },
        () => runNext()
    );

    await Promise.all(workers);
    return results;
}

// Helper function
function delay(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

// Test
async function test() {
    const tasks = [
        () => delay(100).then(() => "A"),
        () => delay(200).then(() => "B"),
        () => delay(50).then(() => { throw new Error("Failed"); }),
        () => delay(150).then(() => "D"),
        () => delay(80).then(() => "E")
    ];

    console.time("pool");
    const results = await promisePool(tasks, 2);
    console.timeEnd("pool");

    console.log(results);
    // [
    //   { status: "fulfilled", value: "A" },
    //   { status: "fulfilled", value: "B" },
    //   { status: "rejected", reason: Error("Failed") },
    //   { status: "fulfilled", value: "D" },
    //   { status: "fulfilled", value: "E" }
    // ]
}

test();
```

**Giải thích:**
- Tạo `poolSize` workers chạy đồng thời. Mỗi worker lấy task tiếp theo từ mảng tasks khi hoàn thành task hiện tại.
- `currentIndex` đảm bảo mỗi task chỉ được lấy 1 lần (JavaScript là single-threaded nên không cần lock).
- Kết quả lưu theo index gốc để đảm bảo thứ tự.
- try/catch xử lý lỗi của từng task riêng, không ảnh hưởng task khác.

---

## Bài 5: Event Emitter

```javascript
class EventEmitter {
    #listeners = {};
    #errorHandler = null;

    on(event, callback) {
        if (!this.#listeners[event]) {
            this.#listeners[event] = [];
        }
        this.#listeners[event].push(callback);

        // Trả về hàm unsubscribe
        return () => this.off(event, callback);
    }

    off(event, callback) {
        if (!this.#listeners[event]) return;
        this.#listeners[event] = this.#listeners[event].filter(
            cb => cb !== callback
        );
        if (this.#listeners[event].length === 0) {
            delete this.#listeners[event];
        }
    }

    emit(event, ...args) {
        // Gọi các listener của event cụ thể
        const listeners = this.#listeners[event] || [];
        for (const listener of [...listeners]) {
            try {
                listener(...args);
            } catch (error) {
                if (this.#errorHandler) {
                    this.#errorHandler(error, event);
                } else {
                    console.error(`Error in listener for "${event}":`, error);
                }
            }
        }

        // Gọi wildcard listeners
        const wildcardListeners = this.#listeners["*"] || [];
        for (const listener of [...wildcardListeners]) {
            try {
                listener(event, ...args);
            } catch (error) {
                if (this.#errorHandler) {
                    this.#errorHandler(error, "*");
                }
            }
        }
    }

    once(event, callback) {
        const wrapper = (...args) => {
            this.off(event, wrapper);
            callback(...args);
        };
        wrapper._original = callback; // Lưu ref để có thể off bằng callback gốc
        return this.on(event, wrapper);
    }

    listenerCount(event) {
        return (this.#listeners[event] || []).length;
    }

    removeAllListeners(event) {
        if (event) {
            delete this.#listeners[event];
        } else {
            this.#listeners = {};
        }
    }

    onError(callback) {
        this.#errorHandler = callback;
    }

    eventNames() {
        return Object.keys(this.#listeners);
    }
}

// Test
const emitter = new EventEmitter();

// Error handler
emitter.onError((error, event) => {
    console.log(`Error in "${event}": ${error.message}`);
});

// Wildcard - bắt tất cả event
emitter.on("*", (event, ...args) => {
    console.log(`[${event}]`, ...args);
});

// Once - chỉ chạy 1 lần
emitter.once("connect", () => console.log("Connected!"));

// Regular listener với unsubscribe
const unsub = emitter.on("data", (msg) => console.log("Data:", msg));

emitter.emit("connect");
// "Connected!" và "[connect]"

emitter.emit("connect");
// Chỉ "[connect]" (once đã xóa)

emitter.emit("data", "Hello");
// "Data: Hello" và "[data] Hello"

console.log(emitter.listenerCount("data")); // 1
unsub(); // Gỡ bỏ
console.log(emitter.listenerCount("data")); // 0
```

**Giải thích:**
- Sử dụng private field `#listeners` để lưu trữ các event handlers theo event name.
- `on()` trả về hàm unsubscribe (closure).
- `once()` bọc callback trong wrapper, wrapper tự gỡ bỏ chính nó sau khi chạy.
- `emit()` spread listener array (`[...listeners]`) để tránh lỗi khi listener tự gỡ bỏ chính nó trong quá trình emit.
- Wildcard `*` nhận event name làm tham số đầu tiên.
- Error handling bảo vệ các listener khác khi 1 listener throw error.

---

## Bài 6: Debounce và Throttle

```javascript
function debounce(fn, delay, options = {}) {
    const { leading = false, trailing = true, maxWait } = options;
    let timeoutId = null;
    let maxWaitId = null;
    let lastArgs = null;
    let lastThis = null;
    let lastCallTime = 0;

    function invoke() {
        const args = lastArgs;
        const thisArg = lastThis;
        lastArgs = null;
        lastThis = null;
        clearTimeout(maxWaitId);
        maxWaitId = null;
        fn.apply(thisArg, args);
    }

    function debounced(...args) {
        lastArgs = args;
        lastThis = this;
        const now = Date.now();

        const isFirstCall = timeoutId === null;
        clearTimeout(timeoutId);

        // Leading edge
        if (leading && isFirstCall) {
            invoke();
        }

        // Trailing edge
        if (trailing) {
            timeoutId = setTimeout(() => {
                timeoutId = null;
                if (!leading || lastArgs) {
                    invoke();
                }
            }, delay);
        }

        // Max wait
        if (maxWait !== undefined && maxWaitId === null) {
            maxWaitId = setTimeout(() => {
                clearTimeout(timeoutId);
                timeoutId = null;
                maxWaitId = null;
                if (lastArgs) invoke();
            }, maxWait);
        }

        lastCallTime = now;
    }

    debounced.cancel = function() {
        clearTimeout(timeoutId);
        clearTimeout(maxWaitId);
        timeoutId = null;
        maxWaitId = null;
        lastArgs = null;
        lastThis = null;
    };

    debounced.flush = function() {
        if (lastArgs) {
            clearTimeout(timeoutId);
            clearTimeout(maxWaitId);
            timeoutId = null;
            maxWaitId = null;
            invoke();
        }
    };

    return debounced;
}

function throttle(fn, interval) {
    let lastTime = 0;
    let timeoutId = null;
    let lastArgs = null;
    let lastThis = null;

    function throttled(...args) {
        const now = Date.now();
        const remaining = interval - (now - lastTime);

        lastArgs = args;
        lastThis = this;

        if (remaining <= 0) {
            clearTimeout(timeoutId);
            timeoutId = null;
            lastTime = now;
            fn.apply(this, args);
            lastArgs = null;
            lastThis = null;
        } else if (!timeoutId) {
            timeoutId = setTimeout(() => {
                lastTime = Date.now();
                timeoutId = null;
                fn.apply(lastThis, lastArgs);
                lastArgs = null;
                lastThis = null;
            }, remaining);
        }
    }

    throttled.cancel = function() {
        clearTimeout(timeoutId);
        timeoutId = null;
        lastArgs = null;
        lastThis = null;
        lastTime = 0;
    };

    return throttled;
}

// Test debounce
let callCount = 0;
const debounced = debounce(() => {
    callCount++;
    console.log(`Called: ${callCount}`);
}, 100);

debounced(); debounced(); debounced();
// Chỉ gọi 1 lần sau 100ms: "Called: 1"

// Test throttle
const throttled = throttle((x) => console.log("Throttled:", x), 200);
throttled(1); // Gọi ngay
throttled(2); // Bỏ qua
throttled(3); // Bỏ qua, nhưng sẽ gọi sau 200ms với args cuối
```

**Giải thích:**
- **Debounce**: Mỗi lần gọi, hủy timer cũ và tạo timer mới. Hàm chỉ thực sự chạy khi ngừng gọi trong `delay` ms. `leading` cho phép gọi ngay lần đầu. `maxWait` đảm bảo hàm được gọi ít nhất 1 lần trong khoảng thời gian đó.
- **Throttle**: Đảm bảo hàm chỉ chạy 1 lần trong mỗi `interval`. Nếu gọi nhiều lần, lần cuối sẽ được gọi khi hết interval (trailing call).

---

## Bài 7: Mini Reactive System

```javascript
let activeEffect = null;
const targetMap = new WeakMap();

function reactive(target) {
    const handler = {
        get(obj, prop, receiver) {
            track(obj, prop);
            const result = Reflect.get(obj, prop, receiver);
            if (typeof result === "object" && result !== null) {
                return reactive(result); // Deep reactive
            }
            return result;
        },
        set(obj, prop, value, receiver) {
            const oldValue = obj[prop];
            const result = Reflect.set(obj, prop, value, receiver);
            if (oldValue !== value) {
                trigger(obj, prop);
            }
            return result;
        }
    };
    return new Proxy(target, handler);
}

function track(target, prop) {
    if (!activeEffect) return;
    let depsMap = targetMap.get(target);
    if (!depsMap) {
        depsMap = new Map();
        targetMap.set(target, depsMap);
    }
    let deps = depsMap.get(prop);
    if (!deps) {
        deps = new Set();
        depsMap.set(prop, deps);
    }
    deps.add(activeEffect);
}

function trigger(target, prop) {
    const depsMap = targetMap.get(target);
    if (!depsMap) return;
    const deps = depsMap.get(prop);
    if (!deps) return;
    deps.forEach(effect => effect());
}

function effect(fn) {
    const effectFn = () => {
        activeEffect = effectFn;
        fn();
        activeEffect = null;
    };
    effectFn(); // Chạy lần đầu để thu thập dependencies
    return effectFn;
}

function computed(fn) {
    let cachedValue;
    let dirty = true;

    const effectFn = effect(() => {
        // Khi dependency thay đổi, đánh dấu dirty
        dirty = true;
    });

    // Override effect để không chạy fn ngay
    // Mà chỉ đánh dấu dirty
    activeEffect = null;

    return {
        get value() {
            if (dirty) {
                activeEffect = effectFn;
                cachedValue = fn();
                activeEffect = null;
                dirty = false;
            }
            return cachedValue;
        }
    };
}

// Cách implement computed đơn giản hơn (dễ hiểu):
function computedSimple(fn) {
    let cache;
    let dirty = true;

    // Tạo effect để theo dõi dependencies
    const runner = () => {
        dirty = true;
    };

    // Thu thập dependencies lần đầu
    activeEffect = runner;
    cache = fn();
    activeEffect = null;
    dirty = false;

    return {
        get value() {
            if (dirty) {
                activeEffect = runner;
                cache = fn();
                activeEffect = null;
                dirty = false;
            }
            return cache;
        }
    };
}

// Test
const state = reactive({ count: 0, name: "An" });

console.log("--- Effect demo ---");
effect(() => {
    console.log(`Count là: ${state.count}`);
});
// In ngay: "Count là: 0"

state.count = 5;
// Tự động in: "Count là: 5"

state.count = 5;
// KHÔNG in (giá trị không thay đổi)

state.name = "Bình";
// KHÔNG in (effect không phụ thuộc name)

console.log("--- Computed demo ---");
const doubled = computedSimple(() => state.count * 2);
console.log(doubled.value); // 10
state.count = 10;
console.log(doubled.value); // 20
```

**Giải thích:**
- **reactive()**: Sử dụng Proxy để chặn `get` (theo dõi dependencies) và `set` (kích hoạt re-render).
- **track()**: Khi đọc thuộc tính trong effect, lưu lại rằng effect này phụ thuộc vào thuộc tính đó. Sử dụng `WeakMap -> Map -> Set` để lưu dependency graph.
- **trigger()**: Khi thay đổi thuộc tính, tìm tất cả effects phụ thuộc và chạy lại chúng.
- **effect()**: Chạy hàm lần đầu để thu thập dependencies (thiết lập `activeEffect` trước khi chạy).
- **computed()**: Chỉ tính lại khi dependency thay đổi (lazy evaluation với cache).

---

## Bài 8: JSON Path Query

```javascript
function jsonQuery(obj, path) {
    if (!obj || !path) return undefined;

    const tokens = tokenize(path);
    return evaluate(obj, tokens);
}

function tokenize(path) {
    const tokens = [];
    const regex = /(\w+)|\[(\d+)\]|\[\*\]|\[\?\((.+?)\)\]/g;
    let match;

    // Tách path theo dot và bracket
    const parts = path.split(/\.(?![^\[]*\])/);

    for (const part of parts) {
        const bracketMatch = part.match(/^(\w+)((?:\[.+?\])*)$/);
        if (bracketMatch) {
            if (bracketMatch[1]) {
                tokens.push({ type: "prop", value: bracketMatch[1] });
            }
            if (bracketMatch[2]) {
                const brackets = bracketMatch[2].match(/\[.+?\]/g) || [];
                for (const bracket of brackets) {
                    if (bracket === "[*]") {
                        tokens.push({ type: "wildcard" });
                    } else if (bracket.startsWith("[?(")) {
                        const expr = bracket.slice(3, -2);
                        tokens.push({ type: "filter", value: expr });
                    } else {
                        const index = parseInt(bracket.slice(1, -1));
                        tokens.push({ type: "index", value: index });
                    }
                }
            }
        } else {
            tokens.push({ type: "prop", value: part });
        }
    }

    return tokens;
}

function evaluate(data, tokens) {
    let current = data;

    for (let i = 0; i < tokens.length; i++) {
        if (current === undefined || current === null) return undefined;

        const token = tokens[i];

        switch (token.type) {
            case "prop":
                current = current[token.value];
                break;

            case "index":
                if (!Array.isArray(current)) return undefined;
                current = current[token.value];
                break;

            case "wildcard": {
                if (!Array.isArray(current)) return undefined;
                const remainingTokens = tokens.slice(i + 1);
                if (remainingTokens.length === 0) return [...current];
                return current
                    .map(item => evaluate(item, remainingTokens))
                    .filter(v => v !== undefined);
            }

            case "filter": {
                if (!Array.isArray(current)) return undefined;
                const expr = token.value;
                const filtered = current.filter(item => {
                    const evaluateExpr = expr.replace(/@\.(\w+)/g, (_, prop) => {
                        const val = item[prop];
                        return typeof val === "string" ? `"${val}"` : val;
                    });
                    try {
                        return new Function(`return ${evaluateExpr}`)();
                    } catch {
                        return false;
                    }
                });
                const remainingTokens = tokens.slice(i + 1);
                if (remainingTokens.length === 0) return filtered;
                return filtered
                    .map(item => evaluate(item, remainingTokens))
                    .filter(v => v !== undefined);
            }
        }
    }

    return current;
}

// Test
const data = {
    store: {
        books: [
            { title: "JS Guide", price: 100, author: "An" },
            { title: "CSS Tricks", price: 80, author: "Bình" },
            { title: "Node.js", price: 120, author: "An" }
        ],
        location: { city: "HCM", country: "VN" }
    }
};

console.log(jsonQuery(data, "store.location.city"));
// "HCM"

console.log(jsonQuery(data, "store.books[0].title"));
// "JS Guide"

console.log(jsonQuery(data, "store.books[*].title"));
// ["JS Guide", "CSS Tricks", "Node.js"]

console.log(jsonQuery(data, 'store.books[?(@.price > 100)].title'));
// ["Node.js"]

console.log(jsonQuery(data, 'store.books[?(@.author === "An")].title'));
// ["JS Guide", "Node.js"]

console.log(jsonQuery(data, "store.noExist.deep"));
// undefined
```

**Giải thích:**
- **tokenize()**: Tách path string thành mảng tokens (property, index, wildcard, filter).
- **evaluate()**: Duyệt qua từng token và truy vấn dữ liệu:
  - `prop`: Truy cập thuộc tính object.
  - `index`: Truy cập phần tử array theo chỉ số.
  - `wildcard [*]`: Map qua tất cả phần tử, áp dụng tokens còn lại cho mỗi phần tử.
  - `filter [?()]`: Lọc phần tử theo điều kiện, thay `@.prop` bằng giá trị thực tế.
- Sử dụng đệ quy cho wildcard và filter để xử lý các tokens còn lại.

---

## Bài 9: Currying và Partial Application

```javascript
// Curry - chuyển hàm nhiều tham số thành chuỗi hàm
function curry(fn) {
    const arity = fn.length;

    return function curried(...args) {
        if (args.length >= arity) {
            return fn.apply(this, args);
        }
        return function(...moreArgs) {
            return curried.apply(this, [...args, ...moreArgs]);
        };
    };
}

// Partial - cố định một số tham số
function partial(fn, ...partialArgs) {
    return function(...remainingArgs) {
        return fn.apply(this, [...partialArgs, ...remainingArgs]);
    };
}

// Compose - chạy từ phải sang trái
function compose(...fns) {
    if (fns.length === 0) return (x) => x;
    if (fns.length === 1) return fns[0];

    return function(x) {
        return fns.reduceRight((acc, fn) => fn(acc), x);
    };
}

// Pipe - chạy từ trái sang phải
function pipe(...fns) {
    if (fns.length === 0) return (x) => x;
    if (fns.length === 1) return fns[0];

    return function(x) {
        return fns.reduce((acc, fn) => fn(acc), x);
    };
}

// Test Curry
function add(a, b, c) { return a + b + c; }
const curriedAdd = curry(add);

console.log(curriedAdd(1)(2)(3));   // 6
console.log(curriedAdd(1, 2)(3));   // 6
console.log(curriedAdd(1)(2, 3));   // 6
console.log(curriedAdd(1, 2, 3));   // 6

// Test Partial
const add10 = partial(add, 10);
console.log(add10(20, 30));         // 60

const add10and20 = partial(add, 10, 20);
console.log(add10and20(30));        // 60

// Test Compose và Pipe
const double = x => x * 2;
const addOne = x => x + 1;
const square = x => x * x;

const composed = compose(square, addOne, double);
console.log(composed(3));           // 49: double(3)=6, addOne(6)=7, square(7)=49

const piped = pipe(double, addOne, square);
console.log(piped(3));              // 49: double(3)=6, addOne(6)=7, square(7)=49
```

**Giải thích:**
- **curry()**: So sánh số lượng args đã nhận với `fn.length` (số tham số của hàm gốc). Nếu đủ -> gọi hàm. Nếu chưa đủ -> trả về hàm mới chờ thêm args.
- **partial()**: Dùng closure để lưu `partialArgs`, khi gọi hàm mới -> gộp partialArgs và remainingArgs.
- **compose()**: `reduceRight` chạy các hàm từ phải sang trái, truyền output của hàm trước làm input của hàm sau.
- **pipe()**: `reduce` chạy các hàm từ trái sang phải.

---

## Bài 10: LRU Cache

```javascript
class LRUCache {
    #capacity;
    #cache;

    constructor(capacity) {
        if (capacity <= 0) throw new Error("Capacity phải > 0");
        this.#capacity = capacity;
        this.#cache = new Map(); // Map giữ thứ tự chèn
    }

    get(key) {
        if (!this.#cache.has(key)) return -1;

        // Di chuyển key lên cuối (recently used)
        const value = this.#cache.get(key);
        this.#cache.delete(key);
        this.#cache.set(key, value);
        return value;
    }

    put(key, value) {
        // Nếu key đã tồn tại, xóa để cập nhật vị trí
        if (this.#cache.has(key)) {
            this.#cache.delete(key);
        } else if (this.#cache.size >= this.#capacity) {
            // Xóa item cũ nhất (đầu Map = least recently used)
            const oldestKey = this.#cache.keys().next().value;
            this.#cache.delete(oldestKey);
        }
        this.#cache.set(key, value);
    }

    delete(key) {
        return this.#cache.delete(key);
    }

    size() {
        return this.#cache.size;
    }

    clear() {
        this.#cache.clear();
    }

    // Bonus: lấy tất cả entries (từ mới nhất đến cũ nhất)
    entries() {
        return [...this.#cache.entries()].reverse();
    }

    // Bonus: kiểm tra key có tồn tại
    has(key) {
        return this.#cache.has(key);
    }
}

// Test
const cache = new LRUCache(3);

cache.put("a", 1);
cache.put("b", 2);
cache.put("c", 3);
console.log(cache.size()); // 3

console.log(cache.get("a")); // 1 (a trở thành recently used)

cache.put("d", 4); // Cache đầy -> xóa "b" (least recently used)
console.log(cache.get("b")); // -1 (đã bị xóa)

console.log(cache.get("a")); // 1
console.log(cache.get("c")); // 3
console.log(cache.get("d")); // 4
console.log(cache.size());   // 3

// Thứ tự từ mới nhất đến cũ nhất
console.log(cache.entries());
// [["d", 4], ["c", 3], ["a", 1]]

cache.put("e", 5); // Xóa "a" (least recently used sau khi d và c được truy cập)
console.log(cache.get("a")); // -1

cache.clear();
console.log(cache.size()); // 0
```

**Giải thích:**
- Sử dụng `Map` của JavaScript vì nó **giữ thứ tự chèn**. Phần tử được chèn đầu tiên sẽ ở đầu iterator.
- **get()**: Khi truy cập, xóa và thêm lại để chuyển key lên cuối (most recently used).
- **put()**: Nếu đã đầy và key chưa tồn tại, xóa phần tử đầu tiên của Map (least recently used) bằng `map.keys().next().value`.
- Độ phức tạp: O(1) cho cả `get` và `put` vì Map operations là O(1) trung bình.
- Sử dụng private fields (`#capacity`, `#cache`) để encapsulate.
