# Quiz - HTML

## Câu 1

[TYPE: MULTIPLE_CHOICE]

HTML la viet tat cua gi?

- [ ] Hyper Transfer Markup Language
- [x] HyperText Markup Language
- [ ] High Tech Modern Language
- [ ] HyperText Machine Language

> **Giải thích:** HTML = HyperText Markup Language (Ngon ngu Danh dau Sieu van ban). Day la ngon ngu danh dau dung de tao cau truc noi dung trang web.

## Câu 2

[TYPE: MULTIPLE_CHOICE]

The HTML nao dung de tao tieu de lon nhat?

- [x] `<h1>`
- [ ] `<heading>`
- [ ] `<head>`
- [ ] `<title>`

> **Giải thích:** `<h1>` la the heading lon nhat (h1 → h6). `<head>` chua metadata, `<title>` la tieu de tab trinh duyet, `<heading>` khong ton tai trong HTML.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Thuoc tinh nao bat buoc phai co trong the `<img>`?

- [ ] `src`
- [ ] `alt`
- [x] Ca `src` va `alt`
- [ ] `width` va `height`

> **Giải thích:** Theo chuan HTML, `<img>` can ca `src` (duong dan anh) va `alt` (mo ta thay the khi anh khong tai duoc, quan trong cho accessibility va SEO).

## Câu 4

[TYPE: MULTIPLE_CHOICE]

The nao la the semantic HTML?

- [ ] `<div>`
- [ ] `<span>`
- [x] `<article>`
- [ ] `<b>`

> **Giải thích:** `<article>` la the semantic - no truyen dat y nghia (noi dung doc lap). `<div>` va `<span>` la the non-semantic (chi la container). `<b>` chi la dinh dang hinh thuc.

## Câu 5

[TYPE: MULTIPLE_CHOICE]

Doan code nao tao lien ket mo tab moi AN TOAN?

- [ ] `<a href="url" target="_blank">Link</a>`
- [x] `<a href="url" target="_blank" rel="noopener noreferrer">Link</a>`
- [ ] `<a href="url" new-tab="true">Link</a>`
- [ ] `<link href="url" target="_blank" />`

> **Giải thích:** Khi dung `target="_blank"`, can them `rel="noopener noreferrer"` de ngan trang moi truy cap `window.opener` (lo hong bao mat). `<link>` dung trong `<head>` cho stylesheet, khong phai hyperlink.

## Câu 6

[TYPE: MULTIPLE_CHOICE]

The `<meta name="viewport" content="width=device-width, initial-scale=1.0">` co tac dung gi?

- [ ] Tang toc do tai trang
- [ ] Them SEO cho trang
- [x] Dieu chinh viewport cho responsive tren mobile
- [ ] Thay doi charset cua trang

> **Giải thích:** Viewport meta tag bao trinh duyet mobile dieu chinh chieu rong trang bang chieu rong thiet bi va scale ban dau la 1.0. Khong co the nay, trang se hien thi nhu tren desktop va bi thu nho tren mobile.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

Su khac biet giua `<strong>` va `<b>` la gi?

- [ ] Khong co su khac biet
- [ ] `<strong>` in dam hon `<b>`
- [x] `<strong>` co y nghia ngu nghia (quan trong), `<b>` chi dinh dang hinh thuc
- [ ] `<b>` da bi loai bo trong HTML5

> **Giải thích:** `<strong>` mang y nghia ngu nghia - noi dung quan trong, screen reader se nhan manh khi doc. `<b>` chi lam in dam ve hinh thuc (visual), khong mang y nghia gi cho may tinh/screen reader.

## Câu 8

[TYPE: SELECT_RESULT]

Doan code HTML sau se render nhu the nao?

```html
<ol start="3" reversed>
    <li>Muc A</li>
    <li>Muc B</li>
    <li>Muc C</li>
</ol>
```

- [ ] 3. Muc A, 4. Muc B, 5. Muc C
- [ ] 1. Muc A, 2. Muc B, 3. Muc C
- [x] 3. Muc A, 2. Muc B, 1. Muc C
- [ ] C. Muc A, B. Muc B, A. Muc C

