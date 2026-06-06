package com.mysiupysiu.bignay.neoforge.network;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.client.containers.ContainersManager;
import com.mysiupysiu.bignay.utils.BignayNetworking;
import com.mysiupysiu.bignay.utils.TotemClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

public final class BignayPacketHandler {

    private BignayPacketHandler() {}

    public static void register() {
        BignayNetworking.init(BignayPacketHandler::sendTotemActivation);
    }

    public static void onRegisterPayloads(final RegisterPayloadHandlerEvent event) {
        final IPayloadRegistrar registrar = event.registrar(BignayMod.MODID);

        registrar.play(SortPacket.ID, SortPacket::new, handler -> handler.server(SortPacket::handle));

        registrar.play(TransferPacket.ID, TransferPacket::new, handler -> handler.server(TransferPacket::handle));

        registrar.play(WithdrawPacket.ID, WithdrawPacket::new, handler -> handler.server(WithdrawPacket::handle));

        registrar.play(TotemActivationPacket.ID, TotemActivationPacket::new, handler -> handler.client(TotemActivationPacket::handle));
    }

    public static void sendToServer(CustomPacketPayload payload) {
        PacketDistributor.SERVER.noArg().send(payload);
    }

    public static void sendTotemActivation(ServerPlayer player, ItemStack stack) {
        if (player == null || stack == null || stack.isEmpty()) return;
        player.connection.send(new ClientboundCustomPayloadPacket(new TotemActivationPacket(stack)));
    }

    public record SortPacket(boolean isPlayerInventory) implements CustomPacketPayload {

        public static final ResourceLocation ID = ResourceLocation.tryBuild(BignayMod.MODID, "sort");

        public SortPacket(final FriendlyByteBuf buf) {
            this(buf.readBoolean());
        }

        @Override
        public void write(final FriendlyByteBuf buf) {
            buf.writeBoolean(isPlayerInventory);
        }

        @Override
        public ResourceLocation id() {
            return ID;
        }

        public static void handle(final SortPacket packet, final PlayPayloadContext context) {
            context.workHandler().submitAsync(() -> {
                context.player().ifPresent(player -> {
                    if (player.containerMenu != null) {
                        ContainersManager.sort(player.containerMenu, packet.isPlayerInventory);
                        player.containerMenu.sendAllDataToRemote();
                    }
                });
            });
        }
    }

    public record TransferPacket() implements CustomPacketPayload {

        public static final ResourceLocation ID = ResourceLocation.tryBuild(BignayMod.MODID, "transfer");

        public TransferPacket(final FriendlyByteBuf buf) {
            this();
        }

        @Override
        public void write(final FriendlyByteBuf buf) {
        }

        @Override
        public ResourceLocation id() {
            return ID;
        }

        public static void handle(final TransferPacket packet, final PlayPayloadContext context) {
            context.workHandler().submitAsync(() -> {
                context.player().ifPresent(player -> {
                    if (player.containerMenu != null) {
                        ContainersManager.transferToContainer(player.containerMenu);
                        player.containerMenu.sendAllDataToRemote();
                    }
                });
            });
        }
    }

    public record WithdrawPacket() implements CustomPacketPayload {

        public static final ResourceLocation ID = ResourceLocation.tryBuild(BignayMod.MODID, "withdraw");

        public WithdrawPacket(final FriendlyByteBuf buf) {
            this();
        }

        @Override
        public void write(final FriendlyByteBuf buf) {
        }

        @Override
        public ResourceLocation id() {
            return ID;
        }

        public static void handle(final WithdrawPacket packet, final PlayPayloadContext context) {
            context.workHandler().submitAsync(() -> {
                context.player().ifPresent(player -> {
                    if (player.containerMenu != null) {
                        ContainersManager.transferToPlayer(player.containerMenu);
                        player.containerMenu.sendAllDataToRemote();
                    }
                });
            });
        }
    }

    public record TotemActivationPacket(ItemStack stack) implements CustomPacketPayload {

        public TotemActivationPacket {
            stack = stack == null ? ItemStack.EMPTY : stack.copy();
        }

        public static final ResourceLocation ID = ResourceLocation.tryBuild(BignayMod.MODID, "totem_activation");

        public TotemActivationPacket(final FriendlyByteBuf buf) {
            this(buf.readItem());
        }

        @Override
        public void write(final FriendlyByteBuf buf) {
            buf.writeItem(stack);
        }

        @Override
        public ResourceLocation id() {
            return ID;
        }

        public static void handle(final TotemActivationPacket packet, final PlayPayloadContext context) {
            context.workHandler().submitAsync(() -> TotemClientHandler.play(packet.stack));
        }
    }
}
