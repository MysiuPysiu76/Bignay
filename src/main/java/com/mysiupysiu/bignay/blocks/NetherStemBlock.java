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
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class NetherStemBlock extends RotatedPillarBlock implements CreativeTabProvider {

    public NetherStemBlock(MapColor color) {
        super(BlockBehaviour.Properties.of().mapColor(color).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.STEM));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.getItem() instanceof AxeItem) {
            ResourceLocation key = ForgeRegistries.BLOCKS.getKey(state.getBlock());
            if (key != null && !key.getPath().contains("stripped")) {
                String newName = "stripped_" + key.getPath();
                Block stripped = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(key.getNamespace(), newName));
                if (stripped != null && stripped != Blocks.AIR) {
                    BlockState ns = stripped.defaultBlockState().setValue(AXIS, state.getValue(AXIS));
                    level.setBlock(pos, ns, 11);
                    level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1f, 1f);
                    if (!level.isClientSide()) stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                    return InteractionResult.sidedSuccess(level.isClientSide());
                }
            }
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(CreativeModeTabs.BUILDING_BLOCKS);
    }
}
