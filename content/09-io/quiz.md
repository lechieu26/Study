# Quiz - Java I/O

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Java I/O có hai hệ thống chính: byte streams và character streams. Đâu là đặc điểm?

- [x] Byte streams (InputStream/OutputStream) xử lý byte; Character streams (Reader/Writer) xử lý char (Unicode)
- [ ] Byte streams nhanh hơn character streams
- [ ] Character streams xử lý byte
- [ ] Không có sự khác biệt

> **Giải thích:** Byte streams: InputStream, OutputStream (binary data). Character streams: Reader, Writer (text, auto encoding). Character = byte + charset encoding.

## Câu 2

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("test.txt");
Files.writeString(path, "Hello World");
String content = Files.readString(path);
System.out.println(content);
Files.delete(path);
```

- [x] Hello World
- [ ] null
- [ ] Lỗi biên dịch
- [ ] IOException

> **Giải thích:** Files.writeString (Java 11): ghi String ra file. Files.readString: đọc toàn bộ file thành String. Đơn giản, hiệu quả cho small files.

## Câu 3

[TYPE: FILL_BLANK]

Class `BufferedReader` dùng `___` để cải thiện performance đọc file.

- [x] buffer (bộ đệm)
- [ ] cache
- [ ] thread
- [ ] compression

> **Giải thích:** BufferedReader: đọc chunks vào buffer (default 8KB). Giảm I/O operations. readLine() tiện cho text. Wrap: new BufferedReader(new FileReader(file)).

## Câu 4

[TYPE: TRUE_FALSE]

Mệnh đề: "Java NIO (New I/O) sử dụng Channels và Buffers, hỗ trợ non-blocking I/O."

- [x] Đúng
- [ ] Sai

> **Giải thích:** NIO: Channel (read/write), Buffer (data container), Selector (multiplexing). Non-blocking mode cho network I/O. java.nio package.

## Câu 5

[TYPE: SELECT_RESULT]

```java
try (BufferedWriter writer = new BufferedWriter(new FileWriter("out.txt"))) {
    writer.write("Line 1");
    writer.newLine();
    writer.write("Line 2");
}
List<String> lines = Files.readAllLines(Path.of("out.txt"));
System.out.println(lines.size() + ": " + lines);
Files.delete(Path.of("out.txt"));
```

- [x] 2: [Line 1, Line 2]
- [ ] 1: [Line 1Line 2]
- [ ] 2: [Line 1, , Line 2]
- [ ] IOException

> **Giải thích:** writer.newLine(): platform-specific line separator. 2 lines written. readAllLines: List of lines. size=2.

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Sự khác biệt giữa FileInputStream và FileReader?

- [x] FileInputStream đọc bytes (binary); FileReader đọc chars (text với encoding)
- [ ] FileReader nhanh hơn
- [ ] Không có sự khác biệt
- [ ] FileInputStream đọc text

> **Giải thích:** FileInputStream: raw bytes, cho binary files (images, ZIP). FileReader: chars, default charset, cho text files. Dùng InputStreamReader cho custom charset.

## Câu 7

[TYPE: SELECT_RESULT]

```java
Path dir = Path.of("testdir");
Files.createDirectory(dir);
Files.writeString(dir.resolve("a.txt"), "A");
Files.writeString(dir.resolve("b.txt"), "B");
long count;
try (Stream<Path> stream = Files.list(dir)) {
    count = stream.count();
}
System.out.println(count);
// cleanup
Files.walk(dir).sorted(Comparator.reverseOrder()).forEach(p -> {
    try { Files.delete(p); } catch (IOException e) {}
});
```

- [x] 2
- [ ] 0
- [ ] 3
- [ ] 1

> **Giải thích:** Files.list(dir): list trực tiếp contents. 2 files (a.txt, b.txt). count()=2. Không đệ quy (chỉ level 1).

## Câu 8

[TYPE: SELECT_RESULT]

```java
ByteArrayOutputStream baos = new ByteArrayOutputStream();
ObjectOutputStream oos = new ObjectOutputStream(baos);
oos.writeObject("Hello");
oos.writeObject(42);
oos.close();

ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
ObjectInputStream ois = new ObjectInputStream(bais);
String s = (String) ois.readObject();
int n = (int) ois.readObject();
System.out.println(s + " " + n);
```

- [x] Hello 42
- [ ] null 0
- [ ] Lỗi biên dịch
- [ ] ClassNotFoundException

> **Giải thích:** ObjectOutputStream: serialize objects to bytes. ObjectInputStream: deserialize. String và Integer implement Serializable. Read in same order as write.

## Câu 9

[TYPE: FILL_BLANK]

`Path.of("a", "b", "c.txt")` tạo path `___`.

- [x] a/b/c.txt (hoặc a\b\c.txt trên Windows)
- [ ] abc.txt
- [ ] a-b-c.txt
- [ ] /a/b/c.txt

> **Giải thích:** Path.of(parts...): join với platform separator. Unix: a/b/c.txt. Windows: a\b\c.txt. Path immutable, platform-independent API.

## Câu 10

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("/home/user/docs/file.txt");
System.out.println(path.getFileName());
System.out.println(path.getParent());
System.out.println(path.getNameCount());
```

- [x] file.txt, /home/user/docs, 4
- [ ] file.txt, /home/user, 3
- [ ] /home/user/docs/file.txt, null, 5
- [ ] file, /home/user/docs, 4

> **Giải thích:** getFileName(): file.txt. getParent(): /home/user/docs. getNameCount(): home, user, docs, file.txt = 4 (không đếm root /).

## Câu 11

[TYPE: TRUE_FALSE]

Mệnh đề: "Files.copy() có thể copy file và cả directory (nhưng không đệ quy copy nội dung directory)."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Files.copy(source, target): copy file hoặc tạo empty directory. Không copy directory contents. Dùng Files.walk() để recursive copy.

## Câu 12

[TYPE: SELECT_RESULT]

```java
String text = "Hello\nWorld\nJava";
try (BufferedReader reader = new BufferedReader(new StringReader(text))) {
    List<String> lines = reader.lines().collect(Collectors.toList());
    System.out.println(lines);
}
```

- [x] [Hello, World, Java]
- [ ] Hello World Java
- [ ] [Hello\nWorld\nJava]
- [ ] Lỗi biên dịch

> **Giải thích:** BufferedReader.lines(): Stream<String> of lines. StringReader: đọc từ String (không cần file). 3 lines separated by \n.

## Câu 13

[TYPE: MULTIPLE_CHOICE]

Files.walk() vs Files.list()?

- [x] walk: recursive traversal; list: chỉ 1 level (direct children)
- [ ] Giống nhau
- [ ] list recursive, walk 1 level
- [ ] walk chỉ files, list cả directories

> **Giải thích:** Files.walk(dir): recursive, all subdirectories. Files.list(dir): chỉ direct children (depth 1). Cả hai return Stream<Path>.

