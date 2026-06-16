# CI/CD - Dap An Bai Tap

## Bài 1: GitHub Actions CI co ban

```yaml
# .github/workflows/ci.yml
name: CI

on:
  push:
    branches: [main]
  pull_request:
    branches: [main]

jobs:
  ci:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Setup Node.js
        uses: actions/setup-node@v4
        with:
          node-version: '20'
          cache: 'npm'

      - name: Install dependencies
        run: npm ci

      - name: Run lint
        run: npm run lint

      - name: Run unit tests
        run: npm test -- --coverage

      - name: Upload coverage
        uses: actions/upload-artifact@v4
        with:
          name: coverage-report
          path: coverage/
          retention-days: 7
```

**Giải thích:**
- `actions/checkout@v4`: Clone repo vao runner
- `actions/setup-node@v4` voi `cache: 'npm'`: Cai Node.js va cache node_modules
- `npm ci`: Cai dependencies tu lock file (nhanh va nhat quan hon `npm install`)
- `--coverage`: Tao bao cao coverage
- `retention-days: 7`: Giu artifact 7 ngay

---

## Bài 2: Multi-stage Pipeline

```yaml
name: Multi-Stage CI

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
      - run: npx prettier --check "src/**/*.{ts,tsx,js,jsx}"

  test:
    runs-on: ubuntu-latest
    needs: lint
    strategy:
      matrix:
        node-version: [18, 20, 22]
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
        with:
          node-version: ${{ matrix.node-version }}
          cache: 'npm'
      - run: npm ci
      - run: npm test -- --coverage
      - name: Upload coverage
        if: matrix.node-version == 20
        uses: actions/upload-artifact@v4
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
          name: build-output
          path: dist/

  deploy:
    runs-on: ubuntu-latest
    needs: build
    if: github.event_name == 'push' && github.ref == 'refs/heads/main'
    environment: staging
    steps:
      - uses: actions/download-artifact@v4
        with:
          name: build-output
          path: dist/
      - name: Deploy to staging
        run: echo "Deploying to staging..."
        # Thay bang lenh deploy thuc te
```

**Giải thích:**
- `needs: lint`: Job test CHI chay sau khi lint pass
- `strategy.matrix`: Chay test tren 3 phien ban Node.js song song
- `if: matrix.node-version == 20`: Chi upload coverage tu Node 20
- `if: github.event_name == 'push' && github.ref == 'refs/heads/main'`: Chi deploy khi push len main

---

## Bài 3: Docker CI/CD Pipeline

```yaml
name: Docker CI/CD

on:
  push:
    branches: [main]

jobs:
  build-and-push:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Set up Docker Buildx
        uses: docker/setup-buildx-action@v3

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
            ${{ secrets.DOCKER_USERNAME }}/myapp:latest
            ${{ secrets.DOCKER_USERNAME }}/myapp:${{ github.sha }}
          cache-from: type=gha
          cache-to: type=gha,mode=max

      - name: Scan image with Trivy
        uses: aquasecurity/trivy-action@master
        with:
          image-ref: ${{ secrets.DOCKER_USERNAME }}/myapp:latest
          format: 'table'
          severity: 'CRITICAL,HIGH'
          exit-code: '1'

  deploy-staging:
    runs-on: ubuntu-latest
    needs: build-and-push
    environment: staging
    steps:
      - name: Deploy to staging
        run: |
          echo "Deploying ${{ github.sha }} to staging..."
          # ssh user@staging-server "docker pull myapp:${{ github.sha }} && docker-compose up -d"

      - name: Health check
        run: |
          for i in $(seq 1 30); do
            STATUS=$(curl -s -o /dev/null -w "%{http_code}" https://staging.example.com/health || echo "000")
            if [ "$STATUS" = "200" ]; then
              echo "Health check passed!"
              exit 0
            fi
            echo "Attempt $i: Status $STATUS"
            sleep 10
          done
          echo "Health check FAILED"
          exit 1
```

---

## Bài 4: Full-Stack CI/CD

```yaml
name: Full-Stack CI/CD

on:
  push:
    branches: [main]
  pull_request:
    branches: [main]

jobs:
  frontend:
    runs-on: ubuntu-latest
    defaults:
      run:
        working-directory: ./frontend
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
        with:
          node-version: '20'
          cache: 'npm'
          cache-dependency-path: frontend/package-lock.json
      - run: npm ci
      - run: npm run lint
      - run: npm test -- --coverage
      - run: npm run build
      - uses: actions/upload-artifact@v4
        with:
          name: frontend-build
          path: frontend/dist/

  backend:
    runs-on: ubuntu-latest
    defaults:
      run:
        working-directory: ./backend
    services:
      postgres:
        image: postgres:16
        env:
          POSTGRES_USER: test
          POSTGRES_PASSWORD: test
          POSTGRES_DB: testdb
        ports:
          - 5432:5432
        options: >-
          --health-cmd pg_isready
          --health-interval 10s
          --health-timeout 5s
          --health-retries 5
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
        with:
          node-version: '20'
          cache: 'npm'
          cache-dependency-path: backend/package-lock.json
      - run: npm ci
      - run: npm run lint
      - run: npm test
        env:
          DATABASE_URL: postgres://test:test@localhost:5432/testdb
      - run: npm run build
      - uses: actions/upload-artifact@v4
        with:
          name: backend-build
          path: backend/dist/

  deploy-staging:
    runs-on: ubuntu-latest
    needs: [frontend, backend]
    if: github.event_name == 'push' && github.ref == 'refs/heads/main'
    environment: staging
    steps:
      - uses: actions/checkout@v4
      - uses: actions/download-artifact@v4
        with:
          name: frontend-build
          path: frontend-dist/
      - uses: actions/download-artifact@v4
        with:
          name: backend-build
          path: backend-dist/
      - name: Deploy to staging
        run: echo "Deploying to staging..."

  e2e:
    runs-on: ubuntu-latest
    needs: deploy-staging
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
        with:
          node-version: '20'
      - run: npm ci
      - run: npx playwright install --with-deps
      - run: npx playwright test
        env:
          BASE_URL: https://staging.example.com

  deploy-production:
    runs-on: ubuntu-latest
    needs: e2e
    environment: production  # Requires manual approval
    steps:
      - name: Deploy to production
        run: echo "Deploying to production..."

      - name: Health check
        id: health
        run: |
          sleep 30
          STATUS=$(curl -s -o /dev/null -w "%{http_code}" https://app.example.com/health || echo "000")
          echo "status=$STATUS" >> $GITHUB_OUTPUT

      - name: Rollback on failure
        if: steps.health.outputs.status != '200'
        run: |
          echo "Health check failed! Rolling back..."
          # ./scripts/rollback.sh

      - name: Notify success
        if: success()
        run: |
          curl -X POST "${{ secrets.SLACK_WEBHOOK }}" \
            -H 'Content-Type: application/json' \
            -d '{"text":"Deploy v${{ github.sha }} to production SUCCESS"}'

      - name: Notify failure
        if: failure()
        run: |
          curl -X POST "${{ secrets.SLACK_WEBHOOK }}" \
            -H 'Content-Type: application/json' \
            -d '{"text":"Deploy FAILED! Check: ${{ github.server_url }}/${{ github.repository }}/actions/runs/${{ github.run_id }}"}'
```
