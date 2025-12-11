package com.bwt.blocks;

import com.bwt.utils.Id;
import com.bwt.utils.RegistrationUtils;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.WoodType;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class MaterialInheritedBlock extends Block {
    public Block fullBlock;
    public boolean isWood = false;

    public MaterialInheritedBlock(Settings settings, Block fullBlock) {
        super(settings);
        this.fullBlock = fullBlock;
    }

    public boolean isWood() {
        return isWood;
    }

    public static void registerMaterialBlocks(
            ArrayList<SidingBlock> sidingBlocks,
            ArrayList<MouldingBlock> mouldingBlocks,
            ArrayList<CornerBlock> cornerBlocks,
            ArrayList<ColumnBlock> columnBlocks,
            ArrayList<PedestalBlock> pedestalBlocks,
            ArrayList<TableBlock> tableBlocks
    ) {
        Stream.concat(
            WoodType.stream()
                    .map(woodType -> Registries.BLOCK.getOptionalValue(Id.mc(woodType.name() + "_planks")))
                    .filter(Optional::isPresent)
                    .map(Optional::get),
            Stream.of(Blocks.BAMBOO_MOSAIC)
        ).forEach(block -> registerBlockSet(
                sidingBlocks,
                mouldingBlocks,
                cornerBlocks,
                columnBlocks,
                pedestalBlocks,
                tableBlocks,
                block,
                true
        ));

        Stream.of(
                BlockFamilies.COBBLESTONE,
                BlockFamilies.STONE,
                BlockFamilies.STONE_BRICK,
                BlockFamilies.MOSSY_STONE_BRICK,
                BlockFamilies.SANDSTONE,
                BlockFamilies.RED_SANDSTONE,
                BlockFamilies.BRICK,
                BlockFamilies.NETHER_BRICK,
                BlockFamilies.DIORITE,
                BlockFamilies.POLISHED_DIORITE,
                BlockFamilies.ANDESITE,
                BlockFamilies.POLISHED_ANDESITE,
                BlockFamilies.GRANITE,
                BlockFamilies.POLISHED_GRANITE,
                BlockFamilies.COBBLED_DEEPSLATE,
                BlockFamilies.TUFF,
                BlockFamilies.MUD_BRICK,
                BlockFamilies.PRISMARINE,
                BlockFamilies.END_STONE_BRICK,
                BlockFamilies.PURPUR
        )
                .map(BlockFamily::getBaseBlock)
                .forEach(block -> registerBlockSet(
                        sidingBlocks,
                        mouldingBlocks,
                        cornerBlocks,
                        columnBlocks,
                        pedestalBlocks,
                        tableBlocks,
                        block,
                        false
                ));
    }

    protected interface MaterialInheritedBlockFactory<T extends MaterialInheritedBlock> {
        T create(AbstractBlock.Settings settings, Block block, boolean isWood);
    }

    protected static <T extends MaterialInheritedBlock> T registerMaterialInheritedBlock(Block fullBlock, String blockId, MaterialInheritedBlockFactory<T> factory, boolean isWood) {
        return RegistrationUtils.registerBlockAndItem(
                blockId,
                settings -> factory.create(settings, fullBlock, isWood),
                AbstractBlock.Settings.copy(fullBlock)
        );
    }

    protected static void registerBlockSet(
            ArrayList<SidingBlock> sidingBlocks,
            ArrayList<MouldingBlock> mouldingBlocks,
            ArrayList<CornerBlock> cornerBlocks,
            ArrayList<ColumnBlock> columnBlocks,
            ArrayList<PedestalBlock> pedestalBlocks,
            ArrayList<TableBlock> tableBlocks,
            Block fullBlock,
            boolean isWood
    ) {
        String blockId = Registries.BLOCK.getId(fullBlock).getPath();
        sidingBlocks.add(registerMaterialInheritedBlock(
                fullBlock,
                blockId + "_siding",
                SidingBlock::new,
                isWood
        ));
        mouldingBlocks.add(registerMaterialInheritedBlock(
                fullBlock,
                blockId + "_moulding",
                MouldingBlock::new,
                isWood
        ));
        cornerBlocks.add(registerMaterialInheritedBlock(
                fullBlock,
                blockId + "_corner",
                CornerBlock::new,
                isWood
        ));
        columnBlocks.add(registerMaterialInheritedBlock(
                fullBlock,
                blockId +"_column",
                ColumnBlock::new,
                isWood
        ));
        pedestalBlocks.add(registerMaterialInheritedBlock(
                fullBlock,
                blockId + "_pedestal",
                PedestalBlock::new,
                isWood
        ));
        tableBlocks.add(registerMaterialInheritedBlock(
                fullBlock,
                blockId + "_table",
                TableBlock::new,
                isWood
        ));
    }
}
