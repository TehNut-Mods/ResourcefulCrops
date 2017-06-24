package tehnut.resourceful.crops.compat;

import net.minecraftforge.fml.common.event.FMLInterModComms;
import tehnut.resourceful.crops.core.RegistrarResourcefulCrops;

@Compatibility(modid = "torcherino")
public class CompatibilityTorcherino implements ICompatibility {

    @Override
    public void loadCompatibility() {
        FMLInterModComms.sendMessage("torcherino", "blacklist-block", RegistrarResourcefulCrops.CROP.getRegistryName().toString());
    }
}
