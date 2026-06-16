# Quiz - JDBC & Connection Pool

## Câu 1

[TYPE: MULTIPLE_CHOICE]

JDBC (Java Database Connectivity) là gì?

- [x] API chuẩn của Java để kết nối và thao tác với relational databases
- [ ] Một database engine
- [ ] Chỉ cho MySQL
- [ ] Thay thế SQL

> **Giải thích:** JDBC: java.sql package. Driver-based API. Hỗ trợ: MySQL, PostgreSQL, Oracle, H2, etc. Abstraction layer giữa Java app và database.

## Câu 2

[TYPE: SELECT_RESULT]

```java
String url = "jdbc:h2:mem:testdb";
Connection conn = DriverManager.getConnection(url);
System.out.println(conn.isClosed());
conn.close();
System.out.println(conn.isClosed());
```

- [x] false và true
- [ ] true và false
- [ ] false và false
- [ ] true và true

> **Giải thích:** DriverManager.getConnection: mở connection. isClosed()=false (open). close() → isClosed()=true. H2 in-memory database.

## Câu 3

[TYPE: FILL_BLANK]

JDBC URL format cơ bản: `jdbc:___:host:port/database`.

- [x] subprotocol (ví dụ: mysql, postgresql, h2)
- [ ] driver
- [ ] connection
- [ ] server

> **Giải thích:** JDBC URL: jdbc:mysql://localhost:3306/mydb, jdbc:postgresql://localhost:5432/mydb, jdbc:h2:mem:testdb. Subprotocol xác định driver.

## Câu 4

[TYPE: SELECT_RESULT]

Cho bảng `users`:
| id | name   | age |
|----|--------|-----|
| 1  | An     | 25  |
| 2  | Bình   | 30  |
| 3  | Cường  | 28  |

```java
String sql = "SELECT name, age FROM users WHERE age > ?";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setInt(1, 27);
ResultSet rs = ps.executeQuery();
while (rs.next()) {
    System.out.println(rs.getString("name") + " " + rs.getInt("age"));
}
```

- [x] Bình 30 và Cường 28
- [ ] An 25
- [ ] Tất cả 3 rows
- [ ] Lỗi SQL

> **Giải thích:** PreparedStatement: parameterized query. ? → 27. WHERE age > 27: Bình(30), Cường(28). getString/getInt by column name.

## Câu 5

[TYPE: TRUE_FALSE]

Mệnh đề: "PreparedStatement ngăn SQL injection bằng cách separate SQL logic từ data."

- [x] Đúng
- [ ] Sai

> **Giải thích:** PreparedStatement: precompiled SQL. Parameters bound separately. No string concatenation → no injection. Statement: vulnerable to SQL injection.

## Câu 6

[TYPE: SELECT_RESULT]

Cho bảng `products`:
| id | name    | price |
|----|---------|-------|
| 1  | Phone   | 999   |
| 2  | Laptop  | 1500  |
| 3  | Tablet  | 500   |

```java
String sql = "INSERT INTO products (name, price) VALUES (?, ?)";
PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
ps.setString(1, "Watch");
ps.setDouble(2, 299.99);
int rows = ps.executeUpdate();
ResultSet keys = ps.getGeneratedKeys();
keys.next();
System.out.println("Inserted: " + rows + ", ID: " + keys.getLong(1));
```

- [x] Inserted: 1, ID: 4
- [ ] Inserted: 0, ID: 0
- [ ] Lỗi SQL
- [ ] Inserted: 1, ID: 1

> **Giải thích:** executeUpdate: return affected rows (1). RETURN_GENERATED_KEYS: auto-increment id. Next id = 4. getGeneratedKeys → ResultSet.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

Statement vs PreparedStatement vs CallableStatement?

- [x] Statement: simple SQL; PreparedStatement: parameterized, precompiled; CallableStatement: stored procedures
- [ ] Tất cả giống nhau
- [ ] Chỉ PreparedStatement
- [ ] CallableStatement cho simple queries

> **Giải thích:** Statement: createStatement(), no params. PreparedStatement: prepareStatement(sql), ? params, safe. CallableStatement: prepareCall("{call proc(?)}"), stored procs.

## Câu 8

[TYPE: SELECT_RESULT]

Cho bảng `orders`:
| id | customer | amount | status    |
|----|----------|--------|-----------|
| 1  | An       | 100    | completed |
| 2  | Bình     | 200    | pending   |
| 3  | An       | 150    | completed |
| 4  | Cường    | 300    | pending   |

```java
String sql = "SELECT customer, SUM(amount) as total FROM orders WHERE status = ? GROUP BY customer";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, "completed");
ResultSet rs = ps.executeQuery();
while (rs.next()) {
    System.out.println(rs.getString("customer") + ": " + rs.getInt("total"));
}
```

- [x] An: 250
- [ ] An: 100, An: 150
- [ ] Bình: 200, Cường: 300
- [ ] An: 250, Bình: 200, Cường: 300

> **Giải thích:** WHERE status='completed': An(100), An(150). GROUP BY customer + SUM: An = 100+150 = 250. Chỉ 1 customer completed.

## Câu 9

[TYPE: FILL_BLANK]

`ResultSet.___()` di chuyển cursor đến row tiếp theo, trả về false khi hết data.

- [x] next
- [ ] advance
- [ ] move
- [ ] hasNext

> **Giải thích:** rs.next(): move to next row. true → có data. false → hết. While loop: while(rs.next()). Iterator pattern.

## Câu 10

[TYPE: SELECT_RESULT]

```java
Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb");
conn.setAutoCommit(false);
try {
    Statement stmt = conn.createStatement();
    stmt.executeUpdate("INSERT INTO accounts VALUES (1, 'An', 1000)");
    stmt.executeUpdate("INSERT INTO accounts VALUES (2, 'Bình', 2000)");
    conn.commit();
    System.out.println("Committed");
} catch (SQLException e) {
    conn.rollback();
    System.out.println("Rolled back");
}
```

Giả sử bảng accounts tồn tại:

- [x] Committed
- [ ] Rolled back
- [ ] Lỗi biên dịch
- [ ] Không in gì

> **Giải thích:** setAutoCommit(false): manual transaction. 2 inserts OK → commit. Nếu lỗi → rollback. Transaction management pattern.

## Câu 11

[TYPE: TRUE_FALSE]

Mệnh đề: "Connection pool tái sử dụng connections thay vì tạo mới mỗi lần, cải thiện performance."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Connection creation: expensive (TCP, auth, etc). Pool: pre-create N connections, borrow/return. HikariCP, Apache DBCP, C3P0. Spring Boot default: HikariCP.

## Câu 12

[TYPE: SELECT_RESULT]

Cho bảng `employees`:
| id | name   | dept | salary |
|----|--------|------|--------|
| 1  | An     | IT   | 1000   |
| 2  | Bình   | HR   | 1200   |
| 3  | Cường  | IT   | 1500   |
| 4  | Dung   | HR   | 900    |

```java
String sql = "UPDATE employees SET salary = salary * 1.1 WHERE dept = ?";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, "IT");
int updated = ps.executeUpdate();
System.out.println("Updated: " + updated);
```

- [x] Updated: 2
- [ ] Updated: 4
- [ ] Updated: 1
- [ ] Updated: 0

> **Giải thích:** WHERE dept='IT': An, Cường. 2 rows updated (salary * 1.1). executeUpdate returns affected row count.

## Câu 13

[TYPE: SELECT_RESULT]

```java
HikariConfig config = new HikariConfig();
config.setJdbcUrl("jdbc:h2:mem:testdb");
config.setMaximumPoolSize(10);
config.setMinimumIdle(5);
config.setConnectionTimeout(30000);
HikariDataSource ds = new HikariDataSource(config);
try (Connection conn = ds.getConnection()) {
    System.out.println(!conn.isClosed());
}
ds.close();
```

- [x] true
- [ ] false
- [ ] Lỗi
- [ ] null

> **Giải thích:** HikariCP config: max 10 connections, min 5 idle, 30s timeout. getConnection: borrow from pool. try-with-resources: return to pool (not actually close).

## Câu 14

[TYPE: MULTIPLE_CHOICE]

HikariCP advantages so với khác connection pools?

- [x] Fastest, lightweight, zero-overhead, bytecode-level optimizations
- [ ] Chỉ cho MySQL
- [ ] Không có advantages
- [ ] Chỉ cho Spring Boot

> **Giải thích:** HikariCP: fastest Java connection pool. ConcurrentBag, bytecode engineering, micro-optimizations. Spring Boot 2+ default. 130KB JAR.

