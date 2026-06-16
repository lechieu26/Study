# Git & GitHub - Bài Tập

## Bài 1: Git Cơ Bản
**Độ khó: Dễ**

Thực hành các lệnh Git cơ bản:

1. Tạo thư mục `my-project`, init Git repository
2. Tạo file `README.md` với nội dung "# My Project"
3. Tạo file `.gitignore` bỏ qua `node_modules/` và `.env`
4. Add và commit với message "feat: initial project setup"
5. Tạo file `index.html` và `style.css`
6. Add chỉ `index.html` vào staging (không add style.css)
7. Kiểm tra trạng thái (`git status`)
8. Commit `index.html` với message "feat: add homepage"
9. Xem lịch sử commits (`git log --oneline`)
10. Add và commit `style.css`

---

## Bài 2: Branching và Merging
**Độ khó: Trung bình**

Thực hành làm việc với branches:

1. Từ main, tạo branch `feature/navbar`
2. Trên `feature/navbar`: tạo file `navbar.html`, commit
3. Quay lại main, tạo branch `feature/footer`
4. Trên `feature/footer`: tạo file `footer.html`, commit
5. Merge `feature/navbar` vào main (fast-forward)
6. Merge `feature/footer` vào main (sẽ tạo merge commit)
7. Tạo branch `feature/hero`, sửa `index.html` dòng 1
8. Quay lại main, cùng sửa `index.html` dòng 1 (tạo conflict)
9. Merge `feature/hero` vào main - giải quyết conflict
10. Xem lịch sử với `git log --oneline --graph --all`

---

## Bài 3: Remote Workflow
**Độ khó: Trung bình**

Mô phỏng quy trình làm việc với GitHub:

1. Tạo repository mới trên GitHub
2. Kết nối local repo với remote (`git remote add origin`)
3. Push main lên remote
4. Tạo branch `feature/login`, thêm code, push lên remote
5. Trên GitHub, tạo Pull Request từ `feature/login` -> main
6. Mô phỏng code review: thêm 1 commit fix theo feedback
7. Push commit mới, merge PR trên GitHub
8. Pull main mới nhất về local
9. Xóa branch `feature/login` (local và remote)

---

## Bài 4: Rebase và History
**Độ khó: Khó**

Thực hành chỉnh sửa lịch sử Git:

1. Tạo 5 commits trên branch `feature/dashboard`:
   - "add dashboard layout"
   - "fix typo in dashboard"
   - "add charts component"
   - "wip: styling"
   - "finish dashboard styling"
2. Dùng interactive rebase (`git rebase -i HEAD~5`) để:
   - Squash "fix typo" vào commit đầu
   - Squash "wip: styling" và "finish dashboard styling" thành 1
   - Reword commit charts thành "feat: add interactive charts"
3. Kết quả: 3 commits sạch
4. Rebase `feature/dashboard` lên main mới nhất
5. Tạo PR và merge

---

## Bài 5: Git Rescue
**Độ khó: Khó**

Thực hành xử lý các tình huống "cứu hộ" Git:

1. **Hoàn tác commit chưa push:** Commit nhầm, dùng `git reset --soft` để giữ thay đổi
2. **Hoàn tác commit đã push:** Dùng `git revert` tạo commit mới
3. **Khôi phục file đã xóa:** Xóa file, commit, rồi khôi phục từ lịch sử
4. **Stash:** Đang code dở trên feature branch, cần chuyển sang hotfix
   - Stash thay đổi hiện tại
   - Chuyển sang `hotfix/urgent`, fix, commit, push
   - Quay lại feature branch, pop stash
5. **Cherry-pick:** Lấy 1 commit cụ thể từ branch khác
6. **Reflog:** Tìm và khôi phục branch đã xóa nhầm
