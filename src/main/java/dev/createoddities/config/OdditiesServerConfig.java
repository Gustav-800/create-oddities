package dev.createoddities.config;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Server-side configuration for the velocity sensor unit behaviour.
 */
public final class OdditiesServerConfig {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.BooleanValue SPEED_DISPLAY_ENABLED;
    public static final ModConfigSpec.EnumValue<SpeedUnit> SPEED_UNIT;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("modules");
        builder.push("speed_display");
        SPEED_DISPLAY_ENABLED = builder
                .comment(
                        "Enable the velocity sensor unit selector on the server.",
                        "在服务端启用速度传感器单位选择器。",
                        "Requires Create: Aeronautics / Simulated. If Simulated is absent, this module stays inactive.",
                        "需要 Create: Aeronautics / Simulated；缺失时该模块自动停用。"
                )
                .define("enabled", true);
        SPEED_UNIT = builder
                .comment(
                        "Default unit for newly placed velocity sensors. Each sensor can override it with the side selector.",
                        "新放置速度传感器的默认单位；每个传感器可用侧面滑块单独覆盖。",
                        "METERS_PER_SECOND = m/s, KILOMETERS_PER_HOUR = km/h, KNOTS = knots.",
                        "METERS_PER_SECOND = 米/秒，KILOMETERS_PER_HOUR = 千米/时，KNOTS = 节。"
                )
                .defineEnum("default_unit", SpeedUnit.METERS_PER_SECOND);
        builder.pop();
        builder.pop();

        SPEC = builder.build();
    }

    public static SpeedUnit defaultUnit() {
        try {
            return SPEED_UNIT.get();
        } catch (RuntimeException ignored) {
            return SpeedUnit.METERS_PER_SECOND;
        }
    }

    public static boolean isSpeedDisplayEnabled() {
        try {
            return SPEED_DISPLAY_ENABLED.get();
        } catch (RuntimeException ignored) {
            return false;
        }
    }

    private OdditiesServerConfig() {
    }
}
