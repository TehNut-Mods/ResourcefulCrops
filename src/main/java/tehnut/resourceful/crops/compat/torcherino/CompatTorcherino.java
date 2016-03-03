package tehnut.resourceful.crops.compat.torcherino;

import net.minecraftforge.fml.common.event.FMLInterModComms;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.block.BlockRCrop;
import tehnut.resourceful.repack.tehnut.lib.iface.ICompatibility;

public class CompatTorcherino implements ICompatibility {

    @Override
    public void loadCompatibility(InitializationPhase phase) {
        if (phase == InitializationPhase.PRE_INIT && ConfigHandler.blacklistTorcherinoAccelerator)
            FMLInterModComms.sendMessage("Torcherino", "blacklist-block", ModInformation.ID + ":" + BlockRCrop.class.getSimpleName());
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
