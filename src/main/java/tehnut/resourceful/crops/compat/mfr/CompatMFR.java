package tehnut.resourceful.crops.compat.mfr;

import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.MFRRegistry;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.compat.ICompatibility;
import tehnut.resourceful.crops.registry.BlockRegistry;

public class CompatMFR implements ICompatibility {

    @Override
    public void loadCompatibility() {
        if (ConfigHandler.enableMFRAutomation) {
            MFRRegistry.registerHarvestable(new HarvestableHandler());
            MFRRegistry.registerPlantable(new PlantableHandler());
        }

        if (ConfigHandler.enableMFRLaserDrill) {
            ItemStack oreStack;
            oreStack = new ItemStack(BlockRegistry.ore);
            MFRRegistry.registerLaserOre(5, oreStack);
            MFRRegistry.addLaserPreferredOre(10, oreStack);
            oreStack = new ItemStack(BlockRegistry.ore, 1, 1);
            MFRRegistry.registerLaserOre(5, oreStack);
            MFRRegistry.addLaserPreferredOre(10, oreStack);
        }
    }

    @Override
    public String getModId() {
        return "MineFactoryReloaded";
    }

    @Override
    public boolean enableCompat() {
        return true;
    }
}
