# React - Bài Tập (Trung Bình đến Khó)

## Bài 1: Toggle Theme Component
**Độ khó: Trung bình**

Tạo một component `ThemeToggle` sử dụng `useState` để chuyển đổi giữa theme sáng (light) và tối (dark). Yêu cầu:
1. Button hiển thị icon phù hợp (🌞 cho light, 🌙 cho dark).
2. Khi click, toàn bộ background và text color thay đổi tương ứng.
3. Lưu theme vào `localStorage` để giữ lại khi refresh trang.

**Đầu vào:** Click button toggle
**Đầu ra:** Giao diện chuyển đổi giữa light mode và dark mode, giá trị được persist qua localStorage.

---

## Bài 2: Counter với useReducer
**Độ khó: Trung bình**

Implement một Counter component sử dụng `useReducer` với các tính năng:
1. Increment (+1), Decrement (-1), Reset (về 0)
2. Increment by custom amount (nhập số từ input)
3. Hiển thị lịch sử tất cả các thao tác (action log)
4. Undo: quay lại state trước đó

**Đầu vào:** Các button actions và input amount
**Đầu ra:** Counter value cập nhật đúng, action log hiển thị đầy đủ lịch sử, undo hoạt động chính xác.

---

## Bài 3: Custom Hook - useFetch
**Độ khó: Trung bình**

Viết custom hook `useFetch(url)` với các tính năng:
1. Trả về `{ data, loading, error, refetch }`
2. Tự động fetch khi `url` thay đổi
3. Xử lý race condition (cancel request cũ khi url mới)
4. Hỗ trợ `AbortController` để cancel request khi unmount
5. Cache kết quả để tránh fetch lại cùng URL

**Đầu vào:** URL string
**Đầu ra:** Object chứa data, loading state, error state, và hàm refetch.

---

## Bài 4: Todo App với Context + useReducer
**Độ khó: Trung bình - Khó**

Xây dựng Todo Application hoàn chỉnh với:
1. **TodoContext** quản lý global state bằng `useReducer`
2. Actions: ADD_TODO, TOGGLE_TODO, DELETE_TODO, EDIT_TODO, FILTER_TODOS, CLEAR_COMPLETED
3. Filter: All, Active, Completed
4. Component structure:
   - `TodoProvider` (Context Provider)
   - `TodoInput` (thêm todo mới)
   - `TodoList` (hiển thị danh sách)
   - `TodoItem` (mỗi todo item - editable)
   - `TodoFilter` (filter buttons)
   - `TodoStats` (thống kê: total, active, completed)
5. Persist data vào localStorage
6. Keyboard shortcut: Enter để thêm, Escape để cancel edit

---

## Bài 5: Infinite Scroll với Intersection Observer
**Độ khó: Khó**

Implement component `InfiniteScroll` hiển thị danh sách users từ API:
1. Sử dụng `useRef` + `IntersectionObserver` để detect khi scroll đến cuối
2. Load thêm data khi scroll đến sentinel element
3. Hiển thị loading spinner khi đang fetch
4. Xử lý trường hợp không còn data (end of list)
5. Tạo custom hook `useInfiniteScroll(fetchFn, options)` tái sử dụng được
6. Hiển thị skeleton loading cho items đang load

**API mẫu:** `https://jsonplaceholder.typicode.com/users?_page={page}&_limit=10`

---

## Bài 6: Form Validation với Custom Hook
**Độ khó: Khó**

Tạo custom hook `useForm` cho form validation:
1. Nhận config object với initial values và validation rules
2. Hỗ trợ validation: required, minLength, maxLength, pattern (regex), custom validator function
3. Validate on change (optional) và on submit
4. Trả về: `{ values, errors, touched, handleChange, handleBlur, handleSubmit, isValid, reset }`
5. Áp dụng cho form đăng ký: name, email, password, confirmPassword
6. Hiển thị error messages real-time
7. Disable submit button khi form invalid

---

## Bài 7: Shopping Cart với Redux Toolkit
**Độ khó: Khó**

Xây dựng Shopping Cart sử dụng Redux Toolkit:
1. **Product Slice**: fetch products từ API, filter by category, search
2. **Cart Slice**: add/remove items, update quantity, clear cart
3. **UI Slice**: manage loading states, notifications, sidebar open/close
4. Components:
   - `ProductGrid` - hiển thị products
   - `ProductCard` - mỗi product với "Add to Cart"
   - `CartSidebar` - sliding panel hiển thị cart items
   - `CartItem` - quantity controls (+/-)
   - `CartSummary` - total price, item count
5. Sử dụng `createAsyncThunk` cho API calls
6. Persist cart vào localStorage
7. Optimistic updates cho add/remove

---

## Bài 8: Drag and Drop Kanban Board
**Độ khó: Khó**

Implement Kanban Board (kiểu Trello) với:
1. Ba cột: "To Do", "In Progress", "Done"
2. Drag and drop cards giữa các cột (sử dụng HTML5 Drag & Drop API hoặc react-beautiful-dnd)
3. Thêm/sửa/xóa cards
4. Mỗi card có: title, description, priority (High/Medium/Low), assignee
5. Filter cards theo priority hoặc assignee
6. Persist data vào localStorage
7. Animation khi move cards
8. Responsive: trên mobile hiển thị dạng tabs thay vì columns

---

## Bài 9: Real-time Chat Component
**Độ khó: Khó**

Xây dựng Chat Interface với:
1. Sử dụng WebSocket (hoặc mock với setInterval) cho real-time messages
2. Components: `ChatRoom`, `MessageList`, `MessageItem`, `MessageInput`, `UserList`
3. Features:
   - Gửi/nhận tin nhắn real-time
   - Hiển thị "typing..." indicator
   - Auto-scroll xuống tin nhắn mới
   - Emoji picker đơn giản
   - Timestamp cho mỗi tin nhắn
   - Nhóm tin nhắn liên tiếp của cùng user
4. Custom hooks: `useWebSocket`, `useScrollToBottom`
5. Xử lý reconnection khi mất kết nối
6. Virtualized list cho performance (react-window)

---

## Bài 10: Dashboard với React Router + Code Splitting
**Độ khó: Khó**

Xây dựng Admin Dashboard với:
1. **React Router v6** nested routes:
   - `/` → Dashboard overview
   - `/users` → User list
   - `/users/:id` → User detail
   - `/products` → Product management
   - `/settings` → Settings page
   - `/login` → Login page
2. **Protected routes** - redirect về /login nếu chưa auth
3. **Code splitting** - lazy load mỗi route
4. **Layout component** với:
   - Sidebar navigation (collapsible)
   - Header với user info + notifications
   - Breadcrumb tự động theo route
5. **Shared state**: Auth context cho user session
6. Loading states với Suspense fallback
7. 404 page cho routes không tồn tại
8. Persist auth state (token trong localStorage)
