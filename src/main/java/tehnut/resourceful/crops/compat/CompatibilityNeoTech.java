package tehnut.resourceful.crops.compat;

import net.minecraftforge.fml.common.event.FMLInterModComms;
import tehnut.resourceful.crops.core.ModObjects;

@Compatibility(modid = "neotech", enabled = "blacklistNeotechTickAccelerator")
public class CompatibilityNeoTech implements ICompatibility {

    @Override
    public void loadCompatibility() {
        FMLInterModComms.sendMessage("neotech", "blacklistFertilizer", ModObjects.CROP.getRegistryName().toString());
    }
}
