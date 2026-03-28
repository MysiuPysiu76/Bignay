package com.mysiupysiu.bignay.utils;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ContainersManager {

    public static void sortPlayerInventory(AbstractContainerMenu menu) {
        List<Slot> playerSlots = new ArrayList<>();

        for (Slot slot : menu.slots) {
            if (slot.container instanceof Inventory) {
                int slotIndex = slot.getContainerSlot();
                if (slotIndex >= 9 && slotIndex <= 35) {
                    playerSlots.add(slot);
                }
            }
        }

        sortSlotList(playerSlots);
    }

    public static void sortContainer(AbstractContainerMenu menu) {
        List<Slot> containerSlots = new ArrayList<>();

        for (Slot slot : menu.slots) {
            if (!(slot.container instanceof Inventory)) {
                containerSlots.add(slot);
            }
        }

        sortSlotList(containerSlots);
    }

    private static void sortSlotList(List<Slot> slots) {
        if (slots.isEmpty()) return;

        List<ItemStack> items = new ArrayList<>();
        for (Slot slot : slots) {
            if (slot.hasItem()) {
                items.add(slot.getItem().copy());
                slot.set(ItemStack.EMPTY);
            }
        }

        if (items.isEmpty()) return;

        List<ItemStack> mergedItems = new ArrayList<>();
        for (ItemStack stack : items) {
            boolean added = false;

            for (ItemStack existing : mergedItems) {
                if (ItemStack.isSameItemSameTags(existing, stack)) {
                    int spaceLeft = existing.getMaxStackSize() - existing.getCount();
                    if (spaceLeft > 0) {
                        int amountToAdd = Math.min(spaceLeft, stack.getCount());
                        existing.grow(amountToAdd);
                        stack.shrink(amountToAdd);

                        if (stack.isEmpty()) {
                            added = true;
                            break;
                        }
                    }
                }
            }

            if (!stack.isEmpty()) {
                mergedItems.add(stack);
            }
        }

        mergedItems.sort((stack1, stack2) -> {
            String name1 = stack1.getHoverName().getString();
            String name2 = stack2.getHoverName().getString();

            int nameCompare = name1.compareToIgnoreCase(name2);
            if (nameCompare != 0) {
                return nameCompare;
            }

            return Integer.compare(stack2.getCount(), stack1.getCount());
        });

        for (int i = 0; i < mergedItems.size(); i++) {
            if (i < slots.size()) {
                slots.get(i).set(mergedItems.get(i));
                slots.get(i).setChanged();
            }
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
