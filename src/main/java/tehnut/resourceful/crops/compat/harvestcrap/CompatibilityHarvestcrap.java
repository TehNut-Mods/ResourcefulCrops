package tehnut.resourceful.crops.compat.harvestcrap;

import net.minecraft.block.Block;
import tehnut.lib.iface.ICompatibility;
import tehnut.resourceful.crops.block.BlockRCrop;

import java.util.HashSet;
import java.util.Set;

public class CompatibilityHarvestcrap implements ICompatibility {

	// Public so that anybody else who needs it can blacklist their crops.
	public static final Set<Class<? extends Block>> rightClickBlacklist = new HashSet<Class<? extends Block>>();

	@Override
	public void loadCompatibility(InitializationPhase phase) {
		if (phase == InitializationPhase.INIT) {
			HandlerHarvestcrap.init();
			rightClickBlacklist.add(BlockRCrop.class);
		}
	}

	@Override
	public String getModId() {
		return "harvestcraft";
	}

	@Override
	public boolean enableCompat() {
		return true;
	}
}
