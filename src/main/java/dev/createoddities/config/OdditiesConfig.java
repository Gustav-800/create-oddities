package dev.createoddities.config;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Client-side configuration for Create Oddities.
 *
 * <p>0.2.0 uses NeoForge's config system as the stable baseline. If Create
 * exposes a stable public config-menu API later, the same values can be
 * surfaced there without changing the module code.</p>
 */
public final class OdditiesConfig {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.BooleanValue TYPEWRITER_ENABLED;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("modules");
        builder.push("typewriter_keyguard");
        TYPEWRITER_ENABLED = builder
                .comment(
                        "Enable the wireless typewriter keyguard module.",
                        "Requires Create: Aeronautics / Simulated. If Simulated is absent, this module stays inactive."
                )
                .define("enabled", true);
        builder.pop();
        builder.pop();

        SPEC = builder.build();
    }

    private OdditiesConfig() {
    }
}
