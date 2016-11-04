package tehnut.resourceful.crops2.compat;

import net.minecraftforge.fml.common.event.FMLInterModComms;
import tehnut.resourceful.crops2.core.ModObjects;

@Compatibility(modid = "Torcherino", enabled = "blacklistTorcherinoTickAccelerator")
public class CompatibilityTorcherino implements ICompatibility {

    @Override
    public void loadCompatibility() {
        FMLInterModComms.sendMessage("Torcherino", "blacklist-block", ModObjects.CROP.getRegistryName().toString());
    }
}
