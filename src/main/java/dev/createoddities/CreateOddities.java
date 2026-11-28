package dev.createoddities;

import dev.createoddities.config.OdditiesCommonConfig;
import dev.createoddities.config.OdditiesConfig;
import dev.createoddities.config.OdditiesServerConfig;
import dev.createoddities.module.ModuleManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;

/**
 * Create Oddities: a modular collection of fixes, tweaks and additions for
 * Create and its addons.
 *
 * <p>0.2.0 adds the module framework, a client typewriter module and a
 * server-synchronised unit selector for the Aeronautics velocity sensor.</p>
 */
@Mod(value = CreateOddities.MODID)
public final class CreateOddities {
    public static final String MODID = "create_oddities";

    public CreateOddities(ModContainer modContainer, IEventBus modEventBus) {
        modContainer.registerConfig(ModConfig.Type.COMMON, OdditiesCommonConfig.SPEC);
        modContainer.registerConfig(ModConfig.Type.SERVER, OdditiesServerConfig.SPEC);
        if (FMLEnvironment.dist.isClient()) {
            modContainer.registerConfig(ModConfig.Type.CLIENT, OdditiesConfig.SPEC);
            modEventBus.addListener(this::onClientSetup);
        }
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        // Config values are not safe to read until after config load.
        event.enqueueWork(ModuleManager::initialize);
    }
}
