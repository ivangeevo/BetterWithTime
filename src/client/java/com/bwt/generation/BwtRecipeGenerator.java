package com.bwt.generation;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.registry.RegistryWrapper;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class BwtRecipeGenerator extends FabricRecipeProvider {
    protected BlockDispenserClumpRecipeGenerator blockDispenserClumpRecipeGenerator;
    protected CauldronRecipeGenerator cauldronRecipeGenerator;
    protected CrucibleRecipeGenerator crucibleRecipeGenerator;
    protected CraftingRecipeGenerator craftingRecipeGenerator;
    protected VanillaRecipeGenerator vanillaRecipeGenerator;
    protected DisabledVanilaRecipeGenerator disabledVanilaRecipeGenerator;
    protected HopperRecipeGenerator hopperRecipeGenerator;
    protected MillStoneRecipeGenerator millStoneRecipeGenerator;
    protected MobSpawnerConversionRecipeGenerator mobSpawnerConversionRecipeGenerator;
    protected SawRecipeGenerator sawRecipeGenerator;
    protected TurntableRecipeGenerator turntableRecipeGenerator;
    protected KilnRecipeGenerator kilnRecipeGenerator;
    protected SoulForgeRecipeGenerator soulForgeRecipeGenerator;
    protected EmiDefaultsGenerator emiDefaultsGenerator;

    public BwtRecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
        this.blockDispenserClumpRecipeGenerator = new BlockDispenserClumpRecipeGenerator(output, registriesFuture);
        this.cauldronRecipeGenerator = new CauldronRecipeGenerator(output, registriesFuture);
        this.crucibleRecipeGenerator = new CrucibleRecipeGenerator(output, registriesFuture);
        this.craftingRecipeGenerator = new CraftingRecipeGenerator(output, registriesFuture);
        this.vanillaRecipeGenerator = new VanillaRecipeGenerator(output, registriesFuture);
        this.disabledVanilaRecipeGenerator = new DisabledVanilaRecipeGenerator(output, registriesFuture);
        this.hopperRecipeGenerator = new HopperRecipeGenerator(output, registriesFuture);
        this.millStoneRecipeGenerator = new MillStoneRecipeGenerator(output, registriesFuture);
        this.mobSpawnerConversionRecipeGenerator = new MobSpawnerConversionRecipeGenerator(output, registriesFuture);
        this.sawRecipeGenerator = new SawRecipeGenerator(output, registriesFuture);
        this.turntableRecipeGenerator = new TurntableRecipeGenerator(output, registriesFuture);
        this.kilnRecipeGenerator = new KilnRecipeGenerator(output, registriesFuture);
        this.soulForgeRecipeGenerator = new SoulForgeRecipeGenerator(output, registriesFuture);
        this.emiDefaultsGenerator = new EmiDefaultsGenerator(output);
    }

    @Override
    protected @NonNull RecipeGenerator getRecipeGenerator(RegistryWrapper.@NonNull WrapperLookup registryLookup, @NonNull RecipeExporter exporter) {
        return null;
    }

    @Override
    public @NonNull CompletableFuture<?> run(@NonNull DataWriter writer) {
        return CompletableFuture.allOf(
                disabledVanilaRecipeGenerator.run(writer),
                blockDispenserClumpRecipeGenerator.run(writer),
                cauldronRecipeGenerator.run(writer),
                crucibleRecipeGenerator.run(writer),
                craftingRecipeGenerator.run(writer),
                vanillaRecipeGenerator.run(writer),
                hopperRecipeGenerator.run(writer),
                millStoneRecipeGenerator.run(writer),
                mobSpawnerConversionRecipeGenerator.run(writer),
                sawRecipeGenerator.run(writer),
                turntableRecipeGenerator.run(writer),
                kilnRecipeGenerator.run(writer),
                soulForgeRecipeGenerator.run(writer),
                emiDefaultsGenerator.run(writer)
        );
    }

    @Override
    public String getName() {
        return "";
    }
}
