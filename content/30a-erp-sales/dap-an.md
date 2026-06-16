# Module Bán Hàng - Đáp Án

## Bài 1: CRUD Khách hàng (Customer)

**CustomerEntity.java:**
```java
@Entity
@Table(name = "customers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CustomerEntity extends BaseEntity {

    @Column(unique = true, nullable = false, length = 20)
    private String code;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "tax_code", length = 20)
    private String taxCode;
}
```

**CustomerDTO.java:**
```java
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CustomerDTO {
    private Long id;

    @NotBlank(message = "Mã khách hàng bắt buộc")
    private String code;

    @NotBlank(message = "Tên khách hàng bắt buộc")
    private String name;

    private String phone;

    @Email(message = "Email không hợp lệ")
    private String email;

    private String address;
    private String taxCode;
}
```

**CustomerService.java:**
```java
@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository repo;

    @Transactional(readOnly = true)
    public Page<CustomerDTO> findAll(String search, Pageable pageable) {
        if (search.isBlank()) {
            return repo.findAll(pageable).map(this::toDTO);
        }
        return repo.findByCodeContainingOrNameContaining(search, search, pageable)
                    .map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public CustomerDTO findById(Long id) {
        return toDTO(repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Khách hàng không tồn tại")));
    }

    public CustomerDTO create(CustomerDTO dto) {
        if (repo.existsByCode(dto.getCode())) {
            throw new BusinessException("Mã khách hàng đã tồn tại: " + dto.getCode());
        }
        CustomerEntity entity = toEntity(dto);
        return toDTO(repo.save(entity));
    }

    public CustomerDTO update(Long id, CustomerDTO dto) {
        CustomerEntity entity = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Khách hàng không tồn tại"));
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setAddress(dto.getAddress());
        entity.setTaxCode(dto.getTaxCode());
        return toDTO(repo.save(entity));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Khách hàng không tồn tại");
        }
        repo.deleteById(id);
    }

    private CustomerDTO toDTO(CustomerEntity e) {
        return CustomerDTO.builder()
            .id(e.getId()).code(e.getCode()).name(e.getName())
            .phone(e.getPhone()).email(e.getEmail())
            .address(e.getAddress()).taxCode(e.getTaxCode())
            .build();
    }

    private CustomerEntity toEntity(CustomerDTO d) {
        return CustomerEntity.builder()
            .code(d.getCode()).name(d.getName())
            .phone(d.getPhone()).email(d.getEmail())
            .address(d.getAddress()).taxCode(d.getTaxCode())
            .build();
    }
}
```

**CustomerController.java:**
```java
@RestController
@RequestMapping("/api/sales/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @GetMapping
    public ApiResponse<Page<CustomerDTO>> list(
            @RequestParam(defaultValue = "") String search,
            Pageable pageable) {
        return ApiResponse.ok(service.findAll(search, pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<CustomerDTO> getById(@PathVariable Long id) {
        return ApiResponse.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerDTO>> create(
            @Valid @RequestBody CustomerDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.ok("Tạo khách hàng thành công", service.create(dto)));
    }

    @PutMapping("/{id}")
    public ApiResponse<CustomerDTO> update(@PathVariable Long id,
            @Valid @RequestBody CustomerDTO dto) {
        return ApiResponse.ok("Cập nhật thành công", service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok("Xóa thành công", null);
    }
}
```

