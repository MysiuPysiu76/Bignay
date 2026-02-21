package com.mysiupysiu.bignay.items;

import com.mysiupysiu.bignay.utils.CreativeTabProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PumpkinPatternItem extends Item implements CreativeTabProvider {

    private final Type type;

    public PumpkinPatternItem(Type type) {
        super(new Properties());
        this.type = type;
    }

    public static PumpkinPatternItem.Type fromId(String id) {
        if (id == null || id.isEmpty()) return PumpkinPatternItem.Type.DEFAULT;

        if (id.endsWith("_pumpkin_pattern")) {
            id = id.substring(0, id.length() - "_pumpkin_pattern".length());
        }

        try {
            return PumpkinPatternItem.Type.valueOf(id.toUpperCase());
        } catch (IllegalArgumentException e) {
            return PumpkinPatternItem.Type.DEFAULT;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("pumpkin_pattern." + type.getName()));
    }

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(CreativeModeTabs.INGREDIENTS);
    }

    public enum Type {
        ANGRY("angry"),
        ANXIOUS("anxious"),
        DERPY("derpy"),
        HAPPY("happy"),
        DEFAULT("default"),
        SKULL("skull"),;

        public final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
