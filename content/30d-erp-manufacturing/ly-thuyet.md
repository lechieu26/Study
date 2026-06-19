# Module Quản Lý Sản Xuất (Manufacturing)

## Mục lục

1. [Tổng quan sản xuất](#1-tổng-quan-sản-xuất)
2. [BOM - Bill of Materials](#2-bom---bill-of-materials)
3. [Kế hoạch sản xuất](#3-kế-hoạch-sản-xuất)
4. [Lệnh sản xuất (Work Order)](#4-lệnh-sản-xuất-work-order)
5. [Thiết kế Database](#5-thiết-kế-database)
6. [Backend - API sản xuất](#6-backend---api-sản-xuất)
7. [Frontend - Giao diện sản xuất](#7-frontend---giao-diện-sản-xuất)

---

> **📥 Tải file SQL:** [manufacturing-database.sql](/api/download/erp-manufacturing/manufacturing-database.sql) — Cấu trúc bảng và dữ liệu mẫu cho module Sản xuất (boms, bom_items, work_orders). Yêu cầu chạy file `erp-database.sql` từ module Tổng Quan trước.

---

## 1. Tổng quan sản xuất

### 1.1 Manufacturing trong ERP

Module Manufacturing quản lý quy trình biến nguyên vật liệu (NVL) thành thành phẩm. Module này kết nối chặt chẽ với Kho (xuất NVL, nhập thành phẩm) và Mua hàng (mua NVL khi thiếu).

### 1.2 Ví dụ thực tế

```
Sản xuất 100.000 thẻ SIM 4G

BOM (Công thức sản xuất 1 SIM):
  - 1 × Chip eSIM         (giá: 30.000₫)
  - 1 × Phôi nhựa SIM     (giá: 5.000₫)
  - 1 × Bao bì đóng gói   (giá: 2.000₫)
  → Giá vốn SX 1 SIM = 37.000₫

Sản xuất 100.000 SIM cần:
  - 100.000 × Chip eSIM
  - 100.000 × Phôi nhựa
  - 100.000 × Bao bì

Kiểm tra tồn kho:
  - Chip: tồn 80.000 → thiếu 20.000 → Tạo YCMH
  - Phôi: tồn 150.000 → đủ ✅
  - Bao bì: tồn 120.000 → đủ ✅
```

### 1.3 Luồng sản xuất

```
BOM (công thức)
    │
    ▼
Kế hoạch SX (bao nhiêu, khi nào)
    │
    ▼
Kiểm tra NVL (đủ không?)
    ├── Đủ → Tạo Lệnh SX
    └── Thiếu → Tạo YCMH bổ sung
         │
         ▼
Lệnh sản xuất (Work Order)
    │
    ├── Xuất NVL từ kho
    │
    ├── Sản xuất (thực hiện)
    │
    └── Nhập thành phẩm vào kho
```

---

## 2. BOM - Bill of Materials

### 2.1 BOM là gì?

BOM (Bill of Materials - Bảng kê vật tư) là **công thức** liệt kê tất cả NVL cần thiết để sản xuất 1 đơn vị thành phẩm.

### 2.2 Cấu trúc BOM

```
Thành phẩm: SIM 4G (SP-001)
├── Chip eSIM (NVL-001)      × 1 cái
├── Phôi nhựa (NVL-002)     × 1 cái
├── Bao bì (NVL-003)        × 1 cái
└── Nhãn in (NVL-004)       × 1 tờ

→ Sản xuất 1.000 SIM cần:
   1.000 Chip + 1.000 Phôi + 1.000 Bao bì + 1.000 Nhãn
```

### 2.3 Tính giá vốn sản xuất

```java
public BigDecimal calculateProductionCost(Long bomId, int quantity) {
    BomEntity bom = bomRepo.findById(bomId).orElseThrow();
    BigDecimal unitCost = BigDecimal.ZERO;

    for (BomItemEntity item : bom.getItems()) {
        BigDecimal materialCost = item.getMaterial().getCostPrice()
            .multiply(BigDecimal.valueOf(item.getQuantity()));
        unitCost = unitCost.add(materialCost);
    }

    return unitCost.multiply(BigDecimal.valueOf(quantity));
}
```

---

## 3. Kế hoạch sản xuất

### 3.1 Lập kế hoạch

```
Input:
  - Nhu cầu: 100.000 SIM trong tháng 6
  - BOM: 1 SIM = 1 chip + 1 phôi + 1 bao bì

Tính toán:
  - NVL cần: 100.000 chip, 100.000 phôi, 100.000 bao bì
  - Tồn kho: chip 80.000, phôi 150.000, bao bì 120.000
  - Thiếu: chip 20.000

Kế hoạch:
  1. Tuần 1: Mua bổ sung 20.000 chip
  2. Tuần 2-3: SX 50.000 SIM/tuần
  3. Tuần 4: SX 50.000 SIM còn lại
```

### 3.2 Kiểm tra NVL khả dụng

```java
public MaterialAvailabilityDTO checkMaterials(Long bomId, int quantity) {
    BomEntity bom = bomRepo.findById(bomId).orElseThrow();
    List<MaterialStatus> statuses = new ArrayList<>();
    boolean allAvailable = true;

    for (BomItemEntity item : bom.getItems()) {
        int required = item.getQuantity() * quantity;
        int available = inventoryService.getTotalStock(item.getMaterial().getId());
        int shortage = Math.max(0, required - available);

        if (shortage > 0) allAvailable = false;

        statuses.add(new MaterialStatus(
            item.getMaterial().getName(),
            required, available, shortage
        ));
    }

    return new MaterialAvailabilityDTO(allAvailable, statuses);
}
```

---

## 4. Lệnh sản xuất (Work Order)

### 4.1 Trạng thái Work Order

```
DRAFT → CONFIRMED → IN_PROGRESS → COMPLETED
  │                                    │
  └──→ CANCELLED                  (nhập thành phẩm)
```

| Trạng thái | Hành động |
|---|---|
| DRAFT | Lệnh mới tạo |
| CONFIRMED | Đã kiểm tra NVL đủ, sẵn sàng SX |
| IN_PROGRESS | Đang SX, NVL đã xuất kho |
| COMPLETED | SX xong, thành phẩm đã nhập kho |

### 4.2 Xuất NVL & Nhập thành phẩm

```java
// Khi bắt đầu SX: xuất NVL từ kho
public void startProduction(Long workOrderId) {
    WorkOrderEntity wo = woRepo.findById(workOrderId).orElseThrow();
    BomEntity bom = wo.getBom();

    for (BomItemEntity material : bom.getItems()) {
        int totalQty = material.getQuantity() * wo.getQuantity();
        inventoryService.decreaseStock(
            wo.getWarehouseId(),
            material.getMaterial().getId(),
            totalQty
        );
    }
    wo.setStatus("IN_PROGRESS");
    woRepo.save(wo);
}

// Khi SX xong: nhập thành phẩm vào kho
public void completeProduction(Long workOrderId, int actualQuantity) {
    WorkOrderEntity wo = woRepo.findById(workOrderId).orElseThrow();

    inventoryService.increaseStock(
        wo.getWarehouseId(),
        wo.getProduct().getId(),
        actualQuantity
    );

    wo.setActualQuantity(actualQuantity);
    wo.setStatus("COMPLETED");
    wo.setCompletedDate(LocalDate.now());
    woRepo.save(wo);
}
```

---

## 5. Thiết kế Database

```sql
-- BOM (Công thức sản xuất)
CREATE TABLE boms (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT REFERENCES products(id),  -- Thành phẩm
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100),
    version INT DEFAULT 1,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE bom_items (
    id BIGSERIAL PRIMARY KEY,
    bom_id BIGINT REFERENCES boms(id) ON DELETE CASCADE,
    material_id BIGINT REFERENCES products(id),  -- Nguyên vật liệu
    quantity INT NOT NULL,                        -- SL NVL cho 1 thành phẩm
    unit VARCHAR(20)
);

-- Work Order (Lệnh sản xuất)
CREATE TABLE work_orders (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    bom_id BIGINT REFERENCES boms(id),
    product_id BIGINT REFERENCES products(id),
    warehouse_id BIGINT REFERENCES warehouses(id),
    planned_quantity INT NOT NULL,
    actual_quantity INT DEFAULT 0,
    planned_start DATE,
    planned_end DATE,
    actual_start DATE,
    completed_date DATE,
    status VARCHAR(20) DEFAULT 'DRAFT',
    note TEXT,
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

## 6. Backend - API sản xuất

### 6.1 API Endpoints

| Method | URL | Mô tả |
|---|---|---|
| GET | `/api/manufacturing/boms` | Danh sách BOM |
| POST | `/api/manufacturing/boms` | Tạo BOM mới |
| GET | `/api/manufacturing/boms/{id}` | Chi tiết BOM |
| POST | `/api/manufacturing/boms/{id}/check-materials` | Kiểm tra NVL |
| GET | `/api/manufacturing/work-orders` | Danh sách lệnh SX |
| POST | `/api/manufacturing/work-orders` | Tạo lệnh SX |
| PUT | `/api/manufacturing/work-orders/{id}/start` | Bắt đầu SX |
| PUT | `/api/manufacturing/work-orders/{id}/complete` | Hoàn thành SX |

---

## 7. Frontend - Giao diện sản xuất

### 7.1 Cấu trúc trang

```
src/pages/Manufacturing/
├── BomList.jsx              ← Danh sách BOM
├── BomForm.jsx              ← Tạo/sửa BOM (SP + NVL)
├── BomDetail.jsx            ← Chi tiết BOM + kiểm tra NVL
├── WorkOrderList.jsx        ← Danh sách lệnh SX
├── WorkOrderForm.jsx        ← Tạo lệnh SX (chọn BOM + SL)
├── WorkOrderDetail.jsx      ← Chi tiết + nút Start/Complete
└── ProductionDashboard.jsx  ← Tổng quan SX
```

### 7.2 BOM Form - Dynamic rows

```jsx
function BomForm({ onSave }) {
  const [bom, setBom] = useState({
    productId: '',
    name: '',
    items: [{ materialId: '', quantity: 1 }]
  });

  const addMaterial = () => {
    setBom({
      ...bom,
      items: [...bom.items, { materialId: '', quantity: 1 }]
    });
  };

  const unitCost = bom.items.reduce((sum, item) => {
    const material = products.find(p => p.id === Number(item.materialId));
    return sum + (material ? material.costPrice * item.quantity : 0);
  }, 0);

  return (
    <form onSubmit={handleSubmit}>
      <h2>Tạo công thức sản xuất (BOM)</h2>
      <label>Thành phẩm:</label>
      <select value={bom.productId} onChange={...}>...</select>

      <h3>Nguyên vật liệu:</h3>
      <table>
        <thead>
          <tr><th>NVL</th><th>SL/1 TP</th><th>Giá NVL</th><th>Thành tiền</th></tr>
        </thead>
        <tbody>
          {bom.items.map((item, idx) => (
            <tr key={idx}>
              <td><select value={item.materialId} onChange={...}>...</select></td>
              <td><input type="number" min="1" value={item.quantity} onChange={...} /></td>
              <td>{getPrice(item.materialId).toLocaleString()}₫</td>
              <td>{(getPrice(item.materialId) * item.quantity).toLocaleString()}₫</td>
            </tr>
          ))}
        </tbody>
      </table>
      <button type="button" onClick={addMaterial}>+ Thêm NVL</button>
      <p><strong>Giá vốn SX / 1 SP: {unitCost.toLocaleString()}₫</strong></p>
      <button type="submit">💾 Lưu BOM</button>
    </form>
  );
}
```

### 7.3 Material Check Component

```jsx
function MaterialCheck({ bomId, quantity }) {
  const [result, setResult] = useState(null);

  const check = async () => {
    const res = await api.post(`/manufacturing/boms/${bomId}/check-materials`,
      { quantity });
    setResult(res.data);
  };

  return (
    <div>
      <button onClick={check}>🔍 Kiểm tra NVL cho {quantity} SP</button>
      {result && (
        <div>
          <p>{result.allAvailable ? '✅ Đủ NVL' : '⚠️ Thiếu NVL'}</p>
          <table>
            <thead>
              <tr><th>NVL</th><th>Cần</th><th>Tồn</th><th>Thiếu</th></tr>
            </thead>
            <tbody>
              {result.statuses.map(s => (
                <tr key={s.name} className={s.shortage > 0 ? 'row-danger' : ''}>
                  <td>{s.name}</td>
                  <td>{s.required.toLocaleString()}</td>
                  <td>{s.available.toLocaleString()}</td>
                  <td>{s.shortage > 0 ? s.shortage.toLocaleString() : '-'}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}
```

---

> **Tiếp theo:** Module **Kế toán - Tài chính (Accounting)** - Sổ cái, thu chi, báo cáo tài chính.
