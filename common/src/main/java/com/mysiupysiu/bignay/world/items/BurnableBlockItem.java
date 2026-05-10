package com.mysiupysiu.bignay.world.items;

import com.mysiupysiu.bignay.registry.BignayItems;
import com.mysiupysiu.bignay.utils.Fuels;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class BurnableBlockItem extends BlockItem {

    private final int burnTime;

    public BurnableBlockItem(Block block, int burnTime) {
        super(block, new Item.Properties());
        this.burnTime = burnTime;
        Fuels.FUELS_LIST.add(this);
        Fuels.FUELS_MAP.put(block.asItem(), burnTime);
    }

    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return getBurnTime();
    }

    public int getBurnTime() {
        return this.burnTime;
    }
}
