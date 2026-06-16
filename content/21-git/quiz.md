# Quiz - Git & GitHub

## Câu 1

[TYPE: MULTIPLE_CHOICE]

Git là loại Version Control System nào?

- [ ] Centralized (CVCS)
- [x] Distributed (DVCS)
- [ ] Local only
- [ ] Cloud-based

> **Giải thích:** Git là Distributed VCS - mỗi developer có bản sao đầy đủ của repository (bao gồm toàn bộ lịch sử). Khác với Centralized VCS (SVN) chỉ có 1 server trung tâm.

## Câu 2

[TYPE: MULTIPLE_CHOICE]

Lệnh nào đưa file từ Working Directory vào Staging Area?

- [ ] `git commit`
- [x] `git add`
- [ ] `git push`
- [ ] `git stage`

> **Giải thích:** `git add` chuyển file từ Working Directory vào Staging Area (Index). `git commit` lưu Staging Area vào Repository. `git push` đẩy commits lên remote. `git stage` không phải lệnh Git chuẩn.

## Câu 3

[TYPE: MULTIPLE_CHOICE]

Sự khác biệt giữa `git pull` và `git fetch`?

- [ ] Giống nhau
- [x] `git fetch` chỉ tải dữ liệu, `git pull` tải và tự động merge
- [ ] `git pull` nhanh hơn `git fetch`
- [ ] `git fetch` xóa branches cũ

> **Giải thích:** `git fetch` tải thay đổi từ remote nhưng KHÔNG thay đổi working directory. `git pull` = `git fetch` + `git merge` (hoặc `git rebase` nếu cấu hình). Nên dùng `git fetch` trước để xem thay đổi, rồi quyết định merge.

## Câu 4

[TYPE: MULTIPLE_CHOICE]

Khi nào xảy ra merge conflict?

- [ ] Khi 2 người commit cùng lúc
- [ ] Khi push mà remote có commit mới
- [x] Khi 2 branches sửa cùng 1 dòng trong cùng 1 file
- [ ] Khi xóa branch

> **Giải thích:** Conflict xảy ra khi Git không thể tự động gộp 2 thay đổi. Cụ thể: khi 2 branches thay đổi cùng một dòng (hoặc vùng) trong cùng 1 file. Git sẽ đánh dấu conflict và yêu cầu developer giải quyết thủ công.

## Câu 5

[TYPE: MULTIPLE_CHOICE]

`git reset --soft HEAD~1` làm gì?

- [ ] Xóa commit cuối và tất cả thay đổi
- [x] Hủy commit cuối nhưng GIỮ thay đổi trong staging area
- [ ] Hủy commit cuối, thay đổi về working directory
- [ ] Tạo commit mới đảo ngược

> **Giải thích:** `--soft` chỉ di chuyển HEAD lùi 1 commit, giữ tất cả thay đổi trong staging area. `--mixed` (mặc định) chuyển thay đổi về working directory. `--hard` xóa tất cả thay đổi (NGUY HIỂM). `git revert` mới tạo commit đảo ngược.

## Câu 6

[TYPE: MULTIPLE_CHOICE]

Tại sao KHÔNG nên `git rebase` trên branch đã push và có người khác đang dùng?

- [ ] Rebase chậm hơn merge
- [ ] Rebase không hoạt động trên remote
- [x] Rebase thay đổi lịch sử commits, gây conflict cho người khác đã pull branch cũ
- [ ] Rebase xóa code

> **Giải thích:** Rebase rewrite commit history (tạo commits mới với hash khác). Nếu người khác đã pull branch cũ, khi bạn push rebase, lịch sử sẽ không khớp. Họ sẽ gặp conflict phức tạp khi pull. Quy tắc: chỉ rebase branch CÁ NHÂN, đừng rebase branch CHIA SẺ.

## Câu 7

[TYPE: MULTIPLE_CHOICE]

`git stash` dùng khi nào?

- [ ] Khi muốn xóa tất cả thay đổi
- [x] Khi cần lưu tạm thay đổi để chuyển sang việc khác
- [ ] Khi muốn commit nhanh
- [ ] Khi muốn push lên remote

> **Giải thích:** `git stash` lưu tạm các thay đổi chưa commit vào stack, làm sạch working directory. Sau đó bạn có thể chuyển branch, làm việc khác, rồi `git stash pop` để lấy lại thay đổi. Rất hữu ích khi đang code dở mà cần fix bug gấp.

## Câu 8

[TYPE: MULTIPLE_CHOICE]

Conventional Commit message cho việc sửa lỗi là gì?

- [ ] `bug: fix login error`
- [x] `fix: resolve login validation error`
- [ ] `fixed: login error`
- [ ] `bugfix(login): error`

> **Giải thích:** Conventional Commits dùng format: `<type>: <description>`. Type cho sửa lỗi là `fix`. Các type khác: `feat` (tính năng mới), `docs` (tài liệu), `style` (format), `refactor`, `test`, `chore`. Optional scope: `fix(auth): ...`

## Câu 9

[TYPE: MULTIPLE_CHOICE]

Lệnh nào AN TOÀN để hoàn tác commit đã push lên remote?

- [ ] `git reset --hard`
- [ ] `git push --force`
- [x] `git revert`
- [ ] `git checkout`

> **Giải thích:** `git revert` tạo commit MỚI để đảo ngược thay đổi, KHÔNG thay đổi lịch sử. An toàn cho shared branches. `git reset --hard` + `git push --force` rewrite history, gây vấn đề cho người khác. `git checkout` không hoàn tác commits.

## Câu 10

[TYPE: MULTIPLE_CHOICE]

`.gitignore` có tác dụng gì?

- [ ] Xóa files khỏi repository
- [ ] Ẩn files khỏi người dùng
- [x] Bảo Git bỏ qua (không track) các files/thư mục chỉ định
- [ ] Mã hóa files nhạy cảm

> **Giải thích:** `.gitignore` chỉ định các file/folder mà Git sẽ bỏ qua (không add, không commit). Thường dùng cho: `node_modules/`, `.env`, `dist/`, IDE files. Lưu ý: file ĐÃ ĐƯỢC tracked trước đó cần `git rm --cached` trước khi .gitignore có hiệu lực.

## Câu 11

[TYPE: TRUE_FALSE]

`git cherry-pick` cho phép lấy một commit cụ thể từ branch khác mà không cần merge toàn bộ branch.

- [x] True
- [ ] False

> **Giải thích:** `git cherry-pick abc1234` sao chép chính xác 1 commit (hoặc nhiều commits) từ branch khác vào branch hiện tại. Không cần merge toàn bộ branch. Hữu ích khi chỉ cần 1 fix cụ thể từ branch khác.

## Câu 12

[TYPE: MULTIPLE_CHOICE]

`git reflog` hữu ích trong trường hợp nào?

- [ ] Xem lịch sử commit của remote
- [ ] Xem ai đã sửa file nào
- [x] Khôi phục commits/branches đã mất (vì reset, delete branch, ...)
- [ ] Xem log của file cụ thể

> **Giải thích:** `git reflog` ghi lại MỌI thay đổi của HEAD (commit, reset, checkout, rebase, ...). Đây là "bảo hiểm cuối cùng" - ngay cả khi bạn `reset --hard` hoặc xóa branch, reflog vẫn ghi lại và bạn có thể khôi phục. Mặc định lưu 90 ngày.
