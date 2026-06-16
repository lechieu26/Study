# Module Nhân Sự - Đáp Án

## Bài 1: Quản lý Phòng ban & Nhân viên

**EmployeeEntity.java:**
```java
@Entity
@Table(name = "employees")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EmployeeEntity extends BaseEntity {

    @Column(unique = true, nullable = false, length = 20)
    private String code;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String gender;

    @Column(name = "id_number", length = 20)
    private String idNumber;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

    @Column(length = 50)
    private String position;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "contract_type", length = 20)
    private String contractType;

    @Column(name = "base_salary", precision = 15, scale = 2)
    private BigDecimal baseSalary;

    @Column(precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal allowance = BigDecimal.ZERO;

    @Column(name = "bank_account", length = 30)
    private String bankAccount;

    @Column(name = "bank_name", length = 50)
    private String bankName;

    @Builder.Default
    private Integer dependents = 0;

    @Builder.Default
    private String status = "ACTIVE";
}
```

**EmployeeService.java:**
```java
@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

    private final EmployeeRepository repo;
    private final DepartmentRepository deptRepo;

    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findAll(String search, Long departmentId, Pageable pageable) {
        if (departmentId != null) {
            return repo.findByDepartmentIdAndFullNameContaining(departmentId, search, pageable)
                       .map(this::toDTO);
        }
        return repo.findByFullNameContainingOrCodeContaining(search, search, pageable)
                   .map(this::toDTO);
    }

    public EmployeeDTO create(CreateEmployeeRequest req) {
        String code = generateCode();

        DepartmentEntity dept = deptRepo.findById(req.getDepartmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Phòng ban không tồn tại"));

        EmployeeEntity emp = EmployeeEntity.builder()
            .code(code)
            .fullName(req.getFullName())
            .dateOfBirth(req.getDateOfBirth())
            .gender(req.getGender())
            .idNumber(req.getIdNumber())
            .phone(req.getPhone())
            .email(req.getEmail())
            .address(req.getAddress())
            .department(dept)
            .position(req.getPosition())
            .hireDate(req.getHireDate())
            .contractType(req.getContractType())
            .baseSalary(req.getBaseSalary())
            .allowance(req.getAllowance())
            .bankAccount(req.getBankAccount())
            .bankName(req.getBankName())
            .dependents(req.getDependents())
            .build();

        return toDTO(repo.save(emp));
    }

    private String generateCode() {
        long count = repo.count();
        return String.format("NV-%03d", count + 1);
    }
}
```

**Giải thích:**
- Employee có quan hệ ManyToOne với Department
- Mã NV tự sinh: NV-001, NV-002...
- Filter theo phòng ban + search tên/mã

---

## Bài 2: Hệ thống Chấm công

**AttendanceService.java:**
```java
@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceService {

    private final AttendanceRepository repo;

    public AttendanceDTO checkIn(Long employeeId) {
        LocalDate today = LocalDate.now();

        if (repo.existsByEmployeeIdAndDate(employeeId, today)) {
            throw new BusinessException("Đã check-in hôm nay rồi");
        }

        AttendanceEntity att = AttendanceEntity.builder()
            .employeeId(employeeId)
            .date(today)
            .checkIn(LocalTime.now())
            .status("PRESENT")
            .build();

        return toDTO(repo.save(att));
    }

    public AttendanceDTO checkOut(Long employeeId) {
        LocalDate today = LocalDate.now();

        AttendanceEntity att = repo.findByEmployeeIdAndDate(employeeId, today)
            .orElseThrow(() -> new BusinessException("Chưa check-in hôm nay"));

        if (att.getCheckOut() != null) {
            throw new BusinessException("Đã check-out rồi");
        }

        att.setCheckOut(LocalTime.now());

        long minutes = Duration.between(att.getCheckIn(), att.getCheckOut()).toMinutes();
        double hours = minutes / 60.0;
        att.setWorkHours(BigDecimal.valueOf(hours).setScale(2, RoundingMode.HALF_UP));

        if (hours > 8) {
            att.setOtHours(BigDecimal.valueOf(hours - 8).setScale(2, RoundingMode.HALF_UP));
        }

        return toDTO(repo.save(att));
    }

    @Transactional(readOnly = true)
    public AttendanceSummaryDTO getMonthlySummary(Long employeeId, int month, int year) {
        List<AttendanceEntity> records = repo.findByEmployeeIdAndMonthAndYear(
            employeeId, month, year);

        int workDays = (int) records.stream()
            .filter(r -> "PRESENT".equals(r.getStatus())).count();
        int leaveDays = (int) records.stream()
            .filter(r -> r.getStatus().startsWith("LEAVE")).count();
        double totalOt = records.stream()
            .filter(r -> r.getOtHours() != null)
            .mapToDouble(r -> r.getOtHours().doubleValue())
            .sum();

        return new AttendanceSummaryDTO(employeeId, month, year,
            workDays, leaveDays, totalOt);
    }
}
```

