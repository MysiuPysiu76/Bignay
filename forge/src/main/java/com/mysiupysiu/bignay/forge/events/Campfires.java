package com.mysiupysiu.bignay.forge.events;

import com.mysiupysiu.bignay.client.renderers.CampfiresRenderer;
import com.mysiupysiu.bignay.registry.core.RegistrySupplier;
import com.mysiupysiu.bignay.registry.BignayBlockEntities;
import com.mysiupysiu.bignay.world.blocks.be.CampfiresBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.UnknownNullability;

@Mod.EventBusSubscriber(modid = "bignay", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class Campfires {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers e) {
        registerCampfire(e, BignayBlockEntities.ACACIA_CAMPFIRE);
        registerCampfire(e, BignayBlockEntities.BIRCH_CAMPFIRE);
        registerCampfire(e, BignayBlockEntities.CHERRY_CAMPFIRE);
        registerCampfire(e, BignayBlockEntities.CRIMSON_CAMPFIRE);
        registerCampfire(e, BignayBlockEntities.DARK_OAK_CAMPFIRE);
        registerCampfire(e, BignayBlockEntities.JUNGLE_CAMPFIRE);
        registerCampfire(e, BignayBlockEntities.MANGROVE_CAMPFIRE);
        registerCampfire(e, BignayBlockEntities.OAK_CAMPFIRE);
        registerCampfire(e, BignayBlockEntities.SPRUCE_CAMPFIRE);
        registerCampfire(e, BignayBlockEntities.VERDANT_CAMPFIRE);
        registerCampfire(e, BignayBlockEntities.WARPED_CAMPFIRE);

        registerCampfire(e, BignayBlockEntities.SOUL_ACACIA_CAMPFIRE);
        registerCampfire(e, BignayBlockEntities.SOUL_BIRCH_CAMPFIRE);
        registerCampfire(e, BignayBlockEntities.SOUL_CHERRY_CAMPFIRE);
        registerCampfire(e, BignayBlockEntities.SOUL_CRIMSON_CAMPFIRE);
        registerCampfire(e, BignayBlockEntities.SOUL_DARK_OAK_CAMPFIRE);
        registerCampfire(e, BignayBlockEntities.SOUL_JUNGLE_CAMPFIRE);
        registerCampfire(e, BignayBlockEntities.SOUL_MANGROVE_CAMPFIRE);
        registerCampfire(e, BignayBlockEntities.SOUL_OAK_CAMPFIRE);
        registerCampfire(e, BignayBlockEntities.SOUL_SPRUCE_CAMPFIRE);
        registerCampfire(e, BignayBlockEntities.SOUL_VERDANT_CAMPFIRE);
        registerCampfire(e, BignayBlockEntities.SOUL_WARPED_CAMPFIRE);
    }

    private static <T extends BlockEntity> void registerCampfire(EntityRenderersEvent.RegisterRenderers event, @UnknownNullability RegistrySupplier<BlockEntityType<?>> supplier) {
        event.registerBlockEntityRenderer((BlockEntityType<CampfiresBlockEntity>) supplier.get(), CampfiresRenderer::new);
    }
}
