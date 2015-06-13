package tehnut.resourceful.crops.registry;

import com.google.gson.GsonBuilder;
import net.minecraft.item.ItemStack;
import tehnut.resourceful.crops.base.Seed;

import java.util.ArrayList;
import java.util.List;

public class SeedRegistry {

    public static GsonBuilder seedBuilder;
    private static List<Seed> seedList = new ArrayList<Seed>();

    public static void registerSeed(Seed seed) {
        seedList.add(seed);
    }

    public static Seed getSeed(int index) {
        return seedList.get(index);
    }

    public static int getIndexOf(Seed seed) {
        return seedList.indexOf(seed);
    }

    public static int getSize() {
        return seedList.size();
    }

    public static boolean isEmpty() {
        return seedList.isEmpty();
    }

    public static List<Seed> getSeedList() {
        return new ArrayList<Seed>(seedList);
    }

    public static ItemStack getItemStackForSeed(Seed seed) {
        return new ItemStack(ItemRegistry.seed, 1, getIndexOf(seed));
    }
}
