package com.mysiupysiu.bignay.world.blocks.custom;

import com.google.common.collect.ImmutableMap;
import com.mysiupysiu.bignay.registry.BignayBlocks;
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
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class NetherStemBlock extends RotatedPillarBlock implements BuildingBlocks {

    public static final Supplier<Map<Block, Block>> STEMS = NetherStemBlock::getStrippableStems;

    public NetherStemBlock(MapColor color) {
        super(BlockBehaviour.Properties.of().mapColor(color).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.STEM));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (!(stack.getItem() instanceof AxeItem)) {
            return super.use(state, level, pos, player, hand, hit);
        }

        return getStripped(state).map(strippedState -> {
            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!level.isClientSide) {
                level.setBlock(pos, strippedState, 11);
                stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }).orElse(super.use(state, level, pos, player, hand, hit));
    }

    public static Optional<BlockState> getStripped(BlockState state) {
        return Optional.ofNullable(STEMS.get().get(state.getBlock())).map(block -> block.withPropertiesOf(state));
    }

    public static Map<Block, Block> getStrippableStems() {
        return ImmutableMap.<Block, Block>builder().put(BignayBlocks.VERDANT_STEM.get(), BignayBlocks.STRIPPED_VERDANT_STEM.get()).put(BignayBlocks.VERDANT_HYPHAE.get(), BignayBlocks.STRIPPED_VERDANT_HYPHAE.get()).build();
    }
}
