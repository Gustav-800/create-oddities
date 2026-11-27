package dev.createoddities;

import dev.createoddities.config.OdditiesConfig;
import dev.createoddities.module.ModuleManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Create Oddities: a modular collection of fixes, tweaks and additions for
 * Create and its addons.
 *
 * <p>0.2.0 is the module-framework release. The typewriter keyguard is the
 * first module; CCA cables, Aeronautics speed units and the CBCAT toggle are
 * added on top of the same registry and config.</p>
 */
@Mod(value = CreateOddities.MODID, dist = Dist.CLIENT)
public final class CreateOddities {
    public static final String MODID = "create_oddities";

    public CreateOddities(ModContainer modContainer, IEventBus modEventBus) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, OdditiesConfig.SPEC);
        modEventBus.addListener(this::onClientSetup);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        // Config values are not safe to read until after config load.
        event.enqueueWork(ModuleManager::initialize);
    }
}
