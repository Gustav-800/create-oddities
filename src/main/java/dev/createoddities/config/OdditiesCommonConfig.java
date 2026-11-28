package dev.createoddities.config;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Configuration shared by both sides. The CCA blueprint fix runs on the server
 * for printing but also on the client for blueprint preview rotation.
 */
public final class OdditiesCommonConfig {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.BooleanValue CABLE_BLUEPRINT_ENABLED;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("modules");
        builder.push("cable_blueprint");
        CABLE_BLUEPRINT_ENABLED = builder
                .comment(
                        "Keep Create: Crafts & Additions wire nodes when printing Create blueprints.",
                        "在使用 Create 蓝图打印时保留机械动力：创想附加的电线节点。",
                        "Requires Create: Crafts & Additions. If it is absent, this module stays inactive.",
                        "需要机械动力：创想附加；缺失时该模块自动停用。"
                )
                .define("enabled", true);
        builder.pop();
        builder.pop();

        SPEC = builder.build();
    }

    public static boolean isCableBlueprintEnabled() {
        try {
            return CABLE_BLUEPRINT_ENABLED.get();
        } catch (RuntimeException ignored) {
            return false;
        }
    }

    private OdditiesCommonConfig() {
    }
}
