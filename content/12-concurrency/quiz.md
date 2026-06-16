# Quiz - Concurrency

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Hai cách tạo thread trong Java?

- [x] Extend Thread class hoặc implement Runnable interface
- [ ] Chỉ extend Thread
- [ ] Chỉ implement Runnable
- [ ] Dùng synchronized keyword

> **Giải thích:** Thread: `class MyThread extends Thread { run() }`. Runnable: `class MyTask implements Runnable { run() }`. Runnable preferred (không tốn inheritance).

## Câu 2

[TYPE: SELECT_RESULT]

```java
Thread t = new Thread(() -> System.out.print("Hello"));
t.start();
t.join();
System.out.print(" World");
```

- [x] Hello World
- [ ] World Hello
- [ ] Hello
- [ ] Random order

> **Giải thích:** t.start(): bắt đầu thread. t.join(): main thread chờ t hoàn thành. Sau join → in " World". Đảm bảo order.

## Câu 3

[TYPE: FILL_BLANK]

`synchronized` keyword đảm bảo chỉ `___` thread truy cập critical section tại một thời điểm.

- [x] một (one)
- [ ] hai
- [ ] tất cả
- [ ] không

> **Giải thích:** synchronized: mutual exclusion (mutex). Chỉ 1 thread hold lock. Khác threads phải wait. Đảm bảo thread safety.

## Câu 4

[TYPE: SELECT_RESULT]

```java
class Counter {
    private int count = 0;
    public synchronized void increment() { count++; }
    public synchronized int getCount() { return count; }
}
Counter counter = new Counter();
Thread t1 = new Thread(() -> { for (int i = 0; i < 1000; i++) counter.increment(); });
Thread t2 = new Thread(() -> { for (int i = 0; i < 1000; i++) counter.increment(); });
t1.start(); t2.start();
t1.join(); t2.join();
System.out.println(counter.getCount());
```

- [x] 2000
- [ ] Giá trị < 2000 (race condition)
- [ ] 1000
- [ ] Lỗi runtime

> **Giải thích:** synchronized increment(): thread-safe. Mỗi thread +1000. Total = 2000. Không có synchronized → race condition → < 2000.

## Câu 5

[TYPE: TRUE_FALSE]

Mệnh đề: "volatile keyword đảm bảo visibility nhưng KHÔNG đảm bảo atomicity."

- [x] Đúng
- [ ] Sai

> **Giải thích:** volatile: đọc/ghi trực tiếp main memory (visibility). count++ không atomic (read+increment+write). Dùng AtomicInteger cho atomic operations.

## Câu 6

[TYPE: SELECT_RESULT]

```java
AtomicInteger counter = new AtomicInteger(0);
Thread t1 = new Thread(() -> { for (int i = 0; i < 1000; i++) counter.incrementAndGet(); });
Thread t2 = new Thread(() -> { for (int i = 0; i < 1000; i++) counter.incrementAndGet(); });
t1.start(); t2.start();
t1.join(); t2.join();
System.out.println(counter.get());
```

- [x] 2000
- [ ] < 2000
- [ ] 1000
- [ ] Lỗi

> **Giải thích:** AtomicInteger: lock-free atomic operations. incrementAndGet(): atomic increment. Thread-safe without synchronized. CAS (Compare-And-Swap).

## Câu 7

[TYPE: MULTIPLE_CHOICE]

Thread states trong Java?

- [x] NEW → RUNNABLE → (BLOCKED/WAITING/TIMED_WAITING) → TERMINATED
- [ ] Chỉ RUNNING và STOPPED
- [ ] START → RUN → END
- [ ] NEW → RUNNING → DEAD

> **Giải thích:** NEW: created. RUNNABLE: executing/ready. BLOCKED: waiting for monitor lock. WAITING: wait()/join(). TIMED_WAITING: sleep()/wait(timeout). TERMINATED: finished.

## Câu 8

[TYPE: SELECT_RESULT]

```java
ExecutorService executor = Executors.newFixedThreadPool(2);
List<Future<Integer>> futures = new ArrayList<>();
for (int i = 0; i < 5; i++) {
    final int num = i;
    futures.add(executor.submit(() -> num * num));
}
int sum = 0;
for (Future<Integer> f : futures) sum += f.get();
executor.shutdown();
System.out.println(sum);
```

- [x] 30
- [ ] 10
- [ ] 25
- [ ] 0

> **Giải thích:** 0²+1²+2²+3²+4² = 0+1+4+9+16 = 30. FixedThreadPool(2): 2 worker threads. Future.get(): block until result. Sum = 30.

## Câu 9

[TYPE: FILL_BLANK]

`ReentrantLock` là lock cho phép cùng thread `___` lock nhiều lần.

- [x] acquire (lấy)
- [ ] release
- [ ] break
- [ ] share

> **Giải thích:** Reentrant: same thread can lock() multiple times. Must unlock() same number of times. Ví dụ: method A locks → calls method B which also locks → OK.

## Câu 10

[TYPE: SELECT_RESULT]

```java
ReentrantLock lock = new ReentrantLock();
lock.lock();
try {
    lock.lock(); // reentrant
    System.out.println("Hold count: " + lock.getHoldCount());
    lock.unlock();
} finally {
    lock.unlock();
}
System.out.println("Hold count: " + lock.getHoldCount());
```

- [x] Hold count: 2 và Hold count: 0
- [ ] IllegalMonitorStateException
- [ ] Hold count: 1 và Hold count: 0
- [ ] Deadlock

> **Giải thích:** First lock → holdCount=1. Second lock → holdCount=2 (reentrant). First unlock → 1. Second unlock → 0.

## Câu 11

[TYPE: SELECT_RESULT]

```java
CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> "Hello")
    .thenApply(s -> s + " World")
    .thenApply(String::toUpperCase);
System.out.println(cf.get());
```

- [x] HELLO WORLD
- [ ] Hello World
- [ ] HELLO
- [ ] Hello

> **Giải thích:** supplyAsync: "Hello". thenApply: "Hello World". thenApply: "HELLO WORLD". Chain of async transformations.

## Câu 12

[TYPE: TRUE_FALSE]

Mệnh đề: "Deadlock xảy ra khi 2+ threads chờ nhau release locks mà cả hai đều đang hold."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Deadlock: T1 holds A, waits B. T2 holds B, waits A. Cả hai chờ vô hạn. Tránh: lock ordering, tryLock timeout, avoid nested locks.

## Câu 13

[TYPE: SELECT_RESULT]

```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
map.put("a", 1);
map.put("b", 2);
map.compute("a", (k, v) -> v + 10);
map.computeIfAbsent("c", k -> 3);
map.computeIfPresent("b", (k, v) -> v * 2);
System.out.println(map);
```

- [x] {a=11, b=4, c=3}
- [ ] {a=1, b=2}
- [ ] {a=11, b=4}
- [ ] Lỗi ConcurrentModificationException

