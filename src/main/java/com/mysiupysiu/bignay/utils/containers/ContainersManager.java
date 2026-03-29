package com.mysiupysiu.bignay.utils.containers;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

class ContainersManager {

    public static void sort(AbstractContainerMenu container, boolean isInventory) {
        if (isInventory) {
            Sorter.sortInventory(container);
        } else  {
            Sorter.sortContainer(container);
        }
    }

    public static void transferToContainer(AbstractContainerMenu menu) {
        List<Slot> playerSlots = new ArrayList<>();
        List<Slot> containerSlots = new ArrayList<>();

        for (Slot slot : menu.slots) {
            if (slot.container instanceof Inventory) {
                int slotIndex = slot.getContainerSlot();
                if (slotIndex >= 9 && slotIndex <= 35) {
                    playerSlots.add(slot);
                }
            } else {
                containerSlots.add(slot);
            }
        }

        for (Slot pSlot : playerSlots) {
            if (!pSlot.hasItem()) continue;
            ItemStack pStack = pSlot.getItem();

            for (Slot cSlot : containerSlots) {
                if (!cSlot.hasItem() || !cSlot.mayPlace(pStack)) continue;
                ItemStack cStack = cSlot.getItem();

                if (ItemStack.isSameItemSameTags(pStack, cStack)) {
                    int spaceLeft = cStack.getMaxStackSize() - cStack.getCount();
                    if (spaceLeft > 0) {
                        int amountToMove = Math.min(spaceLeft, pStack.getCount());
                        cStack.grow(amountToMove);
                        pStack.shrink(amountToMove);
                        cSlot.setChanged();

                        if (pStack.isEmpty()) break;
                    }
                }
            }

            if (!pStack.isEmpty()) {
                for (Slot cSlot : containerSlots) {
                    if (!cSlot.hasItem() && cSlot.mayPlace(pStack)) {
                        cSlot.set(pStack.copy());
                        pStack.setCount(0);
                        cSlot.setChanged();
                        break;
                    }
                }
            }

            if (pStack.isEmpty()) {
                pSlot.set(ItemStack.EMPTY);
            } else {
                pSlot.set(pStack);
            }
            pSlot.setChanged();
        }
    }

    public static void transferToPlayer(AbstractContainerMenu menu) {
        List<Slot> playerSlots = new ArrayList<>();
        List<Slot> containerSlots = new ArrayList<>();

        for (Slot slot : menu.slots) {
            if (slot.container instanceof Inventory) {
                int slotIndex = slot.getContainerSlot();
                if (slotIndex >= 9 && slotIndex <= 35) {
                    playerSlots.add(slot);
                }
            } else {
                containerSlots.add(slot);
            }
        }

        for (Slot cSlot : containerSlots) {
            if (!cSlot.hasItem()) continue;
            ItemStack cStack = cSlot.getItem();

            for (Slot pSlot : playerSlots) {
                if (!pSlot.hasItem()) continue;
                ItemStack pStack = pSlot.getItem();

                if (ItemStack.isSameItemSameTags(cStack, pStack)) {
                    int spaceLeft = pStack.getMaxStackSize() - pStack.getCount();
                    if (spaceLeft > 0) {
                        int amountToMove = Math.min(spaceLeft, cStack.getCount());
                        pStack.grow(amountToMove);
                        cStack.shrink(amountToMove);
                        pSlot.setChanged();
                        if (cStack.isEmpty()) break;
                    }
                }
            }

            if (!cStack.isEmpty()) {
                for (Slot pSlot : playerSlots) {
                    if (!pSlot.hasItem()) {
                        pSlot.set(cStack.copy());
                        cStack.setCount(0);
                        pSlot.setChanged();
                        break;
                    }
                }
            }

            if (cStack.isEmpty()) {
                cSlot.set(ItemStack.EMPTY);
            } else {
                cSlot.set(cStack);
            }
            cSlot.setChanged();
        }
    }
}
