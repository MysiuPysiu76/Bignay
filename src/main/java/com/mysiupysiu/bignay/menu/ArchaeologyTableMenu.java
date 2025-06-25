package com.mysiupysiu.bignay.menu;

import com.mysiupysiu.bignay.items.ItemInit;
import com.mysiupysiu.bignay.util.BignayTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.SimpleContainer;

public class ArchaeologyTableMenu extends AbstractContainerMenu {
    private final SimpleContainer container = new SimpleContainer(3) {
        @Override
        public void setChanged() {
            super.setChanged();
            updateResult();
        }
    };

    public ArchaeologyTableMenu(int id, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(id, playerInventory);
    }

    public ArchaeologyTableMenu(int id, Inventory playerInventory) {
        super(MenuInit.ARCHAEOLOGY_TABLE_MENU.get(), id);

        this.addSlot(new Slot(container, 0, 27, 23) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(BignayTags.Items.POTTERY_SHERDS);
            }
        });

        this.addSlot(new Slot(container, 1, 76, 23) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() == ItemInit.POTTER_SHERD.get();
            }
        });

        this.addSlot(new Slot(container, 2, 134, 23) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public boolean mayPickup(Player player) {
                return !container.getItem(2).isEmpty();
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                super.onTake(player, stack);

                container.getItem(0).shrink(1);
                container.getItem(1).shrink(1);

                updateResult();
            }
        });

        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 60 + i * 18));
        for (int k = 0; k < 9; ++k)
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 118));
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack original = slot.getItem();
            newStack = original.copy();

            if (index == 2) {
                if (!this.moveItemStackTo(original, 3, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }

                ItemStack slot0 = container.getItem(0);
                ItemStack slot1 = container.getItem(1);

                if (!slot0.isEmpty() && !slot1.isEmpty()) {
                    slot0.shrink(1);
                    slot1.shrink(1);
                }

                updateResult();

                if (slot0.isEmpty()) container.setItem(0, ItemStack.EMPTY);
                if (slot1.isEmpty()) container.setItem(1, ItemStack.EMPTY);

                slot.setChanged();

            } else if (index < 3) {
                if (!this.moveItemStackTo(original, 3, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (original.is(BignayTags.Items.POTTERY_SHERDS)) {
                    if (!this.moveItemStackTo(original, 0, 1, false)) return ItemStack.EMPTY;
                } else if (original.getItem() == ItemInit.POTTER_SHERD.get()) {
                    if (!this.moveItemStackTo(original, 1, 2, false)) return ItemStack.EMPTY;
                } else {
                    return ItemStack.EMPTY;
                }
            }

            if (original.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return newStack;
    }

    private void updateResult() {
        ItemStack slot0 = container.getItem(0);
        ItemStack slot1 = container.getItem(1);

        if (!slot0.isEmpty() && !slot1.isEmpty() && slot0.is(BignayTags.Items.POTTERY_SHERDS) && slot1.getItem() == ItemInit.POTTER_SHERD.get()) {
            ItemStack result = new ItemStack(slot0.getItem(), 2);
            if (!ItemStack.isSameItemSameTags(container.getItem(2), result)) {
                container.setItem(2, result);
            }
        } else {
            if (!container.getItem(2).isEmpty()) {
                container.setItem(2, ItemStack.EMPTY);
            }
        }
    }

    @Override
    public void removed(Player player) {
        super.removed(player);

        if (!player.level().isClientSide) {
            for (int i = 0; i < 2; i++) {
                ItemStack stack = container.getItem(i);
                if (!stack.isEmpty()) {
                    player.getInventory().placeItemBackInInventory(stack);
                }
            }

            container.setItem(2, ItemStack.EMPTY);
        }
    }
}
