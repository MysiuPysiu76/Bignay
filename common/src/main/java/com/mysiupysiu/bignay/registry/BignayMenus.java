package com.mysiupysiu.bignay.registry;

import com.mysiupysiu.bignay.client.menu.ArchaeologyTableMenu;
import com.mysiupysiu.bignay.registry.core.Registrar;
import com.mysiupysiu.bignay.registry.core.RegistrySupplier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class BignayMenus {

    public static final Registrar<MenuType<?>> MENUS = new Registrar<>();

    public static final RegistrySupplier<MenuType<ArchaeologyTableMenu>> ARCHAEOLOGY_TABLE_MENU = (RegistrySupplier<MenuType<ArchaeologyTableMenu>>) (Object) MENUS.register("archaeology_table_menu", () -> new MenuType<>(ArchaeologyTableMenu::new, FeatureFlags.VANILLA_SET));
}
