# Module Sản Xuất - Đáp Án

## Bài 1: CRUD BOM

**BomEntity.java:**
```java
@Entity
@Table(name = "boms")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BomEntity extends BaseEntity {

    @Column(unique = true, nullable = false, length = 20)
    private String code;

    @Column(length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;  // Thành phẩm

    @Builder.Default
    private Integer version = 1;

    @Builder.Default
    private Boolean active = true;

    @OneToMany(mappedBy = "bom", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<BomItemEntity> items = new ArrayList<>();
}
```

**BomItemEntity.java:**
```java
@Entity
@Table(name = "bom_items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BomItemEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bom_id")
    private BomEntity bom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private ProductEntity material;  // Nguyên vật liệu

    @Column(nullable = false)
    private Integer quantity;

    @Column(length = 20)
    private String unit;
}
```

**BomService.java:**
```java
@Service
@RequiredArgsConstructor
@Transactional
public class BomService {

    private final BomRepository bomRepo;
    private final ProductRepository productRepo;

    public BomDTO create(CreateBomRequest request) {
        ProductEntity product = productRepo.findById(request.getProductId())
            .orElseThrow(() -> new ResourceNotFoundException("Thành phẩm không tồn tại"));

        if (request.getItems().isEmpty()) {
            throw new BusinessException("BOM phải có ít nhất 1 NVL");
        }

        BomEntity bom = BomEntity.builder()
            .code(generateCode())
            .name(request.getName())
            .product(product)
            .build();

        BigDecimal unitCost = BigDecimal.ZERO;
        for (BomItemRequest itemReq : request.getItems()) {
            if (itemReq.getMaterialId().equals(request.getProductId())) {
                throw new BusinessException("NVL không được trùng với thành phẩm");
            }
            ProductEntity material = productRepo.findById(itemReq.getMaterialId())
                .orElseThrow(() -> new ResourceNotFoundException("NVL không tồn tại"));

            bom.getItems().add(BomItemEntity.builder()
                .bom(bom)
                .material(material)
                .quantity(itemReq.getQuantity())
                .unit(itemReq.getUnit())
                .build());

            unitCost = unitCost.add(
                material.getCostPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity())));
        }

        return mapToDTO(bomRepo.save(bom), unitCost);
    }
}
```

**Giải thích:**
- BOM = 1 thành phẩm + N nguyên vật liệu (quan hệ 1:N)
- Validate: NVL ≠ thành phẩm (tránh recursive BOM)
- Giá vốn SX = sum(material.costPrice × quantity)

---

## Bài 2: Kiểm tra NVL khả dụng

```java
public MaterialAvailabilityDTO checkMaterials(Long bomId, int quantity) {
    BomEntity bom = bomRepo.findById(bomId)
        .orElseThrow(() -> new ResourceNotFoundException("BOM không tồn tại"));

    List<MaterialStatusDTO> statuses = new ArrayList<>();
    boolean allAvailable = true;

    for (BomItemEntity item : bom.getItems()) {
        int required = item.getQuantity() * quantity;
        int available = inventoryService.getTotalStock(item.getMaterial().getId());
        int shortage = Math.max(0, required - available);

        if (shortage > 0) allAvailable = false;

        statuses.add(new MaterialStatusDTO(
            item.getMaterial().getId(),
            item.getMaterial().getName(),
            required,
            available,
            shortage
        ));
    }

    return new MaterialAvailabilityDTO(allAvailable, statuses);
}
```

