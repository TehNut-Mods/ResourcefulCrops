package tehnut.resourceful.crops.util;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tehnut.resourceful.crops.base.Seed;
import tehnut.resourceful.crops.registry.SeedRegistry;

public class Utils {

    public static ItemStack parseItemStack(String stackString, boolean input) {
        if (stackString.contains(":")) {
            String[] nameInfo = stackString.split(":");
            String name = nameInfo[0] + ":" + nameInfo[1];
            String[] stackInfo = nameInfo[2].split("#");
            int meta = Integer.parseInt(stackInfo[0]);
            int amount = Integer.parseInt(stackInfo[1]);

            return new ItemStack(GameData.getItemRegistry().getObject(name), amount, meta);
        } else if (!input) {
            String[] stackInfo = stackString.split("#");
            ItemStack oreStack = OreDictionary.getOres(stackInfo[0]).get(0);
            int amount = Integer.parseInt(stackInfo[1]);

            return new ItemStack(oreStack.getItem(), amount, oreStack.getItemDamage());
        }

        return null;
    }

    public static int getItemDamage(ItemStack stack) {
        return stack.getItemDamage();
    }

    public static boolean isValidSeed(int seedIndex) {
        return SeedRegistry.getSeed(seedIndex) != null;
    }

    public static boolean isValidSeed(String seedName) {
        return SeedRegistry.getSeed(seedName) != null;
    }

    public static boolean isValidSeed(Seed seed) {
        return SeedRegistry.getSeedList().contains(seed);
    }

    public static void registerCompat(Class clazz, String modid) {
        if (Loader.isModLoaded(modid)) {
            try {
                Class.forName(clazz.getCanonicalName());
            } catch (ClassNotFoundException e) {
                LogHelper.error("Could not find compatibility class for mod { " + modid + " }. Please report this.");
            }
        }
    }
}
