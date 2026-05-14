package com.mysiupysiu.bignay.registry;

import com.mysiupysiu.bignay.registry.core.Registrar;
import com.mysiupysiu.bignay.registry.core.RegistrySupplier;
import com.mysiupysiu.bignay.world.items.*;
import com.mysiupysiu.bignay.world.items.frames.GlowQuadItemFrameItem;
import com.mysiupysiu.bignay.world.items.frames.QuadItemFrameItem;
import com.mysiupysiu.bignay.world.items.tabs.CreativeTabProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BignayItems {

    public static final Registrar<Item> ITEMS = new Registrar<>();

    public static final RegistrySupplier<Item> WAX = item("wax", WaxItem::new);
    public static final RegistrySupplier<Item> WITHER_BONE = item("wither_bone", IngredientsDefaultItem::new);
    public static final RegistrySupplier<Item> WITHER_BONE_MEAL = item("wither_bone_meal", WitherBoneMealItem::new);
    public static final RegistrySupplier<Item> POTTER_SHERD = item("pottery_sherd", IngredientsDefaultItem::new);
    public static final RegistrySupplier<Item> TOTEM_OF_KEEPING_INVENTORY = item("totem_of_keeping_inventory", TotemOfKeepingInventory::new);
    public static final RegistrySupplier<Item> RAVAGER_HIDE = item("ravager_hide", IngredientsDefaultItem::new);
    public static final RegistrySupplier<Item> KNIFE = item("knife", KnifeItem::new);
    public static final RegistrySupplier<Item> COPPER_HORN = item("copper_horn", CopperHorn::new);
    public static final RegistrySupplier<Item> GOLDEN_BERRIES = item("golden_berries", GoldenBerriesItem::new);
    public static final RegistrySupplier<Item> PALE_PUMPKIN_SEEDS = item("pale_pumpkin_seeds", () -> new SeedsItem(BignayBlocks.PALE_PUMPKIN_STEM));

    public static final RegistrySupplier<Item> VERDANT_SIGN = item("verdant_sign", () -> new SignsItem(BignayBlocks.VERDANT_SIGN, BignayBlocks.VERDANT_WALL_SIGN));
    public static final RegistrySupplier<Item> VERDANT_HANGING_SIGN = item("verdant_hanging_sign", () -> new SignsItem(BignayBlocks.VERDANT_HANGING_SIGN, BignayBlocks.VERDANT_WALL_HANGING_SIGN));

    public static final RegistrySupplier<Item> QUAD_ITEM_FRAME = item("quad_item_frame", () -> new QuadItemFrameItem(new Item.Properties().stacksTo(64)));
    public static final RegistrySupplier<Item> GLOW_QUAD_ITEM_FRAME = item("glow_quad_item_frame", () -> new GlowQuadItemFrameItem(new Item.Properties().stacksTo(64)));

    public static final RegistrySupplier<Item> ENDERMAN_HEAD = item("enderman_head", () -> new HeadItem(BignayBlocks.ENDERMAN_HEAD, BignayBlocks.ENDERMAN_WALL_HEAD));
    public static final RegistrySupplier<Item> SPIDER_HEAD = item("spider_head", () -> new HeadItem(BignayBlocks.SPIDER_HEAD, BignayBlocks.SPIDER_WALL_HEAD));
    public static final RegistrySupplier<Item> BLAZE_HEAD = item("blaze_head", () -> new HeadItem(BignayBlocks.BLAZE_HEAD, BignayBlocks.BLAZE_WALL_HEAD));
    public static final RegistrySupplier<Item> HUSK_HEAD = item("husk_head", () -> new HeadItem(BignayBlocks.HUSK_HEAD, BignayBlocks.HUSK_WALL_HEAD));
    public static final RegistrySupplier<Item> SLIME_HEAD = item("slime_head", () -> new HeadItem(BignayBlocks.SLIME_HEAD, BignayBlocks.SLIME_WALL_HEAD));
    public static final RegistrySupplier<Item> CAVE_SPIDER_HEAD = item("cave_spider_head", () -> new HeadItem(BignayBlocks.CAVE_SPIDER_HEAD, BignayBlocks.CAVE_SPIDER_WALL_HEAD));
    public static final RegistrySupplier<Item> MAGMA_CUBE_HEAD = item("magma_cube_head", () -> new HeadItem(BignayBlocks.MAGMA_CUBE_HEAD, BignayBlocks.MAGMA_CUBE_WALL_HEAD));
    public static final RegistrySupplier<Item> DROWNED_HEAD = item("drowned_head", () -> new HeadItem(BignayBlocks.DROWNED_HEAD, BignayBlocks.DROWNED_WALL_HEAD));
    public static final RegistrySupplier<Item> STRAY_SKULL = item("stray_skull", () -> new HeadItem(BignayBlocks.STRAY_SKULL, BignayBlocks.STRAY_WALL_SKULL));

    public static final RegistrySupplier<Item> ANGRY_PUMPKIN_PATTERN = item("angry_pumpkin_pattern", () -> new PumpkinPatternItem(PumpkinPatternItem.Type.ANGRY));
    public static final RegistrySupplier<Item> ANXIOUS_PUMPKIN_PATTERN = item("anxious_pumpkin_pattern", () -> new PumpkinPatternItem(PumpkinPatternItem.Type.ANXIOUS));
    public static final RegistrySupplier<Item> CREEPER_PUMPKIN_PATTERN = item("creeper_pumpkin_pattern", () -> new PumpkinPatternItem(PumpkinPatternItem.Type.CREEPER));
    public static final RegistrySupplier<Item> DERPY_PUMPKIN_PATTERN = item("derpy_pumpkin_pattern", () -> new PumpkinPatternItem(PumpkinPatternItem.Type.DERPY));
    public static final RegistrySupplier<Item> GHOST_PUMPKIN_PATTERN = item("ghost_pumpkin_pattern", () -> new PumpkinPatternItem(PumpkinPatternItem.Type.GHOST));
    public static final RegistrySupplier<Item> HAPPY_PUMPKIN_PATTERN = item("happy_pumpkin_pattern", () -> new PumpkinPatternItem(PumpkinPatternItem.Type.HAPPY));
    public static final RegistrySupplier<Item> SKULL_PUMPKIN_PATTERN = item("skull_pumpkin_pattern", () -> new PumpkinPatternItem(PumpkinPatternItem.Type.SKULL));

    private static RegistrySupplier<Item> item(String id, Supplier<Item> item) {
        return ITEMS.register(id, item);
    }

    public static void addItemsToTabs(ResourceKey<CreativeModeTab> tabKey, CreativeModeTab.Output output) {
        ITEMS.getEntries().forEach(entry -> {
            Item item = entry.get();
            CreativeTabProvider provider = null;

            if (item instanceof CreativeTabProvider p) {
                provider = p;
            } else if (item instanceof BlockItem blockItem && blockItem.getBlock() instanceof CreativeTabProvider p) {
                provider = p;
            }

            if (provider != null && provider.getCreativeTabs().contains(tabKey)) {
                output.accept(item);
            }
        });
    }
}
