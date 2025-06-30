package com.mysiupysiu.bignay.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Map;

public class CustomCampfireBlockEntity extends BlockEntity {

    private static final Map<Block, BlockEntityType<CustomCampfireBlockEntity>> ENTITY_TYPES = Map.of(
            BlockInit.ACACIA_CAMPFIRE.get(), BlockEntityInit.ACACIA_CAMPFIRE.get(),
            BlockInit.BIRCH_CAMPFIRE.get(), BlockEntityInit.BIRCH_CAMPFIRE.get(),
            BlockInit.CHERRY_CAMPFIRE.get(), BlockEntityInit.CHERRY_CAMPFIRE.get(),
            BlockInit.CRIMSON_CAMPFIRE.get(), BlockEntityInit.CRIMSON_CAMPFIRE.get(),
            BlockInit.DARK_OAK_CAMPFIRE.get(), BlockEntityInit.DARK_OAK_CAMPFIRE.get(),
            BlockInit.JUNGLE_CAMPFIRE.get(), BlockEntityInit.JUNGLE_CAMPFIRE.get(),
            BlockInit.MANGROVE_CAMPFIRE.get(), BlockEntityInit.MANGROVE_CAMPFIRE.get(),
            BlockInit.OAK_CAMPFIRE.get(), BlockEntityInit.OAK_CAMPFIRE.get(),
            BlockInit.SPRUCE_CAMPFIRE.get(), BlockEntityInit.SPRUCE_CAMPFIRE.get(),
            BlockInit.WARPED_CAMPFIRE.get(), BlockEntityInit.WARPED_CAMPFIRE.get());

    private final NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    private final int[] cookingProgress = new int[4];
    private final int[] cookingTimeTotal = new int[4];

    public CustomCampfireBlockEntity(BlockPos pos, BlockState state) {
        super(getEntityType(state.getBlock()), pos, state);
    }

    private static BlockEntityType<CustomCampfireBlockEntity> getEntityType(Block block) {
        return ENTITY_TYPES.getOrDefault(block, BlockEntityInit.OAK_CAMPFIRE.get());
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CustomCampfireBlockEntity campfire) {
        if (!state.getValue(CampfireBlock.LIT)) return;

        for (int i = 0; i < campfire.items.size(); ++i) {
            ItemStack stack = campfire.items.get(i);
            if (!stack.isEmpty()) {
                campfire.cookingProgress[i]++;
                if (campfire.cookingProgress[i] >= campfire.cookingTimeTotal[i]) {
                    ItemStack result = getCookingResult(level, stack);
                    if (!result.isEmpty()) {
                        double x = pos.getX() + 0.4 + 0.2 * (i % 2);
                        double z = pos.getZ() + 0.4 + 0.2 * (i / 2);
                        level.addFreshEntity(new ItemEntity(level, x, pos.getY() + 0.5, z, result.copy()));
                    }

                    campfire.setItem(i, ItemStack.EMPTY);
                    campfire.cookingProgress[i] = 0;
                    campfire.cookingTimeTotal[i] = 0;

                    campfire.setChanged();
                    level.sendBlockUpdated(pos, state, state, 3);
                    campfire.syncToClient();
                }
            }
        }
    }

    public boolean placeFood(ItemStack stack, int slot) {
        if (slot >= 0 && slot < 4 && items.get(slot).isEmpty()) {
            if (!hasCampfireRecipe(level, stack)) return false;

            ItemStack single = stack.copy();
            single.setCount(1);
            items.set(slot, single);
            cookingTimeTotal[slot] = getCookTime(level, single);
            cookingProgress[slot] = 0;

            setChanged();
            syncToClient();
            return true;
        }
        return false;
    }

    private static boolean hasCampfireRecipe(Level level, ItemStack stack) {
        return level.getRecipeManager()
                .getRecipeFor(RecipeType.CAMPFIRE_COOKING, new SimpleContainer(stack), level)
                .isPresent();
    }

    public ItemStack getItem(int index) {
        return items.get(index);
    }

    private static int getCookTime(Level level, ItemStack stack) {
        return level.getRecipeManager()
                .getRecipeFor(RecipeType.CAMPFIRE_COOKING, new SimpleContainer(stack), level)
                .map(r -> r.getCookingTime())
                .orElse(200);
    }

    private static ItemStack getCookingResult(Level level, ItemStack stack) {
        return level.getRecipeManager()
                .getRecipeFor(RecipeType.CAMPFIRE_COOKING, new SimpleContainer(stack), level)
                .map(r -> r.getResultItem(level.registryAccess()))
                .orElse(ItemStack.EMPTY);
    }

    public void syncToClient() {
        if (level instanceof ServerLevel serverLevel) {
            ClientboundBlockEntityDataPacket packet = getUpdatePacket();
            if (packet != null) {
                serverLevel.getChunkSource().chunkMap
                        .getPlayers(serverLevel.getChunkAt(worldPosition).getPos(), false)
                        .forEach(player -> player.connection.send(packet));
            }
        }
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.load(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, this.items);
        for (int i = 0; i < 4; i++) {
            this.cookingProgress[i] = tag.getInt("CookingProgress" + i);
            this.cookingTimeTotal[i] = tag.getInt("CookingTimeTotal" + i);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.items);
        for (int i = 0; i < 4; i++) {
            tag.putInt("CookingProgress" + i, this.cookingProgress[i]);
            tag.putInt("CookingTimeTotal" + i, this.cookingTimeTotal[i]);
        }
    }

    public void setItem(int index, ItemStack stack) {
        if (index >= 0 && index < items.size()) {
            items.set(index, stack);
            setChanged();
        }
    }
}
