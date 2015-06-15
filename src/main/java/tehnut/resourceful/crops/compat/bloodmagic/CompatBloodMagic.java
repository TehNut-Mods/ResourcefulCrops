package tehnut.resourceful.crops.compat.bloodmagic;

import WayofTime.alchemicalWizardry.api.harvest.HarvestRegistry;

public class CompatBloodMagic {

    static {
        HarvestRegistry.registerHarvestHandler(new ResourcefulCropsHarvestHandler());
    }
}
