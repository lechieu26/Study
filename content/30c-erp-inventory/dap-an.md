# Module Kho - Đáp Án

## Bài 1: Quản lý Kho hàng

**WarehouseEntity.java:**
```java
@Entity
@Table(name = "warehouses")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WarehouseEntity extends BaseEntity {

    @Column(unique = true, nullable = false, length = 20)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private UserEntity manager;

    @Builder.Default
    private Boolean active = true;
}
```

**WarehouseController.java:**
```java
@RestController
@RequestMapping("/api/inventory/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService service;

    @GetMapping
    public ApiResponse<Page<WarehouseDTO>> list(
            @RequestParam(defaultValue = "") String search, Pageable pageable) {
        return ApiResponse.ok(service.findAll(search, pageable));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<WarehouseDTO>> create(
            @Valid @RequestBody WarehouseDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.ok("Tạo kho thành công", service.create(dto)));
    }

    @PutMapping("/{id}")
    public ApiResponse<WarehouseDTO> update(@PathVariable Long id,
            @Valid @RequestBody WarehouseDTO dto) {
        return ApiResponse.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok("Xóa thành công", null);
    }
}
```

**Giải thích:**
- Warehouse có quan hệ ManyToOne với User (manager)
- CRUD pattern giống Customer/Supplier

---

## Bài 2: Xem tồn kho Realtime

**StockDTO.java:**
```java
@Data @AllArgsConstructor
public class StockDTO {
    private Long warehouseId;
    private String warehouseName;
    private Long productId;
    private String productCode;
    private String productName;
    private int quantity;
    private int minStock;
    private String status; // OK, LOW, OUT_OF_STOCK
}
```

**InventoryController.java:**
```java
@GetMapping("/stock")
public ApiResponse<List<StockDTO>> getStock(
        @RequestParam(required = false) Long warehouseId) {
    return ApiResponse.ok(inventoryService.getStockList(warehouseId));
}

@GetMapping("/alerts")
public ApiResponse<List<StockDTO>> getAlerts() {
    return ApiResponse.ok(inventoryService.getLowStockAlerts());
}
```

**InventoryService.java:**
```java
@Transactional(readOnly = true)
public List<StockDTO> getStockList(Long warehouseId) {
    List<Stock> stocks = warehouseId != null
        ? stockRepo.findByWarehouseId(warehouseId)
        : stockRepo.findAll();

    return stocks.stream().map(s -> {
        ProductEntity p = productRepo.findById(s.getProductId()).orElseThrow();
        WarehouseEntity w = warehouseRepo.findById(s.getWarehouseId()).orElseThrow();
        String status = s.getQuantity() <= 0 ? "OUT_OF_STOCK"
            : s.getQuantity() <= p.getMinStock() ? "LOW" : "OK";
        return new StockDTO(w.getId(), w.getName(), p.getId(), p.getCode(),
            p.getName(), s.getQuantity(), p.getMinStock(), status);
    }).toList();
}

@Transactional(readOnly = true)
public List<StockDTO> getLowStockAlerts() {
    return getStockList(null).stream()
        .filter(s -> !"OK".equals(s.getStatus()))
        .toList();
}
```

**Frontend - StockOverview.jsx:**
```jsx
function StockOverview() {
  const [stocks, setStocks] = useState([]);
  const [alerts, setAlerts] = useState([]);
  const [warehouseId, setWarehouseId] = useState('');
  const [warehouses, setWarehouses] = useState([]);

  useEffect(() => {
    api.get('/inventory/warehouses?size=100').then(r => setWarehouses(r.data.content));
    api.get('/inventory/alerts').then(r => setAlerts(r.data));
  }, []);

  useEffect(() => {
    const url = warehouseId
      ? `/inventory/stock?warehouseId=${warehouseId}`
      : '/inventory/stock';
    api.get(url).then(r => setStocks(r.data));
  }, [warehouseId]);

  const statusBadge = (status) => {
    const map = {
      OK: { color: '#52c41a', label: 'Đủ hàng' },
      LOW: { color: '#faad14', label: 'Sắp hết' },
      OUT_OF_STOCK: { color: '#ff4d4f', label: 'Hết hàng' }
    };
    const s = map[status];
    return <span className="badge" style={{ backgroundColor: s.color }}>{s.label}</span>;
  };

  return (
    <div>
      <h1>🏭 Tồn kho</h1>

      {alerts.length > 0 && (
        <div className="alert-box">
          ⚠️ <strong>{alerts.length} sản phẩm</strong> dưới mức tồn kho tối thiểu!
        </div>
      )}

      <select value={warehouseId} onChange={e => setWarehouseId(e.target.value)}>
        <option value="">Tất cả kho</option>
        {warehouses.map(w => <option key={w.id} value={w.id}>{w.name}</option>)}
      </select>

      <table className="data-table">
        <thead>
          <tr><th>Mã SP</th><th>Tên SP</th><th>Kho</th><th>Tồn</th><th>Min</th><th>TT</th></tr>
        </thead>
        <tbody>
          {stocks.map(s => (
            <tr key={`${s.warehouseId}-${s.productId}`}
                className={s.status !== 'OK' ? 'row-warning' : ''}>
              <td>{s.productCode}</td>
              <td>{s.productName}</td>
              <td>{s.warehouseName}</td>
              <td>{s.quantity.toLocaleString()}</td>
              <td>{s.minStock.toLocaleString()}</td>
              <td>{statusBadge(s.status)}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
```

**Giải thích:**
- Tồn kho = bảng `stock` JOIN products + warehouses
- Badge 3 trạng thái: OK, LOW, OUT_OF_STOCK
- Filter dropdown theo kho

---

## Bài 3: Chuyển kho

