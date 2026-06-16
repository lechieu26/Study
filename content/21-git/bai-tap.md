# Git & GitHub - Bai Tap

## Bai 1: Git Co Ban
**Do kho: De**

Thuc hanh cac lenh Git co ban:

1. Tao thu muc `my-project`, init Git repository
2. Tao file `README.md` voi noi dung "# My Project"
3. Tao file `.gitignore` bo qua `node_modules/` va `.env`
4. Add va commit voi message "feat: initial project setup"
5. Tao file `index.html` va `style.css`
6. Add chi `index.html` vao staging (khong add style.css)
7. Kiem tra trang thai (`git status`)
8. Commit `index.html` voi message "feat: add homepage"
9. Xem lich su commits (`git log --oneline`)
10. Add va commit `style.css`

---

## Bai 2: Branching va Merging
**Do kho: Trung binh**

Thuc hanh lam viec voi branches:

1. Tu main, tao branch `feature/navbar`
2. Tren `feature/navbar`: tao file `navbar.html`, commit
3. Quay lai main, tao branch `feature/footer`
4. Tren `feature/footer`: tao file `footer.html`, commit
5. Merge `feature/navbar` vao main (fast-forward)
6. Merge `feature/footer` vao main (se tao merge commit)
7. Tao branch `feature/hero`, sua `index.html` dong 1
8. Quay lai main, cung sua `index.html` dong 1 (tao conflict)
9. Merge `feature/hero` vao main - giai quyet conflict
10. Xem lich su voi `git log --oneline --graph --all`

---

## Bai 3: Remote Workflow
**Do kho: Trung binh**

Mo phong quy trinh lam viec voi GitHub:

1. Tao repository moi tren GitHub
2. Ket noi local repo voi remote (`git remote add origin`)
3. Push main len remote
4. Tao branch `feature/login`, them code, push len remote
5. Tren GitHub, tao Pull Request tu `feature/login` -> main
6. Mo phong code review: them 1 commit fix theo feedback
7. Push commit moi, merge PR tren GitHub
8. Pull main moi nhat ve local
9. Xoa branch `feature/login` (local va remote)

---

## Bai 4: Rebase va History
**Do kho: Kho**

Thuc hanh chinh sua lich su Git:

1. Tao 5 commits tren branch `feature/dashboard`:
   - "add dashboard layout"
   - "fix typo in dashboard"
   - "add charts component"
   - "wip: styling"
   - "finish dashboard styling"
2. Dung interactive rebase (`git rebase -i HEAD~5`) de:
   - Squash "fix typo" vao commit dau
   - Squash "wip: styling" va "finish dashboard styling" thanh 1
   - Reword commit charts thanh "feat: add interactive charts"
3. Ket qua: 3 commits sach
4. Rebase `feature/dashboard` len main moi nhat
5. Tao PR va merge

---

## Bai 5: Git Rescue
**Do kho: Kho**

Thuc hanh xu ly cac tinh huong "cuu ho" Git:

1. **Hoan tac commit chua push:** Commit nham, dung `git reset --soft` de giu thay doi
2. **Hoan tac commit da push:** Dung `git revert` tao commit moi
3. **Khoi phuc file da xoa:** Xoa file, commit, roi khoi phuc tu lich su
4. **Stash:** Dang code do tren feature branch, can chuyen sang hotfix
   - Stash thay doi hien tai
   - Chuyen sang `hotfix/urgent`, fix, commit, push
   - Quay lai feature branch, pop stash
5. **Cherry-pick:** Lay 1 commit cu the tu branch khac
6. **Reflog:** Tim va khoi phuc branch da xoa nham
