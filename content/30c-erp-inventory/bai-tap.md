# Module Kho - Bài Tập

## Bài 1: Quản lý Kho hàng (Warehouse CRUD)
**Độ khó: Trung bình**

Xây dựng chức năng quản lý danh sách kho hàng:

**Backend:**
1. Entity `WarehouseEntity`: id, code, name, address, managerId (FK→users), active
2. REST API: GET (list + search), POST, PUT, DELETE tại `/api/inventory/warehouses`
3. Validate: code unique, name required

**Frontend:**
4. Trang danh sách kho: bảng hiển thị, tìm kiếm
5. Modal form thêm/sửa kho
6. Dropdown chọn người quản lý từ danh sách users

**Đầu vào:** Thông tin kho hàng
**Đầu ra:** CRUD kho hàng hoạt động

---

## Bài 2: Xem tồn kho Realtime
**Độ khó: Trung bình**

Xây dựng trang tổng quan tồn kho:

**Backend:**
1. Bảng `stock`: warehouse_id, product_id, quantity (UNIQUE warehouse+product)
2. API `/api/inventory/stock`: trả về danh sách tồn kho, JOIN product + warehouse info
3. API `/api/inventory/stock?warehouseId=1`: filter theo kho
4. API `/api/inventory/alerts`: SP có quantity ≤ min_stock

**Frontend:**
5. Trang tồn kho: bảng SP | Kho | SL | Min | Trạng thái
6. Badge trạng thái: xanh (Đủ), vàng (Sắp hết), đỏ (Hết hàng)
7. Filter dropdown theo kho
8. Banner cảnh báo phía trên khi có SP dưới mức min

**Đầu vào:** Bộ lọc kho
**Đầu ra:** Bảng tồn kho realtime với badge trạng thái và cảnh báo

---

## Bài 3: Chuyển kho (Stock Transfer)
**Độ khó: Trung bình - Khó**

Xây dựng chức năng chuyển hàng giữa các kho:

**Backend:**
1. Entity: `StockTransferEntity` (header) + `StockTransferItemEntity` (items)
2. API tạo phiếu chuyển: chọn kho nguồn, kho đích, danh sách SP + SL
3. Khi xác nhận: giảm tồn kho nguồn, tăng tồn kho đích (trong 1 transaction)
4. Validate: kho nguồn ≠ kho đích, SL chuyển ≤ tồn kho nguồn
5. Ghi log vào stock_transactions

**Frontend:**
6. Form chuyển kho: chọn kho nguồn → kho đích → thêm SP + SL
7. Hiển thị tồn kho hiện tại của kho nguồn khi chọn SP
8. Danh sách phiếu chuyển kho đã tạo
9. Nút xác nhận chuyển kho

**Đầu vào:** Kho nguồn, kho đích, danh sách SP cần chuyển
**Đầu ra:** Phiếu chuyển kho, tồn kho 2 kho cập nhật đúng

---

## Bài 4: Kiểm kê hàng tồn
**Độ khó: Khó**

Xây dựng chức năng kiểm kê:

**Backend:**
1. Entity: `InventoryCheckEntity` + `InventoryCheckItemEntity`
2. Tạo phiếu kiểm kê: chọn kho → load tất cả SP có tồn, pre-fill SL hệ thống
3. Lưu SL thực đếm (actual_quantity), tự tính difference
4. Xác nhận: tự động điều chỉnh tồn kho (tăng/giảm theo difference)
5. Ghi log transaction type = ADJUST

**Frontend:**
6. Form kiểm kê: bảng SP | SL hệ thống | SL thực đếm (input) | Chênh lệch (auto)
7. Highlight dòng có chênh lệch: xanh (+), đỏ (-)
8. Tổng kết: bao nhiêu SP đúng, bao nhiêu chênh lệch
9. Nút xác nhận kiểm kê

**Đầu vào:** Kho kiểm kê, SL thực đếm cho từng SP
**Đầu ra:** Phiếu kiểm kê, tồn kho tự điều chỉnh

---

## Bài 5: Lịch sử nhập xuất (Stock Card)
**Độ khó: Trung bình**

Xây dựng thẻ kho theo dõi lịch sử:

**Backend:**
1. API `/api/inventory/transactions`: lịch sử nhập xuất, filter theo SP + kho + thời gian
2. API `/api/inventory/stock-card?productId=1&warehouseId=1`: thẻ kho chi tiết
3. Trả về: ngày, loại, SL nhập, SL xuất, tồn cuối kỳ (running balance)

**Frontend:**
4. Trang lịch sử: chọn SP + kho → hiển thị thẻ kho
5. Bảng: Ngày | Loại | Nguồn | Nhập | Xuất | Tồn
6. Filter theo khoảng thời gian
7. Biểu đồ Line chart: biến động tồn kho theo thời gian

**Đầu vào:** SP, kho, khoảng thời gian
**Đầu ra:** Thẻ kho chi tiết với running balance và biểu đồ
