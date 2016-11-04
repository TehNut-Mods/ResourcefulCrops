package tehnut.resourceful.crops.registry;

import net.minecraftforge.fml.common.Loader;
import tehnut.lib.iface.ICompatibility;
import tehnut.resourceful.crops.compat.bloodmagic.CompatibilityBloodMagic;
import tehnut.resourceful.crops.compat.harvestcrap.CompatibilityHarvestcrap;
import tehnut.resourceful.crops.compat.neotech.CompatibilityNeoTech;
import tehnut.resourceful.crops.compat.thaumcraft.CompatibilityThaumcraft;
import tehnut.resourceful.crops.compat.torcherino.CompatibilityTorcherino;
import tehnut.resourceful.crops.compat.waila.CompatibilityWaila;

import java.util.ArrayList;

public class CompatibilityRegistry {

    private static ArrayList<ICompatibility> compatibilities = new ArrayList<ICompatibility>();

    public static void registerModCompat() {
        compatibilities.add(new CompatibilityWaila());
        compatibilities.add(new CompatibilityBloodMagic());
        compatibilities.add(new CompatibilityNeoTech());
        compatibilities.add(new CompatibilityTorcherino());
        compatibilities.add(new CompatibilityThaumcraft());
        compatibilities.add(new CompatibilityHarvestcrap());
    }

    public static void runCompat(ICompatibility.InitializationPhase phase) {
        for (ICompatibility compat : compatibilities)
            if (compat.enableCompat() && Loader.isModLoaded(compat.getModId()))
                compat.loadCompatibility(phase);
    }
}