## Câu 15

[TYPE: SELECT_RESULT]

Cho bảng `students`:
| id | name   | score |
|----|--------|-------|
| 1  | An     | 85    |
| 2  | Bình   | 92    |
| 3  | Cường  | 78    |
| 4  | Dung   | 92    |

```java
String sql = "SELECT name FROM students WHERE score = (SELECT MAX(score) FROM students)";
PreparedStatement ps = conn.prepareStatement(sql);
ResultSet rs = ps.executeQuery();
List<String> topStudents = new ArrayList<>();
while (rs.next()) topStudents.add(rs.getString("name"));
System.out.println(topStudents);
```

- [x] [Bình, Dung]
- [ ] [Bình]
- [ ] [Dung]
- [ ] [An, Bình, Cường, Dung]

> **Giải thích:** MAX(score) = 92. WHERE score=92: Bình, Dung. Subquery for max value. 2 students with top score.

## Câu 16

[TYPE: FILL_BLANK]

`DataSource` interface thay thế `DriverManager` trong production vì hỗ trợ `___` và connection pooling.

- [x] connection pool (JNDI lookup)
- [ ] faster queries
- [ ] encryption
- [ ] compression

> **Giải thích:** DataSource: factory for connections. Supports pooling, distributed transactions. JNDI: naming service for container-managed DataSources. Production standard.

## Câu 17

[TYPE: SELECT_RESULT]

Cho bảng `inventory`:
| id | product | quantity | warehouse |
|----|---------|----------|-----------|
| 1  | Phone   | 50       | HCM       |
| 2  | Laptop  | 30       | HN        |
| 3  | Phone   | 20       | HN        |
| 4  | Tablet  | 40       | HCM       |

```java
String sql = "SELECT product, SUM(quantity) as total FROM inventory GROUP BY product HAVING SUM(quantity) > 40";
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(sql);
while (rs.next()) {
    System.out.println(rs.getString(1) + ": " + rs.getInt(2));
}
```

- [x] Phone: 70 và Tablet: 40 (chỉ Phone vì 70 > 40, Tablet = 40 không > 40)
- [ ] Phone: 70
- [ ] Phone: 70, Laptop: 30, Tablet: 40
- [ ] Tất cả products

> **Giải thích:** GROUP BY product: Phone=50+20=70, Laptop=30, Tablet=40. HAVING SUM > 40: chỉ Phone(70). Tablet=40 không thỏa >40.

## Câu 18

[TYPE: SELECT_RESULT]

```java
String sql = "SELECT * FROM users WHERE name LIKE ?";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, "%an%");
ResultSet rs = ps.executeQuery();
```

Cho bảng users: An, Bình, Cường, Hoàng, Khanh

Kết quả trả về:

- [x] Hoàng, Khanh (chứa "an" case-insensitive tùy DB)
- [ ] An
- [ ] Tất cả
- [ ] Không row nào

> **Giải thích:** LIKE '%an%': chứa "an". Hoàng (oàng chứa an? Không). Khanh (khanh chứa "an"? anh có "an"). Tùy DB case sensitivity. H2 default case-insensitive.

## Câu 19

[TYPE: MULTIPLE_CHOICE]

ResultSet types?

- [x] TYPE_FORWARD_ONLY (default), TYPE_SCROLL_INSENSITIVE, TYPE_SCROLL_SENSITIVE
- [ ] Chỉ forward
- [ ] Chỉ scrollable
- [ ] Không có types

> **Giải thích:** FORWARD_ONLY: chỉ next(). SCROLL_INSENSITIVE: scroll, không reflect changes. SCROLL_SENSITIVE: scroll, reflect changes. prepareStatement(sql, type, concurrency).

## Câu 20

[TYPE: TRUE_FALSE]

Mệnh đề: "Batch processing (addBatch/executeBatch) gửi nhiều SQL statements cùng lúc, giảm network round trips."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Batch: addBatch() accumulate. executeBatch() send all at once. Ví dụ: insert 1000 rows in 1 batch vs 1000 individual inserts. Significant performance improvement.

## Câu 21

[TYPE: SELECT_RESULT]

```java
String sql = "INSERT INTO logs (message) VALUES (?)";
PreparedStatement ps = conn.prepareStatement(sql);
for (int i = 0; i < 3; i++) {
    ps.setString(1, "Log " + i);
    ps.addBatch();
}
int[] results = ps.executeBatch();
System.out.println(results.length);
System.out.println(Arrays.stream(results).sum());
```

- [x] 3 và 3
- [ ] 1 và 3
- [ ] 3 và 0
- [ ] Lỗi SQL

> **Giải thích:** 3 statements in batch. executeBatch: int[] with affected rows per statement. [1,1,1]. length=3. sum=3. Batch insert efficient.

## Câu 22

[TYPE: SELECT_RESULT]

Cho bảng `accounts`:
| id | owner | balance |
|----|-------|---------|
| 1  | An    | 1000    |
| 2  | Bình  | 500     |

```java
conn.setAutoCommit(false);
try {
    ps1.setDouble(1, 200); ps1.setInt(2, 1); // debit An
    ps1.executeUpdate(); // UPDATE accounts SET balance = balance - ? WHERE id = ?

    // Simulate error
    if (true) throw new SQLException("Transfer failed");

    ps2.setDouble(1, 200); ps2.setInt(2, 2); // credit Bình
    ps2.executeUpdate(); // UPDATE accounts SET balance = balance + ? WHERE id = ?

    conn.commit();
} catch (SQLException e) {
    conn.rollback();
    System.out.println("Transaction rolled back");
}
```

An's balance sau rollback:

- [x] 1000 (unchanged, rolled back)
- [ ] 800
- [ ] 1200
- [ ] 0

> **Giải thích:** Debit An → error → rollback. An's balance restored to 1000. Bình's balance unchanged (500). ACID: Atomicity ensures all-or-nothing.

## Câu 23

[TYPE: FILL_BLANK]

Connection pool property `maximumPoolSize` giới hạn số `___` connections tối đa.

- [x] active (đang sử dụng)
- [ ] idle
- [ ] total
- [ ] pending

> **Giải thích:** maximumPoolSize: max connections (active + idle). HikariCP default: 10. Quá ít → contention. Quá nhiều → resource waste. Formula: connections = (core_count * 2) + disk_spindles.

## Câu 24

[TYPE: SELECT_RESULT]

Cho bảng `products` (id, name, category, price):
| id | name    | category    | price |
|----|---------|-------------|-------|
| 1  | iPhone  | Electronics | 999   |
| 2  | Book    | Education   | 20    |
| 3  | Laptop  | Electronics | 1500  |
| 4  | Pen     | Education   | 5     |
| 5  | Tablet  | Electronics | 500   |

```java
String sql = "SELECT category, COUNT(*) as cnt, AVG(price) as avg_price FROM products GROUP BY category ORDER BY avg_price DESC";
ResultSet rs = stmt.executeQuery(sql);
while (rs.next()) {
    System.out.printf("%s: count=%d, avg=%.0f%n",
        rs.getString("category"), rs.getInt("cnt"), rs.getDouble("avg_price"));
}
```

- [x] Electronics: count=3, avg=1000 rồi Education: count=2, avg=13
- [ ] Education trước Electronics
- [ ] Chỉ Electronics
- [ ] Lỗi SQL

> **Giải thích:** Electronics: (999+1500+500)/3≈999.7. Education: (20+5)/2=12.5. ORDER BY avg DESC: Electronics first. COUNT: 3, 2.

## Câu 25

[TYPE: MULTIPLE_CHOICE]

Transaction isolation levels?

- [x] READ_UNCOMMITTED, READ_COMMITTED, REPEATABLE_READ, SERIALIZABLE
- [ ] Chỉ READ_COMMITTED
- [ ] LOW, MEDIUM, HIGH
- [ ] Không có levels

> **Giải thích:** READ_UNCOMMITTED: dirty reads. READ_COMMITTED: no dirty reads. REPEATABLE_READ: no non-repeatable reads. SERIALIZABLE: no phantom reads. Higher = safer but slower.

## Câu 26

[TYPE: SELECT_RESULT]

```java
DatabaseMetaData meta = conn.getMetaData();
System.out.println(meta.getDatabaseProductName());
System.out.println(meta.getDriverName());
ResultSet tables = meta.getTables(null, null, "%", new String[]{"TABLE"});
int tableCount = 0;
while (tables.next()) tableCount++;
System.out.println("Tables: " + tableCount);
```

Với H2 database có 2 tables:

