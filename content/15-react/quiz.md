# Quiz - React

## Câu 1

[TYPE: MULTIPLE_CHOICE]

React là gì?

- [ ] Một framework JavaScript đầy đủ
- [x] Một thư viện JavaScript để xây dựng giao diện người dùng
- [ ] Một ngôn ngữ lập trình mới
- [ ] Một CSS framework

> **Giải thích:** React là một thư viện (library), không phải framework. React tập trung vào việc xây dựng UI theo mô hình component-based, được phát triển bởi Facebook (Meta).

## Câu 2

[TYPE: MULTIPLE_CHOICE]

Virtual DOM trong React là gì?

- [ ] Một DOM thật được tối ưu
- [x] Một bản sao nhẹ của Real DOM trong bộ nhớ
- [ ] Một plugin của trình duyệt
- [ ] Một file HTML riêng biệt

> **Giải thích:** Virtual DOM là một biểu diễn JavaScript nhẹ của Real DOM. React so sánh Virtual DOM mới và cũ (diffing), sau đó chỉ cập nhật những phần thay đổi trên Real DOM (reconciliation).

## Câu 3

[TYPE: MULTIPLE_CHOICE]

JSX là viết tắt của gì?

- [ ] Java Syntax Extension
- [x] JavaScript XML
- [ ] JavaScript Extra
- [ ] JSON XML Syntax

> **Giải thích:** JSX (JavaScript XML) là syntax extension cho phép viết cấu trúc giống HTML trong JavaScript. JSX được biên dịch thành `React.createElement()` bởi Babel/SWC.

## Câu 4

[TYPE: TRUE_FALSE]

Mệnh đề: "Trong JSX, ta sử dụng `class` để đặt CSS class cho element."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Trong JSX phải dùng `className` thay vì `class` vì `class` là reserved keyword trong JavaScript. Tương tự, dùng `htmlFor` thay vì `for`.

## Câu 5

[TYPE: SELECT_RESULT]

Đoạn code sau render ra gì?

```jsx
function App() {
  const name = "React";
  return <h1>Hello, {name.toUpperCase()}!</h1>;
}
```

- [ ] Hello, name!
- [ ] Hello, {name.toUpperCase()}!
- [x] Hello, REACT!
- [ ] Error

> **Giải thích:** Trong JSX, biểu thức JavaScript bên trong `{}` được evaluate. `name.toUpperCase()` trả về "REACT".

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Cách nào đúng để tạo một functional component trong React?

- [ ] `const App = () => { <div>Hello</div> }`
- [x] `const App = () => { return <div>Hello</div>; }`
- [ ] `function App { return <div>Hello</div>; }`
- [ ] `class App() { render <div>Hello</div>; }`

> **Giải thích:** Functional component phải `return` JSX. Nếu dùng `{}` thì phải có `return`. Hoặc dùng implicit return: `const App = () => <div>Hello</div>`.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

Props trong React là gì?

- [ ] Biến toàn cục của ứng dụng
- [x] Dữ liệu truyền từ component cha xuống component con
- [ ] State nội bộ của component
- [ ] CSS properties

> **Giải thích:** Props (Properties) là cơ chế truyền dữ liệu một chiều từ parent component xuống child component. Props là read-only — child không được thay đổi props nhận được.

## Câu 8

[TYPE: TRUE_FALSE]

Mệnh đề: "Props trong React là mutable (có thể thay đổi) bên trong component con."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Props là immutable (read-only). Component con không được phép thay đổi props. Nếu cần thay đổi dữ liệu, phải sử dụng state.

## Câu 9

[TYPE: MULTIPLE_CHOICE]

useState hook trả về gì?

- [ ] Chỉ giá trị state hiện tại
- [ ] Một object với get và set methods
- [x] Một array gồm [state value, setter function]
- [ ] Một promise

> **Giải thích:** `useState` trả về array 2 phần tử: giá trị state hiện tại và hàm để cập nhật state. Ví dụ: `const [count, setCount] = useState(0)`.

## Câu 10

[TYPE: SELECT_RESULT]

Khi click button 1 lần, giá trị count tăng lên bao nhiêu?

```jsx
function Counter() {
  const [count, setCount] = useState(0);
  const handleClick = () => {
    setCount(count + 1);
    setCount(count + 1);
    setCount(count + 1);
  };
  return <button onClick={handleClick}>{count}</button>;
}
```

- [x] 1
- [ ] 3
- [ ] 0
- [ ] 9

> **Giải thích:** Vì `count` là closure value (luôn = 0 trong lần click đầu), cả 3 lần `setCount(0 + 1)` đều set giá trị 1. Để fix, dùng functional update: `setCount(prev => prev + 1)`.

## Câu 11

[TYPE: SELECT_RESULT]

Nếu dùng functional update, giá trị count sau 1 click là?

```jsx
const handleClick = () => {
  setCount(prev => prev + 1);
  setCount(prev => prev + 1);
  setCount(prev => prev + 1);
};
```

- [ ] 1
- [x] 3
- [ ] 0
- [ ] Undefined

> **Giải thích:** Functional update `prev => prev + 1` luôn nhận giá trị mới nhất. Lần 1: 0→1, lần 2: 1→2, lần 3: 2→3.

## Câu 12

[TYPE: MULTIPLE_CHOICE]

Đâu là cách đúng để cập nhật một field trong state object?

- [ ] `state.name = 'new name'`
- [ ] `setState({ name: 'new name' })`
- [x] `setState(prev => ({ ...prev, name: 'new name' }))`
- [ ] `setState.name = 'new name'`

> **Giải thích:** State phải immutable. Sử dụng spread operator `...prev` để copy tất cả fields cũ, sau đó override field cần thay đổi. Không bao giờ mutate state trực tiếp.

## Câu 13

[TYPE: MULTIPLE_CHOICE]

Lazy initialization trong useState dùng khi nào?

- [ ] Khi state thay đổi thường xuyên
- [x] Khi giá trị khởi tạo cần tính toán nặng
- [ ] Khi component có nhiều props
- [ ] Khi sử dụng TypeScript

> **Giải thích:** `useState(() => expensiveCalculation())` — hàm chỉ chạy 1 lần khi mount. Nếu viết `useState(expensiveCalculation())`, hàm sẽ chạy mỗi lần render (dù giá trị bị bỏ qua).

## Câu 14

