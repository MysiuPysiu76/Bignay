package com.mysiupysiu.bignay.utils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public final class BignayNetworking {

    public interface Impl {
        void sendTotemActivation(ServerPlayer player, ItemStack stack);
    }

    private static Impl impl = (player, stack) -> {};

    private BignayNetworking() {}

    public static void init(Impl implementation) {
        impl = implementation;
    }

    public static void sendTotemActivation(ServerPlayer player, ItemStack stack) {
        if (player == null) return;
        impl.sendTotemActivation(player, stack == null ? ItemStack.EMPTY : stack.copy());
    }
}