## Câu 14

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("data.bin");
try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(path.toFile()))) {
    dos.writeInt(42);
    dos.writeDouble(3.14);
    dos.writeUTF("Hello");
}
try (DataInputStream dis = new DataInputStream(new FileInputStream(path.toFile()))) {
    System.out.println(dis.readInt());
    System.out.println(dis.readDouble());
    System.out.println(dis.readUTF());
}
Files.delete(path);
```

- [x] 42, 3.14, Hello
- [ ] Lỗi read
- [ ] 0, 0.0, null
- [ ] Lỗi biên dịch

> **Giải thích:** DataOutputStream/DataInputStream: write/read primitive types. Read phải cùng thứ tự và type với write. Binary format.

## Câu 15

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("/home/user/../user/./docs/file.txt");
Path normalized = path.normalize();
System.out.println(normalized);
```

- [x] /home/user/docs/file.txt
- [ ] /home/user/../user/./docs/file.txt
- [ ] /home/docs/file.txt
- [ ] /user/docs/file.txt

> **Giải thích:** normalize(): resolve `.` (current) và `..` (parent). `/home/user/../user` → `/home/user`. `./docs` → `docs`. Result: `/home/user/docs/file.txt`.

## Câu 16

[TYPE: FILL_BLANK]

`StandardOpenOption.___` mở file ở chế độ thêm vào cuối (append).

- [x] APPEND
- [ ] WRITE
- [ ] CREATE
- [ ] ADD

> **Giải thích:** StandardOpenOption: CREATE, WRITE, APPEND, TRUNCATE_EXISTING, READ, CREATE_NEW, DELETE_ON_CLOSE. APPEND: write at end.

## Câu 17

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("append.txt");
Files.writeString(path, "First\n");
Files.writeString(path, "Second\n", StandardOpenOption.APPEND);
System.out.println(Files.readString(path).trim());
Files.delete(path);
```

- [x] First\nSecond (hai dòng)
- [ ] Second
- [ ] First
- [ ] First Second

> **Giải thích:** First writeString: tạo/overwrite. Second with APPEND: thêm vào cuối. Kết quả 2 dòng. Không có APPEND → overwrite.

## Câu 18

[TYPE: MULTIPLE_CHOICE]

PrintWriter vs BufferedWriter?

- [x] PrintWriter: auto-flush, print/println/printf methods, no IOException; BufferedWriter: buffered, throws IOException
- [ ] Giống nhau
- [ ] BufferedWriter có print/println
- [ ] PrintWriter không buffered

> **Giải thích:** PrintWriter: convenient print methods, checkError() instead of throwing. BufferedWriter: efficient buffered writing, throws IOException. Có thể wrap: PrintWriter(BufferedWriter).

## Câu 19

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("test.txt");
Files.writeString(path, "ABCDEFGHIJ");
try (SeekableByteChannel channel = Files.newByteChannel(path, StandardOpenOption.READ)) {
    ByteBuffer buf = ByteBuffer.allocate(5);
    channel.position(3);
    channel.read(buf);
    buf.flip();
    byte[] bytes = new byte[buf.remaining()];
    buf.get(bytes);
    System.out.println(new String(bytes));
}
Files.delete(path);
```

- [x] DEFGH
- [ ] ABCDE
- [ ] FGHIJ
- [ ] CDEFG

> **Giải thích:** channel.position(3): seek to byte 3. Read 5 bytes: D(3) E(4) F(5) G(6) H(7). flip(): prepare buffer for reading.

## Câu 20

[TYPE: TRUE_FALSE]

Mệnh đề: "Scanner có thể đọc từ nhiều nguồn: file, InputStream, String, Readable."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Scanner: new Scanner(file), new Scanner(System.in), new Scanner("string"), new Scanner(inputStream). nextLine(), nextInt(), hasNext(), useDelimiter().

## Câu 21

[TYPE: SELECT_RESULT]

```java
Path dir = Path.of("testdir2");
Files.createDirectories(dir.resolve("sub1/sub2"));
System.out.println(Files.exists(dir.resolve("sub1")));
System.out.println(Files.exists(dir.resolve("sub1/sub2")));
// cleanup
Files.walk(dir).sorted(Comparator.reverseOrder()).forEach(p -> {
    try { Files.delete(p); } catch (IOException e) {}
});
```

- [x] true và true
- [ ] false và true
- [ ] true và false
- [ ] false và false

> **Giải thích:** createDirectories: tạo toàn bộ path bao gồm parent directories. Cả sub1 và sub1/sub2 đều được tạo. createDirectory: chỉ tạo 1 level.

## Câu 22

[TYPE: SELECT_RESULT]

```java
String csv = "name,age,city\nAn,25,HCM\nBình,30,HN";
try (BufferedReader br = new BufferedReader(new StringReader(csv))) {
    String header = br.readLine();
    String firstData = br.readLine();
    String[] parts = firstData.split(",");
    System.out.println(parts[0] + " is " + parts[1]);
}
```

- [x] An is 25
- [ ] name is age
- [ ] Bình is 30
- [ ] Lỗi runtime

> **Giải thích:** readLine 1: "name,age,city" (header, bỏ qua). readLine 2: "An,25,HCM". split(",") → ["An", "25", "HCM"]. parts[0]="An", parts[1]="25".

## Câu 23

[TYPE: MULTIPLE_CHOICE]

NIO2 WatchService dùng để:

- [x] Monitor file system changes (create, modify, delete events) trong directory
- [ ] Watch HTTP requests
- [ ] Monitor database changes
- [ ] Watch thread status

> **Giải thích:** WatchService: register directory, poll events (ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE). Real-time file monitoring. Non-blocking.

## Câu 24

[TYPE: SELECT_RESULT]

```java
Path source = Path.of("src.txt");
Path target = Path.of("dst.txt");
Files.writeString(source, "Original content");
Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
Files.writeString(source, "Modified content");
System.out.println(Files.readString(target));
Files.delete(source);
Files.delete(target);
```

- [x] Original content
- [ ] Modified content
- [ ] Lỗi file exists
- [ ] null

> **Giải thích:** copy: tạo copy tại thời điểm copy. Modify source sau → target không thay đổi. REPLACE_EXISTING: overwrite nếu target tồn tại.

## Câu 25

[TYPE: FILL_BLANK]

`Files.readAllBytes(path)` trả về `___[]`.

- [x] byte
- [ ] char
- [ ] String
- [ ] int

> **Giải thích:** readAllBytes: đọc toàn bộ file thành byte array. Cho binary files. readAllLines: cho text → List<String>. readString: cho text → String.

