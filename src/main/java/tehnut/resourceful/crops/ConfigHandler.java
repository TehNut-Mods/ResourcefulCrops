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
    public static boolean enableFakePlayerMining;

    public static boolean enableWorldGeneration;

    public static boolean enableConsoleLogging;
    public static boolean enableFancyRender;
    public static boolean forceFancyRender;
    public static boolean enableRightClickHarvest;
    public static boolean forceAddDuplicates;
    public static boolean generateDefaults;
    public static boolean registerClientCommands;

    public static boolean enableTorcherinoAccelerator;
    public static boolean enableMFRAutomation;
    public static boolean enableMFRLaserDrill;
    public static boolean enableEnderIOAutomation;
    public static boolean enableBloodMagicAutomation;
    public static int gaianiteSieveChance;
    public static String gaianiteSieveBlock;

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
        enableFakePlayerMining = config.getBoolean("enableFakePlayerMining", category, true, "Allows ore to be mined by Quarries and Miners");

        category = "World";
        config.addCustomCategoryComment(category, "World settings");
        enableWorldGeneration = config.getBoolean("enableWorldGeneration", category, true, "Allows Gaianite Ore to generate in the world.");

        category = "General";
        config.addCustomCategoryComment(category, "Miscellaneous settings");
        enableConsoleLogging = config.getBoolean("enableConsoleLogging", category, true, "Enables extra information being printed to the console.");
        enableRightClickHarvest = config.getBoolean("enableRightClickHarvest", category, true, "Allows crops to be right clicked to automatically harvest and replant if the crop is mature.");
        forceAddDuplicates = config.getBoolean("forceAddDuplicates", category, false, "Forces duplicate seeds to be registered");
        generateDefaults = config.getBoolean("generateDefaults", category, true, "Generates a list of default seeds.");
        forceFancyRender = config.getBoolean("forceFancyRender", category, true, "Forces the fancy render for crops if you have fast graphics enabled.");
        registerClientCommands = config.getBoolean("registerClientCommands", category, false, "Registers some client commands that can make adding Seeds a bit easier.\nCommands are \"/createSeed\" and \"printSeeds\".");

        category = "Compat";
        config.addCustomCategoryComment(category, "Compatibility settings");
        category = "Compat.Torcherino";
        enableTorcherinoAccelerator = config.getBoolean("enableTorcherinoAccelerator", category, false, "Allows the Torcherino from Torcherino to accelerate crop growth.");
        category = "Compat.ExNihilio";
        gaianiteSieveChance = config.getInt("gaianiteSieveChance", category, 40, 0, 100, "Chance for a sieve to drop a standard Gaianite Essence. Set to 0 to disable.");
        gaianiteSieveBlock = config.getString("gaianiteSieveBlock", category, "minecraft:dirt:0", "Block to sieve for Gaianite Essence.\nSyntax is: modid:regname:meta");
        category = "Compat.MinefactoryReloaded";
        enableMFRAutomation = config.getBoolean("enableMFRAutomation", category, true, "Allows the MFR Planter/Harvester to work with Seeds/Crops.");
        enableMFRLaserDrill = config.getBoolean("enableMFRLaserDrill", category, true, "Allows the MFR Laser Drill to collect Gaianite Ore with a Purple Lens.");
        category = "Compat.EnderIO";
        enableEnderIOAutomation = config.getBoolean("enableEnderIOAutomation", category, true, "Allows the EnderIO Farming Station to work with Seeds/Crops.");
        category = "Compat.BloodMagic";
        enableBloodMagicAutomation = config.getBoolean("enableBloodMagicAutomation", category, true, "Allows the BloodMagic Ritual of the Harvest Moon to work with Seeds/Crops");
        category = "Compat.Seeds";
        config.addCustomCategoryComment(category, "Custom compatibility seeds. For seeds that do unique things.\n" +
                "This is based on the seed name. If you want to use the name for something else, you must disable it in the config here.\n" +
                "Reserved names include:\n" +
                "-");

        config.save();
    }
}