[TYPE: MULTIPLE_CHOICE]

Event handler trong React khác gì với HTML thuần?

- [ ] Không có sự khác biệt
- [x] React dùng camelCase (onClick) và truyền function reference
- [ ] React dùng lowercase (onclick) và truyền string
- [ ] React không hỗ trợ events

> **Giải thích:** React dùng camelCase: `onClick`, `onChange`, `onSubmit`. Truyền function reference: `onClick={handleClick}`, không phải string: `onclick="handleClick()"`.

## Câu 15

[TYPE: SELECT_RESULT]

Đoạn code sau render ra gì?

```jsx
function App() {
  const items = [];
  return <div>{items.length && <p>Has items</p>}</div>;
}
```

- [ ] Không hiển thị gì
- [x] Hiển thị số "0"
- [ ] Hiển thị "Has items"
- [ ] Error

> **Giải thích:** `0 && <p>Has items</p>` trả về `0` (falsy value). React render số 0 ra DOM. Fix: `{items.length > 0 && <p>Has items</p>}`.

## Câu 16

[TYPE: MULTIPLE_CHOICE]

Tại sao key quan trọng khi render danh sách?

- [ ] Để CSS styling hoạt động
- [ ] Để React biết component nào đã mounted
- [x] Để React xác định element nào thay đổi, thêm mới, hoặc bị xóa hiệu quả
- [ ] Key không quan trọng, chỉ là best practice

> **Giải thích:** Key giúp React's reconciliation algorithm xác định chính xác element nào đã thay đổi. Không có key, React phải re-render toàn bộ list khi có thay đổi.

## Câu 17

[TYPE: TRUE_FALSE]

Mệnh đề: "Sử dụng index của array làm key luôn luôn an toàn."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Dùng index làm key gây bug khi reorder, thêm, hoặc xóa items. React sẽ không nhận ra item nào thực sự thay đổi. Chỉ dùng index khi list tĩnh, không bao giờ thay đổi.

## Câu 18

[TYPE: MULTIPLE_CHOICE]

Controlled component là gì?

- [ ] Component không có state
- [x] Component mà giá trị form input được điều khiển bởi React state
- [ ] Component bị disabled
- [ ] Component chỉ nhận props

> **Giải thích:** Controlled component: giá trị input được lưu trong state, mỗi thay đổi đi qua `onChange` handler. React state là "single source of truth" cho giá trị input.

## Câu 19

[TYPE: MULTIPLE_CHOICE]

Uncontrolled component dùng cái gì để truy cập DOM value?

- [ ] useState
- [x] useRef
- [ ] useEffect
- [ ] useContext

> **Giải thích:** Uncontrolled component sử dụng `useRef` để tham chiếu trực tiếp đến DOM element và đọc giá trị từ `ref.current.value` thay vì qua state.

## Câu 20

[TYPE: MULTIPLE_CHOICE]

Quy tắc nào KHÔNG đúng về React Hooks?

- [ ] Chỉ gọi Hooks ở top level
- [ ] Chỉ gọi Hooks trong React function components
- [x] Có thể gọi Hooks bên trong vòng lặp và điều kiện
- [ ] Custom hooks phải bắt đầu bằng "use"

> **Giải thích:** Hooks KHÔNG được gọi bên trong loops, conditions, hoặc nested functions. React phụ thuộc vào thứ tự gọi hooks để tracking state đúng.

## Câu 21

[TYPE: MULTIPLE_CHOICE]

useEffect với dependency array rỗng `[]` chạy khi nào?

- [ ] Mỗi lần render
- [x] Chỉ một lần sau lần mount đầu tiên
- [ ] Không bao giờ chạy
- [ ] Khi component unmount

> **Giải thích:** `useEffect(() => {...}, [])` — dependency array rỗng nghĩa là effect không phụ thuộc vào giá trị nào → chỉ chạy 1 lần sau initial render (tương tự componentDidMount).

## Câu 22

[TYPE: MULTIPLE_CHOICE]

Cleanup function trong useEffect dùng để làm gì?

- [ ] Reset state về giá trị ban đầu
- [x] Dọn dẹp side effects (clearInterval, unsubscribe, close connection)
- [ ] Gọi API lần nữa
- [ ] Cập nhật DOM trực tiếp

> **Giải thích:** Cleanup function (return () => {...}) chạy khi: (1) component unmount, (2) trước khi effect chạy lại. Dùng để clear timers, cancel subscriptions, abort fetch requests.

## Câu 23

[TYPE: SELECT_RESULT]

Đoạn code sau có vấn đề gì?

```jsx
useEffect(async () => {
  const data = await fetch('/api/data');
  setData(await data.json());
}, []);
```

- [ ] Không có vấn đề gì
- [x] useEffect callback không được là async function
- [ ] Thiếu dependency
- [ ] fetch không hoạt động trong useEffect

> **Giải thích:** useEffect callback phải return void hoặc cleanup function, không phải Promise. Fix: định nghĩa async function bên trong rồi gọi nó: `useEffect(() => { async function fetchData() {...} fetchData(); }, [])`.

## Câu 24

[TYPE: MULTIPLE_CHOICE]

useRef khác useState ở điểm nào?

- [ ] useRef không thể lưu giá trị
- [x] Thay đổi useRef.current KHÔNG gây re-render
- [ ] useRef chỉ dùng cho DOM elements
- [ ] useState nhanh hơn useRef

> **Giải thích:** `useRef` tạo một object `{current: value}` persist qua renders. Thay đổi `.current` KHÔNG trigger re-render, khác với `setState` luôn gây re-render.

## Câu 25

[TYPE: MULTIPLE_CHOICE]

useMemo dùng để làm gì?

- [ ] Memoize component
- [x] Memoize giá trị tính toán nặng, chỉ tính lại khi dependencies thay đổi
- [ ] Tạo cache cho API calls
- [ ] Lưu state vào localStorage

> **Giải thích:** `useMemo(() => expensiveCalc(a, b), [a, b])` chỉ tính lại khi `a` hoặc `b` thay đổi. Tránh tính toán không cần thiết mỗi render.

## Câu 26

[TYPE: MULTIPLE_CHOICE]

useCallback khác useMemo ở điểm nào?

- [x] useCallback memoize function reference, useMemo memoize giá trị trả về
- [ ] Không có sự khác biệt
- [ ] useCallback cho class components, useMemo cho functional
- [ ] useCallback nhanh hơn useMemo