## Câu 26

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("props.txt");
Properties props = new Properties();
props.setProperty("db.host", "localhost");
props.setProperty("db.port", "5432");
try (OutputStream os = Files.newOutputStream(path)) {
    props.store(os, "Database Config");
}
Properties loaded = new Properties();
try (InputStream is = Files.newInputStream(path)) {
    loaded.load(is);
}
System.out.println(loaded.getProperty("db.host"));
System.out.println(loaded.getProperty("db.port"));
Files.delete(path);
```

- [x] localhost và 5432
- [ ] null và null
- [ ] Lỗi format
- [ ] db.host và db.port

> **Giải thích:** Properties: key=value format. store(): ghi ra file. load(): đọc từ file. getProperty(): lấy giá trị. Standard Java config format.

## Câu 27

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("temp.txt");
Files.writeString(path, "Hello World");
long size = Files.size(path);
System.out.println(size);
System.out.println(Files.isRegularFile(path));
System.out.println(Files.isDirectory(path));
Files.delete(path);
```

- [x] 11, true, false
- [ ] 12, true, false
- [ ] 11, false, true
- [ ] 10, true, false

> **Giải thích:** "Hello World" = 11 bytes (11 chars, UTF-8). isRegularFile=true (file, not directory). isDirectory=false.

## Câu 28

[TYPE: TRUE_FALSE]

Mệnh đề: "PipedInputStream và PipedOutputStream cho phép communication giữa hai threads."

- [x] Đúng
- [ ] Sai

> **Giải thích:** PipedInputStream connected to PipedOutputStream. Thread A writes → Thread B reads. Inter-thread communication via streams. Blocking when buffer full/empty.

## Câu 29

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("lines.txt");
Files.write(path, List.of("alpha", "beta", "gamma"));
try (Stream<String> lines = Files.lines(path)) {
    String result = lines.filter(s -> s.length() > 4).collect(Collectors.joining(", "));
    System.out.println(result);
}
Files.delete(path);
```

- [x] alpha, gamma
- [ ] alpha, beta, gamma
- [ ] beta
- [ ] alpha

> **Giải thích:** Files.write(path, Iterable): ghi collection. lines(): Stream<String>. filter length > 4: "alpha"(5) ✓, "beta"(4) ✗, "gamma"(5) ✓. Join: "alpha, gamma".

## Câu 30

[TYPE: MULTIPLE_CHOICE]

InputStream.transferTo(OutputStream) (Java 9+) làm gì?

- [x] Copy tất cả bytes từ InputStream sang OutputStream
- [ ] Transfer file ownership
- [ ] Move file
- [ ] Compress data

> **Giải thích:** transferTo: đọc từ input, ghi sang output cho đến hết. Thay thế manual byte[] buffer loop. Efficient, built-in.

## Câu 31

[TYPE: SELECT_RESULT]

```java
ByteBuffer buffer = ByteBuffer.allocate(10);
buffer.put((byte) 'H');
buffer.put((byte) 'i');
System.out.println("Position: " + buffer.position());
buffer.flip();
System.out.println("Limit: " + buffer.limit());
System.out.println((char) buffer.get());
```

- [x] Position: 2, Limit: 2, H
- [ ] Position: 0, Limit: 10, H
- [ ] Position: 2, Limit: 10, H
- [ ] Position: 0, Limit: 2, i

> **Giải thích:** put 2 bytes → position=2. flip(): limit=position(2), position=0. get(): read at position 0 → 'H'. NIO Buffer: position, limit, capacity.

## Câu 32

[TYPE: FILL_BLANK]

`Charset.___()` trả về default character encoding của JVM.

- [x] defaultCharset
- [ ] getDefault
- [ ] systemCharset
- [ ] encoding

> **Giải thích:** Charset.defaultCharset(): thường UTF-8. Explicit charset: `new InputStreamReader(is, StandardCharsets.UTF_8)`. Avoid default charset cho portability.

## Câu 33

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("utf8.txt");
Files.writeString(path, "Xin chào Việt Nam", StandardCharsets.UTF_8);
byte[] bytes = Files.readAllBytes(path);
String content = new String(bytes, StandardCharsets.UTF_8);
System.out.println(content);
System.out.println(bytes.length > content.length());
Files.delete(path);
```

- [x] Xin chào Việt Nam và true
- [ ] Xin chào Việt Nam và false
- [ ] Lỗi encoding
- [ ] null

> **Giải thích:** UTF-8: Vietnamese chars (ào, ệ) dùng > 1 byte. bytes.length > chars count. "Xin chào Việt Nam" nhiều bytes hơn chars.

## Câu 34

[TYPE: MULTIPLE_CHOICE]

File.separator vs "/" trong Java paths?

- [x] "/" hoạt động cross-platform trong Java, File.separator trả OS-specific separator
- [ ] Phải dùng File.separator
- [ ] "/" chỉ hoạt động trên Unix
- [ ] Không có sự khác biệt

> **Giải thích:** Java tự động convert "/" thành OS separator. Path.of("a/b/c") works trên Windows lẫn Unix. File.separator: "\" trên Windows, "/" trên Unix.

## Câu 35

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("test.txt");
Files.writeString(path, "Hello");
BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
System.out.println(attrs.isRegularFile());
System.out.println(attrs.size());
Files.delete(path);
```

- [x] true và 5
- [ ] false và 5
- [ ] true và 0
- [ ] Lỗi

> **Giải thích:** BasicFileAttributes: file metadata. isRegularFile()=true. size()=5 bytes ("Hello"). Cũng có: creationTime(), lastModifiedTime(), isDirectory().

## Câu 36

[TYPE: SELECT_RESULT]

```java
StringWriter sw = new StringWriter();
PrintWriter pw = new PrintWriter(sw);
pw.printf("Name: %s, Age: %d", "An", 25);
pw.println();
pw.print("Done");
System.out.println(sw.toString());
```

- [x] Name: An, Age: 25 (newline) Done
- [ ] Name: %s, Age: %d
- [ ] Lỗi biên dịch
- [ ] null

> **Giải thích:** PrintWriter → StringWriter: ghi vào String. printf: formatted output. println: add newline. print: no newline.

## Câu 37

[TYPE: TRUE_FALSE]

Mệnh đề: "RandomAccessFile cho phép đọc/ghi tại bất kỳ vị trí nào trong file."

- [x] Đúng
- [ ] Sai

> **Giải thích:** RandomAccessFile: seek(position), read/write tại vị trí bất kỳ. Mode: "r" (read), "rw" (read-write). Dùng cho database files, random access patterns.

## Câu 38

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("random.dat");
try (RandomAccessFile raf = new RandomAccessFile(path.toFile(), "rw")) {
    raf.writeInt(100);
    raf.writeInt(200);
    raf.writeInt(300);
    raf.seek(4); // int = 4 bytes
    System.out.println(raf.readInt());
}
Files.delete(path);
```

- [x] 200
- [ ] 100
- [ ] 300
- [ ] Lỗi

> **Giải thích:** 3 ints: bytes 0-3 (100), 4-7 (200), 8-11 (300). seek(4) → position at byte 4. readInt() → 200. Random access.

## Câu 39

[TYPE: MULTIPLE_CHOICE]

