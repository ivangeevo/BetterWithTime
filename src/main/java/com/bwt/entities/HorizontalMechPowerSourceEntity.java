package com.bwt.entities;

import com.bwt.blocks.axles.AxleBlock;
import com.bwt.blocks.BwtBlocks;
import com.bwt.utils.rectangular_entity.RectangularEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public abstract class HorizontalMechPowerSourceEntity extends RectangularEntity {
    protected float rotation = 0;
    protected float prevRotation = 0;

    protected int ticksBeforeNextFullUpdate = 20;

    protected static final TrackedData<Float> rotationSpeed = DataTracker.registerData(HorizontalMechPowerSourceEntity.class, TrackedDataHandlerRegistry.FLOAT);
    protected static final TrackedData<Integer> DAMAGE_WOBBLE_TICKS = DataTracker.registerData(HorizontalMechPowerSourceEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> DAMAGE_WOBBLE_SIDE = DataTracker.registerData(HorizontalMechPowerSourceEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Float> DAMAGE_WOBBLE_STRENGTH = DataTracker.registerData(HorizontalMechPowerSourceEntity.class, TrackedDataHandlerRegistry.FLOAT);

    public HorizontalMechPowerSourceEntity(EntityType<? extends HorizontalMechPowerSourceEntity> type, World world) {
        super(type, world);
        this.intersectionChecked = true;
    }

    public HorizontalMechPowerSourceEntity(EntityType<? extends HorizontalMechPowerSourceEntity> type, World world, Vec3d pos, Direction facing) {
        this(type, world);
        setPosition(pos);
        setYaw(facing.getPositiveHorizontalDegrees());
    }

    public interface Factory {
        HorizontalMechPowerSourceEntity create(World world, Vec3d pos, Direction facing);
    }


    abstract public boolean tryToSpawn(PlayerEntity player);
    abstract public Predicate<BlockPos> getBlockInterferencePredicate();
    abstract float computeRotation();
    abstract float getSpeedToPowerThreshold();

    @Override
    public double getEyeY() {
        return this.getHeight() / 2;
    }

    @Override
    protected MoveEffect getMoveEffect() {
        return MoveEffect.NONE;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(rotationSpeed, 0f);
        builder.add(DAMAGE_WOBBLE_TICKS, 0);
        builder.add(DAMAGE_WOBBLE_SIDE, 1);
        builder.add(DAMAGE_WOBBLE_STRENGTH, 0.0f);
    }

    public float getRotation() {
        return rotation;
    }

    protected void setRotation(float rotation) {
        rotation = (rotation + 360f) % 360f;
        this.prevRotation = this.rotation;
        this.rotation = rotation;
    }

    public float getPrevRotation() {
        return prevRotation;
    }

    public float getRotationSpeed() {
        return getDataTracker().get(rotationSpeed);
    }

    public void setRotationSpeed(float speed) {
        getDataTracker().set(rotationSpeed, speed);
    }

    public void setDamageWobbleTicks(int damageWobbleTicks) {
        this.dataTracker.set(DAMAGE_WOBBLE_TICKS, damageWobbleTicks);
    }

    public void setDamageWobbleSide(int damageWobbleSide) {
        this.dataTracker.set(DAMAGE_WOBBLE_SIDE, damageWobbleSide);
    }

    public void setDamageWobbleStrength(float damageWobbleStrength) {
        this.dataTracker.set(DAMAGE_WOBBLE_STRENGTH, damageWobbleStrength);
    }

    public float getDamageWobbleStrength() {
        return this.dataTracker.get(DAMAGE_WOBBLE_STRENGTH);
    }

    public int getDamageWobbleTicks() {
        return this.dataTracker.get(DAMAGE_WOBBLE_TICKS);
    }

    public int getDamageWobbleSide() {
        return this.dataTracker.get(DAMAGE_WOBBLE_SIDE);
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    public boolean isCollidable(@Nullable Entity entity) {
        return true;
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public PistonBehavior getPistonBehavior() {
        return PistonBehavior.DESTROY;
    }

    public boolean tryToSpawn(PlayerEntity player, Text blockBlockedErrorMessage, Text entityBlockedErrorMessage) {
        if (player instanceof ServerPlayerEntity) {
            player = null;
        }

        if (placementBlockedByBlock()) {
            if (player != null) {
                player.sendMessage(blockBlockedErrorMessage, false);
            }
            return false;
        }
        if (placementBlockedByEntity()) {
            if (player != null) {
                player.sendMessage(entityBlockedErrorMessage, false);
            }
            return false;
        }

        if (placementHasBadAxleState()) {
            return false;
        }

        setRotationSpeed(computeRotation());
        World world = getEntityWorld();
        world.spawnEntity(this);
        return true;
    }

    public boolean placementBlockedByBlock() {
        Predicate<BlockPos> blockInterferencePredicate = getBlockInterferencePredicate();
        return BlockPos.stream(getBoundingBox())
                // Ignore the axle we're on
                .filter(blockPos -> !blockPos.equals(this.getBlockPos()))
                .anyMatch(blockInterferencePredicate);
    }

    @Override
    protected void onBlockCollision(BlockState state) {
        if (getEntityWorld() instanceof ServerWorld serverWorld) {
            destroyWithDrop(serverWorld);
        }
    }

    public boolean placementBlockedByEntity() {
        ArrayList<Entity> anyEntities = new ArrayList<>();
        getEntityWorld().collectEntitiesByType(
                TypeFilter.instanceOf(Entity.class),
                getBoundingBox(),
                entity -> entity != this && EntityPredicates.EXCEPT_SPECTATOR.test(entity) && !(entity instanceof ItemEntity),
                anyEntities, 1);
        return !anyEntities.isEmpty();
    }

    public boolean placementHasBadAxleState() {
        World world = getEntityWorld();

        BlockState axleBlock = world.getBlockState(getBlockPos());

        // Bad block type
        if (!axleBlock.isOf(BwtBlocks.axleBlock) && !axleBlock.isOf(BwtBlocks.axlePowerSourceBlock)) {
            return true;
        }
        Direction.Axis axleAxis = axleBlock.get(AxleBlock.AXIS);
        float yaw = getYaw();

        // Misaligned
        return Direction.from(axleAxis, Direction.AxisDirection.NEGATIVE).getPositiveHorizontalDegrees() != yaw
                && Direction.from(axleAxis, Direction.AxisDirection.POSITIVE).getPositiveHorizontalDegrees() != yaw;
    }

    @Override
    public void tick() {
        super.tick();

        if (isRemoved()) {
            return;
        }
        if (this.getDamageWobbleTicks() > 0) {
            this.setDamageWobbleTicks(this.getDamageWobbleTicks() - 1);
        }
        if (this.getDamageWobbleStrength() > 0.0f) {
            this.setDamageWobbleStrength(this.getDamageWobbleStrength() - 1.0f);
        }

        if (getEntityWorld() instanceof ServerWorld serverWorld) {
            ticksBeforeNextFullUpdate--;
            if (ticksBeforeNextFullUpdate <= 0) {
                ticksBeforeNextFullUpdate = 20;
                fullUpdate(serverWorld);
            }
        }
        else {
            updateRotation();
        }
        getEntityWorld()
                .getOtherEntities(this, this.getBoundingBox().expand(0.01f, 0.01f, 0.01f), EntityPredicates.canBePushedBy(this))
                .forEach(this::pushAwayFrom);
    }

    @Override
    public void pushAwayFrom(Entity entity) {
        if (entity.noClip || this.noClip) {
            return;
        }
        Box thisBox = this.getBoundingBox().expand(0.01f, 0.01f, 0.01f);
        Box entityBox = entity.getBoundingBox();
        List<Pair<Direction, Double>> intersections = new ArrayList<>();
        for (Direction.Axis axis : Direction.Axis.values()) {
            double thisMin = thisBox.getMin(axis);
            double thisMax = thisBox.getMax(axis);
            double entityMin = entityBox.getMin(axis);
            double entityMax = entityBox.getMax(axis);
            if (thisMax - entityMin > 0) {
                Direction intersectDirection = Direction.from(axis, Direction.AxisDirection.POSITIVE);
                intersections.add(new Pair<>(intersectDirection, thisMax - entityMin));
            }
            if (entityMax - thisMin > 0) {
                Direction intersectDirection = Direction.from(axis, Direction.AxisDirection.NEGATIVE);
                intersections.add(new Pair<>(intersectDirection, entityMax - thisMin));
            }
        }
        intersections.stream()
                .min(Comparator.comparingDouble(pair -> Math.abs(pair.getRight())))
                .filter(pair -> pair.getRight() > 0.01f)
                .ifPresent(pair -> entity.addVelocity(
                        new Vec3d(pair.getLeft().getUnitVector().mul(pair.getRight().floatValue() / 2)))
                );
    }

    protected void updateRotation() {
        setRotation(rotation + this.getDataTracker().get(rotationSpeed));
    }

    protected void fullUpdate(ServerWorld serverWorld) {
        if (placementBlockedByBlock() || placementHasBadAxleState()) {
            destroyWithDrop(serverWorld);
            return;
        }

        setRotationSpeed(computeRotation());

        setHostAxlePower(Math.abs(getRotationSpeed()) > getSpeedToPowerThreshold());
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        if (this.isRemoved()) {
            return true;
        }
        if (this.isAlwaysInvulnerableTo(source)) {
            return false;
        }
        this.setDamageWobbleSide(-this.getDamageWobbleSide());
        this.setDamageWobbleTicks(10);
        this.scheduleVelocityUpdate();
        this.setDamageWobbleStrength(this.getDamageWobbleStrength() + amount * 10.0f);
        this.emitGameEvent(GameEvent.ENTITY_DAMAGE, source.getAttacker());
        boolean instantKill = source.getAttacker() instanceof PlayerEntity && ((PlayerEntity)source.getAttacker()).getAbilities().creativeMode;
        if (instantKill) {
            discard();
            return true;
        }
        if (this.getDamageWobbleStrength() > 40.0f) {
            destroyWithDrop(world);
        }
        return true;
    }

    public void destroyWithDrop(ServerWorld serverWorld) {
        if (isRemoved()) return;
        dropStack(serverWorld, getPickBlockStack(), 0.5f);
        kill(serverWorld);
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        setHostAxlePower(false);
    }

    protected void setHostAxlePower(boolean powered) {
        World world = getEntityWorld();
        BlockPos pos = getBlockPos();
        BlockState hostBlockState = world.getBlockState(pos);
        if (!powered && hostBlockState.isOf(BwtBlocks.axlePowerSourceBlock)) {
            world.removeBlock(pos, false);
            world.setBlockState(pos, BwtBlocks.axleBlock.getDefaultState()
                    .with(AxleBlock.AXIS, hostBlockState.get(AxleBlock.AXIS)));
        }
        if (powered && hostBlockState.isOf(BwtBlocks.axleBlock)) {
            world.setBlockState(pos, BwtBlocks.axlePowerSourceBlock.getDefaultState()
                    .with(AxleBlock.AXIS, hostBlockState.get(AxleBlock.AXIS)));
        }
    }

    @Override
    protected void writeCustomData(WriteView view) {
        view.putFloat("rotationSpeed", dataTracker.get(rotationSpeed));
    }

    @Override
    protected void readCustomData(ReadView view) {
        this.dataTracker.set(rotationSpeed, view.getFloat("rotationSpeed", getRotationSpeed()));
    }
}
