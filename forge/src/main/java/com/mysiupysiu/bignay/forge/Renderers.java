package com.mysiupysiu.bignay.forge;

import com.mysiupysiu.bignay.BignayMod;
//import com.mysiupysiu.bignay.registry.content.BignayBlockEntities;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BignayMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Renderers {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
//        BignayBlockEntities.BLOCK_ENTITIES.getEntries().forEach(entry -> {
//            BlockEntityType<?> type = (BlockEntityType<?>) entry.get();
////            if (entry.get())
////            event.registerBlockEntityRenderer(type, com.mysiupysiu.bignay.client.renderers.CampfiresRenderer::new);
//        });
    }
}