Files.newDirectoryStream() vs Files.list()?

- [x] DirectoryStream: Iterable (for-each), auto-closeable; list: Stream<Path>, lazy
- [ ] Giống nhau
- [ ] list dùng cho DirectoryStream
- [ ] DirectoryStream recursive

> **Giải thích:** DirectoryStream: pre-Java 8, Iterable, glob filter. Files.list: Java 8+, Stream API, lazy, combinable with filter/map/collect.

## Câu 40

[TYPE: SELECT_RESULT]

```java
Path dir = Path.of("globtest");
Files.createDirectory(dir);
Files.writeString(dir.resolve("a.java"), "");
Files.writeString(dir.resolve("b.txt"), "");
Files.writeString(dir.resolve("c.java"), "");
List<String> javaFiles;
try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir, "*.java")) {
    javaFiles = new ArrayList<>();
    for (Path p : ds) javaFiles.add(p.getFileName().toString());
}
Collections.sort(javaFiles);
System.out.println(javaFiles);
Files.walk(dir).sorted(Comparator.reverseOrder()).forEach(p -> {
    try { Files.delete(p); } catch (IOException e) {}
});
```

- [x] [a.java, c.java]
- [ ] [a.java, b.txt, c.java]
- [ ] [b.txt]
- [ ] []

> **Giải thích:** Glob "*.java": chỉ files matching .java extension. a.java ✓, b.txt ✗, c.java ✓. Sorted: [a.java, c.java].

## Câu 41

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("lines.txt");
Files.writeString(path, "line1\nline2\nline3\nline4\nline5");
try (Stream<String> stream = Files.lines(path)) {
    stream.skip(2).limit(2).forEach(System.out::println);
}
Files.delete(path);
```

- [x] line3 và line4
- [ ] line1 và line2
- [ ] line4 và line5
- [ ] line3

> **Giải thích:** skip(2): bỏ 2 dòng đầu (line1, line2). limit(2): lấy 2 dòng tiếp (line3, line4). Lazy processing.

## Câu 42

[TYPE: FILL_BLANK]

`Files.createTempFile(prefix, suffix)` tạo file tạm trong thư mục `___` của hệ thống.

- [x] temp (temporary)
- [ ] home
- [ ] root
- [ ] current

> **Giải thích:** createTempFile: OS temp directory (/tmp trên Linux, %TEMP% trên Windows). Có thể specify directory: createTempFile(dir, prefix, suffix).

## Câu 43

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("compress.gz");
try (GZIPOutputStream gzos = new GZIPOutputStream(Files.newOutputStream(path))) {
    gzos.write("Hello compressed world!".getBytes());
}
try (GZIPInputStream gzis = new GZIPInputStream(Files.newInputStream(path))) {
    String content = new String(gzis.readAllBytes());
    System.out.println(content);
}
Files.delete(path);
```

- [x] Hello compressed world!
- [ ] Binary data
- [ ] Lỗi biên dịch
- [ ] null

> **Giải thích:** GZIPOutputStream: compress data. GZIPInputStream: decompress. readAllBytes() (Java 9+). Transparent compression/decompression.

## Câu 44

[TYPE: SELECT_RESULT]

```java
Path base = Path.of("/home/user");
Path other = Path.of("/home/user/docs/file.txt");
Path relative = base.relativize(other);
System.out.println(relative);
Path resolved = base.resolve("images/photo.jpg");
System.out.println(resolved);
```

- [x] docs/file.txt và /home/user/images/photo.jpg
- [ ] /home/user/docs/file.txt và images/photo.jpg
- [ ] file.txt và /home/user/images
- [ ] Lỗi runtime

> **Giải thích:** relativize: tính relative path từ base đến other → docs/file.txt. resolve: join paths → /home/user/images/photo.jpg.

## Câu 45

[TYPE: MULTIPLE_CHOICE]

try-with-resources với multiple resources thì close thứ tự nào?

- [x] Reverse order (LIFO) - resource khai báo sau close trước
- [ ] Same order (FIFO)
- [ ] Random
- [ ] Chỉ resource đầu tiên

> **Giải thích:** `try (A a = ...; B b = ...)`: close B trước, close A sau. LIFO như stack. Phù hợp dependency: A tạo trước → close sau.

## Câu 46

[TYPE: SELECT_RESULT]

```java
CharArrayWriter caw = new CharArrayWriter();
caw.write("Hello");
caw.write(' ');
caw.write("World");
char[] result = caw.toCharArray();
System.out.println(new String(result));
System.out.println(result.length);
```

- [x] Hello World và 11
- [ ] Hello và 5
- [ ] HelloWorld và 10
- [ ] Lỗi biên dịch

> **Giải thích:** CharArrayWriter: write chars to internal buffer. "Hello" + ' ' + "World" = "Hello World" = 11 chars. toCharArray() → char[11].

## Câu 47

[TYPE: TRUE_FALSE]

Mệnh đề: "Memory-mapped files (MappedByteBuffer) map file vào memory để truy cập như array."

- [x] Đúng
- [ ] Sai

> **Giải thích:** FileChannel.map(): map file region vào memory. MappedByteBuffer: direct access. Very fast cho large files. OS handles paging.

## Câu 48

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("walk_test");
Files.createDirectories(path.resolve("a/b"));
Files.writeString(path.resolve("a/file1.txt"), "");
Files.writeString(path.resolve("a/b/file2.txt"), "");
long fileCount;
try (Stream<Path> stream = Files.walk(path)) {
    fileCount = stream.filter(Files::isRegularFile).count();
}
System.out.println(fileCount);
Files.walk(path).sorted(Comparator.reverseOrder()).forEach(p -> {
    try { Files.delete(p); } catch (IOException e) {}
});
```

- [x] 2
- [ ] 4
- [ ] 1
- [ ] 3

> **Giải thích:** walk recursive: tìm tất cả paths. filter(isRegularFile): chỉ files (không directories). file1.txt, file2.txt = 2 files.

## Câu 49

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("stream.txt");
Files.writeString(path, "abc");
try (InputStream is = Files.newInputStream(path)) {
    int b;
    while ((b = is.read()) != -1) {
        System.out.print((char) b + " ");
    }
}
Files.delete(path);
```

- [x] a b c
- [ ] 97 98 99
- [ ] abc
- [ ] -1

> **Giải thích:** read(): trả int (byte value). Cast to char: 97→'a', 98→'b', 99→'c'. -1 = end of stream. Byte-by-byte reading.

## Câu 50

[TYPE: MULTIPLE_CHOICE]

Console class (System.console()) dùng khi nào?

- [x] Đọc password an toàn (readPassword() không echo), interactive console I/O
- [ ] Chỉ cho logging
- [ ] Thay thế Scanner
- [ ] Chỉ cho GUI

> **Giải thích:** Console: readPassword() (no echo), readLine(). Chỉ available khi chạy từ terminal (không available trong IDE). System.console() có thể return null.