**Frontend - AttendanceBoard.jsx:**
```jsx
function AttendanceBoard() {
  const [month, setMonth] = useState(new Date().getMonth() + 1);
  const [year, setYear] = useState(new Date().getFullYear());
  const [employeeId, setEmployeeId] = useState('');
  const [records, setRecords] = useState([]);
  const [summary, setSummary] = useState(null);

  const loadData = async () => {
    if (!employeeId) return;
    const [rec, sum] = await Promise.all([
      api.get(`/hr/attendance/monthly/${employeeId}?month=${month}&year=${year}`),
      api.get(`/hr/attendance/summary/${employeeId}?month=${month}&year=${year}`)
    ]);
    setRecords(rec.data);
    setSummary(sum.data);
  };

  useEffect(() => { loadData(); }, [employeeId, month, year]);

  const statusIcon = (status) => {
    const map = { PRESENT: '✓', LEAVE_PAID: 'P', LEAVE_UNPAID: 'U', SICK: 'S' };
    return map[status] || '-';
  };

  return (
    <div>
      <h1>📅 Bảng chấm công</h1>
      <div className="filters">
        <select value={employeeId} onChange={e => setEmployeeId(e.target.value)}>
          <option value="">-- Chọn NV --</option>
          {/* employees */}
        </select>
        <select value={month} onChange={e => setMonth(Number(e.target.value))}>
          {Array.from({length: 12}, (_, i) => (
            <option key={i+1} value={i+1}>Tháng {i+1}</option>
          ))}
        </select>
      </div>

      {summary && (
        <div className="summary-cards">
          <div className="card">Ngày công: {summary.workDays}</div>
          <div className="card">Nghỉ phép: {summary.leaveDays}</div>
          <div className="card">OT: {summary.totalOt}h</div>
        </div>
      )}

      <table className="attendance-table">
        <thead><tr><th>Ngày</th><th>Vào</th><th>Ra</th><th>Giờ</th><th>OT</th><th>TT</th></tr></thead>
        <tbody>
          {records.map(r => (
            <tr key={r.date}>
              <td>{r.date}</td>
              <td>{r.checkIn}</td>
              <td>{r.checkOut}</td>
              <td>{r.workHours}h</td>
              <td>{r.otHours > 0 ? r.otHours + 'h' : '-'}</td>
              <td>{statusIcon(r.status)}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
```

**Giải thích:**
- Check-in validate: không cho check-in 2 lần/ngày
- workHours tự tính từ checkIn → checkOut
- OT = workHours - 8 (nếu > 8)

---

## Bài 3: Tính lương

**PayrollService.java:**
```java
@Service
@RequiredArgsConstructor
@Transactional
public class PayrollService {

    private static final int STANDARD_DAYS = 22;
    private static final BigDecimal BHXH_RATE = new BigDecimal("0.08");
    private static final BigDecimal BHYT_RATE = new BigDecimal("0.015");
    private static final BigDecimal BHTN_RATE = new BigDecimal("0.01");
    private static final BigDecimal OT_MULTIPLIER = new BigDecimal("1.5");
    private static final BigDecimal PERSONAL_DEDUCTION = new BigDecimal("11000000");
    private static final BigDecimal DEPENDENT_DEDUCTION = new BigDecimal("4400000");

    public PayslipDTO calculate(Long employeeId, int month, int year) {
        EmployeeEntity emp = employeeRepo.findById(employeeId).orElseThrow();
        AttendanceSummaryDTO summary = attendanceService.getMonthlySummary(employeeId, month, year);

        BigDecimal baseSalary = emp.getBaseSalary();
        BigDecimal dailyRate = baseSalary.divide(BigDecimal.valueOf(STANDARD_DAYS), 2, RoundingMode.HALF_UP);
        BigDecimal hourlyRate = dailyRate.divide(BigDecimal.valueOf(8), 2, RoundingMode.HALF_UP);

        BigDecimal actualSalary = dailyRate.multiply(BigDecimal.valueOf(summary.getWorkDays()));
        BigDecimal otPay = hourlyRate.multiply(OT_MULTIPLIER)
            .multiply(BigDecimal.valueOf(summary.getTotalOt()));
        BigDecimal grossPay = actualSalary.add(emp.getAllowance()).add(otPay);

        BigDecimal bhxh = baseSalary.multiply(BHXH_RATE);
        BigDecimal bhyt = baseSalary.multiply(BHYT_RATE);
        BigDecimal bhtn = baseSalary.multiply(BHTN_RATE);
        BigDecimal totalInsurance = bhxh.add(bhyt).add(bhtn);

        BigDecimal tax = calculateTax(grossPay, totalInsurance, emp.getDependents());
        BigDecimal totalDeduction = totalInsurance.add(tax);
        BigDecimal netPay = grossPay.subtract(totalDeduction);

        PayslipEntity payslip = PayslipEntity.builder()
            .employeeId(employeeId).month(month).year(year)
            .workDays(summary.getWorkDays())
            .otHours(BigDecimal.valueOf(summary.getTotalOt()))
            .baseSalary(baseSalary).actualSalary(actualSalary)
            .allowance(emp.getAllowance()).otPay(otPay).grossPay(grossPay)
            .socialInsurance(bhxh).healthInsurance(bhyt)
            .unemploymentInsurance(bhtn).tax(tax)
            .totalDeduction(totalDeduction).netPay(netPay)
            .status("DRAFT")
            .build();

        return toDTO(payslipRepo.save(payslip));
    }

    private BigDecimal calculateTax(BigDecimal grossPay, BigDecimal insurance, int dependents) {
        BigDecimal taxableIncome = grossPay.subtract(insurance)
            .subtract(PERSONAL_DEDUCTION)
            .subtract(DEPENDENT_DEDUCTION.multiply(BigDecimal.valueOf(dependents)));

        if (taxableIncome.compareTo(BigDecimal.ZERO) <= 0) return BigDecimal.ZERO;

        // Biểu thuế lũy tiến đơn giản
        BigDecimal tax = BigDecimal.ZERO;
        BigDecimal remaining = taxableIncome;
        BigDecimal[][] brackets = {
            {new BigDecimal("5000000"), new BigDecimal("0.05")},
            {new BigDecimal("5000000"), new BigDecimal("0.10")},
            {new BigDecimal("8000000"), new BigDecimal("0.15")},
            {new BigDecimal("14000000"), new BigDecimal("0.20")},
            {new BigDecimal("20000000"), new BigDecimal("0.25")}
        };

        for (BigDecimal[] bracket : brackets) {
            if (remaining.compareTo(BigDecimal.ZERO) <= 0) break;
            BigDecimal taxable = remaining.min(bracket[0]);
            tax = tax.add(taxable.multiply(bracket[1]));
            remaining = remaining.subtract(bracket[0]);
        }

        return tax.setScale(0, RoundingMode.HALF_UP);
    }
}
```

