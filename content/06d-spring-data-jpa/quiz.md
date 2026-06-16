# Spring Data JPA - Quiz

## Câu 1
[TYPE: MULTIPLE_CHOICE]
Spring Data JPA nằm ở tầng nào trong stack?

A. Application → JDBC → Database
B. Application → Spring Data JPA → JPA (Hibernate) → JDBC → Database
C. Application → Database
D. Application → JPA → Spring Data JPA → JDBC

**Đáp án: B**
> Stack: Code → Spring Data JPA (abstractions) → JPA spec (Hibernate implementation) → JDBC → Database.

## Câu 2
[TYPE: MULTIPLE_CHOICE]
@Entity annotation đánh dấu class:

A. Là một Spring Bean
B. Được map tới database table
C. Là REST controller
D. Có transaction support

**Đáp án: B**
> @Entity = JPA annotation đánh dấu class là persistent entity, map tới table.

## Câu 3
[TYPE: TRUE_FALSE]
@Id + @GeneratedValue(strategy = GenerationType.IDENTITY) sử dụng database auto-increment.

**Đáp án: TRUE**
> IDENTITY strategy dùng auto-increment column của database (MySQL, PostgreSQL serial).

## Câu 4
[TYPE: SELECT_RESULT]
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "department_id")
private Department department;
```
Khi load Employee, department được load khi nào?

A. Ngay lập tức cùng Employee
B. Khi lần đầu access employee.getDepartment()
C. Không bao giờ
D. Khi persist

**Đáp án: B**
> LAZY = proxy. Department chỉ load khi access lần đầu (trigger SELECT query).

## Câu 5
[TYPE: MULTIPLE_CHOICE]
FetchType default cho @ManyToOne là:

A. LAZY
B. EAGER
C. Không có default
D. FETCH

**Đáp án: B**
> @ManyToOne và @OneToOne mặc định EAGER. @OneToMany và @ManyToMany mặc định LAZY.

## Câu 6
[TYPE: MULTIPLE_CHOICE]
N+1 problem là gì?

A. Database chỉ cho phép N+1 connections
B. 1 query lấy N records + N queries thêm cho mỗi relationship
C. Pagination lỗi
D. Transaction timeout

**Đáp án: B**
> Load list (1 query) → access relationship mỗi item (N queries). Tổng: N+1 queries.

## Câu 7
[TYPE: MULTIPLE_CHOICE]
Giải pháp N+1 problem:

A. JOIN FETCH trong JPQL
B. @EntityGraph
C. @BatchSize
D. Tất cả đáp án trên

**Đáp án: D**
> JOIN FETCH, @EntityGraph, @BatchSize đều giải quyết N+1. Mỗi cách có trade-off riêng.

## Câu 8
[TYPE: SELECT_RESULT]
```java
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmailContainingIgnoreCase(String email);
}
```
Query SQL tương đương?

A. `SELECT * FROM user WHERE email = ?`
B. `SELECT * FROM user WHERE LOWER(email) LIKE LOWER('%?%')`
C. `SELECT * FROM user WHERE email LIKE '?%'`
D. `SELECT * FROM user WHERE email IS NOT NULL`

**Đáp án: B**
> Containing = LIKE %value%. IgnoreCase = case-insensitive comparison.

## Câu 9
[TYPE: TRUE_FALSE]
JpaRepository extends PagingAndSortingRepository extends CrudRepository.

**Đáp án: TRUE**
> Hierarchy: Repository → CrudRepository → PagingAndSortingRepository → JpaRepository.

## Câu 10
[TYPE: MULTIPLE_CHOICE]
@Transactional(readOnly = true) optimization:

A. Không làm gì
B. Hibernate skip dirty checking, tối ưu flush mode → performance tốt hơn cho SELECT
C. Lock database
D. Disable caching

**Đáp án: B**
> readOnly=true hints Hibernate: skip dirty checking (no flush), optimize connection usage.

## Câu 11
[TYPE: MULTIPLE_CHOICE]
Cascade.REMOVE nghĩa là:

A. Khi xóa parent, child entities cũng bị xóa
B. Khi xóa child, parent bị xóa
C. Không cho xóa
D. Soft delete

**Đáp án: A**
> CascadeType.REMOVE: delete parent → delete children. orphanRemoval=true: remove child from collection → delete.

## Câu 12
[TYPE: SELECT_RESULT]
```java
@Query("SELECT u FROM User u WHERE u.age BETWEEN :min AND :max")
List<User> findByAgeRange(@Param("min") int min, @Param("max") int max);
```
Đây là loại query gì?

A. Native SQL query
B. JPQL query
C. Criteria query
D. Named query

**Đáp án: B**
> JPQL (Java Persistence Query Language) dùng entity/field names, không phải table/column names.

## Câu 13
[TYPE: MULTIPLE_CHOICE]
@Modifying cần đi kèm với:

A. @Query cho SELECT
B. @Query cho UPDATE/DELETE operations
C. @Entity
D. @Repository

**Đáp án: B**
> @Modifying bắt buộc cho @Query UPDATE/DELETE. Cần thêm @Transactional.

## Câu 14
[TYPE: TRUE_FALSE]
Specification<T> cho phép xây dựng dynamic queries theo Criteria API.

**Đáp án: TRUE**
> Specification pattern cho phép compose predicates: Specification.where(hasName).and(hasAge).

## Câu 15
[TYPE: MULTIPLE_CHOICE]
Soft delete thường implement bằng:

A. @SQLDelete + @SQLRestriction (hoặc @Where)
B. Xóa record thật
C. Dùng @PreRemove throw exception
D. @Cacheable

**Đáp án: A**
> @SQLDelete("UPDATE x SET deleted=true WHERE id=?") + @SQLRestriction("deleted = false") filter soft-deleted.