**Frontend - MaterialCheck.jsx:**
```jsx
function MaterialCheck({ bomId }) {
  const [quantity, setQuantity] = useState(100);
  const [result, setResult] = useState(null);

  const check = async () => {
    const res = await api.post(`/manufacturing/boms/${bomId}/check-materials`,
      { quantity });
    setResult(res.data);
  };

  return (
    <div className="material-check">
      <div className="check-input">
        <label>Số lượng cần SX:</label>
        <input type="number" min="1" value={quantity}
          onChange={e => setQuantity(Number(e.target.value))} />
        <button onClick={check}>🔍 Kiểm tra NVL</button>
      </div>

      {result && (
        <>
          <div className={`check-status ${result.allAvailable ? 'success' : 'warning'}`}>
            {result.allAvailable ? '✅ Đủ NVL để sản xuất' : '⚠️ Thiếu NVL'}
          </div>
          <table className="data-table">
            <thead>
              <tr><th>Nguyên vật liệu</th><th>Cần</th><th>Tồn kho</th><th>Thiếu</th></tr>
            </thead>
            <tbody>
              {result.statuses.map(s => (
                <tr key={s.materialId} className={s.shortage > 0 ? 'row-danger' : ''}>
                  <td>{s.materialName}</td>
                  <td>{s.required.toLocaleString()}</td>
                  <td>{s.available.toLocaleString()}</td>
                  <td>
                    {s.shortage > 0
                      ? <span className="text-danger">{s.shortage.toLocaleString()}</span>
                      : '-'}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </>
      )}
    </div>
  );
}
```

**Giải thích:**
- Kiểm tra tồn kho cho mỗi NVL: required = bomQty × planQty
- So sánh với tồn kho tổng (tất cả kho)
- Highlight dòng thiếu hụt

---

## Bài 3: Lệnh sản xuất

**WorkOrderService.java:**
```java
@Service
@RequiredArgsConstructor
@Transactional
public class WorkOrderService {

    private final WorkOrderRepository woRepo;
    private final BomRepository bomRepo;
    private final InventoryService inventoryService;

    public WorkOrderDTO create(CreateWorkOrderRequest request) {
        BomEntity bom = bomRepo.findById(request.getBomId()).orElseThrow();

        WorkOrderEntity wo = WorkOrderEntity.builder()
            .code(generateCode())
            .bom(bom)
            .product(bom.getProduct())
            .warehouseId(request.getWarehouseId())
            .plannedQuantity(request.getQuantity())
            .plannedStart(request.getPlannedStart())
            .plannedEnd(request.getPlannedEnd())
            .status("DRAFT")
            .build();

        return mapToDTO(woRepo.save(wo));
    }

    public WorkOrderDTO startProduction(Long woId) {
        WorkOrderEntity wo = woRepo.findById(woId).orElseThrow();

        if (!"CONFIRMED".equals(wo.getStatus())) {
            throw new BusinessException("WO phải ở trạng thái CONFIRMED");
        }

        // Xuất NVL từ kho
        for (BomItemEntity material : wo.getBom().getItems()) {
            int totalQty = material.getQuantity() * wo.getPlannedQuantity();
            inventoryService.decreaseStock(
                wo.getWarehouseId(), material.getMaterial().getId(), totalQty);
        }

        wo.setStatus("IN_PROGRESS");
        wo.setActualStart(LocalDate.now());
        return mapToDTO(woRepo.save(wo));
    }

    public WorkOrderDTO completeProduction(Long woId, int actualQuantity) {
        WorkOrderEntity wo = woRepo.findById(woId).orElseThrow();

        if (!"IN_PROGRESS".equals(wo.getStatus())) {
            throw new BusinessException("WO phải đang sản xuất");
        }

        // Nhập thành phẩm vào kho
        inventoryService.increaseStock(
            wo.getWarehouseId(), wo.getProduct().getId(), actualQuantity);

        wo.setActualQuantity(actualQuantity);
        wo.setStatus("COMPLETED");
        wo.setCompletedDate(LocalDate.now());
        return mapToDTO(woRepo.save(wo));
    }
}
```

**Giải thích:**
- Start → xuất NVL (giảm tồn kho NVL)
- Complete → nhập thành phẩm (tăng tồn kho TP)
- actualQuantity có thể < plannedQuantity (do phế phẩm)

