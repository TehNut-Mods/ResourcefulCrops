package tehnut.resourceful.crops;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tehnut.resourceful.crops.compat.Compatibility;
import tehnut.resourceful.crops.core.ConfigHandler;
import tehnut.resourceful.crops.core.SeedLoader;
import tehnut.resourceful.crops.proxy.CommonProxy;
import tehnut.resourceful.crops.core.ModObjects;
import tehnut.resourceful.crops.util.AnnotationHelper;

import java.io.File;
import java.util.Set;

@Mod(modid = ResourcefulCrops.MODID, name = ResourcefulCrops.NAME, version = ResourcefulCrops.VERSION)
public class ResourcefulCrops {

    public static final String MODID = "resourcefulcrops";
    public static final String NAME = "ResourcefulCrops";
    public static final String VERSION = "@VERSION@";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static final boolean DEV_MODE = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    public static final CreativeTabs TAB_RCROP = new CreativeTabs(MODID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModObjects.ESSENCE, 1, 4);
        }
    };

    @SidedProxy(clientSide = "tehnut.resourceful.crops.proxy.ClientProxy", serverSide = "tehnut.resourceful.crops.proxy.CommonProxy")
    public static CommonProxy PROXY;
    public static File configDir;
    public static Set<ASMDataTable.ASMData> modCompatibilities;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configDir = new File(event.getModConfigurationDirectory(), MODID);

        modCompatibilities = event.getAsmData().getAll(Compatibility.class.getCanonicalName());

        ModObjects.preInit();

        PROXY.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        SeedLoader.init(new File(configDir, "seeds"));
        ModObjects.init();

        AnnotationHelper.loadCompatibilities(modCompatibilities);

        PROXY.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        PROXY.postInit();
    }

    public static void debug(String message, Object... params) {
        if (ConfigHandler.miscellaneous.debugLogging)
            LOGGER.info("[DEBUG] " + message, params);
    }
}
