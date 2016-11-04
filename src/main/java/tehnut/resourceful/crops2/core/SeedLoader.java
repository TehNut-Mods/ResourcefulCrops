package tehnut.resourceful.crops2.core;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.io.filefilter.FileFilterUtils;
import tehnut.resourceful.crops2.core.data.Seed;
import tehnut.resourceful.crops2.core.json.Serializers;
import tehnut.resourceful.crops2.util.Util;

import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.List;

public class SeedLoader {

    public static void init(File seedDir) {
        Gson gson = Serializers.withAll();
        if (!seedDir.exists() && seedDir.mkdirs()) {
            for (Seed seed : getDefaults()) {
                String json = gson.toJson(seed);
                try {
                    FileWriter fileWriter = new FileWriter(new File(seedDir, Util.cleanString(seed.getName()) + ".json"));
                    fileWriter.write(json);
                    fileWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                GameRegistry.register(seed.setRegistryName(Util.cleanString(seed.getName())));
            }

            return;
        }

        File[] jsonFiles = seedDir.listFiles((FileFilter) FileFilterUtils.suffixFileFilter(".json"));
        if (jsonFiles == null)
            return;

        List<Seed> seeds = Lists.newArrayList();
        for (File jsonFile : jsonFiles) {
            try {
                Seed seed = gson.fromJson(new FileReader(jsonFile), Seed.class);
                seeds.add(seed.setRegistryName(Util.cleanString(seed.getName())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Collections.sort(seeds, new Comparator<Seed>() {
            @Override
            public int compare(Seed seed1, Seed seed2) {
                return ((Integer) seed1.getTier()).compareTo(seed2.getTier());
            }
        });

        for (Seed seed : seeds)
            GameRegistry.register(seed);
    }

    private static Set<Seed> getDefaults() {
        Set<Seed> defaultSeeds = new HashSet<Seed>();
        // Tier 1
        addSeed(defaultSeeds, "inky", 0, 4, new Color(22, 22, 22), "dyeBlack", 8);
        addSeed(defaultSeeds, "fleshy", 0, 4, new Color(255, 160, 136), new ItemStack(Items.ROTTEN_FLESH), 8);
        addSeed(defaultSeeds, "feathery", 0, 4, new Color(208, 203, 199), new ItemStack(Items.FEATHER), 8);
        // Tier 2
        addSeed(defaultSeeds, "tin", 1, 4, new Color(135, 154, 168), "ingotTin", 4);
        addSeed(defaultSeeds, "copper", 1, 4, new Color(204, 102, 51), "ingotCopper", 4);
        addSeed(defaultSeeds, "aluminum", 1, 4, new Color(198, 206, 130), "ingotAluminum", 4);
        addSeed(defaultSeeds, "zinc", 1, 4, new Color(192, 176, 182), "ingotZinc", 4);
        addSeed(defaultSeeds, "coal", 1, 4, new Color(45, 44, 47), new ItemStack(Items.COAL), 4);
        addSeed(defaultSeeds, "charcoal", 1, 4, new Color(45, 44, 47), new ItemStack(Items.COAL, 1, 1), 4);
        addSeed(defaultSeeds, "saltpeter", 1, 4, new Color(182, 197, 212), "dustSaltpeter", 8);
        addSeed(defaultSeeds, "salt", 1, 4, new Color(182, 197, 212), "dustSalt", 8);
        addSeed(defaultSeeds, "leathery", 1, 4, new Color(255, 68, 17), new ItemStack(Items.LEATHER), 4);
        addSeed(defaultSeeds, "stringy", 1, 4, new Color(241, 255, 210), new ItemStack(Items.STRING), 8);
        addSeed(defaultSeeds, "boney", 1, 4, new Color(255, 240, 205), new ItemStack(Items.BONE), 4);
        addSeed(defaultSeeds, "slimy", 1, 4, new Color(62, 255, 119), new ItemStack(Items.SLIME_BALL), 4);
        //Tier 3
        addSeed(defaultSeeds, "blaze", 2, 4, new Color(255, 215, 66), new ItemStack(Items.BLAZE_ROD), 2);
        addSeed(defaultSeeds, "iron", 2, 4, new Color(159, 156, 160), "ingotIron", 4);
        addSeed(defaultSeeds, "gold", 2, 4, new Color(255, 255, 0), "ingotGold", 4);
        addSeed(defaultSeeds, "lead", 2, 4, new Color(102, 102, 153), "ingotLead", 4);
        addSeed(defaultSeeds, "silver", 2, 4, new Color(187, 189, 184), "ingotSilver", 4);
        addSeed(defaultSeeds, "nickel", 2, 4, new Color(204, 204, 204), "ingotNickel", 4);
        addSeed(defaultSeeds, "mithril", 2, 4, new Color(146, 164, 208), "ingotMithril", 4);
        addSeed(defaultSeeds, "osmium", 2, 4, new Color(68, 60, 190), "ingotOsmium", 4);
        addSeed(defaultSeeds, "ender", 2, 4, new Color(72, 100, 97), new ItemStack(Items.ENDER_PEARL), 2);
        addSeed(defaultSeeds, "teary", 2, 4, new Color(212, 255, 241), new ItemStack(Items.GHAST_TEAR), 4);
        addSeed(defaultSeeds, "creepy", 2, 4, new Color(0, 255, 33), new ItemStack(Items.GUNPOWDER), 8);
        addSeed(defaultSeeds, "skelesprout", 2, 2, new Color(159, 164, 155), new ItemStack(Items.SKULL), 1);
        addSeed(defaultSeeds, "brainy", 2, 2, new Color(49, 105, 50), new ItemStack(Items.SKULL, 1, 2), 1);
        addSeed(defaultSeeds, "mindful", 2, 2, new Color(232, 186, 131), new ItemStack(Items.SKULL, 1, 3), 1);
        addSeed(defaultSeeds, "creepot", 2, 2, new Color(71, 178, 74), new ItemStack(Items.SKULL, 1, 4), 1);
        addSeed(defaultSeeds, "glowstone", 2, 4, new Color(233, 255, 84), "dustGlowstone", 8);
        addSeed(defaultSeeds, "redstone", 2, 4, new Color(159, 13, 0), "dustRedstone", 8);

        return defaultSeeds;
    }

    private static void addSeed(Set<Seed> seeds, String name, int tier, int amount, Color color, ItemStack stack, int outputAmount) {
        ItemStack output = stack.copy();
        output.stackSize = outputAmount;
        seeds.add(new Seed(name, tier, amount, color, stack, output, null));
    }

    private static void addSeed(Set<Seed> seeds, String name, int tier, int amount, Color color, String oreDict, int outputAmount) {
        if (OreDictionary.doesOreNameExist(oreDict) && OreDictionary.getOres(oreDict).size() > 0) {
            ItemStack output = OreDictionary.getOres(oreDict).get(0).copy();
            output.stackSize = outputAmount;
            Seed seed = new Seed(name, tier, amount, color, oreDict, output, null);
            seed.setOreName(oreDict);
            seeds.add(seed);
        }
    }
}
