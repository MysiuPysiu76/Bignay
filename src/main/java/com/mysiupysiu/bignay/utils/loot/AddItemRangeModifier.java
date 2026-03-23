package com.mysiupysiu.bignay.utils.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class AddItemRangeModifier extends LootModifier {

    public static final Supplier<Codec<AddItemRangeModifier>> CODEC = () -> RecordCodecBuilder.create(inst ->
            codecStart(inst).and(inst.group(
                    ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(m -> m.item),
                    Codec.FLOAT.fieldOf("chance").forGetter(m -> m.chance),
                    Codec.INT.fieldOf("min").forGetter(m -> m.minCount),
                    Codec.INT.fieldOf("max").forGetter(m -> m.maxCount)
            )).apply(inst, AddItemRangeModifier::new));

    private final Item item;
    private final float chance;
    private final int minCount;
    private final int maxCount;

    public AddItemRangeModifier(LootItemCondition[] conditionsIn, Item item, float chance, int min, int max) {
        super(conditionsIn);
        this.item = item;
        this.chance = chance;
        this.minCount = min;
        this.maxCount = max;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (context.getRandom().nextFloat() <= this.chance) {
            int count = minCount;
            if (maxCount > minCount) {
                count = context.getRandom().nextInt(maxCount - minCount + 1) + minCount;
            }
            generatedLoot.add(new ItemStack(this.item, count));
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
