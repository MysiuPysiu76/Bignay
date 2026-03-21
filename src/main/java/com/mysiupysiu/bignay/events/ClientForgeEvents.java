package com.mysiupysiu.bignay.events;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.items.EquipablePumpkinItem;
import com.mysiupysiu.bignay.items.ItemInit;
import com.mysiupysiu.bignay.utils.BignayTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BignayMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEvents {

    private static final ResourceLocation PUMPKIN_BLUR = new ResourceLocation("textures/misc/pumpkinblur.png");

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay() != VanillaGuiOverlay.HELMET.type()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        ItemStack helmet = mc.player.getItemBySlot(EquipmentSlot.HEAD);

        if (helmet.getItem() instanceof EquipablePumpkinItem && mc.options.getCameraType().isFirstPerson()) {
            GuiGraphics guiGraphics = event.getGuiGraphics();

            int width = guiGraphics.guiWidth();
            int height = guiGraphics.guiHeight();

            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);

            guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            guiGraphics.blit(PUMPKIN_BLUR, 0, 0, -90, 0.0F, 0.0F, width, height, width, height);

            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();

            guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @SubscribeEvent
    public static void onGoatHornUse(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        if (event.getItemStack().is(BignayTags.Items.HORNS)) {
            player.getCooldowns().addCooldown(Items.GOAT_HORN, 140);
            player.getCooldowns().addCooldown(ItemInit.COPPER_HORN.get(), 140);
        }
    }
}
