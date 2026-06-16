# JavaScript - Bai Tap (Trung Binh den Kho)

## Bai 1: Array Utilities
**Do kho: Trung binh**

Viet cac ham xu ly mang sau (KHONG dung cac method co san nhu filter, map, reduce):

1. `myFilter(arr, callback)` - loc cac phan tu thoa dieu kien
2. `myMap(arr, callback)` - bien doi moi phan tu
3. `myReduce(arr, callback, initialValue)` - gom mang thanh 1 gia tri
4. `myFlat(arr, depth)` - lam phang mang long nhau

**Dau vao:**
```javascript
const numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
```

**Dau ra:**
```javascript
myFilter(numbers, n => n % 2 === 0);  // [2, 4, 6, 8, 10]
myMap(numbers, n => n * 3);           // [3, 6, 9, 12, 15, 18, 21, 24, 27, 30]
myReduce(numbers, (acc, n) => acc + n, 0); // 55
myFlat([1, [2, [3, [4]]]], 2);        // [1, 2, 3, [4]]
```

---

## Bai 2: Closure - Counter Factory
**Do kho: Trung binh**

Tao mot ham `createCounter(options)` su dung closure voi cac yeu cau:
1. Nhan options: `{ min, max, step, initialValue }`
2. Tra ve object voi cac method: `increment()`, `decrement()`, `reset()`, `getValue()`, `getHistory()`
3. Gia tri khong duoc vuot qua `min` va `max`
4. Luu lai lich su thay doi (moi lan tang/giam/reset)
5. Ho tro `undo()` - quay lai gia tri truoc do

**Dau vao:**
```javascript
const counter = createCounter({ min: 0, max: 10, step: 2, initialValue: 4 });
```

**Dau ra:**
```javascript
counter.getValue();   // 4
counter.increment();  // 6
counter.increment();  // 8
counter.increment();  // 10 (khong vuot qua max)
counter.increment();  // 10
counter.decrement();  // 8
counter.undo();       // 10 (quay lai)
counter.getHistory(); // [4, 6, 8, 10, 10, 8, 10]
counter.reset();      // 4 (ve initialValue)
```

---

## Bai 3: Deep Clone
**Do kho: Trung binh**

Viet ham `deepClone(obj)` tao ban sao sau (deep copy) cua mot object. Ho tro cac kieu du lieu:
1. Primitive values (number, string, boolean, null, undefined)
2. Object va nested objects
3. Array va nested arrays
4. Date
5. RegExp
6. Map va Set
7. Xu ly circular reference (object tham chieu chinh no)

**Dau vao:**
```javascript
const original = {
    name: "An",
    scores: [90, 85, 95],
    info: { age: 25, address: { city: "HCM" } },
    createdAt: new Date(),
    pattern: /test/gi,
    tags: new Set(["js", "web"]),
    meta: new Map([["key", "value"]])
};
```

**Dau ra:**
```javascript
const cloned = deepClone(original);
cloned.info.address.city = "HN";
console.log(original.info.address.city); // "HCM" (khong bi anh huong)
cloned.scores.push(100);
console.log(original.scores.length); // 3 (khong bi anh huong)
```

---

## Bai 4: Promise Pool
**Do kho: Kho**

Viet ham `promisePool(tasks, poolSize)` gioi han so luong Promise chay dong thoi:
1. `tasks` la mang cac ham tra ve Promise
2. `poolSize` la so luong Promise toi da chay cung luc
3. Tra ve mang ket qua theo dung thu tu cua tasks
4. Neu mot task that bai, van tiep tuc chay cac task con lai
5. Tra ve ca ket qua thanh cong va loi (tuong tu `Promise.allSettled`)

**Dau vao:**
```javascript
const tasks = [
    () => delay(100).then(() => "A"),
    () => delay(200).then(() => "B"),
    () => delay(50).then(() => { throw new Error("Failed"); }),
    () => delay(150).then(() => "D"),
    () => delay(80).then(() => "E")
];
```

