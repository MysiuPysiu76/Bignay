package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.world.items.tabs.NaturalBlocks;
import net.minecraft.world.level.block.EquipableCarvedPumpkinBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class EquipablePumpkinBlock extends EquipableCarvedPumpkinBlock implements NaturalBlocks {

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
}
