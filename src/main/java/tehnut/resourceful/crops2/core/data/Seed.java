package tehnut.resourceful.crops2.core.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class Seed extends IForgeRegistryEntry.Impl<Seed> {

    private final String name;
    private final int tier;
    private final int craftAmount;
    private final Color color;
    private final List<ItemStack> inputItems;
    private final ItemStack[] outputItems;
    private final GrowthRequirement growthRequirement;

    @Nullable
    private String oreName;

    public Seed(String name, int tier, int craftAmount, Color color, List<ItemStack> inputItems, ItemStack[] outputItems, @Nullable GrowthRequirement growthRequirement) {
        this.name = name;
        this.tier = tier;
        this.craftAmount = craftAmount;
        this.color = color;
        this.inputItems = inputItems;
        this.outputItems = outputItems;
        this.growthRequirement = growthRequirement == null ? GrowthRequirement.DEFAULT : growthRequirement;
    }

    public Seed(String name, int tier, int craftAmount, Color color, List<ItemStack> inputItems, ItemStack outputItem, @Nullable GrowthRequirement growthRequirement) {
        this(name, tier, craftAmount, color, inputItems, new ItemStack[]{outputItem}, growthRequirement);
    }

    public Seed(String name, int tier, int craftAmount, Color color, ItemStack inputItem, ItemStack outputItem, @Nullable GrowthRequirement growthRequirement) {
        this(name, tier, craftAmount, color, Lists.newArrayList(inputItem), new ItemStack[]{outputItem}, growthRequirement);
    }

    public Seed(String name, int tier, int craftAmount, Color color, String oreName, ItemStack[] outputItems, @Nullable GrowthRequirement growthRequirement) {
        this(name, tier, craftAmount, color, OreDictionary.getOres(oreName), outputItems, growthRequirement);
    }

    public Seed(String name, int tier, int craftAmount, Color color, String oreName, ItemStack outputItem, @Nullable GrowthRequirement growthRequirement) {
        this(name, tier, craftAmount, color, oreName, new ItemStack[]{outputItem}, growthRequirement);
    }

    public String getName() {
        return name;
    }

    public int getTier() {
        return tier;
    }

    public int getCraftAmount() {
        return craftAmount;
    }

    public Color getColor() {
        return color;
    }

    public List<ItemStack> getInputItems() {
        return ImmutableList.copyOf(inputItems);
    }

    public ItemStack[] getOutputItems() {
        return outputItems;
    }

    public GrowthRequirement getGrowthRequirement() {
        return growthRequirement;
    }

    @Nullable
    public String getOreName() {
        return oreName;
    }

    public void setOreName(@Nullable String oreName) {
        this.oreName = oreName;
    }
}
