package dev.createoddities.mixin.aeronautics;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import dev.createoddities.config.SpeedUnit;
import dev.createoddities.module.speed.VelocitySensorUnitBehaviour;
import dev.simulated_team.simulated.content.blocks.velocity_sensor.VelocitySensorBlockEntity.VelocitySensorScrollValueBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * The sensor's redstone/full-scale calculation uses the max-speed behaviour's
 * raw value. That value is stored in the selected unit, so convert it to m/s
 * before the original division.
 */
@Mixin(
        targets = "dev.simulated_team.simulated.content.blocks.velocity_sensor.VelocitySensorBlockEntity",
        remap = false
)
public abstract class VelocitySensorMaxSpeedMixin {
    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Ldev/simulated_team/simulated/content/blocks/velocity_sensor/VelocitySensorBlockEntity$VelocitySensorScrollValueBehaviour;getValue()I"
            ),
            remap = false
    )
    private int createoddities$maxSpeedInMetersPerSecond(VelocitySensorScrollValueBehaviour behaviour) {
        int raw = behaviour.getValue();
        SpeedUnit unit = VelocitySensorUnitBehaviour.unitOf((SmartBlockEntity) (Object) this);
        if (unit == null) {
            return raw;
        }
        return (int) Math.round(unit.toMetersPerSecond(raw));
    }
}
