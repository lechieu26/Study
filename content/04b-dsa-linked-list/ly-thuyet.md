# Linked List (Danh sách liên kết)

## 1. Khái niệm

**Linked List** là cấu trúc dữ liệu gồm các **node** (nút) liên kết với nhau qua **pointer** (con trỏ). Mỗi node chứa **dữ liệu** và **tham chiếu** đến node tiếp theo.

```
Singly Linked List:
head → [10|→] → [20|→] → [30|→] → [40|→] → null

Doubly Linked List:
null ← [←|10|→] ⇄ [←|20|→] ⇄ [←|30|→] ⇄ [←|40|→] → null
```

**Đặc điểm chính:**
- **Không cần vùng nhớ liên tiếp** — các node nằm rải rác trong bộ nhớ
- **Kích thước linh hoạt** — thêm/xóa dễ dàng, không cần resize
- **Thêm/xóa ở đầu O(1)** — chỉ cần thay đổi pointer
- **Không hỗ trợ random access** — phải duyệt tuần tự O(n)

## 2. Cài đặt Node trong Java

```java
// Singly Linked List Node
class ListNode {
    int val;
    ListNode next;
    ListNode(int val) {
        this.val = val;
        this.next = null;
    }
}

// Doubly Linked List Node
class DoublyNode {
    int val;
    DoublyNode prev, next;
    DoublyNode(int val) {
        this.val = val;
        this.prev = null;
        this.next = null;
    }
}
```

## 3. Các thao tác cơ bản

### 3.1 Thêm phần tử

```java
// Thêm vào đầu — O(1)
public ListNode addFirst(ListNode head, int val) {
    ListNode newNode = new ListNode(val);
    newNode.next = head;
    return newNode;  // newNode trở thành head mới
}

// Thêm vào cuối — O(n) (hoặc O(1) nếu giữ tail pointer)
public void addLast(ListNode head, int val) {
    ListNode newNode = new ListNode(val);
    if (head == null) { head = newNode; return; }
    ListNode curr = head;
    while (curr.next != null) curr = curr.next;
    curr.next = newNode;
}

// Thêm vào vị trí index — O(n)
public ListNode addAtIndex(ListNode head, int index, int val) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode prev = dummy;
    for (int i = 0; i < index; i++) {
        if (prev.next == null) return head;  // Index vượt quá
        prev = prev.next;
    }
    ListNode newNode = new ListNode(val);
    newNode.next = prev.next;
    prev.next = newNode;
    return dummy.next;
}
```

### 3.2 Xóa phần tử

```java
// Xóa đầu — O(1)
public ListNode removeFirst(ListNode head) {
    if (head == null) return null;
    return head.next;
}

// Xóa node có giá trị val — O(n)
public ListNode removeByValue(ListNode head, int val) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode prev = dummy;
    while (prev.next != null) {
        if (prev.next.val == val) {
            prev.next = prev.next.next;  // Bỏ qua node cần xóa
        } else {
            prev = prev.next;
        }
    }
    return dummy.next;
}
```

> **Dummy Node (Sentinel):** Kỹ thuật tạo node giả trước head để đơn giản hóa xử lý edge cases (xóa head, thêm trước head).

### 3.3 Duyệt danh sách

```java
// Duyệt và in tất cả phần tử
public void traverse(ListNode head) {
    ListNode curr = head;
    while (curr != null) {
        System.out.print(curr.val + " → ");
        curr = curr.next;
    }
    System.out.println("null");
}

// Tìm phần tử — O(n)
public boolean contains(ListNode head, int val) {
    ListNode curr = head;
    while (curr != null) {
        if (curr.val == val) return true;
        curr = curr.next;
    }
    return false;
}
```

## 4. So sánh các loại Linked List

| Thao tác | Singly Linked | Doubly Linked | Circular |
|----------|---------------|---------------|----------|
| Truy cập index | O(n) | O(n) | O(n) |
| Thêm đầu | O(1) | O(1) | O(1) |
| Thêm cuối | O(n) hoặc O(1)* | O(1) | O(1) |
| Xóa đầu | O(1) | O(1) | O(1) |
| Xóa node biết trước | O(n)** | O(1) | O(n)** |
| Duyệt ngược | Không hỗ trợ | O(n) | Không |
| Bộ nhớ/node | data + next | data + prev + next | data + next |

> (*) Nếu giữ tail pointer, (**) Singly list cần tìm node trước để xóa

## 5. Các kỹ thuật quan trọng

### 5.1 Đảo ngược Linked List (Reverse)

