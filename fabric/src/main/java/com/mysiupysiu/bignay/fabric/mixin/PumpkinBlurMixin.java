package com.mysiupysiu.bignay.fabric.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mysiupysiu.bignay.world.items.EquipablePumpkinItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class PumpkinBlurMixin {
    @Shadow @Final private Minecraft minecraft;
    @Shadow @Final private static ResourceLocation PUMPKIN_BLUR_LOCATION;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHotbar(FLnet/minecraft/client/gui/GuiGraphics;)V"))
    private void bignay$renderCustomPumpkinBlur(GuiGraphics guiGraphics, float f, CallbackInfo ci) {
        if (this.minecraft.player == null) return;

        ItemStack helmet = this.minecraft.player.getItemBySlot(EquipmentSlot.HEAD);

        if (helmet.getItem() instanceof EquipablePumpkinItem && this.minecraft.options.getCameraType().isFirstPerson()) {
            this.bignay$renderBlur(guiGraphics);
        }
    }

    @Unique
    private void bignay$renderBlur(GuiGraphics guiGraphics) {
        int width = guiGraphics.guiWidth();
        int height = guiGraphics.guiHeight();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);

        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(PUMPKIN_BLUR_LOCATION, 0, 0, -90, 0.0F, 0.0F, width, height, width, height);

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
