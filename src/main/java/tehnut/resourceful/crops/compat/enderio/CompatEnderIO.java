package tehnut.resourceful.crops.compat.enderio;

import crazypants.enderio.machine.farm.farmers.FarmersCommune;

public class CompatEnderIO {

    static {
        FarmersCommune.joinCommune(new ResourcefulCropsFarmer());
    }
}
