# Git & GitHub - Đáp Án Bài Tập

## Bài 1: Git Cơ Bản

```bash
# 1. Tạo thư mục và init
mkdir my-project && cd my-project
git init

# 2. Tạo README.md
echo "# My Project" > README.md

# 3. Tạo .gitignore
cat > .gitignore << EOF
node_modules/
.env
.env.local
dist/
.DS_Store
EOF

# 4. Commit đầu tiên
git add README.md .gitignore
git commit -m "feat: initial project setup"

# 5. Tạo các file
echo '<!DOCTYPE html><html><body><h1>Hello</h1></body></html>' > index.html
echo 'body { margin: 0; font-family: sans-serif; }' > style.css

# 6. Chỉ add index.html
git add index.html

# 7. Kiểm tra trạng thái
git status
# On branch main
# Changes to be committed:
#   new file:   index.html
# Untracked files:
#   style.css

# 8. Commit index.html
git commit -m "feat: add homepage"

# 9. Xem lịch sử
git log --oneline
# abc1234 feat: add homepage
# def5678 feat: initial project setup

# 10. Add và commit style.css
git add style.css
git commit -m "feat: add base styles"
```

---

## Bài 2: Branching và Merging

```bash
# 1. Tạo branch feature/navbar
git checkout -b feature/navbar

# 2. Tạo và commit navbar
echo '<nav>Navigation</nav>' > navbar.html
git add navbar.html
git commit -m "feat: add navigation bar"

# 3. Quay lại main, tạo feature/footer
git checkout main
git checkout -b feature/footer

# 4. Tạo và commit footer
echo '<footer>Footer</footer>' > footer.html
git add footer.html
git commit -m "feat: add footer"

# 5. Merge navbar vào main (fast-forward)
git checkout main
git merge feature/navbar
# Fast-forward merge (vì main chưa có commit mới)

# 6. Merge footer vào main (merge commit)
git merge --no-ff feature/footer -m "Merge feature/footer into main"

# 7. Tạo feature/hero, sửa index.html
git checkout -b feature/hero
# Sửa dòng 1 của index.html thành: <!DOCTYPE html><html><body><h1>HERO</h1></body></html>
sed -i 's/Hello/HERO/' index.html
git add index.html
git commit -m "feat: add hero section"

# 8. Quay lại main, tạo conflict
git checkout main
sed -i 's/Hello/Welcome/' index.html
git add index.html
git commit -m "feat: update welcome message"

# 9. Merge - sẽ có conflict
git merge feature/hero
# CONFLICT (content): Merge conflict in index.html

# Sửa file index.html - giữ nội dung mong muốn
# Ví dụ: giữ cả hai -> "HERO Welcome"
# Xóa các marker <<<<<<< ======= >>>>>>>

git add index.html
git commit -m "Merge feature/hero, resolve index.html conflict"

# 10. Xem lịch sử
git log --oneline --graph --all
```

---

## Bài 3: Remote Workflow

```bash
# 1. Tạo repo trên GitHub (qua web)

# 2. Kết nối remote
git remote add origin https://github.com/username/my-project.git

# 3. Push main
git push -u origin main

# 4. Tạo feature branch và push
git checkout -b feature/login
echo '<form><input type="email"/><input type="password"/></form>' > login.html
git add login.html
git commit -m "feat: add login form"
git push -u origin feature/login

# 5. Tạo PR trên GitHub (qua giao diện web)
# Title: feat: add login functionality
# Description: Thêm form đăng nhập với email và password

# 6. Sửa theo feedback
echo 'Updated login with validation' >> login.html
git add login.html
git commit -m "fix: add form validation per review feedback"

# 7. Push và merge
git push
# Merge PR trên GitHub

# 8. Pull main mới nhất
git checkout main
git pull origin main

# 9. Xóa branch
git branch -d feature/login          # Local
git push origin --delete feature/login  # Remote
```

---

