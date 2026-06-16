# JavaScript - Bài Tập (Trung Bình đến Khó)

## Bài 1: Array Utilities
**Độ khó: Trung bình**

Viết các hàm xử lý mảng sau (KHÔNG dùng các method có sẵn như filter, map, reduce):

1. `myFilter(arr, callback)` - lọc các phần tử thỏa điều kiện
2. `myMap(arr, callback)` - biến đổi mỗi phần tử
3. `myReduce(arr, callback, initialValue)` - gom mảng thành 1 giá trị
4. `myFlat(arr, depth)` - làm phẳng mảng lồng nhau

**Đầu vào:**
```javascript
const numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
```

**Đầu ra:**
```javascript
myFilter(numbers, n => n % 2 === 0);  // [2, 4, 6, 8, 10]
myMap(numbers, n => n * 3);           // [3, 6, 9, 12, 15, 18, 21, 24, 27, 30]
myReduce(numbers, (acc, n) => acc + n, 0); // 55
myFlat([1, [2, [3, [4]]]], 2);        // [1, 2, 3, [4]]
```

---

## Bài 2: Closure - Counter Factory
**Độ khó: Trung bình**

Tạo một hàm `createCounter(options)` sử dụng closure với các yêu cầu:
1. Nhận options: `{ min, max, step, initialValue }`
2. Trả về object với các method: `increment()`, `decrement()`, `reset()`, `getValue()`, `getHistory()`
3. Giá trị không được vượt quá `min` và `max`
4. Lưu lại lịch sử thay đổi (mỗi lần tăng/giảm/reset)
5. Hỗ trợ `undo()` - quay lại giá trị trước đó

**Đầu vào:**
```javascript
const counter = createCounter({ min: 0, max: 10, step: 2, initialValue: 4 });
```

**Đầu ra:**
```javascript
counter.getValue();   // 4
counter.increment();  // 6
counter.increment();  // 8
counter.increment();  // 10 (không vượt quá max)
counter.increment();  // 10
counter.decrement();  // 8
counter.undo();       // 10 (quay lại)
counter.getHistory(); // [4, 6, 8, 10, 10, 8, 10]
counter.reset();      // 4 (về initialValue)
```

---

## Bài 3: Deep Clone
**Độ khó: Trung bình**

Viết hàm `deepClone(obj)` tạo bản sao sâu (deep copy) của một object. Hỗ trợ các kiểu dữ liệu:
1. Primitive values (number, string, boolean, null, undefined)
2. Object và nested objects
3. Array và nested arrays
4. Date
5. RegExp
6. Map và Set
7. Xử lý circular reference (object tham chiếu chính nó)

**Đầu vào:**
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

**Đầu ra:**
```javascript
const cloned = deepClone(original);
cloned.info.address.city = "HN";
console.log(original.info.address.city); // "HCM" (không bị ảnh hưởng)
cloned.scores.push(100);
console.log(original.scores.length); // 3 (không bị ảnh hưởng)
```

---

## Bài 4: Promise Pool
**Độ khó: Khó**

Viết hàm `promisePool(tasks, poolSize)` giới hạn số lượng Promise chạy đồng thời:
1. `tasks` là mảng các hàm trả về Promise
2. `poolSize` là số lượng Promise tối đa chạy cùng lúc
3. Trả về mảng kết quả theo đúng thứ tự của tasks
4. Nếu một task thất bại, vẫn tiếp tục chạy các task còn lại
5. Trả về cả kết quả thành công và lỗi (tương tự `Promise.allSettled`)

**Đầu vào:**
```javascript
const tasks = [
    () => delay(100).then(() => "A"),
    () => delay(200).then(() => "B"),
    () => delay(50).then(() => { throw new Error("Failed"); }),
    () => delay(150).then(() => "D"),
    () => delay(80).then(() => "E")
];
```

