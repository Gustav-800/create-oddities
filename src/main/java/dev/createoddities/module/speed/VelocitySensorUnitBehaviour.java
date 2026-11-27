package dev.createoddities.module.speed;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.blockEntity.behaviour.CenteredSideValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBoard;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import dev.createoddities.config.OdditiesServerConfig;
import dev.createoddities.config.SpeedUnit;
import dev.simulated_team.simulated.content.blocks.util.AbstractDirectionalAxisBlock;
import dev.simulated_team.simulated.content.blocks.velocity_sensor.VelocitySensorBlockEntity;
import java.util.List;
import java.util.function.BiPredicate;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;

/**
 * Side-mounted unit selector for the Aeronautics velocity sensor.
 *
 * <p>The sensor's own value box uses the measurement axis. The unit selector
 * uses a perpendicular side. Its value box is raised by one pixel and sits
 * just outside the model so the frame remains visible.</p>
 */
public final class VelocitySensorUnitBehaviour extends ScrollValueBehaviour {
    public static final BehaviourType<VelocitySensorUnitBehaviour> TYPE =
            new BehaviourType<>("create_oddities_velocity_sensor_unit");
    private static final String NBT_KEY = "CreateOdditiesSpeedUnit";
    private static final int UNIT_STEPS = 1;
    private static final int MAX_STEP = (SpeedUnit.values().length - 1) * UNIT_STEPS;
    private boolean disabledDefaultsApplied;

    public VelocitySensorUnitBehaviour(SmartBlockEntity blockEntity) {
        super(
                Component.translatable("create_oddities.velocity_sensor.unit"),
                blockEntity,
                new UnitValueBox((state, side) -> side.getAxis() != axisOf(state))
        );
        between(0, MAX_STEP);
        withFormatter(step -> unitFor(unitFromStep(step)).suffix());
        value = defaultUnitOrdinal() * UNIT_STEPS;
        applyMaxSpeedLimit();
    }

    public static VelocitySensorUnitBehaviour get(SmartBlockEntity blockEntity) {
        return blockEntity == null ? null : blockEntity.getBehaviour(TYPE);
    }

    public static SpeedUnit unitOf(SmartBlockEntity blockEntity) {
        VelocitySensorUnitBehaviour behaviour = get(blockEntity);
        return behaviour == null ? null : behaviour.unit();
    }

