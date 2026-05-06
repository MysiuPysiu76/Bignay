package com.mysiupysiu.bignay.registry;

import com.mysiupysiu.bignay.registry.core.Registrar;
import com.mysiupysiu.bignay.registry.core.RegistrySupplier;
import com.mysiupysiu.bignay.world.entities.GlowQuadItemFrameEntity;
import com.mysiupysiu.bignay.world.entities.QuadItemFrameEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class BignayEntities {

    public static final Registrar<EntityType<?>> ENTITIES = new Registrar<>();

    public static final RegistrySupplier<EntityType<QuadItemFrameEntity>> QUAD_ITEM_FRAME = entity("quad_item_frame", () -> EntityType.Builder.<QuadItemFrameEntity>of(QuadItemFrameEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).build("quad_item_frame"));
    public static final RegistrySupplier<EntityType<GlowQuadItemFrameEntity>> GLOW_QUAD_ITEM_FRAME = entity("glow_quad_item_frame", () -> EntityType.Builder.<GlowQuadItemFrameEntity>of(GlowQuadItemFrameEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).build("glow_quad_item_frame"));

    private static <T extends Entity> RegistrySupplier<EntityType<T>> entity(String id, Supplier<EntityType<T>> entity) {
        return (RegistrySupplier<EntityType<T>>) (Object) ENTITIES.register(id, (Supplier<EntityType<?>>) (Object) entity);
    }
}
