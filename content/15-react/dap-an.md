# React - Đáp Án Bài Tập

## Bài 1: Toggle Theme Component

```jsx
import { useState, useEffect } from 'react';

function ThemeToggle() {
  const [theme, setTheme] = useState(() => {
    const saved = localStorage.getItem('theme');
    return saved || 'light';
  });

  useEffect(() => {
    localStorage.setItem('theme', theme);
    document.body.className = theme;
  }, [theme]);

  const toggleTheme = () => {
    setTheme(prev => prev === 'light' ? 'dark' : 'light');
  };

  const styles = {
    container: {
      minHeight: '100vh',
      backgroundColor: theme === 'light' ? '#ffffff' : '#1a1a2e',
      color: theme === 'light' ? '#333333' : '#eaeaea',
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      justifyContent: 'center',
      transition: 'all 0.3s ease'
    },
    button: {
      padding: '12px 24px',
      fontSize: '18px',
      border: 'none',
      borderRadius: '8px',
      cursor: 'pointer',
      backgroundColor: theme === 'light' ? '#333' : '#f0f0f0',
      color: theme === 'light' ? '#fff' : '#333',
      transition: 'all 0.3s ease'
    }
  };

  return (
    <div style={styles.container}>
      <h1>Current Theme: {theme}</h1>
      <button style={styles.button} onClick={toggleTheme}>
        {theme === 'light' ? '🌙 Switch to Dark' : '🌞 Switch to Light'}
      </button>
    </div>
  );
}

export default ThemeToggle;
```

**Giải thích:**
- Sử dụng lazy initialization trong `useState` để đọc từ localStorage chỉ 1 lần.
- `useEffect` sync theme lên localStorage và update body className mỗi khi theme thay đổi.
- Inline styles thay đổi theo theme state.

---

## Bài 2: Counter với useReducer

```jsx
import { useReducer, useState } from 'react';

const initialState = {
  count: 0,
  history: [],
  past: []  // for undo
};

function counterReducer(state, action) {
  switch (action.type) {
    case 'INCREMENT':
      return {
        ...state,
        count: state.count + 1,
        history: [...state.history, `+1 → ${state.count + 1}`],
        past: [...state.past, state.count]
      };
    case 'DECREMENT':
      return {
        ...state,
        count: state.count - 1,
        history: [...state.history, `-1 → ${state.count - 1}`],
        past: [...state.past, state.count]
      };
    case 'RESET':
      return {
        ...state,
        count: 0,
        history: [...state.history, `Reset → 0`],
        past: [...state.past, state.count]
      };
    case 'INCREMENT_BY':
      return {
        ...state,
        count: state.count + action.payload,
        history: [...state.history, `+${action.payload} → ${state.count + action.payload}`],
        past: [...state.past, state.count]
      };
    case 'UNDO':
      if (state.past.length === 0) return state;
      const previousCount = state.past[state.past.length - 1];
      return {
        ...state,
        count: previousCount,
        history: [...state.history, `Undo → ${previousCount}`],
        past: state.past.slice(0, -1)
      };
    default:
      return state;
  }
}

function Counter() {
  const [state, dispatch] = useReducer(counterReducer, initialState);
  const [amount, setAmount] = useState('');

  const handleIncrementBy = () => {
    const num = parseInt(amount);
    if (!isNaN(num)) {
      dispatch({ type: 'INCREMENT_BY', payload: num });
      setAmount('');
    }
  };

  return (
    <div style={{ padding: '20px', maxWidth: '400px', margin: '0 auto' }}>
      <h1>Count: {state.count}</h1>

      <div style={{ display: 'flex', gap: '8px', marginBottom: '16px' }}>
        <button onClick={() => dispatch({ type: 'DECREMENT' })}>-1</button>
        <button onClick={() => dispatch({ type: 'INCREMENT' })}>+1</button>
        <button onClick={() => dispatch({ type: 'RESET' })}>Reset</button>
        <button
          onClick={() => dispatch({ type: 'UNDO' })}
          disabled={state.past.length === 0}
        >
          Undo
        </button>
      </div>

      <div style={{ display: 'flex', gap: '8px', marginBottom: '16px' }}>
        <input
          type="number"
          value={amount}
          onChange={e => setAmount(e.target.value)}
          placeholder="Enter amount"
        />
        <button onClick={handleIncrementBy}>Add</button>
      </div>

      <h3>Action Log:</h3>
      <ul style={{ maxHeight: '200px', overflow: 'auto' }}>
        {state.history.map((entry, i) => (
          <li key={i}>{entry}</li>
        ))}
      </ul>
    </div>
  );
}

export default Counter;
```

