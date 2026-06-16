# Module Kế Toán - Bài Tập

## Bài 1: Hệ thống tài khoản (Chart of Accounts)
**Độ khó: Trung bình**

Xây dựng quản lý hệ thống tài khoản kế toán:

**Backend:**
1. Entity `AccountEntity`: code, name, type (ASSET/LIABILITY/EQUITY/REVENUE/EXPENSE), parentCode, level
2. API: GET (tree structure), POST, PUT, DELETE
3. Seed data: tạo sẵn các tài khoản cơ bản (111, 112, 131, 331, 511, 632...)

**Frontend:**
4. Hiển thị dạng cây (tree view): nhóm cha → con
5. Tìm kiếm theo mã hoặc tên tài khoản
6. Form thêm/sửa tài khoản

**Đầu vào:** Thông tin tài khoản
**Đầu ra:** Hệ thống tài khoản dạng cây

---

## Bài 2: Sổ cái - Bút toán (Journal Entry)
**Độ khó: Khó**

Xây dựng chức năng ghi sổ cái:

**Backend:**
1. Entity: `JournalEntryEntity` (header) + `JournalEntryLineEntity` (lines: accountCode, debit, credit)
2. API tạo bút toán: nhận danh sách dòng, validate tổng Nợ = tổng Có
3. API post bút toán: DRAFT → POSTED (không thể sửa sau khi post)
4. API xem sổ cái: filter theo tài khoản, thời gian

**Frontend:**
5. Danh sách bút toán: mã, ngày, diễn giải, tổng tiền, trạng thái
6. Form tạo bút toán: thêm dòng (dynamic rows), mỗi dòng chọn TK + nhập Nợ/Có
7. Validate realtime: hiển thị tổng Nợ, tổng Có, chênh lệch (phải = 0)
8. Nút "Ghi sổ" (Post) khi cân

**Đầu vào:** Ngày, diễn giải, danh sách dòng (TK + Nợ/Có)
**Đầu ra:** Bút toán ghi sổ đúng, validate Nợ = Có

---

## Bài 3: Phiếu thu / Phiếu chi
**Độ khó: Trung bình**

Xây dựng quản lý thu chi:

**Backend:**
1. Entity: `ReceiptVoucherEntity` (thu), `PaymentVoucherEntity` (chi)
2. Phiếu thu: tạo từ hóa đơn bán hàng hoặc thu thủ công
3. Phiếu chi: chi cho NCC, lương, tiện ích, khác
4. Tự động tạo bút toán kế toán khi tạo phiếu

**Frontend:**
5. Danh sách phiếu thu: filter theo ngày, phương thức (tiền mặt/CK)
6. Danh sách phiếu chi: filter theo loại chi (NCC/Lương/Khác)
7. Form tạo phiếu: chọn loại, số tiền, phương thức, liên kết HĐ

**Đầu vào:** Loại phiếu, số tiền, ngày, phương thức
**Đầu ra:** Phiếu thu/chi, bút toán tự tạo

---

## Bài 4: Báo cáo Lãi lỗ (Income Statement)
**Độ khó: Trung bình - Khó**

Xây dựng báo cáo lãi lỗ:

**Backend:**
1. API `/api/accounting/reports/income-statement?from=&to=`
2. Tính: Doanh thu (TK 511) - Giá vốn (TK 632) = Lợi nhuận gộp
3. Lợi nhuận gộp - Chi phí (TK 641, 642) = Lợi nhuận thuần
4. Trả về object có cấu trúc: revenue, cogs, grossProfit, expenses[], netProfit

**Frontend:**
5. Hiển thị dạng bảng kế toán (giống báo cáo chính thức)
6. Chọn khoảng thời gian (tháng/quý/năm)
7. Biểu đồ Bar: doanh thu vs chi phí theo tháng
8. So sánh với kỳ trước (nếu có)

**Đầu vào:** Khoảng thời gian
**Đầu ra:** Báo cáo lãi lỗ đúng cấu trúc kế toán

---

## Bài 5: Dashboard Tài chính
**Độ khó: Trung bình**

Xây dựng trang tổng quan tài chính:

**Backend:**
1. API thống kê: doanh thu/chi phí/lợi nhuận tháng hiện tại
2. API công nợ tổng hợp: phải thu + phải trả
3. API dòng tiền: tổng thu - tổng chi trong kỳ

**Frontend:**
4. 4 card: Doanh thu, Chi phí, Lợi nhuận, Dòng tiền ròng
5. Biểu đồ Line: doanh thu vs chi phí 6 tháng gần nhất
6. Biểu đồ Pie: cơ cấu chi phí
7. Bảng công nợ tóm tắt: AR vs AP

**Đầu vào:** Không
**Đầu ra:** Dashboard tài chính trực quan