> **Giải thích:** `useCallback(fn, deps)` = `useMemo(() => fn, deps)`. useCallback trả về memoized function, useMemo trả về memoized value.

## Câu 27

[TYPE: TRUE_FALSE]

Mệnh đề: "Nên sử dụng useMemo và useCallback cho mọi giá trị và function."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Memoization có overhead (so sánh deps, lưu trữ). Chỉ dùng khi: (1) tính toán nặng, (2) truyền function xuống React.memo child, (3) dependency của hook khác. Phép tính đơn giản không cần memoize.

## Câu 28

[TYPE: MULTIPLE_CHOICE]

useReducer phù hợp khi nào hơn useState?

- [ ] Khi state chỉ là 1 boolean
- [x] Khi state phức tạp với nhiều sub-values và logic cập nhật liên quan
- [ ] Khi component không có state
- [ ] Khi dùng TypeScript

> **Giải thích:** useReducer tốt hơn useState khi: state phức tạp, nhiều actions liên quan, next state phụ thuộc previous state, logic cập nhật cần tái sử dụng/test riêng.

## Câu 29

[TYPE: MULTIPLE_CHOICE]

Reducer function phải là gì?

- [ ] Async function
- [x] Pure function (không side effects)
- [ ] Arrow function
- [ ] Class method

> **Giải thích:** Reducer phải là pure function: cùng input → cùng output, không side effects (API calls, localStorage, random, Date.now()). Side effects đặt ở useEffect hoặc middleware.

## Câu 30

[TYPE: MULTIPLE_CHOICE]

Context API giải quyết vấn đề gì?

- [ ] Performance optimization
- [ ] CSS styling
- [x] Prop drilling (truyền props qua nhiều tầng component)
- [ ] Routing

> **Giải thích:** Context cho phép truyền data xuống component tree mà không cần pass props qua từng level. Giải quyết prop drilling khi data cần dùng ở nhiều nơi.

## Câu 31

[TYPE: MULTIPLE_CHOICE]

Cách tạo Context đúng là?

- [ ] `const ctx = useContext()`
- [x] `const MyContext = createContext(defaultValue)`
- [ ] `const ctx = new Context()`
- [ ] `const ctx = React.context()`

> **Giải thích:** `createContext(defaultValue)` tạo Context object. `defaultValue` được dùng khi component không có Provider phía trên nó trong tree.

## Câu 32

[TYPE: MULTIPLE_CHOICE]

Khi nào component con re-render khi dùng Context?

- [ ] Khi bất kỳ state nào trong app thay đổi
- [x] Khi value của Provider mà nó subscribe thay đổi
- [ ] Khi parent component re-render
- [ ] Chỉ khi gọi forceUpdate()

> **Giải thích:** Component dùng `useContext(MyContext)` sẽ re-render khi giá trị value của nearest Provider thay đổi. Tuy nhiên, nếu parent re-render và value là object mới, tất cả consumers sẽ re-render.

## Câu 33

[TYPE: MULTIPLE_CHOICE]

Custom Hook phải bắt đầu bằng prefix nào?

- [ ] hook
- [x] use
- [ ] custom
- [ ] my

> **Giải thích:** Custom hooks PHẢI bắt đầu bằng "use" (ví dụ: `useLocalStorage`, `useFetch`). Đây là convention bắt buộc để React linter nhận diện và enforce rules of hooks.

## Câu 34

[TYPE: TRUE_FALSE]

Mệnh đề: "Custom Hook có thể sử dụng các hooks khác (useState, useEffect, etc.) bên trong nó."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Đó chính là mục đích của custom hooks — đóng gói và tái sử dụng logic stateful bằng cách sử dụng built-in hooks bên trong.

## Câu 35

[TYPE: MULTIPLE_CHOICE]

React Router v6 sử dụng component nào để định nghĩa routes?

- [ ] `<Switch>` và `<Route>`
- [x] `<Routes>` và `<Route>`
- [ ] `<Router>` và `<Path>`
- [ ] `<Navigation>` và `<Route>`

> **Giải thích:** React Router v6 sử dụng `<Routes>` (thay thế `<Switch>` ở v5) và `<Route>` với prop `element` thay vì `component`.

## Câu 36

[TYPE: MULTIPLE_CHOICE]

Hook nào dùng để lấy URL parameters trong React Router?

- [ ] useLocation
- [x] useParams
- [ ] useRoute
- [ ] useQuery

> **Giải thích:** `useParams()` trả về object chứa dynamic params. Ví dụ: route `/users/:id` → `const { id } = useParams()`.

## Câu 37

[TYPE: MULTIPLE_CHOICE]

Cách nào để navigate programmatically trong React Router v6?

- [ ] `this.props.history.push('/path')`
- [x] `const navigate = useNavigate(); navigate('/path')`
- [ ] `window.location.href = '/path'`
- [ ] `Router.push('/path')`

> **Giải thích:** React Router v6 dùng `useNavigate()` hook. `navigate('/path')` để chuyển trang, `navigate(-1)` để go back.

## Câu 38

[TYPE: MULTIPLE_CHOICE]

`<Outlet />` trong React Router dùng để làm gì?

- [ ] Redirect sang route khác
- [x] Render child route element trong nested routes
- [ ] Hiển thị loading state
- [ ] Catch 404 errors

> **Giải thích:** `<Outlet />` là placeholder trong parent route layout component. Khi user navigate đến child route, child element sẽ render tại vị trí `<Outlet />`.

## Câu 39

[TYPE: MULTIPLE_CHOICE]

Redux Toolkit sử dụng library nào để cho phép "mutate" state trực tiếp trong reducers?

- [ ] Lodash
- [x] Immer
- [ ] Ramda
- [ ] Immutable.js

> **Giải thích:** Redux Toolkit tích hợp Immer, cho phép viết code "mutative" (ví dụ: `state.count += 1`) nhưng thực tế tạo immutable update bên dưới.

## Câu 40

[TYPE: MULTIPLE_CHOICE]

createAsyncThunk trong Redux Toolkit dùng để làm gì?

- [ ] Tạo reducer synchronous
- [x] Xử lý async logic (API calls) và tự động dispatch pending/fulfilled/rejected
- [ ] Tạo middleware
- [ ] Connect component với store

