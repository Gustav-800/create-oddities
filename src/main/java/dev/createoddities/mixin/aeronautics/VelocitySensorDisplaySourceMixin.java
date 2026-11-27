package dev.createoddities.mixin.aeronautics;

import com.simibubi.create.content.redstone.displayLink.DisplayLinkContext;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTargetStats;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import dev.createoddities.config.OdditiesServerConfig;
import dev.createoddities.config.SpeedUnit;
import dev.createoddities.module.speed.SpeedDisplay;
import dev.createoddities.module.speed.VelocitySensorUnitBehaviour;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Rewrites the display-link line produced by the velocity sensor.
 */
@Mixin(
        targets = "dev.simulated_team.simulated.content.display_sources.VelocitySensorDisplaySource",
        remap = false
)
public abstract class VelocitySensorDisplaySourceMixin {
    @Inject(method = "provideLine", at = @At("RETURN"), cancellable = true, remap = false)
    private void createoddities$formatDisplayLine(
            DisplayLinkContext context,
            DisplayTargetStats stats,
            CallbackInfoReturnable<MutableComponent> cir
    ) {
        SpeedUnit unit = null;
        BlockEntity source = context.getSourceBlockEntity();
        if (source instanceof SmartBlockEntity smartBlockEntity) {
            unit = VelocitySensorUnitBehaviour.unitOf(smartBlockEntity);
        }
        if (unit == null) {
            unit = OdditiesServerConfig.defaultUnit();
        }
        MutableComponent original = cir.getReturnValue();
        MutableComponent converted = SpeedDisplay.convert(original, unit);
        if (converted != original) {
            cir.setReturnValue(converted);
        }
    }
}
