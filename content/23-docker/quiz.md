# Quiz - Docker

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Docker container khác Virtual Machine như thế nào?

- [ ] Container chạy trên hypervisor, VM chạy trên OS
- [x] Container chia sẻ kernel với host OS, nhẹ và nhanh hơn VM
- [ ] Không có sự khác biệt
- [ ] VM nhẹ hơn container

> **Giải thích:** Container chia sẻ kernel của host OS, chỉ đóng gói app và dependencies → kích thước MB, khởi động trong giây. VM có OS riêng (guest OS) chạy trên hypervisor → kích thước GB, khởi động trong phút. Container nhẹ và nhanh hơn nhưng isolation kém hơn VM.

## Câu 2

[TYPE: MULTIPLE_CHOICE]

Trong Dockerfile, sự khác biệt giữa `CMD` và `ENTRYPOINT`?

- [ ] Giống nhau
- [x] `CMD` có thể bị override khi `docker run`, `ENTRYPOINT` không bị override
- [ ] `CMD` chạy khi build, `ENTRYPOINT` chạy khi start
- [ ] Chỉ được dùng 1 trong 2

> **Giải thích:** `CMD` định nghĩa lệnh mặc định, có thể bị thay thế khi `docker run myapp <new-command>`. `ENTRYPOINT` định nghĩa executable chính, KHÔNG bị thay thế (chỉ thêm tham số). Thường kết hợp: `ENTRYPOINT ["node"]` + `CMD ["app.js"]` → có thể đổi file chạy nhưng luôn dùng node.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Tại sao nên `COPY package.json` TRƯỚC `COPY . .` trong Dockerfile?

- [ ] Package.json nhỏ hơn nên copy nhanh
- [x] Tận dụng Docker layer cache - chỉ rebuild dependencies khi package.json thay đổi
- [ ] Docker yêu cầu thứ tự này
- [ ] Không có lý do, thứ tự không quan trọng

> **Giải thích:** Docker cache mỗi layer. Nếu COPY package.json trước rồi RUN npm install, khi chỉ thay đổi source code (không đổi package.json), Docker sẽ dùng cache cho layer install dependencies → build NHANH hơn nhiều. Nếu COPY . . trước, bất kỳ thay đổi nào cũng làm mất cache (invalidate cache) của các layer phía sau.

## Câu 4

[TYPE: MULTIPLE_CHOICE]

Multi-stage build giải quyết vấn đề gì?

- [ ] Làm app chạy nhanh hơn
- [x] Giảm kích thước image bằng cách tách build tools khỏi runtime
- [ ] Cho phép chạy nhiều apps trong 1 container
- [ ] Tăng bảo mật bằng cách mã hóa code

> **Giải thích:** Multi-stage build: stage 1 có build tools (compiler, npm dev deps) để build app. Stage 2 chỉ copy artifacts (compiled code) vào image nhẹ (alpine, distroless). Kết quả: image nhỏ hơn nhiều (VD: React app từ 1.2GB xuống 25MB).

## Câu 5

[TYPE: MULTIPLE_CHOICE]

`docker compose up -d` làm gì?

- [ ] Build images
- [ ] Xóa containers
- [x] Khởi động tất cả services trong background (detached mode)
- [ ] Hiển thị logs

> **Giải thích:** `docker compose up` khởi động tất cả services định nghĩa trong docker-compose.yml. Flag `-d` (detached) chạy trong background, trả lại terminal. Không có `-d`, logs sẽ hiển thị và Ctrl+C dừng tất cả services.

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Named volume trong Docker dùng để làm gì?

- [ ] Tăng tốc độ container
- [ ] Chia sẻ code giữa containers
- [x] Lưu trữ data persistent (không mất khi xóa container)
- [ ] Lưu trữ Docker images

> **Giải thích:** Container là ephemeral - data trong container MẤT khi xóa container. Named volumes lưu data BÊN NGOÀI container lifecycle. Dù xóa và tạo lại container, data trong volume VẪN CÒN. Thường dùng cho database data, upload files.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

