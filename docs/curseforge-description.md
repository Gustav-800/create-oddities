# Create Oddities

A modular companion for Create: small fixes, careful tweaks, and the occasional new machine.

Create Oddities is an early-development addon collection for Create and its addons. Each feature is a separate module; optional integrations only activate when the relevant addon is installed.

## 0.2.0 — Module framework and velocity-sensor units

0.2.0 adds a module/config framework and a per-sensor unit selector for the Create: Aeronautics velocity sensor:

- Typewriter keyguard: keys bound to the wireless typewriter no longer trigger other mods' shortcuts. Keys outside the 68 bindable keys pass through; Esc exits; F11 toggles fullscreen; F3 combos work.
- Velocity sensor units: each sensor can be switched on its side between `m/s`, `km/h` and `kn`.
- The setting is per block entity and syncs to the max-speed value box, display links and Cyber Goggles.
- Max-speed setting limits are unit-specific: `50 m/s`, `180 km/h`, `100 kn`; the slider shows six equal milestones.
- Create config menu entries for Create Oddities are localized; behavior modules can be enabled/disabled from the native config screen.
- Speed sensor logic is server-side data; the typewriter remains client-only.

## Requirements

| | |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.228 or newer |
| Create | 6.0.10 or newer |
| Create: Aeronautics / Simulated | Optional |
| Side | Client + Server |

Only Create is required. Without Create: Aeronautics / Simulated, the typewriter and velocity-sensor modules disable themselves and the rest of the game continues normally.

## Config

- `config/create_oddities-client.toml`
  - `modules.typewriter_keyguard.enabled`
- `config/create_oddities-server.toml`
  - `modules.speed_display.enabled`
  - `modules.speed_display.default_unit` (default for newly placed sensors)

Existing sensors keep their own stored unit; changing the default does not rewrite them.

## Notes

- CCA cable blueprint compatibility is planned for 0.2.1.
- CBCAT already provides `disablePhysicRework` (default true); use that upstream setting.
- The max-speed setting is a full-scale redstone threshold, not the sensor's measurement range.
- Content modules that add registry entries always register their content; their toggle takes effect after re-entering a world.

## Links

- Source: https://github.com/Gustav-800/create-oddities
- Issues: https://github.com/Gustav-800/create-oddities/issues
- License: MIT

Development note: parts of the code and documentation were drafted with AI assistance, then reviewed, tested and edited by the author. The mod does not use generative AI at runtime.
