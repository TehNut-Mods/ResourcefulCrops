package tehnut.resourceful.crops.compat.bloodmagic;

import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.repack.tehnut.lib.iface.ICompatibility;

public class CompatBloodMagic implements ICompatibility {

    @Override
    public void loadCompatibility(InitializationPhase phase) {
//        if (phase == InitializationPhase.INIT && ConfigHandler.enableBloodMagicAutomation)
//            HandlerBloodMagic.registerHarvestHandlers();
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
