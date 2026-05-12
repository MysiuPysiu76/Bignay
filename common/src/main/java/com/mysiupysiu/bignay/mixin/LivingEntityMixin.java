package com.mysiupysiu.bignay.mixin;

import com.mysiupysiu.bignay.registry.BignayItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "dropCustomDeathLoot", at = @At("HEAD"), cancellable = true)
    private void bignay$dropCustomMobHeads(DamageSource source, int looting, boolean hitByPlayer, CallbackInfo ci) {
        LivingEntity killedMob = (LivingEntity) (Object) this;

        if (source.getEntity() instanceof Creeper creeper) {
            if (creeper.canDropMobsSkull()) {
                ItemStack headStack = bignay$getHeadForMob(killedMob);
                if (headStack != null && !headStack.isEmpty()) {
                    creeper.increaseDroppedSkulls();
                    killedMob.spawnAtLocation(headStack);
                    ci.cancel();
                }
            }
        }
    }

    @Unique
    private ItemStack bignay$getHeadForMob(LivingEntity mob) {
//        if (mob instanceof Blaze) return new ItemStack(BignayItems.BLAZE_HEAD.get());
//        if (mob instanceof CaveSpider) return new ItemStack(BignayItems.CAVE_SPIDER_HEAD.get());
//        if (mob instanceof Drowned) return new ItemStack(BignayItems.DROWNED_HEAD.get());
//        if (mob instanceof EnderMan) return new ItemStack(BignayItems.ENDERMAN_HEAD.get());
//        if (mob instanceof Husk) return new ItemStack(BignayItems.HUSK_HEAD.get());
//        if (mob instanceof MagmaCube) return new ItemStack(BignayItems.MAGMA_CUBE_HEAD.get());
//        if (mob instanceof Slime) return new ItemStack(BignayItems.SLIME_HEAD.get());
//        if (mob instanceof Spider) return new ItemStack(BignayItems.SPIDER_HEAD.get());
//        if (mob instanceof Stray) return new ItemStack(BignayItems.STRAY_SKULL.get());

        return null;
    }
}
