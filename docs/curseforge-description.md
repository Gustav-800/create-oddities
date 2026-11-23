# Create Oddities

A modular companion for Create: small fixes, careful tweaks, and the occasional new machine.

Create Oddities is an early-development addon collection for Create and its addons. Each feature is a separate module; optional integrations only activate when the relevant addon is installed.

## 0.1.2 — Typewriter Keyguard

The current build protects the Create: Aeronautics wireless typewriter:

- Keys bound to the typewriter no longer trigger other mods' shortcuts.
- The typewriter still receives the key event first, so its frequency, sound and animation work normally.
- Esc exits the typewriter; F11 toggles fullscreen.
- Keys outside the typewriter's 68 bindable keys pass through, including F1-F12, Insert, Home, numpad, PrintScreen and media keys.
- F3 debug combos work while F3 is held.

## Requirements

| | |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.228 or newer |
| Create | 6.0.10 or newer |
| Create: Aeronautics / Simulated | Optional |
| Side | Client |

Only Create is required. If Create: Aeronautics / Simulated is not installed, the typewriter module disables itself and the rest of the game continues normally.

## Early development

0.1.2 is an early build. The module loader and configuration menu are planned for 0.2.0; see the [roadmap](https://github.com/Gustav-800/create-oddities/blob/main/ROADMAP.md) for planned modules.

## Links

- Source: https://github.com/Gustav-800/create-oddities
- Issues: https://github.com/Gustav-800/create-oddities/issues
- License: MIT

Development note: parts of the code and documentation were drafted with AI assistance, then reviewed, tested and edited by the author. The mod does not use generative AI at runtime.
