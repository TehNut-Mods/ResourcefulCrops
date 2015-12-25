package tehnut.resourceful.crops.registry;

import cpw.mods.fml.common.Loader;
import tehnut.resourceful.crops.compat.ICompatibility;
import tehnut.resourceful.crops.compat.bloodmagic.CompatBloodMagic;
import tehnut.resourceful.crops.compat.enderio.CompatEnderIO;
import tehnut.resourceful.crops.compat.exnihilio.CompatExNihilio;
import tehnut.resourceful.crops.compat.mfr.CompatMFR;
import tehnut.resourceful.crops.compat.torcherino.CompatTorcherino;
import tehnut.resourceful.crops.compat.waila.CompatWaila;

import java.util.ArrayList;

public class CompatibilityRegistry {

    private static ArrayList<ICompatibility> compatibilities = new ArrayList<ICompatibility>();

    public static void registerModCompat() {
        compatibilities.add(new CompatBloodMagic());
        compatibilities.add(new CompatEnderIO());
        compatibilities.add(new CompatExNihilio());
        compatibilities.add(new CompatMFR());
        compatibilities.add(new CompatTorcherino());
        compatibilities.add(new CompatWaila());

        for (ICompatibility compat : compatibilities) {
            if (compat.enableCompat() && Loader.isModLoaded(compat.getModId()))
                compat.loadCompatibility();
        }
    }
}
