# Create Oddities 0.1.2

Non-bindable key and F3 debug-combo hotfix.

## Fixed

- **Keys outside the 68 bindable set** now pass through while the typewriter is active, including F1-F12, Insert, Home, numpad, PrintScreen and media keys. Screenshots, fullscreen and the other function-key actions work again.
- **F3 debug combos** now work: while F3 is held, the second key of the combo is passed through instead of being captured by the typewriter.
- **Esc** still exits the typewriter.
- **F11** still toggles fullscreen and cancels the event so Simulated cannot consume it.

## Changed

- Updated `neoforge.mods.toml` description for 0.1.2.
- Updated README, issue template and metadata for the current dependency policy: only Create is required; Create: Aeronautics / Simulated is optional.
- Archived old 0.1.1-era platform docs under `docs/legacy/`.

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
3. If upgrading from 1.0.0, remove `create-every-key-a-gem-1.0.0.jar` (different mod id).
4. Replace any older `create-oddities-0.1.x.jar` with `create-oddities-0.1.2.jar`.
5. Restart the game completely.

## Notes

- 0.1.2 is an early development build. The module loader and configuration menu are still planned for 0.2.0.
- Client-only typewriter module; no extra packets are sent to the server.

## File

`create-oddities-0.1.2.jar` — 122,290 bytes  
SHA-256: `5B8AF168F6FB0DCD7BF22146C1E3E0ADD0155ED919144321449EB0F6786DD770`
