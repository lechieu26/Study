# Chương 3: Điều kiện và vòng lặp

## 1. Câu lệnh `if`

Dùng `if` khi chương trình cần rẽ nhánh theo điều kiện.

```cpp
if (score >= 5) {
    std::cout << "Đậu\n";
} else {
    std::cout << "Rớt\n";
}
```

Điều kiện là biểu thức có giá trị đúng/sai.

## 2. `else if`

Dùng khi có nhiều mức điều kiện.

```cpp
if (score >= 8) {
    std::cout << "Giỏi\n";
} else if (score >= 6.5) {
    std::cout << "Khá\n";
} else if (score >= 5) {
    std::cout << "Trung bình\n";
} else {
    std::cout << "Yếu\n";
}
```

Thứ tự điều kiện rất quan trọng. Điều kiện lớn hơn nên đặt trước trong ví dụ trên.

## 3. `switch`

Dùng `switch` khi so sánh một giá trị rồi chia thành nhiều trường hợp rõ ràng.

```cpp
switch (choice) {
case 1:
    std::cout << "Thêm\n";
    break;
case 2:
    std::cout << "Xóa\n";
    break;
default:
    std::cout << "Không hợp lệ\n";
    break;
}
```

Nếu quên `break`, chương trình có thể chạy tiếp sang case sau.

## 4. Vòng lặp `for`

Dùng khi biết trước số lần lặp.

```cpp
for (int i = 1; i <= 10; ++i) {
    std::cout << i << "\n";
}
```

Nên dùng `++i` như thói quen tốt. Với kiểu cơ bản, `++i` và `i++` gần như không khác về hiệu năng.

## 5. Vòng lặp `while`

Dùng khi chưa biết trước số lần lặp, lặp đến khi điều kiện sai.

```cpp
while (password != "123456") {
    std::cin >> password;
}
```

## 6. Vòng lặp `do while`

Chạy thân vòng lặp ít nhất một lần.

```cpp
do {
    std::cout << "Nhập lựa chọn: ";
    std::cin >> choice;
} while (choice < 1 || choice > 3);
```

## 7. `break` và `continue`

- `break`: thoát khỏi vòng lặp.
- `continue`: bỏ qua phần còn lại của lần lặp hiện tại, sang lần lặp tiếp theo.

Dùng chúng có mục đích rõ ràng, tránh làm luồng chương trình khó đọc.

## 8. Lỗi thường gặp

- Vòng lặp vô hạn do quên cập nhật biến đếm.
- Sai điều kiện biên: dùng `<` thay vì `<=`.
- Quên `break` trong `switch`.
- Viết điều kiện quá dài, nên tách thành biến có tên.

---

# Ví dụ thực hành

# Ví dụ chương 3

## 1. Kiểm tra chẵn lẻ

```cpp
#include <iostream>

int main() {
    int n;
    std::cout << "Nhập n: ";
    std::cin >> n;

    if (n % 2 == 0) {
        std::cout << "Số chẵn\n";
    } else {
        std::cout << "Số lẻ\n";
    }

    return 0;
}
```

## 2. Tính tổng từ 1 đến n

```cpp
#include <iostream>

int main() {
    int n;
    std::cin >> n;

    long long sum = 0;
    for (int i = 1; i <= n; ++i) {
        sum += i;
    }

    std::cout << "Tổng = " << sum << "\n";
    return 0;
}
```

## 3. Menu lặp lại đến khi thoát

```cpp
#include <iostream>

int main() {
    int choice;

    do {
        std::cout << "1. Xin chào\n";
        std::cout << "2. Tính 2 + 3\n";
        std::cout << "0. Thoát\n";
        std::cout << "Chọn: ";
        std::cin >> choice;

        switch (choice) {
        case 1:
            std::cout << "Xin chào!\n";
            break;
        case 2:
            std::cout << "2 + 3 = 5\n";
            break;
        case 0:
            std::cout << "Tạm biệt\n";
            break;
        default:
            std::cout << "Lựa chọn không hợp lệ\n";
            break;
        }
    } while (choice != 0);

    return 0;
}
```

## 4. Bài tập

1. Nhập n, in các số chẵn từ 1 đến n.
2. Nhập n, tính giai thừa n.
3. Nhập một số, kiểm tra có phải số nguyên tố không.
4. Viết menu máy tính: cộng, trừ, nhân, chia, thoát.
