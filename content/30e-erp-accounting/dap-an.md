# Module Kế Toán - Đáp Án

## Bài 1: Hệ thống tài khoản

**AccountEntity.java:**
```java
@Entity
@Table(name = "accounts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AccountEntity {

    @Id
    @Column(length = 10)
    private String code;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, length = 20)
    private String type; // ASSET, LIABILITY, EQUITY, REVENUE, EXPENSE

    @Column(name = "parent_code", length = 10)
    private String parentCode;

    @Builder.Default
    private Integer level = 1;

    @Builder.Default
    private Boolean active = true;
}
```

**AccountService - Tree structure:**
```java
public List<AccountTreeDTO> getAccountTree() {
    List<AccountEntity> all = accountRepo.findAllByActiveTrue();
    Map<String, List<AccountEntity>> grouped = all.stream()
        .collect(Collectors.groupingBy(
            a -> a.getParentCode() != null ? a.getParentCode() : "ROOT"
        ));

    return buildTree(grouped, "ROOT");
}

private List<AccountTreeDTO> buildTree(Map<String, List<AccountEntity>> grouped, String parentCode) {
    return grouped.getOrDefault(parentCode, List.of()).stream()
        .map(a -> new AccountTreeDTO(
            a.getCode(), a.getName(), a.getType(),
            buildTree(grouped, a.getCode()) // recursive children
        ))
        .toList();
}
```

**Seed data (data.sql):**
```sql
INSERT INTO accounts (code, name, type, level) VALUES
('111', 'Tiền mặt', 'ASSET', 1),
('112', 'Tiền gửi ngân hàng', 'ASSET', 1),
('131', 'Phải thu khách hàng', 'ASSET', 1),
('156', 'Hàng hóa', 'ASSET', 1),
('211', 'Tài sản cố định', 'ASSET', 1),
('331', 'Phải trả nhà cung cấp', 'LIABILITY', 1),
('334', 'Phải trả người lao động', 'LIABILITY', 1),
('3331', 'Thuế GTGT phải nộp', 'LIABILITY', 2),
('411', 'Vốn đầu tư', 'EQUITY', 1),
('421', 'Lợi nhuận chưa phân phối', 'EQUITY', 1),
('511', 'Doanh thu bán hàng', 'REVENUE', 1),
('632', 'Giá vốn hàng bán', 'EXPENSE', 1),
('641', 'Chi phí bán hàng', 'EXPENSE', 1),
('642', 'Chi phí quản lý doanh nghiệp', 'EXPENSE', 1);
```

**Giải thích:**
- Tài khoản kế toán theo hệ thống VN (Thông tư 200)
- Tree structure: parent_code tạo quan hệ cha-con
- Seed data đảm bảo hệ thống có tài khoản cơ bản ngay từ đầu

---

## Bài 2: Sổ cái - Bút toán

**JournalEntryService.java:**
```java
@Service
@RequiredArgsConstructor
@Transactional
public class JournalEntryService {

    private final JournalEntryRepository entryRepo;

    public JournalEntryDTO create(CreateJournalEntryRequest request) {
        // Validate tổng Nợ = tổng Có
        BigDecimal totalDebit = request.getLines().stream()
            .map(CreateJournalEntryRequest.Line::getDebitAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalCredit = request.getLines().stream()
            .map(CreateJournalEntryRequest.Line::getCreditAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalDebit.compareTo(totalCredit) != 0) {
            throw new BusinessException("Tổng Nợ (" + totalDebit
                + ") phải bằng Tổng Có (" + totalCredit + ")");
        }

        JournalEntryEntity entry = JournalEntryEntity.builder()
            .code(generateCode())
            .entryDate(request.getEntryDate())
            .description(request.getDescription())
            .status("DRAFT")
            .build();

        for (var line : request.getLines()) {
            entry.getLines().add(JournalEntryLineEntity.builder()
                .entry(entry)
                .accountCode(line.getAccountCode())
                .description(line.getDescription())
                .debitAmount(line.getDebitAmount())
                .creditAmount(line.getCreditAmount())
                .build());
        }

        return mapToDTO(entryRepo.save(entry));
    }

    public JournalEntryDTO post(Long entryId) {
        JournalEntryEntity entry = entryRepo.findById(entryId).orElseThrow();
        if (!"DRAFT".equals(entry.getStatus())) {
            throw new BusinessException("Chỉ post bút toán DRAFT");
        }
        entry.setStatus("POSTED");
        return mapToDTO(entryRepo.save(entry));
    }
}
```

