package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.world.items.tabs.BuildingBlocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class NetherStemBlock extends RotatedPillarBlock implements BuildingBlocks {

    public NetherStemBlock(MapColor color) {
        super(BlockBehaviour.Properties.of().mapColor(color).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.STEM));
    }

//    @Override
//    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
//        ItemStack stack = player.getItemInHand(hand);
//
//        if (stack.getItem() instanceof AxeItem) {
//            ResourceLocation key = ForgeRegistries.BLOCKS.getKey(state.getBlock());
//            if (key != null && !key.getPath().contains("stripped")) {
//                String newName = "stripped_" + key.getPath();
//                Block stripped = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(key.getNamespace(), newName));
//                if (stripped != null && stripped != Blocks.AIR) {
//                    BlockState ns = stripped.defaultBlockState().setValue(AXIS, state.getValue(AXIS));
//                    level.setBlock(pos, ns, 11);
//                    level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1f, 1f);
//                    if (!level.isClientSide()) stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
//                    return InteractionResult.sidedSuccess(level.isClientSide());
//                }
//            }
//        }
//
//        return super.use(state, level, pos, player, hand, hit);
//    }
}
