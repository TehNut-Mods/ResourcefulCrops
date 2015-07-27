package tehnut.resourceful.crops.api.base;

import net.minecraft.item.ItemStack;

import java.awt.*;

/**
 * Factory for creating Seeds/Crops/Shards.
 * Documentation for each field can be found in {@link Seed}
 */
public class SeedBuilder {

    private String name;
    private int tier;
    private int amount;
    private boolean nether = false;
    private String input;
    private ItemStack output;
    private ItemStack secondOutput = null;
    private ItemStack thirdOutput = null;
    private Color color;
    private SeedReq seedReq = new SeedReqBuilder().build();
    private Chance chance = new ChanceBuilder().build();
    private boolean compat = false;

    public SeedBuilder() {

    }

    public SeedBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public SeedBuilder setTier(int tier) {
        if (tier > 4)
            this.tier = 4;
        else if (tier < 1)
            this.tier = 1;
        else
            this.tier = tier;
        return this;
    }

    public SeedBuilder setAmount(int amount) {
        if (amount > 64)
            this.amount = 64;
        else
            this.amount = amount;
        return this;
    }

    public SeedBuilder setNether(boolean nether) {
        this.nether = nether;
        return this;
    }

    public SeedBuilder setInput(String input) {
        this.input = input;
        return this;
    }

    public SeedBuilder setOutput(ItemStack output) {
        this.output = output;
        return this;
    }

    public SeedBuilder setSecondOutput(ItemStack secondOutput) {
        this.secondOutput = secondOutput;
        return this;
    }

    public SeedBuilder setThirdOutput(ItemStack thirdOutput) {
        this.thirdOutput = thirdOutput;
        return this;
    }

    public SeedBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public SeedBuilder setSeedReq(SeedReq seedReq) {
        this.seedReq = seedReq;
        return this;
    }

    public SeedBuilder setChance(Chance chance) {
        this.chance = chance;
        return this;
    }

    public SeedBuilder setCompat(boolean compat) {
        this.compat = compat;
        return this;
    }

    public Seed build() {
        return new Seed(name, tier, amount, nether, input, output, secondOutput, thirdOutput, color, seedReq, chance, compat);
    }
}
