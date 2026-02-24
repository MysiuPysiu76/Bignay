package com.mysiupysiu.bignay.events;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.blocks.WeatheringCopperVerticalSlabBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = BignayMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CopperAxeInteractionEvent {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();

        if (!(stack.getItem() instanceof AxeItem)) return;

        ResourceLocation id = ForgeRegistries.BLOCKS.getKey(state.getBlock());
        if (id == null) return;
        String namespace = id.getNamespace();
        String path = id.getPath();

        BlockState newState = null;
        boolean isWaxOff = false;
        boolean isScrape = false;

        if (path.startsWith("waxed_")) {
            String unwaxedPath = path.substring(6);
            Block unwaxedBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(namespace, unwaxedPath));

            if (unwaxedBlock != null && unwaxedBlock != net.minecraft.world.level.block.Blocks.AIR) {
                newState = copyProperties(state, unwaxedBlock.defaultBlockState());
                isWaxOff = true;
            }
        }
        else if (state.getBlock() instanceof WeatheringCopperVerticalSlabBlock) {
            String previousPath = getPreviousStage(path);

            if (previousPath != null) {
                Block previousBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(namespace, previousPath));
                if (previousBlock != null && previousBlock != net.minecraft.world.level.block.Blocks.AIR) {
                    newState = copyProperties(state, previousBlock.defaultBlockState());
                    isScrape = true;
                }
            }
        }

        if (newState != null) {
            if (isWaxOff) {
                level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.levelEvent(player, 3004, pos, 0);
            } else if (isScrape) {
                level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.levelEvent(player, 3005, pos, 0);
            }

            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);

            if (!level.isClientSide()) {
                level.setBlock(pos, newState, Block.UPDATE_ALL_IMMEDIATE);
                stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(event.getHand()));
            }

            event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            event.setCanceled(true);
        }
    }

    private static String getPreviousStage(String currentPath) {
        return switch (currentPath) {
            case "oxidized_cut_copper_vertical_slab" -> "weathered_cut_copper_vertical_slab";
            case "weathered_cut_copper_vertical_slab" -> "exposed_cut_copper_vertical_slab";
            case "exposed_cut_copper_vertical_slab" -> "cut_copper_vertical_slab";
            default -> null;
        };
    }

    private static BlockState copyProperties(BlockState from, BlockState to) {
        for (Property<?> prop : from.getProperties()) {
            if (to.hasProperty(prop)) {
                to = copyPropertySafe(from, to, prop);
            }
        }
        return to;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> BlockState copyPropertySafe(BlockState from, BlockState to, Property<?> prop) {
        Property<T> p = (Property<T>) prop;
        return to.setValue(p, from.getValue(p));
    }
}
