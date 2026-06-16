# Git & GitHub - Lý Thuyết Từ Cơ Bản Đến Nâng Cao

## Mục lục

1. [Giới thiệu về Git](#1-gioi-thieu-ve-git)
2. [Cài đặt và Cấu hình](#2-cai-dat-va-cau-hinh)
3. [Cơ bản: init, add, commit](#3-co-ban-init-add-commit)
4. [Branching và Merging](#4-branching-va-merging)
5. [Remote Repositories](#5-remote-repositories)
6. [Git Log và History](#6-git-log-va-history)
7. [Undoing Changes](#7-undoing-changes)
8. [Stashing](#8-stashing)
9. [Rebase](#9-rebase)
10. [Cherry-pick và Tags](#10-cherry-pick-va-tags)
11. [Gitignore](#11-gitignore)
12. [GitHub Workflow](#12-github-workflow)
13. [Git Hooks](#13-git-hooks)
14. [Conventional Commits](#14-conventional-commits)
15. [Best Practices](#15-best-practices)

---

## 1. Giới thiệu về Git

### 1.1 Git là gì?

Git là **hệ thống quản lý phiên bản phân tán** (Distributed Version Control System - DVCS) do Linus Torvalds tạo ra năm 2005. Git theo dõi thay đổi trong source code và cho phép nhiều developer cùng làm việc trên một dự án.

### 1.2 Khái niệm cơ bản

| Khái niệm | Mô tả |
|-----------|-------|
| Repository (Repo) | Thư mục dự án được Git quản lý |
| Commit | Một "snapshot" của code tại một thời điểm |
| Branch | Nhánh phát triển độc lập |
| Merge | Gộp hai nhánh lại |
| Remote | Repo trên server (GitHub, GitLab) |
| Clone | Sao chép repo từ remote về local |
| Pull | Tải thay đổi từ remote |
| Push | Đẩy thay đổi lên remote |

### 1.3 Ba vùng của Git

```
Working Directory  →  Staging Area  →  Repository
   (làm việc)        (git add)       (git commit)
     |                    |                |
  Các file            Chuẩn bị          Lưu trữ
  thay đổi            commit          vĩnh viễn
```

---

## 2. Cài đặt và Cấu hình

### 2.1 Cấu hình ban đầu

```bash
# Thiết lập tên và email (bắt buộc)
git config --global user.name "Nguyen Van A"
git config --global user.email "nguyenvana@email.com"

# Editor mặc định
git config --global core.editor "code --wait"

# Xem cấu hình
git config --list

# Cấu hình cho dự án cụ thể (không có --global)
git config user.name "Work Account"
```

### 2.2 SSH Key (kết nối GitHub)

```bash
# Tạo SSH key
ssh-keygen -t ed25519 -C "email@example.com"

# Copy public key
cat ~/.ssh/id_ed25519.pub

# Test kết nối
ssh -T git@github.com
```

---

## 3. Cơ bản: init, add, commit

### 3.1 Khởi tạo Repository

```bash
# Tạo repo mới
git init
git init my-project   # Tạo thư mục và init

# Clone repo có sẵn
git clone https://github.com/user/repo.git
git clone git@github.com:user/repo.git      # SSH
git clone https://github.com/user/repo.git my-folder  # Tên thư mục khác
```

### 3.2 Staging và Commit

```bash
# Xem trạng thái
git status
git status -s    # Rút gọn

# Thêm file vào staging
git add file.txt           # Một file
git add src/               # Cả thư mục
git add *.js               # Pattern
git add -A                 # Tất cả thay đổi
git add -p                 # Tương tác, chọn từng phần

# Commit
git commit -m "feat: them tinh nang dang nhap"
git commit -am "fix: sua loi validate"   # add + commit (chỉ file đã tracked)

# Xem thay đổi
git diff                   # Working vs Staging
git diff --staged          # Staging vs Last commit
git diff HEAD              # Working vs Last commit
```

---

## 4. Branching và Merging

### 4.1 Quản lý Branch

```bash
# Xem branches
git branch           # Local
git branch -r        # Remote
git branch -a        # Tất cả

# Tạo branch mới
git branch feature/login

# Chuyển branch
git checkout feature/login
git switch feature/login      # Git 2.23+

# Tạo và chuyển (tất cả trong 1)
git checkout -b feature/login
git switch -c feature/login   # Git 2.23+

# Xóa branch
git branch -d feature/login        # Đã merge
git branch -D feature/login        # Chưa merge (force)

# Đổi tên branch
git branch -m old-name new-name
git branch -m new-name             # Đổi tên branch hiện tại
```

### 4.2 Merge

```bash
# Merge branch vào branch hiện tại
git checkout main
git merge feature/login

# Merge không tạo merge commit (fast-forward)
git merge --ff-only feature/login

# Merge luôn tạo merge commit
git merge --no-ff feature/login

# Hủy merge đang conflict
git merge --abort
```

### 4.3 Giải quyết Conflict

```
<<<<<<< HEAD
Nội dung trên branch hiện tại
=======
Nội dung trên branch merge vào
>>>>>>> feature/login
```

```bash
# Sau khi sửa conflict:
git add file-conflict.txt
git commit    # Hoàn thành merge
```

---

## 5. Remote Repositories

### 5.1 Quản lý Remote

```bash
# Xem remotes
git remote -v

# Thêm remote
git remote add origin https://github.com/user/repo.git
git remote add upstream https://github.com/original/repo.git

# Push
git push origin main
git push -u origin main     # Set upstream (lần đầu)
git push                    # Sau khi set upstream

# Pull (fetch + merge)
git pull origin main
git pull --rebase origin main   # Dùng rebase thay vì merge

# Fetch (chỉ tải, không merge)
git fetch origin
git fetch --all

# Xóa remote branch
git push origin --delete feature/old
```

### 5.2 Fork Workflow

```bash
# 1. Fork repo trên GitHub
# 2. Clone fork về
git clone https://github.com/your-user/repo.git

# 3. Thêm upstream (repo gốc)
git remote add upstream https://github.com/original/repo.git

# 4. Đồng bộ với upstream
git fetch upstream
git checkout main
git merge upstream/main

# 5. Tạo branch, code, push lên fork
git checkout -b feature/new-feature
# ... code ...
git push origin feature/new-feature

# 6. Tạo Pull Request trên GitHub
```

---

## 6. Git Log và History

```bash
# Log cơ bản
git log
git log --oneline                    # 1 dòng mỗi commit
git log --oneline --graph            # Có biểu đồ nhánh
git log --oneline --graph --all      # Tất cả branches
git log -5                           # 5 commits gần nhất
git log --author="An"                # Theo tác giả
git log --since="2024-01-01"         # Theo ngày
git log --grep="fix"                 # Tìm trong commit message
git log -- path/to/file              # Lịch sử của file cụ thể
git log -p                           # Hiện diff

# Blame - xem ai sửa dòng nào
git blame file.txt
git blame -L 10,20 file.txt         # Chỉ dòng 10-20

# Show commit cụ thể
git show abc1234
git show HEAD~3                      # 3 commits trước
```

---

## 7. Undoing Changes

### 7.1 Các cách hoàn tác

```bash
# Hủy thay đổi trong Working Directory
git checkout -- file.txt      # Khôi phục file (GIT CŨ)
git restore file.txt          # Git 2.23+ (KHUYÊN DÙNG)

# Hủy staging (unstage)
git reset HEAD file.txt       # Git cũ
git restore --staged file.txt # Git 2.23+

# Sửa commit cuối (chưa push)
git commit --amend -m "Message mới"
git commit --amend --no-edit     # Giữ message, thêm file

# Revert (tạo commit mới để hoàn tác, AN TOÀN cho shared branches)
git revert abc1234
git revert HEAD                  # Revert commit cuối

# Reset (DI CHUYỂN HEAD, NGUY HIỂM cho shared branches)
git reset --soft HEAD~1    # Giữ staging và working
git reset --mixed HEAD~1   # Giữ working, xóa staging (mặc định)
git reset --hard HEAD~1    # Xóa hết (NGUY HIỂM!)
```

### 7.2 Khôi phục file đã xóa

```bash
# Tìm commit xóa file
git log --diff-filter=D -- path/to/file

# Khôi phục
git checkout abc1234^ -- path/to/file
```

---

## 8. Stashing

```bash
# Lưu tạm thay đổi hiện tại
git stash
git stash push -m "Dang lam feature login"

# Xem danh sách stash
git stash list

# Lấy lại stash
git stash pop             # Lấy và xóa khỏi stash list
git stash apply           # Lấy nhưng giữ lại trong stash list
git stash apply stash@{2} # Lấy stash cụ thể

# Xóa stash
git stash drop stash@{0}
git stash clear           # Xóa tất cả

# Tạo branch từ stash
git stash branch new-branch stash@{0}
```

---

## 9. Rebase

### 9.1 Rebase cơ bản

```bash
# Rebase feature branch lên main mới nhất
git checkout feature/login
git rebase main

# Tương đương với:
# 1. Lấy các commit của feature/login
# 2. Đặt chúng lên đầu main
# Kết quả: lịch sử sạch, tuyến tính
```

### 9.2 Interactive Rebase

```bash
# Chỉnh sửa 3 commits gần nhất
git rebase -i HEAD~3

# Trong editor:
pick abc1234 feat: them login
squash def5678 fix: sua typo login    # Gộp vào commit trước
reword ghi9012 feat: them logout      # Đổi message

# Các lệnh: pick, reword, edit, squash, fixup, drop
```

### 9.3 Rebase vs Merge

| | Merge | Rebase |
| |--|-------|--------|
| Lịch sử | Giữ nguyên, có merge commits | Tuyến tính, sạch |
| An toàn | An toàn cho shared branches | CHỈ dùng cho local branches |
| Conflict | Giải quyết 1 lần | Có thể giải quyết nhiều lần |
| Khi nào | Merge feature vào main | Cập nhật feature branch |

**Quy tắc vàng:** KHÔNG BAO GIỜ rebase branch đã push và có người khác đang làm việc.

---

## 10. Cherry-pick và Tags

### 10.1 Cherry-pick

```bash
# Lấy 1 commit từ branch khác
git cherry-pick abc1234

# Nhiều commits
git cherry-pick abc1234 def5678

# Chỉ staging, không commit
git cherry-pick --no-commit abc1234
```

### 10.2 Tags

```bash
# Tạo tag
git tag v1.0.0                        # Lightweight
git tag -a v1.0.0 -m "Release 1.0.0" # Annotated (khuyên dùng)
git tag -a v1.0.0 abc1234             # Tag commit cụ thể

# Xem tags
git tag
git show v1.0.0

# Push tags
git push origin v1.0.0      # 1 tag
git push origin --tags       # Tất cả tags

# Xóa tag
git tag -d v1.0.0                    # Local
git push origin --delete v1.0.0      # Remote
```

---

## 11. Gitignore

### 11.1 Cú pháp .gitignore

```gitignore
# Comment
*.log                  # Tất cả file .log
!important.log         # Ngoại trừ important.log

# Thư mục
node_modules/
dist/
build/
.idea/
.vscode/

# File cụ thể
.env
.env.local
*.secret

# Pattern
**/temp/               # Thư mục temp ở bất kỳ đâu
src/**/*.test.js       # Tất cả .test.js trong src/

# OS files
.DS_Store
Thumbs.db
```

### 11.2 Gitignore cho file đã tracked

```bash
# Xóa file khỏi Git nhưng giữ trên disk
git rm --cached file.txt
git rm -r --cached node_modules/

# Sau đó thêm vào .gitignore và commit
```

---

## 12. GitHub Workflow

### 12.1 Pull Request (PR)

1. **Fork** hoặc **tạo branch** từ main
2. **Code** và commit với conventional commits
3. **Push** branch lên remote
4. **Tạo PR** trên GitHub với mô tả rõ ràng
5. **Code Review** - đợi người khác review
6. **Address feedback** - sửa theo comment
7. **Merge** sau khi approved
8. **Xóa branch** sau khi merge

### 12.2 GitHub Actions (CI/CD cơ bản)

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
# Các hook phổ biến (trong .git/hooks/)
pre-commit       # Chạy trước khi commit (lint, format)
commit-msg       # Kiểm tra commit message
pre-push         # Chạy trước khi push (test)
```

### 13.2 Husky (Git Hooks dễ dàng)

```bash
# Cài đặt
npm install --save-dev husky
npx husky init

# Pre-commit hook
echo "npm run lint" > .husky/pre-commit

# Commit-msg hook (kiểm tra conventional commits)
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

| Type | Mô tả |
|------|-------|
| feat | Tính năng mới |
| fix | Sửa lỗi |
| docs | Chỉ thay đổi documentation |
| style | Format, thiếu dấu chấm phẩy (không thay đổi logic) |
| refactor | Tái cấu trúc code (không thêm tính năng, không sửa lỗi) |
| perf | Cải thiện performance |
| test | Thêm hoặc sửa test |
| chore | Thay đổi build, CI, dependencies |
| ci | Thay đổi CI configuration |
| revert | Revert commit trước |

### 14.3 Ví dụ

```
feat(auth): them chuc nang dang nhap bang Google

- Tich hop Google OAuth2
- Them nut "Dang nhap voi Google" tren trang login
- Luu token vao localStorage

Closes #123
```

---

## 15. Best Practices

1. **Commit thường xuyên:** Mỗi commit là một thay đổi logic nhỏ
2. **Message rõ ràng:** Dùng conventional commits
3. **Branch strategy:** main (stable), develop, feature/*, bugfix/*, hotfix/*
4. **Pull trước Push:** Luôn `git pull --rebase` trước khi push
5. **Review code:** Không merge trực tiếp, luôn qua PR/MR
6. **Không commit secrets:** Dùng .env và .gitignore
7. **.gitignore từ đầu:** Thêm ngay khi init project
8. **Tag releases:** Dùng semantic versioning (v1.2.3)
9. **Không force push main:** Chỉ force push trên branch cá nhân
10. **Rebase local, merge shared:** Giữ lịch sử sạch nhưng an toàn

---

## Tổng kết

Git là công cụ quản lý phiên bản thiết yếu cho mọi developer:

1. **Cơ bản:** init, add, commit, status, diff
2. **Branching:** branch, checkout/switch, merge
3. **Remote:** clone, push, pull, fetch
4. **Lịch sử:** log, blame, show
5. **Hoàn tác:** restore, revert, reset
6. **Nâng cao:** rebase, cherry-pick, stash, tags
7. **Workflow:** Feature branch, PR, code review
8. **Convention:** Conventional commits, semantic versioning
