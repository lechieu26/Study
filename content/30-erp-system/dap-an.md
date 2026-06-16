# Dự Án ERP - Đáp Án Bài Tập Khởi Tạo

## Bài 1: Khởi tạo Backend Spring Boot

**pom.xml (dependencies chính):**
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
</dependencies>
```

**BaseEntity.java:**
```java
package com.erp.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;
}
```

**ApiResponse.java:**
```java
package com.erp.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, "Thành công", data);
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }
}
```

**GlobalExceptionHandler.java:**
```java
package com.erp.exception;

import com.erp.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .reduce((a, b) -> a + "; " + b)
            .orElse("Dữ liệu không hợp lệ");
        return ResponseEntity.badRequest().body(ApiResponse.error(msg));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error("Lỗi hệ thống: " + ex.getMessage()));
    }
}
```

**CorsConfig.java:**
```java
package com.erp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }
}
```

**Giải thích:**
- `BaseEntity` sử dụng JPA Auditing để tự động ghi createdAt, updatedAt.
- `ApiResponse<T>` chuẩn hóa mọi response về dạng `{success, message, data}`.
- `GlobalExceptionHandler` bắt exception tập trung, trả về lỗi có cấu trúc.
- CORS cho phép frontend port 3000 gọi API.

---

## Bài 2: Khởi tạo Frontend React

**MainLayout.jsx:**
```jsx
import React, { useState } from 'react';
import { Outlet, Link, useLocation } from 'react-router-dom';
import './MainLayout.css';

const menuItems = [
  { key: '/', icon: '📊', label: 'Dashboard' },
  { key: '/sales', icon: '💰', label: 'Bán hàng' },
  { key: '/procurement', icon: '📦', label: 'Mua hàng' },
  { key: '/inventory', icon: '🏭', label: 'Kho hàng' },
  { key: '/manufacturing', icon: '⚙️', label: 'Sản xuất' },
  { key: '/accounting', icon: '📒', label: 'Kế toán' },
  { key: '/hr', icon: '👥', label: 'Nhân sự' },
];

function MainLayout() {
  const [collapsed, setCollapsed] = useState(false);
  const location = useLocation();

  return (
    <div className="layout">
      <aside className={`sidebar ${collapsed ? 'collapsed' : ''}`}>
        <div className="sidebar-logo">
          <h2>{collapsed ? 'E' : 'ERP System'}</h2>
        </div>
        <nav className="sidebar-menu">
          {menuItems.map(item => (
            <Link
              key={item.key}
              to={item.key}
              className={`menu-item ${location.pathname === item.key ? 'active' : ''}`}
            >
              <span className="menu-icon">{item.icon}</span>
              {!collapsed && <span className="menu-label">{item.label}</span>}
            </Link>
          ))}
        </nav>
      </aside>
      <div className="main-content">
        <header className="header">
          <button onClick={() => setCollapsed(!collapsed)} className="toggle-btn">
            ☰
          </button>
          <div className="header-right">
            <span>👤 Admin</span>
            <button className="logout-btn">Đăng xuất</button>
          </div>
        </header>
        <main className="page-content">
          <Outlet />
        </main>
      </div>
    </div>
  );
}

export default MainLayout;
```

**Dashboard.jsx:**
```jsx
import React from 'react';
import './Dashboard.css';

const stats = [
  { title: 'Doanh thu tháng', value: '1,250,000,000 ₫', icon: '💰', color: '#1890ff' },
  { title: 'Đơn hàng', value: '342', icon: '📋', color: '#52c41a' },
  { title: 'Khách hàng', value: '1,205', icon: '👥', color: '#722ed1' },
  { title: 'Sản phẩm', value: '856', icon: '📦', color: '#fa8c16' },
];

