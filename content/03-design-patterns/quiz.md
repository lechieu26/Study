# Quiz - Design Patterns

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Design Patterns được chia thành mấy nhóm chính?

- [ ] 2 nhóm: Creational và Structural
- [x] 3 nhóm: Creational, Structural, Behavioral
- [ ] 4 nhóm: Creational, Structural, Behavioral, Architectural
- [ ] 5 nhóm

> **Giải thích:** Gang of Four (GoF) chia 23 patterns thành 3 nhóm: Creational (tạo object), Structural (cấu trúc class/object), Behavioral (hành vi tương tác).

## Câu 2

[TYPE: SELECT_RESULT]

Cho đoạn code sau:

```java
class Singleton {
    private static Singleton instance;
    private Singleton() {}
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
Singleton a = Singleton.getInstance();
Singleton b = Singleton.getInstance();
System.out.println(a == b);
```

- [x] true
- [ ] false
- [ ] Lỗi biên dịch
- [ ] NullPointerException

> **Giải thích:** Singleton đảm bảo chỉ 1 instance. Lần gọi đầu tạo mới, lần sau trả về instance cũ → `a == b` → true.

## Câu 3

[TYPE: FILL_BLANK]

Pattern nào tạo đối tượng mà không cần chỉ rõ class cụ thể, thường dùng method tĩnh trả về interface? Đó là `___` Pattern.

- [ ] Singleton
- [x] Factory Method
- [ ] Builder
- [ ] Prototype

> **Giải thích:** Factory Method: tạo object qua method thay vì `new`. Client gọi factory method, nhận về interface/abstract type mà không biết concrete class.

## Câu 4

[TYPE: TRUE_FALSE]

Mệnh đề: "Singleton pattern lazy initialization (tạo instance khi cần) là thread-safe mặc định."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Lazy Singleton không thread-safe vì 2 thread có thể vào if(instance==null) cùng lúc. Cần `synchronized`, double-checked locking, hoặc enum/inner class.

## Câu 5

[TYPE: SELECT_RESULT]

Cho đoạn code Factory Method:

```java
interface Shape { String draw(); }
class Circle implements Shape { public String draw() { return "Circle"; } }
class Square implements Shape { public String draw() { return "Square"; } }

class ShapeFactory {
    static Shape create(String type) {
        return switch(type) {
            case "circle" -> new Circle();
            case "square" -> new Square();
            default -> throw new IllegalArgumentException("Unknown: " + type);
        };
    }
}
Shape s = ShapeFactory.create("circle");
System.out.println(s.draw());
```

- [x] Circle
- [ ] Square
- [ ] Shape
- [ ] IllegalArgumentException

> **Giải thích:** Factory tạo Circle khi type="circle". `s.draw()` → Circle.draw() → "Circle".

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Builder Pattern giải quyết vấn đề gì?

- [ ] Tạo chỉ 1 instance
- [x] Tạo object phức tạp từng bước, tránh constructor nhiều tham số
- [ ] Clone object
- [ ] Tạo family of related objects

> **Giải thích:** Builder tách quá trình xây dựng object phức tạp. Thay vì constructor 10 tham số, dùng method chain: `new Builder().name("A").age(25).build()`.

## Câu 7

[TYPE: SELECT_RESULT]

Cho đoạn code Builder:

```java
class User {
    String name; int age; String email;
    static class Builder {
        String name; int age; String email;
        Builder name(String n) { this.name = n; return this; }
        Builder age(int a) { this.age = a; return this; }
        Builder email(String e) { this.email = e; return this; }
        User build() {
            User u = new User();
            u.name = name; u.age = age; u.email = email;
            return u;
        }
    }
}
User user = new User.Builder().name("An").age(25).build();
System.out.println(user.name + ":" + user.age + ":" + user.email);
```

- [x] An:25:null
- [ ] An:25:
- [ ] Lỗi biên dịch
- [ ] An:0:null

> **Giải thích:** Builder set name="An", age=25. email không set → null (default cho String). Builder cho phép optional parameters.

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Prototype Pattern dùng cơ chế nào để tạo object mới?

- [ ] Constructor
- [x] Clone (sao chép) từ object hiện có
- [ ] Factory method
- [ ] Reflection

> **Giải thích:** Prototype tạo object mới bằng cách clone object hiện có. Dùng khi tạo object tốn kém (nhiều tính toán, I/O). Implement `Cloneable` interface trong Java.

## Câu 9

[TYPE: SELECT_RESULT]

Cho đoạn code Adapter:

```java
interface MediaPlayer { void play(String filename); }
class Mp3Player { void playMp3(String f) { System.out.println("MP3: " + f); } }

class Mp3Adapter implements MediaPlayer {
    Mp3Player player = new Mp3Player();
    public void play(String filename) { player.playMp3(filename); }
}
MediaPlayer player = new Mp3Adapter();
player.play("song.mp3");
```

- [x] MP3: song.mp3
- [ ] Lỗi biên dịch
- [ ] null
- [ ] song.mp3

> **Giải thích:** Adapter wraps Mp3Player để phù hợp interface MediaPlayer. `play()` delegate sang `playMp3()`. Client sử dụng qua MediaPlayer interface.

## Câu 10

[TYPE: TRUE_FALSE]

Mệnh đề: "Abstract Factory tạo family of related objects mà không cần chỉ rõ concrete class."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Abstract Factory cung cấp interface để tạo nhóm objects liên quan. Ví dụ: `UIFactory.createButton()`, `UIFactory.createTextField()` → Windows hoặc Mac style.

## Câu 11

[TYPE: SELECT_RESULT]

Cho đoạn code Decorator:

```java
interface Notifier { void send(String msg); }
class EmailNotifier implements Notifier {
    public void send(String msg) { System.out.print("Email:" + msg); }
}
class SMSDecorator implements Notifier {
    Notifier wrapped;
    SMSDecorator(Notifier n) { this.wrapped = n; }
    public void send(String msg) {
        wrapped.send(msg);
        System.out.print(" SMS:" + msg);
    }
}
Notifier n = new SMSDecorator(new EmailNotifier());
n.send("Hi");
```

- [x] Email:Hi SMS:Hi
- [ ] SMS:Hi Email:Hi
- [ ] Email:Hi
- [ ] SMS:Hi

> **Giải thích:** Decorator wraps EmailNotifier. `send()` gọi wrapped.send() trước → "Email:Hi", rồi thêm " SMS:Hi". Decorator mở rộng behavior mà không sửa class gốc.

## Câu 12

[TYPE: MULTIPLE_CHOICE]

Observer Pattern giải quyết vấn đề gì?

- [ ] Tạo object phức tạp
- [ ] Chuyển đổi interface
- [x] Thông báo cho nhiều đối tượng khi trạng thái thay đổi (one-to-many)
- [ ] Giới hạn số lượng instance

> **Giải thích:** Observer: khi subject thay đổi, tự động notify tất cả observers đã đăng ký. Ví dụ: EventBus, Event Listener, Pub/Sub.

## Câu 13

[TYPE: SELECT_RESULT]

Cho đoạn code Observer:

```java
interface Observer { void update(String event); }
class EventEmitter {
    List<Observer> observers = new ArrayList<>();
    void on(Observer o) { observers.add(o); }
    void emit(String event) { observers.forEach(o -> o.update(event)); }
}
EventEmitter emitter = new EventEmitter();
StringBuilder log = new StringBuilder();
emitter.on(e -> log.append("A:" + e + " "));
emitter.on(e -> log.append("B:" + e));
emitter.emit("click");
System.out.println(log);
```

- [x] A:click B:click
- [ ] B:click A:click
- [ ] A:click
- [ ] Lỗi biên dịch

> **Giải thích:** 2 observers đăng ký. `emit("click")` notify theo thứ tự đăng ký: A trước, B sau.

## Câu 14

[TYPE: FILL_BLANK]

Pattern nào cho phép thay đổi thuật toán tại runtime bằng cách đóng gói mỗi thuật toán trong class riêng? Đó là `___` Pattern.

- [ ] Observer
- [x] Strategy
- [ ] Template Method
- [ ] Command

> **Giải thích:** Strategy encapsulate family of algorithms, cho phép swap tại runtime. Ví dụ: `SortStrategy` có BubbleSort, QuickSort, MergeSort.

## Câu 15

[TYPE: SELECT_RESULT]

Cho đoạn code Strategy:

