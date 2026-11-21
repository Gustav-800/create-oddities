# Changelog

## 0.1.1 — 2026-11-21

### Fixed

- F11 now toggles fullscreen while the typewriter is active. The event is handled
  at the head of `KeyboardHandler#keyPress` and cancelled, so Simulated's later
  handler cannot consume it.
- F3 remains explicitly available for debug combos; the old F11 constant
  accidentally pointed at F3 (292) instead of F11 (300).
- F2 is no longer a special exception; the declared pass-through keys are F3 and F11.

### Changed

- Project renamed to **Create Oddities / 机械动力：奇思妙想**.
- Internal mod id changed from `every_key_a_gem` to `create_oddities`.
- Create is now the only required mod dependency; Simulated is optional and the
  typewriter module disables itself when Simulated is absent.
- Scope expanded from the typewriter keyguard to a modular collection of fixes,
  tweaks, and additions for Create and its addons.
- Added `ROADMAP.md` and updated the README with the module/config/dependency policy.

### Planned

- 0.2.0: module loader/config, CCA blueprint cable fix, CBCAT ballistics toggle,
  Aeronautics speed units.
- 0.3.0: smooth transmission animation, CBCMW recipes, dynamic stress model.
- 0.4.0: Aeronautics bearing stress, chain-powered piston.

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
