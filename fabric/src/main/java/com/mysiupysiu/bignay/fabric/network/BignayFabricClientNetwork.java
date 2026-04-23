package com.mysiupysiu.bignay.fabric.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public final class BignayFabricClientNetwork {

    private BignayFabricClientNetwork() {}

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(BignayPacketHandler.TOTEM_ACTIVATION, (client, handler, buf, responseSender) -> {
            ItemStack stack = buf.readItem();
            client.execute(() -> {
                Minecraft mc = Minecraft.getInstance();
                if (mc != null && mc.gameRenderer != null) {
                    mc.gameRenderer.displayItemActivation(stack);
                }
            });
        });
    }
}
