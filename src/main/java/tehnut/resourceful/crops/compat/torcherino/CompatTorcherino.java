package tehnut.resourceful.crops.compat.torcherino;

import cpw.mods.fml.common.event.FMLInterModComms;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.block.BlockRCrop;
import tehnut.resourceful.crops.compat.ICompatibility;

public class CompatTorcherino implements ICompatibility {

    @Override
    public void loadCompatibility() {
        if (!ConfigHandler.enableTorcherinoAccelerator)
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
