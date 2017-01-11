package tehnut.resourceful.crops.compat;

import net.minecraftforge.fml.common.event.FMLInterModComms;
import tehnut.resourceful.crops.core.ModObjects;

@Compatibility(modid = "Torcherino")
public class CompatibilityTorcherino implements ICompatibility {

    @Override
    public void loadCompatibility() {
        FMLInterModComms.sendMessage("Torcherino", "blacklist-block", ModObjects.CROP.getRegistryName().toString());
    }
}
