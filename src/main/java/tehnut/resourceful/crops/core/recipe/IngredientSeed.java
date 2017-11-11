package tehnut.resourceful.crops.core.recipe;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import tehnut.resourceful.crops.core.RegistrarResourcefulCrops;
import tehnut.resourceful.crops.core.data.Seed;
import tehnut.resourceful.crops.core.data.SeedStack;
import tehnut.resourceful.crops.item.ItemResourceful;

import javax.annotation.Nullable;
import java.util.List;

public class IngredientSeed extends Ingredient {

    private static final SeedStack EMPTY = new SeedStack(RegistrarResourcefulCrops.SEED, RegistrarResourcefulCrops.SEED_DEFAULT, 0);

    private final NonNullList<SeedStack> seeds;
    private IntList itemIds;

    public IngredientSeed(SeedStack... seeds) {
        super(0);
        this.seeds = NonNullList.from(EMPTY, seeds);
    }

    @Override
    public ItemStack[] getMatchingStacks() {
        List<ItemStack> seedStacks = Lists.newArrayList();
        for (SeedStack seed : seeds)
            seedStacks.add(ItemResourceful.getResourcefulStack(seed));
        return seedStacks.toArray(new ItemStack[0]);
    }

    @Override
    public IntList getValidItemStacksPacked() {
        if (this.itemIds == null || this.itemIds.size() != seeds.size()) {
            this.itemIds = new IntArrayList(seeds.size());

            for (ItemStack itemstack : getMatchingStacks())
                this.itemIds.add(RecipeItemHelper.pack(itemstack));

            this.itemIds.sort(IntComparators.NATURAL_COMPARATOR);
        }

        return this.itemIds;
    }

    @Override
    public boolean apply(@Nullable ItemStack input) {
        if (input == null || input.isEmpty() || !(input.getItem() instanceof ItemResourceful))
            return false;

        Seed seed = ((ItemResourceful) input.getItem()).getSeed(input);
        SeedStack seedStack = new SeedStack((ItemResourceful) input.getItem(), seed.getRegistryName(), input.getCount());
        return seeds.contains(seedStack);
    }

    @Override
    protected void invalidate() {
        this.itemIds = null;
    }
}
