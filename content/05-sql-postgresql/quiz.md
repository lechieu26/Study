# Quiz - SQL & PostgreSQL

## Câu 1

[TYPE: SELECT_RESULT]

Cho bảng `employees`:

| id | name    | department | salary |
|----|---------|-----------|--------|
| 1  | An      | IT        | 1500   |
| 2  | Bình    | HR        | 1200   |
| 3  | Cường   | IT        | 1800   |
| 4  | Dung    | HR        | 1300   |
| 5  | Em      | IT        | 1600   |

```sql
SELECT department, COUNT(*) as cnt FROM employees GROUP BY department;
```

Kết quả:

- [x] IT: 3, HR: 2
- [ ] IT: 2, HR: 3
- [ ] IT: 5
- [ ] Lỗi cú pháp

> **Giải thích:** GROUP BY department gom nhóm theo phòng ban. IT có 3 nhân viên (An, Cường, Em), HR có 2 (Bình, Dung).

## Câu 2

[TYPE: SELECT_RESULT]

Cho bảng `employees` (như câu 1):

```sql
SELECT name, salary FROM employees WHERE department = 'IT' ORDER BY salary DESC LIMIT 1;
```

Kết quả:

- [x] Cường, 1800
- [ ] An, 1500
- [ ] Em, 1600
- [ ] Lỗi cú pháp

> **Giải thích:** Lọc IT → [An 1500, Cường 1800, Em 1600]. ORDER BY salary DESC → [Cường 1800, Em 1600, An 1500]. LIMIT 1 → Cường, 1800.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Đâu là sự khác biệt giữa WHERE và HAVING?

- [ ] WHERE và HAVING giống nhau
- [x] WHERE lọc trước GROUP BY, HAVING lọc sau GROUP BY (trên kết quả aggregate)
- [ ] HAVING nhanh hơn WHERE
- [ ] WHERE chỉ dùng với SELECT

> **Giải thích:** WHERE lọc rows trước khi GROUP BY. HAVING lọc groups sau khi GROUP BY. HAVING dùng được aggregate functions (COUNT, SUM, AVG...).

## Câu 4

[TYPE: SELECT_RESULT]

Cho bảng `employees` (như câu 1):

```sql
SELECT department, AVG(salary) as avg_sal
FROM employees
GROUP BY department
HAVING AVG(salary) > 1400;
```

- [x] IT: 1633.33
- [ ] HR: 1250
- [ ] Cả IT và HR
- [ ] Không có kết quả

> **Giải thích:** IT avg = (1500+1800+1600)/3 = 1633.33. HR avg = (1200+1300)/2 = 1250. HAVING > 1400 → chỉ IT.

## Câu 5

[TYPE: TRUE_FALSE]

Mệnh đề: "PRIMARY KEY tự động tạo UNIQUE constraint và NOT NULL constraint."

- [x] Đúng
- [ ] Sai

> **Giải thích:** PRIMARY KEY = UNIQUE + NOT NULL. Mỗi bảng chỉ có 1 PRIMARY KEY. Có thể có nhiều UNIQUE constraints.

## Câu 6

[TYPE: SELECT_RESULT]

Cho 2 bảng:

**orders:**

| id | customer_id | amount |
|----|-------------|--------|
| 1  | 101         | 500    |
| 2  | 102         | 300    |
| 3  | 101         | 200    |

**customers:**

| id  | name   |
|-----|--------|
| 101 | An     |
| 102 | Bình   |
| 103 | Cường  |

```sql
SELECT c.name, SUM(o.amount) as total
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id
GROUP BY c.name;
```

- [x] An: 700, Bình: 300, Cường: NULL
- [ ] An: 700, Bình: 300
- [ ] An: 500, Bình: 300, Cường: 0
- [ ] Lỗi cú pháp

> **Giải thích:** LEFT JOIN: giữ tất cả customers. An có 2 orders (500+200=700). Bình có 1 order (300). Cường không có order → NULL (LEFT JOIN).

## Câu 7

[TYPE: FILL_BLANK]

Loại JOIN nào chỉ trả về các rows match ở cả 2 bảng? Đó là `___` JOIN.

- [x] INNER
- [ ] LEFT
- [ ] RIGHT
- [ ] FULL

> **Giải thích:** INNER JOIN: chỉ rows match ở cả 2 bảng. LEFT JOIN: tất cả bên trái + match bên phải. RIGHT JOIN: ngược lại. FULL JOIN: tất cả cả hai bên.

## Câu 8

[TYPE: SELECT_RESULT]

Cho bảng `products`:

| id | name   | price | category   |
|----|--------|-------|------------|
| 1  | Laptop | 1000  | Electronics|
| 2  | Phone  | 500   | Electronics|
| 3  | Shirt  | 30    | Clothing   |
| 4  | Book   | 15    | Books      |
| 5  | TV     | 800   | Electronics|

```sql
SELECT category, MAX(price) - MIN(price) as price_range
FROM products
GROUP BY category
ORDER BY price_range DESC;
```

- [x] Electronics: 500, Clothing: 0, Books: 0
- [ ] Electronics: 1000, Clothing: 30, Books: 15
- [ ] Electronics: 500
- [ ] Lỗi cú pháp

> **Giải thích:** Electronics: MAX(1000)-MIN(500)=500. Clothing: 30-30=0. Books: 15-15=0. ORDER BY price_range DESC → Electronics first.

## Câu 9

[TYPE: SELECT_RESULT]

Cho bảng `employees` (như câu 1):

```sql
SELECT name, salary,
       RANK() OVER (ORDER BY salary DESC) as rank
FROM employees;
```

Kết quả rank:

- [x] Cường:1, Em:2, An:3, Dung:4, Bình:5
- [ ] Cường:1, Em:2, An:3, Dung:3, Bình:5
- [ ] An:1, Bình:2, Cường:3, Dung:4, Em:5
- [ ] Lỗi cú pháp

> **Giải thích:** RANK() xếp hạng theo salary DESC: 1800(1), 1600(2), 1500(3), 1300(4), 1200(5). Không có salary trùng nên RANK = ROW_NUMBER ở đây.

## Câu 10

[TYPE: MULTIPLE_CHOICE]

Sự khác biệt giữa RANK() và DENSE_RANK()?

- [x] RANK() bỏ qua thứ hạng sau ties (1,1,3), DENSE_RANK() không bỏ qua (1,1,2)
- [ ] Không có sự khác biệt
- [ ] DENSE_RANK() nhanh hơn
- [ ] RANK() chỉ dùng trong PostgreSQL

> **Giải thích:** RANK: tie → gap (1,1,3,4). DENSE_RANK: tie → no gap (1,1,2,3). ROW_NUMBER: không tie (1,2,3,4).

## Câu 11

[TYPE: SELECT_RESULT]

Cho bảng `students`:

| id | name  | score |
|----|-------|-------|
| 1  | An    | 85    |
| 2  | Bình  | NULL  |
| 3  | Cường | 90    |
| 4  | Dung  | NULL  |

```sql
SELECT COUNT(*), COUNT(score), AVG(score)
FROM students;
```

- [x] 4, 2, 87.5
- [ ] 4, 4, 43.75
- [ ] 4, 2, 175
- [ ] 2, 2, 87.5

> **Giải thích:** COUNT(*) = 4 (đếm tất cả rows). COUNT(score) = 2 (bỏ qua NULL). AVG(score) = (85+90)/2 = 87.5 (NULL bị bỏ qua).

## Câu 12

[TYPE: TRUE_FALSE]

Mệnh đề: "NULL = NULL trả về TRUE trong SQL."

- [ ] Đúng
- [x] Sai

> **Giải thích:** NULL = NULL → NULL (không phải TRUE). Dùng IS NULL hoặc IS NOT NULL để kiểm tra. NULL không bằng bất kỳ giá trị nào kể cả chính nó.

## Câu 13

[TYPE: SELECT_RESULT]

Cho bảng `orders`:

| id | customer_id | order_date | amount |
|----|-------------|------------|--------|
| 1  | 1           | 2024-01-15 | 100    |
| 2  | 2           | 2024-01-20 | 200    |
| 3  | 1           | 2024-02-10 | 150    |
| 4  | 3           | 2024-02-15 | 300    |
| 5  | 1           | 2024-03-01 | 250    |

```sql
SELECT customer_id, SUM(amount) as total
FROM orders
GROUP BY customer_id
HAVING SUM(amount) > 200
ORDER BY total DESC;
```

