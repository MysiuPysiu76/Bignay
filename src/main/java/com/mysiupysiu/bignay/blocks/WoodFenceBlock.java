package com.mysiupysiu.bignay.blocks;

import com.mysiupysiu.bignay.utils.CreativeTabProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class WoodFenceBlock extends FenceBlock implements CreativeTabProvider {

    public WoodFenceBlock(Block block) {
        super(BlockBehaviour.Properties.copy((FenceBlock) block));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemInHand = player.getItemInHand(hand);

        if (!(itemInHand.getItem() instanceof AxeItem)) {
            return super.use(state, level, pos, player, hand, hit);
        }

        if (!level.isClientSide) {
            ResourceLocation id = ForgeRegistries.BLOCKS.getKey(this);

            if (id != null) {
                Block targetBlock = null;

                if (id.getPath().equals("bamboo_block_fence")) {
                    targetBlock = Blocks.BAMBOO_FENCE;
                } else {
                    String path = id.getPath();
                    String strippedPath = "stripped_" + path;

                    ResourceLocation strippedId = new ResourceLocation(id.getNamespace(), strippedPath);
                    targetBlock = ForgeRegistries.BLOCKS.getValue(strippedId);
                }

                if (targetBlock != null) {
                    BlockState newState = targetBlock.defaultBlockState()
                            .setValue(NORTH, state.getValue(NORTH))
                            .setValue(SOUTH, state.getValue(SOUTH))
                            .setValue(EAST, state.getValue(EAST))
                            .setValue(WEST, state.getValue(WEST));

                    level.setBlock(pos, newState, 3);
                    level.playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);

                    itemInHand.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
                    return InteractionResult.SUCCESS;
                } else {
                    return InteractionResult.PASS;
                }
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(CreativeModeTabs.BUILDING_BLOCKS);
    }
}
