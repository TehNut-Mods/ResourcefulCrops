package tehnut.resourceful.crops.compat.bloodmagic;

import WayofTime.bloodmagic.api.registry.HarvestRegistry;

public class HandlerBloodMagic {

    public static void registerHarvestHandlers() {
        HarvestRegistry.registerHandler(new ResourcefulCropsHarvestHandler());
    }
}
