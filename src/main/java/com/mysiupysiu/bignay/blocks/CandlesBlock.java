package com.mysiupysiu.bignay.blocks;

import com.mysiupysiu.bignay.utils.CreativeTabProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.ToIntFunction;

public class CandlesBlock extends CandleBlock implements CreativeTabProvider {

    public static final ToIntFunction<BlockState> LIGHT_EMISSION = (bs) ->
         bs.getValue(LIT) ? 2 * bs.getValue(CANDLES) : 0;

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

        level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, vec3.x, vec3.y, vec3.z, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(CreativeModeTabs.COLORED_BLOCKS, CreativeModeTabs.FUNCTIONAL_BLOCKS);
    }
}