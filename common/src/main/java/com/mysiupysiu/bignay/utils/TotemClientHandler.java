package com.mysiupysiu.bignay.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

public final class TotemClientHandler {

    private TotemClientHandler() {}

    public static void play(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc != null && mc.gameRenderer != null) {
            mc.gameRenderer.displayItemActivation(stack);
        }
    }
}
