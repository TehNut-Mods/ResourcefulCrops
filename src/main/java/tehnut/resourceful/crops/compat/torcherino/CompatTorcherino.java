package tehnut.resourceful.crops.compat.torcherino;

import net.minecraftforge.fml.common.event.FMLInterModComms;
import tehnut.lib.iface.ICompatibility;
import tehnut.lib.util.helper.BlockHelper;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.block.BlockRCrop;

public class CompatTorcherino implements ICompatibility {

    @Override
    public void loadCompatibility(InitializationPhase phase) {
        if (phase == InitializationPhase.PRE_INIT && ConfigHandler.blacklistTorcherinoAccelerator)
            FMLInterModComms.sendMessage("Torcherino", "blacklist-block", BlockHelper.getBlock(BlockRCrop.class).getRegistryName().toString());
    }

    @Override
    public String getModId() {
        return "Torcherino";
    }

    @Override
    public boolean enableCompat() {
        return true;
    }
}
