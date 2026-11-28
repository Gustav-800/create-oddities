package dev.createoddities.module;

import dev.createoddities.module.cable.CableBlueprintModule;
import dev.createoddities.module.typewriter.TypewriterModule;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registers and initializes the modules that are present in this build.
 *
 * <p>0.2.0 starts with the typewriter module; future versions append modules
 * here. Initialization happens after config load, so reading config values is
 * safe.</p>
 */
public final class ModuleManager {
    private static final Logger LOGGER = LoggerFactory.getLogger("Create Oddities");
    private static final List<OddityModule> MODULES = List.of(
            new TypewriterModule(),
            new CableBlueprintModule()
    );

    private ModuleManager() {
    }

    public static void initialize() {
        for (OddityModule module : MODULES) {
            String id = module.id();
            if (!module.isEnabled()) {
                LOGGER.info("Module '{}' is disabled in the config.", id);
                continue;
            }
            if (!module.isAvailable()) {
                LOGGER.info("Module '{}' is unavailable; its optional dependency is not installed or not supported.", id);
                continue;
            }
            try {
                module.initialize();
                LOGGER.info("Module '{}' initialized.", id);
            } catch (RuntimeException | LinkageError exception) {
                LOGGER.error("Module '{}' failed to initialize; it will stay inactive.", id, exception);
            }
        }
    }
}
