package com.mysiupysiu.bignay.registry.init;

import com.mysiupysiu.bignay.registry.Registrar;
import com.mysiupysiu.bignay.registry.RegistrySupplier;
import com.mysiupysiu.bignay.world.recipes.CopperHornRecipe;
import com.mysiupysiu.bignay.world.recipes.KnifePatternSmithingRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

import java.util.function.Supplier;

public class BignayRecipes {

    public static final Registrar<RecipeSerializer<?>> RECIPES = new Registrar<>();

    public static final RegistrySupplier<RecipeSerializer<?>> KNIFE_PATTERN_SMITHING = recipe("knife_pattern_smithing", () -> KnifePatternSmithingRecipeSerializer.INSTANCE);
    public static final RegistrySupplier<RecipeSerializer<?>> COPPER_HORN_CRAFTING = recipe("copper_horn_crafting", () -> new SimpleCraftingRecipeSerializer<>(CopperHornRecipe::new));

    private static RegistrySupplier<RecipeSerializer<?>> recipe(String id, Supplier<RecipeSerializer<?>> recipe) {
        return RECIPES.register(id, recipe);
    }
}
