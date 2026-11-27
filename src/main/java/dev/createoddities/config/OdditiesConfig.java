package dev.createoddities.config;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Client-only configuration for Create Oddities.
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
                        "启用无线红石打字机按键保护模块。",
                        "Requires Create: Aeronautics / Simulated. If Simulated is absent, this module stays inactive.",
                        "需要 Create: Aeronautics / Simulated；缺失时该模块自动停用。"
                )
                .define("enabled", true);
        builder.pop();
        builder.pop();

        SPEC = builder.build();
    }

    private OdditiesConfig() {
    }
}