- [x] H2, H2 JDBC Driver, Tables: 2
- [ ] null, null, Tables: 0
- [ ] Lỗi
- [ ] MySQL, MySQL Connector, Tables: 2

> **Giải thích:** DatabaseMetaData: database info. getProductName, getDriverName. getTables: list tables. H2 in-memory database.

## Câu 27

[TYPE: SELECT_RESULT]

Cho bảng `employees` (id, name, manager_id):
| id | name   | manager_id |
|----|--------|------------|
| 1  | CEO    | null       |
| 2  | VP     | 1          |
| 3  | Dev    | 2          |
| 4  | QA     | 2          |

```java
String sql = "SELECT e.name as emp, m.name as mgr FROM employees e LEFT JOIN employees m ON e.manager_id = m.id";
ResultSet rs = stmt.executeQuery(sql);
while (rs.next()) {
    String mgr = rs.getString("mgr");
    System.out.println(rs.getString("emp") + " -> " + (rs.wasNull() ? "none" : mgr));
}
```

CEO's output:

- [x] CEO -> none
- [ ] CEO -> CEO
- [ ] CEO -> null
- [ ] Lỗi SQL

> **Giải thích:** LEFT JOIN: CEO has null manager_id → m.name = NULL. rs.wasNull(): check if last read was SQL NULL. "none" for NULL.

## Câu 28

[TYPE: TRUE_FALSE]

Mệnh đề: "try-with-resources tự động close Connection, Statement, ResultSet."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Connection, Statement, ResultSet implement AutoCloseable. try(conn; stmt; rs) → auto close in reverse order. Prevents resource leaks. Best practice.

## Câu 29

[TYPE: SELECT_RESULT]

```java
try (Connection conn = dataSource.getConnection();
     PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM users");
     ResultSet rs = ps.executeQuery()) {
    rs.next();
    System.out.println(rs.getInt(1));
}
// conn, ps, rs are all closed here
```

Cho bảng users có 5 rows:

- [x] 5
- [ ] 0
- [ ] Lỗi
- [ ] null

> **Giải thích:** COUNT(*) → 5. getInt(1): column index 1. try-with-resources: auto-close all 3 resources. Clean resource management.

## Câu 30

[TYPE: FILL_BLANK]

`conn.___()` thiết lập savepoint cho partial rollback trong transaction.

- [x] setSavepoint
- [ ] save
- [ ] checkpoint
- [ ] mark

> **Giải thích:** Savepoint: named point in transaction. conn.setSavepoint("sp1"). conn.rollback(savepoint): rollback to savepoint, not beginning. Partial transaction recovery.

## Câu 31

[TYPE: SELECT_RESULT]

```java
conn.setAutoCommit(false);
Statement stmt = conn.createStatement();
stmt.executeUpdate("INSERT INTO data VALUES (1, 'A')");
Savepoint sp = conn.setSavepoint("sp1");
stmt.executeUpdate("INSERT INTO data VALUES (2, 'B')");
conn.rollback(sp); // rollback to savepoint
stmt.executeUpdate("INSERT INTO data VALUES (3, 'C')");
conn.commit();
// Query all
ResultSet rs = stmt.executeQuery("SELECT * FROM data");
int count = 0;
while (rs.next()) count++;
System.out.println(count);
```

- [x] 2
- [ ] 3
- [ ] 1
- [ ] 0

> **Giải thích:** Insert (1,'A') → savepoint → Insert (2,'B') → rollback to savepoint (undo B) → Insert (3,'C') → commit. Data: A, C. count=2.

## Câu 32

[TYPE: SELECT_RESULT]

Cho bảng `sales`:
| id | product | amount | sale_date  |
|----|---------|--------|------------|
| 1  | Phone   | 500    | 2024-01-15 |
| 2  | Laptop  | 1200   | 2024-01-20 |
| 3  | Phone   | 600    | 2024-02-10 |
| 4  | Tablet  | 300    | 2024-02-15 |

```java
String sql = "SELECT product, SUM(amount) FROM sales WHERE sale_date BETWEEN ? AND ? GROUP BY product";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setDate(1, java.sql.Date.valueOf("2024-01-01"));
ps.setDate(2, java.sql.Date.valueOf("2024-01-31"));
ResultSet rs = ps.executeQuery();
```

Kết quả:

- [x] Phone: 500, Laptop: 1200
- [ ] Phone: 1100, Laptop: 1200
- [ ] Tất cả 4 rows
- [ ] Lỗi

> **Giải thích:** BETWEEN Jan 1-31: rows 1 (Phone 500), 2 (Laptop 1200). Feb rows excluded. GROUP BY: Phone=500, Laptop=1200.

## Câu 33

[TYPE: MULTIPLE_CHOICE]

Connection pool health checks?

- [x] Validation query, leak detection, idle timeout, max lifetime
- [ ] Chỉ ping
- [ ] Không cần
- [ ] Chỉ timeout

> **Giải thích:** HikariCP: connectionTestQuery, leakDetectionThreshold, idleTimeout, maxLifetime. Đảm bảo connections valid. Remove stale connections. Prevent leaks.

## Câu 34

[TYPE: SELECT_RESULT]

```java
HikariConfig config = new HikariConfig();
config.setJdbcUrl("jdbc:h2:mem:testdb");
config.setMaximumPoolSize(5);
config.setConnectionTimeout(1000); // 1 second
HikariDataSource ds = new HikariDataSource(config);

List<Connection> connections = new ArrayList<>();
for (int i = 0; i < 5; i++) {
    connections.add(ds.getConnection()); // borrow all 5
}
try {
    ds.getConnection(); // 6th connection, pool exhausted
} catch (SQLException e) {
    System.out.println("Pool exhausted");
}
connections.forEach(c -> { try { c.close(); } catch (Exception e) {} });
ds.close();
```

- [x] Pool exhausted
- [ ] Connection created
- [ ] Lỗi biên dịch
- [ ] Deadlock

> **Giải thích:** Pool size 5, all borrowed. 6th request → timeout (1s) → SQLException. Pool exhaustion. Close returns connections to pool.

## Câu 35

[TYPE: FILL_BLANK]

`RowMapper` (Spring JDBC) chuyển mỗi row của ResultSet thành `___`.

- [x] Java object
- [ ] String
- [ ] Map
- [ ] JSON

> **Giải thích:** RowMapper<T>: (ResultSet rs, int rowNum) → T. Map each row to domain object. JdbcTemplate.query(sql, rowMapper). Clean separation of mapping logic.

## Câu 36

[TYPE: SELECT_RESULT]

Cho bảng `users`:
| id | name  | email           |
|----|-------|-----------------|
| 1  | An    | an@test.com     |
| 2  | Bình  | binh@test.com   |

```java
// Spring JdbcTemplate
String sql = "SELECT * FROM users WHERE id = ?";
User user = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
    new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")),
    1);
System.out.println(user.getName());
```

- [x] An
- [ ] Bình
- [ ] null
- [ ] Lỗi

> **Giải thích:** queryForObject: single row. id=1 → An. RowMapper lambda maps ResultSet → User. getName() → "An".

## Câu 37

[TYPE: SELECT_RESULT]

Cho bảng `log_entries`:
| id | level | message         | timestamp           |
|----|-------|-----------------|---------------------|
| 1  | ERROR | NullPointer     | 2024-01-15 10:00:00 |
| 2  | WARN  | Slow query      | 2024-01-15 10:05:00 |
| 3  | ERROR | OutOfMemory     | 2024-01-15 10:10:00 |
| 4  | INFO  | Server started  | 2024-01-15 10:15:00 |

```java
String sql = "SELECT level, COUNT(*) as cnt FROM log_entries GROUP BY level ORDER BY cnt DESC";
ResultSet rs = stmt.executeQuery(sql);
while (rs.next()) {
    System.out.println(rs.getString("level") + ": " + rs.getInt("cnt"));
}
```

- [x] ERROR: 2, WARN: 1, INFO: 1
- [ ] INFO: 1, WARN: 1, ERROR: 2
- [ ] ERROR: 2
- [ ] Lỗi

> **Giải thích:** GROUP BY level: ERROR=2, WARN=1, INFO=1. ORDER BY cnt DESC: ERROR(2) first. Log analysis via SQL.

## Câu 38

[TYPE: TRUE_FALSE]

Mệnh đề: "DriverManager.getConnection() tạo physical connection mỗi lần gọi, tốn tài nguyên."

- [x] Đúng
- [ ] Sai

> **Giải thích:** DriverManager: no pooling. Mỗi call → new TCP connection, authentication, etc. Expensive. Production: dùng DataSource + connection pool.

