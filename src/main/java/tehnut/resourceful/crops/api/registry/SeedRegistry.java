package tehnut.resourceful.crops.api.registry;

import com.google.gson.GsonBuilder;
import net.minecraft.item.ItemStack;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.crops.api.base.Seed;

import java.util.ArrayList;
import java.util.List;

public class SeedRegistry {

    public static GsonBuilder seedBuilder;
    public static ArrayList<Seed> seedList;
    public static int badSeeds = 0;

    public static void registerSeed(Seed seed) {
        try {
            ResourcefulAPI.seedCache.addObject(seed, !seed.getCompat() ? seed.getName() : seed.getName() + "-Compat");
        } catch (IllegalArgumentException e) {
            if (ResourcefulAPI.forceAddDuplicates) {
                ResourcefulAPI.logger.error("Seed { " + seed.getName() + " } has been registered twice. Force adding the copy.");
                ResourcefulAPI.seedCache.addObject(seed, seed.getName() + badSeeds);
            } else {
                ResourcefulAPI.logger.error("Seed { " + seed.getName() + " } has been registered twice. Skipping the copy and continuing.");
            }
            badSeeds++;
        }
    }

    public static Seed getSeed(int index) {
        return ResourcefulAPI.seedCache.getObject(index);
    }

    public static Seed getSeed(String name) {
        return ResourcefulAPI.seedCache.getObject(name);
    }

    public static int getIndexOf(Seed seed) {
        return ResourcefulAPI.seedCache.getID(seed);
    }

    public static int getIndexOf(String name) {
        return ResourcefulAPI.seedCache.getID(getSeed(name));
    }

    public static String getNameOf(Seed seed) {
        return ResourcefulAPI.seedCache.getName(seed);
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
        return new ItemStack(ResourcefulAPI.getItem(ResourcefulAPI.SEED), 1, getIndexOf(seed));
    }
}
