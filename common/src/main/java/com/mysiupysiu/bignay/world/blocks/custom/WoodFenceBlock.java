package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.registry.core.RegistrySupplier;
import com.mysiupysiu.bignay.world.items.tabs.BuildingBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class WoodFenceBlock extends FenceBlock implements BuildingBlocks {

    public WoodFenceBlock(Block block) {
        super(BlockBehaviour.Properties.ofFullCopy(block).noOcclusion());
    }

    public WoodFenceBlock(RegistrySupplier<Block> block) {
        this(block.get());
    }

//    @Override
//    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
//        ItemStack itemInHand = player.getItemInHand(hand);
//
//        if (!(itemInHand.getItem() instanceof AxeItem)) {
//            return super.use(state, level, pos, player, hand, hit);
//        }
//
//        if (!level.isClientSide) {
//            ResourceLocation id = ForgeRegistries.BLOCKS.getKey(this);
//
//            if (id != null) {
//                Block targetBlock = null;
//
//                if (id.getPath().equals("bamboo_block_fence")) {
//                    targetBlock = Blocks.BAMBOO_FENCE;
//                } else {
//                    String path = id.getPath();
//                    String strippedPath = "stripped_" + path;
//
//                    ResourceLocation strippedId = new ResourceLocation(id.getNamespace(), strippedPath);
//                    targetBlock = ForgeRegistries.BLOCKS.getValue(strippedId);
//                }
//
//                if (targetBlock != null) {
//                    BlockState newState = targetBlock.defaultBlockState()
//                            .setValue(NORTH, state.getValue(NORTH))
//                            .setValue(SOUTH, state.getValue(SOUTH))
//                            .setValue(EAST, state.getValue(EAST))
//                            .setValue(WEST, state.getValue(WEST));
//
//                    level.setBlock(pos, newState, 3);
//                    level.playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
//
//                    itemInHand.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
//                    return InteractionResult.SUCCESS;
//                } else {
//                    return InteractionResult.PASS;
//                }
//            }
//        }
//
//        return InteractionResult.PASS;
//    }
}
