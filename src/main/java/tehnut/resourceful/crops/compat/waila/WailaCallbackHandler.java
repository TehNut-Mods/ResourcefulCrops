package tehnut.resourceful.crops.compat.waila;

import mcp.mobius.waila.api.IWailaRegistrar;
import tehnut.lib.annot.Used;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.block.BlockRCrop;

@Used
public class WailaCallbackHandler {

    @Used
    public static void callbackRegister(IWailaRegistrar registrar) {
        registrar.registerStackProvider(new ResourcefulCropsDataProvider(), BlockRCrop.class);
        registrar.addConfig(ModInformation.NAME, "outputStack", true);
    }
}
