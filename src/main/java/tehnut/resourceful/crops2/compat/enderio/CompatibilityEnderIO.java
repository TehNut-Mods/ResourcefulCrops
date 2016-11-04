package tehnut.resourceful.crops2.compat.enderio;

import crazypants.enderio.machine.farm.FarmersRegistry;
import crazypants.enderio.machine.farm.farmers.FarmersCommune;
import tehnut.resourceful.crops2.compat.ICompatibility;
import tehnut.resourceful.crops2.core.ConfigHandler;
import tehnut.resourceful.crops2.core.ModObjects;
import tehnut.resourceful.crops2.compat.Compatibility;

@Compatibility(modid = "EnderIO")
public class CompatibilityEnderIO implements ICompatibility {

    public void loadCompatibility() {
        FarmersRegistry.DEFAULT_FARMER.addHarvestExlude(ModObjects.CROP);
        if (ConfigHandler.compatibility.enableEnderIOAutomation)
            FarmersCommune.joinCommune(new ResourcefulFarmer());
    }
}
