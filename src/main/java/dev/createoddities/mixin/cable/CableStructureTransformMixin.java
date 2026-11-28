package dev.createoddities.mixin.cable;

import dev.createoddities.config.OdditiesCommonConfig;
import dev.createoddities.module.cable.PreviewMirrorContext;
import dev.createoddities.module.cable.WireNodeTransform;
import net.createmod.catnip.levelWrappers.SchematicLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Create's deployed-blueprint hologram mirrors block entity positions but not
 * arbitrary block entity NBT. CCA stores wire endpoints as relative offsets, so
 * mirroring the schematic preview leaves the wire rendering offset.
 *
 * <p>Only apply this while the block entity lives in a SchematicLevel, which is
 * the preview world. The actual print path is handled by
 * CableSchematicPrinterMixin.</p>
 */
@Mixin(targets = "com.simibubi.create.content.contraptions.StructureTransform", remap = false)
public abstract class CableStructureTransformMixin {
    @Inject(method = "apply(Lnet/minecraft/world/level/block/entity/BlockEntity;)V", at = @At("TAIL"), remap = false)
    private void createoddities$mirrorWireNodes(BlockEntity blockEntity, CallbackInfo ci) {
        if (!OdditiesCommonConfig.isCableBlueprintEnabled() || !PreviewMirrorContext.active()) {
            return;
        }
        if (!(blockEntity.getLevel() instanceof SchematicLevel level)) {
            return;
        }
        if (!blockEntity.getClass().getName().startsWith("com.mrh0.createaddition.")) {
            return;
        }

        com.simibubi.create.content.contraptions.StructureTransform transform =
                (com.simibubi.create.content.contraptions.StructureTransform) (Object) this;
        Mirror mirror = transform.mirror == null ? Mirror.NONE : transform.mirror;
        Rotation rotation = transform.rotation == null ? Rotation.NONE : transform.rotation;
        if (WireNodeTransform.isIdentity(mirror, rotation)) {
            return;
        }

        CompoundTag current = blockEntity.saveWithFullMetadata(level.registryAccess());
        if (!current.contains("nodes")) {
            return;
        }
        CompoundTag replacement = new CompoundTag();
        replacement.put("nodes", current.get("nodes").copy());
        WireNodeTransform.rotateInPlace(replacement, mirror, rotation);
        blockEntity.loadWithComponents(replacement, level.registryAccess());
    }
}
