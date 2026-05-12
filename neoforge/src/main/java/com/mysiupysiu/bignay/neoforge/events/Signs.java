package com.mysiupysiu.bignay.neoforge.events;

import com.mysiupysiu.bignay.registry.BignayBlockEntities;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod.EventBusSubscriber(modid = "bignay", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class Signs {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer((BlockEntityType<SignBlockEntity>) BignayBlockEntities.VERDANT_SIGN.get(), SignRenderer::new);
//        event.registerBlockEntityRenderer((BlockEntityType<HangingSignBlockEntity>) BignayBlockEntities.VERDANT_HANGING_SIGN.get(), HangingSignRenderer::new);
    }
}
