package tehnut.resourceful.crops;

import com.google.gson.GsonBuilder;
import cpw.mods.fml.common.FMLCommonHandler;
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
import tehnut.resourceful.crops.compat.bloodmagic.CompatBloodMagic;
import tehnut.resourceful.crops.compat.enderio.CompatEnderIO;
import tehnut.resourceful.crops.compat.mfr.CompatMFR;
import tehnut.resourceful.crops.compat.torcherino.CompatTorcherino;
import tehnut.resourceful.crops.compat.waila.CompatWaila;
import tehnut.resourceful.crops.proxies.CommonProxy;
import tehnut.resourceful.crops.registry.BlockRegistry;
import tehnut.resourceful.crops.registry.ItemRegistry;
import tehnut.resourceful.crops.registry.RecipeRegistry;
import tehnut.resourceful.crops.registry.SeedRegistry;
import tehnut.resourceful.crops.util.*;
import tehnut.resourceful.crops.util.cache.PermanentCache;
import tehnut.resourceful.crops.util.handlers.EventHandler;
import tehnut.resourceful.crops.util.handlers.GenerationHandler;
import tehnut.resourceful.crops.util.helpers.LogHelper;
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
        if (!defaultSeedsFile.exists())
            StartupUtils.initDefaults();

        SeedCreator.registerJsonSeeds(SeedRegistry.seedBuilder, seedsFolder);
        SeedRegistry.setSeedList(new ArrayList<Seed>(getSeedCache().getEnumeratedObjects().valueCollection()));
        RecipeRegistry.registerItemRecipes();

        Utils.registerCompat(CompatWaila.class, "Waila");
        Utils.registerCompat(CompatBloodMagic.class, "AWWayofTime");
        Utils.registerCompat(CompatEnderIO.class, "EnderIO");
        Utils.registerCompat(CompatMFR.class, "MineFactoryReloaded");
        Utils.registerCompat(CompatTorcherino.class, "Torcherino");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LogHelper.info(SeedRegistry.badSeeds + " Seeds failed to register.");
    }
}