package tehnut.resourceful.crops.compat.waila;

import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.block.BlockRCrop;
import tehnut.resourceful.crops.compat.ICompatibility;

public class CompatWaila implements ICompatibility {

    @Override
    public void loadCompatibility() {
        FMLInterModComms.sendMessage("Waila", "register", "tehnut.resourceful.crops.compat.waila.CompatWaila.callbackRegister");
    }

    @Override
    public String getModId() {
        return "Waila";
    }

    @Override
    public boolean enableCompat() {
        return true;
    }

    public static void callbackRegister(IWailaRegistrar registrar) {
        registrar.registerStackProvider(new ResourcefulCropsDataProvider(), BlockRCrop.class);
        registrar.addConfig(ModInformation.NAME, "outputStack", true);
    }
}
