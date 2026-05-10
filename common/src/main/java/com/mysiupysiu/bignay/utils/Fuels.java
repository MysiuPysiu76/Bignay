package com.mysiupysiu.bignay.utils;

import com.mysiupysiu.bignay.world.items.BurnableBlockItem;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Fuels {

    public static final List<BurnableBlockItem> FUELS_LIST = new ArrayList<>();
    public static final Map<Item, Integer> FUELS_MAP = new Object2IntArrayMap<>();

    public static void register() {
        addLeaves();
    }

    private static void addLeaves() {
        FUELS_MAP.put(Items.ACACIA_LEAVES, 100);
        FUELS_MAP.put(Items.BIRCH_LEAVES, 100);
        FUELS_MAP.put(Items.CHERRY_LEAVES, 100);
        FUELS_MAP.put(Items.DARK_OAK_LEAVES, 100);
        FUELS_MAP.put(Items.JUNGLE_LEAVES, 100);
        FUELS_MAP.put(Items.MANGROVE_LEAVES, 100);
        FUELS_MAP.put(Items.OAK_LEAVES, 100);
        FUELS_MAP.put(Items.SPRUCE_LEAVES, 100);
    }
}