**Đầu ra:**
```javascript
const results = await promisePool(tasks, 2);
// [
//   { status: "fulfilled", value: "A" },
//   { status: "fulfilled", value: "B" },
//   { status: "rejected", reason: Error("Failed") },
//   { status: "fulfilled", value: "D" },
//   { status: "fulfilled", value: "E" }
// ]
// Tối đa 2 tasks chạy cùng lúc tại mỗi thời điểm
```

---

## Bài 5: Event Emitter
**Độ khó: Trung bình - Khó**

Implement class `EventEmitter` với đầy đủ tính năng:
1. `on(event, callback)` - đăng ký listener, trả về hàm `unsubscribe`
2. `off(event, callback)` - gỡ bỏ listener
3. `emit(event, ...args)` - phát sự kiện với dữ liệu
4. `once(event, callback)` - listener chỉ chạy 1 lần
5. `listenerCount(event)` - đếm số listener của event
6. `removeAllListeners(event?)` - gỡ bỏ tất cả listener (hoặc của 1 event)
7. Hỗ trợ wildcard `*` - listener bắt tất cả event
8. Hỗ trợ `onError(callback)` - xử lý lỗi từ listener

**Đầu vào:**
```javascript
const emitter = new EventEmitter();
```

**Đầu ra:**
```javascript
const unsub = emitter.on("data", (msg) => console.log(msg));
emitter.once("connect", () => console.log("Connected!"));
emitter.on("*", (event, ...args) => console.log(`[${event}]`, ...args));

emitter.emit("connect");          // "Connected!" và "[connect]"
emitter.emit("connect");          // Chỉ "[connect]" (once đã xóa)
emitter.emit("data", "Hello");    // "Hello" và "[data] Hello"

unsub();                           // Gỡ bỏ listener "data"
emitter.listenerCount("data");    // 0
```

---

## Bài 6: Debounce và Throttle
**Độ khó: Trung bình - Khó**

Implement 2 hàm:

**debounce(fn, delay, options):**
1. Trì hoãn thực thi hàm cho đến khi ngừng gọi trong `delay` ms
2. Options: `{ leading: false, trailing: true, maxWait: undefined }`
3. `leading: true` - gọi ngay lần đầu, sau đó debounce
4. `maxWait` - thời gian tối đa chờ trước khi bắt buộc gọi
5. Trả về hàm có method `cancel()` và `flush()`

**throttle(fn, interval):**
1. Đảm bảo hàm chỉ được gọi tối đa 1 lần trong mỗi `interval` ms
2. Trả về hàm có method `cancel()`

**Đầu vào:**
```javascript
const debouncedSearch = debounce(search, 300, { leading: true, maxWait: 1000 });
const throttledScroll = throttle(handleScroll, 200);
```

**Đầu ra:**
```javascript
// debounce: Gọi search ngay lần đầu (leading), sau đó đợi 300ms không gọi mới chạy lại.
// Nếu gọi liên tục > 1000ms, bắt buộc gọi 1 lần (maxWait).
debouncedSearch("query1"); // Gọi ngay (leading)
debouncedSearch("query2"); // Đợi 300ms...
debouncedSearch.cancel();  // Hủy pending call
debouncedSearch.flush();   // Gọi ngay pending call

// throttle: handleScroll chỉ chạy tối đa mỗi 200ms dù scroll liên tục
window.addEventListener("scroll", throttledScroll);
```

---

## Bài 7: Mini Reactive System
**Độ khó: Khó**

Xây dựng một hệ thống reactive đơn giản (giống như Vue.js reactivity):
1. `reactive(obj)` - tạo reactive object (sử dụng Proxy)
2. `effect(fn)` - đăng ký side effect, tự động chạy lại khi dependency thay đổi
3. `computed(fn)` - tạo giá trị computed, chỉ tính lại khi dependency thay đổi (có cache)

**Đầu vào:**
```javascript
const state = reactive({ count: 0, name: "An" });
```

