package com.mysiupysiu.bignay.neoforge.loot;

import com.mojang.serialization.Codec;
import com.mysiupysiu.bignay.BignayMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class BignayLootModifiers {

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, BignayMod.MODID);

    public static final DeferredHolder<Codec<? extends IGlobalLootModifier>, Codec<AddItemModifier>> ADD_ITEM = LOOT_MODIFIER_SERIALIZERS.register("add_item", () -> AddItemModifier.CODEC);
    public static final DeferredHolder<Codec<? extends IGlobalLootModifier>, Codec<AddItemRangeModifier>> ADD_ITEM_RANGE = LOOT_MODIFIER_SERIALIZERS.register("add_item_range", () -> AddItemRangeModifier.CODEC);
    public static final DeferredHolder<Codec<? extends IGlobalLootModifier>, Codec<AddHornModifier>> ADD_COPPER_HORN = LOOT_MODIFIER_SERIALIZERS.register("add_copper_horn", () -> AddHornModifier.CODEC);

    public static void register(IEventBus eventBus) {
        LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }
}
