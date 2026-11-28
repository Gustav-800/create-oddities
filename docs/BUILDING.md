# 本地构建 / Building

0.2.0 的正式构建使用 `build.ps1`，因为项目直接引用了 Create、Ponder 和 Simulated 的类。

## 命令

```powershell
.\build.ps1 `
  -MinecraftDir "D:\PCL 正式版 2.10.3\.minecraft" `
  -ModsDir "D:\PCL 正式版 2.10.3\.minecraft\versions\航空学整合\mods"
```

`-ModsDir` 必须包含：

- `create-1.21.1-*.jar`
- `create-aeronautics-bundled-*.jar`

脚本会从这两个 jar 中提取编译用的 Ponder / Simulated 内嵌 jar，产物在：

```
build/libs/create-oddities-0.2.0.jar
```

## 发布哈希

0.2.1 的正式发布 jar SHA-256：

```
0580DDD1262C174D90CEE501DA10A283445B951A7A61CC696EAA3953645553DD
```

0.2.1 起 `build.ps1` 使用固定 jar 时间戳；相同源码重复构建得到相同 SHA-256。0.2.0 的说明仅适用于旧构建。

## Gradle 说明

当前 `build.gradle` 只配置了 NeoForge，没有声明 Create / Ponder / Simulated 的编译依赖。发布构建请使用上面的 `build.ps1`；Gradle 仅用于 IDE 基础导入。
