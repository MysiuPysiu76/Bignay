package com.mysiupysiu.bignay.mixin;

import com.mysiupysiu.bignay.config.BignayConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(EndPodiumFeature.class)
public abstract class EndPodiumMixin {

    @Shadow @Final
    private boolean active;

    @Inject(method = "place", at = @At("HEAD"), cancellable = true)
    private void replaceWithNBTStructure(FeaturePlaceContext<NoneFeatureConfiguration> context, CallbackInfoReturnable<Boolean> cir) {
        if (!BignayConfig.features.betterEndPodium.get()) return;

        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();

        if (!level.isClientSide()) {
            ServerLevel serverLevel = level.getLevel();
            StructureTemplateManager manager = serverLevel.getServer().getStructureManager();

            ResourceLocation structureLocation = new ResourceLocation("bignay", "end_fountain");
            Optional<StructureTemplate> templateOpt = manager.get(structureLocation);

            if (templateOpt.isPresent()) {
                StructureTemplate template = templateOpt.get();
                StructurePlaceSettings settings = new StructurePlaceSettings().setMirror(Mirror.NONE).setRotation(Rotation.NONE).setIgnoreEntities(false);

                BlockPos size = new BlockPos(template.getSize().getX(), template.getSize().getY(), template.getSize().getZ());
                BlockPos placePos = origin.offset(-size.getX() / 2, 0, -size.getZ() / 2);

                template.placeInWorld(serverLevel, placePos, placePos, settings, serverLevel.getRandom(), 2);

                if (this.active) {
                    for (int x = -3; x <= 3; x++) {
                        for (int z = -3; z <= 3; z++) {
                            if (Math.abs(x) == 3 && Math.abs(z) == 3 || x == 0 && z == 0) continue;
                            serverLevel.setBlockAndUpdate(origin.offset(x, 1, z), Blocks.END_PORTAL.defaultBlockState());
                        }
                    }
                }

                cir.setReturnValue(true);
            }
        }
    }
}
