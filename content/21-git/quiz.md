# Quiz - Git & GitHub

## Cau 1

[TYPE: MULTIPLE_CHOICE]

Git la loai Version Control System nao?

- [ ] Centralized (CVCS)
- [x] Distributed (DVCS)
- [ ] Local only
- [ ] Cloud-based

> **Giai thich:** Git la Distributed VCS - moi developer co ban sao day du cua repository (bao gom toan bo lich su). Khac voi Centralized VCS (SVN) chi co 1 server trung tam.

## Cau 2

[TYPE: MULTIPLE_CHOICE]

Lenh nao dua file tu Working Directory vao Staging Area?

- [ ] `git commit`
- [x] `git add`
- [ ] `git push`
- [ ] `git stage`

> **Giai thich:** `git add` chuyen file tu Working Directory vao Staging Area (Index). `git commit` luu Staging Area vao Repository. `git push` day commits len remote. `git stage` khong phai lenh Git chuan.

## Cau 3

[TYPE: MULTIPLE_CHOICE]

Su khac biet giua `git pull` va `git fetch`?

- [ ] Giong nhau
- [x] `git fetch` chi tai du lieu, `git pull` tai va tu dong merge
- [ ] `git pull` nhanh hon `git fetch`
- [ ] `git fetch` xoa branches cu

> **Giai thich:** `git fetch` tai thay doi tu remote nhung KHONG thay doi working directory. `git pull` = `git fetch` + `git merge` (hoac `git rebase` neu cau hinh). Nen dung `git fetch` truoc de xem thay doi, roi quyet dinh merge.

## Cau 4

[TYPE: MULTIPLE_CHOICE]

Khi nao xay ra merge conflict?

- [ ] Khi 2 nguoi commit cung luc
- [ ] Khi push ma remote co commit moi
- [x] Khi 2 branches sua cung 1 dong trong cung 1 file
- [ ] Khi xoa branch

> **Giai thich:** Conflict xay ra khi Git khong the tu dong gop 2 thay doi. Cu the: khi 2 branches thay doi cung mot dong (hoac vung) trong cung 1 file. Git se danh dau conflict va yeu cau developer giai quyet thu cong.

## Cau 5

[TYPE: MULTIPLE_CHOICE]

`git reset --soft HEAD~1` lam gi?

- [ ] Xoa commit cuoi va tat ca thay doi
- [x] Huy commit cuoi nhung GIU thay doi trong staging area
- [ ] Huy commit cuoi, thay doi ve working directory
- [ ] Tao commit moi dao nguoc

> **Giai thich:** `--soft` chi di chuyen HEAD lui 1 commit, giu tat ca thay doi trong staging area. `--mixed` (mac dinh) chuyen thay doi ve working directory. `--hard` xoa tat ca thay doi (NGUY HIEM). `git revert` moi tao commit dao nguoc.

## Cau 6

[TYPE: MULTIPLE_CHOICE]

Tai sao KHONG nen `git rebase` tren branch da push va co nguoi khac dang dung?

- [ ] Rebase cham hon merge
- [ ] Rebase khong hoat dong tren remote
- [x] Rebase thay doi lich su commits, gay conflict cho nguoi khac da pull branch cu
- [ ] Rebase xoa code

> **Giai thich:** Rebase rewrite commit history (tao commits moi voi hash khac). Neu nguoi khac da pull branch cu, khi ban push rebase, lich su se khong khop. Ho se gap conflict phuc tap khi pull. Quy tac: chi rebase branch CA NHAN, dung rebase branch CHIA SE.

## Cau 7

[TYPE: MULTIPLE_CHOICE]

`git stash` dung khi nao?

- [ ] Khi muon xoa tat ca thay doi
- [x] Khi can luu tam thay doi de chuyen sang viec khac
- [ ] Khi muon commit nhanh
- [ ] Khi muon push len remote

> **Giai thich:** `git stash` luu tam cac thay doi chua commit vao stack, lam sach working directory. Sau do ban co the chuyen branch, lam viec khac, roi `git stash pop` de lay lai thay doi. Rat huu ich khi dang code do ma can fix bug gap.

## Cau 8

[TYPE: MULTIPLE_CHOICE]

Conventional Commit message cho viec sua loi la gi?

- [ ] `bug: fix login error`
- [x] `fix: resolve login validation error`
- [ ] `fixed: login error`
- [ ] `bugfix(login): error`

> **Giai thich:** Conventional Commits dung format: `<type>: <description>`. Type cho sua loi la `fix`. Cac type khac: `feat` (tinh nang moi), `docs` (tai lieu), `style` (format), `refactor`, `test`, `chore`. Optional scope: `fix(auth): ...`

## Cau 9

[TYPE: MULTIPLE_CHOICE]

Lenh nao AN TOAN de hoan tac commit da push len remote?

- [ ] `git reset --hard`
- [ ] `git push --force`
- [x] `git revert`
- [ ] `git checkout`

> **Giai thich:** `git revert` tao commit MOI de dao nguoc thay doi, KHONG thay doi lich su. An toan cho shared branches. `git reset --hard` + `git push --force` rewrite history, gay van de cho nguoi khac. `git checkout` khong hoan tac commits.

## Cau 10

[TYPE: MULTIPLE_CHOICE]

`.gitignore` co tac dung gi?

- [ ] Xoa files khoi repository
- [ ] An files khoi nguoi dung
- [x] Bao Git bo qua (khong track) cac files/thu muc chi dinh
- [ ] Ma hoa files nhay cam

> **Giai thich:** `.gitignore` chi dinh cac file/folder ma Git se bo qua (khong add, khong commit). Thuong dung cho: `node_modules/`, `.env`, `dist/`, IDE files. Luu y: file DA DUOC tracked truoc do can `git rm --cached` truoc khi .gitignore co hieu luc.

## Cau 11

[TYPE: TRUE_FALSE]

`git cherry-pick` cho phep lay mot commit cu the tu branch khac ma khong can merge toan bo branch.

- [x] True
- [ ] False

> **Giai thich:** `git cherry-pick abc1234` sao chep chinh xac 1 commit (hoac nhieu commits) tu branch khac vao branch hien tai. Khong can merge toan bo branch. Huu ich khi chi can 1 fix cu the tu branch khac.

## Cau 12

[TYPE: MULTIPLE_CHOICE]

`git reflog` huu ich trong truong hop nao?

- [ ] Xem lich su commit cua remote
- [ ] Xem ai da sua file nao
- [x] Khoi phuc commits/branches da mat (vi reset, delete branch, ...)
- [ ] Xem log cua file cu the

> **Giai thich:** `git reflog` ghi lai MOI thay doi cua HEAD (commit, reset, checkout, rebase, ...). Day la "bao hiem cuoi cung" - ngay ca khi ban `reset --hard` hoac xoa branch, reflog van ghi lai va ban co the khoi phuc. Mac dinh luu 90 ngay.
