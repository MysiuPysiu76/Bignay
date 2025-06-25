package com.mysiupysiu.bignay.menu;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class ArchaeologyTableScreen extends AbstractContainerScreen<ArchaeologyTableMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("bignay", "textures/gui/archaeology_table.png");

    public ArchaeologyTableScreen(ArchaeologyTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 142;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
        RenderSystem.setShaderTexture(0, TEXTURE);
        int startX = (width - imageWidth) / 2;
        int startY = (height - imageHeight) / 2;
        guiGraphics.blit(TEXTURE, startX, startY, 0, 0, imageWidth, imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, 8, 6, 0x3F3F3F, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, 8, this.imageHeight - 94, 0x3F3F3F, false);

        if (isHoveringSlot(0, mouseX, mouseY)) {
            guiGraphics.renderTooltip(this.font, Component.translatable("tooltip.bignay.archaeology_table.pottery_sherd_to_duplicate"), mouseX - this.leftPos, mouseY - this.topPos);
        }

        if (isHoveringSlot(1, mouseX, mouseY)) {
            guiGraphics.renderTooltip(this.font, Component.translatable("tooltip.bignay.archaeology_table.empty_pottery_sherd"), mouseX - this.leftPos, mouseY - this.topPos);
        }
    }

    private boolean isHoveringSlot(int slotIndex, int mouseX, int mouseY) {
        if (slotIndex >= menu.slots.size()) return false;
        Slot slot = this.menu.slots.get(slotIndex);
        int x = this.leftPos + slot.x;
        int y = this.topPos + slot.y;
        return mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 16;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
