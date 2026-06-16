# Docker - Dap An Bai Tap

## Bài 1: Dockerfile co ban

**index.js:**
```javascript
const express = require('express');
const app = express();
const PORT = process.env.PORT || 3000;

app.get('/', (req, res) => {
    res.json({ message: 'Hello from Docker!', timestamp: new Date() });
});

app.get('/health', (req, res) => {
    res.json({ status: 'ok' });
});

app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});
```

**Dockerfile:**
```dockerfile
FROM node:20-alpine

WORKDIR /app

# Copy dependency files truoc (layer cache)
COPY package.json package-lock.json ./

# Cai dependencies
RUN npm ci --production

# Copy source code
COPY . .

# Port documentation
EXPOSE 3000

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s \
    CMD wget -qO- http://localhost:3000/health || exit 1

# Chay voi non-root user
USER node

CMD ["node", "index.js"]
```

**.dockerignore:**
```
node_modules
.git
.env
*.md
.vscode
```

**Build va chay:**
```bash
# Build
docker build -t my-api:v1 .

# Chay
docker run -d --name my-api -p 8080:3000 my-api:v1

# Kiem tra
curl http://localhost:8080
# {"message":"Hello from Docker!","timestamp":"..."}

curl http://localhost:8080/health
# {"status":"ok"}

# Xem logs
docker logs my-api

# Xem kich thuoc
docker images my-api
# ~180MB (node:20-alpine)
```

---

## Bài 2: Docker Compose Full-Stack

```yaml
# docker-compose.yml
services:
  frontend:
    build:
      context: ./frontend
      dockerfile: Dockerfile
    ports:
      - "3000:3000"
    environment:
      - REACT_APP_API_URL=http://localhost:8080
    volumes:
      - ./frontend/src:/app/src      # Hot reload
      - ./frontend/public:/app/public
    depends_on:
      - backend
    networks:
      - frontend-net

  backend:
    build:
      context: ./backend
      dockerfile: Dockerfile
    ports:
      - "8080:8080"
    env_file:
      - .env
    environment:
      - DATABASE_URL=postgres://${DB_USER}:${DB_PASS}@db:5432/${DB_NAME}
      - REDIS_URL=redis://cache:6379
    depends_on:
      db:
        condition: service_healthy
      cache:
        condition: service_started
    networks:
      - frontend-net
      - backend-net

  db:
    image: postgres:16-alpine
    ports:
      - "5432:5432"
    environment:
      POSTGRES_USER: ${DB_USER}
      POSTGRES_PASSWORD: ${DB_PASS}
      POSTGRES_DB: ${DB_NAME}
    volumes:
      - postgres-data:/var/lib/postgresql/data
      - ./scripts/init.sql:/docker-entrypoint-initdb.d/init.sql
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U ${DB_USER}"]
      interval: 10s
      timeout: 5s
      retries: 5
      start_period: 30s
    networks:
      - backend-net

  cache:
    image: redis:7-alpine
    ports:
      - "6379:6379"
    volumes:
      - redis-data:/data
    networks:
      - backend-net

networks:
  frontend-net:
    driver: bridge
  backend-net:
    driver: bridge

volumes:
  postgres-data:
  redis-data:
```

**.env:**
```
DB_USER=appuser
DB_PASS=secret123
DB_NAME=myapp
```

**Giải thích:**
- Frontend chi o `frontend-net` → khong truy cap truc tiep DB
- Backend o ca 2 networks → bridge giua frontend va DB
- DB co healthcheck → backend doi DB san sang truoc khi start
- Named volumes giu data khi restart containers
- `.env` file tach cau hinh khoi docker-compose.yml

---

## Bài 3: Multi-stage Build

**Dockerfile.multi:**
```dockerfile
# === Stage 1: Build ===
FROM node:20-alpine AS builder
WORKDIR /app

COPY package.json package-lock.json ./
RUN npm ci

COPY . .
RUN npm run build

# === Stage 2: Serve voi Nginx ===
FROM nginx:1.25-alpine AS production

# Copy nginx config
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy build output
COPY --from=builder /app/dist /usr/share/nginx/html

# Non-root user
RUN chown -R nginx:nginx /usr/share/nginx/html

EXPOSE 80

HEALTHCHECK --interval=30s --timeout=3s \
    CMD wget -qO- http://localhost:80/ || exit 1

CMD ["nginx", "-g", "daemon off;"]
```

