package com.mysiupysiu.bignay.fabric.registry;

import com.mysiupysiu.bignay.registry.init.BignayBlockEntities;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;

public class Signs {

    public static void register() {
        BlockEntityRenderers.register((BlockEntityType<SignBlockEntity>) BignayBlockEntities.VERDANT_SIGN.get(), SignRenderer::new);
        BlockEntityRenderers.register((BlockEntityType<HangingSignBlockEntity>) BignayBlockEntities.VERDANT_HANGING_SIGN.get(), HangingSignRenderer::new);
    }
}
