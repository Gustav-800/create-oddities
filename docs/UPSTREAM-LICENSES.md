# 上游源码与许可证审计

最后更新：2026-11-27

本文件只做工程判断，不构成法律意见。复制任何上游代码或素材前，必须重新核对对应仓库的 LICENSE。

## 结论速览

| 分类 | 模组 |
| --- | --- |
| 代码 MIT，源码可用 | Create、Create: Aeronautics / Simulated、Create: Crafts & Additions、Create Big Cannons、CBC: Modern Warfare、Create: Bits 'n' Bobs |
| 代码 MIT，但素材受限 | Create、Create: Aeronautics / Simulated、Create Big Cannons |
| 源码可见，但许可证受限 | Sable（PolyForm Shield 1.0.0）、Create: Copycats+（All Rights Reserved） |
| 闭源，无公开源码 | CBCAT / Create Big Cannons: Advanced Technologies |

**最重要的三条：**

1. **CBCAT 闭源**，涉及它的功能只能黑盒测试、Mixin/反射，不能参考源码。
2. **Sable 是 source-available，不是 OSI 开源**；可以读，但不能把代码复制进本项目。
3. **Create: Copycats+ 是 All Rights Reserved**；伪装方块功能必须独立实现，不能复制代码或素材。

## 逐项清单

### Create / 机械动力

- 仓库：https://github.com/Creators-of-Create/Create
- 代码：**MIT**
- 素材：**All Rights Reserved**（`LICENSE.md` 中列出的 assets 目录）
- 源码可得：**是**
- 影响：可以阅读、修改代码并保留 MIT 声明；不能直接复用纹理、模型、声音。功能 1/5/8/13 以及 D1/D2 的 Create 侧实现可基于源码。
- 许可原文：https://github.com/Creators-of-Create/Create/blob/HEAD/LICENSE.md

### Create: Aeronautics / Simulated

- 仓库：https://github.com/Creators-of-Aeronautics/Simulated-Project
- 代码：**MIT**
- 素材：**All Rights Reserved**（`aeronautics/.../assets/`、`simulated/.../assets/`）
- 源码可得：**是**
- 影响：可以阅读 Aeronautics/Simulated 代码；不能复制模型、纹理和声音。功能 2/6/7/9/10/12 的 Aeronautics 侧逻辑可基于源码。
- 许可原文：https://github.com/Creators-of-Aeronautics/Simulated-Project/blob/main/LICENSE.md

### Sable

- 仓库：https://github.com/ryanhcode/sable
- 代码：**PolyForm Shield License 1.0.0**
- 源码可得：**是，但受限**
- 影响：这是 source-available 许可证，不是 OSI 开源。可以阅读代码、调用公开 API；不能把 Sable 代码复制进本项目，也不能用它的代码制作竞争性物理库。功能 7/9/14/15 必须优先走公开 API，必要时才用反射，且不做代码搬运。
- 许可信息：https://github.com/ryanhcode/sable（`gradle.properties` 标注 `license=PolyForm Shield License 1.0.0`）

### Create: Crafts & Additions / CCA

- 仓库：https://github.com/mrh0/createaddition
- 代码：**MIT**
- 源码可得：**是**
- 影响：C1 线缆蓝图修复可以直接参考实现；保留 MIT 声明。素材如要复用需单独确认。

### Create Big Cannons / CBC

- 仓库：https://github.com/rbasamoyai/CreateBigCannons
- 代码：**MIT**
- 素材：**CC BY-NC-SA 4.0**（手工纹理/模型）
- 源码可得：**是**
- 影响：B1/B2/B3 的 CBC 侧逻辑可参考源码；代码复用需 MIT 声明；素材是非商业 + 相同方式共享，不能直接塞进 MIT 项目的素材目录。
- 许可原文：https://github.com/rbasamoyai/CreateBigCannons/blob/multiversion/LICENSE.md

### CBCAT / Create Big Cannons: Advanced Technologies

- 来源：https://www.mcmod.cn/class/18707.html
- 状态：**闭源**（MC百科条目标注“闭源”）
- 源码可得：**否**
- 影响：B1 的 CBCAT 内弹道行为、C2 的 CBCAT 开关只能黑盒测试 + Mixin/反射实现。没有源码意味着版本兼容性风险最高，必须做版本矩阵和 fail-open。
- 备注：未找到官方公开 GitHub 仓库。

