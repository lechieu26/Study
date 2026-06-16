# Module Sản Xuất - Bài Tập

## Bài 1: CRUD BOM (Bill of Materials)
**Độ khó: Trung bình**

Xây dựng chức năng quản lý công thức sản xuất:

**Backend:**
1. Entity: `BomEntity` (header: productId, name, version) + `BomItemEntity` (materialId, quantity)
2. API: GET list, GET detail, POST create, PUT update, DELETE
3. Validate: thành phẩm (productId) bắt buộc, phải có ít nhất 1 NVL, NVL ≠ thành phẩm

**Frontend:**
4. Danh sách BOM: tên, thành phẩm, số NVL, giá vốn SX
5. Form tạo BOM: chọn thành phẩm, thêm NVL (dynamic rows), tự tính giá vốn
6. Chi tiết BOM: hiển thị cây NVL

**Đầu vào:** Thành phẩm, danh sách NVL + SL
**Đầu ra:** BOM hoạt động, giá vốn tính đúng

---

## Bài 2: Kiểm tra NVL khả dụng
**Độ khó: Trung bình**

Xây dựng chức năng kiểm tra NVL trước khi sản xuất:

**Backend:**
1. API POST `/api/manufacturing/boms/{id}/check-materials`: nhận SL cần SX
2. Tính NVL cần = BOM items × SL sản xuất
3. So sánh với tồn kho hiện tại (gọi InventoryService)
4. Trả về: mỗi NVL có cần/tồn/thiếu, flag allAvailable

**Frontend:**
5. Từ BOM detail: input SL cần SX + nút "Kiểm tra"
6. Hiển thị bảng: NVL | Cần | Tồn kho | Thiếu
7. Highlight đỏ dòng NVL thiếu
8. Nút "Tạo YCMH" cho NVL thiếu (link sang module Procurement)

**Đầu vào:** BOM ID, SL sản xuất
**Đầu ra:** Bảng kiểm tra NVL, đánh dấu thiếu/đủ

---

## Bài 3: Lệnh sản xuất (Work Order)
**Độ khó: Khó**

Xây dựng chức năng quản lý lệnh sản xuất end-to-end:

**Backend:**
1. Entity: `WorkOrderEntity` (bomId, productId, warehouseId, plannedQty, actualQty, status)
2. Tạo WO: chọn BOM + SL + kho + ngày bắt đầu/kết thúc dự kiến
3. Start SX (CONFIRMED → IN_PROGRESS): xuất NVL từ kho theo BOM
4. Complete SX (IN_PROGRESS → COMPLETED): nhập thành phẩm vào kho, ghi actualQuantity
5. Flow trạng thái: DRAFT → CONFIRMED → IN_PROGRESS → COMPLETED

**Frontend:**
6. Danh sách WO: mã, thành phẩm, SL, trạng thái, tiến độ
7. Form tạo WO: chọn BOM → auto fill thành phẩm, nhập SL
8. Detail: hiển thị NVL cần, tiến độ SX, nút Start/Complete
9. Khi Complete: input SL thực tế sản xuất (có thể < planned nếu hư hỏng)

**Đầu vào:** BOM, SL sản xuất, kho
**Đầu ra:** WO với flow trạng thái, xuất NVL + nhập TP tự động

---

## Bài 4: Dashboard Sản xuất
**Độ khó: Trung bình**

Xây dựng trang tổng quan sản xuất:

**Backend:**
1. API thống kê: số WO theo trạng thái, SL sản xuất trong tháng
2. API top sản phẩm SX nhiều nhất

**Frontend:**
3. 4 card thống kê: WO đang chạy, WO hoàn thành tháng này, SL sản xuất, Hiệu suất
4. Biểu đồ Bar: SL sản xuất theo tuần/tháng
5. Bảng WO đang thực hiện (IN_PROGRESS)
6. Hiệu suất = actualQuantity / plannedQuantity × 100%

**Đầu vào:** Không
**Đầu ra:** Dashboard trực quan cho sản xuất

---

## Bài 5: Tính giá thành sản phẩm
**Độ khó: Trung bình**

Xây dựng chức năng tính giá thành sản xuất:

**Backend:**
1. Giá NVL = sum(material.costPrice × bomItem.quantity) cho 1 đơn vị TP
2. API tính giá cho N sản phẩm: giá NVL × quantity
3. So sánh giá SX vs giá bán → tính lợi nhuận dự kiến

**Frontend:**
4. Trang phân tích giá: chọn BOM → hiển thị chi tiết NVL + giá
5. Input SL → tính tổng chi phí SX
6. So sánh với giá bán: hiển thị biên lợi nhuận (%)

**Đầu vào:** BOM ID, SL sản xuất
**Đầu ra:** Chi tiết giá thành, biên lợi nhuận
