package com.mysiupysiu.bignay.mixin;

import com.mysiupysiu.bignay.items.ItemInit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "use", at = @At("HEAD"))
    private void onUse(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack stack = (ItemStack) (Object) this;

        if (stack.is(Items.GOAT_HORN) || stack.is(ItemInit.COPPER_HORN.get())) {
            player.getCooldowns().addCooldown(Items.GOAT_HORN, 140);
            player.getCooldowns().addCooldown(ItemInit.COPPER_HORN.get(), 140);
        }
    }
}
