package com.bwt.blocks;

import com.bwt.utils.RegistrationUtils;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.Arrays;
import java.util.HashMap;

public class VaseBlock extends Block {
    public static VoxelShape outlineShape = Block.createCuboidShape(3, 0, 3, 13, 16, 13);

    protected DyeColor dyeColor;
    public VaseBlock(DyeColor color, Settings settings) {
        super(settings);
        this.dyeColor = color;
    }

    public static void registerColors(HashMap<DyeColor, VaseBlock> vaseBlocks) {
        Arrays.stream(DyeColor.values()).forEach(dyeColor -> {
            VaseBlock vaseBlock = RegistrationUtils.registerBlockAndItem(
                    "vase_" + dyeColor.name(),
                    settings -> new VaseBlock(dyeColor, settings),
                    AbstractBlock.Settings.create()
                            .nonOpaque()
                            .solidBlock(Blocks::never)
                            .sounds(BlockSoundGroup.GLASS)
                            .hardness(0f)
            );
            vaseBlocks.put(dyeColor, vaseBlock);
        });
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return outlineShape;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return outlineShape;
    }
}