## Câu 39

[TYPE: SELECT_RESULT]

```java
String sql = "SELECT * FROM users";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setFetchSize(100);
ResultSet rs = ps.executeQuery();
System.out.println(rs.getFetchSize());
```

- [x] 100
- [ ] 0
- [ ] 1
- [ ] Lỗi

> **Giải thích:** setFetchSize: hint to JDBC driver — fetch N rows at a time. Reduces memory for large result sets. getFetchSize returns configured value.

## Câu 40

[TYPE: SELECT_RESULT]

Cho bảng `categories`:
| id | name        | parent_id |
|----|-------------|-----------|
| 1  | Electronics | null      |
| 2  | Phones      | 1         |
| 3  | Laptops     | 1         |
| 4  | iPhone      | 2         |

```java
String sql = "SELECT c.name, p.name as parent_name FROM categories c LEFT JOIN categories p ON c.parent_id = p.id WHERE c.parent_id IS NOT NULL";
ResultSet rs = stmt.executeQuery(sql);
int count = 0;
while (rs.next()) {
    count++;
    System.out.println(rs.getString("name") + " -> " + rs.getString("parent_name"));
}
System.out.println("Count: " + count);
```

- [x] Phones -> Electronics, Laptops -> Electronics, iPhone -> Phones, Count: 3
- [ ] Count: 4
- [ ] Count: 1
- [ ] Lỗi

> **Giải thích:** WHERE parent_id IS NOT NULL: exclude Electronics (root). 3 rows: Phones→Electronics, Laptops→Electronics, iPhone→Phones.

## Câu 41

[TYPE: FILL_BLANK]

HikariCP property `___` phát hiện connection không được trả về pool (connection leak).

- [x] leakDetectionThreshold
- [ ] leakCheck
- [ ] connectionLeak
- [ ] detectLeak

> **Giải thích:** leakDetectionThreshold: milliseconds. Nếu connection borrowed > threshold → log warning with stack trace. Default: 0 (disabled). Recommended: 60000 (60s).

## Câu 42

[TYPE: SELECT_RESULT]

Cho bảng `tasks`:
| id | title      | status   | priority |
|----|------------|----------|----------|
| 1  | Bug fix    | OPEN     | HIGH     |
| 2  | Feature    | CLOSED   | LOW      |
| 3  | Refactor   | OPEN     | MEDIUM   |
| 4  | Deploy     | OPEN     | HIGH     |
| 5  | Test       | CLOSED   | HIGH     |

```java
String sql = "SELECT COUNT(*) FROM tasks WHERE status = ? AND priority = ?";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, "OPEN");
ps.setString(2, "HIGH");
ResultSet rs = ps.executeQuery();
rs.next();
System.out.println(rs.getInt(1));
```

- [x] 2
- [ ] 3
- [ ] 1
- [ ] 5

> **Giải thích:** status=OPEN AND priority=HIGH: Bug fix, Deploy. COUNT=2. Multi-condition filter.

## Câu 43

[TYPE: SELECT_RESULT]

```java
// Connection pool monitoring
HikariDataSource ds = new HikariDataSource(config);
HikariPoolMXBean poolBean = ds.getHikariPoolMXBean();
System.out.println("Active: " + poolBean.getActiveConnections());
System.out.println("Idle: " + poolBean.getIdleConnections());
System.out.println("Total: " + poolBean.getTotalConnections());
```

Ban đầu pool chưa ai dùng, min idle = 5:

- [x] Active: 0, Idle: 5, Total: 5
- [ ] Active: 5, Idle: 0, Total: 5
- [ ] Active: 0, Idle: 0, Total: 0
- [ ] Lỗi

> **Giải thích:** No active connections. minimumIdle=5 → 5 idle connections ready. Total=5. MXBean for monitoring pool health.

## Câu 44

[TYPE: MULTIPLE_CHOICE]

N+1 query problem là gì?

- [x] 1 query lấy N items, rồi N queries lấy related data cho mỗi item → N+1 total
- [ ] Query chạy N lần
- [ ] N connections needed
- [ ] Chỉ xảy ra với ORM

> **Giải thích:** N+1: SELECT orders (1) + SELECT items WHERE order_id=? (N). Fix: JOIN, subquery, batch fetch. ORM (Hibernate) thường gây. Giám sát query count.

## Câu 45

[TYPE: SELECT_RESULT]

Cho 2 bảng:
`orders`: | id | customer |
`order_items`: | id | order_id | product | qty |

```java
// N+1 problem:
ResultSet orders = stmt.executeQuery("SELECT * FROM orders"); // 1 query
while (orders.next()) {
    int orderId = orders.getInt("id");
    PreparedStatement ps = conn.prepareStatement("SELECT * FROM order_items WHERE order_id = ?"); // N queries
    ps.setInt(1, orderId);
    // process items...
}

// Fix: JOIN
String sql = "SELECT o.id, o.customer, oi.product, oi.qty FROM orders o JOIN order_items oi ON o.id = oi.order_id";
```

Fix giảm từ N+1 queries xuống:

- [x] 1 query
- [ ] N queries
- [ ] 2 queries
- [ ] Không thể fix

> **Giải thích:** JOIN: 1 query lấy tất cả data. N+1 → 1. Significant performance improvement. Always prefer JOINs over nested queries.

## Câu 46

[TYPE: TRUE_FALSE]

Mệnh đề: "ResultSet column index bắt đầu từ 1, không phải 0."

- [x] Đúng
- [ ] Sai

> **Giải thích:** JDBC: 1-indexed. rs.getString(1) = first column. rs.getInt(2) = second column. Khác Java arrays (0-indexed). Common mistake source.

## Câu 47

[TYPE: SELECT_RESULT]

Cho bảng `config`:
| key       | value     |
|-----------|-----------|
| db.host   | localhost |
| db.port   | 5432      |
| db.name   | mydb      |

```java
String sql = "SELECT value FROM config WHERE key = ?";
PreparedStatement ps = conn.prepareStatement(sql);

ps.setString(1, "db.host");
ResultSet rs1 = ps.executeQuery();
rs1.next();
String host = rs1.getString(1);

ps.setString(1, "db.port");
ResultSet rs2 = ps.executeQuery();
rs2.next();
int port = Integer.parseInt(rs2.getString(1));

System.out.println(host + ":" + port);
```

- [x] localhost:5432
- [ ] mydb:5432
- [ ] localhost:0
- [ ] Lỗi

> **Giải thích:** PreparedStatement reuse: change parameter, re-execute. host="localhost", port=5432. Efficient prepared statement reuse.

## Câu 48

[TYPE: SELECT_RESULT]

```java
// SQL injection vulnerability
String username = "admin'; DROP TABLE users; --";
String sql = "SELECT * FROM users WHERE name = '" + username + "'";
System.out.println(sql);
```

Output SQL:

- [x] SELECT * FROM users WHERE name = 'admin'; DROP TABLE users; --'
- [ ] SELECT * FROM users WHERE name = 'admin'
- [ ] Lỗi biên dịch
- [ ] Safe query

> **Giải thích:** String concatenation → SQL injection! Attacker terminates query, drops table. PreparedStatement prevents this: parameters escaped automatically.

## Câu 49

[TYPE: FILL_BLANK]

`Connection.setTransactionIsolation(Connection.TRANSACTION___)` sets the strictest isolation level.

- [x] SERIALIZABLE
- [ ] REPEATABLE_READ
- [ ] READ_COMMITTED
- [ ] READ_UNCOMMITTED

> **Giải thích:** SERIALIZABLE: highest isolation. No dirty, non-repeatable, phantom reads. Slowest. Trade-off: safety vs performance. Default: database-specific (usually READ_COMMITTED).

## Câu 50

[TYPE: SELECT_RESULT]

Cho bảng `audit_log`:
| id | action | user_id | created_at          |
|----|--------|---------|---------------------|
| 1  | LOGIN  | 1       | 2024-01-15 08:00:00 |
| 2  | VIEW   | 1       | 2024-01-15 08:05:00 |
| 3  | LOGIN  | 2       | 2024-01-15 09:00:00 |
| 4  | EDIT   | 1       | 2024-01-15 08:10:00 |
| 5  | LOGIN  | 1       | 2024-01-16 08:00:00 |

```java
String sql = "SELECT user_id, COUNT(DISTINCT DATE(created_at)) as login_days FROM audit_log WHERE action = 'LOGIN' GROUP BY user_id";
ResultSet rs = stmt.executeQuery(sql);
while (rs.next()) {
    System.out.println("User " + rs.getInt("user_id") + ": " + rs.getInt("login_days") + " days");
}
```