**Giải thích:**
- `useReducer` quản lý state phức tạp gồm count, history, và past (cho undo).
- Mỗi action lưu count hiện tại vào `past` array trước khi thay đổi.
- Undo pop giá trị cuối cùng từ `past` và set lại count.

---

## Bài 3: Custom Hook - useFetch

```jsx
import { useState, useEffect, useRef, useCallback } from 'react';

const cache = new Map();

function useFetch(url, options = {}) {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const abortControllerRef = useRef(null);

  const fetchData = useCallback(async (fetchUrl) => {
    if (!fetchUrl) {
      setData(null);
      setLoading(false);
      return;
    }

    // Check cache
    if (cache.has(fetchUrl) && !options.skipCache) {
      setData(cache.get(fetchUrl));
      setLoading(false);
      return;
    }

    // Cancel previous request
    if (abortControllerRef.current) {
      abortControllerRef.current.abort();
    }

    const controller = new AbortController();
    abortControllerRef.current = controller;

    try {
      setLoading(true);
      setError(null);

      const response = await fetch(fetchUrl, {
        ...options,
        signal: controller.signal
      });

      if (!response.ok) {
        throw new Error(`HTTP Error: ${response.status} ${response.statusText}`);
      }

      const json = await response.json();

      // Only update state if not aborted
      if (!controller.signal.aborted) {
        cache.set(fetchUrl, json);
        setData(json);
        setError(null);
      }
    } catch (err) {
      if (err.name !== 'AbortError') {
        setError(err.message);
        setData(null);
      }
    } finally {
      if (!controller.signal.aborted) {
        setLoading(false);
      }
    }
  }, []);

  useEffect(() => {
    fetchData(url);

    return () => {
      if (abortControllerRef.current) {
        abortControllerRef.current.abort();
      }
    };
  }, [url, fetchData]);

  const refetch = useCallback(() => {
    cache.delete(url); // Clear cache for this URL
    fetchData(url);
  }, [url, fetchData]);

  return { data, loading, error, refetch };
}

export default useFetch;

// === Sử dụng ===
function UserList() {
  const { data: users, loading, error, refetch } = useFetch(
    'https://jsonplaceholder.typicode.com/users'
  );

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error} <button onClick={refetch}>Retry</button></p>;

  return (
    <div>
      <button onClick={refetch}>Refresh</button>
      <ul>
        {users?.map(user => (
          <li key={user.id}>{user.name} - {user.email}</li>
        ))}
      </ul>
    </div>
  );
}
```

**Giải thích:**
- `AbortController` cancel request trước đó khi URL thay đổi hoặc component unmount.
- Cache lưu kết quả theo URL, tránh fetch lại dữ liệu đã có.
- `refetch` xóa cache và fetch lại.
- Kiểm tra `controller.signal.aborted` để tránh update state sau khi bị cancel.

---

## Bài 4: Todo App với Context + useReducer

