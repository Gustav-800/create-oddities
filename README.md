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
| **0.2.1** | Beta（CurseForge 已上传；GitHub 待发布） | C1 CCA 线缆蓝图、确定性构建、COMMON 配置收尾 |
| **0.2.0** | Beta（GitHub Pre-release；CurseForge Beta/审核中） | 模块加载器、双端配置、打字机迁移、速度传感器单位选择；CBCAT 使用其自带开关 |
| **0.1.2** | 已发布（GitHub + CurseForge） | 68 个可绑定键之外全部放行；F3 组合键可用；Esc 退出；F11 全屏；仅 Create 前置，Simulated 可选 |
| **0.1.1** | 已发布 | 修复 F11 键码；Esc/F3/F11 放行；内部 id `create_oddities`、仅 Create 前置、Simulated 可选 |
| **0.1.0** | 早期开发版 | 首个 Create Oddities 构建：更名、内部 id 迁移、依赖调整 |

> **重要**：1.0.0 的 jar 仍然是旧名字、旧依赖（需要 Simulated）。它不是完整的 Create Oddities。0.1.1 与 0.1.2 均已发布；0.2.0 已在 GitHub 发布，并已上传 CurseForge Beta（审核中），开始模块化并提供速度传感器单位选择。
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
   0.2.0 起模组本体为双端。打字机按键保护是纯客户端模块；速度传感器单位数据在服务端，需要双端安装。涉及方块、配方和动力网络的模块会明确标注安装侧。

5. **不复制附属代码。**  
   优先使用公开 API、反射和软依赖；不打包、不重编译其他模组的代码或素材。

## 模块 / Modules

