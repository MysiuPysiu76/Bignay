package com.mysiupysiu.bignay.fabric;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.fabric.network.BignayPacketHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;

public class BignayFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BignayMod.init();

        FabricRegistry.register();
        FabricRegistry.addToCreativeTab();

        Commands.register();
        Fuels.register();

        BignayPacketHandler.register();




    }


}