**Frontend - CustomerList.jsx:**
```jsx
import React, { useState, useEffect, useCallback } from 'react';
import api from '../../services/api';

function CustomerList() {
  const [customers, setCustomers] = useState([]);
  const [search, setSearch] = useState('');
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [showModal, setShowModal] = useState(false);
  const [editItem, setEditItem] = useState(null);
  const [form, setForm] = useState({ code:'', name:'', phone:'', email:'', address:'', taxCode:'' });

  const loadData = useCallback(async () => {
    const res = await api.get(`/sales/customers?search=${search}&page=${page}&size=10`);
    setCustomers(res.data.content);
    setTotalPages(res.data.totalPages);
  }, [search, page]);

  useEffect(() => { loadData(); }, [loadData]);

  const openCreate = () => {
    setEditItem(null);
    setForm({ code:'', name:'', phone:'', email:'', address:'', taxCode:'' });
    setShowModal(true);
  };

  const openEdit = (item) => {
    setEditItem(item);
    setForm({ ...item });
    setShowModal(true);
  };

  const handleSave = async (e) => {
    e.preventDefault();
    try {
      if (editItem) {
        await api.put(`/sales/customers/${editItem.id}`, form);
      } else {
        await api.post('/sales/customers', form);
      }
      setShowModal(false);
      loadData();
      alert(editItem ? 'Cập nhật thành công!' : 'Tạo mới thành công!');
    } catch (err) {
      alert(err.response?.data?.message || 'Có lỗi xảy ra');
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Xác nhận xóa?')) return;
    await api.delete(`/sales/customers/${id}`);
    loadData();
  };

  return (
    <div>
      <div className="page-header">
        <h1>👥 Khách hàng</h1>
        <button className="btn-primary" onClick={openCreate}>+ Thêm mới</button>
      </div>
      <input type="text" placeholder="Tìm kiếm..."
        value={search} onChange={e => { setSearch(e.target.value); setPage(0); }} />
      <table className="data-table">
        <thead>
          <tr><th>Mã</th><th>Tên</th><th>SĐT</th><th>Email</th><th>MST</th><th>Thao tác</th></tr>
        </thead>
        <tbody>
          {customers.map(c => (
            <tr key={c.id}>
              <td>{c.code}</td><td>{c.name}</td><td>{c.phone}</td>
              <td>{c.email}</td><td>{c.taxCode}</td>
              <td>
                <button onClick={() => openEdit(c)}>✏️</button>
                <button onClick={() => handleDelete(c.id)}>🗑️</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {/* Modal form */}
      {showModal && (
        <div className="modal-overlay">
          <div className="modal">
            <h3>{editItem ? 'Cập nhật khách hàng' : 'Thêm khách hàng mới'}</h3>
            <form onSubmit={handleSave}>
              <input placeholder="Mã KH" value={form.code}
                onChange={e => setForm({...form, code: e.target.value})}
                disabled={!!editItem} required />
              <input placeholder="Tên KH" value={form.name}
                onChange={e => setForm({...form, name: e.target.value})} required />
              <input placeholder="SĐT" value={form.phone}
                onChange={e => setForm({...form, phone: e.target.value})} />
              <input placeholder="Email" type="email" value={form.email}
                onChange={e => setForm({...form, email: e.target.value})} />
              <textarea placeholder="Địa chỉ" value={form.address}
                onChange={e => setForm({...form, address: e.target.value})} />
              <input placeholder="Mã số thuế" value={form.taxCode}
                onChange={e => setForm({...form, taxCode: e.target.value})} />
              <div className="modal-actions">
                <button type="button" onClick={() => setShowModal(false)}>Hủy</button>
                <button type="submit" className="btn-primary">Lưu</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

export default CustomerList;
```

**Giải thích:**
- Backend theo pattern: Entity → DTO → Repository → Service → Controller
- Service kiểm tra code trùng trước khi tạo mới
- Frontend dùng Modal form chung cho cả Thêm/Sửa, disable code khi sửa
- `useCallback` tránh re-create hàm loadData khi re-render

---

## Bài 2: Tạo Báo giá (Quotation)