**Frontend - JournalEntryForm.jsx:**
```jsx
function JournalEntryForm({ onSave }) {
  const [entry, setEntry] = useState({
    entryDate: new Date().toISOString().slice(0, 10),
    description: '',
    lines: [
      { accountCode: '', description: '', debitAmount: 0, creditAmount: 0 },
      { accountCode: '', description: '', debitAmount: 0, creditAmount: 0 }
    ]
  });

  const totalDebit = entry.lines.reduce((s, l) => s + Number(l.debitAmount), 0);
  const totalCredit = entry.lines.reduce((s, l) => s + Number(l.creditAmount), 0);
  const isBalanced = totalDebit === totalCredit && totalDebit > 0;

  const addLine = () => {
    setEntry({
      ...entry,
      lines: [...entry.lines, { accountCode: '', description: '', debitAmount: 0, creditAmount: 0 }]
    });
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Tạo bút toán</h2>
      <input type="date" value={entry.entryDate} onChange={...} />
      <input placeholder="Diễn giải" value={entry.description} onChange={...} />

      <table>
        <thead>
          <tr><th>Tài khoản</th><th>Diễn giải</th><th>Nợ (Debit)</th><th>Có (Credit)</th></tr>
        </thead>
        <tbody>
          {entry.lines.map((line, idx) => (
            <tr key={idx}>
              <td><select value={line.accountCode} onChange={...}>...</select></td>
              <td><input value={line.description} onChange={...} /></td>
              <td><input type="number" min="0" value={line.debitAmount} onChange={...} /></td>
              <td><input type="number" min="0" value={line.creditAmount} onChange={...} /></td>
            </tr>
          ))}
          <tr className="total-row">
            <td colSpan="2"><strong>Tổng cộng:</strong></td>
            <td><strong>{totalDebit.toLocaleString('vi-VN')}₫</strong></td>
            <td><strong>{totalCredit.toLocaleString('vi-VN')}₫</strong></td>
          </tr>
        </tbody>
      </table>

      {!isBalanced && (
        <div className="error">
          ⚠️ Chênh lệch: {Math.abs(totalDebit - totalCredit).toLocaleString('vi-VN')}₫
        </div>
      )}

      <button type="button" onClick={addLine}>+ Thêm dòng</button>
      <button type="submit" disabled={!isBalanced}>💾 Lưu bút toán</button>
    </form>
  );
}
```

**Giải thích:**
- Validate server-side: tổng Debit = tổng Credit
- Frontend validate realtime, disable nút Lưu khi không cân
- POSTED không thể sửa → đảm bảo tính toàn vẹn sổ cái

---

## Bài 3: Phiếu thu / Phiếu chi

**ReceiptVoucherService.java:**
```java
public ReceiptVoucherDTO create(CreateReceiptRequest request) {
    ReceiptVoucherEntity voucher = ReceiptVoucherEntity.builder()
        .code(generateCode("PT"))
        .voucherDate(request.getDate())
        .customerId(request.getCustomerId())
        .amount(request.getAmount())
        .paymentMethod(request.getPaymentMethod())
        .description(request.getDescription())
        .invoiceId(request.getInvoiceId())
        .build();

    receiptRepo.save(voucher);

    // Tự động tạo bút toán: Nợ TK Tiền, Có TK Phải thu
    String debitAccount = "CASH".equals(request.getPaymentMethod()) ? "111" : "112";
    accountingService.createAutoEntry(
        voucher.getVoucherDate(),
        "Thu tiền KH: " + voucher.getCode(),
        debitAccount, voucher.getAmount(),   // Nợ: Tiền
        "131", voucher.getAmount()           // Có: Phải thu
    );

    return mapToDTO(voucher);
}
```

**Giải thích:**
- Phiếu thu → Nợ Tiền (111/112), Có Phải thu KH (131)
- Phiếu chi → Nợ Chi phí/NCC (6xx/331), Có Tiền (111/112)
- Bút toán tự tạo đảm bảo sổ cái luôn cập nhật

---

## Bài 4: Báo cáo Lãi lỗ

**IncomeStatementService.java:**
```java
public IncomeStatementDTO getIncomeStatement(LocalDate from, LocalDate to) {
    BigDecimal revenue = getAccountBalance("511", from, to, "credit");
    BigDecimal cogs = getAccountBalance("632", from, to, "debit");
    BigDecimal salesExpense = getAccountBalance("641", from, to, "debit");
    BigDecimal adminExpense = getAccountBalance("642", from, to, "debit");

    BigDecimal grossProfit = revenue.subtract(cogs);
    BigDecimal totalExpenses = salesExpense.add(adminExpense);
    BigDecimal netProfit = grossProfit.subtract(totalExpenses);

    return IncomeStatementDTO.builder()
        .revenue(revenue)
        .cogs(cogs)
        .grossProfit(grossProfit)
        .salesExpense(salesExpense)
        .adminExpense(adminExpense)
        .totalExpenses(totalExpenses)
        .netProfit(netProfit)
        .build();
}

private BigDecimal getAccountBalance(String accountPrefix, LocalDate from,
                                     LocalDate to, String side) {
    if ("debit".equals(side)) {
        return entryLineRepo.sumDebitByAccountAndPeriod(accountPrefix + "%", from, to);
    }
    return entryLineRepo.sumCreditByAccountAndPeriod(accountPrefix + "%", from, to);
}
```

