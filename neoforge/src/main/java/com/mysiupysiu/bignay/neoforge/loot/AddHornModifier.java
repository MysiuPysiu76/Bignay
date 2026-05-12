package com.mysiupysiu.bignay.neoforge.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class AddHornModifier extends LootModifier {

    public static final Codec<AddHornModifier> CODEC = RecordCodecBuilder.create(inst -> codecStart(inst).and(inst.group(BuiltInRegistries.ITEM.byNameCodec().fieldOf("id").forGetter(m -> m.item), Codec.STRING.fieldOf("instrument").forGetter(m -> m.instrument), Codec.FLOAT.fieldOf("chance").forGetter(m -> m.chance))).apply(inst, AddHornModifier::new));

    private final Item item;
    private final String instrument;
    private final float chance;

    public AddHornModifier(LootItemCondition[] conditionsIn, Item item, String instrument, float chance) {
        super(conditionsIn);
        this.item = item;
        this.instrument = instrument;
        this.chance = chance;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (context.getRandom().nextFloat() <= this.chance) {
            ItemStack stack = new ItemStack(this.item);

            CompoundTag tag = new CompoundTag();
            tag.putString("instrument", this.instrument);
            stack.setTag(tag);

            // For the future for +1.21
            // stack.set(DataComponents.INSTRUMENT, BuiltInRegistries.INSTRUMENT.getHolder(ResourceLocation.parse(this.instrument)).get());

            generatedLoot.add(stack);
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
