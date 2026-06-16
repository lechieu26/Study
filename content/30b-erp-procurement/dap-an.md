# Module Mua Hàng - Đáp Án

## Bài 1: CRUD Nhà cung cấp

**SupplierEntity.java:**
```java
@Entity
@Table(name = "suppliers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SupplierEntity extends BaseEntity {

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

    @Column(name = "contact_person", length = 100)
    private String contactPerson;
}
```

**SupplierService.java:**
```java
@Service
@RequiredArgsConstructor
@Transactional
public class SupplierService {

    private final SupplierRepository repo;

    @Transactional(readOnly = true)
    public Page<SupplierDTO> findAll(String search, Pageable pageable) {
        if (search.isBlank()) return repo.findAll(pageable).map(this::toDTO);
        return repo.findByCodeContainingOrNameContaining(search, search, pageable)
                    .map(this::toDTO);
    }

    public SupplierDTO create(SupplierDTO dto) {
        if (repo.existsByCode(dto.getCode())) {
            throw new BusinessException("Mã NCC đã tồn tại");
        }
        return toDTO(repo.save(toEntity(dto)));
    }

    public SupplierDTO update(Long id, SupplierDTO dto) {
        SupplierEntity entity = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("NCC không tồn tại"));
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setAddress(dto.getAddress());
        entity.setTaxCode(dto.getTaxCode());
        entity.setContactPerson(dto.getContactPerson());
        return toDTO(repo.save(entity));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
```

**Giải thích:**
- Pattern giống hệt Customer CRUD nhưng cho Supplier
- Sử dụng Spring Data JPA `findByCodeContainingOrNameContaining` cho tìm kiếm

---

## Bài 2: Yêu cầu mua hàng

**PurchaseRequestService.java:**
```java
@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseRequestService {

    private final PurchaseRequestRepository repo;
    private final ProductRepository productRepo;

    public PurchaseRequestDTO create(CreatePRRequest request) {
        PurchaseRequestEntity pr = PurchaseRequestEntity.builder()
            .code(generateCode())
            .requestDate(LocalDate.now())
            .department(request.getDepartment())
            .reason(request.getReason())
            .status("PENDING")
            .build();

        for (PRItemRequest item : request.getItems()) {
            ProductEntity product = productRepo.findById(item.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("SP không tồn tại"));
            pr.getItems().add(PurchaseRequestItemEntity.builder()
                .request(pr)
                .product(product)
                .quantity(item.getQuantity())
                .note(item.getNote())
                .build());
        }

        return mapToDTO(repo.save(pr));
    }

    public PurchaseRequestDTO approve(Long id) {
        PurchaseRequestEntity pr = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("YCMH không tồn tại"));
        if (!"PENDING".equals(pr.getStatus())) {
            throw new BusinessException("YCMH đã được xử lý");
        }
        pr.setStatus("APPROVED");
        pr.setApprovedDate(LocalDate.now());
        return mapToDTO(repo.save(pr));
    }

    public PurchaseRequestDTO reject(Long id, String reason) {
        PurchaseRequestEntity pr = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("YCMH không tồn tại"));
        pr.setStatus("REJECTED");
        pr.setNote(reason);
        return mapToDTO(repo.save(pr));
    }
}
```

**Frontend - PurchaseRequestList.jsx:**
```jsx
function PurchaseRequestList() {
  const [requests, setRequests] = useState([]);
  const [statusFilter, setStatusFilter] = useState('ALL');

  useEffect(() => {
    const params = statusFilter !== 'ALL' ? `?status=${statusFilter}` : '';
    api.get(`/procurement/requests${params}`).then(r => setRequests(r.data.content));
  }, [statusFilter]);

  const statusBadge = (status) => {
    const colors = { PENDING: '#faad14', APPROVED: '#52c41a', REJECTED: '#ff4d4f' };
    const labels = { PENDING: 'Chờ duyệt', APPROVED: 'Đã duyệt', REJECTED: 'Từ chối' };
    return (
      <span className="badge" style={{ backgroundColor: colors[status], color: '#fff' }}>
        {labels[status]}
      </span>
    );
  };

  return (
    <div>
      <h1>📋 Yêu cầu mua hàng</h1>
      <div className="filters">
        {['ALL','PENDING','APPROVED','REJECTED'].map(s => (
          <button key={s} className={statusFilter === s ? 'active' : ''}
            onClick={() => setStatusFilter(s)}>
            {s === 'ALL' ? 'Tất cả' : s}
          </button>
        ))}
      </div>
      <table className="data-table">
        <thead>
          <tr><th>Mã</th><th>Phòng ban</th><th>Ngày</th><th>Trạng thái</th><th>Thao tác</th></tr>
        </thead>
        <tbody>
          {requests.map(r => (
            <tr key={r.id}>
              <td>{r.code}</td><td>{r.department}</td>
              <td>{r.requestDate}</td><td>{statusBadge(r.status)}</td>
              <td><a href={`/procurement/requests/${r.id}`}>Xem</a></td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
```

**Giải thích:**
- YCMH có flow đơn giản: PENDING → APPROVED/REJECTED
- Frontend filter bằng query parameter `?status=`
- Badge màu giúp phân biệt trạng thái nhanh

---

## Bài 3: Đơn đặt hàng (Purchase Order)

