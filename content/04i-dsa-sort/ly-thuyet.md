# Sorting Algorithms (Thuật toán sắp xếp)

## 1. Tổng quan

| Thuật toán | Best | Average | Worst | Space | Ổn định | Khi nào dùng |
|-----------|------|---------|-------|-------|---------|-------------|
| Bubble Sort | O(n) | O(n²) | O(n²) | O(1) | Có | Giáo dục, gần sorted |
| Selection Sort | O(n²) | O(n²) | O(n²) | O(1) | Không | Khi số lần swap quan trọng |
| Insertion Sort | O(n) | O(n²) | O(n²) | O(1) | Có | Dữ liệu nhỏ hoặc gần sorted |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) | Có | Cần stable, linked list |
| Quick Sort | O(n log n) | O(n log n) | O(n²) | O(log n) | Không | Đa số (nhanh nhất thực tế) |
| Heap Sort | O(n log n) | O(n log n) | O(n log n) | O(1) | Không | Guaranteed O(n log n), O(1) |
| Counting Sort | O(n+k) | O(n+k) | O(n+k) | O(k) | Có | Số nguyên, range hẹp |
| Radix Sort | O(d×n) | O(d×n) | O(d×n) | O(n+k) | Có | Số nguyên/chuỗi |

> **Stable sort:** Giữ nguyên thứ tự tương đối của các phần tử bằng nhau.

## 2. Bubble Sort — O(n²)

So sánh và swap cặp liền kề. Mỗi pass đẩy phần tử lớn nhất về cuối.

```java
public static void bubbleSort(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n - 1; i++) {
        boolean swapped = false;
        for (int j = 0; j < n - 1 - i; j++) {
            if (arr[j] > arr[j + 1]) {
                int temp = arr[j]; arr[j] = arr[j + 1]; arr[j + 1] = temp;
                swapped = true;
            }
        }
        if (!swapped) break;  // Tối ưu: dừng nếu đã sorted
    }
}
```

## 3. Selection Sort — O(n²)

Tìm phần tử nhỏ nhất, swap với vị trí hiện tại.

```java
public static void selectionSort(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
        int minIdx = i;
        for (int j = i + 1; j < arr.length; j++) {
            if (arr[j] < arr[minIdx]) minIdx = j;
        }
        int temp = arr[i]; arr[i] = arr[minIdx]; arr[minIdx] = temp;
    }
}
```

## 4. Insertion Sort — O(n²), best O(n)

Chèn từng phần tử vào vị trí đúng trong phần đã sorted.

```java
public static void insertionSort(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
        int key = arr[i], j = i - 1;
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            j--;
        }
        arr[j + 1] = key;
    }
}
// Rất hiệu quả khi dữ liệu gần sorted hoặc nhỏ
// Java TimSort dùng Insertion Sort cho mảng con nhỏ
```

## 5. Merge Sort — O(n log n), Stable

Chia đôi → sort → merge. Luôn O(n log n) mọi trường hợp.

```java
public static void mergeSort(int[] arr, int left, int right) {
    if (left < right) {
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }
}

private static void merge(int[] arr, int left, int mid, int right) {
    int[] temp = new int[right - left + 1];
    int i = left, j = mid + 1, k = 0;
    while (i <= mid && j <= right) {
        temp[k++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];
    }
    while (i <= mid) temp[k++] = arr[i++];
    while (j <= right) temp[k++] = arr[j++];
    System.arraycopy(temp, 0, arr, left, temp.length);
}
```

## 6. Quick Sort — O(n log n) average

Chọn pivot → partition → sort 2 phần. Nhanh nhất trong thực tế.

```java
public static void quickSort(int[] arr, int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        quickSort(arr, low, pi - 1);
        quickSort(arr, pi + 1, high);
    }
}

private static int partition(int[] arr, int low, int high) {
    int pivot = arr[high];
    int i = low - 1;
    for (int j = low; j < high; j++) {
        if (arr[j] < pivot) {
            i++;
            int temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
        }
    }
    int temp = arr[i + 1]; arr[i + 1] = arr[high]; arr[high] = temp;
    return i + 1;
}
// Worst case O(n²) khi pivot luôn là min/max
// Giải pháp: Random pivot hoặc Median-of-three
```

## 7. Heap Sort — O(n log n), In-place

Xây max-heap → extract max lần lượt.

```java
public static void heapSort(int[] arr) {
    int n = arr.length;
    for (int i = n / 2 - 1; i >= 0; i--) siftDown(arr, n, i);
    for (int i = n - 1; i > 0; i--) {
        int temp = arr[0]; arr[0] = arr[i]; arr[i] = temp;
        siftDown(arr, i, 0);
    }
}

private static void siftDown(int[] arr, int n, int i) {
    while (2 * i + 1 < n) {
        int child = 2 * i + 1;
        if (child + 1 < n && arr[child + 1] > arr[child]) child++;
        if (arr[i] >= arr[child]) break;
        int temp = arr[i]; arr[i] = arr[child]; arr[child] = temp;
        i = child;
    }
}
```

## 8. Counting Sort — O(n + k)

Đếm tần suất, tính vị trí. Chỉ cho số nguyên với range hẹp.

```java
public static void countingSort(int[] arr) {
    int max = Arrays.stream(arr).max().orElse(0);
    int[] count = new int[max + 1];
    for (int num : arr) count[num]++;
    int idx = 0;
    for (int i = 0; i <= max; i++) {
        while (count[i]-- > 0) arr[idx++] = i;
    }
}
```

## 9. Java Sort

```java
// Arrays.sort() — Dual-Pivot QuickSort cho primitives
int[] arr = {5, 3, 1, 4, 2};
Arrays.sort(arr);  // [1, 2, 3, 4, 5]

// Arrays.sort() — TimSort cho Objects
Integer[] objArr = {5, 3, 1, 4, 2};
Arrays.sort(objArr);

// Collections.sort() — TimSort
List<Integer> list = Arrays.asList(5, 3, 1);
Collections.sort(list);

// Custom comparator
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);  // Sort by first element
list.sort(Comparator.comparing(String::length));  // Sort by length
```

> **TimSort** = hybrid Merge Sort + Insertion Sort. O(n log n) worst, O(n) best (already sorted). Stable.

## 10. Khi nào dùng Sort nào?

| Tình huống | Thuật toán |
|-----------|-----------|
| Dữ liệu nhỏ (< 50) | Insertion Sort |
| Cần stable sort | Merge Sort, TimSort |
| Dữ liệu lớn, general | Quick Sort (hoặc `Arrays.sort()`) |
| Bộ nhớ hạn chế | Heap Sort (O(1) space) |
| Số nguyên range hẹp | Counting Sort |
| Cần O(n log n) guaranteed | Merge Sort, Heap Sort |

> **Phỏng vấn thường hỏi:** Implement Merge Sort/Quick Sort, Sort Colors (Dutch National Flag), Merge Intervals, Sort K-Sorted Array.
