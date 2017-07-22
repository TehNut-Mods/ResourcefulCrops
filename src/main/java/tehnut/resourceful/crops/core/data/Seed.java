package tehnut.resourceful.crops.core.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;
import tehnut.resourceful.crops.core.RegistrarResourcefulCrops;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public final class Seed extends IForgeRegistryEntry.Impl<Seed> {

    public static final Seed DEFAULT = new Seed("null", 0, 0, false, Color.BLACK, Collections.emptyList(), new Output[] {}, null, null);

    private final String name;
    private final int tier;
    private final int craftAmount;
    private final boolean canFertilize;
    @Nullable
    private Color color;
    private final transient boolean generateColor;
    private final List<ItemStack> inputItems;
    private final Output[] outputs;
    private final GrowthRequirement growthRequirement;
    private final InfoOverride overrides;

    @Nullable
    private String oreName;

    public Seed(String name, int tier, int craftAmount, boolean canFertilize, @Nullable Color color, List<ItemStack> inputItems, Output[] outputs, @Nullable GrowthRequirement growthRequirement, @Nullable InfoOverride overrides) {
        this.name = name;
        this.tier = tier;
        this.craftAmount = craftAmount;
        this.canFertilize = canFertilize;
        this.color = color;
        this.generateColor = color == null;
        this.inputItems = inputItems;
        this.outputs = outputs;
        this.growthRequirement = growthRequirement == null ? GrowthRequirement.DEFAULT : growthRequirement;
        this.overrides = overrides == null ? InfoOverride.DEFAULT : overrides;
    }

    public Seed(String name, int tier, int craftAmount, boolean canFertilize, @Nullable Color color, List<ItemStack> inputItems, Output output, @Nullable GrowthRequirement growthRequirement, @Nullable InfoOverride overrides) {
        this(name, tier, craftAmount, canFertilize, color, inputItems, new Output[]{output}, growthRequirement, overrides);
    }

    public Seed(String name, int tier, int craftAmount, boolean canFertilize, @Nullable Color color, ItemStack inputItem, Output output, @Nullable GrowthRequirement growthRequirement, @Nullable InfoOverride overrides) {
        this(name, tier, craftAmount, canFertilize, color, Lists.newArrayList(inputItem), new Output[]{output}, growthRequirement, overrides);
    }

    public Seed(String name, int tier, int craftAmount, boolean canFertilize, @Nullable Color color, String oreName, Output[] output, @Nullable GrowthRequirement growthRequirement, @Nullable InfoOverride overrides) {
        this(name, tier, craftAmount, canFertilize, color, OreDictionary.getOres(oreName), output, growthRequirement, overrides);
    }

    public Seed(String name, int tier, int craftAmount, boolean canFertilize, @Nullable Color color, String oreName, Output output, @Nullable GrowthRequirement growthRequirement, @Nullable InfoOverride overrides) {
        this(name, tier, craftAmount, canFertilize, color, oreName, new Output[]{output}, growthRequirement, overrides);
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

    public boolean canFertilize() {
        return canFertilize;
    }

    @Nullable
    public Color getColor() {
        return color;
    }

    public void setColor(@Nullable Color color) {
        this.color = color;
    }

    public boolean shouldGenerateColor() {
        return generateColor;
    }

    public List<ItemStack> getInputItems() {
        return ImmutableList.copyOf(inputItems);
    }

    public Output[] getOutputs() {
        return outputs;
    }

    public GrowthRequirement getGrowthRequirement() {
        return growthRequirement;
    }

    @Nonnull
    public InfoOverride getOverrides() {
        return overrides;
    }

    @Nullable
    public String getOreName() {
        return oreName;
    }

    public void setOreName(@Nullable String oreName) {
        this.oreName = oreName;
    }

    public boolean isNull() {
        return RegistrarResourcefulCrops.SEED_DEFAULT.equals(getRegistryName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seed)) return false;

        Seed seed = (Seed) o;

        return getRegistryName() != null ? getRegistryName().equals(seed.getRegistryName()) : seed.getRegistryName() == null;
    }

    @Override
    public int hashCode() {
        return getRegistryName() != null ? getRegistryName().hashCode() : 0;
    }
}
