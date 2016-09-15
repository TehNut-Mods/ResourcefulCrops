package tehnut.resourceful.crops.compat.bloodmagic;

import tehnut.lib.iface.ICompatibility;
import tehnut.resourceful.crops.ConfigHandler;

public class CompatibilityBloodMagic implements ICompatibility {

    @Override
    public void loadCompatibility(InitializationPhase phase) {
        if (phase == InitializationPhase.INIT)
            HandlerBloodMagic.registerHarvestHandlers();
    }

    @Override
    public String getModId() {
        return "BloodMagic";
    }

    @Override
    public boolean enableCompat() {
        return ConfigHandler.enableBloodMagicAutomation;
    }
}