- [x] User 1: 2 days, User 2: 1 days
- [ ] User 1: 2 days
- [ ] User 1: 3 days, User 2: 1 days
- [ ] Lỗi

> **Giải thích:** User 1 LOGIN: Jan 15, Jan 16 → 2 distinct dates. User 2 LOGIN: Jan 15 → 1 date. COUNT DISTINCT DATE for login days.

## Câu 51

[TYPE: MULTIPLE_CHOICE]

Spring JdbcTemplate advantages?

- [x] Less boilerplate, auto resource management, exception translation, RowMapper support
- [ ] Chỉ simple queries
- [ ] Thay thế JDBC hoàn toàn
- [ ] Không cần connection pool

> **Giải thích:** JdbcTemplate: wraps JDBC. Auto close resources. SQLException → DataAccessException hierarchy. query(), update(), batchUpdate(). NamedParameterJdbcTemplate cho named params.

## Câu 52

[TYPE: SELECT_RESULT]

```java
// Spring JdbcTemplate
int count = jdbcTemplate.queryForObject(
    "SELECT COUNT(*) FROM users WHERE age > ?",
    Integer.class, 25);
System.out.println(count);
```

Cho users: An(25), Bình(30), Cường(28), Dung(22):

- [x] 2
- [ ] 4
- [ ] 3
- [ ] 1

> **Giải thích:** age > 25: Bình(30), Cường(28). Count=2. queryForObject: single value. Integer.class: return type.

## Câu 53

[TYPE: SELECT_RESULT]

Cho bảng `products`:
| id | name   | price | stock |
|----|--------|-------|-------|
| 1  | A      | 100   | 5     |
| 2  | B      | 200   | 0     |
| 3  | C      | 150   | 3     |
| 4  | D      | 50    | 10    |

```java
String sql = "SELECT name, price * stock as inventory_value FROM products WHERE stock > 0 ORDER BY inventory_value DESC";
ResultSet rs = stmt.executeQuery(sql);
while (rs.next()) {
    System.out.println(rs.getString("name") + ": " + rs.getInt("inventory_value"));
}
```

- [x] A: 500, D: 500, C: 450
- [ ] D: 500, A: 500, C: 450
- [ ] B: 0, C: 450, A: 500, D: 500
- [ ] Lỗi

> **Giải thích:** stock > 0: A(500), C(450), D(500). B excluded (stock=0). ORDER BY DESC: A=D=500 first, C=450. A,D order depends on DB.

## Câu 54

[TYPE: TRUE_FALSE]

Mệnh đề: "connection.close() trong connection pool context trả connection về pool, KHÔNG thực sự đóng physical connection."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Pool wraps connection with proxy. close() = return to pool. Pool manages physical lifecycle. Application code unchanged (still calls close). Transparent pooling.

## Câu 55

[TYPE: SELECT_RESULT]

```java
// NamedParameterJdbcTemplate (Spring)
String sql = "SELECT * FROM users WHERE name = :name AND age > :minAge";
MapSqlParameterSource params = new MapSqlParameterSource();
params.addValue("name", "An");
params.addValue("minAge", 20);
List<Map<String, Object>> result = namedTemplate.queryForList(sql, params);
System.out.println(result.size());
```

Cho users: An(25), An(18), Bình(30):

- [x] 1
- [ ] 2
- [ ] 3
- [ ] 0

> **Giải thích:** name="An" AND age>20: An(25) ✓. An(18) age≤20 ✗. Named parameters: :name, :minAge. More readable than ? placeholders.

## Câu 56

[TYPE: FILL_BLANK]

`ResultSetMetaData.___()` trả về số columns trong ResultSet.

- [x] getColumnCount
- [ ] columnCount
- [ ] size
- [ ] count

> **Giải thích:** ResultSetMetaData: metadata about ResultSet. getColumnCount(), getColumnName(i), getColumnType(i), getColumnTypeName(i). Dynamic result processing.

## Câu 57

[TYPE: SELECT_RESULT]

```java
ResultSet rs = stmt.executeQuery("SELECT id, name, age FROM users");
ResultSetMetaData meta = rs.getMetaData();
int cols = meta.getColumnCount();
System.out.println(cols);
for (int i = 1; i <= cols; i++) {
    System.out.print(meta.getColumnName(i) + " ");
}
```

- [x] 3 và ID NAME AGE
- [ ] 3 và id name age
- [ ] 0
- [ ] Lỗi

> **Giải thích:** 3 columns: id, name, age. getColumnName: column names (case depends on DB). Dynamic column discovery.

## Câu 58

[TYPE: SELECT_RESULT]

Cho bảng `scores`:
| student_id | subject | score |
|------------|---------|-------|
| 1          | Math    | 85    |
| 1          | English | 90    |
| 2          | Math    | 70    |
| 2          | English | 80    |
| 3          | Math    | 95    |

```java
String sql = "SELECT student_id, AVG(score) as avg_score FROM scores GROUP BY student_id HAVING AVG(score) >= 80 ORDER BY avg_score DESC";
ResultSet rs = stmt.executeQuery(sql);
while (rs.next()) {
    System.out.printf("Student %d: %.1f%n", rs.getInt("student_id"), rs.getDouble("avg_score"));
}
```

- [x] Student 3: 95.0, Student 1: 87.5
- [ ] Student 1: 87.5, Student 3: 95.0
- [ ] Student 2: 75.0
- [ ] Tất cả 3 students

> **Giải thích:** AVG: S1=(85+90)/2=87.5, S2=(70+80)/2=75.0, S3=95/1=95.0. HAVING ≥80: S1, S3. DESC: S3(95)→S1(87.5).

## Câu 59

[TYPE: MULTIPLE_CHOICE]

BLOB vs CLOB trong JDBC?

- [x] BLOB: Binary Large Object (images, files); CLOB: Character Large Object (large text)
- [ ] Giống nhau
- [ ] BLOB cho text
- [ ] CLOB cho binary

> **Giải thích:** BLOB: getBlob(), getBinaryStream(). CLOB: getClob(), getCharacterStream(). Large data types. PreparedStatement: setBlob(), setClob().

## Câu 60

[TYPE: SELECT_RESULT]

```java
String sql = "INSERT INTO documents (title, content) VALUES (?, ?)";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, "Report");
Clob clob = conn.createClob();
clob.setString(1, "Very long text content...");
ps.setClob(2, clob);
int rows = ps.executeUpdate();
System.out.println("Inserted: " + rows);
```

- [x] Inserted: 1
- [ ] Inserted: 0
- [ ] Lỗi biên dịch
- [ ] UnsupportedOperationException

> **Giải thích:** CLOB for large text. createClob → setString → setClob → executeUpdate. 1 row inserted. Large text storage.

## Câu 61

[TYPE: TRUE_FALSE]

Mệnh đề: "Spring Boot auto-configures HikariCP khi spring-boot-starter-jdbc dependency có mặt."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Spring Boot 2+: HikariCP default. spring.datasource.url, username, password in application.properties. Auto DataSource bean. No manual pool config needed.

## Câu 62

[TYPE: SELECT_RESULT]

```yaml
# application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=admin
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=30000
```

Pool khi startup:

- [x] 5 idle connections, max sẽ grow đến 20 khi cần
- [ ] 20 connections ngay lập tức
- [ ] 0 connections
- [ ] 1 connection

> **Giải thích:** minimumIdle=5: pre-create 5. maximumPoolSize=20: grow on demand up to 20. connectionTimeout=30s: wait max 30s for connection.

## Câu 63

[TYPE: SELECT_RESULT]

Cho bảng `transactions`:
| id | account_id | type    | amount | created_at |
|----|------------|---------|--------|------------|
| 1  | 1          | CREDIT  | 1000   | 2024-01-01 |
| 2  | 1          | DEBIT   | 200    | 2024-01-05 |
| 3  | 1          | DEBIT   | 300    | 2024-01-10 |
| 4  | 2          | CREDIT  | 500    | 2024-01-01 |

```java
String sql = "SELECT account_id, SUM(CASE WHEN type='CREDIT' THEN amount ELSE -amount END) as balance FROM transactions GROUP BY account_id";
ResultSet rs = stmt.executeQuery(sql);
while (rs.next()) {
    System.out.println("Account " + rs.getInt(1) + ": " + rs.getInt(2));
}
```

- [x] Account 1: 500, Account 2: 500
- [ ] Account 1: 1500, Account 2: 500
- [ ] Account 1: -500, Account 2: 500
- [ ] Lỗi SQL

