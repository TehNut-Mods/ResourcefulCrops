package tehnut.resourceful.crops.util;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
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

//    public static ItemStack setSeedIndex(ItemStack stack, int seedIndex) {
//        if (stack.stackTagCompound == null)
//            stack.stackTagCompound = new NBTTagCompound();
//
//        stack.stackTagCompound.setInteger("seedIndex", seedIndex);
//
//        return stack;
//    }
//
//    public static int getSeedIndex(ItemStack stack) {
//        if (stack.stackTagCompound == null)
//            stack.stackTagCompound = new NBTTagCompound();
//
//        return stack.stackTagCompound.getInteger("seedIndex");
//    }

    public static boolean isValidSeed(int seedIndex) {
        return !SeedRegistry.isEmpty() && !(SeedRegistry.getSize() < seedIndex);
    }

    public static boolean isValidSeed(ItemStack stack) {
        return isValidSeed(getItemDamage(stack));
    }
}