**QuotationService.java (phần tạo mới):**
```java
@Service
@RequiredArgsConstructor
@Transactional
public class QuotationService {

    private final QuotationRepository quotationRepo;
    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;

    public QuotationDTO create(CreateQuotationRequest request) {
        CustomerEntity customer = customerRepo.findById(request.getCustomerId())
            .orElseThrow(() -> new ResourceNotFoundException("Khách hàng không tồn tại"));

        String code = generateCode();

        QuotationEntity quotation = QuotationEntity.builder()
            .code(code)
            .customer(customer)
            .quoteDate(LocalDate.now())
            .validUntil(request.getValidUntil())
            .status("DRAFT")
            .build();

        BigDecimal total = BigDecimal.ZERO;
        for (QuotationItemRequest itemReq : request.getItems()) {
            ProductEntity product = productRepo.findById(itemReq.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại"));

            BigDecimal amount = product.getPrice()
                .multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            total = total.add(amount);

            QuotationItemEntity item = QuotationItemEntity.builder()
                .quotation(quotation)
                .product(product)
                .quantity(itemReq.getQuantity())
                .unitPrice(product.getPrice())
                .amount(amount)
                .build();
            quotation.getItems().add(item);
        }
        quotation.setTotalAmount(total);

        return mapToDTO(quotationRepo.save(quotation));
    }

    private String generateCode() {
        int year = LocalDate.now().getYear();
        long count = quotationRepo.countByCodeStartingWith("QT-" + year);
        return String.format("QT-%d-%04d", year, count + 1);
    }
}
```

**Frontend - QuotationForm.jsx:**
```jsx
import React, { useState, useEffect } from 'react';
import api from '../../services/api';

function QuotationForm({ onSave, onCancel }) {
  const [customers, setCustomers] = useState([]);
  const [products, setProducts] = useState([]);
  const [form, setForm] = useState({
    customerId: '',
    validUntil: '',
    items: [{ productId: '', quantity: 1, unitPrice: 0, amount: 0 }]
  });

  useEffect(() => {
    api.get('/sales/customers?size=1000').then(r => setCustomers(r.data.content));
    api.get('/products?size=1000').then(r => setProducts(r.data.content));
  }, []);

  const handleProductChange = (index, productId) => {
    const product = products.find(p => p.id === Number(productId));
    const items = [...form.items];
    items[index] = {
      ...items[index],
      productId,
      unitPrice: product ? product.price : 0,
      amount: product ? product.price * items[index].quantity : 0
    };
    setForm({ ...form, items });
  };

  const handleQuantityChange = (index, quantity) => {
    const items = [...form.items];
    items[index] = {
      ...items[index],
      quantity: Number(quantity),
      amount: items[index].unitPrice * Number(quantity)
    };
    setForm({ ...form, items });
  };

  const addItem = () => {
    setForm({
      ...form,
      items: [...form.items, { productId: '', quantity: 1, unitPrice: 0, amount: 0 }]
    });
  };

  const removeItem = (index) => {
    setForm({
      ...form,
      items: form.items.filter((_, i) => i !== index)
    });
  };

  const totalAmount = form.items.reduce((sum, item) => sum + item.amount, 0);

  const handleSubmit = async (e) => {
    e.preventDefault();
    await api.post('/sales/quotations', {
      customerId: form.customerId,
      validUntil: form.validUntil,
      items: form.items.map(i => ({ productId: i.productId, quantity: i.quantity }))
    });
    onSave();
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Tạo Báo giá mới</h2>
      <div className="form-row">
        <label>Khách hàng:</label>
        <select value={form.customerId} required
          onChange={e => setForm({...form, customerId: e.target.value})}>
          <option value="">-- Chọn khách hàng --</option>
          {customers.map(c => <option key={c.id} value={c.id}>{c.code} - {c.name}</option>)}
        </select>
      </div>
      <div className="form-row">
        <label>Hiệu lực đến:</label>
        <input type="date" value={form.validUntil}
          onChange={e => setForm({...form, validUntil: e.target.value})} />
      </div>

      <h3>Chi tiết sản phẩm</h3>
      <table>
        <thead>
          <tr><th>Sản phẩm</th><th>Số lượng</th><th>Đơn giá</th><th>Thành tiền</th><th></th></tr>
        </thead>
        <tbody>
          {form.items.map((item, idx) => (
            <tr key={idx}>
              <td>
                <select value={item.productId} required
                  onChange={e => handleProductChange(idx, e.target.value)}>
                  <option value="">-- Chọn SP --</option>
                  {products.map(p =>
                    <option key={p.id} value={p.id}>{p.code} - {p.name}</option>
                  )}
                </select>
              </td>
              <td>
                <input type="number" min="1" value={item.quantity}
                  onChange={e => handleQuantityChange(idx, e.target.value)} />
              </td>
              <td>{item.unitPrice.toLocaleString('vi-VN')}₫</td>
              <td>{item.amount.toLocaleString('vi-VN')}₫</td>
              <td>
                {form.items.length > 1 &&
                  <button type="button" onClick={() => removeItem(idx)}>🗑️</button>}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <button type="button" onClick={addItem}>+ Thêm sản phẩm</button>

      <div className="total-section">
        <h3>Tổng cộng: {totalAmount.toLocaleString('vi-VN')}₫</h3>
      </div>

      <div className="form-actions">
        <button type="button" onClick={onCancel}>Hủy</button>
        <button type="submit" className="btn-primary">💾 Lưu báo giá</button>
      </div>
    </form>
  );
}

export default QuotationForm;
```

