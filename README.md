# Create Oddities / 机械动力：奇思妙想

**A modular companion for Create: small fixes, careful tweaks, and the occasional new machine.**

**机械动力及其附属的模块化补丁与扩展：修复小问题，调整旧机制，偶尔加点新东西。**

> 这个项目始于无线红石打字机的按键冲突修复。从 0.1.0 起，它以 **Create Oddities / 机械动力：奇思妙想** 的名字继续扩展。

[![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.1-62b47a.svg)](https://www.minecraft.net/)
[![NeoForge](https://img.shields.io/badge/NeoForge-21.1.228%2B-f16436.svg)](https://neoforged.net/)
[![Create](https://img.shields.io/badge/Create-6.0.10%2B-c89b5a.svg)](https://modrinth.com/mod/create)
[![CurseForge](https://img.shields.io/badge/CurseForge-Create%20Oddities-f16436)](https://www.curseforge.com/minecraft/mc-mods/create-oddities)

**下载 / Download:** [GitHub Releases](https://github.com/Gustav-800/create-oddities/releases) · [CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-oddities)

## 项目定位 / What this is

Create Oddities 是面向 **Create 本体及其附属** 的模块化补丁集合，而不是某一个附属的专属修复模组。

它的目标包括三类内容：

- **修复**：处理 Create 或附属在特定组合下出现的漏洞、数据丢失和兼容问题；
- **调整**：在不破坏原有玩法的前提下，让某些机制更平滑、更合理；
- **拓展**：在 Create 的体系内增加少量新组件或新行为。

所有功能按模块组织。每个模块都可以单独启用/禁用并调整参数；不安装对应附属时，相关模块自动关闭，游戏仍可正常启动。

## 当前状态 / Status

| 版本 | 状态 | 内容 |
| --- | --- | --- |
| **1.0.0** | 已发布（旧名 *Create: Every Key a Gem* / *机械动力：字字珠玑*） | 打字机按键保护。客户端模组，需要 Simulated |
| **0.1.2** | 当前开发版 | 68 个可绑定键之外全部放行；F3 组合键可用；Esc 退出；F11 全屏；仅 Create 前置，Simulated 可选 |
| **0.1.1** | 已发布 | 修复 F11 键码；Esc/F3/F11 放行；内部 id `create_oddities`、仅 Create 前置、Simulated 可选 |
| **0.1.0** | 早期开发版 | 首个 Create Oddities 构建：更名、内部 id 迁移、依赖调整 |

> **重要**：1.0.0 的 jar 仍然是旧名字、旧依赖（需要 Simulated）。它不是完整的 Create Oddities。0.1.1 已发布；0.1.2 是当前开发版，68 个可绑定键之外全部放行，并修正 F3 组合键。
>
> 0.1.x 起内部 mod id 改为 `create_oddities`。它和 1.0.0 的 `every_key_a_gem` 会被加载器视为两个不同模组；升级时必须先移除旧 jar，不能同时安装。

## 设计原则 / Design rules

1. **唯一硬前置是 Create 本体。**  
   Create: Aeronautics / Simulated、Create: Crafts & Additions、Create Big Cannons、CBCAT、CBCMW 等全部是**可选联动**。

2. **缺附属不崩游戏。**  
   对应附属不存在时，相关模块自动禁用；其余模块照常工作。

3. **一切可配置。**  
   每个模块都有启用/禁用开关和参数；默认值安全。计划接入 Create 的配置菜单，让玩家在游戏内调整，而不必手改文件。

4. **客户端/服务端边界清晰。**  
   纯客户端模块（如打字机按键保护）不要求服务端安装；涉及方块、配方和动力网络的模块会明确标注安装侧。

5. **不复制附属代码。**  
   优先使用公开 API、反射和软依赖；不打包、不重编译其他模组的代码或素材。

## 模块 / Modules

| 模块 | 状态 | 前置 | 说明 |
| --- | --- | --- | --- |
| **Typewriter Keyguard** | ✅ 1.0.0（旧名）/ 0.1.2 当前 | Create + 可选 Simulated | 打字机激活时接管键盘；68 个可绑定键之外放行，F3 组合键可用，Esc 退出，F11 全屏 |
| **Cable Blueprint Fix** | 🟡 原型 | Create + 可选 Create: Crafts & Additions | 让 CCA 电线在 Create 蓝图打印后保留，并支持旋转/镜像；原型见 [create-cable-guard](https://github.com/Gustav-800/create-cable-guard) |
| **Soft Kinetics** | 🟡 研究/原型 | Create | 把应力网络的硬过载改为连续降速；原型见 [create-mechanical-principles](https://github.com/Gustav-800/create-mechanical-principles) |
| **CBC/CBCAT/CBCMW 调整** | ⬜ 计划 | Create + 对应附属 | 内弹道开关、生存配方、火炮装载辅助等 |
| **Aeronautics 扩展** | ⬜ 计划 | Create + 可选 Aeronautics | 旋转轴承应力、速度单位等 |

## 路线图 / Roadmap

详细状态、依赖和目标版本见 [ROADMAP.md](ROADMAP.md)。

| # | 类型 | 内容 | 状态 |
| --- | --- | --- | --- |
| 1 | 修复 | 打字机使用时被其他模组热键拦截 | ✅ 1.0.0（旧名） |
| 2 | 修复 | CCA 线缆无法用 Create NBT 蓝图打印 | 🟡 原型 |
| 3 | 调整 | 传动组件在转速变化时的动画更平滑 | ⬜ 计划 |
| 4 | 调整 | 禁用 CBCAT 对 CBC 火炮内弹道学的修改，回归原版行为 | ⬜ 计划 |
| 5 | 调整 | 为 CBCMW 物品添加生存配方（可能新增铸模） | ⬜ 计划 |
| 6 | 调整 | 动力活塞/轴承/起重机按移动规模与质量计算应力 | ⬜ 计划 |
| 7 | 调整 | 类似 6，但对象是 Aeronautics 旋转轴承 | ⬜ 计划 |
| 8 | 拓展 | 链式动力活塞（横向绳索滑轮，暂定 16 格） | ⬜ 计划 |
| 9 | 拓展 | Aeronautics 速度传感器支持 km/h、节等单位 | ⬜ 计划 |

## 兼容性 / Compatibility

| 项目 | 要求 |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.228 或更高 |
| Create | 6.0.10 或更高 |
| 必需前置 | **仅 Create**（0.1.x 起） |
| 可选联动 | Create: Aeronautics / Simulated、Create: Crafts & Additions、Create Big Cannons、CBCAT、CBCMW |

**1.0.0 例外**：当前已发布的打字机模块需要 Create: Aeronautics（内含 Simulated 1.3.2+）。0.1.x 已把 Simulated 改为可选。

## 配置 / Configuration

0.1.x 目标：

- 每个模块的 `enabled` 开关；
- 模块参数（阈值、倍率、单位、配方开关等）；
- 与 Create 配置菜单兼容的游戏内入口；
- 配置文件缺失或损坏时回退到安全默认值。

**0.1.2 目前也没有配置文件。** 打字机模块在检测到 Simulated 时启用。

## 安装 / Installation

### 1.0.0（旧版发布，legacy）

1. Minecraft 1.21.1 + NeoForge 21.1.228+
2. Create + Create: Aeronautics（及其前置 Sable）
3. 把 `create-every-key-a-gem-1.0.0.jar` 放进 `mods/`
4. 完全重启游戏

### 0.1.2（开发中）

1. 先移除 1.0.0 的 `create-every-key-a-gem-1.0.0.jar`（内部 id 不同，不能共存）
2. 只需要 Create；Aeronautics / Simulated 可选
3. 把 `create-oddities-0.1.2.jar` 放进 `mods/`
4. 完全重启游戏

## 构建 / Building

### Gradle

```bash
./gradlew build
# Windows
.\gradlew.bat build
```

### PowerShell（本机验证过的备用方式）

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File .\build.ps1 `
  -MinecraftDir 'C:\path\to\.minecraft'
```

Gradle 构建需要能访问 `maven.neoforged.net`；如果网络不通，用 PowerShell 脚本针对本机已安装的 NeoForge 进行编译。

## 链接 / Links

- GitHub：<https://github.com/Gustav-800/create-oddities>
- GitHub Releases：<https://github.com/Gustav-800/create-oddities/releases>
- CurseForge：<https://www.curseforge.com/minecraft/mc-mods/create-oddities>
- MC百科：[机械动力：奇思妙想](https://www.mcmod.cn/class/32518.html)（更名/0.1.2 审核中）
- Cable Guard 原型：<https://github.com/Gustav-800/create-cable-guard>
- Mechanical Principles 原型：<https://github.com/Gustav-800/create-mechanical-principles>

## 许可 / License

MIT。详见 [LICENSE](LICENSE)。

本项目为非官方附属，与 Create、Create: Aeronautics、Simulated、Create: Crafts & Additions、Create Big Cannons 等项目的开发团队无隶属关系。