**nginx.conf:**
```nginx
server {
    listen 80;
    server_name _;

    root /usr/share/nginx/html;
    index index.html;

    # Gzip
    gzip on;
    gzip_types text/plain text/css application/json application/javascript text/xml;
    gzip_min_length 1000;

    # Cache static assets
    location ~* \.(js|css|png|jpg|jpeg|gif|ico|svg|woff|woff2)$ {
        expires 1y;
        add_header Cache-Control "public, immutable";
    }

    # SPA routing - fallback to index.html
    location / {
        try_files $uri $uri/ /index.html;
    }

    # Security headers
    add_header X-Frame-Options "SAMEORIGIN" always;
    add_header X-Content-Type-Options "nosniff" always;
    add_header X-XSS-Protection "1; mode=block" always;
}
```

**So sanh kich thuoc:**
```bash
# Single-stage (tat ca trong 1)
docker build -t react-app:single -f Dockerfile.single .
# Kich thuoc: ~1.2GB

# Multi-stage
docker build -t react-app:multi -f Dockerfile.multi .
# Kich thuoc: ~25MB

docker images | grep react-app
# react-app   multi    25MB
# react-app   single   1.2GB
```

---

## Bài 4: Docker Networking

```yaml
services:
  nginx:
    image: nginx:alpine
    ports:
      - "80:80"
    volumes:
      - ./nginx/nginx.conf:/etc/nginx/nginx.conf
    networks:
      - frontend-net
    depends_on:
      - api-gateway

  api-gateway:
    build: ./api-gateway
    ports:
      - "8080:8080"
    networks:
      - frontend-net
      - backend-net

  user-service:
    build: ./user-service
    networks:
      - backend-net

  order-service:
    build: ./order-service
    networks:
      - backend-net

  db:
    image: postgres:16-alpine
    environment:
      POSTGRES_PASSWORD: secret
    volumes:
      - db-data:/var/lib/postgresql/data
    networks:
      - backend-net

  prometheus:
    image: prom/prometheus
    ports:
      - "9090:9090"
    networks:
      - monitoring-net
      - backend-net

  grafana:
    image: grafana/grafana
    ports:
      - "3001:3000"
    networks:
      - monitoring-net

networks:
  frontend-net:
  backend-net:
  monitoring-net:

volumes:
  db-data:
```

**Kiem tra network isolation:**
```bash
# Start
docker compose up -d

# Kiem tra: nginx KHONG the ping db
docker compose exec nginx ping db
# ping: bad address 'db' → KHONG ket noi duoc (khac network)

# Kiem tra: api-gateway CO THE ping db
docker compose exec api-gateway ping db
# PING db (172.x.x.x): 56 data bytes → OK (cung backend-net)

# Kiem tra: nginx CO THE ping api-gateway
docker compose exec nginx ping api-gateway
# OK (cung frontend-net)
```

---

## Bài 5: Production-Ready Docker Setup

```dockerfile
# Dockerfile.prod
FROM node:20-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build
RUN npm prune --production

FROM node:20-alpine
WORKDIR /app

# Non-root user
RUN addgroup -S app && adduser -S app -G app

# Copy tu builder
COPY --from=builder --chown=app:app /app/dist ./dist
COPY --from=builder --chown=app:app /app/node_modules ./node_modules
COPY --from=builder --chown=app:app /app/package.json ./

USER app
EXPOSE 3000

HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
    CMD wget -qO- http://localhost:3000/health || exit 1

CMD ["node", "dist/index.js"]
```

```yaml
# docker-compose.prod.yml
services:
  app:
    build:
      context: .
      dockerfile: Dockerfile.prod
    deploy:
      replicas: 3
      resources:
        limits:
          cpus: '0.5'
          memory: 512M
        reservations:
          cpus: '0.25'
          memory: 256M
    restart: unless-stopped
    networks:
      - backend
    logging:
      driver: "json-file"
      options:
        max-size: "10m"
        max-file: "3"

  nginx:
    image: nginx:1.25-alpine
    ports:
      - "80:80"
      - "443:443"
    volumes:
      - ./nginx/nginx.prod.conf:/etc/nginx/nginx.conf:ro
    depends_on:
      app:
        condition: service_healthy
    restart: unless-stopped
    networks:
      - frontend
      - backend

  db:
    image: postgres:16-alpine
    environment:
      POSTGRES_PASSWORD_FILE: /run/secrets/db_password
    secrets:
      - db_password
    volumes:
      - postgres-data:/var/lib/postgresql/data
      - postgres-backup:/backup
    healthcheck:
      test: ["CMD-SHELL", "pg_isready"]
      interval: 10s
      timeout: 5s
      retries: 5
    restart: unless-stopped
    deploy:
      resources:
        limits:
          cpus: '1'
          memory: 1G
    networks:
      - backend

  redis:
    image: redis:7-alpine
    command: redis-server --requirepass ${REDIS_PASSWORD}
    restart: unless-stopped
    networks:
      - backend

secrets:
  db_password:
    file: ./secrets/db_password.txt

networks:
  frontend:
  backend:

volumes:
  postgres-data:
  postgres-backup:
```
