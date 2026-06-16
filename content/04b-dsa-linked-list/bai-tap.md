# Linked List - Bài Tập

## Bài 1: Đảo Ngược Linked List
**Độ khó: Dễ**

Đảo ngược một singly linked list.

**Ví dụ:**
```
Input:  1 -> 2 -> 3 -> 4 -> 5 -> null
Output: 5 -> 4 -> 3 -> 2 -> 1 -> null

Input:  1 -> 2 -> null
Output: 2 -> 1 -> null

Input:  null
Output: null
```

**Yêu cầu:** Implement hàm `reverseList` để đảo ngược linked list.

```java
public static ListNode reverseList(ListNode head) {
    // TODO: Implement your solution here
}
```

**Độ phức tạp:**
- Time: O(n)
- Space: O(1) cho iterative, O(n) cho recursive

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | 1->2->3->4->5 | 5->4->3->2->1 |
| 2 | 1->2 | 2->1 |
| 3 | 1 | 1 |
| 4 | null | null |
| 5 | 1->2->3 | 3->2->1 |

### 🧪 Main Demo
```java
public class Main {

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static void main(String[] args) {
        // Test 1: 1->2->3->4->5
        ListNode h1 = buildList(new int[]{1,2,3,4,5});
        check(1, listToString(reverseList(h1)), "5->4->3->2->1");

        // Test 2: 1->2
        ListNode h2 = buildList(new int[]{1,2});
        check(2, listToString(reverseList(h2)), "2->1");

        // Test 3: 1
        ListNode h3 = buildList(new int[]{1});
        check(3, listToString(reverseList(h3)), "1");

        // Test 4: null
        check(4, listToString(reverseList(null)), "null");

        // Test 5: 1->2->3
        ListNode h5 = buildList(new int[]{1,2,3});
        check(5, listToString(reverseList(h5)), "3->2->1");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static ListNode buildList(int[] vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : vals) { cur.next = new ListNode(v); cur = cur.next; }
        return dummy.next;
    }

    static String listToString(ListNode head) {
        if (head == null) return "null";
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) sb.append("->");
            head = head.next;
        }
        return sb.toString();
    }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static ListNode reverseList(ListNode head) {
        // Code here ...
        return head;
    }
}
```

---

## Bài 2: Phát Hiện Cycle
**Độ khó: Dễ**

Kiểm tra xem linked list có chứa vòng lặp (cycle) hay không.

**Ví dụ:**
```
Input: 3 -> 2 -> 0 -> -4 -> (trỏ về node 2)
Output: true

Input: 1 -> 2 -> null
Output: false
```

**Yêu cầu:** Implement hàm `hasCycle`.

```java
public static boolean hasCycle(ListNode head) {
    // TODO: Implement your solution here
}
```

**Gợi ý:** Floyd's Tortoise and Hare algorithm.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | 3->2->0->-4 (cycle tại 2) | true |
| 2 | 1->2 (no cycle) | false |
| 3 | 1 (no cycle) | false |
| 4 | null | false |
| 5 | 1->2->3 (cycle tại 1) | true |

### 🧪 Main Demo
```java
public class Main {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        // Test 1: cycle at node index 1
        ListNode h1 = buildList(new int[]{3,2,0,-4});
        h1.next.next.next.next = h1.next; // -4 -> 2
        check(1, String.valueOf(hasCycle(h1)), "true");

        // Test 2: no cycle
        ListNode h2 = buildList(new int[]{1,2});
        check(2, String.valueOf(hasCycle(h2)), "false");

        // Test 3: single node no cycle
        ListNode h3 = buildList(new int[]{1});
        check(3, String.valueOf(hasCycle(h3)), "false");

        // Test 4: null
        check(4, String.valueOf(hasCycle(null)), "false");

        // Test 5: cycle at head
        ListNode h5 = buildList(new int[]{1,2,3});
        h5.next.next.next = h5; // 3 -> 1
        check(5, String.valueOf(hasCycle(h5)), "true");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static ListNode buildList(int[] vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : vals) { cur.next = new ListNode(v); cur = cur.next; }
        return dummy.next;
    }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static boolean hasCycle(ListNode head) {
        // Code here ...
        return false;
    }
}
```

