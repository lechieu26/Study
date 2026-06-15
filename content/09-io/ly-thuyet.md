# Java IO (Input/Output)

## Mục lục

1. [Giới thiệu về Java IO](#1-giới-thiệu-về-java-io)
2. [Byte Streams](#2-byte-streams)
3. [Character Streams](#3-character-streams)
4. [File Class](#4-file-class)
5. [Java NIO (New IO)](#5-java-nio-new-io)
6. [Serialization và Deserialization](#6-serialization-và-deserialization)
7. [Path và Files (Java 7+)](#7-path-và-files-java-7)
8. [Best Practices](#8-best-practices)

---

## 1. Giới thiệu về Java IO

### 1.1. Java IO là gì?

**Java IO** (Input/Output) là hệ thống API cho phép đọc và ghi dữ liệu từ/đến các nguồn khác nhau: file, network, console, memory. Package chính: `java.io`.

```java
import java.io.*;

public class IOIntroDemo {
    public static void main(String[] args) throws IOException {
        // Ghi dữ liệu ra file
        try (FileWriter writer = new FileWriter("hello.txt")) {
            writer.write("Hello, Java IO!");
        }
        
        // Đọc dữ liệu từ file
        try (FileReader reader = new FileReader("hello.txt")) {
            int ch;
            while ((ch = reader.read()) != -1) {
                System.out.print((char) ch);
            }
        }
        // Output: Hello, Java IO!
    }
}
```

### 1.2. Kiến trúc Java IO

```
                    Java IO
                   /       \
          Byte Streams    Character Streams
          (binary data)   (text data)
          /        \        /         \
    InputStream  OutputStream  Reader   Writer
        |            |           |         |
  FileInputStream  FileOutputStream  FileReader  FileWriter
  BufferedInputStream  BufferedOutputStream  BufferedReader  BufferedWriter
  DataInputStream  DataOutputStream  InputStreamReader  OutputStreamWriter
  ObjectInputStream  ObjectOutputStream  PrintWriter  StringWriter
```

### 1.3. Các loại Stream trong Java IO

| Loại | Input | Output | Mô tả |
|------|-------|--------|--------|
| **Byte Stream** | InputStream | OutputStream | Đọc/ghi từng byte (ảnh, video, binary) |
| **Character Stream** | Reader | Writer | Đọc/ghi từng ký tự (text, file văn bản) |
| **Buffered Stream** | BufferedInputStream/Reader | BufferedOutputStream/Writer | Tăng hiệu suất bằng buffer |
| **Data Stream** | DataInputStream | DataOutputStream | Đọc/ghi kiểu dữ liệu nguyên thủy |
| **Object Stream** | ObjectInputStream | ObjectOutputStream | Đọc/ghi object (serialization) |

---

## 2. Byte Streams

### 2.1. InputStream và OutputStream

**InputStream** và **OutputStream** là các abstract class gốc cho byte streams.

```java
// Các phương thức chính của InputStream
// int read()              - đọc 1 byte, trả về -1 nếu EOF
// int read(byte[] b)      - đọc vào mảng byte
// int read(byte[] b, int off, int len) - đọc vào mảng từ offset
// long skip(long n)       - bỏ qua n bytes
// int available()         - số bytes có thể đọc mà không bị block
// void close()            - đóng stream

// Các phương thức chính của OutputStream
// void write(int b)       - ghi 1 byte
// void write(byte[] b)    - ghi mảng byte
// void write(byte[] b, int off, int len) - ghi từ offset
// void flush()            - đẩy buffer ra output
// void close()            - đóng stream
```

### 2.2. FileInputStream và FileOutputStream

```java
import java.io.*;

public class FileStreamDemo {
    public static void main(String[] args) {
        // Ghi file binary
        try (FileOutputStream fos = new FileOutputStream("data.bin")) {
            byte[] data = {72, 101, 108, 108, 111}; // "Hello" in ASCII
            fos.write(data);
            fos.write(33); // '!'
            System.out.println("File written successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Đọc file binary
        try (FileInputStream fis = new FileInputStream("data.bin")) {
            int byteData;
            System.out.print("Content: ");
            while ((byteData = fis.read()) != -1) {
                System.out.print((char) byteData);
            }
            System.out.println(); // Hello!
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Copy file binary
        try (FileInputStream fis = new FileInputStream("source.jpg");
             FileOutputStream fos = new FileOutputStream("copy.jpg")) {
            
            byte[] buffer = new byte[8192]; // 8KB buffer
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            System.out.println("File copied successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### 2.3. BufferedInputStream và BufferedOutputStream

**Buffered streams** đọc/ghi theo khối (block) thay vì từng byte, tăng hiệu suất đáng kể.

```java
import java.io.*;

public class BufferedStreamDemo {
    public static void main(String[] args) throws IOException {
        // Ghi với buffer
        try (BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream("buffered.txt"), 8192)) { // 8KB buffer
            
            String data = "This is buffered output.\nLine 2.\nLine 3.";
            bos.write(data.getBytes());
            bos.flush(); // Đảm bảo dữ liệu được ghi ra file
        }
        
        // Đọc với buffer
        try (BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream("buffered.txt"))) {
            
            byte[] buffer = new byte[1024];
            int bytesRead;
            StringBuilder sb = new StringBuilder();
            while ((bytesRead = bis.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, bytesRead));
            }
            System.out.println(sb.toString());
        }
        
        // Performance comparison
        long start, end;
        
        // Không buffer
        start = System.currentTimeMillis();
        try (FileOutputStream fos = new FileOutputStream("nobuffer.tmp")) {
            for (int i = 0; i < 100000; i++) {
                fos.write(i);
            }
        }
        end = System.currentTimeMillis();
        System.out.println("Without buffer: " + (end - start) + "ms");
        
        // Có buffer
        start = System.currentTimeMillis();
        try (BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream("withbuffer.tmp"))) {
            for (int i = 0; i < 100000; i++) {
                bos.write(i);
            }
        }
        end = System.currentTimeMillis();
        System.out.println("With buffer: " + (end - start) + "ms");
    }
}
```

### 2.4. DataInputStream và DataOutputStream

Đọc/ghi **kiểu dữ liệu nguyên thủy** (int, double, boolean, UTF string).

```java
import java.io.*;

public class DataStreamDemo {
    public static void main(String[] args) throws IOException {
        // Ghi dữ liệu có kiểu
        try (DataOutputStream dos = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream("data.dat")))) {
            
            dos.writeInt(42);
            dos.writeDouble(3.14159);
            dos.writeBoolean(true);
            dos.writeUTF("Hello, DataStream!");
            dos.writeLong(System.currentTimeMillis());
        }
        
        // Đọc dữ liệu (cùng thứ tự ghi)
        try (DataInputStream dis = new DataInputStream(
                new BufferedInputStream(new FileInputStream("data.dat")))) {
            
            int intVal = dis.readInt();
            double doubleVal = dis.readDouble();
            boolean boolVal = dis.readBoolean();
            String strVal = dis.readUTF();
            long longVal = dis.readLong();
            
            System.out.println("Int: " + intVal);        // 42
            System.out.println("Double: " + doubleVal);  // 3.14159
            System.out.println("Boolean: " + boolVal);   // true
            System.out.println("String: " + strVal);     // Hello, DataStream!
            System.out.println("Long: " + longVal);
        }
    }
}
```

---

## 3. Character Streams

### 3.1. Reader và Writer

**Reader** và **Writer** là abstract class gốc cho character streams, xử lý text data với encoding.

```java
// Reader methods:
// int read()              - đọc 1 char
// int read(char[] cbuf)   - đọc vào char array
// long skip(long n)       - bỏ qua n chars
// boolean ready()         - có thể đọc không bị block?
// void close()

// Writer methods:
// void write(int c)           - ghi 1 char
// void write(char[] cbuf)     - ghi char array
// void write(String str)      - ghi String
// void flush()                - flush buffer
// void close()
```

### 3.2. FileReader và FileWriter

```java
import java.io.*;

public class FileReaderWriterDemo {
    public static void main(String[] args) throws IOException {
        // FileWriter - ghi text file
        try (FileWriter writer = new FileWriter("story.txt")) {
            writer.write("Chào mừng đến với Java IO!\n");
            writer.write("Đây là dòng thứ hai.\n");
            writer.write("Java hỗ trợ Unicode đầy đủ.");
        }
        
        // FileWriter - append mode
        try (FileWriter writer = new FileWriter("story.txt", true)) { // append=true
            writer.write("\nDòng được append thêm.");
        }
        
        // FileReader - đọc text file
        try (FileReader reader = new FileReader("story.txt")) {
            int ch;
            while ((ch = reader.read()) != -1) {
                System.out.print((char) ch);
            }
        }
        
        // Đọc bằng char array
        try (FileReader reader = new FileReader("story.txt")) {
            char[] buffer = new char[100];
            int charsRead;
            while ((charsRead = reader.read(buffer)) != -1) {
                System.out.print(new String(buffer, 0, charsRead));
            }
        }
    }
}
```

### 3.3. BufferedReader và BufferedWriter

**BufferedReader** hỗ trợ đọc **từng dòng** (`readLine()`), rất tiện cho text processing.

```java
import java.io.*;

public class BufferedReaderWriterDemo {
    public static void main(String[] args) throws IOException {
        // BufferedWriter
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("poem.txt"))) {
            writer.write("Dòng 1: Java là ngôn ngữ lập trình");
            writer.newLine(); // platform-independent newline
            writer.write("Dòng 2: Hướng đối tượng, đa nền tảng");
            writer.newLine();
            writer.write("Dòng 3: Write Once, Run Anywhere");
        }
        
        // BufferedReader - đọc từng dòng
        try (BufferedReader reader = new BufferedReader(new FileReader("poem.txt"))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                System.out.println(lineNumber++ + ": " + line);
            }
        }
        // 1: Dòng 1: Java là ngôn ngữ lập trình
        // 2: Dòng 2: Hướng đối tượng, đa nền tảng
        // 3: Dòng 3: Write Once, Run Anywhere
        
        // Java 8+: lines() trả về Stream<String>
        try (BufferedReader reader = new BufferedReader(new FileReader("poem.txt"))) {
            reader.lines()
                .filter(l -> l.contains("Java"))
                .forEach(System.out::println);
        }
        
        // Đọc từ Console
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Nhập tên: ");
        String name = consoleReader.readLine();
        System.out.println("Xin chào, " + name);
    }
}
```

### 3.4. InputStreamReader và OutputStreamWriter

**Bridge classes** chuyển đổi giữa byte stream và character stream, cho phép chỉ định **encoding**.

```java
import java.io.*;
import java.nio.charset.StandardCharsets;

