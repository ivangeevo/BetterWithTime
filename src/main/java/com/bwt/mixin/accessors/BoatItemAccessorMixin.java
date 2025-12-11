package com.bwt.mixin.accessors;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BoatItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BoatItem.class)
public interface BoatItemAccessorMixin {
    @Accessor
    EntityType<? extends AbstractBoatEntity> getBoatEntityType();

    @Accessor
    boolean getChest();
}
