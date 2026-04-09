package com.mysiupysiu.bignay.mixin;

import com.mysiupysiu.bignay.utils.world.CreatedWorldDate;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class CreateWorldMixin {

    @Final
    @Shadow
    protected LevelStorageSource.LevelStorageAccess storageSource;

    @Inject(method = "loadLevel", at = @At("HEAD"))
    private void bignay$onLoadLevel(CallbackInfo ci) {

        try {
            String folderName = this.storageSource.getLevelId();
            MinecraftServer server = (MinecraftServer) (Object) this;

            if (server.isSingleplayer()) {
                CreatedWorldDate.setCreatedDate(folderName);
            }
        } catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error("Error while saving world creation date", e);
        }
    }
}
