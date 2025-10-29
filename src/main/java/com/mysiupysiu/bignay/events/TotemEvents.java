package com.mysiupysiu.bignay.events;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.items.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.EventPriority;

@Mod.EventBusSubscriber(modid = BignayMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TotemEvents {

    private static final String USED_TOTEM_TAG_TIME = "bignay:used_totem_time";
    private static final long TOTEM_TAG_VALID_TICKS = 40L;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingAttack(LivingAttackEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getEntity() instanceof ServerPlayer)) return;
        ServerPlayer player = (ServerPlayer) event.getEntity();
        if (player.level().isClientSide) return;

        float damage = event.getAmount();
        float currentHealth = player.getHealth();
        if (damage < currentHealth) return;

        ItemStack stack = findTotemInHand(player);
        if (stack.isEmpty()) return;

        boolean consumed = consumeTotem(player, stack);
        if (!consumed) return;

        event.setCanceled(true);

        CompoundTag tag = player.getPersistentData();
        try {
            long time = player.level().getGameTime();
            tag.putLong(USED_TOTEM_TAG_TIME, time);
        } catch (Throwable ignored) {
        }

        player.setHealth(Math.max(1.0F, player.getMaxHealth() / 2.0F));
        player.getFoodData().setFoodLevel(20);
        player.getFoodData().setSaturation(5.0F);
        try {
            player.invulnerableTime = 20;
        } catch (Throwable ignored) {
        }

        teleportToRespawnSafe(player);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getEntity() instanceof ServerPlayer)) return;
        ServerPlayer player = (ServerPlayer) event.getEntity();
        if (player.level().isClientSide) return;

        ItemStack stack = findTotemInHand(player);
        CompoundTag tag = player.getPersistentData();
        boolean hadFreshTag = hasFreshTotemTag(tag, player);

        boolean consumed = false;
        if (!stack.isEmpty()) {
            consumed = consumeTotem(player, stack);
        }

        if (consumed || hadFreshTag) {
            try {
                long time = player.level().getGameTime();
                tag.putLong(USED_TOTEM_TAG_TIME, time);
            } catch (Throwable ignored) {
            }

            event.setCanceled(true);

            player.setHealth(Math.max(1.0F, player.getMaxHealth() / 2.0F));
            player.getFoodData().setFoodLevel(20);
            player.getFoodData().setSaturation(5.0F);
            try {
                player.invulnerableTime = 20;
            } catch (Throwable ignored) {
            }

            teleportToRespawnSafe(player);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getEntity() instanceof ServerPlayer)) return;
        ServerPlayer player = (ServerPlayer) event.getEntity();
        if (player.level().isClientSide) return;

        CompoundTag tag = player.getPersistentData();
        boolean hasFreshTag = hasFreshTotemTag(tag, player);

        if (hasFreshTag) {
            try {
                event.getDrops().clear();
            } catch (Throwable ignored) {
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        Player original = event.getOriginal();
        Player cloned = event.getEntity();
        if (!(original instanceof ServerPlayer) || !(cloned instanceof ServerPlayer)) return;
        ServerPlayer oldP = (ServerPlayer) original;
        ServerPlayer newP = (ServerPlayer) cloned;

        try {
            CompoundTag oldTag = oldP.getPersistentData();
            boolean hadFreshTag = hasFreshTotemTag(oldTag, oldP);

            if (!hadFreshTag) {
                return;
            }

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

            newP.getPersistentData().remove(USED_TOTEM_TAG_TIME);
        } catch (Throwable ignored) {
        }
    }

    private static boolean hasFreshTotemTag(CompoundTag tag, ServerPlayer player) {
        if (tag == null || player == null || player.level() == null) return false;
        try {
            if (!tag.contains(USED_TOTEM_TAG_TIME)) return false;
            long time = tag.getLong(USED_TOTEM_TAG_TIME);
            long now = player.level().getGameTime();
            long diff = now - time;
            return diff >= 0 && diff <= TOTEM_TAG_VALID_TICKS;
        } catch (Throwable ignored) {
            return false;
        }
    }

    private static ItemStack findTotemInHand(ServerPlayer player) {
        ItemStack main = player.getMainHandItem();
        if (!main.isEmpty()) {
            if (main.getItem() == ItemInit.TOTEM_OF_KEEPING_INVENTORY.get()) return main;
        }
        ItemStack off = player.getOffhandItem();
        if (!off.isEmpty()) {
            if (off.getItem() == ItemInit.TOTEM_OF_KEEPING_INVENTORY.get()) return off;
        }
        return ItemStack.EMPTY;
    }

    private static boolean consumeTotem(ServerPlayer player, ItemStack stack) {
        if (stack == null || stack.isEmpty()) return false;
        stack.shrink(1);
        ServerLevel lvl = player.serverLevel();
        if (lvl != null) {
            lvl.playSound(null, player.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        return true;
    }

    private static void teleportToRespawnSafe(ServerPlayer player) {
        ServerLevel respawnLevel = player.server.getLevel(player.getRespawnDimension());
        if (respawnLevel == null) respawnLevel = player.server.getLevel(Level.OVERWORLD);

        Vec3 targetPos = null;
        ServerLevel targetLevel = respawnLevel;

        BlockPos respawnPos = player.getRespawnPosition();
        float angle = player.getRespawnAngle();
        boolean forced = player.isRespawnForced();

        if (respawnPos != null && respawnLevel != null) {
            try {
                var optional = ServerPlayer.findRespawnPositionAndUseSpawnBlock(respawnLevel, respawnPos, angle, forced, false);
                if (optional.isPresent()) {
                    BlockPos p = BlockPos.containing(optional.get());
                    targetPos = new Vec3(p.getX() + 0.5, p.getY(), p.getZ() + 0.5);
                    targetLevel = respawnLevel;
                }
            } catch (Throwable ignored) {
            }
        }

        if (targetPos == null) {
            ServerLevel overworld = player.server.getLevel(Level.OVERWORLD);
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
        }

        try {
            player.teleportTo(targetLevel, targetPos.x, targetPos.y, targetPos.z, player.getYRot(), player.getXRot());
            player.setDeltaMovement(Vec3.ZERO);
        } catch (Throwable t) {
            try {
                ServerLevel overworld = player.server.getLevel(Level.OVERWORLD);
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
            player.setHealth(20);
            player.getFoodData().setFoodLevel(20);
            player.getFoodData().setSaturation(5.0F);
        } catch (Throwable ignored) {
        }
    }
}