| 模块 | 状态 | 前置 | 说明 |
| --- | --- | --- | --- |
| **Typewriter Keyguard** | ✅ 1.0.0（旧名）/ 0.2.0 当前 / 客户端 | Create + 可选 Simulated | 打字机激活时接管键盘；68 个可绑定键之外放行，F3 组合键可用，Esc 退出，F11 全屏；`modules.typewriter_keyguard.enabled` |
| **Cable Blueprint Fix** | 🟢 0.2.1 | Create + 可选 Create: Crafts & Additions | 让 CCA 电线在 Create 蓝图打印后保留，并支持旋转/镜像；开关为 COMMON 配置 `modules.cable_blueprint.enabled` |
| **Soft Kinetics** | 🟡 研究/原型 | Create | 把应力网络的硬过载改为连续降速；原型见 [create-mechanical-principles](https://github.com/Gustav-800/create-mechanical-principles) |
| **Speed Display** | 🟢 0.2.0 / 双端 | Create + 可选 Aeronautics | 每个速度传感器可在侧面切换 m/s、km/h、节；最大速度设置上限分别为 50 / 180 / 100；显示链接和护目镜同步 |
| **Create 调整** | ⬜ 计划 | Create；个别功能联动附属 | 铁轨朝向、传动杆穿栅栏、伪装方块、蓝图预览、动力臂 |
| **CBC/CBCAT/CBCMW 调整** | ⬜ 计划 | Create + 对应附属 | 内弹道、发射药、生存配方、装卸过渡 |
| **Sable / Aeronautics 组件** | ⬜ 计划 | Create + 可选 Sable / Aeronautics | 锁链轴承、铰链/绞盘、绳索传动轮、摇杆、高度计 |

## 路线图 / Roadmap

详细状态、依赖和目标版本见 [ROADMAP.md](ROADMAP.md)；上游源码/许可审计见 [docs/UPSTREAM-LICENSES.md](docs/UPSTREAM-LICENSES.md)。

| 版本 | 主题 | 主要内容 |
| --- | --- | --- |
| 0.2.0 | 模块化基础与速度单位 | 模块/配置、打字机迁移、速度传感器单位选择；CBCAT 开关使用其上游配置 |
| 0.2.1 | CCA 线缆蓝图 | CCA 蓝图部署全息/打印/旋转/镜像、确定性构建、COMMON 配置收尾 |
| 0.3.0 | Create 内容与视觉 | 伪装斜坡/机壳、蓝图等轴预览、传动动画平滑 |
| 0.4.0 | CBC 火炮 | 自由选择发射药、半格高能发射药、装卸过渡、CBCMW 配方 |
| 0.5.0 | Aeronautics 交互与能源 | 红石磁铁 FE、高度计转盘、鼠标摇杆 |
| 0.6.0 | Sable 机械结构 | 锁链轴承、铰链/绞盘/万向铰链、绳索传动轮、轴承应力 |
| 0.7.0 | 动力学与动态结构 | 动态应力、链式动力活塞、不完整方块组合 |
| 0.8.0 | 动力臂与跨结构 | 序列装配、跨 Sable 物理结构交互 |

## 兼容性 / Compatibility

| 项目 | 要求 |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.228 或更高 |
| Create | 6.0.10 或更高 |
| 必需前置 | **仅 Create**（0.1.x 起） |
| 可选联动 | Create: Aeronautics / Simulated、Create: Crafts & Additions、Create Big Cannons、CBCAT、CBCMW |

**1.0.0 例外**：当前已发布的打字机模块需要 Create: Aeronautics（内含 Simulated 1.3.2+）。0.1.x 已把 Simulated 改为可选。

## 本地构建 / Building

0.2.0 使用项目根目录的 `build.ps1` 构建（需要指向包含 Create / Aeronautics 的 `mods` 目录）。详细命令见 [docs/BUILDING.md](docs/BUILDING.md)。

## 模块生命周期 / Module lifecycle

新物品/方块类模块不能在运行中注销；配置修改需要退出世界再重进。详细约定见 [docs/MODULE-LIFECYCLE.md](docs/MODULE-LIFECYCLE.md)。

## 配置 / Configuration

0.2.1 使用三个 NeoForge 配置：

- `config/create_oddities-client.toml`：打字机模块
- `config/create_oddities-common.toml`：CCA 线缆蓝图模块（`modules.cable_blueprint.enabled`）
- `config/create_oddities-server.toml`：速度传感器单位模块

| 配置项 | 默认 | 说明 |
| --- | --- | --- |
| `modules.typewriter_keyguard.enabled` | `true` | 打字机按键保护 |
| `modules.speed_display.enabled` | `true` | 服务端启用速度传感器单位选择 |
| `modules.speed_display.default_unit` | `METERS_PER_SECOND` | 新放置传感器的默认单位；可选 `KILOMETERS_PER_HOUR`、`KNOTS` |

每个传感器的单位保存在方块实体 NBT 中，由侧面选择行为同步；全局配置只提供默认值。配置在游戏启动时加载，模块缺失对应附属时自动禁用。

## 安装 / Installation

### 1.0.0（旧版发布，legacy）

1. Minecraft 1.21.1 + NeoForge 21.1.228+
2. Create + Create: Aeronautics（及其前置 Sable）
3. 把 `create-every-key-a-gem-1.0.0.jar` 放进 `mods/`
4. 完全重启游戏

### 0.2.1（Beta）

1. 先移除旧版 `create-oddities-0.1.x.jar` / `0.2.0`
2. 需要 Create；Aeronautics、CCA 可选
3. 把 `create-oddities-0.2.1.jar` 放进 `mods/`
4. 完全重启游戏

### 0.2.0（Beta / Pre-release）

1. 先移除 1.0.0 的 `create-every-key-a-gem-1.0.0.jar`（内部 id 不同，不能共存）；再替换旧的 `create-oddities-0.1.x.jar`
2. 只需要 Create；Aeronautics / Simulated、Create: Crafts & Additions 可选
3. 把 `create-oddities-0.2.0.jar` 放进 `mods/`
4. 完全重启游戏

服务端安装：速度传感器单位模块需要服务端也安装本模组；打字机按键保护只在客户端生效。

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
- GitHub Release v0.1.2：<https://github.com/Gustav-800/create-oddities/releases/tag/v0.1.2>
- CurseForge：<https://www.curseforge.com/minecraft/mc-mods/create-oddities>
- MC百科：[机械动力：奇思妙想](https://www.mcmod.cn/class/32518.html)（更名/0.1.2 审核中）
- Cable Guard 原型：<https://github.com/Gustav-800/create-cable-guard>
- Mechanical Principles 原型：<https://github.com/Gustav-800/create-mechanical-principles>

## 许可 / License

MIT。详见 [LICENSE](LICENSE)。

本项目为非官方附属，与 Create、Create: Aeronautics、Simulated、Create: Crafts & Additions、Create Big Cannons 等项目的开发团队无隶属关系。
