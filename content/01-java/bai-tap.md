# Java - Bài Tập (Trung Bình đến Khó)

## Bài 1: Quản lý Chuỗi Ký Tự
**Độ khó: Trung bình**

Viết chương trình kiểm tra xem một chuỗi có phải là **Palindrome** (chuỗi đối xứng) hay không, bỏ qua khoảng trắng, dấu câu và không phân biệt hoa thường.

**Đầu vào:** `"A man, a plan, a canal: Panama"`
**Đầu ra:** `true`

**Đầu vào:** `"race a car"`
**Đầu ra:** `false`

---

## Bài 2: Tìm Phần Tử Xuất Hiện Nhiều Nhất
**Độ khó: Trung bình**

Cho một mảng số nguyên, tìm phần tử xuất hiện nhiều hơn ⌊n/2⌋ lần (majority element). Đảm bảo phần tử luôn tồn tại.

**Đầu vào:** `[2, 2, 1, 1, 1, 2, 2]`
**Đầu ra:** `2`

---

## Bài 3: Nhóm Anagram
**Độ khó: Trung bình - Khó**

Cho một mảng các chuỗi, nhóm các chuỗi là anagram của nhau lại với nhau. Anagram là các chuỗi có cùng các ký tự nhưng khác thứ tự.

**Đầu vào:** `["eat", "tea", "tan", "ate", "nat", "bat"]`
**Đầu ra:** `[["eat","tea","ate"], ["tan","nat"], ["bat"]]`

---

## Bài 4: Hệ Thống Quản Lý Sinh Viên với Generics
**Độ khó: Khó**

Thiết kế hệ thống quản lý sinh viên sử dụng Generics và Collections:
1. Tạo class `SinhVien` với các thuộc tính: maSV, hoTen, diem (List<Double>).
2. Tạo interface `DanhSachQuanLy<T>` với các phương thức: them, xoa, timKiem, sapXep.
3. Implement `DanhSachSinhVien` với các chức năng:
   - Thêm/Xóa sinh viên
   - Tìm kiếm theo mã hoặc tên (hỗ trợ tìm gần đúng)
   - Sắp xếp theo điểm trung bình (sử dụng Comparator)
   - Lọc sinh viên đạt/không đạt (điểm TB >= 5.0)
   - Thống kê: điểm cao nhất, thấp nhất, trung bình lớp
4. Sử dụng Stream API để thực hiện các thao tác trên.

---

## Bài 5: Thread-Safe Bounded Queue
**Độ khó: Khó**

Implement một hàng đợi có giới hạn kích thước (Bounded Queue) an toàn với đa luồng:
1. Hỗ trợ các thao tác: `enqueue(T item)`, `dequeue()`, `peek()`, `size()`.
2. `enqueue` phải chờ (block) khi hàng đợi đầy.
3. `dequeue` phải chờ (block) khi hàng đợi rỗng.
4. Sử dụng `ReentrantLock` và `Condition` thay vì `synchronized`.
5. Viết test mô phỏng Producer-Consumer với nhiều thread.

---

## Bài 6: Stream API Nâng Cao - Xử Lý Dữ Liệu
**Độ khó: Trung bình - Khó**

Cho danh sách nhân viên với các thuộc tính: id, ten, phongBan, luong, ngayVaoLam.
Sử dụng Stream API để:
1. Tìm nhân viên có lương cao nhất mỗi phòng ban.
2. Tính tổng lương theo từng phòng ban.
3. Nhóm nhân viên theo khoảng lương (< 10tr, 10-20tr, > 20tr).
4. Tìm phòng ban có lương trung bình cao nhất.
5. Lấy top 3 nhân viên có thâm niên lâu nhất.
6. Tạo báo cáo dạng String: "PhongBan: [danh sách tên] - Tổng lương: X".

---

## Bài 7: Custom Exception và Hệ Thống Ngân Hàng
**Độ khó: Khó**

Xây dựng hệ thống ngân hàng đơn giản:
1. Tạo các custom exception: `SoDuKhongDuException`, `TaiKhoanKhongTonTaiException`, `SoTienKhongHopLeException`.
2. Tạo class `TaiKhoanNganHang` với: soTaiKhoan, chuTaiKhoan, soDu, lichSuGiaoDich.
3. Implement các thao tác: guiTien, rutTien, chuyenKhoan.
4. Mỗi giao dịch phải ghi log với thời gian, loại giao dịch, số tiền, số dư còn lại.
5. Tạo class `NganHang` quản lý danh sách tài khoản với các chức năng tìm kiếm, thống kê.
6. Đảm bảo thread-safe cho các thao tác chuyển khoản.

---

## Bài 8: Triển khai Iterator Pattern với Generic
**Độ khó: Khó**

Tạo cấu trúc dữ liệu `CayNhiPhan<T extends Comparable<T>>`:
1. Implement các phương thức: them, xoa, timKiem, demNode.
2. Implement `Iterable<T>` để hỗ trợ for-each.
3. Cung cấp 3 kiểu duyệt: inOrder, preOrder, postOrder (trả về Iterator).
4. Implement phương thức `stream()` trả về Stream<T>.
5. Hỗ trợ serialization để lưu/đọc cây từ file.

---

## Bài 9: File Processing Pipeline
**Độ khó: Trung bình - Khó**

Xây dựng pipeline xử lý file văn bản:
1. Đọc file CSV chứa dữ liệu sản phẩm (id, ten, loai, gia, soLuong).
2. Phân tích và tạo báo cáo:
   - Top 5 sản phẩm đắt nhất.
   - Tổng giá trị tồn kho theo từng loại.
   - Sản phẩm cần nhập thêm (số lượng < 10).
3. Xuất báo cáo ra file TXT và JSON.
4. Sử dụng try-with-resources và NIO.
5. Xử lý các exception: file không tồn tại, dữ liệu sai định dạng.

---

## Bài 10: Design Mini Framework với Reflection và Annotation
**Độ khó: Rất Khó**

Tạo một mini dependency injection framework:
1. Tạo annotation `@Component`, `@Inject`, `@Singleton`.
2. Tạo class `Container` quản lý các component:
   - Quét package để tìm class có `@Component`.
   - Tự động tạo instance và inject dependencies.
   - Hỗ trợ Singleton scope.
3. Sử dụng Reflection API để:
   - Tìm constructor phù hợp.
   - Inject qua constructor hoặc field.
   - Xử lý circular dependency (phát hiện và throw exception).
4. Viết test sử dụng framework.
