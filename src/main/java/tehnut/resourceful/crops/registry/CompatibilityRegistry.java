package tehnut.resourceful.crops.registry;

import net.minecraftforge.fml.common.Loader;
import tehnut.lib.iface.ICompatibility;
import tehnut.resourceful.crops.compat.bloodmagic.CompatBloodMagic;
import tehnut.resourceful.crops.compat.harvestcrap.CompatibilityHarvestcrap;
import tehnut.resourceful.crops.compat.neotech.CompatNeoTech;
import tehnut.resourceful.crops.compat.thaumcraft.CompatibilityThaumcraft;
import tehnut.resourceful.crops.compat.torcherino.CompatTorcherino;
import tehnut.resourceful.crops.compat.waila.CompatWaila;

import java.util.ArrayList;

public class CompatibilityRegistry {

    private static ArrayList<ICompatibility> compatibilities = new ArrayList<ICompatibility>();

    public static void registerModCompat() {
        compatibilities.add(new CompatWaila());
        compatibilities.add(new CompatBloodMagic());
        compatibilities.add(new CompatNeoTech());
        compatibilities.add(new CompatTorcherino());
        compatibilities.add(new CompatibilityThaumcraft());
        compatibilities.add(new CompatibilityHarvestcrap());
    }

    public static void runCompat(ICompatibility.InitializationPhase phase) {
        for (ICompatibility compat : compatibilities)
            if (compat.enableCompat() && Loader.isModLoaded(compat.getModId()))
                compat.loadCompatibility(phase);
    }
}
