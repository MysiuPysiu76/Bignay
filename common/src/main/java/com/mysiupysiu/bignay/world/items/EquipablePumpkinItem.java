package com.mysiupysiu.bignay.world.items;

import com.mysiupysiu.bignay.registry.RegistrySupplier;
import com.mysiupysiu.bignay.world.items.tabs.NaturalBlocks;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class EquipablePumpkinItem extends BlockItem implements NaturalBlocks {

    public EquipablePumpkinItem(Block block) {
        super(block, new Properties());
    }

    public EquipablePumpkinItem(RegistrySupplier<Block> block) {
        this(block.get());
    }

    public boolean isEnderMask(ItemStack stack, Player player, EnderMan endermanEntity) {
        return true;
    }
}
