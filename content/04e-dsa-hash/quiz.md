# Quiz - Hash Table

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Thao tác nào có O(1) trên HashMap trong trường hợp trung bình?

- [x] get(key) và put(key, value)
- [ ] Chỉ get(key)
- [ ] values() (lấy tất cả values)
- [ ] Sắp xếp các keys

> **Giải thích:** HashMap: get/put trung bình O(1) nhờ hash function. Worst case O(n) khi collision.

## Câu 2

[TYPE: MULTIPLE_CHOICE]

Khi hai key khác nhau có cùng hashCode, hiện tượng này gọi là gì?

- [ ] Overflow
- [x] Collision
- [ ] Deadlock
- [ ] Fragmentation

> **Giải thích:** Collision xảy ra khi hash function trả về cùng index cho hai key khác nhau. Java HashMap xử lý bằng chaining (linked list/tree).

## Câu 3

[TYPE: TRUE_FALSE]

Mệnh đề: "HashMap trong Java đảm bảo thứ tự insert."

- [ ] Đúng
- [x] Sai

> **Giải thích:** HashMap không đảm bảo thứ tự. LinkedHashMap giữ thứ tự insert. TreeMap sắp xếp theo key.

## Câu 4

[TYPE: SELECT_RESULT]

Cho HashMap: put("a",1), put("b",2), put("a",3). Kết quả get("a") là gì?

- [ ] 1
- [ ] 2
- [x] 3
- [ ] null

> **Giải thích:** put() với key đã tồn tại sẽ ghi đè value cũ. "a" ban đầu = 1, sau đó bị ghi đè thành 3.

## Câu 5

[TYPE: MULTIPLE_CHOICE]

Java 8+ cải thiện HashMap khi nhiều collision bằng cách nào?

- [ ] Tăng kích thước mảng
- [ ] Dùng Open Addressing
- [x] Chuyển bucket từ LinkedList sang Red-Black Tree khi >8 entries
- [ ] Dùng Double Hashing

> **Giải thích:** Khi bucket có hơn 8 entries, Java chuyển từ linked list sang red-black tree → O(log n) thay vì O(n).

## Câu 6

[TYPE: FILL_BLANK]

Cấu trúc dữ liệu kết hợp HashMap và Doubly Linked List để implement cache với get/put O(1) gọi là `___`.

- [x] LRU Cache
- [ ] LFU Cache
- [ ] Hash Map
- [ ] Trie

> **Giải thích:** LRU Cache (Least Recently Used) dùng HashMap cho O(1) lookup và Doubly Linked List cho O(1) move/remove.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

Khi override `equals()` trong Java, bắt buộc phải làm gì?

- [ ] Override `toString()`
- [x] Override `hashCode()`
- [ ] Override `compareTo()`
- [ ] Không cần làm gì thêm

> **Giải thích:** Quy tắc: nếu a.equals(b) = true thì a.hashCode() == b.hashCode(). Vi phạm sẽ làm HashMap/HashSet hoạt động sai.

## Câu 8

[TYPE: MULTIPLE_CHOICE]

TreeMap khác HashMap ở điểm nào?

- [ ] TreeMap nhanh hơn
- [x] TreeMap sắp xếp key tự động, các thao tác O(log n)
- [ ] TreeMap dùng hash function
- [ ] TreeMap không cho phép null key

> **Giải thích:** TreeMap dùng Red-Black Tree, key được sắp xếp tự động. Thao tác O(log n). Hỗ trợ range query (floorKey, ceilingKey).
