# Docker - Bài Tập

## Bài 1: Dockerfile cơ bản
**Độ khó: Dễ**

Tạo Dockerfile cho ứng dụng Node.js Express đơn giản:

1. Base image: `node:20-alpine`
2. Working directory: `/app`
3. Copy và cài dependencies (tận dụng layer cache)
4. Copy source code
5. Expose port 3000
6. Lệnh chạy: `node index.js`
7. Build image với tag `my-api:v1`
8. Chạy container map port 8080:3000
9. Kiểm tra truy cập `http://localhost:8080`

---

## Bài 2: Docker Compose Full-Stack
**Độ khó: Trung bình**

Tạo Docker Compose cho ứng dụng full-stack:

1. **Frontend (React):** Build từ Dockerfile, port 3000, hot reload với volume mount
2. **Backend (Node/Java):** Build từ Dockerfile, port 8080, kết nối DB và Redis
3. **PostgreSQL:** Image `postgres:16-alpine`, persistent volume, healthcheck
4. **Redis:** Image `redis:7-alpine`, port 6379
5. Backend chỉ start sau khi DB healthy
6. Tạo custom network tách frontend và backend
7. Dùng `.env` file cho environment variables

---

## Bài 3: Multi-stage Build
**Độ khó: Trung bình**

Tạo multi-stage Dockerfile cho React app:

1. **Stage 1 (builder):** Cài dependencies, build React app
2. **Stage 2 (production):** Dùng `nginx:alpine`, copy build output
3. Tạo file `nginx.conf` custom (SPA routing, gzip, cache headers)
4. So sánh kích thước image giữa single-stage và multi-stage
5. Thêm `.dockerignore` tối ưu

---

## Bài 4: Docker Networking
**Độ khó: Trung bình**

Thiết lập networking cho microservices:

1. Tạo 3 networks: `frontend-net`, `backend-net`, `monitoring-net`
2. **Nginx (reverse proxy):** Thuộc `frontend-net`
3. **API Gateway:** Thuộc `frontend-net` và `backend-net`
4. **User Service:** Thuộc `backend-net`
5. **Order Service:** Thuộc `backend-net`
6. **Database:** Thuộc `backend-net`
7. **Prometheus + Grafana:** Thuộc `monitoring-net` và `backend-net`
8. Kiểm tra: Nginx KHÔNG thể truy cập Database trực tiếp

---

## Bài 5: Production-Ready Docker Setup
**Độ khó: Khó**

Tạo Docker setup sẵn sàng cho production:

1. Multi-stage Dockerfile với non-root user
2. Health check trong Dockerfile
3. Docker Compose với:
   - App (3 replicas)
   - PostgreSQL với backup volume
   - Redis với password
   - Nginx load balancer
4. Resource limits (CPU, memory)
5. Logging configuration
6. Restart policy
7. Security scan với Trivy
8. `.dockerignore` tối ưu
9. Docker secrets cho passwords (không dùng env vars)
10. Tạo script `deploy.sh` để build, test, và deploy