public class BridgeStreamDemo {
    public static void main(String[] args) throws IOException {
        // Ghi file với encoding cụ thể
        try (OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream("utf8.txt"), StandardCharsets.UTF_8)) {
            writer.write("Xin chào! 你好! こんにちは!");
        }
        
        // Đọc file với encoding cụ thể
        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream("utf8.txt"), StandardCharsets.UTF_8)) {
            char[] buffer = new char[256];
            int charsRead = reader.read(buffer);
            System.out.println(new String(buffer, 0, charsRead));
        }
        
        // Kết hợp với Buffered
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("utf8.txt"), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        
        // Đọc từ URL/Network stream
        // InputStream is = new URL("https://example.com").openStream();
        // BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
    }
}
```

---

## 4. File Class

### 4.1. Tạo file

```java
import java.io.File;
import java.io.IOException;

public class FileCreateDemo {
    public static void main(String[] args) throws IOException {
        // Tạo file mới
        File file = new File("newfile.txt");
        boolean created = file.createNewFile();
        System.out.println("File created: " + created); // true nếu file chưa tồn tại
        
        // Tạo thư mục
        File dir = new File("mydir");
        boolean dirCreated = dir.mkdir();
        System.out.println("Dir created: " + dirCreated);
        
        // Tạo nhiều thư mục lồng nhau
        File nestedDir = new File("parent/child/grandchild");
        boolean nestedCreated = nestedDir.mkdirs(); // tạo cả parent nếu chưa có
        System.out.println("Nested dirs created: " + nestedCreated);
        
        // Tạo file tạm
        File tempFile = File.createTempFile("prefix_", "_suffix.tmp");
        System.out.println("Temp file: " + tempFile.getAbsolutePath());
        tempFile.deleteOnExit(); // tự xóa khi JVM exit
    }
}
```

### 4.2. Đọc thông tin file

```java
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileInfoDemo {
    public static void main(String[] args) {
        File file = new File("example.txt");
        
        // Thông tin cơ bản
        System.out.println("Name: " + file.getName());
        System.out.println("Path: " + file.getPath());
        System.out.println("Absolute path: " + file.getAbsolutePath());
        System.out.println("Parent: " + file.getParent());
        
        // Kích thước
        System.out.println("Size: " + file.length() + " bytes");
        
        // Thời gian
        long lastModified = file.lastModified();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Last modified: " + sdf.format(new Date(lastModified)));
        
        // Quyền truy cập
        System.out.println("Readable: " + file.canRead());
        System.out.println("Writable: " + file.canWrite());
        System.out.println("Executable: " + file.canExecute());
        System.out.println("Hidden: " + file.isHidden());
        
        // Liệt kê file trong thư mục
        File dir = new File(".");
        String[] fileNames = dir.list();
        File[] files = dir.listFiles();
        
        // Filter files
        File[] txtFiles = dir.listFiles((d, name) -> name.endsWith(".txt"));
        if (txtFiles != null) {
            for (File f : txtFiles) {
                System.out.println("TXT: " + f.getName());
            }
        }
    }
}
```

### 4.3. Xóa file

```java
import java.io.File;

