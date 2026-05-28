# Hướng dẫn Deploy miễn phí - Study Roadmap

## Tổng quan

App này là Spring Boot 3.2 + Java 17, **không cần database** (dùng file Markdown), nên deploy khá đơn giản.

> ⚠️ **Lưu ý quan trọng**: App có tính năng chạy code Java (`javac`/`java`) trên server. Khi deploy, cần dùng image **JDK** (không phải JRE) và nên cân nhắc bảo mật (sandbox) nếu mở public.

---

## Tùy chọn 1: Render.com ⭐ (Khuyên dùng)

**Free tier**: Web service miễn phí, tự động deploy từ GitHub.

### Bước thực hiện:

1. Đăng ký tại [render.com](https://render.com) (dùng GitHub login)
2. Click **"New" → "Web Service"**
3. Kết nối repo `lechieu26/Study`
4. Render sẽ tự detect `render.yaml` và cấu hình:
   - **Runtime**: Docker
   - **Plan**: Free
5. Click **"Create Web Service"** → chờ build (~3-5 phút)
6. App sẽ có URL dạng: `https://study-roadmap.onrender.com`

### Hạn chế free tier:
- Tự động sleep sau 15 phút không hoạt động (lần đầu truy cập sẽ chậm ~30s)
- 750 giờ/tháng
- RAM: 512MB

---

## Tùy chọn 2: Railway.app

**Free tier**: $5 credit/tháng (đủ chạy app nhỏ).

### Bước thực hiện:

1. Đăng ký tại [railway.app](https://railway.app)
2. Click **"New Project" → "Deploy from GitHub Repo"**
3. Chọn repo `lechieu26/Study`
4. Railway tự detect Dockerfile
5. Thêm biến môi trường:
   - `PORT` = `8080`
6. Deploy tự động

### Hạn chế:
- $5 credit/tháng (khoảng 500 giờ chạy)
- Cần verify bằng thẻ tín dụng (không bị charge)

---

## Tùy chọn 3: Fly.io

**Free tier**: 3 shared VMs, 256MB RAM mỗi cái.

### Bước thực hiện:

1. Cài `flyctl`:
   ```bash
   curl -L https://fly.io/install.sh | sh
   ```

2. Đăng nhập:
   ```bash
   fly auth signup
   ```

3. Trong thư mục project, tạo app:
   ```bash
   fly launch --name study-roadmap
   ```
   - Chọn region gần nhất (ví dụ: `sin` cho Singapore)
   - Chọn **Free** tier

4. Deploy:
   ```bash
   fly deploy
   ```

5. App sẽ có URL: `https://study-roadmap.fly.dev`

### Hạn chế:
- RAM: 256MB (cần tune JVM: `-Xmx200m`)
- 3 VMs miễn phí
- Cần verify thẻ tín dụng

---

## Tùy chọn 4: Google Cloud Run

**Free tier**: 2 triệu request/tháng, 360,000 vCPU-giây.

### Bước thực hiện:

1. Cài [Google Cloud SDK](https://cloud.google.com/sdk/docs/install)
2. Build & push Docker image:
   ```bash
   gcloud builds submit --tag gcr.io/PROJECT_ID/study-roadmap
   ```
3. Deploy:
   ```bash
   gcloud run deploy study-roadmap \
     --image gcr.io/PROJECT_ID/study-roadmap \
     --platform managed \
     --region asia-southeast1 \
     --allow-unauthenticated \
     --memory 512Mi \
     --port 8080
   ```

### Hạn chế:
- Cần tài khoản Google Cloud (free trial $300)
- Cold start chậm hơn

---

## Chạy local bằng Docker

```bash
# Build
docker build -t study-roadmap .

# Chạy
docker run -p 8080:8080 study-roadmap

# Truy cập: http://localhost:8080
```

---

## So sánh nhanh

| Nền tảng       | Miễn phí      | RAM     | Sleep? | Cần thẻ? |
|----------------|---------------|---------|--------|-----------|
| **Render**     | 750h/tháng    | 512MB   | Có     | Không     |
| **Railway**    | $5/tháng      | 512MB   | Không  | Có        |
| **Fly.io**     | 3 VMs         | 256MB   | Không  | Có        |
| **Cloud Run**  | 2M req/tháng  | 512MB+  | Có     | Có*       |

*Google Cloud cho $300 credit khi đăng ký mới.

---

## Khuyến nghị

**Nếu muốn đơn giản nhất**: → **Render.com** (không cần thẻ, auto-deploy từ GitHub)

**Nếu muốn không bị sleep**: → **Railway** hoặc **Fly.io**

**Nếu muốn scale sau này**: → **Google Cloud Run**
