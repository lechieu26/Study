# Module Mua Hàng - Bài Tập

## Bài 1: CRUD Nhà cung cấp (Supplier)
**Độ khó: Trung bình**

Xây dựng chức năng quản lý nhà cung cấp:

**Backend (Spring Boot):**
1. Tạo `SupplierEntity` với: id, code, name, phone, email, address, taxCode, contactPerson
2. Tạo `SupplierDTO`, `SupplierRepository`, `SupplierService`, `SupplierController`
3. REST API tại `/api/procurement/suppliers`: GET (phân trang + search), GET/{id}, POST, PUT, DELETE
4. Validate: code và name bắt buộc, code không trùng

**Frontend (React):**
5. Trang `SupplierList` với bảng dữ liệu, tìm kiếm, phân trang
6. Modal form thêm/sửa nhà cung cấp
7. Nút xóa với xác nhận

**Đầu vào:** Thông tin NCC
**Đầu ra:** CRUD NCC hoạt động hoàn chỉnh

---

## Bài 2: Yêu cầu mua hàng (Purchase Request)
**Độ khó: Trung bình**

Xây dựng chức năng tạo và duyệt yêu cầu mua hàng:

**Backend:**
1. Entity: `PurchaseRequestEntity` (header) + `PurchaseRequestItemEntity` (items)
2. API tạo YCMH: chọn sản phẩm, số lượng, lý do mua
3. API duyệt: PUT `/api/procurement/requests/{id}/approve` (trưởng phòng duyệt)
4. API từ chối: PUT `/api/procurement/requests/{id}/reject` (kèm lý do)
5. Mã tự sinh: PR-{năm}-{thứ tự}

**Frontend:**
6. Danh sách YCMH với filter trạng thái (PENDING / APPROVED / REJECTED)
7. Form tạo YCMH: chọn sản phẩm, nhập số lượng (dynamic rows)
8. Nút Duyệt/Từ chối trên trang chi tiết (chỉ hiện cho role Manager)
9. Badge màu theo trạng thái: vàng (Pending), xanh (Approved), đỏ (Rejected)

**Đầu vào:** Danh sách sản phẩm cần mua + số lượng + lý do
**Đầu ra:** YCMH được tạo, quy trình duyệt hoạt động

---

## Bài 3: Đơn đặt hàng (Purchase Order)
**Độ khó: Khó**

Xây dựng chức năng quản lý PO:

**Backend:**
1. Entity: `PurchaseOrderEntity` + `PurchaseOrderItemEntity`
2. Tạo PO mới: chọn NCC, thêm sản phẩm với đơn giá mua (cost price)
3. Tạo PO từ YCMH đã duyệt: copy items, bổ sung NCC và đơn giá
4. Chuyển trạng thái: DRAFT → SENT → CONFIRMED → RECEIVED → COMPLETED
5. Tính tổng tiền tự động: sum(quantity × unit_price)

**Frontend:**
6. Danh sách PO với filter trạng thái
7. Form tạo PO: chọn NCC, thêm sản phẩm (hoặc import từ YCMH)
8. Chi tiết PO: hiển thị items, trạng thái, nút chuyển trạng thái
9. Nút "Nhập hàng" khi PO ở trạng thái CONFIRMED

**Đầu vào:** NCC, sản phẩm, số lượng, đơn giá
**Đầu ra:** PO hoạt động, flow trạng thái đúng, có thể tạo từ YCMH

---

## Bài 4: Nhập hàng (Goods Receipt)
**Độ khó: Khó**

Xây dựng chức năng nhập hàng từ PO:

**Backend:**
1. Entity: `GoodsReceiptEntity` + `GoodsReceiptItemEntity`
2. Tạo phiếu nhập từ PO: load items từ PO, cho nhập SL thực nhận
3. Validate: SL nhận ≤ SL đặt, kho nhập phải tồn tại
4. Khi xác nhận: tăng tồn kho (gọi InventoryService), cập nhật PO status
5. Xử lý nhận thiếu: PO → PARTIALLY_RECEIVED, cho phép nhập nhiều lần

**Frontend:**
6. Từ PO detail → Nút "Nhập hàng" → mở form nhập
7. Form hiển thị: tên SP, SL đặt, input SL thực nhận, ghi chú
8. Mặc định SL thực nhận = SL đặt, user có thể sửa
9. Chọn kho nhập từ dropdown
10. Sau nhập xong → hiển thị thông báo + redirect về PO detail

**Đầu vào:** PO ID, SL thực nhận cho từng sản phẩm, kho nhập
**Đầu ra:** Phiếu nhập kho được tạo, tồn kho tăng, PO cập nhật trạng thái

---

## Bài 5: Báo cáo công nợ phải trả
**Độ khó: Trung bình**

Xây dựng báo cáo công nợ nhà cung cấp:

**Backend:**
1. API `/api/procurement/debts`: tổng hợp công nợ phải trả theo NCC
2. API `/api/procurement/debts/summary`: tổng công nợ, số NCC nợ
3. Query: JOIN purchase_orders + suppliers, GROUP BY supplier
4. Filter: khoảng thời gian, NCC cụ thể

**Frontend:**
5. Bảng công nợ: Mã NCC, Tên NCC, Tổng PO, Đã TT, Còn nợ
6. Card tổng hợp: Tổng nợ phải trả, Số NCC đang nợ
7. Biểu đồ Bar chart: top 5 NCC nợ nhiều nhất
8. Export CSV

**Đầu vào:** Bộ lọc thời gian
**Đầu ra:** Báo cáo công nợ phải trả tổng hợp
