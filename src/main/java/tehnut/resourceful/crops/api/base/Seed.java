package tehnut.resourceful.crops.api.base;

import lombok.Getter;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.awt.*;

@Getter
public class Seed {

    private String name;
    private int tier;
    private int amount;
    private String input;
    private ItemStack output;
    @Nullable
    private ItemStack secondOutput;
    @Nullable
    private ItemStack thirdOutput;
    private Color color;
    private Requirement requirement;
    private Chance chance;

    /**
     * To create a seed, use {@link SeedBuilder}
     *
     * @param name         - Name of the seed (Localized or Unlocalized).
     * @param tier         - The tier of the seed. <ul><li>1 - Mundane</li><li>2 - Magical</li><li>3 - Infused</li><li>4 - Arcane</li></ul>
     * @param amount       - Amount of seeds to produce per craft.
     * @param input        - Input ItemStack or OreDict entry for creating the seeds.
     * @param output       - Output ItemStack or OreDict entry from crafting the shards.
     * @param secondOutput - Secondary output ItemStack or OreDict entry from crafting shards
     * @param thirdOutput  - Third output ItemStack or OreDict entry from crafting shards
     * @param color        - Color of the Seed/Shard/Crop
     * @param requirement      - Special conditions for Seeds
     * @param chance       - Chances for events to happen
     */
    protected Seed(String name, int tier, int amount, String input, ItemStack output, ItemStack secondOutput, ItemStack thirdOutput, Color color, Requirement requirement, Chance chance) {
        this.name = name;
        this.tier = tier;
        this.amount = amount;
        this.input = input;
        this.output = output;
        this.secondOutput = secondOutput;
        this.thirdOutput = thirdOutput;
        this.color = color;
        this.requirement = requirement;
        this.chance = chance;
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
                ", seedReq=" + requirement +
                ", chance=" + chance +
                '}';
    }
}
