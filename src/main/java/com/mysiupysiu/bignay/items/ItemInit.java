package com.mysiupysiu.bignay.items;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.blocks.BlockInit;
import com.mysiupysiu.bignay.entities.GlowQuadItemFrameItem;
import com.mysiupysiu.bignay.entities.QuadItemFrameItem;
import net.minecraft.world.item.*;
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
    public static final RegistryObject<Item> TOTEM_OF_KEEPING_INVENTORY = registerItem("totem_of_keeping_inventory", TotemOfKeepingInventory::new);
    public static final RegistryObject<Item> RAVAGER_HIDE = registerItem("ravager_hide", DefaultItem::new);
    public static final RegistryObject<Item> QUAD_ITEM_FRAME = registerItem("quad_item_frame", () -> new QuadItemFrameItem(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> GLOW_QUAD_ITEM_FRAME = registerItem("glow_quad_item_frame", () -> new GlowQuadItemFrameItem(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> VERDANT_SIGN = registerItem("verdant_sign", () -> new SignItem(new Item.Properties().stacksTo(16), BlockInit.VERDANT_SIGN.get(), BlockInit.VERDANT_WALL_SIGN.get()));
    public static final RegistryObject<Item> VERDANT_HANGING_SIGN = registerItem("verdant_hanging_sign", () -> new SignItem(new Item.Properties().stacksTo(16), BlockInit.VERDANT_HANGING_SIGN.get(), BlockInit.VERDANT_WALL_HANGING_SIGN.get()));

    public static final RegistryObject<Item> PALE_PUMPKIN_SEEDS = registerItem("pale_pumpkin_seeds", () -> new ItemNameBlockItem(BlockInit.PALE_PUMPKIN_STEM.get(), new Item.Properties()));

    public static final RegistryObject<Item> ENDERMAN_HEAD = registerItem("enderman_head", () -> new HeadItem(BlockInit.ENDERMAN_HEAD.get(), BlockInit.ENDERMAN_WALL_HEAD.get()));
    public static final RegistryObject<Item> SPIDER_HEAD = registerItem("spider_head", () -> new HeadItem(BlockInit.SPIDER_HEAD.get(), BlockInit.SPIDER_WALL_HEAD.get()));

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
