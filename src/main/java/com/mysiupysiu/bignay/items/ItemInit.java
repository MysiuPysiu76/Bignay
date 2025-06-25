package com.mysiupysiu.bignay.items;

import com.mysiupysiu.bignay.BignayMod;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BignayMod.MODID);

    private static final List<RegistryObject<Item>> INGREDIENTS_TAB_ITEMS = new ArrayList<>();

    public static final RegistryObject<Item> WAX = registerItem("wax", WaxItem::new);
    public static final RegistryObject<Item> WITHER_BONE = registerItem("wither_bone", DefaultItem::new);
    public static final RegistryObject<Item> WITHER_BONE_MEAL = registerItem("wither_bone_meal", WitherBoneMealItem::new);
    public static final RegistryObject<Item> POTTER_SHERD = registerItem("pottery_sherd", DefaultItem::new);

    private static RegistryObject<Item> registerItem(String name, Supplier<Item> supplier) {
        RegistryObject<Item> item = ITEMS.register(name, supplier);
        INGREDIENTS_TAB_ITEMS.add(item);
        return item;
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.INGREDIENTS)) {
            INGREDIENTS_TAB_ITEMS.forEach(event::accept);
        }
    }
}
