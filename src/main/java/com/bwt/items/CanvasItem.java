package com.bwt.items;

import com.bwt.entities.BwtEntities;
import com.bwt.entities.CanvasEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.GlowItemFrameEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DecorationItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class CanvasItem extends DecorationItem {
    private static final Text RANDOM_TEXT = Text.translatable("canvas.random").formatted(Formatting.GRAY);

    public CanvasItem(Settings settings) {
        super(BwtEntities.canvasEntity, settings);
    }

    @Override
    protected boolean canPlaceOn(PlayerEntity player, Direction side, ItemStack stack, BlockPos pos) {
        return !player.getEntityWorld().isOutOfHeightLimit(pos) && super.canPlaceOn(player, side, stack, pos);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos blockPos = context.getBlockPos();
        Direction direction = context.getSide();
        BlockPos targetPos = blockPos.offset(direction);
        PlayerEntity playerEntity = context.getPlayer();
        ItemStack itemStack = context.getStack();
        if (playerEntity != null && !this.canPlaceOn(playerEntity, direction, itemStack, targetPos)) {
            return ActionResult.FAIL;
        }
        World world = context.getWorld();
        Optional<CanvasEntity> optional = CanvasEntity.placeCanvas(world, targetPos, direction);
        if (optional.isEmpty()) {
            return ActionResult.CONSUME;
        }
        CanvasEntity canvasEntity = optional.get();

        EntityType.copier(world, itemStack, playerEntity).accept(canvasEntity);
        if (canvasEntity.canStayAttached()) {
            if (!world.isClient()) {
                canvasEntity.onPlace();
                world.emitGameEvent(playerEntity, GameEvent.ENTITY_PLACE, canvasEntity.getEntityPos());
                world.spawnEntity(canvasEntity);
            }

            itemStack.decrement(1);
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.CONSUME;
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
        if (displayComponent.shouldDisplay(DataComponentTypes.PAINTING_VARIANT)) {
            RegistryEntry<PaintingVariant> variant = stack.get(DataComponentTypes.PAINTING_VARIANT);
            if (variant != null) {
                variant.value().title().ifPresent(textConsumer);
                variant.value().author().ifPresent(textConsumer);
                textConsumer.accept(Text.translatable("canvas.dimensions", variant.value().width(), variant.value().height()));
            } else if (type.isCreative()) {
                textConsumer.accept(RANDOM_TEXT);
            }
        }
    }
}
