package dev.createoddities.mixin.config;

import net.createmod.catnip.config.ui.ConfigScreen;
import net.createmod.catnip.config.ui.ConfigScreenList.LabeledEntry;
import net.createmod.catnip.gui.element.TextStencilElement;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Translate only Create Oddities entries in Create's native config screen.
 * Catnip builds config labels from raw paths, so normal lang keys do not apply.
 */
@Mixin(value = LabeledEntry.class, remap = false)
public abstract class ConfigLabelMixin {
    @Shadow
    protected TextStencilElement label;

    @Inject(method = "<init>(Ljava/lang/String;)V", at = @At("RETURN"), remap = false)
    private void createoddities$translateSingle(String name, CallbackInfo ci) {
        translate(name);
    }

    @Inject(method = "<init>(Ljava/lang/String;Ljava/lang/String;)V", at = @At("RETURN"), remap = false)
    private void createoddities$translateWithUnit(String name, String unit, CallbackInfo ci) {
        translate(name);
    }

    private void translate(String name) {
        if (!"create_oddities".equals(ConfigScreen.modID)) {
            return;
        }
        String key = switch (name) {
            case "Speed Display" -> "create_oddities.config.modules.speed_display";
            case "Typewriter Keyguard" -> "create_oddities.config.modules.typewriter_keyguard";
            case "Default Unit" -> "create_oddities.config.modules.speed_display.default_unit";
            case "Enabled" -> "create_oddities.config.enabled";
            default -> null;
        };
        if (key != null) {
            label.withText(Component.translatable(key));
        }
    }
}
