package com.mysiupysiu.bignay.mixin;

import com.mysiupysiu.bignay.utils.BignayPacketHandler;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin<T extends AbstractContainerMenu> extends Screen {

    private static final ResourceLocation SORT_TEXTURE = new ResourceLocation("bignay", "textures/gui/widget/sort.png");
    private static final ResourceLocation UP_TEXTURE = new ResourceLocation("bignay", "textures/gui/widget/up.png");
    private static final ResourceLocation DOWN_TEXTURE = new ResourceLocation("bignay", "textures/gui/widget/down.png");

    @Shadow
    protected int leftPos;
    @Shadow
    protected int topPos;
    @Shadow
    protected int imageWidth;
    @Shadow
    @Final
    protected T menu;

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
        int contMinY = Integer.MAX_VALUE;
        boolean hasContainer = false;

        for (Slot slot : this.menu.slots) {
            if (slot.container instanceof Inventory) {
                int slotIndex = slot.getContainerSlot();
                if (slotIndex >= 9 && slotIndex <= 35) {
                    if (slot.y < invMinY) invMinY = slot.y;
                }
            } else {
                if (slot.y < contMinY) contMinY = slot.y;
                hasContainer = true;
            }
        }

        if (invMinY == Integer.MAX_VALUE) return;

        int xPos = this.leftPos + this.imageWidth - 18;

        this.bignay$addSafeButton(xPos, this.topPos + invMinY - 13, "container.sort_inventory", SORT_TEXTURE, btn ->
                BignayPacketHandler.INSTANCE.sendToServer(new BignayPacketHandler.SortPacket(true)));

        if (isChest && hasContainer && contMinY != Integer.MAX_VALUE) {
            this.bignay$addSafeButton(xPos, this.topPos + contMinY - 13, "container.sort", SORT_TEXTURE, btn ->
                    BignayPacketHandler.INSTANCE.sendToServer(new BignayPacketHandler.SortPacket(false)));

            this.bignay$addSafeButton(xPos - 13, this.topPos + invMinY - 13, "container.up", UP_TEXTURE, btn ->
                    BignayPacketHandler.INSTANCE.sendToServer(new BignayPacketHandler.TransferPacket()));

            this.bignay$addSafeButton(xPos - 26, this.topPos + invMinY - 13, "container.down", DOWN_TEXTURE, btn ->
                    BignayPacketHandler.INSTANCE.sendToServer(new BignayPacketHandler.WithdrawPacket()));
        }
    }

    @Unique
    private void bignay$addSafeButton(int x, int y, String langKey, ResourceLocation icon, Button.OnPress onPress) {
        ImageButton btn = new ImageButton(x, y, 11, 11, 0, 0, 11, icon, 16, 32, onPress) {
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
