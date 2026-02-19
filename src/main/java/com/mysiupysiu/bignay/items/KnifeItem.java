package com.mysiupysiu.bignay.items;

import com.mysiupysiu.bignay.utils.CreativeTabProvider;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;

import java.util.List;

public class KnifeItem extends Item implements CreativeTabProvider {

    private final Multimap<Attribute, AttributeModifier> modifiers;

    public KnifeItem() {
        super(new Item.Properties().stacksTo(1).durability(256));

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon damage", 5.0, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon speed", -1.8, AttributeModifier.Operation.ADDITION));

        this.modifiers = builder.build();
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();

        if (state.is(Blocks.PUMPKIN)) {

            if (!level.isClientSide) {

                Direction face = context.getClickedFace();
                Direction spawnDir = face.getAxis() == Direction.Axis.Y ? context.getHorizontalDirection().getOpposite() : face;

                level.setBlock(pos, Blocks.CARVED_PUMPKIN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, spawnDir), 11);

                level.playSound(null, pos, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F);

                double x = pos.getX() + 0.5 + spawnDir.getStepX() * 0.65;
                double y = pos.getY() + 0.1;
                double z = pos.getZ() + 0.5 + spawnDir.getStepZ() * 0.65;

                ItemEntity seeds = new ItemEntity(level, x, y, z, new ItemStack(Items.PUMPKIN_SEEDS, 4));

                seeds.setDeltaMovement(0.05 * spawnDir.getStepX() + level.random.nextDouble() * 0.02, 0.05, 0.05 * spawnDir.getStepZ() + level.random.nextDouble() * 0.02);

                level.addFreshEntity(seeds);

                context.getItemInHand().hurtAndBreak(1, player, p -> p.broadcastBreakEvent(context.getHand()));
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.useOn(context);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? modifiers : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
            stack.hurtAndBreak(1, miner, e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(CreativeModeTabs.TOOLS_AND_UTILITIES, CreativeModeTabs.COMBAT);
    }
}
