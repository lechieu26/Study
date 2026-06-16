# Quiz - CI/CD

## Câu 1

[TYPE: MULTIPLE_CHOICE]

CI trong CI/CD la viet tat cua gi?

- [ ] Code Integration
- [x] Continuous Integration
- [ ] Complete Installation
- [ ] Cloud Infrastructure

> **Giải thích:** CI = Continuous Integration - quy trinh tu dong hoa viec build va test code moi khi developer push code. Muc dich la phat hien loi som va dam bao code luon o trang thai co the release.

## Câu 2

[TYPE: MULTIPLE_CHOICE]

Su khac biet giua Continuous Delivery va Continuous Deployment?

- [ ] Giong nhau
- [x] Delivery can manual approval truoc production, Deployment hoan toan tu dong
- [ ] Delivery nhanh hon Deployment
- [ ] Deployment khong can testing

> **Giải thích:** Continuous Delivery: code duoc tu dong build, test, deploy len staging nhung CAN nguoi duyet (manual approval) truoc khi len production. Continuous Deployment: HOAN TOAN tu dong, code da pass tests se duoc deploy len production khong can duyet.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Trong GitHub Actions, `needs: lint` co y nghia gi?

- [ ] Job nay se chay song song voi lint
- [x] Job nay CHI chay SAU KHI job lint thanh cong
- [ ] Job nay thay the job lint
- [ ] Job nay khong can lint

> **Giải thích:** `needs` tao dependency giua cac jobs. `needs: lint` nghia la job hien tai se DOI job `lint` hoan thanh THANH CONG truoc khi bat dau. Neu `lint` fail, job nay se bi skip. Day la cach tao pipeline tuan tu.

## Câu 4

[TYPE: MULTIPLE_CHOICE]

`npm ci` khac `npm install` nhu the nao trong CI?

- [ ] Giong nhau
- [ ] `npm ci` cham hon
- [x] `npm ci` cai chinh xac tu lock file, nhanh hon, va xoa node_modules truoc
- [ ] `npm ci` khong can package.json

> **Giải thích:** `npm ci` (clean install): xoa node_modules, cai chinh xac theo package-lock.json (khong thay doi lock file), nhanh hon. `npm install` co the update lock file va khong dam bao version chinh xac. Trong CI, LUON dung `npm ci` de dam bao reproducibility.

## Câu 5

[TYPE: MULTIPLE_CHOICE]

GitHub Secrets duoc su dung de lam gi?

- [ ] Ma hoa source code
- [x] Luu tru thong tin nhay cam (API keys, passwords) an toan cho CI/CD
- [ ] An commit history
- [ ] Bao ve branches

> **Giải thích:** GitHub Secrets luu tru cac gia tri nhay cam (API keys, tokens, passwords) duoc ma hoa. Chung chi co the truy cap trong GitHub Actions workflows qua `${{ secrets.SECRET_NAME }}`. Secrets KHONG hien thi trong logs va KHONG the xem lai gia tri.

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Blue-Green Deployment la gi?

- [ ] Deploy mau xanh truoc, mau xanh la sau
- [x] Chay 2 moi truong giong nhau, chuyen traffic giua chung khi deploy
- [ ] Deploy 2 lan moi release
- [ ] Chi deploy vao ban dem

> **Giải thích:** Blue-Green: 2 moi truong production giong nhau (Blue va Green). Mot cai dang serve traffic, cai kia idle. Deploy len moi truong idle, test, roi chuyen traffic. Rollback nhanh: chi can chuyen traffic ve moi truong cu.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

Canary Deployment la gi?

- [ ] Deploy len toan bo server cung luc
- [ ] Deploy chi cho team noi bo
- [x] Deploy len mot phan nho users truoc, tang dan neu khong co loi
- [ ] Deploy vao ban dem khi it traffic

> **Giải thích:** Canary: deploy version moi cho 1-10% users truoc (canary group). Monitor loi va performance. Neu OK, tang dan (10% → 25% → 50% → 100%). Neu co loi, rollback chi anh huong phan nho. Ten "canary" tu chim hoang yen trong ham mo (canh bao som).

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Matrix strategy trong GitHub Actions dung de lam gi?

- [ ] Ma hoa build output
- [x] Chay cung pipeline voi nhieu cau hinh khac nhau (VD: nhieu Node versions, nhieu OS)
- [ ] Tao nhieu repositories
- [ ] Chia code thanh modules

> **Giải thích:** Matrix strategy chay cung job voi nhieu to hop cau hinh. Vi du: test tren Node 18, 20, 22 x Ubuntu, Windows = 6 jobs song song. Dam bao ung dung hoat dong tren nhieu moi truong ma khong can viet nhieu workflows.

## Câu 9

[TYPE: MULTIPLE_CHOICE]

Tại sao nen dat lint/type-check TRUOC unit tests trong pipeline?

- [ ] Lint nhanh hon nen dat truoc cho dep
- [x] Lint nhanh, fail som - khong can chay tests nau lon neu code format sai
- [ ] Lint phai chay truoc vi tests phu thuoc vao no
- [ ] Khong co ly do, thu tu khong quan trong

> **Giải thích:** Nguyen tac "Fail Fast": dat cac checks nhanh nhat truoc. Lint/format check chi mat vai giay. Neu fail, pipeline dung ngay ma khong mat thoi gian chay tests (co the mat vai phut). Tiet kiem thoi gian va tai nguyen CI runner.

## Câu 10

[TYPE: TRUE_FALSE]

Trong CI/CD, moi truong Staging nen giong Production nhat co the.

- [x] True
- [ ] False

> **Giải thích:** Environment Parity: Staging nen "mirror" Production (cung OS, cung dependencies, cau hinh tuong tu). Dieu nay dam bao test tren Staging phan anh chinh xac hanh vi tren Production. Su khac biet giua environments la nguyen nhan pho bien cua loi "works on staging but fails in production".

## Câu 11

[TYPE: MULTIPLE_CHOICE]

Artifact trong CI/CD la gi?

- [ ] Mot loai bug
- [x] File/folder output tu build process (VD: compiled code, reports, docker images)
- [ ] Secret key
- [ ] Ten cua CI server

> **Giải thích:** Artifacts la cac file duoc tao ra trong qua trinh build: compiled code (dist/), test reports, coverage reports, Docker images, deployment packages. Chung duoc luu tru va truyen giua cac jobs trong pipeline (vd: job build tao artifact, job deploy su dung artifact do).

## Câu 12

[TYPE: MULTIPLE_CHOICE]

Infrastructure as Code (IaC) co loi ich gi cho CI/CD?

- [ ] Lam code chay nhanh hon
- [ ] Thay the CI/CD pipeline
- [x] Quan ly infrastructure bang code, co the version control va tu dong hoa
- [ ] Chi dung cho frontend

> **Giải thích:** IaC (Terraform, CloudFormation, Pulumi) dinh nghia infrastructure bang code thay vi cau hinh thu cong. Loi ich: version control (track changes), reproducibility (tao lai moi truong giong nhau), automation (tich hop vao CI/CD pipeline), consistency (tranh "snowflake servers").
