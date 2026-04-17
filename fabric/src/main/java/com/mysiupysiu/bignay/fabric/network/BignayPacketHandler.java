package com.mysiupysiu.bignay.fabric.network;

import com.mysiupysiu.bignay.client.containers.ContainersManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class BignayPacketHandler {

    public static final ResourceLocation SORT = new ResourceLocation("bignay", "sort");
    public static final ResourceLocation TRANSFER = new ResourceLocation("bignay", "transfer");
    public static final ResourceLocation WITHDRAW = new ResourceLocation("bignay", "withdraw");

    public static void register() {

        ServerPlayNetworking.registerGlobalReceiver(SORT, (server, player, handler, buf, responseSender) -> {
            SortPacket msg = SortPacket.decode(buf);
            server.execute(() -> msg.handle(player));
        });

        ServerPlayNetworking.registerGlobalReceiver(TRANSFER, (server, player, handler, buf, responseSender) -> {
            TransferPacket msg = TransferPacket.decode(buf);
            server.execute(() -> msg.handle(player));
        });

        ServerPlayNetworking.registerGlobalReceiver(WITHDRAW, (server, player, handler, buf, responseSender) -> {
            WithdrawPacket msg = WithdrawPacket.decode(buf);
            server.execute(() -> msg.handle(player));
        });
    }

    @Environment(EnvType.CLIENT)
    public static void sendSortPacket(boolean isPlayerInventory) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        SortPacket.encode(new SortPacket(isPlayerInventory), buf);
        ClientPlayNetworking.send(SORT, buf);
    }

    @Environment(EnvType.CLIENT)
    public static void sendTransferPacket() {
        FriendlyByteBuf buf = PacketByteBufs.create();
        ClientPlayNetworking.send(TRANSFER, buf);
    }

    @Environment(EnvType.CLIENT)
    public static void sendWithdrawPacket() {
        FriendlyByteBuf buf = PacketByteBufs.create();
        ClientPlayNetworking.send(WITHDRAW, buf);
    }

    public static class SortPacket {
        private final boolean isPlayerInventory;

        public SortPacket(boolean isPlayerInventory) {
            this.isPlayerInventory = isPlayerInventory;
        }

        public static void encode(SortPacket msg, FriendlyByteBuf buf) {
            buf.writeBoolean(msg.isPlayerInventory);
        }

        public static SortPacket decode(FriendlyByteBuf buf) {
            return new SortPacket(buf.readBoolean());
        }

        public void handle(ServerPlayer player) {
            if (player != null && player.containerMenu != null) {
                ContainersManager.sort(player.containerMenu, isPlayerInventory);
                player.containerMenu.broadcastChanges();
            }
        }
    }

    public static class TransferPacket {

        public TransferPacket() {}

        public static void encode(TransferPacket msg, FriendlyByteBuf buf) {}

        public static TransferPacket decode(FriendlyByteBuf buf) {
            return new TransferPacket();
        }

        public void handle(ServerPlayer player) {
            if (player != null && player.containerMenu != null) {
                ContainersManager.transferToContainer(player.containerMenu);
                player.containerMenu.broadcastChanges();
            }
        }
    }

    public static class WithdrawPacket {

        public WithdrawPacket() {}

        public static void encode(WithdrawPacket msg, FriendlyByteBuf buf) {}

        public static WithdrawPacket decode(FriendlyByteBuf buf) {
            return new WithdrawPacket();
        }

        public void handle(ServerPlayer player) {
            if (player != null && player.containerMenu != null) {
                ContainersManager.transferToPlayer(player.containerMenu);
                player.containerMenu.broadcastChanges();
            }
        }
    }
}
