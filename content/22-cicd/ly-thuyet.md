# CI/CD - Lý Thuyết Từ Cơ Bản Đến Nâng Cao

## Mục lục

1. [Giới thiệu về CI/CD](#1-gioi-thieu-ve-cicd)
2. [Continuous Integration (CI)](#2-continuous-integration-ci)
3. [Continuous Delivery vs Continuous Deployment](#3-continuous-delivery-vs-continuous-deployment)
4. [GitHub Actions](#4-github-actions)
5. [Pipeline Stages](#5-pipeline-stages)
6. [Testing trong CI](#6-testing-trong-ci)
7. [Docker trong CI/CD](#7-docker-trong-cicd)
8. [Environment và Secrets](#8-environment-va-secrets)
9. [Deployment Strategies](#9-deployment-strategies)
10. [Monitoring và Rollback](#10-monitoring-va-rollback)
11. [Các CI/CD Tools phổ biến](#11-cac-cicd-tools-pho-bien)
12. [Best Practices](#12-best-practices)

---

## 1. Giới thiệu về CI/CD

### 1.1 CI/CD là gì?

CI/CD là tập hợp các phương pháp tự động hóa quy trình phát triển phần mềm:

- **CI (Continuous Integration):** Tự động build và test mỗi khi có code mới
- **CD (Continuous Delivery):** Tự động chuẩn bị release, deploy thủ công
- **CD (Continuous Deployment):** Tự động deploy lên production

### 1.2 Tại sao cần CI/CD?

| Không có CI/CD | Có CI/CD |
|---------------|----------|
| Build thủ công, dễ sai | Build tự động, nhất quán |
| Test bị bỏ qua | Test bắt buộc chạy |
| "It works on my machine" | Môi trường nhất quán |
| Deploy mất nhiều giờ | Deploy trong vài phút |
| Phát hiện lỗi muộn | Phát hiện lỗi sớm |
| Rollback khó khăn | Rollback nhanh chóng |

### 1.3 Quy trình CI/CD

```
Developer push code
    → CI Server phát hiện
    → Build ứng dụng
    → Chạy unit tests
    → Chạy integration tests
    → Security scan
    → Build Docker image
    → Deploy to staging
    → Chạy E2E tests
    → Deploy to production
    → Monitor & Alert
```

---

## 2. Continuous Integration (CI)

### 2.1 Nguyên tắc CI

1. **Merge thường xuyên:** Mỗi developer merge code vào main ít nhất 1 lần/ngày
2. **Build tự động:** Mỗi commit trigger build tự động
3. **Test tự động:** Mỗi build phải chạy test suite
4. **Fix ngay:** Nếu build fail, fix là ưu tiên số 1
5. **Mọi người thấy được:** Dashboard build status công khai

### 2.2 CI Pipeline cơ bản

```yaml
# Luồng CI điển hình
1. Checkout code
2. Cài đặt dependencies
3. Lint / Format check
4. Unit tests
5. Integration tests
6. Build artifact
7. Báo cáo kết quả
```

---

## 3. Continuous Delivery vs Continuous Deployment

### 3.1 So sánh

```
Continuous Integration
    Code → Build → Test (tự động)

Continuous Delivery
    Code → Build → Test → Staging → [MANUAL APPROVE] → Production

Continuous Deployment
    Code → Build → Test → Staging → Production (HOÀN TOÀN tự động)
```

### 3.2 Khi nào dùng gì?

- **Delivery:** Khi cần human review trước production (finance, healthcare)
- **Deployment:** Khi đã có độ tin cậy cao vào test suite (SaaS, web apps)

---

## 4. GitHub Actions

### 4.1 Khái niệm cơ bản

| Khái niệm | Mô tả |
|-----------|-------|
| Workflow | File YAML định nghĩa pipeline (.github/workflows/) |
| Event/Trigger | Sự kiện kích hoạt workflow (push, PR, schedule) |
| Job | Tập hợp các steps chạy trên 1 runner |
| Step | 1 lệnh hoặc 1 action |
| Action | Thành phần tái sử dụng (marketplace) |
| Runner | Máy chạy workflow (ubuntu, windows, macos) |
| Artifact | File output (build, report) |

### 4.2 Workflow cơ bản

```yaml
# .github/workflows/ci.yml
name: CI Pipeline

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main]

jobs:
  lint:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
        with:
          node-version: '20'
          cache: 'npm'
      - run: npm ci
      - run: npm run lint

  test:
    runs-on: ubuntu-latest
    needs: lint
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
        with:
          node-version: '20'
          cache: 'npm'
      - run: npm ci
      - run: npm test -- --coverage
      - uses: actions/upload-artifact@v4
        with:
          name: coverage
          path: coverage/

  build:
    runs-on: ubuntu-latest
    needs: test
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
        with:
          node-version: '20'
          cache: 'npm'
      - run: npm ci
      - run: npm run build
      - uses: actions/upload-artifact@v4
        with:
          name: build
          path: dist/
```

### 4.3 Triggers phổ biến

```yaml
on:
  # Push lên branch cụ thể
  push:
    branches: [main]
    paths:
      - 'src/**'
      - '!docs/**'

  # Pull Request
  pull_request:
    types: [opened, synchronize, reopened]

  # Định kỳ (cron)
  schedule:
    - cron: '0 2 * * 1'  # Mỗi thứ Hai lúc 2h sáng UTC

  # Thủ công
  workflow_dispatch:
    inputs:
      environment:
        description: 'Deploy environment'
        required: true
        default: 'staging'
        type: choice
        options:
          - staging
          - production

  # Khi release
  release:
    types: [published]
```

### 4.4 Matrix Strategy

```yaml
jobs:
  test:
    strategy:
      matrix:
        node-version: [18, 20, 22]
        os: [ubuntu-latest, windows-latest]
    runs-on: ${{ matrix.os }}
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
        with:
          node-version: ${{ matrix.node-version }}
      - run: npm ci
      - run: npm test
```

### 4.5 Conditional Steps

```yaml
steps:
  - name: Deploy to production
    if: github.ref == 'refs/heads/main' && github.event_name == 'push'
    run: ./deploy.sh production

  - name: Comment on PR
    if: github.event_name == 'pull_request'
    uses: actions/github-script@v7
    with:
      script: |
        github.rest.issues.createComment({
          issue_number: context.issue.number,
          owner: context.repo.owner,
          repo: context.repo.repo,
          body: 'Build passed!'
        })
```

---

## 5. Pipeline Stages

### 5.1 Pipeline hoàn chỉnh

```yaml
name: Full CI/CD Pipeline

on:
  push:
    branches: [main]

jobs:
  # Stage 1: Quality checks
  quality:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - run: npm ci
      - run: npm run lint
      - run: npm run format:check
      - run: npm run type-check

  # Stage 2: Tests
  test:
    runs-on: ubuntu-latest
    needs: quality
    services:
      postgres:
        image: postgres:16
        env:
          POSTGRES_PASSWORD: test
        ports:
          - 5432:5432
    steps:
      - uses: actions/checkout@v4
      - run: npm ci
      - run: npm run test:unit
      - run: npm run test:integration

  # Stage 3: Build
  build:
    runs-on: ubuntu-latest
    needs: test
    steps:
      - uses: actions/checkout@v4
      - run: npm ci
      - run: npm run build
      - uses: actions/upload-artifact@v4
        with:
          name: build-output
          path: dist/

  # Stage 4: Deploy Staging
  deploy-staging:
    runs-on: ubuntu-latest
    needs: build
    environment: staging
    steps:
      - uses: actions/download-artifact@v4
        with:
          name: build-output
      - run: ./scripts/deploy.sh staging

  # Stage 5: E2E Tests on Staging
  e2e:
    runs-on: ubuntu-latest
    needs: deploy-staging
    steps:
      - uses: actions/checkout@v4
      - run: npm ci
      - run: npx playwright install --with-deps
      - run: npm run test:e2e
        env:
          BASE_URL: https://staging.example.com

  # Stage 6: Deploy Production
  deploy-production:
    runs-on: ubuntu-latest
    needs: e2e
    environment: production
    steps:
      - uses: actions/download-artifact@v4
        with:
          name: build-output
      - run: ./scripts/deploy.sh production
```

---

## 6. Testing trong CI

### 6.1 Kim tự tháp Testing

```
        /\
       /  \        E2E Tests (ít, chậm)
      /----\
     /      \      Integration Tests
    /--------\
   /          \    Unit Tests (nhiều, nhanh)
  /____________\
```

### 6.2 Test Configuration

```yaml
# Unit tests với coverage threshold
- name: Unit Tests
  run: |
    npm run test:unit -- --coverage --coverageReporters=text-summary
    # Fail nếu coverage dưới 80%

# Integration tests với database
- name: Integration Tests
  env:
    DATABASE_URL: postgres://postgres:test@localhost:5432/testdb
  run: npm run test:integration

# E2E tests với Playwright
- name: E2E Tests
  run: npx playwright test
  env:
    BASE_URL: ${{ secrets.STAGING_URL }}
```

---

## 7. Docker trong CI/CD

### 7.1 Build và Push Docker Image

```yaml
jobs:
  docker:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Login to Docker Hub
        uses: docker/login-action@v3
        with:
          username: ${{ secrets.DOCKER_USERNAME }}
          password: ${{ secrets.DOCKER_PASSWORD }}

      - name: Build and Push
        uses: docker/build-push-action@v5
        with:
          context: .
          push: true
          tags: |
            user/app:latest
            user/app:${{ github.sha }}
          cache-from: type=gha
          cache-to: type=gha,mode=max
```

---

## 8. Environment và Secrets

### 8.1 GitHub Secrets

```yaml
# Sử dụng secrets
env:
  DATABASE_URL: ${{ secrets.DATABASE_URL }}
  API_KEY: ${{ secrets.API_KEY }}

# Environment secrets
jobs:
  deploy:
    environment: production  # Secrets riêng cho production
    steps:
      - run: echo "Deploying with ${{ secrets.DEPLOY_KEY }}"
```

### 8.2 Environment Protection Rules

- **Required reviewers:** Cần người approve trước khi deploy
- **Wait timer:** Đợi X phút trước khi deploy
- **Branch restrictions:** Chỉ deploy từ main
- **Secrets:** Secrets riêng cho mỗi environment

---

## 9. Deployment Strategies

### 9.1 Các chiến lược deploy

**1. Rolling Deployment:**
```
Server 1: v1 → v2 (update)
Server 2: v1 (vẫn chạy v1)
Server 3: v1 (vẫn chạy v1)

Server 1: v2 (xong)
Server 2: v1 → v2 (update)
Server 3: v1 (vẫn chạy v1)
...
```

**2. Blue-Green Deployment:**
```
Blue (v1) ← Traffic
Green (v2) - chuẩn bị

Kiểm tra Green OK:
Blue (v1)
Green (v2) ← Traffic (chuyển)

Rollback: chuyển lại Blue
```

**3. Canary Deployment:**
```
v1: 90% traffic
v2: 10% traffic (canary)

Monitor OK → tăng dần:
v1: 50% | v2: 50%
v1: 0%  | v2: 100%
```

---

## 10. Monitoring và Rollback

### 10.1 Health Checks

```yaml
- name: Health Check
  run: |
    for i in $(seq 1 30); do
      STATUS=$(curl -s -o /dev/null -w "%{http_code}" https://app.example.com/health)
      if [ "$STATUS" = "200" ]; then
        echo "Health check passed"
        exit 0
      fi
      echo "Attempt $i: Status $STATUS, retrying..."
      sleep 10
    done
    echo "Health check failed!"
    exit 1
```

### 10.2 Rollback

```yaml
- name: Rollback on failure
  if: failure()
  run: |
    echo "Deployment failed, rolling back..."
    ./scripts/rollback.sh ${{ env.PREVIOUS_VERSION }}
```

---

## 11. Các CI/CD Tools phổ biến

| Tool | Đặc điểm |
|------|---------|
| GitHub Actions | Tích hợp với GitHub, miễn phí cho public repos |
| GitLab CI/CD | Tích hợp với GitLab, pipeline mạnh mẽ |
| Jenkins | Open source, tự host, plugins phong phú |
| CircleCI | Cloud-based, nhanh, config dễ đọc |
| Travis CI | Đơn giản, tốt cho open source |
| AWS CodePipeline | Tích hợp với AWS ecosystem |
| Azure DevOps | Tích hợp với Microsoft ecosystem |

---

## 12. Best Practices

1. **Build nhanh:** Pipeline nên hoàn thành dưới 10 phút
2. **Fail fast:** Đặt lint/type check trước tests để fail sớm
3. **Cache dependencies:** Dùng cache cho npm, maven, docker layers
4. **Parallel jobs:** Chạy test song song khi có thể
5. **Idempotent deploys:** Deploy nhiều lần cùng kết quả
6. **Secrets management:** KHÔNG BAO GÌỜ hardcode secrets
7. **Environment parity:** Staging giống production nhất có thể
8. **Monitoring:** Theo dõi sau deploy, tự động alert
9. **Rollback plan:** Luôn có kế hoạch rollback
10. **Infrastructure as Code:** Dùng Docker, Terraform, Kubernetes

---

## Tổng kết

CI/CD là xu hướng bắt buộc trong phát triển phần mềm hiện đại:

1. **CI:** Tự động build và test mỗi commit
2. **CD:** Tự động (hoặc bán tự động) deploy lên production
3. **GitHub Actions:** Công cụ CI/CD phổ biến, dễ học
4. **Pipeline stages:** Lint → Test → Build → Deploy
5. **Testing:** Unit → Integration → E2E
6. **Docker:** Container hóa để deploy nhất quán
7. **Strategies:** Rolling, Blue-Green, Canary
8. **Monitoring:** Health checks và rollback tự động
