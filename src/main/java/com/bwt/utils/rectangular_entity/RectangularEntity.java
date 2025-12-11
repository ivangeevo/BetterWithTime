package com.bwt.utils.rectangular_entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class RectangularEntity extends Entity {
    public RectangularEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void calculateDimensions() {
        double d = this.getX();
        double e = this.getY();
        double f = this.getZ();
        super.calculateDimensions();
        this.setPosition(d, e, f);
    }

    abstract public EntityRectDimensions getRectDimensions();

    @Override
    public void setYaw(float yaw) {
        super.setYaw(yaw);
        this.setBoundingBox(this.calculateBoundingBox());
    }

    @Override
    protected Box calculateDefaultBoundingBox(Vec3d pos) {
        EntityRectDimensions dimensions = this.getRectDimensions();
        return dimensions.getBoxAt(pos, getYaw()).offset(0, -1 * (dimensions.height() / 2), 0);
    }
}