function Dashboard() {
  return (
    <div className="dashboard">
      <h1>Dashboard</h1>
      <div className="stats-grid">
        {stats.map((stat, index) => (
          <div key={index} className="stat-card" style={{ borderTopColor: stat.color }}>
            <div className="stat-icon">{stat.icon}</div>
            <div className="stat-info">
              <h3>{stat.title}</h3>
              <p className="stat-value">{stat.value}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Dashboard;
```

**api.js:**
```javascript
import axios from 'axios';

const api = axios.create({
  baseURL: process.env.REACT_APP_API_URL || 'http://localhost:8080/api',
  headers: { 'Content-Type': 'application/json' }
});

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

api.interceptors.response.use(
  response => response.data,
  error => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default api;
```

**Giải thích:**
- `MainLayout` sử dụng React Router `Outlet` để render trang con.
- Sidebar có thể thu gọn (collapse) bằng nút ☰.
- Dashboard hiển thị 4 card thống kê với icon và màu sắc khác nhau.
- `api.js` tự động gắn JWT token và redirect về login khi token hết hạn.

---

## Bài 3: Thiết kế Database Schema

```java
// UserEntity.java
package com.erp.module.auth.entity;

import com.erp.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserEntity extends BaseEntity {

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    @Builder.Default
    private String role = "USER";

    @Builder.Default
    private Boolean active = true;
}

// CustomerEntity.java
package com.erp.module.sales.entity;

import com.erp.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

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

// ProductEntity.java
package com.erp.module.inventory.entity;

import com.erp.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductEntity extends BaseEntity {

    @Column(unique = true, nullable = false, length = 20)
    private String code;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 50)
    private String category;

    @Column(length = 20)
    private String unit;

    @Builder.Default
    private BigDecimal price = BigDecimal.ZERO;

    @Column(name = "cost_price")
    @Builder.Default
    private BigDecimal costPrice = BigDecimal.ZERO;

    @Column(name = "min_stock")
    @Builder.Default
    private Integer minStock = 0;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Builder.Default
    private Boolean active = true;
}

// WarehouseEntity.java
package com.erp.module.inventory.entity;

import com.erp.common.BaseEntity;
import com.erp.module.auth.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

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

**Giải thích:**
- Tất cả Entity kế thừa `BaseEntity` để có id, createdAt, updatedAt.
- Sử dụng Lombok `@Builder` để tạo object dễ dàng hơn.
- `@Column` định nghĩa ràng buộc DB: unique, nullable, length.
- `WarehouseEntity` có quan hệ `@ManyToOne` với `UserEntity` (manager).

---

## Bài 4: Authentication - Đăng nhập / Đăng ký

**Backend - AuthController.java:**
```java
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(
            @Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok(ApiResponse.ok("Đăng ký thành công", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}
```

**Frontend - LoginPage.jsx:**
```jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../../services/api';
import './LoginPage.css';

function LoginPage() {
  const [form, setForm] = useState({ username: '', password: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    try {
      const response = await api.post('/auth/login', form);
      localStorage.setItem('token', response.data.token);
      localStorage.setItem('user', JSON.stringify(response.data.user));
      navigate('/');
    } catch (err) {
      setError(err.response?.data?.message || 'Đăng nhập thất bại');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleSubmit}>
        <h2>🏢 ERP System</h2>
        <h3>Đăng nhập</h3>
        {error && <div className="error-msg">{error}</div>}
        <input
          type="text"
          placeholder="Tên đăng nhập"
          value={form.username}
          onChange={e => setForm({...form, username: e.target.value})}
          required
        />
        <input
          type="password"
          placeholder="Mật khẩu"
          value={form.password}
          onChange={e => setForm({...form, password: e.target.value})}
          required
        />
        <button type="submit" disabled={loading}>
          {loading ? 'Đang xử lý...' : 'Đăng nhập'}
        </button>
      </form>
    </div>
  );
}

export default LoginPage;
```

**Giải thích:**
- Backend sử dụng JWT token để xác thực stateless.
- Frontend lưu token vào localStorage và gửi kèm mọi request.
- `ProtectedRoute` kiểm tra token trước khi cho truy cập route.

---

## Bài 5: CRUD Sản phẩm (Product)

**Backend - ProductController.java:**
```java
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductDTO>>> getAll(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.ok(
            productService.findAll(search, PageRequest.of(page, size))
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(productService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDTO>> create(
            @Valid @RequestBody ProductDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.ok("Tạo sản phẩm thành công", productService.create(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO dto) {
        return ResponseEntity.ok(
            ApiResponse.ok("Cập nhật thành công", productService.update(id, dto))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Xóa thành công", null));
    }
}
```

**Frontend - ProductList.jsx:**
```jsx
import React, { useState, useEffect } from 'react';
import api from '../../services/api';

function ProductList() {
  const [products, setProducts] = useState([]);
  const [search, setSearch] = useState('');
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [showModal, setShowModal] = useState(false);
  const [editItem, setEditItem] = useState(null);

  useEffect(() => {
    loadProducts();
  }, [page, search]);

  const loadProducts = async () => {
    const res = await api.get(`/products?search=${search}&page=${page}&size=10`);
    setProducts(res.data.content);
    setTotalPages(res.data.totalPages);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Bạn có chắc muốn xóa?')) {
      await api.delete(`/products/${id}`);
      loadProducts();
    }
  };

  const handleSave = async (formData) => {
    if (editItem) {
      await api.put(`/products/${editItem.id}`, formData);
    } else {
      await api.post('/products', formData);
    }
    setShowModal(false);
    setEditItem(null);
    loadProducts();
  };

  return (
    <div className="product-page">
      <div className="page-header">
        <h1>📦 Quản lý Sản phẩm</h1>
        <button className="btn-primary" onClick={() => { setEditItem(null); setShowModal(true); }}>
          + Thêm mới
        </button>
      </div>

      <input
        type="text"
        placeholder="Tìm kiếm theo tên hoặc mã..."
        value={search}
        onChange={e => { setSearch(e.target.value); setPage(0); }}
        className="search-input"
      />

      <table className="data-table">
        <thead>
          <tr>
            <th>Mã</th><th>Tên</th><th>Danh mục</th>
            <th>Đơn vị</th><th>Giá bán</th><th>Thao tác</th>
          </tr>
        </thead>
        <tbody>
          {products.map(p => (
            <tr key={p.id}>
              <td>{p.code}</td>
              <td>{p.name}</td>
              <td>{p.category}</td>
              <td>{p.unit}</td>
              <td>{Number(p.price).toLocaleString('vi-VN')} ₫</td>
              <td>
                <button onClick={() => { setEditItem(p); setShowModal(true); }}>✏️</button>
                <button onClick={() => handleDelete(p.id)}>🗑️</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <div className="pagination">
        <button disabled={page === 0} onClick={() => setPage(page - 1)}>‹ Trước</button>
        <span>Trang {page + 1} / {totalPages}</span>
        <button disabled={page >= totalPages - 1} onClick={() => setPage(page + 1)}>Sau ›</button>
      </div>
    </div>
  );
}

export default ProductList;
```

**Giải thích:**
- Backend cung cấp 5 endpoint CRUD chuẩn REST.
- Frontend sử dụng `useEffect` để load dữ liệu khi page/search thay đổi.
- Phân trang server-side với Spring `Pageable`.
- Modal dùng chung cho cả Thêm mới và Chỉnh sửa.