**Frontend - IncomeStatement.jsx:**
```jsx
function IncomeStatement() {
  const [report, setReport] = useState(null);
  const [period, setPeriod] = useState({ from: '2024-06-01', to: '2024-06-30' });

  const load = async () => {
    const res = await api.get(`/accounting/reports/income-statement?from=${period.from}&to=${period.to}`);
    setReport(res.data);
  };

  return (
    <div className="income-statement">
      <h1>📊 Báo cáo Lãi lỗ</h1>
      <div className="period-selector">
        <input type="date" value={period.from} onChange={...} />
        <input type="date" value={period.to} onChange={...} />
        <button onClick={load}>Xem báo cáo</button>
      </div>

      {report && (
        <table className="report-table">
          <tbody>
            <tr><td>Doanh thu bán hàng (511)</td>
                <td className="amount">{report.revenue.toLocaleString('vi-VN')}₫</td></tr>
            <tr><td>Giá vốn hàng bán (632)</td>
                <td className="amount negative">({report.cogs.toLocaleString('vi-VN')}₫)</td></tr>
            <tr className="subtotal"><td><strong>Lợi nhuận gộp</strong></td>
                <td className="amount"><strong>{report.grossProfit.toLocaleString('vi-VN')}₫</strong></td></tr>
            <tr><td>Chi phí bán hàng (641)</td>
                <td className="amount negative">({report.salesExpense.toLocaleString('vi-VN')}₫)</td></tr>
            <tr><td>Chi phí quản lý (642)</td>
                <td className="amount negative">({report.adminExpense.toLocaleString('vi-VN')}₫)</td></tr>
            <tr className="total"><td><strong>Lợi nhuận thuần</strong></td>
                <td className={`amount ${report.netProfit >= 0 ? 'positive' : 'negative'}`}>
                  <strong>{report.netProfit.toLocaleString('vi-VN')}₫</strong>
                </td></tr>
          </tbody>
        </table>
      )}
    </div>
  );
}
```

**Giải thích:**
- Doanh thu = credit side của TK 511
- Chi phí = debit side của TK 6xx
- Lợi nhuận thuần = Doanh thu - Giá vốn - Chi phí

---

## Bài 5: Dashboard Tài chính

```jsx
function AccountingDashboard() {
  const [stats, setStats] = useState({});
  const [monthlyData, setMonthlyData] = useState([]);

  useEffect(() => {
    api.get('/accounting/reports/dashboard').then(r => setStats(r.data));
    api.get('/accounting/reports/monthly-trend').then(r => setMonthlyData(r.data));
  }, []);

  const chartData = {
    labels: monthlyData.map(d => d.month),
    datasets: [
      { label: 'Doanh thu', data: monthlyData.map(d => d.revenue), borderColor: '#52c41a' },
      { label: 'Chi phí', data: monthlyData.map(d => d.expense), borderColor: '#ff4d4f' }
    ]
  };

  return (
    <div>
      <h1>📒 Tổng quan Tài chính</h1>
      <div className="stats-grid">
        <div className="stat-card" style={{borderColor:'#52c41a'}}>
          <h4>Doanh thu tháng</h4>
          <p>{stats.monthlyRevenue?.toLocaleString('vi-VN')}₫</p>
        </div>
        <div className="stat-card" style={{borderColor:'#ff4d4f'}}>
          <h4>Chi phí tháng</h4>
          <p>{stats.monthlyExpense?.toLocaleString('vi-VN')}₫</p>
        </div>
        <div className="stat-card" style={{borderColor:'#1890ff'}}>
          <h4>Lợi nhuận</h4>
          <p>{stats.monthlyProfit?.toLocaleString('vi-VN')}₫</p>
        </div>
        <div className="stat-card" style={{borderColor:'#faad14'}}>
          <h4>Dòng tiền ròng</h4>
          <p>{stats.netCashFlow?.toLocaleString('vi-VN')}₫</p>
        </div>
      </div>
      <div className="chart-container">
        <h3>Xu hướng Doanh thu - Chi phí</h3>
        <Line data={chartData} />
      </div>
    </div>
  );
}
```

**Giải thích:**
- Dashboard tổng hợp từ sổ cái (journal entries)
- Line chart hiển thị trend doanh thu vs chi phí
- Dòng tiền ròng = tổng phiếu thu - tổng phiếu chi
