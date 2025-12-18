package com.bwt.entities;

import com.bwt.utils.Id;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class BwtEntities implements ModInitializer {
    public static final EntityType<WindmillEntity> windmillEntity = register(
            "windmill",
            EntityType.Builder.<WindmillEntity>create(WindmillEntity::new, SpawnGroup.MISC)
                    .maxTrackingRange(10)
    );
    public static final EntityType<WaterWheelEntity> waterWheelEntity = register(
            "water_wheel",
            EntityType.Builder.<WaterWheelEntity>create(WaterWheelEntity::new, SpawnGroup.MISC)
                    .maxTrackingRange(10)
    );
    public static final EntityType<MovingRopeEntity> movingRopeEntity = register(
            "moving_rope",
            EntityType.Builder.<MovingRopeEntity>create(MovingRopeEntity::new, SpawnGroup.MISC)
                    .dimensions(0.98f, 0.98f)
    );
    public static final EntityType<BroadheadArrowEntity> broadheadArrowEntity = register(
            "broadhead_arrow",
            EntityType.Builder.<BroadheadArrowEntity>create(BroadheadArrowEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5f, 0.5f)
                    .maxTrackingRange(4)
                    .trackingTickInterval(20)
    );
    public static final EntityType<RottedArrowEntity> rottedArrowEntity = register(
            "rotted_arrow",
            EntityType.Builder.<RottedArrowEntity>create(RottedArrowEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5f, 0.5f)
                    .maxTrackingRange(4)
                    .trackingTickInterval(20)
    );
    public static final EntityType<DynamiteEntity> dynamiteEntity = register(
            "dynamite",
            EntityType.Builder.<DynamiteEntity>create(DynamiteEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25f, 0.40f)
                    .maxTrackingRange(4)
                    .trackingTickInterval(20)
    );
    public static final EntityType<MiningChargeEntity> miningChargeEntity = register(
            "mining_charge",
            EntityType.Builder.<MiningChargeEntity>create(MiningChargeEntity::new, SpawnGroup.MISC)
                    .makeFireImmune()
                    .dimensions(0.98f, 0.98f)
                    .maxTrackingRange(10)
                    .trackingTickInterval(10)
    );
    public static final EntityType<SoulUrnProjectileEntity> soulUrnProjectileEntity = register(
            "soul_urn",
            EntityType.Builder.<SoulUrnProjectileEntity>create(SoulUrnProjectileEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25f, 0.40f)
                    .maxTrackingRange(6)
                    .trackingTickInterval(20)
    );
    public static final EntityType<CanvasEntity> canvasEntity = register(
            "canvas",
            EntityType.Builder.<CanvasEntity>create(CanvasEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5F, 0.5F)
                    .maxTrackingRange(10)
                    .trackingTickInterval(Integer.MAX_VALUE)
    );

    private static RegistryKey<EntityType<?>> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ENTITY_TYPE, Id.of(id));
    }

    private static <T extends Entity> EntityType<T> register(RegistryKey<EntityType<?>> key, EntityType.Builder<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, key, type.build(key));
    }

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        return register(keyOf(id), type);
    }

    @Override
    public void onInitialize() {
    }
}
