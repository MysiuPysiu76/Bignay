package com.mysiupysiu.bignay.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;

public class QuadItemFrameClientService {

    public static ItemStack getCustomPickResult(QuadItemFrameEntity frame) {
        Minecraft mc = net.minecraft.client.Minecraft.getInstance();
        LocalPlayer player = mc.player;

        if (player != null) {
            int slot = frame.getQuadrantFromHit(player);
            ItemStack s = frame.getItem(slot);
            if (!s.isEmpty()) return s.copy();
        }
        return frame.getFrameItemStack();
    }
}