> **Giải thích:** `createAsyncThunk` tạo async action creator. Nó tự động dispatch 3 action types: `pending`, `fulfilled`, `rejected` tương ứng với lifecycle của Promise.

## Câu 41

[TYPE: MULTIPLE_CHOICE]

React.memo dùng để làm gì?

- [ ] Memoize state values
- [x] Ngăn component re-render khi props không thay đổi (shallow comparison)
- [ ] Cache API responses
- [ ] Optimize CSS rendering

> **Giải thích:** `React.memo(Component)` tạo memoized version. Component chỉ re-render khi props thay đổi (shallow comparison). Tương tự PureComponent cho class.

## Câu 42

[TYPE: MULTIPLE_CHOICE]

Code splitting với React.lazy cần kết hợp với component nào?

- [ ] ErrorBoundary
- [x] Suspense
- [ ] Fragment
- [ ] Portal

> **Giải thích:** `React.lazy()` tạo component load bất đồng bộ. `Suspense` cung cấp fallback UI (loading) trong khi component đang được load.

## Câu 43

[TYPE: MULTIPLE_CHOICE]

Error Boundary có thể catch lỗi nào?

- [ ] Lỗi trong event handlers
- [x] Lỗi trong render methods và lifecycle của component con
- [ ] Lỗi trong async code (setTimeout, fetch)
- [ ] Tất cả các loại lỗi

> **Giải thích:** Error Boundary chỉ catch lỗi trong: rendering, lifecycle methods, constructor. KHÔNG catch: event handlers, async code, server-side rendering, lỗi trong chính boundary.

## Câu 44

[TYPE: TRUE_FALSE]

Mệnh đề: "Error Boundary có thể viết bằng functional component với hooks."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Hiện tại Error Boundary chỉ có thể viết bằng class component (cần `getDerivedStateFromError` hoặc `componentDidCatch`). Chưa có hook equivalent. Tuy nhiên, thư viện `react-error-boundary` cung cấp wrapper tiện lợi.

## Câu 45

[TYPE: MULTIPLE_CHOICE]

Higher-Order Component (HOC) là gì?

- [ ] Component có priority cao hơn
- [x] Hàm nhận component và trả về component mới với chức năng mở rộng
- [ ] Component cha trong tree
- [ ] Component có nhiều props

> **Giải thích:** HOC là pattern: `const Enhanced = withSomething(OriginalComponent)`. Nó wrap component gốc và thêm logic/props mới mà không modify component gốc.

## Câu 46

[TYPE: MULTIPLE_CHOICE]

Portal trong React dùng khi nào?

- [ ] Khi cần tạo route mới
- [x] Khi cần render children vào DOM node khác ngoài parent hierarchy
- [ ] Khi cần lazy load component
- [ ] Khi cần global state

> **Giải thích:** `createPortal(children, domNode)` render children vào domNode bất kỳ ngoài parent. Use cases: Modal, Tooltip, Dropdown (tránh overflow:hidden, z-index issues).

## Câu 47

[TYPE: MULTIPLE_CHOICE]

Fragment (<></>) trong React dùng để làm gì?

- [ ] Tạo ref cho DOM element
- [x] Group nhiều elements mà không tạo thêm DOM node
- [ ] Cache rendered output
- [ ] Handle errors

> **Giải thích:** Fragment cho phép return nhiều elements từ component mà không thêm wrapper div vào DOM. Cú pháp ngắn: `<>...</>` hoặc `<React.Fragment key={...}>`.

## Câu 48

[TYPE: MULTIPLE_CHOICE]

Trong React 18, Automatic Batching có nghĩa là gì?

- [ ] React tự động merge components
- [x] React gộp nhiều state updates thành một lần re-render duy nhất
- [ ] React batch API calls lại
- [ ] React tự compress bundle

> **Giải thích:** React 18 batch tất cả state updates (kể cả trong setTimeout, promises, native event handlers) thành 1 re-render. Trước v18, chỉ batch trong React event handlers.

## Câu 49

[TYPE: MULTIPLE_CHOICE]

`useId` hook dùng để làm gì?

- [ ] Tạo unique key cho list items
- [x] Tạo unique ID cho accessibility attributes (htmlFor, aria-describedby)
- [ ] Tạo ID cho database records
- [ ] Tạo session ID

> **Giải thích:** `useId()` tạo unique ID ổn định giữa server và client rendering. Dùng cho accessibility: liên kết label-input, aria attributes. KHÔNG dùng làm key cho lists.

## Câu 50

[TYPE: SELECT_RESULT]

Đoạn code sau có bao nhiêu lần re-render sau khi click button?

```jsx
function App() {
  const [a, setA] = useState(0);
  const [b, setB] = useState(0);
  const [c, setC] = useState(0);

  const handleClick = () => {
    setA(1);
    setB(2);
    setC(3);
  };

  return <button onClick={handleClick}>Click</button>;
}
```

- [x] 1 lần
- [ ] 3 lần
- [ ] 0 lần
- [ ] Vô hạn

> **Giải thích:** React 18 automatic batching gộp cả 3 state updates thành 1 lần re-render duy nhất, bất kể chúng ở đâu.

## Câu 51

[TYPE: MULTIPLE_CHOICE]

`useTransition` hook dùng cho mục đích gì?

- [ ] Animation transitions
- [x] Đánh dấu state update là non-urgent để UI responsive hơn
- [ ] Route transitions
- [ ] CSS transitions

> **Giải thích:** `useTransition` đánh dấu update là "transition" (non-urgent). React ưu tiên urgent updates (typing, clicking) trước, transition updates có thể bị interrupt.

## Câu 52

[TYPE: MULTIPLE_CHOICE]

Sự khác biệt giữa `useEffect` và `useLayoutEffect`?

- [ ] Không có sự khác biệt
- [x] useLayoutEffect chạy synchronously SAU DOM mutation nhưng TRƯỚC browser paint
- [ ] useLayoutEffect chạy trước useEffect
- [ ] useLayoutEffect chỉ dùng cho animations

> **Giải thích:** `useLayoutEffect` chạy synchronously sau DOM changes nhưng trước browser paint → dùng khi cần đọc/thay đổi layout (tránh flicker). `useEffect` chạy asynchronously sau paint.

## Câu 53

[TYPE: MULTIPLE_CHOICE]

`StrictMode` trong React làm gì?

