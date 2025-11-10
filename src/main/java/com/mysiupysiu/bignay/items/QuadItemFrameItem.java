package com.mysiupysiu.bignay.items;

import com.mysiupysiu.bignay.entities.AbstractQuadItemFrameItem;
import com.mysiupysiu.bignay.entities.EntityInit;
import com.mysiupysiu.bignay.entities.QuadItemFrameEntity;
import com.mysiupysiu.bignay.utils.CreativeTabProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.Level;

import java.util.List;

public class QuadItemFrameItem extends AbstractQuadItemFrameItem implements CreativeTabProvider {

    public QuadItemFrameItem(Properties properties) {
        super(properties);
    }

    @Override
    protected QuadItemFrameEntity createEntity(Level level) {
        return new QuadItemFrameEntity(EntityInit.QUAD_ITEM_FRAME.get(), level);
    }

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(CreativeModeTabs.FUNCTIONAL_BLOCKS);
    }
}
