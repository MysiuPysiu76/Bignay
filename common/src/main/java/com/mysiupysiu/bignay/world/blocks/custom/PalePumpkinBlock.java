package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.registry.BignayBlocks;
import com.mysiupysiu.bignay.world.items.tabs.NaturalBlocks;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.StemGrownBlock;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class PalePumpkinBlock extends StemGrownBlock implements NaturalBlocks {

    public PalePumpkinBlock(MapColor color) {
        super(Properties.of().mapColor(color).instrument(NoteBlockInstrument.DIDGERIDOO).strength(1.0F).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));
    }

    @Override
    public StemBlock getStem() {
        return (StemBlock) BignayBlocks.PALE_PUMPKIN_STEM.get();
    }

    @Override
    public AttachedStemBlock getAttachedStem() {
        return (AttachedStemBlock) BignayBlocks.ATTACHED_PALE_PUMPKIN_STEM.get();
    }
}

//    public InteractionResult use(BlockState p_55289_, Level p_55290_, BlockPos p_55291_, Player p_55292_, InteractionHand p_55293_, BlockHitResult p_55294_) {
//        ItemStack itemstack = p_55292_.getItemInHand(p_55293_);
//        if (itemstack.canPerformAction(net.minecraftforge.common.ToolActions.SHEARS_CARVE)) {
//            if (!p_55290_.isClientSide) {
//                Direction direction = p_55294_.getDirection();
//                Direction direction1 = direction.getAxis() == Direction.Axis.Y ? p_55292_.getDirection().getOpposite() : direction;
//                p_55290_.playSound(null, p_55291_, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F);
//                p_55290_.setBlock(p_55291_, BlockInit.CARVED_PALE_PUMPKIN.get().defaultBlockState().setValue(CarvedPumpkinBlock.FACING, direction1), 11);
//                itemstack.hurtAndBreak(1, p_55292_, (p_55287_) -> {
//                    p_55287_.broadcastBreakEvent(p_55293_);
//                });
//                ItemEntity itementity = new ItemEntity(p_55290_, (double)p_55291_.getX() + 0.5D + (double)direction1.getStepX() * 0.65D, (double)p_55291_.getY() + 0.1D, (double)p_55291_.getZ() + 0.5D + (double)direction1.getStepZ() * 0.65D, new ItemStack(ItemInit.PALE_PUMPKIN_SEEDS.get(), 4));
//                itementity.setDeltaMovement(0.05D * (double)direction1.getStepX() + p_55290_.random.nextDouble() * 0.02D, 0.05D, 0.05D * (double)direction1.getStepZ() + p_55290_.random.nextDouble() * 0.02D);
//                p_55290_.addFreshEntity(itementity);
//                p_55290_.gameEvent(p_55292_, GameEvent.SHEAR, p_55291_);
//                p_55292_.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
//            }
//
//            return InteractionResult.sidedSuccess(p_55290_.isClientSide);
//        } else {
//            return super.use(p_55289_, p_55290_, p_55291_, p_55292_, p_55293_, p_55294_);
//        }
//    }
//
//    @Override
//    public StemBlock getStem() {
//        return (StemBlock) BlockInit.PALE_PUMPKIN_STEM.get();
//    }
//
//    @Override
//    public AttachedStemBlock getAttachedStem() {
//        return (AttachedStemBlock) BlockInit.ATTACHED_PALE_PUMPKIN_STEM.get();
//    }
//}
