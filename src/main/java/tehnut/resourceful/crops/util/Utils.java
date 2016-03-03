package tehnut.resourceful.crops.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.oredict.OreDictionary;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.api.registry.SeedRegistry;

import java.lang.reflect.Field;
import java.util.Map;

public class Utils {

    /**
     * Takes a string input with a specific formatting and
     * parses it as an ItemStack.
     * <p/>
     * Syntax: domain:regname:meta#amount
     * IE: minecraft:stone:0#8
     *
     * @param stackString - Formatted string
     * @param input       - Whether the string defines an input or not.
     * @return - An ItemStack built from the string
     */
    public static ItemStack parseItemStack(String stackString, boolean input) {
        if (stackString == null)
            return null;

        try {
            if (stackString.contains(":")) {
                String[] nameInfo = stackString.split(":");
                String name = nameInfo[0] + ":" + nameInfo[1];
                String[] stackInfo = nameInfo[2].split("#");
                int meta = Integer.parseInt(stackInfo[0]);
                int amount = Integer.parseInt(stackInfo[1]);

                return new ItemStack(GameData.getItemRegistry().getObject(new ResourceLocation(name)), amount, meta);
            } else if (stackString.equals("null")) {
                return null;
            } else if (!input) {
                String[] stackInfo = stackString.split("#");
                ItemStack oreStack = OreDictionary.getOres(stackInfo[0]).get(0);
                int amount = Integer.parseInt(stackInfo[1]);

                return new ItemStack(oreStack.getItem(), amount, oreStack.getItemDamage());
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ResourcefulAPI.logger.error("Error adding " + (input ? "inputStack" : "outputStack") + ": " + stackString + ". Is it formatted correctly?");
        }

        return null;
    }

    /**
     * Converts an ItemStack into a string with the formatting of:
     * <p/>
     * domain:regname:meta#amount
     * <p/>
     * Basically the inverse of {@code parseItemStack(String, boolean)}
     *
     * @param stack - ItemStack to create a string of
     * @return - A string with the formatting of an ItemStack
     */
    public static String itemStackToString(ItemStack stack) {
        try {
            if (stack != null)
                return GameData.getItemRegistry().getNameForObject(stack.getItem()) + ":" + stack.getItemDamage() + "#" + stack.stackSize;
        } catch (NullPointerException e) {
            // Pokeball!
        }

        return "null";
    }

    /**
     * Provides an invalid version of the given item. Intended to be
     * used as a fallback for if the entry is invalid and returning
     * null would break things.
     *
     * @param item - Item to give an invalid version of
     * @return - Invalid version of the given item
     */
    public static ItemStack getInvalidSeed(Item item) {
        return new ItemStack(item, 1, Short.MAX_VALUE);
    }

    /**
     * Provides the damage value of the given ItemStack.
     * If I ever decide to stop determining things based on
     * damage value, this makes find/replace that much easier.
     * Otherwise it's just a redundant method that's longer than
     * {@code stack.getItemDamage()}
     *
     * @param stack - ItemStack to get the metadata of.
     * @return - Metadata of the given ItemStack
     */
    public static int getItemDamage(ItemStack stack) {

        int ret = 0;

        try {
            ret = stack.getItemDamage();
        } catch (NullPointerException e) {
            // Pokemon!
        }

        return ret;
    }

    /**
     * Determines if the given seed is valid or not based on
     * the index of it.
     *
     * @param seedIndex - Index of the seed to check.
     * @return - Whether or not the seed is valid.
     */
    public static boolean isValidSeed(int seedIndex) {
        return SeedRegistry.getSeed(seedIndex) != null;
    }

    /**
     * Determines if the given seed is valid or not based on
     * the registered name of it.
     *
     * @param seedName - Name of the seed to check.
     * @return - Whether or not the seed is valid.
     */
    public static boolean isValidSeed(String seedName) {
        return SeedRegistry.getSeed(seedName) != null;
    }

    /**
     * Determines if the given seed is valid or not.
     *
     * @param seed - Seed to check.
     * @return - Whether or not the seed is valid.
     */
    public static boolean isValidSeed(Seed seed) {
        return SeedRegistry.getSeedList().contains(seed);
    }

    /**
     * Since {@code OreDictionary.doesOreNameExist(String)} doesn't exist
     * in 1.8, I needed to write my own version. Not as good as the 1.7.10
     * method, but gets the job done.
     *
     * @param entry - OreDict entry to check existence of
     * @return - If the given entry exists.
     */
    @SuppressWarnings("unchecked")
    public static boolean doesOreNameExist(String entry) {
        try {
            Field nameToId = OreDictionary.class.getDeclaredField("nameToId");
            nameToId.setAccessible(true);
            Map<String, Integer> nameToIdMap = (Map<String, Integer>) nameToId.get(null);
            return nameToIdMap.get(entry) != null;
        } catch (NoSuchFieldException e) {
            // Catch 'em all!
        } catch (IllegalAccessException e) {
            // Catch 'em all!
        }
        return false;
    }
}
