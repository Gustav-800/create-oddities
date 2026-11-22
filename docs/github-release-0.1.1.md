# Create Oddities 0.1.1

First **Create Oddities** release. This build contains the **Typewriter Keyguard** module.

## Fixed

- F11 now toggles fullscreen while the typewriter is active.
- F11 uses the correct GLFW keycode (`300`); the previous build checked `292` (F3).
- Allowed keys are now **Esc**, **F3** and **F11**:
  - Esc exits the typewriter;
  - F3 keeps debug combos usable;
  - F11 toggles fullscreen.
- F2 is no longer a special exception.

## Changed

- Project renamed to **Create Oddities / 机械动力：奇思妙想**.
- Internal mod id changed from `every_key_a_gem` to `create_oddities`.
- Create is now the only required mod dependency; Create: Aeronautics / Simulated is optional.
- The typewriter module disables itself when Simulated is not installed.

## Requirements

| | |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.228 or newer |
| Create | 6.0.10 or newer |
| Create: Aeronautics / Simulated | Optional |
| Side | Client |

## Installation

1. Install Minecraft 1.21.1, NeoForge 21.1.228+ and Create 6.0.10+.
2. Optional: install Create: Aeronautics for the typewriter module.
3. **Remove the old `create-every-key-a-gem-1.0.0.jar`** if present. The mod id changed, so the old and new jars must not be installed together.
4. Put `create-oddities-0.1.1.jar` into `mods/`.
5. Restart the game completely.

## Notes

- 0.1.1 is an early development build. The module loader, configuration menu and the rest of the roadmap are still in development.
- Client-only typewriter module; no extra packets are sent to the server.
- See the [roadmap](https://github.com/Gustav-800/create-oddities/blob/main/ROADMAP.md) for planned modules.

## File

`create-oddities-0.1.1.jar` — 121,739 bytes
SHA-256: `9E7786F32B79FE42843851513D5A7FD2FEEE3ECB039973AA58BE144F541A21C9`
