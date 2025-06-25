package com.mysiupysiu.bignay.util;

import com.mysiupysiu.bignay.BignayMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@Mod.EventBusSubscriber(modid = BignayMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DefaultCopperWaxingBlockerEvent {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        ItemStack stack = event.getItemStack();

        BlockState state = level.getBlockState(pos);

        if (stack.getItem() == Items.HONEYCOMB && state.getBlock().getName().getString().toLowerCase().contains("copper")) {
            event.setCanceled(true);
        }
    }
}