> **Giải thích:** compute("a"): 1+10=11. computeIfAbsent("c"): not present → 3. computeIfPresent("b"): present → 2*2=4. Thread-safe operations.

## Câu 14

[TYPE: MULTIPLE_CHOICE]

ExecutorService types?

- [x] newFixedThreadPool, newCachedThreadPool, newSingleThreadExecutor, newScheduledThreadPool
- [ ] Chỉ FixedThreadPool
- [ ] Chỉ CachedThreadPool
- [ ] ThreadPool and ThreadGroup

> **Giải thích:** Fixed: fixed number threads. Cached: grow/shrink on demand. Single: 1 thread. Scheduled: delayed/periodic tasks. Virtual threads (Java 21).

## Câu 15

[TYPE: SELECT_RESULT]

```java
CountDownLatch latch = new CountDownLatch(3);
for (int i = 0; i < 3; i++) {
    final int id = i;
    new Thread(() -> {
        System.out.print("T" + id + " ");
        latch.countDown();
    }).start();
}
latch.await();
System.out.print("Done");
```

- [x] T0 T1 T2 Done (thứ tự T có thể khác nhưng Done luôn cuối)
- [ ] Done T0 T1 T2
- [ ] Random hoàn toàn
- [ ] Deadlock

> **Giải thích:** CountDownLatch(3): await blocks until count=0. Each thread countDown(). After all 3 → await releases → "Done". Done always last.

## Câu 16

[TYPE: SELECT_RESULT]

```java
Semaphore semaphore = new Semaphore(2);
AtomicInteger concurrent = new AtomicInteger(0);
AtomicInteger maxConcurrent = new AtomicInteger(0);
ExecutorService executor = Executors.newFixedThreadPool(5);
for (int i = 0; i < 5; i++) {
    executor.submit(() -> {
        try {
            semaphore.acquire();
            int cur = concurrent.incrementAndGet();
            maxConcurrent.updateAndGet(max -> Math.max(max, cur));
            Thread.sleep(100);
            concurrent.decrementAndGet();
            semaphore.release();
        } catch (Exception e) {}
    });
}
executor.shutdown();
executor.awaitTermination(5, TimeUnit.SECONDS);
System.out.println(maxConcurrent.get() <= 2);
```

- [x] true
- [ ] false
- [ ] Lỗi
- [ ] 5

> **Giải thích:** Semaphore(2): max 2 threads concurrent. 5 tasks but chỉ 2 chạy đồng thời. maxConcurrent ≤ 2 → true. Rate limiting.

## Câu 17

[TYPE: FILL_BLANK]

`CyclicBarrier` cho phép N threads chờ nhau tại `___` point trước khi tiếp tục.

- [x] barrier (rào chắn)
- [ ] start
- [ ] end
- [ ] lock

> **Giải thích:** CyclicBarrier(N): N threads call await() → all block until N threads arrive → all released. Cyclic: reusable. Phased computation.

## Câu 18

[TYPE: SELECT_RESULT]

```java
ReadWriteLock rwLock = new ReentrantReadWriteLock();
List<String> list = new ArrayList<>();

// Writer
rwLock.writeLock().lock();
try {
    list.add("item");
} finally {
    rwLock.writeLock().unlock();
}

// Reader
rwLock.readLock().lock();
try {
    System.out.println(list.size());
} finally {
    rwLock.readLock().unlock();
}
```

- [x] 1
- [ ] 0
- [ ] Lỗi
- [ ] Deadlock

> **Giải thích:** WriteLock: exclusive access → add "item". ReadLock: shared access → read size=1. Multiple readers allowed, writers exclusive.

## Câu 19

[TYPE: SELECT_RESULT]

```java
CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "Hello");
CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> "World");
CompletableFuture<String> combined = cf1.thenCombine(cf2, (s1, s2) -> s1 + " " + s2);
System.out.println(combined.get());
```

- [x] Hello World
- [ ] Hello
- [ ] World
- [ ] Lỗi

> **Giải thích:** thenCombine: combine results of 2 async operations. cf1="Hello", cf2="World". Combined: "Hello World". Both run concurrently.

## Câu 20

[TYPE: TRUE_FALSE]

Mệnh đề: "Thread.sleep() releases lock, Object.wait() cũng releases lock."

- [ ] Đúng
- [x] Sai

> **Giải thích:** sleep(): KHÔNG release lock, thread vẫn hold monitor. wait(): RELEASE lock, thread enters waiting state. Khác biệt quan trọng!

## Câu 21

[TYPE: SELECT_RESULT]

```java
BlockingQueue<String> queue = new LinkedBlockingQueue<>(2);
queue.offer("A");
queue.offer("B");
boolean added = queue.offer("C"); // queue full
System.out.println(added);
System.out.println(queue.poll());
System.out.println(queue.poll());
```

- [x] false, A, B
- [ ] true, A, B
- [ ] false, B, C
- [ ] Lỗi

> **Giải thích:** Capacity 2. offer A, B → full. offer C → false (not added). poll: FIFO → A, B. BlockingQueue: thread-safe, bounded.

## Câu 22

[TYPE: SELECT_RESULT]

```java
ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
AtomicInteger count = new AtomicInteger(0);
ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(
    () -> count.incrementAndGet(), 0, 100, TimeUnit.MILLISECONDS);
Thread.sleep(350);
future.cancel(false);
scheduler.shutdown();
System.out.println(count.get() >= 3);
```

- [x] true
- [ ] false
- [ ] Lỗi
- [ ] 0

> **Giải thích:** scheduleAtFixedRate: every 100ms starting at 0. After 350ms: ~4 executions (0, 100, 200, 300). count >= 3 → true.

## Câu 23

[TYPE: MULTIPLE_CHOICE]

CopyOnWriteArrayList vs synchronized List?

- [x] CopyOnWrite: copy array on write, no lock on read; synchronized: lock on every access
- [ ] Giống nhau
- [ ] Synchronized nhanh hơn
- [ ] CopyOnWrite lock on read

> **Giải thích:** CopyOnWrite: snapshot iterator (no ConcurrentModificationException), fast reads, slow writes (copy). Best: read-heavy, few writes.

## Câu 24

[TYPE: SELECT_RESULT]

```java
CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(List.of("A", "B", "C"));
for (String s : list) {
    if (s.equals("B")) list.add("D");
    System.out.print(s + " ");
}
System.out.print("size=" + list.size());
```

- [x] A B C size=4
- [ ] ConcurrentModificationException
- [ ] A B D C size=4
- [ ] A B C D size=4

> **Giải thích:** CopyOnWriteArrayList: iterator uses snapshot. add("D") creates new array copy. Iterator still sees original [A,B,C]. After loop: [A,B,C,D] size=4.

## Câu 25

[TYPE: FILL_BLANK]

`CompletableFuture.___()` chạy task async và trả về CompletableFuture<Void>.

