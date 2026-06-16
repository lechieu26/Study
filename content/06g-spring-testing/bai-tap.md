# Spring Testing - Bài Tập

## Bài 1: Unit Test Service Layer
**Độ khó: Trung bình**

1. Tạo `NhanVienService` với methods: layTatCa, layTheoId, taoMoi, capNhat, xoa.
2. Viết unit tests với Mockito cho TẤT CẢ methods.
3. Test cases: happy path, not found, duplicate, validation fail.
4. Sử dụng `@ExtendWith(MockitoExtension.class)`, `@Mock`, `@InjectMocks`.
5. Sử dụng `ArgumentCaptor` verify entity được save đúng.
6. Coverage >= 90% cho service class.

---

## Bài 2: Integration Test với MockMvc
**Độ khó: Trung bình**

1. Test REST API endpoints với `@SpringBootTest` + `@AutoConfigureMockMvc`.
2. Test GET /api/nhan-vien (pagination, filtering).
3. Test POST (valid data → 201, invalid → 400, duplicate → 409).
4. Test PUT (existing → 200, not found → 404).
5. Test DELETE (existing → 204, not found → 404).
6. Sử dụng `@Transactional` để rollback sau mỗi test.

---

## Bài 3: Repository Test với @DataJpaTest
**Độ khó: Trung bình**

1. Test derived query methods: findByEmail, findByLuongBetween, existsByEmail.
2. Test custom @Query methods: findAllWithPhongBan (JOIN FETCH).
3. Test pagination: findAll(Pageable).
4. Test Specification-based queries.
5. Sử dụng `TestEntityManager` setup test data.
6. Verify SQL queries generated (log output).

---

## Bài 4: Security Testing
**Độ khó: Trung bình - Khó**

1. Test unauthenticated access → 401.
2. Test with mock user (`@WithMockUser(roles="USER")`) → allowed endpoints.
3. Test insufficient role → 403.
4. Test JWT authentication flow: register → login → access protected endpoint.
5. Test expired token → 401.
6. Test method-level security (`@PreAuthorize`).

---

## Bài 5: TestContainers và E2E Test
**Độ khó: Khó**

1. Setup PostgreSQL container cho integration tests.
2. Test full flow: create → read → update → delete với real database.
3. Test concurrent operations: 2 users update cùng resource → optimistic locking.
4. Test batch operations: insert 100 records, verify count.
5. Test database migrations (Flyway) run correctly.
6. Verify performance: query execution time < 100ms for indexed fields.