**Giải thích:**
- Lương thực tế dựa trên ngày công thực
- OT tính 150% lương giờ
- Bảo hiểm tính trên lương cơ bản (không tính OT, phụ cấp)
- Thuế TNCN theo biểu lũy tiến sau khi trừ giảm trừ

---

## Bài 4: Đánh giá KPI

**KpiService.java:**
```java
@Service
@RequiredArgsConstructor
public class KpiService {

    public List<KpiEvaluationDTO> getByEmployee(Long employeeId, String period) {
        return kpiRepo.findByEmployeeIdAndPeriod(employeeId, period)
            .stream().map(this::toDTO).toList();
    }

    public KpiEvaluationDTO createOrUpdate(CreateKpiRequest request) {
        KpiEvaluationEntity kpi = KpiEvaluationEntity.builder()
            .employeeId(request.getEmployeeId())
            .period(request.getPeriod())
            .kpiName(request.getKpiName())
            .targetValue(request.getTargetValue())
            .actualValue(request.getActualValue())
            .build();

        // Tính % đạt
        try {
            double target = Double.parseDouble(request.getTargetValue().replaceAll("[^\\d.]", ""));
            double actual = Double.parseDouble(request.getActualValue().replaceAll("[^\\d.]", ""));
            kpi.setAchievementRate(BigDecimal.valueOf(actual / target * 100)
                .setScale(1, RoundingMode.HALF_UP));
        } catch (NumberFormatException e) {
            kpi.setAchievementRate(null);
        }

        return toDTO(kpiRepo.save(kpi));
    }

    public BigDecimal getAverageScore(Long employeeId, String period) {
        return kpiRepo.getAverageScore(employeeId, period);
    }
}
```

**Giải thích:**
- KPI linh hoạt: target/actual là String (hỗ trợ cả số và text)
- Achievement rate tự tính khi giá trị là số
- Score 1-5 do manager đánh giá

---

## Bài 5: Dashboard Nhân sự

```jsx
function HRDashboard() {
  const [stats, setStats] = useState({});
  const [todayAttendance, setTodayAttendance] = useState([]);

  useEffect(() => {
    api.get('/hr/dashboard/stats').then(r => setStats(r.data));
    api.get('/hr/attendance/today').then(r => setTodayAttendance(r.data));
  }, []);

  const notCheckedIn = todayAttendance.filter(a => !a.checkedIn);

  return (
    <div>
      <h1>👥 Tổng quan Nhân sự</h1>
      <div className="stats-grid">
        <div className="stat-card">
          <h4>Tổng nhân viên</h4>
          <p>{stats.totalEmployees}</p>
        </div>
        <div className="stat-card positive">
          <h4>NV mới tháng này</h4>
          <p>{stats.newThisMonth}</p>
        </div>
        <div className="stat-card">
          <h4>Đã check-in hôm nay</h4>
          <p>{stats.checkedInToday} / {stats.totalEmployees}</p>
        </div>
      </div>

      {notCheckedIn.length > 0 && (
        <div className="alert-box">
          ⚠️ {notCheckedIn.length} NV chưa check-in hôm nay:
          <ul>
            {notCheckedIn.map(e => <li key={e.id}>{e.code} - {e.fullName}</li>)}
          </ul>
        </div>
      )}
    </div>
  );
}
```

**Giải thích:**
- Dashboard hiển thị tổng quan NV + chấm công hôm nay
- Cảnh báo NV chưa check-in giúp quản lý theo dõi
