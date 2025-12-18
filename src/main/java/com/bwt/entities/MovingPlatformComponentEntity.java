package com.bwt.entities;

import com.bwt.utils.rectangular_entity.EntityRectDimensions;
import com.bwt.utils.rectangular_entity.RectangularEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class MovingPlatformComponentEntity extends RectangularEntity {
    public final MovingRopeEntity owner;
    public final Vec3i offset;
    public final BlockState cachedState;
    public final BlockEntity blockEntity;
    public final EntityRectDimensions stateDimensions;

    public MovingPlatformComponentEntity(MovingRopeEntity owner, Vec3i offset, BlockState cachedState, BlockEntity blockEntity) {
        super(owner.getType(), owner.getEntityWorld());
        this.owner = owner;
        this.offset = offset;
        this.cachedState = cachedState;
        this.blockEntity = blockEntity;
        setPosition(owner.getPos().add(Vec3d.of(offset)));
        VoxelShape shape = cachedState.getCollisionShape(owner.getEntityWorld(), owner.getBlockPos().add(offset));
        Box outlineShape = shape.getBoundingBox();
        this.stateDimensions = EntityRectDimensions.fixed((float) (outlineShape.maxX - outlineShape.minX), (float) (outlineShape.maxY - outlineShape.minY), (float) (outlineShape.maxZ - outlineShape.minZ));
        this.calculateDimensions();
    }


    @Override
    protected Box calculateBoundingBox() {
        if (stateDimensions == null) {
            return VoxelShapes.fullCube().getBoundingBox().offset(getPos());
        }
        return super.calculateBoundingBox();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    public boolean isPartOf(Entity entity) {
        return this == entity || this.owner == entity;
    }

    public EntityRectDimensions getRectDimensions() {
        return this.stateDimensions;
    }

    @Override
    public boolean shouldSave() {
        return false;
    }
}