```java
interface Discount { double apply(double price); }
class NoDiscount implements Discount {
    public double apply(double price) { return price; }
}
class PercentDiscount implements Discount {
    double percent;
    PercentDiscount(double p) { this.percent = p; }
    public double apply(double price) { return price * (1 - percent / 100); }
}
Discount strategy = new PercentDiscount(20);
System.out.println(strategy.apply(100));
```

- [x] 80.0
- [ ] 100.0
- [ ] 20.0
- [ ] 120.0

> **Giải thích:** PercentDiscount(20) → giảm 20%. 100 * (1 - 20/100) = 100 * 0.8 = 80.0.

## Câu 16

[TYPE: MULTIPLE_CHOICE]

Facade Pattern có mục đích gì?

- [x] Cung cấp interface đơn giản cho một hệ thống phức tạp
- [ ] Tạo object từ factory
- [ ] Thêm behavior cho object
- [ ] Clone object

> **Giải thích:** Facade ẩn sự phức tạp bên trong, cung cấp API đơn giản. Ví dụ: `OrderFacade.placeOrder()` bao gồm checkInventory, processPayment, shipOrder.

## Câu 17

[TYPE: SELECT_RESULT]

Cho đoạn code Template Method:

```java
abstract class Report {
    final void generate() {
        header();
        body();
        footer();
    }
    abstract void header();
    abstract void body();
    void footer() { System.out.print("---End"); }
}
class HTMLReport extends Report {
    void header() { System.out.print("<h1>Title</h1> "); }
    void body() { System.out.print("<p>Content</p> "); }
}
new HTMLReport().generate();
```

- [x] <h1>Title</h1> <p>Content</p> ---End
- [ ] ---End
- [ ] <h1>Title</h1> <p>Content</p>
- [ ] Lỗi biên dịch

> **Giải thích:** Template Method: `generate()` là template (final), định nghĩa skeleton. Subclass implement `header()` và `body()`, `footer()` dùng default.

## Câu 18

[TYPE: TRUE_FALSE]

Mệnh đề: "Proxy Pattern và Decorator Pattern đều wrap object, nhưng Proxy kiểm soát truy cập còn Decorator thêm behavior."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Proxy: kiểm soát truy cập (lazy loading, caching, security check). Decorator: thêm tính năng mới. Cả hai đều implement cùng interface và wrap object gốc.

## Câu 19

[TYPE: SELECT_RESULT]

Cho đoạn code Proxy:

```java
interface Image { void display(); }
class RealImage implements Image {
    String filename;
    RealImage(String f) { this.filename = f; System.out.print("Loading:" + f + " "); }
    public void display() { System.out.print("Display:" + filename); }
}
class ProxyImage implements Image {
    String filename;
    RealImage realImage;
    ProxyImage(String f) { this.filename = f; }
    public void display() {
        if (realImage == null) realImage = new RealImage(filename);
        realImage.display();
    }
}
Image img = new ProxyImage("photo.jpg");
System.out.print("Created ");
img.display();
```

- [x] Created Loading:photo.jpg Display:photo.jpg
- [ ] Loading:photo.jpg Created Display:photo.jpg
- [ ] Created Display:photo.jpg
- [ ] Loading:photo.jpg Display:photo.jpg Created

> **Giải thích:** ProxyImage lazy loads. Khi tạo ProxyImage → không load ảnh, in "Created". Khi display() → tạo RealImage (in "Loading:...") → display (in "Display:...").

## Câu 20

[TYPE: MULTIPLE_CHOICE]

Command Pattern encapsulate gì?

- [ ] Object creation
- [x] Request/action thành object, cho phép undo, queue, log
- [ ] Object structure
- [ ] Algorithm family

> **Giải thích:** Command encapsulate request thành object với execute()/undo(). Ví dụ: text editor commands (TypeCommand, DeleteCommand) có thể undo/redo.

## Câu 21

[TYPE: SELECT_RESULT]

Cho đoạn code Command:

```java
interface Command { void execute(); }
class Light {
    void on() { System.out.print("ON "); }
    void off() { System.out.print("OFF "); }
}
class LightOnCmd implements Command {
    Light light;
    LightOnCmd(Light l) { this.light = l; }
    public void execute() { light.on(); }
}
class LightOffCmd implements Command {
    Light light;
    LightOffCmd(Light l) { this.light = l; }
    public void execute() { light.off(); }
}
Light light = new Light();
List<Command> commands = List.of(new LightOnCmd(light), new LightOffCmd(light));
commands.forEach(Command::execute);
```

- [x] ON OFF
- [ ] OFF ON
- [ ] ON
- [ ] Lỗi biên dịch

> **Giải thích:** Hai command: ON rồi OFF. forEach thực thi lần lượt → "ON OFF".

## Câu 22

[TYPE: FILL_BLANK]

Pattern nào cho phép object thay đổi hành vi khi trạng thái nội bộ thay đổi, như thể đang thay đổi class? Đó là `___` Pattern.

- [ ] Strategy
- [x] State
- [ ] Observer
- [ ] Memento

> **Giải thích:** State: mỗi state là một class riêng, object delegate behavior cho state hiện tại. Khi state thay đổi → behavior thay đổi. Giống Strategy nhưng state tự chuyển.

## Câu 23

[TYPE: SELECT_RESULT]

Cho đoạn code State:

```java
interface State { String handle(); }
class Locked implements State { public String handle() { return "Locked->Unlocked"; } }
class Unlocked implements State { public String handle() { return "Unlocked->Locked"; } }

class Door {
    State state = new Locked();
    String toggle() {
        String result = state.handle();
        state = (state instanceof Locked) ? new Unlocked() : new Locked();
        return result;
    }
}
Door door = new Door();
System.out.println(door.toggle());
System.out.println(door.toggle());
```

- [x] Locked->Unlocked và Unlocked->Locked
- [ ] Locked->Unlocked và Locked->Unlocked
- [ ] Unlocked->Locked và Locked->Unlocked
- [ ] Lỗi biên dịch

> **Giải thích:** Ban đầu Locked. Toggle 1: handle() → "Locked->Unlocked", chuyển sang Unlocked. Toggle 2: handle() → "Unlocked->Locked", chuyển về Locked.

## Câu 24

[TYPE: MULTIPLE_CHOICE]

Composite Pattern dùng để làm gì?

- [ ] Tạo object phức tạp
- [x] Tổ chức objects thành cấu trúc cây (tree), xử lý leaf và composite đồng nhất
- [ ] Thêm behavior cho object
- [ ] Cache kết quả

> **Giải thích:** Composite: folder chứa files và sub-folders, tất cả implement chung interface. Client xử lý leaf (file) và composite (folder) giống nhau.

## Câu 25

[TYPE: SELECT_RESULT]

Cho đoạn code Composite:

```java
interface Component { int getSize(); }
class File implements Component {
    int size;
    File(int s) { this.size = s; }
    public int getSize() { return size; }
}
class Folder implements Component {
    List<Component> children = new ArrayList<>();
    void add(Component c) { children.add(c); }
    public int getSize() { return children.stream().mapToInt(Component::getSize).sum(); }
}
Folder root = new Folder();
root.add(new File(10));
Folder sub = new Folder();
sub.add(new File(20));
sub.add(new File(30));
root.add(sub);
System.out.println(root.getSize());
```

- [ ] 10
- [ ] 50
- [x] 60
- [ ] 30

> **Giải thích:** root: File(10) + Folder(File(20)+File(30)). root.getSize() = 10 + (20+30) = 60. Composite tính tổng đệ quy.

## Câu 26

[TYPE: TRUE_FALSE]

Mệnh đề: "Bridge Pattern tách abstraction khỏi implementation để cả hai có thể thay đổi độc lập."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Bridge: tách "what" (abstraction) khỏi "how" (implementation). Ví dụ: Shape (Circle, Square) × Drawing API (OpenGL, DirectX) → kết hợp bất kỳ.

## Câu 27

[TYPE: SELECT_RESULT]

Cho đoạn code Chain of Responsibility:

```java
abstract class Handler {
    Handler next;
    Handler setNext(Handler h) { this.next = h; return h; }
    void handle(int amount) {
        if (canHandle(amount)) process(amount);
        else if (next != null) next.handle(amount);
        else System.out.println("Rejected");
    }
    abstract boolean canHandle(int amount);
    abstract void process(int amount);
}
class Manager extends Handler {
    boolean canHandle(int a) { return a <= 1000; }
    void process(int a) { System.out.println("Manager approved " + a); }
}
class Director extends Handler {
    boolean canHandle(int a) { return a <= 5000; }
    void process(int a) { System.out.println("Director approved " + a); }
}
Handler chain = new Manager();
chain.setNext(new Director());
chain.handle(3000);
```

