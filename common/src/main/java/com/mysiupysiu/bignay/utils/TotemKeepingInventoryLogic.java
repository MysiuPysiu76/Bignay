package com.mysiupysiu.bignay.utils;

import com.mysiupysiu.bignay.registry.BignayItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public final class TotemKeepingInventoryLogic {

    private TotemKeepingInventoryLogic() {}

    private static final long TOTEM_TAG_VALID_TICKS = 40L;

    public static boolean hasFreshTotemTag(ServerPlayer player) {
        if (player == null) return false;
        if (!(player instanceof ITotemKeepingInventoryData data)) return false;

        try {
            long time = data.bignay$getUsedTotemTime();
            if (time == Long.MIN_VALUE) return false;

            long now = player.level().getGameTime();
            long diff = now - time;
            return diff >= 0 && diff <= TOTEM_TAG_VALID_TICKS;
        } catch (Throwable ignored) {
            return false;
        }
    }

    public static void markTotemUsed(ServerPlayer player) {
        if (player == null) return;
        if (!(player instanceof ITotemKeepingInventoryData data)) return;

        try {
            data.bignay$setUsedTotemTime(player.level().getGameTime());
        } catch (Throwable ignored) {}
    }

    public static ItemStack findTotemInHand(ServerPlayer player) {
        if (player == null) return ItemStack.EMPTY;

        try {
            ItemStack main = player.getMainHandItem();
            if (!main.isEmpty() && main.getItem() == BignayItems.TOTEM_OF_KEEPING_INVENTORY.get()) {
                return main;
            }

            ItemStack off = player.getOffhandItem();
            if (!off.isEmpty() && off.getItem() == BignayItems.TOTEM_OF_KEEPING_INVENTORY.get()) {
                return off;
            }
        } catch (Throwable ignored) {}
        return ItemStack.EMPTY;
    }

    public static boolean consumeTotem(ServerPlayer player, ItemStack stack) {
        if (player == null || stack == null || stack.isEmpty()) return false;

        try {
            stack.shrink(1);
            player.awardStat(Stats.ITEM_USED.get(BignayItems.TOTEM_OF_KEEPING_INVENTORY.get()));
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }

    public static void rescue(ServerPlayer player, ItemStack usedStack) {
        if (player == null) return;

        try {
            player.setHealth(1.0F);
            player.getFoodData().setFoodLevel(20);
            player.getFoodData().setSaturation(5.0F);
            player.invulnerableTime = 20;
        } catch (Throwable ignored) {}

        teleportToRespawnSafe(player);
        playCustomTotemVisual(player, usedStack);
    }

    private static void playCustomTotemVisual(ServerPlayer player, ItemStack usedStack) {
        try {
            ServerLevel level = player.serverLevel();
            if (level == null) return;

            ItemStack visualStack = (usedStack == null || usedStack.isEmpty()) ? BignayItems.TOTEM_OF_KEEPING_INVENTORY.get().getDefaultInstance() : usedStack.copy();

            BignayNetworking.sendTotemActivation(player, visualStack);

            level.playSound(null, player.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);

            level.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, player.getX(), player.getY() + 1.0D, player.getZ(), 30, 0.5D, 0.8D, 0.5D, 0.2D);
        } catch (Throwable ignored) {}
    }

    public static void copyInventoryAndXp(ServerPlayer oldP, ServerPlayer newP) {
        if (oldP == null || newP == null) return;

        try {
            Inventory oldInv = oldP.getInventory();
            Inventory newInv = newP.getInventory();

            for (int i = 0; i < Math.min(oldInv.items.size(), newInv.items.size()); i++) {
                ItemStack s = oldInv.items.get(i);
                newInv.items.set(i, s == null ? ItemStack.EMPTY : s.copy());
            }
            for (int i = 0; i < Math.min(oldInv.armor.size(), newInv.armor.size()); i++) {
                ItemStack s = oldInv.armor.get(i);
                newInv.armor.set(i, s == null ? ItemStack.EMPTY : s.copy());
            }
            for (int i = 0; i < Math.min(oldInv.offhand.size(), newInv.offhand.size()); i++) {
                ItemStack s = oldInv.offhand.get(i);
                newInv.offhand.set(i, s == null ? ItemStack.EMPTY : s.copy());
            }

            int levels = oldP.experienceLevel;
            if (levels > 0) newP.giveExperienceLevels(levels);

            newP.totalExperience = oldP.totalExperience;
            newP.experienceProgress = oldP.experienceProgress;
        } catch (Throwable ignored) {
        }
    }

    private static void teleportToRespawnSafe(ServerPlayer player) {
        ServerLevel respawnLevel = null;
        try {
            respawnLevel = player.getServer().getLevel(player.getRespawnDimension());
        } catch (Throwable ignored) {
        }

        if (respawnLevel == null) {
            try {
                respawnLevel = player.getServer().getLevel(Level.OVERWORLD);
            } catch (Throwable ignored) {
            }
        }

        Vec3 targetPos = null;
        ServerLevel targetLevel = respawnLevel;

        try {
            BlockPos respawnPos = player.getRespawnPosition();
            float angle = player.getRespawnAngle();
            boolean forced = player.isRespawnForced();

            if (respawnPos != null && respawnLevel != null) {
                var optional = ServerPlayer.findRespawnPositionAndUseSpawnBlock(respawnLevel, respawnPos, angle, forced, false);
                if (optional.isPresent()) {
                    BlockPos p = BlockPos.containing(optional.get());
                    targetPos = new Vec3(p.getX() + 0.5, p.getY(), p.getZ() + 0.5);
                    targetLevel = respawnLevel;
                }
            }
        } catch (Throwable ignored) {
        }

        if (targetPos == null) {
            try {
                ServerLevel overworld = player.getServer().getLevel(Level.OVERWORLD);
                if (overworld == null) return;

                targetLevel = overworld;
                BlockPos spawn = overworld.getSharedSpawnPos();

                int chunkRadius = 1;
                int attempts = 10;

                for (int i = 0; i < attempts && targetPos == null; i++) {
                    int offsetX = player.getRandom().nextInt(chunkRadius * 16 * 2 + 1) - chunkRadius * 16;
                    int offsetZ = player.getRandom().nextInt(chunkRadius * 16 * 2 + 1) - chunkRadius * 16;
                    BlockPos candidate = spawn.offset(offsetX, 0, offsetZ);
                    BlockPos safe = overworld.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, candidate);

                    if (safe != null && safe.getY() > 0 && safe.getY() < overworld.getMaxBuildHeight()) {
                        targetPos = new Vec3(safe.getX() + 0.5, safe.getY(), safe.getZ() + 0.5);
                    }
                }

                if (targetPos == null) {
                    BlockPos safe = overworld.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, spawn);
                    if (safe == null) safe = spawn;
                    targetPos = new Vec3(safe.getX() + 0.5, safe.getY(), safe.getZ() + 0.5);
                }
            } catch (Throwable ignored) {
            }
        }

        if (targetLevel == null || targetPos == null) return;

        try {
            player.teleportTo(targetLevel, targetPos.x, targetPos.y, targetPos.z, player.getYRot(), player.getXRot());
            player.setDeltaMovement(Vec3.ZERO);
        } catch (Throwable t) {
            try {
                ServerLevel overworld = player.getServer().getLevel(Level.OVERWORLD);
                if (overworld == null) return;

                BlockPos fallback = overworld.getSharedSpawnPos();
                BlockPos safe = overworld.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, fallback);
                if (safe == null) safe = fallback;

                Vec3 pos = new Vec3(safe.getX() + 0.5, safe.getY(), safe.getZ() + 0.5);
                player.teleportTo(overworld, pos.x, pos.y, pos.z, player.getYRot(), player.getXRot());
                player.setDeltaMovement(Vec3.ZERO);
            } catch (Throwable ignored) {
            }
        }

        try {
            player.setHealth(10.0F);
            player.getFoodData().setFoodLevel(20);
            player.getFoodData().setSaturation(5.0F);
        } catch (Throwable ignored) {
        }
    }
}