public class FileDeleteDemo {
    public static void main(String[] args) {
        // Xóa file
        File file = new File("temp.txt");
        if (file.exists()) {
            boolean deleted = file.delete();
            System.out.println("Deleted: " + deleted);
        }
        
        // Xóa thư mục (phải rỗng)
        File dir = new File("emptyDir");
        dir.delete(); // chỉ xóa được nếu thư mục rỗng
        
        // Xóa thư mục đệ quy
        deleteRecursive(new File("dirToDelete"));
    }
    
    public static void deleteRecursive(File file) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteRecursive(child);
                }
            }
        }
        file.delete();
    }
}
```

### 4.4. Kiểm tra file

```java
import java.io.File;

public class FileCheckDemo {
    public static void main(String[] args) {
        File file = new File("test.txt");
        
        // Kiểm tra tồn tại
        System.out.println("Exists: " + file.exists());
        
        // Kiểm tra loại
        System.out.println("Is file: " + file.isFile());
        System.out.println("Is directory: " + file.isDirectory());
        
        // Kiểm tra dung lượng ổ đĩa
        File root = new File("/");
        System.out.println("Total space: " + root.getTotalSpace() / (1024*1024*1024) + " GB");
        System.out.println("Free space: " + root.getFreeSpace() / (1024*1024*1024) + " GB");
        System.out.println("Usable space: " + root.getUsableSpace() / (1024*1024*1024) + " GB");
        
        // Đổi tên/di chuyển file
        File oldFile = new File("old.txt");
        File newFile = new File("new.txt");
        boolean renamed = oldFile.renameTo(newFile);
    }
}
```

---

## 5. Java NIO (New IO)

### 5.1. Buffer

**Buffer** là container chứa dữ liệu trong NIO. Hoạt động với capacity, position, limit.

```java
import java.nio.*;

