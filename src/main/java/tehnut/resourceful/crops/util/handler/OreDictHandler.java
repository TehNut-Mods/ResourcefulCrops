package tehnut.resourceful.crops.util.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.registry.BlockRegistry;
import tehnut.resourceful.crops.registry.ItemRegistry;
import tehnut.resourceful.crops.api.registry.SeedRegistry;

public class OreDictHandler {

    public static void load() {
        registerSeeds();
        registerShards();
        if (ConfigHandler.enableSeedPouches)
            registerPouches();

        OreDictionary.registerOre("oreGaianite", new ItemStack(BlockRegistry.ore, 1, 0));
        OreDictionary.registerOre("oreGaianite", new ItemStack(BlockRegistry.ore, 1, 1));
    }

    private static void registerSeeds() {
        for (Seed seed : SeedRegistry.getSeedList())
            OreDictionary.registerOre("rcropSeed" + seed.getName().replace(" ", ""), new ItemStack(ItemRegistry.seed, 1, SeedRegistry.getIndexOf(seed)));
    }

    private static void registerShards() {
        for (Seed seed : SeedRegistry.getSeedList())
            OreDictionary.registerOre("rcropShard" + seed.getName().replace(" ", ""), new ItemStack(ItemRegistry.shard, 1, SeedRegistry.getIndexOf(seed)));
    }

    private static void registerPouches() {
        for (Seed seed : SeedRegistry.getSeedList())
            OreDictionary.registerOre("rcropPouch" + seed.getName().replace(" ", ""), new ItemStack(ItemRegistry.pouch, 1, SeedRegistry.getIndexOf(seed)));
    }
}
