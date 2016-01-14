package tehnut.resourceful.crops;

import com.google.gson.GsonBuilder;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tehnut.resourceful.crops.annot.Handler;
import tehnut.resourceful.crops.annot.ModBlock;
import tehnut.resourceful.crops.annot.ModItem;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.api.registry.SeedRegistry;
import tehnut.resourceful.crops.api.util.cache.PermanentCache;
import tehnut.resourceful.crops.item.ItemStone;
import tehnut.resourceful.crops.proxy.CommonProxy;
import tehnut.resourceful.crops.registry.*;
import tehnut.resourceful.crops.util.StartupUtils;
import tehnut.resourceful.crops.util.handler.GenerationHandler;
import tehnut.resourceful.crops.util.handler.OreDictHandler;
import tehnut.resourceful.crops.util.helper.LogHelper;
import tehnut.resourceful.crops.util.serialization.SeedCreator;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

@Mod(modid = ModInformation.ID, name = ModInformation.NAME, version = ModInformation.VERSION, acceptedMinecraftVersions = "[1.8.8,1.8.9]", dependencies = ModInformation.REQUIRED, guiFactory = ModInformation.GUIFACTORY)
public class ResourcefulCrops {

    @SidedProxy(clientSide = ModInformation.CLIENTPROXY, serverSide = ModInformation.COMMONPROXY)
    public static CommonProxy proxy;

    public static CreativeTabs tabResourcefulCrops = new CreativeTabs(ModInformation.ID + ".creativeTab") {
        @Override
        public ItemStack getIconItemStack() {
            return new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 0);
        }

        @Override
        public Item getTabIconItem() {
            return ItemRegistry.getItem(ItemStone.class);
        }
    };

    @Mod.Instance
    public static ResourcefulCrops instance;
    private static File configDir;
    private static PermanentCache<Seed> seedCache;
    public Set<ASMDataTable.ASMData> modItems;
    public Set<ASMDataTable.ASMData> modBlocks;
    public Set<ASMDataTable.ASMData> eventHandlers;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configDir = new File(event.getModConfigurationDirectory() + "/" + ModInformation.ID);
        configDir.mkdirs();
        ConfigHandler.init(new File(configDir.getPath(), ModInformation.ID + ".cfg"));

        seedCache = new PermanentCache<Seed>(ModInformation.ID + "Cache");

        ResourcefulAPI.seedCache = seedCache;
        ResourcefulAPI.logger = LogHelper.getLogger();
        ResourcefulAPI.forceAddDuplicates = ConfigHandler.forceAddDuplicates;

        modItems = event.getAsmData().getAll(ModItem.class.getCanonicalName());
        modBlocks = event.getAsmData().getAll(ModBlock.class.getCanonicalName());
        eventHandlers = event.getAsmData().getAll(Handler.class.getCanonicalName());

        BlockRegistry.init();
        ItemRegistry.init();

        AchievementRegistry.registerAchievements();
        GameRegistry.registerWorldGenerator(new GenerationHandler(), 2);

        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        SeedRegistry.seedBuilder = new GsonBuilder();
        SeedCreator.registerCustomSerializers(SeedRegistry.seedBuilder);

        File seedsFolder = new File(getConfigDir().getPath() + "/seeds");
        seedsFolder.mkdir();
        File defaultSeedsFile = new File(seedsFolder, "DefaultSeeds.json");
        if (!defaultSeedsFile.exists() && ConfigHandler.generateDefaults)
            StartupUtils.initDefaults();

        SeedCreator.registerJsonSeeds(SeedRegistry.seedBuilder, seedsFolder);
        SeedRegistry.setSeedList(new ArrayList<Seed>(getSeedCache().getEnumeratedObjects().valueCollection()));
        RecipeRegistry.registerItemRecipes();

        OreDictHandler.load();

        CompatibilityRegistry.registerModCompat();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (SeedRegistry.badSeeds > 0)
            LogHelper.error(SeedRegistry.badSeeds + " Seeds failed to register.");

        proxy.loadCommands();
        proxy.loadRenders();
    }

    public static File getConfigDir() {
        return configDir;
    }

    public static PermanentCache<Seed> getSeedCache() {
        return seedCache;
    }
}