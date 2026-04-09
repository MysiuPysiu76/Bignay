package com.mysiupysiu.bignay.world.blocks;

import com.mysiupysiu.bignay.world.items.tabs.CreativeTabProvider;
import com.mysiupysiu.bignay.world.items.tabs.NaturalBlocks;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.List;

public class JackOLantern extends CarvedPumpkinBlock implements NaturalBlocks {

    public JackOLantern() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(1.0F).sound(SoundType.WOOD).lightLevel(bs -> 15).isValidSpawn(JackOLantern::always).pushReaction(PushReaction.DESTROY));
    }

    public JackOLantern(BlockBehaviour.Properties properties) {
       super(properties);
    }

    public static class Soul extends JackOLantern {

        public Soul() {
            super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(1.0F).sound(SoundType.WOOD).lightLevel(bs -> 10).isValidSpawn(JackOLantern::always).pushReaction(PushReaction.DESTROY));
        }
    }

    public static class Pale extends JackOLantern {

        public Pale() {
            super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.0F).sound(SoundType.WOOD).lightLevel(bs -> 15).isValidSpawn(JackOLantern::always).pushReaction(PushReaction.DESTROY));
        }
    }

    public static class SoulPale extends JackOLantern {

        public SoulPale() {
            super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.0F).sound(SoundType.WOOD).lightLevel(bs -> 10).isValidSpawn(JackOLantern::always).pushReaction(PushReaction.DESTROY));
        }
    }

    private static Boolean always(Object... o) {
        return true;
    }
}