**Giải thích:**
- Mã báo giá tự sinh theo format QT-{year}-{sequence}
- Dynamic rows: thêm/xóa sản phẩm trong form
- Khi chọn sản phẩm, tự fill đơn giá → tính thành tiền realtime
- Tổng tiền cập nhật realtime khi thay đổi

---

## Bài 3: Đơn hàng (Sales Order)

**SalesOrderService.java (convert quotation):**
```java
public SalesOrderDTO convertFromQuotation(Long quotationId) {
    QuotationEntity qt = quotationRepo.findById(quotationId)
        .orElseThrow(() -> new ResourceNotFoundException("Báo giá không tồn tại"));

    if (!"ACCEPTED".equals(qt.getStatus())) {
        throw new BusinessException("Chỉ convert báo giá đã được chấp nhận");
    }

    SalesOrderEntity order = SalesOrderEntity.builder()
        .code(generateOrderCode())
        .quotation(qt)
        .customer(qt.getCustomer())
        .orderDate(LocalDate.now())
        .status("DRAFT")
        .totalAmount(qt.getTotalAmount())
        .build();

    for (QuotationItemEntity qi : qt.getItems()) {
        order.getItems().add(SalesOrderItemEntity.builder()
            .salesOrder(order)
            .product(qi.getProduct())
            .quantity(qi.getQuantity())
            .unitPrice(qi.getUnitPrice())
            .amount(qi.getAmount())
            .build());
    }

    qt.setStatus("CONVERTED");
    quotationRepo.save(qt);

    return mapToDTO(salesOrderRepo.save(order));
}

public SalesOrderDTO updateStatus(Long id, String newStatus) {
    SalesOrderEntity order = salesOrderRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Đơn hàng không tồn tại"));

    validateStatusTransition(order.getStatus(), newStatus);
    order.setStatus(newStatus);

    return mapToDTO(salesOrderRepo.save(order));
}

private void validateStatusTransition(String current, String next) {
    Map<String, Set<String>> allowed = Map.of(
        "DRAFT", Set.of("CONFIRMED", "CANCELLED"),
        "CONFIRMED", Set.of("DELIVERING", "CANCELLED"),
        "DELIVERING", Set.of("DELIVERED"),
        "DELIVERED", Set.of("INVOICED"),
        "INVOICED", Set.of("COMPLETED")
    );
    if (!allowed.getOrDefault(current, Set.of()).contains(next)) {
        throw new BusinessException("Không thể chuyển từ " + current + " sang " + next);
    }
}
```

