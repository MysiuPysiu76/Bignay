package com.mysiupysiu.bignay.entities;

import com.mysiupysiu.bignay.BignayMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BignayMod.MODID);

    public static final RegistryObject<EntityType<QuadItemFrameEntity>> QUAD_ITEM_FRAME = ENTITIES.register("quad_item_frame", () -> EntityType.Builder.<QuadItemFrameEntity>of(QuadItemFrameEntity::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F).clientTrackingRange(10).build("quad_item_frame"));

    public static void register(IEventBus bus) {
        ENTITIES.register(bus);
    }
}
