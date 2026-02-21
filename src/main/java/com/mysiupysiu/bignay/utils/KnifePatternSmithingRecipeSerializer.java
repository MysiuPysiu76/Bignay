package com.mysiupysiu.bignay.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;

public class KnifePatternSmithingRecipeSerializer implements RecipeSerializer<KnifePatternSmithingRecipe> {

    public static final KnifePatternSmithingRecipeSerializer INSTANCE = new KnifePatternSmithingRecipeSerializer();

    @Override
    public KnifePatternSmithingRecipe fromJson(ResourceLocation id, JsonObject json) {
        if (!json.has("base") || !json.has("addition")) {
            throw new JsonParseException("KnifePatternSmithingRecipe requires 'base' and 'addition'");
        }
        Ingredient base = Ingredient.fromJson(json.getAsJsonObject("base"));
        Ingredient addition = Ingredient.fromJson(json.getAsJsonObject("addition"));
        return new KnifePatternSmithingRecipe(id, base, addition);
    }

    @Override
    public KnifePatternSmithingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
        Ingredient base = Ingredient.fromNetwork(buf);
        Ingredient addition = Ingredient.fromNetwork(buf);
        return new KnifePatternSmithingRecipe(id, base, addition);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, KnifePatternSmithingRecipe recipe) {
        recipe.getBase().toNetwork(buf);
        recipe.getAddition().toNetwork(buf);
    }
}
