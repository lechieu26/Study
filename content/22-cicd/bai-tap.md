# CI/CD - Bài Tập

## Bài 1: GitHub Actions CI cơ bản
**Độ khó: Dễ**

Tạo workflow CI cho một dự án Node.js:

1. Trigger khi push lên main hoặc tạo PR
2. Chạy trên ubuntu-latest
3. Cài đặt Node.js 20 với npm cache
4. Cài dependencies (`npm ci`)
5. Chạy lint (`npm run lint`)
6. Chạy unit tests (`npm test`)
7. Upload coverage report dạng artifact

---

## Bài 2: Multi-stage Pipeline
**Độ khó: Trung bình**

Tạo pipeline nhiều giai đoạn:

1. **Job lint:** Chạy ESLint và Prettier check
2. **Job test:** Chạy unit tests (cần lint pass trước)
3. **Job build:** Build ứng dụng (cần test pass trước)
4. **Matrix testing:** Test trên Node 18, 20, 22
5. **Conditional deploy:** Chỉ deploy khi push lên main (không phải PR)
6. Sử dụng caching cho node_modules
7. Upload build artifact

---

## Bài 3: Docker CI/CD Pipeline
**Độ khó: Trung bình**

Tạo pipeline build và push Docker image:

1. Build Docker image từ Dockerfile
2. Tag image với: `latest` và git commit SHA
3. Push lên Docker Hub (sử dụng secrets)
4. Scan image với Trivy (security vulnerabilities)
5. Deploy lên staging environment
6. Chạy health check sau deploy

---

## Bài 4: Full-Stack CI/CD
**Độ khó: Khó**

Tạo CI/CD pipeline cho ứng dụng full-stack (frontend + backend):

1. **Frontend job:** Lint, test, build React app
2. **Backend job:** Lint, test (với PostgreSQL service), build Java/Node app
3. **E2E tests:** Chạy Playwright tests trên staging
4. **Deploy staging:** Tự động sau khi tests pass
5. **Deploy production:** Cần manual approval (environment protection)
6. **Notification:** Gửi thông báo Slack/Discord khi deploy thành công/thất bại
7. **Rollback:** Tự động rollback nếu health check fail

---

## Bài 5: Reusable Workflows
**Độ khó: Khó**

Tạo bộ reusable workflows và composite actions:

1. Tạo **composite action** cho: setup Node + install deps + cache
2. Tạo **reusable workflow** cho: lint + test + build
3. Tạo **reusable workflow** cho: Docker build + push
4. Gọi các reusable workflows từ main workflow
5. Tạo workflow `release.yml` sử dụng semantic-release:
   - Tự động tăng version dựa trên conventional commits
   - Tạo GitHub Release với changelog
   - Build và push Docker image với version tag
6. Tạo workflow `scheduled-check.yml` chạy hàng tuần kiểm tra dependencies lỗi thời
