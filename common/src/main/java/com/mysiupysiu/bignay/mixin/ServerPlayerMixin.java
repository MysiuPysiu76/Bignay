package com.mysiupysiu.bignay.mixin;

import com.mysiupysiu.bignay.utils.ITotemKeepingInventoryData;
import com.mysiupysiu.bignay.utils.TotemKeepingInventoryLogic;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin implements ITotemKeepingInventoryData {

    @Unique
    private long bignay$usedTotemTime = Long.MIN_VALUE;

    @Override
    public long bignay$getUsedTotemTime() {
        return bignay$usedTotemTime;
    }

    @Override
    public void bignay$setUsedTotemTime(long time) {
        bignay$usedTotemTime = time;
    }

    @Inject(method = "restoreFrom", at = @At("TAIL"))
    private void bignay$restoreFrom(ServerPlayer oldPlayer, boolean alive, CallbackInfo ci) {
        if (!(oldPlayer instanceof ITotemKeepingInventoryData oldData)) return;

        long usedTime = oldData.bignay$getUsedTotemTime();
        if (usedTime == Long.MIN_VALUE) return;

        ServerPlayer self = (ServerPlayer) (Object) this;
        ITotemKeepingInventoryData newData = this;

        newData.bignay$setUsedTotemTime(usedTime);
        TotemKeepingInventoryLogic.copyInventoryAndXp(oldPlayer, self);
    }
}