**Frontend - SalesOrderDetail.jsx (trạng thái + timeline):**
```jsx
const statusFlow = ['DRAFT','CONFIRMED','DELIVERING','DELIVERED','INVOICED','COMPLETED'];
const statusLabels = {
  DRAFT: 'Nháp', CONFIRMED: 'Đã duyệt', DELIVERING: 'Đang giao',
  DELIVERED: 'Đã giao', INVOICED: 'Đã xuất HĐ', COMPLETED: 'Hoàn thành',
  CANCELLED: 'Đã hủy'
};
const statusColors = {
  DRAFT: '#999', CONFIRMED: '#1890ff', DELIVERING: '#fa8c16',
  DELIVERED: '#52c41a', INVOICED: '#722ed1', COMPLETED: '#389e0d',
  CANCELLED: '#ff4d4f'
};

function StatusTimeline({ currentStatus }) {
  const currentIdx = statusFlow.indexOf(currentStatus);
  return (
    <div className="status-timeline">
      {statusFlow.map((s, idx) => (
        <div key={s} className={`timeline-step ${idx <= currentIdx ? 'active' : ''}`}>
          <div className="timeline-dot"
            style={{ backgroundColor: idx <= currentIdx ? statusColors[s] : '#ddd' }}>
            {idx < currentIdx ? '✓' : idx + 1}
          </div>
          <span>{statusLabels[s]}</span>
        </div>
      ))}
    </div>
  );
}
```

**Giải thích:**
- Convert quotation → sales order copy toàn bộ items, đánh trạng thái CONVERTED
- Status transition validation đảm bảo flow đúng nghiệp vụ
- Frontend hiển thị timeline trực quan cho trạng thái đơn hàng

---

## Bài 4: Hóa đơn & Thanh toán

**InvoiceService.java:**
```java
public InvoiceDTO createFromOrder(Long orderId) {
    SalesOrderEntity order = orderRepo.findById(orderId)
        .orElseThrow(() -> new ResourceNotFoundException("Đơn hàng không tồn tại"));

    if (!"DELIVERED".equals(order.getStatus())) {
        throw new BusinessException("Chỉ tạo hóa đơn cho đơn đã giao");
    }

    BigDecimal taxAmount = order.getTotalAmount().multiply(new BigDecimal("0.10"));

    InvoiceEntity invoice = InvoiceEntity.builder()
        .code(generateInvoiceCode())
        .salesOrder(order)
        .customer(order.getCustomer())
        .invoiceDate(LocalDate.now())
        .dueDate(LocalDate.now().plusDays(30))
        .totalAmount(order.getTotalAmount().add(taxAmount))
        .taxAmount(taxAmount)
        .status("SENT")
        .build();

    order.setStatus("INVOICED");
    orderRepo.save(order);

    return mapToDTO(invoiceRepo.save(invoice));
}

public PaymentDTO addPayment(Long invoiceId, CreatePaymentRequest request) {
    InvoiceEntity invoice = invoiceRepo.findById(invoiceId)
        .orElseThrow(() -> new ResourceNotFoundException("Hóa đơn không tồn tại"));

    PaymentEntity payment = PaymentEntity.builder()
        .invoice(invoice)
        .paymentDate(request.getPaymentDate())
        .amount(request.getAmount())
        .paymentMethod(request.getPaymentMethod())
        .reference(request.getReference())
        .build();

    paymentRepo.save(payment);

    // Cập nhật paid amount
    BigDecimal totalPaid = invoice.getPaidAmount().add(request.getAmount());
    invoice.setPaidAmount(totalPaid);

    if (totalPaid.compareTo(invoice.getTotalAmount()) >= 0) {
        invoice.setStatus("PAID");
        // Cập nhật đơn hàng thành COMPLETED
        invoice.getSalesOrder().setStatus("COMPLETED");
    } else {
        invoice.setStatus("PARTIALLY_PAID");
    }
    invoiceRepo.save(invoice);

    return mapToDTO(payment);
}
```

**Giải thích:**
- Hóa đơn tự tính VAT 10% và đặt hạn thanh toán 30 ngày
- Thanh toán hỗ trợ nhiều lần (partial payment)
- Khi tổng thanh toán ≥ tổng hóa đơn → tự chuyển PAID và đơn hàng COMPLETED

---

## Bài 5: Báo cáo Công nợ