```jsx
// --- TodoContext.jsx ---
import { createContext, useContext, useReducer, useEffect } from 'react';

const TodoContext = createContext(null);

function todoReducer(state, action) {
  switch (action.type) {
    case 'ADD_TODO':
      return {
        ...state,
        todos: [...state.todos, {
          id: Date.now(),
          text: action.payload,
          completed: false
        }]
      };
    case 'TOGGLE_TODO':
      return {
        ...state,
        todos: state.todos.map(todo =>
          todo.id === action.payload
            ? { ...todo, completed: !todo.completed }
            : todo
        )
      };
    case 'DELETE_TODO':
      return {
        ...state,
        todos: state.todos.filter(todo => todo.id !== action.payload)
      };
    case 'EDIT_TODO':
      return {
        ...state,
        todos: state.todos.map(todo =>
          todo.id === action.payload.id
            ? { ...todo, text: action.payload.text }
            : todo
        )
      };
    case 'SET_FILTER':
      return { ...state, filter: action.payload };
    case 'CLEAR_COMPLETED':
      return {
        ...state,
        todos: state.todos.filter(todo => !todo.completed)
      };
    default:
      return state;
  }
}

const initialState = {
  todos: JSON.parse(localStorage.getItem('todos') || '[]'),
  filter: 'all'
};

export function TodoProvider({ children }) {
  const [state, dispatch] = useReducer(todoReducer, initialState);

  useEffect(() => {
    localStorage.setItem('todos', JSON.stringify(state.todos));
  }, [state.todos]);

  const filteredTodos = state.todos.filter(todo => {
    if (state.filter === 'active') return !todo.completed;
    if (state.filter === 'completed') return todo.completed;
    return true;
  });

  const stats = {
    total: state.todos.length,
    active: state.todos.filter(t => !t.completed).length,
    completed: state.todos.filter(t => t.completed).length
  };

  return (
    <TodoContext.Provider value={{ ...state, filteredTodos, stats, dispatch }}>
      {children}
    </TodoContext.Provider>
  );
}

export function useTodos() {
  const context = useContext(TodoContext);
  if (!context) throw new Error('useTodos must be used within TodoProvider');
  return context;
}

// --- TodoInput.jsx ---
import { useState } from 'react';
import { useTodos } from './TodoContext';

function TodoInput() {
  const [text, setText] = useState('');
  const { dispatch } = useTodos();

  const handleSubmit = (e) => {
    e.preventDefault();
    if (text.trim()) {
      dispatch({ type: 'ADD_TODO', payload: text.trim() });
      setText('');
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Escape') setText('');
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        value={text}
        onChange={e => setText(e.target.value)}
        onKeyDown={handleKeyDown}
        placeholder="Add a new todo... (Enter to add, Esc to clear)"
      />
      <button type="submit">Add</button>
    </form>
  );
}

// --- TodoItem.jsx ---
import { useState } from 'react';
import { useTodos } from './TodoContext';

function TodoItem({ todo }) {
  const [isEditing, setIsEditing] = useState(false);
  const [editText, setEditText] = useState(todo.text);
  const { dispatch } = useTodos();

  const handleSave = () => {
    if (editText.trim()) {
      dispatch({ type: 'EDIT_TODO', payload: { id: todo.id, text: editText.trim() } });
    }
    setIsEditing(false);
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter') handleSave();
    if (e.key === 'Escape') {
      setEditText(todo.text);
      setIsEditing(false);
    }
  };

  return (
    <div style={{ display: 'flex', alignItems: 'center', gap: '8px', padding: '8px' }}>
      <input
        type="checkbox"
        checked={todo.completed}
        onChange={() => dispatch({ type: 'TOGGLE_TODO', payload: todo.id })}
      />
      {isEditing ? (
        <input
          value={editText}
          onChange={e => setEditText(e.target.value)}
          onKeyDown={handleKeyDown}
          onBlur={handleSave}
          autoFocus
        />
      ) : (
        <span
          style={{ textDecoration: todo.completed ? 'line-through' : 'none', flex: 1 }}
          onDoubleClick={() => setIsEditing(true)}
        >
          {todo.text}
        </span>
      )}
      <button onClick={() => dispatch({ type: 'DELETE_TODO', payload: todo.id })}>
        Delete
      </button>
    </div>
  );
}

// --- TodoApp.jsx (Main) ---
import { TodoProvider, useTodos } from './TodoContext';

function TodoApp() {
  return (
    <TodoProvider>
      <div style={{ maxWidth: '600px', margin: '0 auto', padding: '20px' }}>
        <h1>Todo App</h1>
        <TodoInput />
        <TodoFilter />
        <TodoStats />
        <TodoList />
      </div>
    </TodoProvider>
  );
}

function TodoList() {
  const { filteredTodos } = useTodos();
  return (
    <div>
      {filteredTodos.map(todo => <TodoItem key={todo.id} todo={todo} />)}
    </div>
  );
}

function TodoFilter() {
  const { filter, dispatch } = useTodos();
  const filters = ['all', 'active', 'completed'];
  return (
    <div style={{ display: 'flex', gap: '8px', margin: '16px 0' }}>
      {filters.map(f => (
        <button
          key={f}
          onClick={() => dispatch({ type: 'SET_FILTER', payload: f })}
          style={{ fontWeight: filter === f ? 'bold' : 'normal' }}
        >
          {f.charAt(0).toUpperCase() + f.slice(1)}
        </button>
      ))}
      <button onClick={() => dispatch({ type: 'CLEAR_COMPLETED' })}>
        Clear Completed
      </button>
    </div>
  );
}

function TodoStats() {
  const { stats } = useTodos();
  return (
    <p>Total: {stats.total} | Active: {stats.active} | Completed: {stats.completed}</p>
  );
}
```