- [x] customer 1: 500, customer 3: 300
- [ ] customer 1: 500, customer 2: 200, customer 3: 300
- [ ] customer 3: 300, customer 1: 500
- [ ] Chỉ customer 1: 500

> **Giải thích:** Cust 1: 100+150+250=500. Cust 2: 200. Cust 3: 300. HAVING > 200 → loại Cust 2. ORDER DESC → 500, 300.

## Câu 14

[TYPE: FILL_BLANK]

Câu lệnh SQL nào dùng để tạo index trên cột? `CREATE ___ ON table(column);`

- [x] INDEX index_name
- [ ] VIEW index_name
- [ ] SEQUENCE index_name
- [ ] TRIGGER index_name

> **Giải thích:** `CREATE INDEX idx_name ON table(column)`. Index tăng tốc SELECT nhưng tốn space và chậm INSERT/UPDATE/DELETE.

## Câu 15

[TYPE: SELECT_RESULT]

Cho bảng `employees` (như câu 1):

```sql
SELECT name, salary,
       SUM(salary) OVER (ORDER BY salary) as running_total
FROM employees;
```

Running total cho dòng có salary = 1500:

- [ ] 1500
- [x] 4000
- [ ] 7400
- [ ] 2500

> **Giải thích:** ORDER BY salary: 1200, 1300, 1500, 1600, 1800. Running total tại 1500 = 1200+1300+1500 = 4000.

## Câu 16

[TYPE: SELECT_RESULT]

Cho 2 bảng:

**departments:**

| id | name |
|----|------|
| 1  | IT   |
| 2  | HR   |
| 3  | Sales|

**employees:**

| id | name  | dept_id |
|----|-------|---------|
| 1  | An    | 1       |
| 2  | Bình  | 1       |
| 3  | Cường | 2       |

```sql
SELECT d.name, COUNT(e.id) as emp_count
FROM departments d
LEFT JOIN employees e ON d.id = e.dept_id
GROUP BY d.name;
```

- [x] IT: 2, HR: 1, Sales: 0
- [ ] IT: 2, HR: 1
- [ ] IT: 2, HR: 1, Sales: NULL
- [ ] Lỗi cú pháp

> **Giải thích:** LEFT JOIN giữ tất cả departments. Sales không có employee → COUNT(e.id) = 0 (COUNT column bỏ NULL).

## Câu 17

[TYPE: MULTIPLE_CHOICE]

Đâu là loại index phổ biến nhất trong PostgreSQL?

- [x] B-Tree
- [ ] Hash
- [ ] GiST
- [ ] BRIN

> **Giải thích:** B-Tree: default index type. Phù hợp cho =, <, >, BETWEEN, ORDER BY. Hash: chỉ cho =. GiST: geometric/full-text. GIN: array/JSON. BRIN: large tables sorted.

## Câu 18

[TYPE: SELECT_RESULT]

Cho bảng `products` (như câu 8):

```sql
SELECT name, price,
       CASE
           WHEN price >= 500 THEN 'Expensive'
           WHEN price >= 50 THEN 'Medium'
           ELSE 'Cheap'
       END as price_tier
FROM products WHERE category = 'Electronics';
```

Kết quả:

- [x] Laptop: Expensive, Phone: Expensive, TV: Expensive
- [ ] Laptop: Expensive, Phone: Medium, TV: Expensive
- [ ] Lỗi cú pháp
- [ ] Laptop: Expensive, Phone: Expensive, TV: Medium

> **Giải thích:** Electronics: Laptop(1000≥500→Expensive), Phone(500≥500→Expensive), TV(800≥500→Expensive). CASE WHEN kiểm tra theo thứ tự.

## Câu 19

[TYPE: SELECT_RESULT]

Cho bảng `sales`:

| id | product | quantity | sale_date  |
|----|---------|----------|------------|
| 1  | A       | 10       | 2024-01-15 |
| 2  | B       | 5        | 2024-01-20 |
| 3  | A       | 8        | 2024-02-10 |
| 4  | B       | 12       | 2024-02-15 |
| 5  | A       | 6        | 2024-03-01 |

```sql
SELECT product,
       SUM(quantity) as total,
       SUM(quantity) * 100.0 / SUM(SUM(quantity)) OVER () as percentage
FROM sales
GROUP BY product;
```

- [x] A: 24 (58.5%), B: 17 (41.5%)
- [ ] A: 24 (50%), B: 17 (50%)
- [ ] Lỗi cú pháp
- [ ] A: 10, B: 5

> **Giải thích:** A: 10+8+6=24. B: 5+12=17. Total=41. A%=24/41×100≈58.5%. B%=17/41×100≈41.5%.

## Câu 20

[TYPE: TRUE_FALSE]

Mệnh đề: "TRUNCATE TABLE xóa tất cả dữ liệu và có thể ROLLBACK trong PostgreSQL."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Trong PostgreSQL, TRUNCATE là transactional và có thể ROLLBACK (khác với MySQL). TRUNCATE nhanh hơn DELETE vì không log từng row.

## Câu 21

[TYPE: SELECT_RESULT]

Cho bảng `employees` (như câu 1):

```sql
SELECT name, salary,
       LAG(salary) OVER (ORDER BY salary) as prev_salary,
       salary - LAG(salary) OVER (ORDER BY salary) as diff
FROM employees;
```

Dòng có name='Em' (salary=1600), diff là:

- [x] 100
- [ ] 200
- [ ] 1600
- [ ] NULL

> **Giải thích:** ORDER BY salary: 1200,1300,1500,1600,1800. LAG(salary) tại 1600 = 1500 (giá trị trước). diff = 1600-1500 = 100.

## Câu 22

[TYPE: FILL_BLANK]

Lệnh nào tạo bảng tạm thời trong PostgreSQL chỉ tồn tại trong session hiện tại? `CREATE ___ TABLE temp_data (...);`

- [x] TEMPORARY
- [ ] VIRTUAL
- [ ] SESSION
- [ ] LOCAL

> **Giải thích:** `CREATE TEMPORARY TABLE` hoặc `CREATE TEMP TABLE`. Tồn tại trong session, tự động xóa khi session kết thúc.

## Câu 23

[TYPE: SELECT_RESULT]

Cho bảng `employees` (như câu 1). Thêm subquery:

```sql
SELECT name, salary
FROM employees
WHERE salary > (SELECT AVG(salary) FROM employees);
```

- [x] Cường: 1800, Em: 1600
- [ ] Cường: 1800
- [ ] Tất cả nhân viên
- [ ] Không có kết quả

> **Giải thích:** AVG(salary) = (1500+1200+1800+1300+1600)/5 = 1480. salary > 1480: Cường(1800), Em(1600).

## Câu 24

[TYPE: SELECT_RESULT]

Cho bảng `orders`:

| id | product_id | quantity | order_date |
|----|-----------|----------|------------|
| 1  | 1         | 3        | 2024-01-10 |
| 2  | 2         | 5        | 2024-01-15 |
| 3  | 1         | 2        | 2024-01-20 |
| 4  | 1         | 4        | 2024-02-01 |
| 5  | 2         | 1        | 2024-02-10 |

```sql
SELECT product_id,
       EXTRACT(MONTH FROM order_date) as month,
       SUM(quantity) as total
FROM orders
GROUP BY product_id, EXTRACT(MONTH FROM order_date)
ORDER BY product_id, month;
```

Kết quả cho product_id=1:

- [x] Tháng 1: 5, Tháng 2: 4
- [ ] Tháng 1: 3, Tháng 2: 6
- [ ] Total: 9
- [ ] Lỗi cú pháp

> **Giải thích:** Product 1 tháng 1: 3+2=5. Product 1 tháng 2: 4. GROUP BY cả product_id và month.

## Câu 25

[TYPE: MULTIPLE_CHOICE]

ACID trong database là viết tắt của gì?

- [x] Atomicity, Consistency, Isolation, Durability
- [ ] Access, Control, Identity, Data
- [ ] Aggregate, Count, Index, Delete
- [ ] Authentication, Cryptography, Integrity, Distribution

> **Giải thích:** Atomicity: tất cả hoặc không gì. Consistency: dữ liệu hợp lệ. Isolation: transactions không ảnh hưởng nhau. Durability: committed data bền vững.

## Câu 26

[TYPE: SELECT_RESULT]

