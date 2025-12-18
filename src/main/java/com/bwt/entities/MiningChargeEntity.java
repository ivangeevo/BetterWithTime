package com.bwt.entities;

import com.bwt.blocks.BwtBlocks;
import com.bwt.blocks.mining_charge.MiningChargeBlock;
import com.bwt.blocks.mining_charge.MiningChargeExplosion;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.rule.GameRules;
import org.jetbrains.annotations.Nullable;

public class MiningChargeEntity extends Entity implements Ownable {
    private static final TrackedData<Integer> FUSE = DataTracker.registerData(MiningChargeEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<BlockState> BLOCK_STATE = DataTracker.registerData(MiningChargeEntity.class, TrackedDataHandlerRegistry.BLOCK_STATE);
    private static final int DEFAULT_FUSE = 80;

    @Nullable
    private LivingEntity causingEntity;
    public boolean attachedToBlock;

    public MiningChargeEntity(EntityType<? extends MiningChargeEntity> entityType, World world) {
        super(entityType, world);
        this.intersectionChecked = true;
        this.attachedToBlock = true;
    }

    public MiningChargeEntity(World world, Vec3d position, BlockState state, @Nullable LivingEntity igniter) {
        this(BwtEntities.miningChargeEntity, world);
        this.setFuse(DEFAULT_FUSE);
        this.setBlockState(state);
        this.prevX = position.x;
        this.prevY = position.y;
        this.prevZ = position.z;
        this.setPosition(position);
//        setYaw(getYaw());
        this.causingEntity = igniter;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(FUSE, 80);
        builder.add(BLOCK_STATE, BwtBlocks.miningChargeBlock.getDefaultState());
    }

    protected void setFacing(Direction direction) {
        setBlockState(MiningChargeBlock.withSurfaceOrientation(getBlockState(), direction));
    }

    public Direction getFacing() {
        return MiningChargeBlock.getSurfaceOrientation(getBlockState());
    }

    public void setFuse(int fuse) {
        this.dataTracker.set(FUSE, fuse);
    }

    public int getFuse() {
        return this.dataTracker.get(FUSE);
    }

    public void setBlockState(BlockState state) {
        this.dataTracker.set(BLOCK_STATE, state);
    }

    public BlockState getBlockState() {
        return this.dataTracker.get(BLOCK_STATE);
    }

    @Override
    protected MoveEffect getMoveEffect() {
        return MoveEffect.NONE;
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean canHit() {
        return !this.isRemoved();
    }

    @Override
    public void tick() {
        if (attachedToBlock) {
            // make sure we're still attached
            BlockPos attachedBlockPos = getBlockPos().offset(getFacing().getOpposite());
            attachedToBlock = getEntityWorld().getBlockState(attachedBlockPos).isSideSolidFullSquare(getEntityWorld(), attachedBlockPos, getFacing());
        }
        if (!attachedToBlock) {
            if (getFacing() == Direction.DOWN) {
                setFacing(Direction.UP);
            }
            tickMovement();
        }
        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i <= 0) {
            this.discard();
            if (this.getEntityWorld() instanceof ServerWorld serverWorld) {
                this.explode(serverWorld);
            }
        } else {
            this.updateWaterState();
            if (this.getEntityWorld().isClient()) {
                this.getEntityWorld().addParticleClient(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    private void tickMovement() {
        if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
        }
        this.move(MovementType.SELF, this.getVelocity());
        this.setVelocity(this.getVelocity().multiply(0.98));
        if (this.isOnGround()) {
            this.setVelocity(this.getVelocity().multiply(0.7, -0.5, 0.7));
        }
    }

    private void explode(ServerWorld serverWorld) {
//        this.serverWorld.createExplosion(this, this.getX(), this.getY(), this.getZ(), 6.0f, World.ExplosionSourceType.NONE);
        createMiningChargeExplosion(serverWorld, 6.0f);
    }

    public void createMiningChargeExplosion(ServerWorld serverWorld, float power) {
        Explosion.DestructionType destructionType = serverWorld.getGameRules().getValue(GameRules.TNT_EXPLOSION_DROP_DECAY) ? Explosion.DestructionType.DESTROY_WITH_DECAY : Explosion.DestructionType.DESTROY;
        Vec3d offsetPos = this.getBlockPos().offset(getFacing().getOpposite()).toCenterPos();
        Explosion explosion = new MiningChargeExplosion(
                serverWorld,
                this,
                offsetPos,
                Explosion.createDamageSource(serverWorld, this),
                power,
                false,
                destructionType,
                ParticleTypes.EXPLOSION,
                ParticleTypes.EXPLOSION_EMITTER,
                SoundEvents.ENTITY_GENERIC_EXPLODE
        );
        explosion.collectBlocksAndDamageEntities();
        explosion.affectWorld(true);
        if (!explosion.shouldDestroy()) {
            explosion.clearAffectedBlocks();
        }
        for (ServerPlayerEntity serverPlayerEntity : serverWorld.getPlayers()) {
            if (!(serverPlayerEntity.squaredDistanceTo(getX(), getY(), getZ()) < 4096.0)) continue;
            serverPlayerEntity.networkHandler.sendPacket(new ExplosionS2CPacket(getX(), getY(), getZ(), power, explosion.getAffectedBlocks(), explosion.getAffectedPlayers().get(serverPlayerEntity), explosion.getDestructionType(), explosion.getParticle(), explosion.getEmitterParticle(), explosion.getSoundEvent()));
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putShort("fuse", (short)this.getFuse());
        nbt.put("block_state", NbtHelper.fromBlockState(this.getBlockState()));
        nbt.putInt("facing", getFacing().getId());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        setFuse(nbt.getShort("fuse"));
        if (nbt.contains("block_state", NbtElement.COMPOUND_TYPE)) {
            this.setBlockState(NbtHelper.toBlockState(this.getEntityWorld().createCommandRegistryWrapper(RegistryKeys.BLOCK), nbt.getCompound("block_state")));
        }
        setFacing(Direction.byId(nbt.getInt("facing")));
    }

    @Override
    @Nullable
    public LivingEntity getOwner() {
        return this.causingEntity;
    }

    @Override
    public void copyFrom(Entity original) {
        super.copyFrom(original);
        if (original instanceof MiningChargeEntity miningChargeEntity) {
            this.causingEntity = miningChargeEntity.causingEntity;
        }
    }

    @Override
    public double getEyeY() {
        return 0.15f;
    }
}
