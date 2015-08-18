package tehnut.resourceful.crops.compat.mfr;

import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.MFRRegistry;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.registry.BlockRegistry;

public class CompatMFR {

    static {
        MFRRegistry.registerHarvestable(new HarvestableHandler());
        MFRRegistry.registerPlantable(new PlantableHandler());

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
}