> **Giải thích:** Account 1: +1000 -200 -300 = 500. Account 2: +500. CASE expression for credit/debit calculation.

## Câu 64

[TYPE: FILL_BLANK]

`PreparedStatement.___()` clear previously set parameters.

- [x] clearParameters
- [ ] reset
- [ ] clear
- [ ] removeAll

> **Giải thích:** clearParameters(): reset all params. Useful khi reusing PreparedStatement with different values. Alternative: just set new values (overwrite).

## Câu 65

[TYPE: SELECT_RESULT]

```java
// Spring JdbcTemplate batch update
String sql = "INSERT INTO users (name, age) VALUES (?, ?)";
List<Object[]> batchArgs = List.of(
    new Object[]{"An", 25},
    new Object[]{"Bình", 30},
    new Object[]{"Cường", 28}
);
int[] results = jdbcTemplate.batchUpdate(sql, batchArgs);
System.out.println(results.length);
System.out.println(Arrays.stream(results).sum());
```

- [x] 3 và 3
- [ ] 1 và 3
- [ ] 3 và 0
- [ ] Lỗi

> **Giải thích:** batchUpdate: batch insert 3 rows. results: [1,1,1]. length=3, sum=3. Spring wraps JDBC batch processing.

## Câu 66

[TYPE: SELECT_RESULT]

Cho bảng `employees` (id, name, dept, salary):
| id | name   | dept | salary |
|----|--------|------|--------|
| 1  | An     | IT   | 1000   |
| 2  | Bình   | IT   | 1500   |
| 3  | Cường  | HR   | 1200   |
| 4  | Dung   | IT   | 1800   |
| 5  | Em     | HR   | 900    |

```java
String sql = "SELECT dept, name, salary, RANK() OVER (PARTITION BY dept ORDER BY salary DESC) as rank FROM employees";
ResultSet rs = stmt.executeQuery(sql);
while (rs.next()) {
    if (rs.getInt("rank") == 1) {
        System.out.println(rs.getString("dept") + ": " + rs.getString("name"));
    }
}
```

- [x] HR: Cường, IT: Dung
- [ ] IT: Dung
- [ ] HR: Em, IT: An
- [ ] Lỗi SQL

> **Giải thích:** Window function RANK: per dept, ordered by salary DESC. HR rank 1: Cường(1200). IT rank 1: Dung(1800). Top earner per department.

## Câu 67

[TYPE: MULTIPLE_CHOICE]

Connection pool sizing formula?

- [x] connections = (core_count × 2) + effective_spindle_count, thường 10-20 cho web apps
- [ ] connections = 100 luôn
- [ ] connections = 1 per user
- [ ] Càng nhiều càng tốt

> **Giải thích:** HikariCP recommendation. CPU cores × 2 + disk spindles. SSD: spindle_count ≈ 0. Quá nhiều connections → context switching, diminishing returns.

## Câu 68

[TYPE: SELECT_RESULT]

```java
// Proper JDBC resource management
public List<String> getNames() throws SQLException {
    List<String> names = new ArrayList<>();
    try (Connection conn = dataSource.getConnection();
         PreparedStatement ps = conn.prepareStatement("SELECT name FROM users");
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            names.add(rs.getString(1));
        }
    } // auto-close rs, ps, conn
    return names;
}
```

Thứ tự close:

- [x] ResultSet → PreparedStatement → Connection (reverse declaration order)
- [ ] Connection → PreparedStatement → ResultSet
- [ ] Random
- [ ] Chỉ Connection

> **Giải thích:** try-with-resources: close in reverse order. rs first (innermost), then ps, then conn. LIFO. Prevents resource leaks.

## Câu 69

[TYPE: TRUE_FALSE]

Mệnh đề: "executeQuery() cho SELECT, executeUpdate() cho INSERT/UPDATE/DELETE, execute() cho cả hai."

- [x] Đúng
- [ ] Sai

> **Giải thích:** executeQuery: returns ResultSet (SELECT). executeUpdate: returns int (affected rows, for DML). execute: returns boolean (true=ResultSet, false=update count). General-purpose.

## Câu 70

[TYPE: SELECT_RESULT]

Cho bảng `users`:
| id | name   | status   | last_login          |
|----|--------|----------|---------------------|
| 1  | An     | ACTIVE   | 2024-01-15 08:00:00 |
| 2  | Bình   | INACTIVE | 2023-06-01 10:00:00 |
| 3  | Cường  | ACTIVE   | 2024-01-14 09:00:00 |
| 4  | Dung   | ACTIVE   | 2024-01-10 07:00:00 |

```java
String sql = "DELETE FROM users WHERE status = ? AND last_login < ?";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, "INACTIVE");
ps.setTimestamp(2, Timestamp.valueOf("2024-01-01 00:00:00"));
int deleted = ps.executeUpdate();
System.out.println("Deleted: " + deleted);
```

- [x] Deleted: 1
- [ ] Deleted: 0
- [ ] Deleted: 4
- [ ] Deleted: 3

> **Giải thích:** status=INACTIVE AND last_login < 2024-01-01: Bình (INACTIVE, 2023-06-01). 1 row deleted.

## Câu 71

[TYPE: SELECT_RESULT]

```java
// Connection pool with retry logic
int maxRetries = 3;
for (int i = 0; i < maxRetries; i++) {
    try (Connection conn = dataSource.getConnection()) {
        // execute query
        System.out.println("Success on attempt " + (i + 1));
        break;
    } catch (SQLException e) {
        if (i == maxRetries - 1) throw e;
        System.out.println("Retry " + (i + 1));
        Thread.sleep(1000);
    }
}
```

Nếu connection OK lần đầu:

- [x] Success on attempt 1
- [ ] Retry 1, Retry 2, Success on attempt 3
- [ ] Retry 1, Success on attempt 2
- [ ] Lỗi

> **Giải thích:** First attempt succeeds → print + break. No retries needed. Retry pattern for transient failures.

## Câu 72

[TYPE: FILL_BLANK]

`conn.setReadOnly(___)` hint cho database/driver optimize cho read-only transactions.

- [x] true
- [ ] false
- [ ] 1
- [ ] "read"

> **Giải thích:** setReadOnly(true): hint, no guarantee. Database may optimize (no write locks). Pool may route to read replica. Reset before returning to pool.

## Câu 73

[TYPE: SELECT_RESULT]

Cho bảng `products`:
| id | name   | price |
|----|--------|-------|
| 1  | A      | 100   |
| 2  | B      | 200   |
| 3  | C      | 150   |

```java
String sql = "UPDATE products SET price = ? WHERE id = ?";
PreparedStatement ps = conn.prepareStatement(sql);

// Batch update
int[][] updates = {{120, 1}, {180, 2}, {160, 3}};
for (int[] u : updates) {
    ps.setInt(1, u[0]);
    ps.setInt(2, u[1]);
    ps.addBatch();
}
int[] results = ps.executeBatch();
System.out.println(Arrays.stream(results).sum());
```

- [x] 3
- [ ] 0
- [ ] 1
- [ ] Lỗi

> **Giải thích:** 3 updates batched. Each updates 1 row → [1,1,1]. Sum=3. Batch update: efficient for multiple similar operations.

## Câu 74

[TYPE: SELECT_RESULT]

Cho bảng `orders` và `customers`:
`customers`: | id | name | tier |
| 1 | An | GOLD |
| 2 | Bình | SILVER |

`orders`: | id | customer_id | total |
| 1 | 1 | 500 |
| 2 | 1 | 300 |
| 3 | 2 | 200 |

```java
String sql = """
    SELECT c.name, c.tier, COALESCE(SUM(o.total), 0) as total_spent
    FROM customers c
    LEFT JOIN orders o ON c.id = o.customer_id
    GROUP BY c.id, c.name, c.tier
    HAVING COALESCE(SUM(o.total), 0) > 400
    """;
ResultSet rs = stmt.executeQuery(sql);
while (rs.next()) {
    System.out.println(rs.getString("name") + ": " + rs.getInt("total_spent"));
}
```

- [x] An: 800
- [ ] An: 800, Bình: 200
- [ ] Bình: 200
- [ ] Lỗi

> **Giải thích:** An: 500+300=800 > 400 ✓. Bình: 200 ≤ 400 ✗. HAVING filters groups. LEFT JOIN includes customers without orders.

## Câu 75

[TYPE: MULTIPLE_CHOICE]

Proper exception handling with JDBC?

- [x] Catch SQLException, log details (SQLState, error code), handle specific states, clean up resources
- [ ] Catch Exception
- [ ] Ignore exceptions
- [ ] Throw RuntimeException