**Dau ra:**
```javascript
const results = await promisePool(tasks, 2);
// [
//   { status: "fulfilled", value: "A" },
//   { status: "fulfilled", value: "B" },
//   { status: "rejected", reason: Error("Failed") },
//   { status: "fulfilled", value: "D" },
//   { status: "fulfilled", value: "E" }
// ]
// Toi da 2 tasks chay cung luc tai moi thoi diem
```

---

## Bai 5: Event Emitter
**Do kho: Trung binh - Kho**

Implement class `EventEmitter` voi day du tinh nang:
1. `on(event, callback)` - dang ky listener, tra ve ham `unsubscribe`
2. `off(event, callback)` - go bo listener
3. `emit(event, ...args)` - phat su kien voi du lieu
4. `once(event, callback)` - listener chi chay 1 lan
5. `listenerCount(event)` - dem so listener cua event
6. `removeAllListeners(event?)` - go bo tat ca listener (hoac cua 1 event)
7. Ho tro wildcard `*` - listener bat tat ca event
8. Ho tro `onError(callback)` - xu ly loi tu listener

**Dau vao:**
```javascript
const emitter = new EventEmitter();
```

**Dau ra:**
```javascript
const unsub = emitter.on("data", (msg) => console.log(msg));
emitter.once("connect", () => console.log("Connected!"));
emitter.on("*", (event, ...args) => console.log(`[${event}]`, ...args));

emitter.emit("connect");          // "Connected!" va "[connect]"
emitter.emit("connect");          // Chi "[connect]" (once da xoa)
emitter.emit("data", "Hello");    // "Hello" va "[data] Hello"

unsub();                           // Go bo listener "data"
emitter.listenerCount("data");    // 0
```

---

## Bai 6: Debounce va Throttle
**Do kho: Trung binh - Kho**

Implement 2 ham:

**debounce(fn, delay, options):**
1. Tra hoan thuc thi ham cho den khi ngung goi trong `delay` ms
2. Options: `{ leading: false, trailing: true, maxWait: undefined }`
3. `leading: true` - goi ngay lan dau, sau do debounce
4. `maxWait` - thoi gian toi da cho truoc khi bat buoc goi
5. Tra ve ham co method `cancel()` va `flush()`

**throttle(fn, interval):**
1. Dam bao ham chi duoc goi toi da 1 lan trong moi `interval` ms
2. Tra ve ham co method `cancel()`

**Dau vao:**
```javascript
const debouncedSearch = debounce(search, 300, { leading: true, maxWait: 1000 });
const throttledScroll = throttle(handleScroll, 200);
```

**Dau ra:**
```javascript
// debounce: Goi search ngay lan dau (leading), sau do doi 300ms khong goi moi chay lai.
// Neu goi lien tuc > 1000ms, bat buoc goi 1 lan (maxWait).
debouncedSearch("query1"); // Goi ngay (leading)
debouncedSearch("query2"); // Doi 300ms...
debouncedSearch.cancel();  // Huy pending call
debouncedSearch.flush();   // Goi ngay pending call

// throttle: handleScroll chi chay toi da moi 200ms du scroll lien tuc
window.addEventListener("scroll", throttledScroll);
```

---

## Bai 7: Mini Reactive System
**Do kho: Kho**

Xay dung mot he thong reactive don gian (giong nhu Vue.js reactivity):
1. `reactive(obj)` - tao reactive object (su dung Proxy)
2. `effect(fn)` - dang ky side effect, tu dong chay lai khi dependency thay doi
3. `computed(fn)` - tao gia tri computed, chi tinh lai khi dependency thay doi (co cache)

**Dau vao:**
```javascript
const state = reactive({ count: 0, name: "An" });
```

