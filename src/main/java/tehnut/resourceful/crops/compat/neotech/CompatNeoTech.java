package tehnut.resourceful.crops.compat.neotech;

import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.repack.tehnut.lib.iface.ICompatibility;

public class CompatNeoTech implements ICompatibility {

    @Override
    public void loadCompatibility(InitializationPhase phase) {
        if (phase == InitializationPhase.PRE_INIT && ConfigHandler.blacklistNeoTechAccelerator)
            HandlerNeoTech.blacklistBlocks();
    }

    @Override
    public String getModId() {
        return "neotech";
    }

    @Override
    public boolean enableCompat() {
        return true;
    }
}
