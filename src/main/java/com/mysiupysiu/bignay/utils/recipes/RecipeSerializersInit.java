package com.mysiupysiu.bignay.utils.recipes;

import com.mysiupysiu.bignay.BignayMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeSerializersInit {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BignayMod.MODID);

    public static final RegistryObject<RecipeSerializer<?>> KNIFE_PATTERN_SMITHING = RECIPE_SERIALIZERS.register("knife_pattern_smithing", () -> KnifePatternSmithingRecipeSerializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<?>> COPPER_HORN_CRAFTINH = RECIPE_SERIALIZERS.register("copper_horn_crafting", () -> new SimpleCraftingRecipeSerializer<>(CopperHornRecipe::new));

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
