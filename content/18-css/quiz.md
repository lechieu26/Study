# Quiz - CSS

## Cau 1

[TYPE: MULTIPLE_CHOICE]

CSS la viet tat cua gi?

- [ ] Computer Style Sheets
- [x] Cascading Style Sheets
- [ ] Colorful Style Sheets
- [ ] Creative Style Sheets

> **Giai thich:** CSS = Cascading Style Sheets. "Cascading" (thac do) mo ta cach cac quy tac CSS duoc ap dung theo thu tu uu tien (specificity, source order).

## Cau 2

[TYPE: SELECT_RESULT]

Voi CSS sau, mau cua van ban la gi?

```css
p { color: red; }
p { color: blue; }
.text { color: green; }
```

```html
<p class="text">Hello</p>
```

- [ ] red
- [ ] blue
- [x] green
- [ ] black

> **Giai thich:** Class selector `.text` co specificity (0-1-0) cao hon element selector `p` (0-0-1). Do do `.text { color: green }` thang, bat ke thu tu khai bao.

## Cau 3

[TYPE: MULTIPLE_CHOICE]

`box-sizing: border-box` co tac dung gi?

- [ ] Them border cho element
- [ ] Xoa padding cua element
- [x] Width/height bao gom ca padding va border
- [ ] Chi ap dung cho block elements

> **Giai thich:** Voi `border-box`, width va height tinh luon padding va border. Vi du: `width: 300px` thi tong chieu rong la 300px (bao gom content + padding + border). Voi `content-box` (mac dinh), 300px chi la content, padding va border cong them.

## Cau 4

[TYPE: MULTIPLE_CHOICE]

Dau la cach CAN GIUA mot element ca ngang va doc voi Flexbox?

- [ ] `display: flex; align-items: center;`
- [ ] `display: flex; justify-content: center;`
- [x] `display: flex; justify-content: center; align-items: center;`
- [ ] `display: flex; text-align: center;`

> **Giai thich:** `justify-content: center` can giua theo truc chinh (main axis, mac dinh la ngang). `align-items: center` can giua theo truc phu (cross axis, mac dinh la doc). Can ca hai de can giua hoan toan.

## Cau 5

[TYPE: MULTIPLE_CHOICE]

Su khac biet giua `display: none` va `visibility: hidden`?

- [x] `display: none` xoa khoi layout, `visibility: hidden` van chiem khong gian
- [ ] Khong co su khac biet
- [ ] `visibility: hidden` xoa khoi layout, `display: none` van chiem khong gian
- [ ] `display: none` chi an tren mobile

> **Giai thich:** `display: none` xoa element khoi document flow, khong chiem khong gian. `visibility: hidden` an element nhung VAN giu cho trong layout. `opacity: 0` cung an nhung van chiem cho va co the tuong tac (click).

## Cau 6

[TYPE: MULTIPLE_CHOICE]

`position: sticky` hoat dong nhu the nao?

- [ ] Giong `position: fixed`
- [ ] Giong `position: absolute`
- [x] Hoat dong nhu `relative` cho den khi scroll den nguong, roi chuyen thanh `fixed`
- [ ] Chi hoat dong trong Flexbox

> **Giai thich:** `position: sticky` ket hop relative va fixed. Element o vi tri binh thuong (relative) cho den khi nguoi dung scroll den nguong (vd: `top: 0`), luc do no "dinh" lai nhu fixed. Can co `top/bottom/left/right` va parent khong co `overflow: hidden`.

## Cau 7

[TYPE: MULTIPLE_CHOICE]

CSS Specificity dung nhat la gi?

- [ ] ID > Class > Inline style > Element
- [x] !important > Inline style > ID > Class > Element
- [ ] Class > ID > Element > Inline style
- [ ] Element > Class > ID > Inline style