- [ ] Enforce TypeScript types
- [x] Detect unsafe lifecycles và double-invoke functions để phát hiện side effects
- [ ] Minify code
- [ ] Enable production optimizations

> **Giải thích:** `StrictMode` (chỉ development): double-render components, double-invoke effects, warn about deprecated APIs, detect unexpected side effects. Không ảnh hưởng production.

## Câu 54

[TYPE: TRUE_FALSE]

Mệnh đề: "React re-render component con khi component cha re-render, ngay cả khi props không đổi."

- [x] Đúng
- [ ] Sai

> **Giải thích:** Mặc định, khi parent re-render, tất cả children cũng re-render (dù props không đổi). Dùng `React.memo` để ngăn re-render không cần thiết.

## Câu 55

[TYPE: MULTIPLE_CHOICE]

Cách nào tốt nhất để fetch data trong React component?

- [ ] Gọi fetch trong render function
- [ ] Dùng async componentDidMount
- [x] Dùng useEffect (hoặc thư viện như React Query/SWR)
- [ ] Dùng constructor

> **Giải thích:** useEffect là nơi thực hiện side effects (fetch data). Thư viện như React Query/SWR cung cấp caching, refetching, error handling tốt hơn.

## Câu 56

[TYPE: MULTIPLE_CHOICE]

Controlled vs Uncontrolled components — khi nào dùng uncontrolled?

- [ ] Luôn luôn dùng uncontrolled
- [ ] Khi form có nhiều validation
- [x] Khi cần đơn giản (file input) hoặc integrate với non-React code
- [ ] Khi dùng Redux

> **Giải thích:** Uncontrolled components (useRef) phù hợp cho: file input (bắt buộc uncontrolled), form đơn giản không cần validation real-time, hoặc tích hợp với third-party DOM libraries.

## Câu 57

[TYPE: SELECT_RESULT]

Đoạn code sau có vấn đề gì?

```jsx
function App() {
  const [items, setItems] = useState([1, 2, 3]);

  const handleClick = () => {
    items.push(4);
    setItems(items);
  };

  return <button onClick={handleClick}>Add</button>;
}
```

- [ ] Hoạt động bình thường
- [x] Mutate state trực tiếp và setItems cùng reference → không re-render
- [ ] TypeError khi push
- [ ] Infinite loop

> **Giải thích:** `items.push(4)` mutate array gốc. `setItems(items)` set cùng reference → React thấy không thay đổi → không re-render. Fix: `setItems([...items, 4])` hoặc `setItems(prev => [...prev, 4])`.

## Câu 58

[TYPE: MULTIPLE_CHOICE]

forwardRef dùng khi nào?

- [ ] Khi component cần nhiều props
- [x] Khi cần truyền ref từ parent xuống child's DOM element
- [ ] Khi cần forward props
- [ ] Khi component render nhiều elements

> **Giải thích:** `forwardRef` cho phép parent truyền ref xuống child component để truy cập DOM element bên trong child. Ví dụ: custom Input component expose ref đến `<input>` tag.

## Câu 59

[TYPE: MULTIPLE_CHOICE]

`dangerouslySetInnerHTML` dùng khi nào và tại sao "dangerous"?

- [ ] Khi cần render text content
- [x] Khi cần render HTML string — dangerous vì có thể gây XSS attack
- [ ] Khi cần style inline
- [ ] Khi component có error

> **Giải thích:** `dangerouslySetInnerHTML={{__html: htmlString}}` render HTML trực tiếp. "Dangerous" vì nếu htmlString chứa script từ user input → XSS attack. Luôn sanitize trước khi dùng.

## Câu 60

[TYPE: MULTIPLE_CHOICE]

Sự khác biệt chính giữa `npm create vite` và `create-react-app`?

- [ ] Không có sự khác biệt
- [x] Vite nhanh hơn nhiều (dùng ESBuild/SWC) và hỗ trợ HMR tốt hơn
- [ ] CRA mới hơn Vite
- [ ] Vite chỉ dùng cho Vue

> **Giải thích:** Vite sử dụng native ESM + ESBuild/SWC cho development (cực nhanh), HMR gần như instant. CRA dùng Webpack (chậm hơn nhiều). CRA đã deprecated, Vite là lựa chọn hiện đại.

## Câu 61

[TYPE: MULTIPLE_CHOICE]

Trong React, lifecycle method nào tương đương với `useEffect(() => {...}, [])`?

- [ ] componentWillMount
- [x] componentDidMount
- [ ] componentWillUpdate
- [ ] componentDidUpdate

> **Giải thích:** `useEffect` với dependency array rỗng `[]` chạy 1 lần sau mount, tương tự `componentDidMount`. Cleanup function tương tự `componentWillUnmount`.

## Câu 62

[TYPE: MULTIPLE_CHOICE]

Cách nào đúng để prevent default form submission trong React?

- [ ] `<form onSubmit="return false">`
- [x] `<form onSubmit={(e) => { e.preventDefault(); }}>`
- [ ] `<form preventDefault>`
- [ ] `<form noSubmit>`

> **Giải thích:** Trong React, gọi `e.preventDefault()` trong event handler function. Khác HTML thuần dùng `return false` hoặc attribute.

## Câu 63

[TYPE: TRUE_FALSE]

Mệnh đề: "useEffect chạy TRƯỚC khi component render lên màn hình."

- [ ] Đúng
- [x] Sai

> **Giải thích:** useEffect chạy SAU render (asynchronously, sau browser paint). Nếu cần chạy trước paint, dùng `useLayoutEffect`.

## Câu 64

[TYPE: MULTIPLE_CHOICE]

React Fiber là gì?

- [ ] Một thư viện CSS
- [x] Kiến trúc reconciliation mới của React (từ v16) cho phép chia nhỏ rendering work
- [ ] Fiber optic networking cho React apps
- [ ] Một state management library

> **Giải thích:** React Fiber (v16+) là thuật toán reconciliation mới cho phép chia rendering work thành units, pause/resume, set priorities. Nền tảng cho Concurrent Features.

## Câu 65

[TYPE: MULTIPLE_CHOICE]

Cách nào tốt nhất để share logic giữa các components trong React hiện đại?

- [ ] Inheritance (class extends)
- [ ] Mixins
- [x] Custom Hooks
- [ ] Global variables

