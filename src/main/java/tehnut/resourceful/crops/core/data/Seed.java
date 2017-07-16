package tehnut.resourceful.crops.core.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public final class Seed extends IForgeRegistryEntry.Impl<Seed> {

    public static final Seed DEFAULT = new Seed("null", 0, 0, Color.BLACK, Collections.emptyList(), new Output[] {}, null, null);

    private final String name;
    private final int tier;
    private final int craftAmount;
    @Nullable
    private Color color;
    private final transient boolean generateColor;
    private final List<ItemStack> inputItems;
    private final Output[] outputs;
    private final GrowthRequirement growthRequirement;
    private final InfoOverride overrides;

    @Nullable
    private String oreName;

    public Seed(String name, int tier, int craftAmount, @Nullable Color color, List<ItemStack> inputItems, Output[] outputs, @Nullable GrowthRequirement growthRequirement, @Nullable InfoOverride overrides) {
        this.name = name;
        this.tier = tier;
        this.craftAmount = craftAmount;
        this.color = color;
        this.generateColor = color == null;
        this.inputItems = inputItems;
        this.outputs = outputs;
        this.growthRequirement = growthRequirement == null ? GrowthRequirement.DEFAULT : growthRequirement;
        this.overrides = overrides == null ? InfoOverride.DEFAULT : overrides;
    }

    public Seed(String name, int tier, int craftAmount, @Nullable Color color, List<ItemStack> inputItems, Output output, @Nullable GrowthRequirement growthRequirement, @Nullable InfoOverride overrides) {
        this(name, tier, craftAmount, color, inputItems, new Output[]{output}, growthRequirement, overrides);
    }

    public Seed(String name, int tier, int craftAmount, @Nullable Color color, ItemStack inputItem, Output output, @Nullable GrowthRequirement growthRequirement, @Nullable InfoOverride overrides) {
        this(name, tier, craftAmount, color, Lists.newArrayList(inputItem), new Output[]{output}, growthRequirement, overrides);
    }

    public Seed(String name, int tier, int craftAmount, @Nullable Color color, String oreName, Output[] output, @Nullable GrowthRequirement growthRequirement, @Nullable InfoOverride overrides) {
        this(name, tier, craftAmount, color, OreDictionary.getOres(oreName), output, growthRequirement, overrides);
    }

    public Seed(String name, int tier, int craftAmount, @Nullable Color color, String oreName, Output output, @Nullable GrowthRequirement growthRequirement, @Nullable InfoOverride overrides) {
        this(name, tier, craftAmount, color, oreName, new Output[]{output}, growthRequirement, overrides);
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
}
