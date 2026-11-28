package dev.createoddities.mixin.cable;

import dev.createoddities.config.OdditiesCommonConfig;
import dev.createoddities.module.cable.PrintSession;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Create filters block-entity data while printing. CCA's connector NBT is not
 * included in the safe writers, so the wire nodes are normally lost. Carry only
 * the position-safe nodes list during a print.
 */
@Mixin(targets = "com.simibubi.create.foundation.utility.BlockHelper", remap = false)
public abstract class CableBlockHelperMixin {
    @Inject(
            method = "prepareBlockEntityData(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/entity/BlockEntity;)Lnet/minecraft/nbt/CompoundTag;",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void createoddities$carryWireNodes(
            Level level,
            BlockState state,
            BlockEntity blockEntity,
            CallbackInfoReturnable<CompoundTag> cir
    ) {
        if (!OdditiesCommonConfig.isCableBlueprintEnabled()) {
            return;
        }
        PrintSession.ping();
        if (level == null || blockEntity == null) {
            return;
        }
        if (!blockEntity.getClass().getName().startsWith("com.mrh0.createaddition.")) {
            return;
        }
        CompoundTag full = blockEntity.saveWithFullMetadata(level.registryAccess());
        if (!full.contains("nodes")) {
            return;
        }
        CompoundTag out = new CompoundTag();
        out.put("nodes", full.get("nodes"));
        cir.setReturnValue(out);
    }
}