- [ ] Manager approved 3000
- [x] Director approved 3000
- [ ] Rejected
- [ ] Lỗi biên dịch

> **Giải thích:** Manager can handle <= 1000, 3000 > 1000 → pass to next. Director can handle <= 5000, 3000 <= 5000 → "Director approved 3000".

## Câu 28

[TYPE: MULTIPLE_CHOICE]

Iterator Pattern dùng để làm gì?

- [x] Truy cập tuần tự các phần tử của collection mà không expose cấu trúc bên trong
- [ ] Tạo bản sao của collection
- [ ] Sắp xếp collection
- [ ] Filter collection

> **Giải thích:** Iterator cung cấp cách duyệt qua elements mà không biết internal structure (array, linked list, tree). Java: `Iterator<T>`, enhanced for loop.

## Câu 29

[TYPE: SELECT_RESULT]

Cho đoạn code Flyweight:

```java
class CharFactory {
    Map<Character, String> cache = new HashMap<>();
    String getChar(char c) {
        return cache.computeIfAbsent(c, k -> {
            System.out.print("Create:" + k + " ");
            return "Obj_" + k;
        });
    }
}
CharFactory f = new CharFactory();
f.getChar('A');
f.getChar('B');
f.getChar('A');
System.out.print("Size:" + f.cache.size());
```

- [x] Create:A Create:B Size:2
- [ ] Create:A Create:B Create:A Size:3
- [ ] Create:A Create:B Size:3
- [ ] Size:2

> **Giải thích:** Flyweight chia sẻ objects tốn bộ nhớ. 'A' tạo lần đầu, 'B' tạo lần đầu, 'A' lần 2 dùng cache. Size = 2.

## Câu 30

[TYPE: TRUE_FALSE]

Mệnh đề: "Mediator Pattern giảm coupling bằng cách thay thế direct communication giữa objects bằng communication qua mediator."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Mediator: thay vì N objects giao tiếp trực tiếp (N×N connections), tất cả giao tiếp qua 1 mediator (N connections). Ví dụ: Chat Room, Air Traffic Controller.

## Câu 31

[TYPE: SELECT_RESULT]

Cho đoạn code Abstract Factory:

```java
interface Button { String render(); }
interface TextField { String render(); }
interface UIFactory {
    Button createButton();
    TextField createTextField();
}
class WinButton implements Button { public String render() { return "WinBtn"; } }
class WinTextField implements TextField { public String render() { return "WinTxt"; } }
class WinFactory implements UIFactory {
    public Button createButton() { return new WinButton(); }
    public TextField createTextField() { return new WinTextField(); }
}
UIFactory factory = new WinFactory();
System.out.println(factory.createButton().render() + " " + factory.createTextField().render());
```

- [x] WinBtn WinTxt
- [ ] Button TextField
- [ ] Lỗi biên dịch
- [ ] null null

> **Giải thích:** Abstract Factory tạo family of related objects. WinFactory tạo Windows-style components: WinBtn, WinTxt.

## Câu 32

[TYPE: MULTIPLE_CHOICE]

Đâu là Structural Design Pattern?

- [ ] Factory, Builder, Singleton
- [x] Adapter, Decorator, Proxy, Facade, Bridge, Composite, Flyweight
- [ ] Observer, Strategy, Command
- [ ] Iterator, Mediator, State

> **Giải thích:** Structural patterns liên quan đến cách tổ chức class/object. Adapter, Bridge, Composite, Decorator, Facade, Flyweight, Proxy.

## Câu 33

[TYPE: SELECT_RESULT]

Cho đoạn code Memento:

```java
class TextEditor {
    private String text = "";
    void type(String t) { text += t; }
    String getText() { return text; }
    String save() { return text; }
    void restore(String memento) { text = memento; }
}
TextEditor editor = new TextEditor();
editor.type("Hello");
String saved = editor.save();
editor.type(" World");
System.out.print(editor.getText() + " | ");
editor.restore(saved);
System.out.print(editor.getText());
```

- [x] Hello World | Hello
- [ ] Hello | Hello
- [ ] Hello World | Hello World
- [ ] | Hello

> **Giải thích:** Memento lưu trạng thái để khôi phục. Save "Hello", type thêm → "Hello World". Restore → quay về "Hello".

## Câu 34

[TYPE: FILL_BLANK]

Pattern nào cho phép định nghĩa skeleton của algorithm trong method cha, để subclass override các bước cụ thể? Đó là `___` Pattern.

- [ ] Strategy
- [x] Template Method
- [ ] Observer
- [ ] Factory

> **Giải thích:** Template Method: base class có final method với skeleton, gọi abstract/hook methods. Subclass override từng bước mà không thay đổi cấu trúc tổng thể.

## Câu 35

[TYPE: SELECT_RESULT]

Cho đoạn code Visitor:

```java
interface Shape { String accept(ShapeVisitor v); }
interface ShapeVisitor {
    String visit(Circle c);
    String visit(Rect r);
}
class Circle implements Shape {
    double r;
    Circle(double r) { this.r = r; }
    public String accept(ShapeVisitor v) { return v.visit(this); }
}
class Rect implements Shape {
    double w, h;
    Rect(double w, double h) { this.w = w; this.h = h; }
    public String accept(ShapeVisitor v) { return v.visit(this); }
}
class AreaVisitor implements ShapeVisitor {
    public String visit(Circle c) { return String.format("%.1f", Math.PI * c.r * c.r); }
    public String visit(Rect r) { return String.format("%.1f", r.w * r.h); }
}
ShapeVisitor v = new AreaVisitor();
System.out.println(new Circle(5).accept(v));
System.out.println(new Rect(3, 4).accept(v));
```

- [x] 78.5 và 12.0
- [ ] 25.0 và 12.0
- [ ] Lỗi biên dịch
- [ ] 0.0 và 0.0

> **Giải thích:** Visitor pattern: thêm operation mới (AreaVisitor) mà không sửa class Shape. Circle: π×5² ≈ 78.5. Rect: 3×4 = 12.0.

## Câu 36

[TYPE: MULTIPLE_CHOICE]

Khi nào nên dùng Singleton Pattern?

- [ ] Khi cần nhiều instance
- [x] Khi cần đảm bảo chỉ có một instance (database connection, configuration, logger)
- [ ] Khi cần clone object
- [ ] Khi cần tạo object phức tạp

> **Giải thích:** Singleton dùng cho resource chỉ cần 1 instance: Database Connection Pool, Configuration Manager, Logger, Cache Manager.

## Câu 37

[TYPE: SELECT_RESULT]

Cho đoạn code Enum Singleton (thread-safe):

```java
enum DatabaseConnection {
    INSTANCE;
    private int queryCount = 0;
    void executeQuery() { queryCount++; }
    int getQueryCount() { return queryCount; }
}
DatabaseConnection db1 = DatabaseConnection.INSTANCE;
DatabaseConnection db2 = DatabaseConnection.INSTANCE;
db1.executeQuery();
db2.executeQuery();
System.out.println(db1.getQueryCount());
System.out.println(db1 == db2);
```

- [x] 2 và true
- [ ] 1 và true
- [ ] 2 và false
- [ ] 1 và false

> **Giải thích:** Enum Singleton: chỉ 1 instance. db1 == db2 → true. executeQuery() gọi 2 lần → queryCount = 2.

## Câu 38

[TYPE: TRUE_FALSE]

Mệnh đề: "Strategy Pattern và State Pattern có cấu trúc giống nhau nhưng khác mục đích."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Cả hai đều delegate behavior cho object qua interface. Strategy: client chọn algorithm. State: object tự chuyển state, behavior thay đổi theo state.

## Câu 39

[TYPE: SELECT_RESULT]

Cho đoạn code Adapter (Object Adapter):

```java
interface Target { String request(); }
class Adaptee { String specificRequest() { return "Adaptee"; } }
class Adapter implements Target {
    Adaptee adaptee;
    Adapter(Adaptee a) { this.adaptee = a; }
    public String request() { return "Adapted: " + adaptee.specificRequest(); }
}
Target target = new Adapter(new Adaptee());
System.out.println(target.request());
```

- [x] Adapted: Adaptee
- [ ] Adaptee
- [ ] null
- [ ] Lỗi biên dịch