> **Giải thích:** SQLException: getSQLState(), getErrorCode(), getMessage(). SQLState: "23505" (unique violation), "23503" (FK violation). Handle specific errors differently.

## Câu 76

[TYPE: SELECT_RESULT]

```java
try {
    String sql = "INSERT INTO users (id, name) VALUES (1, 'duplicate')";
    stmt.executeUpdate(sql); // id=1 already exists (unique constraint)
} catch (SQLException e) {
    System.out.println("State: " + e.getSQLState());
    System.out.println("Code: " + e.getErrorCode());
    if (e.getSQLState().startsWith("23")) {
        System.out.println("Constraint violation");
    }
}
```

- [x] State: 23505 (hoặc 23xxx), Code: (DB specific), Constraint violation
- [ ] Lỗi biên dịch
- [ ] Không catch được
- [ ] State: null

> **Giải thích:** Unique constraint violation: SQLState starts with "23" (integrity constraint). 23505 = unique violation (PostgreSQL/H2). Error code DB-specific.

## Câu 77

[TYPE: TRUE_FALSE]

Mệnh đề: "HikariCP connectionTestQuery không cần thiết cho JDBC4+ drivers vì chúng support Connection.isValid()."

- [x] Đúng
- [ ] Sai

> **Giải thích:** JDBC4 (Java 6+): isValid(timeout) built-in. Không cần "SELECT 1" test query. HikariCP auto-detects. Reduce overhead. Set connectionTestQuery chỉ cho legacy JDBC3 drivers.

## Câu 78

[TYPE: SELECT_RESULT]

Cho bảng `events`:
| id | type   | data          | created_at          |
|----|--------|---------------|---------------------|
| 1  | CLICK  | {"page":"home"} | 2024-01-15 10:00:00 |
| 2  | VIEW   | {"page":"about"}| 2024-01-15 10:05:00 |
| 3  | CLICK  | {"page":"home"} | 2024-01-15 10:10:00 |
| 4  | CLICK  | {"page":"shop"} | 2024-01-15 10:15:00 |

```java
String sql = "SELECT type, COUNT(*) as cnt FROM events GROUP BY type";
ResultSet rs = stmt.executeQuery(sql);
Map<String, Integer> counts = new HashMap<>();
while (rs.next()) {
    counts.put(rs.getString("type"), rs.getInt("cnt"));
}
System.out.println(counts);
```

- [x] {CLICK=3, VIEW=1}
- [ ] {CLICK=1, VIEW=1}
- [ ] {CLICK=3}
- [ ] Lỗi

> **Giải thích:** GROUP BY type: CLICK=3, VIEW=1. ResultSet → Map. Event analytics via SQL aggregation.

## Câu 79

[TYPE: FILL_BLANK]

`spring.datasource.hikari.___` property sets maximum connection lifetime trước khi bị retired.

- [x] max-lifetime
- [ ] maxAge
- [ ] connection-max-age
- [ ] lifetime

> **Giải thích:** max-lifetime: milliseconds. Default: 1800000 (30 min). Nên < database timeout. Retire stale connections. Prevent issues from firewall/DB timeout.

## Câu 80

[TYPE: SELECT_RESULT]

Cho bảng `products` (id, name, category, price, created_at):
| id | name   | category    | price | created_at |
|----|--------|-------------|-------|------------|
| 1  | A      | Electronics | 100   | 2024-01-01 |
| 2  | B      | Electronics | 200   | 2024-01-15 |
| 3  | C      | Food        | 50    | 2024-01-10 |

```java
String sql = "SELECT * FROM products WHERE category = ? AND price BETWEEN ? AND ? ORDER BY created_at DESC";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, "Electronics");
ps.setDouble(2, 50);
ps.setDouble(3, 150);
ResultSet rs = ps.executeQuery();
rs.next();
System.out.println(rs.getString("name"));
```

- [x] A
- [ ] B
- [ ] C
- [ ] Lỗi

> **Giải thích:** Electronics AND price 50-150: A(100). B(200) too expensive. ORDER BY created_at DESC → A is only result. first row = A.

## Câu 81

[TYPE: SELECT_RESULT]

```java
// Spring @Transactional
@Transactional
public void transfer(int fromId, int toId, double amount) {
    jdbcTemplate.update("UPDATE accounts SET balance = balance - ? WHERE id = ?", amount, fromId);
    if (amount > 10000) throw new RuntimeException("Limit exceeded");
    jdbcTemplate.update("UPDATE accounts SET balance = balance + ? WHERE id = ?", amount, toId);
}

// Call with amount = 15000
try {
    transfer(1, 2, 15000);
} catch (RuntimeException e) {
    System.out.println("Error: " + e.getMessage());
}
```

Account 1 balance after error:

- [x] Unchanged (rolled back by @Transactional)
- [ ] Decreased by 15000
- [ ] Increased by 15000
- [ ] 0

> **Giải thích:** @Transactional: RuntimeException → auto rollback. First update executed but rolled back. Account 1 unchanged. ACID atomicity.

## Câu 82

[TYPE: MULTIPLE_CHOICE]

Connection pool monitoring metrics?

- [x] Active connections, idle connections, pending requests, connection wait time
- [ ] Chỉ connection count
- [ ] Chỉ errors
- [ ] Không cần monitoring

> **Giải thích:** HikariCP MXBean: activeConnections, idleConnections, threadsAwaitingConnection, totalConnections. Monitor with Micrometer/Prometheus. Alert on pool exhaustion.

## Câu 83

[TYPE: SELECT_RESULT]

```java
// Pagination
String sql = "SELECT * FROM products ORDER BY id LIMIT ? OFFSET ?";
PreparedStatement ps = conn.prepareStatement(sql);
int pageSize = 10;
int pageNumber = 3; // 0-indexed
ps.setInt(1, pageSize);
ps.setInt(2, pageNumber * pageSize);
```

OFFSET value:

- [x] 30
- [ ] 3
- [ ] 10
- [ ] 20

> **Giải thích:** Page 3 (0-indexed): OFFSET = 3 × 10 = 30. Skip first 30 rows, take 10. Pagination pattern.

## Câu 84

[TYPE: TRUE_FALSE]

Mệnh đề: "Connection pool nên configured với connectionTimeout để tránh threads blocked vô hạn khi pool exhausted."

- [x] Đúng
- [ ] Sai

> **Giải thích:** connectionTimeout: max time waiting for connection. HikariCP default: 30000ms (30s). 0 = infinite wait (dangerous). SQLTransientConnectionException khi timeout.

## Câu 85

[TYPE: SELECT_RESULT]

Cho bảng `users`:
| id | name   | role    |
|----|--------|---------|
| 1  | An     | ADMIN   |
| 2  | Bình   | USER    |
| 3  | Cường  | ADMIN   |
| 4  | Dung   | USER    |
| 5  | Em     | VIEWER  |

```java
String sql = "SELECT role, GROUP_CONCAT(name ORDER BY name) as members FROM users GROUP BY role ORDER BY role";
ResultSet rs = stmt.executeQuery(sql);
while (rs.next()) {
    System.out.println(rs.getString("role") + ": " + rs.getString("members"));
}
```

- [x] ADMIN: An,Cường / USER: Bình,Dung / VIEWER: Em
- [ ] Chỉ ADMIN
- [ ] Lỗi SQL
- [ ] ADMIN: Cường,An

> **Giải thích:** GROUP_CONCAT: concatenate names per role. ORDER BY name: alphabetical. ADMIN: An,Cường. USER: Bình,Dung. VIEWER: Em.

## Câu 86

[TYPE: SELECT_RESULT]

```java
// Check if table exists
DatabaseMetaData meta = conn.getMetaData();
ResultSet tables = meta.getTables(null, null, "USERS", new String[]{"TABLE"});
boolean exists = tables.next();
System.out.println("Table exists: " + exists);
```

Nếu bảng USERS tồn tại:

- [x] Table exists: true
- [ ] Table exists: false
- [ ] Lỗi
- [ ] null

> **Giải thích:** DatabaseMetaData.getTables: search for table by name. next() → true if found. Schema validation before queries.

## Câu 87

[TYPE: FILL_BLANK]

`@Transactional(propagation = Propagation.___)` tạo transaction mới bất kể có transaction hiện tại hay không.

- [x] REQUIRES_NEW
- [ ] REQUIRED
- [ ] SUPPORTS
- [ ] NEVER

> **Giải thích:** REQUIRES_NEW: always new transaction, suspend current. REQUIRED (default): join existing or create new. SUPPORTS: use existing, no-tx if none. MANDATORY: must have existing.

