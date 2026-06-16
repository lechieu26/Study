# Linked List - Đáp Án Chi Tiết

## Bài 1: Đảo Ngược Linked List

### Cách 1: Iterative O(n) time, O(1) space
```java
public class ReverseLinkedList {
    public static ListNode reverseIterative(ListNode head) {
        ListNode prev = null, current = head;
        while (current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        return prev;
    }
}
```

### Cách 2: Recursive O(n) time, O(n) space
```java
public class ReverseLinkedList_Recursive {
    public static ListNode reverseRecursive(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode newHead = reverseRecursive(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }
}
```

### Cách 3: Stack
```java
public class ReverseLinkedList_Stack {
    public static ListNode reverseWithStack(ListNode head) {
        if (head == null) return null;
        Deque<ListNode> stack = new ArrayDeque<>();
        ListNode current = head;
        while (current != null) { stack.push(current); current = current.next; }
        ListNode newHead = stack.pop();
        current = newHead;
        while (!stack.isEmpty()) { current.next = stack.pop(); current = current.next; }
        current.next = null;
        return newHead;
    }
}
```

---

## Bài 2: Phát Hiện Cycle

### Cách 1: Floyd's Algorithm O(n) time, O(1) space
```java
public class LinkedListCycle {
    public static boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }
}
```

### Cách 2: HashSet O(n) time, O(n) space
```java
public class LinkedListCycle_HashSet {
    public static boolean hasCycle(ListNode head) {
        Set<ListNode> visited = new HashSet<>();
        ListNode curr = head;
        while (curr != null) {
            if (!visited.add(curr)) return true;
            curr = curr.next;
        }
        return false;
    }
}
```

---

## Bài 3: Merge Two Sorted Lists

### Cách 1: Iterative
```java
public class MergeTwoLists {
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) { curr.next = l1; l1 = l1.next; }
            else { curr.next = l2; l2 = l2.next; }
            curr = curr.next;
        }
        curr.next = (l1 != null) ? l1 : l2;
        return dummy.next;
    }
}
```

### Cách 2: Recursive
```java
public class MergeTwoLists_Recursive {
    public static ListNode merge(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        if (l1.val <= l2.val) { l1.next = merge(l1.next, l2); return l1; }
        else { l2.next = merge(l1, l2.next); return l2; }
    }
}
```

---

## Bài 4: Xóa Node Cách Cuối N Bước

### Cách 1: Two Pointers (One Pass)
```java
public class RemoveNthFromEnd {
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode fast = dummy, slow = dummy;
        for (int i = 0; i <= n; i++) fast = fast.next;
        while (fast != null) { fast = fast.next; slow = slow.next; }
        slow.next = slow.next.next;
        return dummy.next;
    }
}
// fast đi trước slow đúng n+1 bước
// khi fast đến null, slow ở ngay trước node cần xóa
```

---

## Bài 5: Palindrome Linked List

### Cách 1: Reverse nửa sau O(n) time, O(1) space
```java
public class PalindromeLinkedList {
    public static boolean isPalindrome(ListNode head) {
        // Tìm middle
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // Reverse nửa sau
        ListNode prev = null;
        while (slow != null) {
            ListNode next = slow.next;
            slow.next = prev;
            prev = slow;
            slow = next;
        }
        // So sánh hai nửa
        ListNode left = head, right = prev;
        while (right != null) {
            if (left.val != right.val) return false;
            left = left.next;
            right = right.next;
        }
        return true;
    }
}
```

---

## Bài 6: Reorder List

### Cách 1: Find Middle + Reverse + Merge
```java
public class ReorderList {
    public static void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        // 1. Tìm middle
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Reverse nửa sau
        ListNode second = slow.next;
        slow.next = null;
        ListNode prev = null;
        while (second != null) {
            ListNode next = second.next;
            second.next = prev;
            prev = second;
            second = next;
        }

        // 3. Merge xen kẽ
        ListNode first = head;
        second = prev;
        while (second != null) {
            ListNode tmp1 = first.next, tmp2 = second.next;
            first.next = second;
            second.next = tmp1;
            first = tmp1;
            second = tmp2;
        }
    }
}
// 1→2→3→4→5 → middle=3, second=4→5
// Reverse: 5→4
// Merge: 1→5→2→4→3
```

---

## Bài 7: Copy List with Random Pointer

### Cách 1: HashMap O(n) time, O(n) space
```java
public class CopyRandomList {
    public static Node copyRandomList(Node head) {
        if (head == null) return null;
        Map<Node, Node> map = new HashMap<>();
        // Pass 1: Tạo bản sao tất cả node
        Node curr = head;
        while (curr != null) {
            map.put(curr, new Node(curr.val));
            curr = curr.next;
        }
        // Pass 2: Gán next và random
        curr = head;
        while (curr != null) {
            map.get(curr).next = map.get(curr.next);
            map.get(curr).random = map.get(curr.random);
            curr = curr.next;
        }
        return map.get(head);
    }
}
```

### Cách 2: Interleave O(n) time, O(1) space
```java
public class CopyRandomList_Interleave {
    public static Node copyRandomList(Node head) {
        if (head == null) return null;
        // Bước 1: Chèn bản sao xen kẽ: A→A'→B→B'→C→C'
        Node curr = head;
        while (curr != null) {
            Node copy = new Node(curr.val);
            copy.next = curr.next;
            curr.next = copy;
            curr = copy.next;
        }
        // Bước 2: Gán random cho bản sao
        curr = head;
        while (curr != null) {
            if (curr.random != null)
                curr.next.random = curr.random.next;
            curr = curr.next.next;
        }
        // Bước 3: Tách hai list
        Node dummy = new Node(0);
        Node copyCurr = dummy;
        curr = head;
        while (curr != null) {
            copyCurr.next = curr.next;
            curr.next = curr.next.next;
            curr = curr.next;
            copyCurr = copyCurr.next;
        }
        return dummy.next;
    }
}
```

---

## Bài 8: Reverse Nodes in k-Group

### Cách 1: Iterative
```java
public class ReverseKGroup {
    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prevGroup = dummy;

        while (true) {
            // Kiểm tra còn đủ k node không
            ListNode kth = prevGroup;
            for (int i = 0; i < k; i++) {
                kth = kth.next;
                if (kth == null) return dummy.next;
            }
            ListNode nextGroup = kth.next;

            // Reverse k nodes
            ListNode prev = nextGroup, curr = prevGroup.next;
            for (int i = 0; i < k; i++) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }

            // Nối lại
            ListNode tmp = prevGroup.next;  // Node đầu nhóm (sẽ thành cuối)
            prevGroup.next = prev;          // prev là đầu nhóm mới
            prevGroup = tmp;                // Tiến đến cuối nhóm vừa reverse
        }
    }
}
// Time: O(n), Space: O(1)
```
