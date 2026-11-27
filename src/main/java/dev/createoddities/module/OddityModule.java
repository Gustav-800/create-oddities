package dev.createoddities.module;

/**
 * A single optional feature of Create Oddities.
 *
 * <p>Modules must not assume their optional integrations are installed. The
 * module manager checks {@link #isAvailable()} before calling
 * {@link #initialize()}, and module code must fail open at runtime.</p>
 */
public interface OddityModule {
    /** Stable id used in logs and (later) config keys. */
    String id();

    /** Whether the optional dependencies required by this module are present. */
    boolean isAvailable();

    /** Whether the user has enabled this module in the config. */
    boolean isEnabled();

    /** Called once after config load, on the client thread. */
    void initialize();

    /**
     * Content modules register items/blocks/recipes. Registry entries cannot be
     * unregistered at runtime, so their enabled state is frozen at world load
     * and config changes take effect only after leaving and re-entering a world.
     */
    default boolean requiresWorldReload() {
        return false;
    }
}
