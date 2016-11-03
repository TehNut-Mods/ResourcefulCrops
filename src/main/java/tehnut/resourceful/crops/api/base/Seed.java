package tehnut.resourceful.crops.api.base;

import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

@Getter
public class Seed extends IForgeRegistryEntry.Impl<Seed> {

    private String name;
    private int tier;
    private int amount;
    private String input;
    private List<Output> output;
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
     * @param output       - Output List of ItemStacks or OreDict entries from crafting the shards (including recipes).
     * @param secondOutput - Secondary output ItemStack or OreDict entry from crafting shards
     * @param thirdOutput  - Third output ItemStack or OreDict entry from crafting shards
     * @param color        - Color of the Seed/Shard/Crop
     * @param requirement  - Special conditions for Seeds
     * @param chance       - Chances for events to happen
     */
    protected Seed(String name, int tier, int amount, String input, List<Output> output, Color color, Requirement requirement, Chance chance) {
        this.name = name;
        this.tier = tier;
        this.amount = amount;
        this.input = input;
        this.output = output;
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
