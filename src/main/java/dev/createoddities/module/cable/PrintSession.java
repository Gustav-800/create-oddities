package dev.createoddities.module.cable;

/**
 * Tracks whether a Create schematic printing session is active.
 *
 * <p>Create's cannon launches blocks one at a time, so a wire endpoint can
 * arrive many ticks before its partner. CCA's wire graph treats a missing
 * partner as a removed wire; during a print we suppress that pruning.</p>
 */
public final class PrintSession {
    private static final long TIMEOUT_MS = 600_000L;
    private static volatile long lastPing;

    private PrintSession() {
    }

    public static void ping() {
        lastPing = System.currentTimeMillis();
    }

    public static void begin() {
        ping();
    }

    public static void end() {
        lastPing = 0L;
    }

    public static boolean active() {
        long time = lastPing;
        return time != 0L && System.currentTimeMillis() - time < TIMEOUT_MS;
    }
}
