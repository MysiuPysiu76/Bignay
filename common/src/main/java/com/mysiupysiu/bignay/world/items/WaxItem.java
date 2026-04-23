package com.mysiupysiu.bignay.world.items;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.mysiupysiu.bignay.registry.init.BignayBlocks;
import com.mysiupysiu.bignay.world.items.tabs.Ingredients;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Optional;
import java.util.function.Supplier;

public class WaxItem extends HoneycombItem implements Ingredients {

    public static final Supplier<BiMap<Block, Block>> WAXABLES = WaxItem::getCopperToWaxedBlocks;

    public WaxItem() {
        super(new Properties());
    }

    // By Mojang Studios form net.minecraft.world.item.HoneycombItem
    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        BlockPos blockPos = useOnContext.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        Player player = useOnContext.getPlayer();
        ItemStack itemStack = useOnContext.getItemInHand();

        if (level.getBlockEntity(blockPos) instanceof SignBlockEntity signBlockEntity) {
            if (this.tryApplyToSign(level, signBlockEntity, player)) {
                if (player instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, blockPos, itemStack);
                }
                itemStack.shrink(1);

                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }

        return getWaxed(blockState).map((blockStatex) -> {
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStack);
            }

            itemStack.shrink(1);
            level.setBlock(blockPos, blockStatex, 11);
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, blockStatex));
            level.levelEvent(player, 3003, blockPos, 0);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }).orElse(InteractionResult.PASS);
    }

    public static Optional<BlockState> getWaxed(BlockState blockState) {
        return Optional.ofNullable(WAXABLES.get().get(blockState.getBlock())).map((block) -> block.withPropertiesOf(blockState));
    }

    public boolean tryApplyToSign(Level level, SignBlockEntity signBlockEntity, Player player) {
        if (signBlockEntity.setWaxed(true)) {
            level.levelEvent(null, 3003, signBlockEntity.getBlockPos(), 0);
            return true;
        } else {
            return false;
        }
    }

    public static BiMap<Block, Block> getCopperToWaxedBlocks() {
        return ImmutableBiMap.<Block, Block>builder()
                .put(Blocks.COPPER_BLOCK, Blocks.WAXED_COPPER_BLOCK)
                .put(Blocks.CUT_COPPER, Blocks.WAXED_CUT_COPPER)
                .put(Blocks.CUT_COPPER_SLAB, Blocks.WAXED_CUT_COPPER_SLAB)
                .put(Blocks.CUT_COPPER_STAIRS, Blocks.WAXED_CUT_COPPER_STAIRS)
                .put(BignayBlocks.CUT_COPPER_VERTICAL_SLAB.get(), BignayBlocks.WAXED_CUT_COPPER_VERTICAL_SLAB.get())
                .put(Blocks.EXPOSED_COPPER, Blocks.WAXED_EXPOSED_COPPER)
                .put(Blocks.EXPOSED_CUT_COPPER, Blocks.WAXED_EXPOSED_CUT_COPPER)
                .put(Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB)
                .put(Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS)
                .put(BignayBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB.get(), BignayBlocks.WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB.get())
                .put(Blocks.WEATHERED_COPPER, Blocks.WAXED_WEATHERED_COPPER)
                .put(Blocks.WEATHERED_CUT_COPPER, Blocks.WAXED_WEATHERED_CUT_COPPER)
                .put(Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB)
                .put(Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS)
                .put(BignayBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB.get(), BignayBlocks.WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB.get())
                .put(Blocks.OXIDIZED_COPPER, Blocks.WAXED_OXIDIZED_COPPER)
                .put(Blocks.OXIDIZED_CUT_COPPER, Blocks.WAXED_OXIDIZED_CUT_COPPER)
                .put(Blocks.OXIDIZED_CUT_COPPER_SLAB, Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB)
                .put(Blocks.OXIDIZED_CUT_COPPER_STAIRS, Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS)
                .put(BignayBlocks.OXIDIZED_CUT_COPPER_VERTICAL_SLAB.get(), BignayBlocks.WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB.get())
                .build();
    }
}
