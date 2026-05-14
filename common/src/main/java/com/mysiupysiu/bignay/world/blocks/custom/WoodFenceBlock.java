package com.mysiupysiu.bignay.world.blocks.custom;

import com.google.common.collect.ImmutableMap;
import com.mysiupysiu.bignay.registry.BignayBlocks;
import com.mysiupysiu.bignay.registry.core.RegistrySupplier;
import com.mysiupysiu.bignay.world.items.tabs.BuildingBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class WoodFenceBlock extends FenceBlock implements BuildingBlocks {

    public static final Supplier<Map<Block, Block>> STRIPPABLE = WoodFenceBlock::getStrippableFences;

    public WoodFenceBlock(Block block) {
        super(BlockBehaviour.Properties.ofFullCopy(block).noOcclusion());
    }

    public WoodFenceBlock(RegistrySupplier<Block> block) {
        this(block.get());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemInHand = player.getItemInHand(hand);

        if (!(itemInHand.getItem() instanceof AxeItem)) {
            return super.use(state, level, pos, player, hand, hit);
        }

        return getStripped(state).map(strippedState -> {
            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);

            if (!level.isClientSide) {
                level.setBlock(pos, strippedState, 11);
                itemInHand.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }).orElse(InteractionResult.PASS);
    }

    public static Optional<BlockState> getStripped(BlockState state) {
        return Optional.ofNullable(STRIPPABLE.get().get(state.getBlock())).map(block -> block.withPropertiesOf(state));
    }

    public static Map<Block, Block> getStrippableFences() {
        return ImmutableMap.<Block, Block>builder()
                .put(BignayBlocks.ACACIA_WOOD_FENCE.get(), BignayBlocks.STRIPPED_ACACIA_WOOD_FENCE.get())
                .put(BignayBlocks.BIRCH_WOOD_FENCE.get(), BignayBlocks.STRIPPED_BIRCH_WOOD_FENCE.get())
                .put(BignayBlocks.CHERRY_WOOD_FENCE.get(), BignayBlocks.STRIPPED_CHERRY_WOOD_FENCE.get())
                .put(BignayBlocks.CRIMSON_HYPHAE_FENCE.get(), BignayBlocks.STRIPPED_CRIMSON_HYPHAE_FENCE.get())
                .put(BignayBlocks.DARK_OAK_WOOD_FENCE.get(), BignayBlocks.STRIPPED_DARK_OAK_WOOD_FENCE.get())
                .put(BignayBlocks.JUNGLE_WOOD_FENCE.get(), BignayBlocks.STRIPPED_JUNGLE_WOOD_FENCE.get())
                .put(BignayBlocks.MANGROVE_WOOD_FENCE.get(), BignayBlocks.STRIPPED_MANGROVE_WOOD_FENCE.get())
                .put(BignayBlocks.SPRUCE_WOOD_FENCE.get(), BignayBlocks.STRIPPED_SPRUCE_WOOD_FENCE.get())
                .put(BignayBlocks.OAK_WOOD_FENCE.get(), BignayBlocks.STRIPPED_OAK_WOOD_FENCE.get())
                .put(BignayBlocks.VERDANT_HYPHAE_FENCE.get(), BignayBlocks.STRIPPED_VERDANT_HYPHAE_FENCE.get())
                .put(BignayBlocks.WARPED_HYPHAE_FENCE.get(), BignayBlocks.STRIPPED_WARPED_HYPHAE_FENCE.get())
                .build();
    }
}
