package tehnut.resourceful.crops.util;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.base.Seed;
import tehnut.resourceful.crops.registry.SeedRegistry;
import tehnut.resourceful.crops.util.serialization.SeedCreator;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StartupUtils {

    private static List<Seed> defaultSeeds = new ArrayList<Seed>();

    public static void initDefaults() {

        // Tier 0
        addDefaultSeedOre(new Seed("Inky", 1, 4, "dyeBlack", new ItemStack(getOreStack("dyeBlack").getItem(), 8, getOreStack("dyeBlack").getItemDamage()), new Color(22, 22, 22)), "dyeBlack");
        addDefaultSeed(new Seed("Fleshy", 1, 4, getItemString(Items.rotten_flesh), new ItemStack(Items.rotten_flesh, 8), new Color(255, 160, 136)));
        addDefaultSeed(new Seed("Feathery", 1, 4, getItemString(Items.feather), new ItemStack(Items.feather, 8), new Color(208, 203, 199)));
        // Tier 1
        addDefaultSeedOre(new Seed("Tin", 2, 4, "ingotTin", new ItemStack(getOreStack("ingotTin").getItem(), 8, getOreStack("ingotTin").getItemDamage()), new Color(135, 154, 168)), "ingotTin");
        addDefaultSeedOre(new Seed("Copper", 2, 4, "ingotCopper", new ItemStack(getOreStack("ingotCopper").getItem(), 8, getOreStack("ingotCopper").getItemDamage()), new Color(204, 102, 51)), "ingotCopper");
        addDefaultSeedOre(new Seed("Aluminum", 2, 4, "ingotAluminum", new ItemStack(getOreStack("ingotAluminum").getItem(), 8, getOreStack("ingotAluminum").getItemDamage()), new Color(198, 206, 130)), "ingotAluminum");
        addDefaultSeedOre(new Seed("Zinc", 2, 4, "ingotZinc", new ItemStack(getOreStack("ingotZinc").getItem(), 8, getOreStack("ingotZinc").getItemDamage()), new Color(192, 176, 182)), "ingotZinc");
        addDefaultSeed(new Seed("Coal", 2, 4, getItemString(Items.coal), new ItemStack(Items.coal, 8), new Color(45, 44, 47)));
        addDefaultSeed(new Seed("Charcoal", 2, 4, getItemString(Items.coal, 1), new ItemStack(Items.coal, 8, 1), new Color(45, 44, 47)));
        addDefaultSeedOre(new Seed("Saltpeter", 2, 4, "dustSaltpeter", new ItemStack(getOreStack("dustSaltpeter").getItem(), 8, getOreStack("dustSaltpeter").getItemDamage()), new Color(182, 197, 212)), "dustSaltpeter");
        addDefaultSeedOre(new Seed("Salt", 2, 4, "dustSalt", new ItemStack(getOreStack("dustSalt").getItem(), 8, getOreStack("dustSalt").getItemDamage()), new Color(182, 197, 212)), "dustSalt");
        addDefaultSeed(new Seed("Leathery", 2, 4, getItemString(Items.leather), new ItemStack(Items.leather, 8), new Color(255, 68, 17)));
        addDefaultSeed(new Seed("Stringy", 2, 4, getItemString(Items.string), new ItemStack(Items.string, 8), new Color(241, 255, 210)));
        addDefaultSeed(new Seed("Boney", 2, 4, getItemString(Items.bone), new ItemStack(Items.bone, 8), new Color(255, 240, 205)));
        addDefaultSeed(new Seed("Slimey", 2, 4, getItemString(Items.slime_ball), new ItemStack(Items.slime_ball, 8), new Color(62, 255, 119)));
        //Tier 2
        addDefaultSeed(new Seed("Blaze", 3, 4, getItemString(Items.blaze_rod), new ItemStack(Items.blaze_rod, 8), new Color(255, 215, 66)));
        addDefaultSeedOre(new Seed("Iron", 3, 4, "ingotIron", new ItemStack(getOreStack("ingotIron").getItem(), 8, getOreStack("ingotIron").getItemDamage()), new Color(159, 156, 160)), "ingotIron");
        addDefaultSeedOre(new Seed("Gold", 3, 4, "ingotGold", new ItemStack(getOreStack("ingotGold").getItem(), 8, getOreStack("ingotGold").getItemDamage()), new Color(255, 255, 0)), "ingotGold");
        addDefaultSeedOre(new Seed("Lead", 3, 4, "ingotLead", new ItemStack(getOreStack("ingotLead").getItem(), 8, getOreStack("ingotLead").getItemDamage()), new Color(102, 102, 153)), "ingotLead");
        addDefaultSeedOre(new Seed("Silver", 3, 4, "ingotSilver", new ItemStack(getOreStack("ingotSilver").getItem(), 8, getOreStack("ingotSilver").getItemDamage()), new Color(187, 189, 184)), "ingotSilver");
        addDefaultSeedOre(new Seed("Nickel", 3, 4, "ingotNickel", new ItemStack(getOreStack("ingotNickel").getItem(), 8, getOreStack("ingotNickel").getItemDamage()), new Color(204, 204, 204)), "ingotNickel");
        addDefaultSeedOre(new Seed("Mithril", 3, 4, "ingotMithril", new ItemStack(getOreStack("ingotMithril").getItem(), 8, getOreStack("ingotMithril").getItemDamage()), new Color(146, 164, 208)), "ingotMithril");
        addDefaultSeedOre(new Seed("Osmium", 3, 4, "ingotOsmium", new ItemStack(getOreStack("ingotOsmium").getItem(), 8, getOreStack("ingotOsmium").getItemDamage()), new Color(68, 60, 190)), "ingotOsmium");
        addDefaultSeed(new Seed("Ender", 3, 4, getItemString(Items.ender_pearl), new ItemStack(Items.ender_pearl, 8), new Color(72, 100, 97)));
        addDefaultSeed(new Seed("Teary", 3, 4, getItemString(Items.ghast_tear), new ItemStack(Items.ghast_tear, 8), new Color(212, 255, 241)));
        addDefaultSeed(new Seed("Creepy", 3, 4, getItemString(Items.gunpowder), new ItemStack(Items.gunpowder, 8), new Color(0, 255, 33)));
        addDefaultSeedOre(new Seed("Glowstone", 3, 4, "dustGlowstone", new ItemStack(getOreStack("dustGlowstone").getItem(), 8, getOreStack("dustGlowstone").getItemDamage()), new Color(233, 255, 84)), "dustGlowstone");
        addDefaultSeedOre(new Seed("Redstone", 3, 4, "dustRedstone", new ItemStack(getOreStack("dustRedstone").getItem(), 8, getOreStack("dustRedstone").getItemDamage()), new Color(159, 13, 0)), "dustRedstone");
        addDefaultSeedOre(new Seed("Sulfur", 3, 4, "dustSulfur", new ItemStack(getOreStack("dustSulfur").getItem(), 8, getOreStack("dustSulfur").getItemDamage()), new Color(212, 190, 85)), "dustSulfur");
        addDefaultSeedOre(new Seed("Lapis", 3, 4, "gemLapis", new ItemStack(getOreStack("gemLapis").getItem(), 8, getOreStack("gemLapis").getItemDamage()), new Color(63, 71, 206)), "gemLapis");
        addDefaultSeedOre(new Seed("Quartz", 3, 4, "gemQuartz", new ItemStack(getOreStack("gemQuartz").getItem(), 8, getOreStack("gemQuartz").getItemDamage()), new Color(255, 255, 255)), "gemQuartz");
        addDefaultSeedOre(new Seed("Certus", 3, 4, "crystalCertusQuartz", new ItemStack(getOreStack("crystalCertusQuartz").getItem(), 8, getOreStack("crystalCertusQuartz").getItemDamage()), new Color(168, 204, 208)), "crystalCertusQuartz");
        addDefaultSeedOre(new Seed("Ruby", 3, 4, "gemRuby", new ItemStack(getOreStack("gemRuby").getItem(), 8, getOreStack("gemRuby").getItemDamage()), new Color(212, 48, 55)), "gemRuby");
        addDefaultSeedOre(new Seed("Peridot", 3, 4, "gemPeridot", new ItemStack(getOreStack("gemPeridot").getItem(), 8, getOreStack("gemPeridot").getItemDamage()), new Color(130, 212, 108)), "gemPeridot");
        addDefaultSeedOre(new Seed("Topaz", 3, 4, "gemTopaz", new ItemStack(getOreStack("gemTopaz").getItem(), 8, getOreStack("gemTopaz").getItemDamage()), new Color(212, 143, 101)), "gemTopaz");
        addDefaultSeedOre(new Seed("Tanzanite", 3, 4, "gemTanzanite", new ItemStack(getOreStack("gemTanzanite").getItem(), 8, getOreStack("gemTanzanite").getItemDamage()), new Color(42, 7, 96)), "gemTanzanite");
        addDefaultSeedOre(new Seed("Malachite", 3, 4, "gemMalachite", new ItemStack(getOreStack("gemMalachite").getItem(), 8, getOreStack("gemMalachite").getItemDamage()), new Color(59, 255, 226)), "gemMalachite");
        addDefaultSeedOre(new Seed("Sapphire", 3, 4, "gemSapphire", new ItemStack(getOreStack("gemSapphire").getItem(), 8, getOreStack("gemSapphire").getItemDamage()), new Color(88, 106, 212)), "gemSapphire");
        addDefaultSeedOre(new Seed("Amber", 3, 4, "gemAmber", new ItemStack(getOreStack("gemAmber").getItem(), 8, getOreStack("gemAmber").getItemDamage()), new Color(212, 121, 60)), "gemAmber");
        addDefaultSeedOre(new Seed("Apatite", 3, 4, "gemApatite", new ItemStack(getOreStack("gemApatite").getItem(), 8, getOreStack("gemApatite").getItemDamage()), new Color(121, 188, 212)), "gemApatite");
        // Tier 3
        addDefaultSeedOre(new Seed("Diamond", 4, 4, "gemDiamond", new ItemStack(getOreStack("gemDiamond").getItem(), 8, getOreStack("gemDiamond").getItemDamage()), new Color(58, 242, 239)), "gemDiamond");
        addDefaultSeedOre(new Seed("Emerald", 4, 4, "gemEmerald", new ItemStack(getOreStack("gemEmerald").getItem(), 8, getOreStack("gemEmerald").getItemDamage()), new Color(87, 242, 111)), "gemEmerald");
        addDefaultSeedOre(new Seed("Platinum", 4, 4, "ingotPlatinum", new ItemStack(getOreStack("ingotPlatinum").getItem(), 8, getOreStack("ingotPlatinum").getItemDamage()), new Color(30, 208, 243)), "ingotPlatinum");
        addDefaultSeedOre(new Seed("Yellorium", 4, 4, "ingotYellorium", new ItemStack(getOreStack("ingotYellorium").getItem(), 8, getOreStack("ingotYellorium").getItemDamage()), new Color(142, 160, 19)), "ingotYellorium");
        addDefaultSeedOre(new Seed("Titanium", 4, 4, "ingotTitanium", new ItemStack(getOreStack("ingotTitanium").getItem(), 8, getOreStack("ingotTitanium").getItemDamage()), new Color(212, 165, 182)), "ingotTitanium");
        addDefaultSeedOre(new Seed("Desh", 4, 4, "ingotDesh", new ItemStack(getOreStack("ingotDesh").getItem(), 8, getOreStack("ingotDesh").getItemDamage()), new Color(39, 39, 40)), "ingotDesh");
        addDefaultSeedOre(new Seed("Cobalt", 4, 4, "ingotCobalt", new ItemStack(getOreStack("ingotCobalt").getItem(), 8, getOreStack("ingotCobalt").getItemDamage()), new Color(0, 60, 255)), "ingotCobalt");
        addDefaultSeedOre(new Seed("Ardite", 4, 4, "ingotArdite", new ItemStack(getOreStack("ingotArdite").getItem(), 8, getOreStack("ingotArdite").getItemDamage()), new Color(255, 102, 0)), "ingotArdite");

        SeedCreator.createJsonFromSeeds(SeedRegistry.seedBuilder, defaultSeeds, "DefaultSeeds");
    }

    private static void addDefaultSeedMod(Seed seed, String modid) {
        if (Loader.isModLoaded(modid))
            defaultSeeds.add(seed);
    }

    private static void addDefaultSeedOre(Seed seed, String ore) {
        if (OreDictionary.doesOreNameExist(ore))
            defaultSeeds.add(seed);
    }

    private static void addDefaultSeed(Seed seed) {
        String[] split = seed.getInput().split(":");
        if (GameData.getItemRegistry().containsKey(split[0] + ":" + split[1]))
            defaultSeeds.add(seed);
    }

    private static String getItemString(Item item) {
        return GameData.getItemRegistry().getNameForObject(item) + ":0#0";
    }

    private static String getItemString(Item item, int meta) {
        return GameData.getItemRegistry().getNameForObject(item) + ":" + meta + "#0";
    }

    private static ItemStack getOreStack(String entry) {
        return OreDictionary.doesOreNameExist(entry) ? OreDictionary.getOres(entry).get(0) : new ItemStack(Blocks.fire);
    }
}
