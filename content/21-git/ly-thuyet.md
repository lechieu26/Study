# Git & GitHub - Ly Thuyet Tu Co Ban Den Nang Cao

## Muc luc

1. [Gioi thieu ve Git](#1-gioi-thieu-ve-git)
2. [Cai dat va Cau hinh](#2-cai-dat-va-cau-hinh)
3. [Co ban: init, add, commit](#3-co-ban-init-add-commit)
4. [Branching va Merging](#4-branching-va-merging)
5. [Remote Repositories](#5-remote-repositories)
6. [Git Log va History](#6-git-log-va-history)
7. [Undoing Changes](#7-undoing-changes)
8. [Stashing](#8-stashing)
9. [Rebase](#9-rebase)
10. [Cherry-pick va Tags](#10-cherry-pick-va-tags)
11. [Gitignore](#11-gitignore)
12. [GitHub Workflow](#12-github-workflow)
13. [Git Hooks](#13-git-hooks)
14. [Conventional Commits](#14-conventional-commits)
15. [Best Practices](#15-best-practices)

---

## 1. Gioi thieu ve Git

### 1.1 Git la gi?

Git la **he thong quan ly phien ban phan tan** (Distributed Version Control System - DVCS) do Linus Torvalds tao ra nam 2005. Git theo doi thay doi trong source code va cho phep nhieu developer cung lam viec tren mot du an.

### 1.2 Khai niem co ban

| Khai niem | Mo ta |
|-----------|-------|
| Repository (Repo) | Thu muc du an duoc Git quan ly |
| Commit | Mot "snapshot" cua code tai mot thoi diem |
| Branch | Nhanh phat trien doc lap |
| Merge | Gop hai nhanh lai |
| Remote | Repo tren server (GitHub, GitLab) |
| Clone | Sao chep repo tu remote ve local |
| Pull | Tai thay doi tu remote |
| Push | Day thay doi len remote |

### 1.3 Ba vung cua Git

```
Working Directory  →  Staging Area  →  Repository
   (lam viec)        (git add)       (git commit)
     |                    |                |
  Cac file            Chuan bi          Luu tru
  thay doi            commit          vinh vien
```

---

## 2. Cai dat va Cau hinh

### 2.1 Cau hinh ban dau

```bash
# Thiet lap ten va email (bat buoc)
git config --global user.name "Nguyen Van A"
git config --global user.email "nguyenvana@email.com"

# Editor mac dinh
git config --global core.editor "code --wait"

# Xem cau hinh
git config --list

# Cau hinh cho du an cu the (khong co --global)
git config user.name "Work Account"
```

### 2.2 SSH Key (ket noi GitHub)

```bash
# Tao SSH key
ssh-keygen -t ed25519 -C "email@example.com"

# Copy public key
cat ~/.ssh/id_ed25519.pub

# Test ket noi
ssh -T git@github.com
```

---

## 3. Co ban: init, add, commit

### 3.1 Khoi tao Repository

```bash
# Tao repo moi
git init
git init my-project   # Tao thu muc va init

# Clone repo co san
git clone https://github.com/user/repo.git
git clone git@github.com:user/repo.git      # SSH
git clone https://github.com/user/repo.git my-folder  # Ten thu muc khac
```

### 3.2 Staging va Commit

```bash
# Xem trang thai
git status
git status -s    # Rut gon

# Them file vao staging
git add file.txt           # Mot file
git add src/               # Ca thu muc
git add *.js               # Pattern
git add -A                 # Tat ca thay doi
git add -p                 # Tuong tac, chon tung phan

# Commit
git commit -m "feat: them tinh nang dang nhap"
git commit -am "fix: sua loi validate"   # add + commit (chi file da tracked)

# Xem thay doi
git diff                   # Working vs Staging
git diff --staged          # Staging vs Last commit
git diff HEAD              # Working vs Last commit
```

---

## 4. Branching va Merging

### 4.1 Quan ly Branch

```bash
# Xem branches
git branch           # Local
git branch -r        # Remote
git branch -a        # Tat ca

# Tao branch moi
git branch feature/login

# Chuyen branch
git checkout feature/login
git switch feature/login      # Git 2.23+

# Tao va chuyen (tat ca 1)
git checkout -b feature/login
git switch -c feature/login   # Git 2.23+

# Xoa branch
git branch -d feature/login        # Da merge
git branch -D feature/login        # Chua merge (force)

# Doi ten branch
git branch -m old-name new-name
git branch -m new-name             # Doi ten branch hien tai
```

### 4.2 Merge

```bash
# Merge branch vao branch hien tai
git checkout main
git merge feature/login

# Merge khong tao merge commit (fast-forward)
git merge --ff-only feature/login

# Merge luon tao merge commit
git merge --no-ff feature/login

# Huy merge dang conflict
git merge --abort
```

### 4.3 Giai quyet Conflict

```
<<<<<<< HEAD
Noi dung tren branch hien tai
=======
Noi dung tren branch merge vao
>>>>>>> feature/login
```

```bash
# Sau khi sua conflict:
git add file-conflict.txt
git commit    # Hoan thanh merge
```

---

## 5. Remote Repositories

### 5.1 Quan ly Remote

```bash
# Xem remotes
git remote -v

# Them remote
git remote add origin https://github.com/user/repo.git
git remote add upstream https://github.com/original/repo.git

# Push
git push origin main
git push -u origin main     # Set upstream (lan dau)
git push                    # Sau khi set upstream

# Pull (fetch + merge)
git pull origin main
git pull --rebase origin main   # Dung rebase thay merge

# Fetch (chi tai, khong merge)
git fetch origin
git fetch --all

# Xoa remote branch
git push origin --delete feature/old
```

### 5.2 Fork Workflow

```bash
# 1. Fork repo tren GitHub
# 2. Clone fork ve
git clone https://github.com/your-user/repo.git

# 3. Them upstream (repo goc)
git remote add upstream https://github.com/original/repo.git

# 4. Dong bo voi upstream
git fetch upstream
git checkout main
git merge upstream/main

# 5. Tao branch, code, push len fork
git checkout -b feature/new-feature
# ... code ...
git push origin feature/new-feature

# 6. Tao Pull Request tren GitHub
```

---

## 6. Git Log va History

```bash
# Log co ban
git log
git log --oneline                    # 1 dong moi commit
git log --oneline --graph            # Co bieu do nhanh
git log --oneline --graph --all      # Tat ca branches
git log -5                           # 5 commits gan nhat
git log --author="An"                # Theo tac gia
git log --since="2024-01-01"         # Theo ngay
git log --grep="fix"                 # Tim trong commit message
git log -- path/to/file              # Lich su cua file cu the
git log -p                           # Hien diff

# Blame - xem ai sua dong nao
git blame file.txt
git blame -L 10,20 file.txt         # Chi dong 10-20

# Show commit cu the
git show abc1234
git show HEAD~3                      # 3 commits truoc
```

---

## 7. Undoing Changes

### 7.1 Cac cach hoan tac

```bash
# Huy thay doi trong Working Directory
git checkout -- file.txt      # Khoi phuc file (GIT CU)
git restore file.txt          # Git 2.23+ (KHUYEN DUNG)

# Huy staging (unstage)
git reset HEAD file.txt       # Git cu
git restore --staged file.txt # Git 2.23+

# Sua commit cuoi (chua push)
git commit --amend -m "Message moi"
git commit --amend --no-edit     # Giu message, them file

# Revert (tao commit moi de hoan tac, AN TOAN cho shared branches)
git revert abc1234
git revert HEAD                  # Revert commit cuoi

# Reset (DI CHUYEN HEAD, NGUY HIEM cho shared branches)
git reset --soft HEAD~1    # Giu staging va working
git reset --mixed HEAD~1   # Giu working, xoa staging (mac dinh)
git reset --hard HEAD~1    # Xoa het (NGUY HIEM!)
```

### 7.2 Khoi phuc file da xoa

```bash
# Tim commit xoa file
git log --diff-filter=D -- path/to/file

# Khoi phuc
git checkout abc1234^ -- path/to/file
```

---

## 8. Stashing

```bash
# Luu tam thay doi hien tai
git stash
git stash push -m "Dang lam feature login"

# Xem danh sach stash
git stash list

# Lay lai stash
git stash pop             # Lay va xoa khoi stash list
git stash apply           # Lay nhung giu lai trong stash list
git stash apply stash@{2} # Lay stash cu the

# Xoa stash
git stash drop stash@{0}
git stash clear           # Xoa tat ca

# Tao branch tu stash
git stash branch new-branch stash@{0}
```

---

## 9. Rebase

### 9.1 Rebase co ban

```bash
# Rebase feature branch len main moi nhat
git checkout feature/login
git rebase main

# Tuong duong voi:
# 1. Lay cac commit cua feature/login
# 2. Dat chung len dau main
# Ket qua: lich su sach, tuyen tinh
```

### 9.2 Interactive Rebase

```bash
# Chinh sua 3 commits gan nhat
git rebase -i HEAD~3

# Trong editor:
pick abc1234 feat: them login
squash def5678 fix: sua typo login    # Gop vao commit truoc
reword ghi9012 feat: them logout      # Doi message

# Cac lenh: pick, reword, edit, squash, fixup, drop
```

### 9.3 Rebase vs Merge

| | Merge | Rebase |
|--|-------|--------|
| Lich su | Giu nguyen, co merge commits | Tuyen tinh, sach |
| An toan | An toan cho shared branches | CHI dung cho local branches |
| Conflict | Giai quyet 1 lan | Co the giai quyet nhieu lan |
| Khi nao | Merge feature vao main | Cap nhat feature branch |

**Quy tac vang:** KHONG BAO GIO rebase branch da push va co nguoi khac dang lam viec.

---

## 10. Cherry-pick va Tags

### 10.1 Cherry-pick

```bash
# Lay 1 commit tu branch khac
git cherry-pick abc1234

# Nhieu commits
git cherry-pick abc1234 def5678

# Chi staging, khong commit
git cherry-pick --no-commit abc1234
```

### 10.2 Tags

```bash
# Tao tag
git tag v1.0.0                        # Lightweight
git tag -a v1.0.0 -m "Release 1.0.0" # Annotated (khuyen dung)
git tag -a v1.0.0 abc1234             # Tag commit cu the

# Xem tags
git tag
git show v1.0.0

# Push tags
git push origin v1.0.0      # 1 tag
git push origin --tags       # Tat ca tags

# Xoa tag
git tag -d v1.0.0                    # Local
git push origin --delete v1.0.0      # Remote
```

---

## 11. Gitignore

### 11.1 Cu phap .gitignore

```gitignore
# Comment
*.log                  # Tat ca file .log
!important.log         # Ngoai tru important.log

# Thu muc
node_modules/
dist/
build/
.idea/
.vscode/

# File cu the
.env
.env.local
*.secret

# Pattern
**/temp/               # Thu muc temp o bat ky dau
src/**/*.test.js       # Tat ca .test.js trong src/

# OS files
.DS_Store
Thumbs.db
```

### 11.2 Gitignore cho file da tracked

```bash
# Xoa file khoi Git nhung giu tren disk
git rm --cached file.txt
git rm -r --cached node_modules/

# Sau do them vao .gitignore va commit
```

---

## 12. GitHub Workflow

### 12.1 Pull Request (PR)

1. **Fork** hoac **tao branch** tu main
2. **Code** va commit voi conventional commits
3. **Push** branch len remote
4. **Tao PR** tren GitHub voi mo ta ro rang
5. **Code Review** - doi nguoi khac review
6. **Address feedback** - sua theo comment
7. **Merge** sau khi approved
8. **Xoa branch** sau merge

### 12.2 GitHub Actions (CI/CD co ban)

```yaml
# .github/workflows/ci.yml
name: CI

on:
  push:
    branches: [main]
  pull_request:
    branches: [main]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
        with:
          node-version: '20'
      - run: npm ci
      - run: npm test
      - run: npm run lint
```

---

## 13. Git Hooks

### 13.1 Local Hooks

```bash
# Cac hook pho bien (trong .git/hooks/)
pre-commit       # Chay truoc khi commit (lint, format)
commit-msg       # Kiem tra commit message
pre-push         # Chay truoc khi push (test)
```

### 13.2 Husky (Git Hooks de dang)

```bash
# Cai dat
npm install --save-dev husky
npx husky init

# Pre-commit hook
echo "npm run lint" > .husky/pre-commit

# Commit-msg hook (kiem tra conventional commits)
echo 'npx commitlint --edit "$1"' > .husky/commit-msg
```

---

## 14. Conventional Commits

### 14.1 Format

```
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

### 14.2 Types

| Type | Mo ta |
|------|-------|
| feat | Tinh nang moi |
| fix | Sua loi |
| docs | Chi thay doi documentation |
| style | Format, thieu dau cham phay (khong thay doi logic) |
| refactor | Tai cau truc code (khong them tinh nang, khong sua loi) |
| perf | Cai thien performance |
| test | Them hoac sua test |
| chore | Thay doi build, CI, dependencies |
| ci | Thay doi CI configuration |
| revert | Revert commit truoc |

### 14.3 Vi du

```
feat(auth): them chuc nang dang nhap bang Google

- Tich hop Google OAuth2
- Them nut "Dang nhap voi Google" tren trang login
- Luu token vao localStorage

Closes #123
```

---

## 15. Best Practices

1. **Commit thuong xuyen:** Moi commit la mot thay doi logic nho
2. **Message ro rang:** Dung conventional commits
3. **Branch strategy:** main (stable), develop, feature/*, bugfix/*, hotfix/*
4. **Pull truoc Push:** Luon `git pull --rebase` truoc khi push
5. **Review code:** Khong merge truc tiep, luon qua PR/MR
6. **Khong commit secrets:** Dung .env va .gitignore
7. **.gitignore tu dau:** Them ngay khi init project
8. **Tag releases:** Dung semantic versioning (v1.2.3)
9. **Khong force push main:** Chi force push tren branch ca nhan
10. **Rebase local, merge shared:** Giu lich su sach nhung an toan

---

## Tong ket

Git la cong cu quan ly phien ban thiet yeu cho moi developer:

1. **Co ban:** init, add, commit, status, diff
2. **Branching:** branch, checkout/switch, merge
3. **Remote:** clone, push, pull, fetch
4. **Lich su:** log, blame, show
5. **Hoan tac:** restore, revert, reset
6. **Nang cao:** rebase, cherry-pick, stash, tags
7. **Workflow:** Feature branch, PR, code review
8. **Convention:** Conventional commits, semantic versioning