---

## Bài 5: Infinite Scroll với Intersection Observer

```jsx
import { useState, useEffect, useRef, useCallback } from 'react';

// Custom Hook
function useInfiniteScroll(fetchFn, options = {}) {
  const { threshold = 0.5, rootMargin = '100px' } = options;
  const [items, setItems] = useState([]);
  const [page, setPage] = useState(1);
  const [loading, setLoading] = useState(false);
  const [hasMore, setHasMore] = useState(true);
  const [error, setError] = useState(null);
  const observerRef = useRef(null);
  const sentinelRef = useRef(null);

  const loadMore = useCallback(async () => {
    if (loading || !hasMore) return;

    try {
      setLoading(true);
      setError(null);
      const newItems = await fetchFn(page);

      if (newItems.length === 0) {
        setHasMore(false);
      } else {
        setItems(prev => [...prev, ...newItems]);
        setPage(prev => prev + 1);
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, [page, loading, hasMore, fetchFn]);

  useEffect(() => {
    observerRef.current = new IntersectionObserver(
      (entries) => {
        if (entries[0].isIntersecting && hasMore && !loading) {
          loadMore();
        }
      },
      { threshold, rootMargin }
    );

    if (sentinelRef.current) {
      observerRef.current.observe(sentinelRef.current);
    }

    return () => {
      if (observerRef.current) {
        observerRef.current.disconnect();
      }
    };
  }, [loadMore, hasMore, loading, threshold, rootMargin]);

  return { items, loading, hasMore, error, sentinelRef };
}

// Component sử dụng
function UserInfiniteList() {
  const fetchUsers = async (page) => {
    const response = await fetch(
      `https://jsonplaceholder.typicode.com/users?_page=${page}&_limit=10`
    );
    if (!response.ok) throw new Error('Failed to fetch');
    return response.json();
  };

  const { items: users, loading, hasMore, error, sentinelRef } =
    useInfiniteScroll(fetchUsers);

  return (
    <div style={{ maxWidth: '600px', margin: '0 auto' }}>
      <h1>Users</h1>

      {users.map((user, index) => (
        <div key={`${user.id}-${index}`} style={{
          padding: '16px', margin: '8px 0', border: '1px solid #ddd', borderRadius: '8px'
        }}>
          <h3>{user.name}</h3>
          <p>{user.email}</p>
          <p>{user.company?.name}</p>
        </div>
      ))}

      {/* Sentinel element */}
      <div ref={sentinelRef} style={{ height: '20px' }} />

      {loading && (
        <div style={{ textAlign: 'center', padding: '20px' }}>
          <p>Loading more...</p>
        </div>
      )}

      {!hasMore && (
        <p style={{ textAlign: 'center', color: '#888' }}>
          No more users to load.
        </p>
      )}

      {error && (
        <p style={{ textAlign: 'center', color: 'red' }}>
          Error: {error}
        </p>
      )}
    </div>
  );
}

