package tehnut.resourceful.crops.core;

import net.minecraftforge.common.config.Config;
import tehnut.resourceful.crops.ResourcefulCrops;

@Config(modid = ResourcefulCrops.MODID, name = ResourcefulCrops.MODID + "/" + ResourcefulCrops.MODID)
public class ConfigHandler {

    public static Balance balance = new Balance();
    public static Compatibility compatibility = new Compatibility();
    public static Crafting crafting = new Crafting();
    public static Miscellaneous miscellaneous = new Miscellaneous();
    public static World world = new World();

    public static class Balance {
        @Config.Comment({"Allows ore to be mined by Quarries and Miners", "Default: true"})
        public boolean enableFakePlayerMining = true;
    }

    public static class Compatibility {
        @Config.Comment({"Allows the Reap of the Harvest Moon to harvest crops.", "Default: true"})
        public boolean enableBloodMagicAutomation = true;
        @Config.Comment({"Allows the Farming Station to harvest crops.", "Default: true"})
        public boolean enableEnderIOAutomation = true;
    }

    public static class Crafting {
        @Config.Comment({"Allows crafting of seeds into pouches. Disable if you wish to use custom recipes.", "Default: true"})
        public boolean enablePouchCrafting = true;
        @Config.Comment({"Allows crafting of materials into seeds. Disable if you wish to use custom recipes.", "Default: true"})
        public boolean enableSeedCrafting = true;
        @Config.Comment({"Allows crafting of shards into materials. Disable if you wish to use custom recipes.", "Default: true"})
        public boolean enableShardCrafting = true;
        @Config.Comment({"The default recipe format to use for all outputs.", "Valid values: chest, cross, two_by_two, 2x2, three_by_three, 3x3", "You can also provide a custom shape with each line marked with a #. eg: SSS#S S#SSS for a chest shape.", "Default: chest"})
        public String defaultRecipeShape = "chest";
    }

    public static class Miscellaneous {
        @Config.Comment({"Enables extra information being printed to the console.", "Default: false"})
        public boolean debugLogging = false;
    }

    public static class World {
        @Config.Comment({"Allows Gaianite Ore to generate in the world.", "Use a third party mod such as O.R.E. to modify generation values.", "Default: true"})
        public boolean enableWorldGeneration = true;
    }
}
