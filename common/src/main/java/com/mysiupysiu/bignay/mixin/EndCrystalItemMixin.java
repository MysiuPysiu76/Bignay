package com.mysiupysiu.bignay.mixin;

import com.mysiupysiu.bignay.world.tags.BignayTags;
import net.minecraft.world.item.EndCrystalItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EndCrystalItem.class)
public class EndCrystalItemMixin {

    @Redirect(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    private boolean bignay$allowPlacementOnTag(BlockState instance, Block block) {
        return instance.is(BignayTags.Blocks.END_CRYSTAL_PLACEABLE);
    }
}
