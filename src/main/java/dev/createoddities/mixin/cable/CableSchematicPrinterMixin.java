package dev.createoddities.mixin.cable;

import dev.createoddities.config.OdditiesCommonConfig;
import dev.createoddities.module.cable.WireNodeTransform;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Create rotates block positions/states but not arbitrary block-entity NBT.
 * CCA stores wire endpoints as relative offsets; rotate them with the same
 * StructurePlaceSettings after the printer loads its copy.
 */
@Mixin(targets = "com.simibubi.create.content.schematics.SchematicPrinter", remap = false)
public abstract class CableSchematicPrinterMixin {
    private static final Logger LOG = LoggerFactory.getLogger("Create Oddities");

    @Inject(method = "loadSchematic", at = @At("RETURN"), remap = false)
    private void createoddities$rotateWireNodes(
            ItemStack blueprint,
            Level originalWorld,
            boolean processNBT,
            CallbackInfo ci
    ) {
        if (!OdditiesCommonConfig.isCableBlueprintEnabled()) {
            return;
        }
        try {
            Object reader = blockReaderOf(this);
            if (reader == null) {
                return;
            }
            StructurePlaceSettings settings = settingsOf(blueprint, processNBT);
            if (settings == null) {
                return;
            }
            Mirror mirror = settings.getMirror();
            Rotation rotation = settings.getRotation();
            if (WireNodeTransform.isIdentity(mirror, rotation)) {
                return;
            }
            Method getBlockEntities = reader.getClass().getMethod("getBlockEntities");
            Iterable<?> blockEntities = (Iterable<?>) getBlockEntities.invoke(reader);
            for (Object candidate : blockEntities) {
                if (!(candidate instanceof BlockEntity blockEntity)) {
                    continue;
                }
                if (!blockEntity.getClass().getName().startsWith("com.mrh0.createaddition.")) {
                    continue;
                }
                CompoundTag current = blockEntity.saveWithFullMetadata(originalWorld.registryAccess());
                if (!current.contains("nodes")) {
                    continue;
                }
                ListTag nodes = current.getList("nodes", Tag.TAG_COMPOUND).copy();
                CompoundTag replacement = new CompoundTag();
                replacement.put("nodes", nodes);
                WireNodeTransform.rotateInPlace(replacement, mirror, rotation);
                blockEntity.loadWithComponents(replacement, originalWorld.registryAccess());
            }
        } catch (Throwable throwable) {
            LOG.warn("Failed to rotate CCA wire nodes for a rotated blueprint", throwable);
        }
    }

    private static Object blockReaderOf(Object printer) throws Exception {
        Field field = printer.getClass().getDeclaredField("blockReader");
        field.setAccessible(true);
        return field.get(printer);
    }

    private static StructurePlaceSettings settingsOf(ItemStack blueprint, boolean processNBT) throws Exception {
        Class<?> schematicItem = Class.forName("com.simibubi.create.content.schematics.SchematicItem");
        Method method = schematicItem.getMethod("getSettings", ItemStack.class, boolean.class);
        return (StructurePlaceSettings) method.invoke(null, blueprint, processNBT);
    }
}

