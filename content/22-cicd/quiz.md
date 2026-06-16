# Quiz - CI/CD

## Câu 1

[TYPE: MULTIPLE_CHOICE]

CI trong CI/CD là viết tắt của gì?

- [ ] Code Integration
- [x] Continuous Integration
- [ ] Complete Installation
- [ ] Cloud Infrastructure

> **Giải thích:** CI = Continuous Integration - quy trình tự động hóa việc build và test code mỗi khi developer push code. Mục đích là phát hiện lỗi sớm và đảm bảo code luôn ở trạng thái có thể release.

## Câu 2

[TYPE: MULTIPLE_CHOICE]

Sự khác biệt giữa Continuous Delivery và Continuous Deployment?

- [ ] Giống nhau
- [x] Delivery cần manual approval trước production, Deployment hoàn toàn tự động
- [ ] Delivery nhanh hơn Deployment
- [ ] Deployment không cần testing

> **Giải thích:** Continuous Delivery: code được tự động build, test, deploy lên staging nhưng CẦN người duyệt (manual approval) trước khi lên production. Continuous Deployment: HOÀN TOÀN tự động, code đã pass tests sẽ được deploy lên production không cần duyệt.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Trong GitHub Actions, `needs: lint` có ý nghĩa gì?

- [ ] Job này sẽ chạy song song với lint
- [x] Job này CHỈ chạy SAU KHI job lint thành công
- [ ] Job này thay thế job lint
- [ ] Job này không cần lint

> **Giải thích:** `needs` tạo dependency giữa các jobs. `needs: lint` nghĩa là job hiện tại sẽ ĐỢI job `lint` hoàn thành THÀNH CÔNG trước khi bắt đầu. Nếu `lint` fail, job này sẽ bị skip. Đây là cách tạo pipeline tuần tự.

## Câu 4

[TYPE: MULTIPLE_CHOICE]

`npm ci` khác `npm install` như thế nào trong CI?

- [ ] Giống nhau
- [ ] `npm ci` chậm hơn
- [x] `npm ci` cài chính xác từ lock file, nhanh hơn, và xóa node_modules trước
- [ ] `npm ci` không cần package.json

> **Giải thích:** `npm ci` (clean install): xóa node_modules, cài chính xác theo package-lock.json (không thay đổi lock file), nhanh hơn. `npm install` có thể update lock file và không đảm bảo version chính xác. Trong CI, LUÔN dùng `npm ci` để đảm bảo tính nhất quán (reproducibility).

## Câu 5

[TYPE: MULTIPLE_CHOICE]

GitHub Secrets được sử dụng để làm gì?

- [ ] Mã hóa source code
- [x] Lưu trữ thông tin nhạy cảm (API keys, passwords) an toàn cho CI/CD
- [ ] Ẩn commit history
- [ ] Bảo vệ branches

> **Giải thích:** GitHub Secrets lưu trữ các giá trị nhạy cảm (API keys, tokens, passwords) được mã hóa. Chúng chỉ có thể truy cập trong GitHub Actions workflows qua `${{ secrets.SECRET_NAME }}`. Secrets KHÔNG hiển thị trong logs và KHÔNG thể xem lại giá trị.

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Blue-Green Deployment là gì?

- [ ] Deploy màu xanh trước, màu xanh lá sau
- [x] Chạy 2 môi trường giống nhau, chuyển traffic giữa chúng khi deploy
- [ ] Deploy 2 lần mỗi release
- [ ] Chỉ deploy vào ban đêm

> **Giải thích:** Blue-Green: 2 môi trường production giống nhau (Blue và Green). Một cái đang serve traffic, cái kia idle. Deploy lên môi trường idle, test, rồi chuyển traffic. Rollback nhanh: chỉ cần chuyển traffic về môi trường cũ.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

Canary Deployment là gì?

- [ ] Deploy lên toàn bộ server cùng lúc
- [ ] Deploy chỉ cho team nội bộ
- [x] Deploy lên một phần nhỏ users trước, tăng dần nếu không có lỗi
- [ ] Deploy vào ban đêm khi ít traffic

> **Giải thích:** Canary: deploy version mới cho 1-10% users trước (canary group). Monitor lỗi và performance. Nếu OK, tăng dần (10% → 25% → 50% → 100%). Nếu có lỗi, rollback chỉ ảnh hưởng phần nhỏ. Tên "canary" từ chim hoàng yến trong hầm mỏ (cảnh báo sớm).

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Matrix strategy trong GitHub Actions dùng để làm gì?

- [ ] Mã hóa build output
- [x] Chạy cùng pipeline với nhiều cấu hình khác nhau (VD: nhiều Node versions, nhiều OS)
- [ ] Tạo nhiều repositories
- [ ] Chia code thành modules

> **Giải thích:** Matrix strategy chạy cùng job với nhiều tổ hợp cấu hình. Ví dụ: test trên Node 18, 20, 22 x Ubuntu, Windows = 6 jobs song song. Đảm bảo ứng dụng hoạt động trên nhiều môi trường mà không cần viết nhiều workflows.

## Câu 9

[TYPE: MULTIPLE_CHOICE]

Tại sao nên đặt lint/type-check TRƯỚC unit tests trong pipeline?

- [ ] Lint nhanh hơn nên đặt trước cho đẹp
- [x] Lint nhanh, fail sớm - không cần chạy tests lâu lắc nếu code format sai
- [ ] Lint phải chạy trước vì tests phụ thuộc vào nó
- [ ] Không có lý do, thứ tự không quan trọng

> **Giải thích:** Nguyên tắc "Fail Fast": đặt các checks nhanh nhất trước. Lint/format check chỉ mất vài giây. Nếu fail, pipeline dừng ngay mà không mất thời gian chạy tests (có thể mất vài phút). Tiết kiệm thời gian và tài nguyên CI runner.

## Câu 10

[TYPE: TRUE_FALSE]

Trong CI/CD, môi trường Staging nên giống Production nhất có thể.

- [x] True
- [ ] False

> **Giải thích:** Environment Parity: Staging nên "mirror" Production (cùng OS, cùng dependencies, cấu hình tương tự). Điều này đảm bảo test trên Staging phản ánh chính xác hành vi trên Production. Sự khác biệt giữa environments là nguyên nhân phổ biến của lỗi "works on staging but fails in production".

## Câu 11

[TYPE: MULTIPLE_CHOICE]

Artifact trong CI/CD là gì?

- [ ] Một loại bug
- [x] File/folder output từ build process (VD: compiled code, reports, docker images)
- [ ] Secret key
- [ ] Tên của CI server

> **Giải thích:** Artifacts là các file được tạo ra trong quá trình build: compiled code (dist/), test reports, coverage reports, Docker images, deployment packages. Chúng được lưu trữ và truyền giữa các jobs trong pipeline (vd: job build tạo artifact, job deploy sử dụng artifact đó).

## Câu 12

[TYPE: MULTIPLE_CHOICE]

Infrastructure as Code (IaC) có lợi ích gì cho CI/CD?

- [ ] Làm code chạy nhanh hơn
- [ ] Thay thế CI/CD pipeline
- [x] Quản lý infrastructure bằng code, có thể version control và tự động hóa
- [ ] Chỉ dùng cho frontend

> **Giải thích:** IaC (Terraform, CloudFormation, Pulumi) định nghĩa infrastructure bằng code thay vì cấu hình thủ công. Lợi ích: version control (track changes), tính nhất quán cao (reproducibility - tạo lại môi trường giống nhau), automation (tích hợp vào CI/CD pipeline), tránh tình trạng "snowflake servers".
