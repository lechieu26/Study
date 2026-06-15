# Concurrency trong Java

## Mục lục

1. [Process và Thread](#1-process-và-thread)
2. [Multithreading](#2-multithreading)
3. [Race Condition](#3-race-condition)
4. [Các Cơ Chế Đồng Bộ](#4-các-cơ-chế-đồng-bộ)
5. [Deadlock](#5-deadlock)
6. [Thread Pools và Executor Framework](#6-thread-pools-và-executor-framework)
7. [Callable và Future](#7-callable-và-future)
8. [ThreadLocal](#8-threadlocal)
9. [Best Practices](#9-best-practices)

---

## 1. Process và Thread

### 1.1. Process (Tiến trình)

**Process** là một chương trình đang chạy, có không gian bộ nhớ riêng biệt (memory space) và tài nguyên hệ thống riêng.

- Mỗi process có **address space** riêng
- Các process giao tiếp qua IPC (Inter-Process Communication)
- Tạo process tốn nhiều tài nguyên
- Crash của 1 process không ảnh hưởng process khác

### 1.2. Thread (Luồng)

**Thread** là đơn vị thực thi nhỏ nhất trong một process. Nhiều threads cùng chia sẻ bộ nhớ của process.

- Threads chia sẻ **heap memory** của process
- Mỗi thread có **stack** riêng
- Tạo thread nhẹ hơn process
- Crash của 1 thread có thể ảnh hưởng toàn bộ process

```java
public class ThreadBasicDemo {
    public static void main(String[] args) {
        // Thông tin thread hiện tại
        Thread current = Thread.currentThread();
        System.out.println("Thread name: " + current.getName());     // main
        System.out.println("Thread ID: " + current.getId());
        System.out.println("Thread priority: " + current.getPriority()); // 5
        System.out.println("Thread state: " + current.getState());   // RUNNABLE
        System.out.println("Is daemon: " + current.isDaemon());      // false
    }
}
```

### 1.3. Thread-safe

**Thread-safe** là khả năng của code hoạt động đúng khi được truy cập đồng thời bởi nhiều threads.

```java
// KHÔNG thread-safe
class Counter {
    private int count = 0;
    
    public void increment() {
        count++; // read-modify-write: KHÔNG atomic!
    }
    
    public int getCount() { return count; }
}

// Thread-safe
class SafeCounter {
    private final AtomicInteger count = new AtomicInteger(0);
    
    public void increment() {
        count.incrementAndGet(); // Atomic operation
    }
    
    public int getCount() { return count.get(); }
}
```

---

## 2. Multithreading

### Lợi ích của Multithreading

| Lợi ích | Mô tả |
|---------|--------|
| **Responsiveness** | UI không bị block khi xử lý nặng |
| **Resource sharing** | Threads chia sẻ memory hiệu quả |
| **Parallelism** | Tận dụng multi-core CPU |
| **Throughput** | Xử lý nhiều request đồng thời |

### Cách tạo Thread

```java
// Cách 1: Extend Thread class
class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(getName() + ": " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

// Cách 2: Implement Runnable interface (RECOMMENDED)
class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }
}

// Cách 3: Lambda (Java 8+)
public class CreateThreadDemo {
    public static void main(String[] args) {
        // Cách 1
        MyThread t1 = new MyThread();
        t1.setName("Thread-1");
        t1.start(); // start() chứ KHÔNG PHẢI run()
        
        // Cách 2
        Thread t2 = new Thread(new MyRunnable(), "Thread-2");
        t2.start();
        
        // Cách 3: Lambda
        Thread t3 = new Thread(() -> {
            System.out.println("Lambda thread: " + Thread.currentThread().getName());
        }, "Thread-3");
        t3.start();
        
        // Chờ threads hoàn thành
        try {
            t1.join();    // chờ t1 kết thúc
            t2.join();    // chờ t2 kết thúc
            t3.join(1000); // chờ tối đa 1 giây
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("All threads completed");
    }
}
```

### Thread Lifecycle

```
      NEW ──start()──> RUNNABLE ──scheduler──> RUNNING
       |                  ^  |                    |
       |                  |  |                    |
       |          notify/ |  | wait/sleep/       | run() ends
       |          unpark  |  | join/IO           |
       |                  |  v                    v
       |               WAITING/              TERMINATED
       |               TIMED_WAITING
       |               BLOCKED
```

```java
public class ThreadLifecycleDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        System.out.println("State after new: " + thread.getState());      // NEW
        
        thread.start();
        System.out.println("State after start: " + thread.getState());    // RUNNABLE
        
        Thread.sleep(100);
        System.out.println("State during sleep: " + thread.getState());   // TIMED_WAITING
        
        thread.join();
        System.out.println("State after join: " + thread.getState());     // TERMINATED
    }
}
```

---

## 3. Race Condition

### Nguyên nhân

**Race Condition** xảy ra khi nhiều threads truy cập cùng một shared resource đồng thời mà không đồng bộ hóa.

```java
public class RaceConditionDemo {
    private static int counter = 0;
    
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 100_000; i++) {
                counter++; // KHÔNG atomic: read -> increment -> write
            }
        };
        
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        
        // Kết quả KHÔNG đoán được! Có thể < 200,000
        System.out.println("Counter: " + counter); // Ví dụ: 156,823
        // Mong đợi: 200,000
    }
}
```

**Giải thích:**
```
Thread 1: read counter (= 5)
Thread 2: read counter (= 5)   // cùng đọc giá trị cũ!
Thread 1: write counter (= 6)
Thread 2: write counter (= 6)  // ghi đè kết quả của Thread 1!
// Kết quả: tăng 2 lần nhưng counter chỉ tăng 1
```

### Cách phòng tránh

1. **synchronized** - đảm bảo mutual exclusion
2. **volatile** - đảm bảo visibility
3. **Atomic variables** - operations nguyên tử
4. **Locks** - fine-grained locking
5. **Immutable objects** - không thể thay đổi

---

## 4. Các Cơ Chế Đồng Bộ

### 4.1. synchronized keyword

```java
public class SynchronizedDemo {
    private int count = 0;
    private final Object lock = new Object();
    
    // Synchronized method - lock trên 'this'
    public synchronized void increment() {
        count++;
    }
    
    // Synchronized static method - lock trên Class object
    private static int staticCount = 0;
    public static synchronized void staticIncrement() {
        staticCount++;
    }
    
    // Synchronized block - fine-grained locking
    public void incrementWithBlock() {
        synchronized (lock) {  // lock trên object cụ thể
            count++;
        }
    }
    
    // Wait/Notify pattern
    private final Queue<String> queue = new LinkedList<>();
    private final int CAPACITY = 10;
    
    public synchronized void produce(String item) throws InterruptedException {
        while (queue.size() == CAPACITY) {
            wait(); // nhả lock, chờ consumer lấy bớt
        }
        queue.add(item);
        notifyAll(); // thông báo consumer có item mới
    }
    
    public synchronized String consume() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // nhả lock, chờ producer thêm
        }
        String item = queue.poll();
        notifyAll(); // thông báo producer có chỗ trống
        return item;
    }
    
    public static void main(String[] args) throws InterruptedException {
        SynchronizedDemo demo = new SynchronizedDemo();
        
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) demo.increment();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) demo.increment();
        });
        
        t1.start(); t2.start();
        t1.join(); t2.join();
        
        System.out.println("Count: " + demo.count); // Luôn = 200,000
    }
}
```

### 4.2. volatile keyword

**volatile** đảm bảo **visibility** - thay đổi từ 1 thread luôn được nhìn thấy bởi threads khác.

```java
public class VolatileDemo {
    // Không có volatile - thread có thể cache giá trị cũ
    // private boolean running = true;
    
    // Có volatile - đảm bảo mọi thread đọc giá trị mới nhất
    private volatile boolean running = true;
    
    public void start() {
        new Thread(() -> {
            int count = 0;
            while (running) { // đọc từ main memory, không cache
                count++;
            }
            System.out.println("Stopped after " + count + " iterations");
        }).start();
    }
    
    public void stop() {
        running = false; // ghi vào main memory ngay lập tức
    }
    
    public static void main(String[] args) throws InterruptedException {
        VolatileDemo demo = new VolatileDemo();
        demo.start();
        Thread.sleep(100);
        demo.stop();
    }
}
```

**Lưu ý:** `volatile` chỉ đảm bảo **visibility**, KHÔNG đảm bảo **atomicity**!

```java
private volatile int count = 0;
count++; // VẪN KHÔNG thread-safe! (read-modify-write)
```

### 4.3. Locks (ReentrantLock, ReadWriteLock)

```java
import java.util.concurrent.locks.*;

public class LockDemo {
    private final ReentrantLock lock = new ReentrantLock();
    private int count = 0;
    
    // ReentrantLock - tương đương synchronized nhưng linh hoạt hơn
    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock(); // PHẢI unlock trong finally
        }
    }
    
    // tryLock - thử lấy lock, không block
    public boolean tryIncrement() {
        if (lock.tryLock()) {
            try {
                count++;
                return true;
            } finally {
                lock.unlock();
            }
        }
        return false; // không lấy được lock
    }
    
    // tryLock with timeout
    public boolean timedIncrement() throws InterruptedException {
        if (lock.tryLock(1, TimeUnit.SECONDS)) {
            try {
                count++;
                return true;
            } finally {
                lock.unlock();
            }
        }
        return false;
    }
}

// ReadWriteLock - nhiều reader OR một writer
class CachedData {
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();
    private Map<String, String> cache = new HashMap<>();
    
    public String read(String key) {
        readLock.lock(); // nhiều threads có thể đọc đồng thời
        try {
            return cache.get(key);
        } finally {
            readLock.unlock();
        }
    }
    
    public void write(String key, String value) {
        writeLock.lock(); // chỉ 1 thread được ghi, block tất cả readers
        try {
            cache.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }
}
```

### 4.4. Atomic Variables

```java
import java.util.concurrent.atomic.*;

public class AtomicDemo {
    // AtomicInteger - thread-safe integer operations
    private final AtomicInteger counter = new AtomicInteger(0);
    
    public void increment() {
        counter.incrementAndGet();  // ++counter (atomic)
        counter.getAndIncrement();  // counter++ (atomic)
        counter.addAndGet(5);       // counter += 5 (atomic)
        counter.compareAndSet(10, 20); // CAS: if counter==10, set to 20
    }
    
    // AtomicReference
    private final AtomicReference<String> name = new AtomicReference<>("initial");
    
    public void updateName(String newName) {
        name.set(newName);
        String old = name.getAndSet(newName);
        name.compareAndSet("old", "new");
    }
    
    // AtomicBoolean - flag
    private final AtomicBoolean initialized = new AtomicBoolean(false);
    
    public void initOnce() {
        if (initialized.compareAndSet(false, true)) {
            // Chỉ 1 thread thực hiện init
            System.out.println("Initializing...");
        }
    }
    
    // LongAdder (Java 8+) - nhanh hơn AtomicLong cho high contention
    private final LongAdder adder = new LongAdder();
    
    public void count() {
        adder.increment();     // thread-local counting
        long total = adder.sum(); // aggregate khi cần
    }
    
    public static void main(String[] args) throws InterruptedException {
        AtomicDemo demo = new AtomicDemo();
        
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100_000; j++) {
                    demo.counter.incrementAndGet();
                }
            });
            threads[i].start();
        }
        
        for (Thread t : threads) t.join();
        System.out.println("Counter: " + demo.counter.get()); // Luôn 1,000,000
    }
}
```

### 4.5. Concurrent Collections

```java
import java.util.concurrent.*;

public class ConcurrentCollectionsDemo {
    public static void main(String[] args) {
        // ConcurrentHashMap - thread-safe HashMap
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("A", 1);
        map.putIfAbsent("B", 2);
        map.compute("A", (k, v) -> v + 10); // atomic compute
        
        // CopyOnWriteArrayList - thread-safe cho nhiều đọc, ít ghi
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("item1");
        // Tạo bản copy mới mỗi lần write -> safe iterate
        for (String item : list) {
            // Không ConcurrentModificationException
            list.add("newItem"); // ghi vào bản copy mới
        }
        
        // BlockingQueue - producer-consumer pattern
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(10);
        
        // Producer
        new Thread(() -> {
            try {
                queue.put("item"); // block nếu queue đầy
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
        
        // Consumer
        new Thread(() -> {
            try {
                String item = queue.take(); // block nếu queue rỗng
                System.out.println("Consumed: " + item);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
        
        // ConcurrentLinkedQueue - non-blocking queue
        ConcurrentLinkedQueue<String> nonBlockingQueue = new ConcurrentLinkedQueue<>();
        nonBlockingQueue.offer("item");
        String polled = nonBlockingQueue.poll(); // null nếu rỗng
    }
}
```

---

## 5. Deadlock

### Nguyên nhân gây Deadlock

**Deadlock** xảy ra khi 2+ threads chờ đợi nhau giải phóng lock, tạo thành vòng chờ.

```java
public class DeadlockDemo {
    private final Object lockA = new Object();
    private final Object lockB = new Object();
    
    public void method1() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + " holds lockA");
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            
            synchronized (lockB) { // Chờ lockB mà thread khác đang giữ
                System.out.println("method1: got both locks");
            }
        }
    }
    
    public void method2() {
        synchronized (lockB) { // Giữ lockB
            System.out.println(Thread.currentThread().getName() + " holds lockB");
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            
            synchronized (lockA) { // Chờ lockA mà thread khác đang giữ
                System.out.println("method2: got both locks");
            }
        }
    }
    
    public static void main(String[] args) {
        DeadlockDemo demo = new DeadlockDemo();
        
        new Thread(demo::method1, "Thread-1").start();
        new Thread(demo::method2, "Thread-2").start();
        // DEADLOCK! Thread-1 giữ lockA chờ lockB
        //           Thread-2 giữ lockB chờ lockA
    }
}
```

**4 điều kiện Coffman (tất cả phải thỏa mãn để deadlock xảy ra):**
1. **Mutual Exclusion** - resource chỉ 1 thread sử dụng tại 1 thời điểm
2. **Hold and Wait** - thread giữ resource và chờ resource khác
3. **No Preemption** - resource không thể bị lấy lại
4. **Circular Wait** - tồn tại vòng chờ

### Cách phòng tránh

```java
public class DeadlockPreventionDemo {
    private final Object lockA = new Object();
    private final Object lockB = new Object();
    
    // Giải pháp 1: Lock ordering - luôn lấy lock theo thứ tự cố định
    public void method1() {
        synchronized (lockA) {  // Luôn lấy lockA trước
            synchronized (lockB) {
                System.out.println("method1: safe");
            }
        }
    }
    
    public void method2() {
        synchronized (lockA) {  // Cũng lấy lockA trước (cùng thứ tự)
            synchronized (lockB) {
                System.out.println("method2: safe");
            }
        }
    }
    
    // Giải pháp 2: tryLock with timeout
    private final ReentrantLock lock1 = new ReentrantLock();
    private final ReentrantLock lock2 = new ReentrantLock();
    
    public void safeMethod() {
        boolean gotBoth = false;
        while (!gotBoth) {
            boolean gotLock1 = false;
            boolean gotLock2 = false;
            try {
                gotLock1 = lock1.tryLock(100, TimeUnit.MILLISECONDS);
                gotLock2 = lock2.tryLock(100, TimeUnit.MILLISECONDS);
                gotBoth = gotLock1 && gotLock2;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            } finally {
                if (!gotBoth) {
                    if (gotLock1) lock1.unlock();
                    if (gotLock2) lock2.unlock();
                }
            }
            if (!gotBoth) {
                // Back off and retry
                try { Thread.sleep(50); } catch (InterruptedException e) { return; }
            }
        }
        
        try {
            // Do work with both locks
            System.out.println("Got both locks safely");
        } finally {
            lock2.unlock();
            lock1.unlock();
        }
    }
}
```

### Cách phát hiện và xử lý

```java
// Phát hiện deadlock bằng ThreadMXBean
import java.lang.management.*;

public class DeadlockDetector {
    public static void detectDeadlock() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] deadlockedThreads = bean.findDeadlockedThreads();
        
        if (deadlockedThreads != null) {
            ThreadInfo[] infos = bean.getThreadInfo(deadlockedThreads, true, true);
            System.out.println("DEADLOCK DETECTED!");
            for (ThreadInfo info : infos) {
                System.out.println("Thread: " + info.getThreadName());
                System.out.println("  State: " + info.getThreadState());
                System.out.println("  Waiting for: " + info.getLockName());
                System.out.println("  Held by: " + info.getLockOwnerName());
            }
        }
    }
}
```

---

## 6. Thread Pools và Executor Framework

### ExecutorService

```java
import java.util.concurrent.*;

public class ExecutorDemo {
    public static void main(String[] args) throws Exception {
        // Tạo thread pool
        ExecutorService executor = Executors.newFixedThreadPool(4);
        
        // Submit tasks
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("Task " + taskId + " - Thread: " + 
                    Thread.currentThread().getName());
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
            });
        }
        
        // Shutdown
        executor.shutdown(); // không nhận task mới, chờ tasks hiện tại hoàn thành
        boolean finished = executor.awaitTermination(30, TimeUnit.SECONDS);
        System.out.println("All tasks finished: " + finished);
        
        // Hoặc shutdownNow() - cố gắng stop ngay
        // List<Runnable> pending = executor.shutdownNow();
    }
}
```

### ThreadPoolExecutor

```java
import java.util.concurrent.*;

public class ThreadPoolExecutorDemo {
    public static void main(String[] args) {
        // Custom ThreadPoolExecutor
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            2,                      // corePoolSize - threads duy trì tối thiểu
            4,                      // maximumPoolSize - threads tối đa
            60, TimeUnit.SECONDS,   // keepAliveTime - thời gian idle trước khi xóa thread thừa
            new LinkedBlockingQueue<>(100), // work queue
            new ThreadPoolExecutor.CallerRunsPolicy() // rejection policy
        );
        
        // Rejection policies:
        // AbortPolicy (default) - throw RejectedExecutionException
        // CallerRunsPolicy - caller thread tự chạy task
        // DiscardPolicy - bỏ task mới
        // DiscardOldestPolicy - bỏ task cũ nhất trong queue
        
        // Các loại pool có sẵn
        ExecutorService fixed = Executors.newFixedThreadPool(4);      // số thread cố định
        ExecutorService cached = Executors.newCachedThreadPool();     // tạo thread theo nhu cầu
        ExecutorService single = Executors.newSingleThreadExecutor(); // 1 thread
    }
}
```

### ScheduledExecutorService

```java
import java.util.concurrent.*;

public class ScheduledExecutorDemo {
    public static void main(String[] args) throws Exception {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        
        // Delay 3 giây rồi chạy
        scheduler.schedule(() -> {
            System.out.println("Delayed task executed after 3s");
        }, 3, TimeUnit.SECONDS);
        
        // Chạy định kỳ (fixed rate): mỗi 2 giây
        ScheduledFuture<?> fixedRate = scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Fixed rate: " + System.currentTimeMillis());
        }, 0, 2, TimeUnit.SECONDS);
        
        // Chạy định kỳ (fixed delay): 2 giây SAU KHI task trước kết thúc
        ScheduledFuture<?> fixedDelay = scheduler.scheduleWithFixedDelay(() -> {
            System.out.println("Fixed delay: " + System.currentTimeMillis());
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }, 0, 2, TimeUnit.SECONDS);
        
        // Dừng sau 10 giây
        Thread.sleep(10000);
        fixedRate.cancel(false);
        fixedDelay.cancel(false);
        scheduler.shutdown();
    }
}
```

### ForkJoinPool

```java
import java.util.concurrent.*;

// ForkJoinPool - phù hợp cho divide-and-conquer tasks
class SumTask extends RecursiveTask<Long> {
    private final long[] array;
    private final int start, end;
    private static final int THRESHOLD = 1000;
    
    public SumTask(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }
    
    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            // Base case: đủ nhỏ, tính trực tiếp
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        }
        
        // Recursive case: chia đôi
        int mid = (start + end) / 2;
        SumTask leftTask = new SumTask(array, start, mid);
        SumTask rightTask = new SumTask(array, mid, end);
        
        leftTask.fork();  // chạy async trên thread khác
        long rightResult = rightTask.compute(); // chạy trên thread hiện tại
        long leftResult = leftTask.join();      // chờ kết quả left
        
        return leftResult + rightResult;
    }
}

public class ForkJoinDemo {
    public static void main(String[] args) {
        long[] array = new long[10_000_000];
        for (int i = 0; i < array.length; i++) array[i] = i + 1;
        
        ForkJoinPool pool = new ForkJoinPool(); // hoặc ForkJoinPool.commonPool()
        SumTask task = new SumTask(array, 0, array.length);
        
        long start = System.currentTimeMillis();
        long result = pool.invoke(task);
        long elapsed = System.currentTimeMillis() - start;
        
        System.out.println("Sum: " + result);
        System.out.println("Time: " + elapsed + "ms");
        System.out.println("Parallelism: " + pool.getParallelism());
    }
}
```

---

## 7. Callable và Future

### Callable Interface

**Callable** giống Runnable nhưng có thể **trả về giá trị** và **throw checked exception**.

```java
import java.util.concurrent.*;

public class CallableDemo {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // Callable trả về kết quả
        Callable<Integer> task = () -> {
            Thread.sleep(2000); // simulate work
            return 42;
        };
        
        Future<Integer> future = executor.submit(task);
        
        System.out.println("Task submitted, doing other work...");
        System.out.println("Is done? " + future.isDone()); // false
        
        // get() blocks cho đến khi có kết quả
        Integer result = future.get(); // blocking!
        System.out.println("Result: " + result); // 42
        
        // get() with timeout
        Callable<String> longTask = () -> {
            Thread.sleep(10000);
            return "done";
        };
        Future<String> longFuture = executor.submit(longTask);
        try {
            String r = longFuture.get(3, TimeUnit.SECONDS); // timeout after 3s
        } catch (TimeoutException e) {
            System.out.println("Timeout! Cancelling...");
            longFuture.cancel(true); // interrupt the task
        }
        
        executor.shutdown();
    }
}
```

### Future Interface

```java
// invokeAll - submit nhiều tasks, chờ tất cả hoàn thành
public class InvokeAllDemo {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        
        List<Callable<String>> tasks = Arrays.asList(
            () -> { Thread.sleep(1000); return "Task 1 done"; },
            () -> { Thread.sleep(2000); return "Task 2 done"; },
            () -> { Thread.sleep(500);  return "Task 3 done"; }
        );
        
        // invokeAll - chờ TẤT CẢ tasks hoàn thành
        List<Future<String>> futures = executor.invokeAll(tasks);
        for (Future<String> f : futures) {
            System.out.println(f.get());
        }
        
        // invokeAny - trả về kết quả của task hoàn thành ĐẦU TIÊN
        String first = executor.invokeAny(tasks);
        System.out.println("First completed: " + first); // Task 3 done
        
        executor.shutdown();
    }
}
```

### CompletableFuture

```java
import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {
    public static void main(String[] args) throws Exception {
        // Tạo CompletableFuture
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "Hello";
        });
        
        // Chain operations (non-blocking)
        CompletableFuture<String> result = future
            .thenApply(s -> s + " World")           // transform
            .thenApply(String::toUpperCase);         // transform
        
        System.out.println(result.get()); // HELLO WORLD
        
        // thenCompose - flatMap (chain async operations)
        CompletableFuture<String> composed = CompletableFuture
            .supplyAsync(() -> "userId123")
            .thenCompose(userId -> fetchUser(userId))  // returns CompletableFuture
            .thenCompose(user -> fetchOrders(user));
        
        // thenCombine - combine 2 independent futures
        CompletableFuture<String> name = CompletableFuture.supplyAsync(() -> "Alice");
        CompletableFuture<Integer> age = CompletableFuture.supplyAsync(() -> 25);
        
        CompletableFuture<String> combined = name.thenCombine(age, 
            (n, a) -> n + " is " + a + " years old");
        System.out.println(combined.get()); // Alice is 25 years old
        
        // allOf - chờ tất cả hoàn thành
        CompletableFuture<Void> all = CompletableFuture.allOf(
            CompletableFuture.runAsync(() -> sleep(1000)),
            CompletableFuture.runAsync(() -> sleep(2000)),
            CompletableFuture.runAsync(() -> sleep(500))
        );
        all.get(); // chờ 2 giây (task chậm nhất)
        
        // anyOf - kết quả đầu tiên
        CompletableFuture<Object> any = CompletableFuture.anyOf(
            CompletableFuture.supplyAsync(() -> { sleep(1000); return "Slow"; }),
            CompletableFuture.supplyAsync(() -> { sleep(100); return "Fast"; })
        );
        System.out.println(any.get()); // Fast
        
        // Exception handling
        CompletableFuture<String> handled = CompletableFuture
            .supplyAsync(() -> {
                if (true) throw new RuntimeException("Oops");
                return "OK";
            })
            .exceptionally(ex -> "Error: " + ex.getMessage())
            .thenApply(s -> "Result: " + s);
        
        System.out.println(handled.get()); // Result: Error: Oops
        
        // handle - xử lý cả success và failure
        CompletableFuture<String> handled2 = CompletableFuture
            .supplyAsync(() -> "data")
            .handle((result2, ex) -> {
                if (ex != null) return "Error: " + ex.getMessage();
                return "Success: " + result2;
            });
    }
    
    static CompletableFuture<String> fetchUser(String id) {
        return CompletableFuture.supplyAsync(() -> "User_" + id);
    }
    
    static CompletableFuture<String> fetchOrders(String user) {
        return CompletableFuture.supplyAsync(() -> "Orders for " + user);
    }
    
    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) {}
    }
}
```

---

## 8. ThreadLocal

### Cách sử dụng

**ThreadLocal** cung cấp biến **riêng cho mỗi thread** - mỗi thread có bản copy riêng.

```java
public class ThreadLocalDemo {
    // Mỗi thread có SimpleDateFormat riêng (vì SDF không thread-safe)
    private static final ThreadLocal<SimpleDateFormat> dateFormat = 
        ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    
    // User context cho mỗi request
    private static final ThreadLocal<String> currentUser = new ThreadLocal<>();
    
    public static void main(String[] args) throws InterruptedException {
        // Demo: mỗi thread có giá trị riêng
        ThreadLocal<Integer> threadId = new ThreadLocal<>();
        
        Thread t1 = new Thread(() -> {
            threadId.set(1);
            System.out.println("Thread 1: " + threadId.get()); // 1
        });
        
        Thread t2 = new Thread(() -> {
            threadId.set(2);
            System.out.println("Thread 2: " + threadId.get()); // 2
        });
        
        t1.start(); t2.start();
        t1.join(); t2.join();
        
        System.out.println("Main: " + threadId.get()); // null (main chưa set)
    }
    
    // Sử dụng trong web application
    public static void handleRequest(String username) {
        try {
            currentUser.set(username);
            // Bất kỳ code nào trong thread này đều có thể access currentUser
            processRequest();
            logAction();
        } finally {
            currentUser.remove(); // QUAN TRỌNG: clean up để tránh memory leak
        }
    }
    
    private static void processRequest() {
        String user = currentUser.get();
        System.out.println("Processing for: " + user);
    }
    
    private static void logAction() {
        String user = currentUser.get();
        String time = dateFormat.get().format(new Date());
        System.out.println("[" + time + "] Action by: " + user);
    }
}
```

### Use Cases

```java
// 1. Database connection per thread
private static final ThreadLocal<Connection> connectionHolder = 
    ThreadLocal.withInitial(() -> {
        try {
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    });

// 2. Transaction context
private static final ThreadLocal<Transaction> transactionContext = new ThreadLocal<>();

// 3. Request-scoped data (web apps)
private static final ThreadLocal<Map<String, Object>> requestAttributes = 
    ThreadLocal.withInitial(HashMap::new);

// 4. Performance - avoid object creation
private static final ThreadLocal<StringBuilder> stringBuilder = 
    ThreadLocal.withInitial(StringBuilder::new);

public static String format(String template, Object... args) {
    StringBuilder sb = stringBuilder.get();
    sb.setLength(0); // reuse, không tạo mới
    // ... format logic
    return sb.toString();
}
```

---

## 9. Best Practices

### Immutable Objects

```java
// ✅ Immutable class - inherently thread-safe
public final class ImmutablePoint {
    private final int x;
    private final int y;
    
    public ImmutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
    
    // Trả về object mới thay vì modify
    public ImmutablePoint translate(int dx, int dy) {
        return new ImmutablePoint(x + dx, y + dy);
    }
}
```

### Thread-safe Collections

```java
// ✅ Chọn concurrent collection phù hợp
Map<String, Object> map = new ConcurrentHashMap<>();         // high concurrency
List<String> list = new CopyOnWriteArrayList<>();            // nhiều read, ít write
Queue<String> queue = new ConcurrentLinkedQueue<>();         // non-blocking queue
BlockingQueue<String> bq = new LinkedBlockingQueue<>(100);  // producer-consumer

// ❌ KHÔNG dùng synchronized wrappers (chậm)
// List<String> syncList = Collections.synchronizedList(new ArrayList<>());
```

### Proper Resource Management

```java
// ✅ Always shutdown ExecutorService
ExecutorService executor = Executors.newFixedThreadPool(4);
try {
    // submit tasks...
} finally {
    executor.shutdown();
    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
        executor.shutdownNow();
    }
}

// ✅ Clean up ThreadLocal
ThreadLocal<Connection> connLocal = new ThreadLocal<>();
try {
    connLocal.set(getConnection());
    // use connection
} finally {
    Connection conn = connLocal.get();
    if (conn != null) conn.close();
    connLocal.remove(); // Prevent memory leak!
}

// ✅ Handle InterruptedException correctly
public void interruptibleTask() {
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        // Restore interrupt status
        Thread.currentThread().interrupt();
        // Clean up and return
        return;
    }
}
```

### Tóm tắt các công cụ đồng bộ

| Tình huống | Công cụ |
|-----------|---------|
| Simple counter | `AtomicInteger`, `LongAdder` |
| Flag/boolean | `AtomicBoolean`, `volatile boolean` |
| Protect code block | `synchronized`, `ReentrantLock` |
| Many readers, few writers | `ReadWriteLock` |
| Producer-consumer | `BlockingQueue` |
| Async computation | `CompletableFuture` |
| Thread pool | `ExecutorService` |
| Scheduled tasks | `ScheduledExecutorService` |
| Divide-and-conquer | `ForkJoinPool` |
| Per-thread data | `ThreadLocal` |
| Wait for N threads | `CountDownLatch` |
| Reusable barrier | `CyclicBarrier` |
| Limit concurrent access | `Semaphore` |

---

> **Tóm tắt:** Concurrency là chủ đề phức tạp nhưng quan trọng trong Java. Hiểu rõ thread lifecycle, race conditions, deadlock, và các cơ chế đồng bộ giúp xây dựng ứng dụng multi-threaded đúng đắn và hiệu quả. Ưu tiên sử dụng high-level abstractions (ExecutorService, CompletableFuture) thay vì low-level thread management.
