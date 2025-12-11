package com.bwt.blocks;

import com.bwt.blocks.axles.AxleBlock;
import com.bwt.blocks.axles.AxlePowerSourceBlock;
import com.bwt.blocks.axles.CreativePowerSourceBlock;
import com.bwt.blocks.block_dispenser.BlockDispenserBlock;
import com.bwt.blocks.blood_wood.BloodWoodBlocks;
import com.bwt.blocks.cauldron.CauldronBlock;
import com.bwt.blocks.crucible.CrucibleBlock;
import com.bwt.blocks.detector.DetectorBlock;
import com.bwt.blocks.detector.DetectorLogicBlock;
import com.bwt.blocks.dirt_slab.DirtPathSlabBlock;
import com.bwt.blocks.dirt_slab.DirtSlabBlock;
import com.bwt.blocks.dirt_slab.GrassSlabBlock;
import com.bwt.blocks.lens.LensBeamBlock;
import com.bwt.blocks.lens.LensBeamGlassBlock;
import com.bwt.blocks.lens.LensBlock;
import com.bwt.blocks.dirt_slab.MyceliumSlabBlock;
import com.bwt.blocks.mech_hopper.MechHopperBlock;
import com.bwt.blocks.mill_stone.MillStoneBlock;
import com.bwt.blocks.mining_charge.MiningChargeBlock;
import com.bwt.blocks.pulley.PulleyBlock;
import com.bwt.blocks.soul_forge.SoulForgeBlock;
import com.bwt.blocks.turntable.TurntableBlock;
import com.bwt.blocks.unfired_pottery.*;
import com.bwt.utils.DyeUtils;
import com.bwt.utils.Id;
import com.bwt.utils.RegistrationUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FlattenableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.Sherds;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;

import java.util.ArrayList;
import java.util.HashMap;

public class BwtBlocks implements ModInitializer {

    public static final AqueductBlock aqueductBlock = RegistrationUtils.registerBlockAndItem(
            "aqueduct_block",
            AqueductBlock::new,
            AbstractBlock.Settings.copy(Blocks.BRICKS)
    );
    public static final Block anchorBlock = RegistrationUtils.registerBlockAndItem(
            "anchor",
            AnchorBlock::new,
            AbstractBlock.Settings.create()
                    .hardness(2f)
                    .sounds(BlockSoundGroup.STONE)
                    .nonOpaque()
                    .solid()
                    .requiresTool()
    );
    public static final Block axleBlock = RegistrationUtils.registerBlockAndItem(
            "axle",
            AxleBlock::new,
            AbstractBlock.Settings.create()
                    .hardness(2F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()
                    .solid()
                    .nonOpaque()
    );
    public static final Block axlePowerSourceBlock = RegistrationUtils.registerBlock(
            "axle_power_source",
            AxlePowerSourceBlock::new,
            AbstractBlock.Settings.copy(axleBlock)
    );
    public static final Block bellowsBlock = RegistrationUtils.registerBlockAndItem(
            "bellows",
            BellowsBlock::new,
            AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)
    );
    public static final BlockDispenserBlock blockDispenserBlock = RegistrationUtils.registerBlockAndItem(
            "block_dispenser",
            BlockDispenserBlock::new,
            AbstractBlock.Settings.copy(Blocks.DISPENSER)
                    .hardness(3.5f)
    );

    public static final BloodWoodBlocks bloodWoodBlocks = new BloodWoodBlocks().initialize();