> **Giải thích:** Object Adapter: wraps Adaptee bằng composition. request() delegate sang specificRequest() và format kết quả.

## Câu 40

[TYPE: MULTIPLE_CHOICE]

Decorator khác Inheritance ở điểm nào khi mở rộng behavior?

- [x] Decorator mở rộng behavior tại runtime, Inheritance tại compile-time
- [ ] Inheritance linh hoạt hơn Decorator
- [ ] Decorator chậm hơn
- [ ] Không có sự khác biệt

> **Giải thích:** Decorator: wrap object tại runtime, có thể stack nhiều decorators. Inheritance: fixed tại compile-time. Decorator tuân thủ OCP (Open/Closed).

## Câu 41

[TYPE: SELECT_RESULT]

Cho đoạn code Facade:

```java
class CPU { String freeze() { return "CPU freeze"; } }
class Memory { String load(String data) { return "Memory load " + data; } }
class Disk { String read() { return "sector5"; } }

class ComputerFacade {
    CPU cpu = new CPU(); Memory mem = new Memory(); Disk disk = new Disk();
    String start() {
        return cpu.freeze() + " > " + mem.load(disk.read()) + " > Boot OK";
    }
}
System.out.println(new ComputerFacade().start());
```

- [x] CPU freeze > Memory load sector5 > Boot OK
- [ ] Boot OK
- [ ] CPU freeze
- [ ] Lỗi biên dịch

> **Giải thích:** Facade ẩn sự phức tạp (CPU, Memory, Disk) sau 1 method `start()`. Client chỉ cần gọi `start()`.

## Câu 42

[TYPE: FILL_BLANK]

Pattern nào tách một request khỏi receiver, cho phép parameterize client với request, queue request, hoặc log request? Đó là `___` Pattern.

- [x] Command
- [ ] Observer
- [ ] Strategy
- [ ] Mediator

> **Giải thích:** Command: encapsulate request thành object. Cho phép: undo/redo, macro (batch commands), queue, log actions.

## Câu 43

[TYPE: SELECT_RESULT]

Cho đoạn code Prototype:

```java
class Settings implements Cloneable {
    String theme;
    List<String> plugins;
    Settings(String t, List<String> p) { this.theme = t; this.plugins = new ArrayList<>(p); }
    Settings copy() {
        try { return (Settings) super.clone(); }
        catch (Exception e) { return null; }
    }
}
Settings s1 = new Settings("dark", new ArrayList<>(List.of("A", "B")));
Settings s2 = s1.copy();
s2.theme = "light";
s2.plugins.add("C");
System.out.println(s1.theme + ":" + s1.plugins.size());
System.out.println(s2.theme + ":" + s2.plugins.size());
```

- [ ] dark:2 và light:3
- [x] dark:3 và light:3
- [ ] light:3 và light:3
- [ ] dark:2 và light:2

> **Giải thích:** Shallow clone: theme là String (immutable) nên riêng biệt. Nhưng plugins list được share reference → s2.add("C") ảnh hưởng s1. Cần deep copy cho mutable objects!

## Câu 44

[TYPE: MULTIPLE_CHOICE]

Null Object Pattern giải quyết vấn đề gì?

- [x] Tránh null checks bằng cách cung cấp object "trống" thay vì null
- [ ] Tạo object từ null
- [ ] Kiểm tra null tại compile-time
- [ ] Convert null sang empty string

> **Giải thích:** Thay vì `if (logger != null) logger.log(msg)`, dùng NullLogger implements Logger với method body rỗng. Không cần check null.

## Câu 45

[TYPE: SELECT_RESULT]

Cho đoạn code Strategy với lambda:

```java
interface Validator { boolean validate(String input); }
class FormValidator {
    Map<String, Validator> rules = new HashMap<>();
    void addRule(String field, Validator v) { rules.put(field, v); }
    boolean validate(Map<String, String> data) {
        for (var entry : rules.entrySet()) {
            if (!entry.getValue().validate(data.getOrDefault(entry.getKey(), "")))
                return false;
        }
        return true;
    }
}
FormValidator v = new FormValidator();
v.addRule("email", s -> s.contains("@"));
v.addRule("name", s -> s.length() >= 2);
System.out.println(v.validate(Map.of("email", "a@b.com", "name", "An")));
System.out.println(v.validate(Map.of("email", "invalid", "name", "An")));
```

- [x] true và false
- [ ] true và true
- [ ] false và false
- [ ] false và true

> **Giải thích:** Validation 1: email chứa "@" ✓, name >= 2 ✓ → true. Validation 2: email "invalid" không chứa "@" → false.

## Câu 46

[TYPE: TRUE_FALSE]

Mệnh đề: "Flyweight Pattern chia sẻ objects để giảm memory khi có nhiều objects tương tự."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Flyweight: chia sẻ common state (intrinsic) giữa objects. Ví dụ: Character objects trong text editor — font, style chia sẻ, position riêng.

## Câu 47

[TYPE: SELECT_RESULT]

Cho đoạn code Bridge:

```java
interface Color { String fill(); }
class Red implements Color { public String fill() { return "Red"; } }
class Blue implements Color { public String fill() { return "Blue"; } }

abstract class Shape {
    Color color;
    Shape(Color c) { this.color = c; }
    abstract String draw();
}
class Circle extends Shape {
    Circle(Color c) { super(c); }
    String draw() { return "Circle:" + color.fill(); }
}
System.out.println(new Circle(new Red()).draw());
System.out.println(new Circle(new Blue()).draw());
```

- [x] Circle:Red và Circle:Blue
- [ ] Circle:Circle và Red:Blue
- [ ] Red và Blue
- [ ] Lỗi biên dịch

> **Giải thích:** Bridge: Shape (abstraction) × Color (implementation). Circle+Red → "Circle:Red". Circle+Blue → "Circle:Blue". Cả hai thay đổi độc lập.

## Câu 48

[TYPE: MULTIPLE_CHOICE]

MVC Pattern gồm những component nào?

- [x] Model (data), View (UI), Controller (logic điều hướng)
- [ ] Module, Variable, Class
- [ ] Manager, Validator, Creator
- [ ] Memory, Volume, Cache

> **Giải thích:** MVC: Model (dữ liệu + business logic), View (giao diện hiển thị), Controller (xử lý input, điều phối Model và View).

## Câu 49

[TYPE: SELECT_RESULT]

Cho đoạn code Observer với Java built-in:

```java
class EventSystem {
    Map<String, List<Consumer<String>>> listeners = new HashMap<>();
    
    void on(String event, Consumer<String> handler) {
        listeners.computeIfAbsent(event, k -> new ArrayList<>()).add(handler);
    }
    void emit(String event, String data) {
        listeners.getOrDefault(event, List.of()).forEach(h -> h.accept(data));
    }
}
EventSystem es = new EventSystem();
StringBuilder log = new StringBuilder();
es.on("save", d -> log.append("Saved:" + d + " "));
es.on("save", d -> log.append("Logged:" + d));
es.on("delete", d -> log.append("Deleted:" + d));
es.emit("save", "file.txt");
System.out.println(log);
```

- [x] Saved:file.txt Logged:file.txt
- [ ] Saved:file.txt Logged:file.txt Deleted:file.txt
- [ ] Logged:file.txt Saved:file.txt
- [ ] Lỗi biên dịch

> **Giải thích:** emit("save") chỉ trigger listeners của event "save". "delete" listener không được gọi. Thứ tự: Saved rồi Logged.

## Câu 50

[TYPE: FILL_BLANK]

Pattern nào cung cấp cách truy cập tuần tự các phần tử của aggregate mà không expose internal representation? Đó là `___` Pattern.

- [ ] Composite
- [x] Iterator
- [ ] Visitor
- [ ] Mediator

> **Giải thích:** Iterator: `hasNext()`, `next()` cho phép duyệt collection mà không biết nó là array, linked list, hay tree. Java: `Iterable`, `Iterator`.

## Câu 51

[TYPE: SELECT_RESULT]

Cho đoạn code Double-Checked Locking Singleton:

```java
class ThreadSafeSingleton {
    private static volatile ThreadSafeSingleton instance;
    private ThreadSafeSingleton() {}
    static ThreadSafeSingleton getInstance() {
        if (instance == null) {
            synchronized (ThreadSafeSingleton.class) {
                if (instance == null) {
                    instance = new ThreadSafeSingleton();
                }
            }
        }
        return instance;
    }
}
```

Tại sao cần `volatile`?