- [x] runAsync
- [ ] supplyAsync
- [ ] executeAsync
- [ ] startAsync

> **Giải thích:** runAsync(Runnable): no return value → CompletableFuture<Void>. supplyAsync(Supplier<T>): có return value → CompletableFuture<T>.

## Câu 26

[TYPE: SELECT_RESULT]

```java
CompletableFuture<Void> all = CompletableFuture.allOf(
    CompletableFuture.runAsync(() -> System.out.print("A ")),
    CompletableFuture.runAsync(() -> System.out.print("B ")),
    CompletableFuture.runAsync(() -> System.out.print("C "))
);
all.get();
System.out.print("Done");
```

- [x] A B C Done (thứ tự A/B/C có thể khác, Done luôn cuối)
- [ ] Done A B C
- [ ] Chỉ Done
- [ ] Lỗi

> **Giải thích:** allOf: chờ tất cả complete. 3 async tasks chạy concurrent. Order không đảm bảo. "Done" sau khi tất cả xong.

## Câu 27

[TYPE: SELECT_RESULT]

```java
CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
    throw new RuntimeException("error");
}).exceptionally(ex -> "fallback: " + ex.getMessage());
System.out.println(cf.get());
```

- [x] fallback: java.lang.RuntimeException: error
- [ ] RuntimeException
- [ ] null
- [ ] ExecutionException

> **Giải thích:** exceptionally: handle exceptions. Exception wrapped in CompletionException. getMessage includes original exception. Returns fallback value.

## Câu 28

[TYPE: MULTIPLE_CHOICE]

ForkJoinPool dùng cho:

- [x] Divide-and-conquer tasks, work-stealing algorithm, parallel streams backend
- [ ] Chỉ cho sorting
- [ ] Thay thế ExecutorService
- [ ] I/O tasks

> **Giải thích:** ForkJoinPool: RecursiveTask (return value), RecursiveAction (no return). Work-stealing: idle threads steal from busy threads. Default pool for parallel streams.

## Câu 29

[TYPE: SELECT_RESULT]

```java
ForkJoinPool pool = ForkJoinPool.commonPool();
System.out.println(pool.getParallelism() > 0);

long sum = LongStream.rangeClosed(1, 1000000)
    .parallel()
    .sum();
System.out.println(sum);
```

- [x] true và 500000500000
- [ ] false và 500000500000
- [ ] true và 0
- [ ] Lỗi

> **Giải thích:** commonPool: parallelism = cores - 1. parallel stream: uses ForkJoinPool. Sum 1..1000000 = 500000500000. Parallel safe cho stateless ops.

## Câu 30

[TYPE: TRUE_FALSE]

Mệnh đề: "ThreadLocal cung cấp biến riêng cho mỗi thread, không shared giữa các threads."

- [x] Đúng
- [ ] Sai

> **Giải thích:** ThreadLocal<T>: mỗi thread có bản copy riêng. get/set per thread. Dùng cho: user context, database connection, date formatters.

## Câu 31

[TYPE: SELECT_RESULT]

```java
ThreadLocal<String> context = new ThreadLocal<>();
Thread t1 = new Thread(() -> {
    context.set("T1-value");
    System.out.print(context.get() + " ");
});
Thread t2 = new Thread(() -> {
    context.set("T2-value");
    System.out.print(context.get() + " ");
});
t1.start(); t1.join();
t2.start(); t2.join();
System.out.print(context.get());
```

- [x] T1-value T2-value null
- [ ] T1-value T2-value T2-value
- [ ] T1-value T2-value T1-value
- [ ] Lỗi

> **Giải thích:** Mỗi thread có riêng value. Main thread: chưa set → null. T1: "T1-value". T2: "T2-value". ThreadLocal per-thread isolation.

## Câu 32

[TYPE: SELECT_RESULT]

```java
Phaser phaser = new Phaser(3);
for (int i = 0; i < 3; i++) {
    final int id = i;
    new Thread(() -> {
        System.out.print("P" + phaser.getPhase() + " ");
        phaser.arriveAndAwaitAdvance();
        System.out.print("P" + phaser.getPhase() + " ");
        phaser.arriveAndDeregister();
    }).start();
}
```

- [x] P0 P0 P0 P1 P1 P1 (thứ tự trong mỗi phase có thể khác)
- [ ] P0 P1 P0 P1 P0 P1
- [ ] Deadlock
- [ ] Lỗi

> **Giải thích:** Phaser: flexible barrier. Phase 0: all 3 arrive → advance to phase 1. Phase 1: all print P1 → deregister. Phase-based synchronization.

## Câu 33

[TYPE: FILL_BLANK]

`Exchanger<T>` cho phép 2 threads `___` data với nhau tại synchronization point.

- [x] exchange (trao đổi)
- [ ] share
- [ ] transfer
- [ ] sync

> **Giải thích:** Exchanger: thread A exchange(objA), thread B exchange(objB). A gets objB, B gets objA. Bidirectional data exchange. Producer-consumer variant.

## Câu 34

[TYPE: SELECT_RESULT]

```java
AtomicReference<String> ref = new AtomicReference<>("Hello");
boolean updated = ref.compareAndSet("Hello", "World");
System.out.println(updated + " " + ref.get());
updated = ref.compareAndSet("Hello", "Java");
System.out.println(updated + " " + ref.get());
```

- [x] true World và false World
- [ ] true World và true Java
- [ ] false Hello và false Hello
- [ ] true World và true Java

> **Giải thích:** CAS(expected="Hello", new="World"): current is "Hello" → success, set "World". CAS("Hello", "Java"): current is "World" ≠ "Hello" → fail.

## Câu 35

[TYPE: MULTIPLE_CHOICE]

StampedLock (Java 8) advantages?

- [x] Optimistic read (no lock), read/write locks, better performance than ReentrantReadWriteLock
- [ ] Simpler API
- [ ] Thay thế synchronized
- [ ] Chỉ cho write

> **Giải thích:** StampedLock: tryOptimisticRead (no blocking), readLock, writeLock. Optimistic read: validate after read. Better throughput. Not reentrant.

## Câu 36

[TYPE: SELECT_RESULT]

```java
ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
queue.offer(1);
queue.offer(2);
queue.offer(3);
System.out.println(queue.peek());
System.out.println(queue.poll());
System.out.println(queue.poll());
System.out.println(queue.size());
```

- [x] 1, 1, 2, 1
- [ ] 3, 3, 2, 1
- [ ] 1, 1, 2, 0
- [ ] 1, 2, 3, 0

> **Giải thích:** peek: view head without remove → 1. poll: remove head → 1. poll → 2. Remaining: [3], size=1. FIFO, lock-free.

## Câu 37

[TYPE: SELECT_RESULT]