**DebtReportService.java:**
```java
public List<CustomerDebtDTO> getDebtReport(LocalDate from, LocalDate to) {
    return invoiceRepo.getCustomerDebts(from, to);
}

// Repository query
@Query("""
    SELECT new com.erp.module.sales.dto.CustomerDebtDTO(
        c.code, c.name,
        COALESCE(SUM(i.totalAmount), 0),
        COALESCE(SUM(i.paidAmount), 0),
        COALESCE(SUM(i.totalAmount - i.paidAmount), 0),
        COUNT(CASE WHEN i.dueDate < CURRENT_DATE AND i.status <> 'PAID' THEN 1 END)
    )
    FROM InvoiceEntity i JOIN i.customer c
    WHERE i.invoiceDate BETWEEN :from AND :to
    AND i.status IN ('SENT', 'PARTIALLY_PAID', 'OVERDUE')
    GROUP BY c.id, c.code, c.name
    HAVING SUM(i.totalAmount - i.paidAmount) > 0
    ORDER BY SUM(i.totalAmount - i.paidAmount) DESC
    """)
List<CustomerDebtDTO> getCustomerDebts(
    @Param("from") LocalDate from,
    @Param("to") LocalDate to);
```

**Frontend - DebtReport.jsx:**
```jsx
import React, { useState, useEffect } from 'react';
import { Pie } from 'react-chartjs-2';
import api from '../../services/api';

function DebtReport() {
  const [debts, setDebts] = useState([]);
  const [summary, setSummary] = useState({});

  useEffect(() => {
    api.get('/sales/debts').then(r => setDebts(r.data));
    api.get('/sales/debts/summary').then(r => setSummary(r.data));
  }, []);

  const chartData = {
    labels: debts.slice(0, 5).map(d => d.customerName),
    datasets: [{
      data: debts.slice(0, 5).map(d => d.debtAmount),
      backgroundColor: ['#1890ff','#52c41a','#fa8c16','#722ed1','#ff4d4f']
    }]
  };

  const exportCSV = () => {
    const header = 'Mã KH,Tên KH,Tổng HĐ,Đã TT,Còn nợ\n';
    const rows = debts.map(d =>
      `${d.customerCode},${d.customerName},${d.totalInvoice},${d.totalPaid},${d.debtAmount}`
    ).join('\n');
    const blob = new Blob([header + rows], { type: 'text/csv' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'cong-no-khach-hang.csv';
    a.click();
  };

  return (
    <div className="debt-report">
      <h1>📊 Báo cáo Công nợ Khách hàng</h1>

      <div className="summary-cards">
        <div className="card">
          <h4>Tổng công nợ</h4>
          <p>{summary.totalDebt?.toLocaleString('vi-VN')}₫</p>
        </div>
        <div className="card">
          <h4>Số KH đang nợ</h4>
          <p>{summary.customerCount}</p>
        </div>
        <div className="card warning">
          <h4>HĐ quá hạn</h4>
          <p>{summary.overdueCount}</p>
        </div>
      </div>

      <div className="report-content">
        <div className="chart-section">
          <h3>Top 5 Công nợ</h3>
          <Pie data={chartData} />
        </div>

        <table className="data-table">
          <thead>
            <tr>
              <th>Mã KH</th><th>Tên</th><th>Tổng HĐ</th>
              <th>Đã TT</th><th>Còn nợ</th><th>Quá hạn</th>
            </tr>
          </thead>
          <tbody>
            {debts.map(d => (
              <tr key={d.customerCode}
                  className={d.overdueCount > 0 ? 'row-danger' : ''}>
                <td>{d.customerCode}</td>
                <td>{d.customerName}</td>
                <td>{d.totalInvoice.toLocaleString('vi-VN')}₫</td>
                <td>{d.totalPaid.toLocaleString('vi-VN')}₫</td>
                <td className="text-danger">
                  {d.debtAmount.toLocaleString('vi-VN')}₫
                </td>
                <td>
                  {d.overdueCount > 0 &&
                    <span className="badge-danger">{d.overdueCount} HĐ</span>}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <button onClick={exportCSV}>📥 Export CSV</button>
      </div>
    </div>
  );
}

export default DebtReport;
```

**Giải thích:**
- JPQL query JOIN invoices + customers, GROUP BY để tính tổng nợ
- Pie chart hiển thị top 5 khách nợ nhiều nhất
- Export CSV tạo file download từ client-side
- Highlight dòng quá hạn bằng class `row-danger`