public class BufferDemo {
    public static void main(String[] args) {
        // Tạo ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(10); // capacity = 10
        
        // Ghi dữ liệu vào buffer
        buffer.put((byte) 'H');
        buffer.put((byte) 'e');
        buffer.put((byte) 'l');
        buffer.put((byte) 'l');
        buffer.put((byte) 'o');
        
        System.out.println("After write - Position: " + buffer.position()); // 5
        System.out.println("Limit: " + buffer.limit());     // 10
        System.out.println("Capacity: " + buffer.capacity()); // 10
        
        // Chuyển sang chế độ đọc
        buffer.flip(); // position = 0, limit = 5
        System.out.println("After flip - Position: " + buffer.position()); // 0
        System.out.println("Limit: " + buffer.limit());     // 5
        
        // Đọc dữ liệu từ buffer
        while (buffer.hasRemaining()) {
            System.out.print((char) buffer.get());
        }
        System.out.println(); // Hello
        
        // Clear buffer để tái sử dụng
        buffer.clear(); // position = 0, limit = capacity
        
        // Wrap existing array
        byte[] arr = "World".getBytes();
        ByteBuffer wrapped = ByteBuffer.wrap(arr);
        
        // IntBuffer, CharBuffer, etc.
        IntBuffer intBuf = IntBuffer.allocate(5);
        intBuf.put(new int[]{1, 2, 3, 4, 5});
        intBuf.flip();
        while (intBuf.hasRemaining()) {
            System.out.print(intBuf.get() + " "); // 1 2 3 4 5
        }
    }
}
```

### 5.2. Channel

**Channel** là kênh đọc/ghi dữ liệu, hoạt động với Buffer. Có thể non-blocking.

```java
import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class ChannelDemo {
    public static void main(String[] args) throws IOException {
        // FileChannel - ghi file
        try (FileOutputStream fos = new FileOutputStream("channel.txt");
             FileChannel channel = fos.getChannel()) {
            
            String data = "Hello from NIO Channel!";
            ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
            channel.write(buffer);
        }
        
        // FileChannel - đọc file
        try (FileInputStream fis = new FileInputStream("channel.txt");
             FileChannel channel = fis.getChannel()) {
            
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int bytesRead = channel.read(buffer);
            
            buffer.flip();
            byte[] data = new byte[bytesRead];
            buffer.get(data);
            System.out.println(new String(data)); // Hello from NIO Channel!
        }
        
        // Copy file sử dụng Channel (hiệu quả nhất)
        try (FileChannel src = new FileInputStream("source.txt").getChannel();
             FileChannel dest = new FileOutputStream("dest.txt").getChannel()) {
            
            dest.transferFrom(src, 0, src.size());
            // Hoặc: src.transferTo(0, src.size(), dest);
        }
    }
}
```

### 5.3. Selector

**Selector** cho phép một thread quản lý nhiều Channel (multiplexing) - hữu ích cho network I/O.

```java
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.*;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class SelectorDemo {
    public static void main(String[] args) throws IOException {
        // Tạo Selector
        Selector selector = Selector.open();
        
        // Tạo ServerSocketChannel
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(8080));
        serverChannel.configureBlocking(false); // Non-blocking mode
        
        // Đăng ký channel với selector
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        
        System.out.println("Server started on port 8080");
        
        while (true) {
            // Chờ events (blocking)
            selector.select();
            
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                
                if (key.isAcceptable()) {
                    // Có client mới kết nối
                    SocketChannel client = serverChannel.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                    System.out.println("Client connected");
                    
                } else if (key.isReadable()) {
                    // Có dữ liệu từ client
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(256);
                    int bytesRead = client.read(buffer);
                    
                    if (bytesRead == -1) {
                        client.close();
                    } else {
                        buffer.flip();
                        System.out.println("Received: " + new String(buffer.array(), 0, bytesRead));
                    }
                }
                
                iter.remove();
            }
        }
    }
}
```

### 5.4. So sánh IO vs NIO

| Đặc điểm | Java IO | Java NIO |
|-----------|---------|----------|
| **Model** | Stream-oriented | Buffer-oriented |
| **Blocking** | Blocking | Non-blocking có thể |
| **API** | InputStream/OutputStream | Channel + Buffer |
| **Multiplexing** | Không | Selector |
| **Direction** | Một chiều | Đọc/ghi trên cùng Channel |
| **Use case** | File I/O đơn giản | Network I/O, nhiều connections |
| **Complexity** | Đơn giản | Phức tạp hơn |
| **Performance** | Tốt cho file nhỏ | Tốt cho file lớn, nhiều kết nối |

---

## 6. Serialization và Deserialization

### 6.1. Serializable Interface

**Serialization** là quá trình chuyển đổi object thành byte stream để lưu trữ hoặc truyền tải.

```java
import java.io.*;

