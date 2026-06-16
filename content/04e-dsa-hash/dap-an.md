# Hash Table - Đáp Án Chi Tiết

## Bài 1: Valid Anagram

### Cách 1: Counting Array O(n)
```java
public class ValidAnagram {
    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
            count[t.charAt(i) - 'a']--;
        }
        for (int c : count) if (c != 0) return false;
        return true;
    }
}
```

### Cách 2: Sort O(n log n)
```java
public class ValidAnagram_Sort {
    public static boolean isAnagram(String s, String t) {
        char[] a = s.toCharArray(), b = t.toCharArray();
        Arrays.sort(a); Arrays.sort(b);
        return Arrays.equals(a, b);
    }
}
```

---

## Bài 2: Group Anagrams

### Cách 1: Sort key O(n × k log k)
```java
public class GroupAnagrams {
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
    }
}
```

### Cách 2: Counting key O(n × k)
```java
public class GroupAnagrams_Count {
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            int[] count = new int[26];
            for (char c : s.toCharArray()) count[c - 'a']++;
            String key = Arrays.toString(count);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
    }
}
```

---

## Bài 3: Longest Consecutive Sequence

### Cách 1: HashSet O(n)
```java
public class LongestConsecutive {
    public static int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) set.add(n);
        int longest = 0;
        for (int num : set) {
            if (!set.contains(num - 1)) {
                int length = 1;
                while (set.contains(num + length)) length++;
                longest = Math.max(longest, length);
            }
        }
        return longest;
    }
}
```

---

## Bài 4: Top K Frequent Elements

### Cách 1: HashMap + PriorityQueue O(n log k)
```java
public class TopKFrequent {
    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int n : nums) freq.merge(n, 1, Integer::sum);

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        for (var entry : freq.entrySet()) {
            pq.offer(new int[]{entry.getKey(), entry.getValue()});
            if (pq.size() > k) pq.poll();
        }
        return pq.stream().mapToInt(a -> a[0]).toArray();
    }
}
```

### Cách 2: Bucket Sort O(n)
```java
public class TopKFrequent_Bucket {
    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int n : nums) freq.merge(n, 1, Integer::sum);

        @SuppressWarnings("unchecked")
        List<Integer>[] buckets = new List[nums.length + 1];
        for (var entry : freq.entrySet()) {
            int f = entry.getValue();
            if (buckets[f] == null) buckets[f] = new ArrayList<>();
            buckets[f].add(entry.getKey());
        }

        List<Integer> result = new ArrayList<>();
        for (int i = buckets.length - 1; i >= 0 && result.size() < k; i--) {
            if (buckets[i] != null) result.addAll(buckets[i]);
        }
        return result.stream().mapToInt(Integer::intValue).toArray();
    }
}
```

---

## Bài 5: LRU Cache

### Cách 1: HashMap + Doubly Linked List O(1)
```java
public class LRUCache {
    private static class Node {
        int key, value;
        Node prev, next;
        Node(int key, int value) { this.key = key; this.value = value; }
    }

    private final int capacity;
    private final Map<Integer, Node> map;
    private final Node head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) return -1;
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node node = map.get(key);
        if (node != null) {
            node.value = value;
            moveToHead(node);
        } else {
            node = new Node(key, value);
            map.put(key, node);
            addToHead(node);
            if (map.size() > capacity) {
                Node removed = removeTail();
                map.remove(removed.key);
            }
        }
    }

    private void addToHead(Node node) {
        node.next = head.next; node.prev = head;
        head.next.prev = node; head.next = node;
    }
    private void removeNode(Node node) {
        node.prev.next = node.next; node.next.prev = node.prev;
    }
    private void moveToHead(Node node) { removeNode(node); addToHead(node); }
    private Node removeTail() {
        Node node = tail.prev; removeNode(node); return node;
    }
}
```

---

## Bài 6: Subarray Sum Equals K

### Cách 1: Prefix Sum + HashMap O(n)
```java
public class SubarraySum {
    public static int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> prefixCount = new HashMap<>();
        prefixCount.put(0, 1);
        int sum = 0, count = 0;
        for (int num : nums) {
            sum += num;
            count += prefixCount.getOrDefault(sum - k, 0);
            prefixCount.merge(sum, 1, Integer::sum);
        }
        return count;
    }
}
```
