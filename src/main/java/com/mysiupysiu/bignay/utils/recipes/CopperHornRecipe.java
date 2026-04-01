package com.mysiupysiu.bignay.utils.recipes;

import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import com.mysiupysiu.bignay.items.ItemInit;

public class CopperHornRecipe extends CustomRecipe {

    public CopperHornRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        int hornSlot = -1;
        int hornCount = 0;
        int copperCount = 0;

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.is(Items.GOAT_HORN)) {
                    hornCount++;
                    hornSlot = i;
                } else if (stack.is(Items.COPPER_INGOT)) {
                    copperCount++;
                } else {
                    return false;
                }
            }
        }

        if (hornCount != 1 || copperCount != 3) {
            return false;
        }

        int width = container.getWidth();
        int x = hornSlot % width;
        int y = hornSlot / width;

        if (x == 0 || x == width - 1 || y == container.getHeight() - 1) {
            return false;
        }

        ItemStack left = container.getItem(hornSlot - 1);
        ItemStack right = container.getItem(hornSlot + 1);
        ItemStack bottom = container.getItem(hornSlot + width);

        return left.is(Items.COPPER_INGOT) && right.is(Items.COPPER_INGOT) && bottom.is(Items.COPPER_INGOT);
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        ItemStack goatHorn = ItemStack.EMPTY;

        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack stack = container.getItem(i);
            if (stack.is(Items.GOAT_HORN)) {
                goatHorn = stack;
                break;
            }
        }

        if (goatHorn.isEmpty()) return ItemStack.EMPTY;

        ItemStack result = new ItemStack(ItemInit.COPPER_HORN.get(), 1);
        CompoundTag tag = goatHorn.getTag();

        if (tag != null && tag.contains("instrument")) {
            result.getOrCreateTag().putString("instrument", getMappedInstrument(tag.getString("instrument")));
        }

        return result;
    }

    private String getMappedInstrument(String vanilla) {
        if (vanilla.contains("feel")) return "bignay:hymn_copper_horn";
        if (vanilla.contains("ponder")) return "bignay:water_copper_horn";
        if (vanilla.contains("dream")) return "bignay:fire_copper_horn";
        if (vanilla.contains("call")) return "bignay:urge_copper_horn";
        if (vanilla.contains("yearn") || vanilla.contains("yarn")) return "bignay:temper_copper_horn";
        if (vanilla.contains("sing") || vanilla.contains("sign")) return "bignay:nest_copper_horn";
        if (vanilla.contains("seek")) return "bignay:river_copper_horn";

        return "bignay:moon_copper_horn";
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 3 && height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializersInit.COPPER_HORN_CRAFTINH.get();
    }
}