export default UserInfiniteList;
```

---

## Bài 6: Form Validation với Custom Hook

```jsx
import { useState, useCallback } from 'react';

function useForm({ initialValues, validationRules, validateOnChange = false }) {
  const [values, setValues] = useState(initialValues);
  const [errors, setErrors] = useState({});
  const [touched, setTouched] = useState({});

  const validateField = useCallback((name, value) => {
    const rules = validationRules[name];
    if (!rules) return '';

    for (const rule of rules) {
      if (rule.required && !value) {
        return rule.message || `${name} is required`;
      }
      if (rule.minLength && value.length < rule.minLength) {
        return rule.message || `${name} must be at least ${rule.minLength} characters`;
      }
      if (rule.maxLength && value.length > rule.maxLength) {
        return rule.message || `${name} must be at most ${rule.maxLength} characters`;
      }
      if (rule.pattern && !rule.pattern.test(value)) {
        return rule.message || `${name} is invalid`;
      }
      if (rule.custom) {
        const error = rule.custom(value, values);
        if (error) return error;
      }
    }
    return '';
  }, [validationRules, values]);

  const validateAll = useCallback(() => {
    const newErrors = {};
    Object.keys(validationRules).forEach(field => {
      const error = validateField(field, values[field]);
      if (error) newErrors[field] = error;
    });
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  }, [values, validateField, validationRules]);

  const handleChange = useCallback((e) => {
    const { name, value } = e.target;
    setValues(prev => ({ ...prev, [name]: value }));

    if (validateOnChange && touched[name]) {
      const error = validateField(name, value);
      setErrors(prev => ({ ...prev, [name]: error }));
    }
  }, [validateOnChange, touched, validateField]);

  const handleBlur = useCallback((e) => {
    const { name, value } = e.target;
    setTouched(prev => ({ ...prev, [name]: true }));
    const error = validateField(name, value);
    setErrors(prev => ({ ...prev, [name]: error }));
  }, [validateField]);

  const handleSubmit = useCallback((onSubmit) => (e) => {
    e.preventDefault();
    // Mark all fields as touched
    const allTouched = Object.keys(validationRules).reduce(
      (acc, key) => ({ ...acc, [key]: true }), {}
    );
    setTouched(allTouched);

    if (validateAll()) {
      onSubmit(values);
    }
  }, [values, validateAll, validationRules]);

  const reset = useCallback(() => {
    setValues(initialValues);
    setErrors({});
    setTouched({});
  }, [initialValues]);

  const isValid = Object.values(errors).every(e => !e) &&
    Object.keys(validationRules).every(field => !validateField(field, values[field]));

  return { values, errors, touched, handleChange, handleBlur, handleSubmit, isValid, reset };
}

