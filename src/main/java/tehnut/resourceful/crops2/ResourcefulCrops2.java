package tehnut.resourceful.crops2;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLModIdMappingEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tehnut.resourceful.crops2.compat.Compatibility;
import tehnut.resourceful.crops2.core.ConfigHandler;
import tehnut.resourceful.crops2.core.SeedLoader;
import tehnut.resourceful.crops2.proxy.CommonProxy;
import tehnut.resourceful.crops2.core.ModObjects;
import tehnut.resourceful.crops2.util.AnnotationHelper;

import java.io.File;
import java.util.Set;

@Mod(modid = ResourcefulCrops2.MODID, name = ResourcefulCrops2.NAME, version = ResourcefulCrops2.VERSION)
public class ResourcefulCrops2 {

    public static final String MODID = "resourcefulcrops";
    public static final String NAME = "ResourcefulCrops";
    public static final String VERSION = "@VERSION@";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static final boolean DEV_MODE = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    public static final CreativeTabs TAB_RCROP = new CreativeTabs(MODID) {
        @Override
        public Item getTabIconItem() {
            return ModObjects.ESSENCE;
        }

        @Override
        public int getIconItemDamage() {
            return 4;
        }
    };

    @SidedProxy(clientSide = "tehnut.resourceful.crops2.proxy.ClientProxy", serverSide = "tehnut.resourceful.crops2.proxy.CommonProxy")
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

    @Mod.EventHandler
    public void modMapping(FMLModIdMappingEvent event) {
        ModObjects.mapping();
    }

    public static void debug(String message, Object... params) {
        if (ConfigHandler.miscellaneous.debugLogging)
            LOGGER.info("[DEBUG] " + message, params);
    }
}
