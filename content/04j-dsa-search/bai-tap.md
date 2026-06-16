# Searching - Bài Tập

## Bài 1: Binary Search
**Độ khó: Dễ**

Implement binary search trên mảng sorted. Trả về index hoặc -1.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | nums=[-1,0,3,5,9,12], target=9 | 4 |
| 2 | nums=[-1,0,3,5,9,12], target=2 | -1 |
| 3 | nums=[5], target=5 | 0 |
| 4 | nums=[2,5], target=5 | 1 |
| 5 | nums=[1,2,3,4,5,6,7,8,9], target=1 | 0 |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(search(new int[]{-1,0,3,5,9,12}, 9)), "4");
        check(2, String.valueOf(search(new int[]{-1,0,3,5,9,12}, 2)), "-1");
        check(3, String.valueOf(search(new int[]{5}, 5)), "0");
        check(4, String.valueOf(search(new int[]{2,5}, 5)), "1");
        check(5, String.valueOf(search(new int[]{1,2,3,4,5,6,7,8,9}, 1)), "0");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int search(int[] nums, int target) {
        // Code here ...
        return -1;
    }
}
```

---

## Bài 2: First and Last Position in Sorted Array
**Độ khó: Trung bình**

Tìm vị trí đầu tiên và cuối cùng của target trong mảng sorted. Yêu cầu O(log n).

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | nums=[5,7,7,8,8,10], target=8 | [3, 4] |
| 2 | nums=[5,7,7,8,8,10], target=6 | [-1, -1] |
| 3 | nums=[], target=0 | [-1, -1] |
| 4 | nums=[1], target=1 | [0, 0] |
| 5 | nums=[2,2,2,2], target=2 | [0, 3] |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, Arrays.toString(searchRange(new int[]{5,7,7,8,8,10}, 8)), "[3, 4]");
        check(2, Arrays.toString(searchRange(new int[]{5,7,7,8,8,10}, 6)), "[-1, -1]");
        check(3, Arrays.toString(searchRange(new int[]{}, 0)), "[-1, -1]");
        check(4, Arrays.toString(searchRange(new int[]{1}, 1)), "[0, 0]");
        check(5, Arrays.toString(searchRange(new int[]{2,2,2,2}, 2)), "[0, 3]");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int[] searchRange(int[] nums, int target) {
        // Code here ...
        return new int[]{-1, -1};
    }
}
```

---

## Bài 3: Search in Rotated Sorted Array
**Độ khó: Trung bình**

Mảng sorted bị xoay tại 1 điểm. Tìm target trong O(log n).

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | nums=[4,5,6,7,0,1,2], target=0 | 4 |
| 2 | nums=[4,5,6,7,0,1,2], target=3 | -1 |
| 3 | nums=[1], target=0 | -1 |
| 4 | nums=[3,1], target=1 | 1 |
| 5 | nums=[1,3,5], target=5 | 2 |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(searchRotated(new int[]{4,5,6,7,0,1,2}, 0)), "4");
        check(2, String.valueOf(searchRotated(new int[]{4,5,6,7,0,1,2}, 3)), "-1");
        check(3, String.valueOf(searchRotated(new int[]{1}, 0)), "-1");
        check(4, String.valueOf(searchRotated(new int[]{3,1}, 1)), "1");
        check(5, String.valueOf(searchRotated(new int[]{1,3,5}, 5)), "2");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int searchRotated(int[] nums, int target) {
        // Code here ...
        return -1;
    }
}
```

---

## Bài 4: Koko Eating Bananas
**Độ khó: Trung bình**

Koko có h giờ để ăn hết chuối. Mỗi giờ ăn tối đa k quả từ 1 đống. Tìm k nhỏ nhất.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | piles=[3,6,7,11], h=8 | 4 |
| 2 | piles=[30,11,23,4,20], h=5 | 30 |
| 3 | piles=[30,11,23,4,20], h=6 | 23 |
| 4 | piles=[1,1,1], h=3 | 1 |
| 5 | piles=[312884470], h=312884469 | 2 |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(minEatingSpeed(new int[]{3,6,7,11}, 8)), "4");
        check(2, String.valueOf(minEatingSpeed(new int[]{30,11,23,4,20}, 5)), "30");
        check(3, String.valueOf(minEatingSpeed(new int[]{30,11,23,4,20}, 6)), "23");
        check(4, String.valueOf(minEatingSpeed(new int[]{1,1,1}, 3)), "1");
        check(5, String.valueOf(minEatingSpeed(new int[]{312884470}, 312884469)), "2");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int minEatingSpeed(int[] piles, int h) {
        // Code here ...
        return 0;
    }
}
```

---

## Bài 5: Search a 2D Matrix
**Độ khó: Trung bình**

Ma trận m×n sorted: mỗi hàng sorted, phần tử đầu hàng > cuối hàng trước. Tìm target.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | matrix=[[1,3,5,7],[10,11,16,20],[23,30,34,60]], target=3 | true |
| 2 | matrix=[[1,3,5,7],[10,11,16,20],[23,30,34,60]], target=13 | false |
| 3 | matrix=[[1]], target=1 | true |
| 4 | matrix=[[1]], target=2 | false |
| 5 | matrix=[[1,3],[5,7]], target=5 | true |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(searchMatrix(new int[][]{{1,3,5,7},{10,11,16,20},{23,30,34,60}}, 3)), "true");
        check(2, String.valueOf(searchMatrix(new int[][]{{1,3,5,7},{10,11,16,20},{23,30,34,60}}, 13)), "false");
        check(3, String.valueOf(searchMatrix(new int[][]{{1}}, 1)), "true");
        check(4, String.valueOf(searchMatrix(new int[][]{{1}}, 2)), "false");
        check(5, String.valueOf(searchMatrix(new int[][]{{1,3},{5,7}}, 5)), "true");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static boolean searchMatrix(int[][] matrix, int target) {
        // Code here ...
        return false;
    }
}
```

---

## Bài 6: Median of Two Sorted Arrays
**Độ khó: Khó**

Tìm median của 2 mảng sorted. Yêu cầu O(log(m+n)).

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | nums1=[1,3], nums2=[2] | 2.0 |
| 2 | nums1=[1,2], nums2=[3,4] | 2.5 |
| 3 | nums1=[], nums2=[1] | 1.0 |
| 4 | nums1=[2], nums2=[] | 2.0 |
| 5 | nums1=[1,2,3], nums2=[4,5,6] | 3.5 |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(findMedianSortedArrays(new int[]{1,3}, new int[]{2})), "2.0");
        check(2, String.valueOf(findMedianSortedArrays(new int[]{1,2}, new int[]{3,4})), "2.5");
        check(3, String.valueOf(findMedianSortedArrays(new int[]{}, new int[]{1})), "1.0");
        check(4, String.valueOf(findMedianSortedArrays(new int[]{2}, new int[]{})), "2.0");
        check(5, String.valueOf(findMedianSortedArrays(new int[]{1,2,3}, new int[]{4,5,6})), "3.5");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Code here ...
        return 0.0;
    }
}
```
