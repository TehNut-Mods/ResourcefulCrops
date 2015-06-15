package tehnut.resourceful.crops.compat.waila;

import cpw.mods.fml.common.event.FMLInterModComms;
import mcp.mobius.waila.api.IWailaRegistrar;
import tehnut.resourceful.crops.blocks.BlockRCrop;

public class CompatWaila {

    static {
        FMLInterModComms.sendMessage("Waila", "register", "tehnut.resourceful.crops.compat.waila.CompatWaila.callbackRegister");
    }

    public static void callbackRegister(IWailaRegistrar registrar) {
        registrar.registerStackProvider(new ResourcefulCropsDataProvider(), BlockRCrop.class);
    }
}
