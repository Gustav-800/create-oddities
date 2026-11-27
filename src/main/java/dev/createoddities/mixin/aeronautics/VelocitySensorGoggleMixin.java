package dev.createoddities.mixin.aeronautics;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import dev.createoddities.config.OdditiesServerConfig;
import dev.createoddities.config.SpeedUnit;
import dev.createoddities.module.speed.SpeedDisplay;
import dev.createoddities.module.speed.VelocitySensorUnitBehaviour;
import java.util.List;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Converts the sensor's goggle tooltip. Create's goggles and Cyber Goggles both
 * consume this list.
 */
@Mixin(
        targets = "dev.simulated_team.simulated.content.blocks.velocity_sensor.VelocitySensorBlockEntity",
        remap = false
)
public abstract class VelocitySensorGoggleMixin {
    @Inject(method = "addToGoggleTooltip", at = @At("RETURN"), remap = false)
    private void createoddities$convertGoggleTooltip(
            List<Component> tooltip,
            boolean isPlayerSneaking,
            CallbackInfoReturnable<Boolean> cir
    ) {
        SpeedUnit unit = VelocitySensorUnitBehaviour.unitOf((SmartBlockEntity) (Object) this);
        if (unit == null) {
            unit = OdditiesServerConfig.defaultUnit();
        }
        for (int i = 0; i < tooltip.size(); i++) {
            Component line = tooltip.get(i);
            String original = line.getString();
            String converted = SpeedDisplay.convertString(original, unit);
            if (!converted.equals(original)) {
                // Cyber Goggles draws its item/goggle icon over the first
                // characters of the line. Leave room for that icon so the
                // numeric value stays visible.
                tooltip.set(i, Component.literal("    " + converted));
            }
        }
    }
}