---

## Bài 3: Merge Two Sorted Lists
**Độ khó: Dễ**

Gộp hai linked list đã sắp xếp thành một linked list mới cũng đã sắp xếp.

**Ví dụ:**
```
Input:  l1 = 1 -> 2 -> 4, l2 = 1 -> 3 -> 4
Output: 1 -> 1 -> 2 -> 3 -> 4 -> 4
```

**Yêu cầu:** Implement hàm `mergeTwoLists`.

```java
public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    // TODO: Implement your solution here
}
```

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | l1=[1,2,4], l2=[1,3,4] | 1->1->2->3->4->4 |
| 2 | l1=[], l2=[0] | 0 |
| 3 | l1=[], l2=[] | null |
| 4 | l1=[1], l2=[2] | 1->2 |
| 5 | l1=[5,10,15], l2=[2,3,20] | 2->3->5->10->15->20 |

### 🧪 Main Demo
```java
public class Main {

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static void main(String[] args) {
        check(1, listToString(mergeTwoLists(buildList(new int[]{1,2,4}), buildList(new int[]{1,3,4}))), "1->1->2->3->4->4");
        check(2, listToString(mergeTwoLists(null, buildList(new int[]{0}))), "0");
        check(3, listToString(mergeTwoLists(null, null)), "null");
        check(4, listToString(mergeTwoLists(buildList(new int[]{1}), buildList(new int[]{2}))), "1->2");
        check(5, listToString(mergeTwoLists(buildList(new int[]{5,10,15}), buildList(new int[]{2,3,20}))), "2->3->5->10->15->20");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static ListNode buildList(int[] vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : vals) { cur.next = new ListNode(v); cur = cur.next; }
        return dummy.next;
    }

    static String listToString(ListNode head) {
        if (head == null) return "null";
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) sb.append("->");
            head = head.next;
        }
        return sb.toString();
    }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // Code here ...
        return null;
    }
}
```

---

## Bài 4: Xóa Node Cách Cuối N Bước
**Độ khó: Trung bình**

Xóa node thứ n từ cuối linked list. Yêu cầu: duyệt chỉ 1 lần (one pass).

**Ví dụ:**
```
Input:  1 -> 2 -> 3 -> 4 -> 5, n = 2
Output: 1 -> 2 -> 3 -> 5
```

**Yêu cầu:** Implement hàm `removeNthFromEnd`.

```java
public static ListNode removeNthFromEnd(ListNode head, int n) {
    // TODO: Implement your solution here
}
```

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [1,2,3,4,5], n=2 | 1->2->3->5 |
| 2 | [1], n=1 | null |
| 3 | [1,2], n=1 | 1 |
| 4 | [1,2], n=2 | 2 |
| 5 | [1,2,3,4,5], n=5 | 2->3->4->5 |

### 🧪 Main Demo
```java
public class Main {

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static void main(String[] args) {
        check(1, listToString(removeNthFromEnd(buildList(new int[]{1,2,3,4,5}), 2)), "1->2->3->5");
        check(2, listToString(removeNthFromEnd(buildList(new int[]{1}), 1)), "null");
        check(3, listToString(removeNthFromEnd(buildList(new int[]{1,2}), 1)), "1");
        check(4, listToString(removeNthFromEnd(buildList(new int[]{1,2}), 2)), "2");
        check(5, listToString(removeNthFromEnd(buildList(new int[]{1,2,3,4,5}), 5)), "2->3->4->5");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static ListNode buildList(int[] vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : vals) { cur.next = new ListNode(v); cur = cur.next; }
        return dummy.next;
    }

    static String listToString(ListNode head) {
        if (head == null) return "null";
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) sb.append("->");
            head = head.next;
        }
        return sb.toString();
    }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        // Code here ...
        return head;
    }
}
```