> **Giải thích:** Custom Hooks là cách hiện đại và khuyến nghị nhất để share stateful logic. HOC và Render Props vẫn dùng được nhưng hooks đơn giản hơn.

## Câu 66

[TYPE: SELECT_RESULT]

Đoạn code sau render ra gì khi `show` = false?

```jsx
function App({ show }) {
  return (
    <div>
      {show ? <ComponentA /> : null}
    </div>
  );
}
```

- [ ] `<div><ComponentA /></div>`
- [x] `<div></div>` (div rỗng)
- [ ] Error
- [ ] Không render gì cả

> **Giải thích:** Khi `show` = false, expression trả về `null`. React render `null` nghĩa là không hiển thị gì trong DOM, nhưng vẫn render wrapper `<div>`.

## Câu 67

[TYPE: MULTIPLE_CHOICE]

`React.StrictMode` double-renders components. Điều này ảnh hưởng gì đến production?

- [x] Không ảnh hưởng — StrictMode chỉ hoạt động trong development
- [ ] Giảm performance 50%
- [ ] Tăng bundle size
- [ ] Gây memory leaks

> **Giải thích:** StrictMode hoàn toàn bị bỏ qua trong production build. Double-rendering chỉ xảy ra trong development để phát hiện side effects.

## Câu 68

[TYPE: MULTIPLE_CHOICE]

Khi nào nên tách component thành component riêng?

- [ ] Khi có hơn 100 dòng code
- [ ] Khi component có state
- [x] Khi UI hoặc logic có thể tái sử dụng, hoặc khi component quá phức tạp
- [ ] Luôn luôn tách thành 1 component = 1 element

> **Giải thích:** Tách component khi: (1) Phần UI được dùng ở nhiều nơi, (2) Component quá lớn/phức tạp, (3) Logic cần isolate, (4) Performance (memo từng phần). Không cần over-split.

## Câu 69

[TYPE: MULTIPLE_CHOICE]

Server Components (React 19+) khác Client Components ở điểm nào?

- [ ] Server Components nhanh hơn
- [x] Server Components chạy trên server, không gửi JS xuống client, có thể truy cập database trực tiếp
- [ ] Client Components không thể fetch data
- [ ] Server Components không thể render HTML

> **Giải thích:** Server Components: chạy trên server, output HTML, không gửi JS bundle. Client Components: chạy trên browser, có state/effects. Kết hợp cả hai cho best performance + interactivity.

## Câu 70

[TYPE: MULTIPLE_CHOICE]

Directive `'use client'` trong Next.js/React 19 có nghĩa gì?

- [ ] Component chỉ chạy trên client (SSR bị skip)
- [x] Component là Client Component — cần JavaScript, có thể dùng state/effects/events
- [ ] Component bị cached trên client
- [ ] Component chỉ visible trên client side

> **Giải thích:** `'use client'` đánh dấu boundary giữa Server và Client Components. Component có directive này (và children) sẽ được hydrated trên client, có thể dùng hooks và event handlers.

## Câu 71

[TYPE: MULTIPLE_CHOICE]

Zustand khác Redux ở điểm nào?

- [ ] Zustand không quản lý state
- [x] Zustand đơn giản hơn, ít boilerplate, không cần Provider wrapper
- [ ] Redux nhanh hơn Zustand
- [ ] Zustand chỉ cho server components

> **Giải thích:** Zustand: minimal API, hook-based, không cần Provider, ít boilerplate. Redux (Toolkit): powerful nhưng nhiều concepts (slices, thunks, selectors), phù hợp app lớn.

## Câu 72

[TYPE: MULTIPLE_CHOICE]

React Query (TanStack Query) giải quyết vấn đề gì?

- [ ] Routing
- [x] Server state management (caching, refetching, synchronization, pagination)
- [ ] CSS-in-JS
- [ ] Form validation

> **Giải thích:** React Query quản lý "server state" (data từ API): automatic caching, background refetching, optimistic updates, pagination, infinite queries. Khác "client state" (UI state).

## Câu 73

[TYPE: SELECT_RESULT]

Đoạn code sau có vấn đề gì?

```jsx
function App() {
  const [count, setCount] = useState(0);

  useEffect(() => {
    setInterval(() => {
      setCount(count + 1);
    }, 1000);
  }, []);

  return <p>{count}</p>;
}
```

- [ ] Hoạt động đúng, count tăng mỗi giây
- [x] count luôn = 1 vì stale closure
- [ ] TypeError
- [ ] Infinite loop

> **Giải thích:** `count` trong closure luôn = 0 (giá trị khi effect mount). `setCount(0 + 1)` mỗi giây → luôn = 1. Fix: `setCount(prev => prev + 1)` hoặc thêm `count` vào deps (nhưng cần cleanup).

## Câu 74

[TYPE: MULTIPLE_CHOICE]

Pattern nào tốt nhất để tránh prop drilling sâu 5+ levels?

- [ ] Global variables
- [ ] Truyền props qua từng level
- [x] Context API hoặc state management library
- [ ] Local Storage

> **Giải thích:** Khi data cần truyền qua nhiều levels (5+), Context API hoặc state management (Redux, Zustand) là giải pháp tốt nhất. Tránh prop drilling giúp code cleaner và maintainable.

## Câu 75

[TYPE: MULTIPLE_CHOICE]

Cách tối ưu nào phù hợp cho list có 10,000+ items?

- [ ] React.memo mỗi item
- [ ] useMemo cho toàn bộ list
- [x] Virtualization (react-window, react-virtualized)
- [ ] Pagination phía client

> **Giải thích:** Virtualization chỉ render items visible trong viewport (thường 10-20), không phải tất cả 10,000. Giảm DOM nodes từ 10,000 xuống ~20, cải thiện performance đáng kể.

## Câu 76

[TYPE: MULTIPLE_CHOICE]

`useImperativeHandle` dùng cho mục đích gì?

- [ ] Handle form submission
- [x] Customize ref value exposed cho parent khi dùng forwardRef
- [ ] Handle keyboard events
- [ ] Manage state updates

> **Giải thích:** `useImperativeHandle(ref, () => ({ focus, scroll }))` cho phép child component expose custom methods qua ref thay vì expose toàn bộ DOM node.

## Câu 77

[TYPE: TRUE_FALSE]

Mệnh đề: "React component name phải bắt đầu bằng chữ hoa (PascalCase)."

- [x] Đúng
- [ ] Sai

