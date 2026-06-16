# JavaScript - Dap An Bai Tap

## Bai 1: Array Utilities

```javascript
// myFilter - loc phan tu thoa dieu kien
function myFilter(arr, callback) {
    const result = [];
    for (let i = 0; i < arr.length; i++) {
        if (callback(arr[i], i, arr)) {
            result.push(arr[i]);
        }
    }
    return result;
}

// myMap - bien doi moi phan tu
function myMap(arr, callback) {
    const result = [];
    for (let i = 0; i < arr.length; i++) {
        result.push(callback(arr[i], i, arr));
    }
    return result;
}

// myReduce - gom mang thanh 1 gia tri
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

// myFlat - lam phang mang long nhau
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

**Giai thich:**
- `myFilter`: Duyet mang, goi callback cho tung phan tu, them vao ket qua neu callback tra ve `true`.
- `myMap`: Duyet mang, goi callback cho tung phan tu, them ket qua callback vao mang moi.
- `myReduce`: Su dung accumulator, goi callback voi (accumulator, currentValue) cho tung phan tu. Xu ly truong hop khong co initialValue.
- `myFlat`: De quy - neu phan tu la array va depth > 0, goi de quy voi depth - 1. Neu khong, them truc tiep vao ket qua.

---

## Bai 2: Closure - Counter Factory

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

**Giai thich:**
- Closure giu cac bien `value`, `history`, `undoStack` private - khong truy cap duoc tu ben ngoai.
- `clamp()` dam bao gia tri luon nam trong khoang [min, max].
- `saveAndUpdate()` luu gia tri cu vao undoStack truoc khi cap nhat.
- `undo()` lay gia tri tu undoStack va them vao history.

---

## Bai 3: Deep Clone

```javascript
function deepClone(obj, seen = new WeakMap()) {
    // Xu ly primitive va null
    if (obj === null || typeof obj !== "object") {
        return obj;
    }

    // Xu ly circular reference
    if (seen.has(obj)) {
        return seen.get(obj);
    }

    // Xu ly Date
    if (obj instanceof Date) {
        return new Date(obj.getTime());
    }

    // Xu ly RegExp
    if (obj instanceof RegExp) {
        return new RegExp(obj.source, obj.flags);
    }

    // Xu ly Map
    if (obj instanceof Map) {
        const mapClone = new Map();
        seen.set(obj, mapClone);
        for (const [key, value] of obj) {
            mapClone.set(deepClone(key, seen), deepClone(value, seen));
        }
        return mapClone;
    }

    // Xu ly Set
    if (obj instanceof Set) {
        const setClone = new Set();
        seen.set(obj, setClone);
        for (const value of obj) {
            setClone.add(deepClone(value, seen));
        }
        return setClone;
    }

    // Xu ly Array
    if (Array.isArray(obj)) {
        const arrClone = [];
        seen.set(obj, arrClone);
        for (let i = 0; i < obj.length; i++) {
            arrClone[i] = deepClone(obj[i], seen);
        }
        return arrClone;
    }

    // Xu ly Object
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

console.log(cloned.self === cloned); // true (circular ref duoc xu ly)
console.log(cloned.self !== original); // true
```

**Giai thich:**
- Su dung `WeakMap` (`seen`) de theo doi cac object da clone, ngan vong lap vo han khi gap circular reference.
- Kiem tra tung kieu du lieu va xu ly phu hop: Date -> copy timestamp, RegExp -> copy source va flags.
- Su dung `Reflect.ownKeys()` de lay tat ca keys (ke ca Symbol).
- `Object.create(Object.getPrototypeOf(obj))` giu nguyen prototype chain.

---

## Bai 4: Promise Pool

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

    // Tao poolSize workers chay dong thoi
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

**Giai thich:**
- Tao `poolSize` workers chay dong thoi. Moi worker lay task tiep theo tu mang tasks khi hoan thanh task hien tai.
- `currentIndex` dam bao moi task chi duoc lay 1 lan (JavaScript la single-threaded nen khong can lock).
- Ket qua luu theo index goc de dam bao thu tu.
- try/catch xu ly loi cua tung task rieng, khong anh huong task khac.

---

## Bai 5: Event Emitter

```javascript
class EventEmitter {
    #listeners = {};
    #errorHandler = null;

    on(event, callback) {
        if (!this.#listeners[event]) {
            this.#listeners[event] = [];
        }
        this.#listeners[event].push(callback);

        // Tra ve ham unsubscribe
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
        // Goi cac listener cua event cu the
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

        // Goi wildcard listeners
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
        wrapper._original = callback; // Luu ref de co the off bang callback goc
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

// Wildcard - bat tat ca event
emitter.on("*", (event, ...args) => {
    console.log(`[${event}]`, ...args);
});

// Once - chi chay 1 lan
emitter.once("connect", () => console.log("Connected!"));

// Regular listener voi unsubscribe
const unsub = emitter.on("data", (msg) => console.log("Data:", msg));

emitter.emit("connect");
// "Connected!" va "[connect]"

emitter.emit("connect");
// Chi "[connect]" (once da xoa)

emitter.emit("data", "Hello");
// "Data: Hello" va "[data] Hello"

console.log(emitter.listenerCount("data")); // 1
unsub(); // Go bo
console.log(emitter.listenerCount("data")); // 0
```

**Giai thich:**
- Su dung private field `#listeners` de luu tru cac event handlers theo event name.
- `on()` tra ve ham unsubscribe (closure).
- `once()` boc callback trong wrapper, wrapper tu go bo chinh no sau khi chay.
- `emit()` spread listener array (`[...listeners]`) de tranh loi khi listener tu go bo chinh no trong qua trinh emit.
- Wildcard `*` nhan event name lam tham so dau tien.
- Error handling bao ve cac listener khac khi 1 listener throw error.

---

## Bai 6: Debounce va Throttle

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
// Chi goi 1 lan sau 100ms: "Called: 1"

// Test throttle
const throttled = throttle((x) => console.log("Throttled:", x), 200);
throttled(1); // Goi ngay
throttled(2); // Bo qua
throttled(3); // Bo qua, nhung se goi sau 200ms voi args cuoi
```

**Giai thich:**
- **Debounce**: Moi lan goi, huy timer cu va tao timer moi. Ham chi thuc su chay khi ngung goi trong `delay` ms. `leading` cho phep goi ngay lan dau. `maxWait` dam bao ham duoc goi it nhat 1 lan trong khoang thoi gian do.
- **Throttle**: Dam bao ham chi chay 1 lan trong moi `interval`. Neu goi nhieu lan, lan cuoi se duoc goi khi het interval (trailing call).

---

## Bai 7: Mini Reactive System

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
    effectFn(); // Chay lan dau de thu thap dependencies
    return effectFn;
}

function computed(fn) {
    let cachedValue;
    let dirty = true;

    const effectFn = effect(() => {
        // Khi dependency thay doi, danh dau dirty
        dirty = true;
    });

    // Override effect de khong chay fn ngay
    // Ma chi danh dau dirty
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

// Cach implement computed don gian hon (de hieu):
function computedSimple(fn) {
    let cache;
    let dirty = true;

    // Tao effect de theo doi dependencies
    const runner = () => {
        dirty = true;
    };

    // Thu thap dependencies lan dau
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
    console.log(`Count la: ${state.count}`);
});
// In ngay: "Count la: 0"

state.count = 5;
// Tu dong in: "Count la: 5"

state.count = 5;
// KHONG in (gia tri khong thay doi)

state.name = "Binh";
// KHONG in (effect khong phu thuoc name)

console.log("--- Computed demo ---");
const doubled = computedSimple(() => state.count * 2);
console.log(doubled.value); // 10
state.count = 10;
console.log(doubled.value); // 20
```

**Giai thich:**
- **reactive()**: Su dung Proxy de chan `get` (theo doi dependencies) va `set` (kich hoat re-render).
- **track()**: Khi doc thuoc tinh trong effect, luu lai rang effect nay phu thuoc vao thuoc tinh do. Su dung `WeakMap -> Map -> Set` de luu dependency graph.
- **trigger()**: Khi thay doi thuoc tinh, tim tat ca effects phu thuoc va chay lai chung.
- **effect()**: Chay ham lan dau de thu thap dependencies (thiet lap `activeEffect` truoc khi chay).
- **computed()**: Chi tinh lai khi dependency thay doi (lazy evaluation voi cache).

---

## Bai 8: JSON Path Query

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

    // Tach path theo dot va bracket
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
            { title: "CSS Tricks", price: 80, author: "Binh" },
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

**Giai thich:**
- **tokenize()**: Tach path string thanh mang tokens (property, index, wildcard, filter).
- **evaluate()**: Duyet qua tung token va truy van du lieu:
  - `prop`: Truy cap thuoc tinh object.
  - `index`: Truy cap phan tu array theo chi so.
  - `wildcard [*]`: Map qua tat ca phan tu, ap dung tokens con lai cho moi phan tu.
  - `filter [?()]`: Loc phan tu theo dieu kien, thay `@.prop` bang gia tri thuc te.
- Su dung de quy cho wildcard va filter de xu ly cac tokens con lai.

---

## Bai 9: Currying va Partial Application

```javascript
// Curry - chuyen ham nhieu tham so thanh chuoi ham
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

// Partial - co dinh mot so tham so
function partial(fn, ...partialArgs) {
    return function(...remainingArgs) {
        return fn.apply(this, [...partialArgs, ...remainingArgs]);
    };
}

// Compose - chay tu phai sang trai
function compose(...fns) {
    if (fns.length === 0) return (x) => x;
    if (fns.length === 1) return fns[0];

    return function(x) {
        return fns.reduceRight((acc, fn) => fn(acc), x);
    };
}

// Pipe - chay tu trai sang phai
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

// Test Compose va Pipe
const double = x => x * 2;
const addOne = x => x + 1;
const square = x => x * x;

const composed = compose(square, addOne, double);
console.log(composed(3));           // 49: double(3)=6, addOne(6)=7, square(7)=49

const piped = pipe(double, addOne, square);
console.log(piped(3));              // 49: double(3)=6, addOne(6)=7, square(7)=49
```

**Giai thich:**
- **curry()**: So sanh so luong args da nhan voi `fn.length` (so tham so cua ham goc). Neu du -> goi ham. Neu chua du -> tra ve ham moi cho them args.
- **partial()**: Dung closure de luu `partialArgs`, khi goi ham moi -> gop partialArgs va remainingArgs.
- **compose()**: `reduceRight` chay cac ham tu phai sang trai, truyen output cua ham truoc lam input cua ham sau.
- **pipe()**: `reduce` chay cac ham tu trai sang phai.

---

## Bai 10: LRU Cache

```javascript
class LRUCache {
    #capacity;
    #cache;

    constructor(capacity) {
        if (capacity <= 0) throw new Error("Capacity phai > 0");
        this.#capacity = capacity;
        this.#cache = new Map(); // Map giu thu tu chen
    }

    get(key) {
        if (!this.#cache.has(key)) return -1;

        // Di chuyen key len cuoi (recently used)
        const value = this.#cache.get(key);
        this.#cache.delete(key);
        this.#cache.set(key, value);
        return value;
    }

    put(key, value) {
        // Neu key da ton tai, xoa de cap nhat vi tri
        if (this.#cache.has(key)) {
            this.#cache.delete(key);
        } else if (this.#cache.size >= this.#capacity) {
            // Xoa item cu nhat (dau Map = least recently used)
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

    // Bonus: lay tat ca entries (tu moi nhat den cu nhat)
    entries() {
        return [...this.#cache.entries()].reverse();
    }

    // Bonus: kiem tra key co ton tai
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

console.log(cache.get("a")); // 1 (a tro thanh recently used)

cache.put("d", 4); // Cache day -> xoa "b" (least recently used)
console.log(cache.get("b")); // -1 (da bi xoa)

console.log(cache.get("a")); // 1
console.log(cache.get("c")); // 3
console.log(cache.get("d")); // 4
console.log(cache.size());   // 3

// Thu tu tu moi nhat den cu nhat
console.log(cache.entries());
// [["d", 4], ["c", 3], ["a", 1]]

cache.put("e", 5); // Xoa "a" (least recently used sau khi d va c duoc truy cap)
console.log(cache.get("a")); // -1

cache.clear();
console.log(cache.size()); // 0
```

**Giai thich:**
- Su dung `Map` cua JavaScript vi no **giu thu tu chen**. Phan tu duoc chen dau tien se o dau iterator.
- **get()**: Khi truy cap, xoa va them lai de chuyen key len cuoi (most recently used).
- **put()**: Neu da day va key chua ton tai, xoa phan tu dau tien cua Map (least recently used) bang `map.keys().next().value`.
- Do phuc tap: O(1) cho ca `get` va `put` vi Map operations la O(1) trung binh.
- Su dung private fields (`#capacity`, `#cache`) de encapsulate.
