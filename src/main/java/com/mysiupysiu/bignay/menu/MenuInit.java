package com.mysiupysiu.bignay.menu;

import com.mysiupysiu.bignay.BignayMod;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = BignayMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MenuInit {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, BignayMod.MODID);

    public static final RegistryObject<MenuType<ArchaeologyTableMenu>> ARCHAEOLOGY_TABLE_MENU = MENUS.register("archaeology_table_menu", () -> IForgeMenuType.create(ArchaeologyTableMenu::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ARCHAEOLOGY_TABLE_MENU.get(), ArchaeologyTableScreen::new);
        });
    }
}
