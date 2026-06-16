# Sorting - Bài Tập

## Bài 1: Sort Colors (Dutch National Flag)
**Độ khó: Trung bình**

Cho mảng chỉ chứa 0, 1, 2. Sắp xếp in-place, chỉ 1 pass, không dùng sort.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [2,0,2,1,1,0] | [0, 0, 1, 1, 2, 2] |
| 2 | [2,0,1] | [0, 1, 2] |
| 3 | [0] | [0] |
| 4 | [1,1,1] | [1, 1, 1] |
| 5 | [2,1,0,0,1,2] | [0, 0, 1, 1, 2, 2] |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int[] a1 = {2,0,2,1,1,0}; sortColors(a1);
        check(1, Arrays.toString(a1), "[0, 0, 1, 1, 2, 2]");

        int[] a2 = {2,0,1}; sortColors(a2);
        check(2, Arrays.toString(a2), "[0, 1, 2]");

        int[] a3 = {0}; sortColors(a3);
        check(3, Arrays.toString(a3), "[0]");

        int[] a4 = {1,1,1}; sortColors(a4);
        check(4, Arrays.toString(a4), "[1, 1, 1]");

        int[] a5 = {2,1,0,0,1,2}; sortColors(a5);
        check(5, Arrays.toString(a5), "[0, 0, 1, 1, 2, 2]");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static void sortColors(int[] nums) {
        // Code here ...
    }
}
```

---

## Bài 2: Merge Intervals
**Độ khó: Trung bình**

Gộp các khoảng chồng lấp.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [[1,3],[2,6],[8,10],[15,18]] | [[1,6],[8,10],[15,18]] |
| 2 | [[1,4],[4,5]] | [[1,5]] |
| 3 | [[1,4],[2,3]] | [[1,4]] |
| 4 | [[1,2]] | [[1,2]] |
| 5 | [[1,3],[5,7],[2,4]] | [[1,4],[5,7]] |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, arrToString(merge(new int[][]{{1,3},{2,6},{8,10},{15,18}})), "[[1,6],[8,10],[15,18]]");
        check(2, arrToString(merge(new int[][]{{1,4},{4,5}})), "[[1,5]]");
        check(3, arrToString(merge(new int[][]{{1,4},{2,3}})), "[[1,4]]");
        check(4, arrToString(merge(new int[][]{{1,2}})), "[[1,2]]");
        check(5, arrToString(merge(new int[][]{{1,3},{5,7},{2,4}})), "[[1,4],[5,7]]");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static String arrToString(int[][] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append("[").append(arr[i][0]).append(",").append(arr[i][1]).append("]");
            if (i < arr.length - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int[][] merge(int[][] intervals) {
        // Code here ...
        return new int[][]{};
    }
}
```

---

## Bài 3: Implement Merge Sort
**Độ khó: Trung bình**

Implement thuật toán Merge Sort.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [38,27,43,3,9,82,10] | [3, 9, 10, 27, 38, 43, 82] |
| 2 | [5,4,3,2,1] | [1, 2, 3, 4, 5] |
| 3 | [1] | [1] |
| 4 | [2,1] | [1, 2] |
| 5 | [3,3,3] | [3, 3, 3] |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int[] a1 = {38,27,43,3,9,82,10}; mergeSort(a1, 0, a1.length-1);
        check(1, Arrays.toString(a1), "[3, 9, 10, 27, 38, 43, 82]");

        int[] a2 = {5,4,3,2,1}; mergeSort(a2, 0, a2.length-1);
        check(2, Arrays.toString(a2), "[1, 2, 3, 4, 5]");

        int[] a3 = {1}; mergeSort(a3, 0, a3.length-1);
        check(3, Arrays.toString(a3), "[1]");

        int[] a4 = {2,1}; mergeSort(a4, 0, a4.length-1);
        check(4, Arrays.toString(a4), "[1, 2]");

        int[] a5 = {3,3,3}; mergeSort(a5, 0, a5.length-1);
        check(5, Arrays.toString(a5), "[3, 3, 3]");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static void mergeSort(int[] arr, int left, int right) {
        // Code here ...
    }
}
```

---

## Bài 4: Implement Quick Sort
**Độ khó: Trung bình**

Implement Quick Sort.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [10,7,8,9,1,5] | [1, 5, 7, 8, 9, 10] |
| 2 | [3,2,1] | [1, 2, 3] |
| 3 | [1] | [1] |
| 4 | [1,1,1] | [1, 1, 1] |
| 5 | [5,3,8,4,2] | [2, 3, 4, 5, 8] |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int[] a1 = {10,7,8,9,1,5}; quickSort(a1, 0, a1.length-1);
        check(1, Arrays.toString(a1), "[1, 5, 7, 8, 9, 10]");

        int[] a2 = {3,2,1}; quickSort(a2, 0, a2.length-1);
        check(2, Arrays.toString(a2), "[1, 2, 3]");

        int[] a3 = {1}; quickSort(a3, 0, a3.length-1);
        check(3, Arrays.toString(a3), "[1]");

        int[] a4 = {1,1,1}; quickSort(a4, 0, a4.length-1);
        check(4, Arrays.toString(a4), "[1, 1, 1]");

        int[] a5 = {5,3,8,4,2}; quickSort(a5, 0, a5.length-1);
        check(5, Arrays.toString(a5), "[2, 3, 4, 5, 8]");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static void quickSort(int[] arr, int low, int high) {
        // Code here ...
    }
}
```

---

## Bài 5: Kth Largest Element
**Độ khó: Trung bình**

Tìm phần tử lớn thứ k. Yêu cầu O(n) average (QuickSelect).

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | nums=[3,2,3,1,2,4,5,5,6], k=4 | 4 |
| 2 | nums=[3,2,1,5,6,4], k=2 | 5 |
| 3 | nums=[1], k=1 | 1 |
| 4 | nums=[7,6,5,4,3,2,1], k=5 | 3 |
| 5 | nums=[2,1], k=1 | 2 |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(findKthLargest(new int[]{3,2,3,1,2,4,5,5,6}, 4)), "4");
        check(2, String.valueOf(findKthLargest(new int[]{3,2,1,5,6,4}, 2)), "5");
        check(3, String.valueOf(findKthLargest(new int[]{1}, 1)), "1");
        check(4, String.valueOf(findKthLargest(new int[]{7,6,5,4,3,2,1}, 5)), "3");
        check(5, String.valueOf(findKthLargest(new int[]{2,1}, 1)), "2");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int findKthLargest(int[] nums, int k) {
        // Code here ...
        return 0;
    }
}
```

---

## Bài 6: Custom Sort — Largest Number
**Độ khó: Trung bình**

Cho mảng số không âm, sắp xếp để tạo số lớn nhất.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [3,30,34,5,9] | 9534330 |
| 2 | [10,2] | 210 |
| 3 | [1] | 1 |
| 4 | [0,0] | 0 |
| 5 | [999,99,9] | 999999 |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, largestNumber(new int[]{3,30,34,5,9}), "9534330");
        check(2, largestNumber(new int[]{10,2}), "210");
        check(3, largestNumber(new int[]{1}), "1");
        check(4, largestNumber(new int[]{0,0}), "0");
        check(5, largestNumber(new int[]{999,99,9}), "999999");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static String largestNumber(int[] nums) {
        // Code here ...
        return "";
    }
}
```
