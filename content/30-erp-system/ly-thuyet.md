# Dự Án ERP - Hệ Thống Quản Lý Doanh Nghiệp

## Mục lục

1. [Tổng quan về ERP](#1-tổng-quan-về-erp)
2. [Kiến trúc hệ thống](#2-kiến-trúc-hệ-thống)
3. [Công nghệ sử dụng](#3-công-nghệ-sử-dụng)
4. [Thiết kế Database tổng quan](#4-thiết-kế-database-tổng-quan)
5. [Cấu trúc dự án](#5-cấu-trúc-dự-án)
6. [Các module nghiệp vụ](#6-các-module-nghiệp-vụ)
7. [Hướng dẫn khởi tạo dự án](#7-hướng-dẫn-khởi-tạo-dự-án)

---

## 1. Tổng quan về ERP

### 1.1 ERP là gì?

**ERP (Enterprise Resource Planning)** là hệ thống phần mềm tích hợp giúp quản lý toàn bộ hoạt động của doanh nghiệp trong một nền tảng thống nhất. Thay vì mỗi phòng ban sử dụng phần mềm riêng biệt, ERP kết nối tất cả thành một hệ thống duy nhất.

### 1.2 Tại sao cần ERP?

| Vấn đề KHÔNG có ERP | Giải pháp với ERP |
|---|---|
| Dữ liệu rời rạc giữa các phòng ban | Một database tập trung, dữ liệu nhất quán |
| Quy trình thủ công, dễ sai sót | Tự động hóa quy trình nghiệp vụ |
| Không có báo cáo tổng hợp | Dashboard realtime, báo cáo đa chiều |
| Khó kiểm soát tồn kho, công nợ | Theo dõi realtime, cảnh báo tự động |
| Phụ thuộc Excel, giấy tờ | Hệ thống số hóa hoàn toàn |

### 1.3 Các module trong dự án ERP

Dự án ERP của chúng ta gồm **6 module chính**:

```
┌─────────────────────────────────────────────────┐
│                    ERP SYSTEM                     │
├─────────┬─────────┬─────────┬──────────┬────────┤
│  Sales  │Purchase │Inventory│Manufactur│Accounti│
│  Bán    │Mua hàng │  Kho    │Sản xuất  │Kế toán │
│  hàng   │         │         │          │Tài chính│
├─────────┴─────────┴─────────┴──────────┴────────┤
│              HR - Nhân sự                        │
├─────────────────────────────────────────────────┤
│         Shared: Auth, User, Dashboard            │
└─────────────────────────────────────────────────┘
```

**1. Quản lý Bán hàng (Sales):** Báo giá → Đơn hàng → Hóa đơn → Công nợ

**2. Quản lý Mua hàng (Procurement):** Yêu cầu mua → Đơn đặt hàng → Nhập hàng → Công nợ phải trả

**3. Quản lý Kho (Inventory):** Nhập kho, Xuất kho, Chuyển kho, Kiểm kê, Tồn kho realtime

**4. Quản lý Sản xuất (Manufacturing):** BOM → Kế hoạch → Lệnh sản xuất

**5. Kế toán - Tài chính (Accounting):** Sổ cái, Thu chi, Công nợ, Báo cáo tài chính

**6. Nhân sự (HR):** Hồ sơ nhân viên, Chấm công, Lương, KPI

---

## 2. Kiến trúc hệ thống

### 2.1 Kiến trúc tổng quan

Dự án sử dụng kiến trúc **Client-Server** với:
- **Frontend:** React (SPA - Single Page Application)
- **Backend:** Spring Boot (REST API)
- **Database:** PostgreSQL

```
┌──────────────┐     HTTP/REST     ┌──────────────┐     JDBC      ┌──────────┐
│   React App  │ ◄──────────────► │  Spring Boot │ ◄───────────► │PostgreSQL│
│  (Frontend)  │     JSON          │  (Backend)   │               │(Database)│
│  Port: 3000  │                   │  Port: 8080  │               │Port: 5432│
└──────────────┘                   └──────────────┘               └──────────┘
```

### 2.2 Kiến trúc Backend (Layered Architecture)

```
┌─────────────────────────────────────┐
│         Controller Layer            │  ← Nhận HTTP Request, trả Response
│   (REST API Endpoints)              │
├─────────────────────────────────────┤
│          Service Layer              │  ← Business Logic (nghiệp vụ)
│   (Business Logic)                  │
├─────────────────────────────────────┤
│        Repository Layer             │  ← Truy vấn Database
│   (Data Access - JPA)               │
├─────────────────────────────────────┤
│          Entity Layer               │  ← Ánh xạ bảng DB thành Java Object
│   (JPA Entities)                    │
├─────────────────────────────────────┤
│           Database                  │  ← PostgreSQL
└─────────────────────────────────────┘
```

### 2.3 Kiến trúc Frontend (Component-Based)

```
src/
├── components/          ← UI Components tái sử dụng
│   ├── common/          ← Button, Input, Table, Modal...
│   ├── layout/          ← Header, Sidebar, Footer
│   └── charts/          ← Biểu đồ báo cáo
├── pages/               ← Trang chính cho mỗi module
│   ├── Dashboard/
│   ├── Sales/
│   ├── Inventory/
│   └── ...
├── services/            ← Gọi API (axios)
├── store/               ← State management (Redux/Context)
├── hooks/               ← Custom hooks
├── utils/               ← Helper functions
└── App.jsx              ← Router chính
```

---

## 3. Công nghệ sử dụng

### 3.1 Backend

| Công nghệ | Mục đích |
|---|---|
| **Java 17** | Ngôn ngữ chính |
| **Spring Boot 3.x** | Framework backend |
| **Spring Data JPA** | ORM - truy vấn database |
| **Spring Security** | Xác thực, phân quyền |
| **Spring Validation** | Validate dữ liệu đầu vào |
| **PostgreSQL** | Cơ sở dữ liệu quan hệ |
| **Lombok** | Giảm boilerplate code |
| **MapStruct** | Chuyển đổi Entity ↔ DTO |
| **Maven** | Quản lý dependencies |

### 3.2 Frontend

| Công nghệ | Mục đích |
|---|---|
| **React 18** | Thư viện UI |
| **React Router v6** | Điều hướng trang |
| **Axios** | Gọi REST API |
| **Redux Toolkit** | Quản lý state toàn cục |
| **Ant Design / MUI** | UI Component Library |
| **Chart.js** | Biểu đồ báo cáo |
| **React Hook Form** | Quản lý form |
| **CSS Modules** | Styling |

---

## 4. Thiết kế Database tổng quan

### 4.1 ERD (Entity Relationship Diagram) cốt lõi

```sql
-- Bảng Users (Dùng chung cho toàn hệ thống)
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    email VARCHAR(100),
    role VARCHAR(20) DEFAULT 'USER',
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bảng Customers (Khách hàng)
CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(200) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    address TEXT,
    tax_code VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bảng Suppliers (Nhà cung cấp)
CREATE TABLE suppliers (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(200) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    address TEXT,
    tax_code VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bảng Products (Sản phẩm)
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(200) NOT NULL,
    category VARCHAR(50),
    unit VARCHAR(20),
    price DECIMAL(15,2) DEFAULT 0,
    cost_price DECIMAL(15,2) DEFAULT 0,
    min_stock INT DEFAULT 0,
    description TEXT,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bảng Warehouses (Kho hàng)
CREATE TABLE warehouses (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    address TEXT,
    manager_id BIGINT REFERENCES users(id),
    active BOOLEAN DEFAULT TRUE
);
```

### 4.2 Mối quan hệ giữa các module

```
Customer ──┐
           ├── Sales Order ──► Invoice ──► Payment
Product  ──┘       │
  │                ▼
  │         Inventory (Xuất kho)
  │                ▲
  ▼                │
Supplier ──► Purchase Order ──► Inventory (Nhập kho)
  │
  ▼
BOM ──► Work Order ──► Inventory (Nhập thành phẩm)
```

---

## 5. Cấu trúc dự án

### 5.1 Backend Project Structure

```
erp-backend/
├── pom.xml
├── src/main/java/com/erp/
│   ├── ErpApplication.java
│   ├── config/
│   │   ├── SecurityConfig.java
│   │   ├── CorsConfig.java
│   │   └── JwtConfig.java
│   ├── common/
│   │   ├── BaseEntity.java
│   │   ├── ApiResponse.java
│   │   └── PageResponse.java
│   ├── module/
│   │   ├── auth/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── dto/
│   │   │   └── entity/
│   │   ├── sales/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── dto/
│   │   │   └── entity/
│   │   ├── procurement/
│   │   ├── inventory/
│   │   ├── manufacturing/
│   │   ├── accounting/
│   │   └── hr/
│   └── exception/
│       ├── GlobalExceptionHandler.java
│       └── ResourceNotFoundException.java
└── src/main/resources/
    ├── application.yml
    └── db/migration/
```

### 5.2 Frontend Project Structure

```
erp-frontend/
├── package.json
├── public/
├── src/
│   ├── App.jsx
│   ├── index.js
│   ├── components/
│   │   ├── common/
│   │   │   ├── Button.jsx
│   │   │   ├── DataTable.jsx
│   │   │   ├── Modal.jsx
│   │   │   ├── SearchBar.jsx
│   │   │   └── StatusBadge.jsx
│   │   └── layout/
│   │       ├── MainLayout.jsx
│   │       ├── Sidebar.jsx
│   │       └── Header.jsx
│   ├── pages/
│   │   ├── Dashboard/
│   │   ├── Sales/
│   │   ├── Procurement/
│   │   ├── Inventory/
│   │   ├── Manufacturing/
│   │   ├── Accounting/
│   │   └── HR/
│   ├── services/
│   │   ├── api.js
│   │   ├── salesService.js
│   │   └── ...
│   ├── store/
│   │   ├── store.js
│   │   └── slices/
│   └── utils/
│       ├── formatters.js
│       └── constants.js
└── .env
```

---

## 6. Các module nghiệp vụ

### 6.1 Quản lý Bán hàng (Sales)
- **Báo giá (Quotation):** Tạo báo giá cho khách, có thể convert sang đơn hàng
- **Đơn hàng (Sales Order):** Quản lý đơn hàng, trạng thái, lịch sử
- **Hóa đơn (Invoice):** Xuất hóa đơn từ đơn hàng, theo dõi thanh toán
- **Công nợ khách hàng:** Bao nhiêu khách nợ, đến hạn, quá hạn

### 6.2 Quản lý Mua hàng (Procurement)
- **Yêu cầu mua hàng:** Các phòng ban đề xuất mua
- **Đơn đặt hàng (PO):** Tạo PO cho nhà cung cấp
- **Nhập hàng:** Kiểm tra và nhập kho
- **Công nợ phải trả:** Theo dõi tiền nợ nhà cung cấp

### 6.3 Quản lý Kho (Inventory)
- Nhập kho, Xuất kho, Chuyển kho giữa các kho
- Kiểm kê hàng tồn
- Theo dõi tồn kho realtime, cảnh báo hết hàng

### 6.4 Quản lý Sản xuất (Manufacturing)
- **BOM (Bill of Materials):** Công thức sản xuất
- **Kế hoạch sản xuất:** Lập kế hoạch dựa trên nhu cầu
- **Lệnh sản xuất (Work Order):** Theo dõi tiến độ sản xuất

### 6.5 Kế toán - Tài chính (Accounting)
- Sổ cái tổng hợp
- Quản lý thu chi
- Báo cáo tài chính: Bảng cân đối, Lãi lỗ

### 6.6 Nhân sự (HR)
- Hồ sơ nhân viên
- Chấm công
- Tính lương
- Đánh giá KPI

---

## 7. Hướng dẫn khởi tạo dự án

### 7.1 Khởi tạo Backend (Spring Boot)

**Bước 1:** Truy cập [Spring Initializr](https://start.spring.io/) và chọn:
- **Project:** Maven
- **Language:** Java
- **Spring Boot:** 3.2.x
- **Group:** com.erp
- **Artifact:** erp-backend
- **Dependencies:** Spring Web, Spring Data JPA, PostgreSQL Driver, Spring Security, Lombok, Validation

**Bước 2:** Cấu hình `application.yml`:

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/erp_db
    username: postgres
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: Asia/Ho_Chi_Minh
```

**Bước 3:** Tạo BaseEntity:

```java
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private String createdBy;
}
```

### 7.2 Khởi tạo Frontend (React)

**Bước 1:** Tạo project React:

```bash
npx create-react-app erp-frontend
cd erp-frontend
```

**Bước 2:** Cài đặt dependencies:

```bash
npm install react-router-dom axios @reduxjs/toolkit react-redux
npm install antd @ant-design/icons
npm install chart.js react-chartjs-2
npm install react-hook-form
```

**Bước 3:** Tạo cấu trúc thư mục:

```bash
mkdir -p src/components/common src/components/layout
mkdir -p src/pages/Dashboard src/pages/Sales src/pages/Procurement
mkdir -p src/pages/Inventory src/pages/Manufacturing
mkdir -p src/pages/Accounting src/pages/HR
mkdir -p src/services src/store/slices src/hooks src/utils
```

**Bước 4:** Cấu hình Router cơ bản:

```jsx
// src/App.jsx
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import MainLayout from './components/layout/MainLayout';
import Dashboard from './pages/Dashboard/Dashboard';
import SalesList from './pages/Sales/SalesList';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainLayout />}>
          <Route index element={<Dashboard />} />
          <Route path="sales/*" element={<SalesList />} />
          {/* Thêm routes cho các module khác */}
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
```

**Bước 5:** Tạo API service:

```javascript
// src/services/api.js
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: { 'Content-Type': 'application/json' }
});

// Interceptor thêm JWT token
api.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;
```

---

> **Lưu ý:** Mỗi module nghiệp vụ sẽ được hướng dẫn chi tiết trong phần riêng. Hãy bắt đầu từ module **Quản lý Bán hàng (Sales)** vì đây là module cốt lõi và dễ hiểu nhất.