    public static final Block buddyBlock = RegistrationUtils.registerBlockAndItem(
            "buddy_block",
            BuddyBlock::new,
            AbstractBlock.Settings.create()
                    .hardness(3.5f)
                    .sounds(BlockSoundGroup.STONE)
                    .mapColor(MapColor.LIGHT_GRAY)
                    .requiresTool()
    );
    public static final Block cauldronBlock = RegistrationUtils.registerBlockAndItem(
            "cauldron",
            CauldronBlock::new,
            AbstractBlock.Settings.create()
                    .solidBlock(Blocks::never)
                    .nonOpaque()
                    .hardness(3.5f)
                    .resistance(10f)
                    .sounds(BlockSoundGroup.METAL)
                    .mapColor(MapColor.BLACK)
                    .requiresTool()
    );
    public static final Block concentratedHellfireBlock = RegistrationUtils.registerBlockAndItem(
            "concentrated_hellfire_block",
            AbstractBlock.Settings.create()
                    .hardness(2f)
                    .requiresTool()
                    .mapColor(MapColor.BRIGHT_RED)
                    .sounds(BlockSoundGroup.METAL)
    );
    public static final Block companionCubeBlock = RegistrationUtils.registerBlockAndItem(
            "companion_cube",
            CompanionCubeBlock::new,
            AbstractBlock.Settings.copy(Blocks.WHITE_WOOL)
                    .hardness(0.4f)
    );
    public static final Block companionSlabBlock = RegistrationUtils.registerBlockAndItem(
            "companion_slab",
            CompanionSlabBlock::new,
            AbstractBlock.Settings.copy(companionCubeBlock));
    public static final CreativePowerSourceBlock creativePowerSouceBlock = RegistrationUtils.registerBlockAndItem(
            "creative_power_source",
            CreativePowerSourceBlock::new,
            AbstractBlock.Settings.create()
                    .hardness(2F)
                    .sounds(BlockSoundGroup.WOOD)
                    .solid()
                    .nonOpaque()
    );
    public static final Block crucibleBlock = RegistrationUtils.registerBlockAndItem(
            "crucible",
            CrucibleBlock::new,
            AbstractBlock.Settings.create()
                    .solidBlock(Blocks::never)
                    .nonOpaque()
                    .hardness(0.6f)
                    .resistance(3f)
                    .sounds(BlockSoundGroup.GLASS)
                    .mapColor(MapColor.WHITE_GRAY)
                    .requiresTool()
    );
    public static final Block detectorBlock = RegistrationUtils.registerBlockAndItem(
            "detector_block",
            DetectorBlock::new,
            AbstractBlock.Settings.copy(Blocks.DISPENSER)
                    .hardness(3.5f)
    );
    public static final Block detectorLogicBlock = RegistrationUtils.registerBlock(
            "detector_logic_block",
            DetectorLogicBlock::new,
            AbstractBlock.Settings.create()
                    .replaceable()
                    .noCollision()
                    .dropsNothing()
                    .pistonBehavior(PistonBehavior.DESTROY)
                    .air()
    );
    public static final Block dungBlock = RegistrationUtils.registerBlockAndItem(
            "dung_block",
            AbstractBlock.Settings.create()
                    .hardness(2f)
                    .mapColor(MapColor.BROWN)
                    .sounds(BlockSoundGroup.HONEY)
    );
    public static final Block gearBoxBlock = RegistrationUtils.registerBlockAndItem(
            "gear_box",
            GearBoxBlock::new,
            AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)
                    .hardness(2F)
    );
    public static final Block grateBlock = RegistrationUtils.registerBlockAndItem(
            "grate",
            PaneBlock::new,
            AbstractBlock.Settings.create()
                    .hardness(0.5f)
                    .sounds(BlockSoundGroup.WOOD)
                    .nonOpaque()
    );
    public static final Block handCrankBlock = RegistrationUtils.registerBlockAndItem(
            "hand_crank",
            HandCrankBlock::new,
            AbstractBlock.Settings.create()
                    .hardness(0.5f)
                    .sounds(BlockSoundGroup.WOOD)
                    .solid()
                    .nonOpaque()
                    .allowsSpawning(Blocks::never)
                    .suffocates(Blocks::never)
                    .blockVision(Blocks::never)
                    .requiresTool()
    );
    public static final Block hempCropBlock = RegistrationUtils.registerBlock(
            "hemp_crop_block",
            HempCropBlock::new,
            AbstractBlock.Settings.copy(Blocks.SUGAR_CANE)
    );
    public static final Block hibachiBlock = RegistrationUtils.registerBlockAndItem(
            "hibachi",
            HibachiBlock::new,
            AbstractBlock.Settings.create()
                    .hardness(3.5f)
                    .sounds(BlockSoundGroup.STONE)
                    .requiresTool()
    );
    public static final Block hopperBlock = RegistrationUtils.registerBlockAndItem(
            "hopper",
            MechHopperBlock::new,
            AbstractBlock.Settings.create()
                    .hardness(2f)
                    .sounds(BlockSoundGroup.WOOD)
                    .solid()
                    .nonOpaque()
    );
