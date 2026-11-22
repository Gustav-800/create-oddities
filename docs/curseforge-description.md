# CurseForge description / 项目简介

## 短简介（Summary，约 150 字符）

A modular companion for Create: fixes, tweaks, and the occasional new machine. 0.1.2 includes the wireless typewriter keyguard with F1-F12 pass-through; only Create is required.

## 中文短简介

机械动力及其附属的模块化补丁与扩展。0.1.2 包含无线红石打字机按键保护，F1-F12 放行；唯一硬前置为 Create。

---

# Create Oddities

**A modular companion for Create: small fixes, careful tweaks, and the occasional new machine.**

Create Oddities is an early-development addon collection for Create and its addons. Instead of being tied to a single addon, it is organized as independent modules: each module can be enabled or disabled, and optional integrations are only active when the relevant addon is installed.

## Current release: 0.1.2

This build contains the **Typewriter Keyguard** module:

- While the Create: Aeronautics wireless typewriter owns the keyboard, keys bound to the typewriter no longer trigger other mods' shortcuts.
- The key event is handed to Simulated's typewriter handler first, then cancelled before vanilla key mappings and other mods can process it.
- **Esc** exits the typewriter, **F11** still toggles fullscreen, **F1-F12** pass through, and **F3** debug combos work while typing.
- The typewriter's own key-editor screen is left alone.

F11 is handled explicitly at the keyboard entry point and cancelled, so Simulated's later key handler cannot consume it. F1-F12 are passed through; while F3 is held, the second key of a debug combo is passed through as well.

## Requirements

| | |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.228 or newer |
| Create | 6.0.10 or newer |
| Create: Aeronautics / Simulated | Optional |
| Side | Client |

Only **Create** is required. If Create: Aeronautics / Simulated is not installed, the typewriter module disables itself and the rest of the game continues normally.

## Early development

0.1.2 is an early build. The module loader, configuration menu and the rest of the roadmap are still in development. Expect behavior and file structure to change between 0.1.x releases.

Planned modules include:

- CCA cable connections preserved when printing Create blueprints;
- a config toggle to disable CBCAT's internal ballistics changes;
- survival recipes for CBCMW items;
- smoother transmission animation at changing RPM;
- dynamic stress for pistons, bearings and cranes based on moved structure size/mass;
- stress handling for the Aeronautics rotary bearing;
- a chain-powered piston for cannon loading;
- additional speed units for the Aeronautics speed sensor.

## Links

- Source: https://github.com/Gustav-800/create-oddities
- Issues: https://github.com/Gustav-800/create-oddities/issues
- License: MIT

## Development disclosure

Parts of the code and documentation were drafted with generative AI assistance, then reviewed, tested and edited by the author. The mod does not call generative AI at runtime and contains no AI-generated in-game textures, models or sounds. The project icon is composed from an in-game screenshot and a blueprint-style background.

---

# 机械动力：奇思妙想

**机械动力及其附属的模块化补丁与扩展：修复小问题，调整旧机制，偶尔加点新东西。**

Create Oddities 是面向 Create 及其附属的早期开发模组集合。它按模块组织，每个模块都可以单独启用或禁用；只有安装对应附属时，相关联动才会生效。

## 当前版本 0.1.2

本版本包含 **打字机按键保护** 模块：

- 无线红石打字机接管键盘时，绑定的按键不再触发其他模组的快捷键；
- 按键先交给 Simulated 的打字机处理器，再在进入原版按键映射和其他模组之前取消；
- 打字时 **Esc** 退出打字机，**F11** 仍可切换全屏，**F1-F12** 全部放行，**F3** 调试组合键可用；
- 不接管打字机自己的按键编辑界面。

## 环境要求

| | |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.228 或更高 |
| Create | 6.0.10 或更高 |
| Create: Aeronautics / Simulated | 可选 |
| 安装侧 | 客户端 |

唯一硬前置是 **Create**。没有安装 Create: Aeronautics / Simulated 时，打字机模块会自动禁用，游戏仍可正常启动。

## 早期开发说明

0.1.2 是早期版本。模块加载器、配置菜单和路线图中的其他内容仍在开发中，0.1.x 各版本之间可能发生变化。

## 链接

- 源码：https://github.com/Gustav-800/create-oddities
- 问题反馈：https://github.com/Gustav-800/create-oddities/issues
- 许可：MIT
