package dev.createoddities.mixin.cable;

import dev.createoddities.config.OdditiesCommonConfig;
import dev.createoddities.module.cable.PrintSession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Keep the print session alive while the cannon is finishing/resetting. The
 * timeout in PrintSession handles blocks that land after the last progress
 * update.
 */
@Mixin(targets = "com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity", remap = false)
public abstract class CableSchematicannonMixin {
    @Inject(method = "finishedPrinting", at = @At("HEAD"), remap = false)
    private void createoddities$pingOnFinish(CallbackInfo ci) {
        if (OdditiesCommonConfig.isCableBlueprintEnabled()) {
            PrintSession.ping();
        }
    }

    @Inject(method = "resetPrinter", at = @At("HEAD"), remap = false)
    private void createoddities$pingOnReset(CallbackInfo ci) {
        if (OdditiesCommonConfig.isCableBlueprintEnabled()) {
            PrintSession.ping();
        }
    }
}

