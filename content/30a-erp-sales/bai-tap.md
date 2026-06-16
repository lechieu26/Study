# Module Bán Hàng - Bài Tập

## Bài 1: CRUD Khách hàng (Customer)
**Độ khó: Trung bình**

Xây dựng chức năng quản lý khách hàng hoàn chỉnh:

**Backend (Spring Boot):**
1. Tạo `CustomerEntity` với các field: id, code, name, phone, email, address, taxCode
2. Tạo `CustomerDTO` và `CustomerRepository`
3. Tạo `CustomerService` với các method: findAll (phân trang + tìm kiếm), findById, create, update, delete
4. Tạo `CustomerController` với REST endpoints tại `/api/sales/customers`
5. Validate: code bắt buộc, name bắt buộc, email hợp lệ (nếu có), code không trùng

**Frontend (React):**
6. Tạo trang `CustomerList` hiển thị danh sách khách hàng trong bảng
7. Thêm ô tìm kiếm theo tên hoặc mã khách hàng
8. Nút "Thêm mới" mở Modal form nhập thông tin
9. Nút Sửa/Xóa trên mỗi dòng
10. Hiển thị thông báo toast khi thao tác thành công/thất bại

**Đầu vào:** Thông tin khách hàng (code, name, phone, email, address, taxCode)
**Đầu ra:** Danh sách khách hàng, CRUD hoạt động, tìm kiếm và phân trang hoạt động

---

## Bài 2: Tạo Báo giá (Quotation)
**Độ khó: Trung bình - Khó**

Xây dựng chức năng tạo và quản lý báo giá:

**Backend:**
1. Tạo Entity: `QuotationEntity` (header) và `QuotationItemEntity` (chi tiết)
2. API tạo báo giá: nhận customerId, validUntil, danh sách items (productId, quantity)
3. Tự động tính: unitPrice từ bảng Product, amount = quantity × unitPrice, totalAmount = sum(amount)
4. Mã báo giá tự sinh: QT-{năm}-{số thứ tự 4 chữ số} (VD: QT-2024-0001)
5. API cập nhật trạng thái: DRAFT → SENT → ACCEPTED/REJECTED

**Frontend:**
6. Trang `QuotationList`: bảng hiển thị báo giá, filter theo trạng thái, badge màu theo status
7. Form `QuotationForm`: chọn khách hàng, thêm/xóa sản phẩm (dynamic rows)
8. Khi chọn sản phẩm → tự động fill đơn giá, tính thành tiền
9. Hiển thị tổng tiền realtime khi thay đổi số lượng
10. Nút "Gửi báo giá" chuyển status từ DRAFT → SENT

**Đầu vào:** Khách hàng, danh sách sản phẩm + số lượng
**Đầu ra:** Báo giá được tạo với mã tự sinh, tổng tiền tính đúng, có thể chuyển trạng thái

---

## Bài 3: Đơn hàng (Sales Order)
**Độ khó: Khó**

Xây dựng chức năng quản lý đơn hàng bán:

**Backend:**
1. Tạo Entity: `SalesOrderEntity`, `SalesOrderItemEntity`
2. API tạo đơn hàng: có thể tạo mới hoặc convert từ Quotation (copy items)
3. API chuyển trạng thái: DRAFT → CONFIRMED → DELIVERING → DELIVERED → INVOICED → COMPLETED
4. Validate: chỉ cho phép chuyển trạng thái hợp lệ (VD: DRAFT chỉ được → CONFIRMED hoặc CANCELLED)
5. Khi chuyển DELIVERING: kiểm tra tồn kho đủ không (gọi InventoryService)

**Frontend:**
6. `SalesOrderList`: bảng đơn hàng, filter theo status, hiển thị badge màu
7. `SalesOrderForm`: form tạo đơn, chọn từ báo giá có sẵn hoặc tạo mới
8. `SalesOrderDetail`: xem chi tiết đơn, lịch sử trạng thái, nút chuyển trạng thái
9. Timeline hiển thị lịch sử thay đổi trạng thái
10. In đơn hàng (print-friendly layout)

**Đầu vào:** Thông tin đơn hàng hoặc ID báo giá để convert
**Đầu ra:** Đơn hàng được tạo, flow trạng thái hoạt động đúng, tích hợp kiểm tra tồn kho

---

## Bài 4: Hóa đơn & Thanh toán (Invoice & Payment)
**Độ khó: Khó**

Xây dựng chức năng xuất hóa đơn và ghi nhận thanh toán:

**Backend:**
1. `InvoiceEntity` với: code, salesOrderId, customerId, invoiceDate, dueDate, totalAmount, taxAmount, paidAmount, status
2. API tạo hóa đơn từ đơn hàng đã giao (DELIVERED)
3. Tự động tính VAT 10%: taxAmount = totalAmount × 10%
4. `PaymentEntity`: invoiceId, paymentDate, amount, paymentMethod, reference
5. API ghi nhận thanh toán: cập nhật paidAmount, tự động chuyển status (PARTIALLY_PAID / PAID)
6. Khi hóa đơn PAID → cập nhật đơn hàng thành COMPLETED

**Frontend:**
7. `InvoiceList`: danh sách hóa đơn, filter PAID/UNPAID/OVERDUE
8. Chi tiết hóa đơn: hiển thị thông tin + lịch sử thanh toán
9. Form ghi nhận thanh toán: số tiền, phương thức (Tiền mặt/CK/Séc), ngày thanh toán
10. Progress bar hiển thị % đã thanh toán
11. Highlight hóa đơn quá hạn (due_date < today) bằng màu đỏ

**Đầu vào:** ID đơn hàng (tạo HĐ), thông tin thanh toán
**Đầu ra:** Hóa đơn VAT, thanh toán nhiều lần, tự động cập nhật trạng thái

---

## Bài 5: Báo cáo Công nợ Khách hàng
**Độ khó: Trung bình**

Xây dựng báo cáo theo dõi công nợ:

**Backend:**
1. API `/api/sales/debts`: trả về danh sách khách hàng cùng số tiền nợ
2. Query: JOIN invoices + customers, GROUP BY customer, tính tổng nợ = tổng HĐ - tổng đã TT
3. Thêm filter: theo khoảng thời gian, theo khách hàng
4. API `/api/sales/debts/summary`: tổng công nợ, số KH nợ, số HĐ quá hạn

**Frontend:**
5. Trang báo cáo công nợ: bảng tổng hợp với các cột: Mã KH, Tên, Tổng HĐ, Đã TT, Còn nợ, Số HĐ quá hạn
6. Card tổng hợp phía trên: Tổng công nợ, Số KH đang nợ, Số HĐ quá hạn
7. Highlight dòng có HĐ quá hạn bằng màu đỏ
8. Biểu đồ Pie chart: phân bổ công nợ theo khách hàng (top 5)
9. Export báo cáo ra file CSV/Excel

**Đầu vào:** Bộ lọc thời gian, khách hàng
**Đầu ra:** Bảng công nợ tổng hợp, biểu đồ trực quan, có thể export
