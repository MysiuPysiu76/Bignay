package com.mysiupysiu.bignay.fabric.mixin;

import com.mysiupysiu.bignay.world.items.EquipablePumpkinItem;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderMan.class)
public class EndermanMaskMixin {

    @Inject(method = "isLookingAtMe", at = @At("HEAD"), cancellable = true)
    private void bignay$ignoreCustomPumpkin(Player player, CallbackInfoReturnable<Boolean> cir) {
        ItemStack helmet = player.getInventory().armor.get(3);

        if (helmet.getItem() instanceof EquipablePumpkinItem) {
            cir.setReturnValue(false);
        }
    }
}
