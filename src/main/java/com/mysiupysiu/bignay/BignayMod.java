package com.mysiupysiu.bignay;

import com.mysiupysiu.bignay.blocks.BlockEntityInit;
import com.mysiupysiu.bignay.blocks.BlockInit;
import com.mysiupysiu.bignay.entities.*;
import com.mysiupysiu.bignay.items.ItemInit;
import com.mysiupysiu.bignay.menu.MenuInit;
import com.mysiupysiu.bignay.utils.ModelLayers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
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
        EntityInit.register(modEventBus);

        modEventBus.addListener(BlockInit::addCreative);
        modEventBus.addListener(ItemInit::addCreative);
        modEventBus.addListener(MenuInit::onClientSetup);
        modEventBus.addListener(BlockEntityInit::onClientSetup);
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        public static final ModelLayerLocation QUAD_ITEM_FRAME_LAYER = new ModelLayerLocation(new ResourceLocation("bignay", "quad_item_frame"), "main");
        public static final ModelLayerLocation GLOW_QUAD_ITEM_FRAME_LAYER = new ModelLayerLocation(new ResourceLocation("bignay", "glow_quad_item_frame"), "main");

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                BlockEntityRenderers.register(BlockEntityInit.VERDANT_SIGN.get(), SignRenderer::new);
                BlockEntityRenderers.register(BlockEntityInit.VERDANT_HANGING_SIGN.get(), HangingSignRenderer::new);

                ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(BlockInit.VERDANT_FUNGUS.getId(), BlockInit.POTTED_VERDANT_FUNGUS);
                ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(BlockInit.VERDANT_ROOTS.getId(), BlockInit.POTTED_VERDANT_ROOTS);

                BlockColors blockColors = Minecraft.getInstance().getBlockColors();
                blockColors.register((state, world, pos, tintIndex) -> {
                    return BiomeColors.getAverageFoliageColor(world, pos);
                }, BlockInit.PALE_PUMPKIN_STEM.get(), BlockInit.ATTACHED_PALE_PUMPKIN_STEM.get());
            });
        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(EntityInit.QUAD_ITEM_FRAME.get(), QuadItemFrameRenderer::new);
            event.registerEntityRenderer(EntityInit.GLOW_QUAD_ITEM_FRAME.get(), GlowQuadItemFrameRenderer::new);
        }

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(ModelLayers.QUAD_ITEM_FRAME, QuadItemFrameModel::createBodyLayer);
            event.registerLayerDefinition(ModelLayers.GLOW_QUAD_ITEM_FRAME, GlowQuadItemFrameModel::createBodyLayer);
        }
    }
}
