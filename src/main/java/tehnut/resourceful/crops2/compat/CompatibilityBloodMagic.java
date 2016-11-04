package tehnut.resourceful.crops2.compat;

import WayofTime.bloodmagic.api.registry.HarvestRegistry;
import tehnut.resourceful.crops2.core.ModObjects;

@Compatibility(modid = "BloodMagic", enabled = "enableBloodMagicAutomation")
public class CompatibilityBloodMagic implements ICompatibility {

    @Override
    public void loadCompatibility() {
        HarvestRegistry.registerStandardCrop(ModObjects.CROP, 7);
    }
}