//    public static final Block infernalEnchanterBlock = RegistrationUtils.registerBlockAndItem(
//            "infernal_enchanter",
//            InfernalEnchanterBlock::new,
//            AbstractBlock.Settings.create()
//    );
    public static final Block kilnBlock = RegistrationUtils.registerBlock(
            "kiln",
            KilnBlock::new,
            AbstractBlock.Settings.copy(Blocks.BRICKS)
    );
    public static final LensBlock lensBlock = RegistrationUtils.registerBlockAndItem(
            "lens",
            LensBlock::new,
            AbstractBlock.Settings.create()
                    .hardness(3.5f)
                    .sounds(BlockSoundGroup.METAL)
                    .solid()
                    .pistonBehavior(PistonBehavior.BLOCK)
    );
    public static final LensBeamBlock lensBeamBlock = RegistrationUtils.registerBlock(
            "lens_beam",
            LensBeamBlock::new,
            AbstractBlock.Settings.create()
                    .replaceable()
                    .noCollision()
                    .dropsNothing()
                    .pistonBehavior(PistonBehavior.DESTROY)
                    .luminance(state -> state.get(LensBeamBlock.TERMINUS) ? 14 : 0)
                    .emissiveLighting(((state, world, pos) -> state.get(LensBeamBlock.TERMINUS)))
    );
    public static final LensBeamGlassBlock lensBeamGlassBlock = RegistrationUtils.registerBlock(
            "lens_beam_glass",
            settings -> new LensBeamGlassBlock(Blocks.GLASS, settings),
            AbstractBlock.Settings
                    .copy(Blocks.GLASS)
                    .luminance(state -> state.get(LensBeamBlock.TERMINUS) ? 14 : 0)
                    .emissiveLighting(((state, world, pos) -> state.get(LensBeamBlock.TERMINUS)))
    );
    public static final Block lightBlockBlock = RegistrationUtils.registerBlockAndItem(
            "light_block",
            LightBlock::new,
            AbstractBlock.Settings.copy(Blocks.GLASS)
                    .strength(0.4f)
                    .luminance(Blocks.createLightLevelFromLitBlockState(15))
    );
    public static final Block millStoneBlock = RegistrationUtils.registerBlockAndItem(
            "mill_stone",
            MillStoneBlock::new,
            AbstractBlock.Settings.copy(Blocks.DISPENSER)
                    .hardness(3.5f)
    );
    public static final MiningChargeBlock miningChargeBlock = RegistrationUtils.registerBlockAndItem(
            "mining_charge",
            MiningChargeBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.BROWN)
                    .breakInstantly()
                    .sounds(BlockSoundGroup.GRASS)
                    .burnable()
                    .solidBlock(Blocks::never)
    );
    public static final Block obsidianPressurePlateBlock = RegistrationUtils.registerBlockAndItem(
            "obsidian_pressure_plate",
            ObsidianPressurePlateBlock::new,
            AbstractBlock.Settings.copy(Blocks.STONE_PRESSURE_PLATE)
                    .strength(50.0f, 1200.0f)
    );
    public static final Block obsidianDetectorRailBlock = RegistrationUtils.registerBlockAndItem(
            "obsidian_detector_rail",
            DetectorRailBlock::new,
            AbstractBlock.Settings.copy(Blocks.DETECTOR_RAIL)
                    .strength(25.0f, 1200.0f)
    );
    public static final Block planterBlock = RegistrationUtils.registerBlockAndItem(
            "planter",
            PlanterBlock::new,
            AbstractBlock.Settings.copy(Blocks.TERRACOTTA)
                    .nonOpaque()
                    .hardness(0.6f)
    );
    public static final Block soilPlanterBlock = RegistrationUtils.registerBlockAndItem(
            "soil_planter",
            SoilPlanterBlock::new,
            AbstractBlock.Settings.copy(planterBlock)
    );
    public static final Block soulSandPlanterBlock = RegistrationUtils.registerBlockAndItem(
            "soul_sand_planter",
            SoulSandPlanterBlock::new,
            AbstractBlock.Settings.copy(planterBlock)
    );
    public static final Block grassPlanterBlock = RegistrationUtils.registerBlockAndItem(
            "grass_planter",
            GrassPlanterBlock::new,
            AbstractBlock.Settings.copy(planterBlock)
    );
    public static final Block paddingBlock = RegistrationUtils.registerBlockAndItem(
            "padding_block",
            PaddingBlock::new,
            AbstractBlock.Settings.create()
                    .hardness(2f)
                    .mapColor(MapColor.OFF_WHITE)
                    .sounds(BlockSoundGroup.WOOL)
    );
    public static final Block platformBlock = RegistrationUtils.registerBlockAndItem(
            "platform",
            PlatformBlock::new,
            AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)
                    .nonOpaque()
                    .allowsSpawning(Blocks::never)
                    .solidBlock(Blocks::never)
                    .suffocates(Blocks::never)
                    .blockVision(Blocks::never)
                    .hardness(2f)
                    .burnable()
    );
    public static final Block pulleyBlock = RegistrationUtils.registerBlockAndItem(
            "pulley",
            PulleyBlock::new,
            AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)
                    .hardness(2f)
                    .mapColor(MapColor.TERRACOTTA_BROWN)
                    .pistonBehavior(PistonBehavior.IGNORE)
    );
    public static final Block redstoneClutchBlock = RegistrationUtils.registerBlockAndItem(
            "redstone_clutch",
            RedstoneClutchBlock::new,
            AbstractBlock.Settings.copy(gearBoxBlock));
    public static final Block ropeCoilBlock = RegistrationUtils.registerBlockAndItem(
            "rope_coil_block",
            AbstractBlock.Settings.create()
                    .hardness(1f)
                    .mapColor(MapColor.BROWN)
                    .sounds(BlockSoundGroup.GRASS)
    );
    public static final RopeBlock ropeBlock = RegistrationUtils.registerBlock(
            "rope",
            RopeBlock::new,
            AbstractBlock.Settings.create()
                    .hardness(0.5f)
                    .sounds(BlockSoundGroup.GRASS)
                    .pistonBehavior(PistonBehavior.DESTROY)
    );
    public static final Block sawBlock = RegistrationUtils.registerBlockAndItem(
            "saw",
            SawBlock::new,
            AbstractBlock.Settings.create()
                    .hardness(2f)
                    .burnable()
                    .sounds(BlockSoundGroup.WOOD)
                    .nonOpaque()
    );
    public static final ScrewPumpBlock screwPumpBlock = RegistrationUtils.registerBlockAndItem(
            "screw_pump",
            ScrewPumpBlock::new,
            AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)
                    .hardness(2f)
                    .resistance(5f)
    );
    public static final Block slatsBlock = RegistrationUtils.registerBlockAndItem(
            "slats",
            PaneBlock::new,
            AbstractBlock.Settings.create()
                    .strength(0.5f)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()
                    .nonOpaque()
    );
    public static final Block soapBlock = RegistrationUtils.registerBlockAndItem(
            "soap_block",
            SimpleFacingBlock::new,
            AbstractBlock.Settings.create()
                    .hardness(2f)
                    .mapColor(MapColor.PINK)
                    .sounds(BlockSoundGroup.SLIME)
    );
