package com.mysiupysiu.bignay.items;

import com.mysiupysiu.bignay.utils.BignayTags;
import com.mysiupysiu.bignay.utils.SoundsInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class CopperHorn extends InstrumentItem {

    public CopperHorn() {
        super(new Properties().stacksTo(1), BignayTags.Instruments.COPPER_HORNS);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag tag = stack.getTag();

        if (tag != null && tag.contains("instrument")) {
            String instrumentId = tag.getString("instrument");

            int id = getVariantIndex(instrumentId);

            SoundEvent soundToPlay;
            if (player.isCrouching()) {
                soundToPlay = SoundsInit.BASS.get(id).get();
            } else if (player.getXRot() < -45.0F) {
                soundToPlay = SoundsInit.HARMONY.get(id).get();
            } else {
                soundToPlay = SoundsInit.MELODY.get(id).get();
            }

            level.playSound(null, player.getX(), player.getY(), player.getZ(), soundToPlay, SoundSource.RECORDS, 16.0F, 1.0F);
            level.gameEvent(GameEvent.INSTRUMENT_PLAY, player.position(), GameEvent.Context.of(player));
            player.startUsingItem(hand);
            player.getCooldowns().addCooldown(this, 140);

            return InteractionResultHolder.consume(stack);
        }
        return InteractionResultHolder.fail(stack);
    }

    private int getVariantIndex(String instrumentId) {
        if (instrumentId.contains("sky")) return 0;
        if (instrumentId.contains("hymn")) return 1;
        if (instrumentId.contains("water")) return 2;
        if (instrumentId.contains("fire")) return 3;
        if (instrumentId.contains("urge")) return 4;
        if (instrumentId.contains("temper")) return 5;
        if (instrumentId.contains("nest")) return 6;
        if (instrumentId.contains("lake")) return 7;
        if (instrumentId.contains("river")) return 8;
        if (instrumentId.contains("moon")) return 9;
        return 0;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.TOOT_HORN;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }
}
