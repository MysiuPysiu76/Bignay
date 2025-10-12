package com.mysiupysiu.bignay.util;

import com.mysiupysiu.bignay.items.ItemInit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobDropHandler {

    private static final Set<Integer> USED_CREEPERS = new HashSet<>();

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (event.getEntity().level().isClientSide) return;

        Entity mob = event.getEntity();
        DamageSource source = event.getSource();

        Entity attacker = source.getEntity();
        Entity direct = source.getDirectEntity();

        Creeper creeper = null;
        if (attacker instanceof Creeper c) creeper = c;
        else if (direct instanceof Creeper c) creeper = c;

        if (creeper == null || !creeper.isPowered()) return;

        if (USED_CREEPERS.contains(creeper.getId())) return;
        USED_CREEPERS.add(creeper.getId());

        ItemStack dropStack = switch (mob) {
            case EnderMan enderMan -> new ItemStack(ItemInit.ENDERMAN_HEAD.get());
            case CaveSpider caveSpider -> new ItemStack(ItemInit.CAVE_SPIDER_HEAD.get());
            case Spider spider -> new ItemStack(ItemInit.SPIDER_HEAD.get());
            case MagmaCube magma -> new ItemStack(ItemInit.MAGMA_CUBE_HEAD.get());
            case Drowned drowned -> new ItemStack(ItemInit.DROWNED_HEAD.get());
            case Husk husk -> new ItemStack(ItemInit.HUSK_HEAD.get());
            case Blaze blaze -> new ItemStack(ItemInit.BLAZE_HEAD.get());
            case Slime slime -> new ItemStack(ItemInit.SLIME_HEAD.get());
            default -> null;
        };

        if (dropStack == null) return;

        ItemEntity drop = new ItemEntity(mob.level(), mob.getX(), mob.getY(), mob.getZ(), dropStack);
        drop.setDefaultPickUpDelay();
        event.getDrops().add(drop);
    }
}
