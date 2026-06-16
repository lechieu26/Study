# Array (Mảng)

## 1. Khái niệm

**Array (Mảng)** là cấu trúc dữ liệu lưu trữ các phần tử **cùng kiểu** trong một **vùng nhớ liên tiếp**. Mỗi phần tử được truy cập thông qua **chỉ số (index)**, bắt đầu từ 0.

```
Index:    0    1    2    3    4
        +----+----+----+----+----+
Array:  | 10 | 20 | 30 | 40 | 50 |
        +----+----+----+----+----+
Address: 100  104  108  112  116   (mỗi int = 4 bytes)
```

**Đặc điểm chính:**
- **Kích thước cố định** (static array) — phải khai báo trước số phần tử
- **Truy cập ngẫu nhiên O(1)** — nhờ công thức: `address = base + index × elementSize`
- **Vùng nhớ liên tiếp** — cache-friendly, hiệu suất cao trên CPU hiện đại

## 2. Khai báo và khởi tạo trong Java

```java
// Cách 1: Khai báo rồi cấp phát
int[] arr = new int[5];           // [0, 0, 0, 0, 0] — mặc định 0

// Cách 2: Khởi tạo trực tiếp
int[] arr = {10, 20, 30, 40, 50};

// Cách 3: Khai báo rồi gán
int[] arr = new int[]{10, 20, 30};

// Mảng 2D
int[][] matrix = new int[3][4];   // 3 hàng, 4 cột
int[][] matrix = {{1,2,3}, {4,5,6}, {7,8,9}};

// Lưu ý: arr.length là thuộc tính, không phải method
System.out.println(arr.length);   // 5
```

## 3. Dynamic Array (ArrayList)

**ArrayList** là mảng động — tự tăng kích thước khi cần. Bên trong nó dùng một mảng tĩnh và copy sang mảng mới lớn hơn khi đầy.

```java
ArrayList<Integer> list = new ArrayList<>();  // Default capacity: 10
list.add(10);          // Thêm cuối — amortized O(1)
list.add(0, 5);        // Thêm vào index 0 — O(n) vì phải dịch
list.get(0);           // Truy cập — O(1)
list.set(1, 99);       // Cập nhật — O(1)
list.remove(0);        // Xóa theo index — O(n)
list.size();           // Số phần tử hiện tại
list.contains(10);     // Tìm kiếm — O(n)
```

**Cơ chế mở rộng (Resizing):**
```
Capacity: 4,  Size: 4  → Đầy!
[10, 20, 30, 40]

Tạo mảng mới capacity = 4 × 1.5 = 6 (Java dùng ~1.5x)
Copy toàn bộ sang mảng mới: O(n)
[10, 20, 30, 40, __, __]
```

### So sánh Array vs ArrayList

| Thao tác | Array (static) | ArrayList (dynamic) |
|----------|---------------|-------------------|
| Truy cập index | O(1) | O(1) |
| Thêm cuối | Không hỗ trợ | O(1) amortized |
| Thêm vào giữa | O(n) | O(n) |
| Xóa phần tử | O(n) | O(n) |
| Kích thước | Cố định | Tự động tăng |
| Kiểu dữ liệu | Primitive + Object | Chỉ Object (autoboxing) |
| Bộ nhớ | Ít hơn | Nhiều hơn (wrapper objects) |

## 4. Các kỹ thuật quan trọng trên Array

### 4.1 Two Pointers (Hai con trỏ)

Dùng 2 con trỏ di chuyển trên mảng để giải quyết bài toán trong O(n) thay vì O(n²).

**Bài toán:** Tìm 2 số trong mảng **đã sắp xếp** có tổng bằng target.

```java
public int[] twoSum(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    while (left < right) {
        int sum = arr[left] + arr[right];
        if (sum == target) return new int[]{left, right};
        else if (sum < target) left++;   // Cần tổng lớn hơn → tăng left
        else right--;                     // Cần tổng nhỏ hơn → giảm right
    }
    return new int[]{-1, -1};  // Không tìm thấy
}
```

**Bài toán:** Xóa phần tử trùng lặp trong mảng sorted (in-place).

```java
public int removeDuplicates(int[] nums) {
    if (nums.length == 0) return 0;
    int slow = 0;  // Con trỏ ghi
    for (int fast = 1; fast < nums.length; fast++) {
        if (nums[fast] != nums[slow]) {
            slow++;
            nums[slow] = nums[fast];
        }
    }
    return slow + 1;  // Số phần tử unique
}
// Input:  [1, 1, 2, 2, 3] → Output: 3, arr = [1, 2, 3, ...]
```

### 4.2 Sliding Window (Cửa sổ trượt)

Duy trì một "cửa sổ" kích thước cố định hoặc thay đổi trên mảng.

**Bài toán:** Tổng lớn nhất của k phần tử liên tiếp.

