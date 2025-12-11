package com.bwt.items;

import com.bwt.entities.DynamiteEntity;
import com.bwt.sounds.BwtSoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ProjectileItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public class DynamiteItem extends Item implements ProjectileItem {
    public DynamiteItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), BwtSoundEvents.DYNAMITE_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        if (world instanceof ServerWorld serverWorld) {
            ProjectileEntity.spawn(
                    new DynamiteEntity(serverWorld, user, itemStack),
                    serverWorld,
                    itemStack,
                    entity -> {
                        entity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.0f, 1.0f);
                        for (int i = 0; i < user.getInventory().size(); ++i) {
                            ItemStack otherStack = user.getInventory().getStack(i);
                            if (!otherStack.isOf(Items.FLINT_AND_STEEL)) {
                                continue;
                            }
                            if (!user.isInCreativeMode()) {
                                otherStack.damage(1, user);
                            }
                            entity.ignite();
                            break;
                        }
                    }
            );
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        itemStack.decrementUnlessCreative(1, user);
        return ActionResult.SUCCESS;
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        DynamiteEntity dynamiteEntity = new DynamiteEntity(pos.getX(), pos.getY(), pos.getZ(), world);
        dynamiteEntity.setItem(stack);
        dynamiteEntity.ignite();
        return dynamiteEntity;
    }

    @Override
    public ProjectileItem.Settings getProjectileSettings() {
        return ProjectileItem.Settings.builder()
                .power(1.0f)
                .uncertainty(1.0f)
                .build();
    }
}
