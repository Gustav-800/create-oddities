package dev.createoddities.module.speed;

import dev.createoddities.config.OdditiesServerConfig;
import dev.createoddities.config.SpeedUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

/**
 * Rewrites the velocity sensor's "x m/s" display into the selected unit.
 */
public final class SpeedDisplay {
    private static final Pattern METERS_PER_SECOND =
            Pattern.compile("^\\s*([+-]?\\d+(?:\\.\\d+)?)\\s*m/s\\s*$");

    private SpeedDisplay() {
    }

    public static MutableComponent convert(MutableComponent original) {
        return convert(original, SpeedUnit.METERS_PER_SECOND);
    }

    public static MutableComponent convert(MutableComponent original, SpeedUnit unit) {
        if (original == null || !OdditiesServerConfig.isSpeedDisplayEnabled()) {
            return original;
        }
        String converted = convertString(original.getString(), unit);
        return converted.equals(original.getString()) ? original : Component.literal(converted);
    }

    /**
     * Converts a raw "x m/s" string. Used by the value-box formatter, which
     * returns a String rather than a Component.
     */
    public static String convertString(String original) {
        return convertString(original, SpeedUnit.METERS_PER_SECOND);
    }

    public static String convertString(String original, SpeedUnit unit) {
        if (original == null || !OdditiesServerConfig.isSpeedDisplayEnabled()) {
            return original;
        }
        Matcher matcher = METERS_PER_SECOND.matcher(original);
        if (!matcher.matches()) {
            return original;
        }
        try {
            double metersPerSecond = Double.parseDouble(matcher.group(1));
            return unit.formatFromMetersPerSecond(metersPerSecond);
        } catch (NumberFormatException ignored) {
            return original;
        }
    }
}
