package tehnut.resourceful.crops;

import com.google.gson.GsonBuilder;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import tehnut.resourceful.crops.base.Seed;
import tehnut.resourceful.crops.compat.waila.CompatWaila;
import tehnut.resourceful.crops.proxies.CommonProxy;
import tehnut.resourceful.crops.registry.BlockRegistry;
import tehnut.resourceful.crops.registry.ItemRegistry;
import tehnut.resourceful.crops.registry.RecipeRegistry;
import tehnut.resourceful.crops.registry.SeedRegistry;
import tehnut.resourceful.crops.util.*;
import tehnut.resourceful.crops.util.serialization.SeedCreator;

import java.io.File;

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
    public static PermanentCache<Seed> seedCache;

    public static File getConfigDir() {
        return configDir;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configDir = new File(event.getModConfigurationDirectory() + "/" + ModInformation.ID);
        configDir.mkdirs();
        ConfigHandler.init(new File(configDir.getPath(), ModInformation.ID + ".cfg"));

        proxy.load();

        seedCache = new PermanentCache<Seed>(ModInformation.ID + "Cache");
        for (Seed seed : SeedRegistry.getSeedList())
            seedCache.addObject(seed, seed.getName());

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
        if (!defaultSeedsFile.exists())
            StartupUtils.initDefaults();

        SeedCreator.registerJsonSeeds(SeedRegistry.seedBuilder, seedsFolder);
        RecipeRegistry.registerItemRecipes();

        if (Loader.isModLoaded("Waila"))
            CompatWaila.load();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
}