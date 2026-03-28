package com.mysiupysiu.bignay.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class BignayPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("bignay", "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void register() {
        int id = 0;
        INSTANCE.registerMessage(id++, SortPacket.class, SortPacket::encode, SortPacket::decode, SortPacket::handle);
        INSTANCE.registerMessage(id++, TransferPacket.class, TransferPacket::encode, TransferPacket::decode, TransferPacket::handle);
        INSTANCE.registerMessage(id++, WithdrawPacket.class, WithdrawPacket::encode, WithdrawPacket::decode, WithdrawPacket::handle);
    }

    public static class SortPacket {
        private final boolean isPlayerInventory;

        public SortPacket(boolean isPlayerInventory) {
            this.isPlayerInventory = isPlayerInventory;
        }

        public static void encode(SortPacket msg, net.minecraft.network.FriendlyByteBuf buf) {
            buf.writeBoolean(msg.isPlayerInventory);
        }

        public static SortPacket decode(net.minecraft.network.FriendlyByteBuf buf) {
            return new SortPacket(buf.readBoolean());
        }

        public static void handle(SortPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if (player != null && player.containerMenu != null) {
                    if (msg.isPlayerInventory) {
                        ContainersManager.sortPlayerInventory(player.containerMenu);
                    } else {
                        ContainersManager.sortContainer(player.containerMenu);
                    }
                    player.containerMenu.sendAllDataToRemote();
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }

    public static class TransferPacket {
        public TransferPacket() {}

        public static void encode(TransferPacket msg, net.minecraft.network.FriendlyByteBuf buf) {}

        public static TransferPacket decode(net.minecraft.network.FriendlyByteBuf buf) {
            return new TransferPacket();
        }

        public static void handle(TransferPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if (player != null && player.containerMenu != null) {
                    ContainersManager.transferToContainer(player.containerMenu);
                    player.containerMenu.sendAllDataToRemote();
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }

    public static class WithdrawPacket {
        public WithdrawPacket() {}
        public static void encode(WithdrawPacket msg, net.minecraft.network.FriendlyByteBuf buf) {}
        public static WithdrawPacket decode(net.minecraft.network.FriendlyByteBuf buf) { return new WithdrawPacket(); }

        public static void handle(WithdrawPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if (player != null && player.containerMenu != null) {
                    ContainersManager.transferToPlayer(player.containerMenu);
                    player.containerMenu.sendAllDataToRemote();
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
