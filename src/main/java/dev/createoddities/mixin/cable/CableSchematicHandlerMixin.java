package dev.createoddities.mixin.cable;

import dev.createoddities.module.cable.PreviewMirrorContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Bracket the deployed-blueprint hologram setup. Only during this call should
 * StructureTransform mirror CCA wire nodes.
 */
@Mixin(targets = "com.simibubi.create.content.schematics.client.SchematicHandler", remap = false)
public abstract class CableSchematicHandlerMixin {
    @Inject(method = "setupRenderer", at = @At("HEAD"), remap = false)
    private void createoddities$beginPreviewMirror(CallbackInfo ci) {
        PreviewMirrorContext.begin();
    }

    @Inject(method = "setupRenderer", at = @At("RETURN"), remap = false)
    private void createoddities$endPreviewMirror(CallbackInfo ci) {
        PreviewMirrorContext.end();
    }
}