## Câu 51

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("channel.txt");
try (FileChannel fc = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
    ByteBuffer buf = ByteBuffer.wrap("NIO Channel".getBytes());
    fc.write(buf);
}
System.out.println(Files.readString(path));
Files.delete(path);
```

- [x] NIO Channel
- [ ] null
- [ ] Lỗi biên dịch
- [ ] Empty file

> **Giải thích:** FileChannel: NIO channel API. wrap(): create ByteBuffer from byte array. write(buf): ghi buffer contents. Efficient I/O.

## Câu 52

[TYPE: FILL_BLANK]

`System.lineSeparator()` trả về `___` separator phụ thuộc hệ điều hành.

- [x] line (dòng mới)
- [ ] file
- [ ] word
- [ ] path

> **Giải thích:** lineSeparator(): "\n" (Unix/Mac), "\r\n" (Windows). Portable newline. Dùng thay vì hardcode "\n".

## Câu 53

[TYPE: SELECT_RESULT]

```java
class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    transient String password;
    Person(String n, String p) { name = n; password = p; }
}
Path path = Path.of("person.dat");
try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
    oos.writeObject(new Person("An", "secret123"));
}
try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
    Person p = (Person) ois.readObject();
    System.out.println(p.name + " " + p.password);
}
Files.delete(path);
```

- [x] An null
- [ ] An secret123
- [ ] Lỗi NotSerializableException
- [ ] null null

> **Giải thích:** transient: field không được serialize. Deserialize → password=null (default). name serialize bình thường. Serializable security pattern.

## Câu 54

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("multi.txt");
try (BufferedWriter bw = Files.newBufferedWriter(path)) {
    bw.write("First");
    bw.newLine();
    bw.write("Second");
}
try (BufferedReader br = Files.newBufferedReader(path)) {
    String line;
    int count = 0;
    while ((line = br.readLine()) != null) count++;
    System.out.println(count);
}
Files.delete(path);
```

- [x] 2
- [ ] 1
- [ ] 3
- [ ] 0

> **Giải thích:** Files.newBufferedWriter/Reader: convenience methods. 2 writes with newLine → 2 lines. readLine loop counts: First, Second = 2.

## Câu 55

[TYPE: MULTIPLE_CHOICE]

Đâu là cách tạo InputStream từ String?

- [x] new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8))
- [ ] new StringInputStream(string)
- [ ] InputStream.of(string)
- [ ] new FileInputStream(string)

> **Giải thích:** ByteArrayInputStream: wrap byte[] as InputStream. getBytes(charset): String → bytes. Useful cho testing, parsing String as stream.

## Câu 56

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("test.txt");
Files.writeString(path, "Hello");
try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
    long size = channel.size();
    MappedByteBuffer mbb = channel.map(FileChannel.MapMode.READ_ONLY, 0, size);
    byte[] bytes = new byte[(int) size];
    mbb.get(bytes);
    System.out.println(new String(bytes));
}
Files.delete(path);
```

- [x] Hello
- [ ] null
- [ ] Lỗi
- [ ] Binary data

> **Giải thích:** Memory-mapped file: map file vào virtual memory. Direct access. Very efficient cho large files. MappedByteBuffer extends ByteBuffer.

## Câu 57

[TYPE: TRUE_FALSE]

Mệnh đề: "Files.move() có thể rename file và move file across directories."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Files.move(source, target): rename nếu cùng directory, move nếu khác directory. ATOMIC_MOVE: atomic operation (if supported by filesystem).

## Câu 58

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("test.txt");
Files.writeString(path, "Content");
Path link = Path.of("link.txt");
Files.createSymbolicLink(link, path);
System.out.println(Files.isSymbolicLink(link));
System.out.println(Files.readString(link));
Files.delete(link);
Files.delete(path);
```

Giả sử OS hỗ trợ:

- [x] true và Content
- [ ] false và Content
- [ ] true và null
- [ ] Lỗi

> **Giải thích:** createSymbolicLink: symbolic link trỏ đến target. isSymbolicLink=true. readString(link): follow link → read target content. OS support required.

## Câu 59

[TYPE: SELECT_RESULT]

```java
StreamTokenizer st = new StreamTokenizer(new StringReader("Hello 42 3.14 World"));
List<String> tokens = new ArrayList<>();
while (st.nextToken() != StreamTokenizer.TT_EOF) {
    if (st.ttype == StreamTokenizer.TT_WORD) tokens.add(st.sval);
    else if (st.ttype == StreamTokenizer.TT_NUMBER) tokens.add(String.valueOf(st.nval));
}
System.out.println(tokens);
```

- [x] [Hello, 42.0, 3.14, World]
- [ ] [Hello, World]
- [ ] [42, 3.14]
- [ ] Lỗi

> **Giải thích:** StreamTokenizer: tokenize text. TT_WORD: string token. TT_NUMBER: numeric token (stored as double). All tokens: Hello, 42.0, 3.14, World.

## Câu 60

[TYPE: FILL_BLANK]

`FileChannel.lock()` dùng để `___` file, ngăn processes khác truy cập.

- [x] lock (khóa)
- [ ] encrypt
- [ ] compress
- [ ] cache

> **Giải thích:** FileChannel.lock(): file locking. Exclusive lock (write) hoặc shared lock (read). tryLock(): non-blocking. Release: lock.release() hoặc close channel.

## Câu 61

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("test.txt");
Files.writeString(path, "Hello World");
try (var reader = Files.newBufferedReader(path)) {
    reader.mark(100);
    char[] buf = new char[5];
    reader.read(buf);
    System.out.print(new String(buf) + " ");
    reader.reset();
    reader.read(buf);
    System.out.print(new String(buf));
}
Files.delete(path);
```

- [x] Hello Hello
- [ ] Hello World
- [ ] Hello
- [ ] Lỗi

> **Giải thích:** mark(100): set mark at current position. read 5 chars → "Hello". reset(): go back to mark. read 5 chars again → "Hello". Mark/reset for re-reading.

## Câu 62

[TYPE: SELECT_RESULT]

```java
byte[] data = {72, 101, 108, 108, 111};
ByteArrayInputStream bais = new ByteArrayInputStream(data);
int available = bais.available();
byte[] result = bais.readAllBytes();
System.out.println(available + ": " + new String(result));
```

- [x] 5: Hello
- [ ] 0: Hello
- [ ] 5:
- [ ] Lỗi

> **Giải thích:** byte values: 72=H, 101=e, 108=l, 108=l, 111=o. available()=5. readAllBytes() → "Hello". ByteArrayInputStream: no I/O, in-memory.

## Câu 63

[TYPE: MULTIPLE_CHOICE]

Khi nào dùng BufferedInputStream?

- [x] Khi wrap unbuffered stream (FileInputStream) để giảm system calls, tăng performance
- [ ] Luôn luôn
- [ ] Chỉ cho network
- [ ] Không bao giờ cần

> **Giải thích:** BufferedInputStream: buffer (8KB default). Một read system call cho nhiều bytes thay vì mỗi byte một call. Significant performance improvement cho file/network I/O.

## Câu 64

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("test.txt");
Files.writeString(path, "Hello World 123");
try (Scanner scanner = new Scanner(path)) {
    System.out.print(scanner.next() + " ");
    System.out.print(scanner.next() + " ");
    System.out.print(scanner.nextInt());
}
Files.delete(path);
```

