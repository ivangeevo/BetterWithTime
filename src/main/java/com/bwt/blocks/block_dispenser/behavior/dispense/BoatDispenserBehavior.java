package com.bwt.blocks.block_dispenser.behavior.dispense;

import com.bwt.blocks.block_dispenser.BlockDispenserBlock;
import com.bwt.mixin.accessors.BoatItemAccessorMixin;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class BoatDispenserBehavior extends ItemDispenserBehavior {
    public BoatDispenserBehavior() {}

    @Override
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        if (!(stack.getItem() instanceof BoatItem boatItem)) {
            return stack;
        }
        EntityType<? extends AbstractBoatEntity> boatType = ((BoatItemAccessorMixin) boatItem).getBoatEntityType();
        Direction direction = pointer.state().get(BlockDispenserBlock.FACING);
        ServerWorld serverWorld = pointer.world();
        Vec3d vec3d = pointer.centerPos();
        double d = 0.5625 + (double) boatType.getWidth() / 2.0;
        double e = vec3d.getX() + (double) direction.getOffsetX() * d;
        double f = vec3d.getY() + (double) ((float) direction.getOffsetY() * 1.125f);
        double g = vec3d.getZ() + (double) direction.getOffsetZ() * d;

        AbstractBoatEntity abstractBoatEntity = boatType.create(serverWorld, SpawnReason.DISPENSER);
        if (abstractBoatEntity == null) {
            return stack;
        }
        abstractBoatEntity.initPosition(e, f, g);
        EntityType.copier(serverWorld, stack, null).accept(abstractBoatEntity);
        abstractBoatEntity.setYaw(direction.getPositiveHorizontalDegrees());
        serverWorld.spawnEntity(abstractBoatEntity);
        return stack;
    }
}