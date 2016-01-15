package tehnut.resourceful.crops.compat.waila;

import net.minecraftforge.fml.common.event.FMLInterModComms;
import tehnut.resourceful.crops.compat.ICompatibility;

public class CompatWaila implements ICompatibility {

    @Override
    public void loadCompatibility() {
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
