package com.bwt.generation;

import com.bwt.items.BwtItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantedCountIncreaseLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class EntityLootTableGenerator extends SimpleFabricLootTableProvider {
    private final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup;

    public EntityLootTableGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        this(dataOutput, registryLookup, LootContextTypes.ENTITY);
    }

    public EntityLootTableGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup, LootContextType lootContextType) {
        super(output, registryLookup, lootContextType);
        this.registryLookup = registryLookup;
    }

    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
        lootTableBiConsumer.accept(
                EntityType.SKELETON.getLootTableId(),
                LootTable.builder()
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1.0f))
                                .with(ItemEntry.builder(BwtItems.rottedArrowItem)
                                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f)))
                                        .apply(EnchantedCountIncreaseLootFunction.builder(registryLookup.join(), UniformLootNumberProvider.create(0.0f, 1.0f)))
                                )
                        )
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1.0f))
                                .with(ItemEntry.builder(Items.BONE)
                                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f)))
                                        .apply(EnchantedCountIncreaseLootFunction.builder(registryLookup.join(), UniformLootNumberProvider.create(0.0f, 1.0f))))
                        )
        );
    }
}
