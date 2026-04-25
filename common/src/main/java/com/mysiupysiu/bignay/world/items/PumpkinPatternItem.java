package com.mysiupysiu.bignay.world.items;

import com.mysiupysiu.bignay.world.items.tabs.Ingredients;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PumpkinPatternItem extends Item implements Ingredients {

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

    public enum Type {
        ANGRY("angry"),
        ANXIOUS("anxious"),
        CREEPER("creeper"),
        DEFAULT("default"),
        DERPY("derpy"),
        GHOST("ghost"),
        HAPPY("happy"),
        SKULL("skull");

        public final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
