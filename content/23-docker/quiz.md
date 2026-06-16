# Quiz - Docker

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Docker container khac Virtual Machine nhu the nao?

- [ ] Container chay tren hypervisor, VM chay tren OS
- [x] Container chia se kernel voi host OS, nhe va nhanh hon VM
- [ ] Khong co su khac biet
- [ ] VM nhe hon container

> **Giải thích:** Container chia se kernel cua host OS, chi dong goi app va dependencies → kich thuoc MB, khoi dong trong giay. VM co OS rieng (guest OS) chay tren hypervisor → kich thuoc GB, khoi dong trong phut. Container nhe va nhanh hon nhung isolation kem hon VM.

## Câu 2

[TYPE: MULTIPLE_CHOICE]

Trong Dockerfile, su khac biet giua `CMD` va `ENTRYPOINT`?

- [ ] Giong nhau
- [x] `CMD` co the bi override khi `docker run`, `ENTRYPOINT` khong bi override
- [ ] `CMD` chay khi build, `ENTRYPOINT` chay khi start
- [ ] Chi duoc dung 1 trong 2

> **Giải thích:** `CMD` dinh nghia lenh mac dinh, co the bi thay the khi `docker run myapp <new-command>`. `ENTRYPOINT` dinh nghia executable chinh, KHONG bi thay the (chi them tham so). Thuong ket hop: `ENTRYPOINT ["node"]` + `CMD ["app.js"]` → co the doi file chay nhung luon dung node.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Tai sao nen `COPY package.json` TRUOC `COPY . .` trong Dockerfile?

- [ ] Package.json nho hon nen copy nhanh
- [x] Tan dung Docker layer cache - chi rebuild dependencies khi package.json thay doi
- [ ] Docker yeu cau thu tu nay
- [ ] Khong co ly do, thu tu khong quan trong

> **Giải thích:** Docker cache moi layer. Neu COPY package.json truoc roi RUN npm install, khi chi thay doi source code (khong doi package.json), Docker se dung cache cho layer install dependencies → build NHANH hon nhieu. Neu COPY . . truoc, bat ky thay doi nao cung invalidate cache.

## Câu 4

[TYPE: MULTIPLE_CHOICE]

Multi-stage build giai quyet van de gi?

- [ ] Lam app chay nhanh hon
- [x] Giam kich thuoc image bang cach tach build tools khoi runtime
- [ ] Cho phep chay nhieu apps trong 1 container
- [ ] Tang bao mat bang cach ma hoa code

> **Giải thích:** Multi-stage build: stage 1 co build tools (compiler, npm dev deps) de build app. Stage 2 chi copy artifacts (compiled code) vao image nhe (alpine, distroless). Ket qua: image nho hon nhieu (VD: React app tu 1.2GB xuong 25MB).

## Câu 5

[TYPE: MULTIPLE_CHOICE]

`docker compose up -d` lam gi?

- [ ] Build images
- [ ] Xoa containers
- [x] Khoi dong tat ca services trong background (detached mode)
- [ ] Hien thi logs

> **Giải thích:** `docker compose up` khoi dong tat ca services dinh nghia trong docker-compose.yml. Flag `-d` (detached) chay trong background, tra lai terminal. Khong co `-d`, logs se hien thi va Ctrl+C dung tat ca services.

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Named volume trong Docker dung de lam gi?

- [ ] Tang toc do container
- [ ] Chia se code giua containers
- [x] Luu tru data persistent (khong mat khi xoa container)
- [ ] Luu tru Docker images

> **Giải thích:** Container la ephemeral - data trong container MAT khi xoa container. Named volumes luu data BEN NGOAI container lifecycle. Du xoa va tao lai container, data trong volume VAN CON. Thuong dung cho database data, upload files.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

`depends_on` voi `condition: service_healthy` co tac dung gi?

- [ ] Tu dong restart service neu fail
- [x] Chi start service SAU KHI service phu thuoc HEALTHY (pass healthcheck)
- [ ] Ket noi 2 services vao cung network
- [ ] Chia se volumes giua services

> **Giải thích:** `depends_on` voi `condition: service_healthy` dam bao service chi start khi dependency da HEALTHY (khong chi started). Vi du: backend doi DB pass healthcheck (`pg_isready`) truoc khi start. Khong co condition, backend co the start khi DB chua san sang → connection error.

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Tai sao nen dung non-root user trong container?

- [ ] Container chay nhanh hon
- [ ] Docker yeu cau bat buoc
- [x] Giam rui ro bao mat - neu container bi compromise, attacker khong co quyen root
- [ ] Giam kich thuoc image

> **Giải thích:** Mac dinh container chay voi root. Neu ung dung bi exploit, attacker co quyen root TRONG container va co the escape ra host (container escape vulnerabilities). Chay voi non-root user (vd: `USER node`) gioi han quyen, giam thiet hai khi bi tan cong.

## Câu 9

[TYPE: SELECT_RESULT]

Voi Docker Compose sau, frontend co the truy cap database truc tiep khong?

```yaml
services:
  frontend:
    networks: [public]
  backend:
    networks: [public, private]
  database:
    networks: [private]

networks:
  public:
  private:
```

- [ ] Co
- [x] Khong
- [ ] Tuy thuoc vao port mapping
- [ ] Chi khi dung IP truc tiep

> **Giải thích:** Frontend chi o network `public`, database chi o network `private`. Chung KHONG co network chung nen KHONG the giao tiep. Backend o CA HAI networks nen la "bridge" giua frontend va database. Day la cach co lap network dung trong microservices.

## Câu 10

[TYPE: MULTIPLE_CHOICE]

`docker image prune -a` lam gi?

- [ ] Xoa tat ca containers
- [ ] Xoa tat ca volumes
- [x] Xoa tat ca images khong duoc container nao su dung
- [ ] Xoa tat ca networks

> **Giải thích:** `docker image prune` xoa dangling images (images khong co tag). Them `-a` xoa TAT CA images khong duoc container nao dang su dung. Huu ich de giai phong dung luong disk. Chu y: images dang duoc container dung (ke ca stopped) se KHONG bi xoa.

## Câu 11

[TYPE: TRUE_FALSE]

`EXPOSE 3000` trong Dockerfile tu dong publish port 3000 ra ngoai host.

- [ ] True
- [x] False

> **Giải thích:** `EXPOSE` chi la DOCUMENTATION - ghi lai port nao container lang nghe. No KHONG publish port ra host. De publish, can `-p 3000:3000` khi `docker run` hoac `ports: ["3000:3000"]` trong docker-compose.yml. `EXPOSE` giup developer va tools (nhu Docker Desktop) biet port nao nen map.

## Câu 12

[TYPE: MULTIPLE_CHOICE]

Cach nao tot nhat de truyen secrets (passwords, API keys) vao Docker container trong production?

- [ ] Hardcode trong Dockerfile
- [ ] Dung ENV trong Dockerfile
- [ ] Dung .env file commit vao Git
- [x] Dung Docker secrets hoac environment variables truyen luc runtime (khong commit vao code)

> **Giải thích:** KHONG BAO GIO hardcode secrets trong Dockerfile (secrets nam trong image layers, ai pull image deu thay). Cach tot: Docker secrets (Swarm/Compose), environment variables truyen luc runtime (`docker run -e`), hoac secret managers (AWS Secrets Manager, Vault). File `.env` KHONG commit vao Git.
