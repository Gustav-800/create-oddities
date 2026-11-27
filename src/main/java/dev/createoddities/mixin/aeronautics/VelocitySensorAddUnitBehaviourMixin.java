package dev.createoddities.mixin.aeronautics;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import dev.createoddities.module.speed.VelocitySensorUnitBehaviour;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Adds the side-mounted unit selector to every velocity sensor.
 */
@Mixin(
        targets = "dev.simulated_team.simulated.content.blocks.velocity_sensor.VelocitySensorBlockEntity",
        remap = false
)
public abstract class VelocitySensorAddUnitBehaviourMixin {
    @Inject(method = "addBehaviours", at = @At("TAIL"), remap = false)
    private void createoddities$addUnitBehaviour(List<BlockEntityBehaviour> behaviours, CallbackInfo ci) {
        behaviours.add(new VelocitySensorUnitBehaviour((SmartBlockEntity) (Object) this));
    }
}
