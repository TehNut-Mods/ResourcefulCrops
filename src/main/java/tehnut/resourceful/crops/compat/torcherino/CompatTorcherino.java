package tehnut.resourceful.crops.compat.torcherino;

import cpw.mods.fml.common.event.FMLInterModComms;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.block.BlockRCrop;

public class CompatTorcherino {

    static {
        if (!ConfigHandler.enableTorcherinoAccelerator)
            FMLInterModComms.sendMessage("Torcherino", "blacklist-block", ModInformation.ID + ":" + BlockRCrop.class.getSimpleName());
    }
}