```java
ExecutorService executor = Executors.newFixedThreadPool(3);
List<Callable<String>> tasks = List.of(
    () -> { Thread.sleep(300); return "slow"; },
    () -> { Thread.sleep(100); return "fast"; },
    () -> { Thread.sleep(200); return "medium"; }
);
String first = executor.invokeAny(tasks);
System.out.println(first);
executor.shutdown();
```

- [x] fast
- [ ] slow
- [ ] medium
- [ ] Random

> **Giải thích:** invokeAny: return result of fastest task. "fast" (100ms) finishes first. Others cancelled. Useful for redundant requests.

## Câu 38

[TYPE: TRUE_FALSE]

Mệnh đề: "Java 21 Virtual Threads (Project Loom) là lightweight threads managed by JVM, not OS."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Virtual Threads: JVM-managed, millions possible. Platform threads: OS-managed, limited. Thread.ofVirtual().start(). Executors.newVirtualThreadPerTaskExecutor().

## Câu 39

[TYPE: SELECT_RESULT]

```java
// Java 21
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    var futures = new ArrayList<Future<String>>();
    for (int i = 0; i < 5; i++) {
        final int id = i;
        futures.add(executor.submit(() -> "VT-" + id));
    }
    for (var f : futures) System.out.print(f.get() + " ");
}
```

- [x] VT-0 VT-1 VT-2 VT-3 VT-4
- [ ] Lỗi biên dịch
- [ ] Random order
- [ ] Lỗi runtime

> **Giải thích:** Virtual threads: lightweight. newVirtualThreadPerTaskExecutor: mỗi task 1 virtual thread. Future.get() in order → VT-0..VT-4.

## Câu 40

[TYPE: SELECT_RESULT]

```java
var results = new ConcurrentHashMap<String, Integer>();
var executor = Executors.newFixedThreadPool(4);
var latch = new CountDownLatch(4);
List.of("A", "B", "C", "D").forEach(key -> {
    executor.submit(() -> {
        results.put(key, key.charAt(0) - 'A' + 1);
        latch.countDown();
    });
});
latch.await();
executor.shutdown();
System.out.println(results.size());
```

- [x] 4
- [ ] 0
- [ ] Random
- [ ] Lỗi

> **Giải thích:** 4 tasks → 4 entries: A=1, B=2, C=3, D=4. CountDownLatch ensures all complete. ConcurrentHashMap thread-safe. size=4.

## Câu 41

[TYPE: FILL_BLANK]

`LockSupport.___()` blocks thread cho đến khi unpark.

- [x] park
- [ ] wait
- [ ] sleep
- [ ] block

> **Giải thích:** LockSupport.park(): block current thread. LockSupport.unpark(thread): unblock specific thread. Low-level synchronization primitive. Basis for locks, conditions.

## Câu 42

[TYPE: MULTIPLE_CHOICE]

wait() / notify() / notifyAll() rules?

- [x] Phải gọi trong synchronized block/method trên cùng object
- [ ] Gọi ở bất kỳ đâu
- [ ] Chỉ trong main thread
- [ ] Không cần synchronized

> **Giải thích:** wait/notify: phải own monitor (synchronized). IllegalMonitorStateException nếu không synchronized. notify: wake 1 waiting thread. notifyAll: wake all.

## Câu 43

[TYPE: SELECT_RESULT]

```java
Object lock = new Object();
Thread producer = new Thread(() -> {
    synchronized (lock) {
        System.out.print("Producing ");
        lock.notify();
    }
});
Thread consumer = new Thread(() -> {
    synchronized (lock) {
        try {
            lock.wait(1000); // wait with timeout
            System.out.print("Consumed ");
        } catch (InterruptedException e) {}
    }
});
consumer.start();
Thread.sleep(100); // ensure consumer starts first
producer.start();
consumer.join();
producer.join();
```

- [x] Producing Consumed
- [ ] Consumed Producing
- [ ] Deadlock
- [ ] Lỗi

> **Giải thích:** Consumer starts, waits. Producer produces, notifies. Consumer wakes, continues. wait releases lock → producer can enter synchronized.

## Câu 44

[TYPE: SELECT_RESULT]

```java
Condition condition = new ReentrantLock().newCondition();
// This code is outside of lock
try {
    condition.await();
} catch (IllegalMonitorStateException e) {
    System.out.println("Must hold lock first");
} catch (InterruptedException e) {}
```

- [x] Must hold lock first
- [ ] Thread waits forever
- [ ] null
- [ ] Lỗi biên dịch

> **Giải thích:** Condition.await(): phải hold associated lock. Giống wait() cần synchronized. IllegalMonitorStateException khi gọi ngoài lock.

## Câu 45

[TYPE: SELECT_RESULT]

```java
CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> "Hello")
    .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"))
    .thenApply(String::toLowerCase);
System.out.println(cf.get());
```

- [x] hello world
- [ ] Hello World
- [ ] HELLO WORLD
- [ ] Hello

> **Giải thích:** supplyAsync: "Hello". thenCompose: flatMap → "Hello World". thenApply: toLowerCase → "hello world". thenCompose vs thenApply: flatMap vs map.

## Câu 46

[TYPE: TRUE_FALSE]

Mệnh đề: "Thread.interrupt() KHÔNG dừng thread ngay lập tức, chỉ set interrupt flag."

- [x] Đúng
- [ ] Sai

> **Giải thích:** interrupt(): set flag. Thread phải check Thread.interrupted() hoặc isInterrupted(). sleep/wait/join throw InterruptedException when interrupted. Cooperative cancellation.

## Câu 47

[TYPE: SELECT_RESULT]

```java
Thread t = new Thread(() -> {
    while (!Thread.currentThread().isInterrupted()) {
        // working...
    }
    System.out.print("Interrupted!");
});
t.start();
Thread.sleep(100);
t.interrupt();
t.join();
```

- [x] Interrupted!
- [ ] Vòng lặp vô hạn
- [ ] Lỗi runtime
- [ ] Không in gì

> **Giải thích:** isInterrupted(): check flag. interrupt(): set flag. Loop exits → print "Interrupted!". Cooperative interruption pattern.

## Câu 48

[TYPE: SELECT_RESULT]

```java
ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
map.put("banana", 2);
map.put("apple", 1);
map.put("cherry", 3);
System.out.println(map.firstKey());
System.out.println(map.lastKey());
System.out.println(map); // sorted
```

- [x] apple, cherry, {apple=1, banana=2, cherry=3}
- [ ] banana, cherry, {banana=2, apple=1, cherry=3}
- [ ] apple, cherry, {apple=1, cherry=3, banana=2}
- [ ] Lỗi

> **Giải thích:** ConcurrentSkipListMap: sorted, concurrent NavigableMap. firstKey=apple. lastKey=cherry. Entries in natural order.

## Câu 49

[TYPE: MULTIPLE_CHOICE]

CompletableFuture: thenApply vs thenCompose vs thenAccept?

