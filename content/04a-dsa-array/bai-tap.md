# Array - Bài Tập

## Bài 1: Two Sum
**Độ khó: Dễ**

Cho một mảng số nguyên `nums` và một số nguyên `target`, tìm hai chỉ số sao cho tổng hai phần tử bằng `target`. Mỗi phần tử chỉ được dùng một lần.

**Ví dụ:**
```
Input:  nums = [2, 7, 11, 15], target = 9
Output: [0, 1] (vì nums[0] + nums[1] = 2 + 7 = 9)

Input:  nums = [3, 2, 4], target = 6
Output: [1, 2]
```

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | nums=[2,7,11,15], target=9 | [0, 1] |
| 2 | nums=[3,2,4], target=6 | [1, 2] |
| 3 | nums=[3,3], target=6 | [0, 1] |
| 4 | nums=[1,5,3,7], target=8 | [1, 2] |
| 5 | nums=[-1,0,1,2], target=1 | [0, 2] |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, Arrays.toString(twoSum(new int[]{2,7,11,15}, 9)), "[0, 1]");
        check(2, Arrays.toString(twoSum(new int[]{3,2,4}, 6)), "[1, 2]");
        check(3, Arrays.toString(twoSum(new int[]{3,3}, 6)), "[0, 1]");
        check(4, Arrays.toString(twoSum(new int[]{1,5,3,7}, 8)), "[1, 2]");
        check(5, Arrays.toString(twoSum(new int[]{-1,0,1,2}, 1)), "[0, 2]");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int[] twoSum(int[] nums, int target) {
        // Code here ...
        return new int[]{};
    }
}
```

---

## Bài 2: Maximum Subarray (Kadane)
**Độ khó: Trung bình**

Tìm subarray liên tiếp có tổng lớn nhất.

**Ví dụ:**
```
Input:  nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]
Output: 6 (subarray [4, -1, 2, 1])
```

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [-2,1,-3,4,-1,2,1,-5,4] | 6 |
| 2 | [1] | 1 |
| 3 | [5,4,-1,7,8] | 23 |
| 4 | [-1,-2,-3] | -1 |
| 5 | [-2,1] | 1 |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4})), "6");
        check(2, String.valueOf(maxSubArray(new int[]{1})), "1");
        check(3, String.valueOf(maxSubArray(new int[]{5,4,-1,7,8})), "23");
        check(4, String.valueOf(maxSubArray(new int[]{-1,-2,-3})), "-1");
        check(5, String.valueOf(maxSubArray(new int[]{-2,1})), "1");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int maxSubArray(int[] nums) {
        // Code here ...
        return 0;
    }
}
```

---

## Bài 3: Merge Intervals
**Độ khó: Trung bình**

Cho danh sách các khoảng (intervals), gộp tất cả các khoảng chồng lấp.

**Ví dụ:**
```
Input:  intervals = [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
```

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

## Bài 4: Product of Array Except Self
**Độ khó: Trung bình**

Cho mảng `nums`, trả về mảng `output` sao cho `output[i]` bằng tích tất cả phần tử trừ `nums[i]`. **Không dùng phép chia**, time O(n).

**Ví dụ:**
```
Input:  nums = [1, 2, 3, 4]
Output: [24, 12, 8, 6]
```

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [1,2,3,4] | [24, 12, 8, 6] |
| 2 | [-1,1,0,-3,3] | [0, 0, 9, 0, 0] |
| 3 | [2,3] | [3, 2] |
| 4 | [1,1,1,1] | [1, 1, 1, 1] |
| 5 | [5,2,4] | [8, 20, 10] |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, Arrays.toString(productExceptSelf(new int[]{1,2,3,4})), "[24, 12, 8, 6]");
        check(2, Arrays.toString(productExceptSelf(new int[]{-1,1,0,-3,3})), "[0, 0, 9, 0, 0]");
        check(3, Arrays.toString(productExceptSelf(new int[]{2,3})), "[3, 2]");
        check(4, Arrays.toString(productExceptSelf(new int[]{1,1,1,1})), "[1, 1, 1, 1]");
        check(5, Arrays.toString(productExceptSelf(new int[]{5,2,4})), "[8, 20, 10]");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int[] productExceptSelf(int[] nums) {
        // Code here ...
        return new int[]{};
    }
}
```

---

## Bài 5: Sliding Window Maximum
**Độ khó: Khó**

Cho mảng `nums` và số `k`, tìm giá trị lớn nhất trong mỗi cửa sổ trượt kích thước k.

**Gợi ý:** Dùng Deque (Monotonic Queue) để đạt O(n).

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | nums=[1,3,-1,-3,5,3,6,7], k=3 | [3, 3, 5, 5, 6, 7] |
| 2 | nums=[1], k=1 | [1] |
| 3 | nums=[9,11], k=2 | [11] |
| 4 | nums=[4,-2], k=2 | [4] |
| 5 | nums=[1,2,3,4,5], k=3 | [3, 4, 5] |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        check(1, Arrays.toString(maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3)), "[3, 3, 5, 5, 6, 7]");
        check(2, Arrays.toString(maxSlidingWindow(new int[]{1}, 1)), "[1]");
        check(3, Arrays.toString(maxSlidingWindow(new int[]{9,11}, 2)), "[11]");
        check(4, Arrays.toString(maxSlidingWindow(new int[]{4,-2}, 2)), "[4]");
        check(5, Arrays.toString(maxSlidingWindow(new int[]{1,2,3,4,5}, 3)), "[3, 4, 5]");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {
        // Code here ...
        return new int[]{};
    }
}
```

