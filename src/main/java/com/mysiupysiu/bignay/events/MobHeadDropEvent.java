package com.mysiupysiu.bignay.events;

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
public class MobHeadDropEvent {

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

        ItemStack dropStack = null;
        if (mob instanceof EnderMan) {
            dropStack = new ItemStack(ItemInit.ENDERMAN_HEAD.get());
        } else if (mob instanceof CaveSpider) {
            dropStack = new ItemStack(ItemInit.CAVE_SPIDER_HEAD.get());
        } else if (mob instanceof Spider) {
            dropStack = new ItemStack(ItemInit.SPIDER_HEAD.get());
        } else if (mob instanceof MagmaCube) {
            dropStack = new ItemStack(ItemInit.MAGMA_CUBE_HEAD.get());
        } else if (mob instanceof Drowned) {
            dropStack = new ItemStack(ItemInit.DROWNED_HEAD.get());
        } else if (mob instanceof Husk) {
            dropStack = new ItemStack(ItemInit.HUSK_HEAD.get());
        } else if (mob instanceof Blaze) {
            dropStack = new ItemStack(ItemInit.BLAZE_HEAD.get());
        } else if (mob instanceof Slime) {
            dropStack = new ItemStack(ItemInit.SLIME_HEAD.get());
        } else if (mob instanceof Stray) {
            dropStack = new ItemStack(ItemInit.STRAY_SKULL.get());
        }

        if (dropStack == null) return;

        ItemEntity drop = new ItemEntity(mob.level(), mob.getX(), mob.getY(), mob.getZ(), dropStack);
        drop.setDefaultPickUpDelay();
        event.getDrops().add(drop);
    }
}
