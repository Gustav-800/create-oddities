package dev.createoddities.module.cable;

/**
 * Marks the short window in which Create builds mirrored copies for the
 * deployed-blueprint hologram. The print path must not use this mirror fix a
 * second time.
 */
public final class PreviewMirrorContext {
    private static boolean active;

    private PreviewMirrorContext() {
    }

    public static void begin() {
        active = true;
    }

    public static void end() {
        active = false;
    }

    public static boolean active() {
        return active;
    }
}