- [ ] Để tăng tốc
- [x] Để đảm bảo tất cả thread nhìn thấy giá trị mới nhất, tránh instruction reordering
- [ ] Để cho phép null
- [ ] Không cần thiết

> **Giải thích:** Không có `volatile`, thread khác có thể nhìn thấy object chưa khởi tạo hoàn chỉnh do instruction reordering. `volatile` đảm bảo happens-before relationship.

## Câu 52

[TYPE: MULTIPLE_CHOICE]

Đâu là anti-pattern (pattern xấu)?

- [ ] Factory Method
- [x] God Object (class làm quá nhiều việc)
- [ ] Observer
- [ ] Strategy

> **Giải thích:** Anti-patterns: God Object (vi phạm SRP), Spaghetti Code, Golden Hammer (dùng 1 pattern cho mọi thứ), Lava Flow (code chết không dám xóa).

## Câu 53

[TYPE: SELECT_RESULT]

Cho đoạn code Decorator stacking:

```java
interface Coffee { double cost(); String desc(); }
class Espresso implements Coffee {
    public double cost() { return 2.0; }
    public String desc() { return "Espresso"; }
}
abstract class CoffeeDecorator implements Coffee {
    Coffee coffee;
    CoffeeDecorator(Coffee c) { this.coffee = c; }
}
class Milk extends CoffeeDecorator {
    Milk(Coffee c) { super(c); }
    public double cost() { return coffee.cost() + 0.5; }
    public String desc() { return coffee.desc() + "+Milk"; }
}
class Sugar extends CoffeeDecorator {
    Sugar(Coffee c) { super(c); }
    public double cost() { return coffee.cost() + 0.3; }
    public String desc() { return coffee.desc() + "+Sugar"; }
}
Coffee c = new Sugar(new Milk(new Espresso()));
System.out.println(c.desc() + " = $" + c.cost());
```

- [x] Espresso+Milk+Sugar = $2.8
- [ ] Espresso = $2.0
- [ ] Espresso+Sugar+Milk = $2.8
- [ ] Sugar+Milk+Espresso = $2.8

> **Giải thích:** Stacked decorators: Espresso → +Milk → +Sugar. cost: 2.0 + 0.5 + 0.3 = 2.8. desc builds from inside out.

## Câu 54

[TYPE: TRUE_FALSE]

Mệnh đề: "Adapter Pattern thay đổi interface của class hiện có để phù hợp với interface mong muốn."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Adapter: chuyển đổi interface của class này thành interface khác mà client mong muốn. Không sửa class gốc, chỉ wrap bằng adapter.

## Câu 55

[TYPE: SELECT_RESULT]

Cho đoạn code Repository Pattern:

```java
interface Repository<T> {
    void save(T entity);
    T findById(int id);
    List<T> findAll();
}
class InMemoryRepo implements Repository<String> {
    Map<Integer, String> data = new HashMap<>();
    int nextId = 1;
    public void save(String entity) { data.put(nextId++, entity); }
    public String findById(int id) { return data.get(id); }
    public List<String> findAll() { return new ArrayList<>(data.values()); }
}
Repository<String> repo = new InMemoryRepo();
repo.save("An");
repo.save("Bình");
System.out.println(repo.findById(1));
System.out.println(repo.findAll().size());
```

- [x] An và 2
- [ ] Bình và 2
- [ ] null và 0
- [ ] An và 1

> **Giải thích:** save("An") → id=1, save("Bình") → id=2. findById(1) → "An". findAll().size() → 2.

## Câu 56

[TYPE: MULTIPLE_CHOICE]

Difference giữa Facade và Adapter?

- [x] Facade đơn giản hóa interface phức tạp, Adapter chuyển đổi interface không tương thích
- [ ] Không có sự khác biệt
- [ ] Facade nhanh hơn Adapter
- [ ] Adapter dùng cho nhiều class, Facade chỉ cho 1 class

> **Giải thích:** Facade: cung cấp interface đơn giản cho subsystem phức tạp. Adapter: convert interface của class này thành interface khác mà client cần.

## Câu 57

[TYPE: SELECT_RESULT]

Cho đoạn code Builder với validation:

```java
class Config {
    int port; String host; boolean ssl;
    static class Builder {
        int port = 8080; String host = "localhost"; boolean ssl = false;
        Builder port(int p) {
            if (p < 1 || p > 65535) throw new IllegalArgumentException("Invalid port");
            this.port = p; return this;
        }
        Builder host(String h) { this.host = h; return this; }
        Builder ssl(boolean s) { this.ssl = s; return this; }
        Config build() {
            Config c = new Config(); c.port = port; c.host = host; c.ssl = ssl;
            return c;
        }
    }
}
Config c = new Config.Builder().host("api.com").ssl(true).build();
System.out.println(c.host + ":" + c.port + ":" + c.ssl);
```

- [x] api.com:8080:true
- [ ] localhost:8080:false
- [ ] api.com:0:true
- [ ] Lỗi biên dịch

> **Giải thích:** Builder có defaults: port=8080. Chỉ set host và ssl. build() tạo Config với host="api.com", port=8080 (default), ssl=true.

## Câu 58

[TYPE: FILL_BLANK]

Pattern nào lưu trạng thái nội bộ của object mà không vi phạm encapsulation, cho phép khôi phục sau đó? Đó là `___` Pattern.

- [ ] Command
- [x] Memento
- [ ] State
- [ ] Snapshot

> **Giải thích:** Memento: save và restore state. 3 roles: Originator (object cần save), Memento (snapshot), Caretaker (giữ mementos). Ví dụ: Undo trong text editor.

## Câu 59

[TYPE: SELECT_RESULT]

Cho đoạn code Null Object:

```java
interface Logger { void log(String msg); }
class ConsoleLogger implements Logger {
    public void log(String msg) { System.out.println("LOG: " + msg); }
}
class NullLogger implements Logger {
    public void log(String msg) { /* do nothing */ }
}
class Service {
    Logger logger;
    Service(Logger l) { this.logger = (l != null) ? l : new NullLogger(); }
    void process() {
        logger.log("Processing");
        System.out.println("Done");
    }
}
new Service(null).process();
```

- [ ] LOG: Processing và Done
- [x] Done
- [ ] NullPointerException
- [ ] Lỗi biên dịch

> **Giải thích:** Truyền null → Service dùng NullLogger. NullLogger.log() không làm gì → chỉ in "Done". Tránh NullPointerException.

## Câu 60

[TYPE: MULTIPLE_CHOICE]

Đâu là ưu điểm chính của Design Patterns?

- [ ] Tăng tốc chương trình
- [ ] Giảm số dòng code
- [x] Cung cấp giải pháp đã được kiểm chứng, tạo ngôn ngữ chung giữa developers
- [ ] Tự động fix bugs

> **Giải thích:** Design Patterns: proven solutions, common vocabulary (developer nói "dùng Observer" → tất cả hiểu), code maintainable, reusable, flexible.

## Câu 61

[TYPE: SELECT_RESULT]

Cho đoạn code Proxy (Caching Proxy):

```java
interface DataService { String fetch(String key); }
class RealDataService implements DataService {
    public String fetch(String key) {
        System.out.print("[DB] ");
        return "data_" + key;
    }
}
class CachingProxy implements DataService {
    DataService real;
    Map<String, String> cache = new HashMap<>();
    CachingProxy(DataService r) { this.real = r; }
    public String fetch(String key) {
        return cache.computeIfAbsent(key, k -> real.fetch(k));
    }
}
DataService service = new CachingProxy(new RealDataService());
System.out.print(service.fetch("x") + " ");
System.out.print(service.fetch("x") + " ");
System.out.print(service.fetch("y"));
```

- [x] [DB] data_x data_x [DB] data_y
- [ ] [DB] data_x [DB] data_x [DB] data_y
- [ ] data_x data_x data_y
- [ ] [DB] data_x [DB] data_y

> **Giải thích:** Caching Proxy: lần 1 fetch("x") → gọi DB. Lần 2 fetch("x") → cache hit, không gọi DB. fetch("y") → cache miss → gọi DB.

## Câu 62

[TYPE: TRUE_FALSE]

Mệnh đề: "Singleton tạo ra global state, có thể gây khó khăn cho unit testing."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Singleton là global state → khó mock/stub trong unit test. Best practice: inject Singleton qua constructor (DI) thay vì gọi trực tiếp `getInstance()`.

## Câu 63

[TYPE: SELECT_RESULT]

Cho đoạn code Iterator custom:

```java
class Range implements Iterable<Integer> {
    int start, end;
    Range(int s, int e) { this.start = s; this.end = e; }
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            int current = start;
            public boolean hasNext() { return current <= end; }
            public Integer next() { return current++; }
        };
    }
}
StringBuilder sb = new StringBuilder();
for (int i : new Range(1, 5)) sb.append(i).append(" ");
System.out.println(sb.toString().trim());
```

- [x] 1 2 3 4 5
- [ ] 1 2 3 4
- [ ] 2 3 4 5
- [ ] Lỗi biên dịch

> **Giải thích:** Custom Iterable: hasNext() true khi current <= 5, next() trả về current rồi tăng. Enhanced for-loop dùng Iterator.

## Câu 64

[TYPE: MULTIPLE_CHOICE]

Khi nào nên dùng Builder thay vì constructor?

- [x] Khi object có nhiều tham số optional
- [ ] Khi object chỉ có 1 field
- [ ] Khi cần immutable object
- [ ] Builder luôn tốt hơn constructor

> **Giải thích:** Builder hữu ích khi: nhiều optional params, cần validation khi build, tránh telescoping constructor (constructor nhiều overloads).

## Câu 65

[TYPE: SELECT_RESULT]

Cho đoạn code Mediator:

```java
class ChatRoom {
    Map<String, Consumer<String>> users = new HashMap<>();
    void join(String name, Consumer<String> handler) { users.put(name, handler); }
    void send(String from, String to, String msg) {
        Consumer<String> receiver = users.get(to);
        if (receiver != null) receiver.accept(from + ": " + msg);
    }
}
ChatRoom room = new ChatRoom();
StringBuilder log = new StringBuilder();
room.join("An", msg -> log.append("[An got] " + msg));
room.join("Binh", msg -> log.append("[Binh got] " + msg));
room.send("An", "Binh", "Hello");
System.out.println(log);
```

- [x] [Binh got] An: Hello
- [ ] [An got] An: Hello
- [ ] [An got] Hello [Binh got] Hello
- [ ] Lỗi biên dịch

> **Giải thích:** Mediator (ChatRoom) điều phối message. An gửi cho Binh → Binh's handler nhận "An: Hello".

## Câu 66

[TYPE: TRUE_FALSE]

Mệnh đề: "Composite Pattern cho phép xử lý leaf và composite objects bằng cùng interface."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Composite: File (leaf) và Folder (composite) cùng implement Component interface. Client gọi `getSize()` mà không cần biết đó là File hay Folder.

## Câu 67

[TYPE: SELECT_RESULT]

Cho đoạn code Dependency Injection:

```java
interface MessageService { String send(String msg); }
class EmailService implements MessageService {
    public String send(String msg) { return "Email: " + msg; }
}
class SMSService implements MessageService {
    public String send(String msg) { return "SMS: " + msg; }
}
class NotificationManager {
    private final MessageService service;
    NotificationManager(MessageService s) { this.service = s; }
    String notify(String msg) { return service.send(msg); }
}
System.out.println(new NotificationManager(new SMSService()).notify("Hi"));
```

- [ ] Email: Hi
- [x] SMS: Hi
- [ ] Hi
- [ ] Lỗi biên dịch

> **Giải thích:** DI: inject SMSService qua constructor. NotificationManager phụ thuộc vào interface, không biết cụ thể class nào → dễ swap, test.

## Câu 68

[TYPE: FILL_BLANK]

Pattern nào biến incompatible interface thành compatible interface mà client cần? Đó là `___` Pattern.

- [x] Adapter
- [ ] Decorator
- [ ] Proxy
- [ ] Facade

> **Giải thích:** Adapter: convert interface A sang interface B. Ví dụ: XMLParser → Adapter → JSONParser interface.

## Câu 69

[TYPE: SELECT_RESULT]

Cho đoạn code DAO Pattern:

```java
interface UserDAO {
    void save(String user);
    String find(int id);
}
class InMemoryUserDAO implements UserDAO {
    Map<Integer, String> db = new HashMap<>();
    int nextId = 1;
    public void save(String user) { db.put(nextId++, user); }
    public String find(int id) { return db.getOrDefault(id, "Not Found"); }
}
UserDAO dao = new InMemoryUserDAO();
dao.save("An");
dao.save("Binh");
System.out.println(dao.find(2));
System.out.println(dao.find(5));
```

- [x] Binh và Not Found
- [ ] An và Not Found
- [ ] An và Binh
- [ ] null và null

> **Giải thích:** DAO: data access abstraction. save("An")→id=1, save("Binh")→id=2. find(2)→"Binh". find(5)→key không có → "Not Found".

## Câu 70

[TYPE: MULTIPLE_CHOICE]

Đâu KHÔNG phải là Creational Pattern?

- [ ] Singleton
- [ ] Factory Method
- [x] Decorator
- [ ] Prototype

> **Giải thích:** Decorator là Structural Pattern (thêm behavior cho object). Creational: Singleton, Factory, Abstract Factory, Builder, Prototype.

## Câu 71

[TYPE: SELECT_RESULT]

Cho đoạn code Command with Undo:

```java
interface Command { void execute(); void undo(); }
class Calculator {
    int value = 0;
    void add(int n) { value += n; }
    void subtract(int n) { value -= n; }
}
class AddCommand implements Command {
    Calculator calc; int amount;
    AddCommand(Calculator c, int a) { this.calc = c; this.amount = a; }
    public void execute() { calc.add(amount); }
    public void undo() { calc.subtract(amount); }
}
Calculator calc = new Calculator();
Command cmd = new AddCommand(calc, 10);
cmd.execute();
cmd.execute();
System.out.print(calc.value + " ");
cmd.undo();
System.out.print(calc.value);
```

- [x] 20 10
- [ ] 10 0
- [ ] 20 20
- [ ] 10 10

> **Giải thích:** execute() 2 lần: 0+10+10 = 20. undo() 1 lần: 20-10 = 10.

## Câu 72

[TYPE: TRUE_FALSE]

Mệnh đề: "Visitor Pattern cho phép thêm operation mới mà không sửa class hiện có."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Visitor: thêm new operation bằng cách tạo new Visitor class. Các element class có `accept(Visitor)` → double dispatch. Tuân thủ OCP.

## Câu 73

[TYPE: SELECT_RESULT]

Cho đoạn code Observer với removal:

```java
class EventBus {
    Map<String, List<Runnable>> handlers = new HashMap<>();
    void on(String event, Runnable handler) {
        handlers.computeIfAbsent(event, k -> new ArrayList<>()).add(handler);
    }
    void off(String event) { handlers.remove(event); }
    void emit(String event) {
        handlers.getOrDefault(event, List.of()).forEach(Runnable::run);
    }
}
EventBus bus = new EventBus();
StringBuilder log = new StringBuilder();
bus.on("click", () -> log.append("A "));
bus.on("click", () -> log.append("B "));
bus.emit("click");
bus.off("click");
bus.emit("click");
System.out.println(log.toString().trim());
```

- [x] A B
- [ ] A B A B
- [ ] A
- [ ] (empty)

> **Giải thích:** emit("click") → "A B". off("click") → remove tất cả handlers. emit("click") lần 2 → không có handler → không in gì.

## Câu 74

[TYPE: MULTIPLE_CHOICE]

Template Method khác Strategy ở điểm nào?

- [x] Template Method dùng inheritance (abstract class), Strategy dùng composition (interface)
- [ ] Không có sự khác biệt
- [ ] Template Method nhanh hơn
- [ ] Strategy chỉ dùng cho sort

> **Giải thích:** Template Method: skeleton ở base class, hook methods override. Strategy: thuật toán đóng gói riêng, inject qua composition. Strategy linh hoạt hơn.

## Câu 75

[TYPE: SELECT_RESULT]

Cho đoạn code Registry Pattern:

```java
class ServiceRegistry {
    private static final Map<String, Object> services = new HashMap<>();
    static void register(String name, Object service) { services.put(name, service); }
    @SuppressWarnings("unchecked")
    static <T> T get(String name) { return (T) services.get(name); }
}
ServiceRegistry.register("greeting", "Hello World");
String msg = ServiceRegistry.get("greeting");
System.out.println(msg);
System.out.println(ServiceRegistry.get("unknown") == null);
```

- [x] Hello World và true
- [ ] Hello World và false
- [ ] null và true
- [ ] Lỗi biên dịch

> **Giải thích:** Registry: global lookup table. "greeting" registered → get → "Hello World". "unknown" not registered → get → null → == null → true.

