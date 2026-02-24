package com.mysiupysiu.bignay.events;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.blocks.BlockEntityInit;
import com.mysiupysiu.bignay.blocks.BlockInit;
import com.mysiupysiu.bignay.entities.*;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BignayMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    public static final ModelLayerLocation QUAD_ITEM_FRAME_LAYER = new ModelLayerLocation(new ResourceLocation("bignay", "quad_item_frame"), "main");
    public static final ModelLayerLocation GLOW_QUAD_ITEM_FRAME_LAYER = new ModelLayerLocation(new ResourceLocation("bignay", "glow_quad_item_frame"), "main");

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            BlockEntityRenderers.register(BlockEntityInit.VERDANT_SIGN.get(), SignRenderer::new);
            BlockEntityRenderers.register(BlockEntityInit.VERDANT_HANGING_SIGN.get(), HangingSignRenderer::new);

            registerPots();

            BlockColors blockColors = Minecraft.getInstance().getBlockColors();
            blockColors.register((state, world, pos, tintIndex) -> BiomeColors.getAverageFoliageColor(world, pos), BlockInit.PALE_PUMPKIN_STEM.get(), BlockInit.ATTACHED_PALE_PUMPKIN_STEM.get());
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

    private static void registerPots() {
        FlowerPotBlock pot = (FlowerPotBlock)Blocks.FLOWER_POT;

        pot.addPlant(BlockInit.PEONY.getId(), BlockInit.POTTED_PEONY);
        pot.addPlant(BlockInit.LILAC.getId(), BlockInit.POTTED_LILAC);
        pot.addPlant(BlockInit.HYACINTH.getId(), BlockInit.POTTED_HYACINTH);
        pot.addPlant(BlockInit.LAPIS_LOTUS.getId(), BlockInit.POTTED_LAPIS_LOTUS);
        pot.addPlant(BlockInit.GOLDEN_POPPY.getId(), BlockInit.POTTED_GOLDEN_POPPY);
        pot.addPlant(BlockInit.ROSE.getId(), BlockInit.POTTED_ROSE);
        pot.addPlant(BlockInit.CYAN_ROSE.getId(), BlockInit.POTTED_CYAN_ROSE);

        pot.addPlant(BlockInit.PUFFBALLS.getId(), BlockInit.POTTED_PUFFBALLS);
        pot.addPlant(BlockInit.MYCELIUM_ROOTS.getId(), BlockInit.POTTED_MYCELIUM_ROOTS);
        pot.addPlant(BlockInit.MUSHROOM_CLUSTER.getId(), BlockInit.POTTED_MUSHROOM_CLUSTER);
        pot.addPlant(BlockInit.BROWN_MUSHROOM_CLUSTER.getId(), BlockInit.POTTED_BROWN_MUSHROOM_CLUSTER);
        pot.addPlant(BlockInit.RED_MUSHROOM_CLUSTER.getId(), BlockInit.POTTED_RED_MUSHROOM_CLUSTER);

        pot.addPlant(BlockInit.VERDANT_FUNGUS.getId(), BlockInit.POTTED_VERDANT_FUNGUS);
        pot.addPlant(BlockInit.VERDANT_ROOTS.getId(), BlockInit.POTTED_VERDANT_ROOTS);
    }
}
