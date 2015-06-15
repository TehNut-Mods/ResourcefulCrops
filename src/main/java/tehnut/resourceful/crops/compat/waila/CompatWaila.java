package tehnut.resourceful.crops.compat.waila;

import mcp.mobius.waila.api.impl.ModuleRegistrar;
import tehnut.resourceful.crops.blocks.BlockRCrop;

public class CompatWaila {

    static {
        ModuleRegistrar.instance().registerStackProvider(new ResourcefulCropsDataProvider(), BlockRCrop.class);
    }
}