Cho bảng `students` (như câu 11):

```sql
SELECT COALESCE(score, 0) as adjusted_score FROM students ORDER BY id;
```

- [x] 85, 0, 90, 0
- [ ] 85, NULL, 90, NULL
- [ ] 0, 0, 0, 0
- [ ] 85, 85, 90, 90

> **Giải thích:** COALESCE trả về giá trị đầu tiên không phải NULL. score NULL → 0. An: 85, Bình: 0, Cường: 90, Dung: 0.

## Câu 27

[TYPE: SELECT_RESULT]

Cho bảng `employees`:

| id | name  | manager_id |
|----|-------|-----------|
| 1  | CEO   | NULL      |
| 2  | CTO   | 1         |
| 3  | Dev1  | 2         |
| 4  | Dev2  | 2         |
| 5  | HR    | 1         |

```sql
SELECT e.name as employee, m.name as manager
FROM employees e
LEFT JOIN employees m ON e.manager_id = m.id;
```

Dòng cho CEO:

- [x] employee: CEO, manager: NULL
- [ ] employee: CEO, manager: CEO
- [ ] Không có dòng cho CEO
- [ ] Lỗi cú pháp

> **Giải thích:** Self JOIN: CEO.manager_id = NULL → LEFT JOIN giữ CEO nhưng không match → manager = NULL. CTO→CEO, Dev1→CTO, Dev2→CTO, HR→CEO.

## Câu 28

[TYPE: TRUE_FALSE]

Mệnh đề: "EXISTS thường nhanh hơn IN khi subquery trả về nhiều kết quả."

- [x] Đúng
- [ ] Sai

> **Giải thích:** EXISTS dừng ngay khi tìm thấy match đầu tiên. IN phải evaluate toàn bộ subquery. Với large subquery result, EXISTS hiệu quả hơn.

## Câu 29

[TYPE: SELECT_RESULT]

Cho bảng `products` (như câu 8):

```sql
SELECT * FROM products
WHERE price BETWEEN 50 AND 800
ORDER BY price;
```

Có bao nhiêu kết quả?

- [ ] 1
- [x] 2
- [ ] 3
- [ ] 0

> **Giải thích:** BETWEEN 50 AND 800 (inclusive): Phone(500)✓, TV(800)✓. Laptop(1000)✗, Shirt(30)✗, Book(15)✗. Kết quả: 2 rows.

## Câu 30

[TYPE: FILL_BLANK]

Trong PostgreSQL, kiểu dữ liệu lưu trữ chuỗi có độ dài thay đổi mà không giới hạn độ dài là `___`.

- [x] TEXT
- [ ] VARCHAR
- [ ] CHAR
- [ ] STRING

> **Giải thích:** TEXT: chuỗi có độ dài không giới hạn. VARCHAR(n): chuỗi tối đa n ký tự. CHAR(n): chuỗi cố định n ký tự (padding spaces).

## Câu 31

[TYPE: SELECT_RESULT]

Cho bảng `transactions`:

| id | account_id | type    | amount | created_at          |
|----|-----------|---------|--------|---------------------|
| 1  | 1         | credit  | 1000   | 2024-01-01 10:00:00 |
| 2  | 1         | debit   | 200    | 2024-01-02 14:00:00 |
| 3  | 1         | credit  | 500    | 2024-01-03 09:00:00 |
| 4  | 2         | credit  | 2000   | 2024-01-01 11:00:00 |
| 5  | 2         | debit   | 800    | 2024-01-02 16:00:00 |

```sql
SELECT account_id,
       SUM(CASE WHEN type = 'credit' THEN amount ELSE -amount END) as balance
FROM transactions
GROUP BY account_id;
```

- [x] Account 1: 1300, Account 2: 1200
- [ ] Account 1: 1700, Account 2: 2800
- [ ] Account 1: -300, Account 2: -800
- [ ] Lỗi cú pháp

> **Giải thích:** Acc 1: 1000 - 200 + 500 = 1300. Acc 2: 2000 - 800 = 1200. CASE WHEN chuyển debit thành âm.

## Câu 32

[TYPE: SELECT_RESULT]

Cho bảng `employees` (như câu 1):

```sql
SELECT department,
       STRING_AGG(name, ', ' ORDER BY name) as members
FROM employees
GROUP BY department;
```

- [x] IT: "An, Cường, Em", HR: "Bình, Dung"
- [ ] IT: "An,Cường,Em", HR: "Bình,Dung"
- [ ] Lỗi cú pháp
- [ ] IT: "Cường, Em, An", HR: "Dung, Bình"

> **Giải thích:** STRING_AGG: nối strings trong group. ORDER BY name → alphabetical. PostgreSQL specific function.

## Câu 33

[TYPE: MULTIPLE_CHOICE]

Transaction Isolation Level nào trong PostgreSQL mặc định?

- [ ] READ UNCOMMITTED
- [x] READ COMMITTED
- [ ] REPEATABLE READ
- [ ] SERIALIZABLE

> **Giải thích:** PostgreSQL default: READ COMMITTED. Mỗi query trong transaction thấy data committed trước khi query bắt đầu.

## Câu 34

[TYPE: SELECT_RESULT]

Cho bảng `logs`:

| id | user_id | action  | timestamp           |
|----|---------|---------|---------------------|
| 1  | 1       | login   | 2024-01-01 08:00:00 |
| 2  | 1       | view    | 2024-01-01 08:05:00 |
| 3  | 2       | login   | 2024-01-01 09:00:00 |
| 4  | 1       | logout  | 2024-01-01 08:30:00 |
| 5  | 2       | view    | 2024-01-01 09:10:00 |

```sql
SELECT user_id, MIN(timestamp) as first_action, MAX(timestamp) as last_action
FROM logs
GROUP BY user_id;
```

User 1 kết quả:

- [x] first: 2024-01-01 08:00:00, last: 2024-01-01 08:30:00
- [ ] first: 2024-01-01 08:05:00, last: 2024-01-01 08:30:00
- [ ] first: 2024-01-01 08:00:00, last: 2024-01-01 09:10:00
- [ ] Lỗi cú pháp

> **Giải thích:** User 1 có 3 actions: 08:00, 08:05, 08:30. MIN=08:00, MAX=08:30.

## Câu 35

[TYPE: SELECT_RESULT]

Cho bảng `employees` (như câu 1):

```sql
SELECT name, salary,
       NTILE(2) OVER (ORDER BY salary DESC) as bucket
FROM employees;
```

Bucket 1 gồm những ai?

- [x] Cường (1800), Em (1600), An (1500)
- [ ] Cường (1800), Em (1600)
- [ ] Tất cả 5 người
- [ ] Lỗi cú pháp

> **Giải thích:** NTILE(2): chia thành 2 nhóm bằng nhau. 5 rows → bucket 1: 3 rows (1800,1600,1500), bucket 2: 2 rows (1300,1200).

## Câu 36

[TYPE: TRUE_FALSE]

Mệnh đề: "UNIQUE constraint cho phép nhiều giá trị NULL."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Trong PostgreSQL, UNIQUE constraint cho phép nhiều NULL (SQL standard: NULL ≠ NULL). Mỗi NULL được coi là khác nhau.

## Câu 37

[TYPE: SELECT_RESULT]

Cho bảng `products` (như câu 8). Sử dụng CTE (Common Table Expression):

```sql
WITH expensive AS (
    SELECT * FROM products WHERE price > 100
)
SELECT category, COUNT(*) as cnt FROM expensive GROUP BY category;
```

- [x] Electronics: 3
- [ ] Electronics: 3, Clothing: 1
- [ ] Electronics: 2
- [ ] Lỗi cú pháp

> **Giải thích:** CTE expensive: Laptop(1000), Phone(500), TV(800) → tất cả Electronics. COUNT = 3.

## Câu 38

[TYPE: SELECT_RESULT]

Cho 2 bảng:

**students:**

| id | name  |
|----|-------|
| 1  | An    |
| 2  | Bình  |
| 3  | Cường |

**enrollments:**

| student_id | course    |
|-----------|-----------|
| 1         | Math      |
| 1         | Physics   |
| 2         | Math      |

```sql
SELECT s.name
FROM students s
WHERE NOT EXISTS (
    SELECT 1 FROM enrollments e WHERE e.student_id = s.id
);
```

- [x] Cường
- [ ] An, Bình
- [ ] Không có kết quả
- [ ] An, Bình, Cường