// === Sử dụng: Registration Form ===
function RegistrationForm() {
  const { values, errors, touched, handleChange, handleBlur, handleSubmit, isValid, reset } =
    useForm({
      initialValues: { name: '', email: '', password: '', confirmPassword: '' },
      validateOnChange: true,
      validationRules: {
        name: [
          { required: true, message: 'Tên là bắt buộc' },
          { minLength: 2, message: 'Tên phải có ít nhất 2 ký tự' }
        ],
        email: [
          { required: true, message: 'Email là bắt buộc' },
          { pattern: /^[^\s@]+@[^\s@]+\.[^\s@]+$/, message: 'Email không hợp lệ' }
        ],
        password: [
          { required: true, message: 'Mật khẩu là bắt buộc' },
          { minLength: 8, message: 'Mật khẩu phải có ít nhất 8 ký tự' },
          { pattern: /(?=.*[A-Z])(?=.*[0-9])/, message: 'Phải có ít nhất 1 chữ hoa và 1 số' }
        ],
        confirmPassword: [
          { required: true, message: 'Xác nhận mật khẩu là bắt buộc' },
          { custom: (value, allValues) =>
            value !== allValues.password ? 'Mật khẩu không khớp' : ''
          }
        ]
      }
    });

  const onSubmit = (data) => {
    console.log('Form submitted:', data);
    alert('Registration successful!');
    reset();
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)} style={{ maxWidth: '400px', margin: '0 auto' }}>
      <h2>Registration</h2>

      {['name', 'email', 'password', 'confirmPassword'].map(field => (
        <div key={field} style={{ marginBottom: '16px' }}>
          <label>{field}</label>
          <input
            type={field.includes('password') || field.includes('Password') ? 'password' : 'text'}
            name={field}
            value={values[field]}
            onChange={handleChange}
            onBlur={handleBlur}
            style={{ display: 'block', width: '100%', padding: '8px' }}
          />
          {touched[field] && errors[field] && (
            <span style={{ color: 'red', fontSize: '12px' }}>{errors[field]}</span>
          )}
        </div>
      ))}

      <button type="submit" disabled={!isValid}>Register</button>
      <button type="button" onClick={reset} style={{ marginLeft: '8px' }}>Reset</button>
    </form>
  );
}

export default RegistrationForm;
```

---

## Bài 7: Shopping Cart với Redux Toolkit

```jsx
// --- store/productSlice.js ---
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';

export const fetchProducts = createAsyncThunk(
  'products/fetchAll',
  async (_, { rejectWithValue }) => {
    try {
      const response = await fetch('https://fakestoreapi.com/products');
      if (!response.ok) throw new Error('Failed to fetch products');
      return await response.json();
    } catch (err) {
      return rejectWithValue(err.message);
    }
  }
);

const productSlice = createSlice({
  name: 'products',
  initialState: { items: [], loading: false, error: null, searchQuery: '', category: 'all' },
  reducers: {
    setSearchQuery: (state, action) => { state.searchQuery = action.payload; },
    setCategory: (state, action) => { state.category = action.payload; }
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchProducts.pending, (state) => { state.loading = true; state.error = null; })
      .addCase(fetchProducts.fulfilled, (state, action) => { state.loading = false; state.items = action.payload; })
      .addCase(fetchProducts.rejected, (state, action) => { state.loading = false; state.error = action.payload; });
  }
});

export const { setSearchQuery, setCategory } = productSlice.actions;
export const selectFilteredProducts = (state) => {
  let products = state.products.items;
  if (state.products.category !== 'all') {
    products = products.filter(p => p.category === state.products.category);
  }
  if (state.products.searchQuery) {
    const query = state.products.searchQuery.toLowerCase();
    products = products.filter(p => p.title.toLowerCase().includes(query));
  }
  return products;
};
export default productSlice.reducer;

// --- store/cartSlice.js ---
import { createSlice } from '@reduxjs/toolkit';

const savedCart = JSON.parse(localStorage.getItem('cart') || '[]');

const cartSlice = createSlice({
  name: 'cart',
  initialState: { items: savedCart },
  reducers: {
    addToCart: (state, action) => {
      const existing = state.items.find(i => i.id === action.payload.id);
      if (existing) {
        existing.quantity += 1;
      } else {
        state.items.push({ ...action.payload, quantity: 1 });
      }
      localStorage.setItem('cart', JSON.stringify(state.items));
    },
    removeFromCart: (state, action) => {
      state.items = state.items.filter(i => i.id !== action.payload);
      localStorage.setItem('cart', JSON.stringify(state.items));
    },
    updateQuantity: (state, action) => {
      const item = state.items.find(i => i.id === action.payload.id);
      if (item) {
        item.quantity = Math.max(1, action.payload.quantity);
      }
      localStorage.setItem('cart', JSON.stringify(state.items));
    },
    clearCart: (state) => {
      state.items = [];
      localStorage.removeItem('cart');
    }
  }
});