// Class phải implement Serializable
public class Person implements Serializable {
    // serialVersionUID đảm bảo tương thích giữa các phiên bản
    private static final long serialVersionUID = 1L;
    
    private String name;
    private int age;
    private String email;
    private transient String password; // KHÔNG được serialize
    
    public Person(String name, int age, String email, String password) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + 
               ", email='" + email + "', password='" + password + "'}";
    }
}
```

### 6.2. Viết object vào file

```java
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializationDemo {
    public static void main(String[] args) {
        // Serialize một object
        Person person = new Person("Alice", 25, "alice@email.com", "secret123");
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("person.ser"))) {
            oos.writeObject(person);
            System.out.println("Object serialized successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Serialize collection
        List<Person> people = new ArrayList<>();
        people.add(new Person("Bob", 30, "bob@email.com", "pass1"));
        people.add(new Person("Charlie", 28, "charlie@email.com", "pass2"));
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("people.ser"))) {
            oos.writeObject(people);
            System.out.println("List serialized successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### 6.3. Đọc object từ file

```java
import java.io.*;
import java.util.List;

public class DeserializationDemo {
    public static void main(String[] args) {
        // Deserialize một object
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("person.ser"))) {
            Person person = (Person) ois.readObject();
            System.out.println("Deserialized: " + person);
            // Person{name='Alice', age=25, email='alice@email.com', password='null'}
            // password = null vì là transient
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        // Deserialize collection
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("people.ser"))) {
            @SuppressWarnings("unchecked")
            List<Person> people = (List<Person>) ois.readObject();
            people.forEach(System.out::println);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
```

### 6.4. transient keyword

**`transient`** đánh dấu field KHÔNG được serialize (dữ liệu nhạy cảm, dữ liệu tạm).

```java
import java.io.*;

public class TransientDemo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String username;
    private transient String password;      // Không serialize - nhạy cảm
    private transient int cachedValue;      // Không serialize - dữ liệu tạm
    private static String COMPANY = "ABC"; // static cũng không serialize
    
    public TransientDemo(String username, String password) {
        this.username = username;
        this.password = password;
        this.cachedValue = computeExpensiveValue();
    }
    
    private int computeExpensiveValue() {
        return username.hashCode();
    }
    
    // Custom serialization (nếu cần kiểm soát)
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        // Có thể ghi thêm dữ liệu custom
        oos.writeObject(encrypt(password));
    }
    
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        // Đọc dữ liệu custom
        this.password = decrypt((String) ois.readObject());
        this.cachedValue = computeExpensiveValue(); // Tính lại cached value
    }
    
    private String encrypt(String s) { return s != null ? "ENC:" + s : null; }
    private String decrypt(String s) { return s != null ? s.substring(4) : null; }
}
```

---

## 7. Path và Files (Java 7+)

### 7.1. Path Interface

**Path** (Java 7+) thay thế `File` class, cung cấp API hiện đại và mạnh mẽ hơn.

```java
import java.nio.file.*;

public class PathDemo {
    public static void main(String[] args) {
        // Tạo Path
        Path path1 = Paths.get("/home/user/documents/report.txt");
        Path path2 = Paths.get("src", "main", "java", "App.java"); // relative
        Path path3 = Path.of("/home/user/file.txt"); // Java 11+
        
        // Thông tin Path
        System.out.println("Path: " + path1);
        System.out.println("File name: " + path1.getFileName());      // report.txt
        System.out.println("Parent: " + path1.getParent());            // /home/user/documents
        System.out.println("Root: " + path1.getRoot());                // /
        System.out.println("Name count: " + path1.getNameCount());     // 4
        System.out.println("Name(0): " + path1.getName(0));            // home
        
        // Resolve (nối path)
        Path base = Paths.get("/home/user");
        Path resolved = base.resolve("documents/file.txt");
        System.out.println("Resolved: " + resolved); // /home/user/documents/file.txt
        
        // Relativize (tính relative path)
        Path p1 = Paths.get("/home/user/docs");
        Path p2 = Paths.get("/home/user/images/photo.jpg");
        Path relative = p1.relativize(p2);
        System.out.println("Relative: " + relative); // ../images/photo.jpg
        
        // Normalize (loại bỏ . và ..)
        Path dirty = Paths.get("/home/user/../user/./docs/./file.txt");
        System.out.println("Normalized: " + dirty.normalize()); // /home/user/docs/file.txt
        
        // toAbsolutePath
        Path rel = Paths.get("file.txt");
        System.out.println("Absolute: " + rel.toAbsolutePath());
    }
}
```

### 7.2. Files Class

**Files** cung cấp static methods cho thao tác file/directory.

```java
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.io.IOException;

public class FilesDemo {
    public static void main(String[] args) throws IOException {
        Path file = Paths.get("demo.txt");
        
        // Ghi file
        Files.write(file, "Hello NIO!".getBytes());
        Files.write(file, List.of("Line 1", "Line 2", "Line 3")); // ghi nhiều dòng
        
        // Đọc file
        String content = Files.readString(file); // Java 11+
        byte[] bytes = Files.readAllBytes(file);
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        
        System.out.println("Content: " + content);
        System.out.println("Lines: " + lines);
        
        // Kiểm tra
        System.out.println("Exists: " + Files.exists(file));
        System.out.println("Is regular file: " + Files.isRegularFile(file));
        System.out.println("Is directory: " + Files.isDirectory(file));
        System.out.println("Is readable: " + Files.isReadable(file));
        System.out.println("Size: " + Files.size(file));
        
        // Tạo directory
        Path dir = Paths.get("newdir");
        Files.createDirectory(dir);
        Files.createDirectories(Paths.get("parent/child/grandchild"));
        
        // Copy
        Files.copy(file, Paths.get("copy.txt"), StandardCopyOption.REPLACE_EXISTING);
        
        // Move
        Files.move(Paths.get("copy.txt"), Paths.get("moved.txt"), 
                   StandardCopyOption.REPLACE_EXISTING);
        
        // Delete
        Files.delete(Paths.get("moved.txt"));       // throws if not exists
        Files.deleteIfExists(Paths.get("maybe.txt")); // no exception if not exists
        
        // Tạo temp file
        Path temp = Files.createTempFile("prefix", ".tmp");
        System.out.println("Temp: " + temp);
        
        // Stream API với Files (Java 8+)
        try (var stream = Files.lines(file)) {
            stream.filter(line -> line.contains("Line"))
                  .forEach(System.out::println);
        }
        
        // Liệt kê files trong directory
        try (var entries = Files.list(Paths.get("."))) {
            entries.filter(Files::isRegularFile)
                   .forEach(System.out::println);
        }
        
        // Walk directory tree
        try (var walk = Files.walk(Paths.get("."), 3)) { // maxDepth = 3
            walk.filter(p -> p.toString().endsWith(".java"))
                .forEach(System.out::println);
        }
    }
}
```

### 7.3. FileVisitor

**FileVisitor** cho phép duyệt cây thư mục với logic tùy chỉnh.

```java
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileVisitorDemo {
    public static void main(String[] args) throws IOException {
        // SimpleFileVisitor - xóa thư mục đệ quy
        Path dirToDelete = Paths.get("dirToDelete");
        
        Files.walkFileTree(dirToDelete, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) 
                    throws IOException {
                Files.delete(file);
                System.out.println("Deleted file: " + file);
                return FileVisitResult.CONTINUE;
            }
            
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) 
                    throws IOException {
                Files.delete(dir);
                System.out.println("Deleted dir: " + dir);
                return FileVisitResult.CONTINUE;
            }
        });
        
        // Tìm file theo pattern
        Path startDir = Paths.get(".");
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**.java");
        
        Files.walkFileTree(startDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (matcher.matches(file)) {
                    System.out.println("Found: " + file);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
```

---

## 8. Best Practices

### 1. Luôn dùng try-with-resources

```java
// ✅ Tài nguyên tự động close
try (BufferedReader reader = Files.newBufferedReader(path)) {
    // use reader
}

// ❌ Manual close - dễ quên, verbose
BufferedReader reader = null;
try {
    reader = Files.newBufferedReader(path);
    // use reader
} finally {
    if (reader != null) reader.close();
}
```

### 2. Sử dụng Buffered streams

```java
// ✅ Có buffer - nhanh
try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) { ... }
try (BufferedWriter writer = new BufferedWriter(new FileWriter("file.txt"))) { ... }

// ❌ Không buffer - chậm (mỗi read/write = 1 system call)
try (FileReader reader = new FileReader("file.txt")) { ... }
```

### 3. Chỉ định charset explicitly

```java
// ✅ Rõ ràng charset
Files.readString(path, StandardCharsets.UTF_8);
new InputStreamReader(is, StandardCharsets.UTF_8);

// ❌ Dùng default charset - có thể khác nhau giữa các hệ thống
new FileReader("file.txt"); // dùng platform default charset
```

### 4. Ưu tiên NIO.2 (java.nio.file) cho code mới

```java
// ✅ Java NIO.2 - modern, powerful
Path path = Path.of("file.txt");
String content = Files.readString(path);
List<String> lines = Files.readAllLines(path);
Files.write(path, lines);

// ❌ Cũ hơn, ít tính năng
File file = new File("file.txt");
```

### 5. Đọc file lớn bằng streaming

```java
// ✅ Streaming - xử lý từng dòng, không load toàn bộ vào RAM
try (Stream<String> lines = Files.lines(path)) {
    lines.filter(line -> line.contains("ERROR"))
         .forEach(System.out::println);
}

// ❌ Load toàn bộ file vào memory - tốn RAM với file lớn
List<String> allLines = Files.readAllLines(path); // OOM với file lớn!
```

### 6. Flush trước khi đóng (hoặc dùng try-with-resources)

```java
// ✅ try-with-resources tự động flush + close
try (BufferedWriter writer = Files.newBufferedWriter(path)) {
    writer.write("data");
}

// Nếu cần flush giữa chừng
try (BufferedWriter writer = Files.newBufferedWriter(path)) {
    writer.write("important data");
    writer.flush(); // đảm bảo data đã ghi xuống disk
    // tiếp tục xử lý...
}
```

### 7. Xử lý path an toàn

```java
// ✅ Sử dụng Path.resolve thay vì string concatenation
Path base = Paths.get("/home/user");
Path file = base.resolve("docs").resolve("file.txt");

// ❌ String concatenation - platform-dependent separator
String path = "/home/user" + "/" + "docs" + "/" + "file.txt";

// ✅ Normalize để tránh path traversal
Path userInput = Paths.get(userInputString).normalize();
if (!userInput.startsWith(allowedBase)) {
    throw new SecurityException("Path traversal detected!");
}
```

---

> **Tóm tắt:** Java IO cung cấp hệ thống API phong phú cho đọc/ghi dữ liệu. Với code mới, ưu tiên sử dụng NIO.2 (`java.nio.file`) và luôn dùng try-with-resources để đảm bảo tài nguyên được giải phóng đúng cách.
