# C/C++ Core Notes

Bộ ghi chú trong thư mục này tập trung vào các kiến thức trong danh sách ôn tập:

1. [01_OOP_SOLID.md](01_OOP_SOLID.md) - OOP, SOLID, virtual function, static/final/friend.
2. [02_NAMESPACE_TEMPLATE_EXCEPTION.md](02_NAMESPACE_TEMPLATE_EXCEPTION.md) - namespace, template, exception handling.
3. [03_STL_CONTAINERS.md](03_STL_CONTAINERS.md) - vector, list, map, unordered containers và cách chọn.
4. [04_POINTER_REFERENCE_MEMORY.md](04_POINTER_REFERENCE_MEMORY.md) - pointer, reference, smart pointer, memory management, copy.
5. [05_STRUCT_PADDING_KEYWORDS.md](05_STRUCT_PADDING_KEYWORDS.md) - struct, sizeof, padding, static/final/friend/register.
6. [06_COMPILE_PROCESS_CPP_STANDARDS.md](06_COMPILE_PROCESS_CPP_STANDARDS.md) - quá trình biên dịch và C++11/14/17/20.
7. [07_CONCURRENCY_IPC.md](07_CONCURRENCY_IPC.md) - process, thread, mutex, semaphore, deadlock, IPC.
8. [08_DESIGN_PATTERNS.md](08_DESIGN_PATTERNS.md) - Singleton, Factory và một số pattern cần biết.
9. [09_ALGORITHM_INTERVIEW.md](09_ALGORITHM_INTERVIEW.md) - bài tập Fibonacci, hash table, substring, linked list cycle.

Gợi ý học nhanh:

- Đọc file 01, 04, 07 trước vì đây là các chủ đề hay hỏi phỏng vấn.
- Khi gặp code, tự trả lời 3 câu: object nào sở hữu memory, hàm nào có thể throw exception, container nào phù hợp với thao tác chính?
- Nên chạy lại các ví dụ nhỏ bằng lệnh:

```powershell
g++ -std=c++17 main.cpp -o main
.\main.exe
```