---

## Bài 5: Palindrome Linked List
**Độ khó: Trung bình**

Kiểm tra linked list có phải palindrome (đọc xuôi ngược giống nhau) không. Yêu cầu O(n) time, O(1) space.

**Ví dụ:**
```
Input:  1 -> 2 -> 2 -> 1
Output: true

Input:  1 -> 2 -> 3
Output: false
```

**Yêu cầu:** Implement hàm `isPalindrome`.

**Gợi ý:** Tìm middle, reverse nửa sau, so sánh hai nửa.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | 1->2->2->1 | true |
| 2 | 1->2->3 | false |
| 3 | 1 | true |
| 4 | 1->2->3->2->1 | true |
| 5 | 1->2->3->4 | false |

### 🧪 Main Demo
```java
public class Main {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        check(1, String.valueOf(isPalindrome(buildList(new int[]{1,2,2,1}))), "true");
        check(2, String.valueOf(isPalindrome(buildList(new int[]{1,2,3}))), "false");
        check(3, String.valueOf(isPalindrome(buildList(new int[]{1}))), "true");
        check(4, String.valueOf(isPalindrome(buildList(new int[]{1,2,3,2,1}))), "true");
        check(5, String.valueOf(isPalindrome(buildList(new int[]{1,2,3,4}))), "false");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static ListNode buildList(int[] vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : vals) { cur.next = new ListNode(v); cur = cur.next; }
        return dummy.next;
    }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static boolean isPalindrome(ListNode head) {
        // Code here ...
        return false;
    }
}
```

---

## Bài 6: Reorder List
**Độ khó: Trung bình**

Cho linked list L₀ → L₁ → ... → Lₙ₋₁ → Lₙ, sắp xếp lại thành L₀ → Lₙ → L₁ → Lₙ₋₁ → L₂ → Lₙ₋₂ → ...

**Ví dụ:**
```
Input:  1 -> 2 -> 3 -> 4 -> 5
Output: 1 -> 5 -> 2 -> 4 -> 3
```

**Gợi ý:** (1) Tìm middle, (2) Reverse nửa sau, (3) Merge xen kẽ.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | 1->2->3->4->5 | 1->5->2->4->3 |
| 2 | 1->2->3->4 | 1->4->2->3 |
| 3 | 1 | 1 |
| 4 | 1->2 | 1->2 |
| 5 | 1->2->3 | 1->3->2 |

### 🧪 Main Demo
```java
public class Main {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        ListNode h1 = buildList(new int[]{1,2,3,4,5});
        reorderList(h1);
        check(1, listToString(h1), "1->5->2->4->3");

        ListNode h2 = buildList(new int[]{1,2,3,4});
        reorderList(h2);
        check(2, listToString(h2), "1->4->2->3");

        ListNode h3 = buildList(new int[]{1});
        reorderList(h3);
        check(3, listToString(h3), "1");

        ListNode h4 = buildList(new int[]{1,2});
        reorderList(h4);
        check(4, listToString(h4), "1->2");

        ListNode h5 = buildList(new int[]{1,2,3});
        reorderList(h5);
        check(5, listToString(h5), "1->3->2");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static ListNode buildList(int[] vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : vals) { cur.next = new ListNode(v); cur = cur.next; }
        return dummy.next;
    }

    static String listToString(ListNode head) {
        if (head == null) return "null";
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) sb.append("->");
            head = head.next;
        }
        return sb.toString();
    }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static void reorderList(ListNode head) {
        // Code here ...
    }
}
```

---

## Bài 7: Copy List with Random Pointer
**Độ khó: Trung bình**

Cho linked list với mỗi node có thêm pointer `random` trỏ đến node bất kỳ hoặc null. Tạo deep copy của list.

