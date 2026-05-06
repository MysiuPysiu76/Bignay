package com.mysiupysiu.bignay.world.items;

import com.mysiupysiu.bignay.registry.BignayItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class BurnableBlockItem extends BlockItem {

    private final int burnTime;

    public BurnableBlockItem(Block block, Properties properties, int burnTime) {
        super(block, properties);
        this.burnTime = burnTime;
        BignayItems.FUELS.add(this);
    }

    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return getBurnTime();
    }

    public int getBurnTime() {
        return this.burnTime;
    }
}