> **Giải thích:** React phân biệt custom components và HTML elements qua chữ hoa đầu tiên. `<div>` → HTML element, `<MyComponent>` → custom component. Nếu viết `<myComponent>`, React sẽ treat nó như HTML element.

## Câu 78

[TYPE: MULTIPLE_CHOICE]

React DevTools Profiler dùng để làm gì?

- [ ] Profile network requests
- [x] Đo thời gian render của components, phát hiện unnecessary re-renders
- [ ] Profile CSS performance
- [ ] Debug Redux store

> **Giải thích:** React Profiler ghi lại mỗi commit (render cycle): thời gian render mỗi component, tại sao re-render, component nào tốn thời gian nhất. Giúp optimize performance.

## Câu 79

[TYPE: MULTIPLE_CHOICE]

Concurrent Mode trong React 18 cho phép gì?

- [ ] Chạy nhiều React apps đồng thời
- [x] React có thể interrupt rendering để xử lý urgent updates trước
- [ ] Multi-threading trong JavaScript
- [ ] Parallel API calls

> **Giải thích:** Concurrent rendering cho phép React: interrupt long renders, prioritize urgent updates (typing), render in background mà không block UI. Nền tảng cho useTransition, Suspense.

## Câu 80

[TYPE: MULTIPLE_CHOICE]

Hydration trong SSR là gì?

- [ ] Thêm CSS vào HTML
- [x] Quá trình React attach event listeners và state vào HTML đã render trên server
- [ ] Caching HTML responses
- [ ] Minify JavaScript

> **Giải thích:** Server render HTML tĩnh → client download HTML + JS → React "hydrate" (gắn event handlers, state) vào existing HTML thay vì render lại từ đầu. Giúp page interactive nhanh hơn.

## Câu 81

[TYPE: MULTIPLE_CHOICE]

Trong React, `children` prop có thể nhận kiểu dữ liệu nào?

- [ ] Chỉ JSX elements
- [ ] Chỉ strings
- [ ] Chỉ arrays
- [x] Bất kỳ: JSX, string, number, array, function, null

> **Giải thích:** `children` là prop đặc biệt, có thể là: single element, multiple elements, string, number, array, function (render props pattern), boolean, null/undefined.

## Câu 82

[TYPE: MULTIPLE_CHOICE]

`e.stopPropagation()` trong React event handler dùng để?

- [ ] Ngăn form submission
- [ ] Cancel async operations
- [x] Ngăn event bubble lên parent elements
- [ ] Stop component re-rendering

> **Giải thích:** `stopPropagation()` ngăn event propagation (bubbling) lên ancestors. Ví dụ: click button trong Modal không trigger onClick của overlay đằng sau.

## Câu 83

[TYPE: MULTIPLE_CHOICE]

Cách nào đúng để conditionally apply CSS class trong React?

- [ ] `<div class={isActive ? 'active' : ''}>`
- [x] `<div className={isActive ? 'active' : ''}>`
- [ ] `<div className={if(isActive) 'active'}>`
- [ ] `<div css={isActive && 'active'}>`

> **Giải thích:** JSX dùng `className` (không phải `class`). Conditional class dùng ternary, template literal: `` className={`card ${isActive ? 'active' : ''}`} ``, hoặc library classnames/clsx.

## Câu 84

[TYPE: MULTIPLE_CHOICE]

`useDebugValue` hook dùng khi nào?

- [ ] Debug production errors
- [x] Hiển thị label cho custom hooks trong React DevTools
- [ ] Log state changes
- [ ] Debug network requests

> **Giải thích:** `useDebugValue(value)` dùng trong custom hooks để hiển thị readable value trong React DevTools. Chỉ dùng cho hooks phức tạp cần debug.

## Câu 85

[TYPE: SELECT_RESULT]

Đoạn code sau, component `Child` render bao nhiêu lần khi click button?

```jsx
const Child = React.memo(({ onClick }) => {
  console.log('Child rendered');
  return <button onClick={onClick}>Click</button>;
});

function Parent() {
  const [count, setCount] = useState(0);
  const handleClick = () => setCount(c => c + 1);

  return (
    <div>
      <p>{count}</p>
      <Child onClick={handleClick} />
    </div>
  );
}
```

- [ ] 0 lần (chỉ render ban đầu)
- [x] Mỗi lần click (vì handleClick reference mới mỗi render)
- [ ] Chỉ 1 lần
- [ ] Error

> **Giải thích:** `handleClick` là function mới mỗi render → memo thấy props thay đổi → Child re-render. Fix: `const handleClick = useCallback(() => setCount(c => c + 1), [])`.

## Câu 86

[TYPE: MULTIPLE_CHOICE]

React Hook Form ưu điểm gì so với controlled components?

- [ ] Đơn giản hơn
- [x] Ít re-renders hơn (uncontrolled approach), performance tốt hơn cho form lớn
- [ ] Hỗ trợ TypeScript
- [ ] Có UI components sẵn

> **Giải thích:** React Hook Form dùng uncontrolled inputs (refs) → input changes không trigger re-render toàn form. Chỉ re-render khi submit hoặc có error. Rất hiệu quả cho forms phức tạp.

## Câu 87

[TYPE: MULTIPLE_CHOICE]

`key` prop có truyền vào component con được không?

- [ ] Có, dùng props.key
- [x] Không, key là prop đặc biệt React giữ lại, không truyền vào component
- [ ] Có, nhưng chỉ trong class components
- [ ] Chỉ khi dùng forwardRef

> **Giải thích:** `key` và `ref` là reserved props. React sử dụng chúng internally và KHÔNG truyền vào component. Nếu cần giá trị key trong child, truyền thêm prop khác: `<Item key={id} id={id} />`.

## Câu 88

[TYPE: MULTIPLE_CHOICE]

Sự khác biệt giữa `<Link>` và `<a>` tag trong React Router?

- [ ] Không có sự khác biệt
- [x] Link dùng client-side navigation (không full page reload), `<a>` gây full reload
- [ ] Link nhanh hơn `<a>`
- [ ] `<a>` không hoạt động trong React

> **Giải thích:** `<Link to="/path">` sử dụng History API cho client-side navigation (SPA behavior). `<a href="/path">` gây full page reload, mất state, chậm hơn.

## Câu 89

[TYPE: MULTIPLE_CHOICE]

Cách nào KHÔNG phải best practice khi dùng useEffect?

