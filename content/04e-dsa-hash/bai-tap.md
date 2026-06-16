# Hash Table - Bài Tập

## Bài 1: Valid Anagram
**Độ khó: Dễ**

Kiểm tra hai chuỗi có phải anagram (cùng chữ cái, khác thứ tự) không.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | s="anagram", t="nagaram" | true |
| 2 | s="rat", t="car" | false |
| 3 | s="a", t="a" | true |
| 4 | s="ab", t="a" | false |
| 5 | s="listen", t="silent" | true |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(isAnagram("anagram", "nagaram")), "true");
        check(2, String.valueOf(isAnagram("rat", "car")), "false");
        check(3, String.valueOf(isAnagram("a", "a")), "true");
        check(4, String.valueOf(isAnagram("ab", "a")), "false");
        check(5, String.valueOf(isAnagram("listen", "silent")), "true");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static boolean isAnagram(String s, String t) {
        // Code here ...
        return false;
    }
}
```

---

## Bài 2: Group Anagrams
**Độ khó: Trung bình**

Nhóm các chuỗi anagram lại với nhau.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | ["eat","tea","ate"] | 1 group, size 3 |
| 2 | [""] | 1 group |
| 3 | ["a"] | 1 group |
| 4 | ["eat","tea","tan","nat"] | 2 groups |
| 5 | ["abc","def","ghi"] | 3 groups |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(groupAnagrams(new String[]{"eat","tea","ate"}).size()), "1");
        check(2, String.valueOf(groupAnagrams(new String[]{""}).size()), "1");
        check(3, String.valueOf(groupAnagrams(new String[]{"a"}).size()), "1");
        check(4, String.valueOf(groupAnagrams(new String[]{"eat","tea","tan","nat"}).size()), "2");
        check(5, String.valueOf(groupAnagrams(new String[]{"abc","def","ghi"}).size()), "3");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        // Code here ...
        return new ArrayList<>();
    }
}
```

---

## Bài 3: Longest Consecutive Sequence
**Độ khó: Trung bình**

Tìm độ dài dãy số liên tiếp dài nhất trong mảng unsorted. Yêu cầu O(n).

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [100,4,200,1,3,2] | 4 |
| 2 | [0,3,7,2,5,8,4,6,0,1] | 9 |
| 3 | [] | 0 |
| 4 | [1] | 1 |
| 5 | [1,2,0,1] | 3 |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(longestConsecutive(new int[]{100,4,200,1,3,2})), "4");
        check(2, String.valueOf(longestConsecutive(new int[]{0,3,7,2,5,8,4,6,0,1})), "9");
        check(3, String.valueOf(longestConsecutive(new int[]{})), "0");
        check(4, String.valueOf(longestConsecutive(new int[]{1})), "1");
        check(5, String.valueOf(longestConsecutive(new int[]{1,2,0,1})), "3");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int longestConsecutive(int[] nums) {
        // Code here ...
        return 0;
    }
}
```

---

## Bài 4: Top K Frequent Elements
**Độ khó: Trung bình**

Tìm k phần tử xuất hiện nhiều nhất.

**Gợi ý:** HashMap đếm tần suất + PriorityQueue hoặc Bucket Sort.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | nums=[1,1,1,2,2,3], k=2 | [1, 2] |
| 2 | nums=[1], k=1 | [1] |
| 3 | nums=[4,4,4,1,1,2], k=1 | [4] |
| 4 | nums=[1,2], k=2 | size=2 |
| 5 | nums=[3,3,3,3], k=1 | [3] |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int[] r1 = topKFrequent(new int[]{1,1,1,2,2,3}, 2);
        Arrays.sort(r1);
        check(1, Arrays.toString(r1), "[1, 2]");

        check(2, Arrays.toString(topKFrequent(new int[]{1}, 1)), "[1]");

        check(3, Arrays.toString(topKFrequent(new int[]{4,4,4,1,1,2}, 1)), "[4]");

        check(4, String.valueOf(topKFrequent(new int[]{1,2}, 2).length), "2");

        check(5, Arrays.toString(topKFrequent(new int[]{3,3,3,3}, 1)), "[3]");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int[] topKFrequent(int[] nums, int k) {
        // Code here ...
        return new int[]{};
    }
}
```

---

## Bài 5: Thiết Kế LRU Cache
**Độ khó: Khó**

Thiết kế Least Recently Used (LRU) Cache với `get(key)` và `put(key, value)`, cả hai O(1).

**Gợi ý:** HashMap + Doubly Linked List.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | cap=2,put(1,1),put(2,2),get(1) | 1 |
| 2 | cap=2,put(1,1),put(2,2),put(3,3),get(2) | -1 |
| 3 | cap=2,put(1,1),put(2,2),put(3,3),get(3) | 3 |
| 4 | cap=1,put(1,1),put(2,2),get(1) | -1 |
| 5 | cap=2,put(1,1),get(1),put(2,2),put(3,3),get(1) | 1 |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    static Map<Integer, int[]> cache;
    static int cap;
    static int order;

    public static void main(String[] args) {
        // Test 1
        initLRU(2); lruPut(1,1); lruPut(2,2);
        check(1, String.valueOf(lruGet(1)), "1");

        // Test 2
        initLRU(2); lruPut(1,1); lruPut(2,2); lruPut(3,3);
        check(2, String.valueOf(lruGet(2)), "-1");

        // Test 3
        initLRU(2); lruPut(1,1); lruPut(2,2); lruPut(3,3);
        check(3, String.valueOf(lruGet(3)), "3");

        // Test 4
        initLRU(1); lruPut(1,1); lruPut(2,2);
        check(4, String.valueOf(lruGet(1)), "-1");

        // Test 5
        initLRU(2); lruPut(1,1); lruGet(1); lruPut(2,2); lruPut(3,3);
        check(5, String.valueOf(lruGet(1)), "1");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void initLRU(int capacity) {
        cache = new LinkedHashMap<>();
        cap = capacity;
        order = 0;
    }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int lruGet(int key) {
        // Code here ...
        return -1;
    }

    public static void lruPut(int key, int value) {
        // Code here ...
    }
}
```

---

## Bài 6: Subarray Sum Equals K
**Độ khó: Trung bình**

Đếm số lượng subarray liên tiếp có tổng bằng k.

**Gợi ý:** Prefix Sum + HashMap.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | nums=[1,1,1], k=2 | 2 |
| 2 | nums=[1,2,3], k=3 | 2 |
| 3 | nums=[1], k=0 | 0 |
| 4 | nums=[1,-1,0], k=0 | 3 |
| 5 | nums=[3,4,7,2,-3,1,4,2], k=7 | 4 |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(subarraySum(new int[]{1,1,1}, 2)), "2");
        check(2, String.valueOf(subarraySum(new int[]{1,2,3}, 3)), "2");
        check(3, String.valueOf(subarraySum(new int[]{1}, 0)), "0");
        check(4, String.valueOf(subarraySum(new int[]{1,-1,0}, 0)), "3");
        check(5, String.valueOf(subarraySum(new int[]{3,4,7,2,-3,1,4,2}, 7)), "4");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int subarraySum(int[] nums, int k) {
        // Code here ...
        return 0;
    }
}
```
