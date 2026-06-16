# Docker - Ly Thuyet Tu Co Ban Den Nang Cao

## Muc luc

1. [Gioi thieu ve Docker](#1-gioi-thieu-ve-docker)
2. [Cai dat Docker](#2-cai-dat-docker)
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

## 1. Gioi thieu ve Docker

### 1.1 Docker la gi?

Docker la nen tang **container hoa** cho phep dong goi ung dung cung tat ca dependencies vao mot **container** nhe, di dong, chay nhat quan tren moi moi truong.

### 1.2 Container vs Virtual Machine

| | Container | Virtual Machine |
|--|-----------|-----------------|
| Kich thuoc | MB | GB |
| Khoi dong | Giay | Phut |
| OS | Chia se kernel voi host | OS rieng |
| Isolation | Process-level | Hardware-level |
| Performance | Gan nhu native | Overhead do hypervisor |
| Density | Hang tram tren 1 host | Hang chuc |

### 1.3 Kien truc Docker

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

### 1.4 Khai niem co ban

| Khai niem | Mo ta |
|-----------|-------|
| Image | Template chi doc, chua OS + app + dependencies |
| Container | Instance dang chay cua image |
| Dockerfile | File chi dan de build image |
| Docker Compose | Tool quan ly nhieu containers |
| Volume | Luu tru du lieu ben ngoai container |
| Network | Mang ket noi giua cac containers |
| Registry | Kho luu tru images (Docker Hub) |

---

## 2. Cai dat Docker

### 2.1 Cai dat tren Ubuntu

```bash
# Cap nhat va cai dependencies
sudo apt-get update
sudo apt-get install ca-certificates curl gnupg

# Them Docker GPG key
sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg

# Them Docker repository
echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# Cai Docker
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-compose-plugin

# Chay Docker khong can sudo
sudo usermod -aG docker $USER
```

### 2.2 Kiem tra

```bash
docker --version
docker compose version
docker run hello-world
```

---

## 3. Docker Images

### 3.1 Quan ly Images

```bash
# Tim image tren Docker Hub
docker search nginx

# Tai image
docker pull nginx                  # Latest
docker pull nginx:1.25             # Version cu the
docker pull node:20-alpine         # Variant nhe

# Xem images local
docker images
docker image ls

# Xoa image
docker rmi nginx
docker image prune          # Xoa images khong dung
docker image prune -a       # Xoa TAT CA images khong dung

# Xem chi tiet image
docker inspect nginx
docker history nginx        # Xem cac layers
```

### 3.2 Image Tags va Naming

```
registry/repository:tag

docker.io/library/nginx:1.25-alpine
|         |       |    |
Registry  User    Name Tag

# Vi du:
nginx                    → docker.io/library/nginx:latest
node:20-alpine           → docker.io/library/node:20-alpine
myuser/myapp:v1.0        → docker.io/myuser/myapp:v1.0
ghcr.io/user/app:latest  → GitHub Container Registry
```

---

## 4. Docker Containers

### 4.1 Chay Containers

```bash
# Chay container co ban
docker run nginx

# Chay nen (detached)
docker run -d nginx

# Chay voi ten
docker run -d --name my-nginx nginx

# Map port: host:container
docker run -d -p 8080:80 nginx
# Truy cap http://localhost:8080

# Truyen environment variables
docker run -d -e DB_HOST=localhost -e DB_PORT=5432 postgres

# Tu dong xoa khi dung
docker run --rm nginx echo "Hello"

# Tuong tac (interactive terminal)
docker run -it ubuntu bash
docker run -it node:20-alpine sh
```

### 4.2 Quan ly Containers

```bash
# Xem containers dang chay
docker ps
docker ps -a               # Tat ca (ca da dung)

# Dung / Khoi dong / Khoi dong lai
docker stop my-nginx
docker start my-nginx
docker restart my-nginx

# Xoa container
docker rm my-nginx
docker rm -f my-nginx      # Force (dang chay)
docker container prune     # Xoa tat ca da dung

# Xem logs
docker logs my-nginx
docker logs -f my-nginx    # Follow (real-time)
docker logs --tail 100 my-nginx  # 100 dong cuoi

# Chay lenh trong container dang chay
docker exec -it my-nginx bash
docker exec my-nginx ls /etc/nginx

# Xem resource usage
docker stats
docker top my-nginx        # Processes trong container

# Copy file giua host va container
docker cp file.txt my-nginx:/app/
docker cp my-nginx:/app/log.txt ./
```

---

## 5. Dockerfile

### 5.1 Cau truc Dockerfile

```dockerfile
# Base image
FROM node:20-alpine

# Metadata
LABEL maintainer="dev@example.com"
LABEL version="1.0"

# Tao thu muc lam viec
WORKDIR /app

# Copy dependency files truoc (tan dung cache)
COPY package.json package-lock.json ./

# Cai dependencies
RUN npm ci --production

# Copy source code
COPY . .

# Build ung dung
RUN npm run build

# Expose port (documentation)
EXPOSE 3000

# Environment variables
ENV NODE_ENV=production
ENV PORT=3000

# Tao user khong phai root
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Lenh chay khi container start
CMD ["node", "dist/index.js"]
```

### 5.2 Cac Instructions quan trong

```dockerfile
# FROM - Base image (BAT BUOC, dong dau)
FROM node:20-alpine
FROM python:3.12-slim
FROM eclipse-temurin:17-jre-alpine

# RUN - Chay lenh khi build (tao layer moi)
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
RUN npm ci --production

# COPY vs ADD
COPY . .                  # Copy files tu host vao image
COPY --chown=app:app . .  # Copy voi owner
ADD archive.tar.gz /app/  # Tu dong extract tar (dung COPY khi khong can)

# CMD vs ENTRYPOINT
CMD ["node", "app.js"]           # Co the bi override khi docker run
ENTRYPOINT ["node", "app.js"]    # Khong bi override (dung cho CLI tools)

# ENTRYPOINT + CMD ket hop
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

# Build voi ARG
docker build --build-arg NODE_VERSION=18 -t myapp .

# Xem build process
docker build --progress=plain -t myapp .
```

---

## 6. Docker Compose

### 6.1 docker-compose.yml co ban

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

### 6.2 Lenh Docker Compose

```bash
# Khoi dong tat ca services
docker compose up
docker compose up -d            # Detached
docker compose up --build       # Rebuild images

# Dung
docker compose down             # Dung va xoa containers
docker compose down -v          # + xoa volumes
docker compose down --rmi all   # + xoa images

# Quan ly
docker compose ps               # Xem services
docker compose logs             # Xem logs
docker compose logs -f backend  # Follow logs cua 1 service
docker compose exec backend sh  # Shell vao service

# Scale
docker compose up -d --scale backend=3

# Restart
docker compose restart backend
```

---

## 7. Docker Networking

### 7.1 Cac loai Network

```bash
# Xem networks
docker network ls

# Cac driver:
# - bridge (mac dinh): Containers tren cung host giao tiep
# - host: Container dung network cua host
# - none: Khong co network
# - overlay: Multi-host (Docker Swarm)
```

### 7.2 Custom Network

```bash
# Tao network
docker network create my-network

# Chay containers tren cung network
docker run -d --name api --network my-network node:20-alpine
docker run -d --name db --network my-network postgres:16

# Container "api" co the truy cap "db" bang ten:
# postgres://db:5432

# Xem chi tiet network
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

# frontend <-> backend: OK (cung frontend-net)
# backend <-> db: OK (cung backend-net)
# frontend <-> db: KHONG (khac network)
```

---

## 8. Docker Volumes

### 8.1 Cac loai Volumes

```bash
# 1. Named Volume (KHUYEN DUNG cho data)
docker volume create my-data
docker run -v my-data:/app/data nginx

# 2. Bind Mount (dung cho development)
docker run -v $(pwd)/src:/app/src nginx
docker run -v ./src:/app/src nginx         # Docker Compose

# 3. tmpfs Mount (RAM, mat khi dung container)
docker run --tmpfs /app/temp nginx
```

### 8.2 Quan ly Volumes

```bash
docker volume ls               # Xem tat ca
docker volume inspect my-data  # Chi tiet
docker volume rm my-data       # Xoa
docker volume prune            # Xoa khong dung
```

---

## 9. Multi-stage Builds

### 9.1 Tai sao Multi-stage?

Giam kich thuoc image bang cach tach build stage va runtime stage.

### 9.2 Vi du Node.js

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

### 9.3 Vi du Java Spring Boot

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

### 9.4 Vi du React (Static)

```dockerfile
# === Stage 1: Build ===
FROM node:20-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

# === Stage 2: Serve voi Nginx ===
FROM nginx:alpine
COPY --from=builder /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

**Kich thuoc:**
- Khong multi-stage: ~1.2GB (node + dependencies + source)
- Multi-stage: ~25MB (chi nginx + static files)

---

## 10. Docker Registry

### 10.1 Docker Hub

```bash
# Dang nhap
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

# Tag va push
docker tag myapp ghcr.io/username/myapp:latest
docker push ghcr.io/username/myapp:latest
```

---

## 11. Docker trong Production

### 11.1 Security

```dockerfile
# 1. Dung non-root user
RUN addgroup -S app && adduser -S app -G app
USER app

# 2. Dung image nhe (alpine, slim, distroless)
FROM node:20-alpine       # ~180MB thay vi ~1GB

# 3. Scan vulnerabilities
# docker scout cves myapp:latest
# hoac dung Trivy: trivy image myapp:latest

# 4. Khong copy secrets vao image
# Dung build secrets:
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

1. **Dung image cu the:** `node:20.11-alpine` thay vi `node:latest`
2. **Multi-stage builds:** Tach build va runtime
3. **Layer ordering:** COPY package.json truoc source code (cache)
4. **Gop RUN:** Giam so layers (`RUN apt-get update && apt-get install -y ...`)
5. **Non-root user:** Luon chay app voi user khong phai root
6. **Alpine/Slim:** Dung base image nhe nhat co the
7. **.dockerignore:** Giong .gitignore, tranh copy file khong can
8. **HEALTHCHECK:** Luon dinh nghia health check
9. **Labels:** Metadata cho image (maintainer, version)
10. **No secrets in image:** Dung build secrets hoac env vars luc runtime

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

1. **depends_on + healthcheck:** Dam bao services san sang truoc khi connect
2. **Named volumes:** Cho persistent data
3. **Environment variables:** Dung .env file
4. **Networks:** Tach frontend va backend network

---

## Tong ket

Docker la nen tang container hoa thiet yeu cho development va deployment:

1. **Images:** Template chua OS + app + dependencies
2. **Containers:** Instance chay tu image, nhe va nhanh
3. **Dockerfile:** Dinh nghia cach build image (FROM, RUN, COPY, CMD)
4. **Docker Compose:** Quan ly nhieu containers (full-stack apps)
5. **Multi-stage:** Giam kich thuoc image dang ke
6. **Volumes:** Luu tru data ben ngoai container
7. **Networks:** Ket noi va co lap containers
8. **Security:** Non-root user, image scanning, secrets management
