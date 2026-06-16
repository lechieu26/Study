# Array - Đáp Án Chi Tiết

## Bài 1: Two Sum

### Cách 1: Brute Force O(n²)
```java
public class TwoSum_BruteForce {
    public static int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) return new int[]{i, j};
            }
        }
        return new int[]{};
    }
}
```

### Cách 2: HashMap O(n)
```java
public class TwoSum_HashMap {
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }
}
// Duyệt 1 lần, mỗi phần tử kiểm tra complement đã gặp chưa
// Time: O(n), Space: O(n)
```

### Cách 3: Sắp xếp + Two Pointers O(n log n)
```java
public class TwoSum_TwoPointers {
    public static int[] twoSum(int[] nums, int target) {
        int[][] indexed = new int[nums.length][2];
        for (int i = 0; i < nums.length; i++) {
            indexed[i] = new int[]{nums[i], i};
        }
        Arrays.sort(indexed, Comparator.comparingInt(a -> a[0]));

        int left = 0, right = nums.length - 1;
        while (left < right) {
            int sum = indexed[left][0] + indexed[right][0];
            if (sum == target) return new int[]{indexed[left][1], indexed[right][1]};
            else if (sum < target) left++;
            else right--;
        }
        return new int[]{};
    }
}
```

---

## Bài 2: Maximum Subarray (Kadane)

### Cách 1: Kadane's Algorithm O(n)
```java
public class MaxSubarray_Kadane {
    public static int maxSubArray(int[] nums) {
        int maxSum = nums[0];
        int currentSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            maxSum = Math.max(maxSum, currentSum);
        }
        return maxSum;
    }
}
// Ý tưởng: Tại mỗi vị trí, quyết định mở rộng subarray cũ hay bắt đầu mới
// currentSum < 0 → bắt đầu mới tốt hơn
```

### Cách 2: Divide and Conquer O(n log n)
```java
public class MaxSubarray_DivideConquer {
    public static int maxSubArray(int[] nums) {
        return solve(nums, 0, nums.length - 1);
    }

    private static int solve(int[] nums, int left, int right) {
        if (left == right) return nums[left];
        int mid = left + (right - left) / 2;
        int leftMax = solve(nums, left, mid);
        int rightMax = solve(nums, mid + 1, right);
        int crossMax = maxCrossing(nums, left, mid, right);
        return Math.max(Math.max(leftMax, rightMax), crossMax);
    }

    private static int maxCrossing(int[] nums, int left, int mid, int right) {
        int leftSum = Integer.MIN_VALUE, sum = 0;
        for (int i = mid; i >= left; i--) {
            sum += nums[i];
            leftSum = Math.max(leftSum, sum);
        }
        int rightSum = Integer.MIN_VALUE;
        sum = 0;
        for (int i = mid + 1; i <= right; i++) {
            sum += nums[i];
            rightSum = Math.max(rightSum, sum);
        }
        return leftSum + rightSum;
    }
}
```

---

## Bài 3: Merge Intervals

### Cách 1: Sort + Merge O(n log n)
```java
public class MergeIntervals {
    public static int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        List<int[]> result = new ArrayList<>();
        result.add(intervals[0]);

        for (int i = 1; i < intervals.length; i++) {
            int[] last = result.get(result.size() - 1);
            if (intervals[i][0] <= last[1]) {
                last[1] = Math.max(last[1], intervals[i][1]);  // Gộp
            } else {
                result.add(intervals[i]);  // Thêm mới
            }
        }
        return result.toArray(new int[0][]);
    }
}
```

---

## Bài 4: Product of Array Except Self

### Cách 1: Prefix & Suffix Products O(n)
```java
public class ProductExceptSelf {
    public static int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] output = new int[n];

        // output[i] = tích tất cả phần tử bên trái i
        output[0] = 1;
        for (int i = 1; i < n; i++) {
            output[i] = output[i - 1] * nums[i - 1];
        }

        // Nhân thêm tích tất cả phần tử bên phải i
        int rightProduct = 1;
        for (int i = n - 2; i >= 0; i--) {
            rightProduct *= nums[i + 1];
            output[i] *= rightProduct;
        }
        return output;
    }
}
// Time: O(n), Space: O(1) (không tính output)
```

---

## Bài 5: Sliding Window Maximum

### Cách 1: Monotonic Deque O(n)
```java
public class SlidingWindowMax {
    public static int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> deque = new ArrayDeque<>();  // Lưu index, giảm dần
        int[] result = new int[nums.length - k + 1];

        for (int i = 0; i < nums.length; i++) {
            // Xóa phần tử ngoài cửa sổ
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            // Xóa phần tử nhỏ hơn nums[i] — không bao giờ là max
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            deque.offerLast(i);

            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return result;
    }
}
// Deque luôn giảm dần → peekFirst() = max trong cửa sổ
```

---

## Bài 6: Trapping Rain Water

### Cách 1: Two Pointers O(n)
```java
public class TrappingRainWater {
    public static int trap(int[] height) {
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0, water = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                leftMax = Math.max(leftMax, height[left]);
                water += leftMax - height[left];
                left++;
            } else {
                rightMax = Math.max(rightMax, height[right]);
                water += rightMax - height[right];
                right--;
            }
        }
        return water;
    }
}
// Time: O(n), Space: O(1)
```

### Cách 2: Prefix Max Arrays O(n)
```java
public class TrappingRainWater_Prefix {
    public static int trap(int[] height) {
        int n = height.length;
        int[] leftMax = new int[n], rightMax = new int[n];

        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) leftMax[i] = Math.max(leftMax[i-1], height[i]);

        rightMax[n-1] = height[n-1];
        for (int i = n-2; i >= 0; i--) rightMax[i] = Math.max(rightMax[i+1], height[i]);

        int water = 0;
        for (int i = 0; i < n; i++) {
            water += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return water;
    }
}
```

---

## Bài 7: Xoay Mảng

### Cách 1: Reverse 3 lần O(n), O(1) space
```java
public class RotateArray {
    public static void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;  // Xử lý k > n
        reverse(nums, 0, n - 1);      // Đảo toàn bộ
        reverse(nums, 0, k - 1);      // Đảo k phần tử đầu
        reverse(nums, k, n - 1);      // Đảo phần còn lại
    }

    private static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++; end--;
        }
    }
}
// [1,2,3,4,5,6,7] k=3
// Reverse all: [7,6,5,4,3,2,1]
// Reverse [0,2]: [5,6,7,4,3,2,1]
// Reverse [3,6]: [5,6,7,1,2,3,4] ✓
```

---

## Bài 8: Subarray Sum Equals K

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
// Nếu prefix[j] - prefix[i] = k → subarray [i+1..j] có tổng k
// Đếm số prefix sum = (sum - k) đã xuất hiện trước đó
```
