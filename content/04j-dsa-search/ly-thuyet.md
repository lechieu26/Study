# Searching Algorithms (Thuật toán tìm kiếm)

## 1. Tổng quan

| Thuật toán | Time | Space | Yêu cầu |
|-----------|------|-------|---------|
| Linear Search | O(n) | O(1) | Không |
| Binary Search | O(log n) | O(1) | Mảng sorted |
| Binary Search (recursive) | O(log n) | O(log n) | Mảng sorted |
| Interpolation Search | O(log log n) avg | O(1) | Sorted, phân bố đều |

## 2. Linear Search — O(n)

Duyệt từng phần tử. Đơn giản nhưng chậm.

```java
public static int linearSearch(int[] arr, int target) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == target) return i;
    }
    return -1;
}
```

## 3. Binary Search — O(log n)

Chia đôi không gian tìm kiếm mỗi bước. **Yêu cầu mảng đã sorted.**

```java
// Standard Binary Search
public static int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;  // Tránh integer overflow
        if (arr[mid] == target) return mid;
        else if (arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}
```

### Biến thể Binary Search

```java
// Lower Bound: Vị trí đầu tiên >= target
public static int lowerBound(int[] arr, int target) {
    int left = 0, right = arr.length;
    while (left < right) {
        int mid = left + (right - left) / 2;
        if (arr[mid] < target) left = mid + 1;
        else right = mid;
    }
    return left;
}

// Upper Bound: Vị trí đầu tiên > target
public static int upperBound(int[] arr, int target) {
    int left = 0, right = arr.length;
    while (left < right) {
        int mid = left + (right - left) / 2;
        if (arr[mid] <= target) left = mid + 1;
        else right = mid;
    }
    return left;
}

// Tìm phần tử đầu tiên và cuối cùng bằng target
public static int[] searchRange(int[] nums, int target) {
    int first = lowerBound(nums, target);
    if (first == nums.length || nums[first] != target) return new int[]{-1, -1};
    int last = upperBound(nums, target) - 1;
    return new int[]{first, last};
}
```

## 4. Binary Search on Answer

Không chỉ tìm trong mảng — **bất kỳ bài toán nào có tính monotonic** đều binary search được.

```java
// Tìm căn bậc 2 (integer)
public static int mySqrt(int x) {
    long left = 0, right = x;
    while (left <= right) {
        long mid = left + (right - left) / 2;
        if (mid * mid <= x) left = mid + 1;
        else right = mid - 1;
    }
    return (int) right;
}

// Minimum number of days to make m bouquets
// Nếu f(d) = true → f(d+1) = true (monotonic)
public static int minDays(int[] bloomDay, int m, int k) {
    int left = 1, right = Arrays.stream(bloomDay).max().orElse(0);
    int result = -1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (canMake(bloomDay, m, k, mid)) {
            result = mid;
            right = mid - 1;
        } else {
            left = mid + 1;
        }
    }
    return result;
}

// Koko eating bananas — tìm tốc độ ăn nhỏ nhất
public static int minEatingSpeed(int[] piles, int h) {
    int left = 1, right = Arrays.stream(piles).max().orElse(1);
    while (left < right) {
        int mid = left + (right - left) / 2;
        int hours = 0;
        for (int pile : piles) hours += (pile + mid - 1) / mid;  // ceil division
        if (hours <= h) right = mid;
        else left = mid + 1;
    }
    return left;
}
```

## 5. Binary Search trên ma trận

```java
// Search in sorted matrix (mỗi hàng sorted, cột sorted)
public static boolean searchMatrix(int[][] matrix, int target) {
    int row = 0, col = matrix[0].length - 1;
    while (row < matrix.length && col >= 0) {
        if (matrix[row][col] == target) return true;
        else if (matrix[row][col] > target) col--;
        else row++;
    }
    return false;
}
// Bắt đầu từ góc phải trên: > target → sang trái, < target → xuống dưới
// Time: O(m + n)
```

## 6. Binary Search trên Rotated Sorted Array

```java
public static int searchRotated(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) return mid;

        if (nums[left] <= nums[mid]) {  // Nửa trái sorted
            if (target >= nums[left] && target < nums[mid]) right = mid - 1;
            else left = mid + 1;
        } else {  // Nửa phải sorted
            if (target > nums[mid] && target <= nums[right]) left = mid + 1;
            else right = mid - 1;
        }
    }
    return -1;
}
```

## 7. Template Binary Search

```
// Template 1: left <= right (tìm chính xác)
while (left <= right) {
    mid = left + (right - left) / 2;
    if (found) return mid;
    if (condition) left = mid + 1;
    else right = mid - 1;
}

// Template 2: left < right (tìm boundary)
while (left < right) {
    mid = left + (right - left) / 2;
    if (condition) right = mid;      // mid có thể là đáp án
    else left = mid + 1;
}
return left;  // left == right = boundary

// Template 3: left + 1 < right (giữ 2 ứng viên)
while (left + 1 < right) {
    mid = left + (right - left) / 2;
    if (condition) right = mid;
    else left = mid;
}
// Kiểm tra left và right
```

## 8. Java Built-in

```java
// Arrays.binarySearch() — trả về index hoặc -(insertion point) - 1
int[] arr = {1, 3, 5, 7, 9};
Arrays.binarySearch(arr, 5);   // 2
Arrays.binarySearch(arr, 4);   // -3 (insertion point = 2, return -(2)-1 = -3)

// Collections.binarySearch()
List<Integer> list = List.of(1, 3, 5, 7, 9);
Collections.binarySearch(list, 5);  // 2
```

## 9. Khi nào dùng Binary Search?

| Dấu hiệu | Ví dụ |
|----------|-------|
| Mảng đã sorted | Tìm phần tử, tìm range |
| Tính monotonic | f(x) true → f(x+1) true |
| Tìm min/max thỏa điều kiện | Koko bananas, Split Array |
| "Tìm giá trị nhỏ nhất sao cho..." | Binary Search on Answer |
| Rotated sorted array | Search in rotated array |

> **Phỏng vấn thường hỏi:** Binary Search, Search in Rotated Array, Find First/Last Position, Koko Eating Bananas, Search 2D Matrix, Median of Two Sorted Arrays.
