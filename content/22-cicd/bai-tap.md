# CI/CD - Bai Tap

## Bài 1: GitHub Actions CI co ban
**Độ khó: Dễ**

Tao workflow CI cho mot du an Node.js:

1. Trigger khi push len main hoac tao PR
2. Chay tren ubuntu-latest
3. Cai dat Node.js 20 voi npm cache
4. Cai dependencies (`npm ci`)
5. Chay lint (`npm run lint`)
6. Chay unit tests (`npm test`)
7. Upload coverage report dang artifact

---

## Bài 2: Multi-stage Pipeline
**Độ khó: Trung bình**

Tao pipeline nhieu giai doan:

1. **Job lint:** Chay ESLint va Prettier check
2. **Job test:** Chay unit tests (can lint pass truoc)
3. **Job build:** Build ung dung (can test pass truoc)
4. **Matrix testing:** Test tren Node 18, 20, 22
5. **Conditional deploy:** Chi deploy khi push len main (khong phai PR)
6. Su dung caching cho node_modules
7. Upload build artifact

---

## Bài 3: Docker CI/CD Pipeline
**Độ khó: Trung bình**

Tao pipeline build va push Docker image:

1. Build Docker image tu Dockerfile
2. Tag image voi: `latest` va git commit SHA
3. Push len Docker Hub (su dung secrets)
4. Scan image voi Trivy (security vulnerabilities)
5. Deploy len staging environment
6. Chay health check sau deploy

---

## Bài 4: Full-Stack CI/CD
**Độ khó: Khó**

Tao CI/CD pipeline cho ung dung full-stack (frontend + backend):

1. **Frontend job:** Lint, test, build React app
2. **Backend job:** Lint, test (voi PostgreSQL service), build Java/Node app
3. **E2E tests:** Chay Playwright tests tren staging
4. **Deploy staging:** Tu dong sau khi tests pass
5. **Deploy production:** Can manual approval (environment protection)
6. **Notification:** Gui thong bao Slack/Discord khi deploy thanh cong/that bai
7. **Rollback:** Tu dong rollback neu health check fail

---

## Bài 5: Reusable Workflows
**Độ khó: Khó**

Tao bo reusable workflows va composite actions:

1. Tao **composite action** cho: setup Node + install deps + cache
2. Tao **reusable workflow** cho: lint + test + build
3. Tao **reusable workflow** cho: Docker build + push
4. Goi cac reusable workflows tu main workflow
5. Tao workflow `release.yml` su dung semantic-release:
   - Tu dong tang version dua tren conventional commits
   - Tao GitHub Release voi changelog
   - Build va push Docker image voi version tag
6. Tao workflow `scheduled-check.yml` chay hang tuan kiem tra dependencies loi thoi