- [x] thenApply: transform (Function); thenCompose: flatMap (returns CF); thenAccept: consume (Consumer, returns Void)
- [ ] Tất cả giống nhau
- [ ] thenApply returns void
- [ ] thenCompose cho sync

> **Giải thích:** thenApply: T→R, returns CF<R>. thenCompose: T→CF<R>, unwrap (flatMap). thenAccept: T→void, returns CF<Void>. thenRun: Runnable, no input.

## Câu 50

[TYPE: SELECT_RESULT]

```java
CompletableFuture<String> cf = CompletableFuture.anyOf(
    CompletableFuture.supplyAsync(() -> { sleep(300); return "slow"; }),
    CompletableFuture.supplyAsync(() -> { sleep(100); return "fast"; }),
    CompletableFuture.supplyAsync(() -> { sleep(200); return "medium"; })
).thenApply(Object::toString);
System.out.println(cf.get());
```

static void sleep(long ms) { try { Thread.sleep(ms); } catch (Exception e) {} }

- [x] fast
- [ ] slow
- [ ] medium
- [ ] Random

> **Giải thích:** anyOf: return first completed. "fast" (100ms) completes first. Other tasks still running but result already determined.

## Câu 51

[TYPE: FILL_BLANK]

`Executors.newWorkStealingPool()` tạo `___` pool sử dụng work-stealing algorithm.

- [x] ForkJoin
- [ ] Fixed
- [ ] Cached
- [ ] Virtual

> **Giải thích:** newWorkStealingPool(): ForkJoinPool with parallelism = processors. Work-stealing: idle threads steal tasks from busy threads' deques.

## Câu 52

[TYPE: SELECT_RESULT]

```java
ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
queue.add(1);
queue.add(2);
queue.add(3);
try {
    queue.add(4); // queue full
} catch (IllegalStateException e) {
    System.out.print("Full ");
}
System.out.print(queue.take() + " ");
queue.put(4); // blocks if full
System.out.print(queue.size());
```

- [x] Full 1 3
- [ ] Full 2 3
- [ ] Lỗi runtime
- [ ] Full 1 4

> **Giải thích:** add(4) on full queue → ISE "Full". take() → 1 (FIFO, removes). put(4) → now has space → [2,3,4]. size=3.

## Câu 53

[TYPE: SELECT_RESULT]

```java
AtomicLong counter = new AtomicLong(10);
long prev = counter.getAndAdd(5);
long curr = counter.addAndGet(3);
System.out.println(prev + " " + curr);
```

- [x] 10 18
- [ ] 15 18
- [ ] 10 15
- [ ] 5 3

> **Giải thích:** getAndAdd(5): return old(10), set 15. addAndGet(3): set 18, return new(18). Atomic operations.

## Câu 54

[TYPE: TRUE_FALSE]

Mệnh đề: "daemon threads terminate khi tất cả non-daemon threads kết thúc."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Daemon: background threads (GC, finalizer). JVM exits khi no non-daemon threads. setDaemon(true) trước start(). Default: non-daemon.

## Câu 55

[TYPE: SELECT_RESULT]

```java
ReentrantLock lock = new ReentrantLock(true); // fair lock
System.out.println(lock.isFair());
lock.lock();
System.out.println(lock.isLocked());
System.out.println(lock.isHeldByCurrentThread());
lock.unlock();
System.out.println(lock.isLocked());
```

- [x] true, true, true, false
- [ ] false, true, true, false
- [ ] true, false, true, false
- [ ] true, true, false, false

> **Giải thích:** Fair lock: FIFO ordering. isFair=true. After lock: isLocked=true, isHeldByCurrentThread=true. After unlock: isLocked=false.

## Câu 56

[TYPE: MULTIPLE_CHOICE]

Difference: synchronized vs ReentrantLock?

- [x] Lock: tryLock, timeout, fairness, interruptible, multiple conditions; synchronized: simpler, auto-release
- [ ] Giống nhau
- [ ] synchronized nhanh hơn
- [ ] Lock deprecated

> **Giải thích:** Lock: explicit lock/unlock, tryLock(timeout), lockInterruptibly(), fair mode, newCondition(). synchronized: implicit, auto-release, simpler.

## Câu 57

[TYPE: SELECT_RESULT]

```java
ReentrantLock lock = new ReentrantLock();
boolean acquired = lock.tryLock();
System.out.println(acquired);
if (acquired) {
    try {
        System.out.println("In critical section");
    } finally {
        lock.unlock();
    }
}
```

- [x] true và In critical section
- [ ] false
- [ ] Lỗi
- [ ] Deadlock

> **Giải thích:** tryLock(): non-blocking attempt. Lock available → true. Execute critical section. finally: always unlock.

## Câu 58

[TYPE: SELECT_RESULT]

```java
CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> "Result")
    .whenComplete((result, error) -> {
        System.out.print("Complete: " + result + " ");
    })
    .thenApply(s -> s.toUpperCase());
System.out.print(cf.get());
```

- [x] Complete: Result RESULT
- [ ] RESULT Complete: Result
- [ ] Complete: Result Result
- [ ] RESULT

> **Giải thích:** whenComplete: execute on completion (does not transform). print "Complete: Result ". thenApply: "RESULT". get: "RESULT".

## Câu 59

[TYPE: SELECT_RESULT]

```java
LongAdder adder = new LongAdder();
ExecutorService exec = Executors.newFixedThreadPool(4);
for (int i = 0; i < 10000; i++) {
    exec.submit(adder::increment);
}
exec.shutdown();
exec.awaitTermination(5, TimeUnit.SECONDS);
System.out.println(adder.sum());
```

- [x] 10000
- [ ] < 10000
- [ ] 0
- [ ] Lỗi

> **Giải thích:** LongAdder: high-contention atomic counter. increment() thread-safe. 10000 increments → sum=10000. Better than AtomicLong under high contention.

## Câu 60

[TYPE: FILL_BLANK]

`Thread.___()` trả về reference đến thread hiện đang execute.

- [x] currentThread
- [ ] this
- [ ] getThread
- [ ] self

> **Giải thích:** Thread.currentThread(): reference to running thread. getName(), getId(), getState(), isAlive(), getPriority().

## Câu 61

[TYPE: SELECT_RESULT]

```java
SynchronousQueue<String> queue = new SynchronousQueue<>();
new Thread(() -> {
    try {
        queue.put("Hello"); // blocks until taken
        System.out.print("Sent ");
    } catch (InterruptedException e) {}
}).start();
Thread.sleep(100);
System.out.print(queue.take() + " ");
Thread.sleep(100); // wait for "Sent"
```

- [x] Hello Sent
- [ ] Sent Hello
- [ ] Deadlock
- [ ] null

> **Giải thích:** SynchronousQueue: no capacity. put blocks until take. take() → "Hello", then put unblocks → "Sent". Direct handoff.

## Câu 62

[TYPE: SELECT_RESULT]

