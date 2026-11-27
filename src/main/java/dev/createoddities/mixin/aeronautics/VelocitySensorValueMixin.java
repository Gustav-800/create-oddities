package dev.createoddities.mixin.aeronautics;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBehaviour.ValueSettings;
import dev.createoddities.config.OdditiesServerConfig;
import dev.createoddities.config.SpeedUnit;
import dev.createoddities.module.speed.VelocitySensorUnitBehaviour;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * The value-settings board must show the max-speed value in the unit it is
 * stored in, not convert it from m/s a second time.
 */
@Mixin(
        targets = "dev.simulated_team.simulated.content.blocks.velocity_sensor.VelocitySensorBlockEntity$VelocitySensorScrollValueBehaviour",
        remap = false
)
public abstract class VelocitySensorValueMixin {
    @Inject(method = "formatValue", at = @At("RETURN"), cancellable = true, remap = false)
    private void createoddities$formatBoardValue(
            ValueSettings settings,
            CallbackInfoReturnable<MutableComponent> cir
    ) {
        if (!OdditiesServerConfig.isSpeedDisplayEnabled()) {
            return;
        }
        SmartBlockEntity blockEntity = ((BlockEntityBehaviour) (Object) this).blockEntity;
        SpeedUnit unit = VelocitySensorUnitBehaviour.unitOf(blockEntity);
        if (unit == null) {
            unit = OdditiesServerConfig.defaultUnit();
        }
        cir.setReturnValue(Component.literal(settings.value() + " " + unit.suffix()));
    }
}
