package dev.createoddities.mixin.aeronautics;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBoard;
import dev.createoddities.config.OdditiesServerConfig;
import dev.createoddities.config.SpeedUnit;
import dev.createoddities.module.speed.VelocitySensorUnitBehaviour;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Rebuild the max-speed board with a unit-specific milestone interval so the
 * slider shows six equal markers instead of 0,10,20,... for every unit.
 */
@Mixin(
        targets = "dev.simulated_team.simulated.content.blocks.velocity_sensor.VelocitySensorBlockEntity$VelocitySensorScrollValueBehaviour",
        remap = false
)
public abstract class VelocitySensorMaxSpeedBoardMixin {
    @Inject(method = "createBoard", at = @At("RETURN"), cancellable = true, remap = false)
    private void createoddities$unitAwareMilestones(
            Player player,
            BlockHitResult hitResult,
            CallbackInfoReturnable<ValueSettingsBoard> cir
    ) {
        SmartBlockEntity blockEntity = ((com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour) (Object) this).blockEntity;
        SpeedUnit unit = VelocitySensorUnitBehaviour.unitOf(blockEntity);
        if (unit == null) {
            unit = OdditiesServerConfig.defaultUnit();
        }
        ValueSettingsBoard board = cir.getReturnValue();
        cir.setReturnValue(new ValueSettingsBoard(
                board.title(),
                board.maxValue(),
                unit.milestoneInterval(),
                board.rows(),
                board.formatter()
        ));
    }
}