`depends_on` với `condition: service_healthy` có tác dụng gì?

- [ ] Tự động restart service nếu fail
- [x] Chỉ start service SAU KHI service phụ thuộc HEALTHY (pass healthcheck)
- [ ] Kết nối 2 services vào cùng network
- [ ] Chia sẻ volumes giữa services

> **Giải thích:** `depends_on` với `condition: service_healthy` đảm bảo service chỉ start khi dependency đã HEALTHY (không chỉ started). Ví dụ: backend đợi DB pass healthcheck (`pg_isready`) trước khi start. Không có condition, backend có thể start khi DB chưa sẵn sàng → connection error.

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Tại sao nên dùng non-root user trong container?

- [ ] Container chạy nhanh hơn
- [ ] Docker yêu cầu bắt buộc
- [x] Giảm rủi ro bảo mật - nếu container bị compromise, attacker không có quyền root
- [ ] Giảm kích thước image

> **Giải thích:** Mặc định container chạy với root. Nếu ứng dụng bị exploit, attacker có quyền root TRONG container và có thể thoát ra host (container escape vulnerabilities). Chạy với non-root user (vd: `USER node`) giới hạn quyền, giảm thiểu thiệt hại khi bị tấn công.

## Câu 9

[TYPE: SELECT_RESULT]

Với Docker Compose sau, frontend có thể truy cập database trực tiếp không?

```yaml
services:
  frontend:
    networks: [public]
  backend:
    networks: [public, private]
  database:
    networks: [private]

networks:
  public:
  private:
```

- [ ] Có
- [x] Không
- [ ] Tùy thuộc vào port mapping
- [ ] Chỉ khi dùng IP trực tiếp

> **Giải thích:** Frontend chỉ ở network `public`, database chỉ ở network `private`. Chúng KHÔNG có network chung nên KHÔNG thể giao tiếp. Backend ở CẢ HAI networks nên là "bridge" giữa frontend và database. Đây là cách cô lập network dùng trong microservices.

## Câu 10

[TYPE: MULTIPLE_CHOICE]

`docker image prune -a` làm gì?

- [ ] Xóa tất cả containers
- [ ] Xóa tất cả volumes
- [x] Xóa tất cả images không được container nào sử dụng
- [ ] Xóa tất cả networks

> **Giải thích:** `docker image prune` xóa dangling images (images không có tag). Thêm `-a` xóa TẤT CẢ images không được container nào đang sử dụng. Hữu ích để giải phóng dung lượng disk. Chú ý: images đang được container dùng (kể cả stopped) sẽ KHÔNG bị xóa.

## Câu 11

[TYPE: TRUE_FALSE]

`EXPOSE 3000` trong Dockerfile tự động publish port 3000 ra ngoài host.

- [ ] True
- [x] False

> **Giải thích:** `EXPOSE` chỉ là DOCUMENTATION - ghi lại port nào container lắng nghe. Nó KHÔNG publish port ra host. Để publish, cần `-p 3000:3000` khi `docker run` hoặc `ports: ["3000:3000"]` trong docker-compose.yml. `EXPOSE` giúp developer và tools (như Docker Desktop) biết port nào nên map.

## Câu 12

[TYPE: MULTIPLE_CHOICE]

Cách nào tốt nhất để truyền secrets (passwords, API keys) vào Docker container trong production?

- [ ] Hardcode trong Dockerfile
- [ ] Dùng ENV trong Dockerfile
- [ ] Dùng .env file commit vào Git
- [x] Dùng Docker secrets hoặc environment variables truyền lúc runtime (không commit vào code)

> **Giải thích:** KHÔNG BAO GIỜ hardcode secrets trong Dockerfile (secrets nằm trong image layers, ai pull image đều thấy). Cách tốt: Docker secrets (Swarm/Compose), environment variables truyền lúc runtime (`docker run -e`), hoặc secret managers (AWS Secrets Manager, Vault). File `.env` KHÔNG commit vào Git.