> **Giải thích:** NOT EXISTS: tìm students không có enrollment. An có 2 enrollments, Bình có 1. Cường không có → trả về Cường.

## Câu 39

[TYPE: MULTIPLE_CHOICE]

Đâu là ưu điểm của VIEW trong PostgreSQL?

- [x] Đơn giản hóa query phức tạp, bảo mật (ẩn cột), tái sử dụng logic
- [ ] Tăng tốc query
- [ ] Tự động update khi data thay đổi
- [ ] Thay thế hoàn toàn table

> **Giải thích:** VIEW: virtual table từ SELECT. Đơn giản hóa, bảo mật, reuse. Không lưu data (trừ Materialized View). Không tự động tăng tốc query.

## Câu 40

[TYPE: SELECT_RESULT]

Cho bảng `orders`:

| id | customer_id | product  | amount |
|----|-------------|----------|--------|
| 1  | 1           | Laptop   | 1000   |
| 2  | 1           | Phone    | 500    |
| 3  | 2           | Laptop   | 1000   |
| 4  | 2           | Tablet   | 700    |
| 5  | 3           | Phone    | 500    |

```sql
SELECT customer_id
FROM orders
WHERE product IN ('Laptop', 'Phone')
GROUP BY customer_id
HAVING COUNT(DISTINCT product) = 2;
```

- [x] customer_id = 1
- [ ] customer_id = 1, 2
- [ ] customer_id = 1, 2, 3
- [ ] Không có kết quả

> **Giải thích:** Lọc Laptop/Phone → Cust 1 (Laptop+Phone), Cust 2 (chỉ Laptop), Cust 3 (chỉ Phone). HAVING DISTINCT = 2 → chỉ Cust 1 mua cả hai.

## Câu 41

[TYPE: FILL_BLANK]

Lệnh PostgreSQL tạo chuỗi tự tăng cho cột ID gọi là `___`.

- [x] SERIAL / GENERATED ALWAYS AS IDENTITY
- [ ] AUTO_INCREMENT
- [ ] AUTOINCREMENT
- [ ] SEQUENCE_AUTO

> **Giải thích:** PostgreSQL: `SERIAL` (legacy) hoặc `GENERATED ALWAYS AS IDENTITY` (SQL standard). MySQL dùng AUTO_INCREMENT.

## Câu 42

[TYPE: SELECT_RESULT]

Cho bảng `sales`:

| id | region | product | revenue |
|----|--------|---------|---------|
| 1  | North  | A       | 100     |
| 2  | North  | B       | 200     |
| 3  | South  | A       | 150     |
| 4  | South  | B       | 250     |
| 5  | North  | A       | 50      |

```sql
SELECT region, product, SUM(revenue) as total,
       SUM(SUM(revenue)) OVER (PARTITION BY region) as region_total
FROM sales
GROUP BY region, product;
```

North-A:

- [x] total: 150, region_total: 350
- [ ] total: 100, region_total: 300
- [ ] total: 150, region_total: 750
- [ ] Lỗi cú pháp

> **Giải thích:** North-A: 100+50=150. North-B: 200. region_total cho North = 150+200 = 350. Window PARTITION BY region.

## Câu 43

[TYPE: SELECT_RESULT]

Cho bảng `employees`:

| id | name  | dept | salary | hire_date  |
|----|-------|------|--------|------------|
| 1  | An    | IT   | 1500   | 2020-01-15 |
| 2  | Bình  | IT   | 1800   | 2019-06-01 |
| 3  | Cường | HR   | 1200   | 2021-03-10 |
| 4  | Dung  | IT   | 2000   | 2018-09-20 |
| 5  | Em    | HR   | 1400   | 2020-11-05 |

```sql
SELECT dept, name, salary,
       ROW_NUMBER() OVER (PARTITION BY dept ORDER BY salary DESC) as rn
FROM employees;
```

Nhân viên có rn = 1 ở mỗi department:

- [x] IT: Dung (2000), HR: Em (1400)
- [ ] IT: An (1500), HR: Cường (1200)
- [ ] IT: Bình (1800), HR: Cường (1200)
- [ ] Lỗi cú pháp

> **Giải thích:** PARTITION BY dept, ORDER BY salary DESC. IT: Dung(2000)→rn1, Bình(1800)→rn2, An(1500)→rn3. HR: Em(1400)→rn1, Cường(1200)→rn2.

## Câu 44

[TYPE: MULTIPLE_CHOICE]

Khi nào nên dùng Materialized View?

- [x] Khi query phức tạp chạy thường xuyên và data ít thay đổi
- [ ] Khi data thay đổi liên tục
- [ ] Khi cần real-time data
- [ ] Luôn luôn nên dùng

> **Giải thích:** Materialized View: lưu kết quả query. Cần REFRESH để update. Phù hợp: report, dashboard. Không phù hợp: real-time data.

## Câu 45

[TYPE: SELECT_RESULT]

Cho bảng `products` (như câu 8):

```sql
SELECT category,
       ARRAY_AGG(name ORDER BY price DESC) as products
FROM products
GROUP BY category;
```

Electronics:

- [x] {Laptop, TV, Phone}
- [ ] {Phone, TV, Laptop}
- [ ] {Laptop, Phone, TV}
- [ ] Lỗi cú pháp

> **Giải thích:** ARRAY_AGG: tạo array từ group. ORDER BY price DESC: Laptop(1000), TV(800), Phone(500). PostgreSQL specific function.

## Câu 46

[TYPE: TRUE_FALSE]

Mệnh đề: "PostgreSQL hỗ trợ JSON và JSONB data types."

- [x] Đúng
- [ ] Sai

> **Giải thích:** JSON: lưu text gốc, parse mỗi lần truy vấn. JSONB: lưu binary, nhanh hơn khi query, hỗ trợ indexing. Nên dùng JSONB.

## Câu 47

[TYPE: SELECT_RESULT]

Cho bảng `events`:

| id | user_id | event_type | event_date |
|----|---------|------------|------------|
| 1  | 1       | signup     | 2024-01-01 |
| 2  | 2       | signup     | 2024-01-05 |
| 3  | 1       | purchase   | 2024-01-10 |
| 4  | 3       | signup     | 2024-01-15 |
| 5  | 2       | purchase   | 2024-01-20 |

```sql
SELECT u.user_id, u.signup_date, p.purchase_date,
       p.purchase_date - u.signup_date as days_to_convert
FROM (SELECT user_id, event_date as signup_date FROM events WHERE event_type = 'signup') u
LEFT JOIN (SELECT user_id, MIN(event_date) as purchase_date FROM events WHERE event_type = 'purchase' GROUP BY user_id) p
ON u.user_id = p.user_id;
```

User 1 days_to_convert:

- [x] 9
- [ ] 10
- [ ] NULL
- [ ] 0

> **Giải thích:** User 1: signup 2024-01-01, purchase 2024-01-10. days = 10 - 1 = 9 days.

## Câu 48

[TYPE: SELECT_RESULT]

Cho bảng `employees` (câu 43):

```sql
SELECT name, salary,
       salary - LAG(salary) OVER (PARTITION BY dept ORDER BY hire_date) as salary_diff
FROM employees
WHERE dept = 'IT';
```

Dòng cho An:

- [x] salary_diff: -300
- [ ] salary_diff: 300
- [ ] salary_diff: NULL
- [ ] salary_diff: 1500

> **Giải thích:** IT ORDER BY hire_date: Dung(2018, 2000), Bình(2019, 1800), An(2020, 1500). An's LAG = Bình's salary = 1800. diff = 1500 - 1800 = -300.

## Câu 49

[TYPE: MULTIPLE_CHOICE]

Đâu là Normal Form thứ 3 (3NF)?

- [x] Mọi cột non-key phụ thuộc trực tiếp vào primary key, không có transitive dependency
- [ ] Mọi cột đều atomic
- [ ] Không có partial dependency
- [ ] Mọi dependency đều là candidate key

> **Giải thích:** 1NF: atomic values. 2NF: no partial dependency. 3NF: no transitive dependency (non-key → non-key). BCNF: mọi determinant là superkey.

## Câu 50

[TYPE: SELECT_RESULT]

Cho bảng `orders`:

