# Quiz - Responsive Web Design

## Cau 1

[TYPE: MULTIPLE_CHOICE]

Meta tag viewport nao la CHUAN cho responsive web?

- [ ] `<meta name="viewport" content="width=1024">`
- [x] `<meta name="viewport" content="width=device-width, initial-scale=1.0">`
- [ ] `<meta name="viewport" content="width=device-width, user-scalable=no">`
- [ ] `<meta name="responsive" content="true">`

> **Giai thich:** `width=device-width` dat chieu rong viewport bang chieu rong thiet bi, `initial-scale=1.0` dat ty le zoom ban dau. Khong nen dung `user-scalable=no` vi no cam zoom, xau cho accessibility.

## Cau 2

[TYPE: MULTIPLE_CHOICE]

Mobile-First approach su dung media query nao?

- [ ] `@media (max-width: 768px)`
- [x] `@media (min-width: 768px)`
- [ ] `@media (width: 768px)`
- [ ] `@media screen`

> **Giai thich:** Mobile-First viet CSS cho mobile truoc (khong can media query), roi dung `min-width` de THEM style cho man hinh LON HON. Desktop-First dung `max-width`. Mobile-First duoc khuyen dung vi code gon hon va performance tot hon tren mobile.

## Cau 3

[TYPE: MULTIPLE_CHOICE]

Thuoc tinh CSS nao giup hinh anh tu dong co gian theo container?

- [ ] `width: 100%`
- [ ] `max-width: auto`
- [x] `max-width: 100%; height: auto;`
- [ ] `object-fit: contain`

> **Giai thich:** `max-width: 100%` dam bao hinh khong lon hon container. `height: auto` giu ty le goc. `width: 100%` se keo gian hinh lon hon kich thuoc goc. `object-fit` dung cho hinh trong container co kich thuoc co dinh.

## Cau 4

[TYPE: SELECT_RESULT]

Voi CSS Mobile-First sau, tren man hinh 900px rong, grid co bao nhieu cot?

```css
.grid { grid-template-columns: 1fr; }

@media (min-width: 768px) {
    .grid { grid-template-columns: repeat(2, 1fr); }
}

@media (min-width: 1024px) {
    .grid { grid-template-columns: repeat(3, 1fr); }
}
```

- [ ] 1 cot
- [x] 2 cot
- [ ] 3 cot
- [ ] 4 cot

> **Giai thich:** 900px > 768px nen media query `min-width: 768px` duoc kich hoat (2 cot). Nhung 900px < 1024px nen media query thu 2 KHONG duoc kich hoat. Ket qua: 2 cot.

## Cau 5

[TYPE: MULTIPLE_CHOICE]

`clamp(1rem, 2.5vw, 2rem)` co y nghia gi?

- [ ] Font luon la 2.5vw
- [ ] Font la 1rem tren mobile, 2rem tren desktop
- [x] Font responsive theo viewport, toi thieu 1rem, toi da 2rem
- [ ] Font tang tu 1rem den 2rem roi dung

> **Giai thich:** `clamp(min, preferred, max)` tra ve gia tri preferred (2.5vw) nhung khong duoi min (1rem) va khong qua max (2rem). Day la cach tao responsive typography khong can media queries.

## Cau 6

[TYPE: MULTIPLE_CHOICE]

Su khac biet giua `auto-fill` va `auto-fit` trong CSS Grid?

- [ ] Khong co su khac biet
- [x] `auto-fill` giu cac track rong, `auto-fit` co cac track hien co de lap day khong gian
- [ ] `auto-fill` chi dung voi minmax
- [ ] `auto-fit` nhanh hon `auto-fill`

> **Giai thich:** Khi so items it hon so cot co the: `auto-fill` tao cac track rong (co khong gian thua), `auto-fit` collapse cac track rong va gian cac item de lap day khong gian. Tren thuc te, su khac biet chi ro khi co it items.

## Cau 7

[TYPE: MULTIPLE_CHOICE]

Kich thuoc toi thieu cho touch target tren mobile la bao nhieu (theo WCAG)?

- [ ] 24x24px
- [ ] 32x32px
- [x] 44x44px
- [ ] 64x64px

> **Giai thich:** Theo Apple HIG va WCAG 2.1, touch target toi thieu la 44x44 CSS pixels. Google khuyen 48x48dp. Day dam bao nguoi dung co the bam chinh xac tren man hinh cam ung ma khong bi nham.

## Cau 8

[TYPE: MULTIPLE_CHOICE]

Container Queries khac Media Queries o diem nao?

- [ ] Container Queries nhanh hon
- [x] Container Queries dua tren kich thuoc parent container, Media Queries dua tren viewport
- [ ] Media Queries da bi thay the boi Container Queries
- [ ] Container Queries chi dung cho images

> **Giai thich:** Media Queries phan hoi theo kich thuoc **viewport** (man hinh). Container Queries phan hoi theo kich thuoc **parent container**. Dieu nay rat huu ich cho component-level responsive - mot component co the tu dieu chinh du o bat ky vi tri nao tren trang.

## Cau 9

[TYPE: MULTIPLE_CHOICE]

Cach nao tot nhat de phuc vu hinh anh khac nhau cho mobile va desktop?

- [ ] Dung JavaScript de doi src
- [ ] Dung CSS media queries voi background-image
- [x] Dung the `<picture>` voi `<source>` va media queries
- [ ] Dung mot hinh lon cho tat ca

> **Giai thich:** The `<picture>` voi `<source>` cho phep trinh duyet chon hinh phu hop TRUOC KHI tai. CSS background chi tai sau khi render. JavaScript doi src se tai hinh sai truoc roi moi doi. `<picture>` la giai phap chuan cho art direction responsive images.

## Cau 10

[TYPE: TRUE_FALSE]

Media query `@media (hover: hover)` dung de phat hien thiet bi co ho tro hover (chuot) hay khong.

- [x] True
- [ ] False

> **Giai thich:** `@media (hover: hover)` kiem tra thiet bi co primary input ho tro hover khong. Desktop voi chuot = hover: hover. Mobile cam ung = hover: none. Rat huu ich de chi ap dung hover effects tren desktop, tranh UX xau tren mobile.

## Cau 11

[TYPE: MULTIPLE_CHOICE]

Don vi `svh` (small viewport height) khac `vh` nhu the nao?

- [ ] `svh` nho hon `vh`
- [x] `svh` tinh viewport KHI thanh dia chi trinh duyet mobile hien thi (viewport nho nhat), `vh` co the thay doi
- [ ] `svh` la don vi cu, `vh` la don vi moi
- [ ] Khong co su khac biet

> **Giai thich:** Tren mobile, thanh dia chi trinh duyet co the an/hien khi scroll, lam thay doi chieu cao viewport. `vh` co the khong on dinh. `svh` (small viewport height) luon tinh theo viewport nho nhat (khi address bar hien). `lvh` (large) tinh khi address bar an. `dvh` (dynamic) thay doi theo trang thai hien tai.

## Cau 12

[TYPE: MULTIPLE_CHOICE]

Khi nao nen dung `aspect-ratio` trong responsive design?

- [ ] Chi dung cho video
- [ ] Chi dung tren desktop
- [x] Khi can giu ty le chieu rong/cao co dinh cho element responsive
- [ ] Thay the cho width va height

> **Giai thich:** `aspect-ratio` giu ty le khi element thay doi kich thuoc. Vi du: `aspect-ratio: 16/9` cho video container, `aspect-ratio: 1/1` cho hinh vuong. Element se tu tinh chieu cao dua tren chieu rong va ty le, rat huu ich cho responsive layout.