- [x] Hello World 123
- [ ] Hello World 123
- [ ] Hello
- [ ] Lỗi InputMismatchException

> **Giải thích:** Scanner: next() đọc token (whitespace delimited). "Hello", "World", nextInt() = 123. Scanner auto-tokenize by whitespace.

## Câu 65

[TYPE: TRUE_FALSE]

Mệnh đề: "SequenceInputStream nối nhiều InputStreams thành một stream."

- [x] Đúng
- [ ] Sai

> **Giải thích:** SequenceInputStream: đọc stream 1 đến hết, rồi stream 2, etc. Concatenate streams. `new SequenceInputStream(is1, is2)`.

## Câu 66

[TYPE: SELECT_RESULT]

```java
Path tempDir = Files.createTempDirectory("myapp_");
Path tempFile = Files.createTempFile(tempDir, "data_", ".tmp");
Files.writeString(tempFile, "temporary data");
System.out.println(tempFile.getFileName().toString().startsWith("data_"));
System.out.println(tempFile.getFileName().toString().endsWith(".tmp"));
Files.delete(tempFile);
Files.delete(tempDir);
```

- [x] true và true
- [ ] false và true
- [ ] true và false
- [ ] false và false

> **Giải thích:** createTempFile(dir, prefix, suffix): prefix="data_", suffix=".tmp". Filename: data_<random>.tmp. startsWith("data_")=true, endsWith(".tmp")=true.

## Câu 67

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("test.txt");
Files.writeString(path, "ABCDEFGHIJ");
try (var channel = Files.newByteChannel(path)) {
    ByteBuffer buf = ByteBuffer.allocate(3);
    StringBuilder sb = new StringBuilder();
    while (channel.read(buf) > 0) {
        buf.flip();
        while (buf.hasRemaining()) sb.append((char) buf.get());
        buf.clear();
    }
    System.out.println(sb);
}
Files.delete(path);
```

- [x] ABCDEFGHIJ
- [ ] ABC
- [ ] ABCDEF
- [ ] Lỗi

> **Giải thích:** Buffer size 3: read in chunks. ABC → DEF → GHI → J. flip/clear cycle. Đọc toàn bộ file qua buffer. Standard NIO pattern.

## Câu 68

[TYPE: FILL_BLANK]

`InputStreamReader` convert `___` stream sang character stream.

- [x] byte
- [ ] character
- [ ] object
- [ ] string

> **Giải thích:** InputStreamReader: bridge byte → char. Wraps InputStream, decodes bytes using charset. OutputStreamWriter: char → byte. Adapter pattern.

## Câu 69

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("piped.txt");
PipedOutputStream pos = new PipedOutputStream();
PipedInputStream pis = new PipedInputStream(pos);

Thread writer = new Thread(() -> {
    try {
        pos.write("Hello Piped".getBytes());
        pos.close();
    } catch (IOException e) {}
});

writer.start();
String result = new String(pis.readAllBytes());
pis.close();
System.out.println(result);
```

- [x] Hello Piped
- [ ] Deadlock
- [ ] null
- [ ] IOException

> **Giải thích:** Writer thread writes, then closes. Main thread reads all bytes. PipedInputStream/OutputStream: inter-thread communication. Must be in different threads.

## Câu 70

[TYPE: MULTIPLE_CHOICE]

File.deleteOnExit() vs Files.delete()?

- [x] deleteOnExit: delete khi JVM exits (deferred); Files.delete: delete ngay lập tức
- [ ] Giống nhau
- [ ] delete ngay tốt hơn
- [ ] deleteOnExit nhanh hơn

> **Giải thích:** deleteOnExit(): register for deletion on JVM shutdown. Useful for temp files. Risk: accumulate if JVM runs long. Files.delete(): immediate, preferred.

## Câu 71

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("convert.txt");
try (Writer writer = new OutputStreamWriter(
        Files.newOutputStream(path), StandardCharsets.ISO_8859_1)) {
    writer.write("Hello ISO");
}
byte[] bytes = Files.readAllBytes(path);
System.out.println(new String(bytes, StandardCharsets.ISO_8859_1));
Files.delete(path);
```

- [x] Hello ISO
- [ ] Garbled text
- [ ] Lỗi encoding
- [ ] null

> **Giải thích:** OutputStreamWriter: encode chars → bytes using ISO_8859_1. Read with same charset → correct decode. Charset consistency important.

## Câu 72

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("test.txt");
Files.writeString(path, "Hello");
FileStore store = Files.getFileStore(path);
System.out.println(store.getTotalSpace() > 0);
System.out.println(store.getUsableSpace() >= 0);
Files.delete(path);
```

- [x] true và true
- [ ] false và false
- [ ] Lỗi
- [ ] true và false

> **Giải thích:** FileStore: file system info. getTotalSpace(): tổng dung lượng. getUsableSpace(): available space. Useful cho disk space checks.

## Câu 73

[TYPE: TRUE_FALSE]

Mệnh đề: "Java NIO Selector cho phép một thread quản lý nhiều Channels (multiplexing)."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Selector: register multiple channels (SocketChannel). select() blocks until events. Non-blocking I/O multiplexing. Scalable server pattern.

## Câu 74

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("lines.txt");
Files.write(path, List.of("a", "b", "c", "d", "e"));
List<String> lines = Files.readAllLines(path);
Collections.reverse(lines);
Files.write(path, lines);
System.out.println(Files.readAllLines(path));
Files.delete(path);
```

- [x] [e, d, c, b, a]
- [ ] [a, b, c, d, e]
- [ ] [a, e, d, c, b]
- [ ] Lỗi

> **Giải thích:** Read all lines → reverse → write back. File now contains reversed lines. readAllLines returns new list.

## Câu 75

[TYPE: SELECT_RESULT]

```java
Path source = Path.of("source.txt");
Path target = Path.of("target.txt");
Files.writeString(source, "Important Data");
Files.move(source, target, StandardCopyOption.ATOMIC_MOVE);
System.out.println(Files.exists(source));
System.out.println(Files.readString(target));
Files.delete(target);
```

- [x] false và Important Data
- [ ] true và Important Data
- [ ] false và null
- [ ] Lỗi

> **Giải thích:** move: source → target. source không còn tồn tại. target có nội dung. ATOMIC_MOVE: đảm bảo atomicity (nếu filesystem hỗ trợ).

## Câu 76

[TYPE: FILL_BLANK]

`Files.probeContentType(path)` trả về `___` type của file (ví dụ "text/plain").

- [x] MIME (content/media)
- [ ] file
- [ ] extension
- [ ] encoding

> **Giải thích:** probeContentType: return MIME type. "text/plain", "image/png", "application/pdf". Based on file content/extension. May return null.

## Câu 77

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("find_test");
Files.createDirectories(path.resolve("sub"));
Files.writeString(path.resolve("a.java"), "");
Files.writeString(path.resolve("b.txt"), "");
Files.writeString(path.resolve("sub/c.java"), "");
List<String> found;
try (Stream<Path> stream = Files.find(path, Integer.MAX_VALUE,
        (p, attrs) -> p.toString().endsWith(".java") && attrs.isRegularFile())) {
    found = stream.map(p -> p.getFileName().toString()).sorted().collect(Collectors.toList());
}
System.out.println(found);
Files.walk(path).sorted(Comparator.reverseOrder()).forEach(p -> {
    try { Files.delete(p); } catch (IOException e) {}
});
```

