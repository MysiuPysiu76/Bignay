package com.mysiupysiu.bignay.mixin;

import com.mysiupysiu.bignay.blocks.WeatheringCopperVerticalSlabBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin {

    @Inject(method = "useOn", at = @At("TAIL"), cancellable = true)
    private void bignay$onAxeUse(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        String path = id.getPath();
        String namespace = id.getNamespace();

        BlockState newState = null;
        int particleEvent = -1;

        if (path.startsWith("waxed_")) {
            String unwaxedPath = path.substring(6);
            Block unwaxedBlock = BuiltInRegistries.BLOCK.get(new ResourceLocation(namespace, unwaxedPath));

            if (unwaxedBlock != Blocks.AIR) {
                newState = bignay$copyProperties(state, unwaxedBlock.defaultBlockState());
                particleEvent = 3004;
            }
        } else if (state.getBlock() instanceof WeatheringCopperVerticalSlabBlock) {
            String previousPath = bignay$getPreviousStage(path);
            if (previousPath != null) {
                Block previousBlock = BuiltInRegistries.BLOCK.get(new ResourceLocation(namespace, previousPath));
                if (previousBlock != Blocks.AIR) {
                    newState = bignay$copyProperties(state, previousBlock.defaultBlockState());
                    particleEvent = 3005;
                }
            }
        }

        if (newState != null) {
            if (particleEvent == 3004) {
                level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0f, 1.0f);
            } else {
                level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);
            }

            level.levelEvent(player, particleEvent, pos, 0);
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);

            if (!level.isClientSide) {
                level.setBlock(pos, newState, Block.UPDATE_ALL_IMMEDIATE);
                if (player != null) {
                    stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(context.getHand()));
                }
            }

            cir.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide));
        }
    }

    @Unique
    private String bignay$getPreviousStage(String currentPath) {
        return switch (currentPath) {
            case "oxidized_cut_copper_vertical_slab" -> "weathered_cut_copper_vertical_slab";
            case "weathered_cut_copper_vertical_slab" -> "exposed_cut_copper_vertical_slab";
            case "exposed_cut_copper_vertical_slab" -> "cut_copper_vertical_slab";
            default -> null;
        };
    }

    @Unique
    private BlockState bignay$copyProperties(BlockState from, BlockState to) {
        for (Property<?> prop : from.getProperties()) {
            if (to.hasProperty(prop)) {
                to = bignay$copyPropertySafe(from, to, prop);
            }
        }
        return to;
    }

    @Unique
    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> BlockState bignay$copyPropertySafe(BlockState from, BlockState to, Property<?> prop) {
        return to.setValue((Property<T>) prop, from.getValue((Property<T>) prop));
    }
}
