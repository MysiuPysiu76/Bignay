package com.mysiupysiu.bignay.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EndDragonFight.class)
public abstract class EndDragonFightMixin {

    @Shadow private BlockPos portalLocation;
    @Shadow @Final private ServerLevel level;
    @Shadow private boolean dragonKilled;

    @Inject(method = "tryRespawn", at = @At("HEAD"), cancellable = true)
    private void bignay$requireFourCrystalsToStart(CallbackInfo ci) {
        if (this.dragonKilled && this.portalLocation != null) {
            AABB searchArea = new AABB(this.portalLocation).inflate(5.0D);
            List<EndCrystal> crystals = this.level.getEntitiesOfClass(EndCrystal.class, searchArea);

            if (crystals.size() < 4) {
                ci.cancel();
            }
        }
    }

    @Redirect(method = "tryRespawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;"))
    private List<EndCrystal> bignay$expandSearchBox(ServerLevel instance, Class<EndCrystal> entityClass, AABB box) {
        return instance.getEntitiesOfClass(entityClass, box.inflate(2.5D));
    }
}
