# Module Quản Lý Bán Hàng (Sales)

## Mục lục

1. [Tổng quan nghiệp vụ bán hàng](#1-tổng-quan-nghiệp-vụ-bán-hàng)
2. [Quy trình bán hàng](#2-quy-trình-bán-hàng)
3. [Thiết kế Database](#3-thiết-kế-database)
4. [Backend - Spring Boot REST API](#4-backend---spring-boot-rest-api)
5. [Frontend - React Components](#5-frontend---react-components)
6. [Báo giá (Quotation)](#6-báo-giá-quotation)
7. [Đơn hàng (Sales Order)](#7-đơn-hàng-sales-order)
8. [Hóa đơn (Invoice)](#8-hóa-đơn-invoice)
9. [Công nợ khách hàng](#9-công-nợ-khách-hàng)

---

## 1. Tổng quan nghiệp vụ bán hàng

### 1.1 Sales là gì trong ERP?

Module Sales quản lý toàn bộ quy trình bán hàng từ khi nhận yêu cầu khách hàng đến khi thu tiền hoàn tất. Đây là module **cốt lõi** vì nó tạo ra doanh thu cho doanh nghiệp.

### 1.2 Các thành phần chính

| Thành phần | Mô tả | Ví dụ |
|---|---|---|
| **Quotation** (Báo giá) | Gửi giá cho khách hàng xem xét | Báo giá 10.000 SIM 4G với đơn giá 50.000₫/SIM |
| **Sales Order** (Đơn hàng) | Khách đồng ý → tạo đơn hàng chính thức | Đơn hàng #SO-2024-001 |
| **Invoice** (Hóa đơn) | Xuất hóa đơn để thu tiền | Hóa đơn VAT 500.000.000₫ |
| **Payment** (Thanh toán) | Theo dõi thanh toán | Đã thanh toán 300tr, còn nợ 200tr |
| **Customer** (Khách hàng) | Quản lý thông tin khách | Công ty ABC, SĐT, Địa chỉ... |

### 1.3 Ví dụ thực tế

```
Tình huống: Công ty MobiFone muốn mua 10.000 SIM 4G

Bước 1 - Báo giá:
  → Nhân viên sales tạo báo giá: 10.000 SIM × 50.000₫ = 500.000.000₫
  → Gửi cho MobiFone xem xét

Bước 2 - Đơn hàng:
  → MobiFone đồng ý → Chuyển báo giá thành Đơn hàng
  → Đơn hàng #SO-2024-001 được tạo

Bước 3 - Giao hàng:
  → Kho xuất 10.000 SIM → Giao cho MobiFone
  → Trạng thái đơn: "Đã giao hàng"

Bước 4 - Hóa đơn:
  → Xuất hóa đơn VAT cho MobiFone
  → Số tiền: 500.000.000₫ (chưa VAT) + 10% VAT = 550.000.000₫

Bước 5 - Thanh toán:
  → MobiFone thanh toán 300.000.000₫ (lần 1)
  → Công nợ còn lại: 250.000.000₫
  → Thanh toán nốt → Đơn hàng hoàn tất
```

---

## 2. Quy trình bán hàng

### 2.1 Flowchart quy trình

```
┌──────────┐    Đồng ý    ┌───────────┐   Giao hàng   ┌──────────┐
│  Báo giá │ ───────────► │ Đơn hàng  │ ────────────► │ Hóa đơn  │
│Quotation │              │Sales Order│               │ Invoice  │
└──────────┘              └───────────┘               └──────────┘
     │                         │                           │
     │ Từ chối                 │ Hủy                       │
     ▼                         ▼                           ▼
  [Đóng]                    [Hủy]                    ┌──────────┐
                                                     │Thanh toán│
                                                     │ Payment  │
                                                     └──────────┘
```

### 2.2 Trạng thái đơn hàng (Sales Order Status)

| Trạng thái | Mã | Mô tả |
|---|---|---|
| Nháp | DRAFT | Đơn mới tạo, chưa xác nhận |
| Đã xác nhận | CONFIRMED | Đơn đã được duyệt |
| Đang giao | DELIVERING | Hàng đang được giao |
| Đã giao | DELIVERED | Giao hàng hoàn tất |
| Đã hóa đơn | INVOICED | Đã xuất hóa đơn |
| Hoàn thành | COMPLETED | Thanh toán xong |
| Đã hủy | CANCELLED | Đơn bị hủy |

---

## 3. Thiết kế Database

### 3.1 Bảng Quotation (Báo giá)

```sql
CREATE TABLE quotations (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,        -- QT-2024-0001
    customer_id BIGINT REFERENCES customers(id),
    quote_date DATE NOT NULL,
    valid_until DATE,                         -- Hiệu lực đến ngày
    status VARCHAR(20) DEFAULT 'DRAFT',       -- DRAFT, SENT, ACCEPTED, REJECTED
    total_amount DECIMAL(15,2) DEFAULT 0,
    discount_percent DECIMAL(5,2) DEFAULT 0,
    tax_percent DECIMAL(5,2) DEFAULT 10,
    note TEXT,
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE quotation_items (
    id BIGSERIAL PRIMARY KEY,
    quotation_id BIGINT REFERENCES quotations(id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES products(id),
    quantity INT NOT NULL,
    unit_price DECIMAL(15,2) NOT NULL,
    discount_percent DECIMAL(5,2) DEFAULT 0,
    amount DECIMAL(15,2) NOT NULL             -- quantity * unit_price * (1 - discount)
);
```

### 3.2 Bảng Sales Order (Đơn hàng)

```sql
CREATE TABLE sales_orders (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,         -- SO-2024-0001
    quotation_id BIGINT REFERENCES quotations(id),
    customer_id BIGINT REFERENCES customers(id),
    order_date DATE NOT NULL,
    delivery_date DATE,
    status VARCHAR(20) DEFAULT 'DRAFT',
    total_amount DECIMAL(15,2) DEFAULT 0,
    discount_percent DECIMAL(5,2) DEFAULT 0,
    tax_percent DECIMAL(5,2) DEFAULT 10,
    paid_amount DECIMAL(15,2) DEFAULT 0,
    note TEXT,
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sales_order_items (
    id BIGSERIAL PRIMARY KEY,
    sales_order_id BIGINT REFERENCES sales_orders(id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES products(id),
    quantity INT NOT NULL,
    unit_price DECIMAL(15,2) NOT NULL,
    delivered_quantity INT DEFAULT 0,
    amount DECIMAL(15,2) NOT NULL
);
```

### 3.3 Bảng Invoice (Hóa đơn)

```sql
CREATE TABLE invoices (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,         -- INV-2024-0001
    sales_order_id BIGINT REFERENCES sales_orders(id),
    customer_id BIGINT REFERENCES customers(id),
    invoice_date DATE NOT NULL,
    due_date DATE,                            -- Hạn thanh toán
    status VARCHAR(20) DEFAULT 'DRAFT',       -- DRAFT, SENT, PAID, PARTIALLY_PAID, OVERDUE
    total_amount DECIMAL(15,2) DEFAULT 0,
    tax_amount DECIMAL(15,2) DEFAULT 0,
    paid_amount DECIMAL(15,2) DEFAULT 0,
    note TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    invoice_id BIGINT REFERENCES invoices(id),
    payment_date DATE NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    payment_method VARCHAR(20),               -- CASH, BANK_TRANSFER, CHECK
    reference VARCHAR(50),                    -- Số chứng từ
    note TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 3.4 Mối quan hệ Entity

```
Customer ──1:N──► Quotation ──1:1──► Sales Order ──1:1──► Invoice
                     │                     │                  │
                  1:N │                  1:N │               1:N│
                     ▼                     ▼                  ▼
              QuotationItem         SalesOrderItem         Payment
                     │                     │
                  N:1 │                  N:1│
                     ▼                     ▼
                  Product               Product
```

---

## 4. Backend - Spring Boot REST API

### 4.1 Entity Classes

**SalesOrderEntity:**
```java
@Entity
@Table(name = "sales_orders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SalesOrderEntity extends BaseEntity {

    @Column(unique = true, nullable = false, length = 20)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quotation_id")
    private QuotationEntity quotation;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(length = 20)
    @Builder.Default
    private String status = "DRAFT";

    @Column(name = "total_amount", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "paid_amount", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal paidAmount = BigDecimal.ZERO;

    @OneToMany(mappedBy = "salesOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalesOrderItemEntity> items = new ArrayList<>();

    private String note;
}
```

### 4.2 Service Layer Pattern

```java
@Service
@RequiredArgsConstructor
@Transactional
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepo;
    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;

    // Tạo đơn hàng mới
    public SalesOrderDTO create(CreateSalesOrderRequest request) {
        // 1. Validate customer tồn tại
        CustomerEntity customer = customerRepo.findById(request.getCustomerId())
            .orElseThrow(() -> new ResourceNotFoundException("Khách hàng không tồn tại"));

        // 2. Tạo mã đơn tự động: SO-2024-0001
        String code = generateOrderCode();

        // 3. Tạo đơn hàng
        SalesOrderEntity order = SalesOrderEntity.builder()
            .code(code)
            .customer(customer)
            .orderDate(LocalDate.now())
            .status("DRAFT")
            .build();

        // 4. Thêm chi tiết đơn hàng
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemRequest item : request.getItems()) {
            ProductEntity product = productRepo.findById(item.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại"));

            BigDecimal amount = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(amount);

            SalesOrderItemEntity orderItem = SalesOrderItemEntity.builder()
                .salesOrder(order)
                .product(product)
                .quantity(item.getQuantity())
                .unitPrice(product.getPrice())
                .amount(amount)
                .build();
            order.getItems().add(orderItem);
        }
        order.setTotalAmount(total);

        // 5. Lưu và trả về DTO
        return mapToDTO(salesOrderRepo.save(order));
    }

    // Chuyển trạng thái
    public SalesOrderDTO updateStatus(Long id, String newStatus) {
        SalesOrderEntity order = salesOrderRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Đơn hàng không tồn tại"));

        validateStatusTransition(order.getStatus(), newStatus);
        order.setStatus(newStatus);

        return mapToDTO(salesOrderRepo.save(order));
    }
}
```

### 4.3 Controller Layer

```java
@RestController
@RequestMapping("/api/sales/orders")
@RequiredArgsConstructor
public class SalesOrderController {

    private final SalesOrderService service;

    @GetMapping
    public ApiResponse<Page<SalesOrderDTO>> list(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(required = false) String status,
            Pageable pageable) {
        return ApiResponse.ok(service.findAll(search, status, pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<SalesOrderDTO> getById(@PathVariable Long id) {
        return ApiResponse.ok(service.findById(id));
    }

    @PostMapping
    public ApiResponse<SalesOrderDTO> create(@Valid @RequestBody CreateSalesOrderRequest request) {
        return ApiResponse.ok("Tạo đơn hàng thành công", service.create(request));
    }

    @PutMapping("/{id}/status")
    public ApiResponse<SalesOrderDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ApiResponse.ok("Cập nhật trạng thái thành công", service.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok("Xóa đơn hàng thành công", null);
    }
}
```

---

## 5. Frontend - React Components

### 5.1 Cấu trúc trang Sales

```
src/pages/Sales/
├── SalesDashboard.jsx       ← Tổng quan bán hàng
├── QuotationList.jsx        ← Danh sách báo giá
├── QuotationForm.jsx        ← Form tạo/sửa báo giá
├── SalesOrderList.jsx       ← Danh sách đơn hàng
├── SalesOrderForm.jsx       ← Form tạo/sửa đơn hàng
├── SalesOrderDetail.jsx     ← Chi tiết đơn hàng
├── InvoiceList.jsx          ← Danh sách hóa đơn
├── CustomerList.jsx         ← Danh sách khách hàng
├── CustomerForm.jsx         ← Form khách hàng
└── DebtReport.jsx           ← Báo cáo công nợ
```

### 5.2 Component Pattern - DataTable

```jsx
// Pattern dùng lại cho mọi danh sách trong ERP
function DataTable({ columns, data, loading, pagination, onPageChange }) {
  if (loading) return <div className="loading">Đang tải...</div>;

  return (
    <div className="table-container">
      <table className="data-table">
        <thead>
          <tr>{columns.map(col => <th key={col.key}>{col.title}</th>)}</tr>
        </thead>
        <tbody>
          {data.map((row, idx) => (
            <tr key={row.id || idx}>
              {columns.map(col => (
                <td key={col.key}>
                  {col.render ? col.render(row[col.key], row) : row[col.key]}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
      {/* Pagination */}
    </div>
  );
}
```

### 5.3 Form Pattern - Đơn hàng với chi tiết

```jsx
function SalesOrderForm({ orderId, onSave }) {
  const [order, setOrder] = useState({
    customerId: '',
    note: '',
    items: [{ productId: '', quantity: 1, unitPrice: 0 }]
  });

  const addItem = () => {
    setOrder(prev => ({
      ...prev,
      items: [...prev.items, { productId: '', quantity: 1, unitPrice: 0 }]
    }));
  };

  const removeItem = (index) => {
    setOrder(prev => ({
      ...prev,
      items: prev.items.filter((_, i) => i !== index)
    }));
  };

  const totalAmount = order.items.reduce(
    (sum, item) => sum + item.quantity * item.unitPrice, 0
  );

  return (
    <form onSubmit={handleSubmit}>
      {/* Header: Chọn khách hàng */}
      <select value={order.customerId} onChange={...}>
        <option>-- Chọn khách hàng --</option>
        {customers.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
      </select>

      {/* Chi tiết đơn hàng - dynamic rows */}
      <table>
        <thead><tr><th>Sản phẩm</th><th>SL</th><th>Đơn giá</th><th>Thành tiền</th><th></th></tr></thead>
        <tbody>
          {order.items.map((item, idx) => (
            <tr key={idx}>
              <td><ProductSelect value={item.productId} onChange={...} /></td>
              <td><input type="number" value={item.quantity} onChange={...} /></td>
              <td>{item.unitPrice.toLocaleString()}₫</td>
              <td>{(item.quantity * item.unitPrice).toLocaleString()}₫</td>
              <td><button onClick={() => removeItem(idx)}>🗑️</button></td>
            </tr>
          ))}
        </tbody>
      </table>
      <button type="button" onClick={addItem}>+ Thêm sản phẩm</button>

      {/* Tổng cộng */}
      <div className="total">Tổng: {totalAmount.toLocaleString()}₫</div>

      <button type="submit">💾 Lưu đơn hàng</button>
    </form>
  );
}
```

---

## 6. Báo giá (Quotation)

### 6.1 Nghiệp vụ

- Nhân viên tạo báo giá cho khách hàng
- Báo giá có **thời hạn hiệu lực** (VD: 30 ngày)
- Khách **đồng ý** → chuyển thành Đơn hàng
- Khách **từ chối** → đóng báo giá

### 6.2 API Endpoints

| Method | URL | Mô tả |
|---|---|---|
| GET | `/api/sales/quotations` | Danh sách báo giá (phân trang) |
| GET | `/api/sales/quotations/{id}` | Chi tiết báo giá |
| POST | `/api/sales/quotations` | Tạo báo giá mới |
| PUT | `/api/sales/quotations/{id}` | Cập nhật báo giá |
| POST | `/api/sales/quotations/{id}/convert` | Chuyển thành đơn hàng |
| DELETE | `/api/sales/quotations/{id}` | Xóa báo giá (chỉ DRAFT) |

### 6.3 Chuyển Báo giá → Đơn hàng

```java
public SalesOrderDTO convertQuotationToOrder(Long quotationId) {
    QuotationEntity quotation = quotationRepo.findById(quotationId)
        .orElseThrow(() -> new ResourceNotFoundException("Báo giá không tồn tại"));

    if (!"ACCEPTED".equals(quotation.getStatus())) {
        throw new BusinessException("Chỉ chuyển đổi báo giá đã được chấp nhận");
    }

    // Tạo đơn hàng từ báo giá
    SalesOrderEntity order = SalesOrderEntity.builder()
        .code(generateOrderCode())
        .customer(quotation.getCustomer())
        .quotation(quotation)
        .orderDate(LocalDate.now())
        .totalAmount(quotation.getTotalAmount())
        .build();

    // Copy items
    for (QuotationItemEntity qi : quotation.getItems()) {
        SalesOrderItemEntity item = SalesOrderItemEntity.builder()
            .salesOrder(order)
            .product(qi.getProduct())
            .quantity(qi.getQuantity())
            .unitPrice(qi.getUnitPrice())
            .amount(qi.getAmount())
            .build();
        order.getItems().add(item);
    }

    quotation.setStatus("CONVERTED");
    quotationRepo.save(quotation);

    return mapToDTO(salesOrderRepo.save(order));
}
```

---

## 7. Đơn hàng (Sales Order)

### 7.1 Vòng đời đơn hàng

```
DRAFT → CONFIRMED → DELIVERING → DELIVERED → INVOICED → COMPLETED
  │                                                          
  └──→ CANCELLED (có thể hủy khi còn DRAFT hoặc CONFIRMED)
```

### 7.2 Validation chuyển trạng thái

```java
private void validateStatusTransition(String current, String next) {
    Map<String, Set<String>> allowed = Map.of(
        "DRAFT", Set.of("CONFIRMED", "CANCELLED"),
        "CONFIRMED", Set.of("DELIVERING", "CANCELLED"),
        "DELIVERING", Set.of("DELIVERED"),
        "DELIVERED", Set.of("INVOICED"),
        "INVOICED", Set.of("COMPLETED")
    );

    if (!allowed.getOrDefault(current, Set.of()).contains(next)) {
        throw new BusinessException(
            "Không thể chuyển từ " + current + " sang " + next
        );
    }
}
```

### 7.3 Tích hợp với Kho (Inventory)

Khi đơn hàng chuyển sang `DELIVERING`:
```java
public void processDelivery(Long orderId) {
    SalesOrderEntity order = findOrderById(orderId);

    // Kiểm tra tồn kho đủ không
    for (SalesOrderItemEntity item : order.getItems()) {
        int stock = inventoryService.getStock(item.getProduct().getId());
        if (stock < item.getQuantity()) {
            throw new BusinessException("Tồn kho không đủ: " +
                item.getProduct().getName() + " (tồn: " + stock + ")");
        }
    }

    // Tạo phiếu xuất kho
    inventoryService.createStockOut(order);

    // Cập nhật trạng thái
    order.setStatus("DELIVERING");
    salesOrderRepo.save(order);
}
```

---

## 8. Hóa đơn (Invoice)

### 8.1 Tạo hóa đơn từ đơn hàng

```java
public InvoiceDTO createFromOrder(Long orderId) {
    SalesOrderEntity order = findOrderById(orderId);

    if (!"DELIVERED".equals(order.getStatus())) {
        throw new BusinessException("Chỉ tạo hóa đơn cho đơn đã giao hàng");
    }

    BigDecimal taxAmount = order.getTotalAmount()
        .multiply(BigDecimal.valueOf(0.10));  // VAT 10%

    InvoiceEntity invoice = InvoiceEntity.builder()
        .code(generateInvoiceCode())
        .salesOrder(order)
        .customer(order.getCustomer())
        .invoiceDate(LocalDate.now())
        .dueDate(LocalDate.now().plusDays(30))  // Hạn thanh toán 30 ngày
        .totalAmount(order.getTotalAmount().add(taxAmount))
        .taxAmount(taxAmount)
        .build();

    order.setStatus("INVOICED");
    salesOrderRepo.save(order);

    return mapToDTO(invoiceRepo.save(invoice));
}
```

---

## 9. Công nợ khách hàng

### 9.1 Cách tính công nợ

```
Công nợ = Tổng tiền hóa đơn - Tổng tiền đã thanh toán
```

### 9.2 Query báo cáo công nợ

```sql
SELECT
    c.code AS customer_code,
    c.name AS customer_name,
    SUM(i.total_amount) AS total_invoice,
    SUM(i.paid_amount) AS total_paid,
    SUM(i.total_amount - i.paid_amount) AS debt_amount,
    COUNT(CASE WHEN i.due_date < CURRENT_DATE AND i.status != 'PAID'
          THEN 1 END) AS overdue_count
FROM invoices i
JOIN customers c ON i.customer_id = c.id
WHERE i.status IN ('SENT', 'PARTIALLY_PAID', 'OVERDUE')
GROUP BY c.id, c.code, c.name
HAVING SUM(i.total_amount - i.paid_amount) > 0
ORDER BY debt_amount DESC;
```

### 9.3 Frontend - Bảng công nợ

```jsx
function DebtReport() {
  const [debts, setDebts] = useState([]);

  useEffect(() => {
    api.get('/sales/debts').then(res => setDebts(res.data));
  }, []);

  const totalDebt = debts.reduce((sum, d) => sum + d.debtAmount, 0);

  return (
    <div>
      <h1>📊 Báo cáo Công nợ Khách hàng</h1>
      <div className="summary-card">
        <h3>Tổng công nợ: {totalDebt.toLocaleString('vi-VN')}₫</h3>
      </div>
      <table>
        <thead>
          <tr>
            <th>Mã KH</th><th>Tên KH</th>
            <th>Tổng HĐ</th><th>Đã TT</th>
            <th>Còn nợ</th><th>Quá hạn</th>
          </tr>
        </thead>
        <tbody>
          {debts.map(d => (
            <tr key={d.customerCode} className={d.overdueCount > 0 ? 'overdue' : ''}>
              <td>{d.customerCode}</td>
              <td>{d.customerName}</td>
              <td>{d.totalInvoice.toLocaleString()}₫</td>
              <td>{d.totalPaid.toLocaleString()}₫</td>
              <td className="debt">{d.debtAmount.toLocaleString()}₫</td>
              <td>{d.overdueCount > 0 && <span className="badge-danger">{d.overdueCount} HĐ</span>}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
```

---

> **Tiếp theo:** Chuyển sang module **Quản lý Mua hàng (Procurement)** để xây dựng quy trình mua hàng từ nhà cung cấp.
