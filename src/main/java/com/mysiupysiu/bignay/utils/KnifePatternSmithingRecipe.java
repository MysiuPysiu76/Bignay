package com.mysiupysiu.bignay.utils;

import com.mysiupysiu.bignay.items.PumpkinPatternItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class KnifePatternSmithingRecipe implements SmithingRecipe {

    private final ResourceLocation id;
    private final Ingredient base;
    private final Ingredient addition;

    public KnifePatternSmithingRecipe(ResourceLocation id, Ingredient base, Ingredient addition) {
        this.id = id;
        this.base = base;
        this.addition = addition;
    }

    @Override
    public boolean matches(Container p_44002_, Level p_44003_) {
        ItemStack baseStack = p_44002_.getItem(1);
        ItemStack addStack = p_44002_.getItem(2);
        return base.test(baseStack) && addition.test(addStack);
    }

    @Override
    public ItemStack assemble(Container p_44001_, RegistryAccess p_267165_) {
        ItemStack baseStack = p_44001_.getItem(1).copy();
        ItemStack addStack = p_44001_.getItem(2);

        if (baseStack.isEmpty() || addStack.isEmpty()) return ItemStack.EMPTY;

        ResourceLocation addId = BuiltInRegistries.ITEM.getKey(addStack.getItem());
        if (addId == null) return ItemStack.EMPTY;

        baseStack.getOrCreateTag().putString("bignay:pattern", PumpkinPatternItem.fromId(addId.getPath()).toString().toLowerCase());
        return baseStack;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public boolean isTemplateIngredient(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBaseIngredient(ItemStack stack) {
        return base.test(stack);
    }

    @Override
    public boolean isAdditionIngredient(ItemStack stack) {
        return addition.test(stack);
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializersInit.KNIFE_PATTERN_SMITHING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeType.SMITHING;
    }

    public Ingredient getBase() {
        return base;
    }

    public Ingredient getAddition() {
        return addition;
    }
}