> **Giải thích:** `start="3"` bat dau tu so 3, `reversed` dao nguoc thu tu dem. Nen ket qua la 3, 2, 1.

## Câu 9

[TYPE: MULTIPLE_CHOICE]

Input type nao KHONG ton tai trong HTML5?

- [ ] `<input type="email">`
- [ ] `<input type="date">`
- [x] `<input type="timestamp">`
- [ ] `<input type="range">`

> **Giải thích:** HTML5 co cac input type: text, email, password, number, date, time, datetime-local, range, color, tel, url, search, file, ... Nhung KHONG co `type="timestamp"`. De nhap ngay gio, dung `type="datetime-local"`.

## Câu 10

[TYPE: MULTIPLE_CHOICE]

Cach dung nhat de nhom cac truong form lien quan?

- [ ] `<div class="group">`
- [ ] `<section>`
- [x] `<fieldset>` voi `<legend>`
- [ ] `<group>`

> **Giải thích:** `<fieldset>` va `<legend>` la the chuan de nhom cac form controls lien quan. `<fieldset>` tao viien bao quanh, `<legend>` la tieu de cua nhom. Day la cach tot nhat cho accessibility.

## Câu 11

[TYPE: MULTIPLE_CHOICE]

The nao dung de nhung video YouTube vao trang web?

- [ ] `<video src="youtube-url">`
- [ ] `<embed src="youtube-url">`
- [x] `<iframe src="youtube-embed-url">`
- [ ] `<object data="youtube-url">`

> **Giải thích:** YouTube cung cap embed URL dang `https://www.youtube.com/embed/VIDEO_ID` de nhung qua `<iframe>`. The `<video>` chi dung cho file video truc tiep, khong phai URL streaming.

## Câu 12

[TYPE: TRUE_FALSE]

`localStorage` va `sessionStorage` deu mat du lieu khi dong trinh duyet.

- [ ] True
- [x] False

> **Giải thích:** `sessionStorage` mat khi dong tab/cua so. Nhung `localStorage` luu tru VINH VIEN cho den khi bi xoa bang code hoac user tu xoa. Du dong trinh duyet, `localStorage` van con.

## Câu 13

[TYPE: MULTIPLE_CHOICE]

Thuoc tinh `loading="lazy"` tren the `<img>` co tac dung gi?

- [ ] Giam kich thuoc hinh anh
- [ ] Lam mo hinh anh khi tai
- [x] Chi tai hinh anh khi no xuat hien gan viewport
- [ ] Tai hinh anh tu server khac

> **Giải thích:** `loading="lazy"` la native lazy loading - trinh duyet chi bat dau tai hinh anh khi nguoi dung cuon gan den vi tri cua no. Giup tang toc do tai trang ban dau vi khong tai tat ca hinh cung luc.

## Câu 14

[TYPE: MULTIPLE_CHOICE]

Dau la cach dung nhat de them alt text cho hinh anh trang tri (khong mang noi dung)?

- [ ] Khong them thuoc tinh `alt`
- [ ] `alt="hinh trang tri"`
- [x] `alt=""` (alt rong)
- [ ] `alt="image"`

> **Giải thích:** Voi hinh anh trang tri (khong mang thong tin), dung `alt=""` (chuoi rong). Screen reader se bo qua hinh nay. Neu khong co `alt` thi screen reader se doc ten file, gay kho chiu. `alt="hinh trang tri"` khong cung cap thong tin huu ich.

## Câu 15

[TYPE: MULTIPLE_CHOICE]

Su khac biet giua `defer` va `async` trong the `<script>` la gi?

- [ ] Khong co su khac biet
- [ ] `defer` tai nhanh hon `async`
- [x] `defer` chay sau khi DOM parsed xong, `async` chay ngay khi tai xong
- [ ] `async` chi dung cho module scripts

> **Giải thích:** Ca hai deu tai script song song voi HTML parsing. Nhung `defer` dam bao script chay THEO THU TU va SAU KHI DOM parsed xong. `async` chay NGAY khi tai xong (khong dam bao thu tu). Dung `defer` cho app scripts, `async` cho analytics/ads.
