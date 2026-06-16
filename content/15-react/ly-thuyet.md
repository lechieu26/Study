# React - Lý Thuyết Từ Cơ Bản Đến Nâng Cao

## Mục lục

1. [Giới thiệu về React](#1-giới-thiệu-về-react)
2. [Cài đặt và Thiết lập Dự án](#2-cài-đặt-và-thiết-lập-dự-án)
3. [JSX - JavaScript XML](#3-jsx---javascript-xml)
4. [Components](#4-components)
5. [Props - Truyền Dữ liệu](#5-props---truyền-dữ-liệu)
6. [State - Quản lý Trạng thái](#6-state---quản-lý-trạng-thái)
7. [Event Handling - Xử lý Sự kiện](#7-event-handling---xử-lý-sự-kiện)
8. [Conditional Rendering](#8-conditional-rendering)
9. [Lists và Keys](#9-lists-và-keys)
10. [Forms và Controlled Components](#10-forms-và-controlled-components)
11. [React Hooks](#11-react-hooks)
12. [useEffect và Side Effects](#12-useeffect-và-side-effects)
13. [useRef và DOM Manipulation](#13-useref-và-dom-manipulation)
14. [useMemo và useCallback](#14-usememo-và-usecallback)
15. [useReducer - State phức tạp](#15-usereducer---state-phức-tạp)
16. [Context API - Global State](#16-context-api---global-state)
17. [Custom Hooks](#17-custom-hooks)
18. [React Router](#18-react-router)
19. [State Management với Redux](#19-state-management-với-redux)
20. [Performance Optimization](#20-performance-optimization)
21. [Error Boundaries](#21-error-boundaries)
22. [Higher-Order Components (HOC)](#22-higher-order-components-hoc)
23. [Render Props Pattern](#23-render-props-pattern)
24. [React.memo và Pure Components](#24-reactmemo-và-pure-components)
25. [Portals](#25-portals)
26. [Suspense và Lazy Loading](#26-suspense-và-lazy-loading)
27. [Server-Side Rendering (SSR)](#27-server-side-rendering-ssr)
28. [Testing trong React](#28-testing-trong-react)
29. [Best Practices và Design Patterns](#29-best-practices-và-design-patterns)
30. [Tổng kết](#30-tổng-kết)

---

## 1. Giới thiệu về React

### 1.1 React là gì?

React là một **thư viện JavaScript** (không phải framework) dùng để xây dựng giao diện người dùng (User Interface - UI), được phát triển bởi **Facebook** (nay là Meta) vào năm **2013**. React tập trung vào việc xây dựng UI theo mô hình **component-based** (dựa trên thành phần).

### 1.2 Lịch sử phát triển

| Năm | Sự kiện |
|-----|---------|
| 2011 | Jordan Walke tạo prototype đầu tiên tại Facebook |
| 2013 | React được open-source tại JSConf US |
| 2015 | React Native ra đời cho mobile development |
| 2016 | React 15 - cải thiện rendering performance |
| 2017 | React 16 - Fiber Architecture, Error Boundaries |
| 2019 | React 16.8 - Hooks ra đời, thay đổi cách viết React |
| 2022 | React 18 - Concurrent Features, Automatic Batching |
| 2024 | React 19 - Server Components, Actions, use() hook |

### 1.3 Tại sao chọn React?

**1. Virtual DOM - Hiệu suất cao:**
React sử dụng Virtual DOM để tối ưu hóa việc cập nhật giao diện. Thay vì thao tác trực tiếp trên DOM thật (chậm), React tạo một bản sao nhẹ (Virtual DOM), so sánh sự khác biệt (diffing), và chỉ cập nhật những phần thay đổi (reconciliation).

```
State thay đổi → Virtual DOM mới → Diff với Virtual DOM cũ → Patch DOM thật
```

**2. Component-Based Architecture:**
Giao diện được chia thành các component nhỏ, độc lập, có thể tái sử dụng.

**3. One-Way Data Flow (Unidirectional):**
Dữ liệu chỉ chảy theo một chiều: từ component cha xuống component con thông qua props. Điều này giúp dễ debug và dự đoán được state.

**4. Hệ sinh thái phong phú:**
- React Router cho routing
- Redux/Zustand/Recoil cho state management
- Next.js cho server-side rendering
- React Native cho mobile apps

**5. Cộng đồng lớn và được hỗ trợ bởi Meta.**

### 1.4 React vs Angular vs Vue

| Tiêu chí | React | Angular | Vue |
|-----------|-------|---------|-----|
| Loại | Library | Framework | Framework |
| Ngôn ngữ | JSX (JS + HTML) | TypeScript | Template-based |
| DOM | Virtual DOM | Real DOM + Change Detection | Virtual DOM |
| Data Flow | One-way | Two-way | Two-way |
| Learning Curve | Trung bình | Cao | Thấp |
| Backed by | Meta | Google | Cộng đồng |
| Mobile | React Native | Ionic/NativeScript | Capacitor |

### 1.5 Kiến trúc React

```
┌─────────────────────────────────────────┐
│              React Application           │
├─────────────────────────────────────────┤
│  ┌─────┐  ┌─────┐  ┌─────┐  ┌─────┐   │
│  │Comp │  │Comp │  │Comp │  │Comp │   │
│  │  A  │  │  B  │  │  C  │  │  D  │   │
│  └──┬──┘  └──┬──┘  └──┬──┘  └──┬──┘   │
│     │        │        │        │        │
│  ┌──┴────────┴────────┴────────┴──┐     │
│  │         Virtual DOM             │     │
│  └─────────────┬───────────────────┘     │
│                │ Reconciliation           │
│  ┌─────────────┴───────────────────┐     │
│  │           Real DOM              │     │
│  └─────────────────────────────────┘     │
└─────────────────────────────────────────┘
```

---

## 2. Cài đặt và Thiết lập Dự án

### 2.1 Yêu cầu hệ thống

- **Node.js** >= 18.x (khuyến nghị LTS)
- **npm** >= 9.x hoặc **yarn** >= 1.22

### 2.2 Tạo dự án với Vite (Khuyến nghị)

```bash
# Tạo project mới
npm create vite@latest my-react-app -- --template react

# Hoặc với TypeScript
npm create vite@latest my-react-app -- --template react-ts

# Di chuyển vào project
cd my-react-app

# Cài đặt dependencies
npm install

# Chạy development server
npm run dev
```

### 2.3 Tạo dự án với Create React App (Legacy)

```bash
npx create-react-app my-app
cd my-app
npm start
```

> **Lưu ý:** Create React App đã không còn được khuyến nghị chính thức. Sử dụng Vite hoặc Next.js cho dự án mới.

### 2.4 Cấu trúc dự án chuẩn

```
my-react-app/
├── public/
│   └── index.html
├── src/
│   ├── assets/          # Hình ảnh, fonts
│   ├── components/      # Reusable components
│   │   ├── common/      # Button, Input, Modal...
│   │   └── layout/      # Header, Footer, Sidebar
│   ├── hooks/           # Custom hooks
│   ├── pages/           # Page-level components
│   ├── services/        # API calls
│   ├── store/           # State management
│   ├── utils/           # Helper functions
│   ├── App.jsx
│   ├── App.css
│   └── main.jsx
├── package.json
└── vite.config.js
```

### 2.5 File entry point

```jsx
// main.jsx
import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import './index.css'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
)
```

---

## 3. JSX - JavaScript XML

### 3.1 JSX là gì?

JSX (JavaScript XML) là một **syntax extension** cho JavaScript, cho phép viết cấu trúc giống HTML ngay trong code JavaScript. JSX không phải là HTML, mà được Babel/SWC biên dịch thành các lệnh `React.createElement()`.

```jsx
// JSX
const element = <h1 className="title">Hello, React!</h1>;

// Biên dịch thành:
const element = React.createElement('h1', { className: 'title' }, 'Hello, React!');
```

### 3.2 Quy tắc JSX

**1. Phải có một root element duy nhất:**
```jsx
// ❌ Sai - nhiều root elements
return (
  <h1>Title</h1>
  <p>Content</p>
)

// ✅ Đúng - wrap trong div hoặc Fragment
return (
  <div>
    <h1>Title</h1>
    <p>Content</p>
  </div>
)

// ✅ Tốt hơn - Fragment (không tạo thêm DOM node)
return (
  <>
    <h1>Title</h1>
    <p>Content</p>
  </>
)
```

**2. Đóng tag bắt buộc:**
```jsx
// ❌ Sai
<img src="photo.jpg">
<input type="text">

// ✅ Đúng
<img src="photo.jpg" />
<input type="text" />
```

**3. Sử dụng camelCase cho attributes:**
```jsx
// HTML: class, for, tabindex, onclick
// JSX:  className, htmlFor, tabIndex, onClick

<label htmlFor="name" className="form-label">
  <input tabIndex={1} onClick={handleClick} />
</label>
```

### 3.3 Biểu thức JavaScript trong JSX

Sử dụng dấu ngoặc nhọn `{}` để nhúng biểu thức JavaScript:

```jsx
const name = "React";
const items = ['Apple', 'Banana', 'Cherry'];

function App() {
  return (
    <div>
      {/* Biến */}
      <h1>Hello, {name}!</h1>

      {/* Biểu thức */}
      <p>2 + 2 = {2 + 2}</p>

      {/* Gọi hàm */}
      <p>{name.toUpperCase()}</p>

      {/* Ternary operator */}
      <p>{items.length > 0 ? 'Có items' : 'Trống'}</p>

      {/* Template literal */}
      <p>{`Có ${items.length} items`}</p>
    </div>
  );
}
```

### 3.4 Style trong JSX

```jsx
// Inline style - object với camelCase
const divStyle = {
  backgroundColor: '#f0f0f0',
  fontSize: '16px',
  padding: '20px',
  borderRadius: '8px'
};

<div style={divStyle}>Styled content</div>

// Hoặc inline trực tiếp
<div style={{ color: 'red', fontWeight: 'bold' }}>Red bold text</div>
```

### 3.5 Conditional rendering trong JSX

```jsx
function Greeting({ isLoggedIn, username }) {
  return (
    <div>
      {/* Ternary */}
      {isLoggedIn ? <p>Welcome, {username}!</p> : <p>Please log in</p>}

      {/* Logical AND - chỉ render khi true */}
      {isLoggedIn && <button>Logout</button>}

      {/* Logical OR - render khi false/null/undefined */}
      {username || 'Anonymous'}
    </div>
  );
}
```

---

## 4. Components

### 4.1 Component là gì?

Component là **đơn vị cơ bản** của React. Mỗi component là một hàm (hoặc class) nhận input (props) và trả về React elements mô tả giao diện.

### 4.2 Functional Components (Khuyến nghị)

```jsx
// Arrow function
const Welcome = ({ name }) => {
  return <h1>Hello, {name}!</h1>;
};

// Regular function
function Welcome({ name }) {
  return <h1>Hello, {name}!</h1>;
}

// Sử dụng
<Welcome name="React" />
```

### 4.3 Class Components (Legacy)

```jsx
import React, { Component } from 'react';

class Welcome extends Component {
  render() {
    return <h1>Hello, {this.props.name}!</h1>;
  }
}

// Với state
class Counter extends Component {
  constructor(props) {
    super(props);
    this.state = { count: 0 };
  }

  increment = () => {
    this.setState(prev => ({ count: prev.count + 1 }));
  };

  render() {
    return (
      <div>
        <p>Count: {this.state.count}</p>
        <button onClick={this.increment}>+1</button>
      </div>
    );
  }
}
```

### 4.4 Component Composition (Tổ hợp Component)

```jsx
function Header() {
  return <header><h1>My App</h1></header>;
}

function Sidebar() {
  return <aside><nav>Menu items...</nav></aside>;
}

function MainContent() {
  return <main><p>Main content here</p></main>;
}

function Footer() {
  return <footer><p>&copy; 2024</p></footer>;
}

// App kết hợp các component
function App() {
  return (
    <div className="app">
      <Header />
      <div className="content">
        <Sidebar />
        <MainContent />
      </div>
      <Footer />
    </div>
  );
}
```

### 4.5 Children Props

```jsx
function Card({ title, children }) {
  return (
    <div className="card">
      <h2>{title}</h2>
      <div className="card-body">
        {children}
      </div>
    </div>
  );
}

// Sử dụng
<Card title="Thông tin">
  <p>Đây là nội dung bên trong Card</p>
  <button>Click me</button>
</Card>
```

---

## 5. Props - Truyền Dữ liệu

### 5.1 Props là gì?

Props (Properties) là cơ chế truyền dữ liệu từ component cha xuống component con. Props là **read-only** — component con không được phép thay đổi props.

### 5.2 Truyền và nhận Props

```jsx
// Component con
function UserCard({ name, age, email, isActive }) {
  return (
    <div className={`card ${isActive ? 'active' : ''}`}>
      <h3>{name}</h3>
      <p>Tuổi: {age}</p>
      <p>Email: {email}</p>
    </div>
  );
}

// Component cha truyền props
function App() {
  return (
    <UserCard
      name="Nguyễn Văn A"
      age={25}
      email="a@example.com"
      isActive={true}
    />
  );
}
```

### 5.3 Default Props

```jsx
function Button({ text = "Click me", variant = "primary", size = "md" }) {
  return (
    <button className={`btn btn-${variant} btn-${size}`}>
      {text}
    </button>
  );
}

// Sử dụng - có thể bỏ qua props có default
<Button />                          // text="Click me", variant="primary"
<Button text="Submit" />            // text="Submit", variant="primary"
<Button variant="danger" size="lg" text="Delete" />
```

### 5.4 Destructuring Props

```jsx
// Cách 1: Destructure trong parameter
function Profile({ name, avatar, bio }) {
  return (
    <div>
      <img src={avatar} alt={name} />
      <h2>{name}</h2>
      <p>{bio}</p>
    </div>
  );
}

// Cách 2: Spread operator
function Profile(props) {
  const { name, avatar, ...rest } = props;
  return <div {...rest}><img src={avatar} alt={name} /></div>;
}
```

### 5.5 PropTypes (Runtime Type Checking)

```jsx
import PropTypes from 'prop-types';

function UserCard({ name, age, email, friends }) {
  return (/* ... */);
}

UserCard.propTypes = {
  name: PropTypes.string.isRequired,
  age: PropTypes.number,
  email: PropTypes.string.isRequired,
  friends: PropTypes.arrayOf(PropTypes.string),
  onSelect: PropTypes.func,
  status: PropTypes.oneOf(['active', 'inactive', 'pending']),
  config: PropTypes.shape({
    theme: PropTypes.string,
    language: PropTypes.string
  })
};

UserCard.defaultProps = {
  age: 0,
  friends: []
};
```

### 5.6 Props với TypeScript (Khuyến nghị)

```tsx
interface UserCardProps {
  name: string;
  age?: number;          // optional
  email: string;
  friends?: string[];
  onSelect?: (id: string) => void;
  status?: 'active' | 'inactive' | 'pending';
  config?: {
    theme: string;
    language: string;
  };
}

const UserCard: React.FC<UserCardProps> = ({
  name,
  age = 0,
  email,
  friends = [],
  onSelect,
  status = 'active'
}) => {
  return (/* ... */);
};
```

---

## 6. State - Quản lý Trạng thái

### 6.1 State là gì?

State là dữ liệu **nội bộ** của component, có thể thay đổi theo thời gian. Khi state thay đổi, React sẽ **re-render** component đó và các component con.

### 6.2 useState Hook

```jsx
import { useState } from 'react';

function Counter() {
  // [giá trị hiện tại, hàm cập nhật] = useState(giá trị khởi tạo)
  const [count, setCount] = useState(0);

  return (
    <div>
      <p>Count: {count}</p>
      <button onClick={() => setCount(count + 1)}>Tăng</button>
      <button onClick={() => setCount(count - 1)}>Giảm</button>
      <button onClick={() => setCount(0)}>Reset</button>
    </div>
  );
}
```

### 6.3 State với Object và Array

```jsx
function UserForm() {
  const [user, setUser] = useState({
    name: '',
    email: '',
    age: 0
  });

  const [tags, setTags] = useState([]);

  // Cập nhật một field trong object (immutable update)
  const handleChange = (e) => {
    const { name, value } = e.target;
    setUser(prev => ({
      ...prev,           // spread giữ lại các field cũ
      [name]: value      // cập nhật field cần thay đổi
    }));
  };

  // Thêm vào array
  const addTag = (tag) => {
    setTags(prev => [...prev, tag]);
  };

  // Xóa khỏi array
  const removeTag = (index) => {
    setTags(prev => prev.filter((_, i) => i !== index));
  };

  // Cập nhật item trong array
  const updateTag = (index, newValue) => {
    setTags(prev => prev.map((tag, i) => i === index ? newValue : tag));
  };

  return (/* form JSX */);
}
```

### 6.4 Functional Update (Quan trọng)

```jsx
function Counter() {
  const [count, setCount] = useState(0);

  // ❌ Sai - có thể bị "stale state" khi gọi liên tiếp
  const incrementThree = () => {
    setCount(count + 1);  // count = 0, set 1
    setCount(count + 1);  // count vẫn = 0, set 1
    setCount(count + 1);  // count vẫn = 0, set 1
    // Kết quả: count = 1 (không phải 3!)
  };

  // ✅ Đúng - sử dụng functional update
  const incrementThreeCorrect = () => {
    setCount(prev => prev + 1);  // 0 → 1
    setCount(prev => prev + 1);  // 1 → 2
    setCount(prev => prev + 1);  // 2 → 3
    // Kết quả: count = 3
  };

  return (/* ... */);
}
```

### 6.5 Lazy Initialization

```jsx
// ❌ Tính toán nặng mỗi lần render
const [data, setData] = useState(expensiveCalculation());

// ✅ Chỉ tính toán lần đầu (lazy init)
const [data, setData] = useState(() => expensiveCalculation());

// Ví dụ: đọc từ localStorage
const [theme, setTheme] = useState(() => {
  const saved = localStorage.getItem('theme');
  return saved ? JSON.parse(saved) : 'light';
});
```

### 6.6 Batching State Updates (React 18+)

Từ React 18, tất cả state updates đều được **batch** (gộp lại) dù ở đâu:

```jsx
function handleClick() {
  setCount(c => c + 1);    // Không re-render ngay
  setFlag(f => !f);        // Không re-render ngay
  setName('React');        // Không re-render ngay
  // React batch tất cả → chỉ 1 lần re-render
}

// Trước React 18: batch chỉ hoạt động trong event handlers
// React 18+: batch hoạt động mọi nơi (setTimeout, promises, etc.)
```

---

## 7. Event Handling - Xử lý Sự kiện

### 7.1 Cú pháp Event Handler

```jsx
function EventDemo() {
  // Event handler function
  const handleClick = (e) => {
    console.log('Clicked!', e.target);
  };

  const handleMouseEnter = () => {
    console.log('Mouse entered!');
  };

  return (
    <div>
      {/* Truyền reference, KHÔNG gọi hàm */}
      <button onClick={handleClick}>Click me</button>

      {/* ❌ Sai - gọi ngay lập tức */}
      <button onClick={handleClick()}>Wrong!</button>

      {/* Truyền argument */}
      <button onClick={() => handleClick('custom')}>With arg</button>

      <div onMouseEnter={handleMouseEnter}>Hover me</div>
    </div>
  );
}
```

### 7.2 Synthetic Events

React wrap native events thành **Synthetic Events** (SyntheticEvent) để đảm bảo consistency cross-browser:

```jsx
function FormDemo() {
  const handleSubmit = (e) => {
    e.preventDefault();  // Ngăn form submit mặc định
    console.log('Form submitted');
  };

  const handleChange = (e) => {
    console.log(e.target.name, e.target.value);
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter') {
      console.log('Enter pressed');
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        name="email"
        onChange={handleChange}
        onKeyDown={handleKeyDown}
        onFocus={() => console.log('focused')}
        onBlur={() => console.log('blurred')}
      />
      <button type="submit">Submit</button>
    </form>
  );
}
```

### 7.3 Các sự kiện phổ biến

| Loại | Events |
|------|--------|
| Mouse | onClick, onDoubleClick, onMouseEnter, onMouseLeave, onMouseMove |
| Keyboard | onKeyDown, onKeyUp, onKeyPress |
| Form | onChange, onSubmit, onFocus, onBlur, onInput |
| Touch | onTouchStart, onTouchMove, onTouchEnd |
| Drag | onDrag, onDragStart, onDragEnd, onDrop |
| Scroll | onScroll |
| Clipboard | onCopy, onCut, onPaste |

### 7.4 Event Delegation

React sử dụng **event delegation** — tất cả events được gắn vào root element, không phải từng DOM node:

```jsx
// Dù có 1000 items, React chỉ gắn 1 event listener ở root
function TodoList({ items }) {
  const handleItemClick = (id) => {
    console.log('Clicked item:', id);
  };

  return (
    <ul>
      {items.map(item => (
        <li key={item.id} onClick={() => handleItemClick(item.id)}>
          {item.text}
        </li>
      ))}
    </ul>
  );
}
```

---

## 8. Conditional Rendering

### 8.1 if/else

```jsx
function Greeting({ isLoggedIn }) {
  if (isLoggedIn) {
    return <h1>Welcome back!</h1>;
  }
  return <h1>Please sign in.</h1>;
}
```

### 8.2 Ternary Operator

```jsx
function StatusBadge({ status }) {
  return (
    <span className={`badge badge-${status}`}>
      {status === 'active' ? '🟢 Active' : '🔴 Inactive'}
    </span>
  );
}
```

### 8.3 Logical AND (&&)

```jsx
function Notification({ messages }) {
  return (
    <div>
      {messages.length > 0 && (
        <p>You have {messages.length} unread messages.</p>
      )}
    </div>
  );
}

// ⚠️ Cẩn thận với falsy values
// {0 && <Component />} sẽ render "0" trên màn hình!
// Fix: {count > 0 && <Component />}
```

### 8.4 Switch/Case Pattern

```jsx
function StatusIcon({ status }) {
  switch (status) {
    case 'loading':
      return <Spinner />;
    case 'error':
      return <ErrorMessage />;
    case 'success':
      return <SuccessMessage />;
    default:
      return null;
  }
}

// Hoặc dùng object lookup
const STATUS_COMPONENTS = {
  loading: Spinner,
  error: ErrorMessage,
  success: SuccessMessage,
};

function StatusIcon({ status }) {
  const Component = STATUS_COMPONENTS[status];
  return Component ? <Component /> : null;
}
```

### 8.5 Render null (Ẩn component)

```jsx
function WarningBanner({ show, message }) {
  if (!show) {
    return null;  // Không render gì cả
  }
  return <div className="warning">{message}</div>;
}
```

---

## 9. Lists và Keys

### 9.1 Render danh sách

```jsx
function TodoList({ todos }) {
  return (
    <ul>
      {todos.map(todo => (
        <li key={todo.id}>
          <span>{todo.text}</span>
          <span>{todo.completed ? '✅' : '⬜'}</span>
        </li>
      ))}
    </ul>
  );
}
```

### 9.2 Key - Tại sao quan trọng?

Key giúp React **xác định** element nào đã thay đổi, thêm mới, hoặc bị xóa. Điều này giúp React cập nhật DOM hiệu quả.

```jsx
// ❌ Sai - dùng index làm key (gây bug khi reorder/delete)
{items.map((item, index) => (
  <li key={index}>{item.name}</li>
))}

// ✅ Đúng - dùng unique ID
{items.map(item => (
  <li key={item.id}>{item.name}</li>
))}
```

**Khi nào dùng index làm key được?**
- Danh sách tĩnh, không bao giờ thay đổi thứ tự
- Không có thêm/xóa
- Items không có unique ID

### 9.3 Ví dụ phức tạp

```jsx
function ProductGrid({ products, onAddToCart }) {
  if (products.length === 0) {
    return <p>Không có sản phẩm nào.</p>;
  }

  return (
    <div className="grid">
      {products.map(product => (
        <ProductCard
          key={product.id}
          name={product.name}
          price={product.price}
          image={product.image}
          onAdd={() => onAddToCart(product.id)}
        />
      ))}
    </div>
  );
}
```

---

## 10. Forms và Controlled Components

### 10.1 Controlled Component

Component mà giá trị của nó được **điều khiển hoàn toàn** bởi React state:

```jsx
function LoginForm() {
  const [formData, setFormData] = useState({
    email: '',
    password: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Submitting:', formData);
    // Call API
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="email"
        name="email"
        value={formData.email}
        onChange={handleChange}
        placeholder="Email"
      />
      <input
        type="password"
        name="password"
        value={formData.password}
        onChange={handleChange}
        placeholder="Password"
      />
      <button type="submit">Login</button>
    </form>
  );
}
```

### 10.2 Uncontrolled Component

Sử dụng `useRef` để truy cập giá trị DOM trực tiếp:

```jsx
function FileUpload() {
  const fileInputRef = useRef(null);

  const handleSubmit = (e) => {
    e.preventDefault();
    const file = fileInputRef.current.files[0];
    console.log('Selected file:', file.name);
  };

  return (
    <form onSubmit={handleSubmit}>
      <input type="file" ref={fileInputRef} />
      <button type="submit">Upload</button>
    </form>
  );
}
```

### 10.3 Các loại form elements

```jsx
function FullForm() {
  const [form, setForm] = useState({
    name: '',
    gender: 'male',
    hobbies: [],
    country: '',
    bio: '',
    agreed: false
  });

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setForm(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  const handleCheckboxGroup = (e) => {
    const { value, checked } = e.target;
    setForm(prev => ({
      ...prev,
      hobbies: checked
        ? [...prev.hobbies, value]
        : prev.hobbies.filter(h => h !== value)
    }));
  };

  return (
    <form>
      {/* Text Input */}
      <input name="name" value={form.name} onChange={handleChange} />

      {/* Radio Buttons */}
      <label>
        <input type="radio" name="gender" value="male"
          checked={form.gender === 'male'} onChange={handleChange} />
        Nam
      </label>
      <label>
        <input type="radio" name="gender" value="female"
          checked={form.gender === 'female'} onChange={handleChange} />
        Nữ
      </label>

      {/* Checkbox Group */}
      <label>
        <input type="checkbox" value="reading"
          checked={form.hobbies.includes('reading')}
          onChange={handleCheckboxGroup} />
        Đọc sách
      </label>

      {/* Select */}
      <select name="country" value={form.country} onChange={handleChange}>
        <option value="">Chọn quốc gia</option>
        <option value="vn">Việt Nam</option>
        <option value="us">Mỹ</option>
      </select>

      {/* Textarea */}
      <textarea name="bio" value={form.bio} onChange={handleChange} />

      {/* Single Checkbox */}
      <label>
        <input type="checkbox" name="agreed"
          checked={form.agreed} onChange={handleChange} />
        Đồng ý điều khoản
      </label>
    </form>
  );
}
```

### 10.4 Form Validation

```jsx
function RegistrationForm() {
  const [form, setForm] = useState({ email: '', password: '' });
  const [errors, setErrors] = useState({});

  const validate = () => {
    const newErrors = {};
    if (!form.email) {
      newErrors.email = 'Email là bắt buộc';
    } else if (!/\S+@\S+\.\S+/.test(form.email)) {
      newErrors.email = 'Email không hợp lệ';
    }
    if (!form.password) {
      newErrors.password = 'Mật khẩu là bắt buộc';
    } else if (form.password.length < 8) {
      newErrors.password = 'Mật khẩu phải ít nhất 8 ký tự';
    }
    return newErrors;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const newErrors = validate();
    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
    } else {
      setErrors({});
      // Submit form
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <input
          type="email"
          value={form.email}
          onChange={e => setForm(prev => ({ ...prev, email: e.target.value }))}
        />
        {errors.email && <span className="error">{errors.email}</span>}
      </div>
      <div>
        <input
          type="password"
          value={form.password}
          onChange={e => setForm(prev => ({ ...prev, password: e.target.value }))}
        />
        {errors.password && <span className="error">{errors.password}</span>}
      </div>
      <button type="submit">Đăng ký</button>
    </form>
  );
}
```

---

## 11. React Hooks

### 11.1 Hooks là gì?

Hooks là các hàm đặc biệt cho phép bạn "hook into" (móc vào) các tính năng của React (state, lifecycle, context...) từ **functional components**. Hooks được giới thiệu từ React 16.8.

### 11.2 Quy tắc của Hooks

**1. Chỉ gọi Hooks ở top level:**
```jsx
// ❌ Sai
if (condition) {
  const [state, setState] = useState(0);
}

// ❌ Sai
for (let i = 0; i < 5; i++) {
  useEffect(() => {});
}

// ✅ Đúng - luôn ở top level
function Component() {
  const [state, setState] = useState(0);
  useEffect(() => {}, []);
  // ...
}
```

**2. Chỉ gọi Hooks trong React functions:**
- Functional components
- Custom hooks (bắt đầu bằng "use")

### 11.3 Tổng quan các Built-in Hooks

| Hook | Mục đích |
|------|----------|
| useState | Quản lý state cơ bản |
| useEffect | Side effects (API calls, subscriptions) |
| useContext | Đọc context value |
| useRef | Tham chiếu DOM hoặc giá trị persist |
| useMemo | Memoize giá trị tính toán nặng |
| useCallback | Memoize function reference |
| useReducer | State phức tạp với reducer pattern |
| useId | Tạo unique ID cho accessibility |
| useTransition | Đánh dấu state update là non-urgent |
| useDeferredValue | Defer re-render cho giá trị |

---

## 12. useEffect và Side Effects

### 12.1 useEffect cơ bản

```jsx
import { useState, useEffect } from 'react';

function UserProfile({ userId }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Side effect: fetch data
    setLoading(true);
    fetch(`/api/users/${userId}`)
      .then(res => res.json())
      .then(data => {
        setUser(data);
        setLoading(false);
      })
      .catch(err => {
        console.error(err);
        setLoading(false);
      });
  }, [userId]); // Dependency array - chạy lại khi userId thay đổi

  if (loading) return <p>Loading...</p>;
  if (!user) return <p>User not found</p>;
  return <h1>{user.name}</h1>;
}
```

### 12.2 Dependency Array

```jsx
// Chạy SAU MỖI render (không có dependency array)
useEffect(() => {
  console.log('Chạy mỗi render');
});

// Chạy CHỈ MỘT LẦN sau mount (dependency array rỗng)
useEffect(() => {
  console.log('Chỉ chạy 1 lần');
}, []);

// Chạy khi dependency thay đổi
useEffect(() => {
  console.log('count hoặc name đã thay đổi');
}, [count, name]);
```

### 12.3 Cleanup Function

```jsx
function Timer() {
  const [seconds, setSeconds] = useState(0);

  useEffect(() => {
    const interval = setInterval(() => {
      setSeconds(s => s + 1);
    }, 1000);

    // Cleanup: chạy khi unmount hoặc trước khi effect chạy lại
    return () => {
      clearInterval(interval);
    };
  }, []);

  return <p>Seconds: {seconds}</p>;
}

// WebSocket example
function ChatRoom({ roomId }) {
  useEffect(() => {
    const ws = new WebSocket(`wss://chat.example.com/${roomId}`);

    ws.onmessage = (event) => {
      console.log('New message:', event.data);
    };

    // Cleanup khi roomId thay đổi hoặc unmount
    return () => {
      ws.close();
    };
  }, [roomId]);

  return <div>Chat room: {roomId}</div>;
}
```

### 12.4 Pattern: Data Fetching với Async

```jsx
function Products() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    let cancelled = false;  // Avoid race condition

    async function fetchProducts() {
      try {
        setLoading(true);
        const response = await fetch('/api/products');
        if (!response.ok) throw new Error('Failed to fetch');
        const data = await response.json();

        if (!cancelled) {
          setProducts(data);
          setError(null);
        }
      } catch (err) {
        if (!cancelled) {
          setError(err.message);
        }
      } finally {
        if (!cancelled) {
          setLoading(false);
        }
      }
    }

    fetchProducts();

    return () => { cancelled = true; };  // Cleanup
  }, []);

  if (loading) return <Spinner />;
  if (error) return <ErrorMessage message={error} />;
  return <ProductList products={products} />;
}
```

---

## 13. useRef và DOM Manipulation

### 13.1 useRef cơ bản

`useRef` trả về một object `{ current: value }` persist qua các lần render mà **không gây re-render** khi thay đổi.

```jsx
import { useRef, useEffect } from 'react';

function TextInput() {
  const inputRef = useRef(null);

  useEffect(() => {
    // Auto-focus khi mount
    inputRef.current.focus();
  }, []);

  const handleButtonClick = () => {
    inputRef.current.focus();
    inputRef.current.select();
  };

  return (
    <div>
      <input ref={inputRef} type="text" placeholder="Type here..." />
      <button onClick={handleButtonClick}>Focus Input</button>
    </div>
  );
}
```

### 13.2 useRef lưu giá trị (không re-render)

```jsx
function StopWatch() {
  const [time, setTime] = useState(0);
  const [isRunning, setIsRunning] = useState(false);
  const intervalRef = useRef(null);  // Lưu interval ID

  const start = () => {
    setIsRunning(true);
    intervalRef.current = setInterval(() => {
      setTime(t => t + 1);
    }, 1000);
  };

  const stop = () => {
    setIsRunning(false);
    clearInterval(intervalRef.current);
  };

  const reset = () => {
    stop();
    setTime(0);
  };

  useEffect(() => {
    return () => clearInterval(intervalRef.current);
  }, []);

  return (
    <div>
      <p>{time}s</p>
      <button onClick={isRunning ? stop : start}>
        {isRunning ? 'Stop' : 'Start'}
      </button>
      <button onClick={reset}>Reset</button>
    </div>
  );
}
```

### 13.3 Previous Value Pattern

```jsx
function usePrevious(value) {
  const ref = useRef();
  useEffect(() => {
    ref.current = value;
  });
  return ref.current;
}

function Counter() {
  const [count, setCount] = useState(0);
  const prevCount = usePrevious(count);

  return (
    <p>
      Now: {count}, Before: {prevCount}
    </p>
  );
}
```

### 13.4 forwardRef - Truyền ref xuống component con

```jsx
import { forwardRef, useRef } from 'react';

// Component con nhận ref từ cha
const FancyInput = forwardRef((props, ref) => {
  return (
    <input
      ref={ref}
      className="fancy-input"
      {...props}
    />
  );
});

// Component cha
function Form() {
  const inputRef = useRef(null);

  const handleClick = () => {
    inputRef.current.focus();
  };

  return (
    <div>
      <FancyInput ref={inputRef} placeholder="Enter text" />
      <button onClick={handleClick}>Focus</button>
    </div>
  );
}
```

---

## 14. useMemo và useCallback

### 14.1 useMemo - Memoize computed values

```jsx
import { useMemo } from 'react';

function ExpensiveList({ items, filter }) {
  // Chỉ tính lại khi items hoặc filter thay đổi
  const filteredItems = useMemo(() => {
    console.log('Computing filtered items...');
    return items.filter(item =>
      item.name.toLowerCase().includes(filter.toLowerCase())
    );
  }, [items, filter]);

  const stats = useMemo(() => ({
    total: items.length,
    filtered: filteredItems.length,
    avgPrice: filteredItems.reduce((sum, i) => sum + i.price, 0) / filteredItems.length
  }), [items, filteredItems]);

  return (
    <div>
      <p>Showing {stats.filtered} of {stats.total} items</p>
      {filteredItems.map(item => <ItemCard key={item.id} item={item} />)}
    </div>
  );
}
```

### 14.2 useCallback - Memoize function references

```jsx
import { useCallback, useState } from 'react';

function Parent() {
  const [count, setCount] = useState(0);
  const [text, setText] = useState('');

  // Hàm này sẽ có reference mới mỗi render → con re-render không cần thiết
  // const handleClick = () => { setCount(c => c + 1); };

  // ✅ Memoize - reference ổn định
  const handleClick = useCallback(() => {
    setCount(c => c + 1);
  }, []);

  const handleTextChange = useCallback((newText) => {
    setText(newText);
  }, []);

  return (
    <div>
      <p>Count: {count}</p>
      <ExpensiveChild onClick={handleClick} />
      <TextInput value={text} onChange={handleTextChange} />
    </div>
  );
}

// Chỉ re-render khi props thực sự thay đổi
const ExpensiveChild = React.memo(({ onClick }) => {
  console.log('ExpensiveChild rendered');
  return <button onClick={onClick}>Increment</button>;
});
```

### 14.3 Khi nào dùng / không dùng

```jsx
// ❌ KHÔNG cần memoize
const simple = useMemo(() => a + b, [a, b]);  // phép tính đơn giản
const handler = useCallback(() => setOpen(true), []);  // không truyền xuống memo child

// ✅ NÊN memoize
const sorted = useMemo(() =>
  [...items].sort((a, b) => a.price - b.price),  // sort array lớn
  [items]
);

const onSubmit = useCallback(async (data) => {
  await api.post('/submit', data);
}, []);  // Truyền xuống React.memo child
```

---

## 15. useReducer - State phức tạp

### 15.1 Cú pháp cơ bản

```jsx
import { useReducer } from 'react';

// Reducer function (pure function)
function counterReducer(state, action) {
  switch (action.type) {
    case 'INCREMENT':
      return { count: state.count + 1 };
    case 'DECREMENT':
      return { count: state.count - 1 };
    case 'RESET':
      return { count: 0 };
    case 'SET':
      return { count: action.payload };
    default:
      throw new Error(`Unknown action: ${action.type}`);
  }
}

function Counter() {
  const [state, dispatch] = useReducer(counterReducer, { count: 0 });

  return (
    <div>
      <p>Count: {state.count}</p>
      <button onClick={() => dispatch({ type: 'INCREMENT' })}>+</button>
      <button onClick={() => dispatch({ type: 'DECREMENT' })}>-</button>
      <button onClick={() => dispatch({ type: 'RESET' })}>Reset</button>
      <button onClick={() => dispatch({ type: 'SET', payload: 100 })}>Set 100</button>
    </div>
  );
}
```

### 15.2 Ví dụ thực tế - Todo App

```jsx
function todoReducer(state, action) {
  switch (action.type) {
    case 'ADD_TODO':
      return [
        ...state,
        { id: Date.now(), text: action.payload, completed: false }
      ];
    case 'TOGGLE_TODO':
      return state.map(todo =>
        todo.id === action.payload
          ? { ...todo, completed: !todo.completed }
          : todo
      );
    case 'DELETE_TODO':
      return state.filter(todo => todo.id !== action.payload);
    case 'EDIT_TODO':
      return state.map(todo =>
        todo.id === action.payload.id
          ? { ...todo, text: action.payload.text }
          : todo
      );
    case 'CLEAR_COMPLETED':
      return state.filter(todo => !todo.completed);
    default:
      return state;
  }
}

function TodoApp() {
  const [todos, dispatch] = useReducer(todoReducer, []);
  const [input, setInput] = useState('');

  const handleAdd = () => {
    if (input.trim()) {
      dispatch({ type: 'ADD_TODO', payload: input.trim() });
      setInput('');
    }
  };

  return (
    <div>
      <input value={input} onChange={e => setInput(e.target.value)} />
      <button onClick={handleAdd}>Add</button>

      {todos.map(todo => (
        <div key={todo.id}>
          <span
            style={{ textDecoration: todo.completed ? 'line-through' : 'none' }}
            onClick={() => dispatch({ type: 'TOGGLE_TODO', payload: todo.id })}
          >
            {todo.text}
          </span>
          <button onClick={() => dispatch({ type: 'DELETE_TODO', payload: todo.id })}>
            ❌
          </button>
        </div>
      ))}

      <button onClick={() => dispatch({ type: 'CLEAR_COMPLETED' })}>
        Clear Completed
      </button>
    </div>
  );
}
```

### 15.3 useState vs useReducer

| Tiêu chí | useState | useReducer |
|-----------|----------|------------|
| State đơn giản | ✅ Phù hợp | Quá phức tạp |
| State phức tạp | Khó quản lý | ✅ Phù hợp |
| Nhiều sub-values | Nhiều useState | ✅ 1 reducer |
| Logic phụ thuộc state trước | Functional update | ✅ Rõ ràng hơn |
| Testing | Khó test riêng | ✅ Test reducer dễ |
| Sharing logic | Không | ✅ Tái sử dụng reducer |

---

## 16. Context API - Global State

### 16.1 Vấn đề Prop Drilling

```jsx
// ❌ Prop drilling - truyền props qua nhiều tầng
function App() {
  const [theme, setTheme] = useState('light');
  return <Layout theme={theme} setTheme={setTheme} />;
}
function Layout({ theme, setTheme }) {
  return <Header theme={theme} setTheme={setTheme} />;
}
function Header({ theme, setTheme }) {
  return <ThemeToggle theme={theme} setTheme={setTheme} />;
}
// Props phải "drill" qua Layout và Header dù chúng không dùng
```

### 16.2 Tạo và sử dụng Context

```jsx
import { createContext, useContext, useState } from 'react';

// 1. Tạo Context
const ThemeContext = createContext(null);

// 2. Tạo Provider Component
function ThemeProvider({ children }) {
  const [theme, setTheme] = useState('light');

  const toggleTheme = () => {
    setTheme(prev => prev === 'light' ? 'dark' : 'light');
  };

  const value = { theme, toggleTheme };

  return (
    <ThemeContext.Provider value={value}>
      {children}
    </ThemeContext.Provider>
  );
}

// 3. Custom hook để sử dụng context
function useTheme() {
  const context = useContext(ThemeContext);
  if (!context) {
    throw new Error('useTheme must be used within ThemeProvider');
  }
  return context;
}

// 4. Sử dụng ở bất kỳ đâu (không cần prop drilling)
function ThemeToggle() {
  const { theme, toggleTheme } = useTheme();
  return (
    <button onClick={toggleTheme}>
      Current: {theme} | Switch to {theme === 'light' ? 'dark' : 'light'}
    </button>
  );
}

// 5. Wrap App với Provider
function App() {
  return (
    <ThemeProvider>
      <Layout />
    </ThemeProvider>
  );
}
```

### 16.3 Multiple Contexts

```jsx
// Auth Context
const AuthContext = createContext(null);

function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const login = async (credentials) => { /* ... */ };
  const logout = () => setUser(null);

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

// Notification Context
const NotificationContext = createContext(null);

// Combine providers
function App() {
  return (
    <AuthProvider>
      <ThemeProvider>
        <NotificationProvider>
          <Router />
        </NotificationProvider>
      </ThemeProvider>
    </AuthProvider>
  );
}
```

### 16.4 Context + useReducer Pattern

```jsx
const CartContext = createContext(null);

function cartReducer(state, action) {
  switch (action.type) {
    case 'ADD_ITEM':
      const existing = state.items.find(i => i.id === action.payload.id);
      if (existing) {
        return {
          ...state,
          items: state.items.map(i =>
            i.id === action.payload.id
              ? { ...i, quantity: i.quantity + 1 }
              : i
          )
        };
      }
      return {
        ...state,
        items: [...state.items, { ...action.payload, quantity: 1 }]
      };
    case 'REMOVE_ITEM':
      return {
        ...state,
        items: state.items.filter(i => i.id !== action.payload)
      };
    case 'CLEAR_CART':
      return { ...state, items: [] };
    default:
      return state;
  }
}

function CartProvider({ children }) {
  const [state, dispatch] = useReducer(cartReducer, { items: [] });

  const totalPrice = useMemo(
    () => state.items.reduce((sum, item) => sum + item.price * item.quantity, 0),
    [state.items]
  );

  return (
    <CartContext.Provider value={{ ...state, totalPrice, dispatch }}>
      {children}
    </CartContext.Provider>
  );
}

function useCart() {
  const context = useContext(CartContext);
  if (!context) throw new Error('useCart must be used within CartProvider');
  return context;
}
```

---

## 17. Custom Hooks

### 17.1 Custom Hooks là gì?

Custom Hook là một hàm JavaScript bắt đầu bằng `use`, có thể sử dụng các hooks khác bên trong. Dùng để **tái sử dụng logic stateful** giữa các components.

### 17.2 Ví dụ: useLocalStorage

```jsx
function useLocalStorage(key, initialValue) {
  const [value, setValue] = useState(() => {
    try {
      const item = localStorage.getItem(key);
      return item ? JSON.parse(item) : initialValue;
    } catch {
      return initialValue;
    }
  });

  useEffect(() => {
    localStorage.setItem(key, JSON.stringify(value));
  }, [key, value]);

  return [value, setValue];
}

// Sử dụng
function Settings() {
  const [theme, setTheme] = useLocalStorage('theme', 'light');
  const [fontSize, setFontSize] = useLocalStorage('fontSize', 16);
  // ...
}
```

### 17.3 Ví dụ: useFetch

```jsx
function useFetch(url, options = {}) {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    let cancelled = false;
    const controller = new AbortController();

    async function fetchData() {
      try {
        setLoading(true);
        setError(null);
        const response = await fetch(url, {
          ...options,
          signal: controller.signal
        });
        if (!response.ok) throw new Error(`HTTP ${response.status}`);
        const json = await response.json();
        if (!cancelled) setData(json);
      } catch (err) {
        if (!cancelled && err.name !== 'AbortError') {
          setError(err.message);
        }
      } finally {
        if (!cancelled) setLoading(false);
      }
    }

    fetchData();

    return () => {
      cancelled = true;
      controller.abort();
    };
  }, [url]);

  return { data, loading, error };
}

// Sử dụng
function UserList() {
  const { data: users, loading, error } = useFetch('/api/users');

  if (loading) return <Spinner />;
  if (error) return <Error message={error} />;
  return <ul>{users.map(u => <li key={u.id}>{u.name}</li>)}</ul>;
}
```

### 17.4 Ví dụ: useDebounce

```jsx
function useDebounce(value, delay = 500) {
  const [debouncedValue, setDebouncedValue] = useState(value);

  useEffect(() => {
    const timer = setTimeout(() => {
      setDebouncedValue(value);
    }, delay);

    return () => clearTimeout(timer);
  }, [value, delay]);

  return debouncedValue;
}

// Sử dụng cho search
function SearchBar() {
  const [query, setQuery] = useState('');
  const debouncedQuery = useDebounce(query, 300);
  const { data: results } = useFetch(
    debouncedQuery ? `/api/search?q=${debouncedQuery}` : null
  );

  return (
    <div>
      <input
        value={query}
        onChange={e => setQuery(e.target.value)}
        placeholder="Search..."
      />
      {results && results.map(r => <div key={r.id}>{r.title}</div>)}
    </div>
  );
}
```

### 17.5 Ví dụ: useToggle, useWindowSize, useOnClickOutside

```jsx
// useToggle
function useToggle(initial = false) {
  const [value, setValue] = useState(initial);
  const toggle = useCallback(() => setValue(v => !v), []);
  const setTrue = useCallback(() => setValue(true), []);
  const setFalse = useCallback(() => setValue(false), []);
  return { value, toggle, setTrue, setFalse };
}

// useWindowSize
function useWindowSize() {
  const [size, setSize] = useState({
    width: window.innerWidth,
    height: window.innerHeight
  });

  useEffect(() => {
    const handleResize = () => {
      setSize({ width: window.innerWidth, height: window.innerHeight });
    };
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  return size;
}

// useOnClickOutside
function useOnClickOutside(ref, handler) {
  useEffect(() => {
    const listener = (event) => {
      if (!ref.current || ref.current.contains(event.target)) return;
      handler(event);
    };
    document.addEventListener('mousedown', listener);
    document.addEventListener('touchstart', listener);
    return () => {
      document.removeEventListener('mousedown', listener);
      document.removeEventListener('touchstart', listener);
    };
  }, [ref, handler]);
}
```

---

## 18. React Router

### 18.1 Cài đặt

```bash
npm install react-router-dom
```

### 18.2 Cấu hình Routes cơ bản

```jsx
import { BrowserRouter, Routes, Route, Link, NavLink } from 'react-router-dom';

function App() {
  return (
    <BrowserRouter>
      <nav>
        <NavLink to="/" className={({ isActive }) => isActive ? 'active' : ''}>
          Home
        </NavLink>
        <NavLink to="/about">About</NavLink>
        <NavLink to="/products">Products</NavLink>
      </nav>

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/products" element={<Products />} />
        <Route path="/products/:id" element={<ProductDetail />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
}
```

### 18.3 Dynamic Routes và useParams

```jsx
import { useParams, useNavigate, useSearchParams } from 'react-router-dom';

function ProductDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useSearchParams();

  const tab = searchParams.get('tab') || 'overview';

  return (
    <div>
      <h1>Product #{id}</h1>
      <button onClick={() => navigate(-1)}>← Back</button>
      <button onClick={() => navigate('/products')}>All Products</button>
      <button onClick={() => setSearchParams({ tab: 'reviews' })}>
        Reviews
      </button>
    </div>
  );
}
```

### 18.4 Nested Routes

```jsx
function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<Home />} />
        <Route path="dashboard" element={<Dashboard />}>
          <Route index element={<DashboardOverview />} />
          <Route path="analytics" element={<Analytics />} />
          <Route path="settings" element={<Settings />} />
        </Route>
        <Route path="*" element={<NotFound />} />
      </Route>
    </Routes>
  );
}

// Layout component với Outlet
import { Outlet } from 'react-router-dom';

function Layout() {
  return (
    <div>
      <Header />
      <main>
        <Outlet /> {/* Render child route */}
      </main>
      <Footer />
    </div>
  );
}
```

### 18.5 Protected Routes

```jsx
import { Navigate, useLocation } from 'react-router-dom';

function ProtectedRoute({ children }) {
  const { user } = useAuth();
  const location = useLocation();

  if (!user) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  return children;
}

// Sử dụng
<Routes>
  <Route path="/login" element={<Login />} />
  <Route path="/dashboard" element={
    <ProtectedRoute>
      <Dashboard />
    </ProtectedRoute>
  } />
</Routes>
```

---

## 19. State Management với Redux

### 19.1 Redux là gì?

Redux là thư viện quản lý state theo mô hình **unidirectional data flow**:
```
Action → Dispatch → Reducer → Store → UI → Action...
```

### 19.2 Redux Toolkit (Cách hiện đại)

```bash
npm install @reduxjs/toolkit react-redux
```

### 19.3 Tạo Slice

```jsx
// store/counterSlice.js
import { createSlice } from '@reduxjs/toolkit';

const counterSlice = createSlice({
  name: 'counter',
  initialState: { value: 0, history: [] },
  reducers: {
    increment: (state) => {
      state.value += 1;  // Immer cho phép "mutate" trực tiếp
      state.history.push(state.value);
    },
    decrement: (state) => {
      state.value -= 1;
      state.history.push(state.value);
    },
    incrementByAmount: (state, action) => {
      state.value += action.payload;
      state.history.push(state.value);
    },
    reset: (state) => {
      state.value = 0;
      state.history = [];
    }
  }
});

export const { increment, decrement, incrementByAmount, reset } = counterSlice.actions;
export default counterSlice.reducer;
```

### 19.4 Tạo Store

```jsx
// store/index.js
import { configureStore } from '@reduxjs/toolkit';
import counterReducer from './counterSlice';
import todosReducer from './todosSlice';
import userReducer from './userSlice';

export const store = configureStore({
  reducer: {
    counter: counterReducer,
    todos: todosReducer,
    user: userReducer,
  },
});
```

### 19.5 Kết nối với React

```jsx
// main.jsx
import { Provider } from 'react-redux';
import { store } from './store';

ReactDOM.createRoot(document.getElementById('root')).render(
  <Provider store={store}>
    <App />
  </Provider>
);

// Sử dụng trong component
import { useSelector, useDispatch } from 'react-redux';
import { increment, decrement, incrementByAmount } from './store/counterSlice';

function Counter() {
  const count = useSelector(state => state.counter.value);
  const history = useSelector(state => state.counter.history);
  const dispatch = useDispatch();

  return (
    <div>
      <p>Count: {count}</p>
      <button onClick={() => dispatch(increment())}>+1</button>
      <button onClick={() => dispatch(decrement())}>-1</button>
      <button onClick={() => dispatch(incrementByAmount(5))}>+5</button>
      <p>History: {history.join(', ')}</p>
    </div>
  );
}
```

### 19.6 Async Thunks (API calls)

```jsx
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';

// Async action
export const fetchUsers = createAsyncThunk(
  'users/fetchAll',
  async (_, { rejectWithValue }) => {
    try {
      const response = await fetch('/api/users');
      if (!response.ok) throw new Error('Failed');
      return await response.json();
    } catch (err) {
      return rejectWithValue(err.message);
    }
  }
);

const usersSlice = createSlice({
  name: 'users',
  initialState: { list: [], loading: false, error: null },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchUsers.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchUsers.fulfilled, (state, action) => {
        state.loading = false;
        state.list = action.payload;
      })
      .addCase(fetchUsers.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      });
  }
});
```

---

## 20. Performance Optimization

### 20.1 React.memo

```jsx
// Chỉ re-render khi props thay đổi (shallow comparison)
const ExpensiveList = React.memo(function ExpensiveList({ items, onSelect }) {
  console.log('ExpensiveList rendered');
  return (
    <ul>
      {items.map(item => (
        <li key={item.id} onClick={() => onSelect(item.id)}>
          {item.name}
        </li>
      ))}
    </ul>
  );
});

// Custom comparison function
const MemoizedComponent = React.memo(MyComponent, (prevProps, nextProps) => {
  // Return true nếu KHÔNG cần re-render
  return prevProps.id === nextProps.id && prevProps.name === nextProps.name;
});
```

### 20.2 Code Splitting với React.lazy

```jsx
import { lazy, Suspense } from 'react';

// Dynamic import - chỉ load khi cần
const Dashboard = lazy(() => import('./pages/Dashboard'));
const Settings = lazy(() => import('./pages/Settings'));
const Analytics = lazy(() => import('./pages/Analytics'));

function App() {
  return (
    <Suspense fallback={<LoadingSpinner />}>
      <Routes>
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/settings" element={<Settings />} />
        <Route path="/analytics" element={<Analytics />} />
      </Routes>
    </Suspense>
  );
}
```

### 20.3 Virtualization cho danh sách lớn

```jsx
// Sử dụng react-window cho list 10,000+ items
import { FixedSizeList } from 'react-window';

function VirtualList({ items }) {
  const Row = ({ index, style }) => (
    <div style={style} className="row">
      {items[index].name}
    </div>
  );

  return (
    <FixedSizeList
      height={400}
      width="100%"
      itemCount={items.length}
      itemSize={50}
    >
      {Row}
    </FixedSizeList>
  );
}
```

### 20.4 Profiler

```jsx
import { Profiler } from 'react';

function onRenderCallback(id, phase, actualDuration) {
  console.log(`${id} ${phase}: ${actualDuration}ms`);
}

function App() {
  return (
    <Profiler id="App" onRender={onRenderCallback}>
      <Header />
      <MainContent />
    </Profiler>
  );
}
```

### 20.5 Tối ưu hóa Checklist

| Kỹ thuật | Khi nào dùng |
|----------|-------------|
| React.memo | Component render expensive, props ít thay đổi |
| useMemo | Tính toán nặng dựa trên dependencies |
| useCallback | Function truyền xuống memo children |
| Code splitting | Route-based, feature-based |
| Virtualization | Lists > 100 items |
| Debounce | Input search, resize handlers |
| Lazy loading images | Nhiều hình ảnh ngoài viewport |

---

## 21. Error Boundaries

### 21.1 Error Boundary là gì?

Error Boundary là component **catch JavaScript errors** trong component tree con, hiển thị fallback UI thay vì crash toàn app.

### 21.2 Class-based Error Boundary

```jsx
class ErrorBoundary extends React.Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false, error: null };
  }

  static getDerivedStateFromError(error) {
    return { hasError: true, error };
  }

  componentDidCatch(error, errorInfo) {
    // Log error to service
    console.error('Error caught:', error, errorInfo);
  }

  render() {
    if (this.state.hasError) {
      return this.props.fallback || (
        <div className="error-fallback">
          <h2>Something went wrong</h2>
          <p>{this.state.error?.message}</p>
          <button onClick={() => this.setState({ hasError: false })}>
            Try Again
          </button>
        </div>
      );
    }
    return this.props.children;
  }
}

// Sử dụng
function App() {
  return (
    <ErrorBoundary fallback={<p>App crashed!</p>}>
      <Header />
      <ErrorBoundary fallback={<p>Content failed</p>}>
        <MainContent />
      </ErrorBoundary>
      <Footer />
    </ErrorBoundary>
  );
}
```

### 21.3 react-error-boundary (Library hiện đại)

```jsx
import { ErrorBoundary } from 'react-error-boundary';

function ErrorFallback({ error, resetErrorBoundary }) {
  return (
    <div role="alert">
      <h2>Something went wrong:</h2>
      <pre>{error.message}</pre>
      <button onClick={resetErrorBoundary}>Try again</button>
    </div>
  );
}

function App() {
  return (
    <ErrorBoundary
      FallbackComponent={ErrorFallback}
      onReset={() => { /* reset state */ }}
      onError={(error, info) => { /* log error */ }}
    >
      <MyApp />
    </ErrorBoundary>
  );
}
```

---

## 22. Higher-Order Components (HOC)

### 22.1 HOC là gì?

HOC là một **hàm nhận component** và **trả về component mới** với chức năng được mở rộng.

```jsx
// HOC: withLoading
function withLoading(WrappedComponent) {
  return function WithLoadingComponent({ isLoading, ...props }) {
    if (isLoading) return <Spinner />;
    return <WrappedComponent {...props} />;
  };
}

// HOC: withAuth
function withAuth(WrappedComponent) {
  return function WithAuthComponent(props) {
    const { user } = useAuth();
    if (!user) return <Navigate to="/login" />;
    return <WrappedComponent {...props} user={user} />;
  };
}

// Sử dụng
const UserListWithLoading = withLoading(UserList);
const ProtectedDashboard = withAuth(Dashboard);

// Compose multiple HOCs
const EnhancedComponent = withAuth(withLoading(withTheme(MyComponent)));
```

> **Lưu ý:** Trong React hiện đại, Custom Hooks thường thay thế HOCs vì đơn giản và dễ hiểu hơn.

---

## 23. Render Props Pattern

### 23.1 Render Props

Component nhận một **function prop** và gọi nó để render UI:

```jsx
function MouseTracker({ render }) {
  const [position, setPosition] = useState({ x: 0, y: 0 });

  useEffect(() => {
    const handleMouseMove = (e) => {
      setPosition({ x: e.clientX, y: e.clientY });
    };
    window.addEventListener('mousemove', handleMouseMove);
    return () => window.removeEventListener('mousemove', handleMouseMove);
  }, []);

  return render(position);
}

// Sử dụng
function App() {
  return (
    <MouseTracker render={({ x, y }) => (
      <div>
        <p>Mouse position: ({x}, {y})</p>
        <div style={{ position: 'absolute', left: x, top: y }}>🎯</div>
      </div>
    )} />
  );
}

// Hoặc dùng children as function
function DataFetcher({ url, children }) {
  const { data, loading, error } = useFetch(url);
  return children({ data, loading, error });
}

<DataFetcher url="/api/users">
  {({ data, loading, error }) => {
    if (loading) return <Spinner />;
    if (error) return <Error />;
    return <UserList users={data} />;
  }}
</DataFetcher>
```

---

## 24. React.memo và Pure Components

### 24.1 Shallow Comparison

```jsx
// React.memo với shallow comparison mặc định
const UserCard = React.memo(({ user, onSelect }) => {
  console.log(`Rendering UserCard for ${user.name}`);
  return (
    <div onClick={() => onSelect(user.id)}>
      <img src={user.avatar} alt={user.name} />
      <h3>{user.name}</h3>
    </div>
  );
});

// Component cha phải đảm bảo reference ổn định
function UserList({ users }) {
  const handleSelect = useCallback((id) => {
    console.log('Selected:', id);
  }, []);

  return (
    <div>
      {users.map(user => (
        <UserCard
          key={user.id}
          user={user}
          onSelect={handleSelect}  // Reference ổn định nhờ useCallback
        />
      ))}
    </div>
  );
}
```

---

## 25. Portals

### 25.1 Portal là gì?

Portal cho phép render children vào một **DOM node khác** ngoài parent component hierarchy.

```jsx
import { createPortal } from 'react-dom';

function Modal({ isOpen, onClose, children }) {
  if (!isOpen) return null;

  return createPortal(
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={e => e.stopPropagation()}>
        <button className="modal-close" onClick={onClose}>×</button>
        {children}
      </div>
    </div>,
    document.getElementById('modal-root')  // Render vào #modal-root
  );
}

// Sử dụng
function App() {
  const [showModal, setShowModal] = useState(false);

  return (
    <div>
      <button onClick={() => setShowModal(true)}>Open Modal</button>
      <Modal isOpen={showModal} onClose={() => setShowModal(false)}>
        <h2>Modal Title</h2>
        <p>Modal content here</p>
      </Modal>
    </div>
  );
}
```

### 25.2 Use cases cho Portals

- Modal/Dialog
- Tooltip/Popover
- Notification/Toast
- Dropdown menu (tránh overflow: hidden)

---

## 26. Suspense và Lazy Loading

### 26.1 React.lazy + Suspense

```jsx
import { lazy, Suspense } from 'react';

const HeavyChart = lazy(() => import('./components/HeavyChart'));
const AdminPanel = lazy(() => import('./pages/AdminPanel'));

function App() {
  return (
    <div>
      <Suspense fallback={<div>Loading chart...</div>}>
        <HeavyChart data={chartData} />
      </Suspense>

      <Suspense fallback={<FullPageLoader />}>
        <AdminPanel />
      </Suspense>
    </div>
  );
}
```

### 26.2 Suspense cho Data Fetching (React 18+)

```jsx
// Với libraries hỗ trợ Suspense (React Query, SWR, Relay)
import { Suspense } from 'react';
import { useSuspenseQuery } from '@tanstack/react-query';

function UserProfile({ userId }) {
  // Tự động suspend cho đến khi data sẵn sàng
  const { data: user } = useSuspenseQuery({
    queryKey: ['user', userId],
    queryFn: () => fetchUser(userId)
  });

  return <h1>{user.name}</h1>;
}

function App() {
  return (
    <Suspense fallback={<Skeleton />}>
      <UserProfile userId={1} />
    </Suspense>
  );
}
```

---

## 27. Server-Side Rendering (SSR)

### 27.1 SSR vs CSR

| | Client-Side Rendering (CSR) | Server-Side Rendering (SSR) |
|--|---|---|
| Initial Load | Chậm (download JS → render) | Nhanh (HTML có sẵn) |
| SEO | Kém (bot thấy blank page) | Tốt (full HTML) |
| Interactivity | Nhanh sau load | Cần hydration |
| Server Load | Thấp | Cao hơn |
| Use case | SPA, Dashboard | Blog, E-commerce |

### 27.2 Next.js - Framework SSR cho React

```bash
npx create-next-app@latest my-app
```

```jsx
// app/page.jsx (App Router - Next.js 13+)
// Server Component mặc định
async function HomePage() {
  const posts = await fetch('https://api.example.com/posts').then(r => r.json());

  return (
    <div>
      <h1>Blog Posts</h1>
      {posts.map(post => (
        <article key={post.id}>
          <h2>{post.title}</h2>
          <p>{post.excerpt}</p>
        </article>
      ))}
    </div>
  );
}

export default HomePage;
```

### 27.3 Static Site Generation (SSG)

```jsx
// Next.js - Static generation at build time
// app/blog/[slug]/page.jsx

export async function generateStaticParams() {
  const posts = await fetch('https://api.example.com/posts').then(r => r.json());
  return posts.map(post => ({ slug: post.slug }));
}

async function BlogPost({ params }) {
  const post = await fetch(`https://api.example.com/posts/${params.slug}`)
    .then(r => r.json());

  return (
    <article>
      <h1>{post.title}</h1>
      <div dangerouslySetInnerHTML={{ __html: post.content }} />
    </article>
  );
}
```

### 27.4 React Server Components (RSC)

```jsx
// Server Component - chạy trên server, không gửi JS xuống client
// app/products/page.jsx
import { db } from '@/lib/database';

async function ProductsPage() {
  // Truy cập database trực tiếp (server-only)
  const products = await db.products.findMany();

  return (
    <div>
      {products.map(p => <ProductCard key={p.id} product={p} />)}
      {/* Client Component cho interactivity */}
      <AddToCartButton />
    </div>
  );
}

// Client Component (cần 'use client' directive)
// components/AddToCartButton.jsx
'use client';
import { useState } from 'react';

function AddToCartButton({ productId }) {
  const [added, setAdded] = useState(false);
  return (
    <button onClick={() => setAdded(true)}>
      {added ? '✓ Added' : 'Add to Cart'}
    </button>
  );
}
```

---

## 28. Testing trong React

### 28.1 Testing Libraries

- **Jest** — Test runner + assertions
- **React Testing Library (RTL)** — Testing components
- **MSW** — Mocking API calls
- **Cypress/Playwright** — End-to-end testing

### 28.2 Unit Testing với React Testing Library

```jsx
// components/Counter.test.jsx
import { render, screen, fireEvent } from '@testing-library/react';
import Counter from './Counter';

describe('Counter', () => {
  test('renders initial count', () => {
    render(<Counter initialCount={5} />);
    expect(screen.getByText('Count: 5')).toBeInTheDocument();
  });

  test('increments count on button click', () => {
    render(<Counter initialCount={0} />);
    const button = screen.getByRole('button', { name: /increment/i });
    fireEvent.click(button);
    expect(screen.getByText('Count: 1')).toBeInTheDocument();
  });

  test('decrements count', () => {
    render(<Counter initialCount={10} />);
    fireEvent.click(screen.getByRole('button', { name: /decrement/i }));
    expect(screen.getByText('Count: 9')).toBeInTheDocument();
  });
});
```

### 28.3 Testing Async Components

```jsx
import { render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import UserProfile from './UserProfile';

// Mock fetch
global.fetch = jest.fn(() =>
  Promise.resolve({
    ok: true,
    json: () => Promise.resolve({ name: 'John', email: 'john@test.com' })
  })
);

test('loads and displays user data', async () => {
  render(<UserProfile userId="1" />);

  // Loading state
  expect(screen.getByText(/loading/i)).toBeInTheDocument();

  // Wait for data
  await waitFor(() => {
    expect(screen.getByText('John')).toBeInTheDocument();
  });

  expect(screen.getByText('john@test.com')).toBeInTheDocument();
});
```

### 28.4 Testing Custom Hooks

```jsx
import { renderHook, act } from '@testing-library/react';
import useCounter from './useCounter';

test('useCounter increments and decrements', () => {
  const { result } = renderHook(() => useCounter(0));

  expect(result.current.count).toBe(0);

  act(() => {
    result.current.increment();
  });
  expect(result.current.count).toBe(1);

  act(() => {
    result.current.decrement();
  });
  expect(result.current.count).toBe(0);
});
```

---

## 29. Best Practices và Design Patterns

### 29.1 Component Organization

```
src/
├── components/
│   ├── ui/                 # Presentational (Button, Card, Modal)
│   ├── features/           # Feature-specific (UserCard, ProductList)
│   └── layout/             # Layout (Header, Sidebar, Footer)
├── hooks/                  # Custom hooks
├── contexts/               # Context providers
├── services/               # API layer
├── utils/                  # Pure utility functions
├── types/                  # TypeScript types/interfaces
└── constants/              # App constants
```

### 29.2 Naming Conventions

```jsx
// Components: PascalCase
function UserProfile() {}
function ProductCard() {}

// Hooks: camelCase, prefix "use"
function useAuth() {}
function useLocalStorage() {}

// Event handlers: prefix "handle"
const handleClick = () => {};
const handleSubmit = () => {};

// Boolean props: prefix "is", "has", "should"
<Modal isOpen={true} hasCloseButton={true} shouldAnimate={false} />

// Files: match component name
// UserProfile.jsx, useAuth.js, authContext.js
```

### 29.3 State Management Guidelines

```
Local State (useState)
  └── Chỉ component này cần
      └── Form input, toggle, local UI state

Lifted State (useState ở parent)
  └── Vài component gần nhau cần
      └── Sibling communication

Context
  └── Nhiều component ở các levels khác nhau cần
      └── Theme, Auth, Language

Redux/Zustand
  └── Complex app-wide state
      └── E-commerce cart, real-time data, undo/redo
```

### 29.4 Composition over Inheritance

```jsx
// ✅ Composition pattern
function Dialog({ title, children, actions }) {
  return (
    <div className="dialog">
      <h2>{title}</h2>
      <div className="dialog-body">{children}</div>
      <div className="dialog-actions">{actions}</div>
    </div>
  );
}

function ConfirmDialog({ title, message, onConfirm, onCancel }) {
  return (
    <Dialog
      title={title}
      actions={
        <>
          <button onClick={onCancel}>Cancel</button>
          <button onClick={onConfirm}>Confirm</button>
        </>
      }
    >
      <p>{message}</p>
    </Dialog>
  );
}
```

### 29.5 Tránh các Anti-patterns

```jsx
// ❌ Anti-pattern: quá nhiều state
function Bad() {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [phone, setPhone] = useState('');
  // 10+ useState...
}

// ✅ Group related state
function Good() {
  const [formData, setFormData] = useState({
    firstName: '', lastName: '', email: '', phone: ''
  });
  // hoặc useReducer cho logic phức tạp
}

// ❌ Anti-pattern: derived state trong useState
function Bad({ items }) {
  const [filteredItems, setFilteredItems] = useState(items);
  // Phải sync manually → bugs
}

// ✅ Tính toán từ existing state/props
function Good({ items }) {
  const filteredItems = useMemo(
    () => items.filter(i => i.active),
    [items]
  );
}

// ❌ Anti-pattern: useEffect để sync state
useEffect(() => {
  setFullName(`${firstName} ${lastName}`);
}, [firstName, lastName]);

// ✅ Tính toán trực tiếp
const fullName = `${firstName} ${lastName}`;
```

### 29.6 Accessibility (a11y)

```jsx
function AccessibleForm() {
  return (
    <form role="form" aria-label="Registration form">
      <div>
        <label htmlFor="email">Email *</label>
        <input
          id="email"
          type="email"
          required
          aria-required="true"
          aria-describedby="email-help"
        />
        <small id="email-help">Enter your work email</small>
      </div>

      <button
        type="submit"
        aria-busy={isLoading}
        disabled={isLoading}
      >
        {isLoading ? 'Submitting...' : 'Submit'}
      </button>
    </form>
  );
}

// Keyboard navigation
function Dropdown({ items }) {
  return (
    <ul role="listbox" aria-label="Options">
      {items.map((item, i) => (
        <li
          key={item.id}
          role="option"
          tabIndex={0}
          aria-selected={selectedId === item.id}
          onKeyDown={(e) => {
            if (e.key === 'Enter' || e.key === ' ') select(item.id);
          }}
        >
          {item.name}
        </li>
      ))}
    </ul>
  );
}
```

---

## 30. Tổng kết

### 30.1 Lộ trình học React

**Beginner (1-2 tháng):**
- JSX, Components, Props, State
- Event Handling, Forms
- useState, useEffect
- Conditional Rendering, Lists

**Intermediate (2-3 tháng):**
- Custom Hooks
- Context API
- React Router
- Error Boundaries
- Performance basics (React.memo)

**Advanced (3+ tháng):**
- Redux/Zustand
- Server-Side Rendering (Next.js)
- Testing (RTL, Cypress)
- Advanced Patterns (HOC, Render Props)
- React Server Components
- Concurrent Features

### 30.2 Ecosystem quan trọng

| Lĩnh vực | Thư viện |
|-----------|----------|
| Routing | React Router, TanStack Router |
| State Management | Redux Toolkit, Zustand, Jotai, Recoil |
| Data Fetching | TanStack Query, SWR, RTK Query |
| Forms | React Hook Form, Formik |
| UI Libraries | Material UI, Ant Design, Chakra UI, shadcn/ui |
| Animation | Framer Motion, React Spring |
| Testing | Jest, React Testing Library, Cypress, Playwright |
| SSR/SSG | Next.js, Remix, Gatsby |
| Mobile | React Native, Expo |
| Meta-framework | Next.js, Remix |

### 30.3 Tips cho React Developer

1. **Think in Components** — Chia UI thành các component nhỏ, có trách nhiệm rõ ràng
2. **Immutability** — Luôn tạo state mới, không mutate trực tiếp
3. **Single Source of Truth** — Mỗi piece of state chỉ có 1 nơi quản lý
4. **Lift State Up** — Nâng state lên component cha chung gần nhất khi cần share
5. **Composition > Inheritance** — Sử dụng composition thay vì kế thừa
6. **DRY with Custom Hooks** — Tái sử dụng logic với custom hooks
7. **Type Safety** — Sử dụng TypeScript cho dự án production
8. **Test Critical Paths** — Viết test cho business logic quan trọng
9. **Performance Last** — Tối ưu hiệu suất sau khi code hoạt động đúng
10. **Stay Updated** — React phát triển nhanh, cập nhật kiến thức thường xuyên