- [x] [a.java, c.java]
- [ ] [a.java, b.txt, c.java]
- [ ] [a.java]
- [ ] []

> **Giải thích:** Files.find: walk + filter by BiPredicate(Path, BasicFileAttributes). .java files + isRegularFile. Recursive: a.java, sub/c.java.

## Câu 78

[TYPE: SELECT_RESULT]

```java
ByteBuffer buf = ByteBuffer.allocate(10);
buf.putInt(42);
buf.putShort((short) 7);
System.out.println("Position: " + buf.position());
buf.flip();
System.out.println("Int: " + buf.getInt());
System.out.println("Short: " + buf.getShort());
```

- [x] Position: 6, Int: 42, Short: 7
- [ ] Position: 10, Int: 42, Short: 7
- [ ] Position: 4, Int: 42, Short: 0
- [ ] Lỗi BufferOverflow

> **Giải thích:** putInt(4 bytes) + putShort(2 bytes) = position 6. flip: limit=6, position=0. getInt()=42, getShort()=7. Type-safe binary I/O.

## Câu 79

[TYPE: MULTIPLE_CHOICE]

Đâu là best practice khi đọc large files?

- [x] Dùng Files.lines() (lazy Stream), BufferedReader, hoặc NIO Channel với buffer
- [ ] Files.readAllBytes()
- [ ] Files.readString()
- [ ] Scanner without buffer

> **Giải thích:** Large files: Files.lines() (lazy, streaming). BufferedReader.readLine() (buffered). NIO Channel + ByteBuffer. Tránh readAllBytes/readString cho large files (OOM risk).

## Câu 80

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("attrs_test.txt");
Files.writeString(path, "Test");
Files.setAttribute(path, "dos:hidden", true);
boolean hidden = (boolean) Files.getAttribute(path, "dos:hidden");
System.out.println(hidden);
Files.delete(path);
```

Trên Windows:

- [x] true
- [ ] false
- [ ] UnsupportedOperationException trên Linux
- [ ] Lỗi biên dịch

> **Giải thích:** File attributes: dos: (Windows), posix: (Unix). setAttribute/getAttribute: set/get file attributes. Platform-specific. Linux → UnsupportedOperationException cho "dos:".

## Câu 81

[TYPE: TRUE_FALSE]

Mệnh đề: "AsynchronousFileChannel hỗ trợ non-blocking file I/O với CompletionHandler hoặc Future."

- [x] Đúng
- [ ] Sai

> **Giải thích:** AsynchronousFileChannel: async read/write. CompletionHandler callback hoặc Future<Integer>. Non-blocking file I/O. Java NIO.2 (Java 7+).

## Câu 82

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("test.txt");
Files.writeString(path, "Hello World");
try (var reader = Files.newBufferedReader(path)) {
    long count = reader.lines().filter(line -> line.contains("World")).count();
    System.out.println(count);
}
Files.delete(path);
```

- [x] 1
- [ ] 0
- [ ] 2
- [ ] Lỗi

> **Giải thích:** 1 line "Hello World" contains "World" → count = 1. BufferedReader.lines(): Stream for processing.

## Câu 83

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("zip.zip");
try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(path))) {
    zos.putNextEntry(new ZipEntry("file.txt"));
    zos.write("Zipped content".getBytes());
    zos.closeEntry();
}
try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(path))) {
    ZipEntry entry = zis.getNextEntry();
    System.out.println(entry.getName());
    System.out.println(new String(zis.readAllBytes()));
}
Files.delete(path);
```

- [x] file.txt và Zipped content
- [ ] zip.zip và null
- [ ] Lỗi
- [ ] file.txt và binary data

> **Giải thích:** ZipOutputStream: tạo ZIP file. putNextEntry + write + closeEntry. ZipInputStream: đọc ZIP. getNextEntry → entry name. readAllBytes → content.

## Câu 84

[TYPE: FILL_BLANK]

`Path.toAbsolutePath()` convert relative path thành `___` path.

- [x] absolute
- [ ] canonical
- [ ] relative
- [ ] normalized

> **Giải thích:** toAbsolutePath(): relative → absolute (prepend working directory). toRealPath(): resolve symlinks + normalize. absolute ≠ canonical (may contain . or ..).

## Câu 85

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("test.csv");
try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(path))) {
    pw.println("name,score");
    pw.printf("%s,%d%n", "An", 95);
    pw.printf("%s,%d%n", "Bình", 88);
}
Files.readAllLines(path).forEach(System.out::println);
Files.delete(path);
```

- [x] name,score / An,95 / Bình,88
- [ ] Chỉ name,score
- [ ] Lỗi biên dịch
- [ ] An,95 / Bình,88

> **Giải thích:** PrintWriter: println + printf. %n = platform newline. 3 lines: header + 2 data rows. CSV format.

## Câu 86

[TYPE: SELECT_RESULT]

```java
CharBuffer cbuf = CharBuffer.allocate(10);
cbuf.put("Hello");
cbuf.flip();
String result = cbuf.toString();
System.out.println(result + " length=" + result.length());
```

- [x] Hello length=5
- [ ] Hello      length=10
- [ ] Lỗi biên dịch
- [ ] null length=0

> **Giải thích:** put("Hello"): 5 chars, position=5. flip(): limit=5, position=0. toString(): chars from position to limit = "Hello" (5 chars).

## Câu 87

[TYPE: MULTIPLE_CHOICE]

Đâu là advantages của NIO.2 Path API so với java.io.File?

- [x] Immutable, better error handling, symbolic links, file attributes, watch service
- [ ] File nhanh hơn
- [ ] Không có advantages
- [ ] Path chỉ cho Unix

> **Giải thích:** Path: immutable, exception-based errors (vs boolean return), symbolic link support, rich attributes, WatchService. File: legacy, boolean errors, limited.

