package tehnut.resourceful.crops;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

    public static Configuration config;

    // Settings
    public static boolean enableSeedCrafting;
    public static boolean enableShardCrafting;
    public static boolean enablePouchCrafting;

    public static boolean enableSeedPouches;

    public static boolean enableWorldGeneration;

    public static boolean enableConsoleLogging;
    public static boolean enableFancyRender;
    public static boolean enableRightClickHarvest;
    public static boolean forceAddDuplicates;
    public static boolean generateDefaults;

    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }

    public static void syncConfig() {
        String category;

        category = "Crafting";
        config.addCustomCategoryComment(category, "Crafting settings");
        enableSeedCrafting = config.getBoolean("enableSeedCrafting", category, true, "Allows crafting of materials into seeds. Disable if you wish to use custom recipes.");
        enableShardCrafting = config.getBoolean("enableShardCrafting", category, true, "Allows crafting of shards into materials. Disable if you wish to use custom recipes.");
        enablePouchCrafting = config.getBoolean("enablePouchCrafting", category, true, "Allows crafting of seeds into pouches. Disable if you wish to use custom recipes.");

        category = "Balance";
        config.addCustomCategoryComment(category, "Balance settings");
        enableSeedPouches = config.getBoolean("enableSeedPouches", category, true, "Allows registering of seed pouches. These plant a 3x3 (9 total) seeds around a central block.");

        category = "World";
        config.addCustomCategoryComment(category, "World settings");
        enableWorldGeneration = config.getBoolean("enableWorldGeneration", category, true, "Allows Gaianite Ore to generate in the world.");

        category = "General";
        config.addCustomCategoryComment(category, "Miscellaneous settings");
        enableConsoleLogging = config.getBoolean("enableConsoleLogging", category, true, "Enables extra information being printed to the console.");
        enableRightClickHarvest = config.getBoolean("enableRightClickHarvest", category, true, "Allows crops to be right clicked to automatically harvest and replant if the crop is mature.");
        forceAddDuplicates = config.getBoolean("forceAddDuplicates", category, false, "Forces duplicate seeds to be registered");
        generateDefaults = config.getBoolean("generateDefaults", category, true, "Generates a list of default seeds.");


        config.save();
    }
}
