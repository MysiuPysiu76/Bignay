package com.mysiupysiu.bignay;

import com.mysiupysiu.bignay.blocks.BlockEntityInit;
import com.mysiupysiu.bignay.blocks.BlockInit;
import com.mysiupysiu.bignay.items.ItemInit;
import com.mysiupysiu.bignay.menu.MenuInit;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BignayMod.MODID)
public class BignayMod {
    public static final String MODID = "bignay";

    public BignayMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockInit.register(modEventBus);
        ItemInit.register(modEventBus);
        BlockEntityInit.register(modEventBus);
        MenuInit.register(modEventBus);

        modEventBus.addListener(BlockInit::addCreative);
        modEventBus.addListener(ItemInit::addCreative);
        modEventBus.addListener(MenuInit::onClientSetup);
        modEventBus.addListener(BlockEntityInit::onClientSetup);
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                BlockEntityRenderers.register(BlockEntityInit.VERDANT_SIGN.get(), SignRenderer::new);
                BlockEntityRenderers.register(BlockEntityInit.VERDANT_HANGING_SIGN.get(), HangingSignRenderer::new);

                ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(BlockInit.VERDANT_FUNGUS.getId(), BlockInit.POTTED_VERDANT_FUNGUS);
            });
        }
    }
}
