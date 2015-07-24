package tehnut.resourceful.crops.registry;

import com.google.gson.GsonBuilder;
import net.minecraft.item.ItemStack;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.util.helper.LogHelper;

import java.util.ArrayList;
import java.util.List;

public class SeedRegistry {

    public static GsonBuilder seedBuilder;
    public static ArrayList<Seed> seedList;
    public static int badSeeds = 0;

    public static void registerSeed(Seed seed) {
        try {
            ResourcefulCrops.getSeedCache().addObject(seed, !seed.getCompat() ? seed.getName() : seed.getName() + "-Compat");
        } catch (IllegalArgumentException e) {
            if (ConfigHandler.forceAddDuplicates) {
                LogHelper.error("Seed { " + seed.getName() + " } has been registered twice. Force adding the copy.");
                ResourcefulCrops.getSeedCache().addObject(seed, seed.getName() + badSeeds);
            } else {
                LogHelper.error("Seed { " + seed.getName() + " } has been registered twice. Skipping the copy and continuing.");
            }
            badSeeds++;
        }
    }

    public static Seed getSeed(int index) {
        return ResourcefulCrops.getSeedCache().getObject(index);
    }

    public static Seed getSeed(String name) {
        return ResourcefulCrops.getSeedCache().getObject(name);
    }

    public static int getIndexOf(Seed seed) {
        return ResourcefulCrops.getSeedCache().getID(seed);
    }

    public static int getIndexOf(String name) {
        return ResourcefulCrops.getSeedCache().getID(getSeed(name));
    }

    public static String getNameOf(Seed seed) {
        return ResourcefulCrops.getSeedCache().getName(seed);
    }

    public static int getSize() {
        return getSeedList().size();
    }

    public static boolean isEmpty() {
        return getSeedList().isEmpty();
    }

    public static List<Seed> getSeedList() {
        return new ArrayList<Seed>(seedList);
    }

    public static void setSeedList(ArrayList<Seed> list) {
        seedList = list;
    }

    public static ItemStack getItemStackForSeed(Seed seed) {
        return new ItemStack(ItemRegistry.seed, 1, getIndexOf(seed));
    }
}
