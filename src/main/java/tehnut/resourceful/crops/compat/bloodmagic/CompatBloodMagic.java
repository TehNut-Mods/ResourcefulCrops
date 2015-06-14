package tehnut.resourceful.crops.compat.bloodmagic;

import WayofTime.alchemicalWizardry.api.harvest.HarvestRegistry;

public class CompatBloodMagic {

    public static void load() {
        HarvestRegistry.registerHarvestHandler(new ResourcefulCropsHarvestHandler());
    }
}