> **Giai thich:** Thu tu uu tien: `!important` (cao nhat) > Inline style (thuoc tinh style="") > ID selector (#id) > Class/Attribute/Pseudo-class (.class, [attr], :hover) > Element/Pseudo-element (p, ::before). Khi cung specificity, khai bao SAU se thang.

## Cau 8

[TYPE: MULTIPLE_CHOICE]

Khi nao nen dung CSS Grid thay vi Flexbox?

- [ ] Khi can layout 1 chieu
- [x] Khi can layout 2 chieu (dong va cot cung luc)
- [ ] Khi can animation
- [ ] Grid da thay the hoan toan Flexbox

> **Giai thich:** CSS Grid ly tuong cho layout 2 chieu (dinh nghia ca dong va cot). Flexbox tot cho layout 1 chieu (row HOAC column). Trong thuc te, chung thuong duoc ket hop: Grid cho page layout, Flexbox cho component layout.

## Cau 9

[TYPE: SELECT_RESULT]

Gia tri cua `--size` trong element `.child` la gi?

```css
:root { --size: 16px; }
.parent { --size: 20px; }
.child { font-size: var(--size); }
```

```html
<div class="parent">
    <div class="child">Text</div>
</div>
```

- [ ] 16px
- [x] 20px
- [ ] 0px
- [ ] inherit

> **Giai thich:** CSS Variables ke thua theo DOM tree. `.child` nam trong `.parent`, nen `--size` duoc ke thua tu `.parent` (20px) thay vi `:root` (16px). Gia tri gan nhat trong scope chain se duoc su dung.

## Cau 10

[TYPE: MULTIPLE_CHOICE]

`clamp(14px, 2.5vw, 22px)` co y nghia gi?

- [ ] Font size luon la 2.5vw
- [ ] Font size la 14px hoac 22px
- [x] Font size la 2.5vw nhung toi thieu 14px va toi da 22px
- [ ] Font size tang tu 14px den 22px roi dung

> **Giai thich:** `clamp(min, preferred, max)` tra ve gia tri preferred (2.5vw) nhung dam bao khong nho hon min (14px) va khong lon hon max (22px). Day la cach tuyet voi de tao responsive typography ma khong can media queries.

## Cau 11

[TYPE: MULTIPLE_CHOICE]

Pseudo-element nao dung de them noi dung TRUOC mot element?

- [ ] `:before`
- [x] `::before`
- [ ] `:first-child`
- [ ] `::first-line`

> **Giai thich:** `::before` (2 dau hai cham) la pseudo-element them noi dung truoc element. CSS3 quy dinh pseudo-elements dung `::` (2 dau) de phan biet voi pseudo-classes dung `:` (1 dau). Can co `content` property de hoat dong.

## Cau 12

[TYPE: MULTIPLE_CHOICE]

Tai sao nen dung `rem` thay vi `px` cho font-size?

- [ ] `rem` nhanh hon `px`
- [ ] `rem` dep hon `px`
- [x] `rem` tuong doi voi root font-size, giup accessibility tot hon (nguoi dung co the thay doi font-size trinh duyet)
- [ ] `px` da bi loai bo trong CSS3

> **Giai thich:** `rem` (root em) tuong doi voi font-size cua `<html>`. Khi nguoi dung thay doi font-size mac dinh cua trinh duyet (vi du nguoi kem mat tang len 20px), tat ca noi dung dung `rem` se tu dong dieu chinh. `px` co dinh nen khong phan hoi thay doi nay.

## Cau 13

[TYPE: TRUE_FALSE]

Margin cua hai block elements ke nhau se cong lai (vi du: margin-bottom 20px + margin-top 30px = 50px).

- [ ] True
- [x] False

> **Giai thich:** Day la Margin Collapsing. Margin doc (top/bottom) cua hai block elements ke nhau se gop lai, lay gia tri LON HON. Voi margin-bottom 20px + margin-top 30px, khoang cach thuc te la 30px (khong phai 50px). Margin ngang (left/right) KHONG collapse.

## Cau 14

[TYPE: MULTIPLE_CHOICE]

`z-index` chi hoat dong khi element co thuoc tinh nao?

- [x] `position` khac `static` (hoac trong flex/grid container)
- [ ] `display: block`
- [ ] `overflow: hidden`
- [ ] `z-index` luon hoat dong

> **Giai thich:** `z-index` chi co tac dung tren elements co `position: relative/absolute/fixed/sticky` hoac la flex/grid items. Tren element `position: static` (mac dinh), `z-index` bi bo qua.

## Cau 15

[TYPE: MULTIPLE_CHOICE]

Cach tot nhat de an noi dung nhung van cho screen reader doc duoc?

- [ ] `display: none`
- [ ] `visibility: hidden`
- [ ] `opacity: 0`
- [x] `position: absolute; width: 1px; height: 1px; clip: rect(0,0,0,0); overflow: hidden;`

> **Giai thich:** `display: none` va `visibility: hidden` deu an khoi screen reader. `opacity: 0` van co the bi click. Ky thuat "visually hidden" (hoac sr-only) dung position absolute voi clip de an thi giac nhung van duoc screen reader doc. Day la pattern pho bien trong accessibility.