---

## Bài 6: Trapping Rain Water
**Độ khó: Khó**

Cho mảng `height` biểu diễn chiều cao các cột, tính lượng nước mưa có thể chứa.

**Gợi ý:** Two Pointers hoặc Prefix Max arrays.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | [0,1,0,2,1,0,1,3,2,1,2,1] | 6 |
| 2 | [4,2,0,3,2,5] | 9 |
| 3 | [1,2,3,4,5] | 0 |
| 4 | [5,4,3,2,1] | 0 |
| 5 | [3,0,2,0,4] | 7 |

### 🧪 Main Demo
```java
public class Main {

    public static void main(String[] args) {
        check(1, String.valueOf(trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1})), "6");
        check(2, String.valueOf(trap(new int[]{4,2,0,3,2,5})), "9");
        check(3, String.valueOf(trap(new int[]{1,2,3,4,5})), "0");
        check(4, String.valueOf(trap(new int[]{5,4,3,2,1})), "0");
        check(5, String.valueOf(trap(new int[]{3,0,2,0,4})), "7");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static int trap(int[] height) {
        // Code here ...
        return 0;
    }
}
```

---

## Bài 7: Xoay Mảng (Rotate Array)
**Độ khó: Trung bình**

Xoay mảng sang phải k bước. Yêu cầu: O(1) extra space.

**Gợi ý:** Dùng kỹ thuật đảo ngược 3 lần.

| Test | Input | Expected Output |
|------|-------|-----------------|
| 1 | nums=[1,2,3,4,5,6,7], k=3 | [5, 6, 7, 1, 2, 3, 4] |
| 2 | nums=[-1,-100,3,99], k=2 | [3, 99, -1, -100] |
| 3 | nums=[1,2,3], k=0 | [1, 2, 3] |
| 4 | nums=[1], k=5 | [1] |
| 5 | nums=[1,2], k=3 | [2, 1] |

### 🧪 Main Demo
```java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int[] a1 = {1,2,3,4,5,6,7}; rotate(a1, 3);
        check(1, Arrays.toString(a1), "[5, 6, 7, 1, 2, 3, 4]");

        int[] a2 = {-1,-100,3,99}; rotate(a2, 2);
        check(2, Arrays.toString(a2), "[3, 99, -1, -100]");

        int[] a3 = {1,2,3}; rotate(a3, 0);
        check(3, Arrays.toString(a3), "[1, 2, 3]");

        int[] a4 = {1}; rotate(a4, 5);
        check(4, Arrays.toString(a4), "[1]");

        int[] a5 = {1,2}; rotate(a5, 3);
        check(5, Arrays.toString(a5), "[2, 1]");
    }

    // ==================== DO NOT MODIFY BELOW ====================
    static void check(int testId, String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println("[TEST " + testId + "] PASS");
        } else {
            System.out.println("[TEST " + testId + "] FAIL: Expected " + expected + ", Got " + actual);
        }
    }

    public static void rotate(int[] nums, int k) {
        // Code here ...
    }
}
```

---

## Bài 8: Subarray Sum Equals K
**Độ khó: Trung bình**

Đếm số lượng subarray liên tiếp có tổng bằng k.

**Gợi ý:** Dùng Prefix Sum + HashMap.

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
