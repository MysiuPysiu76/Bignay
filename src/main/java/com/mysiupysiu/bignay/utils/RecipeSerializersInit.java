package com.mysiupysiu.bignay.utils;

import com.mysiupysiu.bignay.BignayMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeSerializersInit {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BignayMod.MODID);

    public static final RegistryObject<RecipeSerializer<?>> KNIFE_PATTERN_SMITHING = RECIPE_SERIALIZERS.register("knife_pattern_smithing", () -> KnifePatternSmithingRecipeSerializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
