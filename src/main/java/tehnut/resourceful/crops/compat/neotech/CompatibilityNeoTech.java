package tehnut.resourceful.crops.compat.neotech;

import net.minecraftforge.fml.common.event.FMLInterModComms;
import tehnut.lib.iface.ICompatibility;
import tehnut.lib.util.helper.BlockHelper;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.block.BlockRCrop;

public class CompatibilityNeoTech implements ICompatibility {

    @Override
    public void loadCompatibility(InitializationPhase phase) {
        if (phase == InitializationPhase.PRE_INIT)
            FMLInterModComms.sendMessage("neotech", "blacklistFertilizer", BlockHelper.getBlock(BlockRCrop.class).getRegistryName().toString());
    }

    @Override
    public String getModId() {
        return "neotech";
    }

    @Override
    public boolean enableCompat() {
        return ConfigHandler.blacklistNeoTechAccelerator;
    }
}
