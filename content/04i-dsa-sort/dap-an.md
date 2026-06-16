# Sorting - Đáp Án Chi Tiết

## Bài 1: Sort Colors

### Cách 1: Dutch National Flag (3 Pointers)
```java
public class SortColors {
    public static void sortColors(int[] nums) {
        int low = 0, mid = 0, high = nums.length - 1;
        while (mid <= high) {
            if (nums[mid] == 0) { swap(nums, low++, mid++); }
            else if (nums[mid] == 1) { mid++; }
            else { swap(nums, mid, high--); }
        }
    }
    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}
// 0s đi trước low, 2s đi sau high, 1s ở giữa
```

---

## Bài 2: Merge Intervals

### Cách 1: Sort + Merge
```java
public class MergeIntervals {
    public static int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        List<int[]> result = new ArrayList<>();
        result.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            int[] last = result.get(result.size() - 1);
            if (intervals[i][0] <= last[1]) last[1] = Math.max(last[1], intervals[i][1]);
            else result.add(intervals[i]);
        }
        return result.toArray(new int[0][]);
    }
}
```

---

## Bài 3: Merge Sort

### Cách 1: Recursive
```java
public class MergeSortImpl {
    public static void mergeSort(int[] arr, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }
    private static void merge(int[] arr, int l, int m, int r) {
        int[] temp = new int[r - l + 1];
        int i = l, j = m + 1, k = 0;
        while (i <= m && j <= r) temp[k++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];
        while (i <= m) temp[k++] = arr[i++];
        while (j <= r) temp[k++] = arr[j++];
        System.arraycopy(temp, 0, arr, l, temp.length);
    }
}
```

---

## Bài 4: Quick Sort

### Cách 1: Random Pivot
```java
public class QuickSortImpl {
    private static Random rand = new Random();

    public static void quickSort(int[] arr, int lo, int hi) {
        if (lo < hi) {
            int pivotIdx = lo + rand.nextInt(hi - lo + 1);
            int temp = arr[pivotIdx]; arr[pivotIdx] = arr[hi]; arr[hi] = temp;
            int pi = partition(arr, lo, hi);
            quickSort(arr, lo, pi - 1);
            quickSort(arr, pi + 1, hi);
        }
    }

    private static int partition(int[] arr, int lo, int hi) {
        int pivot = arr[hi], i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (arr[j] < pivot) { i++; int t = arr[i]; arr[i] = arr[j]; arr[j] = t; }
        }
        int t = arr[i+1]; arr[i+1] = arr[hi]; arr[hi] = t;
        return i + 1;
    }
}
```

---

## Bài 5: Kth Largest (QuickSelect)

### Cách 1: QuickSelect O(n) average
```java
public class QuickSelect {
    public static int findKthLargest(int[] nums, int k) {
        int target = nums.length - k;
        return select(nums, 0, nums.length - 1, target);
    }

    private static int select(int[] nums, int lo, int hi, int target) {
        int pivot = nums[hi], i = lo;
        for (int j = lo; j < hi; j++) {
            if (nums[j] <= pivot) { swap(nums, i, j); i++; }
        }
        swap(nums, i, hi);
        if (i == target) return nums[i];
        if (i < target) return select(nums, i + 1, hi, target);
        return select(nums, lo, i - 1, target);
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}
```

---

## Bài 6: Largest Number

### Cách 1: Custom Comparator
```java
public class LargestNumber {
    public static String largestNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) strs[i] = String.valueOf(nums[i]);
        Arrays.sort(strs, (a, b) -> (b + a).compareTo(a + b));
        if (strs[0].equals("0")) return "0";
        return String.join("", strs);
    }
}
// So sánh: "330" vs "303" → "3" + "30" = "330" > "30" + "3" = "303"
// → 3 đứng trước 30
```
