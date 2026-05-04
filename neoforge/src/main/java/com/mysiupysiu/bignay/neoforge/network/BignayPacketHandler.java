package com.mysiupysiu.bignay.neoforge.network;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.client.containers.ContainersManager;
import com.mysiupysiu.bignay.utils.BignayNetworking;
import com.mysiupysiu.bignay.utils.TotemClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.simple.SimpleChannel;
import net.neoforged.neoforge.network.NetworkRegistry;
import net.neoforged.neoforge.network.NetworkEvent;

import java.util.function.Supplier;

public final class BignayPacketHandler {

    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE;
    private static int id = 0;

    public static void register() {
        INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(BignayMod.MODID, "main")).networkProtocolVersion(() ->
                PROTOCOL_VERSION).clientAcceptedVersions(PROTOCOL_VERSION::equals).serverAcceptedVersions(PROTOCOL_VERSION::equals).simpleChannel();

        INSTANCE.messageBuilder(SortPacket.class, id++).encoder(SortPacket::encode).decoder(SortPacket::decode).consumerMainThread(SortPacket::handle).add();

        INSTANCE.messageBuilder(TransferPacket.class, id++).encoder(TransferPacket::encode).decoder(TransferPacket::decode).consumerMainThread(TransferPacket::handle).add();

        INSTANCE.messageBuilder(WithdrawPacket.class, id++).encoder(WithdrawPacket::encode).decoder(WithdrawPacket::decode).consumerMainThread(WithdrawPacket::handle).add();

        INSTANCE.messageBuilder(TotemActivationPacket.class, id++).encoder(TotemActivationPacket::encode).decoder(TotemActivationPacket::decode).consumerMainThread(TotemActivationPacket::handle).add();

        BignayNetworking.init(BignayPacketHandler::sendTotemActivation);
    }

    public static void sendTotemActivation(ServerPlayer player, ItemStack stack) {
        if (player != null && INSTANCE != null) {
            INSTANCE.send(PacketDistributor.PLAYER.with((Supplier<ServerPlayer>) player), new TotemActivationPacket(stack));
        }
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

        public static void handle(SortPacket msg, NetworkEvent.Context ctx) {
            ctx.enqueueWork(() -> {
                ServerPlayer player = ctx.getSender();
                if (player != null && player.containerMenu != null) {
                    ContainersManager.sort(player.containerMenu, msg.isPlayerInventory);
                    player.containerMenu.sendAllDataToRemote();
                }
            });
            ctx.setPacketHandled(true);
        }
    }

    public static class TransferPacket {
        public static void encode(TransferPacket msg, FriendlyByteBuf buf) {
        }

        public static TransferPacket decode(FriendlyByteBuf buf) {
            return new TransferPacket();
        }

        public static void handle(TransferPacket msg, NetworkEvent.Context ctx) {
            ctx.enqueueWork(() -> {
                ServerPlayer player = ctx.getSender();
                if (player != null && player.containerMenu != null) {
                    ContainersManager.transferToContainer(player.containerMenu);
                    player.containerMenu.sendAllDataToRemote();
                }
            });
            ctx.setPacketHandled(true);
        }
    }

    public static class WithdrawPacket {
        public static void encode(WithdrawPacket msg, FriendlyByteBuf buf) {
        }

        public static WithdrawPacket decode(FriendlyByteBuf buf) {
            return new WithdrawPacket();
        }

        public static void handle(WithdrawPacket msg, NetworkEvent.Context ctx) {
            ctx.enqueueWork(() -> {
                ServerPlayer player = ctx.getSender();
                if (player != null && player.containerMenu != null) {
                    ContainersManager.transferToPlayer(player.containerMenu);
                    player.containerMenu.sendAllDataToRemote();
                }
            });
            ctx.setPacketHandled(true);
        }
    }

    public static class TotemActivationPacket {
        private final ItemStack stack;

        public TotemActivationPacket(ItemStack stack) {
            this.stack = stack;
        }

        public static void encode(TotemActivationPacket msg, FriendlyByteBuf buf) {
            buf.writeItem(msg.stack);
        }

        public static TotemActivationPacket decode(FriendlyByteBuf buf) {
            return new TotemActivationPacket(buf.readItem());
        }

        public static void handle(TotemActivationPacket msg, NetworkEvent.Context ctx) {
            ctx.enqueueWork(() -> {
                TotemClientHandler.play(msg.stack);
            });
            ctx.setPacketHandled(true);
        }
    }
}