- [ ] Cleanup subscriptions trong return function
- [ ] Sử dụng AbortController cho fetch
- [x] Đặt object/array làm dependency mà không memoize
- [ ] Check mounted status trước setState

> **Giải thích:** Object/array mới mỗi render (reference khác) → useEffect chạy vô hạn. Fix: useMemo dependency, hoặc chỉ dùng primitive values trong deps, hoặc JSON.stringify (hack).

## Câu 90

[TYPE: MULTIPLE_CHOICE]

`flushSync` trong React 18 dùng để?

- [ ] Clear React cache
- [x] Force synchronous re-render ngay lập tức (opt-out of batching)
- [ ] Flush CSS styles
- [ ] Sync state với server

> **Giải thích:** `flushSync(() => setState(x))` force React render ngay lập tức (không batch). Dùng khi cần DOM update ngay (ví dụ: scroll to element ngay sau state change).

## Câu 91

[TYPE: TRUE_FALSE]

Mệnh đề: "Hooks có thể gọi bên trong một callback function (ví dụ: onClick handler)."

- [ ] Đúng
- [x] Sai

> **Giải thích:** Hooks chỉ được gọi ở TOP LEVEL của component hoặc custom hook. Không được gọi bên trong callbacks, loops, conditions, hoặc nested functions.

## Câu 92

[TYPE: MULTIPLE_CHOICE]

Pattern "Compound Components" trong React là gì?

- [ ] Components kết hợp nhiều libraries
- [x] Nhóm components chia sẻ implicit state, hoạt động cùng nhau (ví dụ: Tabs, Accordion)
- [ ] Components với nhiều renders
- [ ] Components không có props

> **Giải thích:** Compound Components: `<Tabs><Tab/><Tab/></Tabs>` — parent quản lý state ngầm, children giao tiếp qua context. Giống HTML native `<select><option/></select>`.

## Câu 93

[TYPE: MULTIPLE_CHOICE]

Khi nào nên dùng `useLayoutEffect` thay vì `useEffect`?

- [ ] Luôn luôn
- [ ] Khi fetch data
- [x] Khi cần đọc/thay đổi DOM layout trước browser paint (tránh visual flicker)
- [ ] Khi dùng TypeScript

> **Giải thích:** `useLayoutEffect` dùng khi: đo DOM dimensions, reposition elements, hoặc bất kỳ tình huống cần DOM changes trước user nhìn thấy. Nếu không liên quan visual → dùng `useEffect`.

## Câu 94

[TYPE: MULTIPLE_CHOICE]

Suspense trong React 18 có thể dùng cho?

- [ ] Chỉ code splitting
- [ ] Chỉ data fetching
- [x] Code splitting, data fetching (với compatible libraries), và lazy components
- [ ] Error handling

> **Giải thích:** React 18 Suspense hỗ trợ: lazy components (code splitting), data fetching (React Query, Relay, SWR với suspense mode), và các async operations khác.

## Câu 95

[TYPE: MULTIPLE_CHOICE]

Cách nào đúng để type-safe một event handler trong TypeScript + React?

- [ ] `const handleClick = (e) => {...}`
- [x] `const handleClick = (e: React.MouseEvent<HTMLButtonElement>) => {...}`
- [ ] `const handleClick: EventHandler = (e) => {...}`
- [ ] `const handleClick = (e: Event) => {...}`

> **Giải thích:** React cung cấp generic event types: `React.MouseEvent<T>`, `React.ChangeEvent<T>`, `React.FormEvent<T>` với generic parameter là element type cụ thể.

## Câu 96

[TYPE: MULTIPLE_CHOICE]

`useDeferredValue` khác `useTransition` ở điểm nào?

- [x] useDeferredValue defer giá trị (không cần control state setter), useTransition wrap state update
- [ ] Không có sự khác biệt
- [ ] useDeferredValue cho arrays, useTransition cho objects
- [ ] useDeferredValue nhanh hơn

> **Giải thích:** `useTransition`: wrap setter → `startTransition(() => setState(x))`. `useDeferredValue(value)`: nhận value và trả về deferred version. Dùng khi không control nguồn update (ví dụ: prop từ parent).

## Câu 97

[TYPE: MULTIPLE_CHOICE]

Optimistic Update pattern là gì?

- [ ] Update state sau khi API thành công
- [x] Update UI ngay lập tức (giả sử API sẽ thành công), rollback nếu fail
- [ ] Cache API responses
- [ ] Prefetch data trước khi user action

> **Giải thích:** Optimistic update: update UI immediately → call API → nếu fail thì rollback. Giúp UI responsive hơn (không chờ network). Ví dụ: like button, add to cart.

## Câu 98

[TYPE: MULTIPLE_CHOICE]

React 19 giới thiệu `use()` hook dùng để?

- [ ] Thay thế useState
- [x] Đọc Promise hoặc Context trực tiếp (có thể gọi conditionally)
- [ ] Sử dụng third-party libraries
- [ ] Manage form state

> **Giải thích:** `use()` hook mới (React 19): đọc Promise (suspend cho đến khi resolve) hoặc Context. Khác hooks khác: CÓ THỂ gọi trong conditions/loops. Thay thế useContext trong một số cases.

## Câu 99

[TYPE: MULTIPLE_CHOICE]

Khi test React component, `screen.getByRole` ưu tiên hơn `getByTestId` vì sao?

- [ ] Nhanh hơn
- [x] Test behavior/accessibility thay vì implementation details
- [ ] Dễ viết hơn
- [ ] Không cần thêm attributes vào HTML

> **Giải thích:** `getByRole` query dựa trên accessibility role (như user/screen reader tương tác). `getByTestId` phụ thuộc vào implementation (data-testid attribute). RTL philosophy: test như user sử dụng.

## Câu 100

[TYPE: MULTIPLE_CHOICE]

Đâu là nguyên tắc quan trọng nhất khi thiết kế React components?

- [ ] Components phải nhỏ nhất có thể
- [ ] Luôn sử dụng TypeScript
- [x] Single Responsibility — mỗi component có một trách nhiệm rõ ràng
- [ ] Không bao giờ dùng state

> **Giải thích:** Single Responsibility Principle: mỗi component nên có 1 lý do để thay đổi. Component quá lớn → khó maintain/test/reuse. Tách thành smaller, focused components khi logic phức tạp.
