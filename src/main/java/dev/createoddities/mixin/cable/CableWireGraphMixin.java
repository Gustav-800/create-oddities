package dev.createoddities.mixin.cable;

import dev.createoddities.config.OdditiesCommonConfig;
import dev.createoddities.module.cable.PrintSession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * CCA prunes a wire when the partner block entity is not present yet. During a
 * schematic print the partner may still be in flight, so defer the prune.
 */
@Mixin(targets = "com.mrh0.createaddition.energy.network.WireGraph", remap = false)
public abstract class CableWireGraphMixin {
    @Inject(method = "pruneDanglingWires", at = @At("HEAD"), cancellable = true, remap = false)
    private void createoddities$deferPruneDuringPrint(CallbackInfo ci) {
        if (OdditiesCommonConfig.isCableBlueprintEnabled() && PrintSession.active()) {
            ci.cancel();
        }
    }
}

