# Spring Testing - Quiz

## Câu 1
[TYPE: MULTIPLE_CHOICE]
Test pyramid từ dưới lên trên:

A. E2E → Integration → Unit
B. Unit → Integration → E2E
C. Integration → Unit → E2E
D. Unit → E2E → Integration

**Đáp án: B**
> Bottom: Unit tests (nhiều nhất, nhanh nhất) → Integration tests → Top: E2E tests (ít nhất, chậm nhất).

## Câu 2
[TYPE: MULTIPLE_CHOICE]
@ExtendWith(MockitoExtension.class) dùng để:

A. Start Spring context
B. Enable Mockito annotations (@Mock, @InjectMocks) KHÔNG cần Spring context
C. Run tests in parallel
D. Generate test data

**Đáp án: B**
> MockitoExtension enable @Mock, @InjectMocks. Lightweight - không load Spring context → nhanh.

## Câu 3
[TYPE: TRUE_FALSE]
@Mock tạo mock object, @InjectMocks tạo real object và inject mocks vào dependencies.

**Đáp án: TRUE**
> @Mock = fake dependency. @InjectMocks = real class under test, auto-inject @Mock vào constructor/field.

## Câu 4
[TYPE: SELECT_RESULT]
```java
when(repo.findById(1L)).thenReturn(Optional.of(user));
UserDTO result = service.getById(1L);
verify(repo).findById(1L);
```
Pattern testing này gọi là:

A. TDD
B. BDD
C. AAA (Arrange-Act-Assert)
D. Integration testing

**Đáp án: C**
> Arrange (when/setup) → Act (service.getById) → Assert/Verify (verify repo called).

## Câu 5
[TYPE: MULTIPLE_CHOICE]
@SpringBootTest load:

A. Chỉ 1 bean
B. Toàn bộ Spring ApplicationContext (full integration test)
C. Chỉ controllers
D. Chỉ repositories

**Đáp án: B**
> @SpringBootTest load FULL context. Dùng cho integration tests. Chậm hơn unit tests.

## Câu 6
[TYPE: MULTIPLE_CHOICE]
@WebMvcTest(UserController.class) load gì?

A. Full context
B. Chỉ Web layer: controller, filters, ControllerAdvice (KHÔNG service, repo)
C. Chỉ repository
D. Toàn bộ beans

**Đáp án: B**
> @WebMvcTest = slice test cho web layer. Services phải @MockBean. Nhanh hơn @SpringBootTest.

## Câu 7
[TYPE: TRUE_FALSE]
@DataJpaTest mặc định sử dụng in-memory database (H2) và @Transactional (auto-rollback).

**Đáp án: TRUE**
> @DataJpaTest: replace datasource với embedded DB, auto-rollback, chỉ load JPA components.

## Câu 8
[TYPE: SELECT_RESULT]
```java
mockMvc.perform(get("/api/users")
        .param("page", "0").param("size", "5"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.content", hasSize(5)));
```
Test này verify gì?

A. Database có 5 records
B. API trả 200 OK với page chứa 5 items
C. Service method được gọi
D. Controller throw exception

**Đáp án: B**
> MockMvc verify: HTTP status 200 + JSON response body content[].size == 5.

## Câu 9
[TYPE: MULTIPLE_CHOICE]
ArgumentCaptor dùng để:

A. Capture exceptions
B. Capture arguments passed to mock method calls để verify
C. Capture HTTP responses
D. Capture logs

**Đáp án: B**
> ArgumentCaptor.forClass(User.class) → verify(repo).save(captor.capture()) → captor.getValue().

## Câu 10
[TYPE: MULTIPLE_CHOICE]
@MockBean vs @Mock:

A. Giống nhau
B. @MockBean thay bean trong Spring context, @Mock không cần Spring context
C. @Mock mạnh hơn
D. @MockBean chỉ cho repository

**Đáp án: B**
> @MockBean = mock + replace bean trong ApplicationContext. @Mock = Mockito mock, no Spring.

## Câu 11
[TYPE: TRUE_FALSE]
TestContainers cho phép chạy real database (PostgreSQL, MySQL) trong Docker containers cho tests.

**Đáp án: TRUE**
> TestContainers start/stop containers automatically. Real DB cho integration tests chính xác hơn H2.

## Câu 12
[TYPE: MULTIPLE_CHOICE]
verify(mock, times(2)).method() kiểm tra:

A. Method được gọi tối đa 2 lần
B. Method được gọi đúng 2 lần
C. Method được gọi ít nhất 2 lần
D. Method chạy trong 2ms

**Đáp án: B**
> times(2) = exactly 2 invocations. atLeast(2), atMost(2), never() cho các case khác.

## Câu 13
[TYPE: SELECT_RESULT]
```java
@Test
void shouldFail() {
    assertThatThrownBy(() -> service.getById(99L))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessageContaining("not found");
}
```
Test này dùng thư viện assertion nào?

A. JUnit 5 Assertions
B. AssertJ
C. Hamcrest
D. Truth

**Đáp án: B**
> assertThatThrownBy() là AssertJ fluent API. JUnit 5 dùng assertThrows().

## Câu 14
[TYPE: MULTIPLE_CHOICE]
@Transactional trên test class:

A. Bắt buộc commit sau test
B. Auto-rollback sau mỗi test method (isolation giữa tests)
C. Disable transactions
D. Lock database

**Đáp án: B**
> @Transactional trên test → auto-rollback. Database clean sau mỗi test. Test isolation.

## Câu 15
[TYPE: MULTIPLE_CHOICE]
@WithMockUser dùng trong context nào?

A. Unit test service
B. Security testing (mock authenticated user trong SecurityContext)
C. Database testing
D. Performance testing

**Đáp án: B**
> @WithMockUser populate SecurityContextHolder với mock Authentication cho security-related tests.
