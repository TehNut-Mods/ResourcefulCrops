package tehnut.resourceful.crops.util.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tehnut.lib.util.helper.BlockHelper;
import tehnut.lib.util.helper.ItemHelper;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.api.registry.SeedRegistry;
import tehnut.resourceful.crops.block.BlockROre;
import tehnut.resourceful.crops.item.ItemPouch;
import tehnut.resourceful.crops.item.ItemSeed;
import tehnut.resourceful.crops.item.ItemShard;

public class OreDictHandler {

    public static void load() {
        registerSeeds();
        registerShards();
        if (ConfigHandler.enableSeedPouches)
            registerPouches();

        OreDictionary.registerOre("oreGaianite", new ItemStack(BlockHelper.getBlock(BlockROre.class), 1, 0));
        OreDictionary.registerOre("oreGaianite", new ItemStack(BlockHelper.getBlock(BlockROre.class), 1, 1));
    }

    private static void registerSeeds() {
        for (Seed seed : ResourcefulAPI.SEEDS.getValues())
            OreDictionary.registerOre("rcropSeed" + seed.getName().replace(" ", ""), new ItemStack(ItemHelper.getItem(ItemSeed.class), 1, ResourcefulAPI.SEEDS.getId(seed)));
    }

    private static void registerShards() {
        for (Seed seed : ResourcefulAPI.SEEDS.getValues())
            OreDictionary.registerOre("rcropShard" + seed.getName().replace(" ", ""), new ItemStack(ItemHelper.getItem(ItemShard.class), 1, ResourcefulAPI.SEEDS.getId(seed)));
    }

    private static void registerPouches() {
        for (Seed seed : ResourcefulAPI.SEEDS.getValues())
            OreDictionary.registerOre("rcropPouch" + seed.getName().replace(" ", ""), new ItemStack(ItemHelper.getItem(ItemPouch.class), 1, ResourcefulAPI.SEEDS.getId(seed)));
    }
}
