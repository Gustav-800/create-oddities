package dev.createoddities.mixin;

import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

/**
 * Only applies the CCA cable mixins when Create: Crafts & Additions is present.
 */
public final class CableMixinPlugin implements IMixinConfigPlugin {
    private static final String CCA_MAIN = "com.mrh0.createaddition.CreateAddition";
    private boolean available;

    @Override
    public void onLoad(String mixinPackage) {
        try {
            Class.forName(CCA_MAIN, false, CableMixinPlugin.class.getClassLoader());
            available = true;
        } catch (ClassNotFoundException | LinkageError exception) {
            available = false;
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return available;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
