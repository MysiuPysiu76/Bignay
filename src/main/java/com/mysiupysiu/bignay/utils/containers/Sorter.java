package com.mysiupysiu.bignay.utils.containers;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Sorter {

    public enum SortMode {
        ALPHABETICAL,
        QUANTITY
    }

    public static void sortInventory(AbstractContainerMenu menu) {
        List<Slot> playerSlots = new ArrayList<>();

        for (Slot slot : menu.slots) {
            if (slot.container instanceof Inventory) {
                int slotIndex = slot.getContainerSlot();
                if (slotIndex >= 9 && slotIndex <= 35) {
                    playerSlots.add(slot);
                }
            }
        }

        sortSlotList(playerSlots, SortMode.QUANTITY);
    }

    public static void sortContainer(AbstractContainerMenu menu) {
        List<Slot> containerSlots = new ArrayList<>();

        for (Slot slot : menu.slots) {
            if (!(slot.container instanceof Inventory)) {
                containerSlots.add(slot);
            }
        }

        sortSlotList(containerSlots, SortMode.QUANTITY);
    }

    private static void sortSlotList(List<Slot> slots, SortMode mode) {
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
            int count1 = stack1.getCount();
            int count2 = stack2.getCount();

            if (mode == SortMode.QUANTITY) {
                int countCompare = Integer.compare(count2, count1);
                if (countCompare != 0) return countCompare;
                return name1.compareToIgnoreCase(name2);
            } else {
                int nameCompare = name1.compareToIgnoreCase(name2);
                if (nameCompare != 0) return nameCompare;
                return Integer.compare(count2, count1);
            }
        });

        for (int i = 0; i < mergedItems.size(); i++) {
            if (i < slots.size()) {
                slots.get(i).set(mergedItems.get(i));
                slots.get(i).setChanged();
            }
        }
    }
}