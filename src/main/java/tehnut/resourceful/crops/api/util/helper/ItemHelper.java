package tehnut.resourceful.crops.api.util.helper;

import com.google.common.base.Strings;
import com.google.common.collect.HashBiMap;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.crops.util.helper.LogHelper;

import java.util.Map;

public class ItemHelper {

    public static Map<String, ItemStack> stackCache = HashBiMap.create();

    /**
     * Converts an Item into a string with the formatting of:
     *
     * domain:regname:meta#amount
     *
     * @param item - Item to create a string of
     * @return     - A string with the formatting of an ItemStack
     */
    public static String getItemString(Item item) {
        return GameData.getItemRegistry().getNameForObject(item) + ":0";
    }

    /**
     * Converts an Item into a string with the formatting of:
     *
     * domain:regname:meta#amount
     *
     * @param item - Item to create a string of
     * @param meta - The damage value of the item
     * @return     - A string with the formatting of an ItemStack
     */
    public static String getItemString(Item item, int meta) {
        return GameData.getItemRegistry().getNameForObject(item) + ":" + meta;
    }

    /**
     * Converts an Item into a string with the formatting of:
     *
     * domain:regname:meta#amount
     *
     * @param item   - Item to create a string of
     * @param meta   - The damage value of the item
     * @param amount - The amount of the item
     * @return       - A string with the formatting of an ItemStack
     */
    public static String getItemString(Item item, int meta, int amount) {
        return GameData.getItemRegistry().getNameForObject(item) + ":" + meta + "#" + amount;
    }

    /**
     * Converts an ItemStack into a string with the formatting of:
     *
     * domain:regname:meta#amount
     *
     * @param stack - Stack to convert
     * @return      - A string with the formatting of an ItemStack
     */
    public static String getItemString(ItemStack stack) {
        return stack != null ? GameData.getItemRegistry().getNameForObject(stack.getItem()) + ":" + stack.getItemDamage() + "#" + stack.stackSize : "null";
    }

    /**
     * Provides an ItemStack obtained from a given OreDict entry
     *
     * @param entry - OreDict entry to get the ItemStack of
     * @return      - An ItemStack retrieved from the entry
     */
    public static ItemStack getOreStack(String entry) {
        if (OreDictionary.getOreNames().length != 0 && OreDictionary.doesOreNameExist(entry))
            if (OreDictionary.getOres(entry).size() != 0)
                return OreDictionary.getOres(entry).get(0);

        return new ItemStack(Blocks.fire);
    }

    /**
     * Takes a string input with a specific formatting and
     * parses it as an ItemStack.
     *
     * Syntax: domain:regname:meta#amount
     * IE: {@code minecraft:stone:0#8}
     *
     * @param stackString - Formatted string
     * @param input       - Whether the string defines an input or not.
     * @return            - An ItemStack built from the string
     */
    public static ItemStack parseItemStack(String stackString, boolean input) {
        if (Strings.isNullOrEmpty(stackString))
            return null;

        try {
            if (stackString.contains(":")) {
                String[] nameInfo = stackString.split(":");
                String name = nameInfo[0] + ":" + nameInfo[1];
                String[] stackInfo = nameInfo[2].split("#");
                int meta = Integer.parseInt(stackInfo[0]);
                int amount = Integer.parseInt(stackInfo[1]);

                ItemStack ret = new ItemStack(GameData.getItemRegistry().getObject(name), amount, meta);
                stackCache.put(stackString, ret);
                return ret;
            } else if (!input) {
                String[] stackInfo = stackString.split("#");
                ItemStack oreStack = getOreStack(stackInfo[0]);
                int amount = Integer.parseInt(stackInfo[1]);

                ItemStack ret = new ItemStack(oreStack.getItem(), amount, oreStack.getItemDamage());
                stackCache.put(stackString, ret);
                return ret;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ResourcefulAPI.logger.info("Error adding " + (input ? "inputStack" : "outputStack") + ": " + stackString + ". Is it formatted correctly?");
        }

        return null;
    }
}