## Câu 88

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("lines.txt");
Files.writeString(path, "apple\nbanana\napple\ncherry\napple");
Map<String, Long> freq;
try (Stream<String> lines = Files.lines(path)) {
    freq = lines.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
}
System.out.println(freq.get("apple"));
Files.delete(path);
```

- [x] 3
- [ ] 1
- [ ] 5
- [ ] Lỗi

> **Giải thích:** lines: apple, banana, apple, cherry, apple. groupingBy identity + counting: apple=3. Frequency count from file.

## Câu 89

[TYPE: TRUE_FALSE]

Mệnh đề: "Files.isSameFile(path1, path2) kiểm tra hai paths trỏ đến cùng một file vật lý."

- [x] Đúng
- [ ] Sai

> **Giải thích:** isSameFile: resolve symbolic links, compare file system identity. Khác với path.equals() (chỉ compare path strings). Handles symlinks, hard links.

## Câu 90

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("posix.txt");
Files.writeString(path, "test");
try {
    Set<PosixFilePermission> perms = Files.getPosixFilePermissions(path);
    System.out.println(perms.contains(PosixFilePermission.OWNER_READ));
} catch (UnsupportedOperationException e) {
    System.out.println("Not POSIX");
}
Files.delete(path);
```

Trên Linux:

- [x] true
- [ ] false
- [ ] Not POSIX
- [ ] Lỗi

> **Giải thích:** Linux = POSIX filesystem. OWNER_READ thường true cho files. getPosixFilePermissions: rwx for owner/group/others. Windows → UnsupportedOperationException.

## Câu 91

[TYPE: SELECT_RESULT]

```java
byte[] original = "Hello World".getBytes();
String encoded = Base64.getEncoder().encodeToString(original);
System.out.print(encoded.length() > original.length);
byte[] decoded = Base64.getDecoder().decode(encoded);
System.out.print(" " + new String(decoded));
```

- [x] true Hello World
- [ ] false Hello World
- [ ] true SGVsbG8gV29ybGQ=
- [ ] Lỗi

> **Giải thích:** Base64: encode binary → text. Encoded larger than original (~33% overhead). Decode back → original bytes. Built-in Java 8+.

## Câu 92

[TYPE: FILL_BLANK]

`FileVisitor` interface dùng với `Files.walkFileTree()` để `___` directory tree.

- [x] traverse (duyệt)
- [ ] delete
- [ ] copy
- [ ] create

> **Giải thích:** FileVisitor: preVisitDirectory, visitFile, postVisitDirectory, visitFileFailed. walkFileTree: depth-first traversal. More control than Files.walk().

## Câu 93

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("gather.txt");
try (FileChannel fc = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
    ByteBuffer[] buffers = {
        ByteBuffer.wrap("Hello ".getBytes()),
        ByteBuffer.wrap("World".getBytes())
    };
    fc.write(buffers); // gathering write
}
System.out.println(Files.readString(path));
Files.delete(path);
```

- [x] Hello World
- [ ] Hello
- [ ] World
- [ ] Lỗi

> **Giải thích:** Gathering write: write from multiple buffers in one operation. Efficient for headers + data. Scatter read: read into multiple buffers.

## Câu 94

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("test.txt");
Files.writeString(path, "Hello");
Path path2 = Path.of("TEST.txt");
System.out.println(path.equals(path2));
System.out.println(path.compareTo(path2));
Files.delete(path);
```

Trên case-sensitive filesystem (Linux):

- [x] false và số khác 0
- [ ] true và 0
- [ ] false và 0
- [ ] Lỗi

> **Giải thích:** Linux case-sensitive: "test.txt" ≠ "TEST.txt". equals=false, compareTo≠0. Windows case-insensitive: có thể equals=true.

## Câu 95

[TYPE: MULTIPLE_CHOICE]

Khi nào dùng ObjectInputStream/ObjectOutputStream?

- [x] Java native serialization cho inter-JVM communication, deep clone, persistence
- [ ] Cho mọi I/O
- [ ] Thay thế JSON
- [ ] Chỉ cho text

> **Giải thích:** Java Serialization: Object → bytes → Object. Use cases: RMI, session replication, deep clone. Drawbacks: security risks, version coupling. Prefer JSON/Protobuf.

## Câu 96

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("test.txt");
Files.writeString(path, "Hello World Hello Java");
String content = Files.readString(path);
long count = content.chars().filter(c -> c == 'l').count();
System.out.println(count);
Files.delete(path);
```

- [x] 5
- [ ] 3
- [ ] 4
- [ ] 2

> **Giải thích:** "Hello World Hello Java": l xuất hiện tại: He_ll_o Wor_l_d He_ll_o Java = 5 lần (ll=2 + l=1 + ll=2).

## Câu 97

[TYPE: TRUE_FALSE]

Mệnh đề: "Java 11 thêm Files.readString() và Files.writeString() cho đơn giản hóa text file I/O."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Java 11: readString(path) → String, writeString(path, content) → Path. UTF-8 default. Simplify common text I/O tasks.

## Câu 98

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("test.txt");
Files.writeString(path, "Line1\nLine2\nLine3");
try (var channel = Files.newByteChannel(path)) {
    ByteBuffer buf = ByteBuffer.allocate(5);
    channel.read(buf);
    buf.flip();
    System.out.print(StandardCharsets.UTF_8.decode(buf) + " ");
    System.out.print("pos:" + channel.position());
}
Files.delete(path);
```

- [x] Line1 pos:5
- [ ] Line1 pos:0
- [ ] Line pos:4
- [ ] Lỗi

> **Giải thích:** Read 5 bytes → "Line1". Channel position advances to 5. decode(): ByteBuffer → CharBuffer → String.

## Câu 99

[TYPE: SELECT_RESULT]

```java
Path path = Path.of("deletetest");
Files.createDirectory(path);
Files.writeString(path.resolve("file.txt"), "data");
try {
    Files.delete(path);
} catch (DirectoryNotEmptyException e) {
    System.out.println("Directory not empty");
}
// cleanup
Files.delete(path.resolve("file.txt"));
Files.delete(path);
```

- [x] Directory not empty
- [ ] Directory deleted successfully
- [ ] IOException
- [ ] Lỗi biên dịch

> **Giải thích:** Files.delete(directory) với non-empty directory → DirectoryNotEmptyException. Phải delete contents trước. Dùng Files.walk() cho recursive delete.

## Câu 100

[TYPE: MULTIPLE_CHOICE]

Đâu là cách đọc resource file từ classpath?

- [x] getClass().getResourceAsStream("/file.txt") hoặc ClassLoader.getSystemResourceAsStream()
- [ ] new FileReader("/file.txt")
- [ ] Files.readString(Path.of("/file.txt"))
- [ ] Scanner(new File("/file.txt"))

> **Giải thích:** Classpath resources: getResourceAsStream("/config.properties"). Works trong JAR. FileReader dùng filesystem path, không tìm trong classpath/JAR.
