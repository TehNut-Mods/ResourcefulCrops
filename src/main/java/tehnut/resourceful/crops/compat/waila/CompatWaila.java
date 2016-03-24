package tehnut.resourceful.crops.compat.waila;

import net.minecraftforge.fml.common.event.FMLInterModComms;
import tehnut.lib.iface.ICompatibility;

public class CompatWaila implements ICompatibility {

    @Override
    public void loadCompatibility(InitializationPhase phase) {
        if (phase == InitializationPhase.PRE_INIT)
            FMLInterModComms.sendMessage("Waila", "register", "tehnut.resourceful.crops.compat.waila.WailaCallbackHandler.callbackRegister");
    }

    @Override
    public String getModId() {
        return "Waila";
    }

    @Override
    public boolean enableCompat() {
        return true;
    }
}
