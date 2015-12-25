package tehnut.resourceful.crops.compat.enderio;

import crazypants.enderio.machine.farm.farmers.FarmersCommune;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.compat.ICompatibility;

public class CompatEnderIO implements ICompatibility {

    @Override
    public void loadCompatibility() {
        if (ConfigHandler.enableEnderIOAutomation)
            FarmersCommune.joinCommune(new ResourcefulCropsFarmer());
    }

    @Override
    public String getModId() {
        return "EnderIO";
    }

    @Override
    public boolean enableCompat() {
        return true;
    }
}