## Bài 4: Rebase và History

```bash
# Tạo 5 commits
git checkout -b feature/dashboard

echo '<div class="dashbord">Dashboard</div>' > dashboard.html
git add dashboard.html
git commit -m "add dashboard layout"

# Sửa lỗi chính tả (typo)
sed -i 's/dashbord/dashboard/' dashboard.html
git add dashboard.html
git commit -m "fix typo in dashboard"

echo '<div class="chart">Chart</div>' > chart.html
git add chart.html
git commit -m "add charts component"

echo '.dashboard { padding: 20px; }' > dashboard.css
git add dashboard.css
git commit -m "wip: styling"

echo '.dashboard { padding: 20px; background: #f5f5f5; border-radius: 8px; }' > dashboard.css
git add dashboard.css
git commit -m "finish dashboard styling"

# Interactive rebase
git rebase -i HEAD~5

# Trong editor, thay đổi thành:
# pick abc1234 add dashboard layout
# fixup def5678 fix typo in dashboard        <- gộp vào commit trên, bỏ message
# reword ghi9012 add charts component        <- đổi message
# pick jkl3456 wip: styling
# fixup mno7890 finish dashboard styling     <- gộp vào commit trên

# Khi editor mở lại cho reword, đổi thành:
# feat: add interactive charts

# Kiểm tra kết quả
git log --oneline
# 3 commits sạch:
# xxx feat: add dashboard styling
# xxx feat: add interactive charts
# xxx feat: add dashboard layout

# Rebase lên main mới nhất
git checkout main
git pull
git checkout feature/dashboard
git rebase main

# Push (force vì đã rewrite history)
git push --force-with-lease origin feature/dashboard
```

---

## Bài 5: Git Rescue

```bash
# === 1. Hoàn tác commit chưa push ===
git commit -m "wrong commit"
git reset --soft HEAD~1
# Thay đổi vẫn ở staging area, có thể sửa và commit lại

# === 2. Hoàn tác commit đã push ===
git revert HEAD
# Tạo commit mới đảo ngược thay đổi của commit cuối
git push

# === 3. Khôi phục file đã xóa ===
# Tìm commit xóa file
git log --diff-filter=D --summary | grep "delete"
git log --all -- path/to/deleted-file.txt

# Khôi phục (checkout từ commit TRƯỚC commit xóa)
git checkout abc1234^ -- path/to/deleted-file.txt
git add path/to/deleted-file.txt
git commit -m "fix: restore accidentally deleted file"

# === 4. Stash workflow ===
# Đang code trên feature branch
git stash push -m "Dang lam login form"

# Chuyển sang hotfix
git checkout -b hotfix/urgent main
echo "fix" >> critical-file.txt
git add critical-file.txt
git commit -m "hotfix: fix critical bug"
git push origin hotfix/urgent

# Quay lại feature branch
git checkout feature/login
git stash pop
# Tiếp tục code...

# === 5. Cherry-pick ===
# Lấy commit từ branch khác
git log --oneline other-branch
# abc1234 feat: useful utility function

git checkout main
git cherry-pick abc1234

# === 6. Reflog - khôi phục branch đã xóa nhầm ===
# Xóa nhầm branch
git branch -D feature/important

# Tìm lại bằng reflog
git reflog
# abc1234 HEAD@{5}: commit: feat: important feature

# Khôi phục
git checkout -b feature/important abc1234
```

**Giải thích:**
- `reset --soft`: Di chuyển HEAD nhưng giữ thay đổi ở staging (an toàn nhất).
- `revert`: Tạo commit mới, an toàn cho shared branches (không rewrite history).
- `stash`: Lưu tạm thay đổi, rất hữu ích khi cần chuyển branch gấp.
- `cherry-pick`: Lấy 1 commit cụ thể, hữu ích cho hotfixes.
- `reflog`: Ghi lại mọi thay đổi của HEAD, là "bảo hiểm" cuối cùng.