## Câu 76

[TYPE: FILL_BLANK]

Pattern nào cung cấp substitute hoặc placeholder cho object khác để kiểm soát truy cập? Đó là `___` Pattern.

- [ ] Decorator
- [x] Proxy
- [ ] Adapter
- [ ] Bridge

> **Giải thích:** Proxy: kiểm soát truy cập đến object thật. Các loại: Virtual (lazy init), Protection (access control), Remote, Caching.

## Câu 77

[TYPE: SELECT_RESULT]

Cho đoạn code Specification Pattern:

```java
interface Spec<T> {
    boolean isSatisfiedBy(T t);
    default Spec<T> and(Spec<T> other) { return t -> this.isSatisfiedBy(t) && other.isSatisfiedBy(t); }
    default Spec<T> or(Spec<T> other) { return t -> this.isSatisfiedBy(t) || other.isSatisfiedBy(t); }
}
record Product(String name, double price, String category) {}
Spec<Product> expensive = p -> p.price() > 100;
Spec<Product> electronics = p -> p.category().equals("Electronics");
Spec<Product> filter = expensive.and(electronics);

Product p = new Product("Laptop", 1500, "Electronics");
System.out.println(filter.isSatisfiedBy(p));
Product p2 = new Product("Book", 20, "Education");
System.out.println(filter.isSatisfiedBy(p2));
```

- [x] true và false
- [ ] true và true
- [ ] false và false
- [ ] false và true

> **Giải thích:** Laptop: price>100 ✓ AND Electronics ✓ → true. Book: price>100 ✗ → false (short-circuit AND).

## Câu 78

[TYPE: MULTIPLE_CHOICE]

Đâu là ví dụ thực tế của Observer Pattern?

- [ ] JDBC Connection Pool
- [x] Event Listeners trong GUI, Pub/Sub messaging
- [ ] StringBuilder
- [ ] Thread Pool

> **Giải thích:** Observer: GUI event handlers (button click listeners), RxJava, Message Queues (Kafka consumers), Spring ApplicationEvent.

## Câu 79

[TYPE: SELECT_RESULT]

Cho đoạn code Composite + Visitor:

```java
interface Element { void accept(ElementVisitor v); }
class Text implements Element {
    String content;
    Text(String c) { this.content = c; }
    public void accept(ElementVisitor v) { v.visit(this); }
}
class Container implements Element {
    List<Element> children = new ArrayList<>();
    void add(Element e) { children.add(e); }
    public void accept(ElementVisitor v) {
        v.visit(this);
        children.forEach(c -> c.accept(v));
    }
}
interface ElementVisitor {
    void visit(Text t);
    void visit(Container c);
}
class CountVisitor implements ElementVisitor {
    int textCount = 0;
    public void visit(Text t) { textCount++; }
    public void visit(Container c) { /* just traverse */ }
}
Container root = new Container();
root.add(new Text("A"));
Container sub = new Container();
sub.add(new Text("B"));
sub.add(new Text("C"));
root.add(sub);
CountVisitor cv = new CountVisitor();
root.accept(cv);
System.out.println(cv.textCount);
```

- [x] 3
- [ ] 2
- [ ] 5
- [ ] 1

> **Giải thích:** root → Text("A"), Container(Text("B"), Text("C")). Visitor đếm Text nodes: A, B, C = 3.

## Câu 80

[TYPE: TRUE_FALSE]

Mệnh đề: "Chain of Responsibility cho phép nhiều handler xử lý request theo thứ tự cho đến khi một handler xử lý xong."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Chain of Responsibility: request được truyền qua chuỗi handlers. Mỗi handler quyết định xử lý hoặc chuyển tiếp. Ví dụ: Middleware chain, Approval workflow.

## Câu 81

[TYPE: SELECT_RESULT]

Cho đoạn code Object Pool:

```java
class ObjectPool<T> {
    Queue<T> pool = new LinkedList<>();
    Supplier<T> creator;
    ObjectPool(Supplier<T> c, int size) {
        this.creator = c;
        for (int i = 0; i < size; i++) pool.add(c.get());
    }
    T acquire() { return pool.isEmpty() ? creator.get() : pool.poll(); }
    void release(T obj) { pool.add(obj); }
}
ObjectPool<StringBuilder> pool = new ObjectPool<>(StringBuilder::new, 2);
System.out.println(pool.pool.size());
StringBuilder sb = pool.acquire();
System.out.println(pool.pool.size());
pool.release(sb);
System.out.println(pool.pool.size());
```

- [x] 2, 1, 2
- [ ] 2, 2, 2
- [ ] 0, 0, 1
- [ ] 2, 0, 1

> **Giải thích:** Pool khởi tạo 2 objects. Acquire 1 → pool còn 1. Release trả về → pool có 2.

## Câu 82

[TYPE: MULTIPLE_CHOICE]

Decorator và Proxy có cấu trúc tương tự. Điểm khác chính là gì?

- [x] Decorator thêm behavior, Proxy kiểm soát truy cập
- [ ] Proxy nhanh hơn Decorator
- [ ] Decorator dùng inheritance, Proxy dùng composition
- [ ] Không có sự khác biệt

> **Giải thích:** Cả hai wrap object và implement cùng interface. Decorator: add functionality (logging, compression). Proxy: control access (lazy loading, security, caching).

## Câu 83

[TYPE: SELECT_RESULT]

Cho đoạn code Pipeline Pattern:

```java
interface Stage<I, O> { O process(I input); }
class Pipeline<I, O> {
    Stage<I, O> stage;
    Pipeline(Stage<I, O> s) { this.stage = s; }
    <R> Pipeline<I, R> then(Stage<O, R> next) {
        return new Pipeline<>(input -> next.process(stage.process(input)));
    }
    O execute(I input) { return stage.process(input); }
}
String result = new Pipeline<String, String>(s -> s.trim())
    .then(s -> s.toUpperCase())
    .then(s -> s.replace(" ", "_"))
    .execute("  hello world  ");
System.out.println(result);
```

- [x] HELLO_WORLD
- [ ] hello world
- [ ] HELLO WORLD
- [ ] hello_world

> **Giải thích:** Pipeline: trim → "hello world" → toUpperCase → "HELLO WORLD" → replace spaces → "HELLO_WORLD".

## Câu 84

[TYPE: FILL_BLANK]

Pattern nào tạo family of related objects mà không chỉ rõ concrete class, sử dụng factory of factories? Đó là `___` Pattern.

- [ ] Factory Method
- [x] Abstract Factory
- [ ] Builder
- [ ] Prototype

> **Giải thích:** Abstract Factory: factory tạo related objects. Ví dụ: `GUIFactory` tạo Button + TextField. `WinFactory` → WinButton + WinTextField. `MacFactory` → MacButton + MacTextField.

## Câu 85

[TYPE: SELECT_RESULT]

Cho đoạn code Intercepting Filter:

```java
interface Filter { String doFilter(String request, FilterChain chain); }
class FilterChain {
    List<Filter> filters = new ArrayList<>();
    int index = 0;
    void addFilter(Filter f) { filters.add(f); }
    String proceed(String request) {
        if (index < filters.size()) return filters.get(index++).doFilter(request, this);
        return request;
    }
}
FilterChain chain = new FilterChain();
chain.addFilter((req, c) -> c.proceed("[Auth]" + req));
chain.addFilter((req, c) -> c.proceed("[Log]" + req));
System.out.println(chain.proceed("Request"));
```

- [x] [Log][Auth]Request
- [ ] [Auth][Log]Request
- [ ] [Auth]Request
- [ ] Request

> **Giải thích:** Filter 1 (Auth) wrap và gọi chain.proceed. Filter 2 (Log) wrap và gọi chain.proceed. Kết quả: ngoài cùng là Log, trong là Auth.

## Câu 86

[TYPE: TRUE_FALSE]

Mệnh đề: "Builder Pattern có thể tạo immutable object."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Builder collect params, build() tạo immutable object (all fields final, no setters). Ví dụ: `String`, `java.time.LocalDate`.

## Câu 87

[TYPE: SELECT_RESULT]

Pattern nào đang được sử dụng?

```java
class Logger {
    private static Logger instance;
    private List<String> logs = new ArrayList<>();
    
    private Logger() {}
    
    static Logger getInstance() {
        if (instance == null) instance = new Logger();
        return instance;
    }
    
    void log(String msg) { logs.add(msg); }
    List<String> getLogs() { return Collections.unmodifiableList(logs); }
}
```

