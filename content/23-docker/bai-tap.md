# Docker - Bai Tap

## Bài 1: Dockerfile co ban
**Độ khó: Dễ**

Tao Dockerfile cho ung dung Node.js Express don gian:

1. Base image: `node:20-alpine`
2. Working directory: `/app`
3. Copy va cai dependencies (tan dung layer cache)
4. Copy source code
5. Expose port 3000
6. Lenh chay: `node index.js`
7. Build image voi tag `my-api:v1`
8. Chay container map port 8080:3000
9. Kiem tra truy cap `http://localhost:8080`

---

## Bài 2: Docker Compose Full-Stack
**Độ khó: Trung bình**

Tao Docker Compose cho ung dung full-stack:

1. **Frontend (React):** Build tu Dockerfile, port 3000, hot reload voi volume mount
2. **Backend (Node/Java):** Build tu Dockerfile, port 8080, ket noi DB va Redis
3. **PostgreSQL:** Image `postgres:16-alpine`, persistent volume, healthcheck
4. **Redis:** Image `redis:7-alpine`, port 6379
5. Backend chi start sau khi DB healthy
6. Tao custom network tach frontend va backend
7. Dung `.env` file cho environment variables

---

## Bài 3: Multi-stage Build
**Độ khó: Trung bình**

Tao multi-stage Dockerfile cho React app:

1. **Stage 1 (builder):** Cai dependencies, build React app
2. **Stage 2 (production):** Dung `nginx:alpine`, copy build output
3. Tao file `nginx.conf` custom (SPA routing, gzip, cache headers)
4. So sanh kich thuoc image giua single-stage va multi-stage
5. Them `.dockerignore` toi uu

---

## Bài 4: Docker Networking
**Độ khó: Trung bình**

Thiet lap networking cho microservices:

1. Tao 3 networks: `frontend-net`, `backend-net`, `monitoring-net`
2. **Nginx (reverse proxy):** Thuoc `frontend-net`
3. **API Gateway:** Thuoc `frontend-net` va `backend-net`
4. **User Service:** Thuoc `backend-net`
5. **Order Service:** Thuoc `backend-net`
6. **Database:** Thuoc `backend-net`
7. **Prometheus + Grafana:** Thuoc `monitoring-net` va `backend-net`
8. Kiem tra: Nginx KHONG the truy cap Database truc tiep

---

## Bài 5: Production-Ready Docker Setup
**Độ khó: Khó**

Tao Docker setup san sang cho production:

1. Multi-stage Dockerfile voi non-root user
2. Health check trong Dockerfile
3. Docker Compose voi:
   - App (3 replicas)
   - PostgreSQL voi backup volume
   - Redis voi password
   - Nginx load balancer
4. Resource limits (CPU, memory)
5. Logging configuration
6. Restart policy
7. Security scan voi Trivy
8. `.dockerignore` toi uu
9. Docker secrets cho passwords (khong dung env vars)
10. Tao script `deploy.sh` de build, test, va deploy
