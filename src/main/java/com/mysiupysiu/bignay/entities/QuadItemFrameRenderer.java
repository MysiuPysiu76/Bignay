package com.mysiupysiu.bignay.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mysiupysiu.bignay.BignayMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class QuadItemFrameRenderer extends EntityRenderer<QuadItemFrameEntity> {

    private final QuadItemFrameModel model;

    public QuadItemFrameRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = new QuadItemFrameModel(ctx.bakeLayer(BignayMod.ClientModEvents.QUAD_ITEM_FRAME_LAYER));
    }

    @Override
    public void render(QuadItemFrameEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        Direction dir = entity.getDirection();
        BlockPos blockPos = entity.getPos();

        double blockCenterX = blockPos.getX() + 0.5D;
        double blockCenterY = blockPos.getY() + 0.5D;
        double blockCenterZ = blockPos.getZ() + 0.5D;

        double correction = 0.5D;

        double desiredPivotX = blockCenterX - dir.getStepX() * correction;
        double desiredPivotY = blockCenterY - dir.getStepY() * correction;
        double desiredPivotZ = blockCenterZ - dir.getStepZ() * correction;

        double entityX = entity.getX();
        double entityY = entity.getY();
        double entityZ = entity.getZ();

        double dx = desiredPivotX - entityX;
        double dy = desiredPivotY - entityY;
        double dz = desiredPivotZ - entityZ;

        poseStack.translate(dx, dy, dz);

        switch (dir) {
            case NORTH -> poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            case WEST -> {
                poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            }
            case EAST -> {
                poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            }
            case UP -> poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            case DOWN -> {
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees(0.0F));
            }
        }

        VertexConsumer vc = buffer.getBuffer(RenderType.entityCutout(QuadItemFrameModel.TEXTURE));
        this.model.renderToBuffer(poseStack, vc, packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

        final float quadOffset = 0.18f;
        final float scale = 0.5f;
        final float depthOffset = 1 / 20f;

        for (int i = 0; i < 4; i++) {
            ItemStack stack = entity.getItem(i);
            if (stack == null || stack.isEmpty()) continue;

            poseStack.pushPose();

            float tx;
            float ty;

            if (dir == Direction.UP) {
                tx = (i % 2 == 0) ? quadOffset : -quadOffset;
                ty = (i < 2) ? -quadOffset : quadOffset;
            } else {
                tx = (i % 2 == 0) ? -quadOffset : quadOffset;
                ty = (i < 2) ? quadOffset : -quadOffset;
            }

            poseStack.translate(tx, ty, depthOffset);
            poseStack.scale(scale, scale, scale);
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

            int rot = entity.getRotation(i);
            float rotDeg = (rot % QuadItemFrameEntity.NUM_ROTATIONS) * 45.0F;
            poseStack.mulPose(Axis.ZP.rotationDegrees(rotDeg));

            Minecraft.getInstance().getItemRenderer().renderStatic(
                    stack,
                    ItemDisplayContext.FIXED,
                    packedLight,
                    OverlayTexture.NO_OVERLAY,
                    poseStack,
                    buffer,
                    entity.level(),
                    0
            );

            poseStack.popPose();
        }

        poseStack.popPose();
        super.render(entity, yaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(QuadItemFrameEntity entity) {
        return QuadItemFrameModel.TEXTURE;
    }
}
