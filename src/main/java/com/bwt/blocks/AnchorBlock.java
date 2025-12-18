package com.bwt.blocks;

import com.bwt.items.BwtItems;
import com.bwt.sounds.BwtSoundEvents;
import com.bwt.utils.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AnchorBlock extends SimpleFacingBlock {
    public static final BooleanProperty CONNECTED_ABOVE = BooleanProperty.of("connected_above");
    public static final BooleanProperty CONNECTED_BELOW = BooleanProperty.of("connected_below");

    public static final Box baseBox = new Box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0);
    protected static final VoxelShape NUB_SHAPE = Block.createCuboidShape(6, 6, 6, 10, 10, 10);
    protected static final List<VoxelShape> SHAPES = Arrays.stream(Direction.values())
            .map(direction -> VoxelShapes.union(BlockUtils.rotateCuboidFromUp(direction, baseBox), NUB_SHAPE))
            .collect(Collectors.toList());

    public AnchorBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CONNECTED_ABOVE, CONNECTED_BELOW);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return SHAPES.get(state.get(FACING).getId());
    }

    @NotNull
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState upState = ctx.getWorld().getBlockState(ctx.getBlockPos().up());
        BlockState downState = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
        BlockState placementState = getDefaultState().with(FACING, ctx.getSide());
        return placementState
                .with(CONNECTED_ABOVE, placementState.get(FACING) != Direction.DOWN && (upState.isOf(BwtBlocks.ropeBlock) || upState.isOf(BwtBlocks.pulleyBlock)))
                .with(CONNECTED_BELOW, placementState.get(FACING) != Direction.UP && (downState.isOf(BwtBlocks.ropeBlock)));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return switch (direction) {
            case UP -> state.with(CONNECTED_ABOVE,
                    (state.get(FACING) != Direction.DOWN && neighborState.isOf(BwtBlocks.ropeBlock))
                            || (state.get(FACING) == Direction.UP && neighborState.isOf(BwtBlocks.pulleyBlock)));
            case DOWN -> state.with(CONNECTED_BELOW,
                    (state.get(FACING) != Direction.UP && neighborState.isOf(BwtBlocks.ropeBlock)));
            default -> state;
        };
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (player.getMainHandStack().isOf(BwtItems.ropeItem)) {
            return super.onUse(state, world, pos, player, hit);
        }
        BlockPos.Mutable mutablePos = pos.mutableCopy().move(Direction.DOWN);
        while (world.getBlockState(mutablePos.down()).isOf(BwtBlocks.ropeBlock)) {
            mutablePos.move(Direction.DOWN);
        }
        if (!world.getBlockState(mutablePos).isOf(BwtBlocks.ropeBlock)) {
            return ActionResult.FAIL;
        }
        player.getInventory().offerOrDrop(BwtItems.ropeItem.getDefaultStack());
        world.setBlockState(mutablePos, Blocks.AIR.getDefaultState());
        world.playSound(null, pos, BwtSoundEvents.ANCHOR_RETRACT, SoundCategory.PLAYERS, 0.2f, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 1.4f + 2.0f);

        return ActionResult.SUCCESS;
    }
}