```java
var flag = new AtomicBoolean(false);
Thread t = new Thread(() -> {
    while (!flag.get()) { /* busy wait */ }
    System.out.print("Flag set!");
});
t.start();
Thread.sleep(100);
flag.set(true);
t.join();
```

- [x] Flag set!
- [ ] Infinite loop
- [ ] Lỗi
- [ ] Không in gì

> **Giải thích:** AtomicBoolean: thread-safe flag. Main thread sets true after 100ms. Worker thread sees update → exit loop → print. Visibility guaranteed.

## Câu 63

[TYPE: MULTIPLE_CHOICE]

Structured Concurrency (Java 21 preview)?

- [x] StructuredTaskScope: manage related tasks as unit, auto-cancel on failure
- [ ] Chỉ thread pools
- [ ] Virtual threads only
- [ ] Replaces CompletableFuture

> **Giải thích:** StructuredTaskScope: fork subtasks, join all/any. ShutdownOnFailure: cancel all on first failure. ShutdownOnSuccess: cancel remaining on first success. Clean cancellation.

## Câu 64

[TYPE: SELECT_RESULT]

```java
CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> "Hello")
    .thenApplyAsync(s -> s + " World") // runs on different thread
    .thenApplyAsync(s -> s + "!");
System.out.println(cf.get());
```

- [x] Hello World!
- [ ] Hello
- [ ] Hello World
- [ ] Lỗi

> **Giải thích:** thenApplyAsync: execute on different thread (from ForkJoinPool). Chain: "Hello" → "Hello World" → "Hello World!". Async execution.

## Câu 65

[TYPE: SELECT_RESULT]

```java
List<Integer> list = Collections.synchronizedList(new ArrayList<>());
list.add(1); list.add(2); list.add(3);
synchronized (list) {
    for (int n : list) System.out.print(n + " ");
}
```

- [x] 1 2 3
- [ ] ConcurrentModificationException
- [ ] Random order
- [ ] Lỗi biên dịch

> **Giải thích:** synchronizedList: individual ops thread-safe. Iteration: PHẢI manually synchronized. Without sync block → potential CME.

## Câu 66

[TYPE: TRUE_FALSE]

Mệnh đề: "Future.get() blocks calling thread cho đến khi result available hoặc timeout."

- [x] Đúng
- [ ] Sai

> **Giải thích:** get(): blocks indefinitely. get(timeout, unit): blocks with timeout → TimeoutException. isDone(): non-blocking check. cancel(): attempt to cancel.

## Câu 67

[TYPE: SELECT_RESULT]

```java
DelayQueue<Delayed> queue = new DelayQueue<>();
// Add items with different delays
long now = System.nanoTime();
System.out.println(queue.size() == 0);
// DelayQueue only releases elements after their delay expires
```

- [x] true
- [ ] false
- [ ] Lỗi
- [ ] null

> **Giải thích:** DelayQueue: elements available only after delay expires. Empty queue → size=0 → true. Used for scheduled tasks, caching expiry.

## Câu 68

[TYPE: SELECT_RESULT]

```java
Map<String, String> map = new ConcurrentHashMap<>();
map.put("key", "value1");
map.putIfAbsent("key", "value2");
map.putIfAbsent("key2", "value3");
System.out.println(map.get("key") + " " + map.get("key2"));
```

- [x] value1 value3
- [ ] value2 value3
- [ ] value1 null
- [ ] value2 null

> **Giải thích:** putIfAbsent("key"): key exists → no change (value1). putIfAbsent("key2"): absent → insert value3. Thread-safe conditional put.

## Câu 69

[TYPE: FILL_BLANK]

`CompletableFuture.___()` tạo already-completed future.

- [x] completedFuture
- [ ] of
- [ ] done
- [ ] finished

> **Giải thích:** completedFuture(value): pre-completed CF. completedStage(value): CompletionStage. failedFuture(ex): pre-failed CF (Java 9).

## Câu 70

[TYPE: SELECT_RESULT]

```java
CompletableFuture<Integer> cf1 = CompletableFuture.completedFuture(10);
CompletableFuture<Integer> cf2 = CompletableFuture.completedFuture(20);
CompletableFuture<Integer> cf3 = cf1.thenCombine(cf2, Integer::sum);
System.out.println(cf3.get());
```

- [x] 30
- [ ] 10
- [ ] 20
- [ ] Lỗi

> **Giải thích:** completedFuture: pre-completed. thenCombine: combine cf1(10) + cf2(20) = 30. sum.

## Câu 71

[TYPE: MULTIPLE_CHOICE]

Happens-before relationship đảm bảo:

- [x] Visibility: action A happens-before B → A's writes visible to B
- [ ] Chỉ ordering
- [ ] Chỉ cho volatile
- [ ] Không liên quan concurrency

> **Giải thích:** Happens-before: JMM guarantee. unlock → lock, volatile write → read, thread start → run, interrupt → detection. Ensures memory visibility.

## Câu 72

[TYPE: SELECT_RESULT]

```java
ExecutorService executor = Executors.newFixedThreadPool(2);
List<Future<String>> futures = executor.invokeAll(List.of(
    () -> "Task1",
    () -> "Task2",
    () -> "Task3"
));
for (Future<String> f : futures) System.out.print(f.get() + " ");
executor.shutdown();
```

- [x] Task1 Task2 Task3
- [ ] Random order
- [ ] Task1
- [ ] Lỗi

> **Giải thích:** invokeAll: execute all, return when ALL complete. Futures in submission order. get() returns result. Order preserved.

## Câu 73

[TYPE: SELECT_RESULT]

```java
Callable<String> task = () -> {
    Thread.sleep(5000);
    return "Done";
};
ExecutorService exec = Executors.newSingleThreadExecutor();
Future<String> future = exec.submit(task);
try {
    String result = future.get(1, TimeUnit.SECONDS);
} catch (TimeoutException e) {
    System.out.println("Timeout!");
    future.cancel(true);
}
exec.shutdown();
```

- [x] Timeout!
- [ ] Done
- [ ] Lỗi biên dịch
- [ ] Vòng lặp vô hạn

> **Giải thích:** Task sleeps 5s. get(1s) timeout: 1s < 5s → TimeoutException. Cancel task. Timeout handling pattern.

## Câu 74

[TYPE: TRUE_FALSE]

Mệnh đề: "ConcurrentHashMap KHÔNG cho phép null keys hoặc null values."

- [x] Đúng
- [ ] Sai

> **Giải thích:** ConcurrentHashMap: NullPointerException cho null key/value. HashMap: allows 1 null key, multiple null values. Design decision for concurrent operations.

## Câu 75

[TYPE: SELECT_RESULT]

```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
map.put("a", 1);
map.put("b", 2);
map.put("c", 3);
map.forEach(1, (k, v) -> System.out.print(k + "=" + v + " "));
// parallelismThreshold = 1 means always parallel
```

