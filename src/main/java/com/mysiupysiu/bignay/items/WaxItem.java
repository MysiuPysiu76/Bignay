package com.mysiupysiu.bignay.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.ForgeRegistries;

public class WaxItem extends Item {

    public WaxItem() {
        super(new Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        boolean isCopper = state.getBlock().getName().getString().toLowerCase().contains("copper");
        boolean isWaxed = state.getBlock().getName().getString().toLowerCase().contains("waxed");

        if (!level.isClientSide && isCopper && !isWaxed) {
            ResourceLocation originalId = ForgeRegistries.BLOCKS.getKey(state.getBlock());

            if (originalId != null) {
                String waxedName = "waxed_" + originalId.getPath();
                ResourceLocation waxedId = new ResourceLocation(originalId.getNamespace(), waxedName);
                Block waxedBlock = ForgeRegistries.BLOCKS.getValue(waxedId);

                if (waxedBlock != null) {
                    BlockState newState = waxedBlock.defaultBlockState();

                    for (Property<?> property : state.getProperties()) {
                        if (newState.hasProperty(property)) {
                            newState = copyProperty(newState, state, property);
                        }
                    }

                    level.setBlock(pos, newState, 3);
                    level.playSound(null, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0f, 1.0f);

                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.WAX_ON, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 30, 0.5, 0.5, 0.5, 0.0);
                    }

                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.PASS;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> BlockState copyProperty(BlockState to, BlockState from, Property<?> property) {
        return to.setValue((Property<T>) property, (T) from.getValue(property));
    }
}