- [x] Singleton
- [ ] Factory
- [ ] Builder
- [ ] Observer

> **Giải thích:** Private constructor + static getInstance() → Singleton. Chỉ 1 Logger instance tồn tại.

## Câu 88

[TYPE: MULTIPLE_CHOICE]

Khi nào nên dùng Facade Pattern?

- [x] Khi hệ thống con phức tạp và cần cung cấp interface đơn giản
- [ ] Khi cần thêm behavior cho object
- [ ] Khi cần clone object
- [ ] Khi cần thread-safe singleton

> **Giải thích:** Facade khi: nhiều subsystems phức tạp, client chỉ cần interface đơn giản. Ví dụ: `OrderFacade` gom checkStock + processPayment + shipOrder.

## Câu 89

[TYPE: SELECT_RESULT]

Cho đoạn code Event Sourcing Pattern:

```java
interface Event {}
record Deposited(double amount) implements Event {}
record Withdrawn(double amount) implements Event {}

class Account {
    List<Event> events = new ArrayList<>();
    void apply(Event e) { events.add(e); }
    double balance() {
        double bal = 0;
        for (Event e : events) {
            if (e instanceof Deposited d) bal += d.amount();
            if (e instanceof Withdrawn w) bal -= w.amount();
        }
        return bal;
    }
}
Account acc = new Account();
acc.apply(new Deposited(1000));
acc.apply(new Withdrawn(300));
acc.apply(new Deposited(500));
System.out.println(acc.balance());
```

- [x] 1200.0
- [ ] 500.0
- [ ] 1000.0
- [ ] 300.0

> **Giải thích:** Event Sourcing: state từ event history. 1000 - 300 + 500 = 1200. Không lưu state, replay events để tính.

## Câu 90

[TYPE: FILL_BLANK]

Pattern nào cho phép objects giao tiếp qua một object trung gian thay vì trực tiếp, giảm N×N dependencies thành N? Đó là `___` Pattern.

- [ ] Observer
- [x] Mediator
- [ ] Proxy
- [ ] Facade

> **Giải thích:** Mediator: tập trung communication. N objects giao tiếp qua 1 mediator thay vì trực tiếp. Ví dụ: Chat Room, Air Traffic Controller.

## Câu 91

[TYPE: SELECT_RESULT]

Cho đoạn code Method Factory Pattern:

```java
abstract class Dialog {
    abstract Button createButton();
    void render() { System.out.println(createButton().render()); }
}
interface Button { String render(); }
class WindowsDialog extends Dialog {
    Button createButton() { return () -> "Windows Button"; }
}
class WebDialog extends Dialog {
    Button createButton() { return () -> "Web Button"; }
}
new WindowsDialog().render();
new WebDialog().render();
```

- [x] Windows Button và Web Button
- [ ] Button và Button
- [ ] Lỗi biên dịch
- [ ] null và null

> **Giải thích:** Factory Method: mỗi Dialog subclass tạo Button khác nhau. render() gọi createButton() → polymorphism quyết định Button nào.

## Câu 92

[TYPE: MULTIPLE_CHOICE]

Đâu là ví dụ thực tế của Decorator Pattern trong Java standard library?

- [x] java.io.BufferedInputStream wraps InputStream
- [ ] java.util.ArrayList
- [ ] java.lang.String
- [ ] java.util.HashMap

> **Giải thích:** Java IO dùng Decorator: `BufferedInputStream(new FileInputStream("file"))`. BufferedInputStream thêm buffering behavior cho InputStream bất kỳ.

## Câu 93

[TYPE: SELECT_RESULT]

Cho đoạn code Prototype (deep copy):

```java
class Address {
    String city;
    Address(String c) { this.city = c; }
    Address copy() { return new Address(this.city); }
}
class Person {
    String name;
    Address address;
    Person(String n, Address a) { this.name = n; this.address = a; }
    Person deepCopy() { return new Person(this.name, this.address.copy()); }
}
Person p1 = new Person("An", new Address("HCM"));
Person p2 = p1.deepCopy();
p2.address.city = "HN";
System.out.println(p1.address.city + " " + p2.address.city);
```

- [x] HCM HN
- [ ] HN HN
- [ ] HCM HCM
- [ ] NullPointerException

> **Giải thích:** Deep copy: address cũng được copy. Thay đổi p2.address.city không ảnh hưởng p1. Nếu shallow copy → cả hai thay đổi.

## Câu 94

[TYPE: TRUE_FALSE]

Mệnh đề: "Strategy Pattern tuân thủ Open/Closed Principle vì thêm strategy mới không cần sửa code client."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Thêm strategy mới (new class implements Strategy interface) → không sửa client code. Client dùng Strategy interface → OCP compliant.

## Câu 95

[TYPE: SELECT_RESULT]

Cho đoạn code Lazy Initialization:

```java
class ExpensiveResource {
    static int count = 0;
    ExpensiveResource() { count++; System.out.print("Created "); }
}
class ResourceHolder {
    private ExpensiveResource resource;
    ExpensiveResource get() {
        if (resource == null) resource = new ExpensiveResource();
        return resource;
    }
}
ResourceHolder holder = new ResourceHolder();
System.out.print("Start ");
holder.get();
holder.get();
System.out.print("Count:" + ExpensiveResource.count);
```

- [x] Start Created Count:1
- [ ] Created Start Created Count:2
- [ ] Start Created Created Count:2
- [ ] Start Count:0

> **Giải thích:** Lazy init: resource tạo khi gọi get() lần đầu. Lần 2 dùng instance cũ. Count = 1.

## Câu 96

[TYPE: MULTIPLE_CHOICE]

Đâu là Behavioral Pattern?

- [ ] Builder, Prototype
- [ ] Adapter, Proxy
- [x] Command, Iterator, Memento, Observer, State, Strategy, Template Method, Visitor
- [ ] Composite, Flyweight

> **Giải thích:** Behavioral patterns: Chain of Responsibility, Command, Interpreter, Iterator, Mediator, Memento, Observer, State, Strategy, Template Method, Visitor.

## Câu 97

[TYPE: SELECT_RESULT]

Pattern nào đang được sử dụng?

```java
interface Converter<S, T> {
    T convert(S source);
}
class StringToIntConverter implements Converter<String, Integer> {
    public Integer convert(String source) { return Integer.parseInt(source); }
}
Converter<String, Integer> c = new StringToIntConverter();
System.out.println(c.convert("42"));
```

- [ ] Factory
- [ ] Decorator
- [x] Strategy
- [ ] Adapter

> **Giải thích:** Converter interface + different implementations = Strategy pattern. Có thể swap conversion strategy tại runtime.

## Câu 98

[TYPE: TRUE_FALSE]

Mệnh đề: "Adapter Pattern có 2 dạng: Class Adapter (dùng inheritance) và Object Adapter (dùng composition)."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Class Adapter: extends Adaptee, implements Target. Object Adapter: implements Target, wraps Adaptee instance. Object Adapter linh hoạt hơn.

## Câu 99

[TYPE: SELECT_RESULT]

Cho đoạn code Interpreter Pattern:

```java
interface Expression { int interpret(); }
class Number implements Expression {
    int value;
    Number(int v) { this.value = v; }
    public int interpret() { return value; }
}
class Add implements Expression {
    Expression left, right;
    Add(Expression l, Expression r) { this.left = l; this.right = r; }
    public int interpret() { return left.interpret() + right.interpret(); }
}
class Multiply implements Expression {
    Expression left, right;
    Multiply(Expression l, Expression r) { this.left = l; this.right = r; }
    public int interpret() { return left.interpret() * right.interpret(); }
}
// (2 + 3) * 4
Expression expr = new Multiply(new Add(new Number(2), new Number(3)), new Number(4));
System.out.println(expr.interpret());
```

- [x] 20
- [ ] 14
- [ ] 24
- [ ] Lỗi biên dịch

> **Giải thích:** Interpreter pattern: biểu diễn grammar. (2+3)*4 = 5*4 = 20. Add.interpret() = 2+3 = 5. Multiply.interpret() = 5*4 = 20.

## Câu 100

[TYPE: MULTIPLE_CHOICE]

Nguyên tắc nào KHÔNG phải là SOLID?

- [ ] Single Responsibility
- [ ] Open/Closed
- [x] Don't Repeat Yourself (DRY)
- [ ] Interface Segregation

> **Giải thích:** SOLID: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion. DRY là nguyên tắc quan trọng nhưng không thuộc SOLID.
