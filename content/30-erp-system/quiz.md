# Quiz - Dự Án ERP

## Câu 1

[TYPE: MULTIPLE_CHOICE]

ERP là viết tắt của gì?

- [ ] Enterprise Resource Processing
- [x] Enterprise Resource Planning
- [ ] Enterprise Relationship Platform
- [ ] Enterprise Report Planning

> **Giải thích:** ERP = Enterprise Resource Planning (Hoạch định Tài nguyên Doanh nghiệp) - hệ thống phần mềm tích hợp quản lý toàn bộ hoạt động doanh nghiệp.

## Câu 2

[TYPE: MULTIPLE_CHOICE]

Trong kiến trúc Layered Architecture, lớp nào chịu trách nhiệm xử lý business logic?

- [ ] Controller Layer
- [x] Service Layer
- [ ] Repository Layer
- [ ] Entity Layer

> **Giải thích:** Service Layer chứa toàn bộ business logic (nghiệp vụ). Controller chỉ nhận request/trả response. Repository chỉ truy vấn DB.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Tại sao cần sử dụng DTO (Data Transfer Object) thay vì trả Entity trực tiếp?

- [ ] DTO chạy nhanh hơn Entity
- [ ] DTO được Spring tự động tạo
- [x] Để kiểm soát dữ liệu gửi cho client, tránh lộ thông tin nhạy cảm
- [ ] Entity không thể chuyển thành JSON

> **Giải thích:** DTO giúp kiểm soát chính xác những field nào được gửi cho client. Entity có thể chứa field nhạy cảm (password) hoặc lazy-loaded relationships gây lỗi serialization.

## Câu 4

[TYPE: MULTIPLE_CHOICE]

Trong Spring Data JPA, annotation nào đánh dấu một class là entity ánh xạ tới bảng database?

- [ ] @Table
- [x] @Entity
- [ ] @Repository
- [ ] @Component

> **Giải thích:** `@Entity` là annotation JPA đánh dấu class là một persistent entity. `@Table` chỉ tùy chỉnh tên bảng, không bắt buộc.

## Câu 5

[TYPE: MULTIPLE_CHOICE]

JWT (JSON Web Token) được sử dụng cho mục đích gì trong hệ thống ERP?

- [ ] Mã hóa dữ liệu trong database
- [ ] Nén dữ liệu API response
- [x] Xác thực người dùng mà không cần lưu session trên server
- [ ] Tạo báo cáo tài chính

> **Giải thích:** JWT cho phép xác thực stateless - server không cần lưu session. Token chứa thông tin user, được ký bằng secret key, client gửi kèm mỗi request.

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Trong React, hook nào phù hợp nhất để gọi API khi component mount?

- [ ] useState
- [x] useEffect
- [ ] useRef
- [ ] useMemo

> **Giải thích:** `useEffect` với dependency array rỗng `[]` chạy 1 lần khi component mount, phù hợp để gọi API load dữ liệu ban đầu.

## Câu 7

[TYPE: TRUE_FALSE]

Mệnh đề: "Trong REST API, method PUT dùng để tạo mới resource."

- [ ] Đúng
- [x] Sai

> **Giải thích:** PUT dùng để cập nhật (update) toàn bộ resource. POST dùng để tạo mới. PUT yêu cầu gửi toàn bộ fields, PATCH chỉ gửi fields cần sửa.

## Câu 8

[TYPE: MULTIPLE_CHOICE]

CORS (Cross-Origin Resource Sharing) cần cấu hình khi nào?

- [ ] Khi frontend và backend cùng domain và port
- [x] Khi frontend (port 3000) và backend (port 8080) khác origin
- [ ] Khi sử dụng HTTPS
- [ ] Khi database ở server khác

> **Giải thích:** Browser chặn request cross-origin mặc định. Frontend (localhost:3000) gọi Backend (localhost:8080) là khác origin → cần cấu hình CORS trên server.

## Câu 9

[TYPE: MULTIPLE_CHOICE]

Trong dự án ERP, quy trình bán hàng đúng là:

- [ ] Hóa đơn → Đơn hàng → Báo giá → Giao hàng
- [ ] Đơn hàng → Hóa đơn → Báo giá → Thanh toán
- [x] Báo giá → Đơn hàng → Giao hàng → Hóa đơn → Thanh toán
- [ ] Báo giá → Hóa đơn → Đơn hàng → Giao hàng

> **Giải thích:** Quy trình chuẩn: Báo giá cho khách → Khách đồng ý → Tạo đơn hàng → Giao hàng → Xuất hóa đơn → Thu tiền. Mỗi bước chuyển trạng thái tự động.

## Câu 10

[TYPE: MULTIPLE_CHOICE]

BOM (Bill of Materials) trong module Sản xuất là gì?

- [ ] Danh sách nhân viên tham gia sản xuất
- [ ] Báo cáo chi phí sản xuất
- [x] Công thức/danh sách nguyên vật liệu cần để sản xuất một sản phẩm
- [ ] Đơn đặt hàng mua nguyên liệu

> **Giải thích:** BOM (Bill of Materials) liệt kê tất cả nguyên vật liệu, số lượng cần thiết để sản xuất 1 đơn vị sản phẩm. VD: Sản xuất 1 SIM cần: 1 chip, 1 phôi, 1 bao bì.
