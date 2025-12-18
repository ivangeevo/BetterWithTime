package com.bwt.entities;

import com.bwt.damage_types.BwtDamageTypes;
import com.bwt.items.BwtItems;
import com.bwt.sounds.BwtSoundEvents;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Optional;

public class DynamiteEntity extends ProjectileEntity implements FlyingItemEntity {

    public static final int TICKS_TO_DETONATE = 100;

    private static final TrackedData<ItemStack> ITEM = DataTracker.registerData(DynamiteEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    public static TrackedData<Integer> FUSE = DataTracker.registerData(DynamiteEntity.class, TrackedDataHandlerRegistry.INTEGER);

    protected DynamiteEntity(EntityType<DynamiteEntity> entityType, World world) {
        super(entityType, world);
    }

    public DynamiteEntity(double x, double y, double z, World world) {
        this(BwtEntities.dynamiteEntity, world);
        this.setPosition(x, y, z);
    }

    public DynamiteEntity(World world, LivingEntity owner, ItemStack stack) {
        this(owner.getX(), owner.getEyeY() - (double)0.1f, owner.getZ(), world);
        this.setOwner(owner);
        this.setItem(stack);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(ITEM, ItemStack.EMPTY);
        builder.add(FUSE, -1);
    }

    @Override
    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        view.putInt("fuse", getFuse());
        ItemStack itemStack = getItem();
        if (!itemStack.isEmpty()) {
            view.put("Item", ItemStack.CODEC, itemStack);
        }
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        setFuse(view.getInt("fuse", getFuse()));
        setItem(view.read("Item", ItemStack.CODEC).orElse(getDefaultItem().getDefaultStack()));
    }

    @Override
    public ItemStack getStack() {
        ItemStack itemStack = this.getItem();
        return itemStack.isEmpty() ? new ItemStack(this.getDefaultItem()) : itemStack;
    }

    protected Item getDefaultItem() {
        return BwtItems.dynamiteItem;
    }

    public int getFuse() {
        return dataTracker.get(FUSE);
    }

    public void setFuse(int value) {
        dataTracker.set(FUSE, value);
    }

    protected ItemStack getItem() {
        return this.getDataTracker().get(ITEM);
    }

    public void setItem(ItemStack item) {
        this.getDataTracker().set(ITEM, item.copyWithCount(1));
    }

    public void ignite() {
        dataTracker.set(FUSE, TICKS_TO_DETONATE);
        playSound(BwtSoundEvents.DYNAMITE_IGNITE, 0.5f, 1.0f);
    }

    public boolean isFireImmune() {
        return true;
    }

    @Override
    public boolean shouldRender(double distance) {
        double d = this.getBoundingBox().getAverageSideLength() * 4.0;
        if (Double.isNaN(d)) {
            d = 4.0;
        }
        return distance < (d *= 64.0) * d;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.hasNoGravity()) {
            this.addVelocity(0.0, -0.04, 0.0);
        }
        this.move(MovementType.SELF, this.getVelocity());
        if (this.isOnGround()) {
            this.setVelocity(this.getVelocity().multiply(0.7, -0.5, 0.7));
        }

        int fuse = getFuse();
        if (fuse > 0) {
            setFuse(fuse - 1);
            fuse--;
            if (getEntityWorld().isClient()) {
                getEntityWorld().addParticleClient(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), getVelocity().getX() * 0.1, getVelocity().getY() * 0.1, getVelocity().getZ() * 0.1);
            }
        }

        if (fuse == 0) {
            discard();
            if (!getEntityWorld().isClient()) {
                explode();
            }
            return;
        }

        if (fuse < 0) {
            if (this.isInLava()) {
                ignite();
                discard();
                if (!getEntityWorld().isClient()) {
                    explode();
                }
                return;
            }
            if (isOnGround()) {
                if (getVelocity().length() < 0.01) {
                    // The dynamite has come to a stop. Convert it to an item.
                    if (!getEntityWorld().isClient()) {
                        convertToItem();
                        return;
                    }
                }
            }
        }
    }

    public void explode() {
        this.getEntityWorld().createExplosion(this, this.getX(), this.getBodyY(0.0625), this.getZ(), 1.5f, World.ExplosionSourceType.TNT);
        if (isSubmergedInWater()) {
            redneckFish();
        }
    }

    private void redneckFish() {
        for (int i = getBlockX() - 2; i <= getBlockX() + 2; i++) {
            // favor deep water
            for (int j = getBlockY() - 2; j <= getBlockY() + 4; j++) {
                for (int k = getBlockZ() - 2; k <= getBlockZ() + 2; k++) {
                    BlockPos offsetPos = new BlockPos(i, j, k);
                    if (!isValidBlockForRedneckFishing(offsetPos)) {
                        continue;
                    }
                    // averages one per j layer
                    if (random.nextInt( 25 ) == 0 ) {
                        spawnRedneckFish(offsetPos);
                    }
                }
            }
        }
    }

    private boolean isValidBlockForRedneckFishing(BlockPos pos) {
        // block must be totally surrounded by water (except positive j) to be valid
        return Arrays.stream(Direction.values())
                .filter(direction -> direction != Direction.UP)
                .map(pos::offset)
                .map(offsetPos -> getEntityWorld().getFluidState(offsetPos))
                .allMatch(fluidState -> fluidState.isIn(FluidTags.WATER));
    }

    private void spawnRedneckFish(BlockPos pos) {
        if (!(getEntityWorld() instanceof ServerWorld serverWorld)) {
            return;
        }
        LootWorldContext lootContextParameterSet = new LootWorldContext.Builder(serverWorld)
                .add(LootContextParameters.ORIGIN, this.getEntityPos())
                .add(LootContextParameters.THIS_ENTITY, this)
                .add(LootContextParameters.DAMAGE_SOURCE, BwtDamageTypes.of(serverWorld, DamageTypes.EXPLOSION))
                .build(LootContextTypes.ENTITY);
        Optional<ObjectArrayList<ItemStack>> optionalItems = Optional.ofNullable(serverWorld.getServer())
                .map(MinecraftServer::getReloadableRegistries)
                .map(reg -> reg.getLootTable(LootTables.FISHING_FISH_GAMEPLAY))
                .map(table -> table.generateLoot(lootContextParameterSet));
        if (optionalItems.isEmpty()) {
            return;
        }
        ObjectArrayList<ItemStack> itemList = optionalItems.get();
        ItemStack itemStack = itemList.get(random.nextInt(itemList.size()));
        if (!itemStack.isIn(ItemTags.FISHES)) return;
        ItemEntity itemEntity = new ItemEntity(serverWorld, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemStack);
        this.getEntityWorld().spawnEntity(itemEntity);
    }

    private void convertToItem() {
        ItemScatterer.spawn(getEntityWorld(), getX(), getY(), getZ(), getStack());
        discard();
    }
}