### CBC: Modern Warfare / CBCMW

- 仓库：https://github.com/GPorubanTKK/CreateBigCannons-ModernWarfare
- 代码：**MIT**（仓库 README 声明开源 MIT；复用前仍需确认 LICENSE 文件）
- 源码可得：**是**
- 影响：B3 的 CBCMW 配方可以参考；素材许可需单独确认。

### Create: Bits 'n' Bobs

- 仓库：https://github.com/Aztech-Modding/Create-Bits-n-Bobs
- 代码：**MIT**（Modrinth 标注；GitHub 仓库当前未附标准 LICENSE 文件）
- 源码可得：**是**
- 影响：S1 锁链传动可以读源码；由于仓库缺少 LICENSE 文件，正式复制代码前建议向作者确认，优先只调用 API。
- Modrinth：https://modrinth.com/mod/create-bits-n-bobs

### Create: Copycats+ / 伪装方块+

- 仓库：https://github.com/copycats-plus/copycats
- 代码/素材：**All Rights Reserved**
- 源码可得：**可见，但不可复用**
- 影响：V1 伪装斜坡/机壳不能复制 Copycats+ 的代码、模型或纹理；只能独立实现，或先取得作者授权。它是 0.3.0 的主要法律/工作量风险。
- Modrinth：https://modrinth.com/mod/copycats

### NeoForge Energy

- 来源：NeoForge 本体
- 许可：开源
- 影响：A2 的红石磁铁 FE 消耗可以直接使用 NeoForge 的 `IEnergyStorage`/能量 API，不需要额外闭源依赖。

## 对版本计划的影响

| 版本 | 功能 | 源码/许可风险 | 处理方式 |
| --- | --- | --- | --- |
| 0.2.0 | C2 CBCAT 开关 | 高：CBCAT 闭源 | 黑盒 + 配置开关 + fail-open；版本不兼容就禁用 |
| 0.2.1 | C1 CCA 线缆 | 低：MIT | 参考 CableGuard 原型，保留 MIT 声明 |
| 0.2.1 | R1/R2 Create 小修 | 低：Create 代码 MIT | 直接基于 Create 源码 |
| 0.3.0 | V1 伪装方块 | 高：Copycats+ ARR | 独立实现，禁止复制代码/素材 |
| 0.3.0 | V2 蓝图预览 | 中：Create 代码 MIT | 基于 Create 源码，素材不复用 |
| 0.4.0 | B1 内弹道/高能发射药 | 高：CBCAT 闭源 | CBC 侧用 MIT 源码；CBCAT 侧黑盒兼容 |
| 0.4.0 | B3 CBCMW 配方 | 低：MIT | 参考源码，确认素材许可 |
| 0.5.0 | A2/A3/A4 | 低：Aeronautics 代码 MIT | 代码可用，素材不复用 |
| 0.6.0 | S1 锁链轴承 | 中：BnB/Aeronautics MIT | 优先 API；确认 BnB LICENSE |
| 0.6.0 | S2/S3/S4 | 高：Sable 受限 | 只用公开 API/反射，不复制代码 |
| 0.7.0 | D1/D2 | 低：Create MIT | 基于源码 |
| 0.7.0 | D3 不完整方块 | 高：Sable 受限 | 公开 API + 大量测试 |
| 0.8.0 | P1 | 低：Create MIT | 基于源码 |
| 0.8.0 | P2 跨 Sable | 高：Sable 受限 | 最后做，严格隔离兼容层 |

## 开发策略

1. **每个可选附属一个兼容层。** 不要让 Sable/CBCAT/Copycats+ 的类型出现在核心模块里。
2. **代码可读，不等于可以复制。** MIT 代码保留声明；ARR/PolyForm/CC BY-NC-SA 代码和素材只作为行为参考。
3. **CBCAT 相关功能默认关闭。** 检测不到类或版本不匹配时，模块直接禁用，不阻止启动。
4. **Sable 相关功能只用公开 API。** 必须反射时集中在一个类里，并加版本检测。
5. **Copycats+ 相关功能从零实现。** 先做小范围原型，确认工作量后再进 0.3.0。
6. **建立版本矩阵。** 至少覆盖 Create、Aeronautics/Simulated、Sable、CBC、CBCAT、CBCMW、BnB、Copycats+ 的 1.21.1 版本。
