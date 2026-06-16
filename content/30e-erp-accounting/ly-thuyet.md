# Module Kế Toán - Tài Chính (Accounting)

## Mục lục

1. [Tổng quan kế toán trong ERP](#1-tổng-quan-kế-toán-trong-erp)
2. [Hệ thống tài khoản kế toán](#2-hệ-thống-tài-khoản-kế-toán)
3. [Sổ cái (General Ledger)](#3-sổ-cái-general-ledger)
4. [Quản lý thu chi](#4-quản-lý-thu-chi)
5. [Công nợ tổng hợp](#5-công-nợ-tổng-hợp)
6. [Báo cáo tài chính](#6-báo-cáo-tài-chính)
7. [Thiết kế Database](#7-thiết-kế-database)
8. [Backend - API kế toán](#8-backend---api-kế-toán)
9. [Frontend - Giao diện kế toán](#9-frontend---giao-diện-kế-toán)

---

## 1. Tổng quan kế toán trong ERP

### 1.1 Accounting module là gì?

Module Accounting tổng hợp tất cả giao dịch tài chính từ các module khác (Sales, Procurement) và cung cấp báo cáo tài chính cho ban lãnh đạo.

### 1.2 Nguyên tắc kế toán cơ bản

**Nguyên tắc ghi sổ kép (Double-entry):**

Mỗi giao dịch đều có 2 vế: **Nợ (Debit)** và **Có (Credit)**, tổng Nợ = tổng Có.

```
Ví dụ: Bán hàng thu tiền mặt 10.000.000₫

  Nợ (Debit):  Tiền mặt (111)    +10.000.000₫
  Có (Credit): Doanh thu (511)   +10.000.000₫

Ví dụ: Mua hàng chưa thanh toán 5.000.000₫

  Nợ (Debit):  Hàng hóa (156)    +5.000.000₫
  Có (Credit): Phải trả NCC (331) +5.000.000₫
```

### 1.3 Mối liên hệ với các module khác

```
Sales (Bán hàng)      → Ghi nhận Doanh thu + Công nợ phải thu
Procurement (Mua hàng) → Ghi nhận Chi phí + Công nợ phải trả
HR (Nhân sự)           → Ghi nhận Chi phí lương
Inventory (Kho)        → Ghi nhận Giá vốn hàng bán
```

---

## 2. Hệ thống tài khoản kế toán

### 2.1 Nhóm tài khoản chính

| Nhóm | Loại | Ví dụ |
|---|---|---|
| **1xx** | Tài sản | 111: Tiền mặt, 112: Ngân hàng, 131: Phải thu KH, 156: Hàng hóa |
| **2xx** | Tài sản cố định | 211: TSCĐ, 214: Hao mòn |
| **3xx** | Nợ phải trả | 331: Phải trả NCC, 334: Phải trả nhân viên |
| **4xx** | Vốn chủ sở hữu | 411: Vốn đầu tư, 421: Lợi nhuận |
| **5xx** | Doanh thu | 511: Doanh thu bán hàng |
| **6xx** | Chi phí | 632: Giá vốn, 641: Chi phí bán hàng, 642: Chi phí quản lý |

### 2.2 Database - Chart of Accounts

```sql
CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(10) UNIQUE NOT NULL,    -- 111, 112, 131...
    name VARCHAR(200) NOT NULL,
    type VARCHAR(20) NOT NULL,           -- ASSET, LIABILITY, EQUITY, REVENUE, EXPENSE
    parent_code VARCHAR(10),
    level INT DEFAULT 1,
    active BOOLEAN DEFAULT TRUE
);
```

---

## 3. Sổ cái (General Ledger)

### 3.1 Sổ cái là gì?

Sổ cái ghi nhận **tất cả** giao dịch tài chính. Mỗi bút toán (journal entry) có nhiều dòng, tổng Nợ = tổng Có.

### 3.2 Cấu trúc bút toán

```sql
-- Journal Entry (Bút toán)
CREATE TABLE journal_entries (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    entry_date DATE NOT NULL,
    description TEXT,
    reference_type VARCHAR(20),    -- SALES, PURCHASE, PAYMENT, SALARY, MANUAL
    reference_id BIGINT,
    status VARCHAR(20) DEFAULT 'DRAFT',  -- DRAFT, POSTED
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Journal Entry Lines (Chi tiết bút toán)
CREATE TABLE journal_entry_lines (
    id BIGSERIAL PRIMARY KEY,
    entry_id BIGINT REFERENCES journal_entries(id) ON DELETE CASCADE,
    account_code VARCHAR(10) REFERENCES accounts(code),
    description TEXT,
    debit_amount DECIMAL(15,2) DEFAULT 0,
    credit_amount DECIMAL(15,2) DEFAULT 0
);
```

### 3.3 Ví dụ bút toán

```
Bán hàng 10.000.000₫ (chưa thu tiền):

Journal Entry: JE-2024-0001
| TK    | Diễn giải       | Nợ (Debit)   | Có (Credit)  |
|-------|-----------------|-------------|-------------|
| 131   | Phải thu KH     | 10.000.000  |             |
| 511   | Doanh thu       |             | 10.000.000  |
| Tổng  |                 | 10.000.000  | 10.000.000  | ✅ Cân

Thu tiền khách:

| TK    | Diễn giải       | Nợ (Debit)   | Có (Credit)  |
|-------|-----------------|-------------|-------------|
| 112   | Tiền ngân hàng  | 10.000.000  |             |
| 131   | Phải thu KH     |             | 10.000.000  |
```

### 3.4 Tự động tạo bút toán

```java
// Khi Sales tạo hóa đơn → tự động ghi sổ
public void postSalesInvoice(InvoiceEntity invoice) {
    JournalEntry entry = JournalEntry.builder()
        .code(generateCode())
        .entryDate(invoice.getInvoiceDate())
        .description("Hóa đơn bán hàng: " + invoice.getCode())
        .referenceType("SALES")
        .referenceId(invoice.getId())
        .status("POSTED")
        .build();

    // Nợ: Phải thu khách hàng
    entry.addLine("131", invoice.getTotalAmount(), BigDecimal.ZERO);
    // Có: Doanh thu
    entry.addLine("511", BigDecimal.ZERO,
        invoice.getTotalAmount().subtract(invoice.getTaxAmount()));
    // Có: Thuế GTGT phải nộp
    entry.addLine("3331", BigDecimal.ZERO, invoice.getTaxAmount());

    journalEntryRepo.save(entry);
}
```

---

## 4. Quản lý thu chi

### 4.1 Phiếu thu (Receipt Voucher)

```sql
CREATE TABLE receipt_vouchers (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    voucher_date DATE NOT NULL,
    customer_id BIGINT REFERENCES customers(id),
    amount DECIMAL(15,2) NOT NULL,
    payment_method VARCHAR(20),
    description TEXT,
    invoice_id BIGINT REFERENCES invoices(id),
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 4.2 Phiếu chi (Payment Voucher)

```sql
CREATE TABLE payment_vouchers (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    voucher_date DATE NOT NULL,
    supplier_id BIGINT REFERENCES suppliers(id),
    amount DECIMAL(15,2) NOT NULL,
    payment_method VARCHAR(20),
    description TEXT,
    purchase_order_id BIGINT,
    category VARCHAR(50),         -- SUPPLIER, SALARY, RENT, UTILITY, OTHER
    created_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

## 5. Công nợ tổng hợp

### 5.1 Bảng tổng hợp công nợ

```
┌─────────────────────────────────────────────┐
│          BẢNG TỔNG HỢP CÔNG NỢ             │
├──────────────────┬──────────────────────────┤
│  Phải thu (AR)   │  Phải trả (AP)          │
│  = Tổng HĐ bán  │  = Tổng PO đã nhận      │
│  - Tổng đã thu   │  - Tổng đã trả          │
│                  │                          │
│  150.000.000₫    │  80.000.000₫            │
└──────────────────┴──────────────────────────┘
```

---

## 6. Báo cáo tài chính

### 6.1 Báo cáo Lãi lỗ (Income Statement)

```
┌─────────────────────────────────────┐
│       BÁO CÁO LÃI LỖ              │
│       Tháng 06/2024                 │
├─────────────────────────────────────┤
│ Doanh thu bán hàng    1.250.000.000│
│ - Giá vốn hàng bán      750.000.000│
│ ─────────────────────────────────── │
│ = Lợi nhuận gộp         500.000.000│
│                                     │
│ - Chi phí bán hàng       100.000.000│
│ - Chi phí quản lý        150.000.000│
│ - Chi phí lương           200.000.000│
│ ─────────────────────────────────── │
│ = Lợi nhuận thuần         50.000.000│
└─────────────────────────────────────┘
```

### 6.2 Query báo cáo lãi lỗ

```sql
-- Doanh thu
SELECT COALESCE(SUM(credit_amount), 0) AS revenue
FROM journal_entry_lines jl
JOIN journal_entries je ON jl.entry_id = je.id
WHERE jl.account_code LIKE '511%'
AND je.entry_date BETWEEN :from AND :to
AND je.status = 'POSTED';

-- Chi phí
SELECT jl.account_code,
       a.name AS account_name,
       COALESCE(SUM(jl.debit_amount), 0) AS amount
FROM journal_entry_lines jl
JOIN journal_entries je ON jl.entry_id = je.id
JOIN accounts a ON jl.account_code = a.code
WHERE jl.account_code LIKE '6%'
AND je.entry_date BETWEEN :from AND :to
AND je.status = 'POSTED'
GROUP BY jl.account_code, a.name;
```

### 6.3 Bảng cân đối kế toán

```
┌──────────────────────────────────────────────┐
│        BẢNG CÂN ĐỐI KẾ TOÁN                │
├──────────────────┬───────────────────────────┤
│    TÀI SẢN       │    NGUỒN VỐN             │
├──────────────────┤───────────────────────────┤
│ Tiền mặt   200tr │ Phải trả NCC    80tr     │
│ Ngân hàng  500tr │ Phải trả NV    150tr     │
│ Phải thu   150tr │ Vay NH         300tr     │
│ Hàng tồn   300tr │ ────────────────────      │
│ TSCĐ       500tr │ Tổng nợ        530tr     │
│                   │                           │
│                   │ Vốn CSH        800tr     │
│                   │ Lợi nhuận      320tr     │
│                   │ ────────────────────      │
│                   │ Tổng vốn CSH  1.120tr    │
├──────────────────┤───────────────────────────┤
│ Tổng TS  1.650tr │ Tổng NV     1.650tr      │
└──────────────────┴───────────────────────────┘
   (Tổng Tài sản = Tổng Nguồn vốn) ✅
```

---

## 7. Thiết kế Database

Xem các bảng đã mô tả ở trên: `accounts`, `journal_entries`, `journal_entry_lines`, `receipt_vouchers`, `payment_vouchers`.

---

## 8. Backend - API kế toán

### 8.1 API Endpoints

| Method | URL | Mô tả |
|---|---|---|
| GET | `/api/accounting/accounts` | Hệ thống tài khoản |
| GET | `/api/accounting/journal-entries` | Sổ cái |
| POST | `/api/accounting/journal-entries` | Tạo bút toán thủ công |
| GET | `/api/accounting/receipts` | Danh sách phiếu thu |
| POST | `/api/accounting/receipts` | Tạo phiếu thu |
| GET | `/api/accounting/payments` | Danh sách phiếu chi |
| POST | `/api/accounting/payments` | Tạo phiếu chi |
| GET | `/api/accounting/reports/income-statement` | Báo cáo lãi lỗ |
| GET | `/api/accounting/reports/balance-sheet` | Bảng cân đối |
| GET | `/api/accounting/reports/cash-flow` | Báo cáo tiền |

---

## 9. Frontend - Giao diện kế toán

### 9.1 Cấu trúc trang

```
src/pages/Accounting/
├── ChartOfAccounts.jsx      ← Hệ thống tài khoản
├── JournalEntryList.jsx     ← Sổ cái
├── JournalEntryForm.jsx     ← Tạo bút toán
├── ReceiptList.jsx          ← Phiếu thu
├── PaymentList.jsx          ← Phiếu chi
├── IncomeStatement.jsx      ← Báo cáo lãi lỗ
├── BalanceSheet.jsx         ← Bảng cân đối
└── AccountingDashboard.jsx  ← Tổng quan tài chính
```

### 9.2 Dashboard Tài chính

```jsx
function AccountingDashboard() {
  return (
    <div>
      <h1>📒 Tổng quan Tài chính</h1>
      <div className="stats-grid">
        <div className="stat-card positive">
          <h4>Doanh thu tháng</h4>
          <p>1.250.000.000₫</p>
        </div>
        <div className="stat-card negative">
          <h4>Chi phí tháng</h4>
          <p>1.200.000.000₫</p>
        </div>
        <div className="stat-card">
          <h4>Lợi nhuận</h4>
          <p>50.000.000₫</p>
        </div>
        <div className="stat-card warning">
          <h4>Công nợ phải thu</h4>
          <p>150.000.000₫</p>
        </div>
      </div>
      {/* Biểu đồ doanh thu - chi phí theo tháng */}
    </div>
  );
}
```

---

> **Tiếp theo:** Module cuối cùng - **Nhân sự (HR)**.
