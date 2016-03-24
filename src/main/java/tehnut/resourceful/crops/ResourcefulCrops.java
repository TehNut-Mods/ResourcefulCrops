package tehnut.resourceful.crops;

import lombok.Getter;
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
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.crops.api.registry.SeedRegistry;
import tehnut.resourceful.crops.item.ItemStone;
import tehnut.resourceful.crops.proxy.CommonProxy;
import tehnut.resourceful.crops.registry.*;
import tehnut.resourceful.crops.util.handler.GenerationHandler;
import tehnut.resourceful.crops.util.handler.OreDictHandler;
import tehnut.resourceful.repack.tehnut.lib.annot.Handler;
import tehnut.resourceful.repack.tehnut.lib.annot.ModBlock;
import tehnut.resourceful.repack.tehnut.lib.annot.ModItem;
import tehnut.resourceful.repack.tehnut.lib.iface.ICompatibility;

import java.io.File;
import java.util.Set;

@Mod(modid = ModInformation.ID, name = ModInformation.NAME, version = ModInformation.VERSION, dependencies = ModInformation.REQUIRED, guiFactory = ModInformation.GUIFACTORY)
public class ResourcefulCrops {

    @SidedProxy(clientSide = ModInformation.CLIENTPROXY, serverSide = ModInformation.COMMONPROXY)
    public static CommonProxy proxy;

    public static CreativeTabs tabResourcefulCrops = new CreativeTabs(ModInformation.ID + ".creativeTab") {
        @Override
        public ItemStack getIconItemStack() {
            return new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 4);
        }

        @Override
        public Item getTabIconItem() {
            return ItemRegistry.getItem(ItemStone.class);
        }
    };

    @Mod.Instance(ModInformation.ID)
    public static ResourcefulCrops instance;

    @Getter
    private static File configDir;
    @Getter
    public Set<ASMDataTable.ASMData> modItems;
    public Set<ASMDataTable.ASMData> modBlocks;
    public Set<ASMDataTable.ASMData> eventHandlers;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configDir = new File(event.getModConfigurationDirectory() + "/" + ModInformation.ID);
        configDir.mkdirs();
        ConfigHandler.init(new File(configDir.getPath(), ModInformation.ID + ".cfg"));

        ResourcefulAPI.logger = event.getModLog();

        modItems = event.getAsmData().getAll(ModItem.class.getCanonicalName());
        modBlocks = event.getAsmData().getAll(ModBlock.class.getCanonicalName());
        eventHandlers = event.getAsmData().getAll(Handler.class.getCanonicalName());

        BlockRegistry.init();
        ItemRegistry.init();

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
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (SeedRegistry.badSeeds > 0)
            ResourcefulAPI.logger.error(SeedRegistry.badSeeds + " Seeds failed to register.");

        CompatibilityRegistry.runCompat(ICompatibility.InitializationPhase.POST_INIT);
    }
}