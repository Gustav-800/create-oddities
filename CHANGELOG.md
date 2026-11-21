# Changelog

## Unreleased — Create Oddities

### Changed

- Project renamed to **Create Oddities / 机械动力：奇思妙想**.
- Scope expanded from the typewriter keyguard to a modular collection of fixes,
  tweaks, and additions for Create and its addons.
- Added `ROADMAP.md` and updated the README with the module/config/dependency policy.

### Planned

- 2.0.0: module loader, configuration system, Create-only hard dependency.
- 2.1.0: CCA blueprint cable fix, CBCAT ballistics toggle, Aeronautics speed units.
- 2.2.0: smooth transmission animation, CBCMW recipes, dynamic stress model.
- 2.3.0: Aeronautics bearing stress, chain-powered piston.

## 1.0.0 — 2026-10-18

### Added

- Client-side keyguard for the Create: Aeronautics wireless redstone typewriter.
- Keyboard events are claimed at the head of `KeyboardHandler#keyPress` while the
  typewriter is active or while binding a key from a typewriter item.
- Keys are forwarded to Simulated's typewriter handler before being cancelled.
- `Esc` exits the typewriter; `F2` and `F11` remain available.
- Reflection-based Simulated integration: the addon fails open if the installed
  Simulated version changes its API.

### Requirements

- Minecraft 1.21.1
- NeoForge 21.1.228+
- Create: Aeronautics 1.3.2+ (Simulated 1.3.2+)
- Client only