| id | customer_id | order_date |
|----|------------|------------|
| 1  | 1          | 2024-01-10 |
| 2  | 1          | 2024-01-15 |
| 3  | 2          | 2024-01-12 |
| 4  | 1          | 2024-02-01 |
| 5  | 2          | 2024-02-10 |

```sql
SELECT customer_id, order_date,
       order_date - LAG(order_date) OVER (PARTITION BY customer_id ORDER BY order_date) as days_between
FROM orders;
```

Customer 1, order_date 2024-01-15:

- [x] days_between: 5
- [ ] days_between: NULL
- [ ] days_between: 15
- [ ] days_between: 17

> **Giải thích:** Customer 1 orders: 01-10, 01-15, 02-01. LAG tại 01-15 = 01-10. Days = 15-10 = 5.

## Câu 51

[TYPE: FILL_BLANK]

Lệnh SQL nào dùng để thay đổi cấu trúc bảng (thêm/xóa cột)? Đó là `___`.

- [x] ALTER TABLE
- [ ] UPDATE TABLE
- [ ] MODIFY TABLE
- [ ] CHANGE TABLE

> **Giải thích:** ALTER TABLE: ADD COLUMN, DROP COLUMN, ALTER COLUMN, RENAME COLUMN, ADD CONSTRAINT. DDL statement.

## Câu 52

[TYPE: SELECT_RESULT]

Cho bảng `inventory`:

| id | product | warehouse | quantity |
|----|---------|-----------|----------|
| 1  | A       | W1        | 100      |
| 2  | A       | W2        | 50       |
| 3  | B       | W1        | 200      |
| 4  | B       | W2        | 80       |
| 5  | C       | W1        | 30       |

```sql
SELECT product, SUM(quantity) as total
FROM inventory
GROUP BY product
HAVING SUM(quantity) > 100
ORDER BY total DESC;
```

- [x] B: 280, A: 150
- [ ] A: 150, B: 280, C: 30
- [ ] B: 280
- [ ] A: 100, B: 200, C: 30

> **Giải thích:** A: 100+50=150. B: 200+80=280. C: 30. HAVING > 100: A(150)✓, B(280)✓. ORDER DESC: B(280), A(150).

## Câu 53

[TYPE: SELECT_RESULT]

Cho bảng `employees` (câu 43):

```sql
WITH ranked AS (
    SELECT *, DENSE_RANK() OVER (PARTITION BY dept ORDER BY salary DESC) as dr
    FROM employees
)
SELECT dept, name, salary FROM ranked WHERE dr <= 2;
```

IT department results:

- [x] Dung: 2000, Bình: 1800
- [ ] Dung: 2000
- [ ] An: 1500, Bình: 1800
- [ ] Dung: 2000, Bình: 1800, An: 1500

> **Giải thích:** IT DENSE_RANK by salary DESC: Dung(2000)→1, Bình(1800)→2, An(1500)→3. WHERE dr <= 2: Dung, Bình. Top 2 salaries per dept.

## Câu 54

[TYPE: TRUE_FALSE]

Mệnh đề: "DELETE FROM table và TRUNCATE TABLE đều xóa tất cả data nhưng DELETE log từng row còn TRUNCATE không."

- [x] Đúng
- [ ] Sai

> **Giải thích:** DELETE: DML, log từng row, trigger WHERE, có thể chậm với nhiều data. TRUNCATE: DDL, deallocate pages, nhanh hơn nhưng không trigger row-level triggers.

## Câu 55

[TYPE: SELECT_RESULT]

Cho bảng `users`:

| id | name  | email           | created_at |
|----|-------|-----------------|------------|
| 1  | An    | an@gmail.com    | 2024-01-01 |
| 2  | Bình  | binh@yahoo.com  | 2024-01-05 |
| 3  | Cường | cuong@gmail.com | 2024-02-01 |
| 4  | Dung  | dung@gmail.com  | 2024-02-15 |
| 5  | Em    | em@outlook.com  | 2024-03-01 |

```sql
SELECT
    SPLIT_PART(email, '@', 2) as domain,
    COUNT(*) as cnt
FROM users
GROUP BY domain
ORDER BY cnt DESC;
```

- [x] gmail.com: 3, yahoo.com: 1, outlook.com: 1
- [ ] gmail.com: 3
- [ ] Lỗi cú pháp
- [ ] 5 rows

> **Giải thích:** SPLIT_PART: tách chuỗi. gmail.com: 3 (An, Cường, Dung). yahoo.com: 1 (Bình). outlook.com: 1 (Em). PostgreSQL specific.

## Câu 56

[TYPE: SELECT_RESULT]

Cho bảng `products` (như câu 8):

```sql
SELECT * FROM products
WHERE name LIKE '%o%' AND price > 20
ORDER BY price;
```

- [x] Book: 15 bị loại (price > 20 fail), Phone: 500 match
- [ ] Phone, Book
- [ ] Phone
- [ ] Không có kết quả

> **Giải thích:** LIKE '%o%': chứa 'o'. Book: 'o' ✓ nhưng price=15 < 20 ✗. Phone: 'o' ✓, price=500 > 20 ✓. Kết quả: chỉ Phone.

## Câu 57

[TYPE: MULTIPLE_CHOICE]

Đâu là lợi ích của Index?

- [x] Tăng tốc SELECT/WHERE/JOIN, nhưng chậm INSERT/UPDATE/DELETE
- [ ] Tăng tốc tất cả operations
- [ ] Giảm storage
- [ ] Thay thế Primary Key

> **Giải thích:** Index: B-Tree structure tăng tốc lookup. Trade-off: INSERT/UPDATE/DELETE phải update index. Tốn thêm disk space.

## Câu 58

[TYPE: SELECT_RESULT]

Cho bảng `employee_projects`:

| employee_id | project_id | hours |
|------------|-----------|-------|
| 1          | A         | 40    |
| 1          | B         | 20    |
| 2          | A         | 30    |
| 2          | C         | 25    |
| 3          | B         | 35    |

```sql
SELECT employee_id,
       COUNT(DISTINCT project_id) as num_projects,
       SUM(hours) as total_hours,
       ROUND(AVG(hours), 1) as avg_hours
FROM employee_projects
GROUP BY employee_id
HAVING COUNT(DISTINCT project_id) > 1;
```

- [x] Emp 1: 2 projects, 60h, 30.0 avg; Emp 2: 2 projects, 55h, 27.5 avg
- [ ] Emp 1: 2, 60h; Emp 2: 2, 55h; Emp 3: 1, 35h
- [ ] Emp 1: 60h, Emp 2: 55h
- [ ] Lỗi cú pháp

> **Giải thích:** HAVING count > 1: Emp 1 (2 projects), Emp 2 (2 projects). Emp 3 (1 project) loại. Emp 1: avg = 60/2 = 30.0. Emp 2: avg = 55/2 = 27.5.

## Câu 59

[TYPE: SELECT_RESULT]

```sql
SELECT
    GENERATE_SERIES('2024-01-01'::date, '2024-01-05'::date, '1 day'::interval) as date;
```

Trả về bao nhiêu rows?

- [x] 5
- [ ] 4
- [ ] 6
- [ ] Lỗi cú pháp

> **Giải thích:** GENERATE_SERIES: từ 01-01 đến 01-05, bước 1 ngày → 5 rows: 01, 02, 03, 04, 05. Inclusive cả start và end.

## Câu 60

[TYPE: FILL_BLANK]

Trong SQL, để kết hợp kết quả của 2 SELECT mà loại bỏ trùng lặp, dùng `___`.

- [x] UNION
- [ ] UNION ALL
- [ ] MERGE
- [ ] COMBINE

> **Giải thích:** UNION: kết hợp + loại trùng. UNION ALL: kết hợp + giữ trùng (nhanh hơn). INTERSECT: lấy chung. EXCEPT: loại trừ.

## Câu 61

[TYPE: SELECT_RESULT]

Cho bảng `orders` (câu 50):

```sql
SELECT customer_id,
       COUNT(*) as total_orders,
       MAX(order_date) - MIN(order_date) as date_range
FROM orders
GROUP BY customer_id;
```

Customer 1:

- [x] total_orders: 3, date_range: 22 days
- [ ] total_orders: 3, date_range: 21 days
- [ ] total_orders: 2, date_range: 5 days
- [ ] Lỗi cú pháp

> **Giải thích:** Cust 1: 3 orders. MAX=2024-02-01, MIN=2024-01-10. Diff = 22 days (Jan has 31 days, 31-10+1 = 22).

