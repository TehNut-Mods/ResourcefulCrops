package tehnut.resourceful.crops.api.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.item.ItemStack;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.crops.api.base.Seed;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SeedRegistry {

//    public static BiMap<Integer, Seed> seedMap = HashBiMap.create();
//    public static BiMap<String, Seed> seedNameMap = HashBiMap.create();
//    public static List<Seed> seedList;
//    public static int badSeeds = 0;
//
//    @Nullable
//    public static Seed getSeed(int index) {
//        return seedMap.get(index);
//    }
//
//    @Nullable
//    public static Seed getSeed(String name) {
//        return seedNameMap.get(name);
//    }
//
//    public static int getIndexOf(Seed seed) {
//        return seedMap.inverse().get(seed);
//    }
//
//    public static int getIndexOf(String name) {
//        return getIndexOf(seedNameMap.get(name));
//    }
//
//    public static String getNameOf(Seed seed) {
//        return seedNameMap.inverse().get(seed);
//    }
//
//    public static int getSize() {
//        return getSeedList().size();
//    }
//
//    public static boolean isEmpty() {
//        return getSeedList().isEmpty();
//    }
//
//    public static List<Seed> getSeedList() {
//        if (seedList == null)
//            seedList = new ArrayList<Seed>(seedMap.values());
//
//        return seedList;
//    }
//
//    public static BiMap<Integer, Seed> getSeedMap() {
//        return HashBiMap.create(seedMap);
//    }
//
//    public static BiMap<String, Seed> getSeedNameMap() {
//        return HashBiMap.create(seedNameMap);
//    }
//
//
//    public static ItemStack getItemStackForSeed(Seed seed) {
//        return new ItemStack(ResourcefulAPI.getItem(ResourcefulAPI.SEED), 1, getIndexOf(seed));
//    }
}
