# 07. Multithreading, Mutex, Semaphore, IPC

## Process và thread

Process (tiến trình) là một chương trình đang chạy, có không gian địa chỉ riêng.

Thread (luồng) là luồng thực thi bên trong process. Nhiều thread trong cùng process chia sẻ memory, file descriptor/handle và tài nguyên của process.

So sánh:

| Tiêu chí | Process | Thread |
|---|---|---|
| Memory | riêng | chia sẻ trong process |
| Tạo/chuyển ngữ cảnh | nặng hơn | nhẹ hơn |
| Lỗi crash | ít ảnh hưởng process khác | có thể làm crash cả process |
| Giao tiếp | cần IPC | đọc/ghi memory chung, cần sync |

## Tạo thread trong C++11

```cpp
#include <iostream>
#include <thread>
using namespace std;

void work(int id) {
    cout << "Thread " << id << "\n";
}

int main() {
    thread t1(work, 1);
    thread t2(work, 2);

    t1.join();
    t2.join();
}
```

- `join()`: đợi thread kết thúc.
- `detach()`: tách thread, khó quản lý hơn, cần cẩn thận lifetime (vòng đời).

## Race condition

Race condition (tình trạng tranh chấp) xảy ra khi nhiều thread truy cập chung data, ít nhất một thread ghi, mà không đồng bộ.

```cpp
int counter = 0;

void increment() {
    for (int i = 0; i < 100000; ++i) {
        ++counter; // không atomic
    }
}
```

`++counter` gồm đọc, cộng, ghi. Hai thread có thể xen ngang làm mất update.

## mutex

`std::mutex` bảo vệ critical section (vùng tới hạn).

```cpp
#include <iostream>
#include <mutex>
#include <thread>
using namespace std;

int counter = 0;
mutex mtx;

void increment() {
    for (int i = 0; i < 100000; ++i) {
        mtx.lock();
        ++counter;
        mtx.unlock();
    }
}
```

Không nên lock/unlock thủ công vì nếu exception xảy ra sẽ quên unlock.

## lock_guard

RAII wrapper cho mutex, tự unlock khi ra khỏi scope.

```cpp
void safeIncrement() {
    lock_guard<mutex> lock(mtx);
    ++counter;
}
```

Dùng khi chỉ cần lock ngay lúc tạo và unlock lúc hết scope.

## unique_lock

Linh hoạt hơn `lock_guard`: có thể trì hoãn lock (defer lock), unlock sớm, relock, dùng với condition variable (biến điều kiện).

```cpp
unique_lock<mutex> lock(mtx);
// ...
lock.unlock();
// làm việc không cần mutex
lock.lock();
```

## scoped_lock

C++17. Lock nhiều mutex cùng lúc, giúp tránh deadlock (khóa chết) khi cần lấy nhiều khóa.

```cpp
mutex m1, m2;

void transfer() {
    scoped_lock lock(m1, m2);
    // thao tác cần cả m1 và m2
}
```

## Deadlock

Deadlock (khóa chết) xảy ra khi các thread chờ nhau vô hạn.

Ví dụ:

```cpp
// Thread 1: lock A rồi chờ B
// Thread 2: lock B rồi chờ A
```

Cách xử lý:

- Luôn lock mutex theo cùng một thứ tự.
- Dùng `std::scoped_lock(m1, m2)`.
- Giảm thời gian giữ lock.
- Không gọi code ngoài/unknown callback khi đang giữ lock.
- Có thể dùng timeout lock nếu phù hợp.

## Semaphore

Semaphore quản lý số lượng "permit" (giấy phép). Mutex là khóa 1 người vào critical section; semaphore cho phép N người/tài nguyên.

C++20 có `std::counting_semaphore`.

```cpp
#include <semaphore>
#include <thread>

std::counting_semaphore<3> sem(3); // tối đa 3 thread vào cùng lúc

void task() {
    sem.acquire();
    // dùng resource
    sem.release();
}
```

Binary semaphore có giá trị 0/1, gần giống mutex nhưng ý nghĩa sync (đồng bộ) có thể khác.

## condition_variable

Dùng để thread chờ đến khi có điều kiện thỏa mãn.

```cpp
#include <condition_variable>
#include <mutex>
#include <queue>

std::mutex m;
std::condition_variable cv;
std::queue<int> q;

void producer() {
    {
        std::lock_guard<std::mutex> lock(m);
        q.push(10);
    }
    cv.notify_one();
}

void consumer() {
    std::unique_lock<std::mutex> lock(m);
    cv.wait(lock, [] { return !q.empty(); });
    int value = q.front();
    q.pop();
}
```

## IPC - Inter-Process Communication

IPC là giao tiếp giữa các tiến trình (process).

Hình thức thường gặp:

- Signal: gửi thông báo sự kiện tới process, ví dụ `SIGINT`.
- Pipe: truyền byte một chiều giữa các process.
- Named pipe/FIFO: pipe có tên trong hệ thống.
- Socket: giao tiếp qua network hoặc local socket.
- Shared memory: chia sẻ vùng memory giữa các process, nhanh nhưng cần sync.
- Message queue: gửi message có cấu trúc.

## Socket

Socket là endpoint giao tiếp. Co thể dùng TCP/UDP.

- TCP: tin cậy, có kết nối, đảm bảo thứ tự.
- UDP: không kết nối, nhanh, không đảm bảo mất gói/thứ tự.

Flow TCP server cơ bản:

1. `socket()`
2. `bind()`
3. `listen()`
4. `accept()`
5. `send()/recv()`
6. `close()`

Flow TCP client:

1. `socket()`
2. `connect()`
3. `send()/recv()`
4. `close()`

## Pipe

Pipe phù hợp cho quan hệ parent-child process (tiến trình cha-con).

