package dev.createoddities.config;

import java.util.Locale;

/**
 * Speed unit used by the optional Aeronautics velocity-sensor display module.
 *
 * <p>Unit suffixes deliberately stay in English. Chinese suffixes are wider
 * than the Create slider cells and overflow the value box.</p>
 */
public enum SpeedUnit {
    METERS_PER_SECOND("m/s", 1.0),
    KILOMETERS_PER_HOUR("km/h", 3.6),
    KNOTS("kn", 1.943844);

    private final String suffix;
    private final double factor;

    SpeedUnit(String suffix, double factor) {
        this.suffix = suffix;
        this.factor = factor;
    }

    public String suffix() {
        return suffix;
    }

    public String formatFromMetersPerSecond(double metersPerSecond) {
        return String.format(Locale.ROOT, "%.1f %s", metersPerSecond * factor, suffix);
    }

    public int maxSetting() {
        return switch (this) {
            case METERS_PER_SECOND -> 50;
            case KILOMETERS_PER_HOUR -> 180;
            case KNOTS -> 100;
        };
    }

    /**
     * Interval that produces six equal markers from zero to maxSetting().
     */
    public int milestoneInterval() {
        return switch (this) {
            case METERS_PER_SECOND -> 10;
            case KILOMETERS_PER_HOUR -> 36;
            case KNOTS -> 20;
        };
    }

    public double toMetersPerSecond(double value) {
        return value / factor;
    }

    public double fromMetersPerSecond(double metersPerSecond) {
        return metersPerSecond * factor;
    }
}
