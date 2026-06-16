# Module Quản Lý Kho (Inventory)

## Mục lục

1. [Tổng quan quản lý kho](#1-tổng-quan-quản-lý-kho)
2. [Quy trình kho hàng](#2-quy-trình-kho-hàng)
3. [Thiết kế Database](#3-thiết-kế-database)
4. [Backend - API quản lý kho](#4-backend---api-quản-lý-kho)
5. [Frontend - Giao diện kho](#5-frontend---giao-diện-kho)
6. [Nhập kho](#6-nhập-kho)
7. [Xuất kho](#7-xuất-kho)
8. [Chuyển kho](#8-chuyển-kho)
9. [Kiểm kê](#9-kiểm-kê)
10. [Tồn kho realtime](#10-tồn-kho-realtime)

---

## 1. Tổng quan quản lý kho

### 1.1 Inventory Management là gì?

Module Inventory quản lý toàn bộ hàng hóa trong kho: số lượng tồn, vị trí, lịch sử nhập xuất, kiểm kê. Đây là module **kết nối** giữa Mua hàng (nhập) và Bán hàng (xuất).

### 1.2 Các nghiệp vụ chính

| Nghiệp vụ | Mô tả | Ảnh hưởng tồn kho |
|---|---|---|
| **Nhập kho** | Nhận hàng từ NCC, nhập thành phẩm sản xuất | Tồn kho **tăng** (+) |
| **Xuất kho** | Giao hàng cho khách, xuất NVL sản xuất | Tồn kho **giảm** (-) |
| **Chuyển kho** | Di chuyển hàng giữa các kho | Kho A giảm, Kho B tăng |
| **Kiểm kê** | Đếm thực tế, điều chỉnh sai lệch | Điều chỉnh +/- |

### 1.3 Ví dụ thực tế

```
Kho hàng công ty SIM:

Kho Hà Nội:
  - Chip eSIM: 15.000 cái (min: 5.000)     ✅ Đủ
  - Phôi SIM: 2.000 cái (min: 3.000)       ⚠️ Sắp hết
  - Bao bì: 500 hộp (min: 1.000)           🔴 Dưới mức tối thiểu

Kho HCM:
  - Chip eSIM: 3.000 cái
  - Phôi SIM: 8.000 cái

→ Chuyển 3.000 Phôi SIM từ Kho HCM → Kho HN
→ Tạo YCMH bổ sung Bao bì
```

---

## 2. Quy trình kho hàng

### 2.1 Luồng hàng hóa

```
     Nhà cung cấp              Khách hàng
          │                          ▲
          ▼                          │
    ┌──────────┐              ┌──────────┐
    │ Nhập kho │              │ Xuất kho │
    │   (+)    │              │   (-)    │
    └────┬─────┘              └────┬─────┘
         │                         │
         ▼                         │
    ┌─────────────────────────────────┐
    │         TỒN KHO REALTIME        │
    │   Product A: 15.000             │
    │   Product B: 2.000              │
    │   Product C: 500                │
    └─────────┬───────────────────────┘
              │
         ┌────┴────┐
         │Chuyển kho│
         │ A → B    │
         └─────────┘
```

### 2.2 Phiếu kho (Stock Ticket)

Mọi thay đổi tồn kho đều phải có **phiếu kho** (chứng từ):

| Loại phiếu | Mã prefix | Nguồn |
|---|---|---|
| Phiếu nhập mua hàng | SI (Stock In) | Purchase Order |
| Phiếu nhập sản xuất | SI | Work Order |
| Phiếu xuất bán hàng | SO (Stock Out) | Sales Order |
| Phiếu xuất sản xuất | SO | Work Order |
| Phiếu chuyển kho | ST (Stock Transfer) | Nội bộ |
| Phiếu kiểm kê | SA (Stock Adjust) | Kiểm kê |

---

## 3. Thiết kế Database

### 3.1 Bảng Stock (Tồn kho)

```sql
-- Tồn kho hiện tại = snapshot realtime
CREATE TABLE stock (
    id BIGSERIAL PRIMARY KEY,
    warehouse_id BIGINT REFERENCES warehouses(id),
    product_id BIGINT REFERENCES products(id),
    quantity INT NOT NULL DEFAULT 0,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(warehouse_id, product_id)  -- Mỗi SP/Kho chỉ 1 record
);
```

### 3.2 Bảng Stock Transaction (Lịch sử nhập xuất)

```sql
CREATE TABLE stock_transactions (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) NOT NULL,                 -- SI-2024-0001
    type VARCHAR(20) NOT NULL,                 -- IN, OUT, TRANSFER, ADJUST
    warehouse_id BIGINT REFERENCES warehouses(id),
    product_id BIGINT REFERENCES products(id),
    quantity INT NOT NULL,                      -- Số lượng (+/-)
    reference_type VARCHAR(20),                -- PURCHASE, SALES, PRODUCTION, MANUAL
    reference_id BIGINT,                       -- ID của PO/SO/WO
    note TEXT,
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 3.3 Bảng Stock Transfer (Chuyển kho)

```sql
CREATE TABLE stock_transfers (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    from_warehouse_id BIGINT REFERENCES warehouses(id),
    to_warehouse_id BIGINT REFERENCES warehouses(id),
    transfer_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'DRAFT',        -- DRAFT, IN_TRANSIT, COMPLETED
    note TEXT,
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE stock_transfer_items (
    id BIGSERIAL PRIMARY KEY,
    transfer_id BIGINT REFERENCES stock_transfers(id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES products(id),
    quantity INT NOT NULL
);
```

### 3.4 Bảng Inventory Check (Kiểm kê)

```sql
CREATE TABLE inventory_checks (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    warehouse_id BIGINT REFERENCES warehouses(id),
    check_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'DRAFT',
    note TEXT,
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE inventory_check_items (
    id BIGSERIAL PRIMARY KEY,
    check_id BIGINT REFERENCES inventory_checks(id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES products(id),
    system_quantity INT,                        -- SL trong hệ thống
    actual_quantity INT,                        -- SL đếm thực tế
    difference INT,                             -- actual - system
    note TEXT
);
```

---

## 4. Backend - API quản lý kho

### 4.1 InventoryService

```java
@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {

    private final StockRepository stockRepo;
    private final StockTransactionRepository txnRepo;

    // Lấy tồn kho 1 SP tại 1 kho
    @Transactional(readOnly = true)
    public int getStock(Long warehouseId, Long productId) {
        return stockRepo.findByWarehouseIdAndProductId(warehouseId, productId)
            .map(Stock::getQuantity)
            .orElse(0);
    }

    // Lấy tổng tồn kho 1 SP tại tất cả kho
    @Transactional(readOnly = true)
    public int getTotalStock(Long productId) {
        return stockRepo.sumQuantityByProductId(productId);
    }

    // Nhập kho (tăng tồn)
    public void increaseStock(Long warehouseId, Long productId, int quantity) {
        Stock stock = stockRepo.findByWarehouseIdAndProductId(warehouseId, productId)
            .orElse(new Stock(warehouseId, productId, 0));
        stock.setQuantity(stock.getQuantity() + quantity);
        stockRepo.save(stock);

        // Ghi log transaction
        txnRepo.save(StockTransaction.builder()
            .type("IN")
            .warehouseId(warehouseId)
            .productId(productId)
            .quantity(quantity)
            .build());
    }

    // Xuất kho (giảm tồn)
    public void decreaseStock(Long warehouseId, Long productId, int quantity) {
        Stock stock = stockRepo.findByWarehouseIdAndProductId(warehouseId, productId)
            .orElseThrow(() -> new BusinessException("Không có tồn kho"));

        if (stock.getQuantity() < quantity) {
            throw new BusinessException("Tồn kho không đủ: cần " + quantity
                + ", tồn " + stock.getQuantity());
        }
        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepo.save(stock);

        txnRepo.save(StockTransaction.builder()
            .type("OUT")
            .warehouseId(warehouseId)
            .productId(productId)
            .quantity(-quantity)
            .build());
    }

    // Chuyển kho
    public void transfer(Long fromWarehouse, Long toWarehouse,
                         Long productId, int quantity) {
        decreaseStock(fromWarehouse, productId, quantity);
        increaseStock(toWarehouse, productId, quantity);

        txnRepo.save(StockTransaction.builder()
            .type("TRANSFER")
            .warehouseId(fromWarehouse)
            .productId(productId)
            .quantity(-quantity)
            .note("Chuyển đến kho " + toWarehouse)
            .build());
    }
}
```

### 4.2 API Endpoints

| Method | URL | Mô tả |
|---|---|---|
| GET | `/api/inventory/stock` | Tồn kho toàn bộ (phân trang) |
| GET | `/api/inventory/stock/{warehouseId}` | Tồn kho theo kho |
| GET | `/api/inventory/stock/product/{productId}` | Tồn kho SP theo từng kho |
| GET | `/api/inventory/transactions` | Lịch sử nhập xuất |
| POST | `/api/inventory/transfers` | Tạo phiếu chuyển kho |
| POST | `/api/inventory/checks` | Tạo phiếu kiểm kê |
| PUT | `/api/inventory/checks/{id}/confirm` | Xác nhận kiểm kê |
| GET | `/api/inventory/alerts` | Sản phẩm dưới mức tồn tối thiểu |

---

## 5. Frontend - Giao diện kho

### 5.1 Cấu trúc trang

```
src/pages/Inventory/
├── StockOverview.jsx         ← Tổng quan tồn kho (Dashboard)
├── StockByWarehouse.jsx      ← Tồn kho theo kho
├── TransactionHistory.jsx    ← Lịch sử nhập xuất
├── StockTransferForm.jsx     ← Form chuyển kho
├── InventoryCheckList.jsx    ← Danh sách kiểm kê
├── InventoryCheckForm.jsx    ← Form kiểm kê
├── StockAlerts.jsx           ← Cảnh báo tồn kho thấp
└── WarehouseManagement.jsx   ← Quản lý kho hàng
```

### 5.2 Dashboard Tồn kho

```jsx
function StockOverview() {
  const [stocks, setStocks] = useState([]);
  const [alerts, setAlerts] = useState([]);

  useEffect(() => {
    api.get('/inventory/stock').then(r => setStocks(r.data));
    api.get('/inventory/alerts').then(r => setAlerts(r.data));
  }, []);

  return (
    <div className="stock-overview">
      <h1>🏭 Tồn kho</h1>

      {/* Cảnh báo */}
      {alerts.length > 0 && (
        <div className="alert-banner">
          ⚠️ {alerts.length} sản phẩm dưới mức tồn kho tối thiểu!
          <ul>
            {alerts.map(a => (
              <li key={a.productId}>
                {a.productName}: tồn {a.quantity} / min {a.minStock}
              </li>
            ))}
          </ul>
        </div>
      )}

      {/* Bảng tồn kho */}
      <table className="data-table">
        <thead>
          <tr>
            <th>Mã SP</th><th>Tên SP</th><th>Kho</th>
            <th>Tồn kho</th><th>Min</th><th>Trạng thái</th>
          </tr>
        </thead>
        <tbody>
          {stocks.map(s => (
            <tr key={`${s.warehouseId}-${s.productId}`}
                className={s.quantity <= s.minStock ? 'row-warning' : ''}>
              <td>{s.productCode}</td>
              <td>{s.productName}</td>
              <td>{s.warehouseName}</td>
              <td>{s.quantity.toLocaleString()}</td>
              <td>{s.minStock.toLocaleString()}</td>
              <td>
                {s.quantity <= 0 && <span className="badge-danger">Hết hàng</span>}
                {s.quantity > 0 && s.quantity <= s.minStock &&
                  <span className="badge-warning">Sắp hết</span>}
                {s.quantity > s.minStock &&
                  <span className="badge-success">Đủ hàng</span>}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
```

---

## 6. Nhập kho

### 6.1 Các nguồn nhập kho

1. **Mua hàng:** Nhận hàng từ PO → tự động nhập kho (module Procurement gọi)
2. **Sản xuất:** Thành phẩm từ Work Order → nhập kho
3. **Kiểm kê:** Điều chỉnh tăng khi đếm thực tế > hệ thống
4. **Khác:** Nhập thủ công (hàng tặng, trả lại...)

---

## 7. Xuất kho

### 7.1 Các nguồn xuất kho

1. **Bán hàng:** Giao hàng cho khách → xuất kho (module Sales gọi)
2. **Sản xuất:** Xuất NVL cho sản xuất → xuất kho
3. **Kiểm kê:** Điều chỉnh giảm khi hệ thống > thực tế
4. **Khác:** Xuất hủy, xuất biếu tặng...

### 7.2 Kiểm tra trước khi xuất

```java
public void validateStockOut(Long warehouseId, Long productId, int quantity) {
    int currentStock = getStock(warehouseId, productId);
    if (currentStock < quantity) {
        throw new InsufficientStockException(
            "SP " + productId + ": yêu cầu " + quantity + ", tồn " + currentStock
        );
    }
}
```

---

## 8. Chuyển kho

### 8.1 Quy trình

```
Tạo phiếu chuyển (DRAFT)
    → Xác nhận (IN_TRANSIT): Kho nguồn giảm tồn
    → Hoàn thành (COMPLETED): Kho đích tăng tồn
```

### 8.2 Implementation

```java
public StockTransferDTO confirmTransfer(Long transferId) {
    StockTransferEntity transfer = transferRepo.findById(transferId).orElseThrow();

    for (StockTransferItemEntity item : transfer.getItems()) {
        // Giảm kho nguồn
        decreaseStock(transfer.getFromWarehouseId(),
                     item.getProductId(), item.getQuantity());
        // Tăng kho đích
        increaseStock(transfer.getToWarehouseId(),
                     item.getProductId(), item.getQuantity());
    }

    transfer.setStatus("COMPLETED");
    return mapToDTO(transferRepo.save(transfer));
}
```

---

## 9. Kiểm kê

### 9.1 Quy trình kiểm kê

1. Tạo phiếu kiểm kê cho 1 kho → hệ thống load SL hiện tại
2. Nhân viên đếm thực tế → nhập SL thực
3. Hệ thống tính chênh lệch: `difference = actual - system`
4. Xác nhận → Tự động điều chỉnh tồn kho

### 9.2 Điều chỉnh tồn kho

```java
public void confirmCheck(Long checkId) {
    InventoryCheckEntity check = checkRepo.findById(checkId).orElseThrow();

    for (InventoryCheckItemEntity item : check.getItems()) {
        int diff = item.getActualQuantity() - item.getSystemQuantity();
        if (diff > 0) {
            increaseStock(check.getWarehouseId(), item.getProductId(), diff);
        } else if (diff < 0) {
            decreaseStock(check.getWarehouseId(), item.getProductId(), Math.abs(diff));
        }
    }
    check.setStatus("COMPLETED");
    checkRepo.save(check);
}
```

---

## 10. Tồn kho realtime

### 10.1 Query tồn kho

```sql
-- Tồn kho theo kho
SELECT p.code, p.name, w.name AS warehouse,
       s.quantity, p.min_stock,
       CASE
         WHEN s.quantity <= 0 THEN 'HẾT HÀNG'
         WHEN s.quantity <= p.min_stock THEN 'SẮP HẾT'
         ELSE 'ĐỦ'
       END AS status
FROM stock s
JOIN products p ON s.product_id = p.id
JOIN warehouses w ON s.warehouse_id = w.id
ORDER BY s.quantity ASC;

-- Cảnh báo SP dưới mức tối thiểu
SELECT p.code, p.name, s.quantity, p.min_stock
FROM stock s
JOIN products p ON s.product_id = p.id
WHERE s.quantity <= p.min_stock AND p.active = true;
```

### 10.2 Thẻ kho (Stock Card)

Thẻ kho là lịch sử nhập xuất của 1 sản phẩm tại 1 kho:

```sql
SELECT
    t.created_at,
    t.type,
    t.reference_type,
    CASE WHEN t.quantity > 0 THEN t.quantity ELSE 0 END AS qty_in,
    CASE WHEN t.quantity < 0 THEN ABS(t.quantity) ELSE 0 END AS qty_out,
    SUM(t.quantity) OVER (ORDER BY t.created_at) AS running_balance,
    t.note
FROM stock_transactions t
WHERE t.warehouse_id = :warehouseId AND t.product_id = :productId
ORDER BY t.created_at;
```

---

> **Tiếp theo:** Module **Quản lý Sản xuất (Manufacturing)** - BOM, kế hoạch sản xuất, lệnh sản xuất.