- [x] a=1 b=2 c=3 (thứ tự có thể khác)
- [ ] ConcurrentModificationException
- [ ] Lỗi biên dịch
- [ ] null

> **Giải thích:** ConcurrentHashMap.forEach(parallelism, action): bulk operation. parallelism=1: use common pool. Thread-safe iteration.

## Câu 76

[TYPE: SELECT_RESULT]

```java
var map = new ConcurrentHashMap<String, AtomicInteger>();
String[] words = {"apple", "banana", "apple", "cherry", "apple"};
for (String w : words) {
    map.computeIfAbsent(w, k -> new AtomicInteger(0)).incrementAndGet();
}
System.out.println(map.get("apple").get());
```

- [x] 3
- [ ] 1
- [ ] 0
- [ ] 5

> **Giải thích:** computeIfAbsent + incrementAndGet: thread-safe word count. apple appears 3 times. AtomicInteger for thread-safe increment.

## Câu 77

[TYPE: MULTIPLE_CHOICE]

RejectedExecutionHandler policies?

- [x] AbortPolicy (throw), CallerRunsPolicy (run in caller), DiscardPolicy (silent drop), DiscardOldestPolicy (drop oldest)
- [ ] Chỉ AbortPolicy
- [ ] Chỉ retry
- [ ] No policies

> **Giải thích:** ThreadPoolExecutor: when queue full + max threads reached. Default: AbortPolicy (RejectedExecutionException). Custom handler possible.

## Câu 78

[TYPE: SELECT_RESULT]

```java
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    2, 4, 60, TimeUnit.SECONDS,
    new ArrayBlockingQueue<>(2));
System.out.println("Core: " + executor.getCorePoolSize());
System.out.println("Max: " + executor.getMaximumPoolSize());
executor.shutdown();
```

- [x] Core: 2 và Max: 4
- [ ] Core: 4 và Max: 2
- [ ] Core: 2 và Max: 2
- [ ] Lỗi

> **Giải thích:** ThreadPoolExecutor(core=2, max=4, keepAlive=60s, queue). core: minimum threads. max: maximum threads when queue full.

## Câu 79

[TYPE: SELECT_RESULT]

```java
CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> "Hello")
    .handle((result, ex) -> {
        if (ex != null) return "Error";
        return result + " Handled";
    });
System.out.println(cf.get());
```

- [x] Hello Handled
- [ ] Hello
- [ ] Error
- [ ] null

> **Giải thích:** handle: always called (success or failure). No exception → result="Hello", ex=null. Return "Hello Handled". Unlike exceptionally (only on error).

## Câu 80

[TYPE: FILL_BLANK]

`ThreadFactory` interface dùng để customize `___` creation.

- [x] thread
- [ ] pool
- [ ] executor
- [ ] task

> **Giải thích:** ThreadFactory.newThread(Runnable): create customized threads. Set name, daemon, priority, uncaughtExceptionHandler. Used by ExecutorService.

## Câu 81

[TYPE: SELECT_RESULT]

```java
Thread t = Thread.ofVirtual().name("vt-1").start(() -> {
    System.out.print(Thread.currentThread().isVirtual() + " ");
    System.out.print(Thread.currentThread().getName());
});
t.join();
```

Java 21:

- [x] true vt-1
- [ ] false vt-1
- [ ] true Thread-0
- [ ] Lỗi

> **Giải thích:** Thread.ofVirtual(): virtual thread builder. name("vt-1"): set name. isVirtual()=true. Lightweight, JVM-managed.

## Câu 82

[TYPE: SELECT_RESULT]

```java
TransferQueue<String> queue = new LinkedTransferQueue<>();
new Thread(() -> {
    try {
        queue.transfer("Data"); // blocks until received
        System.out.print("Transferred ");
    } catch (InterruptedException e) {}
}).start();
Thread.sleep(100);
System.out.print(queue.take() + " ");
Thread.sleep(100);
```

- [x] Data Transferred
- [ ] Transferred Data
- [ ] Deadlock
- [ ] Lỗi

> **Giải thích:** transfer(): blocks until consumer takes. take() → "Data". Then transfer() unblocks → "Transferred". Direct producer-consumer handoff.

## Câu 83

[TYPE: TRUE_FALSE]

Mệnh đề: "AtomicStampedReference giải quyết ABA problem bằng cách kèm theo stamp (version number)."

- [x] Đúng
- [ ] Sai

> **Giải thích:** ABA: value A→B→A, CAS thinks unchanged. AtomicStampedReference: compareAndSet(expectedRef, newRef, expectedStamp, newStamp). Stamp prevents ABA.

## Câu 84

[TYPE: SELECT_RESULT]

```java
var lock = new ReentrantLock();
var condition = lock.newCondition();
var message = new AtomicReference<String>("");

Thread waiter = new Thread(() -> {
    lock.lock();
    try {
        condition.await();
        System.out.print(message.get());
    } catch (InterruptedException e) {}
    finally { lock.unlock(); }
});
waiter.start();
Thread.sleep(100);
lock.lock();
try {
    message.set("Signaled!");
    condition.signal();
} finally { lock.unlock(); }
waiter.join();
```

- [x] Signaled!
- [ ] null
- [ ] Deadlock
- [ ] Lỗi

> **Giải thích:** Condition: await releases lock, signal wakes waiter. Waiter awaits → producer sets message + signals → waiter wakes → print. Lock-based wait/notify.

## Câu 85

[TYPE: SELECT_RESULT]

```java
Executors.newSingleThreadExecutor().submit(() -> {
    throw new RuntimeException("Task error");
});
Thread.sleep(500);
System.out.println("Main continues");
```

- [x] Main continues
- [ ] RuntimeException in main
- [ ] Task error
- [ ] Lỗi biên dịch

> **Giải thích:** Exception in submitted task: stored in Future, not propagated to main. Main continues normally. Exception only thrown when Future.get() called.

## Câu 86

[TYPE: MULTIPLE_CHOICE]

Scoped Values (Java 21) thay thế gì?

- [x] ThreadLocal: immutable per-thread values, works with virtual threads, no memory leaks
- [ ] Volatile
- [ ] Atomic
- [ ] Locks

> **Giải thích:** ScopedValue: bound trong scope, auto-cleanup. ThreadLocal: mutable, manual cleanup, memory leak risk. ScopedValue: structured, virtual-thread friendly.

## Câu 87

[TYPE: SELECT_RESULT]

```java
var accumulator = new LongAccumulator(Long::max, Long.MIN_VALUE);
List.of(5L, 3L, 8L, 1L, 9L).forEach(accumulator::accumulate);
System.out.println(accumulator.get());
```

- [x] 9
- [ ] 26
- [ ] 1
- [ ] Long.MIN_VALUE

> **Giải thích:** LongAccumulator(max, identity=MIN_VALUE): accumulate max values. 5→8→9. get()=9. Thread-safe accumulator.

## Câu 88

