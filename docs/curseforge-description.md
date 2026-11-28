# Create Oddities

A modular companion for Create: small fixes, careful tweaks, and the occasional new machine.

Create Oddities is an early-development addon collection for Create and its addons. Each feature is a separate module; optional integrations only activate when the relevant addon is installed.

## 0.2.1 Beta — CCA blueprint support

0.2.1 adds Create: Crafts & Additions wire support to Create blueprints:

- CCA wire nodes are preserved when a blueprint is printed.
- Wire endpoints rotate and mirror with the blueprint.
- The deployed-blueprint hologram uses the same transform, so the preview and printed result match.
- CCA is optional; when absent, the module and its mixins are skipped.
- Existing typewriter keyguard and velocity-sensor unit features are unchanged.

0.2.0 features remain available:

- Typewriter keyguard: keys bound to the wireless typewriter no longer trigger other mods' shortcuts. Keys outside the 68 bindable keys pass through; Esc exits; F11 toggles fullscreen; F3 combos work.
- Velocity sensor units: each sensor can be switched on its side between `m/s`, `km/h` and `kn`; the setting syncs to the max-speed value box, display links and goggles.
- Unit-specific max-speed limits: `50 m/s`, `180 km/h`, `100 kn`, with six equal milestones.

## Requirements

| | |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.228 or newer |
| Java | 21 |
| Create | 6.0.10 or newer |
| Create: Aeronautics / Simulated | Optional |
| Create: Crafts & Additions | Optional |
| Side | Client + Server |

Only Create is required. Optional modules disable themselves when their addon is absent.

## Config

- `config/create_oddities-client.toml`
  - `modules.typewriter_keyguard.enabled`
- `config/create_oddities-common.toml`
  - `modules.cable_blueprint.enabled`
- `config/create_oddities-server.toml`
  - `modules.speed_display.enabled`
  - `modules.speed_display.default_unit`

The cable module uses COMMON config so the deployed-blueprint preview and the printed result use the same switch.

## Notes

- 0.2.1 is a Beta build.
- Existing sensors keep their own stored unit; changing the server default does not rewrite them.
- Because the vanilla max-speed value is an integer, 100 kn (51.44 m/s) uses 51 m/s for the redstone full-scale threshold.
- The shaft/fence encasing module is planned for 0.3.0 as a technical block without an item.

## Links

- Source: https://github.com/Gustav-800/create-oddities
- Issues: https://github.com/Gustav-800/create-oddities/issues
- License: MIT

Development note: parts of the code and documentation were drafted with AI assistance, then reviewed, tested and edited by the author. The mod does not use generative AI at runtime.
