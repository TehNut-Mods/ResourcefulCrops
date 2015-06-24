package tehnut.resourceful.crops.base;

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
    private String input;
    private ItemStack output;
    private Color color;

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

    public SeedBuilder setInput(String input) {
        this.input = input;
        return this;
    }

    public SeedBuilder setOutput(ItemStack output) {
        this.output = output;
        return this;
    }

    public SeedBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public Seed build() {
        return new Seed(name, tier, amount, input, output, color);
    }
}
