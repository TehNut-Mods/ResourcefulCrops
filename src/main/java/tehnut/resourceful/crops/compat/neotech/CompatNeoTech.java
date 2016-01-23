package tehnut.resourceful.crops.compat.neotech;

import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.compat.ICompatibility;

public class CompatNeoTech implements ICompatibility {

    @Override
    public void loadCompatibility() {
        if (ConfigHandler.blacklistNeoTechAccelerator)
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
