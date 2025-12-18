package com.bwt.generation;

import com.bwt.tags.BwtEntityTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EntityTypeTags;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class EntityTypeTagGenerator extends FabricTagProvider.EntityTypeTagProvider {
    public EntityTypeTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.@NonNull WrapperLookup arg) {
        valueLookupBuilder(BwtEntityTags.BLOCK_DISPENSER_INHALE_ENTITIES)
                .add(EntityType.WOLF)
                .add(EntityType.CHICKEN)
                .add(EntityType.SHEEP)
                .add(EntityType.MINECART)
                .add(EntityType.FURNACE_MINECART)
                .add(EntityType.CHEST_MINECART)
                .add(EntityType.HOPPER_MINECART)
                .add(EntityType.SPAWNER_MINECART)
                .add(EntityType.TNT_MINECART)
                .forceAddTag(EntityTypeTags.BOAT)
                .add(EntityType.ACACIA_CHEST_BOAT)
                .add(EntityType.BIRCH_CHEST_BOAT)
                .add(EntityType.CHERRY_CHEST_BOAT)
                .add(EntityType.DARK_OAK_CHEST_BOAT)
                .add(EntityType.JUNGLE_CHEST_BOAT)
                .add(EntityType.MANGROVE_CHEST_BOAT)
                .add(EntityType.OAK_CHEST_BOAT)
                .add(EntityType.PALE_OAK_CHEST_BOAT)
                .add(EntityType.SPRUCE_CHEST_BOAT)
                .add(EntityType.ARMOR_STAND)
                .add(EntityType.ITEM_FRAME)
                .add(EntityType.GLOW_ITEM_FRAME);
    }
}
