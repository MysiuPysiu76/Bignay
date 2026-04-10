package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.registry.init.BignayParticles;
import com.mysiupysiu.bignay.world.tags.BignayTags;
import com.mysiupysiu.bignay.world.items.tabs.BignayTabs;
import com.mysiupysiu.bignay.world.items.tabs.CreativeTabProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.ToIntFunction;

public class CandlesBlock extends CandleBlock implements CreativeTabProvider {

    public static final ToIntFunction<BlockState> LIGHT_EMISSION = (bs) -> bs.getValue(LIT) ? 2 * bs.getValue(CANDLES) : 0;

    public CandlesBlock(MapColor color) {
        super(BlockBehaviour.Properties.of()
                .mapColor(color)
                .noOcclusion()
                .strength(0.1F)
                .sound(SoundType.CANDLE)
                .lightLevel(LIGHT_EMISSION)
                .pushReaction(PushReaction.DESTROY));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!state.getValue(LIT) && itemstack.is(BignayTags.Items.FIRE_PROVIDERS)) {
            level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
            level.setBlock(pos, state.setValue(LIT, true), 11);

            if (!player.getAbilities().instabuild) {
                if (itemstack.is(Items.FLINT_AND_STEEL)) {
                    itemstack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
                } else {
                    itemstack.shrink(1);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public void animateTick(BlockState bs, Level level, BlockPos pos, RandomSource random) {
        if (bs.getValue(LIT)) {
            this.getParticleOffsets(bs).forEach((p_220695_) ->
                addParticlesAndSound(level, p_220695_.add(pos.getX(), pos.getY(), pos.getZ()), random));
        }
    }

    private void addParticlesAndSound(Level level, Vec3 vec3, RandomSource randomSource) {
        float f = randomSource.nextFloat();
        if (f < 0.3F) {
            level.addParticle(ParticleTypes.SMOKE, vec3.x, vec3.y, vec3.z, 0.0D, 0.0D, 0.0D);
            if (f < 0.17F) {
                level.playLocalSound(vec3.x + 0.5D, vec3.y + 0.5D, vec3.z + 0.5D, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0F + randomSource.nextFloat(), randomSource.nextFloat() * 0.7F + 0.3F, false);
            }
        }

        level.addParticle((ParticleOptions) BignayParticles.SMALL_SOUL_FLAME.get(), vec3.x, vec3.y, vec3.z, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(BignayTabs.COLORED_BLOCKS, BignayTabs.FUNCTIONAL_BLOCKS);
    }
}
