package tehnut.resourceful.crops.compat.waila;

import mcp.mobius.waila.api.impl.ModuleRegistrar;
import tehnut.resourceful.crops.blocks.BlockRCrop;

public class CompatWaila {

    public static void load() {
//        ModuleRegistrar.instance().addConfig(ModInformation.NAME, ModInformation.NAME, "waila.config.render.fancy", true);
        ModuleRegistrar.instance().registerStackProvider(new RCropDataProvider(), BlockRCrop.class);
    }
}
