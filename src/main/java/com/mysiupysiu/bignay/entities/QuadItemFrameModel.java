package com.mysiupysiu.bignay.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class QuadItemFrameModel<T extends QuadItemFrameEntity> extends EntityModel<T> {

    private final ModelPart bb_main;

    public static final ResourceLocation TEXTURE = new ResourceLocation("bignay", "textures/entity/quad_item_frame.png");

    public QuadItemFrameModel(ModelPart root) {
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create()
                .texOffs(6, 4)
                .addBox(-6.0F, -14.0F, 0.0F, 12.0F, 12.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(4, 18)
                .addBox(-7.0F, -2.0F, 0.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 1)
                .addBox(-7.0F, -15.0F, 0.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(1, 4)
                .addBox(-7.0F, -14.0F, 0.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(31, 4)
                .addBox(6.0F, -14.0F, 0.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 4)
                .addBox(-6.0F, -14.0F, 0.5F, 12.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 48, 24);
    }

    @Override
    public void setupAnim(QuadItemFrameEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        poseStack.translate(0.0D, -1.0D, 0.0D);
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }
}
