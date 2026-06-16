# Git & GitHub - Dap An Bai Tap

## Bai 1: Git Co Ban

```bash
# 1. Tao thu muc va init
mkdir my-project && cd my-project
git init

# 2. Tao README.md
echo "# My Project" > README.md

# 3. Tao .gitignore
cat > .gitignore << EOF
node_modules/
.env
.env.local
dist/
.DS_Store
EOF

# 4. Commit dau tien
git add README.md .gitignore
git commit -m "feat: initial project setup"

# 5. Tao files
echo '<!DOCTYPE html><html><body><h1>Hello</h1></body></html>' > index.html
echo 'body { margin: 0; font-family: sans-serif; }' > style.css

# 6. Chi add index.html
git add index.html

# 7. Kiem tra trang thai
git status
# On branch main
# Changes to be committed:
#   new file:   index.html
# Untracked files:
#   style.css

# 8. Commit index.html
git commit -m "feat: add homepage"

# 9. Xem lich su
git log --oneline
# abc1234 feat: add homepage
# def5678 feat: initial project setup

# 10. Add va commit style.css
git add style.css
git commit -m "feat: add base styles"
```

---

## Bai 2: Branching va Merging

```bash
# 1. Tao branch feature/navbar
git checkout -b feature/navbar

# 2. Tao va commit navbar
echo '<nav>Navigation</nav>' > navbar.html
git add navbar.html
git commit -m "feat: add navigation bar"

# 3. Quay lai main, tao feature/footer
git checkout main
git checkout -b feature/footer

# 4. Tao va commit footer
echo '<footer>Footer</footer>' > footer.html
git add footer.html
git commit -m "feat: add footer"

# 5. Merge navbar vao main (fast-forward)
git checkout main
git merge feature/navbar
# Fast-forward merge (vi main chua co commit moi)

# 6. Merge footer vao main (merge commit)
git merge --no-ff feature/footer -m "Merge feature/footer into main"

# 7. Tao feature/hero, sua index.html
git checkout -b feature/hero
# Sua dong 1 cua index.html thanh: <!DOCTYPE html><html><body><h1>HERO</h1></body></html>
sed -i 's/Hello/HERO/' index.html
git add index.html
git commit -m "feat: add hero section"

# 8. Quay lai main, tao conflict
git checkout main
sed -i 's/Hello/Welcome/' index.html
git add index.html
git commit -m "feat: update welcome message"

# 9. Merge - se co conflict
git merge feature/hero
# CONFLICT (content): Merge conflict in index.html

# Sua file index.html - giu noi dung mong muon
# Vi du: giu ca hai -> "HERO Welcome"
# Xoa cac marker <<<<<<< ======= >>>>>>>

git add index.html
git commit -m "Merge feature/hero, resolve index.html conflict"

# 10. Xem lich su
git log --oneline --graph --all
```

---

## Bai 3: Remote Workflow

```bash
# 1. Tao repo tren GitHub (qua web)

# 2. Ket noi remote
git remote add origin https://github.com/username/my-project.git

# 3. Push main
git push -u origin main

# 4. Tao feature branch va push
git checkout -b feature/login
echo '<form><input type="email"/><input type="password"/></form>' > login.html
git add login.html
git commit -m "feat: add login form"
git push -u origin feature/login

# 5. Tao PR tren GitHub (qua web interface)
# Title: feat: add login functionality
# Description: Them form dang nhap voi email va password

# 6. Fix theo feedback
echo 'Updated login with validation' >> login.html
git add login.html
git commit -m "fix: add form validation per review feedback"

# 7. Push va merge
git push
# Merge PR tren GitHub

# 8. Pull main moi nhat
git checkout main
git pull origin main

# 9. Xoa branch
git branch -d feature/login          # Local
git push origin --delete feature/login  # Remote
```

---

## Bai 4: Rebase va History

```bash
# Tao 5 commits
git checkout -b feature/dashboard

echo '<div class="dashbord">Dashboard</div>' > dashboard.html
git add dashboard.html
git commit -m "add dashboard layout"

# Fix typo
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

# Trong editor, thay doi thanh:
# pick abc1234 add dashboard layout
# fixup def5678 fix typo in dashboard        <- gop vao commit tren, bo message
# reword ghi9012 add charts component        <- doi message
# pick jkl3456 wip: styling
# fixup mno7890 finish dashboard styling     <- gop vao commit tren

# Khi editor mo lai cho reword, doi thanh:
# feat: add interactive charts

# Kiem tra ket qua
git log --oneline
# 3 commits sach:
# xxx feat: add dashboard styling
# xxx feat: add interactive charts
# xxx feat: add dashboard layout

# Rebase len main moi nhat
git checkout main
git pull
git checkout feature/dashboard
git rebase main

# Push (force vi da rewrite history)
git push --force-with-lease origin feature/dashboard
```

---

## Bai 5: Git Rescue

```bash
# === 1. Hoan tac commit chua push ===
git commit -m "wrong commit"
git reset --soft HEAD~1
# Thay doi van o staging area, co the sua va commit lai

# === 2. Hoan tac commit da push ===
git revert HEAD
# Tao commit moi dao nguoc thay doi cua commit cuoi
git push

# === 3. Khoi phuc file da xoa ===
# Tim commit xoa file
git log --diff-filter=D --summary | grep "delete"
git log --all -- path/to/deleted-file.txt

# Khoi phuc (checkout tu commit TRUOC commit xoa)
git checkout abc1234^ -- path/to/deleted-file.txt
git add path/to/deleted-file.txt
git commit -m "fix: restore accidentally deleted file"

# === 4. Stash workflow ===
# Dang code tren feature branch
git stash push -m "Dang lam login form"

# Chuyen sang hotfix
git checkout -b hotfix/urgent main
echo "fix" >> critical-file.txt
git add critical-file.txt
git commit -m "hotfix: fix critical bug"
git push origin hotfix/urgent

# Quay lai feature branch
git checkout feature/login
git stash pop
# Tiep tuc code...

# === 5. Cherry-pick ===
# Lay commit tu branch khac
git log --oneline other-branch
# abc1234 feat: useful utility function

git checkout main
git cherry-pick abc1234

# === 6. Reflog - khoi phuc branch da xoa ===
# Xoa nham branch
git branch -D feature/important

# Tim lai bang reflog
git reflog
# abc1234 HEAD@{5}: commit: feat: important feature

# Khoi phuc
git checkout -b feature/important abc1234
```

**Giai thich:**
- `reset --soft`: Di chuyen HEAD nhung giu thay doi o staging (an toan nhat)
- `revert`: Tao commit moi, an toan cho shared branches (khong rewrite history)
- `stash`: Luu tam thay doi, rat huu ich khi can chuyen branch gap
- `cherry-pick`: Lay 1 commit cu the, huu ich cho hotfixes
- `reflog`: Ghi lai moi thay doi cua HEAD, la "bao hiem" cuoi cung