Ý tưởng:

- Parent tạo pipe.
- Fork/spawn child.
- Một bên ghi vào write-end.
- Bên kia đọc từ read-end.

Trên Windows và Linux API khác nhau, nhưng concept giống nhau.

## Data race vs race condition

Hai khái niệm liên quan nhưng không hoàn toàn giống nhau.

- Data race: nhiều thread truy cập cùng memory, ít nhất một ghi, không có đồng bộ phù hợp. Trong C++ data race là undefined behavior.
- Race condition: kết quả phụ thuộc thứ tự timing giữa các thread/process. Có thể có race condition logic ngay cả khi không có data race.

Ví dụ data race:

```cpp
int counter = 0;

void inc() {
    ++counter; // nhiều thread gọi cùng lúc: data race
}
```

Ví dụ race condition logic: hai request cùng kiểm tra "còn hàng" rồi cùng trừ kho nếu thao tác check/update không atomic ở mức nghiệp vụ.

## Atomic

`std::atomic` dùng cho thao tác đơn giản cần atomic mà không muốn mutex.

```cpp
#include <atomic>

std::atomic<int> counter{0};

void inc() {
    counter.fetch_add(1);
}
```

Atomic phù hợp cho counter, flag, reference count. Không nên dùng atomic để thay toàn bộ mutex nếu invariant gồm nhiều biến.

```cpp
struct Account {
    int balance;
    int version;
};
```

Nếu cần cập nhật `balance` và `version` cùng nhau, mutex dễ đúng hơn atomic rời rạc.

## Memory ordering nói ngắn gọn

C++ atomic có memory ordering như `memory_order_relaxed`, `acquire`, `release`, `seq_cst`. Đây là chủ đề sâu.

Khi phỏng vấn cơ bản:

- Mặc định atomic dùng `seq_cst`, dễ hiểu nhất, thứ tự mạnh nhất.
- `relaxed` chỉ đảm bảo atomicity, không đảm bảo ordering giữa các memory operation khác.
- `acquire/release` thường dùng để publish/consume data giữa threads.

Không nên tự tối ưu memory order nếu chưa đo và chưa hiểu rõ. Mutex thường đủ và dễ maintain hơn.

## Condition variable: vì sao dùng predicate?

`cv.wait(lock)` có thể tỉnh dậy dù chưa có dữ liệu, gọi là spurious wakeup. Vì vậy nên dùng overload có predicate:

```cpp
cv.wait(lock, [] { return !q.empty(); });
```

Nó tương đương loop:

```cpp
while (q.empty()) {
    cv.wait(lock);
}
```

Điểm cần nhớ: predicate phải kiểm tra state được bảo vệ bởi cùng mutex.

## Producer-consumer hoàn chỉnh hơn

```cpp
queue<int> q;
mutex m;
condition_variable cv;
bool done = false;

void producer() {
    for (int i = 0; i < 10; ++i) {
        {
            lock_guard<mutex> lock(m);
            q.push(i);
        }
        cv.notify_one();
    }

    {
        lock_guard<mutex> lock(m);
        done = true;
    }
    cv.notify_all();
}

void consumer() {
    while (true) {
        unique_lock<mutex> lock(m);
        cv.wait(lock, [] { return !q.empty() || done; });

        if (q.empty() && done) break;

        int value = q.front();
        q.pop();
        lock.unlock();

        // xử lý value ngoài lock để giảm thời gian giữ mutex
    }
}
```

Điểm hay để nói khi phỏng vấn: chỉ giữ lock khi truy cập shared state; xử lý nặng nên làm ngoài lock.

## Thread lifetime và lỗi hay gặp

Nếu object `std::thread` còn joinable khi destructor chạy, chương trình gọi `std::terminate`.

```cpp
void f() {
    thread t([] {});
} // lỗi: chưa join/detach
```

Phải:

```cpp
thread t([] {});
t.join();
```

C++20 có `std::jthread`, tự join khi hủy và hỗ trợ stop token.

```cpp
std::jthread t([] {
    // work
});
```

## Lock granularity

Lock quá rộng: dễ chậm vì thread chờ nhau nhiều. Lock quá nhỏ: code phức tạp, dễ sai. Cách thực tế:

- Bảo vệ đúng shared state.
- Giữ lock trong thời gian ngắn.
- Không gọi IO, network, callback ngoài kiểm soát khi đang giữ lock.
- Document mutex nào bảo vệ biến nào.

## IPC chi tiết hơn

### Shared memory

Nhanh vì process đọc/ghi cùng vùng memory, nhưng phải tự đồng bộ bằng mutex/semaphore liên process. Nếu không sync, lỗi giống multithreading nhưng khó debug hơn.

### Message queue

Tốt khi muốn giao tiếp theo message rõ ràng. Chậm hơn shared memory nhưng dễ quản lý ownership dữ liệu và boundary giữa process.

### Socket

Linh hoạt nhất, dùng được giữa máy khác nhau. Local socket cũng dùng được cho process cùng máy.

### Pipe

Đơn giản cho stream một chiều, thường dùng nối output process này vào input process khác.

## Câu hỏi bẫy thường gặp

1. Mutex khác semaphore?

Mutex thường bảo vệ critical section và có ownership: thread lock nên là thread unlock. Semaphore quản lý số permit, thường dùng giới hạn tài nguyên hoặc signaling.

2. Khi nào dùng atomic thay mutex?

Dùng atomic cho state đơn giản, độc lập như counter/flag. Dùng mutex khi invariant gồm nhiều biến hoặc thao tác phức tạp.

3. Deadlock cần mấy điều kiện?

Thường nhắc 4 điều kiện Coffman: mutual exclusion, hold and wait, no preemption, circular wait. Phá một trong các điều kiện sẽ tránh deadlock.
