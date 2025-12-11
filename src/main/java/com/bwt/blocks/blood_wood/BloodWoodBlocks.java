package com.bwt.blocks.blood_wood;

import com.bwt.features.BwtConfiguredFeatures;
import com.bwt.utils.Id;
import com.bwt.utils.RegistrationUtils;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HangingSignItem;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;

import java.util.Optional;
import java.util.function.Function;

public class BloodWoodBlocks {
    public BlockSetType blockSetType;
    public WoodType woodType;
    public BlockFamily blockFamily;

    public Block logBlock;
    public Block strippedLogBlock;
    public Block woodBlock;
    public Block strippedWoodBlock;
    public Block leavesBlock;
    public Block saplingBlock;
    public BlockItem saplingItem;
    public Block pottedSaplingBlock;

    public Block planksBlock;
    public Block buttonBlock;
    public Block fenceBlock;
    public Block fenceGateBlock;
    public Block pressurePlateBlock;
    public Block slabBlock;
    public Block stairsBlock;
    public Block doorBlock;
    public Block trapdoorBlock;


    public BloodWoodBlocks initialize() {
        blockSetType = BlockSetTypeBuilder.copyOf(BlockSetType.CRIMSON).register(Id.of("blood_wood"));
        woodType = WoodTypeBuilder.copyOf(WoodType.CRIMSON).register(Id.of("blood_wood"), blockSetType);

        logBlock = RegistrationUtils.registerBlockAndItem(
                "blood_wood_log",
                BloodWoodLogBlock::new,
                Blocks.createLogSettings(MapColor.DARK_CRIMSON, MapColor.OFF_WHITE, BlockSoundGroup.NETHER_STEM)
        );
        strippedLogBlock = RegistrationUtils.registerBlockAndItem(
                "stripped_blood_wood_log",
                PillarBlock::new,
                Blocks.createLogSettings(MapColor.DARK_CRIMSON, MapColor.DARK_CRIMSON, BlockSoundGroup.NETHER_STEM)
        );

        woodBlock = RegistrationUtils.registerBlockAndItem(
                "blood_wood_wood",
                PillarBlock::new,
                Blocks.createLogSettings(MapColor.OFF_WHITE, MapColor.OFF_WHITE, BlockSoundGroup.NETHER_STEM)
        );
        strippedWoodBlock = RegistrationUtils.registerBlockAndItem(
                "stripped_blood_wood",
                PillarBlock::new,
                Blocks.createLogSettings(MapColor.DARK_CRIMSON, MapColor.DARK_CRIMSON, BlockSoundGroup.NETHER_STEM)
        );

        leavesBlock = RegistrationUtils.registerBlockAndItem(
                "blood_wood_leaves",
                BloodWoodLeavesBlock::new,
                AbstractBlock.Settings.create()
                        .mapColor(MapColor.DARK_GREEN)
                        .strength(0.2F)
                        .ticksRandomly()
                        .sounds(BlockSoundGroup.GRASS)
                        .nonOpaque()
                        .allowsSpawning(Blocks::canSpawnOnLeaves)
                        .suffocates(Blocks::never)
                        .blockVision(Blocks::never)
                        .burnable()
                        .pistonBehavior(PistonBehavior.DESTROY)
                        .solidBlock(Blocks::never)
        );
        saplingBlock = RegistrationUtils.registerBlock(
                "blood_wood_sapling",
                settings -> new BloodWoodSaplingBlock(
                        new SaplingGenerator(
                                Id.of("blood_wood").toString(),
                                Optional.empty(),
                                Optional.of(BwtConfiguredFeatures.BLOOD_WOOD_KEY),
                                Optional.empty()
                        ),
                        settings
                ),
                AbstractBlock.Settings.copy(Blocks.OAK_SAPLING).mapColor(MapColor.RED)
        );
        saplingItem = RegistrationUtils.registerBlockItem(saplingBlock, "blood_wood_sapling");
        pottedSaplingBlock = RegistrationUtils.registerBlock(
                "potted_blood_wood_sapling",
                settings -> new FlowerPotBlock(saplingBlock, settings),
                Blocks.createFlowerPotSettings()
        );

        planksBlock = RegistrationUtils.registerBlockAndItem(
                "blood_wood_planks",
                Block::new,
                AbstractBlock.Settings.copy(Blocks.CRIMSON_PLANKS)
        );
        buttonBlock = RegistrationUtils.registerBlockAndItem(
                "blood_wood_button",
                settings -> new ButtonBlock(BlockSetType.OAK, 30, settings),
                Blocks.createButtonSettings()
        );
        fenceBlock = RegistrationUtils.registerBlockAndItem(
                "blood_wood_fence",
                FenceBlock::new,
                AbstractBlock.Settings.copy(Blocks.CRIMSON_FENCE)
        );
        fenceGateBlock = RegistrationUtils.registerBlockAndItem(
                "blood_wood_fence_gate",
                settings -> new FenceGateBlock(woodType, settings),
                AbstractBlock.Settings.copy(Blocks.CRIMSON_FENCE_GATE)
        );
        pressurePlateBlock = RegistrationUtils.registerBlockAndItem(
                "blood_wood_pressure_plate",
                settings -> new PressurePlateBlock(blockSetType, settings),
                AbstractBlock.Settings.copy(Blocks.CRIMSON_PRESSURE_PLATE)
        );
        slabBlock = RegistrationUtils.registerBlockAndItem(
                "blood_wood_slab",
                SlabBlock::new,
                AbstractBlock.Settings.copy(Blocks.CRIMSON_SLAB)
        );
        stairsBlock = RegistrationUtils.registerBlockAndItem(
                "blood_wood_stairs",
                settings -> new StairsBlock(planksBlock.getDefaultState(), settings),
                AbstractBlock.Settings.copy(planksBlock)
        );
        doorBlock = RegistrationUtils.registerBlockAndItem(
                "blood_wood_door",
                settings -> new DoorBlock(blockSetType, settings),
                AbstractBlock.Settings.copy(Blocks.CRIMSON_DOOR)
        );
        trapdoorBlock = RegistrationUtils.registerBlockAndItem(
                "blood_wood_trapdoor",
                settings -> new TrapdoorBlock(blockSetType, settings),
                AbstractBlock.Settings.copy(Blocks.CRIMSON_TRAPDOOR)
        );

        blockFamily = BlockFamilies.register(planksBlock)
                .button(buttonBlock)
                .fence(fenceBlock)
                .fenceGate(fenceGateBlock)
                .pressurePlate(pressurePlateBlock)
                .slab(slabBlock)
                .stairs(stairsBlock)
                .door(doorBlock)
                .trapdoor(trapdoorBlock)
                .group("wooden")
                .unlockCriterionName("has_planks")
                .build();
        return this;
    }
}
