package tehnut.resourceful.crops.base;

import net.minecraft.item.ItemStack;

import java.awt.*;

public class Seed {

    private String name;
    private int tier;
    private int amount;
    private String input;
    private ItemStack output;
    private Color color;
    private SeedReq seedReq;
    private boolean compat;

    /**
     * To create a seed, use {@link SeedBuilder}
     *
     * @param name    - Name of the seed (Localized or Unlocalized).
     * @param tier    - The tier of the seed. <ul><li>1 - Mundane</li><li>2 - Magical</li><li>3 - Infused</li><li>4 - Arcane</li></ul>
     * @param amount  - Amount of seeds to produce per craft.
     * @param input   - Input ItemStack or OreDict entry for creating the seeds.
     * @param output  - Output ItemStack or OreDict entry from crafting the shards.
     * @param color   - Color of the Seed/Shard/Crop
     * @param seedReq - Special conditions for Seeds
     * @param compat  - Whether or not this seed is for a {@link tehnut.resourceful.crops.compat.CompatibilitySeed}
     */
    protected Seed(String name, int tier, int amount, String input, ItemStack output, Color color, SeedReq seedReq, boolean compat) {
        this.name = name;
        this.tier = tier;
        this.amount = amount;
        this.input = input;
        this.output = output;
        this.color = color;
        this.seedReq = seedReq;
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

    public String getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public Color getColor() {
        return color;
    }

    public SeedReq getSeedReq() {
        return seedReq;
    }

    public boolean getCompat() {
        return compat;
    }

    @Override
    public String toString() {
        return "Seed{" +
                "name='" + name + '\'' +
                ", tier=" + tier +
                ", amount=" + amount +
                ", input='" + input + '\'' +
                ", output=" + output +
                ", color=" + color +
                ", seedReq=" + seedReq +
                ", compat=" + compat+
                '}';
    }
}
