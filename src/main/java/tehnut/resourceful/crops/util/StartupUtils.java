package tehnut.resourceful.crops.util;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tehnut.resourceful.crops.api.base.Compat;
import tehnut.resourceful.crops.api.base.CompatBuilder;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.api.base.SeedBuilder;
import tehnut.resourceful.crops.api.compat.CompatibilitySeed;
import tehnut.resourceful.crops.api.registry.SeedRegistry;
import tehnut.resourceful.crops.api.util.BlockStack;
import tehnut.resourceful.crops.util.helper.ItemHelper;
import tehnut.resourceful.crops.util.helper.LogHelper;
import tehnut.resourceful.crops.util.serialization.SeedCreator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StartupUtils {

    private static List<Seed> defaultSeeds = new ArrayList<Seed>();

    /**
     * Creates a list of default seeds to add to the game.
     */
    public static void initDefaults() {
        
        // Tier 1
        addDefaultSeedOre(makeSeed("Inky", 1, 4, "dyeBlack", "dyeBlack#8", new Color(22, 22, 22), getCompat(30)), "dyeBlack");
        addDefaultSeed(makeSeed("Fleshy", 1, 4, ItemHelper.getItemString(Items.rotten_flesh), ItemHelper.getItemString(Items.rotten_flesh, 0, 8), new Color(255, 160, 136), getCompat(30)));
        addDefaultSeed(makeSeed("Feathery", 1, 4, ItemHelper.getItemString(Items.feather), ItemHelper.getItemString(Items.feather, 0, 8), new Color(208, 203, 199), getCompat(30)));
        // Tier 2
        addDefaultSeedOre(makeSeed("Tin", 2, 4, "ingotTin", "ingotTin#4", new Color(135, 154, 168)), "ingotTin");
        addDefaultSeedOre(makeSeed("Copper", 2, 4, "ingotCopper", "ingotCopper#4", new Color(204, 102, 51)), "ingotCopper");
        addDefaultSeedOre(makeSeed("Aluminum", 2, 4, "ingotAluminum", "ingotAluminum#4", new Color(198, 206, 130)), "ingotAluminum");
        addDefaultSeedOre(makeSeed("Zinc", 2, 4, "ingotZinc", "ingotZinc#4", new Color(192, 176, 182)), "ingotZinc");
        addDefaultSeed(makeSeed("Coal", 2, 4, ItemHelper.getItemString(Items.coal), new ItemStack(Items.coal, 4), new Color(45, 44, 47)));
        addDefaultSeed(makeSeed("Charcoal", 2, 4, ItemHelper.getItemString(Items.coal, 1), new ItemStack(Items.coal, 4, 1), new Color(45, 44, 47)));
        addDefaultSeedOre(makeSeed("Saltpeter", 2, 4, "dustSaltpeter", "dustSaltpeter#8", new Color(182, 197, 212)), "dustSaltpeter");
        addDefaultSeedOre(makeSeed("Salt", 2, 4, "dustSalt", "dustSalt#8", new Color(182, 197, 212)), "dustSalt");
        addDefaultSeed(makeSeed("Leathery", 2, 4, ItemHelper.getItemString(Items.leather), new ItemStack(Items.leather, 4), new Color(255, 68, 17)));
        addDefaultSeed(makeSeed("Stringy", 2, 4, ItemHelper.getItemString(Items.string), new ItemStack(Items.string, 8), new Color(241, 255, 210)));
        addDefaultSeed(makeSeed("Boney", 2, 4, ItemHelper.getItemString(Items.bone), new ItemStack(Items.bone, 4), new Color(255, 240, 205)));
        addDefaultSeed(makeSeed("Slimey", 2, 4, ItemHelper.getItemString(Items.slime_ball), new ItemStack(Items.slime_ball, 4), new Color(62, 255, 119)));
        //Tier 3
        addDefaultSeed(makeSeed("Blaze", 3, 4, ItemHelper.getItemString(Items.blaze_rod), new ItemStack(Items.blaze_rod, 2), new Color(255, 215, 66)));
        addDefaultSeedOre(makeSeed("Iron", 3, 4, "ingotIron", "ingotIron#4", new Color(159, 156, 160)), "ingotIron");
        addDefaultSeedOre(makeSeed("Gold", 3, 4, "ingotGold", "ingotGold#4", new Color(255, 255, 0)), "ingotGold");
        addDefaultSeedOre(makeSeed("Lead", 3, 4, "ingotLead", "ingotLead#4", new Color(102, 102, 153)), "ingotLead");
        addDefaultSeedOre(makeSeed("Silver", 3, 4, "ingotSilver", "ingotSilver#4", new Color(187, 189, 184)), "ingotSilver");
        addDefaultSeedOre(makeSeed("Nickel", 3, 4, "ingotNickel", "ingotNickel#4", new Color(204, 204, 204)), "ingotNickel");
        addDefaultSeedOre(makeSeed("Mithril", 3, 4, "ingotMithril", "ingotMithril#4", new Color(146, 164, 208)), "ingotMithril");
        addDefaultSeedOre(makeSeed("Osmium", 3, 4, "ingotOsmium", "ingotOsmium#4", new Color(68, 60, 190)), "ingotOsmium");
        addDefaultSeed(makeSeed("Ender", 3, 4, ItemHelper.getItemString(Items.ender_pearl), new ItemStack(Items.ender_pearl, 2), new Color(72, 100, 97)));
        addDefaultSeed(makeSeed("Teary", 3, 4, ItemHelper.getItemString(Items.ghast_tear), new ItemStack(Items.ghast_tear, 4), new Color(212, 255, 241)));
        addDefaultSeed(makeSeed("Creepy", 3, 4, ItemHelper.getItemString(Items.gunpowder), new ItemStack(Items.gunpowder, 8), new Color(0, 255, 33)));
        addDefaultSeed(makeSeed("Skelesprout", 3, 2, ItemHelper.getItemString(Items.skull), new ItemStack(Items.skull, 1), new Color(159, 164, 155)));
        addDefaultSeed(makeSeed("Brainy", 3, 2, ItemHelper.getItemString(Items.skull, 2), new ItemStack(Items.skull, 1, 2), new Color(49, 105, 50)));
        addDefaultSeed(makeSeed("Mindful", 3, 2, ItemHelper.getItemString(Items.skull, 3), new ItemStack(Items.skull, 1, 3), new Color(232, 186, 131)));
        addDefaultSeed(makeSeed("Creepots", 3, 2, ItemHelper.getItemString(Items.skull, 4), new ItemStack(Items.skull, 1, 4), new Color(71, 178, 74)));
        addDefaultSeedOre(makeSeed("Glowstone", 3, 4, "dustGlowstone", "dustGlowstone#8", new Color(233, 255, 84)), "dustGlowstone");
        addDefaultSeedOre(makeSeed("Redstone", 3, 4, "dustRedstone", "dustRedstone#8", new Color(159, 13, 0)), "dustRedstone");
        addDefaultSeedOre(makeSeed("Sulfur", 3, 4, "dustSulfur", "dustSulfur#8", new Color(212, 190, 85)), "dustSulfur");
        addDefaultSeedOre(makeSeed("Lapis", 3, 4, "gemLapis", "gemLapis#8", new Color(63, 71, 206)), "gemLapis");
        addDefaultSeedOre(makeSeed("Quartz", 3, 4, "gemQuartz", "gemQuartz#4", new Color(255, 255, 255)), "gemQuartz");
        addDefaultSeedOre(makeSeed("Certus", 3, 4, "crystalCertusQuartz", "crystalCertusQuartz#4", new Color(168, 204, 208)), "crystalCertusQuartz");
        addDefaultSeedOre(makeSeed("Ruby", 3, 4, "gemRuby", "gemRuby#8", new Color(212, 48, 55)), "gemRuby");
        addDefaultSeedOre(makeSeed("Peridot", 3, 4, "gemPeridot", "gemPeridot#8", new Color(130, 212, 108)), "gemPeridot");
        addDefaultSeedOre(makeSeed("Topaz", 3, 4, "gemTopaz", "gemTopaz#8", new Color(212, 143, 101)), "gemTopaz");
        addDefaultSeedOre(makeSeed("Tanzanite", 3, 4, "gemTanzanite", "gemTanzanite#8", new Color(42, 7, 96)), "gemTanzanite");
        addDefaultSeedOre(makeSeed("Malachite", 3, 4, "gemMalachite", "gemMalachite#8", new Color(59, 255, 226)), "gemMalachite");
        addDefaultSeedOre(makeSeed("Sapphire", 3, 4, "gemSapphire", "gemSapphire#8", new Color(88, 106, 212)), "gemSapphire");
        addDefaultSeedOre(makeSeed("Amber", 3, 4, "gemAmber", "gemAmber#8", new Color(212, 121, 60)), "gemAmber");
        addDefaultSeedOre(makeSeed("Apatite", 3, 4, "gemApatite", "gemApatite#8", new Color(121, 188, 212)), "gemApatite");
        // Tier 4
        addDefaultSeed(makeSeed("Witherwheat", 4, 2, ItemHelper.getItemString(Items.skull, 1), new ItemStack(Items.skull, 1, 1), new Color(52, 52, 51)));
        addDefaultSeedOre(makeSeed("Diamond", 4, 2, "gemDiamond", "gemDiamond#1", new Color(58, 242, 239)), "gemDiamond");
        addDefaultSeedOre(makeSeed("Emerald", 4, 2, "gemEmerald", "gemEmerald#1", new Color(87, 242, 111)), "gemEmerald");
        addDefaultSeedOre(makeSeed("Platinum", 4, 2, "ingotPlatinum", "ingotPlatinum#1", new Color(30, 208, 243)), "ingotPlatinum");
        addDefaultSeedOre(makeSeed("Yellorium", 4, 2, "ingotYellorium", "ingotYellorium#1", new Color(142, 160, 19)), "ingotYellorium");
        addDefaultSeedOre(makeSeed("Titanium", 4, 2, "ingotTitanium", "ingotTitanium#1", new Color(212, 165, 182)), "ingotTitanium");
        addDefaultSeedOre(makeSeed("Desh", 4, 2, "ingotDesh", "ingotDesh#1", new Color(39, 39, 40)), "ingotDesh");
        addDefaultSeedOre(makeSeed("Cobalt", 4, 2, "ingotCobalt", "ingotCobalt#1", new Color(0, 60, 255)), "ingotCobalt");
        addDefaultSeedOre(makeSeed("Ardite", 4, 2, "ingotArdite", "ingotArdite#1", new Color(255, 102, 0)), "ingotArdite");

        // Compatibility
        for (CompatibilitySeed compatSeed : CompatibilitySeed.values())
            addDefaultSeedMod(compatSeed);

        SeedCreator.createJsonFromSeeds(SeedRegistry.seedBuilder, defaultSeeds, "DefaultSeeds");
    }

    /**
     * Builds a seed based on the given parameters
     */
    private static Seed makeSeed(String name, int tier, int amount, String input, String output, Color color, Compat compat) {
        SeedBuilder builder = new SeedBuilder();

        builder.setName(name);
        builder.setTier(tier);
        builder.setAmount(amount);
        builder.setInput(input);
        builder.setOutput(output);
        builder.setColor(color);
        builder.setCompat(compat);

        return builder.build();
    }

    private static Seed makeSeed(String name, int tier, int amount, String input, ItemStack output, Color color) {
        return makeSeed(name, tier, amount, input, ItemHelper.getItemString(output), color, null);
    }

    private static Seed makeSeed(String name, int tier, int amount, String input, String output, Color color) {
        return makeSeed(name, tier, amount, input, output, color, null);
    }

    /**
     * Adds a default seed for a given mod
     *
     * @param compatibilitySeed - {@link CompatibilitySeed} to check and register
     */
    private static void addDefaultSeedMod(CompatibilitySeed compatibilitySeed) {

        LogHelper.info("Adding compatibility Seed for { " + compatibilitySeed.getModid() + " }");

        if (Loader.isModLoaded(compatibilitySeed.getModid()) && compatibilitySeed.getConfig())
            defaultSeeds.add(compatibilitySeed.getCompatSeed());
    }

    /**
     * Adds a default seed for a OreDict entry
     *
     * @param seed - Seed to add to defaults
     * @param ore  - OreDict entry required to add
     */
    private static void addDefaultSeedOre(Seed seed, String ore) {
        if (OreDictionary.doesOreNameExist(ore))
            defaultSeeds.add(seed);
    }

    /**
     * Adds a default seed
     *
     * @param seed - Seed to add to defaults
     */
    private static void addDefaultSeed(Seed seed) {
        String[] split = seed.getInput().split(":");
        if (GameData.getItemRegistry().containsKey(split[0] + ":" + split[1]))
            defaultSeeds.add(seed);
    }

    private static Compat getCompat(int sieveChance) {
        return new CompatBuilder().setCompatExNihilio(new CompatBuilder.CompatExNihilioBuilder().setSourceBlock(new BlockStack(Blocks.dirt)).setSieveChance(sieveChance).build()).build();
    }
}
