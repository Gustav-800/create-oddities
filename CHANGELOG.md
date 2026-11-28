# Changelog

## 0.2.1 — 2026-11-28

### Added

- Create: Crafts & Additions wire nodes are preserved when printing Create blueprints.
- CCA wire endpoints are rotated/mirrored with the blueprint in both the deployed hologram and the printed result.
- CCA is an optional dependency; the cable module and its mixins are skipped when it is absent.
- Common config: `config/create_oddities-common.toml` with `modules.cable_blueprint.enabled`.

### Changed

- The cable module uses COMMON config so client preview and server printing use the same switch.
- `build.ps1` now writes a fixed jar timestamp; repeated builds of the same sources produce the same SHA-256.
- The shaft encasing fences/railings feature is planned for 0.2.2 as a content module.

### Notes

- 0.2.x is an early-development version.
- The deployed-blueprint hologram and the printed result support mirrored and rotated blueprints.
- The shaft encasing feature is planned for 0.2.2. It is a technical block and does not appear in the creative inventory.

## 0.2.0 — 2026-11-28

### Added

- Module framework: each feature is an `OddityModule` with availability, enable/disable and initialization checks.
- Client config `config/create_oddities-client.toml` and server config `config/create_oddities-server.toml`.
- Per-sensor unit selector on the Aeronautics velocity sensor: side value box opens Create's native `ValueSettingsScreen`.
- Units: m/s, km/h and knots. The setting is stored per block entity and synced through Create's value-settings packet.
- Speed-display conversion covers the max-speed value box, the display-link output and the Cyber Goggles tooltip.
- Max-speed setting limits are unit-specific: 50 m/s, 180 km/h, 100 kn; the slider shows six equal milestones.
- Module manager logs disabled/unavailable modules and isolates initialization failures.

### Changed

- Typewriter keyguard migrated into the module framework.
- Typewriter can be disabled via `modules.typewriter_keyguard.enabled`.
- Speed-display module is server-side data; `modules.speed_display.default_unit` only supplies the default for new sensors.
- Speed sensor logic now runs on both sides; typewriter code remains client-only.

### Notes

- CCA cable blueprint fix moved to 0.2.1.
- CBCAT already provides `disablePhysicRework` (default true); use that upstream config instead of a duplicate toggle.
- Because the vanilla max-speed value is an integer, 100 kn (51.44 m/s) uses 51 m/s for the redstone full-scale threshold.
- Create Oddities config-menu labels are localized through a small Catnip label Mixin; other mods' labels are untouched.
- Content modules will freeze their enabled state at world load; the lifecycle contract is documented in `docs/MODULE-LIFECYCLE.md`.
- This is still an early-development build; the module loader/config API may change before 1.0.0.

## 0.1.2 — 2026-11-22

### Fixed

- Keys outside the typewriter's 68 bindable keys are now passed through while the
  typewriter is active. This restores screenshots, fullscreen and the other
  function-key actions. The bindable set remains the 68 keys exposed by Simulated's
  key editor; F1-F12, Insert, Home, numpad, PrintScreen and media keys are not captured.
- F3 debug combos now work: while F3 is held, the second key is passed through
  instead of being captured by the typewriter.
- Esc still exits the typewriter; F11 still toggles fullscreen.

### Changed

- Updated `neoforge.mods.toml` description to the 0.1.2 behavior.
- Updated README, issue template and release metadata for the current dependency
  policy (only Create is required; Create: Aeronautics / Simulated is optional).

## 0.1.1 — 2026-11-21

### Fixed

- F11 now toggles fullscreen while the typewriter is active. The event is handled
  at the head of `KeyboardHandler#keyPress` and cancelled, so Simulated's later
  handler cannot consume it.
- F3 remains explicitly available for debug combos; the old F11 constant
  accidentally pointed at F3 (292) instead of F11 (300).
- F2 is no longer a special exception; the declared allowed keys are Esc, F3 and F11.

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
