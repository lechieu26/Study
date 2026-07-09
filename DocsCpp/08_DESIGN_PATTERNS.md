# 08. Design Patterns

Design pattern (mẫu thiết kế) là cách giải quyết mẫu cho các vấn đề lặp lại trong thiết kế phần mềm. Không nên học để áp dụng máy móc; hãy dùng khi pattern làm code rõ hơn và giảm coupling (sự phụ thuộc lẫn nhau).

## Singleton

Đảm bảo class chỉ có một instance (thể hiện) duy nhất và có điểm truy cập global.

```cpp
#include <iostream>
using namespace std;

class Logger {
private:
    Logger() = default;

public:
    Logger(const Logger&) = delete;
    Logger& operator=(const Logger&) = delete;

    static Logger& instance() {
        static Logger logger; // thread-safe từ C++11
        return logger;
    }

    void log(const string& msg) {
        cout << "[log] " << msg << "\n";
    }
};

int main() {
    Logger::instance().log("app started");
}
```

Nên dùng khi:

- Tài nguyên thực sự chỉ nên có một instance: logger, config read-only, registry.

Cẩn thận:

- Làm tăng global state (trạng thái toàn cục).
- Khó test nếu class phụ thuộc trực tiếp singleton.
- Lifetime/destruction order (thứ tự hủy) có thể phức tạp.

## Factory Method

Tách logic tạo object khỏi code sử dụng object.

```cpp
#include <iostream>
#include <memory>
using namespace std;

class Button {
public:
    virtual void render() = 0;
    virtual ~Button() = default;
};

class WindowsButton : public Button {
public:
    void render() override { cout << "Windows button\n"; }
};

class MacButton : public Button {
public:
    void render() override { cout << "Mac button\n"; }
};

unique_ptr<Button> createButton(const string& os) {
    if (os == "windows") return make_unique<WindowsButton>();
    if (os == "mac") return make_unique<MacButton>();
    throw invalid_argument("unknown os");
}

int main() {
    auto button = createButton("windows");
    button->render();
}
```

Lợi ích:

- Code gọi không cần biết class cụ thể.
- Dễ thêm loại object mới.
- Giảm việc dùng `new` rải rác.

## Abstract Factory

Tạo cả họ object liên quan.

```cpp
class Checkbox {
public:
    virtual void render() = 0;
    virtual ~Checkbox() = default;
};

class GUIFactory {
public:
    virtual unique_ptr<Button> createButton() = 0;
    virtual unique_ptr<Checkbox> createCheckbox() = 0;
    virtual ~GUIFactory() = default;
};
```

Dùng khi cần đảm bảo các object cùng "family" (họ), ví dụ WindowsButton + WindowsCheckbox.

## Strategy

Đóng gói thuật toán, cho phép thay đổi hành vi lúc runtime (thời gian chạy).

```cpp
class SortStrategy {
public:
    virtual void sort(vector<int>& data) = 0;
    virtual ~SortStrategy() = default;
};

class QuickSort : public SortStrategy {
public:
    void sort(vector<int>& data) override {
        std::sort(data.begin(), data.end());
    }
};

class Context {
private:
    unique_ptr<SortStrategy> strategy;

public:
    Context(unique_ptr<SortStrategy> s) : strategy(move(s)) {}
    void run(vector<int>& data) {
        strategy->sort(data);
    }
};
```

## Observer

Một object thay đổi thì thông báo cho các observer (người quan sát).

```cpp
class Observer {
public:
    virtual void update(int value) = 0;
    virtual ~Observer() = default;
};

class Subject {
private:
    vector<Observer*> observers;
    int value = 0;

public:
    void attach(Observer* o) { observers.push_back(o); }

    void setValue(int v) {
        value = v;
        for (Observer* o : observers) {
            o->update(value);
        }
    }
};
```

Dùng trong event system (hệ thống sự kiện), UI, pub-sub.

## Khi nào nên dùng pattern?

- Singleton: cần một instance duy nhất, chấp nhận global access.
- Factory: logic tạo object phức tạp hoặc phụ thuộc config.
- Strategy: có nhiều thuật toán thay thế nhau.
- Observer: cần thông báo sự kiện one-to-many (một-nhiều).

## Nhóm pattern theo mục đích

| Nhóm | Mục đích | Ví dụ |
|---|---|---|
| Creational | Tạo object linh hoạt | Singleton, Factory, Abstract Factory, Builder |
| Structural | Tổ chức quan hệ giữa object/class | Adapter, Facade, Decorator |
| Behavioral | Tổ chức hành vi/giao tiếp | Strategy, Observer, Command, State |

