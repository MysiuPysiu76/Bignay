package com.mysiupysiu.bignay.utils;

import com.mysiupysiu.bignay.BignayMod;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.resources.ResourceLocation;

public class ModRecipeTypes {
    public static final RecipeType<?> KNIFE_PATTERN_SMITHING = RecipeType.register(new ResourceLocation(BignayMod.MODID, "knife_pattern_smithing").toString());
}