//    public static final Block stakeBlock = RegistrationUtils.registerBlockAndItem(
//            "stake",
//            StakeBlock::new,
//            AbstractBlock.Settings.create()
//    );
    public static final StokedFireBlock stokedFireBlock = RegistrationUtils.registerBlock(
            "stoked_fire",
            StokedFireBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.BRIGHT_RED)
                    .replaceable()
                    .noCollision()
                    .breakInstantly()
                    .luminance(state -> 15)
                    .sounds(BlockSoundGroup.WOOL)
                    .pistonBehavior(PistonBehavior.DESTROY)
    );
    public static final Block stoneDetectorRailBlock = RegistrationUtils.registerBlockAndItem(
            "stone_detector_rail",
            DetectorRailBlock::new,
            AbstractBlock.Settings.copy(Blocks.DETECTOR_RAIL)
    );
    public static final Block soulForgeBlock = RegistrationUtils.registerBlockAndItem(
            "soul_forge",
            SoulForgeBlock::new,
            AbstractBlock.Settings.copy(Blocks.ANVIL)
    );
    public static final Block turntableBlock = RegistrationUtils.registerBlockAndItem(
            "turntable",
            TurntableBlock::new,
            AbstractBlock.Settings.create()
                    .strength(2f)
                    .sounds(BlockSoundGroup.STONE)
                    .mapColor(Blocks.PISTON_HEAD.getDefaultMapColor())
    );
    public static final UnfiredPotteryBlock unfiredDecoratedPotBlock = RegistrationUtils.registerBlockAndItem(
            "unfired_decorated_pot",
            UnfiredDecoratedPotBlock::new,
            AbstractBlock.Settings.copy(Blocks.CLAY)
                    .nonOpaque()
                    .solidBlock(Blocks::never)
    );
    public static final UnfiredPotteryBlock unfiredDecoratedPotBlockWithSherds = registerUnfiredDecoratedPotBlockWithSherds();
    public static final UnfiredPotteryBlock unfiredCrucibleBlock = RegistrationUtils.registerBlockAndItem(
            "unfired_crucible",
            UnfiredCrucibleBlock::new,
            AbstractBlock.Settings.copy(Blocks.CLAY)
                    .nonOpaque()
                    .solidBlock(Blocks::never)
    );
    public static final UnfiredPotteryBlock unfiredPlanterBlock = RegistrationUtils.registerBlockAndItem(
            "unfired_planter",
            UnfiredPlanterBlock::new,
            AbstractBlock.Settings.copy(Blocks.CLAY)
                    .nonOpaque()
                    .solidBlock(Blocks::never)
    );
    public static final UnfiredPotteryBlock unfiredVaseBlock = RegistrationUtils.registerBlockAndItem(
            "unfired_vase",
            UnfiredVaseBlock::new,
            AbstractBlock.Settings.copy(Blocks.CLAY)
                    .nonOpaque()
                    .solidBlock(Blocks::never)
    );
    public static final UnfiredPotteryBlock unfiredUrnBlock = RegistrationUtils.registerBlockAndItem(
            "unfired_urn",
            UnfiredUrnBlock::new,
            AbstractBlock.Settings.copy(Blocks.CLAY)
                    .nonOpaque()
                    .solidBlock(Blocks::never)
    );
    public static final UnfiredPotteryBlock unfiredFlowerPotBlock = RegistrationUtils.registerBlockAndItem(
            "unfired_flower_pot",
            UnfiredFlowerPotBlock::new,
            AbstractBlock.Settings.copy(Blocks.CLAY)
                    .nonOpaque()
                    .solidBlock(Blocks::never)
    );
    public static final Block urnBlock = RegistrationUtils.registerBlockAndItem(
            "urn",
            UrnBlock::new,
            AbstractBlock.Settings.copy(Blocks.TERRACOTTA)
                    .nonOpaque()
                    .solidBlock(Blocks::never)
                    .allowsSpawning(Blocks::never)
                    .hardness(2f)
    );
    public static final Block wickerPaneBlock = RegistrationUtils.registerBlockAndItem(
            "wicker",
            PaneBlock::new,
            AbstractBlock.Settings.create()
                    .strength(0.5f)
                    .sounds(BlockSoundGroup.GRASS)
                    .burnable()
                    .nonOpaque()
    );
    public static final Block wickerBlock = RegistrationUtils.registerBlockAndItem(
            "wicker_block",
            AbstractBlock.Settings.create()
                    .hardness(2f)
                    .burnable()
                    .mapColor(MapColor.SPRUCE_BROWN)
                    .sounds(BlockSoundGroup.GRASS)
    );
    public static final Block wickerSlabBlock = RegistrationUtils.registerBlockAndItem(
            "wicker_slab",
            SlabBlock::new,
            AbstractBlock.Settings.copy(wickerBlock)
    );
    public static final Block vineTrapBlock = RegistrationUtils.registerBlockAndItem(
            "vine_trap",
            VineTrapBlock::new,
            AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)
                    .allowsSpawning(Blocks::never)
                    .noCollision()
    );
    public static final Block dirtSlabBlock = RegistrationUtils.registerBlockAndItem(
            "dirt_slab",
            settings -> new DirtSlabBlock(settings, Blocks.DIRT),
            AbstractBlock.Settings.copy(Blocks.DIRT)
    );
    public static final Block dirtPathSlabBlock = RegistrationUtils.registerBlockAndItem(
            "dirt_path_slab",
            settings -> new DirtPathSlabBlock(settings, Blocks.DIRT_PATH),
            AbstractBlock.Settings.copy(Blocks.DIRT_PATH)
    );
    public static final Block grassSlabBlock = RegistrationUtils.registerBlockAndItem(
            "grass_slab",
            settings -> new GrassSlabBlock(settings, Blocks.GRASS_BLOCK),
            AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK)
    );
    public static final Block myceliumSlabBlock = RegistrationUtils.registerBlockAndItem(
            "mycelium_slab",
            settings -> new MyceliumSlabBlock(settings, Blocks.MYCELIUM),
            AbstractBlock.Settings.copy(Blocks.MYCELIUM)
    );
    public static final Block podzolSlabBlock = RegistrationUtils.registerBlockAndItem(
            "podzol_slab",
            settings -> new MyceliumSlabBlock(settings, Blocks.PODZOL),
            AbstractBlock.Settings.copy(Blocks.PODZOL)
    );
    public static final Block netherGroth = RegistrationUtils.registerBlockAndItem(
            "nether_groth",
            NetherGrothBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_RED)
                    .solidBlock(Blocks::never)
                    .ticksRandomly()
                    .strength(0.2f)
                    .velocityMultiplier(0.4F)
                    .sounds(BlockSoundGroup.FUNGUS)
                    .pistonBehavior(PistonBehavior.DESTROY)
                    .velocityMultiplier(0.8f)
    );
    public static final Block grothedNetherrackBlock = RegistrationUtils.registerBlock(
            "grothed_netherrack",
            GrothedNetherrackBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_RED)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresTool()
                    .strength(0.4F)
                    .sounds(BlockSoundGroup.NETHERRACK)
    );

    public static final ArrayList<SidingBlock> sidingBlocks = new ArrayList<>();
    public static final ArrayList<MouldingBlock> mouldingBlocks = new ArrayList<>();
    public static final ArrayList<CornerBlock> cornerBlocks = new ArrayList<>();
    public static final ArrayList<ColumnBlock> columnBlocks = new ArrayList<>();
    public static final ArrayList<PedestalBlock> pedestalBlocks = new ArrayList<>();
    public static final ArrayList<TableBlock> tableBlocks = new ArrayList<>();

    public static final HashMap<DyeColor, SlabBlock> woolSlabBlocks = new HashMap<>();
    public static final HashMap<DyeColor, VaseBlock> vaseBlocks = new HashMap<>();

    static {
        MaterialInheritedBlock.registerMaterialBlocks(
                sidingBlocks, mouldingBlocks, cornerBlocks,
                columnBlocks, pedestalBlocks, tableBlocks
        );
        VaseBlock.registerColors(vaseBlocks);
        DyeUtils.WOOL_COLORS.forEach((dyeColor, woolBlock) ->
                woolSlabBlocks.put(dyeColor, RegistrationUtils.registerBlockAndItem(
                        dyeColor.name() + "_wool_slab",
                        SlabBlock::new,
                        AbstractBlock.Settings.copy(woolBlock)
                ))
        );
    }

    @Override
    public void onInitialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(content -> {
            content.addAfter(Items.NETHER_WART, BwtBlocks.netherGroth);
            content.addAfter(Items.CHERRY_LOG, BwtBlocks.bloodWoodBlocks.logBlock);
            content.addAfter(Items.CHERRY_LEAVES, BwtBlocks.bloodWoodBlocks.leavesBlock);
            content.addAfter(Items.CHERRY_SAPLING, BwtBlocks.bloodWoodBlocks.saplingBlock);
            content.addAfter(Blocks.DIRT, dirtSlabBlock);
            content.addAfter(Blocks.DIRT_PATH, dirtPathSlabBlock);
            content.addAfter(Blocks.GRASS_BLOCK, grassSlabBlock);
            content.addAfter(Blocks.MYCELIUM, myceliumSlabBlock);
            content.addAfter(Blocks.PODZOL, podzolSlabBlock);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(content -> {
            content.addAll(DyeUtils.streamColorItemsSorted(vaseBlocks).map(vaseBlock -> vaseBlock.asItem().getDefaultStack()).toList());
            content.addAll(DyeUtils.streamColorItemsSorted(woolSlabBlocks).map(woolSlabBlock -> woolSlabBlock.asItem().getDefaultStack()).toList());
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(content -> {
            content.add(axleBlock);
            content.add(creativePowerSouceBlock);
            content.add(gearBoxBlock);
            content.add(redstoneClutchBlock);
            content.add(hibachiBlock);
            content.add(lightBlockBlock);
            content.add(blockDispenserBlock);
            content.add(obsidianPressurePlateBlock);
            content.add(detectorBlock);
            content.add(buddyBlock);
            content.add(millStoneBlock);
            content.add(handCrankBlock);
            content.add(stoneDetectorRailBlock);
            content.add(obsidianDetectorRailBlock);
            content.add(sawBlock);
            content.add(hopperBlock);
            content.add(pulleyBlock);
            content.add(anchorBlock);
            content.add(platformBlock);
            content.add(turntableBlock);
            content.add(bellowsBlock);
            content.add(cauldronBlock);
            content.add(crucibleBlock);
            content.add(soulForgeBlock);
            content.add(lensBlock);
//            content.add(BwtBlocks.aqueductBlock);
//            content.add(BwtBlocks.screwPumpBlock);
            content.addAfter(Items.TNT, miningChargeBlock);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {
            content.add(cauldronBlock);
            content.add(crucibleBlock);
            content.add(planterBlock);
            content.add(soilPlanterBlock);
            content.add(soulSandPlanterBlock);
            content.add(grassPlanterBlock);
            content.add(urnBlock);
            content.add(unfiredDecoratedPotBlock);
            content.add(unfiredCrucibleBlock);
            content.add(unfiredPlanterBlock);
            content.add(unfiredVaseBlock);
            content.add(unfiredUrnBlock);
            content.add(unfiredFlowerPotBlock);
            content.addAfter(Items.CRAFTING_TABLE, soulForgeBlock);
            content.addAfter(Items.SCAFFOLDING, BwtBlocks.vineTrapBlock);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> {
            content.addAfter(Items.CHERRY_BUTTON,
                    BwtBlocks.bloodWoodBlocks.logBlock,
                    BwtBlocks.bloodWoodBlocks.woodBlock,
                    BwtBlocks.bloodWoodBlocks.strippedLogBlock,
                    BwtBlocks.bloodWoodBlocks.strippedWoodBlock,
                    BwtBlocks.bloodWoodBlocks.planksBlock,
                    BwtBlocks.bloodWoodBlocks.stairsBlock,
                    BwtBlocks.bloodWoodBlocks.slabBlock,
                    BwtBlocks.bloodWoodBlocks.fenceBlock,
                    BwtBlocks.bloodWoodBlocks.fenceGateBlock,
                    BwtBlocks.bloodWoodBlocks.doorBlock,
                    BwtBlocks.bloodWoodBlocks.trapdoorBlock,
                    BwtBlocks.bloodWoodBlocks.pressurePlateBlock,
                    BwtBlocks.bloodWoodBlocks.buttonBlock
            );
            for (int i = 0; i < sidingBlocks.size(); i++) {
                SidingBlock sidingBlock = sidingBlocks.get(i);
                MouldingBlock mouldingBlock = mouldingBlocks.get(i);
                CornerBlock cornerBlock = cornerBlocks.get(i);
                ColumnBlock columnBlock = columnBlocks.get(i);
                PedestalBlock pedestalBlock = pedestalBlocks.get(i);
                TableBlock tableBlock = tableBlocks.get(i);
                if (content.getDisplayStacks().stream().anyMatch(itemStack -> itemStack.isOf(sidingBlock.fullBlock.asItem()))) {
                    content.addAfter(sidingBlock.fullBlock, sidingBlock, mouldingBlock, cornerBlock, columnBlock, pedestalBlock, tableBlock);
                }
            }
            content.add(companionCubeBlock);
            content.add(companionSlabBlock);
            content.add(grateBlock);
            content.add(slatsBlock);
            content.add(wickerPaneBlock);
            content.add(wickerBlock);
            content.add(wickerSlabBlock);
            content.add(platformBlock);
            content.add(soapBlock);
            content.add(dungBlock);
            content.add(paddingBlock);
            content.add(ropeCoilBlock);
            content.add(concentratedHellfireBlock);
            content.add(dirtSlabBlock);
            content.add(dirtPathSlabBlock);
            content.add(grassSlabBlock);
            content.add(myceliumSlabBlock);
            content.add(podzolSlabBlock);
        });

        FlattenableBlockRegistry.register(BwtBlocks.grassSlabBlock, BwtBlocks.dirtPathSlabBlock.getDefaultState());
        FlattenableBlockRegistry.register(BwtBlocks.dirtSlabBlock, BwtBlocks.dirtPathSlabBlock.getDefaultState());
        FlattenableBlockRegistry.register(BwtBlocks.myceliumSlabBlock, BwtBlocks.dirtPathSlabBlock.getDefaultState());
        FlattenableBlockRegistry.register(BwtBlocks.podzolSlabBlock, BwtBlocks.dirtPathSlabBlock.getDefaultState());
    }



    private static UnfiredDecoratedPotBlockWithSherds registerUnfiredDecoratedPotBlockWithSherds() {
        String id = "unfired_decorated_pot_with_sherds";
        UnfiredDecoratedPotBlockWithSherds block = RegistrationUtils.registerBlock(
                id,
                UnfiredDecoratedPotBlockWithSherds::new,
                AbstractBlock.Settings.copy(Blocks.CLAY)
                        .nonOpaque()
                        .solidBlock(Blocks::never)
        );
        RegistryKey<Item> itemKey = RegistrationUtils.keyOfItem(id);

        BlockItem blockItem = new BlockItem(
                unfiredDecoratedPotBlockWithSherds,
                new Item.Settings()
                        .component(DataComponentTypes.POT_DECORATIONS, Sherds.DEFAULT)
                        .registryKey(itemKey)
                        .useBlockPrefixedTranslationKey()
        );
        Registry.register(Registries.ITEM, itemKey, blockItem);
        return block;
    }
}
