package dev.createoddities.mixin.aeronautics;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import dev.createoddities.config.OdditiesServerConfig;
import dev.createoddities.config.SpeedUnit;
import dev.createoddities.module.speed.VelocitySensorUnitBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * The value box uses ScrollValueBehaviour#formatValue(). The max-speed value
 * is already stored in the selected unit, so do not convert it a second time.
 */
@Mixin(targets = "com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour", remap = false)
public abstract class VelocitySensorValueBoxMixin {
    private static final String SENSOR_BEHAVIOUR =
            "dev.simulated_team.simulated.content.blocks.velocity_sensor.VelocitySensorBlockEntity$VelocitySensorScrollValueBehaviour";

    @Inject(method = "formatValue", at = @At("RETURN"), cancellable = true, remap = false)
    private void createoddities$formatValueBox(CallbackInfoReturnable<String> cir) {
        if (!getClass().getName().equals(SENSOR_BEHAVIOUR)) {
            return;
        }
        if (!OdditiesServerConfig.isSpeedDisplayEnabled()) {
            return;
        }
        SmartBlockEntity blockEntity = ((BlockEntityBehaviour) (Object) this).blockEntity;
        SpeedUnit unit = VelocitySensorUnitBehaviour.unitOf(blockEntity);
        if (unit == null) {
            unit = OdditiesServerConfig.defaultUnit();
        }
        int value = ((ScrollValueBehaviour) (Object) this).getValue();
        cir.setReturnValue(value + " " + unit.suffix());
    }
}
