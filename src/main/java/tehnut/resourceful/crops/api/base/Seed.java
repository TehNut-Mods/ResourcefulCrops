package tehnut.resourceful.crops.api.base;

import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.awt.*;

public class Seed {

    private String name;
    private int tier;
    private int amount;
    private boolean nether;
    private String input;
    private ItemStack output;
    private ItemStack secondOutput;
    private ItemStack thirdOutput;
    private Color color;
    private SeedReq seedReq;
    private Chance chance;
    private boolean compatSeed;
    private Compat compat;

    /**
     * To create a seed, use {@link SeedBuilder}
     *
     * @param name         - Name of the seed (Localized or Unlocalized).
     * @param tier         - The tier of the seed. <ul><li>1 - Mundane</li><li>2 - Magical</li><li>3 - Infused</li><li>4 - Arcane</li></ul>
     * @param amount       - Amount of seeds to produce per craft.
     * @param nether       - If true, the crop can only be planted on Soul Sand.
     * @param input        - Input ItemStack or OreDict entry for creating the seeds.
     * @param output       - Output ItemStack or OreDict entry from crafting the shards.
     * @param secondOutput - Secondary output ItemStack or OreDict entry from crafting shards
     * @param thirdOutput  - Third output ItemStack or OreDict entry from crafting shards
     * @param color        - Color of the Seed/Shard/Crop
     * @param seedReq      - Special conditions for Seeds
     * @param chance       - Chances for events to happen
     * @param compatSeed   - Whether or not this seed is for a {@link tehnut.resourceful.crops.api.compat.CompatibilitySeed}
     * @param compat       - The compatibility settings for specific mods.
     */
    protected Seed(String name, int tier, int amount, boolean nether, String input, ItemStack output, @Nullable ItemStack secondOutput, @Nullable ItemStack thirdOutput, Color color, SeedReq seedReq, Chance chance, boolean compatSeed, Compat compat) {
        this.name = name;
        this.tier = tier;
        this.amount = amount;
        this.nether = nether;
        this.input = input;
        this.output = output;
        this.secondOutput = secondOutput;
        this.thirdOutput = thirdOutput;
        this.color = color;
        this.seedReq = seedReq;
        this.chance = chance;
        this.compatSeed = compatSeed;
        this.compat = compat;
    }

    public String getName() {
        return name;
    }

    public int getTier() {
        return tier;
    }

    public int getAmount() {
        return amount;
    }

    public boolean getNether() {
        return nether;
    }

    public String getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public ItemStack getSecondOutput() {
        return secondOutput;
    }

    public ItemStack getThirdOutput() {
        return thirdOutput;
    }

    public Color getColor() {
        return color;
    }

    public SeedReq getSeedReq() {
        return seedReq;
    }

    public Chance getChance() {
        return chance;
    }

    public boolean getCompatSeed() {
        return compatSeed;
    }

    public Compat getCompat() {
        return compat;
    }

    @Override
    public String toString() {
        return "Seed{" +
                "name='" + name + '\'' +
                ", tier=" + tier +
                ", amount=" + amount +
                ", nether=" + nether +
                ", input='" + input + '\'' +
                ", output=" + output +
                ", secondOutput=" + secondOutput +
                ", thirdOutput=" + thirdOutput +
                ", color=" + color +
                ", seedReq=" + seedReq +
                ", chance=" + chance +
                ", compatSeed=" + compatSeed +
                ", compat=" + compat +
                '}';
    }
}