```java
public int maxSumSubarray(int[] arr, int k) {
    // Tính tổng cửa sổ đầu tiên
    int windowSum = 0;
    for (int i = 0; i < k; i++) windowSum += arr[i];

    int maxSum = windowSum;
    for (int i = k; i < arr.length; i++) {
        windowSum += arr[i] - arr[i - k];  // Thêm phần tử mới, bỏ phần tử cũ
        maxSum = Math.max(maxSum, windowSum);
    }
    return maxSum;
}
```

**Bài toán:** Chuỗi con ngắn nhất có tổng ≥ target (variable-size window).

```java
public int minSubArrayLen(int target, int[] nums) {
    int left = 0, sum = 0, minLen = Integer.MAX_VALUE;
    for (int right = 0; right < nums.length; right++) {
        sum += nums[right];
        while (sum >= target) {
            minLen = Math.min(minLen, right - left + 1);
            sum -= nums[left++];  // Thu hẹp cửa sổ từ trái
        }
    }
    return minLen == Integer.MAX_VALUE ? 0 : minLen;
}
```

### 4.3 Prefix Sum (Tổng tiền tố)

Tiền xử lý mảng để trả lời nhanh các truy vấn tổng đoạn.

```java
// Xây dựng mảng prefix sum
int[] prefix = new int[arr.length + 1];
for (int i = 0; i < arr.length; i++) {
    prefix[i + 1] = prefix[i] + arr[i];
}
// Tổng đoạn [l, r] = prefix[r+1] - prefix[l]

// Ví dụ: arr = [2, 4, 1, 3, 5]
// prefix  = [0, 2, 6, 7, 10, 15]
// Tổng [1,3] = prefix[4] - prefix[1] = 10 - 2 = 8 (= 4+1+3)
```

**Bài toán:** Số lượng subarray có tổng bằng k.

```java
public int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> prefixCount = new HashMap<>();
    prefixCount.put(0, 1);  // Empty prefix
    int sum = 0, count = 0;

    for (int num : nums) {
        sum += num;
        // Nếu tồn tại prefix = sum - k → có subarray tổng = k
        count += prefixCount.getOrDefault(sum - k, 0);
        prefixCount.merge(sum, 1, Integer::sum);
    }
    return count;
}
```

### 4.4 Kadane's Algorithm

Tìm subarray có tổng lớn nhất — O(n) time, O(1) space.

```java
public int maxSubArray(int[] nums) {
    int maxSum = nums[0];
    int currentSum = nums[0];

    for (int i = 1; i < nums.length; i++) {
        // Hoặc mở rộng subarray hiện tại, hoặc bắt đầu subarray mới
        currentSum = Math.max(nums[i], currentSum + nums[i]);
        maxSum = Math.max(maxSum, currentSum);
    }
    return maxSum;
}
// arr = [-2, 1, -3, 4, -1, 2, 1, -5, 4]
// maxSubArray = [4, -1, 2, 1] = 6
```

## 5. Xử lý mảng 2D (Matrix)

```java
// Duyệt matrix
int[][] matrix = {{1,2,3}, {4,5,6}, {7,8,9}};
int rows = matrix.length;        // 3
int cols = matrix[0].length;     // 3

// Xoay matrix 90 độ (clockwise) — in-place
public void rotate(int[][] matrix) {
    int n = matrix.length;
    // Bước 1: Transpose (đổi hàng thành cột)
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            int temp = matrix[i][j];
            matrix[i][j] = matrix[j][i];
            matrix[j][i] = temp;
        }
    }
    // Bước 2: Đảo ngược từng hàng
    for (int[] row : matrix) {
        int l = 0, r = n - 1;
        while (l < r) {
            int temp = row[l]; row[l] = row[r]; row[r] = temp;
            l++; r--;
        }
    }
}
```

## 6. Phân tích độ phức tạp

| Thao tác | Static Array | ArrayList |
|----------|-------------|-----------|
| Truy cập `arr[i]` | O(1) | O(1) |
| Thêm cuối | N/A | O(1) amortized |
| Thêm vào index i | O(n) | O(n) |
| Xóa phần tử | O(n) | O(n) |
| Tìm kiếm (unsorted) | O(n) | O(n) |
| Tìm kiếm (sorted) | O(log n) | O(log n) |
| Space | O(n) | O(n) |

## 7. Khi nào dùng Array?

| Dùng Array khi | Không nên dùng khi |
|---------------|-------------------|
| Cần truy cập ngẫu nhiên O(1) | Thêm/xóa ở đầu/giữa thường xuyên |
| Biết trước kích thước | Kích thước thay đổi nhiều |
| Dữ liệu kiểu primitive | Cần thêm/xóa ở hai đầu → dùng Deque |
| Cần hiệu suất cache tốt | Dữ liệu rất lớn cần resize → cân nhắc LinkedList |

> **Phỏng vấn thường hỏi:** Two Sum, Three Sum, Maximum Subarray (Kadane), Merge Intervals, Product of Array Except Self, Container With Most Water, Trapping Rain Water.
