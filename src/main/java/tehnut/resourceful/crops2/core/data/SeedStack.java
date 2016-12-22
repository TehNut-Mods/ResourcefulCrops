package tehnut.resourceful.crops2.core.data;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tehnut.resourceful.crops2.item.ItemResourceful;

public class SeedStack {

    private final ItemResourceful type;
    private final ResourceLocation seed;
    private final int amount;

    public SeedStack(ItemResourceful type, ResourceLocation seed, int amount) {
        this.type = type;
        this.seed = seed;
        this.amount = amount;
    }

    public SeedStack(ItemResourceful type, ResourceLocation seed) {
        this(type, seed, 1);
    }

    public SeedStack(ItemResourceful type, Seed seed, int amount) {
        this(type, seed.getRegistryName(), amount);
    }

    public SeedStack(ItemResourceful type, Seed seed) {
        this(type, seed, 1);
    }

    public ItemResourceful getType() {
        return type;
    }

    public ResourceLocation getSeedKey() {
        return seed;
    }

    public Seed getSeed() {
        return GameRegistry.findRegistry(Seed.class).getValue(seed);
    }

    public int getAmount() {
        return amount;
    }
}