## Câu 62

[TYPE: SELECT_RESULT]

Cho bảng `scores`:

| id | student | subject | score |
|----|---------|---------|-------|
| 1  | An      | Math    | 80    |
| 2  | An      | Physics | 90    |
| 3  | Bình    | Math    | 70    |
| 4  | Bình    | Physics | 85    |
| 5  | An      | Chemistry| 75   |

```sql
SELECT student,
       MAX(score) FILTER (WHERE subject = 'Math') as math,
       MAX(score) FILTER (WHERE subject = 'Physics') as physics
FROM scores
GROUP BY student;
```

- [x] An: math=80, physics=90. Bình: math=70, physics=85
- [ ] An: 90, 90. Bình: 85, 85
- [ ] Lỗi cú pháp
- [ ] An: 80, Bình: 70

> **Giải thích:** FILTER: PostgreSQL specific aggregate filter. Pivot table: mỗi subject thành cột. An Math=80, Physics=90. Bình Math=70, Physics=85.

## Câu 63

[TYPE: MULTIPLE_CHOICE]

Đâu là sự khác biệt giữa INNER JOIN và CROSS JOIN?

- [x] INNER JOIN dùng điều kiện ON, CROSS JOIN tạo tích Descartes (mọi tổ hợp)
- [ ] Không có sự khác biệt
- [ ] CROSS JOIN nhanh hơn
- [ ] INNER JOIN tạo nhiều rows hơn

> **Giải thích:** INNER JOIN: match rows theo ON condition. CROSS JOIN: mỗi row bảng A × mỗi row bảng B. 3 rows × 4 rows = 12 rows.

## Câu 64

[TYPE: SELECT_RESULT]

Cho bảng `products` (như câu 8):

```sql
SELECT name, price,
       PERCENT_RANK() OVER (ORDER BY price) as pct_rank
FROM products;
```

Shirt (price=30) có percent_rank:

- [x] 0.25
- [ ] 0
- [ ] 0.5
- [ ] 1

> **Giải thích:** ORDER BY price: Book(15), Shirt(30), Phone(500), TV(800), Laptop(1000). PERCENT_RANK = (rank-1)/(n-1). Shirt rank=2: (2-1)/(5-1) = 0.25.

## Câu 65

[TYPE: SELECT_RESULT]

Cho bảng `products`:

| id | name   | price | stock |
|----|--------|-------|-------|
| 1  | A      | 100   | 0     |
| 2  | B      | 200   | 5     |
| 3  | C      | 150   | NULL  |
| 4  | D      | 300   | 10    |

```sql
SELECT name FROM products WHERE stock > 0 OR stock IS NULL;
```

- [x] B, C, D
- [ ] B, D
- [ ] A, B, C, D
- [ ] C

> **Giải thích:** A: stock=0 → 0>0 false, IS NULL false → ✗. B: 5>0 ✓. C: NULL>0 = NULL, IS NULL ✓. D: 10>0 ✓.

## Câu 66

[TYPE: TRUE_FALSE]

Mệnh đề: "PostgreSQL hỗ trợ table inheritance."

- [x] Đúng
- [ ] Sai

> **Giải thích:** PostgreSQL: `CREATE TABLE child_table () INHERITS (parent_table)`. Child thừa kế columns từ parent. Unique feature of PostgreSQL.

## Câu 67

[TYPE: SELECT_RESULT]

Cho bảng `employees` (câu 43):

```sql
UPDATE employees SET salary = salary * 1.1 WHERE dept = 'IT';
SELECT name, salary FROM employees WHERE dept = 'IT' ORDER BY salary DESC;
```

Kết quả sau UPDATE:

- [x] Dung: 2200, Bình: 1980, An: 1650
- [ ] Dung: 2100, Bình: 1900, An: 1600
- [ ] Dung: 2000, Bình: 1800, An: 1500
- [ ] Lỗi cú pháp

> **Giải thích:** salary * 1.1 = tăng 10%. Dung: 2000×1.1=2200. Bình: 1800×1.1=1980. An: 1500×1.1=1650.

## Câu 68

[TYPE: SELECT_RESULT]

Cho bảng `logs` (câu 34):

```sql
SELECT DISTINCT user_id FROM logs
WHERE action = 'login'
EXCEPT
SELECT DISTINCT user_id FROM logs
WHERE action = 'view';
```

- [x] Không có kết quả (empty)
- [ ] user_id = 1
- [ ] user_id = 2
- [ ] user_id = 1, 2

> **Giải thích:** Login users: {1, 2}. View users: {1, 2}. EXCEPT: {1,2} - {1,2} = empty. Cả hai user đều có cả login và view.

## Câu 69

[TYPE: MULTIPLE_CHOICE]

Đâu là constraint type trong PostgreSQL?

- [x] NOT NULL, UNIQUE, PRIMARY KEY, FOREIGN KEY, CHECK, EXCLUDE
- [ ] Chỉ NOT NULL và PRIMARY KEY
- [ ] NOT NULL, UNIQUE, INDEX
- [ ] PRIMARY KEY, INDEX, VIEW

> **Giải thích:** PostgreSQL constraints: NOT NULL, UNIQUE, PRIMARY KEY, FOREIGN KEY, CHECK (condition), EXCLUDE (exclusion constraint). INDEX không phải constraint.

## Câu 70

[TYPE: SELECT_RESULT]

Cho JSONB column:

```sql
CREATE TABLE configs (id SERIAL, data JSONB);
INSERT INTO configs (data) VALUES
    ('{"name": "app1", "settings": {"debug": true, "port": 8080}}'),
    ('{"name": "app2", "settings": {"debug": false, "port": 3000}}');

SELECT data->>'name' as name, data->'settings'->>'port' as port
FROM configs;
```

- [x] app1: 8080, app2: 3000
- [ ] Lỗi cú pháp
- [ ] {"name": "app1"}, {"name": "app2"}
- [ ] null, null

> **Giải thích:** `->>` trả về text, `->` trả về JSON object. `data->>'name'` → text "app1". `data->'settings'->>'port'` → "8080".

## Câu 71

[TYPE: FILL_BLANK]

Trong PostgreSQL, `EXPLAIN ANALYZE` dùng để xem `___` của câu query.

- [x] Execution plan và thời gian thực thi thực tế
- [ ] Cú pháp đúng hay sai
- [ ] Số lượng bảng
- [ ] Quyền truy cập

> **Giải thích:** EXPLAIN: show query plan (estimated). EXPLAIN ANALYZE: run query + show actual execution time, rows, loops. Dùng để tối ưu performance.

## Câu 72

[TYPE: SELECT_RESULT]

Cho bảng `employees` (câu 43):

```sql
SELECT dept,
       PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY salary) as median_salary
FROM employees
GROUP BY dept;
```

IT department median:

- [x] 1800
- [ ] 1500
- [ ] 1766.67
- [ ] 2000

> **Giải thích:** IT salaries: 1500, 1800, 2000. Median (50th percentile) = middle value = 1800. PERCENTILE_CONT interpolates.

## Câu 73

[TYPE: SELECT_RESULT]

Cho 3 bảng:

**authors:**

| id | name  |
|----|-------|
| 1  | An    |
| 2  | Bình  |

**books:**

| id | title  | author_id |
|----|--------|-----------|
| 1  | Book A | 1         |
| 2  | Book B | 1         |
| 3  | Book C | 2         |

**reviews:**

| id | book_id | rating |
|----|---------|--------|
| 1  | 1       | 5      |
| 2  | 1       | 4      |
| 3  | 2       | 3      |
| 4  | 3       | 5      |

```sql
SELECT a.name, ROUND(AVG(r.rating), 1) as avg_rating
FROM authors a
JOIN books b ON a.id = b.author_id
JOIN reviews r ON b.id = r.book_id
GROUP BY a.name;
```

- [x] An: 4.0, Bình: 5.0
- [ ] An: 4.5, Bình: 5.0
- [ ] An: 3.0, Bình: 5.0
- [ ] Lỗi cú pháp

> **Giải thích:** An's books: Book A (ratings 5,4), Book B (rating 3). AVG = (5+4+3)/3 = 4.0. Bình's books: Book C (rating 5). AVG = 5.0.

## Câu 74

[TYPE: MULTIPLE_CHOICE]

Đâu là loại JOIN hiếm gặp nhất?