**Gợi ý:** HashMap hoặc kỹ thuật interleave.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [1,2,3] random:[3,1,null] | [1,2,3] random:[3,1,null] |
| 2 | [1] random:[1] | [1] random:[1] |
| 3 | null | null |
| 4 | [7,13,11] random:[null,7,13] | [7,13,11] random:[null,7,13] |
| 5 | [1,2] random:[2,1] | [1,2] random:[2,1] |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    static class Node {
        int val;
        Node next;
        Node random;
        Node(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        // Test 1
        Node n1 = new Node(1); Node n2 = new Node(2); Node n3 = new Node(3);
        n1.next = n2; n2.next = n3;
        n1.random = n3; n2.random = n1; n3.random = null;
        Node c1 = copyRandomList(n1);
        check(1, nodeToString(c1), "1(->3)->2(->1)->3(->null)");

        // Test 2
        Node s1 = new Node(1); s1.random = s1;
        Node c2 = copyRandomList(s1);
        check(2, nodeToString(c2), "1(->1)");

        // Test 3
        check(3, nodeToString(copyRandomList(null)), "null");

        // Test 4
        Node a = new Node(7); Node b = new Node(13); Node c = new Node(11);
        a.next = b; b.next = c;
        a.random = null; b.random = a; c.random = b;
        Node c4 = copyRandomList(a);
        check(4, nodeToString(c4), "7(->null)->13(->7)->11(->13)");

        // Test 5
        Node x = new Node(1); Node y = new Node(2);
        x.next = y; x.random = y; y.random = x;
        Node c5 = copyRandomList(x);
        check(5, nodeToString(c5), "1(->2)->2(->1)");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static String nodeToString(Node head) {
        if (head == null) return "null";
        StringBuilder sb = new StringBuilder();
        Node cur = head;
        while (cur != null) {
            sb.append(cur.val).append("(->").append(cur.random == null ? "null" : cur.random.val).append(")");
            if (cur.next != null) sb.append("->");
            cur = cur.next;
        }
        return sb.toString();
    }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static Node copyRandomList(Node head) {
        // Code here ...
        return null;
    }
}
```

---

## Bài 8: Reverse Nodes in k-Group
**Độ khó: Khó**

Đảo ngược linked list theo từng nhóm k node. Nếu số node còn lại ít hơn k, giữ nguyên.

**Ví dụ:**
```
Input:  1 -> 2 -> 3 -> 4 -> 5, k = 2
Output: 2 -> 1 -> 4 -> 3 -> 5

Input:  1 -> 2 -> 3 -> 4 -> 5, k = 3
Output: 3 -> 2 -> 1 -> 4 -> 5
```

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [1,2,3,4,5], k=2 | 2->1->4->3->5 |
| 2 | [1,2,3,4,5], k=3 | 3->2->1->4->5 |
| 3 | [1,2,3], k=1 | 1->2->3 |
| 4 | [1], k=2 | 1 |
| 5 | [1,2,3,4], k=2 | 2->1->4->3 |

### 🧪 Main Demo
```java
public class Main {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        check(1, listToString(reverseKGroup(buildList(new int[]{1,2,3,4,5}), 2)), "2->1->4->3->5");
        check(2, listToString(reverseKGroup(buildList(new int[]{1,2,3,4,5}), 3)), "3->2->1->4->5");
        check(3, listToString(reverseKGroup(buildList(new int[]{1,2,3}), 1)), "1->2->3");
        check(4, listToString(reverseKGroup(buildList(new int[]{1}), 2)), "1");
        check(5, listToString(reverseKGroup(buildList(new int[]{1,2,3,4}), 2)), "2->1->4->3");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static ListNode buildList(int[] vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : vals) { cur.next = new ListNode(v); cur = cur.next; }
        return dummy.next;
    }

    static String listToString(ListNode head) {
        if (head == null) return "null";
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) sb.append("->");
            head = head.next;
        }
        return sb.toString();
    }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        // Code here ...
        return head;
    }
}
```