**PurchaseOrderService - tạo từ YCMH:**
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
        .status("DRAFT")
        .build();

    BigDecimal total = BigDecimal.ZERO;
    for (PurchaseRequestItemEntity pri : pr.getItems()) {
        BigDecimal unitPrice = pri.getProduct().getCostPrice();
        BigDecimal amount = unitPrice.multiply(BigDecimal.valueOf(pri.getQuantity()));
        total = total.add(amount);

        po.getItems().add(PurchaseOrderItemEntity.builder()
            .purchaseOrder(po)
            .product(pri.getProduct())
            .quantity(pri.getQuantity())
            .unitPrice(unitPrice)
            .amount(amount)
            .build());
    }
    po.setTotalAmount(total);

    return mapToDTO(poRepo.save(po));
}
```

**Giải thích:**
- Copy items từ YCMH, sử dụng `costPrice` (giá gốc) làm đơn giá mua
- Tính tổng tiền PO tự động

---

## Bài 4: Nhập hàng

**GoodsReceiptService.java:**
```java
public GoodsReceiptDTO receive(Long poId, ReceiveRequest request) {
    PurchaseOrderEntity po = poRepo.findById(poId)
        .orElseThrow(() -> new ResourceNotFoundException("PO không tồn tại"));

    if (!"CONFIRMED".equals(po.getStatus()) && !"PARTIALLY_RECEIVED".equals(po.getStatus())) {
        throw new BusinessException("PO chưa ở trạng thái có thể nhập hàng");
    }

    GoodsReceiptEntity gr = GoodsReceiptEntity.builder()
        .code(generateGRCode())
        .purchaseOrder(po)
        .warehouseId(request.getWarehouseId())
        .receiptDate(LocalDate.now())
        .build();

    boolean allReceived = true;
    for (ReceiveItemRequest item : request.getItems()) {
        if (item.getReceivedQuantity() <= 0) continue;

        PurchaseOrderItemEntity poItem = po.getItems().stream()
            .filter(i -> i.getProduct().getId().equals(item.getProductId()))
            .findFirst().orElseThrow();

        int remaining = poItem.getQuantity() - poItem.getReceivedQuantity();
        if (item.getReceivedQuantity() > remaining) {
            throw new BusinessException("SL nhận vượt quá SL chưa nhận");
        }

        gr.getItems().add(GoodsReceiptItemEntity.builder()
            .receipt(gr)
            .productId(item.getProductId())
            .orderedQuantity(poItem.getQuantity())
            .receivedQuantity(item.getReceivedQuantity())
            .note(item.getNote())
            .build());

        // Cập nhật SL đã nhận trên PO item
        poItem.setReceivedQuantity(
            poItem.getReceivedQuantity() + item.getReceivedQuantity());

        if (poItem.getReceivedQuantity() < poItem.getQuantity()) {
            allReceived = false;
        }

        // Tăng tồn kho
        inventoryService.increaseStock(
            request.getWarehouseId(),
            item.getProductId(),
            item.getReceivedQuantity());
    }

    po.setStatus(allReceived ? "RECEIVED" : "PARTIALLY_RECEIVED");
    poRepo.save(po);

    gr.setStatus("COMPLETED");
    return mapToDTO(grRepo.save(gr));
}
```

**Giải thích:**
- Hỗ trợ nhận hàng nhiều lần (partial receive)
- Kiểm tra SL nhận không vượt quá SL chưa nhận
- Tự động cập nhật tồn kho qua `inventoryService.increaseStock()`
- PO tự chuyển PARTIALLY_RECEIVED hoặc RECEIVED tùy theo SL

---

## Bài 5: Báo cáo công nợ phải trả

**Repository query:**
```java
@Query("""
    SELECT new com.erp.module.procurement.dto.SupplierDebtDTO(
        s.code, s.name,
        COALESCE(SUM(po.totalAmount), 0),
        COALESCE(SUM(po.paidAmount), 0),
        COALESCE(SUM(po.totalAmount - po.paidAmount), 0)
    )
    FROM PurchaseOrderEntity po JOIN po.supplier s
    WHERE po.status IN ('RECEIVED', 'PARTIALLY_RECEIVED', 'COMPLETED')
    GROUP BY s.id, s.code, s.name
    HAVING SUM(po.totalAmount - po.paidAmount) > 0
    ORDER BY SUM(po.totalAmount - po.paidAmount) DESC
    """)
List<SupplierDebtDTO> getSupplierDebts();
```

**Frontend - APDebtReport.jsx:**
```jsx
function APDebtReport() {
  const [debts, setDebts] = useState([]);

  useEffect(() => {
    api.get('/procurement/debts').then(r => setDebts(r.data));
  }, []);

  const totalDebt = debts.reduce((sum, d) => sum + d.debtAmount, 0);

  return (
    <div>
      <h1>📊 Công nợ phải trả NCC</h1>
      <div className="summary-card">
        <h3>Tổng nợ: {totalDebt.toLocaleString('vi-VN')}₫</h3>
      </div>
      <table className="data-table">
        <thead>
          <tr><th>Mã NCC</th><th>Tên</th><th>Tổng PO</th><th>Đã TT</th><th>Còn nợ</th></tr>
        </thead>
        <tbody>
          {debts.map(d => (
            <tr key={d.supplierCode}>
              <td>{d.supplierCode}</td><td>{d.supplierName}</td>
              <td>{d.totalPO.toLocaleString('vi-VN')}₫</td>
              <td>{d.totalPaid.toLocaleString('vi-VN')}₫</td>
              <td className="text-danger">{d.debtAmount.toLocaleString('vi-VN')}₫</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
```

**Giải thích:**
- Công nợ phải trả = tổng PO đã nhận hàng - tổng đã thanh toán
- JPQL GROUP BY supplier, HAVING để lọc chỉ NCC đang nợ
- Tương tự cấu trúc của báo cáo công nợ khách hàng nhưng ngược chiều
