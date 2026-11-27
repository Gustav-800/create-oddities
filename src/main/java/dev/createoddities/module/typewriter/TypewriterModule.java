package dev.createoddities.module.typewriter;

import dev.createoddities.config.OdditiesConfig;
import dev.createoddities.module.OddityModule;

/**
 * Typewriter keyguard module registration.
 */
public final class TypewriterModule implements OddityModule {
    public static final String ID = "typewriter_keyguard";

    @Override
    public String id() {
        return ID;
    }

    @Override
    public boolean isAvailable() {
        return TypewriterKeyguard.isAvailable();
    }

    @Override
    public boolean isEnabled() {
        return OdditiesConfig.TYPEWRITER_ENABLED.get();
    }

    @Override
    public void initialize() {
        TypewriterKeyguard.initialize();
    }
}
