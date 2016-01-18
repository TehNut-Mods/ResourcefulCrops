package tehnut.resourceful.crops.compat.bloodmagic;

import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.compat.ICompatibility;

public class CompatBloodMagic implements ICompatibility {

    @Override
    public void loadCompatibility() {
        if (ConfigHandler.enableBloodMagicAutomation)
            HandlerBloodMagic.registerHarvestHandlers();
    }

    @Override
    public String getModId() {
        return "BloodMagic";
    }

    @Override
    public boolean enableCompat() {
        return true;
    }
}
