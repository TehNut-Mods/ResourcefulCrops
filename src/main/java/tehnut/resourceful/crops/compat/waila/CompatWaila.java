package tehnut.resourceful.crops.compat.waila;

import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.block.BlockRCrop;

public class CompatWaila {

    static {
        FMLInterModComms.sendMessage("Waila", "register", "tehnut.resourceful.crops.compat.waila.CompatWaila.callbackRegister");
    }

    public static void callbackRegister(IWailaRegistrar registrar) {
        registrar.registerStackProvider(new ResourcefulCropsDataProvider(), BlockRCrop.class);
        registrar.addConfig(ModInformation.NAME, "outputStack", true);
    }
}
