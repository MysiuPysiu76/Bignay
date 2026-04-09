package com.mysiupysiu.bignay.mixin;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoneycombItem.class)
public abstract class HoneycombItemMixin {

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void bignay$stopCopperWaxing(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        String path = BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath();

        if (path.contains("copper")) {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }
}
