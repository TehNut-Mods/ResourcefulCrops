package tehnut.resourceful.crops.core;

import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.commons.io.filefilter.FileFilterUtils;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.core.data.Output;
import tehnut.resourceful.crops.core.data.Seed;
import tehnut.resourceful.crops.core.json.Serializers;
import tehnut.resourceful.crops.util.Util;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.List;

public class SeedLoader {

    public static void init(File seedDir, IForgeRegistry<Seed> seedRegistry) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Gson gson = Serializers.withAll();
        if (!seedDir.exists() && seedDir.mkdirs()) {
            for (Seed seed : getDefaults()) {
                String json = gson.toJson(seed);
                try {
                    File printFile = new File(seedDir, Util.cleanString(seed.getName()) + ".json");
                    FileWriter fileWriter = new FileWriter(printFile);
                    fileWriter.write(json);
                    fileWriter.close();
                    ResourcefulCrops.debug("Printed default file for {} to {}", seed.getName(), printFile.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                seedRegistry.register(seed.setRegistryName(Util.cleanString(seed.getName())));
            }

            ResourcefulCrops.LOGGER.info("Finished loading {} seeds in {}", seedRegistry.getValues().size(), stopwatch.stop());
            return;
        }

        File[] jsonFiles = seedDir.listFiles((FileFilter) FileFilterUtils.suffixFileFilter(".json"));
        if (jsonFiles == null)
            return;

        List<Seed> seeds = Lists.newArrayList();
        for (File jsonFile : jsonFiles) {
            try {
                FileReader reader = new FileReader(jsonFile);
                Seed seed = gson.fromJson(reader, Seed.class);
                seeds.add(seed.setRegistryName(Util.cleanString(seed.getName())));
                reader.close();
                ResourcefulCrops.debug("Read file for {} to {}", seed.getRegistryName(), jsonFile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        seeds.sort((seed1, seed2) -> ((Integer) seed1.getTier()).compareTo(seed2.getTier()));

        for (Seed seed : seeds) {
            String sanity = sanityCheck(seed);
            if (!Strings.isNullOrEmpty(sanity)) {
                ResourcefulCrops.LOGGER.error(sanity);
                continue;
            }

            seedRegistry.register(seed);
        }
        ResourcefulCrops.LOGGER.info("Finished loading {} seeds in {}", seedRegistry.getValues().size() - 1, stopwatch.stop());
    }

    private static Set<Seed> getDefaults() {
        Set<Seed> defaultSeeds = Sets.newHashSet();
        // Tier 1
        addSeed(defaultSeeds, "inky", 0, 4, null, "dyeBlack", 8);
        addSeed(defaultSeeds, "rotting", 0, 4, null, new ItemStack(Items.ROTTEN_FLESH), 8);
        addSeed(defaultSeeds, "feathery", 0, 4, null, new ItemStack(Items.FEATHER), 8);
        // Tier 2
        addSeed(defaultSeeds, "tin", 1, 4, null, "ingotTin", 4);
        addSeed(defaultSeeds, "copper", 1, 4, null, "ingotCopper", 4);
        addSeed(defaultSeeds, "aluminum", 1, 4, null, "ingotAluminum", 4);
        addSeed(defaultSeeds, "zinc", 1, 4, null, "ingotZinc", 4);
        addSeed(defaultSeeds, "coal", 1, 4, null, new ItemStack(Items.COAL), 4);
        addSeed(defaultSeeds, "charcoal", 1, 4, null, new ItemStack(Items.COAL, 1, 1), 4);
        addSeed(defaultSeeds, "saltpeter", 1, 4, null, "dustSaltpeter", 8);
        addSeed(defaultSeeds, "salty", 1, 4, null, "dustSalt", 8);
        addSeed(defaultSeeds, "leathery", 1, 4, null, new ItemStack(Items.LEATHER), 4);
        addSeed(defaultSeeds, "stringy", 1, 4, null, new ItemStack(Items.STRING), 8);
        addSeed(defaultSeeds, "boney", 1, 4, null, new ItemStack(Items.BONE), 4);
        addSeed(defaultSeeds, "slimy", 1, 4, null, new ItemStack(Items.SLIME_BALL), 4);
        // Tier 3
        addSeed(defaultSeeds, "blazing", 2, 4, null, new ItemStack(Items.BLAZE_ROD), 2);
        addSeed(defaultSeeds, "ferrous", 2, 4, null, "ingotIron", 4);
        addSeed(defaultSeeds, "golden", 2, 4, null, "ingotGold", 4);
        addSeed(defaultSeeds, "lead", 2, 4, null, "ingotLead", 4);
        addSeed(defaultSeeds, "silver", 2, 4, null, "ingotSilver", 4);
        addSeed(defaultSeeds, "nickel", 2, 4, null, "ingotNickel", 4);
        addSeed(defaultSeeds, "mithril", 2, 4, null, "ingotMithril", 4);
        addSeed(defaultSeeds, "osmium", 2, 4, null, "ingotOsmium", 4);
        addSeed(defaultSeeds, "ender", 2, 4, null, new ItemStack(Items.ENDER_PEARL), 2);
        addSeed(defaultSeeds, "teary", 2, 4, null, new ItemStack(Items.GHAST_TEAR), 4);
        addSeed(defaultSeeds, "creepy", 2, 4, null, new ItemStack(Items.GUNPOWDER), 8);
        addSeed(defaultSeeds, "skelesprout", 2, 2, new Color(159, 164, 155), new ItemStack(Items.SKULL), 1);
        addSeed(defaultSeeds, "brainy", 2, 2, new Color(49, 105, 50), new ItemStack(Items.SKULL, 1, 2), 1);
        addSeed(defaultSeeds, "mindful", 2, 2, new Color(232, 186, 131), new ItemStack(Items.SKULL, 1, 3), 1);
        addSeed(defaultSeeds, "creepot", 2, 2, new Color(71, 178, 74), new ItemStack(Items.SKULL, 1, 4), 1);
        addSeed(defaultSeeds, "glowing", 2, 4, null, "dustGlowstone", 8);
        addSeed(defaultSeeds, "redstone", 2, 4, null, "dustRedstone", 8);
        addSeed(defaultSeeds, "sulfur", 2, 4, null, "dustSulfur", 8);
        addSeed(defaultSeeds, "lapis", 2, 4, null, "gemLapis", 8);
        addSeed(defaultSeeds, "quartz", 2, 4, null, "gemQuartz", 4);
        addSeed(defaultSeeds, "certus", 2, 4, null, "crystalCertusQuartz", 4);
        addSeed(defaultSeeds, "ruby", 2, 4, null, "gemRuby", 8);
        addSeed(defaultSeeds, "peridot", 2, 4, null, "gemPeridot", 8);
        addSeed(defaultSeeds, "topaz", 2, 4, null, "gemTopaz", 8);
        addSeed(defaultSeeds, "tanzanite", 2, 4, null, "gemTanzanite", 8);
        addSeed(defaultSeeds, "malachite", 2, 4, null, "gemMalachite", 8);
        addSeed(defaultSeeds, "sapphire", 2, 4, null, "gemSapphire", 8);
        addSeed(defaultSeeds, "amber", 2, 4, null, "gemAmber", 8);
        addSeed(defaultSeeds, "apatite", 2, 4, null, "gemApatite", 8);
        // Tier 4
        addSeed(defaultSeeds, "witherwheat", 3, 2, new Color(52, 52, 51), new ItemStack(Items.SKULL, 1, 1), 1);
        addSeed(defaultSeeds, "diamond", 3, 4, null, "gemDiamond", 1);
        addSeed(defaultSeeds, "emerald", 3, 4, null, "gemEmerald", 1);
        addSeed(defaultSeeds, "platinum", 3, 4, null, "ingotPlatinum", 1);
        addSeed(defaultSeeds, "yellorium", 3, 4, null, "ingotYellorium", 2);
        addSeed(defaultSeeds, "titanium", 3, 4, null, "ingotTitanium", 1);
        addSeed(defaultSeeds, "desh", 3, 4, null, "ingotDesh", 1);
        addSeed(defaultSeeds, "cobalt", 3, 4, null, "ingotCobalt", 1);
        addSeed(defaultSeeds, "ardite", 3, 4, null, "ingotArdite", 1);

        return defaultSeeds;
    }

    private static void addSeed(Set<Seed> seeds, String name, int tier, int amount, Color color, ItemStack stack, int outputAmount) {
        ItemStack output = stack.copy();
        output.setCount(outputAmount);
        seeds.add(new Seed(name, tier, amount, color, stack, new Output(output, Output.Shape.DEFAULT, null), null, null));
    }

    private static void addSeed(Set<Seed> seeds, String name, int tier, int amount, Color color, String oreDict, int outputAmount) {
        if (OreDictionary.doesOreNameExist(oreDict) && OreDictionary.getOres(oreDict).size() > 0) {
            ItemStack output = OreDictionary.getOres(oreDict).get(0).copy();
            output.setCount(outputAmount);
            Seed seed = new Seed(name, tier, amount, color, oreDict, new Output(output, Output.Shape.DEFAULT, null), null, null);
            seed.setOreName(oreDict);
            seeds.add(seed);
        }
    }

    // Used to add new checks in the future for requirements on seeds. Returns an error message to display
    @Nullable
    private static String sanityCheck(Seed seed) {
        if (seed.getInputItems().isEmpty())
            return String.format("Seed %s has no valid inputs. Ignoring.", seed.getRegistryName().toString());

        return null;
    }
}