[TYPE: SELECT_RESULT]

```java
var executor = Executors.newFixedThreadPool(3);
var futures = new ArrayList<CompletableFuture<String>>();
for (int i = 0; i < 3; i++) {
    final int id = i;
    futures.add(CompletableFuture.supplyAsync(
        () -> "Result-" + id, executor));
}
CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
futures.forEach(f -> System.out.print(f.join() + " "));
executor.shutdown();
```

- [x] Result-0 Result-1 Result-2
- [ ] Random order
- [ ] Lỗi
- [ ] Timeout

> **Giải thích:** allOf: wait all. join() in order: Result-0, Result-1, Result-2. Custom executor for CompletableFuture.

## Câu 89

[TYPE: FILL_BLANK]

`CountDownLatch.___()` giảm count và nếu về 0 thì release tất cả waiting threads.

- [x] countDown
- [ ] release
- [ ] decrement
- [ ] signal

> **Giải thích:** countDown(): decrement count. await(): block until count=0. One-shot (không reset). count=0 → all waiters released.

## Câu 90

[TYPE: SELECT_RESULT]

```java
var map = new ConcurrentHashMap<String, List<Integer>>();
map.merge("key", List.of(1), (oldVal, newVal) -> {
    var combined = new ArrayList<>(oldVal);
    combined.addAll(newVal);
    return combined;
});
map.merge("key", List.of(2), (oldVal, newVal) -> {
    var combined = new ArrayList<>(oldVal);
    combined.addAll(newVal);
    return combined;
});
System.out.println(map.get("key"));
```

- [x] [1, 2]
- [ ] [2]
- [ ] [1]
- [ ] Lỗi

> **Giải thích:** merge: first call → key absent → set [1]. Second call → key present → merge [1] + [2] = [1, 2]. Thread-safe accumulation.

## Câu 91

[TYPE: SELECT_RESULT]

```java
var phaser = new Phaser(1); // self-registration
for (int i = 0; i < 3; i++) {
    phaser.register();
    final int id = i;
    new Thread(() -> {
        phaser.arriveAndAwaitAdvance(); // phase 0 → 1
        System.out.print("T" + id + " ");
        phaser.arriveAndDeregister();
    }).start();
}
phaser.arriveAndAwaitAdvance(); // main arrives
phaser.arriveAndDeregister();
```

- [x] T0 T1 T2 (thứ tự có thể khác)
- [ ] Deadlock
- [ ] Không in gì
- [ ] Lỗi

> **Giải thích:** Phaser: 4 parties (main + 3 threads). All arrive phase 0 → advance to phase 1. Threads print. Main deregisters. Dynamic registration.

## Câu 92

[TYPE: TRUE_FALSE]

Mệnh đề: "ConcurrentHashMap.size() có thể trả về approximate count dưới concurrent modification."

- [x] Đúng
- [ ] Sai

> **Giải thích:** size(): best-effort, có thể approximate under concurrent updates. mappingCount() (Java 8): long, also approximate. Exact count requires synchronization.

## Câu 93

[TYPE: SELECT_RESULT]

```java
var ref = new AtomicReference<>("initial");
String old = ref.getAndUpdate(s -> s.toUpperCase());
System.out.println(old);
System.out.println(ref.get());
```

- [x] initial và INITIAL
- [ ] INITIAL và INITIAL
- [ ] initial và initial
- [ ] INITIAL và initial

> **Giải thích:** getAndUpdate: return old value, apply update. old="initial". Update: toUpperCase → "INITIAL". ref.get()="INITIAL".

## Câu 94

[TYPE: SELECT_RESULT]

```java
PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
pq.add(5);
pq.add(1);
pq.add(3);
System.out.print(pq.poll() + " ");
System.out.print(pq.poll() + " ");
System.out.print(pq.poll());
```

- [x] 1 3 5
- [ ] 5 1 3
- [ ] 5 3 1
- [ ] 3 1 5

> **Giải thích:** PriorityBlockingQueue: thread-safe priority queue. Natural ordering: smallest first. poll: 1, 3, 5. Unbounded.

## Câu 95

[TYPE: MULTIPLE_CHOICE]

Best practices for thread safety?

- [x] Minimize shared state, use immutable objects, prefer concurrent collections, avoid nested locks
- [ ] Synchronized everything
- [ ] Use global locks
- [ ] Ignore thread safety

> **Giải thích:** Immutable: inherently thread-safe. Concurrent collections: tested, optimized. Minimize shared mutable state. Lock ordering prevents deadlock.

## Câu 96

[TYPE: SELECT_RESULT]

```java
CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> "Value")
    .orTimeout(1, TimeUnit.SECONDS);
System.out.println(cf.get());
```

- [x] Value
- [ ] TimeoutException
- [ ] null
- [ ] Lỗi biên dịch

> **Giải thích:** orTimeout (Java 9): complete exceptionally if not done within timeout. "Value" completes immediately → no timeout. Result: "Value".

## Câu 97

[TYPE: SELECT_RESULT]

```java
CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
    try { Thread.sleep(5000); } catch (Exception e) {}
    return "Slow";
}).completeOnTimeout("Default", 1, TimeUnit.SECONDS);
System.out.println(cf.get());
```

- [x] Default
- [ ] Slow
- [ ] TimeoutException
- [ ] null

> **Giải thích:** completeOnTimeout (Java 9): complete with default value on timeout. Task takes 5s, timeout 1s → "Default".

## Câu 98

[TYPE: FILL_BLANK]

`Executor.___()` ngừng nhận tasks mới và chờ running tasks hoàn thành.

- [x] shutdown
- [ ] stop
- [ ] close
- [ ] terminate

> **Giải thích:** shutdown(): graceful shutdown. shutdownNow(): interrupt running, return queued. awaitTermination(timeout): wait for completion. close() (Java 19): shutdown + await.

## Câu 99

[TYPE: SELECT_RESULT]

```java
var executor = Executors.newFixedThreadPool(2);
var cf = CompletableFuture.supplyAsync(() -> 10, executor)
    .thenCombine(
        CompletableFuture.supplyAsync(() -> 20, executor),
        Integer::sum
    )
    .thenApply(sum -> sum * 2);
System.out.println(cf.get());
executor.shutdown();
```

- [x] 60
- [ ] 30
- [ ] 40
- [ ] 20

> **Giải thích:** cf1=10, cf2=20. thenCombine(sum): 10+20=30. thenApply(*2): 30*2=60. Async computation with custom executor.

## Câu 100

[TYPE: TRUE_FALSE]

Mệnh đề: "Java Memory Model (JMM) defines happens-before ordering rules đảm bảo visibility giữa threads."

- [x] Đúng
- [ ] Sai

> **Giải thích:** JMM (JSR 133): defines visibility, ordering, atomicity rules. Happens-before: program order, monitor lock, volatile, thread start/join. Foundation of concurrent programming.
