package com.bwt.generation;

import com.bwt.recipes.block_dispenser_clump.BlockDispenserClumpRecipe;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class BlockDispenserClumpRecipeGenerator extends FabricRecipeProvider {
    public BlockDispenserClumpRecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    protected @NonNull RecipeGenerator getRecipeGenerator(RegistryWrapper.@NonNull WrapperLookup registryLookup, @NonNull RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate() {
                BlockDispenserClumpRecipe.JsonBuilder.create().ingredient(Items.CLAY_BALL).count(4).output(Blocks.CLAY).offerTo(exporter);
                BlockDispenserClumpRecipe.JsonBuilder.create().ingredient(Items.SNOWBALL).count(4).output(Blocks.SNOW_BLOCK).offerTo(exporter);
                BlockDispenserClumpRecipe.JsonBuilder.create().ingredient(Items.RAW_IRON).count(9).output(Blocks.RAW_IRON_BLOCK).offerTo(exporter);
                BlockDispenserClumpRecipe.JsonBuilder.create().ingredient(Items.RAW_GOLD).count(9).output(Blocks.RAW_GOLD_BLOCK).offerTo(exporter);
                BlockDispenserClumpRecipe.JsonBuilder.create().ingredient(Items.RAW_COPPER).count(9).output(Blocks.RAW_COPPER_BLOCK).offerTo(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return "";
    }
}
