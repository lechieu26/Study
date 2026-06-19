# Module Quản Lý Mua Hàng (Procurement)

## Mục lục

1. [Tổng quan nghiệp vụ mua hàng](#1-tổng-quan-nghiệp-vụ-mua-hàng)
2. [Quy trình mua hàng](#2-quy-trình-mua-hàng)
3. [Thiết kế Database](#3-thiết-kế-database)
4. [Backend - API mua hàng](#4-backend---api-mua-hàng)
5. [Frontend - Giao diện mua hàng](#5-frontend---giao-diện-mua-hàng)
6. [Yêu cầu mua hàng (Purchase Request)](#6-yêu-cầu-mua-hàng-purchase-request)
7. [Đơn đặt hàng (Purchase Order)](#7-đơn-đặt-hàng-purchase-order)
8. [Nhập hàng từ nhà cung cấp](#8-nhập-hàng-từ-nhà-cung-cấp)
9. [Công nợ phải trả](#9-công-nợ-phải-trả)

---

> **📥 Tải file SQL:** [procurement-database.sql](/api/download/erp-procurement/procurement-database.sql) — Cấu trúc bảng và dữ liệu mẫu cho module Mua hàng (suppliers, purchase_requests, purchase_orders, goods_receipts). Yêu cầu chạy file `erp-database.sql` từ module Tổng Quan trước.

---

## 1. Tổng quan nghiệp vụ mua hàng

### 1.1 Procurement là gì?

Module Procurement quản lý quy trình mua hàng hóa, nguyên vật liệu từ nhà cung cấp. Đây là module quan trọng để đảm bảo doanh nghiệp luôn có đủ hàng để bán hoặc sản xuất.

### 1.2 Các thành phần chính

| Thành phần | Mô tả | Ví dụ |
|---|---|---|
| **Supplier** (NCC) | Quản lý nhà cung cấp | Công ty SIM Tech, SĐT, MST |
| **Purchase Request** (YCMH) | Yêu cầu mua hàng từ phòng ban | Phòng kho yêu cầu mua 5.000 chip |
| **Purchase Order** (PO) | Đơn đặt hàng gửi NCC | PO-2024-001 đặt mua chip eSIM |
| **Goods Receipt** (Nhập hàng) | Nhận hàng và nhập kho | Nhận 5.000 chip, kiểm tra chất lượng |
| **AP Invoice** (Công nợ) | Hóa đơn phải trả NCC | Nợ SIM Tech 250.000.000₫ |

### 1.3 Ví dụ thực tế

```
Tình huống: Kho sắp hết chip eSIM (tồn kho < mức tối thiểu)

Bước 1 - Yêu cầu mua hàng:
  → Phòng kho tạo yêu cầu mua: 5.000 chip eSIM
  → Trưởng phòng duyệt yêu cầu

Bước 2 - Đơn đặt hàng:
  → Phòng mua hàng tạo PO cho NCC SIM Tech
  → PO-2024-001: 5.000 chip × 50.000₫ = 250.000.000₫
  → Gửi PO cho NCC

Bước 3 - Nhận hàng:
  → NCC giao 5.000 chip
  → Kiểm tra số lượng, chất lượng
  → Nhập kho → Tồn kho tăng

Bước 4 - Thanh toán:
  → NCC gửi hóa đơn
  → Ghi nhận công nợ phải trả: 250.000.000₫
  → Thanh toán theo kỳ hạn (30-60 ngày)
```

---

## 2. Quy trình mua hàng

### 2.1 Flowchart

```
┌──────────────┐    Duyệt    ┌──────────────┐    NCC xác nhận    ┌──────────┐
│ Yêu cầu mua │ ──────────► │Đơn đặt hàng  │ ────────────────► │ Nhập hàng│
│(Purch.Request)│             │(Purchase Order)│                   │(Goods    │
└──────────────┘             └──────────────┘                   │ Receipt) │
     │                             │                             └──────────┘
     │ Từ chối                     │ Hủy                              │
     ▼                             ▼                                  ▼
   [Đóng]                       [Hủy]                          ┌──────────┐
                                                                │ Công nợ  │
                                                                │phải trả  │
                                                                └──────────┘
```

### 2.2 So sánh Mua hàng vs Bán hàng

| | Mua hàng (Procurement) | Bán hàng (Sales) |
|---|---|---|
| Đối tác | Nhà cung cấp | Khách hàng |
| Luồng hàng | Hàng VÀO kho | Hàng RA kho |
| Luồng tiền | Tiền RA (chi) | Tiền VÀO (thu) |
| Công nợ | Phải trả NCC | Phải thu KH |
| Chứng từ | Purchase Order | Sales Order |

### 2.3 Trạng thái Purchase Order

| Trạng thái | Mã | Mô tả |
|---|---|---|
| Nháp | DRAFT | PO mới tạo |
| Đã gửi NCC | SENT | Đã gửi cho nhà cung cấp |
| NCC xác nhận | CONFIRMED | NCC đồng ý giao hàng |
| Đã nhận hàng | RECEIVED | Hàng đã nhập kho |
| Hoàn thành | COMPLETED | Đã thanh toán xong |
| Đã hủy | CANCELLED | PO bị hủy |

---

## 3. Thiết kế Database

### 3.1 Bảng Purchase Request

```sql
CREATE TABLE purchase_requests (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,         -- PR-2024-0001
    requested_by BIGINT REFERENCES users(id), -- Người yêu cầu
    department VARCHAR(50),                    -- Phòng ban
    request_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',      -- PENDING, APPROVED, REJECTED
    reason TEXT,                                -- Lý do mua
    approved_by BIGINT REFERENCES users(id),
    approved_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE purchase_request_items (
    id BIGSERIAL PRIMARY KEY,
    request_id BIGINT REFERENCES purchase_requests(id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES products(id),
    quantity INT NOT NULL,
    note TEXT
);
```

### 3.2 Bảng Purchase Order

```sql
CREATE TABLE purchase_orders (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,          -- PO-2024-0001
    supplier_id BIGINT REFERENCES suppliers(id),
    request_id BIGINT REFERENCES purchase_requests(id),
    order_date DATE NOT NULL,
    expected_date DATE,                         -- Ngày dự kiến nhận hàng
    status VARCHAR(20) DEFAULT 'DRAFT',
    total_amount DECIMAL(15,2) DEFAULT 0,
    paid_amount DECIMAL(15,2) DEFAULT 0,
    note TEXT,
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE purchase_order_items (
    id BIGSERIAL PRIMARY KEY,
    purchase_order_id BIGINT REFERENCES purchase_orders(id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES products(id),
    quantity INT NOT NULL,
    unit_price DECIMAL(15,2) NOT NULL,
    received_quantity INT DEFAULT 0,
    amount DECIMAL(15,2) NOT NULL
);
```

### 3.3 Bảng Goods Receipt (Nhập hàng)

```sql
CREATE TABLE goods_receipts (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,          -- GR-2024-0001
    purchase_order_id BIGINT REFERENCES purchase_orders(id),
    warehouse_id BIGINT REFERENCES warehouses(id),
    receipt_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'DRAFT',        -- DRAFT, COMPLETED
    note TEXT,
    received_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE goods_receipt_items (
    id BIGSERIAL PRIMARY KEY,
    receipt_id BIGINT REFERENCES goods_receipts(id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES products(id),
    ordered_quantity INT,                       -- SL đặt
    received_quantity INT NOT NULL,             -- SL thực nhận
    note TEXT                                   -- Ghi chú (hư hỏng...)
);
```

---

## 4. Backend - API mua hàng

### 4.1 PurchaseOrderService

```java
@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseOrderService {

    private final PurchaseOrderRepository poRepo;
    private final SupplierRepository supplierRepo;
    private final ProductRepository productRepo;

    public PurchaseOrderDTO create(CreatePORequest request) {
        SupplierEntity supplier = supplierRepo.findById(request.getSupplierId())
            .orElseThrow(() -> new ResourceNotFoundException("NCC không tồn tại"));

        PurchaseOrderEntity po = PurchaseOrderEntity.builder()
            .code(generateCode())
            .supplier(supplier)
            .orderDate(LocalDate.now())
            .expectedDate(request.getExpectedDate())
            .status("DRAFT")
            .build();

        BigDecimal total = BigDecimal.ZERO;
        for (POItemRequest item : request.getItems()) {
            ProductEntity product = productRepo.findById(item.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("SP không tồn tại"));

            BigDecimal amount = item.getUnitPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(amount);

            po.getItems().add(PurchaseOrderItemEntity.builder()
                .purchaseOrder(po)
                .product(product)
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .amount(amount)
                .build());
        }
        po.setTotalAmount(total);

        return mapToDTO(poRepo.save(po));
    }
}
```

### 4.2 GoodsReceiptService (Nhập hàng)

```java
@Service
@RequiredArgsConstructor
@Transactional
public class GoodsReceiptService {

    private final GoodsReceiptRepository grRepo;
    private final PurchaseOrderRepository poRepo;
    private final InventoryService inventoryService;

    public GoodsReceiptDTO createFromPO(Long poId, CreateGRRequest request) {
        PurchaseOrderEntity po = poRepo.findById(poId)
            .orElseThrow(() -> new ResourceNotFoundException("PO không tồn tại"));

        GoodsReceiptEntity gr = GoodsReceiptEntity.builder()
            .code(generateGRCode())
            .purchaseOrder(po)
            .warehouseId(request.getWarehouseId())
            .receiptDate(LocalDate.now())
            .status("DRAFT")
            .build();

        for (GRItemRequest item : request.getItems()) {
            gr.getItems().add(GoodsReceiptItemEntity.builder()
                .receipt(gr)
                .product(productRepo.findById(item.getProductId()).orElseThrow())
                .orderedQuantity(item.getOrderedQuantity())
                .receivedQuantity(item.getReceivedQuantity())
                .build());
        }

        grRepo.save(gr);

        // Nhập kho - tăng tồn kho
        for (GoodsReceiptItemEntity item : gr.getItems()) {
            inventoryService.increaseStock(
                request.getWarehouseId(),
                item.getProduct().getId(),
                item.getReceivedQuantity()
            );
        }

        // Cập nhật PO
        po.setStatus("RECEIVED");
        poRepo.save(po);

        gr.setStatus("COMPLETED");
        return mapToDTO(grRepo.save(gr));
    }
}
```

### 4.3 API Endpoints

| Method | URL | Mô tả |
|---|---|---|
| GET | `/api/procurement/suppliers` | Danh sách NCC |
| POST | `/api/procurement/suppliers` | Tạo NCC mới |
| GET | `/api/procurement/requests` | Danh sách YCMH |
| POST | `/api/procurement/requests` | Tạo YCMH |
| PUT | `/api/procurement/requests/{id}/approve` | Duyệt YCMH |
| GET | `/api/procurement/orders` | Danh sách PO |
| POST | `/api/procurement/orders` | Tạo PO |
| POST | `/api/procurement/orders/{id}/receive` | Nhập hàng từ PO |
| GET | `/api/procurement/debts` | Công nợ phải trả |

---

## 5. Frontend - Giao diện mua hàng

### 5.1 Cấu trúc trang

```
src/pages/Procurement/
├── SupplierList.jsx          ← CRUD nhà cung cấp
├── PurchaseRequestList.jsx   ← Danh sách yêu cầu mua
├── PurchaseRequestForm.jsx   ← Form tạo YCMH
├── PurchaseOrderList.jsx     ← Danh sách PO
├── PurchaseOrderForm.jsx     ← Form tạo PO
├── PurchaseOrderDetail.jsx   ← Chi tiết PO + nhập hàng
├── GoodsReceiptForm.jsx      ← Form nhập hàng
└── APDebtReport.jsx          ← Báo cáo công nợ phải trả
```

### 5.2 Component Nhập hàng

```jsx
function GoodsReceiptForm({ purchaseOrder, onComplete }) {
  const [items, setItems] = useState(
    purchaseOrder.items.map(item => ({
      productId: item.product.id,
      productName: item.product.name,
      orderedQty: item.quantity,
      receivedQty: item.quantity,  // Mặc định nhận đủ
      note: ''
    }))
  );
  const [warehouseId, setWarehouseId] = useState('');

  const handleSubmit = async () => {
    await api.post(`/procurement/orders/${purchaseOrder.id}/receive`, {
      warehouseId,
      items: items.map(i => ({
        productId: i.productId,
        orderedQuantity: i.orderedQty,
        receivedQuantity: i.receivedQty,
        note: i.note
      }))
    });
    onComplete();
  };

  return (
    <div className="goods-receipt-form">
      <h2>📦 Nhập hàng từ PO: {purchaseOrder.code}</h2>
      <div className="form-row">
        <label>Nhập vào kho:</label>
        <select value={warehouseId} onChange={e => setWarehouseId(e.target.value)} required>
          <option value="">-- Chọn kho --</option>
          {/* warehouses options */}
        </select>
      </div>
      <table>
        <thead>
          <tr>
            <th>Sản phẩm</th><th>SL đặt</th>
            <th>SL thực nhận</th><th>Ghi chú</th>
          </tr>
        </thead>
        <tbody>
          {items.map((item, idx) => (
            <tr key={idx}>
              <td>{item.productName}</td>
              <td>{item.orderedQty}</td>
              <td>
                <input type="number" min="0" max={item.orderedQty}
                  value={item.receivedQty}
                  onChange={e => {
                    const newItems = [...items];
                    newItems[idx].receivedQty = Number(e.target.value);
                    setItems(newItems);
                  }} />
              </td>
              <td>
                <input type="text" value={item.note}
                  placeholder="VD: 10 cái hư"
                  onChange={e => {
                    const newItems = [...items];
                    newItems[idx].note = e.target.value;
                    setItems(newItems);
                  }} />
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <button className="btn-primary" onClick={handleSubmit}>
        ✅ Xác nhận nhập kho
      </button>
    </div>
  );
}
```

---

## 6. Yêu cầu mua hàng (Purchase Request)

- Nhân viên/phòng ban tạo yêu cầu mua hàng khi cần bổ sung hàng
- Trưởng phòng/Quản lý duyệt hoặc từ chối
- Sau khi duyệt → có thể tạo PO

### 6.1 Flow duyệt

```
Nhân viên tạo YCMH → PENDING
         ↓
Trưởng phòng xem xét
    ├── Duyệt → APPROVED → Có thể tạo PO
    └── Từ chối → REJECTED (kèm lý do)
```

---

## 7. Đơn đặt hàng (Purchase Order)

### 7.1 Tạo PO từ YCMH

```java
public PurchaseOrderDTO createFromRequest(Long requestId, Long supplierId) {
    PurchaseRequestEntity pr = requestRepo.findById(requestId)
        .orElseThrow(() -> new ResourceNotFoundException("YCMH không tồn tại"));

    if (!"APPROVED".equals(pr.getStatus())) {
        throw new BusinessException("YCMH chưa được duyệt");
    }

    SupplierEntity supplier = supplierRepo.findById(supplierId)
        .orElseThrow(() -> new ResourceNotFoundException("NCC không tồn tại"));

    PurchaseOrderEntity po = PurchaseOrderEntity.builder()
        .code(generateCode())
        .supplier(supplier)
        .request(pr)
        .orderDate(LocalDate.now())
        .build();

    // Copy items từ YCMH sang PO, bổ sung đơn giá
    for (PurchaseRequestItemEntity pri : pr.getItems()) {
        po.getItems().add(PurchaseOrderItemEntity.builder()
            .purchaseOrder(po)
            .product(pri.getProduct())
            .quantity(pri.getQuantity())
            .unitPrice(pri.getProduct().getCostPrice())
            .amount(pri.getProduct().getCostPrice()
                .multiply(BigDecimal.valueOf(pri.getQuantity())))
            .build());
    }

    po.setTotalAmount(po.getItems().stream()
        .map(PurchaseOrderItemEntity::getAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add));

    return mapToDTO(poRepo.save(po));
}
```

---

## 8. Nhập hàng từ nhà cung cấp

### 8.1 Quy trình nhập hàng

1. NCC giao hàng đến kho
2. Nhân viên kho kiểm tra: đối chiếu SL thực nhận vs SL đặt
3. Ghi nhận sản phẩm hư hỏng (nếu có)
4. Xác nhận nhập kho → Tồn kho tăng
5. Cập nhật PO: `received_quantity` trên từng item

### 8.2 Xử lý nhận thiếu/thừa

```java
// Kiểm tra SL nhận
for (GRItemRequest item : request.getItems()) {
    POItemEntity poItem = findPOItem(po, item.getProductId());

    if (item.getReceivedQuantity() > poItem.getQuantity()) {
        throw new BusinessException("SL nhận không được vượt quá SL đặt");
    }

    if (item.getReceivedQuantity() < poItem.getQuantity()) {
        // Nhận thiếu → PO chuyển PARTIALLY_RECEIVED
        po.setStatus("PARTIALLY_RECEIVED");
    }
}
```

---

## 9. Công nợ phải trả

### 9.1 Cách tính

```
Công nợ phải trả = Tổng PO đã nhận hàng - Tổng đã thanh toán cho NCC
```

### 9.2 Query báo cáo

```sql
SELECT
    s.code AS supplier_code,
    s.name AS supplier_name,
    SUM(po.total_amount) AS total_po,
    SUM(po.paid_amount) AS total_paid,
    SUM(po.total_amount - po.paid_amount) AS debt_amount
FROM purchase_orders po
JOIN suppliers s ON po.supplier_id = s.id
WHERE po.status IN ('RECEIVED', 'COMPLETED')
GROUP BY s.id, s.code, s.name
HAVING SUM(po.total_amount - po.paid_amount) > 0
ORDER BY debt_amount DESC;
```

### 9.3 Lịch thanh toán

| Kỳ hạn | Mô tả |
|---|---|
| COD | Thanh toán khi giao hàng |
| Net 30 | Thanh toán trong 30 ngày |
| Net 60 | Thanh toán trong 60 ngày |
| 50/50 | 50% trước, 50% sau khi nhận hàng |

---

> **Tiếp theo:** Module **Quản lý Kho (Inventory)** - nhập kho, xuất kho, tồn kho realtime.
