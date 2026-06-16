# Searching - Đáp Án Chi Tiết

## Bài 1: Binary Search

### Cách 1: Iterative O(log n)
```java
public class BinarySearch {
    public static int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) return mid;
            else if (nums[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }
}
```

---

## Bài 2: First and Last Position

### Cách 1: Lower/Upper Bound O(log n)
```java
public class SearchRange {
    public static int[] searchRange(int[] nums, int target) {
        int first = lowerBound(nums, target);
        if (first == nums.length || nums[first] != target) return new int[]{-1, -1};
        int last = lowerBound(nums, target + 1) - 1;
        return new int[]{first, last};
    }

    private static int lowerBound(int[] nums, int target) {
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) left = mid + 1;
            else right = mid;
        }
        return left;
    }
}
```

---

## Bài 3: Search in Rotated Sorted Array

### Cách 1: Modified Binary Search O(log n)
```java
public class SearchRotated {
    public static int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) return mid;
            if (nums[left] <= nums[mid]) {
                if (target >= nums[left] && target < nums[mid]) right = mid - 1;
                else left = mid + 1;
            } else {
                if (target > nums[mid] && target <= nums[right]) left = mid + 1;
                else right = mid - 1;
            }
        }
        return -1;
    }
}
```

---

## Bài 4: Koko Eating Bananas

### Cách 1: Binary Search on Answer O(n log m)
```java
public class KokoEatingBananas {
    public static int minEatingSpeed(int[] piles, int h) {
        int left = 1, right = Arrays.stream(piles).max().orElse(1);
        while (left < right) {
            int mid = left + (right - left) / 2;
            int hours = 0;
            for (int pile : piles) hours += (pile + mid - 1) / mid;
            if (hours <= h) right = mid;
            else left = mid + 1;
        }
        return left;
    }
}
```

---

## Bài 5: Search a 2D Matrix

### Cách 1: Binary Search (treat as 1D) O(log(m×n))
```java
public class SearchMatrix {
    public static boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int left = 0, right = m * n - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int val = matrix[mid / n][mid % n];
            if (val == target) return true;
            else if (val < target) left = mid + 1;
            else right = mid - 1;
        }
        return false;
    }
}
```

---

## Bài 6: Median of Two Sorted Arrays

### Cách 1: Binary Search O(log(min(m,n)))
```java
public class MedianSortedArrays {
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) return findMedianSortedArrays(nums2, nums1);
        int m = nums1.length, n = nums2.length;
        int left = 0, right = m;

        while (left <= right) {
            int i = left + (right - left) / 2;
            int j = (m + n + 1) / 2 - i;

            int maxLeft1 = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int minRight1 = (i == m) ? Integer.MAX_VALUE : nums1[i];
            int maxLeft2 = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int minRight2 = (j == n) ? Integer.MAX_VALUE : nums2[j];

            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                if ((m + n) % 2 == 0) {
                    return (Math.max(maxLeft1, maxLeft2) + Math.min(minRight1, minRight2)) / 2.0;
                }
                return Math.max(maxLeft1, maxLeft2);
            }
            if (maxLeft1 > minRight2) right = i - 1;
            else left = i + 1;
        }
        return 0;
    }
}
```
