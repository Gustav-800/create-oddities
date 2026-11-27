package dev.createoddities.module.speed;

import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBehaviour.ValueSettings;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsFormatter.ScrollOptionSettingsFormatter;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.INamedIconOptions;
import com.simibubi.create.foundation.gui.AllIcons;
import dev.createoddities.config.SpeedUnit;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

/**
 * Uses the mechanical arm's icon-mode board layout, but replaces the icons with
 * the unit text. ValueSettingsScreen still renders this through its native
 * option-selector path.
 */
public final class SpeedUnitFormatter extends ScrollOptionSettingsFormatter {
    public SpeedUnitFormatter() {
        super(new INamedIconOptions[0]);
    }

    @Override
    public MutableComponent format(ValueSettings valueSettings) {
        return Component.literal(unitFor(valueSettings.value()).suffix());
    }

    @Override
    public AllIcons getIcon(ValueSettings valueSettings) {
        return null;
    }

    private static SpeedUnit unitFor(int ordinal) {
        return SpeedUnit.values()[Math.max(0, Math.min(SpeedUnit.values().length - 1, ordinal))];
    }
}
