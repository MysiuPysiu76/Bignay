package com.mysiupysiu.bignay.forge.network;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.client.containers.ContainersManager;
import com.mysiupysiu.bignay.utils.BignayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public final class BignayPacketHandler {

    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE;
    private static boolean registered = false;
    private static int id = 0;

    private BignayPacketHandler() {}

    public static void register() {
        if (registered) return;
        registered = true;

        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(BignayMod.MODID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

        INSTANCE.registerMessage(id++, SortPacket.class, SortPacket::encode, SortPacket::decode, SortPacket::handle);
        INSTANCE.registerMessage(id++, TransferPacket.class, TransferPacket::encode, TransferPacket::decode, TransferPacket::handle);
        INSTANCE.registerMessage(id++, WithdrawPacket.class, WithdrawPacket::encode, WithdrawPacket::decode, WithdrawPacket::handle);
        INSTANCE.registerMessage(id++, TotemActivationPacket.class, TotemActivationPacket::encode, TotemActivationPacket::decode, TotemActivationPacket::handle);

        BignayNetworking.init(BignayPacketHandler::sendTotemActivation);
    }

    public static void sendTotemActivation(ServerPlayer player, ItemStack stack) {
        if (player == null || stack == null || INSTANCE == null) return;
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new TotemActivationPacket(stack));
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

        public static void handle(SortPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if (player != null && player.containerMenu != null) {
                    ContainersManager.sort(player.containerMenu, msg.isPlayerInventory);
                    player.containerMenu.sendAllDataToRemote();
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }

    public static class TransferPacket {

        public TransferPacket() {}

        public static void encode(TransferPacket msg, FriendlyByteBuf buf) {}

        public static TransferPacket decode(FriendlyByteBuf buf) {
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

        public static void encode(WithdrawPacket msg, FriendlyByteBuf buf) {}

        public static WithdrawPacket decode(FriendlyByteBuf buf) {
            return new WithdrawPacket();
        }

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

        public static void handle(TotemActivationPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
            NetworkEvent.Context ctx = ctxSupplier.get();
            ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ForgeTotemClientHandler.play(msg.stack)));
            ctx.setPacketHandled(true);
        }
    }
}
