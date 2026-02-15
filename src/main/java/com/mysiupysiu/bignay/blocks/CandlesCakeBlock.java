package com.mysiupysiu.bignay.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;

public class CandlesCakeBlock extends CandleCakeBlock {

    public CandlesCakeBlock(Block candle, Block block) {
        super(candle, BlockBehaviour.Properties.copy(block).lightLevel(b -> 2));
    }

    public CandlesCakeBlock(RegistryObject<Block> candle, RegistryObject<Block> block) {
        super(candle.get(), BlockBehaviour.Properties.copy(block.get()).lightLevel(b -> 2));
    }

    public CandlesCakeBlock(RegistryObject<Block> candle) {
        super(candle.get(), BlockBehaviour.Properties.copy(Blocks.CAKE).lightLevel(b -> 2));
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
}