- [ ] INNER JOIN
- [ ] LEFT JOIN
- [x] FULL OUTER JOIN
- [ ] CROSS JOIN

> **Giải thích:** FULL OUTER JOIN: giữ rows từ CẢ HAI bảng, NULL cho side không match. Ít dùng vì thường chỉ cần INNER hoặc LEFT.

## Câu 75

[TYPE: SELECT_RESULT]

Cho bảng `sales`:

| id | date       | amount |
|----|------------|--------|
| 1  | 2024-01-01 | 100    |
| 2  | 2024-01-02 | 200    |
| 3  | 2024-01-03 | 150    |
| 4  | 2024-01-04 | 300    |
| 5  | 2024-01-05 | 250    |

```sql
SELECT date, amount,
       AVG(amount) OVER (ORDER BY date ROWS BETWEEN 2 PRECEDING AND CURRENT ROW) as moving_avg
FROM sales;
```

Dòng 2024-01-03 moving_avg:

- [x] 150.0
- [ ] 200.0
- [ ] 100.0
- [ ] 150

> **Giải thích:** Tại 01-03: 2 PRECEDING = 01-01(100), 01-02(200), CURRENT = 01-03(150). AVG = (100+200+150)/3 = 150.0.

## Câu 76

[TYPE: TRUE_FALSE]

Mệnh đề: "Subquery trong FROM clause được gọi là derived table."

- [x] Đúng
- [ ] Sai

> **Giải thích:** `SELECT * FROM (SELECT ... ) AS derived_table`. Derived table = inline view = subquery trong FROM. Phải có alias.

## Câu 77

[TYPE: SELECT_RESULT]

Cho bảng `employees` (câu 43):

```sql
SELECT name, salary,
       FIRST_VALUE(name) OVER (ORDER BY salary DESC) as highest_paid,
       LAST_VALUE(name) OVER (ORDER BY salary DESC
           ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING) as lowest_paid
FROM employees;
```

Mỗi dòng highest_paid và lowest_paid:

- [x] highest_paid: Dung, lowest_paid: Cường (cho mọi dòng)
- [ ] highest_paid thay đổi mỗi dòng
- [ ] Lỗi cú pháp
- [ ] highest_paid: An, lowest_paid: Bình

> **Giải thích:** FIRST_VALUE: Dung (highest salary 2000). LAST_VALUE cần frame UNBOUNDED FOLLOWING để lấy giá trị cuối → Cường (lowest 1200).

## Câu 78

[TYPE: SELECT_RESULT]

```sql
SELECT
    unnest(ARRAY[1, 2, 3]) as num,
    unnest(ARRAY['a', 'b', 'c']) as letter;
```

Trả về bao nhiêu rows?

- [x] 3
- [ ] 9
- [ ] 1
- [ ] 6

> **Giải thích:** UNNEST: mở rộng array thành rows. 2 arrays cùng kích thước 3 → 3 rows: (1,a), (2,b), (3,c).

## Câu 79

[TYPE: MULTIPLE_CHOICE]

Đâu là cách tối ưu query performance?

- [x] Thêm index phù hợp, tránh SELECT *, dùng EXPLAIN ANALYZE, tránh N+1 query
- [ ] Thêm index cho mọi cột
- [ ] Luôn dùng SELECT *
- [ ] Tránh dùng JOIN

> **Giải thích:** Best practices: index trên WHERE/JOIN columns, SELECT chỉ cột cần, EXPLAIN ANALYZE, batch queries, avoid N+1, partition large tables.

## Câu 80

[TYPE: SELECT_RESULT]

Cho bảng `orders`:

| id | customer_id | status    | amount |
|----|-------------|-----------|--------|
| 1  | 1           | completed | 500    |
| 2  | 1           | pending   | 300    |
| 3  | 2           | completed | 700    |
| 4  | 2           | cancelled | 200    |
| 5  | 3           | completed | 400    |

```sql
SELECT customer_id,
       COUNT(*) FILTER (WHERE status = 'completed') as completed_cnt,
       SUM(amount) FILTER (WHERE status = 'completed') as completed_total
FROM orders
GROUP BY customer_id;
```

Customer 2:

- [x] completed_cnt: 1, completed_total: 700
- [ ] completed_cnt: 2, completed_total: 900
- [ ] completed_cnt: 1, completed_total: 200
- [ ] Lỗi cú pháp

> **Giải thích:** Customer 2: completed(700), cancelled(200). FILTER WHERE completed: count=1, sum=700.

## Câu 81

[TYPE: FILL_BLANK]

Constraint nào đảm bảo giá trị trong cột phải tham chiếu đến giá trị tồn tại trong bảng khác? Đó là `___` KEY.

- [x] FOREIGN
- [ ] PRIMARY
- [ ] UNIQUE
- [ ] CHECK

> **Giải thích:** FOREIGN KEY: referential integrity. `REFERENCES parent_table(column)`. Đảm bảo child row có parent row tương ứng.

## Câu 82

[TYPE: SELECT_RESULT]

Cho Recursive CTE:

```sql
WITH RECURSIVE countdown AS (
    SELECT 5 as n
    UNION ALL
    SELECT n - 1 FROM countdown WHERE n > 1
)
SELECT * FROM countdown;
```

- [x] 5, 4, 3, 2, 1
- [ ] 5
- [ ] 5, 4, 3, 2, 1, 0
- [ ] Lỗi: infinite loop

> **Giải thích:** Recursive CTE: base case n=5. Recursive: n-1 WHERE n>1. 5→4→3→2→1 (n=1, n>1 false → stop). 5 rows.

## Câu 83

[TYPE: SELECT_RESULT]

Cho bảng `employees` (câu 43):

```sql
SELECT dept, name, salary,
       salary * 100.0 / SUM(salary) OVER (PARTITION BY dept) as pct_of_dept
FROM employees WHERE dept = 'IT';
```

An's percentage:

- [x] ~28.3%
- [ ] ~33.3%
- [ ] ~50%
- [ ] 100%

> **Giải thích:** IT total salary = 1500+1800+2000 = 5300. An: 1500/5300×100 ≈ 28.3%. Bình: 34.0%. Dung: 37.7%.

## Câu 84

[TYPE: MULTIPLE_CHOICE]

Partitioning trong PostgreSQL giúp gì?

- [x] Chia table lớn thành partitions nhỏ hơn, cải thiện query performance và maintenance
- [ ] Encrypt data
- [ ] Backup data
- [ ] Thay thế indexing

> **Giải thích:** Partitioning: range (date), list (category), hash. Partition pruning: query chỉ scan partitions liên quan. Cải thiện performance cho big tables.

## Câu 85

[TYPE: SELECT_RESULT]

Cho bảng `events` (câu 47). Tính conversion rate:

```sql
WITH signups AS (SELECT COUNT(DISTINCT user_id) as cnt FROM events WHERE event_type = 'signup'),
     purchases AS (SELECT COUNT(DISTINCT user_id) as cnt FROM events WHERE event_type = 'purchase')
SELECT ROUND(purchases.cnt * 100.0 / signups.cnt, 1) as conversion_rate
FROM signups, purchases;
```

- [x] 66.7%
- [ ] 100%
- [ ] 33.3%
- [ ] 50%

> **Giải thích:** Signups: 3 users (1,2,3). Purchases: 2 users (1,2). Conversion = 2/3 × 100 = 66.7%.

## Câu 86

[TYPE: TRUE_FALSE]

Mệnh đề: "PostgreSQL hỗ trợ Window Functions."

- [x] Đúng
- [ ] Sai

> **Giải thích:** PostgreSQL hỗ trợ đầy đủ: ROW_NUMBER, RANK, DENSE_RANK, LAG, LEAD, FIRST_VALUE, LAST_VALUE, NTILE, SUM/AVG/COUNT OVER.

## Câu 87

[TYPE: SELECT_RESULT]

Cho bảng `inventory` (câu 52):

```sql
SELECT product,
       SUM(quantity) as total,
       CASE WHEN SUM(quantity) > 100 THEN 'High'
            WHEN SUM(quantity) > 50 THEN 'Medium'
            ELSE 'Low'
       END as stock_level
FROM inventory
GROUP BY product;
```

- [x] A: 150 High, B: 280 High, C: 30 Low
- [ ] A: Medium, B: High, C: Low
- [ ] A: High, B: High, C: Medium
- [ ] Lỗi cú pháp