Khi trả lời interview, nên nói vấn đề trước rồi mới nói pattern. Pattern là công cụ, không phải mục tiêu.

## Builder

Builder dùng khi object có nhiều tham số cấu hình, nhất là tham số optional, và constructor dài khó đọc.

```cpp
class HttpRequest {
private:
    string url;
    string method = "GET";
    map<string, string> headers;

public:
    class Builder {
    private:
        HttpRequest req;

    public:
        Builder& url(string value) {
            req.url = move(value);
            return *this;
        }

        Builder& method(string value) {
            req.method = move(value);
            return *this;
        }

        Builder& header(string key, string value) {
            req.headers[move(key)] = move(value);
            return *this;
        }

        HttpRequest build() {
            return move(req);
        }
    };
};
```

Trong C++ thực tế, đôi khi chỉ cần aggregate/options struct là đủ:

```cpp
struct RequestOptions {
    string method = "GET";
    int timeoutMs = 3000;
};
```

Không nên dùng Builder nếu object đơn giản.

## Adapter

Adapter chuyển interface không tương thích thành interface mà client mong muốn.

```cpp
class OldPrinter {
public:
    void printText(const string& text) {}
};

class Printer {
public:
    virtual void print(const string& text) = 0;
    virtual ~Printer() = default;
};

class PrinterAdapter : public Printer {
private:
    OldPrinter& oldPrinter;

public:
    PrinterAdapter(OldPrinter& p) : oldPrinter(p) {}

    void print(const string& text) override {
        oldPrinter.printText(text);
    }
};
```

Dùng khi cần tích hợp thư viện cũ/third-party mà không muốn code chính phụ thuộc trực tiếp API đó.

## Facade

Facade cung cấp interface đơn giản cho một hệ thống phức tạp.

```cpp
class VideoConverter {
public:
    void convert(const string& input, const string& output) {
        // gọi decoder, encoder, muxer...
    }
};
```

Facade không nhất thiết che giấu hết sức mạnh bên dưới. Nó chỉ tạo entry point dễ dùng cho use case phổ biến.

## Decorator

Decorator thêm hành vi cho object mà không sửa class gốc.

```cpp
class DataSource {
public:
    virtual string read() = 0;
    virtual void write(const string& data) = 0;
    virtual ~DataSource() = default;
};

class EncryptionDecorator : public DataSource {
private:
    unique_ptr<DataSource> wrappee;

public:
    EncryptionDecorator(unique_ptr<DataSource> source)
        : wrappee(move(source)) {}

    string read() override {
        return decrypt(wrappee->read());
    }

    void write(const string& data) override {
        wrappee->write(encrypt(data));
    }
};
```

Ý tưởng: object wrapper có cùng interface với object gốc, nên có thể xếp lớp nhiều decorator.

## Command

Command đóng gói một hành động thành object. Dùng cho undo/redo, queue job, logging action.

```cpp
class Command {
public:
    virtual void execute() = 0;
    virtual ~Command() = default;
};

class SaveCommand : public Command {
public:
    void execute() override {
        // save document
    }
};
```

Nếu cần undo:

```cpp
class UndoableCommand {
public:
    virtual void execute() = 0;
    virtual void undo() = 0;
    virtual ~UndoableCommand() = default;
};
```

## State

State giống Strategy ở chỗ đóng gói hành vi, nhưng State biểu diễn trạng thái nội bộ và cho phép object đổi hành vi khi state thay đổi.

Ví dụ: TCP connection có `Closed`, `Listen`, `Established`. Mỗi state xử lý event khác nhau.

```cpp
class ConnectionState {
public:
    virtual void onData() = 0;
    virtual ~ConnectionState() = default;
};
```

## Pattern và SOLID

- Strategy hỗ trợ Open/Closed: thêm thuật toán mới không sửa context nhiều.
- Factory hỗ trợ Dependency Inversion: client nhận abstraction.
- Observer hỗ trợ tách publisher/subscriber.
- Adapter giảm coupling với API bên ngoài.

Nhưng pattern cũng có chi phí: thêm class, thêm indirection, khó debug hơn nếu lạm dụng.

## Câu hỏi bẫy thường gặp

1. Singleton có phải anti-pattern không?

Không phải lúc nào cũng xấu, nhưng dễ thành global state, khó test và ẩn dependency. Nếu dùng, nên giới hạn trách nhiệm và cân nhắc dependency injection.

2. Factory Method khác Abstract Factory?

Factory Method thường tạo một product thông qua method/creator. Abstract Factory tạo cả họ product liên quan, ví dụ button + checkbox cùng style Windows.

3. Strategy khác State?

Strategy thường do client chọn thuật toán. State thường do object tự đổi state theo lifecycle/event.
