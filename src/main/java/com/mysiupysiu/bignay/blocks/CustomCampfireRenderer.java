package com.mysiupysiu.bignay.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class CustomCampfireRenderer implements BlockEntityRenderer<CustomCampfireBlockEntity> {

    private final ItemRenderer itemRenderer;

    public CustomCampfireRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
    }

    @Override
    public void render(CustomCampfireBlockEntity campfire, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        for (int i = 0; i < 4; ++i) {
            ItemStack stack = campfire.getItem(i);
            if (!stack.isEmpty()) {
                poseStack.pushPose();

                float offset = 0.3125f;
                float xOffset = (i % 2 == 0) ? -offset : offset;
                float zOffset = (i / 2 == 0) ? -offset : offset;

                poseStack.translate(0.5f + xOffset, 0.45f, 0.5f + zOffset);

                poseStack.mulPose(Axis.XP.rotationDegrees(90f));

                float[] yRotations = {0f, 90f, 180f, 270f};
                poseStack.mulPose(Axis.ZP.rotationDegrees(yRotations[i]));

                poseStack.scale(0.38f, 0.38f, 0.38f);
                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, combinedLight, combinedOverlay, poseStack, buffer, null, 0);
                poseStack.popPose();
            }
        }
    }
}