> **Giải thích:** A: 150>100→High. B: 280>100→High. C: 30≤50→Low. CASE WHEN trong aggregate results.

## Câu 88

[TYPE: SELECT_RESULT]

```sql
SELECT
    DATE_TRUNC('month', '2024-03-15'::date) as truncated,
    DATE_TRUNC('month', '2024-03-15'::date) + INTERVAL '1 month' - INTERVAL '1 day' as last_day;
```

- [x] truncated: 2024-03-01, last_day: 2024-03-31
- [ ] truncated: 2024-03-15, last_day: 2024-03-31
- [ ] truncated: 2024-03-01, last_day: 2024-04-01
- [ ] Lỗi cú pháp

> **Giải thích:** DATE_TRUNC('month') → đầu tháng 2024-03-01. +1 month → 2024-04-01. -1 day → 2024-03-31 (last day of month).

## Câu 89

[TYPE: MULTIPLE_CHOICE]

Đâu là cách handle NULL trong aggregate functions?

- [x] Aggregate functions (SUM, AVG, COUNT column) tự động bỏ qua NULL
- [ ] NULL được tính là 0
- [ ] Query báo lỗi
- [ ] NULL được tính là empty string

> **Giải thích:** SUM, AVG, MIN, MAX bỏ qua NULL. COUNT(*) đếm tất cả rows, COUNT(column) bỏ qua NULL. Dùng COALESCE để thay NULL.

## Câu 90

[TYPE: SELECT_RESULT]

Cho bảng `employees` (câu 43):

```sql
SELECT dept, name, salary,
       SUM(salary) OVER (PARTITION BY dept ORDER BY salary
                         ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) as cumulative
FROM employees;
```

IT department, dòng Bình (salary=1800):

- [x] cumulative: 3300
- [ ] cumulative: 1800
- [ ] cumulative: 5300
- [ ] cumulative: 1500

> **Giải thích:** IT ORDER BY salary: An(1500), Bình(1800), Dung(2000). Cumulative tại Bình = 1500 + 1800 = 3300.

## Câu 91

[TYPE: FILL_BLANK]

Trong PostgreSQL, lệnh `VACUUM` dùng để `___`.

- [x] Giải phóng space từ dead tuples (rows đã delete/update)
- [ ] Xóa tất cả data
- [ ] Tạo backup
- [ ] Tối ưu index

> **Giải thích:** VACUUM: clean up dead tuples (MVCC). VACUUM FULL: reclaim disk space. AUTOVACUUM: automatic in PostgreSQL. Quan trọng cho performance.

## Câu 92

[TYPE: SELECT_RESULT]

Cho bảng `orders` (câu 80):

```sql
SELECT status, SUM(amount) as total,
       ROUND(SUM(amount) * 100.0 / SUM(SUM(amount)) OVER(), 1) as percentage
FROM orders
GROUP BY status
ORDER BY total DESC;
```

- [x] completed: 1600 (76.2%), pending: 300 (14.3%), cancelled: 200 (9.5%)
- [ ] completed: 100%, pending: 0%, cancelled: 0%
- [ ] Lỗi cú pháp
- [ ] completed: 1600, pending: 300, cancelled: 200

> **Giải thích:** Total=2100. completed: 1600/2100=76.2%. pending: 300/2100=14.3%. cancelled: 200/2100=9.5%.

## Câu 93

[TYPE: SELECT_RESULT]

Cho bảng `products`:

| id | name | tags                    |
|----|------|-------------------------|
| 1  | A    | {electronics, gadget}   |
| 2  | B    | {clothing, fashion}     |
| 3  | C    | {electronics, home}     |

```sql
SELECT name FROM products WHERE 'electronics' = ANY(tags);
```

- [x] A, C
- [ ] A
- [ ] A, B, C
- [ ] Lỗi cú pháp

> **Giải thích:** ANY(tags): kiểm tra 'electronics' có trong array tags không. A: {electronics, gadget} ✓. C: {electronics, home} ✓. PostgreSQL array feature.

## Câu 94

[TYPE: MULTIPLE_CHOICE]

Deadlock trong database xảy ra khi nào?

- [x] 2+ transactions chờ nhau giải phóng lock, tạo vòng chờ
- [ ] Khi có quá nhiều queries
- [ ] Khi disk đầy
- [ ] Khi network chậm

> **Giải thích:** Deadlock: T1 giữ lock A, chờ B. T2 giữ lock B, chờ A. PostgreSQL tự detect và abort 1 transaction. Tránh bằng consistent lock ordering.

## Câu 95

[TYPE: SELECT_RESULT]

Cho bảng `users` (câu 55):

```sql
SELECT
    DATE_TRUNC('month', created_at) as month,
    COUNT(*) as new_users,
    SUM(COUNT(*)) OVER (ORDER BY DATE_TRUNC('month', created_at)) as cumulative_users
FROM users
GROUP BY DATE_TRUNC('month', created_at);
```

Tháng 2:

- [x] new_users: 2, cumulative_users: 4
- [ ] new_users: 2, cumulative_users: 2
- [ ] new_users: 3, cumulative_users: 5
- [ ] Lỗi cú pháp

> **Giải thích:** Tháng 1: 2 users (An, Bình). Tháng 2: 2 users (Cường, Dung). Tháng 3: 1 (Em). Cumulative tháng 2: 2+2=4.

## Câu 96

[TYPE: TRUE_FALSE]

Mệnh đề: "CTEs (WITH clause) luôn tạo temporary table."

- [ ] Đúng
- [x] Sai

> **Giải thích:** PostgreSQL 12+: CTE có thể được inline (không tạo temp table) trừ khi dùng MATERIALIZED hint. Optimizer có thể optimize CTE như subquery.

## Câu 97

[TYPE: SELECT_RESULT]

```sql
SELECT
    'PostgreSQL' LIKE 'Post%' as test1,
    'PostgreSQL' ILIKE 'post%' as test2,
    'PostgreSQL' SIMILAR TO 'Post(gre)?SQL' as test3;
```

- [x] test1: true, test2: true, test3: true
- [ ] test1: true, test2: false, test3: false
- [ ] test1: true, test2: true, test3: false
- [ ] Tất cả true

> **Giải thích:** LIKE: case-sensitive. ILIKE: case-insensitive. SIMILAR TO: SQL regex. 'Post%' matches 'PostgreSQL'. 'post%' matches case-insensitive. 'Post(gre)?SQL' matches.

## Câu 98

[TYPE: SELECT_RESULT]

Cho bảng `employees` (câu 43):

```sql
SELECT dept, name, salary,
       salary - AVG(salary) OVER (PARTITION BY dept) as diff_from_avg
FROM employees;
```

An (IT, 1500) diff_from_avg:

- [x] ~-266.67
- [ ] 0
- [ ] ~266.67
- [ ] 1500

> **Giải thích:** IT AVG = (1500+1800+2000)/3 = 1766.67. An: 1500 - 1766.67 = -266.67. Dưới trung bình phòng ban.

## Câu 99

[TYPE: MULTIPLE_CHOICE]

Đâu là ưu điểm của Prepared Statements?

- [x] Tránh SQL Injection, tối ưu query plan reuse, type safety
- [ ] Chỉ nhanh hơn
- [ ] Chỉ bảo mật hơn
- [ ] Không có ưu điểm so với raw SQL

> **Giải thích:** Prepared Statements: prevent SQL injection (tách logic và data), cached plan, type checking. Best practice cho mọi application queries.

## Câu 100

[TYPE: SELECT_RESULT]

Cho bảng `employees` (câu 43):

```sql
SELECT name, salary, dept,
       CASE
           WHEN salary >= (SELECT PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY salary) FROM employees WHERE dept = e.dept) THEN 'Top 25%'
           WHEN salary >= (SELECT PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY salary) FROM employees WHERE dept = e.dept) THEN 'Top 50%'
           ELSE 'Bottom 50%'
       END as tier
FROM employees e
WHERE dept = 'IT'
ORDER BY salary DESC;
```

Dung (2000) tier:

- [x] Top 25%
- [ ] Top 50%
- [ ] Bottom 50%
- [ ] Lỗi cú pháp

> **Giải thích:** IT: 1500, 1800, 2000. P75 = 1900. P50 = 1800. Dung(2000) >= 1900 → Top 25%. Bình(1800) >= 1800 → Top 50%. An(1500) < 1800 → Bottom 50%.
