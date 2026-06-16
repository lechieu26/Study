# Docker - Lý Thuyết Từ Cơ Bản Đến Nâng Cao

## Mục lục

1. [Giới thiệu về Docker](#1-gioi-thieu-ve-docker)
2. [Cài đặt Docker](#2-cai-dat-docker)
3. [Docker Images](#3-docker-images)
4. [Docker Containers](#4-docker-containers)
5. [Dockerfile](#5-dockerfile)
6. [Docker Compose](#6-docker-compose)
7. [Docker Networking](#7-docker-networking)
8. [Docker Volumes](#8-docker-volumes)
9. [Multi-stage Builds](#9-multi-stage-builds)
10. [Docker Registry](#10-docker-registry)
11. [Docker trong Production](#11-docker-trong-production)
12. [Best Practices](#12-best-practices)

---

## 1. Giới thiệu về Docker

### 1.1 Docker là gì?

Docker là nền tảng **container hóa** cho phép đóng gói ứng dụng cùng tất cả dependencies vào một **container** nhẹ, di động, chạy nhất quán trên mọi môi trường.

### 1.2 Container vs Virtual Machine

| | Container | Virtual Machine |
|--|-----------|-----------------|
| Kích thước | MB | GB |
| Khởi động | Giây | Phút |
| OS | Chia sẻ kernel với host | OS riêng |
| Isolation | Process-level | Hardware-level |
| Performance | Gần như native | Overhead do hypervisor |
| Density | Hàng trăm trên 1 host | Hàng chục |

### 1.3 Kiến trúc Docker

```
Docker Client (CLI)
    |
    v
    Docker Daemon (dockerd)
    |
    +--→ Images (templates)
    +--→ Containers (instances)
    +--→ Volumes (data)
    +--→ Networks
    |
    v
    Docker Registry (Docker Hub)
```

### 1.4 Khái niệm cơ bản

| Khái niệm | Mô tả |
|-----------|-------|
| Image | Template chỉ đọc, chứa OS + app + dependencies |
| Container | Instance đang chạy của image |
| Dockerfile | File chỉ dẫn để build image |
| Docker Compose | Tool quản lý nhiều containers |
| Volume | Lưu trữ dữ liệu bên ngoài container |
| Network | Mạng kết nối giữa các containers |
| Registry | Kho lưu trữ images (Docker Hub) |

---

## 2. Cài đặt Docker

### 2.1 Cài đặt trên Ubuntu

```bash
# Cập nhật và cài dependencies
sudo apt-get update
sudo apt-get install ca-certificates curl gnupg

# Thêm Docker GPG key
sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg

# Thêm Docker repository
echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# Cài Docker
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-compose-plugin

# Chạy Docker không cần sudo
sudo usermod -aG docker $USER
```

### 2.2 Kiểm tra

```bash
docker --version
docker compose version
docker run hello-world
```

---

## 3. Docker Images

### 3.1 Quản lý Images

```bash
# Tìm image trên Docker Hub
docker search nginx

# Tải image
docker pull nginx                  # Latest
docker pull nginx:1.25             # Version cụ thể
docker pull node:20-alpine         # Variant nhẹ

# Xem images local
docker images
docker image ls

# Xóa image
docker rmi nginx
docker image prune          # Xóa images không dùng
docker image prune -a       # Xóa TẤT CẢ images không dùng

# Xem chi tiết image
docker inspect nginx
docker history nginx        # Xem các layers
```

### 3.2 Image Tags và Naming

```
registry/repository:tag

docker.io/library/nginx:1.25-alpine
|         |       |    |
Registry  User    Name Tag

# Ví dụ:
nginx                    → docker.io/library/nginx:latest
node:20-alpine           → docker.io/library/node:20-alpine
myuser/myapp:v1.0        → docker.io/myuser/myapp:v1.0
ghcr.io/user/app:latest  → GitHub Container Registry
```

---

## 4. Docker Containers

### 4.1 Chạy Containers

```bash
# Chạy container cơ bản
docker run nginx

# Chạy nền (detached)
docker run -d nginx

# Chạy với tên
docker run -d --name my-nginx nginx

# Map port: host:container
docker run -d -p 8080:80 nginx
# Truy cập http://localhost:8080

# Truyền environment variables
docker run -d -e DB_HOST=localhost -e DB_PORT=5432 postgres

# Tự động xóa khi dừng
docker run --rm nginx echo "Hello"

# Tương tác (interactive terminal)
docker run -it ubuntu bash
docker run -it node:20-alpine sh
```

### 4.2 Quản lý Containers

```bash
# Xem containers đang chạy
docker ps
docker ps -a               # Tất cả (cả đã dừng)

# Dừng / Khởi động / Khởi động lại
docker stop my-nginx
docker start my-nginx
docker restart my-nginx

# Xóa container
docker rm my-nginx
docker rm -f my-nginx      # Force (đang chạy)
docker container prune     # Xóa tất cả đã dừng

# Xem logs
docker logs my-nginx
docker logs -f my-nginx    # Follow (real-time)
docker logs --tail 100 my-nginx  # 100 dòng cuối

# Chạy lệnh trong container đang chạy
docker exec -it my-nginx bash
docker exec my-nginx ls /etc/nginx

# Xem resource usage
docker stats
docker top my-nginx        # Processes trong container

# Copy file giữa host và container
docker cp file.txt my-nginx:/app/
docker cp my-nginx:/app/log.txt ./
```

---

## 5. Dockerfile

### 5.1 Cấu trúc Dockerfile

```dockerfile
# Base image
FROM node:20-alpine

# Metadata
LABEL maintainer="dev@example.com"
LABEL version="1.0"

# Tạo thư mục làm việc
WORKDIR /app

# Copy dependency files trước (tận dụng cache)
COPY package.json package-lock.json ./

# Cài dependencies
RUN npm ci --production

# Copy source code
COPY . .

# Build ứng dụng
RUN npm run build

# Expose port (documentation)
EXPOSE 3000

# Environment variables
ENV NODE_ENV=production
ENV PORT=3000

# Tạo user không phải root
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Lệnh chạy khi container start
CMD ["node", "dist/index.js"]
```

### 5.2 Các Instructions quan trọng

```dockerfile
# FROM - Base image (BẮT BUỘC, dòng đầu)
FROM node:20-alpine
FROM python:3.12-slim
FROM eclipse-temurin:17-jre-alpine

# RUN - Chạy lệnh khi build (tạo layer mới)
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
RUN npm ci --production

# COPY vs ADD
COPY . .                  # Copy files từ host vào image
COPY --chown=app:app . .  # Copy với owner
ADD archive.tar.gz /app/  # Tự động extract tar (dùng COPY khi không cần)

# CMD vs ENTRYPOINT
CMD ["node", "app.js"]           # Có thể bị override khi docker run
ENTRYPOINT ["node", "app.js"]    # Không bị override (dùng cho CLI tools)

# ENTRYPOINT + CMD kết hợp
ENTRYPOINT ["node"]
CMD ["app.js"]
# docker run myapp → node app.js
# docker run myapp server.js → node server.js

# ARG - Build-time variables
ARG NODE_VERSION=20
FROM node:${NODE_VERSION}-alpine

# HEALTHCHECK
HEALTHCHECK --interval=30s --timeout=3s --retries=3 \
    CMD curl -f http://localhost:3000/health || exit 1
```

### 5.3 Build Image

```bash
# Build image
docker build -t myapp .
docker build -t myapp:v1.0 .
docker build -t myapp:v1.0 -f Dockerfile.prod .

# Build với ARG
docker build --build-arg NODE_VERSION=18 -t myapp .

# Xem build process
docker build --progress=plain -t myapp .
```

---

## 6. Docker Compose

### 6.1 docker-compose.yml cơ bản

```yaml
# docker-compose.yml
services:
  # Frontend
  frontend:
    build:
      context: ./frontend
      dockerfile: Dockerfile
    ports:
      - "3000:3000"
    environment:
      - REACT_APP_API_URL=http://localhost:8080
    depends_on:
      - backend
    volumes:
      - ./frontend/src:/app/src    # Hot reload

  # Backend
  backend:
    build: ./backend
    ports:
      - "8080:8080"
    environment:
      - DATABASE_URL=postgres://user:pass@db:5432/mydb
      - REDIS_URL=redis://cache:6379
    depends_on:
      db:
        condition: service_healthy
      cache:
        condition: service_started

  # Database
  db:
    image: postgres:16-alpine
    ports:
      - "5432:5432"
    environment:
      POSTGRES_USER: user
      POSTGRES_PASSWORD: pass
      POSTGRES_DB: mydb
    volumes:
      - postgres-data:/var/lib/postgresql/data
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U user"]
      interval: 10s
      timeout: 5s
      retries: 5

  # Cache
  cache:
    image: redis:7-alpine
    ports:
      - "6379:6379"

volumes:
  postgres-data:
```

### 6.2 Lệnh Docker Compose

```bash
# Khởi động tất cả services
docker compose up
docker compose up -d            # Detached
docker compose up --build       # Rebuild images

# Dừng
docker compose down             # Dừng và xóa containers
docker compose down -v          # + xóa volumes
docker compose down --rmi all   # + xóa images

# Quản lý
docker compose ps               # Xem services
docker compose logs             # Xem logs
docker compose logs -f backend  # Follow logs của 1 service
docker compose exec backend sh  # Shell vào service

# Scale
docker compose up -d --scale backend=3

# Restart
docker compose restart backend
```

---

## 7. Docker Networking

### 7.1 Các loại Network

```bash
# Xem networks
docker network ls

# Các driver:
# - bridge (mặc định): Containers trên cùng host giao tiếp
# - host: Container dùng network của host
# - none: Không có network
# - overlay: Multi-host (Docker Swarm)
```

### 7.2 Custom Network

```bash
# Tạo network
docker network create my-network

# Chạy containers trên cùng network
docker run -d --name api --network my-network node:20-alpine
docker run -d --name db --network my-network postgres:16

# Container "api" có thể truy cập "db" bằng tên:
# postgres://db:5432

# Xem chi tiết network
docker network inspect my-network
```

### 7.3 Network trong Docker Compose

```yaml
services:
  frontend:
    networks:
      - frontend-net

  backend:
    networks:
      - frontend-net
      - backend-net

  db:
    networks:
      - backend-net

networks:
  frontend-net:
  backend-net:

# frontend <-> backend: OK (cùng frontend-net)
# backend <-> db: OK (cùng backend-net)
# frontend <-> db: KHÔNG (khác network)
```

---

## 8. Docker Volumes

### 8.1 Các loại Volumes

```bash
# 1. Named Volume (KHUYÊN DÙNG cho data)
docker volume create my-data
docker run -v my-data:/app/data nginx

# 2. Bind Mount (dùng cho development)
docker run -v $(pwd)/src:/app/src nginx
docker run -v ./src:/app/src nginx         # Docker Compose

# 3. tmpfs Mount (RAM, mất khi dừng container)
docker run --tmpfs /app/temp nginx
```

### 8.2 Quản lý Volumes

```bash
docker volume ls               # Xem tất cả
docker volume inspect my-data  # Chi tiết
docker volume rm my-data       # Xóa
docker volume prune            # Xóa không dùng
```

---

## 9. Multi-stage Builds

### 9.1 Tại sao Multi-stage?

Giảm kích thước image bằng cách tách build stage và runtime stage.

### 9.2 Ví dụ Node.js

```dockerfile
# === Stage 1: Build ===
FROM node:20-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

# === Stage 2: Production ===
FROM node:20-alpine AS production
WORKDIR /app
COPY package*.json ./
RUN npm ci --production
COPY --from=builder /app/dist ./dist

ENV NODE_ENV=production
USER node
EXPOSE 3000
CMD ["node", "dist/index.js"]
```

### 9.3 Ví dụ Java Spring Boot

```dockerfile
# === Stage 1: Build ===
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve
COPY src ./src
RUN ./mvnw package -DskipTests

# === Stage 2: Runtime ===
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

RUN addgroup -S app && adduser -S app -G app
USER app

EXPOSE 8080
HEALTHCHECK --interval=30s CMD wget -qO- http://localhost:8080/actuator/health || exit 1
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 9.4 Ví dụ React (Static)

```dockerfile
# === Stage 1: Build ===
FROM node:20-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

# === Stage 2: Serve với Nginx ===
FROM nginx:alpine
COPY --from=builder /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

**Kích thước:**
- Không multi-stage: ~1.2GB (node + dependencies + source)
- Multi-stage: ~25MB (chỉ nginx + static files)

---

## 10. Docker Registry

### 10.1 Docker Hub

```bash
# Đăng nhập
docker login

# Tag image
docker tag myapp:latest username/myapp:v1.0

# Push
docker push username/myapp:v1.0

# Pull
docker pull username/myapp:v1.0
```

### 10.2 GitHub Container Registry

```bash
# Login
echo $GITHUB_TOKEN | docker login ghcr.io -u USERNAME --password-stdin

# Tag và push
docker tag myapp ghcr.io/username/myapp:latest
docker push ghcr.io/username/myapp:latest
```

---

## 11. Docker trong Production

### 11.1 Security

```dockerfile
# 1. Dùng non-root user
RUN addgroup -S app && adduser -S app -G app
USER app

# 2. Dùng image nhẹ (alpine, slim, distroless)
FROM node:20-alpine       # ~180MB thay vì ~1GB

# 3. Scan vulnerabilities
# docker scout cves myapp:latest
# hoặc dùng Trivy: trivy image myapp:latest

# 4. Không copy secrets vào image
# Dùng build secrets:
RUN --mount=type=secret,id=npmrc,target=/root/.npmrc npm ci
```

### 11.2 Health Checks

```dockerfile
HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
    CMD curl -f http://localhost:3000/health || exit 1
```

```yaml
# docker-compose.yml
services:
  app:
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:3000/health"]
      interval: 30s
      timeout: 3s
      retries: 3
      start_period: 10s
```

---

## 12. Best Practices

### 12.1 Dockerfile

1. **Dùng image cụ thể:** `node:20.11-alpine` thay vì `node:latest`
2. **Multi-stage builds:** Tách build và runtime
3. **Layer ordering:** COPY package.json trước source code (cache)
4. **Gộp RUN:** Giảm số layers (`RUN apt-get update && apt-get install -y ...`)
5. **Non-root user:** Luôn chạy app với user không phải root
6. **Alpine/Slim:** Dùng base image nhẹ nhất có thể
7. **.dockerignore:** Giống .gitignore, tránh copy file không cần
8. **HEALTHCHECK:** Luôn định nghĩa health check
9. **Labels:** Metadata cho image (maintainer, version)
10. **No secrets in image:** Dùng build secrets hoặc env vars lúc runtime

### 12.2 .dockerignore

```
node_modules
.git
.env
.env.local
*.md
.vscode
.idea
dist
coverage
```

### 12.3 Docker Compose

1. **depends_on + healthcheck:** Đảm bảo services sẵn sàng trước khi connect
2. **Named volumes:** Cho persistent data
3. **Environment variables:** Dùng .env file
4. **Networks:** Tách frontend và backend network

---

## Tổng kết

Docker là nền tảng container hóa thiết yếu cho development và deployment:

1. **Images:** Template chứa OS + app + dependencies
2. **Containers:** Instance chạy từ image, nhẹ và nhanh
3. **Dockerfile:** Định nghĩa cách build image (FROM, RUN, COPY, CMD)
4. **Docker Compose:** Quản lý nhiều containers (full-stack apps)
5. **Multi-stage:** Giảm kích thước image đáng kể
6. **Volumes:** Lưu trữ data bên ngoài container
7. **Networks:** Kết nối và cô lập containers
8. **Security:** Non-root user, image scanning, secrets management
