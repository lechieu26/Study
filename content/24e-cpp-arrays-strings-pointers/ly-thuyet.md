# Chương 5: Mảng, chuỗi và con trỏ

## 1. Mảng tĩnh

Mảng là tập hợp nhiều phần tử cùng kiểu, nằm liên tiếp trong bộ nhớ.

```cpp
int numbers[5] = {1, 2, 3, 4, 5};
```

Chỉ số bắt đầu từ 0. Phần tử cuối của mảng 5 phần tử là `numbers[4]`.

Truy cập vượt biên là lỗi nguy hiểm:

```cpp
numbers[5] = 10; // sai
```

## 2. Mảng và vòng lặp

```cpp
for (int i = 0; i < 5; ++i) {
    std::cout << numbers[i] << "\n";
}
```

Điều kiện dừng thường là `i < size`, không phải `i <= size`.

## 3. Chuỗi C

Chuỗi C là mảng ký tự kết thúc bằng ký tự null `'\0'`.

```c
char name[20] = "An";
```

Nếu quên cho đủ chỗ cho `'\0'`, chuỗi có thể gây lỗi đọc quá bộ nhớ.

## 4. `std::string`

Trong C++, nên ưu tiên `std::string` thay vì chuỗi C khi không có lý do đặc biệt.

```cpp
std::string name = "Nguyễn Văn A";
std::cout << name.size();
```

## 5. Con trỏ

Con trỏ là biến lưu địa chỉ của biến khác.

```cpp
int x = 10;
int* p = &x;
```

- `&x`: lấy địa chỉ của `x`.
- `p`: địa chỉ đang lưu.
- `*p`: giá trị tại địa chỉ đó.

```cpp
*p = 20; // x thành 20
```

## 6. Con trỏ null

Con trỏ không trỏ đến đâu nên gán `nullptr`.

```cpp
int* p = nullptr;
```

Không được dereference con trỏ null:

```cpp
*p = 5; // lỗi runtime
```

## 7. Cấp phát động

C:

```c
int* a = malloc(10 * sizeof(int));
free(a);
```

C++ cũ:

```cpp
int* a = new int[10];
delete[] a;
```

C++ hiện đại nên ưu tiên `std::vector`, `std::unique_ptr`, `std::shared_ptr` thay vì quản lý `new/delete` thủ công.

## 8. Lỗi thường gặp

- Truy cập mảng vượt biên.
- Dùng con trỏ chưa khởi tạo.
- Quên giải phóng bộ nhớ.
- Giải phóng hai lần.
- Dùng con trỏ sau khi đã `delete`.

---

# Ví dụ thực hành

# Ví dụ chương 5

## 1. Tìm giá trị lớn nhất trong mảng

```cpp
#include <iostream>

int main() {
    int a[] = {4, 9, 1, 7, 3};
    int size = 5;
    int maxValue = a[0];

    for (int i = 1; i < size; ++i) {
        if (a[i] > maxValue) {
            maxValue = a[i];
        }
    }

    std::cout << "Max = " << maxValue << "\n";
    return 0;
}
```

## 2. Đảo ngược chuỗi với `std::string`

```cpp
#include <iostream>
#include <string>

int main() {
    std::string text = "hello";

    for (int left = 0, right = static_cast<int>(text.size()) - 1; left < right; ++left, --right) {
        char temp = text[left];
        text[left] = text[right];
        text[right] = temp;
    }

    std::cout << text << "\n";
    return 0;
}
```

## 3. Con trỏ trỏ vào biến

```cpp
#include <iostream>

int main() {
    int x = 10;
    int* p = &x;

    std::cout << "Địa chỉ: " << p << "\n";
    std::cout << "Giá trị: " << *p << "\n";

    *p = 25;
    std::cout << "x = " << x << "\n";
    return 0;
}
```

## 4. Dùng `std::vector` thay mảng động

```cpp
#include <iostream>
#include <vector>

int main() {
    int n;
    std::cin >> n;

    std::vector<int> numbers(n);
    for (int i = 0; i < n; ++i) {
        std::cin >> numbers[i];
    }

    long long sum = 0;
    for (int value : numbers) {
        sum += value;
    }

    std::cout << "Tổng = " << sum << "\n";
    return 0;
}
```

## 5. Bài tập

1. Nhập n số nguyên, tính tổng và trung bình.
2. Đếm số lần xuất hiện của một ký tự trong chuỗi.
3. Tìm vị trí đầu tiên của giá trị x trong mảng.
4. Viết hàm nhận con trỏ `int*` và tăng giá trị biến lên 1.
