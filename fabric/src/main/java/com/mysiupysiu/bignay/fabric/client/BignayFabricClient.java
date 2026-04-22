package com.mysiupysiu.bignay.fabric.client;

import com.mysiupysiu.bignay.client.menu.ArchaeologyTableScreen;
import com.mysiupysiu.bignay.client.models.QuadItemFrameModel;
import com.mysiupysiu.bignay.client.renderers.CampfiresRenderer;
import com.mysiupysiu.bignay.client.renderers.GlowQuadItemFrameRenderer;
import com.mysiupysiu.bignay.client.renderers.QuadItemFrameRenderer;
import com.mysiupysiu.bignay.fabric.network.BignayFabricClientNetwork;
import com.mysiupysiu.bignay.fabric.registry.Signs;
import com.mysiupysiu.bignay.registry.RegistrySupplier;
import com.mysiupysiu.bignay.registry.init.*;
import com.mysiupysiu.bignay.client.particles.SmallSoulFlameParticle;
import com.mysiupysiu.bignay.world.blocks.be.CampfiresBlockEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;

public class BignayFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register((SimpleParticleType) BignayParticles.SMALL_SOUL_FLAME.get(), SmallSoulFlameParticle.Provider::new);

        BignayBlocks.BLOCKS.getEntries().forEach(e -> BlockRenderLayerMap.INSTANCE.putBlock(e.get(), RenderType.cutout()));

        EntityRendererRegistry.register(BignayEntities.QUAD_ITEM_FRAME.get(), QuadItemFrameRenderer::new);
        EntityRendererRegistry.register(BignayEntities.GLOW_QUAD_ITEM_FRAME.get(), GlowQuadItemFrameRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(QuadItemFrameRenderer.QUAD_ITEM_FRAME_LAYER, QuadItemFrameModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(GlowQuadItemFrameRenderer.GLOW_QUAD_ITEM_FRAME_LAYER, QuadItemFrameModel::createBodyLayer);

        registerCampfire(BignayBlockEntities.ACACIA_CAMPFIRE);
        registerCampfire(BignayBlockEntities.BIRCH_CAMPFIRE);
        registerCampfire(BignayBlockEntities.CHERRY_CAMPFIRE);
        registerCampfire(BignayBlockEntities.CRIMSON_CAMPFIRE);
        registerCampfire(BignayBlockEntities.DARK_OAK_CAMPFIRE);
        registerCampfire(BignayBlockEntities.JUNGLE_CAMPFIRE);
        registerCampfire(BignayBlockEntities.MANGROVE_CAMPFIRE);
        registerCampfire(BignayBlockEntities.OAK_CAMPFIRE);
        registerCampfire(BignayBlockEntities.SPRUCE_CAMPFIRE);
        registerCampfire(BignayBlockEntities.VERDANT_CAMPFIRE);
        registerCampfire(BignayBlockEntities.WARPED_CAMPFIRE);

        registerCampfire(BignayBlockEntities.SOUL_ACACIA_CAMPFIRE);
        registerCampfire(BignayBlockEntities.SOUL_BIRCH_CAMPFIRE);
        registerCampfire(BignayBlockEntities.SOUL_CHERRY_CAMPFIRE);
        registerCampfire(BignayBlockEntities.SOUL_CRIMSON_CAMPFIRE);
        registerCampfire(BignayBlockEntities.SOUL_DARK_OAK_CAMPFIRE);
        registerCampfire(BignayBlockEntities.SOUL_JUNGLE_CAMPFIRE);
        registerCampfire(BignayBlockEntities.SOUL_MANGROVE_CAMPFIRE);
        registerCampfire(BignayBlockEntities.SOUL_OAK_CAMPFIRE);
        registerCampfire(BignayBlockEntities.SOUL_SPRUCE_CAMPFIRE);
        registerCampfire(BignayBlockEntities.SOUL_VERDANT_CAMPFIRE);
        registerCampfire(BignayBlockEntities.SOUL_WARPED_CAMPFIRE);

        Signs.register();

        MenuScreens.register(MenuInit.ARCHAEOLOGY_TABLE_MENU.get(), ArchaeologyTableScreen::new);

        BignayFabricClientNetwork.register();
    }

    private void registerCampfire(RegistrySupplier<BlockEntityType<?>> supplier) {
        BlockEntityRendererRegistry.register((BlockEntityType<CampfiresBlockEntity>) supplier.get(), CampfiresRenderer::new);
    }
}
