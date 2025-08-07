---
简介: 便于查询使用
---
# 常用命令

Git 是最常用的分布式版本控制系统之一，以下是一些开发中最常用的 Git 命令，按用途分类总结如下：

---

## 一、配置类

```bash
git config --global user.name "你的名字"
git config --global user.email "你的邮箱"
git config --global core.editor vim           # 设置默认编辑器
git config --list                             # 查看当前配置
```

---

## 二、本地仓库操作

```bash
git init                                       # 初始化本地仓库
git status                                     # 查看当前状态
git add <文件/目录>                            # 添加到暂存区
git add .                                      # 添加全部修改
git commit -m "提交信息"                        # 提交到本地仓库
git commit -am "提交信息"                       # 跳过 add（适用于已跟踪的文件）
```

---

## 三、版本查看与回退

```bash
git log                                        # 查看提交历史
git log --oneline                              # 简洁格式查看日志
git diff                                       # 查看尚未暂存的变更
git diff --cached                              # 查看已暂存但未提交的变更
git reset HEAD <文件>                          # 从暂存区移除（不会删除修改）
git checkout -- <文件>                         # 撤销工作区修改
git reset --soft HEAD^                         # 回退一次提交，保留工作区和暂存区修改
git reset --mixed HEAD^                        # 回退并移出暂存区（默认）
git reset --hard HEAD^                         # 回退并清除所有修改
```

---

## 四、分支操作

```bash
git branch                                     # 查看本地分支
git branch <分支名>                            # 创建新分支
git checkout <分支名>                          # 切换分支
git checkout -b <分支名>                       # 创建并切换分支
git merge <分支名>                             # 合并其他分支到当前分支
git branch -d <分支名>                         # 删除本地分支
```

---

## 五、远程操作

```bash
git remote add origin <仓库地址>              # 添加远程仓库
git remote -v                                  # 查看远程仓库信息
git push -u origin <分支名>                    # 推送本地分支到远程
git push                                       # 推送更新
git pull                                       # 拉取并合并远程更新
git clone <仓库地址>                           # 克隆远程仓库
```

---

## 六、标签（Tag）管理

```bash
git tag                                        # 查看所有标签
git tag <标签名>                               # 创建标签
git tag -a <标签名> -m "说明"                   # 创建带注释的标签
git push origin <标签名>                       # 推送标签到远程
git push origin --tags                         # 推送所有标签
```

---

## 七、其他常用

```bash
git stash                                      # 暂存当前修改
git stash apply                                # 应用最近的暂存
git stash pop                                  # 应用并删除最近暂存
git cherry-pick <提交ID>                       # 拿一个提交应用到当前分支
```

---

需要更深入的使用或工作流（如 rebase、submodule、hook、reflog）可以继续问我。是否需要我提供「典型 Git 工作流」示意图或真实项目中使用的命令顺序？

# 多人协作流程

### 1. git clone远端代码

```bash
git clone <远程仓库地址>
cd <项目目录>
```

### 2. 新建开发分支

```bash
git checkout main              # 切换到主分支（确保是最新的基础）
git pull origin main           # 同步远程主分支
git checkout -b feature/login  # 创建并切换到开发分支
```

### 3. 在开发分支上进行开发

```bash
git add .
git commit -m "feat: 登录功能开发完成"
```


### 4. 将本地分支推送到远程，并与远程分支关联：

```bash
git push -u origin feature/login
```

> `-u` 作用是设置 upstream，之后可以直接用 `git push` / `git pull`。

### 5. 提交pr

使用github或者gitlab进行

### 5. pr合并后回到本地主分支，拉取更新

```bash
git checkout main
git pull origin main
```

### 6. 删除开发分支（本地+远端）

```bash
# 删除本地分支
git branch -d feature/login

# 删除远程分支
git push origin --delete feature/login
```

### 7. 回到步骤2，继续开发

# 本地和远端建库

### 1. 在github等新建一个空仓库

不要README

### 2. 在本地文件夹初始化git

```bash
cd /你的项目目录路径

git init                            # 初始化为 git 仓库
git add .                           # 添加所有文件
git commit -m "init: 初始化项目代码"  # 提交第一次 commit
```

如果有需要记得提前创建好gitignore
### 3. 本地和远端关联

```bash
git remote add origin https://github.com/your-name/your-repo.git
```

### 4. 推送代码到远端

如果远端的主分支是main

```bash
git branch -M main              # 重命名当前分支为 main（如需要）
git push -u origin main         # 第一次推送，建立本地和远程关联
```

如果使用的是master分支，也可以不改名

```bash
git push -u origin master
```

`-u` 参数用于将本地分支绑定远程分支，之后可以直接使用 `git push` / `git pull`。


# 冲突处理（Conflict Resolution）

多人协作时常见合并冲突，需要人工介入处理。

```bash
git pull                            # 拉取代码时可能出现冲突
# Git 会提示冲突文件，手动修改后继续流程

git status                         # 查看冲突文件
# 修改冲突后，标记为已解决：
git add <冲突文件>
git commit                         # 提交合并后的结果
```

> 冲突文件会包含：
> 
> ```
> <<<<<<< HEAD
> 当前分支内容
> =======
> 远程分支内容
> >>>>>>> remote-branch
> ```
> 
> 手动保留正确版本、删除冲突标记。

# 进阶操作

### 1. `rebase`（变基）

将当前分支的提交“平铺”到目标分支之后，使提交历史更干净。

```bash
git checkout feature/login
git fetch origin
git rebase origin/main
```

> 注意：
> 
> - rebase 会**修改历史**，用于自己分支更清晰
>     
> - 合并冲突后需 `git rebase --continue`
>     

---

### 2. `reflog`（查看历史 HEAD）

当误删分支、reset 错误时，可通过 reflog 找回提交。

```bash
git reflog                      # 查看 HEAD 的历史变动记录
git checkout <提交ID>          # 回到误删之前的状态
```

---

### 3. `submodule`（子模块）

适用于将另一个 Git 仓库作为当前项目的一部分。

```bash
git submodule add <子模块地址> <路径>
git submodule update --init     # 初始化子模块
git submodule update --remote   # 拉取子模块更新
```

---

### 4. Git Hooks（钩子脚本）

自动在某些 Git 操作前后执行脚本，例如提交前代码检查。

路径：`.git/hooks/`，常用如：

- `pre-commit`：提交前运行 lint 工具
    
- `post-merge`：合并后自动构建
    

---

### 5. 修改最后一次提交（不改变历史）

```bash
git commit --amend -m "修改后的提交信息"
```

---

### 6. 忽略文件配置 `.gitignore`

创建 `.gitignore` 文件并添加不需要纳入 Git 管理的文件/目录。

示例内容：

```
node_modules/
dist/
*.log
.env
.idea/
```

---

### 7. 清理工作区

```bash
git clean -fd                     # 删除未被跟踪的文件和目录（慎用）
git clean -n                      # 查看会删除哪些文件（推荐先用）
```


