# CurseForge 发布操作手册

按顺序做，不要跳步。CurseForge 的页面文字可能是英文，下面括号里是中文含义。

## 0. 准备材料

| 材料 | 文件 |
| --- | --- |
| jar | `D:\常用\MC模组开发\create-oddities\build\libs\create-oddities-0.1.1.jar` |
| 项目头像 | `src\main\resources\icon.png`（512×512） |
| 项目简介 | `docs\curseforge-description.md` |
| 源码 | `https://github.com/Gustav-800/create-oddities` |
| 问题反馈 | `https://github.com/Gustav-800/create-oddities/issues` |
| 许可 | MIT |

先确认 jar 是最新的 0.1.1：

```
大小：121,691 字节
SHA-256：C55C7C52143E5A7C314CF63EEF662F8039337A6305C255634B6701E70B723432
```

---

## 1. 登录作者后台

打开：

```
https://authors.curseforge.com/
```

用 CurseForge 账号登录。第一次进入作者后台会要求：

- 同意 Author Terms（作者条款）
- 填写作者资料 / 收款信息（可以不填收款，除非你要变现）
- 验证邮箱

完成后进入 **Projects** 页面。

---

## 2. 创建项目

点 **Create a Project**（创建项目），选择：

| 字段 | 填写 |
| --- | --- |
| Game | Minecraft |
| Project type | Mods |
| Name | `Create Oddities` |
| Summary | `A modular companion for Create: fixes, tweaks, and the occasional new machine.` |
| Categories | `Technology` + `Utility & QoL`（以页面实际分类为准） |
| License | MIT License |
| Source URL | `https://github.com/Gustav-800/create-oddities` |
| Issues URL | `https://github.com/Gustav-800/create-oddities/issues` |
| Project avatar | `src\main\resources\icon.png` |

### Description（项目介绍）

打开 `docs\curseforge-description.md`，把 **Create Oddities** 那一整段英文复制进 Description。

CurseForge 的编辑器是富文本：

- 标题用工具栏的 Heading
- 列表用项目符号按钮
- 不要直接粘贴 Markdown 的 `#`、`**`
- 粘贴后检查一次排版

中文段落可以放在英文后面，也可以不放；CurseForge 主要面向英文用户。

### 可见性

- Visibility：**Public**
- Comments：可以打开
- 其他选项保持默认

保存项目。

---

## 3. 上传 0.1.1 文件

进入项目 Dashboard → **Files** → **Upload File**（上传文件）。

| 字段 | 填写 |
| --- | --- |
| File | `build\libs\create-oddities-0.1.1.jar` |
| Display name | `Create Oddities 0.1.1` |
| Release type | `Beta`（早期开发版） |
| Game version | `1.21.1` |
| Mod loader | `NeoForge` |
| Environment | `Client` |
| File name | 保持 `create-oddities-0.1.1.jar` |

### Changelog

复制 `CHANGELOG.md` 的 0.1.1 段：

```
0.1.1 — 2026-11-21

Fixed
- F11 now toggles fullscreen while the typewriter is active. The event is handled at the head of KeyboardHandler#keyPress and cancelled, so Simulated's later handler cannot consume it.

Changed
- Project renamed to Create Oddities / 机械动力：奇思妙想.
- Internal mod id changed from every_key_a_gem to create_oddities.
- Create is now the only required mod dependency; Simulated is optional.
```

### Dependencies（依赖关系）

在文件的依赖设置里添加：

| 项目 | 类型 |
| --- | --- |
| Create | **Required**（必需） |
| Create: Aeronautics | **Optional**（可选） |

不要添加其他附属为必需；当前 0.1.1 只硬依赖 Create。

### 保存并提交

点 **Upload / Save**。

CurseForge 会：

1. 扫描 jar
2. 检查项目资料
3. 新项目/新文件可能进入人工审核

审核期间不要重复上传同一个文件。等邮件或项目 Dashboard 状态变化。

---

## 4. 审核通过后

拿到项目链接，例如：

```
https://www.curseforge.com/minecraft/mc-mods/create-oddities
```

然后：

1. 把 CurseForge 链接补进 README 的 Links 区
2. 把 CurseForge 项目 ID 补进 `docs/metadata.md`（可选）
3. MC百科条目里增加 CurseForge 下载链接（你自己改）
4. 如果你愿意，再发一个 GitHub Release `v0.1.1`，附件用同一个 jar

---

## 5. 常见卡点

### 创建项目按钮灰色

- 没同意 Author Terms
- 邮箱没验证
- 作者资料没填完

### 上传文件后一直 Pending

- 新作者/新项目常见，等 1–3 个工作日
- 检查邮箱有没有 CurseForge 的补充材料要求

### 文件被拒

常见原因：

- jar 里缺少 `META-INF/neoforge.mods.toml`
- 项目描述与 jar 内容不符
- 使用了别人的素材/代码但没有许可说明
- 版本号或依赖写错

我们的 jar 已包含：

```
META-INF/neoforge.mods.toml
create_oddities.mixins.json
dev/createoddities/*.class
icon.png
```

### 依赖里搜不到 Create

在依赖搜索框输入项目名 `Create`，选择 CurseForge 上的 Create 项目；不要选成 `Create: Aeronautics`。

---

## 6. 项目创建后要记下的信息

| 信息 | 值 |
| --- | --- |
| Project name | Create Oddities |
| Slug | `create-oddities` |
| Project ID | 创建后填写 |
| Latest file | `create-oddities-0.1.1.jar` |
| Game version | 1.21.1 |
| Loader | NeoForge |
| License | MIT |