## Câu 88

[TYPE: SELECT_RESULT]

Cho bảng `items` (id, name, quantity):

```java
// Optimistic locking
String selectSql = "SELECT quantity, version FROM items WHERE id = ?";
String updateSql = "UPDATE items SET quantity = ?, version = version + 1 WHERE id = ? AND version = ?";

PreparedStatement selectPs = conn.prepareStatement(selectSql);
selectPs.setInt(1, 1);
ResultSet rs = selectPs.executeQuery();
rs.next();
int currentQty = rs.getInt("quantity"); // 10
int currentVersion = rs.getInt("version"); // 1

PreparedStatement updatePs = conn.prepareStatement(updateSql);
updatePs.setInt(1, currentQty - 1); // 9
updatePs.setInt(2, 1); // id
updatePs.setInt(3, currentVersion); // version check
int updated = updatePs.executeUpdate();
System.out.println("Updated: " + updated);
```

Nếu không ai khác modify:

- [x] Updated: 1
- [ ] Updated: 0
- [ ] Lỗi
- [ ] Updated: 2

> **Giải thích:** Optimistic lock: WHERE version=currentVersion. No concurrent modification → version matches → update succeeds. If someone else changed → version mismatch → updated=0.

## Câu 89

[TYPE: SELECT_RESULT]

Cho bảng `logs`:
| id | level | message       | timestamp           |
|----|-------|---------------|---------------------|
| 1  | ERROR | Exception A   | 2024-01-15 10:00:00 |
| 2  | ERROR | Exception B   | 2024-01-15 10:05:00 |
| 3  | WARN  | Slow query    | 2024-01-15 10:10:00 |

```java
String sql = "SELECT * FROM logs WHERE level = ? AND timestamp > ? ORDER BY timestamp ASC LIMIT 1";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, "ERROR");
ps.setTimestamp(2, Timestamp.valueOf("2024-01-15 10:00:00"));
ResultSet rs = ps.executeQuery();
if (rs.next()) {
    System.out.println(rs.getString("message"));
}
```

- [x] Exception B
- [ ] Exception A
- [ ] Slow query
- [ ] Không có kết quả

> **Giải thích:** level=ERROR AND timestamp > 10:00:00: Exception B (10:05). ASC + LIMIT 1 → first matching. Exception A excluded (= not >).

## Câu 90

[TYPE: MULTIPLE_CHOICE]

JPA vs JDBC?

- [x] JDBC: low-level SQL; JPA: ORM abstraction (entities, repositories, JPQL), built on JDBC
- [ ] Giống nhau
- [ ] JPA thay thế JDBC
- [ ] JDBC cho ORM

> **Giải thích:** JDBC: raw SQL, manual mapping. JPA: Object-Relational Mapping. Hibernate implements JPA. JPA uses JDBC underneath. Choice: control (JDBC) vs productivity (JPA).

## Câu 91

[TYPE: SELECT_RESULT]

```java
// Spring JDBC RowMapper
record User(int id, String name, int age) {}

RowMapper<User> mapper = (rs, rowNum) ->
    new User(rs.getInt("id"), rs.getString("name"), rs.getInt("age"));

List<User> users = jdbcTemplate.query("SELECT * FROM users", mapper);
System.out.println(users.size());
users.forEach(u -> System.out.println(u.name()));
```

Cho 3 users: An, Bình, Cường:

- [x] 3, An, Bình, Cường
- [ ] 0
- [ ] 1
- [ ] Lỗi

> **Giải thích:** RowMapper: map each row to User record. query returns List<User>. 3 users. forEach prints names.

## Câu 92

[TYPE: TRUE_FALSE]

Mệnh đề: "Connection validation (isValid) nên dùng trước mỗi query để đảm bảo connection active."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Connection pool handles validation automatically. Manual isValid() trước mỗi query: overhead không cần thiết. HikariCP validates on borrow/return. Trust the pool.

## Câu 93

[TYPE: SELECT_RESULT]

```java
// JDBC Connection properties
Properties props = new Properties();
props.setProperty("user", "admin");
props.setProperty("password", "secret");
props.setProperty("ssl", "true");
props.setProperty("connectTimeout", "5000");
Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mydb", props);
```

SSL connection:

- [x] Encrypted connection với SSL enabled
- [ ] Lỗi
- [ ] Plain text connection
- [ ] null

> **Giải thích:** Properties: extra connection params. ssl=true: encrypted. connectTimeout: 5s. Driver-specific properties. Secure database connection.

## Câu 94

[TYPE: SELECT_RESULT]

Cho bảng `items` (id, name, price):

```java
String sql = "SELECT name, price, CASE WHEN price > 1000 THEN 'Expensive' WHEN price > 500 THEN 'Medium' ELSE 'Cheap' END as tier FROM items";
```

Item với price = 750:

- [x] Medium
- [ ] Expensive
- [ ] Cheap
- [ ] null

> **Giải thích:** CASE: 750 > 1000? No. 750 > 500? Yes → "Medium". SQL CASE expression for categorization.

## Câu 95

[TYPE: FILL_BLANK]

HikariCP `___` property specifies idle time trước khi connection bị retired khỏi pool.

- [x] idleTimeout
- [ ] idleTime
- [ ] connectionIdle
- [ ] timeout

> **Giải thích:** idleTimeout: milliseconds. Default: 600000 (10 min). Connection idle > timeout → removed. minimumIdle prevents pool shrinking below minimum.

## Câu 96

[TYPE: SELECT_RESULT]

```java
// Stored procedure call
String sql = "{call calculate_discount(?, ?)}";
CallableStatement cs = conn.prepareCall(sql);
cs.setDouble(1, 1000.0); // input: amount
cs.registerOutParameter(2, Types.DOUBLE); // output: discount
cs.execute();
double discount = cs.getDouble(2);
System.out.println("Discount: " + discount);
```

Giả sử procedure trả discount 10% cho amount > 500:

- [x] Discount: 100.0
- [ ] Discount: 0
- [ ] Lỗi SQL
- [ ] Discount: 1000.0

> **Giải thích:** CallableStatement: stored procedure call. Input: 1000. Output: 10% of 1000 = 100.0. registerOutParameter: declare output type.

## Câu 97

[TYPE: SELECT_RESULT]

```java
// Connection pool with multi-tenant
Map<String, HikariDataSource> pools = new HashMap<>();
pools.put("tenant1", createPool("jdbc:h2:mem:tenant1"));
pools.put("tenant2", createPool("jdbc:h2:mem:tenant2"));

DataSource ds = pools.get("tenant1");
try (Connection conn = ds.getConnection()) {
    // query tenant1 database
    System.out.println("Connected to tenant1");
}
```

- [x] Connected to tenant1
- [ ] Lỗi
- [ ] Connected to tenant2
- [ ] null

> **Giải thích:** Multi-tenant: separate pool per tenant. Route by tenant ID. Each pool connects to different database. Isolation between tenants.

## Câu 98

[TYPE: TRUE_FALSE]

Mệnh đề: "Flyway và Liquibase là database migration tools, thường dùng cùng JDBC/connection pool."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Flyway: SQL-based migrations (V1__init.sql). Liquibase: XML/YAML/JSON changesets. Track schema changes. Run via JDBC connection. Spring Boot integration.

## Câu 99

[TYPE: SELECT_RESULT]

```java
// HikariCP metrics with Micrometer
HikariConfig config = new HikariConfig();
config.setJdbcUrl("jdbc:h2:mem:testdb");
config.setMaximumPoolSize(10);
config.setMetricRegistry(new SimpleMeterRegistry());
config.setPoolName("myPool");
HikariDataSource ds = new HikariDataSource(config);
System.out.println(ds.getPoolName());
System.out.println(ds.getMaximumPoolSize());
ds.close();
```

- [x] myPool và 10
- [ ] HikariPool-1 và 10
- [ ] null và 0
- [ ] Lỗi

> **Giải thích:** Custom pool name: "myPool". MaxPoolSize: 10. MetricRegistry: expose metrics. Pool monitoring integration.

## Câu 100

[TYPE: MULTIPLE_CHOICE]

JDBC best practices?

- [x] Use PreparedStatement, connection pools, try-with-resources, batch for bulk ops, proper transaction management
- [ ] Use Statement everywhere
- [ ] No connection pool needed
- [ ] Ignore transactions

> **Giải thích:** PreparedStatement: SQL injection prevention + performance. Connection pool: resource efficiency. Try-with-resources: no leaks. Batch: bulk performance. Transactions: data integrity.
