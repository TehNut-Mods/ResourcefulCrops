package tehnut.resourceful.crops.registry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.achievement.AchievementRCrops;
import tehnut.resourceful.crops.achievement.AchievementTrigger;
import tehnut.resourceful.crops.block.BlockRCrop;
import tehnut.resourceful.crops.item.*;
import tehnut.resourceful.crops.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class AchievementRegistry {

    public static List<Achievement> achievements = new ArrayList<Achievement>();

    public static AchievementPage rcropsAchievementPage;

    public static Achievement[] getStone = new Achievement[5];
    public static Achievement getEssence;
    public static Achievement getSeed;
    public static Achievement getShard;
    public static Achievement getPouch;
    public static Achievement getInfo;
    public static Achievement getHarvest;

    public static void registerAchievements() {
        getEssence = new AchievementRCrops("get.essence", 0, 0, new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 4), null);
        getStone[0] = new AchievementRCrops("get.stone.0", 2, 0, new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 0), getEssence);
        getStone[1] = new AchievementRCrops("get.stone.1", 4, 0, new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 1), getStone[0]);
        getStone[2] = new AchievementRCrops("get.stone.2", 6, 0, new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 2), getStone[1]);
        getStone[3] = new AchievementRCrops("get.stone.3", 8, 0, new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 3), getStone[2]);
        getStone[4] = new AchievementRCrops("get.stone.4", 10, 0, new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 4), getStone[3]).setSpecial();
        getSeed = new AchievementRCrops("get.seed", 2, 2, Utils.getInvalidSeed(ItemRegistry.getItem(ItemSeed.class)), getStone[0]);
        getPouch = new AchievementRCrops("get.pouch", 4, 2, Utils.getInvalidSeed(ItemRegistry.getItem(ItemPouch.class)), getSeed);
        getShard = new AchievementRCrops("get.shard", 4, 4, Utils.getInvalidSeed(ItemRegistry.getItem(ItemShard.class)), getSeed);
        getInfo = new AchievementRCrops("get.info", 0, 2, new ItemStack(Items.diamond_hoe), getSeed).setSpecial();
        if (ConfigHandler.enableRightClickHarvest)
            getHarvest = new AchievementRCrops("get.harvest", 0, 4, new ItemStack(BlockRegistry.getBlock(BlockRCrop.class), 6), getSeed).setSpecial();

        rcropsAchievementPage = new AchievementPage(StatCollector.translateToLocal("achievement.ResourcefulCrops.page"), achievements.toArray(new Achievement[achievements.size()]));
        AchievementPage.registerAchievementPage(rcropsAchievementPage);
        MinecraftForge.EVENT_BUS.register(new AchievementTrigger());
    }
}
