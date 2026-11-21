package dev.createoddities;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

/**
 * Create Oddities: a modular collection of fixes, tweaks and additions for
 * Create and its addons.
 *
 * <p>The 0.1.0 build contains the client-side typewriter keyguard. While the
 * wireless typewriter owns the keyboard, this module claims keyboard events
 * before vanilla or any other mod can process them.</p>
 */
@Mod(value = CreateOddities.MODID, dist = Dist.CLIENT)
public final class CreateOddities {
    public static final String MODID = "create_oddities";

    public CreateOddities() {
    }
}
