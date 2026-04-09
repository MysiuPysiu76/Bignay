package com.mysiupysiu.bignay.world.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;

// Picking items on servers because of servers we don't have client package
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