export const { addToCart, removeFromCart, updateQuantity, clearCart } = cartSlice.actions;
export const selectCartTotal = (state) =>
  state.cart.items.reduce((sum, item) => sum + item.price * item.quantity, 0);
export const selectCartItemCount = (state) =>
  state.cart.items.reduce((sum, item) => sum + item.quantity, 0);
export default cartSlice.reducer;

// --- store/index.js ---
import { configureStore } from '@reduxjs/toolkit';
import productReducer from './productSlice';
import cartReducer from './cartSlice';

export const store = configureStore({
  reducer: {
    products: productReducer,
    cart: cartReducer
  }
});

// --- Components ---
import { useSelector, useDispatch } from 'react-redux';
import { useEffect } from 'react';

function ProductGrid() {
  const dispatch = useDispatch();
  const products = useSelector(selectFilteredProducts);
  const { loading, error } = useSelector(state => state.products);

  useEffect(() => { dispatch(fetchProducts()); }, [dispatch]);

  if (loading) return <p>Loading products...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: '16px' }}>
      {products.map(product => (
        <ProductCard key={product.id} product={product} />
      ))}
    </div>
  );
}

function ProductCard({ product }) {
  const dispatch = useDispatch();
  return (
    <div style={{ border: '1px solid #ddd', padding: '16px', borderRadius: '8px' }}>
      <img src={product.image} alt={product.title} style={{ width: '100%', height: '200px', objectFit: 'contain' }} />
      <h3 style={{ fontSize: '14px' }}>{product.title}</h3>
      <p>${product.price.toFixed(2)}</p>
      <button onClick={() => dispatch(addToCart(product))}>Add to Cart</button>
    </div>
  );
}

function CartSummary() {
  const totalPrice = useSelector(selectCartTotal);
  const itemCount = useSelector(selectCartItemCount);
  const cartItems = useSelector(state => state.cart.items);
  const dispatch = useDispatch();

  return (
    <div style={{ padding: '16px', border: '1px solid #333', borderRadius: '8px' }}>
      <h2>Cart ({itemCount} items)</h2>
      {cartItems.map(item => (
        <div key={item.id} style={{ display: 'flex', justifyContent: 'space-between', margin: '8px 0' }}>
          <span>{item.title.substring(0, 30)}...</span>
          <span>
            <button onClick={() => dispatch(updateQuantity({ id: item.id, quantity: item.quantity - 1 }))}>-</button>
            {item.quantity}
            <button onClick={() => dispatch(updateQuantity({ id: item.id, quantity: item.quantity + 1 }))}>+</button>
            <button onClick={() => dispatch(removeFromCart(item.id))}>X</button>
          </span>
        </div>
      ))}
      <hr />
      <p><strong>Total: ${totalPrice.toFixed(2)}</strong></p>
      <button onClick={() => dispatch(clearCart())}>Clear Cart</button>
    </div>
  );
}
```

---

## Bài 8-10: Hướng dẫn giải

**Bài 8 (Kanban Board):** Sử dụng HTML5 Drag and Drop API với `onDragStart`, `onDragOver`, `onDrop`. State quản lý bởi useReducer với columns là object `{ todo: [], inProgress: [], done: [] }`. Khi drop, move item giữa columns.

**Bài 9 (Chat):** Sử dụng WebSocket API (`new WebSocket(url)`). Custom hook `useWebSocket` quản lý connection, reconnection logic. `useRef` + `scrollIntoView` cho auto-scroll. Tin nhắn group bởi sender + timestamp gap.

**Bài 10 (Dashboard):** React Router v6 `createBrowserRouter` + `RouterProvider`. Lazy load routes với `React.lazy`. AuthContext wrap protected routes. Layout component dùng `<Outlet />` cho nested routes. Breadcrumb tự generate từ `useLocation().pathname`.
