package tehnut.resourceful.crops.compat.thaumcraft;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import tehnut.resourceful.crops.block.BlockRCrop;
import tehnut.resourceful.crops.registry.BlockRegistry;
import tehnut.resourceful.repack.tehnut.lib.iface.ICompatibility;

public class CompatibilityThaumcraft implements ICompatibility {

    @Override
    public void loadCompatibility(InitializationPhase phase) {
        if (phase == InitializationPhase.PRE_INIT)
            FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(BlockRegistry.getBlock(BlockRCrop.class), 1, 7));
    }

    @Override
    public String getModId() {
        return "Thaumcraft";
    }

    @Override
    public boolean enableCompat() {
        return true;
    }
}
