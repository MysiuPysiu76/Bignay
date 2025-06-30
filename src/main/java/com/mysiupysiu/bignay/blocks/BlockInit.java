package com.mysiupysiu.bignay.blocks;

import com.mysiupysiu.bignay.BignayMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BignayMod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BignayMod.MODID);

    private static final List<RegistryObject<Item>> NATURAL_TAB_ITEMS = new ArrayList<>();

    public static final RegistryObject<Block> WAX_BLOCK = registerBlock("wax_block", WaxBlock::new);
    public static final RegistryObject<Block> WITHER_BONE_BLOCK = registerBlock("wither_bone_block", WitherBoneBlock::new);
    public static final RegistryObject<Block> ARCHAEOLOGY_TABLE_BLOCK = registerBlock("archaeology_table", ArchaeologyTableBlock::new);

    public static final RegistryObject<Block> ACACIA_CAMPFIRE = registerBlock("acacia_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE)));
    public static final RegistryObject<Block> BIRCH_CAMPFIRE = registerBlock("birch_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE)));
    public static final RegistryObject<Block> CHERRY_CAMPFIRE = registerBlock("cherry_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE)));
    public static final RegistryObject<Block> CRIMSON_CAMPFIRE = registerBlock("crimson_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE)));
    public static final RegistryObject<Block> DARK_OAK_CAMPFIRE = registerBlock("dark_oak_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE)));
    public static final RegistryObject<Block> JUNGLE_CAMPFIRE = registerBlock("jungle_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE)));
    public static final RegistryObject<Block> MANGROVE_CAMPFIRE = registerBlock("mangrove_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE)));
    public static final RegistryObject<Block> OAK_CAMPFIRE = registerBlock("oak_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE)));
    public static final RegistryObject<Block> SPRUCE_CAMPFIRE = registerBlock("spruce_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE)));
    public static final RegistryObject<Block> WARPED_CAMPFIRE = registerBlock("warped_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE)));

    private static RegistryObject<Block> registerBlock(String name, Supplier<Block> blockSupplier) {
        RegistryObject<Block> block = BLOCKS.register(name, blockSupplier);
        RegistryObject<Item> blockItem = ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties()));
        NATURAL_TAB_ITEMS.add(blockItem);
        return block;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.NATURAL_BLOCKS)) {
            NATURAL_TAB_ITEMS.forEach(event::accept);
        }
    }
}
