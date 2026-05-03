package com.mysiupysiu.bignay.world.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class KnifePatternSmithingRecipeSerializer implements RecipeSerializer<KnifePatternSmithingRecipe> {

    public static final KnifePatternSmithingRecipeSerializer INSTANCE = new KnifePatternSmithingRecipeSerializer();

    private final Codec<KnifePatternSmithingRecipe> codec = RecordCodecBuilder.create(instance ->
            instance.group(Ingredient.CODEC.fieldOf("base").forGetter(KnifePatternSmithingRecipe::getBase), Ingredient.CODEC.fieldOf("addition").forGetter(KnifePatternSmithingRecipe::getAddition)).apply(instance, KnifePatternSmithingRecipe::new));

    @Override
    public Codec<KnifePatternSmithingRecipe> codec() {
        return codec;
    }

    @Override
    public KnifePatternSmithingRecipe fromNetwork(FriendlyByteBuf buf) {
        Ingredient base = Ingredient.fromNetwork(buf);
        Ingredient addition = Ingredient.fromNetwork(buf);
        return new KnifePatternSmithingRecipe(base, addition);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, KnifePatternSmithingRecipe recipe) {
        recipe.getBase().toNetwork(buf);
        recipe.getAddition().toNetwork(buf);
    }
}
