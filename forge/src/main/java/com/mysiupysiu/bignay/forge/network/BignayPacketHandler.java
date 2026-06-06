package com.mysiupysiu.bignay.forge.network;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.client.containers.ContainersManager;
import com.mysiupysiu.bignay.utils.BignayNetworking;
import com.mysiupysiu.bignay.utils.TotemClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

public final class BignayPacketHandler {

    private static final int PROTOCOL_VERSION = 1;
    public static SimpleChannel INSTANCE;
    private static boolean registered = false;
    private static int id = 0;

    private BignayPacketHandler() {
    }

    public static void register() {
        if (registered) return;
        registered = true;

        INSTANCE = ChannelBuilder.named(ResourceLocation.tryBuild(BignayMod.MODID, "main")).networkProtocolVersion(PROTOCOL_VERSION).clientAcceptedVersions((status, version) ->
                version == PROTOCOL_VERSION).serverAcceptedVersions((status, version) -> version == PROTOCOL_VERSION).simpleChannel();

        INSTANCE.messageBuilder(SortPacket.class, id++, NetworkDirection.PLAY_TO_SERVER).encoder(SortPacket::encode).decoder(SortPacket::decode).consumerMainThread(SortPacket::handle).add();

        INSTANCE.messageBuilder(TransferPacket.class, id++, NetworkDirection.PLAY_TO_SERVER).encoder(TransferPacket::encode).decoder(TransferPacket::decode).consumerMainThread(TransferPacket::handle).add();

        INSTANCE.messageBuilder(WithdrawPacket.class, id++, NetworkDirection.PLAY_TO_SERVER).encoder(WithdrawPacket::encode).decoder(WithdrawPacket::decode).consumerMainThread(WithdrawPacket::handle).add();

        INSTANCE.messageBuilder(TotemActivationPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT).encoder(TotemActivationPacket::encode).decoder(TotemActivationPacket::decode).consumerMainThread(TotemActivationPacket::handle).add();

        BignayNetworking.init(BignayPacketHandler::sendTotemActivation);
    }

    public static void sendTotemActivation(ServerPlayer player, ItemStack stack) {
        if (player == null || stack == null || INSTANCE == null) return;

        INSTANCE.send(new TotemActivationPacket(stack), PacketDistributor.PLAYER.with(player));
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

        public static void handle(SortPacket msg, CustomPayloadEvent.Context ctx) {
            ServerPlayer player = ctx.getSender();
            if (player != null && player.containerMenu != null) {
                ContainersManager.sort(player.containerMenu, msg.isPlayerInventory);
                player.containerMenu.sendAllDataToRemote();
            }
            ctx.setPacketHandled(true);
        }
    }

    public static class TransferPacket {
        public static void encode(TransferPacket msg, FriendlyByteBuf buf) {}

        public static TransferPacket decode(FriendlyByteBuf buf) {
            return new TransferPacket();
        }

        public static void handle(TransferPacket msg, CustomPayloadEvent.Context ctx) {
            ServerPlayer player = ctx.getSender();
            if (player != null && player.containerMenu != null) {
                ContainersManager.transferToContainer(player.containerMenu);
                player.containerMenu.sendAllDataToRemote();
            }
            ctx.setPacketHandled(true);
        }
    }

    public static class WithdrawPacket {
        public static void encode(WithdrawPacket msg, FriendlyByteBuf buf) {}

        public static WithdrawPacket decode(FriendlyByteBuf buf) {
            return new WithdrawPacket();
        }

        public static void handle(WithdrawPacket msg, CustomPayloadEvent.Context ctx) {
            ServerPlayer player = ctx.getSender();
            if (player != null && player.containerMenu != null) {
                ContainersManager.transferToPlayer(player.containerMenu);
                player.containerMenu.sendAllDataToRemote();
            }
            ctx.setPacketHandled(true);
        }
    }

    public static class TotemActivationPacket {
        private final ItemStack stack;

        public TotemActivationPacket(ItemStack stack) {
            this.stack = stack == null ? ItemStack.EMPTY : stack.copy();
        }

        public static void encode(TotemActivationPacket msg, FriendlyByteBuf buf) {
            buf.writeItemStack(msg.stack, false);
        }

        public static TotemActivationPacket decode(FriendlyByteBuf buf) {
            return new TotemActivationPacket(buf.readItem());
        }

        public static void handle(TotemActivationPacket msg, CustomPayloadEvent.Context ctx) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                    TotemClientHandler.play(msg.stack));
            ctx.setPacketHandled(true);
        }
    }
}
