package com.mysiupysiu.bignay.entities;

import com.mojang.logging.LogUtils;

import java.util.OptionalInt;
import javax.annotation.Nullable;

import com.mysiupysiu.bignay.items.ItemInit;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

public class QuadItemFrameEntity extends HangingEntity {
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final EntityDataAccessor<ItemStack> DATA_ITEM_0 = SynchedEntityData.defineId(QuadItemFrameEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_1 = SynchedEntityData.defineId(QuadItemFrameEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_2 = SynchedEntityData.defineId(QuadItemFrameEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_3 = SynchedEntityData.defineId(QuadItemFrameEntity.class, EntityDataSerializers.ITEM_STACK);

    private static final EntityDataAccessor<Integer> DATA_ROT_0 = SynchedEntityData.defineId(QuadItemFrameEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ROT_1 = SynchedEntityData.defineId(QuadItemFrameEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ROT_2 = SynchedEntityData.defineId(QuadItemFrameEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ROT_3 = SynchedEntityData.defineId(QuadItemFrameEntity.class, EntityDataSerializers.INT);

    public static final int NUM_ROTATIONS = 8;
    private float dropChance = 1.0F;
    private boolean fixed;

    public QuadItemFrameEntity(EntityType<? extends QuadItemFrameEntity> p_31761_, Level p_31762_) {
        super(p_31761_, p_31762_);
    }

    @Override
    protected float getEyeHeight(Pose p_31784_, EntityDimensions p_31785_) {
        return 0.0F;
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_ITEM_0, ItemStack.EMPTY);
        this.getEntityData().define(DATA_ITEM_1, ItemStack.EMPTY);
        this.getEntityData().define(DATA_ITEM_2, ItemStack.EMPTY);
        this.getEntityData().define(DATA_ITEM_3, ItemStack.EMPTY);

        this.getEntityData().define(DATA_ROT_0, 0);
        this.getEntityData().define(DATA_ROT_1, 0);
        this.getEntityData().define(DATA_ROT_2, 0);
        this.getEntityData().define(DATA_ROT_3, 0);
    }

    @Override
    public void setDirection(Direction p_31793_) {
        Validate.notNull(p_31793_);
        this.direction = p_31793_;
        if (p_31793_.getAxis().isHorizontal()) {
            this.setXRot(0.0F);
            this.setYRot((float) (this.direction.get2DDataValue() * 90));
        } else {
            this.setXRot((float) (-90 * p_31793_.getAxisDirection().getStep()));
            this.setYRot(0.0F);
        }

        this.xRotO = this.getXRot();
        this.yRotO = this.getYRot();
        this.recalculateBoundingBox();
    }

    @Override
    protected void recalculateBoundingBox() {
        if (this.direction != null) {
            double d0 = 0.46875D;
            double d1 = (double) this.pos.getX() + 0.5D - (double) this.direction.getStepX() * 0.46875D;
            double d2 = (double) this.pos.getY() + 0.5D - (double) this.direction.getStepY() * 0.46875D;
            double d3 = (double) this.pos.getZ() + 0.5D - (double) this.direction.getStepZ() * 0.46875D;
            this.setPosRaw(d1, d2, d3);
            double d4 = (double) this.getWidth();
            double d5 = (double) this.getHeight();
            double d6 = (double) this.getWidth();
            Direction.Axis direction$axis = this.direction.getAxis();
            switch (direction$axis) {
                case X:
                    d4 = 1.0D;
                    break;
                case Y:
                    d5 = 1.0D;
                    break;
                case Z:
                    d6 = 1.0D;
            }

            d4 /= 32.0D;
            d5 /= 32.0D;
            d6 /= 32.0D;
            this.setBoundingBox(new AABB(d1 - d4, d2 - d5, d3 - d6, d1 + d4, d2 + d5, d3 + d6));
        }
    }

    @Override
    public boolean survives() {
        if (this.fixed) {
            return true;
        } else if (!this.level().noCollision(this)) {
            return false;
        } else {
            BlockState blockstate = this.level().getBlockState(this.pos.relative(this.direction.getOpposite()));
            return blockstate.isSolid() || this.direction.getAxis().isHorizontal() && DiodeBlock.isDiode(blockstate) ? this.level().getEntities(this, this.getBoundingBox(), HANGING_ENTITY).isEmpty() : false;
        }
    }

    @Override
    public void move(MoverType p_31781_, Vec3 p_31782_) {
        if (!this.fixed) {
            super.move(p_31781_, p_31782_);
        }
    }

    @Override
    public void push(double p_31817_, double p_31818_, double p_31819_) {
        if (!this.fixed) {
            super.push(p_31817_, p_31818_, p_31819_);
        }
    }

    @Override
    public float getPickRadius() {
        return 0.0F;
    }

    @Override
    public void kill() {
        this.removeAllFramedMaps();
        super.kill();
    }

    @Override
    public boolean hurt(DamageSource p_31776_, float p_31777_) {
        if (this.fixed) {
            return !p_31776_.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && !p_31776_.isCreativePlayer() ? false : super.hurt(p_31776_, p_31777_);
        } else if (this.isInvulnerableTo(p_31776_)) {
            return false;
        } else if (!p_31776_.is(DamageTypeTags.IS_EXPLOSION) && (!this.isAllEmpty())) {
            if (!this.level().isClientSide) {
                this.dropItem(p_31776_.getEntity(), false);
                this.gameEvent(GameEvent.BLOCK_CHANGE, p_31776_.getEntity());
                this.playSound(this.getRemoveItemSound(), 1.0F, 1.0F);
            }

            return true;
        } else {
            return super.hurt(p_31776_, p_31777_);
        }
    }

    private boolean isAllEmpty() {
        for (int i = 0; i < 4; i++) {
            if (!this.getItem(i).isEmpty()) return false;
        }
        return true;
    }

    public SoundEvent getRemoveItemSound() {
        return SoundEvents.ITEM_FRAME_REMOVE_ITEM;
    }

    @Override
    public int getWidth() {
        return 14;
    }

    @Override
    public int getHeight() {
        return 14;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double p_31769_) {
        double d0 = 16.0D;
        d0 *= 64.0D * getViewScale();
        return p_31769_ < d0 * d0;
    }

    @Override
    public void dropItem(@Nullable Entity p_31779_) {
        this.playSound(this.getBreakSound(), 1.0F, 1.0F);
        this.dropItem(p_31779_, true);
        this.gameEvent(GameEvent.BLOCK_CHANGE, p_31779_);
    }

    public SoundEvent getBreakSound() {
        return SoundEvents.ITEM_FRAME_BREAK;
    }

    @Override
    public void playPlacementSound() {
        this.playSound(this.getPlaceSound(), 1.0F, 1.0F);
    }

    public SoundEvent getPlaceSound() {
        return SoundEvents.ITEM_FRAME_PLACE;
    }

    private void dropItem(@Nullable Entity p_31803_, boolean p_31804_) {
        if (!this.fixed) {
            if (p_31803_ instanceof Player) {
                Player player = (Player) p_31803_;
                if (player.getAbilities().instabuild) {
                    this.removeAllFramedMaps();
                    return;
                }
            }

            if (p_31804_) {
                this.spawnAtLocation(this.getFrameItemStack());
            }

            for (int i = 0; i < 4; i++) {
                ItemStack itemstack = this.getItem(i);
                if (!itemstack.isEmpty()) {
                    itemstack = itemstack.copy();
                    this.removeFramedMap(itemstack);
                    if (this.random.nextFloat() < this.dropChance) {
                        this.spawnAtLocation(itemstack);
                    }
                    this.setItem(i, ItemStack.EMPTY, false);
                }
            }
        }
    }

    private void removeAllFramedMaps() {
        for (int i = 0; i < 4; i++) {
            this.removeFramedMap(this.getItem(i));
        }
    }

    private void removeFramedMap(ItemStack p_31811_) {
        this.getFramedMapId(p_31811_).ifPresent((p_289456_) -> {
            MapItemSavedData mapitemsaveddata = MapItem.getSavedData(p_289456_, this.level());
            if (mapitemsaveddata != null) {
                mapitemsaveddata.removedFromFrame(this.pos, this.getId());
                mapitemsaveddata.setDirty(true);
            }
        });
        p_31811_.setEntityRepresentation((Entity) null);
    }

    private OptionalInt getFramedMapId(ItemStack stack) {
        if (stack.is(Items.FILLED_MAP)) {
            Integer integer = MapItem.getMapId(stack);
            if (integer != null) {
                return OptionalInt.of(integer);
            }
        }
        return OptionalInt.empty();
    }

    public ItemStack getItem() {
        return this.getItem(0);
    }

    public ItemStack getItem(int slot) {
        return switch (slot) {
            case 0 -> this.getEntityData().get(DATA_ITEM_0);
            case 1 -> this.getEntityData().get(DATA_ITEM_1);
            case 2 -> this.getEntityData().get(DATA_ITEM_2);
            case 3 -> this.getEntityData().get(DATA_ITEM_3);
            default -> ItemStack.EMPTY;
        };
    }

    public OptionalInt getFramedMapId() {
        for (int i = 0; i < 4; i++) {
            OptionalInt id = this.getFramedMapId(this.getItem(i));
            if (id.isPresent()) return id;
        }
        return OptionalInt.empty();
    }

    public void setItem(ItemStack p_31806_) {
        this.setItem(0, p_31806_, true);
    }

    public void setItem(int slot, ItemStack stack, boolean notify) {
        if (!stack.isEmpty()) {
            stack = stack.copyWithCount(1);
        }

        ItemStack old = this.getItem(slot);
        if (!old.isEmpty() && !ItemStack.matches(old, stack)) {
            this.removeFramedMap(old);
        }

        this.onItemChanged(slot, stack);
        switch (slot) {
            case 0 -> this.getEntityData().set(DATA_ITEM_0, stack);
            case 1 -> this.getEntityData().set(DATA_ITEM_1, stack);
            case 2 -> this.getEntityData().set(DATA_ITEM_2, stack);
            case 3 -> this.getEntityData().set(DATA_ITEM_3, stack);
        }
        if (!stack.isEmpty()) {
            this.playSound(this.getAddItemSound(), 1.0F, 1.0F);
        }

        if (notify && this.pos != null) {
            this.level().updateNeighbourForOutputSignal(this.pos, Blocks.AIR);
        }
    }

    public SoundEvent getAddItemSound() {
        return SoundEvents.ITEM_FRAME_ADD_ITEM;
    }

    @Override
    public SlotAccess getSlot(int p_149629_) {
        if (p_149629_ >= 0 && p_149629_ < 4) {
            final int idx = p_149629_;
            return new SlotAccess() {
                public ItemStack get() {
                    return QuadItemFrameEntity.this.getItem(idx);
                }

                public boolean set(ItemStack p_149635_) {
                    QuadItemFrameEntity.this.setItem(idx, p_149635_, false);
                    return true;
                }
            };
        }
        return super.getSlot(p_149629_);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_31797_) {
        if (p_31797_.equals(DATA_ITEM_0) || p_31797_.equals(DATA_ITEM_1) || p_31797_.equals(DATA_ITEM_2) || p_31797_.equals(DATA_ITEM_3)) {
            this.recalculateBoundingBox();
        }
    }

    private void onItemChanged(int slot, ItemStack stack) {
        this.recalculateBoundingBox();
    }

    public int getRotation(int slot) {
        return switch (slot) {
            case 0 -> this.getEntityData().get(DATA_ROT_0);
            case 1 -> this.getEntityData().get(DATA_ROT_1);
            case 2 -> this.getEntityData().get(DATA_ROT_2);
            case 3 -> this.getEntityData().get(DATA_ROT_3);
            default -> 0;
        };
    }

    public void setRotation(int slot, int rot) {
        setRotation(slot, rot, true);
    }

    private void setRotation(int slot, int rot, boolean notify) {
        int r = Math.floorMod(rot, NUM_ROTATIONS);
        switch (slot) {
            case 0 -> this.getEntityData().set(DATA_ROT_0, r);
            case 1 -> this.getEntityData().set(DATA_ROT_1, r);
            case 2 -> this.getEntityData().set(DATA_ROT_2, r);
            case 3 -> this.getEntityData().set(DATA_ROT_3, r);
        }
        if (notify && this.pos != null) {
            this.level().updateNeighbourForOutputSignal(this.pos, Blocks.AIR);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_31808_) {
        super.addAdditionalSaveData(p_31808_);
        for (int i = 0; i < 4; i++) {
            ItemStack it = this.getItem(i);
            if (!it.isEmpty()) {
                CompoundTag tag = new CompoundTag();
                it.save(tag);
                p_31808_.put("Item" + i, tag);
                p_31808_.putByte("ItemRotation" + i, (byte) this.getRotation(i));
            }
        }

        p_31808_.putByte("Facing", (byte) this.direction.get3DDataValue());
        p_31808_.putBoolean("Invisible", this.isInvisible());
        p_31808_.putBoolean("Fixed", this.fixed);
        p_31808_.putFloat("ItemDropChance", this.dropChance);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_31795_) {
        super.readAdditionalSaveData(p_31795_);
        for (int i = 0; i < 4; i++) {
            String key = "Item" + i;
            if (p_31795_.contains(key)) {
                CompoundTag compoundtag = p_31795_.getCompound(key);
                if (compoundtag != null && !compoundtag.isEmpty()) {
                    ItemStack itemstack = ItemStack.of(compoundtag);
                    if (itemstack.isEmpty()) {
                        LOGGER.warn("Unable to load item from: {}", (Object) compoundtag);
                    } else {
                        this.setItem(i, itemstack, false);
                        String rKey = "ItemRotation" + i;
                        if (p_31795_.contains(rKey)) {
                            this.setRotation(i, p_31795_.getByte(rKey), false);
                        }
                    }
                }
            }
        }

        if (p_31795_.contains("ItemDropChance")) {
            this.dropChance = p_31795_.getFloat("ItemDropChance");
        }

        this.setDirection(Direction.from3DDataValue(p_31795_.getByte("Facing")));
        this.setInvisible(p_31795_.getBoolean("Invisible"));
        this.fixed = p_31795_.getBoolean("Fixed");
    }

    private int getQuadrantFromHit(Player player) {
        Vec3 rayOrigin = player.getEyePosition(1.0F);
        Vec3 rayDir = player.getViewVector(1.0F).normalize();
        Vec3 planeCenter = new Vec3(this.getX(), this.getY(), this.getZ());
        Vec3 normal = new Vec3(this.direction.getStepX(), this.direction.getStepY(), this.direction.getStepZ()).normalize();
        Vec3 planePoint = planeCenter.add(-normal.x * 0.46875D, -normal.y * 0.46875D, -normal.z * 0.46875D);

        double denom = rayDir.dot(normal);
        if (Math.abs(denom) < 1.0E-6) {
            return 0;
        }

        double t = (planePoint.subtract(rayOrigin)).dot(normal) / denom;
        if (t < 0) {
            return 0;
        }

        Vec3 hit = rayOrigin.add(rayDir.scale(t));

        Vec3 localUp;
        if (Math.abs(normal.y) > 0.9) {
            localUp = new Vec3(0.0D, 0.0D, 1.0D);
        } else {
            localUp = new Vec3(0.0D, 1.0D, 0.0D);
        }

        Vec3 right = localUp.cross(normal).normalize();
        Vec3 up = normal.cross(right).normalize();

        Vec3 delta = hit.subtract(planePoint);
        double x = delta.dot(right);
        double y = delta.dot(up);

        boolean left = x < 0;
        boolean top = y > 0;

        if (left && top) return 0;
        if (!left && top) return 1;
        if (left && !top) return 2;
        return 3;
    }

    @Override
    public InteractionResult interact(Player p_31787_, InteractionHand p_31788_) {
        ItemStack itemstackInHand = p_31787_.getItemInHand(p_31788_);
        boolean hasAny = !this.isAllEmpty();
        boolean hasHeld = !itemstackInHand.isEmpty();
        if (this.fixed) {
            return InteractionResult.PASS;
        } else if (!this.level().isClientSide) {
            int slot = this.getQuadrantFromHit(p_31787_);
            ItemStack slotStack = this.getItem(slot);
            if (slotStack.isEmpty()) {
                if (hasHeld && !this.isRemoved()) {
                    if (itemstackInHand.is(Items.FILLED_MAP)) {
                        MapItemSavedData mapitemsaveddata = MapItem.getSavedData(itemstackInHand, this.level());
                        if (mapitemsaveddata != null && mapitemsaveddata.isTrackedCountOverLimit(256)) {
                            return InteractionResult.FAIL;
                        }
                    }

                    this.setItem(slot, itemstackInHand.copy(), true);
                    this.gameEvent(GameEvent.BLOCK_CHANGE, p_31787_);
                    if (!p_31787_.getAbilities().instabuild) {
                        itemstackInHand.shrink(1);
                    }
                }
            } else {
                this.playSound(this.getRotateItemSound(), 1.0F, 1.0F);
                this.setRotation(slot, this.getRotation(slot) + 1);
                this.gameEvent(GameEvent.BLOCK_CHANGE, p_31787_);
            }
            return InteractionResult.CONSUME;
        } else {
            return !hasAny && !hasHeld ? InteractionResult.PASS : InteractionResult.SUCCESS;
        }
    }

    public SoundEvent getRotateItemSound() {
        return SoundEvents.ITEM_FRAME_ROTATE_ITEM;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this, this.direction.get3DDataValue(), this.getPos());
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket p_149626_) {
        super.recreateFromPacket(p_149626_);
        this.setDirection(Direction.from3DDataValue(p_149626_.getData()));
    }

    @Override
    public ItemStack getPickResult() {
        for (int i = 0; i < 4; i++) {
            ItemStack s = this.getItem(i);
            if (!s.isEmpty()) return s.copy();
        }
        return this.getFrameItemStack();
    }

    protected ItemStack getFrameItemStack() {
        return new ItemStack(ItemInit.QUAD_ITEM_FRAME.get());
    }

    @Override
    public float getVisualRotationYInDegrees() {
        Direction direction = this.getDirection();
        int i = direction.getAxis().isVertical() ? 90 * direction.getAxisDirection().getStep() : 0;
        return (float) Mth.wrapDegrees(180 + direction.get2DDataValue() * 90 + this.getRotation(0) * 45 + i);
    }
}