**Đầu ra:**
```javascript
// Effect tự động chạy lại khi state thay đổi
effect(() => {
    console.log(`Count là: ${state.count}`);
});
// In ngay: "Count là: 0"

const double = computed(() => state.count * 2);
console.log(double.value); // 0

state.count = 5;
// Effect tự động chạy: "Count là: 5"
console.log(double.value); // 10

state.count = 5; // Không thay đổi -> effect KHÔNG chạy lại
state.name = "Bình"; // Effect trên không phụ thuộc name -> KHÔNG chạy lại
```

---

## Bài 8: JSON Path Query
**Độ khó: Khó**

Viết hàm `jsonQuery(obj, path)` truy vấn dữ liệu từ object theo path string:
1. Hỗ trợ dot notation: `"user.name"`
2. Hỗ trợ array index: `"users[0].name"`
3. Hỗ trợ wildcard: `"users[*].name"` (lấy name của tất cả users)
4. Hỗ trợ filter: `"users[?(@.age > 25)].name"` (lọc theo điều kiện)
5. Trả về `undefined` nếu path không tồn tại (không throw error)

**Đầu vào:**
```javascript
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
```

**Đầu ra:**
```javascript
jsonQuery(data, "store.location.city");              // "HCM"
jsonQuery(data, "store.books[0].title");             // "JS Guide"
jsonQuery(data, "store.books[*].title");             // ["JS Guide", "CSS Tricks", "Node.js"]
jsonQuery(data, 'store.books[?(@.price > 100)].title'); // ["Node.js"]
jsonQuery(data, 'store.books[?(@.author === "An")].title'); // ["JS Guide", "Node.js"]
jsonQuery(data, "store.noExist.deep");               // undefined
```

---

## Bài 9: Currying và Partial Application
**Độ khó: Trung bình - Khó**

Implement các hàm sau:

1. `curry(fn)` - chuyển đổi hàm nhiều tham số thành chuỗi hàm 1 tham số
2. `partial(fn, ...args)` - tạo hàm mới với một số tham số đã được cố định
3. `compose(...fns)` - kết hợp nhiều hàm (chạy từ phải sang trái)
4. `pipe(...fns)` - kết hợp nhiều hàm (chạy từ trái sang phải)

**Đầu vào:**
```javascript
function add(a, b, c) { return a + b + c; }
```

**Đầu ra:**
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

// Compose và Pipe
const double = x => x * 2;
const addOne = x => x + 1;
const square = x => x * x;

const composed = compose(square, addOne, double);
composed(3); // square(addOne(double(3))) = square(addOne(6)) = square(7) = 49

const piped = pipe(double, addOne, square);
piped(3);    // square(addOne(double(3))) = 49
```

---

## Bài 10: LRU Cache
**Độ khó: Khó**

Implement class `LRUCache` (Least Recently Used Cache):
1. Constructor nhận `capacity` - số lượng item tối đa
2. `get(key)` - lấy giá trị, trả về -1 nếu không tồn tại. Item được truy cập sẽ trở thành "recently used"
3. `put(key, value)` - thêm/cập nhật item. Nếu đã đầy, xóa item ít được sử dụng nhất
4. `delete(key)` - xóa item
5. `size()` - số lượng item hiện tại
6. `clear()` - xóa tất cả
7. Độ phức tạp: O(1) cho cả `get` và `put`

**Gợi ý:** Sử dụng Map (giữ thứ tự chèn) hoặc doubly linked list + Map.

**Đầu vào:**
```javascript
const cache = new LRUCache(3);
```

**Đầu ra:**
```javascript
cache.put("a", 1);
cache.put("b", 2);
cache.put("c", 3);
cache.get("a");        // 1 (a trở thành recently used)
cache.put("d", 4);     // Cache đầy -> xóa "b" (least recently used)
cache.get("b");        // -1 (đã bị xóa)
cache.size();          // 3
cache.get("a");        // 1
cache.get("c");        // 3
cache.get("d");        // 4
```
