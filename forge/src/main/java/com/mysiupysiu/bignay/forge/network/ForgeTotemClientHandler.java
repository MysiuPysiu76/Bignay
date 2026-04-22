package com.mysiupysiu.bignay.forge.network;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

public final class ForgeTotemClientHandler {

    private ForgeTotemClientHandler() {}

    public static void play(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc != null && mc.gameRenderer != null) {
            mc.gameRenderer.displayItemActivation(stack);
        }
    }
}
