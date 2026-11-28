package dev.createoddities.module.cable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

/**
 * Rotates and mirrors the relative wire endpoints stored in a CCA connector's
 * {@code nodes} list, using the same transform Create applies to block
 * positions.
 */
public final class WireNodeTransform {
    private WireNodeTransform() {
    }

    public static boolean isIdentity(Mirror mirror, Rotation rotation) {
        return mirror == Mirror.NONE && rotation == Rotation.NONE;
    }

    public static BlockPos transform(BlockPos offset, Mirror mirror, Rotation rotation) {
        BlockPos zero = StructureTemplate.transform(BlockPos.ZERO, mirror, rotation, BlockPos.ZERO);
        BlockPos moved = StructureTemplate.transform(offset, mirror, rotation, BlockPos.ZERO);
        return moved.offset(-zero.getX(), -zero.getY(), -zero.getZ());
    }

    public static void rotateInPlace(CompoundTag parent, Mirror mirror, Rotation rotation) {
        if (isIdentity(mirror, rotation)) {
            return;
        }
        ListTag nodes = parent.getList("nodes", Tag.TAG_COMPOUND);
        for (int i = 0; i < nodes.size(); i++) {
            CompoundTag node = nodes.getCompound(i);
            BlockPos offset = new BlockPos(node.getInt("x"), node.getInt("y"), node.getInt("z"));
            BlockPos rotated = transform(offset, mirror, rotation);
            node.putInt("x", rotated.getX());
            node.putInt("y", rotated.getY());
            node.putInt("z", rotated.getZ());
        }
    }
}