---

## Bài 4: Dashboard Sản xuất

```jsx
function ProductionDashboard() {
  const [stats, setStats] = useState({});
  const [activeOrders, setActiveOrders] = useState([]);

  useEffect(() => {
    api.get('/manufacturing/work-orders/stats').then(r => setStats(r.data));
    api.get('/manufacturing/work-orders?status=IN_PROGRESS').then(r =>
      setActiveOrders(r.data.content));
  }, []);

  return (
    <div>
      <h1>⚙️ Tổng quan Sản xuất</h1>
      <div className="stats-grid">
        <div className="stat-card">
          <h4>Đang SX</h4>
          <p>{stats.inProgressCount || 0}</p>
        </div>
        <div className="stat-card">
          <h4>Hoàn thành tháng này</h4>
          <p>{stats.completedThisMonth || 0}</p>
        </div>
        <div className="stat-card">
          <h4>SL sản xuất</h4>
          <p>{(stats.totalProduced || 0).toLocaleString()}</p>
        </div>
        <div className="stat-card">
          <h4>Hiệu suất TB</h4>
          <p>{stats.avgEfficiency || 0}%</p>
        </div>
      </div>

      <h2>🔄 Đang sản xuất</h2>
      <table className="data-table">
        <thead>
          <tr><th>Mã</th><th>Thành phẩm</th><th>SL</th><th>Bắt đầu</th><th>Tiến độ</th></tr>
        </thead>
        <tbody>
          {activeOrders.map(wo => (
            <tr key={wo.id}>
              <td>{wo.code}</td>
              <td>{wo.productName}</td>
              <td>{wo.plannedQuantity.toLocaleString()}</td>
              <td>{wo.actualStart}</td>
              <td>
                <div className="progress-bar">
                  <div className="progress-fill"
                    style={{ width: `${(wo.actualQuantity / wo.plannedQuantity) * 100}%` }}>
                  </div>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
```

**Giải thích:**
- Hiệu suất = actual / planned × 100%
- Progress bar hiển thị trực quan tiến độ SX

---

## Bài 5: Tính giá thành sản phẩm

```java
public ProductCostDTO calculateCost(Long bomId, int quantity) {
    BomEntity bom = bomRepo.findById(bomId).orElseThrow();

    BigDecimal unitMaterialCost = BigDecimal.ZERO;
    List<CostDetailDTO> details = new ArrayList<>();

    for (BomItemEntity item : bom.getItems()) {
        BigDecimal itemCost = item.getMaterial().getCostPrice()
            .multiply(BigDecimal.valueOf(item.getQuantity()));
        unitMaterialCost = unitMaterialCost.add(itemCost);

        details.add(new CostDetailDTO(
            item.getMaterial().getName(),
            item.getQuantity(),
            item.getMaterial().getCostPrice(),
            itemCost
        ));
    }

    BigDecimal totalCost = unitMaterialCost.multiply(BigDecimal.valueOf(quantity));
    BigDecimal sellingPrice = bom.getProduct().getPrice();
    BigDecimal profitPerUnit = sellingPrice.subtract(unitMaterialCost);
    BigDecimal profitMargin = sellingPrice.compareTo(BigDecimal.ZERO) > 0
        ? profitPerUnit.divide(sellingPrice, 4, RoundingMode.HALF_UP)
            .multiply(BigDecimal.valueOf(100))
        : BigDecimal.ZERO;

    return new ProductCostDTO(
        bom.getProduct().getName(), quantity,
        unitMaterialCost, totalCost,
        sellingPrice, profitPerUnit, profitMargin,
        details
    );
}
```

**Giải thích:**
- Giá vốn NVL = sum(costPrice × bomQuantity) cho 1 đơn vị TP
- Lợi nhuận = sellingPrice - unitMaterialCost
- Biên lợi nhuận = profit / sellingPrice × 100%