**StockTransferService.java:**
```java
@Transactional
public StockTransferDTO confirmTransfer(Long transferId) {
    StockTransferEntity transfer = transferRepo.findById(transferId)
        .orElseThrow(() -> new ResourceNotFoundException("Phiếu chuyển không tồn tại"));

    if (!"DRAFT".equals(transfer.getStatus())) {
        throw new BusinessException("Chỉ xác nhận phiếu ở trạng thái DRAFT");
    }

    if (transfer.getFromWarehouseId().equals(transfer.getToWarehouseId())) {
        throw new BusinessException("Kho nguồn và kho đích không được giống nhau");
    }

    for (StockTransferItemEntity item : transfer.getItems()) {
        // Validate tồn kho nguồn đủ
        int stock = getStock(transfer.getFromWarehouseId(), item.getProductId());
        if (stock < item.getQuantity()) {
            throw new BusinessException("Tồn kho không đủ để chuyển");
        }

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

**Giải thích:**
- Chuyển kho = 1 transaction: giảm kho A + tăng kho B (atomic)
- Validate: kho nguồn ≠ kho đích, tồn kho đủ

---

## Bài 4: Kiểm kê

**InventoryCheckService.java:**
```java
public InventoryCheckDTO createCheck(Long warehouseId) {
    InventoryCheckEntity check = InventoryCheckEntity.builder()
        .code(generateCode())
        .warehouseId(warehouseId)
        .checkDate(LocalDate.now())
        .status("DRAFT")
        .build();

    // Load tất cả SP có tồn tại kho này
    List<Stock> stocks = stockRepo.findByWarehouseId(warehouseId);
    for (Stock s : stocks) {
        check.getItems().add(InventoryCheckItemEntity.builder()
            .check(check)
            .productId(s.getProductId())
            .systemQuantity(s.getQuantity())
            .actualQuantity(s.getQuantity())  // Default = SL hệ thống
            .difference(0)
            .build());
    }

    return mapToDTO(checkRepo.save(check));
}

@Transactional
public void confirmCheck(Long checkId) {
    InventoryCheckEntity check = checkRepo.findById(checkId).orElseThrow();

    for (InventoryCheckItemEntity item : check.getItems()) {
        int diff = item.getActualQuantity() - item.getSystemQuantity();
        item.setDifference(diff);

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

**Frontend - InventoryCheckForm.jsx:**
```jsx
function InventoryCheckForm({ checkData, onConfirm }) {
  const [items, setItems] = useState(checkData.items.map(i => ({
    ...i, actualQuantity: i.systemQuantity
  })));

  const updateActual = (index, value) => {
    const newItems = [...items];
    newItems[index].actualQuantity = Number(value);
    newItems[index].difference = Number(value) - newItems[index].systemQuantity;
    setItems(newItems);
  };

  const summary = {
    total: items.length,
    matched: items.filter(i => i.difference === 0).length,
    diff: items.filter(i => i.difference !== 0).length
  };

  return (
    <div>
      <h2>📋 Kiểm kê kho</h2>
      <div className="summary">
        Tổng: {summary.total} SP | Đúng: {summary.matched} | Lệch: {summary.diff}
      </div>
      <table>
        <thead>
          <tr><th>Sản phẩm</th><th>SL hệ thống</th><th>SL thực đếm</th><th>Chênh lệch</th></tr>
        </thead>
        <tbody>
          {items.map((item, idx) => (
            <tr key={idx} className={
              item.difference > 0 ? 'row-positive' :
              item.difference < 0 ? 'row-negative' : ''
            }>
              <td>{item.productName}</td>
              <td>{item.systemQuantity}</td>
              <td>
                <input type="number" min="0"
                  value={item.actualQuantity}
                  onChange={e => updateActual(idx, e.target.value)} />
              </td>
              <td className={item.difference !== 0 ? 'bold' : ''}>
                {item.difference > 0 ? '+' : ''}{item.difference}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <button className="btn-primary" onClick={() => onConfirm(items)}>
        ✅ Xác nhận kiểm kê
      </button>
    </div>
  );
}
```

**Giải thích:**
- Kiểm kê pre-fill SL hệ thống, user nhập SL thực đếm
- Chênh lệch tự tính: actual - system
- Xác nhận → tự điều chỉnh tồn kho (tăng nếu +, giảm nếu -)

---

## Bài 5: Lịch sử nhập xuất

**Repository query - Stock Card:**
```java
@Query("""
    SELECT t FROM StockTransaction t
    WHERE t.warehouseId = :warehouseId
    AND t.productId = :productId
    AND t.createdAt BETWEEN :from AND :to
    ORDER BY t.createdAt
    """)
List<StockTransaction> getStockCard(
    @Param("warehouseId") Long warehouseId,
    @Param("productId") Long productId,
    @Param("from") LocalDateTime from,
    @Param("to") LocalDateTime to);
```

**Service - tính running balance:**
```java
public List<StockCardDTO> getStockCard(Long warehouseId, Long productId,
                                       LocalDate from, LocalDate to) {
    List<StockTransaction> txns = txnRepo.getStockCard(
        warehouseId, productId,
        from.atStartOfDay(), to.plusDays(1).atStartOfDay());

    int balance = 0;
    List<StockCardDTO> cards = new ArrayList<>();
    for (StockTransaction t : txns) {
        balance += t.getQuantity();
        cards.add(new StockCardDTO(
            t.getCreatedAt(),
            t.getType(),
            t.getReferenceType(),
            Math.max(t.getQuantity(), 0),   // qty_in
            Math.max(-t.getQuantity(), 0),  // qty_out
            balance,
            t.getNote()
        ));
    }
    return cards;
}
```

**Giải thích:**
- Thẻ kho = lịch sử giao dịch + running balance
- quantity > 0: nhập, quantity < 0: xuất
- Running balance = cumulative sum
