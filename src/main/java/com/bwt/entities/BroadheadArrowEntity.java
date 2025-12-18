package com.bwt.entities;

import com.bwt.items.BroadheadArrowItem;
import com.bwt.items.BwtItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BroadheadArrowEntity extends PersistentProjectileEntity {

    public BroadheadArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return BwtItems.broadheadArrowItem.getDefaultStack();
    }

    public BroadheadArrowEntity(World world, double x, double y, double z, ItemStack stack, @Nullable ItemStack weapon) {
        super(BwtEntities.broadheadArrowEntity, x, y, z, world, stack, weapon);
        setDamage(4d);
    }

    public BroadheadArrowEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(BwtEntities.broadheadArrowEntity, owner, world, stack, shotFrom);
        setDamage(4d);
    }
}