**Dau ra:**
```javascript
// Effect tu dong chay lai khi state thay doi
effect(() => {
    console.log(`Count la: ${state.count}`);
});
// In ngay: "Count la: 0"

const double = computed(() => state.count * 2);
console.log(double.value); // 0

state.count = 5;
// Effect tu dong chay: "Count la: 5"
console.log(double.value); // 10

state.count = 5; // Khong thay doi -> effect KHONG chay lai
state.name = "Binh"; // Effect tren khong phu thuoc name -> KHONG chay lai
```

---

## Bai 8: JSON Path Query
**Do kho: Kho**

Viet ham `jsonQuery(obj, path)` truy van du lieu tu object theo path string:
1. Ho tro dot notation: `"user.name"`
2. Ho tro array index: `"users[0].name"`
3. Ho tro wildcard: `"users[*].name"` (lay name cua tat ca users)
4. Ho tro filter: `"users[?(@.age > 25)].name"` (loc theo dieu kien)
5. Tra ve `undefined` neu path khong ton tai (khong throw error)

**Dau vao:**
```javascript
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
```

**Dau ra:**
```javascript
jsonQuery(data, "store.location.city");              // "HCM"
jsonQuery(data, "store.books[0].title");             // "JS Guide"
jsonQuery(data, "store.books[*].title");             // ["JS Guide", "CSS Tricks", "Node.js"]
jsonQuery(data, 'store.books[?(@.price > 100)].title'); // ["Node.js"]
jsonQuery(data, 'store.books[?(@.author === "An")].title'); // ["JS Guide", "Node.js"]
jsonQuery(data, "store.noExist.deep");               // undefined
```

---

## Bai 9: Currying va Partial Application
**Do kho: Trung binh - Kho**

Implement cac ham sau:

1. `curry(fn)` - chuyen doi ham nhieu tham so thanh chuoi ham 1 tham so
2. `partial(fn, ...args)` - tao ham moi voi mot so tham so da duoc co dinh
3. `compose(...fns)` - ket hop nhieu ham (chay tu phai sang trai)
4. `pipe(...fns)` - ket hop nhieu ham (chay tu trai sang phai)

**Dau vao:**
```javascript
function add(a, b, c) { return a + b + c; }
```

**Dau ra:**
```javascript
// Curry
const curriedAdd = curry(add);
curriedAdd(1)(2)(3);      // 6
curriedAdd(1, 2)(3);      // 6
curriedAdd(1)(2, 3);      // 6
curriedAdd(1, 2, 3);      // 6

// Partial
const add10 = partial(add, 10);
add10(20, 30);             // 60

// Compose va Pipe
const double = x => x * 2;
const addOne = x => x + 1;
const square = x => x * x;

const composed = compose(square, addOne, double);
composed(3); // square(addOne(double(3))) = square(addOne(6)) = square(7) = 49

const piped = pipe(double, addOne, square);
piped(3);    // square(addOne(double(3))) = 49
```

---

## Bai 10: LRU Cache
**Do kho: Kho**

Implement class `LRUCache` (Least Recently Used Cache):
1. Constructor nhan `capacity` - so luong item toi da
2. `get(key)` - lay gia tri, tra ve -1 neu khong ton tai. Item duoc truy cap se tro thanh "recently used"
3. `put(key, value)` - them/cap nhat item. Neu da day, xoa item it duoc su dung nhat
4. `delete(key)` - xoa item
5. `size()` - so luong item hien tai
6. `clear()` - xoa tat ca
7. Do phuc tap: O(1) cho ca `get` va `put`

**Goi y:** Su dung Map (giu thu tu chen) hoac doubly linked list + Map.

**Dau vao:**
```javascript
const cache = new LRUCache(3);
```

**Dau ra:**
```javascript
cache.put("a", 1);
cache.put("b", 2);
cache.put("c", 3);
cache.get("a");        // 1 (a tro thanh recently used)
cache.put("d", 4);     // Cache day -> xoa "b" (least recently used)
cache.get("b");        // -1 (da bi xoa)
cache.size();          // 3
cache.get("a");        // 1
cache.get("c");        // 3
cache.get("d");        // 4
```
