package com.mysiupysiu.bignay.mixin;

import com.mysiupysiu.bignay.utils.TotemKeepingInventoryLogic;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityTotemMixin {

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void bignay$onHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!((Object) this instanceof ServerPlayer player)) return;
        if (player.level().isClientSide) return;

        float currentHealth = player.getHealth();
        if (amount < currentHealth) return;

        ItemStack stack = TotemKeepingInventoryLogic.findTotemInHand(player);
        if (stack.isEmpty()) return;

        ItemStack visual = stack.copy();

        boolean consumed = TotemKeepingInventoryLogic.consumeTotem(player, stack);
        if (!consumed) return;

        TotemKeepingInventoryLogic.markTotemUsed(player);

        cir.setReturnValue(true);

        TotemKeepingInventoryLogic.rescue(player, visual);
    }

    @Inject(method = "die", at = @At("HEAD"), cancellable = true)
    private void bignay$onDie(DamageSource source, CallbackInfo ci) {
        if (!((Object) this instanceof ServerPlayer player)) return;
        if (player.level().isClientSide) return;

        ItemStack stack = TotemKeepingInventoryLogic.findTotemInHand(player);
        boolean hadFreshTag = TotemKeepingInventoryLogic.hasFreshTotemTag(player);

        ItemStack visual = stack.isEmpty() ? ItemStack.EMPTY : stack.copy();

        boolean consumed = false;
        if (!stack.isEmpty()) {
            consumed = TotemKeepingInventoryLogic.consumeTotem(player, stack);
        }

        if (consumed || hadFreshTag) {
            TotemKeepingInventoryLogic.markTotemUsed(player);
            ci.cancel();

            TotemKeepingInventoryLogic.rescue(player, visual);
        }
    }

    @Inject(method = "dropAllDeathLoot", at = @At("HEAD"), cancellable = true)
    private void bignay$onDropAllDeathLoot(DamageSource source, CallbackInfo ci) {
        if (!((Object) this instanceof ServerPlayer player)) return;
        if (player.level().isClientSide) return;

        if (TotemKeepingInventoryLogic.hasFreshTotemTag(player)) {
            ci.cancel();
        }
    }

    @Inject(method = "dropExperience", at = @At("HEAD"), cancellable = true)
    private void bignay$onDropExperience(CallbackInfo ci) {
        if (!((Object) this instanceof ServerPlayer player)) return;
        if (player.level().isClientSide) return;

        if (TotemKeepingInventoryLogic.hasFreshTotemTag(player)) {
            ci.cancel();
        }
    }

    @Inject(method = "dropEquipment", at = @At("HEAD"), cancellable = true)
    private void bignay$onDropEquipment(CallbackInfo ci) {
        if (!((Object) this instanceof ServerPlayer player)) return;
        if (player.level().isClientSide) return;

        if (TotemKeepingInventoryLogic.hasFreshTotemTag(player)) {
            ci.cancel();
        }
    }
}
