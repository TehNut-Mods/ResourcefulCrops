package tehnut.resourceful.crops;

import com.google.gson.GsonBuilder;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tehnut.resourceful.crops.base.Seed;
import tehnut.resourceful.crops.compat.waila.CompatWaila;
import tehnut.resourceful.crops.proxy.CommonProxy;
import tehnut.resourceful.crops.registry.BlockRegistry;
import tehnut.resourceful.crops.registry.ItemRegistry;
import tehnut.resourceful.crops.registry.RecipeRegistry;
import tehnut.resourceful.crops.registry.SeedRegistry;
import tehnut.resourceful.crops.util.*;
import tehnut.resourceful.crops.util.cache.PermanentCache;
import tehnut.resourceful.crops.util.handler.EventHandler;
import tehnut.resourceful.crops.util.handler.GenerationHandler;
import tehnut.resourceful.crops.util.handler.OreDictHandler;
import tehnut.resourceful.crops.util.helper.LogHelper;
import tehnut.resourceful.crops.util.serialization.SeedCreator;

import java.io.File;
import java.util.ArrayList;

@Mod(modid = ModInformation.ID, name = ModInformation.NAME, version = ModInformation.VERSION, dependencies = ModInformation.REQUIRED, guiFactory = ModInformation.GUIFACTORY)
public class ResourcefulCrops {

    @SidedProxy(clientSide = ModInformation.CLIENTPROXY, serverSide = ModInformation.COMMONPROXY)
    public static CommonProxy proxy;

    public static CreativeTabs tabResourcefulCrops = new CreativeTabs(ModInformation.ID + ".creativeTab") {
        @Override
        public ItemStack getIconItemStack() {
            return new ItemStack(ItemRegistry.stone, 1, 0);
        }

        @Override
        public Item getTabIconItem() {
            return ItemRegistry.stone;
        }
    };

    @Mod.Instance
    public static ResourcefulCrops instance;
    public static int renderIDCrop;
    private static File configDir;
    private static PermanentCache<Seed> seedCache;

    public static File getConfigDir() {
        return configDir;
    }

    public static PermanentCache<Seed> getSeedCache() {
        return seedCache;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configDir = new File(event.getModConfigurationDirectory() + "/" + ModInformation.ID);
        configDir.mkdirs();
        ConfigHandler.init(new File(configDir.getPath(), ModInformation.ID + ".cfg"));

        proxy.load();

        seedCache = new PermanentCache<Seed>(ModInformation.ID + "Cache");

        BlockRegistry.registerBlocks();
        ItemRegistry.registerItems();
        GameRegistry.registerWorldGenerator(new GenerationHandler(), 2);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(new EventHandler());

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

        Utils.registerCompat(CompatWaila.class, "Waila");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LogHelper.info(SeedRegistry.badSeeds + " Seeds failed to register.");

        proxy.loadCommands();
    }
}