package tehnut.resourceful.crops.compat.mfr;

import powercrystals.minefactoryreloaded.api.FactoryRegistry;

public class CompatMFR {

    static {
        FactoryRegistry.sendMessage("registerHarvestable", new HarvestableHandler());
        FactoryRegistry.sendMessage("registerPlantable", new PlantableHandler());
    }
}
