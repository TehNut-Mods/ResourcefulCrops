package tehnut.resourceful.crops.util;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import tehnut.resourceful.crops.api.base.Output;
import tehnut.resourceful.crops.api.base.OutputBuilder;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.api.base.SeedBuilder;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StartupUtils {

    private static Map<Integer, Seed> defaultSeeds = new HashMap<Integer, Seed>();
    private static int registered = 0;

    /**
     * Creates a list of default seeds to add to the game.
     */
    public static void initDefaults() {

        // Tier 1
        addDefaultSeedOre(makeSeed("Inky", 1, 4, "dyeBlack", new ItemStack(getOreStack("dyeBlack").getItem(), 8, getOreStack("dyeBlack").getItemDamage()), new Color(22, 22, 22)), "dyeBlack");
        addDefaultSeed(makeSeed("Fleshy", 1, 4, getItemString(Items.ROTTEN_FLESH), new ItemStack(Items.ROTTEN_FLESH, 8), new Color(255, 160, 136)));
        addDefaultSeed(makeSeed("Feathery", 1, 4, getItemString(Items.FEATHER), new ItemStack(Items.FEATHER, 8), new Color(208, 203, 199)));
        // Tier 2
        addDefaultSeedOre(makeSeed("Tin", 2, 4, "ingotTin", new ItemStack(getOreStack("ingotTin").getItem(), 4, Utils.getItemDamage(getOreStack("ingotTin"))), new Color(135, 154, 168)), "ingotTin");
        addDefaultSeedOre(makeSeed("Copper", 2, 4, "ingotCopper", new ItemStack(getOreStack("ingotCopper").getItem(), 4, Utils.getItemDamage(getOreStack("ingotCopper"))), new Color(204, 102, 51)), "ingotCopper");
        addDefaultSeedOre(makeSeed("Aluminum", 2, 4, "ingotAluminum", new ItemStack(getOreStack("ingotAluminum").getItem(), 4, Utils.getItemDamage(getOreStack("ingotAluminum"))), new Color(198, 206, 130)), "ingotAluminum");
        addDefaultSeedOre(makeSeed("Zinc", 2, 4, "ingotZinc", new ItemStack(getOreStack("ingotZinc").getItem(), 4, Utils.getItemDamage(getOreStack("ingotZinc"))), new Color(192, 176, 182)), "ingotZinc");
        addDefaultSeed(makeSeed("Coal", 2, 4, getItemString(Items.COAL), new ItemStack(Items.COAL, 4), new Color(45, 44, 47)));
        addDefaultSeed(makeSeed("Charcoal", 2, 4, getItemString(Items.COAL, 1), new ItemStack(Items.COAL, 4, 1), new Color(45, 44, 47)));
        addDefaultSeedOre(makeSeed("Saltpeter", 2, 4, "dustSaltpeter", new ItemStack(getOreStack("dustSaltpeter").getItem(), 8, Utils.getItemDamage(getOreStack("dustSaltpeter"))), new Color(182, 197, 212)), "dustSaltpeter");
        addDefaultSeedOre(makeSeed("Salt", 2, 4, "dustSalt", new ItemStack(getOreStack("dustSalt").getItem(), 8, Utils.getItemDamage(getOreStack("dustSalt"))), new Color(182, 197, 212)), "dustSalt");
        addDefaultSeed(makeSeed("Leathery", 2, 4, getItemString(Items.LEATHER), new ItemStack(Items.LEATHER, 4), new Color(255, 68, 17)));
        addDefaultSeed(makeSeed("Stringy", 2, 4, getItemString(Items.STRING), new ItemStack(Items.STRING, 8), new Color(241, 255, 210)));
        addDefaultSeed(makeSeed("Boney", 2, 4, getItemString(Items.BONE), new ItemStack(Items.BONE, 4), new Color(255, 240, 205)));
        addDefaultSeed(makeSeed("Slimey", 2, 4, getItemString(Items.SLIME_BALL), new ItemStack(Items.SLIME_BALL, 4), new Color(62, 255, 119)));
        //Tier 3
        addDefaultSeed(makeSeed("Blaze", 3, 4, getItemString(Items.BLAZE_ROD), new ItemStack(Items.BLAZE_ROD, 2), new Color(255, 215, 66)));
        addDefaultSeedOre(makeSeed("Iron", 3, 4, "ingotIron", new ItemStack(getOreStack("ingotIron").getItem(), 4, Utils.getItemDamage(getOreStack("ingotIron"))), new Color(159, 156, 160)), "ingotIron");
        addDefaultSeedOre(makeSeed("Gold", 3, 4, "ingotGold", new ItemStack(getOreStack("ingotGold").getItem(), 4, Utils.getItemDamage(getOreStack("ingotGold"))), new Color(255, 255, 0)), "ingotGold");
        addDefaultSeedOre(makeSeed("Lead", 3, 4, "ingotLead", new ItemStack(getOreStack("ingotLead").getItem(), 4, Utils.getItemDamage(getOreStack("ingotLead"))), new Color(102, 102, 153)), "ingotLead");
        addDefaultSeedOre(makeSeed("Silver", 3, 4, "ingotSilver", new ItemStack(getOreStack("ingotSilver").getItem(), 4, Utils.getItemDamage(getOreStack("ingotSilver"))), new Color(187, 189, 184)), "ingotSilver");
        addDefaultSeedOre(makeSeed("Nickel", 3, 4, "ingotNickel", new ItemStack(getOreStack("ingotNickel").getItem(), 4, Utils.getItemDamage(getOreStack("ingotNickel"))), new Color(204, 204, 204)), "ingotNickel");
        addDefaultSeedOre(makeSeed("Mithril", 3, 4, "ingotMithril", new ItemStack(getOreStack("ingotMithril").getItem(), 4, Utils.getItemDamage(getOreStack("ingotMithril"))), new Color(146, 164, 208)), "ingotMithril");
        addDefaultSeedOre(makeSeed("Osmium", 3, 4, "ingotOsmium", new ItemStack(getOreStack("ingotOsmium").getItem(), 4, Utils.getItemDamage(getOreStack("ingotOsmium"))), new Color(68, 60, 190)), "ingotOsmium");
        addDefaultSeed(makeSeed("Ender", 3, 4, getItemString(Items.ENDER_PEARL), new ItemStack(Items.ENDER_PEARL, 2), new Color(72, 100, 97)));
        addDefaultSeed(makeSeed("Teary", 3, 4, getItemString(Items.GHAST_TEAR), new ItemStack(Items.GHAST_TEAR, 4), new Color(212, 255, 241)));
        addDefaultSeed(makeSeed("Creepy", 3, 4, getItemString(Items.GUNPOWDER), new ItemStack(Items.GUNPOWDER, 8), new Color(0, 255, 33)));
        addDefaultSeed(makeSeed("Skelesprout", 3, 2, getItemString(Items.SKULL), new ItemStack(Items.SKULL, 1), new Color(159, 164, 155)));
        addDefaultSeed(makeSeed("Brainy", 3, 2, getItemString(Items.SKULL, 2), new ItemStack(Items.SKULL, 1, 2), new Color(49, 105, 50)));
        addDefaultSeed(makeSeed("Mindful", 3, 2, getItemString(Items.SKULL, 3), new ItemStack(Items.SKULL, 1, 3), new Color(232, 186, 131)));
        addDefaultSeed(makeSeed("Creepots", 3, 2, getItemString(Items.SKULL, 4), new ItemStack(Items.SKULL, 1, 4), new Color(71, 178, 74)));
        addDefaultSeedOre(makeSeed("Glowstone", 3, 4, "dustGlowstone", new ItemStack(getOreStack("dustGlowstone").getItem(), 8, Utils.getItemDamage(getOreStack("dustGlowstone"))), new Color(233, 255, 84)), "dustGlowstone");
        addDefaultSeedOre(makeSeed("Redstone", 3, 4, "dustRedstone", new ItemStack(getOreStack("dustRedstone").getItem(), 8, Utils.getItemDamage(getOreStack("dustRedstone"))), new Color(159, 13, 0)), "dustRedstone");
        addDefaultSeedOre(makeSeed("Sulfur", 3, 4, "dustSulfur", new ItemStack(getOreStack("dustSulfur").getItem(), 8, Utils.getItemDamage(getOreStack("dustSulfur"))), new Color(212, 190, 85)), "dustSulfur");
        addDefaultSeedOre(makeSeed("Lapis", 3, 4, "gemLapis", new ItemStack(getOreStack("gemLapis").getItem(), 8, Utils.getItemDamage(getOreStack("gemLapis"))), new Color(63, 71, 206)), "gemLapis");
        addDefaultSeedOre(makeSeed("Quartz", 3, 4, "gemQuartz", new ItemStack(getOreStack("gemQuartz").getItem(), 4, Utils.getItemDamage(getOreStack("gemQuartz"))), new Color(255, 255, 255)), "gemQuartz");
        addDefaultSeedOre(makeSeed("Certus", 3, 4, "crystalCertusQuartz", new ItemStack(getOreStack("crystalCertusQuartz").getItem(), 4, Utils.getItemDamage(getOreStack("crystalCertusQuartz"))), new Color(168, 204, 208)), "crystalCertusQuartz");
        addDefaultSeedOre(makeSeed("Ruby", 3, 4, "gemRuby", new ItemStack(getOreStack("gemRuby").getItem(), 8, Utils.getItemDamage(getOreStack("gemRuby"))), new Color(212, 48, 55)), "gemRuby");
        addDefaultSeedOre(makeSeed("Peridot", 3, 4, "gemPeridot", new ItemStack(getOreStack("gemPeridot").getItem(), 8, Utils.getItemDamage(getOreStack("gemPeridot"))), new Color(130, 212, 108)), "gemPeridot");
        addDefaultSeedOre(makeSeed("Topaz", 3, 4, "gemTopaz", new ItemStack(getOreStack("gemTopaz").getItem(), 8, Utils.getItemDamage(getOreStack("gemTopaz"))), new Color(212, 143, 101)), "gemTopaz");
        addDefaultSeedOre(makeSeed("Tanzanite", 3, 4, "gemTanzanite", new ItemStack(getOreStack("gemTanzanite").getItem(), 8, Utils.getItemDamage(getOreStack("gemTanzanite"))), new Color(42, 7, 96)), "gemTanzanite");
        addDefaultSeedOre(makeSeed("Malachite", 3, 4, "gemMalachite", new ItemStack(getOreStack("gemMalachite").getItem(), 8, Utils.getItemDamage(getOreStack("gemMalachite"))), new Color(59, 255, 226)), "gemMalachite");
        addDefaultSeedOre(makeSeed("Sapphire", 3, 4, "gemSapphire", new ItemStack(getOreStack("gemSapphire").getItem(), 8, Utils.getItemDamage(getOreStack("gemSapphire"))), new Color(88, 106, 212)), "gemSapphire");
        addDefaultSeedOre(makeSeed("Amber", 3, 4, "gemAmber", new ItemStack(getOreStack("gemAmber").getItem(), 8, Utils.getItemDamage(getOreStack("gemAmber"))), new Color(212, 121, 60)), "gemAmber");
        addDefaultSeedOre(makeSeed("Apatite", 3, 4, "gemApatite", new ItemStack(getOreStack("gemApatite").getItem(), 8, Utils.getItemDamage(getOreStack("gemApatite"))), new Color(121, 188, 212)), "gemApatite");
        // Tier 4
        addDefaultSeed(makeSeed("Witherwheat", 4, 2, getItemString(Items.SKULL, 1), new ItemStack(Items.SKULL, 1, 1), new Color(52, 52, 51)));
        addDefaultSeedOre(makeSeed("Diamond", 4, 2, "gemDiamond", new ItemStack(getOreStack("gemDiamond").getItem(), 1, Utils.getItemDamage(getOreStack("gemDiamond"))), new Color(58, 242, 239)), "gemDiamond");
        addDefaultSeedOre(makeSeed("Emerald", 4, 2, "gemEmerald", new ItemStack(getOreStack("gemEmerald").getItem(), 1, Utils.getItemDamage(getOreStack("gemEmerald"))), new Color(87, 242, 111)), "gemEmerald");
        addDefaultSeedOre(makeSeed("Platinum", 4, 2, "ingotPlatinum", new ItemStack(getOreStack("ingotPlatinum").getItem(), 1, Utils.getItemDamage(getOreStack("ingotPlatinum"))), new Color(30, 208, 243)), "ingotPlatinum");
        addDefaultSeedOre(makeSeed("Yellorium", 4, 2, "ingotYellorium", new ItemStack(getOreStack("ingotYellorium").getItem(), 2, Utils.getItemDamage(getOreStack("ingotYellorium"))), new Color(142, 160, 19)), "ingotYellorium");
        addDefaultSeedOre(makeSeed("Titanium", 4, 2, "ingotTitanium", new ItemStack(getOreStack("ingotTitanium").getItem(), 1, Utils.getItemDamage(getOreStack("ingotTitanium"))), new Color(212, 165, 182)), "ingotTitanium");
        addDefaultSeedOre(makeSeed("Desh", 4, 2, "ingotDesh", new ItemStack(getOreStack("ingotDesh").getItem(), 1, Utils.getItemDamage(getOreStack("ingotDesh"))), new Color(39, 39, 40)), "ingotDesh");
        addDefaultSeedOre(makeSeed("Cobalt", 4, 2, "ingotCobalt", new ItemStack(getOreStack("ingotCobalt").getItem(), 1, Utils.getItemDamage(getOreStack("ingotCobalt"))), new Color(0, 60, 255)), "ingotCobalt");
        addDefaultSeedOre(makeSeed("Ardite", 4, 2, "ingotArdite", new ItemStack(getOreStack("ingotArdite").getItem(), 1, Utils.getItemDamage(getOreStack("ingotArdite"))), new Color(255, 102, 0)), "ingotArdite");
    }

    /**
     * Builds a seed based on the given parameters
     */
    private static Seed makeSeed(String name, int tier, int amount, String input, ItemStack output, Color color) {
        SeedBuilder builder = new SeedBuilder();

        builder.setName(name);
        builder.setTier(tier);
        builder.setAmount(amount);
        builder.setInput(input);
        builder.setOutput(Arrays.asList(new Output[] {new OutputBuilder().setOutputStack(output).setRecipe("default").build()}));
        builder.setColor(color);

        return builder.build();
    }

    /**
     * Adds a default seed for a OreDict entry
     *
     * @param seed - Seed to add to defaults
     * @param ore  - OreDict entry required to add
     */
    private static void addDefaultSeedOre(Seed seed, String ore) {
        if (OreDictionary.doesOreNameExist(ore) && !OreDictionary.getOres(ore).isEmpty())
            defaultSeeds.put(registered++, seed);
    }

    /**
     * Adds a default seed
     *
     * @param seed - Seed to add to defaults
     */
    private static void addDefaultSeed(Seed seed) {
        String[] split = seed.getInput().split(":");
        if (ForgeRegistries.ITEMS.containsKey(new ResourceLocation(split[0], split[1])))
            defaultSeeds.put(registered++, seed);
    }

    /**
     * Converts an Item into a string with the formatting of:
     * <p/>
     * domain:regname:meta#amount
     *
     * @param item - Item to create a string of
     * @return - A string with the formatting of an ItemStack
     */
    private static String getItemString(Item item) {
        return item.getRegistryName().toString() + ":0#0";
    }

    /**
     * Converts an Item into a string with the formatting of:
     * <p/>
     * domain:regname:meta#amount
     *
     * @param item - Item to create a string of
     * @param meta - The damage value of the item
     * @return - A string with the formatting of an ItemStack
     */
    private static String getItemString(Item item, int meta) {
        return item.getRegistryName().toString() + ":" + meta + "#0";
    }

    /**
     * Provides an ItemStack obtained from a given OreDict entry
     *
     * @param entry - OreDict entry to get the ItemStack of
     * @return - An ItemStack retrieved from the entry
     */
    private static ItemStack getOreStack(String entry) {
        return OreDictionary.doesOreNameExist(entry) && OreDictionary.getOres(entry).size() > 0 ? OreDictionary.getOres(entry).get(0) : new ItemStack(Blocks.FIRE);
    }

    public static Map<Integer, Seed> getDefaultSeeds() {
        return new HashMap<Integer, Seed>(defaultSeeds);
    }
}
