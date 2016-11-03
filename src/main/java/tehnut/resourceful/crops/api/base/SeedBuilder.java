package tehnut.resourceful.crops.api.base;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.item.ItemStack;

import java.awt.Color;
import java.util.List;

/**
 * Factory for creating Seeds/Crops/Shards.
 * Documentation for each field can be found in {@link Seed}
 */
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class SeedBuilder {

    private String name;
    private int tier;
    private int amount;
    private String input;
    private List<Output> output;
    private Color color;
    private Requirement requirement = new RequirementBuilder().build();
    private Chance chance = new ChanceBuilder().build();

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
        this.amount = amount > 64 ? 64 : amount;
        return this;
    }

    public Seed build() {
        return new Seed(name, tier, amount, input, output, color, requirement, chance);
    }
}
