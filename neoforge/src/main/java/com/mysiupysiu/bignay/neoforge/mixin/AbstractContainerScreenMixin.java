package com.mysiupysiu.bignay.neoforge.mixin;

import com.mysiupysiu.bignay.config.BignayConfig;
import com.mysiupysiu.bignay.neoforge.network.BignayPacketHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin<T extends AbstractContainerMenu> extends Screen {

    @Unique
    private static final ResourceLocation SORT_TEXTURE = new ResourceLocation("bignay", "textures/gui/widget/sort.png");
    @Unique
    private static final ResourceLocation UP_TEXTURE = new ResourceLocation("bignay", "textures/gui/widget/up.png");
    @Unique
    private static final ResourceLocation DOWN_TEXTURE = new ResourceLocation("bignay", "textures/gui/widget/down.png");

    @Shadow protected int leftPos;
    @Shadow protected int topPos;
    @Shadow @Final protected T menu;

    protected AbstractContainerScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "init", at = @At("RETURN"))
    protected void bignay$addSortButtons(CallbackInfo ci) {
        if (this.minecraft == null || this.minecraft.player == null) return;

        Object self = this;
        boolean isInv = self instanceof InventoryScreen;
        boolean isChest = self instanceof ContainerScreen;
        boolean isMachine = self instanceof FurnaceScreen || self instanceof CraftingScreen || self instanceof AbstractFurnaceScreen || self instanceof StonecutterScreen;

        if (!isInv && !isChest && !isMachine) return;

        int invMinY = Integer.MAX_VALUE;
        int invMaxX = Integer.MIN_VALUE;
        int contMinY = Integer.MAX_VALUE;
        boolean hasContainer = false;

        for (Slot slot : this.menu.slots) {
            if (slot.container instanceof Inventory) {
                int slotIndex = slot.getContainerSlot();
                if (slotIndex >= 9 && slotIndex <= 35) {
                    if (slot.y < invMinY) invMinY = slot.y;
                }
                if (slot.x > invMaxX) invMaxX = slot.x;
            } else {
                if (slot.y < contMinY) contMinY = slot.y;
                hasContainer = true;
            }
        }

        if (invMaxX == Integer.MIN_VALUE) return;

        final int xOffset = invMaxX + 6;

        if (BignayConfig.containers.showSortInventory.get()) {
            this.bignay$addSafeButton(xOffset, invMinY - 13, "container.sort_inventory", SORT_TEXTURE, btn ->
                    BignayPacketHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new BignayPacketHandler.SortPacket(true)));
        }

        if (isChest && hasContainer && contMinY != Integer.MAX_VALUE) {
            if (BignayConfig.containers.showSortContainer.get()) {
                this.bignay$addSafeButton(xOffset, contMinY - 13, "container.sort", SORT_TEXTURE, btn ->
                        BignayPacketHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new BignayPacketHandler.SortPacket(false)));
            }

            if (BignayConfig.containers.showTransferToContainer.get()) {
                this.bignay$addSafeButton(xOffset - 13, invMinY - 13, "container.up", UP_TEXTURE, btn ->
                        BignayPacketHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new BignayPacketHandler.TransferPacket()));
            }

            if (BignayConfig.containers.showTransferToInventory.get()) {
                this.bignay$addSafeButton(xOffset - 26, invMinY - 13, "container.down", DOWN_TEXTURE, btn ->
                        BignayPacketHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new BignayPacketHandler.WithdrawPacket()));
            }
        }
    }

    @Unique
    private void bignay$addSafeButton(int xOffset, int yOffset, String langKey, ResourceLocation icon, Button.OnPress onPress) {
        WidgetSprites sprites = new WidgetSprites(icon, icon);

        ImageButton btn = new ImageButton(0, 0, 11, 11, sprites, onPress, Component.translatable(langKey)) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
                this.setX(AbstractContainerScreenMixin.this.leftPos + xOffset);
                this.setY(AbstractContainerScreenMixin.this.topPos + yOffset);

                float vOffset = this.isHoveredOrFocused() ? 11.0f : 0.0f;

                guiGraphics.blit(icon, this.getX(), this.getY(), 0.0f, vOffset, 11, 11, 16, 32);
            }

            @Override
            public void onClick(double mouseX, double mouseY) {
                super.onClick(mouseX, mouseY);
                this.setFocused(false);
            }

            @Override
            public boolean isFocused() {
                return false;
            }
        };

        btn.setTooltip(Tooltip.create(Component.translatable(langKey)));
        this.addRenderableWidget(btn);
    }
}
