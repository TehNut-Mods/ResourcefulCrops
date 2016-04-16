package tehnut.resourceful.crops;

import lombok.Getter;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tehnut.lib.LendingLibrary;
import tehnut.lib.iface.ICompatibility;
import tehnut.lib.util.helper.ItemHelper;
import tehnut.lib.util.helper.LogHelper;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.crops.api.registry.SeedRegistry;
import tehnut.resourceful.crops.item.ItemStone;
import tehnut.resourceful.crops.proxy.CommonProxy;
import tehnut.resourceful.crops.registry.AchievementRegistry;
import tehnut.resourceful.crops.registry.CompatibilityRegistry;
import tehnut.resourceful.crops.registry.RecipeRegistry;
import tehnut.resourceful.crops.util.handler.GenerationHandler;
import tehnut.resourceful.crops.util.handler.OreDictHandler;

import java.io.File;

@Mod(modid = ModInformation.ID, name = ModInformation.NAME, version = ModInformation.VERSION, dependencies = ModInformation.REQUIRED, guiFactory = ModInformation.GUIFACTORY)
@Getter
public class ResourcefulCrops {

    @SidedProxy(clientSide = ModInformation.CLIENTPROXY, serverSide = ModInformation.COMMONPROXY)
    public static CommonProxy proxy;

    public static CreativeTabs tabResourcefulCrops = new CreativeTabs(ModInformation.ID + ".creativeTab") {
        @Override
        public ItemStack getIconItemStack() {
            return new ItemStack(ItemHelper.getItem(ItemStone.class), 1, 4);
        }

        @Override
        public Item getTabIconItem() {
            return ItemHelper.getItem(ItemStone.class);
        }
    };

    @Mod.Instance(ModInformation.ID)
    public static ResourcefulCrops instance;

    private final LendingLibrary library;
    private File configDir;

    public ResourcefulCrops() {
        library = new LendingLibrary(ModInformation.ID);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configDir = new File(event.getModConfigurationDirectory() + "/" + ModInformation.ID);
        configDir.mkdirs();
        ConfigHandler.init(new File(configDir.getPath(), ModInformation.ID + ".cfg"));

        ResourcefulAPI.logger = new LogHelper("ResourcefulCrops").getLogger();

        getLibrary().registerObjects(event);

        JsonConfigHandler.init(new File(getConfigDir(), "Seeds-v2.json"));

        AchievementRegistry.registerAchievements();
        GameRegistry.registerWorldGenerator(new GenerationHandler(), 2);

        CompatibilityRegistry.registerModCompat();
        CompatibilityRegistry.runCompat(ICompatibility.InitializationPhase.PRE_INIT);

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        RecipeRegistry.registerItemRecipes();
        OreDictHandler.load();
        CompatibilityRegistry.runCompat(ICompatibility.InitializationPhase.INIT);

        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (SeedRegistry.badSeeds > 0)
            ResourcefulAPI.logger.error(SeedRegistry.badSeeds + " Seeds failed to register.");

        CompatibilityRegistry.runCompat(ICompatibility.InitializationPhase.POST_INIT);
    }
}