    public SpeedUnit unit() {
        return unitFor(unitFromStep(value));
    }

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    }

    @Override
    public int netId() {
        // ValueSettingsPacket identifies the target behaviour by netId. The
        // original max-speed behaviour uses the default 0, so the unit
        // behaviour needs its own id or packets are applied to maxSpeed.
        return 1;
    }

    @Override
    public boolean isActive() {
        return OdditiesServerConfig.isSpeedDisplayEnabled();
    }

    @Override
    public void tick() {
        super.tick();
        if (OdditiesServerConfig.isSpeedDisplayEnabled()) {
            disabledDefaultsApplied = false;
            return;
        }
        if (disabledDefaultsApplied) {
            return;
        }
        disabledDefaultsApplied = true;

        SpeedUnit previousUnit = unit();
        ScrollValueBehaviour maxSpeed = maxSpeed();
        if (maxSpeed != null) {
            int metersPerSecond = (int) Math.round(previousUnit.toMetersPerSecond(maxSpeed.getValue()));
            maxSpeed.between(1, 50);
            maxSpeed.withFormatter(value -> value + " m/s");
            maxSpeed.setValue(Math.max(1, Math.min(50, metersPerSecond)));
        }
        if (value != 0) {
            super.setValue(0);
        }
    }

    @Override
    public boolean bypassesInput(ItemStack stack) {
        // A wrench must reach Create's normal rotate/wrench handling instead
        // of being captured by the side unit selector.
        return stack.is(Tags.Items.TOOLS_WRENCH);
    }

    @Override
    public boolean testHit(Vec3 hit) {
        // The side model is inset; accept any face perpendicular to the
        // sensor's measurement axis.
        double x = hit.x - getPos().getX();
        double y = hit.y - getPos().getY();
        double z = hit.z - getPos().getZ();
        Direction side;
        if (x < 0.05) {
            side = Direction.WEST;
        } else if (x > 0.95) {
            side = Direction.EAST;
        } else if (z < 0.05) {
            side = Direction.NORTH;
        } else if (z > 0.95) {
            side = Direction.SOUTH;
        } else if (y < 0.05) {
            side = Direction.DOWN;
        } else if (y > 0.95) {
            side = Direction.UP;
        } else {
            return false;
        }
        return side.getAxis() != axisOf(blockEntity.getBlockState());
    }

    @Override
    public ValueSettingsBoard createBoard(Player player, BlockHitResult hitResult) {
        return new ValueSettingsBoard(
                label,
                MAX_STEP,
                UNIT_STEPS,
                List.of(Component.translatable("create_oddities.velocity_sensor.unit.row")),
                new SpeedUnitFormatter()
        );
    }

    @Override
    public void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        // Do not call ScrollValueBehaviour#read: it would read the shared
        // "ScrollValue" key used by the original max-speed behaviour.
        value = tag.contains(NBT_KEY)
                ? Math.max(0, Math.min(MAX_STEP, tag.getInt(NBT_KEY)))
                : defaultUnitOrdinal() * UNIT_STEPS;
        applyMaxSpeedLimit();
    }

    @Override
    public void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        // Keep this behaviour's NBT separate from maxSpeed's "ScrollValue".
        tag.putInt(NBT_KEY, value);
    }

    @Override
    public void setValue(int newValue) {
        if (!OdditiesServerConfig.isSpeedDisplayEnabled()) {
            return;
        }
        SpeedUnit previousUnit = unit();
        super.setValue(newValue);
        SpeedUnit newUnit = unit();
        if (previousUnit != newUnit) {
            convertMaxSpeed(previousUnit, newUnit);
        }
        applyMaxSpeedLimit();
    }

    private void applyMaxSpeedLimit() {
        ScrollValueBehaviour maxSpeed = maxSpeed();
        if (maxSpeed == null) {
            return;
        }
        SpeedUnit unit = unit();
        maxSpeed.between(1, unit.maxSetting());
        maxSpeed.withFormatter(value -> value + " " + unit.suffix());
        if (maxSpeed.getValue() > unit.maxSetting()) {
            maxSpeed.setValue(unit.maxSetting());
        }
    }

    private void convertMaxSpeed(SpeedUnit oldUnit, SpeedUnit newUnit) {
        ScrollValueBehaviour maxSpeed = maxSpeed();
        if (maxSpeed == null) {
            return;
        }
        double metersPerSecond = oldUnit.toMetersPerSecond(maxSpeed.getValue());
        int converted = (int) Math.round(newUnit.fromMetersPerSecond(metersPerSecond));
        maxSpeed.between(1, newUnit.maxSetting());
        maxSpeed.setValue(Math.max(1, Math.min(newUnit.maxSetting(), converted)));
    }

    private ScrollValueBehaviour maxSpeed() {
        if (blockEntity instanceof VelocitySensorBlockEntity sensor) {
            return sensor.getMaxSpeed();
        }
        return null;
    }

    private static Direction.Axis axisOf(BlockState state) {
        return AbstractDirectionalAxisBlock.getAxis(state);
    }

    private static int defaultUnitOrdinal() {
        try {
            return OdditiesServerConfig.SPEED_UNIT.get().ordinal();
        } catch (RuntimeException ignored) {
            return SpeedUnit.METERS_PER_SECOND.ordinal();
        }
    }

    private static int unitFromStep(int step) {
        int clamped = Math.max(0, Math.min(MAX_STEP, step));
        return Math.round(clamped / (float) UNIT_STEPS);
    }

    private static SpeedUnit unitFor(int ordinal) {
        return SpeedUnit.values()[Math.max(0, Math.min(SpeedUnit.values().length - 1, ordinal))];
    }

    /**
     * One pixel above the original value-box centre, just outside the side
     * model, using the original 0.35 text scale.
     */
    private static final class UnitValueBox extends CenteredSideValueBoxTransform {
        private UnitValueBox(BiPredicate<BlockState, Direction> allowedDirections) {
            super(allowedDirections);
        }

        @Override
        protected Vec3 getSouthLocation() {
            return new Vec3(8.0 / 16.0, 9.0 / 16.0, 16.5 / 16.0);
        }

        @Override
        public float getScale() {
            return 0.35f;
        }
    }
}