```java
// Iterative — O(n) time, O(1) space
public ListNode reverseList(ListNode head) {
    ListNode prev = null, curr = head;
    while (curr != null) {
        ListNode next = curr.next;  // Lưu node tiếp theo
        curr.next = prev;           // Đảo chiều pointer
        prev = curr;                // Tiến prev
        curr = next;                // Tiến curr
    }
    return prev;  // prev là head mới
}

// Recursive — O(n) time, O(n) space (call stack)
public ListNode reverseListRecursive(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode newHead = reverseListRecursive(head.next);
    head.next.next = head;  // Node tiếp theo trỏ ngược về head
    head.next = null;        // Head trỏ ra null (trở thành tail)
    return newHead;
}
```

### 5.2 Fast & Slow Pointers (Tortoise & Hare)

Kỹ thuật dùng 2 con trỏ di chuyển với tốc độ khác nhau.

```java
// Tìm node giữa — slow đi 1 bước, fast đi 2 bước
public ListNode findMiddle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    return slow;  // slow dừng ở giữa
}
// 1 → 2 → 3 → 4 → 5 → null
//              ↑ slow (middle)

// Phát hiện Cycle (vòng lặp)
public boolean hasCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) return true;  // Gặp nhau → có cycle
    }
    return false;
}

// Tìm điểm bắt đầu cycle
public ListNode detectCycleStart(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) {
            // Đặt slow2 từ head, cả hai đi cùng tốc độ
            ListNode slow2 = head;
            while (slow != slow2) {
                slow = slow.next;
                slow2 = slow2.next;
            }
            return slow;  // Điểm bắt đầu cycle
        }
    }
    return null;
}
```

### 5.3 Merge Two Sorted Lists

```java
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode curr = dummy;

    while (l1 != null && l2 != null) {
        if (l1.val <= l2.val) {
            curr.next = l1;
            l1 = l1.next;
        } else {
            curr.next = l2;
            l2 = l2.next;
        }
        curr = curr.next;
    }
    curr.next = (l1 != null) ? l1 : l2;
    return dummy.next;
}
```

### 5.4 Xóa node cách cuối n bước

```java
// Two pass: Đếm length → xóa node (length - n)
// One pass: Dùng 2 con trỏ cách nhau n bước
public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode fast = dummy, slow = dummy;

    // fast đi trước n + 1 bước
    for (int i = 0; i <= n; i++) fast = fast.next;

    // Cả hai đi cùng tốc độ đến khi fast = null
    while (fast != null) {
        fast = fast.next;
        slow = slow.next;
    }
    slow.next = slow.next.next;  // Xóa node
    return dummy.next;
}
```

## 6. Java LinkedList Class

```java
LinkedList<Integer> list = new LinkedList<>();
list.addFirst(1);      // O(1)
list.addLast(2);       // O(1)
list.add(1, 5);        // O(n) — thêm tại index
list.getFirst();       // O(1)
list.getLast();         // O(1)
list.get(2);           // O(n) — random access chậm!
list.removeFirst();    // O(1)
list.removeLast();     // O(1)
list.size();

// LinkedList implement cả List, Deque → có thể dùng như Stack/Queue
// Nhưng ArrayDeque thường tốt hơn cho Stack/Queue
```

## 7. So sánh Array vs Linked List

| Tiêu chí | Array / ArrayList | Linked List |
|---------|-------------------|-------------|
| Truy cập ngẫu nhiên | O(1) ✓ | O(n) ✗ |
| Thêm/xóa đầu | O(n) | O(1) ✓ |
| Thêm/xóa giữa | O(n) | O(1)* nếu biết node |
| Bộ nhớ | Liên tiếp, cache-friendly | Phân tán, overhead pointer |
| Cache performance | Tốt (locality) | Kém (random access memory) |
| Khi nào dùng | Truy cập nhiều, ít thay đổi | Thêm/xóa nhiều, ít truy cập |

> **Thực tế:** ArrayList thường nhanh hơn LinkedList trong hầu hết trường hợp nhờ cache locality. Chỉ dùng LinkedList khi cần O(1) insert/delete ở đầu hoặc khi implement Stack/Queue/Deque.

## 8. Khi nào dùng Linked List?

| Dùng khi | Không nên dùng khi |
|---------|-------------------|
| Thêm/xóa ở đầu rất nhiều | Cần random access thường xuyên |
| Implement Stack, Queue, Deque | Dữ liệu nhỏ, cần hiệu suất cache |
| Kích thước thay đổi liên tục | Cần tìm kiếm nhanh theo index |
| Merge sort trên linked list | Dữ liệu cần sorted → dùng Tree/Array |

> **Phỏng vấn thường hỏi:** Reverse Linked List, Detect Cycle, Find Middle, Merge Two Sorted Lists, Remove Nth From End, Palindrome Linked List, Intersection of Two Lists.
