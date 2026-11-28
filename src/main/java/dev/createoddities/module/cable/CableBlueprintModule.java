package dev.createoddities.module.cable;

import dev.createoddities.config.OdditiesCommonConfig;
import dev.createoddities.module.OddityModule;

/**
 * Keeps Create: Crafts & Additions wire endpoints in Create blueprints.
 */
public final class CableBlueprintModule implements OddityModule {
    public static final String ID = "cable_blueprint";
    private static final String CCA_MAIN = "com.mrh0.createaddition.CreateAddition";

    @Override
    public String id() {
        return ID;
    }

    @Override
    public boolean isAvailable() {
        try {
            Class.forName(CCA_MAIN, false, CableBlueprintModule.class.getClassLoader());
            return true;
        } catch (ClassNotFoundException | LinkageError exception) {
            return false;
        }
    }

    @Override
    public boolean isEnabled() {
        return OdditiesCommonConfig.isCableBlueprintEnabled();
    }

    @Override
    public void initialize() {
        // The mixins do the work; nothing to register.
    }
}

