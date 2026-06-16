# CI/CD - Ly Thuyet Tu Co Ban Den Nang Cao

## Muc luc

1. [Gioi thieu ve CI/CD](#1-gioi-thieu-ve-cicd)
2. [Continuous Integration (CI)](#2-continuous-integration-ci)
3. [Continuous Delivery vs Continuous Deployment](#3-continuous-delivery-vs-continuous-deployment)
4. [GitHub Actions](#4-github-actions)
5. [Pipeline Stages](#5-pipeline-stages)
6. [Testing trong CI](#6-testing-trong-ci)
7. [Docker trong CI/CD](#7-docker-trong-cicd)
8. [Environment va Secrets](#8-environment-va-secrets)
9. [Deployment Strategies](#9-deployment-strategies)
10. [Monitoring va Rollback](#10-monitoring-va-rollback)
11. [Cac CI/CD Tools pho bien](#11-cac-cicd-tools-pho-bien)
12. [Best Practices](#12-best-practices)

---

## 1. Gioi thieu ve CI/CD

### 1.1 CI/CD la gi?

CI/CD la tap hop cac phuong phap tu dong hoa quy trinh phat trien phan mem:

- **CI (Continuous Integration):** Tu dong build va test moi khi co code moi
- **CD (Continuous Delivery):** Tu dong chuan bi release, deploy thu cong
- **CD (Continuous Deployment):** Tu dong deploy len production

### 1.2 Tai sao can CI/CD?

| Khong co CI/CD | Co CI/CD |
|---------------|----------|
| Build thu cong, de sai | Build tu dong, nhat quan |
| Test bi bo qua | Test bat buoc chay |
| "It works on my machine" | Moi truong nhat quan |
| Deploy mat nhieu gio | Deploy trong vai phut |
| Phat hien loi muon | Phat hien loi som |
| Rollback kho khan | Rollback nhanh chong |

### 1.3 Quy trinh CI/CD

```
Developer push code
    → CI Server phat hien
    → Build ung dung
    → Chay unit tests
    → Chay integration tests
    → Security scan
    → Build Docker image
    → Deploy to staging
    → Chay E2E tests
    → Deploy to production
    → Monitor & Alert
```

---

## 2. Continuous Integration (CI)

### 2.1 Nguyen tac CI

1. **Merge thuong xuyen:** Moi developer merge code vao main it nhat 1 lan/ngay
2. **Build tu dong:** Moi commit trigger build tu dong
3. **Test tu dong:** Moi build phai chay test suite
4. **Fix ngay:** Neu build fail, fix la uu tien so 1
5. **Moi nguoi thay duoc:** Dashboard build status cong khai

### 2.2 CI Pipeline co ban

```yaml
# Luong CI dien hinh
1. Checkout code
2. Cai dat dependencies
3. Lint / Format check
4. Unit tests
5. Integration tests
6. Build artifact
7. Bao cao ket qua
```

---

## 3. Continuous Delivery vs Continuous Deployment

### 3.1 So sanh

```
Continuous Integration
    Code → Build → Test (tu dong)

Continuous Delivery
    Code → Build → Test → Staging → [MANUAL APPROVE] → Production

Continuous Deployment
    Code → Build → Test → Staging → Production (HOAN TOAN tu dong)
```

### 3.2 Khi nao dung gi?

- **Delivery:** Khi can human review truoc production (finance, healthcare)
- **Deployment:** Khi da co do tin cay cao vao test suite (SaaS, web apps)

---

## 4. GitHub Actions

### 4.1 Khai niem co ban

| Khai niem | Mo ta |
|-----------|-------|
| Workflow | File YAML dinh nghia pipeline (.github/workflows/) |
| Event/Trigger | Su kien kich hoat workflow (push, PR, schedule) |
| Job | Tap hop cac steps chay tren 1 runner |
| Step | 1 lenh hoac 1 action |
| Action | Thanh phan tai su dung (marketplace) |
| Runner | May chay workflow (ubuntu, windows, macos) |
| Artifact | File output (build, report) |

### 4.2 Workflow co ban

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

### 4.3 Triggers pho bien

```yaml
on:
  # Push len branch cu the
  push:
    branches: [main]
    paths:
      - 'src/**'
      - '!docs/**'

  # Pull Request
  pull_request:
    types: [opened, synchronize, reopened]

  # Dinh ky (cron)
  schedule:
    - cron: '0 2 * * 1'  # Moi thu Hai luc 2h sang UTC

  # Thu cong
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

### 5.1 Pipeline hoan chinh

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

### 6.1 Kim tu thap Testing

```
        /\
       /  \        E2E Tests (it, cham)
      /----\
     /      \      Integration Tests
    /--------\
   /          \    Unit Tests (nhieu, nhanh)
  /____________\
```

### 6.2 Test Configuration

```yaml
# Unit tests voi coverage threshold
- name: Unit Tests
  run: |
    npm run test:unit -- --coverage --coverageReporters=text-summary
    # Fail neu coverage duoi 80%

# Integration tests voi database
- name: Integration Tests
  env:
    DATABASE_URL: postgres://postgres:test@localhost:5432/testdb
  run: npm run test:integration

# E2E tests voi Playwright
- name: E2E Tests
  run: npx playwright test
  env:
    BASE_URL: ${{ secrets.STAGING_URL }}
```

---

## 7. Docker trong CI/CD

### 7.1 Build va Push Docker Image

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

## 8. Environment va Secrets

### 8.1 GitHub Secrets

```yaml
# Su dung secrets
env:
  DATABASE_URL: ${{ secrets.DATABASE_URL }}
  API_KEY: ${{ secrets.API_KEY }}

# Environment secrets
jobs:
  deploy:
    environment: production  # Secrets rieng cho production
    steps:
      - run: echo "Deploying with ${{ secrets.DEPLOY_KEY }}"
```

### 8.2 Environment Protection Rules

- **Required reviewers:** Can nguoi approve truoc khi deploy
- **Wait timer:** Doi X phut truoc khi deploy
- **Branch restrictions:** Chi deploy tu main
- **Secrets:** Secrets rieng cho moi environment

---

## 9. Deployment Strategies

### 9.1 Cac chien luoc deploy

**1. Rolling Deployment:**
```
Server 1: v1 → v2 (update)
Server 2: v1 (van chay v1)
Server 3: v1 (van chay v1)

Server 1: v2 (xong)
Server 2: v1 → v2 (update)
Server 3: v1 (van chay v1)
...
```

**2. Blue-Green Deployment:**
```
Blue (v1) ← Traffic
Green (v2) - chuan bi

Kiem tra Green OK:
Blue (v1)
Green (v2) ← Traffic (chuyen)

Rollback: chuyen lai Blue
```

**3. Canary Deployment:**
```
v1: 90% traffic
v2: 10% traffic (canary)

Monitor OK → tang dan:
v1: 50% | v2: 50%
v1: 0%  | v2: 100%
```

---

## 10. Monitoring va Rollback

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

## 11. Cac CI/CD Tools pho bien

| Tool | Dac diem |
|------|---------|
| GitHub Actions | Tich hop voi GitHub, mien phi cho public repos |
| GitLab CI/CD | Tich hop voi GitLab, pipeline manh me |
| Jenkins | Open source, tu host, plugins phong phu |
| CircleCI | Cloud-based, nhanh, config de doc |
| Travis CI | Don gian, tot cho open source |
| AWS CodePipeline | Tich hop voi AWS ecosystem |
| Azure DevOps | Tich hop voi Microsoft ecosystem |

---

## 12. Best Practices

1. **Build nhanh:** Pipeline nen hoan thanh duoi 10 phut
2. **Fail fast:** Dat lint/type check truoc tests de fail som
3. **Cache dependencies:** Dung cache cho npm, maven, docker layers
4. **Parallel jobs:** Chay test song song khi co the
5. **Idempotent deploys:** Deploy nhieu lan cung ket qua
6. **Secrets management:** KHONG BAO GIO hardcode secrets
7. **Environment parity:** Staging giong production nhat co the
8. **Monitoring:** Theo doi sau deploy, tu dong alert
9. **Rollback plan:** Luon co ke hoach rollback
10. **Infrastructure as Code:** Dung Docker, Terraform, Kubernetes

---

## Tong ket

CI/CD la xu huong bat buoc trong phat trien phan mem hien dai:

1. **CI:** Tu dong build va test moi commit
2. **CD:** Tu dong (hoac ban tu dong) deploy len production
3. **GitHub Actions:** Cong cu CI/CD pho bien, de hoc
4. **Pipeline stages:** Lint → Test → Build → Deploy
5. **Testing:** Unit → Integration → E2E
6. **Docker:** Container hoa de deploy nhat quan
7. **Strategies:** Rolling, Blue-Green, Canary
8. **Monitoring:** Health checks va rollback tu dong
