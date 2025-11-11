package com.mysiupysiu.bignay.blocks;

import com.mysiupysiu.bignay.utils.CreativeTabProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.EquipableCarvedPumpkinBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.List;

public class EquipablePumpkinBlock extends EquipableCarvedPumpkinBlock implements CreativeTabProvider {

    public EquipablePumpkinBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(1.0F).sound(SoundType.WOOD).isValidSpawn(EquipablePumpkinBlock::always).pushReaction(PushReaction.DESTROY));
    }

    public EquipablePumpkinBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public static class PalePumpkin extends EquipablePumpkinBlock {

        public PalePumpkin() {
            super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.0F).sound(SoundType.WOOD).isValidSpawn(EquipablePumpkinBlock::always).pushReaction(PushReaction.DESTROY));
        }
    }

    private static Boolean always(Object... o) {
        return true;
    }

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(CreativeModeTabs.NATURAL_BLOCKS);
    }
}
