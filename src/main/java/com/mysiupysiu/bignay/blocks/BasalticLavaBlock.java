package com.mysiupysiu.bignay.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class BasalticLavaBlock extends Block {

    public BasalticLavaBlock() {
        super(BlockBehaviour.Properties
                .copy(Blocks.BASALT)
                .mapColor(MapColor.NETHER)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .requiresCorrectToolForDrops()
                .lightLevel(bs -> 3)
                .strength(1.25F, 4.2F)
                .isValidSpawn((state, world, pos, entityType) -> entityType.fireImmune())
                .hasPostProcess(BasalticLavaBlock::always)
                .emissiveRendering(BasalticLavaBlock::always)
        );
    }

    private static boolean always(BlockState p_50775_, BlockGetter p_50776_, BlockPos p_50777_) {
        return true;
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.isSteppingCarefully() && entity instanceof LivingEntity living && !EnchantmentHelper.hasFrostWalker(living)) {
            entity.hurt(level.damageSources().hotFloor(), 1.0F);
        }
        super.stepOn(level, pos, state, entity);
    }
}
